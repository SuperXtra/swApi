package com.softwareplant.sw.exception.customErrors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MultipleRecordsNotFoundException extends RuntimeException {
    private String message;
}
