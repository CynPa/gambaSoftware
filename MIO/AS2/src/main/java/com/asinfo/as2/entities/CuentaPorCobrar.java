/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.EmpresaListener;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EntityListeners;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.persistence.Transient;
/*  24:    */ import javax.validation.constraints.DecimalMin;
/*  25:    */ import javax.validation.constraints.Digits;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ import org.hibernate.annotations.ColumnDefault;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="cuenta_por_cobrar", indexes={@javax.persistence.Index(name="IX_cuenta_por_cobrar_factura_cliente", columnList="id_factura_cliente")})
/*  32:    */ @EntityListeners({EmpresaListener.class})
/*  33:    */ public class CuentaPorCobrar
/*  34:    */   extends EntidadBase
/*  35:    */   implements Serializable
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -937072550181812313L;
/*  38:    */   @Id
/*  39:    */   @TableGenerator(name="cuenta_por_cobrar", initialValue=0, allocationSize=50)
/*  40:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_por_cobrar")
/*  41:    */   @Column(name="id_cuenta_por_cobrar")
/*  42:    */   private int idCuentaPorCobrar;
/*  43:    */   @Column(name="id_organizacion", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private int idOrganizacion;
/*  46:    */   @Column(name="id_sucursal", nullable=false)
/*  47:    */   private int idSucursal;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/*  50:    */   private FacturaCliente facturaCliente;
/*  51:    */   @Column(name="numero_cuota", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private int numeroCuota;
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   @Column(name="fecha_vencimiento", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Date fechaVencimiento;
/*  58:    */   @Temporal(TemporalType.DATE)
/*  59:    */   @Column(name="fecha_cancelacion", nullable=true)
/*  60:    */   private Date fechaCancelacion;
/*  61:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  62:    */   @NotNull
/*  63:    */   @Digits(integer=12, fraction=2)
/*  64:    */   @DecimalMin("0.01")
/*  65:    */   private BigDecimal valor;
/*  66:    */   @Column(name="saldo", nullable=false, precision=12, scale=2)
/*  67:    */   @NotNull
/*  68:    */   @Digits(integer=12, fraction=2)
/*  69:    */   private BigDecimal saldo;
/*  70:    */   @Column(name="indicador_generada_protesto", nullable=false)
/*  71:    */   private boolean indicadorGeneradaProtesto;
/*  72:    */   @Temporal(TemporalType.DATE)
/*  73:    */   @Column(name="fecha_protesto", nullable=true)
/*  74:    */   private Date fechaProtesto;
/*  75:    */   @Column(name="indicador_anulada", nullable=true)
/*  76: 99 */   private boolean indicadorAnulada = false;
/*  77:    */   @Column(name="valor_bloqueado", nullable=true, precision=12, scale=2)
/*  78:    */   @Digits(integer=12, fraction=2)
/*  79:102 */   private BigDecimal valorBloqueado = BigDecimal.ZERO;
/*  80:    */   @Column(name="descripcion", length=200, nullable=true)
/*  81:    */   @Size(max=200)
/*  82:    */   private String descripcion;
/*  83:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  84:    */   @JoinColumn(name="id_detalle_forma_cobro_protesto", nullable=true)
/*  85:    */   private DetalleFormaCobro detalleFormaCobroProtesto;
/*  86:    */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="cuentaPorCobrar")
/*  87:114 */   private List<DetalleCobro> listaDetalleCobro = new ArrayList();
/*  88:    */   @Column(name="dias_plazo", nullable=false)
/*  89:    */   @NotNull
/*  90:    */   @ColumnDefault("0")
/*  91:117 */   private int diasPlazo = 0;
/*  92:    */   @Column(name="dias_rotacion", nullable=false)
/*  93:    */   @NotNull
/*  94:    */   @ColumnDefault("0")
/*  95:122 */   private int diasRotacion = 0;
/*  96:    */   @Transient
/*  97:    */   private Date traFechaPago;
/*  98:    */   @Transient
/*  99:    */   private boolean activoEdicionCuota;
/* 100:    */   @Transient
/* 101:133 */   private BigDecimal traValorCobrado = BigDecimal.ZERO;
/* 102:    */   
/* 103:    */   public int getId()
/* 104:    */   {
/* 105:143 */     return this.idCuentaPorCobrar;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdCuentaPorCobrar()
/* 109:    */   {
/* 110:148 */     return this.idCuentaPorCobrar;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdCuentaPorCobrar(int idCuentaPorCobrar)
/* 114:    */   {
/* 115:152 */     this.idCuentaPorCobrar = idCuentaPorCobrar;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getIdOrganizacion()
/* 119:    */   {
/* 120:156 */     return this.idOrganizacion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setIdOrganizacion(int idOrganizacion)
/* 124:    */   {
/* 125:160 */     this.idOrganizacion = idOrganizacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getIdSucursal()
/* 129:    */   {
/* 130:164 */     return this.idSucursal;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIdSucursal(int idSucursal)
/* 134:    */   {
/* 135:168 */     this.idSucursal = idSucursal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public FacturaCliente getFacturaCliente()
/* 139:    */   {
/* 140:172 */     if (this.facturaCliente == null) {
/* 141:173 */       this.facturaCliente = new FacturaCliente();
/* 142:    */     }
/* 143:175 */     return this.facturaCliente;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 147:    */   {
/* 148:179 */     this.facturaCliente = facturaCliente;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getNumeroCuota()
/* 152:    */   {
/* 153:183 */     return this.numeroCuota;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setNumeroCuota(int numeroCuota)
/* 157:    */   {
/* 158:187 */     this.numeroCuota = numeroCuota;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Date getFechaVencimiento()
/* 162:    */   {
/* 163:191 */     return this.fechaVencimiento;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 167:    */   {
/* 168:195 */     this.fechaVencimiento = fechaVencimiento;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public BigDecimal getValor()
/* 172:    */   {
/* 173:199 */     return this.valor;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setValor(BigDecimal valor)
/* 177:    */   {
/* 178:203 */     this.valor = valor;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public BigDecimal getSaldo()
/* 182:    */   {
/* 183:212 */     return this.saldo;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setSaldo(BigDecimal saldo)
/* 187:    */   {
/* 188:222 */     this.saldo = saldo;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String toString()
/* 192:    */   {
/* 193:227 */     return "" + this.numeroCuota;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Date getFechaCancelacion()
/* 197:    */   {
/* 198:236 */     return this.fechaCancelacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setFechaCancelacion(Date fechaCancelacion)
/* 202:    */   {
/* 203:246 */     this.fechaCancelacion = fechaCancelacion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Date getTraFechaPago()
/* 207:    */   {
/* 208:255 */     return this.traFechaPago;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setTraFechaPago(Date traFechaPago)
/* 212:    */   {
/* 213:265 */     this.traFechaPago = traFechaPago;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean isIndicadorGeneradaProtesto()
/* 217:    */   {
/* 218:274 */     return this.indicadorGeneradaProtesto;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setIndicadorGeneradaProtesto(boolean indicadorGeneradaProtesto)
/* 222:    */   {
/* 223:284 */     this.indicadorGeneradaProtesto = indicadorGeneradaProtesto;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public BigDecimal getValorBloqueado()
/* 227:    */   {
/* 228:291 */     return this.valorBloqueado;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setValorBloqueado(BigDecimal valorBloqueado)
/* 232:    */   {
/* 233:299 */     this.valorBloqueado = valorBloqueado;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String getDescripcion()
/* 237:    */   {
/* 238:306 */     return this.descripcion;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setDescripcion(String descripcion)
/* 242:    */   {
/* 243:314 */     this.descripcion = descripcion;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public DetalleFormaCobro getDetalleFormaCobroProtesto()
/* 247:    */   {
/* 248:318 */     return this.detalleFormaCobroProtesto;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setDetalleFormaCobroProtesto(DetalleFormaCobro detalleFormaCobroProtesto)
/* 252:    */   {
/* 253:322 */     this.detalleFormaCobroProtesto = detalleFormaCobroProtesto;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean isIndicadorAnulada()
/* 257:    */   {
/* 258:326 */     return this.indicadorAnulada;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setIndicadorAnulada(boolean indicadorAnulada)
/* 262:    */   {
/* 263:330 */     this.indicadorAnulada = indicadorAnulada;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public boolean isActivoEdicionCuota()
/* 267:    */   {
/* 268:334 */     return this.activoEdicionCuota;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setActivoEdicionCuota(boolean activoEdicionCuota)
/* 272:    */   {
/* 273:338 */     this.activoEdicionCuota = activoEdicionCuota;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public Date getFechaProtesto()
/* 277:    */   {
/* 278:342 */     return this.fechaProtesto;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setFechaProtesto(Date fechaProtesto)
/* 282:    */   {
/* 283:346 */     this.fechaProtesto = fechaProtesto;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public BigDecimal getTraValorCobrado()
/* 287:    */   {
/* 288:350 */     return this.traValorCobrado;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setTraValorCobrado(BigDecimal traValorCobrado)
/* 292:    */   {
/* 293:354 */     this.traValorCobrado = traValorCobrado;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public int getDiasPlazo()
/* 297:    */   {
/* 298:358 */     return this.diasPlazo;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setDiasPlazo(int diasPlazo)
/* 302:    */   {
/* 303:362 */     this.diasPlazo = diasPlazo;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public int getDiasRotacion()
/* 307:    */   {
/* 308:366 */     return this.diasRotacion;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setDiasRotacion(int diasRotacion)
/* 312:    */   {
/* 313:370 */     this.diasRotacion = diasRotacion;
/* 314:    */   }
/* 315:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaPorCobrar
 * JD-Core Version:    0.7.0.1
 */