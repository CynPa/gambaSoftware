/*  1:   */ package com.asinfo.as2.dao.mantenimiento.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.Tarea;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ 
/*  7:   */ @Stateless
/*  8:   */ public class TareaDao
/*  9:   */   extends AbstractDaoAS2<Tarea>
/* 10:   */ {
/* 11:   */   public TareaDao()
/* 12:   */   {
/* 13:41 */     super(Tarea.class);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.TareaDao
 * JD-Core Version:    0.7.0.1
 */