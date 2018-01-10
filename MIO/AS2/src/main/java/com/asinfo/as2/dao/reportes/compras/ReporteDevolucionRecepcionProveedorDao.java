/*  1:   */ package com.asinfo.as2.dao.reportes.compras;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.entities.FacturaProveedor;
/*  7:   */ import com.asinfo.as2.entities.Producto;
/*  8:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  9:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/* 10:   */ import com.asinfo.as2.enumeraciones.Estado;
/* 11:   */ import java.util.Date;
/* 12:   */ import java.util.List;
/* 13:   */ import javax.ejb.Stateless;
/* 14:   */ import javax.persistence.EntityManager;
/* 15:   */ import javax.persistence.Query;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class ReporteDevolucionRecepcionProveedorDao
/* 19:   */   extends AbstractDaoAS2<FacturaProveedor>
/* 20:   */ {
/* 21:   */   public ReporteDevolucionRecepcionProveedorDao()
/* 22:   */   {
/* 23:35 */     super(FacturaProveedor.class);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List getReporteDevolucionRecepcionProveedor(int idOrganizacion, Empresa empresa, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Date fechaDesde, Date fechaHasta)
/* 27:   */   {
/* 28:43 */     StringBuilder sbSQL = new StringBuilder();
/* 29:44 */     sbSQL.append(" SELECT fp.fecha, em.codigo, em.nombreFiscal, lte.codigo, pr.codigo , pr.nombre, ivpr.numeroDocumento, rp.descripcion, drpd.cantidad, ");
/* 30:45 */     sbSQL.append(" drpd.cantidadUnidadInformativa, ivpr.costo/drpd.cantidad , ivpr.costo,  ivpf.numeroDocumento, dfp.cantidad, dfp.cantidadUnidadInformativa, ivpf.costo ");
/* 31:46 */     sbSQL.append(" FROM DetalleFacturaProveedor dfp ");
/* 32:47 */     sbSQL.append(" JOIN dfp.facturaProveedor  fp");
/* 33:48 */     sbSQL.append(" JOIN dfp.inventarioProducto  ivpf");
/* 34:49 */     sbSQL.append(" JOIN fp.documento  dc");
/* 35:50 */     sbSQL.append(" JOIN ivpf.producto pr ");
/* 36:51 */     sbSQL.append(" JOIN pr.subcategoriaProducto sbp ");
/* 37:52 */     sbSQL.append(" JOIN sbp.categoriaProducto cpr ");
/* 38:53 */     sbSQL.append(" LEFT JOIN ivpf.lote  lte ");
/* 39:54 */     sbSQL.append(" JOIN dfp.detalleRecepcionProveedorDevolucion drpd ");
/* 40:55 */     sbSQL.append(" JOIN drpd.inventarioProducto  ivpr");
/* 41:56 */     sbSQL.append(" JOIN drpd.producto prpr");
/* 42:57 */     sbSQL.append(" JOIN fp.empresa  em ");
/* 43:58 */     sbSQL.append(" JOIN drpd.recepcionProveedor rp");
/* 44:59 */     sbSQL.append(" WHERE fp.idOrganizacion =:idOrganizacion");
/* 45:60 */     sbSQL.append(" AND fp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 46:61 */     sbSQL.append(" AND fp.estado <> :estadoAnulado ");
/* 47:62 */     sbSQL.append(" AND dc.documentoBase =:documentoBase ");
/* 48:63 */     sbSQL.append(" AND dc.indicadorDocumentoTributario =:indicadorDocumentoTributario ");
/* 49:65 */     if (empresa != null) {
/* 50:66 */       sbSQL.append(" AND em.idEmpresa =:idEmpresa ");
/* 51:   */     }
/* 52:68 */     if (categoriaProducto != null) {
/* 53:69 */       sbSQL.append(" AND cpr.idCategoriaProducto =:idCategoriaProducto ");
/* 54:   */     }
/* 55:71 */     if (subcategoriaProducto != null) {
/* 56:72 */       sbSQL.append(" AND sbp.idSubcategoriaProducto =:idSubcategoriaProducto ");
/* 57:   */     }
/* 58:74 */     if (producto != null) {
/* 59:75 */       sbSQL.append(" AND pr.idProducto =:idProducto ");
/* 60:   */     }
/* 61:78 */     Query query = this.em.createQuery(sbSQL.toString());
/* 62:79 */     query.setParameter("fechaDesde", fechaDesde);
/* 63:80 */     query.setParameter("fechaHasta", fechaHasta);
/* 64:81 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 65:82 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 66:83 */     query.setParameter("documentoBase", DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 67:84 */     query.setParameter("indicadorDocumentoTributario", Boolean.valueOf(false));
/* 68:86 */     if (empresa != null) {
/* 69:87 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 70:   */     }
/* 71:89 */     if (categoriaProducto != null) {
/* 72:90 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 73:   */     }
/* 74:92 */     if (subcategoriaProducto != null) {
/* 75:93 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 76:   */     }
/* 77:95 */     if (producto != null) {
/* 78:96 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 79:   */     }
/* 80:99 */     return query.getResultList();
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.ReporteDevolucionRecepcionProveedorDao
 * JD-Core Version:    0.7.0.1
 */