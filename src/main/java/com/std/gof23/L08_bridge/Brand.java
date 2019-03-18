package com.std.gof23.L08_bridge;

/**
 * 电脑品牌
 *
 * @author zhaojy
 * @date 2018-03-09
 */
public interface Brand {
    String getBrandName();
}

class Lenevo implements Brand {

    @Override
    public String getBrandName() {
        return "联想";
    }
}


class Dell implements Brand {

    @Override
    public String getBrandName() {
        return "戴尔";
    }
}

class Hasse implements Brand {

    @Override
    public String getBrandName() {
        return "神舟";
    }
}
