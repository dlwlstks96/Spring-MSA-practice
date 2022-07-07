package com.chapter01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication //이 클래스를 스프링 부트 서비스의 진입점으로 스프링 부트에 지정
@RestController //이 클래스의 코드를 Spring RestController로 노출하도록 스프링 부트에 지정
@RequestMapping(value = "hello") //이 애플리케이션에서 노출되는 모든 URL 앞에 /hello가 붙는다.
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping(value = "/{firstName}")
	public String helloGET(
			@PathVariable("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		//직접 간단한 JSON 문자열을 만들어 반환한다.
		return String.format("{\"message\":\"Hello %s %s\"}",
				firstName, lastName);
	}

	@PostMapping
	public String helloPOST( @RequestBody HelloRequest request) {
		return String.format("{\"message\":\"Hello %s %s\"}",
				request.getFirstName(), request.getLastName());
	}

}

class HelloRequest { //사용자가 전송한 JSON 구조체 필드를 저장한다.

	private String firstName;
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
