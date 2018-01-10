/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.FactorConversion;
/*   4:    */ import com.asinfo.as2.clases.ReporteInventarioProducto;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.entities.Unidad;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  21:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  22:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteInventarioProducto;
/*  23:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValorado;
/*  24:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  25:    */ import com.asinfo.as2.util.AppUtil;
/*  26:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  27:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  28:    */ import java.io.IOException;
/*  29:    */ import java.io.PrintStream;
/*  30:    */ import java.math.BigDecimal;
/*  31:    */ import java.math.RoundingMode;
/*  32:    */ import java.util.ArrayList;
/*  33:    */ import java.util.Calendar;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import java.util.TreeMap;
/*  39:    */ import javax.annotation.PostConstruct;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.faces.bean.ManagedBean;
/*  42:    */ import javax.faces.bean.ViewScoped;
/*  43:    */ import javax.faces.model.SelectItem;
/*  44:    */ import javax.persistence.Temporal;
/*  45:    */ import javax.persistence.TemporalType;
/*  46:    */ import javax.validation.constraints.NotNull;
/*  47:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  48:    */ import net.sf.jasperreports.engine.JRException;
/*  49:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  50:    */ import org.apache.log4j.Logger;
/*  51:    */ 
/*  52:    */ @ManagedBean
/*  53:    */ @ViewScoped
/*  54:    */ public class ReporteInventarioBean
/*  55:    */   extends AbstractBaseReportBean
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = 1L;
/*  58:    */   @EJB
/*  59:    */   private ServicioReporteInventarioProducto servicioReporteInventarioProducto;
/*  60:    */   @EJB
/*  61:    */   private ServicioBodega servicioBodega;
/*  62:    */   @EJB
/*  63:    */   private ServicioProducto servicioProducto;
/*  64:    */   @EJB
/*  65:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  66:    */   @EJB
/*  67:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  68:    */   @EJB
/*  69:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  70:    */   @EJB
/*  71:    */   private ServicioReporteStockValorado servicioReporteStockValorado;
/*  72:    */   @EJB
/*  73:    */   private ServicioGenerico<OrganizacionConfiguracion> servicioOrganizacionConfiguracion;
/*  74:    */   private Bodega bodega;
/*  75:    */   private SubcategoriaProducto subcategoriaProducto;
/*  76:    */   private CategoriaProducto categoriaProducto;
/*  77:    */   @Temporal(TemporalType.DATE)
/*  78:    */   @NotNull
/*  79:103 */   private Date fechaDesde = new Date();
/*  80:    */   @Temporal(TemporalType.DATE)
/*  81:    */   @NotNull
/*  82:107 */   private Date fechaHasta = new Date();
/*  83:    */   private List<Producto> listaProducto;
/*  84:    */   private List<Bodega> listaBodega;
/*  85:116 */   private List<ReporteInventarioProducto> listaReporteInventarioProducto = new ArrayList();
/*  86:    */   private HashMap<Integer, Producto> hmProducto;
/*  87:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  88:    */   
/*  89:    */   private static enum enumUnidad
/*  90:    */   {
/*  91:122 */     STOCK,  VENTA,  ALMACENAMIENTO;
/*  92:    */     
/*  93:    */     private enumUnidad() {}
/*  94:    */   }
/*  95:    */   
/*  96:125 */   private enumUnidad unidad = enumUnidad.STOCK;
/*  97:    */   private List<SelectItem> listaUnidad;
/*  98:    */   
/*  99:    */   protected JRDataSource getJRDataSource()
/* 100:    */   {
/* 101:136 */     JRDataSource ds = null;
/* 102:    */     
/* 103:138 */     this.listaReporteInventarioProducto = LReporteInventarioProducto(this.bodega);
/* 104:    */     
/* 105:140 */     ds = new JRBeanCollectionDataSource(this.listaReporteInventarioProducto);
/* 106:141 */     return ds;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<ReporteInventarioProducto> LReporteInventarioProducto(Bodega b)
/* 110:    */   {
/* 111:150 */     List<ReporteInventarioProducto> lReporteInventarioProducto = new ArrayList();
/* 112:    */     try
/* 113:    */     {
/* 114:154 */       validaDatos();
/* 115:155 */       hmReporte = new TreeMap();
/* 116:157 */       if (!getListaProducto().isEmpty())
/* 117:    */       {
/* 118:159 */         lReporteInventarioProducto = this.servicioReporteInventarioProducto.obtenerMovimientosInventarioProducto(AppUtil.getOrganizacion().getId(), this.listaProducto, b, this.fechaDesde, this.fechaHasta, 
/* 119:160 */           getUnidad().ordinal(), null, this.listaBodega, 
/* 120:161 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 121:    */         
/* 122:163 */         saldoInicial = BigDecimal.ZERO;
/* 123:164 */         costoInicial = BigDecimal.ZERO;
/* 124:165 */         idProducto = -1;
/* 125:167 */         for (ReporteInventarioProducto reporteInventarioProducto : lReporteInventarioProducto)
/* 126:    */         {
/* 127:170 */           String clave = "" + reporteInventarioProducto.getIdProducto();
/* 128:174 */           if (idProducto != reporteInventarioProducto.getIdProducto())
/* 129:    */           {
/* 130:175 */             saldoInicial = BigDecimal.ZERO;
/* 131:176 */             costoInicial = BigDecimal.ZERO;
/* 132:177 */             idProducto = reporteInventarioProducto.getIdProducto();
/* 133:    */           }
/* 134:180 */           Producto producto = (Producto)this.hmProducto.get(Integer.valueOf(reporteInventarioProducto.getIdProducto()));
/* 135:181 */           reporteInventarioProducto.setProducto(producto);
/* 136:    */           
/* 137:183 */           FactorConversion factorConversion = null;
/* 138:184 */           String strMensaje = "";
/* 139:186 */           if (this.unidad.equals(enumUnidad.VENTA))
/* 140:    */           {
/* 141:188 */             factorConversion = this.servicioUnidadConversion.obtenerFactorConversion(producto.getId(), producto
/* 142:189 */               .getSubcategoriaProducto().getIdSubcategoriaProducto(), producto.getUnidad().getId(), producto
/* 143:190 */               .getUnidadVenta().getId());
/* 144:    */             
/* 145:192 */             strMensaje = producto.getCodigo() + "-" + producto.getNombre() + " (" + producto.getUnidad().getNombre() + "-" + producto.getUnidadVenta().getNombre() + ")";
/* 146:    */           }
/* 147:194 */           else if (this.unidad.equals(enumUnidad.ALMACENAMIENTO))
/* 148:    */           {
/* 149:196 */             factorConversion = this.servicioUnidadConversion.obtenerFactorConversion(producto.getId(), producto
/* 150:197 */               .getSubcategoriaProducto().getIdSubcategoriaProducto(), producto.getUnidad().getId(), producto
/* 151:198 */               .getUnidadAlmacenamiento().getId());
/* 152:    */             
/* 153:200 */             strMensaje = producto.getCodigo() + "-" + producto.getNombre() + " (" + producto.getUnidad().getNombre() + "-" + producto.getUnidadAlmacenamiento().getNombre() + ")";
/* 154:    */           }
/* 155:204 */           if (!this.unidad.equals(enumUnidad.STOCK)) {
/* 156:205 */             if (factorConversion != null)
/* 157:    */             {
/* 158:206 */               if (factorConversion.isIndicadorInverso()) {
/* 159:207 */                 reporteInventarioProducto.setCantidad(reporteInventarioProducto
/* 160:208 */                   .getCantidad().divide(factorConversion.getFactor(), 4, RoundingMode.HALF_UP));
/* 161:    */               } else {
/* 162:210 */                 reporteInventarioProducto.setCantidad(reporteInventarioProducto.getCantidad().multiply(factorConversion.getFactor())
/* 163:211 */                   .setScale(4, RoundingMode.HALF_UP));
/* 164:    */               }
/* 165:    */             }
/* 166:    */             else {
/* 167:214 */               throw new ExcepcionAS2Inventario("msg_error_unidad_conversion", strMensaje);
/* 168:    */             }
/* 169:    */           }
/* 170:219 */           saldoInicial = saldoInicial.add(reporteInventarioProducto.getCantidad().multiply(new BigDecimal(reporteInventarioProducto.getOperacion().intValue())));
/* 171:    */           
/* 172:221 */           costoInicial = costoInicial.add(reporteInventarioProducto.getCosto().multiply(new BigDecimal(reporteInventarioProducto.getOperacion().intValue())));
/* 173:    */           
/* 174:223 */           reporteInventarioProducto.setCantidadTotal(saldoInicial.setScale(4, RoundingMode.HALF_UP));
/* 175:224 */           reporteInventarioProducto.setCostoTotal(costoInicial.setScale(4, RoundingMode.HALF_UP));
/* 176:    */           
/* 177:226 */           hmReporte.put(clave, Integer.valueOf(producto.getIdProducto()));
/* 178:    */         }
/* 179:    */       }
/* 180:    */     }
/* 181:    */     catch (ExcepcionAS2 e)
/* 182:    */     {
/* 183:    */       Map<String, Integer> hmReporte;
/* 184:    */       BigDecimal saldoInicial;
/* 185:    */       BigDecimal costoInicial;
/* 186:    */       int idProducto;
/* 187:232 */       LOG.info("Error " + e);
/* 188:233 */       e.printStackTrace();
/* 189:    */     }
/* 190:236 */     return lReporteInventarioProducto;
/* 191:    */   }
/* 192:    */   
/* 193:    */   @PostConstruct
/* 194:    */   public void init()
/* 195:    */   {
/* 196:241 */     Calendar calfechaDesde = Calendar.getInstance();
/* 197:242 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 198:243 */     this.fechaDesde = calfechaDesde.getTime();
/* 199:244 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected String getCompileFileName()
/* 203:    */   {
/* 204:254 */     return "reporteInventario";
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected Map<String, Object> getReportParameters()
/* 208:    */   {
/* 209:265 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 210:266 */     reportParameters.put("ReportTitle", "Inventario Producto");
/* 211:267 */     reportParameters.put("fechaDesde", getFechaDesde());
/* 212:268 */     reportParameters.put("fechaHasta", getFechaHasta());
/* 213:269 */     reportParameters.put("suucategoriaProducto", this.subcategoriaProducto != null ? this.subcategoriaProducto.getNombre() : "Todos");
/* 214:270 */     reportParameters.put("p_categoriaProducto", this.categoriaProducto != null ? this.categoriaProducto.getNombre() : "Todos");
/* 215:271 */     reportParameters.put("p_bodega", this.bodega != null ? this.bodega.getNombre() : "Todos");
/* 216:272 */     reportParameters.put("atributo1", getOrganizacionConfiguracion().getAtributo1() != null ? getOrganizacionConfiguracion().getAtributo1().getNombre() : "");
/* 217:273 */     reportParameters.put("atributo2", getOrganizacionConfiguracion().getAtributo2() != null ? getOrganizacionConfiguracion().getAtributo2().getNombre() : "");
/* 218:274 */     reportParameters.put("atributo3", getOrganizacionConfiguracion().getAtributo3() != null ? getOrganizacionConfiguracion().getAtributo3().getNombre() : "");
/* 219:275 */     reportParameters.put("atributo4", getOrganizacionConfiguracion().getAtributo4() != null ? getOrganizacionConfiguracion().getAtributo4().getNombre() : "");
/* 220:276 */     reportParameters.put("atributo5", getOrganizacionConfiguracion().getAtributo5() != null ? getOrganizacionConfiguracion().getAtributo5().getNombre() : "");
/* 221:277 */     reportParameters.put("atributo6", getOrganizacionConfiguracion().getAtributo6() != null ? getOrganizacionConfiguracion().getAtributo6().getNombre() : "");
/* 222:278 */     reportParameters.put("atributo7", getOrganizacionConfiguracion().getAtributo7() != null ? getOrganizacionConfiguracion().getAtributo7().getNombre() : "");
/* 223:279 */     reportParameters.put("atributo8", getOrganizacionConfiguracion().getAtributo8() != null ? getOrganizacionConfiguracion().getAtributo8().getNombre() : "");
/* 224:280 */     reportParameters.put("atributo9", getOrganizacionConfiguracion().getAtributo9() != null ? getOrganizacionConfiguracion().getAtributo9().getNombre() : "");
/* 225:281 */     reportParameters.put("atributo10", getOrganizacionConfiguracion().getAtributo10() != null ? getOrganizacionConfiguracion().getAtributo10().getNombre() : "");
/* 226:282 */     return reportParameters;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String execute()
/* 230:    */   {
/* 231:    */     try
/* 232:    */     {
/* 233:292 */       super.prepareReport();
/* 234:    */     }
/* 235:    */     catch (JRException e)
/* 236:    */     {
/* 237:294 */       LOG.info("Error JRException");
/* 238:295 */       e.printStackTrace();
/* 239:296 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 240:    */     }
/* 241:    */     catch (IOException e)
/* 242:    */     {
/* 243:298 */       LOG.info("Error IOException");
/* 244:299 */       e.printStackTrace();
/* 245:300 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 246:    */     }
/* 247:302 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void validaDatos()
/* 251:    */   {
/* 252:307 */     HashMap<String, String> filtros = new HashMap();
/* 253:    */     
/* 254:309 */     filtros.put("tipoProducto", "" + TipoProducto.ARTICULO);
/* 255:310 */     if (getCategoriaProducto() != null) {
/* 256:311 */       filtros.put("subcategoriaProducto.categoriaProducto.idCategoriaProducto", "" + getCategoriaProducto().getId());
/* 257:    */     }
/* 258:314 */     if (getSubcategoriaProducto().getId() > 0) {
/* 259:315 */       filtros.put("subcategoriaProducto.idSubcategoriaProducto", "" + getSubcategoriaProducto().getId());
/* 260:    */     }
/* 261:318 */     this.listaProducto = this.servicioProducto.obtenerListaCombo("nombre", true, filtros);
/* 262:319 */     this.hmProducto = new HashMap();
/* 263:320 */     for (Producto producto : this.listaProducto)
/* 264:    */     {
/* 265:321 */       System.out.println("------" + producto.getNombre());
/* 266:322 */       this.hmProducto.put(Integer.valueOf(producto.getId()), producto);
/* 267:    */     }
/* 268:325 */     if (this.fechaDesde == null) {
/* 269:326 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 270:    */     }
/* 271:328 */     if (this.fechaHasta == null) {
/* 272:329 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 277:    */   {
/* 278:334 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 279:    */     
/* 280:336 */     HashMap<String, String> filters = new HashMap();
/* 281:337 */     filters.put("nombre", "%" + consulta.trim());
/* 282:    */     
/* 283:339 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 284:    */     
/* 285:341 */     return lista;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 289:    */   {
/* 290:345 */     List<CategoriaProducto> lista = new ArrayList();
/* 291:    */     
/* 292:347 */     HashMap<String, String> filters = new HashMap();
/* 293:348 */     filters.put("nombre", "%" + consulta.trim());
/* 294:    */     
/* 295:350 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 296:    */     
/* 297:352 */     return lista;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Date getFechaDesde()
/* 301:    */   {
/* 302:361 */     return this.fechaDesde;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setFechaDesde(Date fechaDesde)
/* 306:    */   {
/* 307:371 */     this.fechaDesde = fechaDesde;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Date getFechaHasta()
/* 311:    */   {
/* 312:380 */     return this.fechaHasta;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setFechaHasta(Date fechaHasta)
/* 316:    */   {
/* 317:390 */     this.fechaHasta = fechaHasta;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public Bodega getBodega()
/* 321:    */   {
/* 322:399 */     return this.bodega;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setBodega(Bodega bodega)
/* 326:    */   {
/* 327:409 */     this.bodega = bodega;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 331:    */   {
/* 332:418 */     if (this.subcategoriaProducto == null) {
/* 333:419 */       this.subcategoriaProducto = new SubcategoriaProducto();
/* 334:    */     }
/* 335:421 */     return this.subcategoriaProducto;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 339:    */   {
/* 340:431 */     this.subcategoriaProducto = subcategoriaProducto;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public List<Producto> getListaProducto()
/* 344:    */   {
/* 345:440 */     if (this.listaProducto == null) {
/* 346:441 */       this.listaProducto = new ArrayList();
/* 347:    */     }
/* 348:443 */     return this.listaProducto;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setListaProducto(List<Producto> listaProducto)
/* 352:    */   {
/* 353:453 */     this.listaProducto = listaProducto;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<Bodega> getListaBodega()
/* 357:    */   {
/* 358:462 */     if (this.listaBodega == null) {
/* 359:463 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/* 360:    */     }
/* 361:465 */     return this.listaBodega;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 365:    */   {
/* 366:475 */     this.listaBodega = listaBodega;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public List<ReporteInventarioProducto> getListaReporteInventarioProducto()
/* 370:    */   {
/* 371:484 */     return this.listaReporteInventarioProducto;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setListaReporteInventarioProducto(List<ReporteInventarioProducto> listaReporteInventarioProducto)
/* 375:    */   {
/* 376:494 */     this.listaReporteInventarioProducto = listaReporteInventarioProducto;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public enumUnidad getUnidad()
/* 380:    */   {
/* 381:503 */     return this.unidad;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setUnidad(enumUnidad unidad)
/* 385:    */   {
/* 386:513 */     this.unidad = unidad;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public List<SelectItem> getListaUnidad()
/* 390:    */   {
/* 391:522 */     if (this.listaUnidad == null)
/* 392:    */     {
/* 393:523 */       this.listaUnidad = new ArrayList();
/* 394:524 */       for (enumUnidad unidad : enumUnidad.values())
/* 395:    */       {
/* 396:525 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 397:526 */         this.listaUnidad.add(item);
/* 398:    */       }
/* 399:    */     }
/* 400:529 */     return this.listaUnidad;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 404:    */   {
/* 405:539 */     this.listaUnidad = listaUnidad;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public HashMap<Integer, Producto> getHmProducto()
/* 409:    */   {
/* 410:548 */     return this.hmProducto;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setHmProducto(HashMap<Integer, Producto> hmProducto)
/* 414:    */   {
/* 415:558 */     this.hmProducto = hmProducto;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public CategoriaProducto getCategoriaProducto()
/* 419:    */   {
/* 420:562 */     return this.categoriaProducto;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 424:    */   {
/* 425:566 */     this.categoriaProducto = categoriaProducto;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 429:    */   {
/* 430:570 */     if (this.organizacionConfiguracion == null)
/* 431:    */     {
/* 432:571 */       int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 433:572 */       List<String> listaCampos = new ArrayList();
/* 434:573 */       for (int i = 1; i <= numeroAtributos; i++) {
/* 435:574 */         listaCampos.add("atributo" + i);
/* 436:    */       }
/* 437:576 */       this.organizacionConfiguracion = ((OrganizacionConfiguracion)this.servicioOrganizacionConfiguracion.cargarDetalle(OrganizacionConfiguracion.class, 
/* 438:577 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getIdOrganizacionConfiguracion(), listaCampos));
/* 439:    */     }
/* 440:579 */     return this.organizacionConfiguracion;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 444:    */   {
/* 445:583 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 446:    */   }
/* 447:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteInventarioBean
 * JD-Core Version:    0.7.0.1
 */