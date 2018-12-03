package com.wutu.web;

import com.wutu.vo.UserVO;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/user",tags = "用户文档")
@RequestMapping("/user/")
@ResponseBody
@Controller
public class UserController {

    @ApiOperation(value = "用户信息列表",notes = "方法备注说明")
    @ApiImplicitParam(name = "name",required = true,defaultValue = "0")
    @ApiResponse(code =1 ,message ="dddd",response = UserVO.class,reference = "sss")
    @GetMapping("list")
    public UserVO list(){
        return new UserVO();
    }
}
