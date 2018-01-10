/*  1:   */ package com.asinfo.as2.ws.respuesta;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.MensajeErrorEnum;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class MensajeWSEntity
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private String codigo;
/* 11:   */   private String mensaje;
/* 12:   */   private String detalle;
/* 13:   */   
/* 14:   */   public MensajeWSEntity() {}
/* 15:   */   
/* 16:   */   public MensajeWSEntity(String codigo, String mensaje, String detalle)
/* 17:   */   {
/* 18:37 */     this.codigo = codigo;
/* 19:38 */     this.mensaje = mensaje;
/* 20:39 */     this.detalle = detalle;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MensajeWSEntity(MensajeErrorEnum mensajeErrorEnum, String detalle)
/* 24:   */   {
/* 25:43 */     this.codigo = mensajeErrorEnum.getCodigo();
/* 26:44 */     this.mensaje = mensajeErrorEnum.getNombre();
/* 27:45 */     this.detalle = detalle;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getCodigo()
/* 31:   */   {
/* 32:51 */     return this.codigo;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setCodigo(String codigo)
/* 36:   */   {
/* 37:56 */     this.codigo = codigo;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getMensaje()
/* 41:   */   {
/* 42:61 */     return this.mensaje;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setMensaje(String mensaje)
/* 46:   */   {
/* 47:66 */     this.mensaje = mensaje;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String getDetalle()
/* 51:   */   {
/* 52:71 */     return this.detalle;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setDetalle(String detalle)
/* 56:   */   {
/* 57:76 */     this.detalle = detalle;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.respuesta.MensajeWSEntity
 * JD-Core Version:    0.7.0.1
 */