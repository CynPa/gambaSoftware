/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class OrganizacionRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idOrganizacion;
/* 11:   */   private Integer hashCode;
/* 12:15 */   private Boolean revisado = Boolean.valueOf(false);
/* 13:   */   
/* 14:   */   public Integer getHashCode()
/* 15:   */   {
/* 16:18 */     return this.hashCode;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setHashCode(Integer hashCode)
/* 20:   */   {
/* 21:22 */     this.hashCode = hashCode;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Boolean getRevisado()
/* 25:   */   {
/* 26:26 */     return this.revisado;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setRevisado(Boolean revisado)
/* 30:   */   {
/* 31:30 */     this.revisado = revisado;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Integer getIdOrganizacion()
/* 35:   */   {
/* 36:37 */     return this.idOrganizacion;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 40:   */   {
/* 41:45 */     this.idOrganizacion = idOrganizacion;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.OrganizacionRequestDto
 * JD-Core Version:    0.7.0.1
 */