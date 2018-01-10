/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.RangoImpuesto;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ import java.util.Date;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ import javax.persistence.TemporalType;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class RangoImpuestoDao
/* 14:   */   extends AbstractDaoAS2<RangoImpuesto>
/* 15:   */ {
/* 16:   */   public RangoImpuestoDao()
/* 17:   */   {
/* 18:33 */     super(RangoImpuesto.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public List<RangoImpuesto> getRangoImpuestosPorFecha(int idProducto, Date fecha)
/* 22:   */   {
/* 23:43 */     return this.em.createQuery("SELECT i.impuesto FROM ImpuestoCategoria i, Producto p WHERE i.CategoriaImpuesto i.categoriaImpuesto.producto.id =:idProducto and :fecha between i.impuesto.rangoImpuesto.fecha_desde and i.impuesto.rangoImpuesto.fecha_hasta").setParameter("idProducto", Integer.valueOf(idProducto)).getResultList();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public RangoImpuesto getRangoRangoImpuestoTributario(Date fecha, int idOrganizacion)
/* 27:   */   {
/* 28:60 */     StringBuilder sql = new StringBuilder();
/* 29:61 */     sql.append(" SELECT ri FROM RangoImpuesto ri ");
/* 30:62 */     sql.append(" JOIN FETCH ri.impuesto imp ");
/* 31:63 */     sql.append(" LEFT JOIN FETCH imp.cuentaContableCompra ");
/* 32:64 */     sql.append(" WHERE imp.idOrganizacion = :idOrganizacion ");
/* 33:65 */     sql.append(" AND imp.indicadorImpuestoTributario = true ");
/* 34:66 */     sql.append(" AND ri.fechaDesde <= :fecha  ");
/* 35:67 */     sql.append(" AND (ri.fechaHasta IS NULL OR ri.fechaHasta >= :fecha) ");
/* 36:   */     
/* 37:69 */     Query query = this.em.createQuery(sql.toString());
/* 38:70 */     query.setMaxResults(1);
/* 39:71 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 40:72 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 41:   */     
/* 42:74 */     return (RangoImpuesto)query.getSingleResult();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public BigDecimal getPorcentajeIVA(int idOrganizacion, Date fecha)
/* 46:   */   {
/* 47:78 */     StringBuilder sql = new StringBuilder();
/* 48:79 */     sql.append(" SELECT ri.porcentajeImpuesto ");
/* 49:80 */     sql.append(" FROM RangoImpuesto ri ");
/* 50:81 */     sql.append(" INNER JOIN ri.impuesto imp ");
/* 51:82 */     sql.append(" WHERE imp.idOrganizacion = :idOrganizacion ");
/* 52:83 */     sql.append(" AND imp.indicadorImpuestoTributario = true ");
/* 53:   */     
/* 54:85 */     sql.append(" AND ri.fechaDesde <= :fecha  ");
/* 55:86 */     sql.append(" AND (ri.fechaHasta IS NULL OR ri.fechaHasta >= :fecha) ");
/* 56:   */     
/* 57:88 */     Query query = this.em.createQuery(sql.toString());
/* 58:89 */     query.setMaxResults(1);
/* 59:90 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 60:91 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 61:   */     
/* 62:93 */     return (BigDecimal)query.getSingleResult();
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RangoImpuestoDao
 * JD-Core Version:    0.7.0.1
 */