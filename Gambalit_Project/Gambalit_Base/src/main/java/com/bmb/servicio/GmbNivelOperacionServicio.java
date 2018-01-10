package com.bmb.servicio;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.gmb.eao.GmbNivelOperacionEAO;
import com.gmb.eao.IGmbNivelOperacionEAO;
import com.gmb.modelo.GmbNivelOperacion;


@Stateless
public class GmbNivelOperacionServicio implements IGmbNivelOperacionEAO {

	@EJB
	private GmbNivelOperacionEAO gmbNivelOperacionEAO;// =new GmbNivelOperacionEAO();

	public List<GmbNivelOperacion> getListaNivelOperacion() {
		
		return getGmbNivelOperacionEAO().consultarNivelOperacion();
	}
	public void insertNivelOperacion(GmbNivelOperacion objeto) {
		 getGmbNivelOperacionEAO().insertarNivelOperacion(objeto);
	}
	
	public GmbNivelOperacion getNivelOperacion()
	{
		return getGmbNivelOperacionEAO().getNivelOperacion();
	}
	public void eliminarNivelOperacion(GmbNivelOperacion objeto) {
		getGmbNivelOperacionEAO().eliminarNivelOperacion(objeto);
	}
	
	public void actualizarNivelOperacion(GmbNivelOperacion objeto) {
		getGmbNivelOperacionEAO().actualizarNivelOperacion(objeto);
	}
	public GmbNivelOperacion getNivelOperacionxId(Integer idNivelOperacion)
	{
		return getGmbNivelOperacionEAO().getNivelOperacionxId(idNivelOperacion);
	}
	public BigInteger getOperacionDiariaxFechaxPiscina(String piscina)
	{
		return getGmbNivelOperacionEAO().getOperacionDiariaxFechaxPiscina(piscina);
	}
	
	
	public GmbNivelOperacionEAO getGmbNivelOperacionEAO() {
		return gmbNivelOperacionEAO;
	}

	public void setGmbNivelOperacionEAO(GmbNivelOperacionEAO gmbNivelOperacionEAO) {
		this.gmbNivelOperacionEAO = gmbNivelOperacionEAO;
	}


}
