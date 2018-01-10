/*  1:   */ package com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteChequeAsientoDao;
/*  4:   */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteChequeAsiento;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.EJB;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class ServicioReporteChequeAsientoImpl
/* 11:   */   implements ServicioReporteChequeAsiento
/* 12:   */ {
/* 13:   */   @EJB
/* 14:   */   ReporteChequeAsientoDao reporteChequeAsientoDao;
/* 15:   */   
/* 16:   */   public List<Object[]> getReporteChequeAsiento(int idAsiento)
/* 17:   */   {
/* 18:34 */     return this.reporteChequeAsientoDao.getReporteChequeAsiento(idAsiento);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioReporteChequeAsientoImpl
 * JD-Core Version:    0.7.0.1
 */