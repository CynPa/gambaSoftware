/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoContacto;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.Query;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class TipoContactoDao
/* 11:   */   extends AbstractDaoAS2<TipoContacto>
/* 12:   */ {
/* 13:   */   public TipoContactoDao()
/* 14:   */   {
/* 15:32 */     super(TipoContacto.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TipoContacto getTipoContactoPredeterminadoPorTipoNotificacion(String tipoNotificacion, int idOrganizacion)
/* 19:   */   {
/* 20:37 */     StringBuilder sql = new StringBuilder();
/* 21:38 */     sql.append("SELECT tc");
/* 22:39 */     sql.append(" FROM TipoContacto tc ");
/* 23:40 */     sql.append(" WHERE tc.predeterminado = true ");
/* 24:41 */     sql.append(" AND tc.activo = true ");
/* 25:42 */     sql.append(" AND tc.indicadorNotificar" + tipoNotificacion + " = true");
/* 26:43 */     sql.append(" AND tc.idOrganizacion = :idOrganizacion");
/* 27:   */     
/* 28:45 */     Query query = this.em.createQuery(sql.toString());
/* 29:46 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 30:47 */     query.setMaxResults(1);
/* 31:48 */     List<TipoContacto> lista = query.getResultList();
/* 32:49 */     if (lista.size() > 0) {
/* 33:50 */       return (TipoContacto)lista.get(0);
/* 34:   */     }
/* 35:52 */     return null;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoContactoDao
 * JD-Core Version:    0.7.0.1
 */