/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ 
/*  5:   */ public class FactorConversion
/*  6:   */ {
/*  7:   */   private BigDecimal factor;
/*  8:   */   private boolean indicadorInverso;
/*  9:   */   
/* 10:   */   public FactorConversion(BigDecimal factor, boolean indicadorInverso)
/* 11:   */   {
/* 12:30 */     this.factor = factor;
/* 13:31 */     this.indicadorInverso = indicadorInverso;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public BigDecimal getFactor()
/* 17:   */   {
/* 18:40 */     return this.factor;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setFactor(BigDecimal factor)
/* 22:   */   {
/* 23:50 */     this.factor = factor;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean isIndicadorInverso()
/* 27:   */   {
/* 28:59 */     return this.indicadorInverso;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setIndicadorInverso(boolean indicadorInverso)
/* 32:   */   {
/* 33:69 */     this.indicadorInverso = indicadorInverso;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.FactorConversion
 * JD-Core Version:    0.7.0.1
 */