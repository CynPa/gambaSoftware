/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EstadoCivil;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Path;
/* 12:   */ import javax.persistence.criteria.Root;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class EstadoCivilDao
/* 16:   */   extends AbstractDaoAS2<EstadoCivil>
/* 17:   */ {
/* 18:   */   public EstadoCivilDao()
/* 19:   */   {
/* 20:39 */     super(EstadoCivil.class);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public EstadoCivil cargarDetalle(int idEstadoCivil)
/* 24:   */   {
/* 25:49 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public EstadoCivil buscarPorNombre(String nombre)
/* 29:   */   {
/* 30:53 */     List<EstadoCivil> estadoCivil = new ArrayList();
/* 31:54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 32:55 */     CriteriaQuery<EstadoCivil> criteriaQuery = criteriaBuilder.createQuery(EstadoCivil.class);
/* 33:56 */     Root<EstadoCivil> from = criteriaQuery.from(EstadoCivil.class);
/* 34:   */     
/* 35:58 */     Path<String> pathNombre = from.get("nombre");
/* 36:59 */     criteriaQuery.where(criteriaBuilder.equal(pathNombre, nombre));
/* 37:60 */     CriteriaQuery<EstadoCivil> select = criteriaQuery.select(from);
/* 38:61 */     TypedQuery<EstadoCivil> typedQuery = this.em.createQuery(select);
/* 39:62 */     estadoCivil = typedQuery.getResultList();
/* 40:63 */     if (estadoCivil.size() > 0) {
/* 41:64 */       return (EstadoCivil)estadoCivil.get(0);
/* 42:   */     }
/* 43:66 */     return null;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EstadoCivilDao
 * JD-Core Version:    0.7.0.1
 */