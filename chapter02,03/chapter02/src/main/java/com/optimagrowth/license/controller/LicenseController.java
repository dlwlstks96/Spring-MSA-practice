package com.optimagrowth.license.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //스프링 부트에 이 서비스는 REST 기반 서비스이며, 응답은 JSON으로 서비스 요청 및 자동으로 직렬화, 역직렬화할 것이라 지정
@RequestMapping(value="v1/organization/{organizationId}/license")

public class LicenseController {
}
