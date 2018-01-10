package com.asinfo.as2.webservices.procesos;

import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.ws.respuesta.RespuestaWSEntity;
import com.asinfo.as2.ws.ventas.model.FacturaClienteWSEntity;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebFacturaCliente
{
  @WebMethod
  @WebResult(name="listaRespuesta")
  public abstract RespuestaWSEntity[] crearFacturaCliente(@WebParam(name="organizacion") int paramInt, @WebParam(name="listaFacturaCliente") FacturaClienteWSEntity[] paramArrayOfFacturaClienteWSEntity);
  
  @WebMethod
  @WebResult(name="facturaCliente")
  public abstract FacturaClienteWSEntity consultarFacturaCliente(@WebParam(name="organizacion") int paramInt, @WebParam(name="tipoDocumento") String paramString1, @WebParam(name="establecimiento") String paramString2, @WebParam(name="puntoDeVenta") String paramString3, @WebParam(name="numero") String paramString4)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.procesos.ServicioWebFacturaCliente
 * JD-Core Version:    0.7.0.1
 */