/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.xml.bind.annotation.XmlElement;
/*   5:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlRootElement(name="infoNotaCredito")
/*   9:    */ @XmlType(propOrder={"fechaEmision", "dirEstablecimiento", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "contribuyenteEspecial", "obligadoContabilidad", "rise", "codDocModificado", "numDocModificado", "fechaEmisionDocSustento", "totalSinImpuestos", "compensacionesJaxb", "valorModificacion", "moneda", "totalConImpuestosJaxb", "motivo"})
/*  10:    */ public class InfoNCreditoJaxb
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
/*  21:    */   private String moneda;
/*  22:    */   private TotalConImpuestosJaxb totalConImpuestosJaxb;
/*  23:    */   private CompensacionesJaxb compensacionesJaxb;
/*  24:    */   private String codDocModificado;
/*  25:    */   private String numDocModificado;
/*  26:    */   private String fechaEmisionDocSustento;
/*  27:    */   private BigDecimal valorModificacion;
/*  28:    */   private String motivo;
/*  29:    */   
/*  30:    */   @XmlElement(name="totalConImpuestos")
/*  31:    */   public TotalConImpuestosJaxb getTotalConImpuestosJaxb()
/*  32:    */   {
/*  33: 51 */     return this.totalConImpuestosJaxb;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setTotalConImpuestosJaxb(TotalConImpuestosJaxb totalConImpuestosJaxb)
/*  37:    */   {
/*  38: 55 */     this.totalConImpuestosJaxb = totalConImpuestosJaxb;
/*  39:    */   }
/*  40:    */   
/*  41:    */   @XmlElement(name="compensaciones")
/*  42:    */   public CompensacionesJaxb getCompensacionesJaxb()
/*  43:    */   {
/*  44: 60 */     return this.compensacionesJaxb;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setCompensacionesJaxb(CompensacionesJaxb compensacionesJaxb)
/*  48:    */   {
/*  49: 64 */     this.compensacionesJaxb = compensacionesJaxb;
/*  50:    */   }
/*  51:    */   
/*  52:    */   @XmlElement(name="fechaEmision")
/*  53:    */   public String getFechaEmision()
/*  54:    */   {
/*  55: 69 */     return this.fechaEmision;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setFechaEmision(String fechaEmision)
/*  59:    */   {
/*  60: 73 */     this.fechaEmision = fechaEmision;
/*  61:    */   }
/*  62:    */   
/*  63:    */   @XmlElement(name="dirEstablecimiento")
/*  64:    */   public String getDirEstablecimiento()
/*  65:    */   {
/*  66: 78 */     return this.dirEstablecimiento;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setDirEstablecimiento(String dirEstablecimiento)
/*  70:    */   {
/*  71: 82 */     this.dirEstablecimiento = dirEstablecimiento;
/*  72:    */   }
/*  73:    */   
/*  74:    */   @XmlElement(name="obligadoContabilidad")
/*  75:    */   public String getObligadoContabilidad()
/*  76:    */   {
/*  77: 87 */     return this.obligadoContabilidad;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setObligadoContabilidad(String obligadoContabilidad)
/*  81:    */   {
/*  82: 91 */     this.obligadoContabilidad = obligadoContabilidad;
/*  83:    */   }
/*  84:    */   
/*  85:    */   @XmlElement(name="tipoIdentificacionComprador")
/*  86:    */   public String getTipoIdentificacionComprador()
/*  87:    */   {
/*  88: 96 */     return this.tipoIdentificacionComprador;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setTipoIdentificacionComprador(String tipoIdentificacionComprador)
/*  92:    */   {
/*  93:100 */     this.tipoIdentificacionComprador = tipoIdentificacionComprador;
/*  94:    */   }
/*  95:    */   
/*  96:    */   @XmlElement(name="razonSocialComprador")
/*  97:    */   public String getRazonSocialComprador()
/*  98:    */   {
/*  99:105 */     return this.razonSocialComprador;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setRazonSocialComprador(String razonSocialComprador)
/* 103:    */   {
/* 104:109 */     this.razonSocialComprador = razonSocialComprador;
/* 105:    */   }
/* 106:    */   
/* 107:    */   @XmlElement(name="identificacionComprador")
/* 108:    */   public String getIdentificacionComprador()
/* 109:    */   {
/* 110:114 */     return this.identificacionComprador;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdentificacionComprador(String identificacionComprador)
/* 114:    */   {
/* 115:118 */     this.identificacionComprador = identificacionComprador;
/* 116:    */   }
/* 117:    */   
/* 118:    */   @XmlElement(name="totalSinImpuestos")
/* 119:    */   public BigDecimal getTotalSinImpuestos()
/* 120:    */   {
/* 121:123 */     return this.totalSinImpuestos;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setTotalSinImpuestos(BigDecimal totalSinImpuestos)
/* 125:    */   {
/* 126:127 */     this.totalSinImpuestos = totalSinImpuestos;
/* 127:    */   }
/* 128:    */   
/* 129:    */   @XmlElement(name="moneda")
/* 130:    */   public String getMoneda()
/* 131:    */   {
/* 132:132 */     return this.moneda;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setMoneda(String moneda)
/* 136:    */   {
/* 137:136 */     this.moneda = moneda;
/* 138:    */   }
/* 139:    */   
/* 140:    */   @XmlElement(name="codDocModificado")
/* 141:    */   public String getCodDocModificado()
/* 142:    */   {
/* 143:141 */     return this.codDocModificado;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setCodDocModificado(String codDocModificado)
/* 147:    */   {
/* 148:145 */     this.codDocModificado = codDocModificado;
/* 149:    */   }
/* 150:    */   
/* 151:    */   @XmlElement(name="numDocModificado")
/* 152:    */   public String getNumDocModificado()
/* 153:    */   {
/* 154:150 */     return this.numDocModificado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setNumDocModificado(String numDocModificado)
/* 158:    */   {
/* 159:154 */     this.numDocModificado = numDocModificado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getFechaEmisionDocSustento()
/* 163:    */   {
/* 164:159 */     return this.fechaEmisionDocSustento;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setFechaEmisionDocSustento(String fechaEmisionDocSustento)
/* 168:    */   {
/* 169:163 */     this.fechaEmisionDocSustento = fechaEmisionDocSustento;
/* 170:    */   }
/* 171:    */   
/* 172:    */   @XmlElement(name="valorModificacion")
/* 173:    */   public BigDecimal getValorModificacion()
/* 174:    */   {
/* 175:168 */     return this.valorModificacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setValorModificacion(BigDecimal valorModificacion)
/* 179:    */   {
/* 180:172 */     this.valorModificacion = valorModificacion;
/* 181:    */   }
/* 182:    */   
/* 183:    */   @XmlElement(name="motivo")
/* 184:    */   public String getMotivo()
/* 185:    */   {
/* 186:177 */     return this.motivo;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setMotivo(String motivo)
/* 190:    */   {
/* 191:181 */     this.motivo = motivo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   @XmlElement(name="contribuyenteEspecial")
/* 195:    */   public String getContribuyenteEspecial()
/* 196:    */   {
/* 197:186 */     return this.contribuyenteEspecial;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setContribuyenteEspecial(String contribuyenteEspecial)
/* 201:    */   {
/* 202:190 */     this.contribuyenteEspecial = contribuyenteEspecial;
/* 203:    */   }
/* 204:    */   
/* 205:    */   @XmlElement(name="rise")
/* 206:    */   public String getRise()
/* 207:    */   {
/* 208:195 */     return this.rise;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setRise(String rise)
/* 212:    */   {
/* 213:199 */     this.rise = rise;
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.InfoNCreditoJaxb
 * JD-Core Version:    0.7.0.1
 */