/*  1:   */ package com.asinfo.as2.seguridad.modelo;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  4:   */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  5:   */ import com.asinfo.as2.util.seguridad.AuthorizationPermission;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import java.security.Principal;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.HashMap;
/* 10:   */ import java.util.List;
/* 11:   */ 
/* 12:   */ public class Rol
/* 13:   */   implements Principal, Serializable
/* 14:   */ {
/* 15:   */   private static final long serialVersionUID = 1L;
/* 16:   */   private String name;
/* 17:34 */   private HashMap<String, AuthorizationPermission> hmPermisos = new HashMap();
/* 18:35 */   private List<AuthorizationPermission> listaPermisos = new ArrayList();
/* 19:   */   
/* 20:   */   public Rol(EntidadRol entidadRol)
/* 21:   */   {
/* 22:38 */     setName(entidadRol.getNombre());
/* 23:39 */     for (EntidadPermiso pe : entidadRol.getListaPermiso()) {
/* 24:40 */       agregarPermiso(pe.toPermision());
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Rol(String name)
/* 29:   */   {
/* 30:45 */     this.name = name;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Rol() {}
/* 34:   */   
/* 35:   */   public String getName()
/* 36:   */   {
/* 37:53 */     return this.name;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setName(String name)
/* 41:   */   {
/* 42:57 */     this.name = name;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public HashMap<String, AuthorizationPermission> getPermissions()
/* 46:   */   {
/* 47:61 */     return this.hmPermisos;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void agregarPermiso(AuthorizationPermission permission)
/* 51:   */   {
/* 52:71 */     this.hmPermisos.put(permission.getViewId(), permission);
/* 53:72 */     this.listaPermisos.add(permission);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public List<AuthorizationPermission> getListaPermisos()
/* 57:   */   {
/* 58:81 */     return this.listaPermisos;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void setListaPermisos(List<AuthorizationPermission> listaPermisos)
/* 62:   */   {
/* 63:91 */     this.listaPermisos = listaPermisos;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.modelo.Rol
 * JD-Core Version:    0.7.0.1
 */