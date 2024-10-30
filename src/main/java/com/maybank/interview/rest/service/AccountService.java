package com.maybank.interview.rest.service;

import com.maybank.interview.web.generated.model.CreateAccountRequest;
import com.maybank.interview.web.generated.model.CreateAccountResponse;
import com.maybank.interview.web.generated.model.GetAllAccountsResponse;

import java.util.List;

public interface AccountService {
    /**
     * Create bank account
     *
     * @param CreateAccountRequest the bank account to create
     * @return the created bank account
     */
    CreateAccountResponse createAccount(CreateAccountRequest CreateAccountRequest);

    /**
     * Retrieves a paginated list of all bank accounts.
     *
     * @param page the page number to retrieve (0-based index).
     * @param size the number of accounts per page.
     * @return a list of CreateAccountResponse containing account details.
     */
    GetAllAccountsResponse getAllBankAccounts(int page, int size);
}
