package com.asinfo.as2.ventas.reportes.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.MotivoPedidoCliente;
import com.asinfo.as2.entities.PedidoCliente;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReportePedidoDespachoFactura
{
  public abstract List getReportePedidoDespachoFactura(PedidoCliente paramPedidoCliente, Date paramDate1, Date paramDate2, Empresa paramEmpresa, int paramInt, Producto paramProducto)
    throws ExcepcionAS2;
  
  public abstract List getReporteDespachoSinFactura(PedidoCliente paramPedidoCliente, Date paramDate1, Date paramDate2, Empresa paramEmpresa, int paramInt, Producto paramProducto)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidoConFactura(PedidoCliente paramPedidoCliente, Date paramDate1, Date paramDate2, Empresa paramEmpresa, int paramInt, Producto paramProducto)
    throws ExcepcionAS2;
  
  public abstract List getReporteDespachoFactura(Date paramDate1, Date paramDate2, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getReporteFacturaDespacho(Date paramDate1, Date paramDate2, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidoResumido(int paramInt1, Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt2, int paramInt3, boolean paramBoolean1, int paramInt4, int paramInt5, int paramInt6, MotivoPedidoCliente paramMotivoPedidoCliente, Transportista paramTransportista, boolean paramBoolean2);
  
  public abstract List getReporteListaPedidos(PedidoCliente paramPedidoCliente, Date paramDate1, Date paramDate2, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidos(Date paramDate1, Date paramDate2, int paramInt, Producto paramProducto, boolean paramBoolean);
  
  public abstract List getReportePedidosResumido(Date paramDate1, Date paramDate2, Producto paramProducto, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.ServicioReportePedidoDespachoFactura
 * JD-Core Version:    0.7.0.1
 */