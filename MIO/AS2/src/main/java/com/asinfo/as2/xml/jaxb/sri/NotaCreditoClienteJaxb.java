/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlRootElement(name="notaCredito")
/*  9:   */ @XmlType(propOrder={"infoTributaria", "infoNotaCredito", "detallesJaxb", "infoAdicional"})
/* 10:   */ public class NotaCreditoClienteJaxb
/* 11:   */   extends ComprobanteVentaJaxb
/* 12:   */ {
/* 13:   */   private InfoNCreditoJaxb infoNotaCredito;
/* 14:   */   private DetallesJaxb detallesJaxb;
/* 15:   */   
/* 16:   */   @XmlElement(name="infoNotaCredito")
/* 17:   */   public InfoNCreditoJaxb getInfoNotaCredito()
/* 18:   */   {
/* 19:34 */     return this.infoNotaCredito;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setInfoNotaCredito(InfoNCreditoJaxb infoNotaCredito)
/* 23:   */   {
/* 24:38 */     this.infoNotaCredito = infoNotaCredito;
/* 25:   */   }
/* 26:   */   
/* 27:   */   @XmlElement(name="detalles")
/* 28:   */   public DetallesJaxb getDetallesJaxb()
/* 29:   */   {
/* 30:43 */     return this.detallesJaxb;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setDetallesJaxb(DetallesJaxb detalles)
/* 34:   */   {
/* 35:47 */     this.detallesJaxb = detalles;
/* 36:   */   }
/* 37:   */   
/* 38:   */   @XmlAttribute(name="id")
/* 39:   */   public String getId()
/* 40:   */   {
/* 41:53 */     return this.id;
/* 42:   */   }
/* 43:   */   
/* 44:   */   @XmlAttribute(name="version")
/* 45:   */   public String getVersion()
/* 46:   */   {
/* 47:58 */     return this.version;
/* 48:   */   }
/* 49:   */   
/* 50:   */   @XmlElement(name="infoAdicional")
/* 51:   */   public InfoAdicionalJaxb getInfoAdicional()
/* 52:   */   {
/* 53:63 */     return this.infoAdicional;
/* 54:   */   }
/* 55:   */   
/* 56:   */   @XmlElement(name="infoTributaria")
/* 57:   */   public InfoTributariaJaxb getInfoTributaria()
/* 58:   */   {
/* 59:68 */     return this.infoTributaria;
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.NotaCreditoClienteJaxb
 * JD-Core Version:    0.7.0.1
 */