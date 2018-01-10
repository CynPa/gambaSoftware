/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.CategoriaArticuloServicioDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.CategoriaArticuloServicio;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioCategoriaArticuloServicio;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioCategoriaArticuloServicioImpl
/* 13:   */   implements ServicioCategoriaArticuloServicio
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private CategoriaArticuloServicioDao categoriaArticuloServicioDao;
/* 17:   */   
/* 18:   */   public void guardar(CategoriaArticuloServicio categoriaArticuloServicio)
/* 19:   */   {
/* 20:43 */     this.categoriaArticuloServicioDao.guardar(categoriaArticuloServicio);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(CategoriaArticuloServicio categoriaArticuloServicio)
/* 24:   */   {
/* 25:53 */     this.categoriaArticuloServicioDao.eliminar(categoriaArticuloServicio);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public CategoriaArticuloServicio buscarPorId(int idCategoriaArticuloServicio)
/* 29:   */   {
/* 30:64 */     return (CategoriaArticuloServicio)this.categoriaArticuloServicioDao.buscarPorId(Integer.valueOf(idCategoriaArticuloServicio));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<CategoriaArticuloServicio> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:75 */     return this.categoriaArticuloServicioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<CategoriaArticuloServicio> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:85 */     return this.categoriaArticuloServicioDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:95 */     return this.categoriaArticuloServicioDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioCategoriaArticuloServicioImpl
 * JD-Core Version:    0.7.0.1
 */