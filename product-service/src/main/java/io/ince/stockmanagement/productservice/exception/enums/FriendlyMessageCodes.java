package io.ince.stockmanagement.productservice.exception.enums;

import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCode;

public enum FriendlyMessageCodes implements FriendlyMessageCode {
    OK(1000),
    SUCCESS(1001),
    ERROR(1002),
    PRODUCT_NOT_CREATED_EXCEPTION(1500),
    PRODUCT_SUCCESSFULLY_CREATED(1501);
    private final int value;

    FriendlyMessageCodes(int value) {
        this.value = value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
