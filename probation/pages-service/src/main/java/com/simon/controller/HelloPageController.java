package com.simon.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Component
@Controller
public class HelloPageController {

    //pass value by ModelAndView
    @RequestMapping("hello")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");//test.html must be in templates folder
        modelAndView.addObject("key", "test value");
        return modelAndView;
    }

    //pass value by Map/Model
    @RequestMapping("user")
    public String login(HashMap<String, Object> map, Model model) {
        model.addAttribute("say", "hello");
        map.put("key", "test value2");
        return "user";
    }
}
