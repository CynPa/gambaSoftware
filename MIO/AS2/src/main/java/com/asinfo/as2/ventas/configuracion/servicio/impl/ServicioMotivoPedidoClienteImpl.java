/*  1:   */ package com.asinfo.as2.ventas.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MotivoPedidoClienteDao;
/*  4:   */ import com.asinfo.as2.entities.MotivoPedidoCliente;
/*  5:   */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioMotivoPedidoCliente;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioMotivoPedidoClienteImpl
/* 13:   */   implements ServicioMotivoPedidoCliente
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private MotivoPedidoClienteDao motivoPedidoClienteDao;
/* 17:   */   
/* 18:   */   public void guardar(MotivoPedidoCliente motivoPedidoCliente)
/* 19:   */   {
/* 20:42 */     this.motivoPedidoClienteDao.guardar(motivoPedidoCliente);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(MotivoPedidoCliente motivoPedidoCliente)
/* 24:   */   {
/* 25:53 */     this.motivoPedidoClienteDao.eliminar(motivoPedidoCliente);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public MotivoPedidoCliente buscarPorId(int idMotivoPedidoCliente)
/* 29:   */   {
/* 30:64 */     return (MotivoPedidoCliente)this.motivoPedidoClienteDao.buscarPorId(Integer.valueOf(idMotivoPedidoCliente));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<MotivoPedidoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:76 */     return this.motivoPedidoClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<MotivoPedidoCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:86 */     return this.motivoPedidoClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:96 */     return this.motivoPedidoClienteDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.impl.ServicioMotivoPedidoClienteImpl
 * JD-Core Version:    0.7.0.1
 */