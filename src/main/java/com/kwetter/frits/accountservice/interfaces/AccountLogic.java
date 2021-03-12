package com.kwetter.frits.accountservice.interfaces;

import com.kwetter.frits.accountservice.entity.Account;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface AccountLogic {
    Optional<Account> findById(Integer id);
    Account createAccount(Account account);
}
