/*   1:    */ package com.asinfo.as2.financiero.activos.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.UbicacionActivoDao;
/*   4:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*   5:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioUbicacionActivo;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioUbicacionActivoImpl
/*  13:    */   implements ServicioUbicacionActivo
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private UbicacionActivoDao ubicacionActivoDao;
/*  17:    */   
/*  18:    */   public void guardar(UbicacionActivo ubicacionActivo)
/*  19:    */   {
/*  20: 43 */     this.ubicacionActivoDao.guardar(ubicacionActivo);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(UbicacionActivo ubicacionActivo)
/*  24:    */   {
/*  25: 54 */     this.ubicacionActivoDao.eliminar(ubicacionActivo);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public UbicacionActivo buscarPorId(int idUbicacionActivo)
/*  29:    */   {
/*  30: 66 */     return (UbicacionActivo)this.ubicacionActivoDao.buscarPorId(Integer.valueOf(idUbicacionActivo));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<UbicacionActivo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 78 */     return this.ubicacionActivoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<UbicacionActivo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 90 */     return this.ubicacionActivoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45:101 */     return this.ubicacionActivoDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public UbicacionActivo cargarDetalle(int idUbicacionActivo)
/*  49:    */   {
/*  50:112 */     return null;
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioUbicacionActivoImpl
 * JD-Core Version:    0.7.0.1
 */