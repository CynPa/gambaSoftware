/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum EstadoSolicitudCompraEnum
/*  4:   */ {
/*  5:19 */   ANULADO("Anulado"),  ELABORADO("Elaborado"),  CERRADO("Cerrado"),  CONSOLIDADO("Consolidado"),  CONSOLIDADO_PARCIAL("Consolidado Parcial"),  APROBADO("Aprobado");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private EstadoSolicitudCompraEnum(String nombre)
/* 10:   */   {
/* 11:32 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:41 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static EstadoSolicitudCompraEnum obtenerEstadoSolicitudCompra(String estadoString)
/* 20:   */   {
/* 21:51 */     EstadoSolicitudCompraEnum estadoAux = null;
/* 22:52 */     for (EstadoSolicitudCompraEnum estado : values()) {
/* 23:53 */       if (estadoString.equals(estado.getNombre())) {
/* 24:54 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:57 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum
 * JD-Core Version:    0.7.0.1
 */