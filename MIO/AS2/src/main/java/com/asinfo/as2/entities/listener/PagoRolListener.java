/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Empleado;
/*  4:   */ import com.asinfo.as2.entities.PagoRol;
/*  5:   */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.persistence.PrePersist;
/*  8:   */ import javax.persistence.PreUpdate;
/*  9:   */ 
/* 10:   */ public class PagoRolListener
/* 11:   */ {
/* 12:   */   @PrePersist
/* 13:   */   @PreUpdate
/* 14:   */   void actualizarPagoRol(Object entity)
/* 15:   */   {
/* 16:32 */     if ((entity instanceof PagoRol))
/* 17:   */     {
/* 18:33 */       PagoRol pagoRol = (PagoRol)entity;
/* 19:35 */       if (pagoRol.isIndicadorFiniquito()) {
/* 20:36 */         pagoRol.setNombreEmpleadoFiniquito(((PagoRolEmpleado)pagoRol.getListaPagoRolEmpleado().get(0)).getEmpleado().getApellidos() + " " + 
/* 21:37 */           ((PagoRolEmpleado)pagoRol.getListaPagoRolEmpleado().get(0)).getEmpleado().getNombres());
/* 22:   */       }
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.PagoRolListener
 * JD-Core Version:    0.7.0.1
 */