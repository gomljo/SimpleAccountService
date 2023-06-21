package com.simpleaccountservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
    // EnableJpaAuditing 어노테이션을 설정해놓음으로써 데이터를 저장, 수정 시
    // @CreateDate, @LastModifiedDate 어노테이션이 지정된 위치에 시간을 자동으로 저장
}
