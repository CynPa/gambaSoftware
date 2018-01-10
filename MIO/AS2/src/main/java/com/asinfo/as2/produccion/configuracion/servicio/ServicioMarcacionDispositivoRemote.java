package com.asinfo.as2.produccion.configuracion.servicio;

import java.math.BigDecimal;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioMarcacionDispositivoRemote
{
  public abstract void nuevaMarcacion(String paramString1, String paramString2, BigDecimal paramBigDecimal);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivoRemote
 * JD-Core Version:    0.7.0.1
 */