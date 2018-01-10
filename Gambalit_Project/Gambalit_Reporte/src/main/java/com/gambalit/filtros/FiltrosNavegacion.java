package com.gambalit.filtros;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gambalit.properties.Propiedades;

/**
 * Servlet Filter implementation class FiltrosNavegacion
 */

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, urlPatterns = { "/generales/*", "/seguridades/*","/reportes/*"})
public class FiltrosNavegacion implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltrosNavegacion() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		res.setDateHeader("Expires", 0);
		res.setHeader("Pragma", "No-cache");
		//System.out.println(" validador de sesion ");
		// Obtengo el bean que representa el usuario desde el scope sesión
		String loginBean = (String) req.getSession().getAttribute("user");
		if (loginBean == null) {
			//FacesContext ctx = FacesContext.getCurrentInstance();
			//String path = ctx.getExternalContext().getRequestContextPath();
         res.sendRedirect("/gambalit"+Propiedades.valor_pagina_login);
		}
		else
		{
		chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
