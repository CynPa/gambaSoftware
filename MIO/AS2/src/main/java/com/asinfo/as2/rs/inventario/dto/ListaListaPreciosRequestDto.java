/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  9:   */ public class ListaListaPreciosRequestDto
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private Integer idOrganizacion;
/* 13:15 */   private List<ListaPreciosRequestDto> listaListaPrecios = new ArrayList();
/* 14:   */   
/* 15:   */   public Integer getIdOrganizacion()
/* 16:   */   {
/* 17:18 */     return this.idOrganizacion;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 21:   */   {
/* 22:22 */     this.idOrganizacion = idOrganizacion;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<ListaPreciosRequestDto> getListaListaPrecios()
/* 26:   */   {
/* 27:26 */     return this.listaListaPrecios;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setListaListaPrecios(List<ListaPreciosRequestDto> listaListaPrecios)
/* 31:   */   {
/* 32:30 */     this.listaListaPrecios = listaListaPrecios;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ListaListaPreciosRequestDto
 * JD-Core Version:    0.7.0.1
 */