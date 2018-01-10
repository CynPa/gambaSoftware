/*   1:    */ package com.asinfo.as2.nomina.asistencia.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   5:    */ import com.asinfo.as2.entities.Departamento;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.nomina.asistencia.Asistencia;
/*  11:    */ import com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia;
/*  12:    */ import com.asinfo.as2.entities.nomina.asistencia.TipoFalta;
/*  13:    */ import com.asinfo.as2.entities.nomina.asistencia.Turno;
/*  14:    */ import com.asinfo.as2.entities.nomina.asistencia.TurnoDepartamento;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistencia;
/*  18:    */ import com.asinfo.as2.nomina.configuracion.EmpleadoBean;
/*  19:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  20:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  21:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  22:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  25:    */ import com.asinfo.as2.utils.JsfUtil;
/*  26:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  27:    */ import java.io.PrintStream;
/*  28:    */ import java.lang.reflect.Method;
/*  29:    */ import java.math.BigDecimal;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Date;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.List;
/*  34:    */ import java.util.Map;
/*  35:    */ import javax.annotation.PostConstruct;
/*  36:    */ import javax.ejb.EJB;
/*  37:    */ import javax.faces.bean.ManagedBean;
/*  38:    */ import javax.faces.bean.ViewScoped;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.component.datatable.DataTable;
/*  41:    */ import org.primefaces.model.LazyDataModel;
/*  42:    */ import org.primefaces.model.SortOrder;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class RegistroAsistenciaBean
/*  47:    */   extends EmpleadoBean
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = 1L;
/*  50:    */   public static final String NUMERO_FILAS_POR_PAGINA = "5,10,15,30,50";
/*  51:    */   @EJB
/*  52:    */   private ServicioAsistencia servicioAsistencia;
/*  53:    */   @EJB
/*  54:    */   private ServicioUsuario servicioUsuario;
/*  55:    */   @EJB
/*  56:    */   private ServicioEmpleado servicioEmpleado;
/*  57:    */   @EJB
/*  58:    */   private ServicioGenerico<Turno> servicioTurno;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioGenerico<TipoFalta> servicioTipoFalta;
/*  61:    */   private Asistencia asistencia;
/*  62:    */   private Departamento departamentoSeleccionado;
/*  63:    */   private LazyDataModel<Asistencia> listaAsistencia;
/*  64:    */   private List<Asistencia> listaAsistenciaAux;
/*  65:    */   private List<EmpleadoAsistencia> listaEmpleadoResumido;
/*  66:    */   private List<Turno> listaTurno;
/*  67:    */   private DataTable dtAsistencia;
/*  68:    */   private DataTable dtAsistenciaResumido;
/*  69:    */   private DataTable dtEmpleadoResumido;
/*  70:    */   private Date fechaDesde;
/*  71:    */   private Date fechaHasta;
/*  72:    */   private boolean indicadorEditable;
/*  73:    */   private boolean indicadorAgrupado;
/*  74:    */   private Boolean apruebaHorasExtra100Feriado;
/*  75:    */   private List<Asistencia> listaAsistenciaFiltrado;
/*  76:    */   private List<EmpleadoAsistencia> listaEmpleadoFiltrado;
/*  77:    */   private List<TipoFalta> listaTipoFalta;
/*  78:107 */   private BigDecimal horasFaltaTotal = BigDecimal.ZERO;
/*  79:108 */   private BigDecimal horasPermisoTotal = BigDecimal.ZERO;
/*  80:109 */   private BigDecimal horasSubsidioTotal = BigDecimal.ZERO;
/*  81:    */   private int diasVacacionesTotal;
/*  82:111 */   private BigDecimal horasExtra25Total = BigDecimal.ZERO;
/*  83:112 */   private BigDecimal horasExtra50Total = BigDecimal.ZERO;
/*  84:113 */   private BigDecimal horasExtra100Total = BigDecimal.ZERO;
/*  85:114 */   private BigDecimal horasExtra100FeriadoTotal = BigDecimal.ZERO;
/*  86:115 */   private BigDecimal horasExtra25AprobadasTotal = BigDecimal.ZERO;
/*  87:116 */   private BigDecimal horasExtra50AprobadasTotal = BigDecimal.ZERO;
/*  88:117 */   private BigDecimal horasExtra100AprobadasTotal = BigDecimal.ZERO;
/*  89:118 */   private BigDecimal horasExtra100FeriadoAprobadasTotal = BigDecimal.ZERO;
/*  90:    */   private Asistencia asistenciaManual;
/*  91:    */   private Empleado empleado;
/*  92:    */   private Turno turno;
/*  93:123 */   private Boolean usuarioAdministrador = null;
/*  94:    */   private boolean mostrarReingresos;
/*  95:    */   private Boolean registraReingresos;
/*  96:126 */   public static String camposActualizar = "panelEntrada,panelMEntrada,panelSalidaReceso,panelMSalidaReceso,panelEntradaReceso,panelMEntradaReceso,panelSalida,panelMSalida,panelHFalta,panelHSubsidio,panelSubsidioEmpleado,panelIVacacion,panelIDiaFestivo,panelHPermiso,panelHE25,panelHE50,panelHE100, panelHE50Aprobadas, panelHE100Aprobadas,panelDescripcion";
/*  97:    */   
/*  98:    */   @PostConstruct
/*  99:    */   public void init()
/* 100:    */   {
/* 101:131 */     this.fechaDesde = FuncionesUtiles.setAtributoFecha(new Date());
/* 102:132 */     this.fechaHasta = this.fechaDesde;
/* 103:134 */     if (((this.departamentoSeleccionado == null) || (this.departamentoSeleccionado.getId() == 0)) && (getListaDepartamento().size() > 0)) {
/* 104:135 */       this.departamentoSeleccionado = ((Departamento)getListaDepartamento().get(0));
/* 105:    */     }
/* 106:138 */     this.listaAsistencia = new LazyDataModel()
/* 107:    */     {
/* 108:    */       private static final long serialVersionUID = -1752987002238164010L;
/* 109:    */       
/* 110:    */       public List<Asistencia> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 111:    */       {
/* 112:144 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 113:145 */         filters.put("empleado.idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 114:146 */         if (RegistroAsistenciaBean.this.getDepartamentoSeleccionado() != null) {
/* 115:147 */           filters.put("empleado.departamento.idDepartamento", "" + RegistroAsistenciaBean.this.getDepartamentoSeleccionado().getId());
/* 116:    */         } else {
/* 117:150 */           filters.put("empleado.departamento.idDepartamento", "0");
/* 118:    */         }
/* 119:153 */         RegistroAsistenciaBean.this.listaAsistenciaAux = RegistroAsistenciaBean.this.servicioAsistencia.obtenerListaPorPagina(startIndex, pageSize, filters, RegistroAsistenciaBean.this.fechaDesde, RegistroAsistenciaBean.this.fechaHasta);
/* 120:154 */         RegistroAsistenciaBean.this.listaAsistencia.setRowCount(RegistroAsistenciaBean.this.servicioAsistencia.contarPorCriterio(filters, RegistroAsistenciaBean.this.fechaDesde, RegistroAsistenciaBean.this.fechaHasta));
/* 121:    */         
/* 122:156 */         return RegistroAsistenciaBean.this.listaAsistenciaAux;
/* 123:    */       }
/* 124:159 */     };
/* 125:160 */     actualizarTabla();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void generarAsistenciaListener()
/* 129:    */   {
/* 130:164 */     Date fecha = FuncionesUtiles.setAtributoFecha(new Date());
/* 131:    */     try
/* 132:    */     {
/* 133:166 */       this.servicioAsistencia.procesarAsistencia(AppUtil.getOrganizacion().getId(), fecha, getDepartamentoSeleccionado());
/* 134:    */     }
/* 135:    */     catch (AS2Exception e)
/* 136:    */     {
/* 137:169 */       e.printStackTrace();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void generarAsistenciaRangoFechasListener()
/* 142:    */   {
/* 143:175 */     Date fecha = this.fechaDesde;
/* 144:    */     try
/* 145:    */     {
/* 146:178 */       while (fecha.getTime() <= this.fechaHasta.getTime())
/* 147:    */       {
/* 148:179 */         this.servicioAsistencia.procesarAsistencia(AppUtil.getOrganizacion().getId(), fecha, getDepartamentoSeleccionado());
/* 149:180 */         fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, 1);
/* 150:    */       }
/* 151:    */     }
/* 152:    */     catch (AS2Exception e)
/* 153:    */     {
/* 154:184 */       e.printStackTrace();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void guardarAsistencia(Asistencia asistencia, int campoModificado)
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:191 */       Object[] parametrosSet = new Object[1];
/* 163:192 */       parametrosSet[0] = Boolean.valueOf(true);
/* 164:193 */       Method metodoSet = asistencia.getClass().getMethod("setIndicadorModificadoManual" + campoModificado, new Class[] { Boolean.class });
/* 165:194 */       metodoSet.invoke(asistencia, parametrosSet);
/* 166:    */     }
/* 167:    */     catch (Exception e)
/* 168:    */     {
/* 169:196 */       System.out.println(e.getMessage());
/* 170:197 */       e.printStackTrace();
/* 171:    */     }
/* 172:199 */     guardarAsistencia(asistencia);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void guardarAsistencia(Asistencia asistencia)
/* 176:    */   {
/* 177:    */     try
/* 178:    */     {
/* 179:204 */       this.servicioAsistencia.guardar(asistencia);
/* 180:    */       
/* 181:    */ 
/* 182:207 */       asistenciaBDD = this.servicioAsistencia.cargarDetalle(asistencia.getIdAsistencia());
/* 183:208 */       i = 0;
/* 184:209 */       for (Asistencia asis : this.listaAsistenciaAux)
/* 185:    */       {
/* 186:210 */         if (asis.equals(asistencia))
/* 187:    */         {
/* 188:211 */           this.listaAsistenciaAux.set(i, asistenciaBDD);
/* 189:212 */           break;
/* 190:    */         }
/* 191:214 */         i++;
/* 192:    */       }
/* 193:    */     }
/* 194:    */     catch (AS2Exception e)
/* 195:    */     {
/* 196:    */       Asistencia asistenciaBDD;
/* 197:    */       int i;
/* 198:218 */       JsfUtil.addErrorMessage(e, "");
/* 199:219 */       LOG.info("ERROR AL GUARDAR DATOS Asistencia", e);
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void actualizarListaTurno()
/* 204:    */   {
/* 205:225 */     this.listaTurno = new ArrayList();
/* 206:226 */     if (this.departamentoSeleccionado != null)
/* 207:    */     {
/* 208:227 */       Departamento departamento = this.servicioDepartamento.cargarDetalle(this.departamentoSeleccionado.getId());
/* 209:228 */       for (TurnoDepartamento turnoDepartamento : departamento.getListaTurnoDepartamento()) {
/* 210:229 */         this.listaTurno.add(turnoDepartamento.getTurno());
/* 211:    */       }
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void actualizarDiasFestivos()
/* 216:    */   {
/* 217:    */     try
/* 218:    */     {
/* 219:236 */       this.servicioAsistencia.actualizarFeriados(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, getDepartamentoSeleccionado());
/* 220:237 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 221:    */     }
/* 222:    */     catch (AS2Exception e)
/* 223:    */     {
/* 224:239 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 225:240 */       LOG.info("ERROR AL GUARDAR DATOS Asistencia", e);
/* 226:241 */       e.printStackTrace();
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void actualizarVacacion()
/* 231:    */   {
/* 232:    */     try
/* 233:    */     {
/* 234:247 */       this.servicioAsistencia.actualizarVacacion(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, getDepartamentoSeleccionado());
/* 235:248 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 236:    */     }
/* 237:    */     catch (AS2Exception e)
/* 238:    */     {
/* 239:250 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 240:251 */       LOG.info("ERROR AL GUARDAR Vacacion", e);
/* 241:252 */       e.printStackTrace();
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void actualizarPermiso()
/* 246:    */   {
/* 247:    */     try
/* 248:    */     {
/* 249:258 */       this.servicioAsistencia.actualizarHorasPermiso(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, getDepartamentoSeleccionado());
/* 250:259 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 251:    */     }
/* 252:    */     catch (AS2Exception e)
/* 253:    */     {
/* 254:261 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 255:262 */       LOG.info("ERROR AL GUARDAR Vacacion", e);
/* 256:263 */       e.printStackTrace();
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void actualizarSubsidio()
/* 261:    */   {
/* 262:    */     try
/* 263:    */     {
/* 264:268 */       this.servicioAsistencia.actualizarSubsidio(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, getDepartamentoSeleccionado());
/* 265:269 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 266:    */     }
/* 267:    */     catch (AS2Exception e)
/* 268:    */     {
/* 269:271 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 270:272 */       LOG.info("ERROR AL GUARDAR Subsidio", e);
/* 271:273 */       e.printStackTrace();
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void actualizarDatos() {}
/* 276:    */   
/* 277:    */   public Asistencia getAsistencia()
/* 278:    */   {
/* 279:282 */     return this.asistencia;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setAsistencia(Asistencia asistencia)
/* 283:    */   {
/* 284:286 */     this.asistencia = asistencia;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Departamento getDepartamentoSeleccionado()
/* 288:    */   {
/* 289:290 */     return this.departamentoSeleccionado;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setDepartamentoSeleccionado(Departamento departamentoSeleccionado)
/* 293:    */   {
/* 294:294 */     this.departamentoSeleccionado = departamentoSeleccionado;
/* 295:295 */     this.listaEmpleadoResumido = null;
/* 296:296 */     actualizarListaTurno();
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<Turno> getListaTurno()
/* 300:    */   {
/* 301:300 */     if (this.listaTurno == null) {
/* 302:301 */       actualizarListaTurno();
/* 303:    */     }
/* 304:303 */     return this.listaTurno;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public List<Departamento> getListaDepartamento()
/* 308:    */   {
/* 309:307 */     EntidadUsuario usuario = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/* 310:308 */     Map<String, String> filtros = new HashMap();
/* 311:309 */     Map<String, String> filtros2 = new HashMap();
/* 312:310 */     List<Departamento> lista = new ArrayList();
/* 313:    */     
/* 314:312 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 315:313 */     filtros2.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 316:315 */     if ((usuario.getEmpleado() != null) && (!usuario.isIndicadorAdministrador()))
/* 317:    */     {
/* 318:317 */       filtros.put("supervisor.idEmpleado", "" + usuario.getEmpleado().getId());
/* 319:318 */       filtros2.put("supervisor2.idEmpleado", "" + usuario.getEmpleado().getId());
/* 320:    */       
/* 321:320 */       lista.addAll(this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros2));
/* 322:    */     }
/* 323:323 */     else if (!usuario.isIndicadorAdministrador())
/* 324:    */     {
/* 325:324 */       return lista;
/* 326:    */     }
/* 327:327 */     lista.addAll(this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros));
/* 328:    */     
/* 329:    */ 
/* 330:330 */     return lista;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public LazyDataModel<Asistencia> getListaAsistencia()
/* 334:    */   {
/* 335:334 */     return this.listaAsistencia;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setListaAsistencia(LazyDataModel<Asistencia> listaAsistencia)
/* 339:    */   {
/* 340:338 */     this.listaAsistencia = listaAsistencia;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public DataTable getDtAsistencia()
/* 344:    */   {
/* 345:342 */     return this.dtAsistencia;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setDtAsistencia(DataTable dtAsistencia)
/* 349:    */   {
/* 350:346 */     this.dtAsistencia = dtAsistencia;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public Date getFechaDesde()
/* 354:    */   {
/* 355:350 */     return this.fechaDesde;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setFechaDesde(Date fechaDesde)
/* 359:    */   {
/* 360:354 */     this.fechaDesde = fechaDesde;
/* 361:355 */     actualizarTabla();
/* 362:    */   }
/* 363:    */   
/* 364:    */   public Date getFechaHasta()
/* 365:    */   {
/* 366:359 */     return this.fechaHasta;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setFechaHasta(Date fechaHasta)
/* 370:    */   {
/* 371:363 */     this.fechaHasta = fechaHasta;
/* 372:364 */     actualizarTabla();
/* 373:    */   }
/* 374:    */   
/* 375:    */   public boolean isIndicadorEditable()
/* 376:    */   {
/* 377:368 */     return this.indicadorEditable;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setIndicadorEditable(boolean indicadorEditable)
/* 381:    */   {
/* 382:372 */     this.indicadorEditable = indicadorEditable;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List<EmpleadoAsistencia> getListaEmpleadoResumido()
/* 386:    */   {
/* 387:376 */     if (this.listaEmpleadoResumido == null) {
/* 388:377 */       cargarListaEmpleadoResumido();
/* 389:    */     }
/* 390:379 */     return this.listaEmpleadoResumido;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setListaEmpleadoResumido(List<EmpleadoAsistencia> listaEmpleadoResumido)
/* 394:    */   {
/* 395:383 */     this.listaEmpleadoResumido = listaEmpleadoResumido;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void cargarListaEmpleadoResumido()
/* 399:    */   {
/* 400:387 */     this.listaEmpleadoResumido = new ArrayList();
/* 401:388 */     this.horasFaltaTotal = BigDecimal.ZERO;
/* 402:389 */     this.horasPermisoTotal = BigDecimal.ZERO;
/* 403:390 */     this.horasSubsidioTotal = BigDecimal.ZERO;
/* 404:391 */     this.diasVacacionesTotal = 0;
/* 405:392 */     this.horasExtra25Total = BigDecimal.ZERO;
/* 406:393 */     this.horasExtra50Total = BigDecimal.ZERO;
/* 407:394 */     this.horasExtra100Total = BigDecimal.ZERO;
/* 408:395 */     this.horasExtra100FeriadoTotal = BigDecimal.ZERO;
/* 409:396 */     this.horasExtra25AprobadasTotal = BigDecimal.ZERO;
/* 410:397 */     this.horasExtra50AprobadasTotal = BigDecimal.ZERO;
/* 411:398 */     this.horasExtra100AprobadasTotal = BigDecimal.ZERO;
/* 412:399 */     this.horasExtra100FeriadoAprobadasTotal = BigDecimal.ZERO;
/* 413:    */     
/* 414:401 */     Map<String, String> filters = new HashMap();
/* 415:402 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 416:403 */     filters.put("empleado.departamento.idDepartamento", "" + getDepartamentoSeleccionado().getId());
/* 417:404 */     filters.put("empleado.idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 418:405 */     List<Asistencia> lista = new ArrayList();
/* 419:    */     
/* 420:407 */     lista = this.servicioAsistencia.obtenerListaPorPagina(0, 100000, filters, this.fechaDesde, this.fechaHasta);
/* 421:    */     
/* 422:409 */     Map<Integer, EmpleadoAsistencia> mapaEmpleado = new HashMap();
/* 423:411 */     for (Asistencia asistencia : lista)
/* 424:    */     {
/* 425:413 */       EmpleadoAsistencia empleado = (EmpleadoAsistencia)mapaEmpleado.get(Integer.valueOf(asistencia.getEmpleado().getId()));
/* 426:414 */       if (empleado == null)
/* 427:    */       {
/* 428:415 */         empleado = new EmpleadoAsistencia();
/* 429:416 */         empleado.setIdEmpleado(asistencia.getEmpleado().getId());
/* 430:417 */         empleado.setNombres(asistencia.getEmpleado().getNombres());
/* 431:418 */         empleado.setApellidos(asistencia.getEmpleado().getApellidos());
/* 432:419 */         empleado.setIdentificacion(asistencia.getEmpleado().getEmpresa().getIdentificacion());
/* 433:420 */         empleado.setTotalHoras25(asistencia.getHorasExtras25());
/* 434:421 */         empleado.setTotalHoras25Aprobadas(asistencia.getHorasExtras25Aprobadas());
/* 435:422 */         empleado.setTotalHoras50(asistencia.getHorasExtras50());
/* 436:423 */         empleado.setTotalHoras50Aprobadas(asistencia.getHorasExtras50Aprobadas());
/* 437:424 */         empleado.setTotalHoras100(asistencia.getHorasExtras100());
/* 438:425 */         empleado.setTotalHoras100Feriado(asistencia.getHorasExtras100Feriado());
/* 439:426 */         empleado.setTotalHoras100FeriadoAprobadas(asistencia.getHorasExtras100FeriadoAprobadas());
/* 440:427 */         empleado.setTotalHoras100Aprobadas(asistencia.getHorasExtras100Aprobadas());
/* 441:428 */         empleado.setTotalDiasVacaciones(asistencia.isIndicadorVacacion() ? 1 : 0);
/* 442:429 */         empleado.setTotalHorasPermiso(asistencia.getHorasPermiso());
/* 443:430 */         empleado.setTotalHorasFalta(asistencia.getHorasFalta());
/* 444:431 */         if (asistencia.getHorasSubsidio() != null) {
/* 445:432 */           empleado.setTotalHorasSubsidio(new BigDecimal(asistencia.getHorasSubsidio().intValue()));
/* 446:    */         }
/* 447:    */       }
/* 448:    */       else
/* 449:    */       {
/* 450:435 */         empleado.setTotalHoras25(empleado.getTotalHoras25().add(asistencia.getHorasExtras25()));
/* 451:436 */         empleado.setTotalHoras25Aprobadas(empleado.getTotalHoras25Aprobadas().add(asistencia.getHorasExtras25Aprobadas()));
/* 452:437 */         empleado.setTotalHoras50(empleado.getTotalHoras50().add(asistencia.getHorasExtras50()));
/* 453:438 */         empleado.setTotalHoras50Aprobadas(empleado.getTotalHoras50Aprobadas().add(asistencia.getHorasExtras50Aprobadas()));
/* 454:439 */         empleado.setTotalHoras100(empleado.getTotalHoras100().add(asistencia.getHorasExtras100()));
/* 455:440 */         empleado.setTotalHoras100Feriado(empleado.getTotalHoras100Feriado().add(asistencia.getHorasExtras100Feriado()));
/* 456:441 */         empleado.setTotalHoras100FeriadoAprobadas(empleado.getTotalHoras100FeriadoAprobadas().add(asistencia.getHorasExtras100FeriadoAprobadas()));
/* 457:442 */         empleado.setTotalHoras100Aprobadas(empleado.getTotalHoras100Aprobadas().add(asistencia.getHorasExtras100Aprobadas()));
/* 458:443 */         empleado.setTotalDiasVacaciones(empleado.getTotalDiasVacaciones() + (asistencia.isIndicadorVacacion() ? 1 : 0));
/* 459:444 */         empleado.setTotalHorasPermiso(empleado.getTotalHorasPermiso().add(asistencia.getHorasPermiso()));
/* 460:445 */         empleado.setTotalHorasFalta(empleado.getTotalHorasFalta().add(asistencia.getHorasFalta()));
/* 461:446 */         if (asistencia.getHorasSubsidio() != null) {
/* 462:447 */           empleado.setTotalHorasSubsidio(empleado.getTotalHorasSubsidio().add(new BigDecimal(asistencia.getHorasSubsidio().intValue())));
/* 463:    */         }
/* 464:    */       }
/* 465:450 */       empleado.getListaAsistencia().add(asistencia);
/* 466:451 */       mapaEmpleado.put(Integer.valueOf(empleado.getId()), empleado);
/* 467:    */       
/* 468:453 */       this.horasFaltaTotal = this.horasFaltaTotal.add(asistencia.getHorasFalta());
/* 469:454 */       this.horasPermisoTotal = this.horasPermisoTotal.add(asistencia.getHorasPermiso());
/* 470:455 */       if (asistencia.getHorasSubsidio() != null) {
/* 471:456 */         this.horasSubsidioTotal = this.horasSubsidioTotal.add(new BigDecimal(asistencia.getHorasSubsidio().intValue()));
/* 472:    */       }
/* 473:458 */       this.diasVacacionesTotal += (asistencia.isIndicadorVacacion() ? 1 : 0);
/* 474:459 */       this.horasExtra25Total = this.horasExtra25Total.add(asistencia.getHorasExtras25());
/* 475:460 */       this.horasExtra50Total = this.horasExtra50Total.add(asistencia.getHorasExtras50());
/* 476:461 */       this.horasExtra100Total = this.horasExtra100Total.add(asistencia.getHorasExtras100());
/* 477:462 */       this.horasExtra100FeriadoTotal = this.horasExtra100FeriadoTotal.add(asistencia.getHorasExtras100Feriado());
/* 478:463 */       this.horasExtra25AprobadasTotal = this.horasExtra25AprobadasTotal.add(asistencia.getHorasExtras25Aprobadas());
/* 479:464 */       this.horasExtra50AprobadasTotal = this.horasExtra50AprobadasTotal.add(asistencia.getHorasExtras50Aprobadas());
/* 480:465 */       this.horasExtra100AprobadasTotal = this.horasExtra100AprobadasTotal.add(asistencia.getHorasExtras100Aprobadas());
/* 481:466 */       this.horasExtra100FeriadoAprobadasTotal = this.horasExtra100FeriadoAprobadasTotal.add(asistencia.getHorasExtras100FeriadoAprobadas());
/* 482:    */     }
/* 483:469 */     this.listaEmpleadoResumido.addAll(mapaEmpleado.values());
/* 484:    */   }
/* 485:    */   
/* 486:    */   public DataTable getDtEmpleadoResumido()
/* 487:    */   {
/* 488:473 */     return this.dtEmpleadoResumido;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public void setDtEmpleadoResumido(DataTable dtEmpleadoResumido)
/* 492:    */   {
/* 493:477 */     this.dtEmpleadoResumido = dtEmpleadoResumido;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public boolean isIndicadorAgrupado()
/* 497:    */   {
/* 498:481 */     return this.indicadorAgrupado;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public void setIndicadorAgrupado(boolean indicadorAgrupado)
/* 502:    */   {
/* 503:485 */     this.indicadorAgrupado = indicadorAgrupado;
/* 504:486 */     actualizarTabla();
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void actualizarTabla()
/* 508:    */   {
/* 509:490 */     this.listaEmpleadoResumido = null;
/* 510:491 */     this.listaEmpleadoFiltrado = null;
/* 511:492 */     this.listaAsistenciaFiltrado = null;
/* 512:493 */     if (this.indicadorAgrupado)
/* 513:    */     {
/* 514:494 */       if (this.dtEmpleadoResumido != null) {
/* 515:495 */         this.dtEmpleadoResumido.reset();
/* 516:    */       }
/* 517:497 */       if (this.dtAsistenciaResumido != null) {
/* 518:498 */         this.dtAsistenciaResumido.reset();
/* 519:    */       }
/* 520:500 */       cargarListaEmpleadoResumido();
/* 521:    */     }
/* 522:502 */     else if (this.dtAsistencia != null)
/* 523:    */     {
/* 524:503 */       this.dtAsistencia.reset();
/* 525:    */     }
/* 526:    */   }
/* 527:    */   
/* 528:    */   public DataTable getDtAsistenciaResumido()
/* 529:    */   {
/* 530:509 */     return this.dtAsistenciaResumido;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public void setDtAsistenciaResumido(DataTable dtAsistenciaResumido)
/* 534:    */   {
/* 535:513 */     this.dtAsistenciaResumido = dtAsistenciaResumido;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public List<Asistencia> getListaAsistenciaFiltrado()
/* 539:    */   {
/* 540:517 */     return this.listaAsistenciaFiltrado;
/* 541:    */   }
/* 542:    */   
/* 543:    */   public void setListaAsistenciaFiltrado(List<Asistencia> listaAsistenciaFiltrado)
/* 544:    */   {
/* 545:521 */     this.listaAsistenciaFiltrado = listaAsistenciaFiltrado;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public List<EmpleadoAsistencia> getListaEmpleadoFiltrado()
/* 549:    */   {
/* 550:525 */     return this.listaEmpleadoFiltrado;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public void setListaEmpleadoFiltrado(List<EmpleadoAsistencia> listaEmpleadoFiltrado)
/* 554:    */   {
/* 555:529 */     this.listaEmpleadoFiltrado = listaEmpleadoFiltrado;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public BigDecimal getHorasFaltaTotal()
/* 559:    */   {
/* 560:533 */     return this.horasFaltaTotal;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public void setHorasFaltaTotal(BigDecimal horasFaltaTotal)
/* 564:    */   {
/* 565:537 */     this.horasFaltaTotal = horasFaltaTotal;
/* 566:    */   }
/* 567:    */   
/* 568:    */   public BigDecimal getHorasPermisoTotal()
/* 569:    */   {
/* 570:541 */     return this.horasPermisoTotal;
/* 571:    */   }
/* 572:    */   
/* 573:    */   public void setHorasPermisoTotal(BigDecimal horasPermisoTotal)
/* 574:    */   {
/* 575:545 */     this.horasPermisoTotal = horasPermisoTotal;
/* 576:    */   }
/* 577:    */   
/* 578:    */   public BigDecimal getHorasSubsidioTotal()
/* 579:    */   {
/* 580:549 */     return this.horasSubsidioTotal;
/* 581:    */   }
/* 582:    */   
/* 583:    */   public void setHorasSubsidioTotal(BigDecimal horasSubsidioTotal)
/* 584:    */   {
/* 585:553 */     this.horasSubsidioTotal = horasSubsidioTotal;
/* 586:    */   }
/* 587:    */   
/* 588:    */   public int getDiasVacacionesTotal()
/* 589:    */   {
/* 590:557 */     return this.diasVacacionesTotal;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public void setDiasVacacionesTotal(int diasVacacionesTotal)
/* 594:    */   {
/* 595:561 */     this.diasVacacionesTotal = diasVacacionesTotal;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public BigDecimal getHorasExtra25Total()
/* 599:    */   {
/* 600:565 */     return this.horasExtra25Total;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public void setHorasExtra25Total(BigDecimal horasExtra25Total)
/* 604:    */   {
/* 605:569 */     this.horasExtra25Total = horasExtra25Total;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public BigDecimal getHorasExtra50Total()
/* 609:    */   {
/* 610:573 */     return this.horasExtra50Total;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public void setHorasExtra50Total(BigDecimal horasExtra50Total)
/* 614:    */   {
/* 615:577 */     this.horasExtra50Total = horasExtra50Total;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public BigDecimal getHorasExtra100Total()
/* 619:    */   {
/* 620:581 */     return this.horasExtra100Total;
/* 621:    */   }
/* 622:    */   
/* 623:    */   public void setHorasExtra100Total(BigDecimal horasExtra100Total)
/* 624:    */   {
/* 625:585 */     this.horasExtra100Total = horasExtra100Total;
/* 626:    */   }
/* 627:    */   
/* 628:    */   public BigDecimal getHorasExtra100FeriadoTotal()
/* 629:    */   {
/* 630:592 */     return this.horasExtra100FeriadoTotal;
/* 631:    */   }
/* 632:    */   
/* 633:    */   public void setHorasExtra100FeriadoTotal(BigDecimal horasExtra100FeriadoTotal)
/* 634:    */   {
/* 635:600 */     this.horasExtra100FeriadoTotal = horasExtra100FeriadoTotal;
/* 636:    */   }
/* 637:    */   
/* 638:    */   public BigDecimal getHorasExtra25AprobadasTotal()
/* 639:    */   {
/* 640:604 */     return this.horasExtra25AprobadasTotal;
/* 641:    */   }
/* 642:    */   
/* 643:    */   public void setHorasExtra25AprobadasTotal(BigDecimal horasExtra25AprobadasTotal)
/* 644:    */   {
/* 645:608 */     this.horasExtra25AprobadasTotal = horasExtra25AprobadasTotal;
/* 646:    */   }
/* 647:    */   
/* 648:    */   public BigDecimal getHorasExtra50AprobadasTotal()
/* 649:    */   {
/* 650:612 */     return this.horasExtra50AprobadasTotal;
/* 651:    */   }
/* 652:    */   
/* 653:    */   public void setHorasExtra50AprobadasTotal(BigDecimal horasExtra50AprobadasTotal)
/* 654:    */   {
/* 655:616 */     this.horasExtra50AprobadasTotal = horasExtra50AprobadasTotal;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public BigDecimal getHorasExtra100AprobadasTotal()
/* 659:    */   {
/* 660:620 */     return this.horasExtra100AprobadasTotal;
/* 661:    */   }
/* 662:    */   
/* 663:    */   public void setHorasExtra100AprobadasTotal(BigDecimal horasExtra100AprobadasTotal)
/* 664:    */   {
/* 665:624 */     this.horasExtra100AprobadasTotal = horasExtra100AprobadasTotal;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public void aprobarCancelarHorasExtras(Asistencia asistencia, boolean aprobar, boolean todos)
/* 669:    */   {
/* 670:629 */     if (aprobar)
/* 671:    */     {
/* 672:630 */       asistencia.setHorasExtras100Aprobadas(asistencia.getHorasExtras100());
/* 673:631 */       asistencia.setHorasExtras50Aprobadas(asistencia.getHorasExtras50());
/* 674:632 */       if (getApruebaHorasExtra100Feriado().booleanValue()) {
/* 675:633 */         asistencia.setHorasExtras100FeriadoAprobadas(asistencia.getHorasExtras100Feriado());
/* 676:    */       }
/* 677:    */     }
/* 678:    */     else
/* 679:    */     {
/* 680:637 */       asistencia.setHorasExtras100Aprobadas(BigDecimal.ZERO);
/* 681:638 */       asistencia.setHorasExtras50Aprobadas(BigDecimal.ZERO);
/* 682:639 */       if (getApruebaHorasExtra100Feriado().booleanValue()) {
/* 683:640 */         asistencia.setHorasExtras100FeriadoAprobadas(BigDecimal.ZERO);
/* 684:    */       }
/* 685:    */     }
/* 686:645 */     if (!todos) {
/* 687:646 */       guardarAsistencia(asistencia);
/* 688:    */     } else {
/* 689:    */       try
/* 690:    */       {
/* 691:649 */         this.servicioAsistencia.guardar(asistencia);
/* 692:    */       }
/* 693:    */       catch (AS2Exception e)
/* 694:    */       {
/* 695:651 */         LOG.info("ERROR AL GUARDAR DATOS Asistencia", e);
/* 696:    */       }
/* 697:    */     }
/* 698:    */   }
/* 699:    */   
/* 700:    */   public void aprobarCancelarTodoHorasExtras(boolean aprobar)
/* 701:    */   {
/* 702:658 */     List<Asistencia> lista = new ArrayList();
/* 703:    */     Map<String, String> filters;
/* 704:659 */     if (this.indicadorAgrupado)
/* 705:    */     {
/* 706:660 */       List<EmpleadoAsistencia> listaEmpleado = new ArrayList();
/* 707:661 */       if (this.listaEmpleadoFiltrado != null) {
/* 708:662 */         listaEmpleado = this.listaEmpleadoFiltrado;
/* 709:    */       } else {
/* 710:664 */         listaEmpleado = this.listaEmpleadoResumido;
/* 711:    */       }
/* 712:666 */       for (EmpleadoAsistencia ea : listaEmpleado) {
/* 713:667 */         lista.addAll(ea.getListaAsistencia());
/* 714:    */       }
/* 715:    */     }
/* 716:670 */     else if (this.listaAsistenciaFiltrado != null)
/* 717:    */     {
/* 718:671 */       lista = this.listaAsistenciaFiltrado;
/* 719:    */     }
/* 720:    */     else
/* 721:    */     {
/* 722:673 */       filters = new HashMap();
/* 723:674 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 724:675 */       filters.put("empleado.idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 725:676 */       filters.put("empleado.departamento.idDepartamento", "" + getDepartamentoSeleccionado().getId());
/* 726:677 */       lista = this.servicioAsistencia.obtenerListaPorPagina(0, 100000, filters, this.fechaDesde, this.fechaHasta);
/* 727:    */     }
/* 728:680 */     for (Asistencia a : lista) {
/* 729:681 */       aprobarCancelarHorasExtras(a, aprobar, true);
/* 730:    */     }
/* 731:684 */     actualizarTabla();
/* 732:    */   }
/* 733:    */   
/* 734:    */   public void aprobarCancelarHorasExtras(EmpleadoAsistencia empleadoAsistencia, boolean aprobar, boolean todos)
/* 735:    */   {
/* 736:695 */     for (Asistencia a : empleadoAsistencia.getListaAsistencia()) {
/* 737:696 */       aprobarCancelarHorasExtras(a, aprobar, true);
/* 738:    */     }
/* 739:699 */     actualizarTabla();
/* 740:    */   }
/* 741:    */   
/* 742:    */   public Asistencia getAsistenciaManual()
/* 743:    */   {
/* 744:703 */     return this.asistenciaManual;
/* 745:    */   }
/* 746:    */   
/* 747:    */   public void setAsistenciaManual(Asistencia asistenciaManual)
/* 748:    */   {
/* 749:707 */     this.asistenciaManual = asistenciaManual;
/* 750:    */   }
/* 751:    */   
/* 752:    */   public Empleado getEmpleado()
/* 753:    */   {
/* 754:711 */     return this.empleado;
/* 755:    */   }
/* 756:    */   
/* 757:    */   public void setEmpleado(Empleado empleado)
/* 758:    */   {
/* 759:715 */     this.empleado = empleado;
/* 760:    */   }
/* 761:    */   
/* 762:    */   public Turno getTurno()
/* 763:    */   {
/* 764:719 */     return this.turno;
/* 765:    */   }
/* 766:    */   
/* 767:    */   public void setTurno(Turno turno)
/* 768:    */   {
/* 769:723 */     this.turno = turno;
/* 770:    */   }
/* 771:    */   
/* 772:    */   public void nuevaAsistencia()
/* 773:    */   {
/* 774:727 */     this.asistenciaManual = new Asistencia();
/* 775:728 */     this.asistenciaManual.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 776:729 */     this.asistenciaManual.setIdSucursal(AppUtil.getSucursal().getId());
/* 777:730 */     this.asistenciaManual.setIndicadorCreadaManualmente(true);
/* 778:    */   }
/* 779:    */   
/* 780:    */   public void crearAsistencia()
/* 781:    */   {
/* 782:    */     try
/* 783:    */     {
/* 784:735 */       this.servicioAsistencia.crearAsistenciaManual(this.asistenciaManual, this.turno);
/* 785:736 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 786:    */     }
/* 787:    */     catch (AS2Exception e)
/* 788:    */     {
/* 789:738 */       e.printStackTrace();
/* 790:739 */       JsfUtil.addErrorMessage(e, "");
/* 791:    */     }
/* 792:    */   }
/* 793:    */   
/* 794:    */   public List<Empleado> autocompletarEmpleado(String consulta)
/* 795:    */   {
/* 796:744 */     if (this.departamentoSeleccionado != null)
/* 797:    */     {
/* 798:745 */       Map<String, String> filtro = new HashMap();
/* 799:746 */       filtro.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 800:747 */       filtro.put("filtro", consulta);
/* 801:748 */       filtro.put("departamento.idDepartamento", this.departamentoSeleccionado.getId() + "");
/* 802:749 */       List<Empleado> listaEmpleado = this.servicioEmpleado.obtenerListaCombo("apellidos", true, filtro);
/* 803:750 */       return listaEmpleado;
/* 804:    */     }
/* 805:752 */     return new ArrayList();
/* 806:    */   }
/* 807:    */   
/* 808:    */   public String eliminar()
/* 809:    */   {
/* 810:    */     try
/* 811:    */     {
/* 812:758 */       this.servicioAsistencia.eliminar(this.asistencia);
/* 813:759 */       actualizarTabla();
/* 814:760 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 815:    */     }
/* 816:    */     catch (Exception e)
/* 817:    */     {
/* 818:762 */       e.printStackTrace();
/* 819:763 */       addErrorMessage(e.getMessage());
/* 820:    */     }
/* 821:765 */     return null;
/* 822:    */   }
/* 823:    */   
/* 824:    */   public static String getCamposActualizar()
/* 825:    */   {
/* 826:770 */     return camposActualizar;
/* 827:    */   }
/* 828:    */   
/* 829:    */   public static void setCamposActualizar(String camposActualizar)
/* 830:    */   {
/* 831:774 */     camposActualizar = camposActualizar;
/* 832:    */   }
/* 833:    */   
/* 834:    */   public String getNumeroFilasPorPagina()
/* 835:    */   {
/* 836:778 */     return "5,10,15,30,50";
/* 837:    */   }
/* 838:    */   
/* 839:    */   public boolean isUsuarioAdministrador()
/* 840:    */   {
/* 841:782 */     if (this.usuarioAdministrador == null)
/* 842:    */     {
/* 843:783 */       Integer idUsuario = Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 844:784 */       EntidadUsuario usuario = this.servicioUsuario.buscarPorId(idUsuario);
/* 845:785 */       this.usuarioAdministrador = Boolean.valueOf(usuario.isIndicadorAdministrador());
/* 846:    */     }
/* 847:787 */     return this.usuarioAdministrador.booleanValue();
/* 848:    */   }
/* 849:    */   
/* 850:    */   public List<TipoFalta> getListaTipoFalta()
/* 851:    */   {
/* 852:791 */     if (this.listaTipoFalta == null)
/* 853:    */     {
/* 854:792 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 855:793 */       filters.put("activo", "true");
/* 856:794 */       this.listaTipoFalta = this.servicioTipoFalta.obtenerListaCombo(TipoFalta.class, "nombre", true, filters);
/* 857:    */     }
/* 858:796 */     return this.listaTipoFalta;
/* 859:    */   }
/* 860:    */   
/* 861:    */   public void setListaTipoFalta(List<TipoFalta> listaTipoFalta)
/* 862:    */   {
/* 863:800 */     this.listaTipoFalta = listaTipoFalta;
/* 864:    */   }
/* 865:    */   
/* 866:    */   public Boolean getApruebaHorasExtra100Feriado()
/* 867:    */   {
/* 868:804 */     if (this.apruebaHorasExtra100Feriado == null) {
/* 869:805 */       this.apruebaHorasExtra100Feriado = ParametrosSistema.isApruebarHorasExtra100Feriados(AppUtil.getOrganizacion().getId());
/* 870:    */     }
/* 871:807 */     return this.apruebaHorasExtra100Feriado;
/* 872:    */   }
/* 873:    */   
/* 874:    */   public BigDecimal getHorasExtra100FeriadoAprobadasTotal()
/* 875:    */   {
/* 876:811 */     return this.horasExtra100FeriadoAprobadasTotal;
/* 877:    */   }
/* 878:    */   
/* 879:    */   public void setHorasExtra100FeriadoAprobadasTotal(BigDecimal horasExtra100FeriadoAprobadasTotal)
/* 880:    */   {
/* 881:815 */     this.horasExtra100FeriadoAprobadasTotal = horasExtra100FeriadoAprobadasTotal;
/* 882:    */   }
/* 883:    */   
/* 884:    */   public Boolean getRegistraReingresos()
/* 885:    */   {
/* 886:819 */     if (this.registraReingresos == null) {
/* 887:820 */       this.registraReingresos = ParametrosSistema.isRegistraReingresos(AppUtil.getOrganizacion().getId());
/* 888:    */     }
/* 889:822 */     return this.registraReingresos;
/* 890:    */   }
/* 891:    */   
/* 892:    */   public boolean isMostrarReingresos()
/* 893:    */   {
/* 894:826 */     return this.mostrarReingresos;
/* 895:    */   }
/* 896:    */   
/* 897:    */   public void setMostrarReingresos(boolean mostrarReingresos)
/* 898:    */   {
/* 899:830 */     this.mostrarReingresos = mostrarReingresos;
/* 900:    */   }
/* 901:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.procesos.RegistroAsistenciaBean
 * JD-Core Version:    0.7.0.1
 */