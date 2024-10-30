package com.maybank.interview.rest.service;

import com.maybank.interview.persistence.entity.BankAccount;
import com.maybank.interview.persistence.repository.BankAccountRepository;
import com.maybank.interview.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DBBankAccountServiceImpl implements DBBankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    @Transactional
    public BankAccount createBankAccount(BankAccount bankAccount) {
        validateBankAccount(bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    @Transactional
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
