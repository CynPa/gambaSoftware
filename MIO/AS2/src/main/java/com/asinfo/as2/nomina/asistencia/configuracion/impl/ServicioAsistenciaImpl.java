/*    1:     */ package com.asinfo.as2.nomina.asistencia.configuracion.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.dao.nomina.asistencia.AsistenciaDao;
/*    4:     */ import com.asinfo.as2.dao.nomina.asistencia.HoraExtraDao;
/*    5:     */ import com.asinfo.as2.entities.Departamento;
/*    6:     */ import com.asinfo.as2.entities.DetallePermisoEmpleado;
/*    7:     */ import com.asinfo.as2.entities.Empleado;
/*    8:     */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*    9:     */ import com.asinfo.as2.entities.PagoRol;
/*   10:     */ import com.asinfo.as2.entities.PermisoEmpleado;
/*   11:     */ import com.asinfo.as2.entities.SubsidioEmpleado;
/*   12:     */ import com.asinfo.as2.entities.TipoSubsidio;
/*   13:     */ import com.asinfo.as2.entities.nomina.asistencia.Asistencia;
/*   14:     */ import com.asinfo.as2.entities.nomina.asistencia.DetallePlanPersonalizadoHorarioEmpleado;
/*   15:     */ import com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia;
/*   16:     */ import com.asinfo.as2.entities.nomina.asistencia.HoraExtra;
/*   17:     */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*   18:     */ import com.asinfo.as2.entities.nomina.asistencia.MarcacionReloj;
/*   19:     */ import com.asinfo.as2.entities.nomina.asistencia.Turno;
/*   20:     */ import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
/*   21:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   22:     */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistencia;
/*   23:     */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistenciaConfiguracion;
/*   24:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPermisoEmpleado;
/*   25:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioSubsidioEmpleado;
/*   26:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion;
/*   27:     */ import com.asinfo.as2.servicio.tema.impl.ServicioGenericoImpl;
/*   28:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   29:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   30:     */ import java.math.BigDecimal;
/*   31:     */ import java.math.RoundingMode;
/*   32:     */ import java.sql.Time;
/*   33:     */ import java.text.SimpleDateFormat;
/*   34:     */ import java.util.ArrayList;
/*   35:     */ import java.util.Calendar;
/*   36:     */ import java.util.Date;
/*   37:     */ import java.util.HashMap;
/*   38:     */ import java.util.Iterator;
/*   39:     */ import java.util.List;
/*   40:     */ import java.util.Map;
/*   41:     */ import java.util.TreeMap;
/*   42:     */ import javax.ejb.EJB;
/*   43:     */ import javax.ejb.Stateless;
/*   44:     */ 
/*   45:     */ @Stateless
/*   46:     */ public class ServicioAsistenciaImpl
/*   47:     */   extends ServicioGenericoImpl<Asistencia>
/*   48:     */   implements ServicioAsistencia
/*   49:     */ {
/*   50:     */   @EJB
/*   51:     */   private transient AsistenciaDao asistenciaDao;
/*   52:     */   @EJB
/*   53:     */   private transient ServicioAsistenciaConfiguracion servicioAsistenciaConfiguracion;
/*   54:     */   @EJB
/*   55:     */   private transient ServicioSubsidioEmpleado servicioSubsidioEmpleado;
/*   56:     */   @EJB
/*   57:     */   private transient HoraExtraDao horaExtraDao;
/*   58:     */   @EJB
/*   59:     */   private transient ServicioVacacion servicioVacacion;
/*   60:     */   @EJB
/*   61:     */   private transient ServicioPermisoEmpleado servicioPermisoEmpleado;
/*   62:     */   
/*   63:     */   public void procesarAsistencia(int idOrganizacion, Date fecha, Departamento departamento)
/*   64:     */     throws AS2Exception
/*   65:     */   {
/*   66:  79 */     generarAsistencia(idOrganizacion, fecha, departamento);
/*   67:     */     
/*   68:     */ 
/*   69:  82 */     registrarAsistencia(idOrganizacion, fecha, departamento);
/*   70:     */     
/*   71:     */ 
/*   72:  85 */     generarNovedades(idOrganizacion, fecha);
/*   73:     */     
/*   74:     */ 
/*   75:  88 */     calcularHorasExtras(idOrganizacion, fecha);
/*   76:     */   }
/*   77:     */   
/*   78:     */   @Deprecated
/*   79:     */   public void crearAsistenciaManual(Asistencia asistencia, Turno turno)
/*   80:     */     throws AS2Exception
/*   81:     */   {
/*   82:  98 */     Date fecha = FuncionesUtiles.setAtributoFecha(asistencia.getFecha());
/*   83:  99 */     Date fechaDia2 = FuncionesUtiles.sumarFechaDiasMeses(fecha, 1);
/*   84:     */     
/*   85: 101 */     boolean indicadorDiaFestivoDia1 = this.servicioAsistenciaConfiguracion.esDiaFestivo(asistencia.getIdOrganizacion(), fecha);
/*   86: 102 */     boolean indicadorDiaFestivoDia2 = this.servicioAsistenciaConfiguracion.esDiaFestivo(asistencia.getIdOrganizacion(), fechaDia2);
/*   87:     */     
/*   88: 104 */     Calendar calFecha = Calendar.getInstance();
/*   89: 105 */     calFecha.setFirstDayOfWeek(1);
/*   90: 106 */     calFecha.setTime(fecha);
/*   91: 107 */     int diaMes = calFecha.get(5);
/*   92:     */     
/*   93: 109 */     asistencia.setEntrada(turno.getHoraEntrada());
/*   94: 110 */     asistencia.setSalida(turno.getHoraSalida());
/*   95: 111 */     asistencia.setDiaMes(diaMes);
/*   96: 112 */     asistencia.setIndicadorDiaFestivo(indicadorDiaFestivoDia1);
/*   97: 113 */     asistencia.setIndicadorHorasAdelantoExtra(ParametrosSistema.getHorasAdelantoConsideroExtra(asistencia.getIdOrganizacion()));
/*   98: 114 */     asistencia.setIndicadorPagoHorasSuplementarias(turno.isIndicadorPagoHorasSuplementarias());
/*   99: 115 */     asignarHorarioReceso(asistencia, turno);
/*  100: 116 */     this.asistenciaDao.guardar(asistencia);
/*  101: 118 */     if (asistencia.getEntrada().after(asistencia.getSalidaReal()))
/*  102:     */     {
/*  103: 120 */       asistencia.setSalida(FuncionesUtiles.getHoraCero(turno.getHoraSalida()));
/*  104:     */       
/*  105:     */ 
/*  106: 123 */       Asistencia asistencia2 = new Asistencia();
/*  107: 124 */       asistencia2.setAsistenciaPadre(asistencia);
/*  108: 125 */       asistencia2.setIdOrganizacion(asistencia.getIdOrganizacion());
/*  109: 126 */       asistencia2.setEmpleado(asistencia.getEmpleado());
/*  110: 127 */       asistencia2.setFecha(fechaDia2);
/*  111: 128 */       asistencia2.setEntrada(FuncionesUtiles.getHoraCero(turno.getHoraSalida()));
/*  112: 129 */       asistencia2.setSalida(turno.getHoraSalida());
/*  113: 130 */       asistencia2.setIndicadorHorasAdelantoExtra(asistencia.getIndicadorHorasAdelantoExtra());
/*  114: 131 */       asistencia2.setIndicadorCreadaManualmente(asistencia.isIndicadorCreadaManualmente());
/*  115: 132 */       asistencia2.setIndicadorPagoHorasSuplementarias(turno.isIndicadorPagoHorasSuplementarias());
/*  116: 133 */       asignarHorarioReceso(asistencia2, turno);
/*  117:     */       
/*  118:     */ 
/*  119: 136 */       asistencia2.setDiaMes(diaMes);
/*  120: 137 */       asistencia2.setIndicadorDiaFestivo(indicadorDiaFestivoDia2);
/*  121: 138 */       asistencia2.setIndicadorDiaDescanso(asistencia.getIndicadorDiaDescanso());
/*  122: 139 */       this.asistenciaDao.guardar(asistencia2);
/*  123:     */     }
/*  124:     */   }
/*  125:     */   
/*  126:     */   private Map<String, Asistencia> getMapaAsistencias(int idOrganizacion, Date fecha, Departamento departamento)
/*  127:     */   {
/*  128: 144 */     Map<String, Asistencia> mapaAsistenciasBD = new HashMap();
/*  129: 145 */     List<Asistencia> listaAsistencia = this.asistenciaDao.getAsistenciaPorFecha(idOrganizacion, fecha, departamento, true);
/*  130: 146 */     for (Asistencia a : listaAsistencia) {
/*  131: 147 */       if (!a.isIndicadorCreadaManualmente())
/*  132:     */       {
/*  133: 148 */         a.setEliminado(true);
/*  134: 149 */         mapaAsistenciasBD.put(a.getEmpleado().getId() + "~" + a.getFecha(), a);
/*  135:     */       }
/*  136:     */     }
/*  137: 152 */     return mapaAsistenciasBD;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void generarAsistencia(int idOrganizacion, Date fecha)
/*  141:     */     throws AS2Exception
/*  142:     */   {
/*  143: 157 */     generarAsistencia(idOrganizacion, fecha, null);
/*  144:     */   }
/*  145:     */   
/*  146:     */   public void generarAsistencia(int idOrganizacion, Date fecha, Departamento departamento)
/*  147:     */     throws AS2Exception
/*  148:     */   {
/*  149: 163 */     SimpleDateFormat dfa = new SimpleDateFormat("yyyy-MM-dd");
/*  150: 164 */     String strFechaAsistencia = dfa.format(fecha);
/*  151:     */     
/*  152:     */ 
/*  153: 167 */     fecha = FuncionesUtiles.setAtributoFecha(fecha);
/*  154: 168 */     Date fechaDia2 = FuncionesUtiles.sumarFechaDiasMeses(fecha, 1);
/*  155:     */     
/*  156: 170 */     Map<String, Asistencia> hmAsistencia = new TreeMap();
/*  157: 171 */     Map<Empleado, Object[]> hmEmpleados = new HashMap();
/*  158: 172 */     Map<String, Asistencia> mapaAsistenciasBD = getMapaAsistencias(idOrganizacion, fecha, departamento);
/*  159:     */     
/*  160: 174 */     SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
/*  161: 175 */     Calendar calFecha = Calendar.getInstance();
/*  162: 176 */     calFecha.setFirstDayOfWeek(1);
/*  163: 177 */     calFecha.setTime(fecha);
/*  164:     */     
/*  165: 179 */     String strFecha1 = df.format(fecha);
/*  166: 180 */     String strFecha2 = df.format(fechaDia2);
/*  167: 181 */     int diaMes = calFecha.get(5);
/*  168:     */     
/*  169:     */ 
/*  170:     */ 
/*  171: 185 */     int diaSemana = calFecha.get(7) - 1;
/*  172: 186 */     List<Empleado> listaEmpleado = this.asistenciaDao.obtenerHorariosPorDiaSemana(idOrganizacion, fecha, diaMes, diaSemana, departamento);
/*  173: 188 */     for (Iterator localIterator = listaEmpleado.iterator(); localIterator.hasNext();)
/*  174:     */     {
/*  175: 188 */       empleado = (Empleado)localIterator.next();
/*  176: 189 */       Turno turno = getTurnoDiaSemana(empleado.getHorarioEmpleado(), diaSemana);
/*  177:     */       
/*  178:     */ 
/*  179:     */ 
/*  180: 193 */       Object[] dato = { empleado, turno, getIndicadorDiaDescansoUOpcionalDiaSemana(empleado.getHorarioEmpleado(), diaSemana, true), Boolean.valueOf(turno.isIndicadorDiaComplementario()), getIndicadorDiaDescansoUOpcionalDiaSemana(empleado.getHorarioEmpleado(), diaSemana, false) };
/*  181: 194 */       hmEmpleados.put(empleado, dato);
/*  182:     */     }
/*  183:     */     Empleado empleado;
/*  184: 197 */     Object listaHorario = this.asistenciaDao.obtenerHorariosMensual(idOrganizacion, calFecha, diaMes, departamento);
/*  185: 200 */     for (DetallePlanPersonalizadoHorarioEmpleado detalle : (List)listaHorario)
/*  186:     */     {
/*  187: 201 */       Turno turno = detalle.getTurnoDiaMes(Integer.valueOf(diaMes));
/*  188: 202 */       Boolean indicadorDiaDescanso = detalle.getIndicador(Integer.valueOf(diaMes), "getIndicadorDiaDescanso");
/*  189: 203 */       Boolean indicadorDiaComplementario = detalle.getIndicador(Integer.valueOf(diaMes), "getIndicadorDiaComplementario");
/*  190: 204 */       if (turno != null)
/*  191:     */       {
/*  192: 205 */         Object[] dato = { detalle.getEmpleado(), turno, indicadorDiaDescanso, indicadorDiaComplementario, Boolean.valueOf(false) };
/*  193: 206 */         hmEmpleados.put(detalle.getEmpleado(), dato);
/*  194:     */       }
/*  195:     */     }
/*  196: 211 */     for (Object[] dato : hmEmpleados.values())
/*  197:     */     {
/*  198: 213 */       Empleado empleado = (Empleado)dato[0];
/*  199: 214 */       Turno turno = (Turno)dato[1];
/*  200: 215 */       Boolean indicadorDiaDescanso = (Boolean)dato[2];
/*  201: 216 */       Boolean indicadorDiaComplementario = (Boolean)dato[3];
/*  202: 217 */       Boolean indicadorDiaOpcional = (Boolean)dato[4];
/*  203: 218 */       Asistencia asistencia = (Asistencia)mapaAsistenciasBD.get(empleado.getId() + "~" + strFechaAsistencia);
/*  204: 219 */       if (asistencia == null)
/*  205:     */       {
/*  206: 220 */         asistencia = new Asistencia();
/*  207: 221 */         asistencia.setIdOrganizacion(idOrganizacion);
/*  208: 222 */         asistencia.setEmpleado(empleado);
/*  209: 223 */         asistencia.setFecha(fecha);
/*  210: 224 */         asistencia.setEntrada(turno.getHoraEntrada());
/*  211: 225 */         asistencia.setSalida(turno.getHoraSalida());
/*  212: 226 */         asignarHorarioReceso(asistencia, turno);
/*  213: 227 */         asistencia.setDiaMes(diaMes);
/*  214:     */         
/*  215: 229 */         asistencia.setIndicadorPagoHorasSuplementarias(turno.isIndicadorPagoHorasSuplementarias());
/*  216:     */         
/*  217: 231 */         asistencia.setIndicadorDiaDescanso(indicadorDiaDescanso);
/*  218: 232 */         asistencia.setIndicadorDiaComplementario(indicadorDiaComplementario.booleanValue());
/*  219: 233 */         asistencia.setIndicadorDiaOpcional(indicadorDiaOpcional.booleanValue());
/*  220: 234 */         asistencia.setIndicadorHorasAdelantoExtra(ParametrosSistema.getHorasAdelantoConsideroExtra(idOrganizacion));
/*  221: 235 */         hmAsistencia.put(strFecha1 + String.valueOf(empleado.getId()), asistencia);
/*  222: 237 */         if (asistencia.getEntrada().after(asistencia.getSalidaReal()))
/*  223:     */         {
/*  224: 240 */           asistencia.setSalida(FuncionesUtiles.setAtributoFecha(turno.getHoraSalida()));
/*  225:     */           
/*  226: 242 */           asignarHorarioReceso(asistencia, turno);
/*  227:     */           
/*  228: 244 */           Asistencia asistencia2 = new Asistencia();
/*  229: 245 */           asistencia2.setAsistenciaPadre(asistencia);
/*  230: 246 */           asistencia2.setIdOrganizacion(idOrganizacion);
/*  231: 247 */           asistencia2.setEmpleado(empleado);
/*  232: 248 */           asistencia2.setFecha(fechaDia2);
/*  233: 249 */           asistencia2.setEntrada(FuncionesUtiles.setAtributoFecha(turno.getHoraSalida()));
/*  234: 250 */           asistencia2.setSalida(turno.getHoraSalida());
/*  235: 251 */           asistencia2.setIndicadorDiaDescanso(indicadorDiaDescanso);
/*  236: 252 */           asistencia2.setIndicadorDiaComplementario(indicadorDiaComplementario.booleanValue());
/*  237: 253 */           asistencia2.setIndicadorDiaOpcional(indicadorDiaOpcional.booleanValue());
/*  238: 254 */           asignarHorarioReceso(asistencia2, turno);
/*  239:     */           
/*  240: 256 */           asistencia2.setIndicadorPagoHorasSuplementarias(turno.isIndicadorPagoHorasSuplementarias());
/*  241:     */           
/*  242: 258 */           asistencia2.setIndicadorHorasAdelantoExtra(asistencia.getIndicadorHorasAdelantoExtra());
/*  243:     */           
/*  244:     */ 
/*  245: 261 */           asistencia2.setDiaMes(diaMes);
/*  246: 262 */           asistencia2.setIndicadorDiaDescanso(asistencia.getIndicadorDiaDescanso());
/*  247: 263 */           hmAsistencia.put(strFecha2 + String.valueOf(empleado.getId()), asistencia2);
/*  248:     */         }
/*  249:     */       }
/*  250:     */       else
/*  251:     */       {
/*  252: 266 */         asistencia.setEliminado(false);
/*  253: 267 */         asistencia.setIdOrganizacion(idOrganizacion);
/*  254: 268 */         asistencia.setEmpleado(empleado);
/*  255: 269 */         asistencia.setFecha(fecha);
/*  256: 270 */         asistencia.setEntrada(turno.getHoraEntrada());
/*  257: 271 */         asistencia.setSalida(turno.getHoraSalida());
/*  258: 272 */         asignarHorarioReceso(asistencia, turno);
/*  259: 273 */         asistencia.setDiaMes(diaMes);
/*  260:     */         
/*  261: 275 */         asistencia.setIndicadorPagoHorasSuplementarias(turno.isIndicadorPagoHorasSuplementarias());
/*  262:     */         
/*  263: 277 */         asistencia.setIndicadorDiaDescanso(indicadorDiaDescanso);
/*  264: 278 */         asistencia.setIndicadorDiaComplementario(indicadorDiaComplementario.booleanValue());
/*  265: 279 */         asistencia.setIndicadorDiaOpcional(indicadorDiaOpcional.booleanValue());
/*  266: 280 */         asistencia.setIndicadorHorasAdelantoExtra(ParametrosSistema.getHorasAdelantoConsideroExtra(idOrganizacion));
/*  267: 281 */         hmAsistencia.put(strFecha1 + String.valueOf(empleado.getId()), asistencia);
/*  268: 283 */         if (asistencia.getEntrada().after(asistencia.getSalidaReal()))
/*  269:     */         {
/*  270: 286 */           asistencia.setSalida(FuncionesUtiles.setAtributoFecha(turno.getHoraSalida()));
/*  271:     */           
/*  272: 288 */           asignarHorarioReceso(asistencia, turno);
/*  273:     */           
/*  274: 290 */           Asistencia asistencia2 = this.asistenciaDao.getAsistenciaHija(asistencia);
/*  275: 291 */           asistencia2.setEntrada(FuncionesUtiles.setAtributoFecha(turno.getHoraSalida()));
/*  276: 292 */           asistencia2.setSalida(turno.getHoraSalida());
/*  277: 293 */           asistencia2.setIndicadorDiaDescanso(indicadorDiaDescanso);
/*  278: 294 */           asistencia2.setIndicadorDiaComplementario(indicadorDiaComplementario.booleanValue());
/*  279: 295 */           asistencia2.setIndicadorDiaOpcional(indicadorDiaOpcional.booleanValue());
/*  280: 296 */           asignarHorarioReceso(asistencia2, turno);
/*  281:     */           
/*  282: 298 */           asistencia2.setIndicadorPagoHorasSuplementarias(turno.isIndicadorPagoHorasSuplementarias());
/*  283: 299 */           asistencia2.setIndicadorHorasAdelantoExtra(asistencia.getIndicadorHorasAdelantoExtra());
/*  284:     */           
/*  285: 301 */           asistencia2.setIndicadorDiaDescanso(asistencia.getIndicadorDiaDescanso());
/*  286: 302 */           hmAsistencia.put(strFecha2 + String.valueOf(empleado.getId()), asistencia2);
/*  287:     */         }
/*  288: 304 */         mapaAsistenciasBD.remove(empleado.getId() + "~" + strFechaAsistencia);
/*  289:     */       }
/*  290:     */     }
/*  291: 308 */     for (Asistencia asistencia : hmAsistencia.values()) {
/*  292: 309 */       this.asistenciaDao.guardar(asistencia);
/*  293:     */     }
/*  294: 314 */     for (Asistencia asistencia : mapaAsistenciasBD.values()) {
/*  295: 315 */       this.asistenciaDao.eliminarAsistencia(asistencia);
/*  296:     */     }
/*  297:     */   }
/*  298:     */   
/*  299:     */   public void registrarAsistencia(int idOrganizacion, Date fecha)
/*  300:     */     throws AS2Exception
/*  301:     */   {
/*  302: 321 */     registrarAsistencia(idOrganizacion, fecha, null);
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void registrarAsistencia(int idOrganizacion, Date fecha, Departamento departamento)
/*  306:     */     throws AS2Exception
/*  307:     */   {
/*  308: 328 */     int tiempoMinEntreTimbradas = ParametrosSistema.getTiempoMinimoEntreTimbradas(idOrganizacion).intValue();
/*  309: 329 */     int tiempoMaximoSalidaTemprana = ParametrosSistema.getTiempoMarcacionValidaSalida(idOrganizacion).intValue();
/*  310: 330 */     int tiempoMaximoEntradaTemprana = ParametrosSistema.getTiempoMarcacionValidaEntrada(idOrganizacion).intValue();
/*  311: 331 */     Date fechaDiaPrevio = FuncionesUtiles.sumarFechaDiasMeses(fecha, -1);
/*  312: 332 */     Date horaCero = FuncionesUtiles.stringToDate("00:00", "HH:mm");
/*  313:     */     
/*  314:     */ 
/*  315: 335 */     List<MarcacionReloj> listaMarcaciones = this.asistenciaDao.getMarcacionesRelojPorFecha(idOrganizacion, fecha, departamento);
/*  316: 336 */     Map<Integer, List<MarcacionReloj>> hmMarcaciones = new HashMap();
/*  317: 339 */     for (MarcacionReloj marcacionReloj : listaMarcaciones)
/*  318:     */     {
/*  319: 340 */       List<MarcacionReloj> listaMarcacion = (List)hmMarcaciones.get(Integer.valueOf(marcacionReloj.getEmpleado().getId()));
/*  320: 342 */       if (listaMarcacion == null)
/*  321:     */       {
/*  322: 343 */         listaMarcacion = new ArrayList();
/*  323: 344 */         hmMarcaciones.put(Integer.valueOf(marcacionReloj.getEmpleado().getId()), listaMarcacion);
/*  324:     */       }
/*  325: 347 */       listaMarcacion.add(marcacionReloj);
/*  326:     */     }
/*  327: 351 */     for (List<MarcacionReloj> listaMarcacion : hmMarcaciones.values())
/*  328:     */     {
/*  329: 352 */       MarcacionReloj marcacionRelojOld = null;
/*  330: 353 */       listaMarcacionRepetidas = new ArrayList();
/*  331: 355 */       for (MarcacionReloj marcacionReloj : listaMarcacion)
/*  332:     */       {
/*  333: 356 */         if ((marcacionRelojOld != null) && 
/*  334: 357 */           (FuncionesUtiles.getMinutosEntreFechas(marcacionRelojOld.getMarcacion(), marcacionReloj
/*  335: 358 */           .getMarcacion()) <= tiempoMinEntreTimbradas)) {
/*  336: 359 */           listaMarcacionRepetidas.add(marcacionReloj);
/*  337:     */         }
/*  338: 362 */         marcacionRelojOld = marcacionReloj;
/*  339:     */       }
/*  340: 365 */       if (!listaMarcacionRepetidas.isEmpty()) {
/*  341: 366 */         listaMarcacion.removeAll(listaMarcacionRepetidas);
/*  342:     */       }
/*  343:     */     }
/*  344:     */     List<MarcacionReloj> listaMarcacionRepetidas;
/*  345: 371 */     Object listaAsistencia = this.asistenciaDao.getAsistenciaPorFecha(idOrganizacion, fecha, departamento, false);
/*  346:     */     
/*  347: 373 */     List<Asistencia> listaAsistenciaDiaPrevio = this.asistenciaDao.getAsistenciaPorFecha(idOrganizacion, fechaDiaPrevio, departamento, false);
/*  348:     */     
/*  349: 375 */     Map<Integer, Asistencia> hmAsistencia = new HashMap();
/*  350: 376 */     for (Asistencia asistencia : (List)listaAsistencia) {
/*  351: 379 */       if (!hmAsistencia.containsKey(Integer.valueOf(asistencia.getEmpleado().getId()))) {
/*  352: 380 */         hmAsistencia.put(Integer.valueOf(asistencia.getEmpleado().getId()), asistencia);
/*  353:     */       }
/*  354:     */     }
/*  355: 385 */     for (Asistencia asistenciaDiaPrevio : listaAsistenciaDiaPrevio)
/*  356:     */     {
/*  357: 386 */       Asistencia asistencia = (Asistencia)hmAsistencia.get(Integer.valueOf(asistenciaDiaPrevio.getEmpleado().getId()));
/*  358: 387 */       if (asistencia != null) {
/*  359: 388 */         asistencia.setAsistenciaDiaPrevio(asistenciaDiaPrevio);
/*  360:     */       } else {
/*  361: 390 */         ((List)listaAsistencia).add(asistenciaDiaPrevio);
/*  362:     */       }
/*  363:     */     }
/*  364: 395 */     for (int i = 0; i < ((List)listaAsistencia).size(); i++)
/*  365:     */     {
/*  366: 397 */       Asistencia asistencia = (Asistencia)((List)listaAsistencia).get(i);
/*  367: 398 */       Asistencia asistenciaSig = null;
/*  368: 399 */       Empleado empleado = asistencia.getEmpleado();
/*  369: 400 */       if (i < ((List)listaAsistencia).size() - 1) {
/*  370: 401 */         asistenciaSig = (Asistencia)((List)listaAsistencia).get(i + 1);
/*  371:     */       }
/*  372: 404 */       List<MarcacionReloj> listaMarcacionIni = (List)hmMarcaciones.get(Integer.valueOf(empleado.getId()));
/*  373: 405 */       List<MarcacionReloj> listaMarcacion = listaMarcacionIni;
/*  374: 407 */       if (listaMarcacion != null)
/*  375:     */       {
/*  376: 411 */         if ((asistenciaSig != null) && (asistencia.getEmpleado().equals(asistenciaSig.getEmpleado())) && 
/*  377: 412 */           (asistencia.getFecha().compareTo(asistenciaSig.getFecha()) == 0))
/*  378:     */         {
/*  379: 414 */           Long horaMediaES = Long.valueOf(0L);
/*  380: 415 */           if ((asistencia.getEntrada() != null) && (asistencia.getSalida() != null)) {
/*  381: 416 */             horaMediaES = Long.valueOf(asistencia.getSalida().getTime() + 
/*  382: 417 */               (asistenciaSig.getEntrada().getTime() - asistencia.getSalida().getTime()) / 2L);
/*  383:     */           }
/*  384: 420 */           listaMarcacion = new ArrayList();
/*  385: 421 */           for (MarcacionReloj marcacion : listaMarcacionIni) {
/*  386: 422 */             if (marcacion.getMarcacion().getTime() < horaMediaES.longValue()) {
/*  387: 423 */               listaMarcacion.add(marcacion);
/*  388:     */             }
/*  389:     */           }
/*  390: 427 */           listaMarcacionIni.removeAll(listaMarcacion);
/*  391:     */         }
/*  392: 430 */         if ((asistencia.getAsistenciaPadre() != null) && (listaMarcacion.size() > 0))
/*  393:     */         {
/*  394: 432 */           MarcacionReloj mr = new MarcacionReloj();
/*  395: 433 */           mr.setEmpleado(asistencia.getEmpleado());
/*  396: 434 */           mr.setFecha(asistencia.getFecha());
/*  397: 435 */           mr.setMarcacion(asistencia.getEntrada());
/*  398:     */           
/*  399: 437 */           listaMarcacion.add(0, mr);
/*  400:     */         }
/*  401: 441 */         if ((!listaMarcacion.isEmpty()) && (asistencia.getAsistenciaPadre() == null) && (
/*  402: 442 */           (asistencia.getAsistenciaDiaPrevio() != null) || (asistencia.getFecha().equals(fechaDiaPrevio))))
/*  403:     */         {
/*  404: 444 */           MarcacionReloj marcacion = (MarcacionReloj)listaMarcacion.get(0);
/*  405: 446 */           if (asistencia.getAsistenciaDiaPrevio() != null)
/*  406:     */           {
/*  407: 450 */             Time horaMarcacion = (Time)marcacion.getMarcacion();
/*  408: 451 */             Time horaEntradaMinima = new Time(asistencia.getEntrada().getTime());
/*  409:     */             
/*  410: 453 */             horaEntradaMinima.setHours(horaEntradaMinima.getHours() / 2);
/*  411: 454 */             if (horaMarcacion.getTime() < horaEntradaMinima.getTime())
/*  412:     */             {
/*  413: 455 */               if (asistencia.getAsistenciaDiaPrevio().getMarcacionEntradaReingreso1() != null) {
/*  414: 456 */                 asistencia.getAsistenciaDiaPrevio().setMarcacionSalidaReingreso1(marcacion.getMarcacion());
/*  415: 457 */               } else if (asistencia.getAsistenciaDiaPrevio().getMarcacionEntradaReingreso2() != null) {
/*  416: 458 */                 asistencia.getAsistenciaDiaPrevio().setMarcacionSalidaReingreso2(marcacion.getMarcacion());
/*  417:     */               } else {
/*  418: 461 */                 asistencia.getAsistenciaDiaPrevio().setMarcacionSalida(marcacion.getMarcacion());
/*  419:     */               }
/*  420: 463 */               asistencia.getAsistenciaDiaPrevio().setEditado(true);
/*  421: 464 */               listaMarcacion.remove(0);
/*  422:     */             }
/*  423:     */           }
/*  424: 467 */           else if (asistencia.getFecha().equals(fechaDiaPrevio))
/*  425:     */           {
/*  426: 471 */             if (asistencia.getMarcacionEntradaReingreso1() != null) {
/*  427: 472 */               asistencia.setMarcacionSalidaReingreso1(marcacion.getMarcacion());
/*  428: 473 */             } else if (asistencia.getMarcacionEntradaReingreso2() != null) {
/*  429: 474 */               asistencia.setMarcacionSalidaReingreso2(marcacion.getMarcacion());
/*  430:     */             } else {
/*  431: 476 */               asistencia.setMarcacionSalida(marcacion.getMarcacion());
/*  432:     */             }
/*  433: 478 */             listaMarcacion.clear();
/*  434:     */           }
/*  435:     */         }
/*  436: 483 */         if (!listaMarcacion.isEmpty())
/*  437:     */         {
/*  438: 484 */           asistencia.setMarcacionEntrada(null);
/*  439: 485 */           asistencia.setMarcacionSalida(null);
/*  440: 486 */           asistencia.setMarcacionSalidaReceso(null);
/*  441: 487 */           asistencia.setMarcacionEntradaReceso(null);
/*  442: 488 */           asistencia.setMarcacionEntradaReingreso1(null);
/*  443: 489 */           asistencia.setMarcacionSalidaReingreso1(null);
/*  444: 490 */           asistencia.setMarcacionEntradaReingreso2(null);
/*  445: 491 */           asistencia.setMarcacionSalidaReingreso2(null);
/*  446:     */         }
/*  447:     */         MarcacionReloj marcacionEntrada;
/*  448: 495 */         if (listaMarcacion.size() >= 2)
/*  449:     */         {
/*  450: 497 */           marcacionEntrada = getMarcacionCercanaASalida(listaMarcacion, asistencia.getEntrada(), tiempoMaximoEntradaTemprana);
/*  451: 498 */           marcacionEntrada = marcacionEntrada == null ? (MarcacionReloj)listaMarcacion.get(0) : marcacionEntrada;
/*  452: 499 */           asistencia.setMarcacionEntrada(marcacionEntrada.getMarcacion());
/*  453: 500 */           listaMarcacion.remove(marcacionEntrada);
/*  454: 502 */           if ((asistencia.getSalida() != null) && (!asistencia.getSalida().equals(horaCero)))
/*  455:     */           {
/*  456: 503 */             MarcacionReloj marcacionSalida = getMarcacionCercanaASalida(listaMarcacion, asistencia.getSalida(), tiempoMaximoSalidaTemprana);
/*  457:     */             
/*  458: 505 */             marcacionSalida = marcacionSalida == null ? (MarcacionReloj)listaMarcacion.get(listaMarcacion.size() - 1) : marcacionSalida;
/*  459: 506 */             asistencia.setMarcacionSalida(marcacionSalida.getMarcacion());
/*  460: 507 */             listaMarcacion.remove(marcacionSalida);
/*  461:     */           }
/*  462:     */         }
/*  463: 512 */         for (MarcacionReloj marcacion : listaMarcacion) {
/*  464: 513 */           if (asistencia.getMarcacionEntrada() == null) {
/*  465: 514 */             asistencia.setMarcacionEntrada(marcacion.getMarcacion());
/*  466: 516 */           } else if ((asistencia.getMarcacionSalidaReceso() == null) && 
/*  467: 517 */             (verificarMarcacion(asistencia.getMarcacionEntrada(), asistencia.getMarcacionSalida(), marcacion.getMarcacion(), false))) {
/*  468: 518 */             asistencia.setMarcacionSalidaReceso(marcacion.getMarcacion());
/*  469: 520 */           } else if ((asistencia.getMarcacionEntradaReceso() == null) && 
/*  470: 521 */             (verificarMarcacion(asistencia.getMarcacionEntrada(), asistencia.getMarcacionSalida(), marcacion.getMarcacion(), false))) {
/*  471: 522 */             asistencia.setMarcacionEntradaReceso(marcacion.getMarcacion());
/*  472: 524 */           } else if (asistencia.getMarcacionSalida() == null) {
/*  473: 525 */             asistencia.setMarcacionSalida(marcacion.getMarcacion());
/*  474: 527 */           } else if (asistencia.getMarcacionEntradaReingreso1() == null) {
/*  475: 528 */             asistencia.setMarcacionEntradaReingreso1(marcacion.getMarcacion());
/*  476: 529 */           } else if (asistencia.getMarcacionSalidaReingreso1() == null) {
/*  477: 530 */             asistencia.setMarcacionSalidaReingreso1(marcacion.getMarcacion());
/*  478: 531 */           } else if (asistencia.getMarcacionEntradaReingreso2() == null) {
/*  479: 532 */             asistencia.setMarcacionEntradaReingreso2(marcacion.getMarcacion());
/*  480: 533 */           } else if (asistencia.getMarcacionSalidaReingreso2() == null) {
/*  481: 534 */             asistencia.setMarcacionSalidaReingreso2(marcacion.getMarcacion());
/*  482:     */           }
/*  483:     */         }
/*  484: 537 */         setEntradaSalidaAsistenciaOpcional(asistencia, false);
/*  485: 538 */         if ((asistencia.getAsistenciaPadre() != null) && (asistencia.getMarcacionSalida() != null))
/*  486:     */         {
/*  487: 539 */           asistencia.getAsistenciaPadre().setMarcacionSalida(FuncionesUtiles.getHoraCero(asistencia.getEntrada()));
/*  488: 540 */           setEntradaSalidaAsistenciaOpcional(asistencia.getAsistenciaPadre(), true);
/*  489: 541 */           guardar(asistencia.getAsistenciaPadre());
/*  490:     */         }
/*  491: 544 */         if ((asistencia.getAsistenciaDiaPrevio() != null) && (asistencia.getAsistenciaDiaPrevio().isEditado())) {
/*  492: 545 */           guardar(asistencia.getAsistenciaDiaPrevio());
/*  493:     */         }
/*  494: 547 */         guardar(asistencia);
/*  495:     */       }
/*  496:     */       else
/*  497:     */       {
/*  498: 549 */         guardar(asistencia);
/*  499:     */       }
/*  500:     */     }
/*  501:     */   }
/*  502:     */   
/*  503:     */   private MarcacionReloj getMarcacionCercanaASalida(List<MarcacionReloj> listaMarcacion, Date fachaCompare, int tiempoPermitido)
/*  504:     */   {
/*  505: 557 */     List<Date> dates = new ArrayList();
/*  506: 558 */     Calendar cal = Calendar.getInstance();
/*  507:     */     
/*  508: 560 */     cal.setTime(fachaCompare);
/*  509: 561 */     cal.add(12, -tiempoPermitido);
/*  510: 562 */     Date oneHourBack = cal.getTime();
/*  511: 563 */     Map<Date, MarcacionReloj> hashMarcaciones = new HashMap();
/*  512: 564 */     for (MarcacionReloj m : listaMarcacion) {
/*  513: 565 */       if (m.getMarcacion().compareTo(oneHourBack) >= 0)
/*  514:     */       {
/*  515: 566 */         hashMarcaciones.put(m.getMarcacion(), m);
/*  516: 567 */         dates.add(m.getMarcacion());
/*  517:     */       }
/*  518:     */     }
/*  519: 570 */     return (MarcacionReloj)hashMarcaciones.get(FuncionesUtiles.fechaMasCercana(dates, fachaCompare));
/*  520:     */   }
/*  521:     */   
/*  522:     */   private boolean verificarMarcacion(Date marcacionEntrada, Date marcacionSalida, Date marcacion, boolean verificaReingresoAntes)
/*  523:     */   {
/*  524: 574 */     boolean setearMarcacion = true;
/*  525: 575 */     if (!verificaReingresoAntes)
/*  526:     */     {
/*  527: 576 */       if ((marcacionSalida != null) && (marcacion.compareTo(marcacionSalida) > 0)) {
/*  528: 577 */         setearMarcacion = false;
/*  529:     */       }
/*  530: 579 */       if ((marcacionEntrada != null) && (marcacion.compareTo(marcacionEntrada) < 0)) {
/*  531: 580 */         setearMarcacion = false;
/*  532:     */       }
/*  533:     */     }
/*  534: 583 */     return setearMarcacion;
/*  535:     */   }
/*  536:     */   
/*  537:     */   private void setEntradaSalidaAsistenciaOpcional(Asistencia asistencia, boolean soloSalida)
/*  538:     */   {
/*  539: 587 */     if (asistencia.isIndicadorDiaOpcional()) {
/*  540: 588 */       if (soloSalida)
/*  541:     */       {
/*  542: 589 */         asistencia.setSalida(asistencia.getMarcacionSalida());
/*  543:     */       }
/*  544:     */       else
/*  545:     */       {
/*  546: 591 */         asistencia.setEntrada(asistencia.getMarcacionEntrada());
/*  547: 592 */         asistencia.setSalida(asistencia.getMarcacionSalida());
/*  548:     */       }
/*  549:     */     }
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void calcularHorasExtras(int idOrganizacion, Date fecha)
/*  553:     */     throws AS2Exception
/*  554:     */   {}
/*  555:     */   
/*  556:     */   public Asistencia calcularHorasExtras(Asistencia asistencia)
/*  557:     */     throws AS2Exception
/*  558:     */   {
/*  559: 605 */     return calcularHorasExtras(asistencia, true);
/*  560:     */   }
/*  561:     */   
/*  562:     */   public Asistencia calcularHorasExtras(Asistencia asistencia, boolean actualizarTodo)
/*  563:     */     throws AS2Exception
/*  564:     */   {
/*  565: 611 */     validarIngresoHorarioAsistencia(asistencia);
/*  566: 612 */     BigDecimal horas25 = BigDecimal.ZERO;
/*  567: 613 */     BigDecimal horas50 = BigDecimal.ZERO;
/*  568: 614 */     BigDecimal horas100 = BigDecimal.ZERO;
/*  569: 615 */     BigDecimal horas100Feriado = BigDecimal.ZERO;
/*  570: 616 */     BigDecimal horasFalta = BigDecimal.ZERO;
/*  571:     */     
/*  572: 618 */     int idOrganizacion = asistencia.getIdOrganizacion();
/*  573: 619 */     int tiempoMinimoSalidaTemprana = ParametrosSistema.getTiempoMinimoSalidaTemprana(idOrganizacion).intValue();
/*  574: 620 */     int tiempoMinimoEntradaTardia = ParametrosSistema.getTiempoMinimoEntradaTardia(idOrganizacion).intValue();
/*  575: 621 */     int tiempoPagoHorasExtras = ParametrosSistema.getTiempoPagoHorasExtras(idOrganizacion).intValue();
/*  576: 622 */     boolean pagarHorasExtraCumplidoJornada = ParametrosSistema.getPagarHorasExtrasCumplidoJornada(idOrganizacion).booleanValue();
/*  577: 623 */     boolean apruebaHorasExtra100Feriado = ParametrosSistema.isApruebarHorasExtra100Feriados(idOrganizacion).booleanValue();
/*  578: 624 */     Date horaInicioFueraHorarioDespuesOtroDia = null;
/*  579: 625 */     Date horaFinFueraHorarioDespuesOtroDia = null;
/*  580: 626 */     Date fechaActualizacion = asistencia.getFecha();
/*  581: 627 */     if (asistencia.getAsistenciaPadre() != null) {
/*  582: 628 */       fechaActualizacion = asistencia.getFechaFiltro();
/*  583:     */     }
/*  584: 636 */     if (asistencia.isIndicadorDiaOpcional()) {
/*  585: 637 */       asistencia.setIndicadorDiaDescanso(Boolean.valueOf(true));
/*  586:     */     }
/*  587: 640 */     boolean indicadorDiaFestivoDia1 = this.servicioAsistenciaConfiguracion.esDiaFestivo(asistencia.getIdOrganizacion(), fechaActualizacion);
/*  588: 641 */     asistencia.setIndicadorDiaFestivo(indicadorDiaFestivoDia1);
/*  589: 643 */     if (actualizarTodo)
/*  590:     */     {
/*  591: 644 */       List<SubsidioEmpleado> listaSubsidio = this.servicioSubsidioEmpleado.getSubsidioEmpleadoPorFecha(idOrganizacion, fechaActualizacion, null, asistencia
/*  592: 645 */         .getEmpleado());
/*  593: 646 */       asistencia.setSubsidioEmpleado(null);
/*  594: 647 */       asistencia.setHorasSubsidio(Integer.valueOf(0));
/*  595: 648 */       asistencia.setIndicadorSubsidioVespertino(null);
/*  596: 649 */       for (SubsidioEmpleado subsidio : listaSubsidio) {
/*  597: 650 */         if (asistencia.getEmpleado().getId() == subsidio.getEmpleado().getId())
/*  598:     */         {
/*  599: 651 */           asistencia.setSubsidioEmpleado(subsidio);
/*  600: 652 */           asistencia.setHorasSubsidio(subsidio.getTipoSubsidio().getHorasSubsidio());
/*  601: 653 */           asistencia.setIndicadorSubsidioVespertino(subsidio.getTipoSubsidio().getIndicadorSubsidioVespertino());
/*  602:     */         }
/*  603:     */       }
/*  604:     */     }
/*  605: 658 */     if (actualizarTodo)
/*  606:     */     {
/*  607: 660 */       List<Empleado> listaVacaciones = this.servicioVacacion.getEmpleadosConVacacionesPorFecha(idOrganizacion, fechaActualizacion, null, asistencia
/*  608: 661 */         .getEmpleado());
/*  609: 662 */       asistencia.setIndicadorVacacion(false);
/*  610: 663 */       for (Empleado empleado : listaVacaciones) {
/*  611: 664 */         if (asistencia.getEmpleado().getId() == empleado.getId()) {
/*  612: 665 */           asistencia.setIndicadorVacacion(true);
/*  613:     */         }
/*  614:     */       }
/*  615:     */     }
/*  616: 671 */     if (actualizarTodo)
/*  617:     */     {
/*  618: 672 */       List<DetallePermisoEmpleado> listaPermisoEmpleado = this.servicioPermisoEmpleado.getPermisoEmpleadoPorFecha(idOrganizacion, asistencia
/*  619: 673 */         .getFecha(), null, asistencia.getEmpleado());
/*  620: 674 */       asistencia.setHorasPermiso(BigDecimal.ZERO);
/*  621: 675 */       for (DetallePermisoEmpleado permisoDetalle : listaPermisoEmpleado) {
/*  622: 676 */         if ((asistencia.getEmpleado().getId() == permisoDetalle.getPermisoEmpleado().getHistoricoEmpleado().getEmpleado().getId()) && 
/*  623: 677 */           (isDentroRango(asistencia.getEntrada(), asistencia.getSalida(), permisoDetalle.getHoraDesde())))
/*  624:     */         {
/*  625: 678 */           asistencia.setIndicadorPermisoDiaCompleto(permisoDetalle.isIndicadorDiaCompleto());
/*  626: 679 */           asistencia.setHorasPermiso(permisoDetalle.getHoras());
/*  627:     */         }
/*  628:     */       }
/*  629:     */     }
/*  630: 684 */     if (asistencia.getAsistenciaPadre() != null) {
/*  631: 685 */       asistencia.setIndicadorPermisoDiaCompleto(asistencia.getAsistenciaPadre().isIndicadorPermisoDiaCompleto());
/*  632:     */     }
/*  633: 687 */     Boolean consideroExtraHorasAdelanto = asistencia.getIndicadorHorasAdelantoExtra();
/*  634: 688 */     if ((!asistencia.isIndicadorVacacion()) && (asistencia.getEntrada() != null) && (asistencia.getMarcacionEntrada() != null) && 
/*  635: 689 */       (asistencia.getSalida() != null) && (asistencia.getMarcacionSalidaReal() != null) && (
/*  636: 690 */       (asistencia.getSubsidioEmpleado() == null) || (asistencia.getHorasSubsidio().intValue() != 0)))
/*  637:     */     {
/*  638: 692 */       asistencia.setMarcacionEntrada(FuncionesUtiles.copiarFechaManteniendoHoras(asistencia.getEntrada(), asistencia.getMarcacionEntrada()));
/*  639: 693 */       asistencia.setMarcacionSalida(FuncionesUtiles.copiarFechaManteniendoHoras(asistencia.getEntrada(), asistencia.getMarcacionSalida()));
/*  640: 694 */       asistencia.setSalida(FuncionesUtiles.copiarFechaManteniendoHoras(asistencia.getEntrada(), asistencia.getSalida()));
/*  641:     */       
/*  642: 696 */       asistencia.setEntradaReceso(FuncionesUtiles.copiarFechaManteniendoHoras(asistencia.getEntrada(), asistencia.getEntradaReceso()));
/*  643: 697 */       asistencia.setMarcacionEntradaReceso(
/*  644: 698 */         FuncionesUtiles.copiarFechaManteniendoHoras(asistencia.getEntrada(), asistencia.getMarcacionEntradaReceso()));
/*  645: 699 */       asistencia.setMarcacionSalidaReceso(
/*  646: 700 */         FuncionesUtiles.copiarFechaManteniendoHoras(asistencia.getEntrada(), asistencia.getMarcacionSalidaReceso()));
/*  647: 701 */       asistencia.setSalidaReceso(FuncionesUtiles.copiarFechaManteniendoHoras(asistencia.getEntrada(), asistencia.getSalidaReceso()));
/*  648:     */       
/*  649:     */ 
/*  650:     */ 
/*  651: 705 */       Date marcacionSalida = asistencia.getMarcacionSalida();
/*  652: 706 */       if (asistencia.getMarcacionSalidaReal().before(asistencia.getMarcacionEntrada()))
/*  653:     */       {
/*  654: 709 */         horaInicioFueraHorarioDespuesOtroDia = FuncionesUtiles.sumarFechaDiasMeses(FuncionesUtiles.setAtributoFecha(asistencia.getEntrada()), 1);
/*  655:     */         
/*  656: 711 */         horaFinFueraHorarioDespuesOtroDia = FuncionesUtiles.sumarFechaDiasMeses(asistencia.getMarcacionSalidaReal(), 1);
/*  657: 712 */         asistencia.setMarcacionSalida(FuncionesUtiles.setAtributoFecha(asistencia.getEntrada()));
/*  658:     */       }
/*  659: 716 */       Date horaInicioDentroHorario = null;
/*  660: 717 */       Date horaInicioFueraHorarioAntes = null;
/*  661: 718 */       Date horaInicioFueraHorarioDespues = null;
/*  662: 719 */       Date horaFinDentroHorario = null;
/*  663: 720 */       Date horaFinFueraHorarioAntes = null;
/*  664: 721 */       Date horaFinFueraHorarioDespues = null;
/*  665:     */       
/*  666: 723 */       int diferenciaEntrada = FuncionesUtiles.getMinutosEntreFechas(asistencia.getMarcacionEntrada(), asistencia.getEntrada());
/*  667:     */       
/*  668: 725 */       int diferenciaSalida = FuncionesUtiles.getMinutosEntreFechas(asistencia.getSalidaReal(), asistencia.getMarcacionSalidaReal());
/*  669:     */       
/*  670: 727 */       int diferenciaEntradaReceso = 0;
/*  671: 728 */       int diferenciaSalidaReceso = 0;
/*  672: 730 */       if ((asistencia.getEntradaReceso() != null) && (asistencia.getMarcacionEntradaReceso() != null)) {
/*  673: 731 */         diferenciaEntradaReceso = FuncionesUtiles.getMinutosEntreFechas(asistencia.getMarcacionEntradaReceso(), asistencia
/*  674: 732 */           .getEntradaReceso());
/*  675:     */       }
/*  676: 735 */       if ((asistencia.getSalidaReceso() != null) && (asistencia.getMarcacionSalidaReceso() != null)) {
/*  677: 736 */         diferenciaSalidaReceso = FuncionesUtiles.getMinutosEntreFechas(asistencia.getSalidaReceso(), asistencia.getMarcacionSalidaReceso());
/*  678:     */       }
/*  679: 739 */       if (diferenciaEntrada < 0)
/*  680:     */       {
/*  681: 740 */         horaInicioDentroHorario = asistencia.getMarcacionEntrada();
/*  682: 741 */         if (diferenciaEntrada > -1 * tiempoMinimoEntradaTardia) {
/*  683: 742 */           diferenciaEntrada = 0;
/*  684:     */         }
/*  685:     */       }
/*  686:     */       else
/*  687:     */       {
/*  688: 745 */         horaInicioDentroHorario = asistencia.getEntrada();
/*  689: 746 */         horaInicioFueraHorarioAntes = asistencia.getMarcacionEntrada();
/*  690: 747 */         horaFinFueraHorarioAntes = asistencia.getEntrada();
/*  691:     */       }
/*  692: 750 */       if ((diferenciaEntradaReceso < 0) && 
/*  693: 751 */         (diferenciaEntradaReceso > -1 * tiempoMinimoEntradaTardia)) {
/*  694: 752 */         diferenciaEntradaReceso = 0;
/*  695:     */       }
/*  696: 756 */       if (diferenciaSalida < 0)
/*  697:     */       {
/*  698: 757 */         horaFinDentroHorario = asistencia.getMarcacionSalidaReal();
/*  699: 758 */         if (diferenciaSalida > -1 * tiempoMinimoSalidaTemprana) {
/*  700: 759 */           diferenciaSalida = 0;
/*  701:     */         }
/*  702:     */       }
/*  703:     */       else
/*  704:     */       {
/*  705: 762 */         horaFinDentroHorario = asistencia.getSalidaReal();
/*  706: 763 */         Calendar salidaCal = Calendar.getInstance();
/*  707: 764 */         salidaCal.setTime(asistencia.getSalida());
/*  708: 765 */         if ((salidaCal.get(11) == 0) && (salidaCal.get(12) == 0)) {
/*  709: 766 */           horaInicioFueraHorarioDespues = asistencia.getSalidaReal();
/*  710:     */         } else {
/*  711: 768 */           horaInicioFueraHorarioDespues = asistencia.getSalida();
/*  712:     */         }
/*  713: 770 */         horaFinFueraHorarioDespues = asistencia.getMarcacionSalidaReal();
/*  714:     */       }
/*  715: 773 */       if ((diferenciaSalidaReceso < 0) && 
/*  716: 774 */         (diferenciaSalidaReceso > -1 * tiempoMinimoSalidaTemprana)) {
/*  717: 775 */         diferenciaSalidaReceso = 0;
/*  718:     */       }
/*  719: 781 */       BigDecimal tiempoReceso = BigDecimal.ZERO;
/*  720: 782 */       if ((asistencia.getMarcacionEntradaReceso() != null) && (asistencia.getMarcacionSalidaReceso() != null)) {
/*  721: 785 */         tiempoReceso = new BigDecimal((asistencia.getMarcacionEntradaReceso().getTime() - asistencia.getMarcacionSalidaReceso().getTime()) / 1000L / 60L / 60.0D).setScale(2, RoundingMode.HALF_UP);
/*  722:     */       }
/*  723: 789 */       List<HorarioCalculos> listaHorarioCalculos = new ArrayList();
/*  724: 790 */       if ((horaInicioFueraHorarioAntes != null) && (horaFinFueraHorarioAntes != null) && (consideroExtraHorasAdelanto.booleanValue())) {
/*  725: 791 */         listaHorarioCalculos.addAll(dividirPorHoras(horaInicioFueraHorarioAntes, horaFinFueraHorarioAntes, false));
/*  726:     */       }
/*  727: 793 */       if ((horaInicioDentroHorario != null) && (horaFinDentroHorario != null)) {
/*  728: 794 */         listaHorarioCalculos.addAll(dividirPorHoras(horaInicioDentroHorario, horaFinDentroHorario, true));
/*  729:     */       }
/*  730: 796 */       if ((horaInicioFueraHorarioDespues != null) && (horaFinFueraHorarioDespues != null)) {
/*  731: 797 */         listaHorarioCalculos.addAll(dividirPorHoras(horaInicioFueraHorarioDespues, horaFinFueraHorarioDespues, false));
/*  732:     */       }
/*  733: 799 */       if ((horaInicioFueraHorarioDespuesOtroDia != null) && (horaFinFueraHorarioDespuesOtroDia != null)) {
/*  734: 800 */         listaHorarioCalculos.addAll(dividirPorHoras(horaInicioFueraHorarioDespuesOtroDia, horaFinFueraHorarioDespuesOtroDia, false));
/*  735:     */       }
/*  736: 804 */       if ((asistencia.getMarcacionEntradaReingreso1() != null) && (asistencia.getMarcacionSalidaReingreso1() != null)) {
/*  737: 805 */         listaHorarioCalculos.addAll(dividirPorHorasReingresos(asistencia.getMarcacionEntradaReingreso1(), asistencia.getMarcacionSalidaReingreso1()));
/*  738:     */       }
/*  739: 807 */       if ((asistencia.getMarcacionEntradaReingreso2() != null) && (asistencia.getMarcacionSalidaReingreso2() != null)) {
/*  740: 808 */         listaHorarioCalculos.addAll(dividirPorHorasReingresos(asistencia.getMarcacionEntradaReingreso2(), asistencia.getMarcacionSalidaReingreso2()));
/*  741:     */       }
/*  742: 810 */       Boolean indicadorDiaFestivo = Boolean.valueOf(asistencia.isIndicadorDiaFestivo());
/*  743: 811 */       boolean indicadorDiaDescanso = asistencia.getIndicadorDiaDescanso() == null ? false : asistencia.getIndicadorDiaDescanso().booleanValue();
/*  744: 812 */       boolean isDiaComplentario = asistencia.isIndicadorDiaComplementario();
/*  745: 815 */       for (Iterator localIterator2 = listaHorarioCalculos.iterator(); localIterator2.hasNext();)
/*  746:     */       {
/*  747: 815 */         horarioCalculos = (HorarioCalculos)localIterator2.next();
/*  748:     */         
/*  749: 817 */         List<HoraExtra> listaHoraExtra = this.horaExtraDao.buscarHoraExtra(idOrganizacion, null, horarioCalculos.getDesde(), horarioCalculos
/*  750: 818 */           .getHasta(), indicadorDiaFestivo, Boolean.valueOf(horarioCalculos.isIndicadorDentroHorario()), indicadorDiaDescanso, isDiaComplentario);
/*  751: 821 */         for (HoraExtra horaExtra : listaHoraExtra)
/*  752:     */         {
/*  753: 822 */           horarioCalculos.setHoraExtra(horaExtra);
/*  754: 824 */           if (horaExtra != null)
/*  755:     */           {
/*  756: 826 */             if (horaExtra.isIndicadorDentroHorario()) {
/*  757: 827 */               horarioCalculos.setCantidadHoras(horarioCalculos.getCantidadHoras()
/*  758: 828 */                 .subtract(tiempoRecesoHorasExtra(asistencia, horarioCalculos.getDesde(), horarioCalculos.getHasta())));
/*  759:     */             }
/*  760: 833 */             if (horaExtra.getPorCiento().equals(PorCientoHoraExtra.VEINTICINCO)) {
/*  761: 834 */               horas25 = horas25.add(horarioCalculos.getCantidadHoras());
/*  762:     */             }
/*  763: 837 */             if (horaExtra.getPorCiento().equals(PorCientoHoraExtra.CINCUENTA)) {
/*  764: 838 */               horas50 = horas50.add(horarioCalculos.getCantidadHoras());
/*  765:     */             }
/*  766: 840 */             if ((horaExtra.getPorCiento().equals(PorCientoHoraExtra.CIEN)) || (
/*  767: 841 */               (asistencia.getIndicadorDiaDescanso().booleanValue()) && (asistencia.isIndicadorDiaOpcional()))) {
/*  768: 842 */               if (indicadorDiaFestivo.booleanValue()) {
/*  769: 843 */                 horas100Feriado = horas100Feriado.add(horarioCalculos.getCantidadHoras());
/*  770:     */               } else {
/*  771: 845 */                 horas100 = horas100.add(horarioCalculos.getCantidadHoras());
/*  772:     */               }
/*  773:     */             }
/*  774:     */           }
/*  775:     */         }
/*  776:     */       }
/*  777:     */       HorarioCalculos horarioCalculos;
/*  778: 854 */       long diferenciasNegativas = 0L;
/*  779: 855 */       if (diferenciaEntrada < 0)
/*  780:     */       {
/*  781: 856 */         int dif = diferenciaEntrada;
/*  782: 858 */         if ((asistencia.getSubsidioEmpleado() != null) && (!asistencia.getIndicadorSubsidioVespertino().booleanValue())) {
/*  783: 859 */           if (diferenciaEntrada * -1 > asistencia.getHorasSubsidio().intValue() * 60) {
/*  784: 860 */             dif = diferenciaEntrada + asistencia.getHorasSubsidio().intValue() * 60;
/*  785:     */           } else {
/*  786: 862 */             dif = 0;
/*  787:     */           }
/*  788:     */         }
/*  789: 865 */         diferenciasNegativas += dif;
/*  790:     */       }
/*  791: 867 */       if (diferenciaSalida < 0)
/*  792:     */       {
/*  793: 868 */         int dif = diferenciaSalida;
/*  794: 870 */         if ((asistencia.getSubsidioEmpleado() != null) && (asistencia.getIndicadorSubsidioVespertino().booleanValue())) {
/*  795: 871 */           if (diferenciaSalida * -1 > asistencia.getHorasSubsidio().intValue() * 60) {
/*  796: 872 */             dif = diferenciaSalida + asistencia.getHorasSubsidio().intValue() * 60;
/*  797:     */           } else {
/*  798: 874 */             dif = 0;
/*  799:     */           }
/*  800:     */         }
/*  801: 877 */         diferenciasNegativas += dif;
/*  802:     */       }
/*  803: 879 */       if (diferenciaSalidaReceso + diferenciaEntradaReceso < 0) {
/*  804: 880 */         diferenciasNegativas += diferenciaSalidaReceso + diferenciaEntradaReceso;
/*  805:     */       }
/*  806: 882 */       horasFalta = new BigDecimal(diferenciasNegativas);
/*  807: 883 */       BigDecimal minutoAgregar = BigDecimal.ZERO;
/*  808: 884 */       if (horasFalta.compareTo(BigDecimal.ZERO) != 0) {}
/*  809: 887 */       horasFalta = horasFalta.multiply(new BigDecimal(-1)).add(minutoAgregar).divide(new BigDecimal(60.0D), 2, RoundingMode.HALF_UP);
/*  810:     */       
/*  811: 889 */       asistencia.setMarcacionSalida(marcacionSalida);
/*  812:     */     }
/*  813: 891 */     if (horas100.compareTo(new BigDecimal(tiempoPagoHorasExtras / 60.0D).setScale(2, RoundingMode.HALF_UP)) < 0) {
/*  814: 892 */       horas100 = BigDecimal.ZERO;
/*  815:     */     }
/*  816: 897 */     if ((horaInicioFueraHorarioDespuesOtroDia == null) && (horaFinFueraHorarioDespuesOtroDia == null) && 
/*  817: 898 */       (horas50.compareTo(new BigDecimal(tiempoPagoHorasExtras / 60.0D).setScale(2, RoundingMode.HALF_UP)) < 0)) {
/*  818: 899 */       horas50 = BigDecimal.ZERO;
/*  819:     */     }
/*  820: 902 */     BigDecimal auxHorasFalta = horasFalta;
/*  821: 904 */     if ((pagarHorasExtraCumplidoJornada) && (!asistencia.getIndicadorDiaDescanso().booleanValue()) && (!asistencia.isIndicadorDiaFestivo()))
/*  822:     */     {
/*  823: 905 */       if (horasFalta.compareTo(horas50) > 0)
/*  824:     */       {
/*  825: 906 */         auxHorasFalta = horasFalta.subtract(horas50);
/*  826: 907 */         horas50 = BigDecimal.ZERO;
/*  827:     */       }
/*  828:     */       else
/*  829:     */       {
/*  830: 910 */         horas50 = horas50.subtract(horasFalta);
/*  831: 911 */         auxHorasFalta = BigDecimal.ZERO;
/*  832:     */       }
/*  833: 913 */       if (horas50.compareTo(BigDecimal.ZERO) == 0) {
/*  834: 914 */         if ((auxHorasFalta.compareTo(BigDecimal.ZERO) > 0) && (auxHorasFalta.compareTo(horas100) > 0)) {
/*  835: 915 */           horas100 = BigDecimal.ZERO;
/*  836: 916 */         } else if ((auxHorasFalta.compareTo(BigDecimal.ZERO) > 0) && (auxHorasFalta.compareTo(horas100) <= 0)) {
/*  837: 917 */           horas100 = horas100.subtract(auxHorasFalta);
/*  838:     */         }
/*  839:     */       }
/*  840:     */     }
/*  841: 923 */     asistencia.setHorasExtras100(horas100);
/*  842: 924 */     asistencia.setHorasExtras100Feriado(horas100Feriado);
/*  843: 925 */     if (!apruebaHorasExtra100Feriado) {
/*  844: 926 */       asistencia.setHorasExtras100FeriadoAprobadas(horas100Feriado);
/*  845:     */     }
/*  846: 928 */     asistencia.setHorasExtras50(horas50);
/*  847: 932 */     if (asistencia.isIndicadorPagoHorasSuplementarias())
/*  848:     */     {
/*  849: 933 */       horas25 = FuncionesUtiles.redondearBigDecimal(horas25);
/*  850: 934 */       asistencia.setHorasExtras25(horas25);
/*  851:     */       
/*  852: 936 */       asistencia.setHorasExtras25Aprobadas(horas25);
/*  853:     */     }
/*  854: 939 */     if ((asistencia.getMarcacionEntrada() == null) && (asistencia.getMarcacionSalidaReal() == null) && (!asistencia.isIndicadorDiaOpcional()))
/*  855:     */     {
/*  856: 940 */       Date horaCero = FuncionesUtiles.stringToDate("00:00", "HH:mm");
/*  857: 941 */       Date horaFinDia = FuncionesUtiles.stringToDate("23:59", "HH:mm");
/*  858: 942 */       BigDecimal redondeo = BigDecimal.ZERO;
/*  859: 943 */       Date horaSalida = asistencia.getSalida();
/*  860: 944 */       if (horaCero.equals(asistencia.getSalida()))
/*  861:     */       {
/*  862: 945 */         horaSalida = horaFinDia;
/*  863: 946 */         redondeo = new BigDecimal(0.02D);
/*  864:     */       }
/*  865: 948 */       horasFalta = horasFalta.add(new BigDecimal(FuncionesUtiles.getMinutosEntreFechas(asistencia.getEntrada(), horaSalida) / 60.0D)
/*  866: 949 */         .setScale(2, RoundingMode.HALF_UP));
/*  867: 950 */       if ((asistencia.getSalidaReceso() != null) && (asistencia.getEntradaReceso() != null))
/*  868:     */       {
/*  869: 951 */         horasFalta = new BigDecimal(FuncionesUtiles.getMinutosEntreFechas(asistencia.getEntrada(), asistencia.getSalidaReceso()) / 60.0D);
/*  870: 952 */         horasFalta = horasFalta.add(new BigDecimal(FuncionesUtiles.getMinutosEntreFechas(asistencia.getEntradaReceso(), horaSalida) / 60.0D)
/*  871: 953 */           .setScale(2, RoundingMode.HALF_UP));
/*  872:     */       }
/*  873: 955 */       horasFalta = horasFalta.add(redondeo);
/*  874:     */     }
/*  875: 957 */     horasFalta = horasFalta.subtract(asistencia.getHorasPermiso());
/*  876: 958 */     if ((asistencia.getHorasSubsidio() != null) && 
/*  877: 959 */       (asistencia.getHorasSubsidio().intValue() != 0)) {
/*  878: 960 */       horasFalta = horasFalta.subtract(new BigDecimal(asistencia.getHorasSubsidio().intValue()));
/*  879:     */     }
/*  880: 963 */     if (((asistencia.getSubsidioEmpleado() != null) && (asistencia.getHorasSubsidio() != null) && (asistencia.getHorasSubsidio().intValue() == 0)) || 
/*  881: 964 */       (horasFalta.compareTo(BigDecimal.ZERO) < 0) || (asistencia.isIndicadorVacacion()) || (asistencia.isIndicadorDiaFestivo()) || 
/*  882: 965 */       (asistencia.isIndicadorPermisoDiaCompleto())) {
/*  883: 966 */       horasFalta = BigDecimal.ZERO;
/*  884:     */     }
/*  885: 968 */     asistencia.setHorasFalta(FuncionesUtiles.redondearBigDecimal(horasFalta));
/*  886: 969 */     return asistencia;
/*  887:     */   }
/*  888:     */   
/*  889:     */   private List<HorarioCalculos> dividirPorHorasReingresos(Date desde, Date hasta)
/*  890:     */   {
/*  891: 980 */     List<HorarioCalculos> lista = new ArrayList();
/*  892: 981 */     if (hasta.after(desde)) {
/*  893: 982 */       return dividirPorHoras(desde, hasta, false);
/*  894:     */     }
/*  895: 984 */     Date horaCero = FuncionesUtiles.stringToDate("00:00", "HH:mm");
/*  896: 985 */     Date horaFinDia = FuncionesUtiles.stringToDate("23:59", "HH:mm");
/*  897: 986 */     lista.addAll(dividirPorHoras(desde, horaFinDia, false));
/*  898: 987 */     lista.addAll(dividirPorHoras(horaCero, hasta, false));
/*  899: 988 */     return lista;
/*  900:     */   }
/*  901:     */   
/*  902:     */   private boolean isDentroRango(Date horaDesde, Date horaHasta, Date horaCompare)
/*  903:     */   {
/*  904: 993 */     Date horaCero = FuncionesUtiles.stringToDate("00:00", "HH:mm");
/*  905: 994 */     if (horaCero.equals(horaHasta)) {
/*  906: 995 */       horaHasta = FuncionesUtiles.stringToDate("23:59", "HH:mm");
/*  907:     */     }
/*  908: 998 */     if ((horaCompare.compareTo(horaDesde) >= 0) && (horaCompare.compareTo(horaHasta) <= 0)) {
/*  909: 999 */       return true;
/*  910:     */     }
/*  911:1001 */     return false;
/*  912:     */   }
/*  913:     */   
/*  914:     */   private BigDecimal tiempoRecesoHorasExtra(Asistencia asistencia, Date horaDesde, Date horaHasta)
/*  915:     */   {
/*  916:1017 */     BigDecimal result = BigDecimal.ZERO;
/*  917:1018 */     Boolean elegirHorarioPlanificado = elegirHorarioPlanificado(asistencia);
/*  918:1019 */     if (elegirHorarioPlanificado != null)
/*  919:     */     {
/*  920:1020 */       Date horaCero = FuncionesUtiles.stringToDate("00:00", "HH:mm");
/*  921:1021 */       Date horaFinDia = FuncionesUtiles.stringToDate("23:59", "HH:mm");
/*  922:1022 */       Date marcacionSalidaReceso = elegirHorarioPlanificado.booleanValue() ? asistencia.getSalidaReceso() : asistencia.getMarcacionSalidaReceso();
/*  923:1023 */       Date marcacionEntradaReceso = elegirHorarioPlanificado.booleanValue() ? asistencia.getEntradaReceso() : asistencia.getMarcacionEntradaReceso();
/*  924:1024 */       if (marcacionEntradaReceso.equals(horaCero))
/*  925:     */       {
/*  926:1025 */         marcacionEntradaReceso = horaFinDia;
/*  927:     */       }
/*  928:     */       else
/*  929:     */       {
/*  930:1027 */         Calendar nuevoFin = Calendar.getInstance();
/*  931:1028 */         nuevoFin.setTime(marcacionEntradaReceso);
/*  932:1029 */         if (nuevoFin.get(12) == 0)
/*  933:     */         {
/*  934:1030 */           nuevoFin.set(12, 59);
/*  935:1031 */           nuevoFin.add(10, -1);
/*  936:     */         }
/*  937:1033 */         marcacionEntradaReceso = nuevoFin.getTime();
/*  938:     */       }
/*  939:1035 */       if ((marcacionSalidaReceso.compareTo(horaDesde) >= 0) && (marcacionSalidaReceso.compareTo(horaHasta) <= 0))
/*  940:     */       {
/*  941:1036 */         if ((marcacionEntradaReceso.compareTo(horaDesde) >= 0) && (marcacionEntradaReceso.compareTo(horaHasta) <= 0)) {
/*  942:1038 */           result = new BigDecimal(FuncionesUtiles.getMinutosEntreFechas(marcacionSalidaReceso, marcacionEntradaReceso) / 60.0D).setScale(2, RoundingMode.HALF_UP);
/*  943:     */         } else {
/*  944:1040 */           result = new BigDecimal(FuncionesUtiles.getMinutosEntreFechas(marcacionSalidaReceso, horaHasta) / 60.0D).setScale(2, RoundingMode.HALF_UP);
/*  945:     */         }
/*  946:     */       }
/*  947:1043 */       else if ((marcacionEntradaReceso.compareTo(horaDesde) >= 0) && (marcacionEntradaReceso.compareTo(horaHasta) <= 0)) {
/*  948:1044 */         result = new BigDecimal((FuncionesUtiles.getMinutosEntreFechas(horaDesde, marcacionEntradaReceso) + 1) / 60.0D).setScale(2, RoundingMode.HALF_UP);
/*  949:     */       }
/*  950:     */     }
/*  951:1049 */     return result;
/*  952:     */   }
/*  953:     */   
/*  954:     */   private Boolean elegirHorarioPlanificado(Asistencia asistencia)
/*  955:     */   {
/*  956:1053 */     BigDecimal recesoPlanificado = null;
/*  957:1054 */     BigDecimal recesoUtilizado = null;
/*  958:1055 */     if ((asistencia.getSalidaReceso() != null) && (asistencia.getEntradaReceso() != null)) {
/*  959:1056 */       recesoPlanificado = FuncionesUtiles.diferenciasDeHoras(asistencia.getSalidaReceso(), asistencia.getEntradaReceso());
/*  960:     */     }
/*  961:1058 */     if ((asistencia.getMarcacionSalidaReceso() != null) && (asistencia.getMarcacionEntradaReceso() != null)) {
/*  962:1059 */       recesoUtilizado = FuncionesUtiles.diferenciasDeHoras(asistencia.getMarcacionSalidaReceso(), asistencia.getMarcacionEntradaReceso());
/*  963:     */     }
/*  964:1061 */     if ((recesoPlanificado != null) && (recesoUtilizado != null))
/*  965:     */     {
/*  966:1062 */       if (recesoPlanificado.compareTo(recesoUtilizado) == 1) {
/*  967:1063 */         return Boolean.valueOf(true);
/*  968:     */       }
/*  969:1065 */       return Boolean.valueOf(false);
/*  970:     */     }
/*  971:1067 */     if (recesoPlanificado != null) {
/*  972:1068 */       return Boolean.valueOf(true);
/*  973:     */     }
/*  974:1069 */     if (recesoUtilizado != null) {
/*  975:1070 */       return Boolean.valueOf(false);
/*  976:     */     }
/*  977:1072 */     return null;
/*  978:     */   }
/*  979:     */   
/*  980:     */   private void asignarHorarioReceso(Asistencia asistencia, Turno turno)
/*  981:     */   {
/*  982:1076 */     Date horaCero = FuncionesUtiles.stringToDate("00:00", "HH:mm");
/*  983:     */     
/*  984:1078 */     String formatoFecha = new SimpleDateFormat("HH:mm:ss").format(asistencia.getSalida());
/*  985:     */     Date compareDate;
/*  986:     */     Date compareDate;
/*  987:1079 */     if (horaCero.equals(FuncionesUtiles.stringToDate(formatoFecha, "HH:mm"))) {
/*  988:1080 */       compareDate = FuncionesUtiles.stringToDate("23:59", "HH:mm");
/*  989:     */     } else {
/*  990:1082 */       compareDate = asistencia.getSalida();
/*  991:     */     }
/*  992:1085 */     if ((turno.getHoraRecesoSalida() != null) && (turno.getHoraRecesoSalida().compareTo(asistencia.getEntrada()) >= 0) && 
/*  993:1086 */       (turno.getHoraRecesoSalida().compareTo(compareDate) <= 0))
/*  994:     */     {
/*  995:1087 */       asistencia.setSalidaReceso(turno.getHoraRecesoSalida());
/*  996:1088 */       asistencia.setEntradaReceso(turno.getHoraRecesoEntrada());
/*  997:     */     }
/*  998:     */     else
/*  999:     */     {
/* 1000:1090 */       asistencia.setSalidaReceso(null);
/* 1001:1091 */       asistencia.setEntradaReceso(null);
/* 1002:     */     }
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public void validarIngresoHorarioAsistencia(Asistencia asistencia)
/* 1006:     */     throws AS2Exception
/* 1007:     */   {
/* 1008:1097 */     if ((asistencia.getMarcacionEntrada() != null) && (asistencia.getMarcacionSalidaReal() != null) && (asistencia.getMarcacionSalidaReceso() != null) && 
/* 1009:1098 */       (asistencia.getMarcacionEntradaReceso() != null))
/* 1010:     */     {
/* 1011:1100 */       if (asistencia.getMarcacionSalidaReceso().before(asistencia.getMarcacionEntrada())) {
/* 1012:1106 */         throw new AS2Exception("com.asinfo.as2.nomina.asistencia.configuracion.impl.ServicioAsistenciaImpl.MARCACION_FUERA_DEL_RANGO_VALIDO", new String[] { "" });
/* 1013:     */       }
/* 1014:1108 */       if ((asistencia.getMarcacionEntradaReceso().before(asistencia.getMarcacionEntrada())) && 
/* 1015:1109 */         (asistencia.getMarcacionEntradaReceso().after(asistencia.getMarcacionSalidaReal()))) {
/* 1016:1110 */         throw new AS2Exception("com.asinfo.as2.nomina.asistencia.configuracion.impl.ServicioAsistenciaImpl.MARCACION_FUERA_DEL_RANGO_VALIDO", new String[] { "" });
/* 1017:     */       }
/* 1018:1112 */       if ((asistencia.getMarcacionSalidaReal().after(asistencia.getMarcacionEntrada())) && 
/* 1019:1113 */         (asistencia.getMarcacionEntradaReceso().before(asistencia.getMarcacionSalidaReceso()))) {
/* 1020:1114 */         throw new AS2Exception("com.asinfo.as2.nomina.asistencia.configuracion.impl.ServicioAsistenciaImpl.MARCACION_FUERA_DEL_RANGO_VALIDO", new String[] { "" });
/* 1021:     */       }
/* 1022:1116 */       if ((asistencia.getMarcacionSalidaReal().before(asistencia.getMarcacionEntrada())) && 
/* 1023:1117 */         (asistencia.getMarcacionEntradaReceso().before(asistencia.getMarcacionSalidaReceso())) && (
/* 1024:1118 */         (asistencia.getMarcacionEntradaReceso().after(asistencia.getMarcacionEntrada())) || 
/* 1025:1119 */         (asistencia.getMarcacionSalidaReceso().before(asistencia.getMarcacionSalidaReal())))) {
/* 1026:1120 */         throw new AS2Exception("com.asinfo.as2.nomina.asistencia.configuracion.impl.ServicioAsistenciaImpl.MARCACION_FUERA_DEL_RANGO_VALIDO", new String[] { "" });
/* 1027:     */       }
/* 1028:     */     }
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public void generarHorasExtrasPagoRol(int idOrganizacion, PagoRol pagoRol)
/* 1032:     */     throws AS2Exception
/* 1033:     */   {}
/* 1034:     */   
/* 1035:     */   public void generarNovedades(int idOrganizacion, Date fecha)
/* 1036:     */     throws AS2Exception
/* 1037:     */   {}
/* 1038:     */   
/* 1039:     */   public static Turno getTurnoDiaSemana(HorarioEmpleado horario, int diaSemana)
/* 1040:     */   {
/* 1041:1139 */     Turno turno = null;
/* 1042:1140 */     switch (diaSemana)
/* 1043:     */     {
/* 1044:     */     case 0: 
/* 1045:1142 */       turno = horario.getTurno0();
/* 1046:1143 */       turno.setIndicadorDiaComplementario(horario.getIndicadorHorarioComplementario0().booleanValue());
/* 1047:1144 */       break;
/* 1048:     */     case 1: 
/* 1049:1147 */       turno = horario.getTurno1();
/* 1050:1148 */       turno.setIndicadorDiaComplementario(horario.getIndicadorHorarioComplementario1().booleanValue());
/* 1051:1149 */       break;
/* 1052:     */     case 2: 
/* 1053:1152 */       turno = horario.getTurno2();
/* 1054:1153 */       turno.setIndicadorDiaComplementario(horario.getIndicadorHorarioComplementario2().booleanValue());
/* 1055:1154 */       break;
/* 1056:     */     case 3: 
/* 1057:1157 */       turno = horario.getTurno3();
/* 1058:1158 */       turno.setIndicadorDiaComplementario(horario.getIndicadorHorarioComplementario3().booleanValue());
/* 1059:1159 */       break;
/* 1060:     */     case 4: 
/* 1061:1162 */       turno = horario.getTurno4();
/* 1062:1163 */       turno.setIndicadorDiaComplementario(horario.getIndicadorHorarioComplementario4().booleanValue());
/* 1063:1164 */       break;
/* 1064:     */     case 5: 
/* 1065:1167 */       turno = horario.getTurno5();
/* 1066:1168 */       turno.setIndicadorDiaComplementario(horario.getIndicadorHorarioComplementario5().booleanValue());
/* 1067:1169 */       break;
/* 1068:     */     case 6: 
/* 1069:1172 */       turno = horario.getTurno6();
/* 1070:1173 */       turno.setIndicadorDiaComplementario(horario.getIndicadorHorarioComplementario6().booleanValue());
/* 1071:1174 */       break;
/* 1072:     */     }
/* 1073:1180 */     return turno;
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public static Boolean getIndicadorDiaDescansoUOpcionalDiaSemana(HorarioEmpleado horario, int diaSemana, boolean obtenerIndicadorDiaDescanso)
/* 1077:     */   {
/* 1078:1184 */     Boolean indicador = Boolean.valueOf(false);
/* 1079:1185 */     switch (diaSemana)
/* 1080:     */     {
/* 1081:     */     case 0: 
/* 1082:1187 */       if (obtenerIndicadorDiaDescanso) {
/* 1083:1188 */         indicador = horario.getIndicadorDiaDescanso0();
/* 1084:     */       } else {
/* 1085:1190 */         indicador = horario.getIndicadorDiaOpcional0();
/* 1086:     */       }
/* 1087:1192 */       break;
/* 1088:     */     case 1: 
/* 1089:1195 */       if (obtenerIndicadorDiaDescanso) {
/* 1090:1196 */         indicador = horario.getIndicadorDiaDescanso1();
/* 1091:     */       } else {
/* 1092:1198 */         indicador = horario.getIndicadorDiaOpcional1();
/* 1093:     */       }
/* 1094:1200 */       break;
/* 1095:     */     case 2: 
/* 1096:1203 */       if (obtenerIndicadorDiaDescanso) {
/* 1097:1204 */         indicador = horario.getIndicadorDiaDescanso2();
/* 1098:     */       } else {
/* 1099:1206 */         indicador = horario.getIndicadorDiaOpcional2();
/* 1100:     */       }
/* 1101:1208 */       break;
/* 1102:     */     case 3: 
/* 1103:1211 */       if (obtenerIndicadorDiaDescanso) {
/* 1104:1212 */         indicador = horario.getIndicadorDiaDescanso3();
/* 1105:     */       } else {
/* 1106:1214 */         indicador = horario.getIndicadorDiaOpcional3();
/* 1107:     */       }
/* 1108:1216 */       break;
/* 1109:     */     case 4: 
/* 1110:1219 */       if (obtenerIndicadorDiaDescanso) {
/* 1111:1220 */         indicador = horario.getIndicadorDiaDescanso4();
/* 1112:     */       } else {
/* 1113:1222 */         indicador = horario.getIndicadorDiaOpcional4();
/* 1114:     */       }
/* 1115:1224 */       break;
/* 1116:     */     case 5: 
/* 1117:1227 */       if (obtenerIndicadorDiaDescanso) {
/* 1118:1228 */         indicador = horario.getIndicadorDiaDescanso5();
/* 1119:     */       } else {
/* 1120:1230 */         indicador = horario.getIndicadorDiaOpcional5();
/* 1121:     */       }
/* 1122:1232 */       break;
/* 1123:     */     case 6: 
/* 1124:1235 */       if (obtenerIndicadorDiaDescanso) {
/* 1125:1236 */         indicador = horario.getIndicadorDiaDescanso6();
/* 1126:     */       } else {
/* 1127:1238 */         indicador = horario.getIndicadorDiaOpcional6();
/* 1128:     */       }
/* 1129:1240 */       break;
/* 1130:     */     }
/* 1131:1246 */     return indicador;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public List<Asistencia> obtenerListaPorPagina(int startIndex, int pageSize, Map<String, String> filters, Date fechaDesde, Date fechaHasta)
/* 1135:     */   {
/* 1136:1251 */     return this.asistenciaDao.obtenerListaPorPagina(startIndex, pageSize, filters, fechaDesde, fechaHasta);
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public int contarPorCriterio(Map<String, String> filters, Date fechaDesde, Date fechaHasta)
/* 1140:     */   {
/* 1141:1256 */     return this.asistenciaDao.contarPorCriterio(filters, fechaDesde, fechaHasta);
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public void guardar(Asistencia asistencia)
/* 1145:     */     throws AS2Exception
/* 1146:     */   {
/* 1147:1261 */     boolean nuevo = asistencia.getId() == 0;
/* 1148:1262 */     if (!nuevo) {
/* 1149:1263 */       asistencia = calcularHorasExtras(asistencia);
/* 1150:     */     }
/* 1151:1266 */     super.guardar(asistencia);
/* 1152:1268 */     if (nuevo)
/* 1153:     */     {
/* 1154:1269 */       asistencia = calcularHorasExtras(asistencia);
/* 1155:1270 */       super.guardar(asistencia);
/* 1156:     */     }
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public Asistencia cargarDetalle(int idAsistencia)
/* 1160:     */   {
/* 1161:1277 */     return this.asistenciaDao.cargarDetalle(idAsistencia);
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   private List<HorarioCalculos> dividirPorHoras(Date desde, Date hasta, boolean indicadorDentroHorario)
/* 1165:     */   {
/* 1166:1281 */     List<HorarioCalculos> lista = new ArrayList();
/* 1167:1282 */     if (hasta.getTime() - desde.getTime() == 0L) {
/* 1168:1283 */       return lista;
/* 1169:     */     }
/* 1170:1286 */     Calendar desdeCal = Calendar.getInstance();
/* 1171:1287 */     desdeCal.setTime(desde);
/* 1172:1288 */     Calendar hastaCal = Calendar.getInstance();
/* 1173:1289 */     hastaCal.setTime(hasta);
/* 1174:     */     
/* 1175:1291 */     Calendar nuevoInicio = Calendar.getInstance();
/* 1176:1292 */     nuevoInicio.setTime(desde);
/* 1177:1293 */     Calendar nuevoFin = Calendar.getInstance();
/* 1178:1294 */     nuevoFin.setTime(hasta);
/* 1179:1295 */     while (nuevoInicio.before(hastaCal))
/* 1180:     */     {
/* 1181:1296 */       nuevoFin = (Calendar)nuevoInicio.clone();
/* 1182:1297 */       nuevoFin.set(12, 59);
/* 1183:1298 */       if (nuevoFin.after(hastaCal))
/* 1184:     */       {
/* 1185:1299 */         nuevoFin = Calendar.getInstance();
/* 1186:1300 */         nuevoFin.setTime(hasta);
/* 1187:1301 */         nuevoFin.add(12, -1);
/* 1188:     */       }
/* 1189:1303 */       long minutos = (nuevoFin.getTimeInMillis() - nuevoInicio.getTimeInMillis()) / 1000L / 60L + 1L;
/* 1190:1304 */       HorarioCalculos hc = new HorarioCalculos(null);
/* 1191:1305 */       hc.setDesde(nuevoInicio.getTime());
/* 1192:1306 */       hc.setHasta(nuevoFin.getTime());
/* 1193:1307 */       hc.setIndicadorDentroHorario(indicadorDentroHorario);
/* 1194:1308 */       hc.setCantidadHoras(new BigDecimal(minutos / 60.0D).setScale(2, RoundingMode.HALF_UP));
/* 1195:1309 */       lista.add(hc);
/* 1196:1310 */       nuevoInicio = (Calendar)nuevoFin.clone();
/* 1197:1311 */       nuevoInicio.add(12, 1);
/* 1198:     */     }
/* 1199:1313 */     return lista;
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   private class HorarioCalculos
/* 1203:     */   {
/* 1204:     */     private Date desde;
/* 1205:     */     private Date hasta;
/* 1206:     */     private boolean indicadorDentroHorario;
/* 1207:1321 */     private BigDecimal cantidadHoras = BigDecimal.ZERO;
/* 1208:     */     private HoraExtra horaExtra;
/* 1209:     */     
/* 1210:     */     private HorarioCalculos() {}
/* 1211:     */     
/* 1212:     */     public Date getDesde()
/* 1213:     */     {
/* 1214:1325 */       return this.desde;
/* 1215:     */     }
/* 1216:     */     
/* 1217:     */     public void setDesde(Date desde)
/* 1218:     */     {
/* 1219:1329 */       this.desde = desde;
/* 1220:     */     }
/* 1221:     */     
/* 1222:     */     public Date getHasta()
/* 1223:     */     {
/* 1224:1333 */       return this.hasta;
/* 1225:     */     }
/* 1226:     */     
/* 1227:     */     public void setHasta(Date hasta)
/* 1228:     */     {
/* 1229:1337 */       this.hasta = hasta;
/* 1230:     */     }
/* 1231:     */     
/* 1232:     */     public boolean isIndicadorDentroHorario()
/* 1233:     */     {
/* 1234:1341 */       return this.indicadorDentroHorario;
/* 1235:     */     }
/* 1236:     */     
/* 1237:     */     public void setIndicadorDentroHorario(boolean indicadorDentroHorario)
/* 1238:     */     {
/* 1239:1345 */       this.indicadorDentroHorario = indicadorDentroHorario;
/* 1240:     */     }
/* 1241:     */     
/* 1242:     */     public BigDecimal getCantidadHoras()
/* 1243:     */     {
/* 1244:1349 */       return this.cantidadHoras;
/* 1245:     */     }
/* 1246:     */     
/* 1247:     */     public void setCantidadHoras(BigDecimal cantidadHoras)
/* 1248:     */     {
/* 1249:1353 */       this.cantidadHoras = cantidadHoras;
/* 1250:     */     }
/* 1251:     */     
/* 1252:     */     public HoraExtra getHoraExtra()
/* 1253:     */     {
/* 1254:1357 */       return this.horaExtra;
/* 1255:     */     }
/* 1256:     */     
/* 1257:     */     public void setHoraExtra(HoraExtra horaExtra)
/* 1258:     */     {
/* 1259:1361 */       this.horaExtra = horaExtra;
/* 1260:     */     }
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   public void actualizarFeriados(int idOrganizacion, Date fechaDesde, Date fechaHasta, Departamento departamento)
/* 1264:     */     throws AS2Exception
/* 1265:     */   {
/* 1266:1367 */     Map<String, String> filtros = new HashMap();
/* 1267:1368 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 1268:1369 */     if (departamento != null) {
/* 1269:1370 */       filtros.put("empleado.departamento.idDepartamento", String.valueOf(departamento.getId()));
/* 1270:     */     }
/* 1271:1372 */     List<Asistencia> listaAsistencia = obtenerListaPorPagina(0, 10000, filtros, fechaDesde, fechaHasta);
/* 1272:     */     
/* 1273:1374 */     fechaDesde = FuncionesUtiles.setAtributoFecha(fechaDesde);
/* 1274:1375 */     fechaHasta = FuncionesUtiles.setAtributoFecha(fechaHasta);
/* 1275:1377 */     for (Asistencia asistencia : listaAsistencia)
/* 1276:     */     {
/* 1277:1378 */       calcularHorasExtras(asistencia, false);
/* 1278:1379 */       super.guardar(asistencia);
/* 1279:     */     }
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   public void actualizarVacacion(int idOrganizacion, Date fechaDesde, Date fechaHasta, Departamento departamento)
/* 1283:     */     throws AS2Exception
/* 1284:     */   {
/* 1285:1385 */     Map<String, String> filtros = new HashMap();
/* 1286:1386 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 1287:1387 */     if (departamento != null) {
/* 1288:1388 */       filtros.put("empleado.departamento.idDepartamento", String.valueOf(departamento.getId()));
/* 1289:     */     }
/* 1290:1391 */     List<Asistencia> listaAsistencia = obtenerListaPorPagina(0, 10000, filtros, fechaDesde, fechaHasta);
/* 1291:1392 */     Map<Date, List<Asistencia>> hmAsistenciaPorFecha = new HashMap();
/* 1292:1393 */     for (Asistencia asistencia : listaAsistencia)
/* 1293:     */     {
/* 1294:1394 */       List<Asistencia> list = (List)hmAsistenciaPorFecha.get(asistencia.getFechaFiltro());
/* 1295:1395 */       if (list != null)
/* 1296:     */       {
/* 1297:1396 */         list.add(asistencia);
/* 1298:1397 */         hmAsistenciaPorFecha.put(asistencia.getFechaFiltro(), list);
/* 1299:     */       }
/* 1300:     */       else
/* 1301:     */       {
/* 1302:1399 */         list = new ArrayList();
/* 1303:1400 */         list.add(asistencia);
/* 1304:1401 */         hmAsistenciaPorFecha.put(asistencia.getFechaFiltro(), list);
/* 1305:     */       }
/* 1306:     */     }
/* 1307:1406 */     fechaDesde = FuncionesUtiles.setAtributoFecha(fechaDesde);
/* 1308:1407 */     fechaHasta = FuncionesUtiles.setAtributoFecha(fechaHasta);
/* 1309:     */     
/* 1310:     */ 
/* 1311:     */ 
/* 1312:1411 */     Date fecha = fechaDesde;
/* 1313:1413 */     while (fecha.getTime() <= fechaHasta.getTime())
/* 1314:     */     {
/* 1315:1414 */       List<Asistencia> listAsistenciaBD = (List)hmAsistenciaPorFecha.get(fecha);
/* 1316:     */       Asistencia asistencia;
/* 1317:     */       List<Asistencia> aux;
/* 1318:1415 */       if (listAsistenciaBD != null)
/* 1319:     */       {
/* 1320:1416 */         Object hmAsistencia = new HashMap();
/* 1321:1417 */         for (Iterator localIterator2 = listAsistenciaBD.iterator(); localIterator2.hasNext();)
/* 1322:     */         {
/* 1323:1417 */           asistencia = (Asistencia)localIterator2.next();
/* 1324:1418 */           List<Asistencia> list = (List)((Map)hmAsistencia).get(Integer.valueOf(asistencia.getEmpleado().getId()));
/* 1325:1419 */           if (list != null)
/* 1326:     */           {
/* 1327:1420 */             list.add(asistencia);
/* 1328:1421 */             ((Map)hmAsistencia).put(Integer.valueOf(asistencia.getEmpleado().getId()), list);
/* 1329:     */           }
/* 1330:     */           else
/* 1331:     */           {
/* 1332:1423 */             list = new ArrayList();
/* 1333:1424 */             list.add(asistencia);
/* 1334:1425 */             ((Map)hmAsistencia).put(Integer.valueOf(asistencia.getEmpleado().getId()), list);
/* 1335:     */           }
/* 1336:     */         }
/* 1337:1429 */         Object listaVacaciones = this.servicioVacacion.getEmpleadosConVacacionesPorFecha(idOrganizacion, fecha, departamento, null);
/* 1338:1430 */         for (Empleado empleado : (List)listaVacaciones)
/* 1339:     */         {
/* 1340:1431 */           aux = (List)((Map)hmAsistencia).get(Integer.valueOf(empleado.getId()));
/* 1341:1432 */           if (aux != null)
/* 1342:     */           {
/* 1343:1433 */             for (Asistencia asisAux : aux)
/* 1344:     */             {
/* 1345:1434 */               asisAux.setIndicadorVacacion(true);
/* 1346:1435 */               asisAux.setHorasFalta(BigDecimal.ZERO);
/* 1347:1436 */               asisAux.setHorasExtras100(BigDecimal.ZERO);
/* 1348:1437 */               asisAux.setHorasExtras100Aprobadas(BigDecimal.ZERO);
/* 1349:1438 */               asisAux.setHorasExtras25(BigDecimal.ZERO);
/* 1350:1439 */               asisAux.setHorasExtras25Aprobadas(BigDecimal.ZERO);
/* 1351:1440 */               asisAux.setHorasExtras50(BigDecimal.ZERO);
/* 1352:1441 */               asisAux.setHorasExtras50Aprobadas(BigDecimal.ZERO);
/* 1353:1442 */               super.guardar(asisAux);
/* 1354:     */             }
/* 1355:1444 */             ((Map)hmAsistencia).remove(Integer.valueOf(empleado.getId()));
/* 1356:     */           }
/* 1357:     */         }
/* 1358:1447 */         for (List<Asistencia> listAsistencia : ((Map)hmAsistencia).values()) {
/* 1359:1448 */           for (Asistencia asistencia2 : listAsistencia)
/* 1360:     */           {
/* 1361:1449 */             asistencia2.setIndicadorVacacion(false);
/* 1362:1450 */             calcularHorasExtras(asistencia2, false);
/* 1363:1451 */             super.guardar(asistencia2);
/* 1364:     */           }
/* 1365:     */         }
/* 1366:     */       }
/* 1367:1455 */       fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, 1);
/* 1368:     */     }
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   public void actualizarSubsidio(int idOrganizacion, Date fechaDesde, Date fechaHasta, Departamento departamento)
/* 1372:     */     throws AS2Exception
/* 1373:     */   {
/* 1374:1462 */     Map<String, String> filtros = new HashMap();
/* 1375:1463 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 1376:1464 */     if (departamento != null) {
/* 1377:1465 */       filtros.put("empleado.departamento.idDepartamento", String.valueOf(departamento.getId()));
/* 1378:     */     }
/* 1379:1468 */     List<Asistencia> listaAsistencia = obtenerListaPorPagina(0, 10000, filtros, fechaDesde, fechaHasta);
/* 1380:1469 */     Map<Date, List<Asistencia>> hmAsistenciaPorFecha = new HashMap();
/* 1381:1470 */     for (Asistencia asistencia : listaAsistencia)
/* 1382:     */     {
/* 1383:1471 */       List<Asistencia> list = (List)hmAsistenciaPorFecha.get(asistencia.getFechaFiltro());
/* 1384:1472 */       if (list != null)
/* 1385:     */       {
/* 1386:1473 */         list.add(asistencia);
/* 1387:1474 */         hmAsistenciaPorFecha.put(asistencia.getFechaFiltro(), list);
/* 1388:     */       }
/* 1389:     */       else
/* 1390:     */       {
/* 1391:1476 */         list = new ArrayList();
/* 1392:1477 */         list.add(asistencia);
/* 1393:1478 */         hmAsistenciaPorFecha.put(asistencia.getFechaFiltro(), list);
/* 1394:     */       }
/* 1395:     */     }
/* 1396:1483 */     fechaDesde = FuncionesUtiles.setAtributoFecha(fechaDesde);
/* 1397:1484 */     fechaHasta = FuncionesUtiles.setAtributoFecha(fechaHasta);
/* 1398:     */     
/* 1399:     */ 
/* 1400:     */ 
/* 1401:1488 */     Date fecha = fechaDesde;
/* 1402:1490 */     while (fecha.getTime() <= fechaHasta.getTime())
/* 1403:     */     {
/* 1404:1491 */       List<Asistencia> listAsistenciaBD = (List)hmAsistenciaPorFecha.get(fecha);
/* 1405:     */       Asistencia asistencia;
/* 1406:     */       List<Asistencia> listAsistencia;
/* 1407:1492 */       if (listAsistenciaBD != null)
/* 1408:     */       {
/* 1409:1493 */         Object hmAsistencia = new HashMap();
/* 1410:1495 */         for (Iterator localIterator2 = listAsistenciaBD.iterator(); localIterator2.hasNext();)
/* 1411:     */         {
/* 1412:1495 */           asistencia = (Asistencia)localIterator2.next();
/* 1413:1496 */           List<Asistencia> list = (List)((Map)hmAsistencia).get(Integer.valueOf(asistencia.getEmpleado().getId()));
/* 1414:1497 */           if (list != null)
/* 1415:     */           {
/* 1416:1498 */             list.add(asistencia);
/* 1417:1499 */             ((Map)hmAsistencia).put(Integer.valueOf(asistencia.getEmpleado().getId()), list);
/* 1418:     */           }
/* 1419:     */           else
/* 1420:     */           {
/* 1421:1501 */             list = new ArrayList();
/* 1422:1502 */             list.add(asistencia);
/* 1423:1503 */             ((Map)hmAsistencia).put(Integer.valueOf(asistencia.getEmpleado().getId()), list);
/* 1424:     */           }
/* 1425:     */         }
/* 1426:1508 */         Object listaSubsidio = this.servicioSubsidioEmpleado.getSubsidioEmpleadoPorFecha(idOrganizacion, fecha, departamento, null);
/* 1427:1510 */         for (SubsidioEmpleado subsidio : (List)listaSubsidio)
/* 1428:     */         {
/* 1429:1511 */           listAsistencia = (List)((Map)hmAsistencia).get(Integer.valueOf(subsidio.getEmpleado().getId()));
/* 1430:1512 */           if (listAsistencia != null)
/* 1431:     */           {
/* 1432:1513 */             for (Asistencia asistencia : listAsistencia)
/* 1433:     */             {
/* 1434:1514 */               asistencia.setSubsidioEmpleado(subsidio);
/* 1435:1515 */               asistencia.setHorasSubsidio(subsidio.getTipoSubsidio().getHorasSubsidio());
/* 1436:1516 */               asistencia.setIndicadorSubsidioVespertino(subsidio.getTipoSubsidio().getIndicadorSubsidioVespertino());
/* 1437:1517 */               calcularHorasExtras(asistencia, false);
/* 1438:1518 */               super.guardar(asistencia);
/* 1439:     */             }
/* 1440:1520 */             ((Map)hmAsistencia).remove(Integer.valueOf(subsidio.getEmpleado().getId()));
/* 1441:     */           }
/* 1442:     */         }
/* 1443:1523 */         for (List<Asistencia> lAsistencia : ((Map)hmAsistencia).values()) {
/* 1444:1524 */           for (Asistencia asistencia2 : lAsistencia)
/* 1445:     */           {
/* 1446:1525 */             asistencia2.setSubsidioEmpleado(null);
/* 1447:1526 */             asistencia2.setHorasSubsidio(null);
/* 1448:1527 */             asistencia2.setIndicadorSubsidioVespertino(Boolean.valueOf(true));
/* 1449:1528 */             calcularHorasExtras(asistencia2, false);
/* 1450:1529 */             super.guardar(asistencia2);
/* 1451:     */           }
/* 1452:     */         }
/* 1453:     */       }
/* 1454:1533 */       fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, 1);
/* 1455:     */     }
/* 1456:     */   }
/* 1457:     */   
/* 1458:     */   public void actualizarHorasPermiso(int idOrganizacion, Date fechaDesde, Date fechaHasta, Departamento departamento)
/* 1459:     */     throws AS2Exception
/* 1460:     */   {
/* 1461:1539 */     Map<String, String> filtros = new HashMap();
/* 1462:1540 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 1463:1541 */     if (departamento != null) {
/* 1464:1542 */       filtros.put("empleado.departamento.idDepartamento", String.valueOf(departamento.getId()));
/* 1465:     */     }
/* 1466:1544 */     List<Asistencia> listaAsistencia = obtenerListaPorPagina(0, 10000, filtros, fechaDesde, fechaHasta);
/* 1467:1545 */     Map<Date, List<Asistencia>> hmAsistenciaPorFecha = new HashMap();
/* 1468:1546 */     for (Asistencia asistencia : listaAsistencia)
/* 1469:     */     {
/* 1470:1547 */       List<Asistencia> list = (List)hmAsistenciaPorFecha.get(asistencia.getFecha());
/* 1471:1548 */       if (list != null)
/* 1472:     */       {
/* 1473:1549 */         list.add(asistencia);
/* 1474:1550 */         hmAsistenciaPorFecha.put(asistencia.getFecha(), list);
/* 1475:     */       }
/* 1476:     */       else
/* 1477:     */       {
/* 1478:1552 */         list = new ArrayList();
/* 1479:1553 */         list.add(asistencia);
/* 1480:1554 */         hmAsistenciaPorFecha.put(asistencia.getFecha(), list);
/* 1481:     */       }
/* 1482:     */     }
/* 1483:1560 */     fechaDesde = FuncionesUtiles.setAtributoFecha(fechaDesde);
/* 1484:1561 */     fechaHasta = FuncionesUtiles.setAtributoFecha(fechaHasta);
/* 1485:     */     
/* 1486:     */ 
/* 1487:     */ 
/* 1488:     */ 
/* 1489:1566 */     Date fecha = fechaDesde;
/* 1490:1568 */     while (fecha.getTime() <= fechaHasta.getTime())
/* 1491:     */     {
/* 1492:1569 */       List<Asistencia> listAsistenciaBD = (List)hmAsistenciaPorFecha.get(fecha);
/* 1493:     */       Asistencia asistencia;
/* 1494:     */       List<Asistencia> listAsistencia;
/* 1495:1570 */       if (listAsistenciaBD != null)
/* 1496:     */       {
/* 1497:1571 */         Object hmAsistencia = new HashMap();
/* 1498:1572 */         for (Iterator localIterator2 = listAsistenciaBD.iterator(); localIterator2.hasNext();)
/* 1499:     */         {
/* 1500:1572 */           asistencia = (Asistencia)localIterator2.next();
/* 1501:1573 */           List<Asistencia> list = (List)((Map)hmAsistencia).get(Integer.valueOf(asistencia.getEmpleado().getId()));
/* 1502:1574 */           if (list != null)
/* 1503:     */           {
/* 1504:1575 */             list.add(asistencia);
/* 1505:1576 */             ((Map)hmAsistencia).put(Integer.valueOf(asistencia.getEmpleado().getId()), list);
/* 1506:     */           }
/* 1507:     */           else
/* 1508:     */           {
/* 1509:1578 */             list = new ArrayList();
/* 1510:1579 */             list.add(asistencia);
/* 1511:1580 */             ((Map)hmAsistencia).put(Integer.valueOf(asistencia.getEmpleado().getId()), list);
/* 1512:     */           }
/* 1513:     */         }
/* 1514:1584 */         Object listaPermisoEmpleado = this.servicioPermisoEmpleado.getPermisoEmpleadoPorFecha(idOrganizacion, fecha, departamento, null);
/* 1515:1586 */         for (DetallePermisoEmpleado permisoDetalle : (List)listaPermisoEmpleado)
/* 1516:     */         {
/* 1517:1588 */           listAsistencia = (List)((Map)hmAsistencia).get(Integer.valueOf(permisoDetalle.getPermisoEmpleado().getHistoricoEmpleado().getEmpleado().getId()));
/* 1518:1589 */           if (listAsistencia != null)
/* 1519:     */           {
/* 1520:1590 */             for (Asistencia asistencia : listAsistencia) {
/* 1521:1591 */               if (isDentroRango(asistencia.getEntrada(), asistencia.getSalida(), permisoDetalle.getHoraDesde()))
/* 1522:     */               {
/* 1523:1592 */                 asistencia.setHorasPermiso(permisoDetalle.getHoras());
/* 1524:1593 */                 calcularHorasExtras(asistencia, false);
/* 1525:1594 */                 super.guardar(asistencia);
/* 1526:     */               }
/* 1527:     */               else
/* 1528:     */               {
/* 1529:1596 */                 asistencia.setHorasPermiso(BigDecimal.ZERO);
/* 1530:1597 */                 calcularHorasExtras(asistencia, false);
/* 1531:1598 */                 super.guardar(asistencia);
/* 1532:     */               }
/* 1533:     */             }
/* 1534:1601 */             ((Map)hmAsistencia).remove(Integer.valueOf(permisoDetalle.getPermisoEmpleado().getHistoricoEmpleado().getEmpleado().getId()));
/* 1535:     */           }
/* 1536:     */         }
/* 1537:1604 */         for (List<Asistencia> lA : ((Map)hmAsistencia).values()) {
/* 1538:1605 */           for (Asistencia as : lA)
/* 1539:     */           {
/* 1540:1606 */             as.setHorasPermiso(BigDecimal.ZERO);
/* 1541:1607 */             calcularHorasExtras(as, false);
/* 1542:1608 */             super.guardar(as);
/* 1543:     */           }
/* 1544:     */         }
/* 1545:     */       }
/* 1546:1612 */       fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, 1);
/* 1547:     */     }
/* 1548:     */   }
/* 1549:     */   
/* 1550:     */   public List<EmpleadoAsistencia> obtenerListaHoraExtraEmpleado(Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 1551:     */   {
/* 1552:1618 */     return this.asistenciaDao.obtenerListaHoraExtraEmpleado(departamento, fechaDesde, fechaHasta, idOrganizacion);
/* 1553:     */   }
/* 1554:     */   
/* 1555:     */   public List<EmpleadoAsistencia> obtenerListaFaltasEmpleado(Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 1556:     */   {
/* 1557:1623 */     return this.asistenciaDao.obtenerListaFaltasEmpleado(departamento, fechaDesde, fechaHasta, idOrganizacion);
/* 1558:     */   }
/* 1559:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.impl.ServicioAsistenciaImpl
 * JD-Core Version:    0.7.0.1
 */