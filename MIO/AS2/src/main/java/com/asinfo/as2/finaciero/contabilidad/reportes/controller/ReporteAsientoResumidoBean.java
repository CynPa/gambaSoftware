/*  1:   */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*  2:   */ 
/*  3:   */ import javax.faces.bean.ManagedBean;
/*  4:   */ import javax.faces.bean.ViewScoped;
/*  5:   */ 
/*  6:   */ @ManagedBean
/*  7:   */ @ViewScoped
/*  8:   */ public class ReporteAsientoResumidoBean
/*  9:   */   extends ReporteAsientoBean
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 1766715845988189677L;
/* 12:   */   
/* 13:   */   public void setearResumen()
/* 14:   */   {
/* 15:16 */     setResumido(Boolean.valueOf(true));
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteAsientoResumidoBean
 * JD-Core Version:    0.7.0.1
 */