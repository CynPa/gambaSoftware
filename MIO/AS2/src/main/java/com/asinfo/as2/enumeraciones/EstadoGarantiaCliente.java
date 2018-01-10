/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum EstadoGarantiaCliente
/*  4:   */ {
/*  5:13 */   ANULADO("Anulado"),  REGISTRADO("Registrado"),  CONTABILIZADO("Contabilizado"),  PROTESTADO("Protestado"),  DEVUELTO("Devuelto");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private EstadoGarantiaCliente(String nombre)
/* 10:   */   {
/* 11:22 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:31 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.EstadoGarantiaCliente
 * JD-Core Version:    0.7.0.1
 */