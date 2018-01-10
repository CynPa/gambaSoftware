package com.asinfo.as2.caja.reportes.servicio;

import com.asinfo.as2.entities.CierreCaja;
import com.asinfo.as2.enumeraciones.Estado;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteCaja
{
  public abstract List obtenerReporteCaja(Date paramDate1, Date paramDate2, int paramInt, Estado paramEstado);
  
  public abstract List getReporteCierreCaja(int paramInt);
  
  public abstract List<Object[]> getListaCierreCaja(CierreCaja paramCierreCaja);
  
  public abstract BigDecimal totalChquesPosfechados(CierreCaja paramCierreCaja);
  
  public abstract Integer numeroChequesPosfechados(CierreCaja paramCierreCaja);
  
  public abstract List<Object[]> getReporteCierreCajaPorDenominacionFormaCobro(int paramInt);
  
  public abstract List<Object[]> getReporteCierreCajaComparativo(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.reportes.servicio.ServicioReporteCaja
 * JD-Core Version:    0.7.0.1
 */