package com.maybank.interview.util;

import com.maybank.interview.exception.RequestException;
import com.maybank.interview.web.generated.model.RequestError;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    /**
     * Build request exception by inputting message
     *
     * @param errorMessage error message you want to include in the response
     * @return RequestException
     */
    public static RequestException buildRequestException(String errorMessage) {
        return new RequestException(new RequestError().message(errorMessage));
    }
}
