/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.activos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.entities.HistoricoDepreciacion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ReporteDepreciacionDao
/*  16:    */   extends AbstractDaoAS2<HistoricoDepreciacion>
/*  17:    */ {
/*  18:    */   public ReporteDepreciacionDao()
/*  19:    */   {
/*  20: 40 */     super(HistoricoDepreciacion.class);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public List getReporteDepreciacionMensualNIIF(ActivoFijo activoFijo, Date fechaDesde, Date fechaHasta, boolean indicadorDepreciacionFiscal, boolean activo)
/*  24:    */   {
/*  25: 47 */     StringBuilder sql = new StringBuilder();
/*  26: 48 */     sql.append(" SELECT af.codigo, af.nombre, d.fechaInicioDepreciacion, ");
/*  27: 49 */     sql.append(" d.valorActivo, d.valorDepreciado, d.valorResidual, d.valorADepreciar, d.vidaUtil,  ");
/*  28: 50 */     sql.append(" dd.mes, dd.anio, dd.valor,af.descripcion ");
/*  29: 51 */     sql.append(" FROM DetalleDepreciacion dd ");
/*  30: 52 */     sql.append(" INNER JOIN dd.depreciacion d ");
/*  31: 53 */     sql.append(" INNER JOIN d.activoFijo af ");
/*  32: 54 */     sql.append(" WHERE d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal ");
/*  33: 55 */     sql.append(" AND dd.historicoDepreciacion IS NOT NULL ");
/*  34: 56 */     sql.append(" AND dd.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  35: 57 */     sql.append(" AND af.idOrganizacion = :idOrganizacion ");
/*  36: 58 */     if (activo) {
/*  37: 59 */       sql.append(" AND af.activo = :activo ");
/*  38:    */     }
/*  39: 61 */     if (activoFijo != null) {
/*  40: 62 */       sql.append(" AND af.idActivoFijo = :idActivoFijo");
/*  41:    */     }
/*  42: 64 */     sql.append(" ORDER BY af.codigo,dd.anio,dd.mes");
/*  43:    */     
/*  44: 66 */     Query query = this.em.createQuery(sql.toString());
/*  45: 67 */     query.setParameter("fechaDesde", fechaDesde);
/*  46: 68 */     query.setParameter("fechaHasta", fechaHasta);
/*  47: 69 */     query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(indicadorDepreciacionFiscal));
/*  48: 70 */     query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/*  49: 72 */     if (activo) {
/*  50: 73 */       query.setParameter("activo", Boolean.valueOf(activo));
/*  51:    */     }
/*  52: 77 */     if (activoFijo != null) {
/*  53: 78 */       query.setParameter("idActivoFijo", Integer.valueOf(activoFijo.getIdActivoFijo()));
/*  54:    */     }
/*  55: 81 */     return query.getResultList();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List getReporteDepreciacionFiscalVsNIIF(ActivoFijo activoFijo, Date fechaDesde, Date fechaHasta, boolean activo, String dimension, String codigoDimension)
/*  59:    */   {
/*  60: 88 */     StringBuilder sql = new StringBuilder();
/*  61: 89 */     sql.append(" SELECT af.codigo, af.nombre, MIN(d.fechaInicioDepreciacion), ");
/*  62: 90 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=true THEN d.valorActivo ELSE 0 END) as valorActivoFiscal,  ");
/*  63: 91 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN d.valorActivo ELSE 0 END) as valorActivo,  ");
/*  64: 92 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=true THEN d.valorDepreciado ELSE 0 END) as valorDepreciadoFiscal,  ");
/*  65: 93 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN d.valorDepreciado ELSE 0 END) as valorDepreciado,  ");
/*  66: 94 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=true THEN d.valorResidual ELSE 0 END) as valorResidualFiscal,  ");
/*  67: 95 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN d.valorResidual ELSE 0 END) as valorResidual,  ");
/*  68: 96 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=true THEN d.valorADepreciar ELSE 0 END) as valorADepreciarFiscal,  ");
/*  69: 97 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN d.valorADepreciar ELSE 0 END) as valorADepreciar,  ");
/*  70: 98 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=true THEN d.vidaUtil ELSE 0 END) as vidaUtilFiscal,  ");
/*  71: 99 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN d.vidaUtil ELSE 0 END) as vidaUtil,  ");
/*  72:100 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=true THEN dd.valor ELSE 0 END) as valorFiscal ,  ");
/*  73:101 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN dd.valor ELSE 0 END) as valor,  ");
/*  74:102 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN dd.diferenciaTemporal ELSE 0 END) as diferenciaTemporal,  ");
/*  75:103 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN dd.diferenciaTemporalRevalorizacion ELSE 0 END) as diferenciaTemporalRevalorizacion,  ");
/*  76:104 */     sql.append(" ROUND(SUM(CASE WHEN d.indicadorDepreciacionFiscal=false THEN dd.diferenciaTemporal ELSE 0 END)*p.porcentaje/100,2) as impuestoDiferido,  ");
/*  77:105 */     sql.append(" dd.mes, dd.anio ");
/*  78:106 */     sql.append(" FROM DetalleDepreciacion dd ");
/*  79:107 */     sql.append(" INNER JOIN dd.depreciacion d ");
/*  80:108 */     sql.append(" INNER JOIN d.activoFijo af, PorcentajeImpuestoRentaAnual p ");
/*  81:109 */     if ((dimension != null) && (!dimension.isEmpty())) {
/*  82:110 */       sql.append(" LEFT JOIN af.centroCosto cc ");
/*  83:    */     }
/*  84:113 */     sql.append(" WHERE dd.historicoDepreciacion IS NOT NULL AND p.anio = dd.anio ");
/*  85:114 */     sql.append(" AND (af.idActivoFijo = :idActivoFijo OR :idActivoFijo = 0) ");
/*  86:115 */     sql.append(" AND dd.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  87:116 */     if ((dimension != null) && (!dimension.isEmpty())) {
/*  88:117 */       sql.append(" AND cc.codigo LIKE :codigoDimension ");
/*  89:    */     }
/*  90:120 */     sql.append(" GROUP BY af.codigo, af.nombre, dd.mes, dd.anio, p.porcentaje");
/*  91:121 */     sql.append(" ORDER BY af.codigo,dd.anio,dd.mes");
/*  92:    */     
/*  93:123 */     Query query = this.em.createQuery(sql.toString());
/*  94:124 */     query.setParameter("fechaDesde", fechaDesde);
/*  95:125 */     query.setParameter("fechaHasta", fechaHasta);
/*  96:126 */     query.setParameter("idActivoFijo", Integer.valueOf(activoFijo != null ? activoFijo.getIdActivoFijo() : 0));
/*  97:127 */     if ((dimension != null) && (!dimension.isEmpty())) {
/*  98:128 */       query.setParameter("codigoDimension", codigoDimension);
/*  99:    */     }
/* 100:134 */     return query.getResultList();
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.activos.ReporteDepreciacionDao
 * JD-Core Version:    0.7.0.1
 */