package br.com.paygo.enums;

public enum PTIKey {
    KEY_0((short)48),
    KEY_1((short)49),
    KEY_2((short)50),
    KEY_3((short)51),
    KEY_4((short)52),
    KEY_5((short)53),
    KEY_6((short)54),
    KEY_7((short)55),
    KEY_8((short)56),
    KEY_9((short)57),
    KEY_STAR((short)42),
    KEY_HASH((short)35),
    KEY_DOT((short)46),
    KEY_00((short)37),
    KEY_BACKSP((short)8),
    KEY_OK((short)13),
    KEY_CANCEL((short)27),
    KEY_FUNC0((short)97),
    KEY_FUNC1((short)98),
    KEY_FUNC2((short)99),
    KEY_FUNC3((short)100),
    KEY_FUNC4((short)101),
    KEY_FUNC5((short)102),
    KEY_FUNC6((short)103),
    KEY_FUNC7((short)104),
    KEY_FUNC8((short)105),
    KEY_FUNC9((short)106),
    KEY_FUNC10((short)107),
    KEY_TOUCH((short)126),
    KEY_ALPHA((short)38);

    private final short value;

    PTIKey(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
