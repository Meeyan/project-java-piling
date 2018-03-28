package com.std.gof23.L02_factory.factory_method;

/**
 * 导出工厂
 *
 * @author zhaojy
 * @create-time 2018-03-21
 */
public interface ExportFactory {
    public ExportFile exportFactory(String type);
}
