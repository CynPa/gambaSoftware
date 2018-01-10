/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class EstadisticaVentaClienteRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private String urlApp;
/* 11:   */   private String fechaDesde;
/* 12:   */   private String fechaHasta;
/* 13:   */   private Integer idEmpresa;
/* 14:   */   private Integer idProducto;
/* 15:   */   private Boolean resumen;
/* 16:   */   
/* 17:   */   public String getUrlApp()
/* 18:   */   {
/* 19:19 */     return this.urlApp;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setUrlApp(String urlApp)
/* 23:   */   {
/* 24:23 */     this.urlApp = urlApp;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getFechaDesde()
/* 28:   */   {
/* 29:27 */     return this.fechaDesde;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setFechaDesde(String fechaDesde)
/* 33:   */   {
/* 34:31 */     this.fechaDesde = fechaDesde;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getFechaHasta()
/* 38:   */   {
/* 39:35 */     return this.fechaHasta;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setFechaHasta(String fechaHasta)
/* 43:   */   {
/* 44:39 */     this.fechaHasta = fechaHasta;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Integer getIdEmpresa()
/* 48:   */   {
/* 49:43 */     return this.idEmpresa;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setIdEmpresa(Integer idEmpresa)
/* 53:   */   {
/* 54:47 */     this.idEmpresa = idEmpresa;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Integer getIdProducto()
/* 58:   */   {
/* 59:51 */     return this.idProducto;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setIdProducto(Integer idProducto)
/* 63:   */   {
/* 64:55 */     this.idProducto = idProducto;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public Boolean getResumen()
/* 68:   */   {
/* 69:59 */     return this.resumen;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setResumen(Boolean resumen)
/* 73:   */   {
/* 74:63 */     this.resumen = resumen;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.EstadisticaVentaClienteRequestDto
 * JD-Core Version:    0.7.0.1
 */