/*  1:   */ package com.asinfo.as2.webservices.seguridad.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  4:   */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  5:   */ import com.asinfo.as2.webservices.seguridad.ServicioWebUsuario;
/*  6:   */ import com.asinfo.pos.model.Usuario;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.jws.WebService;
/* 13:   */ 
/* 14:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.seguridad.ServicioWebUsuario")
/* 15:   */ public class ServicioWebUsuarioImpl
/* 16:   */   implements ServicioWebUsuario
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private transient ServicioUsuario servicioUsuario;
/* 20:   */   
/* 21:   */   public List<Usuario> getListaUsuarioPos()
/* 22:   */   {
/* 23:44 */     Map<String, String> filters = new HashMap();
/* 24:45 */     filters.put("indicadorUsuarioPos", "true");
/* 25:   */     
/* 26:47 */     List<Usuario> lista = new ArrayList();
/* 27:   */     
/* 28:49 */     List<EntidadUsuario> datos = this.servicioUsuario.obtenerListaCombo(null, false, filters);
/* 29:50 */     for (EntidadUsuario usuarioAS2 : datos)
/* 30:   */     {
/* 31:51 */       Usuario usuarioPos = new Usuario();
/* 32:52 */       usuarioPos.setActivo(usuarioAS2.isActivo());
/* 33:53 */       usuarioPos.setClave(usuarioAS2.getClave());
/* 34:54 */       usuarioPos.setNombreUsuario(usuarioAS2.getNombreUsuario());
/* 35:55 */       lista.add(usuarioPos);
/* 36:   */     }
/* 37:58 */     return lista;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.seguridad.impl.ServicioWebUsuarioImpl
 * JD-Core Version:    0.7.0.1
 */