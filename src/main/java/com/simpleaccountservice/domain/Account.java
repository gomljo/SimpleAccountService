package com.simpleAccountService.domain;

import com.simpleAccountService.exception.AccountException;
import com.simpleAccountService.type.AccountStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static com.simpleAccountService.type.ErrorCode.AMOUNT_EXCEED_BALANCE;
import static com.simpleAccountService.type.ErrorCode.INVALID_REQUEST;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account extends BaseEntity {

    @ManyToOne
    private AccountUser accountUser;
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    public void useBalance(Long amount) {
        if (amount > balance) {
            throw new AccountException(AMOUNT_EXCEED_BALANCE);
        }
        balance -= amount;
    }

    public void cancelBalance(Long amount) {
        if (amount < 0) {
            throw new AccountException(INVALID_REQUEST);
        }
        balance += amount;
    }

    public boolean isAccountStatusUnregistered() {
        return accountStatus == AccountStatus.UNREGISTERED;
    }

    public boolean existBalance() {
        return balance > 0;
    }

    public boolean isRequestedAmountOverBalance(Long amount){
        return amount > balance;
    }
}
