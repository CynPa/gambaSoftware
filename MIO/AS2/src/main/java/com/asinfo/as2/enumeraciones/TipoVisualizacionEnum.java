/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoVisualizacionEnum
/*  4:   */ {
/*  5:21 */   MIS_REGISTROS("Mis Registros"),  MIS_SUCURSALES("Mis Sucursales"),  TODA_LA_ORGANIZACION("Toda la Organizacion");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoVisualizacionEnum(String nombre)
/* 10:   */   {
/* 11:26 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:35 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:45 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoVisualizacionEnum
 * JD-Core Version:    0.7.0.1
 */