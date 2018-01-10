/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   4:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Expression;
/*  15:    */ import javax.persistence.criteria.Fetch;
/*  16:    */ import javax.persistence.criteria.JoinType;
/*  17:    */ import javax.persistence.criteria.Predicate;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class PagoRolEmpleadoRubroDao
/*  22:    */   extends AbstractDaoAS2<PagoRolEmpleadoRubro>
/*  23:    */ {
/*  24:    */   public PagoRolEmpleadoRubroDao()
/*  25:    */   {
/*  26: 34 */     super(PagoRolEmpleadoRubro.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<PagoRolEmpleadoRubro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  30:    */   {
/*  31: 44 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  32: 45 */     CriteriaQuery<PagoRolEmpleadoRubro> criteriaQuery = criteriaBuilder.createQuery(PagoRolEmpleadoRubro.class);
/*  33: 46 */     Root<PagoRolEmpleadoRubro> from = criteriaQuery.from(PagoRolEmpleadoRubro.class);
/*  34:    */     
/*  35: 48 */     from.fetch("pagoRolEmpleado", JoinType.LEFT).fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  36: 49 */     from.fetch("rubro", JoinType.LEFT);
/*  37:    */     
/*  38: 51 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  39: 52 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  40:    */     
/*  41: 54 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  42:    */     
/*  43: 56 */     CriteriaQuery<PagoRolEmpleadoRubro> select = criteriaQuery.select(from);
/*  44: 57 */     TypedQuery<PagoRolEmpleadoRubro> typedQuery = this.em.createQuery(select);
/*  45: 58 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  46:    */     
/*  47: 60 */     return typedQuery.getResultList();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<PagoRolEmpleadoRubro> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  51:    */   {
/*  52: 69 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  53: 70 */     CriteriaQuery<PagoRolEmpleadoRubro> criteriaQuery = criteriaBuilder.createQuery(PagoRolEmpleadoRubro.class);
/*  54: 71 */     Root<PagoRolEmpleadoRubro> from = criteriaQuery.from(PagoRolEmpleadoRubro.class);
/*  55: 72 */     from.fetch("pagoRolEmpleado", JoinType.LEFT).fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  56: 73 */     from.fetch("rubro", JoinType.LEFT);
/*  57:    */     
/*  58: 75 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  59: 76 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  60:    */     
/*  61: 78 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  62:    */     
/*  63: 80 */     CriteriaQuery<PagoRolEmpleadoRubro> select = criteriaQuery.select(from);
/*  64: 81 */     TypedQuery<PagoRolEmpleadoRubro> typedQuery = this.em.createQuery(select);
/*  65:    */     
/*  66: 83 */     return typedQuery.getResultList();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List<PagoRolEmpleadoRubro> obtenerListaPorPagoRolRubro(int idPagoRol, int idRubro)
/*  70:    */   {
/*  71: 88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  72: 89 */     CriteriaQuery<PagoRolEmpleadoRubro> criteriaQuery = criteriaBuilder.createQuery(PagoRolEmpleadoRubro.class);
/*  73: 90 */     Root<PagoRolEmpleadoRubro> from = criteriaQuery.from(PagoRolEmpleadoRubro.class);
/*  74:    */     
/*  75: 92 */     from.fetch("rubro", JoinType.LEFT);
/*  76: 93 */     from.fetch("pagoRolEmpleado", JoinType.LEFT);
/*  77:    */     
/*  78: 95 */     HashMap<String, String> filters = new HashMap();
/*  79: 96 */     filters.put("pagoRolEmpleado.pagoRol.idPagoRol", String.valueOf(idPagoRol));
/*  80: 97 */     filters.put("rubro.idRubro", String.valueOf(idRubro));
/*  81:    */     
/*  82: 99 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  83:    */     
/*  84:101 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  85:    */     
/*  86:103 */     CriteriaQuery<PagoRolEmpleadoRubro> select = criteriaQuery.select(from);
/*  87:104 */     TypedQuery<PagoRolEmpleadoRubro> typedQuery = this.em.createQuery(select);
/*  88:    */     
/*  89:106 */     return typedQuery.getResultList();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubroFiniquito(HistoricoEmpleado historicoEmpleado)
/*  93:    */   {
/*  94:112 */     StringBuilder sql = new StringBuilder();
/*  95:113 */     sql.append(" SELECT prer ");
/*  96:114 */     sql.append(" FROM PagoRolEmpleadoRubro prer");
/*  97:115 */     sql.append(" JOIN FETCH prer.rubro r");
/*  98:116 */     sql.append(" LEFT JOIN FETCH r.rubroPadre pr");
/*  99:117 */     sql.append(" JOIN prer.pagoRolEmpleado pre");
/* 100:118 */     sql.append(" WHERE pre=:pagoRolEmpleado");
/* 101:119 */     Query query = this.em.createQuery(sql.toString());
/* 102:120 */     query.setParameter("pagoRolEmpleado", historicoEmpleado.getPagoRolEmpleadoFiniquito());
/* 103:121 */     return query.getResultList();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorRubro(int idPagoRol, int idRubro)
/* 107:    */   {
/* 108:126 */     Query query = this.em.createQuery(" SELECT prer FROM PagoRolEmpleadoRubro prer join FETCH prer.rubro r WHERE r.idRubro = :idRubro and prer.pagoRolEmpleado.pagoRol.idPagoRol = :idPagoRol");
/* 109:    */     
/* 110:128 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol)).setParameter("idRubro", Integer.valueOf(idRubro));
/* 111:    */     
/* 112:130 */     return query.getResultList();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorRubroYEmpleado(int idPagoRol, int idRubro, int idEmpleado)
/* 116:    */   {
/* 117:136 */     StringBuilder sql = new StringBuilder();
/* 118:137 */     sql.append("SELECT prer");
/* 119:138 */     sql.append(" FROM PagoRolEmpleadoRubro prer");
/* 120:139 */     sql.append(" JOIN FETCH prer.rubro r");
/* 121:140 */     sql.append(" JOIN prer.pagoRolEmpleado pre");
/* 122:141 */     sql.append(" JOIN pre.empleado e");
/* 123:142 */     sql.append(" WHERE r.idRubro = :idRubro");
/* 124:143 */     sql.append(" AND prer.pagoRolEmpleado.pagoRol.idPagoRol = :idPagoRol");
/* 125:144 */     sql.append(" AND e.idEmpleado = :idEmpleado");
/* 126:    */     
/* 127:146 */     Query query = this.em.createQuery(sql.toString());
/* 128:147 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/* 129:148 */     query.setParameter("idRubro", Integer.valueOf(idRubro));
/* 130:149 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 131:    */     
/* 132:151 */     return query.getResultList();
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoRolEmpleadoRubroDao
 * JD-Core Version:    0.7.0.1
 */