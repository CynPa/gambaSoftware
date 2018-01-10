package com.asinfo.as2.financiero.cobros.reportes.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
import com.asinfo.as2.entities.Sucursal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteNotaCreditoCliente
{
  public abstract List<Object[]> getListaNotaCreditoCliente(Date paramDate1, Date paramDate2, Empresa paramEmpresa, MotivoNotaCreditoCliente paramMotivoNotaCreditoCliente, int paramInt, Sucursal paramSucursal);
  
  public abstract List getReporteNotasCredito(Date paramDate1, Date paramDate2, Empresa paramEmpresa, MotivoNotaCreditoCliente paramMotivoNotaCreditoCliente, int paramInt, Sucursal paramSucursal);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteNotaCreditoCliente
 * JD-Core Version:    0.7.0.1
 */