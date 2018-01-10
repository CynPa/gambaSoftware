/*  1:   */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MotivoLlamadoAtencionDao;
/*  4:   */ import com.asinfo.as2.entities.MotivoLlamadoAtencion;
/*  5:   */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioMotivoLlamadoAtencion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioMotivoLlamadoAtencionImpl
/* 13:   */   implements ServicioMotivoLlamadoAtencion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private MotivoLlamadoAtencionDao motivoLlamadoAtencionDao;
/* 17:   */   
/* 18:   */   public List<MotivoLlamadoAtencion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 19:   */   {
/* 20:23 */     return this.motivoLlamadoAtencionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int contarPorCriterio(Map<String, String> filters)
/* 24:   */   {
/* 25:28 */     return this.motivoLlamadoAtencionDao.contarPorCriterio(filters);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void guardar(MotivoLlamadoAtencion motivoLlamadoAtencion)
/* 29:   */   {
/* 30:33 */     this.motivoLlamadoAtencionDao.guardar(motivoLlamadoAtencion);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void eliminar(MotivoLlamadoAtencion motivoLlamadoAtencion)
/* 34:   */   {
/* 35:39 */     this.motivoLlamadoAtencionDao.eliminar(motivoLlamadoAtencion);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public MotivoLlamadoAtencion buscarPorId(int idMotivoLlamadoAtencion)
/* 39:   */   {
/* 40:46 */     return (MotivoLlamadoAtencion)this.motivoLlamadoAtencionDao.buscarPorId(Integer.valueOf(idMotivoLlamadoAtencion));
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<MotivoLlamadoAtencion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 44:   */   {
/* 45:51 */     return this.motivoLlamadoAtencionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioMotivoLlamadoAtencionImpl
 * JD-Core Version:    0.7.0.1
 */