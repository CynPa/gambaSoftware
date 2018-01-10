/*   1:    */ package com.asinfo.as2.nomina.asistencia.procesos.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.EmpleadoDao;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.dao.nomina.asistencia.AsistenciaProcesosDao;
/*   6:    */ import com.asinfo.as2.dao.nomina.asistencia.PlanPersonalizadoHorarioEmpleadoDao;
/*   7:    */ import com.asinfo.as2.entities.Departamento;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.nomina.asistencia.DetallePlanPersonalizadoHorarioEmpleado;
/*  11:    */ import com.asinfo.as2.entities.nomina.asistencia.DiaFestivo;
/*  12:    */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*  13:    */ import com.asinfo.as2.entities.nomina.asistencia.PlanPersonalizadoHorarioEmpleado;
/*  14:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  15:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  16:    */ import com.asinfo.as2.nomina.asistencia.procesos.ServicioAsistenciaProcesos;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class ServicioAsistenciaProcesosImpl
/*  26:    */   implements ServicioAsistenciaProcesos
/*  27:    */ {
/*  28:    */   @EJB
/*  29:    */   private AsistenciaProcesosDao asistenciaProcesosDao;
/*  30:    */   @EJB
/*  31:    */   private GenericoDao<DiaFestivo> diaFestivoDao;
/*  32:    */   @EJB
/*  33:    */   private PlanPersonalizadoHorarioEmpleadoDao planPersonalizadoHorarioEmpleadoDao;
/*  34:    */   @EJB
/*  35:    */   private GenericoDao<DetallePlanPersonalizadoHorarioEmpleado> detallePlanPersonalizadoHorarioEmpleadoDao;
/*  36:    */   @EJB
/*  37:    */   private EmpleadoDao empleadoDao;
/*  38:    */   
/*  39:    */   public PlanPersonalizadoHorarioEmpleado cargarDetallePlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleado)
/*  40:    */   {
/*  41: 58 */     PlanPersonalizadoHorarioEmpleado plan = this.planPersonalizadoHorarioEmpleadoDao.cargarDetalle(planPersonalizadoHorarioEmpleado);
/*  42:    */     
/*  43: 60 */     List<Empleado> lista = this.planPersonalizadoHorarioEmpleadoDao.obtenerEmpleadosPorDepartamentoNoAsignadosPlan(plan.getDepartamento(), plan
/*  44: 61 */       .getHorarioEmpleado(), plan);
/*  45: 62 */     for (Empleado empleado : lista)
/*  46:    */     {
/*  47: 63 */       DetallePlanPersonalizadoHorarioEmpleado detalle = new DetallePlanPersonalizadoHorarioEmpleado();
/*  48: 64 */       detalle.setIdOrganizacion(plan.getIdOrganizacion());
/*  49: 65 */       detalle.setIdSucursal(plan.getIdSucursal());
/*  50: 66 */       detalle.setEmpleado(empleado);
/*  51: 67 */       detalle.setPlanPersonalizadoHorarioEmpleado(plan);
/*  52: 68 */       plan.getListaDetallePlanPersonalizadoHorarioEmpleado().add(detalle);
/*  53:    */     }
/*  54: 70 */     return plan;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<PlanPersonalizadoHorarioEmpleado> obtenerListaPorPaginaPlanPersonalizadoHorarioEmpleado(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  58:    */   {
/*  59: 76 */     return this.planPersonalizadoHorarioEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int contarPorCriterioPlanPersonalizadoHorarioEmpleado(Map<String, String> filters)
/*  63:    */   {
/*  64: 81 */     return this.planPersonalizadoHorarioEmpleadoDao.contarPorCriterio(filters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void guardarPlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado planPersonalizado)
/*  68:    */     throws AS2Exception
/*  69:    */   {
/*  70: 88 */     if (this.planPersonalizadoHorarioEmpleadoDao.existePlanPersonalizadoHorarioEmpleado(planPersonalizado)) {
/*  71: 90 */       throw new AS2Exception("com.asinfo.as2.nomina.asistencia.procesos.impl.ServicioAsistenciaProcesosImpl.ERROR_PLANIFICACION_HORARIO_DUPLICADA", new String[] { planPersonalizado.getMes().toString(), planPersonalizado.getDepartamento().getNombre() });
/*  72:    */     }
/*  73: 93 */     this.planPersonalizadoHorarioEmpleadoDao.guardar(planPersonalizado);
/*  74: 94 */     for (DetallePlanPersonalizadoHorarioEmpleado detalle : planPersonalizado.getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/*  75: 95 */       this.detallePlanPersonalizadoHorarioEmpleadoDao.guardar(detalle);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void copiarPlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado planPersonalizado)
/*  80:    */     throws AS2Exception
/*  81:    */   {
/*  82:102 */     HashMap<Integer, Empleado> hpEmpleado = new HashMap();
/*  83:    */     
/*  84:104 */     int mes = planPersonalizado.getMes().getNumero();
/*  85:105 */     int anio = planPersonalizado.getAnno().intValue();
/*  86:107 */     if (mes == 12)
/*  87:    */     {
/*  88:108 */       planPersonalizado.setMes(Mes.ENERO);
/*  89:109 */       anio += 1;
/*  90:110 */       planPersonalizado.setAnno(Integer.valueOf(anio));
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:112 */       mes += 1;
/*  95:113 */       for (Mes meses : Mes.values()) {
/*  96:114 */         if (meses.getNumero() == mes) {
/*  97:115 */           planPersonalizado.setMes(meses);
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:120 */     Object filtros = new HashMap();
/* 102:121 */     ((Map)filtros).put("idOrganizacion", AppUtil.getOrganizacion().getId() + "");
/* 103:122 */     ((Map)filtros).put("mes", "" + planPersonalizado.getMes());
/* 104:123 */     ((Map)filtros).put("anno", "" + planPersonalizado.getAnno());
/* 105:124 */     ((Map)filtros).put("departamento.idDepartamento", "" + planPersonalizado.getDepartamento().getIdDepartamento());
/* 106:125 */     ((Map)filtros).put("horarioEmpleado.idHorarioEmpleado", "" + planPersonalizado.getHorarioEmpleado().getIdHorarioEmpleado());
/* 107:    */     
/* 108:127 */     Object listaPlanPersonalizadoHorarioEmpleado = this.planPersonalizadoHorarioEmpleadoDao.obtenerListaCombo("mes", true, (Map)filtros);
/* 109:130 */     if (((List)listaPlanPersonalizadoHorarioEmpleado).size() > 0) {
/* 110:132 */       throw new AS2Exception("com.asinfo.as2.nomina.asistencia.procesos.impl.ServicioAsistenciaProcesosImpl.ERROR_PLANIFICACION_HORARIO_DUPLICADA", new String[] { planPersonalizado.getMes().toString(), planPersonalizado.getDepartamento().getNombre() });
/* 111:    */     }
/* 112:134 */     planPersonalizado.setIdPlanPersonalizadoHorarioEmpleado(0);
/* 113:    */     
/* 114:136 */     this.planPersonalizadoHorarioEmpleadoDao.guardar(planPersonalizado);
/* 115:137 */     Object filters = new HashMap();
/* 116:138 */     ((Map)filters).put("idOrganizacion", AppUtil.getOrganizacion().getId() + "");
/* 117:139 */     ((Map)filters).put("departamento.idDepartamento", "" + planPersonalizado.getDepartamento().getId());
/* 118:140 */     ((Map)filters).put("horarioEmpleado.idHorarioEmpleado", "" + planPersonalizado.getHorarioEmpleado().getId());
/* 119:141 */     ((Map)filters).put("activo", "true");
/* 120:    */     
/* 121:143 */     List<Empleado> listaEmpleado = this.empleadoDao.obtenerListaCombo("apellidos", true, (Map)filters);
/* 122:144 */     for (Empleado empleado : listaEmpleado) {
/* 123:145 */       hpEmpleado.put(Integer.valueOf(empleado.getIdEmpleado()), empleado);
/* 124:    */     }
/* 125:148 */     for (DetallePlanPersonalizadoHorarioEmpleado detallePlanPersonalizadoHorarioEmpleado : planPersonalizado
/* 126:149 */       .getListaDetallePlanPersonalizadoHorarioEmpleado()) {
/* 127:150 */       if (hpEmpleado.containsKey(Integer.valueOf(detallePlanPersonalizadoHorarioEmpleado.getEmpleado().getIdEmpleado())))
/* 128:    */       {
/* 129:151 */         detallePlanPersonalizadoHorarioEmpleado.setIdDetallePlanPersonalizadoHorarioEmpleado(0);
/* 130:152 */         detallePlanPersonalizadoHorarioEmpleado.setPlanPersonalizadoHorarioEmpleado(planPersonalizado);
/* 131:153 */         this.detallePlanPersonalizadoHorarioEmpleadoDao.guardar(detallePlanPersonalizadoHorarioEmpleado);
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.procesos.impl.ServicioAsistenciaProcesosImpl
 * JD-Core Version:    0.7.0.1
 */