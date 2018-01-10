/*  1:   */ package com.asinfo.as2.dao.mantenimiento;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.DocumentoEquipo;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ 
/*  7:   */ @Stateless
/*  8:   */ public class DocumentoEquipoDao
/*  9:   */   extends AbstractDaoAS2<DocumentoEquipo>
/* 10:   */ {
/* 11:   */   public DocumentoEquipoDao()
/* 12:   */   {
/* 13:19 */     super(DocumentoEquipo.class);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.DocumentoEquipoDao
 * JD-Core Version:    0.7.0.1
 */