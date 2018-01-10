/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Organizacion;
/*  4:   */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.Query;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class OrganizacionConfiguracionDao
/* 11:   */   extends AbstractDaoAS2<OrganizacionConfiguracion>
/* 12:   */ {
/* 13:   */   public OrganizacionConfiguracionDao()
/* 14:   */   {
/* 15:31 */     super(OrganizacionConfiguracion.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Integer actualizarSecuenciaInicioSerie(Organizacion organizacion)
/* 19:   */   {
/* 20:39 */     StringBuilder sql = new StringBuilder();
/* 21:40 */     sql.append("SELECT MAX(oc.inicioSerie) FROM OrganizacionConfiguracion oc ");
/* 22:41 */     sql.append(" WHERE oc.organizacion = :organizacion ");
/* 23:   */     
/* 24:43 */     Query query = this.em.createQuery(sql.toString());
/* 25:44 */     query.setParameter("organizacion", organizacion);
/* 26:45 */     Integer numeroSerie = Integer.valueOf(((Integer)query.getSingleResult()).intValue() + 1);
/* 27:   */     
/* 28:47 */     StringBuilder sql2 = new StringBuilder();
/* 29:48 */     sql2.append(" UPDATE OrganizacionConfiguracion oc ");
/* 30:49 */     sql2.append(" SET oc.inicioSerie = :numeroSerie ");
/* 31:50 */     sql2.append(" WHERE oc.organizacion = :organizacion ");
/* 32:   */     
/* 33:52 */     Query query2 = this.em.createQuery(sql2.toString());
/* 34:53 */     query2.setParameter("numeroSerie", numeroSerie).setParameter("organizacion", organizacion);
/* 35:54 */     query2.executeUpdate();
/* 36:   */     
/* 37:56 */     return numeroSerie;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String obtenerDireccionMatriz(int idOrganizacion)
/* 41:   */   {
/* 42:61 */     StringBuilder sql = new StringBuilder();
/* 43:62 */     sql.append(" SELECT coalesce(MAX(  CONCAT(coalesce(direccion1,''),' ',coalesce(direccion2,''),' ',coalesce(direccion3,''),' ',coalesce(direccion4,''))     )       ,'')");
/* 44:63 */     sql.append(" FROM Sucursal s INNER JOIN s.ubicacion u ");
/* 45:64 */     sql.append(" WHERE s.idOrganizacion = :idOrganizacion AND s.indicadorMatriz = true");
/* 46:   */     
/* 47:66 */     Query query = this.em.createQuery(sql.toString());
/* 48:67 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 49:   */     
/* 50:69 */     return query.getSingleResult().toString();
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.OrganizacionConfiguracionDao
 * JD-Core Version:    0.7.0.1
 */