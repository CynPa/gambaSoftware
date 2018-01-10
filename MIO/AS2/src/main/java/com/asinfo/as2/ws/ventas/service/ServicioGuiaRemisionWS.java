package com.asinfo.as2.ws.ventas.service;

import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.ws.ventas.model.GuiaRemisionWSEntity;
import java.util.ArrayList;
import javax.jws.WebService;

@WebService
public abstract interface ServicioGuiaRemisionWS
{
  public abstract GuiaRemisionWSEntity guardarGuiaRemision(GuiaRemisionWSEntity paramGuiaRemisionWSEntity)
    throws AS2Exception;
  
  public abstract boolean liberarGuiaRemisionAutomatica(Long paramLong)
    throws AS2Exception;
  
  public abstract ArrayList<Object[]> getDatosReporteGuiaRemision(int paramInt);
  
  public abstract String[] getFieldsReporteGuiaRemision();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.service.ServicioGuiaRemisionWS
 * JD-Core Version:    0.7.0.1
 */