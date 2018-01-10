/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoGarantiaCliente
/*  4:   */ {
/*  5:20 */   CHEQUE_POSFECHADO("Cheque Posfechado"),  LETRA_CAMBIO("Letra de cambio");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoGarantiaCliente(String nombre)
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
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoGarantiaCliente
 * JD-Core Version:    0.7.0.1
 */