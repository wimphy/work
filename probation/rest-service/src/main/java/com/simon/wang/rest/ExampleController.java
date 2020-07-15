package com.simon.wang.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@RestController
public class ExampleController {

    @PostConstruct
    public void init(){}

    @PreDestroy
    public void destroy(){}

    @ApiOperation(value = "Hello World")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "Hello World";
    }

}
