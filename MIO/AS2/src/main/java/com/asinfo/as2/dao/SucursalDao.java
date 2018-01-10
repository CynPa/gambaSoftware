/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Contacto;
/*   4:    */ import com.asinfo.as2.entities.Sucursal;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.Query;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  12:    */ import javax.persistence.criteria.CriteriaQuery;
/*  13:    */ import javax.persistence.criteria.Expression;
/*  14:    */ import javax.persistence.criteria.Fetch;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Order;
/*  17:    */ import javax.persistence.criteria.Predicate;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class SucursalDao
/*  22:    */   extends AbstractDaoAS2<Sucursal>
/*  23:    */ {
/*  24:    */   public SucursalDao()
/*  25:    */   {
/*  26: 40 */     super(Sucursal.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<Sucursal> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  30:    */   {
/*  31: 46 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  32: 47 */     CriteriaQuery<Sucursal> criteriaQuery = criteriaBuilder.createQuery(Sucursal.class);
/*  33: 48 */     Root<Sucursal> from = criteriaQuery.from(Sucursal.class);
/*  34: 49 */     from.fetch("ubicacion", JoinType.LEFT);
/*  35: 50 */     from.fetch("ciudad", JoinType.LEFT);
/*  36: 51 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  37:    */     
/*  38: 53 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  39: 54 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  40:    */     
/*  41: 56 */     CriteriaQuery<Sucursal> select = criteriaQuery.select(from);
/*  42: 57 */     TypedQuery<Sucursal> typedQuery = this.em.createQuery(select);
/*  43:    */     
/*  44: 59 */     return typedQuery.getResultList();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<Sucursal> obtenerListaComboPorUsuario(int idUsuario, int idOrganizacion)
/*  48:    */   {
/*  49: 70 */     String sql = "SELECT s FROM UsuarioSucursal us LEFT JOIN us.sucursal s LEFT JOIN FETCH s.ubicacion WHERE us.entidadUsuario.idUsuario = :idUsuario and s.idOrganizacion = :idOrganizacion  ORDER BY us.predeterminado DESC ";
/*  50:    */     
/*  51: 72 */     Query query = this.em.createQuery(sql).setParameter("idUsuario", Integer.valueOf(idUsuario)).setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  52: 73 */     return query.getResultList();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Sucursal cargarDetalle(int idSucursal)
/*  56:    */   {
/*  57: 84 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  58: 85 */     CriteriaQuery<Sucursal> cq = cb.createQuery(Sucursal.class);
/*  59:    */     
/*  60: 87 */     Root<Sucursal> from = cq.from(Sucursal.class);
/*  61: 88 */     from.fetch("ubicacion", JoinType.LEFT);
/*  62: 89 */     from.fetch("ciudad", JoinType.LEFT).fetch("provincia", JoinType.LEFT).fetch("pais", JoinType.LEFT);
/*  63:    */     
/*  64: 91 */     cq.where(cb.equal(from.get("idSucursal"), Integer.valueOf(idSucursal)));
/*  65: 92 */     Sucursal sucursal = (Sucursal)this.em.createQuery(cq.select(from)).getSingleResult();
/*  66: 93 */     this.em.detach(sucursal);
/*  67:    */     
/*  68: 95 */     CriteriaQuery<Contacto> cqDetalle = cb.createQuery(Contacto.class);
/*  69: 96 */     Root<Contacto> detalle = cqDetalle.from(Contacto.class);
/*  70: 97 */     detalle.fetch("tipoContacto", JoinType.LEFT);
/*  71:    */     
/*  72: 99 */     cqDetalle.where(cb.equal(detalle.get("sucursal"), sucursal));
/*  73:100 */     cqDetalle.orderBy(new Order[] { cb.asc(detalle.get("idContacto")) });
/*  74:    */     
/*  75:102 */     List<Contacto> listaDetalle = this.em.createQuery(cqDetalle).getResultList();
/*  76:103 */     sucursal.setListaContacto(listaDetalle);
/*  77:104 */     return sucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Long getNumeroEstablecimientosPorOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:108 */     StringBuilder sql = new StringBuilder();
/*  83:109 */     sql.append(" SELECT COUNT(*)");
/*  84:110 */     sql.append(" FROM  Sucursal s");
/*  85:111 */     sql.append(" WHERE s.idOrganizacion=:idOrganizacion");
/*  86:112 */     sql.append(" AND s.activo=true");
/*  87:113 */     Query query = this.em.createQuery(sql.toString());
/*  88:114 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  89:115 */     return (Long)query.getSingleResult();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Sucursal buscarPorCodigo(int idOrganizacion, String codigo)
/*  93:    */   {
/*  94:120 */     StringBuilder sql = new StringBuilder();
/*  95:121 */     sql.append(" SELECT s");
/*  96:122 */     sql.append(" FROM  Sucursal s");
/*  97:123 */     sql.append(" LEFT JOIN s.ubicacion u");
/*  98:124 */     sql.append(" WHERE s.idOrganizacion=:idOrganizacion");
/*  99:125 */     sql.append(" AND s.codigo=:codigo");
/* 100:126 */     Query query = this.em.createQuery(sql.toString());
/* 101:127 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 102:128 */     query.setParameter("codigo", codigo);
/* 103:    */     
/* 104:130 */     List lista = query.getResultList();
/* 105:131 */     if ((lista != null) && (lista.size() > 0)) {
/* 106:132 */       return (Sucursal)lista.get(0);
/* 107:    */     }
/* 108:134 */     return null;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SucursalDao
 * JD-Core Version:    0.7.0.1
 */