/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ public abstract class ComprobanteVentaJaxb
/*  4:   */ {
/*  5:20 */   protected String id = "comprobante";
/*  6:21 */   protected String version = "1.0.0";
/*  7:   */   protected InfoTributariaJaxb infoTributaria;
/*  8:   */   protected InfoAdicionalJaxb infoAdicional;
/*  9:   */   
/* 10:   */   public abstract String getId();
/* 11:   */   
/* 12:   */   public void setId(String id)
/* 13:   */   {
/* 14:31 */     this.id = id;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public abstract String getVersion();
/* 18:   */   
/* 19:   */   public void setVersion(String version)
/* 20:   */   {
/* 21:37 */     this.version = version;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public abstract InfoAdicionalJaxb getInfoAdicional();
/* 25:   */   
/* 26:   */   public void setInfoAdicional(InfoAdicionalJaxb infoAdicional)
/* 27:   */   {
/* 28:43 */     this.infoAdicional = infoAdicional;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public abstract InfoTributariaJaxb getInfoTributaria();
/* 32:   */   
/* 33:   */   public void setInfoTributaria(InfoTributariaJaxb infoTributaria)
/* 34:   */   {
/* 35:49 */     this.infoTributaria = infoTributaria;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.ComprobanteVentaJaxb
 * JD-Core Version:    0.7.0.1
 */