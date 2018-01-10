/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoIdentificacionSRI
/*  4:   */ {
/*  5:20 */   CEDULA("C", "Cedula"),  IDENTIFICACION_TRIBUTARIA_EXTERIOR("E", "Identificacion tributaria del exterior"),  PASAPORTE("P", "Pasaporte");
/*  6:   */   
/*  7:   */   private String codigo;
/*  8:   */   private String nombre;
/*  9:   */   
/* 10:   */   private TipoIdentificacionSRI(String codigo, String nombre)
/* 11:   */   {
/* 12:26 */     this.codigo = codigo;
/* 13:27 */     this.nombre = nombre;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getCodigo()
/* 17:   */   {
/* 18:36 */     return this.codigo;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getNombre()
/* 22:   */   {
/* 23:45 */     return this.nombre;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoIdentificacionSRI
 * JD-Core Version:    0.7.0.1
 */