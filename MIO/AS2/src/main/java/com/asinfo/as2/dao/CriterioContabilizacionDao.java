/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*  4:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  5:   */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class CriterioContabilizacionDao
/* 13:   */   extends AbstractDaoAS2<CriterioContabilizacion>
/* 14:   */ {
/* 15:   */   public CriterioContabilizacionDao()
/* 16:   */   {
/* 17:35 */     super(CriterioContabilizacion.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<CriterioContabilizacion> getListaCriterioContabilizacion(int idOrganizacion, DocumentoBase documentoBase, ProcesoContabilizacionEnum procesoContabilizacion)
/* 21:   */   {
/* 22:40 */     StringBuilder sql = new StringBuilder();
/* 23:   */     
/* 24:42 */     sql.append(" SELECT cc FROM CriterioContabilizacion cc");
/* 25:43 */     sql.append(" JOIN FETCH cc.cuentaContable cu");
/* 26:44 */     sql.append(" JOIN  cc.documentoContabilizacion dc");
/* 27:45 */     sql.append(" WHERE cc.idOrganizacion=:idOrganizacion");
/* 28:46 */     sql.append(" AND dc.documentoBase=:documentoBase");
/* 29:47 */     sql.append(" AND dc.procesoContabilizacion=:procesoContabilizacion");
/* 30:   */     
/* 31:49 */     Query query = this.em.createQuery(sql.toString());
/* 32:50 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 33:51 */     query.setParameter("documentoBase", documentoBase);
/* 34:52 */     query.setParameter("procesoContabilizacion", procesoContabilizacion);
/* 35:   */     
/* 36:54 */     return query.getResultList();
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CriterioContabilizacionDao
 * JD-Core Version:    0.7.0.1
 */