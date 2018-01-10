/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleCobro;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.Query;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class DetalleCobroDao
/* 11:   */   extends AbstractDaoAS2<DetalleCobro>
/* 12:   */ {
/* 13:   */   public DetalleCobroDao()
/* 14:   */   {
/* 15:31 */     super(DetalleCobro.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public List<DetalleCobro> obtenerDetalleCobro(int idCuentaPorCobrar)
/* 19:   */   {
/* 20:36 */     StringBuilder sql = new StringBuilder();
/* 21:37 */     sql.append("SELECT dc FROM DetalleCobro dc LEFT JOIN FETCH dc.cuentaPorCobrar cxc");
/* 22:38 */     sql.append(" WHERE  cxc.idCuentaPorCobrar = :idCuentaPorCobrar");
/* 23:39 */     Query query = this.em.createQuery(sql.toString());
/* 24:40 */     query.setParameter("idCuentaPorCobrar", Integer.valueOf(idCuentaPorCobrar));
/* 25:41 */     return query.getResultList();
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleCobroDao
 * JD-Core Version:    0.7.0.1
 */