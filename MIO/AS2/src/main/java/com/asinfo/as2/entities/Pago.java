/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
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
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ import org.hibernate.annotations.ColumnDefault;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="pago", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="id_empresa"), @javax.persistence.Index(columnList="fecha")})
/*  31:    */ public class Pago
/*  32:    */   extends EntidadBase
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="pago", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pago")
/*  38:    */   @Column(name="id_pago")
/*  39:    */   private int idPago;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  44:    */   private Sucursal sucursal;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private Empresa empresa;
/*  49:    */   @Column(name="beneficiario", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   @Size(max=100)
/*  52:    */   @ColumnDefault("''")
/*  53:    */   private String beneficiario;
/*  54:    */   @Enumerated(EnumType.ORDINAL)
/*  55:    */   @Column(name="estado", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Estado estado;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_documento", nullable=false)
/*  60:    */   @NotNull
/*  61:    */   private Documento documento;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_pago_cash", nullable=true)
/*  64:    */   private PagoCash pagoCash;
/*  65:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  66:    */   private Date fechaContabilizacion;
/*  67:    */   @Column(name="numero", nullable=false, length=20)
/*  68:    */   private String numero;
/*  69:    */   @Temporal(TemporalType.DATE)
/*  70:    */   @Column(name="fecha", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private Date fecha;
/*  73:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  74:    */   private BigDecimal valor;
/*  75:    */   @Column(name="descripcion", length=200, nullable=true)
/*  76:    */   @Size(max=200)
/*  77:    */   private String descripcion;
/*  78:    */   @Column(name="pdf", nullable=true)
/*  79:    */   @Size(max=50)
/*  80:    */   private String pdf;
/*  81:    */   @OneToOne(fetch=FetchType.LAZY)
/*  82:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  83:    */   private Asiento asiento;
/*  84:    */   @Column(name="indicador_cheque_entregado", nullable=false)
/*  85:    */   private boolean indicadorChequeEntregado;
/*  86:    */   @Column(name="indicador_tiene_cheques", nullable=false)
/*  87:    */   private boolean indicadorTieneCheques;
/*  88:    */   @Column(name="indicador_retencion_asumida", nullable=false)
/*  89:130 */   private boolean indicadorRetencionAsumida = false;
/*  90:    */   @Transient
/*  91:133 */   private BigDecimal tolerancia = BigDecimal.ZERO;
/*  92:    */   @OneToMany(mappedBy="pago", fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  93:136 */   private List<DetallePago> listaDetallePago = new ArrayList();
/*  94:    */   @OneToMany(mappedBy="pago", fetch=FetchType.LAZY)
/*  95:139 */   private List<DetalleFormaPago> listaDetalleFormaPago = new ArrayList();
/*  96:    */   @Temporal(TemporalType.DATE)
/*  97:    */   @Column(name="fecha_entrega_cheque", nullable=true)
/*  98:    */   private Date fechaEntregaCheque;
/*  99:    */   @Column(name="usuario_entrega_cheque", nullable=true)
/* 100:    */   private String usuarioEntregaCheque;
/* 101:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 102:    */   @JoinColumn(name="id_orden_pago_proveedor", nullable=true)
/* 103:    */   private OrdenPagoProveedor ordenPagoProveedor;
/* 104:    */   @Transient
/* 105:    */   private boolean indicadorPagoLiquidacion;
/* 106:    */   @Transient
/* 107:    */   private Date fechaPagoLiquidacion;
/* 108:    */   @Transient
/* 109:159 */   private boolean indicadorRetencion = false;
/* 110:    */   @Transient
/* 111:    */   private String numeroEgreso;
/* 112:    */   @Transient
/* 113:165 */   private boolean indicadorEdicionBloqueo = false;
/* 114:    */   
/* 115:    */   public int getId()
/* 116:    */   {
/* 117:175 */     return this.idPago;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getIdPago()
/* 121:    */   {
/* 122:187 */     return this.idPago;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdPago(int idPago)
/* 126:    */   {
/* 127:197 */     this.idPago = idPago;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getIdOrganizacion()
/* 131:    */   {
/* 132:206 */     return this.idOrganizacion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIdOrganizacion(int idOrganizacion)
/* 136:    */   {
/* 137:216 */     this.idOrganizacion = idOrganizacion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Sucursal getSucursal()
/* 141:    */   {
/* 142:223 */     return this.sucursal;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setSucursal(Sucursal sucursal)
/* 146:    */   {
/* 147:231 */     this.sucursal = sucursal;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Empresa getEmpresa()
/* 151:    */   {
/* 152:240 */     return this.empresa;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setEmpresa(Empresa empresa)
/* 156:    */   {
/* 157:250 */     this.empresa = empresa;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Documento getDocumento()
/* 161:    */   {
/* 162:259 */     return this.documento;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDocumento(Documento documento)
/* 166:    */   {
/* 167:269 */     this.documento = documento;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Date getFechaContabilizacion()
/* 171:    */   {
/* 172:278 */     return this.fechaContabilizacion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 176:    */   {
/* 177:288 */     this.fechaContabilizacion = fechaContabilizacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getNumero()
/* 181:    */   {
/* 182:297 */     return this.numero;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setNumero(String numero)
/* 186:    */   {
/* 187:307 */     this.numero = numero;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Date getFecha()
/* 191:    */   {
/* 192:316 */     return this.fecha;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setFecha(Date fecha)
/* 196:    */   {
/* 197:326 */     this.fecha = fecha;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public BigDecimal getValor()
/* 201:    */   {
/* 202:335 */     return this.valor;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setValor(BigDecimal valor)
/* 206:    */   {
/* 207:345 */     this.valor = valor;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getDescripcion()
/* 211:    */   {
/* 212:354 */     return this.descripcion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDescripcion(String descripcion)
/* 216:    */   {
/* 217:364 */     this.descripcion = descripcion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<DetallePago> getListaDetallePago()
/* 221:    */   {
/* 222:373 */     return this.listaDetallePago;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setListaDetallePago(List<DetallePago> listaDetallePago)
/* 226:    */   {
/* 227:383 */     this.listaDetallePago = listaDetallePago;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<DetalleFormaPago> getListaDetalleFormaPago()
/* 231:    */   {
/* 232:392 */     return this.listaDetalleFormaPago;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaDetalleFormaPago(List<DetalleFormaPago> listaDetalleFormaPago)
/* 236:    */   {
/* 237:402 */     this.listaDetalleFormaPago = listaDetalleFormaPago;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Estado getEstado()
/* 241:    */   {
/* 242:411 */     return this.estado;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setEstado(Estado estado)
/* 246:    */   {
/* 247:421 */     this.estado = estado;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Asiento getAsiento()
/* 251:    */   {
/* 252:430 */     return this.asiento;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setAsiento(Asiento asiento)
/* 256:    */   {
/* 257:440 */     this.asiento = asiento;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String toString()
/* 261:    */   {
/* 262:450 */     return this.numero == null ? "NUEVO REGISTRO AUN NO ALMECENADO EN LA BASE DE DATOS" : this.numero;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String getBeneficiario()
/* 266:    */   {
/* 267:454 */     return this.beneficiario;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setBeneficiario(String beneficiario)
/* 271:    */   {
/* 272:458 */     this.beneficiario = beneficiario;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public PagoCash getPagoCash()
/* 276:    */   {
/* 277:467 */     return this.pagoCash;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setPagoCash(PagoCash pagoCash)
/* 281:    */   {
/* 282:477 */     this.pagoCash = pagoCash;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public boolean isIndicadorChequeEntregado()
/* 286:    */   {
/* 287:486 */     return this.indicadorChequeEntregado;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setIndicadorChequeEntregado(boolean indicadorChequeEntregado)
/* 291:    */   {
/* 292:496 */     this.indicadorChequeEntregado = indicadorChequeEntregado;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Date getFechaEntregaCheque()
/* 296:    */   {
/* 297:505 */     return this.fechaEntregaCheque;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setFechaEntregaCheque(Date fechaEntregaCheque)
/* 301:    */   {
/* 302:515 */     this.fechaEntregaCheque = fechaEntregaCheque;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public String getUsuarioEntregaCheque()
/* 306:    */   {
/* 307:524 */     return this.usuarioEntregaCheque;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setUsuarioEntregaCheque(String usuarioEntregaCheque)
/* 311:    */   {
/* 312:534 */     this.usuarioEntregaCheque = usuarioEntregaCheque;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public boolean isIndicadorTieneCheques()
/* 316:    */   {
/* 317:543 */     return this.indicadorTieneCheques;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setIndicadorTieneCheques(boolean indicadorTieneCheques)
/* 321:    */   {
/* 322:553 */     this.indicadorTieneCheques = indicadorTieneCheques;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public boolean isIndicadorRetencionAsumida()
/* 326:    */   {
/* 327:560 */     return this.indicadorRetencionAsumida;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setIndicadorRetencionAsumida(boolean indicadorRetencionAsumida)
/* 331:    */   {
/* 332:568 */     this.indicadorRetencionAsumida = indicadorRetencionAsumida;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public BigDecimal getTolerancia()
/* 336:    */   {
/* 337:575 */     return this.tolerancia;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setTolerancia(BigDecimal tolerancia)
/* 341:    */   {
/* 342:583 */     this.tolerancia = tolerancia;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public boolean getIndicadorPagoLiquidacion()
/* 346:    */   {
/* 347:587 */     return this.indicadorPagoLiquidacion;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setIndicadorPagoLiquidacion(boolean indicadorPagoLiquidacion)
/* 351:    */   {
/* 352:591 */     this.indicadorPagoLiquidacion = indicadorPagoLiquidacion;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public Date getFechaPagoLiquidacion()
/* 356:    */   {
/* 357:595 */     return this.fechaPagoLiquidacion;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setFechaPagoLiquidacion(Date fechaPagoLiquidacion)
/* 361:    */   {
/* 362:599 */     this.fechaPagoLiquidacion = fechaPagoLiquidacion;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public boolean isIndicadorRetencion()
/* 366:    */   {
/* 367:606 */     return this.indicadorRetencion;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setIndicadorRetencion(boolean indicadorRetencion)
/* 371:    */   {
/* 372:614 */     this.indicadorRetencion = indicadorRetencion;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public String getNumeroEgreso()
/* 376:    */   {
/* 377:621 */     return this.numeroEgreso;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setNumeroEgreso(String numeroEgreso)
/* 381:    */   {
/* 382:629 */     this.numeroEgreso = numeroEgreso;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public OrdenPagoProveedor getOrdenPagoProveedor()
/* 386:    */   {
/* 387:633 */     return this.ordenPagoProveedor;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 391:    */   {
/* 392:637 */     this.ordenPagoProveedor = ordenPagoProveedor;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public boolean isIndicadorEdicionBloqueo()
/* 396:    */   {
/* 397:641 */     return this.indicadorEdicionBloqueo;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setIndicadorEdicionBloqueo(boolean indicadorEdicionBloqueo)
/* 401:    */   {
/* 402:645 */     this.indicadorEdicionBloqueo = indicadorEdicionBloqueo;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public String getPdf()
/* 406:    */   {
/* 407:649 */     return this.pdf;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setPdf(String pdf)
/* 411:    */   {
/* 412:653 */     this.pdf = pdf;
/* 413:    */   }
/* 414:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Pago
 * JD-Core Version:    0.7.0.1
 */