/*   1:    */ package com.asinfo.as2.financiero.SRI.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.sri.PorcentajeImpuestoRentaAnualDao;
/*   4:    */ import com.asinfo.as2.entities.sri.PorcentajeImpuestoRentaAnual;
/*   5:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioPorcentajeImpuestoRentaAnual;
/*   6:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioPorcentajeImpuestoRentaAnualImpl
/*  15:    */   implements ServicioPorcentajeImpuestoRentaAnual
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   PorcentajeImpuestoRentaAnualDao porcentajeImpuestoRentaAnualDao;
/*  19:    */   
/*  20:    */   public void guardar(PorcentajeImpuestoRentaAnual porcentajeImpuestoRentaAnual)
/*  21:    */   {
/*  22: 45 */     this.porcentajeImpuestoRentaAnualDao.guardar(porcentajeImpuestoRentaAnual);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(PorcentajeImpuestoRentaAnual porcentajeImpuestoRentaAnual)
/*  26:    */   {
/*  27: 57 */     this.porcentajeImpuestoRentaAnualDao.eliminar(porcentajeImpuestoRentaAnual);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public PorcentajeImpuestoRentaAnual buscarPorId(int idPorcentajeImpuestoRentaAnual)
/*  31:    */   {
/*  32: 68 */     return (PorcentajeImpuestoRentaAnual)this.porcentajeImpuestoRentaAnualDao.buscarPorId(Integer.valueOf(idPorcentajeImpuestoRentaAnual));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<PorcentajeImpuestoRentaAnual> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 80 */     return this.porcentajeImpuestoRentaAnualDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<PorcentajeImpuestoRentaAnual> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 91 */     return this.porcentajeImpuestoRentaAnualDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47: 95 */     return this.porcentajeImpuestoRentaAnualDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public BigDecimal obtenerPorcentajePorAnio(int anio)
/*  51:    */     throws ExcepcionAS2Financiero
/*  52:    */   {
/*  53:105 */     return this.porcentajeImpuestoRentaAnualDao.obtenerPorcentajePorAnio(anio);
/*  54:    */   }
/*  55:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.impl.ServicioPorcentajeImpuestoRentaAnualImpl
 * JD-Core Version:    0.7.0.1
 */