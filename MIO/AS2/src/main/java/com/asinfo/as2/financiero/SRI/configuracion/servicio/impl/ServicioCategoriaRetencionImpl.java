/*   1:    */ package com.asinfo.as2.financiero.SRI.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CategoriaRetencionDao;
/*   4:    */ import com.asinfo.as2.dao.DetalleCategoriaRetencionDao;
/*   5:    */ import com.asinfo.as2.entities.CategoriaRetencion;
/*   6:    */ import com.asinfo.as2.entities.DetalleCategoriaRetencion;
/*   7:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioCategoriaRetencionImpl
/*  15:    */   implements ServicioCategoriaRetencion
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private CategoriaRetencionDao categoriaRetencionDao;
/*  19:    */   @EJB
/*  20:    */   private DetalleCategoriaRetencionDao detalleCategoriaRetencionDao;
/*  21:    */   
/*  22:    */   public void guardar(CategoriaRetencion categoriaRetencion)
/*  23:    */   {
/*  24: 46 */     for (DetalleCategoriaRetencion detalleCategoriaRetencion : categoriaRetencion.getListaDetalleCategoriaRetencion()) {
/*  25: 47 */       this.detalleCategoriaRetencionDao.guardar(detalleCategoriaRetencion);
/*  26:    */     }
/*  27: 49 */     this.categoriaRetencionDao.guardar(categoriaRetencion);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(CategoriaRetencion categoriaRetencion)
/*  31:    */   {
/*  32: 59 */     for (DetalleCategoriaRetencion detalleCategoriaRetencion : categoriaRetencion.getListaDetalleCategoriaRetencion()) {
/*  33: 60 */       this.detalleCategoriaRetencionDao.eliminar(detalleCategoriaRetencion);
/*  34:    */     }
/*  35: 62 */     this.categoriaRetencionDao.eliminar(categoriaRetencion);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public CategoriaRetencion buscarPorId(int idCategoriaRetencion)
/*  39:    */   {
/*  40: 72 */     return (CategoriaRetencion)this.categoriaRetencionDao.buscarPorId(Integer.valueOf(idCategoriaRetencion));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<CategoriaRetencion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  44:    */   {
/*  45: 84 */     return this.categoriaRetencionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<CategoriaRetencion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  49:    */   {
/*  50: 95 */     return this.categoriaRetencionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int contarPorCriterio(Map<String, String> filters)
/*  54:    */   {
/*  55:105 */     return this.categoriaRetencionDao.contarPorCriterio(filters);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public CategoriaRetencion cargarDetalle(int idCategoriaRetencion)
/*  59:    */   {
/*  60:115 */     return this.categoriaRetencionDao.cargarDetalle(idCategoriaRetencion);
/*  61:    */   }
/*  62:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.impl.ServicioCategoriaRetencionImpl
 * JD-Core Version:    0.7.0.1
 */