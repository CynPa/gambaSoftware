/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Contacto;
/*  4:   */ import com.asinfo.as2.entities.Empresa;
/*  5:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  6:   */ import com.asinfo.as2.enumeraciones.ReporteEnvioMailsEnum;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ContactoDao
/* 14:   */   extends AbstractDaoAS2<Contacto>
/* 15:   */ {
/* 16:   */   public ContactoDao()
/* 17:   */   {
/* 18:35 */     super(Contacto.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public List<Contacto> leerPorEmpresa(Empresa empresa, DocumentoBase documentoBase)
/* 22:   */   {
/* 23:39 */     return leerPorEmpresa(empresa, documentoBase, null);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<Contacto> leerPorEmpresa(Empresa empresa, DocumentoBase documentoBase, ReporteEnvioMailsEnum indicadorEnvioMailsEnum)
/* 27:   */   {
/* 28:52 */     String indicador = "";
/* 29:53 */     if (documentoBase == null) {
/* 30:54 */       indicador = indicadorEnvioMailsEnum.getNombre();
/* 31:   */     } else {
/* 32:56 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[documentoBase.ordinal()])
/* 33:   */       {
/* 34:   */       case 1: 
/* 35:58 */         indicador = "indicadorNotificarFactura";
/* 36:59 */         break;
/* 37:   */       case 2: 
/* 38:61 */         indicador = "indicadorNotificarNotaCredito";
/* 39:62 */         break;
/* 40:   */       case 3: 
/* 41:64 */         indicador = "indicadorNotificarNotaCredito";
/* 42:65 */         break;
/* 43:   */       case 4: 
/* 44:67 */         indicador = "indicadorNotificarNotaDebito";
/* 45:68 */         break;
/* 46:   */       case 5: 
/* 47:70 */         indicador = "indicadorNotificarRetencion";
/* 48:71 */         break;
/* 49:   */       case 6: 
/* 50:73 */         indicador = "indicadorNotificarGuiaRemision";
/* 51:74 */         break;
/* 52:   */       case 7: 
/* 53:76 */         indicador = "indicadorNotificarPagoProveedor";
/* 54:77 */         break;
/* 55:   */       case 8: 
/* 56:79 */         indicador = "indicadorNotificarPedidoCliente";
/* 57:80 */         break;
/* 58:   */       case 9: 
/* 59:82 */         indicador = "indicadorNotificarPedidoProveedor";
/* 60:83 */         break;
/* 61:   */       }
/* 62:   */     }
/* 63:88 */     StringBuilder sql = new StringBuilder();
/* 64:89 */     sql.append(" SELECT c ");
/* 65:90 */     sql.append(" FROM Contacto c ");
/* 66:91 */     sql.append(" INNER JOIN FETCH c.tipoContacto tc ");
/* 67:92 */     sql.append(" WHERE c.empresa = :empresa ");
/* 68:93 */     sql.append(" AND tc." + indicador + " IS TRUE ");
/* 69:94 */     Query query = this.em.createQuery(sql.toString());
/* 70:95 */     query.setParameter("empresa", empresa);
/* 71:   */     
/* 72:97 */     return query.getResultList();
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ContactoDao
 * JD-Core Version:    0.7.0.1
 */