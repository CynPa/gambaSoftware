/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.PeriodoDao;
/*   4:    */ import com.asinfo.as2.entities.Periodo;
/*   5:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   7:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioPeriodoImpl
/*  16:    */   implements ServicioPeriodo
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private PeriodoDao periodoDao;
/*  20:    */   
/*  21:    */   public void guardar(Periodo periodo)
/*  22:    */   {
/*  23: 42 */     this.periodoDao.guardar(periodo);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void eliminar(Periodo periodo)
/*  27:    */   {
/*  28: 53 */     this.periodoDao.eliminar(periodo);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Periodo buscarPorId(Integer id)
/*  32:    */   {
/*  33: 64 */     return (Periodo)this.periodoDao.buscarPorId(id);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Periodo buscarPorFecha(Date fecha, int idOrganizacion, DocumentoBase documentoBase)
/*  37:    */     throws ExcepcionAS2Financiero
/*  38:    */   {
/*  39: 75 */     return this.periodoDao.buscarPorFecha(fecha, idOrganizacion, documentoBase);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void validar(Date fechaDesde, Date fechaHasta, int idOrganizacion)
/*  43:    */     throws ExcepcionAS2Financiero
/*  44:    */   {
/*  45: 86 */     this.periodoDao.validar(fechaDesde, fechaHasta, idOrganizacion);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Periodo obtenerPeriodoActual(int idOrganizacion, DocumentoBase documentoBase)
/*  49:    */     throws ExcepcionAS2Financiero
/*  50:    */   {
/*  51: 97 */     return this.periodoDao.obtenerPeriodoActual(idOrganizacion, documentoBase);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<Periodo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56:107 */     return this.periodoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioPeriodoImpl
 * JD-Core Version:    0.7.0.1
 */