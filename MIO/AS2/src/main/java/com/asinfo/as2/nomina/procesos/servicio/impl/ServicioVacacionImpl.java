/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.VacacionDao;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.DetalleVacacion;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   8:    */ import com.asinfo.as2.entities.Vacacion;
/*   9:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  10:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  11:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Collection;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.Iterator;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import java.util.TreeMap;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class ServicioVacacionImpl
/*  27:    */   implements ServicioVacacion
/*  28:    */ {
/*  29:    */   @EJB
/*  30:    */   private VacacionDao vacacionDao;
/*  31:    */   @EJB
/*  32:    */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  33:    */   
/*  34:    */   public void guardar(Vacacion vacacion)
/*  35:    */   {
/*  36: 57 */     this.vacacionDao.guardar(vacacion);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void eliminar(Vacacion vacacion)
/*  40:    */   {
/*  41: 67 */     this.vacacionDao.eliminar(vacacion);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Vacacion buscarPorId(int idVacacion)
/*  45:    */   {
/*  46: 77 */     return (Vacacion)this.vacacionDao.buscarPorId(Integer.valueOf(idVacacion));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<Vacacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  50:    */   {
/*  51: 87 */     return this.vacacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<Vacacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56: 97 */     return this.vacacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int contarPorCriterio(Map<String, String> filters)
/*  60:    */   {
/*  61:108 */     return this.vacacionDao.contarPorCriterio(filters);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Vacacion cargarDetalle(int idVacacion)
/*  65:    */   {
/*  66:118 */     Vacacion vacacion = new Vacacion();
/*  67:119 */     vacacion = (Vacacion)this.vacacionDao.buscarPorId(Integer.valueOf(idVacacion));
/*  68:120 */     vacacion.getListaDetalleVacacion().size();
/*  69:121 */     return vacacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void generarVacaciones(int idOrganizacion, Empleado empleado, boolean activo)
/*  73:    */   {
/*  74:132 */     Date fechaProceso = FuncionesUtiles.setAtributoFecha(new Date());
/*  75:133 */     int anioDiaAdicional = ParametrosSistema.getAnioDiaAdicionalVacacion(idOrganizacion).intValue();
/*  76:    */     
/*  77:135 */     TreeMap<Integer, TreeMap<Date, Vacacion>> hmlistaVacaciones = new TreeMap();
/*  78:136 */     List<Vacacion> listaVacacion = this.vacacionDao.getVacaciones(idOrganizacion, empleado, activo);
/*  79:139 */     for (Vacacion vacacion : listaVacacion)
/*  80:    */     {
/*  81:141 */       int idHistoricoEmpleado = vacacion.getHistoricoEmpleado().getIdHistoricoEmpleado();
/*  82:    */       
/*  83:143 */       TreeMap<Date, Vacacion> hmVacaciones = (TreeMap)hmlistaVacaciones.get(Integer.valueOf(idHistoricoEmpleado));
/*  84:144 */       if (hmVacaciones == null) {
/*  85:145 */         hmVacaciones = new TreeMap();
/*  86:    */       }
/*  87:147 */       hmVacaciones.put(vacacion.getFechaInicioPeriodo(), vacacion);
/*  88:148 */       hmlistaVacaciones.put(Integer.valueOf(idHistoricoEmpleado), hmVacaciones);
/*  89:    */     }
/*  90:153 */     for (Integer idHistoricoEmpleado : hmlistaVacaciones.keySet())
/*  91:    */     {
/*  92:156 */       TreeMap<Date, Vacacion> hmVacaciones = (TreeMap)hmlistaVacaciones.get(idHistoricoEmpleado);
/*  93:    */       
/*  94:    */ 
/*  95:159 */       List<Date> listaFecha = new ArrayList(hmVacaciones.keySet());
/*  96:160 */       Date fechaMaxima = FuncionesUtiles.obtenerMaximaFechaLista(listaFecha);
/*  97:161 */       Date fechaIngreso = ((Vacacion)hmVacaciones.values().iterator().next()).getFechaIngreso();
/*  98:    */       
/*  99:    */ 
/* 100:164 */       Date fechaInicioPeriodo = fechaMaxima;
/* 101:165 */       Date fechaFinPeriodo = FuncionesUtiles.sumarFechaAnios(fechaInicioPeriodo, 1);
/* 102:    */       
/* 103:167 */       HistoricoEmpleado historicoEmpleado = null;
/* 104:168 */       while (fechaFinPeriodo.compareTo(fechaProceso) <= 0)
/* 105:    */       {
/* 106:170 */         if (((hmVacaciones.size() == 1) && (((Vacacion)hmVacaciones.get(fechaMaxima)).getId() == 0)) || (!hmVacaciones.containsKey(fechaInicioPeriodo)))
/* 107:    */         {
/* 108:172 */           if (historicoEmpleado == null) {
/* 109:173 */             historicoEmpleado = this.servicioHistoricoEmpleado.buscarPorId(idHistoricoEmpleado.intValue());
/* 110:    */           }
/* 111:176 */           Vacacion auxVacacion = new Vacacion();
/* 112:177 */           auxVacacion.setIdOrganizacion(idOrganizacion);
/* 113:178 */           auxVacacion.setIdSucursal(historicoEmpleado.getIdSucursal());
/* 114:179 */           auxVacacion.setHistoricoEmpleado(historicoEmpleado);
/* 115:180 */           auxVacacion.setFechaInicioPeriodo(fechaInicioPeriodo);
/* 116:181 */           auxVacacion.setFechaFinPeriodo(fechaFinPeriodo);
/* 117:182 */           auxVacacion.setDias(15);
/* 118:183 */           auxVacacion.setDiasTomados(0);
/* 119:    */           
/* 120:185 */           int diasAdicionales = 0;
/* 121:186 */           Date fechaDiasAdicionales = FuncionesUtiles.sumarFechaAnios(fechaIngreso, anioDiaAdicional);
/* 122:188 */           while (fechaFinPeriodo.compareTo(fechaDiasAdicionales) >= 0)
/* 123:    */           {
/* 124:189 */             diasAdicionales++;
/* 125:190 */             fechaDiasAdicionales = FuncionesUtiles.sumarFechaAnios(fechaDiasAdicionales, 1);
/* 126:    */           }
/* 127:192 */           auxVacacion.setDiasAdicionales(diasAdicionales > 15 ? 15 : diasAdicionales);
/* 128:    */           
/* 129:194 */           this.vacacionDao.guardar(auxVacacion);
/* 130:195 */           hmVacaciones.put(fechaInicioPeriodo, auxVacacion);
/* 131:    */         }
/* 132:199 */         fechaInicioPeriodo = fechaFinPeriodo;
/* 133:200 */         fechaFinPeriodo = FuncionesUtiles.sumarFechaAnios(fechaFinPeriodo, 1);
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Vacacion buscarVacacionPorEmpleadoId(int idEmpleado)
/* 139:    */   {
/* 140:213 */     return null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Vacacion generarAnticipoVacacion(int idOrganizacion, int idEmpleado)
/* 144:    */     throws ExcepcionAS2Nomina
/* 145:    */   {
/* 146:224 */     int anioDiaAdicional = ParametrosSistema.getAnioDiaAdicionalVacacion(idOrganizacion).intValue();
/* 147:    */     
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:229 */     HistoricoEmpleado historicoEmpleado = this.servicioHistoricoEmpleado.obtenerPeriodoActivo(idEmpleado, new Date());
/* 152:232 */     if (getDiasPendientes(historicoEmpleado.getId()) > 0) {
/* 153:233 */       throw new ExcepcionAS2Nomina("msg_error_anticipo_vacacion_dias_pendientes");
/* 154:    */     }
/* 155:237 */     HashMap<String, String> filters = new HashMap();
/* 156:238 */     filters.put("historicoEmpleado.idHistoricoEmpleado", "" + historicoEmpleado.getId());
/* 157:239 */     List<Vacacion> listaVacaciones = obtenerListaCombo("historicoEmpleado.fechaIngreso", true, filters);
/* 158:    */     Date fechaInicioPeriodo;
/* 159:    */     Date fechaInicioPeriodo;
/* 160:243 */     if (listaVacaciones.isEmpty()) {
/* 161:244 */       fechaInicioPeriodo = historicoEmpleado.getFechaIngreso();
/* 162:    */     } else {
/* 163:247 */       fechaInicioPeriodo = ((Vacacion)listaVacaciones.get(listaVacaciones.size() - 1)).getFechaFinPeriodo();
/* 164:    */     }
/* 165:249 */     Date fechaFinPeriodo = FuncionesUtiles.sumarFechaAnios(fechaInicioPeriodo, 1);
/* 166:    */     
/* 167:    */ 
/* 168:252 */     int diasAdicionales = 0;
/* 169:253 */     Date fechaDiasAdicionales = FuncionesUtiles.sumarFechaAnios(historicoEmpleado.getFechaIngreso(), anioDiaAdicional);
/* 170:255 */     while (fechaFinPeriodo.compareTo(fechaDiasAdicionales) >= 0)
/* 171:    */     {
/* 172:256 */       diasAdicionales++;
/* 173:257 */       fechaDiasAdicionales = FuncionesUtiles.sumarFechaAnios(fechaDiasAdicionales, 1);
/* 174:    */     }
/* 175:260 */     Vacacion vacacion = new Vacacion();
/* 176:261 */     vacacion.setIdOrganizacion(idOrganizacion);
/* 177:262 */     vacacion.setIdSucursal(historicoEmpleado.getIdSucursal());
/* 178:263 */     vacacion.setHistoricoEmpleado(historicoEmpleado);
/* 179:264 */     vacacion.setIndicadorAnticipoVacacion(true);
/* 180:265 */     vacacion.setFechaInicioPeriodo(fechaInicioPeriodo);
/* 181:266 */     vacacion.setFechaFinPeriodo(fechaFinPeriodo);
/* 182:267 */     vacacion.setDias(15);
/* 183:268 */     vacacion.setDiasTomados(0);
/* 184:269 */     vacacion.setDiasAdicionales(diasAdicionales >= 15 ? 15 : diasAdicionales);
/* 185:    */     
/* 186:271 */     return vacacion;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<Empleado> getEmpleadosConVacacionesPorFecha(int idOrganizacion, Date fecha, Departamento departamento, Empleado empleado)
/* 190:    */   {
/* 191:280 */     return this.vacacionDao.getEmpleadosConVacacionesPorFecha(idOrganizacion, fecha, departamento, empleado);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public int getDiasPendientes(int idHistoricoEmpleado)
/* 195:    */   {
/* 196:288 */     return Long.valueOf(this.vacacionDao.getDiasPendientes(idHistoricoEmpleado)).intValue();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getDiasPendientesFiniquito(HistoricoEmpleado historicoEmpleado)
/* 200:    */   {
/* 201:299 */     Date fechaDesde = FuncionesUtiles.getFecha(FuncionesUtiles.getDiaFecha(historicoEmpleado.getFechaIngreso()), 
/* 202:300 */       FuncionesUtiles.getMes(historicoEmpleado.getFechaIngreso()), FuncionesUtiles.getAnio(historicoEmpleado.getFechaSalida()));
/* 203:301 */     if (fechaDesde.after(historicoEmpleado.getFechaSalida())) {
/* 204:302 */       fechaDesde = FuncionesUtiles.sumarFechaAnios(fechaDesde, -1);
/* 205:    */     }
/* 206:304 */     Date fechaHasta = FuncionesUtiles.sumarFechaAnios(fechaDesde, 1);
/* 207:    */     
/* 208:306 */     int dias_vacacion = FuncionesUtiles.diferenciasDeFechas(fechaDesde, historicoEmpleado.getFechaSalida()) / 24;
/* 209:308 */     if (this.vacacionDao.existeVacacionPorPeriodo(historicoEmpleado.getIdHistoricoEmpleado(), fechaDesde, fechaHasta).booleanValue())
/* 210:    */     {
/* 211:309 */       Vacacion vacacion = (Vacacion)this.vacacionDao.obtenerVacacionPorPeriodo(historicoEmpleado.getIdHistoricoEmpleado(), fechaDesde, fechaHasta).get(0);
/* 212:310 */       dias_vacacion -= vacacion.getDiasTomados();
/* 213:    */     }
/* 214:312 */     return dias_vacacion;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public long getTotalDiasPendientesXCerrar(DetalleVacacion detalleVacacion, Empleado empleado)
/* 218:    */   {
/* 219:317 */     return this.vacacionDao.getTotalDiasPendientesXCerrar(detalleVacacion, empleado);
/* 220:    */   }
/* 221:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioVacacionImpl
 * JD-Core Version:    0.7.0.1
 */