/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ 
/*  7:   */ @XmlRootElement(name="impuestos")
/*  8:   */ public class ImpuestosJaxb
/*  9:   */ {
/* 10:   */   private List<ImpuestoJaxb> listaImpuesto;
/* 11:   */   
/* 12:   */   @XmlElement(name="impuesto")
/* 13:   */   public List<ImpuestoJaxb> getListaImpuesto()
/* 14:   */   {
/* 15:29 */     return this.listaImpuesto;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setListaImpuesto(List<ImpuestoJaxb> listaImpuesto)
/* 19:   */   {
/* 20:33 */     this.listaImpuesto = listaImpuesto;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.ImpuestosJaxb
 * JD-Core Version:    0.7.0.1
 */