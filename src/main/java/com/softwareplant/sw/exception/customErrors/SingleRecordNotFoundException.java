package com.softwareplant.sw.exception.customErrors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleRecordNotFoundException extends RuntimeException {
    private String message;
}
