/*   1:    */ package com.asinfo.as2.dao.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.db.AS2DBAuditoria;
/*   4:    */ import com.asinfo.as2.entities.seguridad.LogAuditoria;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TemporalType;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Root;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class LogAuditoriaDao
/*  19:    */   extends AS2DBAuditoria
/*  20:    */ {
/*  21:    */   public List<LogAuditoria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  22:    */   {
/*  23: 43 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  24: 44 */     CriteriaQuery<LogAuditoria> criteriaQuery = criteriaBuilder.createQuery(LogAuditoria.class);
/*  25: 45 */     Root<LogAuditoria> from = criteriaQuery.from(LogAuditoria.class);
/*  26:    */     
/*  27: 47 */     CriteriaQuery<LogAuditoria> select = criteriaQuery.select(from);
/*  28:    */     
/*  29: 49 */     TypedQuery<LogAuditoria> typedQuery = this.em.createQuery(select);
/*  30: 50 */     typedQuery.setFirstResult(startIndex);
/*  31: 51 */     typedQuery.setMaxResults(pageSize);
/*  32:    */     
/*  33: 53 */     return typedQuery.getResultList();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int contar()
/*  37:    */   {
/*  38: 63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  39: 64 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  40:    */     
/*  41: 66 */     criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(LogAuditoria.class)));
/*  42:    */     
/*  43: 68 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void guardar(LogAuditoria logAuditoria)
/*  47:    */   {
/*  48: 72 */     this.em.persist(logAuditoria);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<LogAuditoria> obtenerAuditoriaFiltrado(String nombreUsuario, String entidad, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/*  52:    */   {
/*  53: 78 */     StringBuilder sql = new StringBuilder();
/*  54: 79 */     sql.append(" SELECT l FROM LogAuditoria l ");
/*  55: 80 */     sql.append(" WHERE l.idOrganizacion = :idOrganizacion");
/*  56: 82 */     if (!entidad.isEmpty()) {
/*  57: 83 */       sql.append(" AND l.claseEntidad = :entidad");
/*  58:    */     }
/*  59: 85 */     if (!nombreUsuario.isEmpty()) {
/*  60: 86 */       sql.append(" AND l.nombreUsuario = :nombreUsuario");
/*  61:    */     }
/*  62: 88 */     sql.append(" AND l.fechaCreacion between :fechaDesde AND :fechaHasta");
/*  63:    */     
/*  64: 90 */     Query query = this.em.createQuery(sql.toString());
/*  65:    */     
/*  66: 92 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  67: 93 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  68: 94 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  69: 96 */     if (!entidad.isEmpty()) {
/*  70: 97 */       query.setParameter("entidad", entidad);
/*  71:    */     }
/*  72:100 */     if (!nombreUsuario.isEmpty()) {
/*  73:101 */       query.setParameter("nombreUsuario", nombreUsuario);
/*  74:    */     }
/*  75:104 */     return query.getResultList();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<Object[]> listaReporteLogAuditoria(Date fechaDesde, Date fechaHasta, String nombreUsuario, String proceso, int idOrganizacion)
/*  79:    */   {
/*  80:113 */     StringBuilder sql = new StringBuilder();
/*  81:114 */     sql.append(" SELECT la.claseEntidad, la.fechaCreacion, la.mensaje, la.nombreUsuario, la.hostRemoto ");
/*  82:115 */     sql.append(" FROM LogAuditoria la ");
/*  83:116 */     sql.append(" WHERE la.idOrganizacion = :idOrganizacion ");
/*  84:117 */     sql.append(" AND la.fechaCreacion BETWEEN :fechaDesde AND :fechaHasta ");
/*  85:119 */     if (!nombreUsuario.isEmpty()) {
/*  86:120 */       sql.append(" AND la.nombreUsuario = :nombreUsuario ");
/*  87:    */     }
/*  88:123 */     if (!proceso.isEmpty()) {
/*  89:124 */       sql.append(" AND la.claseEntidad = :proceso ");
/*  90:    */     }
/*  91:126 */     Query query = this.em.createQuery(sql.toString());
/*  92:127 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  93:128 */     query.setParameter("fechaDesde", fechaDesde);
/*  94:129 */     query.setParameter("fechaHasta", fechaHasta);
/*  95:131 */     if (!nombreUsuario.isEmpty()) {
/*  96:132 */       query.setParameter("nombreUsuario", nombreUsuario);
/*  97:    */     }
/*  98:134 */     if (!proceso.isEmpty()) {
/*  99:135 */       query.setParameter("proceso", proceso);
/* 100:    */     }
/* 101:138 */     return query.getResultList();
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.seguridad.LogAuditoriaDao
 * JD-Core Version:    0.7.0.1
 */