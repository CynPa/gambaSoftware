/*  1:   */ package com.asinfo.as2.dao.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  5:   */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  6:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.NoResultException;
/* 11:   */ import javax.persistence.Query;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class TipoComprobanteSRIDao
/* 15:   */   extends AbstractDaoAS2<TipoComprobanteSRI>
/* 16:   */ {
/* 17:   */   public TipoComprobanteSRIDao()
/* 18:   */   {
/* 19:38 */     super(TipoComprobanteSRI.class);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public TipoComprobanteSRI buscarPorCodigo(String codigo)
/* 23:   */     throws ExcepcionAS2
/* 24:   */   {
/* 25:   */     try
/* 26:   */     {
/* 27:43 */       Query query = this.em.createQuery("SELECT t FROM TipoComprobanteSRI t WHERE t.codigo=:codigo");
/* 28:44 */       query.setParameter("codigo", codigo);
/* 29:   */       
/* 30:46 */       return (TipoComprobanteSRI)query.getSingleResult();
/* 31:   */     }
/* 32:   */     catch (NoResultException e)
/* 33:   */     {
/* 34:49 */       throw new ExcepcionAS2("msg_no_hay_datos");
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TipoComprobanteSRI> buscarPorTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/* 39:   */   {
/* 40:55 */     StringBuilder sb = new StringBuilder();
/* 41:56 */     sb.append(" SELECT tc FROM TipoComprobanteSRI tc ");
/* 42:57 */     sb.append(" INNER JOIN tc.listaTipoIdentificacionComprobanteSRI tic ");
/* 43:58 */     sb.append(" WHERE tic.tipoIdentificacion = :tipoIdentificacion ");
/* 44:59 */     sb.append(" ORDER BY tc.predeterminado, tc.codigo ");
/* 45:60 */     Query qry = this.em.createQuery(sb.toString());
/* 46:61 */     qry.setParameter("tipoIdentificacion", tipoIdentificacion);
/* 47:   */     
/* 48:63 */     return qry.getResultList();
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.TipoComprobanteSRIDao
 * JD-Core Version:    0.7.0.1
 */