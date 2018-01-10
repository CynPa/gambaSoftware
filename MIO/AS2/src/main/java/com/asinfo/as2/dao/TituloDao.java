/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Titulo;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ import javax.persistence.TypedQuery;
/*  7:   */ import javax.persistence.criteria.CriteriaBuilder;
/*  8:   */ import javax.persistence.criteria.CriteriaQuery;
/*  9:   */ import javax.persistence.criteria.Path;
/* 10:   */ import javax.persistence.criteria.Root;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class TituloDao
/* 14:   */   extends AbstractDaoAS2<Titulo>
/* 15:   */ {
/* 16:   */   public TituloDao()
/* 17:   */   {
/* 18:34 */     super(Titulo.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Titulo cargarDetalle(int idTitulo)
/* 22:   */   {
/* 23:44 */     return null;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Titulo buscarPorNombre(String nombre)
/* 27:   */   {
/* 28:48 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 29:49 */     CriteriaQuery<Titulo> criteriaQuery = criteriaBuilder.createQuery(Titulo.class);
/* 30:50 */     Root<Titulo> from = criteriaQuery.from(Titulo.class);
/* 31:   */     
/* 32:52 */     Path<String> pathNombre = from.get("nombre");
/* 33:53 */     criteriaQuery.where(criteriaBuilder.equal(pathNombre, nombre));
/* 34:54 */     CriteriaQuery<Titulo> select = criteriaQuery.select(from);
/* 35:55 */     TypedQuery<Titulo> typedQuery = this.em.createQuery(select);
/* 36:   */     
/* 37:57 */     return (Titulo)typedQuery.getSingleResult();
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TituloDao
 * JD-Core Version:    0.7.0.1
 */