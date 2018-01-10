/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*  4:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebTipoIdentificacion;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.jws.WebService;
/* 11:   */ 
/* 12:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebTipoIdentificacion")
/* 13:   */ public class ServicioWebTipoIdentificacionImpl
/* 14:   */   implements ServicioWebTipoIdentificacion
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/* 18:   */   
/* 19:   */   public List<com.asinfo.pos.model.TipoIdentificacion> getListaTipoIdentificacion(String idOrganizacion)
/* 20:   */   {
/* 21:44 */     Map<String, String> filters = new HashMap();
/* 22:45 */     filters.put("idOrganizacion", idOrganizacion);
/* 23:   */     
/* 24:47 */     List<com.asinfo.pos.model.TipoIdentificacion> lista = new ArrayList();
/* 25:   */     
/* 26:49 */     List<com.asinfo.as2.entities.TipoIdentificacion> datos = this.servicioTipoIdentificacion.obtenerListaCombo(null, false, filters);
/* 27:50 */     for (com.asinfo.as2.entities.TipoIdentificacion tipoIdentificacion : datos)
/* 28:   */     {
/* 29:51 */       com.asinfo.pos.model.TipoIdentificacion tipoIdentificacionPos = new com.asinfo.pos.model.TipoIdentificacion();
/* 30:52 */       tipoIdentificacionPos.setActivo(tipoIdentificacion.isActivo());
/* 31:53 */       tipoIdentificacionPos.setCodigo(tipoIdentificacion.getCodigo());
/* 32:54 */       tipoIdentificacionPos.setDescripcion(tipoIdentificacion.getDescripcion());
/* 33:55 */       tipoIdentificacionPos.setIdTipoIdentificacionAs2(tipoIdentificacion.getIdTipoIdentificacion());
/* 34:56 */       tipoIdentificacionPos.setIndicadorValidarIdentificacion(tipoIdentificacion.isIndicadorValidarIdentificacion());
/* 35:57 */       tipoIdentificacionPos.setNombre(tipoIdentificacion.getNombre());
/* 36:58 */       tipoIdentificacionPos.setPredeterminado(tipoIdentificacion.isPredeterminado());
/* 37:59 */       lista.add(tipoIdentificacionPos);
/* 38:   */     }
/* 39:62 */     return lista;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebTipoIdentificacionImpl
 * JD-Core Version:    0.7.0.1
 */