/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPaisRemoto;
/*   5:    */ import com.asinfo.as2.dao.CiudadDao;
/*   6:    */ import com.asinfo.as2.dao.PaisDao;
/*   7:    */ import com.asinfo.as2.dao.ProvinciaDao;
/*   8:    */ import com.asinfo.as2.entities.Ciudad;
/*   9:    */ import com.asinfo.as2.entities.Pais;
/*  10:    */ import com.asinfo.as2.entities.Provincia;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioPaisImpl
/*  18:    */   implements ServicioPais, ServicioPaisRemoto
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private PaisDao paisDao;
/*  22:    */   @EJB
/*  23:    */   private ProvinciaDao provinciaDao;
/*  24:    */   @EJB
/*  25:    */   private CiudadDao ciudadDao;
/*  26:    */   
/*  27:    */   public void guardar(Pais entidad)
/*  28:    */   {
/*  29: 60 */     for (Provincia provincia : entidad.getListaProvincia())
/*  30:    */     {
/*  31: 61 */       for (Ciudad ciudad : provincia.getListaCiudad()) {
/*  32: 62 */         this.ciudadDao.guardar(ciudad);
/*  33:    */       }
/*  34: 64 */       this.provinciaDao.guardar(provincia);
/*  35:    */     }
/*  36: 66 */     this.paisDao.guardar(entidad);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void eliminar(Pais entidad)
/*  40:    */   {
/*  41: 76 */     this.paisDao.eliminar(entidad);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Pais buscarPorId(Integer id)
/*  45:    */   {
/*  46: 86 */     return (Pais)this.paisDao.buscarPorId(id);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Pais cargarDetalle(int id)
/*  50:    */   {
/*  51: 96 */     return this.paisDao.cargarDetalle(id);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<Pais> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56:106 */     return this.paisDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<Pais> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  60:    */   {
/*  61:116 */     return this.paisDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int contarPorCriterio(Map<String, String> filters)
/*  65:    */   {
/*  66:126 */     return this.paisDao.contarPorCriterio(filters);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Pais buscarPorNombre(String nombre)
/*  70:    */   {
/*  71:136 */     return this.paisDao.buscarPorNombre(nombre);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<Pais> obtenerPaises()
/*  75:    */   {
/*  76:141 */     List<Pais> paises = obtenerListaCombo("nombre", true, null);
/*  77:142 */     paises.size();
/*  78:143 */     for (Pais pais : paises)
/*  79:    */     {
/*  80:144 */       pais.getListaProvincia().size();
/*  81:145 */       for (Provincia provincia : pais.getListaProvincia()) {
/*  82:146 */         provincia.getListaCiudad().size();
/*  83:    */       }
/*  84:    */     }
/*  85:149 */     return paises;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioPaisImpl
 * JD-Core Version:    0.7.0.1
 */