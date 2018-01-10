package com.asinfo.as2.webservices.datosbase;

import com.asinfo.pos.model.TipoIdentificacion;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebTipoIdentificacion
{
  @WebMethod
  public abstract List<TipoIdentificacion> getListaTipoIdentificacion(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.ServicioWebTipoIdentificacion
 * JD-Core Version:    0.7.0.1
 */