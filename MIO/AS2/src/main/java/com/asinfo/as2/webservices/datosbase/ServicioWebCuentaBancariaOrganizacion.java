package com.asinfo.as2.webservices.datosbase;

import com.asinfo.pos.model.CuentaBancariaOrganizacion;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebCuentaBancariaOrganizacion
{
  @WebMethod
  public abstract List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacionPos(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.ServicioWebCuentaBancariaOrganizacion
 * JD-Core Version:    0.7.0.1
 */