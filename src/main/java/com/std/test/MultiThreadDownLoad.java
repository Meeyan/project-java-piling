package com.std.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 多线程下载
 *
 * @author zhaojy
 * @createTime 2017-08-10
 */
public class MultiThreadDownLoad {

    private static int threadCnt = 3;   // 总线程数量

    public static void main(String[] args) throws Exception {
        String fileUrl = "http://dev.birdfenqi.com/php-5.4.45.tar.gz";
        String saveName = "D:/demodir/local/test.tar.gz";

        URL url = new URL(fileUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // 文件长度：字节
        long contentLength = urlConnection.getContentLength();

        long blockSize = contentLength / threadCnt; // 每个线程负责下载的总长度

        // 开启随机访问文件
        RandomAccessFile randomAccessFile = new RandomAccessFile(saveName, "rw");
        randomAccessFile.setLength(contentLength);  // 预定文件大小
        randomAccessFile.close();

        long startTime = System.currentTimeMillis();

        List<DownLoadThread> threadList = new ArrayList<>();
        for (int i = 0; i < threadCnt; i++) {
            // 准备计算文件的位置
            long startIndex = i * blockSize;                // 开始下载的起始位置
            long endIndex = (i + 1) * blockSize - 1;        // 下载的结束位置

            if (i + 1 == threadCnt) {
                endIndex = contentLength;
            }
            System.out.println(startIndex + "--------" + endIndex);
            DownLoadThread downLoadThread = new DownLoadThread("dThread-" + i, startIndex, endIndex, fileUrl, saveName);
            downLoadThread.start();
            threadList.add(downLoadThread);
        }

        //todo 加入join测试效率，是否和带宽有关？
        for (DownLoadThread th : threadList) {
            // th.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

    /**
     * 下载线程
     */
    static class DownLoadThread extends Thread {

        private String threadId;
        private long startIndex;
        private long endIndex;
        private String fileUrl;
        private String fileName;   // 存到本地的文件名

        // 构造函数传入参数
        public DownLoadThread(String threadId, long startIndex, long endIndex, String fileUrl, String fileName) {
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.fileUrl = fileUrl;
            this.fileName = fileName;
        }

        /**
         * 下载逻辑
         */
        @Override
        public void run() {

            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Range", "bytes=" + this.startIndex + "-" + this.endIndex);
                urlConnection.setRequestMethod("GET");

                // 文件长度：字节
                long contentLength = urlConnection.getContentLength();

                InputStream inputStream = urlConnection.getInputStream();
                int length = -1;
                byte[] bytes = new byte[1024 * 1024];  // 读取的文件长度

                RandomAccessFile randomAccessFile = new RandomAccessFile(this.fileName, "rw");
                randomAccessFile.seek(startIndex);  // 设定开始写的位置，重要
                long currPos = startIndex;

                File locFile = new File("D:/demodir/local/" + threadId + ".txt");
                FileOutputStream fos = new FileOutputStream(locFile);
                while ((length = inputStream.read(bytes)) != -1) {
                    randomAccessFile.write(bytes, 0, length);
                    currPos += length + startIndex;
                    fos.write(String.valueOf(currPos + "-----").getBytes());    // 这个位置有问题，比实际位置多+了1
                }
                fos.close();
                randomAccessFile.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
