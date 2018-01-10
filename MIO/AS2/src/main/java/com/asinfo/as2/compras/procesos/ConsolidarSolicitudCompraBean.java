/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioSolicitudCompra;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   9:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  10:    */ import com.asinfo.as2.entities.DetalleSolicitudCompra;
/*  11:    */ import com.asinfo.as2.entities.DetalleSolicitudCompraProveedor;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empleado;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.Producto;
/*  18:    */ import com.asinfo.as2.entities.ProductoUltimaCompra;
/*  19:    */ import com.asinfo.as2.entities.SaldoProducto;
/*  20:    */ import com.asinfo.as2.entities.SolicitudCompra;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  28:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.utils.JsfUtil;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Calendar;
/*  35:    */ import java.util.Collection;
/*  36:    */ import java.util.Date;
/*  37:    */ import java.util.HashMap;
/*  38:    */ import java.util.HashSet;
/*  39:    */ import java.util.Iterator;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import java.util.Set;
/*  43:    */ import javax.annotation.PostConstruct;
/*  44:    */ import javax.ejb.EJB;
/*  45:    */ import javax.faces.bean.ManagedBean;
/*  46:    */ import javax.faces.bean.ViewScoped;
/*  47:    */ import org.apache.log4j.Logger;
/*  48:    */ import org.primefaces.component.datatable.DataTable;
/*  49:    */ import org.primefaces.context.RequestContext;
/*  50:    */ import org.primefaces.model.DefaultTreeNode;
/*  51:    */ import org.primefaces.model.LazyDataModel;
/*  52:    */ import org.primefaces.model.SortOrder;
/*  53:    */ import org.primefaces.model.TreeNode;
/*  54:    */ 
/*  55:    */ @ManagedBean
/*  56:    */ @ViewScoped
/*  57:    */ public class ConsolidarSolicitudCompraBean
/*  58:    */   extends SolicitudCompraBean
/*  59:    */ {
/*  60:    */   private static final long serialVersionUID = 725178972436303995L;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioSolicitudCompra servicioSolicitudCompra;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioProducto servicioProducto;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioEmpresa servicioEmpresa;
/*  67:    */   @EJB
/*  68:    */   private transient ServicioListaPrecios servicioListaPrecios;
/*  69:    */   @EJB
/*  70:    */   private transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  71:    */   @EJB
/*  72:    */   private transient ServicioGenerico<DetalleSolicitudCompra> servicioDetalleSolicitudCompra;
/*  73:    */   @EJB
/*  74:    */   private transient ServicioCategoriaProducto servicioCategoriaProducto;
/*  75:    */   private Date fechaDesde;
/*  76:    */   private Date fechaHasta;
/*  77:    */   private List<Empleado> listaEmpleadoSeleccionado;
/*  78:    */   private List<Producto> listaProductoSeleccionado;
/*  79:    */   private List<CategoriaProducto> listaCategoriaProductoSeleccionado;
/*  80:    */   private List<SolicitudCompra> listaSolicitudCompraSeleccionada;
/*  81:    */   private TreeNode nodoSeleccionado;
/*  82:    */   private DataTable dtProveedoresListaPrecios;
/*  83:    */   private DetalleSolicitudCompra detalleSeleccionado;
/*  84:    */   private Producto producto;
/*  85:    */   private List<DetalleSolicitudCompra> listaDetalleSolicitudCompraGenerar;
/*  86:    */   private List<DetalleSolicitudCompra> listaCrearPedidoProveedor;
/*  87:    */   private List<SaldoProducto> listaSaldoProducto;
/*  88:    */   private List<ProductoUltimaCompra> listProveedores;
/*  89:    */   private List<DetalleSolicitudCompraProveedor> listaDetallesPedidoProveedor;
/*  90:    */   private List<DetalleSolicitudCompraProveedor> listaDetallesPorSolicitar;
/*  91:    */   private List<DetalleSolicitudCompraProveedor> listaDetallesConsolidados;
/*  92:    */   Map<Integer, BigDecimal> hashTotalesProveedores;
/*  93:107 */   Map<Integer, BigDecimal> hashTotalesAprobados = new HashMap();
/*  94:109 */   private BigDecimal totalSaldoStock = BigDecimal.ZERO;
/*  95:110 */   private BigDecimal totalSaldoAlmacenamiento = BigDecimal.ZERO;
/*  96:111 */   private BigDecimal totalCompra = BigDecimal.ZERO;
/*  97:112 */   private BigDecimal totalAprobado = BigDecimal.ZERO;
/*  98:113 */   private BigDecimal totalCantidad = BigDecimal.ZERO;
/*  99:114 */   private BigDecimal totalPrecioPactado = BigDecimal.ZERO;
/* 100:115 */   private BigDecimal totalPrecioUC = BigDecimal.ZERO;
/* 101:    */   private Integer idSolicitudCompra;
/* 102:    */   
/* 103:    */   @PostConstruct
/* 104:    */   public void init()
/* 105:    */   {
/* 106:123 */     setDocumentoBase(DocumentoBase.CONSOLIDACION_SOLICITUD_COMPRA);
/* 107:    */     
/* 108:125 */     this.listaSolicitudCompra = new LazyDataModel()
/* 109:    */     {
/* 110:    */       private static final long serialVersionUID = 1L;
/* 111:    */       
/* 112:    */       public List<SolicitudCompra> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 113:    */       {
/* 114:132 */         List<SolicitudCompra> lista = new ArrayList();
/* 115:134 */         if (filters.size() == 0)
/* 116:    */         {
/* 117:136 */           filters.put("AND~est~01~estado", "!=" + EstadoSolicitudCompraEnum.CONSOLIDADO);
/* 118:137 */           filters.put("AND~est~02~estado", "!=" + EstadoSolicitudCompraEnum.CERRADO);
/* 119:    */         }
/* 120:140 */         for (String filterValue : ConsolidarSolicitudCompraBean.this.getFiltrosListado().keySet()) {
/* 121:141 */           if (!filters.containsKey(filterValue)) {
/* 122:142 */             filters.put(filterValue, ConsolidarSolicitudCompraBean.this.getFiltrosListado().get(filterValue));
/* 123:    */           }
/* 124:    */         }
/* 125:146 */         if (ConsolidarSolicitudCompraBean.this.idSolicitudCompra != null)
/* 126:    */         {
/* 127:147 */           filters.put("idSolicitudCompra", ConsolidarSolicitudCompraBean.this.idSolicitudCompra.toString());
/* 128:148 */           ConsolidarSolicitudCompraBean.this.idSolicitudCompra = null;
/* 129:    */         }
/* 130:151 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 131:    */         try
/* 132:    */         {
/* 133:154 */           lista = ConsolidarSolicitudCompraBean.this.servicioSolicitudCompra.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 134:155 */           ConsolidarSolicitudCompraBean.this.listaSolicitudCompra.setRowCount(ConsolidarSolicitudCompraBean.this.servicioSolicitudCompra.contarPorCriterio(filters));
/* 135:    */         }
/* 136:    */         catch (ExcepcionAS2 e)
/* 137:    */         {
/* 138:158 */           ConsolidarSolicitudCompraBean.this.addInfoMessage(ConsolidarSolicitudCompraBean.this.getLanguageController().getMensaje("msg_info_carga_datos"));
/* 139:159 */           ConsolidarSolicitudCompraBean.LOG.info("ERROR AL CARGAR DATOS PEDIDO PROVEEDOR", e);
/* 140:    */         }
/* 141:162 */         return lista;
/* 142:    */       }
/* 143:    */     };
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String guardar()
/* 147:    */   {
/* 148:    */     try
/* 149:    */     {
/* 150:172 */       for (Iterator localIterator1 = getSolicitudCompra().getListaDetalleSolicitudCompra().iterator(); localIterator1.hasNext();)
/* 151:    */       {
/* 152:172 */         dsc = (DetalleSolicitudCompra)localIterator1.next();
/* 153:173 */         dsc.setEmpleado(getSolicitudCompra().getEmpleado());
/* 154:174 */         for (DetalleSolicitudCompra dsc2 : dsc.getListaDetalleSolicitudCompra()) {
/* 155:175 */           dsc2.setDetalleSolicitudCompraPadre(dsc);
/* 156:    */         }
/* 157:    */       }
/* 158:    */       DetalleSolicitudCompra dsc;
/* 159:179 */       this.servicioSolicitudCompra.guardar(getSolicitudCompra());
/* 160:180 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 161:181 */       cargarDatos();
/* 162:    */     }
/* 163:    */     catch (ExcepcionAS2 e)
/* 164:    */     {
/* 165:183 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 166:184 */       LOG.info("ERROR AL GUARDAR DATOS", e);
/* 167:    */     }
/* 168:    */     catch (AS2Exception e)
/* 169:    */     {
/* 170:187 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 171:188 */       LOG.info("ERROR AL GUARDAR DATOS", e);
/* 172:    */     }
/* 173:    */     catch (Exception e)
/* 174:    */     {
/* 175:191 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 176:192 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 177:    */     }
/* 178:194 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Map<String, String> getFiltrosListado()
/* 182:    */   {
/* 183:199 */     Map<String, String> filtros = new HashMap();
/* 184:200 */     filtros.put("indicadorConsolidado", "true");
/* 185:201 */     return filtros;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<Empleado> completeEmpleado(String consulta)
/* 189:    */   {
/* 190:206 */     HashMap<String, String> filtros = new HashMap();
/* 191:207 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 192:208 */     return this.servicioSolicitudCompra.autocompletarEmpleado(consulta, filtros);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<Producto> completeProducto(String consulta)
/* 196:    */   {
/* 197:212 */     HashMap<String, String> filtros = new HashMap();
/* 198:213 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 199:214 */     return this.servicioSolicitudCompra.autocompletarProducto(consulta, filtros);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<CategoriaProducto> completeCategoriaProducto(String consulta)
/* 203:    */   {
/* 204:218 */     List<CategoriaProducto> lista = new ArrayList();
/* 205:    */     
/* 206:220 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 207:221 */     filters.put("nombre", "%" + consulta.trim());
/* 208:    */     
/* 209:223 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 210:    */     
/* 211:225 */     return lista;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<SolicitudCompra> completeSolicitud(String consulta)
/* 215:    */   {
/* 216:229 */     HashMap<String, String> filtros = new HashMap();
/* 217:230 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 218:231 */     filtros.put("AND~01~01~estado", "!=" + EstadoSolicitudCompraEnum.CERRADO.name());
/* 219:232 */     filtros.put("AND~01~02~estado", "!=" + EstadoSolicitudCompraEnum.ELABORADO.name());
/* 220:233 */     filtros.put("numero", consulta);
/* 221:234 */     filtros.put("documento.documentoBase", "" + DocumentoBase.SOLICITUD_COMPRA);
/* 222:235 */     return this.servicioSolicitudCompra.autocompletarSolicitudCompra(consulta, filtros);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void consolidarProductos()
/* 226:    */   {
/* 227:240 */     setSolicitudCompra(this.servicioSolicitudCompra.consolidarProducto(AppUtil.getOrganizacion().getIdOrganizacion(), 
/* 228:241 */       getListaCategoriaProductoSeleccionado(), getListaProductoSeleccionado(), getListaEmpleadoSeleccionado(), getSolicitudCompra(), this.fechaDesde, this.fechaHasta, 
/* 229:242 */       getListaSolicitudCompraSeleccionada()));
/* 230:    */     
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:249 */     this.raiz = null;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String cargarValorCuota()
/* 240:    */   {
/* 241:254 */     DetalleSolicitudCompra dsc = this.detalleSeleccionado;
/* 242:255 */     if (dsc.isIndicadorAgrupado())
/* 243:    */     {
/* 244:256 */       DetalleSolicitudCompra d = null;
/* 245:257 */       for (DetalleSolicitudCompra dscAux : dsc.getListaDetalleSolicitudCompra()) {
/* 246:258 */         if (!dscAux.isEliminadoDesdeConsolidacion())
/* 247:    */         {
/* 248:259 */           dscAux.setCantidadAprobada(dscAux.getCantidad());
/* 249:260 */           d = dscAux;
/* 250:    */         }
/* 251:    */       }
/* 252:263 */       if (d != null) {
/* 253:264 */         totalizarCantidad(d);
/* 254:    */       }
/* 255:    */     }
/* 256:    */     else
/* 257:    */     {
/* 258:267 */       dsc.setCantidadAprobada(dsc.getCantidad());
/* 259:268 */       totalizarCantidad(dsc);
/* 260:    */     }
/* 261:271 */     return "";
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String limpiarValorCuota()
/* 265:    */   {
/* 266:276 */     DetalleSolicitudCompra dsc = this.detalleSeleccionado;
/* 267:277 */     if (dsc.isIndicadorAgrupado())
/* 268:    */     {
/* 269:278 */       DetalleSolicitudCompra d = null;
/* 270:279 */       for (DetalleSolicitudCompra dscAux : dsc.getListaDetalleSolicitudCompra())
/* 271:    */       {
/* 272:280 */         dscAux.setCantidadAprobada(BigDecimal.ZERO);
/* 273:281 */         d = dscAux;
/* 274:    */       }
/* 275:283 */       totalizarCantidad(d);
/* 276:    */     }
/* 277:    */     else
/* 278:    */     {
/* 279:285 */       dsc.setCantidadAprobada(BigDecimal.ZERO);
/* 280:286 */       totalizarCantidad(dsc);
/* 281:    */     }
/* 282:288 */     return "";
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void totalizar()
/* 286:    */   {
/* 287:293 */     for (DetalleSolicitudCompra dsc : getSolicitudCompra().getListaDetalleSolicitudCompra())
/* 288:    */     {
/* 289:294 */       BigDecimal totalCantidadAprobada = BigDecimal.ZERO;
/* 290:295 */       for (DetalleSolicitudCompra dscAux : dsc.getListaDetalleSolicitudCompra()) {
/* 291:296 */         totalCantidadAprobada = totalCantidadAprobada.add(dscAux.getCantidadAprobada());
/* 292:    */       }
/* 293:298 */       dsc.setCantidad(totalCantidadAprobada);
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void totalizarCantidad(DetalleSolicitudCompra dsc)
/* 298:    */   {
/* 299:305 */     BigDecimal totalCantidadAprobada = BigDecimal.ZERO;
/* 300:306 */     if ((dsc.getCantidadAprobada().compareTo(dsc.getCantidad()) > 0) || (dsc.getCantidadAprobada().compareTo(BigDecimal.ZERO) < 0)) {
/* 301:307 */       dsc.setCantidadAprobada(dsc.getCantidad());
/* 302:    */     }
/* 303:309 */     DetalleSolicitudCompra dscp = dsc.getDetalleSolicitudCompraPadre();
/* 304:310 */     for (DetalleSolicitudCompra dscAux : dscp.getListaDetalleSolicitudCompra()) {
/* 305:311 */       totalCantidadAprobada = totalCantidadAprobada.add(dscAux.getCantidadAprobada());
/* 306:    */     }
/* 307:313 */     dscp.setCantidad(totalCantidadAprobada);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public String editar()
/* 311:    */   {
/* 312:322 */     if ((getSolicitudCompra() != null) && (getSolicitudCompra().getId() != 0))
/* 313:    */     {
/* 314:323 */       this.raiz = null;
/* 315:324 */       setSolicitudCompra(this.servicioSolicitudCompra.cargarDetalle(getSolicitudCompra().getId()));
/* 316:325 */       if ((EstadoSolicitudCompraEnum.ELABORADO.equals(getSolicitudCompra().getEstado())) || 
/* 317:326 */         (EstadoSolicitudCompraEnum.CONSOLIDADO_PARCIAL.equals(getSolicitudCompra().getEstado()))) {
/* 318:327 */         setEditado(true);
/* 319:    */       } else {
/* 320:337 */         addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 321:    */       }
/* 322:    */     }
/* 323:    */     else
/* 324:    */     {
/* 325:340 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 326:    */     }
/* 327:343 */     return "";
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void agregarProveedor()
/* 331:    */   {
/* 332:347 */     Empresa empresa = new Empresa();
/* 333:348 */     ProductoUltimaCompra plp = new ProductoUltimaCompra();
/* 334:349 */     plp.setEmpresa(empresa);
/* 335:350 */     plp.setProducto(this.producto);
/* 336:351 */     plp.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 337:352 */     plp.setIdSucursal(AppUtil.getSucursal().getId());
/* 338:353 */     plp.setFecha(new Date());
/* 339:354 */     plp.setIndicadorGuardar(true);
/* 340:355 */     plp.setIndicadorNuevo(true);
/* 341:356 */     this.listProveedores.add(plp);
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void cargarListaGenerarPedidoProveedor(SolicitudCompra solicitudCompra)
/* 345:    */   {
/* 346:361 */     setSolicitudCompra(this.servicioSolicitudCompra.cargarDetalle(solicitudCompra.getId()));
/* 347:362 */     this.listaDetallesPorSolicitar = new ArrayList();
/* 348:    */     
/* 349:364 */     Map<Integer, List<DetalleSolicitudCompraProveedor>> hashDetallesPedido = new HashMap();
/* 350:365 */     for (DetalleSolicitudCompraProveedor dscp : getSolicitudCompra().getListaDetalleSolicitudCompraProveedor()) {
/* 351:366 */       if (!dscp.isIndicadorEnPedido()) {
/* 352:367 */         if (hashDetallesPedido.containsKey(Integer.valueOf(dscp.getEmpresa().getId())))
/* 353:    */         {
/* 354:368 */           ((List)hashDetallesPedido.get(Integer.valueOf(dscp.getEmpresa().getId()))).add(dscp);
/* 355:    */         }
/* 356:    */         else
/* 357:    */         {
/* 358:370 */           List<DetalleSolicitudCompraProveedor> detalles = new ArrayList();
/* 359:371 */           detalles.add(dscp);
/* 360:372 */           hashDetallesPedido.put(Integer.valueOf(dscp.getEmpresa().getId()), detalles);
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:376 */     this.hashTotalesProveedores = new HashMap();
/* 365:377 */     this.totalCompra = BigDecimal.ZERO;
/* 366:378 */     for (Integer id : hashDetallesPedido.keySet())
/* 367:    */     {
/* 368:379 */       BigDecimal tot = BigDecimal.ZERO;
/* 369:380 */       for (DetalleSolicitudCompraProveedor det : (List)hashDetallesPedido.get(id)) {
/* 370:381 */         tot = tot.add(det.getTotal());
/* 371:    */       }
/* 372:383 */       this.hashTotalesProveedores.put(id, tot);
/* 373:384 */       this.totalCompra = this.totalCompra.add(tot);
/* 374:385 */       this.listaDetallesPorSolicitar.addAll((Collection)hashDetallesPedido.get(id));
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void totalizarValoresProveedores()
/* 379:    */   {
/* 380:391 */     this.totalCantidad = BigDecimal.ZERO;
/* 381:392 */     this.totalPrecioPactado = BigDecimal.ZERO;
/* 382:393 */     this.totalPrecioUC = BigDecimal.ZERO;
/* 383:394 */     if (this.listProveedores != null) {
/* 384:395 */       for (ProductoUltimaCompra puc : this.listProveedores)
/* 385:    */       {
/* 386:396 */         if (puc.getCantidad() != null) {
/* 387:397 */           this.totalCantidad = this.totalCantidad.add(puc.getCantidad());
/* 388:    */         }
/* 389:399 */         if ((puc.getPrecioPactado() != null) && (puc.getCantidad() != null)) {
/* 390:400 */           this.totalPrecioPactado = this.totalPrecioPactado.add(puc.getPrecioPactado().multiply(puc.getCantidad()));
/* 391:    */         }
/* 392:402 */         if ((puc.getPrecio() != null) && (puc.getCantidad() != null)) {
/* 393:403 */           this.totalPrecioUC = this.totalPrecioUC.add(puc.getPrecio().multiply(puc.getCantidad()));
/* 394:    */         }
/* 395:    */       }
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   public List<DetalleSolicitudCompra> listaDetalleSolicitudCompraPendientes(List<DetalleSolicitudCompra> lista)
/* 400:    */   {
/* 401:410 */     List<DetalleSolicitudCompra> listaPendiente = new ArrayList();
/* 402:411 */     for (DetalleSolicitudCompra dsc : lista) {
/* 403:412 */       if (!dsc.isIndicadorEnPedido()) {
/* 404:413 */         listaPendiente.add(dsc);
/* 405:    */       }
/* 406:    */     }
/* 407:416 */     return listaPendiente;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public List<Documento> getListaDocumento()
/* 411:    */   {
/* 412:    */     try
/* 413:    */     {
/* 414:421 */       if (this.listaDocumento == null) {
/* 415:422 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.CONSOLIDACION_SOLICITUD_COMPRA, 
/* 416:423 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 417:    */       }
/* 418:    */     }
/* 419:    */     catch (ExcepcionAS2 e)
/* 420:    */     {
/* 421:426 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 422:    */     }
/* 423:428 */     return this.listaDocumento;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public Date getFechaDesde()
/* 427:    */   {
/* 428:434 */     return this.fechaDesde;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setFechaDesde(Date fechaDesde)
/* 432:    */   {
/* 433:438 */     this.fechaDesde = fechaDesde;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public Date getFechaHasta()
/* 437:    */   {
/* 438:442 */     return this.fechaHasta;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setFechaHasta(Date fechaHasta)
/* 442:    */   {
/* 443:446 */     this.fechaHasta = fechaHasta;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public List<Empleado> getListaEmpleadoSeleccionado()
/* 447:    */   {
/* 448:450 */     if (this.listaEmpleadoSeleccionado == null) {
/* 449:451 */       this.listaEmpleadoSeleccionado = new ArrayList();
/* 450:    */     }
/* 451:453 */     return this.listaEmpleadoSeleccionado;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setListaEmpleadoSeleccionado(List<Empleado> listaEmpleadoSeleccionado)
/* 455:    */   {
/* 456:457 */     this.listaEmpleadoSeleccionado = listaEmpleadoSeleccionado;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public List<Producto> getListaProductoSeleccionado()
/* 460:    */   {
/* 461:461 */     if (this.listaProductoSeleccionado == null) {
/* 462:462 */       this.listaProductoSeleccionado = new ArrayList();
/* 463:    */     }
/* 464:464 */     return this.listaProductoSeleccionado;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void setListaProductoSeleccionado(List<Producto> listaProductoSeleccionado)
/* 468:    */   {
/* 469:468 */     this.listaProductoSeleccionado = listaProductoSeleccionado;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public List<CategoriaProducto> getListaCategoriaProductoSeleccionado()
/* 473:    */   {
/* 474:472 */     if (this.listaCategoriaProductoSeleccionado == null) {
/* 475:473 */       this.listaCategoriaProductoSeleccionado = new ArrayList();
/* 476:    */     }
/* 477:475 */     return this.listaCategoriaProductoSeleccionado;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setListaCategoriaProductoSeleccionado(List<CategoriaProducto> listaCategoriaProductoSeleccionado)
/* 481:    */   {
/* 482:479 */     this.listaCategoriaProductoSeleccionado = listaCategoriaProductoSeleccionado;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public TreeNode getRaiz()
/* 486:    */   {
/* 487:483 */     if (this.raiz == null)
/* 488:    */     {
/* 489:484 */       this.raiz = new DefaultTreeNode();
/* 490:485 */       if ((getSolicitudCompra() != null) && (isEditado())) {
/* 491:491 */         for (DetalleSolicitudCompra dsc : getSolicitudCompra().getListaDetalleSolicitudCompra())
/* 492:    */         {
/* 493:492 */           detalleSolicitudCompraConsolidada = null;
/* 494:493 */           if (!dsc.isEliminadoDesdeConsolidacion())
/* 495:    */           {
/* 496:494 */             detalleSolicitudCompraConsolidada = new DefaultTreeNode(dsc, this.raiz);
/* 497:495 */             detalleSolicitudCompraConsolidada.setExpanded(true);
/* 498:    */           }
/* 499:497 */           for (DetalleSolicitudCompra dscAux : dsc.getListaDetalleSolicitudCompra()) {
/* 500:498 */             if ((!dscAux.isEliminadoDesdeConsolidacion()) && (detalleSolicitudCompraConsolidada != null)) {
/* 501:499 */               new DefaultTreeNode(dscAux, detalleSolicitudCompraConsolidada);
/* 502:    */             }
/* 503:    */           }
/* 504:    */         }
/* 505:    */       }
/* 506:    */     }
/* 507:    */     TreeNode detalleSolicitudCompraConsolidada;
/* 508:505 */     return this.raiz;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setRaiz(TreeNode raiz)
/* 512:    */   {
/* 513:509 */     this.raiz = raiz;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public TreeNode getNodoSeleccionado()
/* 517:    */   {
/* 518:513 */     return this.nodoSeleccionado;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void setNodoSeleccionado(TreeNode nodoSeleccionado)
/* 522:    */   {
/* 523:517 */     this.nodoSeleccionado = nodoSeleccionado;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public List<DetalleSolicitudCompra> getListaDetalleSolicitudCompraGenerar()
/* 527:    */   {
/* 528:521 */     return this.listaDetalleSolicitudCompraGenerar == null ? (this.listaDetalleSolicitudCompraGenerar = new ArrayList()) : this.listaDetalleSolicitudCompraGenerar;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public void setListaDetalleSolicitudCompraGenerar(List<DetalleSolicitudCompra> listaDetalleSolicitudCompraGenerar)
/* 532:    */   {
/* 533:526 */     this.listaDetalleSolicitudCompraGenerar = listaDetalleSolicitudCompraGenerar;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public List<DetalleSolicitudCompra> getListaCrearPedidoProveedor()
/* 537:    */   {
/* 538:530 */     return this.listaCrearPedidoProveedor;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public void setListaCrearPedidoProveedor(List<DetalleSolicitudCompra> listaCrearPedidoProveedor)
/* 542:    */   {
/* 543:534 */     this.listaCrearPedidoProveedor = listaCrearPedidoProveedor;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public DetalleSolicitudCompra getDetalleSeleccionado()
/* 547:    */   {
/* 548:543 */     return this.detalleSeleccionado;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public void setDetalleSeleccionado(DetalleSolicitudCompra detalleSeleccionado)
/* 552:    */   {
/* 553:547 */     this.detalleSeleccionado = detalleSeleccionado;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void cargarSaldoProducto()
/* 557:    */   {
/* 558:551 */     this.totalSaldoStock = BigDecimal.ZERO;
/* 559:552 */     this.totalSaldoAlmacenamiento = BigDecimal.ZERO;
/* 560:553 */     this.producto = this.detalleSeleccionado.getProducto();
/* 561:554 */     this.listaSaldoProducto = this.servicioProducto.obtenerSaldos(this.producto.getId(), Calendar.getInstance().getTime(), null);
/* 562:555 */     for (SaldoProducto sp : this.listaSaldoProducto) {
/* 563:    */       try
/* 564:    */       {
/* 565:559 */         this.totalSaldoStock = this.totalSaldoStock.add(sp.getSaldo());
/* 566:    */         
/* 567:561 */         BigDecimal cantidad = this.servicioProducto.convierteUnidad(this.producto.getUnidad(), this.producto.getUnidadAlmacenamiento(), this.producto.getId(), sp
/* 568:562 */           .getSaldo());
/* 569:563 */         cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 4);
/* 570:564 */         sp.setSaldoUnidadAlmacenamiento(cantidad);
/* 571:    */         
/* 572:566 */         this.totalSaldoAlmacenamiento = this.totalSaldoAlmacenamiento.add(cantidad);
/* 573:    */       }
/* 574:    */       catch (ExcepcionAS2 localExcepcionAS2) {}
/* 575:    */     }
/* 576:571 */     this.detalleSeleccionado.setSaldoProductoActual(this.totalSaldoStock);
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void eliminarDetalleConsolidado()
/* 580:    */   {
/* 581:575 */     DetalleSolicitudCompra detallePadre = this.detalleSeleccionado.getDetalleSolicitudCompraPadre();
/* 582:    */     try
/* 583:    */     {
/* 584:577 */       this.detalleSeleccionado.setEliminadoDesdeConsolidacion(true);
/* 585:578 */       boolean tieneDetalles = false;
/* 586:579 */       for (DetalleSolicitudCompra det : detallePadre.getListaDetalleSolicitudCompra()) {
/* 587:580 */         if (!det.isEliminadoDesdeConsolidacion())
/* 588:    */         {
/* 589:581 */           tieneDetalles = true;
/* 590:582 */           break;
/* 591:    */         }
/* 592:    */       }
/* 593:585 */       if (!tieneDetalles) {
/* 594:586 */         detallePadre.setEliminadoDesdeConsolidacion(true);
/* 595:    */       }
/* 596:588 */       this.raiz = null;
/* 597:    */     }
/* 598:    */     catch (Exception e)
/* 599:    */     {
/* 600:590 */       this.detalleSeleccionado.setDetalleSolicitudCompraPadre(detallePadre);
/* 601:591 */       detallePadre.setSolicitudCompra(getSolicitudCompra());
/* 602:592 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 603:    */     }
/* 604:    */   }
/* 605:    */   
/* 606:    */   public List<SaldoProducto> getListaSaldoProducto()
/* 607:    */   {
/* 608:597 */     return this.listaSaldoProducto;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void setListaSaldoProducto(List<SaldoProducto> listaSaldoProducto)
/* 612:    */   {
/* 613:601 */     this.listaSaldoProducto = listaSaldoProducto;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public Producto getProducto()
/* 617:    */   {
/* 618:605 */     return this.producto;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public void setProducto(Producto producto)
/* 622:    */   {
/* 623:609 */     this.producto = producto;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public BigDecimal getTotalSaldoStock()
/* 627:    */   {
/* 628:613 */     return this.totalSaldoStock;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void setTotalSaldoStock(BigDecimal totalSaldoStock)
/* 632:    */   {
/* 633:617 */     this.totalSaldoStock = totalSaldoStock;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public BigDecimal getTotalSaldoAlmacenamiento()
/* 637:    */   {
/* 638:621 */     return this.totalSaldoAlmacenamiento;
/* 639:    */   }
/* 640:    */   
/* 641:    */   public void setTotalSaldoAlmacenamiento(BigDecimal totalSaldoAlmacenamiento)
/* 642:    */   {
/* 643:625 */     this.totalSaldoAlmacenamiento = totalSaldoAlmacenamiento;
/* 644:    */   }
/* 645:    */   
/* 646:    */   public void cargarListaDePrecios()
/* 647:    */   {
/* 648:629 */     this.totalCantidad = BigDecimal.ZERO;
/* 649:630 */     this.totalPrecioPactado = BigDecimal.ZERO;
/* 650:631 */     this.totalPrecioUC = BigDecimal.ZERO;
/* 651:632 */     this.producto = this.detalleSeleccionado.getProducto();
/* 652:633 */     this.listProveedores = this.servicioEmpresa.obtenerProveedoresProductoEspecifico(this.producto.getId());
/* 653:    */   }
/* 654:    */   
/* 655:    */   public List<ListaPrecios> autocompletarListaPrecios(String consulta)
/* 656:    */   {
/* 657:637 */     return this.servicioListaPrecios.autocompletarListaPrecios(consulta, true, AppUtil.getOrganizacion().getId());
/* 658:    */   }
/* 659:    */   
/* 660:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 661:    */   {
/* 662:641 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 663:    */   }
/* 664:    */   
/* 665:    */   public List<ProductoUltimaCompra> getListProveedores()
/* 666:    */   {
/* 667:645 */     return this.listProveedores;
/* 668:    */   }
/* 669:    */   
/* 670:    */   public void setListProveedorListaPrecios(List<ProductoUltimaCompra> listProveedores)
/* 671:    */   {
/* 672:649 */     this.listProveedores = listProveedores;
/* 673:    */   }
/* 674:    */   
/* 675:    */   public void actualizarProveedores(Empresa empresa)
/* 676:    */   {
/* 677:653 */     for (ProductoUltimaCompra e : this.listProveedores) {
/* 678:654 */       if (e.getEmpresa().getId() != empresa.getId()) {
/* 679:655 */         e.getEmpresa().setIndicadorProveedorSeleccionado(false);
/* 680:    */       }
/* 681:    */     }
/* 682:    */   }
/* 683:    */   
/* 684:    */   public void crearDetalleProveedor()
/* 685:    */   {
/* 686:    */     try
/* 687:    */     {
/* 688:661 */       this.servicioSolicitudCompra.crearDetalleProveedor(getSolicitudCompra(), this.listProveedores, this.detalleSeleccionado);
/* 689:662 */       RequestContext.getCurrentInstance().execute("proveedoresDialog.hide()");
/* 690:    */       
/* 691:664 */       this.dtProveedoresListaPrecios.reset();
/* 692:    */     }
/* 693:    */     catch (AS2Exception e)
/* 694:    */     {
/* 695:666 */       e.printStackTrace();
/* 696:667 */       JsfUtil.addErrorMessage(e, "");
/* 697:    */     }
/* 698:    */     catch (ExcepcionAS2 e)
/* 699:    */     {
/* 700:669 */       e.printStackTrace();
/* 701:670 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 702:    */     }
/* 703:    */   }
/* 704:    */   
/* 705:    */   public void crearPedidoProveedor()
/* 706:    */   {
/* 707:    */     try
/* 708:    */     {
/* 709:676 */       this.servicioSolicitudCompra.crearPedidoProveedor(getSolicitudCompra(), this.listaDetallesPedidoProveedor, AppUtil.getBodega());
/* 710:677 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 711:    */     }
/* 712:    */     catch (ExcepcionAS2 e)
/* 713:    */     {
/* 714:679 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 715:    */     }
/* 716:    */     catch (AS2Exception e)
/* 717:    */     {
/* 718:681 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 719:    */     }
/* 720:    */   }
/* 721:    */   
/* 722:    */   public void cerrarConsolidacion()
/* 723:    */   {
/* 724:686 */     this.servicioSolicitudCompra.cerrarConsolidacion(getSolicitudCompra().getId());
/* 725:    */   }
/* 726:    */   
/* 727:    */   public List<DetalleSolicitudCompraProveedor> getListaDetallesPedidoProveedor()
/* 728:    */   {
/* 729:690 */     return this.listaDetallesPedidoProveedor;
/* 730:    */   }
/* 731:    */   
/* 732:    */   public void setListaDetallesPedidoProveedor(List<DetalleSolicitudCompraProveedor> listaDetallesPedidoProveedor)
/* 733:    */   {
/* 734:694 */     this.listaDetallesPedidoProveedor = listaDetallesPedidoProveedor;
/* 735:    */   }
/* 736:    */   
/* 737:    */   public List<DetalleSolicitudCompraProveedor> getListaDetallesPorSolicitar()
/* 738:    */   {
/* 739:698 */     return this.listaDetallesPorSolicitar;
/* 740:    */   }
/* 741:    */   
/* 742:    */   public void setListaDetallesPorSolicitar(List<DetalleSolicitudCompraProveedor> listaDetallesPorSolicitar)
/* 743:    */   {
/* 744:702 */     this.listaDetallesPorSolicitar = listaDetallesPorSolicitar;
/* 745:    */   }
/* 746:    */   
/* 747:    */   public List<DetalleSolicitudCompraProveedor> getListaDetallesConsolidados()
/* 748:    */   {
/* 749:706 */     this.listaDetallesConsolidados = new ArrayList();
/* 750:707 */     Map<Integer, List<DetalleSolicitudCompraProveedor>> hashDetalles = new HashMap();
/* 751:708 */     Set<String> listaProveedores = new HashSet();
/* 752:709 */     for (DetalleSolicitudCompraProveedor det : getSolicitudCompra().getListaDetalleSolicitudCompraProveedor()) {
/* 753:710 */       if ((!det.isEliminado()) && (!listaProveedores.contains(det.getEmpresa().getId() + "~" + det.getProducto().getId())))
/* 754:    */       {
/* 755:711 */         if (hashDetalles.containsKey(Integer.valueOf(det.getEmpresa().getId())))
/* 756:    */         {
/* 757:712 */           ((List)hashDetalles.get(Integer.valueOf(det.getEmpresa().getId()))).add(det);
/* 758:    */         }
/* 759:    */         else
/* 760:    */         {
/* 761:714 */           List<DetalleSolicitudCompraProveedor> list = new ArrayList();
/* 762:715 */           list.add(det);
/* 763:716 */           hashDetalles.put(Integer.valueOf(det.getEmpresa().getId()), list);
/* 764:    */         }
/* 765:718 */         listaProveedores.add(det.getEmpresa().getId() + "~" + det.getProducto().getId());
/* 766:    */       }
/* 767:    */     }
/* 768:721 */     this.hashTotalesProveedores = new HashMap();
/* 769:722 */     this.totalCompra = BigDecimal.ZERO;
/* 770:723 */     for (Integer id : hashDetalles.keySet())
/* 771:    */     {
/* 772:724 */       BigDecimal tot = BigDecimal.ZERO;
/* 773:725 */       for (DetalleSolicitudCompraProveedor det : (List)hashDetalles.get(id)) {
/* 774:726 */         tot = tot.add(det.getTotal());
/* 775:    */       }
/* 776:728 */       this.hashTotalesProveedores.put(id, tot);
/* 777:729 */       this.totalCompra = this.totalCompra.add(tot);
/* 778:730 */       this.listaDetallesConsolidados.addAll((Collection)hashDetalles.get(id));
/* 779:    */     }
/* 780:732 */     return this.listaDetallesConsolidados;
/* 781:    */   }
/* 782:    */   
/* 783:    */   public void actualizarValoresAprobados()
/* 784:    */   {
/* 785:736 */     this.hashTotalesAprobados = new HashMap();
/* 786:    */     
/* 787:738 */     this.totalAprobado = BigDecimal.ZERO;
/* 788:739 */     for (DetalleSolicitudCompraProveedor dscp : this.listaDetallesPedidoProveedor)
/* 789:    */     {
/* 790:740 */       BigDecimal total = (BigDecimal)this.hashTotalesAprobados.get(Integer.valueOf(dscp.getEmpresa().getId()));
/* 791:741 */       this.totalAprobado = this.totalAprobado.add(dscp.getTotal());
/* 792:742 */       if (total != null)
/* 793:    */       {
/* 794:743 */         total = total.add(dscp.getTotal());
/* 795:744 */         this.hashTotalesAprobados.put(Integer.valueOf(dscp.getEmpresa().getId()), total);
/* 796:    */       }
/* 797:    */       else
/* 798:    */       {
/* 799:746 */         total = dscp.getTotal();
/* 800:747 */         this.hashTotalesAprobados.put(Integer.valueOf(dscp.getEmpresa().getId()), total);
/* 801:    */       }
/* 802:    */     }
/* 803:    */   }
/* 804:    */   
/* 805:    */   public BigDecimal getTotalProveedor(int idProveedor, boolean valorAprobado)
/* 806:    */   {
/* 807:    */     BigDecimal total;
/* 808:    */     BigDecimal total;
/* 809:754 */     if (valorAprobado) {
/* 810:755 */       total = (BigDecimal)this.hashTotalesAprobados.get(Integer.valueOf(idProveedor));
/* 811:    */     } else {
/* 812:757 */       total = (BigDecimal)this.hashTotalesProveedores.get(Integer.valueOf(idProveedor));
/* 813:    */     }
/* 814:759 */     if (total == null) {
/* 815:760 */       total = BigDecimal.ZERO;
/* 816:    */     }
/* 817:762 */     return total;
/* 818:    */   }
/* 819:    */   
/* 820:    */   public void setListaDetallesConsolidados(List<DetalleSolicitudCompraProveedor> listaDetallesConsolidados)
/* 821:    */   {
/* 822:766 */     this.listaDetallesConsolidados = listaDetallesConsolidados;
/* 823:    */   }
/* 824:    */   
/* 825:    */   public BigDecimal getTotalCompra()
/* 826:    */   {
/* 827:770 */     return FuncionesUtiles.redondearBigDecimal(this.totalCompra);
/* 828:    */   }
/* 829:    */   
/* 830:    */   public Integer getIdSolicitudCompra()
/* 831:    */   {
/* 832:774 */     return this.idSolicitudCompra;
/* 833:    */   }
/* 834:    */   
/* 835:    */   public void setIdSolicitudCompra(Integer idSolicitudCompra)
/* 836:    */   {
/* 837:778 */     this.idSolicitudCompra = idSolicitudCompra;
/* 838:    */   }
/* 839:    */   
/* 840:    */   public BigDecimal getTotalAprobado()
/* 841:    */   {
/* 842:782 */     return this.totalAprobado;
/* 843:    */   }
/* 844:    */   
/* 845:    */   public DataTable getDtProveedoresListaPrecios()
/* 846:    */   {
/* 847:786 */     return this.dtProveedoresListaPrecios;
/* 848:    */   }
/* 849:    */   
/* 850:    */   public void setDtProveedoresListaPrecios(DataTable dtProveedoresListaPrecios)
/* 851:    */   {
/* 852:790 */     this.dtProveedoresListaPrecios = dtProveedoresListaPrecios;
/* 853:    */   }
/* 854:    */   
/* 855:    */   public BigDecimal getTotalCantidad()
/* 856:    */   {
/* 857:794 */     return this.totalCantidad;
/* 858:    */   }
/* 859:    */   
/* 860:    */   public void setTotalCantidad(BigDecimal totalCantidad)
/* 861:    */   {
/* 862:798 */     this.totalCantidad = totalCantidad;
/* 863:    */   }
/* 864:    */   
/* 865:    */   public BigDecimal getTotalPrecioPactado()
/* 866:    */   {
/* 867:802 */     return this.totalPrecioPactado;
/* 868:    */   }
/* 869:    */   
/* 870:    */   public void setTotalPrecioPactado(BigDecimal totalPrecioPactado)
/* 871:    */   {
/* 872:806 */     this.totalPrecioPactado = totalPrecioPactado;
/* 873:    */   }
/* 874:    */   
/* 875:    */   public BigDecimal getTotalPrecioUC()
/* 876:    */   {
/* 877:810 */     return this.totalPrecioUC;
/* 878:    */   }
/* 879:    */   
/* 880:    */   public void setTotalPrecioUC(BigDecimal totalPrecioUC)
/* 881:    */   {
/* 882:814 */     this.totalPrecioUC = totalPrecioUC;
/* 883:    */   }
/* 884:    */   
/* 885:    */   public List<SolicitudCompra> getListaSolicitudCompraSeleccionada()
/* 886:    */   {
/* 887:818 */     if (this.listaSolicitudCompraSeleccionada == null) {
/* 888:819 */       this.listaSolicitudCompraSeleccionada = new ArrayList();
/* 889:    */     }
/* 890:821 */     return this.listaSolicitudCompraSeleccionada;
/* 891:    */   }
/* 892:    */   
/* 893:    */   public void setListaSolicitudCompraSeleccionada(List<SolicitudCompra> listaSolicitudCompraSeleccionada)
/* 894:    */   {
/* 895:825 */     this.listaSolicitudCompraSeleccionada = listaSolicitudCompraSeleccionada;
/* 896:    */   }
/* 897:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.ConsolidarSolicitudCompraBean
 * JD-Core Version:    0.7.0.1
 */