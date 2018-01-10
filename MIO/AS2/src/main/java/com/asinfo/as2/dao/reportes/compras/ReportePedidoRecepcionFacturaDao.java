/*   1:    */ package com.asinfo.as2.dao.reportes.compras;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ReportePedidoRecepcionFacturaDao
/*  16:    */   extends AbstractDaoAS2<DetalleFacturaProveedor>
/*  17:    */ {
/*  18:    */   public ReportePedidoRecepcionFacturaDao()
/*  19:    */   {
/*  20: 35 */     super(DetalleFacturaProveedor.class);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public List getReportePedidoRecepcionFactura(PedidoProveedor pedidoProveedor, Date fechaDesde, Date fechaHasta, Empresa proveedor)
/*  24:    */     throws ExcepcionAS2
/*  25:    */   {
/*  26: 42 */     StringBuilder sql = new StringBuilder();
/*  27: 43 */     sql.append("SELECT '-', '-', rp.numero, rp.fecha, COALESCE(dpp.cantidad,0)*0, COALESCE(drp.cantidad,0), COALESCE(dpp.cantidad,0), ");
/*  28: 44 */     sql.append(" pp.numero, pp.fecha, p.codigo, p.nombre, e.nombreFiscal, rp.idRecepcionProveedor, drp.idDetalleRecepcionProveedor, ");
/*  29: 45 */     sql.append(" pp.idPedidoProveedor, dpp.idDetallePedidoProveedor ");
/*  30: 46 */     sql.append(" FROM DetalleRecepcionProveedor drp ");
/*  31: 47 */     sql.append(" RIGHT OUTER JOIN drp.detallePedidoProveedor dpp ");
/*  32: 48 */     sql.append(" LEFT OUTER JOIN drp.detalleFacturaProveedor dfp ");
/*  33: 49 */     sql.append(" INNER JOIN dpp.producto p ");
/*  34: 50 */     sql.append(" INNER JOIN dpp.pedidoProveedor pp ");
/*  35: 51 */     sql.append(" INNER JOIN pp.empresa e ");
/*  36: 52 */     sql.append(" INNER JOIN drp.recepcionProveedor rp ");
/*  37: 53 */     sql.append(" WHERE pp IS NOT NULL AND (:pedidoProveedor IS NULL OR pp=:pedidoProveedor) ");
/*  38: 54 */     sql.append(" AND pp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  39: 55 */     sql.append(" AND (:proveedor IS NULL OR e=:proveedor) ");
/*  40: 56 */     sql.append(" AND dfp.facturaProveedor IS NULL ");
/*  41:    */     
/*  42:    */ 
/*  43: 59 */     Query query = this.em.createQuery(sql.toString()).setParameter("pedidoProveedor", pedidoProveedor).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("proveedor", proveedor);
/*  44:    */     
/*  45: 61 */     return query.getResultList();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List getReporteRecepcionSinFactura(PedidoProveedor pedidoProveedor, Date fechaDesde, Date fechaHasta, Empresa proveedor)
/*  49:    */     throws ExcepcionAS2
/*  50:    */   {
/*  51: 74 */     StringBuilder sql = new StringBuilder();
/*  52: 75 */     sql.append("SELECT fp.numero, fp.fecha, '-', '-', COALESCE(dfp.cantidad,0), COALESCE(dpp.cantidad,0)*0, COALESCE(dpp.cantidad,0), ");
/*  53: 76 */     sql.append(" pp.numero, pp.fecha, p.codigo, p.nombre, e.nombreFiscal, fp.idFacturaProveedor, dfp.idDetalleFacturaProveedor, ");
/*  54: 77 */     sql.append(" pp.idPedidoProveedor, dpp.idDetallePedidoProveedor ");
/*  55: 78 */     sql.append(" FROM DetalleFacturaProveedor dfp ");
/*  56: 79 */     sql.append(" RIGHT OUTER JOIN dfp.detallePedidoProveedor dpp ");
/*  57: 80 */     sql.append(" LEFT OUTER JOIN dfp.detalleRecepcionProveedorDevolucion drp ");
/*  58: 81 */     sql.append(" INNER JOIN dpp.pedidoProveedor pp ");
/*  59: 82 */     sql.append(" INNER JOIN dfp.facturaProveedor fp ");
/*  60: 83 */     sql.append(" INNER JOIN dpp.producto p ");
/*  61: 84 */     sql.append(" INNER JOIN pp.empresa e ");
/*  62: 85 */     sql.append(" WHERE pp IS NOT NULL AND (:pedidoProveedor IS NULL OR pp=:pedidoProveedor) ");
/*  63: 86 */     sql.append(" AND pp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  64: 87 */     sql.append(" AND (:proveedor IS NULL OR e=:proveedor) ");
/*  65: 88 */     sql.append(" AND drp.recepcionProveedor is null ");
/*  66:    */     
/*  67:    */ 
/*  68: 91 */     Query query = this.em.createQuery(sql.toString()).setParameter("pedidoProveedor", pedidoProveedor).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("proveedor", proveedor);
/*  69:    */     
/*  70: 93 */     return query.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List getReportePedidoConFactura(PedidoProveedor pedidoProveedor, Date fechaDesde, Date fechaHasta, Empresa proveedor)
/*  74:    */     throws ExcepcionAS2
/*  75:    */   {
/*  76:105 */     StringBuilder sql = new StringBuilder();
/*  77:106 */     sql.append("SELECT fp.numero, fp.fecha, rp.numero, rp.fecha, COALESCE(dfp.cantidad,0), COALESCE(drp.cantidad,0), COALESCE(dpp.cantidad,0), ");
/*  78:107 */     sql.append(" pp.numero, pp.fecha, p.codigo, p.nombre, e.nombreFiscal, fp.idFacturaProveedor, dfp.idDetalleFacturaProveedor, ");
/*  79:108 */     sql.append(" rp.idRecepcionProveedor, drp.idDetalleRecepcionProveedor, pp.idPedidoProveedor, dpp.idDetallePedidoProveedor ");
/*  80:109 */     sql.append(" FROM DetalleFacturaProveedor dfp ");
/*  81:110 */     sql.append(" INNER JOIN dfp.facturaProveedor fp ");
/*  82:111 */     sql.append(" LEFT OUTER JOIN dfp.detalleRecepcionProveedorDevolucion drp ");
/*  83:112 */     sql.append(" LEFT OUTER JOIN fp.recepcionProveedor rp ");
/*  84:113 */     sql.append(" LEFT OUTER JOIN drp.detallePedidoProveedor dpp ");
/*  85:114 */     sql.append(" INNER JOIN dpp.producto p ");
/*  86:115 */     sql.append(" LEFT OUTER JOIN rp.pedidoProveedor pp ");
/*  87:116 */     sql.append(" INNER JOIN pp.empresa e ");
/*  88:117 */     sql.append(" WHERE pp IS NOT NULL ");
/*  89:118 */     sql.append(" AND (:pedidoProveedor IS NULL OR pp=:pedidoProveedor) ");
/*  90:119 */     sql.append(" AND pp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  91:120 */     sql.append(" AND (:proveedor IS NULL OR e=:proveedor)");
/*  92:    */     
/*  93:    */ 
/*  94:123 */     Query query = this.em.createQuery(sql.toString()).setParameter("pedidoProveedor", pedidoProveedor).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("proveedor", proveedor);
/*  95:    */     
/*  96:125 */     return query.getResultList();
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.ReportePedidoRecepcionFacturaDao
 * JD-Core Version:    0.7.0.1
 */