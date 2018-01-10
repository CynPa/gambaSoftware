/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum RefrendoEnum
/*  4:   */ {
/*  5:22 */   NO_ESCOGER("No escoger", "00"),  CON_REFRENDO("Con refrendo", "01"),  SIN_REFRENDO("Sin refrendo", "02"),  EXPORTACION_SERVICIOS("Exportación de Servicios (incluye activos intangibles)", "03");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   private String codigo;
/*  9:   */   
/* 10:   */   private RefrendoEnum(String nombre, String codigo)
/* 11:   */   {
/* 12:28 */     this.nombre = nombre;
/* 13:29 */     this.codigo = codigo;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static RefrendoEnum buscarPorCodigo(String codigo)
/* 17:   */   {
/* 18:42 */     for (RefrendoEnum refrendoEnum : ) {
/* 19:43 */       if (refrendoEnum.getCodigo().equals(codigo)) {
/* 20:44 */         return refrendoEnum;
/* 21:   */       }
/* 22:   */     }
/* 23:47 */     return null;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getNombre()
/* 27:   */   {
/* 28:56 */     return this.nombre;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setNombre(String nombre)
/* 32:   */   {
/* 33:66 */     this.nombre = nombre;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getCodigo()
/* 37:   */   {
/* 38:75 */     return this.codigo;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setCodigo(String codigo)
/* 42:   */   {
/* 43:85 */     this.codigo = codigo;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.RefrendoEnum
 * JD-Core Version:    0.7.0.1
 */