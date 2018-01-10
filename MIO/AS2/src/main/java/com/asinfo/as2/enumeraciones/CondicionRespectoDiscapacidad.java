/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum CondicionRespectoDiscapacidad
/*  4:   */ {
/*  5:20 */   NO_APLICA("01", "No aplica"),  DISCAPACIDAD("02", "Trabajador con discapacidad"),  SUSTITUTO("03", "Trabajador que actua en calidad de sustituto de una persona con discapacidad"),  PARIENTE_DISCAPACIDAD("04", "Trabajador tiene cónyuge, pareja en unión de hecho o hijo con discapacidad y se encuentra bajo su cuidado y/o responsabilidad");
/*  6:   */   
/*  7:   */   private String codigo;
/*  8:   */   private String nombre;
/*  9:   */   
/* 10:   */   private CondicionRespectoDiscapacidad(String codigo, String nombre)
/* 11:   */   {
/* 12:29 */     this.codigo = codigo;
/* 13:30 */     this.nombre = nombre;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getCodigo()
/* 17:   */   {
/* 18:39 */     return this.codigo;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getNombre()
/* 22:   */   {
/* 23:48 */     return this.nombre;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.CondicionRespectoDiscapacidad
 * JD-Core Version:    0.7.0.1
 */