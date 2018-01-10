/*  1:   */ package com.asinfo.as2.compronteselectronicos.base;
/*  2:   */ 
/*  3:   */ import es.mityc.javasign.pkstore.IPassStoreKS;
/*  4:   */ import java.security.cert.X509Certificate;
/*  5:   */ 
/*  6:   */ public class PassStoreKS
/*  7:   */   implements IPassStoreKS
/*  8:   */ {
/*  9:   */   private transient String password;
/* 10:   */   
/* 11:   */   public PassStoreKS(String pass)
/* 12:   */   {
/* 13:28 */     this.password = new String(pass);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public char[] getPassword(X509Certificate certificate, String alias)
/* 17:   */   {
/* 18:39 */     return this.password.toCharArray();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.PassStoreKS
 * JD-Core Version:    0.7.0.1
 */