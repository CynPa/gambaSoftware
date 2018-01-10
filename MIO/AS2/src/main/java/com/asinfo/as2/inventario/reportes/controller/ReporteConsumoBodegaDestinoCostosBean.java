/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   8:    */ import com.asinfo.as2.entities.DimensionContable;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  18:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Calendar;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ViewScoped;
/*  34:    */ import javax.faces.model.SelectItem;
/*  35:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  36:    */ import net.sf.jasperreports.engine.JRException;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @ViewScoped
/*  41:    */ public class ReporteConsumoBodegaDestinoCostosBean
/*  42:    */   extends AbstractBaseReportBean
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 4992625350815288045L;
/*  45:    */   @EJB
/*  46:    */   protected ServicioSucursal servicioSucursal;
/*  47:    */   @EJB
/*  48:    */   protected ServicioDocumento servicioDocumento;
/*  49:    */   private DimensionContable proyecto;
/*  50:    */   protected Sucursal sucursal;
/*  51:    */   protected List<Sucursal> listaSucursal;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  56:    */   @EJB
/*  57:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  58:    */   private Date fechaDesde;
/*  59:    */   private Date fechaHasta;
/*  60:    */   private SubcategoriaProducto subcategoriaProducto;
/*  61:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  62:    */   
/*  63:    */   static enum TipoReporte
/*  64:    */   {
/*  65: 72 */     NORMAL("Normal"),  MENSUAL_RESUMIDO("Mensual Resumido"),  CATEGORIA("Categoria");
/*  66:    */     
/*  67:    */     private String nombre;
/*  68:    */     
/*  69:    */     private TipoReporte(String nombre)
/*  70:    */     {
/*  71: 77 */       this.nombre = nombre;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public String getNombre()
/*  75:    */     {
/*  76: 81 */       return this.nombre;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public void setNombre(String nombre)
/*  80:    */     {
/*  81: 85 */       this.nombre = nombre;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:101 */   private Map<Integer, CategoriaProducto> hashCategorias = new HashMap();
/*  86:    */   private List<CategoriaProducto> listaCategoriaProductosSeleccionada;
/*  87:    */   private boolean indicadorResumen;
/*  88:    */   private TipoReporte tipoReporte;
/*  89:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  90:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  91:    */   private List<Documento> listaDocumento;
/*  92:    */   private List<SelectItem> listaTipoReporte;
/*  93:    */   private Producto producto;
/*  94:    */   private DestinoCosto destinoCosto;
/*  95:    */   private Documento documento;
/*  96:    */   
/*  97:    */   protected JRDataSource getJRDataSource()
/*  98:    */   {
/*  99:125 */     List listaDatosReporte = new ArrayList();
/* 100:126 */     JRDataSource ds = null;
/* 101:    */     try
/* 102:    */     {
/* 103:128 */       String[] fields = new String[0];
/* 104:129 */       if (this.tipoReporte.equals(TipoReporte.MENSUAL_RESUMIDO))
/* 105:    */       {
/* 106:130 */         listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteConsumoBodegaDestinoCostoMensual(this.fechaDesde, this.fechaHasta, 
/* 107:131 */           AppUtil.getOrganizacion().getId(), this.producto, this.destinoCosto, getListaCategoriaProductosSeleccionada(), this.subcategoriaProducto, this.proyecto, getSucursal(), this.documento);
/* 108:132 */         fields = new String[] { "codigoDestinoCosto", "nombreDestinoCosto", "codigoProducto", "nombreProducto", "anio", "mes", "cantidad", "costo", "f_proyectoCodigo" };
/* 109:    */       }
/* 110:134 */       if (this.tipoReporte.equals(TipoReporte.CATEGORIA))
/* 111:    */       {
/* 112:135 */         listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteConsumoBodegaDestinoCostoCategoria(this.fechaDesde, this.fechaHasta, this.subcategoriaProducto, 
/* 113:136 */           AppUtil.getOrganizacion().getId(), this.producto, this.destinoCosto, getListaCategoriaProductosSeleccionada(), this.proyecto, getSucursal(), this.documento);
/* 114:137 */         fields = new String[] { "f_categoria", "f_subcategoria", "totalCosto", "codigoDestinocosto", "nombreDestinoCosto" };
/* 115:    */       }
/* 116:139 */       if (this.tipoReporte.equals(TipoReporte.NORMAL)) {
/* 117:140 */         if (isIndicadorResumen())
/* 118:    */         {
/* 119:141 */           listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteConsumoBodegaDestinoCostoDetallado(this.fechaDesde, this.fechaHasta, this.subcategoriaProducto, 
/* 120:142 */             AppUtil.getOrganizacion().getId(), this.producto, this.destinoCosto, getListaCategoriaProductosSeleccionada(), this.proyecto, getSucursal(), true, this.documento);
/* 121:    */           
/* 122:144 */           fields = new String[] { "f_numero", "f_fecha", "codigoDestinoCosto", "nombreDestinoCosto", "codigoProducto", "nombreProducto", "cantidad", "costoTotal", "f_detalleMovimientoInventarioDescripcion", "f_proyectoCodigo", "f_movimientoInventarioDescripcion", "f_categoria", "f_subcategoria", "f_miDescripcion", "f_codigoUnidad", "f_nombreUnidad" };
/* 123:    */         }
/* 124:    */         else
/* 125:    */         {
/* 126:148 */           listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteConsumoBodegaDestinoCostoDetallado(this.fechaDesde, this.fechaHasta, this.subcategoriaProducto, 
/* 127:149 */             AppUtil.getOrganizacion().getId(), this.producto, this.destinoCosto, getListaCategoriaProductosSeleccionada(), this.proyecto, getSucursal(), false, this.documento);
/* 128:150 */           fields = new String[] { "f_numero", "f_fecha", "f_codigoDestinoCosto", "f_nombreDestinoCosto", "f_codigoProducto", "f_nombreProducto", "f_cantidad", "f_costoTotal", "f_detalleMovimientoInventarioDescripcion", "f_proyectoCodigo", "f_movimientoInventarioDescripcion", "f_categoria", "f_subcategoria", "f_miDescripcion" };
/* 129:    */         }
/* 130:    */       }
/* 131:156 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 132:    */     }
/* 133:    */     catch (Exception e)
/* 134:    */     {
/* 135:158 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 136:    */     }
/* 137:161 */     return ds;
/* 138:    */   }
/* 139:    */   
/* 140:    */   @PostConstruct
/* 141:    */   public void init()
/* 142:    */   {
/* 143:167 */     Calendar calfechaDesde = Calendar.getInstance();
/* 144:168 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 145:169 */     this.fechaDesde = calfechaDesde.getTime();
/* 146:170 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected String getCompileFileName()
/* 150:    */   {
/* 151:180 */     if (this.tipoReporte.equals(TipoReporte.MENSUAL_RESUMIDO)) {
/* 152:181 */       return "reporteConsumoBodegaDestinoCostosMensual";
/* 153:    */     }
/* 154:183 */     if (this.tipoReporte.equals(TipoReporte.CATEGORIA)) {
/* 155:184 */       return "reporteConsumoBodegaDestinoCostosCategoria";
/* 156:    */     }
/* 157:186 */     if (this.tipoReporte.equals(TipoReporte.NORMAL))
/* 158:    */     {
/* 159:187 */       if (this.indicadorResumen) {
/* 160:188 */         return "reporteConsumoBodegaDestinoCostosResumido";
/* 161:    */       }
/* 162:190 */       return "reporteConsumoBodegaDestinoCostosDetallado";
/* 163:    */     }
/* 164:193 */     return null;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void cargarListaSubcategoriaProducto()
/* 168:    */   {
/* 169:198 */     HashMap<String, String> filters = new HashMap();
/* 170:199 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 171:200 */     this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 172:201 */     this.hashCategorias.put(Integer.valueOf(this.categoriaProductoSeleccionado.getId()), this.categoriaProductoSeleccionado);
/* 173:202 */     if (getListaCategoriaProductosSeleccionada().size() > 1) {
/* 174:203 */       this.subcategoriaProducto = null;
/* 175:    */     }
/* 176:205 */     this.categoriaProductoSeleccionado = null;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String execute()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:216 */       if (this.fechaDesde == null) {
/* 184:217 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 185:    */       }
/* 186:219 */       if (this.fechaHasta == null) {
/* 187:220 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 188:    */       }
/* 189:222 */       super.prepareReport();
/* 190:    */     }
/* 191:    */     catch (JRException e)
/* 192:    */     {
/* 193:224 */       LOG.info("Error JRException");
/* 194:225 */       e.printStackTrace();
/* 195:226 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 196:    */     }
/* 197:    */     catch (IOException e)
/* 198:    */     {
/* 199:228 */       LOG.info("Error IOException");
/* 200:229 */       e.printStackTrace();
/* 201:230 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 202:    */     }
/* 203:234 */     return "";
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected Map<String, Object> getReportParameters()
/* 207:    */   {
/* 208:246 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 209:    */     
/* 210:248 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_reporte_consumo_bodega_mensual"));
/* 211:250 */     if (this.fechaDesde == null) {
/* 212:251 */       this.fechaDesde = new Date();
/* 213:    */     }
/* 214:253 */     if (this.fechaHasta == null) {
/* 215:254 */       this.fechaHasta = new Date();
/* 216:    */     }
/* 217:256 */     reportParameters.put("FechaDesde", this.fechaDesde);
/* 218:257 */     reportParameters.put("FechaHasta", this.fechaHasta);
/* 219:258 */     reportParameters.put("p_subcategoriaProducto", this.subcategoriaProducto == null ? "TODAS" : this.subcategoriaProducto.getNombre());
/* 220:259 */     String categoriasSeleccionadas = "";
/* 221:260 */     if (getListaCategoriaProductosSeleccionada().size() > 0)
/* 222:    */     {
/* 223:261 */       for (CategoriaProducto categoriaProducto : getListaCategoriaProductosSeleccionada()) {
/* 224:262 */         categoriasSeleccionadas = categoriasSeleccionadas + categoriaProducto.getNombre().replace(" ", "") + ", ";
/* 225:    */       }
/* 226:264 */       categoriasSeleccionadas = categoriasSeleccionadas.substring(0, categoriasSeleccionadas.length() - 2);
/* 227:265 */       categoriasSeleccionadas = categoriasSeleccionadas + ".";
/* 228:    */     }
/* 229:267 */     reportParameters.put("p_categoriaProducto", getListaCategoriaProductosSeleccionada().size() > 0 ? categoriasSeleccionadas : "TODAS");
/* 230:268 */     reportParameters.put("p_producto", this.producto != null ? this.producto.getNombre() : "Todos");
/* 231:269 */     reportParameters.put("p_destinoCosto", this.destinoCosto != null ? this.destinoCosto.getNombre() : "Todos");
/* 232:270 */     reportParameters.put("total", "Total");
/* 233:271 */     reportParameters.put("p_proyecto", this.proyecto != null ? this.proyecto.getNombre() : "Todos");
/* 234:272 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 235:273 */     return reportParameters;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void cargarProducto(Producto producto)
/* 239:    */   {
/* 240:277 */     this.producto = producto;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Date getFechaDesde()
/* 244:    */   {
/* 245:286 */     return this.fechaDesde;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setFechaDesde(Date fechaDesde)
/* 249:    */   {
/* 250:296 */     this.fechaDesde = fechaDesde;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Date getFechaHasta()
/* 254:    */   {
/* 255:305 */     return this.fechaHasta;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setFechaHasta(Date fechaHasta)
/* 259:    */   {
/* 260:315 */     this.fechaHasta = fechaHasta;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public boolean isIndicadorResumen()
/* 264:    */   {
/* 265:324 */     return this.indicadorResumen;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 269:    */   {
/* 270:334 */     this.indicadorResumen = indicadorResumen;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 274:    */   {
/* 275:343 */     return this.subcategoriaProducto;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 279:    */   {
/* 280:353 */     this.subcategoriaProducto = subcategoriaProducto;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 284:    */   {
/* 285:362 */     if (this.listaSubcategoriaProducto == null) {
/* 286:363 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 287:    */     }
/* 288:365 */     return this.listaSubcategoriaProducto;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Producto getProducto()
/* 292:    */   {
/* 293:372 */     return this.producto;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setProducto(Producto producto)
/* 297:    */   {
/* 298:380 */     this.producto = producto;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public DestinoCosto getDestinoCosto()
/* 302:    */   {
/* 303:384 */     return this.destinoCosto;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 307:    */   {
/* 308:388 */     this.destinoCosto = destinoCosto;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 312:    */   {
/* 313:392 */     return this.categoriaProductoSeleccionado;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 317:    */   {
/* 318:396 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 322:    */   {
/* 323:403 */     HashMap<String, String> filters = new HashMap();
/* 324:404 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 325:405 */     if (this.listaCategoriaProductos == null) {
/* 326:406 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 327:    */     }
/* 328:408 */     return this.listaCategoriaProductos;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 332:    */   {
/* 333:416 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public TipoReporte getTipoReporte()
/* 337:    */   {
/* 338:424 */     return this.tipoReporte;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 342:    */   {
/* 343:432 */     this.tipoReporte = tipoReporte;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public List<SelectItem> getListaTipoReporte()
/* 347:    */   {
/* 348:440 */     this.listaTipoReporte = new ArrayList();
/* 349:441 */     for (TipoReporte tipoReporte : TipoReporte.values())
/* 350:    */     {
/* 351:442 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 352:443 */       this.listaTipoReporte.add(item);
/* 353:    */     }
/* 354:446 */     return this.listaTipoReporte;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 358:    */   {
/* 359:454 */     this.listaTipoReporte = listaTipoReporte;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public DimensionContable getProyecto()
/* 363:    */   {
/* 364:459 */     return this.proyecto;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setProyecto(DimensionContable proyecto)
/* 368:    */   {
/* 369:464 */     this.proyecto = proyecto;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public List<CategoriaProducto> getListaCategoriaProductosSeleccionada()
/* 373:    */   {
/* 374:469 */     return new ArrayList(this.hashCategorias.values());
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void setListaCategoriaProductosSeleccionada(List<CategoriaProducto> listaCategoriaProductosSeleccionada)
/* 378:    */   {
/* 379:474 */     this.listaCategoriaProductosSeleccionada = listaCategoriaProductosSeleccionada;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public String eliminarCategoria(CategoriaProducto categoriaProducto)
/* 383:    */   {
/* 384:478 */     this.hashCategorias.remove(Integer.valueOf(categoriaProducto.getId()));
/* 385:479 */     return null;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public Sucursal getSucursal()
/* 389:    */   {
/* 390:486 */     return this.sucursal;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setSucursal(Sucursal sucursal)
/* 394:    */   {
/* 395:494 */     this.sucursal = sucursal;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public Documento getDocumento()
/* 399:    */   {
/* 400:501 */     return this.documento;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setDocumento(Documento documento)
/* 404:    */   {
/* 405:508 */     this.documento = documento;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public List<Sucursal> getListaSucursal()
/* 409:    */   {
/* 410:515 */     if (this.listaSucursal == null)
/* 411:    */     {
/* 412:516 */       Map<String, String> filters = new HashMap();
/* 413:517 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 414:518 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 415:    */     }
/* 416:520 */     return this.listaSucursal;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public List<Documento> getListaDocumento()
/* 420:    */   {
/* 421:528 */     if (this.listaDocumento == null)
/* 422:    */     {
/* 423:529 */       this.listaDocumento = new ArrayList();
/* 424:    */       try
/* 425:    */       {
/* 426:531 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.CONSUMO_BODEGA, 
/* 427:532 */           AppUtil.getOrganizacion().getIdOrganizacion()));
/* 428:533 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.SALIDA_MATERIAL, 
/* 429:534 */           AppUtil.getOrganizacion().getIdOrganizacion()));
/* 430:    */       }
/* 431:    */       catch (ExcepcionAS2 e)
/* 432:    */       {
/* 433:537 */         e.printStackTrace();
/* 434:    */       }
/* 435:    */     }
/* 436:540 */     return this.listaDocumento;
/* 437:    */   }
/* 438:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteConsumoBodegaDestinoCostosBean
 * JD-Core Version:    0.7.0.1
 */