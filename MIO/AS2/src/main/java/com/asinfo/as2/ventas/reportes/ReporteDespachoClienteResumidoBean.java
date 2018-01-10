/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.entities.Subempresa;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteDespachoCliente;
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
/*  41:    */ public class ReporteDespachoClienteResumidoBean
/*  42:    */   extends AbstractBaseReportBean
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 1L;
/*  45:    */   @EJB
/*  46:    */   private ServicioEmpresa servicioEmpresa;
/*  47:    */   @EJB
/*  48:    */   private ServicioPersonaResponsable servicioResponsableSalidaMercaderia;
/*  49:    */   @EJB
/*  50:    */   private ServicioReporteDespachoCliente servicioReporteDespachoCliente;
/*  51:    */   @EJB
/*  52:    */   private ServicioBodega servicioBodega;
/*  53:    */   @EJB
/*  54:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  55:    */   @EJB
/*  56:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  57:    */   private Empresa empresa;
/*  58:    */   private PersonaResponsable responsableSalidaMercaderia;
/*  59:    */   private Date fechaDesde;
/*  60:    */   private Date fechaHasta;
/*  61:    */   private Bodega bodega;
/*  62:    */   private SubcategoriaProducto subcategoriaProducto;
/*  63:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  64:    */   private Producto producto;
/*  65:    */   private List<Bodega> listaBodega;
/*  66:    */   private Subempresa subempresa;
/*  67:    */   private List<Subempresa> listaSubempresa;
/*  68:    */   private List<SelectItem> listaTipoReporte;
/*  69:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  70:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  71:    */   private List<PersonaResponsable> listaResponsableSalidaMercaderia;
/*  72:    */   private TipoReporte tipoReporte;
/*  73:    */   
/*  74:    */   static enum TipoReporte
/*  75:    */   {
/*  76: 96 */     DETALLADO("Reporte Detallado por Cliente"),  RESUMIDO("Reporte Resumido por Productos"),  POR_LOTES("Reporte Detallado por Cliente y Lote");
/*  77:    */     
/*  78:    */     private String nombre;
/*  79:    */     
/*  80:    */     private TipoReporte(String nombre)
/*  81:    */     {
/*  82:101 */       this.nombre = nombre;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public String getNombre()
/*  86:    */     {
/*  87:105 */       return this.nombre;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public void setNombre(String nombre)
/*  91:    */     {
/*  92:109 */       this.nombre = nombre;
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected JRDataSource getJRDataSource()
/*  97:    */   {
/*  98:123 */     List listaDatosReporte = new ArrayList();
/*  99:124 */     JRDataSource ds = null;
/* 100:    */     
/* 101:126 */     listaDatosReporte = this.servicioReporteDespachoCliente.getReporteDespachoDetallado(this.tipoReporte.ordinal(), this.fechaDesde, this.fechaHasta, 
/* 102:127 */       getEmpresa().getId(), this.responsableSalidaMercaderia == null ? 0 : this.responsableSalidaMercaderia.getId(), this.bodega, getSubempresa(), 
/* 103:128 */       AppUtil.getOrganizacion().getIdOrganizacion(), this.categoriaProductoSeleccionado, this.subcategoriaProducto, this.producto);
/* 104:129 */     String[] fields = null;
/* 105:130 */     if (this.tipoReporte.equals(TipoReporte.DETALLADO)) {
/* 106:131 */       fields = new String[] { "f_numeroDespacho", "f_fechaDespacho", "f_nombreComercialCliente", "f_nombreFiscalCliente", "f_identificacionCliente", "f_numeroGuiaRemision", "f_usuario", "codigo_subCliente", "cliente_final", "numero_factura", "f_descripcion", "f_nombreResponsableSalidaMercaderia", "f_identificacionResponsableSalidaMercaderia", "f_codigoProducto", "f_codigoComercialProducto", "f_nombreProducto", "f_nombreComercialProducto", "f_unidad", "f_codigoBodega", "f_nombreBodega", "f_cantidad", "f_costo", "f_numeroPedido", "f_peso" };
/* 107:136 */     } else if (this.tipoReporte.equals(TipoReporte.RESUMIDO)) {
/* 108:137 */       fields = new String[] { "f_nombreResponsableSalidaMercaderia", "f_identificacionResponsableSalidaMercaderia", "f_codigoProducto", "f_codigoComercialProducto", "f_nombreProducto", "f_nombreComercialProducto", "f_unidad", "f_codigoBodega", "f_nombreBodega", "f_cantidad", "f_costo", "f_numeroPedido", "f_peso" };
/* 109:140 */     } else if (this.tipoReporte.equals(TipoReporte.POR_LOTES)) {
/* 110:141 */       fields = new String[] { "f_numeroDespacho", "f_fechaDespacho", "f_nombreComercialCliente", "f_nombreFiscalCliente", "f_identificacionCliente", "f_numeroGuiaRemision", "f_usuario", "codigo_subCliente", "cliente_final", "numero_factura", "f_descripcion", "f_nombreResponsableSalidaMercaderia", "f_identificacionResponsableSalidaMercaderia", "f_codigoProducto", "f_codigoComercialProducto", "f_nombreProducto", "f_nombreComercialProducto", "f_unidad", "f_codigoBodega", "f_nombreBodega", "f_cantidad", "f_costo", "f_lote", "f_numeroPedido", "f_peso" };
/* 111:    */     }
/* 112:147 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 113:    */     
/* 114:149 */     return ds;
/* 115:    */   }
/* 116:    */   
/* 117:    */   @PostConstruct
/* 118:    */   public void init()
/* 119:    */   {
/* 120:154 */     Calendar calfechaDesde = Calendar.getInstance();
/* 121:155 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 122:156 */     this.fechaDesde = calfechaDesde.getTime();
/* 123:157 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 124:158 */     this.tipoReporte = TipoReporte.DETALLADO;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected String getCompileFileName()
/* 128:    */   {
/* 129:168 */     if (TipoReporte.RESUMIDO.equals(this.tipoReporte)) {
/* 130:169 */       return "reporteDespachoClienteResumido";
/* 131:    */     }
/* 132:170 */     if (TipoReporte.DETALLADO.equals(this.tipoReporte)) {
/* 133:171 */       return "reporteDespachoClienteDetallado";
/* 134:    */     }
/* 135:173 */     return "reporteDespachoClienteDetalladoPorLote";
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected Map<String, Object> getReportParameters()
/* 139:    */   {
/* 140:184 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 141:185 */     if (TipoReporte.RESUMIDO.equals(this.tipoReporte)) {
/* 142:186 */       reportParameters.put("ReportTitle", "Reporte Despacho Resumido");
/* 143:187 */     } else if (TipoReporte.DETALLADO.equals(this.tipoReporte)) {
/* 144:188 */       reportParameters.put("ReportTitle", "Reporte Despacho Detallado");
/* 145:    */     } else {
/* 146:190 */       reportParameters.put("ReportTitle", "Reporte Despacho Detallado por Lotes");
/* 147:    */     }
/* 148:193 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 149:194 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 150:195 */     reportParameters.put("FormatoFecha", ParametrosSistema.getFormatoFecha(AppUtil.getOrganizacion().getId()));
/* 151:196 */     reportParameters.put("p_responsable", (this.responsableSalidaMercaderia != null) && (this.responsableSalidaMercaderia.getId() != 0) ? this.responsableSalidaMercaderia
/* 152:197 */       .getNombres() + " " + this.responsableSalidaMercaderia.getApellidos() : "Todos");
/* 153:198 */     reportParameters.put("p_bodega", this.bodega != null ? this.bodega.getNombre() : "Todos");
/* 154:199 */     reportParameters.put("p_categoriaProducto", this.categoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 155:200 */     reportParameters.put("p_subcategoriaProducto", this.subcategoriaProducto != null ? this.subcategoriaProducto.getNombre() : "Todos");
/* 156:201 */     reportParameters.put("p_producto", this.producto != null ? this.producto.getNombre() : "Todos");
/* 157:202 */     return reportParameters;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String execute()
/* 161:    */   {
/* 162:    */     try
/* 163:    */     {
/* 164:212 */       validaDatos();
/* 165:213 */       super.prepareReport();
/* 166:    */     }
/* 167:    */     catch (JRException e)
/* 168:    */     {
/* 169:215 */       LOG.info("Error JRException");
/* 170:216 */       e.printStackTrace();
/* 171:217 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 172:    */     }
/* 173:    */     catch (IOException e)
/* 174:    */     {
/* 175:219 */       LOG.info("Error IOException");
/* 176:220 */       e.printStackTrace();
/* 177:221 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 178:    */     }
/* 179:224 */     return null;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void validaDatos()
/* 183:    */   {
/* 184:228 */     if (this.fechaDesde == null) {
/* 185:229 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 186:    */     }
/* 187:231 */     if (this.fechaHasta == null) {
/* 188:232 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 193:    */   {
/* 194:243 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<Subempresa> autocompletarSubEmpresa(String consulta)
/* 198:    */   {
/* 199:247 */     return this.servicioEmpresa.autocompletarSubEmpresa(consulta, getEmpresa());
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<PersonaResponsable> autocompletarResponsableSalidaMercaderia(String consulta)
/* 203:    */   {
/* 204:258 */     consulta = consulta.toUpperCase();
/* 205:    */     
/* 206:260 */     String sortField = "codigo";
/* 207:261 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 208:262 */     filters.put("activo", "true");
/* 209:263 */     filters.put("indicadorSalidaMercaderia", "true");
/* 210:    */     
/* 211:265 */     List<PersonaResponsable> lista = new ArrayList();
/* 212:267 */     for (PersonaResponsable responsableSalidaMercaderia : this.servicioResponsableSalidaMercaderia.obtenerListaCombo(sortField, true, filters)) {
/* 213:269 */       if ((responsableSalidaMercaderia.getIdentificacion().toUpperCase().contains(consulta)) || 
/* 214:270 */         (responsableSalidaMercaderia.getNombres().toUpperCase().contains(consulta)) || 
/* 215:271 */         (responsableSalidaMercaderia.getApellidos().toUpperCase().contains(consulta))) {
/* 216:272 */         lista.add(responsableSalidaMercaderia);
/* 217:    */       }
/* 218:    */     }
/* 219:275 */     return lista;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Date getFechaDesde()
/* 223:    */   {
/* 224:289 */     return this.fechaDesde;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setFechaDesde(Date fechaDesde)
/* 228:    */   {
/* 229:299 */     this.fechaDesde = fechaDesde;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public Date getFechaHasta()
/* 233:    */   {
/* 234:308 */     return this.fechaHasta;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setFechaHasta(Date fechaHasta)
/* 238:    */   {
/* 239:318 */     this.fechaHasta = fechaHasta;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public Empresa getEmpresa()
/* 243:    */   {
/* 244:327 */     if (this.empresa == null) {
/* 245:328 */       this.empresa = new Empresa();
/* 246:    */     }
/* 247:330 */     return this.empresa;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setEmpresa(Empresa empresa)
/* 251:    */   {
/* 252:340 */     this.empresa = empresa;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public PersonaResponsable getResponsableSalidaMercaderia()
/* 256:    */   {
/* 257:349 */     if (this.responsableSalidaMercaderia == null) {
/* 258:350 */       this.responsableSalidaMercaderia = new PersonaResponsable();
/* 259:    */     }
/* 260:352 */     return this.responsableSalidaMercaderia;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setResponsableSalidaMercaderia(PersonaResponsable responsableSalidaMercaderia)
/* 264:    */   {
/* 265:362 */     this.responsableSalidaMercaderia = responsableSalidaMercaderia;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public List<PersonaResponsable> getListaResponsableSalidaMercaderia()
/* 269:    */   {
/* 270:371 */     if (this.listaResponsableSalidaMercaderia == null) {
/* 271:372 */       this.listaResponsableSalidaMercaderia = this.servicioResponsableSalidaMercaderia.obtenerListaCombo("apellidos", true, null);
/* 272:    */     }
/* 273:374 */     return this.listaResponsableSalidaMercaderia;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setListaResponsableSalidaMercaderia(List<PersonaResponsable> listaResponsableSalidaMercaderia)
/* 277:    */   {
/* 278:384 */     this.listaResponsableSalidaMercaderia = listaResponsableSalidaMercaderia;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Bodega getBodega()
/* 282:    */   {
/* 283:388 */     return this.bodega;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setBodega(Bodega bodega)
/* 287:    */   {
/* 288:392 */     this.bodega = bodega;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<Bodega> getListaBodega()
/* 292:    */   {
/* 293:396 */     if (this.listaBodega == null) {
/* 294:397 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 295:    */     }
/* 296:399 */     return this.listaBodega;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 300:    */   {
/* 301:403 */     this.listaBodega = listaBodega;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void actualizarClienteListener()
/* 305:    */   {
/* 306:407 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.empresa.getId(), false);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public Subempresa getSubempresa()
/* 310:    */   {
/* 311:411 */     return this.subempresa;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setSubempresa(Subempresa subempresa)
/* 315:    */   {
/* 316:415 */     this.subempresa = subempresa;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public List<Subempresa> getListaSubempresa()
/* 320:    */   {
/* 321:419 */     if (this.listaSubempresa == null) {
/* 322:420 */       this.listaSubempresa = new ArrayList();
/* 323:    */     }
/* 324:422 */     return this.listaSubempresa;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 328:    */   {
/* 329:426 */     this.listaSubempresa = listaSubempresa;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public List<SelectItem> getListaTipoReporte()
/* 333:    */   {
/* 334:430 */     if (this.listaTipoReporte == null)
/* 335:    */     {
/* 336:431 */       this.listaTipoReporte = new ArrayList();
/* 337:432 */       for (TipoReporte tipoReporte : TipoReporte.values())
/* 338:    */       {
/* 339:433 */         SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 340:434 */         this.listaTipoReporte.add(item);
/* 341:    */       }
/* 342:    */     }
/* 343:438 */     return this.listaTipoReporte;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public TipoReporte getTipoReporte()
/* 347:    */   {
/* 348:442 */     return this.tipoReporte;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 352:    */   {
/* 353:446 */     this.tipoReporte = tipoReporte;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 357:    */   {
/* 358:450 */     HashMap<String, String> filters = new HashMap();
/* 359:451 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 360:452 */     if (this.listaCategoriaProductos == null) {
/* 361:453 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 362:    */     }
/* 363:455 */     return this.listaCategoriaProductos;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void cargarListaSubcategoriaProducto()
/* 367:    */   {
/* 368:459 */     this.listaSubcategoriaProducto = new ArrayList();
/* 369:460 */     if (this.categoriaProductoSeleccionado != null)
/* 370:    */     {
/* 371:461 */       HashMap<String, String> filters = new HashMap();
/* 372:462 */       filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 373:463 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 374:    */     }
/* 375:466 */     this.subcategoriaProducto = null;
/* 376:467 */     actualizarSubcategoriaProducto();
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void actualizarSubcategoriaProducto()
/* 380:    */   {
/* 381:471 */     this.producto = null;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void cargarProducto(Producto producto)
/* 385:    */   {
/* 386:475 */     this.producto = producto;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 390:    */   {
/* 391:479 */     return this.subcategoriaProducto;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 395:    */   {
/* 396:483 */     this.subcategoriaProducto = subcategoriaProducto;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 400:    */   {
/* 401:487 */     return this.categoriaProductoSeleccionado;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 405:    */   {
/* 406:491 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public Producto getProducto()
/* 410:    */   {
/* 411:495 */     return this.producto;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setProducto(Producto producto)
/* 415:    */   {
/* 416:499 */     this.producto = producto;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 420:    */   {
/* 421:503 */     return this.listaSubcategoriaProducto;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 425:    */   {
/* 426:507 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 427:    */   }
/* 428:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteDespachoClienteResumidoBean
 * JD-Core Version:    0.7.0.1
 */