package org.inspien.type;

import lombok.Getter;

public enum CharSet {
    UTF_8("UTF-8"),
    EUC_KR("EUC-KR");

    @Getter
    private String name;

    CharSet(String name) {
        this.name = name;
    }
}
