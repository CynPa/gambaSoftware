/*    1:     */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.CalculoImpuestoRenta;
/*    4:     */ import com.asinfo.as2.dao.DetalleHistoricoEmpleadoDao;
/*    5:     */ import com.asinfo.as2.dao.DetallePagoCuotaPrestamoDao;
/*    6:     */ import com.asinfo.as2.dao.PagoRolDao;
/*    7:     */ import com.asinfo.as2.dao.PagoRolEmpleadoDao;
/*    8:     */ import com.asinfo.as2.dao.PagoRolEmpleadoRubroDao;
/*    9:     */ import com.asinfo.as2.dao.PrestamoDao;
/*   10:     */ import com.asinfo.as2.dao.RubroOtraEmpresaDao;
/*   11:     */ import com.asinfo.as2.dao.SubsidioEmpleadoDao;
/*   12:     */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*   13:     */ import com.asinfo.as2.entities.DetallePagoCuotaPrestamo;
/*   14:     */ import com.asinfo.as2.entities.DetallePrestamo;
/*   15:     */ import com.asinfo.as2.entities.Empleado;
/*   16:     */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   17:     */ import com.asinfo.as2.entities.ImpuestoRentaSRI;
/*   18:     */ import com.asinfo.as2.entities.PagoEmpleado;
/*   19:     */ import com.asinfo.as2.entities.PagoRol;
/*   20:     */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   21:     */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*   22:     */ import com.asinfo.as2.entities.Rubro;
/*   23:     */ import com.asinfo.as2.entities.RubroOtraEmpresa;
/*   24:     */ import com.asinfo.as2.entities.SubsidioEmpleado;
/*   25:     */ import com.asinfo.as2.entities.TipoContrato;
/*   26:     */ import com.asinfo.as2.entities.TipoSubsidio;
/*   27:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioGastoDeducibleSRI;
/*   28:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioImpuestoRentaSRI;
/*   29:     */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   30:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*   31:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*   32:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*   33:     */ import com.asinfo.as2.util.AppUtil;
/*   34:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   35:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   36:     */ import java.math.BigDecimal;
/*   37:     */ import java.math.RoundingMode;
/*   38:     */ import java.util.ArrayList;
/*   39:     */ import java.util.Date;
/*   40:     */ import java.util.HashMap;
/*   41:     */ import java.util.List;
/*   42:     */ import java.util.Map;
/*   43:     */ import javax.ejb.EJB;
/*   44:     */ import javax.ejb.Stateless;
/*   45:     */ 
/*   46:     */ @Stateless
/*   47:     */ public class ServicioPagoRolEmpleadoImpl
/*   48:     */   implements ServicioPagoRolEmpleado
/*   49:     */ {
/*   50:     */   @EJB
/*   51:     */   private PagoRolDao pagoRolDao;
/*   52:     */   @EJB
/*   53:     */   private PagoRolEmpleadoDao pagoRolEmpleadoDao;
/*   54:     */   @EJB
/*   55:     */   private PagoRolEmpleadoRubroDao pagoRolEmpleadoRubroDao;
/*   56:     */   @EJB
/*   57:     */   private ServicioPagoRol servicioPagoRol;
/*   58:     */   @EJB
/*   59:     */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*   60:     */   @EJB
/*   61:     */   private ServicioGastoDeducibleSRI servicioGastoDeducibleSRI;
/*   62:     */   @EJB
/*   63:     */   private ServicioImpuestoRentaSRI servicioImpuestoRentaSRI;
/*   64:     */   @EJB
/*   65:     */   private DetallePagoCuotaPrestamoDao detallePagoCuotaPrestamoDao;
/*   66:     */   @EJB
/*   67:     */   private PrestamoDao prestamoDao;
/*   68:     */   @EJB
/*   69:     */   private SubsidioEmpleadoDao subsidioEmpleadoDao;
/*   70:     */   @EJB
/*   71:     */   private RubroOtraEmpresaDao rubroOtraEmpresaDao;
/*   72:     */   @EJB
/*   73:     */   private transient DetalleHistoricoEmpleadoDao detalleHistoricoEmpleadoDao;
/*   74:     */   
/*   75:     */   private class FechasDesdeHastaValidos
/*   76:     */   {
/*   77:     */     private Date fechaDesde;
/*   78:     */     private Date fechaHasta;
/*   79:     */     
/*   80:     */     public FechasDesdeHastaValidos(Date fechaDesde, Date fechaHasta)
/*   81:     */     {
/*   82:  99 */       this.fechaDesde = fechaDesde;
/*   83: 100 */       this.fechaHasta = fechaHasta;
/*   84:     */     }
/*   85:     */     
/*   86:     */     public Date getFechaDesde()
/*   87:     */     {
/*   88: 104 */       return this.fechaDesde;
/*   89:     */     }
/*   90:     */     
/*   91:     */     public Date getFechaHasta()
/*   92:     */     {
/*   93: 108 */       return this.fechaHasta;
/*   94:     */     }
/*   95:     */   }
/*   96:     */   
/*   97:     */   public void guardar(PagoRolEmpleado pagoRolEmpleado)
/*   98:     */   {
/*   99: 120 */     if (pagoRolEmpleado.getListaPagoRolEmpleadoRubro() != null) {
/*  100: 121 */       for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : pagoRolEmpleado.getListaPagoRolEmpleadoRubro()) {
/*  101: 123 */         this.pagoRolEmpleadoRubroDao.guardar(pagoRolEmpleadoRubro);
/*  102:     */       }
/*  103:     */     }
/*  104: 126 */     this.pagoRolEmpleadoDao.guardar(pagoRolEmpleado);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void eliminar(PagoRolEmpleado pagoRolEmpleado)
/*  108:     */   {
/*  109: 136 */     this.pagoRolEmpleadoDao.eliminar(pagoRolEmpleado);
/*  110:     */   }
/*  111:     */   
/*  112:     */   public PagoRolEmpleado buscarPorId(int idPagoRolEmpleado)
/*  113:     */   {
/*  114: 147 */     return (PagoRolEmpleado)this.pagoRolEmpleadoDao.buscarPorId(Integer.valueOf(idPagoRolEmpleado));
/*  115:     */   }
/*  116:     */   
/*  117:     */   public List<PagoRolEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  118:     */   {
/*  119: 158 */     return this.pagoRolEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  120:     */   }
/*  121:     */   
/*  122:     */   public List<PagoRolEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  123:     */   {
/*  124: 168 */     return this.pagoRolEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  125:     */   }
/*  126:     */   
/*  127:     */   public int contarPorCriterio(Map<String, String> filters)
/*  128:     */   {
/*  129: 178 */     return this.pagoRolEmpleadoDao.contarPorCriterio(filters);
/*  130:     */   }
/*  131:     */   
/*  132:     */   public PagoRolEmpleado cargarDetalle(int idPagoRolEmpleado)
/*  133:     */   {
/*  134: 188 */     return this.pagoRolEmpleadoDao.cargarDetalle(idPagoRolEmpleado);
/*  135:     */   }
/*  136:     */   
/*  137:     */   public List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorEmpleado(int idPagoRolEmpleado)
/*  138:     */   {
/*  139: 198 */     return this.pagoRolDao.cargarRubrosVariablesPorEmpleado(idPagoRolEmpleado);
/*  140:     */   }
/*  141:     */   
/*  142:     */   public BigDecimal obtenerSueldoPorEmpleado(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  143:     */   {
/*  144: 209 */     int idRubroSueldo = ParametrosSistema.getRubroSalarioUnificado(pagoRolEmpleadoRubro.getIdOrganizacion()).intValue();
/*  145: 210 */     BigDecimal sueldo = BigDecimal.ZERO;
/*  146:     */     
/*  147:     */ 
/*  148:     */ 
/*  149: 214 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  150:     */     
/*  151: 216 */     HashMap<Integer, HashMap<Integer, PagoRolEmpleadoRubro>> mapaPagoRolEmpleadoRubro = (HashMap)AppUtil.getAtributo("mapaPagoRolEmpleadoRubro");
/*  152: 217 */     if (((HashMap)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(historicoEmpleado.getId()))).get(Integer.valueOf(idRubroSueldo)) != null) {
/*  153: 218 */       sueldo = ((PagoRolEmpleadoRubro)((HashMap)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(historicoEmpleado.getId()))).get(Integer.valueOf(idRubroSueldo))).getValor();
/*  154:     */     }
/*  155: 220 */     return sueldo;
/*  156:     */   }
/*  157:     */   
/*  158:     */   public BigDecimal obtenerRubroPorEmpleado(PagoRolEmpleadoRubro pagoRolEmpleadoRubro, int numeroRubro)
/*  159:     */   {
/*  160: 226 */     int idRubro = 0;
/*  161: 227 */     int idOrganizacion = pagoRolEmpleadoRubro.getIdOrganizacion();
/*  162: 228 */     switch (numeroRubro)
/*  163:     */     {
/*  164:     */     case 1: 
/*  165: 230 */       idRubro = ParametrosSistema.getRubroFormula1(idOrganizacion).intValue();
/*  166: 231 */       break;
/*  167:     */     case 2: 
/*  168: 233 */       idRubro = ParametrosSistema.getRubroFormula2(idOrganizacion).intValue();
/*  169: 234 */       break;
/*  170:     */     case 3: 
/*  171: 237 */       idRubro = ParametrosSistema.getRubroFormula3(idOrganizacion).intValue();
/*  172: 238 */       break;
/*  173:     */     case 4: 
/*  174: 241 */       idRubro = ParametrosSistema.getRubroFormula4(idOrganizacion).intValue();
/*  175: 242 */       break;
/*  176:     */     case 5: 
/*  177: 245 */       idRubro = ParametrosSistema.getRubroFormula5(idOrganizacion).intValue();
/*  178: 246 */       break;
/*  179:     */     }
/*  180: 251 */     BigDecimal valor = BigDecimal.ZERO;
/*  181:     */     
/*  182: 253 */     HashMap<Integer, HashMap<Integer, PagoRolEmpleadoRubro>> mapaPagoRolEmpleadoRubro = (HashMap)AppUtil.getAtributo("mapaPagoRolEmpleadoRubro");
/*  183: 254 */     if (((HashMap)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado().getId()))).get(Integer.valueOf(idRubro)) != null) {
/*  184: 255 */       valor = ((PagoRolEmpleadoRubro)((HashMap)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado().getId()))).get(Integer.valueOf(idRubro))).getValor();
/*  185:     */     }
/*  186: 257 */     return valor;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public List<PagoRolEmpleadoRubro> cargarRubrosPorEmpleado(PagoRol pagoRol)
/*  190:     */   {
/*  191: 268 */     return this.pagoRolEmpleadoDao.cargarRubrosPorEmpleado(pagoRol);
/*  192:     */   }
/*  193:     */   
/*  194:     */   public BigDecimal calcularDecimoTercero(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  195:     */     throws ExcepcionAS2Nomina
/*  196:     */   {
/*  197: 279 */     Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/*  198:     */     
/*  199:     */ 
/*  200: 282 */     int anio = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio() - 1;
/*  201:     */     
/*  202: 284 */     int mesDesde = rubro.getMesCalculoDesde();
/*  203: 285 */     int mesHasta = rubro.getMesCalculoHasta();
/*  204: 286 */     if (pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().isIndicadorFiniquito()) {
/*  205: 289 */       anio = FuncionesUtiles.getMes(pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getFecha()) >= mesDesde ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio() - 1;
/*  206:     */     }
/*  207: 292 */     Date fechaDesde = FuncionesUtiles.getFecha(1, mesDesde, anio);
/*  208: 293 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(anio + 1, mesHasta);
/*  209: 295 */     if (pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito() != null)
/*  210:     */     {
/*  211: 297 */       Date fechaSalida = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito().getFechaFiniquito();
/*  212: 299 */       if (fechaSalida.after(fechaHasta))
/*  213:     */       {
/*  214: 300 */         fechaDesde = FuncionesUtiles.getFecha(1, mesDesde, anio + 1);
/*  215: 301 */         fechaHasta = FuncionesUtiles.getFechaFinMes(anio + 2, mesHasta);
/*  216:     */       }
/*  217:     */     }
/*  218: 305 */     return decimoTercero(pagoRolEmpleadoRubro, fechaDesde, fechaHasta);
/*  219:     */   }
/*  220:     */   
/*  221:     */   public BigDecimal calcularProvisionDecimoTercero(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  222:     */   {
/*  223: 320 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  224:     */     
/*  225: 322 */     BigDecimal valorAportable = obtenerIngresosAprotables(historicoEmpleado, false);
/*  226:     */     
/*  227: 324 */     pagoRolEmpleadoRubro.setTiempo(new BigDecimal(pagoRolEmpleadoRubro.getPagoRolEmpleado().getDiasTrabajados())
/*  228: 325 */       .subtract(new BigDecimal(pagoRolEmpleadoRubro.getPagoRolEmpleado().getDiasFalta())));
/*  229:     */     
/*  230: 327 */     return valorAportable.divide(BigDecimal.valueOf(12.0D), 2, RoundingMode.HALF_UP);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public BigDecimal calcularValoresAportables(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  234:     */   {
/*  235: 339 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  236: 340 */     return obtenerIngresosAprotables(historicoEmpleado, false);
/*  237:     */   }
/*  238:     */   
/*  239:     */   private BigDecimal decimoTercero(PagoRolEmpleadoRubro pagoRolEmpleadoRubro, Date fechaDesde, Date fechaHasta)
/*  240:     */     throws ExcepcionAS2Nomina
/*  241:     */   {
/*  242: 350 */     Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/*  243:     */     
/*  244:     */ 
/*  245: 353 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  246: 354 */     Date fechaDecimoDesde = fechaDesde;
/*  247: 355 */     Date fechaDecimoHasta = fechaHasta;
/*  248: 356 */     FechasDesdeHastaValidos fechas = calculaFechasValidas(historicoEmpleado, fechaDecimoDesde, fechaDecimoHasta, true);
/*  249:     */     
/*  250: 358 */     fechaDecimoDesde = fechas.getFechaDesde();
/*  251:     */     
/*  252: 360 */     fechaDecimoHasta = fechas.getFechaHasta();
/*  253: 361 */     BigDecimal sumaIngresos = BigDecimal.ZERO;
/*  254: 362 */     BigDecimal totalAcumuladoDecimoTercero = BigDecimal.ZERO;
/*  255: 363 */     if (!fechaDecimoHasta.before(fechaDecimoDesde))
/*  256:     */     {
/*  257: 365 */       sumaIngresos = this.pagoRolEmpleadoDao.obtenerIngresosAportables(fechaDecimoDesde, fechaDecimoHasta, historicoEmpleado.getId());
/*  258: 366 */       if (pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito() != null)
/*  259:     */       {
/*  260: 367 */         BigDecimal valorAportable = obtenerIngresosAprotables(historicoEmpleado, false);
/*  261: 368 */         sumaIngresos = sumaIngresos.add(valorAportable);
/*  262:     */       }
/*  263: 370 */       totalAcumuladoDecimoTercero = this.pagoRolEmpleadoDao.obtenerValoresAportablesMensualesDecimosTerceroCuarto(fechaDecimoDesde, 
/*  264: 371 */         FuncionesUtiles.getFechaFinMes(fechaDecimoHasta), historicoEmpleado.getId(), rubro.getRubroPadre());
/*  265:     */       
/*  266: 373 */       BigDecimal meses = obtenerMesesValidos(fechaDecimoDesde, fechaDecimoHasta);
/*  267: 374 */       if (!rubro.isIndicadorProvision())
/*  268:     */       {
/*  269: 375 */         pagoRolEmpleadoRubro.setTiempo(meses.multiply(new BigDecimal(30)).setScale(0, RoundingMode.HALF_UP));
/*  270: 376 */         if (rubro.getRubroPadre() == null) {
/*  271: 377 */           pagoRolEmpleadoRubro.getPagoRolEmpleado().setDiasTrabajados(pagoRolEmpleadoRubro.getTiempo().intValue());
/*  272:     */         }
/*  273:     */       }
/*  274:     */     }
/*  275: 381 */     return sumaIngresos.divide(BigDecimal.valueOf(12L), 2, RoundingMode.HALF_UP).subtract(totalAcumuladoDecimoTercero);
/*  276:     */   }
/*  277:     */   
/*  278:     */   public BigDecimal obtenerIngresosAportables(Date fechaDesde, Date fechaHasta, int idHistoricoEmpleado)
/*  279:     */   {
/*  280: 387 */     return this.pagoRolEmpleadoDao.obtenerIngresosAportables(fechaDesde, fechaHasta, idHistoricoEmpleado);
/*  281:     */   }
/*  282:     */   
/*  283:     */   public BigDecimal obtenerAportePersonal(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  284:     */   {
/*  285: 399 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  286: 400 */     BigDecimal ingresosAportables = obtenerIngresosAprotables(historicoEmpleado, false);
/*  287:     */     
/*  288: 402 */     BigDecimal valorAportable = ingresosAportables.multiply(ParametrosSistema.getPorcentajeAportePersonal(pagoRolEmpleadoRubro.getIdOrganizacion()).divide(BigDecimal.valueOf(100L)));
/*  289: 403 */     pagoRolEmpleadoRubro.getPagoRolEmpleado().setBaseImponibleIEES(ingresosAportables.setScale(2, RoundingMode.HALF_UP));
/*  290: 404 */     return valorAportable;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public BigDecimal obtenerAportePersonalProyectado(HistoricoEmpleado historicoEmpleado)
/*  294:     */   {
/*  295: 414 */     BigDecimal valorAportable = obtenerIngresosAprotables(historicoEmpleado, true);
/*  296:     */     
/*  297:     */ 
/*  298: 417 */     return valorAportable.multiply(ParametrosSistema.getPorcentajeAportePersonal(historicoEmpleado.getIdOrganizacion()).divide(BigDecimal.valueOf(100L)));
/*  299:     */   }
/*  300:     */   
/*  301:     */   public BigDecimal obtenerAportePatronal(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  302:     */   {
/*  303: 430 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  304:     */     
/*  305: 432 */     BigDecimal valorAportable = obtenerIngresosAprotables(historicoEmpleado, false);
/*  306:     */     
/*  307: 434 */     return valorAportable.multiply(ParametrosSistema.getPorcentajeAportePatronal(pagoRolEmpleadoRubro.getPagoRolEmpleado().getIdOrganizacion())
/*  308: 435 */       .divide(BigDecimal.valueOf(100L)));
/*  309:     */   }
/*  310:     */   
/*  311:     */   private BigDecimal obtenerIngresosAprotables(HistoricoEmpleado historicoEmpleado, boolean soloProyectables)
/*  312:     */   {
/*  313: 442 */     HashMap<Integer, HashMap<Integer, PagoRolEmpleadoRubro>> mapaPagoRolEmpleadoRubro = (HashMap)AppUtil.getAtributo("mapaPagoRolEmpleadoRubro");
/*  314: 443 */     BigDecimal valorAportable = BigDecimal.ZERO;
/*  315: 444 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : ((HashMap)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(historicoEmpleado.getIdHistoricoEmpleado()))).values()) {
/*  316: 445 */       if ((pagoRolEmpleadoRubro.isIndicadorCalculoIESS()) && (
/*  317: 446 */         (!soloProyectables) || (pagoRolEmpleadoRubro.getRubro().isIndicadorProyectar()))) {
/*  318: 447 */         valorAportable = valorAportable.add(pagoRolEmpleadoRubro.getValor());
/*  319:     */       }
/*  320:     */     }
/*  321: 451 */     return valorAportable;
/*  322:     */   }
/*  323:     */   
/*  324:     */   public BigDecimal calcularDecimoCuarto(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  325:     */     throws ExcepcionAS2Nomina
/*  326:     */   {
/*  327: 462 */     Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/*  328:     */     
/*  329:     */ 
/*  330:     */ 
/*  331: 466 */     int anio = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio() - 1;
/*  332:     */     
/*  333: 468 */     int mesDesde = rubro.getMesCalculoDesde();
/*  334: 469 */     int mesHasta = rubro.getMesCalculoHasta();
/*  335: 470 */     if (pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().isIndicadorFiniquito()) {
/*  336: 473 */       anio = FuncionesUtiles.getMes(pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getFecha()) >= mesDesde ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio() - 1;
/*  337:     */     }
/*  338: 475 */     Date fechaDesde = FuncionesUtiles.getFecha(1, mesDesde, anio);
/*  339: 476 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(anio + 1, mesHasta);
/*  340: 477 */     if (pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito() != null)
/*  341:     */     {
/*  342: 479 */       Date fechaSalida = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito().getFechaSalida();
/*  343: 481 */       if (fechaSalida.after(fechaHasta))
/*  344:     */       {
/*  345: 482 */         fechaDesde = FuncionesUtiles.getFecha(1, mesDesde, anio + 1);
/*  346: 483 */         fechaHasta = FuncionesUtiles.getFechaFinMes(anio + 2, mesHasta);
/*  347:     */       }
/*  348:     */     }
/*  349: 486 */     return decimoCuarto(pagoRolEmpleadoRubro, fechaDesde, fechaHasta, false);
/*  350:     */   }
/*  351:     */   
/*  352:     */   public BigDecimal calcularProvisionDecimoCuarto(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  353:     */     throws ExcepcionAS2Nomina
/*  354:     */   {
/*  355: 497 */     int mes = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getMes();
/*  356: 498 */     int anio = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio();
/*  357:     */     
/*  358: 500 */     Date fechaDesde = FuncionesUtiles.getFecha(1, mes, anio);
/*  359: 501 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(anio, mes);
/*  360: 502 */     return decimoCuarto(pagoRolEmpleadoRubro, fechaDesde, fechaHasta, true);
/*  361:     */   }
/*  362:     */   
/*  363:     */   private BigDecimal decimoCuarto(PagoRolEmpleadoRubro pagoRolEmpleadoRubro, Date fechaDesde, Date fechaHasta, boolean provision)
/*  364:     */     throws ExcepcionAS2Nomina
/*  365:     */   {
/*  366: 507 */     Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/*  367: 508 */     BigDecimal valorDecimoCuartoAnual = ParametrosSistema.getValorDecimoCuarto(pagoRolEmpleadoRubro.getIdOrganizacion());
/*  368:     */     
/*  369:     */ 
/*  370:     */ 
/*  371: 512 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  372: 513 */     List<DetalleHistoricoEmpleado> lista = this.detalleHistoricoEmpleadoDao.getListaDetalleHistoricoEmpleado(historicoEmpleado, fechaDesde, fechaHasta, provision);
/*  373:     */     
/*  374: 515 */     boolean eventual = false;
/*  375: 516 */     int diasFalta = 0;
/*  376:     */     
/*  377: 518 */     BigDecimal valor = BigDecimal.ZERO;
/*  378: 520 */     if (fechaDesde.compareTo(historicoEmpleado.getFechaIngreso()) < 0) {
/*  379: 521 */       if (fechaHasta.compareTo(historicoEmpleado.getFechaIngreso()) >= 0) {
/*  380: 522 */         fechaDesde = historicoEmpleado.getFechaIngreso();
/*  381:     */       } else {
/*  382: 524 */         fechaDesde = fechaHasta;
/*  383:     */       }
/*  384:     */     }
/*  385: 528 */     if ((historicoEmpleado.getFechaSalida() != null) && (fechaHasta.compareTo(historicoEmpleado.getFechaSalida()) > 0)) {
/*  386: 529 */       fechaHasta = historicoEmpleado.getFechaSalida();
/*  387:     */     }
/*  388: 532 */     BigDecimal meses = BigDecimal.ZERO;
/*  389: 533 */     for (DetalleHistoricoEmpleado detalleHistoricoEmpleado : lista)
/*  390:     */     {
/*  391: 535 */       Date fechaDesdeAux = fechaDesde;
/*  392: 536 */       Date fechaHastaAux = fechaHasta;
/*  393:     */       
/*  394:     */ 
/*  395:     */ 
/*  396:     */ 
/*  397:     */ 
/*  398:     */ 
/*  399: 543 */       Date fechaInicio = provision ? detalleHistoricoEmpleado.getFechaInicio() : detalleHistoricoEmpleado.getFechaInicioContratoFijo();
/*  400: 544 */       fechaInicio = fechaInicio == null ? detalleHistoricoEmpleado.getFechaInicio() : fechaInicio;
/*  401: 546 */       if (fechaInicio.compareTo(fechaDesdeAux) > 0) {
/*  402: 547 */         fechaDesdeAux = fechaInicio;
/*  403:     */       }
/*  404: 550 */       if ((detalleHistoricoEmpleado.getFechaFin() != null) && (detalleHistoricoEmpleado.getFechaFin().compareTo(fechaHastaAux) < 0)) {
/*  405: 551 */         fechaHastaAux = detalleHistoricoEmpleado.getFechaFin();
/*  406:     */       }
/*  407: 554 */       BigDecimal valorDecimoCuarto = FuncionesUtiles.porcentaje(valorDecimoCuartoAnual, detalleHistoricoEmpleado
/*  408: 555 */         .getPorcentajeCapacidadSemanal(), 10);
/*  409:     */       
/*  410: 557 */       BigDecimal valorDecimoCuartoMensual = valorDecimoCuarto.divide(BigDecimal.valueOf(12.0D), 10, RoundingMode.HALF_UP);
/*  411: 559 */       if ((!eventual) && (detalleHistoricoEmpleado.getTipoContrato().isContratoEventual()))
/*  412:     */       {
/*  413: 560 */         eventual = true;
/*  414: 561 */         diasFalta = pagoRolEmpleadoRubro.getPagoRolEmpleado().getDiasFalta();
/*  415:     */       }
/*  416: 564 */       BigDecimal mesesValidos = obtenerMesesValidos(fechaDesdeAux, fechaHastaAux, diasFalta);
/*  417: 565 */       diasFalta = 0;
/*  418: 566 */       meses = meses.add(mesesValidos);
/*  419:     */       
/*  420: 568 */       valor = valor.add(valorDecimoCuartoMensual.multiply(mesesValidos));
/*  421:     */     }
/*  422: 572 */     BigDecimal TotalAcumuladoDecimoCuarto = BigDecimal.ZERO;
/*  423:     */     
/*  424: 574 */     TotalAcumuladoDecimoCuarto = this.pagoRolEmpleadoDao.obtenerValoresAportablesMensualesDecimosTerceroCuarto(fechaDesde, 
/*  425: 575 */       FuncionesUtiles.getFechaFinMes(fechaHasta), historicoEmpleado.getId(), rubro.getRubroPadre());
/*  426:     */     
/*  427:     */ 
/*  428: 578 */     pagoRolEmpleadoRubro.setTiempo(meses.multiply(new BigDecimal(30)).setScale(0, RoundingMode.HALF_UP));
/*  429:     */     
/*  430: 580 */     return FuncionesUtiles.redondearBigDecimal(valor.subtract(TotalAcumuladoDecimoCuarto), 2);
/*  431:     */   }
/*  432:     */   
/*  433:     */   @Deprecated
/*  434:     */   public BigDecimal obtenerMesesValidos(Date fechaDesde, Date fechaHasta)
/*  435:     */   {
/*  436: 585 */     return obtenerMesesValidos(fechaDesde, fechaHasta, 0);
/*  437:     */   }
/*  438:     */   
/*  439:     */   @Deprecated
/*  440:     */   public BigDecimal obtenerMesesValidos(Date fechaDesde, Date fechaHasta, int diasFalta)
/*  441:     */   {
/*  442: 591 */     double meses = 0.0D;
/*  443: 592 */     int dias = 0;
/*  444:     */     
/*  445:     */ 
/*  446:     */ 
/*  447: 596 */     int diaFinMes = FuncionesUtiles.obtenerDiaFecha(FuncionesUtiles.getFechaFinMes(fechaDesde));
/*  448:     */     
/*  449: 598 */     int diaEntrada = FuncionesUtiles.obtenerDiaFecha(fechaDesde);
/*  450: 600 */     if (FuncionesUtiles.getAnio(fechaDesde) == FuncionesUtiles.getAnio(fechaHasta)) {
/*  451: 601 */       meses = FuncionesUtiles.getMes(fechaHasta) - FuncionesUtiles.getMes(fechaDesde) - 1;
/*  452:     */     } else {
/*  453: 603 */       meses = 12 - FuncionesUtiles.getMes(fechaDesde) + FuncionesUtiles.getMes(fechaHasta) - 1;
/*  454:     */     }
/*  455: 607 */     int diasFin = FuncionesUtiles.obtenerDiaFecha(fechaHasta) > 30 ? 30 : FuncionesUtiles.obtenerDiaFecha(fechaHasta);
/*  456:     */     
/*  457: 609 */     boolean anioBiciesto = FuncionesUtiles.getAnio(fechaHasta) % 4 == 0;
/*  458: 610 */     if ((FuncionesUtiles.getMes(fechaHasta) == 2) && (((diasFin == 28) && (!anioBiciesto)) || ((diasFin == 29) && (anioBiciesto)))) {
/*  459: 611 */       diasFin = 30;
/*  460:     */     }
/*  461:     */     int diasInicio;
/*  462:     */     int diasInicio;
/*  463: 614 */     if ((diaFinMes - diaEntrada > 2) || (FuncionesUtiles.getMes(fechaHasta) == 2)) {
/*  464: 615 */       diasInicio = 30 - FuncionesUtiles.obtenerDiaFecha(fechaDesde) + 1;
/*  465:     */     } else {
/*  466: 617 */       diasInicio = ((diaFinMes > diasFin) && (meses == -1.0D) ? diaFinMes : diasFin) - FuncionesUtiles.obtenerDiaFecha(fechaDesde) + 1;
/*  467:     */     }
/*  468: 620 */     dias = diasFin + diasInicio - diasFalta;
/*  469:     */     
/*  470:     */ 
/*  471:     */ 
/*  472: 624 */     return new BigDecimal(meses + dias / 30.0D);
/*  473:     */   }
/*  474:     */   
/*  475:     */   public BigDecimal calcularImpuestoRenta(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  476:     */     throws ExcepcionAS2Nomina
/*  477:     */   {
/*  478: 635 */     int mes = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getMes();
/*  479: 636 */     int anio = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio();
/*  480: 637 */     Date fecha = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getFecha();
/*  481:     */     
/*  482: 639 */     int idEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getIdEmpleado();
/*  483: 640 */     int idOrganizacion = pagoRolEmpleadoRubro.getIdOrganizacion();
/*  484: 641 */     List<RubroOtraEmpresa> listaRubroOtraEmpresa = this.rubroOtraEmpresaDao.getRubroOtraEmpresa(pagoRolEmpleadoRubro.getIdOrganizacion(), Integer.valueOf(idEmpleado), anio);
/*  485:     */     
/*  486: 643 */     BigDecimal valorIngresoOtraEmpresa = BigDecimal.ZERO;
/*  487: 644 */     BigDecimal valorRetenidoOtraEmpresa = BigDecimal.ZERO;
/*  488: 646 */     if (!listaRubroOtraEmpresa.isEmpty()) {
/*  489: 647 */       for (RubroOtraEmpresa rubroOtraEmpresa : listaRubroOtraEmpresa)
/*  490:     */       {
/*  491: 648 */         valorIngresoOtraEmpresa = valorIngresoOtraEmpresa.add(rubroOtraEmpresa.getValorIngreso());
/*  492: 649 */         valorRetenidoOtraEmpresa = valorRetenidoOtraEmpresa.add(rubroOtraEmpresa.getValorRetenido());
/*  493:     */       }
/*  494:     */     }
/*  495: 654 */     Object mapaPagoRolEmpleadoRubro = (HashMap)AppUtil.getAtributo("mapaPagoRolEmpleadoRubro");
/*  496:     */     
/*  497: 656 */     BigDecimal imputable = BigDecimal.ZERO;
/*  498: 657 */     BigDecimal proyectable = BigDecimal.ZERO;
/*  499:     */     
/*  500:     */ 
/*  501:     */ 
/*  502: 661 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  503:     */     
/*  504:     */ 
/*  505: 664 */     PagoRolEmpleadoRubro pagoRolEmpleadoRubroIESS = (PagoRolEmpleadoRubro)((HashMap)((HashMap)mapaPagoRolEmpleadoRubro).get(Integer.valueOf(historicoEmpleado.getId()))).get(ParametrosSistema.getRubroAportePersonalIESS(idOrganizacion));
/*  506: 665 */     if (pagoRolEmpleadoRubroIESS == null) {
/*  507: 668 */       throw new ExcepcionAS2Nomina("msg_error_parametrizacion_rubros", " APORTE IESS PERSONAL " + pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getNombres() + " " + pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getApellidos());
/*  508:     */     }
/*  509: 670 */     BigDecimal iessPersonal = pagoRolEmpleadoRubroIESS.getValor();
/*  510: 671 */     BigDecimal iessPersonalProyectable = obtenerAportePersonalProyectado(pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado());
/*  511: 672 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro1 : ((HashMap)((HashMap)mapaPagoRolEmpleadoRubro).get(Integer.valueOf(historicoEmpleado.getId()))).values()) {
/*  512: 673 */       if ((pagoRolEmpleadoRubro1.getRubro().getOperacion() == 1) && (pagoRolEmpleadoRubro1.getRubro().isIndicadorCalculoImpuestoRenta()) && 
/*  513: 674 */         (!pagoRolEmpleadoRubro1.getValor().equals(BigDecimal.ZERO)))
/*  514:     */       {
/*  515: 675 */         imputable = imputable.add(pagoRolEmpleadoRubro1.getValor());
/*  516: 676 */         if (pagoRolEmpleadoRubro1.getRubro().isIndicadorProyectar()) {
/*  517: 677 */           proyectable = proyectable.add(pagoRolEmpleadoRubro1.getValor());
/*  518:     */         }
/*  519:     */       }
/*  520:     */     }
/*  521: 682 */     pagoRolEmpleadoRubro.getPagoRolEmpleado().setBaseImponibleImpuestoRenta(imputable);
/*  522:     */     
/*  523: 684 */     CalculoImpuestoRenta calculoImpuestoRenta = this.pagoRolEmpleadoDao.obtenerValoresCalculoImpuestoRenta(idEmpleado, anio, mes, 
/*  524: 685 */       ParametrosSistema.getRubroAportePersonalIESS(idOrganizacion).intValue(), ParametrosSistema.getRubroImpuestoALARenta(idOrganizacion).intValue(), fecha);
/*  525:     */     
/*  526: 687 */     int mesesRestantes = 12 - mes;
/*  527:     */     
/*  528: 689 */     BigDecimal impuestoALaRentaRetenido = calculoImpuestoRenta.getImpuestoRenta();
/*  529:     */     
/*  530: 691 */     BigDecimal ingresosTotales = calculoImpuestoRenta.getIngresos().add(valorIngresoOtraEmpresa).add(imputable).add(proyectable.multiply(BigDecimal.valueOf(mesesRestantes)));
/*  531:     */     
/*  532: 693 */     BigDecimal iessTotal = calculoImpuestoRenta.getIess().add(iessPersonal).add(iessPersonalProyectable.multiply(BigDecimal.valueOf(mesesRestantes)));
/*  533: 694 */     BigDecimal gastosDeducibles = this.servicioGastoDeducibleSRI.obtenerPorEmpleadoAnio(idEmpleado, anio);
/*  534:     */     
/*  535: 696 */     BigDecimal valorImpuestoRenta = impuestoALaRentaCausado(anio, idOrganizacion, ingresosTotales, iessTotal, gastosDeducibles);
/*  536: 697 */     valorImpuestoRenta = valorImpuestoRenta.subtract(impuestoALaRentaRetenido).subtract(valorRetenidoOtraEmpresa);
/*  537: 698 */     valorImpuestoRenta = valorImpuestoRenta.divide(BigDecimal.valueOf(mesesRestantes + 1), 2, RoundingMode.HALF_UP);
/*  538: 699 */     valorImpuestoRenta = valorImpuestoRenta.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : valorImpuestoRenta;
/*  539:     */     
/*  540: 701 */     return valorImpuestoRenta;
/*  541:     */   }
/*  542:     */   
/*  543:     */   public BigDecimal impuestoALaRentaCausado(int anio, int idOrganizacion, BigDecimal ingresosTotales, BigDecimal iessTotal, BigDecimal gastosDeducibles)
/*  544:     */     throws ExcepcionAS2Nomina
/*  545:     */   {
/*  546: 712 */     List<ImpuestoRentaSRI> listaImpuestoRentaSRI = this.servicioImpuestoRentaSRI.obtenerTablaPorAnio(anio, idOrganizacion);
/*  547: 714 */     if (listaImpuestoRentaSRI.isEmpty()) {
/*  548: 715 */       throw new ExcepcionAS2Nomina("msg_info_sri_tabla_impuesto_renta_sri", String.valueOf(anio));
/*  549:     */     }
/*  550: 722 */     if (gastosDeducibles.compareTo(ingresosTotales.divide(BigDecimal.valueOf(2L))) > 0) {
/*  551: 723 */       gastosDeducibles = ingresosTotales.divide(BigDecimal.valueOf(2L));
/*  552:     */     }
/*  553: 726 */     BigDecimal valorNeto = ingresosTotales.subtract(iessTotal).subtract(gastosDeducibles);
/*  554: 727 */     BigDecimal valorImpuestoRenta = BigDecimal.ZERO;
/*  555: 729 */     for (ImpuestoRentaSRI impuestoRentaSRI : listaImpuestoRentaSRI) {
/*  556: 730 */       if (valorNeto.compareTo(impuestoRentaSRI.getHasta()) <= 0)
/*  557:     */       {
/*  558: 731 */         BigDecimal diferenciaFraccionBase = valorNeto.subtract(impuestoRentaSRI.getDesde());
/*  559:     */         
/*  560: 733 */         valorImpuestoRenta = FuncionesUtiles.porcentaje(diferenciaFraccionBase, impuestoRentaSRI.getPorcentaje(), 2).add(impuestoRentaSRI.getFraccionBasica());
/*  561: 734 */         break;
/*  562:     */       }
/*  563:     */     }
/*  564: 738 */     return valorImpuestoRenta;
/*  565:     */   }
/*  566:     */   
/*  567:     */   public BigDecimal descuentoPrestamo(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  568:     */   {
/*  569: 748 */     Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/*  570: 749 */     List<DetallePrestamo> lista = new ArrayList();
/*  571:     */     
/*  572: 751 */     int mes = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getMes();
/*  573: 752 */     int anio = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio();
/*  574: 753 */     Date fechaInicioMes = FuncionesUtiles.setAtributoFecha(FuncionesUtiles.getFecha(1, mes, anio));
/*  575: 754 */     Date fechaFinMes = FuncionesUtiles.setAtributoFecha(FuncionesUtiles.getFechaFinMes(anio, mes));
/*  576: 755 */     lista = this.prestamoDao.obtenerCuotasPrestamoPorEmpleadoRubroFecha(pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getIdEmpleado(), rubro, fechaInicioMes, fechaFinMes);
/*  577:     */     
/*  578:     */ 
/*  579: 758 */     BigDecimal valorRubro = BigDecimal.ZERO;
/*  580: 760 */     for (DetallePrestamo detallePrestamo : lista)
/*  581:     */     {
/*  582: 761 */       Date fechaDescuento = detallePrestamo.getFechaDescuento() != null ? detallePrestamo.getFechaDescuento() : detallePrestamo.getFechaCuota();
/*  583: 762 */       if (((fechaDescuento.before(fechaFinMes)) && (fechaDescuento.after(fechaInicioMes))) || (fechaDescuento.compareTo(fechaFinMes) == 0) || 
/*  584: 763 */         (fechaDescuento.compareTo(fechaInicioMes) == 0)) {
/*  585: 765 */         if (detallePrestamo.getSaldoCapitalCuota().add(detallePrestamo.getSaldoInteresCuota()).compareTo(BigDecimal.ZERO) == 1)
/*  586:     */         {
/*  587: 769 */           DetallePagoCuotaPrestamo detallePagoCuotaPrestamo = new DetallePagoCuotaPrestamo();
/*  588: 770 */           detallePagoCuotaPrestamo.setCapitalCuota(detallePrestamo.getSaldoCapitalCuota());
/*  589: 771 */           detallePagoCuotaPrestamo.setInteresCuota(detallePrestamo.getSaldoInteresCuota());
/*  590: 772 */           detallePagoCuotaPrestamo.setInteresMoraCuota(detallePrestamo.getSaldoInteresMoraCuota());
/*  591: 773 */           detallePagoCuotaPrestamo.setDetallePrestamo(detallePrestamo);
/*  592: 774 */           detallePagoCuotaPrestamo.setNumeroCuota(detallePrestamo.getNumeroCuota());
/*  593: 775 */           detallePagoCuotaPrestamo.setPagoRolEmpleado(pagoRolEmpleadoRubro.getPagoRolEmpleado());
/*  594: 776 */           detallePrestamo.getListaDetallePagoCuotaPrestamo().add(detallePagoCuotaPrestamo);
/*  595:     */           
/*  596:     */ 
/*  597:     */ 
/*  598: 780 */           detallePrestamo.setSaldoCapitalCuota(BigDecimal.ZERO);
/*  599: 781 */           detallePrestamo.setSaldoInteresCuota(BigDecimal.ZERO);
/*  600:     */           
/*  601:     */ 
/*  602:     */ 
/*  603: 785 */           this.detallePagoCuotaPrestamoDao.guardar(detallePagoCuotaPrestamo);
/*  604: 786 */           valorRubro = valorRubro.add(detallePrestamo.getCapitalCuota().add(detallePrestamo.getInteresCuota()));
/*  605:     */         }
/*  606:     */         else
/*  607:     */         {
/*  608: 789 */           valorRubro = valorRubro.add(detallePrestamo.getCapitalCuota().add(detallePrestamo.getInteresCuota()));
/*  609:     */         }
/*  610:     */       }
/*  611:     */     }
/*  612: 795 */     return valorRubro;
/*  613:     */   }
/*  614:     */   
/*  615:     */   public BigDecimal calcularFondoReserva(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  616:     */     throws ExcepcionAS2Nomina
/*  617:     */   {
/*  618: 805 */     PagoRol pagoRol = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol();
/*  619: 806 */     int mes = pagoRol.getMes();
/*  620: 807 */     int anio = pagoRol.getAnio();
/*  621: 808 */     Date fechaDesde = FuncionesUtiles.getFecha(1, mes, anio);
/*  622: 809 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(anio, mes);
/*  623:     */     
/*  624:     */ 
/*  625:     */ 
/*  626: 813 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() != null ? pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado() : pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleadoFiniquito();
/*  627:     */     
/*  628: 815 */     return fondoReserva(historicoEmpleado, fechaDesde, fechaHasta);
/*  629:     */   }
/*  630:     */   
/*  631:     */   private FechasDesdeHastaValidos calculaFechasValidas(HistoricoEmpleado historicoEmpleado, Date fechaDesde, Date fechaHasta, boolean indicadorDecimoTercero)
/*  632:     */     throws ExcepcionAS2Nomina
/*  633:     */   {
/*  634: 821 */     if (historicoEmpleado.getFechaIngreso().after(fechaDesde)) {
/*  635: 822 */       fechaDesde = historicoEmpleado.getFechaIngreso();
/*  636:     */     }
/*  637: 824 */     if ((historicoEmpleado.getFechaSalida() != null) && (historicoEmpleado.getFechaSalida().before(fechaHasta)) && (
/*  638: 825 */       (FuncionesUtiles.getAnio(historicoEmpleado.getFechaSalida()) == FuncionesUtiles.getAnio(fechaHasta)) || 
/*  639: 826 */       (FuncionesUtiles.getMes(historicoEmpleado.getFechaSalida()) == FuncionesUtiles.getMes(fechaHasta)))) {
/*  640: 827 */       if (indicadorDecimoTercero) {
/*  641: 828 */         fechaHasta = historicoEmpleado.getFechaFiniquito();
/*  642:     */       } else {
/*  643: 830 */         fechaHasta = historicoEmpleado.getFechaSalida();
/*  644:     */       }
/*  645:     */     }
/*  646: 835 */     return new FechasDesdeHastaValidos(fechaDesde, fechaHasta);
/*  647:     */   }
/*  648:     */   
/*  649:     */   private BigDecimal fondoReserva(HistoricoEmpleado historicoEmpleado, Date fechaDesde, Date fechaHasta)
/*  650:     */     throws ExcepcionAS2Nomina
/*  651:     */   {
/*  652: 841 */     Date fechaFondoDesde = FuncionesUtiles.sumarFechaDiasMeses(FuncionesUtiles.sumarFechaAnios(historicoEmpleado.getFechaIngreso(), 1), 
/*  653: 842 */       -(historicoEmpleado.getDiasFondoReserva() == 365 ? 366 : historicoEmpleado.getDiasFondoReserva()));
/*  654: 843 */     FechasDesdeHastaValidos fechas = calculaFechasValidas(historicoEmpleado, fechaFondoDesde, fechaHasta, false);
/*  655: 844 */     Date fechaFondoHasta = fechas.getFechaHasta();
/*  656:     */     
/*  657:     */ 
/*  658: 847 */     long diasFondoTmp = FuncionesUtiles.DiasEntreFechas(historicoEmpleado.getFechaIngreso(), fechaFondoHasta) + historicoEmpleado.getDiasFondoReserva();
/*  659: 848 */     long diasAnio = FuncionesUtiles.DiasEntreFechas(FuncionesUtiles.sumarFechaMeses(fechaHasta, -12), fechaHasta) - 1L;
/*  660: 849 */     int diasMes = FuncionesUtiles.getDiaFecha(fechaFondoHasta);
/*  661: 850 */     if (fechaFondoDesde.compareTo(fechaHasta) <= 0)
/*  662:     */     {
/*  663: 852 */       BigDecimal proporcional = BigDecimal.valueOf(1L);
/*  664: 853 */       if ((historicoEmpleado.getDiasFondoReserva() < 365) && (diasFondoTmp - diasAnio < diasMes)) {
/*  665: 855 */         if ((fechaFondoDesde.compareTo(fechaDesde) > 0) || (fechaFondoHasta.compareTo(fechaHasta) < 0))
/*  666:     */         {
/*  667: 857 */           if (fechaFondoDesde.compareTo(fechaDesde) < 0) {
/*  668: 858 */             fechaFondoDesde = FuncionesUtiles.getFechaInicioMes(fechaFondoHasta);
/*  669:     */           }
/*  670: 861 */           BigDecimal dias = obtenerMesesValidos(fechaFondoDesde, fechaFondoHasta);
/*  671:     */           
/*  672: 863 */           dias = dias.multiply(new BigDecimal(30.0D));
/*  673:     */           
/*  674: 865 */           proporcional = dias.divide(new BigDecimal(30), 10, RoundingMode.HALF_UP);
/*  675:     */         }
/*  676:     */       }
/*  677: 869 */       BigDecimal sumaIngresos = obtenerIngresosAprotables(historicoEmpleado, false);
/*  678: 870 */       return FuncionesUtiles.redondearBigDecimal(sumaIngresos.multiply(BigDecimal.valueOf(0.0833D).multiply(proporcional)), 2);
/*  679:     */     }
/*  680: 873 */     return BigDecimal.ZERO.setScale(2);
/*  681:     */   }
/*  682:     */   
/*  683:     */   public BigDecimal subsidio(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  684:     */   {
/*  685: 884 */     Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/*  686: 885 */     int mes = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getMes();
/*  687: 886 */     int anio = pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getAnio();
/*  688: 887 */     int idEmpleado = pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getIdEmpleado();
/*  689: 888 */     Date fechaDesde = FuncionesUtiles.getFecha(1, mes, anio);
/*  690: 889 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(anio, mes);
/*  691:     */     
/*  692:     */ 
/*  693:     */ 
/*  694:     */ 
/*  695: 894 */     List<SubsidioEmpleado> listaSubsidioEmpleado = this.subsidioEmpleadoDao.buscarPoIdEmpleado(idEmpleado, rubro);
/*  696: 895 */     BigDecimal acumulado = BigDecimal.ZERO;
/*  697: 897 */     if (listaSubsidioEmpleado.size() > 0) {
/*  698: 898 */       for (SubsidioEmpleado subsidioEmpleado : listaSubsidioEmpleado)
/*  699:     */       {
/*  700: 899 */         BigDecimal porcentajeSubsidio = subsidioEmpleado.getTipoSubsidio().getPorcentajeSubsidio();
/*  701:     */         
/*  702:     */ 
/*  703: 902 */         BigDecimal valorAportable = obtenerIngresosAprotables(pagoRolEmpleadoRubro.getPagoRolEmpleado().getHistoricoEmpleado(), false);
/*  704:     */         
/*  705: 904 */         Date fechaSubsidioDesde = subsidioEmpleado.getFechaDesde();
/*  706: 905 */         Date fechaSubsidioHasta = subsidioEmpleado.getFechaHasta();
/*  707: 906 */         long diasSubsidio = 0L;
/*  708: 908 */         if (fechaSubsidioDesde.before(fechaDesde)) {
/*  709: 909 */           fechaSubsidioDesde = fechaDesde;
/*  710:     */         }
/*  711: 911 */         if (fechaSubsidioHasta.after(fechaHasta)) {
/*  712: 912 */           fechaSubsidioHasta = fechaHasta;
/*  713:     */         }
/*  714: 914 */         if (FuncionesUtiles.getDiaFecha(fechaSubsidioHasta) == 31) {
/*  715: 915 */           fechaSubsidioHasta = FuncionesUtiles.sumarFechaDiasMeses(fechaSubsidioHasta, -1);
/*  716:     */         }
/*  717: 917 */         diasSubsidio = FuncionesUtiles.DiasEntreFechas(fechaSubsidioDesde, fechaSubsidioHasta);
/*  718:     */         
/*  719: 919 */         diasSubsidio = diasSubsidio > 30L ? 30L : diasSubsidio;
/*  720: 920 */         if (diasSubsidio > 0L)
/*  721:     */         {
/*  722: 922 */           long diasTrabajados = pagoRolEmpleadoRubro.getPagoRolEmpleado().getDiasTrabajados() - pagoRolEmpleadoRubro.getPagoRolEmpleado().getDiasFalta();
/*  723:     */           
/*  724: 924 */           BigDecimal subsidio = valorAportable.multiply(new BigDecimal(diasSubsidio)).divide(new BigDecimal(diasTrabajados), RoundingMode.HALF_UP);
/*  725:     */           
/*  726:     */ 
/*  727: 927 */           subsidio = subsidio.multiply(new BigDecimal(100).subtract(porcentajeSubsidio).divide(new BigDecimal(100), RoundingMode.HALF_UP));
/*  728:     */           
/*  729: 929 */           acumulado = acumulado.add(subsidio);
/*  730:     */         }
/*  731:     */       }
/*  732:     */     }
/*  733: 936 */     return acumulado;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public List<PagoRolEmpleadoRubro> obtenerListaPorEmpleadoRubro(Empleado empleado, int idRubro)
/*  737:     */   {
/*  738: 941 */     return null;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public List<PagoRolEmpleadoRubro> obtenerListaPorPagoRolRubro(int idPagoRol, int idRubro)
/*  742:     */   {
/*  743: 946 */     return this.pagoRolEmpleadoRubroDao.obtenerListaPorPagoRolRubro(idPagoRol, idRubro);
/*  744:     */   }
/*  745:     */   
/*  746:     */   public BigDecimal obtenerValorRubroPadre(int anio, int mes, int idHistoricoEmpleado, int idRubro)
/*  747:     */   {
/*  748: 956 */     BigDecimal valorAportable = this.pagoRolEmpleadoDao.obtenerValorRubroPadre(anio, mes, idHistoricoEmpleado, idRubro);
/*  749: 957 */     return valorAportable;
/*  750:     */   }
/*  751:     */   
/*  752:     */   public void actualizarPagoEmpleado(PagoRolEmpleado pagoRolEmpleado, PagoEmpleado pagoEmpleado, boolean indicadorCobrado)
/*  753:     */   {
/*  754: 969 */     this.pagoRolEmpleadoDao.actualizarPagoEmpleado(pagoRolEmpleado, pagoEmpleado, indicadorCobrado);
/*  755:     */   }
/*  756:     */   
/*  757:     */   public void actualizarReferenciaPagoRolEmpleado(PagoRolEmpleado pagoRolEmpleado)
/*  758:     */   {
/*  759: 980 */     this.pagoRolEmpleadoDao.actualizarReferenciaPagoRolEmpleado(pagoRolEmpleado);
/*  760:     */   }
/*  761:     */   
/*  762:     */   public Empleado obtenerEmpleado(int idPagoRolEmpleado)
/*  763:     */   {
/*  764: 985 */     return this.pagoRolEmpleadoDao.obtenerEmpleado(idPagoRolEmpleado);
/*  765:     */   }
/*  766:     */   
/*  767:     */   public List getSaldoPrestamosEmpleado(int idOrganizacion, Empleado empleado)
/*  768:     */   {
/*  769: 991 */     return this.prestamoDao.getSaldoPrestamosEmpleado(idOrganizacion, empleado);
/*  770:     */   }
/*  771:     */   
/*  772:     */   public BigDecimal getSaldoTotalPrestamosEmpleado(int idOrganizacion, Empleado empleado)
/*  773:     */   {
/*  774: 997 */     BigDecimal saldoTotal = BigDecimal.ZERO;
/*  775: 998 */     List<Object[]> prestamos = this.prestamoDao.getSaldoPrestamosEmpleado(idOrganizacion, empleado);
/*  776: 999 */     for (Object[] obj : prestamos) {
/*  777:1000 */       saldoTotal = saldoTotal.add((BigDecimal)obj[1]);
/*  778:     */     }
/*  779:1003 */     return saldoTotal;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public List<PagoRolEmpleado> getPagoRolEmpleado(PagoRol pagoRol)
/*  783:     */   {
/*  784:1008 */     return this.pagoRolEmpleadoDao.getPagoRolEmpleado(pagoRol);
/*  785:     */   }
/*  786:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPagoRolEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */