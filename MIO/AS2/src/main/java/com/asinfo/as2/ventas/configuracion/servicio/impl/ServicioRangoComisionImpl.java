/*   1:    */ package com.asinfo.as2.ventas.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.RangoComisionCategoriaProductoDao;
/*   4:    */ import com.asinfo.as2.dao.RangoComisionDao;
/*   5:    */ import com.asinfo.as2.entities.RangoComision;
/*   6:    */ import com.asinfo.as2.entities.RangoComisionCategoriaProducto;
/*   7:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRangoComision;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioRangoComisionImpl
/*  15:    */   implements ServicioRangoComision
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private RangoComisionDao rangoComisionDao;
/*  19:    */   @EJB
/*  20:    */   private RangoComisionCategoriaProductoDao rangoComisionCategoriaProductoDao;
/*  21:    */   
/*  22:    */   public void guardar(RangoComision rangoComision)
/*  23:    */   {
/*  24: 45 */     for (RangoComisionCategoriaProducto rccp : rangoComision.getListaRangoComisionCategoriaProducto()) {
/*  25: 46 */       this.rangoComisionCategoriaProductoDao.guardar(rccp);
/*  26:    */     }
/*  27: 48 */     this.rangoComisionDao.guardar(rangoComision);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(RangoComision rangoComision)
/*  31:    */   {
/*  32: 57 */     this.rangoComisionDao.eliminar(rangoComision);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public RangoComision buscarPorId(int idRangoComision)
/*  36:    */   {
/*  37: 66 */     return (RangoComision)this.rangoComisionDao.buscarPorId(Integer.valueOf(idRangoComision));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<RangoComision> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 76 */     return this.rangoComisionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<RangoComision> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 85 */     return this.rangoComisionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52: 93 */     return this.rangoComisionDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public RangoComision cargarDetalle(int idRangoComision)
/*  56:    */   {
/*  57:102 */     return this.rangoComisionDao.cargarDetalle(idRangoComision);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<RangoComision> getListaOrderByValorHasta()
/*  61:    */   {
/*  62:110 */     return this.rangoComisionDao.getListaOrderByValorHasta();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<RangoComisionCategoriaProducto> getListaRangoComisionCategoriaProducto()
/*  66:    */   {
/*  67:118 */     return this.rangoComisionCategoriaProductoDao.getListaRangoComisionCategoriaProducto();
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.impl.ServicioRangoComisionImpl
 * JD-Core Version:    0.7.0.1
 */