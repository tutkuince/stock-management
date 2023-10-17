package io.ince.stockmanagement.productservice.exception.utils;

import io.ince.stockmanagement.productservice.enums.Language;
import io.ince.stockmanagement.productservice.exception.enums.FriendlyMessageCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass
public class FriendlyMessageUtils {

    private static final String RESOURCE_BUNDLE_NAME = "FriendlyMessage";
    private static final String SPECIAL_CHARACTER = "__";

    public static String getFriendlyMessage(Language language, FriendlyMessageCode friendlyMessageCode) {
        String messageKey = null;
        try {
            Locale locale = new Locale(language.name());
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
            messageKey = friendlyMessageCode.getClass().getSimpleName() + SPECIAL_CHARACTER + friendlyMessageCode;
            return resourceBundle.getString(messageKey);
        } catch (MissingResourceException ex) {
            log.error("Friendly message nof found for key: {}", messageKey);
            return null;
        }
    }
}
