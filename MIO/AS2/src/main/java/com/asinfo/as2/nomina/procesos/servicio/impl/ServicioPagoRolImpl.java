/*    1:     */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.AprobacionPagoRol;
/*    4:     */ import com.asinfo.as2.clases.RelacionDependencia;
/*    5:     */ import com.asinfo.as2.dao.DetalleHistoricoEmpleadoDao;
/*    6:     */ import com.asinfo.as2.dao.HistoricoEmpleadoDao;
/*    7:     */ import com.asinfo.as2.dao.PagoRolDao;
/*    8:     */ import com.asinfo.as2.dao.PagoRolEmpleadoDao;
/*    9:     */ import com.asinfo.as2.dao.PagoRolEmpleadoRubroDao;
/*   10:     */ import com.asinfo.as2.dao.ReporteNominaDao;
/*   11:     */ import com.asinfo.as2.dao.RubroDao;
/*   12:     */ import com.asinfo.as2.dao.RubroOtraEmpresaDao;
/*   13:     */ import com.asinfo.as2.dao.UtilidadDao;
/*   14:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   15:     */ import com.asinfo.as2.entities.Asiento;
/*   16:     */ import com.asinfo.as2.entities.CargoEmpleado;
/*   17:     */ import com.asinfo.as2.entities.Departamento;
/*   18:     */ import com.asinfo.as2.entities.DetalleFiniquitoEmpleado;
/*   19:     */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*   20:     */ import com.asinfo.as2.entities.DetalleUtilidad;
/*   21:     */ import com.asinfo.as2.entities.Documento;
/*   22:     */ import com.asinfo.as2.entities.Empleado;
/*   23:     */ import com.asinfo.as2.entities.Empresa;
/*   24:     */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   25:     */ import com.asinfo.as2.entities.Organizacion;
/*   26:     */ import com.asinfo.as2.entities.PagoCash;
/*   27:     */ import com.asinfo.as2.entities.PagoRol;
/*   28:     */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   29:     */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*   30:     */ import com.asinfo.as2.entities.Quincena;
/*   31:     */ import com.asinfo.as2.entities.Rubro;
/*   32:     */ import com.asinfo.as2.entities.RubroEmpleado;
/*   33:     */ import com.asinfo.as2.entities.RubroOtraEmpresa;
/*   34:     */ import com.asinfo.as2.entities.Sucursal;
/*   35:     */ import com.asinfo.as2.entities.Utilidad;
/*   36:     */ import com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia;
/*   37:     */ import com.asinfo.as2.entities.nomina.asistencia.TipoFalta;
/*   38:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   39:     */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*   40:     */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*   41:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   42:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   43:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   44:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   45:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   46:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*   47:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*   48:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*   49:     */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   50:     */ import com.asinfo.as2.nomina.procesos.servicio.FuncionRol;
/*   51:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*   52:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*   53:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*   54:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*   55:     */ import com.asinfo.as2.util.AppUtil;
/*   56:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   57:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   58:     */ import java.io.IOException;
/*   59:     */ import java.io.InputStream;
/*   60:     */ import java.math.BigDecimal;
/*   61:     */ import java.math.RoundingMode;
/*   62:     */ import java.util.ArrayList;
/*   63:     */ import java.util.Calendar;
/*   64:     */ import java.util.Collection;
/*   65:     */ import java.util.Date;
/*   66:     */ import java.util.HashMap;
/*   67:     */ import java.util.Iterator;
/*   68:     */ import java.util.List;
/*   69:     */ import java.util.Map;
/*   70:     */ import java.util.TreeMap;
/*   71:     */ import javax.annotation.Resource;
/*   72:     */ import javax.ejb.EJB;
/*   73:     */ import javax.ejb.SessionContext;
/*   74:     */ import javax.ejb.Stateless;
/*   75:     */ import javax.ejb.TransactionAttribute;
/*   76:     */ import javax.ejb.TransactionAttributeType;
/*   77:     */ import javax.ejb.TransactionManagement;
/*   78:     */ import javax.ejb.TransactionManagementType;
/*   79:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   80:     */ 
/*   81:     */ @Stateless
/*   82:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   83:     */ public class ServicioPagoRolImpl
/*   84:     */   implements ServicioPagoRol
/*   85:     */ {
/*   86:     */   @EJB
/*   87:     */   private transient PagoRolDao pagoRolDao;
/*   88:     */   @EJB
/*   89:     */   private transient PagoRolEmpleadoDao pagoRolEmpleadoDao;
/*   90:     */   @EJB
/*   91:     */   private transient HistoricoEmpleadoDao historicoEmpleadoDao;
/*   92:     */   @EJB
/*   93:     */   private transient PagoRolEmpleadoRubroDao pagoRolEmpleadoRubroDao;
/*   94:     */   @EJB
/*   95:     */   private transient ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*   96:     */   @EJB
/*   97:     */   private transient ServicioPeriodo servicioPeriodo;
/*   98:     */   @EJB
/*   99:     */   private transient ServicioEmpleado servicioEmpleado;
/*  100:     */   @EJB
/*  101:     */   private transient ServicioQuincena servicioQuincena;
/*  102:     */   @EJB
/*  103:     */   private transient ServicioDocumento servicioDocumento;
/*  104:     */   @EJB
/*  105:     */   private transient ServicioRubro servicioRubro;
/*  106:     */   @EJB
/*  107:     */   private transient ServicioAsiento servicioAsiento;
/*  108:     */   @EJB
/*  109:     */   private transient ServicioPagoRol servicioPagoRol;
/*  110:     */   @EJB
/*  111:     */   private transient ReporteNominaDao reporteNominaDao;
/*  112:     */   @EJB
/*  113:     */   private transient RubroDao rubroDao;
/*  114:     */   @EJB
/*  115:     */   private transient RubroOtraEmpresaDao rubroOtraEmpresaDao;
/*  116:     */   @EJB
/*  117:     */   private transient UtilidadDao utilidadDao;
/*  118:     */   @EJB
/*  119:     */   private transient ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  120:     */   @EJB
/*  121:     */   private transient DetalleHistoricoEmpleadoDao detalleHistoricoEmpleadoDao;
/*  122:     */   @EJB
/*  123:     */   private transient ServicioRubroEmpleado servicioRubroEmpleado;
/*  124:     */   @Resource
/*  125:     */   protected SessionContext context;
/*  126:     */   
/*  127:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  128:     */   public void guardar(PagoRol pagoRol)
/*  129:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  130:     */   {
/*  131: 153 */     if (pagoRol.getListaPagoRolEmpleado().size() == 0) {
/*  132: 154 */       pagoRol.setListaPagoRolEmpleado(null);
/*  133:     */     } else {
/*  134: 156 */       for (PagoRolEmpleado pagoRolEmpleado : pagoRol.getListaPagoRolEmpleado())
/*  135:     */       {
/*  136: 157 */         if (pagoRolEmpleado.getListaPagoRolEmpleadoRubro().size() == 0) {
/*  137: 158 */           pagoRolEmpleado.setListaPagoRolEmpleadoRubro(null);
/*  138:     */         } else {
/*  139: 163 */           for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : pagoRolEmpleado.getListaPagoRolEmpleadoRubro()) {
/*  140: 164 */             this.pagoRolEmpleadoRubroDao.guardar(pagoRolEmpleadoRubro);
/*  141:     */           }
/*  142:     */         }
/*  143: 168 */         this.pagoRolEmpleadoDao.guardar(pagoRolEmpleado);
/*  144:     */       }
/*  145:     */     }
/*  146: 171 */     this.pagoRolDao.guardar(pagoRol);
/*  147:     */   }
/*  148:     */   
/*  149:     */   public void eliminar(PagoRol pagoRol)
/*  150:     */   {
/*  151: 181 */     this.pagoRolDao.eliminar(pagoRol);
/*  152:     */   }
/*  153:     */   
/*  154:     */   public void anular(PagoRol pagoRol)
/*  155:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  156:     */   {
/*  157: 187 */     validar(pagoRol);
/*  158: 188 */     pagoRol = cargarDetalle(pagoRol.getIdPagoRol());
/*  159: 189 */     if (pagoRol.getAsiento() != null)
/*  160:     */     {
/*  161: 190 */       pagoRol.getAsiento().setIndicadorAutomatico(false);
/*  162: 191 */       this.servicioAsiento.anular(pagoRol.getAsiento());
/*  163:     */     }
/*  164: 193 */     pagoRol.setAsiento(null);
/*  165: 194 */     pagoRol.setEstado(Estado.ELABORADO);
/*  166:     */   }
/*  167:     */   
/*  168:     */   public PagoRol buscarPorId(int idPagoRol)
/*  169:     */   {
/*  170: 204 */     return (PagoRol)this.pagoRolDao.buscarPorId(Integer.valueOf(idPagoRol));
/*  171:     */   }
/*  172:     */   
/*  173:     */   public List<PagoRol> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  174:     */   {
/*  175: 214 */     return this.pagoRolDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  176:     */   }
/*  177:     */   
/*  178:     */   public List<PagoRol> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  179:     */   {
/*  180: 224 */     return this.pagoRolDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  181:     */   }
/*  182:     */   
/*  183:     */   public int contarPorCriterio(Map<String, String> filters)
/*  184:     */   {
/*  185: 234 */     return this.pagoRolDao.contarPorCriterio(filters);
/*  186:     */   }
/*  187:     */   
/*  188:     */   public PagoRol cargarDetalle(int idPagoRol)
/*  189:     */   {
/*  190: 244 */     return this.pagoRolDao.cargarDetalle(idPagoRol);
/*  191:     */   }
/*  192:     */   
/*  193:     */   public List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorEmpleado(int idPagoRolEmpleado)
/*  194:     */   {
/*  195: 254 */     return this.pagoRolDao.cargarRubrosVariablesPorEmpleado(idPagoRolEmpleado);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorRubro(int idPagoRol, int idRubro)
/*  199:     */   {
/*  200: 264 */     return this.pagoRolDao.cargarRubrosVariablesPorRubro(idPagoRol, idRubro);
/*  201:     */   }
/*  202:     */   
/*  203:     */   private void valorProporcional(PagoRolEmpleadoRubro pagoRolEmpleadoRubro, int diasQuincena)
/*  204:     */   {
/*  205: 268 */     pagoRolEmpleadoRubro.setValor(FuncionesUtiles.redondearBigDecimal(pagoRolEmpleadoRubro
/*  206: 269 */       .getValor().multiply(
/*  207: 270 */       BigDecimal.valueOf(pagoRolEmpleadoRubro
/*  208: 271 */       .getPagoRolEmpleado().getDiasTrabajados() - pagoRolEmpleadoRubro
/*  209: 272 */       .getPagoRolEmpleado().getDiasFalta()).divide(BigDecimal.valueOf(diasQuincena), 6, RoundingMode.HALF_UP)), 2));
/*  210:     */   }
/*  211:     */   
/*  212:     */   private void validar(PagoRol pagoRol)
/*  213:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  214:     */   {
/*  215: 277 */     Date fechaFin = FuncionesUtiles.getFechaFinMes(pagoRol.getAnio(), pagoRol.getMes());
/*  216: 278 */     Date fechaInicio = FuncionesUtiles.getFecha(1, pagoRol.getMes(), pagoRol.getAnio());
/*  217: 279 */     pagoRol.getFecha().before(fechaInicio);
/*  218: 280 */     pagoRol.getFecha().after(fechaFin);
/*  219: 281 */     if ((pagoRol.getFecha().before(fechaInicio)) || (pagoRol.getFecha().after(fechaFin))) {
/*  220: 282 */       throw new ExcepcionAS2Financiero("msg_error_fecha_pago_rol");
/*  221:     */     }
/*  222: 289 */     this.servicioPeriodo.buscarPorFecha(pagoRol.getFecha(), pagoRol.getIdOrganizacion(), pagoRol.getDocumento().getDocumentoBase());
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void esEditable(PagoRol pagoRol)
/*  226:     */     throws ExcepcionAS2Financiero
/*  227:     */   {
/*  228: 300 */     this.servicioPeriodo.buscarPorFecha(pagoRol.getFecha(), pagoRol.getIdOrganizacion(), pagoRol.getDocumento().getDocumentoBase());
/*  229: 304 */     if (pagoRol.getEstado() == Estado.ANULADO) {
/*  230: 305 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/*  231:     */     }
/*  232: 307 */     if ((pagoRol.getAsiento() != null) && (pagoRol.getAsiento().getEstado() == Estado.REVISADO)) {
/*  233: 308 */       throw new ExcepcionAS2Financiero("msg_info_anulacion_proceso_estado_asiento");
/*  234:     */     }
/*  235:     */   }
/*  236:     */   
/*  237:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  238:     */   public void actualizarPagoRolEmpleado(PagoRol pagoRol, int idOrganizacion, int idSucursal, Integer idEmpleado)
/*  239:     */     throws ExcepcionAS2Nomina
/*  240:     */   {
/*  241: 323 */     for (HistoricoEmpleado historicoEmpleado : this.pagoRolDao.actualizarPagoRolEmpleado(pagoRol.getIdPagoRol(), idOrganizacion))
/*  242:     */     {
/*  243: 324 */       PagoRolEmpleado pagoRolEmpleado = new PagoRolEmpleado();
/*  244: 325 */       pagoRolEmpleado.setEmpleado(historicoEmpleado.getEmpleado());
/*  245: 326 */       pagoRolEmpleado.setHistoricoEmpleado(historicoEmpleado);
/*  246: 327 */       pagoRolEmpleado.setDiasFalta(0);
/*  247: 328 */       pagoRolEmpleado.setPagoRol(pagoRol);
/*  248: 329 */       pagoRolEmpleado.setIdOrganizacion(idOrganizacion);
/*  249: 330 */       pagoRolEmpleado.setIdSucursal(idSucursal);
/*  250: 331 */       pagoRolEmpleado.setFechaIngresoEmpleado(historicoEmpleado.getFechaIngreso());
/*  251: 332 */       if (historicoEmpleado.getFechaSalida() != null) {
/*  252: 334 */         if (FuncionesUtiles.obtenerMesFecha(historicoEmpleado.getFechaSalida()) == FuncionesUtiles.obtenerMesFecha(pagoRolEmpleado.getPagoRol().getFecha())) {
/*  253: 336 */           if (FuncionesUtiles.obtenerAnioFecha(historicoEmpleado.getFechaSalida()) == FuncionesUtiles.obtenerAnioFecha(pagoRolEmpleado.getPagoRol().getFecha())) {
/*  254: 337 */             pagoRolEmpleado.setFechaSalidaEmpleado(historicoEmpleado.getFechaSalida());
/*  255:     */           }
/*  256:     */         }
/*  257:     */       }
/*  258: 339 */       pagoRolEmpleado.setDepartamento(historicoEmpleado.getEmpleado().getDepartamento());
/*  259: 340 */       pagoRolEmpleado.setCentroCosto(historicoEmpleado.getEmpleado().getCentroCosto());
/*  260: 341 */       this.pagoRolEmpleadoDao.guardar(pagoRolEmpleado);
/*  261:     */     }
/*  262:     */   }
/*  263:     */   
/*  264:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  265:     */   public void actualizarPagoRolEmpleadoRubro(PagoRol pagoRol, Integer idEmpleado)
/*  266:     */   {
/*  267: 355 */     Map<Integer, List<RubroEmpleado>> mapaPagoRolEmpleadoRubroNuevo = new HashMap();
/*  268: 356 */     List<RubroEmpleado> listaRubroEmpleado = this.pagoRolDao.actualizarPagoRolEmpleadoRubro(pagoRol);
/*  269: 357 */     for (RubroEmpleado rubroEmpleado : listaRubroEmpleado)
/*  270:     */     {
/*  271: 358 */       List<RubroEmpleado> listaRubroEmpleadoAux = (List)mapaPagoRolEmpleadoRubroNuevo.get(Integer.valueOf(rubroEmpleado.getEmpleado().getId()));
/*  272: 359 */       if (listaRubroEmpleadoAux == null) {
/*  273: 360 */         listaRubroEmpleadoAux = new ArrayList();
/*  274:     */       }
/*  275: 362 */       listaRubroEmpleadoAux.add(rubroEmpleado);
/*  276: 363 */       mapaPagoRolEmpleadoRubroNuevo.put(Integer.valueOf(rubroEmpleado.getEmpleado().getId()), listaRubroEmpleadoAux);
/*  277:     */     }
/*  278: 365 */     pagoRol = cargarDetalle(pagoRol.getIdPagoRol());
/*  279: 366 */     for (??? = pagoRol.getListaPagoRolEmpleado().iterator(); ???.hasNext();)
/*  280:     */     {
/*  281: 366 */       pagoRolEmpleado = (PagoRolEmpleado)???.next();
/*  282: 367 */       mapaPagoRolEmpeladoRubro = new HashMap();
/*  283: 368 */       for (Iterator localIterator2 = pagoRolEmpleado.getListaPagoRolEmpleadoRubro().iterator(); localIterator2.hasNext();)
/*  284:     */       {
/*  285: 368 */         pagoRolEmpleadoRubro = (PagoRolEmpleadoRubro)localIterator2.next();
/*  286: 369 */         if (!pagoRolEmpleadoRubro.isIndicadorAutomatico()) {
/*  287: 370 */           mapaPagoRolEmpeladoRubro.put(Integer.valueOf(pagoRolEmpleadoRubro.getRubro().getId()), pagoRolEmpleadoRubro);
/*  288:     */         }
/*  289:     */       }
/*  290: 373 */       Object listaRubroEmpleadoAux = (List)mapaPagoRolEmpleadoRubroNuevo.get(Integer.valueOf(pagoRolEmpleado.getEmpleado().getId()));
/*  291: 374 */       if (listaRubroEmpleadoAux != null) {
/*  292: 375 */         for (RubroEmpleado rubroEmpleado : (List)listaRubroEmpleadoAux)
/*  293:     */         {
/*  294: 376 */           PagoRolEmpleadoRubro pagoRolEmpleadoRubro = null;
/*  295:     */           
/*  296: 378 */           Rubro rubro = rubroEmpleado.getRubro();
/*  297: 379 */           boolean eliminar = false;
/*  298: 380 */           if ((rubro.getMesPago() != 0) && (rubro.getMesPago() != pagoRol.getMes())) {
/*  299: 381 */             eliminar = true;
/*  300:     */           }
/*  301: 383 */           if (!mapaPagoRolEmpeladoRubro.containsKey(Integer.valueOf(rubro.getId())))
/*  302:     */           {
/*  303: 384 */             if (!eliminar)
/*  304:     */             {
/*  305: 385 */               pagoRolEmpleadoRubro = new PagoRolEmpleadoRubro();
/*  306: 386 */               pagoRolEmpleadoRubro.setPagoRolEmpleado(pagoRolEmpleado);
/*  307: 387 */               pagoRolEmpleadoRubro.setRubro(rubro);
/*  308: 388 */               pagoRolEmpleadoRubro.setValor(rubroEmpleado.getValor());
/*  309: 389 */               pagoRolEmpleadoRubro.setTiempo(BigDecimal.ZERO);
/*  310:     */             }
/*  311:     */           }
/*  312:     */           else
/*  313:     */           {
/*  314: 393 */             pagoRolEmpleadoRubro = (PagoRolEmpleadoRubro)mapaPagoRolEmpeladoRubro.get(Integer.valueOf(rubro.getId()));
/*  315: 394 */             if (!eliminar)
/*  316:     */             {
/*  317: 395 */               if (rubro.getTipoRubro().equals(TipoRubro.FIJO)) {
/*  318: 396 */                 pagoRolEmpleadoRubro.setValor(rubroEmpleado.getValor());
/*  319:     */               }
/*  320:     */             }
/*  321:     */             else {
/*  322: 399 */               pagoRolEmpleadoRubro.setEliminado(true);
/*  323:     */             }
/*  324:     */           }
/*  325: 402 */           if (!eliminar)
/*  326:     */           {
/*  327: 403 */             pagoRolEmpleadoRubro.setIndicadorCalculoIESS(rubro.isIndicadorCalculoIESS());
/*  328: 404 */             pagoRolEmpleadoRubro.setIndicadorCalculoImpuestoRenta(rubro.isIndicadorCalculoImpuestoRenta());
/*  329: 405 */             pagoRolEmpleadoRubro.setIndicadorImpresionSobre(rubro.isIndicadorImpresionSobre());
/*  330: 406 */             pagoRolEmpleadoRubro.setIndicadorProvision(rubro.isIndicadorProvision());
/*  331: 407 */             pagoRolEmpleadoRubro.setIndicadorTiempo(rubro.isIndicadorTiempo());
/*  332: 408 */             this.pagoRolEmpleadoRubroDao.guardar(pagoRolEmpleadoRubro);
/*  333:     */           }
/*  334:     */         }
/*  335:     */       }
/*  336:     */     }
/*  337:     */     PagoRolEmpleado pagoRolEmpleado;
/*  338:     */     Map<Integer, PagoRolEmpleadoRubro> mapaPagoRolEmpeladoRubro;
/*  339:     */     PagoRolEmpleadoRubro pagoRolEmpleadoRubro;
/*  340:     */   }
/*  341:     */   
/*  342:     */   public List<Object[]> getDatosArchivoVariacionesIESS(PagoRol pagoRol, Sucursal sucursal)
/*  343:     */   {
/*  344: 419 */     return this.pagoRolDao.getDatosArchivoVariacionesIESS(pagoRol, sucursal);
/*  345:     */   }
/*  346:     */   
/*  347:     */   public List<Object[]> getArchivoDecimocuarto(PagoRol pagoRol, Rubro rubroDC, int idOrganizacion)
/*  348:     */   {
/*  349: 424 */     Calendar calDesde = Calendar.getInstance();
/*  350: 425 */     calDesde.set(pagoRol.getAnio() - 1, rubroDC.getMesCalculoDesde() - 1, 1, 0, 0, 0);
/*  351: 426 */     Calendar calHasta = Calendar.getInstance();
/*  352: 427 */     calHasta.set(pagoRol.getAnio(), rubroDC.getMesCalculoHasta(), 1, 0, 0, 0);
/*  353: 428 */     calHasta.add(5, -1);
/*  354: 429 */     Date fechaDesdeDC = calDesde.getTime();
/*  355: 430 */     Date fechaHastaDC = calHasta.getTime();
/*  356:     */     
/*  357: 432 */     List<RubroEmpleado> listaRubroEmpleado = this.servicioRubroEmpleado.obtenerRubroPorFormula(idOrganizacion, null, "f");
/*  358: 433 */     Map<Integer, Rubro> mapaRubroEmpleado = new HashMap();
/*  359: 435 */     for (RubroEmpleado rubroEmpleado : listaRubroEmpleado) {
/*  360: 436 */       mapaRubroEmpleado.put(Integer.valueOf(rubroEmpleado.getEmpleado().getIdEmpleado()), rubroEmpleado.getRubro());
/*  361:     */     }
/*  362: 439 */     Object listaRetencionJudicial = this.pagoRolDao.getValoresRetenidos(pagoRol.getIdPagoRol());
/*  363: 440 */     Map<Integer, BigDecimal> mapaRetencionJudicial = new HashMap();
/*  364: 441 */     for (Object[] objects : (List)listaRetencionJudicial) {
/*  365: 442 */       mapaRetencionJudicial.put((Integer)objects[0], (BigDecimal)objects[1]);
/*  366:     */     }
/*  367: 444 */     Object listaDecimoCuarto = this.pagoRolDao.getArchivoDecimocuarto(idOrganizacion, fechaHastaDC, fechaDesdeDC);
/*  368: 445 */     Map<Integer, Object[]> mapaDecimoCuartoEmpleado = new HashMap();
/*  369: 446 */     Map<Integer, Object[]> mapaDecimoCuarto = new HashMap();
/*  370: 447 */     for (Object[] objects : (List)listaDecimoCuarto) {
/*  371: 448 */       if (mapaRubroEmpleado.containsKey(objects[18]))
/*  372:     */       {
/*  373: 449 */         Date fechaDesde = (Date)objects[15];
/*  374:     */         
/*  375: 451 */         Date fechaHasta = (Date)objects[16];
/*  376: 452 */         fechaHasta = fechaHasta == null ? fechaHastaDC : fechaHasta;
/*  377:     */         
/*  378: 454 */         fechaDesde = fechaDesde.before(fechaDesdeDC) ? fechaDesdeDC : fechaDesde;
/*  379: 455 */         fechaHasta = fechaHasta.after(fechaHastaDC) ? fechaHastaDC : fechaHasta;
/*  380:     */         
/*  381: 457 */         long diasTrabajados = FuncionesUtiles.diferenciasDeFechas30Dias(fechaDesde, fechaHasta);
/*  382: 459 */         if (((Boolean)objects[14]).booleanValue()) {
/*  383: 460 */           diasTrabajados -= this.pagoRolDao.getDiasFaltaContratoEventual(((Integer)objects[17]).intValue(), fechaDesde, fechaHasta);
/*  384:     */         }
/*  385: 463 */         objects[5] = Long.valueOf(diasTrabajados);
/*  386:     */         
/*  387: 465 */         mapaDecimoCuarto.put((Integer)objects[13], objects);
/*  388: 466 */         mapaDecimoCuartoEmpleado.put((Integer)objects[18], objects);
/*  389:     */       }
/*  390:     */     }
/*  391: 470 */     for (Object[] objects : mapaDecimoCuartoEmpleado.values())
/*  392:     */     {
/*  393: 471 */       BigDecimal valorRetencionJudicial = (BigDecimal)mapaRetencionJudicial.get((Integer)objects[17]);
/*  394: 472 */       objects[11] = (valorRetencionJudicial != null ? valorRetencionJudicial : objects[11]);
/*  395:     */     }
/*  396: 475 */     for (RubroEmpleado rubroEmpleado : listaRubroEmpleado)
/*  397:     */     {
/*  398: 476 */       Object[] objects = (Object[])mapaDecimoCuartoEmpleado.get(Integer.valueOf(rubroEmpleado.getEmpleado().getIdEmpleado()));
/*  399: 477 */       if (objects != null) {
/*  400: 478 */         objects[12] = (rubroEmpleado.getRubro().isIndicadorProvision() ? "" : "X");
/*  401:     */       }
/*  402:     */     }
/*  403: 481 */     listaDecimoCuarto = new ArrayList(mapaDecimoCuarto.values());
/*  404: 482 */     return listaDecimoCuarto;
/*  405:     */   }
/*  406:     */   
/*  407:     */   public List<Object[]> getArchivoUtilidades(int idPagoRol, int idOrganizacion)
/*  408:     */   {
/*  409: 488 */     HashMap<String, Object[]> hmUtilidades = new HashMap();
/*  410: 489 */     List<Object[]> listaUtilidades = this.pagoRolDao.getArchivoUtilidades(idPagoRol, idOrganizacion);
/*  411: 490 */     for (Iterator localIterator = listaUtilidades.iterator(); localIterator.hasNext();)
/*  412:     */     {
/*  413: 490 */       datoUtilidad = (Object[])localIterator.next();
/*  414: 491 */       String identificacion = (String)datoUtilidad[0];
/*  415: 492 */       hmUtilidades.put(identificacion, datoUtilidad);
/*  416:     */     }
/*  417:     */     Object[] datoUtilidad;
/*  418: 495 */     Object listaJornadaParcialPorcentaje = this.pagoRolDao.listaJornadaParcialPorcentaje(idPagoRol);
/*  419: 496 */     for (Object[] datoJornadaParcialPorcentaje : (List)listaJornadaParcialPorcentaje)
/*  420:     */     {
/*  421: 497 */       identificacion = (String)datoJornadaParcialPorcentaje[0];
/*  422: 498 */       Object[] datoRecuperadoUtilidad = (Object[])hmUtilidades.get(identificacion);
/*  423: 499 */       if (datoRecuperadoUtilidad != null)
/*  424:     */       {
/*  425: 500 */         datoRecuperadoUtilidad[8] = datoJornadaParcialPorcentaje[1];
/*  426: 501 */         datoRecuperadoUtilidad[9] = datoJornadaParcialPorcentaje[2];
/*  427:     */       }
/*  428:     */     }
/*  429:     */     String identificacion;
/*  430: 505 */     PagoRol pagoRol = buscarPorId(idPagoRol);
/*  431:     */     
/*  432: 507 */     List<Object[]> listaBeneficiosAdicionalesPorAnio = this.pagoRolDao.beneficiosAdicionalesPorAnio(pagoRol.getAnio() - 1, pagoRol.getIdOrganizacion());
/*  433: 508 */     for (Object[] datoBeneficio : listaBeneficiosAdicionalesPorAnio)
/*  434:     */     {
/*  435: 509 */       String identificacion = (String)datoBeneficio[0];
/*  436: 510 */       Object[] datoRecuperadoUtilidad = (Object[])hmUtilidades.get(identificacion);
/*  437: 511 */       if (datoRecuperadoUtilidad != null) {
/*  438: 512 */         datoRecuperadoUtilidad[18] = datoBeneficio[1];
/*  439:     */       }
/*  440:     */     }
/*  441: 516 */     return listaUtilidades;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public List<Object[]> getValorPagoRubros(int idOrganizacion, int anio)
/*  445:     */   {
/*  446: 521 */     return this.pagoRolDao.getValorPagoRubros(idOrganizacion, anio);
/*  447:     */   }
/*  448:     */   
/*  449:     */   public int getRubroID(String formula)
/*  450:     */   {
/*  451: 525 */     return this.pagoRolDao.getRubroID(formula);
/*  452:     */   }
/*  453:     */   
/*  454:     */   public List<Rubro> getRubroDecimoTercero(int idOrganizacion)
/*  455:     */   {
/*  456: 530 */     return this.pagoRolDao.getRubroDecimoTercero(idOrganizacion);
/*  457:     */   }
/*  458:     */   
/*  459:     */   public List<RelacionDependencia> obtenerDatosRDEP(int anio, int idOrganizacion, List<Integer> lista)
/*  460:     */   {
/*  461: 540 */     List<RelacionDependencia> listaRDEP = this.pagoRolDao.obtenerDatosRDEP(anio, idOrganizacion, lista);
/*  462: 541 */     Map<String, RelacionDependencia> hmRDEP = new HashMap();
/*  463: 542 */     for (Iterator localIterator = listaRDEP.iterator(); localIterator.hasNext();)
/*  464:     */     {
/*  465: 542 */       dato = (RelacionDependencia)localIterator.next();
/*  466: 543 */       hmRDEP.put(dato.getCedula(), dato);
/*  467:     */     }
/*  468: 547 */     Object listaRubroOtraEmpresa = this.rubroOtraEmpresaDao.getRubroOtraEmpresa(idOrganizacion, null, anio);
/*  469: 548 */     for (RelacionDependencia dato = ((List)listaRubroOtraEmpresa).iterator(); dato.hasNext();)
/*  470:     */     {
/*  471: 548 */       dato = (RubroOtraEmpresa)dato.next();
/*  472: 549 */       RelacionDependencia fila = (RelacionDependencia)hmRDEP.get(dato.getCedula());
/*  473: 551 */       if (fila != null)
/*  474:     */       {
/*  475: 552 */         fila.setSueldoSalarioOtroEmpleador(fila.getSueldoSalarioOtroEmpleador().add(dato.getValorIngreso()));
/*  476: 553 */         fila.setAportePersonalIessOtroEmpleador(fila.getAportePersonalIessOtroEmpleador().add(dato.getAportePersonalIessOtroEmpleador()));
/*  477: 554 */         fila.setValorRetenidoOtroEmpleado(fila.getValorRetenidoOtroEmpleado().add(dato.getValorRetenido()));
/*  478:     */         
/*  479:     */ 
/*  480:     */ 
/*  481: 558 */         fila.setSueldoAgravadaContribucionOtroEmpleador(dato.getSueldoAgravadaContribucion());
/*  482: 559 */         fila.setMesesTrabajadosVigenciaContribucionOtroEmpleador(dato.getMesesTrabajadosVigenciaContribucion());
/*  483: 560 */         fila.setMesesTrabajadosContribucionOtroEmpleador(dato.getMesesTrabajadosContribucion());
/*  484:     */       }
/*  485:     */     }
/*  486:     */     RubroOtraEmpresa dato;
/*  487: 568 */     Utilidad utilidad = this.utilidadDao.obtenerUtilidadPorAnio(idOrganizacion, anio - 1);
/*  488: 569 */     if (utilidad != null)
/*  489:     */     {
/*  490: 570 */       utilidad = this.utilidadDao.cargarDetalle(utilidad.getIdUtilidad());
/*  491: 571 */       for (dato = utilidad.getListaDetalleUtilidad().iterator(); dato.hasNext();)
/*  492:     */       {
/*  493: 571 */         detalle = (DetalleUtilidad)dato.next();
/*  494: 573 */         if (detalle.getEmpleado() != null)
/*  495:     */         {
/*  496: 574 */           RelacionDependencia fila = (RelacionDependencia)hmRDEP.get(detalle.getEmpleado().getEmpresa().getIdentificacion());
/*  497: 576 */           if (fila != null) {
/*  498: 577 */             fila.setUtilidades(fila.getUtilidades().add(detalle.getValor10().add(detalle.getValor5())));
/*  499:     */           }
/*  500:     */         }
/*  501:     */       }
/*  502:     */     }
/*  503: 585 */     List<Object[]> listaGastoDeducibleSRI = this.pagoRolDao.obtenerDatosRDEPGastosDeducibles(anio, idOrganizacion);
/*  504: 586 */     for (DetalleUtilidad detalle = listaGastoDeducibleSRI.iterator(); detalle.hasNext();)
/*  505:     */     {
/*  506: 586 */       gastoDeducible = (Object[])detalle.next();
/*  507: 587 */       RelacionDependencia fila = (RelacionDependencia)hmRDEP.get(gastoDeducible[0].toString());
/*  508: 589 */       if (fila != null)
/*  509:     */       {
/*  510: 590 */         fila.setDeducibleAlimentacion((BigDecimal)gastoDeducible[1]);
/*  511: 591 */         fila.setDeducibleEducacion((BigDecimal)gastoDeducible[2]);
/*  512: 592 */         fila.setDeducibleSalud((BigDecimal)gastoDeducible[3]);
/*  513: 593 */         fila.setDeducibleVestimenta((BigDecimal)gastoDeducible[4]);
/*  514: 594 */         fila.setDeducibleVivienda((BigDecimal)gastoDeducible[5]);
/*  515:     */       }
/*  516:     */     }
/*  517:     */     Object[] gastoDeducible;
/*  518: 600 */     List<RelacionDependencia> listaContribucionSolidaria = this.pagoRolDao.obtenerDatosContribucionSolidariaRDEP(anio, idOrganizacion);
/*  519: 601 */     for (RelacionDependencia contribucionSolidaria : listaContribucionSolidaria)
/*  520:     */     {
/*  521: 602 */       RelacionDependencia fila = (RelacionDependencia)hmRDEP.get(contribucionSolidaria.getCedula());
/*  522: 603 */       if (fila != null)
/*  523:     */       {
/*  524: 604 */         fila.setSueldoAgravadaContribucionEmpleador(contribucionSolidaria.getSueldoAgravadaContribucionEmpleador());
/*  525: 605 */         fila.setSobreSueldoAgravadaContribucionEmpleador(contribucionSolidaria.getSobreSueldoAgravadaContribucionEmpleador());
/*  526: 606 */         fila.setMesesTrabajadosVigenciaContribucionEmpleador(contribucionSolidaria.getMesesTrabajadosVigenciaContribucionEmpleador());
/*  527: 607 */         fila.setMesesTrabajadosContribucionEmpleador(contribucionSolidaria.getMesesTrabajadosContribucionEmpleador());
/*  528: 608 */         fila.setSueldoAgravadaContribucionOtroEmpleador(contribucionSolidaria.getSueldoAgravadaContribucionOtroEmpleador());
/*  529: 609 */         fila.setMesesTrabajadosVigenciaContribucionOtroEmpleador(contribucionSolidaria.getMesesTrabajadosVigenciaContribucionOtroEmpleador());
/*  530: 610 */         fila.setMesesTrabajadosContribucionOtroEmpleador(contribucionSolidaria.getMesesTrabajadosContribucionOtroEmpleador());
/*  531: 611 */         fila.setContribucionRetenida(contribucionSolidaria.getContribucionRetenida());
/*  532:     */       }
/*  533:     */     }
/*  534: 616 */     return listaRDEP;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void actualizarPagoCash(int idPagoRol, PagoCash pagoCash, boolean indicadorAnular, boolean indicadorCobrado)
/*  538:     */   {
/*  539: 626 */     this.pagoRolDao.actualizarPagoCash(idPagoRol, pagoCash, indicadorAnular, indicadorCobrado);
/*  540:     */   }
/*  541:     */   
/*  542:     */   public boolean validarPagoRolAnterior(Date fechaRol, int idOrganizacion)
/*  543:     */   {
/*  544: 636 */     return this.pagoRolDao.validarPagoRolAnterior(fechaRol, idOrganizacion);
/*  545:     */   }
/*  546:     */   
/*  547:     */   public void actualizarDiasTrabajados(PagoRol pagoRol, Integer idEmpleado)
/*  548:     */   {
/*  549: 648 */     List<Object[]> lista = this.pagoRolDao.obtenerPagoRolEmpleadoActivos(pagoRol.getId(), FuncionesUtiles.getFechaInicioMes(pagoRol.getFecha()), pagoRol
/*  550: 649 */       .getFecha());
/*  551: 651 */     for (Object[] objects : lista)
/*  552:     */     {
/*  553: 653 */       int diasTrabajados = 0;
/*  554: 654 */       int idPagoRolEmpleado = ((Integer)objects[0]).intValue();
/*  555: 655 */       Date fechaIngreso = (Date)objects[1];
/*  556: 656 */       Date fechaSalida = (Date)objects[2];
/*  557: 657 */       Date fechaPagoRol = pagoRol.getFecha();
/*  558: 658 */       Date fechaInicioPagoRol = FuncionesUtiles.getFechaInicioMes(fechaPagoRol);
/*  559: 660 */       if (fechaIngreso.compareTo(fechaInicioPagoRol) < 0) {
/*  560: 661 */         fechaIngreso = fechaInicioPagoRol;
/*  561:     */       }
/*  562: 664 */       if ((fechaSalida == null) || (fechaSalida.compareTo(fechaPagoRol) > 0)) {
/*  563: 665 */         fechaSalida = fechaPagoRol;
/*  564:     */       }
/*  565: 668 */       BigDecimal mesesValidos = this.servicioPagoRolEmpleado.obtenerMesesValidos(fechaIngreso, fechaSalida);
/*  566: 669 */       diasTrabajados = mesesValidos.multiply(new BigDecimal(30.0D)).setScale(0, RoundingMode.HALF_UP).intValue();
/*  567: 671 */       if (diasTrabajados >= 0) {
/*  568: 672 */         this.pagoRolEmpleadoDao.actualizarDiasTrabajados(idPagoRolEmpleado, diasTrabajados);
/*  569:     */       }
/*  570:     */     }
/*  571:     */   }
/*  572:     */   
/*  573:     */   public List<PagoRol> obtenerPagoRol(Date fechaDesde, Date fechaHasta, int idSucursal)
/*  574:     */   {
/*  575: 684 */     return this.pagoRolDao.obtenerPagoRol(fechaDesde, fechaHasta, idSucursal);
/*  576:     */   }
/*  577:     */   
/*  578:     */   public PagoRol obtenerPagoRolPorDiaMes(int mes, int anio)
/*  579:     */   {
/*  580: 695 */     return this.pagoRolDao.obtenerPagoRolPorDiaMes(mes, anio);
/*  581:     */   }
/*  582:     */   
/*  583:     */   public PagoRol procesarPagoRol(PagoRol pagoRol, int idOrganizacion, int idSucursal, Integer idEmpleado, boolean simular)
/*  584:     */     throws ExcepcionAS2Nomina, ExcepcionAS2Financiero, ExcepcionAS2
/*  585:     */   {
/*  586:     */     try
/*  587:     */     {
/*  588: 707 */       int diasQuincena = 30;
/*  589: 708 */       int idRubroSueldo = ParametrosSistema.getRubroSalarioUnificado(pagoRol.getIdOrganizacion()).intValue();
/*  590:     */       Map<Integer, List<PagoRolEmpleadoRubro>> mapaPagoRolEmpleadoRubro;
/*  591:     */       List<PagoRolEmpleadoRubro> listaPagoRolEmpladoRubroAux;
/*  592: 709 */       if (!pagoRol.getQuincena().isIndicadorFiniquito())
/*  593:     */       {
/*  594: 710 */         pagoRol = cargarDetalle(pagoRol.getIdPagoRol());
/*  595: 713 */         if (pagoRol.getQuincena().isIndicadorAnticipo()) {
/*  596: 714 */           diasQuincena = FuncionesUtiles.getDiaFecha(pagoRol.getFecha());
/*  597:     */         }
/*  598: 718 */         mapaPagoRolEmpleadoRubro = new HashMap();
/*  599: 719 */         listaPagoRolEmpleadoRubro = this.servicioPagoRolEmpleado.cargarRubrosPorEmpleado(pagoRol);
/*  600: 720 */         for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : listaPagoRolEmpleadoRubro)
/*  601:     */         {
/*  602: 721 */           listaPagoRolEmpladoRubroAux = (List)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(pagoRolEmpleadoRubro.getPagoRolEmpleado().getId()));
/*  603: 722 */           if (listaPagoRolEmpladoRubroAux == null) {
/*  604: 723 */             listaPagoRolEmpladoRubroAux = new ArrayList();
/*  605:     */           }
/*  606: 725 */           listaPagoRolEmpladoRubroAux.add(pagoRolEmpleadoRubro);
/*  607: 726 */           mapaPagoRolEmpleadoRubro.put(Integer.valueOf(pagoRolEmpleadoRubro.getPagoRolEmpleado().getId()), listaPagoRolEmpladoRubroAux);
/*  608:     */         }
/*  609: 729 */         for (??? = pagoRol.getListaPagoRolEmpleado().iterator(); ???.hasNext();)
/*  610:     */         {
/*  611: 729 */           pagoRolEmpleado = (PagoRolEmpleado)???.next();
/*  612: 730 */           pagoRolEmpleado.setListaPagoRolEmpleadoRubro((List)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(pagoRolEmpleado.getId())));
/*  613: 731 */           if (pagoRolEmpleado.getListaPagoRolEmpleadoRubro() == null) {
/*  614: 732 */             pagoRolEmpleado.setListaPagoRolEmpleadoRubro(new ArrayList());
/*  615:     */           }
/*  616: 735 */           for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : pagoRolEmpleado.getListaPagoRolEmpleadoRubro()) {
/*  617: 736 */             pagoRolEmpleadoRubro.setPagoRolEmpleado(pagoRolEmpleado);
/*  618:     */           }
/*  619:     */         }
/*  620:     */       }
/*  621:     */       PagoRolEmpleado pagoRolEmpleado;
/*  622: 740 */       HashMap<Integer, HashMap<Integer, PagoRolEmpleadoRubro>> mapaPagoRolEmpleadoRubro = new HashMap();
/*  623: 741 */       for (List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro = pagoRol.getListaPagoRolEmpleado().iterator(); listaPagoRolEmpleadoRubro.hasNext();)
/*  624:     */       {
/*  625: 741 */         pagoRolEmpleado = (PagoRolEmpleado)listaPagoRolEmpleadoRubro.next();
/*  626: 742 */         HashMap<Integer, PagoRolEmpleadoRubro> mapa = new HashMap();
/*  627: 743 */         for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : ((PagoRolEmpleado)pagoRolEmpleado).getListaPagoRolEmpleadoRubro())
/*  628:     */         {
/*  629: 744 */           pagoRolEmpleadoRubro.setIndicadorCalculoIESS(pagoRolEmpleadoRubro.getRubro().isIndicadorCalculoIESS());
/*  630: 745 */           if (!pagoRolEmpleadoRubro.isEliminado())
/*  631:     */           {
/*  632: 746 */             pagoRolEmpleadoRubro.setIdOrganizacion(pagoRol.getIdOrganizacion());
/*  633: 748 */             if ((pagoRolEmpleadoRubro.getRubro().getIdRubro() == idRubroSueldo) && 
/*  634: 749 */               (pagoRolEmpleadoRubro.getRubro().isIndicadorDiasTrabajados()))
/*  635:     */             {
/*  636: 750 */               ((PagoRolEmpleado)pagoRolEmpleado).setSalarioAsignado(pagoRolEmpleadoRubro.getValor());
/*  637: 751 */               valorProporcional(pagoRolEmpleadoRubro, diasQuincena);
/*  638:     */             }
/*  639: 753 */             mapa.put(Integer.valueOf(pagoRolEmpleadoRubro.getRubro().getIdRubro()), pagoRolEmpleadoRubro);
/*  640:     */           }
/*  641:     */         }
/*  642: 757 */         if (((PagoRolEmpleado)pagoRolEmpleado).getHistoricoEmpleado() != null) {
/*  643: 758 */           mapaPagoRolEmpleadoRubro.put(Integer.valueOf(((PagoRolEmpleado)pagoRolEmpleado).getHistoricoEmpleado().getIdHistoricoEmpleado()), mapa);
/*  644: 759 */         } else if (((PagoRolEmpleado)pagoRolEmpleado).getHistoricoEmpleadoFiniquito() != null) {
/*  645: 760 */           mapaPagoRolEmpleadoRubro.put(Integer.valueOf(((PagoRolEmpleado)pagoRolEmpleado).getHistoricoEmpleadoFiniquito().getIdHistoricoEmpleado()), mapa);
/*  646:     */         } else {
/*  647: 762 */           mapaPagoRolEmpleadoRubro.put(Integer.valueOf(((PagoRolEmpleado)pagoRolEmpleado).getEmpleado().getIdEmpleado()), mapa);
/*  648:     */         }
/*  649:     */       }
/*  650: 765 */       AppUtil.setAtributo("mapaPagoRolEmpleadoRubro", mapaPagoRolEmpleadoRubro);
/*  651: 768 */       for (Object pagoRolEmpleado = pagoRol.getListaPagoRolEmpleado().iterator(); ((Iterator)pagoRolEmpleado).hasNext();)
/*  652:     */       {
/*  653: 768 */         PagoRolEmpleado pagoRolEmpleado = (PagoRolEmpleado)((Iterator)pagoRolEmpleado).next();
/*  654: 769 */         BigDecimal valorAPagar = BigDecimal.ZERO;
/*  655: 770 */         pagoRolEmpleado.setDiasProceso(0);
/*  656: 772 */         for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : pagoRolEmpleado.getListaPagoRolEmpleadoRubro()) {
/*  657: 773 */           if (!pagoRolEmpleadoRubro.isEliminado())
/*  658:     */           {
/*  659: 774 */             Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/*  660: 775 */             if ((rubro.getRubroPadre() != null) && 
/*  661: 776 */               (!rubro.getTipo().equals(TipoRubroEnum.DECIMO_TERCERO)) && (!rubro.getTipo().equals(TipoRubroEnum.DECIMO_CUARTO)))
/*  662:     */             {
/*  663: 777 */               rubro.getRubroPadre().getIdRubro();
/*  664: 778 */               BigDecimal valorPadre = this.servicioPagoRolEmpleado.obtenerValorRubroPadre(pagoRol
/*  665: 779 */                 .getAnio(), pagoRol
/*  666: 780 */                 .getMes(), 
/*  667: 781 */                 (pagoRolEmpleado.getHistoricoEmpleado() != null ? pagoRolEmpleado.getHistoricoEmpleado() : pagoRolEmpleado
/*  668: 782 */                 .getHistoricoEmpleadoFiniquito()).getId(), rubro.getRubroPadre().getIdRubro());
/*  669: 783 */               pagoRolEmpleadoRubro.setValor(valorPadre);
/*  670:     */             }
/*  671: 786 */             else if (rubro.getFormula().compareTo("") != 0)
/*  672:     */             {
/*  673: 788 */               BigDecimal valor = FuncionesUtiles.redondearBigDecimal(BigDecimal.valueOf(FuncionRol.evaluarFuncion(rubro
/*  674: 789 */                 .getFormula(), pagoRolEmpleadoRubro).doubleValue()));
/*  675: 792 */               if (rubro.isIndicadorTiempo())
/*  676:     */               {
/*  677: 793 */                 if (pagoRolEmpleadoRubro.getTiempo().compareTo(BigDecimal.ZERO) > 0) {
/*  678: 794 */                   pagoRolEmpleadoRubro.setValor(FuncionesUtiles.redondearBigDecimal(valor.multiply(pagoRolEmpleadoRubro
/*  679: 795 */                     .getTiempo())));
/*  680:     */                 } else {
/*  681: 797 */                   pagoRolEmpleadoRubro.setValor(BigDecimal.ZERO);
/*  682:     */                 }
/*  683:     */               }
/*  684:     */               else {
/*  685: 800 */                 pagoRolEmpleadoRubro.setValor(valor);
/*  686:     */               }
/*  687:     */             }
/*  688: 802 */             else if (rubro.isIndicadorTiempo())
/*  689:     */             {
/*  690: 803 */               throw new ExcepcionAS2Nomina("msg_error_formula", rubro.getNombre());
/*  691:     */             }
/*  692: 806 */             if ((rubro.getIdRubro() != idRubroSueldo) && (rubro.isIndicadorDiasTrabajados())) {
/*  693: 807 */               valorProporcional(pagoRolEmpleadoRubro, diasQuincena);
/*  694:     */             }
/*  695: 809 */             if ((pagoRolEmpleadoRubro.getRubro().isIndicadorImpresionSobre()) && (!pagoRolEmpleadoRubro.isIndicadorNoProcesado())) {
/*  696: 810 */               valorAPagar = valorAPagar.add(pagoRolEmpleadoRubro.getValor().multiply(BigDecimal.valueOf(rubro.getOperacion())));
/*  697:     */             }
/*  698:     */           }
/*  699:     */         }
/*  700: 815 */         pagoRolEmpleado.setCentroCosto(pagoRolEmpleado.getEmpleado().getCentroCosto());
/*  701: 816 */         pagoRolEmpleado.setDepartamento(pagoRolEmpleado.getEmpleado().getDepartamento());
/*  702: 817 */         pagoRolEmpleado.setValorAPagar(valorAPagar);
/*  703:     */       }
/*  704: 820 */       pagoRol.setIndicadorReprocesar(Boolean.valueOf(false));
/*  705: 821 */       if (!simular) {
/*  706: 822 */         guardar(pagoRol);
/*  707:     */       }
/*  708:     */     }
/*  709:     */     catch (ExcepcionAS2Nomina e)
/*  710:     */     {
/*  711: 825 */       this.context.setRollbackOnly();
/*  712: 826 */       throw e;
/*  713:     */     }
/*  714:     */     catch (Exception e)
/*  715:     */     {
/*  716: 828 */       this.context.setRollbackOnly();
/*  717: 829 */       e.printStackTrace();
/*  718: 830 */       throw new ExcepcionAS2(e);
/*  719:     */     }
/*  720: 832 */     return pagoRol;
/*  721:     */   }
/*  722:     */   
/*  723:     */   public void actualizarPagoRolDias(PagoRol pagoRol, int idOrganizacion, int idSucursal, Integer idEmpleado)
/*  724:     */     throws ExcepcionAS2Nomina
/*  725:     */   {
/*  726: 837 */     actualizarPagoRolEmpleado(pagoRol, idOrganizacion, idSucursal, idEmpleado);
/*  727: 838 */     actualizarDiasTrabajados(pagoRol, idEmpleado);
/*  728:     */     
/*  729: 840 */     generarRubrosNoProcesados(pagoRol);
/*  730:     */   }
/*  731:     */   
/*  732:     */   public void cargarPagosRol(String fileName, InputStream imInputStream, int finaInicial)
/*  733:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, IOException
/*  734:     */   {
/*  735: 854 */     HashMap<String, Empleado> hmEmpleado = new HashMap();
/*  736: 855 */     HashMap<String, Rubro> hmRubro = new HashMap();
/*  737: 856 */     HashMap<String, Documento> hmDocumento = new HashMap();
/*  738: 857 */     HashMap<String, Quincena> hmQuincena = new HashMap();
/*  739: 858 */     HashMap<Date, PagoRol> hmPagoRol = new HashMap();
/*  740: 859 */     HashMap<String, PagoRolEmpleado> hmPagoRolEmpleado = new HashMap();
/*  741:     */     try
/*  742:     */     {
/*  743: 864 */       datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, finaInicial, 0);
/*  744:     */     }
/*  745:     */     catch (IOException e1)
/*  746:     */     {
/*  747:     */       HSSFCell[][] datos;
/*  748: 866 */       throw new ExcepcionAS2("msg_error_cargar_datos");
/*  749:     */     }
/*  750:     */     HSSFCell[][] datos;
/*  751: 893 */     int filaActual = finaInicial + 1;
/*  752: 894 */     int columnaActual = 0;
/*  753:     */     
/*  754: 896 */     HSSFCell[] filaErronea = new HSSFCell[0];
/*  755:     */     
/*  756:     */ 
/*  757: 899 */     Quincena q = null;
/*  758: 900 */     HashMap<String, String> filtros = new HashMap();
/*  759: 901 */     filtros.put("codigo", datos[0][9].getStringCellValue());
/*  760: 902 */     List<Quincena> list = this.servicioQuincena.obtenerListaCombo("", false, filtros);
/*  761: 903 */     if (list.isEmpty()) {
/*  762: 904 */       throw new ExcepcionAS2("msg_error_quincena_no_encontrada", " " + datos[0][9].getStringCellValue());
/*  763:     */     }
/*  764: 906 */     q = (Quincena)list.get(0);
/*  765:     */     
/*  766: 908 */     Date fecha = datos[0][3].getDateCellValue();
/*  767: 909 */     if (this.pagoRolDao.obtenerPagoRol(FuncionesUtiles.getMes(fecha), FuncionesUtiles.getAnio(fecha), q.getIdQuincena(), AppUtil.getOrganizacion()
/*  768: 910 */       .getId()) != null) {
/*  769: 911 */       throw new ExcepcionAS2("msg_error_fecha_pago_rol", " " + FuncionesUtiles.dateToString(fecha));
/*  770:     */     }
/*  771: 915 */     for (HSSFCell[] fila : datos)
/*  772:     */     {
/*  773:     */       try
/*  774:     */       {
/*  775: 919 */         filaErronea = fila;
/*  776:     */         
/*  777: 921 */         columnaActual = 0;
/*  778: 922 */         String identificacion = fila[0].getStringCellValue().trim();
/*  779: 923 */         columnaActual = 3;
/*  780: 924 */         fecha = fila[3].getDateCellValue();
/*  781:     */         
/*  782: 926 */         columnaActual = 4;
/*  783: 927 */         String codigoRubro = fila[4].getStringCellValue().trim();
/*  784: 928 */         columnaActual = 5;
/*  785: 929 */         BigDecimal valor = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[5].getNumericCellValue()), 2);
/*  786:     */         
/*  787: 931 */         columnaActual = 6;
/*  788: 932 */         BigDecimal tiempo = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[6].getNumericCellValue()), 2);
/*  789:     */         
/*  790: 934 */         columnaActual = 7;
/*  791: 935 */         int diasFalta = (int)fila[7].getNumericCellValue();
/*  792:     */         
/*  793: 937 */         columnaActual = 8;
/*  794: 938 */         String nombreDocumento = fila[8].getStringCellValue().trim();
/*  795:     */         
/*  796: 940 */         columnaActual = 9;
/*  797: 941 */         String codigoQuincena = fila[9].getStringCellValue().trim();
/*  798: 942 */         columnaActual = 10;
/*  799: 943 */         boolean indicadorTiempo = fila[10].getStringCellValue().trim().equalsIgnoreCase("SI");
/*  800:     */         
/*  801: 945 */         columnaActual = 11;
/*  802: 946 */         boolean indicadorProvision = fila[11].getStringCellValue().trim().equalsIgnoreCase("SI");
/*  803:     */         
/*  804: 948 */         columnaActual = 12;
/*  805: 949 */         boolean indicadorImpresionSobre = fila[12].getStringCellValue().trim().equalsIgnoreCase("SI");
/*  806:     */         
/*  807: 951 */         columnaActual = 13;
/*  808: 952 */         boolean indicadorCalculoIESS = fila[13].getStringCellValue().trim().equalsIgnoreCase("SI");
/*  809:     */         
/*  810: 954 */         columnaActual = 14;
/*  811: 955 */         indicadorCalculoImpuestoRenta = fila[14].getStringCellValue().trim().equalsIgnoreCase("SI");
/*  812:     */       }
/*  813:     */       catch (IllegalArgumentException e)
/*  814:     */       {
/*  815:     */         boolean indicadorCalculoImpuestoRenta;
/*  816: 957 */         this.context.setRollbackOnly();
/*  817:     */         
/*  818: 959 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/*  819:     */       }
/*  820:     */       catch (IllegalStateException e)
/*  821:     */       {
/*  822: 961 */         this.context.setRollbackOnly();
/*  823:     */         
/*  824: 963 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/*  825:     */       }
/*  826:     */       catch (Exception e)
/*  827:     */       {
/*  828: 965 */         this.context.setRollbackOnly();
/*  829: 966 */         e.printStackTrace();
/*  830:     */         
/*  831: 968 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/*  832:     */       }
/*  833:     */       boolean indicadorCalculoImpuestoRenta;
/*  834:     */       boolean indicadorCalculoIESS;
/*  835:     */       boolean indicadorImpresionSobre;
/*  836:     */       boolean indicadorProvision;
/*  837:     */       boolean indicadorTiempo;
/*  838:     */       BigDecimal valor;
/*  839:     */       BigDecimal tiempo;
/*  840:     */       int diasFalta;
/*  841:     */       String identificacion;
/*  842:     */       String codigoQuincena;
/*  843:     */       String nombreDocumento;
/*  844:     */       String codigoRubro;
/*  845: 970 */       Empleado empleado = (Empleado)hmEmpleado.get(identificacion);
/*  846: 971 */       if (empleado == null)
/*  847:     */       {
/*  848: 972 */         empleado = this.servicioEmpleado.bucarEmpleadoPorIdentificacion(identificacion, AppUtil.getOrganizacion().getId());
/*  849: 973 */         if (empleado == null) {
/*  850: 974 */           throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + identificacion);
/*  851:     */         }
/*  852:     */       }
/*  853: 977 */       Quincena quincena = (Quincena)hmQuincena.get(codigoQuincena);
/*  854: 978 */       if (quincena == null)
/*  855:     */       {
/*  856: 979 */         HashMap<String, String> filters = new HashMap();
/*  857: 980 */         filters.put("codigo", codigoQuincena);
/*  858: 981 */         List<Quincena> lista = this.servicioQuincena.obtenerListaCombo("", false, filters);
/*  859: 982 */         if (lista.isEmpty()) {
/*  860: 983 */           throw new ExcepcionAS2("msg_error_quincena_no_encontrada", " " + codigoQuincena);
/*  861:     */         }
/*  862: 985 */         quincena = (Quincena)lista.get(0);
/*  863:     */       }
/*  864: 988 */       Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/*  865: 989 */       if (documento == null)
/*  866:     */       {
/*  867: 990 */         HashMap<String, String> filters = new HashMap();
/*  868: 991 */         filters.put("nombre", nombreDocumento);
/*  869: 992 */         List<Documento> lista = this.servicioDocumento.obtenerListaCombo("", false, filters);
/*  870: 993 */         if (lista.isEmpty()) {
/*  871: 994 */           throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + nombreDocumento);
/*  872:     */         }
/*  873: 996 */         documento = (Documento)lista.get(0);
/*  874:     */       }
/*  875: 999 */       Rubro rubro = (Rubro)hmRubro.get(codigoRubro);
/*  876:1000 */       if (rubro == null)
/*  877:     */       {
/*  878:1001 */         HashMap<String, String> filters = new HashMap();
/*  879:1002 */         filters.put("codigo", codigoRubro);
/*  880:1003 */         List<Rubro> lista = this.servicioRubro.obtenerListaCombo("", false, filters);
/*  881:1004 */         if (lista.isEmpty()) {
/*  882:1005 */           throw new ExcepcionAS2("msg_error_rubro_no_encontrado", " " + codigoRubro);
/*  883:     */         }
/*  884:1007 */         rubro = (Rubro)lista.get(0);
/*  885:     */       }
/*  886:1010 */       String clavePagoRolEmpleado = identificacion + FuncionesUtiles.dateToString(fecha);
/*  887:     */       
/*  888:1012 */       int anio = FuncionesUtiles.getAnio(fecha);
/*  889:1013 */       int mes = FuncionesUtiles.getMes(fecha);
/*  890:     */       
/*  891:1015 */       PagoRol pagoRol = (PagoRol)hmPagoRol.get(fecha);
/*  892:1016 */       if (pagoRol == null)
/*  893:     */       {
/*  894:1017 */         pagoRol = new PagoRol();
/*  895:1018 */         pagoRol.setIdSucursal(AppUtil.getSucursal().getId());
/*  896:1019 */         pagoRol.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  897:1020 */         pagoRol.setFecha(fecha);
/*  898:1021 */         pagoRol.setAnio(anio);
/*  899:1022 */         pagoRol.setMes(mes);
/*  900:1023 */         pagoRol.setEstado(Estado.ELABORADO);
/*  901:1024 */         pagoRol.setIndicadorSaldoInicial(true);
/*  902:1025 */         pagoRol.setDocumento(documento);
/*  903:1026 */         pagoRol.setQuincena(quincena);
/*  904:1027 */         hmPagoRol.put(fecha, pagoRol);
/*  905:     */       }
/*  906:1029 */       PagoRolEmpleado pagoRolEmpleado = (PagoRolEmpleado)hmPagoRolEmpleado.get(clavePagoRolEmpleado);
/*  907:1030 */       if (pagoRolEmpleado == null) {
/*  908:     */         try
/*  909:     */         {
/*  910:1033 */           HistoricoEmpleado historicoEmpleado = this.servicioHistoricoEmpleado.obtenerPeriodoActivo(empleado.getIdEmpleado(), fecha);
/*  911:     */           
/*  912:1035 */           pagoRolEmpleado = new PagoRolEmpleado();
/*  913:1036 */           pagoRolEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/*  914:1037 */           pagoRolEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  915:1038 */           pagoRolEmpleado.setDiasFalta(diasFalta);
/*  916:1039 */           pagoRolEmpleado.setIndicadorCobrado(true);
/*  917:1040 */           pagoRolEmpleado.setValorAPagar(BigDecimal.ZERO);
/*  918:1041 */           pagoRolEmpleado.setEmpleado(empleado);
/*  919:1042 */           pagoRolEmpleado.setHistoricoEmpleado(historicoEmpleado);
/*  920:1043 */           pagoRolEmpleado.setDocumentoReferencia("");
/*  921:1044 */           pagoRolEmpleado.setPagoRol(pagoRol);
/*  922:1045 */           pagoRol.getListaPagoRolEmpleado().add(pagoRolEmpleado);
/*  923:1046 */           hmPagoRolEmpleado.put(clavePagoRolEmpleado, pagoRolEmpleado);
/*  924:     */         }
/*  925:     */         catch (ExcepcionAS2Nomina e)
/*  926:     */         {
/*  927:1050 */           throw new ExcepcionAS2Nomina(e.getCodigoExcepcion(), " " + empleado.getEmpresa().getIdentificacion() + " " + empleado.getNombres());
/*  928:     */         }
/*  929:     */       }
/*  930:1054 */       PagoRolEmpleadoRubro pagoRolEmpleadoRubro = new PagoRolEmpleadoRubro();
/*  931:1055 */       pagoRolEmpleadoRubro.setIdSucursal(AppUtil.getSucursal().getId());
/*  932:1056 */       pagoRolEmpleadoRubro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  933:1057 */       pagoRolEmpleadoRubro.setTiempo(tiempo);
/*  934:1058 */       pagoRolEmpleadoRubro.setValor(valor);
/*  935:1059 */       pagoRolEmpleadoRubro.setRubro(rubro);
/*  936:1060 */       pagoRolEmpleadoRubro.setIndicadorTiempo(indicadorTiempo);
/*  937:1061 */       pagoRolEmpleadoRubro.setIndicadorCalculoIESS(indicadorCalculoIESS);
/*  938:1062 */       pagoRolEmpleadoRubro.setIndicadorImpresionSobre(indicadorImpresionSobre);
/*  939:1063 */       pagoRolEmpleadoRubro.setIndicadorProvision(indicadorProvision);
/*  940:1064 */       pagoRolEmpleadoRubro.setIndicadorCalculoImpuestoRenta(indicadorCalculoImpuestoRenta);
/*  941:1065 */       pagoRolEmpleadoRubro.setPagoRolEmpleado(pagoRolEmpleado);
/*  942:1066 */       pagoRolEmpleado.getListaPagoRolEmpleadoRubro().add(pagoRolEmpleadoRubro);
/*  943:     */     }
/*  944:     */     try
/*  945:     */     {
/*  946:1071 */       for (??? = hmPagoRol.values().iterator(); ((Iterator)???).hasNext();)
/*  947:     */       {
/*  948:1071 */         PagoRol pr = (PagoRol)((Iterator)???).next();
/*  949:1072 */         guardar(pr);
/*  950:     */       }
/*  951:     */     }
/*  952:     */     catch (ExcepcionAS2Financiero e)
/*  953:     */     {
/*  954:1076 */       this.context.setRollbackOnly();
/*  955:1077 */       throw new ExcepcionAS2(e.getCodigoExcepcion());
/*  956:     */     }
/*  957:     */     catch (ExcepcionAS2 e)
/*  958:     */     {
/*  959:1079 */       this.context.setRollbackOnly();
/*  960:1080 */       throw new ExcepcionAS2(e.getCodigoExcepcion());
/*  961:     */     }
/*  962:     */     catch (Exception e)
/*  963:     */     {
/*  964:1082 */       this.context.setRollbackOnly();
/*  965:1083 */       e.printStackTrace();
/*  966:1084 */       throw new ExcepcionAS2("msg_error_guardar", e);
/*  967:     */     }
/*  968:     */   }
/*  969:     */   
/*  970:     */   public List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubroFiniquito(HistoricoEmpleado historicoEmpleado)
/*  971:     */   {
/*  972:1091 */     return this.pagoRolEmpleadoRubroDao.getListaPagoRolEmpleadoRubroFiniquito(historicoEmpleado);
/*  973:     */   }
/*  974:     */   
/*  975:     */   public List<AprobacionPagoRol> getDatosAprobacionPagoRol(int idOrganizacion, Sucursal sucursal, PagoRol pagoRol, Departamento departamento)
/*  976:     */   {
/*  977:1117 */     List<String> listaCabecerasIngresos = new ArrayList();
/*  978:1118 */     List<String> listaCabecerasEgresos = new ArrayList();
/*  979:     */     
/*  980:1120 */     Map<Integer, Integer> hmColumnaRubro = new HashMap();
/*  981:1121 */     Map<Integer, AprobacionPagoRol> hmPagoRolEmpleado = new TreeMap();
/*  982:     */     
/*  983:1123 */     List<Object[]> listaRubros = this.reporteNominaDao.getRubrosAprobacionPagoRol(idOrganizacion, sucursal, pagoRol, departamento);
/*  984:1124 */     for (Object[] dato : listaRubros)
/*  985:     */     {
/*  986:1126 */       int idRubro = ((Integer)dato[0]).intValue();
/*  987:1127 */       int operacion = ((Integer)dato[2]).intValue();
/*  988:1128 */       String cabecera = String.valueOf(dato[1]);
/*  989:1129 */       if (operacion > 0)
/*  990:     */       {
/*  991:1130 */         hmColumnaRubro.put(Integer.valueOf(idRubro), Integer.valueOf(listaCabecerasIngresos.size()));
/*  992:1131 */         listaCabecerasIngresos.add(cabecera);
/*  993:     */       }
/*  994:     */       else
/*  995:     */       {
/*  996:1133 */         hmColumnaRubro.put(Integer.valueOf(idRubro), Integer.valueOf(listaCabecerasEgresos.size()));
/*  997:1134 */         listaCabecerasEgresos.add(cabecera);
/*  998:     */       }
/*  999:     */     }
/* 1000:1139 */     BigDecimal totalIngresos = BigDecimal.ZERO;
/* 1001:1140 */     BigDecimal totalEgresos = BigDecimal.ZERO;
/* 1002:1141 */     List<Object[]> datosPagoRolEmpleado = this.reporteNominaDao.getDatosAprobacionPagoRol(idOrganizacion, sucursal, pagoRol, departamento);
/* 1003:     */     
/* 1004:     */ 
/* 1005:1144 */     Date fecha_t_1 = FuncionesUtiles.sumarFechaDiasMeses(FuncionesUtiles.getFechaInicioMes(pagoRol.getFecha()), -1);
/* 1006:1145 */     Date fecha_t_2 = FuncionesUtiles.sumarFechaDiasMeses(FuncionesUtiles.getFechaInicioMes(fecha_t_1), -1);
/* 1007:     */     
/* 1008:1147 */     Quincena quincena = pagoRol.getQuincena();
/* 1009:1148 */     List<Object[]> ingresosPorEmpleado_t_2 = this.reporteNominaDao.getIngresosPorEmpleado(idOrganizacion, sucursal, quincena, fecha_t_2, departamento);
/* 1010:1149 */     List<Object[]> ingresosPorEmpleado_t_1 = this.reporteNominaDao.getIngresosPorEmpleado(idOrganizacion, sucursal, quincena, fecha_t_1, departamento);
/* 1011:1150 */     Map<Integer, BigDecimal> hmIngresos_t_2 = new HashMap();
/* 1012:1151 */     Map<Integer, BigDecimal> hmIngresos_t_1 = new HashMap();
/* 1013:1153 */     for (Object[] ingresot_2 : ingresosPorEmpleado_t_2) {
/* 1014:1154 */       hmIngresos_t_2.put((Integer)ingresot_2[0], (BigDecimal)ingresot_2[1]);
/* 1015:     */     }
/* 1016:1156 */     for (Object[] ingresot_1 : ingresosPorEmpleado_t_1) {
/* 1017:1157 */       hmIngresos_t_1.put((Integer)ingresot_1[0], (BigDecimal)ingresot_1[1]);
/* 1018:     */     }
/* 1019:1161 */     for (??? = datosPagoRolEmpleado.iterator(); ???.hasNext();)
/* 1020:     */     {
/* 1021:1161 */       dato = (Object[])???.next();
/* 1022:1162 */       Integer clave = (Integer)dato[0];
/* 1023:1163 */       AprobacionPagoRol datosEmpleado = (AprobacionPagoRol)hmPagoRolEmpleado.get(clave);
/* 1024:1164 */       BigDecimal valor = (BigDecimal)dato[10];
/* 1025:1166 */       if (datosEmpleado == null)
/* 1026:     */       {
/* 1027:1167 */         int diasTrabajados = FuncionesUtiles.obtenerDiaFecha((Date)dato[13]) - ((Integer)dato[6]).intValue();
/* 1028:     */         
/* 1029:     */ 
/* 1030:1170 */         datosEmpleado = new AprobacionPagoRol(((Integer)dato[15]).intValue(), dato[2].toString(), dato[3].toString(), dato[4].toString(), dato[5].toString(), diasTrabajados, (BigDecimal)dato[7], ((Boolean)dato[1]).booleanValue(), ((Boolean)dato[14]).booleanValue());
/* 1031:     */         
/* 1032:1172 */         datosEmpleado.setCabecerasIngresos(listaCabecerasIngresos);
/* 1033:1173 */         datosEmpleado.setIngresos(new BigDecimal[listaCabecerasIngresos.size()]);
/* 1034:     */         
/* 1035:1175 */         datosEmpleado.setCabecerasEgresos(listaCabecerasEgresos);
/* 1036:1176 */         datosEmpleado.setEgresos(new BigDecimal[listaCabecerasEgresos.size()]);
/* 1037:1177 */         datosEmpleado.setTotalIngresost_2((BigDecimal)hmIngresos_t_2.get(clave));
/* 1038:1178 */         datosEmpleado.setTotalIngresost_1((BigDecimal)hmIngresos_t_1.get(clave));
/* 1039:     */         
/* 1040:1180 */         hmPagoRolEmpleado.put(clave, datosEmpleado);
/* 1041:     */         
/* 1042:1182 */         totalIngresos = BigDecimal.ZERO;
/* 1043:1183 */         totalEgresos = BigDecimal.ZERO;
/* 1044:     */       }
/* 1045:1186 */       int c = ((Integer)hmColumnaRubro.get(dato[8])).intValue();
/* 1046:1187 */       if (valor.compareTo(BigDecimal.ZERO) > 0) {
/* 1047:1188 */         datosEmpleado.getIngresos()[c] = valor;
/* 1048:     */       } else {
/* 1049:1190 */         datosEmpleado.getEgresos()[c] = valor;
/* 1050:     */       }
/* 1051:1193 */       if (valor.compareTo(BigDecimal.ZERO) > 0) {
/* 1052:1194 */         totalIngresos = totalIngresos.add(valor);
/* 1053:     */       } else {
/* 1054:1196 */         totalEgresos = totalEgresos.add(valor);
/* 1055:     */       }
/* 1056:1199 */       datosEmpleado.setTotalIngresos(totalIngresos);
/* 1057:1200 */       datosEmpleado.setTotalEgresos(totalEgresos);
/* 1058:     */     }
/* 1059:     */     Object[] dato;
/* 1060:1203 */     Object hmPagoRolEmpleadoOrdenado = new TreeMap();
/* 1061:1204 */     for (AprobacionPagoRol prre : hmPagoRolEmpleado.values())
/* 1062:     */     {
/* 1063:1206 */       if ((prre.getTotalIngresost_2() != null) && (prre.getTotalIngresos().compareTo(prre.getTotalIngresost_2()) > 0)) {
/* 1064:1207 */         prre.setIndicadorVariaciont_2(true);
/* 1065:     */       }
/* 1066:1210 */       if ((prre.getTotalIngresost_1() != null) && (prre.getTotalIngresos().compareTo(prre.getTotalIngresost_1()) > 0)) {
/* 1067:1211 */         prre.setIndicadorVariaciont_1(true);
/* 1068:     */       }
/* 1069:1214 */       String clave = prre.getApellidos() + prre.getNombres() + prre.getIdPagoRolEmpleado();
/* 1070:1215 */       ((Map)hmPagoRolEmpleadoOrdenado).put(clave, prre);
/* 1071:     */     }
/* 1072:1217 */     List<AprobacionPagoRol> datosAprobacionPagoRol = new ArrayList(((Map)hmPagoRolEmpleadoOrdenado).values());
/* 1073:     */     
/* 1074:1219 */     return datosAprobacionPagoRol;
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1078:     */   public void guardar(PagoRol pagoRol, List<AprobacionPagoRol> lista)
/* 1079:     */     throws ExcepcionAS2
/* 1080:     */   {
/* 1081:     */     try
/* 1082:     */     {
/* 1083:1231 */       PagoRol pagoRolAux = this.servicioPagoRol.buscarPorId(pagoRol.getId());
/* 1084:1232 */       if (pagoRolAux.getEstado() == Estado.CONTABILIZADO) {
/* 1085:1233 */         throw new ExcepcionAS2("msg_mensaje_error_rol_contabilizado");
/* 1086:     */       }
/* 1087:1235 */       for (AprobacionPagoRol apRol : lista) {
/* 1088:1237 */         if (apRol.isIndicadorAprobado() != apRol.isIndicadorAprobadoAnterior()) {
/* 1089:1239 */           this.pagoRolEmpleadoDao.aprobarPagoRolEmpleado(apRol);
/* 1090:     */         }
/* 1091:     */       }
/* 1092:1243 */       this.pagoRolDao.actualizarEstadoAprobacion(pagoRol);
/* 1093:     */     }
/* 1094:     */     catch (ExcepcionAS2 e)
/* 1095:     */     {
/* 1096:1246 */       this.context.setRollbackOnly();
/* 1097:1247 */       throw e;
/* 1098:     */     }
/* 1099:     */     catch (Exception e)
/* 1100:     */     {
/* 1101:1249 */       this.context.setRollbackOnly();
/* 1102:1250 */       throw new ExcepcionAS2("msg_error_guardar");
/* 1103:     */     }
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public List<Rubro> getRubroTipo(TipoRubroEnum tipo, int idOrganizacion)
/* 1107:     */   {
/* 1108:1256 */     return this.pagoRolDao.getRubroTipo(tipo, idOrganizacion);
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public PagoRol obtenerPagoRolPorQuincenaMesAnio(int idOrganizacion, Quincena quincena, int mes, int anio)
/* 1112:     */   {
/* 1113:1261 */     return this.pagoRolDao.obtenerPagoRolPorQuincenaMesAnio(idOrganizacion, quincena, mes, anio);
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public void procesarPropina(PagoRol pagoRol, PagoRol pagoRolDiasTrabajados, BigDecimal valor)
/* 1117:     */     throws AS2Exception
/* 1118:     */   {
/* 1119:1267 */     BigDecimal totalDiasTrabajadas = BigDecimal.ZERO;
/* 1120:1268 */     BigDecimal valorPorDiaPropina = BigDecimal.ZERO;
/* 1121:1269 */     BigDecimal valorTotalPropina = BigDecimal.ZERO;
/* 1122:1270 */     BigDecimal diferencia = BigDecimal.ZERO;
/* 1123:1271 */     Date fechaDesde = FuncionesUtiles.getFechaInicioMes(pagoRolDiasTrabajados.getFecha());
/* 1124:1272 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(pagoRolDiasTrabajados.getFecha());
/* 1125:1273 */     int diasTrabajados = FuncionesUtiles.getDiaFecha(pagoRol.getFecha());
/* 1126:     */     
/* 1127:1275 */     Rubro rubroPropina = this.rubroDao.obtenerRubroPorTipoRubro(TipoRubroEnum.PAGO_PROPINA, pagoRol.getIdOrganizacion());
/* 1128:1276 */     if (rubroPropina == null) {
/* 1129:1277 */       throw new AS2Exception("com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPagoRolImpl.CONFIGURAR_RUBRO_RETENCION_JUDICIAL", new String[] { "" });
/* 1130:     */     }
/* 1131:1280 */     List<HistoricoEmpleado> listaEmpleadosInactivosPagoRolEmpleado = this.pagoRolEmpleadoDao.getEmpleadosInactivosPagoRolEmpleadoPropina(pagoRol, pagoRolDiasTrabajados);
/* 1132:1282 */     for (HistoricoEmpleado historicoEmpleado : listaEmpleadosInactivosPagoRolEmpleado)
/* 1133:     */     {
/* 1134:1283 */       pagoRolEmpleado = new PagoRolEmpleado();
/* 1135:1284 */       pagoRolEmpleado.setIdOrganizacion(pagoRol.getIdOrganizacion());
/* 1136:1285 */       pagoRolEmpleado.setPagoRol(pagoRol);
/* 1137:1286 */       pagoRolEmpleado.setEmpleado(historicoEmpleado.getEmpleado());
/* 1138:1287 */       pagoRolEmpleado.setHistoricoEmpleado(historicoEmpleado);
/* 1139:1288 */       pagoRolEmpleado.setDiasTrabajados(diasTrabajados);
/* 1140:     */       
/* 1141:1290 */       PagoRolEmpleadoRubro prer = new PagoRolEmpleadoRubro();
/* 1142:1291 */       prer.setIdOrganizacion(pagoRol.getIdOrganizacion());
/* 1143:1292 */       prer.setIdSucursal(pagoRol.getIdSucursal());
/* 1144:1293 */       prer.setPagoRolEmpleado(pagoRolEmpleado);
/* 1145:1294 */       prer.setIndicadorImpresionSobre(true);
/* 1146:1295 */       prer.setIndicadorTiempo(true);
/* 1147:1296 */       prer.setRubro(rubroPropina);
/* 1148:     */       
/* 1149:1298 */       this.pagoRolEmpleadoDao.guardar(pagoRolEmpleado);
/* 1150:1299 */       this.pagoRolEmpleadoRubroDao.guardar(prer);
/* 1151:     */     }
/* 1152:1302 */     Object hmPagoRolEmpleadoDiasTrabajados = new HashMap();
/* 1153:     */     
/* 1154:1304 */     List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubroPropina = new ArrayList();
/* 1155:     */     
/* 1156:1306 */     pagoRolDiasTrabajados = this.servicioPagoRol.cargarDetalle(pagoRolDiasTrabajados.getIdPagoRol());
/* 1157:1307 */     for (PagoRolEmpleado pagoRolEmpleado = pagoRolDiasTrabajados.getListaPagoRolEmpleado().iterator(); pagoRolEmpleado.hasNext();)
/* 1158:     */     {
/* 1159:1307 */       pagoRolEmpleado = (PagoRolEmpleado)pagoRolEmpleado.next();
/* 1160:1308 */       if (pagoRolEmpleado.getEmpleado().getCargoEmpleado().isIndicadorPropina()) {
/* 1161:1309 */         ((HashMap)hmPagoRolEmpleadoDiasTrabajados).put(pagoRolEmpleado.getEmpleado().getEmpresa().getIdentificacion(), pagoRolEmpleado);
/* 1162:     */       }
/* 1163:     */     }
/* 1164:     */     PagoRolEmpleado pagoRolEmpleado;
/* 1165:1313 */     List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro = this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubro(pagoRol.getIdPagoRol(), rubroPropina
/* 1166:1314 */       .getIdRubro());
/* 1167:1316 */     if ((listaPagoRolEmpleadoRubro.isEmpty()) || (listaPagoRolEmpleadoRubro.size() == 0)) {
/* 1168:1317 */       throw new AS2Exception("com.asinfo.as2.datosbase.servicio.impl.ServicioListaDescuentosImpl.REGISTRO_NO_ENCONTRADO_LISTA_DESCUENTO", new String[] { "" });
/* 1169:     */     }
/* 1170:1320 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : listaPagoRolEmpleadoRubro)
/* 1171:     */     {
/* 1172:1322 */       String identificacion = pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getEmpresa().getIdentificacion();
/* 1173:1323 */       PagoRolEmpleado pagoRolEmpleado = (PagoRolEmpleado)((HashMap)hmPagoRolEmpleadoDiasTrabajados).get(identificacion);
/* 1174:1325 */       if (pagoRolEmpleado != null)
/* 1175:     */       {
/* 1176:1327 */         pagoRolEmpleado.setDiasTrabajadosReales(BigDecimal.ZERO);
/* 1177:1328 */         BigDecimal porcentaje = new BigDecimal(100);
/* 1178:     */         try
/* 1179:     */         {
/* 1180:1331 */           HistoricoEmpleado historicoEmpleado = this.servicioHistoricoEmpleado.obtenerPeriodoActivo(pagoRolEmpleadoRubro
/* 1181:1332 */             .getPagoRolEmpleado().getEmpleado().getIdEmpleado(), fechaDesde, fechaHasta);
/* 1182:1333 */           List<DetalleHistoricoEmpleado> lista = this.detalleHistoricoEmpleadoDao.getListaDetalleHistoricoEmpleado(historicoEmpleado, fechaDesde, fechaHasta);
/* 1183:     */           BigDecimal diasTrabajadosDHE;
/* 1184:     */           int regitro;
/* 1185:1336 */           if (!lista.isEmpty())
/* 1186:     */           {
/* 1187:1339 */             diasTrabajadosDHE = BigDecimal.ZERO;
/* 1188:1340 */             regitro = 0;
/* 1189:1341 */             for (DetalleHistoricoEmpleado detalleHistoricoEmpleado : lista)
/* 1190:     */             {
/* 1191:1343 */               regitro++;
/* 1192:1344 */               Date fechaDesdeAux = fechaDesde;
/* 1193:1345 */               Date fechaHastaAux = fechaHasta;
/* 1194:1347 */               if ((historicoEmpleado.getFechaSalida() != null) && (historicoEmpleado.getFechaSalida().compareTo(fechaHastaAux) < 0)) {
/* 1195:1348 */                 fechaHastaAux = historicoEmpleado.getFechaSalida();
/* 1196:     */               }
/* 1197:1351 */               if (detalleHistoricoEmpleado.getFechaInicio().compareTo(fechaDesdeAux) > 0) {
/* 1198:1352 */                 fechaDesdeAux = detalleHistoricoEmpleado.getFechaInicio();
/* 1199:     */               }
/* 1200:1355 */               if ((detalleHistoricoEmpleado.getFechaFin() != null) && 
/* 1201:1356 */                 (detalleHistoricoEmpleado.getFechaFin().compareTo(fechaHastaAux) < 0)) {
/* 1202:1357 */                 fechaHastaAux = detalleHistoricoEmpleado.getFechaFin();
/* 1203:     */               }
/* 1204:1360 */               diasTrabajadosDHE = this.servicioPagoRolEmpleado.obtenerMesesValidos(fechaDesdeAux, fechaHastaAux).multiply(new BigDecimal(30));
/* 1205:1363 */               if (regitro == lista.size()) {
/* 1206:1365 */                 diasTrabajadosDHE = diasTrabajadosDHE.subtract(new BigDecimal(pagoRolEmpleado.getDiasFalta()));
/* 1207:     */               }
/* 1208:1368 */               porcentaje = detalleHistoricoEmpleado.getPorcentajeCapacidadSemanal();
/* 1209:1369 */               pagoRolEmpleado.setDiasTrabajadosReales(pagoRolEmpleado.getDiasTrabajadosReales().add(
/* 1210:1370 */                 FuncionesUtiles.porcentaje(diasTrabajadosDHE, porcentaje, 2)));
/* 1211:     */             }
/* 1212:     */           }
/* 1213:     */           else
/* 1214:     */           {
/* 1215:1374 */             pagoRolEmpleado.setDiasTrabajadosReales(FuncionesUtiles.porcentaje(new BigDecimal(pagoRolEmpleado.getDiasTrabajados() - pagoRolEmpleado
/* 1216:1375 */               .getDiasFalta()), porcentaje, 2));
/* 1217:     */           }
/* 1218:     */         }
/* 1219:     */         catch (ExcepcionAS2Nomina localExcepcionAS2Nomina) {}
/* 1220:1381 */         totalDiasTrabajadas = totalDiasTrabajadas.add(pagoRolEmpleado.getDiasTrabajadosReales());
/* 1221:     */       }
/* 1222:     */     }
/* 1223:1385 */     valorPorDiaPropina = valor.divide(totalDiasTrabajadas, 10, RoundingMode.HALF_UP);
/* 1224:1386 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : listaPagoRolEmpleadoRubro)
/* 1225:     */     {
/* 1226:1388 */       identificacion = pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getEmpresa().getIdentificacion();
/* 1227:1389 */       PagoRolEmpleado pagoRolEmpleado = (PagoRolEmpleado)((HashMap)hmPagoRolEmpleadoDiasTrabajados).get(identificacion);
/* 1228:1391 */       if (pagoRolEmpleado != null)
/* 1229:     */       {
/* 1230:1392 */         pagoRolEmpleadoRubro.setValor(valorPorDiaPropina.multiply(pagoRolEmpleado.getDiasTrabajadosReales()).setScale(2, RoundingMode.HALF_UP));
/* 1231:     */         
/* 1232:     */ 
/* 1233:1395 */         pagoRolEmpleadoRubro.setTiempo(pagoRolEmpleado.getDiasTrabajadosReales());
/* 1234:1396 */         pagoRolEmpleadoRubro.setIndicadorTiempo(true);
/* 1235:     */         
/* 1236:1398 */         valorTotalPropina = valorTotalPropina.add(pagoRolEmpleadoRubro.getValor());
/* 1237:1399 */         listaPagoRolEmpleadoRubroPropina.add(pagoRolEmpleadoRubro);
/* 1238:     */       }
/* 1239:     */       else
/* 1240:     */       {
/* 1241:1401 */         pagoRolEmpleadoRubro.setValor(BigDecimal.ZERO);
/* 1242:1402 */         pagoRolEmpleadoRubro.setTiempo(BigDecimal.ZERO);
/* 1243:     */       }
/* 1244:1405 */       pagoRolEmpleadoRubro.getPagoRolEmpleado().setDiasTrabajados(pagoRolEmpleadoRubro.getTiempo().intValue());
/* 1245:1406 */       pagoRolEmpleadoRubro.setIndicadorAutomatico(true);
/* 1246:     */     }
/* 1247:     */     String identificacion;
/* 1248:1409 */     diferencia = valor.subtract(valorTotalPropina);
/* 1249:1410 */     int i = 0;
/* 1250:1411 */     BigDecimal valorPropinaRepartir = new BigDecimal(0.01D);
/* 1251:1412 */     if (diferencia.compareTo(BigDecimal.ZERO) == -1) {
/* 1252:1413 */       valorPropinaRepartir = valorPropinaRepartir.negate();
/* 1253:     */     }
/* 1254:1415 */     while (diferencia.compareTo(BigDecimal.ZERO) != 0)
/* 1255:     */     {
/* 1256:1417 */       ((PagoRolEmpleadoRubro)listaPagoRolEmpleadoRubroPropina.get(i)).setValor(
/* 1257:1418 */         ((PagoRolEmpleadoRubro)listaPagoRolEmpleadoRubroPropina.get(i)).getValor().add(valorPropinaRepartir).setScale(2, RoundingMode.HALF_UP));
/* 1258:1419 */       i++;
/* 1259:1420 */       diferencia = diferencia.subtract(valorPropinaRepartir).setScale(2, RoundingMode.HALF_UP);
/* 1260:     */     }
/* 1261:1424 */     for (PagoRolEmpleadoRubro prer : listaPagoRolEmpleadoRubroPropina) {
/* 1262:1425 */       this.pagoRolEmpleadoRubroDao.guardar(prer);
/* 1263:     */     }
/* 1264:     */   }
/* 1265:     */   
/* 1266:     */   public List<RubroEmpleado> listaEmpleadosAsignarRubro(PagoRol pagoRol, EmpleadoAsistencia empleadoAsistencia, PagoRolEmpleado pagoRolEmpleado)
/* 1267:     */   {
/* 1268:1434 */     List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro = new ArrayList();
/* 1269:1435 */     if (empleadoAsistencia.getRubro25() != null) {
/* 1270:1436 */       listaPagoRolEmpleadoRubro.addAll(this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubroYEmpleado(pagoRol.getIdPagoRol(), empleadoAsistencia
/* 1271:1437 */         .getRubro25().getId(), empleadoAsistencia.getIdEmpleado()));
/* 1272:     */     }
/* 1273:1439 */     if (empleadoAsistencia.getRubro50() != null) {
/* 1274:1440 */       listaPagoRolEmpleadoRubro.addAll(this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubroYEmpleado(pagoRol.getIdPagoRol(), empleadoAsistencia
/* 1275:1441 */         .getRubro50().getId(), empleadoAsistencia.getIdEmpleado()));
/* 1276:     */     }
/* 1277:1443 */     if (empleadoAsistencia.getRubro100() != null) {
/* 1278:1444 */       listaPagoRolEmpleadoRubro.addAll(this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubroYEmpleado(pagoRol.getIdPagoRol(), empleadoAsistencia
/* 1279:1445 */         .getRubro100().getId(), empleadoAsistencia.getIdEmpleado()));
/* 1280:     */     }
/* 1281:1447 */     HashMap<String, PagoRolEmpleadoRubro> hmPagoRolEmplado = new HashMap();
/* 1282:1448 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : listaPagoRolEmpleadoRubro) {
/* 1283:1449 */       hmPagoRolEmplado.put(pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getEmpresa().getIdentificacion() + "-" + pagoRolEmpleadoRubro
/* 1284:1450 */         .getRubro().getId(), pagoRolEmpleadoRubro);
/* 1285:     */     }
/* 1286:1452 */     Object listaEmpleadosAsignarRubro = new ArrayList();
/* 1287:1453 */     Empleado empleado = this.servicioEmpleado.cargarEmpresa(empleadoAsistencia.getIdEmpleado());
/* 1288:1454 */     if (pagoRolEmpleado.getEmpleado().getIdEmpleado() == empleadoAsistencia.getIdEmpleado())
/* 1289:     */     {
/* 1290:1455 */       if ((empleadoAsistencia.getRubro25() != null) && 
/* 1291:1456 */         (!hmPagoRolEmplado.containsKey(empleado.getEmpresa().getIdentificacion() + "-" + empleadoAsistencia.getRubro25().getId())))
/* 1292:     */       {
/* 1293:1457 */         RubroEmpleado rubroEmpleado = new RubroEmpleado();
/* 1294:1458 */         rubroEmpleado.setEmpleado(empleado);
/* 1295:1459 */         rubroEmpleado.setIdOrganizacion(empleado.getIdOrganizacion());
/* 1296:1460 */         rubroEmpleado.setIdSucursal(empleado.getEmpresa().getIdSucursal());
/* 1297:1461 */         rubroEmpleado.setRubro(empleadoAsistencia.getRubro25());
/* 1298:1462 */         ((List)listaEmpleadosAsignarRubro).add(rubroEmpleado);
/* 1299:     */       }
/* 1300:1464 */       if ((empleadoAsistencia.getRubro50() != null) && 
/* 1301:1465 */         (!hmPagoRolEmplado.containsKey(empleado.getEmpresa().getIdentificacion() + "-" + empleadoAsistencia.getRubro50().getId())))
/* 1302:     */       {
/* 1303:1466 */         RubroEmpleado rubroEmpleado = new RubroEmpleado();
/* 1304:1467 */         rubroEmpleado.setEmpleado(empleado);
/* 1305:1468 */         rubroEmpleado.setIdOrganizacion(empleado.getIdOrganizacion());
/* 1306:1469 */         rubroEmpleado.setIdSucursal(empleado.getEmpresa().getIdSucursal());
/* 1307:1470 */         rubroEmpleado.setRubro(empleadoAsistencia.getRubro50());
/* 1308:1471 */         ((List)listaEmpleadosAsignarRubro).add(rubroEmpleado);
/* 1309:     */       }
/* 1310:1473 */       if ((empleadoAsistencia.getRubro100() != null) && 
/* 1311:1474 */         (!hmPagoRolEmplado.containsKey(empleado.getEmpresa().getIdentificacion() + "-" + empleadoAsistencia.getRubro100().getId())))
/* 1312:     */       {
/* 1313:1475 */         RubroEmpleado rubroEmpleado = new RubroEmpleado();
/* 1314:1476 */         rubroEmpleado.setEmpleado(empleado);
/* 1315:1477 */         rubroEmpleado.setIdOrganizacion(empleado.getIdOrganizacion());
/* 1316:1478 */         rubroEmpleado.setIdSucursal(empleado.getEmpresa().getIdSucursal());
/* 1317:1479 */         rubroEmpleado.setRubro(empleadoAsistencia.getRubro100());
/* 1318:1480 */         ((List)listaEmpleadosAsignarRubro).add(rubroEmpleado);
/* 1319:     */       }
/* 1320:     */     }
/* 1321:1483 */     return listaEmpleadosAsignarRubro;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public List<RubroEmpleado> listaEmpleadosRubroFaltas(PagoRol pagoRol, EmpleadoAsistencia empleadoAsistencia, PagoRolEmpleado pagoRolEmpleado)
/* 1325:     */   {
/* 1326:1490 */     List<RubroEmpleado> listaEmpleadosAsignarRubro = new ArrayList();
/* 1327:     */     
/* 1328:     */ 
/* 1329:1493 */     Empleado empleado = this.servicioEmpleado.cargarEmpresa(empleadoAsistencia.getIdEmpleado());
/* 1330:1494 */     if (pagoRolEmpleado.getEmpleado().getIdEmpleado() == empleadoAsistencia.getIdEmpleado())
/* 1331:     */     {
/* 1332:     */       Rubro rubroEA;
/* 1333:1496 */       if ((empleadoAsistencia.getTipoFalta() != null) && (empleadoAsistencia.getTipoFalta().getRubro() != null))
/* 1334:     */       {
/* 1335:1497 */         List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro = this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubroYEmpleado(pagoRol
/* 1336:1498 */           .getIdPagoRol(), empleadoAsistencia.getTipoFalta().getRubro().getId(), empleado.getId());
/* 1337:     */         
/* 1338:     */ 
/* 1339:     */ 
/* 1340:1502 */         Map<String, String> filters = new HashMap();
/* 1341:1503 */         filters.put("empleado.idEmpleado", empleado.getId() + "");
/* 1342:1504 */         filters.put("rubro.idRubro", empleadoAsistencia.getTipoFalta().getRubro().getId() + "");
/* 1343:1505 */         List<RubroEmpleado> listaRubroEmpleadoTemp = this.servicioRubroEmpleado.obtenerListaCombo(null, false, filters);
/* 1344:1506 */         if (listaRubroEmpleadoTemp.isEmpty())
/* 1345:     */         {
/* 1346:1507 */           RubroEmpleado rubroEmpleado = new RubroEmpleado();
/* 1347:1508 */           rubroEmpleado.setEmpleado(empleado);
/* 1348:1509 */           rubroEmpleado.setIdOrganizacion(empleado.getIdOrganizacion());
/* 1349:1510 */           rubroEmpleado.setIdSucursal(empleado.getEmpresa().getIdSucursal());
/* 1350:1511 */           rubroEmpleado.setRubro(empleadoAsistencia.getTipoFalta().getRubro());
/* 1351:1512 */           listaEmpleadosAsignarRubro.add(rubroEmpleado);
/* 1352:     */         }
/* 1353:1515 */         HashMap<String, PagoRolEmpleadoRubro> hmPagoRolEmplado = new HashMap();
/* 1354:1516 */         for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : listaPagoRolEmpleadoRubro) {
/* 1355:1517 */           hmPagoRolEmplado.put(pagoRolEmpleadoRubro
/* 1356:1518 */             .getPagoRolEmpleado().getEmpleado().getId() + "-" + pagoRolEmpleadoRubro.getRubro().getId(), pagoRolEmpleadoRubro);
/* 1357:     */         }
/* 1358:1522 */         PagoRolEmpleadoRubro pter = (PagoRolEmpleadoRubro)hmPagoRolEmplado.get(empleadoAsistencia.getIdEmpleado() + "-" + empleadoAsistencia.getTipoFalta().getRubro().getId());
/* 1359:1523 */         List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubroGuardar = new ArrayList();
/* 1360:1524 */         if (pter != null)
/* 1361:     */         {
/* 1362:1525 */           pter.setTiempo(new BigDecimal(empleadoAsistencia.getCantidadDiasFalta()));
/* 1363:1526 */           listaPagoRolEmpleadoRubroGuardar.add(pter);
/* 1364:     */         }
/* 1365:     */         else
/* 1366:     */         {
/* 1367:1528 */           rubroEA = empleadoAsistencia.getTipoFalta().getRubro();
/* 1368:1529 */           PagoRolEmpleadoRubro prer = new PagoRolEmpleadoRubro();
/* 1369:1530 */           prer.setIdOrganizacion(pagoRol.getIdOrganizacion());
/* 1370:1531 */           prer.setIdSucursal(pagoRol.getIdSucursal());
/* 1371:1532 */           prer.setPagoRolEmpleado(pagoRolEmpleado);
/* 1372:1533 */           prer.setIndicadorImpresionSobre(rubroEA.isIndicadorImpresionSobre());
/* 1373:1534 */           prer.setIndicadorTiempo(rubroEA.isIndicadorTiempo());
/* 1374:1535 */           prer.setRubro(rubroEA);
/* 1375:1536 */           prer.setTiempo(new BigDecimal(empleadoAsistencia.getCantidadDiasFalta()));
/* 1376:1537 */           listaPagoRolEmpleadoRubroGuardar.add(prer);
/* 1377:     */         }
/* 1378:1539 */         for (PagoRolEmpleadoRubro prer : listaPagoRolEmpleadoRubroGuardar) {
/* 1379:1540 */           this.pagoRolEmpleadoRubroDao.guardar(prer);
/* 1380:     */         }
/* 1381:     */       }
/* 1382:     */       else
/* 1383:     */       {
/* 1384:1546 */         int cantidadDiasFalta = empleadoAsistencia.getCantidadDiasFalta();
/* 1385:1548 */         if (empleadoAsistencia.getTipoFalta() != null) {
/* 1386:1549 */           cantidadDiasFalta *= empleadoAsistencia.getTipoFalta().getNumeroDiasFalta();
/* 1387:     */         }
/* 1388:1552 */         cantidadDiasFalta += pagoRolEmpleado.getDiasFalta();
/* 1389:1553 */         pagoRolEmpleado.setDiasFalta(cantidadDiasFalta);
/* 1390:1554 */         this.pagoRolEmpleadoDao.guardar(pagoRolEmpleado);
/* 1391:     */       }
/* 1392:     */     }
/* 1393:1557 */     return listaEmpleadosAsignarRubro;
/* 1394:     */   }
/* 1395:     */   
/* 1396:     */   public void procesarHorasExtra(PagoRol pagoRol, EmpleadoAsistencia empleadoAsistencia)
/* 1397:     */     throws AS2Exception
/* 1398:     */   {
/* 1399:1562 */     List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro = new ArrayList();
/* 1400:1563 */     List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubroGuardar = new ArrayList();
/* 1401:1564 */     if (empleadoAsistencia.getRubro25() != null) {
/* 1402:1565 */       listaPagoRolEmpleadoRubro.addAll(this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubro(pagoRol.getIdPagoRol(), empleadoAsistencia
/* 1403:1566 */         .getRubro25().getId()));
/* 1404:     */     }
/* 1405:1568 */     if (empleadoAsistencia.getRubro50() != null) {
/* 1406:1569 */       listaPagoRolEmpleadoRubro.addAll(this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubro(pagoRol.getIdPagoRol(), empleadoAsistencia
/* 1407:1570 */         .getRubro50().getId()));
/* 1408:     */     }
/* 1409:1572 */     if (empleadoAsistencia.getRubro100() != null) {
/* 1410:1573 */       listaPagoRolEmpleadoRubro.addAll(this.pagoRolEmpleadoRubroDao.cargarRubrosVariablesPorRubro(pagoRol.getIdPagoRol(), empleadoAsistencia
/* 1411:1574 */         .getRubro100().getId()));
/* 1412:     */     }
/* 1413:1577 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : listaPagoRolEmpleadoRubro) {
/* 1414:1578 */       if (pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getId() == empleadoAsistencia.getIdEmpleado())
/* 1415:     */       {
/* 1416:1579 */         if ((empleadoAsistencia.getRubro25() != null) && (pagoRolEmpleadoRubro.getRubro().getId() == empleadoAsistencia.getRubro25().getId()))
/* 1417:     */         {
/* 1418:1580 */           pagoRolEmpleadoRubro.setTiempo(empleadoAsistencia.getTotalHoras25Aprobadas());
/* 1419:1581 */           listaPagoRolEmpleadoRubroGuardar.add(pagoRolEmpleadoRubro);
/* 1420:     */         }
/* 1421:1583 */         if ((empleadoAsistencia.getRubro50() != null) && (pagoRolEmpleadoRubro.getRubro().getId() == empleadoAsistencia.getRubro50().getId()))
/* 1422:     */         {
/* 1423:1584 */           pagoRolEmpleadoRubro.setTiempo(empleadoAsistencia.getTotalHoras50Aprobadas());
/* 1424:1585 */           listaPagoRolEmpleadoRubroGuardar.add(pagoRolEmpleadoRubro);
/* 1425:     */         }
/* 1426:1587 */         if ((empleadoAsistencia.getRubro100() != null) && (pagoRolEmpleadoRubro.getRubro().getId() == empleadoAsistencia.getRubro100().getId()))
/* 1427:     */         {
/* 1428:1588 */           pagoRolEmpleadoRubro.setTiempo(empleadoAsistencia.getTotalHoras100Aprobadas().add(empleadoAsistencia.getTotalHoras100Feriado()));
/* 1429:1589 */           listaPagoRolEmpleadoRubroGuardar.add(pagoRolEmpleadoRubro);
/* 1430:     */         }
/* 1431:     */       }
/* 1432:     */     }
/* 1433:1593 */     for (PagoRolEmpleadoRubro prer : listaPagoRolEmpleadoRubroGuardar) {
/* 1434:1594 */       this.pagoRolEmpleadoRubroDao.guardar(prer);
/* 1435:     */     }
/* 1436:     */   }
/* 1437:     */   
/* 1438:     */   public List<DetalleFiniquitoEmpleado> obtenerValoresPagadosPorRubrosYFechas(int idOrganizacion, Empleado empleado, List<Rubro> listaRubros, Date fechaDesde, Date fechaHasta)
/* 1439:     */   {
/* 1440:1602 */     return this.pagoRolDao.obtenerValoresPagadosPorRubrosYFechas(idOrganizacion, empleado, listaRubros, fechaDesde, fechaHasta);
/* 1441:     */   }
/* 1442:     */   
/* 1443:     */   public List<Object[]> getArchivoDecimotercero(int idOrganizacion, Date fechaDesde, Date fechaHasta, PagoRol pagoRol)
/* 1444:     */   {
/* 1445:1609 */     List<Object[]> lresult = this.pagoRolDao.getArchivoDecimotercero(idOrganizacion, fechaDesde, fechaHasta);
/* 1446:1612 */     for (Object[] line : lresult)
/* 1447:     */     {
/* 1448:1615 */       List<DetalleHistoricoEmpleado> ldhe = this.pagoRolDao.getContratosEmpleado(((Integer)line[16]).intValue());
/* 1449:1616 */       if (null != ldhe)
/* 1450:     */       {
/* 1451:1617 */         int hsl = ParametrosSistema.getHorasSemanaLaboral(idOrganizacion).intValue();
/* 1452:1618 */         int horas = ((DetalleHistoricoEmpleado)ldhe.get(0)).getHorasSemana();
/* 1453:1619 */         if (horas < hsl)
/* 1454:     */         {
/* 1455:1620 */           line[8] = "x";
/* 1456:1621 */           line[9] = Integer.valueOf(horas);
/* 1457:     */         }
/* 1458:1624 */         BigDecimal valorRetenido = this.pagoRolDao.getValorRetenido(((Integer)line[16]).intValue(), pagoRol.getId());
/* 1459:1625 */         line[11] = (valorRetenido.compareTo(BigDecimal.ZERO) > 0 ? valorRetenido : "");
/* 1460:     */       }
/* 1461:     */     }
/* 1462:1628 */     return lresult;
/* 1463:     */   }
/* 1464:     */   
/* 1465:     */   public void generarRubrosNoProcesados(PagoRol pagoRol)
/* 1466:     */   {
/* 1467:     */     Map<HistoricoEmpleado, PagoRolEmpleado> hmPagoRolEmpleado;
/* 1468:     */     PagoRolEmpleado pre;
/* 1469:1634 */     if (FuncionesUtiles.getDiaFecha(pagoRol.getFecha()) >= 27)
/* 1470:     */     {
/* 1471:1636 */       Date fechaRolPrevio = FuncionesUtiles.sumarFechaMeses(pagoRol.getFecha(), -1);
/* 1472:     */       
/* 1473:1638 */       List<PagoRolEmpleado> listaPagoRolEmpleado = this.servicioPagoRolEmpleado.getPagoRolEmpleado(pagoRol);
/* 1474:1639 */       hmPagoRolEmpleado = new HashMap();
/* 1475:1640 */       for (Iterator localIterator = listaPagoRolEmpleado.iterator(); localIterator.hasNext();)
/* 1476:     */       {
/* 1477:1640 */         pre = (PagoRolEmpleado)localIterator.next();
/* 1478:1641 */         hmPagoRolEmpleado.put(pre.getHistoricoEmpleado(), pre);
/* 1479:     */       }
/* 1480:1644 */       Object lista = this.pagoRolDao.obtenerRubrosNoProcesados(pagoRol.getIdOrganizacion(), pagoRol.getFecha(), fechaRolPrevio);
/* 1481:1645 */       for (PagoRolEmpleadoRubro prer : (List)lista)
/* 1482:     */       {
/* 1483:1647 */         this.pagoRolEmpleadoRubroDao.detach(prer);
/* 1484:1648 */         PagoRolEmpleado pagoRolEmpleado = (PagoRolEmpleado)hmPagoRolEmpleado.get(prer.getPagoRolEmpleado().getHistoricoEmpleado());
/* 1485:1649 */         if (pagoRolEmpleado != null)
/* 1486:     */         {
/* 1487:1651 */           PagoRolEmpleadoRubro prer2 = new PagoRolEmpleadoRubro();
/* 1488:1652 */           prer2.setPagoRolEmpleado(pagoRolEmpleado);
/* 1489:1653 */           prer2.setPagoRolEmpleadoRubroPadre(prer);
/* 1490:1654 */           prer2.setIdOrganizacion(prer.getIdOrganizacion());
/* 1491:1655 */           prer2.setIndicadorAutomatico(true);
/* 1492:1656 */           prer2.setRubro(prer.getRubro());
/* 1493:1657 */           prer2.setValor(prer.getValor());
/* 1494:1658 */           prer2.setIdSucursal(prer.getIdSucursal());
/* 1495:1659 */           prer2.setIndicadorCalculoIESS(prer.isIndicadorCalculoIESS());
/* 1496:1660 */           prer2.setIndicadorCalculoImpuestoRenta(prer.isIndicadorCalculoImpuestoRenta());
/* 1497:1661 */           prer2.setIndicadorImpresionSobre(prer.isIndicadorImpresionSobre());
/* 1498:1662 */           prer2.setIndicadorProvision(prer.isIndicadorProvision());
/* 1499:1663 */           prer2.setIndicadorTiempo(prer.isIndicadorTiempo());
/* 1500:1664 */           prer2.setTiempo(prer.getTiempo());
/* 1501:     */           
/* 1502:1666 */           this.pagoRolEmpleadoRubroDao.guardar(prer2);
/* 1503:     */         }
/* 1504:     */       }
/* 1505:     */     }
/* 1506:     */   }
/* 1507:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPagoRolImpl
 * JD-Core Version:    0.7.0.1
 */