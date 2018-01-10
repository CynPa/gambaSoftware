package com.asinfo.as2.ws.compras.service;

import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.ws.compras.model.FacturaProveedorWSEntity;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioFacturaProveedorWS
{
  @WebMethod
  public abstract FacturaProveedorWSEntity guardarCompraComercializadora(FacturaProveedorWSEntity paramFacturaProveedorWSEntity)
    throws AS2Exception;
  
  @WebMethod
  public abstract boolean anularFactura(Long paramLong)
    throws AS2Exception;
  
  @WebMethod
  public abstract boolean isEditable(Long paramLong)
    throws AS2Exception;
  
  @WebMethod
  public abstract boolean bloquearFactura(int paramInt, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.compras.service.ServicioFacturaProveedorWS
 * JD-Core Version:    0.7.0.1
 */