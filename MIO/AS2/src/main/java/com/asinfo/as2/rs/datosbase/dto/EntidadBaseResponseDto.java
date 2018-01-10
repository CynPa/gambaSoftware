/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public abstract class EntidadBaseResponseDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idOrganizacion;
/* 11:   */   private Integer idSucursal;
/* 12:   */   private String usuarioCreacion;
/* 13:   */   private String usuarioModificacion;
/* 14:   */   private String urlApp;
/* 15:   */   
/* 16:   */   public abstract int getHashCode();
/* 17:   */   
/* 18:   */   public int hashCode()
/* 19:   */   {
/* 20:20 */     int hash = 1;
/* 21:21 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/* 22:22 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/* 23:23 */     hash += hash * 11 + (this.usuarioCreacion + "").hashCode();
/* 24:24 */     hash += hash * 13 + (this.usuarioModificacion + "").hashCode();
/* 25:25 */     return hash;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Integer getIdOrganizacion()
/* 29:   */   {
/* 30:29 */     return this.idOrganizacion;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 34:   */   {
/* 35:33 */     this.idOrganizacion = idOrganizacion;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Integer getIdSucursal()
/* 39:   */   {
/* 40:37 */     return this.idSucursal;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setIdSucursal(Integer idSucursal)
/* 44:   */   {
/* 45:41 */     this.idSucursal = idSucursal;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getUsuarioCreacion()
/* 49:   */   {
/* 50:45 */     return this.usuarioCreacion;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void setUsuarioCreacion(String usuarioCreacion)
/* 54:   */   {
/* 55:49 */     this.usuarioCreacion = usuarioCreacion;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String getUsuarioModificacion()
/* 59:   */   {
/* 60:53 */     return this.usuarioModificacion;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void setUsuarioModificacion(String usuarioModificacion)
/* 64:   */   {
/* 65:57 */     this.usuarioModificacion = usuarioModificacion;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String getUrlApp()
/* 69:   */   {
/* 70:61 */     return this.urlApp;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void setUrlApp(String urlApp)
/* 74:   */   {
/* 75:65 */     this.urlApp = urlApp;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.EntidadBaseResponseDto
 * JD-Core Version:    0.7.0.1
 */