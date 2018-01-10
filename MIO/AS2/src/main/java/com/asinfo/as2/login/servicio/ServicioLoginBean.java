package com.asinfo.as2.login.servicio;

import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import javax.ejb.Local;

@Local
public abstract interface ServicioLoginBean
{
  public abstract EntidadUsuario getUsuario(String paramString1, String paramString2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.login.servicio.ServicioLoginBean
 * JD-Core Version:    0.7.0.1
 */