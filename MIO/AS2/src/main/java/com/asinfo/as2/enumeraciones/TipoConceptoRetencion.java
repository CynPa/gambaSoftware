/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoConceptoRetencion
/*  4:   */ {
/*  5:21 */   IVA("IVA"),  FUENTE("FUENTE"),  ISD("ISD");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoConceptoRetencion(String nombre)
/* 10:   */   {
/* 11:28 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:36 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static TipoConceptoRetencion obtenerPorNombre(String nombre)
/* 20:   */   {
/* 21:40 */     TipoConceptoRetencion resp = null;
/* 22:41 */     for (TipoConceptoRetencion item : values()) {
/* 23:42 */       if (nombre.equals(item.getNombre()))
/* 24:   */       {
/* 25:43 */         resp = item;
/* 26:44 */         break;
/* 27:   */       }
/* 28:   */     }
/* 29:47 */     return resp;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoConceptoRetencion
 * JD-Core Version:    0.7.0.1
 */