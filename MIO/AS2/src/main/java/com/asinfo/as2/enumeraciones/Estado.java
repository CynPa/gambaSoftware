/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum Estado
/*  4:   */ {
/*  5:23 */   ANULADO("Anulado"),  ELABORADO("Elaborado"),  REVISADO("Revisado"),  APROBADO("Aprobado"),  CONTABILIZADO("Contabilizado"),  PROCESADO("Procesado"),  CERRADO("Cerrado"),  FACTURADO("Facturado"),  REGISTRADO_DATOS("Registrado Datos"),  REGISTRADO_REQUISITOS("Registrado Requisitos"),  EMITIDO_CONTRATO("Emitido Contrato"),  CONTRATO_CANCELADO("Contrato Cancelado"),  SIN_ESTADO(" "),  EN_ESPERA("En espera respuesta SRI"),  APROBADO_PARCIAL("Aprobado Parcial"),  EN_ESPERA_CONTINGENCIA("No enviado al SRI"),  RECHAZADO_SRI("Rechazado por el SRI"),  DESPACHADO("Despachado"),  REALIZADO_LOGISTICA("Realizado Logistica");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private Estado(String nombre)
/* 10:   */   {
/* 11:47 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:56 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static Estado obtenerEstado(String estadoString)
/* 20:   */   {
/* 21:66 */     Estado estadoAux = null;
/* 22:67 */     for (Estado estado : values()) {
/* 23:68 */       if ((estado.getNombre().equalsIgnoreCase(estadoString)) || (estado.name().equals(estadoString))) {
/* 24:69 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:72 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.Estado
 * JD-Core Version:    0.7.0.1
 */