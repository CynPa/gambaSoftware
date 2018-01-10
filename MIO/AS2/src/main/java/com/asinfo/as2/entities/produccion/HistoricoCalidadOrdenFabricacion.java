/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.Lote;
/*   7:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.calidad.MotivoCastigoCalidad;
/*  10:    */ import com.asinfo.as2.entities.calidad.VariableCalidadOrdenFabricacion;
/*  11:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  12:    */ import java.math.BigDecimal;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.persistence.Column;
/*  17:    */ import javax.persistence.Entity;
/*  18:    */ import javax.persistence.EnumType;
/*  19:    */ import javax.persistence.Enumerated;
/*  20:    */ import javax.persistence.FetchType;
/*  21:    */ import javax.persistence.GeneratedValue;
/*  22:    */ import javax.persistence.GenerationType;
/*  23:    */ import javax.persistence.Id;
/*  24:    */ import javax.persistence.JoinColumn;
/*  25:    */ import javax.persistence.ManyToOne;
/*  26:    */ import javax.persistence.OneToMany;
/*  27:    */ import javax.persistence.Table;
/*  28:    */ import javax.persistence.TableGenerator;
/*  29:    */ import javax.persistence.Temporal;
/*  30:    */ import javax.persistence.TemporalType;
/*  31:    */ import javax.persistence.Transient;
/*  32:    */ import javax.validation.constraints.DecimalMin;
/*  33:    */ import javax.validation.constraints.Digits;
/*  34:    */ import javax.validation.constraints.NotNull;
/*  35:    */ import javax.validation.constraints.Size;
/*  36:    */ 
/*  37:    */ @Entity
/*  38:    */ @Table(name="historico_calidad_orden_fabricacion")
/*  39:    */ public class HistoricoCalidadOrdenFabricacion
/*  40:    */   extends EntidadBase
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 43902058619899582L;
/*  43:    */   @Id
/*  44:    */   @TableGenerator(name="historico_calidad_orden_fabricacion", initialValue=0, allocationSize=50)
/*  45:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="historico_calidad_orden_fabricacion")
/*  46:    */   @Column(name="id_historico_calidad_orden_fabricacion")
/*  47:    */   private int idHistoricoCalidadOrdenFabricacion;
/*  48:    */   @Column(name="id_organizacion", nullable=false)
/*  49:    */   private int idOrganizacion;
/*  50:    */   @Column(name="id_sucursal", nullable=false)
/*  51:    */   private int idSucursal;
/*  52:    */   @NotNull
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_orden_fabricacion", nullable=false)
/*  55:    */   private OrdenFabricacion ordenFabricacion;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_lote", nullable=true)
/*  58:    */   private Lote lote;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_motivo_castigo_calidad", nullable=true)
/*  61:    */   private MotivoCastigoCalidad motivoCastigoCalidad;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_transformacion", nullable=true)
/*  64:    */   private MovimientoInventario transformacion;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_producto_nuevo", nullable=true)
/*  67:    */   private Producto productoNuevo;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_bodega_destino", nullable=true)
/*  70:    */   private Bodega bodegaDestino;
/*  71:    */   @Column(name="estado", nullable=false)
/*  72:    */   @Enumerated(EnumType.ORDINAL)
/*  73:    */   private EstadoControlCalidad estado;
/*  74:    */   @Column(name="indicador_cambio_producto", nullable=false)
/*  75:    */   @NotNull
/*  76:    */   private boolean indicadorCambioProducto;
/*  77:    */   @OneToMany(mappedBy="historicoCalidadOrdenFabricacion", fetch=FetchType.LAZY)
/*  78:109 */   private List<VariableCalidadOrdenFabricacion> listaVariableCalidadOrdenFabricacion = new ArrayList();
/*  79:    */   @Column(name="cantidad", precision=12, scale=4, nullable=false)
/*  80:    */   @Digits(integer=12, fraction=4)
/*  81:    */   @DecimalMin("0.00")
/*  82:    */   @NotNull
/*  83:    */   private BigDecimal cantidad;
/*  84:    */   @Temporal(TemporalType.TIMESTAMP)
/*  85:    */   @Column(name="fecha_control_calidad", nullable=false)
/*  86:    */   private Date fechaControlCalidad;
/*  87:    */   @Column(name="descripcion", nullable=true, length=200)
/*  88:    */   @Size(max=200)
/*  89:    */   private String descripcion;
/*  90:    */   @Column(name="motivo_cambio_estado", nullable=true, length=200)
/*  91:    */   @Size(max=200)
/*  92:    */   private String motivoCambioEstado;
/*  93:    */   @Column(name="observacion_castigo_calidad", nullable=true, length=200)
/*  94:    */   @Size(max=200)
/*  95:    */   private String observacionCastigoCalidad;
/*  96:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_detalle_movimiento_inventario", nullable=true)
/*  98:    */   private DetalleMovimientoInventario detalleIngresoFabricacion;
/*  99:    */   @Transient
/* 100:138 */   private BigDecimal cantidadControlCalidad = BigDecimal.ZERO;
/* 101:    */   
/* 102:    */   public int getId()
/* 103:    */   {
/* 104:143 */     return this.idHistoricoCalidadOrdenFabricacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getIdHistoricoCalidadOrdenFabricacion()
/* 108:    */   {
/* 109:147 */     return this.idHistoricoCalidadOrdenFabricacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setIdHistoricoCalidadOrdenFabricacion(int idHistoricoCalidadOrdenFabricacion)
/* 113:    */   {
/* 114:151 */     this.idHistoricoCalidadOrdenFabricacion = idHistoricoCalidadOrdenFabricacion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getIdOrganizacion()
/* 118:    */   {
/* 119:155 */     return this.idOrganizacion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdOrganizacion(int idOrganizacion)
/* 123:    */   {
/* 124:159 */     this.idOrganizacion = idOrganizacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getIdSucursal()
/* 128:    */   {
/* 129:163 */     return this.idSucursal;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIdSucursal(int idSucursal)
/* 133:    */   {
/* 134:167 */     this.idSucursal = idSucursal;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public OrdenFabricacion getOrdenFabricacion()
/* 138:    */   {
/* 139:171 */     return this.ordenFabricacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 143:    */   {
/* 144:175 */     this.ordenFabricacion = ordenFabricacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public EstadoControlCalidad getEstado()
/* 148:    */   {
/* 149:179 */     return this.estado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setEstado(EstadoControlCalidad estado)
/* 153:    */   {
/* 154:183 */     this.estado = estado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isIndicadorCambioProducto()
/* 158:    */   {
/* 159:187 */     return this.indicadorCambioProducto;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIndicadorCambioProducto(boolean indicadorCambioProducto)
/* 163:    */   {
/* 164:191 */     this.indicadorCambioProducto = indicadorCambioProducto;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<VariableCalidadOrdenFabricacion> getListaVariableCalidadOrdenFabricacion()
/* 168:    */   {
/* 169:195 */     return this.listaVariableCalidadOrdenFabricacion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setListaVariableCalidadOrdenFabricacion(List<VariableCalidadOrdenFabricacion> listaVariableCalidadOrdenFabricacion)
/* 173:    */   {
/* 174:199 */     this.listaVariableCalidadOrdenFabricacion = listaVariableCalidadOrdenFabricacion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public BigDecimal getCantidad()
/* 178:    */   {
/* 179:203 */     return this.cantidad;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setCantidad(BigDecimal cantidad)
/* 183:    */   {
/* 184:207 */     this.cantidad = cantidad;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Date getFechaControlCalidad()
/* 188:    */   {
/* 189:211 */     return this.fechaControlCalidad;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setFechaControlCalidad(Date fechaControlCalidad)
/* 193:    */   {
/* 194:215 */     this.fechaControlCalidad = fechaControlCalidad;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getDescripcion()
/* 198:    */   {
/* 199:219 */     return this.descripcion;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setDescripcion(String descripcion)
/* 203:    */   {
/* 204:223 */     this.descripcion = descripcion;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Lote getLote()
/* 208:    */   {
/* 209:227 */     return this.lote;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setLote(Lote lote)
/* 213:    */   {
/* 214:231 */     this.lote = lote;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public MotivoCastigoCalidad getMotivoCastigoCalidad()
/* 218:    */   {
/* 219:235 */     return this.motivoCastigoCalidad;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setMotivoCastigoCalidad(MotivoCastigoCalidad motivoCastigoCalidad)
/* 223:    */   {
/* 224:239 */     this.motivoCastigoCalidad = motivoCastigoCalidad;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getObservacionCastigoCalidad()
/* 228:    */   {
/* 229:243 */     return this.observacionCastigoCalidad;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setObservacionCastigoCalidad(String observacionCastigoCalidad)
/* 233:    */   {
/* 234:247 */     this.observacionCastigoCalidad = observacionCastigoCalidad;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Producto getProductoNuevo()
/* 238:    */   {
/* 239:251 */     return this.productoNuevo;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setProductoNuevo(Producto productoNuevo)
/* 243:    */   {
/* 244:255 */     this.productoNuevo = productoNuevo;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public MovimientoInventario getTransformacion()
/* 248:    */   {
/* 249:259 */     return this.transformacion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setTransformacion(MovimientoInventario transformacion)
/* 253:    */   {
/* 254:263 */     this.transformacion = transformacion;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public DetalleMovimientoInventario getDetalleIngresoFabricacion()
/* 258:    */   {
/* 259:267 */     return this.detalleIngresoFabricacion;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setDetalleIngresoFabricacion(DetalleMovimientoInventario detalleIngresoFabricacion)
/* 263:    */   {
/* 264:271 */     this.detalleIngresoFabricacion = detalleIngresoFabricacion;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public BigDecimal getCantidadLiberado()
/* 268:    */   {
/* 269:275 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 270:276 */     if (this.estado.equals(EstadoControlCalidad.LIBERADO)) {
/* 271:277 */       cantidad = this.cantidad;
/* 272:    */     }
/* 273:279 */     return cantidad;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public BigDecimal getCantidadRechazado()
/* 277:    */   {
/* 278:283 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 279:284 */     if (this.estado.equals(EstadoControlCalidad.RECHAZADO)) {
/* 280:285 */       cantidad = this.cantidad;
/* 281:    */     }
/* 282:287 */     return cantidad;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public BigDecimal getCantidadCuarentena()
/* 286:    */   {
/* 287:291 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 288:292 */     if (this.estado.equals(EstadoControlCalidad.CUARENTENA)) {
/* 289:293 */       cantidad = this.cantidad;
/* 290:    */     }
/* 291:295 */     return cantidad;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public BigDecimal getCantidadReproceso()
/* 295:    */   {
/* 296:299 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 297:300 */     if (this.estado.equals(EstadoControlCalidad.REPROCESO)) {
/* 298:301 */       cantidad = this.cantidad;
/* 299:    */     }
/* 300:303 */     return cantidad;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public String getMotivoCambioEstado()
/* 304:    */   {
/* 305:307 */     return this.motivoCambioEstado;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setMotivoCambioEstado(String motivoCambioEstado)
/* 309:    */   {
/* 310:311 */     this.motivoCambioEstado = motivoCambioEstado;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public Bodega getBodegaDestino()
/* 314:    */   {
/* 315:315 */     return this.bodegaDestino;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setBodegaDestino(Bodega bodegaDestino)
/* 319:    */   {
/* 320:319 */     this.bodegaDestino = bodegaDestino;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public BigDecimal getCantidadControlCalidad()
/* 324:    */   {
/* 325:323 */     return this.cantidadControlCalidad;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setCantidadControlCalidad(BigDecimal cantidadControlCalidad)
/* 329:    */   {
/* 330:327 */     this.cantidadControlCalidad = cantidadControlCalidad;
/* 331:    */   }
/* 332:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */