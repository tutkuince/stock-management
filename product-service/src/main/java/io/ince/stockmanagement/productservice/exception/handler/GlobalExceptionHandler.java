package io.ince.stockmanagement.productservice.exception.handler;

import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import io.ince.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import io.ince.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import io.ince.stockmanagement.productservice.response.FriendlyMessage;
import io.ince.stockmanagement.productservice.response.InternalAPIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotCreatedException.class)
    public InternalAPIResponse<String> handleProductNotCreatedException(ProductNotCreatedException exception) {
        return InternalAPIResponse.<String>builder().friendlyMessage(
                        FriendlyMessage.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION))
                                .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                                .build()
                ).httpStatus(HttpStatus.BAD_REQUEST).hasError(true).errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }
}
