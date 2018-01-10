/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   4:    */ import com.asinfo.as2.entities.Empleado;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.LlamadoAtencion;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ import javax.persistence.TypedQuery;
/*  14:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  15:    */ import javax.persistence.criteria.CriteriaQuery;
/*  16:    */ import javax.persistence.criteria.Expression;
/*  17:    */ import javax.persistence.criteria.Fetch;
/*  18:    */ import javax.persistence.criteria.JoinType;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class LlamadoAtencionDao
/*  24:    */   extends AbstractDaoAS2<LlamadoAtencion>
/*  25:    */ {
/*  26:    */   public LlamadoAtencionDao()
/*  27:    */   {
/*  28: 26 */     super(LlamadoAtencion.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public LlamadoAtencion cargarDetalles(int idLlamadoAtencion)
/*  32:    */   {
/*  33: 31 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34: 32 */     CriteriaQuery<LlamadoAtencion> cqLlamadoAtencion = criteriaBuilder.createQuery(LlamadoAtencion.class);
/*  35: 33 */     Root<LlamadoAtencion> fromLlamadoAtencion = cqLlamadoAtencion.from(LlamadoAtencion.class);
/*  36: 34 */     fromLlamadoAtencion.fetch("motivoLlamadoAtencion", JoinType.INNER);
/*  37:    */     
/*  38: 36 */     Fetch<Object, Object> empleado = fromLlamadoAtencion.fetch("empleado", JoinType.INNER);
/*  39: 37 */     empleado.fetch("cargoEmpleado", JoinType.LEFT);
/*  40: 38 */     empleado.fetch("empresa", JoinType.LEFT);
/*  41:    */     
/*  42: 40 */     cqLlamadoAtencion.where(criteriaBuilder.equal(fromLlamadoAtencion.get("idLlamadoAtencion"), Integer.valueOf(idLlamadoAtencion)));
/*  43: 41 */     LlamadoAtencion llamadoAtencion = (LlamadoAtencion)this.em.createQuery(cqLlamadoAtencion.select(fromLlamadoAtencion)).getSingleResult();
/*  44: 42 */     this.em.detach(llamadoAtencion);
/*  45:    */     
/*  46:    */ 
/*  47: 45 */     CriteriaQuery<DireccionEmpresa> direcciones = criteriaBuilder.createQuery(DireccionEmpresa.class);
/*  48: 46 */     Root<DireccionEmpresa> fromDirecciones = direcciones.from(DireccionEmpresa.class);
/*  49:    */     
/*  50: 48 */     fromDirecciones.fetch("ubicacion", JoinType.LEFT);
/*  51: 49 */     fromDirecciones.fetch("parroquia", JoinType.LEFT);
/*  52: 50 */     fromDirecciones.fetch("ciudad", JoinType.LEFT).fetch("provincia", JoinType.LEFT);
/*  53:    */     
/*  54: 52 */     direcciones.where(criteriaBuilder.equal(fromDirecciones.join("empresa"), Integer.valueOf(llamadoAtencion.getEmpleado().getEmpresa().getId())));
/*  55: 53 */     CriteriaQuery<DireccionEmpresa> selectDirecciones = direcciones.select(fromDirecciones);
/*  56:    */     
/*  57: 55 */     List<DireccionEmpresa> listaDirecciones = this.em.createQuery(selectDirecciones).getResultList();
/*  58: 56 */     llamadoAtencion.getEmpleado().getEmpresa().setDirecciones(listaDirecciones);
/*  59: 57 */     return llamadoAtencion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List reportellamadosAtencion(int idOrganizacion, int idEmpleado, int idDepartamento, int idMotivoLlamadoAtencion, Date fechaDesde, Date fechaHasta)
/*  63:    */   {
/*  64: 61 */     StringBuilder sql = new StringBuilder();
/*  65: 62 */     sql.append(" SELECT (lla.empleado.apellidos + ' ' + lla.empleado.nombres),lla.motivoLlamadoAtencion.nombre,count(lla.idLlamadoAtencion)");
/*  66: 63 */     sql.append(" FROM LlamadoAtencion lla ");
/*  67: 64 */     sql.append(" WHERE lla.idOrganizacion = :idOrganizacion ");
/*  68: 65 */     if (idEmpleado != 0) {
/*  69: 66 */       sql.append(" AND lla.empleado.idEmpleado = :idEmpleado");
/*  70:    */     }
/*  71: 68 */     if (idDepartamento != 0) {
/*  72: 69 */       sql.append(" AND lla.empleado.departamento.idDepartamento = :idDepartamento");
/*  73:    */     }
/*  74: 71 */     if (idMotivoLlamadoAtencion != 0) {
/*  75: 72 */       sql.append(" AND lla.motivoLlamadoAtencion.idMotivoLlamadoAtencion = :idMotivoLlamadoAtencion");
/*  76:    */     }
/*  77: 74 */     if ((fechaDesde != null) && (fechaHasta != null)) {
/*  78: 75 */       sql.append(" AND (lla.fechaDesde BETWEEN :fechaDesde and :fechaHasta)");
/*  79:    */     }
/*  80: 77 */     if ((fechaDesde != null) && (fechaHasta == null)) {
/*  81: 78 */       sql.append(" AND (lla.fechaDesde >= :fechaDesde) ");
/*  82:    */     }
/*  83: 80 */     if ((fechaDesde == null) && (fechaHasta != null)) {
/*  84: 81 */       sql.append(" AND (lla.fechaDesde <= :fechaHasta) ");
/*  85:    */     }
/*  86: 83 */     sql.append(" GROUP BY (lla.empleado.apellidos + ' ' + lla.empleado.nombres),lla.motivoLlamadoAtencion.nombre");
/*  87: 84 */     Query query = this.em.createQuery(sql.toString());
/*  88: 85 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  89: 86 */     if (idEmpleado != 0) {
/*  90: 87 */       query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  91:    */     }
/*  92: 89 */     if (idDepartamento != 0) {
/*  93: 90 */       query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/*  94:    */     }
/*  95: 92 */     if (idMotivoLlamadoAtencion != 0) {
/*  96: 93 */       query.setParameter("idMotivoLlamadoAtencion", Integer.valueOf(idMotivoLlamadoAtencion));
/*  97:    */     }
/*  98: 95 */     if (fechaHasta != null) {
/*  99: 96 */       query.setParameter("fechaHasta", fechaHasta);
/* 100:    */     }
/* 101: 98 */     if (fechaDesde != null) {
/* 102: 99 */       query.setParameter("fechaDesde", fechaDesde);
/* 103:    */     }
/* 104:101 */     return query.getResultList();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<LlamadoAtencion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 108:    */   {
/* 109:107 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 110:108 */     CriteriaQuery<LlamadoAtencion> criteriaQuery = criteriaBuilder.createQuery(LlamadoAtencion.class);
/* 111:109 */     Root<LlamadoAtencion> from = criteriaQuery.from(LlamadoAtencion.class);
/* 112:110 */     from.fetch("motivoLlamadoAtencion", JoinType.LEFT);
/* 113:111 */     from.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.INNER);
/* 114:112 */     from.fetch("documento", JoinType.LEFT).fetch("secuencia", JoinType.LEFT);
/* 115:    */     
/* 116:114 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 117:    */     
/* 118:116 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 119:117 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 120:    */     
/* 121:119 */     CriteriaQuery<LlamadoAtencion> select = criteriaQuery.select(from);
/* 122:    */     
/* 123:121 */     TypedQuery<LlamadoAtencion> typedQuery = this.em.createQuery(select);
/* 124:122 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 125:    */     
/* 126:124 */     return typedQuery.getResultList();
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.LlamadoAtencionDao
 * JD-Core Version:    0.7.0.1
 */