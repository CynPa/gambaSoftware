/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
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
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.DecimalMin;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.Max;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ import org.hibernate.annotations.ColumnDefault;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="producto_material")
/*  28:    */ public class ProductoMaterial
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="producto_material", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto_material")
/*  35:    */   @Column(name="id_producto_material")
/*  36:    */   private int idProductoMaterial;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   private int idSucursal;
/*  41:    */   @Temporal(TemporalType.DATE)
/*  42:    */   @Column(name="fecha_desde", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private Date fechaDesde;
/*  45:    */   @Temporal(TemporalType.DATE)
/*  46:    */   @Column(name="fecha_hasta", nullable=true)
/*  47:    */   private Date fechaHasta;
/*  48:    */   @Column(name="cantidad", nullable=false, precision=12, scale=6)
/*  49:    */   @Digits(integer=12, fraction=6)
/*  50: 77 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  51:    */   @Column(name="descripcion", nullable=true, length=200)
/*  52:    */   @Size(max=200)
/*  53:    */   private String descripcion;
/*  54:    */   @Column(name="indicador_porcentaje_factor_perdida", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private boolean indicadorPorcentajeFactorPerdida;
/*  57:    */   @Column(name="factor_perdida", nullable=false, precision=12, scale=2)
/*  58:    */   @Min(0L)
/*  59: 90 */   private BigDecimal factorPerdida = BigDecimal.ZERO;
/*  60:    */   @Column(name="orden", nullable=false)
/*  61:    */   private int orden;
/*  62:    */   @Column(name="proporcion", nullable=false, precision=12, scale=2)
/*  63:    */   @Min(0L)
/*  64:    */   @Max(100L)
/*  65: 97 */   private BigDecimal proporcion = new BigDecimal(100);
/*  66:    */   @Column(name="proporcion_material_principal_hijo", nullable=true, precision=12, scale=2)
/*  67:    */   @Min(0L)
/*  68:    */   @Max(100L)
/*  69:104 */   private BigDecimal proporcionMaterialPrincipalHijo = null;
/*  70:    */   @Column(name="activo", nullable=false)
/*  71:    */   @NotNull
/*  72:109 */   private boolean activo = true;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_producto", nullable=true)
/*  75:    */   private Producto producto;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_material", nullable=false)
/*  78:    */   private Producto material;
/*  79:    */   @Column(name="indicador_principal", nullable=true)
/*  80:125 */   private Boolean indicadorPrincipal = Boolean.valueOf(false);
/*  81:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  82:    */   @JoinColumn(name="id_sustituto", nullable=true)
/*  83:    */   private Producto sustituto;
/*  84:    */   @Column(name="cantidad_sustituto", nullable=true, precision=12, scale=6)
/*  85:    */   @Digits(integer=12, fraction=6)
/*  86:    */   @DecimalMin("0.000000")
/*  87:131 */   private BigDecimal cantidadSustituto = BigDecimal.ZERO;
/*  88:    */   @Column(name="indicador_explota", nullable=false)
/*  89:    */   @NotNull
/*  90:136 */   private boolean indicadorExplota = true;
/*  91:    */   @Column(name="indicador_genera_suborden", nullable=false)
/*  92:    */   @NotNull
/*  93:    */   @ColumnDefault("'0'")
/*  94:140 */   private boolean indicadorGeneraSuborden = false;
/*  95:    */   @Column(name="indicador_valida_stock_suborden", nullable=false)
/*  96:    */   @NotNull
/*  97:    */   @ColumnDefault("'0'")
/*  98:145 */   private boolean indicadorValidaStockSuborden = false;
/*  99:    */   @Transient
/* 100:    */   private int numeroRepetriciones;
/* 101:    */   
/* 102:    */   public int getId()
/* 103:    */   {
/* 104:171 */     return this.idProductoMaterial;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Producto getProducto()
/* 108:    */   {
/* 109:178 */     return this.producto;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setProducto(Producto producto)
/* 113:    */   {
/* 114:186 */     this.producto = producto;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Producto getMaterial()
/* 118:    */   {
/* 119:195 */     return this.material;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setMaterial(Producto material)
/* 123:    */   {
/* 124:205 */     this.material = material;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getIdProductoMaterial()
/* 128:    */   {
/* 129:214 */     return this.idProductoMaterial;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIdProductoMaterial(int idProductoMaterial)
/* 133:    */   {
/* 134:224 */     this.idProductoMaterial = idProductoMaterial;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public BigDecimal getCantidad()
/* 138:    */   {
/* 139:233 */     return this.cantidad;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setCantidad(BigDecimal cantidad)
/* 143:    */   {
/* 144:243 */     this.cantidad = cantidad;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getDescripcion()
/* 148:    */   {
/* 149:252 */     return this.descripcion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDescripcion(String descripcion)
/* 153:    */   {
/* 154:262 */     this.descripcion = descripcion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int getIdOrganizacion()
/* 158:    */   {
/* 159:271 */     return this.idOrganizacion;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIdOrganizacion(int idOrganizacion)
/* 163:    */   {
/* 164:281 */     this.idOrganizacion = idOrganizacion;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getIdSucursal()
/* 168:    */   {
/* 169:290 */     return this.idSucursal;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setIdSucursal(int idSucursal)
/* 173:    */   {
/* 174:300 */     this.idSucursal = idSucursal;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Date getFechaDesde()
/* 178:    */   {
/* 179:309 */     return this.fechaDesde;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setFechaDesde(Date fechaDesde)
/* 183:    */   {
/* 184:319 */     this.fechaDesde = fechaDesde;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Date getFechaHasta()
/* 188:    */   {
/* 189:328 */     return this.fechaHasta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setFechaHasta(Date fechaHasta)
/* 193:    */   {
/* 194:338 */     this.fechaHasta = fechaHasta;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public BigDecimal getFactorPerdida()
/* 198:    */   {
/* 199:347 */     return this.factorPerdida;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setFactorPerdida(BigDecimal factorPerdida)
/* 203:    */   {
/* 204:357 */     this.factorPerdida = factorPerdida;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public boolean isIndicadorPorcentajeFactorPerdida()
/* 208:    */   {
/* 209:366 */     return this.indicadorPorcentajeFactorPerdida;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setIndicadorPorcentajeFactorPerdida(boolean indicadorPorcentajeFactorPerdida)
/* 213:    */   {
/* 214:376 */     this.indicadorPorcentajeFactorPerdida = indicadorPorcentajeFactorPerdida;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public int getOrden()
/* 218:    */   {
/* 219:385 */     return this.orden;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setOrden(int orden)
/* 223:    */   {
/* 224:395 */     this.orden = orden;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public BigDecimal getProporcion()
/* 228:    */   {
/* 229:404 */     return this.proporcion;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setProporcion(BigDecimal proporcion)
/* 233:    */   {
/* 234:414 */     this.proporcion = proporcion;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Boolean getIndicadorPrincipal()
/* 238:    */   {
/* 239:418 */     if (this.indicadorPrincipal == null) {
/* 240:419 */       this.indicadorPrincipal = Boolean.valueOf(false);
/* 241:    */     }
/* 242:421 */     return this.indicadorPrincipal;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setIndicadorPrincipal(Boolean indicadorPrincipal)
/* 246:    */   {
/* 247:425 */     this.indicadorPrincipal = indicadorPrincipal;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Producto getSustituto()
/* 251:    */   {
/* 252:432 */     return this.sustituto;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setSustituto(Producto sustituto)
/* 256:    */   {
/* 257:440 */     this.sustituto = sustituto;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public BigDecimal getCantidadSustituto()
/* 261:    */   {
/* 262:447 */     return this.cantidadSustituto == null ? BigDecimal.ZERO : this.cantidadSustituto;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setCantidadSustituto(BigDecimal cantidadSustituto)
/* 266:    */   {
/* 267:455 */     this.cantidadSustituto = cantidadSustituto;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public BigDecimal getProporcionMaterialPrincipalHijo()
/* 271:    */   {
/* 272:459 */     return this.proporcionMaterialPrincipalHijo;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setProporcionMaterialPrincipalHijo(BigDecimal proporcionMaterialPrincipalHijo)
/* 276:    */   {
/* 277:463 */     this.proporcionMaterialPrincipalHijo = proporcionMaterialPrincipalHijo;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean isActivo()
/* 281:    */   {
/* 282:467 */     return this.activo;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setActivo(boolean activo)
/* 286:    */   {
/* 287:471 */     this.activo = activo;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean isIndicadorExplota()
/* 291:    */   {
/* 292:475 */     return this.indicadorExplota;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setIndicadorExplota(boolean indicadorExplota)
/* 296:    */   {
/* 297:479 */     this.indicadorExplota = indicadorExplota;
/* 298:480 */     if (indicadorExplota) {
/* 299:481 */       this.indicadorGeneraSuborden = false;
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean isIndicadorGeneraSuborden()
/* 304:    */   {
/* 305:486 */     return this.indicadorGeneraSuborden;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setIndicadorGeneraSuborden(boolean indicadorGeneraSuborden)
/* 309:    */   {
/* 310:490 */     this.indicadorGeneraSuborden = indicadorGeneraSuborden;
/* 311:491 */     if (indicadorGeneraSuborden) {
/* 312:492 */       this.indicadorExplota = false;
/* 313:    */     }
/* 314:    */   }
/* 315:    */   
/* 316:    */   public int getNumeroRepetriciones()
/* 317:    */   {
/* 318:497 */     return this.numeroRepetriciones;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setNumeroRepetriciones(int numeroRepetriciones)
/* 322:    */   {
/* 323:501 */     this.numeroRepetriciones = numeroRepetriciones;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public boolean isIndicadorValidaStockSuborden()
/* 327:    */   {
/* 328:505 */     return this.indicadorValidaStockSuborden;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setIndicadorValidaStockSuborden(boolean indicadorValidaStockSuborden)
/* 332:    */   {
/* 333:509 */     this.indicadorValidaStockSuborden = indicadorValidaStockSuborden;
/* 334:    */   }
/* 335:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ProductoMaterial
 * JD-Core Version:    0.7.0.1
 */