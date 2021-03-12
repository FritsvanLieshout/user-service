package com.kwetter.frits.accountservice.logic;

import com.kwetter.frits.accountservice.entity.Account;
import com.kwetter.frits.accountservice.interfaces.AccountLogic;
import com.kwetter.frits.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountLogicImpl implements AccountLogic {

    private AccountRepository accountRepository;

    public AccountLogicImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
