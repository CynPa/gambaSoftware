/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Departamento;
/*   4:    */ import com.asinfo.as2.entities.Empleado;
/*   5:    */ import com.asinfo.as2.entities.Rubro;
/*   6:    */ import com.asinfo.as2.entities.SubsidioEmpleado;
/*   7:    */ import com.asinfo.as2.entities.TipoSubsidio;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TemporalType;
/*  16:    */ import javax.persistence.TypedQuery;
/*  17:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  18:    */ import javax.persistence.criteria.CriteriaQuery;
/*  19:    */ import javax.persistence.criteria.Expression;
/*  20:    */ import javax.persistence.criteria.Fetch;
/*  21:    */ import javax.persistence.criteria.Join;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Predicate;
/*  24:    */ import javax.persistence.criteria.Root;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class SubsidioEmpleadoDao
/*  28:    */   extends AbstractDaoAS2<SubsidioEmpleado>
/*  29:    */ {
/*  30:    */   public SubsidioEmpleadoDao()
/*  31:    */   {
/*  32: 48 */     super(SubsidioEmpleado.class);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<SubsidioEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 59 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  38: 60 */     CriteriaQuery<SubsidioEmpleado> criteriaQuery = criteriaBuilder.createQuery(SubsidioEmpleado.class);
/*  39: 61 */     Root<SubsidioEmpleado> from = criteriaQuery.from(SubsidioEmpleado.class);
/*  40: 62 */     from.fetch("tipoSubsidio", JoinType.LEFT);
/*  41: 63 */     from.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  42:    */     
/*  43: 65 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  44: 66 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  45: 67 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  46:    */     
/*  47: 69 */     CriteriaQuery<SubsidioEmpleado> select = criteriaQuery.select(from);
/*  48: 70 */     TypedQuery<SubsidioEmpleado> typedQuery = this.em.createQuery(select);
/*  49: 71 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  50:    */     
/*  51: 73 */     return typedQuery.getResultList();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public SubsidioEmpleado cargarDetalle(int idSubsidioEmpleado)
/*  55:    */   {
/*  56: 83 */     SubsidioEmpleado subsidioEmpleado = (SubsidioEmpleado)buscarPorId(Integer.valueOf(idSubsidioEmpleado));
/*  57: 84 */     subsidioEmpleado.getIdSubsidioEmpleado();
/*  58: 85 */     if (subsidioEmpleado.getEmpleado() != null) {
/*  59: 86 */       subsidioEmpleado.getEmpleado().getIdEmpleado();
/*  60:    */     }
/*  61: 88 */     if (subsidioEmpleado.getTipoSubsidio() != null) {
/*  62: 89 */       subsidioEmpleado.getTipoSubsidio().getIdTipoSubsidio();
/*  63:    */     }
/*  64: 91 */     return subsidioEmpleado;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<SubsidioEmpleado> buscarPoIdEmpleado(int idEmpleado, Rubro rubro)
/*  68:    */   {
/*  69: 97 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  70: 98 */     CriteriaQuery<SubsidioEmpleado> cq = cb.createQuery(SubsidioEmpleado.class);
/*  71: 99 */     Root<SubsidioEmpleado> from = cq.from(SubsidioEmpleado.class);
/*  72:    */     
/*  73:101 */     from.fetch("tipoSubsidio", JoinType.LEFT).fetch("rubro", JoinType.LEFT);
/*  74:102 */     from.fetch("empleado", JoinType.LEFT);
/*  75:    */     
/*  76:104 */     List<Expression> predicates = new ArrayList();
/*  77:105 */     predicates.add(cb.equal(from.join("empleado").get("idEmpleado"), Integer.valueOf(idEmpleado)));
/*  78:106 */     predicates.add(cb.equal(from.join("tipoSubsidio").get("rubro"), rubro));
/*  79:    */     
/*  80:108 */     cq.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  81:    */     
/*  82:    */ 
/*  83:111 */     TypedQuery<SubsidioEmpleado> tq = this.em.createQuery(cq);
/*  84:    */     
/*  85:113 */     return tq.getResultList();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public List<SubsidioEmpleado> getSubsidioEmpleadoPorFecha(int idOrganizacion, Date fecha, Departamento departamento, Empleado empleado)
/*  89:    */   {
/*  90:120 */     StringBuilder sql = new StringBuilder();
/*  91:    */     
/*  92:122 */     sql.append(" SELECT se FROM SubsidioEmpleado se");
/*  93:123 */     sql.append(" JOIN FETCH se.tipoSubsidio ts");
/*  94:124 */     sql.append(" JOIN FETCH se.empleado e");
/*  95:125 */     if (departamento != null) {
/*  96:126 */       sql.append(" JOIN e.departamento d");
/*  97:    */     }
/*  98:128 */     sql.append(" JOIN e.empresa emp");
/*  99:129 */     sql.append(" JOIN e.cargoEmpleado ce");
/* 100:130 */     sql.append(" WHERE e.idOrganizacion = :idOrganizacion");
/* 101:131 */     sql.append(" AND ce.indicadorRegistraAsistencia IS TRUE ");
/* 102:132 */     sql.append(" AND e.activo IS TRUE");
/* 103:133 */     sql.append(" AND :fecha BETWEEN se.fechaDesde AND se.fechaHasta");
/* 104:134 */     if (departamento != null) {
/* 105:135 */       sql.append(" AND d = :departamento");
/* 106:    */     }
/* 107:137 */     if (empleado != null) {
/* 108:138 */       sql.append(" AND e=:empleado");
/* 109:    */     }
/* 110:140 */     Query query = this.em.createQuery(sql.toString());
/* 111:141 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 112:142 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 113:143 */     if (departamento != null) {
/* 114:144 */       query.setParameter("departamento", departamento);
/* 115:    */     }
/* 116:146 */     if (empleado != null) {
/* 117:147 */       query.setParameter("empleado", empleado);
/* 118:    */     }
/* 119:149 */     return query.getResultList();
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SubsidioEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */