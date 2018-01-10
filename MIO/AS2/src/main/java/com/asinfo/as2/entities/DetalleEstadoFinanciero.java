/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_estado_financiero")
/*  21:    */ public class DetalleEstadoFinanciero
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_estado_financiero", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_estado_financiero")
/*  29:    */   @Column(name="id_detalle_estado_financiero", unique=true, nullable=false)
/*  30:    */   private int idDetalleEstadoFinanciero;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_estado_financiero", nullable=true)
/*  33:    */   private EstadoFinanciero estadoFinanciero;
/*  34:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  35:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private CuentaContable cuentaContable;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @Column(name="nota", length=200)
/*  43:    */   @Size(max=200)
/*  44:    */   private String nota;
/*  45:    */   @Transient
/*  46:    */   private BigDecimal debe;
/*  47:    */   @Transient
/*  48:    */   private BigDecimal haber;
/*  49:    */   @Transient
/*  50:    */   private BigDecimal saldoInicial;
/*  51:    */   @Transient
/*  52:    */   private BigDecimal saldo;
/*  53:    */   @Transient
/*  54:    */   private BigDecimal saldoDeudor;
/*  55:    */   @Transient
/*  56:    */   private BigDecimal saldoAcreedor;
/*  57:    */   @Transient
/*  58:    */   private int IdPadre;
/*  59:    */   @Transient
/*  60:    */   private int nivel;
/*  61:    */   @Transient
/*  62:    */   private int idNivel;
/*  63:    */   @Transient
/*  64:    */   private String nota2;
/*  65:    */   @Transient
/*  66:    */   private BigDecimal debe2;
/*  67:    */   @Transient
/*  68:    */   private BigDecimal haber2;
/*  69:    */   @Transient
/*  70:    */   private BigDecimal saldo2;
/*  71:    */   @Transient
/*  72:    */   private String nombreCuentaContable;
/*  73:    */   @Transient
/*  74:    */   private String codigoCuenta;
/*  75:    */   
/*  76:    */   public DetalleEstadoFinanciero() {}
/*  77:    */   
/*  78:    */   public DetalleEstadoFinanciero(int idDetalleEstadoFinanciero, EstadoFinanciero estadoFinanciero, CuentaContable cuentaContable, int idOrganizacion, int idSucursal, String nota)
/*  79:    */   {
/*  80:117 */     this.idDetalleEstadoFinanciero = idDetalleEstadoFinanciero;
/*  81:118 */     this.estadoFinanciero = estadoFinanciero;
/*  82:119 */     this.cuentaContable = cuentaContable;
/*  83:120 */     this.idOrganizacion = idOrganizacion;
/*  84:121 */     this.idSucursal = idSucursal;
/*  85:122 */     this.nota = nota;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdDetalleEstadoFinanciero()
/*  89:    */   {
/*  90:126 */     return this.idDetalleEstadoFinanciero;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdDetalleEstadoFinanciero(int idDetalleEstadoFinanciero)
/*  94:    */   {
/*  95:130 */     this.idDetalleEstadoFinanciero = idDetalleEstadoFinanciero;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public EstadoFinanciero getEstadoFinanciero()
/*  99:    */   {
/* 100:134 */     return this.estadoFinanciero;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setEstadoFinanciero(EstadoFinanciero estadoFinanciero)
/* 104:    */   {
/* 105:138 */     this.estadoFinanciero = estadoFinanciero;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public CuentaContable getCuentaContable()
/* 109:    */   {
/* 110:142 */     return this.cuentaContable;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 114:    */   {
/* 115:146 */     this.cuentaContable = cuentaContable;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getIdOrganizacion()
/* 119:    */   {
/* 120:150 */     return this.idOrganizacion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setIdOrganizacion(int idOrganizacion)
/* 124:    */   {
/* 125:154 */     this.idOrganizacion = idOrganizacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getIdSucursal()
/* 129:    */   {
/* 130:158 */     return this.idSucursal;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIdSucursal(int idSucursal)
/* 134:    */   {
/* 135:162 */     this.idSucursal = idSucursal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getNota()
/* 139:    */   {
/* 140:166 */     return this.nota;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setNota(String nota)
/* 144:    */   {
/* 145:170 */     this.nota = nota;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public BigDecimal getDebe()
/* 149:    */   {
/* 150:174 */     if (this.debe == null) {
/* 151:175 */       this.debe = BigDecimal.ZERO;
/* 152:    */     }
/* 153:177 */     return this.debe;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setDebe(BigDecimal debe)
/* 157:    */   {
/* 158:181 */     this.debe = debe;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public BigDecimal getHaber()
/* 162:    */   {
/* 163:185 */     if (this.haber == null) {
/* 164:186 */       this.haber = BigDecimal.ZERO;
/* 165:    */     }
/* 166:189 */     return this.haber;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setHaber(BigDecimal haber)
/* 170:    */   {
/* 171:193 */     this.haber = haber;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public BigDecimal getSaldoInicial()
/* 175:    */   {
/* 176:197 */     if (this.saldoInicial == null) {
/* 177:198 */       this.saldoInicial = BigDecimal.ZERO;
/* 178:    */     }
/* 179:200 */     return this.saldoInicial;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setSaldoInicial(BigDecimal saldoInicial)
/* 183:    */   {
/* 184:204 */     this.saldoInicial = saldoInicial;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public BigDecimal getSaldo()
/* 188:    */   {
/* 189:208 */     this.saldo = BigDecimal.ZERO;
/* 190:209 */     this.saldo = this.saldo.add(getSaldoInicial());
/* 191:210 */     this.saldo = this.saldo.add(getDebe());
/* 192:211 */     this.saldo = this.saldo.subtract(getHaber());
/* 193:212 */     return this.saldo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setSaldo(BigDecimal saldo)
/* 197:    */   {
/* 198:216 */     this.saldo = saldo;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getIdPadre()
/* 202:    */   {
/* 203:220 */     return this.IdPadre;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setIdPadre(int idPadre)
/* 207:    */   {
/* 208:224 */     this.IdPadre = idPadre;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public int getNivel()
/* 212:    */   {
/* 213:228 */     return this.nivel;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setNivel(int nivel)
/* 217:    */   {
/* 218:232 */     this.nivel = nivel;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public BigDecimal getSaldoDeudor()
/* 222:    */   {
/* 223:236 */     if (getSaldo().compareTo(BigDecimal.ZERO) == 1) {
/* 224:237 */       this.saldoDeudor = this.saldo;
/* 225:    */     } else {
/* 226:239 */       this.saldoDeudor = BigDecimal.ZERO;
/* 227:    */     }
/* 228:241 */     return this.saldoDeudor;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setSaldoDeudor(BigDecimal saldoDeudor)
/* 232:    */   {
/* 233:245 */     this.saldoDeudor = saldoDeudor;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public BigDecimal getSaldoAcreedor()
/* 237:    */   {
/* 238:249 */     if (getSaldo().compareTo(BigDecimal.ZERO) == -1) {
/* 239:250 */       this.saldoAcreedor = this.saldo.abs();
/* 240:    */     } else {
/* 241:252 */       this.saldoAcreedor = BigDecimal.ZERO;
/* 242:    */     }
/* 243:254 */     return this.saldoAcreedor;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setSaldoAcreedor(BigDecimal saldoAcreedor)
/* 247:    */   {
/* 248:258 */     this.saldoAcreedor = saldoAcreedor;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public int getIdNivel()
/* 252:    */   {
/* 253:262 */     return this.idNivel;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setIdNivel(int idNivel)
/* 257:    */   {
/* 258:266 */     this.idNivel = idNivel;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getNota2()
/* 262:    */   {
/* 263:270 */     return this.nota2;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setNota2(String nota2)
/* 267:    */   {
/* 268:274 */     this.nota2 = nota2;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public BigDecimal getDebe2()
/* 272:    */   {
/* 273:278 */     if (this.debe2 == null) {
/* 274:279 */       this.debe2 = BigDecimal.ZERO;
/* 275:    */     }
/* 276:281 */     return this.debe2;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setDebe2(BigDecimal debe2)
/* 280:    */   {
/* 281:285 */     this.debe2 = debe2;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public BigDecimal getHaber2()
/* 285:    */   {
/* 286:289 */     if (this.haber2 == null) {
/* 287:290 */       this.haber2 = BigDecimal.ZERO;
/* 288:    */     }
/* 289:292 */     return this.haber2;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setHaber2(BigDecimal haber2)
/* 293:    */   {
/* 294:296 */     this.haber2 = haber2;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public BigDecimal getSaldo2()
/* 298:    */   {
/* 299:300 */     this.saldo2 = BigDecimal.ZERO;
/* 300:301 */     this.saldo2 = this.saldo2.add(getDebe2());
/* 301:302 */     this.saldo2 = this.saldo2.subtract(getHaber2());
/* 302:303 */     return this.saldo2;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setSaldo2(BigDecimal saldo2)
/* 306:    */   {
/* 307:307 */     this.saldo2 = saldo2;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public String getNombreCuentaContable()
/* 311:    */   {
/* 312:311 */     return this.nombreCuentaContable;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setNombreCuentaContable(String nombreCuentaContable)
/* 316:    */   {
/* 317:315 */     this.nombreCuentaContable = nombreCuentaContable;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getCodigoCuenta()
/* 321:    */   {
/* 322:319 */     return this.codigoCuenta;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setCodigoCuenta(String codigoCuenta)
/* 326:    */   {
/* 327:323 */     this.codigoCuenta = codigoCuenta;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public int getId()
/* 331:    */   {
/* 332:333 */     return getIdDetalleEstadoFinanciero();
/* 333:    */   }
/* 334:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleEstadoFinanciero
 * JD-Core Version:    0.7.0.1
 */