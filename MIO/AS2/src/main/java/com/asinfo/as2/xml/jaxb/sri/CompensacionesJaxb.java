/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ 
/*  7:   */ @XmlRootElement(name="compensaciones")
/*  8:   */ public class CompensacionesJaxb
/*  9:   */ {
/* 10:   */   private List<CompensacionJaxb> listaCompensacionJaxb;
/* 11:   */   
/* 12:   */   @XmlElement(name="compensacion")
/* 13:   */   public List<CompensacionJaxb> getListaCompensacionJaxb()
/* 14:   */   {
/* 15:29 */     return this.listaCompensacionJaxb;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setListaCompensacionJaxb(List<CompensacionJaxb> listaCompensacionJaxb)
/* 19:   */   {
/* 20:33 */     this.listaCompensacionJaxb = listaCompensacionJaxb;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.CompensacionesJaxb
 * JD-Core Version:    0.7.0.1
 */