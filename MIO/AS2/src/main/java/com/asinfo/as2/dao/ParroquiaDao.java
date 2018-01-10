/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Organizacion;
/*   4:    */ import com.asinfo.as2.entities.Parroquia;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
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
/*  22:    */ public class ParroquiaDao
/*  23:    */   extends AbstractDaoAS2<Parroquia>
/*  24:    */ {
/*  25:    */   public ParroquiaDao()
/*  26:    */   {
/*  27: 32 */     super(Parroquia.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List<Parroquia> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  31:    */   {
/*  32: 42 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  33: 43 */     CriteriaQuery<Parroquia> criteriaQuery = criteriaBuilder.createQuery(Parroquia.class);
/*  34: 44 */     Root<Parroquia> from = criteriaQuery.from(Parroquia.class);
/*  35:    */     
/*  36: 46 */     from.fetch("ciudad", JoinType.LEFT).fetch("provincia", JoinType.LEFT);
/*  37:    */     
/*  38: 48 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  39: 49 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  40: 50 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  41:    */     
/*  42: 52 */     CriteriaQuery<Parroquia> select = criteriaQuery.select(from);
/*  43: 53 */     TypedQuery<Parroquia> typedQuery = this.em.createQuery(select);
/*  44: 54 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  45:    */     
/*  46: 56 */     return typedQuery.getResultList();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<Parroquia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters, int idCiudad)
/*  50:    */   {
/*  51: 69 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  52: 70 */     CriteriaQuery<Parroquia> criteriaQuery = criteriaBuilder.createQuery(Parroquia.class);
/*  53: 71 */     Root<Parroquia> from = criteriaQuery.from(Parroquia.class);
/*  54:    */     
/*  55: 73 */     Fetch<Object, Object> ciudad = from.fetch("ciudad", JoinType.LEFT);
/*  56: 74 */     ciudad.fetch("provincia", JoinType.LEFT);
/*  57:    */     
/*  58: 76 */     Path<Integer> pathIdCiudad = from.join("ciudad").get("idCiudad");
/*  59: 77 */     criteriaQuery.where(criteriaBuilder.equal(pathIdCiudad, Integer.valueOf(idCiudad)));
/*  60: 78 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  61: 79 */     CriteriaQuery<Parroquia> select = criteriaQuery.select(from);
/*  62: 80 */     TypedQuery<Parroquia> typedQuery = this.em.createQuery(select);
/*  63:    */     
/*  64: 82 */     return typedQuery.getResultList();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Parroquia buscarPorNombre(String nombre, Organizacion organizacion)
/*  68:    */   {
/*  69: 93 */     Map<String, String> filters = new HashMap();
/*  70: 94 */     filters.put("nombre", nombre);
/*  71: 95 */     filters.put("idOrganizacion", "" + organizacion.getId());
/*  72: 96 */     List<Parroquia> parroquia = obtenerListaCombo("nombre", false, filters);
/*  73: 98 */     if (parroquia.size() > 0) {
/*  74: 99 */       return (Parroquia)parroquia.get(0);
/*  75:    */     }
/*  76:101 */     return null;
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ParroquiaDao
 * JD-Core Version:    0.7.0.1
 */