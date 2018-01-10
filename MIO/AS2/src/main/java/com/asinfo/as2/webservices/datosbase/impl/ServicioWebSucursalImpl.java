/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*  4:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebSucursal;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.jws.WebService;
/* 11:   */ 
/* 12:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebSucursal")
/* 13:   */ public class ServicioWebSucursalImpl
/* 14:   */   implements ServicioWebSucursal
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private transient ServicioSucursal servicioSucursal;
/* 18:   */   
/* 19:   */   public List<com.asinfo.pos.model.Sucursal> getListaSucursalPos()
/* 20:   */   {
/* 21:44 */     List<com.asinfo.pos.model.Sucursal> lista = new ArrayList();
/* 22:45 */     List<com.asinfo.as2.entities.Sucursal> listaSucursalAS2 = this.servicioSucursal.obtenerListaCombo(null, false, null);
/* 23:48 */     for (com.asinfo.as2.entities.Sucursal sucursal : listaSucursalAS2)
/* 24:   */     {
/* 25:49 */       com.asinfo.pos.model.Sucursal sucursalDato = new com.asinfo.pos.model.Sucursal();
/* 26:50 */       sucursalDato.setCodigo(sucursal.getCodigo());
/* 27:51 */       sucursalDato.setNombre(sucursal.getNombre());
/* 28:52 */       sucursalDato.setIdSucursalAS2(sucursal.getIdSucursal());
/* 29:53 */       sucursalDato.setIdOrganizacionAs2(sucursal.getIdOrganizacion());
/* 30:54 */       lista.add(sucursalDato);
/* 31:   */     }
/* 32:57 */     return lista;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public List<com.asinfo.pos.model.Sucursal> getListaSucursalPorOrganizacionPos(int idOrganizacion)
/* 36:   */   {
/* 37:65 */     List<com.asinfo.pos.model.Sucursal> lista = new ArrayList();
/* 38:66 */     Map<String, String> filters = new HashMap();
/* 39:67 */     filters.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 40:68 */     List<com.asinfo.as2.entities.Sucursal> listaSucursalAS2 = this.servicioSucursal.obtenerListaCombo(null, false, filters);
/* 41:71 */     for (com.asinfo.as2.entities.Sucursal sucursal : listaSucursalAS2)
/* 42:   */     {
/* 43:72 */       com.asinfo.pos.model.Sucursal sucursalDato = new com.asinfo.pos.model.Sucursal();
/* 44:73 */       sucursalDato.setCodigo(sucursal.getCodigo());
/* 45:74 */       sucursalDato.setNombre(sucursal.getNombre());
/* 46:75 */       sucursalDato.setIdSucursalAS2(sucursal.getIdSucursal());
/* 47:76 */       sucursalDato.setIdOrganizacionAs2(sucursal.getIdOrganizacion());
/* 48:77 */       lista.add(sucursalDato);
/* 49:   */     }
/* 50:81 */     return lista;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebSucursalImpl
 * JD-Core Version:    0.7.0.1
 */