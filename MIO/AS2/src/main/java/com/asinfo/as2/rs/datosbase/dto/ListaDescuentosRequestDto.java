/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ListaDescuentosRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idDetalleListaDescuentos;
/* 11:   */   private Integer idCabeceraListaDescuentos;
/* 12:   */   private Integer hashCode;
/* 13:16 */   private Boolean revisado = Boolean.valueOf(false);
/* 14:   */   
/* 15:   */   public Integer getHashCode()
/* 16:   */   {
/* 17:19 */     return this.hashCode;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setHashCode(Integer hashCode)
/* 21:   */   {
/* 22:23 */     this.hashCode = hashCode;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Boolean getRevisado()
/* 26:   */   {
/* 27:27 */     return this.revisado;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setRevisado(Boolean revisado)
/* 31:   */   {
/* 32:31 */     this.revisado = revisado;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Integer getIdDetalleListaDescuentos()
/* 36:   */   {
/* 37:35 */     return this.idDetalleListaDescuentos;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setIdDetalleListaDescuentos(Integer idDetalleListaDescuentos)
/* 41:   */   {
/* 42:39 */     this.idDetalleListaDescuentos = idDetalleListaDescuentos;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Integer getIdCabeceraListaDescuentos()
/* 46:   */   {
/* 47:43 */     return this.idCabeceraListaDescuentos;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setIdCabeceraListaDescuentos(Integer idCabeceraListaDescuentos)
/* 51:   */   {
/* 52:47 */     this.idCabeceraListaDescuentos = idCabeceraListaDescuentos;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ListaDescuentosRequestDto
 * JD-Core Version:    0.7.0.1
 */