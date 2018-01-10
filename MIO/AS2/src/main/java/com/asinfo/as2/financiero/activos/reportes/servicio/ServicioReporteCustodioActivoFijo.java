package com.asinfo.as2.financiero.activos.reportes.servicio;

import com.asinfo.as2.entities.Empresa;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteCustodioActivoFijo
{
  public abstract List getReporteCustodioActivoFijo(int paramInt1, Date paramDate1, Date paramDate2, Empresa paramEmpresa1, Empresa paramEmpresa2, int paramInt2);
  
  public abstract List getReporteCustodioActivo(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteCustodioActivoFijo
 * JD-Core Version:    0.7.0.1
 */