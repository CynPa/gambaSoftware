/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioCaja;
/*  4:   */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  5:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebCaja;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.jws.WebService;
/* 12:   */ 
/* 13:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebCaja")
/* 14:   */ public class ServicioWebCajaImpl
/* 15:   */   implements ServicioWebCaja
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private transient ServicioCaja servicioCaja;
/* 19:   */   
/* 20:   */   public List<com.asinfo.pos.model.Caja> getListaCajaPos()
/* 21:   */   {
/* 22:44 */     List<com.asinfo.pos.model.Caja> lista = new ArrayList();
/* 23:45 */     List<com.asinfo.as2.entities.Caja> listaCajaAS2 = this.servicioCaja.obtenerListaCombo(null, false, null);
/* 24:48 */     for (com.asinfo.as2.entities.Caja caja : listaCajaAS2)
/* 25:   */     {
/* 26:49 */       com.asinfo.pos.model.Caja cajaDato = new com.asinfo.pos.model.Caja();
/* 27:50 */       cajaDato.setCodigo(caja.getCodigo());
/* 28:51 */       cajaDato.setNombre(caja.getNombre());
/* 29:52 */       cajaDato.setIdCajaAS2(caja.getIdCaja());
/* 30:53 */       cajaDato.setIdPuntoDeVentaAS2(caja.getPuntoDeVenta().getIdPuntoDeVenta());
/* 31:54 */       lista.add(cajaDato);
/* 32:   */     }
/* 33:57 */     return lista;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public List<com.asinfo.pos.model.Caja> getListaCajaConPuntoDeVentaPos(int idPuntoDeVenta)
/* 37:   */   {
/* 38:67 */     List<com.asinfo.pos.model.Caja> lista = new ArrayList();
/* 39:68 */     Map<String, String> filters = new HashMap();
/* 40:69 */     filters.put("puntoDeVenta.idPuntoDeVenta", String.valueOf(idPuntoDeVenta));
/* 41:70 */     List<com.asinfo.as2.entities.Caja> listaCajaAS2 = this.servicioCaja.obtenerListaCombo(null, false, filters);
/* 42:73 */     for (com.asinfo.as2.entities.Caja caja : listaCajaAS2)
/* 43:   */     {
/* 44:74 */       com.asinfo.pos.model.Caja cajaDato = new com.asinfo.pos.model.Caja();
/* 45:75 */       cajaDato.setCodigo(caja.getCodigo());
/* 46:76 */       cajaDato.setNombre(caja.getNombre());
/* 47:77 */       cajaDato.setIdCajaAS2(caja.getIdCaja());
/* 48:78 */       cajaDato.setIdPuntoDeVentaAS2(caja.getPuntoDeVenta().getIdPuntoDeVenta());
/* 49:79 */       lista.add(cajaDato);
/* 50:   */     }
/* 51:82 */     return lista;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebCajaImpl
 * JD-Core Version:    0.7.0.1
 */