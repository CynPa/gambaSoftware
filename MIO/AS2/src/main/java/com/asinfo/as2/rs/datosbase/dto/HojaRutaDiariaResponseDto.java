/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class HojaRutaDiariaResponseDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private int idClienteFinal;
/* 11:   */   private String fecha;
/* 12:   */   
/* 13:   */   public int getIdClienteFinal()
/* 14:   */   {
/* 15:16 */     return this.idClienteFinal;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdClienteFinal(int idClienteFinal)
/* 19:   */   {
/* 20:20 */     this.idClienteFinal = idClienteFinal;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getFecha()
/* 24:   */   {
/* 25:24 */     return this.fecha;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setFecha(String fecha)
/* 29:   */   {
/* 30:28 */     this.fecha = fecha;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.HojaRutaDiariaResponseDto
 * JD-Core Version:    0.7.0.1
 */