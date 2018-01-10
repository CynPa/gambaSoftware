/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EstadoFinanciero;
/*  4:   */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*  5:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class EstadoFinancieroDao
/* 14:   */   extends AbstractDaoAS2<EstadoFinanciero>
/* 15:   */ {
/* 16:   */   public EstadoFinancieroDao()
/* 17:   */   {
/* 18:36 */     super(EstadoFinanciero.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public EstadoFinanciero cargarDetalle(int idEstadoFinanciero)
/* 22:   */   {
/* 23:42 */     EstadoFinanciero estadoFinanciero = (EstadoFinanciero)buscarPorId(Integer.valueOf(idEstadoFinanciero));
/* 24:   */     
/* 25:44 */     estadoFinanciero.getListaDetalleEstadoFinanciero().size();
/* 26:45 */     return estadoFinanciero;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public EstadoFinanciero buscarPorFechaHasta(int idOrganizacion, Date fechaHasta, TipoEstadoFinanciero tipoEstadoFinanciero)
/* 30:   */   {
/* 31:53 */     Query query = this.em.createQuery("SELECT e FROM EstadoFinanciero e WHERE e.fechaHasta =:fechaHasta and e.tipoEstadoFinanciero =:tipoEstadoFinanciero and e.idOrganizacion = :idOrganizacion  ");
/* 32:54 */     query.setParameter("fechaHasta", fechaHasta).setParameter("tipoEstadoFinanciero", tipoEstadoFinanciero).setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 33:   */     
/* 34:56 */     int numeroRegistros = query.getResultList().size();
/* 35:   */     EstadoFinanciero estadoFinanciero;
/* 36:   */     EstadoFinanciero estadoFinanciero;
/* 37:57 */     if (numeroRegistros > 0)
/* 38:   */     {
/* 39:58 */       estadoFinanciero = (EstadoFinanciero)query.getSingleResult();
/* 40:   */     }
/* 41:   */     else
/* 42:   */     {
/* 43:60 */       estadoFinanciero = new EstadoFinanciero();
/* 44:61 */       estadoFinanciero.setFechaDesde(
/* 45:62 */         FuncionesUtiles.obtenerFechaInicial());
/* 46:63 */       estadoFinanciero.setFechaHasta(fechaHasta);
/* 47:64 */       estadoFinanciero.setTipoEstadoFinanciero(tipoEstadoFinanciero);
/* 48:   */     }
/* 49:66 */     return estadoFinanciero;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EstadoFinancieroDao
 * JD-Core Version:    0.7.0.1
 */