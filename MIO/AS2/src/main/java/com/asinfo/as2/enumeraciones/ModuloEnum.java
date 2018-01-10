/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ModuloEnum
/*  4:   */ {
/*  5:23 */   COMPROBANTE_ELECTRONICO("Comprobante Electronico "),  CONFIGURACION_BASE("Configuracion Base"),  DATOS_BASE("Datos Base"),  GESTION_FINANCIERA("Gestion Financiera"),  INVENTARIO("Inventario"),  VENTAS("Ventas"),  COMPRAS("Compras"),  NOMINA("Nomina"),  SEGURIDAD("Seguridad"),  CAJA("Caja"),  FLORICOLA("Floricola"),  AFILIACIONES("Afiliaciones"),  PRODUCCION("Produccion"),  AYUDA("Ayuda");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private ModuloEnum(String nombre)
/* 10:   */   {
/* 11:42 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:51 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static ModuloEnum obtenerModulo(String estadoString)
/* 20:   */   {
/* 21:61 */     ModuloEnum estadoAux = null;
/* 22:62 */     for (ModuloEnum estado : values()) {
/* 23:63 */       if (estadoString.equalsIgnoreCase(estado.getNombre())) {
/* 24:64 */         estadoAux = estado;
/* 25:   */       }
/* 26:   */     }
/* 27:67 */     return estadoAux;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.ModuloEnum
 * JD-Core Version:    0.7.0.1
 */