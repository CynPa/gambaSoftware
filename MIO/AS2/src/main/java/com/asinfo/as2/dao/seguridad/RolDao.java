/*  1:   */ package com.asinfo.as2.dao.seguridad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  5:   */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  6:   */ import java.util.Collection;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class RolDao
/* 12:   */   extends AbstractDaoAS2<EntidadRol>
/* 13:   */ {
/* 14:   */   public RolDao()
/* 15:   */   {
/* 16:33 */     super(EntidadRol.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public EntidadRol cargarDetalle(int id)
/* 20:   */   {
/* 21:41 */     EntidadRol entidadRol = (EntidadRol)buscarPorId(Integer.valueOf(id));
/* 22:   */     
/* 23:   */ 
/* 24:44 */     entidadRol.getListaPermiso().size();
/* 25:47 */     for (EntidadPermiso permiso : entidadRol.getListaPermiso()) {
/* 26:48 */       permiso.getListaAccion().size();
/* 27:   */     }
/* 28:51 */     return entidadRol;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.seguridad.RolDao
 * JD-Core Version:    0.7.0.1
 */