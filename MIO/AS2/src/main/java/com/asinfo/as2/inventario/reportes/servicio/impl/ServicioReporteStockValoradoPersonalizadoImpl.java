/*   1:    */ package com.asinfo.as2.inventario.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteSaldoProducto;
/*   4:    */ import com.asinfo.as2.clases.ReporteStockValoradoResumido;
/*   5:    */ import com.asinfo.as2.dao.reportes.inventario.ReporteStockValoradoPersonalizadoDao;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Lote;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValoradoPersonalizado;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.TreeMap;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class ServicioReporteStockValoradoPersonalizadoImpl
/*  23:    */   implements ServicioReporteStockValoradoPersonalizado
/*  24:    */ {
/*  25:    */   @EJB
/*  26:    */   private ReporteStockValoradoPersonalizadoDao reporteStockValoradoPersonalizadoDao;
/*  27:    */   
/*  28:    */   public List<ReporteStockValoradoResumido> getReporteStockValoradoResumido(Sucursal sucursal, Bodega bodega, Date fechaDesde, Date fechaHasta, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, boolean ordenProducto)
/*  29:    */   {
/*  30: 48 */     return this.reporteStockValoradoPersonalizadoDao.getReporteStockValoradoResumido2(sucursal, bodega, fechaDesde, fechaHasta, atributo, valorAtributo, idOrganizacion, categoriaProducto, subcategoriaProducto, ordenProducto);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo1, String valorAtributo1, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/*  34:    */   {
/*  35: 61 */     return this.reporteStockValoradoPersonalizadoDao.getReporteSaldoProducto(sucursal, bodega, fechaDesde, atributo1, valorAtributo1, idOrganizacion, categoriaProducto, subcategoriaProducto);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo1, String valorAtributo1, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo2, String valorAtributo2)
/*  39:    */   {
/*  40: 75 */     TreeMap<String, ReporteSaldoProducto> hmReporteSaldoProducto = new TreeMap();
/*  41: 76 */     List<ReporteSaldoProducto> lista = this.reporteStockValoradoPersonalizadoDao.getReporteSaldoProducto(sucursal, bodega, fechaDesde, atributo1, valorAtributo1, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo2, valorAtributo2);
/*  42: 79 */     for (ReporteSaldoProducto rSaldoProducto : lista)
/*  43:    */     {
/*  44: 81 */       String clave = rSaldoProducto.getIdBodega() + ":" + rSaldoProducto.getNombreProducto() + ":" + rSaldoProducto.getIdProducto();
/*  45:    */       
/*  46: 83 */       ReporteSaldoProducto reporteSaldoProducto = (ReporteSaldoProducto)hmReporteSaldoProducto.get(clave);
/*  47: 84 */       if (reporteSaldoProducto == null)
/*  48:    */       {
/*  49: 85 */         hmReporteSaldoProducto.put(clave, rSaldoProducto);
/*  50:    */       }
/*  51:    */       else
/*  52:    */       {
/*  53: 87 */         if (!rSaldoProducto.getValorAtributo1().isEmpty()) {
/*  54: 88 */           reporteSaldoProducto.setValorAtributo1(rSaldoProducto.getValorAtributo1());
/*  55:    */         }
/*  56: 90 */         if (!rSaldoProducto.getValorAtributo2().isEmpty()) {
/*  57: 91 */           reporteSaldoProducto.setValorAtributo2(rSaldoProducto.getValorAtributo2());
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61: 95 */     return new ArrayList(hmReporteSaldoProducto.values());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<ReporteSaldoProducto> getReporteSaldoProductoPorLote(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, boolean indicadorLote, Lote lote, Producto producto)
/*  65:    */   {
/*  66:110 */     return this.reporteStockValoradoPersonalizadoDao.getReporteSaldoProductoPorLote(sucursal, bodega, fechaDesde, atributo, valorAtributo, idOrganizacion, categoriaProducto, subcategoriaProducto, indicadorLote, lote, producto);
/*  67:    */   }
/*  68:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.impl.ServicioReporteStockValoradoPersonalizadoImpl
 * JD-Core Version:    0.7.0.1
 */