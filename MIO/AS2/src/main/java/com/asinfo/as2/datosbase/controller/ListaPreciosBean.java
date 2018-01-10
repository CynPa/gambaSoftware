/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  10:    */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*  11:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  12:    */ import com.asinfo.as2.entities.Moneda;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Producto;
/*  15:    */ import com.asinfo.as2.entities.SaldoProducto;
/*  16:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  19:    */ import com.asinfo.as2.entities.Zona;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoListaPreciosEnum;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import com.asinfo.as2.util.RutaArchivo;
/*  28:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  29:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  30:    */ import java.io.BufferedInputStream;
/*  31:    */ import java.io.InputStream;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.HashSet;
/*  37:    */ import java.util.Iterator;
/*  38:    */ import java.util.List;
/*  39:    */ import java.util.Map;
/*  40:    */ import java.util.Set;
/*  41:    */ import java.util.Vector;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.component.html.HtmlInputText;
/*  47:    */ import javax.faces.event.ActionEvent;
/*  48:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  49:    */ import javax.faces.model.SelectItem;
/*  50:    */ import org.apache.log4j.Logger;
/*  51:    */ import org.primefaces.component.datatable.DataTable;
/*  52:    */ import org.primefaces.context.RequestContext;
/*  53:    */ import org.primefaces.event.FileUploadEvent;
/*  54:    */ import org.primefaces.event.SelectEvent;
/*  55:    */ import org.primefaces.model.LazyDataModel;
/*  56:    */ import org.primefaces.model.SortOrder;
/*  57:    */ import org.primefaces.model.StreamedContent;
/*  58:    */ import org.primefaces.model.UploadedFile;
/*  59:    */ 
/*  60:    */ @ManagedBean
/*  61:    */ @ViewScoped
/*  62:    */ public class ListaPreciosBean
/*  63:    */   extends PageControllerAS2
/*  64:    */ {
/*  65:    */   private static final long serialVersionUID = -1575305697743619318L;
/*  66:    */   @EJB
/*  67:    */   private ServicioListaPrecios servicioListaPrecios;
/*  68:    */   @EJB
/*  69:    */   private ServicioMoneda servicioMoneda;
/*  70:    */   @EJB
/*  71:    */   private ServicioProducto servicioProducto;
/*  72:    */   @EJB
/*  73:    */   private ServicioZona servicioZona;
/*  74:    */   @EJB
/*  75:    */   private ServicioMigracion servicioMigracion;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioCategoriaProducto servicioCategoriaProducto;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  80:    */   @EJB
/*  81:    */   private ServicioBodega servicioBodega;
/*  82:    */   private ListaPrecios listaPrecios;
/*  83:    */   private List<Moneda> listaMoneda;
/*  84:    */   private List<Zona> listaZona;
/*  85:    */   private LazyDataModel<ListaPrecios> listasPrecios;
/*  86:    */   private VersionListaPrecios versionListaPrecios;
/*  87:    */   private Producto[] productosSeleccionados;
/*  88:    */   private List<CategoriaProducto> listaCategoriaProductoSeleccionado;
/*  89:    */   private List<SubcategoriaProducto> listaSubcategoriaProductoSeleccionado;
/*  90:    */   private List<SelectItem> listaTipoListaPrecios;
/*  91:    */   private Date fechaDesde;
/*  92:    */   private Date fechaHasta;
/*  93:    */   private StreamedContent file;
/*  94:    */   private static final String TIPO_CONTENIDO = "application/xls";
/*  95:    */   private DataTable dataTableListaPrecios;
/*  96:    */   private DataTable dtListaVersionPrecios;
/*  97:    */   private DataTable dtDetalleVersionListaPrecios;
/*  98:    */   private List<SelectItem> listaPreciosItems;
/*  99:    */   private HashMap<Integer, Producto> hmListaPrecios;
/* 100:    */   private boolean saldoMayorCero;
/* 101:    */   
/* 102:    */   @PostConstruct
/* 103:    */   public void init()
/* 104:    */   {
/* 105:128 */     this.listasPrecios = new LazyDataModel()
/* 106:    */     {
/* 107:    */       private static final long serialVersionUID = 6970370289076934962L;
/* 108:    */       
/* 109:    */       public List<ListaPrecios> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 110:    */       {
/* 111:135 */         List<ListaPrecios> lista = new ArrayList();
/* 112:136 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 113:    */         
/* 114:138 */         lista = ListaPreciosBean.this.servicioListaPrecios.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 115:139 */         ListaPreciosBean.this.listasPrecios.setRowCount(ListaPreciosBean.this.servicioListaPrecios.contarPorCriterio(filters));
/* 116:    */         
/* 117:141 */         return lista;
/* 118:    */       }
/* 119:    */     };
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String editar()
/* 123:    */   {
/* 124:155 */     if (this.listaPrecios.getIdListaPrecios() != 0)
/* 125:    */     {
/* 126:156 */       this.listaPrecios = this.servicioListaPrecios.cargarDetalle(this.listaPrecios.getId());
/* 127:157 */       setEditado(true);
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:159 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 132:    */     }
/* 133:162 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String guardar()
/* 137:    */   {
/* 138:    */     try
/* 139:    */     {
/* 140:173 */       this.servicioListaPrecios.guardar(this.listaPrecios);
/* 141:174 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 142:175 */       limpiar();
/* 143:    */     }
/* 144:    */     catch (Exception e)
/* 145:    */     {
/* 146:177 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 147:178 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 148:    */     }
/* 149:181 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void cargaProductoListaPreciosVigentes()
/* 153:    */   {
/* 154:186 */     DetalleVersionListaPrecios d = (DetalleVersionListaPrecios)this.dtDetalleVersionListaPrecios.getRowData();
/* 155:188 */     if (d.getVersionListaPrecios().getIdVersionListaPrecios() != 0)
/* 156:    */     {
/* 157:189 */       List<VersionListaPrecios> listaVersionListaPrecios = this.servicioListaPrecios.getZonaListaPreciosProductoNuevo(
/* 158:190 */         AppUtil.getOrganizacion().getIdOrganizacion(), d.getVersionListaPrecios().getListaPrecios().getIdListaPrecios(), d
/* 159:191 */         .getProducto().getIdProducto());
/* 160:192 */       List<DetalleVersionListaPrecios> listaDetalleVersionListaPrecios = new ArrayList();
/* 161:193 */       listaDetalleVersionListaPrecios.add(d);
/* 162:194 */       for (VersionListaPrecios versionListaPrecios : listaVersionListaPrecios) {
/* 163:195 */         if ((d.getVersionListaPrecios().getZona() == null) || (!d.getVersionListaPrecios().getZona().equals(versionListaPrecios.getZona())))
/* 164:    */         {
/* 165:196 */           DetalleVersionListaPrecios dvlp = crearDetalleVersionListaPrecios();
/* 166:197 */           dvlp.setProducto(d.getProducto());
/* 167:198 */           dvlp.setVersionListaPrecios(versionListaPrecios);
/* 168:199 */           dvlp.setPrecioUnitario(d.getPrecioUnitario());
/* 169:200 */           dvlp.setPrecioUnitarioClienteFinal(d.getPrecioUnitarioClienteFinal());
/* 170:201 */           listaDetalleVersionListaPrecios.add(dvlp);
/* 171:    */         }
/* 172:    */       }
/* 173:204 */       this.servicioListaPrecios.guardarListaDetalleVersionListaPrecios(listaDetalleVersionListaPrecios);
/* 174:    */       
/* 175:206 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 176:207 */       limpiar();
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:210 */       addErrorMessage(getLanguageController().getMensaje("msg_error_version_lista_precios_nueva"));
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String eliminar()
/* 185:    */   {
/* 186:    */     try
/* 187:    */     {
/* 188:222 */       this.servicioListaPrecios.eliminar(this.listaPrecios);
/* 189:223 */       cargarDatos();
/* 190:224 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 191:    */     }
/* 192:    */     catch (Exception e)
/* 193:    */     {
/* 194:226 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 195:227 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 196:    */     }
/* 197:230 */     return "";
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String agregarVersionListaPrecios()
/* 201:    */   {
/* 202:239 */     VersionListaPrecios versionListaPrecios = new VersionListaPrecios();
/* 203:240 */     versionListaPrecios.setListaPrecios(getListaPrecios());
/* 204:241 */     versionListaPrecios.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 205:242 */     versionListaPrecios.setIdSucursal(AppUtil.getSucursal().getId());
/* 206:243 */     getListaPrecios().getVersionesListaPrecios().add(versionListaPrecios);
/* 207:244 */     return "";
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String limpiar()
/* 211:    */   {
/* 212:254 */     this.listaPrecios = new ListaPrecios();
/* 213:255 */     this.listaPrecios.setMoneda(new Moneda());
/* 214:256 */     this.listaPrecios.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 215:257 */     this.versionListaPrecios = null;
/* 216:    */     
/* 217:259 */     setEditado(false);
/* 218:260 */     return "";
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<ListaPrecios> getObtenerListasPreciosCombo()
/* 222:    */   {
/* 223:264 */     List<ListaPrecios> lista = new ArrayList();
/* 224:265 */     String sortField = "codigo";
/* 225:266 */     lista = this.servicioListaPrecios.obtenerListaCombo(sortField, true, null);
/* 226:267 */     return lista;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String cargarDatos()
/* 230:    */   {
/* 231:277 */     return "";
/* 232:    */   }
/* 233:    */   
/* 234:    */   public String eliminarVersionListaPrecios()
/* 235:    */   {
/* 236:286 */     VersionListaPrecios versionListaPrecios = (VersionListaPrecios)this.dtListaVersionPrecios.getRowData();
/* 237:287 */     versionListaPrecios.setEliminado(true);
/* 238:288 */     for (DetalleVersionListaPrecios detalleVersionListaPrecios : versionListaPrecios.getDetalleVersionesListaPrecios()) {
/* 239:289 */       detalleVersionListaPrecios.setEliminado(true);
/* 240:    */     }
/* 241:291 */     return "";
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String agregarDetalleVersionListaPrecios()
/* 245:    */   {
/* 246:309 */     this.versionListaPrecios.getDetalleVersionesListaPrecios().add(crearDetalleVersionListaPrecios());
/* 247:310 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   private DetalleVersionListaPrecios crearDetalleVersionListaPrecios()
/* 251:    */   {
/* 252:314 */     DetalleVersionListaPrecios detalleVersionListaPrecios = new DetalleVersionListaPrecios();
/* 253:315 */     detalleVersionListaPrecios.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 254:316 */     detalleVersionListaPrecios.setIdSucursal(AppUtil.getSucursal().getId());
/* 255:317 */     detalleVersionListaPrecios.setVersionListaPrecios(this.versionListaPrecios);
/* 256:318 */     detalleVersionListaPrecios.setProducto(new Producto());
/* 257:319 */     return detalleVersionListaPrecios;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void cargarProducto(Producto producto)
/* 261:    */   {
/* 262:323 */     if (!this.hmListaPrecios.containsKey(Integer.valueOf(producto.getId())))
/* 263:    */     {
/* 264:324 */       DetalleVersionListaPrecios detalleVersionListaPrecios = new DetalleVersionListaPrecios();
/* 265:325 */       detalleVersionListaPrecios.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 266:326 */       detalleVersionListaPrecios.setIdSucursal(AppUtil.getSucursal().getId());
/* 267:327 */       detalleVersionListaPrecios.setVersionListaPrecios(getVersionListaPrecios());
/* 268:328 */       detalleVersionListaPrecios.setProducto(producto);
/* 269:329 */       getVersionListaPrecios().getDetalleVersionesListaPrecios().add(detalleVersionListaPrecios);
/* 270:330 */       this.hmListaPrecios.put(Integer.valueOf(producto.getId()), producto);
/* 271:    */     }
/* 272:    */     else
/* 273:    */     {
/* 274:332 */       addErrorMessage(getLanguageController().getMensaje("msg_info_ya_existe_producto"));
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String eliminarDetalleVersionListaPrecios()
/* 279:    */   {
/* 280:337 */     DetalleVersionListaPrecios dvlp = (DetalleVersionListaPrecios)this.dtDetalleVersionListaPrecios.getRowData();
/* 281:338 */     dvlp.setEliminado(true);
/* 282:339 */     this.dtDetalleVersionListaPrecios.reset();
/* 283:340 */     return "";
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 287:    */   {
/* 288:349 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 289:350 */     DetalleVersionListaPrecios detalleVersionListaPrecios = (DetalleVersionListaPrecios)this.dtDetalleVersionListaPrecios.getRowData();
/* 290:    */     try
/* 291:    */     {
/* 292:355 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 293:356 */       detalleVersionListaPrecios.setProducto(producto);
/* 294:    */     }
/* 295:    */     catch (Exception ex)
/* 296:    */     {
/* 297:358 */       LOG.error("Error al cargar el producto por codigo: " + ex);
/* 298:359 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void aceptarDetalle(ActionEvent event)
/* 303:    */   {
/* 304:364 */     RequestContext context = RequestContext.getCurrentInstance();
/* 305:365 */     boolean loggedIn = false;
/* 306:366 */     context.addCallbackParam("loggedIn", Boolean.valueOf(loggedIn));
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void listarPrecios()
/* 310:    */   {
/* 311:371 */     this.hmListaPrecios = new HashMap();
/* 312:372 */     for (DetalleVersionListaPrecios listaPrecios : this.versionListaPrecios.getDetalleVersionesListaPrecios()) {
/* 313:373 */       this.hmListaPrecios.put(Integer.valueOf(listaPrecios.getProducto().getId()), listaPrecios.getProducto());
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void onRowSelect(SelectEvent event)
/* 318:    */   {
/* 319:382 */     ListaPrecios listaPrecios = (ListaPrecios)event.getObject();
/* 320:383 */     setListaPrecios(listaPrecios);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 324:    */   {
/* 325:387 */     List<CategoriaProducto> lista = new ArrayList();
/* 326:    */     
/* 327:389 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 328:390 */     filters.put("nombre", "%" + consulta.trim());
/* 329:    */     
/* 330:392 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 331:    */     
/* 332:394 */     return lista;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 336:    */   {
/* 337:398 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 338:    */     
/* 339:400 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 340:    */     int num;
/* 341:402 */     if ((this.listaCategoriaProductoSeleccionado != null) && (!this.listaCategoriaProductoSeleccionado.isEmpty()))
/* 342:    */     {
/* 343:403 */       num = 1;
/* 344:404 */       for (CategoriaProducto cp : this.listaCategoriaProductoSeleccionado)
/* 345:    */       {
/* 346:405 */         filters.put("OR~id~0" + num + "~categoriaProducto.idCategoriaProducto", String.valueOf(cp.getId()));
/* 347:406 */         num++;
/* 348:    */       }
/* 349:    */     }
/* 350:410 */     filters.put("nombre", "%" + consulta.trim());
/* 351:    */     
/* 352:412 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 353:    */     
/* 354:414 */     return lista;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public ServicioListaPrecios getServicioListaPreciosBean()
/* 358:    */   {
/* 359:419 */     return this.servicioListaPrecios;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setServicioListaPreciosBean(ServicioListaPrecios servicioListaPrecios)
/* 363:    */   {
/* 364:423 */     this.servicioListaPrecios = servicioListaPrecios;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public ListaPrecios getListaPrecios()
/* 368:    */   {
/* 369:427 */     if (this.listaPrecios == null) {
/* 370:428 */       this.listaPrecios = new ListaPrecios();
/* 371:    */     }
/* 372:431 */     return this.listaPrecios;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setListaPrecios(ListaPrecios listaPrecios)
/* 376:    */   {
/* 377:435 */     if (listaPrecios != null) {
/* 378:436 */       this.listaPrecios = listaPrecios;
/* 379:    */     }
/* 380:    */   }
/* 381:    */   
/* 382:    */   public DataTable getDataTableListaPrecios()
/* 383:    */   {
/* 384:440 */     return this.dataTableListaPrecios;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setDataTableListaPrecios(DataTable dataTableListaPrecios)
/* 388:    */   {
/* 389:444 */     this.dataTableListaPrecios = dataTableListaPrecios;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public List<SelectItem> getListaPreciosItems()
/* 393:    */   {
/* 394:448 */     return this.listaPreciosItems;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setListaPreciosItems(List<SelectItem> listaPreciosItems)
/* 398:    */   {
/* 399:452 */     this.listaPreciosItems = listaPreciosItems;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public List<Moneda> getListaMoneda()
/* 403:    */   {
/* 404:461 */     if (this.listaMoneda == null) {
/* 405:462 */       this.listaMoneda = this.servicioMoneda.obtenerListaCombo("codigo", true, null);
/* 406:    */     }
/* 407:464 */     return this.listaMoneda;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setListaMoneda(List<Moneda> listaMoneda)
/* 411:    */   {
/* 412:474 */     this.listaMoneda = listaMoneda;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public List<VersionListaPrecios> getListaVersionListaPrecios()
/* 416:    */   {
/* 417:483 */     List<VersionListaPrecios> lista = new ArrayList();
/* 418:484 */     for (VersionListaPrecios versionListaPrecios : this.listaPrecios.getVersionesListaPrecios()) {
/* 419:485 */       if (!versionListaPrecios.isEliminado()) {
/* 420:486 */         lista.add(versionListaPrecios);
/* 421:    */       }
/* 422:    */     }
/* 423:489 */     return lista;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public List<DetalleVersionListaPrecios> getListaDetalleVersionListaPrecios()
/* 427:    */   {
/* 428:498 */     List<DetalleVersionListaPrecios> lista = new ArrayList();
/* 429:499 */     if (this.versionListaPrecios != null) {
/* 430:500 */       for (DetalleVersionListaPrecios detalleVersionListaPrecios : this.versionListaPrecios.getDetalleVersionesListaPrecios()) {
/* 431:501 */         if (!detalleVersionListaPrecios.isEliminado()) {
/* 432:502 */           lista.add(detalleVersionListaPrecios);
/* 433:    */         }
/* 434:    */       }
/* 435:    */     }
/* 436:506 */     return lista;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public DataTable getDtListaVersionPrecios()
/* 440:    */   {
/* 441:515 */     return this.dtListaVersionPrecios;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setDtListaVersionPrecios(DataTable dtListaVersionPrecios)
/* 445:    */   {
/* 446:525 */     this.dtListaVersionPrecios = dtListaVersionPrecios;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public LazyDataModel<ListaPrecios> getListasPrecios()
/* 450:    */   {
/* 451:534 */     return this.listasPrecios;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setListasPrecios(LazyDataModel<ListaPrecios> listasPrecios)
/* 455:    */   {
/* 456:544 */     this.listasPrecios = listasPrecios;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public VersionListaPrecios getVersionListaPrecios()
/* 460:    */   {
/* 461:553 */     return this.versionListaPrecios;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setVersionListaPrecios(VersionListaPrecios versionListaPrecios)
/* 465:    */   {
/* 466:563 */     this.versionListaPrecios = versionListaPrecios;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public DataTable getDtDetalleVersionListaPrecios()
/* 470:    */   {
/* 471:572 */     return this.dtDetalleVersionListaPrecios;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setDtDetalleVersionListaPrecios(DataTable dtDetalleVersionListaPrecios)
/* 475:    */   {
/* 476:582 */     this.dtDetalleVersionListaPrecios = dtDetalleVersionListaPrecios;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public Producto[] getProductosSeleccionados()
/* 480:    */   {
/* 481:591 */     return this.productosSeleccionados;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/* 485:    */   {
/* 486:601 */     this.productosSeleccionados = productosSeleccionados;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public List<Zona> getListaZona()
/* 490:    */   {
/* 491:610 */     if (this.listaZona == null) {
/* 492:611 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 493:    */     }
/* 494:613 */     return this.listaZona;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void setListaZona(List<Zona> listaZona)
/* 498:    */   {
/* 499:623 */     this.listaZona = listaZona;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public Date getFechaDesde()
/* 503:    */   {
/* 504:632 */     return this.fechaDesde;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void setFechaDesde(Date fechaDesde)
/* 508:    */   {
/* 509:642 */     this.fechaDesde = fechaDesde;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public Date getFechaHasta()
/* 513:    */   {
/* 514:651 */     return this.fechaHasta;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void setFechaHasta(Date fechaHasta)
/* 518:    */   {
/* 519:661 */     this.fechaHasta = fechaHasta;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public String migrarListaPrecios(FileUploadEvent event)
/* 523:    */   {
/* 524:    */     try
/* 525:    */     {
/* 526:667 */       String fileName = "migracion_lista_precios" + event.getFile().getFileName();
/* 527:668 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 528:669 */       this.servicioMigracion.migracionVersionListaPrecios(this.fechaDesde, this.listaPrecios.getId(), fileName, input, 2, 
/* 529:670 */         AppUtil.getOrganizacion().getIdOrganizacion(), isIndicadorListaPrecioPorZona());
/* 530:671 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 531:    */     }
/* 532:    */     catch (ExcepcionAS2 e)
/* 533:    */     {
/* 534:674 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 535:675 */       LOG.error(e);
/* 536:    */     }
/* 537:    */     catch (Exception e)
/* 538:    */     {
/* 539:677 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 540:678 */       LOG.error(e);
/* 541:    */     }
/* 542:680 */     return null;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public String seleccionar()
/* 546:    */   {
/* 547:684 */     this.listaPrecios = ((ListaPrecios)this.dataTableListaPrecios.getRowData());
/* 548:685 */     return "";
/* 549:    */   }
/* 550:    */   
/* 551:    */   public StreamedContent getFile()
/* 552:    */   {
/* 553:689 */     this.file = generarPlantilla();
/* 554:690 */     RequestContext.getCurrentInstance().execute("dialogFiltrosFile.hide()");
/* 555:691 */     RequestContext.getCurrentInstance().update(":form:panelListado");
/* 556:692 */     return this.file;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public StreamedContent generarPlantilla()
/* 560:    */   {
/* 561:    */     try
/* 562:    */     {
/* 563:700 */       this.listaPrecios = this.servicioListaPrecios.obtenerListaPreciosVigente(this.listaPrecios.getIdListaPrecios());
/* 564:701 */       boolean versionesActivas = false;
/* 565:702 */       Map<String, BigDecimal> hmListaPrecio = new HashMap();
/* 566:703 */       String idZona = "";
/* 567:704 */       String codigo = "";
/* 568:705 */       boolean zonaNull = false;
/* 569:706 */       for (VersionListaPrecios versionListaPrecios : this.listaPrecios.getVersionesListaPrecios())
/* 570:    */       {
/* 571:707 */         if (versionListaPrecios.getZona() == null)
/* 572:    */         {
/* 573:708 */           zonaNull = true;
/* 574:709 */           idZona = "0";
/* 575:    */         }
/* 576:    */         else
/* 577:    */         {
/* 578:711 */           idZona = String.valueOf(versionListaPrecios.getZona().getId());
/* 579:    */         }
/* 580:713 */         for (DetalleVersionListaPrecios dvlp : versionListaPrecios.getDetalleVersionesListaPrecios())
/* 581:    */         {
/* 582:714 */           codigo = idZona + "~" + dvlp.getProducto().getIdProducto();
/* 583:715 */           hmListaPrecio.put(codigo, dvlp.getPrecioUnitario());
/* 584:    */         }
/* 585:717 */         if ((versionListaPrecios.isActivo()) && (!versionesActivas)) {
/* 586:718 */           versionesActivas = true;
/* 587:    */         }
/* 588:    */       }
/* 589:722 */       if (versionesActivas)
/* 590:    */       {
/* 591:724 */         Vector v = new Vector();
/* 592:    */         
/* 593:    */ 
/* 594:727 */         String cadenaCabecera = "";
/* 595:728 */         String cadenaCodigoZona = "";
/* 596:729 */         String cadenaNombreZona = "";
/* 597:730 */         HashMap<String, String> filtersZona = new HashMap();
/* 598:731 */         filtersZona.put("activo", "true");
/* 599:732 */         List<Zona> listaZonas = new ArrayList();
/* 600:    */         Zona z;
/* 601:734 */         if (zonaNull)
/* 602:    */         {
/* 603:735 */           z = new Zona();
/* 604:736 */           z.setId(0);
/* 605:737 */           z.setCodigo("0");
/* 606:738 */           z.setNombre("Sin Zona");
/* 607:739 */           z.setActivo(true);
/* 608:740 */           listaZonas.add(z);
/* 609:    */         }
/* 610:742 */         if (isIndicadorListaPrecioPorZona()) {
/* 611:743 */           listaZonas.addAll(this.servicioZona.obtenerListaCombo("nombre", true, filtersZona));
/* 612:    */         }
/* 613:745 */         for (Zona zona : listaZonas)
/* 614:    */         {
/* 615:746 */           cadenaCabecera = cadenaCabecera + "-,";
/* 616:747 */           cadenaCodigoZona = cadenaCodigoZona + zona.getId() + ",";
/* 617:748 */           cadenaNombreZona = cadenaNombreZona + zona.getNombre() + ",";
/* 618:    */         }
/* 619:751 */         if (listaZonas.size() > 0)
/* 620:    */         {
/* 621:752 */           cadenaCabecera = cadenaCabecera.substring(0, cadenaCabecera.length() - 1);
/* 622:753 */           cadenaCodigoZona = cadenaCodigoZona.substring(0, cadenaCodigoZona.length() - 1);
/* 623:754 */           cadenaNombreZona = cadenaNombreZona.substring(0, cadenaNombreZona.length() - 1);
/* 624:    */         }
/* 625:758 */         v.addElement("-,-,-,-,-," + cadenaCabecera);
/* 626:759 */         v.addElement("-,-,-,-,-," + cadenaCodigoZona);
/* 627:760 */         v.addElement("Codigo,Codigo_Alterno,Categoria,Subcategoria,Producto," + cadenaNombreZona);
/* 628:    */         
/* 629:    */ 
/* 630:763 */         HashMap<String, String> filters = null;
/* 631:764 */         List<Bodega> listaBodega = null;
/* 632:765 */         Set<Integer> hmProductoSaldoMayorCero = null;
/* 633:767 */         if (this.saldoMayorCero)
/* 634:    */         {
/* 635:769 */           hmProductoSaldoMayorCero = new HashSet();
/* 636:    */           
/* 637:771 */           filters = new HashMap();
/* 638:772 */           filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 639:773 */           listaBodega = this.servicioBodega.obtenerListaCombo("codigo", true, filters);
/* 640:775 */           for (Bodega bodega : listaBodega) {
/* 641:776 */             for (localIterator4 = this.servicioProducto.obtenerProductosConSaldoBodega(bodega, new Date(), false, true).iterator(); localIterator4.hasNext();)
/* 642:    */             {
/* 643:776 */               sp = (SaldoProducto)localIterator4.next();
/* 644:777 */               if (sp.getSaldo().compareTo(BigDecimal.ZERO) == 1) {
/* 645:778 */                 hmProductoSaldoMayorCero.add(Integer.valueOf(sp.getProducto().getIdProducto()));
/* 646:    */               }
/* 647:    */             }
/* 648:    */           }
/* 649:    */         }
/* 650:    */         Iterator localIterator4;
/* 651:    */         SaldoProducto sp;
/* 652:785 */         String[] cadenaZona = cadenaNombreZona.split(",");
/* 653:786 */         String preciosActuales = "";
/* 654:787 */         filters = new HashMap();
/* 655:789 */         if ((this.listaPrecios.isIndicadorVenta()) || (this.listaPrecios.isIndicadorImpresionEtiqueta())) {
/* 656:790 */           filters.put("indicadorVenta", "true");
/* 657:    */         }
/* 658:792 */         if (this.listaPrecios.isIndicadorCompra()) {
/* 659:793 */           filters.put("indicadorCompra", "true");
/* 660:    */         }
/* 661:795 */         filters.put("activo", "true");
/* 662:    */         int num;
/* 663:796 */         if ((this.listaSubcategoriaProductoSeleccionado != null) && (!this.listaSubcategoriaProductoSeleccionado.isEmpty()))
/* 664:    */         {
/* 665:797 */           num = 1;
/* 666:798 */           for (SubcategoriaProducto scp : this.listaSubcategoriaProductoSeleccionado)
/* 667:    */           {
/* 668:799 */             filters.put("OR~id~0" + num + "~subcategoriaProducto.idSubcategoriaProducto", String.valueOf(scp.getId()));
/* 669:800 */             num++;
/* 670:    */           }
/* 671:    */         }
/* 672:    */         int num;
/* 673:803 */         if ((this.listaCategoriaProductoSeleccionado != null) && (!this.listaCategoriaProductoSeleccionado.isEmpty()))
/* 674:    */         {
/* 675:804 */           num = 1;
/* 676:805 */           for (CategoriaProducto cp : this.listaCategoriaProductoSeleccionado)
/* 677:    */           {
/* 678:806 */             filters.put("OR~id~0" + num + "~subcategoriaProducto.categoriaProducto.idCategoriaProducto", String.valueOf(cp.getId()));
/* 679:807 */             num++;
/* 680:    */           }
/* 681:    */         }
/* 682:811 */         Object listaProductos = this.servicioProducto.obtenerListaCombo("codigo", true, filters);
/* 683:813 */         for (Producto producto : (List)listaProductos)
/* 684:    */         {
/* 685:814 */           boolean insertar = true;
/* 686:815 */           if (this.saldoMayorCero)
/* 687:    */           {
/* 688:816 */             insertar = hmProductoSaldoMayorCero.contains(Integer.valueOf(producto.getId()));
/* 689:817 */             if (insertar)
/* 690:    */             {
/* 691:819 */               preciosActuales = "n::0,";
/* 692:821 */               for (Zona zona : listaZonas)
/* 693:    */               {
/* 694:822 */                 codigo = zona.getIdZona() + "~" + producto.getIdProducto();
/* 695:823 */                 if (hmListaPrecio.containsKey(codigo)) {
/* 696:824 */                   preciosActuales = preciosActuales + "n::" + hmListaPrecio.get(codigo) + ",";
/* 697:    */                 } else {
/* 698:827 */                   preciosActuales = preciosActuales + "n::0,";
/* 699:    */                 }
/* 700:    */               }
/* 701:830 */               preciosActuales = preciosActuales.substring(4, preciosActuales.length() - 1);
/* 702:    */               
/* 703:832 */               v.addElement(producto.getCodigo() + "," + producto.getCodigoAlterno() + " ," + producto
/* 704:833 */                 .getSubcategoriaProducto().getCategoriaProducto().getNombre().replace(",", ";") + " ," + producto
/* 705:834 */                 .getSubcategoriaProducto().getNombre().replace(",", ";") + " ," + producto.getNombre().replace(",", ";") + " ," + preciosActuales);
/* 706:    */             }
/* 707:    */           }
/* 708:    */           else
/* 709:    */           {
/* 710:838 */             preciosActuales = "n::0,";
/* 711:840 */             for (Zona zona : listaZonas)
/* 712:    */             {
/* 713:841 */               codigo = zona.getIdZona() + "~" + producto.getIdProducto();
/* 714:842 */               if (hmListaPrecio.containsKey(codigo)) {
/* 715:843 */                 preciosActuales = preciosActuales + "n::" + hmListaPrecio.get(codigo) + ",";
/* 716:    */               } else {
/* 717:846 */                 preciosActuales = preciosActuales + "n::0,";
/* 718:    */               }
/* 719:    */             }
/* 720:849 */             preciosActuales = preciosActuales.substring(4, preciosActuales.length() - 1);
/* 721:    */             
/* 722:851 */             v.addElement(producto.getCodigo() + "," + producto.getCodigoAlterno() + " ," + producto
/* 723:852 */               .getSubcategoriaProducto().getCategoriaProducto().getNombre().replace(",", ";") + " ," + producto
/* 724:853 */               .getSubcategoriaProducto().getNombre().replace(",", ";") + " ," + producto.getNombre().replace(",", ";") + " ," + preciosActuales);
/* 725:    */           }
/* 726:    */         }
/* 727:859 */         String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "listaPrecios");
/* 728:860 */         String nombreArchivo = "ListaDePrecios.xls";
/* 729:    */         
/* 730:862 */         FuncionesUtiles.crearExcel(v, "ListaPrecios", rutaArchivo, nombreArchivo);
/* 731:863 */         this.file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 732:    */       }
/* 733:    */       else
/* 734:    */       {
/* 735:865 */         addErrorMessage(getLanguageController().getMensaje("msg_error_lista_precio_sin_versuin_activa"));
/* 736:866 */         this.file = null;
/* 737:    */       }
/* 738:    */     }
/* 739:    */     catch (Exception e)
/* 740:    */     {
/* 741:869 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 742:870 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 743:    */     }
/* 744:872 */     return this.file;
/* 745:    */   }
/* 746:    */   
/* 747:    */   public String indicadorVenta()
/* 748:    */   {
/* 749:876 */     this.listaPrecios.setIndicadorCompra(false);
/* 750:877 */     this.listaPrecios.setIndicadorImpresionEtiqueta(false);
/* 751:878 */     zonaNullIndicadorCompra();
/* 752:879 */     return "";
/* 753:    */   }
/* 754:    */   
/* 755:    */   public String indicadorCompra()
/* 756:    */   {
/* 757:883 */     this.listaPrecios.setIndicadorVenta(false);
/* 758:884 */     this.listaPrecios.setIndicadorImpresionEtiqueta(false);
/* 759:885 */     zonaNullIndicadorCompra();
/* 760:886 */     return "";
/* 761:    */   }
/* 762:    */   
/* 763:    */   public String indicadorImpresionEtiqueta()
/* 764:    */   {
/* 765:890 */     this.listaPrecios.setIndicadorCompra(false);
/* 766:891 */     this.listaPrecios.setIndicadorVenta(false);
/* 767:892 */     zonaNullIndicadorCompra();
/* 768:893 */     return "";
/* 769:    */   }
/* 770:    */   
/* 771:    */   public String zonaNullIndicadorCompra()
/* 772:    */   {
/* 773:897 */     if ((this.listaPrecios.isIndicadorCompra()) || (this.listaPrecios.isIndicadorImpresionEtiqueta())) {
/* 774:898 */       for (VersionListaPrecios vlp : this.listaPrecios.getVersionesListaPrecios()) {
/* 775:899 */         vlp.setZona(null);
/* 776:    */       }
/* 777:    */     }
/* 778:902 */     return "";
/* 779:    */   }
/* 780:    */   
/* 781:    */   public List<SelectItem> getListaTipoListaPrecios()
/* 782:    */   {
/* 783:909 */     if (this.listaTipoListaPrecios == null)
/* 784:    */     {
/* 785:910 */       this.listaTipoListaPrecios = new ArrayList();
/* 786:912 */       for (TipoListaPreciosEnum t : TipoListaPreciosEnum.values())
/* 787:    */       {
/* 788:913 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 789:914 */         this.listaTipoListaPrecios.add(item);
/* 790:    */       }
/* 791:    */     }
/* 792:918 */     return this.listaTipoListaPrecios;
/* 793:    */   }
/* 794:    */   
/* 795:    */   public void setListaTipoListaPrecios(List<SelectItem> listaTipoListaPrecios)
/* 796:    */   {
/* 797:926 */     this.listaTipoListaPrecios = listaTipoListaPrecios;
/* 798:    */   }
/* 799:    */   
/* 800:    */   public HashMap<Integer, Producto> getHmListaPrecios()
/* 801:    */   {
/* 802:933 */     if (this.hmListaPrecios == null) {
/* 803:934 */       this.hmListaPrecios = new HashMap();
/* 804:    */     }
/* 805:936 */     return this.hmListaPrecios;
/* 806:    */   }
/* 807:    */   
/* 808:    */   public void setHmListaPrecios(HashMap<Integer, Producto> hmListaPrecios)
/* 809:    */   {
/* 810:944 */     this.hmListaPrecios = hmListaPrecios;
/* 811:    */   }
/* 812:    */   
/* 813:    */   public List<CategoriaProducto> getListaCategoriaProductoSeleccionado()
/* 814:    */   {
/* 815:948 */     return this.listaCategoriaProductoSeleccionado;
/* 816:    */   }
/* 817:    */   
/* 818:    */   public void setListaCategoriaProductoSeleccionado(List<CategoriaProducto> listaCategoriaProductoSeleccionado)
/* 819:    */   {
/* 820:952 */     this.listaCategoriaProductoSeleccionado = listaCategoriaProductoSeleccionado;
/* 821:    */   }
/* 822:    */   
/* 823:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductoSeleccionado()
/* 824:    */   {
/* 825:956 */     return this.listaSubcategoriaProductoSeleccionado;
/* 826:    */   }
/* 827:    */   
/* 828:    */   public void setListaSubcategoriaProductoSeleccionado(List<SubcategoriaProducto> listaSubcategoriaProductoSeleccionado)
/* 829:    */   {
/* 830:960 */     this.listaSubcategoriaProductoSeleccionado = listaSubcategoriaProductoSeleccionado;
/* 831:    */   }
/* 832:    */   
/* 833:    */   public boolean isSaldoMayorCero()
/* 834:    */   {
/* 835:964 */     return this.saldoMayorCero;
/* 836:    */   }
/* 837:    */   
/* 838:    */   public void setSaldoMayorCero(boolean saldoMayorCero)
/* 839:    */   {
/* 840:968 */     this.saldoMayorCero = saldoMayorCero;
/* 841:    */   }
/* 842:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.ListaPreciosBean
 * JD-Core Version:    0.7.0.1
 */