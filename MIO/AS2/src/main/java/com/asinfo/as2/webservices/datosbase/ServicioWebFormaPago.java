package com.asinfo.as2.webservices.datosbase;

import com.asinfo.pos.model.FormaPago;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebFormaPago
{
  @WebMethod
  public abstract List<FormaPago> getListaFormaPago(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.ServicioWebFormaPago
 * JD-Core Version:    0.7.0.1
 */