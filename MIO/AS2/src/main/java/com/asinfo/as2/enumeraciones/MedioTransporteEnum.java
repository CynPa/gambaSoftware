/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum MedioTransporteEnum
/*  4:   */ {
/*  5:20 */   AEREO("Aereo"),  TERRESTRE("Terrestre"),  MARITIMO("Maritimo"),  FLUVIAL("Fluvial"),  OTROS("Otros");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private MedioTransporteEnum(String nombre)
/* 10:   */   {
/* 11:25 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:34 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.MedioTransporteEnum
 * JD-Core Version:    0.7.0.1
 */