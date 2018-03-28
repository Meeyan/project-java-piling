package com.std.gof23.L02_factory.factory_method;

/**
 * html模式的导出
 *
 * @author zhaojy
 * @create-time 2018-03-21
 */
public class ExportHtmlFactory implements ExportFactory {
    @Override
    public ExportFile exportFactory(String type) {
        if (type.equals("standard")) {
            return new ExportStandardHtmlFile();
        } else if (type.equals("financial")) {
            return new ExportFinancialHtmlFile();
        } else {
            throw new RuntimeException("类型错误");
        }
    }
}
