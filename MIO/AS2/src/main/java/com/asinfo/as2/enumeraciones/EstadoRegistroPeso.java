/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum EstadoRegistroPeso
/*  4:   */ {
/*  5:21 */   EN_ESPERA("En Espera"),  PRIMER_PESO("Primer Peso"),  SEGUNDO_PESO("Segundo Peso");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private EstadoRegistroPeso(String nombre)
/* 10:   */   {
/* 11:29 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:38 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static EstadoRegistroPeso obtenerEstado(String estadoString)
/* 20:   */   {
/* 21:48 */     EstadoRegistroPeso estadoAux = null;
/* 22:49 */     for (EstadoRegistroPeso estado : values()) {
/* 23:50 */       if (estadoString.equalsIgnoreCase(estado.getNombre())) {
/* 24:51 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:54 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.EstadoRegistroPeso
 * JD-Core Version:    0.7.0.1
 */