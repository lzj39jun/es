package com.wutu.web;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/test",tags = "测试文档类型")
@RequestMapping("/test")
@ResponseBody
@Controller
public class TestController {

    @RequestMapping("/index")
    public String index(){
        return "hello word";
    }
}
