/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoServicioCuentaBancariaEnum
/*  4:   */ {
/*  5:13 */   OT("Otros"),  RP("Roles de Pago"),  OC("Ordenes de Cobro"),  PR("Pago a Proveedores"),  OP("Orden de Pago"),  RU("Transferencias Interbancarias"),  PP("Pago Casilleros Electrónicos"),  CUE("Credito a una Cuenta (CUE) /Banco Bolivariano)"),  COB("Credito en otros bancos (COB) /Banco Bolivariano)");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoServicioCuentaBancariaEnum(String nombre)
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
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum
 * JD-Core Version:    0.7.0.1
 */