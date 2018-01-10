/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DocumentoVariableProceso;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ 
/*  6:   */ @Stateless
/*  7:   */ public class DocumentoVariableProcesoDao
/*  8:   */   extends AbstractDaoAS2<DocumentoVariableProceso>
/*  9:   */ {
/* 10:   */   public DocumentoVariableProcesoDao()
/* 11:   */   {
/* 12:31 */     super(DocumentoVariableProceso.class);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public DocumentoVariableProceso cargarDetalle(int idDocumentoVariableProceso)
/* 16:   */   {
/* 17:35 */     DocumentoVariableProceso documentoVariableProceso = (DocumentoVariableProceso)buscarPorId(Integer.valueOf(idDocumentoVariableProceso));
/* 18:   */     
/* 19:37 */     return documentoVariableProceso;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DocumentoVariableProcesoDao
 * JD-Core Version:    0.7.0.1
 */