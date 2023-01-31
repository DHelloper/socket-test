package com.example.demo.test;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping(value = "/")
    public String main() {
        return "index.html";
    }

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat(@RequestParam(value = "name") String name) {
        System.out.println("param name : " + name);
        return "chat.html";
    }

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public String seller() {
        return "seller.html";
    }

}