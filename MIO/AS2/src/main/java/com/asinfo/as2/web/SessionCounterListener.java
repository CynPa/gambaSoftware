/*  1:   */ package com.asinfo.as2.web;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import javax.servlet.ServletContext;
/*  6:   */ import javax.servlet.http.HttpSession;
/*  7:   */ import javax.servlet.http.HttpSessionEvent;
/*  8:   */ import javax.servlet.http.HttpSessionListener;
/*  9:   */ 
/* 10:   */ public class SessionCounterListener
/* 11:   */   implements HttpSessionListener
/* 12:   */ {
/* 13:26 */   private static int numeroSesionesActivas = 0;
/* 14:   */   
/* 15:   */   public void sessionCreated(HttpSessionEvent hse)
/* 16:   */   {
/* 17:33 */     HttpSession session = hse.getSession();
/* 18:   */     
/* 19:35 */     System.out.println("sessionCreated " + session.getId());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void sessionDestroyed(HttpSessionEvent hse)
/* 23:   */   {
/* 24:45 */     System.out.println("sessionDestroyed ");
/* 25:46 */     HttpSession session = hse.getSession();
/* 26:47 */     ServletContext context = session.getServletContext();
/* 27:48 */     Object objUsuarioEnSesion = context.getAttribute("com.asinfo.as2.usuario");
/* 28:50 */     if ((objUsuarioEnSesion != null) && ((objUsuarioEnSesion instanceof Usuario)))
/* 29:   */     {
/* 30:51 */       Usuario usuarioEnSesion = (Usuario)objUsuarioEnSesion;
/* 31:52 */       System.out.println("usuarioEnSesion " + usuarioEnSesion.getNombreUsuario());
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static int getNumeroSesionesActivas()
/* 36:   */   {
/* 37:61 */     return numeroSesionesActivas;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static void setNumeroSesionesActivas(int numeroSesionesActivas)
/* 41:   */   {
/* 42:69 */     numeroSesionesActivas = numeroSesionesActivas;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.SessionCounterListener
 * JD-Core Version:    0.7.0.1
 */