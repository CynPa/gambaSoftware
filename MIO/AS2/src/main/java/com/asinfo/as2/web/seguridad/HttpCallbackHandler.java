/*  1:   */ package com.asinfo.as2.web.seguridad;
/*  2:   */ 
/*  3:   */ import javax.security.auth.callback.Callback;
/*  4:   */ import javax.security.auth.callback.CallbackHandler;
/*  5:   */ import javax.security.auth.callback.NameCallback;
/*  6:   */ import javax.security.auth.callback.PasswordCallback;
/*  7:   */ 
/*  8:   */ public class HttpCallbackHandler
/*  9:   */   implements CallbackHandler
/* 10:   */ {
/* 11:   */   private String nombreUsurio;
/* 12:   */   private String clave;
/* 13:   */   
/* 14:   */   public HttpCallbackHandler(String nombreUsurio, String clave)
/* 15:   */   {
/* 16:19 */     this.nombreUsurio = nombreUsurio;
/* 17:20 */     this.clave = clave;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void handle(Callback[] callbacks)
/* 21:   */   {
/* 22:24 */     for (Callback callback : callbacks) {
/* 23:25 */       if ((callback instanceof NameCallback))
/* 24:   */       {
/* 25:26 */         NameCallback nameCB = (NameCallback)callback;
/* 26:27 */         nameCB.setName(this.nombreUsurio);
/* 27:   */       }
/* 28:28 */       else if ((callback instanceof PasswordCallback))
/* 29:   */       {
/* 30:29 */         PasswordCallback passwordCB = (PasswordCallback)callback;
/* 31:30 */         passwordCB.setPassword(this.clave.toCharArray());
/* 32:   */       }
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.seguridad.HttpCallbackHandler
 * JD-Core Version:    0.7.0.1
 */