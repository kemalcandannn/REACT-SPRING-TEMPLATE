package com.my_company.constants.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE,
    PASSIVE;

    public static Status getStatus(String value) {
        return Arrays
                .stream(Status.values())
                .filter(status -> status.name().equals(value))
                .findFirst()
                .orElse(PASSIVE);
    }
}