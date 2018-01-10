/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.SaldoNegativoProducto;
/*   4:    */ import com.asinfo.as2.dao.CostoEstandarProductoDao;
/*   5:    */ import com.asinfo.as2.dao.CostoProductoDao;
/*   6:    */ import com.asinfo.as2.dao.GenericoDao;
/*   7:    */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.CostoEstandarProducto;
/*  10:    */ import com.asinfo.as2.entities.CostoProducto;
/*  11:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  12:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  13:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  14:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  15:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*  16:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
/*  17:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  20:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  21:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  22:    */ import com.asinfo.as2.entities.Producto;
/*  23:    */ import com.asinfo.as2.entities.RangoCostoEstandarProducto;
/*  24:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  25:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  26:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  27:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  28:    */ import com.asinfo.as2.enumeraciones.TipoCosto;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  30:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  31:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  32:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  33:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.math.RoundingMode;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import java.util.Date;
/*  38:    */ import java.util.HashMap;
/*  39:    */ import java.util.HashSet;
/*  40:    */ import java.util.Iterator;
/*  41:    */ import java.util.List;
/*  42:    */ import java.util.Map;
/*  43:    */ import java.util.Set;
/*  44:    */ import javax.ejb.EJB;
/*  45:    */ import javax.ejb.Stateless;
/*  46:    */ import javax.ejb.TransactionManagement;
/*  47:    */ import javax.ejb.TransactionManagementType;
/*  48:    */ 
/*  49:    */ @Stateless
/*  50:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  51:    */ public class ServicioCosteoImpl
/*  52:    */   implements ServicioCosteo
/*  53:    */ {
/*  54:    */   @EJB
/*  55:    */   private transient ServicioProducto servicioProducto;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioInventarioProducto servicioInventarioProducto;
/*  58:    */   @EJB
/*  59:    */   private CostoProductoDao costoProductoDao;
/*  60:    */   @EJB
/*  61:    */   private CostoEstandarProductoDao costoEstandarProductoDao;
/*  62:    */   @EJB
/*  63:    */   private GenericoDao<DetalleOrdenSalidaMaterialOrdenFabricacion> detalleOrdenSalidaMaterialOrdenFabricacionDao;
/*  64:    */   @EJB
/*  65:    */   private OrdenFabricacionDao ordenFabricacionDao;
/*  66:    */   
/*  67:    */   public void generarCostos(MovimientoInventario movimientoInventario, boolean indicadorCostoPorBodega, Integer versionCosteo)
/*  68:    */   {
/*  69: 83 */     Date fecha = movimientoInventario.getFecha();
/*  70:    */     
/*  71: 85 */     Map<Bodega, Map<Integer, Producto>> hmBodegaProductos = new HashMap();
/*  72:    */     
/*  73: 87 */     Map<Integer, Set<Integer>> mapaInventarioProducto = new HashMap();
/*  74: 88 */     for (DetalleMovimientoInventario d : movimientoInventario.getDetalleMovimientosInventario()) {
/*  75: 89 */       if (!d.isEliminado())
/*  76:    */       {
/*  77: 91 */         bodega = null;
/*  78: 92 */         if (indicadorCostoPorBodega) {
/*  79: 93 */           bodega = d.getBodegaOrigen();
/*  80:    */         }
/*  81: 95 */         Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/*  82: 96 */         if (hmProductos == null)
/*  83:    */         {
/*  84: 97 */           hmProductos = new HashMap();
/*  85: 98 */           hmBodegaProductos.put(bodega, hmProductos);
/*  86:    */         }
/*  87:100 */         int idProducto = d.getProducto().getIdProducto();
/*  88:101 */         hmProductos.put(Integer.valueOf(idProducto), d.getProducto());
/*  89:    */         
/*  90:    */ 
/*  91:104 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(idProducto));
/*  92:105 */         if (listaInventarioProducto == null) {
/*  93:106 */           listaInventarioProducto = new HashSet();
/*  94:    */         }
/*  95:108 */         if (d.getInventarioProducto() != null)
/*  96:    */         {
/*  97:109 */           listaInventarioProducto.add(Integer.valueOf(d.getInventarioProducto().getId()));
/*  98:110 */           mapaInventarioProducto.put(Integer.valueOf(idProducto), listaInventarioProducto);
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:    */     Bodega bodega;
/* 103:114 */     this.servicioInventarioProducto.flush();
/* 104:115 */     for (??? = hmBodegaProductos.keySet().iterator(); ???.hasNext();)
/* 105:    */     {
/* 106:115 */       bodega = (Bodega)???.next();
/* 107:116 */       Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/* 108:117 */       for (Producto producto : hmProductos.values())
/* 109:    */       {
/* 110:118 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(producto.getId()));
/* 111:119 */         generarCostos(movimientoInventario.getIdOrganizacion(), producto, fecha, fecha, bodega, versionCosteo, null, listaInventarioProducto);
/* 112:    */       }
/* 113:    */     }
/* 114:    */     Bodega bodega;
/* 115:122 */     this.servicioInventarioProducto.flush();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void generarCostos(MovimientoInventario movimientoInventario, boolean indicadorCostoPorBodega)
/* 119:    */   {
/* 120:132 */     generarCostos(movimientoInventario, indicadorCostoPorBodega, null);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void generarCostos(DespachoCliente despachoCliente, boolean indicadorCostoPorBodega)
/* 124:    */   {
/* 125:141 */     Date fecha = despachoCliente.getFecha();
/* 126:142 */     Map<Bodega, Map<Integer, Producto>> hmBodegaProductos = new HashMap();
/* 127:    */     
/* 128:144 */     Map<Integer, Set<Integer>> mapaInventarioProducto = new HashMap();
/* 129:145 */     for (DetalleDespachoCliente d : despachoCliente.getListaDetalleDespachoCliente()) {
/* 130:146 */       if (!d.isEliminado())
/* 131:    */       {
/* 132:148 */         bodega = null;
/* 133:149 */         if (indicadorCostoPorBodega) {
/* 134:150 */           bodega = d.getBodega();
/* 135:    */         }
/* 136:152 */         Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/* 137:153 */         if (hmProductos == null)
/* 138:    */         {
/* 139:154 */           hmProductos = new HashMap();
/* 140:155 */           hmBodegaProductos.put(bodega, hmProductos);
/* 141:    */         }
/* 142:157 */         int idProducto = d.getProducto().getIdProducto();
/* 143:158 */         hmProductos.put(Integer.valueOf(idProducto), d.getProducto());
/* 144:    */         
/* 145:    */ 
/* 146:161 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(idProducto));
/* 147:162 */         if (listaInventarioProducto == null) {
/* 148:163 */           listaInventarioProducto = new HashSet();
/* 149:    */         }
/* 150:165 */         if (d.getInventarioProducto() != null)
/* 151:    */         {
/* 152:166 */           listaInventarioProducto.add(Integer.valueOf(d.getInventarioProducto().getId()));
/* 153:167 */           mapaInventarioProducto.put(Integer.valueOf(idProducto), listaInventarioProducto);
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:    */     Bodega bodega;
/* 158:171 */     this.servicioInventarioProducto.flush();
/* 159:172 */     for (??? = hmBodegaProductos.keySet().iterator(); ???.hasNext();)
/* 160:    */     {
/* 161:172 */       bodega = (Bodega)???.next();
/* 162:173 */       Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/* 163:174 */       for (Producto producto : hmProductos.values())
/* 164:    */       {
/* 165:175 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(producto.getId()));
/* 166:176 */         generarCostos(despachoCliente.getIdOrganizacion(), producto, fecha, fecha, bodega, null, null, listaInventarioProducto);
/* 167:    */       }
/* 168:    */     }
/* 169:    */     Bodega bodega;
/* 170:179 */     this.servicioInventarioProducto.flush();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void generarCostos(RecepcionProveedor recepcionProveedor, boolean indicadorCostoPorBodega)
/* 174:    */   {
/* 175:189 */     Date fecha = recepcionProveedor.getFecha();
/* 176:190 */     Map<Bodega, Map<Integer, Producto>> hmBodegaProductos = new HashMap();
/* 177:    */     
/* 178:192 */     Map<Integer, Set<Integer>> mapaInventarioProducto = new HashMap();
/* 179:193 */     for (DetalleRecepcionProveedor d : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 180:194 */       if (!d.isEliminado())
/* 181:    */       {
/* 182:196 */         bodega = null;
/* 183:197 */         if (indicadorCostoPorBodega) {
/* 184:198 */           bodega = d.getBodega();
/* 185:    */         }
/* 186:200 */         Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/* 187:201 */         if (hmProductos == null)
/* 188:    */         {
/* 189:202 */           hmProductos = new HashMap();
/* 190:203 */           hmBodegaProductos.put(bodega, hmProductos);
/* 191:    */         }
/* 192:205 */         int idProducto = d.getProducto().getIdProducto();
/* 193:206 */         hmProductos.put(Integer.valueOf(idProducto), d.getProducto());
/* 194:    */         
/* 195:    */ 
/* 196:209 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(idProducto));
/* 197:210 */         if (listaInventarioProducto == null) {
/* 198:211 */           listaInventarioProducto = new HashSet();
/* 199:    */         }
/* 200:213 */         if (d.getInventarioProducto() != null)
/* 201:    */         {
/* 202:214 */           listaInventarioProducto.add(Integer.valueOf(d.getInventarioProducto().getId()));
/* 203:215 */           mapaInventarioProducto.put(Integer.valueOf(idProducto), listaInventarioProducto);
/* 204:    */         }
/* 205:    */       }
/* 206:    */     }
/* 207:    */     Bodega bodega;
/* 208:219 */     this.servicioInventarioProducto.flush();
/* 209:220 */     for (??? = hmBodegaProductos.keySet().iterator(); ???.hasNext();)
/* 210:    */     {
/* 211:220 */       bodega = (Bodega)???.next();
/* 212:221 */       Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/* 213:222 */       for (Producto producto : hmProductos.values())
/* 214:    */       {
/* 215:223 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(producto.getId()));
/* 216:224 */         generarCostos(recepcionProveedor.getIdOrganizacion(), producto, fecha, fecha, bodega, null, null, listaInventarioProducto);
/* 217:    */       }
/* 218:    */     }
/* 219:    */     Bodega bodega;
/* 220:227 */     this.servicioInventarioProducto.flush();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void generarCostos(FacturaCliente devolucionFacturaCliente, boolean indicadorCostoPorBodega)
/* 224:    */   {
/* 225:237 */     Date fecha = devolucionFacturaCliente.getFecha();
/* 226:238 */     Map<Bodega, Map<Integer, Producto>> hmBodegaProductos = new HashMap();
/* 227:    */     
/* 228:240 */     Map<Integer, Set<Integer>> mapaInventarioProducto = new HashMap();
/* 229:241 */     for (DetalleFacturaCliente d : devolucionFacturaCliente.getListaDetalleFacturaCliente()) {
/* 230:242 */       if (!d.isEliminado())
/* 231:    */       {
/* 232:244 */         bodega = null;
/* 233:245 */         if (indicadorCostoPorBodega) {
/* 234:246 */           bodega = d.getBodega();
/* 235:    */         }
/* 236:248 */         Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/* 237:249 */         if (hmProductos == null)
/* 238:    */         {
/* 239:250 */           hmProductos = new HashMap();
/* 240:251 */           hmBodegaProductos.put(bodega, hmProductos);
/* 241:    */         }
/* 242:253 */         int idProducto = d.getProducto().getIdProducto();
/* 243:254 */         hmProductos.put(Integer.valueOf(idProducto), d.getProducto());
/* 244:    */         
/* 245:    */ 
/* 246:257 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(idProducto));
/* 247:258 */         if (listaInventarioProducto == null) {
/* 248:259 */           listaInventarioProducto = new HashSet();
/* 249:    */         }
/* 250:261 */         if (d.getInventarioProducto() != null)
/* 251:    */         {
/* 252:262 */           listaInventarioProducto.add(Integer.valueOf(d.getInventarioProducto().getId()));
/* 253:263 */           mapaInventarioProducto.put(Integer.valueOf(idProducto), listaInventarioProducto);
/* 254:    */         }
/* 255:    */       }
/* 256:    */     }
/* 257:    */     Bodega bodega;
/* 258:267 */     this.servicioInventarioProducto.flush();
/* 259:268 */     for (??? = hmBodegaProductos.keySet().iterator(); ???.hasNext();)
/* 260:    */     {
/* 261:268 */       bodega = (Bodega)???.next();
/* 262:269 */       Map<Integer, Producto> hmProductos = (Map)hmBodegaProductos.get(bodega);
/* 263:270 */       for (Producto producto : hmProductos.values())
/* 264:    */       {
/* 265:271 */         Set<Integer> listaInventarioProducto = (Set)mapaInventarioProducto.get(Integer.valueOf(producto.getId()));
/* 266:272 */         generarCostos(devolucionFacturaCliente.getIdOrganizacion(), producto, fecha, fecha, bodega, null, null, listaInventarioProducto);
/* 267:    */       }
/* 268:    */     }
/* 269:    */     Bodega bodega;
/* 270:275 */     this.servicioInventarioProducto.flush();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<SaldoNegativoProducto> revisionSaldosNegativos(int idOrganizacion, Producto producto, Date fechaDesde, Date fechaHasta, Bodega bodega)
/* 274:    */   {
/* 275:280 */     List<InventarioProducto> lista = this.servicioInventarioProducto.obtenerMovimientos(idOrganizacion, producto, bodega, fechaDesde, fechaHasta);
/* 276:281 */     List<SaldoNegativoProducto> listaNegativoProducto = new ArrayList();
/* 277:282 */     BigDecimal saldo = this.servicioProducto.getSaldoInicial(producto.getId(), bodega, fechaDesde);
/* 278:283 */     BigDecimal cantidad = this.servicioProducto.getSaldoInicial(producto.getId(), bodega, fechaDesde);
/* 279:285 */     for (InventarioProducto inventarioProducto : lista)
/* 280:    */     {
/* 281:287 */       if (inventarioProducto.getOperacion() == 1) {
/* 282:288 */         cantidad = inventarioProducto.getCantidad();
/* 283:    */       } else {
/* 284:290 */         cantidad = inventarioProducto.getCantidad().negate();
/* 285:    */       }
/* 286:292 */       saldo = saldo.add(cantidad);
/* 287:293 */       if (saldo.compareTo(BigDecimal.ZERO) < 0) {
/* 288:294 */         listaNegativoProducto.add(new SaldoNegativoProducto(inventarioProducto.getProducto().getCodigo(), inventarioProducto.getProducto()
/* 289:295 */           .getCodigoAlterno(), inventarioProducto.getProducto().getNombre(), inventarioProducto.getProducto().getNombreComercial(), bodega != null ? bodega
/* 290:296 */           .getCodigo() : "", bodega != null ? bodega.getNombre() : "", inventarioProducto.getFecha(), saldo));
/* 291:    */       }
/* 292:    */     }
/* 293:299 */     return listaNegativoProducto;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void generarCostos(int idOrganizacion, Producto producto, Date fechaDesde, Date fechaHasta, Bodega bodega, Integer versionCosteo, Set<Integer> setIdProducto)
/* 297:    */   {
/* 298:305 */     generarCostos(idOrganizacion, producto, fechaDesde, fechaHasta, bodega, versionCosteo, setIdProducto, null);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void generarCostos(int idOrganizacion, Producto producto, Date fechaDesde, Date fechaHasta, Bodega bodega, Integer versionCosteo, Set<Integer> setIdProducto, Set<Integer> listaInventarioProducto)
/* 302:    */   {
/* 303:310 */     this.costoProductoDao.flush();
/* 304:311 */     if (setIdProducto == null) {
/* 305:312 */       setIdProducto = new HashSet();
/* 306:    */     }
/* 307:315 */     Producto productoBase = versionCosteo == null ? null : this.servicioProducto.buscarPorId(producto.getId());
/* 308:316 */     if ((!setIdProducto.contains(Integer.valueOf(producto.getId()))) && ((versionCosteo == null) || (productoBase.getVersionCosteo() != versionCosteo.intValue())))
/* 309:    */     {
/* 310:317 */       setIdProducto.add(Integer.valueOf(producto.getId()));
/* 311:318 */       this.costoProductoDao.borrarPorRangoDeFechas(producto, fechaDesde, fechaHasta, bodega);
/* 312:319 */       Map<Date, CostoProducto> mapaCostoProducto = new HashMap();
/* 313:    */       
/* 314:321 */       BigDecimal costoInicial = this.servicioProducto.getCostoInicial(producto.getId(), fechaDesde, bodega);
/* 315:322 */       BigDecimal saldoInicial = this.servicioProducto.getSaldoInicial(producto.getId(), bodega, fechaDesde);
/* 316:    */       
/* 317:324 */       BigDecimal costoUnitario = BigDecimal.ZERO;
/* 318:    */       
/* 319:326 */       BigDecimal costoUnitarioAnterior = BigDecimal.ZERO;
/* 320:327 */       BigDecimal costoLinea = BigDecimal.ZERO;
/* 321:328 */       BigDecimal cantidad = BigDecimal.ZERO;
/* 322:329 */       BigDecimal costoEstandar = BigDecimal.ZERO;
/* 323:    */       
/* 324:331 */       List<InventarioProducto> lista = this.servicioInventarioProducto.obtenerMovimientos(idOrganizacion, producto, bodega, fechaDesde, fechaHasta);
/* 325:338 */       for (InventarioProducto inventarioProducto : lista)
/* 326:    */       {
/* 327:340 */         boolean costearInventario = (listaInventarioProducto == null) || (listaInventarioProducto.contains(Integer.valueOf(inventarioProducto.getId())));
/* 328:341 */         boolean indicadorProduccion = false;
/* 329:343 */         if (inventarioProducto.getOperacion() == 1) {
/* 330:344 */           cantidad = inventarioProducto.getCantidad();
/* 331:    */         } else {
/* 332:346 */           cantidad = inventarioProducto.getCantidad().negate();
/* 333:    */         }
/* 334:348 */         boolean indicadorCostoPorBodega = ParametrosSistema.isCosteoPorBodega(idOrganizacion).booleanValue();
/* 335:349 */         DocumentoBase documentoBase = inventarioProducto.getDocumento().getDocumentoBase();
/* 336:350 */         boolean recepcionTransferencia = (inventarioProducto.getOperacion() == 1) && (documentoBase.equals(DocumentoBase.TRANSFERENCIA_BODEGA)) && (indicadorCostoPorBodega);
/* 337:353 */         if (costearInventario)
/* 338:    */         {
/* 339:355 */           if (inventarioProducto.getProducto().getTipoCosto() == TipoCosto.PROMEDIO_PONDERADO)
/* 340:    */           {
/* 341:356 */             if ((cantidad.compareTo(BigDecimal.ZERO) == 0) && (inventarioProducto.getOperacion() == -1) && 
/* 342:357 */               (inventarioProducto.getDetalleDevolucionProveedor() != null))
/* 343:    */             {
/* 344:358 */               costoLinea = inventarioProducto.getCosto().negate();
/* 345:    */             }
/* 346:359 */             else if ((inventarioProducto.getOperacion() == 1) && ((inventarioProducto.isIndicadorGeneraCosto()) || (recepcionTransferencia)) && 
/* 347:360 */               (inventarioProducto.getInventarioProductoTransformacion() == null))
/* 348:    */             {
/* 349:362 */               if (documentoBase.equals(DocumentoBase.INGRESO_PRODUCCION))
/* 350:    */               {
/* 351:363 */                 indicadorProduccion = true;
/* 352:365 */                 if (inventarioProducto.getDetalleMovimientoInventario().getMovimientoInventario().getOrdenFabricacion().getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_CORTO))
/* 353:    */                 {
/* 354:366 */                   costoUnitario = obtenerCostoOrdenFabricacion(false, inventarioProducto.getDetalleMovimientoInventario()
/* 355:367 */                     .getMovimientoInventario().getOrdenFabricacion(), versionCosteo, fechaDesde, fechaHasta, bodega, setIdProducto);
/* 356:    */                   
/* 357:369 */                   costoLinea = costoUnitario.multiply(cantidad).setScale(4, RoundingMode.HALF_UP);
/* 358:370 */                   inventarioProducto.setCostoMateriales(costoLinea);
/* 359:    */                 }
/* 360:    */                 else
/* 361:    */                 {
/* 362:373 */                   costoLinea = inventarioProducto.getCosto();
/* 363:    */                 }
/* 364:    */               }
/* 365:376 */               else if ((documentoBase.equals(DocumentoBase.TRANSFORMACION_PRODUCTO)) && 
/* 366:377 */                 (inventarioProducto.getInventarioProductoTransformacion() == null))
/* 367:    */               {
/* 368:378 */                 indicadorProduccion = true;
/* 369:379 */                 costoLinea = this.servicioInventarioProducto.obtenerCostoTransformacion(inventarioProducto, fechaDesde, fechaHasta, bodega, versionCosteo, idOrganizacion, setIdProducto);
/* 370:    */                 
/* 371:381 */                 inventarioProducto.setCostoMateriales(costoLinea);
/* 372:    */               }
/* 373:    */               else
/* 374:    */               {
/* 375:384 */                 costoLinea = inventarioProducto.getCosto();
/* 376:    */               }
/* 377:    */             }
/* 378:386 */             else if (inventarioProducto.getDetalleRecepcionProveedorAjuste() != null)
/* 379:    */             {
/* 380:388 */               inventarioProducto.setCosto(inventarioProducto.getDetalleRecepcionProveedorAjuste().getInventarioProducto().getCosto());
/* 381:389 */               costoLinea = inventarioProducto.getCosto().negate();
/* 382:    */             }
/* 383:390 */             else if ((inventarioProducto.getDetalleMovimientoInventarioAjuste() != null) && 
/* 384:391 */               (inventarioProducto.getDetalleMovimientoInventarioAjuste().getInventarioProducto().isIndicadorGeneraCosto()))
/* 385:    */             {
/* 386:393 */               inventarioProducto.setCosto(inventarioProducto.getDetalleMovimientoInventarioAjuste().getInventarioProducto().getCosto());
/* 387:394 */               costoLinea = inventarioProducto.getCosto().negate();
/* 388:    */             }
/* 389:    */             else
/* 390:    */             {
/* 391:396 */               if (saldoInicial.compareTo(BigDecimal.ZERO) == 0)
/* 392:    */               {
/* 393:400 */                 if ((costoUnitarioAnterior.equals(BigDecimal.ZERO)) && (inventarioProducto.getDetalleDevolucionCliente() != null)) {
/* 394:401 */                   costoUnitarioAnterior = this.servicioProducto.getCostoInicialMayorCero(inventarioProducto, bodega);
/* 395:    */                 }
/* 396:403 */                 costoUnitario = costoUnitarioAnterior;
/* 397:    */               }
/* 398:    */               else
/* 399:    */               {
/* 400:405 */                 costoUnitario = costoInicial.divide(saldoInicial, 20, RoundingMode.HALF_UP);
/* 401:406 */                 costoUnitarioAnterior = costoUnitario;
/* 402:    */               }
/* 403:408 */               costoLinea = costoUnitario.multiply(cantidad).setScale(4, RoundingMode.HALF_UP);
/* 404:    */             }
/* 405:    */           }
/* 406:    */           else
/* 407:    */           {
/* 408:412 */             costoEstandar = inventarioProducto.getProducto().getCostoEstandar();
/* 409:413 */             BigDecimal cantidadMultiplicar = cantidad;
/* 410:414 */             if (cantidad.compareTo(BigDecimal.ZERO) == 0) {
/* 411:415 */               cantidadMultiplicar = new BigDecimal(1);
/* 412:    */             }
/* 413:418 */             costoLinea = cantidadMultiplicar.multiply(costoEstandar).multiply(new BigDecimal(inventarioProducto.getOperacion())).setScale(4, RoundingMode.HALF_UP);
/* 414:    */           }
/* 415:    */         }
/* 416:    */         else
/* 417:    */         {
/* 418:422 */           costoLinea = inventarioProducto.getCosto();
/* 419:423 */           cantidad = inventarioProducto.getCantidad();
/* 420:    */         }
/* 421:425 */         costoInicial = costoInicial.add(costoLinea);
/* 422:426 */         saldoInicial = saldoInicial.add(cantidad);
/* 423:428 */         if (costearInventario)
/* 424:    */         {
/* 425:430 */           inventarioProducto.setCosto(costoLinea.abs());
/* 426:431 */           if ((inventarioProducto.isIndicadorTransferencia()) && (inventarioProducto.getInventarioProductoTransferencia() != null))
/* 427:    */           {
/* 428:432 */             inventarioProducto.getInventarioProductoTransferencia().setCosto(costoLinea.abs());
/* 429:433 */             inventarioProducto.getInventarioProductoTransferencia().setIndicadorGeneraCosto(indicadorCostoPorBodega);
/* 430:434 */             this.servicioInventarioProducto.guardar(inventarioProducto.getInventarioProductoTransferencia());
/* 431:    */           }
/* 432:437 */           if (indicadorProduccion)
/* 433:    */           {
/* 434:439 */             BigDecimal costoTotalProduccion = inventarioProducto.getCostoDepreciaciones().add(inventarioProducto.getCostoIndirectos()).add(inventarioProducto.getCostoManoDeObra()).add(inventarioProducto.getCostoMateriales());
/* 435:440 */             inventarioProducto.setCosto(costoTotalProduccion);
/* 436:    */           }
/* 437:443 */           this.servicioInventarioProducto.guardar(inventarioProducto);
/* 438:    */         }
/* 439:447 */         CostoProducto costoProducto = (CostoProducto)mapaCostoProducto.get(inventarioProducto.getFecha());
/* 440:448 */         costoInicial = costoInicial.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : costoInicial;
/* 441:449 */         if (costoProducto == null)
/* 442:    */         {
/* 443:450 */           costoProducto = new CostoProducto(idOrganizacion, producto, inventarioProducto.getFecha(), saldoInicial, costoInicial, bodega);
/* 444:451 */           mapaCostoProducto.put(inventarioProducto.getFecha(), costoProducto);
/* 445:    */         }
/* 446:    */         else
/* 447:    */         {
/* 448:453 */           saldoInicial = saldoInicial.setScale(4, RoundingMode.HALF_UP);
/* 449:454 */           costoProducto.setSaldo(saldoInicial);
/* 450:455 */           costoProducto.setCosto(costoInicial);
/* 451:    */         }
/* 452:    */       }
/* 453:459 */       for (CostoProducto costoProducto : mapaCostoProducto.values()) {
/* 454:460 */         this.costoProductoDao.guardar(this.costoProductoDao.buscarYActualizar(costoProducto));
/* 455:    */       }
/* 456:462 */       if ((versionCosteo != null) && (versionCosteo.compareTo(Integer.valueOf(0)) < 0)) {
/* 457:465 */         versionCosteo = Integer.valueOf(0);
/* 458:    */       }
/* 459:467 */       if (versionCosteo != null) {
/* 460:468 */         this.costoProductoDao.actualizarVersionCosteoProducto(producto, versionCosteo);
/* 461:    */       }
/* 462:    */     }
/* 463:    */   }
/* 464:    */   
/* 465:    */   public BigDecimal obtenerCostoOrdenFabricacion(boolean indicadorCicloLargo, OrdenFabricacion ordenFabricacion, Integer versionCosteo, Date fechaDesde, Date fechaHasta, Bodega bodega, Set<Integer> setIdProducto)
/* 466:    */   {
/* 467:476 */     BigDecimal costoUnitario = BigDecimal.ZERO;
/* 468:477 */     BigDecimal costoTotal = BigDecimal.ZERO;
/* 469:478 */     BigDecimal cantidadProducida = ordenFabricacion.getCantidadFabricada();
/* 470:479 */     Map<String, String> filtros = new HashMap();
/* 471:480 */     filtros.put("ordenFabricacion.idOrdenFabricacion", "=" + ordenFabricacion.getId());
/* 472:481 */     List<String> listaCampos = new ArrayList();
/* 473:482 */     listaCampos.add("detalleOrdenSalidaMaterial.listaDetalleConsumoBodega.inventarioProducto.producto");
/* 474:483 */     List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDetalle = this.detalleOrdenSalidaMaterialOrdenFabricacionDao.obtenerListaPorPagina(DetalleOrdenSalidaMaterialOrdenFabricacion.class, 0, 1000, null, true, filtros, listaCampos);
/* 475:486 */     for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion : listaDetalle) {
/* 476:487 */       if (detalleOrdenSalidaMaterialOrdenFabricacion.getDetalleOrdenSalidaMaterial().getListaDetalleConsumoBodega().size() > 0)
/* 477:    */       {
/* 478:489 */         DetalleMovimientoInventario detalleConsumoBodega = (DetalleMovimientoInventario)detalleOrdenSalidaMaterialOrdenFabricacion.getDetalleOrdenSalidaMaterial().getListaDetalleConsumoBodega().get(0);
/* 479:    */         
/* 480:    */ 
/* 481:492 */         InventarioProducto inventarioProductoConsumo = detalleConsumoBodega.getInventarioProducto();
/* 482:495 */         if ((!indicadorCicloLargo) || (
/* 483:496 */           (inventarioProductoConsumo.getFecha().compareTo(fechaDesde) >= 0) && (inventarioProductoConsumo.getFecha().compareTo(fechaHasta) <= 0)))
/* 484:    */         {
/* 485:499 */           if ((!indicadorCicloLargo) && (
/* 486:500 */             (versionCosteo == null) || (inventarioProductoConsumo.getProducto().getVersionCosteo() != versionCosteo.intValue())))
/* 487:    */           {
/* 488:501 */             generarCostos(ordenFabricacion.getIdOrganizacion(), inventarioProductoConsumo.getProducto(), fechaDesde, fechaHasta, bodega, versionCosteo, setIdProducto);
/* 489:    */             
/* 490:503 */             inventarioProductoConsumo = this.servicioInventarioProducto.buscarPorId(Integer.valueOf(inventarioProductoConsumo.getId()));
/* 491:    */           }
/* 492:510 */           BigDecimal cantidadUtilizadaMaterial = detalleOrdenSalidaMaterialOrdenFabricacion.getCantidadUtilizada();
/* 493:511 */           BigDecimal cantidadInventarioConsumoMaterial = inventarioProductoConsumo.getCantidad();
/* 494:512 */           BigDecimal costoInventarioConsumoMaterial = inventarioProductoConsumo.getCosto();
/* 495:    */           
/* 496:514 */           BigDecimal costoUnitarioMaterial = BigDecimal.ZERO;
/* 497:515 */           if (cantidadInventarioConsumoMaterial.compareTo(BigDecimal.ZERO) > 0) {
/* 498:516 */             costoUnitarioMaterial = costoInventarioConsumoMaterial.divide(cantidadInventarioConsumoMaterial, 6, RoundingMode.HALF_UP);
/* 499:    */           }
/* 500:519 */           BigDecimal costoDetalle = costoUnitarioMaterial.multiply(cantidadUtilizadaMaterial).setScale(6, RoundingMode.HALF_UP);
/* 501:520 */           costoTotal = costoTotal.add(costoDetalle);
/* 502:    */         }
/* 503:    */       }
/* 504:    */     }
/* 505:526 */     if (indicadorCicloLargo) {
/* 506:527 */       return costoTotal;
/* 507:    */     }
/* 508:531 */     if (cantidadProducida.compareTo(BigDecimal.ZERO) > 0) {
/* 509:532 */       costoUnitario = costoTotal.divide(cantidadProducida, 6, RoundingMode.HALF_UP);
/* 510:    */     }
/* 511:534 */     return costoUnitario;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void generarCostosEstandar(int idOrganizacion, Producto producto, Date fechaDesde, Date fechaHasta)
/* 515:    */   {
/* 516:541 */     Map<String, BigDecimal> mapaCostoEstandarproducto = new HashMap();
/* 517:542 */     for (CostoEstandarProducto costoEstandarProducto : this.costoEstandarProductoDao.obtenerCostoEstandar(producto, fechaDesde, fechaHasta))
/* 518:    */     {
/* 519:543 */       int anio = costoEstandarProducto.getRangoCostoEstandarProducto().getAnio();
/* 520:544 */       int mes = costoEstandarProducto.getRangoCostoEstandarProducto().getMes();
/* 521:545 */       mapaCostoEstandarproducto.put(producto.getId() + "~" + anio + "~" + mes, costoEstandarProducto.getCosto());
/* 522:    */     }
/* 523:550 */     Object lista = this.servicioInventarioProducto.obtenerMovimientos(idOrganizacion, producto, null, fechaDesde, fechaHasta, Boolean.valueOf(true));
/* 524:    */     
/* 525:    */ 
/* 526:553 */     BigDecimal costoEstandar = BigDecimal.ZERO;
/* 527:554 */     BigDecimal costoLinea = BigDecimal.ZERO;
/* 528:555 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 529:556 */     int DECIMALES_CALCULO_COSTO_TOTAL = 4;
/* 530:557 */     int idProducto = 0;
/* 531:558 */     int anio = 0;
/* 532:559 */     int mes = 0;
/* 533:562 */     for (InventarioProducto inventarioProducto : (List)lista) {
/* 534:564 */       if ((inventarioProducto.getOperacion() == 1) && (inventarioProducto.isIndicadorGeneraCosto()))
/* 535:    */       {
/* 536:567 */         cantidad = inventarioProducto.getCantidad();
/* 537:    */         
/* 538:569 */         idProducto = inventarioProducto.getProducto().getId();
/* 539:570 */         anio = FuncionesUtiles.getAnio(inventarioProducto.getFecha());
/* 540:571 */         mes = FuncionesUtiles.getMes(inventarioProducto.getFecha());
/* 541:572 */         costoEstandar = (BigDecimal)mapaCostoEstandarproducto.get(idProducto + "~" + anio + "~" + mes);
/* 542:573 */         costoLinea = cantidad.multiply(costoEstandar == null ? BigDecimal.ZERO : costoEstandar).setScale(DECIMALES_CALCULO_COSTO_TOTAL, RoundingMode.HALF_UP);
/* 543:    */         
/* 544:    */ 
/* 545:576 */         inventarioProducto.setCosto(costoLinea.abs());
/* 546:577 */         if ((inventarioProducto.isIndicadorTransferencia()) && (inventarioProducto.getInventarioProductoTransferencia() != null))
/* 547:    */         {
/* 548:578 */           inventarioProducto.getInventarioProductoTransferencia().setCosto(costoLinea.abs());
/* 549:579 */           this.servicioInventarioProducto.guardar(inventarioProducto.getInventarioProductoTransferencia());
/* 550:    */         }
/* 551:581 */         this.servicioInventarioProducto.guardar(inventarioProducto);
/* 552:    */       }
/* 553:    */     }
/* 554:    */   }
/* 555:    */   
/* 556:    */   public int actualizaIndicadorGeneraCostoTransferenciaIngreso(int idOrganizacion, Date fechaDesde, Date fechaHasta, Producto producto)
/* 557:    */   {
/* 558:590 */     return this.costoEstandarProductoDao.actualizaIndicadorGeneraCostoTransferenciaIngreso(idOrganizacion, fechaDesde, fechaHasta, producto);
/* 559:    */   }
/* 560:    */   
/* 561:    */   public int eliminarCostoProductoNoInventarioProducto(int idOrganizacion, Date fechaDesde, Date fechaHasta, boolean costeoPorBodega)
/* 562:    */   {
/* 563:595 */     return this.costoEstandarProductoDao.eliminarCostoProductoNoInventarioProducto(idOrganizacion, fechaDesde, fechaHasta, costeoPorBodega);
/* 564:    */   }
/* 565:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioCosteoImpl
 * JD-Core Version:    0.7.0.1
 */