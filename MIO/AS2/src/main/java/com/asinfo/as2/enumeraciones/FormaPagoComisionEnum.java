/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum FormaPagoComisionEnum
/*  4:   */ {
/*  5:21 */   CANTIDAD("Por cantidad"),  PORCENTAJE_COBRO("Por porcentaje del cobro");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private FormaPagoComisionEnum(String nombre)
/* 10:   */   {
/* 11:28 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:37 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.FormaPagoComisionEnum
 * JD-Core Version:    0.7.0.1
 */