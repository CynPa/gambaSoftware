/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Empresa;
/*  4:   */ import com.asinfo.as2.entities.FormaPagoSRI;
/*  5:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ import javax.persistence.TypedQuery;
/* 12:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 13:   */ import javax.persistence.criteria.CriteriaQuery;
/* 14:   */ import javax.persistence.criteria.Expression;
/* 15:   */ import javax.persistence.criteria.Predicate;
/* 16:   */ import javax.persistence.criteria.Root;
/* 17:   */ 
/* 18:   */ @Stateless
/* 19:   */ public class FormaPagoSRIDao
/* 20:   */   extends AbstractDaoAS2<FormaPagoSRI>
/* 21:   */ {
/* 22:   */   public FormaPagoSRIDao()
/* 23:   */   {
/* 24:43 */     super(FormaPagoSRI.class);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List<FormaPagoSRI> buscarFormaPagoSRI(Map<String, String> filters)
/* 28:   */     throws ExcepcionAS2
/* 29:   */   {
/* 30:47 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 31:48 */     CriteriaQuery<FormaPagoSRI> cq = cb.createQuery(FormaPagoSRI.class);
/* 32:49 */     Root<FormaPagoSRI> from = cq.from(FormaPagoSRI.class);
/* 33:   */     
/* 34:51 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, cb, from);
/* 35:52 */     cq.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 36:   */     
/* 37:54 */     CriteriaQuery<FormaPagoSRI> selectEmpresa = cq.select(from);
/* 38:55 */     return this.em.createQuery(selectEmpresa).getResultList();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public List<FormaPagoSRI> getListaFormaPagoSRI(Empresa empresa)
/* 42:   */   {
/* 43:61 */     StringBuffer sql = new StringBuffer();
/* 44:62 */     sql.append(" SELECT fpsri ");
/* 45:63 */     sql.append(" FROM FormaPagoSRI fpsri ");
/* 46:64 */     sql.append(" INNER JOIN fpsri.empresa emp ");
/* 47:65 */     sql.append(" WHERE emp = :empresa ");
/* 48:66 */     Query query = this.em.createQuery(sql.toString());
/* 49:67 */     query.setParameter("empresa", empresa);
/* 50:   */     
/* 51:69 */     return query.getResultList();
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.FormaPagoSRIDao
 * JD-Core Version:    0.7.0.1
 */