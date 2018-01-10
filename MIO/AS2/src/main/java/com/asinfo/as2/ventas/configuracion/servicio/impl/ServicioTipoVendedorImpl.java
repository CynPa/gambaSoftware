/*  1:   */ package com.asinfo.as2.ventas.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.TipoVendedorDao;
/*  4:   */ import com.asinfo.as2.entities.TipoVendedor;
/*  5:   */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioTipoVendedor;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTipoVendedorImpl
/* 13:   */   implements ServicioTipoVendedor
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TipoVendedorDao tipoVendedorDao;
/* 17:   */   
/* 18:   */   public void guardar(TipoVendedor tipoVendedor)
/* 19:   */   {
/* 20:40 */     this.tipoVendedorDao.guardar(tipoVendedor);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TipoVendedor tipoVendedor)
/* 24:   */   {
/* 25:49 */     this.tipoVendedorDao.eliminar(tipoVendedor);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TipoVendedor buscarPorId(int idTipoVendedor)
/* 29:   */   {
/* 30:58 */     return (TipoVendedor)this.tipoVendedorDao.buscarPorId(Integer.valueOf(idTipoVendedor));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TipoVendedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:68 */     return this.tipoVendedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TipoVendedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:77 */     return this.tipoVendedorDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:85 */     return this.tipoVendedorDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.impl.ServicioTipoVendedorImpl
 * JD-Core Version:    0.7.0.1
 */