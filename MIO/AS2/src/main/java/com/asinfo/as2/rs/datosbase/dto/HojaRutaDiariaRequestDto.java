/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class HojaRutaDiariaRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private String fecha;
/* 11:   */   private int idUsuario;
/* 12:   */   
/* 13:   */   public String getFecha()
/* 14:   */   {
/* 15:16 */     return this.fecha;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setFecha(String fecha)
/* 19:   */   {
/* 20:20 */     this.fecha = fecha;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getIdUsuario()
/* 24:   */   {
/* 25:24 */     return this.idUsuario;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdUsuario(int idUsuario)
/* 29:   */   {
/* 30:28 */     this.idUsuario = idUsuario;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.HojaRutaDiariaRequestDto
 * JD-Core Version:    0.7.0.1
 */