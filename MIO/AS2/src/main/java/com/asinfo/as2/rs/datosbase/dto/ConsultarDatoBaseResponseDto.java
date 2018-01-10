/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ 
/*  8:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  9:   */ public class ConsultarDatoBaseResponseDto
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private List<Map<String, Object>> listaDatoBase;
/* 13:   */   
/* 14:   */   public List<Map<String, Object>> getListaDatoBase()
/* 15:   */   {
/* 16:19 */     return this.listaDatoBase;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setListaDatoBase(List<Map<String, Object>> listaDatoBase)
/* 20:   */   {
/* 21:27 */     this.listaDatoBase = listaDatoBase;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ConsultarDatoBaseResponseDto
 * JD-Core Version:    0.7.0.1
 */