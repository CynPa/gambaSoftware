/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TipoAsientoDao;
/*   4:    */ import com.asinfo.as2.entities.Secuencia;
/*   5:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*   7:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioTipoAsientoImpl
/*  15:    */   implements ServicioTipoAsiento
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private TipoAsientoDao tipoAsientoDao;
/*  19:    */   
/*  20:    */   public void guardar(TipoAsiento tipoAsiento)
/*  21:    */   {
/*  22: 40 */     this.tipoAsientoDao.guardar(tipoAsiento);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(TipoAsiento tipoAsiento)
/*  26:    */   {
/*  27: 51 */     this.tipoAsientoDao.eliminar(tipoAsiento);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TipoAsiento buscarPorId(Integer id)
/*  31:    */   {
/*  32: 62 */     TipoAsiento tipoAsiento = (TipoAsiento)this.tipoAsientoDao.buscarPorId(id);
/*  33: 63 */     if ((tipoAsiento != null) && 
/*  34: 64 */       (tipoAsiento.getSecuencia() != null)) {
/*  35: 65 */       tipoAsiento.getSecuencia().getId();
/*  36:    */     }
/*  37: 68 */     return tipoAsiento;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<TipoAsiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 80 */     return this.tipoAsientoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<TipoAsiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 91 */     return this.tipoAsientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52:101 */     return this.tipoAsientoDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public TipoAsiento buscarPorNombre(String nombre, int idOrganizacion)
/*  56:    */     throws ExcepcionAS2Financiero
/*  57:    */   {
/*  58:111 */     return this.tipoAsientoDao.buscarPorNombre(nombre, idOrganizacion);
/*  59:    */   }
/*  60:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioTipoAsientoImpl
 * JD-Core Version:    0.7.0.1
 */