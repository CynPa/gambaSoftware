/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.math.RoundingMode;
/*   7:    */ import java.util.Date;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="tmp_reporte_inventario_producto")
/*  19:    */ public class ReporteInventarioProducto
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="tmp_reporte_inventario_producto", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_reporte_inventario_producto")
/*  26:    */   @Column(name="id_tmp_reporte_inventario_producto")
/*  27:    */   private Integer idReporteInventarioProducto;
/*  28:    */   @Transient
/*  29:    */   private Date fecha;
/*  30:    */   @Transient
/*  31:    */   private String documento;
/*  32:    */   @Transient
/*  33:    */   private String numeroDocumento;
/*  34:    */   @Transient
/*  35: 66 */   private String unidadDocumento = "";
/*  36:    */   @Transient
/*  37:    */   private Integer numeroDecimales;
/*  38:    */   @Transient
/*  39:    */   private String bodega;
/*  40:    */   @Transient
/*  41:    */   private int idProducto;
/*  42:    */   @Transient
/*  43:    */   private Producto producto;
/*  44:    */   @Transient
/*  45:    */   private String facturaCliente;
/*  46:    */   @Transient
/*  47:    */   private Integer operacion;
/*  48:    */   @Transient
/*  49:    */   private Integer orden;
/*  50:    */   @Transient
/*  51: 90 */   private BigDecimal cantidadDocumento = BigDecimal.ZERO;
/*  52:    */   @Transient
/*  53: 93 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  54:    */   @Transient
/*  55: 96 */   private BigDecimal costo = BigDecimal.ZERO;
/*  56:    */   @Transient
/*  57: 99 */   private BigDecimal cantidadTotal = BigDecimal.ZERO;
/*  58:    */   @Transient
/*  59:102 */   private BigDecimal costoTotal = BigDecimal.ZERO;
/*  60:    */   @Transient
/*  61:105 */   private BigDecimal costoUnitario = BigDecimal.ZERO;
/*  62:    */   @Transient
/*  63:    */   private Integer idFacturaCliente;
/*  64:    */   @Transient
/*  65:    */   private String lote;
/*  66:    */   @Transient
/*  67:    */   private String descripcionMovimientoInventario;
/*  68:    */   @Transient
/*  69:    */   private String nombreProyecto;
/*  70:    */   @Transient
/*  71:    */   private String nombreComercialProducto;
/*  72:    */   @Transient
/*  73:    */   private String codigoAlternoProducto;
/*  74:    */   @Transient
/*  75:    */   private String identificacionEmpresa;
/*  76:    */   @Transient
/*  77:    */   private String nombreComercialEmpresa;
/*  78:    */   @Transient
/*  79:    */   private String nombreFiscalEmpresa;
/*  80:    */   @Transient
/*  81:    */   private String valorAtributo1;
/*  82:    */   @Transient
/*  83:    */   private String valorAtributo2;
/*  84:    */   @Transient
/*  85:    */   private String valorAtributo3;
/*  86:    */   @Transient
/*  87:    */   private String valorAtributo4;
/*  88:    */   @Transient
/*  89:    */   private String valorAtributo5;
/*  90:    */   @Transient
/*  91:    */   private String valorAtributo6;
/*  92:    */   @Transient
/*  93:    */   private String valorAtributo7;
/*  94:    */   @Transient
/*  95:    */   private String valorAtributo8;
/*  96:    */   @Transient
/*  97:    */   private String valorAtributo9;
/*  98:    */   @Transient
/*  99:    */   private String valorAtributo10;
/* 100:    */   
/* 101:    */   public ReporteInventarioProducto() {}
/* 102:    */   
/* 103:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, Integer numeroDecimales)
/* 104:    */   {
/* 105:191 */     this.fecha = fecha;
/* 106:192 */     this.documento = documento;
/* 107:193 */     this.numeroDocumento = numeroDocumento;
/* 108:194 */     this.unidadDocumento = unidadDocumento;
/* 109:195 */     this.bodega = bodega;
/* 110:196 */     this.idProducto = idProducto;
/* 111:197 */     this.facturaCliente = facturaCliente;
/* 112:198 */     this.operacion = operacion;
/* 113:199 */     this.orden = orden;
/* 114:200 */     this.cantidadDocumento = cantidadDocumento;
/* 115:201 */     this.cantidad = cantidad;
/* 116:202 */     this.costo = costo;
/* 117:203 */     this.idFacturaCliente = idFacturaCliente;
/* 118:204 */     this.lote = lote;
/* 119:205 */     this.descripcionMovimientoInventario = descripcionMovimientoInventario;
/* 120:206 */     this.numeroDecimales = numeroDecimales;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, Integer numeroDecimales)
/* 124:    */   {
/* 125:214 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, numeroDecimales);
/* 126:    */     
/* 127:    */ 
/* 128:217 */     this.producto = new Producto();
/* 129:218 */     this.producto.setIdProducto(this.idProducto);
/* 130:219 */     this.producto.setCodigo(codigoProducto);
/* 131:220 */     this.producto.setNombre(nombreProducto);
/* 132:    */     
/* 133:222 */     this.nombreProyecto = nombreProyecto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales)
/* 137:    */   {
/* 138:232 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, numeroDecimales);
/* 139:    */     
/* 140:    */ 
/* 141:235 */     this.nombreComercialProducto = nombreComercialProducto;
/* 142:236 */     this.codigoAlternoProducto = codigoAlternoProducto;
/* 143:237 */     this.identificacionEmpresa = identificacionEmpresa;
/* 144:238 */     this.nombreComercialEmpresa = nombreComercialEmpresa;
/* 145:239 */     this.nombreFiscalEmpresa = nombreFiscalEmpresa;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1)
/* 149:    */   {
/* 150:249 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 151:    */     
/* 152:    */ 
/* 153:    */ 
/* 154:253 */     this.valorAtributo1 = valorAtributo1;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2)
/* 158:    */   {
/* 159:263 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 160:    */     
/* 161:    */ 
/* 162:    */ 
/* 163:267 */     this.valorAtributo1 = valorAtributo1;
/* 164:268 */     this.valorAtributo2 = valorAtributo2;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3)
/* 168:    */   {
/* 169:278 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 170:    */     
/* 171:    */ 
/* 172:    */ 
/* 173:282 */     this.valorAtributo1 = valorAtributo1;
/* 174:283 */     this.valorAtributo2 = valorAtributo2;
/* 175:284 */     this.valorAtributo3 = valorAtributo3;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4)
/* 179:    */   {
/* 180:294 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 181:    */     
/* 182:    */ 
/* 183:    */ 
/* 184:298 */     this.valorAtributo1 = valorAtributo1;
/* 185:299 */     this.valorAtributo2 = valorAtributo2;
/* 186:300 */     this.valorAtributo3 = valorAtributo3;
/* 187:301 */     this.valorAtributo4 = valorAtributo4;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5)
/* 191:    */   {
/* 192:311 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 193:    */     
/* 194:    */ 
/* 195:    */ 
/* 196:315 */     this.valorAtributo1 = valorAtributo1;
/* 197:316 */     this.valorAtributo2 = valorAtributo2;
/* 198:317 */     this.valorAtributo3 = valorAtributo3;
/* 199:318 */     this.valorAtributo4 = valorAtributo4;
/* 200:319 */     this.valorAtributo5 = valorAtributo5;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6)
/* 204:    */   {
/* 205:332 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 206:    */     
/* 207:    */ 
/* 208:    */ 
/* 209:336 */     this.valorAtributo1 = valorAtributo1;
/* 210:337 */     this.valorAtributo2 = valorAtributo2;
/* 211:338 */     this.valorAtributo3 = valorAtributo3;
/* 212:339 */     this.valorAtributo4 = valorAtributo4;
/* 213:340 */     this.valorAtributo5 = valorAtributo5;
/* 214:341 */     this.valorAtributo6 = valorAtributo6;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7)
/* 218:    */   {
/* 219:351 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 220:    */     
/* 221:    */ 
/* 222:    */ 
/* 223:355 */     this.valorAtributo1 = valorAtributo1;
/* 224:356 */     this.valorAtributo2 = valorAtributo2;
/* 225:357 */     this.valorAtributo3 = valorAtributo3;
/* 226:358 */     this.valorAtributo4 = valorAtributo4;
/* 227:359 */     this.valorAtributo5 = valorAtributo5;
/* 228:360 */     this.valorAtributo6 = valorAtributo6;
/* 229:361 */     this.valorAtributo7 = valorAtributo7;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7, String valorAtributo8)
/* 233:    */   {
/* 234:372 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 235:    */     
/* 236:    */ 
/* 237:    */ 
/* 238:376 */     this.valorAtributo1 = valorAtributo1;
/* 239:377 */     this.valorAtributo2 = valorAtributo2;
/* 240:378 */     this.valorAtributo3 = valorAtributo3;
/* 241:379 */     this.valorAtributo4 = valorAtributo4;
/* 242:380 */     this.valorAtributo5 = valorAtributo5;
/* 243:381 */     this.valorAtributo6 = valorAtributo6;
/* 244:382 */     this.valorAtributo7 = valorAtributo7;
/* 245:383 */     this.valorAtributo8 = valorAtributo8;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7, String valorAtributo8, String valorAtributo9)
/* 249:    */   {
/* 250:394 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 251:    */     
/* 252:    */ 
/* 253:    */ 
/* 254:398 */     this.valorAtributo1 = valorAtributo1;
/* 255:399 */     this.valorAtributo2 = valorAtributo2;
/* 256:400 */     this.valorAtributo3 = valorAtributo3;
/* 257:401 */     this.valorAtributo4 = valorAtributo4;
/* 258:402 */     this.valorAtributo5 = valorAtributo5;
/* 259:403 */     this.valorAtributo6 = valorAtributo6;
/* 260:404 */     this.valorAtributo7 = valorAtributo7;
/* 261:405 */     this.valorAtributo8 = valorAtributo8;
/* 262:406 */     this.valorAtributo9 = valorAtributo9;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public ReporteInventarioProducto(Date fecha, String documento, String numeroDocumento, String unidadDocumento, String bodega, int idProducto, String facturaCliente, Integer operacion, Integer orden, BigDecimal cantidadDocumento, BigDecimal cantidad, BigDecimal costo, Integer idFacturaCliente, String lote, String descripcionMovimientoInventario, String codigoProducto, String nombreProducto, String nombreProyecto, String nombreComercialProducto, String codigoAlternoProducto, String identificacionEmpresa, String nombreComercialEmpresa, String nombreFiscalEmpresa, Integer numeroDecimales, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7, String valorAtributo8, String valorAtributo9, String valorAtributo10)
/* 266:    */   {
/* 267:417 */     this(fecha, documento, numeroDocumento, unidadDocumento, bodega, idProducto, facturaCliente, operacion, orden, cantidadDocumento, cantidad, costo, idFacturaCliente, lote, descripcionMovimientoInventario, codigoProducto, nombreProducto, nombreProyecto, nombreComercialProducto, codigoAlternoProducto, identificacionEmpresa, nombreComercialEmpresa, nombreFiscalEmpresa, numeroDecimales);
/* 268:    */     
/* 269:    */ 
/* 270:    */ 
/* 271:421 */     this.valorAtributo1 = valorAtributo1;
/* 272:422 */     this.valorAtributo2 = valorAtributo2;
/* 273:423 */     this.valorAtributo3 = valorAtributo3;
/* 274:424 */     this.valorAtributo4 = valorAtributo4;
/* 275:425 */     this.valorAtributo5 = valorAtributo5;
/* 276:426 */     this.valorAtributo6 = valorAtributo6;
/* 277:427 */     this.valorAtributo7 = valorAtributo7;
/* 278:428 */     this.valorAtributo8 = valorAtributo8;
/* 279:429 */     this.valorAtributo9 = valorAtributo9;
/* 280:430 */     this.valorAtributo10 = valorAtributo10;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public Integer getIdReporteInventarioProducto()
/* 284:    */   {
/* 285:439 */     return this.idReporteInventarioProducto;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setIdReporteInventarioProducto(Integer idReporteInventarioProducto)
/* 289:    */   {
/* 290:449 */     this.idReporteInventarioProducto = idReporteInventarioProducto;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public Date getFecha()
/* 294:    */   {
/* 295:458 */     return this.fecha;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setFecha(Date fecha)
/* 299:    */   {
/* 300:468 */     this.fecha = fecha;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public String getDocumento()
/* 304:    */   {
/* 305:477 */     return this.documento;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setDocumento(String documento)
/* 309:    */   {
/* 310:487 */     this.documento = documento;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public String getNumeroDocumento()
/* 314:    */   {
/* 315:496 */     return this.numeroDocumento;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setNumeroDocumento(String numeroDocumento)
/* 319:    */   {
/* 320:506 */     this.numeroDocumento = numeroDocumento;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public String getUnidadDocumento()
/* 324:    */   {
/* 325:515 */     return this.unidadDocumento;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setUnidadDocumento(String unidadDocumento)
/* 329:    */   {
/* 330:525 */     this.unidadDocumento = unidadDocumento;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public String getBodega()
/* 334:    */   {
/* 335:534 */     return this.bodega;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setBodega(String bodega)
/* 339:    */   {
/* 340:544 */     this.bodega = bodega;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public Producto getProducto()
/* 344:    */   {
/* 345:553 */     return this.producto;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setProducto(Producto producto)
/* 349:    */   {
/* 350:563 */     this.producto = producto;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public String getFacturaCliente()
/* 354:    */   {
/* 355:572 */     return this.facturaCliente;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setFacturaCliente(String facturaCliente)
/* 359:    */   {
/* 360:582 */     this.facturaCliente = facturaCliente;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public Integer getOperacion()
/* 364:    */   {
/* 365:591 */     return this.operacion;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setOperacion(Integer operacion)
/* 369:    */   {
/* 370:601 */     this.operacion = operacion;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public Integer getOrden()
/* 374:    */   {
/* 375:610 */     return this.orden;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void setOrden(Integer orden)
/* 379:    */   {
/* 380:620 */     this.orden = orden;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public BigDecimal getCantidadDocumento()
/* 384:    */   {
/* 385:629 */     this.cantidadDocumento = this.cantidadDocumento.setScale(getNumeroDecimales().intValue(), RoundingMode.DOWN);
/* 386:630 */     return this.cantidadDocumento;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setCantidadDocumento(BigDecimal cantidadDocumento)
/* 390:    */   {
/* 391:640 */     this.cantidadDocumento = cantidadDocumento;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public BigDecimal getCantidad()
/* 395:    */   {
/* 396:649 */     this.cantidad = this.cantidad.setScale(getNumeroDecimales().intValue(), RoundingMode.DOWN);
/* 397:650 */     return this.cantidad;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setCantidad(BigDecimal cantidad)
/* 401:    */   {
/* 402:660 */     this.cantidad = cantidad;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public BigDecimal getCosto()
/* 406:    */   {
/* 407:669 */     return this.costo;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setCosto(BigDecimal costo)
/* 411:    */   {
/* 412:679 */     this.costo = costo;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public BigDecimal getCantidadTotal()
/* 416:    */   {
/* 417:688 */     this.cantidadTotal = this.cantidadTotal.setScale(getNumeroDecimales().intValue(), RoundingMode.DOWN);
/* 418:689 */     return this.cantidadTotal;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void setCantidadTotal(BigDecimal cantidadTotal)
/* 422:    */   {
/* 423:699 */     this.cantidadTotal = cantidadTotal;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public BigDecimal getCostoTotal()
/* 427:    */   {
/* 428:708 */     return this.costoTotal;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setCostoTotal(BigDecimal costoTotal)
/* 432:    */   {
/* 433:718 */     this.costoTotal = costoTotal;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public BigDecimal getCostoUnitario()
/* 437:    */   {
/* 438:727 */     this.costoUnitario = BigDecimal.ZERO;
/* 439:728 */     if (this.cantidad.compareTo(BigDecimal.ZERO) != 0) {
/* 440:729 */       this.costoUnitario = this.costo.divide(this.cantidad, 4, RoundingMode.HALF_UP);
/* 441:    */     }
/* 442:731 */     return this.costoUnitario;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public BigDecimal getCostoPromedio()
/* 446:    */   {
/* 447:740 */     BigDecimal costoPromedio = BigDecimal.ZERO;
/* 448:741 */     if (this.cantidadTotal.compareTo(BigDecimal.ZERO) != 0) {
/* 449:742 */       costoPromedio = this.costoTotal.divide(this.cantidadTotal, 4, RoundingMode.HALF_UP);
/* 450:    */     }
/* 451:744 */     return costoPromedio;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setCostoUnitario(BigDecimal costoUnitario)
/* 455:    */   {
/* 456:754 */     this.costoUnitario = costoUnitario;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Integer getIdFacturaCliente()
/* 460:    */   {
/* 461:763 */     return this.idFacturaCliente;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setIdFacturaCliente(Integer idFacturaCliente)
/* 465:    */   {
/* 466:773 */     this.idFacturaCliente = idFacturaCliente;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public int getIdProducto()
/* 470:    */   {
/* 471:782 */     return this.idProducto;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setIdProducto(int idProducto)
/* 475:    */   {
/* 476:792 */     this.idProducto = idProducto;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public String getLote()
/* 480:    */   {
/* 481:801 */     return this.lote;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setLote(String lote)
/* 485:    */   {
/* 486:811 */     this.lote = lote;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public String getDescripcionMovimientoInventario()
/* 490:    */   {
/* 491:820 */     return this.descripcionMovimientoInventario;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setDescripcionMovimientoInventario(String descripcionMovimientoInventario)
/* 495:    */   {
/* 496:830 */     this.descripcionMovimientoInventario = descripcionMovimientoInventario;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public int getId()
/* 500:    */   {
/* 501:840 */     return 0;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public String getNombreProyecto()
/* 505:    */   {
/* 506:847 */     return this.nombreProyecto;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public void setNombreProyecto(String nombreProyecto)
/* 510:    */   {
/* 511:855 */     this.nombreProyecto = nombreProyecto;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public String getNombreComercialProducto()
/* 515:    */   {
/* 516:859 */     return this.nombreComercialProducto;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void setNombreComercialProducto(String nombreComercialProducto)
/* 520:    */   {
/* 521:863 */     this.nombreComercialProducto = nombreComercialProducto;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public String getCodigoAlternoProducto()
/* 525:    */   {
/* 526:867 */     return this.codigoAlternoProducto;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setCodigoAlternoProducto(String codigoAlternoProducto)
/* 530:    */   {
/* 531:871 */     this.codigoAlternoProducto = codigoAlternoProducto;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public String getIdentificacionEmpresa()
/* 535:    */   {
/* 536:875 */     return this.identificacionEmpresa;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void setIdentificacionEmpresa(String identificacionEmpresa)
/* 540:    */   {
/* 541:879 */     this.identificacionEmpresa = identificacionEmpresa;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public String getNombreComercialEmpresa()
/* 545:    */   {
/* 546:883 */     return this.nombreComercialEmpresa;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public void setNombreComercialEmpresa(String nombreComercialEmpresa)
/* 550:    */   {
/* 551:887 */     this.nombreComercialEmpresa = nombreComercialEmpresa;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public String getNombreFiscalEmpresa()
/* 555:    */   {
/* 556:891 */     return this.nombreFiscalEmpresa;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void setNombreFiscalEmpresa(String nombreFiscalEmpresa)
/* 560:    */   {
/* 561:895 */     this.nombreFiscalEmpresa = nombreFiscalEmpresa;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public Integer getNumeroDecimales()
/* 565:    */   {
/* 566:899 */     return Integer.valueOf(this.numeroDecimales == null ? 6 : this.numeroDecimales.intValue());
/* 567:    */   }
/* 568:    */   
/* 569:    */   public void setNumeroDecimales(Integer numeroDecimales)
/* 570:    */   {
/* 571:903 */     this.numeroDecimales = numeroDecimales;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public String getValorAtributo1()
/* 575:    */   {
/* 576:907 */     return this.valorAtributo1;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void setValorAtributo1(String valorAtributo1)
/* 580:    */   {
/* 581:911 */     this.valorAtributo1 = valorAtributo1;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public String getValorAtributo2()
/* 585:    */   {
/* 586:915 */     return this.valorAtributo2;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void setValorAtributo2(String valorAtributo2)
/* 590:    */   {
/* 591:919 */     this.valorAtributo2 = valorAtributo2;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public String getValorAtributo3()
/* 595:    */   {
/* 596:923 */     return this.valorAtributo3;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public void setValorAtributo3(String valorAtributo3)
/* 600:    */   {
/* 601:927 */     this.valorAtributo3 = valorAtributo3;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public String getValorAtributo4()
/* 605:    */   {
/* 606:931 */     return this.valorAtributo4;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public void setValorAtributo4(String valorAtributo4)
/* 610:    */   {
/* 611:935 */     this.valorAtributo4 = valorAtributo4;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public String getValorAtributo5()
/* 615:    */   {
/* 616:939 */     return this.valorAtributo5;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void setValorAtributo5(String valorAtributo5)
/* 620:    */   {
/* 621:943 */     this.valorAtributo5 = valorAtributo5;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public String getValorAtributo6()
/* 625:    */   {
/* 626:947 */     return this.valorAtributo6;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public void setValorAtributo6(String valorAtributo6)
/* 630:    */   {
/* 631:951 */     this.valorAtributo6 = valorAtributo6;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public String getValorAtributo7()
/* 635:    */   {
/* 636:955 */     return this.valorAtributo7;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public void setValorAtributo7(String valorAtributo7)
/* 640:    */   {
/* 641:959 */     this.valorAtributo7 = valorAtributo7;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public String getValorAtributo8()
/* 645:    */   {
/* 646:963 */     return this.valorAtributo8;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void setValorAtributo8(String valorAtributo8)
/* 650:    */   {
/* 651:967 */     this.valorAtributo8 = valorAtributo8;
/* 652:    */   }
/* 653:    */   
/* 654:    */   public String getValorAtributo9()
/* 655:    */   {
/* 656:971 */     return this.valorAtributo9;
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void setValorAtributo9(String valorAtributo9)
/* 660:    */   {
/* 661:975 */     this.valorAtributo9 = valorAtributo9;
/* 662:    */   }
/* 663:    */   
/* 664:    */   public String getValorAtributo10()
/* 665:    */   {
/* 666:979 */     return this.valorAtributo10;
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void setValorAtributo10(String valorAtributo10)
/* 670:    */   {
/* 671:983 */     this.valorAtributo10 = valorAtributo10;
/* 672:    */   }
/* 673:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteInventarioProducto
 * JD-Core Version:    0.7.0.1
 */