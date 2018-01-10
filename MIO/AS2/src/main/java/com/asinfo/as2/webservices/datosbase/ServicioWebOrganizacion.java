package com.asinfo.as2.webservices.datosbase;

import com.asinfo.as2.ws.datosbase.OrganizacionWS;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebOrganizacion
{
  @WebMethod
  public abstract List<OrganizacionWS> getListaOrganizacionPos();
  
  @WebMethod
  public abstract OrganizacionWS buscarPorId(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.ServicioWebOrganizacion
 * JD-Core Version:    0.7.0.1
 */