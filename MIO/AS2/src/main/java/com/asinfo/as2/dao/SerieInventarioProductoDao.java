/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.InventarioProducto;
/*  4:   */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class SerieInventarioProductoDao
/* 12:   */   extends AbstractDaoAS2<SerieInventarioProducto>
/* 13:   */ {
/* 14:   */   public SerieInventarioProductoDao()
/* 15:   */   {
/* 16:31 */     super(SerieInventarioProducto.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public InventarioProducto cargarDetalle(InventarioProducto inventarioProducto)
/* 20:   */   {
/* 21:35 */     StringBuilder sbSQL = new StringBuilder();
/* 22:36 */     sbSQL.append(" SELECT sip from SerieInventarioProducto sip ");
/* 23:37 */     sbSQL.append(" JOIN FETCH sip.serieProducto sp ");
/* 24:38 */     sbSQL.append(" WHERE sip.inventarioProducto = :inventarioProducto");
/* 25:   */     
/* 26:40 */     Query query = this.em.createQuery(sbSQL.toString());
/* 27:41 */     query.setParameter("inventarioProducto", inventarioProducto);
/* 28:   */     
/* 29:   */ 
/* 30:44 */     List<SerieInventarioProducto> listaSerie = query.getResultList();
/* 31:46 */     for (SerieInventarioProducto serieInventarioProducto : listaSerie)
/* 32:   */     {
/* 33:47 */       this.em.detach(serieInventarioProducto);
/* 34:48 */       serieInventarioProducto.setInventarioProducto(inventarioProducto);
/* 35:   */     }
/* 36:51 */     inventarioProducto.setListaSerieProducto(listaSerie);
/* 37:   */     
/* 38:53 */     return inventarioProducto;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SerieInventarioProductoDao
 * JD-Core Version:    0.7.0.1
 */