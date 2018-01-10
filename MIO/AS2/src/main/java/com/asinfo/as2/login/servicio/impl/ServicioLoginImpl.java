/*  1:   */ package com.asinfo.as2.login.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*  4:   */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  5:   */ import com.asinfo.as2.login.servicio.ServicioLoginBean;
/*  6:   */ import javax.ejb.EJB;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class ServicioLoginImpl
/* 11:   */   implements ServicioLoginBean
/* 12:   */ {
/* 13:   */   @EJB
/* 14:   */   private UsuarioDao usuarioDao;
/* 15:   */   
/* 16:   */   public EntidadUsuario getUsuario(String nombreUsuario, String contrasenia)
/* 17:   */   {
/* 18:35 */     return new EntidadUsuario();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.login.servicio.impl.ServicioLoginImpl
 * JD-Core Version:    0.7.0.1
 */