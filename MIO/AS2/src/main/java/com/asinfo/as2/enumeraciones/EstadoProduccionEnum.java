/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum EstadoProduccionEnum
/*  4:   */ {
/*  5:22 */   ANULADO("Anulado"),  PLANEADA("Planeada"),  LANZADA("Lanzada"),  INICIADA("Iniciada"),  SUSPENDIDA("Suspendida"),  FINALIZADA("Finalizada"),  ENVIADA("Enviada");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private EstadoProduccionEnum(String nombre)
/* 10:   */   {
/* 11:33 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:42 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static EstadoProduccionEnum obtenerEstadoProduccion(String estadoString)
/* 20:   */   {
/* 21:52 */     EstadoProduccionEnum estadoAux = null;
/* 22:53 */     for (EstadoProduccionEnum estado : values()) {
/* 23:54 */       if (estadoString.equals(estado.getNombre())) {
/* 24:55 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:58 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.EstadoProduccionEnum
 * JD-Core Version:    0.7.0.1
 */