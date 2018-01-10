package com.asinfo.as2.webservices.datosbase;

import com.asinfo.pos.model.Sucursal;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebSucursal
{
  @WebMethod
  public abstract List<Sucursal> getListaSucursalPos();
  
  @WebMethod
  public abstract List<Sucursal> getListaSucursalPorOrganizacionPos(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.ServicioWebSucursal
 * JD-Core Version:    0.7.0.1
 */