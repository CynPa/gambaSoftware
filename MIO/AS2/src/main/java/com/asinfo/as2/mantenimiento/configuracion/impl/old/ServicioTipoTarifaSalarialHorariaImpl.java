/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.TipoTarifaSalarialHorariaDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.TipoTarifaSalarialHoraria;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoTarifaSalarialHoraria;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTipoTarifaSalarialHorariaImpl
/* 13:   */   implements ServicioTipoTarifaSalarialHoraria
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TipoTarifaSalarialHorariaDao tipoTarifaSalarialHorariaDao;
/* 17:   */   
/* 18:   */   public void guardar(TipoTarifaSalarialHoraria tipoTarifaSalarialHoraria)
/* 19:   */   {
/* 20:42 */     this.tipoTarifaSalarialHorariaDao.guardar(tipoTarifaSalarialHoraria);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TipoTarifaSalarialHoraria tipoTarifaSalarialHoraria)
/* 24:   */   {
/* 25:52 */     this.tipoTarifaSalarialHorariaDao.eliminar(tipoTarifaSalarialHoraria);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TipoTarifaSalarialHoraria buscarPorId(int idTipoTarifaSalarialHoraria)
/* 29:   */   {
/* 30:63 */     return (TipoTarifaSalarialHoraria)this.tipoTarifaSalarialHorariaDao.buscarPorId(Integer.valueOf(idTipoTarifaSalarialHoraria));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TipoTarifaSalarialHoraria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:74 */     return this.tipoTarifaSalarialHorariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TipoTarifaSalarialHoraria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:84 */     return this.tipoTarifaSalarialHorariaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:94 */     return this.tipoTarifaSalarialHorariaDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioTipoTarifaSalarialHorariaImpl
 * JD-Core Version:    0.7.0.1
 */