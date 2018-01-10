/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Departamento;
/*   4:    */ import com.asinfo.as2.entities.DetallePermisoEmpleado;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.PermisoEmpleado;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.Fetch;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Order;
/*  22:    */ import javax.persistence.criteria.Predicate;
/*  23:    */ import javax.persistence.criteria.Root;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class PermisoEmpleadoDao
/*  27:    */   extends AbstractDaoAS2<PermisoEmpleado>
/*  28:    */ {
/*  29:    */   public PermisoEmpleadoDao()
/*  30:    */   {
/*  31: 48 */     super(PermisoEmpleado.class);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<PermisoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 58 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  37: 59 */     CriteriaQuery<PermisoEmpleado> criteriaQuery = criteriaBuilder.createQuery(PermisoEmpleado.class);
/*  38: 60 */     Root<PermisoEmpleado> from = criteriaQuery.from(PermisoEmpleado.class);
/*  39: 61 */     from.fetch("tipoPermisoEmpleado", JoinType.LEFT);
/*  40:    */     
/*  41: 63 */     Fetch<Object, Object> historicoEmpleado = from.fetch("historicoEmpleado", JoinType.LEFT);
/*  42: 64 */     Fetch<Object, Object> empleado = historicoEmpleado.fetch("empleado", JoinType.LEFT);
/*  43: 65 */     empleado.fetch("empresa", JoinType.LEFT);
/*  44:    */     
/*  45: 67 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  46: 68 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  47:    */     
/*  48: 70 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  49:    */     
/*  50: 72 */     CriteriaQuery<PermisoEmpleado> select = criteriaQuery.select(from);
/*  51: 73 */     TypedQuery<PermisoEmpleado> typedQuery = this.em.createQuery(select);
/*  52: 74 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  53:    */     
/*  54: 76 */     return typedQuery.getResultList();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public PermisoEmpleado cargarDetalle(int idPermisoEmpleado)
/*  58:    */   {
/*  59: 87 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  60: 88 */     CriteriaQuery<PermisoEmpleado> cq = cb.createQuery(PermisoEmpleado.class);
/*  61:    */     
/*  62: 90 */     Root<PermisoEmpleado> from = cq.from(PermisoEmpleado.class);
/*  63: 91 */     from.fetch("documento", JoinType.LEFT).fetch("secuencia", JoinType.LEFT);
/*  64: 92 */     from.fetch("historicoEmpleado", JoinType.LEFT).fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  65: 93 */     from.fetch("tipoPermisoEmpleado", JoinType.LEFT);
/*  66: 94 */     cq.where(cb.equal(from.get("idPermisoEmpleado"), Integer.valueOf(idPermisoEmpleado)));
/*  67:    */     
/*  68: 96 */     PermisoEmpleado permisoEmpleado = (PermisoEmpleado)this.em.createQuery(cq.select(from)).getSingleResult();
/*  69: 97 */     this.em.detach(permisoEmpleado);
/*  70:    */     
/*  71: 99 */     CriteriaQuery<DetallePermisoEmpleado> cqDetalle = cb.createQuery(DetallePermisoEmpleado.class);
/*  72:100 */     Root<DetallePermisoEmpleado> detalle = cqDetalle.from(DetallePermisoEmpleado.class);
/*  73:101 */     cqDetalle.where(cb.equal(detalle.get("permisoEmpleado"), permisoEmpleado));
/*  74:102 */     cqDetalle.orderBy(new Order[] { cb.asc(detalle.get("idDetallePermisoEmpleado")) });
/*  75:    */     
/*  76:104 */     List<DetallePermisoEmpleado> listaDetalle = this.em.createQuery(cqDetalle).getResultList();
/*  77:105 */     permisoEmpleado.setListaDetallePermisoEmpleado(listaDetalle);
/*  78:106 */     return permisoEmpleado;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List getPersmisoEmpleado(int idPermisoEmpleado)
/*  82:    */   {
/*  83:111 */     StringBuilder sql = new StringBuilder();
/*  84:112 */     sql.append("SELECT em.codigo, em.identificacion, e.apellidos, e.nombres, ec.nombre, d.nombre, ce.nombre, t.nombre, dpe.horaDesde, dpe.horaHasta,");
/*  85:113 */     sql.append(" p.horas, p.descripcion, tpe.nombre, p.fechaPermiso, p.diaDesde, p.diaHasta, p.numero, dpe.horas, dpe.descripcion, dpe.fechaPermiso,  p.usuarioCreacion,p.usuarioModificacion");
/*  86:    */     
/*  87:115 */     sql.append(" FROM DetallePermisoEmpleado dpe");
/*  88:116 */     sql.append(" INNER JOIN dpe.permisoEmpleado p");
/*  89:117 */     sql.append(" INNER JOIN p.historicoEmpleado he");
/*  90:118 */     sql.append(" INNER JOIN he.empleado e");
/*  91:119 */     sql.append(" INNER JOIN e.empresa em");
/*  92:120 */     sql.append(" INNER JOIN e.cargoEmpleado ce");
/*  93:121 */     sql.append(" INNER JOIN e.titulo t");
/*  94:122 */     sql.append(" INNER JOIN e.departamento d");
/*  95:123 */     sql.append(" INNER JOIN e.estadoCivil ec");
/*  96:124 */     sql.append(" INNER JOIN p.tipoPermisoEmpleado tpe");
/*  97:125 */     sql.append(" WHERE em.indicadorEmpleado = true AND p.idPermisoEmpleado=:idPermisoEmpleado");
/*  98:    */     
/*  99:127 */     Query query = this.em.createQuery(sql.toString());
/* 100:128 */     query.setParameter("idPermisoEmpleado", Integer.valueOf(idPermisoEmpleado));
/* 101:129 */     return query.getResultList();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<DetallePermisoEmpleado> getPermisoEmpleadoPorFecha(int idOrganizacion, Date fecha, Departamento departamento, Empleado empleado)
/* 105:    */   {
/* 106:134 */     StringBuilder sql = new StringBuilder();
/* 107:    */     
/* 108:136 */     sql.append("SELECT dpe");
/* 109:137 */     sql.append(" FROM DetallePermisoEmpleado dpe");
/* 110:138 */     sql.append(" JOIN FETCH dpe.permisoEmpleado pe");
/* 111:139 */     sql.append(" JOIN FETCH pe.historicoEmpleado he");
/* 112:140 */     sql.append(" JOIN FETCH he.empleado e");
/* 113:141 */     sql.append(" JOIN e.cargoEmpleado ce");
/* 114:142 */     sql.append(" JOIN e.departamento d");
/* 115:143 */     sql.append(" WHERE e.idOrganizacion = :idOrganizacion");
/* 116:144 */     sql.append(" AND ce.indicadorRegistraAsistencia IS TRUE");
/* 117:145 */     sql.append(" AND CASE WHEN he.fechaSalida IS NOT NULL THEN he.fechaSalida ELSE :fecha END >=:fecha");
/* 118:146 */     sql.append(" AND dpe.fechaPermiso = :fecha");
/* 119:147 */     sql.append(" AND (pe.estado = :estadoAprobado OR pe.estado = :estadoCerrado)");
/* 120:148 */     if (departamento != null) {
/* 121:149 */       sql.append(" AND d = :departamento");
/* 122:    */     }
/* 123:151 */     if (empleado != null) {
/* 124:152 */       sql.append(" AND e = :empleado");
/* 125:    */     }
/* 126:155 */     Query query = this.em.createQuery(sql.toString());
/* 127:156 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 128:157 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 129:158 */     query.setParameter("estadoAprobado", Estado.APROBADO);
/* 130:159 */     query.setParameter("estadoCerrado", Estado.CERRADO);
/* 131:160 */     if (departamento != null) {
/* 132:161 */       query.setParameter("departamento", departamento);
/* 133:    */     }
/* 134:163 */     if (empleado != null) {
/* 135:164 */       query.setParameter("empleado", empleado);
/* 136:    */     }
/* 137:166 */     return query.getResultList();
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PermisoEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */