/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum PrioridadEnum
/*  4:   */ {
/*  5:11 */   ALTA("Alta"),  MEDIA("Media"),  BAJA("Baja");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private PrioridadEnum(String nombre)
/* 10:   */   {
/* 11:19 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:28 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static PrioridadEnum obtenerEstado(String estadoString)
/* 20:   */   {
/* 21:38 */     PrioridadEnum estadoAux = null;
/* 22:39 */     for (PrioridadEnum estado : values()) {
/* 23:40 */       if (estadoString.equalsIgnoreCase(estado.getNombre())) {
/* 24:41 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:44 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.PrioridadEnum
 * JD-Core Version:    0.7.0.1
 */