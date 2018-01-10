/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoEstadoFinanciero
/*  4:   */ {
/*  5:23 */   BALANCE_GENERAL("Estado de Situacion Financiera"),  BALANCE_COMPROBACION("Balance de Comprobacion"),  BALANCE_RESULTADOS("Estado de Resultados");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoEstadoFinanciero(String nombre)
/* 10:   */   {
/* 11:30 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:39 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoEstadoFinanciero
 * JD-Core Version:    0.7.0.1
 */