/*  1:   */ package com.asinfo.as2.dao.mantenimiento.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
/*  5:   */ import com.asinfo.as2.entities.mantenimiento.old.OrdenServicioMantenimiento;
/*  6:   */ import com.asinfo.as2.entities.mantenimiento.old.Procedimiento;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class OrdenServicioMantenimientoDao
/* 11:   */   extends AbstractDaoAS2<OrdenServicioMantenimiento>
/* 12:   */ {
/* 13:   */   public OrdenServicioMantenimientoDao()
/* 14:   */   {
/* 15:30 */     super(OrdenServicioMantenimiento.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public OrdenServicioMantenimiento cargarDetalle(int idOrdenServicioMantenimiento)
/* 19:   */   {
/* 20:40 */     OrdenServicioMantenimiento ordenServicioMantenimiento = (OrdenServicioMantenimiento)buscarPorId(Integer.valueOf(idOrdenServicioMantenimiento));
/* 21:41 */     ordenServicioMantenimiento.getArticuloServicio().getId();
/* 22:42 */     ordenServicioMantenimiento.getProcedimiento().getId();
/* 23:43 */     return ordenServicioMantenimiento;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.OrdenServicioMantenimientoDao
 * JD-Core Version:    0.7.0.1
 */