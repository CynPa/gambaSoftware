/*  1:   */ package com.asinfo.as2.seguridad.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.ModuloDao;
/*  4:   */ import com.asinfo.as2.entities.Modulo;
/*  5:   */ import com.asinfo.as2.seguridad.ServicioModulo;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioModuloImpl
/* 13:   */   implements ServicioModulo
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private ModuloDao moduloDao;
/* 17:   */   
/* 18:   */   public void guardar(Modulo entidad)
/* 19:   */   {
/* 20:41 */     this.moduloDao.guardar(entidad);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(Modulo entidad)
/* 24:   */   {
/* 25:51 */     this.moduloDao.eliminar(entidad);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Modulo buscarPorId(Integer id)
/* 29:   */   {
/* 30:61 */     return (Modulo)this.moduloDao.buscarPorId(id);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<Modulo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:71 */     return this.moduloDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioModuloImpl
 * JD-Core Version:    0.7.0.1
 */