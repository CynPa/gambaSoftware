/*  1:   */ package com.asinfo.as2.financiero.cobros.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MotivoNotaCreditoClienteDao;
/*  4:   */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  5:   */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioMotivoNotaCreditoClienteImpl
/* 13:   */   implements ServicioMotivoNotaCreditoCliente
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private transient MotivoNotaCreditoClienteDao motivoNotaCreditoClienteDao;
/* 17:   */   
/* 18:   */   public void guardar(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 19:   */   {
/* 20:43 */     this.motivoNotaCreditoClienteDao.guardar(motivoNotaCreditoCliente);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 24:   */   {
/* 25:54 */     this.motivoNotaCreditoClienteDao.eliminar(motivoNotaCreditoCliente);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public MotivoNotaCreditoCliente buscarPorId(int idMotivoNotaCreditoCliente)
/* 29:   */   {
/* 30:64 */     return (MotivoNotaCreditoCliente)this.motivoNotaCreditoClienteDao.buscarPorId(Integer.valueOf(idMotivoNotaCreditoCliente));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<MotivoNotaCreditoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:76 */     return this.motivoNotaCreditoClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<MotivoNotaCreditoCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:87 */     return this.motivoNotaCreditoClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:97 */     return this.motivoNotaCreditoClienteDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.configuracion.servicio.impl.ServicioMotivoNotaCreditoClienteImpl
 * JD-Core Version:    0.7.0.1
 */