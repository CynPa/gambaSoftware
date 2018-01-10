/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlRootElement(name="motivo")
/*  9:   */ @XmlType(propOrder={"razon", "valor"})
/* 10:   */ public class MotivoJaxb
/* 11:   */ {
/* 12:   */   private String razon;
/* 13:   */   private BigDecimal valor;
/* 14:   */   
/* 15:   */   @XmlElement(name="razon")
/* 16:   */   public String getRazon()
/* 17:   */   {
/* 18:32 */     return this.razon;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setRazon(String razon)
/* 22:   */   {
/* 23:36 */     this.razon = razon;
/* 24:   */   }
/* 25:   */   
/* 26:   */   @XmlElement(name="valor")
/* 27:   */   public BigDecimal getValor()
/* 28:   */   {
/* 29:41 */     return this.valor;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setValor(BigDecimal valor)
/* 33:   */   {
/* 34:45 */     this.valor = valor;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.MotivoJaxb
 * JD-Core Version:    0.7.0.1
 */