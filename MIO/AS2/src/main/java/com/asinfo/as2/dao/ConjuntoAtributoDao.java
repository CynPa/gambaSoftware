/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Atributo;
/*  4:   */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ConjuntoAtributoDao
/* 12:   */   extends AbstractDaoAS2<ConjuntoAtributo>
/* 13:   */ {
/* 14:   */   public ConjuntoAtributoDao()
/* 15:   */   {
/* 16:30 */     super(ConjuntoAtributo.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ConjuntoAtributo cargarDetalle(int id)
/* 20:   */   {
/* 21:36 */     ConjuntoAtributo conjuntoAtributo = (ConjuntoAtributo)buscarPorId(Integer.valueOf(id));
/* 22:   */     
/* 23:   */ 
/* 24:39 */     conjuntoAtributo.getListaAtributo().size();
/* 25:42 */     for (Atributo atributo : conjuntoAtributo.getListaAtributo()) {
/* 26:43 */       atributo.getListaValorAtributo().size();
/* 27:   */     }
/* 28:46 */     return conjuntoAtributo;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public List<ConjuntoAtributo> obtenerListaComboIndicadorProducto(int idOrganizacion)
/* 32:   */   {
/* 33:51 */     StringBuilder sql = new StringBuilder();
/* 34:52 */     sql.append(" SELECT ca ");
/* 35:53 */     sql.append(" FROM ConjuntoAtributo ca ");
/* 36:54 */     sql.append(" WHERE ca.activo = true ");
/* 37:55 */     sql.append(" AND ca.idOrganizacion = :idOrganizacion ");
/* 38:56 */     sql.append(" AND ca.indicadorProducto = true ");
/* 39:57 */     sql.append(" ORDER BY ca.nombre ");
/* 40:   */     
/* 41:59 */     Query query = this.em.createQuery(sql.toString());
/* 42:60 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 43:   */     
/* 44:62 */     return query.getResultList();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public List<ConjuntoAtributo> obtenerListaComboIndicadorCliente(int idOrganizacion)
/* 48:   */   {
/* 49:67 */     StringBuilder sql = new StringBuilder();
/* 50:68 */     sql.append(" SELECT ca ");
/* 51:69 */     sql.append(" FROM ConjuntoAtributo ca ");
/* 52:70 */     sql.append(" WHERE ca.activo = true ");
/* 53:71 */     sql.append(" AND ca.idOrganizacion = :idOrganizacion ");
/* 54:72 */     sql.append(" AND ca.indicadorCliente = true ");
/* 55:73 */     sql.append(" ORDER BY ca.nombre ");
/* 56:   */     
/* 57:75 */     Query query = this.em.createQuery(sql.toString());
/* 58:76 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 59:   */     
/* 60:78 */     return query.getResultList();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public List<ConjuntoAtributo> obtenerListaComboIndicadorProveedor(int idOrganizacion)
/* 64:   */   {
/* 65:83 */     StringBuilder sql = new StringBuilder();
/* 66:84 */     sql.append(" SELECT ca ");
/* 67:85 */     sql.append(" FROM ConjuntoAtributo ca ");
/* 68:86 */     sql.append(" WHERE ca.activo = true ");
/* 69:87 */     sql.append(" AND ca.idOrganizacion = :idOrganizacion ");
/* 70:88 */     sql.append(" AND ca.indicadorProveedor = true ");
/* 71:89 */     sql.append(" ORDER BY ca.nombre ");
/* 72:   */     
/* 73:91 */     Query query = this.em.createQuery(sql.toString());
/* 74:92 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 75:   */     
/* 76:94 */     return query.getResultList();
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ConjuntoAtributoDao
 * JD-Core Version:    0.7.0.1
 */