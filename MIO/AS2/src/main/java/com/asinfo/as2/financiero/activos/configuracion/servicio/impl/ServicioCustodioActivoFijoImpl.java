/*   1:    */ package com.asinfo.as2.financiero.activos.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ActivoFijoDao;
/*   4:    */ import com.asinfo.as2.dao.CustodioActivoFijoDao;
/*   5:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   6:    */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*   7:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCustodioActivoFijo;
/*   8:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioCustodioActivoFijoImpl
/*  17:    */   implements ServicioCustodioActivoFijo
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private CustodioActivoFijoDao custodioActivoFijoDao;
/*  21:    */   @EJB
/*  22:    */   private ActivoFijoDao activoFijoDao;
/*  23:    */   
/*  24:    */   public void guardar(CustodioActivoFijo custodioActivoFijo)
/*  25:    */     throws ExcepcionAS2Financiero
/*  26:    */   {
/*  27: 47 */     if (!custodioActivoFijo.getFechaInicio().before(custodioActivoFijo.getActivoFijo().getFechaFacturaProveedor()))
/*  28:    */     {
/*  29: 48 */       actualizaFechaCustodioFijo(custodioActivoFijo);
/*  30: 49 */       this.custodioActivoFijoDao.guardar(custodioActivoFijo);
/*  31: 50 */       this.activoFijoDao.guardar(custodioActivoFijo.getActivoFijo());
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35: 52 */       throw new ExcepcionAS2Financiero("msg_error_fecha_custodio_factura");
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void eliminar(CustodioActivoFijo custodioActivoFijo)
/*  40:    */   {
/*  41: 63 */     this.custodioActivoFijoDao.eliminar(custodioActivoFijo);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CustodioActivoFijo buscarPorId(int idCustodioActivoFijo)
/*  45:    */   {
/*  46: 74 */     return (CustodioActivoFijo)this.custodioActivoFijoDao.buscarPorId(Integer.valueOf(idCustodioActivoFijo));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<CustodioActivoFijo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  50:    */   {
/*  51: 86 */     return this.custodioActivoFijoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<CustodioActivoFijo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56: 97 */     return this.custodioActivoFijoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int contarPorCriterio(Map<String, String> filters)
/*  60:    */   {
/*  61:107 */     return this.custodioActivoFijoDao.contarPorCriterio(filters);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public CustodioActivoFijo cargarDetalle(int idCustodioActivoFijo)
/*  65:    */   {
/*  66:117 */     return this.custodioActivoFijoDao.cargarDetalle(idCustodioActivoFijo);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CustodioActivoFijo buscarPorIdActivoFijoFechaFinNull(int idActivoFijo)
/*  70:    */   {
/*  71:127 */     return this.custodioActivoFijoDao.buscarPorIdActivoFijoFechaFinNull(idActivoFijo);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void actualizaFechaCustodioFijo(CustodioActivoFijo custodioActivoFijo)
/*  75:    */   {
/*  76:138 */     CustodioActivoFijo custodioActivoFijoFechaNull = this.custodioActivoFijoDao.buscarPorIdActivoFijoFechaFinNull(custodioActivoFijo.getActivoFijo()
/*  77:139 */       .getId());
/*  78:140 */     if (custodioActivoFijoFechaNull != null)
/*  79:    */     {
/*  80:141 */       custodioActivoFijoFechaNull.setFechaFin(custodioActivoFijo.getFechaInicio());
/*  81:142 */       custodioActivoFijoFechaNull.setActivo(false);
/*  82:143 */       custodioActivoFijo.setCustodioActivoFijoAnterior(custodioActivoFijoFechaNull);
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioCustodioActivoFijoImpl
 * JD-Core Version:    0.7.0.1
 */