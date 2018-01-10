/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empleado;
/*   4:    */ import com.asinfo.as2.entities.RubroOtraEmpresa;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.Query;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  12:    */ import javax.persistence.criteria.CriteriaQuery;
/*  13:    */ import javax.persistence.criteria.Expression;
/*  14:    */ import javax.persistence.criteria.JoinType;
/*  15:    */ import javax.persistence.criteria.Predicate;
/*  16:    */ import javax.persistence.criteria.Root;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class RubroOtraEmpresaDao
/*  20:    */   extends AbstractDaoAS2<RubroOtraEmpresa>
/*  21:    */ {
/*  22:    */   public RubroOtraEmpresaDao()
/*  23:    */   {
/*  24: 42 */     super(RubroOtraEmpresa.class);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public List<RubroOtraEmpresa> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  28:    */   {
/*  29: 52 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  30: 53 */     CriteriaQuery<RubroOtraEmpresa> criteriaQuery = criteriaBuilder.createQuery(RubroOtraEmpresa.class);
/*  31: 54 */     Root<RubroOtraEmpresa> from = criteriaQuery.from(RubroOtraEmpresa.class);
/*  32: 55 */     from.fetch("empleado", JoinType.LEFT);
/*  33:    */     
/*  34: 57 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  35: 58 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  36:    */     
/*  37: 60 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  38:    */     
/*  39: 62 */     CriteriaQuery<RubroOtraEmpresa> select = criteriaQuery.select(from);
/*  40:    */     
/*  41: 64 */     TypedQuery<RubroOtraEmpresa> typedQuery = this.em.createQuery(select);
/*  42: 65 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  43:    */     
/*  44: 67 */     return typedQuery.getResultList();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public RubroOtraEmpresa cargarDetalle(int idRubroOtraEmpresa)
/*  48:    */   {
/*  49: 78 */     RubroOtraEmpresa rubroOtraEmpresa = (RubroOtraEmpresa)buscarPorId(Integer.valueOf(idRubroOtraEmpresa));
/*  50: 79 */     rubroOtraEmpresa.getEmpleado().getId();
/*  51: 80 */     return rubroOtraEmpresa;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<RubroOtraEmpresa> getRubroOtraEmpresa(int idOrganizacion, Integer idEmpleado, int anio)
/*  55:    */   {
/*  56: 86 */     StringBuilder sql = new StringBuilder();
/*  57: 87 */     sql.append(" SELECT new RubroOtraEmpresa (roe.idRubroOtraEmpresa, roe.idOrganizacion, roe.idSucursal, roe.anio, roe.mes, roe.valorIngreso, roe.aportePersonalIessOtroEmpleador, roe.valorRetenido, e2.identificacion)");
/*  58: 88 */     sql.append(" FROM RubroOtraEmpresa roe");
/*  59: 89 */     sql.append(" JOIN roe.empleado e1");
/*  60: 90 */     sql.append(" JOIN e1.empresa e2");
/*  61: 91 */     sql.append(" WHERE roe.idOrganizacion = :idOrganizacion AND roe.anio = :anio");
/*  62: 93 */     if (idEmpleado != null) {
/*  63: 94 */       sql.append(" AND e1.idEmpleado = :idEmpleado ");
/*  64:    */     }
/*  65: 97 */     Query query = this.em.createQuery(sql.toString());
/*  66: 98 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  67: 99 */     query.setParameter("anio", Integer.valueOf(anio));
/*  68:100 */     if (idEmpleado != null) {
/*  69:101 */       query.setParameter("idEmpleado", idEmpleado);
/*  70:    */     }
/*  71:105 */     return query.getResultList();
/*  72:    */   }
/*  73:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RubroOtraEmpresaDao
 * JD-Core Version:    0.7.0.1
 */