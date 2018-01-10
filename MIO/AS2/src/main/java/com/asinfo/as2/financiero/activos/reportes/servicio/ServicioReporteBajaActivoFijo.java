package com.asinfo.as2.financiero.activos.reportes.servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteBajaActivoFijo
{
  public abstract List getReporteBajaActivoFijo(int paramInt, boolean paramBoolean, Date paramDate1, Date paramDate2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteBajaActivoFijo
 * JD-Core Version:    0.7.0.1
 */