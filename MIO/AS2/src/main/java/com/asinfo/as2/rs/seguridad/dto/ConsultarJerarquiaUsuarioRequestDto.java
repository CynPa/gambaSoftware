/*  1:   */ package com.asinfo.as2.rs.seguridad.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ConsultarJerarquiaUsuarioRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private int idUsuario;
/*  9:   */   private boolean indicadorAscendente;
/* 10:   */   private String nombreDocumentoBase;
/* 11:   */   private String urlApp;
/* 12:   */   
/* 13:   */   public int getIdUsuario()
/* 14:   */   {
/* 15:17 */     return this.idUsuario;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdUsuario(int idUsuario)
/* 19:   */   {
/* 20:21 */     this.idUsuario = idUsuario;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean isIndicadorAscendente()
/* 24:   */   {
/* 25:25 */     return this.indicadorAscendente;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIndicadorAscendente(boolean indicadorAscendente)
/* 29:   */   {
/* 30:29 */     this.indicadorAscendente = indicadorAscendente;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getNombreDocumentoBase()
/* 34:   */   {
/* 35:33 */     return this.nombreDocumentoBase;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setNombreDocumentoBase(String nombreDocumentoBase)
/* 39:   */   {
/* 40:37 */     this.nombreDocumentoBase = nombreDocumentoBase;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getUrlApp()
/* 44:   */   {
/* 45:41 */     return this.urlApp;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setUrlApp(String urlApp)
/* 49:   */   {
/* 50:45 */     this.urlApp = urlApp;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.ConsultarJerarquiaUsuarioRequestDto
 * JD-Core Version:    0.7.0.1
 */