/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ import java.util.Date;
/*  6:   */ 
/*  7:   */ public class Celda
/*  8:   */ {
/*  9:   */   private String valor;
/* 10:   */   
/* 11:   */   public Celda(String valor)
/* 12:   */   {
/* 13:34 */     this.valor = valor;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setValor(String valor)
/* 17:   */   {
/* 18:44 */     this.valor = valor;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getValorString()
/* 22:   */   {
/* 23:51 */     return this.valor;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BigDecimal getValorBigDecimal()
/* 27:   */   {
/* 28:58 */     return BigDecimal.valueOf(Double.parseDouble(this.valor));
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Integer getValorInteger()
/* 32:   */   {
/* 33:65 */     return Integer.valueOf(Integer.parseInt(this.valor.replace(".0", "")));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Date getValorDate()
/* 37:   */   {
/* 38:72 */     return FuncionesUtiles.stringToDate(this.valor);
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.Celda
 * JD-Core Version:    0.7.0.1
 */