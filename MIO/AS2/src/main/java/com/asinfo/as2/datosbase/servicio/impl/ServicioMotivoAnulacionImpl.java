/*  1:   */ package com.asinfo.as2.datosbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MotivoAnulacionDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioMotivoAnulacion;
/*  5:   */ import com.asinfo.as2.entities.MotivoAnulacion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioMotivoAnulacionImpl
/* 13:   */   implements ServicioMotivoAnulacion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private MotivoAnulacionDao motivoAnulacionDao;
/* 17:   */   
/* 18:   */   public void guardar(MotivoAnulacion motivoAnulacion)
/* 19:   */   {
/* 20:40 */     this.motivoAnulacionDao.guardar(motivoAnulacion);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(MotivoAnulacion motivoAnulacion)
/* 24:   */   {
/* 25:50 */     this.motivoAnulacionDao.eliminar(motivoAnulacion);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public MotivoAnulacion buscarPorId(int id)
/* 29:   */   {
/* 30:60 */     return (MotivoAnulacion)this.motivoAnulacionDao.buscarPorId(Integer.valueOf(id));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<MotivoAnulacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:66 */     return this.motivoAnulacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<MotivoAnulacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:76 */     return this.motivoAnulacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:86 */     return this.motivoAnulacionDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioMotivoAnulacionImpl
 * JD-Core Version:    0.7.0.1
 */