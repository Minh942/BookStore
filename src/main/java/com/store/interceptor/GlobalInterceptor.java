package com.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.store.service.CategoryService;
import com.store.service.InformationShopService;
import com.store.service.SessionService;
import com.store.service.ShoppingCartService;

@Component
public class GlobalInterceptor implements HandlerInterceptor {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	InformationShopService informationService;
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	ShoppingCartService cartService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		request.setAttribute("categories", categoryService.findAll());
		request.setAttribute("information", informationService.getOneInformationShop());
		sessionService.set("sessionProduct", cartService);
	}
}
