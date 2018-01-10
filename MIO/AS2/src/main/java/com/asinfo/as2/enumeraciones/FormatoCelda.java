/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum FormatoCelda
/*  4:   */ {
/*  5:21 */   TEXTO("Texto"),  FECHA("Fecha"),  NUMERO("Numero"),  COSTO("Costo");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private FormatoCelda(String nombre)
/* 10:   */   {
/* 11:30 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:39 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static FormatoCelda obtenerEstado(String estadoString)
/* 20:   */   {
/* 21:49 */     FormatoCelda estadoAux = null;
/* 22:50 */     for (FormatoCelda estado : values()) {
/* 23:51 */       if (estadoString.equalsIgnoreCase(estado.getNombre())) {
/* 24:52 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:55 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.FormatoCelda
 * JD-Core Version:    0.7.0.1
 */