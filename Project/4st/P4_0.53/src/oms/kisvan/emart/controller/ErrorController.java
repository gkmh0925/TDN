package oms.kisvan.emart.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//에러 컨트롤러
public class ErrorController {

	// 400, 404 등 에러페이지 처리
	@RequestMapping("/error")
	public String errPage() throws IOException {
		return "errorPage";
	}

	// 커스텀 Exception 처리
	@RequestMapping("/error2")
	public String errPage2() throws IOException {
		return "errorPage2";
	}
}
