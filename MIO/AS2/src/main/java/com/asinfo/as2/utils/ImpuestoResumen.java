/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ 
/*  5:   */ public class ImpuestoResumen
/*  6:   */ {
/*  7:   */   private String nombreImpuesto;
/*  8:   */   private BigDecimal totalImpuesto;
/*  9:   */   
/* 10:   */   public ImpuestoResumen() {}
/* 11:   */   
/* 12:   */   public ImpuestoResumen(String nombreImpuesto, BigDecimal totalImpuesto)
/* 13:   */   {
/* 14:34 */     this.nombreImpuesto = nombreImpuesto;
/* 15:35 */     this.totalImpuesto = totalImpuesto;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getNombreImpuesto()
/* 19:   */   {
/* 20:41 */     return this.nombreImpuesto;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setNombreImpuesto(String nombreImpuesto)
/* 24:   */   {
/* 25:45 */     this.nombreImpuesto = nombreImpuesto;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public BigDecimal getTotalImpuesto()
/* 29:   */   {
/* 30:49 */     if (this.totalImpuesto == null) {
/* 31:50 */       this.totalImpuesto = BigDecimal.ZERO;
/* 32:   */     }
/* 33:52 */     return this.totalImpuesto;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setTotalImpuesto(BigDecimal totalImpuesto)
/* 37:   */   {
/* 38:56 */     this.totalImpuesto = totalImpuesto;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.ImpuestoResumen
 * JD-Core Version:    0.7.0.1
 */