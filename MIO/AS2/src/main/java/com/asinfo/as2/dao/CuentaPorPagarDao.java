/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.Query;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class CuentaPorPagarDao
/*  13:    */   extends AbstractDaoAS2<CuentaPorPagar>
/*  14:    */ {
/*  15:    */   public CuentaPorPagarDao()
/*  16:    */   {
/*  17: 32 */     super(CuentaPorPagar.class);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public BigDecimal getSaldo(CuentaPorPagar cuentaPorPagar)
/*  21:    */   {
/*  22: 41 */     Query query = this.em.createQuery("SELECT c.saldo FROM CuentaPorPagar c WHERE c.idCuentaPorPagar=:idCuentaPorPagar");
/*  23: 42 */     query.setParameter("idCuentaPorPagar", Integer.valueOf(cuentaPorPagar.getIdCuentaPorPagar()));
/*  24: 43 */     return (BigDecimal)query.getSingleResult();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void actualizarCuentaPorPagar(CuentaPorPagar cuentaPorPagar, BigDecimal valor)
/*  28:    */   {
/*  29: 53 */     valor = FuncionesUtiles.redondearBigDecimal(valor, 2);
/*  30:    */     
/*  31: 55 */     Query query = this.em.createQuery("UPDATE CuentaPorPagar c SET saldo=saldo-:valor,indicadorBloqueada=false WHERE c.idCuentaPorPagar=:idCuentaPorPagar");
/*  32: 56 */     query.setParameter("idCuentaPorPagar", Integer.valueOf(cuentaPorPagar.getIdCuentaPorPagar()));
/*  33: 57 */     query.setParameter("valor", valor);
/*  34: 58 */     query.executeUpdate();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void actualizarCuentasPorPagar(Empresa empresa)
/*  38:    */   {
/*  39: 62 */     StringBuilder sql = new StringBuilder();
/*  40: 63 */     sql.append("UPDATE CuentaPorPagar cxp");
/*  41: 64 */     sql.append(" SET saldo=");
/*  42: 65 */     sql.append(" (SELECT COALESCE(SUM(COALESCE(v.debito,0)-COALESCE(v.credito,0)),0)");
/*  43: 66 */     sql.append(" FROM VEstadoCuentaProveedor v");
/*  44: 67 */     sql.append(" WHERE v.idCuentaPorPagar=cxp.idCuentaPorPagar)");
/*  45: 68 */     if (empresa != null) {
/*  46: 69 */       sql.append(" AND v.idEmpresa=:idEmpresa)");
/*  47:    */     }
/*  48: 71 */     Query query = this.em.createQuery(sql.toString());
/*  49: 72 */     if (empresa != null) {
/*  50: 73 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/*  51:    */     }
/*  52: 75 */     query.executeUpdate();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void actualizarIndicadorOrdenPagoProveedor(CuentaPorPagar cuentaPorPagar)
/*  56:    */   {
/*  57: 79 */     StringBuilder sql = new StringBuilder();
/*  58: 80 */     sql.append("UPDATE CuentaPorPagar c");
/*  59: 81 */     sql.append(" SET c.indicadorEnOrdenPagoProveedor=false");
/*  60: 82 */     sql.append(" WHERE c.idCuentaPorPagar=:idCuentaPorPagar");
/*  61: 83 */     Query query = this.em.createQuery(sql.toString());
/*  62: 84 */     query.setParameter("idCuentaPorPagar", Integer.valueOf(cuentaPorPagar.getIdCuentaPorPagar()));
/*  63: 85 */     query.executeUpdate();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void desbloquearCuentaPorPagar(CuentaPorPagar cuentaPorPagar)
/*  67:    */   {
/*  68: 94 */     Query query = this.em.createQuery("UPDATE CuentaPorPagar c SET indicadorBloqueada=false WHERE c.idCuentaPorPagar=:idCuentaPorPagar");
/*  69: 95 */     query.setParameter("idCuentaPorPagar", Integer.valueOf(cuentaPorPagar.getIdCuentaPorPagar()));
/*  70: 96 */     query.executeUpdate();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Boolean isIndicadorEnOrdenPagoProveedor(CuentaPorPagar cuentaPorPagar)
/*  74:    */   {
/*  75:105 */     Query query = this.em.createQuery("SELECT c.indicadorEnOrdenPagoProveedor FROM CuentaPorPagar c WHERE c.idCuentaPorPagar=:idCuentaPorPagar");
/*  76:106 */     query.setParameter("idCuentaPorPagar", Integer.valueOf(cuentaPorPagar.getIdCuentaPorPagar()));
/*  77:107 */     return (Boolean)query.getSingleResult();
/*  78:    */   }
/*  79:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CuentaPorPagarDao
 * JD-Core Version:    0.7.0.1
 */