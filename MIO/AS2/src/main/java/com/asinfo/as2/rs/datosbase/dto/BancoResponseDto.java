/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class BancoResponseDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idBanco;
/* 11:   */   private Integer idOrganizacion;
/* 12:   */   private String codigo;
/* 13:   */   private String nombre;
/* 14:   */   private boolean activo;
/* 15:21 */   private int hashCode = 0;
/* 16:   */   
/* 17:   */   public int getHashCode()
/* 18:   */   {
/* 19:24 */     this.hashCode = hashCode();
/* 20:25 */     return this.hashCode;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int hashCode()
/* 24:   */   {
/* 25:30 */     int hash = 1;
/* 26:31 */     hash += hash * 17 + (this.idBanco + "").hashCode();
/* 27:32 */     hash += hash * 22 + (this.idOrganizacion + "").hashCode();
/* 28:33 */     hash += hash * 41 + (this.codigo == null ? 0 : this.codigo.hashCode());
/* 29:34 */     hash += hash * 36 + this.nombre.hashCode();
/* 30:35 */     hash += hash * 11 + (this.activo + "").hashCode();
/* 31:   */     
/* 32:37 */     return hash;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Integer getIdBanco()
/* 36:   */   {
/* 37:41 */     return this.idBanco;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setIdBanco(Integer idBanco)
/* 41:   */   {
/* 42:45 */     this.idBanco = idBanco;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getCodigo()
/* 46:   */   {
/* 47:49 */     return this.codigo;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setCodigo(String codigo)
/* 51:   */   {
/* 52:53 */     this.codigo = codigo;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getNombre()
/* 56:   */   {
/* 57:57 */     return this.nombre;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setNombre(String nombre)
/* 61:   */   {
/* 62:61 */     this.nombre = nombre;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean isActivo()
/* 66:   */   {
/* 67:65 */     return this.activo;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void setActivo(boolean activo)
/* 71:   */   {
/* 72:69 */     this.activo = activo;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public Integer getIdOrganizacion()
/* 76:   */   {
/* 77:73 */     return this.idOrganizacion;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 81:   */   {
/* 82:77 */     this.idOrganizacion = idOrganizacion;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setHashCode(int hashCode)
/* 86:   */   {
/* 87:81 */     this.hashCode = hashCode;
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.BancoResponseDto
 * JD-Core Version:    0.7.0.1
 */