/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ 
/*  6:   */ @Stateless
/*  7:   */ public class DocumentoContabilizacionDao
/*  8:   */   extends AbstractDaoAS2<DocumentoContabilizacion>
/*  9:   */ {
/* 10:   */   public DocumentoContabilizacionDao()
/* 11:   */   {
/* 12:31 */     super(DocumentoContabilizacion.class);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public DocumentoContabilizacion cargarDetalle(int idDocumentoContabilizacion)
/* 16:   */   {
/* 17:35 */     DocumentoContabilizacion documentoContabilizacion = (DocumentoContabilizacion)buscarPorId(Integer.valueOf(idDocumentoContabilizacion));
/* 18:   */     
/* 19:37 */     return documentoContabilizacion;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DocumentoContabilizacionDao
 * JD-Core Version:    0.7.0.1
 */