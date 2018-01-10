/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.UnidadMedicionDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.UnidadMedicion;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioUnidadMedicion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioUnidadMedicionImpl
/* 13:   */   implements ServicioUnidadMedicion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private UnidadMedicionDao unidadMedicionDao;
/* 17:   */   
/* 18:   */   public void guardar(UnidadMedicion unidadMedicion)
/* 19:   */   {
/* 20:42 */     this.unidadMedicionDao.guardar(unidadMedicion);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(UnidadMedicion unidadMedicion)
/* 24:   */   {
/* 25:52 */     this.unidadMedicionDao.eliminar(unidadMedicion);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public UnidadMedicion buscarPorId(int idUnidadMedicion)
/* 29:   */   {
/* 30:63 */     return (UnidadMedicion)this.unidadMedicionDao.buscarPorId(Integer.valueOf(idUnidadMedicion));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<UnidadMedicion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:73 */     return this.unidadMedicionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<UnidadMedicion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:83 */     return this.unidadMedicionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:93 */     return this.unidadMedicionDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioUnidadMedicionImpl
 * JD-Core Version:    0.7.0.1
 */