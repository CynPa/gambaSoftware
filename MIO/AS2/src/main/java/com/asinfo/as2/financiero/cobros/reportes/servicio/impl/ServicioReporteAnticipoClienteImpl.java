/*  1:   */ package com.asinfo.as2.financiero.cobros.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.cobros.ReporteAnticipoClienteDao;
/*  4:   */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteAnticipoCliente;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.EJB;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class ServicioReporteAnticipoClienteImpl
/* 11:   */   implements ServicioReporteAnticipoCliente
/* 12:   */ {
/* 13:   */   @EJB
/* 14:   */   private ReporteAnticipoClienteDao reporteAnticipoClienteDao;
/* 15:   */   
/* 16:   */   public List getReporteAnticipo(int idAnticipo)
/* 17:   */   {
/* 18:36 */     return this.reporteAnticipoClienteDao.getReporteAnticipo(idAnticipo);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.impl.ServicioReporteAnticipoClienteImpl
 * JD-Core Version:    0.7.0.1
 */