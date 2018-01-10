/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
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
/*  24:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  25:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  26:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  27:    */ import java.io.IOException;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.math.RoundingMode;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Collections;
/*  32:    */ import java.util.Comparator;
/*  33:    */ import java.util.Date;
/*  34:    */ import java.util.HashMap;
/*  35:    */ import java.util.Iterator;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import java.util.TreeMap;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.model.SelectItem;
/*  43:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  44:    */ import net.sf.jasperreports.engine.JRException;
/*  45:    */ import org.apache.log4j.Logger;
/*  46:    */ 
/*  47:    */ @ManagedBean
/*  48:    */ @ViewScoped
/*  49:    */ public class ReporteStockValoradoBean
/*  50:    */   extends AbstractBaseReportBean
/*  51:    */ {
/*  52:    */   private static final long serialVersionUID = 1L;
/*  53:    */   @EJB
/*  54:    */   private ServicioReporteStockValorado servicioReporteStockValorado;
/*  55:    */   @EJB
/*  56:    */   private ServicioBodega servicioBodega;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioProducto servicioProducto;
/*  59:    */   @EJB
/*  60:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  61:    */   @EJB
/*  62:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  63:    */   @EJB
/*  64:    */   private ServicioAtributo servicioAtributo;
/*  65:    */   @EJB
/*  66:    */   private ServicioValorAtributo servicioValorAtributo;
/*  67:    */   
/*  68:    */   private static enum enumUnidad
/*  69:    */   {
/*  70: 87 */     STOCK,  VENTA,  ALMACENAMIENTO;
/*  71:    */     
/*  72:    */     private enumUnidad() {}
/*  73:    */   }
/*  74:    */   
/*  75: 90 */   private enumUnidad unidad = enumUnidad.STOCK;
/*  76:    */   private List<SelectItem> listaUnidad;
/*  77: 94 */   private Date fechaHasta = new Date();
/*  78:    */   private Bodega bodega;
/*  79:    */   private List<Bodega> listaBodega;
/*  80:    */   private boolean indicadorAtributo;
/*  81:    */   private boolean indicadorSaldoCero;
/*  82:    */   private Atributo atributo1;
/*  83:    */   private String valorAtributo1;
/*  84:    */   private Atributo atributo2;
/*  85:    */   private String valorAtributo2;
/*  86:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  87:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  88:    */   private Producto productoSeleccionado;
/*  89:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  90:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  91:    */   private List<Atributo> listaAtributo;
/*  92:    */   private List<SelectItem> listaValorAtributo1;
/*  93:    */   private List<SelectItem> listaValorAtributo2;
/*  94:    */   private boolean indicadorAgrupado;
/*  95:    */   
/*  96:    */   protected JRDataSource getJRDataSource()
/*  97:    */   {
/*  98:130 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  99:131 */     JRDataSource ds = null;
/* 100:    */     try
/* 101:    */     {
/* 102:134 */       Map<String, ReporteSaldoProducto> hmReporte = new TreeMap();
/* 103:135 */       List<Bodega> listaBodegaAux = new ArrayList();
/* 104:136 */       if (this.bodega == null)
/* 105:    */       {
/* 106:137 */         if (ParametrosSistema.isCosteoPorBodega(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 107:138 */           listaBodegaAux = getListaBodega();
/* 108:    */         } else {
/* 109:140 */           listaBodegaAux.add(null);
/* 110:    */         }
/* 111:    */       }
/* 112:    */       else {
/* 113:143 */         listaBodegaAux.add(this.bodega);
/* 114:    */       }
/* 115:145 */       for (Iterator localIterator1 = listaBodegaAux.iterator(); localIterator1.hasNext();)
/* 116:    */       {
/* 117:145 */         bodega = (Bodega)localIterator1.next();
/* 118:147 */         for (ReporteSaldoProducto rSaldoProducto : this.servicioReporteStockValorado.getReporteSaldoProducto(null, bodega, this.fechaHasta, this.atributo1, this.valorAtributo1, 
/* 119:148 */           AppUtil.getOrganizacion().getId(), this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.atributo2, this.valorAtributo2, 
/* 120:149 */           Boolean.valueOf(this.indicadorSaldoCero)))
/* 121:    */         {
/* 122:151 */           String clave = rSaldoProducto.getIdBodega() + ":" + rSaldoProducto.getNombreProducto() + ":" + rSaldoProducto.getIdProducto();
/* 123:    */           
/* 124:153 */           BigDecimal saldo = this.servicioProducto.getSaldo(rSaldoProducto.getIdProducto().intValue(), rSaldoProducto.getIdBodega().intValue(), this.fechaHasta);
/* 125:154 */           if (saldo.compareTo(BigDecimal.ZERO) != 0)
/* 126:    */           {
/* 127:155 */             rSaldoProducto.setSaldo(saldo);
/* 128:    */             
/* 129:    */ 
/* 130:    */ 
/* 131:159 */             BigDecimal saldoTotal = BigDecimal.ZERO;
/* 132:160 */             BigDecimal costoTotal = BigDecimal.ZERO;
/* 133:161 */             if ((ParametrosSistema.isCosteoPorBodega(AppUtil.getOrganizacion().getId()).booleanValue()) && (bodega != null)) {
/* 134:162 */               saldoTotal = this.servicioProducto.getSaldo(rSaldoProducto.getIdProducto().intValue(), bodega.getIdBodega(), this.fechaHasta);
/* 135:    */             } else {
/* 136:164 */               saldoTotal = this.servicioProducto.getSaldo(rSaldoProducto.getIdProducto().intValue(), 0, this.fechaHasta);
/* 137:    */             }
/* 138:167 */             if (saldoTotal.compareTo(BigDecimal.ZERO) > 0) {
/* 139:170 */               if (ParametrosSistema.isCosteoPorBodega(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 140:171 */                 costoTotal = this.servicioProducto.getCosto(rSaldoProducto.getIdProducto().intValue(), this.fechaHasta, bodega);
/* 141:    */               } else {
/* 142:173 */                 costoTotal = this.servicioProducto.getCosto(rSaldoProducto.getIdProducto().intValue(), this.fechaHasta, null);
/* 143:    */               }
/* 144:    */             }
/* 145:    */             try
/* 146:    */             {
/* 147:185 */               Unidad unidadStock = new Unidad(rSaldoProducto.getIdUnidad().intValue(), rSaldoProducto.getCodigoUnidad(), rSaldoProducto.getNombreUnidad());
/* 148:186 */               Unidad unidadConversion = unidadStock;
/* 149:188 */               if (this.unidad.equals(enumUnidad.ALMACENAMIENTO)) {
/* 150:192 */                 unidadConversion = rSaldoProducto.getIdUnidadAlmacenamiento() == null ? unidadStock : new Unidad(rSaldoProducto.getIdUnidadAlmacenamiento().intValue(), rSaldoProducto.getCodigoUnidadAlmacenamiento(), rSaldoProducto.getNombreUnidadAlmacenamiento());
/* 151:194 */               } else if (this.unidad.equals(enumUnidad.VENTA)) {
/* 152:198 */                 unidadConversion = rSaldoProducto.getIdUnidadVenta() == null ? unidadStock : new Unidad(rSaldoProducto.getIdUnidadVenta().intValue(), rSaldoProducto.getCodigoUnidadVenta(), rSaldoProducto.getNombreUnidadVenta());
/* 153:    */               }
/* 154:202 */               BigDecimal cantidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, rSaldoProducto.getIdProducto().intValue(), rSaldoProducto
/* 155:203 */                 .getSaldo());
/* 156:204 */               saldoTotal = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, rSaldoProducto.getIdProducto().intValue(), saldoTotal);
/* 157:    */               
/* 158:206 */               rSaldoProducto.setSaldo(cantidad);
/* 159:207 */               rSaldoProducto.setCostoUnitario(saldoTotal.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : costoTotal.divide(saldoTotal, 10, RoundingMode.HALF_UP));
/* 160:    */               
/* 161:209 */               rSaldoProducto.setCodigoUnidad(unidadConversion.getCodigo());
/* 162:    */             }
/* 163:    */             catch (ExcepcionAS2 localExcepcionAS2) {}
/* 164:214 */             hmReporte.put(clave, rSaldoProducto);
/* 165:    */           }
/* 166:219 */           if ((this.indicadorSaldoCero) && (saldo.compareTo(BigDecimal.ZERO) == 0)) {
/* 167:220 */             hmReporte.put(clave, rSaldoProducto);
/* 168:    */           }
/* 169:    */         }
/* 170:    */       }
/* 171:    */       Bodega bodega;
/* 172:226 */       Object mapaOrdenado = new TreeMap();
/* 173:227 */       for (ReporteSaldoProducto p : hmReporte.values())
/* 174:    */       {
/* 175:228 */         String clave = "";
/* 176:    */         
/* 177:230 */         clave = p.getNombreBodega() + ":" + p.getNombreCategoriaProducto() + ":" + p.getNombreSubcategoriaProducto() + ":" + p.getNombreProducto();
/* 178:231 */         ReporteSaldoProducto p1 = (ReporteSaldoProducto)((TreeMap)mapaOrdenado).get(clave);
/* 179:232 */         if (p1 == null) {
/* 180:233 */           ((TreeMap)mapaOrdenado).put(clave, p);
/* 181:    */         }
/* 182:    */       }
/* 183:237 */       for (ReporteSaldoProducto p : ((TreeMap)mapaOrdenado).values())
/* 184:    */       {
/* 185:239 */         Object[] datos = new Object[13];
/* 186:240 */         datos[0] = p.getCodigoProducto();
/* 187:241 */         datos[1] = p.getCodigoAlternoProducto();
/* 188:242 */         datos[2] = p.getNombreProducto();
/* 189:243 */         datos[3] = p.getCodigoBodega();
/* 190:244 */         datos[4] = p.getNombreBodega();
/* 191:245 */         datos[5] = p.getSaldo();
/* 192:246 */         datos[6] = p.getCostoUnitario();
/* 193:247 */         datos[7] = p.getCodigoUnidad();
/* 194:248 */         datos[8] = p.getValorAtributo1();
/* 195:249 */         datos[9] = p.getValorAtributo2();
/* 196:250 */         if ((this.atributo1 == null) && (this.atributo2 == null)) {
/* 197:251 */           datos[10] = p.getValorAtributo1();
/* 198:252 */         } else if ((this.atributo1 != null) && (this.atributo2 != null)) {
/* 199:253 */           datos[10] = p.getNombreComercialProducto();
/* 200:    */         } else {
/* 201:255 */           datos[10] = p.getValorAtributo2();
/* 202:    */         }
/* 203:258 */         datos[11] = p.getNombreCategoriaProducto();
/* 204:259 */         datos[12] = p.getNombreSubcategoriaProducto();
/* 205:260 */         listaDatosReporte.add(datos);
/* 206:    */       }
/* 207:264 */       if (this.indicadorAgrupado) {
/* 208:265 */         Collections.sort(listaDatosReporte, new Comparator()
/* 209:    */         {
/* 210:    */           public int compare(Object[] o1, Object[] o2)
/* 211:    */           {
/* 212:268 */             return ((String)o1[4] + (String)o1[11]).compareTo((String)o2[4] + (String)o2[11]);
/* 213:    */           }
/* 214:    */         });
/* 215:    */       }
/* 216:273 */       String[] fields = { "f_codigoProducto", "f_codigoAlternoProducto", "f_nombreProducto", "f_codigoBodega", "f_nombreBodega", "f_saldoProducto", "f_costoProducto", "f_codigoUnidad", "f_valorAtributo1", "f_valorAtributo2", "f_nombreComercialProducto", "f_nombreCategoriaProducto", "f_nombreSubCategoriaProducto" };
/* 217:    */       
/* 218:    */ 
/* 219:    */ 
/* 220:277 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 221:    */     }
/* 222:    */     catch (Exception e)
/* 223:    */     {
/* 224:280 */       e.printStackTrace();
/* 225:    */     }
/* 226:283 */     return ds;
/* 227:    */   }
/* 228:    */   
/* 229:    */   protected String getCompileFileName()
/* 230:    */   {
/* 231:295 */     if (this.indicadorAgrupado) {
/* 232:296 */       return "reporteStockValoradoAgrupado";
/* 233:    */     }
/* 234:298 */     return "reporteStockValorado";
/* 235:    */   }
/* 236:    */   
/* 237:    */   protected Map<String, Object> getReportParameters()
/* 238:    */   {
/* 239:306 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 240:307 */     reportParameters.put("ReportTitle", "Stock Valorado");
/* 241:308 */     reportParameters.put("usuario", "Usuario:");
/* 242:309 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/* 243:310 */     reportParameters.put("pagina", "Pagina:");
/* 244:311 */     reportParameters.put("desde", "Desde:");
/* 245:312 */     reportParameters.put("hasta", "Hasta:");
/* 246:313 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 247:314 */     reportParameters.put("bodega", this.bodega != null ? this.bodega.getNombre() : "");
/* 248:315 */     reportParameters.put("p_categoriaProducto", this.categoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 249:316 */     reportParameters.put("p_subcategoriaProducto", this.subcategoriaProductoSeleccionado != null ? this.subcategoriaProductoSeleccionado.getNombre() : "Todos");
/* 250:    */     
/* 251:318 */     reportParameters.put("p_atributo1", this.atributo1 != null ? this.atributo1.getNombre() : null);
/* 252:319 */     reportParameters.put("p_atributo2", this.atributo2 != null ? this.atributo2.getNombre() : null);
/* 253:320 */     return reportParameters;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String execute()
/* 257:    */   {
/* 258:    */     try
/* 259:    */     {
/* 260:331 */       super.prepareReport();
/* 261:    */     }
/* 262:    */     catch (JRException e)
/* 263:    */     {
/* 264:334 */       LOG.info("Error JRException");
/* 265:335 */       e.printStackTrace();
/* 266:336 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 267:    */     }
/* 268:    */     catch (IOException e)
/* 269:    */     {
/* 270:338 */       LOG.info("Error IOException");
/* 271:339 */       e.printStackTrace();
/* 272:340 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 273:    */     }
/* 274:342 */     return "";
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void validaDatos() {}
/* 278:    */   
/* 279:    */   public void cargarListaSubcategoriaProducto()
/* 280:    */   {
/* 281:350 */     HashMap<String, String> filters = new HashMap();
/* 282:351 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 283:352 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void actualizarAtributo()
/* 287:    */   {
/* 288:356 */     if (!isIndicadorAtributo())
/* 289:    */     {
/* 290:357 */       this.atributo1 = null;
/* 291:358 */       this.atributo2 = null;
/* 292:359 */       this.valorAtributo1 = null;
/* 293:360 */       this.valorAtributo2 = null;
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void cargarListaValorAtributo1()
/* 298:    */   {
/* 299:365 */     if (this.atributo1 != null)
/* 300:    */     {
/* 301:366 */       this.listaValorAtributo1 = new ArrayList();
/* 302:367 */       HashMap<String, String> filters = new HashMap();
/* 303:368 */       filters.put("atributo.idAtributo", "" + this.atributo1.getId());
/* 304:369 */       for (ValorAtributo valorAtributo : this.servicioValorAtributo.obtenerListaCombo("nombre", true, filters)) {
/* 305:370 */         this.listaValorAtributo1.add(new SelectItem(valorAtributo.getNombre(), valorAtributo.getNombre()));
/* 306:    */       }
/* 307:    */     }
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void cargarListaValorAtributo2()
/* 311:    */   {
/* 312:376 */     if (this.atributo2 != null)
/* 313:    */     {
/* 314:377 */       this.listaValorAtributo2 = new ArrayList();
/* 315:378 */       HashMap<String, String> filters = new HashMap();
/* 316:379 */       filters.put("atributo.idAtributo", "" + this.atributo2.getId());
/* 317:380 */       for (ValorAtributo valorAtributo : this.servicioValorAtributo.obtenerListaCombo("nombre", true, filters)) {
/* 318:381 */         this.listaValorAtributo2.add(new SelectItem(valorAtributo.getNombre(), valorAtributo.getNombre()));
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public Date getFechaHasta()
/* 324:    */   {
/* 325:392 */     return this.fechaHasta;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setFechaHasta(Date fechaHasta)
/* 329:    */   {
/* 330:402 */     this.fechaHasta = fechaHasta;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Bodega getBodega()
/* 334:    */   {
/* 335:411 */     return this.bodega;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setBodega(Bodega bodega)
/* 339:    */   {
/* 340:421 */     this.bodega = bodega;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public List<Bodega> getListaBodega()
/* 344:    */   {
/* 345:430 */     if (this.listaBodega == null) {
/* 346:431 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 347:    */     }
/* 348:433 */     return this.listaBodega;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 352:    */   {
/* 353:443 */     this.listaBodega = listaBodega;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public enumUnidad getUnidad()
/* 357:    */   {
/* 358:452 */     return this.unidad;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setUnidad(enumUnidad unidad)
/* 362:    */   {
/* 363:462 */     this.unidad = unidad;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<SelectItem> getListaUnidad()
/* 367:    */   {
/* 368:471 */     if (this.listaUnidad == null)
/* 369:    */     {
/* 370:472 */       this.listaUnidad = new ArrayList();
/* 371:473 */       for (enumUnidad unidad : enumUnidad.values())
/* 372:    */       {
/* 373:474 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 374:475 */         this.listaUnidad.add(item);
/* 375:    */       }
/* 376:    */     }
/* 377:478 */     return this.listaUnidad;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 381:    */   {
/* 382:488 */     this.listaUnidad = listaUnidad;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public Producto getProductoSeleccionado()
/* 386:    */   {
/* 387:495 */     return this.productoSeleccionado;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setProductoSeleccionado(Producto productoSeleccionado)
/* 391:    */   {
/* 392:503 */     this.productoSeleccionado = productoSeleccionado;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 396:    */   {
/* 397:510 */     if (this.categoriaProductoSeleccionado == null) {
/* 398:511 */       this.categoriaProductoSeleccionado = new CategoriaProducto();
/* 399:    */     }
/* 400:513 */     return this.categoriaProductoSeleccionado;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 404:    */   {
/* 405:521 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 409:    */   {
/* 410:528 */     return this.subcategoriaProductoSeleccionado;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 414:    */   {
/* 415:536 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 419:    */   {
/* 420:544 */     return this.listaSubcategoriaProductos;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 424:    */   {
/* 425:552 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 429:    */   {
/* 430:559 */     HashMap<String, String> filters = new HashMap();
/* 431:560 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 432:561 */     if (this.listaCategoriaProductos == null) {
/* 433:562 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 434:    */     }
/* 435:564 */     return this.listaCategoriaProductos;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 439:    */   {
/* 440:572 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public List<Atributo> getListaAtributo()
/* 444:    */   {
/* 445:581 */     if (this.listaAtributo == null)
/* 446:    */     {
/* 447:582 */       this.listaAtributo = new ArrayList();
/* 448:583 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 449:584 */       filters.put("indicadorProducto", "true");
/* 450:585 */       for (Atributo atributo : this.servicioAtributo.obtenerListaCombo("nombre", true, filters)) {
/* 451:586 */         if (atributo.getTipoAtributo() == TipoAtributo.LISTA) {
/* 452:587 */           this.listaAtributo.add(atributo);
/* 453:    */         }
/* 454:    */       }
/* 455:    */     }
/* 456:591 */     return this.listaAtributo;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public List<SelectItem> getListaValorAtributo1()
/* 460:    */   {
/* 461:600 */     return this.listaValorAtributo1;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setListaValorAtributo1(List<SelectItem> listaValorAtributo1)
/* 465:    */   {
/* 466:610 */     this.listaValorAtributo1 = listaValorAtributo1;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public List<SelectItem> getListaValorAtributo2()
/* 470:    */   {
/* 471:619 */     return this.listaValorAtributo2;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setListaValorAtributo2(List<SelectItem> listaValorAtributo2)
/* 475:    */   {
/* 476:629 */     this.listaValorAtributo2 = listaValorAtributo2;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public boolean isIndicadorAtributo()
/* 480:    */   {
/* 481:638 */     return this.indicadorAtributo;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setIndicadorAtributo(boolean indicadorAtributo)
/* 485:    */   {
/* 486:648 */     this.indicadorAtributo = indicadorAtributo;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public Atributo getAtributo1()
/* 490:    */   {
/* 491:657 */     return this.atributo1;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setAtributo1(Atributo atributo1)
/* 495:    */   {
/* 496:667 */     this.atributo1 = atributo1;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public String getValorAtributo1()
/* 500:    */   {
/* 501:676 */     return this.valorAtributo1;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void setValorAtributo1(String valorAtributo1)
/* 505:    */   {
/* 506:686 */     this.valorAtributo1 = valorAtributo1;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public Atributo getAtributo2()
/* 510:    */   {
/* 511:695 */     return this.atributo2;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setAtributo2(Atributo atributo2)
/* 515:    */   {
/* 516:705 */     this.atributo2 = atributo2;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public String getValorAtributo2()
/* 520:    */   {
/* 521:714 */     return this.valorAtributo2;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void setValorAtributo2(String valorAtributo2)
/* 525:    */   {
/* 526:724 */     this.valorAtributo2 = valorAtributo2;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public boolean isIndicadorSaldoCero()
/* 530:    */   {
/* 531:728 */     return this.indicadorSaldoCero;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setIndicadorSaldoCero(boolean indicadorSaldoCero)
/* 535:    */   {
/* 536:732 */     this.indicadorSaldoCero = indicadorSaldoCero;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public boolean isIndicadorAgrupado()
/* 540:    */   {
/* 541:736 */     return this.indicadorAgrupado;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setIndicadorAgrupado(boolean indicadorAgrupado)
/* 545:    */   {
/* 546:740 */     this.indicadorAgrupado = indicadorAgrupado;
/* 547:    */   }
/* 548:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteStockValoradoBean
 * JD-Core Version:    0.7.0.1
 */