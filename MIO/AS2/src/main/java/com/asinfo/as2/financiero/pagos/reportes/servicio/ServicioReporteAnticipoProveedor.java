package com.asinfo.as2.financiero.pagos.reportes.servicio;

import com.asinfo.as2.entities.CategoriaEmpresa;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteAnticipoProveedor
{
  public abstract List getReporteAnticipo(int paramInt);
  
  public abstract List getReporteCorteFechaAnticipoProveedores(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, int paramInt2, CategoriaEmpresa paramCategoriaEmpresa, int paramInt3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReporteAnticipoProveedor
 * JD-Core Version:    0.7.0.1
 */