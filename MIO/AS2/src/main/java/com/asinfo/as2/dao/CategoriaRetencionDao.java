/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaRetencion;
/*  4:   */ import com.asinfo.as2.entities.DetalleCategoriaRetencion;
/*  5:   */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class CategoriaRetencionDao
/* 11:   */   extends AbstractDaoAS2<CategoriaRetencion>
/* 12:   */ {
/* 13:   */   public CategoriaRetencionDao()
/* 14:   */   {
/* 15:28 */     super(CategoriaRetencion.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CategoriaRetencion cargarDetalle(int idCategoriaRetencion)
/* 19:   */   {
/* 20:38 */     CategoriaRetencion categoriaRetencion = (CategoriaRetencion)buscarPorId(Integer.valueOf(idCategoriaRetencion));
/* 21:39 */     categoriaRetencion.getListaDetalleCategoriaRetencion().size();
/* 22:40 */     for (DetalleCategoriaRetencion detalleCategoriaRetencion : categoriaRetencion.getListaDetalleCategoriaRetencion()) {
/* 23:41 */       detalleCategoriaRetencion.getConceptoRetencionSRI().getId();
/* 24:   */     }
/* 25:43 */     return categoriaRetencion;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CategoriaRetencionDao
 * JD-Core Version:    0.7.0.1
 */