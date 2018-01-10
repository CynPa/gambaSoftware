/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlRootElement(name="pago")
/*  9:   */ @XmlType(propOrder={"formaPago", "total", "plazo", "unidadTiempo"})
/* 10:   */ public class PagoJaxb
/* 11:   */ {
/* 12:   */   private String formaPago;
/* 13:   */   private BigDecimal total;
/* 14:   */   private BigDecimal plazo;
/* 15:   */   private String unidadTiempo;
/* 16:   */   
/* 17:   */   @XmlElement(name="formaPago")
/* 18:   */   public String getFormaPago()
/* 19:   */   {
/* 20:34 */     return this.formaPago;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setFormaPago(String formaPago)
/* 24:   */   {
/* 25:38 */     this.formaPago = formaPago;
/* 26:   */   }
/* 27:   */   
/* 28:   */   @XmlElement(name="total")
/* 29:   */   public BigDecimal getTotal()
/* 30:   */   {
/* 31:43 */     return this.total;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setTotal(BigDecimal total)
/* 35:   */   {
/* 36:47 */     this.total = total;
/* 37:   */   }
/* 38:   */   
/* 39:   */   @XmlElement(name="plazo")
/* 40:   */   public BigDecimal getPlazo()
/* 41:   */   {
/* 42:52 */     return this.plazo;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setPlazo(BigDecimal plazo)
/* 46:   */   {
/* 47:56 */     this.plazo = plazo;
/* 48:   */   }
/* 49:   */   
/* 50:   */   @XmlElement(name="unidadTiempo")
/* 51:   */   public String getUnidadTiempo()
/* 52:   */   {
/* 53:61 */     return this.unidadTiempo;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setUnidadTiempo(String unidadTiempo)
/* 57:   */   {
/* 58:65 */     this.unidadTiempo = unidadTiempo;
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.PagoJaxb
 * JD-Core Version:    0.7.0.1
 */