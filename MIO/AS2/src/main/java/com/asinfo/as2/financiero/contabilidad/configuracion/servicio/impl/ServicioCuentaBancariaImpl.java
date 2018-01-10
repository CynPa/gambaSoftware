/*  1:   */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.CuentaBancariaDao;
/*  4:   */ import com.asinfo.as2.entities.CuentaBancaria;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaBancaria;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioCuentaBancariaImpl
/* 14:   */   implements ServicioCuentaBancaria
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private CuentaBancariaDao cuentaBancariaDao;
/* 18:   */   
/* 19:   */   public void guardar(CuentaBancaria cuentaBancaria)
/* 20:   */   {
/* 21:41 */     this.cuentaBancariaDao.guardar(cuentaBancaria);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void eliminar(CuentaBancaria cuentaBancaria)
/* 25:   */   {
/* 26:49 */     this.cuentaBancariaDao.eliminar(cuentaBancaria);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public CuentaBancaria buscarPorId(int idCuentaBancaria)
/* 30:   */   {
/* 31:57 */     return (CuentaBancaria)this.cuentaBancariaDao.buscarPorId(Integer.valueOf(idCuentaBancaria));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public List<CuentaBancaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 35:   */   {
/* 36:65 */     return this.cuentaBancariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public List<CuentaBancaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 40:   */   {
/* 41:73 */     return this.cuentaBancariaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int contarPorCriterio(Map<String, String> filters)
/* 45:   */   {
/* 46:81 */     return this.cuentaBancariaDao.contarPorCriterio(filters);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public CuentaBancaria cargarDetalle(int idCuentaBancaria)
/* 50:   */   {
/* 51:90 */     return null;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public List<CuentaBancaria> listaCuentaBancariaPorCliente(Empresa cliente)
/* 55:   */   {
/* 56:98 */     return this.cuentaBancariaDao.listaCuentaBancariaPorCliente(cliente);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioCuentaBancariaImpl
 * JD-Core Version:    0.7.0.1
 */