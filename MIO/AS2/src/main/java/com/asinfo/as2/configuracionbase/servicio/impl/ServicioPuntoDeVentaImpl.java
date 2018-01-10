/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVentaRemoto;
/*   5:    */ import com.asinfo.as2.dao.CajaDao;
/*   6:    */ import com.asinfo.as2.dao.PuntoDeVentaDao;
/*   7:    */ import com.asinfo.as2.entities.Caja;
/*   8:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioPuntoDeVentaImpl
/*  18:    */   implements ServicioPuntoDeVenta, ServicioPuntoDeVentaRemoto
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private PuntoDeVentaDao puntoDeVentaDao;
/*  22:    */   @EJB
/*  23:    */   private CajaDao cajaDao;
/*  24:    */   
/*  25:    */   public void guardar(PuntoDeVenta puntoDeVenta)
/*  26:    */     throws ExcepcionAS2Financiero
/*  27:    */   {
/*  28: 49 */     if (puntoDeVenta.getCodigo().equals("000")) {
/*  29: 50 */       throw new ExcepcionAS2Financiero("msg_error_codigo_serie_autorizacion", " Punto de Venta");
/*  30:    */     }
/*  31: 52 */     for (Caja caja : puntoDeVenta.getListaCaja()) {
/*  32: 53 */       this.cajaDao.guardar(caja);
/*  33:    */     }
/*  34: 55 */     this.puntoDeVentaDao.guardar(puntoDeVenta);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void eliminar(PuntoDeVenta punto)
/*  38:    */     throws ExcepcionAS2Financiero
/*  39:    */   {
/*  40: 67 */     punto = cargarDetalle(punto.getId());
/*  41: 68 */     punto.setEliminado(true);
/*  42: 69 */     for (Caja caja : punto.getListaCaja()) {
/*  43: 70 */       caja.setEliminado(true);
/*  44:    */     }
/*  45: 73 */     guardar(punto);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public PuntoDeVenta buscarPorId(Integer id)
/*  49:    */   {
/*  50: 84 */     return this.puntoDeVentaDao.buscarPorId(id);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<PuntoDeVenta> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  54:    */   {
/*  55: 94 */     return this.puntoDeVentaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<PuntoDeVenta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60:104 */     return this.puntoDeVentaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int contarPorCriterio(Map<String, String> filters)
/*  64:    */   {
/*  65:114 */     return this.puntoDeVentaDao.contarPorCriterio(filters);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public PuntoDeVenta cargarDetalle(int id)
/*  69:    */   {
/*  70:124 */     return this.puntoDeVentaDao.cargarDetalle(id);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public PuntoDeVenta buscarPuntoDeVenta(Map<String, String> filters)
/*  74:    */     throws ExcepcionAS2
/*  75:    */   {
/*  76:134 */     return this.puntoDeVentaDao.buscarPuntoDeVenta(filters);
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioPuntoDeVentaImpl
 * JD-Core Version:    0.7.0.1
 */