package com.bmb.servicio;

import java.util.List;

import javax.ejb.Local;

import com.gmb.modelo.GmbSalinidad;

@Local
public interface IGmbSalinidadServicio {
	public List<GmbSalinidad> getListaSalinidad();

	public GmbSalinidad getListaSalinidadLast13Months();

	public String insertSalinidad(GmbSalinidad objeto);

	public void eliminarSalinidad(GmbSalinidad objeto);

	public void actualizarSalinidad(GmbSalinidad objeto);

}
