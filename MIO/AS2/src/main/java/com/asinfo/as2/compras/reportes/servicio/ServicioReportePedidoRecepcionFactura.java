package com.asinfo.as2.compras.reportes.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.PedidoProveedor;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReportePedidoRecepcionFactura
{
  public abstract List getReportePedidoRecepcionFactura(PedidoProveedor paramPedidoProveedor, Date paramDate1, Date paramDate2, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getReporteRecepcionSinFactura(PedidoProveedor paramPedidoProveedor, Date paramDate1, Date paramDate2, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidoConFactura(PedidoProveedor paramPedidoProveedor, Date paramDate1, Date paramDate2, Empresa paramEmpresa)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoRecepcionFactura
 * JD-Core Version:    0.7.0.1
 */