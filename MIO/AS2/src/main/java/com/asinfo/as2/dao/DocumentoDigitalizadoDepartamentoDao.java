/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  4:   */ import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class DocumentoDigitalizadoDepartamentoDao
/* 12:   */   extends AbstractDaoAS2<DocumentoDigitalizadoDepartamento>
/* 13:   */ {
/* 14:   */   public DocumentoDigitalizadoDepartamentoDao()
/* 15:   */   {
/* 16:16 */     super(DocumentoDigitalizadoDepartamento.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public List<DocumentoDigitalizado> obtenerDocumentosDigitalizados(int idOrganizacion)
/* 20:   */   {
/* 21:21 */     StringBuilder sql = new StringBuilder();
/* 22:22 */     sql.append(" SELECT dd FROM DocumentoDigitalizado dd ");
/* 23:23 */     sql.append(" WHERE dd.idOrganizacion = :idOrganizacion ");
/* 24:24 */     sql.append(" AND dd.activo = true");
/* 25:25 */     sql.append(" ORDER BY dd.categoriaDocumentoDigitalizado.nombre");
/* 26:   */     
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:35 */     Query query = this.em.createQuery(sql.toString());
/* 36:   */     
/* 37:37 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 38:38 */     return query.getResultList();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public List<DocumentoDigitalizadoDepartamento> cargarDocumentosPorDepartamento(int idDepartamento, int idEmpleado)
/* 42:   */   {
/* 43:43 */     StringBuilder sql = new StringBuilder();
/* 44:44 */     sql.append(" SELECT ddd FROM DocumentoDigitalizadoDepartamento ddd ");
/* 45:45 */     sql.append(" WHERE ddd.departamento.idDepartamento = :idDepartamento ");
/* 46:46 */     sql.append(" AND ddd.documentoDigitalizado.activo IS TRUE ");
/* 47:47 */     sql.append(" AND ddd.documentoDigitalizado.categoriaDocumentoDigitalizado.activo IS TRUE ");
/* 48:48 */     sql.append(" AND ddd.idDocumentoDigitalizadoDepartamento NOT IN  ");
/* 49:49 */     sql.append(" \t( ");
/* 50:50 */     sql.append(" \t\tSELECT ddd2.idDocumentoDigitalizadoDepartamento  ");
/* 51:51 */     sql.append(" \t\tFROM DetalleDocumentoDigitalizado dtdd  ");
/* 52:52 */     sql.append(" \t\tinner join dtdd.documentoDigitalizadoDepartamento ddd2  ");
/* 53:53 */     sql.append(" \t\tinner join dtdd.empleado ep  ");
/* 54:54 */     sql.append(" \t\tWHERE ep.idEmpleado = :idEmpleado ");
/* 55:55 */     sql.append(" \t)");
/* 56:56 */     sql.append(" ORDER BY ddd.documentoDigitalizado.categoriaDocumentoDigitalizado.nombre");
/* 57:   */     
/* 58:58 */     Query query = this.em.createQuery(sql.toString());
/* 59:59 */     query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/* 60:60 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 61:61 */     return query.getResultList();
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DocumentoDigitalizadoDepartamentoDao
 * JD-Core Version:    0.7.0.1
 */