/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum Periodicidad
/*  4:   */ {
/*  5:21 */   ANUAL("Anual"),  MENSUAL("Mensual"),  PERSONALIZADO("Personalizado");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private Periodicidad(String nombre)
/* 10:   */   {
/* 11:26 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:30 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.Periodicidad
 * JD-Core Version:    0.7.0.1
 */