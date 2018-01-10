/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  7:   */ import javax.xml.bind.annotation.XmlValue;
/*  8:   */ 
/*  9:   */ @XmlAccessorType(XmlAccessType.NONE)
/* 10:   */ @XmlRootElement(name="campoAdicional")
/* 11:   */ public class CampoAdicionalJaxb
/* 12:   */ {
/* 13:   */   private String nombre;
/* 14:   */   private String valor;
/* 15:   */   
/* 16:   */   @XmlAttribute(name="nombre")
/* 17:   */   public String getNombre()
/* 18:   */   {
/* 19:32 */     return this.nombre;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setNombre(String nombre)
/* 23:   */   {
/* 24:36 */     this.nombre = nombre;
/* 25:   */   }
/* 26:   */   
/* 27:   */   @XmlValue
/* 28:   */   public String getValor()
/* 29:   */   {
/* 30:41 */     return this.valor;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setValor(String valor)
/* 34:   */   {
/* 35:45 */     this.valor = valor;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.CampoAdicionalJaxb
 * JD-Core Version:    0.7.0.1
 */