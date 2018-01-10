/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  9:   */ public class FiltrosRequestDto
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private String urlApp;
/* 13:   */   private Integer idOrganizacion;
/* 14:   */   private Integer idSucursal;
/* 15:   */   private Integer startIndex;
/* 16:   */   private Integer pageSize;
/* 17:   */   private String sortField;
/* 18:   */   private Boolean sortAsc;
/* 19:21 */   private List<FiltroRequestDto> listaFiltro = new ArrayList();
/* 20:   */   
/* 21:   */   public Integer getIdOrganizacion()
/* 22:   */   {
/* 23:24 */     return this.idOrganizacion;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 27:   */   {
/* 28:28 */     this.idOrganizacion = idOrganizacion;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Integer getIdSucursal()
/* 32:   */   {
/* 33:32 */     return this.idSucursal;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setIdSucursal(Integer idSucursal)
/* 37:   */   {
/* 38:36 */     this.idSucursal = idSucursal;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Integer getStartIndex()
/* 42:   */   {
/* 43:40 */     return this.startIndex;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setStartIndex(Integer startIndex)
/* 47:   */   {
/* 48:44 */     this.startIndex = startIndex;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Integer getPageSize()
/* 52:   */   {
/* 53:48 */     return this.pageSize;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setPageSize(Integer pageSize)
/* 57:   */   {
/* 58:52 */     this.pageSize = pageSize;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String getSortField()
/* 62:   */   {
/* 63:56 */     return this.sortField;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setSortField(String sortField)
/* 67:   */   {
/* 68:60 */     this.sortField = sortField;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public Boolean getSortAsc()
/* 72:   */   {
/* 73:64 */     return this.sortAsc;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void setSortAsc(Boolean sortAsc)
/* 77:   */   {
/* 78:68 */     this.sortAsc = sortAsc;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public List<FiltroRequestDto> getListaFiltro()
/* 82:   */   {
/* 83:72 */     return this.listaFiltro;
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void setListaFiltro(List<FiltroRequestDto> listaFiltro)
/* 87:   */   {
/* 88:76 */     this.listaFiltro = listaFiltro;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public String getUrlApp()
/* 92:   */   {
/* 93:80 */     return this.urlApp;
/* 94:   */   }
/* 95:   */   
/* 96:   */   public void setUrlApp(String urlApp)
/* 97:   */   {
/* 98:84 */     this.urlApp = urlApp;
/* 99:   */   }
/* :0:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.FiltrosRequestDto
 * JD-Core Version:    0.7.0.1
 */