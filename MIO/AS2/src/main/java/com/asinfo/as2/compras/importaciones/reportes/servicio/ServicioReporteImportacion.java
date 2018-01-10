package com.asinfo.as2.compras.importaciones.reportes.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Pais;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteImportacion
{
  public abstract List getReporteFacturasProveedorImportacionPorLiquidar(Date paramDate1, Date paramDate2, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getReporteFacturaProveedorImportacionGasto(Date paramDate1, Date paramDate2, Empresa paramEmpresa, Pais paramPais);
  
  public abstract List getReporteFacturaProveedorImportacionGastoDetallado(Date paramDate1, Date paramDate2, Empresa paramEmpresa);
  
  public abstract List getReporteLiquidacionFacturaImportacion(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3)
    throws ExcepcionAS2Compras;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.reportes.servicio.ServicioReporteImportacion
 * JD-Core Version:    0.7.0.1
 */