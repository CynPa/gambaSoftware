/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.CatalogosACopiarEnum;
/*  4:   */ 
/*  5:   */ public class CatalogosACopiar
/*  6:   */ {
/*  7:   */   private CatalogosACopiarEnum catalogosACopiar;
/*  8:   */   private boolean indicadorCopiar;
/*  9:   */   
/* 10:   */   public CatalogosACopiar() {}
/* 11:   */   
/* 12:   */   public CatalogosACopiar(CatalogosACopiarEnum catalogosACopiar, boolean indicadorCopiar)
/* 13:   */   {
/* 14:41 */     this.catalogosACopiar = catalogosACopiar;
/* 15:42 */     this.indicadorCopiar = indicadorCopiar;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CatalogosACopiarEnum getCatalogosACopiar()
/* 19:   */   {
/* 20:53 */     return this.catalogosACopiar;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setCatalogosACopiar(CatalogosACopiarEnum catalogosACopiar)
/* 24:   */   {
/* 25:63 */     this.catalogosACopiar = catalogosACopiar;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isIndicadorCopiar()
/* 29:   */   {
/* 30:72 */     return this.indicadorCopiar;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setIndicadorCopiar(boolean indicadorCopiar)
/* 34:   */   {
/* 35:82 */     this.indicadorCopiar = indicadorCopiar;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.CatalogosACopiar
 * JD-Core Version:    0.7.0.1
 */