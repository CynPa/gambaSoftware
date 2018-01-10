package com.asinfo.as2.webservices.procesos;

import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.ws.datosbase.ClienteWSEntity;
import com.asinfo.as2.ws.respuesta.RespuestaWSEntity;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebCliente
{
  @WebMethod
  @WebResult(name="listaRespuesta")
  public abstract RespuestaWSEntity[] crearCliente(@WebParam(name="organizacion") int paramInt, @WebParam(name="listaCliente") ClienteWSEntity[] paramArrayOfClienteWSEntity);
  
  @WebMethod
  @WebResult(name="cliente")
  public abstract ClienteWSEntity consultarCliente(@WebParam(name="organizacion") int paramInt, @WebParam(name="identificacion") String paramString)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.procesos.ServicioWebCliente
 * JD-Core Version:    0.7.0.1
 */