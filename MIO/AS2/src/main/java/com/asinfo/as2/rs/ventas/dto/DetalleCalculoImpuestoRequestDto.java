/*  1:   */ package com.asinfo.as2.rs.ventas.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.math.BigDecimal;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class DetalleCalculoImpuestoRequestDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private String urlApp;
/* 12:   */   private Integer idProducto;
/* 13:   */   private Integer idEmpresa;
/* 14:   */   private Boolean empresaExcentoImpuesto;
/* 15:   */   private Integer idSucursal;
/* 16:   */   private String fecha;
/* 17:   */   private BigDecimal cantidad;
/* 18:   */   private BigDecimal baseImponibleInicial;
/* 19:   */   
/* 20:   */   public String getUrlApp()
/* 21:   */   {
/* 22:30 */     return this.urlApp;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setUrlApp(String urlApp)
/* 26:   */   {
/* 27:34 */     this.urlApp = urlApp;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Integer getIdProducto()
/* 31:   */   {
/* 32:38 */     return this.idProducto;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setIdProducto(Integer idProducto)
/* 36:   */   {
/* 37:42 */     this.idProducto = idProducto;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Integer getIdEmpresa()
/* 41:   */   {
/* 42:46 */     return this.idEmpresa;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setIdEmpresa(Integer idEmpresa)
/* 46:   */   {
/* 47:50 */     this.idEmpresa = idEmpresa;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Boolean getEmpresaExcentoImpuesto()
/* 51:   */   {
/* 52:54 */     return this.empresaExcentoImpuesto;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setEmpresaExcentoImpuesto(Boolean empresaExcentoImpuesto)
/* 56:   */   {
/* 57:58 */     this.empresaExcentoImpuesto = empresaExcentoImpuesto;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String getFecha()
/* 61:   */   {
/* 62:62 */     return this.fecha;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setFecha(String fecha)
/* 66:   */   {
/* 67:66 */     this.fecha = fecha;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public Integer getIdSucursal()
/* 71:   */   {
/* 72:70 */     return this.idSucursal;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void setIdSucursal(Integer idSucursal)
/* 76:   */   {
/* 77:74 */     this.idSucursal = idSucursal;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public BigDecimal getCantidad()
/* 81:   */   {
/* 82:78 */     return this.cantidad;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setCantidad(BigDecimal cantidad)
/* 86:   */   {
/* 87:82 */     this.cantidad = cantidad;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public BigDecimal getBaseImponibleInicial()
/* 91:   */   {
/* 92:86 */     return this.baseImponibleInicial;
/* 93:   */   }
/* 94:   */   
/* 95:   */   public void setBaseImponibleInicial(BigDecimal baseImponibleInicial)
/* 96:   */   {
/* 97:90 */     this.baseImponibleInicial = baseImponibleInicial;
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.ventas.dto.DetalleCalculoImpuestoRequestDto
 * JD-Core Version:    0.7.0.1
 */