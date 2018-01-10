/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoCosto
/*  4:   */ {
/*  5:23 */   ESTANDAR("Estandar"),  PROMEDIO_PONDERADO("Promedio Ponderado");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoCosto(String nombre)
/* 10:   */   {
/* 11:29 */     setNombre(nombre);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:37 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:45 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoCosto
 * JD-Core Version:    0.7.0.1
 */