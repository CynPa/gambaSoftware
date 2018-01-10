/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Departamento;
/*   9:    */ import com.asinfo.as2.entities.Empleado;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PagoRol;
/*  12:    */ import com.asinfo.as2.entities.Quincena;
/*  13:    */ import com.asinfo.as2.entities.Rubro;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  16:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  20:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Arrays;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ import org.primefaces.component.datatable.DataTable;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class ReporteRubroEmpleadoBean
/*  40:    */   extends AbstractBaseReportBean
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1L;
/*  43:    */   @EJB
/*  44:    */   private ServicioReporteNomina servicioReporteNomina;
/*  45:    */   @EJB
/*  46:    */   private ServicioPagoRol servicioPagoRol;
/*  47:    */   @EJB
/*  48:    */   private ServicioDepartamento servicioDepartamento;
/*  49:    */   @EJB
/*  50:    */   private ServicioSucursal servicioSucursal;
/*  51:    */   @EJB
/*  52:    */   private ServicioRubro servicioRubro;
/*  53:    */   @EJB
/*  54:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  55:    */   private PagoRol pagoRolDesde;
/*  56:    */   private PagoRol pagoRolHasta;
/*  57:    */   private Sucursal sucursal;
/*  58:    */   private Departamento departamento;
/*  59:    */   private Empleado empleado;
/*  60:    */   private int movimiento;
/*  61:    */   private List<Rubro> listaRubros;
/*  62:    */   private List<PagoRol> listaPagoRol;
/*  63:    */   private List<SelectItem> listaItems;
/*  64:    */   private TipoReporte tipoReporte;
/*  65:    */   private Rubro[] rubrosSeleccionados;
/*  66:    */   private boolean indicadorRubroEmpleado;
/*  67:    */   private boolean indicadorRubroProvisionEmpleado;
/*  68:    */   private List<Sucursal> listaSucursal;
/*  69:    */   private List<Departamento> listaDepartamento;
/*  70:    */   private List<SelectItem> listaPagoRolDesde;
/*  71:    */   private List<SelectItem> listaPagoRolHasta;
/*  72:    */   private List<Rubro> listaRubro;
/*  73:    */   private CategoriaEmpresa categoriaEmpresa;
/*  74:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  75:    */   private DataTable dtRubro;
/*  76:    */   
/*  77:    */   static enum TipoReporte
/*  78:    */   {
/*  79: 60 */     NORMAL("Normal"),  DETALLADO("Detallado"),  MENSUAL_DETALLADO("Mensual Detallado"),  MENSUAL_RESUMIDO("Mensual Resumido");
/*  80:    */     
/*  81:    */     private String nombre;
/*  82:    */     
/*  83:    */     private TipoReporte(String nombre)
/*  84:    */     {
/*  85: 64 */       this.nombre = nombre;
/*  86:    */     }
/*  87:    */     
/*  88:    */     public String getNombre()
/*  89:    */     {
/*  90: 68 */       return this.nombre;
/*  91:    */     }
/*  92:    */     
/*  93:    */     public void setNombre(String nombre)
/*  94:    */     {
/*  95: 72 */       this.nombre = nombre;
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected JRDataSource getJRDataSource()
/* 100:    */   {
/* 101:141 */     cargarRubro();
/* 102:    */     
/* 103:143 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 104:144 */     JRDataSource ds = null;
/* 105:146 */     if (cargarListaPagoRol()) {
/* 106:148 */       if (this.tipoReporte.nombre.equals("Mensual Resumido"))
/* 107:    */       {
/* 108:150 */         listaDatosReporte = this.servicioReporteNomina.getReporteRubroMensual(getSucursal().getId(), getEmpleado().getId(), 
/* 109:151 */           getDepartamento().getId(), getListaPagoRol(), getListaRubros(), getMovimiento(), this.indicadorRubroEmpleado, this.indicadorRubroProvisionEmpleado, 
/* 110:152 */           AppUtil.getOrganizacion().getId(), getCategoriaEmpresa());
/* 111:    */         
/* 112:154 */         String[] fields = { "f_rubro", "f_mes", "f_valor" };
/* 113:155 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 114:    */       }
/* 115:157 */       else if (this.tipoReporte.nombre.equals("Mensual Detallado"))
/* 116:    */       {
/* 117:159 */         listaDatosReporte = this.servicioReporteNomina.getReporteRubroMensualDetallado(getSucursal().getId(), getEmpleado().getId(), 
/* 118:160 */           getDepartamento().getId(), getListaPagoRol(), getListaRubros(), getMovimiento(), this.indicadorRubroEmpleado, this.indicadorRubroProvisionEmpleado, 
/* 119:161 */           AppUtil.getOrganizacion().getId(), getCategoriaEmpresa());
/* 120:    */         
/* 121:    */ 
/* 122:164 */         String[] fields = { "f_empleado", "f_rubro", "f_mes", "f_valor", "f_anio" };
/* 123:165 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127:169 */         listaDatosReporte = this.servicioReporteNomina.getReporteRubroEmpleado(getSucursal().getId(), getEmpleado().getId(), 
/* 128:170 */           getDepartamento().getId(), getListaPagoRol(), getListaRubros(), getMovimiento(), this.indicadorRubroEmpleado, this.indicadorRubroProvisionEmpleado, 
/* 129:171 */           AppUtil.getOrganizacion().getId(), getCategoriaEmpresa());
/* 130:    */         
/* 131:173 */         String[] fields = { "f_empleado", "f_identificacion", "f_operacion", "f_rubro", "f_ingreso", "f_egreso", "f_fechaPagoRol", "f_mes", "f_anio", "f_valor", "f_nombreEmpleado", "f_tiempo", "f_indicadorTiempo" };
/* 132:    */         
/* 133:175 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 134:    */       }
/* 135:    */     }
/* 136:180 */     return ds;
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected Map<String, Object> getReportParameters()
/* 140:    */   {
/* 141:191 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 142:192 */     reportParameters.put("ReportTitle", "Reporte Rubro Empleado");
/* 143:193 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(getPagoRolDesde().getFecha()));
/* 144:194 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(getPagoRolHasta().getFecha()));
/* 145:195 */     reportParameters.put("Sucursal", getSucursal().getNombre());
/* 146:196 */     reportParameters.put("Departamento", getDepartamento() == null ? "Todos" : getDepartamento().getNombre());
/* 147:197 */     return reportParameters;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String execute()
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:207 */       super.prepareReport();
/* 155:    */     }
/* 156:    */     catch (JRException e)
/* 157:    */     {
/* 158:209 */       LOG.info("Error JRException");
/* 159:210 */       e.printStackTrace();
/* 160:211 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 161:    */     }
/* 162:    */     catch (IOException e)
/* 163:    */     {
/* 164:213 */       LOG.info("Error IOException");
/* 165:214 */       e.printStackTrace();
/* 166:215 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 167:    */     }
/* 168:218 */     return null;
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected String getCompileFileName()
/* 172:    */   {
/* 173:228 */     String nombreReporte = "";
/* 174:229 */     switch (1.$SwitchMap$com$asinfo$as2$nomina$reportes$ReporteRubroEmpleadoBean$TipoReporte[this.tipoReporte.ordinal()])
/* 175:    */     {
/* 176:    */     case 1: 
/* 177:231 */       if (this.indicadorRubroEmpleado) {
/* 178:232 */         nombreReporte = "reporteEmpleadoRubro";
/* 179:    */       } else {
/* 180:234 */         nombreReporte = "reporteRubroEmpleado";
/* 181:    */       }
/* 182:236 */       break;
/* 183:    */     case 2: 
/* 184:239 */       nombreReporte = "reporteRubroEmpleadoDetallado";
/* 185:240 */       break;
/* 186:    */     case 3: 
/* 187:243 */       nombreReporte = "reporteRubroEmpleadoMensualDetallado";
/* 188:244 */       break;
/* 189:    */     case 4: 
/* 190:247 */       nombreReporte = "reporteRubroEmpleadoMensualResumido";
/* 191:    */     }
/* 192:251 */     return nombreReporte;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String cargarEmpleado()
/* 196:    */   {
/* 197:258 */     return "";
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String cargarRubro()
/* 201:    */   {
/* 202:262 */     this.listaRubros = new ArrayList();
/* 203:263 */     if (this.rubrosSeleccionados != null) {
/* 204:264 */       this.listaRubros = Arrays.asList(this.rubrosSeleccionados);
/* 205:    */     }
/* 206:266 */     return "";
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String limpiarRubro()
/* 210:    */   {
/* 211:270 */     this.rubrosSeleccionados = null;
/* 212:271 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   private boolean cargarListaPagoRol()
/* 216:    */   {
/* 217:276 */     boolean indicador = false;
/* 218:    */     
/* 219:278 */     Date fechaDesde = FuncionesUtiles.sumarFechaDiasMeses(getPagoRolDesde().getFecha(), -1);
/* 220:279 */     Date fechaHasta = FuncionesUtiles.sumarFechaDiasMeses(getPagoRolHasta().getFecha(), 1);
/* 221:281 */     if (fechaDesde.compareTo(fechaHasta) == -1)
/* 222:    */     {
/* 223:282 */       List<PagoRol> lista = this.servicioPagoRol.obtenerPagoRol(fechaDesde, fechaHasta, getSucursal().getId());
/* 224:283 */       if (!lista.isEmpty())
/* 225:    */       {
/* 226:284 */         this.listaPagoRol = lista;
/* 227:285 */         indicador = true;
/* 228:    */       }
/* 229:    */     }
/* 230:288 */     return indicador;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public PagoRol getPagoRolDesde()
/* 234:    */   {
/* 235:301 */     if (this.pagoRolDesde == null) {
/* 236:302 */       this.pagoRolDesde = new PagoRol();
/* 237:    */     }
/* 238:304 */     return this.pagoRolDesde;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setPagoRolDesde(PagoRol pagoRolDesde)
/* 242:    */   {
/* 243:314 */     this.pagoRolDesde = pagoRolDesde;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public PagoRol getPagoRolHasta()
/* 247:    */   {
/* 248:323 */     if (this.pagoRolHasta == null) {
/* 249:324 */       this.pagoRolHasta = new PagoRol();
/* 250:    */     }
/* 251:326 */     return this.pagoRolHasta;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setPagoRolHasta(PagoRol pagoRolHasta)
/* 255:    */   {
/* 256:336 */     this.pagoRolHasta = pagoRolHasta;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Sucursal getSucursal()
/* 260:    */   {
/* 261:345 */     if (this.sucursal == null) {
/* 262:346 */       this.sucursal = new Sucursal();
/* 263:    */     }
/* 264:348 */     return this.sucursal;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setSucursal(Sucursal sucursal)
/* 268:    */   {
/* 269:358 */     this.sucursal = sucursal;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Departamento getDepartamento()
/* 273:    */   {
/* 274:367 */     if (this.departamento == null) {
/* 275:368 */       this.departamento = new Departamento();
/* 276:    */     }
/* 277:370 */     return this.departamento;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setDepartamento(Departamento departamento)
/* 281:    */   {
/* 282:380 */     this.departamento = departamento;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public Empleado getEmpleado()
/* 286:    */   {
/* 287:389 */     if (this.empleado == null) {
/* 288:390 */       this.empleado = new Empleado();
/* 289:    */     }
/* 290:392 */     return this.empleado;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setEmpleado(Empleado empleado)
/* 294:    */   {
/* 295:402 */     this.empleado = empleado;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Rubro[] getRubrosSeleccionados()
/* 299:    */   {
/* 300:411 */     return this.rubrosSeleccionados;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setRubrosSeleccionados(Rubro[] rubrosSeleccionados)
/* 304:    */   {
/* 305:421 */     this.rubrosSeleccionados = rubrosSeleccionados;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public int getMovimiento()
/* 309:    */   {
/* 310:430 */     return this.movimiento;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setMovimiento(int movimiento)
/* 314:    */   {
/* 315:440 */     this.movimiento = movimiento;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public boolean isIndicadorRubroEmpleado()
/* 319:    */   {
/* 320:449 */     return this.indicadorRubroEmpleado;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setIndicadorRubroEmpleado(boolean indicadorRubroEmpleado)
/* 324:    */   {
/* 325:459 */     this.indicadorRubroEmpleado = indicadorRubroEmpleado;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List<SelectItem> getListaPagoRolDesde()
/* 329:    */   {
/* 330:468 */     if (this.listaPagoRolDesde == null)
/* 331:    */     {
/* 332:469 */       List<PagoRol> lista = new ArrayList();
/* 333:470 */       Map<String, String> filters = new HashMap();
/* 334:471 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 335:472 */       filters.put("indicadorFiniquito", "false");
/* 336:    */       
/* 337:474 */       lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 338:475 */       if (this.listaPagoRolDesde == null)
/* 339:    */       {
/* 340:476 */         this.listaPagoRolDesde = new ArrayList();
/* 341:477 */         for (PagoRol pagoRol : lista)
/* 342:    */         {
/* 343:483 */           String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 344:484 */           SelectItem item = new SelectItem(pagoRol, label);
/* 345:485 */           this.listaPagoRolDesde.add(item);
/* 346:    */         }
/* 347:    */       }
/* 348:    */     }
/* 349:490 */     return this.listaPagoRolDesde;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public List<SelectItem> getListaPagoRolHasta()
/* 353:    */   {
/* 354:500 */     if (this.listaPagoRolHasta == null)
/* 355:    */     {
/* 356:501 */       List<PagoRol> lista = new ArrayList();
/* 357:502 */       Map<String, String> filters = new HashMap();
/* 358:503 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 359:504 */       filters.put("indicadorFiniquito", "false");
/* 360:    */       
/* 361:506 */       lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 362:507 */       if (this.listaPagoRolHasta == null)
/* 363:    */       {
/* 364:508 */         this.listaPagoRolHasta = new ArrayList();
/* 365:509 */         for (PagoRol pagoRol : lista)
/* 366:    */         {
/* 367:515 */           String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 368:516 */           SelectItem item = new SelectItem(pagoRol, label);
/* 369:517 */           this.listaPagoRolHasta.add(item);
/* 370:    */         }
/* 371:    */       }
/* 372:    */     }
/* 373:522 */     return this.listaPagoRolHasta;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public List<Sucursal> getListaSucursal()
/* 377:    */   {
/* 378:532 */     if (this.listaSucursal == null) {
/* 379:533 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 380:    */     }
/* 381:535 */     return this.listaSucursal;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public List<Departamento> getListaDepartamento()
/* 385:    */   {
/* 386:544 */     if (this.listaDepartamento == null) {
/* 387:545 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 388:    */     }
/* 389:547 */     return this.listaDepartamento;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public List<Rubro> getListaRubro()
/* 393:    */   {
/* 394:556 */     if (this.listaRubro == null) {
/* 395:557 */       this.listaRubro = this.servicioRubro.obtenerListaCombo("", false, null);
/* 396:    */     }
/* 397:559 */     return this.listaRubro;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setListaRubro(List<Rubro> listaRubro)
/* 401:    */   {
/* 402:569 */     this.listaRubro = listaRubro;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public List<Rubro> getListaRubros()
/* 406:    */   {
/* 407:578 */     if (this.listaRubros == null) {
/* 408:579 */       this.listaRubros = new ArrayList();
/* 409:    */     }
/* 410:581 */     return this.listaRubros;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setListaRubros(List<Rubro> listaRubros)
/* 414:    */   {
/* 415:591 */     this.listaRubros = listaRubros;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public DataTable getDtRubro()
/* 419:    */   {
/* 420:600 */     return this.dtRubro;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setDtRubro(DataTable dtRubro)
/* 424:    */   {
/* 425:610 */     this.dtRubro = dtRubro;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public List<PagoRol> getListaPagoRol()
/* 429:    */   {
/* 430:619 */     if (this.listaPagoRol == null) {
/* 431:620 */       this.listaPagoRol = new ArrayList();
/* 432:    */     }
/* 433:622 */     return this.listaPagoRol;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setListaPagoRol(List<PagoRol> listaPagoRol)
/* 437:    */   {
/* 438:632 */     this.listaPagoRol = listaPagoRol;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public List<SelectItem> getListaItems()
/* 442:    */   {
/* 443:636 */     this.listaItems = new ArrayList();
/* 444:637 */     for (TipoReporte tipoReporte : TipoReporte.values())
/* 445:    */     {
/* 446:638 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 447:639 */       this.listaItems.add(item);
/* 448:    */     }
/* 449:641 */     return this.listaItems;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public TipoReporte getTipoReporte()
/* 453:    */   {
/* 454:645 */     return this.tipoReporte;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 458:    */   {
/* 459:649 */     this.tipoReporte = tipoReporte;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public boolean isIndicadorRubroProvisionEmpleado()
/* 463:    */   {
/* 464:653 */     return this.indicadorRubroProvisionEmpleado;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void setIndicadorRubroProvisionEmpleado(boolean indicadorRubroProvisionEmpleado)
/* 468:    */   {
/* 469:657 */     this.indicadorRubroProvisionEmpleado = indicadorRubroProvisionEmpleado;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 473:    */   {
/* 474:667 */     if (this.listaCategoriaEmpresa == null) {
/* 475:668 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/* 476:    */     }
/* 477:670 */     return this.listaCategoriaEmpresa;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 481:    */   {
/* 482:680 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 486:    */   {
/* 487:687 */     return this.categoriaEmpresa;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 491:    */   {
/* 492:694 */     this.categoriaEmpresa = categoriaEmpresa;
/* 493:    */   }
/* 494:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteRubroEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */