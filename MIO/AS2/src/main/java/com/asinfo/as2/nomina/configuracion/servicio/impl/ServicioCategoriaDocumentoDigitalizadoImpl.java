/*  1:   */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.CategoriaDocumentoDigitalizadoDao;
/*  4:   */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*  5:   */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCategoriaDocumentoDigitalizado;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioCategoriaDocumentoDigitalizadoImpl
/* 13:   */   implements ServicioCategoriaDocumentoDigitalizado
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private CategoriaDocumentoDigitalizadoDao categoriaDocumentoDigitalizadoDao;
/* 17:   */   
/* 18:   */   public List<CategoriaDocumentoDigitalizado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 19:   */   {
/* 20:23 */     return this.categoriaDocumentoDigitalizadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int contarPorCriterio(Map<String, String> filters)
/* 24:   */   {
/* 25:28 */     return this.categoriaDocumentoDigitalizadoDao.contarPorCriterio(filters);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void guardar(CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado)
/* 29:   */   {
/* 30:33 */     this.categoriaDocumentoDigitalizadoDao.guardar(categoriaDocumentoDigitalizado);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void eliminar(CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado)
/* 34:   */   {
/* 35:39 */     this.categoriaDocumentoDigitalizadoDao.eliminar(categoriaDocumentoDigitalizado);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public CategoriaDocumentoDigitalizado buscarPorId(int idCategoriaDocumentoDigitalizado)
/* 39:   */   {
/* 40:46 */     return (CategoriaDocumentoDigitalizado)this.categoriaDocumentoDigitalizadoDao.buscarPorId(Integer.valueOf(idCategoriaDocumentoDigitalizado));
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<CategoriaDocumentoDigitalizado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 44:   */   {
/* 45:51 */     return this.categoriaDocumentoDigitalizadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioCategoriaDocumentoDigitalizadoImpl
 * JD-Core Version:    0.7.0.1
 */