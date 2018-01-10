package com.asinfo.as2.financiero.cobros.reportes.servicio;

import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteAnticipoCliente
{
  public abstract List getReporteAnticipo(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteAnticipoCliente
 * JD-Core Version:    0.7.0.1
 */