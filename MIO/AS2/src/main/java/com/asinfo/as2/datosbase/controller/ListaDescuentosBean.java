/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*  10:    */ import com.asinfo.as2.entities.ListaDescuentos;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.SaldoProducto;
/*  14:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.VersionListaDescuentos;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.util.RutaArchivo;
/*  25:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  26:    */ import com.asinfo.as2.utils.JsfUtil;
/*  27:    */ import java.io.BufferedInputStream;
/*  28:    */ import java.io.InputStream;
/*  29:    */ import java.math.BigDecimal;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Date;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.HashSet;
/*  34:    */ import java.util.List;
/*  35:    */ import java.util.Map;
/*  36:    */ import java.util.Set;
/*  37:    */ import java.util.Vector;
/*  38:    */ import javax.annotation.PostConstruct;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.component.html.HtmlInputText;
/*  43:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  44:    */ import org.apache.log4j.Logger;
/*  45:    */ import org.primefaces.component.datatable.DataTable;
/*  46:    */ import org.primefaces.event.FileUploadEvent;
/*  47:    */ import org.primefaces.model.LazyDataModel;
/*  48:    */ import org.primefaces.model.SortOrder;
/*  49:    */ import org.primefaces.model.StreamedContent;
/*  50:    */ import org.primefaces.model.UploadedFile;
/*  51:    */ 
/*  52:    */ @ManagedBean
/*  53:    */ @ViewScoped
/*  54:    */ public class ListaDescuentosBean
/*  55:    */   extends PageControllerAS2
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = -6458772552568395980L;
/*  58:    */   @EJB
/*  59:    */   private ServicioListaDescuentos servicioListaDescuentos;
/*  60:    */   @EJB
/*  61:    */   private ServicioProducto servicioProducto;
/*  62:    */   @EJB
/*  63:    */   private ServicioMigracion servicioMigracion;
/*  64:    */   @EJB
/*  65:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  66:    */   @EJB
/*  67:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  68:    */   @EJB
/*  69:    */   private ServicioBodega servicioBodega;
/*  70:    */   private ListaDescuentos listaDescuentos;
/*  71:    */   private VersionListaDescuentos versionListaDescuentos;
/*  72:    */   private LazyDataModel<ListaDescuentos> listaListaDescuentos;
/*  73:    */   private Producto[] productosSeleccionados;
/*  74:    */   private StreamedContent file;
/*  75:    */   private static final String TIPO_CONTENIDO = "application/xls";
/*  76:    */   private List<CategoriaProducto> listaCategoriaProductoSeleccionado;
/*  77:    */   private List<SubcategoriaProducto> listaSubcategoriaProductoSeleccionado;
/*  78:    */   private List<DetalleListaDescuentos> listaDetalleListaDescuentos;
/*  79:    */   private List<DetalleListaDescuentos> listaDetalleListaDescuentosFilter;
/*  80:100 */   private HashMap<Integer, Producto> hmListaDescuentos = new HashMap();
/*  81:    */   private DataTable dtListaDescuentos;
/*  82:    */   private DataTable dtListaVersionDescuentos;
/*  83:    */   private DataTable dtDetalleListaDescuentos;
/*  84:    */   private DataTable dtDetalleVersionListaDescuentos;
/*  85:    */   private Date fechaDesde;
/*  86:    */   private boolean saldoMayorCero;
/*  87:    */   
/*  88:    */   @PostConstruct
/*  89:    */   public void init()
/*  90:    */   {
/*  91:114 */     this.listaListaDescuentos = new LazyDataModel()
/*  92:    */     {
/*  93:    */       private static final long serialVersionUID = 8298780092471798328L;
/*  94:    */       
/*  95:    */       public List<ListaDescuentos> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  96:    */       {
/*  97:121 */         List<ListaDescuentos> lista = new ArrayList();
/*  98:122 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  99:    */         
/* 100:124 */         lista = ListaDescuentosBean.this.servicioListaDescuentos.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 101:125 */         ListaDescuentosBean.this.listaListaDescuentos.setRowCount(ListaDescuentosBean.this.servicioListaDescuentos.contarPorCriterio(filters));
/* 102:    */         
/* 103:127 */         return lista;
/* 104:    */       }
/* 105:    */     };
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void actualizarIndicadorDescuentoPorProducto()
/* 109:    */   {
/* 110:134 */     if (!this.listaDescuentos.isIndicadorDescuentoPorProducto()) {
/* 111:135 */       for (VersionListaDescuentos vld : this.listaDescuentos.getListaVersionesListaDescuentos()) {
/* 112:136 */         vld.setEliminado(true);
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String editar()
/* 118:    */   {
/* 119:149 */     if ((this.listaDescuentos != null) && (this.listaDescuentos.getIdListaDescuentos() != 0))
/* 120:    */     {
/* 121:150 */       this.listaDescuentos = this.servicioListaDescuentos.cargarDetalle(this.listaDescuentos.getId());
/* 122:    */       
/* 123:152 */       cargaMapaDescuentos();
/* 124:    */       
/* 125:154 */       setEditado(true);
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 130:    */     }
/* 131:159 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String guardar()
/* 135:    */   {
/* 136:    */     try
/* 137:    */     {
/* 138:170 */       this.servicioListaDescuentos.guardar(this.listaDescuentos);
/* 139:171 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 140:172 */       limpiar();
/* 141:    */     }
/* 142:    */     catch (AS2Exception e)
/* 143:    */     {
/* 144:174 */       JsfUtil.addErrorMessage(e, "");
/* 145:175 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 146:    */     }
/* 147:    */     catch (Exception e)
/* 148:    */     {
/* 149:177 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 150:178 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 151:    */     }
/* 152:181 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String eliminar()
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:192 */       this.servicioListaDescuentos.eliminar(this.listaDescuentos);
/* 160:193 */       cargarDatos();
/* 161:194 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 162:    */     }
/* 163:    */     catch (Exception e)
/* 164:    */     {
/* 165:196 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 166:197 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 167:    */     }
/* 168:200 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String limpiar()
/* 172:    */   {
/* 173:210 */     this.listaDescuentos = new ListaDescuentos();
/* 174:211 */     this.listaDescuentos.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 175:212 */     this.listaDetalleListaDescuentosFilter = null;
/* 176:213 */     this.listaDetalleListaDescuentos = null;
/* 177:    */     
/* 178:215 */     setearDTDescuentos();
/* 179:216 */     setEditado(false);
/* 180:217 */     return "";
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void setearDTDescuentos()
/* 184:    */   {
/* 185:221 */     if (this.dtDetalleListaDescuentos != null) {
/* 186:222 */       this.dtDetalleListaDescuentos.reset();
/* 187:    */     }
/* 188:225 */     this.listaDetalleListaDescuentosFilter = null;
/* 189:226 */     this.listaDetalleListaDescuentos = null;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String cargarDatos()
/* 193:    */   {
/* 194:236 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String eliminarDetalle(DetalleListaDescuentos detalleListaDescuentos)
/* 198:    */   {
/* 199:245 */     detalleListaDescuentos.setEliminado(true);
/* 200:246 */     setearDTDescuentos();
/* 201:247 */     return "";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 205:    */   {
/* 206:269 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 207:270 */     DetalleListaDescuentos detalleListaDescuentos = (DetalleListaDescuentos)this.dtDetalleListaDescuentos.getRowData();
/* 208:    */     try
/* 209:    */     {
/* 210:275 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 211:276 */       detalleListaDescuentos.setProducto(producto);
/* 212:    */     }
/* 213:    */     catch (Exception ex)
/* 214:    */     {
/* 215:278 */       LOG.error("Error al cargar el producto por codigo: " + ex);
/* 216:279 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void cargaMapaDescuentos()
/* 221:    */   {
/* 222:285 */     this.hmListaDescuentos = new HashMap();
/* 223:286 */     if (this.versionListaDescuentos != null) {
/* 224:287 */       for (DetalleListaDescuentos detalleListaDescuentos : this.versionListaDescuentos.getListaDetalleListaDescuentos()) {
/* 225:288 */         this.hmListaDescuentos.put(Integer.valueOf(detalleListaDescuentos.getProducto().getId()), detalleListaDescuentos.getProducto());
/* 226:    */       }
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String agregarVersionListaDescuentos()
/* 231:    */   {
/* 232:299 */     VersionListaDescuentos versionListaDescuentos = new VersionListaDescuentos();
/* 233:300 */     versionListaDescuentos.setListaDescuentos(getListaDescuentos());
/* 234:301 */     versionListaDescuentos.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 235:302 */     versionListaDescuentos.setIdSucursal(AppUtil.getSucursal().getId());
/* 236:303 */     versionListaDescuentos.setActivo(true);
/* 237:304 */     getListaDescuentos().getListaVersionesListaDescuentos().add(versionListaDescuentos);
/* 238:305 */     return "";
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void listarDescuentos()
/* 242:    */   {
/* 243:310 */     this.hmListaDescuentos = new HashMap();
/* 244:311 */     for (DetalleListaDescuentos dvld : this.versionListaDescuentos.getListaDetalleListaDescuentos()) {
/* 245:312 */       this.hmListaDescuentos.put(Integer.valueOf(dvld.getProducto().getId()), dvld.getProducto());
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String eliminarVersionListaDescuentos()
/* 250:    */   {
/* 251:323 */     this.versionListaDescuentos = ((VersionListaDescuentos)this.dtListaVersionDescuentos.getRowData());
/* 252:324 */     this.versionListaDescuentos.setEliminado(true);
/* 253:325 */     for (DetalleListaDescuentos detalleVersionListaDescuentos : this.versionListaDescuentos.getListaDetalleListaDescuentos()) {
/* 254:326 */       detalleVersionListaDescuentos.setEliminado(true);
/* 255:    */     }
/* 256:328 */     return "";
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String agregarDetalleVersionListaDescuentos()
/* 260:    */   {
/* 261:337 */     this.versionListaDescuentos.getListaDetalleListaDescuentos().add(crearDetalleVersionListaDescuentos());
/* 262:338 */     return "";
/* 263:    */   }
/* 264:    */   
/* 265:    */   private DetalleListaDescuentos crearDetalleVersionListaDescuentos()
/* 266:    */   {
/* 267:342 */     DetalleListaDescuentos detalleListaDescuentos = new DetalleListaDescuentos();
/* 268:343 */     detalleListaDescuentos.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 269:344 */     detalleListaDescuentos.setIdSucursal(AppUtil.getSucursal().getId());
/* 270:345 */     detalleListaDescuentos.setVersionListaDescuentos(this.versionListaDescuentos);
/* 271:346 */     detalleListaDescuentos.setProducto(new Producto());
/* 272:347 */     return detalleListaDescuentos;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void eliminarDetalleVersionListaDescuentos()
/* 276:    */   {
/* 277:351 */     DetalleListaDescuentos dvld = (DetalleListaDescuentos)this.dtDetalleVersionListaDescuentos.getRowData();
/* 278:352 */     dvld.setEliminado(true);
/* 279:353 */     this.dtDetalleVersionListaDescuentos.reset();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void cargarProducto(Producto producto)
/* 283:    */   {
/* 284:357 */     if (!this.hmListaDescuentos.containsKey(Integer.valueOf(producto.getId())))
/* 285:    */     {
/* 286:358 */       DetalleListaDescuentos detalleListaDescuentos = new DetalleListaDescuentos();
/* 287:359 */       detalleListaDescuentos.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 288:360 */       detalleListaDescuentos.setIdSucursal(AppUtil.getSucursal().getId());
/* 289:361 */       detalleListaDescuentos.setVersionListaDescuentos(getVersionListaDescuentos());
/* 290:362 */       detalleListaDescuentos.setProducto(producto);
/* 291:363 */       detalleListaDescuentos.setVersionListaDescuentos(this.versionListaDescuentos);
/* 292:364 */       getVersionListaDescuentos().getListaDetalleListaDescuentos().add(detalleListaDescuentos);
/* 293:365 */       this.hmListaDescuentos.put(Integer.valueOf(producto.getId()), producto);
/* 294:    */     }
/* 295:    */     else
/* 296:    */     {
/* 297:367 */       addErrorMessage(getLanguageController().getMensaje("msg_info_ya_existe_producto"));
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<DetalleListaDescuentos> getListaDetalleListaDescuentos()
/* 302:    */   {
/* 303:377 */     if (this.versionListaDescuentos != null)
/* 304:    */     {
/* 305:378 */       this.listaDetalleListaDescuentos = new ArrayList();
/* 306:379 */       for (DetalleListaDescuentos detalleListaDescuentos : this.versionListaDescuentos.getListaDetalleListaDescuentos()) {
/* 307:380 */         if (!detalleListaDescuentos.isEliminado()) {
/* 308:381 */           this.listaDetalleListaDescuentos.add(detalleListaDescuentos);
/* 309:    */         }
/* 310:    */       }
/* 311:    */     }
/* 312:385 */     return this.listaDetalleListaDescuentos;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 316:    */   {
/* 317:389 */     List<CategoriaProducto> lista = new ArrayList();
/* 318:    */     
/* 319:391 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 320:392 */     filters.put("nombre", "%" + consulta.trim());
/* 321:    */     
/* 322:394 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 323:    */     
/* 324:396 */     return lista;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 328:    */   {
/* 329:400 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 330:    */     
/* 331:402 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 332:    */     int num;
/* 333:404 */     if ((this.listaCategoriaProductoSeleccionado != null) && (!this.listaCategoriaProductoSeleccionado.isEmpty()))
/* 334:    */     {
/* 335:405 */       num = 1;
/* 336:406 */       for (CategoriaProducto cp : this.listaCategoriaProductoSeleccionado)
/* 337:    */       {
/* 338:407 */         filters.put("OR~id~0" + num + "~categoriaProducto.idCategoriaProducto", String.valueOf(cp.getId()));
/* 339:408 */         num++;
/* 340:    */       }
/* 341:    */     }
/* 342:412 */     filters.put("nombre", "%" + consulta.trim());
/* 343:    */     
/* 344:414 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 345:    */     
/* 346:416 */     return lista;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public DataTable getDtListaDescuentos()
/* 350:    */   {
/* 351:425 */     return this.dtListaDescuentos;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setDtListaDescuentos(DataTable dtListaDescuentos)
/* 355:    */   {
/* 356:435 */     this.dtListaDescuentos = dtListaDescuentos;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public LazyDataModel<ListaDescuentos> getListasPrecios()
/* 360:    */   {
/* 361:444 */     return this.listaListaDescuentos;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setListasPrecios(LazyDataModel<ListaDescuentos> listaListaDescuentos)
/* 365:    */   {
/* 366:454 */     this.listaListaDescuentos = listaListaDescuentos;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String seleccionar()
/* 370:    */   {
/* 371:458 */     this.listaDescuentos = ((ListaDescuentos)this.dtListaDescuentos.getRowData());
/* 372:459 */     return "";
/* 373:    */   }
/* 374:    */   
/* 375:    */   public ListaDescuentos getListaDescuentos()
/* 376:    */   {
/* 377:463 */     return this.listaDescuentos;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setListaDescuentos(ListaDescuentos listaDescuentos)
/* 381:    */   {
/* 382:467 */     if (listaDescuentos != null) {
/* 383:468 */       this.listaDescuentos = listaDescuentos;
/* 384:    */     }
/* 385:    */   }
/* 386:    */   
/* 387:    */   public LazyDataModel<ListaDescuentos> getListaListaDescuentos()
/* 388:    */   {
/* 389:472 */     return this.listaListaDescuentos;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setListaListaDescuentos(LazyDataModel<ListaDescuentos> listaListaDescuentos)
/* 393:    */   {
/* 394:476 */     this.listaListaDescuentos = listaListaDescuentos;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public Producto[] getProductosSeleccionados()
/* 398:    */   {
/* 399:480 */     return this.productosSeleccionados;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/* 403:    */   {
/* 404:484 */     this.productosSeleccionados = productosSeleccionados;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public DataTable getDtDetalleListaDescuentos()
/* 408:    */   {
/* 409:488 */     return this.dtDetalleListaDescuentos;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setDtDetalleListaDescuentos(DataTable dtDetalleListaDescuentos)
/* 413:    */   {
/* 414:492 */     this.dtDetalleListaDescuentos = dtDetalleListaDescuentos;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public List<DetalleListaDescuentos> getListaDetalleListaDescuentosFilter()
/* 418:    */   {
/* 419:499 */     return this.listaDetalleListaDescuentosFilter;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setListaDetalleListaDescuentosFilter(List<DetalleListaDescuentos> listaDetalleListaDescuentosFilter)
/* 423:    */   {
/* 424:507 */     this.listaDetalleListaDescuentosFilter = listaDetalleListaDescuentosFilter;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public StreamedContent getFile()
/* 428:    */   {
/* 429:512 */     this.file = generarPlantilla();
/* 430:513 */     return this.file;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public StreamedContent generarPlantilla()
/* 434:    */   {
/* 435:    */     try
/* 436:    */     {
/* 437:520 */       this.listaDescuentos = this.servicioListaDescuentos.obtenerListaDescuentosVigente(this.listaDescuentos.getId());
/* 438:521 */       Map<Integer, BigDecimal> hmDescuentosProductos = new HashMap();
/* 439:523 */       for (VersionListaDescuentos vld : this.listaDescuentos.getListaVersionesListaDescuentos()) {
/* 440:524 */         for (DetalleListaDescuentos ldd : vld.getListaDetalleListaDescuentos()) {
/* 441:525 */           hmDescuentosProductos.put(Integer.valueOf(ldd.getProducto().getId()), ldd.getPorcentajeDescuentoMaximo());
/* 442:    */         }
/* 443:    */       }
/* 444:530 */       Vector v = new Vector();
/* 445:    */       
/* 446:    */ 
/* 447:533 */       v.addElement("Codigo,Codigo_Alterno,Categoria,Subcategoria,Producto,Descuento_Maximo");
/* 448:    */       
/* 449:    */ 
/* 450:536 */       HashMap<String, String> filters = null;
/* 451:537 */       Object listaBodega = null;
/* 452:538 */       Set<Integer> hmProductoSaldoMayorCero = null;
/* 453:540 */       if (this.saldoMayorCero)
/* 454:    */       {
/* 455:542 */         hmProductoSaldoMayorCero = new HashSet();
/* 456:    */         
/* 457:544 */         filters = new HashMap();
/* 458:545 */         filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 459:546 */         listaBodega = this.servicioBodega.obtenerListaCombo("codigo", true, filters);
/* 460:548 */         for (Bodega bodega : (List)listaBodega) {
/* 461:549 */           for (SaldoProducto sp : this.servicioProducto.obtenerProductosConSaldoBodega(bodega, new Date(), false, true)) {
/* 462:550 */             if (sp.getSaldo().compareTo(BigDecimal.ZERO) == 1) {
/* 463:551 */               hmProductoSaldoMayorCero.add(Integer.valueOf(sp.getProducto().getIdProducto()));
/* 464:    */             }
/* 465:    */           }
/* 466:    */         }
/* 467:    */       }
/* 468:558 */       String descuentosActuales = "";
/* 469:559 */       filters = new HashMap();
/* 470:560 */       filters.put("indicadorVenta", "true");
/* 471:561 */       filters.put("activo", "true");
/* 472:562 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 473:    */       int num;
/* 474:563 */       if ((this.listaSubcategoriaProductoSeleccionado != null) && (!this.listaSubcategoriaProductoSeleccionado.isEmpty()))
/* 475:    */       {
/* 476:564 */         num = 1;
/* 477:565 */         for (SubcategoriaProducto scp : this.listaSubcategoriaProductoSeleccionado)
/* 478:    */         {
/* 479:566 */           filters.put("OR~id~0" + num + "~subcategoriaProducto.idSubcategoriaProducto", String.valueOf(scp.getId()));
/* 480:567 */           num++;
/* 481:    */         }
/* 482:    */       }
/* 483:    */       int num;
/* 484:570 */       if ((this.listaCategoriaProductoSeleccionado != null) && (!this.listaCategoriaProductoSeleccionado.isEmpty()))
/* 485:    */       {
/* 486:571 */         num = 1;
/* 487:572 */         for (CategoriaProducto cp : this.listaCategoriaProductoSeleccionado)
/* 488:    */         {
/* 489:573 */           filters.put("OR~id~0" + num + "~subcategoriaProducto.categoriaProducto.idCategoriaProducto", String.valueOf(cp.getId()));
/* 490:574 */           num++;
/* 491:    */         }
/* 492:    */       }
/* 493:577 */       List<Producto> listaProductos = this.servicioProducto.obtenerListaCombo("codigo", true, filters);
/* 494:579 */       for (Producto producto : listaProductos)
/* 495:    */       {
/* 496:581 */         boolean insertar = true;
/* 497:584 */         if (this.saldoMayorCero) {
/* 498:585 */           insertar = hmProductoSaldoMayorCero.contains(Integer.valueOf(producto.getId()));
/* 499:    */         }
/* 500:588 */         if (insertar)
/* 501:    */         {
/* 502:590 */           descuentosActuales = "n::0,";
/* 503:592 */           if (hmDescuentosProductos.containsKey(Integer.valueOf(producto.getId()))) {
/* 504:593 */             descuentosActuales = descuentosActuales + "n::" + hmDescuentosProductos.get(Integer.valueOf(producto.getId())) + ",";
/* 505:    */           } else {
/* 506:595 */             descuentosActuales = descuentosActuales + "n::0,";
/* 507:    */           }
/* 508:597 */           descuentosActuales = descuentosActuales.substring(4, descuentosActuales.length() - 1);
/* 509:    */           
/* 510:599 */           v.addElement(producto.getCodigo() + "," + producto.getCodigoAlterno() + " ," + producto
/* 511:600 */             .getSubcategoriaProducto().getCategoriaProducto().getNombre().replace(",", ";") + " ," + producto
/* 512:601 */             .getSubcategoriaProducto().getNombre().replace(",", ";") + " ," + producto.getNombre().replace(",", ";") + " ," + descuentosActuales);
/* 513:    */         }
/* 514:    */       }
/* 515:607 */       String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "listaDescuento");
/* 516:608 */       String nombreArchivo = "ListaDeDescuentos.xls";
/* 517:    */       
/* 518:610 */       FuncionesUtiles.crearExcel(v, "listaDescuento", rutaArchivo, nombreArchivo);
/* 519:611 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 520:    */     }
/* 521:    */     catch (Exception e)
/* 522:    */     {
/* 523:614 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 524:615 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 525:    */     }
/* 526:618 */     return this.file;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public String migrarListaDescuentos(FileUploadEvent event)
/* 530:    */   {
/* 531:    */     try
/* 532:    */     {
/* 533:624 */       String fileName = "migracion_lista_descuentos" + event.getFile().getFileName();
/* 534:625 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 535:626 */       this.servicioMigracion.migracionListaDescuentos(this.fechaDesde, this.listaDescuentos.getId(), fileName, input, 2, 
/* 536:627 */         AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal());
/* 537:    */       
/* 538:629 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 539:    */     }
/* 540:    */     catch (ExcepcionAS2 e)
/* 541:    */     {
/* 542:632 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 543:633 */       LOG.error(e);
/* 544:    */     }
/* 545:    */     catch (Exception e)
/* 546:    */     {
/* 547:635 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 548:636 */       LOG.error(e);
/* 549:    */     }
/* 550:638 */     return null;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public List<VersionListaDescuentos> getListaVersionListaDescuentos()
/* 554:    */   {
/* 555:647 */     List<VersionListaDescuentos> lista = new ArrayList();
/* 556:648 */     for (VersionListaDescuentos vld : this.listaDescuentos.getListaVersionesListaDescuentos()) {
/* 557:649 */       if (!vld.isEliminado()) {
/* 558:650 */         lista.add(vld);
/* 559:    */       }
/* 560:    */     }
/* 561:653 */     return lista;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public DataTable getDtListaVersionDescuentos()
/* 565:    */   {
/* 566:657 */     return this.dtListaVersionDescuentos;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public void setDtListaVersionDescuentos(DataTable dtListaVersionDescuentos)
/* 570:    */   {
/* 571:661 */     this.dtListaVersionDescuentos = dtListaVersionDescuentos;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public VersionListaDescuentos getVersionListaDescuentos()
/* 575:    */   {
/* 576:665 */     return this.versionListaDescuentos;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void setVersionListaDescuentos(VersionListaDescuentos versionListaDescuentos)
/* 580:    */   {
/* 581:669 */     this.versionListaDescuentos = versionListaDescuentos;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public List<DetalleListaDescuentos> listaDetalleVersionListaDescuentos()
/* 585:    */   {
/* 586:678 */     List<DetalleListaDescuentos> lista = new ArrayList();
/* 587:679 */     if (this.versionListaDescuentos != null) {
/* 588:680 */       for (DetalleListaDescuentos detalleVersionListaDescuentos : this.versionListaDescuentos.getListaDetalleListaDescuentos()) {
/* 589:681 */         if (!detalleVersionListaDescuentos.isEliminado()) {
/* 590:682 */           lista.add(detalleVersionListaDescuentos);
/* 591:    */         }
/* 592:    */       }
/* 593:    */     }
/* 594:686 */     return lista;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public DataTable getDtDetalleVersionListaDescuentos()
/* 598:    */   {
/* 599:690 */     return this.dtDetalleVersionListaDescuentos;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void setDtDetalleVersionListaDescuentos(DataTable dtDetalleVersionListaDescuentos)
/* 603:    */   {
/* 604:694 */     this.dtDetalleVersionListaDescuentos = dtDetalleVersionListaDescuentos;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public List<CategoriaProducto> getListaCategoriaProductoSeleccionado()
/* 608:    */   {
/* 609:698 */     return this.listaCategoriaProductoSeleccionado;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void setListaCategoriaProductoSeleccionado(List<CategoriaProducto> listaCategoriaProductoSeleccionado)
/* 613:    */   {
/* 614:702 */     this.listaCategoriaProductoSeleccionado = listaCategoriaProductoSeleccionado;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductoSeleccionado()
/* 618:    */   {
/* 619:706 */     return this.listaSubcategoriaProductoSeleccionado;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public void setListaSubcategoriaProductoSeleccionado(List<SubcategoriaProducto> listaSubcategoriaProductoSeleccionado)
/* 623:    */   {
/* 624:710 */     this.listaSubcategoriaProductoSeleccionado = listaSubcategoriaProductoSeleccionado;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public Date getFechaDesde()
/* 628:    */   {
/* 629:714 */     return this.fechaDesde;
/* 630:    */   }
/* 631:    */   
/* 632:    */   public void setFechaDesde(Date fechaDesde)
/* 633:    */   {
/* 634:718 */     this.fechaDesde = fechaDesde;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public boolean isSaldoMayorCero()
/* 638:    */   {
/* 639:722 */     return this.saldoMayorCero;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public void setSaldoMayorCero(boolean saldoMayorCero)
/* 643:    */   {
/* 644:726 */     this.saldoMayorCero = saldoMayorCero;
/* 645:    */   }
/* 646:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.ListaDescuentosBean
 * JD-Core Version:    0.7.0.1
 */