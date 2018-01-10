/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  9:   */ public class ListaCuentaOrganizacionRequestDto
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private Integer idOrganizacion;
/* 13:   */   private Integer idUsuario;
/* 14:17 */   private List<CuentaOrganizacionRequestDto> listaCuentaOrganizacionRequest = new ArrayList();
/* 15:   */   
/* 16:   */   public Integer getIdOrganizacion()
/* 17:   */   {
/* 18:20 */     return this.idOrganizacion;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 22:   */   {
/* 23:24 */     this.idOrganizacion = idOrganizacion;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<CuentaOrganizacionRequestDto> getListaCuentaOrganizacionRequest()
/* 27:   */   {
/* 28:28 */     return this.listaCuentaOrganizacionRequest;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setListaCuentaOrganizacionRequest(List<CuentaOrganizacionRequestDto> listaCuentaOrganizacionRequest)
/* 32:   */   {
/* 33:32 */     this.listaCuentaOrganizacionRequest = listaCuentaOrganizacionRequest;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Integer getIdUsuario()
/* 37:   */   {
/* 38:36 */     return this.idUsuario;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setIdUsuario(Integer idUsuario)
/* 42:   */   {
/* 43:40 */     this.idUsuario = idUsuario;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ListaCuentaOrganizacionRequestDto
 * JD-Core Version:    0.7.0.1
 */