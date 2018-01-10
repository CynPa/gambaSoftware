/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoCuentaContable
/*  4:   */ {
/*  5:24 */   PROVEEDOR("Proveedor"),  BANCO("Banco"),  RETENCION("Retencion"),  CLIENTE("Cliente"),  CAJA_CHICA("Caja Chica"),  CAJA("Caja"),  OTROS("Otros"),  ANTICIPO("Anticipos"),  GASTOS_BANCARIOS("Gastos Bancarios"),  CONCEPTOS_CONTABLES("Conceptos Contables"),  DESCUENTOS("Descuentos"),  INCOBRABLES("Incobrables"),  DEPRECIACION("Depreciacion");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoCuentaContable(String nombre)
/* 10:   */   {
/* 11:42 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:51 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoCuentaContable
 * JD-Core Version:    0.7.0.1
 */