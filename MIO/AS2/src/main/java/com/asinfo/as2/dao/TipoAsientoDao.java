/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   4:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.NoResultException;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Expression;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Predicate;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class TipoAsientoDao
/*  21:    */   extends AbstractDaoAS2<TipoAsiento>
/*  22:    */ {
/*  23:    */   public TipoAsientoDao()
/*  24:    */   {
/*  25: 45 */     super(TipoAsiento.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List<TipoAsiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  29:    */   {
/*  30: 57 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  31: 58 */     CriteriaQuery<TipoAsiento> criteriaQuery = criteriaBuilder.createQuery(TipoAsiento.class);
/*  32:    */     
/*  33: 60 */     Root<TipoAsiento> from = criteriaQuery.from(TipoAsiento.class);
/*  34: 61 */     from.fetch("secuencia", JoinType.LEFT);
/*  35:    */     
/*  36: 63 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  37: 64 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  38: 65 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  39:    */     
/*  40: 67 */     CriteriaQuery<TipoAsiento> select = criteriaQuery.select(from);
/*  41:    */     
/*  42: 69 */     TypedQuery<TipoAsiento> typedQuery = this.em.createQuery(select);
/*  43:    */     
/*  44: 71 */     return typedQuery.getResultList();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<TipoAsiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 77 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  50: 78 */     CriteriaQuery<TipoAsiento> criteriaQuery = criteriaBuilder.createQuery(TipoAsiento.class);
/*  51:    */     
/*  52: 80 */     Root<TipoAsiento> from = criteriaQuery.from(TipoAsiento.class);
/*  53: 81 */     from.fetch("secuencia", JoinType.LEFT);
/*  54:    */     
/*  55: 83 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  56: 84 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  57: 85 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  58:    */     
/*  59: 87 */     CriteriaQuery<TipoAsiento> select = criteriaQuery.select(from);
/*  60:    */     
/*  61: 89 */     TypedQuery<TipoAsiento> typedQuery = this.em.createQuery(select);
/*  62: 90 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  63:    */     
/*  64: 92 */     return typedQuery.getResultList();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public TipoAsiento buscarPorNombre(String nombre, int idOrganizacion)
/*  68:    */     throws ExcepcionAS2Financiero
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 99 */       StringBuilder sql = new StringBuilder();
/*  73:100 */       sql.append(" SELECT ta FROM TipoAsiento ta ");
/*  74:101 */       sql.append(" JOIN FETCH ta.secuencia ");
/*  75:102 */       sql.append(" WHERE ta.nombre = :nombre ");
/*  76:103 */       sql.append(" AND ta.idOrganizacion = :idOrganizacion ");
/*  77:    */       
/*  78:105 */       Query query = this.em.createQuery(sql.toString());
/*  79:106 */       query.setParameter("nombre", nombre);
/*  80:107 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  81:108 */       return (TipoAsiento)query.getSingleResult();
/*  82:    */     }
/*  83:    */     catch (NoResultException e)
/*  84:    */     {
/*  85:111 */       throw new ExcepcionAS2Financiero("msg_info_cuenta_contable_0002", " " + nombre);
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoAsientoDao
 * JD-Core Version:    0.7.0.1
 */