/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*  4:   */ import com.asinfo.as2.entities.Organizacion;
/*  5:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebOrganizacion;
/*  6:   */ import com.asinfo.as2.ws.datosbase.OrganizacionWS;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.jws.WebService;
/* 11:   */ 
/* 12:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebOrganizacion")
/* 13:   */ public class ServicioWebOrganizacionImpl
/* 14:   */   implements ServicioWebOrganizacion
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private transient ServicioOrganizacion servicioOrganizacion;
/* 18:   */   
/* 19:   */   public List<OrganizacionWS> getListaOrganizacionPos()
/* 20:   */   {
/* 21:45 */     List<OrganizacionWS> lista = new ArrayList();
/* 22:46 */     List<Organizacion> listaOrganizacion = this.servicioOrganizacion.obtenerListaCombo(null, false, null);
/* 23:49 */     for (Organizacion organizacion : listaOrganizacion)
/* 24:   */     {
/* 25:51 */       OrganizacionWS organizacionWS = new OrganizacionWS();
/* 26:52 */       organizacionWS.setIdOrganizacionAS2(organizacion.getId());
/* 27:53 */       organizacionWS.setRazonSocial(organizacion.getRazonSocial());
/* 28:   */       
/* 29:55 */       lista.add(organizacionWS);
/* 30:   */     }
/* 31:59 */     return lista;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public OrganizacionWS buscarPorId(int idOrganizacionAS2)
/* 35:   */   {
/* 36:69 */     Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacionAS2));
/* 37:70 */     OrganizacionWS organizacionWS = new OrganizacionWS();
/* 38:71 */     organizacionWS.setIdOrganizacionAS2(organizacion.getId());
/* 39:72 */     organizacionWS.setIdentificacion(organizacion.getIdentificacion());
/* 40:73 */     organizacionWS.setIdentificacionRepresentante(organizacion.getIdentificacionRepresentante());
/* 41:74 */     organizacionWS.setRepresentanteLegal(organizacion.getIdentificacionRepresentante());
/* 42:75 */     organizacionWS.setIdentificacionContador(organizacion.getIdentificacionContador());
/* 43:76 */     organizacionWS.setRazonSocial(organizacion.getRazonSocial());
/* 44:77 */     organizacionWS.setPaginaWeb(organizacion.getPaginaWeb());
/* 45:78 */     organizacionWS.setImagen(organizacion.getImagen());
/* 46:79 */     return organizacionWS;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebOrganizacionImpl
 * JD-Core Version:    0.7.0.1
 */