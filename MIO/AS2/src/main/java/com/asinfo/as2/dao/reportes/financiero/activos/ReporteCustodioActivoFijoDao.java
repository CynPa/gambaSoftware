/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.activos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ReporteCustodioActivoFijoDao
/*  14:    */   extends AbstractDaoAS2<CustodioActivoFijo>
/*  15:    */ {
/*  16:    */   public ReporteCustodioActivoFijoDao()
/*  17:    */   {
/*  18: 36 */     super(CustodioActivoFijo.class);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public List getReporteCustodioActivoFijo(int idActivoFijo, Date fechaDesde, Date fechaHasta, Empresa cliente, Empresa empleado, int orden)
/*  22:    */   {
/*  23: 42 */     StringBuilder sql = new StringBuilder();
/*  24: 43 */     sql.append("SELECT caf.fechaInicio, caf.fechaFin, caf.descripcion,");
/*  25: 44 */     sql.append(" af.codigo, af.nombre,e.nombreFiscal, e.identificacion,");
/*  26: 45 */     sql.append(" u.codigo, u.nombre, u.descripcion, d.codigo, d.nombre,d.descripcion, clie.nombreComercial, clie.identificacion");
/*  27: 46 */     sql.append(" FROM CustodioActivoFijo caf");
/*  28: 47 */     sql.append(" INNER JOIN caf.ubicacionActivo u");
/*  29: 48 */     sql.append(" INNER JOIN u.departamento d");
/*  30: 49 */     sql.append(" INNER JOIN caf.activoFijo af");
/*  31: 50 */     sql.append(" LEFT JOIN af.categoriaActivo ca");
/*  32: 51 */     sql.append(" LEFT JOIN af.subcategoriaActivo sca");
/*  33: 52 */     sql.append(" LEFT JOIN caf.empleado em");
/*  34: 53 */     sql.append(" LEFT JOIN em.empresa e");
/*  35:    */     
/*  36: 55 */     sql.append(" LEFT JOIN caf.empresa clie");
/*  37: 56 */     sql.append(" WHERE caf.fechaInicio BETWEEN  :fechaDesde AND :fechaHasta");
/*  38: 58 */     if (idActivoFijo != 0) {
/*  39: 59 */       sql.append(" AND af.idActivoFijo = :idActivoFijo");
/*  40:    */     }
/*  41: 61 */     if (cliente != null) {
/*  42: 62 */       sql.append(" AND clie = :cliente");
/*  43:    */     }
/*  44: 63 */     if (empleado != null) {
/*  45: 64 */       sql.append(" AND e = :empleado");
/*  46:    */     }
/*  47: 65 */     if (orden == 0) {
/*  48: 66 */       sql.append(" ORDER BY af.nombre, caf.fechaInicio");
/*  49:    */     } else {
/*  50: 68 */       sql.append(" ORDER BY e.nombreFiscal, ca.nombre, sca.nombre, af.nombre");
/*  51:    */     }
/*  52: 70 */     Query query = this.em.createQuery(sql.toString());
/*  53: 71 */     query.setParameter("fechaDesde", fechaDesde);
/*  54: 72 */     query.setParameter("fechaHasta", fechaHasta);
/*  55: 74 */     if (idActivoFijo != 0) {
/*  56: 75 */       query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/*  57:    */     }
/*  58: 77 */     if (cliente != null) {
/*  59: 78 */       query.setParameter("cliente", cliente);
/*  60:    */     }
/*  61: 79 */     if (empleado != null) {
/*  62: 80 */       query.setParameter("empleado", empleado);
/*  63:    */     }
/*  64: 81 */     return query.getResultList();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List getReporteCustodioActivo(int idCustodioActivoFijo)
/*  68:    */   {
/*  69: 87 */     String sql = " SELECT caf.fechaInicio, caf.fechaFin, caf.descripcion,  af.codigo, af.nombre,e.nombreFiscal, e.identificacion,  u.codigo, u.nombre, u.descripcion, d.codigo, d.nombre,d.descripcion,  cafa.fechaInicio, cafa.fechaFin, cafa.descripcion,  afa.codigo, afa.nombre,ea.nombreFiscal, ea.identificacion,  ua.codigo, ua.nombre, ua.descripcion, da.codigo, da.nombre,da.descripcion  FROM CustodioActivoFijo caf  INNER JOIN caf.activoFijo af  INNER JOIN caf.empleado em  INNER JOIN em.empresa e  INNER JOIN caf.ubicacionActivo u  INNER JOIN u.departamento d  INNER JOIN caf.custodioActivoFijoAnterior cafa  INNER JOIN cafa.activoFijo afa  INNER JOIN cafa.empleado ema  INNER JOIN ema.empresa ea  INNER JOIN cafa.ubicacionActivo ua  INNER JOIN ua.departamento da  WHERE caf.idCustodioActivoFijo = :idCustodioActivoFijo ";
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:106 */     sql = sql + " ORDER BY af.nombre, caf.fechaInicio";
/*  89:107 */     Query query = this.em.createQuery(sql);
/*  90:108 */     query.setParameter("idCustodioActivoFijo", Integer.valueOf(idCustodioActivoFijo));
/*  91:109 */     return query.getResultList();
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.activos.ReporteCustodioActivoFijoDao
 * JD-Core Version:    0.7.0.1
 */