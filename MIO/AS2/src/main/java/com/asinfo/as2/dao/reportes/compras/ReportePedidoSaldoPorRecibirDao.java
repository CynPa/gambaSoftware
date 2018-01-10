/*   1:    */ package com.asinfo.as2.dao.reportes.compras;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ReportePedidoSaldoPorRecibirDao
/*  19:    */   extends AbstractDaoAS2<PedidoProveedor>
/*  20:    */ {
/*  21:    */   public ReportePedidoSaldoPorRecibirDao()
/*  22:    */   {
/*  23: 39 */     super(PedidoProveedor.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<Object[]> getReportePedidoSaldoPorRecibir(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/*  27:    */   {
/*  28: 53 */     String sql = "SELECT pp.numero, e.nombreFiscal, p.nombre, p.volumen, p.peso, dpp.cantidad, COALESCE ((\tSELECT SUM(CASE WHEN dfp.cantidad IS NOT NULL THEN dfp.cantidad ELSE 0 END) FROM DetalleFacturaProveedor dfp LEFT JOIN dfp.detallePedidoProveedor dppa WHERE ( COALESCE(dppa.idDetallePedidoProveedor,0) = dpp.idDetallePedidoProveedor)  OR ( COALESCE(dppa.idDetallePedidoProveedor,0) != dpp.idDetallePedidoProveedor AND dfp.idDetalleFacturaProveedor IN  (SELECT dfpb.idDetalleFacturaProveedor FROM DetalleRecepcionProveedor drpb INNER JOIN drpb.detallePedidoProveedor dppb INNER JOIN drpb.detalleFacturaProveedor dfpb WHERE dppb.idDetallePedidoProveedor = dpp.idDetallePedidoProveedor)) GROUP BY dppa.idDetallePedidoProveedor ), 0), COALESCE (( SELECT SUM(CASE WHEN drp.cantidad IS NOT NULL THEN drp.cantidad ELSE 0 END) FROM DetalleRecepcionProveedor drp INNER JOIN drp.detallePedidoProveedor dppaa WHERE dppaa.idDetallePedidoProveedor = dpp.idDetallePedidoProveedor GROUP BY dppaa.idDetallePedidoProveedor ), 0), pp.fecha FROM DetallePedidoProveedor dpp INNER JOIN dpp.pedidoProveedor pp INNER JOIN pp.empresa e INNER JOIN dpp.producto p LEFT JOIN p.subcategoriaProducto scp LEFT JOIN scp.categoriaProducto cp WHERE pp.fecha BETWEEN :fechaDesde AND :fechaHasta AND (pp.idOrganizacion = :idOrganizacion) AND pp.estado != :estadoElaborado  AND pp.estado != :estadoAnulado ";
/*  29: 81 */     if (idEmpresa != 0) {
/*  30: 82 */       sql = sql + " AND e.idEmpresa = :idEmpresa ";
/*  31:    */     }
/*  32: 84 */     if (soloPendientes) {
/*  33: 85 */       sql = sql + " AND pp.estado != :estadoCerrado ";
/*  34:    */     }
/*  35: 87 */     if (categoriaProducto != null) {
/*  36: 88 */       sql = sql + " AND cp.idCategoriaProducto = :idCategoriaProducto ";
/*  37:    */     }
/*  38: 90 */     if (subcategoriaProducto != null) {
/*  39: 91 */       sql = sql + " AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ";
/*  40:    */     }
/*  41: 94 */     sql = sql + " ORDER BY e.nombreFiscal, pp.numero, p.nombre ";
/*  42:    */     
/*  43: 96 */     Query query = this.em.createQuery(sql);
/*  44: 97 */     query.setParameter("fechaDesde", fechaDesde);
/*  45: 98 */     query.setParameter("fechaHasta", fechaHasta);
/*  46: 99 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/*  47:100 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  48:101 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  49:102 */     if (idEmpresa != 0) {
/*  50:103 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  51:    */     }
/*  52:105 */     if (soloPendientes) {
/*  53:106 */       query.setParameter("estadoCerrado", Estado.CERRADO);
/*  54:    */     }
/*  55:108 */     if (categoriaProducto != null) {
/*  56:109 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/*  57:    */     }
/*  58:111 */     if (subcategoriaProducto != null) {
/*  59:112 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/*  60:    */     }
/*  61:115 */     List<Object[]> listaCompleta = query.getResultList();
/*  62:116 */     List<Object[]> listaResult = new ArrayList();
/*  63:118 */     if (soloPendientes) {
/*  64:119 */       for (Object[] objects : listaCompleta)
/*  65:    */       {
/*  66:120 */         BigDecimal valorPedido = (BigDecimal)objects[5];
/*  67:121 */         BigDecimal valorFacturado = (BigDecimal)objects[6];
/*  68:122 */         BigDecimal valorDespachado = (BigDecimal)objects[7];
/*  69:123 */         if ((valorPedido.compareTo(valorFacturado) != 0) || (valorPedido.compareTo(valorDespachado) != 0)) {
/*  70:124 */           listaResult.add(objects);
/*  71:    */         }
/*  72:    */       }
/*  73:    */     } else {
/*  74:128 */       listaResult = listaCompleta;
/*  75:    */     }
/*  76:131 */     return listaResult;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<Object[]> getReporteRecepcionFactura(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/*  80:    */   {
/*  81:144 */     String sql = " SELECT fp.numero, fp.fecha, rp.numero, rp.fecha, SUM(COALESCE(dfp.cantidad,0)), SUM(COALESCE(drp.cantidad,0)), p.codigo, p.nombre, e.nombreFiscal FROM DetalleRecepcionProveedor drp   INNER JOIN drp.recepcionProveedor rp LEFT JOIN drp.detalleFacturaProveedor dfp  LEFT JOIN dfp.facturaProveedor fp  INNER JOIN rp.empresa e INNER JOIN drp.producto p WHERE (:idEmpresa = 0 OR e.idEmpresa=:idEmpresa) AND (:categoriaProducto IS NULL OR p.subcategoriaProducto.categoriaProducto=:categoriaProducto) AND (:subcategoriaProducto IS NULL OR p.subcategoriaProducto=:subcategoriaProducto) AND (rp.fecha BETWEEN :fechaDesde AND :fechaHasta)  AND (rp.idOrganizacion = :idOrganizacion) AND (rp.estado <> :estado)";
/*  82:161 */     if (soloPendientes) {
/*  83:162 */       sql = sql + " AND (p.tipoProducto = :tipoProducto)";
/*  84:    */     }
/*  85:164 */     sql = sql + " GROUP BY fp.numero, fp.fecha, rp.numero, rp.fecha, p.codigo, p.nombre, e.nombreFiscal ";
/*  86:165 */     if (soloPendientes) {
/*  87:166 */       sql = sql + " HAVING SUM(COALESCE(drp.cantidad,0)) > SUM(COALESCE(dfp.cantidad,0)) ";
/*  88:    */     }
/*  89:169 */     Query query = this.em.createQuery(sql);
/*  90:170 */     query.setParameter("fechaDesde", fechaDesde);
/*  91:171 */     query.setParameter("fechaHasta", fechaHasta);
/*  92:172 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  93:173 */     query.setParameter("categoriaProducto", categoriaProducto);
/*  94:174 */     query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  95:175 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  96:176 */     query.setParameter("estado", Estado.ANULADO);
/*  97:178 */     if (soloPendientes) {
/*  98:179 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/*  99:    */     }
/* 100:182 */     return query.getResultList();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List getReporteFacturaRecepcion(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 104:    */   {
/* 105:193 */     String sql = " SELECT fp.numero, fp.fecha, rp.numero, rp.fecha, SUM(COALESCE(dfp.cantidad,0)), SUM(COALESCE (drp.cantidad,0)), p.codigo,p.nombre, e.nombreFiscal FROM DetalleFacturaProveedor dfp  INNER JOIN dfp.facturaProveedor fp  INNER JOIN fp.empresa e LEFT JOIN dfp.listaDetalleRecepcionProveedor drp LEFT JOIN drp.recepcionProveedor rp INNER JOIN dfp.producto p WHERE (:idEmpresa = 0 OR e.idEmpresa=:idEmpresa) AND (fp.fecha BETWEEN :fechaDesde AND :fechaHasta)  AND (:categoriaProducto IS NULL OR p.subcategoriaProducto.categoriaProducto=:categoriaProducto) AND (:subcategoriaProducto IS NULL OR p.subcategoriaProducto=:subcategoriaProducto) AND (fp.idOrganizacion = :idOrganizacion) AND (fp.estado <> :estado)";
/* 106:204 */     if (soloPendientes) {
/* 107:205 */       sql = sql + " AND (p.tipoProducto = :tipoProducto)";
/* 108:    */     }
/* 109:208 */     sql = sql + " GROUP BY fp.numero, fp.fecha, rp.numero, rp.fecha, p.codigo,p.nombre, e.nombreFiscal ";
/* 110:209 */     if (soloPendientes) {
/* 111:210 */       sql = sql + " HAVING  SUM(COALESCE(dfp.cantidad,0)) > SUM(COALESCE (drp.cantidad,0)) ";
/* 112:    */     }
/* 113:213 */     Query query = this.em.createQuery(sql);
/* 114:214 */     query.setParameter("fechaDesde", fechaDesde);
/* 115:215 */     query.setParameter("fechaHasta", fechaHasta);
/* 116:216 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 117:217 */     query.setParameter("categoriaProducto", categoriaProducto);
/* 118:218 */     query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 119:219 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 120:220 */     query.setParameter("estado", Estado.ANULADO);
/* 121:222 */     if (soloPendientes) {
/* 122:223 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 123:    */     }
/* 124:226 */     return query.getResultList();
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.ReportePedidoSaldoPorRecibirDao
 * JD-Core Version:    0.7.0.1
 */