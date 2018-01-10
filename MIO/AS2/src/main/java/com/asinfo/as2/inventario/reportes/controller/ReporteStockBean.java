/*  1:   */ package com.asinfo.as2.inventario.reportes.controller;
/*  2:   */ 
/*  3:   */ import javax.faces.bean.ManagedBean;
/*  4:   */ import javax.faces.bean.ViewScoped;
/*  5:   */ 
/*  6:   */ @ManagedBean
/*  7:   */ @ViewScoped
/*  8:   */ public class ReporteStockBean
/*  9:   */   extends ReporteStockValoradoBean
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 1L;
/* 12:   */   
/* 13:   */   protected String getCompileFileName()
/* 14:   */   {
/* 15:32 */     if (isIndicadorAgrupado()) {
/* 16:33 */       return "reporteStockAgrupado";
/* 17:   */     }
/* 18:35 */     return "reporteStock";
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteStockBean
 * JD-Core Version:    0.7.0.1
 */