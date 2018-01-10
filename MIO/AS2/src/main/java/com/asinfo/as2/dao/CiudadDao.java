/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Ciudad;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.Query;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  12:    */ import javax.persistence.criteria.CriteriaQuery;
/*  13:    */ import javax.persistence.criteria.Expression;
/*  14:    */ import javax.persistence.criteria.Fetch;
/*  15:    */ import javax.persistence.criteria.Join;
/*  16:    */ import javax.persistence.criteria.JoinType;
/*  17:    */ import javax.persistence.criteria.Path;
/*  18:    */ import javax.persistence.criteria.Predicate;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class CiudadDao
/*  23:    */   extends AbstractDaoAS2<Ciudad>
/*  24:    */ {
/*  25:    */   public CiudadDao()
/*  26:    */   {
/*  27: 44 */     super(Ciudad.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List<Ciudad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters, int idProvincia)
/*  31:    */   {
/*  32: 49 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  33: 50 */     CriteriaQuery<Ciudad> criteriaQuery = criteriaBuilder.createQuery(Ciudad.class);
/*  34: 51 */     Root<Ciudad> from = criteriaQuery.from(Ciudad.class);
/*  35:    */     
/*  36: 53 */     Fetch<Object, Object> provincia = from.fetch("provincia", JoinType.LEFT);
/*  37: 54 */     provincia.fetch("pais", JoinType.LEFT);
/*  38:    */     
/*  39: 56 */     Path<Integer> pathIdProvincia = from.join("provincia").get("idProvincia");
/*  40: 57 */     criteriaQuery.where(criteriaBuilder.equal(pathIdProvincia, Integer.valueOf(idProvincia)));
/*  41: 58 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  42: 59 */     CriteriaQuery<Ciudad> select = criteriaQuery.select(from);
/*  43: 60 */     TypedQuery<Ciudad> typedQuery = this.em.createQuery(select);
/*  44:    */     
/*  45: 62 */     return typedQuery.getResultList();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Ciudad buscarPorNombre(String nombre)
/*  49:    */   {
/*  50: 72 */     Map<String, String> filters = new HashMap();
/*  51: 73 */     filters.put("nombre", nombre);
/*  52: 74 */     List<Ciudad> ciudad = obtenerListaCombo("nombre", false, filters);
/*  53: 76 */     if (ciudad.size() > 0) {
/*  54: 77 */       return (Ciudad)ciudad.get(0);
/*  55:    */     }
/*  56: 79 */     return null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<Ciudad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  60:    */   {
/*  61: 84 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  62: 85 */     CriteriaQuery<Ciudad> criteriaQuery = criteriaBuilder.createQuery(Ciudad.class);
/*  63: 86 */     Root<Ciudad> from = criteriaQuery.from(Ciudad.class);
/*  64:    */     
/*  65: 88 */     Fetch<Object, Object> provincia = from.fetch("provincia", JoinType.LEFT);
/*  66: 89 */     provincia.fetch("pais", JoinType.LEFT);
/*  67:    */     
/*  68: 91 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  69: 92 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  70:    */     
/*  71: 94 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  72:    */     
/*  73: 96 */     CriteriaQuery<Ciudad> select = criteriaQuery.select(from);
/*  74:    */     
/*  75: 98 */     TypedQuery<Ciudad> typedQuery = this.em.createQuery(select);
/*  76: 99 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  77:    */     
/*  78:101 */     return typedQuery.getResultList();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Ciudad getCiudadDeSucursal(int idSucursal)
/*  82:    */   {
/*  83:105 */     StringBuilder sql = new StringBuilder();
/*  84:106 */     sql.append(" SELECT c FROM Sucursal s ");
/*  85:107 */     sql.append(" INNER JOIN s.ciudad c ");
/*  86:108 */     sql.append(" WHERE s.idSucursal = :idSucursal ");
/*  87:109 */     Query query = this.em.createQuery(sql.toString());
/*  88:110 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  89:111 */     return (Ciudad)query.getSingleResult();
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CiudadDao
 * JD-Core Version:    0.7.0.1
 */