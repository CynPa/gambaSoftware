/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.Sucursal;
/*   5:    */ import com.asinfo.as2.entities.TipoBodega;
/*   6:    */ import com.asinfo.as2.entities.Ubicacion;
/*   7:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.NoResultException;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TypedQuery;
/*  17:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  18:    */ import javax.persistence.criteria.CriteriaQuery;
/*  19:    */ import javax.persistence.criteria.Expression;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Predicate;
/*  22:    */ import javax.persistence.criteria.Root;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class BodegaDao
/*  26:    */   extends AbstractDaoAS2<Bodega>
/*  27:    */ {
/*  28:    */   public BodegaDao()
/*  29:    */   {
/*  30: 46 */     super(Bodega.class);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Bodega cargarDetalle(Bodega bodega)
/*  34:    */   {
/*  35: 51 */     bodega = (Bodega)buscarPorId(Integer.valueOf(bodega.getIdBodega()));
/*  36: 54 */     if (bodega.getTipoBodega() != null) {
/*  37: 55 */       bodega.getTipoBodega().getId();
/*  38:    */     }
/*  39: 58 */     if (bodega.getSucursal() != null) {
/*  40: 59 */       bodega.getSucursal().getId();
/*  41:    */     }
/*  42: 63 */     bodega.getUbicacionesBodega().size();
/*  43: 64 */     bodega.getUbicacion().getIdUbicacion();
/*  44:    */     
/*  45: 66 */     return bodega;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<Bodega> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  49:    */   {
/*  50: 76 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  51: 77 */     CriteriaQuery<Bodega> criteriaQuery = criteriaBuilder.createQuery(Bodega.class);
/*  52: 78 */     Root<Bodega> from = criteriaQuery.from(Bodega.class);
/*  53:    */     
/*  54: 80 */     from.fetch("tipoBodega", JoinType.LEFT);
/*  55:    */     
/*  56: 82 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  57: 83 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  58:    */     
/*  59: 85 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  60:    */     
/*  61: 87 */     CriteriaQuery<Bodega> select = criteriaQuery.select(from);
/*  62:    */     
/*  63: 89 */     TypedQuery<Bodega> typedQuery = this.em.createQuery(select);
/*  64: 90 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  65:    */     
/*  66: 92 */     return typedQuery.getResultList();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Bodega buscarPorCodigo(String codigo)
/*  70:    */     throws ExcepcionAS2
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:105 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  75:106 */       CriteriaQuery<Bodega> criteriaQuery = criteriaBuilder.createQuery(Bodega.class);
/*  76:107 */       Root<Bodega> from = criteriaQuery.from(Bodega.class);
/*  77:    */       
/*  78:109 */       Map<String, String> filters = new HashMap();
/*  79:110 */       filters.put("codigo", "=" + codigo);
/*  80:111 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  81:112 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  82:    */       
/*  83:114 */       CriteriaQuery<Bodega> select = criteriaQuery.select(from);
/*  84:115 */       TypedQuery<Bodega> typedQuery = this.em.createQuery(select);
/*  85:    */       
/*  86:117 */       return (Bodega)typedQuery.getSingleResult();
/*  87:    */     }
/*  88:    */     catch (NoResultException e)
/*  89:    */     {
/*  90:120 */       throw new ExcepcionAS2("msg_info_bodega_no_encontrada", " " + codigo);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<Bodega> obtenerListaComboPorUsuario(int idUsuario, int idOrganizacion)
/*  95:    */   {
/*  96:133 */     StringBuilder sql = new StringBuilder();
/*  97:134 */     sql.append("SELECT b FROM UsuarioBodega ub LEFT JOIN ub.bodega b ");
/*  98:135 */     sql.append(" WHERE ub.entidadUsuario.idUsuario = :idUsuario and b.idOrganizacion = :idOrganizacion ORDER BY ub.predeterminado DESC, b.nombre ASC ");
/*  99:    */     
/* 100:137 */     Query query = this.em.createQuery(sql.toString()).setParameter("idUsuario", Integer.valueOf(idUsuario)).setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 101:    */     
/* 102:139 */     return query.getResultList();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<UsuarioBodega> obtenerListaComboPorUsuarioBodega(int idUsuario, int idOrganizacion)
/* 106:    */   {
/* 107:151 */     StringBuilder sql = new StringBuilder();
/* 108:152 */     sql.append("SELECT ub FROM UsuarioBodega ub LEFT JOIN FETCH ub.bodega b ");
/* 109:153 */     sql.append(" WHERE ub.entidadUsuario.idUsuario = :idUsuario and b.idOrganizacion = :idOrganizacion ORDER BY ub.predeterminado DESC ");
/* 110:    */     
/* 111:155 */     Query query = this.em.createQuery(sql.toString()).setParameter("idUsuario", Integer.valueOf(idUsuario)).setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 112:    */     
/* 113:157 */     return query.getResultList();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<Bodega> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 117:    */   {
/* 118:167 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 119:168 */     CriteriaQuery<Bodega> criteriaQuery = criteriaBuilder.createQuery(Bodega.class);
/* 120:169 */     Root<Bodega> from = criteriaQuery.from(Bodega.class);
/* 121:170 */     from.fetch("tipoBodega", JoinType.LEFT);
/* 122:171 */     from.fetch("sucursal", JoinType.LEFT);
/* 123:172 */     from.fetch("ubicacion", JoinType.LEFT);
/* 124:173 */     boolean filtroOr = false;
/* 125:174 */     String filterValue = "";
/* 126:175 */     if ((filters != null) && (filters.containsKey("nombreOCodigo")))
/* 127:    */     {
/* 128:176 */       filterValue = (String)filters.get("nombreOCodigo");
/* 129:177 */       filters.remove("nombreOCodigo");
/* 130:178 */       filtroOr = true;
/* 131:    */     }
/* 132:180 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 133:181 */     if (filtroOr)
/* 134:    */     {
/* 135:182 */       Expression<String> nombre = from.get("nombre");
/* 136:183 */       Expression<String> codigo = from.get("codigo");
/* 137:184 */       expresiones.add(criteriaBuilder.or(criteriaBuilder.like(nombre, filterValue), criteriaBuilder.like(codigo, filterValue)));
/* 138:    */     }
/* 139:186 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 140:    */     
/* 141:188 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 142:    */     
/* 143:190 */     CriteriaQuery<Bodega> select = criteriaQuery.select(from);
/* 144:    */     
/* 145:192 */     TypedQuery<Bodega> typedQuery = this.em.createQuery(select);
/* 146:    */     
/* 147:194 */     List<Bodega> lista = typedQuery.getResultList();
/* 148:    */     
/* 149:196 */     return lista;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<Bodega> obtenerListaComboPorUsuario(int idUsuario, int idOrganizacion, int idSucursal)
/* 153:    */   {
/* 154:201 */     StringBuilder sql = new StringBuilder();
/* 155:202 */     sql.append(" SELECT b ");
/* 156:203 */     sql.append(" FROM UsuarioBodega ub ");
/* 157:204 */     sql.append(" INNER JOIN ub.bodega b ");
/* 158:205 */     sql.append(" INNER JOIN b.sucursal suc ");
/* 159:206 */     sql.append(" INNER JOIN ub.entidadUsuario eu ");
/* 160:207 */     sql.append(" WHERE eu.idUsuario = :idUsuario ");
/* 161:208 */     sql.append(" AND b.idOrganizacion = :idOrganizacion ");
/* 162:209 */     sql.append(" AND suc.idSucursal = :idSucursal ");
/* 163:210 */     sql.append(" ORDER BY ub.predeterminado DESC, b.nombre ASC ");
/* 164:    */     
/* 165:212 */     Query query = this.em.createQuery(sql.toString());
/* 166:213 */     query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 167:214 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 168:215 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 169:    */     
/* 170:217 */     return query.getResultList();
/* 171:    */   }
/* 172:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.BodegaDao
 * JD-Core Version:    0.7.0.1
 */