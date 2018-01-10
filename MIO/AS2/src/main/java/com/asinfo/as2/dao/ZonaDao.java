/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Sector;
/*  4:   */ import com.asinfo.as2.entities.Zona;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Root;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ZonaDao
/* 15:   */   extends AbstractDaoAS2<Zona>
/* 16:   */ {
/* 17:   */   public ZonaDao()
/* 18:   */   {
/* 19:36 */     super(Zona.class);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Zona cargarDetalle(int idZona)
/* 23:   */   {
/* 24:42 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 25:43 */     CriteriaQuery<Zona> cq = cb.createQuery(Zona.class);
/* 26:   */     
/* 27:45 */     Root<Zona> from = cq.from(Zona.class);
/* 28:46 */     cq.where(cb.equal(from.get("idZona"), Integer.valueOf(idZona)));
/* 29:47 */     Zona zona = (Zona)this.em.createQuery(cq.select(from)).getSingleResult();
/* 30:   */     
/* 31:49 */     this.em.detach(zona);
/* 32:50 */     CriteriaQuery<Sector> cqSector = cb.createQuery(Sector.class);
/* 33:51 */     Root<Sector> detalle = cqSector.from(Sector.class);
/* 34:52 */     cqSector.where(cb.equal(detalle.get("zona"), zona));
/* 35:53 */     List<Sector> listaSector = this.em.createQuery(cqSector).getResultList();
/* 36:54 */     zona.setListaSector(listaSector);
/* 37:56 */     for (Sector sector : zona.getListaSector())
/* 38:   */     {
/* 39:57 */       this.em.detach(sector);
/* 40:58 */       sector.setZona(zona);
/* 41:   */     }
/* 42:61 */     return zona;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ZonaDao
 * JD-Core Version:    0.7.0.1
 */