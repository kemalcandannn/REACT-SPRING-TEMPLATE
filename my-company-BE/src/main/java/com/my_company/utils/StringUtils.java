package com.my_company.utils;

import com.my_company.constants.ApplicationConstants;

import java.util.Objects;

public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static boolean isNullOrBlank(String text) {
        return Objects.isNull(text) || text.isBlank();
    }
}
