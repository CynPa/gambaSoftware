/*  1:   */ package com.asinfo.as2.financiero.cobros.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.cobros.ReporteNotaCreditoClienteDao;
/*  4:   */ import com.asinfo.as2.entities.Empresa;
/*  5:   */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  6:   */ import com.asinfo.as2.entities.Sucursal;
/*  7:   */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteNotaCreditoCliente;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ServicioReportenotaCreditoClienteImpl
/* 15:   */   implements ServicioReporteNotaCreditoCliente
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private ReporteNotaCreditoClienteDao reporteAntiClienteDao;
/* 19:   */   
/* 20:   */   public List<Object[]> getListaNotaCreditoCliente(Date fechaDesde, Date fechaHasta, Empresa empresa, MotivoNotaCreditoCliente motivoNotaCreditoCliente, int idOrganizacion, Sucursal sucursal)
/* 21:   */   {
/* 22:39 */     return this.reporteAntiClienteDao.getListaNotaCreditoCliente(fechaDesde, fechaHasta, empresa, motivoNotaCreditoCliente, idOrganizacion, sucursal);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List getReporteNotasCredito(Date fechaDesde, Date fechaHasta, Empresa empresa, MotivoNotaCreditoCliente motivoNotaCreditoCliente, int idOrganizacion, Sucursal sucursal)
/* 26:   */   {
/* 27:46 */     return this.reporteAntiClienteDao.getReporteNotasCredito(fechaDesde, fechaHasta, empresa, motivoNotaCreditoCliente, idOrganizacion, sucursal);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.impl.ServicioReportenotaCreditoClienteImpl
 * JD-Core Version:    0.7.0.1
 */