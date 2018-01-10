/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CostoProducto;
/*  4:   */ import java.util.Calendar;
/*  5:   */ import javax.persistence.PrePersist;
/*  6:   */ import javax.persistence.PreUpdate;
/*  7:   */ 
/*  8:   */ public class CostoProductoListener
/*  9:   */ {
/* 10:   */   @PrePersist
/* 11:   */   @PreUpdate
/* 12:   */   void actualizarFecha(Object entity)
/* 13:   */   {
/* 14:35 */     if ((entity instanceof CostoProducto))
/* 15:   */     {
/* 16:36 */       CostoProducto costoProducto = (CostoProducto)entity;
/* 17:   */       
/* 18:38 */       Calendar cFecha = Calendar.getInstance();
/* 19:39 */       cFecha.setTime(costoProducto.getFecha());
/* 20:   */       
/* 21:41 */       costoProducto.setAnio(cFecha.get(1));
/* 22:42 */       costoProducto.setMes(cFecha.get(2) + 1);
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.CostoProductoListener
 * JD-Core Version:    0.7.0.1
 */