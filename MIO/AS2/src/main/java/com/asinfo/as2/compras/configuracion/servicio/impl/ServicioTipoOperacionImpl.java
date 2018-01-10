/*   1:    */ package com.asinfo.as2.compras.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TipoOperacionDao;
/*   4:    */ import com.asinfo.as2.entities.TipoOperacion;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.ejb.EJB;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ 
/*  10:    */ @Stateless
/*  11:    */ public class ServicioTipoOperacionImpl
/*  12:    */   implements ServicioTipoOperacion
/*  13:    */ {
/*  14:    */   @EJB
/*  15:    */   private TipoOperacionDao tipoOperacionDao;
/*  16:    */   
/*  17:    */   public void guardar(TipoOperacion tipoOperacion)
/*  18:    */   {
/*  19: 41 */     this.tipoOperacionDao.guardar(tipoOperacion);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void eliminar(TipoOperacion tipoOperacion)
/*  23:    */   {
/*  24: 52 */     this.tipoOperacionDao.eliminar(tipoOperacion);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public TipoOperacion buscarPorId(int idTipoOperacion)
/*  28:    */   {
/*  29: 63 */     return (TipoOperacion)this.tipoOperacionDao.buscarPorId(Integer.valueOf(idTipoOperacion));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<TipoOperacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  33:    */   {
/*  34: 74 */     return this.tipoOperacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<TipoOperacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  38:    */   {
/*  39: 84 */     return this.tipoOperacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int contarPorCriterio(Map<String, String> filters)
/*  43:    */   {
/*  44: 94 */     return this.tipoOperacionDao.contarPorCriterio(filters);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public TipoOperacion cargarDetalle(int idTipoOperacion)
/*  48:    */   {
/*  49:104 */     return null;
/*  50:    */   }
/*  51:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacionImpl
 * JD-Core Version:    0.7.0.1
 */