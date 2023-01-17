package com.example.openfeign;

import lombok.Getter;

@Getter
public enum Currency {
    USD("미국"),
    KRW("한국"),
    JPY("일본"),
    PHP("필리핀");

    private final String nation;

    Currency(String nation) {
        this.nation = nation;
    }
}
