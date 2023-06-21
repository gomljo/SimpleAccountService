package com.simpleaccountservice.service;

import com.simpleaccountservice.domain.Account;
import com.simpleaccountservice.domain.AccountUser;
import com.simpleaccountservice.dto.AccountDto;
import com.simpleaccountservice.exception.AccountException;
import com.simpleaccountservice.repository.AccountRepository;
import com.simpleaccountservice.repository.AccountUserRepository;
import com.simpleaccountservice.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static com.simpleaccountservice.type.AccountStatus.IN_USE;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;
    // final 타입은 무조건 생성자에 들어가야하기 때문에
    // 무조건 생성자에 포함되어 있음

    /**
     *
     * 사용자가 있는지 조회
     * 계좌의 번호를 생성하고
     * 계좌를 저장하고, 그 정보를 넘긴다
     */
    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance){
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));

        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc()
                .map(account -> String.valueOf((Integer.parseInt(account.getAccountNumber())) + 1))
                .orElse("1000000000");
        // 이런 방식으로 엔티티 타입을 리턴하게 되면
        // 지연 로딩이나
        // 엔티티 값 모두가 필요하지 않을 수 있기 때문에
        // 데이터 전송 객체를 이용한다.
        // 반환 구문에서 다소 어색하거나 호불호가 갈릴 수 있지만
        // 따로 분리한 경우, 중간에 로직이 삽입될 가능성이 존재하여
        // 런타임 시 객체가 가지는 값이 어떤 것인지 알기 어려울 수 있기 때문에
        // 아래와 같은 반환 구문을 이용하여 로직을 넣는 것을 방지하여 혼란을 막는다.


        return AccountDto.fromEntity(accountRepository.save(
                Account.builder()
                        .accountUser(accountUser)
                        .accountStatus(IN_USE)
                        .accountNumber(newAccountNumber)
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build())
        );
    }

    @Transactional
    public Account getAccount(Long id){
        if(id < 0){
            throw new RuntimeException("Minus");
        }
        return accountRepository.findById(id).get();
    }
}
