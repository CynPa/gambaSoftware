/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class ListaPaginadaResponseDto
/*  9:   */   extends ProcesosResponseDto
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private List<? extends Object> lista;
/* 13:   */   private Integer rowCount;
/* 14:   */   
/* 15:   */   public List<? extends Object> getLista()
/* 16:   */   {
/* 17:16 */     return this.lista;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setLista(List<? extends Object> lista)
/* 21:   */   {
/* 22:20 */     this.lista = lista;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Integer getRowCount()
/* 26:   */   {
/* 27:24 */     return this.rowCount;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setRowCount(Integer rowCount)
/* 31:   */   {
/* 32:28 */     this.rowCount = rowCount;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ListaPaginadaResponseDto
 * JD-Core Version:    0.7.0.1
 */