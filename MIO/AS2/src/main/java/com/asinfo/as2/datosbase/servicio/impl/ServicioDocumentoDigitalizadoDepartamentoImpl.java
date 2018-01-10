/*  1:   */ package com.asinfo.as2.datosbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.DocumentoDigitalizadoDao;
/*  4:   */ import com.asinfo.as2.dao.DocumentoDigitalizadoDepartamentoDao;
/*  5:   */ import com.asinfo.as2.datosbase.servicio.ServicioDocumentoDigitalizadoDepartamento;
/*  6:   */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  7:   */ import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioDocumentoDigitalizadoDepartamentoImpl
/* 14:   */   implements ServicioDocumentoDigitalizadoDepartamento
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   DocumentoDigitalizadoDepartamentoDao documentoDigitalizadoDepartamentoDao;
/* 18:   */   @EJB
/* 19:   */   DocumentoDigitalizadoDao documentoDigitalizadoDao;
/* 20:   */   
/* 21:   */   public List<DocumentoDigitalizado> obtenerDocumentosDigitalizados(int idOrganizacion)
/* 22:   */   {
/* 23:23 */     List<DocumentoDigitalizado> listaDocumentoDigitalizado = this.documentoDigitalizadoDepartamentoDao.obtenerDocumentosDigitalizados(idOrganizacion);
/* 24:24 */     for (DocumentoDigitalizado documentoDigitalizado : listaDocumentoDigitalizado) {
/* 25:25 */       documentoDigitalizado = this.documentoDigitalizadoDao.cargarDetalles(documentoDigitalizado.getIdDocumentoDigitalizado());
/* 26:   */     }
/* 27:27 */     return this.documentoDigitalizadoDepartamentoDao.obtenerDocumentosDigitalizados(idOrganizacion);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void eliminar(DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento)
/* 31:   */   {
/* 32:32 */     this.documentoDigitalizadoDepartamentoDao.eliminar(documentoDigitalizadoDepartamento);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioDocumentoDigitalizadoDepartamentoImpl
 * JD-Core Version:    0.7.0.1
 */