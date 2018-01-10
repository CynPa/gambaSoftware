/*  1:   */ package com.asinfo.as2.dao.mantenimiento.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
/*  5:   */ import com.asinfo.as2.entities.mantenimiento.old.CategoriaArticuloServicio;
/*  6:   */ import com.asinfo.as2.entities.mantenimiento.old.GrupoArticuloServicio;
/*  7:   */ import com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio;
/*  8:   */ import com.asinfo.as2.entities.mantenimiento.old.TipoCicloOperacion;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.Query;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class HistoricoArticuloServicioDao
/* 15:   */   extends AbstractDaoAS2<HistoricoArticuloServicio>
/* 16:   */ {
/* 17:   */   public HistoricoArticuloServicioDao()
/* 18:   */   {
/* 19:32 */     super(HistoricoArticuloServicio.class);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public HistoricoArticuloServicio obtenerHistoricoArticuloServicioActual(int idArticuloServicio)
/* 23:   */   {
/* 24:36 */     String sql = " SELECT h  FROM HistoricoArticuloServicio h JOIN FETCH h.articuloServicioHijo ah JOIN FETCH h.articuloServicioPadre ap  WHERE ah.idArticuloServicio = :idArticuloServicio AND h.fecha_hasta = NULL";
/* 25:   */     
/* 26:   */ 
/* 27:   */ 
/* 28:40 */     Query query = this.em.createQuery(sql);
/* 29:41 */     query.setParameter("idArticuloServicio", Integer.valueOf(idArticuloServicio));
/* 30:   */     
/* 31:43 */     return (HistoricoArticuloServicio)query.getSingleResult();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public HistoricoArticuloServicio cargarDetalle(int idArticuloServicio)
/* 35:   */   {
/* 36:52 */     HistoricoArticuloServicio historicoArticuloServicio = (HistoricoArticuloServicio)buscarPorId(Integer.valueOf(idArticuloServicio));
/* 37:53 */     historicoArticuloServicio.getArticuloServicioPadre().getId();
/* 38:54 */     historicoArticuloServicio.getArticuloServicioHijo().getId();
/* 39:55 */     historicoArticuloServicio.getArticuloServicioHijo().getGrupoArticuloServicio().getId();
/* 40:56 */     historicoArticuloServicio.getArticuloServicioHijo().getTipoCicloOperacion().getId();
/* 41:57 */     historicoArticuloServicio.getArticuloServicioHijo().getCategoriaArticuloServicio().getId();
/* 42:58 */     return historicoArticuloServicio;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.HistoricoArticuloServicioDao
 * JD-Core Version:    0.7.0.1
 */