package com.my_company.utils;

import com.my_company.constants.ApplicationConstants;

import java.util.Collection;
import java.util.Objects;

public final class CollectionUtils {
    private CollectionUtils() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static <LIST extends Collection<?>> boolean isEmpty(LIST list) {
        return Objects.isNull(list) || list.isEmpty();
    }

    public static <LIST extends Collection<?>> boolean isNotEmpty(LIST list) {
        return !isEmpty(list);
    }
}
