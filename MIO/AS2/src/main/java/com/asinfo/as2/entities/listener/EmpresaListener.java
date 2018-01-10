/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Empresa;
/*  4:   */ import javax.persistence.PrePersist;
/*  5:   */ import javax.persistence.PreUpdate;
/*  6:   */ 
/*  7:   */ public class EmpresaListener
/*  8:   */ {
/*  9:   */   @PrePersist
/* 10:   */   @PreUpdate
/* 11:   */   void actualizarIndicadorClienteProveedor(Object entity)
/* 12:   */   {
/* 13:33 */     if ((entity instanceof Empresa))
/* 14:   */     {
/* 15:34 */       Empresa empresa = (Empresa)entity;
/* 16:36 */       if ((empresa.isIndicadorCliente()) || (empresa.isIndicadorProveedor())) {
/* 17:37 */         empresa.setIndicadorClienteProveedor(true);
/* 18:   */       } else {
/* 19:39 */         empresa.setIndicadorClienteProveedor(false);
/* 20:   */       }
/* 21:41 */       empresa.setTextoBusqueda(empresa.getCodigo() + " " + empresa.getIdentificacion() + " " + empresa.getNombreFiscal() + " " + empresa
/* 22:42 */         .getNombreComercial());
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.EmpresaListener
 * JD-Core Version:    0.7.0.1
 */