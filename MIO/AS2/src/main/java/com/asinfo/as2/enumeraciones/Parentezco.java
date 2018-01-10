/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum Parentezco
/*  4:   */ {
/*  5:21 */   CONYUGE("Conyuge"),  PADRE("Padre / Madre"),  HIJO("Hijo /a"),  SOBRINO("Sobrino / a"),  NIETO("Nieto / a"),  HERMANO("Hermano / a"),  TIO("Tio / a"),  YERNO("Yerno / Nuera"),  PRIMO("Primo / a"),  SUEGRO("Suegro / a"),  ABUELO("Abuelo / a"),  TITULAR("Titular"),  OTRO("Otro / a");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private Parentezco(String nombre)
/* 10:   */   {
/* 11:38 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:47 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static Parentezco obtenerParentesco(String parentezcoString)
/* 20:   */   {
/* 21:58 */     if (parentezcoString.equalsIgnoreCase(HIJO.getNombre())) {
/* 22:59 */       return HIJO;
/* 23:   */     }
/* 24:61 */     if (parentezcoString.equalsIgnoreCase(CONYUGE.getNombre())) {
/* 25:62 */       return CONYUGE;
/* 26:   */     }
/* 27:64 */     if (parentezcoString.equalsIgnoreCase(HERMANO.getNombre())) {
/* 28:65 */       return HERMANO;
/* 29:   */     }
/* 30:67 */     if (parentezcoString.equalsIgnoreCase(PRIMO.getNombre())) {
/* 31:68 */       return PRIMO;
/* 32:   */     }
/* 33:70 */     if (parentezcoString.equalsIgnoreCase(TIO.getNombre())) {
/* 34:71 */       return TIO;
/* 35:   */     }
/* 36:74 */     return OTRO;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.Parentezco
 * JD-Core Version:    0.7.0.1
 */