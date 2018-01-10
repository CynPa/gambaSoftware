/*  1:   */ package com.asinfo.as2.ws.seguridad.service.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  4:   */ import com.asinfo.as2.seguridad.ServicioSistema;
/*  5:   */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  6:   */ import com.asinfo.as2.ws.seguridad.model.UsuarioWSEntity;
/*  7:   */ import com.asinfo.as2.ws.seguridad.service.ServicioSeguridadWS;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.jws.WebService;
/* 10:   */ 
/* 11:   */ @WebService(endpointInterface="com.asinfo.as2.ws.seguridad.service.ServicioSeguridadWS")
/* 12:   */ public class ServicioSeguridadWSImpl
/* 13:   */   implements ServicioSeguridadWS
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private ServicioUsuario servicioUsuario;
/* 17:   */   @EJB
/* 18:   */   private ServicioSistema servicioSistema;
/* 19:   */   
/* 20:   */   public UsuarioWSEntity login(String nombreUsuario, String clave, String sistema)
/* 21:   */     throws AS2Exception
/* 22:   */   {
/* 23:42 */     UsuarioWSEntity usuario = this.servicioUsuario.login(nombreUsuario, clave, sistema);
/* 24:43 */     return usuario;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.seguridad.service.impl.ServicioSeguridadWSImpl
 * JD-Core Version:    0.7.0.1
 */