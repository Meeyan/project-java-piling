package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gz文件压缩&解压
 * 测试结果：
 * -- 104M的json文件，压缩后54兆，耗时7s
 * -- 解压54M的gz文件，耗时2s
 *
 * @author zhaojy
 * @date 2020/4/1 6:15 PM
 */
public class ZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    /**
     *
     */
    public final static String FORMAT_GZ = "gz";

    /**
     * int:1024
     */
    public final static int VALUE_1024 = 1024;

    public static void main(String[] args) {
        // unZipIt("/Users/zhaojy/Desktop/client-vpc-prd06_202004010925_202004011200_2.json.gz", "/mnt/logs/tmp/1212312.json");

        String inputFile = "/mnt/logs/tmp/client-vpc-prd06_202004010925_202004011200_2.json";
        //  doUncompressFile("/Users/zhaojy/Desktop/client-vpc-prd06_202004010925_202004011200_2.json.gz", "/mnt/logs/tmp/");

        doCompressFile(inputFile);
    }

    /**
     * 压缩文件
     *
     * @param inFileName String
     */
    public static void doCompressFile(String inFileName) {
        long startTime = System.currentTimeMillis();
        try {
            String outFileName = inFileName + ".gz";
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(outFileName));

            FileInputStream fileInputStream = new FileInputStream(inFileName);

            logger.info("src:{},dest:{}", inFileName, outFileName);

            FileChannel inputStreamChannel = fileInputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(VALUE_1024);
            while (true) {
                buffer.clear();
                int read = inputStreamChannel.read(buffer);
                if (read == -1) {
                    break;
                }
                buffer.flip();
                gzipOutputStream.write(buffer.array(), 0, read);
            }

            inputStreamChannel.close();
            fileInputStream.close();
            gzipOutputStream.finish();
            gzipOutputStream.flush();
            gzipOutputStream.close();
            logger.info("gz compress cost:{}", (System.currentTimeMillis() - startTime));
        } catch (IOException e) {
            logger.error("error happens:", e);
        }
    }

    /**
     * Uncompress the incoming file.
     *
     * @param inFileName String gz文件全路径
     * @param outputPath String 文件解压后路径，不包含文件名
     */
    private static void doUncompressFile(String inFileName, String outputPath) {
        try {
            long startTime = System.currentTimeMillis();
            if (!getExtension(inFileName).equalsIgnoreCase(FORMAT_GZ)) {
                // TODO: 2020/4/2 处理异常
            }
            GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(inFileName));

            String outFileName = outputPath + getFileName(inFileName);

            FileOutputStream fileOutputStream = new FileOutputStream(outFileName);

            byte[] bytes = new byte[VALUE_1024];
            int len;

            // 输出使用nio
            FileChannel outChannel = fileOutputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(VALUE_1024);
            while (true) {
                buffer.clear();
                len = gzipInputStream.read(bytes);
                if (len == -1) {
                    break;
                }
                buffer.put(bytes, 0, len);
                buffer.flip();
                outChannel.write(buffer);
            }
            outChannel.close();
            fileOutputStream.close();
            gzipInputStream.close();
            System.out.println(System.currentTimeMillis() - startTime + " ms");
        } catch (IOException e) {
            logger.error("error happens:", e);
        }
    }

    /**
     * Used to extract and return the extension of a given file.
     *
     * @param f Incoming file to get the extension of
     * @return <code>String</code> representing the extension of the incoming
     * file.
     */
    public static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {
            ext = f.substring(i + 1);
        }
        return ext;
    }

    /**
     * Used to extract the filename without its extension.
     *
     * @param file Incoming file to get the filename
     * @return <code>String</code> representing the filename without its
     * extension.
     */
    public static String getFileName(String file) {
        String fname = "";
        file = file.substring(file.lastIndexOf(File.separator));
        int i = file.lastIndexOf('.');
        if (i > 0 && i < file.length() - 1) {
            fname = file.substring(0, i);
        }
        return fname;
    }
}
