/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoUnidadMedida
/*  4:   */ {
/*  5:21 */   LONGITUD("Longitud"),  MASA("Masa"),  VOLUMEN("Volumen"),  SUPERFICIE("Superficie"),  OTROS("Otros");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoUnidadMedida(String nombre)
/* 10:   */   {
/* 11:33 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:42 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static TipoUnidadMedida obtieneTipoUnidadMedida(String tipoUnidadMedida)
/* 20:   */   {
/* 21:52 */     TipoUnidadMedida tipoUnidadMedidaAux = null;
/* 22:53 */     for (TipoUnidadMedida tum : values()) {
/* 23:54 */       if (tipoUnidadMedida.equals(tum.getNombre()))
/* 24:   */       {
/* 25:55 */         tipoUnidadMedidaAux = tum;
/* 26:56 */         break;
/* 27:   */       }
/* 28:   */     }
/* 29:59 */     return tipoUnidadMedidaAux;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoUnidadMedida
 * JD-Core Version:    0.7.0.1
 */