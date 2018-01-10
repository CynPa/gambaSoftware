/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*  4:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebFormaPago;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.jws.WebService;
/* 11:   */ 
/* 12:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebFormaPago")
/* 13:   */ public class ServicioWebFormaPagoImpl
/* 14:   */   implements ServicioWebFormaPago
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private ServicioFormaPago servicioFormaPago;
/* 18:   */   
/* 19:   */   public List<com.asinfo.pos.model.FormaPago> getListaFormaPago(int idOrganizacion)
/* 20:   */   {
/* 21:22 */     Map<String, String> filtros = new HashMap();
/* 22:23 */     filtros.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 23:   */     
/* 24:25 */     List<com.asinfo.pos.model.FormaPago> lista = new ArrayList();
/* 25:   */     
/* 26:27 */     List<com.asinfo.as2.entities.FormaPago> datos = this.servicioFormaPago.obtenerListaCombo("", false, filtros);
/* 27:28 */     for (com.asinfo.as2.entities.FormaPago formaPago : datos)
/* 28:   */     {
/* 29:29 */       com.asinfo.pos.model.FormaPago formaPagoPos = new com.asinfo.pos.model.FormaPago();
/* 30:30 */       formaPagoPos.setActivo(formaPago.getActivo());
/* 31:31 */       formaPagoPos.setCodigo(formaPago.getCodigo());
/* 32:32 */       formaPagoPos.setDescripcion(formaPago.getDescripcion());
/* 33:33 */       formaPagoPos.setIdFormaPagoAs2(new Long(formaPago.getIdFormaPago()).longValue());
/* 34:34 */       formaPagoPos.setIndicadorEfectivo(false);
/* 35:35 */       formaPagoPos.setIndicadorRetencionFuente(formaPago.isIndicadorRetencionFuente());
/* 36:36 */       formaPagoPos.setIndicadorRetencionIva(formaPago.isIndicadorRetencionIva());
/* 37:37 */       formaPagoPos.setNombre(formaPago.getNombre());
/* 38:38 */       formaPagoPos.setPredeterminado(formaPago.getPredeterminado());
/* 39:39 */       lista.add(formaPagoPos);
/* 40:   */     }
/* 41:42 */     return lista;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebFormaPagoImpl
 * JD-Core Version:    0.7.0.1
 */