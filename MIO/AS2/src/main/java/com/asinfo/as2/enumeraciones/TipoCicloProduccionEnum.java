/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoCicloProduccionEnum
/*  4:   */ {
/*  5:21 */   CICLO_CORTO("Ciclo Corto"),  CICLO_LARGO("Ciclo Largo");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoCicloProduccionEnum(String nombre)
/* 10:   */   {
/* 11:26 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:30 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:34 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static TipoCicloProduccionEnum obtenerTipoCicloProduccion(String tipoCicloProduccionEnumString)
/* 25:   */   {
/* 26:44 */     TipoCicloProduccionEnum tipoCicloProduccionAux = null;
/* 27:45 */     for (TipoCicloProduccionEnum tipoCicloProduccion : values()) {
/* 28:46 */       if (tipoCicloProduccion.name().equalsIgnoreCase(tipoCicloProduccionEnumString)) {
/* 29:47 */         tipoCicloProduccionAux = tipoCicloProduccion;
/* 30:   */       }
/* 31:   */     }
/* 32:50 */     return tipoCicloProduccionAux;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum
 * JD-Core Version:    0.7.0.1
 */