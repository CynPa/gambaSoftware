/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Canal;
/*  4:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  5:   */ import com.asinfo.as2.entities.RangoComision;
/*  6:   */ import com.asinfo.as2.entities.RangoComisionCategoriaProducto;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class RangoComisionDao
/* 14:   */   extends AbstractDaoAS2<RangoComision>
/* 15:   */ {
/* 16:   */   public RangoComisionDao()
/* 17:   */   {
/* 18:33 */     super(RangoComision.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public RangoComision cargarDetalle(int idRangoComision)
/* 22:   */   {
/* 23:44 */     RangoComision rangoComision = (RangoComision)buscarPorId(Integer.valueOf(idRangoComision));
/* 24:   */     
/* 25:46 */     rangoComision.getListaRangoComisionCategoriaProducto().size();
/* 26:48 */     for (RangoComisionCategoriaProducto rccp : rangoComision.getListaRangoComisionCategoriaProducto())
/* 27:   */     {
/* 28:49 */       if (rccp.getCanal() != null) {
/* 29:50 */         rccp.getCanal().getId();
/* 30:   */       }
/* 31:53 */       if (rccp.getCategoriaProducto() != null) {
/* 32:54 */         rccp.getCategoriaProducto().getId();
/* 33:   */       }
/* 34:   */     }
/* 35:58 */     return rangoComision;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<RangoComision> getListaOrderByValorHasta()
/* 39:   */   {
/* 40:68 */     Query query = this.em.createQuery("SELECT t from RangoComision t  ORDER BY valorHasta ");
/* 41:   */     
/* 42:70 */     return query.getResultList();
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RangoComisionDao
 * JD-Core Version:    0.7.0.1
 */