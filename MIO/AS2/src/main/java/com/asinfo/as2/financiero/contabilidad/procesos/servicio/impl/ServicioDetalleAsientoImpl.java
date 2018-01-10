/*  1:   */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.DetalleAsientoDao;
/*  4:   */ import com.asinfo.as2.entities.DetalleAsiento;
/*  5:   */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioDetalleAsiento;
/*  6:   */ import javax.ejb.EJB;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class ServicioDetalleAsientoImpl
/* 11:   */   implements ServicioDetalleAsiento
/* 12:   */ {
/* 13:   */   @EJB
/* 14:   */   private DetalleAsientoDao detalleAsientoDao;
/* 15:   */   
/* 16:   */   public void guardar(DetalleAsiento detalleAsiento)
/* 17:   */   {
/* 18:37 */     this.detalleAsientoDao.guardar(detalleAsiento);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void eliminar(DetalleAsiento detalleAsiento)
/* 22:   */   {
/* 23:49 */     this.detalleAsientoDao.eliminar(detalleAsiento);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public DetalleAsiento buscarPorId(Integer id)
/* 27:   */   {
/* 28:60 */     return (DetalleAsiento)this.detalleAsientoDao.buscarPorId(id);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioDetalleAsientoImpl
 * JD-Core Version:    0.7.0.1
 */