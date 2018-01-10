/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaCreditoProveedor;
/*   6:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   7:    */ import com.asinfo.as2.controller.LanguageController;
/*   8:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  11:    */ import com.asinfo.as2.entities.Bodega;
/*  12:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  13:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  17:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*  18:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  19:    */ import com.asinfo.as2.entities.Lote;
/*  20:    */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*  21:    */ import com.asinfo.as2.entities.Organizacion;
/*  22:    */ import com.asinfo.as2.entities.Producto;
/*  23:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  24:    */ import com.asinfo.as2.entities.Secuencia;
/*  25:    */ import com.asinfo.as2.entities.Sucursal;
/*  26:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  27:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  28:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  29:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  30:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  31:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*  32:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  33:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  34:    */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioMotivoNotaCreditoProveedor;
/*  35:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  36:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  37:    */ import com.asinfo.as2.util.AppUtil;
/*  38:    */ import com.asinfo.as2.util.RutaArchivo;
/*  39:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  40:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  41:    */ import java.math.BigDecimal;
/*  42:    */ import java.util.ArrayList;
/*  43:    */ import java.util.Date;
/*  44:    */ import java.util.HashMap;
/*  45:    */ import java.util.List;
/*  46:    */ import java.util.Map;
/*  47:    */ import javax.annotation.PostConstruct;
/*  48:    */ import javax.ejb.EJB;
/*  49:    */ import javax.faces.bean.ManagedBean;
/*  50:    */ import javax.faces.bean.ViewScoped;
/*  51:    */ import org.apache.log4j.Logger;
/*  52:    */ import org.primefaces.component.datatable.DataTable;
/*  53:    */ import org.primefaces.event.FileUploadEvent;
/*  54:    */ import org.primefaces.event.SelectEvent;
/*  55:    */ import org.primefaces.model.LazyDataModel;
/*  56:    */ import org.primefaces.model.SortOrder;
/*  57:    */ 
/*  58:    */ @ManagedBean
/*  59:    */ @ViewScoped
/*  60:    */ public class DevolucionProveedorBean
/*  61:    */   extends PageControllerAS2
/*  62:    */ {
/*  63:    */   private static final long serialVersionUID = 8616894703737681721L;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioEmpresa servicioEmpresa;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  70:    */   @EJB
/*  71:    */   private transient ServicioDocumento servicioDocumento;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioCreditoTributario servicioCreditoTributario;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioMotivoNotaCreditoProveedor servicioMotivoNotaCreditoProveedor;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioNotaCreditoProveedor servicioNotaCreditoProveedor;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioLote servicioLote;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  82:    */   private FacturaProveedor devolucionProveedor;
/*  83:    */   private List<CreditoTributarioSRI> listaSustentoTributarioSRI;
/*  84:    */   private List<MotivoNotaCreditoProveedor> listaMotivoNotaCreditoProveedor;
/*  85:    */   private String numeroDevolucion;
/*  86:    */   private Lote loteCrear;
/*  87:    */   private DetalleFacturaProveedor detalleFacturaProveedorSeleccionado;
/*  88:    */   private LazyDataModel<FacturaProveedor> listaDevolucionProveedor;
/*  89:    */   private List<Documento> listaDocumentoCombo;
/*  90:119 */   private List<DireccionEmpresa> listaDireccionEmpresa = new ArrayList();
/*  91:    */   private List<Bodega> listaBodega;
/*  92:    */   private DataTable dtDevolucionProveedor;
/*  93:    */   private DataTable dtDetalleDevolucionProveedor;
/*  94:    */   private DataTable dtImpuestoDevolucion;
/*  95:    */   
/*  96:    */   @PostConstruct
/*  97:    */   public void init()
/*  98:    */   {
/*  99:135 */     this.listaDevolucionProveedor = new LazyDataModel()
/* 100:    */     {
/* 101:    */       private static final long serialVersionUID = 4780083578367601484L;
/* 102:    */       
/* 103:    */       public List<FacturaProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 104:    */       {
/* 105:142 */         if (DevolucionProveedorBean.this.numeroDevolucion != null) {
/* 106:143 */           filters.put("numero", DevolucionProveedorBean.this.numeroDevolucion);
/* 107:    */         }
/* 108:146 */         List<FacturaProveedor> lista = new ArrayList();
/* 109:    */         
/* 110:148 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 111:    */         
/* 112:150 */         filters.put("documento.documentoBase", DocumentoBase.DEVOLUCION_PROVEEDOR.toString());
/* 113:151 */         lista = DevolucionProveedorBean.this.servicioFacturaProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 114:    */         
/* 115:153 */         DevolucionProveedorBean.this.listaDevolucionProveedor.setRowCount(DevolucionProveedorBean.this.servicioFacturaProveedor.contarPorCriterio(filters));
/* 116:154 */         return lista;
/* 117:    */       }
/* 118:    */     };
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String editar()
/* 122:    */   {
/* 123:172 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 124:173 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String guardar()
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:187 */       this.servicioNotaCreditoProveedor.guardar(this.devolucionProveedor);
/* 132:    */       
/* 133:189 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 134:190 */       setEditado(false);
/* 135:191 */       limpiar();
/* 136:    */     }
/* 137:    */     catch (ExcepcionAS2Financiero e)
/* 138:    */     {
/* 139:194 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 140:195 */       LOG.error("ERROR ExcepcionAS2Financiero AL GUARDAR DATOS", e);
/* 141:    */     }
/* 142:    */     catch (ExcepcionAS2 e)
/* 143:    */     {
/* 144:197 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 145:198 */       LOG.error("ERROR ExcepcionAS2 AL GUARDAR DATOS", e);
/* 146:    */     }
/* 147:    */     catch (Exception e)
/* 148:    */     {
/* 149:200 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 150:201 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 151:    */     }
/* 152:204 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String eliminar()
/* 156:    */   {
/* 157:215 */     if (this.devolucionProveedor.getId() > 0) {
/* 158:    */       try
/* 159:    */       {
/* 160:217 */         this.servicioNotaCreditoProveedor.anular(this.devolucionProveedor);
/* 161:218 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 162:    */       }
/* 163:    */       catch (ExcepcionAS2Ventas e)
/* 164:    */       {
/* 165:221 */         e.printStackTrace();
/* 166:222 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 167:223 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE(DEVOLUCION) ExcepcionAS2Ventas");
/* 168:    */       }
/* 169:    */       catch (ExcepcionAS2Financiero e)
/* 170:    */       {
/* 171:225 */         e.printStackTrace();
/* 172:226 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 173:227 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE(DEVOLUCION)ExcepcionAS2Financiero");
/* 174:    */       }
/* 175:    */       catch (Exception e)
/* 176:    */       {
/* 177:229 */         e.printStackTrace();
/* 178:230 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 179:231 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE(DEVOLUCION) Exception");
/* 180:    */       }
/* 181:    */     } else {
/* 182:234 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 183:    */     }
/* 184:237 */     return "";
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String limpiar()
/* 188:    */   {
/* 189:247 */     crearDevolucion();
/* 190:248 */     return "";
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String cargarDatos()
/* 194:    */   {
/* 195:258 */     return "";
/* 196:    */   }
/* 197:    */   
/* 198:    */   private void crearDevolucion()
/* 199:    */   {
/* 200:265 */     this.devolucionProveedor = new FacturaProveedor();
/* 201:266 */     this.devolucionProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 202:267 */     this.devolucionProveedor.setSucursal(AppUtil.getSucursal());
/* 203:268 */     this.devolucionProveedor.setNumero("");
/* 204:269 */     this.devolucionProveedor.setFecha(new Date());
/* 205:270 */     this.devolucionProveedor.setEstado(Estado.ELABORADO);
/* 206:271 */     this.devolucionProveedor.setNumeroCuotas(1);
/* 207:272 */     this.devolucionProveedor.setMotivoNotaCreditoProveedor(new MotivoNotaCreditoProveedor());
/* 208:    */     
/* 209:    */ 
/* 210:275 */     Documento documento = null;
/* 211:276 */     if ((getListaDocumentoCombo() != null) && (!getListaDocumentoCombo().isEmpty()))
/* 212:    */     {
/* 213:277 */       documento = (Documento)getListaDocumentoCombo().get(0);
/* 214:278 */       this.devolucionProveedor.setDocumento(documento);
/* 215:279 */       actualizarDocumento();
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:281 */       documento = new Documento();
/* 220:282 */       documento.setSecuencia(new Secuencia());
/* 221:283 */       this.devolucionProveedor.setDocumento(documento);
/* 222:    */     }
/* 223:286 */     FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/* 224:287 */     facturaProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 225:288 */     facturaProveedorSRI.setIdSucursal(this.devolucionProveedor.getSucursal().getId());
/* 226:289 */     facturaProveedorSRI.setNumero("0");
/* 227:290 */     facturaProveedorSRI.setEstablecimientoRetencion("000");
/* 228:291 */     facturaProveedorSRI.setPuntoEmisionRetencion("000");
/* 229:292 */     facturaProveedorSRI.setNumeroRetencion("0");
/* 230:293 */     facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/* 231:294 */     facturaProveedorSRI.setCreditoTributarioSRI(new CreditoTributarioSRI());
/* 232:    */     
/* 233:296 */     facturaProveedorSRI.setFacturaProveedor(this.devolucionProveedor);
/* 234:297 */     this.devolucionProveedor.setFacturaProveedorSRI(facturaProveedorSRI);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String agregarDetalle()
/* 238:    */   {
/* 239:302 */     DetalleFacturaProveedor d = new DetalleFacturaProveedor();
/* 240:303 */     d.setFacturaProveedor(getDevolucionProveedor());
/* 241:304 */     d.setProducto(new Producto());
/* 242:305 */     d.setCantidadADevolver(BigDecimal.ZERO);
/* 243:306 */     d.setPrecio(BigDecimal.ZERO);
/* 244:307 */     d.setDescuento(BigDecimal.ZERO);
/* 245:308 */     getDevolucionProveedor().getListaDetalleFacturaProveedor().add(d);
/* 246:    */     
/* 247:310 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String creacionLote()
/* 251:    */   {
/* 252:314 */     this.detalleFacturaProveedorSeleccionado = ((DetalleFacturaProveedor)this.dtDetalleDevolucionProveedor.getRowData());
/* 253:315 */     this.loteCrear = new Lote();
/* 254:316 */     this.loteCrear.setActivo(true);
/* 255:317 */     this.loteCrear.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 256:318 */     this.loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 257:319 */     this.loteCrear.setProducto(this.detalleFacturaProveedorSeleccionado.getProducto());
/* 258:320 */     return "";
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String guardarLote()
/* 262:    */   {
/* 263:    */     try
/* 264:    */     {
/* 265:325 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/* 266:326 */       this.servicioLote.guardar(this.loteCrear);
/* 267:327 */       this.loteCrear.getProducto().setPrefijoLote(null);
/* 268:328 */       this.detalleFacturaProveedorSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/* 269:329 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 270:    */     }
/* 271:    */     catch (Exception e)
/* 272:    */     {
/* 273:331 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 274:332 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 275:    */     }
/* 276:334 */     return "";
/* 277:    */   }
/* 278:    */   
/* 279:    */   public String actualizarDocumento()
/* 280:    */   {
/* 281:344 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.devolucionProveedor.getDocumento().getId()));
/* 282:345 */     this.devolucionProveedor.setDocumento(documento);
/* 283:    */     try
/* 284:    */     {
/* 285:348 */       this.servicioFacturaProveedor.cargarSecuencia(this.devolucionProveedor, null);
/* 286:    */     }
/* 287:    */     catch (ExcepcionAS2 e)
/* 288:    */     {
/* 289:351 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 290:    */     }
/* 291:354 */     return "";
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<Empresa> autocompletarProveedor(String consulta)
/* 295:    */   {
/* 296:364 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<FacturaProveedor> autocompletarFacturas(String consulta)
/* 300:    */   {
/* 301:369 */     Map<String, String> filters = new HashMap();
/* 302:370 */     List<FacturaProveedor> lista = new ArrayList();
/* 303:372 */     if (getDevolucionProveedor().getEmpresa() != null)
/* 304:    */     {
/* 305:374 */       if ((consulta != null) && (!consulta.isEmpty())) {
/* 306:375 */         filters.put("numero", consulta);
/* 307:    */       }
/* 308:383 */       lista = this.servicioFacturaProveedor.autocompletarFacturaProveedorDevolucion(getDevolucionProveedor().getEmpresa().getIdEmpresa(), filters);
/* 309:    */     }
/* 310:    */     else
/* 311:    */     {
/* 312:385 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 313:    */     }
/* 314:387 */     return lista;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public List<RecepcionProveedor> autocompletarRecepciones(String consulta)
/* 318:    */   {
/* 319:391 */     List<RecepcionProveedor> lista = new ArrayList();
/* 320:392 */     if (getDevolucionProveedor().getEmpresa() != null) {
/* 321:393 */       lista = this.servicioRecepcionProveedor.autocompletarRecepcionesNoFacturadasPorProveedor(Integer.valueOf(getDevolucionProveedor().getEmpresa().getId()), consulta);
/* 322:    */     } else {
/* 323:396 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 324:    */     }
/* 325:398 */     return lista;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void totalizar()
/* 329:    */   {
/* 330:    */     try
/* 331:    */     {
/* 332:406 */       this.servicioFacturaProveedor.totalizar(this.devolucionProveedor);
/* 333:    */     }
/* 334:    */     catch (ExcepcionAS2Compras e)
/* 335:    */     {
/* 336:408 */       LOG.error(e.getErrorMessage(e));
/* 337:    */     }
/* 338:    */     catch (Exception e)
/* 339:    */     {
/* 340:410 */       LOG.error(e);
/* 341:411 */       e.printStackTrace();
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void actualizarProveedor(SelectEvent event)
/* 346:    */   {
/* 347:421 */     Empresa empresa = (Empresa)event.getObject();
/* 348:422 */     this.devolucionProveedor.setEmpresa(empresa);
/* 349:423 */     this.devolucionProveedor.setFacturaProveedorPadre(null);
/* 350:424 */     cargarDirecciones();
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void actualizarFactura(SelectEvent event)
/* 354:    */   {
/* 355:435 */     FacturaProveedor facturaProveedorPadre = (FacturaProveedor)event.getObject();
/* 356:436 */     this.devolucionProveedor.setFacturaProveedorPadre(facturaProveedorPadre);
/* 357:437 */     this.servicioNotaCreditoProveedor.actulizarDetalleDevolucion(this.devolucionProveedor.getFacturaProveedorPadre().getId(), this.devolucionProveedor, true);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void actualizarFacturaDesdeRecepcion(SelectEvent event)
/* 361:    */   {
/* 362:449 */     RecepcionProveedor recepcionProveedor = (RecepcionProveedor)event.getObject();
/* 363:450 */     this.devolucionProveedor.setRecepcionProveedor(recepcionProveedor);
/* 364:451 */     this.servicioNotaCreditoProveedor.actulizarDetalleDevolucion(this.devolucionProveedor.getRecepcionProveedor().getId(), this.devolucionProveedor, false);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void cargarDirecciones()
/* 368:    */   {
/* 369:461 */     setListaDireccionEmpresa(this.servicioEmpresa.obtenerListaComboDirecciones(this.devolucionProveedor.getEmpresa().getId()));
/* 370:462 */     if (this.listaDireccionEmpresa.size() > 0)
/* 371:    */     {
/* 372:464 */       boolean direccionPrincipal = false;
/* 373:465 */       for (DireccionEmpresa direccionEmpresa : this.listaDireccionEmpresa) {
/* 374:466 */         if (direccionEmpresa.isIndicadorDireccionPrincipal())
/* 375:    */         {
/* 376:467 */           getDevolucionProveedor().setDireccionEmpresa(direccionEmpresa);
/* 377:468 */           direccionPrincipal = true;
/* 378:469 */           break;
/* 379:    */         }
/* 380:    */       }
/* 381:472 */       if (!direccionPrincipal) {
/* 382:473 */         getDevolucionProveedor().setDireccionEmpresa((DireccionEmpresa)this.listaDireccionEmpresa.get(0));
/* 383:    */       }
/* 384:    */     }
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<DetalleFacturaProveedor> getDetalleFacturaProveedor()
/* 388:    */   {
/* 389:484 */     List<DetalleFacturaProveedor> detalle = new ArrayList();
/* 390:485 */     for (DetalleFacturaProveedor dfp : this.devolucionProveedor.getListaDetalleFacturaProveedor()) {
/* 391:486 */       if (!dfp.isEliminado()) {
/* 392:487 */         detalle.add(dfp);
/* 393:    */       }
/* 394:    */     }
/* 395:490 */     return detalle;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public List<ImpuestoProductoFacturaProveedor> getListaImpuestoProductoNC()
/* 399:    */   {
/* 400:500 */     List<ImpuestoProductoFacturaProveedor> listaImpuestoProductoFacturaProveedor = new ArrayList();
/* 401:502 */     for (DetalleFacturaProveedor dfc : this.devolucionProveedor.getListaDetalleFacturaProveedor()) {
/* 402:504 */       for (ImpuestoProductoFacturaProveedor ipfp : dfc.getListaImpuestoProductoFacturaProveedor()) {
/* 403:505 */         if (!ipfp.isEliminado()) {
/* 404:506 */           listaImpuestoProductoFacturaProveedor.add(ipfp);
/* 405:    */         }
/* 406:    */       }
/* 407:    */     }
/* 408:512 */     return listaImpuestoProductoFacturaProveedor;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public String eliminarDetalle()
/* 412:    */   {
/* 413:522 */     DetalleFacturaProveedor d = (DetalleFacturaProveedor)this.dtDetalleDevolucionProveedor.getRowData();
/* 414:524 */     for (ImpuestoProductoFacturaProveedor ipfp : d.getListaImpuestoProductoFacturaProveedor()) {
/* 415:525 */       ipfp.setEliminado(true);
/* 416:    */     }
/* 417:528 */     d.setEliminado(true);
/* 418:529 */     totalizar();
/* 419:    */     
/* 420:531 */     return "";
/* 421:    */   }
/* 422:    */   
/* 423:    */   public String getDirectorioDescarga()
/* 424:    */   {
/* 425:536 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "devolucion_proveedor");
/* 426:    */   }
/* 427:    */   
/* 428:    */   public String getNombreArchivo()
/* 429:    */   {
/* 430:541 */     return this.devolucionProveedor.getPdf();
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void processUpload(FileUploadEvent event)
/* 434:    */   {
/* 435:    */     try
/* 436:    */     {
/* 437:554 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "devolucion_proveedor");
/* 438:    */       
/* 439:556 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), this.devolucionProveedor.getNumero(), uploadDir);
/* 440:    */       
/* 441:    */ 
/* 442:559 */       HashMap<String, Object> campos = new HashMap();
/* 443:560 */       campos.put("pdf", fileName);
/* 444:561 */       this.servicioNotaCreditoProveedor.actualizarAtributoEntidad(this.devolucionProveedor, campos);
/* 445:    */       
/* 446:563 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 447:    */     }
/* 448:    */     catch (Exception e)
/* 449:    */     {
/* 450:566 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 451:567 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 452:    */     }
/* 453:    */   }
/* 454:    */   
/* 455:    */   public String eliminarArchivo()
/* 456:    */   {
/* 457:572 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), this.devolucionProveedor.getPdf());
/* 458:573 */     this.devolucionProveedor.setPdf(null);
/* 459:574 */     HashMap<String, Object> campos = new HashMap();
/* 460:575 */     campos.put("pdf", null);
/* 461:576 */     this.servicioNotaCreditoProveedor.actualizarAtributoEntidad(this.devolucionProveedor, campos);
/* 462:577 */     return null;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public FacturaProveedor getDevolucionProveedor()
/* 466:    */   {
/* 467:593 */     return this.devolucionProveedor;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setDevolucionProveedor(FacturaProveedor devolucionProveedor)
/* 471:    */   {
/* 472:603 */     this.devolucionProveedor = devolucionProveedor;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public String getNumeroDevolucion()
/* 476:    */   {
/* 477:612 */     return this.numeroDevolucion;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setNumeroDevolucion(String numeroDevolucion)
/* 481:    */   {
/* 482:622 */     this.numeroDevolucion = numeroDevolucion;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public LazyDataModel<FacturaProveedor> getListaDevolucionProveedor()
/* 486:    */   {
/* 487:631 */     return this.listaDevolucionProveedor;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setListaDevolucionProveedor(LazyDataModel<FacturaProveedor> listaDevolucionProveedor)
/* 491:    */   {
/* 492:641 */     this.listaDevolucionProveedor = listaDevolucionProveedor;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public List<Documento> getListaDocumentoCombo()
/* 496:    */   {
/* 497:650 */     if (this.listaDocumentoCombo == null) {
/* 498:    */       try
/* 499:    */       {
/* 500:652 */         this.listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 501:    */       }
/* 502:    */       catch (ExcepcionAS2 e)
/* 503:    */       {
/* 504:654 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 505:    */       }
/* 506:    */     }
/* 507:657 */     return this.listaDocumentoCombo;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setListaDocumentoCombo(List<Documento> listaDocumentoCombo)
/* 511:    */   {
/* 512:667 */     this.listaDocumentoCombo = listaDocumentoCombo;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 516:    */   {
/* 517:676 */     return this.listaDireccionEmpresa;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 521:    */   {
/* 522:686 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public DataTable getDtDevolucionProveedor()
/* 526:    */   {
/* 527:695 */     return this.dtDevolucionProveedor;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setDtDevolucionProveedor(DataTable dtDevolucionProveedor)
/* 531:    */   {
/* 532:705 */     this.dtDevolucionProveedor = dtDevolucionProveedor;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public DataTable getDtDetalleDevolucionProveedor()
/* 536:    */   {
/* 537:714 */     return this.dtDetalleDevolucionProveedor;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setDtDetalleDevolucionProveedor(DataTable dtDetalleDevolucionProveedor)
/* 541:    */   {
/* 542:724 */     this.dtDetalleDevolucionProveedor = dtDetalleDevolucionProveedor;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public DataTable getDtImpuestoDevolucion()
/* 546:    */   {
/* 547:733 */     return this.dtImpuestoDevolucion;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setDtImpuestoDevolucion(DataTable dtImpuestoDevolucion)
/* 551:    */   {
/* 552:743 */     this.dtImpuestoDevolucion = dtImpuestoDevolucion;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public List<CreditoTributarioSRI> getListaSustentoTributarioSRI()
/* 556:    */   {
/* 557:752 */     if (this.listaSustentoTributarioSRI == null) {
/* 558:753 */       this.listaSustentoTributarioSRI = this.servicioCreditoTributario.obtenerListaCombo("nombre", true, null);
/* 559:    */     }
/* 560:755 */     return this.listaSustentoTributarioSRI;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public void setListaSustentoTributarioSRI(List<CreditoTributarioSRI> listaSustentoTributarioSRI)
/* 564:    */   {
/* 565:765 */     this.listaSustentoTributarioSRI = listaSustentoTributarioSRI;
/* 566:    */   }
/* 567:    */   
/* 568:    */   public List<MotivoNotaCreditoProveedor> getListaMotivoNotaCreditoProveedor()
/* 569:    */   {
/* 570:774 */     if (this.listaMotivoNotaCreditoProveedor == null) {
/* 571:775 */       this.listaMotivoNotaCreditoProveedor = this.servicioMotivoNotaCreditoProveedor.obtenerListaCombo("nombre", true, null);
/* 572:    */     }
/* 573:777 */     return this.listaMotivoNotaCreditoProveedor;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public void setListaMotivoNotaCreditoProveedor(List<MotivoNotaCreditoProveedor> listaMotivoNotaCreditoProveedor)
/* 577:    */   {
/* 578:787 */     this.listaMotivoNotaCreditoProveedor = listaMotivoNotaCreditoProveedor;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public List<Bodega> getListaBodega()
/* 582:    */   {
/* 583:796 */     if (this.listaBodega == null) {
/* 584:797 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 585:    */     }
/* 586:799 */     return this.listaBodega;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 590:    */   {
/* 591:809 */     this.listaBodega = listaBodega;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public Lote getLoteCrear()
/* 595:    */   {
/* 596:818 */     if (this.loteCrear == null) {
/* 597:819 */       this.loteCrear = new Lote();
/* 598:    */     }
/* 599:821 */     return this.loteCrear;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void setLoteCrear(Lote loteCrear)
/* 603:    */   {
/* 604:831 */     this.loteCrear = loteCrear;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public DetalleFacturaProveedor getDetalleFacturaProveedorSeleccionado()
/* 608:    */   {
/* 609:840 */     return this.detalleFacturaProveedorSeleccionado;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void setDetalleFacturaProveedorSeleccionado(DetalleFacturaProveedor detalleFacturaProveedorSeleccionado)
/* 613:    */   {
/* 614:850 */     this.detalleFacturaProveedorSeleccionado = detalleFacturaProveedorSeleccionado;
/* 615:    */   }
/* 616:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.DevolucionProveedorBean
 * JD-Core Version:    0.7.0.1
 */