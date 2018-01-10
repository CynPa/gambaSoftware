/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ConceptoContableDao;
/*   4:    */ import com.asinfo.as2.entities.ConceptoContable;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioConceptoContable;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioConceptoContableImpl
/*  15:    */   implements ServicioConceptoContable
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private ConceptoContableDao conceptoContableDao;
/*  19:    */   
/*  20:    */   public void guardar(ConceptoContable conceptoContable)
/*  21:    */     throws ExcepcionAS2
/*  22:    */   {
/*  23: 39 */     if ((conceptoContable.getCuentaContableDebe() != null) && (conceptoContable.getCuentaContableDebe().getId() != 0) && (conceptoContable.getCuentaContableHaber() != null) && (conceptoContable.getCuentaContableHaber().getId() != 0)) {
/*  24: 40 */       this.conceptoContableDao.guardar(conceptoContable);
/*  25:    */     } else {
/*  26: 43 */       throw new ExcepcionAS2("msg_error_seleccionar_cuenta_contable");
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(ConceptoContable conceptoContable)
/*  31:    */   {
/*  32: 57 */     this.conceptoContableDao.eliminar(conceptoContable);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ConceptoContable buscarPorId(Integer id)
/*  36:    */   {
/*  37: 69 */     return (ConceptoContable)this.conceptoContableDao.buscarPorId(id);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<ConceptoContable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 83 */     return this.conceptoContableDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<ConceptoContable> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 97 */     return this.conceptoContableDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52:106 */     return this.conceptoContableDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioConceptoContableImpl
 * JD-Core Version:    0.7.0.1
 */