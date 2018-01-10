package com.asinfo.as2.webservices.datosbase;

import com.asinfo.pos.model.Caja;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebCaja
{
  @WebMethod
  public abstract List<Caja> getListaCajaPos();
  
  @WebMethod
  public abstract List<Caja> getListaCajaConPuntoDeVentaPos(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.ServicioWebCaja
 * JD-Core Version:    0.7.0.1
 */