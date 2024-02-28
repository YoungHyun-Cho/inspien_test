package org.inspien.enums;

import lombok.Getter;

/*
* # CharSet.enum
*   - 캐릭터 셋을 상수로 사용하기 위한 Enum
*   - 캐릭터 셋에 대한 잘못된 제공을 방지한다.
* */

public enum CharSet {
    UTF_8("UTF-8"),
    EUC_KR("EUC-KR");

    @Getter
    private String name;

    CharSet(String name) {
        this.name = name;
    }
}
