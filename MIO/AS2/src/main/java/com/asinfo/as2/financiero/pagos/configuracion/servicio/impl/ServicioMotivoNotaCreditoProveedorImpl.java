/*  1:   */ package com.asinfo.as2.financiero.pagos.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MotivoNotaCreditoProveedorDao;
/*  4:   */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*  5:   */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioMotivoNotaCreditoProveedor;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioMotivoNotaCreditoProveedorImpl
/* 13:   */   implements ServicioMotivoNotaCreditoProveedor
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private transient MotivoNotaCreditoProveedorDao motivoNotaCreditoProveedorDao;
/* 17:   */   
/* 18:   */   public void guardar(MotivoNotaCreditoProveedor motivoNotaCreditoProveedor)
/* 19:   */   {
/* 20:42 */     this.motivoNotaCreditoProveedorDao.guardar(motivoNotaCreditoProveedor);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(MotivoNotaCreditoProveedor motivoNotaCreditoProveedor)
/* 24:   */   {
/* 25:52 */     this.motivoNotaCreditoProveedorDao.eliminar(motivoNotaCreditoProveedor);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public MotivoNotaCreditoProveedor buscarPorId(int idMotivoNotaCreditoProveedor)
/* 29:   */   {
/* 30:62 */     return (MotivoNotaCreditoProveedor)this.motivoNotaCreditoProveedorDao.buscarPorId(Integer.valueOf(idMotivoNotaCreditoProveedor));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<MotivoNotaCreditoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:74 */     return this.motivoNotaCreditoProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<MotivoNotaCreditoProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:85 */     return this.motivoNotaCreditoProveedorDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:95 */     return this.motivoNotaCreditoProveedorDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.configuracion.servicio.impl.ServicioMotivoNotaCreditoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */