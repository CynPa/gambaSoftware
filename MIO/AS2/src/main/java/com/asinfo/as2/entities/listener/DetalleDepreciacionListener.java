/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*  4:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  5:   */ import javax.persistence.PrePersist;
/*  6:   */ import javax.persistence.PreUpdate;
/*  7:   */ 
/*  8:   */ public class DetalleDepreciacionListener
/*  9:   */ {
/* 10:   */   @PrePersist
/* 11:   */   @PreUpdate
/* 12:   */   void actualizarFecha(Object entity)
/* 13:   */   {
/* 14:34 */     if ((entity instanceof DetalleDepreciacion))
/* 15:   */     {
/* 16:35 */       DetalleDepreciacion detalleDepreciacion = (DetalleDepreciacion)entity;
/* 17:36 */       detalleDepreciacion
/* 18:37 */         .setFecha(FuncionesUtiles.getFechaFinMes(detalleDepreciacion
/* 19:38 */         .getAnio(), detalleDepreciacion
/* 20:39 */         .getMes()));
/* 21:   */     }
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.DetalleDepreciacionListener
 * JD-Core Version:    0.7.0.1
 */