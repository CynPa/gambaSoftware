/*   1:    */ package com.asinfo.as2.nomina.asistencia.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   5:    */ import com.asinfo.as2.entities.Departamento;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Calendar;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ReporteAsistenciaBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private Date fechaDesde;
/*  32:    */   private Date fechaHasta;
/*  33:    */   private Empleado empleado;
/*  34:    */   private Departamento departamento;
/*  35:    */   
/*  36:    */   static enum OPCIONES
/*  37:    */   {
/*  38: 46 */     HORARIO_PLANIFICADO("Horario planificado"),  SIN_HORARIO("Sin horario"),  HORAS_EXTRA_X_DIA("Horas extras por día"),  HORAS_EXTRA_X_SEMANA("Horas extras por semana"),  CONTROL_ASISTENCIA_SOBRETIEMPOS("Control de Asistencia y Sobretiempos");
/*  39:    */     
/*  40:    */     private String nombre;
/*  41:    */     
/*  42:    */     private OPCIONES(String nombre)
/*  43:    */     {
/*  44: 57 */       this.nombre = nombre;
/*  45:    */     }
/*  46:    */     
/*  47:    */     public String getNombre()
/*  48:    */     {
/*  49: 66 */       return this.nombre;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public void setNombre(String nombre)
/*  53:    */     {
/*  54: 75 */       this.nombre = nombre;
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58: 84 */   private OPCIONES opcion = OPCIONES.HORARIO_PLANIFICADO;
/*  59:    */   @EJB
/*  60:    */   private ServicioReporteAsistencia servicioReporteAsistencia;
/*  61:    */   @EJB
/*  62:    */   private ServicioDepartamento servicioDepartamento;
/*  63:    */   @EJB
/*  64:    */   private ServicioEmpleado servicioEmpleado;
/*  65:    */   private List<Departamento> listaDepartamento;
/*  66:    */   private boolean indicadorAgrupado;
/*  67:    */   private boolean indicadorAsistencia;
/*  68:    */   private boolean indicadorMostrarDiasDescanso;
/*  69:    */   private List<Empleado> listaEmpleado;
/*  70:    */   private Empleado supervisor;
/*  71:    */   private static final long serialVersionUID = 1L;
/*  72:    */   
/*  73:    */   protected JRDataSource getJRDataSource()
/*  74:    */   {
/*  75:107 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  76:108 */     JRDataSource ds = null;
/*  77:    */     try
/*  78:    */     {
/*  79:110 */       if (this.opcion.equals(OPCIONES.HORAS_EXTRA_X_DIA))
/*  80:    */       {
/*  81:112 */         listaDatosReporte = this.servicioReporteAsistencia.getReporteAsistenciaResumido(this.empleado, getDepartamento(), this.fechaDesde, this.fechaHasta, 
/*  82:113 */           AppUtil.getOrganizacion().getIdOrganizacion());
/*  83:    */         
/*  84:115 */         String[] fields = { "f_departamento", "f_empleado", "f_fecha", "f_horasExtras", "f_tipoHoras" };
/*  85:    */         
/*  86:117 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  87:    */       }
/*  88:119 */       else if (this.opcion.equals(OPCIONES.HORAS_EXTRA_X_SEMANA))
/*  89:    */       {
/*  90:120 */         listaDatosReporte = this.servicioReporteAsistencia.getReporteAsistenciaSemanal(this.empleado, getDepartamento(), this.fechaDesde, this.fechaHasta, 
/*  91:121 */           AppUtil.getOrganizacion().getIdOrganizacion());
/*  92:    */         
/*  93:123 */         String[] fields = { "f_departamento", "f_empleado", "f_fechaOrder", "f_horasExtras", "f_tipoHoras", "f_fecha" };
/*  94:    */         
/*  95:125 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  96:    */       }
/*  97:126 */       else if (this.opcion.equals(OPCIONES.CONTROL_ASISTENCIA_SOBRETIEMPOS))
/*  98:    */       {
/*  99:127 */         if (getDepartamento() != null)
/* 100:    */         {
/* 101:128 */           listaDatosReporte = this.servicioReporteAsistencia.getReporteControlAsistenciSobretiempos(getDepartamento(), this.fechaDesde, 
/* 102:129 */             getSupervisor());
/* 103:130 */           String[] fields = { "f_departamento", "f_responsable", "f_empleado", "f_centroCosto" };
/* 104:    */           
/* 105:132 */           ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 106:    */         }
/* 107:    */         else
/* 108:    */         {
/* 109:134 */           addErrorMessage(getLanguageController().getMensaje("msg_error_seleccione_departamento"));
/* 110:    */         }
/* 111:    */       }
/* 112:    */       else
/* 113:    */       {
/* 114:139 */         listaDatosReporte = this.servicioReporteAsistencia.getReporteAsistencia(this.empleado, getDepartamento(), this.fechaDesde, this.fechaHasta, 
/* 115:140 */           AppUtil.getOrganizacion().getIdOrganizacion(), this.opcion.ordinal(), this.indicadorMostrarDiasDescanso);
/* 116:    */         
/* 117:    */ 
/* 118:    */ 
/* 119:144 */         String[] fields = { "f_idAsistenciaPadre", "f_idAsistencia", "f_fecha", "f_entrada", "f_marcacionEntrada", "f_salidaReceso", "f_marcacionSalidaReceso", "f_entradaReceso", "f_marcacionentradaReceso", "f_salida", "f_marcacionSalida", "f_horasFalta", "f_horasPermiso", "f_horasSubsidio", "f_horasExtras25", "f_horasExtras50", "f_horasExtras100", "f_horasExtras100FeriadoAprobadas", "f_horasExtras25Aprobadas", "f_horasExtras50Aprobadas", "f_horasExtras100Aprobadas", "f_nombres", "f_apellidos", "f_departamento", "f_tipoSubsidio", "f_descripcion", "f_diaFestivo", "f_identificacion", "f_horasTrabajadas", "f_lunch", "f_horasExtras100Feriado", "f_marcacionEntradaReingreso1", "f_marcacionSalidaReingreso1", "f_marcacionEntradaReingreso2", "f_marcacionSalidaReingreso2" };
/* 120:    */         
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:152 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 128:    */       }
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:156 */       LOG.info("Error " + e);
/* 133:157 */       e.printStackTrace();
/* 134:    */     }
/* 135:159 */     return ds;
/* 136:    */   }
/* 137:    */   
/* 138:    */   @PostConstruct
/* 139:    */   public void init()
/* 140:    */   {
/* 141:164 */     Calendar calfechaDesde = Calendar.getInstance();
/* 142:165 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 143:166 */     this.fechaDesde = calfechaDesde.getTime();
/* 144:167 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected String getCompileFileName()
/* 148:    */   {
/* 149:173 */     if (this.opcion.equals(OPCIONES.HORAS_EXTRA_X_DIA)) {
/* 150:174 */       return "reporteAsistenciaResumido";
/* 151:    */     }
/* 152:175 */     if (this.opcion.equals(OPCIONES.HORAS_EXTRA_X_SEMANA)) {
/* 153:176 */       return "reporteAsistenciaSemanal";
/* 154:    */     }
/* 155:177 */     if (this.opcion.equals(OPCIONES.CONTROL_ASISTENCIA_SOBRETIEMPOS)) {
/* 156:178 */       return "reporteAsistenciaSobretiempo";
/* 157:    */     }
/* 158:181 */     if (isIndicadorAgrupado()) {
/* 159:182 */       return "reporteAgrupadoAsistencia";
/* 160:    */     }
/* 161:184 */     return "reporteAsistencia";
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected Map<String, Object> getReportParameters()
/* 165:    */   {
/* 166:192 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 167:193 */     reportParameters.put("ReportTitle", "Reporte Asistencia");
/* 168:194 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 169:195 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 170:    */     
/* 171:197 */     reportParameters.put("p_tipo", this.opcion.getNombre());
/* 172:198 */     reportParameters.put("p_departamento", getDepartamento() != null ? getDepartamento().getNombre() : "Todos");
/* 173:199 */     reportParameters.put("p_empleado", getEmpleado() != null ? getEmpleado().getNombreCompleto() : "Todos");
/* 174:200 */     if (this.opcion.equals(OPCIONES.CONTROL_ASISTENCIA_SOBRETIEMPOS))
/* 175:    */     {
/* 176:201 */       reportParameters.put("p_supervisor", getSupervisor() != null ? getSupervisor().getNombreCompleto() : "");
/* 177:202 */       Calendar hastaC = Calendar.getInstance();
/* 178:203 */       hastaC.setFirstDayOfWeek(2);
/* 179:204 */       hastaC.setTime(this.fechaDesde);
/* 180:205 */       hastaC.add(7, 6);
/* 181:206 */       this.fechaHasta = hastaC.getTime();
/* 182:207 */       reportParameters.put("p_semana", Integer.valueOf(hastaC.get(3)));
/* 183:208 */       reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 184:209 */       diasSemana(reportParameters, this.fechaDesde);
/* 185:    */     }
/* 186:211 */     return reportParameters;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String execute()
/* 190:    */   {
/* 191:    */     try
/* 192:    */     {
/* 193:217 */       super.prepareReport();
/* 194:    */     }
/* 195:    */     catch (Exception e)
/* 196:    */     {
/* 197:219 */       e.printStackTrace();
/* 198:    */     }
/* 199:222 */     return null;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String cargarEmpleado()
/* 203:    */   {
/* 204:226 */     return "";
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Empleado getEmpleado()
/* 208:    */   {
/* 209:230 */     return this.empleado;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setEmpleado(Empleado empleado)
/* 213:    */   {
/* 214:234 */     this.empleado = empleado;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Departamento getDepartamento()
/* 218:    */   {
/* 219:239 */     return this.departamento;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setDepartamento(Departamento departamento)
/* 223:    */   {
/* 224:243 */     this.departamento = departamento;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<Departamento> getListaDepartamento()
/* 228:    */   {
/* 229:247 */     if (this.listaDepartamento == null) {
/* 230:248 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 231:    */     }
/* 232:250 */     return this.listaDepartamento;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 236:    */   {
/* 237:254 */     this.listaDepartamento = listaDepartamento;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Date getFechaDesde()
/* 241:    */   {
/* 242:258 */     return this.fechaDesde;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setFechaDesde(Date fechaDesde)
/* 246:    */   {
/* 247:262 */     this.fechaDesde = fechaDesde;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Date getFechaHasta()
/* 251:    */   {
/* 252:266 */     return this.fechaHasta;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setFechaHasta(Date fechaHasta)
/* 256:    */   {
/* 257:270 */     this.fechaHasta = fechaHasta;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean isIndicadorAgrupado()
/* 261:    */   {
/* 262:274 */     return this.indicadorAgrupado;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setIndicadorAgrupado(boolean indicadorAgrupado)
/* 266:    */   {
/* 267:278 */     this.indicadorAgrupado = indicadorAgrupado;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public boolean isIndicadorAsistencia()
/* 271:    */   {
/* 272:282 */     return this.indicadorAsistencia;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setIndicadorAsistencia(boolean indicadorAsistencia)
/* 276:    */   {
/* 277:286 */     this.indicadorAsistencia = indicadorAsistencia;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<SelectItem> getListaOpciones()
/* 281:    */   {
/* 282:290 */     List<SelectItem> lista = new ArrayList();
/* 283:291 */     for (OPCIONES accion : OPCIONES.values()) {
/* 284:292 */       lista.add(new SelectItem(accion, accion.getNombre()));
/* 285:    */     }
/* 286:294 */     return lista;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public OPCIONES getOpcion()
/* 290:    */   {
/* 291:298 */     return this.opcion;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setOpcion(OPCIONES opcion)
/* 295:    */   {
/* 296:302 */     this.opcion = opcion;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void listenerEmpleado()
/* 300:    */   {
/* 301:306 */     if (getDepartamento() != null)
/* 302:    */     {
/* 303:307 */       Departamento d = new Departamento();
/* 304:308 */       if (this.listaEmpleado == null)
/* 305:    */       {
/* 306:309 */         this.listaEmpleado = new ArrayList();
/* 307:310 */         d = this.servicioDepartamento.cargarDetalle(getDepartamento().getId());
/* 308:311 */         if (d.getSupervisor() != null) {
/* 309:312 */           this.listaEmpleado.add(d.getSupervisor());
/* 310:    */         }
/* 311:313 */         if (d.getSupervisor2() != null) {
/* 312:314 */           this.listaEmpleado.add(d.getSupervisor2());
/* 313:    */         }
/* 314:    */       }
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Empleado getSupervisor()
/* 319:    */   {
/* 320:320 */     if (getDepartamento() != null) {
/* 321:321 */       this.supervisor = this.servicioDepartamento.cargarDetalle(getDepartamento().getId()).getSupervisor();
/* 322:    */     }
/* 323:322 */     return this.supervisor;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setSupervisor(Empleado supervisor)
/* 327:    */   {
/* 328:326 */     this.supervisor = supervisor;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public List<Empleado> getListaEmpleado()
/* 332:    */   {
/* 333:330 */     return this.listaEmpleado;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setListaEmpleado(List<Empleado> listaEmpleado)
/* 337:    */   {
/* 338:334 */     this.listaEmpleado = listaEmpleado;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void diasSemana(Map<String, Object> reportParameters, Date fechaDesde)
/* 342:    */   {
/* 343:338 */     Calendar dia = Calendar.getInstance();
/* 344:339 */     dia.setFirstDayOfWeek(2);
/* 345:340 */     dia.setTime(fechaDesde);
/* 346:341 */     int dia1 = dia.get(5);
/* 347:342 */     dia.add(7, 1);
/* 348:343 */     int dia2 = dia.get(5);
/* 349:344 */     dia.add(7, 1);
/* 350:345 */     int dia3 = dia.get(5);
/* 351:346 */     dia.add(7, 1);
/* 352:347 */     int dia4 = dia.get(5);
/* 353:348 */     dia.add(7, 1);
/* 354:349 */     int dia5 = dia.get(5);
/* 355:350 */     dia.add(7, 1);
/* 356:351 */     int dia6 = dia.get(5);
/* 357:352 */     dia.add(7, 1);
/* 358:353 */     int dia7 = dia.get(5);
/* 359:354 */     reportParameters.put("p_dia1", "Lunes " + dia1);
/* 360:355 */     reportParameters.put("p_dia2", "Martes " + dia2);
/* 361:356 */     reportParameters.put("p_dia3", "Miercoles " + dia3);
/* 362:357 */     reportParameters.put("p_dia4", "Jueves " + dia4);
/* 363:358 */     reportParameters.put("p_dia5", "Viernes " + dia5);
/* 364:359 */     reportParameters.put("p_dia6", "Sabado " + dia6);
/* 365:360 */     reportParameters.put("p_dia7", "Domingo " + dia7);
/* 366:    */   }
/* 367:    */   
/* 368:    */   public boolean isIndicadorMostrarDiasDescanso()
/* 369:    */   {
/* 370:364 */     return this.indicadorMostrarDiasDescanso;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setIndicadorMostrarDiasDescanso(boolean indicadorMostrarDiasDescanso)
/* 374:    */   {
/* 375:368 */     this.indicadorMostrarDiasDescanso = indicadorMostrarDiasDescanso;
/* 376:    */   }
/* 377:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.reportes.ReporteAsistenciaBean
 * JD-Core Version:    0.7.0.1
 */