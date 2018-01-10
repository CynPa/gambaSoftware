/*  1:   */ package com.asinfo.as2.ventas.reportes;
/*  2:   */ 
/*  3:   */ import javax.faces.bean.ManagedBean;
/*  4:   */ import javax.faces.bean.ViewScoped;
/*  5:   */ 
/*  6:   */ @ManagedBean
/*  7:   */ @ViewScoped
/*  8:   */ public class ReporteFacturaClienteDetalladoBean
/*  9:   */   extends ReporteFacturaClienteBean
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -5726792286003004390L;
/* 12:   */   
/* 13:   */   public void procesarDetalle()
/* 14:   */   {
/* 15:34 */     setIndicadorDetallado(true);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected String getCompileFileName()
/* 19:   */   {
/* 20:39 */     return "reporteFacturaClienteDetallado";
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteFacturaClienteDetalladoBean
 * JD-Core Version:    0.7.0.1
 */