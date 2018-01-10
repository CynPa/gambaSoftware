/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.GrupoArticuloServicioDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.GrupoArticuloServicio;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioGrupoArticuloServicio;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioGrupoArticuloServicioImpl
/* 13:   */   implements ServicioGrupoArticuloServicio
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private GrupoArticuloServicioDao grupoArticuloServicioDao;
/* 17:   */   
/* 18:   */   public void guardar(GrupoArticuloServicio grupoArticuloServicio)
/* 19:   */   {
/* 20:43 */     this.grupoArticuloServicioDao.guardar(grupoArticuloServicio);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(GrupoArticuloServicio grupoArticuloServicio)
/* 24:   */   {
/* 25:53 */     this.grupoArticuloServicioDao.eliminar(grupoArticuloServicio);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public GrupoArticuloServicio buscarPorId(int idGrupoArticuloServicio)
/* 29:   */   {
/* 30:64 */     return (GrupoArticuloServicio)this.grupoArticuloServicioDao.buscarPorId(Integer.valueOf(idGrupoArticuloServicio));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<GrupoArticuloServicio> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:74 */     return this.grupoArticuloServicioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<GrupoArticuloServicio> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:84 */     return this.grupoArticuloServicioDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:94 */     return this.grupoArticuloServicioDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioGrupoArticuloServicioImpl
 * JD-Core Version:    0.7.0.1
 */