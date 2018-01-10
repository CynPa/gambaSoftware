/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  12:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  13:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  14:    */ import com.asinfo.as2.entities.LogSoporte;
/*  15:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*  16:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  17:    */ import com.asinfo.as2.entities.Organizacion;
/*  18:    */ import com.asinfo.as2.entities.Periodo;
/*  19:    */ import com.asinfo.as2.entities.Producto;
/*  20:    */ import com.asinfo.as2.entities.SaldoProducto;
/*  21:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  27:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  30:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  31:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  32:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  33:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  34:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  35:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  36:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  37:    */ import com.asinfo.as2.util.AppUtil;
/*  38:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  39:    */ import com.asinfo.as2.ventas.procesos.FacturaClienteBaseBean;
/*  40:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioCuentaPorCobrar;
/*  41:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  42:    */ import java.io.PrintStream;
/*  43:    */ import java.util.ArrayList;
/*  44:    */ import java.util.Date;
/*  45:    */ import java.util.HashMap;
/*  46:    */ import java.util.List;
/*  47:    */ import java.util.Map;
/*  48:    */ import javax.ejb.EJB;
/*  49:    */ import javax.faces.bean.ManagedBean;
/*  50:    */ import javax.faces.bean.ViewScoped;
/*  51:    */ import javax.faces.model.SelectItem;
/*  52:    */ import org.apache.log4j.Logger;
/*  53:    */ import org.primefaces.event.SelectEvent;
/*  54:    */ import org.primefaces.model.LazyDataModel;
/*  55:    */ import org.primefaces.model.SortOrder;
/*  56:    */ 
/*  57:    */ @ManagedBean
/*  58:    */ @ViewScoped
/*  59:    */ public class MantenimientoBean
/*  60:    */   extends FacturaClienteBaseBean
/*  61:    */ {
/*  62:    */   private static final long serialVersionUID = 1L;
/*  63:    */   @EJB
/*  64:    */   private ServicioProducto servicioProducto;
/*  65:    */   @EJB
/*  66:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  67:    */   @EJB
/*  68:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  69:    */   @EJB
/*  70:    */   private ServicioInventarioProducto servicioInventarioProducto;
/*  71:    */   @EJB
/*  72:    */   private ServicioGenerico<SaldoProducto> servicioSaldoProducto;
/*  73:    */   @EJB
/*  74:    */   private ServicioGenerico<LogSoporte> servicioLogSoporte;
/*  75:    */   @EJB
/*  76:    */   protected ServicioMovimientoInventario servicioMovimientoInventario;
/*  77:    */   @EJB
/*  78:    */   private ServicioGenerico<MovimientoInventario> servicioGenericoMovimientoInventario;
/*  79:    */   @EJB
/*  80:    */   protected transient ServicioGenerico<FacturaProveedor> servicioFacturaProveedorGenerico;
/*  81:    */   @EJB
/*  82:    */   protected transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  83:    */   @EJB
/*  84:    */   protected transient ServicioCuentaPorCobrar servicioCuentaPorCobrar;
/*  85:    */   @EJB
/*  86:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  87:    */   @EJB
/*  88:    */   protected transient ServicioEmpleado servicioEmpleado;
/*  89:    */   @EJB
/*  90:    */   protected transient ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  91:    */   private LazyDataModel<LogSoporte> listaLogSoporte;
/*  92:    */   private OPCIONES accion;
/*  93:    */   private String render;
/*  94:    */   private Producto producto;
/*  95:    */   private SubcategoriaProducto subCategoriaProducto;
/*  96:    */   private CategoriaProducto categoriaProducto;
/*  97:    */   
/*  98:    */   static enum OPCIONES
/*  99:    */   {
/* 100: 65 */     ACTUALIZAR_CUENTA_POR_COBRAR("Actualizar cuenta por cobrar"),  ACTUALIZAR_CUENTA_POR_PAGAR("Actualizar cuenta por pagar"),  ELIMINAR_FACTURA_ANULADA("Eliminar factura anulada"),  ELIMINAR_SALIDA_EMPLEADO("Eliminar salida empleado"),  REVERSO_LIQUIDACION("Reverso liquidacion"),  ELIMINAR_INVENTARIO("Eliminar inventario"),  ACTUALIZAR_SALDO_POR_PRODUCTO("Actualizar saldo por producto"),  LOG_SOPORTE("Log de soporte");
/* 101:    */     
/* 102:    */     private String nombre;
/* 103:    */     
/* 104:    */     private OPCIONES(String nombre)
/* 105:    */     {
/* 106: 77 */       this.nombre = nombre;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public String getNombre()
/* 110:    */     {
/* 111: 86 */       return this.nombre;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public void setNombre(String nombre)
/* 115:    */     {
/* 116: 95 */       this.nombre = nombre;
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:140 */   private LogSoporte logSoporte = new LogSoporte();
/* 121:    */   private FacturaCliente facturaCliente;
/* 122:    */   private MovimientoInventario ajusteInventario;
/* 123:    */   private Integer ajusteInventarioId;
/* 124:    */   private FacturaProveedor facturaProveedor2;
/* 125:    */   private Empresa empresa;
/* 126:    */   private Date fecha;
/* 127:    */   @EJB
/* 128:    */   private ServicioPeriodo servicioPeriodo;
/* 129:    */   private Empresa empleado;
/* 130:    */   private HistoricoEmpleado historicoEmpleado;
/* 131:    */   
/* 132:    */   public String actualizar()
/* 133:    */   {
/* 134:166 */     if (this.producto == null)
/* 135:    */     {
/* 136:167 */       for (Producto p : this.servicioInventarioProducto.obtenerProductos(AppUtil.getOrganizacion().getId(), this.categoriaProducto, this.subCategoriaProducto))
/* 137:    */       {
/* 138:168 */         if (p.isIndicadorLote()) {
/* 139:169 */           this.servicioInventarioProducto.actualizarSaldoPorProducto(AppUtil.getOrganizacion().getId(), p, p.isIndicadorLote(), this.fecha);
/* 140:    */         }
/* 141:171 */         this.servicioInventarioProducto.actualizarSaldoPorProducto(AppUtil.getOrganizacion().getId(), p, false, this.fecha);
/* 142:    */       }
/* 143:    */     }
/* 144:    */     else
/* 145:    */     {
/* 146:174 */       if (this.producto.isIndicadorLote()) {
/* 147:175 */         this.servicioInventarioProducto.actualizarSaldoPorProducto(AppUtil.getOrganizacion().getId(), this.producto, this.producto.isIndicadorLote(), this.fecha);
/* 148:    */       }
/* 149:177 */       this.servicioInventarioProducto.actualizarSaldoPorProducto(AppUtil.getOrganizacion().getId(), this.producto, false, this.fecha);
/* 150:    */     }
/* 151:179 */     addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 152:180 */     return null;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 156:    */   {
/* 157:184 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 158:    */     
/* 159:186 */     HashMap<String, String> filters = new HashMap();
/* 160:187 */     filters.put("nombre", "%" + consulta.trim());
/* 161:    */     
/* 162:189 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 163:    */     
/* 164:191 */     return lista;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Producto> autocompletarProducto(String consulta)
/* 168:    */   {
/* 169:195 */     List<Producto> lista = new ArrayList();
/* 170:    */     
/* 171:197 */     HashMap<String, String> filters = new HashMap();
/* 172:198 */     filters.put("nombre", "%" + consulta.trim());
/* 173:    */     
/* 174:200 */     lista = this.servicioProducto.obtenerListaCombo("nombre", true, filters);
/* 175:    */     
/* 176:202 */     return lista;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 180:    */   {
/* 181:206 */     List<CategoriaProducto> lista = new ArrayList();
/* 182:    */     
/* 183:208 */     HashMap<String, String> filters = new HashMap();
/* 184:209 */     filters.put("nombre", "%" + consulta.trim());
/* 185:    */     
/* 186:211 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 187:    */     
/* 188:213 */     return lista;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Producto getProducto()
/* 192:    */   {
/* 193:217 */     return this.producto;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setProducto(Producto producto)
/* 197:    */   {
/* 198:221 */     this.producto = producto;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public SubcategoriaProducto getSubCategoriaProducto()
/* 202:    */   {
/* 203:225 */     return this.subCategoriaProducto;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setSubCategoriaProducto(SubcategoriaProducto subCategoriaProducto)
/* 207:    */   {
/* 208:229 */     this.subCategoriaProducto = subCategoriaProducto;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public CategoriaProducto getCategoriaProducto()
/* 212:    */   {
/* 213:233 */     return this.categoriaProducto;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 217:    */   {
/* 218:237 */     this.categoriaProducto = categoriaProducto;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<SelectItem> getListaAccion()
/* 222:    */   {
/* 223:241 */     List<SelectItem> lista = new ArrayList();
/* 224:242 */     for (OPCIONES accion : OPCIONES.values()) {
/* 225:243 */       lista.add(new SelectItem(accion, accion.getNombre()));
/* 226:    */     }
/* 227:245 */     return lista;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public OPCIONES getAccion()
/* 231:    */   {
/* 232:249 */     return this.accion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setAccion(OPCIONES accion)
/* 236:    */   {
/* 237:253 */     this.accion = accion;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String getRender()
/* 241:    */   {
/* 242:257 */     if (this.accion != null) {
/* 243:258 */       this.render = this.accion.nombre;
/* 244:    */     }
/* 245:259 */     return this.render;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setRender(String reder)
/* 249:    */   {
/* 250:263 */     this.render = this.accion.nombre;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public LazyDataModel<LogSoporte> getListaLogSoporte()
/* 254:    */   {
/* 255:267 */     this.listaLogSoporte = new LazyDataModel()
/* 256:    */     {
/* 257:    */       private static final long serialVersionUID = 1L;
/* 258:    */       
/* 259:    */       public List<LogSoporte> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 260:    */       {
/* 261:274 */         List<LogSoporte> lista = new ArrayList();
/* 262:    */         
/* 263:276 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 264:278 */         if (filters == null) {
/* 265:279 */           filters = new HashMap();
/* 266:    */         }
/* 267:282 */         lista = MantenimientoBean.this.servicioLogSoporte.obtenerListaPorPagina(LogSoporte.class, startIndex, pageSize, sortField, ordenar, filters);
/* 268:283 */         System.out.println(lista.size());
/* 269:284 */         MantenimientoBean.this.listaLogSoporte.setRowCount(MantenimientoBean.this.servicioLogSoporte.contarPorCriterio(LogSoporte.class, filters));
/* 270:    */         
/* 271:286 */         return lista;
/* 272:    */       }
/* 273:288 */     };
/* 274:289 */     return this.listaLogSoporte;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setListaLogSoporte(LazyDataModel<LogSoporte> listaLogSoporte)
/* 278:    */   {
/* 279:293 */     this.listaLogSoporte = listaLogSoporte;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public FacturaCliente getFacturaCliente()
/* 283:    */   {
/* 284:298 */     return this.facturaCliente;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 288:    */   {
/* 289:302 */     this.facturaCliente = facturaCliente;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public List<FacturaCliente> autocompletarFacturaCliente(String consulta)
/* 293:    */   {
/* 294:306 */     List<FacturaCliente> lista = new ArrayList();
/* 295:    */     
/* 296:308 */     HashMap<String, String> filters = new HashMap();
/* 297:309 */     filters.put("numero", "%" + consulta.trim());
/* 298:310 */     filters.put("estado", "" + Estado.ANULADO);
/* 299:311 */     lista = this.servicioFacturaCliente.obtenerListaCombo("numero", true, filters);
/* 300:    */     
/* 301:313 */     return lista;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String cargar()
/* 305:    */   {
/* 306:    */     try
/* 307:    */     {
/* 308:318 */       this.facturaCliente = this.servicioFacturaCliente.cargarDetalle(this.facturaCliente.getId());
/* 309:    */     }
/* 310:    */     catch (ExcepcionAS2 e)
/* 311:    */     {
/* 312:320 */       e.printStackTrace();
/* 313:    */     }
/* 314:323 */     System.out.println(this.facturaCliente);
/* 315:324 */     this.logSoporte.setDetalle("Eliminar Factura Anulada------>Factura No.: " + this.facturaCliente.getNumero() + "--->Cliente: " + this.facturaCliente
/* 316:325 */       .getEmpresa().getNombreFiscal() + "--->Total: " + this.facturaCliente.getTotalFactura());
/* 317:326 */     this.logSoporte.setFechaAuditoria(new Date());
/* 318:327 */     this.logSoporte.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 319:328 */     this.logSoporte.setResponsable(AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 320:329 */     return null;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public String eliminarFactura()
/* 324:    */   {
/* 325:334 */     if (this.facturaCliente.getId() > 0) {
/* 326:    */       try
/* 327:    */       {
/* 328:336 */         this.facturaCliente = this.servicioFacturaCliente.cargarDetalle(this.facturaCliente.getId());
/* 329:    */         
/* 330:338 */         this.servicioFacturaCliente.eliminar(this.facturaCliente);
/* 331:339 */         this.servicioLogSoporte.guardar(this.logSoporte);
/* 332:340 */         this.logSoporte = new LogSoporte();
/* 333:341 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 334:    */       }
/* 335:    */       catch (ExcepcionAS2Ventas e)
/* 336:    */       {
/* 337:343 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 338:344 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE ExcepcionAS2Ventas", e);
/* 339:    */       }
/* 340:    */       catch (ExcepcionAS2Compras e)
/* 341:    */       {
/* 342:346 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 343:347 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE Exception", e);
/* 344:    */       }
/* 345:    */       catch (ExcepcionAS2Financiero e)
/* 346:    */       {
/* 347:349 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 348:350 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE ExcepcionAS2Financiero", e);
/* 349:    */       }
/* 350:    */       catch (AS2Exception e)
/* 351:    */       {
/* 352:352 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 353:353 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE ExcepcionAS2Financiero", e);
/* 354:    */       }
/* 355:    */       catch (Exception e)
/* 356:    */       {
/* 357:355 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 358:356 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE Exception", e);
/* 359:    */       }
/* 360:    */     } else {
/* 361:359 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 362:    */     }
/* 363:361 */     return "";
/* 364:    */   }
/* 365:    */   
/* 366:    */   public LogSoporte getLogSoporte()
/* 367:    */   {
/* 368:365 */     return this.logSoporte;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setLogSoporte(LogSoporte logSoporte)
/* 372:    */   {
/* 373:369 */     this.logSoporte = logSoporte;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void onItemSelect(SelectEvent event)
/* 377:    */   {
/* 378:374 */     this.ajusteInventarioId = ((Integer)event.getObject());
/* 379:375 */     this.ajusteInventario = this.servicioMovimientoInventario.buscarPorId(this.ajusteInventarioId);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public String buscarInventario()
/* 383:    */   {
/* 384:379 */     this.ajusteInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.ajusteInventario.getId()));
/* 385:    */     
/* 386:381 */     this.logSoporte.setFechaAuditoria(new Date());
/* 387:382 */     this.logSoporte.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 388:383 */     this.logSoporte.setResponsable(AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 389:384 */     return null;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public String eliminarInventario()
/* 393:    */   {
/* 394:    */     try
/* 395:    */     {
/* 396:391 */       String detalle = "Eliminar inventario------>No.: " + this.ajusteInventario.getNumero() + "--->Documento: " + this.ajusteInventario.getDocumento().getNombre();
/* 397:392 */       if (this.ajusteInventario.getMotivoAjusteInventario() != null) {
/* 398:393 */         detalle = detalle + "--->Motivo Ajuste Inventario: " + this.ajusteInventario.getMotivoAjusteInventario().getNombre();
/* 399:    */       }
/* 400:395 */       if (this.ajusteInventario.getAsiento() != null) {
/* 401:396 */         detalle = detalle + "--->Asiento eliminado: " + this.ajusteInventario.getAsiento().getNumero();
/* 402:    */       }
/* 403:398 */       this.logSoporte.setDetalle(detalle);
/* 404:399 */       this.servicioMovimientoInventario.eliminar(this.ajusteInventario);
/* 405:400 */       this.servicioLogSoporte.guardar(this.logSoporte);
/* 406:401 */       this.logSoporte = new LogSoporte();
/* 407:402 */       addInfoMessage(getLanguageController().getMensaje("msg_eliminar_recuerde"));
/* 408:    */     }
/* 409:    */     catch (AS2Exception e)
/* 410:    */     {
/* 411:404 */       e.printStackTrace();
/* 412:    */     }
/* 413:    */     catch (ExcepcionAS2 e)
/* 414:    */     {
/* 415:406 */       e.printStackTrace();
/* 416:    */     }
/* 417:408 */     return null;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public List<MovimientoInventario> autocompletarInventario(String consulta)
/* 421:    */   {
/* 422:412 */     List<MovimientoInventario> lista = new ArrayList();
/* 423:    */     
/* 424:414 */     HashMap<String, String> filters = new HashMap();
/* 425:415 */     filters.put("numero", "%" + consulta.trim());
/* 426:416 */     lista = this.servicioMovimientoInventario.obtenerListaCombo("numero", true, filters);
/* 427:417 */     return lista;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public MovimientoInventario getAjusteInventario()
/* 431:    */   {
/* 432:421 */     return this.ajusteInventario;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setAjusteInventario(MovimientoInventario ajusteInventario)
/* 436:    */   {
/* 437:425 */     this.ajusteInventario = ajusteInventario;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public Integer getAjusteInventarioId()
/* 441:    */   {
/* 442:429 */     return this.ajusteInventarioId;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setAjusteInventarioId(Integer ajusteInventarioId)
/* 446:    */   {
/* 447:433 */     this.ajusteInventarioId = ajusteInventarioId;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public String buscar()
/* 451:    */   {
/* 452:    */     try
/* 453:    */     {
/* 454:439 */       this.facturaProveedor2 = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(this.facturaProveedor2.getId()));
/* 455:    */     }
/* 456:    */     catch (ExcepcionAS2Compras e)
/* 457:    */     {
/* 458:441 */       e.printStackTrace();
/* 459:    */     }
/* 460:443 */     this.logSoporte.setDetalle("Reverso liquidacion----->Factura No.: " + this.facturaProveedor2.getNumero() + "--->Cliente: " + this.facturaProveedor2
/* 461:444 */       .getEmpresa().getNombreFiscal() + "--->Total: " + this.facturaProveedor2.getTotalFactura());
/* 462:445 */     this.logSoporte.setFechaAuditoria(new Date());
/* 463:446 */     this.logSoporte.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 464:447 */     this.logSoporte.setResponsable(AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 465:448 */     return null;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public String reverso()
/* 469:    */   {
/* 470:    */     try
/* 471:    */     {
/* 472:454 */       this.facturaProveedor2.setEstado(Estado.ELABORADO);
/* 473:455 */       this.servicioFacturaProveedorGenerico.guardar(this.facturaProveedor2);
/* 474:456 */       this.servicioLogSoporte.guardar(this.logSoporte);
/* 475:457 */       this.logSoporte = new LogSoporte();
/* 476:458 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 477:    */     }
/* 478:    */     catch (AS2Exception e)
/* 479:    */     {
/* 480:460 */       e.printStackTrace();
/* 481:    */     }
/* 482:462 */     return null;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public List<FacturaProveedor> autocompletarReverso(String consulta)
/* 486:    */   {
/* 487:466 */     List<FacturaProveedor> lista = new ArrayList();
/* 488:    */     
/* 489:468 */     HashMap<String, String> filters = new HashMap();
/* 490:469 */     filters.put("numero", "%" + consulta.trim());
/* 491:470 */     filters.put("estado", "" + Estado.CERRADO);
/* 492:471 */     lista = this.servicioFacturaProveedor.obtenerListaCombo("numero", true, filters);
/* 493:    */     
/* 494:473 */     return lista;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public FacturaProveedor getFacturaProveedor2()
/* 498:    */   {
/* 499:477 */     return this.facturaProveedor2;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setFacturaProveedor2(FacturaProveedor facturaProveedor2)
/* 503:    */   {
/* 504:481 */     this.facturaProveedor2 = facturaProveedor2;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public String actualizarCuentasPorCobrar()
/* 508:    */   {
/* 509:486 */     this.servicioCuentaPorCobrar.actualizarCuentasPorCobrar(this.empresa);
/* 510:487 */     return null;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public List<Empresa> autocompletarEmpresas(String consulta)
/* 514:    */   {
/* 515:491 */     List<Empresa> lista = new ArrayList();
/* 516:    */     
/* 517:493 */     HashMap<String, String> filters = new HashMap();
/* 518:494 */     filters.put("nombreComercial", "%" + consulta.trim());
/* 519:495 */     lista = this.servicioEmpresa.obtenerClientes("nombreComercial", true, filters);
/* 520:    */     
/* 521:497 */     return lista;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public Empresa getEmpresa()
/* 525:    */   {
/* 526:501 */     return this.empresa;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setEmpresa(Empresa empresa)
/* 530:    */   {
/* 531:505 */     this.empresa = empresa;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public String actualizarCuentasPorPagar()
/* 535:    */   {
/* 536:510 */     this.servicioCuentaPorCobrar.actualizarCuentasPorPagar(this.empresa);
/* 537:511 */     return null;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 541:    */   {
/* 542:515 */     List<Empresa> lista = new ArrayList();
/* 543:    */     
/* 544:517 */     HashMap<String, String> filters = new HashMap();
/* 545:518 */     filters.put("nombreComercial", "%" + consulta.trim());
/* 546:519 */     lista = this.servicioEmpresa.obtenerProveedores("nombreComercial", true, filters);
/* 547:    */     
/* 548:521 */     return lista;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public Date getFecha()
/* 552:    */   {
/* 553:525 */     if (this.fecha == null) {
/* 554:    */       try
/* 555:    */       {
/* 556:527 */         Periodo periodo = this.servicioPeriodo.obtenerPeriodoActual(AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.AJUSTE_INVENTARIO);
/* 557:528 */         this.fecha = periodo.getFechaDesde();
/* 558:    */       }
/* 559:    */       catch (ExcepcionAS2Financiero e)
/* 560:    */       {
/* 561:531 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 562:    */       }
/* 563:    */     }
/* 564:535 */     return this.fecha;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setFecha(Date fecha)
/* 568:    */   {
/* 569:539 */     this.fecha = fecha;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public Empresa getEmpleado()
/* 573:    */   {
/* 574:543 */     return this.empleado;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setEmpleado(Empresa empleado)
/* 578:    */   {
/* 579:547 */     this.empleado = empleado;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 583:    */   {
/* 584:551 */     return this.historicoEmpleado;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 588:    */   {
/* 589:555 */     this.historicoEmpleado = historicoEmpleado;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public List<Empresa> autocompletarEmpleados(String consulta)
/* 593:    */   {
/* 594:559 */     HashMap<String, String> filters = new HashMap();
/* 595:560 */     filters.put("indicadorEmpleado", "true");
/* 596:561 */     filters.put("activo", "false");
/* 597:562 */     filters.put("nombreFiscal", consulta.trim());
/* 598:563 */     filters.put("nombreComercial", consulta.trim());
/* 599:564 */     filters.put("identificacion", consulta.trim());
/* 600:565 */     return this.servicioEmpresa.obtenerListaCombo("nombreFiscal", true, filters);
/* 601:    */   }
/* 602:    */   
/* 603:    */   public List<HistoricoEmpleado> autocompletarHistoricoEmpleado(String consulta)
/* 604:    */   {
/* 605:569 */     return this.servicioHistoricoEmpleado.autocompletarHistoricoEmpleado(consulta, null);
/* 606:    */   }
/* 607:    */   
/* 608:    */   public String eliminarSalidaEmpleado()
/* 609:    */   {
/* 610:573 */     this.servicioHistoricoEmpleado.eliminarSalidaEmpleado(this.historicoEmpleado);
/* 611:574 */     addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 612:575 */     return null;
/* 613:    */   }
/* 614:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.MantenimientoBean
 * JD-Core Version:    0.7.0.1
 */