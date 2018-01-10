package com.asinfo.as2.webservices.seguridad;

import com.asinfo.pos.model.Usuario;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebUsuario
{
  @WebMethod
  public abstract List<Usuario> getListaUsuarioPos();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.seguridad.ServicioWebUsuario
 * JD-Core Version:    0.7.0.1
 */