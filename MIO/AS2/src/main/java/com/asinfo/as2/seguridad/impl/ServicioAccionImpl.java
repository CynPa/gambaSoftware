/*  1:   */ package com.asinfo.as2.seguridad.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.seguridad.AccionDao;
/*  4:   */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*  5:   */ import com.asinfo.as2.seguridad.ServicioAccion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioAccionImpl
/* 13:   */   implements ServicioAccion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private AccionDao accionDao;
/* 17:   */   
/* 18:   */   public void guardar(EntidadAccion entidad)
/* 19:   */   {
/* 20:42 */     this.accionDao.guardar(entidad);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(EntidadAccion entidad)
/* 24:   */   {
/* 25:52 */     this.accionDao.eliminar(entidad);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public EntidadAccion buscarPorId(Integer id)
/* 29:   */   {
/* 30:62 */     return (EntidadAccion)this.accionDao.buscarPorId(id);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<EntidadAccion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:72 */     return this.accionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioAccionImpl
 * JD-Core Version:    0.7.0.1
 */