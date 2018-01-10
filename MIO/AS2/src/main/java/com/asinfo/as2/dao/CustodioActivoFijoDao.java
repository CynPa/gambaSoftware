/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   4:    */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.UbicacionActivo;
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
/*  23:    */ public class CustodioActivoFijoDao
/*  24:    */   extends AbstractDaoAS2<CustodioActivoFijo>
/*  25:    */ {
/*  26:    */   public CustodioActivoFijoDao()
/*  27:    */   {
/*  28: 42 */     super(CustodioActivoFijo.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<CustodioActivoFijo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34: 55 */     CriteriaQuery<CustodioActivoFijo> criteriaQuery = criteriaBuilder.createQuery(CustodioActivoFijo.class);
/*  35: 56 */     Root<CustodioActivoFijo> from = criteriaQuery.from(CustodioActivoFijo.class);
/*  36: 57 */     from.fetch("activoFijo", JoinType.LEFT);
/*  37: 58 */     from.fetch("empresa", JoinType.LEFT);
/*  38:    */     
/*  39: 60 */     Fetch<Object, Object> ubicacion = from.fetch("ubicacionActivo", JoinType.LEFT);
/*  40: 61 */     ubicacion.fetch("sucursal", JoinType.LEFT);
/*  41: 62 */     ubicacion.fetch("departamento", JoinType.LEFT);
/*  42:    */     
/*  43: 64 */     Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/*  44: 65 */     empleado.fetch("empresa", JoinType.LEFT);
/*  45:    */     
/*  46: 67 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  47: 68 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  48:    */     
/*  49: 70 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  50:    */     
/*  51: 72 */     CriteriaQuery<CustodioActivoFijo> select = criteriaQuery.select(from);
/*  52: 73 */     TypedQuery<CustodioActivoFijo> typedQuery = this.em.createQuery(select);
/*  53: 74 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  54:    */     
/*  55: 76 */     return typedQuery.getResultList();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<CustodioActivoFijo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60: 88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  61: 89 */     CriteriaQuery<CustodioActivoFijo> criteriaQuery = criteriaBuilder.createQuery(CustodioActivoFijo.class);
/*  62: 90 */     Root<CustodioActivoFijo> from = criteriaQuery.from(CustodioActivoFijo.class);
/*  63:    */     
/*  64: 92 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  65: 93 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  66:    */     
/*  67: 95 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  68:    */     
/*  69: 97 */     CriteriaQuery<CustodioActivoFijo> select = criteriaQuery.select(from);
/*  70: 98 */     TypedQuery<CustodioActivoFijo> typedQuery = this.em.createQuery(select);
/*  71:    */     
/*  72:100 */     return typedQuery.getResultList();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public CustodioActivoFijo cargarDetalle(int idCustodioActivoFijo)
/*  76:    */   {
/*  77:110 */     CustodioActivoFijo custodioActivoFijo = (CustodioActivoFijo)buscarPorId(Integer.valueOf(idCustodioActivoFijo));
/*  78:111 */     custodioActivoFijo.getActivoFijo().getId();
/*  79:112 */     if (custodioActivoFijo.getEmpleado() != null) {
/*  80:113 */       custodioActivoFijo.getEmpleado().getId();
/*  81:    */     }
/*  82:115 */     if (custodioActivoFijo.getEmpresa() != null) {
/*  83:116 */       custodioActivoFijo.getEmpresa().getId();
/*  84:    */     }
/*  85:118 */     if (custodioActivoFijo.getUbicacionActivo() != null) {
/*  86:119 */       custodioActivoFijo.getUbicacionActivo().getId();
/*  87:    */     }
/*  88:121 */     return custodioActivoFijo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public CustodioActivoFijo buscarPorIdActivoFijoFechaFinNull(int idActivoFijo)
/*  92:    */   {
/*  93:133 */     CustodioActivoFijo custodioActivoFijo = null;
/*  94:    */     
/*  95:135 */     String sql1 = " SELECT COUNT(caf.idCustodioActivoFijo) FROM CustodioActivoFijo caf  INNER JOIN caf.activoFijo af  WHERE af.idActivoFijo = :idActivoFijo ";
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99:139 */     Query query1 = this.em.createQuery(sql1);
/* 100:140 */     query1.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 101:    */     
/* 102:142 */     Long numeroRegistros = (Long)query1.getSingleResult();
/* 103:144 */     if (numeroRegistros.longValue() > 0L)
/* 104:    */     {
/* 105:145 */       String sql = " SELECT caf FROM CustodioActivoFijo caf  INNER JOIN caf.activoFijo af  WHERE af.idActivoFijo = :idActivoFijo and caf.fechaFin IS NULL ";
/* 106:    */       
/* 107:    */ 
/* 108:148 */       Query query = this.em.createQuery(sql);
/* 109:149 */       query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 110:150 */       custodioActivoFijo = (CustodioActivoFijo)query.getSingleResult();
/* 111:    */     }
/* 112:153 */     return custodioActivoFijo;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CustodioActivoFijoDao
 * JD-Core Version:    0.7.0.1
 */