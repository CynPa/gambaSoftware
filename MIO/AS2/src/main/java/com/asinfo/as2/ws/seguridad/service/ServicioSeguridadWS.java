package com.asinfo.as2.ws.seguridad.service;

import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.ws.seguridad.model.UsuarioWSEntity;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public abstract interface ServicioSeguridadWS
{
  public abstract UsuarioWSEntity login(@WebParam(name="nombreUsuario") String paramString1, @WebParam(name="clave") String paramString2, @WebParam(name="sistema") String paramString3)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.seguridad.service.ServicioSeguridadWS
 * JD-Core Version:    0.7.0.1
 */