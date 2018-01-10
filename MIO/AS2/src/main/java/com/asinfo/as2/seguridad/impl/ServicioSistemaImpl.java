/*  1:   */ package com.asinfo.as2.seguridad.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.seguridad.SistemaDao;
/*  4:   */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  5:   */ import com.asinfo.as2.seguridad.ServicioSistema;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioSistemaImpl
/* 13:   */   implements ServicioSistema
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private SistemaDao sistemaDao;
/* 17:   */   
/* 18:   */   public void guardar(EntidadSistema entidad)
/* 19:   */   {
/* 20:41 */     this.sistemaDao.guardar(entidad);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(EntidadSistema entidad)
/* 24:   */   {
/* 25:51 */     this.sistemaDao.eliminar(entidad);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public EntidadSistema buscarPorId(Integer id)
/* 29:   */   {
/* 30:61 */     return (EntidadSistema)this.sistemaDao.buscarPorId(id);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<EntidadSistema> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:71 */     return this.sistemaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public EntidadSistema buscarPorNombre(String nombre)
/* 39:   */   {
/* 40:81 */     return this.sistemaDao.buscarPorNombre(nombre);
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioSistemaImpl
 * JD-Core Version:    0.7.0.1
 */