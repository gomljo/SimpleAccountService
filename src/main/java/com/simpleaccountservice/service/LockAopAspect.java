package com.simpleAccountService.service;

import com.simpleAccountService.aop.AccountLockIdInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {
    private final LockService lockService;
    // args(request): @AccountLock 이라는 어노테이션을 붙인 메서드의 request라는 파라미터를 가지고 오는 역할
    @Around("@annotation(com.simpleAccountService.aop.AccountLock) && args(request)")
    public Object aroundMethod(
            ProceedingJoinPoint pjp,
            AccountLockIdInterface request
    ) throws Throwable {
        // lock 취득 시도
        lockService.lock(request.getAccountNumber());
        try{
            // before
            return pjp.proceed();
            // after
        }
        finally {
            // lock 해제
            lockService.unLock(request.getAccountNumber());

        }
    }
}
