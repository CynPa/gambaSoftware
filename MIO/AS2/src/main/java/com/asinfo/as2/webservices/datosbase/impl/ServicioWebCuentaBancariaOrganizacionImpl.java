/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Banco;
/*  4:   */ import com.asinfo.as2.entities.CuentaBancaria;
/*  5:   */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  6:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebCuentaBancariaOrganizacion;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.jws.WebService;
/* 13:   */ 
/* 14:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebCuentaBancariaOrganizacion")
/* 15:   */ public class ServicioWebCuentaBancariaOrganizacionImpl
/* 16:   */   implements ServicioWebCuentaBancariaOrganizacion
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/* 20:   */   
/* 21:   */   public List<com.asinfo.pos.model.CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacionPos(int idOrganizacion)
/* 22:   */   {
/* 23:43 */     Map<String, String> filters = new HashMap();
/* 24:44 */     filters.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 25:   */     
/* 26:46 */     List<com.asinfo.pos.model.CuentaBancariaOrganizacion> lista = new ArrayList();
/* 27:   */     
/* 28:48 */     List<com.asinfo.as2.entities.CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionAS2 = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, filters);
/* 29:50 */     for (com.asinfo.as2.entities.CuentaBancariaOrganizacion cuentaBancariaAS2 : listaCuentaBancariaOrganizacionAS2)
/* 30:   */     {
/* 31:51 */       com.asinfo.pos.model.CuentaBancariaOrganizacion cuentaBancariaOrganizacion = new com.asinfo.pos.model.CuentaBancariaOrganizacion();
/* 32:52 */       cuentaBancariaOrganizacion.setBanco(cuentaBancariaAS2.getCuentaBancaria().getBanco().getNombre());
/* 33:53 */       cuentaBancariaOrganizacion.setCuentaBancaria(cuentaBancariaAS2.getCuentaBancaria().getNumero());
/* 34:54 */       cuentaBancariaOrganizacion.setCuentaBancariaMostrar(cuentaBancariaAS2.getCuentaBancaria().getBanco().getNombre() + " #" + cuentaBancariaAS2
/* 35:55 */         .getCuentaBancaria().getNumero());
/* 36:56 */       cuentaBancariaOrganizacion.setIdCuentaBancariaOrganizacionAS2(cuentaBancariaAS2.getIdCuentaBancariaOrganizacion());
/* 37:   */       
/* 38:58 */       lista.add(cuentaBancariaOrganizacion);
/* 39:   */     }
/* 40:61 */     return lista;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebCuentaBancariaOrganizacionImpl
 * JD-Core Version:    0.7.0.1
 */