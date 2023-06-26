package com.simpleAccountService.controller;

import com.simpleAccountService.domain.Account;
import com.simpleAccountService.dto.account.AccountInfo;
import com.simpleAccountService.dto.account.CreateAccount;
import com.simpleAccountService.dto.account.DeleteAccount;
import com.simpleAccountService.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

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

    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request){

        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber())
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
            @RequestParam("user_id") Long userId
    ){
        return accountService.getAccountsByUserId(userId)
                .stream().map(accountDto -> AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());
    }


    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
}
