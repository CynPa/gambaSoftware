/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class CargarDetalleRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private String urlApp;
/* 11:   */   private Integer id;
/* 12:   */   
/* 13:   */   public String getUrlApp()
/* 14:   */   {
/* 15:16 */     return this.urlApp;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setUrlApp(String urlApp)
/* 19:   */   {
/* 20:20 */     this.urlApp = urlApp;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Integer getId()
/* 24:   */   {
/* 25:24 */     return this.id;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setId(Integer id)
/* 29:   */   {
/* 30:28 */     this.id = id;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.CargarDetalleRequestDto
 * JD-Core Version:    0.7.0.1
 */