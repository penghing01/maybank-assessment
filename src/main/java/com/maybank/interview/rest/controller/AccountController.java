package com.maybank.interview.rest.controller;

import com.maybank.interview.rest.service.AccountService;
import com.maybank.interview.web.generated.api.AccountApi;
import com.maybank.interview.web.generated.model.CreateAccountRequest;
import com.maybank.interview.web.generated.model.CreateAccountResponse;
import com.maybank.interview.web.generated.model.GetAllAccountsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
    private final AccountService accountService;

    @Override
    public ResponseEntity<CreateAccountResponse> createBankAccount(CreateAccountRequest CreateAccountRequest) {
        LOGGER.debug("Create bank account: {}", CreateAccountRequest);
        return new ResponseEntity<>(accountService.createAccount(CreateAccountRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GetAllAccountsResponse> getAllBankAccounts(Integer page, Integer size) {
        LOGGER.debug("Get all bank accounts: {} page {} size", page, size);
        GetAllAccountsResponse response = accountService.getAllBankAccounts(page, size);
        return ResponseEntity.ok(response);
    }
}
