package com.gambalit.mb;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import com.bmb.servicio.seguridad.IRolesServicio;
import com.bmb.servicio.seguridad.IUsuarioServicio;
import com.gmb.modelo.seguridad.GmbRoles;
import com.gmb.modelo.seguridad.GmbUsuarios;

import net.sf.jasperreports.engine.data.ListOfArrayDataSource;

@ManagedBean(name = "usuarioMb")
@ViewScoped
public class UsuarioMB implements Serializable {
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private List<GmbUsuarios > listaUsuarios;
	private String nombre;
	private String user; 
	private String clave;
	private Date fecha;
	List<GmbRoles> listaRoles;
	List<GmbRoles> listaSource = new ArrayList<GmbRoles>();
	List<GmbRoles> listaTarget = new ArrayList<GmbRoles>();
	List<GmbRoles> listaSourceAct = new ArrayList<GmbRoles>();
	List<GmbRoles> listaTargetAct = new ArrayList<GmbRoles>();
	private DualListModel<GmbRoles> roles;
	private DualListModel<GmbRoles> rolesAsignados;
	private DualListModel<String> cities = new DualListModel<String>();
	private GmbUsuarios usuarioSelect;
	private List<GmbUsuarios> usuariosFilter;
	private GmbUsuarios usuarioEliminar;

	@EJB
	private IUsuarioServicio usuarioOp;
	@EJB
	private IRolesServicio rolServicio;

	@PostConstruct
	public void init() {

		llenarUsuarios();
		picklistFill();

	}

	public void llenarUsuarios() {
		listaUsuarios = usuarioOp.getUsuarios();
	}

	public void picklistFill() {

		listaRoles = rolServicio.consultaRoles();
		for (GmbRoles gmbRoles : listaRoles) {
			listaSource.add(gmbRoles);
		}
		// listaTarget.add("ingreso");
		roles = new DualListModel<GmbRoles>(listaSource, listaTarget);
	}

	public DualListModel<String> getCities() {
		return cities;
	}

	public void setCities(DualListModel<String> cities) {
		this.cities = cities;
	}

	public void getUsuarioSeleccionado() {
		listaTargetAct = new ArrayList<GmbRoles>();
		listaSourceAct = new ArrayList<GmbRoles>();
		boolean bandera = false;

		if (usuarioSelect != null) {
			listaRoles = rolServicio.consultaRoles();
			List<GmbRoles> toRemove = new ArrayList<GmbRoles>();
			for (GmbRoles gmbRolesSel : usuarioSelect.getRoles()) {
				listaTargetAct.add(gmbRolesSel);
			}
			Long valorx = listaRoles.get(0).getIdRol();
			for (GmbRoles a : listaRoles) {
				for (GmbRoles b : listaTargetAct) {
					if (b.getIdRol() == a.getIdRol()) {
						System.out.println("objetos_repetidos");
					} else {
						listaSourceAct.add(a);
					}
				}
			}
			rolesAsignados = new DualListModel<GmbRoles>(listaSourceAct, listaTargetAct);
		}
		RequestContext.getCurrentInstance().execute("PF('dlgActualizarUsu').show()");
	}

	public void cargarUsuarioEliminar(GmbUsuarios usuario)
	{
		usuarioEliminar=usuario;
		 usuarioEliminar.setEstado("I");
		 usuarioOp.modificarUsuario(usuarioEliminar);
		 llenarUsuarios();
		 addMessage("Usuario eliminado exitosamente");
	}
	
	/*public void eliminarUsuario()
	{
		 usuarioEliminar.setEstado("I");
		 usuarioOp.modificarUsuario(usuarioEliminar);
		 llenarUsuarios();
		 addMessage("Usuario eliminado exitosamente");
	}*/
	
	public void modificarUsuario() {
		GmbUsuarios usuario = new GmbUsuarios();
		usuario.setIdUsuario(usuarioSelect.getIdUsuario());
		usuario.setNombre(usuarioSelect.getNombre());
		usuario.setClave(usuarioSelect.getClave());
		usuario.setFechaIngreso(usuarioSelect.getFechaIngreso());
		usuario.setFechaultimaModificacion(usuarioSelect.getFechaultimaModificacion());
		usuario.setUsuario(usuarioSelect.getUsuario());
		usuario.setEstado(usuarioSelect.getEstado());
		if (rolesAsignados.getTarget().size() > 1) {
			errorMessage("Escoger solo un rol");
		} else {
			usuarioSelect.setRoles(rolesAsignados.getTarget());
			usuario.setRoles(usuarioSelect.getRoles());
			usuarioOp.modificarUsuario(usuario);
			// llenarUsuarios();
			RequestContext.getCurrentInstance().execute("PF('dlgActualizarUsu').hide()");
			addMessage("Modificacion de rol exitosa");
		}
	}

	public void insertarUsuario() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		GmbUsuarios usuario = new GmbUsuarios();
		usuario.setNombre(nombre);
		usuario.setUsuario(user);
		usuario.setClave(user);
		usuario.setEstado("A");
		usuario.setRoles(roles.getTarget());
		if (nombre == null || user == null ||  roles.getTarget().size() < 1) {
			errorMessage("Los campos no pueden estar vacios");
		} else if (roles.getTarget().size() > 1) {
			errorMessage("solo puede escoger un rol para el usuario " + nombre);
		} else {
			usuarioOp.insertarUsuario(usuario);
			RequestContext.getCurrentInstance().execute("PF('dlgInsertUsuario').hide()");
			llenarUsuarios();
			addMessage("Usuario Ingresado exitosamente");
		}
	}

	public List<GmbUsuarios> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<GmbUsuarios> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public DualListModel<GmbRoles> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<GmbRoles> roles) {
		this.roles = roles;
	}

	public GmbUsuarios getUsuarioSelect() {
		return usuarioSelect;
	}

	public void setUsuarioSelect(GmbUsuarios usuarioSelect) {
		this.usuarioSelect = usuarioSelect;
	}

	public List<GmbUsuarios> getUsuariosFilter() {
		return usuariosFilter;
	}

	public void setUsuariosFilter(List<GmbUsuarios> usuariosFilter) {
		this.usuariosFilter = usuariosFilter;
	}

	public DualListModel<GmbRoles> getRolesAsignados() {
		return rolesAsignados;
	}

	public void setRolesAsignados(DualListModel<GmbRoles> rolesAsignados) {
		this.rolesAsignados = rolesAsignados;
	}

	public GmbUsuarios getUsuarioEliminar() {
		return usuarioEliminar;
	}

	public void setUsuarioEliminar(GmbUsuarios usuarioEliminar) {
		this.usuarioEliminar = usuarioEliminar;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void errorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
