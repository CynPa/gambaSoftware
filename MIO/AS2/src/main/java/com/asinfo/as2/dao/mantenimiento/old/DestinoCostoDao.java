/*  1:   */ package com.asinfo.as2.dao.mantenimiento.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.DestinoCosto;
/*  5:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.NoResultException;
/* 12:   */ import javax.persistence.TypedQuery;
/* 13:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 14:   */ import javax.persistence.criteria.CriteriaQuery;
/* 15:   */ import javax.persistence.criteria.Expression;
/* 16:   */ import javax.persistence.criteria.Predicate;
/* 17:   */ import javax.persistence.criteria.Root;
/* 18:   */ 
/* 19:   */ @Stateless
/* 20:   */ public class DestinoCostoDao
/* 21:   */   extends AbstractDaoAS2<DestinoCosto>
/* 22:   */ {
/* 23:   */   public DestinoCostoDao()
/* 24:   */   {
/* 25:46 */     super(DestinoCosto.class);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public DestinoCosto buscarPorCodigo(String codigo)
/* 29:   */     throws ExcepcionAS2
/* 30:   */   {
/* 31:   */     try
/* 32:   */     {
/* 33:58 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 34:59 */       CriteriaQuery<DestinoCosto> criteriaQuery = criteriaBuilder.createQuery(DestinoCosto.class);
/* 35:60 */       Root<DestinoCosto> from = criteriaQuery.from(DestinoCosto.class);
/* 36:   */       
/* 37:62 */       Map<String, String> filters = new HashMap();
/* 38:63 */       filters.put("codigo", "=" + codigo);
/* 39:64 */       List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 40:65 */       criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 41:   */       
/* 42:67 */       CriteriaQuery<DestinoCosto> select = criteriaQuery.select(from);
/* 43:68 */       TypedQuery<DestinoCosto> typedQuery = this.em.createQuery(select);
/* 44:   */       
/* 45:70 */       return (DestinoCosto)typedQuery.getSingleResult();
/* 46:   */     }
/* 47:   */     catch (NoResultException e)
/* 48:   */     {
/* 49:73 */       throw new ExcepcionAS2("msg_info_destino_costo_no_encontrado", " " + codigo);
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public List<DestinoCosto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 54:   */   {
/* 55:79 */     filters = agregarFiltrosOrganizacion(filters);
/* 56:   */     
/* 57:81 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 58:82 */     CriteriaQuery<DestinoCosto> criteriaQuery = criteriaBuilder.createQuery(DestinoCosto.class);
/* 59:83 */     Root<DestinoCosto> from = criteriaQuery.from(DestinoCosto.class);
/* 60:   */     
/* 61:85 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 62:86 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 63:   */     
/* 64:88 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 65:   */     
/* 66:90 */     CriteriaQuery<DestinoCosto> select = criteriaQuery.select(from);
/* 67:91 */     TypedQuery<DestinoCosto> typedQuery = this.em.createQuery(select);
/* 68:   */     
/* 69:93 */     return typedQuery.getResultList();
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.DestinoCostoDao
 * JD-Core Version:    0.7.0.1
 */