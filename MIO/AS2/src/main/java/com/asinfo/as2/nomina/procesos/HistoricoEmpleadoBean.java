/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Quincena;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.TipoContrato;
/*  12:    */ import com.asinfo.as2.entities.TipoSubsidio;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.nomina.configuracion.EmpleadoBean;
/*  15:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoContrato;
/*  17:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoSubsidio;
/*  18:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  19:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.util.RutaArchivo;
/*  22:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  23:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  24:    */ import java.math.BigDecimal;
/*  25:    */ import java.text.SimpleDateFormat;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.annotation.PostConstruct;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.faces.bean.ManagedBean;
/*  34:    */ import javax.faces.bean.ManagedProperty;
/*  35:    */ import javax.faces.bean.ViewScoped;
/*  36:    */ import javax.faces.context.ExternalContext;
/*  37:    */ import javax.faces.context.FacesContext;
/*  38:    */ import javax.servlet.http.HttpSession;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.component.datatable.DataTable;
/*  41:    */ import org.primefaces.event.FileUploadEvent;
/*  42:    */ import org.primefaces.event.SelectEvent;
/*  43:    */ import org.primefaces.model.LazyDataModel;
/*  44:    */ import org.primefaces.model.SortOrder;
/*  45:    */ 
/*  46:    */ @ManagedBean
/*  47:    */ @ViewScoped
/*  48:    */ public class HistoricoEmpleadoBean
/*  49:    */   extends PageControllerAS2
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = -7587040629463210781L;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioTipoContrato servicioTipoContrato;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioTipoSubsidio servicioTipoSubsidio;
/*  58:    */   @EJB
/*  59:    */   private ServicioQuincena servicioQuincena;
/*  60:    */   private HistoricoEmpleado historicoEmpleado;
/*  61:    */   private Empleado empleado;
/*  62: 86 */   private Date fechaSalida = new Date();
/*  63:    */   @ManagedProperty("#{empleadoBean}")
/*  64:    */   private EmpleadoBean empleadoBean;
/*  65:    */   private LazyDataModel<HistoricoEmpleado> listaHistoricoEmpleado;
/*  66:    */   private List<TipoContrato> listaTipoContrato;
/*  67:    */   private DataTable dtHistoricoEmpleado;
/*  68:    */   private DataTable dtDetalleHistoricoEmpleado;
/*  69:102 */   private boolean indicadorSinQuincena = false;
/*  70:    */   
/*  71:    */   @PostConstruct
/*  72:    */   public void init()
/*  73:    */   {
/*  74:111 */     Map<String, String> filtersQuincena = new HashMap();
/*  75:112 */     filtersQuincena.put("indicadorFiniquito", "true");
/*  76:113 */     filtersQuincena.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  77:    */     
/*  78:115 */     List<Quincena> quincenas = this.servicioQuincena.obtenerListaCombo("", true, filtersQuincena);
/*  79:116 */     if (quincenas.size() == 0)
/*  80:    */     {
/*  81:117 */       this.indicadorSinQuincena = true;
/*  82:118 */       addInfoMessage(getLanguageController().getMensaje("msg_error_quincena_finiquito"));
/*  83:    */     }
/*  84:122 */     this.listaHistoricoEmpleado = new LazyDataModel()
/*  85:    */     {
/*  86:    */       private static final long serialVersionUID = 1L;
/*  87:    */       
/*  88:    */       public List<HistoricoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  89:    */       {
/*  90:129 */         List<HistoricoEmpleado> lista = new ArrayList();
/*  91:130 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  92:131 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  93:132 */         lista = HistoricoEmpleadoBean.this.servicioHistoricoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  94:133 */         HistoricoEmpleadoBean.this.listaHistoricoEmpleado.setRowCount(HistoricoEmpleadoBean.this.servicioHistoricoEmpleado.contarPorCriterio(filters));
/*  95:134 */         return lista;
/*  96:    */       }
/*  97:    */     };
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void crearHistoricoEmpleado()
/* 101:    */   {
/* 102:147 */     this.historicoEmpleado = new HistoricoEmpleado();
/* 103:148 */     this.historicoEmpleado.setEmpleado(new Empleado());
/* 104:149 */     this.historicoEmpleado.getEmpleado().setTipoContrato(new TipoContrato());
/* 105:150 */     this.historicoEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 106:151 */     this.historicoEmpleado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 107:152 */     agregarNuevo();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String editar()
/* 111:    */   {
/* 112:162 */     if ((getHistoricoEmpleado() != null) && (getHistoricoEmpleado().getId() != 0))
/* 113:    */     {
/* 114:164 */       HistoricoEmpleado historicoEmpleado = this.servicioHistoricoEmpleado.buscarPorId(getHistoricoEmpleado().getId());
/* 115:166 */       if (historicoEmpleado.getFechaSalida() == null)
/* 116:    */       {
/* 117:167 */         setHistoricoEmpleado(this.servicioHistoricoEmpleado.cargarDetalle(getHistoricoEmpleado().getId()));
/* 118:168 */         setEditado(true);
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:170 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 123:    */       }
/* 124:    */     }
/* 125:    */     else
/* 126:    */     {
/* 127:174 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 128:    */     }
/* 129:176 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String guardar()
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:186 */       this.servicioHistoricoEmpleado.guardar(this.historicoEmpleado);
/* 137:    */       
/* 138:188 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 139:189 */       setEditado(false);
/* 140:190 */       limpiar();
/* 141:    */     }
/* 142:    */     catch (ExcepcionAS2Nomina e)
/* 143:    */     {
/* 144:192 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 145:193 */       e.printStackTrace();
/* 146:    */     }
/* 147:    */     catch (ExcepcionAS2 e)
/* 148:    */     {
/* 149:195 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 150:196 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 151:    */     }
/* 152:    */     catch (Exception e)
/* 153:    */     {
/* 154:198 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 155:199 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 156:    */     }
/* 157:201 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String eliminar()
/* 161:    */   {
/* 162:210 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 163:211 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String cargarDatos()
/* 167:    */   {
/* 168:220 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String limpiar()
/* 172:    */   {
/* 173:229 */     crearHistoricoEmpleado();
/* 174:230 */     return "";
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String seleccionar()
/* 178:    */   {
/* 179:234 */     this.historicoEmpleado = ((HistoricoEmpleado)this.dtHistoricoEmpleado.getRowData());
/* 180:235 */     return "";
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void processUpload(FileUploadEvent event)
/* 184:    */   {
/* 185:    */     try
/* 186:    */     {
/* 187:242 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "contrato_empleado");
/* 188:243 */       String fileName = FuncionesUtiles.uploadArchivo(event, "Contrato", this.historicoEmpleado.getEmpleado().getApellidos() + " " + this.historicoEmpleado
/* 189:244 */         .getEmpleado().getNombres(), uploadDir);
/* 190:245 */       HashMap<String, Object> campos = new HashMap();
/* 191:246 */       campos.put("contrato", fileName);
/* 192:247 */       this.servicioHistoricoEmpleado.actualizarAtributoEntidad(this.historicoEmpleado, campos);
/* 193:    */       
/* 194:249 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 195:    */     }
/* 196:    */     catch (Exception e)
/* 197:    */     {
/* 198:252 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 199:253 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void processDownload()
/* 204:    */   {
/* 205:    */     try
/* 206:    */     {
/* 207:260 */       HistoricoEmpleado historicoEmpleado = (HistoricoEmpleado)this.dtHistoricoEmpleado.getRowData();
/* 208:261 */       String fileName = historicoEmpleado.getContrato();
/* 209:262 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "contrato_empleado");
/* 210:264 */       if (fileName == null) {
/* 211:265 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 212:    */       } else {
/* 213:267 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 214:    */       }
/* 215:    */     }
/* 216:    */     catch (Exception e)
/* 217:    */     {
/* 218:270 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 219:271 */       e.printStackTrace();
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   private void agregarNuevo()
/* 224:    */   {
/* 225:283 */     DetalleHistoricoEmpleado dhe = new DetalleHistoricoEmpleado();
/* 226:284 */     dhe.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 227:285 */     dhe.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 228:286 */     dhe.setHistoricoEmpleado(this.historicoEmpleado);
/* 229:287 */     dhe.setTipoContrato(new TipoContrato());
/* 230:288 */     dhe.setHorasSemana(ParametrosSistema.getHorasSemanaLaboral(AppUtil.getOrganizacion().getId()).intValue());
/* 231:289 */     porcentajeHorasSemana(dhe);
/* 232:290 */     this.historicoEmpleado.getListaDetalleHistoricoEmpleado().add(dhe);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void agregarDetalle()
/* 236:    */   {
/* 237:295 */     DetalleHistoricoEmpleado dhe = new DetalleHistoricoEmpleado();
/* 238:296 */     dhe.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 239:297 */     dhe.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 240:298 */     dhe.setHistoricoEmpleado(this.historicoEmpleado);
/* 241:299 */     dhe.setTipoContrato(new TipoContrato());
/* 242:300 */     dhe.setHorasSemana(ParametrosSistema.getHorasSemanaLaboral(AppUtil.getOrganizacion().getId()).intValue());
/* 243:    */     
/* 244:302 */     Date fechaInicio = this.servicioHistoricoEmpleado.getMaximaFechaDetalleHistoricoEmpleado(this.historicoEmpleado.getId());
/* 245:304 */     if ((this.historicoEmpleado.getListaDetalleHistoricoEmpleado().isEmpty()) && (fechaInicio == null))
/* 246:    */     {
/* 247:306 */       dhe.setFechaInicio(this.historicoEmpleado.getFechaIngreso());
/* 248:    */     }
/* 249:308 */     else if (!this.historicoEmpleado.getListaDetalleHistoricoEmpleado().isEmpty())
/* 250:    */     {
/* 251:310 */       Date fechaMaxima = null;
/* 252:311 */       for (DetalleHistoricoEmpleado auxDetalleHistoricoEmpleado : this.historicoEmpleado.getListaDetalleHistoricoEmpleado())
/* 253:    */       {
/* 254:313 */         if (auxDetalleHistoricoEmpleado.getFechaFin() == null)
/* 255:    */         {
/* 256:314 */           addErrorMessage(getLanguageController().getMensaje("msg_error_detalle_historico"));
/* 257:315 */           return;
/* 258:    */         }
/* 259:317 */         fechaMaxima = fechaMaxima == null ? auxDetalleHistoricoEmpleado.getFechaFin() : fechaMaxima;
/* 260:318 */         if (FuncionesUtiles.setAtributoFecha(fechaMaxima).before(FuncionesUtiles.setAtributoFecha(auxDetalleHistoricoEmpleado.getFechaFin()))) {
/* 261:319 */           fechaMaxima = auxDetalleHistoricoEmpleado.getFechaFin();
/* 262:    */         }
/* 263:321 */         fechaInicio = fechaMaxima;
/* 264:322 */         if ((fechaInicio != null) && 
/* 265:323 */           (FuncionesUtiles.setAtributoFecha(fechaMaxima).before(FuncionesUtiles.setAtributoFecha(fechaInicio)))) {
/* 266:324 */           fechaInicio = fechaMaxima;
/* 267:    */         }
/* 268:    */       }
/* 269:328 */       fechaInicio = FuncionesUtiles.sumarFechaDiasMeses(fechaInicio, 1);
/* 270:329 */       dhe.setFechaInicio(fechaInicio);
/* 271:    */     }
/* 272:331 */     porcentajeHorasSemana(dhe);
/* 273:332 */     this.historicoEmpleado.getListaDetalleHistoricoEmpleado().add(dhe);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String setFechaInicio()
/* 277:    */   {
/* 278:338 */     for (DetalleHistoricoEmpleado detalleHistoricoEmpleado : this.historicoEmpleado.getListaDetalleHistoricoEmpleado()) {
/* 279:339 */       if (detalleHistoricoEmpleado.getFechaFin() == null) {
/* 280:340 */         detalleHistoricoEmpleado.setFechaInicio(this.historicoEmpleado.getFechaIngreso());
/* 281:    */       }
/* 282:    */     }
/* 283:344 */     return "";
/* 284:    */   }
/* 285:    */   
/* 286:    */   public String setFechaInicioEven(SelectEvent event)
/* 287:    */   {
/* 288:349 */     for (DetalleHistoricoEmpleado detalleHistoricoEmpleado : this.historicoEmpleado.getListaDetalleHistoricoEmpleado()) {
/* 289:350 */       if (!detalleHistoricoEmpleado.isEliminado())
/* 290:    */       {
/* 291:351 */         detalleHistoricoEmpleado.setFechaInicio((Date)event.getObject());
/* 292:352 */         break;
/* 293:    */       }
/* 294:    */     }
/* 295:356 */     return "";
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String eliminarDetalle()
/* 299:    */   {
/* 300:360 */     DetalleHistoricoEmpleado detalleHistoricoEmpleado = (DetalleHistoricoEmpleado)this.dtDetalleHistoricoEmpleado.getRowData();
/* 301:361 */     detalleHistoricoEmpleado.setEliminado(true);
/* 302:362 */     return "";
/* 303:    */   }
/* 304:    */   
/* 305:    */   public String cargarEmpleado()
/* 306:    */   {
/* 307:371 */     validacionEmpleado();
/* 308:372 */     return "";
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void porcentajeHorasSemana()
/* 312:    */   {
/* 313:377 */     DetalleHistoricoEmpleado dhe = (DetalleHistoricoEmpleado)this.dtDetalleHistoricoEmpleado.getRowData();
/* 314:378 */     porcentajeHorasSemana(dhe);
/* 315:    */   }
/* 316:    */   
/* 317:    */   private void porcentajeHorasSemana(DetalleHistoricoEmpleado detalle)
/* 318:    */   {
/* 319:384 */     DetalleHistoricoEmpleado dhe = detalle;
/* 320:385 */     int horasSemanaLaboral = ParametrosSistema.getHorasSemanaLaboral(AppUtil.getOrganizacion().getId()).intValue();
/* 321:386 */     BigDecimal porcentaje = new BigDecimal(dhe.getHorasSemana()).multiply(new BigDecimal(100.0D)).divide(FuncionesUtiles.redondearBigDecimal(new BigDecimal(horasSemanaLaboral), 2));
/* 322:    */     
/* 323:388 */     dhe.setPorcentajeCapacidadSemanal(FuncionesUtiles.redondearBigDecimal(porcentaje, 2));
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void validacionEmpleado()
/* 327:    */   {
/* 328:394 */     List<HistoricoEmpleado> lista = this.servicioHistoricoEmpleado.verificacion(this.empleado.getIdOrganizacion(), this.empleado);
/* 329:395 */     if ((!lista.isEmpty()) && (((HistoricoEmpleado)lista.get(0)).getFechaSalida() == null)) {
/* 330:396 */       addInfoMessage(getLanguageController().getMensaje("msg_seleccionar_otro_empleado"));
/* 331:    */     } else {
/* 332:398 */       this.historicoEmpleado.setEmpleado(this.empleado);
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 337:    */   {
/* 338:412 */     return this.historicoEmpleado;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 342:    */   {
/* 343:422 */     this.historicoEmpleado = historicoEmpleado;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public LazyDataModel<HistoricoEmpleado> getListaHistoricoEmpleado()
/* 347:    */   {
/* 348:431 */     return this.listaHistoricoEmpleado;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setListaHistoricoEmpleado(LazyDataModel<HistoricoEmpleado> listaHistoricoEmpleado)
/* 352:    */   {
/* 353:441 */     this.listaHistoricoEmpleado = listaHistoricoEmpleado;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public DataTable getDtHistoricoEmpleado()
/* 357:    */   {
/* 358:450 */     return this.dtHistoricoEmpleado;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setDtHistoricoEmpleado(DataTable dtHistoricoEmpleado)
/* 362:    */   {
/* 363:460 */     this.dtHistoricoEmpleado = dtHistoricoEmpleado;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public Empleado getEmpleado()
/* 367:    */   {
/* 368:469 */     return this.empleado;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setEmpleado(Empleado empleado)
/* 372:    */   {
/* 373:479 */     this.empleado = empleado;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public EmpleadoBean getEmpleadoBean()
/* 377:    */   {
/* 378:488 */     return this.empleadoBean;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setEmpleadoBean(EmpleadoBean empleadoBean)
/* 382:    */   {
/* 383:498 */     this.empleadoBean = empleadoBean;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public List<TipoSubsidio> getListaTipoSubsidio()
/* 387:    */   {
/* 388:507 */     return this.servicioTipoSubsidio.obtenerListaCombo("nombre", true, null);
/* 389:    */   }
/* 390:    */   
/* 391:    */   public List<TipoContrato> getListaTipoContrato()
/* 392:    */   {
/* 393:516 */     if (this.listaTipoContrato == null) {
/* 394:517 */       this.listaTipoContrato = this.servicioTipoContrato.obtenerListaCombo("nombre", true, null);
/* 395:    */     }
/* 396:519 */     return this.listaTipoContrato;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public DataTable getDtDetalleHistoricoEmpleado()
/* 400:    */   {
/* 401:528 */     return this.dtDetalleHistoricoEmpleado;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setDtDetalleHistoricoEmpleado(DataTable dtDetalleHistoricoEmpleado)
/* 405:    */   {
/* 406:538 */     this.dtDetalleHistoricoEmpleado = dtDetalleHistoricoEmpleado;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public List<DetalleHistoricoEmpleado> getListaDetalleHistoricoEmpleado()
/* 410:    */   {
/* 411:547 */     List<DetalleHistoricoEmpleado> lista = new ArrayList();
/* 412:548 */     if (this.historicoEmpleado != null) {
/* 413:549 */       for (DetalleHistoricoEmpleado detalleHistoricoEmpleado : this.historicoEmpleado.getListaDetalleHistoricoEmpleado()) {
/* 414:550 */         if (!detalleHistoricoEmpleado.isEliminado()) {
/* 415:551 */           lista.add(detalleHistoricoEmpleado);
/* 416:    */         }
/* 417:    */       }
/* 418:    */     }
/* 419:555 */     return lista;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public Date getFechaSalida()
/* 423:    */   {
/* 424:562 */     return this.fechaSalida;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void setFechaSalida(Date fechaSalida)
/* 428:    */   {
/* 429:570 */     this.fechaSalida = fechaSalida;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public String irFiniquitoEmpleado()
/* 433:    */   {
/* 434:574 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/* 435:575 */     HttpSession session = (HttpSession)context.getSession(true);
/* 436:576 */     session.setAttribute("idHistoricoEmpleado", Integer.valueOf(this.historicoEmpleado.getIdHistoricoEmpleado()));
/* 437:577 */     session.setAttribute("simular", Boolean.valueOf(true));
/* 438:578 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 439:579 */     session.setAttribute("fechaSalida", sdf.format(this.fechaSalida));
/* 440:    */     
/* 441:581 */     return "/paginas/nomina/procesos/finiquitoEmpleado?faces-redirect=true";
/* 442:    */   }
/* 443:    */   
/* 444:    */   public boolean isIndicadorSinQuincena()
/* 445:    */   {
/* 446:585 */     return this.indicadorSinQuincena;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setIndicadorSinQuincena(boolean indicadorSinQuincena)
/* 450:    */   {
/* 451:589 */     this.indicadorSinQuincena = indicadorSinQuincena;
/* 452:    */   }
/* 453:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.HistoricoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */