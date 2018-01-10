/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Empresa;
/*  4:   */ import java.util.Date;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class PagoDecimosDao
/* 12:   */   extends AbstractDaoAS2<Empresa>
/* 13:   */ {
/* 14:   */   public PagoDecimosDao()
/* 15:   */   {
/* 16:31 */     super(Empresa.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public List<Object[]> datosDecimo(Date fechaDesde, Date fechaHasta)
/* 20:   */   {
/* 21:37 */     String sql = " SELECT em.identificacion, em.nombreComercial, em.nombreFiscal, e.genero,  ce.nombre, sum(prer.valor), 360-sum(pre.diasFalta), e.idEmpleado  FROM PagoRolEmpleadoRubro prer  INNER JOIN prer.pagoRolEmpleado pre  INNER JOIN pre.pagoRol pr  INNER JOIN pre.empleado e  INNER JOIN e.empresa em  INNER JOIN e.cargoEmpleado ce  INNER JOIN prer.rubro r  WHERE em.indicadorEmpleado = true  AND r.operacion = 1  AND r.indicadorCalculoIESS = true  AND pr.fecha BETWEEN :fechaDesde AND :fechaHasta  GROUP BY em.identificacion, em.nombreComercial, em.nombreFiscal, e.genero,  ce.nombre, e.idEmpleado ";
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:52 */     Query query = this.em.createQuery(sql);
/* 37:53 */     query.setParameter("fechaDesde", fechaDesde);
/* 38:54 */     query.setParameter("fechaHasta", fechaHasta);
/* 39:   */     
/* 40:56 */     return query.getResultList();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<Object[]> datosUtilidad(int anio, String codigoDT, String codigoDC, String codigoFR)
/* 44:   */   {
/* 45:66 */     String sql = " SELECT em.identificacion, em.nombreComercial, em.nombreFiscal, e.genero,  ce.nombre, sum(CASE WHEN r.indicadorCalculoIESS = true  THEN prer.valor ELSE 0 END), 360-sum(pre.diasFalta), e.idEmpleado, SUM(CASE WHEN r.codigo = :codigoDT THEN prer.valor ELSE 0 END),  SUM(CASE WHEN r.codigo = :codigoDC THEN prer.valor ELSE 0 END),  SUM(CASE WHEN r.codigo = :codigoFR THEN prer.valor ELSE 0 END)  FROM PagoRolEmpleadoRubro prer  INNER JOIN prer.pagoRolEmpleado pre  INNER JOIN pre.pagoRol pr  INNER JOIN pre.empleado e  INNER JOIN e.empresa em  INNER JOIN e.cargoEmpleado ce  INNER JOIN prer.rubro r  WHERE em.indicadorEmpleado = true  AND r.operacion = 1  AND pr.anio = :anio  GROUP BY em.identificacion, em.nombreComercial, em.nombreFiscal, e.genero,  ce.nombre, e.idEmpleado ";
/* 46:   */     
/* 47:   */ 
/* 48:   */ 
/* 49:   */ 
/* 50:   */ 
/* 51:   */ 
/* 52:   */ 
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:   */ 
/* 62:83 */     Query query = this.em.createQuery(sql);
/* 63:84 */     query.setParameter("anio", Integer.valueOf(anio));
/* 64:85 */     query.setParameter("codigoDT", codigoDT);
/* 65:86 */     query.setParameter("codigoDC", codigoDC);
/* 66:87 */     query.setParameter("codigoFR", codigoFR);
/* 67:   */     
/* 68:89 */     return query.getResultList();
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoDecimosDao
 * JD-Core Version:    0.7.0.1
 */