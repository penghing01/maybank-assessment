package com.maybank.interview.rest.service;

import com.maybank.interview.integration.BlacklistClient;
import com.maybank.interview.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final BlacklistClient blacklistClient;

    @Override
    public void validateUser(String username) {
        var response = blacklistClient.checkUserBlacklistStatus(username);

        if (Objects.requireNonNull(response.getBody()).getIsBlacklisted().equals(Boolean.TRUE)) {
            throw ExceptionUtil.buildRequestException(String.format("user [%s] is blacklisted.", username));
        }
    }
}
