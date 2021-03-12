package com.kwetter.frits.accountservice.controller;

import com.kwetter.frits.accountservice.entity.Account;
import com.kwetter.frits.accountservice.logic.AccountLogicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountLogicImpl accountLogic;

    @GetMapping()
    public ResponseEntity<Account> retrieveAccountById(@RequestBody Account account) {
        try {
            Optional<Account> _account = accountLogic.findById(account.getId());
            if (_account.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Account _accountEntity = _account.get();
            return new ResponseEntity<>(_accountEntity, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account _account = accountLogic.createAccount(new Account(account.getUsername(), account.getName(), account.getSurname(), false));
            return new ResponseEntity<>(_account, HttpStatus.CREATED);
        }

        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
