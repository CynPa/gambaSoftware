/*  1:   */ package com.asinfo.as2.dao.seguridad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.Query;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class AccionDao
/* 11:   */   extends AbstractDaoAS2<EntidadAccion>
/* 12:   */ {
/* 13:   */   public AccionDao()
/* 14:   */   {
/* 15:32 */     super(EntidadAccion.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public EntidadAccion obtenerAccionPorNombre(String nombre)
/* 19:   */   {
/* 20:42 */     StringBuffer sql = new StringBuffer();
/* 21:43 */     sql.append("SELECT ea FROM EntidadAccion ea ");
/* 22:44 */     sql.append(" WHERE ea.nombre = :nombre ");
/* 23:   */     
/* 24:46 */     Query query = this.em.createQuery(sql.toString());
/* 25:47 */     query.setParameter("nombre", nombre);
/* 26:   */     
/* 27:49 */     return (EntidadAccion)query.getSingleResult();
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.seguridad.AccionDao
 * JD-Core Version:    0.7.0.1
 */