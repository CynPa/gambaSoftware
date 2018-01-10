/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ 
/*  7:   */ @XmlRootElement(name="totalConImpuestos")
/*  8:   */ public class TotalConImpuestosJaxb
/*  9:   */ {
/* 10:   */   private List<ImpuestoJaxb> listaTotalImpuesto;
/* 11:   */   
/* 12:   */   @XmlElement(name="totalImpuesto")
/* 13:   */   public List<ImpuestoJaxb> getListaTotalImpuesto()
/* 14:   */   {
/* 15:28 */     return this.listaTotalImpuesto;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setListaTotalImpuesto(List<ImpuestoJaxb> listaTotalImpuesto)
/* 19:   */   {
/* 20:32 */     this.listaTotalImpuesto = listaTotalImpuesto;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.TotalConImpuestosJaxb
 * JD-Core Version:    0.7.0.1
 */