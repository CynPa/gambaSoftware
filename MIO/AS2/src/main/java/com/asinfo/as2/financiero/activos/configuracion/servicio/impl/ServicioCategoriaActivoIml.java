/*   1:    */ package com.asinfo.as2.financiero.activos.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CategoriaActivoDao;
/*   4:    */ import com.asinfo.as2.dao.SubcategoriaActivoDao;
/*   5:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*   7:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioCategoriaActivoIml
/*  15:    */   implements ServicioCategoriaActivo
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private transient CategoriaActivoDao categoriaActivoDao;
/*  19:    */   @EJB
/*  20:    */   private transient SubcategoriaActivoDao subcategoriaActivoDao;
/*  21:    */   
/*  22:    */   public void guardar(CategoriaActivo categoriaActivo)
/*  23:    */   {
/*  24: 46 */     for (SubcategoriaActivo subcategoriaActivo : categoriaActivo.getListaSubcategoriaActivo()) {
/*  25: 47 */       this.subcategoriaActivoDao.guardar(subcategoriaActivo);
/*  26:    */     }
/*  27: 50 */     this.categoriaActivoDao.guardar(categoriaActivo);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(CategoriaActivo categoriaActivo)
/*  31:    */   {
/*  32: 60 */     categoriaActivo = cargarDetalle(categoriaActivo.getId());
/*  33: 61 */     for (SubcategoriaActivo subcategoriaActivo : categoriaActivo.getListaSubcategoriaActivo()) {
/*  34: 62 */       this.subcategoriaActivoDao.eliminar(subcategoriaActivo);
/*  35:    */     }
/*  36: 64 */     this.categoriaActivoDao.eliminar(categoriaActivo);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public CategoriaActivo buscarPorId(int idCategoriaActivo)
/*  40:    */   {
/*  41: 75 */     return (CategoriaActivo)this.categoriaActivoDao.buscarPorId(Integer.valueOf(idCategoriaActivo));
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<CategoriaActivo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  45:    */   {
/*  46: 86 */     return this.categoriaActivoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<CategoriaActivo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  50:    */   {
/*  51: 97 */     return this.categoriaActivoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int contarPorCriterio(Map<String, String> filters)
/*  55:    */   {
/*  56:107 */     return this.categoriaActivoDao.contarPorCriterio(filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CategoriaActivo cargarDetalle(int idCategoriaActivo)
/*  60:    */   {
/*  61:117 */     return this.categoriaActivoDao.cargarDetalle(idCategoriaActivo);
/*  62:    */   }
/*  63:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioCategoriaActivoIml
 * JD-Core Version:    0.7.0.1
 */