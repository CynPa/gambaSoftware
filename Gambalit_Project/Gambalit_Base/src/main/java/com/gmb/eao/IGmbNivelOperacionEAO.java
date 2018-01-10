package com.gmb.eao;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;

import com.gmb.modelo.GmbNivelOperacion;
@Local
public interface IGmbNivelOperacionEAO {
	
	public List<GmbNivelOperacion> getListaNivelOperacion();
	public void insertNivelOperacion(GmbNivelOperacion objeto);
	public GmbNivelOperacion getNivelOperacion();
	public void eliminarNivelOperacion(GmbNivelOperacion objeto);
	public GmbNivelOperacion getNivelOperacionxId(Integer idNivelOperacion);
	public void actualizarNivelOperacion(GmbNivelOperacion objeto);
	public BigInteger getOperacionDiariaxFechaxPiscina(String piscina);
}
