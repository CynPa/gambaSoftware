/*  1:   */ package com.asinfo.as2.dao.reportes.ventas;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.PedidoCliente;
/*  5:   */ import java.util.Date;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ReportePedidoOrdenFabricacionDao
/* 13:   */   extends AbstractDaoAS2<PedidoCliente>
/* 14:   */ {
/* 15:   */   public ReportePedidoOrdenFabricacionDao()
/* 16:   */   {
/* 17:18 */     super(PedidoCliente.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List getReportePedidoOrdenFabricacion(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean indicadorSaldosPendientes, int idOrganizacion)
/* 21:   */   {
/* 22:31 */     StringBuffer totalFacturado = new StringBuffer();
/* 23:32 */     totalFacturado.append("COALESCE ((");
/* 24:33 */     totalFacturado.append("\tSELECT SUM(CASE WHEN dfc.cantidad IS NOT NULL THEN dfc.cantidad ELSE 0 END) ");
/* 25:34 */     totalFacturado.append("\tFROM DetalleFacturaCliente dfc ");
/* 26:35 */     totalFacturado.append("\tINNER JOIN dfc.detallePedidoCliente dpca ");
/* 27:36 */     totalFacturado.append("\tWHERE dpca.idDetallePedidoCliente=dpc.idDetallePedidoCliente ");
/* 28:37 */     totalFacturado.append("\tGROUP BY dpca.idDetallePedidoCliente), 0) ");
/* 29:   */     
/* 30:39 */     StringBuffer totalDespachado = new StringBuffer();
/* 31:40 */     totalDespachado.append("COALESCE ((");
/* 32:41 */     totalDespachado.append("\tSELECT SUM (CASE WHEN ddc.cantidad IS NOT NULL THEN ddc.cantidad ELSE 0 END) ");
/* 33:42 */     totalDespachado.append("\tFROM DetalleOrdenFabricacion ddc ");
/* 34:43 */     totalDespachado.append("\tINNER JOIN ddc.detallePedido dpcaa ");
/* 35:44 */     totalDespachado.append("\tWHERE dpcaa.idDetallePedidoCliente=dpc.idDetallePedidoCliente ");
/* 36:45 */     totalDespachado.append("\tGROUP BY dpcaa.idDetallePedidoCliente), 0) ");
/* 37:   */     
/* 38:47 */     StringBuffer sql = new StringBuffer();
/* 39:48 */     sql.append("SELECT  pc.numero, e.nombreFiscal, p.nombre, p.volumen, p.peso, dpc.cantidad, ");
/* 40:49 */     sql.append(totalFacturado);
/* 41:50 */     sql.append(",");
/* 42:51 */     sql.append(totalDespachado);
/* 43:52 */     sql.append(" FROM DetallePedidoCliente dpc ");
/* 44:53 */     sql.append(" INNER JOIN dpc.pedidoCliente pc ");
/* 45:54 */     sql.append(" INNER JOIN pc.empresa e ");
/* 46:55 */     sql.append(" INNER JOIN dpc.producto p ");
/* 47:56 */     sql.append(" WHERE dpc.pedidoCliente.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 48:57 */     sql.append(" AND dpc.pedidoCliente.idOrganizacion = :idOrganizacion ");
/* 49:59 */     if (indicadorSaldosPendientes)
/* 50:   */     {
/* 51:60 */       sql.append(" AND (dpc.cantidad - ");
/* 52:61 */       sql.append(totalDespachado);
/* 53:62 */       sql.append("\t) <> 0");
/* 54:   */     }
/* 55:65 */     if (idEmpresa != 0) {
/* 56:66 */       sql.append(" AND e.idEmpresa = :idEmpresa");
/* 57:   */     }
/* 58:69 */     Query query = this.em.createQuery(sql.toString());
/* 59:70 */     query.setParameter("fechaDesde", fechaDesde);
/* 60:71 */     query.setParameter("fechaHasta", fechaHasta);
/* 61:72 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 62:73 */     if (idEmpresa != 0) {
/* 63:74 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 64:   */     }
/* 65:76 */     return query.getResultList();
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.ReportePedidoOrdenFabricacionDao
 * JD-Core Version:    0.7.0.1
 */