package com.maybank.interview.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
    SAVING("saving"),
    CURRENT("current");

    private final String value;
}