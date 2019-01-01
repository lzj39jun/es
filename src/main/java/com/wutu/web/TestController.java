package com.wutu.web;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "/test", tags = "测试文档类型")
@RequestMapping("/test")
@ResponseBody
@Controller
public class TestController {

    @RequestMapping("/index")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://www.baidu.com");
    }
}