/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoReporteAsiento
/*  4:   */ {
/*  5:23 */   INGRESO("Ingreso"),  EGRESO("Egreso"),  DIARIO("Diario"),  DIARIO_R("Diario-R");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoReporteAsiento(String nombre)
/* 10:   */   {
/* 11:29 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:40 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static TipoReporteAsiento obtenerTipoReporteAsiento(String tipoReporteAsientoString)
/* 20:   */   {
/* 21:50 */     TipoReporteAsiento tipoReporteAsientoAux = null;
/* 22:51 */     for (TipoReporteAsiento tipoReporteAsiento : values()) {
/* 23:52 */       if (tipoReporteAsientoString.equals(tipoReporteAsiento.getNombre()))
/* 24:   */       {
/* 25:53 */         tipoReporteAsientoAux = tipoReporteAsiento;
/* 26:54 */         break;
/* 27:   */       }
/* 28:   */     }
/* 29:57 */     return tipoReporteAsientoAux;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoReporteAsiento
 * JD-Core Version:    0.7.0.1
 */