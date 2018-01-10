/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioProvincia;
/*   4:    */ import com.asinfo.as2.dao.ProvinciaDao;
/*   5:    */ import com.asinfo.as2.entities.Provincia;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioProvinciaImpl
/*  14:    */   implements ServicioProvincia
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private ProvinciaDao provinciaDao;
/*  18:    */   
/*  19:    */   public void guardar(Provincia provincia)
/*  20:    */   {
/*  21: 46 */     this.provinciaDao.guardar(provincia);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void eliminar(Provincia provincia)
/*  25:    */   {
/*  26: 58 */     this.provinciaDao.eliminar(provincia);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Provincia buscarPorId(Integer id)
/*  30:    */   {
/*  31: 70 */     return (Provincia)this.provinciaDao.buscarPorId(id);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Provincia cargarDetalle(int id)
/*  35:    */   {
/*  36: 82 */     return this.provinciaDao.cargarDetalle(id);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<Provincia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 94 */     return this.provinciaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<Provincia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters, int idPais)
/*  45:    */   {
/*  46:106 */     return this.provinciaDao.obtenerListaCombo(sortField, sortOrder, filters, idPais);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Provincia buscarPorNombre(String nombre)
/*  50:    */   {
/*  51:116 */     Map<String, String> filters = new HashMap();
/*  52:117 */     filters.put("nombre", nombre);
/*  53:118 */     List<Provincia> provincia = obtenerListaCombo("nombre", false, filters);
/*  54:120 */     if (provincia.size() > 0) {
/*  55:121 */       return (Provincia)provincia.get(0);
/*  56:    */     }
/*  57:123 */     return null;
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioProvinciaImpl
 * JD-Core Version:    0.7.0.1
 */