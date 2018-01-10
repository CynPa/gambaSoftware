/*  1:   */ package com.asinfo.as2.dao.mantenimiento.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.Actividad;
/*  5:   */ import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
/*  6:   */ import com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion;
/*  7:   */ import com.asinfo.as2.entities.mantenimiento.old.Procedimiento;
/*  8:   */ import com.asinfo.as2.entities.mantenimiento.old.ProcedimientoActividad;
/*  9:   */ import com.asinfo.as2.entities.mantenimiento.old.Tarea;
/* 10:   */ import com.asinfo.as2.entities.mantenimiento.old.TarifaSalarial;
/* 11:   */ import com.asinfo.as2.entities.mantenimiento.old.TipoServicioMantenimiento;
/* 12:   */ import java.util.List;
/* 13:   */ import javax.ejb.Stateless;
/* 14:   */ 
/* 15:   */ @Stateless
/* 16:   */ public class ProcedimientoDao
/* 17:   */   extends AbstractDaoAS2<Procedimiento>
/* 18:   */ {
/* 19:   */   public ProcedimientoDao()
/* 20:   */   {
/* 21:33 */     super(Procedimiento.class);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Procedimiento cargarDetalle(int idProcedimiento)
/* 25:   */   {
/* 26:44 */     Procedimiento procedimiento = (Procedimiento)buscarPorId(Integer.valueOf(idProcedimiento));
/* 27:45 */     procedimiento.getTipoServicioMantenimiento().getId();
/* 28:46 */     if (procedimiento.getListaProcedimientoActividad().size() > 0) {
/* 29:47 */       for (ProcedimientoActividad procedimientoActividad : procedimiento.getListaProcedimientoActividad())
/* 30:   */       {
/* 31:48 */         procedimientoActividad.getActividad().getIdActividad();
/* 32:49 */         procedimientoActividad.getActividad().getListaVerificacion().getId();
/* 33:50 */         procedimientoActividad.getActividad().getCriticidad().getId();
/* 34:51 */         if (procedimientoActividad.getActividad().getListaTarea().size() > 0) {
/* 35:52 */           for (Tarea tarea : procedimientoActividad.getActividad().getListaTarea())
/* 36:   */           {
/* 37:53 */             tarea.getId();
/* 38:54 */             tarea.getTarifaSalarial().getId();
/* 39:   */           }
/* 40:   */         }
/* 41:   */       }
/* 42:   */     }
/* 43:59 */     return procedimiento;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.ProcedimientoDao
 * JD-Core Version:    0.7.0.1
 */