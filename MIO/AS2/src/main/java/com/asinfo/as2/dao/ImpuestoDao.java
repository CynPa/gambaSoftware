/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaContable;
/*  4:   */ import com.asinfo.as2.entities.Impuesto;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ import javax.persistence.TypedQuery;
/* 10:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 11:   */ import javax.persistence.criteria.CriteriaQuery;
/* 12:   */ import javax.persistence.criteria.JoinType;
/* 13:   */ import javax.persistence.criteria.Predicate;
/* 14:   */ import javax.persistence.criteria.Root;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class ImpuestoDao
/* 18:   */   extends AbstractDaoAS2<Impuesto>
/* 19:   */ {
/* 20:   */   public ImpuestoDao()
/* 21:   */   {
/* 22:36 */     super(Impuesto.class);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Impuesto cargarDetalle(int idImpuesto)
/* 26:   */   {
/* 27:41 */     Impuesto impuesto = (Impuesto)buscarPorId(Integer.valueOf(idImpuesto));
/* 28:   */     
/* 29:43 */     impuesto.getListaCategoriaImpuesto().size();
/* 30:46 */     if (impuesto.getCuentaContableVenta() != null) {
/* 31:47 */       impuesto.getCuentaContableVenta().getId();
/* 32:   */     }
/* 33:51 */     if (impuesto.getCuentaContableCompra() != null) {
/* 34:52 */       impuesto.getCuentaContableCompra().getId();
/* 35:   */     }
/* 36:56 */     if (impuesto.getCuentaContableRedondeo() != null) {
/* 37:57 */       impuesto.getCuentaContableRedondeo().getId();
/* 38:   */     }
/* 39:61 */     impuesto.getListaRangoImpuesto().size();
/* 40:   */     
/* 41:63 */     return impuesto;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean getIndicadorTributario()
/* 45:   */   {
/* 46:68 */     String sql = " SELECT CASE WHEN COUNT(i.idImpuesto) >= 1 THEN true ELSE false END FROM Impuesto i  WHERE i.indicadorImpuestoTributario = true ";
/* 47:   */     
/* 48:70 */     Query query = this.em.createQuery(sql);
/* 49:   */     
/* 50:72 */     return ((Boolean)query.getSingleResult()).booleanValue();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Impuesto buscarPorCodigo(String codigo, int idOrganizacion)
/* 54:   */   {
/* 55:76 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 56:77 */     CriteriaQuery<Impuesto> criteriaQuery = criteriaBuilder.createQuery(Impuesto.class);
/* 57:78 */     Root<Impuesto> from = criteriaQuery.from(Impuesto.class);
/* 58:79 */     from.fetch("cuentaContableCompra", JoinType.LEFT);
/* 59:80 */     criteriaQuery.where(new Predicate[] { criteriaBuilder.equal(from.get("codigo"), codigo), criteriaBuilder.equal(from.get("idOrganizacion"), Integer.valueOf(idOrganizacion)) });
/* 60:81 */     CriteriaQuery<Impuesto> select = criteriaQuery.select(from);
/* 61:82 */     TypedQuery<Impuesto> typedQuery = this.em.createQuery(select);
/* 62:83 */     return (Impuesto)typedQuery.getSingleResult();
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ImpuestoDao
 * JD-Core Version:    0.7.0.1
 */