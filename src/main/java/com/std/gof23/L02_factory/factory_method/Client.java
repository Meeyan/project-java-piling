package com.std.gof23.L02_factory.factory_method;

/**
 * @author zhaojy
 * @create-time 2018-03-21
 */
public class Client {
    public static void main(String[] args) {
        String data = "";
        ExportFactory exportFactory = new ExportHtmlFactory();
        ExportFile ef = exportFactory.exportFactory("financial");
        ef.export(data);
    }
}
