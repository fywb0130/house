package com.zeng;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zengqiang on 2017/8/11.
 */
@RestController
public class Controller {

    @RequestMapping("lj")
    public String result() {
        return "lj";
    }
}
