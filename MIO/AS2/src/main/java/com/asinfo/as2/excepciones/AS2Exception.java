/*   1:    */ package com.asinfo.as2.excepciones;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.text.MessageFormat;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.ResourceBundle;
/*   8:    */ 
/*   9:    */ public class AS2Exception
/*  10:    */   extends Exception
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = 1L;
/*  14:    */   protected String codigoExcepcion;
/*  15:    */   protected String mensaje;
/*  16: 33 */   protected List<String> mensajes = new ArrayList();
/*  17: 34 */   protected List<String> codigoMensajes = new ArrayList();
/*  18:    */   
/*  19:    */   public AS2Exception(String codigoExcepcion, String... messageArguments)
/*  20:    */   {
/*  21: 37 */     this(getMensajeAS2(codigoExcepcion, messageArguments));
/*  22: 38 */     this.codigoExcepcion = codigoExcepcion;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public AS2Exception() {}
/*  26:    */   
/*  27:    */   public AS2Exception(String mensaje)
/*  28:    */   {
/*  29: 45 */     super(mensaje);
/*  30: 46 */     this.mensaje = mensaje;
/*  31: 47 */     this.mensajes.add(mensaje);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static AS2Exception agregarMensaje(AS2Exception excepcion, String codigoExcepcion, String... messageArguments)
/*  35:    */   {
/*  36: 51 */     if (excepcion == null) {
/*  37: 52 */       excepcion = new AS2Exception(codigoExcepcion, messageArguments);
/*  38:    */     } else {
/*  39: 54 */       excepcion.getMensajes().add(getMensajeAS2(codigoExcepcion, messageArguments));
/*  40:    */     }
/*  41: 57 */     return excepcion;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void agregarMensaje(String mensaje)
/*  45:    */   {
/*  46: 61 */     this.mensajes.add(mensaje);
/*  47:    */   }
/*  48:    */   
/*  49:    */   private static String getMensajeAS2(String codigoExcepcion, String... messageArguments)
/*  50:    */   {
/*  51: 65 */     ResourceBundle messages = ResourceBundle.getBundle("ExceptionBundle");
/*  52: 66 */     String mensaje = messages.getString(codigoExcepcion);
/*  53: 68 */     if (messageArguments != null)
/*  54:    */     {
/*  55: 69 */       MessageFormat formatter = new MessageFormat("");
/*  56: 70 */       formatter.applyPattern(mensaje);
/*  57: 71 */       mensaje = formatter.format(messageArguments);
/*  58:    */     }
/*  59: 74 */     return mensaje;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getCodigoExcepcion()
/*  63:    */   {
/*  64: 83 */     return this.codigoExcepcion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setCodigoExcepcion(String codigoExcepcion)
/*  68:    */   {
/*  69: 93 */     this.codigoExcepcion = codigoExcepcion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getMensaje()
/*  73:    */   {
/*  74:102 */     return this.mensaje;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setMensaje(String mensaje)
/*  78:    */   {
/*  79:112 */     this.mensaje = mensaje;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<String> getMensajes()
/*  83:    */   {
/*  84:121 */     return this.mensajes;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setMensajes(List<String> mensajes)
/*  88:    */   {
/*  89:131 */     this.mensajes = mensajes;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<String> getCodigoMensajes()
/*  93:    */   {
/*  94:135 */     return this.codigoMensajes;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setCodigoMensajes(List<String> codigoMensajes)
/*  98:    */   {
/*  99:139 */     this.codigoMensajes = codigoMensajes;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.excepciones.AS2Exception
 * JD-Core Version:    0.7.0.1
 */