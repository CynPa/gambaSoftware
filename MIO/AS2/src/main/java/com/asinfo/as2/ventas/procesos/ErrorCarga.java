/*  1:   */ package com.asinfo.as2.ventas.procesos;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ErrorCarga
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = -520226958396026287L;
/*  9:   */   private String error;
/* 10:   */   private String linea;
/* 11:   */   
/* 12:   */   public ErrorCarga() {}
/* 13:   */   
/* 14:   */   public ErrorCarga(String error, String linea)
/* 15:   */   {
/* 16:19 */     this.error = error;
/* 17:20 */     this.linea = linea;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getError()
/* 21:   */   {
/* 22:24 */     return this.error;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setError(String error)
/* 26:   */   {
/* 27:28 */     this.error = error;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getLinea()
/* 31:   */   {
/* 32:32 */     return this.linea;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setLinea(String linea)
/* 36:   */   {
/* 37:36 */     this.linea = linea;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.ErrorCarga
 * JD-Core Version:    0.7.0.1
 */