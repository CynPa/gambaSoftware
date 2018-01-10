/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoSangre
/*  4:   */ {
/*  5:20 */   A_MAS("A+"),  A_MENOS("A-"),  B_MAS("B+"),  B_MENOS("B-"),  AB_MAS("AB+"),  AB_MENOS("AB-"),  O_MAS("O+"),  O_MENOS("O-");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoSangre(String nombre)
/* 10:   */   {
/* 11:25 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:34 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoSangre
 * JD-Core Version:    0.7.0.1
 */