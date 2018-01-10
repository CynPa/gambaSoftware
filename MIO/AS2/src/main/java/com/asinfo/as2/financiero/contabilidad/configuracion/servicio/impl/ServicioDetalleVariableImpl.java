/*  1:   */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.DetalleVariableDao;
/*  4:   */ import com.asinfo.as2.entities.DetalleVariable;
/*  5:   */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDetalleVariable;
/*  6:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioDetalleVariableImpl
/* 14:   */   implements ServicioDetalleVariable
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private DetalleVariableDao detalleVariableDao;
/* 18:   */   
/* 19:   */   public void guardar(DetalleVariable detalleVariable)
/* 20:   */     throws ExcepcionAS2Financiero
/* 21:   */   {
/* 22:46 */     this.detalleVariableDao.guardar(detalleVariable);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void eliminar(DetalleVariable detalleVariable)
/* 26:   */   {
/* 27:53 */     this.detalleVariableDao.eliminar(detalleVariable);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public DetalleVariable buscarPorId(Integer id)
/* 31:   */   {
/* 32:60 */     return (DetalleVariable)this.detalleVariableDao.buscarPorId(id);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public List<DetalleVariable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 36:   */   {
/* 37:68 */     return this.detalleVariableDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public DetalleVariable cargarDetalle(int idDetalleVariable)
/* 41:   */   {
/* 42:74 */     return this.detalleVariableDao.cargarDetalle(idDetalleVariable);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int contarPorCriterio(Map<String, String> filters)
/* 46:   */   {
/* 47:79 */     return this.detalleVariableDao.contarPorCriterio(filters);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioDetalleVariableImpl
 * JD-Core Version:    0.7.0.1
 */