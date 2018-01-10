/*   1:    */ package com.asinfo.as2.compras.importaciones.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.importaciones.reportes.servicio.ServicioReporteImportacion;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   6:    */ import com.asinfo.as2.dao.reportes.compras.importaciones.ReporteImportacionDao;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Pais;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioReporteImportacionImpl
/*  17:    */   implements ServicioReporteImportacion
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private transient ReporteImportacionDao reporteImportacionDao;
/*  21:    */   @EJB
/*  22:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  23:    */   
/*  24:    */   public List getReporteFacturasProveedorImportacionPorLiquidar(Date fechaDesde, Date fechaHasta, Empresa proveedor)
/*  25:    */     throws ExcepcionAS2
/*  26:    */   {
/*  27: 52 */     List<Object[]> lista = this.reporteImportacionDao.getReporteFacturasProveedorImportacionPorLiquidar(fechaDesde, fechaHasta, proveedor);
/*  28:    */     
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32: 57 */     return lista;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List getReporteFacturaProveedorImportacionGasto(Date fechaDesde, Date fechaHasta, Empresa proveedor, Pais paisOrigen)
/*  36:    */   {
/*  37: 72 */     return this.reporteImportacionDao.getReporteFacturaProveedorImportacionGasto(fechaDesde, fechaHasta, proveedor, paisOrigen);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List getReporteFacturaProveedorImportacionGastoDetallado(Date fechaDesde, Date fechaHasta, Empresa proveedor)
/*  41:    */   {
/*  42: 86 */     return this.reporteImportacionDao.getReporteFacturaProveedorImportacionGastoDetallado(fechaDesde, fechaHasta, proveedor);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List getReporteLiquidacionFacturaImportacion(Date fechaDesde, Date fechaHasta, int idEmpresa, int idPais, int idFacturaProveedor)
/*  46:    */     throws ExcepcionAS2Compras
/*  47:    */   {
/*  48:101 */     List<Object[]> listaDatos = this.reporteImportacionDao.getReporteLiquidacionFacturaImportacion(fechaDesde, fechaHasta, idEmpresa, idPais, idFacturaProveedor);
/*  49:102 */     for (Object[] dato : listaDatos)
/*  50:    */     {
/*  51:103 */       int idFacturaProveedorDato = ((Integer)dato[39]).intValue();
/*  52:104 */       dato[39] = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(idFacturaProveedorDato));
/*  53:    */     }
/*  54:106 */     return listaDatos;
/*  55:    */   }
/*  56:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.reportes.servicio.impl.ServicioReporteImportacionImpl
 * JD-Core Version:    0.7.0.1
 */