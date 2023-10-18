package io.ince.stockmanagement.productservice.exception.enums;

import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCode;

public enum FriendlyMessageCodes implements FriendlyMessageCode {
    OK(1000),
    SUCCESS(1001),
    ERROR(1002),
    PRODUCT_NOT_CREATED_EXCEPTION(1500),
    PRODUCT_SUCCESSFULLY_CREATED(1501),
    PRODUCT_NOT_FOUND(1502),
    PRODUCT_SUCCESSFULLY_UPDATED(1503),
    PRODUCT_ALREADY_DELETED(1504);

    private final int value;

    FriendlyMessageCodes(int value) {
        this.value = value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
