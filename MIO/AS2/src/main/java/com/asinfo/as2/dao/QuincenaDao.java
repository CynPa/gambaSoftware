/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Quincena;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ import javax.persistence.Query;
/*  7:   */ 
/*  8:   */ @Stateless
/*  9:   */ public class QuincenaDao
/* 10:   */   extends AbstractDaoAS2<Quincena>
/* 11:   */ {
/* 12:   */   public QuincenaDao()
/* 13:   */   {
/* 14:31 */     super(Quincena.class);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Quincena cargarDetalle(int idQuincena)
/* 18:   */   {
/* 19:41 */     return null;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Quincena obtenerQuincenaPorCodigo(String codigo)
/* 23:   */   {
/* 24:51 */     String sql = "SELECT q FROM Quincena q WHERE q.codigo = :codigo";
/* 25:52 */     Query query = this.em.createQuery(sql);
/* 26:53 */     query.setParameter("codigo", codigo);
/* 27:54 */     return (Quincena)query.getSingleResult();
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.QuincenaDao
 * JD-Core Version:    0.7.0.1
 */