/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Empleado;
/*  4:   */ import com.asinfo.as2.entities.Empresa;
/*  5:   */ import javax.persistence.PrePersist;
/*  6:   */ import javax.persistence.PreUpdate;
/*  7:   */ 
/*  8:   */ public class EmpleadoListener
/*  9:   */ {
/* 10:   */   @PrePersist
/* 11:   */   @PreUpdate
/* 12:   */   void actualizarEmpleadoEmpresa(Object entity)
/* 13:   */   {
/* 14:32 */     if ((entity instanceof Empleado))
/* 15:   */     {
/* 16:33 */       Empleado empleado = (Empleado)entity;
/* 17:34 */       if (empleado.getEmpresa().getNombreComercial() == null) {
/* 18:35 */         empleado.getEmpresa().setNombreComercial(empleado.getNombres() + " " + empleado.getApellidos());
/* 19:   */       }
/* 20:37 */       if (empleado.getEmpresa().getNombreFiscal() == null) {
/* 21:38 */         empleado.getEmpresa().setNombreFiscal(empleado.getNombres() + " " + empleado.getApellidos());
/* 22:   */       }
/* 23:40 */       empleado.getEmpresa().setIndicadorEmpleado(true);
/* 24:41 */       empleado.setFiltro(empleado.getEmpresa().getIdentificacion() + " " + empleado.getNombres() + " " + empleado.getApellidos());
/* 25:   */     }
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.EmpleadoListener
 * JD-Core Version:    0.7.0.1
 */