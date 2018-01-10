/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoCentroTrabajo
/*  4:   */ {
/*  5:21 */   MANUFACTURA("Manufactura"),  SERVICIO("Servicio");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoCentroTrabajo(String nombre)
/* 10:   */   {
/* 11:27 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:36 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoCentroTrabajo
 * JD-Core Version:    0.7.0.1
 */