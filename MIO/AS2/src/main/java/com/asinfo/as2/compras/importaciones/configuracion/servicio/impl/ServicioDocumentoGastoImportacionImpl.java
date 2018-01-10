/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioDocumentoGastoImportacion;
/*   4:    */ import com.asinfo.as2.dao.DocumentoGastoImportacionDao;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.DocumentoGastoImportacion;
/*   7:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   8:    */ import com.asinfo.as2.entities.GastoImportacion;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioDocumentoGastoImportacionImpl
/*  16:    */   implements ServicioDocumentoGastoImportacion
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private transient DocumentoGastoImportacionDao documentoGastoImportacionDao;
/*  20:    */   
/*  21:    */   public void guardar(DocumentoGastoImportacion documentoGastoImportacion)
/*  22:    */   {
/*  23: 46 */     this.documentoGastoImportacionDao.guardar(documentoGastoImportacion);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void eliminar(DocumentoGastoImportacion documentoGastoImportacion)
/*  27:    */   {
/*  28: 57 */     this.documentoGastoImportacionDao.eliminar(documentoGastoImportacion);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public DocumentoGastoImportacion buscarPorId(int idDocumentoGastoImportacion)
/*  32:    */   {
/*  33: 67 */     return (DocumentoGastoImportacion)this.documentoGastoImportacionDao.buscarPorId(Integer.valueOf(idDocumentoGastoImportacion));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<DocumentoGastoImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  37:    */   {
/*  38: 79 */     return this.documentoGastoImportacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<DocumentoGastoImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  42:    */   {
/*  43: 90 */     return this.documentoGastoImportacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int contarPorCriterio(Map<String, String> filters)
/*  47:    */   {
/*  48:100 */     return this.documentoGastoImportacionDao.contarPorCriterio(filters);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public DocumentoGastoImportacion cargarDetalle(int idDocumentoGastoImportacion)
/*  52:    */   {
/*  53:110 */     return this.documentoGastoImportacionDao.cargarDetalle(idDocumentoGastoImportacion);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<DocumentoGastoImportacion> obtenerListaDocumentoGastoImportacion(Documento documento, boolean indicadorGastoObligatorio)
/*  57:    */   {
/*  58:122 */     return this.documentoGastoImportacionDao.obtenerListaDocumentoGastoImportacion(documento, indicadorGastoObligatorio);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<DocumentoGastoImportacion> obtenerListaDocumentoGastoImportacion(FacturaProveedor facturaProveedor, boolean indicadorGastoObligatorio, List<GastoImportacion> lista)
/*  62:    */   {
/*  63:131 */     return this.documentoGastoImportacionDao.obtenerListaDocumentoGastoImportacion(facturaProveedor, indicadorGastoObligatorio, lista);
/*  64:    */   }
/*  65:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.impl.ServicioDocumentoGastoImportacionImpl
 * JD-Core Version:    0.7.0.1
 */