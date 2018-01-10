/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlElement;
/*   4:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   5:    */ import javax.xml.bind.annotation.XmlType;
/*   6:    */ 
/*   7:    */ @XmlRootElement(name="infoTributaria")
/*   8:    */ @XmlType(propOrder={"ambiente", "tipoEmision", "razonSocial", "nombreComercial", "ruc", "claveAcceso", "codDoc", "estab", "ptoEmi", "secuencial", "dirMatriz"})
/*   9:    */ public class InfoTributariaJaxb
/*  10:    */ {
/*  11:    */   private Integer ambiente;
/*  12:    */   private Integer tipoEmision;
/*  13:    */   private String razonSocial;
/*  14:    */   private String nombreComercial;
/*  15:    */   private String ruc;
/*  16:    */   private String claveAcceso;
/*  17:    */   private String codDoc;
/*  18:    */   private String estab;
/*  19:    */   private String ptoEmi;
/*  20:    */   private String secuencial;
/*  21:    */   private String dirMatriz;
/*  22:    */   
/*  23:    */   @XmlElement(name="ambiente")
/*  24:    */   public Integer getAmbiente()
/*  25:    */   {
/*  26: 40 */     return this.ambiente;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setAmbiente(Integer ambiente)
/*  30:    */   {
/*  31: 44 */     this.ambiente = ambiente;
/*  32:    */   }
/*  33:    */   
/*  34:    */   @XmlElement(name="tipoEmision")
/*  35:    */   public Integer getTipoEmision()
/*  36:    */   {
/*  37: 49 */     return this.tipoEmision;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setTipoEmision(Integer tipoEmision)
/*  41:    */   {
/*  42: 53 */     this.tipoEmision = tipoEmision;
/*  43:    */   }
/*  44:    */   
/*  45:    */   @XmlElement(name="razonSocial")
/*  46:    */   public String getRazonSocial()
/*  47:    */   {
/*  48: 58 */     return this.razonSocial;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setRazonSocial(String razonSocial)
/*  52:    */   {
/*  53: 62 */     this.razonSocial = razonSocial;
/*  54:    */   }
/*  55:    */   
/*  56:    */   @XmlElement(name="nombreComercial")
/*  57:    */   public String getNombreComercial()
/*  58:    */   {
/*  59: 67 */     return this.nombreComercial;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setNombreComercial(String nombreComercial)
/*  63:    */   {
/*  64: 71 */     this.nombreComercial = nombreComercial;
/*  65:    */   }
/*  66:    */   
/*  67:    */   @XmlElement(name="ruc")
/*  68:    */   public String getRuc()
/*  69:    */   {
/*  70: 76 */     return this.ruc;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setRuc(String ruc)
/*  74:    */   {
/*  75: 80 */     this.ruc = ruc;
/*  76:    */   }
/*  77:    */   
/*  78:    */   @XmlElement(name="claveAcceso")
/*  79:    */   public String getClaveAcceso()
/*  80:    */   {
/*  81: 85 */     return this.claveAcceso;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setClaveAcceso(String claveAcceso)
/*  85:    */   {
/*  86: 89 */     this.claveAcceso = claveAcceso;
/*  87:    */   }
/*  88:    */   
/*  89:    */   @XmlElement(name="codDoc")
/*  90:    */   public String getCodDoc()
/*  91:    */   {
/*  92: 94 */     return this.codDoc;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCodDoc(String codDoc)
/*  96:    */   {
/*  97: 98 */     this.codDoc = codDoc;
/*  98:    */   }
/*  99:    */   
/* 100:    */   @XmlElement(name="estab")
/* 101:    */   public String getEstab()
/* 102:    */   {
/* 103:103 */     return this.estab;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setEstab(String estab)
/* 107:    */   {
/* 108:107 */     this.estab = estab;
/* 109:    */   }
/* 110:    */   
/* 111:    */   @XmlElement(name="ptoEmi")
/* 112:    */   public String getPtoEmi()
/* 113:    */   {
/* 114:112 */     return this.ptoEmi;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setPtoEmi(String ptoEmi)
/* 118:    */   {
/* 119:116 */     this.ptoEmi = ptoEmi;
/* 120:    */   }
/* 121:    */   
/* 122:    */   @XmlElement(name="secuencial")
/* 123:    */   public String getSecuencial()
/* 124:    */   {
/* 125:121 */     return this.secuencial;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setSecuencial(String secuencial)
/* 129:    */   {
/* 130:125 */     this.secuencial = secuencial;
/* 131:    */   }
/* 132:    */   
/* 133:    */   @XmlElement(name="dirMatriz")
/* 134:    */   public String getDirMatriz()
/* 135:    */   {
/* 136:130 */     return this.dirMatriz;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDirMatriz(String dirMatriz)
/* 140:    */   {
/* 141:134 */     this.dirMatriz = dirMatriz;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.InfoTributariaJaxb
 * JD-Core Version:    0.7.0.1
 */