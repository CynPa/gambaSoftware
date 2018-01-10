/*  1:   */ package com.asinfo.as2.ws.respuesta;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class RespuestaWSEntity
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9:   */   private String estado;
/* 10:   */   private String tipoDocumento;
/* 11:   */   private String referenciaDocumento;
/* 12:   */   private String detalle;
/* 13:   */   private MensajeWSEntity[] listaMensaje;
/* 14:   */   
/* 15:   */   public String getEstado()
/* 16:   */   {
/* 17:40 */     return this.estado;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setEstado(String estado)
/* 21:   */   {
/* 22:46 */     this.estado = estado;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getTipoDocumento()
/* 26:   */   {
/* 27:52 */     return this.tipoDocumento;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setTipoDocumento(String tipoDocumento)
/* 31:   */   {
/* 32:58 */     this.tipoDocumento = tipoDocumento;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getReferenciaDocumento()
/* 36:   */   {
/* 37:64 */     return this.referenciaDocumento;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setReferenciaDocumento(String referenciaDocumento)
/* 41:   */   {
/* 42:70 */     this.referenciaDocumento = referenciaDocumento;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public MensajeWSEntity[] getListaMensaje()
/* 46:   */   {
/* 47:76 */     return this.listaMensaje;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setListaMensaje(MensajeWSEntity[] listaMensaje)
/* 51:   */   {
/* 52:82 */     this.listaMensaje = listaMensaje;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getDetalle()
/* 56:   */   {
/* 57:88 */     return this.detalle;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setDetalle(String detalle)
/* 61:   */   {
/* 62:94 */     this.detalle = detalle;
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.respuesta.RespuestaWSEntity
 * JD-Core Version:    0.7.0.1
 */