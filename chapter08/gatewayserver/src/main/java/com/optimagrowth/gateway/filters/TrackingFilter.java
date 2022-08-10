package com.optimagrowth.gateway.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//상관관계 ID를 생성하는 사전 필터
@Order(1)
@Component
public class TrackingFilter implements GlobalFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    FilterUtils filterUtils; //여러 필터에 걸쳐 공통으로 사용되는 함수는 FilterUtils 클래스에 캡슐화 된다.

    //요청이 필터를 통과할 때마다 실행되는 코드
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //매개 변수로 전달된 ServerWebExchange 객체를 사용하여 요청에서 ServerWebExchange 객체 HTTP 헤더를 추출
        HttpHeaders requestHeaders =
                exchange.getRequest().getHeaders();

        if (isCorrelationIdPresent(requestHeaders)) {
            logger.debug("tmx-correlation-id found in tracking filter: {}. ",
                    filterUtils.getCorrelationId(requestHeaders));
        } else {
            String correlationId = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationId);
            logger.debug(
                    "tmx-correlation-id generated in tracking filter: {}.",
                    correlationId);
        }
        return chain.filter(exchange);
    }

    //요청 헤더에 상관관계 ID가 있는지 확인하는 헬퍼 메소드
    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (filterUtils.getCorrelationId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    //tmx-correlation-id가 있는지 확인하는 헬퍼 메소드이며, 상관관계 ID를 UUID 값으로 생성한다.
    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
