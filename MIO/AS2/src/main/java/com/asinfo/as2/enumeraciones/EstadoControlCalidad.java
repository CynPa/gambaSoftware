/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum EstadoControlCalidad
/*  4:   */ {
/*  5:11 */   PENDIENTE("Pendiente"),  EN_ESPERA("En Espera"),  LIBERADO("Liberado"),  RECHAZADO("Rechazado"),  CUARENTENA("Cuarentena"),  REPROCESO("Reproceso"),  ESPERA_CASTIGO("En espera Confirmacion Castigo");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private EstadoControlCalidad(String nombre)
/* 10:   */   {
/* 11:23 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:32 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static EstadoControlCalidad obtenerEstado(String estadoString)
/* 20:   */   {
/* 21:42 */     EstadoControlCalidad estadoAux = null;
/* 22:43 */     for (EstadoControlCalidad estado : values()) {
/* 23:44 */       if (estadoString.equalsIgnoreCase(estado.getNombre())) {
/* 24:45 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:48 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.EstadoControlCalidad
 * JD-Core Version:    0.7.0.1
 */