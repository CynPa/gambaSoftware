/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ActivoFijo;
/*  4:   */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*  5:   */ import javax.persistence.PrePersist;
/*  6:   */ 
/*  7:   */ public class CustodioActivoFijoListener
/*  8:   */ {
/*  9:   */   @PrePersist
/* 10:   */   void actualizarUbicacionActivo(Object entity)
/* 11:   */   {
/* 12:31 */     if ((entity instanceof CustodioActivoFijo))
/* 13:   */     {
/* 14:32 */       CustodioActivoFijo custodioActivoFijo = (CustodioActivoFijo)entity;
/* 15:33 */       if (custodioActivoFijo.isActivo()) {
/* 16:34 */         custodioActivoFijo.getActivoFijo().setCustodioActivoFijo(custodioActivoFijo);
/* 17:   */       }
/* 18:   */     }
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.CustodioActivoFijoListener
 * JD-Core Version:    0.7.0.1
 */