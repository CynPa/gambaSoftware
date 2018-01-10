/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.FacturaProveedorBaseBean;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   6:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaCreditoProveedor;
/*   7:    */ import com.asinfo.as2.controller.LanguageController;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  10:    */ import com.asinfo.as2.entities.CuentaContable;
/*  11:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  12:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  16:    */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*  17:    */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*  18:    */ import com.asinfo.as2.entities.Organizacion;
/*  19:    */ import com.asinfo.as2.entities.Producto;
/*  20:    */ import com.asinfo.as2.entities.Sucursal;
/*  21:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  22:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  23:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  24:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  28:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  29:    */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioMotivoNotaCreditoProveedor;
/*  30:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  31:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  32:    */ import com.asinfo.as2.util.AppUtil;
/*  33:    */ import com.asinfo.as2.util.RutaArchivo;
/*  34:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  35:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  36:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  37:    */ import java.math.BigDecimal;
/*  38:    */ import java.util.ArrayList;
/*  39:    */ import java.util.Date;
/*  40:    */ import java.util.HashMap;
/*  41:    */ import java.util.List;
/*  42:    */ import java.util.Map;
/*  43:    */ import javax.annotation.PostConstruct;
/*  44:    */ import javax.ejb.EJB;
/*  45:    */ import javax.faces.bean.ManagedBean;
/*  46:    */ import javax.faces.bean.ViewScoped;
/*  47:    */ import javax.faces.component.html.HtmlInputText;
/*  48:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  49:    */ import org.apache.log4j.Logger;
/*  50:    */ import org.primefaces.component.datatable.DataTable;
/*  51:    */ import org.primefaces.event.FileUploadEvent;
/*  52:    */ import org.primefaces.event.SelectEvent;
/*  53:    */ import org.primefaces.model.LazyDataModel;
/*  54:    */ import org.primefaces.model.SortOrder;
/*  55:    */ 
/*  56:    */ @ManagedBean
/*  57:    */ @ViewScoped
/*  58:    */ public class NotaCreditoFinancieraProveedorBean
/*  59:    */   extends FacturaProveedorBaseBean
/*  60:    */ {
/*  61:    */   private static final long serialVersionUID = -596742886326861589L;
/*  62:    */   @EJB
/*  63:    */   private ServicioEmpresa servicioEmpresa;
/*  64:    */   @EJB
/*  65:    */   private ServicioDocumento servicioDocumento;
/*  66:    */   @EJB
/*  67:    */   private ServicioNotaCreditoProveedor servicioNotaCreditoProveedor;
/*  68:    */   @EJB
/*  69:    */   private ServicioMotivoNotaCreditoProveedor servicioMotivoNotaCreditoProveedor;
/*  70:    */   @EJB
/*  71:    */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  72:    */   @EJB
/*  73:    */   private ServicioCreditoTributario servicioCreditoTributario;
/*  74:    */   private BigDecimal totalGastoNotaCredito;
/*  75:    */   private CuentaContable cuentaContable;
/*  76:    */   private LazyDataModel<FacturaProveedor> listaNotaCreditoProveedor;
/*  77:    */   private List<Documento> listaDocumento;
/*  78:    */   private List<MotivoNotaCreditoProveedor> listaMotivoNotaCreditoProveedor;
/*  79:    */   private List<CreditoTributarioSRI> listaCreditoTributarioSRI;
/*  80:    */   private String numeroNotaCredito;
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:109 */       super.getListaProductoBean().setIndicadorCompra(true);
/*  88:110 */       this.listaNotaCreditoProveedor = new LazyDataModel()
/*  89:    */       {
/*  90:    */         private static final long serialVersionUID = 4780083578367601484L;
/*  91:    */         
/*  92:    */         public List<FacturaProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  93:    */         {
/*  94:117 */           if (NotaCreditoFinancieraProveedorBean.this.numeroNotaCredito != null) {
/*  95:118 */             filters.put("numero", NotaCreditoFinancieraProveedorBean.this.numeroNotaCredito);
/*  96:    */           }
/*  97:121 */           List<FacturaProveedor> lista = new ArrayList();
/*  98:    */           
/*  99:123 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 100:    */           
/* 101:125 */           filters.put("indicadorNotaCreditoDebito", "true");
/* 102:126 */           lista = NotaCreditoFinancieraProveedorBean.this.servicioNotaCreditoProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 103:    */           
/* 104:128 */           NotaCreditoFinancieraProveedorBean.this.listaNotaCreditoProveedor.setRowCount(NotaCreditoFinancieraProveedorBean.this.servicioNotaCreditoProveedor.contarPorCriterio(filters));
/* 105:129 */           return lista;
/* 106:    */         }
/* 107:    */       };
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:135 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 112:136 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String editar()
/* 117:    */   {
/* 118:147 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 119:148 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String guardar()
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:161 */       this.servicioNotaCreditoProveedor.guardar(getFacturaProveedor());
/* 127:162 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 128:    */       
/* 129:164 */       limpiar();
/* 130:    */     }
/* 131:    */     catch (ExcepcionAS2Financiero e)
/* 132:    */     {
/* 133:167 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 134:168 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 135:    */     }
/* 136:    */     catch (ExcepcionAS2 e)
/* 137:    */     {
/* 138:171 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 139:172 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 140:    */     }
/* 141:    */     catch (Exception e)
/* 142:    */     {
/* 143:175 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 144:176 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 145:    */     }
/* 146:180 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String eliminar()
/* 150:    */   {
/* 151:190 */     if (getNotaCreditoProveedor().getId() > 0) {
/* 152:    */       try
/* 153:    */       {
/* 154:193 */         this.servicioNotaCreditoProveedor.anular(getNotaCreditoProveedor());
/* 155:194 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 156:    */       }
/* 157:    */       catch (ExcepcionAS2Ventas e)
/* 158:    */       {
/* 159:197 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 160:198 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE PROVEEDOR ExcepcionAS2Ventas");
/* 161:    */       }
/* 162:    */       catch (ExcepcionAS2Financiero e)
/* 163:    */       {
/* 164:200 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 165:201 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE PROVEEDOR ExcepcionAS2Financiero");
/* 166:    */       }
/* 167:    */       catch (Exception e)
/* 168:    */       {
/* 169:203 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 170:204 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE PROVEEDOR Exception", e);
/* 171:    */       }
/* 172:    */     } else {
/* 173:207 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 174:    */     }
/* 175:210 */     return "";
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String limpiar()
/* 179:    */   {
/* 180:221 */     setEditado(false);
/* 181:    */     
/* 182:223 */     crearNotaCredito();
/* 183:    */     
/* 184:225 */     return "";
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void crearNotaCredito()
/* 188:    */   {
/* 189:232 */     setNotaCreditoProveedor(new FacturaProveedor());
/* 190:233 */     getNotaCreditoProveedor().setNumero("");
/* 191:234 */     getNotaCreditoProveedor().setFecha(new Date());
/* 192:235 */     getNotaCreditoProveedor().setEstado(Estado.ELABORADO);
/* 193:236 */     getNotaCreditoProveedor().setNumeroCuotas(1);
/* 194:237 */     getNotaCreditoProveedor().setIndicadorNotaCreditoDebito(true);
/* 195:    */     
/* 196:239 */     getNotaCreditoProveedor().setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 197:240 */     getNotaCreditoProveedor().setSucursal(AppUtil.getSucursal());
/* 198:    */     
/* 199:242 */     FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/* 200:243 */     facturaProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 201:244 */     facturaProveedorSRI.setIdSucursal(getNotaCreditoProveedor().getSucursal().getId());
/* 202:245 */     facturaProveedorSRI.setNumero("0");
/* 203:246 */     facturaProveedorSRI.setEstablecimientoRetencion("000");
/* 204:247 */     facturaProveedorSRI.setPuntoEmisionRetencion("000");
/* 205:248 */     facturaProveedorSRI.setNumeroRetencion("0");
/* 206:249 */     facturaProveedorSRI.setAutorizacionRetencion("000");
/* 207:250 */     facturaProveedorSRI.setFacturaProveedor(getNotaCreditoProveedor());
/* 208:251 */     getNotaCreditoProveedor().setFacturaProveedorSRI(facturaProveedorSRI);
/* 209:    */     
/* 210:253 */     Documento documento = null;
/* 211:254 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 212:    */     {
/* 213:255 */       documento = (Documento)getListaDocumento().get(0);
/* 214:256 */       getNotaCreditoProveedor().setDocumento(documento);
/* 215:257 */       actualizarDocumento();
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:259 */       getNotaCreditoProveedor().setDocumento(new Documento());
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<Empresa> autocompletarProveedors(String consulta)
/* 224:    */   {
/* 225:264 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<FacturaProveedor> autocompletarFacturas(String consulta)
/* 229:    */   {
/* 230:268 */     consulta = consulta.toUpperCase();
/* 231:269 */     List<FacturaProveedor> lista = new ArrayList();
/* 232:270 */     if (getNotaCreditoProveedor().getEmpresa() != null) {
/* 233:271 */       lista = this.servicioFacturaProveedor.obtenerListaComboAutocompletar(getNotaCreditoProveedor().getEmpresa().getId(), consulta);
/* 234:    */     } else {
/* 235:273 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 236:    */     }
/* 237:276 */     return lista;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void totalizar()
/* 241:    */   {
/* 242:    */     try
/* 243:    */     {
/* 244:282 */       this.servicioNotaCreditoProveedor.totalizar(getNotaCreditoProveedor());
/* 245:    */     }
/* 246:    */     catch (ExcepcionAS2Compras e)
/* 247:    */     {
/* 248:284 */       LOG.error(e.getErrorMessage(e));
/* 249:    */     }
/* 250:    */     catch (Exception e)
/* 251:    */     {
/* 252:286 */       LOG.error(e);
/* 253:287 */       e.printStackTrace();
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void actualizarProveedor(SelectEvent event)
/* 258:    */   {
/* 259:298 */     Empresa empresa = (Empresa)event.getObject();
/* 260:299 */     getNotaCreditoProveedor().setEmpresa(empresa);
/* 261:300 */     getNotaCreditoProveedor().setFacturaProveedorPadre(null);
/* 262:301 */     getNotaCreditoProveedor().setDireccionEmpresa(null);
/* 263:    */     
/* 264:303 */     List<DireccionEmpresa> listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(empresa.getId());
/* 265:305 */     for (DireccionEmpresa de : listaDireccionEmpresa) {
/* 266:306 */       if (de.isIndicadorDireccionPrincipal())
/* 267:    */       {
/* 268:307 */         getNotaCreditoProveedor().setDireccionEmpresa(de);
/* 269:308 */         break;
/* 270:    */       }
/* 271:    */     }
/* 272:312 */     cargarListaCreditoTributarioSRI();
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void cargarDetalleNotaCreditoProveedor(SelectEvent event)
/* 276:    */   {
/* 277:321 */     FacturaProveedor facturaProveedor = (FacturaProveedor)event.getObject();
/* 278:322 */     if (getIndicadorNotaCreditoReversaGasto()) {
/* 279:    */       try
/* 280:    */       {
/* 281:326 */         if (getNotaCreditoProveedor().getId() == 0)
/* 282:    */         {
/* 283:327 */           getNotaCreditoProveedor().setListaDetalleFacturaProveedor(new ArrayList());
/* 284:328 */           setNotaCreditoProveedor(this.servicioNotaCreditoProveedor.cargarDetalleFactura(facturaProveedor, getNotaCreditoProveedor()));
/* 285:329 */           cargarDirecciones();
/* 286:330 */           for (DetalleFacturaProveedor detalleFacturaProveedor : getNotaCreditoProveedor().getListaDetalleFacturaProveedor())
/* 287:    */           {
/* 288:331 */             detalleFacturaProveedor.setCantidad(BigDecimal.valueOf(1L));
/* 289:332 */             detalleFacturaProveedor.setPrecio(BigDecimal.ZERO);
/* 290:333 */             for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : detalleFacturaProveedor
/* 291:334 */               .getListaGastoProductoFacturaProveedor()) {
/* 292:335 */               gastoProductoFacturaProveedor.setValor(BigDecimal.ZERO);
/* 293:    */             }
/* 294:    */           }
/* 295:340 */           CreditoTributarioSRI creditoTributarioSRI = getNotaCreditoProveedor().getFacturaProveedorSRI().getCreditoTributarioSRI();
/* 296:341 */           getNotaCreditoProveedor().setFacturaProveedorSRI(new FacturaProveedorSRI());
/* 297:342 */           getNotaCreditoProveedor().getFacturaProveedorSRI().setFacturaProveedor(getNotaCreditoProveedor());
/* 298:343 */           getNotaCreditoProveedor().getFacturaProveedorSRI().setCreditoTributarioSRI(creditoTributarioSRI);
/* 299:344 */           totalizar();
/* 300:    */         }
/* 301:    */       }
/* 302:    */       catch (ExcepcionAS2Compras e)
/* 303:    */       {
/* 304:348 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 305:    */       }
/* 306:    */       catch (ExcepcionAS2 e)
/* 307:    */       {
/* 308:351 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 309:    */       }
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void cargarProducto(Producto producto)
/* 314:    */   {
/* 315:365 */     if (producto != null)
/* 316:    */     {
/* 317:367 */       DetalleFacturaProveedor detalleFacturaProveedor = new DetalleFacturaProveedor();
/* 318:368 */       detalleFacturaProveedor.setCantidad(BigDecimal.ONE);
/* 319:369 */       actualizarProducto(detalleFacturaProveedor, producto);
/* 320:370 */       this.facturaProveedor.getListaDetalleFacturaProveedor().add(detalleFacturaProveedor);
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void cargarCuentaContable()
/* 325:    */   {
/* 326:376 */     this.cuentaContable = ((CuentaContable)getDtCuentaContable().getRowData());
/* 327:377 */     getGastoProductoFacturaProveedorSeleccionado().setCuentaContableGasto(this.cuentaContable);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/* 331:    */   {
/* 332:382 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)getDtDetalleFacturaProveedor().getRowData();
/* 333:383 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 334:    */     try
/* 335:    */     {
/* 336:386 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.NOTA_CREDITO_PROVEEDOR);
/* 337:387 */       dfp.setCantidad(BigDecimal.ONE);
/* 338:388 */       actualizarProducto(dfp, producto);
/* 339:    */     }
/* 340:    */     catch (ExcepcionAS2 e)
/* 341:    */     {
/* 342:391 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public String actualizarDocumento()
/* 347:    */   {
/* 348:397 */     getNotaCreditoProveedor().getFacturaProveedorSRI().setEstablecimiento(null);
/* 349:398 */     getNotaCreditoProveedor().getFacturaProveedorSRI().setPuntoEmision(null);
/* 350:399 */     getNotaCreditoProveedor().getFacturaProveedorSRI().setNumero(null);
/* 351:400 */     getNotaCreditoProveedor().getFacturaProveedorSRI().setAutorizacion(null);
/* 352:    */     
/* 353:402 */     setSecuenciaEditable(!getNotaCreditoProveedor().getDocumento().isIndicadorBloqueoSecuencia());
/* 354:    */     
/* 355:404 */     return "";
/* 356:    */   }
/* 357:    */   
/* 358:    */   public boolean getIndicadorNotaCreditoReversaGasto()
/* 359:    */   {
/* 360:408 */     return ParametrosSistema.getNotaCreditoReversaGasto(AppUtil.getOrganizacion().getId()).booleanValue();
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void processDownload()
/* 364:    */   {
/* 365:    */     try
/* 366:    */     {
/* 367:418 */       FacturaProveedor fp = (FacturaProveedor)getDtFacturaProveedor().getRowData();
/* 368:419 */       String fileName = fp.getPdf();
/* 369:420 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "nota_credito_proveedor");
/* 370:422 */       if (fileName == null) {
/* 371:423 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 372:    */       } else {
/* 373:425 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 374:    */       }
/* 375:    */     }
/* 376:    */     catch (Exception e)
/* 377:    */     {
/* 378:429 */       e.printStackTrace();
/* 379:430 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 380:    */     }
/* 381:    */   }
/* 382:    */   
/* 383:    */   public String eliminarArchivo()
/* 384:    */   {
/* 385:435 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getFacturaProveedor().getPdf());
/* 386:436 */     getFacturaProveedor().setPdf(null);
/* 387:437 */     HashMap<String, Object> campos = new HashMap();
/* 388:438 */     campos.put("pdf", null);
/* 389:439 */     this.servicioFacturaProveedor.actualizarAtributoEntidad(getFacturaProveedor(), campos);
/* 390:440 */     return null;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void processUpload(FileUploadEvent event)
/* 394:    */   {
/* 395:    */     try
/* 396:    */     {
/* 397:453 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "nota_credito_proveedor");
/* 398:    */       
/* 399:455 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getFacturaProveedor().getNumero(), uploadDir);
/* 400:    */       
/* 401:    */ 
/* 402:458 */       HashMap<String, Object> campos = new HashMap();
/* 403:459 */       campos.put("pdf", fileName);
/* 404:460 */       this.servicioFacturaProveedor.actualizarAtributoEntidad(getFacturaProveedor(), campos);
/* 405:    */       
/* 406:462 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 407:    */     }
/* 408:    */     catch (Exception e)
/* 409:    */     {
/* 410:465 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 411:466 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 412:    */     }
/* 413:    */   }
/* 414:    */   
/* 415:    */   public String getDirectorioDescarga()
/* 416:    */   {
/* 417:473 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "nota_credito_proveedor");
/* 418:    */   }
/* 419:    */   
/* 420:    */   public List<GastoProductoFacturaProveedor> getListaGastoProductoFacturaProveedor()
/* 421:    */   {
/* 422:486 */     List<GastoProductoFacturaProveedor> lista = new ArrayList();
/* 423:487 */     this.totalGastoNotaCredito = BigDecimal.ZERO;
/* 424:488 */     for (DetalleFacturaProveedor detalleFacturaProveedor : getNotaCreditoProveedor().getListaDetalleFacturaProveedor()) {
/* 425:489 */       if ((!detalleFacturaProveedor.isEliminado()) && (detalleFacturaProveedor.getProducto().isTraIndicadorServicio())) {
/* 426:490 */         for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : detalleFacturaProveedor.getListaGastoProductoFactura()) {
/* 427:491 */           if (!gastoProductoFacturaProveedor.isEliminado())
/* 428:    */           {
/* 429:492 */             lista.add(gastoProductoFacturaProveedor);
/* 430:493 */             this.totalGastoNotaCredito = this.totalGastoNotaCredito.add(gastoProductoFacturaProveedor.getValor());
/* 431:    */           }
/* 432:    */         }
/* 433:    */       }
/* 434:    */     }
/* 435:498 */     return lista;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void buscarCuentaContable()
/* 439:    */   {
/* 440:506 */     GastoProductoFacturaProveedor gastoProductoFacturaProveedor = (GastoProductoFacturaProveedor)getDtGastoProductoFacturaProveedor().getRowData();
/* 441:507 */     String codigoCuentaContable = gastoProductoFacturaProveedor.getCuentaContableGasto().getCodigo();
/* 442:    */     try
/* 443:    */     {
/* 444:509 */       if (codigoCuentaContable != "")
/* 445:    */       {
/* 446:510 */         this.cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion().getIdOrganizacion());
/* 447:511 */         gastoProductoFacturaProveedor.setCuentaContableGasto(this.cuentaContable);
/* 448:    */       }
/* 449:    */     }
/* 450:    */     catch (ExcepcionAS2Financiero e)
/* 451:    */     {
/* 452:514 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 453:515 */       addInfoMessage(strMensaje);
/* 454:    */     }
/* 455:    */   }
/* 456:    */   
/* 457:    */   public FacturaProveedor getNotaCreditoProveedor()
/* 458:    */   {
/* 459:525 */     return getFacturaProveedor();
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setNotaCreditoProveedor(FacturaProveedor notaCreditoProveedor)
/* 463:    */   {
/* 464:535 */     setFacturaProveedor(notaCreditoProveedor);
/* 465:    */   }
/* 466:    */   
/* 467:    */   public LazyDataModel<FacturaProveedor> getListaNotaCreditoProveedor()
/* 468:    */   {
/* 469:544 */     return this.listaNotaCreditoProveedor;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setListaNotaCreditoProveedor(LazyDataModel<FacturaProveedor> listaNotaCreditoProveedor)
/* 473:    */   {
/* 474:554 */     this.listaNotaCreditoProveedor = listaNotaCreditoProveedor;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public List<Documento> getListaDocumento()
/* 478:    */   {
/* 479:563 */     if (this.listaDocumento == null) {
/* 480:    */       try
/* 481:    */       {
/* 482:565 */         List<Documento> listaDocumentoNotaCreditoProveedor = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.NOTA_CREDITO_PROVEEDOR);
/* 483:566 */         List<Documento> listaDocumentoNotaDebitoProveedor = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.NOTA_DEBITO_PROVEEDOR);
/* 484:567 */         this.listaDocumento = new ArrayList();
/* 485:568 */         this.listaDocumento.addAll(listaDocumentoNotaCreditoProveedor);
/* 486:569 */         this.listaDocumento.addAll(listaDocumentoNotaDebitoProveedor);
/* 487:    */       }
/* 488:    */       catch (ExcepcionAS2 e)
/* 489:    */       {
/* 490:571 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 491:    */       }
/* 492:    */     }
/* 493:575 */     return this.listaDocumento;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void agregarGastoProductoFacturaProveedor()
/* 497:    */   {
/* 498:579 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)getDtDetalleFacturaProveedor().getRowData();
/* 499:580 */     GastoProductoFacturaProveedor gastoProductoFacturaProveedor = new GastoProductoFacturaProveedor();
/* 500:581 */     gastoProductoFacturaProveedor.setDetalleFacturaProveedor(dfp);
/* 501:582 */     gastoProductoFacturaProveedor.setCuentaContableGasto(new CuentaContable());
/* 502:583 */     dfp.getListaGastoProductoFacturaProveedor().add(gastoProductoFacturaProveedor);
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 506:    */   {
/* 507:593 */     this.listaDocumento = listaDocumento;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public String getNumeroNotaCredito()
/* 511:    */   {
/* 512:602 */     return this.numeroNotaCredito;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setNumeroNotaCredito(String numeroNotaCredito)
/* 516:    */   {
/* 517:612 */     this.numeroNotaCredito = numeroNotaCredito;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public BigDecimal getTotalGastoNotaCredito()
/* 521:    */   {
/* 522:621 */     return this.totalGastoNotaCredito;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void setTotalGastoNotaCredito(BigDecimal totalGastoNotaCredito)
/* 526:    */   {
/* 527:631 */     this.totalGastoNotaCredito = totalGastoNotaCredito;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public List<CreditoTributarioSRI> getListaCreditoTributarioSRI()
/* 531:    */   {
/* 532:640 */     return this.listaCreditoTributarioSRI;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public void setListaCreditoTributarioSRI(List<CreditoTributarioSRI> listaCreditoTributarioSRI)
/* 536:    */   {
/* 537:650 */     this.listaCreditoTributarioSRI = listaCreditoTributarioSRI;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public List<MotivoNotaCreditoProveedor> getListaMotivoNotaCreditoProveedor()
/* 541:    */   {
/* 542:659 */     if (this.listaMotivoNotaCreditoProveedor == null) {
/* 543:660 */       this.listaMotivoNotaCreditoProveedor = this.servicioMotivoNotaCreditoProveedor.obtenerListaCombo("nombre", true, null);
/* 544:    */     }
/* 545:662 */     return this.listaMotivoNotaCreditoProveedor;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public CuentaContable getCuentaContable()
/* 549:    */   {
/* 550:671 */     return this.cuentaContable;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 554:    */   {
/* 555:681 */     this.cuentaContable = cuentaContable;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void cargarListaCreditoTributarioSRI()
/* 559:    */   {
/* 560:685 */     this.listaCreditoTributarioSRI = this.servicioCreditoTributario.buscarPorTipoComprobanteSRI(
/* 561:686 */       getNotaCreditoProveedor().getDocumento().getTipoComprobanteSRI(), getNotaCreditoProveedor().getEmpresa().getTipoIdentificacion());
/* 562:    */   }
/* 563:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.NotaCreditoFinancieraProveedorBean
 * JD-Core Version:    0.7.0.1
 */