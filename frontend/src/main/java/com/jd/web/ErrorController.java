package com.jd.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class ErrorController {

    @RequestMapping(value = "submitError", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "errors/submitError";
    }
    
    /**
     * 404
     * @return
     */
    @RequestMapping("errors/404")
    public String noFound(){
    	return "errors/404";
    }


}
