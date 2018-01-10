/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteSaldoProducto;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Atributo;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.Unidad;
/*  12:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  21:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValorado;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  25:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  26:    */ import java.io.IOException;
/*  27:    */ import java.math.BigDecimal;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import java.util.TreeMap;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import javax.faces.model.SelectItem;
/*  38:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  39:    */ import net.sf.jasperreports.engine.JRException;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ 
/*  42:    */ @ManagedBean
/*  43:    */ @ViewScoped
/*  44:    */ public class ReportePedidoVSInventarioBean
/*  45:    */   extends AbstractBaseReportBean
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = 1L;
/*  48:    */   @EJB
/*  49:    */   private ServicioReporteStockValorado servicioReporteStockValorado;
/*  50:    */   @EJB
/*  51:    */   private ServicioBodega servicioBodega;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioProducto servicioProducto;
/*  54:    */   @EJB
/*  55:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  56:    */   @EJB
/*  57:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  58:    */   @EJB
/*  59:    */   private ServicioAtributo servicioAtributo;
/*  60:    */   @EJB
/*  61:    */   private ServicioValorAtributo servicioValorAtributo;
/*  62:    */   
/*  63:    */   private static enum enumUnidad
/*  64:    */   {
/*  65: 83 */     STOCK,  VENTA,  ALMACENAMIENTO;
/*  66:    */     
/*  67:    */     private enumUnidad() {}
/*  68:    */   }
/*  69:    */   
/*  70: 86 */   private enumUnidad unidad = enumUnidad.STOCK;
/*  71:    */   private List<SelectItem> listaUnidad;
/*  72: 90 */   private Date fechaHasta = new Date();
/*  73:    */   private Bodega bodega;
/*  74:    */   private List<Bodega> listaBodega;
/*  75:    */   private boolean indicadorAtributo;
/*  76:    */   private Atributo atributo1;
/*  77:    */   private String valorAtributo1;
/*  78:    */   private Atributo atributo2;
/*  79:    */   private String valorAtributo2;
/*  80:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  81:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  82:    */   private Producto productoSeleccionado;
/*  83:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  84:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  85:    */   private List<Atributo> listaAtributo;
/*  86:    */   private List<SelectItem> listaValorAtributo1;
/*  87:    */   private List<SelectItem> listaValorAtributo2;
/*  88:    */   
/*  89:    */   protected JRDataSource getJRDataSource()
/*  90:    */   {
/*  91:121 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  92:122 */     JRDataSource ds = null;
/*  93:    */     try
/*  94:    */     {
/*  95:125 */       Map<String, ReporteSaldoProducto> hmReporte = new TreeMap();
/*  96:127 */       for (ReporteSaldoProducto rSaldoProducto : this.servicioReporteStockValorado.getReporteSaldoProducto(null, this.bodega, this.fechaHasta, this.atributo1, this.valorAtributo1, 
/*  97:128 */         AppUtil.getOrganizacion().getId(), this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.atributo2, this.valorAtributo2, null))
/*  98:    */       {
/*  99:131 */         String clave = rSaldoProducto.getIdBodega() + ":" + rSaldoProducto.getNombreProducto() + ":" + rSaldoProducto.getIdProducto();
/* 100:    */         
/* 101:133 */         BigDecimal saldo = this.servicioProducto.getSaldo(rSaldoProducto.getIdProducto().intValue(), rSaldoProducto.getIdBodega().intValue(), this.fechaHasta);
/* 102:134 */         Producto producto = new Producto();
/* 103:135 */         producto.setIdProducto(rSaldoProducto.getIdProducto().intValue());
/* 104:136 */         Bodega bodega = new Bodega();
/* 105:137 */         bodega.setIdBodega(rSaldoProducto.getIdBodega().intValue());
/* 106:138 */         BigDecimal saldoComprometido = this.servicioProducto.getInventarioComprometido(producto, null, bodega, this.fechaHasta, false);
/* 107:140 */         if (saldo.compareTo(BigDecimal.ZERO) != 0)
/* 108:    */         {
/* 109:141 */           rSaldoProducto.setSaldo(saldo);
/* 110:142 */           rSaldoProducto.setSaldoComprometido(saldoComprometido);
/* 111:    */           try
/* 112:    */           {
/* 113:149 */             Unidad unidadStock = new Unidad(rSaldoProducto.getIdUnidad().intValue(), rSaldoProducto.getCodigoUnidad(), rSaldoProducto.getNombreUnidad());
/* 114:150 */             Unidad unidadConversion = unidadStock;
/* 115:152 */             if (this.unidad.equals(enumUnidad.ALMACENAMIENTO)) {
/* 116:156 */               unidadConversion = rSaldoProducto.getIdUnidadAlmacenamiento() == null ? unidadStock : new Unidad(rSaldoProducto.getIdUnidadAlmacenamiento().intValue(), rSaldoProducto.getCodigoUnidadAlmacenamiento(), rSaldoProducto.getNombreUnidadAlmacenamiento());
/* 117:158 */             } else if (this.unidad.equals(enumUnidad.VENTA)) {
/* 118:161 */               unidadConversion = rSaldoProducto.getIdUnidadVenta() == null ? unidadStock : new Unidad(rSaldoProducto.getIdUnidadVenta().intValue(), rSaldoProducto.getCodigoUnidadVenta(), rSaldoProducto.getNombreUnidadVenta());
/* 119:    */             }
/* 120:165 */             BigDecimal cantidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, rSaldoProducto.getIdProducto().intValue(), rSaldoProducto
/* 121:166 */               .getSaldo());
/* 122:    */             
/* 123:168 */             BigDecimal cantidadComprometida = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, rSaldoProducto
/* 124:169 */               .getIdProducto().intValue(), rSaldoProducto.getSaldoComprometido());
/* 125:    */             
/* 126:    */ 
/* 127:172 */             rSaldoProducto.setSaldo(cantidad);
/* 128:173 */             rSaldoProducto.setSaldoComprometido(cantidadComprometida);
/* 129:174 */             rSaldoProducto.setCodigoUnidad(unidadConversion.getCodigo());
/* 130:    */           }
/* 131:    */           catch (ExcepcionAS2 localExcepcionAS2) {}
/* 132:179 */           hmReporte.put(clave, rSaldoProducto);
/* 133:    */         }
/* 134:    */       }
/* 135:184 */       for (ReporteSaldoProducto p : hmReporte.values())
/* 136:    */       {
/* 137:186 */         Object[] datos = new Object[9];
/* 138:187 */         datos[0] = p.getCodigoProducto();
/* 139:188 */         datos[1] = p.getNombreProducto();
/* 140:189 */         datos[2] = p.getCodigoBodega();
/* 141:190 */         datos[3] = p.getNombreBodega();
/* 142:191 */         datos[4] = p.getSaldo();
/* 143:192 */         datos[5] = p.getSaldoComprometido();
/* 144:193 */         datos[6] = p.getCodigoUnidad();
/* 145:194 */         datos[7] = p.getValorAtributo1();
/* 146:195 */         datos[8] = p.getValorAtributo2();
/* 147:196 */         listaDatosReporte.add(datos);
/* 148:    */       }
/* 149:200 */       String[] fields = { "f_codigoProducto", "f_nombreProducto", "f_codigoBodega", "f_nombreBodega", "f_saldoProducto", "f_saldoComprometido", "f_codigoUnidad", "f_valorAtributo1", "f_valorAtributo2" };
/* 150:    */       
/* 151:    */ 
/* 152:203 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 153:    */     }
/* 154:    */     catch (Exception e)
/* 155:    */     {
/* 156:206 */       e.printStackTrace();
/* 157:    */     }
/* 158:209 */     return ds;
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected String getCompileFileName()
/* 162:    */   {
/* 163:220 */     return "reportePedidoVSInventario";
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected Map<String, Object> getReportParameters()
/* 167:    */   {
/* 168:226 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 169:227 */     reportParameters.put("ReportTitle", "Pedido Vs Inventario");
/* 170:228 */     reportParameters.put("usuario", "Usuario:");
/* 171:229 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/* 172:230 */     reportParameters.put("pagina", "Pagina:");
/* 173:231 */     reportParameters.put("desde", "Desde:");
/* 174:232 */     reportParameters.put("hasta", "Hasta:");
/* 175:233 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 176:234 */     reportParameters.put("p_categoriaProducto", this.categoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 177:235 */     reportParameters.put("p_subcategoriaProducto", this.subcategoriaProductoSeleccionado != null ? this.subcategoriaProductoSeleccionado.getNombre() : "Todos");
/* 178:    */     
/* 179:237 */     reportParameters.put("p_unidad", this.unidad != null ? this.unidad.name().toString() : "");
/* 180:238 */     reportParameters
/* 181:239 */       .put("p_subcategoriaProducto", this.subcategoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 182:240 */     reportParameters.put("p_atributo1", this.atributo1 != null ? this.atributo1.getNombre() : null);
/* 183:241 */     reportParameters.put("p_atributo2", this.atributo2 != null ? this.atributo2.getNombre() : null);
/* 184:242 */     return reportParameters;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String execute()
/* 188:    */   {
/* 189:    */     try
/* 190:    */     {
/* 191:253 */       super.prepareReport();
/* 192:    */     }
/* 193:    */     catch (JRException e)
/* 194:    */     {
/* 195:256 */       LOG.info("Error JRException");
/* 196:257 */       e.printStackTrace();
/* 197:258 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 198:    */     }
/* 199:    */     catch (IOException e)
/* 200:    */     {
/* 201:260 */       LOG.info("Error IOException");
/* 202:261 */       e.printStackTrace();
/* 203:262 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 204:    */     }
/* 205:264 */     return "";
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void validaDatos() {}
/* 209:    */   
/* 210:    */   public void cargarListaSubcategoriaProducto()
/* 211:    */   {
/* 212:272 */     HashMap<String, String> filters = new HashMap();
/* 213:273 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 214:274 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void actualizarAtributo()
/* 218:    */   {
/* 219:278 */     if (!isIndicadorAtributo())
/* 220:    */     {
/* 221:279 */       this.atributo1 = null;
/* 222:280 */       this.atributo2 = null;
/* 223:281 */       this.valorAtributo1 = null;
/* 224:282 */       this.valorAtributo2 = null;
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void cargarListaValorAtributo1()
/* 229:    */   {
/* 230:287 */     if (this.atributo1 != null)
/* 231:    */     {
/* 232:288 */       this.listaValorAtributo1 = new ArrayList();
/* 233:289 */       HashMap<String, String> filters = new HashMap();
/* 234:290 */       filters.put("atributo.idAtributo", "" + this.atributo1.getId());
/* 235:291 */       for (ValorAtributo valorAtributo : this.servicioValorAtributo.obtenerListaCombo("nombre", true, filters)) {
/* 236:292 */         this.listaValorAtributo1.add(new SelectItem(valorAtributo.getNombre(), valorAtributo.getNombre()));
/* 237:    */       }
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void cargarListaValorAtributo2()
/* 242:    */   {
/* 243:298 */     if (this.atributo2 != null)
/* 244:    */     {
/* 245:299 */       this.listaValorAtributo2 = new ArrayList();
/* 246:300 */       HashMap<String, String> filters = new HashMap();
/* 247:301 */       filters.put("atributo.idAtributo", "" + this.atributo2.getId());
/* 248:302 */       for (ValorAtributo valorAtributo : this.servicioValorAtributo.obtenerListaCombo("nombre", true, filters)) {
/* 249:303 */         this.listaValorAtributo2.add(new SelectItem(valorAtributo.getNombre(), valorAtributo.getNombre()));
/* 250:    */       }
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Date getFechaHasta()
/* 255:    */   {
/* 256:314 */     return this.fechaHasta;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setFechaHasta(Date fechaHasta)
/* 260:    */   {
/* 261:324 */     this.fechaHasta = fechaHasta;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public Bodega getBodega()
/* 265:    */   {
/* 266:333 */     return this.bodega;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setBodega(Bodega bodega)
/* 270:    */   {
/* 271:343 */     this.bodega = bodega;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<Bodega> getListaBodega()
/* 275:    */   {
/* 276:352 */     if (this.listaBodega == null) {
/* 277:353 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 278:    */     }
/* 279:355 */     return this.listaBodega;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 283:    */   {
/* 284:365 */     this.listaBodega = listaBodega;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public enumUnidad getUnidad()
/* 288:    */   {
/* 289:374 */     return this.unidad;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setUnidad(enumUnidad unidad)
/* 293:    */   {
/* 294:384 */     this.unidad = unidad;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<SelectItem> getListaUnidad()
/* 298:    */   {
/* 299:393 */     if (this.listaUnidad == null)
/* 300:    */     {
/* 301:394 */       this.listaUnidad = new ArrayList();
/* 302:395 */       for (enumUnidad unidad : enumUnidad.values())
/* 303:    */       {
/* 304:396 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 305:397 */         this.listaUnidad.add(item);
/* 306:    */       }
/* 307:    */     }
/* 308:400 */     return this.listaUnidad;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 312:    */   {
/* 313:410 */     this.listaUnidad = listaUnidad;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Producto getProductoSeleccionado()
/* 317:    */   {
/* 318:417 */     return this.productoSeleccionado;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setProductoSeleccionado(Producto productoSeleccionado)
/* 322:    */   {
/* 323:425 */     this.productoSeleccionado = productoSeleccionado;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 327:    */   {
/* 328:432 */     if (this.categoriaProductoSeleccionado == null) {
/* 329:433 */       this.categoriaProductoSeleccionado = new CategoriaProducto();
/* 330:    */     }
/* 331:435 */     return this.categoriaProductoSeleccionado;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 335:    */   {
/* 336:443 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 340:    */   {
/* 341:450 */     return this.subcategoriaProductoSeleccionado;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 345:    */   {
/* 346:458 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 350:    */   {
/* 351:466 */     return this.listaSubcategoriaProductos;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 355:    */   {
/* 356:474 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 360:    */   {
/* 361:481 */     HashMap<String, String> filters = new HashMap();
/* 362:482 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 363:483 */     if (this.listaCategoriaProductos == null) {
/* 364:484 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 365:    */     }
/* 366:486 */     return this.listaCategoriaProductos;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 370:    */   {
/* 371:494 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public List<Atributo> getListaAtributo()
/* 375:    */   {
/* 376:503 */     if (this.listaAtributo == null)
/* 377:    */     {
/* 378:504 */       this.listaAtributo = new ArrayList();
/* 379:505 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 380:506 */       filters.put("indicadorProducto", "true");
/* 381:507 */       for (Atributo atributo : this.servicioAtributo.obtenerListaCombo("nombre", true, filters)) {
/* 382:508 */         if (atributo.getTipoAtributo() == TipoAtributo.LISTA) {
/* 383:509 */           this.listaAtributo.add(atributo);
/* 384:    */         }
/* 385:    */       }
/* 386:    */     }
/* 387:513 */     return this.listaAtributo;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<SelectItem> getListaValorAtributo1()
/* 391:    */   {
/* 392:522 */     return this.listaValorAtributo1;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setListaValorAtributo1(List<SelectItem> listaValorAtributo1)
/* 396:    */   {
/* 397:532 */     this.listaValorAtributo1 = listaValorAtributo1;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public List<SelectItem> getListaValorAtributo2()
/* 401:    */   {
/* 402:541 */     return this.listaValorAtributo2;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setListaValorAtributo2(List<SelectItem> listaValorAtributo2)
/* 406:    */   {
/* 407:551 */     this.listaValorAtributo2 = listaValorAtributo2;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public boolean isIndicadorAtributo()
/* 411:    */   {
/* 412:560 */     return this.indicadorAtributo;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setIndicadorAtributo(boolean indicadorAtributo)
/* 416:    */   {
/* 417:570 */     this.indicadorAtributo = indicadorAtributo;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public Atributo getAtributo1()
/* 421:    */   {
/* 422:579 */     return this.atributo1;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setAtributo1(Atributo atributo1)
/* 426:    */   {
/* 427:589 */     this.atributo1 = atributo1;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public String getValorAtributo1()
/* 431:    */   {
/* 432:598 */     return this.valorAtributo1;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setValorAtributo1(String valorAtributo1)
/* 436:    */   {
/* 437:608 */     this.valorAtributo1 = valorAtributo1;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public Atributo getAtributo2()
/* 441:    */   {
/* 442:617 */     return this.atributo2;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setAtributo2(Atributo atributo2)
/* 446:    */   {
/* 447:627 */     this.atributo2 = atributo2;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public String getValorAtributo2()
/* 451:    */   {
/* 452:636 */     return this.valorAtributo2;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setValorAtributo2(String valorAtributo2)
/* 456:    */   {
/* 457:646 */     this.valorAtributo2 = valorAtributo2;
/* 458:    */   }
/* 459:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidoVSInventarioBean
 * JD-Core Version:    0.7.0.1
 */