/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum OrdenamientoEnum
/*  4:   */ {
/*  5:21 */   FECHA("Fecha"),  FACTURA("Factura"),  DOCUMENTO("Documento"),  SALDO_FACTURA("Saldo documento");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private OrdenamientoEnum(String nombre)
/* 10:   */   {
/* 11:26 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:35 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.OrdenamientoEnum
 * JD-Core Version:    0.7.0.1
 */