/*   1:    */ package com.asinfo.as2.ventas.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.SectorDao;
/*   4:    */ import com.asinfo.as2.dao.ZonaDao;
/*   5:    */ import com.asinfo.as2.entities.Sector;
/*   6:    */ import com.asinfo.as2.entities.Zona;
/*   7:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioZonaImpl
/*  15:    */   implements ServicioZona
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private ZonaDao zonaDao;
/*  19:    */   @EJB
/*  20:    */   private SectorDao sectorDao;
/*  21:    */   
/*  22:    */   public void guardar(Zona zona)
/*  23:    */   {
/*  24: 48 */     this.zonaDao.guardar(zona);
/*  25: 49 */     for (Sector sector : zona.getListaSector()) {
/*  26: 50 */       this.sectorDao.guardar(sector);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(Zona zona)
/*  31:    */   {
/*  32: 62 */     this.zonaDao.eliminar(zona);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Zona buscarPorId(int idZona)
/*  36:    */   {
/*  37: 73 */     return (Zona)this.zonaDao.buscarPorId(Integer.valueOf(idZona));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<Zona> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 83 */     return this.zonaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<Zona> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 93 */     return this.zonaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52:103 */     return this.zonaDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Zona cargarDetalle(int idZona)
/*  56:    */   {
/*  57:113 */     return this.zonaDao.cargarDetalle(idZona);
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.impl.ServicioZonaImpl
 * JD-Core Version:    0.7.0.1
 */