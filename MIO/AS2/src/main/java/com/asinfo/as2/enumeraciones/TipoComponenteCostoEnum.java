/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoComponenteCostoEnum
/*  4:   */ {
/*  5:23 */   MATERIA_PRIMA("Costos de Materia Prima"),  MANO_OBRA("Costos de Mano de Obra"),  DEPRECIACION("Costos de Depreciacion"),  INDIRECTO("Costos Indirectos");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoComponenteCostoEnum(String nombre)
/* 10:   */   {
/* 11:29 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:40 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoComponenteCostoEnum
 * JD-Core Version:    0.7.0.1
 */