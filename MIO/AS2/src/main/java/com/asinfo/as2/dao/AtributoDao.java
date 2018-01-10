/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Atributo;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.ejb.Stateless;
/*   7:    */ import javax.persistence.EntityManager;
/*   8:    */ import javax.persistence.Query;
/*   9:    */ 
/*  10:    */ @Stateless
/*  11:    */ public class AtributoDao
/*  12:    */   extends AbstractDaoAS2<Atributo>
/*  13:    */ {
/*  14:    */   public AtributoDao()
/*  15:    */   {
/*  16: 30 */     super(Atributo.class);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Atributo cargarDetalle(int idAtributo)
/*  20:    */   {
/*  21: 41 */     Atributo atributo = (Atributo)buscarPorId(Integer.valueOf(idAtributo));
/*  22: 42 */     atributo.getListaValorAtributo().size();
/*  23: 43 */     return atributo;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<Atributo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  27:    */   {
/*  28: 48 */     List<Atributo> lista = super.obtenerListaCombo(sortField, sortOrder, filters);
/*  29: 49 */     for (Atributo atributo : lista) {
/*  30: 50 */       atributo.getListaValorAtributo().size();
/*  31:    */     }
/*  32: 52 */     return lista;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<Atributo> obtenerListaComboIndicadorProducto(int idOrganizacion)
/*  36:    */   {
/*  37: 57 */     StringBuilder sql = new StringBuilder();
/*  38: 58 */     sql.append(" SELECT a ");
/*  39: 59 */     sql.append(" FROM Atributo a ");
/*  40: 60 */     sql.append(" WHERE a.activo = true ");
/*  41: 61 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/*  42: 62 */     sql.append(" AND a.indicadorProducto = true ");
/*  43: 63 */     sql.append(" ORDER BY a.nombre ");
/*  44:    */     
/*  45: 65 */     Query query = this.em.createQuery(sql.toString());
/*  46: 66 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  47:    */     
/*  48: 68 */     return query.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<Atributo> obtenerListaComboIndicadorCliente(int idOrganizacion)
/*  52:    */   {
/*  53: 73 */     StringBuilder sql = new StringBuilder();
/*  54: 74 */     sql.append(" SELECT a ");
/*  55: 75 */     sql.append(" FROM Atributo a ");
/*  56: 76 */     sql.append(" WHERE a.activo = true ");
/*  57: 77 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/*  58: 78 */     sql.append(" AND a.indicadorCliente = true ");
/*  59: 79 */     sql.append(" ORDER BY a.nombre ");
/*  60:    */     
/*  61: 81 */     Query query = this.em.createQuery(sql.toString());
/*  62: 82 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  63:    */     
/*  64: 84 */     return query.getResultList();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<Atributo> obtenerListaComboIndicadorProveedor(int idOrganizacion)
/*  68:    */   {
/*  69: 89 */     StringBuilder sql = new StringBuilder();
/*  70: 90 */     sql.append(" SELECT a ");
/*  71: 91 */     sql.append(" FROM Atributo a ");
/*  72: 92 */     sql.append(" WHERE a.activo = true ");
/*  73: 93 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/*  74: 94 */     sql.append(" AND a.indicadorProveedor = true ");
/*  75: 95 */     sql.append(" ORDER BY a.nombre ");
/*  76:    */     
/*  77: 97 */     Query query = this.em.createQuery(sql.toString());
/*  78: 98 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  79:    */     
/*  80:100 */     return query.getResultList();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List getReporteAtributo(int idAtributo)
/*  84:    */   {
/*  85:105 */     String sql = " SELECT a.codigo,a.nombre,a.tipoAtributo, lva.codigo, lva.nombre FROM Atributo a  LEFT JOIN a.listaValorAtributo lva  WHERE a.idAtributo=:idAtributo or :idAtributo=0 ";
/*  86:    */     
/*  87:    */ 
/*  88:108 */     Query query = this.em.createQuery(" SELECT a.codigo,a.nombre,a.tipoAtributo, lva.codigo, lva.nombre FROM Atributo a  LEFT JOIN a.listaValorAtributo lva  WHERE a.idAtributo=:idAtributo or :idAtributo=0 ");
/*  89:109 */     query.setParameter("idAtributo", Integer.valueOf(idAtributo));
/*  90:110 */     return query.getResultList();
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.AtributoDao
 * JD-Core Version:    0.7.0.1
 */