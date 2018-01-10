package com.bmb.servicio.seguridad;

import java.util.List;

import javax.ejb.Local;

import com.gmb.modelo.seguridad.GmbOpciones;
 @Local
public interface IOpcionesServicio { 
	public List<GmbOpciones> getOpcionesAll();
	public List<GmbOpciones> getListaOpciones();
	public List<GmbOpciones> getListaOpcionesHijas(Integer id_padre);
	public List<GmbOpciones> getListaOpcionesHijas();
	public GmbOpciones getOpcionHija(Integer id_Padre);
	public List<GmbOpciones> getListaOpcionesxUsuario(String usuario);
	public List<GmbOpciones> getListaOpcionesHijasxUsuario( Integer id_padre, String usuario);

}
