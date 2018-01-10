/*   1:    */ package com.asinfo.as2.produccion.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.produccion.TareaProduccionDao;
/*   4:    */ import com.asinfo.as2.entities.produccion.TareaProduccion;
/*   5:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioTareaProduccion;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioTareaProduccionImpl
/*  15:    */   implements ServicioTareaProduccion
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private TareaProduccionDao tareaProduccionDao;
/*  19:    */   
/*  20:    */   public void guardar(TareaProduccion tareaProduccion)
/*  21:    */   {
/*  22: 44 */     this.tareaProduccionDao.guardar(tareaProduccion);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(TareaProduccion tareaProduccion)
/*  26:    */   {
/*  27: 54 */     this.tareaProduccionDao.eliminar(tareaProduccion);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TareaProduccion buscarPorId(int idTareaProduccion)
/*  31:    */   {
/*  32: 65 */     return (TareaProduccion)this.tareaProduccionDao.buscarPorId(Integer.valueOf(idTareaProduccion));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<TareaProduccion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 75 */     return this.tareaProduccionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<TareaProduccion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 85 */     return this.tareaProduccionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47: 95 */     return this.tareaProduccionDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public TareaProduccion cargarDetalle(int idTareaProduccion)
/*  51:    */   {
/*  52:105 */     return this.tareaProduccionDao.cargarDetalle(idTareaProduccion);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<TareaProduccion> autocompletarTareaProduccion(String consulta)
/*  56:    */   {
/*  57:114 */     List<TareaProduccion> lista = new ArrayList();
/*  58:    */     
/*  59:116 */     String sortField = "codigo";
/*  60:117 */     HashMap<String, String> filters = new HashMap();
/*  61:118 */     filters.put("codigo", consulta.trim());
/*  62:119 */     filters.put("nombre", consulta.trim());
/*  63:120 */     lista = this.tareaProduccionDao.autocompletarTareaProduccion(sortField, true, filters);
/*  64:    */     
/*  65:122 */     return lista;
/*  66:    */   }
/*  67:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.impl.ServicioTareaProduccionImpl
 * JD-Core Version:    0.7.0.1
 */