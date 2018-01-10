/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.math.BigDecimal;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class ValidarDescuentoRequestDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private String urlApp;
/* 12:   */   private Integer idListaDescuentos;
/* 13:   */   private Integer idProducto;
/* 14:   */   private BigDecimal porcentajeDescuentoLinea;
/* 15:   */   
/* 16:   */   public String getUrlApp()
/* 17:   */   {
/* 18:18 */     return this.urlApp;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setUrlApp(String urlApp)
/* 22:   */   {
/* 23:22 */     this.urlApp = urlApp;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Integer getIdListaDescuentos()
/* 27:   */   {
/* 28:26 */     return this.idListaDescuentos;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setIdListaDescuentos(Integer idListaDescuentos)
/* 32:   */   {
/* 33:30 */     this.idListaDescuentos = idListaDescuentos;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Integer getIdProducto()
/* 37:   */   {
/* 38:34 */     return this.idProducto;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setIdProducto(Integer idProducto)
/* 42:   */   {
/* 43:38 */     this.idProducto = idProducto;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public BigDecimal getPorcentajeDescuentoLinea()
/* 47:   */   {
/* 48:42 */     return this.porcentajeDescuentoLinea;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setPorcentajeDescuentoLinea(BigDecimal porcentajeDescuentoLinea)
/* 52:   */   {
/* 53:46 */     this.porcentajeDescuentoLinea = porcentajeDescuentoLinea;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ValidarDescuentoRequestDto
 * JD-Core Version:    0.7.0.1
 */