package com.maybank.interview.rest.service;

import com.maybank.interview.persistence.entity.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DBBankAccountService {
    /**
     * Retrieves a paginated list of all bank accounts.
     *
     * @param pageable the pagination information, including page number and size.
     * @return a Page object containing a list of BankAccount entities and pagination details.
     */
    Page<BankAccount> findAll(Pageable pageable);

    /**
     * Create bank account
     *
     * @param bankAccount the bank account to create
     * @return the created bank account
     */
    BankAccount createBankAccount(BankAccount bankAccount);
}
