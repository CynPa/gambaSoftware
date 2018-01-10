/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.xml.bind.annotation.XmlElement;
/*   5:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlRootElement(name="infoFactura")
/*   9:    */ @XmlType(propOrder={"fechaEmision", "dirEstablecimiento", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "contribuyenteEspecial", "obligadoContabilidad", "rise", "codDocModificado", "numDocModificado", "fechaEmisionDocSustento", "totalSinImpuestos", "impuestos", "compensacionesJaxb", "valorTotal"})
/*  10:    */ public class InfoNDebitoJaxb
/*  11:    */ {
/*  12:    */   private String fechaEmision;
/*  13:    */   private String dirEstablecimiento;
/*  14:    */   private String obligadoContabilidad;
/*  15:    */   private String tipoIdentificacionComprador;
/*  16:    */   private String razonSocialComprador;
/*  17:    */   private String identificacionComprador;
/*  18:    */   private String contribuyenteEspecial;
/*  19:    */   private String rise;
/*  20:    */   private BigDecimal totalSinImpuestos;
/*  21:    */   private String codDocModificado;
/*  22:    */   private String numDocModificado;
/*  23:    */   private String fechaEmisionDocSustento;
/*  24:    */   private CompensacionesJaxb compensacionesJaxb;
/*  25:    */   private ImpuestosJaxb impuestos;
/*  26:    */   private BigDecimal valorTotal;
/*  27:    */   
/*  28:    */   @XmlElement(name="valorTotal")
/*  29:    */   public BigDecimal getValorTotal()
/*  30:    */   {
/*  31: 51 */     return this.valorTotal;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setValorTotal(BigDecimal valorTotal)
/*  35:    */   {
/*  36: 55 */     this.valorTotal = valorTotal;
/*  37:    */   }
/*  38:    */   
/*  39:    */   @XmlElement(name="fechaEmision")
/*  40:    */   public String getFechaEmision()
/*  41:    */   {
/*  42: 60 */     return this.fechaEmision;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setFechaEmision(String fechaEmision)
/*  46:    */   {
/*  47: 64 */     this.fechaEmision = fechaEmision;
/*  48:    */   }
/*  49:    */   
/*  50:    */   @XmlElement(name="dirEstablecimiento")
/*  51:    */   public String getDirEstablecimiento()
/*  52:    */   {
/*  53: 69 */     return this.dirEstablecimiento;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setDirEstablecimiento(String dirEstablecimiento)
/*  57:    */   {
/*  58: 73 */     this.dirEstablecimiento = dirEstablecimiento;
/*  59:    */   }
/*  60:    */   
/*  61:    */   @XmlElement(name="obligadoContabilidad")
/*  62:    */   public String getObligadoContabilidad()
/*  63:    */   {
/*  64: 78 */     return this.obligadoContabilidad;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setObligadoContabilidad(String obligadoContabilidad)
/*  68:    */   {
/*  69: 82 */     this.obligadoContabilidad = obligadoContabilidad;
/*  70:    */   }
/*  71:    */   
/*  72:    */   @XmlElement(name="tipoIdentificacionComprador")
/*  73:    */   public String getTipoIdentificacionComprador()
/*  74:    */   {
/*  75: 87 */     return this.tipoIdentificacionComprador;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setTipoIdentificacionComprador(String tipoIdentificacionComprador)
/*  79:    */   {
/*  80: 91 */     this.tipoIdentificacionComprador = tipoIdentificacionComprador;
/*  81:    */   }
/*  82:    */   
/*  83:    */   @XmlElement(name="razonSocialComprador")
/*  84:    */   public String getRazonSocialComprador()
/*  85:    */   {
/*  86: 96 */     return this.razonSocialComprador;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setRazonSocialComprador(String razonSocialComprador)
/*  90:    */   {
/*  91:100 */     this.razonSocialComprador = razonSocialComprador;
/*  92:    */   }
/*  93:    */   
/*  94:    */   @XmlElement(name="identificacionComprador")
/*  95:    */   public String getIdentificacionComprador()
/*  96:    */   {
/*  97:105 */     return this.identificacionComprador;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdentificacionComprador(String identificacionComprador)
/* 101:    */   {
/* 102:109 */     this.identificacionComprador = identificacionComprador;
/* 103:    */   }
/* 104:    */   
/* 105:    */   @XmlElement(name="totalSinImpuestos")
/* 106:    */   public BigDecimal getTotalSinImpuestos()
/* 107:    */   {
/* 108:114 */     return this.totalSinImpuestos;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setTotalSinImpuestos(BigDecimal totalSinImpuestos)
/* 112:    */   {
/* 113:118 */     this.totalSinImpuestos = totalSinImpuestos;
/* 114:    */   }
/* 115:    */   
/* 116:    */   @XmlElement(name="codDocModificado")
/* 117:    */   public String getCodDocModificado()
/* 118:    */   {
/* 119:123 */     return this.codDocModificado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setCodDocModificado(String codDocModificado)
/* 123:    */   {
/* 124:127 */     this.codDocModificado = codDocModificado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   @XmlElement(name="numDocModificado")
/* 128:    */   public String getNumDocModificado()
/* 129:    */   {
/* 130:132 */     return this.numDocModificado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setNumDocModificado(String numDocModificado)
/* 134:    */   {
/* 135:136 */     this.numDocModificado = numDocModificado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   @XmlElement(name="fechaEmisionDocSustento")
/* 139:    */   public String getFechaEmisionDocSustento()
/* 140:    */   {
/* 141:141 */     return this.fechaEmisionDocSustento;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setFechaEmisionDocSustento(String fechaEmisionDocSustento)
/* 145:    */   {
/* 146:145 */     this.fechaEmisionDocSustento = fechaEmisionDocSustento;
/* 147:    */   }
/* 148:    */   
/* 149:    */   @XmlElement(name="impuestos")
/* 150:    */   public ImpuestosJaxb getImpuestos()
/* 151:    */   {
/* 152:150 */     return this.impuestos;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setImpuestos(ImpuestosJaxb impuestos)
/* 156:    */   {
/* 157:154 */     this.impuestos = impuestos;
/* 158:    */   }
/* 159:    */   
/* 160:    */   @XmlElement(name="contribuyenteEspecial")
/* 161:    */   public String getContribuyenteEspecial()
/* 162:    */   {
/* 163:159 */     return this.contribuyenteEspecial;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setContribuyenteEspecial(String contribuyenteEspecial)
/* 167:    */   {
/* 168:163 */     this.contribuyenteEspecial = contribuyenteEspecial;
/* 169:    */   }
/* 170:    */   
/* 171:    */   @XmlElement(name="rise")
/* 172:    */   public String getRise()
/* 173:    */   {
/* 174:168 */     return this.rise;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setRise(String rise)
/* 178:    */   {
/* 179:172 */     this.rise = rise;
/* 180:    */   }
/* 181:    */   
/* 182:    */   @XmlElement(name="compensaciones")
/* 183:    */   public CompensacionesJaxb getCompensacionesJaxb()
/* 184:    */   {
/* 185:177 */     return this.compensacionesJaxb;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setCompensacionesJaxb(CompensacionesJaxb compensacionesJaxb)
/* 189:    */   {
/* 190:181 */     this.compensacionesJaxb = compensacionesJaxb;
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.InfoNDebitoJaxb
 * JD-Core Version:    0.7.0.1
 */