package com.Project3.BackEnd.Filters;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Project3.BackEnd.REST.RegisteredUsers;
import com.Project3.BackEnd.TicketsManagement.MD5;
import com.Project3.BackEnd.TicketsManagement.User;

@WebFilter(filterName = "userAuthFilter", urlPatterns = "/api/tickets/*")
public class UserAuthFilter implements Filter {
	private RegisteredUsers users = RegisteredUsers.getInstance();
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authorizationHeader = httpRequest.getParameter("Authorization");
		String fromHeader = httpRequest.getParameter("From");
		
		if (validateAuthorization(authorizationHeader,fromHeader)) {			
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(401);
		}
	}
	
	private boolean validateAuthorization(String password, String id) {
		User user = users.getUser(id);
		MD5 md5 = new MD5(password);
		try {
			password = md5.getMD5();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return password.equals(user.getPassword());
	}

}
