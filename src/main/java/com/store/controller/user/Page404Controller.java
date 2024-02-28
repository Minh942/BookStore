package com.store.controller.user;

import com.store.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Page404Controller {
	
	/**
	 * Hien thi page khi nguoi dung truy cap khong dung vai tro
	 * 
	 * @param model
	 * @return page 404
	 */
	@GetMapping("/404page")
	public String display404Page(Model model) {
		return Constants.USER_DISPLAY_404_PAGE;
	}
	
}
