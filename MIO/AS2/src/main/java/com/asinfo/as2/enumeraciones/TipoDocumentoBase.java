/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoDocumentoBase
/*  4:   */ {
/*  5:23 */   CLIENTE("Cliente"),  PROVEEDOR("Proveedor"),  INVENTARIO("Inventario"),  INTERNO("Interno"),  AFILIACIONES("Afiliaciones"),  PRODUCCION("Produccion"),  FLORICOLA("Floricola");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoDocumentoBase(String nombre)
/* 10:   */   {
/* 11:35 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:43 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoDocumentoBase
 * JD-Core Version:    0.7.0.1
 */