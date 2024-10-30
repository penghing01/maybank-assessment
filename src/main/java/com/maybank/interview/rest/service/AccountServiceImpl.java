package com.maybank.interview.rest.service;

import com.maybank.interview.persistence.entity.AccountType;
import com.maybank.interview.persistence.entity.BankAccount;
import com.maybank.interview.util.IdHelper;
import com.maybank.interview.web.generated.model.CreateAccountRequest;
import com.maybank.interview.web.generated.model.CreateAccountResponse;
import com.maybank.interview.web.generated.model.GetAllAccountsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DBBankAccountService dbBankAccountService;
    private final ModelMapper modelMapper;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        LOGGER.info("Start bank account creation: {}", createAccountRequest);
        // This one is just a random number, the real case should be some logic to get a real available account number
        var accountId = IdHelper.generateNumericId();
        BankAccount bankAccount = modelMapper.map(createAccountRequest, BankAccount.class);
        bankAccount.setAccountId(accountId);
        bankAccount.setAccountType(AccountType.valueOf(createAccountRequest.getAccountType()));
        bankAccount.setBalance(BigDecimal.valueOf(createAccountRequest.getInitialDeposit()));
        dbBankAccountService.createBankAccount(bankAccount);
        return new CreateAccountResponse()
                .accountHolder(createAccountRequest.getAccountHolder())
                .accountId(accountId)
                .accountType(bankAccount.getAccountType().getValue())
                .balance(createAccountRequest.getInitialDeposit());
    }

    @Override
    public GetAllAccountsResponse getAllBankAccounts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        List<CreateAccountResponse> accounts = dbBankAccountService.findAll(pageable)
                .map(account -> new CreateAccountResponse()
                        .accountId(account.getAccountId())
                        .accountHolder(account.getAccountHolder())
                        .accountType(account.getAccountType().toString())
                        .balance(account.getBalance().floatValue()))
                .getContent();

        long totalElements = accounts.size(); // Get total number of records
        int totalPages = (int) Math.ceil((double) totalElements / size); // Calculate total pages

        // Create response
        return new GetAllAccountsResponse()
                .content(accounts)
                .totalPages(totalPages)
                .totalElements((int) totalElements)
                .size(size)
                .number(page);
    }
}
