/*  1:   */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.VehiculoDao;
/*  4:   */ import com.asinfo.as2.entities.Vehiculo;
/*  5:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVehiculo;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioVehiculoImpl
/* 13:   */   implements ServicioVehiculo
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private VehiculoDao vehiculoDao;
/* 17:   */   
/* 18:   */   public void guardar(Vehiculo vehiculo)
/* 19:   */   {
/* 20:33 */     if ((vehiculo.getCodigo() == null) || (vehiculo.getCodigo().isEmpty())) {
/* 21:34 */       vehiculo.setCodigo(vehiculo.getPlaca());
/* 22:   */     }
/* 23:36 */     this.vehiculoDao.guardar(vehiculo);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void eliminar(Vehiculo vehiculo)
/* 27:   */   {
/* 28:46 */     this.vehiculoDao.eliminar(vehiculo);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Vehiculo buscarPorId(Integer id)
/* 32:   */   {
/* 33:57 */     return (Vehiculo)this.vehiculoDao.buscarPorId(id);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public List<Vehiculo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 37:   */   {
/* 38:67 */     return this.vehiculoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public List<Vehiculo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 42:   */   {
/* 43:77 */     return this.vehiculoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int contarPorCriterio(Map<String, String> filters)
/* 47:   */   {
/* 48:87 */     return this.vehiculoDao.contarPorCriterio(filters);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public List<Vehiculo> obtenerListaVehiculoPorTransportista(int idTransportista)
/* 52:   */   {
/* 53:97 */     return this.vehiculoDao.obtenerListaVehiculoPorTransportista(idTransportista);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioVehiculoImpl
 * JD-Core Version:    0.7.0.1
 */