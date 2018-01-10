/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoImpuestoEnum
/*  4:   */ {
/*  5:21 */   AFECTA_PRECIO("Afecta Precio"),  AFECTA_CANTIDAD_UNIDADES("Afecta Cantidad Unidades");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoImpuestoEnum(String nombre)
/* 10:   */   {
/* 11:31 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:42 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:52 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoImpuestoEnum
 * JD-Core Version:    0.7.0.1
 */