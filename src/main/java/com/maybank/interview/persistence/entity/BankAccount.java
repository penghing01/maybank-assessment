package com.maybank.interview.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "bank_account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends AuditableEntity {

    @Id
    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
}
