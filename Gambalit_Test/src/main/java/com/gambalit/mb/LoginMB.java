package com.gambalit.mb;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name="login")
@ViewScoped
public class LoginMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String usuaro;
	private String clave;
	
	
	public String getUsuaro() {
		return usuaro;
	}

	public void setUsuaro(String usuaro) {
		this.usuaro = usuaro;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LoginMB() {
		// TODO Auto-generated constructor stub
	}
	
	public void validarUsuario(ActionEvent ev)
	{
	     if(usuaro.equals("paul"))
	     {
	    	 System.out.println("hola como estas");
	    	 FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "principal.jsf");
	     }
	     else
	     {
	    	errorMessage("error usuario y/o clave incorrecta"); 
	     }
	}
	
	public void addMessage(String summary) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    }
	
	public void errorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	

}
