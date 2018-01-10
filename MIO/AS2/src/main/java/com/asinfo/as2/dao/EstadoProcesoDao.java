/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EstadoProceso;
/*  4:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  5:   */ import com.asinfo.as2.enumeraciones.Estado;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class EstadoProcesoDao
/* 13:   */   extends AbstractDaoAS2<EstadoProceso>
/* 14:   */ {
/* 15:   */   public EstadoProcesoDao()
/* 16:   */   {
/* 17:34 */     super(EstadoProceso.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<Estado> buscarPorDocumentoBase(DocumentoBase documentoBase)
/* 21:   */   {
/* 22:40 */     Query query = this.em.createQuery("SELECT e.estado FROM EstadoProceso e WHERE e.documentoBase=:documentoBase");
/* 23:41 */     query.setParameter("documentoBase", documentoBase);
/* 24:   */     
/* 25:43 */     return query.getResultList();
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EstadoProcesoDao
 * JD-Core Version:    0.7.0.1
 */