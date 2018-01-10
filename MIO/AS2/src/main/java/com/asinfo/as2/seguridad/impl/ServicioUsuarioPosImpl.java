/*  1:   */ package com.asinfo.as2.seguridad.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*  4:   */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  5:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  6:   */ import com.asinfo.as2.seguridad.ServicioUsuarioPosRemoto;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Remote;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ @Remote({ServicioUsuarioPosRemoto.class})
/* 15:   */ public class ServicioUsuarioPosImpl
/* 16:   */   implements ServicioUsuarioPosRemoto
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private UsuarioDao usuarioDao;
/* 20:   */   
/* 21:   */   public EntidadUsuario loginPos(String nombreUsuario, String clave)
/* 22:   */     throws ExcepcionAS2
/* 23:   */   {
/* 24:46 */     return this.usuarioDao.loginPos(nombreUsuario, clave);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List<EntidadUsuario> obtenerListaAgenteComercial()
/* 28:   */   {
/* 29:56 */     HashMap<String, String> filters = new HashMap();
/* 30:57 */     filters.put("indicadorAgenteComercial", "true");
/* 31:58 */     return this.usuarioDao.obtenerListaCombo("nombre1", true, filters);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String imprimirMensaje(String mensaje)
/* 35:   */   {
/* 36:64 */     String nuevoMensaje = mensaje + "!!!??";
/* 37:65 */     return nuevoMensaje;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioUsuarioPosImpl
 * JD-Core Version:    0.7.0.1
 */