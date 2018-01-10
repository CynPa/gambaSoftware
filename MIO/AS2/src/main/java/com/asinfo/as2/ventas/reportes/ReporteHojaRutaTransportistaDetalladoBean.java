/*  1:   */ package com.asinfo.as2.ventas.reportes;
/*  2:   */ 
/*  3:   */ import javax.annotation.PostConstruct;
/*  4:   */ import javax.faces.bean.ManagedBean;
/*  5:   */ import javax.faces.bean.RequestScoped;
/*  6:   */ 
/*  7:   */ @ManagedBean
/*  8:   */ @RequestScoped
/*  9:   */ public class ReporteHojaRutaTransportistaDetalladoBean
/* 10:   */   extends ReporteHojaRutaTransportistaBean
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   
/* 14:   */   @PostConstruct
/* 15:   */   public void reporteDetallado()
/* 16:   */   {
/* 17:33 */     setDetallado(true);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteHojaRutaTransportistaDetalladoBean
 * JD-Core Version:    0.7.0.1
 */