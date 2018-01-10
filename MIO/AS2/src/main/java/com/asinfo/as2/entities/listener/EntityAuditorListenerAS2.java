/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  5:   */ import com.asinfo.as2.util.AppUtil;
/*  6:   */ import java.util.Date;
/*  7:   */ import javax.persistence.PrePersist;
/*  8:   */ import javax.persistence.PreUpdate;
/*  9:   */ 
/* 10:   */ public class EntityAuditorListenerAS2
/* 11:   */ {
/* 12:   */   @PrePersist
/* 13:   */   void auditarCreacion(Object entity)
/* 14:   */   {
/* 15:35 */     if ((entity instanceof EntidadBase))
/* 16:   */     {
/* 17:36 */       EntidadBase entidadBase = (EntidadBase)entity;
/* 18:37 */       if (entidadBase.getFechaCreacion() == null) {
/* 19:38 */         entidadBase.setFechaCreacion(new Date());
/* 20:   */       }
/* 21:40 */       String usuarioCreacion = entidadBase.getUsuarioCreacion();
/* 22:41 */       entidadBase.setUsuarioCreacion(usuarioCreacion == null ? AppUtil.getUsuarioEnSesion().getNombreUsuario() : usuarioCreacion);
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   @PreUpdate
/* 27:   */   void auditarActualizacion(Object entity)
/* 28:   */   {
/* 29:52 */     if ((entity instanceof EntidadBase))
/* 30:   */     {
/* 31:53 */       EntidadBase entidadBase = (EntidadBase)entity;
/* 32:54 */       entidadBase.setFechaModificacion(new Date());
/* 33:55 */       entidadBase.setUsuarioModificacion(AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.EntityAuditorListenerAS2
 * JD-Core Version:    0.7.0.1
 */