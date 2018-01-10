/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.xml.bind.annotation.XmlElement;
/*  6:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  7:   */ 
/*  8:   */ @XmlRootElement(name="infoAdicional")
/*  9:   */ public class InfoAdicionalJaxb
/* 10:   */ {
/* 11:   */   private List<CampoAdicionalJaxb> listaCampoAdicional;
/* 12:   */   
/* 13:   */   @XmlElement(name="campoAdicional")
/* 14:   */   public List<CampoAdicionalJaxb> getlListaCampoAdicional()
/* 15:   */   {
/* 16:30 */     if (this.listaCampoAdicional == null) {
/* 17:31 */       this.listaCampoAdicional = new ArrayList();
/* 18:   */     }
/* 19:33 */     return this.listaCampoAdicional;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setListaCampoAdicional(List<CampoAdicionalJaxb> listaCampoAdicional)
/* 23:   */   {
/* 24:37 */     this.listaCampoAdicional = listaCampoAdicional;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.InfoAdicionalJaxb
 * JD-Core Version:    0.7.0.1
 */