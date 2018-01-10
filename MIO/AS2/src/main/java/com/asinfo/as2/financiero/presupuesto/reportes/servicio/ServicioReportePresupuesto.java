package com.asinfo.as2.financiero.presupuesto.reportes.servicio;

import com.asinfo.as2.entities.CentroCosto;
import com.asinfo.as2.entities.CuentaContable;
import com.asinfo.as2.entities.DetalleAsiento;
import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.Ejercicio;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
import com.asinfo.as2.enumeraciones.Mes;
import com.asinfo.as2.enumeraciones.TipoCalculo;
import com.asinfo.as2.enumeraciones.ValoresCalculo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReportePresupuesto
{
  public abstract List<Object[]> getReportePresupuesto(Ejercicio paramEjercicio, Mes paramMes, Sucursal paramSucursal, boolean paramBoolean1, boolean paramBoolean2, List<CuentaContable> paramList, int paramInt, DimensionContable paramDimensionContable, String paramString);
  
  public abstract List<DetalleAsiento> getMayorPartidaPresupuestaria(PartidaPresupuestaria paramPartidaPresupuestaria, Date paramDate1, Date paramDate2, int paramInt, boolean paramBoolean, CentroCosto paramCentroCosto);
  
  public abstract BigDecimal obtenerSaldo(Date paramDate1, Date paramDate2, ValoresCalculo paramValoresCalculo, TipoCalculo paramTipoCalculo, boolean paramBoolean, int paramInt, PartidaPresupuestaria paramPartidaPresupuestaria, CentroCosto paramCentroCosto);
  
  public abstract List getReporteMayorPartidaPresupuestaria(PartidaPresupuestaria paramPartidaPresupuestaria, Date paramDate1, Date paramDate2, int paramInt, boolean paramBoolean, CentroCosto paramCentroCosto);
  
  public abstract List<Object[]> getReportePresupuesto(Ejercicio paramEjercicio, Mes paramMes, int paramInt1, DimensionContable paramDimensionContable, List<DimensionContable> paramList, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract List<Object[]> getReportePresupuestoComparativo(Ejercicio paramEjercicio, Mes paramMes, Sucursal paramSucursal, int paramInt1, String paramString, DimensionContable paramDimensionContable, List<DimensionContable> paramList, int paramInt2, int paramInt3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.reportes.servicio.ServicioReportePresupuesto
 * JD-Core Version:    0.7.0.1
 */