/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum MetodoFacturacionEnum
/*  4:   */ {
/*  5:22 */   PEDIDO_FACTURA_DESPACHO("Pedido,Factura,Despacho"),  PEDIDO_DESPACHO_FACTURA("Pedido,Despacho,Factura");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private MetodoFacturacionEnum(String nombre)
/* 10:   */   {
/* 11:28 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:37 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.MetodoFacturacionEnum
 * JD-Core Version:    0.7.0.1
 */