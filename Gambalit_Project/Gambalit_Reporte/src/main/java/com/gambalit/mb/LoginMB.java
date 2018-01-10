package com.gambalit.mb;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.bmb.servicio.seguridad.IUsuarioServicio;
import com.gambalit.properties.Propiedades;
 


@ManagedBean(name = "login")
@SessionScoped  
public class LoginMB implements Serializable {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String usuario;
	private String clave;
   
	@EJB
	IUsuarioServicio  usuarioServicio;

	public LoginMB() {
		// TODO Auto-generated constructor stub
	}

	public void validarUsuario() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		String pagina="";
		Boolean bandera=false;
		if(usuario !=null && clave!=null)
		{
		bandera=usuarioServicio.autenticarUsuario(usuario, clave);
		}
		else
		{
			bandera=false;
		}
		
	     if(bandera==true)
	     {
	    	 FacesContext context = FacesContext.getCurrentInstance();
	    	 context.getExternalContext().getSessionMap().put("user", usuario);
	   
	    		String path = context.getExternalContext().getRequestContextPath();
	    	 addMessage("Bienvenido "+usuario);
	    	// pagina=Propiedades.valor_pagina_principal;
	    	 FacesContext.getCurrentInstance().getExternalContext().redirect(path+Propiedades.valor_pagina_principal);
	     }
	     else
	     {
	    	 pagina=Propiedades.valor_pagina_login;
	    	errorMessage("error usuario y/o clave incorrecta"); 
	    	
	     }
	    // return pagina;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void errorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void logout() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		//return Propiedades.valor_pagina_login;
		FacesContext.getCurrentInstance().getExternalContext().redirect("."+Propiedades.valor_pagina_login);
		// logeado = false;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	// FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(),
	// null, "principal.jsf")

}
