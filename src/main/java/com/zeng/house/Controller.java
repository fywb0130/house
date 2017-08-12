package com.zeng.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zengqiang on 2017/8/11.
 */
@RestController
public class Controller {
    @Autowired
    private LianjiaCrawler lianjiaCrawler;

    @RequestMapping("lj")
    public String result() {
        return "lj";
    }
}
