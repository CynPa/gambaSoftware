/*  1:   */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.DocumentoDigitalizadoDao;
/*  4:   */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*  5:   */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  6:   */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioDocumentoDigitalizadoImpl
/* 14:   */   implements ServicioDocumentoDigitalizado
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private DocumentoDigitalizadoDao documentoDigitalizadoDao;
/* 18:   */   
/* 19:   */   public List<DocumentoDigitalizado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 20:   */   {
/* 21:23 */     return this.documentoDigitalizadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int contarPorCriterio(Map<String, String> filters)
/* 25:   */   {
/* 26:28 */     return this.documentoDigitalizadoDao.contarPorCriterio(filters);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void guardar(DocumentoDigitalizado documentoDigitalizado)
/* 30:   */   {
/* 31:33 */     this.documentoDigitalizadoDao.guardar(documentoDigitalizado);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void eliminar(DocumentoDigitalizado documentoDigitalizado)
/* 35:   */   {
/* 36:39 */     this.documentoDigitalizadoDao.eliminar(documentoDigitalizado);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public DocumentoDigitalizado buscarPorId(int idDocumentoDigitalizado)
/* 40:   */   {
/* 41:46 */     return (DocumentoDigitalizado)this.documentoDigitalizadoDao.buscarPorId(Integer.valueOf(idDocumentoDigitalizado));
/* 42:   */   }
/* 43:   */   
/* 44:   */   public List<DocumentoDigitalizado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 45:   */   {
/* 46:51 */     List<DocumentoDigitalizado> lista = this.documentoDigitalizadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 47:52 */     for (DocumentoDigitalizado documentoDigitalizado : lista) {
/* 48:53 */       documentoDigitalizado.getCategoriaDocumentoDigitalizado().getNombre();
/* 49:   */     }
/* 50:55 */     return lista;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public DocumentoDigitalizado cargarDetalles(int idDocumentoDigitalizado)
/* 54:   */   {
/* 55:59 */     return this.documentoDigitalizadoDao.cargarDetalles(idDocumentoDigitalizado);
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioDocumentoDigitalizadoImpl
 * JD-Core Version:    0.7.0.1
 */