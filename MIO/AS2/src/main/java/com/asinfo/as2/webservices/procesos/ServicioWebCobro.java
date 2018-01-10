package com.asinfo.as2.webservices.procesos;

import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.ws.respuesta.RespuestaWSEntity;
import com.asinfo.as2.ws.ventas.model.CobroWSEntity;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebCobro
{
  @WebMethod
  @WebResult(name="listaRespuesta")
  public abstract RespuestaWSEntity[] crearCobro(@WebParam(name="organizacion") int paramInt, @WebParam(name="listaCobro") CobroWSEntity[] paramArrayOfCobroWSEntity);
  
  @WebMethod
  @WebResult(name="cobro")
  public abstract CobroWSEntity consultarCobro(@WebParam(name="organizacion") int paramInt, @WebParam(name="numero") String paramString)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.procesos.ServicioWebCobro
 * JD-Core Version:    0.7.0.1
 */