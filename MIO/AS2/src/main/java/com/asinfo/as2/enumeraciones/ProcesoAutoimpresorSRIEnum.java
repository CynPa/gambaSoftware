/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ProcesoAutoimpresorSRIEnum
/*  4:   */ {
/*  5:21 */   AUTORIZACION("Autorización", "6"),  INCLUSION("Inclusión", "10"),  EXCLUSION("Exclusión", "11"),  RENOVACION("Renovación", "8"),  CAMBIO_SOFTWARE("Cambio de Software", "7"),  BAJA("Baja", "9");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   private String codigo;
/*  9:   */   
/* 10:   */   private ProcesoAutoimpresorSRIEnum(String nombre, String codigo)
/* 11:   */   {
/* 12:32 */     this.nombre = nombre;
/* 13:33 */     this.codigo = codigo;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getNombre()
/* 17:   */   {
/* 18:42 */     return this.nombre;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getCodigo()
/* 22:   */   {
/* 23:46 */     return this.codigo;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum
 * JD-Core Version:    0.7.0.1
 */