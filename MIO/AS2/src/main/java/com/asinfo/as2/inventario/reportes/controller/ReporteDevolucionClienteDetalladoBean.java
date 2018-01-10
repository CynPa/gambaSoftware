/*  1:   */ package com.asinfo.as2.inventario.reportes.controller;
/*  2:   */ 
/*  3:   */ import javax.faces.bean.ManagedBean;
/*  4:   */ import javax.faces.bean.ViewScoped;
/*  5:   */ 
/*  6:   */ @ManagedBean
/*  7:   */ @ViewScoped
/*  8:   */ public class ReporteDevolucionClienteDetalladoBean
/*  9:   */   extends ReporteDevolucionClienteBean
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -2485852127833444042L;
/* 12:   */   
/* 13:   */   public void procesarDetalle()
/* 14:   */   {
/* 15:17 */     setIndicadorDetallado(true);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected String getCompileFileName()
/* 19:   */   {
/* 20:22 */     return "reporteDevolucionClienteDetallado";
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteDevolucionClienteDetalladoBean
 * JD-Core Version:    0.7.0.1
 */