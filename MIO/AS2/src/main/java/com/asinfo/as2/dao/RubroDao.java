/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.Quincena;
/*   5:    */ import com.asinfo.as2.entities.Rubro;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.NoResultException;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ import javax.persistence.TypedQuery;
/*  14:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  15:    */ import javax.persistence.criteria.CriteriaQuery;
/*  16:    */ import javax.persistence.criteria.Expression;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Predicate;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class RubroDao
/*  23:    */   extends AbstractDaoAS2<Rubro>
/*  24:    */ {
/*  25:    */   public RubroDao()
/*  26:    */   {
/*  27: 45 */     super(Rubro.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Rubro cargarDetalle(int idRubro)
/*  31:    */   {
/*  32: 56 */     Rubro rubro = (Rubro)buscarPorId(Integer.valueOf(idRubro));
/*  33: 57 */     if (rubro.getQuincena() != null) {
/*  34: 58 */       rubro.getQuincena().getId();
/*  35:    */     }
/*  36: 60 */     if (rubro.getCuentaContableProvision() != null) {
/*  37: 61 */       rubro.getCuentaContableProvision().getId();
/*  38:    */     }
/*  39: 63 */     if (rubro.getRubroPadre() != null) {
/*  40: 64 */       rubro.getRubroPadre().getId();
/*  41:    */     }
/*  42: 66 */     return rubro;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<Rubro> getLisRubro()
/*  46:    */   {
/*  47: 70 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  48: 71 */     CriteriaQuery<Rubro> rubroCriteriaQuery = criteriaBuilder.createQuery(Rubro.class);
/*  49: 72 */     Root<Rubro> rubroRoot = rubroCriteriaQuery.from(Rubro.class);
/*  50:    */     
/*  51: 74 */     CriteriaQuery<Rubro> selectRubro = rubroCriteriaQuery.select(rubroRoot);
/*  52: 75 */     TypedQuery<Rubro> rubroTypedQuery = this.em.createQuery(selectRubro);
/*  53:    */     
/*  54: 77 */     return rubroTypedQuery.getResultList();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<Rubro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  58:    */   {
/*  59: 81 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  60: 82 */     CriteriaQuery<Rubro> criteriaQuery = criteriaBuilder.createQuery(Rubro.class);
/*  61: 83 */     Root<Rubro> from = criteriaQuery.from(Rubro.class);
/*  62:    */     
/*  63: 85 */     from.fetch("quincena", JoinType.LEFT);
/*  64:    */     
/*  65: 87 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  66: 88 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  67:    */     
/*  68: 90 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  69:    */     
/*  70: 92 */     CriteriaQuery<Rubro> select = criteriaQuery.select(from);
/*  71:    */     
/*  72: 94 */     TypedQuery<Rubro> typedQuery = this.em.createQuery(select);
/*  73: 95 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  74:    */     
/*  75: 97 */     return typedQuery.getResultList();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Rubro obtenerRubroPorTipoRubro(TipoRubroEnum tipoRubro, int idOrganizacion)
/*  79:    */   {
/*  80:102 */     StringBuilder sql = new StringBuilder();
/*  81:103 */     sql.append(" SELECT r FROM Rubro r ");
/*  82:104 */     sql.append(" WHERE r.idOrganizacion = :idOrganizacion");
/*  83:105 */     sql.append(" AND r.tipo = :tipoRubro");
/*  84:    */     
/*  85:107 */     Query query = this.em.createQuery(sql.toString());
/*  86:108 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  87:109 */     query.setParameter("tipoRubro", tipoRubro);
/*  88:    */     
/*  89:111 */     Rubro rubro = null;
/*  90:    */     try
/*  91:    */     {
/*  92:113 */       rubro = (Rubro)query.getSingleResult();
/*  93:    */     }
/*  94:    */     catch (NoResultException e)
/*  95:    */     {
/*  96:115 */       rubro = null;
/*  97:    */     }
/*  98:118 */     return rubro;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<Rubro> getListaRubros(int idCRubro)
/* 102:    */   {
/* 103:124 */     StringBuilder SQL_QUERY = new StringBuilder();
/* 104:125 */     SQL_QUERY.append(" SELECT r from CategoriaRubroRubro crr inner join crr.rubro r ");
/* 105:126 */     SQL_QUERY.append(" where crr.categoriaRubro.idCategoriaRubro = :idCategoriaRubro ");
/* 106:127 */     Query query = this.em.createQuery(SQL_QUERY.toString());
/* 107:128 */     query.setParameter("idCategoriaRubro", Integer.valueOf(idCRubro));
/* 108:129 */     return query.getResultList();
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RubroDao
 * JD-Core Version:    0.7.0.1
 */