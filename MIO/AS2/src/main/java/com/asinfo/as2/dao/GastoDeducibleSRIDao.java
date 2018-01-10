/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empleado;
/*   4:    */ import com.asinfo.as2.entities.GastoDeducibleSRI;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.NoResultException;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.JoinType;
/*  20:    */ import javax.persistence.criteria.Predicate;
/*  21:    */ import javax.persistence.criteria.Root;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class GastoDeducibleSRIDao
/*  25:    */   extends AbstractDaoAS2<GastoDeducibleSRI>
/*  26:    */ {
/*  27:    */   public GastoDeducibleSRIDao()
/*  28:    */   {
/*  29: 45 */     super(GastoDeducibleSRI.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<GastoDeducibleSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  33:    */   {
/*  34: 55 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  35: 56 */     CriteriaQuery<GastoDeducibleSRI> criteriaQuery = criteriaBuilder.createQuery(GastoDeducibleSRI.class);
/*  36: 57 */     Root<GastoDeducibleSRI> from = criteriaQuery.from(GastoDeducibleSRI.class);
/*  37:    */     
/*  38: 59 */     from.fetch("tipoGastoDeducibleSRI", JoinType.LEFT);
/*  39: 60 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  40:    */     
/*  41: 62 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  42: 63 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  43:    */     
/*  44: 65 */     CriteriaQuery<GastoDeducibleSRI> select = criteriaQuery.select(from);
/*  45: 66 */     TypedQuery<GastoDeducibleSRI> typedQuery = this.em.createQuery(select);
/*  46: 67 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  47:    */     
/*  48: 69 */     return typedQuery.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<GastoDeducibleSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  52:    */   {
/*  53: 78 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int contarPorCriterio(Map<String, String> filters)
/*  57:    */   {
/*  58: 89 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  59: 90 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  60:    */     
/*  61: 92 */     Root<GastoDeducibleSRI> from = criteriaQuery.from(GastoDeducibleSRI.class);
/*  62: 93 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  63:    */     
/*  64: 95 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  65: 96 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  66:    */     
/*  67: 98 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  68:    */   }
/*  69:    */   
/*  70:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<GastoDeducibleSRI> from)
/*  71:    */   {
/*  72:113 */     List<Predicate> predicates = new ArrayList();
/*  73:114 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  74:    */     {
/*  75:115 */       String filterProperty = (String)it.next();
/*  76:117 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  77:    */       {
/*  78:118 */         String filterValue = (String)filters.get(filterProperty);
/*  79:120 */         if (filterProperty.equals("idOrganizacion"))
/*  80:    */         {
/*  81:121 */           Expression<Integer> path = from.get(filterProperty);
/*  82:122 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  83:    */         }
/*  84:123 */         else if (filterProperty.equals("anio"))
/*  85:    */         {
/*  86:124 */           Expression<Integer> path = from.get(filterProperty);
/*  87:125 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:129 */     return predicates;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public GastoDeducibleSRI cargarDetalle(int idGastoDeducibleSRI)
/*  95:    */   {
/*  96:139 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<Empleado> obtenerGastoDededuciblesNuevos(int anio, int idOrganizacion)
/* 100:    */   {
/* 101:152 */     StringBuilder sql = new StringBuilder();
/* 102:153 */     sql.append(" SELECT e");
/* 103:154 */     sql.append(" FROM  Empleado e ");
/* 104:155 */     sql.append(" WHERE e.idEmpleado NOT IN ");
/* 105:156 */     sql.append(" ( ");
/* 106:157 */     sql.append(" \tSELECT em.idEmpleado FROM GastoDeducibleSRI gp ");
/* 107:158 */     sql.append(" \tINNER JOIN gp.empleado em ");
/* 108:159 */     sql.append("\tWHERE gp.anio = :anio ");
/* 109:160 */     sql.append(" \tAND gp.idOrganizacion = :idOrganizacion ");
/* 110:161 */     sql.append(" ) ");
/* 111:162 */     sql.append(" AND e.idOrganizacion = :idOrganizacion ");
/* 112:163 */     sql.append(" AND e.activo = true ");
/* 113:164 */     Query query = this.em.createQuery(sql.toString());
/* 114:165 */     query.setParameter("anio", Integer.valueOf(anio));
/* 115:166 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 116:167 */     return query.getResultList();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<GastoDeducibleSRI> obtenerListaPorAnio(int anio, int idOrganizacion, int idEmpleado)
/* 120:    */   {
/* 121:173 */     StringBuilder sql = new StringBuilder();
/* 122:174 */     sql.append(" SELECT g FROM  GastoDeducibleSRI g ");
/* 123:175 */     sql.append(" JOIN FETCH g.empleado e ");
/* 124:176 */     sql.append(" WHERE g.anio =:anio ");
/* 125:177 */     sql.append(" AND   e.idOrganizacion = :idOrganizacion ");
/* 126:178 */     if (idEmpleado != 0) {
/* 127:179 */       sql.append(" AND   e.idEmpleado = :idEmpleado ");
/* 128:    */     }
/* 129:181 */     sql.append(" ORDER BY e.apellidos, e.nombres ");
/* 130:    */     
/* 131:183 */     Query query = this.em.createQuery(sql.toString());
/* 132:184 */     query.setParameter("anio", Integer.valueOf(anio));
/* 133:185 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 134:186 */     if (idEmpleado != 0) {
/* 135:187 */       query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 136:    */     }
/* 137:189 */     return query.getResultList();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public BigDecimal obtenerPorEmpleadoAnio(int idEmpleado, int anio)
/* 141:    */   {
/* 142:200 */     Query query = this.em.createQuery("SELECT COALESCE(g.totalGastosDeducibles,0) FROM GastoDeducibleSRI g RIGHT JOIN g.empleado e WHERE e.idEmpleado = :idEmpleado AND g.anio = :anio");
/* 143:    */     
/* 144:202 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 145:203 */     query.setParameter("anio", Integer.valueOf(anio));
/* 146:    */     try
/* 147:    */     {
/* 148:206 */       return (BigDecimal)query.getSingleResult();
/* 149:    */     }
/* 150:    */     catch (NoResultException e) {}
/* 151:208 */     return BigDecimal.ZERO;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.GastoDeducibleSRIDao
 * JD-Core Version:    0.7.0.1
 */