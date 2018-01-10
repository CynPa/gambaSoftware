/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Chofer;
/*   6:    */ import com.asinfo.as2.entities.Ciudad;
/*   7:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   8:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   9:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  10:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.PedidoCliente;
/*  13:    */ import com.asinfo.as2.entities.Ruta;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  16:    */ import com.asinfo.as2.entities.TipoVehiculo;
/*  17:    */ import com.asinfo.as2.entities.Transportista;
/*  18:    */ import com.asinfo.as2.entities.Vehiculo;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  21:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  22:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioChofer;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVehiculo;
/*  29:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  30:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  31:    */ import com.asinfo.as2.util.AppUtil;
/*  32:    */ import com.asinfo.as2.utils.JsfUtil;
/*  33:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  34:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  35:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  36:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  37:    */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  38:    */ import java.util.ArrayList;
/*  39:    */ import java.util.Arrays;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.model.SelectItem;
/*  47:    */ import org.apache.log4j.Logger;
/*  48:    */ import org.primefaces.model.LazyDataModel;
/*  49:    */ import org.primefaces.model.SortOrder;
/*  50:    */ 
/*  51:    */ @ManagedBean
/*  52:    */ @ViewScoped
/*  53:    */ public class LogisticaClienteBean
/*  54:    */   extends PedidoClienteBean
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = 1L;
/*  57:    */   @EJB
/*  58:    */   private ServicioPedidoCliente servicioPedidoCliente;
/*  59:    */   @EJB
/*  60:    */   private ServicioTransportista servicioTransportista;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioChofer servicioChofer;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioVehiculo servicioVehiculo;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioGenerico<Ruta> servicioRuta;
/*  67:    */   @EJB
/*  68:    */   private transient ServicioSucursal servicioSucursal;
/*  69:    */   @EJB
/*  70:    */   private transient ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  71:    */   private List<Chofer> listaChoferCombo;
/*  72:    */   private List<Vehiculo> listaVehiculoCombo;
/*  73:    */   private List<Ruta> listaRuta;
/*  74:    */   private List<DetalleOrdenSalidaMaterial> listaDetalleOrden;
/*  75:    */   private LazyDataModel<OrdenSalidaMaterial> listaOrdenSalidaMaterial;
/*  76:    */   private SelectItem[] listaTipoCicloProduccionItem;
/*  77:    */   private Chofer chofer;
/*  78:    */   private OrdenSalidaMaterial ordenSalidaMaterial;
/*  79:    */   
/*  80:    */   static enum PROCESOS
/*  81:    */   {
/*  82: 66 */     PEDIDO_CLIENTE("Pedido Cliente"),  ORDEN_SALIDA_MATERIAL("Orden salida de material");
/*  83:    */     
/*  84:    */     private String nombre;
/*  85:    */     
/*  86:    */     private PROCESOS(String nombre)
/*  87:    */     {
/*  88: 76 */       this.nombre = nombre;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public String getNombre()
/*  92:    */     {
/*  93: 85 */       return this.nombre;
/*  94:    */     }
/*  95:    */     
/*  96:    */     public void setNombre(String nombre)
/*  97:    */     {
/*  98: 94 */       this.nombre = nombre;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:123 */   private PROCESOS proceso = PROCESOS.PEDIDO_CLIENTE;
/* 103:124 */   private boolean realizaLogisticaCliente = true;
/* 104:    */   private Sucursal sucursal;
/* 105:    */   private Transportista transportista;
/* 106:    */   private Vehiculo vehiculo;
/* 107:    */   
/* 108:    */   @PostConstruct
/* 109:    */   public void init()
/* 110:    */   {
/* 111:133 */     getListaProductoBean().setIndicadorVenta(true);
/* 112:134 */     getListaProductoBean().setActivo(true);
/* 113:135 */     this.sucursal = this.servicioSucursal.cargarDetalle(AppUtil.getSucursal().getId());
/* 114:    */     
/* 115:137 */     this.listaPedidoCliente = new LazyDataModel()
/* 116:    */     {
/* 117:    */       private static final long serialVersionUID = 1L;
/* 118:    */       
/* 119:    */       public List<PedidoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 120:    */       {
/* 121:144 */         List<PedidoCliente> lista = new ArrayList();
/* 122:    */         
/* 123:146 */         LogisticaClienteBean.this.agregarFiltroOrganizacion(filters);
/* 124:    */         
/* 125:148 */         filters.put("OR~01~01~estado", Estado.PROCESADO.toString());
/* 126:149 */         filters.put("OR~01~02~estado", Estado.REALIZADO_LOGISTICA.toString());
/* 127:150 */         filters.put("OR~02~01~indicadorTieneDespacho", "false");
/* 128:151 */         filters.put("OR~02~02~indicadorTieneDespacho", OperacionEnum.IS_NULL.toString());
/* 129:    */         
/* 130:153 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 131:    */         try
/* 132:    */         {
/* 133:156 */           lista = LogisticaClienteBean.this.servicioPedidoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 134:157 */           LogisticaClienteBean.this.listaPedidoCliente.setRowCount(LogisticaClienteBean.this.servicioPedidoCliente.contarPorCriterio(filters));
/* 135:    */         }
/* 136:    */         catch (Exception e)
/* 137:    */         {
/* 138:160 */           LogisticaClienteBean.this.addInfoMessage(LogisticaClienteBean.this.getLanguageController().getMensaje("msg_info_carga_datos"));
/* 139:161 */           LogisticaClienteBean.LOG.info("ERROR AL CARGAR DATOS PEDIDO CLIENTE", e);
/* 140:    */         }
/* 141:164 */         return lista;
/* 142:    */       }
/* 143:167 */     };
/* 144:168 */     this.listaOrdenSalidaMaterial = new LazyDataModel()
/* 145:    */     {
/* 146:    */       private static final long serialVersionUID = 1L;
/* 147:    */       
/* 148:    */       public List<OrdenSalidaMaterial> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 149:    */       {
/* 150:174 */         filters.put("indicadorTransferencia", String.valueOf(true));
/* 151:175 */         filters.put("OR~01~01~estado", Estado.ELABORADO.toString());
/* 152:176 */         filters.put("OR~01~02~estado", Estado.REALIZADO_LOGISTICA.toString());
/* 153:177 */         filters = LogisticaClienteBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/* 154:    */         
/* 155:179 */         List<OrdenSalidaMaterial> lista = new ArrayList();
/* 156:180 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 157:    */         
/* 158:182 */         lista = LogisticaClienteBean.this.servicioOrdenSalidaMaterial.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 159:    */         
/* 160:184 */         LogisticaClienteBean.this.listaOrdenSalidaMaterial.setRowCount(LogisticaClienteBean.this.servicioOrdenSalidaMaterial.contarPorCriterio(filters));
/* 161:    */         
/* 162:186 */         return lista;
/* 163:    */       }
/* 164:    */     };
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String crear()
/* 168:    */   {
/* 169:193 */     addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 170:194 */     return "";
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String editar()
/* 174:    */   {
/* 175:204 */     if ((this.realizaLogisticaCliente) && (getPedidoCliente() != null) && (getPedidoCliente().getId() > 0)) {
/* 176:    */       try
/* 177:    */       {
/* 178:206 */         this.pedidoCliente = this.servicioPedidoCliente.cargarDetalle(this.pedidoCliente.getId());
/* 179:207 */         actualizarCantidadesIniciales();
/* 180:208 */         cargarContactos(this.pedidoCliente.getEmpresa());
/* 181:    */         
/* 182:210 */         setEditado(true);
/* 183:    */       }
/* 184:    */       catch (Exception e)
/* 185:    */       {
/* 186:212 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 187:213 */         LOG.error("ERROR AL EDITAR PEDIDO DE CLIENTE:", e);
/* 188:    */       }
/* 189:215 */     } else if ((!this.realizaLogisticaCliente) && (getOrdenSalidaMaterial() != null) && (getOrdenSalidaMaterial().getId() > 0)) {
/* 190:    */       try
/* 191:    */       {
/* 192:217 */         setOrdenSalidaMaterial(this.servicioOrdenSalidaMaterial.cargarDetalle(getOrdenSalidaMaterial().getId()));
/* 193:    */         
/* 194:    */ 
/* 195:220 */         setEditado(true);
/* 196:    */       }
/* 197:    */       catch (Exception e)
/* 198:    */       {
/* 199:222 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 200:223 */         LOG.error("ERROR AL EDITAR PEDIDO DE CLIENTE:", e);
/* 201:    */       }
/* 202:    */     } else {
/* 203:226 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 204:    */     }
/* 205:228 */     return "";
/* 206:    */   }
/* 207:    */   
/* 208:    */   private void actualizarCantidadesIniciales()
/* 209:    */   {
/* 210:232 */     for (DetallePedidoCliente detalle : this.pedidoCliente.getListaDetallePedidoCliente()) {
/* 211:233 */       detalle.setCantidadOriginal(detalle.getCantidad());
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String guardar()
/* 216:    */   {
/* 217:    */     try
/* 218:    */     {
/* 219:245 */       if (!this.realizaLogisticaCliente)
/* 220:    */       {
/* 221:246 */         this.servicioOrdenSalidaMaterial.guardarLogistica(this.ordenSalidaMaterial);
/* 222:247 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 223:248 */         this.ordenSalidaMaterial = new OrdenSalidaMaterial();
/* 224:249 */         setEditado(false);
/* 225:    */       }
/* 226:251 */       else if (this.pedidoCliente.getListaDetallePedidoCliente().size() > 0)
/* 227:    */       {
/* 228:252 */         PedidoCliente pedidoAuxiliar = this.servicioPedidoCliente.guardarLogistica(this.pedidoCliente, this.servicioPedidoCliente
/* 229:253 */           .cargarDetalle(this.pedidoCliente.getId()));
/* 230:254 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 231:255 */         if (pedidoAuxiliar != null) {
/* 232:256 */           addInfoMessage(getLanguageController().getMensaje("msg_info_generado_auxiliar_diferencia") + " (" + pedidoAuxiliar
/* 233:257 */             .getNumero() + ")");
/* 234:    */         }
/* 235:259 */         cargarDatos();
/* 236:    */       }
/* 237:    */       else
/* 238:    */       {
/* 239:261 */         addErrorMessage(getLanguageController().getMensaje("msg_error_detalles_vacios"));
/* 240:    */       }
/* 241:    */     }
/* 242:    */     catch (ExcepcionAS2Ventas e)
/* 243:    */     {
/* 244:265 */       e.printStackTrace();
/* 245:266 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 246:267 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/* 247:    */     }
/* 248:    */     catch (ExcepcionAS2Financiero e)
/* 249:    */     {
/* 250:269 */       e.printStackTrace();
/* 251:270 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 252:271 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/* 253:    */     }
/* 254:    */     catch (ExcepcionAS2 e)
/* 255:    */     {
/* 256:273 */       e.printStackTrace();
/* 257:274 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 258:275 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/* 259:    */     }
/* 260:    */     catch (AS2Exception e)
/* 261:    */     {
/* 262:277 */       e.printStackTrace();
/* 263:278 */       JsfUtil.addErrorMessage(e, "");
/* 264:279 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/* 265:    */     }
/* 266:    */     catch (Exception e)
/* 267:    */     {
/* 268:281 */       e.printStackTrace();
/* 269:282 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 270:283 */       LOG.error("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/* 271:    */     }
/* 272:286 */     return "";
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String limpiar()
/* 276:    */   {
/* 277:296 */     setEditado(false);
/* 278:297 */     setIndicadorRender(false);
/* 279:298 */     setMostrarAutorizarVenta(false);
/* 280:    */     
/* 281:300 */     this.listaChoferCombo = null;
/* 282:301 */     this.listaVehiculoCombo = null;
/* 283:302 */     limpiarRuta();
/* 284:303 */     return "";
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void crearChofer()
/* 288:    */   {
/* 289:307 */     this.chofer = new Chofer();
/* 290:308 */     this.chofer.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 291:309 */     this.chofer.setIdSucursal(AppUtil.getSucursal().getId());
/* 292:310 */     this.chofer.setActivo(true);
/* 293:311 */     Transportista transportista = this.realizaLogisticaCliente ? this.pedidoCliente.getTransportista() : this.ordenSalidaMaterial.getTransportista();
/* 294:312 */     this.chofer.setTransportista(transportista);
/* 295:313 */     this.chofer.setRendered(true);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void guardarChofer()
/* 299:    */   {
/* 300:    */     try
/* 301:    */     {
/* 302:318 */       this.servicioChofer.guardar(getChofer());
/* 303:319 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 304:321 */       if (this.realizaLogisticaCliente)
/* 305:    */       {
/* 306:322 */         getPedidoCliente().setChofer(getChofer());
/* 307:323 */         getPedidoCliente().setTransportista(getChofer().getTransportista());
/* 308:    */       }
/* 309:    */       else
/* 310:    */       {
/* 311:325 */         getOrdenSalidaMaterial().setChofer(getChofer());
/* 312:326 */         getOrdenSalidaMaterial().setTransportista(getChofer().getTransportista());
/* 313:    */       }
/* 314:328 */       actualizarTransportista(false);
/* 315:    */     }
/* 316:    */     catch (ExcepcionAS2Identification e)
/* 317:    */     {
/* 318:330 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 319:331 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 320:    */     }
/* 321:    */     catch (AS2Exception e)
/* 322:    */     {
/* 323:333 */       JsfUtil.addErrorMessage(e, "");
/* 324:    */     }
/* 325:    */     catch (Exception e)
/* 326:    */     {
/* 327:335 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 328:336 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void actualizarTransportista(boolean setearVehiculo)
/* 333:    */   {
/* 334:341 */     this.listaChoferCombo = null;
/* 335:342 */     this.listaVehiculoCombo = null;
/* 336:343 */     this.listaTransportistaCombo = null;
/* 337:344 */     if (setearVehiculo) {
/* 338:345 */       if (this.realizaLogisticaCliente)
/* 339:    */       {
/* 340:346 */         this.pedidoCliente.setChofer(null);
/* 341:347 */         this.pedidoCliente.setVehiculo(null);
/* 342:    */       }
/* 343:    */       else
/* 344:    */       {
/* 345:349 */         this.ordenSalidaMaterial.setChofer(null);
/* 346:350 */         this.ordenSalidaMaterial.setVehiculo(null);
/* 347:    */       }
/* 348:    */     }
/* 349:353 */     limpiarRuta();
/* 350:    */   }
/* 351:    */   
/* 352:    */   public List<Chofer> getListaChoferCombo()
/* 353:    */   {
/* 354:357 */     Transportista transportista = this.realizaLogisticaCliente ? this.pedidoCliente.getTransportista() : this.ordenSalidaMaterial.getTransportista();
/* 355:358 */     if ((this.listaChoferCombo == null) && (transportista != null))
/* 356:    */     {
/* 357:359 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 358:360 */       filtros.put("transportista.idTransportista", transportista.getId() + "");
/* 359:361 */       this.listaChoferCombo = this.servicioChofer.obtenerListaCombo("nombre", true, filtros);
/* 360:    */     }
/* 361:363 */     return this.listaChoferCombo;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public List<Vehiculo> getListaVehiculoCombo()
/* 365:    */   {
/* 366:367 */     Transportista transportista = this.realizaLogisticaCliente ? this.pedidoCliente.getTransportista() : this.ordenSalidaMaterial.getTransportista();
/* 367:368 */     if ((this.listaVehiculoCombo == null) && (transportista != null))
/* 368:    */     {
/* 369:369 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 370:370 */       filtros.put("transportista.idTransportista", transportista.getId() + "");
/* 371:371 */       this.listaVehiculoCombo = this.servicioVehiculo.obtenerListaCombo("placa", true, filtros);
/* 372:    */     }
/* 373:373 */     return this.listaVehiculoCombo;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public Chofer getChofer()
/* 377:    */   {
/* 378:377 */     if (this.chofer == null) {
/* 379:378 */       this.chofer = new Chofer();
/* 380:    */     }
/* 381:380 */     return this.chofer;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setChofer(Chofer chofer)
/* 385:    */   {
/* 386:384 */     this.chofer = chofer;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void limpiarRuta()
/* 390:    */   {
/* 391:388 */     this.listaRuta = null;
/* 392:389 */     if ((this.realizaLogisticaCliente) && (this.pedidoCliente != null)) {
/* 393:390 */       this.pedidoCliente.setRuta(null);
/* 394:391 */     } else if ((!this.realizaLogisticaCliente) && (this.ordenSalidaMaterial != null)) {
/* 395:392 */       this.ordenSalidaMaterial.setRuta(null);
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   public List<Ruta> getListaRuta()
/* 400:    */   {
/* 401:397 */     Vehiculo vehiculo = this.realizaLogisticaCliente ? this.pedidoCliente.getVehiculo() : this.ordenSalidaMaterial.getVehiculo();
/* 402:398 */     if ((this.listaRuta == null) && (vehiculo != null))
/* 403:    */     {
/* 404:399 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 405:400 */       filters.put("tipoVehiculo.idTipoVehiculo", vehiculo.getTipoVehiculo().getId() + "");
/* 406:401 */       List<String> listaCampos = new ArrayList();
/* 407:402 */       listaCampos.add("ciudadOrigen");
/* 408:403 */       listaCampos.add("ciudadDestino");
/* 409:404 */       this.listaRuta = this.servicioRuta.obtenerListaPorPagina(Ruta.class, 0, 10000, "ciudadOrigen", true, filters, listaCampos);
/* 410:406 */       if ((this.sucursal.getCiudad() != null) && (this.pedidoCliente != null) && (this.pedidoCliente.getEmpresa() != null) && 
/* 411:407 */         (this.pedidoCliente.getDireccionEmpresa() != null)) {
/* 412:408 */         for (Ruta ruta : this.listaRuta) {
/* 413:409 */           if ((ruta.getCiudadOrigen().getId() == this.sucursal.getCiudad().getId()) && 
/* 414:410 */             (ruta.getCiudadDestino().getId() == this.pedidoCliente.getDireccionEmpresa().getCiudad().getId()))
/* 415:    */           {
/* 416:411 */             this.pedidoCliente.setRuta(ruta);
/* 417:412 */             break;
/* 418:    */           }
/* 419:    */         }
/* 420:    */       }
/* 421:    */     }
/* 422:417 */     return this.listaRuta;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public boolean isRealizaLogisticaCliente()
/* 426:    */   {
/* 427:421 */     return this.realizaLogisticaCliente;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setRealizaLogisticaCliente(boolean realizaLogisticaCliente)
/* 431:    */   {
/* 432:425 */     this.realizaLogisticaCliente = realizaLogisticaCliente;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 436:    */   {
/* 437:429 */     return this.ordenSalidaMaterial;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 441:    */   {
/* 442:433 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public List<DetalleOrdenSalidaMaterial> getListaDetalleOrden()
/* 446:    */   {
/* 447:438 */     if (this.listaDetalleOrden == null)
/* 448:    */     {
/* 449:439 */       this.listaDetalleOrden = new ArrayList();
/* 450:441 */       for (DetalleOrdenSalidaMaterial detalleOrden : this.ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/* 451:442 */         if (!detalleOrden.isEliminado()) {
/* 452:445 */           if (!detalleOrden.isIndicadorConsumoDirecto()) {
/* 453:448 */             this.listaDetalleOrden.add(detalleOrden);
/* 454:    */           }
/* 455:    */         }
/* 456:    */       }
/* 457:    */     }
/* 458:453 */     return this.listaDetalleOrden;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public SelectItem[] getListaTipoCicloProduccionItem()
/* 462:    */   {
/* 463:457 */     if (this.listaTipoCicloProduccionItem == null)
/* 464:    */     {
/* 465:459 */       List<SelectItem> lista = new ArrayList();
/* 466:460 */       lista.add(new SelectItem("", ""));
/* 467:462 */       for (TipoCicloProduccionEnum t : TipoCicloProduccionEnum.values())
/* 468:    */       {
/* 469:463 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 470:464 */         lista.add(item);
/* 471:    */       }
/* 472:466 */       this.listaTipoCicloProduccionItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 473:    */     }
/* 474:469 */     Arrays.sort(this.listaTipoCicloProduccionItem, new SelectItemComparator());
/* 475:    */     
/* 476:471 */     return this.listaTipoCicloProduccionItem;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public LazyDataModel<OrdenSalidaMaterial> getListaOrdenSalidaMaterial()
/* 480:    */   {
/* 481:475 */     return this.listaOrdenSalidaMaterial;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void actualizarListas()
/* 485:    */   {
/* 486:479 */     if (PROCESOS.PEDIDO_CLIENTE.equals(getProceso())) {
/* 487:480 */       setRealizaLogisticaCliente(true);
/* 488:    */     } else {
/* 489:482 */       setRealizaLogisticaCliente(false);
/* 490:    */     }
/* 491:    */   }
/* 492:    */   
/* 493:    */   public void setListaOrdenSalidaMaterial(LazyDataModel<OrdenSalidaMaterial> listaOrdenSalidaMaterial)
/* 494:    */   {
/* 495:487 */     this.listaOrdenSalidaMaterial = listaOrdenSalidaMaterial;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public PROCESOS getProceso()
/* 499:    */   {
/* 500:491 */     return this.proceso;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void setProceso(PROCESOS proceso)
/* 504:    */   {
/* 505:495 */     this.proceso = proceso;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public List<SelectItem> getListaProcesos()
/* 509:    */   {
/* 510:499 */     List<SelectItem> lista = new ArrayList();
/* 511:500 */     for (PROCESOS accion : PROCESOS.values()) {
/* 512:501 */       lista.add(new SelectItem(accion, accion.getNombre()));
/* 513:    */     }
/* 514:503 */     return lista;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public Transportista getTransportista()
/* 518:    */   {
/* 519:507 */     if (this.transportista == null) {
/* 520:508 */       this.transportista = new Transportista();
/* 521:    */     }
/* 522:510 */     return this.transportista;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void setTransportista(Transportista transportista)
/* 526:    */   {
/* 527:514 */     this.transportista = transportista;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void crearTransportista()
/* 531:    */   {
/* 532:518 */     this.transportista = new Transportista();
/* 533:519 */     this.transportista.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 534:520 */     this.transportista.setIdSucursal(AppUtil.getSucursal().getId());
/* 535:521 */     this.transportista.setActivo(true);
/* 536:522 */     this.transportista.setDescripcion("");
/* 537:523 */     this.transportista.setDireccion("");
/* 538:524 */     this.transportista.setRendered(true);
/* 539:    */   }
/* 540:    */   
/* 541:    */   public String guardarTrasportista()
/* 542:    */   {
/* 543:    */     try
/* 544:    */     {
/* 545:529 */       ValidarIdentificacion.validarIdentificacion(getTransportista().getTipoIdentificacion().isIndicadorValidarIdentificacion(), 
/* 546:530 */         getTransportista().getIdentificacion());
/* 547:531 */       this.servicioTransportista.guardar(getTransportista());
/* 548:532 */       if (this.realizaLogisticaCliente) {
/* 549:533 */         getPedidoCliente().setTransportista(getTransportista());
/* 550:    */       } else {
/* 551:535 */         getOrdenSalidaMaterial().setTransportista(getTransportista());
/* 552:    */       }
/* 553:537 */       actualizarTransportista(false);
/* 554:538 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 555:    */     }
/* 556:    */     catch (ExcepcionAS2Identification e)
/* 557:    */     {
/* 558:540 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 559:541 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 560:    */     }
/* 561:    */     catch (Exception e)
/* 562:    */     {
/* 563:543 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 564:544 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 565:    */     }
/* 566:546 */     return "";
/* 567:    */   }
/* 568:    */   
/* 569:    */   public Vehiculo getVehiculo()
/* 570:    */   {
/* 571:550 */     if (this.vehiculo == null) {
/* 572:551 */       this.vehiculo = new Vehiculo();
/* 573:    */     }
/* 574:552 */     return this.vehiculo;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setVehiculo(Vehiculo vehiculo)
/* 578:    */   {
/* 579:556 */     this.vehiculo = vehiculo;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void crearVehiculo()
/* 583:    */   {
/* 584:560 */     this.vehiculo = new Vehiculo();
/* 585:561 */     this.vehiculo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 586:562 */     this.vehiculo.setIdSucursal(AppUtil.getSucursal().getId());
/* 587:563 */     this.vehiculo.setActivo(true);
/* 588:564 */     Transportista transportista = this.realizaLogisticaCliente ? this.pedidoCliente.getTransportista() : this.ordenSalidaMaterial.getTransportista();
/* 589:565 */     this.vehiculo.setTransportista(transportista);
/* 590:566 */     this.vehiculo.setDescripcion("");
/* 591:567 */     this.vehiculo.setRendered(true);
/* 592:    */   }
/* 593:    */   
/* 594:    */   public void guardarVehiculo()
/* 595:    */   {
/* 596:    */     try
/* 597:    */     {
/* 598:572 */       this.servicioVehiculo.guardar(getVehiculo());
/* 599:573 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 600:575 */       if (this.realizaLogisticaCliente)
/* 601:    */       {
/* 602:576 */         getPedidoCliente().setVehiculo(getVehiculo());
/* 603:577 */         getPedidoCliente().setTransportista(getVehiculo().getTransportista());
/* 604:    */       }
/* 605:    */       else
/* 606:    */       {
/* 607:579 */         getOrdenSalidaMaterial().setTransportista(getVehiculo().getTransportista());
/* 608:580 */         getOrdenSalidaMaterial().setVehiculo(getVehiculo());
/* 609:    */       }
/* 610:582 */       actualizarTransportista(false);
/* 611:583 */       limpiarRuta();
/* 612:    */     }
/* 613:    */     catch (Exception e)
/* 614:    */     {
/* 615:585 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 616:586 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 617:    */     }
/* 618:    */   }
/* 619:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.LogisticaClienteBean
 * JD-Core Version:    0.7.0.1
 */