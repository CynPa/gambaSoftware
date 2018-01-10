package com.bmb.servicio.seguridad;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.gmb.modelo.seguridad.GmbOpciones;
import com.gmb.seguridad.eao.GmbOpcionesEAO;

@Stateless
public class GmbOpcionesServicio implements IOpcionesServicio {

	@EJB
	private GmbOpcionesEAO opcionEAO;
	
	
	public List<GmbOpciones> getOpcionesAll()
	{
		return opcionEAO.consultarOpciones();
	}
	

	public List<GmbOpciones> getListaOpciones() {
		return opcionEAO.getOpciones();
	}

	public List<GmbOpciones> getListaOpcionesHijas(Integer id_padre) {
		return opcionEAO.getOpcionesHijas(id_padre);
	}
	
	public List<GmbOpciones> getListaOpcionesHijas() {
		return opcionEAO.getOpcionesHijas();
	}
	
	public GmbOpciones getOpcionHija(Integer id_Padre)
	{
		return opcionEAO.getOpcionHijas(id_Padre);
	}
	
	public List<GmbOpciones> getListaOpcionesxUsuario(String usuario) {
		return opcionEAO.getOpcionesxUsuario(usuario);
	}
	public List<GmbOpciones> getListaOpcionesHijasxUsuario( Integer id_padre, String usuario)
	{
		return opcionEAO.getOpcionesHijas(id_padre,usuario);
	}
}
