/*  1:   */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.CuentaBancariaDao;
/*  4:   */ import com.asinfo.as2.dao.CuentaBancariaEmpresaDao;
/*  5:   */ import com.asinfo.as2.entities.CuentaBancariaEmpresa;
/*  6:   */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaBancariaEmpresa;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ServicioCuentaBancariaEmpresaImpl
/* 12:   */   implements ServicioCuentaBancariaEmpresa
/* 13:   */ {
/* 14:   */   @EJB
/* 15:   */   private CuentaBancariaEmpresaDao cuentaBancariaEmpresaDao;
/* 16:   */   @EJB
/* 17:   */   private CuentaBancariaDao cuentaBancariaDao;
/* 18:   */   
/* 19:   */   public void guardarCuentaBancariaEmpresa(CuentaBancariaEmpresa cuentaBancariaEmpresa)
/* 20:   */   {
/* 21:47 */     this.cuentaBancariaDao.guardar(cuentaBancariaEmpresa.getCuentaBancaria());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void eliminarCuentaBancariaEmpresa(CuentaBancariaEmpresa cuentaBancariaEmpresa)
/* 25:   */   {
/* 26:64 */     this.cuentaBancariaEmpresaDao.eliminar(cuentaBancariaEmpresa);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public CuentaBancariaEmpresa buscarPorId(int id)
/* 30:   */   {
/* 31:76 */     return (CuentaBancariaEmpresa)this.cuentaBancariaEmpresaDao.buscarPorId(Integer.valueOf(id));
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioCuentaBancariaEmpresaImpl
 * JD-Core Version:    0.7.0.1
 */