package com.std.gof23.L02_factory.factory_method;

/**
 * @author zhaojy
 * @date 2018-03-21
 */
public class ExportPdfFactory implements ExportFactory {
    @Override
    public ExportFile exportFactory(String type) {
        if (type.equals("standard")) {
            return new ExportStandardPdfFile();
        } else if (type.equals("financial")) {
            return new ExportFinancialPdfFile();
        } else {
            throw new RuntimeException("类型错误");
        }
    }
}
