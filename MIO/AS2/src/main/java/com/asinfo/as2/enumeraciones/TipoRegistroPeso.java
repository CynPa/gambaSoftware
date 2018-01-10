/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoRegistroPeso
/*  4:   */ {
/*  5:21 */   INGRESO_MATERIA_PRIMA("Ingreso Materia Prima"),  DESPACHO_CLIENTE("Despacho Cliente"),  RECEPCION_TRANSFERENCIA("Recepcion Transferencia"),  OTROS("Otros"),  DEVOLUCION_CLIENTE("Devolución Cliente"),  TRANSFERENCIA_ENTRE_BODEGAS("Transferencia entre bodegas");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoRegistroPeso(String nombre)
/* 10:   */   {
/* 11:31 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:40 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoRegistroPeso
 * JD-Core Version:    0.7.0.1
 */