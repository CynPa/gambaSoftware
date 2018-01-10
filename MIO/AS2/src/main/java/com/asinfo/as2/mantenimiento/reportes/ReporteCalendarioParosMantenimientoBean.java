/*  1:   */ package com.asinfo.as2.mantenimiento.reportes;
/*  2:   */ 
/*  3:   */ import javax.annotation.PostConstruct;
/*  4:   */ import javax.faces.bean.ManagedBean;
/*  5:   */ import javax.faces.bean.RequestScoped;
/*  6:   */ 
/*  7:   */ @ManagedBean
/*  8:   */ @RequestScoped
/*  9:   */ public class ReporteCalendarioParosMantenimientoBean
/* 10:   */   extends ReporteCalendarioMantenimientoBean
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   
/* 14:   */   @PostConstruct
/* 15:   */   public void init()
/* 16:   */   {
/* 17:34 */     setIndicadorReporteParo(true);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.reportes.ReporteCalendarioParosMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */