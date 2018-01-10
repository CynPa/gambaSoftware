/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ConsultarPrecioRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private String urlApp;
/* 11:   */   private Integer idListaPrecios;
/* 12:   */   private Integer idProducto;
/* 13:   */   private String fecha;
/* 14:   */   
/* 15:   */   public String getUrlApp()
/* 16:   */   {
/* 17:17 */     return this.urlApp;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setUrlApp(String urlApp)
/* 21:   */   {
/* 22:21 */     this.urlApp = urlApp;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Integer getIdListaPrecios()
/* 26:   */   {
/* 27:25 */     return this.idListaPrecios;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setIdListaPrecios(Integer idListaPrecios)
/* 31:   */   {
/* 32:29 */     this.idListaPrecios = idListaPrecios;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Integer getIdProducto()
/* 36:   */   {
/* 37:33 */     return this.idProducto;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setIdProducto(Integer idProducto)
/* 41:   */   {
/* 42:37 */     this.idProducto = idProducto;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getFecha()
/* 46:   */   {
/* 47:41 */     return this.fecha;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setFecha(String fecha)
/* 51:   */   {
/* 52:45 */     this.fecha = fecha;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ConsultarPrecioRequestDto
 * JD-Core Version:    0.7.0.1
 */