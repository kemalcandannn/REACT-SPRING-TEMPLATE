package com.my_company.constants;

public class SequenceConstants {
    private SequenceConstants() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static final String DEFAULT_SEQ_ALIAS = "_SEQ";
    public static final String USER_TOKEN_SEQ = TableConstants.USER_TOKEN + DEFAULT_SEQ_ALIAS;
}
