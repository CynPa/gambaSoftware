/*   1:    */ package com.asinfo.as2.dao.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.nomina.asistencia.DetallePlanPersonalizadoHorarioEmpleado;
/*   7:    */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*   8:    */ import com.asinfo.as2.entities.nomina.asistencia.PlanPersonalizadoHorarioEmpleado;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.NoResultException;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TypedQuery;
/*  17:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  18:    */ import javax.persistence.criteria.CriteriaQuery;
/*  19:    */ import javax.persistence.criteria.Expression;
/*  20:    */ import javax.persistence.criteria.Fetch;
/*  21:    */ import javax.persistence.criteria.Join;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Order;
/*  24:    */ import javax.persistence.criteria.Path;
/*  25:    */ import javax.persistence.criteria.Predicate;
/*  26:    */ import javax.persistence.criteria.Root;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class PlanPersonalizadoHorarioEmpleadoDao
/*  30:    */   extends AbstractDaoAS2<PlanPersonalizadoHorarioEmpleado>
/*  31:    */ {
/*  32:    */   public PlanPersonalizadoHorarioEmpleadoDao()
/*  33:    */   {
/*  34: 47 */     super(PlanPersonalizadoHorarioEmpleado.class);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public PlanPersonalizadoHorarioEmpleado cargarDetalle(PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleado)
/*  38:    */   {
/*  39: 51 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  40:    */     
/*  41:    */ 
/*  42: 54 */     CriteriaQuery<PlanPersonalizadoHorarioEmpleado> cqCabecera = criteriaBuilder.createQuery(PlanPersonalizadoHorarioEmpleado.class);
/*  43: 55 */     Root<PlanPersonalizadoHorarioEmpleado> fromCabecera = cqCabecera.from(PlanPersonalizadoHorarioEmpleado.class);
/*  44:    */     
/*  45: 57 */     fromCabecera.fetch("departamento", JoinType.LEFT);
/*  46: 58 */     fromCabecera.fetch("horarioEmpleado", JoinType.LEFT);
/*  47:    */     
/*  48: 60 */     Path<Integer> pathId = fromCabecera.get("idPlanPersonalizadoHorarioEmpleado");
/*  49: 61 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(planPersonalizadoHorarioEmpleado.getId())));
/*  50: 62 */     CriteriaQuery<PlanPersonalizadoHorarioEmpleado> select = cqCabecera.select(fromCabecera);
/*  51:    */     
/*  52: 64 */     PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleadoNew = (PlanPersonalizadoHorarioEmpleado)this.em.createQuery(select).getSingleResult();
/*  53: 65 */     this.em.detach(planPersonalizadoHorarioEmpleadoNew);
/*  54:    */     
/*  55:    */ 
/*  56: 68 */     CriteriaQuery<DetallePlanPersonalizadoHorarioEmpleado> cqDetalle = criteriaBuilder.createQuery(DetallePlanPersonalizadoHorarioEmpleado.class);
/*  57: 69 */     Root<DetallePlanPersonalizadoHorarioEmpleado> fromDetalle = cqDetalle.from(DetallePlanPersonalizadoHorarioEmpleado.class);
/*  58:    */     
/*  59: 71 */     fromDetalle.fetch("planPersonalizadoHorarioEmpleado", JoinType.LEFT);
/*  60: 72 */     fromDetalle.fetch("empleado", JoinType.LEFT).fetch("grupoTrabajo", JoinType.LEFT);
/*  61: 73 */     for (int i = 1; i <= 31; i++) {
/*  62: 74 */       fromDetalle.fetch("turno" + i, JoinType.LEFT);
/*  63:    */     }
/*  64: 76 */     Path<String> nombreEmpleado = fromDetalle.join("empleado", JoinType.LEFT).get("apellidos");
/*  65:    */     
/*  66:    */ 
/*  67: 79 */     Predicate expresionPlan = criteriaBuilder.equal(fromDetalle.join("planPersonalizadoHorarioEmpleado"), planPersonalizadoHorarioEmpleado);
/*  68: 80 */     Predicate expresionEmpleadoActivo = criteriaBuilder.equal(fromDetalle.join("empleado").get("activo"), Boolean.valueOf(true));
/*  69:    */     
/*  70: 82 */     List<Predicate> listaExpresiones = new ArrayList();
/*  71: 83 */     listaExpresiones.add(expresionPlan);
/*  72: 84 */     listaExpresiones.add(expresionEmpleadoActivo);
/*  73:    */     
/*  74: 86 */     cqDetalle.where((Predicate[])listaExpresiones.toArray(new Predicate[listaExpresiones.size()]));
/*  75: 87 */     CriteriaQuery<DetallePlanPersonalizadoHorarioEmpleado> selectFactura = cqDetalle.select(fromDetalle).orderBy(new Order[] {criteriaBuilder
/*  76: 88 */       .asc(nombreEmpleado) });
/*  77:    */     
/*  78: 90 */     List<DetallePlanPersonalizadoHorarioEmpleado> listaDetallePlanPersonalizadoHorarioEmpleado = this.em.createQuery(selectFactura).getResultList();
/*  79:    */     
/*  80: 92 */     planPersonalizadoHorarioEmpleadoNew.setListaDetallePlanPersonalizadoHorarioEmpleado(listaDetallePlanPersonalizadoHorarioEmpleado);
/*  81: 93 */     return planPersonalizadoHorarioEmpleadoNew;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<PlanPersonalizadoHorarioEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  85:    */   {
/*  86: 98 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  87: 99 */     CriteriaQuery<PlanPersonalizadoHorarioEmpleado> criteriaQuery = criteriaBuilder.createQuery(PlanPersonalizadoHorarioEmpleado.class);
/*  88:100 */     Root<PlanPersonalizadoHorarioEmpleado> from = criteriaQuery.from(PlanPersonalizadoHorarioEmpleado.class);
/*  89:101 */     from.fetch("departamento", JoinType.LEFT);
/*  90:102 */     from.fetch("horarioEmpleado", JoinType.LEFT);
/*  91:    */     
/*  92:104 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  93:    */     
/*  94:106 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  95:    */     
/*  96:108 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  97:    */     
/*  98:110 */     CriteriaQuery<PlanPersonalizadoHorarioEmpleado> select = criteriaQuery.select(from);
/*  99:    */     
/* 100:112 */     TypedQuery<PlanPersonalizadoHorarioEmpleado> typedQuery = this.em.createQuery(select);
/* 101:113 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 102:114 */     return typedQuery.getResultList();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean existePlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado pphe)
/* 106:    */   {
/* 107:127 */     StringBuilder sql = new StringBuilder();
/* 108:128 */     sql.append(" SELECT ph FROM PlanPersonalizadoHorarioEmpleado ph");
/* 109:129 */     sql.append(" JOIN ph.departamento d");
/* 110:130 */     sql.append(" JOIN ph.horarioEmpleado h");
/* 111:131 */     sql.append(" WHERE ph.idPlanPersonalizadoHorarioEmpleado != :idPlanPersonalizadoHorarioEmpleado");
/* 112:132 */     sql.append(" AND d = :departamento");
/* 113:133 */     sql.append(" AND h = :horarioEmpleado");
/* 114:134 */     sql.append(" AND ph.anno = :anio");
/* 115:135 */     sql.append(" AND ph.mes = :mes");
/* 116:    */     
/* 117:137 */     Query query = this.em.createQuery(sql.toString());
/* 118:138 */     query.setParameter("idPlanPersonalizadoHorarioEmpleado", Integer.valueOf(pphe.getId()));
/* 119:139 */     query.setParameter("departamento", pphe.getDepartamento());
/* 120:140 */     query.setParameter("horarioEmpleado", pphe.getHorarioEmpleado());
/* 121:141 */     query.setParameter("anio", pphe.getAnno());
/* 122:142 */     query.setParameter("mes", pphe.getMes());
/* 123:    */     try
/* 124:    */     {
/* 125:145 */       query.getSingleResult();
/* 126:146 */       return true;
/* 127:    */     }
/* 128:    */     catch (NoResultException e) {}
/* 129:148 */     return false;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<Empleado> obtenerEmpleadosPorDepartamentoNoAsignadosPlan(Departamento departamento, HorarioEmpleado horarioEmpleado, PlanPersonalizadoHorarioEmpleado pphe)
/* 133:    */   {
/* 134:155 */     StringBuilder sql = new StringBuilder();
/* 135:156 */     sql.append(" SELECT e FROM Empleado e");
/* 136:157 */     sql.append(" LEFT JOIN FETCH e.grupoTrabajo gtr ");
/* 137:158 */     sql.append(" INNER JOIN e.departamento d");
/* 138:159 */     sql.append(" INNER JOIN e.horarioEmpleado h");
/* 139:160 */     sql.append(" INNER JOIN e.empresa empr");
/* 140:161 */     sql.append(" INNER JOIN e.empresa empr");
/* 141:162 */     sql.append(" WHERE e.activo = true");
/* 142:163 */     sql.append(" AND d = :departamento");
/* 143:164 */     sql.append(" AND h = :horarioEmpleado");
/* 144:165 */     sql.append(" AND e.idEmpleado NOT IN (");
/* 145:166 */     sql.append(" SELECT em.idEmpleado FROM DetallePlanPersonalizadoHorarioEmpleado det ");
/* 146:167 */     sql.append(" INNER JOIN det.planPersonalizadoHorarioEmpleado pphe");
/* 147:168 */     sql.append(" INNER JOIN det.empleado em");
/* 148:169 */     sql.append(" WHERE pphe =:pphe)");
/* 149:170 */     sql.append(" ORDER BY e.apellidos ");
/* 150:    */     
/* 151:172 */     Query query = this.em.createQuery(sql.toString());
/* 152:173 */     query.setParameter("departamento", departamento);
/* 153:174 */     query.setParameter("horarioEmpleado", horarioEmpleado);
/* 154:175 */     query.setParameter("pphe", pphe);
/* 155:    */     
/* 156:177 */     return query.getResultList();
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.nomina.asistencia.PlanPersonalizadoHorarioEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */