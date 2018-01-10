/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioSolicitudCompra;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.entities.DetalleSolicitudCompra;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empleado;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  14:    */ import com.asinfo.as2.entities.SolicitudCompra;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum;
/*  19:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  20:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  23:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  24:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  25:    */ import com.asinfo.as2.util.AppUtil;
/*  26:    */ import com.asinfo.as2.utils.JsfUtil;
/*  27:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.math.RoundingMode;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Arrays;
/*  32:    */ import java.util.Date;
/*  33:    */ import java.util.HashMap;
/*  34:    */ import java.util.Iterator;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.annotation.PostConstruct;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.faces.bean.ManagedBean;
/*  40:    */ import javax.faces.bean.ManagedProperty;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.component.html.HtmlInputText;
/*  43:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  44:    */ import javax.faces.model.SelectItem;
/*  45:    */ import org.apache.log4j.Logger;
/*  46:    */ import org.primefaces.component.datatable.DataTable;
/*  47:    */ import org.primefaces.event.ToggleEvent;
/*  48:    */ import org.primefaces.model.LazyDataModel;
/*  49:    */ import org.primefaces.model.SortOrder;
/*  50:    */ import org.primefaces.model.TreeNode;
/*  51:    */ 
/*  52:    */ @ManagedBean
/*  53:    */ @ViewScoped
/*  54:    */ public class SolicitudCompraBean
/*  55:    */   extends PageControllerAS2
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = 725178972436303995L;
/*  58:    */   @EJB
/*  59:    */   private ServicioProducto servicioProducto;
/*  60:    */   @EJB
/*  61:    */   protected ServicioDocumento servicioDocumento;
/*  62:    */   @EJB
/*  63:    */   private ServicioSucursal servicioSucursal;
/*  64:    */   @EJB
/*  65:    */   private ServicioSolicitudCompra servicioSolicitudCompra;
/*  66:    */   @EJB
/*  67:    */   private ServicioUsuario servicioUsuario;
/*  68:    */   private SolicitudCompra solicitudCompra;
/*  69:    */   private Empleado empleado;
/*  70:    */   protected LazyDataModel<SolicitudCompra> listaSolicitudCompra;
/*  71:    */   private DataTable dtSolicitudcompra;
/*  72:    */   private DataTable dtDetalleSolicitudCompra;
/*  73:    */   protected List<Documento> listaDocumento;
/*  74:    */   private List<Sucursal> listaSucursal;
/*  75:    */   private List<DetalleSolicitudCompra> listaDetalleSolicitudSeleccionado;
/*  76:    */   private boolean copiarTodo;
/*  77:    */   protected TreeNode raiz;
/*  78:    */   private SelectItem[] listaEstadoItem;
/*  79:    */   @ManagedProperty("#{listaProductoBean}")
/*  80:    */   private ListaProductoBean listaProductoBean;
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85: 99 */     this.listaProductoBean.setIndicadorCompra(true);
/*  86:100 */     this.listaProductoBean.setActivo(true);
/*  87:101 */     setDocumentoBase(DocumentoBase.SOLICITUD_COMPRA);
/*  88:    */     
/*  89:103 */     this.listaSolicitudCompra = new LazyDataModel()
/*  90:    */     {
/*  91:    */       private static final long serialVersionUID = 1L;
/*  92:    */       
/*  93:    */       public List<SolicitudCompra> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  94:    */       {
/*  95:110 */         List<SolicitudCompra> lista = new ArrayList();
/*  96:111 */         if (filters.size() == 0) {
/*  97:113 */           filters.put("estado", "!=" + EstadoSolicitudCompraEnum.CERRADO.name());
/*  98:    */         }
/*  99:115 */         filters = SolicitudCompraBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/* 100:116 */         for (String filterValue : SolicitudCompraBean.this.getFiltrosListado(filters).keySet()) {
/* 101:117 */           if (!filters.containsKey(filterValue)) {
/* 102:118 */             filters.put(filterValue, SolicitudCompraBean.this.getFiltrosListado(filters).get(filterValue));
/* 103:    */           }
/* 104:    */         }
/* 105:121 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 106:    */         try
/* 107:    */         {
/* 108:124 */           lista = SolicitudCompraBean.this.servicioSolicitudCompra.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 109:125 */           SolicitudCompraBean.this.listaSolicitudCompra.setRowCount(SolicitudCompraBean.this.servicioSolicitudCompra.contarPorCriterio(filters));
/* 110:    */         }
/* 111:    */         catch (ExcepcionAS2 e)
/* 112:    */         {
/* 113:128 */           SolicitudCompraBean.this.addInfoMessage(SolicitudCompraBean.this.getLanguageController().getMensaje("msg_info_carga_datos"));
/* 114:129 */           SolicitudCompraBean.LOG.info("ERROR AL CARGAR LISTA SOLICITUD COMPRA", e);
/* 115:    */         }
/* 116:132 */         return lista;
/* 117:    */       }
/* 118:    */     };
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Map<String, String> getFiltrosListado(Map<String, String> filters)
/* 122:    */   {
/* 123:139 */     if (filters == null) {
/* 124:140 */       filters = new HashMap();
/* 125:    */     }
/* 126:141 */     filters.put("indicadorConsolidado", "false");
/* 127:142 */     return filters;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String editar()
/* 131:    */   {
/* 132:147 */     if ((getSolicitudCompra() != null) && (getSolicitudCompra().getId() != 0))
/* 133:    */     {
/* 134:148 */       this.solicitudCompra = this.servicioSolicitudCompra.cargarDetalle(this.solicitudCompra.getId());
/* 135:149 */       if ((this.solicitudCompra.getEstado().equals(EstadoSolicitudCompraEnum.ELABORADO)) || 
/* 136:150 */         (this.solicitudCompra.getEstado().equals(EstadoSolicitudCompraEnum.CONSOLIDADO_PARCIAL))) {
/* 137:151 */         setEditado(true);
/* 138:    */       } else {
/* 139:154 */         addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 140:    */       }
/* 141:    */     }
/* 142:    */     else
/* 143:    */     {
/* 144:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 145:    */     }
/* 146:160 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void copiar()
/* 150:    */   {
/* 151:164 */     if ((getSolicitudCompra() != null) && (getSolicitudCompra().getId() != 0))
/* 152:    */     {
/* 153:165 */       if ((EstadoSolicitudCompraEnum.ELABORADO.equals(this.solicitudCompra.getEstado())) || 
/* 154:166 */         (EstadoSolicitudCompraEnum.CERRADO.equals(this.solicitudCompra.getEstado())) || ((this.copiarTodo) && 
/* 155:167 */         (EstadoSolicitudCompraEnum.CONSOLIDADO_PARCIAL.equals(this.solicitudCompra.getEstado()))) || ((this.copiarTodo) && 
/* 156:168 */         (EstadoSolicitudCompraEnum.CONSOLIDADO.equals(this.solicitudCompra.getEstado()))))
/* 157:    */       {
/* 158:169 */         SolicitudCompra solicitudCompraAux = this.servicioSolicitudCompra.cargarDetalle(this.solicitudCompra.getId());
/* 159:170 */         this.solicitudCompra = this.servicioSolicitudCompra.copiar(solicitudCompraAux, this.copiarTodo, AppUtil.getSucursal());
/* 160:171 */         setEditado(true);
/* 161:    */       }
/* 162:    */       else
/* 163:    */       {
/* 164:173 */         addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 165:    */       }
/* 166:    */     }
/* 167:    */     else {
/* 168:176 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String eliminar()
/* 173:    */   {
/* 174:184 */     return null;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String guardar()
/* 178:    */   {
/* 179:    */     try
/* 180:    */     {
/* 181:191 */       for (Iterator localIterator1 = this.solicitudCompra.getListaDetalleSolicitudCompra().iterator(); localIterator1.hasNext();)
/* 182:    */       {
/* 183:191 */         dsc = (DetalleSolicitudCompra)localIterator1.next();
/* 184:192 */         dsc.setEmpleado(this.solicitudCompra.getEmpleado());
/* 185:193 */         for (DetalleSolicitudCompra dsc2 : dsc.getListaDetalleSolicitudCompra()) {
/* 186:194 */           dsc2.setDetalleSolicitudCompraPadre(dsc);
/* 187:    */         }
/* 188:    */       }
/* 189:    */       DetalleSolicitudCompra dsc;
/* 190:199 */       this.servicioSolicitudCompra.guardar(this.solicitudCompra);
/* 191:200 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 192:201 */       cargarDatos();
/* 193:    */     }
/* 194:    */     catch (ExcepcionAS2 e)
/* 195:    */     {
/* 196:203 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 197:204 */       LOG.info("ERROR AL GUARDAR DATOS", e);
/* 198:    */     }
/* 199:    */     catch (Exception e)
/* 200:    */     {
/* 201:207 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 202:208 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 203:    */     }
/* 204:210 */     return "";
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String cargarEmpleado()
/* 208:    */   {
/* 209:215 */     this.solicitudCompra.setEmpleado(this.empleado);
/* 210:216 */     return "";
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String limpiar()
/* 214:    */   {
/* 215:221 */     crearSolicitudCompra();
/* 216:222 */     return "";
/* 217:    */   }
/* 218:    */   
/* 219:    */   private void crearSolicitudCompra()
/* 220:    */   {
/* 221:226 */     this.solicitudCompra = new SolicitudCompra();
/* 222:227 */     this.solicitudCompra.setSucursal(AppUtil.getSucursal());
/* 223:228 */     this.solicitudCompra.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 224:229 */     this.solicitudCompra.setNumero("");
/* 225:230 */     this.solicitudCompra.setFecha(new Date());
/* 226:231 */     EntidadUsuario usuario = this.servicioUsuario.cargarDetalle(AppUtil.getUsuarioEnSesion().getIdUsuario(), AppUtil.getOrganizacion().getId());
/* 227:232 */     this.solicitudCompra.setEmpleado(usuario.getEmpleado());
/* 228:233 */     this.solicitudCompra.setEstado(EstadoSolicitudCompraEnum.ELABORADO);
/* 229:234 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty())) {
/* 230:235 */       this.solicitudCompra.setDocumento((Documento)getListaDocumento().get(0));
/* 231:    */     }
/* 232:236 */     setRaiz(null);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String cargarDatos()
/* 236:    */   {
/* 237:    */     try
/* 238:    */     {
/* 239:242 */       setEditado(false);
/* 240:243 */       limpiar();
/* 241:    */     }
/* 242:    */     catch (Exception e)
/* 243:    */     {
/* 244:245 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 245:246 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 246:    */     }
/* 247:248 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String agregarDetalle()
/* 251:    */   {
/* 252:252 */     DetalleSolicitudCompra d = new DetalleSolicitudCompra();
/* 253:253 */     d.setSolicitudCompra(getSolicitudCompra());
/* 254:254 */     d.setIdOrganizacion(getSolicitudCompra().getIdOrganizacion());
/* 255:255 */     d.setIdSucursal(getSolicitudCompra().getSucursal().getIdSucursal());
/* 256:256 */     d.setCantidad(BigDecimal.ZERO);
/* 257:257 */     d.setProducto(new Producto());
/* 258:258 */     getSolicitudCompra().getListaDetalleSolicitudCompra().add(d);
/* 259:259 */     return "";
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String eliminarDetalle()
/* 263:    */   {
/* 264:263 */     DetalleSolicitudCompra d = (DetalleSolicitudCompra)this.dtDetalleSolicitudCompra.getRowData();
/* 265:264 */     d.setEliminado(true);
/* 266:265 */     d.setCantidad(BigDecimal.ZERO);
/* 267:266 */     return "";
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 271:    */   {
/* 272:271 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 273:272 */     DetalleSolicitudCompra dpp = (DetalleSolicitudCompra)this.dtDetalleSolicitudCompra.getRowData();
/* 274:    */     try
/* 275:    */     {
/* 276:275 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.SOLICITUD_COMPRA);
/* 277:276 */       actualizarProducto(dpp, producto);
/* 278:    */     }
/* 279:    */     catch (ExcepcionAS2 e)
/* 280:    */     {
/* 281:278 */       e.printStackTrace();
/* 282:279 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void actualizarProducto(DetalleSolicitudCompra dpp, Producto producto)
/* 287:    */   {
/* 288:285 */     if (this.solicitudCompra.getEmpleado() != null)
/* 289:    */     {
/* 290:286 */       dpp.setProducto(producto);
/* 291:287 */       dpp.setUnidadCompra(producto.getUnidadCompra());
/* 292:    */     }
/* 293:    */     else
/* 294:    */     {
/* 295:289 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 296:290 */       dpp.getProducto().setCodigo("");
/* 297:    */     }
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List<DetalleSolicitudCompra> getListaDetalleSolicitudCompra()
/* 301:    */   {
/* 302:295 */     List<DetalleSolicitudCompra> detalle = new ArrayList();
/* 303:296 */     for (DetalleSolicitudCompra dsc : getSolicitudCompra().getListaDetalleSolicitudCompra()) {
/* 304:297 */       if (!dsc.isEliminado()) {
/* 305:298 */         detalle.add(dsc);
/* 306:    */       }
/* 307:    */     }
/* 308:301 */     return detalle;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void cerrarSolicitudCompra()
/* 312:    */   {
/* 313:    */     try
/* 314:    */     {
/* 315:306 */       setSolicitudCompra(this.servicioSolicitudCompra.cargarDetalle(getSolicitudCompra().getId()));
/* 316:307 */       this.servicioSolicitudCompra.cerrarSolicitudCompra(getSolicitudCompra());
/* 317:    */     }
/* 318:    */     catch (AS2Exception e)
/* 319:    */     {
/* 320:309 */       JsfUtil.addErrorMessage(e, "");
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/* 325:    */   {
/* 326:314 */     if (saldoLote.getProducto().equals(getListaProductoBean().getProducto()))
/* 327:    */     {
/* 328:315 */       getListaProductoBean().setSaldoProductoLote(saldoLote);
/* 329:316 */       cargarProducto();
/* 330:    */     }
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void cargarProducto(Producto producto)
/* 334:    */   {
/* 335:321 */     getListaProductoBean().setProducto(producto);
/* 336:322 */     getListaProductoBean().setSaldoProductoLote(null);
/* 337:323 */     cargarProducto();
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void cargarProducto()
/* 341:    */   {
/* 342:331 */     Producto producto = getListaProductoBean().getProducto();
/* 343:332 */     if (producto != null)
/* 344:    */     {
/* 345:333 */       DetalleSolicitudCompra dsc = new DetalleSolicitudCompra();
/* 346:334 */       dsc.setIdOrganizacion(getSolicitudCompra().getIdOrganizacion());
/* 347:335 */       dsc.setIdSucursal(getSolicitudCompra().getSucursal().getIdSucursal());
/* 348:336 */       dsc.setProducto(producto);
/* 349:337 */       dsc.setCantidadOriginal(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/* 350:338 */       dsc.setCantidad(dsc.getCantidadOriginal());
/* 351:339 */       dsc.setSolicitudCompra(getSolicitudCompra());
/* 352:340 */       dsc.setUnidadCompra(producto.getUnidadCompra());
/* 353:341 */       getSolicitudCompra().getListaDetalleSolicitudCompra().add(dsc);
/* 354:    */     }
/* 355:344 */     getListaProductoBean().setProducto(null);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void actualizarCantidadDetalle()
/* 359:    */   {
/* 360:349 */     DetalleSolicitudCompra det = (DetalleSolicitudCompra)this.dtDetalleSolicitudCompra.getRowData();
/* 361:350 */     det.setCantidad(det.getCantidadOriginal());
/* 362:    */   }
/* 363:    */   
/* 364:    */   public SolicitudCompra getSolicitudCompra()
/* 365:    */   {
/* 366:356 */     return this.solicitudCompra;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setSolicitudCompra(SolicitudCompra solicitudCompra)
/* 370:    */   {
/* 371:360 */     this.solicitudCompra = solicitudCompra;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public LazyDataModel<SolicitudCompra> getListaSolicitudCompra()
/* 375:    */   {
/* 376:364 */     if (this.listaSolicitudCompra == null) {
/* 377:365 */       cargarDatos();
/* 378:    */     }
/* 379:367 */     return this.listaSolicitudCompra;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setListaSolicitudCompra(LazyDataModel<SolicitudCompra> listaSolicitudCompra)
/* 383:    */   {
/* 384:371 */     this.listaSolicitudCompra = listaSolicitudCompra;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public DataTable getDtSolicitudCompra()
/* 388:    */   {
/* 389:375 */     return this.dtSolicitudcompra;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setDtSolicitudCompra(DataTable dtSolicitudCompra)
/* 393:    */   {
/* 394:379 */     this.dtSolicitudcompra = dtSolicitudCompra;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public DataTable getDtDetalleSolicitudCompra()
/* 398:    */   {
/* 399:383 */     return this.dtDetalleSolicitudCompra;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setDtDetalleSolicitudCompra(DataTable dtDetalleSolicitudCompra)
/* 403:    */   {
/* 404:387 */     this.dtDetalleSolicitudCompra = dtDetalleSolicitudCompra;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public List<Documento> getListaDocumento()
/* 408:    */   {
/* 409:    */     try
/* 410:    */     {
/* 411:392 */       if (this.listaDocumento == null) {
/* 412:393 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.SOLICITUD_COMPRA, AppUtil.getOrganizacion()
/* 413:394 */           .getIdOrganizacion());
/* 414:    */       }
/* 415:    */     }
/* 416:    */     catch (ExcepcionAS2 e)
/* 417:    */     {
/* 418:397 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 419:    */     }
/* 420:399 */     return this.listaDocumento;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 424:    */   {
/* 425:403 */     this.listaDocumento = listaDocumento;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public List<Sucursal> getListaSucursal()
/* 429:    */   {
/* 430:407 */     if (this.listaSucursal == null) {
/* 431:408 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 432:    */     }
/* 433:410 */     return this.listaSucursal;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 437:    */   {
/* 438:418 */     this.listaSucursal = listaSucursal;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public ListaProductoBean getListaProductoBean()
/* 442:    */   {
/* 443:425 */     return this.listaProductoBean;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 447:    */   {
/* 448:433 */     this.listaProductoBean = listaProductoBean;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public Empleado getEmpleado()
/* 452:    */   {
/* 453:437 */     return this.empleado;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setEmpleado(Empleado empleado)
/* 457:    */   {
/* 458:441 */     this.empleado = empleado;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public List<DetalleSolicitudCompra> getListaDetalleSolicitudSeleccionado()
/* 462:    */   {
/* 463:445 */     return this.listaDetalleSolicitudSeleccionado;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setListaDetalleSolicitudSeleccionado(List<DetalleSolicitudCompra> listaDetalleSolicitudSeleccionado)
/* 467:    */   {
/* 468:449 */     this.listaDetalleSolicitudSeleccionado = listaDetalleSolicitudSeleccionado;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void cargarDetallesListado(ToggleEvent event)
/* 472:    */   {
/* 473:453 */     this.solicitudCompra = ((SolicitudCompra)event.getData());
/* 474:454 */     this.solicitudCompra = this.servicioSolicitudCompra.cargarDetalle(this.solicitudCompra.getId());
/* 475:455 */     setListaDetalleSolicitudSeleccionado(this.solicitudCompra.getListaDetalleSolicitudCompra());
/* 476:    */   }
/* 477:    */   
/* 478:    */   public SelectItem[] getListaEstadoItem()
/* 479:    */   {
/* 480:463 */     if (this.listaEstadoItem == null)
/* 481:    */     {
/* 482:465 */       List<SelectItem> lista = new ArrayList();
/* 483:466 */       lista.add(new SelectItem("", ""));
/* 484:467 */       EstadoSolicitudCompraEnum[] arrayEstados = EstadoSolicitudCompraEnum.values();
/* 485:469 */       for (EstadoSolicitudCompraEnum t : arrayEstados)
/* 486:    */       {
/* 487:470 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 488:471 */         lista.add(item);
/* 489:    */       }
/* 490:473 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 491:    */       
/* 492:475 */       Arrays.sort(this.listaEstadoItem, new SelectItemComparator());
/* 493:    */     }
/* 494:478 */     return this.listaEstadoItem;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public boolean isCopiarTodo()
/* 498:    */   {
/* 499:482 */     return this.copiarTodo;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setCopiarTodo(boolean copiarTodo)
/* 503:    */   {
/* 504:486 */     this.copiarTodo = copiarTodo;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public TreeNode getRaiz()
/* 508:    */   {
/* 509:490 */     return this.raiz;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setRaiz(TreeNode raiz)
/* 513:    */   {
/* 514:494 */     this.raiz = raiz;
/* 515:    */   }
/* 516:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.SolicitudCompraBean
 * JD-Core Version:    0.7.0.1
 */