/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria;
/*   4:    */ import com.asinfo.as2.dao.TipoCuentaBancariaDao;
/*   5:    */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioTipoCuentaBancariaImpl
/*  14:    */   implements ServicioTipoCuentaBancaria
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private TipoCuentaBancariaDao tipoCuentaBancariaDao;
/*  18:    */   
/*  19:    */   public void guardarTipoCuentaBancaria(TipoCuentaBancaria tipoCuentaBancaria)
/*  20:    */   {
/*  21: 41 */     this.tipoCuentaBancariaDao.guardar(tipoCuentaBancaria);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void eliminarTipoCuentaBancaria(TipoCuentaBancaria tipoCuentaBancaria)
/*  25:    */   {
/*  26: 52 */     this.tipoCuentaBancariaDao.eliminar(tipoCuentaBancaria);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public TipoCuentaBancaria buscarPorId(int idTipoCuentaBancaria)
/*  30:    */   {
/*  31: 63 */     return (TipoCuentaBancaria)this.tipoCuentaBancariaDao.buscarPorId(Integer.valueOf(idTipoCuentaBancaria));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<TipoCuentaBancaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 76 */     return this.tipoCuentaBancariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<TipoCuentaBancaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 86 */     return this.tipoCuentaBancariaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int contarPorCriterio(Map<String, String> filters)
/*  45:    */   {
/*  46: 96 */     return this.tipoCuentaBancariaDao.contarPorCriterio(filters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public TipoCuentaBancaria buscarPorCodigo(String codigoTipoCuenta)
/*  50:    */     throws ExcepcionAS2
/*  51:    */   {
/*  52:106 */     return this.tipoCuentaBancariaDao.buscarPorCodigo(codigoTipoCuenta);
/*  53:    */   }
/*  54:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioTipoCuentaBancariaImpl
 * JD-Core Version:    0.7.0.1
 */