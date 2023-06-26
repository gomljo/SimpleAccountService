package com.simpleAccountService.domain;

import com.simpleAccountService.type.TransactionResultType;
import com.simpleAccountService.type.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction extends BaseEntity{


    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionResultType transactionResultType;

    @ManyToOne
    private Account account;
    private Long amount;
    private Long balanceSnapshot;

    private String transactionId;
    private LocalDateTime transactedAt;

    public boolean isSameAmount(Long amount){
        return Objects.equals(this.amount, amount);
    }

    public boolean isOverAYearFromCurrentTime(){
        return transactedAt.isBefore(LocalDateTime.now().minusYears(1));
    }

}
