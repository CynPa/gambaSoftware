/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*  4:   */ import com.asinfo.as2.entities.Sucursal;
/*  5:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebPuntoDeVenta;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.jws.WebService;
/* 12:   */ 
/* 13:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebPuntoDeVenta")
/* 14:   */ public class ServicioWebPuntoDeVentaImpl
/* 15:   */   implements ServicioWebPuntoDeVenta
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/* 19:   */   
/* 20:   */   public List<com.asinfo.pos.model.PuntoDeVenta> getListaPuntoDeVentaPos()
/* 21:   */   {
/* 22:44 */     List<com.asinfo.pos.model.PuntoDeVenta> lista = new ArrayList();
/* 23:45 */     List<com.asinfo.as2.entities.PuntoDeVenta> listaPuntoDeVentaAS2 = this.servicioPuntoDeVenta.obtenerListaCombo(null, false, null);
/* 24:48 */     for (com.asinfo.as2.entities.PuntoDeVenta puntoDeVenta : listaPuntoDeVentaAS2)
/* 25:   */     {
/* 26:49 */       com.asinfo.pos.model.PuntoDeVenta puntoDeVentaDato = new com.asinfo.pos.model.PuntoDeVenta();
/* 27:50 */       puntoDeVentaDato.setCodigo(puntoDeVenta.getCodigo());
/* 28:51 */       puntoDeVentaDato.setNombre(puntoDeVenta.getNombre());
/* 29:52 */       puntoDeVentaDato.setIdPuntoDeVentaAS2(puntoDeVenta.getIdPuntoDeVenta());
/* 30:53 */       puntoDeVentaDato.setIdSucursalAS2(puntoDeVenta.getSucursal().getIdSucursal());
/* 31:54 */       lista.add(puntoDeVentaDato);
/* 32:   */     }
/* 33:57 */     return lista;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public List<com.asinfo.pos.model.PuntoDeVenta> getListaPuntoDeVentaConSucursalPos(int idSucursal)
/* 37:   */   {
/* 38:65 */     List<com.asinfo.pos.model.PuntoDeVenta> lista = new ArrayList();
/* 39:66 */     Map<String, String> filters = new HashMap();
/* 40:67 */     filters.put("sucursal.idSucursal", String.valueOf(idSucursal));
/* 41:68 */     List<com.asinfo.as2.entities.PuntoDeVenta> listaPuntoDeVentaAS2 = this.servicioPuntoDeVenta.obtenerListaCombo(null, false, filters);
/* 42:71 */     for (com.asinfo.as2.entities.PuntoDeVenta puntoDeVenta : listaPuntoDeVentaAS2)
/* 43:   */     {
/* 44:72 */       com.asinfo.pos.model.PuntoDeVenta puntoDeVentaDato = new com.asinfo.pos.model.PuntoDeVenta();
/* 45:73 */       puntoDeVentaDato.setCodigo(puntoDeVenta.getCodigo());
/* 46:74 */       puntoDeVentaDato.setNombre(puntoDeVenta.getNombre());
/* 47:75 */       puntoDeVentaDato.setIdPuntoDeVentaAS2(puntoDeVenta.getIdPuntoDeVenta());
/* 48:76 */       puntoDeVentaDato.setIdSucursalAS2(puntoDeVenta.getSucursal().getIdSucursal());
/* 49:77 */       lista.add(puntoDeVentaDato);
/* 50:   */     }
/* 51:80 */     return lista;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebPuntoDeVentaImpl
 * JD-Core Version:    0.7.0.1
 */