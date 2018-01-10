/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum PorCientoHoraExtra
/*  4:   */ {
/*  5:21 */   CERO("0 %"),  VEINTICINCO("25 %"),  CINCUENTA("50 %"),  CIEN("100 %");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private PorCientoHoraExtra(String nombre)
/* 10:   */   {
/* 11:29 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:38 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static PorCientoHoraExtra obtenerPorCientoHoraExtra(String porCientoHoraExtraString)
/* 20:   */   {
/* 21:42 */     PorCientoHoraExtra porCientoHoraExtraAux = null;
/* 22:43 */     for (PorCientoHoraExtra porCientoHoraExtra : values()) {
/* 23:44 */       if (porCientoHoraExtraString.equals(porCientoHoraExtra.toString()))
/* 24:   */       {
/* 25:45 */         porCientoHoraExtraAux = porCientoHoraExtra;
/* 26:46 */         break;
/* 27:   */       }
/* 28:   */     }
/* 29:49 */     return porCientoHoraExtraAux;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.PorCientoHoraExtra
 * JD-Core Version:    0.7.0.1
 */