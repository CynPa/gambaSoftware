/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ProcesosResponseDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private boolean succsess;
/* 11:   */   private String error;
/* 12:   */   private Object response;
/* 13:   */   
/* 14:   */   public boolean isSuccsess()
/* 15:   */   {
/* 16:18 */     return this.succsess;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setSuccsess(boolean succsess)
/* 20:   */   {
/* 21:22 */     this.succsess = succsess;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getError()
/* 25:   */   {
/* 26:26 */     return this.error;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setError(String error)
/* 30:   */   {
/* 31:30 */     this.error = error;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object getResponse()
/* 35:   */   {
/* 36:34 */     return this.response;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setResponse(Object response)
/* 40:   */   {
/* 41:38 */     this.response = response;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto
 * JD-Core Version:    0.7.0.1
 */