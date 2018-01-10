/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  9:   */ public class ListaOrganizacionRequestDto
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:13 */   private List<OrganizacionRequestDto> listaOrganizacionRequest = new ArrayList();
/* 13:   */   
/* 14:   */   public List<OrganizacionRequestDto> getListaOrganizacionRequest()
/* 15:   */   {
/* 16:19 */     return this.listaOrganizacionRequest;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setListaOrganizacionRequest(List<OrganizacionRequestDto> listaOrganizacionRequest)
/* 20:   */   {
/* 21:27 */     this.listaOrganizacionRequest = listaOrganizacionRequest;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ListaOrganizacionRequestDto
 * JD-Core Version:    0.7.0.1
 */