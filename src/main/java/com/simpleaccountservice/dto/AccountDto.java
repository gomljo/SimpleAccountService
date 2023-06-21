package com.simpleaccountservice.dto;

import com.simpleaccountservice.domain.Account;
import lombok.*;

import java.time.LocalDateTime;


/**
 * 생성자를 통해서 dto를 생성할 수도 있지만
 * dto는 대부분 어떤 도메인 엔티티를 전달하는 역할을 하므로
 * 정적 메서드를 이용하여 변환해주는 것이 가독성도 높고 훨씬 안전하게 생성 가능
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long userId;
    private String accountNumber;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    public static AccountDto fromEntity(Account account){
        return AccountDto.builder()
                .userId(account.getAccountUser().getId())
                .accountNumber(account.getAccountNumber())
                .registeredAt(account.getRegisteredAt())
                .unRegisteredAt(account.getUpRegisteredAt())
                .build();
    }
}
