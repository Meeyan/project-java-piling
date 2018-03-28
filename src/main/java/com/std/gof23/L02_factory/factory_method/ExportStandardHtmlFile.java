package com.std.gof23.L02_factory.factory_method;

/**
 * @author zhaojy
 * @create-time 2018-03-21
 */
public class ExportStandardHtmlFile implements ExportFile {
    @Override
    public boolean export(String data) {
        /**
         * 业务逻辑
         */
        System.out.println("导出标准HTML文件");
        return true;
    }
}
