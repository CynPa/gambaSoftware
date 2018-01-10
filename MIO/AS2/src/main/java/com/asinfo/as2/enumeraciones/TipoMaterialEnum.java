/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoMaterialEnum
/*  4:   */ {
/*  5:22 */   MACRO("Macro"),  MICRO("Micro");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoMaterialEnum(String nombre)
/* 10:   */   {
/* 11:28 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:37 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static TipoMaterialEnum obtenerEstadoProduccion(String estadoString)
/* 20:   */   {
/* 21:47 */     TipoMaterialEnum estadoAux = null;
/* 22:48 */     for (TipoMaterialEnum estado : values()) {
/* 23:49 */       if (estadoString.equals(estado.getNombre())) {
/* 24:50 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:53 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoMaterialEnum
 * JD-Core Version:    0.7.0.1
 */