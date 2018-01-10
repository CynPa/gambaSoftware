/*  1:   */ package com.asinfo.as2.rs.seguridad.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ConsultarUsuarioRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private Integer idOrganizacion;
/*  9:   */   private String usuario;
/* 10:   */   private String clave;
/* 11:   */   private String sistema;
/* 12:   */   
/* 13:   */   public Integer getIdOrganizacion()
/* 14:   */   {
/* 15:17 */     return this.idOrganizacion;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 19:   */   {
/* 20:21 */     this.idOrganizacion = idOrganizacion;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getUsuario()
/* 24:   */   {
/* 25:28 */     return this.usuario;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setUsuario(String usuario)
/* 29:   */   {
/* 30:36 */     this.usuario = usuario;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getClave()
/* 34:   */   {
/* 35:43 */     return this.clave;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setClave(String clave)
/* 39:   */   {
/* 40:51 */     this.clave = clave;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getSistema()
/* 44:   */   {
/* 45:58 */     return this.sistema;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setSistema(String sistema)
/* 49:   */   {
/* 50:66 */     this.sistema = sistema;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.ConsultarUsuarioRequestDto
 * JD-Core Version:    0.7.0.1
 */