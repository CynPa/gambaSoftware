/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.FormaPago;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PagoEmpleado;
/*  12:    */ import com.asinfo.as2.entities.PagoRol;
/*  13:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoEmpleado;
/*  22:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.util.RutaArchivo;
/*  25:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  26:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  27:    */ import java.math.BigDecimal;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Collection;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.annotation.PostConstruct;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.faces.bean.ManagedBean;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import javax.faces.context.FacesContext;
/*  39:    */ import javax.faces.context.PartialViewContext;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.primefaces.component.datatable.DataTable;
/*  42:    */ import org.primefaces.context.RequestContext;
/*  43:    */ import org.primefaces.event.FileUploadEvent;
/*  44:    */ import org.primefaces.model.LazyDataModel;
/*  45:    */ import org.primefaces.model.SortOrder;
/*  46:    */ 
/*  47:    */ @ManagedBean
/*  48:    */ @ViewScoped
/*  49:    */ public class PagoEmpleadoBean
/*  50:    */   extends PageControllerAS2
/*  51:    */ {
/*  52:    */   private static final long serialVersionUID = 82810811459838318L;
/*  53:    */   @EJB
/*  54:    */   private ServicioPagoRol servicioPagoRol;
/*  55:    */   @EJB
/*  56:    */   private ServicioPagoEmpleado servicioPagoEmpleado;
/*  57:    */   @EJB
/*  58:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  59:    */   @EJB
/*  60:    */   private ServicioDocumento servicioDocumento;
/*  61:    */   @EJB
/*  62:    */   private ServicioSecuencia servicioSecuencia;
/*  63:    */   private PagoEmpleado pagoEmpleado;
/*  64: 82 */   private Date fechaContabilizacion = new Date();
/*  65:    */   private LazyDataModel<PagoEmpleado> listaPagoEmpleado;
/*  66:    */   private List<Documento> listaDocumento;
/*  67:    */   private List<PagoRol> listaPagoRol;
/*  68:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  69:    */   private boolean seleccionTodos;
/*  70:    */   private boolean mostrarDialogContabilizar;
/*  71:    */   private DataTable dtPagoEmpleado;
/*  72: 94 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77: 98 */     this.listaPagoEmpleado = new LazyDataModel()
/*  78:    */     {
/*  79:    */       private static final long serialVersionUID = 1L;
/*  80:    */       
/*  81:    */       public List<PagoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  82:    */       {
/*  83:105 */         List<PagoEmpleado> lista = new ArrayList();
/*  84:    */         
/*  85:107 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  86:    */         
/*  87:109 */         lista = PagoEmpleadoBean.this.servicioPagoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  88:110 */         PagoEmpleadoBean.this.listaPagoEmpleado.setRowCount(PagoEmpleadoBean.this.servicioPagoEmpleado.contarPorCriterio(filters));
/*  89:    */         
/*  90:112 */         return lista;
/*  91:    */       }
/*  92:    */     };
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String editar()
/*  96:    */   {
/*  97:124 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  98:125 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String guardar()
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:137 */       if ((this.pagoEmpleado.getListaPagoRolEmpleado() == null) || (this.pagoEmpleado.getListaPagoRolEmpleado().isEmpty()))
/* 106:    */       {
/* 107:138 */         addErrorMessage(getLanguageController().getMensaje("msg_error_debe_cargar_valores"));
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:140 */         this.servicioPagoEmpleado.guardar(this.pagoEmpleado);
/* 112:    */         
/* 113:142 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 114:    */         
/* 115:144 */         cargarDatos();
/* 116:    */       }
/* 117:    */     }
/* 118:    */     catch (ExcepcionAS2Financiero e)
/* 119:    */     {
/* 120:147 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 121:    */     }
/* 122:    */     catch (ExcepcionAS2 e)
/* 123:    */     {
/* 124:149 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 125:    */     }
/* 126:    */     catch (Exception e)
/* 127:    */     {
/* 128:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 129:152 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 130:    */     }
/* 131:154 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String eliminar()
/* 135:    */   {
/* 136:164 */     anularPago();
/* 137:165 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String limpiar()
/* 141:    */   {
/* 142:175 */     this.pagoEmpleado = null;
/* 143:176 */     crearPagoEmpleado();
/* 144:177 */     return "";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String cargarDatos()
/* 148:    */   {
/* 149:187 */     setEditado(false);
/* 150:    */     try
/* 151:    */     {
/* 152:190 */       limpiar();
/* 153:    */     }
/* 154:    */     catch (Exception e)
/* 155:    */     {
/* 156:193 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 157:194 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 158:    */     }
/* 159:196 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String crearPagoEmpleado()
/* 163:    */   {
/* 164:200 */     this.pagoEmpleado = new PagoEmpleado();
/* 165:201 */     this.pagoEmpleado.setFecha(new Date());
/* 166:202 */     this.pagoEmpleado.setEstado(Estado.ELABORADO);
/* 167:203 */     this.pagoEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 168:204 */     this.pagoEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 169:    */     
/* 170:206 */     Documento documento = null;
/* 171:207 */     if (!getListaDocumento().isEmpty())
/* 172:    */     {
/* 173:208 */       documento = (Documento)getListaDocumento().get(0);
/* 174:209 */       this.pagoEmpleado.setDocumento(documento);
/* 175:    */     }
/* 176:    */     else
/* 177:    */     {
/* 178:211 */       documento = new Documento();
/* 179:212 */       this.pagoEmpleado.setDocumento(documento);
/* 180:    */     }
/* 181:215 */     return "";
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void cargarEmpleadosPendientes()
/* 185:    */   {
/* 186:223 */     boolean requiereAprobacion = ParametrosSistema.getIndicadorAprobarNomina(AppUtil.getOrganizacion().getId()).booleanValue();
/* 187:224 */     List<PagoRolEmpleado> lista = this.servicioPagoEmpleado.cargarEmpleadosPendientes(this.pagoEmpleado.getPagoRol().getId(), requiereAprobacion);
/* 188:225 */     this.pagoEmpleado.setListaPagoRolEmpleado(lista);
/* 189:226 */     calcularValorAPagar();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void anularPago()
/* 193:    */   {
/* 194:233 */     if (this.pagoEmpleado.getId() > 0) {
/* 195:    */       try
/* 196:    */       {
/* 197:235 */         this.servicioPagoEmpleado.anular(this.pagoEmpleado);
/* 198:236 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 199:    */       }
/* 200:    */       catch (ExcepcionAS2Financiero e)
/* 201:    */       {
/* 202:239 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 203:    */       }
/* 204:    */       catch (Exception e)
/* 205:    */       {
/* 206:241 */         addInfoMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 207:    */       }
/* 208:    */     } else {
/* 209:244 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void calcularValorAPagar()
/* 214:    */   {
/* 215:253 */     BigDecimal valorTmp = BigDecimal.ZERO;
/* 216:254 */     for (PagoRolEmpleado pagoRolEmpleado : this.pagoEmpleado.getListaPagoRolEmpleado()) {
/* 217:255 */       if (pagoRolEmpleado.isTraSeleccionado()) {
/* 218:256 */         valorTmp = valorTmp.add(pagoRolEmpleado.getValorAPagar());
/* 219:    */       }
/* 220:    */     }
/* 221:259 */     this.pagoEmpleado.setValor(valorTmp);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void contabilizar()
/* 225:    */   {
/* 226:    */     try
/* 227:    */     {
/* 228:265 */       this.servicioPagoEmpleado.contabilizar(this.pagoEmpleado, this.fechaContabilizacion);
/* 229:    */       
/* 230:267 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 231:    */     }
/* 232:    */     catch (ExcepcionAS2Financiero e)
/* 233:    */     {
/* 234:269 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 235:270 */       LOG.info("ERROR AL CONTABILIZAR", e);
/* 236:271 */       e.printStackTrace();
/* 237:    */     }
/* 238:    */     catch (ExcepcionAS2 e)
/* 239:    */     {
/* 240:273 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 241:274 */       LOG.info("ERROR AL CONTABILIZAR", e);
/* 242:275 */       e.printStackTrace();
/* 243:    */     }
/* 244:    */     catch (AS2Exception e)
/* 245:    */     {
/* 246:277 */       e.printStackTrace();
/* 247:278 */       this.exContabilizacion = e;
/* 248:279 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 249:280 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 250:    */     }
/* 251:    */     catch (Exception e)
/* 252:    */     {
/* 253:282 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 254:283 */       LOG.info("ERROR AL CONTABILIZAR", e);
/* 255:284 */       e.printStackTrace();
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void actualizarCuentaBancariaOrganizacion()
/* 260:    */   {
/* 261:289 */     this.pagoEmpleado.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion
/* 262:290 */       .cargarDetalle(this.pagoEmpleado.getCuentaBancariaOrganizacion().getId()));
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void seleccionarTodos()
/* 266:    */   {
/* 267:294 */     seleccionarATodos();
/* 268:295 */     calcularValorAPagar();
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void seleccionarATodos()
/* 272:    */   {
/* 273:299 */     for (PagoRolEmpleado pre : this.pagoEmpleado.getListaPagoRolEmpleado()) {
/* 274:300 */       pre.setTraSeleccionado(this.seleccionTodos);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String cargarSecuencia()
/* 279:    */   {
/* 280:308 */     this.pagoEmpleado = this.servicioPagoEmpleado.cargarDetalle(this.pagoEmpleado.getId());
/* 281:309 */     String numero = "";
/* 282:310 */     int cont = 0;
/* 283:311 */     for (PagoRolEmpleado pagoRolEmpleado : this.pagoEmpleado.getListaPagoRolEmpleado())
/* 284:    */     {
/* 285:312 */       pagoRolEmpleado.setSecuencia(this.servicioCuentaBancariaOrganizacion
/* 286:313 */         .obtenerSecuenciaPorFormaPago(this.pagoEmpleado.getCuentaBancariaOrganizacion().getId(), this.pagoEmpleado.getFormaPago().getId()));
/* 287:314 */       numero = "";
/* 288:315 */       if (pagoRolEmpleado.getSecuencia() != null) {
/* 289:    */         try
/* 290:    */         {
/* 291:317 */           numero = this.servicioSecuencia.obtenerSecuencia(pagoRolEmpleado.getSecuencia(), this.pagoEmpleado.getFecha());
/* 292:318 */           numero = String.valueOf(Integer.parseInt(numero) + cont);
/* 293:319 */           pagoRolEmpleado.setDocumentoReferencia(numero);
/* 294:    */         }
/* 295:    */         catch (ExcepcionAS2 e)
/* 296:    */         {
/* 297:321 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 298:322 */           e.printStackTrace();
/* 299:    */         }
/* 300:    */       }
/* 301:325 */       pagoRolEmpleado.setDocumentoReferencia(numero);
/* 302:326 */       cont++;
/* 303:    */     }
/* 304:329 */     return "";
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void processDownload()
/* 308:    */   {
/* 309:    */     try
/* 310:    */     {
/* 311:339 */       PagoEmpleado fp = (PagoEmpleado)this.dtPagoEmpleado.getRowData();
/* 312:340 */       String fileName = fp.getPdf();
/* 313:341 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_empleado");
/* 314:343 */       if (fileName == null) {
/* 315:344 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 316:    */       } else {
/* 317:346 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 318:    */       }
/* 319:    */     }
/* 320:    */     catch (Exception e)
/* 321:    */     {
/* 322:350 */       e.printStackTrace();
/* 323:351 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String eliminarArchivo()
/* 328:    */   {
/* 329:356 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getPagoEmpleado().getPdf());
/* 330:357 */     getPagoEmpleado().setPdf(null);
/* 331:358 */     HashMap<String, Object> campos = new HashMap();
/* 332:359 */     campos.put("pdf", null);
/* 333:360 */     this.servicioPagoEmpleado.actualizarAtributoEntidad(getPagoEmpleado(), campos);
/* 334:361 */     return null;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void processUpload(FileUploadEvent event)
/* 338:    */   {
/* 339:    */     try
/* 340:    */     {
/* 341:374 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_empleado");
/* 342:    */       
/* 343:376 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getPagoEmpleado().getNumero(), uploadDir);
/* 344:    */       
/* 345:    */ 
/* 346:379 */       HashMap<String, Object> campos = new HashMap();
/* 347:380 */       campos.put("pdf", fileName);
/* 348:381 */       this.servicioPagoEmpleado.actualizarAtributoEntidad(getPagoEmpleado(), campos);
/* 349:    */       
/* 350:383 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 351:    */     }
/* 352:    */     catch (Exception e)
/* 353:    */     {
/* 354:386 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 355:387 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public String getDirectorioDescarga()
/* 360:    */   {
/* 361:394 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_empleado");
/* 362:    */   }
/* 363:    */   
/* 364:    */   public List<Documento> getListaDocumento()
/* 365:    */   {
/* 366:405 */     if (this.listaDocumento == null) {
/* 367:    */       try
/* 368:    */       {
/* 369:407 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PAGO_EMPLEADO);
/* 370:    */       }
/* 371:    */       catch (ExcepcionAS2 e)
/* 372:    */       {
/* 373:409 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 374:    */       }
/* 375:    */     }
/* 376:412 */     return this.listaDocumento;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public PagoEmpleado getPagoEmpleado()
/* 380:    */   {
/* 381:421 */     return this.pagoEmpleado;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setPagoEmpleado(PagoEmpleado pagoEmpleado)
/* 385:    */   {
/* 386:431 */     if (pagoEmpleado == null) {
/* 387:432 */       this.pagoEmpleado = new PagoEmpleado();
/* 388:    */     } else {
/* 389:435 */       this.pagoEmpleado = this.servicioPagoEmpleado.cargarDetalle(pagoEmpleado.getId());
/* 390:    */     }
/* 391:    */   }
/* 392:    */   
/* 393:    */   public LazyDataModel<PagoEmpleado> getListaPagoEmpleado()
/* 394:    */   {
/* 395:445 */     return this.listaPagoEmpleado;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void setListaPagoEmpleado(LazyDataModel<PagoEmpleado> listaPagoEmpleado)
/* 399:    */   {
/* 400:455 */     this.listaPagoEmpleado = listaPagoEmpleado;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public List<PagoRol> getListaPagoRol()
/* 404:    */   {
/* 405:464 */     if (this.listaPagoRol == null)
/* 406:    */     {
/* 407:465 */       Map<String, String> filters = new HashMap();
/* 408:466 */       filters.put("indicadorFiniquito", "false");
/* 409:467 */       this.listaPagoRol = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 410:    */     }
/* 411:469 */     return this.listaPagoRol;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 415:    */   {
/* 416:478 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 417:479 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("descripcion", true, null);
/* 418:    */     }
/* 419:481 */     return this.listaCuentaBancariaOrganizacion;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public boolean isMostrarDialogContabilizar()
/* 423:    */   {
/* 424:490 */     return this.mostrarDialogContabilizar;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void setMostrarDialogContabilizar(boolean mostrarDialogContabilizar)
/* 428:    */   {
/* 429:500 */     this.mostrarDialogContabilizar = mostrarDialogContabilizar;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public boolean isSeleccionTodos()
/* 433:    */   {
/* 434:507 */     return this.seleccionTodos;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public void setSeleccionTodos(boolean seleccionTodos)
/* 438:    */   {
/* 439:515 */     this.seleccionTodos = seleccionTodos;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public Date getFechaContabilizacion()
/* 443:    */   {
/* 444:519 */     return this.fechaContabilizacion;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 448:    */   {
/* 449:523 */     this.fechaContabilizacion = fechaContabilizacion;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public AS2Exception getExContabilizacion()
/* 453:    */   {
/* 454:527 */     return this.exContabilizacion;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 458:    */   {
/* 459:531 */     this.exContabilizacion = exContabilizacion;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public DataTable getDtPagoEmpleado()
/* 463:    */   {
/* 464:535 */     return this.dtPagoEmpleado;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void setDtPagoEmpleado(DataTable dtPagoEmpleado)
/* 468:    */   {
/* 469:539 */     this.dtPagoEmpleado = dtPagoEmpleado;
/* 470:    */   }
/* 471:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.PagoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */