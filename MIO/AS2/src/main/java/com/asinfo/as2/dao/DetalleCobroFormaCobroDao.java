/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleCobro;
/*  4:   */ import com.asinfo.as2.entities.DetalleCobroFormaCobro;
/*  5:   */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class DetalleCobroFormaCobroDao
/* 13:   */   extends AbstractDaoAS2<DetalleCobroFormaCobro>
/* 14:   */ {
/* 15:   */   public DetalleCobroFormaCobroDao()
/* 16:   */   {
/* 17:35 */     super(DetalleCobroFormaCobro.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<DetalleCobroFormaCobro> obtenerLista(DetalleFormaCobro detalleFormaCobro, DetalleCobro detalleCobro)
/* 21:   */   {
/* 22:39 */     StringBuilder sql = new StringBuilder();
/* 23:40 */     sql.append(" SELECT dcfc FROM DetalleCobroFormaCobro dcfc");
/* 24:41 */     sql.append(" LEFT JOIN FETCH dcfc.detalleFormaCobro  dfc");
/* 25:42 */     sql.append(" LEFT JOIN FETCH dcfc.detalleCobro  dc");
/* 26:43 */     if (detalleFormaCobro != null)
/* 27:   */     {
/* 28:44 */       sql.append(" WHERE  dfc.idDetalleFormaCobro = :idDetalleFormaCobro");
/* 29:45 */       if (detalleCobro != null) {
/* 30:46 */         sql.append(" AND ");
/* 31:   */       }
/* 32:   */     }
/* 33:49 */     else if (detalleCobro != null)
/* 34:   */     {
/* 35:50 */       sql.append(" WHERE ");
/* 36:   */     }
/* 37:53 */     if (detalleCobro != null) {
/* 38:54 */       sql.append(" dc.idDetalleCobro = :idDetalleCobro");
/* 39:   */     }
/* 40:56 */     Query query = this.em.createQuery(sql.toString());
/* 41:57 */     if (detalleFormaCobro != null) {
/* 42:58 */       query.setParameter("idDetalleFormaCobro", Integer.valueOf(detalleFormaCobro.getId()));
/* 43:   */     }
/* 44:60 */     if (detalleCobro != null) {
/* 45:61 */       query.setParameter("idDetalleCobro", Integer.valueOf(detalleCobro.getId()));
/* 46:   */     }
/* 47:64 */     return query.getResultList();
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleCobroFormaCobroDao
 * JD-Core Version:    0.7.0.1
 */