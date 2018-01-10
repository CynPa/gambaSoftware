/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoProducto
/*  4:   */ {
/*  5:24 */   ARTICULO("Articulo"),  SERVICIO("Servicio"),  ARTICULO_NO_INVENTARIABLE("Articulo No Inventariable"),  ACTIVO_FIJO("Activo Fijo");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoProducto(String nombre)
/* 10:   */   {
/* 11:32 */     setNombre(nombre);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:41 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:51 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoProducto
 * JD-Core Version:    0.7.0.1
 */