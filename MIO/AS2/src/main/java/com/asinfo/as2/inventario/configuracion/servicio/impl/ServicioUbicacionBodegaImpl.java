/*  1:   */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GenericoDao;
/*  4:   */ import com.asinfo.as2.entities.UbicacionBodega;
/*  5:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUbicacionBodega;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ 
/*  8:   */ @Stateless
/*  9:   */ public class ServicioUbicacionBodegaImpl
/* 10:   */   implements ServicioUbicacionBodega
/* 11:   */ {
/* 12:   */   private GenericoDao<UbicacionBodega> ubicacionBodegaDao;
/* 13:   */   
/* 14:   */   public void guardar(UbicacionBodega ubicacionBodega)
/* 15:   */   {
/* 16:37 */     this.ubicacionBodegaDao.guardar(ubicacionBodega);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void eliminar(UbicacionBodega ubicacionBodega)
/* 20:   */   {
/* 21:48 */     this.ubicacionBodegaDao.eliminar(ubicacionBodega);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public UbicacionBodega buscarPorId(Integer id)
/* 25:   */   {
/* 26:59 */     return (UbicacionBodega)this.ubicacionBodegaDao.buscarPorId(id);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioUbicacionBodegaImpl
 * JD-Core Version:    0.7.0.1
 */