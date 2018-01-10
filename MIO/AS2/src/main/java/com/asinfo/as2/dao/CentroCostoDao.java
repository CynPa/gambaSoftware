/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CentroCosto;
/*   4:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.NoResultException;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Fetch;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Predicate;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class CentroCostoDao
/*  23:    */   extends AbstractDaoAS2<CentroCosto>
/*  24:    */ {
/*  25:    */   public CentroCostoDao()
/*  26:    */   {
/*  27: 46 */     super(CentroCosto.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CentroCosto cargarDetalle(int idCentroCosto)
/*  31:    */   {
/*  32: 56 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  33: 57 */     CriteriaQuery<CentroCosto> criteriaQuery = criteriaBuilder.createQuery(CentroCosto.class);
/*  34: 58 */     Root<CentroCosto> from = criteriaQuery.from(CentroCosto.class);
/*  35:    */     
/*  36: 60 */     from.fetch("nivelCentroCosto", JoinType.LEFT);
/*  37: 61 */     Fetch<Object, Object> centroCostoPadre = from.fetch("centroCostoPadre", JoinType.LEFT);
/*  38: 62 */     centroCostoPadre.fetch("nivelCentroCosto", JoinType.LEFT);
/*  39:    */     
/*  40: 64 */     Map<String, String> filters = new HashMap();
/*  41: 65 */     filters.put("idCentroCosto", String.valueOf(idCentroCosto));
/*  42:    */     
/*  43: 67 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  44: 68 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  45:    */     
/*  46: 70 */     CriteriaQuery<CentroCosto> select = criteriaQuery.select(from);
/*  47:    */     
/*  48: 72 */     TypedQuery<CentroCosto> typedQuery = this.em.createQuery(select);
/*  49:    */     
/*  50: 74 */     return (CentroCosto)typedQuery.getSingleResult();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<CentroCosto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  54:    */   {
/*  55: 83 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  56: 84 */     CriteriaQuery<CentroCosto> criteriaQuery = criteriaBuilder.createQuery(CentroCosto.class);
/*  57: 85 */     Root<CentroCosto> from = criteriaQuery.from(CentroCosto.class);
/*  58:    */     
/*  59: 87 */     from.fetch("nivelCentroCosto", JoinType.LEFT);
/*  60:    */     
/*  61: 89 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  62: 90 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  63:    */     
/*  64: 92 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  65:    */     
/*  66: 94 */     CriteriaQuery<CentroCosto> select = criteriaQuery.select(from);
/*  67:    */     
/*  68: 96 */     TypedQuery<CentroCosto> typedQuery = this.em.createQuery(select);
/*  69:    */     
/*  70: 98 */     return typedQuery.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<CentroCosto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  74:    */   {
/*  75:108 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  76:109 */     CriteriaQuery<CentroCosto> criteriaQuery = criteriaBuilder.createQuery(CentroCosto.class);
/*  77:110 */     Root<CentroCosto> from = criteriaQuery.from(CentroCosto.class);
/*  78:    */     
/*  79:112 */     from.fetch("nivelCentroCosto", JoinType.LEFT);
/*  80:    */     
/*  81:114 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  82:115 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  83:    */     
/*  84:117 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  85:    */     
/*  86:119 */     CriteriaQuery<CentroCosto> select = criteriaQuery.select(from);
/*  87:    */     
/*  88:121 */     TypedQuery<CentroCosto> typedQuery = this.em.createQuery(select);
/*  89:122 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  90:    */     
/*  91:124 */     return typedQuery.getResultList();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public CentroCosto buscarPorCodigo(String codigo)
/*  95:    */     throws ExcepcionAS2
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:136 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 100:137 */       CriteriaQuery<CentroCosto> criteriaQuery = criteriaBuilder.createQuery(CentroCosto.class);
/* 101:138 */       Root<CentroCosto> from = criteriaQuery.from(CentroCosto.class);
/* 102:    */       
/* 103:140 */       Map<String, String> filters = new HashMap();
/* 104:141 */       filters.put("codigo", "=" + codigo);
/* 105:142 */       List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 106:143 */       criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 107:    */       
/* 108:145 */       CriteriaQuery<CentroCosto> select = criteriaQuery.select(from);
/* 109:146 */       TypedQuery<CentroCosto> typedQuery = this.em.createQuery(select);
/* 110:    */       
/* 111:148 */       return (CentroCosto)typedQuery.getSingleResult();
/* 112:    */     }
/* 113:    */     catch (NoResultException e)
/* 114:    */     {
/* 115:151 */       throw new ExcepcionAS2("msg_info_centro_costo_no_encontrado", " " + codigo);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<CentroCosto> buscarPorCodigoNivelCentroCosto(int codigo)
/* 120:    */     throws ExcepcionAS2
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:166 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 125:167 */       CriteriaQuery<CentroCosto> criteriaQuery = criteriaBuilder.createQuery(CentroCosto.class);
/* 126:168 */       Root<CentroCosto> from = criteriaQuery.from(CentroCosto.class);
/* 127:    */       
/* 128:170 */       Map<String, String> filters = new HashMap();
/* 129:171 */       filters.put("nivelCentroCosto.codigo", "=" + codigo);
/* 130:172 */       List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 131:173 */       criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 132:    */       
/* 133:175 */       CriteriaQuery<CentroCosto> select = criteriaQuery.select(from);
/* 134:176 */       TypedQuery<CentroCosto> typedQuery = this.em.createQuery(select);
/* 135:    */       
/* 136:178 */       return typedQuery.getResultList();
/* 137:    */     }
/* 138:    */     catch (NoResultException e)
/* 139:    */     {
/* 140:181 */       throw new ExcepcionAS2("msg_info_centro_costo_no_encontrado", " " + codigo);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<CentroCosto> buscarPorNivel(int codigo)
/* 145:    */   {
/* 146:193 */     return this.em.createQuery("SELECT c FROM CentroCosto c WHERE c.nivelCentroCosto.codigo =:codigo").setParameter("codigo", Integer.valueOf(codigo)).getResultList();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<CentroCosto> ObtenerListaComboPorBodega(int idBodega)
/* 150:    */   {
/* 151:198 */     String sql = "SELECT c FROM BodegaCentroCosto b JOIN b.centroCosto c WHERE b.bodega.idBodega = :idBodega";
/* 152:199 */     Query query = this.em.createQuery(sql);
/* 153:200 */     query.setParameter("idBodega", Integer.valueOf(idBodega));
/* 154:201 */     return query.getResultList();
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CentroCostoDao
 * JD-Core Version:    0.7.0.1
 */