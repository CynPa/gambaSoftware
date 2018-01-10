/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Organizacion;
/*  4:   */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*  5:   */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  6:   */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ProcesoOrganizacionDao
/* 14:   */   extends AbstractDaoAS2<ProcesoOrganizacion>
/* 15:   */ {
/* 16:   */   public ProcesoOrganizacionDao()
/* 17:   */   {
/* 18:28 */     super(ProcesoOrganizacion.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public List<ProcesoOrganizacion> buscarPorSistemaOrganizacion(EntidadSistema sistema, Organizacion organizacion)
/* 22:   */   {
/* 23:32 */     StringBuilder sql = new StringBuilder();
/* 24:33 */     sql.append("SELECT po FROM ProcesoOrganizacion po ");
/* 25:34 */     sql.append(" LEFT JOIN FETCH po.entidadProceso ep ");
/* 26:35 */     sql.append(" LEFT JOIN FETCH ep.sistema s ");
/* 27:36 */     sql.append(" LEFT JOIN FETCH po.organizacion o ");
/* 28:37 */     String where = " WHERE ";
/* 29:38 */     if (sistema != null)
/* 30:   */     {
/* 31:39 */       sql.append(where + " s.idSistema = :idSistema ");
/* 32:40 */       where = " AND ";
/* 33:   */     }
/* 34:42 */     if (organizacion != null)
/* 35:   */     {
/* 36:43 */       sql.append(where + " o.idOrganizacion = :idOrganizacion ");
/* 37:44 */       where = " AND ";
/* 38:   */     }
/* 39:46 */     sql.append(where + " ep.activo = true ");
/* 40:   */     
/* 41:   */ 
/* 42:   */ 
/* 43:50 */     Query query = this.em.createQuery(sql.toString());
/* 44:51 */     if (sistema != null) {
/* 45:52 */       query.setParameter("idSistema", Integer.valueOf(sistema.getId()));
/* 46:   */     }
/* 47:54 */     if (organizacion != null) {
/* 48:55 */       query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getId()));
/* 49:   */     }
/* 50:58 */     return query.getResultList();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public ProcesoOrganizacion buscarProcesoOrganizacion(Organizacion organizacion, EntidadProceso entidadProceso)
/* 54:   */   {
/* 55:63 */     StringBuilder sql = new StringBuilder();
/* 56:64 */     sql.append("SELECT po FROM ProcesoOrganizacion po ");
/* 57:65 */     sql.append(" LEFT JOIN FETCH po.entidadProceso ep ");
/* 58:66 */     sql.append(" LEFT JOIN FETCH po.organizacion o ");
/* 59:67 */     String where = " WHERE ";
/* 60:68 */     if (organizacion != null)
/* 61:   */     {
/* 62:69 */       sql.append(where + " o.idOrganizacion = :idOrganizacion ");
/* 63:70 */       where = " AND ";
/* 64:   */     }
/* 65:72 */     sql.append(where + " ep.idProceso = :idProceso ");
/* 66:   */     
/* 67:74 */     Query query = this.em.createQuery(sql.toString());
/* 68:75 */     if (organizacion != null) {
/* 69:76 */       query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getId()));
/* 70:   */     }
/* 71:78 */     if (entidadProceso != null) {
/* 72:79 */       query.setParameter("idProceso", Integer.valueOf(entidadProceso.getId()));
/* 73:   */     }
/* 74:82 */     return query.getResultList().size() > 0 ? (ProcesoOrganizacion)query.getResultList().get(0) : null;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProcesoOrganizacionDao
 * JD-Core Version:    0.7.0.1
 */