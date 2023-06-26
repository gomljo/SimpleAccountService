package com.simpleAccountService.service;

import com.simpleAccountService.exception.AccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.simpleAccountService.type.ErrorCode.ACCOUNT_TRANSACTION_LOCK;

@Service
@Slf4j
@RequiredArgsConstructor
public class LockService {

    private final RedissonClient redissonClient;

    public void lock(String accountNumber){
        // ACLK: + accountNumber => lock의 키
        RLock lock = redissonClient.getLock(getLockKey(accountNumber));
        log.debug("Trying lock for accountNumber: {}", accountNumber);

        try {
            boolean isLock = lock.tryLock(16,15, TimeUnit.SECONDS);
            if(!isLock){
                log.error("=====Lock acquisition failed=====");
                throw new AccountException(ACCOUNT_TRANSACTION_LOCK);
            }

        }
        catch (AccountException accountException){
            throw accountException;
        }
        catch (Exception e){
            log.error("Redis lock failed");
        }
    }

    public void unLock(String accountNumber){
        log.debug("Unlock for accountNumber: {}", accountNumber);
        redissonClient.getLock(getLockKey(accountNumber)).unlock();
    }
    private static String getLockKey(String accountNumber) {
        return "ACLK:" + accountNumber;
    }
}
