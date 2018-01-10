/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.NONE)
/*  9:   */ @XmlRootElement(name="detAdicional")
/* 10:   */ public class DetAdicionalJaxb
/* 11:   */ {
/* 12:   */   private String nombre;
/* 13:   */   private String valor;
/* 14:   */   
/* 15:   */   @XmlAttribute(name="nombre")
/* 16:   */   public String getNombre()
/* 17:   */   {
/* 18:32 */     return this.nombre;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setNombre(String nombre)
/* 22:   */   {
/* 23:36 */     this.nombre = nombre;
/* 24:   */   }
/* 25:   */   
/* 26:   */   @XmlAttribute(name="valor")
/* 27:   */   public String getValor()
/* 28:   */   {
/* 29:41 */     return this.valor;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setValor(String valor)
/* 33:   */   {
/* 34:45 */     this.valor = valor;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.DetAdicionalJaxb
 * JD-Core Version:    0.7.0.1
 */