/*  1:   */ package com.asinfo.as2.compronteselectronicos.base;
/*  2:   */ 
/*  3:   */ public enum TipoDocumentoElectronicoEnum
/*  4:   */ {
/*  5:20 */   FACTURA("01", "Factura"),  NOTA_CREDITO("04", "Nota Credito"),  NOTA_DEBITO("05", "Nota Debito"),  GUIA_REMISION("06", "Guia Remision"),  RETENCION("07", "Retencion");
/*  6:   */   
/*  7:   */   private String codigo;
/*  8:   */   private String nombre;
/*  9:   */   
/* 10:   */   private TipoDocumentoElectronicoEnum(String codigo, String nombre)
/* 11:   */   {
/* 12:24 */     this.codigo = codigo;
/* 13:25 */     this.nombre = nombre;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getCodigo()
/* 17:   */   {
/* 18:32 */     return this.codigo;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setCodigo(String codigo)
/* 22:   */   {
/* 23:36 */     this.codigo = codigo;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getNombre()
/* 27:   */   {
/* 28:40 */     return this.nombre;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setNombre(String nombre)
/* 32:   */   {
/* 33:44 */     this.nombre = nombre;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum
 * JD-Core Version:    0.7.0.1
 */