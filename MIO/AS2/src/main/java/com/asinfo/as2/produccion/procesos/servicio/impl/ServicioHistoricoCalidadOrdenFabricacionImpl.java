/*   1:    */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.produccion.HistoricoCalidadOrdenFabricacionDao;
/*   4:    */ import com.asinfo.as2.dao.produccion.OperacionProduccionDao;
/*   5:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*   6:    */ import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
/*   7:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   8:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*   9:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioHistoricoCalidadOrdenFabricacion;
/*  10:    */ import java.math.BigDecimal;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioHistoricoCalidadOrdenFabricacionImpl
/*  18:    */   implements ServicioHistoricoCalidadOrdenFabricacion
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private HistoricoCalidadOrdenFabricacionDao historicoCalidadOrdenFabricacionDao;
/*  22:    */   @EJB
/*  23:    */   private OperacionProduccionDao operacionProduccionDao;
/*  24:    */   
/*  25:    */   public void guardar(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/*  26:    */   {
/*  27: 51 */     this.historicoCalidadOrdenFabricacionDao.guardar(historicoCalidadOrdenFabricacion);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/*  31:    */   {
/*  32: 62 */     this.historicoCalidadOrdenFabricacionDao.eliminar(historicoCalidadOrdenFabricacion);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public HistoricoCalidadOrdenFabricacion buscarPorId(int idHistoricoCalidadOrdenFabricacion)
/*  36:    */   {
/*  37: 73 */     return (HistoricoCalidadOrdenFabricacion)this.historicoCalidadOrdenFabricacionDao.buscarPorId(Integer.valueOf(idHistoricoCalidadOrdenFabricacion));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<HistoricoCalidadOrdenFabricacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 85 */     return this.historicoCalidadOrdenFabricacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<HistoricoCalidadOrdenFabricacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 95 */     return this.historicoCalidadOrdenFabricacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52:105 */     return this.historicoCalidadOrdenFabricacionDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public HistoricoCalidadOrdenFabricacion cargarDetalle(int idHistoricoCalidadOrdenFabricacion)
/*  56:    */   {
/*  57:115 */     return (HistoricoCalidadOrdenFabricacion)this.historicoCalidadOrdenFabricacionDao.cargarDetalle(idHistoricoCalidadOrdenFabricacion);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public BigDecimal obtenerSumaCantidadPorOrdenFabricacionEstado(OrdenFabricacion ordenFabricacion, EstadoControlCalidad estadoControlCalidad)
/*  61:    */   {
/*  62:120 */     return this.historicoCalidadOrdenFabricacionDao.obtenerSumaCantidadPorOrdenFabricacionEstado(ordenFabricacion, estadoControlCalidad);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<VariableCalidad> getListaVariableCalidadPromedioSubordenes(List<OrdenFabricacion> listaOrdenFabricacion)
/*  66:    */   {
/*  67:125 */     return this.historicoCalidadOrdenFabricacionDao.getListaVariableCalidadPromedioSubordenes(listaOrdenFabricacion);
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioHistoricoCalidadOrdenFabricacionImpl
 * JD-Core Version:    0.7.0.1
 */