/*   1:    */ package com.asinfo.as2.ventas.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.reportes.ventas.ReportePedidoDespachoFacturaDao;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.MotivoPedidoCliente;
/*   6:    */ import com.asinfo.as2.entities.PedidoCliente;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.Transportista;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReportePedidoDespachoFactura;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioReportePedidoDespachoFacturaImpl
/*  18:    */   implements ServicioReportePedidoDespachoFactura
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private transient ReportePedidoDespachoFacturaDao reportePedidoDespachoFacturaDao;
/*  22:    */   
/*  23:    */   public List getReportePedidoDespachoFactura(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente, int idOrganizacion, Producto producto)
/*  24:    */     throws ExcepcionAS2
/*  25:    */   {
/*  26: 52 */     return this.reportePedidoDespachoFacturaDao.getReportePedidoDespachoFactura(pedidoCliente, fechaDesde, fechaHasta, cliente, idOrganizacion, producto);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List getReporteDespachoSinFactura(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente, int idOrganizacion, Producto producto)
/*  30:    */     throws ExcepcionAS2
/*  31:    */   {
/*  32: 67 */     return this.reportePedidoDespachoFacturaDao.getReporteDespachoSinFactura(pedidoCliente, fechaDesde, fechaHasta, cliente, idOrganizacion, producto);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List getReportePedidoConFactura(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente, int idOrganizacion, Producto producto)
/*  36:    */     throws ExcepcionAS2
/*  37:    */   {
/*  38: 81 */     return this.reportePedidoDespachoFacturaDao.getReportePedidoConFactura(pedidoCliente, fechaDesde, fechaHasta, cliente, idOrganizacion, producto);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List getReporteDespachoFactura(Date fechaDesde, Date fechaHasta, Empresa cliente)
/*  42:    */     throws ExcepcionAS2
/*  43:    */   {
/*  44:101 */     return this.reportePedidoDespachoFacturaDao.getReporteDespachoFactura(fechaDesde, fechaHasta, cliente);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List getReporteFacturaDespacho(Date fechaDesde, Date fechaHasta, Empresa cliente)
/*  48:    */     throws ExcepcionAS2
/*  49:    */   {
/*  50:113 */     return this.reportePedidoDespachoFacturaDao.getReporteFacturaDespacho(fechaDesde, fechaHasta, cliente);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List getReportePedidoResumido(int idOrganizacion, Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, int idSucursal, MotivoPedidoCliente motivoPedidoCliente, Transportista transportista, boolean indicadorTomaFecha)
/*  54:    */   {
/*  55:128 */     return this.reportePedidoDespachoFacturaDao.getReportePedidoResumido(idOrganizacion, fechaDesde, fechaHasta, numeroDesde, numeroHasta, idCliente, idVendedor, anuladas, idCanal, idZona, idSucursal, motivoPedidoCliente, transportista, indicadorTomaFecha);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List getReporteListaPedidos(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente)
/*  59:    */     throws ExcepcionAS2
/*  60:    */   {
/*  61:141 */     return this.reportePedidoDespachoFacturaDao.getReporteListaPedidos(pedidoCliente, fechaDesde, fechaHasta, cliente);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List getReportePedidos(Date fechaDesde, Date fechaHasta, int idCliente, Producto producto, boolean indicadorElaborado)
/*  65:    */   {
/*  66:154 */     return this.reportePedidoDespachoFacturaDao.getReportePedidos(fechaDesde, fechaHasta, idCliente, producto, indicadorElaborado);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List getReportePedidosResumido(Date fechaDesde, Date fechaHasta, Producto producto, boolean indicadorElaborado)
/*  70:    */   {
/*  71:162 */     return this.reportePedidoDespachoFacturaDao.getReportePedidosResumido(fechaDesde, fechaHasta, producto, indicadorElaborado);
/*  72:    */   }
/*  73:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.impl.ServicioReportePedidoDespachoFacturaImpl
 * JD-Core Version:    0.7.0.1
 */