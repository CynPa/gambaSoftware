/*  1:   */ package com.asinfo.as2.dao.seguridad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*  5:   */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  6:   */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class PermisoDao
/* 14:   */   extends AbstractDaoAS2<EntidadPermiso>
/* 15:   */ {
/* 16:   */   public PermisoDao()
/* 17:   */   {
/* 18:36 */     super(EntidadPermiso.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public List<EntidadPermiso> obtenerRolProceso()
/* 22:   */   {
/* 23:46 */     StringBuilder sql = new StringBuilder();
/* 24:47 */     sql.append("SELECT new EntidadPermiso(er, po) ");
/* 25:48 */     sql.append(" FROM ProcesoOrganizacion po, EntidadRol er ");
/* 26:49 */     sql.append(" WHERE er.idRol = 1 ");
/* 27:   */     
/* 28:51 */     Query query = this.em.createQuery(sql.toString());
/* 29:   */     
/* 30:53 */     return query.getResultList();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean existePermiso(ProcesoOrganizacion procesoOrganizacion, EntidadRol entidadRol)
/* 34:   */   {
/* 35:58 */     StringBuilder sql = new StringBuilder();
/* 36:59 */     sql.append("SELECT ep ");
/* 37:60 */     sql.append(" FROM EntidadPermiso ep ");
/* 38:61 */     sql.append(" INNER JOIN ep.procesoOrganizacion po ");
/* 39:62 */     sql.append(" WHERE po.idProcesoOrganizacion = :idProcesoOrganizacion ");
/* 40:63 */     sql.append(" AND ep.entidadRol.idRol = :idRol ");
/* 41:   */     
/* 42:65 */     Query query = this.em.createQuery(sql.toString());
/* 43:66 */     if (procesoOrganizacion != null) {
/* 44:67 */       query.setParameter("idProcesoOrganizacion", Integer.valueOf(procesoOrganizacion.getId()));
/* 45:   */     }
/* 46:69 */     if (entidadRol != null) {
/* 47:70 */       query.setParameter("idRol", Integer.valueOf(entidadRol.getId()));
/* 48:   */     }
/* 49:73 */     List<EntidadPermiso> listaEntidadPermiso = query.getResultList();
/* 50:74 */     if (listaEntidadPermiso.size() > 0) {
/* 51:75 */       return true;
/* 52:   */     }
/* 53:77 */     return false;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.seguridad.PermisoDao
 * JD-Core Version:    0.7.0.1
 */