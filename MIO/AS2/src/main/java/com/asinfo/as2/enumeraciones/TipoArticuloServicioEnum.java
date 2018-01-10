/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoArticuloServicioEnum
/*  4:   */ {
/*  5:20 */   ESTRUCTURA("Estructura"),  PRODUCTO("Producto"),  COMPONENTE("Componente");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoArticuloServicioEnum(String nombre)
/* 10:   */   {
/* 11:28 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:37 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:47 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoArticuloServicioEnum
 * JD-Core Version:    0.7.0.1
 */