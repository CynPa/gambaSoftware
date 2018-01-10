/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   4:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   5:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*   6:    */ import com.asinfo.as2.dao.GenericoDao;
/*   7:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*   8:    */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*   9:    */ import com.asinfo.as2.dao.PedidoClienteDao;
/*  10:    */ import com.asinfo.as2.dao.ProductoDao;
/*  11:    */ import com.asinfo.as2.dao.RecepcionProveedorDao;
/*  12:    */ import com.asinfo.as2.dao.SerieProductoDao;
/*  13:    */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*  14:    */ import com.asinfo.as2.dao.produccion.OrdenSalidaMaterialDao;
/*  15:    */ import com.asinfo.as2.entities.Bodega;
/*  16:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  17:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  18:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  19:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  20:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  21:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*  22:    */ import com.asinfo.as2.entities.DetallePago;
/*  23:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*  24:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  25:    */ import com.asinfo.as2.entities.DetalleRegistroPesoLote;
/*  26:    */ import com.asinfo.as2.entities.Documento;
/*  27:    */ import com.asinfo.as2.entities.EntidadBase;
/*  28:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  29:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  30:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  31:    */ import com.asinfo.as2.entities.Lote;
/*  32:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  33:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  34:    */ import com.asinfo.as2.entities.PedidoCliente;
/*  35:    */ import com.asinfo.as2.entities.Producto;
/*  36:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  37:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  38:    */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*  39:    */ import com.asinfo.as2.entities.SerieProducto;
/*  40:    */ import com.asinfo.as2.entities.Sucursal;
/*  41:    */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*  42:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  43:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  44:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  45:    */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*  46:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  47:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  48:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  49:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  50:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  51:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  52:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*  53:    */ import java.math.BigDecimal;
/*  54:    */ import java.util.ArrayList;
/*  55:    */ import java.util.Date;
/*  56:    */ import java.util.HashMap;
/*  57:    */ import java.util.Iterator;
/*  58:    */ import java.util.List;
/*  59:    */ import java.util.Map;
/*  60:    */ import javax.annotation.Resource;
/*  61:    */ import javax.ejb.EJB;
/*  62:    */ import javax.ejb.Lock;
/*  63:    */ import javax.ejb.LockType;
/*  64:    */ import javax.ejb.SessionContext;
/*  65:    */ import javax.ejb.Singleton;
/*  66:    */ 
/*  67:    */ @Singleton
/*  68:    */ public class ServicioVerificadorInventarioImpl
/*  69:    */   implements ServicioVerificadorInventario
/*  70:    */ {
/*  71:    */   @EJB
/*  72:    */   private transient ProductoDao productoDao;
/*  73:    */   @EJB
/*  74:    */   private transient MovimientoInventarioDao movimientoInventarioDao;
/*  75:    */   @EJB
/*  76:    */   private transient DespachoClienteDao despachoClienteDao;
/*  77:    */   @EJB
/*  78:    */   private transient RecepcionProveedorDao recepcionProveedorDao;
/*  79:    */   @EJB
/*  80:    */   private transient FacturaClienteDao facturaClienteDao;
/*  81:    */   @EJB
/*  82:    */   private transient FacturaProveedorDao facturaProveedorDao;
/*  83:    */   @EJB
/*  84:    */   private transient PedidoClienteDao pedidoClienteDao;
/*  85:    */   @EJB
/*  86:    */   private transient SerieProductoDao serieProductoDao;
/*  87:    */   @EJB
/*  88:    */   private transient ServicioLote servicioLote;
/*  89:    */   @EJB
/*  90:    */   private transient OrdenSalidaMaterialDao ordenSalidaMaterialDao;
/*  91:    */   @EJB
/*  92:    */   private transient OrdenFabricacionDao ordenFabricacionDao;
/*  93:    */   @EJB
/*  94:    */   private transient ServicioProducto servicioProducto;
/*  95:    */   @EJB
/*  96:    */   private transient GenericoDao<DetalleRegistroPesoLote> detalleRegistroPesoDao;
/*  97:    */   @EJB
/*  98:    */   private transient GenericoDao<DetalleOrdenFabricacionMaterial> detalleOrdenFabricacionMaterialDao;
/*  99:    */   @EJB
/* 100:    */   private transient InventarioProductoDao inventarioProductoDao;
/* 101:    */   @Resource
/* 102:    */   protected SessionContext context;
/* 103:    */   
/* 104:    */   public void actualizarSaldoProducto(MovimientoInventario mi, boolean reverso)
/* 105:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 106:    */   {
/* 107:131 */     actualizarSaldoProducto(mi, reverso, null);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void actualizarSaldoProducto(MovimientoInventario mi, boolean reverso, Producto producto)
/* 111:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 112:    */   {
/* 113:142 */     if ((mi.getId() != 0) || (!reverso))
/* 114:    */     {
/* 115:143 */       List<DetalleMovimientoInventario> listataDetalle = new ArrayList();
/* 116:144 */       MovimientoInventario miBase = (MovimientoInventario)this.movimientoInventarioDao.buscarPorId(Integer.valueOf(mi.getId()));
/* 117:145 */       if ((reverso) && (miBase != null) && (!miBase.getEstado().equals(Estado.REGISTRADO_DATOS)))
/* 118:    */       {
/* 119:146 */         mi = this.movimientoInventarioDao.cargarDetalle(mi.getId(), producto);
/* 120:148 */         if (mi.getDocumento().getDocumentoBase() == DocumentoBase.TRANSFERENCIA_BODEGA) {
/* 121:149 */           for (DetalleMovimientoInventario dmi : mi.getDetalleMovimientosInventario())
/* 122:    */           {
/* 123:150 */             if ((dmi.getInventarioProducto() == null) && (mi.getEstado().equals(Estado.ELABORADO))) {
/* 124:151 */               return;
/* 125:    */             }
/* 126:153 */             dmi.getInventarioProducto().setIndicadorTransferencia(true);
/* 127:    */           }
/* 128:    */         }
/* 129:    */       }
/* 130:156 */       else if ((reverso) && (miBase != null) && (miBase.getEstado().equals(Estado.REGISTRADO_DATOS)) && 
/* 131:157 */         (mi.getDocumento().getDocumentoBase() == DocumentoBase.TRANSFERENCIA_BODEGA))
/* 132:    */       {
/* 133:159 */         return;
/* 134:    */       }
/* 135:162 */       listataDetalle = mi.getDetalleMovimientosInventario();
/* 136:164 */       for (DetalleMovimientoInventario dmi : listataDetalle) {
/* 137:165 */         if (!dmi.isEliminado()) {
/* 138:166 */           actualizarSaldoDetalle(dmi, reverso, mi);
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void actualizarSaldoDetalle(DetalleMovimientoInventario dmi, boolean reverso, MovimientoInventario mi)
/* 145:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 146:    */   {
/* 147:174 */     Documento documento = mi.getDocumento();
/* 148:175 */     if (!dmi.getInventarioProducto().isIndicadorTransferencia()) {
/* 149:178 */       actualizarSaldoProducto(dmi.getInventarioProducto(), mi.getFecha(), documento.getOperacion(), reverso);
/* 150:179 */     } else if (dmi.getInventarioProducto().getInventarioProductoTransferencia() != null) {
/* 151:181 */       actualizarSaldoProducto(dmi.getInventarioProducto().getInventarioProductoTransferencia(), mi.getFecha(), documento.getOperacion() * -1, reverso);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void actualizarSaldoProducto(DespachoCliente dc, boolean reverso, Date fecha)
/* 156:    */     throws ExcepcionAS2Inventario, AS2Exception
/* 157:    */   {
/* 158:    */     BigDecimal operacion;
/* 159:    */     BigDecimal cantidad;
/* 160:195 */     if ((dc.getId() > 0) || (!reverso))
/* 161:    */     {
/* 162:196 */       operacion = BigDecimal.ONE;
/* 163:197 */       if (!reverso) {
/* 164:198 */         operacion = operacion.negate();
/* 165:    */       }
/* 166:200 */       List<DetalleDespachoCliente> listataDetalle = new ArrayList();
/* 167:202 */       if ((reverso) && (this.despachoClienteDao.buscarPorId(Integer.valueOf(dc.getId())) != null)) {
/* 168:203 */         dc = this.despachoClienteDao.cargarDetalle(dc.getId());
/* 169:    */       }
/* 170:205 */       listataDetalle = dc.getListaDetalleDespachoCliente();
/* 171:206 */       cantidad = BigDecimal.ZERO;
/* 172:207 */       for (DetalleDespachoCliente ddc : listataDetalle) {
/* 173:208 */         if ((!ddc.isEliminado()) && (!ddc.getProducto().isTraIndicadorServicio()))
/* 174:    */         {
/* 175:209 */           actualizarSaldoProducto(ddc.getInventarioProducto(), fecha, dc.getDocumento().getOperacion(), reverso);
/* 176:210 */           if (ddc.getDetallePedidoCliente() != null)
/* 177:    */           {
/* 178:211 */             cantidad = ddc.getCantidad().multiply(operacion);
/* 179:212 */             if (ddc.getUnidadVenta() != ddc.getDetallePedidoCliente().getUnidadVenta()) {
/* 180:213 */               cantidad = this.servicioProducto.convierteUnidad(ddc.getUnidadVenta(), ddc.getDetallePedidoCliente().getUnidadVenta(), ddc
/* 181:214 */                 .getProducto().getIdProducto(), ddc.getCantidad());
/* 182:    */             }
/* 183:    */           }
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void actualizarSaldoProducto(RecepcionProveedor rp, boolean reverso, Date fecha)
/* 190:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 191:    */   {
/* 192:232 */     if ((rp.getId() > 0) || (!reverso))
/* 193:    */     {
/* 194:234 */       List<DetalleRecepcionProveedor> listataDetalle = new ArrayList();
/* 195:236 */       if ((reverso) && (this.recepcionProveedorDao.buscarPorId(Integer.valueOf(rp.getId())) != null)) {
/* 196:237 */         rp = (RecepcionProveedor)this.recepcionProveedorDao.cargarDetalle(rp.getId());
/* 197:    */       }
/* 198:239 */       listataDetalle = rp.getListaDetalleRecepcionProveedor();
/* 199:241 */       for (DetalleRecepcionProveedor drp : listataDetalle) {
/* 200:242 */         if ((!drp.isEliminado()) && (TipoProducto.ARTICULO.equals(drp.getProducto().getTipoProducto()))) {
/* 201:243 */           actualizarSaldoProducto(drp.getInventarioProducto(), fecha, rp.getDocumento().getOperacion(), reverso);
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void actualizarSaldoProducto(RecepcionProveedor rp, boolean reverso)
/* 208:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 209:    */   {
/* 210:257 */     if ((rp.getId() > 0) || (!reverso))
/* 211:    */     {
/* 212:259 */       List<DetalleRecepcionProveedor> listataDetalle = new ArrayList();
/* 213:261 */       if ((reverso) && (this.recepcionProveedorDao.buscarPorId(Integer.valueOf(rp.getId())) != null))
/* 214:    */       {
/* 215:262 */         rp = (RecepcionProveedor)this.recepcionProveedorDao.cargarDetalle(rp.getId());
/* 216:264 */         if (rp.getEstado().equals(Estado.ELABORADO)) {
/* 217:265 */           return;
/* 218:    */         }
/* 219:    */       }
/* 220:268 */       listataDetalle = rp.getListaDetalleRecepcionProveedor();
/* 221:269 */       Map<String, InventarioProducto> mapaInventarioProducto = new HashMap();
/* 222:    */       
/* 223:271 */       String clave = "";
/* 224:272 */       InventarioProducto i = null;
/* 225:273 */       for (DetalleRecepcionProveedor drp : listataDetalle) {
/* 226:274 */         if ((!drp.isEliminado()) && (drp.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO)))
/* 227:    */         {
/* 228:275 */           clave = drp.getProducto().getId() + "¬" + drp.getInventarioProducto().getBodega().getId();
/* 229:276 */           if (!mapaInventarioProducto.containsKey(clave))
/* 230:    */           {
/* 231:277 */             mapaInventarioProducto.put(clave, drp.getInventarioProducto());
/* 232:    */           }
/* 233:    */           else
/* 234:    */           {
/* 235:279 */             i = (InventarioProducto)mapaInventarioProducto.get(clave);
/* 236:280 */             i.getListaInventarioProducto().add(drp.getInventarioProducto());
/* 237:    */           }
/* 238:    */         }
/* 239:    */       }
/* 240:284 */       for (InventarioProducto inventarioProducto : mapaInventarioProducto.values()) {
/* 241:285 */         actualizarSaldoProducto(inventarioProducto, rp.getFecha(), rp.getDocumento().getOperacion(), reverso);
/* 242:    */       }
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void actualizarSaldoProducto(FacturaCliente devolucionCliente, boolean reverso)
/* 247:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 248:    */   {
/* 249:300 */     if ((devolucionCliente.getId() > 0) || (!reverso)) {
/* 250:302 */       if (devolucionCliente.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE)
/* 251:    */       {
/* 252:304 */         List<DetalleFacturaCliente> listaDetalle = new ArrayList();
/* 253:306 */         if ((reverso) && (this.facturaClienteDao.buscarPorId(Integer.valueOf(devolucionCliente.getId())) != null)) {
/* 254:307 */           devolucionCliente = this.facturaClienteDao.cargarDetalle(devolucionCliente.getId());
/* 255:    */         }
/* 256:310 */         listaDetalle = devolucionCliente.getListaDetalleFacturaCliente();
/* 257:312 */         for (DetalleFacturaCliente dfc : listaDetalle) {
/* 258:313 */           if (!dfc.isEliminado()) {
/* 259:314 */             actualizarSaldoProducto(dfc.getInventarioProducto(), devolucionCliente.getFecha(), 1, reverso);
/* 260:    */           }
/* 261:    */         }
/* 262:    */       }
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void actualizarSaldoProducto(FacturaProveedor devolucionProveedor, boolean reverso)
/* 267:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 268:    */   {
/* 269:    */     DetalleFacturaProveedor dfp;
/* 270:331 */     if ((devolucionProveedor.getId() > 0) || (!reverso)) {
/* 271:333 */       if (devolucionProveedor.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR)
/* 272:    */       {
/* 273:335 */         List<DetalleFacturaProveedor> listaDetalle = new ArrayList();
/* 274:337 */         if ((reverso) && (this.facturaProveedorDao.buscarPorId(Integer.valueOf(devolucionProveedor.getId())) != null)) {
/* 275:338 */           devolucionProveedor = this.facturaProveedorDao.cargarDetalle(devolucionProveedor.getId());
/* 276:    */         }
/* 277:341 */         listaDetalle = devolucionProveedor.getListaDetalleFacturaProveedor();
/* 278:343 */         for (Iterator localIterator = listaDetalle.iterator(); localIterator.hasNext();)
/* 279:    */         {
/* 280:343 */           dfp = (DetalleFacturaProveedor)localIterator.next();
/* 281:344 */           if ((!dfp.isEliminado()) && (dfp.getInventarioProducto() != null)) {
/* 282:345 */             actualizarSaldoProducto(dfp.getInventarioProducto(), devolucionProveedor.getFecha(), devolucionProveedor.getDocumento()
/* 283:346 */               .getOperacion(), reverso);
/* 284:    */           }
/* 285:    */         }
/* 286:352 */         if (reverso)
/* 287:    */         {
/* 288:353 */           Object lista = this.inventarioProductoDao.getInventarioProductoTransformacion(devolucionProveedor.getIdFacturaProveedor());
/* 289:354 */           for (InventarioProducto inventarioProducto : (List)lista) {
/* 290:355 */             actualizarSaldoProducto(inventarioProducto, devolucionProveedor.getFecha(), inventarioProducto.getOperacion(), reverso);
/* 291:    */           }
/* 292:    */         }
/* 293:    */       }
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   @Lock(LockType.WRITE)
/* 298:    */   public void actualizarSaldoProducto(InventarioProducto ip, Date fecha, int operacion, boolean reverso)
/* 299:    */     throws ExcepcionAS2Inventario, AS2Exception
/* 300:    */   {
/* 301:372 */     operacion = reverso ? operacion * -1 : operacion;
/* 302:    */     
/* 303:374 */     Producto producto = ip.getProducto();
/* 304:375 */     Bodega bodega = ip.getBodega();
/* 305:    */     
/* 306:377 */     BigDecimal cantidad = ip.getCantidad().multiply(new BigDecimal(operacion));
/* 307:    */     Iterator localIterator;
/* 308:379 */     if (ip.getListaInventarioProducto().size() > 0) {
/* 309:380 */       for (localIterator = ip.getListaInventarioProducto().iterator(); localIterator.hasNext();)
/* 310:    */       {
/* 311:380 */         inventarioProducto = (InventarioProducto)localIterator.next();
/* 312:381 */         cantidad = cantidad.add(inventarioProducto.getCantidad().multiply(new BigDecimal(operacion)));
/* 313:    */       }
/* 314:    */     }
/* 315:385 */     BigDecimal saldo = validarSaldoProducto(fecha, cantidad, producto, bodega, operacion);
/* 316:    */     
/* 317:387 */     actualizaSaldoLote(ip, fecha, ip.getCantidad().multiply(new BigDecimal(operacion)), producto, bodega);
/* 318:388 */     for (InventarioProducto inventarioProducto = ip.getListaInventarioProducto().iterator(); inventarioProducto.hasNext();)
/* 319:    */     {
/* 320:388 */       inventarioProducto = (InventarioProducto)inventarioProducto.next();
/* 321:389 */       BigDecimal cantidadLote = inventarioProducto.getCantidad().multiply(new BigDecimal(operacion));
/* 322:390 */       actualizaSaldoLote(inventarioProducto, fecha, cantidadLote, producto, bodega);
/* 323:    */     }
/* 324:    */     InventarioProducto inventarioProducto;
/* 325:393 */     this.productoDao.actualizarSaldo(producto, bodega, fecha, cantidad, saldo, ip.getDetalleDespachoCliente() != null);
/* 326:    */     
/* 327:    */ 
/* 328:396 */     this.productoDao.actualizarIndicadorTieneMovimiento(producto.getIdProducto(), true);
/* 329:399 */     if ((ip.getDetalleMovimientoInventario() != null) && (ip.getDetalleMovimientoInventario().getDetalleOrdenSalidaMaterial() != null) && 
/* 330:400 */       (!ip.getDocumento().getDocumentoBase().equals(DocumentoBase.SALIDA_MATERIAL))) {
/* 331:401 */       this.ordenSalidaMaterialDao.actualizarCantidadConsumida(ip.getDetalleMovimientoInventario().getDetalleOrdenSalidaMaterial(), cantidad);
/* 332:    */     }
/* 333:404 */     if (producto.getIndicadorSerie().booleanValue())
/* 334:    */     {
/* 335:408 */       List<SerieInventarioProducto> lista = ip.getInventarioProductoTransferencia() != null ? ip.getInventarioProductoTransferencia().getListaSerieProducto() : ip.getListaSerieProducto();
/* 336:410 */       for (SerieInventarioProducto serieInventario : lista) {
/* 337:412 */         if ((!serieInventario.isEliminado()) && (operacion == -1))
/* 338:    */         {
/* 339:413 */           SerieProducto serieProducto = serieInventario.getSerieProducto();
/* 340:    */           
/* 341:415 */           SerieProducto serieBDD = this.serieProductoDao.buscarPorId(Integer.valueOf(serieProducto.getId()));
/* 342:417 */           if ((!serieBDD.getBodega().equals(ip.getBodega())) || (!serieBDD.getProducto().equals(ip.getProducto())))
/* 343:    */           {
/* 344:419 */             String mensaje = " " + serieInventario.getSerieProducto().getCodigo() + " Bodega: " + ip.getBodega() + " Producto: " + producto.getCodigo() + ":" + producto.getNombre();
/* 345:420 */             throw new ExcepcionAS2Inventario("msg_error_serie_no_encontrada", mensaje);
/* 346:    */           }
/* 347:424 */           if (!serieProducto.isActivo())
/* 348:    */           {
/* 349:428 */             String mensaje = " " + serieInventario.getSerieProducto().getCodigo() + " Bodega: " + ip.getBodega() + " Producto: " + producto.getCodigo() + ":" + producto.getNombre();
/* 350:    */             
/* 351:430 */             throw new ExcepcionAS2Inventario("msg_error_reversar_serie", mensaje);
/* 352:    */           }
/* 353:432 */           serieProducto.setActivo(false);
/* 354:433 */           this.serieProductoDao.guardar(serieProducto);
/* 355:    */         }
/* 356:436 */         else if ((operacion == 1) && (serieInventario.getSerieProducto().getId() != 0))
/* 357:    */         {
/* 358:437 */           serieInventario.getSerieProducto().setActivo(true);
/* 359:438 */           this.serieProductoDao.guardar(serieInventario.getSerieProducto());
/* 360:    */         }
/* 361:    */       }
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   private void actualizaSaldoLote(InventarioProducto ip, Date fecha, BigDecimal cantidad, Producto producto, Bodega bodega)
/* 366:    */     throws ExcepcionAS2Inventario, AS2Exception
/* 367:    */   {
/* 368:447 */     if (producto.isIndicadorLote())
/* 369:    */     {
/* 370:448 */       if (ip.getLote() == null)
/* 371:    */       {
/* 372:449 */         String mensaje = " Producto: " + producto.getNombre() + " - Código: " + producto.getCodigo();
/* 373:450 */         throw new ExcepcionAS2Inventario("msg_producto_indicador_lote_sin_lote", mensaje);
/* 374:    */       }
/* 375:453 */       if ((ip.getDetalleRecepcionProveedor() != null) && (ip.getLote().getIdLote() == 0))
/* 376:    */       {
/* 377:    */         Lote l;
/* 378:    */         try
/* 379:    */         {
/* 380:456 */           l = this.servicioLote.buscarPorCodigoProductoProveedor(ip.getLote().getCodigo(), producto, ip.getDetalleRecepcionProveedor()
/* 381:457 */             .getRecepcionProveedor().getEmpresa());
/* 382:    */         }
/* 383:    */         catch (ExcepcionAS2 e)
/* 384:    */         {
/* 385:    */           Lote l;
/* 386:459 */           l = null;
/* 387:    */         }
/* 388:461 */         if (l == null) {
/* 389:462 */           this.servicioLote.guardar(ip.getLote());
/* 390:    */         } else {
/* 391:464 */           ip.setLote(l);
/* 392:    */         }
/* 393:    */       }
/* 394:469 */       BigDecimal saldoLote = this.productoDao.getSaldoLote(producto.getId(), bodega.getId(), ip.getLote().getId(), fecha);
/* 395:471 */       if (saldoLote.add(cantidad).compareTo(BigDecimal.ZERO) < 0)
/* 396:    */       {
/* 397:473 */         String mensaje = " :" + bodega.getNombre() + " Producto: " + producto.getNombre() + " Lote:" + ip.getLote().getCodigo() + " (" + cantidad.abs().toString() + ">" + saldoLote.toString() + ")";
/* 398:474 */         throw new ExcepcionAS2Inventario("msg_info_inventario_0001", mensaje);
/* 399:    */       }
/* 400:478 */       this.productoDao.actualizarSaldoLote(producto.getId(), bodega.getId(), ip.getLote().getId(), fecha, cantidad, saldoLote, 
/* 401:479 */         (!ip.getLote().isActivo()) && (saldoLote.compareTo(BigDecimal.ZERO) > 0));
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   public BigDecimal validarSaldoProducto(Date fecha, BigDecimal cantidad, Producto producto, Bodega bodega, int operacion)
/* 406:    */     throws ExcepcionAS2Inventario
/* 407:    */   {
/* 408:499 */     BigDecimal saldo = this.productoDao.getSaldo(producto.getId(), bodega.getId(), fecha);
/* 409:501 */     if ((operacion == -1) && 
/* 410:502 */       (saldo.add(cantidad).compareTo(BigDecimal.ZERO) < 0))
/* 411:    */     {
/* 412:504 */       String mensaje = " :" + bodega.getNombre() + "  Producto: " + producto.getNombre() + " (" + cantidad.abs().toString() + ">" + saldo.toString() + ")";
/* 413:    */       
/* 414:    */ 
/* 415:    */ 
/* 416:508 */       throw new ExcepcionAS2Inventario("msg_info_inventario_0001", mensaje);
/* 417:    */     }
/* 418:512 */     return saldo;
/* 419:    */   }
/* 420:    */   
/* 421:    */   @Lock(LockType.WRITE)
/* 422:    */   public void actualizarInventarioComprometido(DetallePedidoCliente dpc, Date fecha, BigDecimal cantidad)
/* 423:    */   {
/* 424:527 */     Producto producto = dpc.getProducto();
/* 425:528 */     Bodega bodega = dpc.getPedidoCliente().getBodega();
/* 426:    */     
/* 427:    */ 
/* 428:531 */     BigDecimal inventarioComprometido = this.productoDao.getInventarioComprometido(producto, null, bodega, fecha, false);
/* 429:    */     
/* 430:    */ 
/* 431:534 */     this.productoDao.actualizarInventarioComprometido(producto, null, bodega, fecha, cantidad, inventarioComprometido, false);
/* 432:    */   }
/* 433:    */   
/* 434:    */   @Lock(LockType.WRITE)
/* 435:    */   public void actualizarInventarioComprometidoDespacho(DetalleRegistroPesoLote drp)
/* 436:    */   {
/* 437:541 */     Producto producto = drp.getProducto();
/* 438:542 */     Bodega bodega = drp.getRegistroPeso().getBodega();
/* 439:543 */     Date fecha = drp.getRegistroPeso().getFecha();
/* 440:544 */     Lote lote = drp.getLote();
/* 441:545 */     EstadoRegistroPeso estado = drp.getRegistroPeso().getEstado();
/* 442:    */     
/* 443:    */ 
/* 444:548 */     BigDecimal inventarioComprometido = this.productoDao.getInventarioComprometido(producto, lote, bodega, fecha, false);
/* 445:551 */     if (drp.getId() != 0)
/* 446:    */     {
/* 447:552 */       DetalleRegistroPesoLote detalleBase = (DetalleRegistroPesoLote)this.detalleRegistroPesoDao.buscarPorId(DetalleRegistroPesoLote.class, Integer.valueOf(drp.getId()));
/* 448:553 */       BigDecimal inventarioComprometidoRevertir = detalleBase.getCantidad();
/* 449:554 */       inventarioComprometidoRevertir = inventarioComprometidoRevertir.multiply(new BigDecimal(-1));
/* 450:    */       
/* 451:556 */       this.productoDao
/* 452:557 */         .actualizarInventarioComprometido(producto, lote, bodega, fecha, inventarioComprometidoRevertir, inventarioComprometido, false);
/* 453:    */     }
/* 454:561 */     if (!EstadoRegistroPeso.SEGUNDO_PESO.equals(estado)) {
/* 455:562 */       this.productoDao.actualizarInventarioComprometido(producto, lote, bodega, fecha, drp.getCantidad(), inventarioComprometido, false);
/* 456:    */     }
/* 457:    */   }
/* 458:    */   
/* 459:    */   @Lock(LockType.WRITE)
/* 460:    */   public void actualizarInventarioComprometidoProduccion(DetalleOrdenFabricacionMaterial dofm, Date fecha, boolean indicadorAnularFinalizar)
/* 461:    */     throws AS2Exception
/* 462:    */   {
/* 463:570 */     Producto producto = dofm.getMaterial();
/* 464:571 */     if (producto.getId() != dofm.getOrdenFabricacion().getProducto().getId())
/* 465:    */     {
/* 466:572 */       Bodega bodega = dofm.getBodegaTrabajoMaterial();
/* 467:573 */       if (bodega == null) {
/* 468:574 */         bodega = this.servicioProducto.obtenerBodegaTrabajoProducto(producto, Integer.valueOf(dofm.getSucursal().getId()));
/* 469:    */       }
/* 470:576 */       if (bodega == null) {
/* 471:577 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PARAMETRIZAR_BODEGA_TRABAJO", new String[] { producto.getNombre() });
/* 472:    */       }
/* 473:579 */       Lote lote = dofm.getLote();
/* 474:    */       
/* 475:    */ 
/* 476:582 */       BigDecimal saldo = BigDecimal.ZERO;
/* 477:583 */       if (!indicadorAnularFinalizar) {
/* 478:584 */         if (lote == null) {
/* 479:585 */           saldo = this.productoDao.getSaldo(producto.getId(), bodega.getId(), fecha);
/* 480:    */         } else {
/* 481:587 */           saldo = this.productoDao.getSaldoLote(producto.getId(), bodega.getId(), lote.getId(), fecha);
/* 482:    */         }
/* 483:    */       }
/* 484:591 */       BigDecimal inventarioComprometido = this.productoDao.getInventarioComprometido(producto, lote, bodega, fecha, true);
/* 485:592 */       BigDecimal inventarioComprometidoRevertir = BigDecimal.ZERO;
/* 486:595 */       if (dofm.getId() != 0)
/* 487:    */       {
/* 488:596 */         DetalleOrdenFabricacionMaterial detalleBase = (DetalleOrdenFabricacionMaterial)this.detalleOrdenFabricacionMaterialDao.buscarPorId(DetalleOrdenFabricacionMaterial.class, 
/* 489:597 */           Integer.valueOf(dofm.getId()));
/* 490:598 */         inventarioComprometidoRevertir = detalleBase.getCantidad();
/* 491:599 */         inventarioComprometidoRevertir = inventarioComprometidoRevertir.multiply(new BigDecimal(-1));
/* 492:    */         
/* 493:601 */         this.productoDao.actualizarInventarioComprometido(producto, lote, bodega, fecha, inventarioComprometidoRevertir, inventarioComprometido, true);
/* 494:    */       }
/* 495:606 */       if (!indicadorAnularFinalizar)
/* 496:    */       {
/* 497:607 */         BigDecimal saldoActual = saldo.subtract(inventarioComprometido).add(inventarioComprometidoRevertir.abs());
/* 498:608 */         if (saldoActual.compareTo(dofm.getCantidad()) < 0)
/* 499:    */         {
/* 500:609 */           this.context.setRollbackOnly();
/* 501:    */           
/* 502:611 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_SALDO_PRODUCTO_BODEGA", new String[] { producto.getNombre(), bodega.getNombre(), dofm.getCantidad().toString() + " > " + saldoActual.toString() });
/* 503:    */         }
/* 504:    */       }
/* 505:615 */       if (!indicadorAnularFinalizar) {
/* 506:617 */         this.productoDao.actualizarInventarioComprometido(producto, lote, bodega, fecha, dofm.getCantidad(), inventarioComprometido, true);
/* 507:    */       }
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public <T extends EntidadBase> void cantidadDetalle(List<? extends EntidadBase> listaDetalle)
/* 512:    */     throws ExcepcionAS2Inventario
/* 513:    */   {
/* 514:624 */     if ((listaDetalle.isEmpty()) || (listaDetalle == null))
/* 515:    */     {
/* 516:625 */       String mensaje = ": No se ha ingresado el detalle del producto";
/* 517:626 */       throw new ExcepcionAS2Inventario("msg_error_cantidad_0", mensaje);
/* 518:    */     }
/* 519:    */   }
/* 520:    */   
/* 521:    */   public <T extends EntidadBase> void verificarTotalDetalle(List<? extends EntidadBase> listaTotal)
/* 522:    */     throws ExcepcionAS2Inventario
/* 523:    */   {
/* 524:639 */     BigDecimal valorTotal = BigDecimal.ZERO;
/* 525:640 */     int contDetalle = 0;
/* 526:641 */     boolean tipoMensaje = false;
/* 527:642 */     boolean validaCantidad = true;
/* 528:644 */     for (EntidadBase eb : listaTotal) {
/* 529:645 */       if ((eb instanceof DetalleMovimientoInventario))
/* 530:    */       {
/* 531:646 */         DetalleMovimientoInventario detalleMovimientoInventario = (DetalleMovimientoInventario)eb;
/* 532:647 */         if (!detalleMovimientoInventario.isEliminado())
/* 533:    */         {
/* 534:648 */           valorTotal = valorTotal.add(detalleMovimientoInventario.getCantidad());
/* 535:649 */           contDetalle++;
/* 536:    */         }
/* 537:    */       }
/* 538:651 */       else if ((eb instanceof DetalleFacturaCliente))
/* 539:    */       {
/* 540:652 */         DetalleFacturaCliente detalleFacturaCliente = (DetalleFacturaCliente)eb;
/* 541:653 */         validaCantidad = detalleFacturaCliente.getFacturaCliente().getDocumento().isIndicadorDocumentoTributario();
/* 542:654 */         if (!detalleFacturaCliente.isEliminado())
/* 543:    */         {
/* 544:655 */           valorTotal = valorTotal.add(detalleFacturaCliente.getPrecioLinea()).setScale(6, 4);
/* 545:656 */           tipoMensaje = true;
/* 546:657 */           contDetalle++;
/* 547:    */         }
/* 548:    */       }
/* 549:659 */       else if ((eb instanceof DetalleFacturaProveedor))
/* 550:    */       {
/* 551:660 */         DetalleFacturaProveedor detalleFacturaProveedor = (DetalleFacturaProveedor)eb;
/* 552:661 */         validaCantidad = detalleFacturaProveedor.getFacturaProveedor().getDocumento().isIndicadorDocumentoTributario();
/* 553:662 */         if (!detalleFacturaProveedor.isEliminado())
/* 554:    */         {
/* 555:663 */           valorTotal = valorTotal.add(detalleFacturaProveedor.getPrecio()).setScale(6, 4);
/* 556:664 */           tipoMensaje = true;
/* 557:665 */           contDetalle++;
/* 558:    */         }
/* 559:    */       }
/* 560:667 */       else if ((eb instanceof DetallePago))
/* 561:    */       {
/* 562:668 */         DetallePago detallePago = (DetallePago)eb;
/* 563:669 */         if (!detallePago.isEliminado())
/* 564:    */         {
/* 565:670 */           valorTotal = valorTotal.add(detallePago.getValor());
/* 566:671 */           tipoMensaje = true;
/* 567:672 */           contDetalle++;
/* 568:    */         }
/* 569:    */       }
/* 570:    */     }
/* 571:676 */     if ((valorTotal.compareTo(BigDecimal.ZERO) == 0) && (validaCantidad))
/* 572:    */     {
/* 573:677 */       String mensaje = "";
/* 574:678 */       if (tipoMensaje) {
/* 575:680 */         mensaje = ": Se ha ingresado " + (contDetalle == 1 ? contDetalle + " detalle" : new StringBuilder().append(contDetalle).append(" detalles ").toString()) + " con valor = " + valorTotal.abs().toString();
/* 576:    */       } else {
/* 577:683 */         mensaje = ": Se ha ingresado " + (contDetalle == 1 ? contDetalle + " detalle" : new StringBuilder().append(contDetalle).append(" detalles ").toString()) + " con cantidad = " + valorTotal.abs().toString();
/* 578:    */       }
/* 579:685 */       throw new ExcepcionAS2Inventario("msg_error_cantidad_0", mensaje);
/* 580:    */     }
/* 581:    */   }
/* 582:    */   
/* 583:    */   @Lock(LockType.WRITE)
/* 584:    */   public void abrirCerrarOrdenSalidaMaterial(MovimientoInventario mi)
/* 585:    */   {
/* 586:692 */     Map<Integer, OrdenSalidaMaterial> hmOrdenSalida = new HashMap();
/* 587:694 */     for (DetalleMovimientoInventario dmi : mi.getDetalleMovimientosInventario())
/* 588:    */     {
/* 589:695 */       DetalleOrdenSalidaMaterial dosm = dmi.getDetalleOrdenSalidaMaterial();
/* 590:696 */       if (dosm != null) {
/* 591:697 */         hmOrdenSalida.put(Integer.valueOf(dosm.getOrdenSalidaMaterial().getId()), dosm.getOrdenSalidaMaterial());
/* 592:    */       }
/* 593:    */     }
/* 594:701 */     for (OrdenSalidaMaterial osm : hmOrdenSalida.values()) {
/* 595:702 */       if (osm.getEstado() != Estado.CERRADO) {
/* 596:703 */         this.ordenSalidaMaterialDao.abrirCerrarOrdenSalidaMaterial(osm);
/* 597:    */       }
/* 598:    */     }
/* 599:    */   }
/* 600:    */   
/* 601:    */   @Lock(LockType.WRITE)
/* 602:    */   public BigDecimal actualizarCantidadFabricadaOrdenFabricacion(OrdenFabricacion ordenFabricacion, BigDecimal cantidad)
/* 603:    */   {
/* 604:711 */     return this.ordenFabricacionDao.actualizarCantidadFabricada(ordenFabricacion, cantidad);
/* 605:    */   }
/* 606:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioVerificadorInventarioImpl
 * JD-Core Version:    0.7.0.1
 */