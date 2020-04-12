package com.softwareplant.sw.exception.customErrors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryReturnedNoDataException extends RuntimeException {
    private String message;
}
