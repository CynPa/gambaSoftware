/*   1:    */ package com.asinfo.as2.dao.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.DocumentoEquipo;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.ImagenEquipo;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.Expression;
/*  18:    */ import javax.persistence.criteria.JoinType;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class EquipoDao
/*  24:    */   extends AbstractDaoAS2<Equipo>
/*  25:    */ {
/*  26:    */   public EquipoDao()
/*  27:    */   {
/*  28: 35 */     super(Equipo.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Equipo cargarDetalle(Equipo equipo)
/*  32:    */   {
/*  33: 39 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  34: 40 */     CriteriaQuery<Equipo> criteriaQuery = cb.createQuery(Equipo.class);
/*  35: 41 */     Root<Equipo> from = criteriaQuery.from(Equipo.class);
/*  36: 42 */     from.fetch("subcategoriaEquipo", JoinType.LEFT);
/*  37: 43 */     from.fetch("ubicacion", JoinType.LEFT);
/*  38: 44 */     from.fetch("centroCosto", JoinType.LEFT);
/*  39: 45 */     from.fetch("equipoPadre", JoinType.LEFT);
/*  40: 46 */     from.fetch("facturaProveedor", JoinType.LEFT);
/*  41: 47 */     from.fetch("activoFijo", JoinType.LEFT);
/*  42: 48 */     criteriaQuery.where(cb.equal(from.get("idEquipo"), Integer.valueOf(equipo.getIdEquipo())));
/*  43: 49 */     CriteriaQuery<Equipo> select = criteriaQuery.select(from);
/*  44: 50 */     Equipo tmpEquipo = (Equipo)this.em.createQuery(select).getSingleResult();
/*  45:    */     
/*  46: 52 */     CriteriaQuery<DetalleComponenteEquipo> cqDCE = cb.createQuery(DetalleComponenteEquipo.class);
/*  47: 53 */     Root<DetalleComponenteEquipo> fromCE = cqDCE.from(DetalleComponenteEquipo.class);
/*  48: 54 */     fromCE.fetch("componenteEquipo", JoinType.LEFT);
/*  49: 55 */     cqDCE.where(cb.equal(fromCE.join("equipo"), tmpEquipo));
/*  50: 56 */     CriteriaQuery<DetalleComponenteEquipo> selectDCE = cqDCE.select(fromCE);
/*  51: 57 */     List<DetalleComponenteEquipo> listaDCE = this.em.createQuery(selectDCE).getResultList();
/*  52:    */     
/*  53: 59 */     CriteriaQuery<DocumentoEquipo> cqDE = cb.createQuery(DocumentoEquipo.class);
/*  54: 60 */     Root<DocumentoEquipo> fromDE = cqDE.from(DocumentoEquipo.class);
/*  55: 61 */     cqDE.where(cb.equal(fromDE.join("equipo"), tmpEquipo));
/*  56: 62 */     CriteriaQuery<DocumentoEquipo> selectDE = cqDE.select(fromDE);
/*  57: 63 */     List<DocumentoEquipo> listaDE = this.em.createQuery(selectDE).getResultList();
/*  58:    */     
/*  59: 65 */     CriteriaQuery<ImagenEquipo> cqIE = cb.createQuery(ImagenEquipo.class);
/*  60: 66 */     Root<ImagenEquipo> fromIE = cqIE.from(ImagenEquipo.class);
/*  61: 67 */     cqIE.where(cb.equal(fromIE.join("equipo"), tmpEquipo));
/*  62: 68 */     CriteriaQuery<ImagenEquipo> selectIE = cqIE.select(fromIE);
/*  63: 69 */     List<ImagenEquipo> listaIE = this.em.createQuery(selectIE).getResultList();
/*  64:    */     
/*  65: 71 */     CriteriaQuery<PlanMantenimientoEquipo> cqPlan = cb.createQuery(PlanMantenimientoEquipo.class);
/*  66: 72 */     Root<PlanMantenimientoEquipo> fromPlan = cqPlan.from(PlanMantenimientoEquipo.class);
/*  67: 73 */     fromPlan.fetch("planMantenimiento", JoinType.INNER);
/*  68: 74 */     cqPlan.where(cb.equal(fromPlan.join("equipo"), tmpEquipo));
/*  69: 75 */     CriteriaQuery<PlanMantenimientoEquipo> selectPlan = cqPlan.select(fromPlan);
/*  70: 76 */     List<PlanMantenimientoEquipo> listaPlan = this.em.createQuery(selectPlan).getResultList();
/*  71:    */     
/*  72: 78 */     this.em.detach(tmpEquipo);
/*  73: 79 */     tmpEquipo.setListaDocumentoEquipo(listaDE);
/*  74: 80 */     tmpEquipo.setListaImagenEquipo(listaIE);
/*  75: 81 */     tmpEquipo.setListaComponenteEquipo(listaDCE);
/*  76: 82 */     tmpEquipo.setListaPlanMantenimientoEquipo(listaPlan);
/*  77:    */     
/*  78: 84 */     return tmpEquipo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List<Equipo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  82:    */   {
/*  83: 88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  84: 89 */     CriteriaQuery<Equipo> criteriaQuery = criteriaBuilder.createQuery(Equipo.class);
/*  85: 90 */     Root<Equipo> from = criteriaQuery.from(Equipo.class);
/*  86: 91 */     from.fetch("subcategoriaEquipo", JoinType.LEFT);
/*  87: 92 */     from.fetch("ubicacion", JoinType.LEFT);
/*  88: 93 */     from.fetch("centroCosto", JoinType.LEFT);
/*  89: 94 */     from.fetch("equipoPadre", JoinType.LEFT);
/*  90: 95 */     from.fetch("facturaProveedor", JoinType.LEFT);
/*  91:    */     
/*  92: 97 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  93: 98 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  94:    */     
/*  95:100 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  96:    */     
/*  97:102 */     CriteriaQuery<Equipo> select = criteriaQuery.select(from);
/*  98:    */     
/*  99:104 */     TypedQuery<Equipo> typedQuery = this.em.createQuery(select);
/* 100:105 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 101:    */     
/* 102:107 */     List<Equipo> listaEquipo = typedQuery.getResultList();
/* 103:109 */     for (Equipo equipo : listaEquipo) {
/* 104:110 */       equipo.setCantidadPlanMantenimiento(contarPlanesPorEquipo(equipo));
/* 105:    */     }
/* 106:113 */     return listaEquipo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int contarPlanesPorEquipo(Equipo equipo)
/* 110:    */   {
/* 111:118 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 112:119 */     CriteriaQuery<Long> cqPlan = cb.createQuery(Long.class);
/* 113:120 */     Root<PlanMantenimientoEquipo> fromPlan = cqPlan.from(PlanMantenimientoEquipo.class);
/* 114:121 */     cqPlan.where(cb.equal(fromPlan.join("equipo"), equipo));
/* 115:122 */     CriteriaQuery<Long> selectPlan = cqPlan.select(cb.count(fromPlan));
/* 116:123 */     Integer cantidad = Integer.valueOf(((Long)this.em.createQuery(selectPlan).getSingleResult()).intValue());
/* 117:124 */     return cantidad.intValue();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public List<Object[]> obtenerEquiposConPlanMantenimiento(int idOrganizacion)
/* 121:    */   {
/* 122:130 */     StringBuilder sql = new StringBuilder();
/* 123:131 */     sql.append(" SELECT dce.equipo, dce.componenteEquipo, pme.planMantenimiento ");
/* 124:132 */     sql.append(" FROM DetalleComponenteEquipo dce, PlanMantenimientoEquipo pme ");
/* 125:133 */     sql.append(" INNER JOIN dce.equipo eq ");
/* 126:134 */     sql.append(" INNER JOIN dce.componenteEquipo ceq ");
/* 127:135 */     sql.append(" INNER JOIN pme.equipo eq2 ");
/* 128:136 */     sql.append(" WHERE eq.idOrganizacion = :idOrganizacion ");
/* 129:137 */     sql.append(" AND   eq2.idEquipo = eq.idEquipo ");
/* 130:    */     
/* 131:    */ 
/* 132:140 */     Query query = this.em.createQuery(sql.toString());
/* 133:141 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 134:    */     
/* 135:143 */     return query.getResultList();
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.EquipoDao
 * JD-Core Version:    0.7.0.1
 */