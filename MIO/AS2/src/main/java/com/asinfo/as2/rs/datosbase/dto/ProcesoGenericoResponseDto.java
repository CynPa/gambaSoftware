/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ProcesoGenericoResponseDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idAs2;
/* 11:   */   private String identificacionDispositivo;
/* 12:   */   private String estado;
/* 13:   */   private String numeroAs2;
/* 14:   */   
/* 15:   */   public Integer getIdAs2()
/* 16:   */   {
/* 17:18 */     return this.idAs2;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setIdAs2(Integer idAs2)
/* 21:   */   {
/* 22:22 */     this.idAs2 = idAs2;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getEstado()
/* 26:   */   {
/* 27:26 */     return this.estado;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setEstado(String estado)
/* 31:   */   {
/* 32:30 */     this.estado = estado;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getIdentificacionDispositivo()
/* 36:   */   {
/* 37:34 */     return this.identificacionDispositivo;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setIdentificacionDispositivo(String identificacionDispositivo)
/* 41:   */   {
/* 42:38 */     this.identificacionDispositivo = identificacionDispositivo;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getNumeroAs2()
/* 46:   */   {
/* 47:42 */     return this.numeroAs2;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setNumeroAs2(String numeroAs2)
/* 51:   */   {
/* 52:46 */     this.numeroAs2 = numeroAs2;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ProcesoGenericoResponseDto
 * JD-Core Version:    0.7.0.1
 */