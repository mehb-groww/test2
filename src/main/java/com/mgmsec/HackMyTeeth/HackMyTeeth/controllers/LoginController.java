package com.mgmsec.HackMyTeeth.HackMyTeeth.controllers;

import java.time.Clock;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.mgmsec.HackMyTeeth.HackMyTeeth.model.User;
import com.mgmsec.HackMyTeeth.HackMyTeeth.model.Session;

import com.mgmsec.HackMyTeeth.HackMyTeeth.service.UserService;
import com.mgmsec.HackMyTeeth.HackMyTeeth.service.SessionService;

import com.mgmsec.HackMyTeeth.HackMyTeeth.setting.SecuritySettings;
@Controller
//@Scope("session")
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	SessionService sessService;
	@Autowired
	SecuritySettings secSettings;
	@RequestMapping("/login")
	public ModelAndView firstPage() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Cookie loginCookie = sessService.checkLoginCookie(request);
		if(loginCookie != null) {
			System.out.println("Login Cookie is: " +loginCookie.getValue());
			
			Session sessions = sessService.findBySession(loginCookie.getValue());
			if (sessions != null) {
				System.out.println("session is" + sessions);
				List<User> listDentist = userService.listDentist();
				System.out.println(listDentist);
				for (User e: listDentist) {
					System.out.println(e.toString());
				}
				modelAndView.addObject("listDentist",listDentist);
				modelAndView.addObject("role",sessions.getRole());
				modelAndView.addObject("username",sessions.getUsername());
				modelAndView.setViewName("home");
			}
			else {
				modelAndView.setViewName("login");
			}
		}
		else {
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}
	@RequestMapping("/welcome")
	public ModelAndView welcome() {
		return new ModelAndView("welcome");
	}
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public String LogoutPage(HttpServletRequest request, HttpServletResponse response) {
		Cookie loginCookie =  sessService.checkLoginCookie(request);
		if (loginCookie != null) {
            loginCookie.setMaxAge(0);
            response.addCookie(loginCookie);
            sessService.delSession(loginCookie.getValue());
        }
		
		return "redirect:/login";
	}

	@RequestMapping(value = "/loginVal2" , method = RequestMethod.POST)
	public ModelAndView login2(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		List<User> get = userService.findByUser(request.getParameter("username"), request.getParameter("password"));
		System.out.print(get.get(0));
		if(get == null) {
			modelAndView.addObject("errorMessage", "Invalid username or password");
			modelAndView.setViewName("login");
			
		}else if(get.get(0).getRole().equals("1")) {
			setSessionCookie(request,response,get.get(0).getUsername(),Integer.parseInt(get.get(0).getRole()),(int) get.get(0).getuserID());
			return new ModelAndView("redirect:/home");

		}else if(get.get(0).getRole().equals("0")){
			setSessionCookie(request,response,get.get(0).getUsername(),Integer.parseInt(get.get(0).getRole()),(int) get.get(0).getuserID());
			return new ModelAndView("redirect:/home");

		}
		return modelAndView;
		
	}
	private void setSessionCookie(HttpServletRequest request, HttpServletResponse response, String username, int role, int userID ) {
		String sessionid = null;
		switch(secSettings.getSessFix()) {
			case Yes:
				sessionid = sessService.addSession(userID, username, role);
				break;
			case No:
				Cookie loginCookie =  sessService.checkLoginCookie(request);
				if (loginCookie != null) {
		            sessionid = loginCookie.getValue();
		        }
				else {
					sessionid = sessService.addSession(userID, username, role);
				}
				break;
		}
		Cookie loginCookie = new Cookie("SESSIONCOOKIE", sessionid);
		switch(secSettings.getCookParam()) {
			case True:
				loginCookie.setHttpOnly(true);
				break;
			case False:
				break;
		}
		response.addCookie(loginCookie);
		
	}
	@RequestMapping(value = "/loginVal", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		String name = user.getUsername();
		String pass =user.getPassword();
		modelAndView.setViewName("login");
		
		String reUser = userService.findByUsername(name, pass);
		String s1 = "customer";
		String s2 = "dentist";
		String s3 = "invalid";
		if (reUser.equals(s3)) {
			modelAndView.addObject("errorMessage", "Invalid username or password");
			modelAndView.setViewName("login");
			return modelAndView;
		} else if (reUser.equals(s1)) {
			return new ModelAndView("redirect:/home");
		} else if (reUser.equals(s2)) {
			
		}
		return modelAndView;
	}
}