/*  1:   */ package com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteGarantiaClienteDao;
/*  4:   */ import com.asinfo.as2.entities.Empresa;
/*  5:   */ import com.asinfo.as2.entities.Sucursal;
/*  6:   */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*  7:   */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
/*  8:   */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  9:   */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteGarantiaCliente;
/* 10:   */ import java.util.Date;
/* 11:   */ import java.util.List;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.ejb.Stateless;
/* 14:   */ 
/* 15:   */ @Stateless
/* 16:   */ public class ServicioReporteGarantiaClienteImpl
/* 17:   */   implements ServicioReporteGarantiaCliente
/* 18:   */ {
/* 19:   */   @EJB
/* 20:   */   private ReporteGarantiaClienteDao reporteGarantiaClienteDao;
/* 21:   */   
/* 22:   */   public List getReporteGarantiaCliente(int idGarantiaCliente)
/* 23:   */   {
/* 24:41 */     return this.reporteGarantiaClienteDao.getReporteGarantiaCliente(idGarantiaCliente);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List getReporteGarantiaClienteLista(Empresa empresa, EstadoGarantiaCliente estadoGarantiaCliente, Date fechaIngresoDesde, Date fechaIngresoHasta, TipoGarantiaCliente tipoGarantiaCliente, int idOrganizacion, boolean indicadorFechaRegistro, Sucursal sucursal, TipoOrganizacion tipoOrganizacion)
/* 28:   */   {
/* 29:56 */     return this.reporteGarantiaClienteDao.getReporteGarantiaClienteLista(empresa, estadoGarantiaCliente, fechaIngresoDesde, fechaIngresoHasta, tipoGarantiaCliente, idOrganizacion, indicadorFechaRegistro, sucursal, tipoOrganizacion);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioReporteGarantiaClienteImpl
 * JD-Core Version:    0.7.0.1
 */