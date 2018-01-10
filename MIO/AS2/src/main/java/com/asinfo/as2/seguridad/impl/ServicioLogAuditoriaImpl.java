/*  1:   */ package com.asinfo.as2.seguridad.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.seguridad.LogAuditoriaDao;
/*  4:   */ import com.asinfo.as2.entities.seguridad.LogAuditoria;
/*  5:   */ import com.asinfo.as2.seguridad.ServicioLogAuditoria;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioLogAuditoriaImpl
/* 14:   */   implements ServicioLogAuditoria
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private LogAuditoriaDao logAuditoriaDao;
/* 18:   */   
/* 19:   */   public List<LogAuditoria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 20:   */   {
/* 21:44 */     return this.logAuditoriaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int contar()
/* 25:   */   {
/* 26:54 */     return this.logAuditoriaDao.contar();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public List<LogAuditoria> obtenerAuditoriaFiltrado(String nombreUsuario, String entidad, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 30:   */   {
/* 31:65 */     return this.logAuditoriaDao.obtenerAuditoriaFiltrado(nombreUsuario, entidad, fechaDesde, fechaHasta, idOrganizacion);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public List<Object[]> listaReporteLogAuditoria(Date fechaDesde, Date fechaHasta, String nombreUsuario, String proceso, int idOrganizacion)
/* 35:   */   {
/* 36:70 */     return this.logAuditoriaDao.listaReporteLogAuditoria(fechaDesde, fechaHasta, nombreUsuario, proceso, idOrganizacion);
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioLogAuditoriaImpl
 * JD-Core Version:    0.7.0.1
 */