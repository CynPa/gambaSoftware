/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.pagos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.entities.DetalleFormaPago;
/*   6:    */ import com.asinfo.as2.entities.Pago;
/*   7:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*   8:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ReportePagoProveedorDao
/*  19:    */   extends AbstractDaoAS2<Pago>
/*  20:    */ {
/*  21:    */   public ReportePagoProveedorDao()
/*  22:    */   {
/*  23: 22 */     super(Pago.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List getReportePagoProveedor(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/*  27:    */     throws ExcepcionAS2
/*  28:    */   {
/*  29: 29 */     boolean agrupadoCategoriaEmpresa = (categoriaEmpresa != null) && (categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()));
/*  30:    */     
/*  31:    */ 
/*  32: 32 */     StringBuilder sql = new StringBuilder();
/*  33: 33 */     sql.append(" SELECT em.identificacion, em.nombreFiscal, p.numero, p.fecha, ");
/*  34: 34 */     sql.append(" CONCAT(fpSRI.establecimiento, ' - ', fpSRI.puntoEmision,' - ', fpSRI.numero), ");
/*  35: 35 */     sql.append(" p.estado, p.descripcion,a.numero, ta.nombre, dp.valor, catem.idCategoriaEmpresa, catem.nombre ");
/*  36: 36 */     sql.append(" FROM  DetallePago dp ");
/*  37: 37 */     sql.append(" INNER JOIN dp.pago p ");
/*  38: 38 */     sql.append(" INNER JOIN p.documento d ");
/*  39: 39 */     sql.append(" INNER JOIN dp.cuentaPorPagar cp ");
/*  40: 40 */     sql.append(" INNER JOIN cp.facturaProveedor fp ");
/*  41: 41 */     sql.append(" LEFT  JOIN fp.facturaProveedorSRI fpSRI ");
/*  42: 42 */     sql.append(" INNER JOIN fp.empresa em ");
/*  43: 43 */     sql.append(" LEFT  JOIN em.categoriaEmpresa catem ");
/*  44: 44 */     sql.append(" LEFT  OUTER JOIN p.asiento a ");
/*  45: 45 */     sql.append(" LEFT  OUTER JOIN a.tipoAsiento ta ");
/*  46: 46 */     sql.append(" WHERE (em.idEmpresa = :idProveedor OR :idProveedor=0) ");
/*  47: 47 */     sql.append(" AND   d.documentoBase = :documentoBase AND p.fecha BETWEEN :fechaDesde AND :fechaHasta AND p.estado!=:estadoAnulado");
/*  48: 48 */     sql.append(" AND   p.idOrganizacion=:idOrganizacion");
/*  49: 49 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  50: 50 */       sql.append(" AND catem =:categoriaEmpresa ");
/*  51:    */     }
/*  52: 52 */     if ((categoriaEmpresa != null) && (agrupadoCategoriaEmpresa)) {
/*  53: 53 */       sql.append(" ORDER BY catem.nombre, em.nombreFiscal, p.numero ");
/*  54:    */     } else {
/*  55: 55 */       sql.append(" ORDER BY em.nombreFiscal, p.numero ");
/*  56:    */     }
/*  57: 58 */     Query query = this.em.createQuery(sql.toString());
/*  58: 59 */     query.setParameter("fechaDesde", fechaDesde);
/*  59: 60 */     query.setParameter("fechaHasta", fechaHasta);
/*  60: 61 */     query.setParameter("documentoBase", DocumentoBase.PAGO_PROVEEDOR);
/*  61: 62 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  62: 63 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  63: 64 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  64: 65 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  65: 66 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/*  66:    */     }
/*  67: 69 */     return query.getResultList();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List getReportePago(int idPago)
/*  71:    */   {
/*  72: 74 */     String sql = " SELECT e.nombreFiscal, e.identificacion, p.numero, p.fecha, p.valor, p.descripcion, fp.numero, fp.fecha,  cp.fechaVencimiento, cp.numeroCuota, cp.saldo, dp.valor,  coalesce(CONCAT(fpSRI.establecimiento, ' - ', fpSRI.puntoEmision,' - ', fpSRI.numero),fp.referencia3) FROM DetallePago dp  INNER JOIN dp.pago p INNER JOIN p.empresa e INNER JOIN dp.cuentaPorPagar cp INNER JOIN cp.facturaProveedor fp  LEFT JOIN fp.facturaProveedorSRI fpSRI WHERE p.idPago = :idPago ";
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78: 80 */     Query query = this.em.createQuery(" SELECT e.nombreFiscal, e.identificacion, p.numero, p.fecha, p.valor, p.descripcion, fp.numero, fp.fecha,  cp.fechaVencimiento, cp.numeroCuota, cp.saldo, dp.valor,  coalesce(CONCAT(fpSRI.establecimiento, ' - ', fpSRI.puntoEmision,' - ', fpSRI.numero),fp.referencia3) FROM DetallePago dp  INNER JOIN dp.pago p INNER JOIN p.empresa e INNER JOIN dp.cuentaPorPagar cp INNER JOIN cp.facturaProveedor fp  LEFT JOIN fp.facturaProveedorSRI fpSRI WHERE p.idPago = :idPago ").setParameter("idPago", Integer.valueOf(idPago));
/*  79: 81 */     return query.getResultList();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<Object[]> getCuentaBancariaProveedor(List<Integer> lproveedor)
/*  83:    */   {
/*  84: 91 */     StringBuilder sql = new StringBuilder();
/*  85: 92 */     sql.append(" SELECT  e.idEmpresa, cb.numero, tcb.codigo, ban.codigo, cb.tipoServicioCuentaBancariaProveedor, cb.numero, tcb.nombre, ban.nombre, cb.predeterminado");
/*  86:    */     
/*  87:    */ 
/*  88: 95 */     sql.append(" FROM    CuentaBancariaEmpresa cbe ");
/*  89: 96 */     sql.append(" LEFT    JOIN cbe.cuentaBancaria cb ");
/*  90: 97 */     sql.append(" INNER   JOIN cbe.empresa e ");
/*  91: 98 */     sql.append(" INNER   JOIN e.proveedor p ");
/*  92: 99 */     sql.append(" INNER   JOIN cb.tipoCuentaBancaria tcb ");
/*  93:100 */     sql.append(" INNER   JOIN cb.banco ban ");
/*  94:101 */     sql.append(" WHERE e.idEmpresa IN (:lproveedor) ");
/*  95:102 */     sql.append(" AND cb.predeterminado = true ");
/*  96:103 */     Query query = this.em.createQuery(sql.toString());
/*  97:104 */     query.setParameter("lproveedor", lproveedor);
/*  98:105 */     List<Object[]> lista = query.getResultList();
/*  99:106 */     return lista;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<Object[]> getDireccionTelefonoProveedor(List<Integer> lproveedor)
/* 103:    */   {
/* 104:116 */     StringBuilder sql = new StringBuilder();
/* 105:    */     
/* 106:118 */     sql.append(" SELECT e.idEmpresa , CONCAT(ub.direccion1, ub.direccion2, ub.direccion3, ub.direccion4), de.telefono1, ci.nombre ");
/* 107:119 */     sql.append(" FROM DireccionEmpresa de ");
/* 108:120 */     sql.append(" INNER JOIN de.empresa e INNER JOIN e.proveedor p INNER JOIN de.ubicacion ub INNER JOIN de.ciudad ci ");
/* 109:121 */     sql.append(" WHERE de.indicadorDireccionPrincipal = true ");
/* 110:122 */     sql.append(" AND e.idEmpresa IN (:lproveedor) ");
/* 111:123 */     Query query = this.em.createQuery(sql.toString());
/* 112:124 */     query.setParameter("lproveedor", lproveedor);
/* 113:    */     
/* 114:126 */     return query.getResultList();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<DetalleFormaPago> getDetalleFormaCobro(int idPago)
/* 118:    */   {
/* 119:131 */     StringBuilder sql = new StringBuilder();
/* 120:132 */     sql.append(" SELECT dfp ");
/* 121:133 */     sql.append(" FROM DetalleFormaPago dfp JOIN FETCH dfp.formaPago fp INNER JOIN dfp.pago p");
/* 122:134 */     sql.append(" WHERE p.idPago = :idPago ");
/* 123:    */     
/* 124:136 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPago", Integer.valueOf(idPago));
/* 125:137 */     return query.getResultList();
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.pagos.ReportePagoProveedorDao
 * JD-Core Version:    0.7.0.1
 */