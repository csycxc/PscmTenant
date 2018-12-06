package com.banry.pscm.tenant.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping("/index")
	public ModelAndView index() {
        return new ModelAndView("index");
    }
	
	@RequestMapping("/to")
	public Object tourl(String url) {
		System.out.println("欢迎您url"+url);
		ModelAndView mv = new ModelAndView(url);
		return mv;
	}
	
}
