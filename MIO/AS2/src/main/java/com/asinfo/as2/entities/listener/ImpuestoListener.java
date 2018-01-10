/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Impuesto;
/*  4:   */ import com.asinfo.as2.entities.RangoImpuesto;
/*  5:   */ import javax.persistence.PrePersist;
/*  6:   */ import javax.persistence.PreUpdate;
/*  7:   */ 
/*  8:   */ public class ImpuestoListener
/*  9:   */ {
/* 10:   */   @PrePersist
/* 11:   */   @PreUpdate
/* 12:   */   void actualizarIndicadorClienteProveedor(Object entity)
/* 13:   */   {
/* 14:   */     Impuesto imp;
/* 15:18 */     if ((entity instanceof Impuesto))
/* 16:   */     {
/* 17:19 */       imp = (Impuesto)entity;
/* 18:20 */       imp.setDescripcion(imp.getDescripcion() + " ");
/* 19:21 */       if (imp.getFechaModificacion() != null) {
/* 20:22 */         for (RangoImpuesto rango : imp.getListaRangoImpuesto()) {
/* 21:25 */           imp.setFechaModificacion(rango.getFechaModificacion());
/* 22:   */         }
/* 23:   */       }
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.ImpuestoListener
 * JD-Core Version:    0.7.0.1
 */