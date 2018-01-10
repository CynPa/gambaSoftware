/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  11:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  14:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  15:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  20:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina;
/*  21:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  25:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  26:    */ import java.io.IOException;
/*  27:    */ import java.util.ArrayList;
/*  28:    */ import java.util.Date;
/*  29:    */ import java.util.HashMap;
/*  30:    */ import java.util.List;
/*  31:    */ import java.util.Map;
/*  32:    */ import javax.annotation.PostConstruct;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.faces.bean.ManagedBean;
/*  35:    */ import javax.faces.bean.ViewScoped;
/*  36:    */ import javax.faces.model.SelectItem;
/*  37:    */ import javax.persistence.Temporal;
/*  38:    */ import javax.persistence.TemporalType;
/*  39:    */ import javax.validation.constraints.NotNull;
/*  40:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  41:    */ import net.sf.jasperreports.engine.JRException;
/*  42:    */ import org.apache.log4j.Logger;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class ReporteProduccionBean
/*  47:    */   extends AbstractBaseReportBean
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = 1L;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioReporteFabricacion servicioReporteFabricacion;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioCategoriaProducto servicioCategoriaProducto;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioBodega servicioBodega;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioMaquina servicioMaquina;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioPersonaResponsable servicioPersonaResponsable;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioAtributo servicioAtributo;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  66:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  67:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  68:    */   private Producto productoSeleccionado;
/*  69:    */   private Bodega bodega;
/*  70:    */   private Atributo atributoOrdenFabricacion;
/*  71:    */   private Atributo atributo1;
/*  72:    */   private Atributo atributo2;
/*  73:    */   private Atributo atributo3;
/*  74:    */   private Atributo atributo4;
/*  75:    */   private Atributo atributo5;
/*  76:    */   private Atributo atributo6;
/*  77:    */   private Atributo atributo7;
/*  78:    */   private Atributo atributo8;
/*  79:    */   private Atributo atributo9;
/*  80:    */   private ValorAtributo valorAtributoOF;
/*  81:    */   private ValorAtributo valorAtributo1;
/*  82:    */   private ValorAtributo valorAtributo2;
/*  83:    */   private ValorAtributo valorAtributo3;
/*  84:    */   private ValorAtributo valorAtributo4;
/*  85:    */   private ValorAtributo valorAtributo5;
/*  86:    */   private ValorAtributo valorAtributo6;
/*  87:    */   private ValorAtributo valorAtributo7;
/*  88:    */   private ValorAtributo valorAtributo8;
/*  89:    */   private ValorAtributo valorAtributo9;
/*  90:    */   private PersonaResponsable personaResponsable;
/*  91:    */   private Maquina maquina;
/*  92:    */   
/*  93:    */   static enum TIPO_REPORTE
/*  94:    */   {
/*  95: 66 */     GENERAL("General"),  POR_ATRIBUTO("Por Atributo"),  POR_MAQUINA("Por Maquina"),  POR_RESPONSABLE("Por Responsable");
/*  96:    */     
/*  97:    */     private String nombre;
/*  98:    */     
/*  99:    */     private TIPO_REPORTE(String nombre)
/* 100:    */     {
/* 101: 76 */       this.nombre = nombre;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public String getNombre()
/* 105:    */     {
/* 106: 85 */       return this.nombre;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public void setNombre(String nombre)
/* 110:    */     {
/* 111: 94 */       this.nombre = nombre;
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:149 */   private TIPO_REPORTE tipoReporte = TIPO_REPORTE.GENERAL;
/* 116:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/* 117:    */   private List<CategoriaProducto> listaCategoriaProductos;
/* 118:    */   private List<Bodega> listaBodega;
/* 119:    */   private List<Maquina> listaMaquina;
/* 120:    */   private List<PersonaResponsable> listaPersonaResponsable;
/* 121:    */   @Temporal(TemporalType.DATE)
/* 122:    */   @NotNull
/* 123:162 */   private Date fechaDesde = FuncionesUtiles.getFechaInicioMes(new Date());
/* 124:    */   @Temporal(TemporalType.DATE)
/* 125:    */   @NotNull
/* 126:164 */   private Date fechaHasta = new Date();
/* 127:    */   private boolean agrupado;
/* 128:    */   
/* 129:    */   @PostConstruct
/* 130:    */   public void init()
/* 131:    */   {
/* 132:173 */     OrganizacionConfiguracion organizacionConfiguracion = this.servicioOrganizacion.cargarDetalle(AppUtil.getOrganizacion().getId()).getOrganizacionConfiguracion();
/* 133:175 */     if (organizacionConfiguracion.getAtributo1() != null) {
/* 134:176 */       this.atributoOrdenFabricacion = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo1().getId());
/* 135:    */     }
/* 136:178 */     if (organizacionConfiguracion.getAtributo2() != null) {
/* 137:179 */       this.atributo1 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo2().getId());
/* 138:    */     }
/* 139:181 */     if (organizacionConfiguracion.getAtributo3() != null) {
/* 140:182 */       this.atributo2 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo3().getId());
/* 141:    */     }
/* 142:184 */     if (organizacionConfiguracion.getAtributo4() != null) {
/* 143:185 */       this.atributo3 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo4().getId());
/* 144:    */     }
/* 145:187 */     if (organizacionConfiguracion.getAtributo5() != null) {
/* 146:188 */       this.atributo4 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo5().getId());
/* 147:    */     }
/* 148:190 */     if (organizacionConfiguracion.getAtributo6() != null) {
/* 149:191 */       this.atributo5 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo6().getId());
/* 150:    */     }
/* 151:193 */     if (organizacionConfiguracion.getAtributo7() != null) {
/* 152:194 */       this.atributo6 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo7().getId());
/* 153:    */     }
/* 154:196 */     if (organizacionConfiguracion.getAtributo8() != null) {
/* 155:197 */       this.atributo7 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo8().getId());
/* 156:    */     }
/* 157:199 */     if (organizacionConfiguracion.getAtributo9() != null) {
/* 158:200 */       this.atributo8 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo9().getId());
/* 159:    */     }
/* 160:202 */     if (organizacionConfiguracion.getAtributo10() != null) {
/* 161:203 */       this.atributo9 = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo10().getId());
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected JRDataSource getJRDataSource()
/* 166:    */   {
/* 167:215 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 168:216 */     JRDataSource ds = null;
/* 169:217 */     String[] fields = null;
/* 170:218 */     int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 171:    */     try
/* 172:    */     {
/* 173:222 */       if (TIPO_REPORTE.GENERAL.equals(this.tipoReporte))
/* 174:    */       {
/* 175:223 */         listaDatosReporte = this.servicioReporteFabricacion.getReporteProduccion(this.fechaDesde, this.fechaHasta, this.bodega, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, null, null, null, false, this.tipoReporte
/* 176:224 */           .ordinal(), 
/* 177:225 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 178:    */         
/* 179:227 */         fields = new String[] { "f_nombreProducto", "f_unidad", "f_presentacionProducto", "f_cantidad", "f_costoMateriales", "f_costosIndirectos", "f_costoDepreciaciones", "f_costoManoDeObra", "f_productoCodigo" };
/* 180:    */       }
/* 181:    */       else
/* 182:    */       {
/* 183:230 */         listaDatosReporte = this.servicioReporteFabricacion.getReporteProduccion(this.fechaDesde, this.fechaHasta, this.bodega, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.maquina, this.personaResponsable, 
/* 184:231 */           getListaVariables(), this.agrupado, this.tipoReporte.ordinal(), 
/* 185:232 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 186:233 */         if (this.agrupado)
/* 187:    */         {
/* 188:234 */           fields = new String[] { "f_nombreProducto", "f_unidad", "f_valor", "f_group", "f_cantidad" };
/* 189:    */         }
/* 190:    */         else
/* 191:    */         {
/* 192:237 */           List<String> fieldList = new ArrayList();
/* 193:238 */           fieldList.add("f_nombreProducto");
/* 194:239 */           fieldList.add("f_unidad");
/* 195:240 */           fieldList.add("f_group");
/* 196:241 */           fieldList.add("f_valor");
/* 197:242 */           fieldList.add("f_fecha");
/* 198:243 */           fieldList.add("f_numeroMovimiento");
/* 199:244 */           fieldList.add("f_numeroOrdenFabricacion");
/* 200:246 */           if (numeroAtributos > 0)
/* 201:    */           {
/* 202:247 */             for (int i = 1; i <= numeroAtributos - 1; i++)
/* 203:    */             {
/* 204:248 */               fieldList.add("f_atributo" + i);
/* 205:249 */               fieldList.add("f_valorAtributo" + i);
/* 206:    */             }
/* 207:251 */             fieldList.add("f_atributoOrdenFabricacion");
/* 208:252 */             fieldList.add("f_valorAtributoOrdenFabricacion");
/* 209:    */           }
/* 210:256 */           fields = (String[])fieldList.toArray(new String[fieldList.size()]);
/* 211:    */         }
/* 212:    */       }
/* 213:259 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 214:    */     }
/* 215:    */     catch (Exception e)
/* 216:    */     {
/* 217:262 */       e.printStackTrace();
/* 218:    */     }
/* 219:264 */     return ds;
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected String getCompileFileName()
/* 223:    */   {
/* 224:274 */     String compileFileName = "reporteProduccionAgrupado";
/* 225:275 */     if (!this.agrupado) {
/* 226:276 */       switch (1.$SwitchMap$com$asinfo$as2$produccion$reportes$controller$ReporteProduccionBean$TIPO_REPORTE[this.tipoReporte.ordinal()])
/* 227:    */       {
/* 228:    */       case 1: 
/* 229:278 */         compileFileName = "reporteProduccion";
/* 230:279 */         break;
/* 231:    */       case 2: 
/* 232:281 */         compileFileName = "reporteProduccionDetalladoAtributo";
/* 233:282 */         break;
/* 234:    */       case 3: 
/* 235:284 */         compileFileName = "reporteProduccionDetallado";
/* 236:285 */         break;
/* 237:    */       case 4: 
/* 238:287 */         compileFileName = "reporteProduccionDetallado";
/* 239:288 */         break;
/* 240:    */       }
/* 241:    */     }
/* 242:293 */     return compileFileName;
/* 243:    */   }
/* 244:    */   
/* 245:    */   protected Map<String, Object> getReportParameters()
/* 246:    */   {
/* 247:303 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 248:304 */     String titleAgrupado = this.agrupado ? "Agrupado" : "";
/* 249:305 */     String title = "";
/* 250:306 */     switch (1.$SwitchMap$com$asinfo$as2$produccion$reportes$controller$ReporteProduccionBean$TIPO_REPORTE[this.tipoReporte.ordinal()])
/* 251:    */     {
/* 252:    */     case 1: 
/* 253:308 */       title = "Parte Produccion";
/* 254:309 */       break;
/* 255:    */     case 2: 
/* 256:311 */       title = "Reporte Produccion por atributo " + titleAgrupado;
/* 257:312 */       break;
/* 258:    */     case 3: 
/* 259:314 */       title = "Reporte Produccion por maquina " + titleAgrupado;
/* 260:315 */       break;
/* 261:    */     case 4: 
/* 262:317 */       title = "Reporte Produccion por responsable " + titleAgrupado;
/* 263:318 */       break;
/* 264:    */     }
/* 265:322 */     reportParameters.put("ReportTitle", title);
/* 266:323 */     reportParameters.put("fechaDesde", getFechaDesde());
/* 267:324 */     reportParameters.put("fechaHasta", getFechaHasta());
/* 268:325 */     if (this.bodega != null) {
/* 269:326 */       reportParameters.put("bodega", this.bodega.getNombre());
/* 270:    */     }
/* 271:328 */     if (this.categoriaProductoSeleccionado != null) {
/* 272:329 */       reportParameters.put("categoriaProducto", this.categoriaProductoSeleccionado.getNombre());
/* 273:    */     }
/* 274:331 */     if (this.subcategoriaProductoSeleccionado != null) {
/* 275:332 */       reportParameters.put("subcategoriaProducto", this.subcategoriaProductoSeleccionado.getNombre());
/* 276:    */     }
/* 277:334 */     if (this.personaResponsable != null) {
/* 278:335 */       reportParameters.put("personaResponsable", this.personaResponsable.getApellidos() + " " + this.personaResponsable.getNombres());
/* 279:    */     }
/* 280:337 */     if (this.maquina != null) {
/* 281:338 */       reportParameters.put("maquina", this.maquina.getNombre());
/* 282:    */     }
/* 283:340 */     if (this.agrupado)
/* 284:    */     {
/* 285:341 */       reportParameters.put("tipo", this.tipoReporte.getNombre().replace("Por ", ""));
/* 286:342 */       if ((TIPO_REPORTE.POR_ATRIBUTO.equals(this.tipoReporte)) && (this.atributoOrdenFabricacion != null)) {
/* 287:343 */         reportParameters.put("tipo", this.atributoOrdenFabricacion.getNombre());
/* 288:    */       }
/* 289:    */     }
/* 290:346 */     return reportParameters;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public String execute()
/* 294:    */   {
/* 295:    */     try
/* 296:    */     {
/* 297:356 */       super.prepareReport();
/* 298:    */     }
/* 299:    */     catch (JRException e)
/* 300:    */     {
/* 301:358 */       LOG.info("Error JRException");
/* 302:359 */       e.printStackTrace();
/* 303:360 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 304:    */     }
/* 305:    */     catch (IOException e)
/* 306:    */     {
/* 307:362 */       LOG.info("Error IOException");
/* 308:363 */       e.printStackTrace();
/* 309:364 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 310:    */     }
/* 311:366 */     return "";
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void validaDatos()
/* 315:    */   {
/* 316:371 */     if (this.fechaDesde == null) {
/* 317:372 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 318:    */     }
/* 319:374 */     if (this.fechaHasta == null) {
/* 320:375 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public Date getFechaDesde()
/* 325:    */   {
/* 326:385 */     return this.fechaDesde;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setFechaDesde(Date fechaDesde)
/* 330:    */   {
/* 331:395 */     this.fechaDesde = fechaDesde;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public Date getFechaHasta()
/* 335:    */   {
/* 336:404 */     return this.fechaHasta;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setFechaHasta(Date fechaHasta)
/* 340:    */   {
/* 341:414 */     this.fechaHasta = fechaHasta;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void cargarListaSubcategoriaProducto()
/* 345:    */   {
/* 346:418 */     HashMap<String, String> filters = new HashMap();
/* 347:419 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 348:420 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 352:    */   {
/* 353:424 */     HashMap<String, String> filters = new HashMap();
/* 354:425 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 355:426 */     if (this.listaCategoriaProductos == null) {
/* 356:427 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 357:    */     }
/* 358:429 */     return this.listaCategoriaProductos;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public List<Bodega> getListaBodega()
/* 362:    */   {
/* 363:433 */     if (this.listaBodega == null) {
/* 364:434 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 365:    */     }
/* 366:436 */     return this.listaBodega;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 370:    */   {
/* 371:440 */     return this.categoriaProductoSeleccionado;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 375:    */   {
/* 376:444 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 380:    */   {
/* 381:448 */     return this.subcategoriaProductoSeleccionado;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 385:    */   {
/* 386:452 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public Producto getProductoSeleccionado()
/* 390:    */   {
/* 391:456 */     return this.productoSeleccionado;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setProductoSeleccionado(Producto productoSeleccionado)
/* 395:    */   {
/* 396:460 */     this.productoSeleccionado = productoSeleccionado;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 400:    */   {
/* 401:464 */     return this.listaSubcategoriaProductos;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 405:    */   {
/* 406:468 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 410:    */   {
/* 411:472 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public Bodega getBodega()
/* 415:    */   {
/* 416:476 */     return this.bodega;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setBodega(Bodega bodega)
/* 420:    */   {
/* 421:480 */     this.bodega = bodega;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 425:    */   {
/* 426:484 */     this.listaBodega = listaBodega;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public Atributo getAtributoOrdenFabricacion()
/* 430:    */   {
/* 431:488 */     return this.atributoOrdenFabricacion;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setAtributoOrdenFabricacion(Atributo atributoOrdenFabricacion)
/* 435:    */   {
/* 436:492 */     this.atributoOrdenFabricacion = atributoOrdenFabricacion;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public PersonaResponsable getPersonaResponsable()
/* 440:    */   {
/* 441:496 */     return this.personaResponsable;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 445:    */   {
/* 446:500 */     this.personaResponsable = personaResponsable;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public Maquina getMaquina()
/* 450:    */   {
/* 451:504 */     return this.maquina;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setMaquina(Maquina maquina)
/* 455:    */   {
/* 456:508 */     this.maquina = maquina;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public List<Maquina> getListaMaquina()
/* 460:    */   {
/* 461:512 */     if (this.listaMaquina == null) {
/* 462:513 */       this.listaMaquina = this.servicioMaquina.obtenerListaCombo("nombre", true, null);
/* 463:    */     }
/* 464:515 */     return this.listaMaquina;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public Atributo getAtributo1()
/* 468:    */   {
/* 469:519 */     return this.atributo1;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setAtributo1(Atributo atributo1)
/* 473:    */   {
/* 474:523 */     this.atributo1 = atributo1;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public Atributo getAtributo2()
/* 478:    */   {
/* 479:527 */     return this.atributo2;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setAtributo2(Atributo atributo2)
/* 483:    */   {
/* 484:531 */     this.atributo2 = atributo2;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public Atributo getAtributo3()
/* 488:    */   {
/* 489:535 */     return this.atributo3;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setAtributo3(Atributo atributo3)
/* 493:    */   {
/* 494:539 */     this.atributo3 = atributo3;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public Atributo getAtributo4()
/* 498:    */   {
/* 499:543 */     return this.atributo4;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setAtributo4(Atributo atributo4)
/* 503:    */   {
/* 504:547 */     this.atributo4 = atributo4;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public Atributo getAtributo5()
/* 508:    */   {
/* 509:551 */     return this.atributo5;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setAtributo5(Atributo atributo5)
/* 513:    */   {
/* 514:555 */     this.atributo5 = atributo5;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public Atributo getAtributo6()
/* 518:    */   {
/* 519:559 */     return this.atributo6;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setAtributo6(Atributo atributo6)
/* 523:    */   {
/* 524:563 */     this.atributo6 = atributo6;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public Atributo getAtributo7()
/* 528:    */   {
/* 529:567 */     return this.atributo7;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void setAtributo7(Atributo atributo7)
/* 533:    */   {
/* 534:571 */     this.atributo7 = atributo7;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public Atributo getAtributo8()
/* 538:    */   {
/* 539:575 */     return this.atributo8;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void setAtributo8(Atributo atributo8)
/* 543:    */   {
/* 544:579 */     this.atributo8 = atributo8;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public Atributo getAtributo9()
/* 548:    */   {
/* 549:583 */     return this.atributo9;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setAtributo9(Atributo atributo9)
/* 553:    */   {
/* 554:587 */     this.atributo9 = atributo9;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public ValorAtributo getValorAtributoOF()
/* 558:    */   {
/* 559:591 */     return this.valorAtributoOF;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setValorAtributoOF(ValorAtributo valorAtributoOF)
/* 563:    */   {
/* 564:595 */     this.valorAtributoOF = valorAtributoOF;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public ValorAtributo getValorAtributo1()
/* 568:    */   {
/* 569:599 */     return this.valorAtributo1;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public void setValorAtributo1(ValorAtributo valorAtributo1)
/* 573:    */   {
/* 574:603 */     this.valorAtributo1 = valorAtributo1;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public ValorAtributo getValorAtributo2()
/* 578:    */   {
/* 579:607 */     return this.valorAtributo2;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void setValorAtributo2(ValorAtributo valorAtributo2)
/* 583:    */   {
/* 584:611 */     this.valorAtributo2 = valorAtributo2;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public ValorAtributo getValorAtributo3()
/* 588:    */   {
/* 589:615 */     return this.valorAtributo3;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public void setValorAtributo3(ValorAtributo valorAtributo3)
/* 593:    */   {
/* 594:619 */     this.valorAtributo3 = valorAtributo3;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public ValorAtributo getValorAtributo4()
/* 598:    */   {
/* 599:623 */     return this.valorAtributo4;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void setValorAtributo4(ValorAtributo valorAtributo4)
/* 603:    */   {
/* 604:627 */     this.valorAtributo4 = valorAtributo4;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public ValorAtributo getValorAtributo5()
/* 608:    */   {
/* 609:631 */     return this.valorAtributo5;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void setValorAtributo5(ValorAtributo valorAtributo5)
/* 613:    */   {
/* 614:635 */     this.valorAtributo5 = valorAtributo5;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public ValorAtributo getValorAtributo6()
/* 618:    */   {
/* 619:639 */     return this.valorAtributo6;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public void setValorAtributo6(ValorAtributo valorAtributo6)
/* 623:    */   {
/* 624:643 */     this.valorAtributo6 = valorAtributo6;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public ValorAtributo getValorAtributo7()
/* 628:    */   {
/* 629:647 */     return this.valorAtributo7;
/* 630:    */   }
/* 631:    */   
/* 632:    */   public void setValorAtributo7(ValorAtributo valorAtributo7)
/* 633:    */   {
/* 634:651 */     this.valorAtributo7 = valorAtributo7;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public ValorAtributo getValorAtributo8()
/* 638:    */   {
/* 639:655 */     return this.valorAtributo8;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public void setValorAtributo8(ValorAtributo valorAtributo8)
/* 643:    */   {
/* 644:659 */     this.valorAtributo8 = valorAtributo8;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public ValorAtributo getValorAtributo9()
/* 648:    */   {
/* 649:663 */     return this.valorAtributo9;
/* 650:    */   }
/* 651:    */   
/* 652:    */   public void setValorAtributo9(ValorAtributo valorAtributo9)
/* 653:    */   {
/* 654:667 */     this.valorAtributo9 = valorAtributo9;
/* 655:    */   }
/* 656:    */   
/* 657:    */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 658:    */   {
/* 659:671 */     if (this.listaPersonaResponsable == null)
/* 660:    */     {
/* 661:672 */       HashMap<String, String> filters = new HashMap();
/* 662:673 */       filters.put("indicadorOrdenFabricacion", "true");
/* 663:674 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 664:    */     }
/* 665:676 */     return this.listaPersonaResponsable;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public TIPO_REPORTE getTipoReporte()
/* 669:    */   {
/* 670:680 */     return this.tipoReporte;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public void setTipoReporte(TIPO_REPORTE tipoReporte)
/* 674:    */   {
/* 675:684 */     this.tipoReporte = tipoReporte;
/* 676:    */   }
/* 677:    */   
/* 678:    */   public List<SelectItem> getListaTipoReporte()
/* 679:    */   {
/* 680:688 */     List<SelectItem> lista = new ArrayList();
/* 681:689 */     for (TIPO_REPORTE tr : TIPO_REPORTE.values()) {
/* 682:690 */       lista.add(new SelectItem(tr, tr.getNombre()));
/* 683:    */     }
/* 684:692 */     return lista;
/* 685:    */   }
/* 686:    */   
/* 687:    */   private List<ValorAtributo> getListaVariables()
/* 688:    */   {
/* 689:696 */     List<ValorAtributo> list = new ArrayList();
/* 690:697 */     if (this.valorAtributoOF != null) {
/* 691:698 */       list.add(this.valorAtributoOF);
/* 692:    */     }
/* 693:700 */     if (this.valorAtributo1 != null) {
/* 694:701 */       list.add(this.valorAtributo1);
/* 695:    */     }
/* 696:703 */     if (this.valorAtributo2 != null) {
/* 697:704 */       list.add(this.valorAtributo2);
/* 698:    */     }
/* 699:706 */     if (this.valorAtributo3 != null) {
/* 700:707 */       list.add(this.valorAtributo3);
/* 701:    */     }
/* 702:709 */     if (this.valorAtributo4 != null) {
/* 703:710 */       list.add(this.valorAtributo4);
/* 704:    */     }
/* 705:712 */     if (this.valorAtributo5 != null) {
/* 706:713 */       list.add(this.valorAtributo5);
/* 707:    */     }
/* 708:715 */     if (this.valorAtributo6 != null) {
/* 709:716 */       list.add(this.valorAtributo6);
/* 710:    */     }
/* 711:718 */     if (this.valorAtributo7 != null) {
/* 712:719 */       list.add(this.valorAtributo7);
/* 713:    */     }
/* 714:721 */     if (this.valorAtributo8 != null) {
/* 715:722 */       list.add(this.valorAtributo8);
/* 716:    */     }
/* 717:724 */     if (this.valorAtributo9 != null) {
/* 718:725 */       list.add(this.valorAtributo9);
/* 719:    */     }
/* 720:727 */     return list;
/* 721:    */   }
/* 722:    */   
/* 723:    */   public boolean isAgrupado()
/* 724:    */   {
/* 725:731 */     return this.agrupado;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public void setAgrupado(boolean agrupado)
/* 729:    */   {
/* 730:735 */     this.agrupado = agrupado;
/* 731:    */   }
/* 732:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteProduccionBean
 * JD-Core Version:    0.7.0.1
 */