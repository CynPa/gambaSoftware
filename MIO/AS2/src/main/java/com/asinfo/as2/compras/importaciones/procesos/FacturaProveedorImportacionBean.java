/*   1:    */ package com.asinfo.as2.compras.importaciones.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioPartidaArancelaria;
/*   5:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioProcesoImportacion;
/*   6:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioDetalleFacturaProveedorImportacionGasto;
/*   7:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*   8:    */ import com.asinfo.as2.compras.importaciones.reportes.servicio.ServicioReporteImportacion;
/*   9:    */ import com.asinfo.as2.compras.procesos.FacturaProveedorBaseBean;
/*  10:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*  11:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*  12:    */ import com.asinfo.as2.controller.LanguageController;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  14:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  15:    */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*  16:    */ import com.asinfo.as2.entities.CuentaContable;
/*  17:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  18:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
/*  19:    */ import com.asinfo.as2.entities.DetalleProcesoImportacion;
/*  20:    */ import com.asinfo.as2.entities.Documento;
/*  21:    */ import com.asinfo.as2.entities.Empresa;
/*  22:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  23:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*  24:    */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*  25:    */ import com.asinfo.as2.entities.Moneda;
/*  26:    */ import com.asinfo.as2.entities.Organizacion;
/*  27:    */ import com.asinfo.as2.entities.Pais;
/*  28:    */ import com.asinfo.as2.entities.PartidaArancelaria;
/*  29:    */ import com.asinfo.as2.entities.ProcesoImportacion;
/*  30:    */ import com.asinfo.as2.entities.Producto;
/*  31:    */ import com.asinfo.as2.entities.Proveedor;
/*  32:    */ import com.asinfo.as2.entities.Secuencia;
/*  33:    */ import com.asinfo.as2.entities.Sucursal;
/*  34:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  35:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  36:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  37:    */ import com.asinfo.as2.enumeraciones.MedioTransporteEnum;
/*  38:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  39:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  40:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  41:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  42:    */ import com.asinfo.as2.util.AppUtil;
/*  43:    */ import com.asinfo.as2.util.RutaArchivo;
/*  44:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  45:    */ import com.asinfo.as2.utils.JsfUtil;
/*  46:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  47:    */ import java.math.BigDecimal;
/*  48:    */ import java.util.ArrayList;
/*  49:    */ import java.util.Date;
/*  50:    */ import java.util.HashMap;
/*  51:    */ import java.util.List;
/*  52:    */ import java.util.Map;
/*  53:    */ import javax.annotation.PostConstruct;
/*  54:    */ import javax.ejb.EJB;
/*  55:    */ import javax.faces.bean.ManagedBean;
/*  56:    */ import javax.faces.bean.ViewScoped;
/*  57:    */ import javax.faces.model.SelectItem;
/*  58:    */ import org.apache.log4j.Logger;
/*  59:    */ import org.primefaces.component.datatable.DataTable;
/*  60:    */ import org.primefaces.event.FileUploadEvent;
/*  61:    */ import org.primefaces.event.SelectEvent;
/*  62:    */ import org.primefaces.event.ToggleEvent;
/*  63:    */ 
/*  64:    */ @ManagedBean
/*  65:    */ @ViewScoped
/*  66:    */ public class FacturaProveedorImportacionBean
/*  67:    */   extends FacturaProveedorBaseBean
/*  68:    */ {
/*  69:    */   private static final long serialVersionUID = -4615327195064201298L;
/*  70:    */   private Boolean indicadorRenderPanelImportacion;
/*  71:    */   private String numero;
/*  72:    */   @EJB
/*  73:    */   private ServicioPartidaArancelaria servicioPartidaArancelaria;
/*  74:    */   @EJB
/*  75:    */   private ServicioMoneda servicioMoneda;
/*  76:    */   @EJB
/*  77:    */   private ServicioPais servicioPais;
/*  78:    */   @EJB
/*  79:    */   private ServicioDetalleFacturaProveedorImportacionGasto servicioDetalleFacturaProveedorImportacionGasto;
/*  80:    */   @EJB
/*  81:    */   private ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioProcesoImportacion servicioProcesoImportacion;
/*  84:    */   @EJB
/*  85:    */   private ServicioReporteImportacion servicioReporteImportacion;
/*  86:    */   private List<SelectItem> listaMedioTransporte;
/*  87:    */   private List<SelectItem> listaEstado;
/*  88:    */   private List<PartidaArancelaria> listaPartidaArancelaria;
/*  89:    */   private List<Moneda> listaMoneda;
/*  90:    */   private List<Pais> listaPais;
/*  91:    */   private List<ProcesoImportacion> listaProcesoImportacion;
/*  92:    */   private List<FacturaProveedor> listaFacturasProveedor;
/*  93:    */   private DataTable dtListaReporte;
/*  94:    */   private CuentaContable cuentaContable;
/*  95:    */   private DataTable dtDetalleProcesoImportacion;
/*  96:    */   private DataTable dtDetalleFacturaProveedorImportacionGasto;
/*  97:    */   
/*  98:    */   protected void obtenerFiltros(Map<String, String> filters)
/*  99:    */   {
/* 100:129 */     if (this.numero != null)
/* 101:    */     {
/* 102:130 */       filters.put("numero", this.numero);
/* 103:131 */       this.numero = null;
/* 104:    */     }
/* 105:133 */     filters.put("documento.documentoBase", DocumentoBase.PEDIDO_IMPORTACION.toString());
/* 106:134 */     filters.put("documento.indicadorDocumentoExterior", "true");
/* 107:    */   }
/* 108:    */   
/* 109:    */   @PostConstruct
/* 110:    */   public void init()
/* 111:    */   {
/* 112:139 */     super.init();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String editar()
/* 116:    */   {
/* 117:149 */     if (getFacturaProveedor().getId() > 0) {
/* 118:    */       try
/* 119:    */       {
/* 120:151 */         this.servicioFacturaProveedor.esEditable(this.facturaProveedor);
/* 121:153 */         if (!this.facturaProveedor.getEstado().equals(Estado.CERRADO))
/* 122:    */         {
/* 123:154 */           setIndicadorRenderPanelImportacion(Boolean.valueOf(true));
/* 124:155 */           this.facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(this.facturaProveedor.getId()));
/* 125:156 */           cargarDirecciones();
/* 126:157 */           setEditado(true);
/* 127:    */         }
/* 128:    */         else
/* 129:    */         {
/* 130:159 */           addInfoMessage(getLanguageController().getMensaje("msg_error_factura_proveedor_importacion_cerrado"));
/* 131:    */         }
/* 132:    */       }
/* 133:    */       catch (ExcepcionAS2Financiero e)
/* 134:    */       {
/* 135:163 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 136:164 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 137:    */       }
/* 138:    */       catch (ExcepcionAS2Compras e)
/* 139:    */       {
/* 140:167 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 141:168 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 142:    */       }
/* 143:    */       catch (Exception e)
/* 144:    */       {
/* 145:171 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 146:172 */         LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 147:    */       }
/* 148:    */     } else {
/* 149:175 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 150:    */     }
/* 151:178 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String guardar()
/* 155:    */   {
/* 156:    */     try
/* 157:    */     {
/* 158:190 */       this.facturaProveedor.getDocumento().setIndicadorDocumentoTributario(false);
/* 159:191 */       this.servicioFacturaProveedorImportacion.validarFacturaProveedorImportacion(this.facturaProveedor.getFacturaProveedorImportacion());
/* 160:    */       
/* 161:193 */       this.servicioFacturaProveedor.guardar(this.facturaProveedor);
/* 162:194 */       this.servicioFacturaProveedorImportacion.agregarPresupuestoImportacion(this.facturaProveedor, true);
/* 163:195 */       this.servicioFacturaProveedorImportacion.prorratear(this.facturaProveedor.getFacturaProveedorImportacion().getId());
/* 164:196 */       limpiar();
/* 165:197 */       setIndicadorRenderPanelImportacion(Boolean.valueOf(false));
/* 166:198 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 167:    */     }
/* 168:    */     catch (ExcepcionAS2Financiero e)
/* 169:    */     {
/* 170:201 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 171:202 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 172:    */     }
/* 173:    */     catch (ExcepcionAS2Compras e)
/* 174:    */     {
/* 175:205 */       actualizarDocumento();
/* 176:206 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 177:207 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 178:    */     }
/* 179:    */     catch (AS2Exception e)
/* 180:    */     {
/* 181:210 */       JsfUtil.addErrorMessage(e, "");
/* 182:211 */       e.printStackTrace();
/* 183:    */     }
/* 184:    */     catch (ExcepcionAS2 e)
/* 185:    */     {
/* 186:213 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 187:214 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 188:    */     }
/* 189:    */     catch (Exception e)
/* 190:    */     {
/* 191:217 */       addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 192:218 */       e.printStackTrace();
/* 193:    */     }
/* 194:221 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String eliminar()
/* 198:    */   {
/* 199:232 */     if (this.facturaProveedor.getId() > 0) {
/* 200:    */       try
/* 201:    */       {
/* 202:235 */         this.servicioFacturaProveedor.anularFacturaProveedorImportacion(this.facturaProveedor);
/* 203:    */       }
/* 204:    */       catch (ExcepcionAS2Financiero e)
/* 205:    */       {
/* 206:238 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 207:239 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR IMPORTACION:", e);
/* 208:    */       }
/* 209:    */       catch (ExcepcionAS2Compras e)
/* 210:    */       {
/* 211:242 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 212:243 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR IMPORTACION:", e);
/* 213:    */       }
/* 214:    */       catch (ExcepcionAS2 e)
/* 215:    */       {
/* 216:246 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 217:247 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR IMPORTACION:", e);
/* 218:    */       }
/* 219:    */       catch (Exception e)
/* 220:    */       {
/* 221:250 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 222:251 */         LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR IMPORTACION:", e);
/* 223:    */       }
/* 224:    */     } else {
/* 225:254 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 226:    */     }
/* 227:257 */     return "";
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void eliminarDetalleProcesoImportacion()
/* 231:    */   {
/* 232:261 */     DetalleProcesoImportacion detalleProcesoImportacion = (DetalleProcesoImportacion)this.dtDetalleProcesoImportacion.getRowData();
/* 233:262 */     detalleProcesoImportacion.setEliminado(true);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String limpiar()
/* 237:    */   {
/* 238:273 */     setEditado(false);
/* 239:    */     
/* 240:275 */     this.facturaProveedor = new FacturaProveedor();
/* 241:    */     
/* 242:277 */     this.facturaProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 243:278 */     this.facturaProveedor.setSucursal(AppUtil.getSucursal());
/* 244:279 */     this.facturaProveedor.setNumero("");
/* 245:280 */     this.facturaProveedor.setFecha(new Date());
/* 246:281 */     this.facturaProveedor.setEstado(Estado.ELABORADO);
/* 247:282 */     this.facturaProveedor.setNumeroCuotas(1);
/* 248:284 */     if ((getListaDocumentoFactura() != null) && (!getListaDocumentoFactura().isEmpty()))
/* 249:    */     {
/* 250:285 */       Documento documento = (Documento)getListaDocumentoFactura().get(0);
/* 251:286 */       this.facturaProveedor.setDocumento(documento);
/* 252:287 */       actualizarDocumento();
/* 253:    */     }
/* 254:    */     else
/* 255:    */     {
/* 256:290 */       Documento documento = new Documento();
/* 257:291 */       documento.setSecuencia(new Secuencia());
/* 258:292 */       this.facturaProveedor.setDocumento(documento);
/* 259:    */     }
/* 260:294 */     crearFacturaClienteImportacion(this.facturaProveedor);
/* 261:    */     
/* 262:296 */     setListaDireccionEmpresa(new ArrayList());
/* 263:297 */     this.listaPedidoProveedorPorFacturar.clear();
/* 264:298 */     setPedidoProveedor(null);
/* 265:299 */     return "";
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void actualizarProducto(DetalleFacturaProveedor dfp, Producto producto)
/* 269:    */   {
/* 270:    */     try
/* 271:    */     {
/* 272:310 */       for (GastoProductoFacturaProveedor gpfp : dfp.getListaGastoProductoFactura()) {
/* 273:311 */         gpfp.setEliminado(true);
/* 274:    */       }
/* 275:314 */       dfp.setProducto(producto);
/* 276:315 */       dfp.setFacturaProveedor(this.facturaProveedor);
/* 277:316 */       dfp.setPartidaArancelaria(producto.getPartidaArancelaria());
/* 278:317 */       dfp.setUnidadCompra(producto.getUnidadCompra());
/* 279:    */       
/* 280:319 */       totalizar();
/* 281:320 */       calcularPesoNeto(dfp);
/* 282:    */     }
/* 283:    */     catch (Exception e)
/* 284:    */     {
/* 285:323 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void totalizar()
/* 290:    */   {
/* 291:    */     try
/* 292:    */     {
/* 293:335 */       this.servicioFacturaProveedor.totalizar(getFacturaProveedor());
/* 294:    */       
/* 295:    */ 
/* 296:338 */       cargarCuentaPorPagar();
/* 297:339 */       calcularPesoNeto();
/* 298:    */     }
/* 299:    */     catch (ExcepcionAS2Compras e)
/* 300:    */     {
/* 301:342 */       LOG.info(e.getErrorMessage(e));
/* 302:    */     }
/* 303:    */     catch (Exception e)
/* 304:    */     {
/* 305:344 */       LOG.info(e);
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public String actualizarDocumento()
/* 310:    */   {
/* 311:349 */     return "";
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void actualizarProveedor(Empresa empresa)
/* 315:    */   {
/* 316:357 */     getFacturaProveedor().setEmpresa(empresa);
/* 317:360 */     if (getFacturaProveedor().getCondicionPago() != null) {
/* 318:361 */       getFacturaProveedor().setCondicionPago(empresa.getProveedor().getCondicionPago());
/* 319:    */     }
/* 320:365 */     if (getFacturaProveedor().getNumeroCuotas() == 0) {
/* 321:366 */       getFacturaProveedor().setNumeroCuotas(empresa.getProveedor().getNumeroCuotas());
/* 322:    */     }
/* 323:369 */     cargarDirecciones();
/* 324:370 */     actualizarListaPedidoClienteADespachar();
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void agregarPresupuestoImportacion()
/* 328:    */   {
/* 329:    */     try
/* 330:    */     {
/* 331:382 */       this.servicioFacturaProveedorImportacion.agregarPresupuestoImportacion(this.facturaProveedor, false);
/* 332:    */     }
/* 333:    */     catch (AS2Exception e)
/* 334:    */     {
/* 335:384 */       JsfUtil.addErrorMessage(e, "");
/* 336:385 */       e.printStackTrace();
/* 337:    */     }
/* 338:    */     catch (Exception e)
/* 339:    */     {
/* 340:387 */       addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 341:388 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void cargaDatosImportacion()
/* 346:    */   {
/* 347:395 */     limpiar();
/* 348:396 */     this.facturaProveedor = ((FacturaProveedor)getDtFacturaProveedor().getRowData());
/* 349:397 */     if ((this.facturaProveedor != null) && (this.facturaProveedor.getId() > 0)) {
/* 350:    */       try
/* 351:    */       {
/* 352:399 */         setIndicadorRenderPanelImportacion(Boolean.valueOf(true));
/* 353:400 */         this.facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(this.facturaProveedor.getId()));
/* 354:    */       }
/* 355:    */       catch (ExcepcionAS2Compras e)
/* 356:    */       {
/* 357:402 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 358:403 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 359:404 */         e.printStackTrace();
/* 360:    */       }
/* 361:    */       catch (Exception e)
/* 362:    */       {
/* 363:406 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 364:407 */         LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 365:    */       }
/* 366:    */     } else {
/* 367:410 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 368:    */     }
/* 369:    */   }
/* 370:    */   
/* 371:    */   public String guardarDatosImportacion()
/* 372:    */   {
/* 373:    */     try
/* 374:    */     {
/* 375:417 */       this.servicioFacturaProveedorImportacion.guardarDatosImportacion(this.facturaProveedor.getFacturaProveedorImportacion());
/* 376:418 */       limpiar();
/* 377:419 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 378:420 */       setIndicadorRenderPanelImportacion(Boolean.valueOf(false));
/* 379:    */     }
/* 380:    */     catch (Exception e)
/* 381:    */     {
/* 382:422 */       e.printStackTrace();
/* 383:423 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 384:    */     }
/* 385:426 */     return "";
/* 386:    */   }
/* 387:    */   
/* 388:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 389:    */   {
/* 390:433 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void crearFacturaClienteImportacion(FacturaProveedor fp)
/* 394:    */   {
/* 395:440 */     FacturaProveedorImportacion facturaProveedorImportacion = new FacturaProveedorImportacion();
/* 396:441 */     facturaProveedorImportacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 397:442 */     facturaProveedorImportacion.setSucursal(AppUtil.getSucursal());
/* 398:443 */     facturaProveedorImportacion.setFacturaProveedor(fp);
/* 399:444 */     facturaProveedorImportacion.setPais(new Pais());
/* 400:445 */     int idCuentaContable = ParametrosSistema.getCuentaImportacionTransito(AppUtil.getOrganizacion().getIdOrganizacion()).intValue();
/* 401:446 */     this.cuentaContable = this.servicioCuentaContable.buscarPorId(Integer.valueOf(idCuentaContable));
/* 402:447 */     if (this.cuentaContable != null) {
/* 403:448 */       facturaProveedorImportacion.setCuentaContableImportacion(this.cuentaContable);
/* 404:    */     } else {
/* 405:450 */       facturaProveedorImportacion.setCuentaContableImportacion(null);
/* 406:    */     }
/* 407:452 */     fp.setIndicadorGastoImportacion(false);
/* 408:453 */     fp.setFacturaProveedorImportacion(facturaProveedorImportacion);
/* 409:454 */     setIndicadorRenderPanelImportacion(Boolean.valueOf(true));
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void seleccionarCuentaContable(SelectEvent event)
/* 413:    */   {
/* 414:459 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/* 415:460 */     getFacturaProveedor().getFacturaProveedorImportacion().setCuentaContableImportacion(cuentaContable);
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void calcularPesoNeto()
/* 419:    */   {
/* 420:465 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)getDtDetalleFacturaProveedor().getRowData();
/* 421:466 */     calcularPesoNeto(dfp);
/* 422:    */   }
/* 423:    */   
/* 424:    */   private void calcularPesoNeto(DetalleFacturaProveedor dfp)
/* 425:    */   {
/* 426:475 */     if (dfp.getProducto() != null)
/* 427:    */     {
/* 428:476 */       dfp.setPesoNeto(dfp.getCantidad().multiply(dfp.getProducto().getPeso()));
/* 429:477 */       dfp.setPesoNeto(FuncionesUtiles.redondearBigDecimal(dfp.getPesoNeto(), 2));
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   public List<Documento> getListaDocumentoFactura()
/* 434:    */   {
/* 435:    */     try
/* 436:    */     {
/* 437:483 */       if (this.listaDocumentoFactura == null)
/* 438:    */       {
/* 439:484 */         this.listaDocumentoFactura = new ArrayList();
/* 440:486 */         for (Documento documento : this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PEDIDO_IMPORTACION, 
/* 441:487 */           AppUtil.getOrganizacion().getId())) {
/* 442:488 */           this.listaDocumentoFactura.add(documento);
/* 443:    */         }
/* 444:    */       }
/* 445:    */     }
/* 446:    */     catch (ExcepcionAS2 e)
/* 447:    */     {
/* 448:492 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 449:    */     }
/* 450:495 */     return this.listaDocumentoFactura;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void agregarDetalleProcesoImportacion()
/* 454:    */   {
/* 455:500 */     DetalleProcesoImportacion detalleProcesoImportacion = new DetalleProcesoImportacion();
/* 456:501 */     detalleProcesoImportacion.setIdOrganizacion(this.facturaProveedor.getFacturaProveedorImportacion().getIdOrganizacion());
/* 457:502 */     detalleProcesoImportacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 458:503 */     detalleProcesoImportacion.setProcesoImportacion(new ProcesoImportacion());
/* 459:504 */     detalleProcesoImportacion.setFecha(new Date());
/* 460:505 */     detalleProcesoImportacion.setFacturaProveedorImportacion(this.facturaProveedor.getFacturaProveedorImportacion());
/* 461:506 */     this.facturaProveedor.getFacturaProveedorImportacion().getListaDetalleProcesoImportacion().add(detalleProcesoImportacion);
/* 462:    */   }
/* 463:    */   
/* 464:    */   public String getDirectorioDescarga()
/* 465:    */   {
/* 466:511 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "importacion");
/* 467:    */   }
/* 468:    */   
/* 469:    */   public String getNombreArchivo()
/* 470:    */   {
/* 471:516 */     return this.facturaProveedor.getPdf();
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void processUpload(FileUploadEvent event)
/* 475:    */   {
/* 476:    */     try
/* 477:    */     {
/* 478:529 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "importacion");
/* 479:    */       
/* 480:531 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), this.facturaProveedor.getNumero(), uploadDir);
/* 481:    */       
/* 482:    */ 
/* 483:534 */       HashMap<String, Object> campos = new HashMap();
/* 484:535 */       campos.put("pdf", fileName);
/* 485:536 */       this.servicioFacturaProveedor.actualizarAtributoEntidad(this.facturaProveedor, campos);
/* 486:    */       
/* 487:538 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 488:    */     }
/* 489:    */     catch (Exception e)
/* 490:    */     {
/* 491:541 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 492:542 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 493:    */     }
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void processDownload()
/* 497:    */   {
/* 498:    */     try
/* 499:    */     {
/* 500:550 */       String fileName = this.facturaProveedor.getPdf();
/* 501:551 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "importacion");
/* 502:553 */       if (fileName == null) {
/* 503:554 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 504:    */       } else {
/* 505:556 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 506:    */       }
/* 507:    */     }
/* 508:    */     catch (Exception e)
/* 509:    */     {
/* 510:560 */       e.printStackTrace();
/* 511:561 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 512:    */     }
/* 513:    */   }
/* 514:    */   
/* 515:    */   public String eliminarArchivo()
/* 516:    */   {
/* 517:566 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), this.facturaProveedor.getPdf());
/* 518:567 */     this.facturaProveedor.setPdf(null);
/* 519:568 */     HashMap<String, Object> campos = new HashMap();
/* 520:569 */     campos.put("pdf", null);
/* 521:570 */     this.servicioFacturaProveedor.actualizarAtributoEntidad(this.facturaProveedor, campos);
/* 522:571 */     return null;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void cargarDatosFacturaImportacion(ToggleEvent event)
/* 526:    */   {
/* 527:576 */     FacturaProveedor facturaProveedorAux = (FacturaProveedor)event.getData();
/* 528:577 */     Empresa empresa = facturaProveedorAux.getEmpresa();
/* 529:    */     
/* 530:579 */     HashMap<String, FacturaProveedor> hmFacturaProveedor = new HashMap();
/* 531:    */     try
/* 532:    */     {
/* 533:581 */       if (this.listaFacturasProveedor != null) {
/* 534:582 */         this.listaFacturasProveedor.clear();
/* 535:    */       }
/* 536:585 */       List<Object[]> listaFacturasProveedor = this.servicioFacturaProveedorImportacion.getFacturasProveedorImportacion(empresa.getId(), facturaProveedorAux
/* 537:586 */         .getId());
/* 538:588 */       for (Object[] objects : listaFacturasProveedor)
/* 539:    */       {
/* 540:590 */         StringBuilder clave = new StringBuilder();
/* 541:591 */         clave.append((String)objects[0]);
/* 542:592 */         clave.append((String)objects[14]);
/* 543:593 */         clave.append((String)objects[31]);
/* 544:594 */         clave.append((String)objects[32]);
/* 545:595 */         clave.append((String)objects[33]);
/* 546:    */         
/* 547:597 */         facturaProveedorAux = (FacturaProveedor)hmFacturaProveedor.get(clave.toString());
/* 548:598 */         if (facturaProveedorAux == null)
/* 549:    */         {
/* 550:599 */           FacturaProveedor facturaProveedor = new FacturaProveedor();
/* 551:600 */           facturaProveedor.setIdFacturaProveedor(objects[39] == null ? 0 : ((Integer)objects[39]).intValue());
/* 552:601 */           facturaProveedor.setNumero((String)objects[14]);
/* 553:602 */           facturaProveedor.setFecha((Date)objects[15]);
/* 554:603 */           facturaProveedor.setEmpresa(new Empresa());
/* 555:604 */           facturaProveedor.getEmpresa().setNombreFiscal((String)objects[17]);
/* 556:605 */           facturaProveedor.setFacturaProveedorSRI(new FacturaProveedorSRI());
/* 557:606 */           facturaProveedor.getFacturaProveedorSRI().setEstablecimiento((String)objects[31]);
/* 558:607 */           facturaProveedor.getFacturaProveedorSRI().setPuntoEmision((String)objects[32]);
/* 559:608 */           facturaProveedor.getFacturaProveedorSRI().setNumero((String)objects[33]);
/* 560:609 */           facturaProveedor.setListaDetalleFacturaProveedorImportacion(new ArrayList());
/* 561:    */           
/* 562:611 */           getListaFacturasProveedor().add(facturaProveedor);
/* 563:612 */           hmFacturaProveedor.put(clave.toString(), facturaProveedor);
/* 564:    */         }
/* 565:    */       }
/* 566:    */     }
/* 567:    */     catch (ExcepcionAS2 e)
/* 568:    */     {
/* 569:617 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 570:618 */       e.printStackTrace();
/* 571:    */     }
/* 572:    */   }
/* 573:    */   
/* 574:    */   public List<SelectItem> getListaMedioTransporte()
/* 575:    */   {
/* 576:630 */     if (this.listaMedioTransporte == null)
/* 577:    */     {
/* 578:631 */       this.listaMedioTransporte = new ArrayList();
/* 579:632 */       for (MedioTransporteEnum medioTransporteEnum : MedioTransporteEnum.values()) {
/* 580:633 */         this.listaMedioTransporte.add(new SelectItem(medioTransporteEnum, medioTransporteEnum.getNombre()));
/* 581:    */       }
/* 582:    */     }
/* 583:637 */     return this.listaMedioTransporte;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public List<SelectItem> getListaEstado()
/* 587:    */   {
/* 588:646 */     if (this.listaEstado == null)
/* 589:    */     {
/* 590:647 */       this.listaEstado = new ArrayList();
/* 591:648 */       for (Estado estado : Estado.values()) {
/* 592:649 */         this.listaEstado.add(new SelectItem(estado, estado.getNombre()));
/* 593:    */       }
/* 594:    */     }
/* 595:652 */     return this.listaEstado;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public List<Moneda> getListaMoneda()
/* 599:    */   {
/* 600:661 */     if (this.listaMoneda == null) {
/* 601:662 */       this.listaMoneda = this.servicioMoneda.obtenerListaCombo("nombre", true, null);
/* 602:    */     }
/* 603:664 */     return this.listaMoneda;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public List<PartidaArancelaria> getListaPartidaArancelaria()
/* 607:    */   {
/* 608:673 */     if (this.listaPartidaArancelaria == null) {
/* 609:674 */       this.listaPartidaArancelaria = this.servicioPartidaArancelaria.obtenerListaCombo("nombre", true, null);
/* 610:    */     }
/* 611:676 */     return this.listaPartidaArancelaria;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public CuentaContable getCuentaContable()
/* 615:    */   {
/* 616:683 */     return this.cuentaContable;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 620:    */   {
/* 621:691 */     this.cuentaContable = cuentaContable;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public String getNumero()
/* 625:    */   {
/* 626:700 */     return this.numero;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public void setNumero(String numero)
/* 630:    */   {
/* 631:710 */     this.numero = numero;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public List<DetalleProcesoImportacion> getListaDetalleProcesoImportacion()
/* 635:    */   {
/* 636:719 */     List<DetalleProcesoImportacion> detalle = new ArrayList();
/* 637:720 */     for (DetalleProcesoImportacion detalleProcesoImportacion : this.facturaProveedor.getFacturaProveedorImportacion()
/* 638:721 */       .getListaDetalleProcesoImportacion()) {
/* 639:723 */       if (!detalleProcesoImportacion.isEliminado()) {
/* 640:724 */         detalle.add(detalleProcesoImportacion);
/* 641:    */       }
/* 642:    */     }
/* 643:727 */     return detalle;
/* 644:    */   }
/* 645:    */   
/* 646:    */   public DataTable getDtDetalleProcesoImportacion()
/* 647:    */   {
/* 648:736 */     return this.dtDetalleProcesoImportacion;
/* 649:    */   }
/* 650:    */   
/* 651:    */   public void setDtDetalleProcesoImportacion(DataTable dtDetalleProcesoImportacion)
/* 652:    */   {
/* 653:746 */     this.dtDetalleProcesoImportacion = dtDetalleProcesoImportacion;
/* 654:    */   }
/* 655:    */   
/* 656:    */   public List<ProcesoImportacion> getListaProcesoImportacion()
/* 657:    */   {
/* 658:756 */     if (this.listaProcesoImportacion == null) {
/* 659:757 */       this.listaProcesoImportacion = this.servicioProcesoImportacion.obtenerListaCombo("nombre", true, null);
/* 660:    */     }
/* 661:759 */     return this.listaProcesoImportacion;
/* 662:    */   }
/* 663:    */   
/* 664:    */   public Boolean getIndicadorRenderPanelImportacion()
/* 665:    */   {
/* 666:768 */     return this.indicadorRenderPanelImportacion;
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void setIndicadorRenderPanelImportacion(Boolean indicadorRenderPanelImportacion)
/* 670:    */   {
/* 671:778 */     this.indicadorRenderPanelImportacion = indicadorRenderPanelImportacion;
/* 672:    */   }
/* 673:    */   
/* 674:    */   public List<Pais> getListaPais()
/* 675:    */   {
/* 676:789 */     if (this.listaPais == null)
/* 677:    */     {
/* 678:790 */       Map<String, String> filters = new HashMap();
/* 679:791 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", true, filters);
/* 680:    */     }
/* 681:793 */     return this.listaPais;
/* 682:    */   }
/* 683:    */   
/* 684:    */   public DataTable getDtListaReporte()
/* 685:    */   {
/* 686:797 */     return this.dtListaReporte;
/* 687:    */   }
/* 688:    */   
/* 689:    */   public void setDtListaReporte(DataTable dtListaReporte)
/* 690:    */   {
/* 691:801 */     this.dtListaReporte = dtListaReporte;
/* 692:    */   }
/* 693:    */   
/* 694:    */   public List<FacturaProveedor> getListaFacturasProveedor()
/* 695:    */   {
/* 696:805 */     if (this.listaFacturasProveedor == null) {
/* 697:806 */       this.listaFacturasProveedor = new ArrayList();
/* 698:    */     }
/* 699:808 */     return this.listaFacturasProveedor;
/* 700:    */   }
/* 701:    */   
/* 702:    */   public void setListaFacturasProveedor(List<FacturaProveedor> listaFacturasProveedor)
/* 703:    */   {
/* 704:812 */     this.listaFacturasProveedor = listaFacturasProveedor;
/* 705:    */   }
/* 706:    */   
/* 707:    */   public List<DetalleFacturaProveedorImportacionGasto> getListaDetalleFacturaProveedorImportacionGasto()
/* 708:    */   {
/* 709:817 */     List<DetalleFacturaProveedorImportacionGasto> lista = new ArrayList();
/* 710:818 */     for (DetalleFacturaProveedorImportacionGasto dfpig : this.facturaProveedor.getFacturaProveedorImportacion().getListaDetalleFacturaProveedorImportacionGasto()) {
/* 711:819 */       if (!dfpig.isEliminado()) {
/* 712:820 */         lista.add(dfpig);
/* 713:    */       }
/* 714:    */     }
/* 715:823 */     return lista;
/* 716:    */   }
/* 717:    */   
/* 718:    */   public DataTable getDtDetalleFacturaProveedorImportacionGasto()
/* 719:    */   {
/* 720:828 */     return this.dtDetalleFacturaProveedorImportacionGasto;
/* 721:    */   }
/* 722:    */   
/* 723:    */   public void setDtDetalleFacturaProveedorImportacionGasto(DataTable dtDetalleFacturaProveedorImportacionGasto)
/* 724:    */   {
/* 725:832 */     this.dtDetalleFacturaProveedorImportacionGasto = dtDetalleFacturaProveedorImportacionGasto;
/* 726:    */   }
/* 727:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.procesos.FacturaProveedorImportacionBean
 * JD-Core Version:    0.7.0.1
 */