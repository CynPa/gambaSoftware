/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.DecimalMin;
/*  23:    */ import javax.validation.constraints.Digits;
/*  24:    */ import javax.validation.constraints.Max;
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_empresa"})})
/*  31:    */ public class Proveedor
/*  32:    */   extends EntidadBase
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="proveedor", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="proveedor")
/*  39:    */   @Column(name="id_proveedor", unique=true, nullable=false)
/*  40:    */   private int idProveedor;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal", nullable=false)
/*  44:    */   private int idSucursal;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  47:    */   private FormaPago formaPago;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_condicion_pago", nullable=false)
/*  50:    */   private CondicionPago condicionPago;
/*  51:    */   @OneToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_empresa")
/*  53:    */   private Empresa empresa;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_categoria_retencion", nullable=true)
/*  56:    */   private CategoriaRetencion categoriaRetencion;
/*  57:    */   @Column(name="numero_cuotas", nullable=false)
/*  58:    */   @Min(1L)
/*  59:    */   @Max(1000L)
/*  60:    */   private int numeroCuotas;
/*  61:    */   @Column(name="indicador_pago_cash", nullable=false)
/*  62:    */   private boolean indicadorPagoCash;
/*  63:    */   @Column(name="indicador_parte_relacionada", nullable=false)
/*  64:    */   private boolean indicadorParteRelacionada;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_lista_precios", nullable=true)
/*  67:    */   private ListaPrecios listaPrecios;
/*  68:    */   @Column(name="contacto", nullable=true, length=100)
/*  69:    */   @Size(max=100)
/*  70: 92 */   private String contacto = "";
/*  71:    */   @Column(name="beneficiario", nullable=true, length=100)
/*  72:    */   @Size(min=0, max=100)
/*  73: 96 */   private String beneficiario = "";
/*  74:    */   @Column(name="valor_maximo_documento", nullable=false, precision=12, scale=2)
/*  75:    */   @Digits(integer=12, fraction=2)
/*  76:    */   @DecimalMin("0.00")
/*  77:    */   @NotNull
/*  78:100 */   private BigDecimal valorMaximoDocumento = BigDecimal.ZERO;
/*  79:    */   @Column(name="tipo_proveedor", nullable=true)
/*  80:    */   @Enumerated(EnumType.ORDINAL)
/*  81:    */   private TipoEmpresaEnum tipoProveedor;
/*  82:    */   @Column(name="porcentaje_adicional_recepcion")
/*  83:    */   @Min(0L)
/*  84:    */   @Max(100L)
/*  85:114 */   private int porcentajeAdicionalRecepcion = 0;
/*  86:    */   @Transient
/*  87:119 */   private List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedor = new ArrayList();
/*  88:    */   @Transient
/*  89:122 */   private List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedorFiltrados = new ArrayList();
/*  90:    */   @Transient
/*  91:125 */   private BigDecimal valorPendientePago = BigDecimal.ZERO;
/*  92:    */   @Transient
/*  93:128 */   private BigDecimal valorSolicitadoPago = BigDecimal.ZERO;
/*  94:    */   @Transient
/*  95:131 */   private BigDecimal valorAprobadoPago = BigDecimal.ZERO;
/*  96:    */   @Transient
/*  97:134 */   private BigDecimal valorPagado = BigDecimal.ZERO;
/*  98:    */   @Transient
/*  99:    */   private String numeroChequePagoAgil;
/* 100:    */   @Transient
/* 101:    */   private String descripcionPagoAgil;
/* 102:    */   
/* 103:    */   public Proveedor() {}
/* 104:    */   
/* 105:    */   public Proveedor(Empresa empresa)
/* 106:    */   {
/* 107:147 */     this.empresa = empresa;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getId()
/* 111:    */   {
/* 112:151 */     return this.idProveedor;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getIdProveedor()
/* 116:    */   {
/* 117:155 */     return this.idProveedor;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setIdProveedor(int idProveedor)
/* 121:    */   {
/* 122:159 */     this.idProveedor = idProveedor;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public CondicionPago getCondicionPago()
/* 126:    */   {
/* 127:163 */     return this.condicionPago;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setCondicionPago(CondicionPago condicionPago)
/* 131:    */   {
/* 132:167 */     this.condicionPago = condicionPago;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public FormaPago getFormaPago()
/* 136:    */   {
/* 137:171 */     return this.formaPago;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setFormaPago(FormaPago formaPago)
/* 141:    */   {
/* 142:175 */     this.formaPago = formaPago;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Integer getIdOrganizacion()
/* 146:    */   {
/* 147:179 */     return Integer.valueOf(this.idOrganizacion);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdOrganizacion(Integer idOrganizacion)
/* 151:    */   {
/* 152:183 */     this.idOrganizacion = idOrganizacion.intValue();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getIdSucursal()
/* 156:    */   {
/* 157:192 */     return this.idSucursal;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setIdSucursal(int idSucursal)
/* 161:    */   {
/* 162:202 */     this.idSucursal = idSucursal;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setIdOrganizacion(int idOrganizacion)
/* 166:    */   {
/* 167:212 */     this.idOrganizacion = idOrganizacion;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Empresa getEmpresa()
/* 171:    */   {
/* 172:216 */     return this.empresa;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setEmpresa(Empresa empresa)
/* 176:    */   {
/* 177:220 */     this.empresa = empresa;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public CategoriaRetencion getCategoriaRetencion()
/* 181:    */   {
/* 182:229 */     return this.categoriaRetencion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setCategoriaRetencion(CategoriaRetencion categoriaRetencion)
/* 186:    */   {
/* 187:239 */     this.categoriaRetencion = categoriaRetencion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getNumeroCuotas()
/* 191:    */   {
/* 192:248 */     return this.numeroCuotas;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setNumeroCuotas(int numeroCuotas)
/* 196:    */   {
/* 197:258 */     this.numeroCuotas = numeroCuotas;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isIndicadorPagoCash()
/* 201:    */   {
/* 202:267 */     return this.indicadorPagoCash;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setIndicadorPagoCash(boolean indicadorPagoCash)
/* 206:    */   {
/* 207:277 */     this.indicadorPagoCash = indicadorPagoCash;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean isIndicadorParteRelacionada()
/* 211:    */   {
/* 212:281 */     return this.indicadorParteRelacionada;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setIndicadorParteRelacionada(boolean indicadorParteRelacionada)
/* 216:    */   {
/* 217:285 */     this.indicadorParteRelacionada = indicadorParteRelacionada;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public ListaPrecios getListaPrecios()
/* 221:    */   {
/* 222:294 */     return this.listaPrecios;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setListaPrecios(ListaPrecios listaPrecios)
/* 226:    */   {
/* 227:304 */     this.listaPrecios = listaPrecios;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String getContacto()
/* 231:    */   {
/* 232:308 */     return this.contacto;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setContacto(String contacto)
/* 236:    */   {
/* 237:312 */     this.contacto = contacto;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String getBeneficiario()
/* 241:    */   {
/* 242:316 */     return this.beneficiario;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setBeneficiario(String beneficiario)
/* 246:    */   {
/* 247:320 */     this.beneficiario = beneficiario;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public BigDecimal getValorMaximoDocumento()
/* 251:    */   {
/* 252:324 */     return this.valorMaximoDocumento;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setValorMaximoDocumento(BigDecimal valorMaximoDocumento)
/* 256:    */   {
/* 257:328 */     this.valorMaximoDocumento = valorMaximoDocumento;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public TipoEmpresaEnum getTipoProveedor()
/* 261:    */   {
/* 262:332 */     return this.tipoProveedor;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setTipoProveedor(TipoEmpresaEnum tipoProveedor)
/* 266:    */   {
/* 267:336 */     this.tipoProveedor = tipoProveedor;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public int getPorcentajeAdicionalRecepcion()
/* 271:    */   {
/* 272:340 */     return this.porcentajeAdicionalRecepcion;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setPorcentajeAdicionalRecepcion(int porcentajeAdicionalRecepcion)
/* 276:    */   {
/* 277:344 */     this.porcentajeAdicionalRecepcion = porcentajeAdicionalRecepcion;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedor()
/* 281:    */   {
/* 282:348 */     return this.listaDetalleOrdenPagoProveedor;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setListaDetalleOrdenPagoProveedor(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedor)
/* 286:    */   {
/* 287:352 */     this.listaDetalleOrdenPagoProveedor = listaDetalleOrdenPagoProveedor;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public BigDecimal getValorPendientePago()
/* 291:    */   {
/* 292:356 */     if (this.valorPendientePago == null) {
/* 293:357 */       this.valorPendientePago = BigDecimal.ZERO;
/* 294:    */     }
/* 295:359 */     return this.valorPendientePago;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setValorPendientePago(BigDecimal valorPendientePago)
/* 299:    */   {
/* 300:363 */     this.valorPendientePago = valorPendientePago;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public BigDecimal getValorSolicitadoPago()
/* 304:    */   {
/* 305:367 */     if (this.valorSolicitadoPago == null) {
/* 306:368 */       this.valorSolicitadoPago = BigDecimal.ZERO;
/* 307:    */     }
/* 308:370 */     return this.valorSolicitadoPago;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setValorSolicitadoPago(BigDecimal valorSolicitadoPago)
/* 312:    */   {
/* 313:374 */     this.valorSolicitadoPago = valorSolicitadoPago;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public BigDecimal getValorAprobadoPago()
/* 317:    */   {
/* 318:378 */     if (this.valorAprobadoPago == null) {
/* 319:379 */       this.valorAprobadoPago = BigDecimal.ZERO;
/* 320:    */     }
/* 321:381 */     return this.valorAprobadoPago;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setValorAprobadoPago(BigDecimal valorAprobadoPago)
/* 325:    */   {
/* 326:385 */     this.valorAprobadoPago = valorAprobadoPago;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public BigDecimal getDiferenciaPendiente()
/* 330:    */   {
/* 331:389 */     return getValorPendientePago().subtract(getValorSolicitadoPago());
/* 332:    */   }
/* 333:    */   
/* 334:    */   public BigDecimal getDiferenciaAprobado()
/* 335:    */   {
/* 336:393 */     return getValorSolicitadoPago().subtract(getValorAprobadoPago());
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String getNumeroChequePagoAgil()
/* 340:    */   {
/* 341:397 */     return this.numeroChequePagoAgil;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setNumeroChequePagoAgil(String numeroChequePagoAgil)
/* 345:    */   {
/* 346:401 */     this.numeroChequePagoAgil = numeroChequePagoAgil;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public BigDecimal getValorPagado()
/* 350:    */   {
/* 351:405 */     return this.valorPagado;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setValorPagado(BigDecimal valorPagado)
/* 355:    */   {
/* 356:409 */     this.valorPagado = valorPagado;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedorFiltrados()
/* 360:    */   {
/* 361:413 */     return this.listaDetalleOrdenPagoProveedorFiltrados;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setListaDetalleOrdenPagoProveedorFiltrados(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedorFiltrados)
/* 365:    */   {
/* 366:417 */     this.listaDetalleOrdenPagoProveedorFiltrados = listaDetalleOrdenPagoProveedorFiltrados;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String getDescripcionPagoAgil()
/* 370:    */   {
/* 371:421 */     return this.descripcionPagoAgil;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setDescripcionPagoAgil(String descripcionPagoAgil)
/* 375:    */   {
/* 376:425 */     this.descripcionPagoAgil = descripcionPagoAgil;
/* 377:    */   }
/* 378:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Proveedor
 * JD-Core Version:    0.7.0.1
 */