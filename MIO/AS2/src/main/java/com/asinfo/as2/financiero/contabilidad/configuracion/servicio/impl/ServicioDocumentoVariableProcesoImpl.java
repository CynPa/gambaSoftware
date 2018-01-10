/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DocumentoVariableProcesoDao;
/*   4:    */ import com.asinfo.as2.entities.DocumentoVariableProceso;
/*   5:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoVariableProceso;
/*   6:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioDocumentoVariableProcesoImpl
/*  14:    */   implements ServicioDocumentoVariableProceso
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private DocumentoVariableProcesoDao documentoVariableProcesoDao;
/*  18:    */   
/*  19:    */   public void guardar(DocumentoVariableProceso documentoVariableProceso)
/*  20:    */     throws ExcepcionAS2Financiero
/*  21:    */   {
/*  22: 42 */     this.documentoVariableProcesoDao.guardar(documentoVariableProceso);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(DocumentoVariableProceso documentoVariableProceso)
/*  26:    */   {
/*  27: 54 */     this.documentoVariableProcesoDao.eliminar(documentoVariableProceso);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public DocumentoVariableProceso buscarPorId(Integer id)
/*  31:    */   {
/*  32: 65 */     return (DocumentoVariableProceso)this.documentoVariableProcesoDao.buscarPorId(id);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<DocumentoVariableProceso> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 77 */     return this.documentoVariableProcesoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<DocumentoVariableProceso> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 89 */     return this.documentoVariableProcesoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public DocumentoVariableProceso cargarDetalle(int idDocumentoVariableProceso)
/*  46:    */   {
/*  47: 99 */     return (DocumentoVariableProceso)this.documentoVariableProcesoDao.buscarPorId(this.documentoVariableProcesoDao);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52:109 */     return this.documentoVariableProcesoDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioDocumentoVariableProcesoImpl
 * JD-Core Version:    0.7.0.1
 */