/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum OrigenEnum
/*  4:   */ {
/*  5:11 */   AS2("AS2"),  POS_WEB("POS Web");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private OrigenEnum(String nombre)
/* 10:   */   {
/* 11:15 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:19 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.OrigenEnum
 * JD-Core Version:    0.7.0.1
 */