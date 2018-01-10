/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteInventarioProducto;
/*   4:    */ import com.asinfo.as2.dao.BodegaDao;
/*   5:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*   6:    */ import com.asinfo.as2.dao.LoteDao;
/*   7:    */ import com.asinfo.as2.dao.ProductoDao;
/*   8:    */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*   9:    */ import com.asinfo.as2.entities.Bodega;
/*  10:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  12:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  13:    */ import com.asinfo.as2.entities.DimensionContable;
/*  14:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  15:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  16:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  17:    */ import com.asinfo.as2.entities.Lote;
/*  18:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  19:    */ import com.asinfo.as2.entities.Producto;
/*  20:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  21:    */ import com.asinfo.as2.entities.SaldoProducto;
/*  22:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  23:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  24:    */ import com.asinfo.as2.entities.produccion.CostosDeFabricacion;
/*  25:    */ import com.asinfo.as2.entities.produccion.DetalleCostoFabricacion;
/*  26:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  27:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  28:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  29:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  30:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  31:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  32:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  33:    */ import java.io.PrintStream;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.math.RoundingMode;
/*  36:    */ import java.util.Collection;
/*  37:    */ import java.util.Date;
/*  38:    */ import java.util.HashMap;
/*  39:    */ import java.util.Iterator;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Set;
/*  42:    */ import javax.ejb.EJB;
/*  43:    */ import javax.ejb.Stateless;
/*  44:    */ 
/*  45:    */ @Stateless
/*  46:    */ public class ServicioInventarioProductoImpl
/*  47:    */   implements ServicioInventarioProducto
/*  48:    */ {
/*  49:    */   @EJB
/*  50:    */   private transient InventarioProductoDao inventarioProductoDao;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioGenerico<SaldoProducto> saldoProductoDao;
/*  53:    */   @EJB
/*  54:    */   private transient ProductoDao productoDao;
/*  55:    */   @EJB
/*  56:    */   private transient BodegaDao bodegaDao;
/*  57:    */   @EJB
/*  58:    */   private transient LoteDao loteDao;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioGenerico<SaldoProductoLote> saldoProductoLoteDao;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioCosteo servicioCosteo;
/*  63:    */   @EJB
/*  64:    */   private OrdenFabricacionDao ordenFabricacionDao;
/*  65:    */   
/*  66:    */   public List<InventarioProducto> obtenerMovimientos(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta)
/*  67:    */   {
/*  68: 87 */     return this.inventarioProductoDao.obtenerMovimientos(idOrganizacion, producto, bodega, fechaDesde, fechaHasta);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void guardar(InventarioProducto inventarioProducto)
/*  72:    */   {
/*  73:107 */     this.inventarioProductoDao.guardar(inventarioProducto);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void eliminaInventarioProductoPorIdDespachoCliente(Integer idDespachoCliente)
/*  77:    */   {
/*  78:117 */     this.inventarioProductoDao.eliminaInventarioProductoPorIdDespachoCliente(idDespachoCliente);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void eliminaInventarioProductoPorIdRecepcionProveedor(Integer idRecepcionProveedor)
/*  82:    */   {
/*  83:129 */     this.inventarioProductoDao.eliminaInventarioProductoPorIdRecepcionProveedor(idRecepcionProveedor);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void eliminaInventarioProductoPorIdMovimientoInventario(Integer idMovimientoInventario)
/*  87:    */   {
/*  88:134 */     this.inventarioProductoDao.eliminaInventarioProductoPorIdMovimientoInventario(idMovimientoInventario);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void eliminaInventarioProductoConIdAjusteTransferencia(Integer idMovimientoInventario)
/*  92:    */   {
/*  93:139 */     this.inventarioProductoDao.eliminaInventarioProductoConIdAjusteTransferencia(idMovimientoInventario);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void eliminaInventarioProductoPorIdDevolucionProveedor(Integer idDevolucionProveedor)
/*  97:    */   {
/*  98:144 */     this.inventarioProductoDao.eliminaInventarioProductoPorIdDevolucionProveedor(idDevolucionProveedor);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void eliminaInventarioProductoPorIdDevolucionCliente(Integer idDevolucionCliente)
/* 102:    */   {
/* 103:149 */     this.inventarioProductoDao.eliminaInventarioProductoPorIdDevolucionCliente(idDevolucionCliente);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<Producto> obtenerProductosConMovimiento(int idOrganizacion, Date fechaDesde, Date fechaHasta, Bodega bodega)
/* 107:    */   {
/* 108:159 */     return this.inventarioProductoDao.obtenerProductosConMovimiento(idOrganizacion, fechaDesde, fechaHasta, bodega);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void actualizarCostoInicial(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 112:    */   {
/* 113:169 */     this.inventarioProductoDao.actualizarCostoInicial(idOrganizacion, fechaDesde, fechaHasta);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public InventarioProducto cargarDetalle(int idInventarioProducto)
/* 117:    */   {
/* 118:179 */     return this.inventarioProductoDao.cargarDetalle(idInventarioProducto);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void generarMovimientoInventarioInverso(FacturaCliente devolucionCliente, Date fechaAnulacion)
/* 122:    */   {
/* 123:189 */     this.inventarioProductoDao.generarMovimientoInventarioInverso(devolucionCliente, fechaAnulacion);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void generarMovimientoInventarioInverso(FacturaProveedor devolucionPrveedor, Date fechaAnulacion)
/* 127:    */   {
/* 128:200 */     this.inventarioProductoDao.generarMovimientoInventarioInverso(devolucionPrveedor, fechaAnulacion);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void generarMovimientoInventarioInverso(DespachoCliente despachoCliente, Date fechaAnulacion)
/* 132:    */   {
/* 133:212 */     this.inventarioProductoDao.generarMovimientoInventarioInverso(despachoCliente, fechaAnulacion);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void generarMovimientoInventarioInverso(RecepcionProveedor recepcionProveedor, Date fechaAnulacion)
/* 137:    */   {
/* 138:223 */     this.inventarioProductoDao.generarMovimientoInventarioInverso(recepcionProveedor, fechaAnulacion);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void generarMovimientoInventarioInverso(MovimientoInventario movimientoInventario, Date fechaAnulacion)
/* 142:    */   {
/* 143:228 */     this.inventarioProductoDao.generarMovimientoInventarioInverso(movimientoInventario, null, fechaAnulacion);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void generarMovimientoInventarioInverso(MovimientoInventario movimientoInventario, DetalleMovimientoInventario dmi, Date fechaAnulacion)
/* 147:    */   {
/* 148:233 */     this.inventarioProductoDao.generarMovimientoInventarioInverso(movimientoInventario, dmi, fechaAnulacion);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public List<InventarioProducto> obtenerMovimientos(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta, Boolean indicadorCostoEstandar)
/* 152:    */   {
/* 153:239 */     return this.inventarioProductoDao.obtenerMovimientos(idOrganizacion, producto, bodega, fechaDesde, fechaHasta, indicadorCostoEstandar);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<ReporteInventarioProducto> obtenerMovimientosInventarioProducto(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta, Lote lote, DimensionContable proyecto, int numeroAtributos)
/* 157:    */   {
/* 158:252 */     return this.inventarioProductoDao.obtenerMovimientosInventarioProducto(idOrganizacion, producto, bodega, fechaDesde, fechaHasta, lote, proyecto, numeroAtributos);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public BigDecimal obtenerCostoTransformacion(InventarioProducto inventarioProducto, Date fechaDesde, Date fechaHasta, Bodega bodega, Integer versionCosteo, int idOrganizacion, Set<Integer> setIdProducto)
/* 162:    */   {
/* 163:267 */     List<Producto> listaMateriales = this.inventarioProductoDao.obtenerMaterialesTransformacion(inventarioProducto);
/* 164:268 */     for (Producto producto : listaMateriales) {
/* 165:269 */       if ((versionCosteo == null) || (producto.getVersionCosteo() != versionCosteo.intValue())) {
/* 166:270 */         this.servicioCosteo.generarCostos(idOrganizacion, producto, fechaDesde, fechaHasta, bodega, versionCosteo, setIdProducto);
/* 167:    */       }
/* 168:    */     }
/* 169:274 */     return this.inventarioProductoDao.obtenerCostoTransformacion(inventarioProducto);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List obtenerSaldosPorBodegaProyecto(Bodega bodega, DimensionContable proyecto, Date fecha)
/* 173:    */   {
/* 174:280 */     return this.inventarioProductoDao.obtenerSaldosPorBodegaProyecto(bodega, proyecto, fecha);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void eliminarInventarioProductoDetalleMovimientoInventario(DetalleMovimientoInventario dmi)
/* 178:    */   {
/* 179:285 */     this.inventarioProductoDao.eliminarInventarioProductoDetalleMovimientoInventario(dmi);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<Producto> obtenerProductos(int id, CategoriaProducto categoriaProducto, SubcategoriaProducto subCategoriaProducto)
/* 183:    */   {
/* 184:290 */     return this.inventarioProductoDao.obtenerProductos(id, categoriaProducto, subCategoriaProducto);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void actualizarSaldoPorProducto(int idOrganizacion, Producto producto, boolean indicadorLote, Date fechaCorte)
/* 188:    */   {
/* 189:295 */     HashMap<String, List<Object[]>> result = this.inventarioProductoDao.obtenerInventarioProducto(producto, indicadorLote, fechaCorte);
/* 190:296 */     HashMap<String, SaldoProducto> hashSaldoProducto = new HashMap();
/* 191:297 */     HashMap<String, SaldoProductoLote> hashSaldoProductoLote = new HashMap();
/* 192:298 */     HashMap<Integer, Bodega> hashBodegas = new HashMap();
/* 193:299 */     HashMap<Integer, Lote> hashLotes = new HashMap();
/* 194:300 */     HashMap<String, Object> hashLoteBodega = new HashMap();
/* 195:302 */     if (indicadorLote) {
/* 196:303 */       for (SaldoProductoLote spp : this.inventarioProductoDao.consultarSaldoProductoLote(producto, fechaCorte))
/* 197:    */       {
/* 198:304 */         spp.setEliminado(true);
/* 199:305 */         hashSaldoProductoLote.put(spp.getBodega().getId() + "~" + spp.getProducto().getId() + "~" + spp.getFecha() + "~" + spp
/* 200:306 */           .getLote().getId(), spp);
/* 201:    */       }
/* 202:    */     } else {
/* 203:309 */       for (SaldoProducto spp : this.inventarioProductoDao.consultarSaldoProducto(producto, fechaCorte))
/* 204:    */       {
/* 205:310 */         spp.setEliminado(true);
/* 206:311 */         hashSaldoProducto.put(spp.getBodega().getId() + "~" + spp.getProducto().getId() + "~" + spp.getFecha(), spp);
/* 207:    */       }
/* 208:    */     }
/* 209:    */     try
/* 210:    */     {
/* 211:316 */       for (??? = result.values().iterator(); ???.hasNext();)
/* 212:    */       {
/* 213:316 */         List<Object[]> listObj = (List)???.next();
/* 214:317 */         saldoI = BigDecimal.ZERO;
/* 215:318 */         for (Object[] objPro : listObj)
/* 216:    */         {
/* 217:319 */           Integer idBodega = Integer.valueOf(objPro[0].toString());
/* 218:320 */           Integer idProducto = Integer.valueOf(objPro[1].toString());
/* 219:321 */           Date fecha = (Date)objPro[2];
/* 220:322 */           BigDecimal total = (BigDecimal)objPro[3];
/* 221:    */           
/* 222:324 */           Bodega bodega = (Bodega)hashBodegas.get(idBodega);
/* 223:326 */           if (bodega == null)
/* 224:    */           {
/* 225:327 */             bodega = (Bodega)this.bodegaDao.buscarPorId(idBodega);
/* 226:328 */             hashBodegas.put(idBodega, bodega);
/* 227:329 */             if (!indicadorLote) {
/* 228:330 */               saldoI = this.productoDao.getSaldo(idProducto.intValue(), idBodega.intValue(), FuncionesUtiles.sumarFechaDiasMeses(fechaCorte, -1));
/* 229:    */             }
/* 230:    */           }
/* 231:334 */           if (indicadorLote)
/* 232:    */           {
/* 233:335 */             Integer idLote = Integer.valueOf(objPro[4].toString());
/* 234:336 */             Lote lote = (Lote)hashLotes.get(idLote);
/* 235:337 */             if (lote == null) {
/* 236:338 */               hashLotes.put(idLote, this.loteDao.buscarPorId(idLote));
/* 237:    */             }
/* 238:341 */             Object o = hashLoteBodega.get(Integer.toString(idLote.intValue()) + "~" + Integer.toString(idBodega.intValue()));
/* 239:342 */             if (o == null)
/* 240:    */             {
/* 241:343 */               saldoI = this.productoDao.getSaldoLote(idProducto.intValue(), idBodega.intValue(), idLote.intValue(), FuncionesUtiles.sumarFechaDiasMeses(fechaCorte, -1));
/* 242:344 */               hashLoteBodega.put(Integer.toString(idLote.intValue()) + "~" + Integer.toString(idBodega.intValue()), "");
/* 243:    */             }
/* 244:347 */             SaldoProductoLote spl = (SaldoProductoLote)hashSaldoProductoLote.get(idBodega + "~" + idProducto + "~" + fecha + "~" + idLote);
/* 245:348 */             if (spl != null)
/* 246:    */             {
/* 247:349 */               spl.setEliminado(false);
/* 248:350 */               spl.setFecha(fecha);
/* 249:351 */               saldoI = saldoI.add(total);
/* 250:352 */               spl.setSaldo(saldoI);
/* 251:353 */               spl.setProducto(producto);
/* 252:354 */               spl.setBodega(bodega);
/* 253:355 */               spl.setLote((Lote)hashLotes.get(idLote));
/* 254:356 */               this.saldoProductoLoteDao.guardar(spl);
/* 255:    */             }
/* 256:    */             else
/* 257:    */             {
/* 258:358 */               spl = new SaldoProductoLote();
/* 259:359 */               spl.setFecha(fecha);
/* 260:360 */               saldoI = saldoI.add(total);
/* 261:361 */               spl.setSaldo(saldoI);
/* 262:362 */               spl.setProducto(producto);
/* 263:363 */               spl.setBodega(bodega);
/* 264:364 */               spl.setLote((Lote)hashLotes.get(idLote));
/* 265:365 */               this.saldoProductoLoteDao.guardar(spl);
/* 266:    */             }
/* 267:    */           }
/* 268:    */           else
/* 269:    */           {
/* 270:368 */             SaldoProducto sp = (SaldoProducto)hashSaldoProducto.get(idBodega + "~" + idProducto + "~" + fecha);
/* 271:369 */             if (sp != null)
/* 272:    */             {
/* 273:370 */               sp.setEliminado(false);
/* 274:371 */               sp.setInventarioComprometido(BigDecimal.ZERO);
/* 275:372 */               sp.setFecha(fecha);
/* 276:373 */               saldoI = saldoI.add(total);
/* 277:374 */               sp.setSaldo(saldoI);
/* 278:375 */               sp.setProducto(producto);
/* 279:376 */               sp.setBodega(bodega);
/* 280:377 */               this.saldoProductoDao.guardar(sp);
/* 281:    */             }
/* 282:    */             else
/* 283:    */             {
/* 284:379 */               sp = new SaldoProducto();
/* 285:380 */               sp.setIdOrganizacion(idOrganizacion);
/* 286:381 */               sp.setInventarioComprometido(BigDecimal.ZERO);
/* 287:382 */               sp.setFecha(fecha);
/* 288:383 */               saldoI = saldoI.add(total);
/* 289:384 */               sp.setSaldo(saldoI);
/* 290:385 */               sp.setProducto(producto);
/* 291:386 */               sp.setBodega(bodega);
/* 292:387 */               this.saldoProductoDao.guardar(sp);
/* 293:    */             }
/* 294:    */           }
/* 295:    */         }
/* 296:    */       }
/* 297:    */       BigDecimal saldoI;
/* 298:392 */       for (??? = hashSaldoProductoLote.values().iterator(); ???.hasNext();)
/* 299:    */       {
/* 300:392 */         SaldoProductoLote spl = (SaldoProductoLote)???.next();
/* 301:393 */         if (!spl.isEliminado()) {
/* 302:394 */           this.saldoProductoLoteDao.guardar(spl);
/* 303:    */         }
/* 304:    */       }
/* 305:397 */       for (??? = hashSaldoProducto.values().iterator(); ???.hasNext();)
/* 306:    */       {
/* 307:397 */         SaldoProducto sp = (SaldoProducto)???.next();
/* 308:398 */         if (!sp.isEliminado()) {
/* 309:399 */           this.saldoProductoDao.guardar(sp);
/* 310:    */         }
/* 311:    */       }
/* 312:    */     }
/* 313:    */     catch (AS2Exception e)
/* 314:    */     {
/* 315:403 */       e.printStackTrace();
/* 316:    */     }
/* 317:    */     catch (Exception e)
/* 318:    */     {
/* 319:405 */       e.printStackTrace();
/* 320:406 */       System.out.println(e);
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public InventarioProducto buscarPorId(Integer idInventarioProducto)
/* 325:    */   {
/* 326:412 */     return (InventarioProducto)this.inventarioProductoDao.buscarPorId(idInventarioProducto);
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void actualizarComponentesCostoFabricacion(DetalleCostoFabricacion detalleCostoFabricacion)
/* 330:    */   {
/* 331:418 */     boolean indicadorCicloLargo = detalleCostoFabricacion.getOrdenFabricacion().getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_LARGO);
/* 332:419 */     Date fechaDesde = indicadorCicloLargo ? detalleCostoFabricacion.getCostosDeFabricacion().getFechaDesde() : null;
/* 333:420 */     Date fechaHasta = indicadorCicloLargo ? detalleCostoFabricacion.getCostosDeFabricacion().getFechaHasta() : null;
/* 334:421 */     if (indicadorCicloLargo)
/* 335:    */     {
/* 336:422 */       System.out.println("fechaDesde: " + fechaDesde);
/* 337:423 */       System.out.println("fechaHasta: " + fechaHasta);
/* 338:    */     }
/* 339:426 */     List<InventarioProducto> listaInventario = this.inventarioProductoDao.buscarInventarioProductoByOrdenFabricacion(detalleCostoFabricacion.getOrdenFabricacion(), fechaDesde, fechaHasta);
/* 340:428 */     for (InventarioProducto inventario : listaInventario)
/* 341:    */     {
/* 342:429 */       if (indicadorCicloLargo) {
/* 343:430 */         System.out.println("fechainventario: " + inventario.getFecha());
/* 344:    */       }
/* 345:438 */       BigDecimal costoManoObra = inventario.getCantidad().multiply(detalleCostoFabricacion.getCostoUnitarioManoObraAsignado());
/* 346:439 */       BigDecimal costoDepreciacion = inventario.getCantidad().multiply(detalleCostoFabricacion.getCostoUnitarioDepreciacionAsignado());
/* 347:440 */       BigDecimal costoIndirectos = inventario.getCantidad().multiply(detalleCostoFabricacion.getCostoUnitarioIndirectosAsignado());
/* 348:441 */       if (indicadorCicloLargo)
/* 349:    */       {
/* 350:443 */         BigDecimal costoMateriales = inventario.getCantidad().multiply(detalleCostoFabricacion.getCostoUnitarioMaterialesAsignado());
/* 351:444 */         inventario.setCostoMateriales(costoMateriales.setScale(4, RoundingMode.HALF_UP));
/* 352:    */       }
/* 353:451 */       inventario.setCostoManoDeObra(costoManoObra.setScale(4, RoundingMode.HALF_UP));
/* 354:452 */       inventario.setCostoDepreciaciones(costoDepreciacion.setScale(4, RoundingMode.HALF_UP));
/* 355:453 */       inventario.setCostoIndirectos(costoIndirectos.setScale(4, RoundingMode.HALF_UP));
/* 356:454 */       inventario.setCosto(inventario.getCostoMateriales().add(inventario.getCostoManoDeObra()).add(inventario.getCostoDepreciaciones())
/* 357:455 */         .add(inventario.getCostoIndirectos()).setScale(4, RoundingMode.HALF_UP));
/* 358:    */       
/* 359:457 */       System.out.println("Costo--------------------------->" + inventario.getCosto());
/* 360:    */       
/* 361:459 */       this.inventarioProductoDao.guardar(inventario);
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void flush()
/* 366:    */   {
/* 367:465 */     this.inventarioProductoDao.flush();
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void eliminaInventarioProductoTransformacionPorIdDevolucionProveedor(Integer idDevolucionProveedor)
/* 371:    */   {
/* 372:470 */     this.inventarioProductoDao.eliminaInventarioProductoTransformacionPorIdDevolucionProveedor(idDevolucionProveedor);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void generarMovimientoInventarioTransformacionInverso(FacturaProveedor devolucionProveedor, Date fechaAnulacion)
/* 376:    */   {
/* 377:475 */     this.inventarioProductoDao.generarMovimientoInventarioTransformacionInverso(devolucionProveedor, fechaAnulacion);
/* 378:    */   }
/* 379:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioInventarioProductoImpl
 * JD-Core Version:    0.7.0.1
 */