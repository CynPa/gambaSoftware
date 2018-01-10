/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ProductoAtributo;
/*  4:   */ import com.asinfo.as2.entities.ValorAtributo;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ import javax.persistence.TypedQuery;
/* 11:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 12:   */ import javax.persistence.criteria.CriteriaQuery;
/* 13:   */ import javax.persistence.criteria.Expression;
/* 14:   */ import javax.persistence.criteria.JoinType;
/* 15:   */ import javax.persistence.criteria.Predicate;
/* 16:   */ import javax.persistence.criteria.Root;
/* 17:   */ 
/* 18:   */ @Stateless
/* 19:   */ public class ProductoAtributoDao
/* 20:   */   extends AbstractDaoAS2<ProductoAtributo>
/* 21:   */ {
/* 22:   */   public ProductoAtributoDao()
/* 23:   */   {
/* 24:38 */     super(ProductoAtributo.class);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void guardar(ProductoAtributo productoAtributo)
/* 28:   */   {
/* 29:48 */     if (productoAtributo.getValorAtributo() != null) {
/* 30:49 */       productoAtributo.setValor(productoAtributo.getValorAtributo().getNombre());
/* 31:   */     }
/* 32:51 */     super.guardar(productoAtributo);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public List<ProductoAtributo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 36:   */   {
/* 37:62 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 38:63 */     CriteriaQuery<ProductoAtributo> criteriaQuery = criteriaBuilder.createQuery(ProductoAtributo.class);
/* 39:64 */     Root<ProductoAtributo> from = criteriaQuery.from(ProductoAtributo.class);
/* 40:   */     
/* 41:66 */     from.fetch("atributo", JoinType.LEFT);
/* 42:67 */     from.fetch("producto", JoinType.LEFT);
/* 43:   */     
/* 44:69 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 45:70 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 46:71 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 47:   */     
/* 48:73 */     CriteriaQuery<ProductoAtributo> select = criteriaQuery.select(from);
/* 49:74 */     TypedQuery<ProductoAtributo> typedQuery = this.em.createQuery(select);
/* 50:   */     
/* 51:76 */     return typedQuery.getResultList();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void actualizarProductoAtributo(ValorAtributo valorAtributo)
/* 55:   */   {
/* 56:80 */     StringBuilder sql = new StringBuilder();
/* 57:81 */     sql.append(" UPDATE ProductoAtributo pa SET pa.valor = :valorAtributoNombre ");
/* 58:82 */     sql.append(" WHERE pa.valor != :valorAtributoNombre ");
/* 59:83 */     sql.append(" AND pa.valorAtributo = :valorAtributo");
/* 60:84 */     Query query = this.em.createQuery(sql.toString());
/* 61:85 */     query.setParameter("valorAtributoNombre", valorAtributo.getNombre());
/* 62:86 */     query.setParameter("valorAtributo", valorAtributo);
/* 63:87 */     query.executeUpdate();
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProductoAtributoDao
 * JD-Core Version:    0.7.0.1
 */