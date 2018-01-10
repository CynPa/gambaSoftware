package com.asinfo.as2.ventas.procesos.aerolineas.servicio;

import com.asinfo.as2.entities.GuiaAerea;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioGuiaAerea
{
  public abstract List<GuiaAerea> obtenerGuiasAereasPorAgentCode(String paramString1, int paramInt, Date paramDate1, Date paramDate2, String paramString2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.servicio.ServicioGuiaAerea
 * JD-Core Version:    0.7.0.1
 */