package com.gmb.seguridad.eao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.seguridad.GmbUsuarios;

@Stateless
public class GmbUsuarioEAO extends GmbConexion<GmbUsuarios, Serializable> {

	@SuppressWarnings("unchecked")
	public List<GmbUsuarios> consultarUsuarios() {

		List<GmbUsuarios> usuarios = null;
		try {
			usuarios = new ArrayList<>();
			Query query = entityMgr.createNamedQuery("findAllUsuarios");
			usuarios = (List<GmbUsuarios>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return usuarios;
	}

	public void insertarUsuario(GmbUsuarios objeto) {
		entityMgr.persist(objeto);
	}

	public void actualizarUsuario(GmbUsuarios objeto) {
		entityMgr.merge(objeto);
	}

	public Boolean autenticarUsuario(String usuario, String clave) {
		Boolean bandera = false;
		BigInteger contador;
		Integer contadorValidador = 0;
		Query query = entityMgr.createNativeQuery(
				"select count(*) from  Gmb_Usuarios c WHERE c.estado='A' and c.usuario= ? and c.clave= ?");
		query.setParameter(1, usuario);
		query.setParameter(2, clave);
		contador = (BigInteger) query.getSingleResult();
		contadorValidador = contador.intValue();
		if (contadorValidador > 0) {
			bandera = true;
		} else {
			bandera = false;
		}
		return bandera;
	}

	public GmbUsuarios getUsuario(String usuario, String clave) {
		GmbUsuarios gmbUsuario = new GmbUsuarios();
		try {
			
			Query query = entityMgr.createNativeQuery(
					"select * from  Gmb_Usuarios c WHERE c.estado='A' and c.usuario= ? and c.clave= ?",
					GmbUsuarios.class);
			query.setParameter(1, usuario);
			query.setParameter(2, clave);
			return (GmbUsuarios) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			return gmbUsuario;
		}
	}

	public String getClavexUsuario(String usuario) {
		Query query = entityMgr
				.createNativeQuery("select c.clave from  Gmb_Usuarios c WHERE c.estado='A' and c.usuario= ?");
		query.setParameter(1, usuario);
		return (String) query.getSingleResult();
	}

}
