/*  1:   */ package com.asinfo.as2.dao.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*  4:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  5:   */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class AutorizacionProveedorSRIDao
/* 14:   */   extends AbstractDaoAS2<AutorizacionProveedorSRI>
/* 15:   */ {
/* 16:   */   public AutorizacionProveedorSRIDao()
/* 17:   */   {
/* 18:37 */     super(AutorizacionProveedorSRI.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public AutorizacionProveedorSRI buscarAutorizacion(int idEmpresa, String factura, Date fecha, int idOrganizacion)
/* 22:   */     throws ExcepcionAS2Compras
/* 23:   */   {
/* 24:43 */     String establecimiento = factura.substring(0, 3);
/* 25:44 */     String punto = factura.substring(4, 7);
/* 26:   */     
/* 27:46 */     StringBuilder sql = new StringBuilder();
/* 28:47 */     sql.append(" SELECT a FROM AutorizacionProveedorSRI a ");
/* 29:48 */     sql.append(" INNER JOIN a.empresa e ");
/* 30:49 */     sql.append(" WHERE :fecha BETWEEN a.fechaDesde AND a.fechaHasta ");
/* 31:50 */     sql.append(" AND a.establecimiento= :establecimiento ");
/* 32:51 */     sql.append(" AND a.puntoEmision= :punto ");
/* 33:52 */     sql.append(" AND a.idOrganizacion= :idOrganizacion ");
/* 34:53 */     sql.append(" AND e.idEmpresa= :idEmpresa ");
/* 35:54 */     sql.append(" AND a.activo= true ");
/* 36:55 */     sql.append(" ORDER BY a.fechaHasta DESC ");
/* 37:   */     
/* 38:57 */     Query query = this.em.createQuery(sql.toString());
/* 39:58 */     query.setParameter("fecha", fecha);
/* 40:59 */     query.setParameter("establecimiento", establecimiento);
/* 41:60 */     query.setParameter("punto", punto);
/* 42:61 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 43:62 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 44:64 */     if (query.getResultList().isEmpty()) {
/* 45:65 */       throw new ExcepcionAS2Compras("msg_error_autorizaciono_factura", " Factura: " + factura);
/* 46:   */     }
/* 47:67 */     return (AutorizacionProveedorSRI)query.getResultList().get(0);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.AutorizacionProveedorSRIDao
 * JD-Core Version:    0.7.0.1
 */