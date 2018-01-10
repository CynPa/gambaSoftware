/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ 
/*   7:    */ public class ReporteEstadoCuentaFactura
/*   8:    */ {
/*   9:    */   private Date fechaDocumento;
/*  10:    */   private String numeroDocumento;
/*  11:    */   private Date fechaVencimiento;
/*  12:    */   private String numeroFactura;
/*  13:    */   private BigDecimal debito;
/*  14:    */   private BigDecimal credito;
/*  15:    */   private BigDecimal totalDebito;
/*  16:    */   private BigDecimal totalCredito;
/*  17:    */   private String codigoDocumento;
/*  18:    */   private String codigoDocumentoProceso;
/*  19:    */   private String descripcionDocumento;
/*  20:    */   private BigDecimal saldo;
/*  21:    */   private BigDecimal totalSaldo;
/*  22:    */   private String nombreDocumento;
/*  23:    */   private int idCobro;
/*  24:    */   private int idFacturaCliente;
/*  25:    */   private DocumentoBase documentoBase;
/*  26:    */   private String numeroPacking;
/*  27:    */   private int idFacturaProveedor;
/*  28:    */   
/*  29:    */   public ReporteEstadoCuentaFactura() {}
/*  30:    */   
/*  31:    */   public ReporteEstadoCuentaFactura(Date fechaDocumento, String numeroDocumento, Date fechaVencimiento, String numeroFactura, BigDecimal debito, BigDecimal credito, BigDecimal saldo)
/*  32:    */   {
/*  33: 82 */     this.fechaDocumento = fechaDocumento;
/*  34: 83 */     this.numeroDocumento = numeroDocumento;
/*  35: 84 */     this.fechaVencimiento = fechaVencimiento;
/*  36: 85 */     this.numeroFactura = numeroFactura;
/*  37: 86 */     this.debito = debito;
/*  38: 87 */     this.credito = credito;
/*  39: 88 */     this.saldo = saldo;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Date getFechaDocumento()
/*  43:    */   {
/*  44: 99 */     return this.fechaDocumento;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setFechaDocumento(Date fechaDocumento)
/*  48:    */   {
/*  49:109 */     this.fechaDocumento = fechaDocumento;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getNumeroDocumento()
/*  53:    */   {
/*  54:118 */     return this.numeroDocumento;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setNumeroDocumento(String numeroDocumento)
/*  58:    */   {
/*  59:128 */     this.numeroDocumento = numeroDocumento;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Date getFechaVencimiento()
/*  63:    */   {
/*  64:137 */     return this.fechaVencimiento;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setFechaVencimiento(Date fechaVencimiento)
/*  68:    */   {
/*  69:147 */     this.fechaVencimiento = fechaVencimiento;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getNumeroFactura()
/*  73:    */   {
/*  74:156 */     return this.numeroFactura;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setNumeroFactura(String numeroFactura)
/*  78:    */   {
/*  79:166 */     this.numeroFactura = numeroFactura;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public BigDecimal getDebito()
/*  83:    */   {
/*  84:175 */     return this.debito;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setDebito(BigDecimal debito)
/*  88:    */   {
/*  89:185 */     this.debito = debito;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public BigDecimal getCredito()
/*  93:    */   {
/*  94:194 */     return this.credito;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setCredito(BigDecimal credito)
/*  98:    */   {
/*  99:204 */     this.credito = credito;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public BigDecimal getTotalDebito()
/* 103:    */   {
/* 104:213 */     if (this.totalDebito == null) {
/* 105:214 */       this.totalDebito = BigDecimal.ZERO;
/* 106:    */     }
/* 107:216 */     return this.totalDebito;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setTotalDebito(BigDecimal totalDebito)
/* 111:    */   {
/* 112:226 */     this.totalDebito = totalDebito;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public BigDecimal getTotalCredito()
/* 116:    */   {
/* 117:235 */     if (this.totalCredito == null) {
/* 118:236 */       this.totalCredito = BigDecimal.ZERO;
/* 119:    */     }
/* 120:238 */     return this.totalCredito;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setTotalCredito(BigDecimal totalCredito)
/* 124:    */   {
/* 125:248 */     this.totalCredito = totalCredito;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getCodigoDocumento()
/* 129:    */   {
/* 130:257 */     return this.codigoDocumento;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setCodigoDocumento(String codigoDocumento)
/* 134:    */   {
/* 135:267 */     this.codigoDocumento = codigoDocumento;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getDescripcionDocumento()
/* 139:    */   {
/* 140:271 */     return this.descripcionDocumento;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDescripcionDocumento(String descripcionDocumento)
/* 144:    */   {
/* 145:275 */     this.descripcionDocumento = descripcionDocumento;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getCodigoDocumentoProceso()
/* 149:    */   {
/* 150:279 */     return this.codigoDocumentoProceso;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setCodigoDocumentoProceso(String codigoDocumentoProceso)
/* 154:    */   {
/* 155:283 */     this.codigoDocumentoProceso = codigoDocumentoProceso;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public BigDecimal getSaldo()
/* 159:    */   {
/* 160:287 */     if (this.saldo == null) {
/* 161:288 */       this.saldo = BigDecimal.ZERO;
/* 162:    */     }
/* 163:290 */     return this.saldo;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setSaldo(BigDecimal saldo)
/* 167:    */   {
/* 168:294 */     this.saldo = saldo;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public BigDecimal getTotalSaldo()
/* 172:    */   {
/* 173:298 */     if (this.totalSaldo == null) {
/* 174:299 */       this.totalSaldo = BigDecimal.ZERO;
/* 175:    */     }
/* 176:301 */     return this.totalSaldo;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setTotalSaldo(BigDecimal saldoTotal)
/* 180:    */   {
/* 181:305 */     this.totalSaldo = saldoTotal;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String getNombreDocumento()
/* 185:    */   {
/* 186:309 */     return this.nombreDocumento;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setNombreDocumento(String nombreDocumento)
/* 190:    */   {
/* 191:313 */     this.nombreDocumento = nombreDocumento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public int getIdCobro()
/* 195:    */   {
/* 196:317 */     return this.idCobro;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setIdCobro(int idCobro)
/* 200:    */   {
/* 201:321 */     this.idCobro = idCobro;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int getIdFacturaCliente()
/* 205:    */   {
/* 206:325 */     return this.idFacturaCliente;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setIdFacturaCliente(int idFacturaCliente)
/* 210:    */   {
/* 211:329 */     this.idFacturaCliente = idFacturaCliente;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public DocumentoBase getDocumentoBase()
/* 215:    */   {
/* 216:333 */     return this.documentoBase;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 220:    */   {
/* 221:337 */     this.documentoBase = documentoBase;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getNumeroPacking()
/* 225:    */   {
/* 226:341 */     return this.numeroPacking;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setNumeroPacking(String numeroPacking)
/* 230:    */   {
/* 231:345 */     this.numeroPacking = numeroPacking;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getIdFacturaProveedor()
/* 235:    */   {
/* 236:349 */     return this.idFacturaProveedor;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setIdFacturaProveedor(int idFacturaProveedor)
/* 240:    */   {
/* 241:353 */     this.idFacturaProveedor = idFacturaProveedor;
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteEstadoCuentaFactura
 * JD-Core Version:    0.7.0.1
 */