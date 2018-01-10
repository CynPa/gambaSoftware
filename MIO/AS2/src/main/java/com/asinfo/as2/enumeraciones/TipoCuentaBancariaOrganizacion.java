/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoCuentaBancariaOrganizacion
/*  4:   */ {
/*  5:22 */   BANCO("Banco"),  CAJA("Caja"),  TARJETA("Tarjeta"),  OTROS("Otros");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoCuentaBancariaOrganizacion(String nombre)
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
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion
 * JD-Core Version:    0.7.0.1
 */