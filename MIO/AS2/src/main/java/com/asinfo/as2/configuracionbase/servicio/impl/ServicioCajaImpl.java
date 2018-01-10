/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCaja;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCajaRemoto;
/*   5:    */ import com.asinfo.as2.dao.CajaDao;
/*   6:    */ import com.asinfo.as2.dao.InterfazContableProcesoDao;
/*   7:    */ import com.asinfo.as2.entities.Caja;
/*   8:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   9:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ServicioCajaImpl
/*  19:    */   implements ServicioCaja, ServicioCajaRemoto
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   private CajaDao cajaDao;
/*  23:    */   @EJB
/*  24:    */   private InterfazContableProcesoDao interfazContableProcesoDao;
/*  25:    */   
/*  26:    */   public void guardar(Caja caja)
/*  27:    */   {
/*  28: 51 */     this.cajaDao.guardar(caja);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void eliminar(Caja caja)
/*  32:    */   {
/*  33: 62 */     this.cajaDao.eliminar(caja);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Caja buscarPorId(int idCaja)
/*  37:    */   {
/*  38: 73 */     return (Caja)this.cajaDao.buscarPorId(Integer.valueOf(idCaja));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<Caja> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  42:    */   {
/*  43: 83 */     return this.cajaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List getReporteCaja(Date fechaDesde, Date fechaHasta, String nombreUsuario, int idFormaPago)
/*  47:    */   {
/*  48: 95 */     return this.cajaDao.getReporteCaja(fechaDesde, fechaHasta, nombreUsuario, idFormaPago);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Caja cargarDetalle(int idCaja)
/*  52:    */   {
/*  53:105 */     Caja caja = new Caja();
/*  54:106 */     caja = buscarPorId(idCaja);
/*  55:107 */     caja.getIdCaja();
/*  56:108 */     caja.getPuntoDeVenta().getIdPuntoDeVenta();
/*  57:109 */     return caja;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Caja buscarCaja(Map<String, String> filters)
/*  61:    */     throws ExcepcionAS2
/*  62:    */   {
/*  63:119 */     return this.cajaDao.buscarCaja(filters);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List getReporteDepositoCierreCajaBean(InterfazContableProceso interfazContableProceso)
/*  67:    */   {
/*  68:125 */     return this.interfazContableProcesoDao.getReporteDepositoCierreCajaBean(interfazContableProceso);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<Object[]> getListaReporteDepositosCierreCaja(Date fechaDesde, Date fechaHasta, int idOrganizacion)
/*  72:    */   {
/*  73:130 */     return this.interfazContableProcesoDao.getListaReporteDepositosCierreCaja(fechaDesde, fechaHasta, idOrganizacion);
/*  74:    */   }
/*  75:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioCajaImpl
 * JD-Core Version:    0.7.0.1
 */