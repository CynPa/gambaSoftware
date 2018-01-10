/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import javax.faces.event.PhaseEvent;
/*  5:   */ import javax.faces.event.PhaseId;
/*  6:   */ import javax.faces.event.PhaseListener;
/*  7:   */ 
/*  8:   */ public class MiFaseListener
/*  9:   */   implements PhaseListener
/* 10:   */ {
/* 11:   */   public PhaseId getPhaseId()
/* 12:   */   {
/* 13:26 */     return PhaseId.ANY_PHASE;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void beforePhase(PhaseEvent event)
/* 17:   */   {
/* 18:30 */     System.out.println("antes de la fase " + event.getPhaseId());
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void afterPhase(PhaseEvent event)
/* 22:   */   {
/* 23:34 */     System.out.println("despues de la fase " + event.getPhaseId());
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.MiFaseListener
 * JD-Core Version:    0.7.0.1
 */