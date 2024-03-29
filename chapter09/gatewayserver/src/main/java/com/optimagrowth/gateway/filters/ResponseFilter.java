package com.optimagrowth.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Mono;

//HTTP 응답에 상관관계 ID 주입하기 (사후 필터)
@Configuration
public class ResponseFilter {

    final Logger logger =LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                //원본 HTTP 요청에 전달된 상관관계 ID를 가져온다.
                String correlationId = filterUtils.getCorrelationId(requestHeaders);
                logger.debug("Adding the correlation id to the outbound headers. {}", correlationId);
                //응답에 상관관계 ID를 삽입한다.
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
                //게이트웨이로 유입된 해당 사용자 요청의 오고 가는 항목을 모두 보여 주는 '북엔드'가 되도록
                //발신 요청 URI를 로깅한다.
                logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
            }));
        };
    }
}