package com.asinfo.as2.ws.compras.service;

import com.asinfo.as2.excepciones.AS2Exception;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public abstract interface ServicioPedidoProveedorWS
{
  @WebMethod
  public abstract void aprobarPedidoPorProducto(@WebParam(name="idOrganizacion") Integer paramInteger, @WebParam(name="numero") String paramString1, @WebParam(name="nombreDocumento") String paramString2, @WebParam(name="codigoProducto") String paramString3)
    throws AS2Exception;
  
  @WebMethod
  public abstract void aprobarPedido(@WebParam(name="idOrganizacion") Integer paramInteger, @WebParam(name="numero") String paramString1, @WebParam(name="nombreDocumento") String paramString2)
    throws AS2Exception;
  
  @WebMethod
  public abstract void cerrarPedido(@WebParam(name="idOrganizacion") Integer paramInteger, @WebParam(name="numero") String paramString1, @WebParam(name="nombreDocumento") String paramString2)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.compras.service.ServicioPedidoProveedorWS
 * JD-Core Version:    0.7.0.1
 */