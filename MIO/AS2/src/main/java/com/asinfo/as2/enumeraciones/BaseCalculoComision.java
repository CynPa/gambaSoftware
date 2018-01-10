/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum BaseCalculoComision
/*  4:   */ {
/*  5:20 */   FACTURADO("Facturado");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private BaseCalculoComision(String nombre)
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
 * Qualified Name:     com.asinfo.as2.enumeraciones.BaseCalculoComision
 * JD-Core Version:    0.7.0.1
 */