/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlRootElement(name="compensacion")
/*  9:   */ @XmlType(propOrder={"codigo", "tarifa", "valor"})
/* 10:   */ public class CompensacionJaxb
/* 11:   */ {
/* 12:   */   private String codigo;
/* 13:   */   private BigDecimal valor;
/* 14:   */   private BigDecimal tarifa;
/* 15:   */   
/* 16:   */   @XmlElement(name="codigo")
/* 17:   */   public String getCodigo()
/* 18:   */   {
/* 19:33 */     return this.codigo;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setCodigo(String codigo)
/* 23:   */   {
/* 24:37 */     this.codigo = codigo;
/* 25:   */   }
/* 26:   */   
/* 27:   */   @XmlElement(name="valor")
/* 28:   */   public BigDecimal getValor()
/* 29:   */   {
/* 30:42 */     return this.valor;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setValor(BigDecimal valor)
/* 34:   */   {
/* 35:46 */     this.valor = valor;
/* 36:   */   }
/* 37:   */   
/* 38:   */   @XmlElement(name="tarifa")
/* 39:   */   public BigDecimal getTarifa()
/* 40:   */   {
/* 41:51 */     return this.tarifa;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setTarifa(BigDecimal tarifa)
/* 45:   */   {
/* 46:55 */     this.tarifa = tarifa;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.CompensacionJaxb
 * JD-Core Version:    0.7.0.1
 */