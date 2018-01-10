/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ClaseBodegaEnum
/*  4:   */ {
/*  5:21 */   NORMAL("Normal"),  SERVICIO_MANTENIMIENTO("Servicio Mantenimiento"),  TRABAJO("Trabajo"),  CUARENTENA("Cuarentena");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private ClaseBodegaEnum(String nombre)
/* 10:   */   {
/* 11:25 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:34 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static ClaseBodegaEnum obtenerClaseBodegaPorNombre(String nombre)
/* 20:   */   {
/* 21:44 */     ClaseBodegaEnum resp = null;
/* 22:45 */     for (ClaseBodegaEnum claseBodega : values()) {
/* 23:46 */       if (nombre.equals(claseBodega.getNombre()))
/* 24:   */       {
/* 25:47 */         resp = claseBodega;
/* 26:48 */         break;
/* 27:   */       }
/* 28:   */     }
/* 29:51 */     return resp;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.ClaseBodegaEnum
 * JD-Core Version:    0.7.0.1
 */