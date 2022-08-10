package com.optimagrowth.license.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    //UsetContextInterceptor를 사용하려면 RestTemplate 빈을 정의한 후 UserContextInterceptor를 그 빈에 추가해야 한다.
    //이를 위해 main 클래스에 RestTemplate 빈을 정의한다.

    private static final Logger logger = LoggerFactory.getLogger(UserContextInterceptor.class);

    //RestTemplate에서 실제 HTTP 서비스 호출이 발생하기 전에 intercept()를 호출한다.
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        //발신 서비스를 호출하고자 준비 중인 HTTP 요청 헤더에 UserContext에 저장된 상관관계 ID를 추가한다.
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());

        return execution.execute(request, body);
    }
}