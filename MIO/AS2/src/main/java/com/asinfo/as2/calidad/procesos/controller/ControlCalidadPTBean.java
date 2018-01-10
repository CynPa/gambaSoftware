/*   1:    */ package com.asinfo.as2.calidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.calidad.procesos.servicio.ServicioControlCalidad;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  10:    */ import com.asinfo.as2.entities.Lote;
/*  11:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.Unidad;
/*  16:    */ import com.asinfo.as2.entities.calidad.CategoriaVariableCalidad;
/*  17:    */ import com.asinfo.as2.entities.calidad.MotivoCastigoCalidad;
/*  18:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*  19:    */ import com.asinfo.as2.entities.calidad.VariableCalidadOrdenFabricacion;
/*  20:    */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*  21:    */ import com.asinfo.as2.entities.calidad.VariableCalidadRegistroPeso;
/*  22:    */ import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
/*  23:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  24:    */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*  25:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  26:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  27:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  28:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  30:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  31:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  32:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  33:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  34:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  35:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioHistoricoCalidadOrdenFabricacion;
/*  36:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  37:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  38:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  39:    */ import com.asinfo.as2.util.AppUtil;
/*  40:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  41:    */ import com.asinfo.as2.utils.JsfUtil;
/*  42:    */ import java.io.BufferedInputStream;
/*  43:    */ import java.io.IOException;
/*  44:    */ import java.io.InputStream;
/*  45:    */ import java.math.BigDecimal;
/*  46:    */ import java.math.RoundingMode;
/*  47:    */ import java.util.ArrayList;
/*  48:    */ import java.util.Date;
/*  49:    */ import java.util.HashMap;
/*  50:    */ import java.util.List;
/*  51:    */ import java.util.Map;
/*  52:    */ import java.util.Map.Entry;
/*  53:    */ import javax.annotation.PostConstruct;
/*  54:    */ import javax.ejb.EJB;
/*  55:    */ import javax.faces.bean.ManagedBean;
/*  56:    */ import javax.faces.bean.ViewScoped;
/*  57:    */ import javax.faces.model.SelectItem;
/*  58:    */ import org.apache.log4j.Logger;
/*  59:    */ import org.primefaces.component.datatable.DataTable;
/*  60:    */ import org.primefaces.context.RequestContext;
/*  61:    */ import org.primefaces.event.FileUploadEvent;
/*  62:    */ import org.primefaces.event.SelectEvent;
/*  63:    */ import org.primefaces.event.ToggleEvent;
/*  64:    */ import org.primefaces.model.LazyDataModel;
/*  65:    */ import org.primefaces.model.SortOrder;
/*  66:    */ import org.primefaces.model.UploadedFile;
/*  67:    */ 
/*  68:    */ @ManagedBean
/*  69:    */ @ViewScoped
/*  70:    */ public class ControlCalidadPTBean
/*  71:    */   extends PageControllerAS2
/*  72:    */ {
/*  73:    */   private static final long serialVersionUID = 1L;
/*  74:    */   @EJB
/*  75:    */   protected transient ServicioHistoricoCalidadOrdenFabricacion servicioHistoricoCalidadOrdenFabricacion;
/*  76:    */   @EJB
/*  77:    */   protected transient ServicioControlCalidad servicioControlCalidad;
/*  78:    */   @EJB
/*  79:    */   protected transient ServicioBodega servicioBodega;
/*  80:    */   @EJB
/*  81:    */   protected transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  82:    */   @EJB
/*  83:    */   protected transient ServicioProducto servicioProducto;
/*  84:    */   @EJB
/*  85:    */   protected transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  86:    */   @EJB
/*  87:    */   protected transient ServicioListaPrecios servicioListaPrecios;
/*  88:    */   @EJB
/*  89:    */   protected transient ServicioGenerico<MotivoCastigoCalidad> servicioMotivoCastigoCalidad;
/*  90:    */   @EJB
/*  91:    */   protected transient ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  92:    */   @EJB
/*  93:    */   protected transient ServicioLote servicioLote;
/*  94:    */   @EJB
/*  95:    */   protected transient ServicioMovimientoInventario servicioMovimientoInventario;
/*  96:    */   private LazyDataModel<OrdenFabricacion> listaOrdenFabricacion;
/*  97:    */   private SelectItem[] listaEstadoControlCalidadItem;
/*  98:    */   private DataTable dtHistoricoCalidadOrdenFabricacion;
/*  99:    */   private DataTable dtSubordenInmediata;
/* 100:    */   private DataTable dtOrdenFabricacion;
/* 101:108 */   private Map<CategoriaVariableCalidad, List<VariableCalidadOrdenFabricacion>> mapaCategoriaVariableCalidad = new HashMap();
/* 102:    */   private HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion;
/* 103:    */   private Bodega bodegaCalidad;
/* 104:    */   private Producto productoNuevo;
/* 105:112 */   private Boolean indicadorBusquedaPorOrdenFabricacion = Boolean.valueOf(true);
/* 106:    */   private MotivoCastigoCalidad motivoReprocesar;
/* 107:    */   private List<Bodega> listaBodegaCalidad;
/* 108:    */   private List<MotivoCastigoCalidad> listaMotivoCastigoCalidad;
/* 109:    */   private boolean indicadorCambiarProducto;
/* 110:    */   private List<Bodega> listaBodega;
/* 111:    */   private String cantidadTransformacion;
/* 112:    */   
/* 113:    */   @PostConstruct
/* 114:    */   public void init()
/* 115:    */   {
/* 116:122 */     this.listaOrdenFabricacion = new LazyDataModel()
/* 117:    */     {
/* 118:    */       private static final long serialVersionUID = 1L;
/* 119:    */       
/* 120:    */       public List<OrdenFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 121:    */       {
/* 122:129 */         List<OrdenFabricacion> lista = new ArrayList();
/* 123:130 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 124:132 */         if (filters.size() == 0) {
/* 125:134 */           filters.put("estado", "!=" + EstadoProduccionEnum.FINALIZADA.toString());
/* 126:    */         }
/* 127:136 */         filters = ControlCalidadPTBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/* 128:137 */         filters.put("indicadorRegistradoCalidad", "=true");
/* 129:138 */         lista = ControlCalidadPTBean.this.servicioOrdenFabricacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters, true);
/* 130:    */         
/* 131:140 */         ControlCalidadPTBean.this.listaOrdenFabricacion.setRowCount(ControlCalidadPTBean.this.servicioOrdenFabricacion.contarPorCriterio(filters));
/* 132:    */         
/* 133:142 */         return lista;
/* 134:    */       }
/* 135:    */     };
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String editar()
/* 139:    */   {
/* 140:150 */     if ((this.historicoCalidadOrdenFabricacion != null) && (this.historicoCalidadOrdenFabricacion.getId() != 0))
/* 141:    */     {
/* 142:153 */       if ((!this.historicoCalidadOrdenFabricacion.getEstado().equals(EstadoControlCalidad.CUARENTENA)) && 
/* 143:154 */         (!this.historicoCalidadOrdenFabricacion.getEstado().equals(EstadoControlCalidad.RECHAZADO)))
/* 144:    */       {
/* 145:155 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 146:156 */         return "";
/* 147:    */       }
/* 148:160 */       this.servicioControlCalidad.cargaListaVariableCalidadOrdenFabricacion(this.historicoCalidadOrdenFabricacion);
/* 149:    */       
/* 150:    */ 
/* 151:163 */       actualizaValorNIRAceptable();
/* 152:    */       
/* 153:    */ 
/* 154:166 */       clasificaVariables();
/* 155:    */       
/* 156:168 */       setEditado(true);
/* 157:    */     }
/* 158:    */     else
/* 159:    */     {
/* 160:170 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 161:    */     }
/* 162:172 */     return "";
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void actualizaValorNIRAceptable()
/* 166:    */   {
/* 167:179 */     for (VariableCalidadOrdenFabricacion vcof : this.historicoCalidadOrdenFabricacion.getListaVariableCalidadOrdenFabricacion()) {
/* 168:180 */       this.servicioControlCalidad.actualizaValorNIRAceptable(vcof);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void actualizaValorNIRAceptable(VariableCalidadOrdenFabricacion vcof)
/* 173:    */   {
/* 174:190 */     this.servicioControlCalidad.actualizaValorNIRAceptable(vcof);
/* 175:    */   }
/* 176:    */   
/* 177:    */   private void clasificaVariables()
/* 178:    */   {
/* 179:197 */     for (VariableCalidadOrdenFabricacion vcof : this.historicoCalidadOrdenFabricacion.getListaVariableCalidadOrdenFabricacion())
/* 180:    */     {
/* 181:198 */       CategoriaVariableCalidad cvc = vcof.getVariableCalidadProducto().getVariableCalidad().getCategoriaVariableCalidad();
/* 182:199 */       if (!this.mapaCategoriaVariableCalidad.containsKey(cvc)) {
/* 183:200 */         this.mapaCategoriaVariableCalidad.put(cvc, new ArrayList());
/* 184:    */       }
/* 185:202 */       ((List)this.mapaCategoriaVariableCalidad.get(cvc)).add(vcof);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String guardar()
/* 190:    */   {
/* 191:208 */     if (this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion() != null)
/* 192:    */     {
/* 193:209 */       this.historicoCalidadOrdenFabricacion.setEstado(EstadoControlCalidad.CUARENTENA);
/* 194:210 */       this.historicoCalidadOrdenFabricacion.setCantidadControlCalidad(this.historicoCalidadOrdenFabricacion.getCantidad());
/* 195:211 */       RequestContext context = RequestContext.getCurrentInstance();
/* 196:212 */       context.update(":form:panelProcesar");
/* 197:213 */       context.execute("dialogProcesar.show();");
/* 198:    */     }
/* 199:    */     else
/* 200:    */     {
/* 201:215 */       this.historicoCalidadOrdenFabricacion.setCantidadControlCalidad(this.historicoCalidadOrdenFabricacion.getCantidad());
/* 202:216 */       this.historicoCalidadOrdenFabricacion.setEstado(EstadoControlCalidad.CUARENTENA);
/* 203:217 */       procesar();
/* 204:    */     }
/* 205:219 */     return "";
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String eliminar()
/* 209:    */   {
/* 210:224 */     if ((this.historicoCalidadOrdenFabricacion != null) && (this.historicoCalidadOrdenFabricacion.getId() != 0))
/* 211:    */     {
/* 212:225 */       if (this.historicoCalidadOrdenFabricacion.getEstado().equals(EstadoControlCalidad.CUARENTENA)) {
/* 213:226 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 214:    */       }
/* 215:    */     }
/* 216:    */     else {
/* 217:231 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 218:    */     }
/* 219:233 */     return "";
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String limpiar()
/* 223:    */   {
/* 224:238 */     this.mapaCategoriaVariableCalidad = new HashMap();
/* 225:239 */     this.bodegaCalidad = null;
/* 226:240 */     this.motivoReprocesar = null;
/* 227:241 */     if (getListaMotivoCastigoCalidad().size() > 0) {
/* 228:242 */       this.motivoReprocesar = ((MotivoCastigoCalidad)getListaMotivoCastigoCalidad().get(0));
/* 229:    */     }
/* 230:244 */     crearHistorico();
/* 231:245 */     this.productoNuevo = null;
/* 232:246 */     this.indicadorCambiarProducto = false;
/* 233:247 */     this.cantidadTransformacion = null;
/* 234:248 */     return "";
/* 235:    */   }
/* 236:    */   
/* 237:    */   private void crearHistorico()
/* 238:    */   {
/* 239:252 */     this.historicoCalidadOrdenFabricacion = new HistoricoCalidadOrdenFabricacion();
/* 240:253 */     this.historicoCalidadOrdenFabricacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 241:254 */     this.historicoCalidadOrdenFabricacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 242:255 */     this.historicoCalidadOrdenFabricacion.setEstado(EstadoControlCalidad.CUARENTENA);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public String cargarDatos()
/* 246:    */   {
/* 247:260 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String cargarExcel(FileUploadEvent event)
/* 251:    */   {
/* 252:    */     try
/* 253:    */     {
/* 254:266 */       InputStream istream = new BufferedInputStream(event.getFile().getInputstream());
/* 255:267 */       this.historicoCalidadOrdenFabricacion = this.servicioControlCalidad.cargarExcel(istream, this.historicoCalidadOrdenFabricacion);
/* 256:268 */       actualizaValorNIRAceptable();
/* 257:269 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 258:    */     }
/* 259:    */     catch (ExcepcionAS2 e)
/* 260:    */     {
/* 261:272 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 262:273 */       LOG.error("ERROR AL CARGAR EXCEL", e);
/* 263:    */     }
/* 264:    */     catch (IOException e)
/* 265:    */     {
/* 266:275 */       JsfUtil.addErrorMessage(e.getMessage());
/* 267:276 */       e.printStackTrace();
/* 268:    */     }
/* 269:279 */     return null;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public List<OrdenFabricacion> autocompletarOrdenFabricacion(String consulta)
/* 273:    */   {
/* 274:283 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 275:284 */     return this.servicioOrdenFabricacion.autocompletarOrdenesAbiertas(idOrganizacion, consulta, null, null, Boolean.valueOf(false));
/* 276:    */   }
/* 277:    */   
/* 278:    */   public List<DetalleMovimientoInventario> autocompletarDetalleIngresoFabricacion(String consulta)
/* 279:    */   {
/* 280:288 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 281:289 */     return this.servicioMovimientoInventario.autocompletarIngresosFabricacion(idOrganizacion, consulta, null);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void detalleIngresoFabricacionValueChanged()
/* 285:    */   {
/* 286:294 */     if ((this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion() != null) || 
/* 287:295 */       (this.historicoCalidadOrdenFabricacion.getOrdenFabricacion() != null))
/* 288:    */     {
/* 289:300 */       OrdenFabricacion ordenFabricacion = this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion() == null ? this.historicoCalidadOrdenFabricacion.getOrdenFabricacion() : this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion().getMovimientoInventario().getOrdenFabricacion();
/* 290:301 */       this.historicoCalidadOrdenFabricacion.setOrdenFabricacion(ordenFabricacion);
/* 291:    */       
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:306 */       Lote lote = (this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion() != null) && (this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion().getLote() != null) ? this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion().getLote() : null;
/* 296:309 */       if (ordenFabricacion.getProducto().isIndicadorLote())
/* 297:    */       {
/* 298:310 */         if (lote == null)
/* 299:    */         {
/* 300:311 */           lote = new Lote();
/* 301:312 */           lote.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 302:313 */           lote.setActivo(true);
/* 303:314 */           lote.setCodigo(ordenFabricacion.getNumero());
/* 304:315 */           lote.setFechaCreacion(new Date());
/* 305:316 */           lote.setIdSucursal(AppUtil.getSucursal().getId());
/* 306:317 */           lote.setIndicadorMovimientoInterno(true);
/* 307:318 */           lote.setProducto(ordenFabricacion.getProducto());
/* 308:319 */           Date fechaCaducidad = FuncionesUtiles.sumarFechaDiasMeses(new Date(), ordenFabricacion.getProducto().getDiasCaducidad().intValue());
/* 309:320 */           lote.setFechaCaducidad(fechaCaducidad);
/* 310:    */         }
/* 311:322 */         this.historicoCalidadOrdenFabricacion.setLote(lote);
/* 312:    */       }
/* 313:326 */       BigDecimal cantidad = this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion() == null ? BigDecimal.ZERO : this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion().getCantidad();
/* 314:    */       
/* 315:    */ 
/* 316:329 */       this.historicoCalidadOrdenFabricacion.setCantidad(cantidad);
/* 317:330 */       this.mapaCategoriaVariableCalidad = new HashMap();
/* 318:    */       
/* 319:    */ 
/* 320:333 */       this.servicioControlCalidad.cargaListaVariableCalidadOrdenFabricacion(this.historicoCalidadOrdenFabricacion);
/* 321:    */       
/* 322:    */ 
/* 323:336 */       actualizaValorNIRAceptable();
/* 324:    */       
/* 325:    */ 
/* 326:339 */       clasificaVariables();
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void changeBusquedaOrdenFabricacion()
/* 331:    */   {
/* 332:348 */     this.historicoCalidadOrdenFabricacion.setDetalleIngresoFabricacion(null);
/* 333:349 */     this.historicoCalidadOrdenFabricacion.setOrdenFabricacion(null);
/* 334:350 */     this.historicoCalidadOrdenFabricacion.setCantidad(BigDecimal.ZERO);
/* 335:351 */     this.mapaCategoriaVariableCalidad = new HashMap();
/* 336:352 */     this.servicioControlCalidad.cargaListaVariableCalidadOrdenFabricacion(this.historicoCalidadOrdenFabricacion);
/* 337:353 */     actualizaValorNIRAceptable();
/* 338:354 */     clasificaVariables();
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void cargarHistoricoCalidadOrdenFabricacion(ToggleEvent event)
/* 342:    */   {
/* 343:358 */     OrdenFabricacion ordenFabricacion = (OrdenFabricacion)event.getData();
/* 344:359 */     HashMap<String, String> filters = new HashMap();
/* 345:360 */     filters.put("ordenFabricacion.idOrdenFabricacion", "" + ordenFabricacion.getIdOrdenFabricacion());
/* 346:361 */     List<HistoricoCalidadOrdenFabricacion> lista = this.servicioHistoricoCalidadOrdenFabricacion.obtenerListaPorPagina(0, 1000, "fechaControlCalidad", false, filters);
/* 347:    */     
/* 348:363 */     ordenFabricacion.setListaHistoricoCalidadOrdenFabricacion(lista);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void onRowSelectHistoricoCalidadOrdenFabricacion(SelectEvent event)
/* 352:    */   {
/* 353:367 */     this.historicoCalidadOrdenFabricacion = ((HistoricoCalidadOrdenFabricacion)event.getObject());
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<Map.Entry<CategoriaVariableCalidad, List<VariableCalidadRegistroPeso>>> getMapaCVCEntryList()
/* 357:    */   {
/* 358:372 */     return new ArrayList(this.mapaCategoriaVariableCalidad.entrySet());
/* 359:    */   }
/* 360:    */   
/* 361:    */   public SelectItem[] getListaEstadoControlCalidadItem()
/* 362:    */   {
/* 363:379 */     if (this.listaEstadoControlCalidadItem == null)
/* 364:    */     {
/* 365:380 */       List<SelectItem> lista = new ArrayList();
/* 366:381 */       lista.add(new SelectItem("", ""));
/* 367:382 */       for (EstadoControlCalidad estadoCC : EstadoControlCalidad.values()) {
/* 368:383 */         lista.add(new SelectItem(estadoCC, estadoCC.getNombre()));
/* 369:    */       }
/* 370:385 */       this.listaEstadoControlCalidadItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 371:    */     }
/* 372:388 */     return this.listaEstadoControlCalidadItem;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void procesar()
/* 376:    */   {
/* 377:    */     try
/* 378:    */     {
/* 379:395 */       if ((this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion() != null) && (
/* 380:396 */         (this.historicoCalidadOrdenFabricacion.getCantidadControlCalidad().compareTo(BigDecimal.ZERO) <= 0) || 
/* 381:397 */         (this.historicoCalidadOrdenFabricacion.getCantidadControlCalidad().compareTo(this.historicoCalidadOrdenFabricacion.getCantidad()) > 0)))
/* 382:    */       {
/* 383:398 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cantidad_calidad_no_mayor_ingreso"));
/* 384:399 */         return;
/* 385:    */       }
/* 386:402 */       switch (2.$SwitchMap$com$asinfo$as2$enumeraciones$EstadoControlCalidad[this.historicoCalidadOrdenFabricacion.getEstado().ordinal()])
/* 387:    */       {
/* 388:    */       case 1: 
/* 389:407 */         if ((this.indicadorCambiarProducto) && (this.productoNuevo == null))
/* 390:    */         {
/* 391:408 */           addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_producto"));
/* 392:409 */           return;
/* 393:    */         }
/* 394:413 */         if (this.productoNuevo != null)
/* 395:    */         {
/* 396:414 */           this.productoNuevo = this.servicioProducto.cargaDetalle(this.productoNuevo.getId());
/* 397:415 */           this.historicoCalidadOrdenFabricacion.setProductoNuevo(this.productoNuevo);
/* 398:    */         }
/* 399:    */         break;
/* 400:    */       case 2: 
/* 401:    */         break;
/* 402:    */       case 3: 
/* 403:422 */         if (this.productoNuevo == null)
/* 404:    */         {
/* 405:423 */           addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_producto"));
/* 406:424 */           return;
/* 407:    */         }
/* 408:426 */         this.historicoCalidadOrdenFabricacion.setProductoNuevo(this.productoNuevo);
/* 409:427 */         break;
/* 410:    */       case 4: 
/* 411:    */         break;
/* 412:    */       }
/* 413:433 */       this.servicioControlCalidad.guardarHistoricoCalidadOrdenFabricacion(this.historicoCalidadOrdenFabricacion);
/* 414:434 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 415:435 */       limpiar();
/* 416:436 */       setEditado(false);
/* 417:    */     }
/* 418:    */     catch (AS2Exception e)
/* 419:    */     {
/* 420:438 */       limpiar();
/* 421:439 */       JsfUtil.addErrorMessage(e, "");
/* 422:    */     }
/* 423:    */     catch (ExcepcionAS2 e)
/* 424:    */     {
/* 425:441 */       limpiar();
/* 426:442 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 427:    */     }
/* 428:    */     catch (Exception e)
/* 429:    */     {
/* 430:444 */       limpiar();
/* 431:445 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 432:446 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 433:    */     }
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void cancelarProceso()
/* 437:    */   {
/* 438:451 */     this.indicadorCambiarProducto = false;
/* 439:452 */     this.historicoCalidadOrdenFabricacion.setEstado(null);
/* 440:453 */     this.productoNuevo = null;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public Bodega getBodegaCalidad()
/* 444:    */   {
/* 445:460 */     return this.bodegaCalidad;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setBodegaCalidad(Bodega bodegaCalidad)
/* 449:    */   {
/* 450:468 */     this.bodegaCalidad = bodegaCalidad;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public List<Bodega> getListaBodegaCalidad()
/* 454:    */   {
/* 455:475 */     if (this.listaBodegaCalidad == null)
/* 456:    */     {
/* 457:476 */       this.listaBodegaCalidad = new ArrayList();
/* 458:477 */       List<Bodega> lBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 459:478 */       for (Bodega bodega : lBodega) {
/* 460:479 */         if (!bodega.getClaseBodega().equals(ClaseBodegaEnum.CUARENTENA)) {
/* 461:480 */           this.listaBodegaCalidad.add(bodega);
/* 462:    */         }
/* 463:    */       }
/* 464:    */     }
/* 465:485 */     return this.listaBodegaCalidad;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public void setListaBodegaCalidad(List<Bodega> listaBodegaCalidad)
/* 469:    */   {
/* 470:493 */     this.listaBodegaCalidad = listaBodegaCalidad;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public List<MotivoCastigoCalidad> getListaMotivoCastigoCalidad()
/* 474:    */   {
/* 475:500 */     if (this.listaMotivoCastigoCalidad == null) {
/* 476:501 */       this.listaMotivoCastigoCalidad = this.servicioMotivoCastigoCalidad.obtenerListaCombo(MotivoCastigoCalidad.class, "nombre", true, 
/* 477:502 */         agregarFiltroOrganizacion(null));
/* 478:    */     }
/* 479:505 */     return this.listaMotivoCastigoCalidad;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setListaMotivoCastigoCalidad(List<MotivoCastigoCalidad> listaMotivoCastigoCalidad)
/* 483:    */   {
/* 484:513 */     this.listaMotivoCastigoCalidad = listaMotivoCastigoCalidad;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public HistoricoCalidadOrdenFabricacion getHistoricoCalidadOrdenFabricacion()
/* 488:    */   {
/* 489:517 */     return this.historicoCalidadOrdenFabricacion;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setHistoricoCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/* 493:    */   {
/* 494:521 */     this.historicoCalidadOrdenFabricacion = historicoCalidadOrdenFabricacion;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public LazyDataModel<OrdenFabricacion> getListaOrdenFabricacion()
/* 498:    */   {
/* 499:525 */     return this.listaOrdenFabricacion;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setListaOrdenFabricacion(LazyDataModel<OrdenFabricacion> listaOrdenFabricacion)
/* 503:    */   {
/* 504:529 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public DataTable getDtHistoricoCalidadOrdenFabricacion()
/* 508:    */   {
/* 509:533 */     return this.dtHistoricoCalidadOrdenFabricacion;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setDtHistoricoCalidadOrdenFabricacion(DataTable dtHistoricoCalidadOrdenFabricacion)
/* 513:    */   {
/* 514:537 */     this.dtHistoricoCalidadOrdenFabricacion = dtHistoricoCalidadOrdenFabricacion;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public Map<CategoriaVariableCalidad, List<VariableCalidadOrdenFabricacion>> getMapaCategoriaVariableCalidad()
/* 518:    */   {
/* 519:541 */     return this.mapaCategoriaVariableCalidad;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setMapaCategoriaVariableCalidad(Map<CategoriaVariableCalidad, List<VariableCalidadOrdenFabricacion>> mapaCategoriaVariableCalidad)
/* 523:    */   {
/* 524:545 */     this.mapaCategoriaVariableCalidad = mapaCategoriaVariableCalidad;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void setListaEstadoControlCalidadItem(SelectItem[] listaEstadoControlCalidadItem)
/* 528:    */   {
/* 529:549 */     this.listaEstadoControlCalidadItem = listaEstadoControlCalidadItem;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public DataTable getDtSubordenInmediata()
/* 533:    */   {
/* 534:553 */     return this.dtSubordenInmediata;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void setDtSubordenInmediata(DataTable dtSubordenInmediata)
/* 538:    */   {
/* 539:557 */     this.dtSubordenInmediata = dtSubordenInmediata;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public DataTable getDtOrdenFabricacion()
/* 543:    */   {
/* 544:561 */     return this.dtOrdenFabricacion;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public void setDtOrdenFabricacion(DataTable dtOrdenFabricacion)
/* 548:    */   {
/* 549:565 */     this.dtOrdenFabricacion = dtOrdenFabricacion;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public MotivoCastigoCalidad getMotivoReprocesar()
/* 553:    */   {
/* 554:569 */     return this.motivoReprocesar;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void setMotivoReprocesar(MotivoCastigoCalidad motivoReprocesar)
/* 558:    */   {
/* 559:573 */     this.motivoReprocesar = motivoReprocesar;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public Producto getProductoNuevo()
/* 563:    */   {
/* 564:577 */     return this.productoNuevo;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setProductoNuevo(Producto productoNuevo)
/* 568:    */   {
/* 569:581 */     this.productoNuevo = productoNuevo;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public void cargarProducto(Producto producto)
/* 573:    */   {
/* 574:585 */     this.productoNuevo = producto;
/* 575:586 */     actualizarConversion();
/* 576:    */   }
/* 577:    */   
/* 578:    */   public void actualizarConversion()
/* 579:    */   {
/* 580:591 */     Producto productoOrigen = this.historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion().getProducto();
/* 581:592 */     Producto productoDestino = this.productoNuevo;
/* 582:594 */     if (productoDestino != null)
/* 583:    */     {
/* 584:595 */       BigDecimal cantidadDestino = this.historicoCalidadOrdenFabricacion.getCantidadControlCalidad();
/* 585:596 */       this.cantidadTransformacion = (" = " + cantidadDestino.toString() + " " + this.productoNuevo.getUnidad().getCodigo());
/* 586:    */       try
/* 587:    */       {
/* 588:598 */         if (!productoDestino.getUnidad().equals(productoOrigen.getUnidad()))
/* 589:    */         {
/* 590:602 */           BigDecimal valorConversion = this.servicioProducto.convierteUnidad(productoOrigen.getUnidad(), productoDestino.getUnidad(), productoDestino.getIdProducto(), cantidadDestino).setScale(productoDestino.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 591:603 */           this.cantidadTransformacion = (" = " + valorConversion.toString() + " " + this.productoNuevo.getUnidad().getCodigo());
/* 592:    */         }
/* 593:    */       }
/* 594:    */       catch (ExcepcionAS2Inventario e)
/* 595:    */       {
/* 596:606 */         this.cantidadTransformacion = (" = " + getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 597:    */       }
/* 598:    */     }
/* 599:    */   }
/* 600:    */   
/* 601:    */   public Boolean getIndicadorBusquedaPorOrdenFabricacion()
/* 602:    */   {
/* 603:612 */     return this.indicadorBusquedaPorOrdenFabricacion;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void setIndicadorBusquedaPorOrdenFabricacion(Boolean indicadorBusquedaPorOrdenFabricacion)
/* 607:    */   {
/* 608:616 */     this.indicadorBusquedaPorOrdenFabricacion = indicadorBusquedaPorOrdenFabricacion;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public boolean isIndicadorCambiarProducto()
/* 612:    */   {
/* 613:620 */     return this.indicadorCambiarProducto;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public void setIndicadorCambiarProducto(boolean indicadorCambiarProducto)
/* 617:    */   {
/* 618:624 */     this.indicadorCambiarProducto = indicadorCambiarProducto;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public List<Bodega> getListaBodega()
/* 622:    */   {
/* 623:628 */     if (this.listaBodega == null) {
/* 624:629 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 625:    */     }
/* 626:631 */     return this.listaBodega;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 630:    */   {
/* 631:635 */     this.listaBodega = listaBodega;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public String getCantidadTransformacion()
/* 635:    */   {
/* 636:639 */     return this.cantidadTransformacion;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public void setCantidadTransformacion(String cantidadTransformacion)
/* 640:    */   {
/* 641:643 */     this.cantidadTransformacion = cantidadTransformacion;
/* 642:    */   }
/* 643:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.procesos.controller.ControlCalidadPTBean
 * JD-Core Version:    0.7.0.1
 */