package com.example.firstProject.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestAPI용 컨트롤러 Json 반환
public class FirstApiController {

    //Data를 반환함
    @GetMapping("/api/hello")
    public String hello(){
        return "hello world!";
    }



}
