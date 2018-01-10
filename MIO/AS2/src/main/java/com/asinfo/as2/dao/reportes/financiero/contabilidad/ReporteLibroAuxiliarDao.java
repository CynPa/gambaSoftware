/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.contabilidad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CentroCosto;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioEstadoFinancieroImpl;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ReporteLibroAuxiliarDao
/*  16:    */   extends AbstractDaoAS2<DetalleAsiento>
/*  17:    */ {
/*  18:    */   public ReporteLibroAuxiliarDao()
/*  19:    */   {
/*  20: 38 */     super(DetalleAsiento.class);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public List<DetalleAsiento> getReporteLibroAuxiliar(int idCuentaContable, Date fechaDesde, Date fechaHasta, boolean indicadorNIIF, int idSucursal, CentroCosto centroCosto, int idOrganizacion)
/*  24:    */   {
/*  25: 78 */     String sql = "";
/*  26: 79 */     if (centroCosto != null) {
/*  27: 80 */       sql = "SELECT new DetalleAsiento(CASE WHEN da.debe>0 THEN dacc.valor ELSE 0 END , CASE WHEN da.haber > 0 THEN dacc.valor ELSE 0 END, da.descripcion, at.concepto, ta.nombre, at.numero, at.fecha,mb.documentoReferencia,mb.beneficiario)  FROM DetalleAsientoCentroCosto dacc INNER JOIN dacc.centroCosto ccc INNER JOIN dacc.detalleAsiento da";
/*  28:    */     } else {
/*  29: 83 */       sql = "SELECT new DetalleAsiento(da.debe,da.haber,da.descripcion, at.concepto, ta.nombre, at.numero, at.fecha,mb.documentoReferencia,mb.beneficiario)  FROM DetalleAsiento da";
/*  30:    */     }
/*  31: 87 */     sql = sql + " INNER JOIN da.cuentaContable cc" + " INNER JOIN da.asiento at " + " INNER JOIN at.tipoAsiento ta " + " LEFT  JOIN da.movimientoBancario mb " + " WHERE cc.idCuentaContable = :idCuentaContable " + " AND (ta.indicadorNIIF = :indicadorNIIF OR :indicadorNIIF = true)" + " AND at.estado IN (:estado) AND at.fecha BETWEEN :fechaDesde AND :fechaHasta AND at.idOrganizacion = :idOrganizacion";
/*  32: 92 */     if (centroCosto != null) {
/*  33: 93 */       sql = sql + " AND ccc.codigo LIKE :codigoCentroCosto";
/*  34:    */     }
/*  35: 96 */     if (idSucursal > 0) {
/*  36: 97 */       sql = sql + " AND at.sucursal.idSucursal = :idSucursal";
/*  37:    */     }
/*  38:100 */     sql = sql + " ORDER BY at.fecha";
/*  39:    */     
/*  40:102 */     Query query = this.em.createQuery(sql);
/*  41:    */     
/*  42:104 */     query.setParameter("fechaDesde", fechaDesde);
/*  43:105 */     query.setParameter("fechaHasta", fechaHasta);
/*  44:106 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/*  45:107 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/*  46:108 */     query.setParameter("idCuentaContable", Integer.valueOf(idCuentaContable));
/*  47:110 */     if (centroCosto != null) {
/*  48:111 */       query.setParameter("codigoCentroCosto", centroCosto.getCodigo() + "%");
/*  49:    */     }
/*  50:113 */     if (idSucursal > 0) {
/*  51:114 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  52:    */     }
/*  53:117 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  54:118 */     List<DetalleAsiento> listaDetalleAsiento = query.getResultList();
/*  55:119 */     return listaDetalleAsiento;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<DetalleAsiento> getReporteLibroAuxiliar(List<CuentaContable> listaCuentaContable, Date fechaDesde, Date fechaHasta, boolean indicadorNIIF, int idSucursal, String dimension, String codigoDimension, int idOrganizacion)
/*  59:    */   {
/*  60:126 */     StringBuilder sql = new StringBuilder();
/*  61:    */     
/*  62:128 */     sql.append(" SELECT new DetalleAsiento(cc.idCuentaContable, da.debe,da.haber,da.descripcion, at.concepto, ta.nombre, at.numero, at.fecha,mb.documentoReferencia,mb.beneficiario, da.descripcion2, da.usuarioCreacion, da.usuarioModificacion, da.fechaCreacion, da.fechaModificacion) ");
/*  63:129 */     sql.append(" FROM DetalleAsiento da ");
/*  64:130 */     sql.append(" INNER JOIN da.cuentaContable cc ");
/*  65:131 */     sql.append(" INNER JOIN da.asiento at ");
/*  66:132 */     sql.append(" INNER JOIN at.tipoAsiento ta ");
/*  67:133 */     sql.append(" LEFT  JOIN da.movimientoBancario mb ");
/*  68:134 */     if (!dimension.isEmpty()) {
/*  69:135 */       sql.append(" LEFT  JOIN da.dimensionContable" + dimension + " dim ");
/*  70:    */     }
/*  71:137 */     sql.append(" WHERE cc in (:listaCuentaContable) ");
/*  72:138 */     sql.append(" AND (ta.indicadorNIIF = :indicadorNIIF OR :indicadorNIIF = true) ");
/*  73:139 */     sql.append(" AND at.estado IN (:estado) AND at.fecha BETWEEN :fechaDesde AND :fechaHasta AND at.idOrganizacion = :idOrganizacion ");
/*  74:141 */     if (!dimension.isEmpty()) {
/*  75:142 */       sql.append(" AND dim.codigo LIKE :codigoDimension ");
/*  76:    */     }
/*  77:145 */     if (idSucursal > 0) {
/*  78:146 */       sql.append(" AND at.sucursal.idSucursal = :idSucursal ");
/*  79:    */     }
/*  80:149 */     sql.append(" ORDER BY at.fecha ");
/*  81:    */     
/*  82:151 */     Query query = this.em.createQuery(sql.toString());
/*  83:    */     
/*  84:153 */     query.setParameter("fechaDesde", fechaDesde);
/*  85:154 */     query.setParameter("fechaHasta", fechaHasta);
/*  86:155 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/*  87:156 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/*  88:157 */     query.setParameter("listaCuentaContable", listaCuentaContable);
/*  89:159 */     if (!dimension.isEmpty()) {
/*  90:160 */       query.setParameter("codigoDimension", codigoDimension + "%");
/*  91:    */     }
/*  92:162 */     if (idSucursal > 0) {
/*  93:163 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  94:    */     }
/*  95:166 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  96:167 */     List<DetalleAsiento> listaDetalleAsiento = query.getResultList();
/*  97:168 */     return listaDetalleAsiento;
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteLibroAuxiliarDao
 * JD-Core Version:    0.7.0.1
 */