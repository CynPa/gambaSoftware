package com.asinfo.as2.financiero.activos.reportes.servicio;

import com.asinfo.as2.entities.ActivoFijo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteDepreciacion
{
  public abstract List getReporteDepreciacionMensualNIIF(ActivoFijo paramActivoFijo, Date paramDate1, Date paramDate2, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract List getReporteDepreciacionFiscalVsNIIF(ActivoFijo paramActivoFijo, Date paramDate1, Date paramDate2, boolean paramBoolean, String paramString1, String paramString2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteDepreciacion
 * JD-Core Version:    0.7.0.1
 */