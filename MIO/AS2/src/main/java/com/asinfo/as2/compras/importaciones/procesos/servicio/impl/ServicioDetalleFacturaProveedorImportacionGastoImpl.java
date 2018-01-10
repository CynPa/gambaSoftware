/*   1:    */ package com.asinfo.as2.compras.importaciones.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioDetalleFacturaProveedorImportacionGasto;
/*   5:    */ import com.asinfo.as2.dao.DetalleFacturaProveedorImportacionGastoDao;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
/*   7:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioDetalleFacturaProveedorImportacionGastoImpl
/*  15:    */   implements ServicioDetalleFacturaProveedorImportacionGasto
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private DetalleFacturaProveedorImportacionGastoDao detalleFacturaProveedorImportacionGastoDao;
/*  19:    */   
/*  20:    */   public void guardar(DetalleFacturaProveedorImportacionGasto detalleFacturaProveedorImportacionGasto)
/*  21:    */   {
/*  22: 45 */     this.detalleFacturaProveedorImportacionGastoDao.guardar(detalleFacturaProveedorImportacionGasto);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(DetalleFacturaProveedorImportacionGasto detalleFacturaProveedorImportacionGasto)
/*  26:    */   {
/*  27: 57 */     this.detalleFacturaProveedorImportacionGastoDao.eliminar(detalleFacturaProveedorImportacionGasto);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public DetalleFacturaProveedorImportacionGasto buscarPorId(int idDetalleFacturaProveedorImportacionGasto)
/*  31:    */   {
/*  32: 68 */     return (DetalleFacturaProveedorImportacionGasto)this.detalleFacturaProveedorImportacionGastoDao.buscarPorId(Integer.valueOf(idDetalleFacturaProveedorImportacionGasto));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<DetalleFacturaProveedorImportacionGasto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 80 */     return this.detalleFacturaProveedorImportacionGastoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<DetalleFacturaProveedorImportacionGasto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 91 */     return this.detalleFacturaProveedorImportacionGastoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47:101 */     return this.detalleFacturaProveedorImportacionGastoDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public DetalleFacturaProveedorImportacionGasto cargarDetalle(int idDetalleFacturaProveedorImportacionGasto)
/*  51:    */   {
/*  52:111 */     return this.detalleFacturaProveedorImportacionGastoDao.cargarDetalle(idDetalleFacturaProveedorImportacionGasto);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<DetalleFacturaProveedorImportacionGasto> obtenerListaPresupuestoImportacion(FacturaProveedor facturaProveedor)
/*  56:    */     throws ExcepcionAS2Compras
/*  57:    */   {
/*  58:123 */     return this.detalleFacturaProveedorImportacionGastoDao.obtenerListaPresupuestoImportacion(facturaProveedor);
/*  59:    */   }
/*  60:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.procesos.servicio.impl.ServicioDetalleFacturaProveedorImportacionGastoImpl
 * JD-Core Version:    0.7.0.1
 */