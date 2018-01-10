/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class EnvioEmailRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private String urlApp;
/* 11:   */   private Integer idOrganizacion;
/* 12:   */   private String para;
/* 13:   */   private String tituloMensaje;
/* 14:   */   private String mensajeCuerpo;
/* 15:   */   private byte[] adjuntoByte;
/* 16:   */   private String nombreAdjuntoByte;
/* 17:   */   private String tipoAdjuntoByte;
/* 18:   */   
/* 19:   */   public String getUrlApp()
/* 20:   */   {
/* 21:21 */     return this.urlApp;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setUrlApp(String urlApp)
/* 25:   */   {
/* 26:25 */     this.urlApp = urlApp;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Integer getIdOrganizacion()
/* 30:   */   {
/* 31:29 */     return this.idOrganizacion;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 35:   */   {
/* 36:33 */     this.idOrganizacion = idOrganizacion;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getPara()
/* 40:   */   {
/* 41:37 */     return this.para;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setPara(String para)
/* 45:   */   {
/* 46:41 */     this.para = para;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String getTituloMensaje()
/* 50:   */   {
/* 51:45 */     return this.tituloMensaje;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void setTituloMensaje(String tituloMensaje)
/* 55:   */   {
/* 56:49 */     this.tituloMensaje = tituloMensaje;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String getMensajeCuerpo()
/* 60:   */   {
/* 61:53 */     return this.mensajeCuerpo;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void setMensajeCuerpo(String mensajeCuerpo)
/* 65:   */   {
/* 66:57 */     this.mensajeCuerpo = mensajeCuerpo;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public byte[] getAdjuntoByte()
/* 70:   */   {
/* 71:61 */     return this.adjuntoByte;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void setAdjuntoByte(byte[] adjuntoByte)
/* 75:   */   {
/* 76:65 */     this.adjuntoByte = adjuntoByte;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public String getNombreAdjuntoByte()
/* 80:   */   {
/* 81:69 */     return this.nombreAdjuntoByte;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public void setNombreAdjuntoByte(String nombreAdjuntoByte)
/* 85:   */   {
/* 86:73 */     this.nombreAdjuntoByte = nombreAdjuntoByte;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public String getTipoAdjuntoByte()
/* 90:   */   {
/* 91:77 */     return this.tipoAdjuntoByte;
/* 92:   */   }
/* 93:   */   
/* 94:   */   public void setTipoAdjuntoByte(String tipoAdjuntoByte)
/* 95:   */   {
/* 96:81 */     this.tipoAdjuntoByte = tipoAdjuntoByte;
/* 97:   */   }
/* 98:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.EnvioEmailRequestDto
 * JD-Core Version:    0.7.0.1
 */