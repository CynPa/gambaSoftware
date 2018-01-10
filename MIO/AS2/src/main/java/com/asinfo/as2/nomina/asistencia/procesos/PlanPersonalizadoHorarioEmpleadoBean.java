/*    1:     */ package com.asinfo.as2.nomina.asistencia.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    5:     */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*    6:     */ import com.asinfo.as2.entities.Departamento;
/*    7:     */ import com.asinfo.as2.entities.Empleado;
/*    8:     */ import com.asinfo.as2.entities.Organizacion;
/*    9:     */ import com.asinfo.as2.entities.Sucursal;
/*   10:     */ import com.asinfo.as2.entities.nomina.asistencia.DetallePlanPersonalizadoHorarioEmpleado;
/*   11:     */ import com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo;
/*   12:     */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*   13:     */ import com.asinfo.as2.entities.nomina.asistencia.PlanPersonalizadoHorarioEmpleado;
/*   14:     */ import com.asinfo.as2.entities.nomina.asistencia.Turno;
/*   15:     */ import com.asinfo.as2.entities.nomina.asistencia.TurnoDepartamento;
/*   16:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   17:     */ import com.asinfo.as2.enumeraciones.Mes;
/*   18:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   19:     */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistenciaConfiguracion;
/*   20:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*   21:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   22:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   23:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   24:     */ import com.asinfo.as2.util.AppUtil;
/*   25:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   26:     */ import com.asinfo.as2.utils.JsfUtil;
/*   27:     */ import java.io.PrintStream;
/*   28:     */ import java.lang.reflect.Method;
/*   29:     */ import java.util.ArrayList;
/*   30:     */ import java.util.Calendar;
/*   31:     */ import java.util.Date;
/*   32:     */ import java.util.HashMap;
/*   33:     */ import java.util.List;
/*   34:     */ import java.util.Map;
/*   35:     */ import javax.annotation.PostConstruct;
/*   36:     */ import javax.ejb.EJB;
/*   37:     */ import javax.faces.bean.ManagedBean;
/*   38:     */ import javax.faces.bean.ViewScoped;
/*   39:     */ import javax.faces.model.SelectItem;
/*   40:     */ import org.apache.log4j.Logger;
/*   41:     */ import org.primefaces.component.datatable.DataTable;
/*   42:     */ import org.primefaces.model.LazyDataModel;
/*   43:     */ import org.primefaces.model.SortOrder;
/*   44:     */ 
/*   45:     */ @ManagedBean
/*   46:     */ @ViewScoped
/*   47:     */ public class PlanPersonalizadoHorarioEmpleadoBean
/*   48:     */   extends PageControllerAS2
/*   49:     */ {
/*   50:     */   private static final long serialVersionUID = -6642782572366933215L;
/*   51:     */   @EJB
/*   52:     */   private ServicioGenerico<PlanPersonalizadoHorarioEmpleado> servicioPlanPersonalizadoHorarioEmpleado;
/*   53:     */   @EJB
/*   54:     */   private ServicioGenerico<DetallePlanPersonalizadoHorarioEmpleado> servicioDetallePlanPersonalizadoHorarioEmpleado;
/*   55:     */   @EJB
/*   56:     */   private ServicioGenerico<Turno> servicioTurno;
/*   57:     */   @EJB
/*   58:     */   private ServicioAsistenciaConfiguracion servicioAsistenciaConfiguracion;
/*   59:     */   @EJB
/*   60:     */   private ServicioAsistenciaProcesos servicioAsistenciaProcesos;
/*   61:     */   @EJB
/*   62:     */   private ServicioUsuario servicioUsuario;
/*   63:     */   @EJB
/*   64:     */   private ServicioDepartamento servicioDepartamento;
/*   65:     */   @EJB
/*   66:     */   private ServicioGenerico<HorarioEmpleado> servicioHorarioEmpleado;
/*   67:     */   @EJB
/*   68:     */   private ServicioEmpleado servicioEmpleado;
/*   69:     */   private PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleado;
/*   70:     */   private Departamento departamentoSeleccionado;
/*   71:     */   private DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoSeleccionado;
/*   72:     */   private LazyDataModel<PlanPersonalizadoHorarioEmpleado> listaPlanPersonalizadoHorarioEmpleado;
/*   73:     */   private List<Turno> listaTurno;
/*   74:     */   public static final String NUMERO_FILAS_POR_PAGINA = "5,10,15";
/*   75:     */   private DataTable dtPlanPersonalizadoHorarioEmpleado;
/*   76:     */   private DataTable dtDetallePlanPersonalizadoHorarioEmpleado1;
/*   77:     */   private DataTable dtDetallePlanPersonalizadoHorarioEmpleado2;
/*   78:     */   private DataTable dtDetallePlanPersonalizadoHorarioEmpleado3;
/*   79:     */   private DataTable dtDetallePlanPersonalizadoHorarioEmpleado4;
/*   80:     */   private DataTable dtDetallePlanPersonalizadoHorarioEmpleado5;
/*   81:     */   private int ultimoDiaSemana1;
/*   82:     */   private boolean dia1;
/*   83:     */   private boolean dia2;
/*   84:     */   private boolean dia3;
/*   85:     */   private boolean dia4;
/*   86:     */   private boolean dia5;
/*   87:     */   private boolean dia6;
/*   88:     */   private boolean dia7;
/*   89:     */   private boolean dia8;
/*   90:     */   private boolean dia9;
/*   91:     */   private boolean dia10;
/*   92:     */   private boolean dia11;
/*   93:     */   private boolean dia12;
/*   94:     */   private boolean dia13;
/*   95:     */   private boolean dia14;
/*   96:     */   private boolean dia15;
/*   97:     */   private boolean dia31;
/*   98:     */   private boolean diaDescanso1;
/*   99:     */   private boolean diaDescanso2;
/*  100:     */   private boolean diaDescanso3;
/*  101:     */   private boolean diaDescanso4;
/*  102:     */   private boolean diaDescanso5;
/*  103:     */   private boolean diaDescanso6;
/*  104:     */   private boolean diaDescanso7;
/*  105:     */   private boolean diaDescanso8;
/*  106:     */   private boolean diaDescanso9;
/*  107:     */   private boolean diaDescanso10;
/*  108:     */   private boolean diaDescanso11;
/*  109:     */   private boolean diaDescanso12;
/*  110:     */   private boolean diaDescanso13;
/*  111:     */   private boolean diaDescanso14;
/*  112:     */   private boolean diaDescanso15;
/*  113:     */   private boolean diaDescanso31;
/*  114:     */   private boolean diaComplementario1;
/*  115:     */   private boolean diaComplementario2;
/*  116:     */   private boolean diaComplementario3;
/*  117:     */   private boolean diaComplementario4;
/*  118:     */   private boolean diaComplementario5;
/*  119:     */   private boolean diaComplementario6;
/*  120:     */   private boolean diaComplementario7;
/*  121:     */   private boolean diaComplementario8;
/*  122:     */   private boolean diaComplementario9;
/*  123:     */   private boolean diaComplementario10;
/*  124:     */   private boolean diaComplementario11;
/*  125:     */   private boolean diaComplementario12;
/*  126:     */   private boolean diaComplementario13;
/*  127:     */   private boolean diaComplementario14;
/*  128:     */   private boolean diaComplementario15;
/*  129:     */   private boolean diaComplementario31;
/*  130:     */   private boolean renderPanelDias;
/*  131:     */   private boolean seleccionarTodos;
/*  132:     */   private GrupoTrabajo grupoTrabajoEmpleado;
/*  133: 173 */   private List<GrupoTrabajo> listaGrupoTrabajoEmpleado = new ArrayList();
/*  134:     */   private int numeroColumna;
/*  135:     */   private Turno turnoDialogo;
/*  136:     */   
/*  137:     */   @PostConstruct
/*  138:     */   public void init()
/*  139:     */   {
/*  140: 182 */     if (((this.departamentoSeleccionado == null) || (this.departamentoSeleccionado.getId() == 0)) && (getListaDepartamento().size() > 0)) {
/*  141: 183 */       this.departamentoSeleccionado = ((Departamento)getListaDepartamento().get(0));
/*  142:     */     }
/*  143: 185 */     this.listaPlanPersonalizadoHorarioEmpleado = new LazyDataModel()
/*  144:     */     {
/*  145:     */       private static final long serialVersionUID = -1752987002238164010L;
/*  146:     */       
/*  147:     */       public List<PlanPersonalizadoHorarioEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  148:     */       {
/*  149: 196 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  150: 197 */         filters.put("departamento.idDepartamento", "" + PlanPersonalizadoHorarioEmpleadoBean.this.getDepartamentoSeleccionado().getId());
/*  151:     */         
/*  152: 199 */         List<PlanPersonalizadoHorarioEmpleado> lista = new ArrayList();
/*  153: 200 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  154:     */         
/*  155: 202 */         lista = PlanPersonalizadoHorarioEmpleadoBean.this.servicioAsistenciaProcesos.obtenerListaPorPaginaPlanPersonalizadoHorarioEmpleado(startIndex, pageSize, sortField, ordenar, filters);
/*  156:     */         
/*  157: 204 */         PlanPersonalizadoHorarioEmpleadoBean.this.listaPlanPersonalizadoHorarioEmpleado
/*  158: 205 */           .setRowCount(PlanPersonalizadoHorarioEmpleadoBean.this.servicioAsistenciaProcesos.contarPorCriterioPlanPersonalizadoHorarioEmpleado(filters));
/*  159:     */         
/*  160: 207 */         return lista;
/*  161:     */       }
/*  162:     */     };
/*  163:     */   }
/*  164:     */   
/*  165:     */   public String crearPlanPersonalizadoHorarioEmpleado()
/*  166:     */   {
/*  167: 220 */     Calendar hoy = Calendar.getInstance();
/*  168: 221 */     this.planPersonalizadoHorarioEmpleado = new PlanPersonalizadoHorarioEmpleado();
/*  169: 222 */     this.planPersonalizadoHorarioEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  170: 223 */     this.planPersonalizadoHorarioEmpleado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  171: 224 */     this.planPersonalizadoHorarioEmpleado.setAnno(Integer.valueOf(hoy.get(1)));
/*  172: 225 */     this.planPersonalizadoHorarioEmpleado.setMes(Mes.values()[hoy.get(2)]);
/*  173: 227 */     if (getListaDepartamento().size() > 0) {
/*  174: 228 */       this.planPersonalizadoHorarioEmpleado.setDepartamento((Departamento)getListaDepartamento().get(0));
/*  175:     */     }
/*  176: 230 */     if (getListaHorarioEmpleado().size() > 0) {
/*  177: 231 */       this.planPersonalizadoHorarioEmpleado.setHorarioEmpleado((HorarioEmpleado)getListaHorarioEmpleado().get(0));
/*  178:     */     }
/*  179: 234 */     actualizarDetalles();
/*  180: 235 */     return "";
/*  181:     */   }
/*  182:     */   
/*  183:     */   public String limpiar()
/*  184:     */   {
/*  185: 244 */     this.dtDetallePlanPersonalizadoHorarioEmpleado1.reset();
/*  186: 245 */     this.dtDetallePlanPersonalizadoHorarioEmpleado2.reset();
/*  187: 246 */     crearPlanPersonalizadoHorarioEmpleado();
/*  188: 247 */     return "";
/*  189:     */   }
/*  190:     */   
/*  191:     */   public String editar()
/*  192:     */   {
/*  193: 256 */     this.dtDetallePlanPersonalizadoHorarioEmpleado1.reset();
/*  194: 257 */     this.dtDetallePlanPersonalizadoHorarioEmpleado2.reset();
/*  195: 258 */     if ((this.planPersonalizadoHorarioEmpleado != null) && (this.planPersonalizadoHorarioEmpleado.getId() != 0))
/*  196:     */     {
/*  197: 260 */       this.planPersonalizadoHorarioEmpleado = this.servicioAsistenciaProcesos.cargarDetallePlanPersonalizadoHorarioEmpleado(this.planPersonalizadoHorarioEmpleado);
/*  198: 261 */       actualizarListaTurno();
/*  199: 262 */       setEditado(true);
/*  200:     */     }
/*  201:     */     else
/*  202:     */     {
/*  203: 264 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  204:     */     }
/*  205: 266 */     return "";
/*  206:     */   }
/*  207:     */   
/*  208:     */   public String guardar()
/*  209:     */   {
/*  210:     */     try
/*  211:     */     {
/*  212: 276 */       this.servicioAsistenciaProcesos.guardarPlanPersonalizadoHorarioEmpleado(this.planPersonalizadoHorarioEmpleado);
/*  213: 277 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  214: 278 */       limpiar();
/*  215: 279 */       setEditado(false);
/*  216:     */     }
/*  217:     */     catch (AS2Exception e)
/*  218:     */     {
/*  219: 281 */       JsfUtil.addErrorMessage(e, "");
/*  220: 282 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  221:     */     }
/*  222:     */     catch (Exception e)
/*  223:     */     {
/*  224: 284 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  225: 285 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  226:     */     }
/*  227: 287 */     return "";
/*  228:     */   }
/*  229:     */   
/*  230:     */   public String eliminar()
/*  231:     */   {
/*  232: 296 */     if ((this.planPersonalizadoHorarioEmpleado != null) && (this.planPersonalizadoHorarioEmpleado.getId() != 0)) {
/*  233:     */       try
/*  234:     */       {
/*  235: 299 */         this.planPersonalizadoHorarioEmpleado = this.servicioAsistenciaProcesos.cargarDetallePlanPersonalizadoHorarioEmpleado(this.planPersonalizadoHorarioEmpleado);
/*  236: 300 */         for (DetallePlanPersonalizadoHorarioEmpleado det : this.planPersonalizadoHorarioEmpleado
/*  237: 301 */           .getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  238: 302 */           this.servicioDetallePlanPersonalizadoHorarioEmpleado.eliminar(det);
/*  239:     */         }
/*  240: 305 */         this.planPersonalizadoHorarioEmpleado.setListaDetallePlanPersonalizadoHorarioEmpleado(new ArrayList());
/*  241: 306 */         this.servicioPlanPersonalizadoHorarioEmpleado.eliminar(this.planPersonalizadoHorarioEmpleado);
/*  242: 307 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  243:     */       }
/*  244:     */       catch (Exception e)
/*  245:     */       {
/*  246: 309 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  247: 310 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  248:     */       }
/*  249:     */     } else {
/*  250: 313 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  251:     */     }
/*  252: 315 */     return "";
/*  253:     */   }
/*  254:     */   
/*  255:     */   public String copiar()
/*  256:     */   {
/*  257:     */     try
/*  258:     */     {
/*  259: 322 */       PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleadoAux = this.servicioAsistenciaProcesos.cargarDetallePlanPersonalizadoHorarioEmpleado(this.planPersonalizadoHorarioEmpleado);
/*  260: 323 */       this.servicioAsistenciaProcesos.copiarPlanPersonalizadoHorarioEmpleado(planPersonalizadoHorarioEmpleadoAux);
/*  261:     */     }
/*  262:     */     catch (AS2Exception e)
/*  263:     */     {
/*  264: 325 */       JsfUtil.addErrorMessage(e, "");
/*  265: 326 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  266:     */     }
/*  267:     */     catch (Exception e)
/*  268:     */     {
/*  269: 328 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  270: 329 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  271:     */     }
/*  272: 331 */     return "";
/*  273:     */   }
/*  274:     */   
/*  275:     */   public String cargarDatos()
/*  276:     */   {
/*  277: 340 */     return "";
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void actualizarDetalles()
/*  281:     */   {
/*  282: 344 */     this.dtDetallePlanPersonalizadoHorarioEmpleado1.reset();
/*  283: 345 */     this.dtDetallePlanPersonalizadoHorarioEmpleado2.reset();
/*  284: 346 */     this.planPersonalizadoHorarioEmpleado.setListaDetallePlanPersonalizadoHorarioEmpleado(new ArrayList());
/*  285: 347 */     if ((this.planPersonalizadoHorarioEmpleado.getDepartamento() != null) && (this.planPersonalizadoHorarioEmpleado.getHorarioEmpleado() != null))
/*  286:     */     {
/*  287: 348 */       Map<String, String> filtros = new HashMap();
/*  288: 349 */       filtros.put("idOrganizacion", AppUtil.getOrganizacion().getId() + "");
/*  289: 350 */       filtros.put("departamento.idDepartamento", "" + this.planPersonalizadoHorarioEmpleado.getDepartamento().getId());
/*  290: 351 */       filtros.put("horarioEmpleado.idHorarioEmpleado", "" + this.planPersonalizadoHorarioEmpleado.getHorarioEmpleado().getId());
/*  291: 352 */       filtros.put("activo", "true");
/*  292: 353 */       List<Empleado> listaEmpleado = this.servicioEmpleado.obtenerListaCombo("apellidos", true, filtros);
/*  293: 354 */       for (Empleado empleado : listaEmpleado)
/*  294:     */       {
/*  295: 355 */         DetallePlanPersonalizadoHorarioEmpleado detalle = new DetallePlanPersonalizadoHorarioEmpleado();
/*  296: 356 */         detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  297: 357 */         detalle.setIdSucursal(AppUtil.getSucursal().getId());
/*  298: 358 */         detalle.setEmpleado(empleado);
/*  299: 359 */         detalle.setPlanPersonalizadoHorarioEmpleado(this.planPersonalizadoHorarioEmpleado);
/*  300: 360 */         this.planPersonalizadoHorarioEmpleado.getListaDetallePlanPersonalizadoHorarioEmpleado().add(detalle);
/*  301:     */       }
/*  302:     */     }
/*  303: 364 */     Calendar fecha = Calendar.getInstance();
/*  304: 365 */     fecha.setFirstDayOfWeek(1);
/*  305: 366 */     fecha.set(this.planPersonalizadoHorarioEmpleado.getAnno().intValue(), this.planPersonalizadoHorarioEmpleado.getMes().ordinal(), 1);
/*  306: 367 */     int diaSemana = fecha.get(7);
/*  307: 368 */     this.ultimoDiaSemana1 = (8 - diaSemana);
/*  308:     */     
/*  309: 370 */     actualizarListaTurno();
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void copiarColumna(int semana, int diaSemana)
/*  313:     */   {
/*  314: 375 */     Integer diaMes = this.planPersonalizadoHorarioEmpleado.getDiaMes(semana, diaSemana);
/*  315: 376 */     Turno turnoCopiar = null;
/*  316: 377 */     if (getListaDetallePlanPersonalizadoHorarioEmpleado().size() > 0) {
/*  317: 378 */       turnoCopiar = ((DetallePlanPersonalizadoHorarioEmpleado)getListaDetallePlanPersonalizadoHorarioEmpleado().get(0)).getTurnoDiaMes(diaMes);
/*  318:     */     }
/*  319: 380 */     for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  320: 381 */       detallePlanPersonalizadoHorarioEmpleado.setTurnoDiaMes(diaMes, turnoCopiar);
/*  321:     */     }
/*  322:     */   }
/*  323:     */   
/*  324:     */   public void copiarColumna(int diaMes)
/*  325:     */   {
/*  326: 386 */     copiarColumna(diaMes, null, null);
/*  327:     */   }
/*  328:     */   
/*  329:     */   public void copiarColumna(int diaMes, Boolean indicadorDiaDescanso, Boolean indicadorDiaComplementario)
/*  330:     */   {
/*  331: 390 */     Turno turnoCopiar = null;
/*  332: 391 */     Boolean diaDescansoCopiar = null;
/*  333: 392 */     Boolean diaComplementarioCopiar = null;
/*  334: 393 */     if ((getListaDetallePlanPersonalizadoHorarioEmpleado().size() > 0) && (indicadorDiaDescanso == null) && (indicadorDiaComplementario == null))
/*  335:     */     {
/*  336: 394 */       turnoCopiar = ((DetallePlanPersonalizadoHorarioEmpleado)getListaDetallePlanPersonalizadoHorarioEmpleado().get(0)).getTurnoDiaMes(Integer.valueOf(diaMes));
/*  337: 395 */       diaDescansoCopiar = ((DetallePlanPersonalizadoHorarioEmpleado)getListaDetallePlanPersonalizadoHorarioEmpleado().get(0)).getIndicador(Integer.valueOf(diaMes), "getIndicadorDiaDescanso");
/*  338: 396 */       diaComplementarioCopiar = ((DetallePlanPersonalizadoHorarioEmpleado)getListaDetallePlanPersonalizadoHorarioEmpleado().get(0)).getIndicador(Integer.valueOf(diaMes), "getIndicadorDiaComplementario");
/*  339:     */     }
/*  340:     */     else
/*  341:     */     {
/*  342: 398 */       diaDescansoCopiar = indicadorDiaDescanso;
/*  343: 399 */       diaComplementarioCopiar = indicadorDiaComplementario;
/*  344:     */     }
/*  345: 402 */     if (getGrupoTrabajoEmpleado() != null) {
/*  346: 403 */       for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  347: 404 */         if (detallePlanPersonalizadoHorarioEmpleado.getEmpleado().getGrupoTrabajo() == getGrupoTrabajoEmpleado())
/*  348:     */         {
/*  349: 405 */           detallePlanPersonalizadoHorarioEmpleado.setTurnoDiaMes(Integer.valueOf(diaMes), getTurnoDialogo());
/*  350: 406 */           detallePlanPersonalizadoHorarioEmpleado.setIndicador(Integer.valueOf(diaMes), diaDescansoCopiar, "setIndicadorDiaDescanso");
/*  351: 407 */           detallePlanPersonalizadoHorarioEmpleado.setIndicador(Integer.valueOf(diaMes), diaComplementarioCopiar, "setIndicadorDiaComplementario");
/*  352:     */         }
/*  353:     */       }
/*  354:     */     } else {
/*  355: 411 */       for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : getListaDetallePlanPersonalizadoHorarioEmpleado())
/*  356:     */       {
/*  357: 412 */         detallePlanPersonalizadoHorarioEmpleado.setTurnoDiaMes(Integer.valueOf(diaMes), turnoCopiar);
/*  358: 413 */         detallePlanPersonalizadoHorarioEmpleado.setIndicador(Integer.valueOf(diaMes), diaDescansoCopiar, "setIndicadorDiaDescanso");
/*  359: 414 */         detallePlanPersonalizadoHorarioEmpleado.setIndicador(Integer.valueOf(diaMes), diaComplementarioCopiar, "setIndicadorDiaComplementario");
/*  360:     */       }
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public String copiarTurno()
/*  365:     */   {
/*  366: 421 */     if (this.dia1) {
/*  367: 422 */       if (getNumeroColumna() <= 15) {
/*  368: 423 */         copiarColumna(1, Boolean.valueOf(this.diaDescanso1), Boolean.valueOf(this.diaComplementario1));
/*  369:     */       } else {
/*  370: 425 */         copiarColumna(16, Boolean.valueOf(this.diaDescanso1), Boolean.valueOf(this.diaComplementario1));
/*  371:     */       }
/*  372:     */     }
/*  373: 428 */     if (this.dia2) {
/*  374: 429 */       if (getNumeroColumna() <= 15) {
/*  375: 430 */         copiarColumna(2, Boolean.valueOf(this.diaDescanso2), Boolean.valueOf(this.diaComplementario2));
/*  376:     */       } else {
/*  377: 432 */         copiarColumna(17, Boolean.valueOf(this.diaDescanso2), Boolean.valueOf(this.diaComplementario2));
/*  378:     */       }
/*  379:     */     }
/*  380: 435 */     if (this.dia3) {
/*  381: 436 */       if (getNumeroColumna() <= 15) {
/*  382: 437 */         copiarColumna(3, Boolean.valueOf(this.diaDescanso3), Boolean.valueOf(this.diaComplementario3));
/*  383:     */       } else {
/*  384: 439 */         copiarColumna(18, Boolean.valueOf(this.diaDescanso3), Boolean.valueOf(this.diaComplementario3));
/*  385:     */       }
/*  386:     */     }
/*  387: 442 */     if (this.dia4) {
/*  388: 443 */       if (getNumeroColumna() <= 15) {
/*  389: 444 */         copiarColumna(4, Boolean.valueOf(this.diaDescanso4), Boolean.valueOf(this.diaComplementario4));
/*  390:     */       } else {
/*  391: 446 */         copiarColumna(19, Boolean.valueOf(this.diaDescanso4), Boolean.valueOf(this.diaComplementario4));
/*  392:     */       }
/*  393:     */     }
/*  394: 449 */     if (this.dia5) {
/*  395: 450 */       if (getNumeroColumna() <= 15) {
/*  396: 451 */         copiarColumna(5, Boolean.valueOf(this.diaDescanso5), Boolean.valueOf(this.diaComplementario5));
/*  397:     */       } else {
/*  398: 453 */         copiarColumna(20, Boolean.valueOf(this.diaDescanso5), Boolean.valueOf(this.diaComplementario5));
/*  399:     */       }
/*  400:     */     }
/*  401: 456 */     if (this.dia6) {
/*  402: 457 */       if (getNumeroColumna() <= 15) {
/*  403: 458 */         copiarColumna(6, Boolean.valueOf(this.diaDescanso6), Boolean.valueOf(this.diaComplementario6));
/*  404:     */       } else {
/*  405: 460 */         copiarColumna(21, Boolean.valueOf(this.diaDescanso6), Boolean.valueOf(this.diaComplementario6));
/*  406:     */       }
/*  407:     */     }
/*  408: 463 */     if (this.dia7) {
/*  409: 464 */       if (getNumeroColumna() <= 15) {
/*  410: 465 */         copiarColumna(7, Boolean.valueOf(this.diaDescanso7), Boolean.valueOf(this.diaComplementario7));
/*  411:     */       } else {
/*  412: 467 */         copiarColumna(22, Boolean.valueOf(this.diaDescanso7), Boolean.valueOf(this.diaComplementario7));
/*  413:     */       }
/*  414:     */     }
/*  415: 470 */     if (this.dia8) {
/*  416: 471 */       if (getNumeroColumna() <= 15) {
/*  417: 472 */         copiarColumna(8, Boolean.valueOf(this.diaDescanso8), Boolean.valueOf(this.diaComplementario8));
/*  418:     */       } else {
/*  419: 474 */         copiarColumna(23, Boolean.valueOf(this.diaDescanso8), Boolean.valueOf(this.diaComplementario8));
/*  420:     */       }
/*  421:     */     }
/*  422: 477 */     if (this.dia9) {
/*  423: 478 */       if (getNumeroColumna() <= 15) {
/*  424: 479 */         copiarColumna(9, Boolean.valueOf(this.diaDescanso9), Boolean.valueOf(this.diaComplementario9));
/*  425:     */       } else {
/*  426: 481 */         copiarColumna(24, Boolean.valueOf(this.diaDescanso9), Boolean.valueOf(this.diaComplementario9));
/*  427:     */       }
/*  428:     */     }
/*  429: 484 */     if (this.dia10) {
/*  430: 485 */       if (getNumeroColumna() <= 15) {
/*  431: 486 */         copiarColumna(10, Boolean.valueOf(this.diaDescanso10), Boolean.valueOf(this.diaComplementario10));
/*  432:     */       } else {
/*  433: 488 */         copiarColumna(25, Boolean.valueOf(this.diaDescanso10), Boolean.valueOf(this.diaComplementario10));
/*  434:     */       }
/*  435:     */     }
/*  436: 491 */     if (this.dia11) {
/*  437: 492 */       if (getNumeroColumna() <= 15) {
/*  438: 493 */         copiarColumna(11, Boolean.valueOf(this.diaDescanso11), Boolean.valueOf(this.diaComplementario11));
/*  439: 495 */       } else if (getDiasMes() >= 26) {
/*  440: 496 */         copiarColumna(26, Boolean.valueOf(this.diaDescanso11), Boolean.valueOf(this.diaComplementario11));
/*  441:     */       }
/*  442:     */     }
/*  443: 499 */     if (this.dia12) {
/*  444: 500 */       if (getNumeroColumna() <= 15) {
/*  445: 501 */         copiarColumna(12, Boolean.valueOf(this.diaDescanso12), Boolean.valueOf(this.diaComplementario12));
/*  446: 503 */       } else if (getDiasMes() >= 27) {
/*  447: 504 */         copiarColumna(27, Boolean.valueOf(this.diaDescanso12), Boolean.valueOf(this.diaComplementario12));
/*  448:     */       }
/*  449:     */     }
/*  450: 507 */     if (this.dia13) {
/*  451: 508 */       if (getNumeroColumna() <= 15) {
/*  452: 509 */         copiarColumna(13, Boolean.valueOf(this.diaDescanso13), Boolean.valueOf(this.diaComplementario13));
/*  453: 511 */       } else if (getDiasMes() >= 28) {
/*  454: 512 */         copiarColumna(28, Boolean.valueOf(this.diaDescanso13), Boolean.valueOf(this.diaComplementario13));
/*  455:     */       }
/*  456:     */     }
/*  457: 515 */     if (this.dia14) {
/*  458: 516 */       if (getNumeroColumna() <= 15) {
/*  459: 517 */         copiarColumna(14, Boolean.valueOf(this.diaDescanso14), Boolean.valueOf(this.diaComplementario14));
/*  460: 519 */       } else if (getDiasMes() >= 29) {
/*  461: 520 */         copiarColumna(29, Boolean.valueOf(this.diaDescanso14), Boolean.valueOf(this.diaComplementario14));
/*  462:     */       }
/*  463:     */     }
/*  464: 523 */     if (this.dia15) {
/*  465: 524 */       if (getNumeroColumna() <= 15) {
/*  466: 525 */         copiarColumna(15, Boolean.valueOf(this.diaDescanso15), Boolean.valueOf(this.diaComplementario15));
/*  467: 527 */       } else if (getDiasMes() >= 30) {
/*  468: 528 */         copiarColumna(30, Boolean.valueOf(this.diaDescanso15), Boolean.valueOf(this.diaComplementario15));
/*  469:     */       }
/*  470:     */     }
/*  471: 531 */     if ((this.dia31) && 
/*  472: 532 */       (getNumeroColumna() >= 16) && (getDiasMes() >= 31)) {
/*  473: 533 */       copiarColumna(31, Boolean.valueOf(this.diaDescanso31), Boolean.valueOf(this.diaComplementario31));
/*  474:     */     }
/*  475: 536 */     setSeleccionarTodos(false);
/*  476: 537 */     cambiarTodos();
/*  477: 538 */     setearIndicadoresDescansoYComplementario();
/*  478: 539 */     setGrupoTrabajoEmpleado(null);
/*  479: 540 */     setTurnoDialogo(null);
/*  480: 541 */     return null;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public void limpiarIndicadores()
/*  484:     */   {
/*  485: 545 */     setSeleccionarTodos(false);
/*  486: 546 */     cambiarTodos();
/*  487: 547 */     setearIndicadoresDescansoYComplementario();
/*  488: 548 */     setGrupoTrabajoEmpleado(null);
/*  489: 549 */     setTurnoDialogo(null);
/*  490:     */   }
/*  491:     */   
/*  492:     */   private void setearIndicadoresDescansoYComplementario()
/*  493:     */   {
/*  494: 553 */     this.diaDescanso1 = false;
/*  495: 554 */     this.diaDescanso2 = false;
/*  496: 555 */     this.diaDescanso3 = false;
/*  497: 556 */     this.diaDescanso4 = false;
/*  498: 557 */     this.diaDescanso5 = false;
/*  499: 558 */     this.diaDescanso6 = false;
/*  500: 559 */     this.diaDescanso7 = false;
/*  501: 560 */     this.diaDescanso8 = false;
/*  502: 561 */     this.diaDescanso9 = false;
/*  503: 562 */     this.diaDescanso10 = false;
/*  504: 563 */     this.diaDescanso11 = false;
/*  505: 564 */     this.diaDescanso12 = false;
/*  506: 565 */     this.diaDescanso13 = false;
/*  507: 566 */     this.diaDescanso14 = false;
/*  508: 567 */     this.diaDescanso15 = false;
/*  509: 568 */     this.diaDescanso31 = false;
/*  510:     */     
/*  511: 570 */     this.diaComplementario1 = false;
/*  512: 571 */     this.diaComplementario2 = false;
/*  513: 572 */     this.diaComplementario3 = false;
/*  514: 573 */     this.diaComplementario4 = false;
/*  515: 574 */     this.diaComplementario5 = false;
/*  516: 575 */     this.diaComplementario6 = false;
/*  517: 576 */     this.diaComplementario7 = false;
/*  518: 577 */     this.diaComplementario8 = false;
/*  519: 578 */     this.diaComplementario9 = false;
/*  520: 579 */     this.diaComplementario10 = false;
/*  521: 580 */     this.diaComplementario11 = false;
/*  522: 581 */     this.diaComplementario12 = false;
/*  523: 582 */     this.diaComplementario13 = false;
/*  524: 583 */     this.diaComplementario14 = false;
/*  525: 584 */     this.diaComplementario15 = false;
/*  526: 585 */     this.diaComplementario31 = false;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public String eliminarTurno()
/*  530:     */   {
/*  531:     */     int i;
/*  532: 589 */     if (getNumeroColumna() == 115) {
/*  533: 590 */       for (int i = 1; i <= 15; i++) {
/*  534: 591 */         for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  535: 592 */           detallePlanPersonalizadoHorarioEmpleado.setTurnoDiaMes(Integer.valueOf(i), null);
/*  536:     */         }
/*  537:     */       }
/*  538: 596 */     } else if (getNumeroColumna() == 215) {
/*  539: 597 */       for (i = 16; i <= 31; i++) {
/*  540: 598 */         if (getDiasMes() >= i) {
/*  541: 599 */           for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  542: 600 */             detallePlanPersonalizadoHorarioEmpleado.setTurnoDiaMes(Integer.valueOf(i), null);
/*  543:     */           }
/*  544:     */         }
/*  545:     */       }
/*  546:     */     } else {
/*  547: 606 */       for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  548: 607 */         detallePlanPersonalizadoHorarioEmpleado.setTurnoDiaMes(Integer.valueOf(getNumeroColumna()), null);
/*  549:     */       }
/*  550:     */     }
/*  551: 611 */     return null;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public String setearBoolean()
/*  555:     */   {
/*  556: 615 */     if ((getNumeroColumna() == 1) || (getNumeroColumna() == 16)) {
/*  557: 616 */       setDia1(true);
/*  558:     */     }
/*  559: 618 */     if ((getNumeroColumna() == 2) || (getNumeroColumna() == 17)) {
/*  560: 619 */       setDia2(true);
/*  561:     */     }
/*  562: 621 */     if ((getNumeroColumna() == 3) || (getNumeroColumna() == 18)) {
/*  563: 622 */       setDia3(true);
/*  564:     */     }
/*  565: 624 */     if ((getNumeroColumna() == 4) || (getNumeroColumna() == 19)) {
/*  566: 625 */       setDia4(true);
/*  567:     */     }
/*  568: 627 */     if ((getNumeroColumna() == 5) || (getNumeroColumna() == 20)) {
/*  569: 628 */       setDia5(true);
/*  570:     */     }
/*  571: 630 */     if ((getNumeroColumna() == 6) || (getNumeroColumna() == 21)) {
/*  572: 631 */       setDia6(true);
/*  573:     */     }
/*  574: 633 */     if ((getNumeroColumna() == 7) || (getNumeroColumna() == 22)) {
/*  575: 634 */       setDia7(true);
/*  576:     */     }
/*  577: 636 */     if ((getNumeroColumna() == 8) || (getNumeroColumna() == 23)) {
/*  578: 637 */       setDia8(true);
/*  579:     */     }
/*  580: 639 */     if ((getNumeroColumna() == 9) || (getNumeroColumna() == 24)) {
/*  581: 640 */       setDia9(true);
/*  582:     */     }
/*  583: 642 */     if ((getNumeroColumna() == 10) || (getNumeroColumna() == 25)) {
/*  584: 643 */       setDia10(true);
/*  585:     */     }
/*  586: 645 */     if ((getNumeroColumna() == 11) || (getNumeroColumna() == 26)) {
/*  587: 646 */       setDia11(true);
/*  588:     */     }
/*  589: 648 */     if ((getNumeroColumna() == 12) || (getNumeroColumna() == 27)) {
/*  590: 649 */       setDia12(true);
/*  591:     */     }
/*  592: 651 */     if ((getNumeroColumna() == 13) || (getNumeroColumna() == 28)) {
/*  593: 652 */       setDia13(true);
/*  594:     */     }
/*  595: 654 */     if ((getNumeroColumna() == 14) || (getNumeroColumna() == 29)) {
/*  596: 655 */       setDia14(true);
/*  597:     */     }
/*  598: 657 */     if ((getNumeroColumna() == 15) || (getNumeroColumna() == 30)) {
/*  599: 658 */       setDia15(true);
/*  600:     */     }
/*  601: 660 */     if (getNumeroColumna() == 31) {
/*  602: 661 */       setDia31(true);
/*  603:     */     }
/*  604: 663 */     return null;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void cargarGrupos()
/*  608:     */   {
/*  609: 667 */     setearBoolean();
/*  610: 668 */     getListaGrupoTrabajoEmpleado().clear();
/*  611: 669 */     HashMap<String, GrupoTrabajo> hmGrupoTrabajo = new HashMap();
/*  612: 671 */     for (DetallePlanPersonalizadoHorarioEmpleado dpphe : getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  613: 672 */       if ((dpphe.getEmpleado().getGrupoTrabajo() != null) && 
/*  614: 673 */         (!hmGrupoTrabajo.containsKey(dpphe.getEmpleado().getGrupoTrabajo().getCodigo())))
/*  615:     */       {
/*  616: 674 */         getListaGrupoTrabajoEmpleado().add(dpphe.getEmpleado().getGrupoTrabajo());
/*  617: 675 */         hmGrupoTrabajo.put(dpphe.getEmpleado().getGrupoTrabajo().getCodigo(), dpphe.getEmpleado().getGrupoTrabajo());
/*  618:     */       }
/*  619:     */     }
/*  620:     */   }
/*  621:     */   
/*  622:     */   public String cambiarTodos()
/*  623:     */   {
/*  624: 685 */     this.dia1 = isSeleccionarTodos();
/*  625: 686 */     this.dia2 = isSeleccionarTodos();
/*  626: 687 */     this.dia3 = isSeleccionarTodos();
/*  627: 688 */     this.dia4 = isSeleccionarTodos();
/*  628: 689 */     this.dia5 = isSeleccionarTodos();
/*  629: 690 */     this.dia6 = isSeleccionarTodos();
/*  630: 691 */     this.dia7 = isSeleccionarTodos();
/*  631: 692 */     this.dia8 = isSeleccionarTodos();
/*  632: 693 */     this.dia9 = isSeleccionarTodos();
/*  633: 694 */     this.dia10 = isSeleccionarTodos();
/*  634: 695 */     this.dia11 = isSeleccionarTodos();
/*  635: 696 */     this.dia12 = isSeleccionarTodos();
/*  636: 697 */     this.dia13 = isSeleccionarTodos();
/*  637: 698 */     this.dia14 = isSeleccionarTodos();
/*  638: 699 */     this.dia15 = isSeleccionarTodos();
/*  639: 700 */     this.dia31 = isSeleccionarTodos();
/*  640: 701 */     return null;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public List<Turno> getListaTurno()
/*  644:     */   {
/*  645: 709 */     if (this.listaTurno == null) {
/*  646: 710 */       actualizarListaTurno();
/*  647:     */     }
/*  648: 712 */     return this.listaTurno;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public void actualizarListaTurno()
/*  652:     */   {
/*  653: 716 */     this.listaTurno = new ArrayList();
/*  654: 717 */     if (this.planPersonalizadoHorarioEmpleado.getDepartamento() != null)
/*  655:     */     {
/*  656: 718 */       Departamento departamento = this.servicioDepartamento.cargarDetalle(this.planPersonalizadoHorarioEmpleado.getDepartamento().getId());
/*  657: 719 */       for (TurnoDepartamento turnoDepartamento : departamento.getListaTurnoDepartamento()) {
/*  658: 720 */         this.listaTurno.add(turnoDepartamento.getTurno());
/*  659:     */       }
/*  660:     */     }
/*  661:     */   }
/*  662:     */   
/*  663:     */   public List<DetallePlanPersonalizadoHorarioEmpleado> getListaDetallePlanPersonalizadoHorarioEmpleado()
/*  664:     */   {
/*  665: 726 */     List<DetallePlanPersonalizadoHorarioEmpleado> lista = new ArrayList();
/*  666: 727 */     for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : this.planPersonalizadoHorarioEmpleado
/*  667: 728 */       .getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  668: 729 */       if (!detallePlanPersonalizadoHorarioEmpleado.isEliminado()) {
/*  669: 730 */         lista.add(detallePlanPersonalizadoHorarioEmpleado);
/*  670:     */       }
/*  671:     */     }
/*  672: 733 */     return lista;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public List<HorarioEmpleado> getListaHorarioEmpleado()
/*  676:     */   {
/*  677: 737 */     if (this.planPersonalizadoHorarioEmpleado.getDepartamento() == null) {
/*  678: 738 */       return new ArrayList();
/*  679:     */     }
/*  680: 740 */     return this.servicioEmpleado.getListaHorarios(this.planPersonalizadoHorarioEmpleado.getDepartamento());
/*  681:     */   }
/*  682:     */   
/*  683:     */   public List<SelectItem> getListaMes()
/*  684:     */   {
/*  685: 744 */     List<SelectItem> lista = new ArrayList();
/*  686: 745 */     for (Mes item : Mes.values()) {
/*  687: 746 */       lista.add(new SelectItem(item, item.getNombre()));
/*  688:     */     }
/*  689: 748 */     return lista;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public List<Departamento> getListaDepartamento()
/*  693:     */   {
/*  694: 752 */     EntidadUsuario usuario = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  695: 753 */     Map<String, String> filtros = new HashMap();
/*  696: 754 */     Map<String, String> filtros2 = new HashMap();
/*  697: 755 */     List<Departamento> lista = new ArrayList();
/*  698:     */     
/*  699: 757 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  700: 758 */     filtros2.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  701: 760 */     if ((usuario.getEmpleado() != null) && (!usuario.isIndicadorAdministrador()))
/*  702:     */     {
/*  703: 762 */       filtros.put("supervisor.idEmpleado", "" + usuario.getEmpleado().getId());
/*  704: 763 */       filtros2.put("supervisor2.idEmpleado", "" + usuario.getEmpleado().getId());
/*  705:     */       
/*  706: 765 */       lista.addAll(this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros2));
/*  707:     */     }
/*  708: 768 */     else if (!usuario.isIndicadorAdministrador())
/*  709:     */     {
/*  710: 769 */       return lista;
/*  711:     */     }
/*  712: 772 */     lista.addAll(this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros));
/*  713:     */     
/*  714: 774 */     return lista;
/*  715:     */   }
/*  716:     */   
/*  717:     */   public GrupoTrabajo getGrupoTrabajoEmpleado()
/*  718:     */   {
/*  719: 778 */     return this.grupoTrabajoEmpleado;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public void setGrupoTrabajoEmpleado(GrupoTrabajo grupoTrabajoEmpleado)
/*  723:     */   {
/*  724: 782 */     this.grupoTrabajoEmpleado = grupoTrabajoEmpleado;
/*  725:     */   }
/*  726:     */   
/*  727:     */   public PlanPersonalizadoHorarioEmpleado getPlanPersonalizadoHorarioEmpleado()
/*  728:     */   {
/*  729: 786 */     return this.planPersonalizadoHorarioEmpleado;
/*  730:     */   }
/*  731:     */   
/*  732:     */   public void setPlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleado)
/*  733:     */   {
/*  734: 790 */     this.planPersonalizadoHorarioEmpleado = planPersonalizadoHorarioEmpleado;
/*  735:     */   }
/*  736:     */   
/*  737:     */   public LazyDataModel<PlanPersonalizadoHorarioEmpleado> getListaPlanPersonalizadoHorarioEmpleado()
/*  738:     */   {
/*  739: 794 */     return this.listaPlanPersonalizadoHorarioEmpleado;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public void setListaPlanPersonalizadoHorarioEmpleado(LazyDataModel<PlanPersonalizadoHorarioEmpleado> listaPlanPersonalizadoHorarioEmpleado)
/*  743:     */   {
/*  744: 798 */     this.listaPlanPersonalizadoHorarioEmpleado = listaPlanPersonalizadoHorarioEmpleado;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public DataTable getDtPlanPersonalizadoHorarioEmpleado()
/*  748:     */   {
/*  749: 802 */     return this.dtPlanPersonalizadoHorarioEmpleado;
/*  750:     */   }
/*  751:     */   
/*  752:     */   public void setDtPlanPersonalizadoHorarioEmpleado(DataTable dtPlanPersonalizadoHorarioEmpleado)
/*  753:     */   {
/*  754: 806 */     this.dtPlanPersonalizadoHorarioEmpleado = dtPlanPersonalizadoHorarioEmpleado;
/*  755:     */   }
/*  756:     */   
/*  757:     */   public DataTable getDtDetallePlanPersonalizadoHorarioEmpleado1()
/*  758:     */   {
/*  759: 810 */     return this.dtDetallePlanPersonalizadoHorarioEmpleado1;
/*  760:     */   }
/*  761:     */   
/*  762:     */   public void setDtDetallePlanPersonalizadoHorarioEmpleado1(DataTable dtDetallePlanPersonalizadoHorarioEmpleado1)
/*  763:     */   {
/*  764: 814 */     this.dtDetallePlanPersonalizadoHorarioEmpleado1 = dtDetallePlanPersonalizadoHorarioEmpleado1;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public DataTable getDtDetallePlanPersonalizadoHorarioEmpleado2()
/*  768:     */   {
/*  769: 818 */     return this.dtDetallePlanPersonalizadoHorarioEmpleado2;
/*  770:     */   }
/*  771:     */   
/*  772:     */   public void setDtDetallePlanPersonalizadoHorarioEmpleado2(DataTable dtDetallePlanPersonalizadoHorarioEmpleado2)
/*  773:     */   {
/*  774: 822 */     this.dtDetallePlanPersonalizadoHorarioEmpleado2 = dtDetallePlanPersonalizadoHorarioEmpleado2;
/*  775:     */   }
/*  776:     */   
/*  777:     */   public DataTable getDtDetallePlanPersonalizadoHorarioEmpleado3()
/*  778:     */   {
/*  779: 826 */     return this.dtDetallePlanPersonalizadoHorarioEmpleado3;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public void setDtDetallePlanPersonalizadoHorarioEmpleado3(DataTable dtDetallePlanPersonalizadoHorarioEmpleado3)
/*  783:     */   {
/*  784: 830 */     this.dtDetallePlanPersonalizadoHorarioEmpleado3 = dtDetallePlanPersonalizadoHorarioEmpleado3;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public DataTable getDtDetallePlanPersonalizadoHorarioEmpleado4()
/*  788:     */   {
/*  789: 834 */     return this.dtDetallePlanPersonalizadoHorarioEmpleado4;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void setDtDetallePlanPersonalizadoHorarioEmpleado4(DataTable dtDetallePlanPersonalizadoHorarioEmpleado4)
/*  793:     */   {
/*  794: 838 */     this.dtDetallePlanPersonalizadoHorarioEmpleado4 = dtDetallePlanPersonalizadoHorarioEmpleado4;
/*  795:     */   }
/*  796:     */   
/*  797:     */   public DataTable getDtDetallePlanPersonalizadoHorarioEmpleado5()
/*  798:     */   {
/*  799: 842 */     return this.dtDetallePlanPersonalizadoHorarioEmpleado5;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void setDtDetallePlanPersonalizadoHorarioEmpleado5(DataTable dtDetallePlanPersonalizadoHorarioEmpleado5)
/*  803:     */   {
/*  804: 846 */     this.dtDetallePlanPersonalizadoHorarioEmpleado5 = dtDetallePlanPersonalizadoHorarioEmpleado5;
/*  805:     */   }
/*  806:     */   
/*  807:     */   public int getUltimoDiaSemana1()
/*  808:     */   {
/*  809: 850 */     return this.ultimoDiaSemana1;
/*  810:     */   }
/*  811:     */   
/*  812:     */   public void setUltimoDiaSemana1(int ultimoDiaSemana1)
/*  813:     */   {
/*  814: 854 */     this.ultimoDiaSemana1 = ultimoDiaSemana1;
/*  815:     */   }
/*  816:     */   
/*  817:     */   public Departamento getDepartamentoSeleccionado()
/*  818:     */   {
/*  819: 858 */     if (this.departamentoSeleccionado == null) {
/*  820: 859 */       this.departamentoSeleccionado = new Departamento();
/*  821:     */     }
/*  822: 861 */     return this.departamentoSeleccionado;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void setDepartamentoSeleccionado(Departamento departamentoSeleccionado)
/*  826:     */   {
/*  827: 865 */     this.departamentoSeleccionado = departamentoSeleccionado;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public int getDiasMes()
/*  831:     */   {
/*  832: 870 */     if ((this.planPersonalizadoHorarioEmpleado != null) && (this.planPersonalizadoHorarioEmpleado.getMes() != null))
/*  833:     */     {
/*  834: 871 */       Date finMes = FuncionesUtiles.getFechaFinMes(this.planPersonalizadoHorarioEmpleado.getAnno().intValue(), this.planPersonalizadoHorarioEmpleado
/*  835: 872 */         .getMes().ordinal() + 1);
/*  836: 873 */       Calendar finMesC = Calendar.getInstance();
/*  837: 874 */       finMesC.clear();
/*  838: 875 */       finMesC.setTime(finMes);
/*  839: 876 */       return finMesC.get(5);
/*  840:     */     }
/*  841: 878 */     return 0;
/*  842:     */   }
/*  843:     */   
/*  844:     */   public String getDiaSemana(int diaMes)
/*  845:     */   {
/*  846: 884 */     if ((this.planPersonalizadoHorarioEmpleado != null) && (this.planPersonalizadoHorarioEmpleado.getMes() != null))
/*  847:     */     {
/*  848: 885 */       Date fecha = FuncionesUtiles.getFecha(diaMes, this.planPersonalizadoHorarioEmpleado.getMes().ordinal() + 1, this.planPersonalizadoHorarioEmpleado
/*  849: 886 */         .getAnno().intValue());
/*  850: 887 */       Calendar fechaC = Calendar.getInstance();
/*  851: 888 */       fechaC.clear();
/*  852: 889 */       fechaC.setTime(fecha);
/*  853: 890 */       int diaSemana = fechaC.get(7);
/*  854: 891 */       String diaLetra = "";
/*  855: 892 */       switch (diaSemana)
/*  856:     */       {
/*  857:     */       case 1: 
/*  858: 894 */         diaLetra = "Dom";
/*  859: 895 */         break;
/*  860:     */       case 2: 
/*  861: 898 */         diaLetra = "Lun";
/*  862: 899 */         break;
/*  863:     */       case 3: 
/*  864: 902 */         diaLetra = "Mar";
/*  865: 903 */         break;
/*  866:     */       case 4: 
/*  867: 906 */         diaLetra = "Mie";
/*  868: 907 */         break;
/*  869:     */       case 5: 
/*  870: 910 */         diaLetra = "Jue";
/*  871: 911 */         break;
/*  872:     */       case 6: 
/*  873: 914 */         diaLetra = "Vie";
/*  874: 915 */         break;
/*  875:     */       case 7: 
/*  876: 918 */         diaLetra = "Sab";
/*  877: 919 */         break;
/*  878:     */       }
/*  879: 924 */       return diaLetra;
/*  880:     */     }
/*  881: 926 */     return "";
/*  882:     */   }
/*  883:     */   
/*  884:     */   public DetallePlanPersonalizadoHorarioEmpleado getDetallePlanPersonalizadoSeleccionado()
/*  885:     */   {
/*  886: 931 */     return this.detallePlanPersonalizadoSeleccionado;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void setDetallePlanPersonalizadoSeleccionado(DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoSeleccionado)
/*  890:     */   {
/*  891: 935 */     this.detallePlanPersonalizadoSeleccionado = detallePlanPersonalizadoSeleccionado;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public String getNumeroFilasPorPagina()
/*  895:     */   {
/*  896: 939 */     return "5,10,15";
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void actualizarDiaDescanso(int dia, DetallePlanPersonalizadoHorarioEmpleado detalle, boolean isDiaDescanso)
/*  900:     */   {
/*  901:     */     try
/*  902:     */     {
/*  903: 945 */       String getMethod = isDiaDescanso ? "getIndicadorDiaDescanso" : "getIndicadorDiaComplementario";
/*  904: 946 */       String setMethod = isDiaDescanso ? "setIndicadorDiaDescanso" : "setIndicadorDiaComplementario";
/*  905:     */       
/*  906: 948 */       Object[] parametrosGet = null;
/*  907: 949 */       Method metodoGet = detalle.getClass().getMethod(getMethod + dia, new Class[0]);
/*  908: 950 */       Boolean indicador = (Boolean)metodoGet.invoke(detalle, parametrosGet);
/*  909:     */       
/*  910: 952 */       Object[] parametrosSet = new Object[1];
/*  911: 953 */       parametrosSet[0] = Boolean.valueOf(!indicador.booleanValue() ? 1 : false);
/*  912: 954 */       Method metodoSet = detalle.getClass().getMethod(setMethod + dia, new Class[] { Boolean.class });
/*  913: 955 */       metodoSet.invoke(detalle, parametrosSet);
/*  914:     */     }
/*  915:     */     catch (Exception e)
/*  916:     */     {
/*  917: 957 */       System.out.println(e.getMessage());
/*  918: 958 */       e.printStackTrace();
/*  919:     */     }
/*  920:     */   }
/*  921:     */   
/*  922:     */   public List<GrupoTrabajo> getListaGrupoTrabajoEmpleado()
/*  923:     */   {
/*  924: 963 */     return this.listaGrupoTrabajoEmpleado;
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void setListaGrupoTrabajoEmpleado(List<GrupoTrabajo> listaGrupoTrabajoEmpleado)
/*  928:     */   {
/*  929: 967 */     this.listaGrupoTrabajoEmpleado = listaGrupoTrabajoEmpleado;
/*  930:     */   }
/*  931:     */   
/*  932:     */   public int getNumeroColumna()
/*  933:     */   {
/*  934: 971 */     return this.numeroColumna;
/*  935:     */   }
/*  936:     */   
/*  937:     */   public void setNumeroColumna(int numeroColumna)
/*  938:     */   {
/*  939: 975 */     this.numeroColumna = numeroColumna;
/*  940: 976 */     if (this.numeroColumna > 15) {
/*  941: 977 */       this.renderPanelDias = false;
/*  942:     */     } else {
/*  943: 979 */       this.renderPanelDias = true;
/*  944:     */     }
/*  945:     */   }
/*  946:     */   
/*  947:     */   public Turno getTurnoDialogo()
/*  948:     */   {
/*  949: 983 */     return this.turnoDialogo;
/*  950:     */   }
/*  951:     */   
/*  952:     */   public void setTurnoDialogo(Turno turnoDialogo)
/*  953:     */   {
/*  954: 987 */     this.turnoDialogo = turnoDialogo;
/*  955:     */   }
/*  956:     */   
/*  957:     */   public boolean isDia1()
/*  958:     */   {
/*  959: 991 */     return this.dia1;
/*  960:     */   }
/*  961:     */   
/*  962:     */   public void setDia1(boolean dia1)
/*  963:     */   {
/*  964: 995 */     this.dia1 = dia1;
/*  965:     */   }
/*  966:     */   
/*  967:     */   public boolean isDia2()
/*  968:     */   {
/*  969: 999 */     return this.dia2;
/*  970:     */   }
/*  971:     */   
/*  972:     */   public void setDia2(boolean dia2)
/*  973:     */   {
/*  974:1003 */     this.dia2 = dia2;
/*  975:     */   }
/*  976:     */   
/*  977:     */   public boolean isDia3()
/*  978:     */   {
/*  979:1007 */     return this.dia3;
/*  980:     */   }
/*  981:     */   
/*  982:     */   public void setDia3(boolean dia3)
/*  983:     */   {
/*  984:1011 */     this.dia3 = dia3;
/*  985:     */   }
/*  986:     */   
/*  987:     */   public boolean isDia4()
/*  988:     */   {
/*  989:1015 */     return this.dia4;
/*  990:     */   }
/*  991:     */   
/*  992:     */   public void setDia4(boolean dia4)
/*  993:     */   {
/*  994:1019 */     this.dia4 = dia4;
/*  995:     */   }
/*  996:     */   
/*  997:     */   public boolean isDia5()
/*  998:     */   {
/*  999:1023 */     return this.dia5;
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public void setDia5(boolean dia5)
/* 1003:     */   {
/* 1004:1027 */     this.dia5 = dia5;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public boolean isDia6()
/* 1008:     */   {
/* 1009:1031 */     return this.dia6;
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public void setDia6(boolean dia6)
/* 1013:     */   {
/* 1014:1035 */     this.dia6 = dia6;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public boolean isDia7()
/* 1018:     */   {
/* 1019:1039 */     return this.dia7;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public void setDia7(boolean dia7)
/* 1023:     */   {
/* 1024:1043 */     this.dia7 = dia7;
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public boolean isDia8()
/* 1028:     */   {
/* 1029:1047 */     return this.dia8;
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public void setDia8(boolean dia8)
/* 1033:     */   {
/* 1034:1051 */     this.dia8 = dia8;
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   public boolean isDia9()
/* 1038:     */   {
/* 1039:1055 */     return this.dia9;
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   public void setDia9(boolean dia9)
/* 1043:     */   {
/* 1044:1059 */     this.dia9 = dia9;
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public boolean isDia10()
/* 1048:     */   {
/* 1049:1063 */     return this.dia10;
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   public void setDia10(boolean dia10)
/* 1053:     */   {
/* 1054:1067 */     this.dia10 = dia10;
/* 1055:     */   }
/* 1056:     */   
/* 1057:     */   public boolean isDia11()
/* 1058:     */   {
/* 1059:1071 */     return this.dia11;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public void setDia11(boolean dia11)
/* 1063:     */   {
/* 1064:1075 */     this.dia11 = dia11;
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public boolean isDia12()
/* 1068:     */   {
/* 1069:1079 */     return this.dia12;
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public void setDia12(boolean dia12)
/* 1073:     */   {
/* 1074:1083 */     this.dia12 = dia12;
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   public boolean isDia13()
/* 1078:     */   {
/* 1079:1087 */     return this.dia13;
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   public void setDia13(boolean dia13)
/* 1083:     */   {
/* 1084:1091 */     this.dia13 = dia13;
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public boolean isDia14()
/* 1088:     */   {
/* 1089:1095 */     return this.dia14;
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public void setDia14(boolean dia14)
/* 1093:     */   {
/* 1094:1099 */     this.dia14 = dia14;
/* 1095:     */   }
/* 1096:     */   
/* 1097:     */   public boolean isDia15()
/* 1098:     */   {
/* 1099:1103 */     return this.dia15;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public void setDia15(boolean dia15)
/* 1103:     */   {
/* 1104:1107 */     this.dia15 = dia15;
/* 1105:     */   }
/* 1106:     */   
/* 1107:     */   public boolean isDia31()
/* 1108:     */   {
/* 1109:1111 */     return this.dia31;
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public void setDia31(boolean dia31)
/* 1113:     */   {
/* 1114:1115 */     this.dia31 = dia31;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public boolean isRenderPanelDias()
/* 1118:     */   {
/* 1119:1119 */     return this.renderPanelDias;
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public void setRenderPanelDias(boolean renderPanelDias)
/* 1123:     */   {
/* 1124:1123 */     this.renderPanelDias = renderPanelDias;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public boolean isSeleccionarTodos()
/* 1128:     */   {
/* 1129:1127 */     return this.seleccionarTodos;
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public void setSeleccionarTodos(boolean seleccionarTodos)
/* 1133:     */   {
/* 1134:1131 */     this.seleccionarTodos = seleccionarTodos;
/* 1135:     */   }
/* 1136:     */   
/* 1137:     */   public boolean isDiaDescanso1()
/* 1138:     */   {
/* 1139:1135 */     return this.diaDescanso1;
/* 1140:     */   }
/* 1141:     */   
/* 1142:     */   public void setDiaDescanso1(boolean diaDescanso1)
/* 1143:     */   {
/* 1144:1139 */     this.diaDescanso1 = diaDescanso1;
/* 1145:     */   }
/* 1146:     */   
/* 1147:     */   public boolean isDiaDescanso2()
/* 1148:     */   {
/* 1149:1143 */     return this.diaDescanso2;
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   public void setDiaDescanso2(boolean diaDescanso2)
/* 1153:     */   {
/* 1154:1147 */     this.diaDescanso2 = diaDescanso2;
/* 1155:     */   }
/* 1156:     */   
/* 1157:     */   public boolean isDiaDescanso3()
/* 1158:     */   {
/* 1159:1151 */     return this.diaDescanso3;
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   public void setDiaDescanso3(boolean diaDescanso3)
/* 1163:     */   {
/* 1164:1155 */     this.diaDescanso3 = diaDescanso3;
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public boolean isDiaDescanso4()
/* 1168:     */   {
/* 1169:1159 */     return this.diaDescanso4;
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public void setDiaDescanso4(boolean diaDescanso4)
/* 1173:     */   {
/* 1174:1163 */     this.diaDescanso4 = diaDescanso4;
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   public boolean isDiaDescanso5()
/* 1178:     */   {
/* 1179:1167 */     return this.diaDescanso5;
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   public void setDiaDescanso5(boolean diaDescanso5)
/* 1183:     */   {
/* 1184:1171 */     this.diaDescanso5 = diaDescanso5;
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   public boolean isDiaDescanso6()
/* 1188:     */   {
/* 1189:1175 */     return this.diaDescanso6;
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public void setDiaDescanso6(boolean diaDescanso6)
/* 1193:     */   {
/* 1194:1179 */     this.diaDescanso6 = diaDescanso6;
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public boolean isDiaDescanso7()
/* 1198:     */   {
/* 1199:1183 */     return this.diaDescanso7;
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public void setDiaDescanso7(boolean diaDescanso7)
/* 1203:     */   {
/* 1204:1187 */     this.diaDescanso7 = diaDescanso7;
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public boolean isDiaDescanso8()
/* 1208:     */   {
/* 1209:1191 */     return this.diaDescanso8;
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public void setDiaDescanso8(boolean diaDescanso8)
/* 1213:     */   {
/* 1214:1195 */     this.diaDescanso8 = diaDescanso8;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public boolean isDiaDescanso9()
/* 1218:     */   {
/* 1219:1199 */     return this.diaDescanso9;
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public void setDiaDescanso9(boolean diaDescanso9)
/* 1223:     */   {
/* 1224:1203 */     this.diaDescanso9 = diaDescanso9;
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public boolean isDiaDescanso10()
/* 1228:     */   {
/* 1229:1207 */     return this.diaDescanso10;
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public void setDiaDescanso10(boolean diaDescanso10)
/* 1233:     */   {
/* 1234:1211 */     this.diaDescanso10 = diaDescanso10;
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   public boolean isDiaDescanso11()
/* 1238:     */   {
/* 1239:1215 */     return this.diaDescanso11;
/* 1240:     */   }
/* 1241:     */   
/* 1242:     */   public void setDiaDescanso11(boolean diaDescanso11)
/* 1243:     */   {
/* 1244:1219 */     this.diaDescanso11 = diaDescanso11;
/* 1245:     */   }
/* 1246:     */   
/* 1247:     */   public boolean isDiaDescanso12()
/* 1248:     */   {
/* 1249:1223 */     return this.diaDescanso12;
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public void setDiaDescanso12(boolean diaDescanso12)
/* 1253:     */   {
/* 1254:1227 */     this.diaDescanso12 = diaDescanso12;
/* 1255:     */   }
/* 1256:     */   
/* 1257:     */   public boolean isDiaDescanso13()
/* 1258:     */   {
/* 1259:1231 */     return this.diaDescanso13;
/* 1260:     */   }
/* 1261:     */   
/* 1262:     */   public void setDiaDescanso13(boolean diaDescanso13)
/* 1263:     */   {
/* 1264:1235 */     this.diaDescanso13 = diaDescanso13;
/* 1265:     */   }
/* 1266:     */   
/* 1267:     */   public boolean isDiaDescanso14()
/* 1268:     */   {
/* 1269:1239 */     return this.diaDescanso14;
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   public void setDiaDescanso14(boolean diaDescanso14)
/* 1273:     */   {
/* 1274:1243 */     this.diaDescanso14 = diaDescanso14;
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   public boolean isDiaDescanso15()
/* 1278:     */   {
/* 1279:1247 */     return this.diaDescanso15;
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   public void setDiaDescanso15(boolean diaDescanso15)
/* 1283:     */   {
/* 1284:1251 */     this.diaDescanso15 = diaDescanso15;
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public boolean isDiaDescanso31()
/* 1288:     */   {
/* 1289:1255 */     return this.diaDescanso31;
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public void setDiaDescanso31(boolean diaDescanso31)
/* 1293:     */   {
/* 1294:1259 */     this.diaDescanso31 = diaDescanso31;
/* 1295:     */   }
/* 1296:     */   
/* 1297:     */   public boolean isDiaComplementario1()
/* 1298:     */   {
/* 1299:1263 */     return this.diaComplementario1;
/* 1300:     */   }
/* 1301:     */   
/* 1302:     */   public void setDiaComplementario1(boolean diaComplementario1)
/* 1303:     */   {
/* 1304:1267 */     this.diaComplementario1 = diaComplementario1;
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   public boolean isDiaComplementario2()
/* 1308:     */   {
/* 1309:1271 */     return this.diaComplementario2;
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   public void setDiaComplementario2(boolean diaComplementario2)
/* 1313:     */   {
/* 1314:1275 */     this.diaComplementario2 = diaComplementario2;
/* 1315:     */   }
/* 1316:     */   
/* 1317:     */   public boolean isDiaComplementario3()
/* 1318:     */   {
/* 1319:1279 */     return this.diaComplementario3;
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   public void setDiaComplementario3(boolean diaComplementario3)
/* 1323:     */   {
/* 1324:1283 */     this.diaComplementario3 = diaComplementario3;
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public boolean isDiaComplementario4()
/* 1328:     */   {
/* 1329:1287 */     return this.diaComplementario4;
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public void setDiaComplementario4(boolean diaComplementario4)
/* 1333:     */   {
/* 1334:1291 */     this.diaComplementario4 = diaComplementario4;
/* 1335:     */   }
/* 1336:     */   
/* 1337:     */   public boolean isDiaComplementario5()
/* 1338:     */   {
/* 1339:1295 */     return this.diaComplementario5;
/* 1340:     */   }
/* 1341:     */   
/* 1342:     */   public void setDiaComplementario5(boolean diaComplementario5)
/* 1343:     */   {
/* 1344:1299 */     this.diaComplementario5 = diaComplementario5;
/* 1345:     */   }
/* 1346:     */   
/* 1347:     */   public boolean isDiaComplementario6()
/* 1348:     */   {
/* 1349:1303 */     return this.diaComplementario6;
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   public void setDiaComplementario6(boolean diaComplementario6)
/* 1353:     */   {
/* 1354:1307 */     this.diaComplementario6 = diaComplementario6;
/* 1355:     */   }
/* 1356:     */   
/* 1357:     */   public boolean isDiaComplementario7()
/* 1358:     */   {
/* 1359:1311 */     return this.diaComplementario7;
/* 1360:     */   }
/* 1361:     */   
/* 1362:     */   public void setDiaComplementario7(boolean diaComplementario7)
/* 1363:     */   {
/* 1364:1315 */     this.diaComplementario7 = diaComplementario7;
/* 1365:     */   }
/* 1366:     */   
/* 1367:     */   public boolean isDiaComplementario8()
/* 1368:     */   {
/* 1369:1319 */     return this.diaComplementario8;
/* 1370:     */   }
/* 1371:     */   
/* 1372:     */   public void setDiaComplementario8(boolean diaComplementario8)
/* 1373:     */   {
/* 1374:1323 */     this.diaComplementario8 = diaComplementario8;
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public boolean isDiaComplementario9()
/* 1378:     */   {
/* 1379:1327 */     return this.diaComplementario9;
/* 1380:     */   }
/* 1381:     */   
/* 1382:     */   public void setDiaComplementario9(boolean diaComplementario9)
/* 1383:     */   {
/* 1384:1331 */     this.diaComplementario9 = diaComplementario9;
/* 1385:     */   }
/* 1386:     */   
/* 1387:     */   public boolean isDiaComplementario10()
/* 1388:     */   {
/* 1389:1335 */     return this.diaComplementario10;
/* 1390:     */   }
/* 1391:     */   
/* 1392:     */   public void setDiaComplementario10(boolean diaComplementario10)
/* 1393:     */   {
/* 1394:1339 */     this.diaComplementario10 = diaComplementario10;
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   public boolean isDiaComplementario11()
/* 1398:     */   {
/* 1399:1343 */     return this.diaComplementario11;
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   public void setDiaComplementario11(boolean diaComplementario11)
/* 1403:     */   {
/* 1404:1347 */     this.diaComplementario11 = diaComplementario11;
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   public boolean isDiaComplementario12()
/* 1408:     */   {
/* 1409:1351 */     return this.diaComplementario12;
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   public void setDiaComplementario12(boolean diaComplementario12)
/* 1413:     */   {
/* 1414:1355 */     this.diaComplementario12 = diaComplementario12;
/* 1415:     */   }
/* 1416:     */   
/* 1417:     */   public boolean isDiaComplementario13()
/* 1418:     */   {
/* 1419:1359 */     return this.diaComplementario13;
/* 1420:     */   }
/* 1421:     */   
/* 1422:     */   public void setDiaComplementario13(boolean diaComplementario13)
/* 1423:     */   {
/* 1424:1363 */     this.diaComplementario13 = diaComplementario13;
/* 1425:     */   }
/* 1426:     */   
/* 1427:     */   public boolean isDiaComplementario14()
/* 1428:     */   {
/* 1429:1367 */     return this.diaComplementario14;
/* 1430:     */   }
/* 1431:     */   
/* 1432:     */   public void setDiaComplementario14(boolean diaComplementario14)
/* 1433:     */   {
/* 1434:1371 */     this.diaComplementario14 = diaComplementario14;
/* 1435:     */   }
/* 1436:     */   
/* 1437:     */   public boolean isDiaComplementario15()
/* 1438:     */   {
/* 1439:1375 */     return this.diaComplementario15;
/* 1440:     */   }
/* 1441:     */   
/* 1442:     */   public void setDiaComplementario15(boolean diaComplementario15)
/* 1443:     */   {
/* 1444:1379 */     this.diaComplementario15 = diaComplementario15;
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public boolean isDiaComplementario31()
/* 1448:     */   {
/* 1449:1383 */     return this.diaComplementario31;
/* 1450:     */   }
/* 1451:     */   
/* 1452:     */   public void setDiaComplementario31(boolean diaComplementario31)
/* 1453:     */   {
/* 1454:1387 */     this.diaComplementario31 = diaComplementario31;
/* 1455:     */   }
/* 1456:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.procesos.PlanPersonalizadoHorarioEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */