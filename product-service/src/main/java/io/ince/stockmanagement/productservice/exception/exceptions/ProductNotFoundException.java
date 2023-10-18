package io.ince.stockmanagement.productservice.exception.exceptions;

import io.ince.stockmanagement.productservice.enums.Language;
import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCode;
import io.ince.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductNotFoundException extends RuntimeException {
    private final Language language;
    private final FriendlyMessageCode friendlyMessageCode;

    public ProductNotFoundException(Language language, FriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[ProductNotFoundException] -> message: {} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }
}
