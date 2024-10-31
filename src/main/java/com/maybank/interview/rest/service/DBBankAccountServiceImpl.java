package com.maybank.interview.rest.service;

import com.maybank.interview.persistence.entity.BankAccount;
import com.maybank.interview.persistence.repository.BankAccountRepository;
import com.maybank.interview.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class DBBankAccountServiceImpl implements DBBankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        validateBankAccount(bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public Page<BankAccount> findAll(Pageable pageable) {
        return bankAccountRepository.findAll(pageable);
    }

    private void validateBankAccount(BankAccount bankAccount) {
        if (bankAccount.getAccountHolder() == null || bankAccount.getAccountHolder().isEmpty()) {
            throw ExceptionUtil.buildRequestException("Account holder name cannot be empty.");
        }

        if (bankAccount.getBalance() == null || bankAccount.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw ExceptionUtil.buildRequestException("Initial deposit must be greater than or equal to zero.");
        }
    }
}
