package com.maybank.interview.exception;

import com.maybank.interview.web.generated.model.RequestError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestException extends RuntimeException {
    private final RequestError requestError;
}
