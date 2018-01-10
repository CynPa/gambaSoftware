/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.FormaPago;
/*  12:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  18:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.util.RutaArchivo;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  25:    */ import java.math.BigDecimal;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Calendar;
/*  28:    */ import java.util.Date;
/*  29:    */ import java.util.HashMap;
/*  30:    */ import java.util.List;
/*  31:    */ import java.util.Map;
/*  32:    */ import javax.annotation.PostConstruct;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.faces.bean.ManagedBean;
/*  35:    */ import javax.faces.bean.ViewScoped;
/*  36:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ import org.primefaces.component.datatable.DataTable;
/*  39:    */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*  40:    */ import org.primefaces.event.FileUploadEvent;
/*  41:    */ import org.primefaces.event.SelectEvent;
/*  42:    */ import org.primefaces.model.LazyDataModel;
/*  43:    */ import org.primefaces.model.SortOrder;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class AnticipoClienteBean
/*  48:    */   extends PageControllerAS2
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = -7650106936737761115L;
/*  51:    */   @EJB
/*  52:    */   private ServicioAnticipoCliente servicioAnticipoCliente;
/*  53:    */   @EJB
/*  54:    */   private ServicioEmpresa servicioEmpresa;
/*  55:    */   @EJB
/*  56:    */   private ServicioDocumento servicioDocumento;
/*  57:    */   @EJB
/*  58:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  59:    */   @EJB
/*  60:    */   private ServicioLiquidacionAnticipoCliente servicioLiquidacionAnticipoCliente;
/*  61:    */   private AnticipoCliente anticipoCliente;
/*  62:    */   private LiquidacionAnticipoCliente liquidacionAnticipoCliente;
/*  63:    */   private LazyDataModel<AnticipoCliente> listaAnticipoCliente;
/*  64:    */   private LazyDataModel<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente;
/*  65:    */   private Empresa empresa;
/*  66:    */   private boolean mostrarSaldoInicial;
/*  67:    */   private List<Empresa> listaEmpresa;
/*  68: 91 */   private List<Documento> listaDocumento = new ArrayList();
/*  69:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  70:    */   private String numeroAnticipo;
/*  71:    */   private DataTable dtAnticipoCliente;
/*  72:    */   private DataTable dtLiquidacionAnticipoCliente;
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77:103 */     this.listaAnticipoCliente = new LazyDataModel()
/*  78:    */     {
/*  79:    */       private static final long serialVersionUID = 1L;
/*  80:    */       
/*  81:    */       public List<AnticipoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  82:    */       {
/*  83:116 */         if (AnticipoClienteBean.this.numeroAnticipo != null)
/*  84:    */         {
/*  85:118 */           int posicionSeparador = AnticipoClienteBean.this.numeroAnticipo.indexOf("/");
/*  86:119 */           if (posicionSeparador >= 0) {
/*  87:120 */             AnticipoClienteBean.this.numeroAnticipo = AnticipoClienteBean.this.numeroAnticipo.substring(posicionSeparador + 1, AnticipoClienteBean.this.numeroAnticipo.length());
/*  88:    */           }
/*  89:123 */           filters.put("numero", AnticipoClienteBean.this.numeroAnticipo);
/*  90:    */         }
/*  91:126 */         List<AnticipoCliente> lista = new ArrayList();
/*  92:    */         
/*  93:128 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  94:    */         
/*  95:130 */         lista = AnticipoClienteBean.this.servicioAnticipoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  96:    */         
/*  97:132 */         AnticipoClienteBean.this.listaAnticipoCliente.setRowCount(AnticipoClienteBean.this.servicioAnticipoCliente.contarPorCriterio(filters));
/*  98:    */         
/*  99:134 */         return lista;
/* 100:    */       }
/* 101:137 */     };
/* 102:138 */     this.listaLiquidacionAnticipoCliente = new LazyDataModel()
/* 103:    */     {
/* 104:    */       private static final long serialVersionUID = 1L;
/* 105:    */       
/* 106:    */       public List<LiquidacionAnticipoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 107:    */       {
/* 108:152 */         List<LiquidacionAnticipoCliente> lista = new ArrayList();
/* 109:153 */         AnticipoClienteBean.this.listaLiquidacionAnticipoCliente.setRowCount(0);
/* 110:    */         
/* 111:155 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 112:157 */         if ((AnticipoClienteBean.this.anticipoCliente != null) && (AnticipoClienteBean.this.anticipoCliente.getId() > 0))
/* 113:    */         {
/* 114:158 */           filters.put("anticipoCliente.idAnticipoCliente", String.valueOf(AnticipoClienteBean.this.anticipoCliente.getId()));
/* 115:    */           
/* 116:160 */           lista = AnticipoClienteBean.this.servicioLiquidacionAnticipoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 117:    */           
/* 118:162 */           AnticipoClienteBean.this.listaLiquidacionAnticipoCliente.setRowCount(AnticipoClienteBean.this.servicioLiquidacionAnticipoCliente.contarPorCriterio(filters));
/* 119:    */         }
/* 120:165 */         return lista;
/* 121:    */       }
/* 122:    */     };
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String editar()
/* 126:    */   {
/* 127:178 */     if (getAnticipoCliente().getId() > 0) {
/* 128:    */       try
/* 129:    */       {
/* 130:183 */         this.servicioAnticipoCliente.esEditable(this.anticipoCliente, true, false);
/* 131:184 */         this.anticipoCliente = this.servicioAnticipoCliente.cargarDetalle(this.anticipoCliente.getId());
/* 132:185 */         setEditado(true);
/* 133:    */       }
/* 134:    */       catch (ExcepcionAS2Financiero e)
/* 135:    */       {
/* 136:188 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 137:189 */         LOG.info("ERROR AL EDITAR ANTICIPO CLIENTE");
/* 138:    */       }
/* 139:    */     } else {
/* 140:193 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 141:    */     }
/* 142:196 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String guardar()
/* 146:    */   {
/* 147:    */     try
/* 148:    */     {
/* 149:208 */       this.servicioAnticipoCliente.guardar(this.anticipoCliente);
/* 150:    */       
/* 151:210 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 152:    */       
/* 153:212 */       cargarDatos();
/* 154:    */     }
/* 155:    */     catch (ExcepcionAS2Financiero e)
/* 156:    */     {
/* 157:215 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 158:    */     }
/* 159:    */     catch (ExcepcionAS2 e)
/* 160:    */     {
/* 161:217 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 162:    */     }
/* 163:    */     catch (Exception e)
/* 164:    */     {
/* 165:219 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 166:220 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 167:    */     }
/* 168:223 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String eliminar()
/* 172:    */   {
/* 173:233 */     anularAnticipoCliente();
/* 174:234 */     return "";
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String limpiar()
/* 178:    */   {
/* 179:244 */     this.anticipoCliente = null;
/* 180:245 */     crearAnticipoCliente();
/* 181:246 */     return "";
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String cargarDatos()
/* 185:    */   {
/* 186:256 */     setEditado(false);
/* 187:    */     try
/* 188:    */     {
/* 189:259 */       limpiar();
/* 190:    */     }
/* 191:    */     catch (Exception e)
/* 192:    */     {
/* 193:262 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 194:263 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 195:    */     }
/* 196:265 */     return "";
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String crearAnticipoCliente()
/* 200:    */   {
/* 201:269 */     this.anticipoCliente = new AnticipoCliente();
/* 202:270 */     this.anticipoCliente.setValor(BigDecimal.ZERO);
/* 203:271 */     this.anticipoCliente.setFecha(new Date());
/* 204:272 */     this.anticipoCliente.setEstado(Estado.ELABORADO);
/* 205:273 */     this.anticipoCliente.setSucursal(AppUtil.getSucursal());
/* 206:274 */     this.anticipoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 207:275 */     this.anticipoCliente.setCaja(AppUtil.getCaja());
/* 208:276 */     this.anticipoCliente.setNumero("");
/* 209:277 */     Documento documento = null;
/* 210:278 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 211:    */     {
/* 212:279 */       documento = (Documento)getListaDocumento().get(0);
/* 213:280 */       this.anticipoCliente.setDocumento(documento);
/* 214:281 */       actualizarDocumento();
/* 215:    */     }
/* 216:284 */     this.anticipoCliente.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/* 217:285 */     this.anticipoCliente.setFormaPago(new FormaPago());
/* 218:    */     
/* 219:287 */     return "";
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void anularAnticipoCliente()
/* 223:    */   {
/* 224:294 */     if (getAnticipoCliente().getId() > 0) {
/* 225:    */       try
/* 226:    */       {
/* 227:298 */         this.servicioAnticipoCliente.anularAnticipoCliente(this.anticipoCliente);
/* 228:    */       }
/* 229:    */       catch (ExcepcionAS2Financiero e)
/* 230:    */       {
/* 231:301 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 232:302 */         LOG.info("ERROR AL ANULAR ANTICIPO CLIENTE", e);
/* 233:    */       }
/* 234:    */       catch (ExcepcionAS2 e)
/* 235:    */       {
/* 236:305 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 237:306 */         LOG.info("ERROR AL ANULAR ANTICIPO CLIENTE", e);
/* 238:    */       }
/* 239:    */     } else {
/* 240:310 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/* 245:    */   {
/* 246:322 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/* 247:323 */     Integer idCuentaBancaria = Integer.valueOf(Integer.parseInt(selectOneMenu.getValue().toString()));
/* 248:    */     
/* 249:325 */     this.anticipoCliente.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(idCuentaBancaria.intValue()));
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void onRowSelect(SelectEvent event)
/* 253:    */   {
/* 254:334 */     this.anticipoCliente = ((AnticipoCliente)event.getObject());
/* 255:    */   }
/* 256:    */   
/* 257:    */   public AnticipoCliente getAnticipoCliente()
/* 258:    */   {
/* 259:338 */     if (this.anticipoCliente == null) {
/* 260:339 */       crearAnticipoCliente();
/* 261:    */     }
/* 262:341 */     return this.anticipoCliente;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String liquidarAnticipoCliente()
/* 266:    */   {
/* 267:346 */     if (this.anticipoCliente.getEstado() == Estado.ANULADO)
/* 268:    */     {
/* 269:347 */       addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 270:348 */       return "";
/* 271:    */     }
/* 272:351 */     AppUtil.removeAtributo("anticipo_cliente");
/* 273:352 */     AppUtil.setAtributo("anticipo_cliente", this.anticipoCliente);
/* 274:    */     
/* 275:354 */     return "liquidacionAnticipoCliente?faces-redirect=true";
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String devolverAnticipoCliente()
/* 279:    */   {
/* 280:360 */     if (this.anticipoCliente.getEstado() == Estado.ANULADO)
/* 281:    */     {
/* 282:362 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 283:363 */       return "";
/* 284:    */     }
/* 285:365 */     if (this.anticipoCliente.getCuentaBancariaOrganizacionDevolucion() != null)
/* 286:    */     {
/* 287:366 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 288:367 */       return "";
/* 289:    */     }
/* 290:369 */     AppUtil.removeAtributo("anticipo_cliente");
/* 291:370 */     AppUtil.setAtributo("anticipo_cliente", this.anticipoCliente);
/* 292:371 */     return "devolucionAnticipoCliente?faces-redirect=true";
/* 293:    */   }
/* 294:    */   
/* 295:    */   public String anularDevolverAnticipoCliente()
/* 296:    */   {
/* 297:377 */     if (this.anticipoCliente.getEstado() == Estado.ANULADO) {
/* 298:379 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 299:    */     }
/* 300:    */     try
/* 301:    */     {
/* 302:384 */       this.servicioAnticipoCliente.anularDevolverAnticipoCliente(this.anticipoCliente);
/* 303:385 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 304:386 */       return "anticipoCliente?faces-redirect=true";
/* 305:    */     }
/* 306:    */     catch (ExcepcionAS2Financiero e)
/* 307:    */     {
/* 308:389 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 309:    */     }
/* 310:    */     catch (ExcepcionAS2 e)
/* 311:    */     {
/* 312:391 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 313:    */     }
/* 314:    */     catch (Exception e)
/* 315:    */     {
/* 316:393 */       e.printStackTrace();
/* 317:    */     }
/* 318:395 */     return "";
/* 319:    */   }
/* 320:    */   
/* 321:    */   public String seleccionarLiquidacionCliente()
/* 322:    */   {
/* 323:399 */     this.liquidacionAnticipoCliente = ((LiquidacionAnticipoCliente)this.dtLiquidacionAnticipoCliente.getRowData());
/* 324:400 */     return "";
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String anularLiquidacionCliente()
/* 328:    */   {
/* 329:    */     try
/* 330:    */     {
/* 331:405 */       this.servicioLiquidacionAnticipoCliente.anular(this.liquidacionAnticipoCliente);
/* 332:    */     }
/* 333:    */     catch (ExcepcionAS2Financiero e)
/* 334:    */     {
/* 335:407 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 336:    */     }
/* 337:    */     catch (ExcepcionAS2 e)
/* 338:    */     {
/* 339:409 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 340:    */     }
/* 341:411 */     return "";
/* 342:    */   }
/* 343:    */   
/* 344:    */   public String getDirectorioDescarga()
/* 345:    */   {
/* 346:416 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "anticipo_cliente");
/* 347:    */   }
/* 348:    */   
/* 349:    */   public String getNombreArchivo()
/* 350:    */   {
/* 351:421 */     return this.anticipoCliente.getArchivo();
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void processUpload(FileUploadEvent event)
/* 355:    */   {
/* 356:    */     try
/* 357:    */     {
/* 358:434 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "anticipo_cliente");
/* 359:    */       
/* 360:436 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getAnticipoCliente().getNumero(), uploadDir);
/* 361:    */       
/* 362:    */ 
/* 363:439 */       HashMap<String, Object> campos = new HashMap();
/* 364:440 */       campos.put("archivo", fileName);
/* 365:441 */       this.servicioAnticipoCliente.actualizarAtributoEntidad(this.anticipoCliente, campos);
/* 366:    */       
/* 367:443 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 368:    */     }
/* 369:    */     catch (Exception e)
/* 370:    */     {
/* 371:446 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 372:447 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 373:    */     }
/* 374:    */   }
/* 375:    */   
/* 376:    */   public String eliminarArchivo()
/* 377:    */   {
/* 378:452 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), this.anticipoCliente.getArchivo());
/* 379:453 */     this.anticipoCliente.setArchivo(null);
/* 380:454 */     HashMap<String, Object> campos = new HashMap();
/* 381:455 */     campos.put("archivo", null);
/* 382:456 */     this.servicioAnticipoCliente.actualizarAtributoEntidad(this.anticipoCliente, campos);
/* 383:457 */     return null;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setAnticipoCliente(AnticipoCliente anticipoCliente)
/* 387:    */   {
/* 388:465 */     this.anticipoCliente = anticipoCliente;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public List<Empresa> getListaEmpresa()
/* 392:    */   {
/* 393:469 */     if (this.listaEmpresa == null) {
/* 394:470 */       this.listaEmpresa = this.servicioEmpresa.obtenerClientes();
/* 395:    */     }
/* 396:472 */     return this.listaEmpresa;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/* 400:    */   {
/* 401:477 */     this.listaEmpresa = listaEmpresa;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public List<Documento> getListaDocumento()
/* 405:    */   {
/* 406:481 */     if (this.listaDocumento.isEmpty()) {
/* 407:    */       try
/* 408:    */       {
/* 409:483 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.ANTICIPO_CLIENTE);
/* 410:    */       }
/* 411:    */       catch (ExcepcionAS2 e)
/* 412:    */       {
/* 413:485 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 414:    */       }
/* 415:    */     }
/* 416:488 */     return this.listaDocumento;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 420:    */   {
/* 421:492 */     this.listaDocumento = listaDocumento;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public DataTable getDtAnticipoCliente()
/* 425:    */   {
/* 426:496 */     return this.dtAnticipoCliente;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void setDtAnticipoCliente(DataTable dtAnticipoCliente)
/* 430:    */   {
/* 431:500 */     this.dtAnticipoCliente = dtAnticipoCliente;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public Empresa getEmpresa()
/* 435:    */   {
/* 436:504 */     return this.empresa;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setEmpresa(Empresa empresa)
/* 440:    */   {
/* 441:508 */     this.empresa = empresa;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public LazyDataModel<AnticipoCliente> getListaAnticipoCliente()
/* 445:    */   {
/* 446:512 */     return this.listaAnticipoCliente;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setListaAnticipoCliente(LazyDataModel<AnticipoCliente> listaAnticipoCliente)
/* 450:    */   {
/* 451:516 */     this.listaAnticipoCliente = listaAnticipoCliente;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 455:    */   {
/* 456:520 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 457:521 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 458:    */     }
/* 459:523 */     return this.listaCuentaBancariaOrganizacion;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 463:    */   {
/* 464:527 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public boolean isMostrarSaldoInicial()
/* 468:    */   {
/* 469:531 */     Calendar calendario = Calendar.getInstance();
/* 470:532 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 471:533 */     int day = calendario.get(5);
/* 472:534 */     int month = calendario.get(2) + 1;
/* 473:535 */     int year = calendario.get(1);
/* 474:    */     
/* 475:537 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/* 476:    */     
/* 477:539 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/* 478:    */     
/* 479:541 */     return this.mostrarSaldoInicial;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setMostrarSaldoInicial(boolean mostrarSaldoInicial)
/* 483:    */   {
/* 484:545 */     this.mostrarSaldoInicial = mostrarSaldoInicial;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 488:    */   {
/* 489:549 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/* 490:    */   }
/* 491:    */   
/* 492:    */   public String getNumeroAnticipo()
/* 493:    */   {
/* 494:558 */     return this.numeroAnticipo;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void setNumeroAnticipo(String numeroAnticipo)
/* 498:    */   {
/* 499:568 */     this.numeroAnticipo = numeroAnticipo;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public LiquidacionAnticipoCliente getLiquidacionAnticipoCliente()
/* 503:    */   {
/* 504:572 */     return this.liquidacionAnticipoCliente;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void setLiquidacionAnticipoCliente(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 508:    */   {
/* 509:576 */     this.liquidacionAnticipoCliente = liquidacionAnticipoCliente;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public DataTable getDtLiquidacionAnticipoCliente()
/* 513:    */   {
/* 514:580 */     return this.dtLiquidacionAnticipoCliente;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void setDtLiquidacionAnticipoCliente(DataTable dtLiquidacionAnticipoCliente)
/* 518:    */   {
/* 519:584 */     this.dtLiquidacionAnticipoCliente = dtLiquidacionAnticipoCliente;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public LazyDataModel<LiquidacionAnticipoCliente> getListaLiquidacionAnticipoCliente()
/* 523:    */   {
/* 524:593 */     return this.listaLiquidacionAnticipoCliente;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void setListaLiquidacionAnticipoCliente(LazyDataModel<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente)
/* 528:    */   {
/* 529:603 */     this.listaLiquidacionAnticipoCliente = listaLiquidacionAnticipoCliente;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public String actualizarDocumento()
/* 533:    */   {
/* 534:608 */     setSecuenciaEditable(!this.anticipoCliente.getDocumento().isIndicadorBloqueoSecuencia());
/* 535:    */     
/* 536:610 */     return "";
/* 537:    */   }
/* 538:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.AnticipoClienteBean
 * JD-Core Version:    0.7.0.1
 */