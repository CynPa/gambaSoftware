/*  1:   */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioParroquia;
/*  4:   */ import com.asinfo.as2.dao.ParroquiaDao;
/*  5:   */ import com.asinfo.as2.entities.Organizacion;
/*  6:   */ import com.asinfo.as2.entities.Parroquia;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ServicioParroquiaImpl
/* 16:   */   implements ServicioParroquia
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private ParroquiaDao parroquiaDao;
/* 20:   */   
/* 21:   */   public List<Parroquia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 22:   */   {
/* 23:31 */     return this.parroquiaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<Parroquia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters, int idCiudad)
/* 27:   */   {
/* 28:36 */     return this.parroquiaDao.obtenerListaCombo(sortField, sortOrder, filters, idCiudad);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Parroquia buscarPorId(int idParroquia)
/* 32:   */   {
/* 33:41 */     return (Parroquia)this.parroquiaDao.buscarPorId(Integer.valueOf(idParroquia));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Parroquia buscarPorNombre(String nombre, Organizacion organizacion)
/* 37:   */   {
/* 38:46 */     return this.parroquiaDao.buscarPorNombre(nombre, organizacion);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public List<Parroquia> autocompletarParroquia(String consulta)
/* 42:   */   {
/* 43:51 */     List<Parroquia> listaParroquia = new ArrayList();
/* 44:52 */     String sortField = "nombre";
/* 45:53 */     Map<String, String> filters = new HashMap();
/* 46:54 */     filters.put("nombre", consulta.trim());
/* 47:   */     
/* 48:56 */     listaParroquia = obtenerListaCombo(sortField, true, filters);
/* 49:   */     
/* 50:58 */     return listaParroquia;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void guardar(Parroquia parroquia)
/* 54:   */   {
/* 55:63 */     this.parroquiaDao.guardar(parroquia);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int contarPorCriterio(Map<String, String> filters)
/* 59:   */   {
/* 60:70 */     return this.parroquiaDao.contarPorCriterio(filters);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public List<Parroquia> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 64:   */   {
/* 65:75 */     return this.parroquiaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void eliminar(Parroquia parroquia)
/* 69:   */   {
/* 70:80 */     this.parroquiaDao.eliminar(parroquia);
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioParroquiaImpl
 * JD-Core Version:    0.7.0.1
 */