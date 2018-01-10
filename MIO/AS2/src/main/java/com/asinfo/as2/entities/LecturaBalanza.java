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
/*  16:    */ import javax.validation.constraints.DecimalMin;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="lectura_balanza", indexes={@javax.persistence.Index(columnList="id_detalle_orden_despacho_cliente")})
/*  23:    */ public class LecturaBalanza
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="lectura_balanza", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="lectura_balanza")
/*  31:    */   @Column(name="id_lectura_balanza", unique=true, nullable=false)
/*  32:    */   private int idLecturaBalanza;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private int idSucursal;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_producto", nullable=false)
/*  41:    */   private Producto producto;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_unidad_manejo", nullable=true)
/*  44:    */   private UnidadManejo unidadManejo;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_pallet", nullable=true)
/*  47:    */   private UnidadManejo pallet;
/*  48:    */   @Column(name="peso_bruto", nullable=false, precision=12, scale=2)
/*  49:    */   @Digits(integer=10, fraction=2)
/*  50:    */   @DecimalMin("0.00")
/*  51:    */   @NotNull
/*  52: 55 */   private BigDecimal pesoBruto = BigDecimal.ZERO;
/*  53:    */   @Column(name="numero_unidades_manejo", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   @Min(0L)
/*  56: 61 */   private int numeroUnidadesManejo = 1;
/*  57:    */   @Column(name="peso_neto", nullable=false, precision=12, scale=2)
/*  58:    */   @Digits(integer=10, fraction=2)
/*  59:    */   @DecimalMin("0.00")
/*  60:    */   @NotNull
/*  61: 66 */   private BigDecimal pesoNeto = BigDecimal.ZERO;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_detalle_orden_despacho_cliente", nullable=true)
/*  64:    */   private DetalleOrdenDespachoCliente detalleOrdenDespachoCliente;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_detalle_orden_salida_material", nullable=true)
/*  67:    */   private DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_detalle_movimiento_invetario", nullable=true)
/*  70:    */   private DetalleMovimientoInventario detalleMovimientoInventario;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_detalle_recepcion_proveedor", nullable=true)
/*  73:    */   private DetalleRecepcionProveedor detalleRecepcionProveedor;
/*  74:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  75:    */   @JoinColumn(name="id_detalle_orden_salida_material_orden_fabricacion", nullable=true)
/*  76:    */   private DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion;
/*  77:    */   @Column(name="indicador_recibido", nullable=false)
/*  78:    */   private boolean indicadorRecibido;
/*  79:    */   @Column(name="cantidad", nullable=true)
/*  80:    */   @Min(0L)
/*  81:    */   private Integer cantidad;
/*  82:    */   @Column(name="operacion", nullable=false)
/*  83:100 */   private int operacion = 1;
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_dispositivo", nullable=true)
/*  86:    */   private Dispositivo dispositivo;
/*  87:    */   @Transient
/*  88:    */   private UnidadManejoProducto unidadManejoProducto;
/*  89:    */   @Transient
/*  90:110 */   private String hotkeyFocoProducto = "ctrl+F9";
/*  91:    */   @Transient
/*  92:113 */   private String hotkeyCapturarPeso = "ctrl+F10";
/*  93:    */   @Transient
/*  94:116 */   private String hotkeyAgregarPeso = "ctrl+F11";
/*  95:    */   
/*  96:    */   public int getIdLecturaBalanza()
/*  97:    */   {
/*  98:124 */     return this.idLecturaBalanza;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdLecturaBalanza(int idLecturaBalanza)
/* 102:    */   {
/* 103:128 */     this.idLecturaBalanza = idLecturaBalanza;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdOrganizacion()
/* 107:    */   {
/* 108:132 */     return this.idOrganizacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdOrganizacion(int idOrganizacion)
/* 112:    */   {
/* 113:136 */     this.idOrganizacion = idOrganizacion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getIdSucursal()
/* 117:    */   {
/* 118:140 */     return this.idSucursal;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIdSucursal(int idSucursal)
/* 122:    */   {
/* 123:144 */     this.idSucursal = idSucursal;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Producto getProducto()
/* 127:    */   {
/* 128:148 */     return this.producto;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setProducto(Producto producto)
/* 132:    */   {
/* 133:152 */     this.producto = producto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public UnidadManejo getUnidadManejo()
/* 137:    */   {
/* 138:156 */     return this.unidadManejo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setUnidadManejo(UnidadManejo unidadManejo)
/* 142:    */   {
/* 143:160 */     this.unidadManejo = unidadManejo;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public UnidadManejo getPallet()
/* 147:    */   {
/* 148:164 */     return this.pallet;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setPallet(UnidadManejo pallet)
/* 152:    */   {
/* 153:168 */     this.pallet = pallet;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BigDecimal getPesoBruto()
/* 157:    */   {
/* 158:172 */     return this.pesoBruto;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setPesoBruto(BigDecimal pesoBruto)
/* 162:    */   {
/* 163:176 */     this.pesoBruto = pesoBruto;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getNumeroUnidadesManejo()
/* 167:    */   {
/* 168:180 */     return this.numeroUnidadesManejo;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setNumeroUnidadesManejo(int numeroUnidadesManejo)
/* 172:    */   {
/* 173:184 */     this.numeroUnidadesManejo = numeroUnidadesManejo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public BigDecimal getPesoNeto()
/* 177:    */   {
/* 178:188 */     return this.pesoNeto;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setPesoNeto(BigDecimal pesoNeto)
/* 182:    */   {
/* 183:192 */     this.pesoNeto = pesoNeto;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public DetalleOrdenDespachoCliente getDetalleOrdenDespachoCliente()
/* 187:    */   {
/* 188:196 */     return this.detalleOrdenDespachoCliente;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDetalleOrdenDespachoCliente(DetalleOrdenDespachoCliente detalleOrdenDespachoCliente)
/* 192:    */   {
/* 193:200 */     this.detalleOrdenDespachoCliente = detalleOrdenDespachoCliente;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public UnidadManejoProducto getUnidadManejoProducto()
/* 197:    */   {
/* 198:204 */     return this.unidadManejoProducto;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setUnidadManejoProducto(UnidadManejoProducto unidadManejoProducto)
/* 202:    */   {
/* 203:208 */     this.unidadManejoProducto = unidadManejoProducto;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int getId()
/* 207:    */   {
/* 208:213 */     return this.idLecturaBalanza;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Integer getCantidad()
/* 212:    */   {
/* 213:217 */     return this.cantidad;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setCantidad(Integer cantidad)
/* 217:    */   {
/* 218:221 */     this.cantidad = cantidad;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public DetalleOrdenSalidaMaterial getDetalleOrdenSalidaMaterial()
/* 222:    */   {
/* 223:225 */     return this.detalleOrdenSalidaMaterial;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setDetalleOrdenSalidaMaterial(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial)
/* 227:    */   {
/* 228:229 */     this.detalleOrdenSalidaMaterial = detalleOrdenSalidaMaterial;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getHotkeyFocoProducto()
/* 232:    */   {
/* 233:233 */     return this.hotkeyFocoProducto;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setHotkeyFocoProducto(String hotkeyFocoProducto)
/* 237:    */   {
/* 238:237 */     this.hotkeyFocoProducto = hotkeyFocoProducto;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getHotkeyCapturarPeso()
/* 242:    */   {
/* 243:241 */     return this.hotkeyCapturarPeso;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setHotkeyCapturarPeso(String hotkeyCapturarPeso)
/* 247:    */   {
/* 248:245 */     this.hotkeyCapturarPeso = hotkeyCapturarPeso;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getHotkeyAgregarPeso()
/* 252:    */   {
/* 253:249 */     return this.hotkeyAgregarPeso;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setHotkeyAgregarPeso(String hotkeyAgregarPeso)
/* 257:    */   {
/* 258:253 */     this.hotkeyAgregarPeso = hotkeyAgregarPeso;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public boolean isIndicadorRecibido()
/* 262:    */   {
/* 263:257 */     return this.indicadorRecibido;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setIndicadorRecibido(boolean indicadorRecibido)
/* 267:    */   {
/* 268:261 */     this.indicadorRecibido = indicadorRecibido;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public int getOperacion()
/* 272:    */   {
/* 273:265 */     return this.operacion;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setOperacion(int operacion)
/* 277:    */   {
/* 278:269 */     this.operacion = operacion;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public DetalleMovimientoInventario getDetalleMovimientoInventario()
/* 282:    */   {
/* 283:273 */     return this.detalleMovimientoInventario;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setDetalleMovimientoInventario(DetalleMovimientoInventario detalleMovimientoInventario)
/* 287:    */   {
/* 288:277 */     this.detalleMovimientoInventario = detalleMovimientoInventario;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public DetalleRecepcionProveedor getDetalleRecepcionProveedor()
/* 292:    */   {
/* 293:281 */     return this.detalleRecepcionProveedor;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setDetalleRecepcionProveedor(DetalleRecepcionProveedor detalleRecepcionProveedor)
/* 297:    */   {
/* 298:285 */     this.detalleRecepcionProveedor = detalleRecepcionProveedor;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public Dispositivo getDispositivo()
/* 302:    */   {
/* 303:289 */     return this.dispositivo;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setDispositivo(Dispositivo dispositivo)
/* 307:    */   {
/* 308:293 */     this.dispositivo = dispositivo;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public DetalleOrdenSalidaMaterialOrdenFabricacion getDetalleOrdenSalidaMaterialOrdenFabricacion()
/* 312:    */   {
/* 313:297 */     return this.detalleOrdenSalidaMaterialOrdenFabricacion;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setDetalleOrdenSalidaMaterialOrdenFabricacion(DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion)
/* 317:    */   {
/* 318:301 */     this.detalleOrdenSalidaMaterialOrdenFabricacion = detalleOrdenSalidaMaterialOrdenFabricacion;
/* 319:    */   }
/* 320:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.LecturaBalanza
 * JD-Core Version:    0.7.0.1
 */