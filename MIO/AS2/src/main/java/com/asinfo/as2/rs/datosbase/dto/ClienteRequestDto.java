/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ClienteRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idUsuario;
/* 11:   */   private Integer idCliente;
/* 12:   */   private Integer idOrganizacion;
/* 13:   */   private Integer idSucursal;
/* 14:   */   private Integer hashCode;
/* 15:21 */   private Boolean revisado = Boolean.valueOf(false);
/* 16:   */   
/* 17:   */   public Integer getIdUsuario()
/* 18:   */   {
/* 19:24 */     return this.idUsuario;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setIdUsuario(Integer idUsuario)
/* 23:   */   {
/* 24:28 */     this.idUsuario = idUsuario;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Integer getIdCliente()
/* 28:   */   {
/* 29:32 */     return this.idCliente;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setIdCliente(Integer idCliente)
/* 33:   */   {
/* 34:36 */     this.idCliente = idCliente;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Integer getHashCode()
/* 38:   */   {
/* 39:40 */     return this.hashCode;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setHashCode(Integer hashCode)
/* 43:   */   {
/* 44:44 */     this.hashCode = hashCode;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Boolean getRevisado()
/* 48:   */   {
/* 49:48 */     return this.revisado;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setRevisado(Boolean revisado)
/* 53:   */   {
/* 54:52 */     this.revisado = revisado;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Integer getIdOrganizacion()
/* 58:   */   {
/* 59:56 */     return this.idOrganizacion;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 63:   */   {
/* 64:60 */     this.idOrganizacion = idOrganizacion;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public Integer getIdSucursal()
/* 68:   */   {
/* 69:64 */     return this.idSucursal;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setIdSucursal(Integer idSucursal)
/* 73:   */   {
/* 74:68 */     this.idSucursal = idSucursal;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ClienteRequestDto
 * JD-Core Version:    0.7.0.1
 */