/*  1:   */ package com.asinfo.as2.dao.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.FacturaCliente;
/*  5:   */ import com.asinfo.as2.entities.GuiaRemision;
/*  6:   */ import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
/*  7:   */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  8:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  9:   */ import java.util.HashMap;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ import javax.persistence.EntityManager;
/* 14:   */ import javax.persistence.Query;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class ComprobanteElectronicoPendienteSRIDao
/* 18:   */   extends AbstractDaoAS2<ComprobanteElectronicoPendienteSRI>
/* 19:   */ {
/* 20:   */   public ComprobanteElectronicoPendienteSRIDao()
/* 21:   */   {
/* 22:32 */     super(ComprobanteElectronicoPendienteSRI.class);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<ComprobanteElectronicoPendienteSRI> obtenerComprobantesPendientes(int idOrganizacion, Integer ambiente, List<DocumentoBase> listaDocumentoBase, boolean indicadorNoEnviado, boolean indicadorComprobarAutorizacion, int limit)
/* 26:   */   {
/* 27:38 */     StringBuilder sql = new StringBuilder();
/* 28:39 */     sql.append(" SELECT cep FROM ComprobanteElectronicoPendienteSRI cep ");
/* 29:40 */     sql.append(" WHERE cep.idOrganizacion = :idOrganizacion ");
/* 30:41 */     sql.append(" AND cep.indicadorRechazado IS NOT TRUE ");
/* 31:42 */     sql.append(" AND cep.indicadorNoEnviado = :indicadorNoEnviado ");
/* 32:43 */     sql.append(" AND cep.indicadorComprobarAutorizacion = :indicadorComprobarAutorizacion ");
/* 33:44 */     if (listaDocumentoBase != null) {
/* 34:45 */       sql.append(" AND cep.documentoBase IN :listaDocumentoBase ");
/* 35:   */     }
/* 36:47 */     if (ambiente != null) {
/* 37:48 */       sql.append(" AND cep.ambiente = :ambiente ");
/* 38:   */     }
/* 39:50 */     sql.append(" ORDER BY cep.cantidadIntentos ASC ");
/* 40:   */     
/* 41:52 */     Query query = this.em.createQuery(sql.toString());
/* 42:53 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 43:54 */     query.setParameter("indicadorNoEnviado", Boolean.valueOf(indicadorNoEnviado));
/* 44:55 */     query.setParameter("indicadorComprobarAutorizacion", Boolean.valueOf(indicadorComprobarAutorizacion));
/* 45:56 */     if (listaDocumentoBase != null) {
/* 46:57 */       query.setParameter("listaDocumentoBase", listaDocumentoBase);
/* 47:   */     }
/* 48:59 */     if (ambiente != null) {
/* 49:60 */       query.setParameter("ambiente", ambiente);
/* 50:   */     }
/* 51:62 */     query.setMaxResults(limit);
/* 52:   */     
/* 53:64 */     return query.getResultList();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void eliminarComprobanteElectronicoPendienteSRI(FacturaCliente facturaCliente, FacturaProveedorSRI facturaProveedorSRI, GuiaRemision guiaRemision)
/* 57:   */   {
/* 58:69 */     Map<String, String> filtros = new HashMap();
/* 59:70 */     if (facturaCliente != null) {
/* 60:71 */       filtros.put("idFacturaCliente", facturaCliente.getId() + "");
/* 61:72 */     } else if (facturaProveedorSRI != null) {
/* 62:73 */       filtros.put("idFacturaProveedorSRI", facturaProveedorSRI.getId() + "");
/* 63:74 */     } else if (guiaRemision != null) {
/* 64:75 */       filtros.put("idGuiaRemision", guiaRemision.getId() + "");
/* 65:   */     } else {
/* 66:77 */       return;
/* 67:   */     }
/* 68:79 */     List<ComprobanteElectronicoPendienteSRI> lista = obtenerListaCombo("idComprobanteElectronicoPendienteSRI", true, filtros);
/* 69:80 */     for (ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI : lista) {
/* 70:81 */       eliminar(comprobanteElectronicoPendienteSRI);
/* 71:   */     }
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao
 * JD-Core Version:    0.7.0.1
 */