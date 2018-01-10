/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ResidenciaTrabajador
/*  4:   */ {
/*  5:20 */   LOCAL("01", "Residente Local"),  EXTERIOR("02", "Residente del Exterior");
/*  6:   */   
/*  7:   */   private String codigo;
/*  8:   */   private String nombre;
/*  9:   */   
/* 10:   */   private ResidenciaTrabajador(String codigo, String nombre)
/* 11:   */   {
/* 12:32 */     this.codigo = codigo;
/* 13:33 */     this.nombre = nombre;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getCodigo()
/* 17:   */   {
/* 18:42 */     return this.codigo;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getNombre()
/* 22:   */   {
/* 23:51 */     return this.nombre;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.ResidenciaTrabajador
 * JD-Core Version:    0.7.0.1
 */