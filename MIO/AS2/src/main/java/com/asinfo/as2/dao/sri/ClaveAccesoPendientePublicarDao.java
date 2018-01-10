/*  1:   */ package com.asinfo.as2.dao.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.sri.ClaveAccesoPendientePublicar;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ClaveAccesoPendientePublicarDao
/* 12:   */   extends AbstractDaoAS2<ClaveAccesoPendientePublicar>
/* 13:   */ {
/* 14:   */   public ClaveAccesoPendientePublicarDao()
/* 15:   */   {
/* 16:24 */     super(ClaveAccesoPendientePublicar.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public List obtenerClaveAccesoPendientePublicar(int idOrganizacion, int limit)
/* 20:   */   {
/* 21:29 */     StringBuilder sql = new StringBuilder();
/* 22:30 */     sql.append(" SELECT capp FROM ClaveAccesoPendientePublicar capp ");
/* 23:31 */     sql.append(" WHERE capp.indicadorPublicado IS NOT TRUE ");
/* 24:32 */     sql.append(" AND capp.idOrganizacion = :idOrganizacion ");
/* 25:33 */     sql.append(" ORDER BY capp.cantidadIntentos ASC ");
/* 26:   */     
/* 27:35 */     Query query = this.em.createQuery(sql.toString());
/* 28:36 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 29:37 */     query.setMaxResults(limit);
/* 30:38 */     List resultado = query.getResultList();
/* 31:   */     
/* 32:40 */     return resultado;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.ClaveAccesoPendientePublicarDao
 * JD-Core Version:    0.7.0.1
 */