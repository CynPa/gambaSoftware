/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlRootElement(name="comprobanteRetencion")
/*  9:   */ @XmlType(propOrder={"infoTributaria", "infoCompRetencion", "impuestos", "infoAdicional"})
/* 10:   */ public class RetencionProveedorJaxb
/* 11:   */ {
/* 12:27 */   private String id = "comprobante";
/* 13:28 */   private String version = "1.0.0";
/* 14:   */   private InfoTributariaJaxb infoTributaria;
/* 15:   */   private InfoFacturaJaxb infoCompRetencion;
/* 16:   */   private ImpuestosJaxb impuestos;
/* 17:   */   private InfoAdicionalJaxb infoAdicional;
/* 18:   */   
/* 19:   */   @XmlAttribute(name="id")
/* 20:   */   public String getId()
/* 21:   */   {
/* 22:39 */     return this.id;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setId(String id)
/* 26:   */   {
/* 27:43 */     this.id = id;
/* 28:   */   }
/* 29:   */   
/* 30:   */   @XmlAttribute(name="version")
/* 31:   */   public String getVersion()
/* 32:   */   {
/* 33:48 */     return this.version;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setVersion(String version)
/* 37:   */   {
/* 38:52 */     this.version = version;
/* 39:   */   }
/* 40:   */   
/* 41:   */   @XmlElement(name="infoTributaria")
/* 42:   */   public InfoTributariaJaxb getInfoTributaria()
/* 43:   */   {
/* 44:57 */     return this.infoTributaria;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setInfoTributaria(InfoTributariaJaxb infoTributaria)
/* 48:   */   {
/* 49:61 */     this.infoTributaria = infoTributaria;
/* 50:   */   }
/* 51:   */   
/* 52:   */   @XmlElement(name="infoCompRetencion")
/* 53:   */   public InfoFacturaJaxb getInfoCompRetencion()
/* 54:   */   {
/* 55:66 */     return this.infoCompRetencion;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void setInfoCompRetencion(InfoFacturaJaxb infoCompRetencion)
/* 59:   */   {
/* 60:70 */     this.infoCompRetencion = infoCompRetencion;
/* 61:   */   }
/* 62:   */   
/* 63:   */   @XmlElement(name="impuestos")
/* 64:   */   public ImpuestosJaxb getImpuestos()
/* 65:   */   {
/* 66:75 */     return this.impuestos;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void setImpuestos(ImpuestosJaxb impuestos)
/* 70:   */   {
/* 71:79 */     this.impuestos = impuestos;
/* 72:   */   }
/* 73:   */   
/* 74:   */   @XmlElement(name="infoAdicional")
/* 75:   */   public InfoAdicionalJaxb getInfoAdicional()
/* 76:   */   {
/* 77:84 */     return this.infoAdicional;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void setInfoAdicional(InfoAdicionalJaxb infoAdicional)
/* 81:   */   {
/* 82:88 */     this.infoAdicional = infoAdicional;
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.RetencionProveedorJaxb
 * JD-Core Version:    0.7.0.1
 */