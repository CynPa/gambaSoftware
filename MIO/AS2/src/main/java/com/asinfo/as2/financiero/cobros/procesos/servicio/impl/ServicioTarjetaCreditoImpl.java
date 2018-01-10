/*  1:   */ package com.asinfo.as2.financiero.cobros.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.TarjetaCreditoDao;
/*  4:   */ import com.asinfo.as2.entities.Comision;
/*  5:   */ import com.asinfo.as2.entities.TarjetaCredito;
/*  6:   */ import com.asinfo.as2.entities.VersionComision;
/*  7:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  8:   */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioTarjetaCredito;
/*  9:   */ import com.asinfo.as2.servicio.ServicioGenerico;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.ejb.Stateless;
/* 14:   */ 
/* 15:   */ @Stateless
/* 16:   */ public class ServicioTarjetaCreditoImpl
/* 17:   */   implements ServicioTarjetaCredito
/* 18:   */ {
/* 19:   */   @EJB
/* 20:   */   private TarjetaCreditoDao tarjetaCreditoDao;
/* 21:   */   @EJB
/* 22:   */   private ServicioGenerico<VersionComision> servicioVersionComision;
/* 23:   */   @EJB
/* 24:   */   private ServicioGenerico<Comision> servicioComision;
/* 25:   */   
/* 26:   */   public void guardar(TarjetaCredito tarjetaCredito)
/* 27:   */     throws AS2Exception
/* 28:   */   {
/* 29:44 */     for (VersionComision versionComision : tarjetaCredito.getListaVersionComision())
/* 30:   */     {
/* 31:45 */       versionComision.setTarjetaCredito(tarjetaCredito);
/* 32:46 */       this.servicioVersionComision.guardar(versionComision);
/* 33:47 */       for (Comision comision : versionComision.getListaComision()) {
/* 34:48 */         this.servicioComision.guardar(comision);
/* 35:   */       }
/* 36:   */     }
/* 37:51 */     this.tarjetaCreditoDao.guardar(tarjetaCredito);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public List<TarjetaCredito> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 41:   */   {
/* 42:56 */     return this.tarjetaCreditoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int contarPorCriterio(Map<String, String> filters)
/* 46:   */   {
/* 47:61 */     return this.tarjetaCreditoDao.contarPorCriterio(filters);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public TarjetaCredito cargarDetalle(int idTarjetaCredito)
/* 51:   */   {
/* 52:66 */     return this.tarjetaCreditoDao.cargarDetalle(idTarjetaCredito);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public List<TarjetaCredito> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 56:   */   {
/* 57:71 */     return this.tarjetaCreditoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioTarjetaCreditoImpl
 * JD-Core Version:    0.7.0.1
 */