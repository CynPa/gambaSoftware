/*  1:   */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.CuentaPorCobrarDao;
/*  4:   */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.ventas.procesos.servicio.ServicioCuentaPorCobrar;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ServicioCuentaPorCobrarImpl
/* 12:   */   implements ServicioCuentaPorCobrar
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 6837854724192646088L;
/* 15:   */   @EJB
/* 16:   */   private CuentaPorCobrarDao cuentaPorCobrarDao;
/* 17:   */   @EJB
/* 18:   */   private CuentaPorPagarDao cuentaPorPagarDao;
/* 19:   */   
/* 20:   */   public void actualizarCuentasPorCobrar(Empresa empresa)
/* 21:   */   {
/* 22:33 */     this.cuentaPorCobrarDao.actualizarCuentasPorCobrar(empresa);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void actualizarCuentasPorPagar(Empresa empresa)
/* 26:   */   {
/* 27:37 */     this.cuentaPorPagarDao.actualizarCuentasPorPagar(empresa);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioCuentaPorCobrarImpl
 * JD-Core Version:    0.7.0.1
 */