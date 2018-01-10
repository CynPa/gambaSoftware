/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoDepartamento
/*  4:   */ {
/*  5:22 */   VENTAS("Ventas"),  COMPRAS("Compras"),  SERVICIO("Servicio"),  CENTRO_TRABAJO("Centro Trabajo"),  CONTABILIDAD("Contabilidad");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoDepartamento(String nombre)
/* 10:   */   {
/* 11:31 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:39 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoDepartamento
 * JD-Core Version:    0.7.0.1
 */