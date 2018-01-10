/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleHistoricoEmpleadoDao;
/*   4:    */ import com.asinfo.as2.dao.EmpleadoDao;
/*   5:    */ import com.asinfo.as2.dao.EmpresaDao;
/*   6:    */ import com.asinfo.as2.dao.HistoricoEmpleadoDao;
/*   7:    */ import com.asinfo.as2.dao.PagoRolEmpleadoRubroDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  10:    */ import com.asinfo.as2.entities.CategoriaRubro;
/*  11:    */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*  12:    */ import com.asinfo.as2.entities.Empleado;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  15:    */ import com.asinfo.as2.entities.Organizacion;
/*  16:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  17:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  18:    */ import com.asinfo.as2.entities.Rubro;
/*  19:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*  20:    */ import com.asinfo.as2.entities.TipoContrato;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  24:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  25:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  26:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  27:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  28:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  29:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  30:    */ import com.asinfo.as2.util.AppUtil;
/*  31:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  32:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Collections;
/*  35:    */ import java.util.Comparator;
/*  36:    */ import java.util.Date;
/*  37:    */ import java.util.HashMap;
/*  38:    */ import java.util.Iterator;
/*  39:    */ import java.util.List;
/*  40:    */ import java.util.Map;
/*  41:    */ import javax.ejb.EJB;
/*  42:    */ import javax.ejb.Stateless;
/*  43:    */ 
/*  44:    */ @Stateless
/*  45:    */ public class ServicioHistoricoEmpleadoImpl
/*  46:    */   implements ServicioHistoricoEmpleado
/*  47:    */ {
/*  48:    */   @EJB
/*  49:    */   private HistoricoEmpleadoDao historicoEmpleadoDao;
/*  50:    */   @EJB
/*  51:    */   private EmpleadoDao empleadoDao;
/*  52:    */   @EJB
/*  53:    */   private ServicioSecuencia servicioSecuencia;
/*  54:    */   @EJB
/*  55:    */   private DetalleHistoricoEmpleadoDao detalleHistoricoEmpleadoDao;
/*  56:    */   @EJB
/*  57:    */   private EmpresaDao empresaDao;
/*  58:    */   @EJB
/*  59:    */   private ServicioDepartamento servicioDepartamento;
/*  60:    */   @EJB
/*  61:    */   private ServicioRubroEmpleado servicioRubroEmpleado;
/*  62:    */   @EJB
/*  63:    */   private ServicioEmpleado servicioEmpleado;
/*  64:    */   @EJB
/*  65:    */   private ServicioRubro servicioRubro;
/*  66:    */   @EJB
/*  67:    */   private transient PagoRolEmpleadoRubroDao pagoRolEmpleadoRubroDao;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  70:    */   
/*  71:    */   public void guardar(HistoricoEmpleado historicoEmpleado)
/*  72:    */     throws ExcepcionAS2
/*  73:    */   {
/*  74: 94 */     validar(historicoEmpleado);
/*  75: 95 */     this.historicoEmpleadoDao.guardar(historicoEmpleado);
/*  76: 96 */     Date fechaSalida = historicoEmpleado.getFechaSalida();
/*  77: 99 */     if (historicoEmpleado.getFechaSalida() == null)
/*  78:    */     {
/*  79:100 */       for (Iterator localIterator = historicoEmpleado.getListaDetalleHistoricoEmpleado().iterator(); localIterator.hasNext();)
/*  80:    */       {
/*  81:100 */         dhe = (DetalleHistoricoEmpleado)localIterator.next();
/*  82:101 */         if (dhe.getHorasSemana() > ParametrosSistema.getHorasSemanaLaboral(AppUtil.getOrganizacion().getId()).intValue()) {
/*  83:102 */           throw new ExcepcionAS2Inventario("msg_error_maximo_horas_semana");
/*  84:    */         }
/*  85:104 */         cargarSecuencia(dhe);
/*  86:105 */         if (dhe.getId() == 0) {
/*  87:106 */           this.servicioSecuencia.actualizarSecuencia(dhe.getTipoContrato().getSecuencia(), dhe.getNumeroContrato());
/*  88:    */         }
/*  89:108 */         this.detalleHistoricoEmpleadoDao.guardar(dhe);
/*  90:    */       }
/*  91:    */       DetalleHistoricoEmpleado dhe;
/*  92:113 */       Object listaRubros = this.servicioEmpleado.getListaRubros(historicoEmpleado.getEmpleado().getIdEmpleado());
/*  93:114 */       if ((listaRubros == null) || ((((List)listaRubros).isEmpty()) && (historicoEmpleado.getEmpleado().getCategoriaRubro() != null))) {
/*  94:115 */         for (Rubro rubro : this.servicioRubro.getListaRubros(historicoEmpleado.getEmpleado().getCategoriaRubro().getId()))
/*  95:    */         {
/*  96:116 */           RubroEmpleado re = new RubroEmpleado();
/*  97:117 */           re.setEmpleado(historicoEmpleado.getEmpleado());
/*  98:118 */           re.setRubro(rubro);
/*  99:119 */           re.setIdOrganizacion(historicoEmpleado.getIdOrganizacion());
/* 100:120 */           re.setIdSucursal(historicoEmpleado.getIdSucursal());
/* 101:121 */           re.setValor(rubro.getValor());
/* 102:122 */           this.servicioRubroEmpleado.guardar(re);
/* 103:    */         }
/* 104:    */       }
/* 105:125 */       historicoEmpleado.getEmpleado().setFechaVencimientoContrato(historicoEmpleado.getFechaVencimientoContrato());
/* 106:126 */       historicoEmpleado.getEmpleado().setActivo(true);
/* 107:    */       
/* 108:128 */       Collections.sort(historicoEmpleado.getListaDetalleHistoricoEmpleado(), new Comparator()
/* 109:    */       {
/* 110:    */         public int compare(DetalleHistoricoEmpleado dhe1, DetalleHistoricoEmpleado dhe2)
/* 111:    */         {
/* 112:132 */           return dhe2.getFechaInicio().compareTo(dhe1.getFechaInicio());
/* 113:    */         }
/* 114:135 */       });
/* 115:136 */       historicoEmpleado.getEmpleado().setTipoContrato(((DetalleHistoricoEmpleado)historicoEmpleado.getListaDetalleHistoricoEmpleado().get(0)).getTipoContrato());
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:139 */       historicoEmpleado = (HistoricoEmpleado)this.historicoEmpleadoDao.cargarDetalle(historicoEmpleado.getIdHistoricoEmpleado());
/* 120:140 */       historicoEmpleado.setFechaSalida(fechaSalida);
/* 121:141 */       historicoEmpleado.setEstadoFiniquito(Estado.SIN_ESTADO);
/* 122:142 */       historicoEmpleado.getEmpleado().getEmpresa().setActivo(false);
/* 123:143 */       historicoEmpleado.getEmpleado().setActivo(false);
/* 124:144 */       this.empresaDao.guardar(historicoEmpleado.getEmpleado().getEmpresa());
/* 125:    */     }
/* 126:149 */     this.empleadoDao.guardar(historicoEmpleado.getEmpleado());
/* 127:    */   }
/* 128:    */   
/* 129:    */   private void cargarSecuencia(DetalleHistoricoEmpleado dhe)
/* 130:    */     throws ExcepcionAS2
/* 131:    */   {
/* 132:153 */     if ((dhe.getNumeroContrato() == null) || (dhe.getNumeroContrato().isEmpty()))
/* 133:    */     {
/* 134:154 */       String numero = this.servicioSecuencia.obtenerSecuencia(dhe.getTipoContrato().getSecuencia(), dhe.getFechaInicio());
/* 135:155 */       dhe.setNumeroContrato(numero);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void eliminar(HistoricoEmpleado historicoEmpleado)
/* 140:    */   {
/* 141:166 */     this.historicoEmpleadoDao.eliminar(historicoEmpleado);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public HistoricoEmpleado buscarPorId(int idHistoricoEmpleado)
/* 145:    */   {
/* 146:177 */     return (HistoricoEmpleado)this.historicoEmpleadoDao.buscarPorId(Integer.valueOf(idHistoricoEmpleado));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<HistoricoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 150:    */   {
/* 151:189 */     return this.historicoEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<HistoricoEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 155:    */   {
/* 156:199 */     return this.historicoEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int contarPorCriterio(Map<String, String> filters)
/* 160:    */   {
/* 161:209 */     return this.historicoEmpleadoDao.contarPorCriterio(filters);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public HistoricoEmpleado obtenerPeriodoActivo(int idEmpleado, Date fechaDesde, Date fechaHasta)
/* 165:    */     throws ExcepcionAS2Nomina
/* 166:    */   {
/* 167:220 */     return this.historicoEmpleadoDao.obtenerPeriodoActivo(idEmpleado, fechaDesde, fechaHasta);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int obtenerAnioAntiguedad(Empleado empleado, Date fechaRol)
/* 171:    */   {
/* 172:230 */     int anioAntiguedad = 0;
/* 173:    */     try
/* 174:    */     {
/* 175:233 */       Date fechaDesde = FuncionesUtiles.getFechaInicioMes(fechaRol);
/* 176:234 */       HistoricoEmpleado historicoEmpleado = this.historicoEmpleadoDao.obtenerPeriodoActivo(empleado.getIdEmpleado(), fechaDesde, fechaRol);
/* 177:    */       
/* 178:236 */       Date fechaIngreso = historicoEmpleado.getFechaIngreso();
/* 179:237 */       anioAntiguedad = FuncionesUtiles.getAnio(fechaRol) - FuncionesUtiles.getAnio(fechaIngreso);
/* 180:238 */       if (FuncionesUtiles.getMes(fechaRol) < FuncionesUtiles.getMes(fechaIngreso)) {
/* 181:239 */         anioAntiguedad--;
/* 182:    */       }
/* 183:    */     }
/* 184:    */     catch (ExcepcionAS2Nomina localExcepcionAS2Nomina) {}
/* 185:246 */     return anioAntiguedad;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public HistoricoEmpleado obtenerPeriodoActivo(int idEmpleado, Date fechaHasta)
/* 189:    */     throws ExcepcionAS2Nomina
/* 190:    */   {
/* 191:251 */     Date fechaInicio = FuncionesUtiles.getFecha(1, FuncionesUtiles.getMes(fechaHasta), FuncionesUtiles.getAnio(fechaHasta));
/* 192:252 */     return this.historicoEmpleadoDao.obtenerPeriodoActivo(idEmpleado, fechaInicio, fechaHasta);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public HistoricoEmpleado cargarDetalle(int idHistoricoEmpleado)
/* 196:    */   {
/* 197:262 */     return cargarDetalle(idHistoricoEmpleado, false);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public HistoricoEmpleado cargarDetalle(int idHistoricoEmpleado, boolean direcciones)
/* 201:    */   {
/* 202:266 */     return this.historicoEmpleadoDao.cargarDetalle(idHistoricoEmpleado, direcciones);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void actualizarAtributoEntidad(HistoricoEmpleado historicoEmpleado, HashMap<String, Object> campos)
/* 206:    */   {
/* 207:277 */     this.historicoEmpleadoDao.actualizarAtributoEntidad(historicoEmpleado, campos);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<HistoricoEmpleado> autocompletarHistoricoEmpleado(String consulta, Map<String, String> filters)
/* 211:    */   {
/* 212:288 */     if (filters == null) {
/* 213:289 */       filters = new HashMap();
/* 214:    */     }
/* 215:291 */     filters.put("empleado.filtro", consulta.trim());
/* 216:292 */     return obtenerListaCombo("", true, filters);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void validar(HistoricoEmpleado historicoEmpleado)
/* 220:    */     throws ExcepcionAS2Nomina
/* 221:    */   {
/* 222:298 */     if (historicoEmpleado.getCausaSalidaEmpleado() == null)
/* 223:    */     {
/* 224:299 */       List<DetalleHistoricoEmpleado> listaDetalle = new ArrayList();
/* 225:300 */       for (Iterator localIterator1 = historicoEmpleado.getListaDetalleHistoricoEmpleado().iterator(); localIterator1.hasNext();)
/* 226:    */       {
/* 227:300 */         detalle = (DetalleHistoricoEmpleado)localIterator1.next();
/* 228:301 */         listaDetalle.add(detalle);
/* 229:    */       }
/* 230:304 */       if (listaDetalle.isEmpty()) {
/* 231:305 */         throw new ExcepcionAS2Nomina("msg_error_detalle_historico_empleado");
/* 232:    */       }
/* 233:309 */       int validador = 0;
/* 234:310 */       for (DetalleHistoricoEmpleado detalle = listaDetalle.iterator(); detalle.hasNext();)
/* 235:    */       {
/* 236:310 */         detalle = (DetalleHistoricoEmpleado)detalle.next();
/* 237:311 */         if (detalle.getFechaFin() == null)
/* 238:    */         {
/* 239:312 */           validador++;
/* 240:314 */           if (validador > 1) {
/* 241:315 */             throw new ExcepcionAS2Nomina("msg_error_historico_empleado");
/* 242:    */           }
/* 243:    */         }
/* 244:    */       }
/* 245:320 */       detalle = (DetalleHistoricoEmpleado[])listaDetalle.toArray(new DetalleHistoricoEmpleado[listaDetalle.size()]);DetalleHistoricoEmpleado detalle = detalle.length;
/* 246:    */       DetalleHistoricoEmpleado fechas1;
/* 247:320 */       for (DetalleHistoricoEmpleado localDetalleHistoricoEmpleado1 = 0; localDetalleHistoricoEmpleado1 < detalle; localDetalleHistoricoEmpleado1++)
/* 248:    */       {
/* 249:320 */         fechas1 = detalle[localDetalleHistoricoEmpleado1];
/* 250:321 */         for (DetalleHistoricoEmpleado fechas2 : listaDetalle)
/* 251:    */         {
/* 252:323 */           if ((fechas2.getFechaFin() != null) && (fechas2.getFechaInicio().compareTo(fechas2.getFechaFin()) > 0)) {
/* 253:324 */             throw new ExcepcionAS2Nomina("msg_error_historico_empleado");
/* 254:    */           }
/* 255:327 */           if (!fechas1.equals(fechas2))
/* 256:    */           {
/* 257:329 */             Date fechaInicio = fechas1.getFechaInicio();
/* 258:331 */             if ((fechaInicio.compareTo(fechas2.getFechaInicio()) >= 0) && (fechaInicio.compareTo(fechas2.getFechaFin()) <= 0)) {
/* 259:332 */               throw new ExcepcionAS2Nomina("msg_error_historico_empleado");
/* 260:    */             }
/* 261:335 */             Date fechaFin = fechas1.getFechaFin() == null ? FuncionesUtiles.obtenerFechaFinal() : fechas1.getFechaFin();
/* 262:336 */             if ((fechaFin.compareTo(fechas2.getFechaInicio()) >= 0) && (fechaFin.compareTo(fechas2.getFechaFin()) <= 0)) {
/* 263:337 */               throw new ExcepcionAS2Nomina("msg_error_historico_empleado");
/* 264:    */             }
/* 265:    */           }
/* 266:    */         }
/* 267:    */       }
/* 268:343 */       if ((((DetalleHistoricoEmpleado)listaDetalle.get(0)).getFechaInicio().compareTo(historicoEmpleado.getFechaIngreso()) > 0) || 
/* 269:344 */         (((DetalleHistoricoEmpleado)listaDetalle.get(0)).getFechaInicio().compareTo(historicoEmpleado.getFechaIngreso()) < 0))
/* 270:    */       {
/* 271:346 */         String mensaje = FuncionesUtiles.dateToString(historicoEmpleado.getFechaIngreso()) + " " + FuncionesUtiles.dateToString(((DetalleHistoricoEmpleado)listaDetalle.get(0)).getFechaInicio());
/* 272:347 */         throw new ExcepcionAS2Nomina("msg_error_historico_empleado_fechas_inicio", mensaje);
/* 273:    */       }
/* 274:350 */       if ((historicoEmpleado.getFechaSalida() == null) && 
/* 275:351 */         (((DetalleHistoricoEmpleado)listaDetalle.get(listaDetalle.size() - 1)).getFechaFin() != null)) {
/* 276:352 */         throw new ExcepcionAS2Nomina("msg_error_historico_empleado");
/* 277:    */       }
/* 278:357 */       if ((((DetalleHistoricoEmpleado)listaDetalle.get(0)).getFechaInicioContratoFijo() != null) && (historicoEmpleado.getFechaIngreso() != null) && 
/* 279:358 */         (((DetalleHistoricoEmpleado)listaDetalle.get(0)).getFechaInicioContratoFijo().compareTo(historicoEmpleado.getFechaIngreso()) == -1)) {
/* 280:359 */         throw new ExcepcionAS2Nomina("msg_info_fecha_contrato_menor_fecha_ingreso", "");
/* 281:    */       }
/* 282:364 */       if ((historicoEmpleado.getFechaVencimientoContrato() != null) && (((DetalleHistoricoEmpleado)listaDetalle.get(0)).getFechaInicioContratoFijo() != null) && 
/* 283:365 */         (historicoEmpleado.getFechaVencimientoContrato().compareTo(((DetalleHistoricoEmpleado)listaDetalle.get(0)).getFechaInicioContratoFijo()) == -1)) {
/* 284:366 */         throw new ExcepcionAS2Nomina("msg_inf_fecha_vencimiento_menor_ingreso_inicio_contrato", "");
/* 285:    */       }
/* 286:370 */       if ((historicoEmpleado.getFechaVencimientoContrato() != null) && (historicoEmpleado.getFechaIngreso() != null) && 
/* 287:371 */         (historicoEmpleado.getFechaVencimientoContrato().compareTo(historicoEmpleado.getFechaIngreso()) == -1)) {
/* 288:372 */         throw new ExcepcionAS2Nomina("msg_inf_fecha_vencimiento_menor_ingreso_inicio_contrato", "");
/* 289:    */       }
/* 290:    */     }
/* 291:377 */     if (historicoEmpleado.getFechaSalida() != null)
/* 292:    */     {
/* 293:378 */       Date ultimaFechaInicio = this.detalleHistoricoEmpleadoDao.getMaximaFechaInicioDetalleHistoricoEmpleado(historicoEmpleado.getId());
/* 294:379 */       if (!FuncionesUtiles.compararFechaAnteriorOIgual(ultimaFechaInicio, historicoEmpleado.getFechaSalida())) {
/* 295:381 */         throw new ExcepcionAS2Nomina("msg_error_historico_empleado_fechas_inicio", FuncionesUtiles.dateToString(ultimaFechaInicio) + " " + FuncionesUtiles.dateToString(historicoEmpleado.getFechaSalida()));
/* 296:    */       }
/* 297:    */     }
/* 298:385 */     if ((ultimaFechaSalida(AppUtil.getOrganizacion().getIdOrganizacion(), historicoEmpleado) != null) && 
/* 299:386 */       (ultimaFechaSalida(AppUtil.getOrganizacion().getIdOrganizacion(), historicoEmpleado).after(historicoEmpleado.getFechaIngreso()))) {
/* 300:387 */       throw new ExcepcionAS2Nomina("msg_error_fecha_ingreso_fecha_salida");
/* 301:    */     }
/* 302:390 */     if ((historicoEmpleado.getFechaSalida() != null) && 
/* 303:391 */       (!FuncionesUtiles.compararFechaAnteriorOIgual(historicoEmpleado.getFechaIngreso(), historicoEmpleado.getFechaSalida()))) {
/* 304:392 */       throw new ExcepcionAS2Nomina("msg_error_fecha_salida");
/* 305:    */     }
/* 306:396 */     if ((historicoEmpleado.getFechaSalida() != null) && (historicoEmpleado.getFechaFiniquito() != null) && 
/* 307:397 */       (!FuncionesUtiles.compararFechaAnteriorOIgual(historicoEmpleado.getFechaSalida(), historicoEmpleado.getFechaFiniquito()))) {
/* 308:398 */       throw new ExcepcionAS2Nomina("msg_error_fecha_finiquito");
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void cargarSecuenciaFiniquito(HistoricoEmpleado historicoEmpleado)
/* 313:    */     throws ExcepcionAS2
/* 314:    */   {
/* 315:406 */     if ((historicoEmpleado.getNumeroFiniquito() != null) && (historicoEmpleado.getNumeroFiniquito().isEmpty())) {}
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Date getMaximaFechaDetalleHistoricoEmpleado(int idHistoricoEmpleado)
/* 319:    */   {
/* 320:422 */     return this.detalleHistoricoEmpleadoDao.getMaximaFechaDetalleHistoricoEmpleado(idHistoricoEmpleado);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<HistoricoEmpleado> verificacion(int idOrganizacion, Empleado empleado)
/* 324:    */   {
/* 325:427 */     return this.historicoEmpleadoDao.verificacion(idOrganizacion, empleado);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public Date ultimaFechaSalida(int idOrganizacion, HistoricoEmpleado historicoEmpleado)
/* 329:    */   {
/* 330:432 */     return this.historicoEmpleadoDao.ultimaFechaSalida(idOrganizacion, historicoEmpleado);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public List<HistoricoEmpleado> historicosParaUtilidad(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 334:    */   {
/* 335:437 */     return this.historicoEmpleadoDao.historicosParaUtilidad(idOrganizacion, fechaDesde, fechaHasta);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void eliminarSalidaEmpleado(HistoricoEmpleado historicoEmpleado)
/* 339:    */   {
/* 340:442 */     historicoEmpleado = cargarDetalle(historicoEmpleado.getId());
/* 341:443 */     if (historicoEmpleado.getFechaSalida() != null)
/* 342:    */     {
/* 343:444 */       for (DetalleHistoricoEmpleado dhe : historicoEmpleado.getListaDetalleHistoricoEmpleado()) {
/* 344:445 */         if ((dhe.getFechaFin() != null) && (dhe.getFechaFin().equals(historicoEmpleado.getFechaSalida()))) {
/* 345:446 */           dhe.setFechaFin(null);
/* 346:    */         }
/* 347:    */       }
/* 348:448 */       historicoEmpleado.setFechaSalida(null);
/* 349:449 */       historicoEmpleado.setCausaSalidaEmpleado(null);
/* 350:450 */       if (historicoEmpleado.getPagoRolEmpleadoFiniquito() != null)
/* 351:    */       {
/* 352:451 */         for (PagoRolEmpleadoRubro prer : historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro())
/* 353:    */         {
/* 354:452 */           prer.setEliminado(true);
/* 355:453 */           this.pagoRolEmpleadoRubroDao.eliminar(prer);
/* 356:    */         }
/* 357:455 */         historicoEmpleado.getPagoRolEmpleadoFiniquito().setEliminado(true);
/* 358:456 */         this.servicioPagoRolEmpleado.eliminar(historicoEmpleado.getPagoRolEmpleadoFiniquito());
/* 359:457 */         historicoEmpleado.setPagoRolEmpleadoFiniquito(null);
/* 360:    */       }
/* 361:460 */       historicoEmpleado.setFechaFiniquito(null);
/* 362:461 */       historicoEmpleado.setEstadoFiniquito(null);
/* 363:462 */       this.historicoEmpleadoDao.guardar(historicoEmpleado);
/* 364:    */     }
/* 365:    */   }
/* 366:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioHistoricoEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */