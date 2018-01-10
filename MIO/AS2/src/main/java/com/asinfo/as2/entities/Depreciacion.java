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
/*  25:    */ import javax.validation.constraints.Digits;
/*  26:    */ import javax.validation.constraints.Min;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ import org.hibernate.annotations.ColumnDefault;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="depreciacion", indexes={@javax.persistence.Index(columnList="id_activo_fijo")})
/*  33:    */ public class Depreciacion
/*  34:    */   extends EntidadBase
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="depreciacion", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="depreciacion")
/*  40:    */   @Column(name="id_depreciacion")
/*  41:    */   private int idDepreciacion;
/*  42:    */   @Column(name="id_organizacion", nullable=false)
/*  43:    */   private int idOrganizacion;
/*  44:    */   @Column(name="id_sucursal", nullable=false)
/*  45:    */   private int idSucursal;
/*  46:    */   @Column(name="fecha_inicio_depreciacion", nullable=false)
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @NotNull
/*  49:    */   private Date fechaInicioDepreciacion;
/*  50:    */   @Column(name="fecha_fin_depreciacion", nullable=true)
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   private Date fechaFinDepreciacion;
/*  53:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   private Date fechaContabilizacion;
/*  56:    */   @Column(name="valor_activo", nullable=false, precision=12, scale=2)
/*  57:    */   @Digits(integer=12, fraction=2)
/*  58:    */   @Min(0L)
/*  59: 85 */   private BigDecimal valorActivo = BigDecimal.ZERO;
/*  60:    */   @Column(name="valor_depreciado", nullable=false, precision=12, scale=2)
/*  61:    */   @Digits(integer=12, fraction=2)
/*  62:    */   @Min(0L)
/*  63: 90 */   private BigDecimal valorDepreciado = BigDecimal.ZERO;
/*  64:    */   @Column(name="valor_residual", nullable=false, precision=12, scale=2)
/*  65:    */   @Digits(integer=12, fraction=2)
/*  66:    */   @Min(0L)
/*  67: 95 */   private BigDecimal valorResidual = BigDecimal.ZERO;
/*  68:    */   @Column(name="valor_a_depreciar", nullable=false, precision=12, scale=2)
/*  69:    */   @Digits(integer=12, fraction=2)
/*  70:    */   @Min(0L)
/*  71:100 */   private BigDecimal valorADepreciar = BigDecimal.ZERO;
/*  72:    */   @Column(name="vida_util", nullable=false)
/*  73:    */   @Min(0L)
/*  74:    */   private int vidaUtil;
/*  75:    */   @Column(name="indicador_depreciacion_fiscal", nullable=false)
/*  76:    */   private boolean indicadorDepreciacionFiscal;
/*  77:    */   @Column(name="descripcion", nullable=true)
/*  78:    */   @Size(max=200)
/*  79:112 */   private String descripcion = "";
/*  80:    */   @Column(name="activo", nullable=false)
/*  81:    */   private boolean activo;
/*  82:    */   @Enumerated(EnumType.ORDINAL)
/*  83:    */   @Column(name="estado", nullable=false)
/*  84:    */   @NotNull
/*  85:    */   private Estado estado;
/*  86:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  87:    */   @JoinColumn(name="id_activo_fijo")
/*  88:    */   private ActivoFijo activoFijo;
/*  89:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="depreciacion")
/*  90:132 */   private List<DetalleDepreciacion> listaDetalleDepreciacion = new ArrayList();
/*  91:    */   @OneToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_depreciacion_padre", nullable=true)
/*  93:    */   private Depreciacion depreciacionPadre;
/*  94:    */   @OneToOne(fetch=FetchType.LAZY)
/*  95:    */   @JoinColumn(name="id_asiento_revalorizacion", nullable=true)
/*  96:    */   private Asiento asientoRevalorizacion;
/*  97:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_documento_revalorizacion", nullable=true)
/*  99:    */   private Documento documentoRevalorizacion;
/* 100:    */   @Column(name="vida_util_informativa", nullable=false)
/* 101:    */   @NotNull
/* 102:    */   @ColumnDefault("0")
/* 103:147 */   private int vidaUtilInformativa = 0;
/* 104:    */   @Transient
/* 105:    */   private BigDecimal total;
/* 106:    */   @Transient
/* 107:    */   private boolean traEsEditable;
/* 108:    */   
/* 109:    */   public int getIdDepreciacion()
/* 110:    */   {
/* 111:175 */     return this.idDepreciacion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setIdDepreciacion(int idDepreciacion)
/* 115:    */   {
/* 116:185 */     this.idDepreciacion = idDepreciacion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getIdOrganizacion()
/* 120:    */   {
/* 121:194 */     return this.idOrganizacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setIdOrganizacion(int idOrganizacion)
/* 125:    */   {
/* 126:204 */     this.idOrganizacion = idOrganizacion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getIdSucursal()
/* 130:    */   {
/* 131:213 */     return this.idSucursal;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setIdSucursal(int idSucursal)
/* 135:    */   {
/* 136:223 */     this.idSucursal = idSucursal;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Date getFechaInicioDepreciacion()
/* 140:    */   {
/* 141:232 */     return this.fechaInicioDepreciacion;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setFechaInicioDepreciacion(Date fechaInicioDepreciacion)
/* 145:    */   {
/* 146:242 */     this.fechaInicioDepreciacion = fechaInicioDepreciacion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Date getFechaFinDepreciacion()
/* 150:    */   {
/* 151:251 */     return this.fechaFinDepreciacion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setFechaFinDepreciacion(Date fechaFinDepreciacion)
/* 155:    */   {
/* 156:261 */     this.fechaFinDepreciacion = fechaFinDepreciacion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public BigDecimal getValorResidual()
/* 160:    */   {
/* 161:270 */     return this.valorResidual;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setValorResidual(BigDecimal valorResidual)
/* 165:    */   {
/* 166:280 */     this.valorResidual = valorResidual;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public BigDecimal getValorADepreciar()
/* 170:    */   {
/* 171:289 */     return this.valorADepreciar;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setValorADepreciar(BigDecimal valorADepreciar)
/* 175:    */   {
/* 176:299 */     this.valorADepreciar = valorADepreciar;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getVidaUtil()
/* 180:    */   {
/* 181:308 */     return this.vidaUtil;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setVidaUtil(int vidaUtil)
/* 185:    */   {
/* 186:318 */     this.vidaUtil = vidaUtil;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isIndicadorDepreciacionFiscal()
/* 190:    */   {
/* 191:327 */     return this.indicadorDepreciacionFiscal;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setIndicadorDepreciacionFiscal(boolean indicadorDepreciacionFiscal)
/* 195:    */   {
/* 196:337 */     this.indicadorDepreciacionFiscal = indicadorDepreciacionFiscal;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getDescripcion()
/* 200:    */   {
/* 201:346 */     return this.descripcion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setDescripcion(String descripcion)
/* 205:    */   {
/* 206:356 */     this.descripcion = descripcion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public ActivoFijo getActivoFijo()
/* 210:    */   {
/* 211:365 */     return this.activoFijo;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 215:    */   {
/* 216:375 */     this.activoFijo = activoFijo;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<DetalleDepreciacion> getListaDetalleDepreciacion()
/* 220:    */   {
/* 221:384 */     return this.listaDetalleDepreciacion;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setListaDetalleDepreciacion(List<DetalleDepreciacion> listaDetalleDepreciacion)
/* 225:    */   {
/* 226:394 */     this.listaDetalleDepreciacion = listaDetalleDepreciacion;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public BigDecimal getTotal()
/* 230:    */   {
/* 231:403 */     this.total = BigDecimal.ZERO;
/* 232:404 */     for (DetalleDepreciacion detalleDepreciacion : getListaDetalleDepreciacion()) {
/* 233:405 */       if (!detalleDepreciacion.isEliminado()) {
/* 234:406 */         this.total = this.total.add(detalleDepreciacion.getValor());
/* 235:    */       }
/* 236:    */     }
/* 237:409 */     return this.total;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setTotal(BigDecimal total)
/* 241:    */   {
/* 242:419 */     this.total = total;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public BigDecimal getValorActivo()
/* 246:    */   {
/* 247:428 */     return this.valorActivo;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setValorActivo(BigDecimal valorActivo)
/* 251:    */   {
/* 252:438 */     this.valorActivo = valorActivo;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public BigDecimal getValorDepreciado()
/* 256:    */   {
/* 257:447 */     return this.valorDepreciado;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setValorDepreciado(BigDecimal valorDepreciado)
/* 261:    */   {
/* 262:457 */     this.valorDepreciado = valorDepreciado;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public boolean isTraEsEditable()
/* 266:    */   {
/* 267:466 */     return this.traEsEditable;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setTraEsEditable(boolean traEsEditable)
/* 271:    */   {
/* 272:476 */     this.traEsEditable = traEsEditable;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public boolean isActivo()
/* 276:    */   {
/* 277:485 */     return this.activo;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setActivo(boolean activo)
/* 281:    */   {
/* 282:495 */     this.activo = activo;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public Depreciacion getDepreciacionPadre()
/* 286:    */   {
/* 287:504 */     return this.depreciacionPadre;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setDepreciacionPadre(Depreciacion depreciacionPadre)
/* 291:    */   {
/* 292:514 */     this.depreciacionPadre = depreciacionPadre;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Documento getDocumentoRevalorizacion()
/* 296:    */   {
/* 297:523 */     return this.documentoRevalorizacion;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setDocumentoRevalorizacion(Documento documentoRevalorizacion)
/* 301:    */   {
/* 302:533 */     this.documentoRevalorizacion = documentoRevalorizacion;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public Asiento getAsientoRevalorizacion()
/* 306:    */   {
/* 307:542 */     return this.asientoRevalorizacion;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setAsientoRevalorizacion(Asiento asientoRevalorizacion)
/* 311:    */   {
/* 312:552 */     this.asientoRevalorizacion = asientoRevalorizacion;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public Date getFechaContabilizacion()
/* 316:    */   {
/* 317:561 */     return this.fechaContabilizacion;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 321:    */   {
/* 322:571 */     this.fechaContabilizacion = fechaContabilizacion;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public Estado getEstado()
/* 326:    */   {
/* 327:580 */     return this.estado;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setEstado(Estado estado)
/* 331:    */   {
/* 332:590 */     this.estado = estado;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public int getId()
/* 336:    */   {
/* 337:600 */     return this.idDepreciacion;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public int getVidaUtilInformativa()
/* 341:    */   {
/* 342:607 */     return this.vidaUtilInformativa;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setVidaUtilInformativa(int vidaUtilInformativa)
/* 346:    */   {
/* 347:614 */     this.vidaUtilInformativa = vidaUtilInformativa;
/* 348:    */   }
/* 349:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Depreciacion
 * JD-Core Version:    0.7.0.1
 */