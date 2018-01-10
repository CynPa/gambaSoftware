/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ConsultaSincronizacionRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idEntidad;
/* 11:   */   private Integer hashCode;
/* 12:15 */   private Boolean revisado = Boolean.valueOf(false);
/* 13:   */   
/* 14:   */   public Integer getIdEntidad()
/* 15:   */   {
/* 16:18 */     return this.idEntidad;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setIdEntidad(Integer idEntidad)
/* 20:   */   {
/* 21:22 */     this.idEntidad = idEntidad;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Integer getHashCode()
/* 25:   */   {
/* 26:26 */     return this.hashCode;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setHashCode(Integer hashCode)
/* 30:   */   {
/* 31:30 */     this.hashCode = hashCode;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Boolean getRevisado()
/* 35:   */   {
/* 36:34 */     return this.revisado;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setRevisado(Boolean revisado)
/* 40:   */   {
/* 41:38 */     this.revisado = revisado;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ConsultaSincronizacionRequestDto
 * JD-Core Version:    0.7.0.1
 */