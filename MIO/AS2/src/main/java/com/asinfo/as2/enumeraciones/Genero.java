/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum Genero
/*  4:   */ {
/*  5:21 */   MASCULINO("Masculino"),  FEMENINO("Femenino");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private Genero(String nombre)
/* 10:   */   {
/* 11:26 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:35 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:45 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static Genero obtenerGenero(String generoString)
/* 25:   */   {
/* 26:55 */     if (generoString.equalsIgnoreCase(MASCULINO.getNombre())) {
/* 27:56 */       return MASCULINO;
/* 28:   */     }
/* 29:58 */     if (generoString.equalsIgnoreCase(FEMENINO.getNombre())) {
/* 30:59 */       return FEMENINO;
/* 31:   */     }
/* 32:61 */     return null;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.Genero
 * JD-Core Version:    0.7.0.1
 */