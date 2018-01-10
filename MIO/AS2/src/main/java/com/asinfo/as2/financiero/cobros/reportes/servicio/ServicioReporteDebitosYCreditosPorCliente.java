package com.asinfo.as2.financiero.cobros.reportes.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Sucursal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteDebitosYCreditosPorCliente
{
  public abstract List<Object[]> getReporteDebitosYCreditosPorCliente(int paramInt1, Sucursal paramSucursal, Empresa paramEmpresa, Date paramDate1, Date paramDate2, int paramInt2, boolean paramBoolean, int paramInt3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteDebitosYCreditosPorCliente
 * JD-Core Version:    0.7.0.1
 */