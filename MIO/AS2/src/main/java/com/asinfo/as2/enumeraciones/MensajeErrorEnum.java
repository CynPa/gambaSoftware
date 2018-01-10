/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum MensajeErrorEnum
/*  4:   */ {
/*  5:18 */   ERROR_101("101", "Ya existe el elemento"),  ERROR_201("201", "No existe el elemento"),  ERROR_301("301", "Tipo de datos del campo no válido"),  ERROR_302("302", "Falta campo requerido"),  ERROR_401("401", "Error de facturación Electrónica"),  ERROR_501("501", "Error interno");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   private String codigo;
/*  9:   */   
/* 10:   */   private MensajeErrorEnum(String nombre)
/* 11:   */   {
/* 12:29 */     this.nombre = nombre;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getNombre()
/* 16:   */   {
/* 17:38 */     return this.nombre;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getCodigo()
/* 21:   */   {
/* 22:42 */     return this.codigo;
/* 23:   */   }
/* 24:   */   
/* 25:   */   private MensajeErrorEnum(String codigo, String nombre)
/* 26:   */   {
/* 27:46 */     this.nombre = nombre;
/* 28:47 */     this.codigo = codigo;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.MensajeErrorEnum
 * JD-Core Version:    0.7.0.1
 */