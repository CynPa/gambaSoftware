package com.bmb.servicio.seguridad;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB; 
import javax.ejb.Stateless;

import com.gmb.modelo.seguridad.GmbUsuarios;
import com.gmb.seguridad.eao.GmbUsuarioEAO;
import com.gmb.utils.UtilCryptography;

@Stateless
public class GmbUsuarioServicio implements IUsuarioServicio  {
	@EJB
	private  GmbUsuarioEAO usuarioEAO;

	
	public List<GmbUsuarios> getUsuarios()
	{
		return usuarioEAO.consultarUsuarios();
	}
	
	public boolean autenticarUsuario(String usuario, String clave) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		String claveEncriptada=UtilCryptography.encriptar(clave);
		Boolean bandera=usuarioEAO.autenticarUsuario(usuario, claveEncriptada);
	   return bandera;
	} 
	public void insertarUsuario(GmbUsuarios usuario) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		usuario.setClave(UtilCryptography.encriptar(usuario.getClave()));
		usuarioEAO.insertarUsuario(usuario);
		
	}
	public void modificarUsuario(GmbUsuarios objeto)
	{
		usuarioEAO.actualizarUsuario(objeto);
	}
	public void modificarUsuarioClave(GmbUsuarios objeto) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		 objeto.setClave(UtilCryptography.encriptar(objeto.getClave()));
		usuarioEAO.actualizarUsuario(objeto);
	}
	
	public String getClavexUsuario(String usuario) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		String claveEncriptada=usuarioEAO.getClavexUsuario(usuario);
		return UtilCryptography.desencriptar(claveEncriptada);
	}
	
	public GmbUsuarios getUsuarioxUsuarioClave(String usuario,String clave) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{ 
		String claveEncriptada=UtilCryptography.encriptar(clave);
		return usuarioEAO.getUsuario(usuario, claveEncriptada);
	}
	
	public static void main(String[] args) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		GmbUsuarioEAO u=new GmbUsuarioEAO();
		
		String clave=u.getClavexUsuario("etorres");
		
		System.out.println(clave);
	}
}
