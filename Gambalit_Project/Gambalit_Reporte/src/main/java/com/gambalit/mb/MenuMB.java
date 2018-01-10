package com.gambalit.mb;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import com.bmb.servicio.seguridad.IOpcionesServicio;
import com.bmb.servicio.seguridad.IUsuarioServicio;
import com.gmb.modelo.seguridad.GmbOpciones;
import com.gmb.modelo.seguridad.GmbUsuarios;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMessages;

@ManagedBean(name = "menuMB")
@ViewScoped
public class MenuMB implements Serializable {
	/** 
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private MenuModel menubar = new DefaultMenuModel();
	FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
	// String usuarioSesion= (String) session.getAttribute("usuario");
	private String clave;
	private String nuevaClave;
	private String confirmaClave;
	String usuarioSesion;

	private String valorUsuario;

	@PostConstruct
	public void init() {

		usuarioSesion = (String) session.getAttribute("user");
		valorUsuario = usuarioSesion;
		// addMenu("File", "New", "Open", "Close", "Exit");
		/// addMenu("Edit", "Undo", "Redo", "Cut", "Copy");
		// addMenu(addMenu("View", "Summary"), "Tools", "Settings", "Layout");
		// addMenu("Help", "Help topics", "Support");
		llenarMenu();
	}

	private MenuModel menuModel;
	@EJB
	private IOpcionesServicio opcionesServicio;
	@EJB
	private IUsuarioServicio  usuariosServicio;

	private MenuModel model = new DefaultMenuModel();

	FacesContext ctx = FacesContext.getCurrentInstance();
	String path = ctx.getExternalContext().getRequestContextPath();

	public void llenarMenu() {
		model = new DefaultMenuModel();
		// List<GmbOpciones> lista=opcionesServicio.getListaOpciones();
		List<GmbOpciones> lista = opcionesServicio.getListaOpcionesxUsuario(valorUsuario);
		DefaultSubMenu subMenu = null;
		for (GmbOpciones gmbOpciones : lista) {
			if (gmbOpciones.getIdPadre() != null) {
				subMenu = new DefaultSubMenu(gmbOpciones.getNombre().toString());
				DefaultMenuItem item = null;
				// List<GmbOpciones>
				// listaHija=opcionesServicio.getListaOpcionesHijas(Integer.parseInt(gmbOpciones.getIdOpcion().toString()));

				List<GmbOpciones> listaHija = opcionesServicio.getListaOpcionesHijasxUsuario(
						Integer.parseInt(gmbOpciones.getIdOpcion().toString()), valorUsuario);

				for (GmbOpciones gmbOpcionesHija : listaHija) {
					item = new DefaultMenuItem(gmbOpcionesHija.getNombre().toString());
					item.setUrl(gmbOpcionesHija.getRuta());
					item.setValue(gmbOpcionesHija.getNombre().toString());
					subMenu.addElement(item);
				}
			}
			model.addElement(subMenu);
		}
	}

	public DefaultSubMenu addMenu(String label, String... items) {
		return addMenu(null, label, items);
	}

	public DefaultSubMenu addMenu(DefaultSubMenu parentMenu, String label, String... items) {
		DefaultSubMenu theMenu = new DefaultSubMenu(label);
		for (Object item : items) {
			DefaultMenuItem mi = new DefaultMenuItem(item);
			mi.setUrl("#");
			theMenu.addElement(mi);
		}
		if (parentMenu == null) {
			model.addElement(theMenu);
		} else {
			parentMenu.addElement(theMenu);
		}
		return theMenu;
		
	}
	
	public void mostrarDialog()
	{
		RequestContext.getCurrentInstance().execute("PF('dlgCambioClave').show()");
	}

	public void cambioClave()
			throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		try {

			String claveBase = usuariosServicio.getClavexUsuario(usuarioSesion);
			if (clave == null || nuevaClave == "" || confirmaClave == null) {
				errorMessage("ingrese valores");
			} else if (!clave.equals(claveBase)) {
				errorMessage("la clave ingresada no esta correcta");
			} else if (!nuevaClave.equals(confirmaClave)) {
				errorMessage("La clave nueva y la confirmacion no son iguales");
			} else {
				GmbUsuarios usuario = usuariosServicio.getUsuarioxUsuarioClave(usuarioSesion, clave);
				usuario.setClave(nuevaClave);
				usuariosServicio.modificarUsuarioClave(usuario);
				addMessage("Cambio de clave exitoso");
			}
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage("Error al realizar cambio de clave");
		}

	}

	public MenuModel getMenuModel() {
		return model;
	}

	public String getValorUsuario() {
		return valorUsuario;
	}

	public void setValorUsuario(String valorUsuario) {
		this.valorUsuario = valorUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getConfirmaClave() {
		return confirmaClave;
	}

	public void setConfirmaClave(String confirmaClave) {
		this.confirmaClave = confirmaClave;
	}

	public String getNuevaClave() {
		return nuevaClave;
	}

	public void setNuevaClave(String nuevaClave) {
		this.nuevaClave = nuevaClave;
	}

	public void irOperaciones() {
		/*
		 * HttpServletRequest req = (HttpServletRequest)
		 * FacesContext.getCurrentInstance().getExternalContext().getRequest(); String
		 * url = req.getRequestURL().toString(); System.out.println("Path 123 :"+
		 * url.substring(0, url.length() - req.getRequestURI().length()) +
		 * req.getContextPath() + "/");
		 * 
		 * FacesContext ctx = FacesContext.getCurrentInstance(); String path =
		 * ctx.getExternalContext().getRequestContextPath();
		 * System.out.println("PATH "+path+"/Operaciones.jsf");
		 */

		// FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(),
		// null, "Operaciones.jsf");

		// return "/Operaciones.jsf";
	}

	/*
	 * List<GmbOpciones> lista=opcionesServicio.getListaOpciones(); DefaultSubMenu
	 * parentMenu=new DefaultSubMenu();
	 * 
	 * for (GmbOpciones gmbOpciones : lista) {
	 * 
	 * DefaultSubMenu theMenu = new DefaultSubMenu();
	 * if(gmbOpciones.getIdPadre()==null) {
	 * theMenu.setLabel(gmbOpciones.getNombre());
	 * 
	 * } if(gmbOpciones.getIdPadre()!=null) { DefaultMenuItem mi = new
	 * DefaultMenuItem(); mi.setValue(gmbOpciones.getNombre());
	 * mi.setUrl(gmbOpciones.getRuta()); theMenu.addElement(mi);
	 * model.addElement(theMenu); } else {
	 * 
	 * parentMenu.addElement(theMenu); model.addElement(parentMenu); }
	 */
	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void errorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
