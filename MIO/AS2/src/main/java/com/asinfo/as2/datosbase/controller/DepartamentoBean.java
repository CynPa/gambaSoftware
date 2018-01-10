/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumentoDigitalizadoDepartamento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpleadoGenerico;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*  10:    */ import com.asinfo.as2.entities.Departamento;
/*  11:    */ import com.asinfo.as2.entities.DepartamentoRubro;
/*  12:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  13:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
/*  14:    */ import com.asinfo.as2.entities.Empleado;
/*  15:    */ import com.asinfo.as2.entities.EmpleadoGenerico;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.Rubro;
/*  18:    */ import com.asinfo.as2.entities.Sucursal;
/*  19:    */ import com.asinfo.as2.entities.nomina.asistencia.Turno;
/*  20:    */ import com.asinfo.as2.entities.nomina.asistencia.TurnoDepartamento;
/*  21:    */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*  22:    */ import com.asinfo.as2.enumeraciones.TipoCentroTrabajo;
/*  23:    */ import com.asinfo.as2.enumeraciones.TipoDepartamento;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  25:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado;
/*  26:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  27:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Arrays;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.List;
/*  34:    */ import java.util.Map;
/*  35:    */ import javax.annotation.PostConstruct;
/*  36:    */ import javax.ejb.EJB;
/*  37:    */ import javax.faces.bean.ManagedBean;
/*  38:    */ import javax.faces.bean.ViewScoped;
/*  39:    */ import javax.faces.model.SelectItem;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.primefaces.component.datatable.DataTable;
/*  42:    */ import org.primefaces.model.LazyDataModel;
/*  43:    */ import org.primefaces.model.SortOrder;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class DepartamentoBean
/*  48:    */   extends PageControllerAS2
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = 4267621156451075319L;
/*  51:    */   @EJB
/*  52:    */   private ServicioDepartamento servicioDepartamento;
/*  53:    */   @EJB
/*  54:    */   private ServicioEmpleadoGenerico servicioEmpleadoGenerico;
/*  55:    */   @EJB
/*  56:    */   private ServicioBodega servicioBodega;
/*  57:    */   @EJB
/*  58:    */   private ServicioDocumentoDigitalizadoDepartamento servicioDocumentoDigitalizadoDepartamento;
/*  59:    */   @EJB
/*  60:    */   private ServicioDocumentoDigitalizado servicioDocumentoDigitalizado;
/*  61:    */   @EJB
/*  62:    */   private ServicioGenerico<Turno> servicioGenerico;
/*  63:    */   @EJB
/*  64:    */   private ServicioRubro servicioRubro;
/*  65:    */   private List<DocumentoDigitalizado> listaDocumentosDigitalizadosNoAsignados;
/*  66:    */   private DocumentoDigitalizado[] listaDocumentosDigitalizadosSeleccionados;
/*  67:    */   private List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamento;
/*  68:    */   private List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamentoEliminados;
/*  69:    */   private DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento;
/*  70:    */   private Empleado empleado;
/*  71:    */   private boolean supervisorPrincipal;
/*  72:    */   private List<Rubro> listaRubrosNoAsignados;
/*  73:    */   private Rubro[] listaRubrosSeleccionados;
/*  74:    */   private List<TurnoDepartamento> listaTurnoDepartamento;
/*  75:    */   private Turno[] listaTurnosSeleccionados;
/*  76:    */   private List<Turno> listaTurnosNoAsignados;
/*  77:    */   private Departamento departamento;
/*  78:    */   private CentroTrabajo centroTrabajo;
/*  79:    */   private LazyDataModel<Departamento> listaDepartamento;
/*  80:    */   private List<SelectItem> listaTipoDepartamento;
/*  81:    */   private List<TipoCentroTrabajo> listaTipoCentroTrabajo;
/*  82:    */   private List<Bodega> listaBodega;
/*  83:    */   private SelectItem[] listaTipoDepartamentoItem;
/*  84:    */   private DataTable dtDepartamento;
/*  85:    */   
/*  86:    */   @PostConstruct
/*  87:    */   public void init()
/*  88:    */   {
/*  89:131 */     this.listaDepartamento = new LazyDataModel()
/*  90:    */     {
/*  91:    */       private static final long serialVersionUID = 1L;
/*  92:    */       
/*  93:    */       public List<Departamento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  94:    */       {
/*  95:138 */         List<Departamento> lista = new ArrayList();
/*  96:139 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  97:    */         
/*  98:141 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  99:142 */         lista = DepartamentoBean.this.servicioDepartamento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 100:    */         
/* 101:144 */         DepartamentoBean.this.listaDepartamento.setRowCount(DepartamentoBean.this.servicioDepartamento.contarPorCriterio(filters));
/* 102:    */         
/* 103:146 */         return lista;
/* 104:    */       }
/* 105:    */     };
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void crearDepartamento()
/* 109:    */   {
/* 110:164 */     this.departamento = new Departamento();
/* 111:165 */     this.departamento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 112:166 */     this.departamento.setIdSucursal(AppUtil.getSucursal().getId());
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String editar()
/* 116:    */   {
/* 117:175 */     if (getDepartamento().getIdDepartamento() > 0)
/* 118:    */     {
/* 119:176 */       this.departamento = this.servicioDepartamento.cargarDetalle(this.departamento.getIdDepartamento());
/* 120:177 */       setEditado(true);
/* 121:    */     }
/* 122:    */     else
/* 123:    */     {
/* 124:179 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 125:    */     }
/* 126:181 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String guardar()
/* 130:    */   {
/* 131:    */     try
/* 132:    */     {
/* 133:191 */       getListaDocumentoDigitalizadoDepartamentoEliminados();
/* 134:192 */       this.servicioDepartamento.guardar(this.departamento);
/* 135:193 */       for (DocumentoDigitalizadoDepartamento elemento : this.listaDocumentoDigitalizadoDepartamentoEliminados) {
/* 136:194 */         this.servicioDocumentoDigitalizadoDepartamento.eliminar(elemento);
/* 137:    */       }
/* 138:196 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 139:197 */       setEditado(false);
/* 140:198 */       limpiar();
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:200 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 145:201 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 146:    */     }
/* 147:203 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String eliminar()
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:213 */       this.servicioDepartamento.eliminar(this.departamento);
/* 155:214 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 156:    */     }
/* 157:    */     catch (Exception e)
/* 158:    */     {
/* 159:216 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 160:217 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 161:    */     }
/* 162:219 */     return "";
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String cargarDatos()
/* 166:    */   {
/* 167:228 */     return "";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String cargarEmpleado()
/* 171:    */   {
/* 172:232 */     if (this.supervisorPrincipal) {
/* 173:233 */       this.departamento.setSupervisor(this.empleado);
/* 174:    */     } else {
/* 175:235 */       this.departamento.setSupervisor2(this.empleado);
/* 176:    */     }
/* 177:237 */     return null;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String limpiar()
/* 181:    */   {
/* 182:246 */     crearDepartamento();
/* 183:247 */     this.listaDocumentoDigitalizadoDepartamentoEliminados = new ArrayList();
/* 184:248 */     this.empleado = null;
/* 185:249 */     this.listaTurnosNoAsignados = null;
/* 186:250 */     this.listaTurnoDepartamento = null;
/* 187:251 */     this.listaRubrosNoAsignados = null;
/* 188:    */     
/* 189:253 */     return "";
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<CentroTrabajo> getListaCentroTrabajo()
/* 193:    */   {
/* 194:257 */     List<CentroTrabajo> lista = new ArrayList();
/* 195:258 */     for (CentroTrabajo centroTrabajo : this.departamento.getListaCentroTrabajo()) {
/* 196:259 */       if (!centroTrabajo.isEliminado()) {
/* 197:260 */         lista.add(centroTrabajo);
/* 198:    */       }
/* 199:    */     }
/* 200:263 */     return lista;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void agregarCentroTrabajo()
/* 204:    */   {
/* 205:267 */     CentroTrabajo ct = new CentroTrabajo();
/* 206:268 */     ct.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 207:269 */     ct.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 208:270 */     ct.setDepartamento(this.departamento);
/* 209:271 */     this.departamento.getListaCentroTrabajo().add(ct);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void eliminarCentroTrabajo()
/* 213:    */   {
/* 214:275 */     this.centroTrabajo.setEliminado(true);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void eliminarTurno(TurnoDepartamento turnoDepartamento)
/* 218:    */   {
/* 219:279 */     turnoDepartamento.setEliminado(true);
/* 220:280 */     this.listaTurnoDepartamento = null;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<EmpleadoGenerico> autocompletarEmpleadoGenerico(String consulta)
/* 224:    */   {
/* 225:284 */     return this.servicioEmpleadoGenerico.autocompletarEmpeladoGenerico(consulta);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Departamento getDepartamento()
/* 229:    */   {
/* 230:297 */     return this.departamento;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDepartamento(Departamento departamento)
/* 234:    */   {
/* 235:307 */     this.departamento = departamento;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public LazyDataModel<Departamento> getListaDepartamento()
/* 239:    */   {
/* 240:316 */     return this.listaDepartamento;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaDepartamento(LazyDataModel<Departamento> listaDepartamento)
/* 244:    */   {
/* 245:326 */     this.listaDepartamento = listaDepartamento;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public DataTable getDtDepartamento()
/* 249:    */   {
/* 250:335 */     return this.dtDepartamento;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setDtDepartamento(DataTable dtDepartamento)
/* 254:    */   {
/* 255:345 */     this.dtDepartamento = dtDepartamento;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<SelectItem> getListaTipoDepartamento()
/* 259:    */   {
/* 260:354 */     if (this.listaTipoDepartamento == null)
/* 261:    */     {
/* 262:355 */       this.listaTipoDepartamento = new ArrayList();
/* 263:356 */       for (TipoDepartamento tipoDepartamento : TipoDepartamento.values())
/* 264:    */       {
/* 265:357 */         SelectItem item = new SelectItem(tipoDepartamento, tipoDepartamento.getNombre());
/* 266:358 */         this.listaTipoDepartamento.add(item);
/* 267:    */       }
/* 268:    */     }
/* 269:361 */     return this.listaTipoDepartamento;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setListaTipoDepartamento(List<SelectItem> listaTipoDepartamento)
/* 273:    */   {
/* 274:371 */     this.listaTipoDepartamento = listaTipoDepartamento;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public CentroTrabajo getCentroTrabajo()
/* 278:    */   {
/* 279:380 */     return this.centroTrabajo;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setCentroTrabajo(CentroTrabajo centroTrabajo)
/* 283:    */   {
/* 284:390 */     this.centroTrabajo = centroTrabajo;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<TipoCentroTrabajo> getListaTipoCentroTrabajo()
/* 288:    */   {
/* 289:399 */     if (this.listaTipoCentroTrabajo == null)
/* 290:    */     {
/* 291:400 */       this.listaTipoCentroTrabajo = new ArrayList();
/* 292:401 */       for (TipoCentroTrabajo tct : TipoCentroTrabajo.values()) {
/* 293:402 */         this.listaTipoCentroTrabajo.add(tct);
/* 294:    */       }
/* 295:    */     }
/* 296:405 */     return this.listaTipoCentroTrabajo;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setListaTipoCentroTrabajo(List<TipoCentroTrabajo> listaTipoCentroTrabajo)
/* 300:    */   {
/* 301:415 */     this.listaTipoCentroTrabajo = listaTipoCentroTrabajo;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public List<Bodega> getListaBodega()
/* 305:    */   {
/* 306:424 */     if (this.listaBodega == null)
/* 307:    */     {
/* 308:425 */       HashMap<String, String> filters = new HashMap();
/* 309:426 */       filters.put("claseBodega", String.valueOf(ClaseBodegaEnum.TRABAJO));
/* 310:427 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, filters);
/* 311:    */     }
/* 312:429 */     return this.listaBodega;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<DocumentoDigitalizado> getListaDocumentosDigitalizadosNoAsignados()
/* 316:    */   {
/* 317:433 */     return this.listaDocumentosDigitalizadosNoAsignados;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setListaDocumentosDigitalizadosNoAsignados(List<DocumentoDigitalizado> listaDocumentosDigitalizadosNoAsignados)
/* 321:    */   {
/* 322:437 */     this.listaDocumentosDigitalizadosNoAsignados = listaDocumentosDigitalizadosNoAsignados;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public DocumentoDigitalizado[] getListaDocumentosDigitalizadosSeleccionados()
/* 326:    */   {
/* 327:441 */     return this.listaDocumentosDigitalizadosSeleccionados;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setListaDocumentosDigitalizadosSeleccionados(DocumentoDigitalizado[] listaDocumentosDigitalizadosSeleccionados)
/* 331:    */   {
/* 332:445 */     this.listaDocumentosDigitalizadosSeleccionados = listaDocumentosDigitalizadosSeleccionados;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void cargarDocumentosDigitalizadosNoAsignados()
/* 336:    */   {
/* 337:449 */     this.listaDocumentosDigitalizadosSeleccionados = null;
/* 338:    */     
/* 339:451 */     this.listaDocumentosDigitalizadosNoAsignados = this.servicioDocumentoDigitalizadoDepartamento.obtenerDocumentosDigitalizados(AppUtil.getOrganizacion().getIdOrganizacion());
/* 340:    */     
/* 341:453 */     HashMap<Integer, DocumentoDigitalizado> documentosMap = new HashMap();
/* 342:454 */     for (DocumentoDigitalizado docDigitalizado : this.listaDocumentosDigitalizadosNoAsignados) {
/* 343:455 */       if (docDigitalizado.isIndicadorEmpleado()) {
/* 344:456 */         documentosMap.put(Integer.valueOf(docDigitalizado.getIdDocumentoDigitalizado()), docDigitalizado);
/* 345:    */       }
/* 346:    */     }
/* 347:459 */     for (DocumentoDigitalizadoDepartamento docDigitalizadoDepartamento : this.departamento.getListaDocumentoDigitalizadoDepartamento()) {
/* 348:460 */       documentosMap.remove(Integer.valueOf(docDigitalizadoDepartamento.getDocumentoDigitalizado().getIdDocumentoDigitalizado()));
/* 349:    */     }
/* 350:463 */     this.listaDocumentosDigitalizadosNoAsignados = new ArrayList(documentosMap.values());
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void cargarTurnosNoAsignados()
/* 354:    */   {
/* 355:467 */     this.listaTurnosSeleccionados = null;
/* 356:468 */     Map<String, String> filters = new HashMap();
/* 357:469 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 358:470 */     this.listaTurnosNoAsignados = this.servicioGenerico.obtenerListaCombo(Turno.class, "codigo", true, filters);
/* 359:    */     
/* 360:472 */     HashMap<Integer, Turno> documentosMap = new HashMap();
/* 361:473 */     for (Turno turno : this.listaTurnosNoAsignados) {
/* 362:474 */       documentosMap.put(Integer.valueOf(turno.getId()), turno);
/* 363:    */     }
/* 364:477 */     for (TurnoDepartamento turnoDep : this.departamento.getListaTurnoDepartamento()) {
/* 365:478 */       if (!turnoDep.isEliminado()) {
/* 366:479 */         documentosMap.remove(Integer.valueOf(turnoDep.getTurno().getId()));
/* 367:    */       }
/* 368:    */     }
/* 369:483 */     this.listaTurnosNoAsignados = new ArrayList(documentosMap.values());
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void cargarRubrosNoAsignados()
/* 373:    */   {
/* 374:487 */     this.listaRubrosSeleccionados = null;
/* 375:488 */     Map<String, String> filters = new HashMap();
/* 376:489 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 377:490 */     this.listaRubrosNoAsignados = this.servicioRubro.obtenerListaCombo("nombre", true, filters);
/* 378:491 */     for (DepartamentoRubro dr : this.departamento.getListaDepartamentoRubro()) {
/* 379:492 */       if (!dr.isEliminado()) {
/* 380:493 */         this.listaRubrosNoAsignados.remove(dr.getRubro());
/* 381:    */       }
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List<DocumentoDigitalizadoDepartamento> getListaDocumentoDigitalizadoDepartamento()
/* 386:    */   {
/* 387:499 */     if (this.listaDocumentoDigitalizadoDepartamento == null) {
/* 388:500 */       this.listaDocumentoDigitalizadoDepartamento = new ArrayList();
/* 389:    */     }
/* 390:502 */     return this.listaDocumentoDigitalizadoDepartamento;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setListaDocumentoDigitalizadoDepartamento(List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamento)
/* 394:    */   {
/* 395:506 */     this.listaDocumentoDigitalizadoDepartamento = listaDocumentoDigitalizadoDepartamento;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public String agregarDocumentosDigitalizados()
/* 399:    */   {
/* 400:510 */     if (this.listaDocumentosDigitalizadosSeleccionados != null) {
/* 401:511 */       for (DocumentoDigitalizado documento : this.listaDocumentosDigitalizadosSeleccionados)
/* 402:    */       {
/* 403:512 */         DocumentoDigitalizadoDepartamento documentoDepartamento = new DocumentoDigitalizadoDepartamento();
/* 404:513 */         documentoDepartamento.setDocumentoDigitalizado(documento);
/* 405:514 */         documentoDepartamento.setDepartamento(this.departamento);
/* 406:515 */         this.departamento.getListaDocumentoDigitalizadoDepartamento().add(documentoDepartamento);
/* 407:516 */         this.listaDocumentosDigitalizadosNoAsignados.remove(documento);
/* 408:    */       }
/* 409:    */     }
/* 410:520 */     this.listaDocumentosDigitalizadosSeleccionados = null;
/* 411:    */     
/* 412:522 */     return "";
/* 413:    */   }
/* 414:    */   
/* 415:    */   public String agregarTurno()
/* 416:    */   {
/* 417:526 */     if (this.listaTurnosSeleccionados != null) {
/* 418:    */       label152:
/* 419:527 */       for (Turno turno : this.listaTurnosSeleccionados)
/* 420:    */       {
/* 421:529 */         for (TurnoDepartamento turnoDepartamento : this.departamento.getListaTurnoDepartamento()) {
/* 422:530 */           if (turnoDepartamento.getTurno().equals(turno))
/* 423:    */           {
/* 424:531 */             turnoDepartamento.setEliminado(false);
/* 425:532 */             getListaTurnoDepartamento().add(turnoDepartamento);
/* 426:    */             break label152;
/* 427:    */           }
/* 428:    */         }
/* 429:536 */         TurnoDepartamento turnoDep = new TurnoDepartamento();
/* 430:537 */         turnoDep.setDepartamento(this.departamento);
/* 431:538 */         turnoDep.setTurno(turno);
/* 432:539 */         this.departamento.getListaTurnoDepartamento().add(turnoDep);
/* 433:540 */         getListaTurnoDepartamento().add(turnoDep);
/* 434:    */       }
/* 435:    */     }
/* 436:543 */     this.listaTurnosSeleccionados = null;
/* 437:    */     
/* 438:545 */     return "";
/* 439:    */   }
/* 440:    */   
/* 441:    */   public String eliminarDocumentoDigitalizadoDepartamento()
/* 442:    */   {
/* 443:549 */     getListaDocumentoDigitalizadoDepartamentoEliminados();
/* 444:550 */     if (this.documentoDigitalizadoDepartamento.getIdDocumentoDigitalizadoDepartamento() != 0) {
/* 445:551 */       this.listaDocumentoDigitalizadoDepartamentoEliminados.add(this.documentoDigitalizadoDepartamento);
/* 446:    */     }
/* 447:553 */     for (int i = 0; i < this.departamento.getListaDocumentoDigitalizadoDepartamento().size(); i++) {
/* 448:555 */       if (((DocumentoDigitalizadoDepartamento)this.departamento.getListaDocumentoDigitalizadoDepartamento().get(i)).getDocumentoDigitalizado().getIdDocumentoDigitalizado() == this.documentoDigitalizadoDepartamento.getDocumentoDigitalizado().getIdDocumentoDigitalizado())
/* 449:    */       {
/* 450:556 */         this.departamento.getListaDocumentoDigitalizadoDepartamento().remove(i);
/* 451:557 */         break;
/* 452:    */       }
/* 453:    */     }
/* 454:560 */     return "";
/* 455:    */   }
/* 456:    */   
/* 457:    */   public DocumentoDigitalizadoDepartamento getDocumentoDigitalizadoDepartamento()
/* 458:    */   {
/* 459:564 */     return this.documentoDigitalizadoDepartamento;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setDocumentoDigitalizadoDepartamento(DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento)
/* 463:    */   {
/* 464:568 */     this.documentoDigitalizadoDepartamento = documentoDigitalizadoDepartamento;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public List<DocumentoDigitalizadoDepartamento> getListaDocumentoDigitalizadoDepartamentoEliminados()
/* 468:    */   {
/* 469:572 */     if (this.listaDocumentoDigitalizadoDepartamentoEliminados == null) {
/* 470:573 */       this.listaDocumentoDigitalizadoDepartamentoEliminados = new ArrayList();
/* 471:    */     }
/* 472:575 */     return this.listaDocumentoDigitalizadoDepartamentoEliminados;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public void setListaDocumentoDigitalizadoDepartamentoEliminados(List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamentoEliminados)
/* 476:    */   {
/* 477:580 */     this.listaDocumentoDigitalizadoDepartamentoEliminados = listaDocumentoDigitalizadoDepartamentoEliminados;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public Empleado getEmpleado()
/* 481:    */   {
/* 482:587 */     return this.empleado;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void setEmpleado(Empleado empleado)
/* 486:    */   {
/* 487:595 */     this.empleado = empleado;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public List<Turno> getListaTurnosNoAsignados()
/* 491:    */   {
/* 492:602 */     return this.listaTurnosNoAsignados;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public void setListaTurnosNoAsignados(List<Turno> listaTurnosNoAsignados)
/* 496:    */   {
/* 497:610 */     this.listaTurnosNoAsignados = listaTurnosNoAsignados;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public Turno[] getListaTurnosSeleccionados()
/* 501:    */   {
/* 502:617 */     return this.listaTurnosSeleccionados;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setListaTurnosSeleccionados(Turno[] listaTurnosSeleccionados)
/* 506:    */   {
/* 507:625 */     this.listaTurnosSeleccionados = listaTurnosSeleccionados;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public List<TurnoDepartamento> getListaTurnoDepartamento()
/* 511:    */   {
/* 512:632 */     if ((this.departamento != null) && (this.listaTurnoDepartamento == null))
/* 513:    */     {
/* 514:633 */       this.listaTurnoDepartamento = new ArrayList();
/* 515:634 */       for (TurnoDepartamento tuenoDepartamento : this.departamento.getListaTurnoDepartamento()) {
/* 516:635 */         if (!tuenoDepartamento.isEliminado()) {
/* 517:636 */           this.listaTurnoDepartamento.add(tuenoDepartamento);
/* 518:    */         }
/* 519:    */       }
/* 520:    */     }
/* 521:640 */     return this.listaTurnoDepartamento;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void setListaTurnoDepartamento(List<TurnoDepartamento> listaTurnoDepartamento)
/* 525:    */   {
/* 526:648 */     this.listaTurnoDepartamento = listaTurnoDepartamento;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public boolean isSupervisorPrincipal()
/* 530:    */   {
/* 531:652 */     return this.supervisorPrincipal;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setSupervisorPrincipal(boolean supervisorPrincipal)
/* 535:    */   {
/* 536:656 */     this.supervisorPrincipal = supervisorPrincipal;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public List<Rubro> getListaRubrosNoAsignados()
/* 540:    */   {
/* 541:663 */     return this.listaRubrosNoAsignados;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setListaRubrosNoAsignados(List<Rubro> listaRubrosNoAsignados)
/* 545:    */   {
/* 546:671 */     this.listaRubrosNoAsignados = listaRubrosNoAsignados;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public Rubro[] getListaRubrosSeleccionados()
/* 550:    */   {
/* 551:678 */     return this.listaRubrosSeleccionados;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void setListaRubrosSeleccionados(Rubro[] listaRubrosSeleccionados)
/* 555:    */   {
/* 556:686 */     this.listaRubrosSeleccionados = listaRubrosSeleccionados;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public SelectItem[] getListaTipoDepartamentoItem()
/* 560:    */   {
/* 561:690 */     if (this.listaTipoDepartamentoItem == null)
/* 562:    */     {
/* 563:691 */       List<SelectItem> lista = new ArrayList();
/* 564:692 */       lista.add(new SelectItem("", ""));
/* 565:693 */       for (TipoDepartamento t : TipoDepartamento.values())
/* 566:    */       {
/* 567:694 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 568:695 */         lista.add(item);
/* 569:    */       }
/* 570:697 */       this.listaTipoDepartamentoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 571:698 */       Arrays.sort(this.listaTipoDepartamentoItem, new SelectItemComparator());
/* 572:699 */       return this.listaTipoDepartamentoItem;
/* 573:    */     }
/* 574:701 */     return this.listaTipoDepartamentoItem;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setListaTipoDepartamentoItem(SelectItem[] listaTipoDepartamentoItem)
/* 578:    */   {
/* 579:705 */     this.listaTipoDepartamentoItem = listaTipoDepartamentoItem;
/* 580:    */   }
/* 581:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.DepartamentoBean
 * JD-Core Version:    0.7.0.1
 */