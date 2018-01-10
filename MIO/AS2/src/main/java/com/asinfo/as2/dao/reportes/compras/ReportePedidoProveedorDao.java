/*   1:    */ package com.asinfo.as2.dao.reportes.compras;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.DimensionContable;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   8:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ReportePedidoProveedorDao
/*  18:    */   extends AbstractDaoAS2<PedidoProveedor>
/*  19:    */ {
/*  20:    */   public ReportePedidoProveedorDao()
/*  21:    */   {
/*  22: 30 */     super(PedidoProveedor.class);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public List<Object[]> getReportePedidoProveedor(int idPedidoProveedor)
/*  26:    */   {
/*  27: 42 */     StringBuilder sql = new StringBuilder();
/*  28: 43 */     sql.append("SELECT e.nombreFiscal, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4), e.identificacion, p.fecha,");
/*  29: 44 */     sql.append(" p.fechaEntrega, dp.cantidad, pr.codigo, pr.nombreComercial, dp.precio, p.total, p.descuento, p.impuesto, de.telefono1,");
/*  30: 45 */     sql.append(" p.descripcion, p.numero, un.codigo, dc1.nombre, dc2.nombre, dc3.nombre, dp.descripcion, p.usuarioCambioEstado, p.fechaCambioEstado,");
/*  31: 46 */     sql.append(" dc4.nombre,dc5.nombre, pr.codigoAlterno, e.codigo, e.codigoAlterno, p.estado, p.numeroPedidoProveedor, p.referencia1 , p.referencia2,");
/*  32: 47 */     sql.append(" p.referencia3, p.referencia4, p.direccionEntrega, cp.nombre, pr.nombre, bo.nombre, pro.nombre, p.estado, p.referencia, p.email,");
/*  33: 48 */     sql.append(" p.telefonoRespuesta, p.emailRespuesta, CONCAT(prs.nombres,' ',prs.apellidos)");
/*  34: 49 */     sql.append(" FROM DetallePedidoProveedor dp");
/*  35: 50 */     sql.append(" LEFT JOIN dp.dimensionContable1 dc1");
/*  36: 51 */     sql.append(" LEFT JOIN dp.dimensionContable2 dc2");
/*  37: 52 */     sql.append(" LEFT JOIN dp.dimensionContable3 dc3");
/*  38: 53 */     sql.append(" LEFT JOIN dp.dimensionContable4 dc4");
/*  39: 54 */     sql.append(" LEFT JOIN dp.dimensionContable5 dc5");
/*  40: 55 */     sql.append(" INNER JOIN dp.producto pr");
/*  41: 56 */     sql.append(" INNER JOIN dp.pedidoProveedor p");
/*  42: 57 */     sql.append(" LEFT  JOIN p.condicionPago cp");
/*  43: 58 */     sql.append(" LEFT  JOIN p.personaResponsable prs");
/*  44: 59 */     sql.append(" LEFT  JOIN p.bodega bo");
/*  45: 60 */     sql.append(" LEFT  JOIN p.proyecto pro");
/*  46: 61 */     sql.append(" INNER JOIN dp.unidadCompra un");
/*  47: 62 */     sql.append(" INNER JOIN p.empresa e");
/*  48: 63 */     sql.append(" INNER JOIN p.direccionEmpresa de");
/*  49: 64 */     sql.append(" INNER JOIN de.ubicacion u");
/*  50: 65 */     sql.append(" WHERE p.idPedidoProveedor = :idPedidoProveedor");
/*  51: 66 */     sql.append(" ORDER BY dp.idDetallePedidoProveedor");
/*  52:    */     
/*  53: 68 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/*  54: 69 */     return query.getResultList();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<Object[]> getListaReportePedidoProveedor(int idOrganizacion, Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, Empresa proveedor, Documento documento, boolean totalizado, boolean porProducto, DimensionContable dimensionContable, Integer numeroDimension, Estado estado)
/*  58:    */   {
/*  59:    */     StringBuilder sql;
/*  60: 79 */     if (totalizado)
/*  61:    */     {
/*  62: 80 */       StringBuilder sql = new StringBuilder();
/*  63: 81 */       sql.append(" SELECT f.numero, f.fecha, e.codigo, e.identificacion, e.nombreFiscal, e.nombreComercial, f.total, f.descuento, f.impuesto, f.descripcion ");
/*  64: 82 */       sql.append(" FROM PedidoProveedor f ");
/*  65: 83 */       sql.append(" INNER JOIN f.empresa e ");
/*  66: 84 */       sql.append(" INNER JOIN f.documento d ");
/*  67: 85 */       sql.append(" WHERE f.idOrganizacion=:idOrganizacion ");
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 88 */       sql = new StringBuilder();
/*  72: 89 */       sql.append(" SELECT f.numero, f.fecha, e.codigo, e.identificacion, e.nombreFiscal, e.nombreComercial, f.total, f.descuento, f.impuesto, f.descripcion, ");
/*  73: 90 */       sql.append(" p.codigo, p.codigoBarras, p.nombre, p.nombreComercial, dp.cantidad, dp.precio, dp.descuento, dp.descripcion");
/*  74: 92 */       if ((numeroDimension != null) && (numeroDimension.intValue() > 0)) {
/*  75: 93 */         sql.append(" ,dc.codigo, dc.nombre");
/*  76:    */       } else {
/*  77: 95 */         sql.append(" ,'', '' ");
/*  78:    */       }
/*  79: 97 */       sql.append(" FROM  DetallePedidoProveedor dp ");
/*  80: 98 */       sql.append(" INNER JOIN dp.pedidoProveedor f ");
/*  81: 99 */       sql.append(" INNER JOIN f.empresa e ");
/*  82:100 */       sql.append(" INNER JOIN f.documento d ");
/*  83:101 */       sql.append(" INNER JOIN dp.producto p ");
/*  84:103 */       if ((numeroDimension != null) && (numeroDimension.intValue() > 0)) {
/*  85:104 */         sql.append(" JOIN dp.dimensionContable" + numeroDimension + " dc");
/*  86:    */       }
/*  87:107 */       sql.append(" WHERE f.idOrganizacion=:idOrganizacion ");
/*  88:109 */       if ((numeroDimension != null) && (numeroDimension.intValue() > 0) && (dimensionContable != null)) {
/*  89:110 */         sql.append(" AND EXISTS (SELECT 1 FROM DimensionContable dc_e WHERE dc_e.idDimensionContable = dc.idDimensionContable AND dc_e.codigo LIKE :codigoDimension )");
/*  90:    */       }
/*  91:    */     }
/*  92:114 */     sql.append(" AND f.estado != :estadoAnulado ");
/*  93:115 */     sql.append(" AND d.documentoBase = :documentoBase ");
/*  94:    */     
/*  95:117 */     sql.append(" AND CASE WHEN f.fechaCambioEstado IS NULL THEN f.fecha ELSE f.fechaCambioEstado END >= :fechaDesde ");
/*  96:118 */     sql.append(" AND CASE WHEN f.fechaCambioEstado IS NULL THEN f.fecha ELSE f.fechaCambioEstado END <= :fechaHasta ");
/*  97:120 */     if (proveedor != null) {
/*  98:121 */       sql.append(" AND e=:proveedor ");
/*  99:    */     }
/* 100:123 */     if (documento != null) {
/* 101:124 */       sql.append(" AND d=:documento ");
/* 102:    */     }
/* 103:126 */     if (estado != null) {
/* 104:127 */       sql.append(" AND f.estado=:estado ");
/* 105:    */     }
/* 106:130 */     if (numeroDesde.trim().length() != 0) {
/* 107:131 */       sql.append(" AND f.numero >= :numeroDesde ");
/* 108:    */     }
/* 109:134 */     if (numeroHasta.trim().length() != 0) {
/* 110:135 */       sql.append(" AND f.numero <= :numeroHasta ");
/* 111:    */     }
/* 112:138 */     if (totalizado) {
/* 113:140 */       sql.append(" ORDER BY f.fecha, f.numero ");
/* 114:141 */     } else if (porProducto) {
/* 115:143 */       sql.append(" ORDER BY p.nombre, f.fecha,f.numero ");
/* 116:    */     } else {
/* 117:146 */       sql.append(" ORDER BY f.fecha, f.numero, p.nombre");
/* 118:    */     }
/* 119:149 */     Query query = this.em.createQuery(sql.toString());
/* 120:150 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 121:151 */     query.setParameter("fechaDesde", fechaDesde);
/* 122:152 */     query.setParameter("fechaHasta", fechaHasta);
/* 123:153 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 124:154 */     query.setParameter("documentoBase", DocumentoBase.PEDIDO_PROVEEDOR);
/* 125:156 */     if (proveedor != null) {
/* 126:157 */       query.setParameter("proveedor", proveedor);
/* 127:    */     }
/* 128:159 */     if (documento != null) {
/* 129:160 */       query.setParameter("documento", documento);
/* 130:    */     }
/* 131:162 */     if (estado != null) {
/* 132:163 */       query.setParameter("estado", estado);
/* 133:    */     }
/* 134:166 */     if ((!totalizado) && (numeroDimension != null) && (numeroDimension.intValue() > 0) && (dimensionContable != null)) {
/* 135:167 */       query.setParameter("codigoDimension", dimensionContable.getCodigo() + "%");
/* 136:    */     }
/* 137:170 */     if (numeroDesde.trim().length() != 0) {
/* 138:171 */       query.setParameter("numeroDesde", numeroDesde);
/* 139:    */     }
/* 140:174 */     if (numeroHasta.trim().length() != 0) {
/* 141:175 */       query.setParameter("numeroHasta", numeroHasta);
/* 142:    */     }
/* 143:178 */     return query.getResultList();
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.ReportePedidoProveedorDao
 * JD-Core Version:    0.7.0.1
 */