/*  1:   */ package com.asinfo.as2.inventario.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MovimientoUnidadManejoDao;
/*  4:   */ import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*  7:   */ import com.asinfo.as2.entities.SaldoUnidadManejo;
/*  8:   */ import com.asinfo.as2.entities.Subempresa;
/*  9:   */ import com.asinfo.as2.entities.Sucursal;
/* 10:   */ import com.asinfo.as2.entities.Transportista;
/* 11:   */ import com.asinfo.as2.entities.UnidadManejo;
/* 12:   */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoUnidadManejo;
/* 13:   */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoUnidadManejo;
/* 14:   */ import java.util.Date;
/* 15:   */ import java.util.List;
/* 16:   */ import javax.ejb.EJB;
/* 17:   */ import javax.ejb.Stateless;
/* 18:   */ 
/* 19:   */ @Stateless
/* 20:   */ public class ServicioReporteMovimientoUnidadManejoImpl
/* 21:   */   implements ServicioReporteMovimientoUnidadManejo
/* 22:   */ {
/* 23:   */   @EJB
/* 24:   */   private MovimientoUnidadManejoDao movimientoUnidadManejoDao;
/* 25:   */   @EJB
/* 26:   */   private ServicioMovimientoUnidadManejo servicioMovimientoUnidadManejo;
/* 27:   */   
/* 28:   */   public List<SaldoUnidadManejo> getSaldoUnidadManejoTransportista(int idOrganizacion, Transportista transportista)
/* 29:   */   {
/* 30:47 */     return this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(idOrganizacion, transportista, null);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<SaldoUnidadManejo> getSaldoUnidadManejoSucursal(int idOrganizacion, Sucursal sucursal)
/* 34:   */   {
/* 35:52 */     return this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(idOrganizacion, sucursal, null);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<SaldoUnidadManejo> getSaldoUnidadManejoEmpresa(int idOrganizacion, Empresa empresa, Subempresa subempresa)
/* 39:   */   {
/* 40:57 */     return this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(idOrganizacion, empresa, subempresa, null);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<Object[]> getSalgoInicialUnidadManejo(int idOrganizacion, Sucursal sucursal, Transportista transportista, Empresa empresa, Subempresa subempresa, UnidadManejo unidadManejo, Date fechaDesde, Date fechaHasta)
/* 44:   */   {
/* 45:63 */     return this.movimientoUnidadManejoDao.getSalgoInicialUnidadManejo(idOrganizacion, sucursal, transportista, empresa, subempresa, unidadManejo, fechaDesde, fechaHasta);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public List<DetalleMovimientoUnidadManejo> getReporteKardexUnidadManejo(int idOrganizacion, Sucursal sucursal, Transportista transportista, Empresa empresa, Subempresa subempresa, UnidadManejo unidadManejo, Date fechaDesde, Date fechaHasta)
/* 49:   */   {
/* 50:70 */     return this.movimientoUnidadManejoDao.getReporteKardexUnidadManejo(idOrganizacion, sucursal, transportista, empresa, subempresa, unidadManejo, fechaDesde, fechaHasta);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public List<Object[]> getReporteTransferencia(MovimientoUnidadManejo transferencia)
/* 54:   */   {
/* 55:76 */     return this.movimientoUnidadManejoDao.getReporteTransferencia(transferencia);
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.impl.ServicioReporteMovimientoUnidadManejoImpl
 * JD-Core Version:    0.7.0.1
 */