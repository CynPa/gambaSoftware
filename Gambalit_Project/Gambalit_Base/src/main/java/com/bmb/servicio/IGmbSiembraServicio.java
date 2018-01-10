package com.bmb.servicio;

import java.util.List;

import javax.ejb.Local;

import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbSiembra;

@Local
public interface IGmbSiembraServicio {

	public List<GmbSiembra> consultarSiembra();

	public void ingresarSiembra(GmbSiembra objeto);

	public void modificarSiembra(GmbSiembra objeto);

	public Integer validarSiembraIngreso(GmbNivelOperacion objeto);

	public List<GmbSiembra> getSiembraxPiscinaActiva();

	public GmbSiembra getSiembraxId(Integer id);

}
