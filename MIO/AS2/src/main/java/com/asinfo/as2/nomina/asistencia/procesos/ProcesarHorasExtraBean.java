/*   1:    */ package com.asinfo.as2.nomina.asistencia.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   5:    */ import com.asinfo.as2.entities.Departamento;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.PagoRol;
/*   9:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  10:    */ import com.asinfo.as2.entities.Quincena;
/*  11:    */ import com.asinfo.as2.entities.Rubro;
/*  12:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*  13:    */ import com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia;
/*  14:    */ import com.asinfo.as2.entities.nomina.asistencia.TipoFalta;
/*  15:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  16:    */ import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistencia;
/*  19:    */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistenciaConfiguracion;
/*  20:    */ import com.asinfo.as2.nomina.configuracion.EmpleadoBean;
/*  21:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  22:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  23:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  24:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  25:    */ import com.asinfo.as2.util.AppUtil;
/*  26:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  27:    */ import com.asinfo.as2.utils.JsfUtil;
/*  28:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  29:    */ import java.math.BigDecimal;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Calendar;
/*  32:    */ import java.util.Collection;
/*  33:    */ import java.util.Date;
/*  34:    */ import java.util.HashMap;
/*  35:    */ import java.util.Iterator;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.annotation.PostConstruct;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.context.FacesContext;
/*  43:    */ import javax.faces.context.PartialViewContext;
/*  44:    */ import javax.faces.model.SelectItem;
/*  45:    */ import org.apache.log4j.Logger;
/*  46:    */ import org.primefaces.component.datatable.DataTable;
/*  47:    */ import org.primefaces.context.RequestContext;
/*  48:    */ 
/*  49:    */ @ManagedBean
/*  50:    */ @ViewScoped
/*  51:    */ public class ProcesarHorasExtraBean
/*  52:    */   extends EmpleadoBean
/*  53:    */ {
/*  54:    */   private static final long serialVersionUID = 1L;
/*  55:    */   @EJB
/*  56:    */   private ServicioUsuario servicioUsuario;
/*  57:    */   @EJB
/*  58:    */   private ServicioAsistenciaProcesos servicioAsistenciaProcesos;
/*  59:    */   @EJB
/*  60:    */   private ServicioAsistencia servicioAsistencia;
/*  61:    */   @EJB
/*  62:    */   private ServicioAsistenciaConfiguracion servicioAsistenciaConfiguracion;
/*  63:    */   @EJB
/*  64:    */   private ServicioPagoRol servicioPagoRol;
/*  65:    */   @EJB
/*  66:    */   private ServicioRubroEmpleado servicioRubroEmpleado;
/*  67:    */   @EJB
/*  68:    */   private transient ServicioGenerico<TipoFalta> servicioTipoFalta;
/*  69:    */   private Departamento departamentoSeleccionado;
/*  70:    */   private DataTable dtEmpleadoAsistencia;
/*  71: 90 */   private Date fechaDesde = new Date();
/*  72: 91 */   private Date fechaHasta = new Date();
/*  73:    */   private Mes mesSeleccionado;
/*  74:    */   private List<SelectItem> listaPagoRol;
/*  75:    */   private PagoRol pagoRol;
/*  76:    */   private List<EmpleadoAsistencia> listaEmpleadoAsistencia;
/*  77:    */   private List<EmpleadoAsistencia> listaEmpleadoAsistenciaFiltrado;
/*  78:    */   private List<EmpleadoAsistencia> listaEmpleadoFaltas;
/*  79:    */   private List<EmpleadoAsistencia> listaEmpleadoFaltasFiltrado;
/*  80:104 */   private BigDecimal cantidadTotal25 = BigDecimal.ZERO;
/*  81:105 */   private BigDecimal cantidadTotal50 = BigDecimal.ZERO;
/*  82:106 */   private BigDecimal cantidadTotal100 = BigDecimal.ZERO;
/*  83:    */   private int totalDiasFalta;
/*  84:    */   private Boolean procesaDiasFalta;
/*  85:    */   private Rubro rubro25;
/*  86:    */   private Rubro rubro50;
/*  87:    */   private Rubro rubro100;
/*  88:    */   private List<RubroEmpleado> listaEmpleadosAsignacionRubro;
/*  89:    */   private DataTable dtEmpleadoAsignacionRubro;
/*  90:    */   private DataTable dtFaltaEmpleado;
/*  91:    */   private int anno;
/*  92:    */   
/*  93:    */   @PostConstruct
/*  94:    */   public void init()
/*  95:    */   {
/*  96:124 */     Calendar fecha = Calendar.getInstance();
/*  97:    */     
/*  98:126 */     this.mesSeleccionado = Mes.values()[fecha.get(2)];
/*  99:127 */     this.anno = fecha.get(1);
/* 100:    */     
/* 101:129 */     actualizarFecha(false);
/* 102:    */     
/* 103:131 */     this.rubro25 = this.servicioAsistenciaConfiguracion.buscarRubroHoraExtra(AppUtil.getOrganizacion().getId(), PorCientoHoraExtra.VEINTICINCO);
/* 104:132 */     this.rubro50 = this.servicioAsistenciaConfiguracion.buscarRubroHoraExtra(AppUtil.getOrganizacion().getId(), PorCientoHoraExtra.CINCUENTA);
/* 105:133 */     this.rubro100 = this.servicioAsistenciaConfiguracion.buscarRubroHoraExtra(AppUtil.getOrganizacion().getId(), PorCientoHoraExtra.CIEN);
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void actualizarFecha(boolean reiniciar)
/* 109:    */   {
/* 110:138 */     int diaCorte = ParametrosSistema.getDiaCortePagoHorasExtras(AppUtil.getOrganizacion().getId()).intValue();
/* 111:139 */     boolean esFinMes = diaCorte == 0;
/* 112:140 */     Date ultimoDiaMes = FuncionesUtiles.getFechaFinMes(this.anno, this.mesSeleccionado.ordinal() + 1);
/* 113:141 */     Calendar fechaHastaC = Calendar.getInstance();
/* 114:142 */     fechaHastaC.setTime(ultimoDiaMes);
/* 115:    */     
/* 116:144 */     int ultimoDia = fechaHastaC.get(5);
/* 117:145 */     if ((diaCorte == 0) || (diaCorte > ultimoDia)) {
/* 118:146 */       diaCorte = ultimoDia;
/* 119:    */     }
/* 120:148 */     fechaHastaC.set(5, diaCorte);
/* 121:149 */     this.fechaHasta = fechaHastaC.getTime();
/* 122:    */     
/* 123:151 */     Calendar fechaDesdeC = (Calendar)fechaHastaC.clone();
/* 124:152 */     fechaDesdeC.add(2, -1);
/* 125:153 */     this.fechaDesde = fechaDesdeC.getTime();
/* 126:155 */     if (esFinMes) {
/* 127:156 */       this.fechaDesde = FuncionesUtiles.getFechaFinMes(this.fechaDesde);
/* 128:    */     }
/* 129:158 */     fechaDesdeC.setTime(this.fechaDesde);
/* 130:159 */     fechaDesdeC.add(5, 1);
/* 131:160 */     this.fechaDesde = fechaDesdeC.getTime();
/* 132:    */     
/* 133:162 */     this.listaPagoRol = null;
/* 134:164 */     if (reiniciar) {
/* 135:165 */       reiniciarTabla();
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Departamento getDepartamentoSeleccionado()
/* 140:    */   {
/* 141:170 */     if (this.departamentoSeleccionado == null) {
/* 142:171 */       this.departamentoSeleccionado = new Departamento();
/* 143:    */     }
/* 144:173 */     return this.departamentoSeleccionado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDepartamentoSeleccionado(Departamento departamentoSeleccionado)
/* 148:    */   {
/* 149:177 */     this.departamentoSeleccionado = departamentoSeleccionado;
/* 150:178 */     reiniciarTabla();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<Departamento> getListaDepartamento()
/* 154:    */   {
/* 155:182 */     Map<String, String> filtros = new HashMap();
/* 156:183 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 157:184 */     return this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFechaDesde()
/* 161:    */   {
/* 162:188 */     return this.fechaDesde;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFechaDesde(Date fechaDesde)
/* 166:    */   {
/* 167:192 */     this.fechaDesde = fechaDesde;
/* 168:193 */     reiniciarTabla();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Date getFechaHasta()
/* 172:    */   {
/* 173:197 */     return this.fechaHasta;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setFechaHasta(Date fechaHasta)
/* 177:    */   {
/* 178:201 */     this.fechaHasta = fechaHasta;
/* 179:202 */     reiniciarTabla();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<SelectItem> getListaMes()
/* 183:    */   {
/* 184:207 */     List<SelectItem> lista = new ArrayList();
/* 185:208 */     for (Mes item : Mes.values()) {
/* 186:209 */       lista.add(new SelectItem(item, item.getNombre()));
/* 187:    */     }
/* 188:211 */     return lista;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Mes getMesSeleccionado()
/* 192:    */   {
/* 193:215 */     return this.mesSeleccionado;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setMesSeleccionado(Mes mesSeleccionado)
/* 197:    */   {
/* 198:219 */     this.mesSeleccionado = mesSeleccionado;
/* 199:220 */     actualizarFecha(true);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int getAnno()
/* 203:    */   {
/* 204:224 */     return this.anno;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setAnno(int anno)
/* 208:    */   {
/* 209:228 */     this.anno = anno;
/* 210:229 */     actualizarFecha(true);
/* 211:    */   }
/* 212:    */   
/* 213:    */   private void reiniciarTabla()
/* 214:    */   {
/* 215:233 */     this.listaEmpleadoAsistencia = null;
/* 216:234 */     this.listaEmpleadoAsistenciaFiltrado = null;
/* 217:235 */     this.listaEmpleadoFaltas = null;
/* 218:236 */     this.listaEmpleadoFaltasFiltrado = null;
/* 219:237 */     this.dtEmpleadoAsistencia.reset();
/* 220:238 */     this.dtFaltaEmpleado.reset();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<SelectItem> getListaPagoRol()
/* 224:    */   {
/* 225:242 */     List<PagoRol> lista = new ArrayList();
/* 226:243 */     Map<String, String> filters = new HashMap();
/* 227:244 */     filters.put("indicadorFiniquito", "false");
/* 228:245 */     filters.put("mes", "" + (this.mesSeleccionado.ordinal() + 1));
/* 229:246 */     filters.put("anio", "" + this.anno);
/* 230:247 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 231:249 */     if (this.listaPagoRol == null)
/* 232:    */     {
/* 233:250 */       lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 234:251 */       this.listaPagoRol = new ArrayList();
/* 235:252 */       for (PagoRol pagoRol : lista)
/* 236:    */       {
/* 237:258 */         String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 238:259 */         SelectItem item = new SelectItem(pagoRol, label);
/* 239:260 */         this.listaPagoRol.add(item);
/* 240:    */       }
/* 241:262 */       if (lista.size() > 0) {
/* 242:263 */         this.pagoRol = ((PagoRol)lista.get(0));
/* 243:    */       }
/* 244:    */     }
/* 245:267 */     return this.listaPagoRol;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setListaPagoRol(List<SelectItem> listaPagoRol)
/* 249:    */   {
/* 250:271 */     this.listaPagoRol = listaPagoRol;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public PagoRol getPagoRol()
/* 254:    */   {
/* 255:275 */     return this.pagoRol;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setPagoRol(PagoRol pagoRol)
/* 259:    */   {
/* 260:279 */     this.pagoRol = pagoRol;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public List<EmpleadoAsistencia> getListaEmpleadoAsistencia()
/* 264:    */   {
/* 265:283 */     if (this.listaEmpleadoAsistencia == null) {
/* 266:284 */       cargarListaEmpleadoAsistencia();
/* 267:    */     }
/* 268:286 */     return this.listaEmpleadoAsistencia;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setListaEmpleadoAsistencia(List<EmpleadoAsistencia> listaEmpleadoAsistencia)
/* 272:    */   {
/* 273:290 */     this.listaEmpleadoAsistencia = listaEmpleadoAsistencia;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<EmpleadoAsistencia> getListaEmpleadoAsistenciaFiltrado()
/* 277:    */   {
/* 278:294 */     return this.listaEmpleadoAsistenciaFiltrado;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setListaEmpleadoAsistenciaFiltrado(List<EmpleadoAsistencia> listaEmpleadoAsistenciaFiltrado)
/* 282:    */   {
/* 283:298 */     this.listaEmpleadoAsistenciaFiltrado = listaEmpleadoAsistenciaFiltrado;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public DataTable getDtEmpleadoAsistencia()
/* 287:    */   {
/* 288:302 */     return this.dtEmpleadoAsistencia;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setDtEmpleadoAsistencia(DataTable dtEmpleadoAsistencia)
/* 292:    */   {
/* 293:306 */     this.dtEmpleadoAsistencia = dtEmpleadoAsistencia;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void cargarListaEmpleadoAsistencia()
/* 297:    */   {
/* 298:310 */     this.listaEmpleadoAsistencia = this.servicioAsistencia.obtenerListaHoraExtraEmpleado(this.departamentoSeleccionado, this.fechaDesde, this.fechaHasta, 
/* 299:311 */       AppUtil.getOrganizacion().getId());
/* 300:312 */     totalizar();
/* 301:    */   }
/* 302:    */   
/* 303:    */   public BigDecimal getCantidadTotal25()
/* 304:    */   {
/* 305:316 */     return this.cantidadTotal25;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setCantidadTotal25(BigDecimal cantidadTotal25)
/* 309:    */   {
/* 310:320 */     this.cantidadTotal25 = cantidadTotal25;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public BigDecimal getCantidadTotal50()
/* 314:    */   {
/* 315:324 */     return this.cantidadTotal50;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setCantidadTotal50(BigDecimal cantidadTotal50)
/* 319:    */   {
/* 320:328 */     this.cantidadTotal50 = cantidadTotal50;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public BigDecimal getCantidadTotal100()
/* 324:    */   {
/* 325:332 */     return this.cantidadTotal100;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setCantidadTotal100(BigDecimal cantidadTotal100)
/* 329:    */   {
/* 330:336 */     this.cantidadTotal100 = cantidadTotal100;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Rubro getRubro25()
/* 334:    */   {
/* 335:340 */     return this.rubro25;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setRubro25(Rubro rubro25)
/* 339:    */   {
/* 340:344 */     this.rubro25 = rubro25;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public Rubro getRubro50()
/* 344:    */   {
/* 345:348 */     return this.rubro50;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setRubro50(Rubro rubro50)
/* 349:    */   {
/* 350:352 */     this.rubro50 = rubro50;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public Rubro getRubro100()
/* 354:    */   {
/* 355:356 */     return this.rubro100;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setRubro100(Rubro rubro100)
/* 359:    */   {
/* 360:360 */     this.rubro100 = rubro100;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public List<RubroEmpleado> getListaEmpleadosAsignacionRubro()
/* 364:    */   {
/* 365:364 */     return this.listaEmpleadosAsignacionRubro;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setListaEmpleadosAsignacionRubro(List<RubroEmpleado> listaEmpleadosAsignacionRubro)
/* 369:    */   {
/* 370:368 */     this.listaEmpleadosAsignacionRubro = listaEmpleadosAsignacionRubro;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public DataTable getDtEmpleadoAsignacionRubro()
/* 374:    */   {
/* 375:372 */     return this.dtEmpleadoAsignacionRubro;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void setDtEmpleadoAsignacionRubro(DataTable dtEmpleadoAsignacionRubro)
/* 379:    */   {
/* 380:376 */     this.dtEmpleadoAsignacionRubro = dtEmpleadoAsignacionRubro;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void totalizar()
/* 384:    */   {
/* 385:380 */     this.cantidadTotal25 = BigDecimal.ZERO;
/* 386:381 */     this.cantidadTotal50 = BigDecimal.ZERO;
/* 387:382 */     this.cantidadTotal100 = BigDecimal.ZERO;
/* 388:    */     List<EmpleadoAsistencia> lista;
/* 389:    */     List<EmpleadoAsistencia> lista;
/* 390:384 */     if (this.listaEmpleadoAsistenciaFiltrado != null) {
/* 391:385 */       lista = this.listaEmpleadoAsistenciaFiltrado;
/* 392:    */     } else {
/* 393:387 */       lista = this.listaEmpleadoAsistencia;
/* 394:    */     }
/* 395:390 */     for (EmpleadoAsistencia ea : lista)
/* 396:    */     {
/* 397:391 */       if (ea.getTotalHoras25Aprobadas().compareTo(BigDecimal.ZERO) > 0) {
/* 398:392 */         ea.setRubro25(this.rubro25);
/* 399:    */       }
/* 400:394 */       if (ea.getTotalHoras50Aprobadas().compareTo(BigDecimal.ZERO) > 0) {
/* 401:395 */         ea.setRubro50(this.rubro50);
/* 402:    */       }
/* 403:397 */       if (ea.getTotalHoras100Aprobadas().compareTo(BigDecimal.ZERO) > 0) {
/* 404:398 */         ea.setRubro100(this.rubro100);
/* 405:    */       }
/* 406:401 */       this.cantidadTotal25 = this.cantidadTotal25.add(ea.getTotalHoras25Aprobadas());
/* 407:402 */       this.cantidadTotal50 = this.cantidadTotal50.add(ea.getTotalHoras50Aprobadas());
/* 408:403 */       this.cantidadTotal100 = this.cantidadTotal100.add(ea.getTotalHoras100Aprobadas());
/* 409:    */     }
/* 410:    */   }
/* 411:    */   
/* 412:    */   private void verificarRubrosAsignados(List<EmpleadoAsistencia> listaEmpleadoAsistencia)
/* 413:    */   {
/* 414:408 */     this.listaEmpleadosAsignacionRubro = new ArrayList();
/* 415:409 */     this.pagoRol = this.servicioPagoRol.cargarDetalle(this.pagoRol.getIdPagoRol());
/* 416:410 */     Map<Integer, PagoRolEmpleado> hashPagoRolEmpleado = clasificarPagoRolEmpleado();
/* 417:412 */     for (EmpleadoAsistencia empleadoAsistencia2 : listaEmpleadoAsistencia)
/* 418:    */     {
/* 419:413 */       PagoRolEmpleado pre = (PagoRolEmpleado)hashPagoRolEmpleado.get(Integer.valueOf(empleadoAsistencia2.getIdEmpleado()));
/* 420:414 */       if (pre != null) {
/* 421:415 */         this.listaEmpleadosAsignacionRubro.addAll(this.servicioPagoRol.listaEmpleadosAsignarRubro(this.pagoRol, empleadoAsistencia2, pre));
/* 422:    */       }
/* 423:    */     }
/* 424:419 */     if (!this.listaEmpleadosAsignacionRubro.isEmpty())
/* 425:    */     {
/* 426:420 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
/* 427:421 */       RequestContext.getCurrentInstance().execute("empleadosAsignacionRubroDialogo.show()");
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   private Map<Integer, PagoRolEmpleado> clasificarPagoRolEmpleado()
/* 432:    */   {
/* 433:426 */     Map<Integer, PagoRolEmpleado> hashPagoRolEmpleado = new HashMap();
/* 434:427 */     for (PagoRolEmpleado pagoRolEmpleado : this.pagoRol.getListaPagoRolEmpleado()) {
/* 435:428 */       if (!hashPagoRolEmpleado.containsKey(Integer.valueOf(pagoRolEmpleado.getEmpleado().getId())))
/* 436:    */       {
/* 437:429 */         pagoRolEmpleado.setDiasFalta(0);
/* 438:430 */         hashPagoRolEmpleado.put(Integer.valueOf(pagoRolEmpleado.getEmpleado().getId()), pagoRolEmpleado);
/* 439:    */       }
/* 440:    */     }
/* 441:433 */     return hashPagoRolEmpleado;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void procesar(EmpleadoAsistencia empleadoAsistencia, boolean reiniciar)
/* 445:    */   {
/* 446:436 */     if (this.pagoRol == null)
/* 447:    */     {
/* 448:437 */       addErrorMessage(getLanguageController().getMensaje("msg_error_no_seleccionado_pago_rol"));
/* 449:438 */       return;
/* 450:    */     }
/* 451:441 */     if (reiniciar)
/* 452:    */     {
/* 453:442 */       List<EmpleadoAsistencia> listaEmpleadoAsistencia = new ArrayList();
/* 454:443 */       listaEmpleadoAsistencia.add(empleadoAsistencia);
/* 455:444 */       verificarRubrosAsignados(listaEmpleadoAsistencia);
/* 456:    */     }
/* 457:447 */     if (this.listaEmpleadosAsignacionRubro.isEmpty())
/* 458:    */     {
/* 459:448 */       boolean error = true;
/* 460:    */       try
/* 461:    */       {
/* 462:450 */         this.servicioPagoRol.procesarHorasExtra(this.pagoRol, empleadoAsistencia);
/* 463:451 */         error = false;
/* 464:    */       }
/* 465:    */       catch (AS2Exception e)
/* 466:    */       {
/* 467:453 */         JsfUtil.addErrorMessage(e, "");
/* 468:454 */         LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 469:455 */         e.printStackTrace();
/* 470:    */       }
/* 471:458 */       if (reiniciar)
/* 472:    */       {
/* 473:459 */         reiniciarTabla();
/* 474:460 */         if (!error) {
/* 475:461 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 476:    */         }
/* 477:    */       }
/* 478:    */     }
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void procesarTodos()
/* 482:    */   {
/* 483:468 */     if (this.pagoRol == null)
/* 484:    */     {
/* 485:469 */       addErrorMessage(getLanguageController().getMensaje("msg_error_no_seleccionado_pago_rol")); return;
/* 486:    */     }
/* 487:    */     List<EmpleadoAsistencia> lista;
/* 488:    */     List<EmpleadoAsistencia> lista;
/* 489:473 */     if (this.listaEmpleadoAsistenciaFiltrado != null) {
/* 490:474 */       lista = this.listaEmpleadoAsistenciaFiltrado;
/* 491:    */     } else {
/* 492:476 */       lista = this.listaEmpleadoAsistencia;
/* 493:    */     }
/* 494:478 */     verificarRubrosAsignados(lista);
/* 495:479 */     if (this.listaEmpleadosAsignacionRubro.isEmpty())
/* 496:    */     {
/* 497:480 */       for (EmpleadoAsistencia empleadoAsistencia : lista) {
/* 498:481 */         procesar(empleadoAsistencia, false);
/* 499:    */       }
/* 500:484 */       reiniciarTabla();
/* 501:485 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 502:    */     }
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void procesarFaltas()
/* 506:    */   {
/* 507:491 */     if (this.pagoRol == null)
/* 508:    */     {
/* 509:492 */       addErrorMessage(getLanguageController().getMensaje("msg_error_no_seleccionado_pago_rol"));
/* 510:493 */       return;
/* 511:    */     }
/* 512:495 */     verificarRubrosFaltas();
/* 513:496 */     if (!this.listaEmpleadosAsignacionRubro.isEmpty())
/* 514:    */     {
/* 515:497 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
/* 516:498 */       RequestContext.getCurrentInstance().execute("empleadosAsignacionRubroDialogo.show()");
/* 517:    */     }
/* 518:500 */     reiniciarTabla();
/* 519:501 */     addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 520:    */   }
/* 521:    */   
/* 522:    */   private void verificarRubrosFaltas()
/* 523:    */   {
/* 524:505 */     this.listaEmpleadosAsignacionRubro = new ArrayList();
/* 525:506 */     this.pagoRol = this.servicioPagoRol.cargarDetalle(this.pagoRol.getIdPagoRol());
/* 526:507 */     Map<Integer, PagoRolEmpleado> hashPagoRolEmpleado = clasificarPagoRolEmpleado();
/* 527:508 */     if (this.listaEmpleadoFaltas != null) {
/* 528:509 */       for (EmpleadoAsistencia empleadoAsistencia2 : this.listaEmpleadoFaltas)
/* 529:    */       {
/* 530:510 */         PagoRolEmpleado pre = (PagoRolEmpleado)hashPagoRolEmpleado.get(Integer.valueOf(empleadoAsistencia2.getIdEmpleado()));
/* 531:511 */         if (pre != null) {
/* 532:512 */           this.listaEmpleadosAsignacionRubro.addAll(this.servicioPagoRol.listaEmpleadosRubroFaltas(this.pagoRol, empleadoAsistencia2, pre));
/* 533:    */         }
/* 534:    */       }
/* 535:    */     }
/* 536:    */   }
/* 537:    */   
/* 538:    */   public void guardarEmpleadoAsignacionRubro()
/* 539:    */   {
/* 540:520 */     this.servicioRubroEmpleado.guardarListaRubroEmpleadoAsignarPagoRol(this.listaEmpleadosAsignacionRubro, this.pagoRol);
/* 541:521 */     addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 542:    */   }
/* 543:    */   
/* 544:    */   public DataTable getDtFaltaEmpleado()
/* 545:    */   {
/* 546:525 */     return this.dtFaltaEmpleado;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public void setDtFaltaEmpleado(DataTable dtFaltaEmpleado)
/* 550:    */   {
/* 551:529 */     this.dtFaltaEmpleado = dtFaltaEmpleado;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public List<EmpleadoAsistencia> getListaEmpleadoFaltas()
/* 555:    */   {
/* 556:533 */     if ((this.listaEmpleadoFaltas == null) && (getProcesaDiasFalta().booleanValue())) {
/* 557:534 */       cargarListaFaltaEmpleado();
/* 558:    */     }
/* 559:535 */     return this.listaEmpleadoFaltas;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setListaEmpleadoFaltas(List<EmpleadoAsistencia> listaEmpleadoFaltas)
/* 563:    */   {
/* 564:539 */     this.listaEmpleadoFaltas = listaEmpleadoFaltas;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void cargarListaFaltaEmpleado()
/* 568:    */   {
/* 569:543 */     this.listaEmpleadoFaltas = new ArrayList();
/* 570:544 */     this.totalDiasFalta = 0;
/* 571:545 */     List<EmpleadoAsistencia> listaTemp = this.servicioAsistencia.obtenerListaFaltasEmpleado(this.departamentoSeleccionado, this.fechaDesde, this.fechaHasta, 
/* 572:546 */       AppUtil.getOrganizacion().getId());
/* 573:547 */     Map<Integer, TipoFalta> hashTipoFalta = new HashMap();
/* 574:548 */     List<String> listaCampos = new ArrayList();
/* 575:549 */     listaCampos.add("rubro");
/* 576:550 */     for (Iterator localIterator1 = this.servicioTipoFalta.obtenerListaCombo(TipoFalta.class, "nombre", true, agregarFiltroOrganizacion(null)).iterator(); localIterator1.hasNext();)
/* 577:    */     {
/* 578:550 */       tf = (TipoFalta)localIterator1.next();
/* 579:551 */       TipoFalta tipoFaltaBD = (TipoFalta)this.servicioTipoFalta.cargarDetalle(TipoFalta.class, tf.getId(), listaCampos);
/* 580:552 */       hashTipoFalta.put(Integer.valueOf(tf.getId()), tipoFaltaBD);
/* 581:    */     }
/* 582:    */     TipoFalta tf;
/* 583:555 */     Object hashEmpleadoAsistencia = new HashMap();
/* 584:556 */     for (EmpleadoAsistencia e : listaTemp)
/* 585:    */     {
/* 586:557 */       if (e.getIdTipoFalta() != 0) {
/* 587:558 */         e.setTipoFalta((TipoFalta)hashTipoFalta.get(Integer.valueOf(e.getIdTipoFalta())));
/* 588:    */       }
/* 589:559 */       if (((Map)hashEmpleadoAsistencia).containsKey(e.getIdentificacion() + "~" + e.getIdTipoFalta()))
/* 590:    */       {
/* 591:560 */         ((List)((Map)hashEmpleadoAsistencia).get(e.getIdentificacion() + "~" + e.getIdTipoFalta())).add(e);
/* 592:    */       }
/* 593:    */       else
/* 594:    */       {
/* 595:562 */         List<EmpleadoAsistencia> listEA = new ArrayList();
/* 596:563 */         listEA.add(e);
/* 597:564 */         ((Map)hashEmpleadoAsistencia).put(e.getIdentificacion() + "~" + e.getIdTipoFalta(), listEA);
/* 598:    */       }
/* 599:    */     }
/* 600:568 */     for (List<EmpleadoAsistencia> lEA : ((Map)hashEmpleadoAsistencia).values())
/* 601:    */     {
/* 602:569 */       int diasFalta = lEA.size();
/* 603:570 */       this.totalDiasFalta += diasFalta;
/* 604:571 */       EmpleadoAsistencia empleadoAsistencia = (EmpleadoAsistencia)lEA.get(0);
/* 605:572 */       empleadoAsistencia.setCantidadDiasFalta(diasFalta);
/* 606:573 */       for (EmpleadoAsistencia ea : lEA) {
/* 607:574 */         empleadoAsistencia.getListaDiasFalta().add(Integer.valueOf(ea.getDiaMes()));
/* 608:    */       }
/* 609:576 */       this.listaEmpleadoFaltas.add(empleadoAsistencia);
/* 610:    */     }
/* 611:    */   }
/* 612:    */   
/* 613:    */   public int getTotalDiasFalta()
/* 614:    */   {
/* 615:582 */     return this.totalDiasFalta;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public void setTotalDiasFalta(int totalDiasFalta)
/* 619:    */   {
/* 620:586 */     this.totalDiasFalta = totalDiasFalta;
/* 621:    */   }
/* 622:    */   
/* 623:    */   public List<EmpleadoAsistencia> getListaEmpleadoFaltasFiltrado()
/* 624:    */   {
/* 625:590 */     return this.listaEmpleadoFaltasFiltrado;
/* 626:    */   }
/* 627:    */   
/* 628:    */   public void setListaEmpleadoFaltasFiltrado(List<EmpleadoAsistencia> listaEmpleadoFaltasFiltrado)
/* 629:    */   {
/* 630:594 */     this.listaEmpleadoFaltasFiltrado = listaEmpleadoFaltasFiltrado;
/* 631:    */   }
/* 632:    */   
/* 633:    */   public void totalizarFaltas()
/* 634:    */   {
/* 635:598 */     if (getProcesaDiasFalta().booleanValue())
/* 636:    */     {
/* 637:599 */       this.totalDiasFalta = 0;
/* 638:    */       List<EmpleadoAsistencia> lista;
/* 639:    */       List<EmpleadoAsistencia> lista;
/* 640:601 */       if (this.listaEmpleadoFaltasFiltrado != null) {
/* 641:602 */         lista = this.listaEmpleadoFaltasFiltrado;
/* 642:    */       } else {
/* 643:604 */         lista = this.listaEmpleadoFaltas;
/* 644:    */       }
/* 645:607 */       for (EmpleadoAsistencia ea : lista) {
/* 646:608 */         this.totalDiasFalta += ea.getCantidadDiasFalta();
/* 647:    */       }
/* 648:    */     }
/* 649:    */   }
/* 650:    */   
/* 651:    */   public Boolean getProcesaDiasFalta()
/* 652:    */   {
/* 653:614 */     if (this.procesaDiasFalta == null) {
/* 654:615 */       this.procesaDiasFalta = ParametrosSistema.getProcesaDiasFalta(AppUtil.getOrganizacion().getId());
/* 655:    */     }
/* 656:616 */     return this.procesaDiasFalta;
/* 657:    */   }
/* 658:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.procesos.ProcesarHorasExtraBean
 * JD-Core Version:    0.7.0.1
 */