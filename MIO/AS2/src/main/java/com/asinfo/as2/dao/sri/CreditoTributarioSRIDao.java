/*  1:   */ package com.asinfo.as2.dao.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  5:   */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  6:   */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class CreditoTributarioSRIDao
/* 14:   */   extends AbstractDaoAS2<CreditoTributarioSRI>
/* 15:   */ {
/* 16:   */   public CreditoTributarioSRIDao()
/* 17:   */   {
/* 18:36 */     super(CreditoTributarioSRI.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public CreditoTributarioSRI buscarPorCodigo(String codigo)
/* 22:   */   {
/* 23:40 */     StringBuilder sbSQL = new StringBuilder();
/* 24:41 */     sbSQL.append("SELECT c FROM CreditoTributarioSRI c");
/* 25:42 */     sbSQL.append(" WHERE c.codigo=:codigo");
/* 26:   */     
/* 27:44 */     Query query = this.em.createQuery(sbSQL.toString());
/* 28:45 */     query.setParameter("codigo", codigo);
/* 29:   */     
/* 30:47 */     return (CreditoTributarioSRI)query.getSingleResult();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<CreditoTributarioSRI> buscarPorTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI, TipoIdentificacion tipoIdentificacion)
/* 34:   */   {
/* 35:52 */     StringBuilder sb = new StringBuilder();
/* 36:53 */     sb.append(" SELECT ct FROM CreditoTributarioSRI ct ");
/* 37:54 */     sb.append(" INNER JOIN ct.listaComprobanteSRICreditoTributarioSRI cct ");
/* 38:55 */     sb.append(" WHERE cct.tipoComprobanteSRI = :tipoComprobanteSRI ");
/* 39:56 */     sb.append(" AND cct.tipoIdentificacion = :tipoIdentificacion ");
/* 40:57 */     sb.append(" ORDER BY ct.predeterminado, ct.codigo ");
/* 41:58 */     Query qry = this.em.createQuery(sb.toString());
/* 42:59 */     qry.setParameter("tipoComprobanteSRI", tipoComprobanteSRI);
/* 43:60 */     qry.setParameter("tipoIdentificacion", tipoIdentificacion);
/* 44:   */     
/* 45:62 */     return qry.getResultList();
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.CreditoTributarioSRIDao
 * JD-Core Version:    0.7.0.1
 */