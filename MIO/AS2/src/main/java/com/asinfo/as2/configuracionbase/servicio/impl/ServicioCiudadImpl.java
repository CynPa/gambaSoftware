/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudadRemoto;
/*   5:    */ import com.asinfo.as2.dao.CiudadDao;
/*   6:    */ import com.asinfo.as2.entities.Ciudad;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioCiudadImpl
/*  16:    */   implements ServicioCiudad, ServicioCiudadRemoto
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private CiudadDao ciudadDao;
/*  20:    */   
/*  21:    */   public List<Ciudad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  22:    */   {
/*  23: 45 */     return this.ciudadDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<Ciudad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters, int idProvincia)
/*  27:    */   {
/*  28: 55 */     return this.ciudadDao.obtenerListaCombo(sortField, sortOrder, filters, idProvincia);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Ciudad buscarPorId(int idCiudad)
/*  32:    */   {
/*  33: 65 */     return (Ciudad)this.ciudadDao.buscarPorId(Integer.valueOf(idCiudad));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Ciudad buscarPorNombre(String nombre)
/*  37:    */   {
/*  38: 75 */     return this.ciudadDao.buscarPorNombre(nombre);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<Ciudad> autocompletarCiudad(String consulta)
/*  42:    */   {
/*  43: 85 */     List<Ciudad> listaCiudad = new ArrayList();
/*  44: 86 */     String sortField = "nombre";
/*  45: 87 */     Map<String, String> filters = new HashMap();
/*  46: 88 */     filters.put("nombre", consulta.trim());
/*  47:    */     
/*  48: 90 */     listaCiudad = obtenerListaCombo(sortField, true, filters);
/*  49:    */     
/*  50: 92 */     return listaCiudad;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void guardar(Ciudad ciudad)
/*  54:    */   {
/*  55: 97 */     this.ciudadDao.guardar(ciudad);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Ciudad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60:102 */     return this.ciudadDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int contarPorCriterio(Map<String, String> filters)
/*  64:    */   {
/*  65:107 */     return this.ciudadDao.contarPorCriterio(filters);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Ciudad getCiudadDeSucursal(int idSucursal)
/*  69:    */   {
/*  70:111 */     return this.ciudadDao.getCiudadDeSucursal(idSucursal);
/*  71:    */   }
/*  72:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioCiudadImpl
 * JD-Core Version:    0.7.0.1
 */