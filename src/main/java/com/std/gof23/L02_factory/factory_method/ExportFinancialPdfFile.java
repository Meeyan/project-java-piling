package com.std.gof23.L02_factory.factory_method;

/**
 * @author zhaojy
 * @date 2018-03-21
 */
public class ExportFinancialPdfFile implements ExportFile {
    @Override
    public boolean export(String data) {
        /**
         * 业务逻辑
         */
        System.out.println("导出财务版PDF文件");
        return true;
    }
}
