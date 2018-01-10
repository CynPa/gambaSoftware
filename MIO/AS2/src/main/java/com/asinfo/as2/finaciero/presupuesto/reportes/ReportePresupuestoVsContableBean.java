/*   1:    */ package com.asinfo.as2.finaciero.presupuesto.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.DimensionContable;
/*   6:    */ import com.asinfo.as2.entities.Ejercicio;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.entities.Periodo;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  12:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  16:    */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria;
/*  17:    */ import com.asinfo.as2.financiero.presupuesto.reportes.servicio.ServicioReportePresupuesto;
/*  18:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class ReportePresupuestoVsContableBean
/*  39:    */   extends AbstractBaseReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioReportePresupuesto servicioReportePresupuesto;
/*  44:    */   @EJB
/*  45:    */   private ServicioSucursal servicioSucursal;
/*  46:    */   @EJB
/*  47:    */   private ServicioEjercicio servicioEjercicio;
/*  48:    */   @EJB
/*  49:    */   private ServicioPeriodo servicioPeriodo;
/*  50:    */   @EJB
/*  51:    */   private ServicioPartidaPresupuestaria servicioPartidaPresupuestaria;
/*  52:    */   @EJB
/*  53:    */   private ServicioDimensionContable servicioDimensionContable;
/*  54:    */   private PartidaPresupuestaria[] partidasPresupuestariasSeleccionadas;
/*  55:    */   private boolean indicadorNIIF;
/*  56:    */   private List<Sucursal> listaSucursal;
/*  57:    */   private List<Ejercicio> listaEjercicio;
/*  58:    */   
/*  59:    */   static enum TIPO_REPORTE
/*  60:    */   {
/*  61: 58 */     CONTABLE("Contable"),  COMPROMETIDO("Comprometido"),  DETALLADO("Detallado");
/*  62:    */     
/*  63:    */     private String nombre;
/*  64:    */     
/*  65:    */     private TIPO_REPORTE(String nombre)
/*  66:    */     {
/*  67: 68 */       this.nombre = nombre;
/*  68:    */     }
/*  69:    */     
/*  70:    */     public String getNombre()
/*  71:    */     {
/*  72: 77 */       return this.nombre;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public void setNombre(String nombre)
/*  76:    */     {
/*  77: 86 */       this.nombre = nombre;
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:115 */   private List<Periodo> listaPeriodo = new ArrayList();
/*  82:    */   private List<DimensionContable> listaDimensionContable;
/*  83:    */   private DataTable dtPartidaPresupuestaria;
/*  84:    */   private Sucursal sucursal;
/*  85:    */   private Ejercicio ejercicio;
/*  86:    */   private Periodo periodo;
/*  87:    */   private DimensionContable dimensionContable;
/*  88:    */   private Mes mesSeleccionado;
/*  89:124 */   private TIPO_REPORTE tipoReporte = TIPO_REPORTE.CONTABLE;
/*  90:    */   private String nivel;
/*  91:    */   private String[] arregloMascara;
/*  92:127 */   private int cantDigitos = 0;
/*  93:    */   private boolean indicadorAcumulado;
/*  94:    */   
/*  95:    */   protected JRDataSource getJRDataSource()
/*  96:    */   {
/*  97:139 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  98:140 */     JRDataSource ds = null;
/*  99:    */     
/* 100:142 */     listaDatosReporte = this.servicioReportePresupuesto.getReportePresupuestoComparativo(getEjercicio(), getMesSeleccionado(), getSucursal(), 
/* 101:143 */       AppUtil.getOrganizacion().getIdOrganizacion(), getDimensionPresupuesto(), getDimensionContable(), getListaDimensionContable(), 
/* 102:144 */       getTipoReporte().ordinal(), AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 103:146 */     if (this.mesSeleccionado == null)
/* 104:    */     {
/* 105:147 */       if (this.tipoReporte.ordinal() == 0)
/* 106:    */       {
/* 107:148 */         String[] fields = { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorEnero", "f_saldoRealEnero", "f_valorFebrero", "f_saldoRealFebrero", "f_valorMarzo", "f_saldoRealMarzo", "f_valorAbril", "f_saldoRealAbril", "f_valorMayo", "f_saldoRealMayo", "f_valorJunio", "f_saldoRealJunio", "f_valorJulio", "f_saldoRealJulio", "f_valorAgosto", "f_saldoRealAgosto", "f_valorSeptiembre", "f_saldoRealSeptiembre", "f_valorOctubre", "f_saldoRealOctubre", "f_valorNoviembre", "f_saldoRealNoviembre", "f_valorDiciembre", "f_saldoRealDiciembre" };
/* 108:    */         
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:154 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 114:    */       }
/* 115:155 */       else if (this.tipoReporte.ordinal() == 1)
/* 116:    */       {
/* 117:156 */         String[] fields = { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorEnero", "f_saldoComprometidoEnero", "f_valorFebrero", "f_saldoComprometidoFebrero", "f_valorMarzo", "f_saldoComprometidoMarzo", "f_valorAbril", "f_saldoComprometidoAbril", "f_valorMayo", "f_saldoComprometidoMayo", "f_valorJunio", "f_saldoComprometidoJunio", "f_valorJulio", "f_saldoComprometidoJulio", "f_valorAgosto", "f_saldoComprometidoAgosto", "f_valorSeptiembre", "f_saldoComprometidoSeptiembre", "f_valorOctubre", "f_saldoComprometidoOctubre", "f_valorNoviembre", "f_saldoComprometidoNoviembre", "f_valorDiciembre", "f_saldoComprometidoDiciembre" };
/* 118:    */         
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:164 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 126:    */       }
/* 127:    */       else
/* 128:    */       {
/* 129:166 */         String[] fields = { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorEnero", "f_saldoComprometidoEnero", "f_saldoRealEnero", "f_valorFebrero", "f_saldoComprometidoFebrero", "f_saldoRealFebrero", "f_valorMarzo", "f_saldoComprometidoMarzo", "f_saldoRealMarzo", "f_valorAbril", "f_saldoComprometidoAbril", "f_saldoRealAbril", "f_valorMayo", "f_saldoComprometidoMayo", "f_saldoRealMayo", "f_valorJunio", "f_saldoComprometidoJunio", "f_saldoRealJunio", "f_valorJulio", "f_saldoComprometidoJulio", "f_saldoRealJulio", "f_valorAgosto", "f_saldoComprometidoAgosto", "f_saldoRealAgosto", "f_valorSeptiembre", "f_saldoComprometidoSeptiembre", "f_saldoRealSeptiembre", "f_valorOctubre", "f_saldoComprometidoOctubre", "f_saldoRealOctubre", "f_valorNoviembre", "f_saldoComprometidoNoviembre", "f_saldoRealNoviembre", "f_valorDiciembre", "f_saldoComprometidoDiciembre", "f_saldoRealDiciembre" };
/* 130:    */         
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:176 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 140:    */       }
/* 141:    */     }
/* 142:179 */     else if (this.tipoReporte.ordinal() == 0)
/* 143:    */     {
/* 144:180 */       String[] fields = { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorMes", "f_saldoRealMes" };
/* 145:    */       
/* 146:    */ 
/* 147:183 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 148:    */     }
/* 149:184 */     else if (this.tipoReporte.ordinal() == 1)
/* 150:    */     {
/* 151:185 */       String[] fields = { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorMes", "f_saldoComprometidoMes" };
/* 152:    */       
/* 153:    */ 
/* 154:188 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 155:    */     }
/* 156:    */     else
/* 157:    */     {
/* 158:190 */       String[] fields = { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorMes", "f_saldoComprometidoMes", "f_saldoRealMes" };
/* 159:    */       
/* 160:    */ 
/* 161:193 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 162:    */     }
/* 163:206 */     return ds;
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected String getCompileFileName()
/* 167:    */   {
/* 168:216 */     if (this.mesSeleccionado == null)
/* 169:    */     {
/* 170:217 */       if (this.tipoReporte.ordinal() == 0) {
/* 171:218 */         return "reportePresupuestoComparativoContable";
/* 172:    */       }
/* 173:219 */       if (this.tipoReporte.ordinal() == 1) {
/* 174:220 */         return "reportePresupuestoComparativoComprometido";
/* 175:    */       }
/* 176:222 */       return "reportePresupuestoComparativoDetallado";
/* 177:    */     }
/* 178:225 */     if (this.tipoReporte.ordinal() == 0) {
/* 179:226 */       return "reportePresupuestoComparativoContableMes";
/* 180:    */     }
/* 181:227 */     if (this.tipoReporte.ordinal() == 1) {
/* 182:228 */       return "reportePresupuestoComparativoComprometidoMes";
/* 183:    */     }
/* 184:230 */     return "reportePresupuestoComparativoDetalladoMes";
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected Map<String, Object> getReportParameters()
/* 188:    */   {
/* 189:242 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 190:247 */     if (this.tipoReporte.ordinal() == 0) {
/* 191:248 */       reportParameters.put("ReportTitle", "Presupuesto Comparativo Contable");
/* 192:249 */     } else if (this.tipoReporte.ordinal() == 1) {
/* 193:250 */       reportParameters.put("ReportTitle", "Presupuesto Comparativo Comprometido");
/* 194:    */     } else {
/* 195:252 */       reportParameters.put("ReportTitle", "Presupuesto Comparativo Detallado");
/* 196:    */     }
/* 197:254 */     if (this.mesSeleccionado != null) {
/* 198:255 */       reportParameters.put("mes", this.mesSeleccionado.getNombre());
/* 199:    */     }
/* 200:256 */     reportParameters.put("ejercicio", this.ejercicio.getNombre());
/* 201:257 */     reportParameters.put("periodo", this.periodo == null ? "Todos" : this.periodo.getNombre());
/* 202:258 */     reportParameters.put("Total", "Total");
/* 203:259 */     return reportParameters;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String execute()
/* 207:    */   {
/* 208:    */     try
/* 209:    */     {
/* 210:269 */       super.prepareReport();
/* 211:    */     }
/* 212:    */     catch (JRException e)
/* 213:    */     {
/* 214:271 */       LOG.info("Error JRException");
/* 215:272 */       e.printStackTrace();
/* 216:273 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 217:    */     }
/* 218:    */     catch (IOException e)
/* 219:    */     {
/* 220:275 */       LOG.info("Error IOException");
/* 221:276 */       e.printStackTrace();
/* 222:277 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 223:    */     }
/* 224:280 */     return null;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void seleccionarPartidaPrespuestaria()
/* 228:    */   {
/* 229:284 */     this.partidasPresupuestariasSeleccionadas = null;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void cargarPartidaPresupuestaria() {}
/* 233:    */   
/* 234:    */   public void actualizarPeriodo()
/* 235:    */   {
/* 236:292 */     HashMap<String, String> filters = new HashMap();
/* 237:293 */     filters.put("ejercicio.idEjercicio", "" + getEjercicio().getId());
/* 238:294 */     filters.put("activo", "true");
/* 239:295 */     this.listaPeriodo = this.servicioPeriodo.obtenerListaCombo("fechaDesde", true, filters);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean isIndicadorNIIF()
/* 243:    */   {
/* 244:308 */     return this.indicadorNIIF;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setIndicadorNIIF(boolean indicadorNIIF)
/* 248:    */   {
/* 249:318 */     this.indicadorNIIF = indicadorNIIF;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean isIndicadorAcumulado()
/* 253:    */   {
/* 254:327 */     return this.indicadorAcumulado;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setIndicadorAcumulado(boolean indicadorAcumulado)
/* 258:    */   {
/* 259:337 */     this.indicadorAcumulado = indicadorAcumulado;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public List<Sucursal> getListaSucursal()
/* 263:    */   {
/* 264:346 */     if (this.listaSucursal == null) {
/* 265:347 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 266:    */     }
/* 267:349 */     return this.listaSucursal;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 271:    */   {
/* 272:359 */     this.listaSucursal = listaSucursal;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public PartidaPresupuestaria[] getPartidasPresupuestariasSeleccionadas()
/* 276:    */   {
/* 277:368 */     return this.partidasPresupuestariasSeleccionadas;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setPartidasPresupuestariasSeleccionadas(PartidaPresupuestaria[] partidasPresupuestariasSeleccionadas)
/* 281:    */   {
/* 282:378 */     this.partidasPresupuestariasSeleccionadas = partidasPresupuestariasSeleccionadas;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public DataTable getDtPartidaPresupuestaria()
/* 286:    */   {
/* 287:387 */     return this.dtPartidaPresupuestaria;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setDtPartidaPresupuestaria(DataTable dtPartidaPresupuestaria)
/* 291:    */   {
/* 292:397 */     this.dtPartidaPresupuestaria = dtPartidaPresupuestaria;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Sucursal getSucursal()
/* 296:    */   {
/* 297:406 */     return this.sucursal;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setSucursal(Sucursal sucursal)
/* 301:    */   {
/* 302:416 */     this.sucursal = sucursal;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public Ejercicio getEjercicio()
/* 306:    */   {
/* 307:425 */     return this.ejercicio;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setEjercicio(Ejercicio ejercicio)
/* 311:    */   {
/* 312:435 */     this.ejercicio = ejercicio;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public Periodo getPeriodo()
/* 316:    */   {
/* 317:444 */     if (this.periodo == null) {
/* 318:445 */       this.periodo = new Periodo();
/* 319:    */     }
/* 320:447 */     return this.periodo;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setPeriodo(Periodo periodo)
/* 324:    */   {
/* 325:457 */     this.periodo = periodo;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List<Periodo> getListaPeriodo()
/* 329:    */   {
/* 330:466 */     return this.listaPeriodo;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public List<Ejercicio> getListaEjercicio()
/* 334:    */   {
/* 335:475 */     if (this.listaEjercicio == null) {
/* 336:476 */       this.listaEjercicio = this.servicioEjercicio.obtenerListaCombo("nombre", true, null);
/* 337:    */     }
/* 338:478 */     return this.listaEjercicio;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public List<SelectItem> getListaMes()
/* 342:    */   {
/* 343:482 */     ArrayList<SelectItem> lista = new ArrayList();
/* 344:483 */     for (Mes mes : Mes.values()) {
/* 345:484 */       lista.add(new SelectItem(mes, mes.getNombre()));
/* 346:    */     }
/* 347:486 */     return lista;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public Mes getMesSeleccionado()
/* 351:    */   {
/* 352:490 */     return this.mesSeleccionado;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setMesSeleccionado(Mes mesSeleccionado)
/* 356:    */   {
/* 357:494 */     this.mesSeleccionado = mesSeleccionado;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public List<PartidaPresupuestaria> getListaPartidaPresupuestaria()
/* 361:    */   {
/* 362:505 */     List<PartidaPresupuestaria> lista = this.servicioPartidaPresupuestaria.obtenerPartidaPresupuestariaPorUsuario(AppUtil.getOrganizacion().getId(), 
/* 363:506 */       AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 364:507 */     return lista;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public List<DimensionContable> getListaDimensionContable()
/* 368:    */   {
/* 369:511 */     if ((getNivel() == null) || (getNivel().isEmpty()))
/* 370:    */     {
/* 371:512 */       this.listaDimensionContable = this.servicioDimensionContable.obtenerDimensionContablePorUsuario(AppUtil.getOrganizacion().getId(), 
/* 372:513 */         AppUtil.getUsuarioEnSesion().getIdUsuario(), 0, true);
/* 373:    */     }
/* 374:    */     else
/* 375:    */     {
/* 376:515 */       this.cantDigitos = 0;
/* 377:516 */       for (int i = 0; i <= this.nivel.split("\\.").length - 1; i++) {
/* 378:517 */         this.cantDigitos += this.arregloMascara[i].length();
/* 379:    */       }
/* 380:519 */       this.cantDigitos += this.nivel.split("\\.").length;
/* 381:520 */       this.listaDimensionContable = this.servicioDimensionContable.obtenerDimensionContablePorUsuario(AppUtil.getOrganizacion().getId(), 
/* 382:521 */         AppUtil.getUsuarioEnSesion().getIdUsuario(), this.cantDigitos, true);
/* 383:    */     }
/* 384:523 */     return this.listaDimensionContable;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public DimensionContable getDimensionContable()
/* 388:    */   {
/* 389:527 */     return this.dimensionContable;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 393:    */   {
/* 394:531 */     this.dimensionContable = dimensionContable;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public List<SelectItem> getListaTipoReporte()
/* 398:    */   {
/* 399:552 */     List<SelectItem> lista = new ArrayList();
/* 400:553 */     for (TIPO_REPORTE tipo : TIPO_REPORTE.values()) {
/* 401:554 */       lista.add(new SelectItem(tipo, tipo.getNombre()));
/* 402:    */     }
/* 403:556 */     return lista;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public TIPO_REPORTE getTipoReporte()
/* 407:    */   {
/* 408:560 */     return this.tipoReporte;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void setTipoReporte(TIPO_REPORTE tipoReporte)
/* 412:    */   {
/* 413:564 */     this.tipoReporte = tipoReporte;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public List<SelectItem> getListaNivelDimensionContable()
/* 417:    */   {
/* 418:567 */     return getNivelDimension(getDimensionPresupuesto());
/* 419:    */   }
/* 420:    */   
/* 421:    */   public String getDimensionPresupuesto()
/* 422:    */   {
/* 423:571 */     OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/* 424:572 */     String numeroDimension = "";
/* 425:573 */     if ((!aux.getNombreDimension1().equals("")) && (aux.isIndicadorUsoPresupuestoDimension1()))
/* 426:    */     {
/* 427:574 */       this.arregloMascara = aux.getMascaraDimension1().split("\\.");
/* 428:575 */       numeroDimension = "1";
/* 429:    */     }
/* 430:576 */     else if ((!aux.getNombreDimension2().equals("")) && (aux.isIndicadorUsoPresupuestoDimension2()))
/* 431:    */     {
/* 432:577 */       this.arregloMascara = aux.getMascaraDimension2().split("\\.");
/* 433:578 */       numeroDimension = "2";
/* 434:    */     }
/* 435:579 */     else if ((!aux.getNombreDimension3().equals("")) && (aux.isIndicadorUsoPresupuestoDimension3()))
/* 436:    */     {
/* 437:580 */       this.arregloMascara = aux.getMascaraDimension3().split("\\.");
/* 438:581 */       numeroDimension = "3";
/* 439:    */     }
/* 440:582 */     else if ((!aux.getNombreDimension4().equals("")) && (aux.isIndicadorUsoPresupuestoDimension4()))
/* 441:    */     {
/* 442:583 */       this.arregloMascara = aux.getMascaraDimension4().split("\\.");
/* 443:584 */       numeroDimension = "4";
/* 444:    */     }
/* 445:585 */     else if ((!aux.getNombreDimension5().equals("")) && (aux.isIndicadorUsoPresupuestoDimension5()))
/* 446:    */     {
/* 447:586 */       this.arregloMascara = aux.getMascaraDimension5().split("\\.");
/* 448:587 */       numeroDimension = "5";
/* 449:    */     }
/* 450:589 */     return numeroDimension;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public String getNivel()
/* 454:    */   {
/* 455:593 */     return this.nivel;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void setNivel(String nivel)
/* 459:    */   {
/* 460:597 */     this.nivel = nivel;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public String actualizarDimension()
/* 464:    */   {
/* 465:600 */     setDimensionContable(null);
/* 466:601 */     return null;
/* 467:    */   }
/* 468:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.reportes.ReportePresupuestoVsContableBean
 * JD-Core Version:    0.7.0.1
 */