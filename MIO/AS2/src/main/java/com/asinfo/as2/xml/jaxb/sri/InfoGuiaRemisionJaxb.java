/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlElement;
/*   4:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   5:    */ import javax.xml.bind.annotation.XmlType;
/*   6:    */ 
/*   7:    */ @XmlRootElement(name="infoGuiaRemision")
/*   8:    */ @XmlType(propOrder={"dirEstablecimiento", "dirPartida", "razonSocialTransportista", "tipoIdentificacionTransportista", "rucTransportista", "obligadoContabilidad", "contribuyenteEspecial", "fechaIniTransporte", "fechaFinTransporte", "placa"})
/*   9:    */ public class InfoGuiaRemisionJaxb
/*  10:    */ {
/*  11:    */   private String dirEstablecimiento;
/*  12:    */   private String dirPartida;
/*  13:    */   private String razonSocialTransportista;
/*  14:    */   private String tipoIdentificacionTransportista;
/*  15:    */   private String rucTransportista;
/*  16:    */   private String obligadoContabilidad;
/*  17:    */   private String contribuyenteEspecial;
/*  18:    */   private String fechaIniTransporte;
/*  19:    */   private String fechaFinTransporte;
/*  20:    */   private String placa;
/*  21:    */   
/*  22:    */   @XmlElement(name="dirEstablecimiento")
/*  23:    */   public String getDirEstablecimiento()
/*  24:    */   {
/*  25: 41 */     return this.dirEstablecimiento;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setDirEstablecimiento(String dirEstablecimiento)
/*  29:    */   {
/*  30: 45 */     this.dirEstablecimiento = dirEstablecimiento;
/*  31:    */   }
/*  32:    */   
/*  33:    */   @XmlElement(name="dirPartida")
/*  34:    */   public String getDirPartida()
/*  35:    */   {
/*  36: 50 */     return this.dirPartida;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setDirPartida(String dirPartida)
/*  40:    */   {
/*  41: 54 */     this.dirPartida = dirPartida;
/*  42:    */   }
/*  43:    */   
/*  44:    */   @XmlElement(name="razonSocialTransportista")
/*  45:    */   public String getRazonSocialTransportista()
/*  46:    */   {
/*  47: 59 */     return this.razonSocialTransportista;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setRazonSocialTransportista(String razonSocialTransportista)
/*  51:    */   {
/*  52: 63 */     this.razonSocialTransportista = razonSocialTransportista;
/*  53:    */   }
/*  54:    */   
/*  55:    */   @XmlElement(name="tipoIdentificacionTransportista")
/*  56:    */   public String getTipoIdentificacionTransportista()
/*  57:    */   {
/*  58: 68 */     return this.tipoIdentificacionTransportista;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setTipoIdentificacionTransportista(String tipoIdentificacionTransportista)
/*  62:    */   {
/*  63: 72 */     this.tipoIdentificacionTransportista = tipoIdentificacionTransportista;
/*  64:    */   }
/*  65:    */   
/*  66:    */   @XmlElement(name="rucTransportista")
/*  67:    */   public String getRucTransportista()
/*  68:    */   {
/*  69: 77 */     return this.rucTransportista;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setRucTransportista(String rucTransportista)
/*  73:    */   {
/*  74: 81 */     this.rucTransportista = rucTransportista;
/*  75:    */   }
/*  76:    */   
/*  77:    */   @XmlElement(name="obligadoContabilidad")
/*  78:    */   public String getObligadoContabilidad()
/*  79:    */   {
/*  80: 86 */     return this.obligadoContabilidad;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setObligadoContabilidad(String obligadoContabilidad)
/*  84:    */   {
/*  85: 90 */     this.obligadoContabilidad = obligadoContabilidad;
/*  86:    */   }
/*  87:    */   
/*  88:    */   @XmlElement(name="contribuyenteEspecial")
/*  89:    */   public String getContribuyenteEspecial()
/*  90:    */   {
/*  91: 95 */     return this.contribuyenteEspecial;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setContribuyenteEspecial(String contribuyenteEspecial)
/*  95:    */   {
/*  96: 99 */     this.contribuyenteEspecial = contribuyenteEspecial;
/*  97:    */   }
/*  98:    */   
/*  99:    */   @XmlElement(name="fechaIniTransporte")
/* 100:    */   public String getFechaIniTransporte()
/* 101:    */   {
/* 102:104 */     return this.fechaIniTransporte;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setFechaIniTransporte(String fechaIniTransporte)
/* 106:    */   {
/* 107:108 */     this.fechaIniTransporte = fechaIniTransporte;
/* 108:    */   }
/* 109:    */   
/* 110:    */   @XmlElement(name="fechaFinTransporte")
/* 111:    */   public String getFechaFinTransporte()
/* 112:    */   {
/* 113:113 */     return this.fechaFinTransporte;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setFechaFinTransporte(String fechaFinTransporte)
/* 117:    */   {
/* 118:117 */     this.fechaFinTransporte = fechaFinTransporte;
/* 119:    */   }
/* 120:    */   
/* 121:    */   @XmlElement(name="placa")
/* 122:    */   public String getPlaca()
/* 123:    */   {
/* 124:122 */     return this.placa;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setPlaca(String placa)
/* 128:    */   {
/* 129:126 */     this.placa = placa;
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.InfoGuiaRemisionJaxb
 * JD-Core Version:    0.7.0.1
 */