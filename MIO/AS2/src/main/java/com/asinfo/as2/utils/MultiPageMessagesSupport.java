/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.faces.application.FacesMessage;
/*  8:   */ import javax.faces.context.ExternalContext;
/*  9:   */ import javax.faces.context.FacesContext;
/* 10:   */ import javax.faces.event.PhaseEvent;
/* 11:   */ import javax.faces.event.PhaseId;
/* 12:   */ import javax.faces.event.PhaseListener;
/* 13:   */ 
/* 14:   */ public class MultiPageMessagesSupport
/* 15:   */   implements PhaseListener
/* 16:   */ {
/* 17:   */   private static final long serialVersionUID = 1250469273857785274L;
/* 18:   */   private static final String sessionToken = "MULTI_PAGE_MESSAGES_SUPPORT";
/* 19:   */   
/* 20:   */   public PhaseId getPhaseId()
/* 21:   */   {
/* 22:29 */     return PhaseId.ANY_PHASE;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void beforePhase(PhaseEvent event)
/* 26:   */   {
/* 27:38 */     FacesContext facesContext = event.getFacesContext();
/* 28:39 */     saveMessages(facesContext);
/* 29:41 */     if ((PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())) && 
/* 30:42 */       (!facesContext.getResponseComplete())) {
/* 31:43 */       restoreMessages(facesContext);
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void afterPhase(PhaseEvent event)
/* 36:   */   {
/* 37:52 */     if (!PhaseId.RENDER_RESPONSE.equals(event.getPhaseId()))
/* 38:   */     {
/* 39:53 */       FacesContext facesContext = event.getFacesContext();
/* 40:54 */       saveMessages(facesContext);
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   private int saveMessages(FacesContext facesContext)
/* 45:   */   {
/* 46:60 */     List<FacesMessage> messages = new ArrayList();
/* 47:61 */     for (Iterator<FacesMessage> iter = facesContext.getMessages(null); iter.hasNext();)
/* 48:   */     {
/* 49:62 */       messages.add(iter.next());
/* 50:63 */       iter.remove();
/* 51:   */     }
/* 52:66 */     if (messages.size() == 0) {
/* 53:67 */       return 0;
/* 54:   */     }
/* 55:70 */     Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
/* 56:71 */     List<FacesMessage> existingMessages = (List)sessionMap.get("MULTI_PAGE_MESSAGES_SUPPORT");
/* 57:72 */     if (existingMessages != null) {
/* 58:73 */       existingMessages.addAll(messages);
/* 59:   */     } else {
/* 60:75 */       sessionMap.put("MULTI_PAGE_MESSAGES_SUPPORT", messages);
/* 61:   */     }
/* 62:77 */     return messages.size();
/* 63:   */   }
/* 64:   */   
/* 65:   */   private int restoreMessages(FacesContext facesContext)
/* 66:   */   {
/* 67:82 */     Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
/* 68:83 */     List<FacesMessage> messages = (List)sessionMap.remove("MULTI_PAGE_MESSAGES_SUPPORT");
/* 69:85 */     if (messages == null) {
/* 70:86 */       return 0;
/* 71:   */     }
/* 72:89 */     int restoredCount = messages.size();
/* 73:90 */     for (Object element : messages) {
/* 74:91 */       facesContext.addMessage(null, (FacesMessage)element);
/* 75:   */     }
/* 76:93 */     return restoredCount;
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.MultiPageMessagesSupport
 * JD-Core Version:    0.7.0.1
 */