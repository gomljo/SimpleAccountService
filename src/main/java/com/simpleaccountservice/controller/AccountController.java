package com.simpleaccountservice.controller;

import com.simpleaccountservice.domain.Account;
import com.simpleaccountservice.dto.AccountDto;
import com.simpleaccountservice.dto.CreateAccount;
import com.simpleaccountservice.service.AccountService;
import com.simpleaccountservice.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RedisTestService redisTestService;

    // @Valid 어노테이션이 붙은 객체는 어떤 조건이 유효한 조건인지 명시해주어야 좋다
    @PostMapping("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request){

        return CreateAccount.Response.from(
                accountService.createAccount(
                request.getUserId(),
                request.getInitialBalance())
        );
    }

    @GetMapping("/get-lock")
    public String getLock(){
        return redisTestService.getLock();
    }



    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
}
