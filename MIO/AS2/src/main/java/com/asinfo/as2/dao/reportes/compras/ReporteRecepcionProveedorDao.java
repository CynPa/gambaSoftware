/*   1:    */ package com.asinfo.as2.dao.reportes.compras;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ReporteRecepcionProveedorDao
/*  18:    */   extends AbstractDaoAS2<RecepcionProveedor>
/*  19:    */ {
/*  20:    */   public ReporteRecepcionProveedorDao()
/*  21:    */   {
/*  22: 33 */     super(RecepcionProveedor.class);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public List getReporteRecepcionProveedor(int idRecepcionProveedor)
/*  26:    */   {
/*  27: 45 */     StringBuilder sbSQL = new StringBuilder();
/*  28: 46 */     sbSQL.append(" SELECT rp.numero, pp.numero, rp.fecha, rp.descripcion, em.nombreComercial, em.nombreFiscal, em.identificacion , (SELECT MAX (CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4)) FROM DireccionEmpresa de JOIN de.empresa e1 JOIN de.ubicacion u WHERE e1 = em ) , ");
/*  29:    */     
/*  30: 48 */     sbSQL.append(" p.codigo, p.nombre, un.codigo, drp.cantidad, b.nombre , drp.descripcion, l.codigo, rp.numeroFactura, ip.costo, rp.usuarioCreacion, cate.nombre, pp.usuarioCreacion, p.codigoAlterno ");
/*  31:    */     
/*  32:    */ 
/*  33: 51 */     sbSQL.append(" , ui.nombre, drp.cantidadUnidadInformativa,  p.idProducto, CONCAT(pr.nombres,' ',pr.apellidos) ");
/*  34: 52 */     sbSQL.append(" FROM InventarioProducto ip ");
/*  35: 53 */     sbSQL.append(" JOIN ip.bodega b ");
/*  36: 54 */     sbSQL.append(" JOIN ip.detalleRecepcionProveedor drp ");
/*  37: 55 */     sbSQL.append(" JOIN drp.recepcionProveedor rp ");
/*  38: 56 */     sbSQL.append(" LEFT JOIN rp.personaResponsable pr ");
/*  39: 57 */     sbSQL.append(" JOIN ip.producto p ");
/*  40: 58 */     sbSQL.append(" JOIN p.unidad un ");
/*  41: 59 */     sbSQL.append(" JOIN rp.empresa em ");
/*  42: 60 */     sbSQL.append(" JOIN em.categoriaEmpresa cate ");
/*  43: 61 */     sbSQL.append(" LEFT JOIN ip.lote l ");
/*  44: 62 */     sbSQL.append(" LEFT JOIN drp.detallePedidoProveedor dpp");
/*  45: 63 */     sbSQL.append(" LEFT JOIN dpp.pedidoProveedor pp");
/*  46: 64 */     sbSQL.append(" LEFT JOIN p.unidadInformativa ui ");
/*  47: 65 */     sbSQL.append(" WHERE rp.idRecepcionProveedor = :idRecepcionProveedor ");
/*  48:    */     
/*  49: 67 */     Query query = this.em.createQuery(sbSQL.toString()).setParameter("idRecepcionProveedor", Integer.valueOf(idRecepcionProveedor));
/*  50: 68 */     return query.getResultList();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List getReporteRecepcionProveedor(int idOrganizacion, int tipoReporte, int idEmpresa, Bodega bodega, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Date fechaDesde, Date fechaHasta)
/*  54:    */   {
/*  55: 75 */     StringBuilder sql = new StringBuilder();
/*  56: 76 */     sql.append(" SELECT ");
/*  57: 78 */     if (tipoReporte != 1) {
/*  58: 79 */       sql.append("rp.numero,rp.fecha,e.nombreComercial,e.nombreFiscal,e.identificacion, rp.usuarioCreacion, drp.descripcion, rp.numero, fp.numero, pp.numero,");
/*  59:    */     }
/*  60: 82 */     sql.append(" p.codigo, p.codigoComercial, p.nombre, p.nombreComercial, un.codigo, b.codigo, b.nombre, ");
/*  61: 83 */     if (tipoReporte != 2) {
/*  62: 84 */       sql.append(" SUM(drp.cantidad), SUM(ip.costo) ");
/*  63:    */     }
/*  64: 86 */     if (tipoReporte == 2)
/*  65:    */     {
/*  66: 87 */       sql.append(" drp.cantidad, ip.costo ");
/*  67: 88 */       sql.append(" ,l.codigo ");
/*  68:    */     }
/*  69: 91 */     sql.append(" FROM InventarioProducto ip ");
/*  70: 92 */     sql.append(" JOIN ip.bodega b ");
/*  71: 93 */     sql.append(" JOIN ip.detalleRecepcionProveedor drp ");
/*  72: 94 */     sql.append(" JOIN drp.recepcionProveedor rp ");
/*  73: 95 */     sql.append(" LEFT JOIN ip.producto p ");
/*  74: 96 */     sql.append(" LEFT JOIN ip.lote l ");
/*  75: 97 */     sql.append(" LEFT JOIN p.subcategoriaProducto scp ");
/*  76: 98 */     sql.append(" LEFT JOIN scp.categoriaProducto cp ");
/*  77: 99 */     sql.append(" LEFT JOIN drp.unidadCompra un ");
/*  78:100 */     sql.append(" LEFT JOIN drp.detalleFacturaProveedor dfp ");
/*  79:101 */     sql.append(" LEFT JOIN dfp.facturaProveedor fp ");
/*  80:102 */     sql.append(" LEFT JOIN drp.detallePedidoProveedor dpp");
/*  81:103 */     sql.append(" LEFT JOIN dpp.pedidoProveedor pp");
/*  82:104 */     sql.append(" LEFT JOIN rp.empresa e ");
/*  83:105 */     sql.append(" WHERE rp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  84:106 */     sql.append(" AND rp.idOrganizacion = :idOrganizacion ");
/*  85:107 */     sql.append(" AND (e.idEmpresa = :idEmpresa OR :idEmpresa=0)");
/*  86:108 */     sql.append(" AND rp.estado <> :estadoAnulado ");
/*  87:110 */     if (bodega != null) {
/*  88:111 */       sql.append(" AND b =:bodega ");
/*  89:    */     }
/*  90:113 */     if (producto != null) {
/*  91:114 */       sql.append("AND p.idProducto = :idProducto ");
/*  92:    */     }
/*  93:116 */     if (categoriaProducto != null) {
/*  94:117 */       sql.append("AND cp.idCategoriaProducto = :idCategoriaProducto ");
/*  95:    */     }
/*  96:119 */     if (subcategoriaProducto != null) {
/*  97:120 */       sql.append("AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/*  98:    */     }
/*  99:123 */     if (tipoReporte == 1)
/* 100:    */     {
/* 101:124 */       sql.append(" GROUP BY p.codigo, p.codigoComercial, p.nombre, p.nombreComercial, un.codigo, b.codigo, b.nombre");
/* 102:125 */       sql.append(" ORDER BY p.nombre ");
/* 103:    */     }
/* 104:126 */     else if (tipoReporte == 2)
/* 105:    */     {
/* 106:127 */       sql.append(" ORDER BY rp.fecha, p.nombre, l.codigo ");
/* 107:    */     }
/* 108:    */     else
/* 109:    */     {
/* 110:129 */       sql.append(" GROUP BY rp.numero,rp.fecha,e.nombreComercial,e.nombreFiscal,e.identificacion, rp.usuarioCreacion, fp.numero, drp.descripcion, p.codigo, p.codigoComercial, p.nombre, p.nombreComercial, un.codigo, b.codigo, b.nombre,pp.numero ");
/* 111:    */       
/* 112:131 */       sql.append(" ORDER BY rp.fecha ");
/* 113:    */     }
/* 114:134 */     Query query = this.em.createQuery(sql.toString());
/* 115:135 */     query.setParameter("fechaDesde", fechaDesde);
/* 116:136 */     query.setParameter("fechaHasta", fechaHasta);
/* 117:137 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 118:138 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 119:139 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 120:142 */     if (bodega != null) {
/* 121:143 */       query.setParameter("bodega", bodega);
/* 122:    */     }
/* 123:146 */     if (producto != null) {
/* 124:147 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 125:    */     }
/* 126:149 */     if (categoriaProducto != null) {
/* 127:150 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 128:    */     }
/* 129:152 */     if (subcategoriaProducto != null) {
/* 130:153 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 131:    */     }
/* 132:156 */     return query.getResultList();
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.ReporteRecepcionProveedorDao
 * JD-Core Version:    0.7.0.1
 */