/*  1:   */ package com.asinfo.as2.inventario.procesos.servicio;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GenericoDao;
/*  4:   */ import com.asinfo.as2.dao.PreDevolucionClienteDao;
/*  5:   */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*  6:   */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*  7:   */ import java.util.Date;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ServicioPreDevolucionClienteImpl
/* 15:   */   implements ServicioPreDevolucionCliente
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private PreDevolucionClienteDao preDevolucionClienteDao;
/* 19:   */   @EJB
/* 20:   */   private GenericoDao<DetallePreDevolucionCliente> detallePreDevolucionClienteDao;
/* 21:   */   
/* 22:   */   public void guardar(PreDevolucionCliente preDevolucionCliente)
/* 23:   */   {
/* 24:39 */     for (DetallePreDevolucionCliente dpdc : preDevolucionCliente.getListaDetallePreDevolucionCliente()) {
/* 25:40 */       if (!dpdc.isEliminado()) {
/* 26:41 */         this.detallePreDevolucionClienteDao.guardar(dpdc);
/* 27:   */       }
/* 28:   */     }
/* 29:43 */     this.preDevolucionClienteDao.guardar(preDevolucionCliente);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void eliminar(PreDevolucionCliente preDevolucionCliente)
/* 33:   */   {
/* 34:48 */     this.preDevolucionClienteDao.eliminar(preDevolucionCliente);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public PreDevolucionCliente buscarPorId(int idPreDevolucionCliente)
/* 38:   */   {
/* 39:53 */     return (PreDevolucionCliente)this.preDevolucionClienteDao.buscarPorId(Integer.valueOf(idPreDevolucionCliente));
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List<PreDevolucionCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 43:   */   {
/* 44:59 */     return this.preDevolucionClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public List<PreDevolucionCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 48:   */   {
/* 49:64 */     return this.preDevolucionClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int contarPorCriterio(Map<String, String> filters)
/* 53:   */   {
/* 54:69 */     return this.preDevolucionClienteDao.contarPorCriterio(filters);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public PreDevolucionCliente cargarDetalle(int idPreDevolucionCliente)
/* 58:   */   {
/* 59:74 */     return this.preDevolucionClienteDao.cargarDetalle(idPreDevolucionCliente);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public List<PreDevolucionCliente> buscarPreDevolucionesPorTransportista(int idOrganizacion, boolean productoBueno, int idUsuario, Date fecha)
/* 63:   */   {
/* 64:79 */     return this.preDevolucionClienteDao.buscarPreDevolucionesPorTransportista(idOrganizacion, productoBueno, idUsuario, fecha);
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioPreDevolucionClienteImpl
 * JD-Core Version:    0.7.0.1
 */