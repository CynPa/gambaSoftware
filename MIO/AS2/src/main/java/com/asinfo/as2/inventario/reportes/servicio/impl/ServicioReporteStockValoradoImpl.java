/*   1:    */ package com.asinfo.as2.inventario.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteSaldoProducto;
/*   4:    */ import com.asinfo.as2.clases.ReporteStockValoradoResumido;
/*   5:    */ import com.asinfo.as2.dao.reportes.inventario.ReporteStockValoradoDao;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Lote;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  14:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValorado;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.TreeMap;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ServicioReporteStockValoradoImpl
/*  24:    */   implements ServicioReporteStockValorado
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private ReporteStockValoradoDao reporteStockValoradoDao;
/*  28:    */   
/*  29:    */   public List<ReporteStockValoradoResumido> getReporteStockValoradoResumido(Sucursal sucursal, Bodega bodega, Date fechaDesde, Date fechaHasta, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/*  30:    */   {
/*  31: 49 */     return this.reporteStockValoradoDao.getReporteStockValoradoResumido2(sucursal, bodega, fechaDesde, fechaHasta, atributo, valorAtributo, idOrganizacion, categoriaProducto, subcategoriaProducto);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo1, String valorAtributo1, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/*  35:    */   {
/*  36: 62 */     return this.reporteStockValoradoDao.getReporteSaldoProducto(sucursal, bodega, fechaDesde, atributo1, valorAtributo1, idOrganizacion, categoriaProducto, subcategoriaProducto, null);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo1, String valorAtributo1, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo2, String valorAtributo2, Boolean indicadorSaldoCero)
/*  40:    */   {
/*  41: 76 */     TreeMap<String, ReporteSaldoProducto> hmReporteSaldoProducto = new TreeMap();
/*  42: 77 */     List<ReporteSaldoProducto> lista = this.reporteStockValoradoDao.getReporteSaldoProducto(sucursal, bodega, fechaDesde, atributo1, valorAtributo1, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo2, valorAtributo2, indicadorSaldoCero);
/*  43: 80 */     for (ReporteSaldoProducto rSaldoProducto : lista)
/*  44:    */     {
/*  45: 82 */       String clave = rSaldoProducto.getIdBodega() + ":" + rSaldoProducto.getNombreProducto() + ":" + rSaldoProducto.getIdProducto();
/*  46:    */       
/*  47: 84 */       ReporteSaldoProducto reporteSaldoProducto = (ReporteSaldoProducto)hmReporteSaldoProducto.get(clave);
/*  48: 85 */       if (reporteSaldoProducto == null)
/*  49:    */       {
/*  50: 86 */         hmReporteSaldoProducto.put(clave, rSaldoProducto);
/*  51:    */       }
/*  52:    */       else
/*  53:    */       {
/*  54: 88 */         if (!rSaldoProducto.getValorAtributo1().isEmpty()) {
/*  55: 89 */           reporteSaldoProducto.setValorAtributo1(rSaldoProducto.getValorAtributo1());
/*  56:    */         }
/*  57: 91 */         if (!rSaldoProducto.getValorAtributo2().isEmpty()) {
/*  58: 92 */           reporteSaldoProducto.setValorAtributo2(rSaldoProducto.getValorAtributo2());
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62: 96 */     return new ArrayList(hmReporteSaldoProducto.values());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<ReporteSaldoProducto> getReporteSaldoProductoPorLote(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, boolean indicadorLote, Lote lote, Producto producto, List<ValorAtributo> listValoresAtributos, int numeroAtributosOrganizacion)
/*  66:    */   {
/*  67:111 */     return this.reporteStockValoradoDao.getReporteSaldoProductoPorLote(sucursal, bodega, fechaDesde, atributo, valorAtributo, idOrganizacion, categoriaProducto, subcategoriaProducto, indicadorLote, lote, producto, listValoresAtributos, numeroAtributosOrganizacion);
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.impl.ServicioReporteStockValoradoImpl
 * JD-Core Version:    0.7.0.1
 */