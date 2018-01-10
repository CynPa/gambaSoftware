/*  1:   */ package com.asinfo.as2.web.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.util.SecurityUtil;
/*  4:   */ import com.asinfo.as2.util.exception.SecurityViolationException;
/*  5:   */ import java.io.Serializable;
/*  6:   */ import java.security.Permission;
/*  7:   */ import java.util.concurrent.Callable;
/*  8:   */ import javax.faces.application.Application;
/*  9:   */ import javax.faces.context.FacesContext;
/* 10:   */ import javax.faces.event.ExceptionQueuedEvent;
/* 11:   */ import javax.faces.event.ExceptionQueuedEventContext;
/* 12:   */ 
/* 13:   */ public abstract class SecurityController
/* 14:   */   implements Serializable
/* 15:   */ {
/* 16:   */   private String checkPermissions;
/* 17:   */   
/* 18:   */   public String getCheckPermissions()
/* 19:   */   {
/* 20:24 */     if ((getPermisssions() != null) && (!SecurityUtil.hasPermissions(getPermisssions()))) {
/* 21:25 */       publishException(new SecurityViolationException("You have no rights to access this page."));
/* 22:   */     }
/* 23:27 */     return "";
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected <T> T execute(Callable<T> c)
/* 27:   */     throws Exception
/* 28:   */   {
/* 29:   */     try
/* 30:   */     {
/* 31:32 */       return c.call();
/* 32:   */     }
/* 33:   */     catch (Exception e)
/* 34:   */     {
/* 35:34 */       publishException(e);
/* 36:35 */       throw e;
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected void publishException(Exception e)
/* 41:   */   {
/* 42:40 */     FacesContext ctx = FacesContext.getCurrentInstance();
/* 43:41 */     ExceptionQueuedEventContext eventContext = new ExceptionQueuedEventContext(ctx, e);
/* 44:42 */     ctx.getApplication().publishEvent(ctx, ExceptionQueuedEvent.class, eventContext);
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected abstract Permission[] getPermisssions();
/* 48:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.controller.SecurityController
 * JD-Core Version:    0.7.0.1
 */