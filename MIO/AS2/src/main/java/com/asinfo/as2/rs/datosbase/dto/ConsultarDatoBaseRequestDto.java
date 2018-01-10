/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class ConsultarDatoBaseRequestDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private String entidad;
/* 12:   */   private String sortField;
/* 13:   */   private Boolean sortOrder;
/* 14:   */   private List<FiltroRequestDto> listaFiltro;
/* 15:   */   
/* 16:   */   public String getEntidad()
/* 17:   */   {
/* 18:24 */     return this.entidad;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setEntidad(String entidad)
/* 22:   */   {
/* 23:32 */     this.entidad = entidad;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getSortField()
/* 27:   */   {
/* 28:39 */     return this.sortField;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setSortField(String sortField)
/* 32:   */   {
/* 33:47 */     this.sortField = sortField;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Boolean getSortOrder()
/* 37:   */   {
/* 38:54 */     return this.sortOrder;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setSortOrder(Boolean sortOrder)
/* 42:   */   {
/* 43:62 */     this.sortOrder = sortOrder;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public List<FiltroRequestDto> getListaFiltro()
/* 47:   */   {
/* 48:69 */     return this.listaFiltro;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setListaFiltro(List<FiltroRequestDto> listaFiltro)
/* 52:   */   {
/* 53:77 */     this.listaFiltro = listaFiltro;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ConsultarDatoBaseRequestDto
 * JD-Core Version:    0.7.0.1
 */