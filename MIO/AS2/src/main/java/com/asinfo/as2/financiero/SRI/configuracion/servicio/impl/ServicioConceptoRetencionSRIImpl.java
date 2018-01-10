/*   1:    */ package com.asinfo.as2.financiero.SRI.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.sri.ConceptoRetencionSRIDao;
/*   4:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   5:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*   6:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioConceptoRetencionSRIImpl
/*  16:    */   implements ServicioConceptoRetencionSRI
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private ConceptoRetencionSRIDao conceptoRetencionSRIDao;
/*  20:    */   
/*  21:    */   public void guardar(ConceptoRetencionSRI conceptoRetencionSRI)
/*  22:    */   {
/*  23: 45 */     if (conceptoRetencionSRI.getFechaHasta() != null) {
/*  24: 46 */       conceptoRetencionSRI.setActivo(false);
/*  25:    */     } else {
/*  26: 48 */       conceptoRetencionSRI.setActivo(true);
/*  27:    */     }
/*  28: 50 */     this.conceptoRetencionSRIDao.guardar(conceptoRetencionSRI);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void eliminar(ConceptoRetencionSRI conceptoRetencionSRI)
/*  32:    */   {
/*  33: 61 */     this.conceptoRetencionSRIDao.eliminar(conceptoRetencionSRI);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ConceptoRetencionSRI buscarPorId(Integer id)
/*  37:    */   {
/*  38: 72 */     return (ConceptoRetencionSRI)this.conceptoRetencionSRIDao.buscarPorId(id);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<ConceptoRetencionSRI> getConceptoListaRetencionPorFecha(Date fecha)
/*  42:    */     throws ExcepcionAS2Financiero
/*  43:    */   {
/*  44: 82 */     return this.conceptoRetencionSRIDao.getConceptoListaRetencionPorFecha(fecha);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<ConceptoRetencionSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 94 */     return this.conceptoRetencionSRIDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public List<ConceptoRetencionSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  53:    */   {
/*  54:105 */     return this.conceptoRetencionSRIDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int contarPorCriterio(Map<String, String> filters)
/*  58:    */   {
/*  59:115 */     return this.conceptoRetencionSRIDao.contarPorCriterio(filters);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public ConceptoRetencionSRI getConceptoRetencionPorIdYFecha(int idConceptoRetencionSRI, Date fecha)
/*  63:    */     throws ExcepcionAS2Financiero
/*  64:    */   {
/*  65:125 */     return this.conceptoRetencionSRIDao.getConceptoRetencionPorIdYFecha(idConceptoRetencionSRI, fecha);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<ConceptoRetencionSRI> conceptoRetencionIVAPorcentaje(int idOrganizacion)
/*  69:    */   {
/*  70:129 */     return this.conceptoRetencionSRIDao.conceptoRetencionIVAPorcentaje(idOrganizacion);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ConceptoRetencionSRI conceptoRetencionPorcentajeYCodigo(BigDecimal porcentajeRetencion, String codigoConcepto, int idOrganizacion)
/*  74:    */   {
/*  75:134 */     return this.conceptoRetencionSRIDao.conceptoRetencionPorcentajeYCodigo(porcentajeRetencion, codigoConcepto, idOrganizacion);
/*  76:    */   }
/*  77:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.impl.ServicioConceptoRetencionSRIImpl
 * JD-Core Version:    0.7.0.1
 */