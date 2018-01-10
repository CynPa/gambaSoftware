/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Organizacion;
/*   4:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   5:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Expression;
/*  15:    */ import javax.persistence.criteria.Fetch;
/*  16:    */ import javax.persistence.criteria.Join;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Path;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class OrganizacionDao
/*  24:    */   extends AbstractDaoAS2<Organizacion>
/*  25:    */ {
/*  26:    */   public OrganizacionDao()
/*  27:    */   {
/*  28: 37 */     super(Organizacion.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<Organizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 47 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34: 48 */     CriteriaQuery<Organizacion> criteriaQuery = criteriaBuilder.createQuery(Organizacion.class);
/*  35: 49 */     Root<Organizacion> from = criteriaQuery.from(Organizacion.class);
/*  36:    */     
/*  37: 51 */     from.fetch("tipoIdentificacion", JoinType.LEFT);
/*  38: 52 */     from.fetch("tipoIdentificacionContador", JoinType.LEFT);
/*  39: 53 */     from.fetch("tipoIdentificacionRepresentante", JoinType.LEFT);
/*  40:    */     
/*  41: 55 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  42:    */     
/*  43: 57 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  44: 58 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  45:    */     
/*  46: 60 */     CriteriaQuery<Organizacion> select = criteriaQuery.select(from);
/*  47:    */     
/*  48: 62 */     TypedQuery<Organizacion> typedQuery = this.em.createQuery(select);
/*  49: 63 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  50:    */     
/*  51: 65 */     return typedQuery.getResultList();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<Organizacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56: 75 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  57: 76 */     CriteriaQuery<Organizacion> criteriaQuery = criteriaBuilder.createQuery(Organizacion.class);
/*  58: 77 */     Root<Organizacion> from = criteriaQuery.from(Organizacion.class);
/*  59:    */     
/*  60: 79 */     from.fetch("tipoIdentificacion", JoinType.LEFT);
/*  61: 80 */     from.fetch("tipoIdentificacionContador", JoinType.LEFT);
/*  62: 81 */     from.fetch("tipoIdentificacionRepresentante", JoinType.LEFT);
/*  63:    */     
/*  64: 83 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  65:    */     
/*  66: 85 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  67: 86 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  68:    */     
/*  69: 88 */     CriteriaQuery<Organizacion> select = criteriaQuery.select(from);
/*  70: 89 */     TypedQuery<Organizacion> typedQuery = this.em.createQuery(select);
/*  71:    */     
/*  72: 91 */     return typedQuery.getResultList();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<Organizacion> obtenerOrganizacionPorUsuario(int idUsuario)
/*  76:    */   {
/*  77:101 */     return obtenerOrganizacionPorUsuario(idUsuario, 0, 0, null, false);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<Organizacion> obtenerOrganizacionPorUsuario(int idUsuario, int startIndex, int pageSize, String sortField, boolean ordenar)
/*  81:    */   {
/*  82:105 */     return obtenerOrganizacionPorUsuario(idUsuario, startIndex, pageSize, sortField, ordenar, null);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<Organizacion> obtenerOrganizacionPorUsuario(int idUsuario, int startIndex, int pageSize, String sortField, boolean ordenar, EntidadSistema entidadSistema)
/*  86:    */   {
/*  87:111 */     String orden = ordenar ? "ASC" : "DESC";
/*  88:    */     
/*  89:113 */     StringBuilder sql = new StringBuilder();
/*  90:114 */     sql.append(" SELECT o ");
/*  91:115 */     sql.append(" FROM UsuarioOrganizacion uo ");
/*  92:116 */     sql.append(" INNER JOIN uo.entidadUsuario u ");
/*  93:117 */     sql.append(" INNER JOIN uo.organizacion o ");
/*  94:118 */     sql.append(" LEFT JOIN FETCH o.tipoIdentificacion t ");
/*  95:119 */     sql.append(" LEFT JOIN FETCH o.tipoIdentificacionContador t ");
/*  96:120 */     sql.append(" LEFT JOIN FETCH o.tipoIdentificacionRepresentante t ");
/*  97:121 */     sql.append(" LEFT JOIN FETCH o.organizacionConfiguracion oc ");
/*  98:122 */     sql.append(" WHERE u.idUsuario = :idUsuario");
/*  99:123 */     if (entidadSistema != null)
/* 100:    */     {
/* 101:125 */       sql.append(" AND o.idOrganizacion IN (  ");
/* 102:126 */       sql.append("\tSELECT DISTINCT(o1.idOrganizacion) FROM EntidadPermiso ep ");
/* 103:127 */       sql.append("\tINNER JOIN ep.procesoOrganizacion po ");
/* 104:128 */       sql.append("\tINNER JOIN po.organizacion o1 ");
/* 105:129 */       sql.append("\tINNER JOIN po.entidadProceso epro ");
/* 106:130 */       sql.append("\tINNER JOIN epro.sistema sis ");
/* 107:131 */       sql.append("\tWHERE sis.idSistema =:idSistema  ) ");
/* 108:    */     }
/* 109:134 */     if (sortField != null) {
/* 110:139 */       sql.append(" ORDER BY o." + sortField + " " + orden);
/* 111:    */     } else {
/* 112:142 */       sql.append(" ORDER BY uo.predeterminado DESC");
/* 113:    */     }
/* 114:144 */     Query queryOrganizacion = this.em.createQuery(sql.toString());
/* 115:145 */     queryOrganizacion.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 116:146 */     if (entidadSistema != null) {
/* 117:147 */       queryOrganizacion.setParameter("idSistema", Integer.valueOf(entidadSistema.getIdSistema()));
/* 118:    */     }
/* 119:149 */     if (startIndex != 0) {
/* 120:150 */       queryOrganizacion.setFirstResult(startIndex);
/* 121:    */     }
/* 122:151 */     if (pageSize != 0) {
/* 123:152 */       queryOrganizacion.setMaxResults(pageSize);
/* 124:    */     }
/* 125:154 */     return queryOrganizacion.getResultList();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Long contarNumeroOrganizaciones()
/* 129:    */   {
/* 130:163 */     StringBuilder sql = new StringBuilder();
/* 131:164 */     sql.append("SELECT count(o) FROM Organizacion o ");
/* 132:165 */     Query query = this.em.createQuery(sql.toString());
/* 133:    */     
/* 134:167 */     return (Long)query.getSingleResult();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Organizacion cargarOrganizacionConfiguracion(int idOrganizacion)
/* 138:    */   {
/* 139:178 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 140:    */     
/* 141:    */ 
/* 142:181 */     CriteriaQuery<Organizacion> cqCabecera = criteriaBuilder.createQuery(Organizacion.class);
/* 143:182 */     Root<Organizacion> fromCabecera = cqCabecera.from(Organizacion.class);
/* 144:    */     
/* 145:184 */     fromCabecera.fetch("organizacionConfiguracion", JoinType.LEFT);
/* 146:185 */     Path<Integer> pathId = fromCabecera.get("idOrganizacion");
/* 147:186 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idOrganizacion)));
/* 148:187 */     CriteriaQuery<Organizacion> selectOrganizacion = cqCabecera.select(fromCabecera);
/* 149:    */     
/* 150:189 */     Organizacion organizacion = (Organizacion)this.em.createQuery(selectOrganizacion).getSingleResult();
/* 151:    */     
/* 152:191 */     return organizacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Organizacion cargarDetalle(int idOrganizacion)
/* 156:    */   {
/* 157:202 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 158:    */     
/* 159:    */ 
/* 160:205 */     CriteriaQuery<Organizacion> cqCabecera = criteriaBuilder.createQuery(Organizacion.class);
/* 161:206 */     Root<Organizacion> fromCabecera = cqCabecera.from(Organizacion.class);
/* 162:    */     
/* 163:208 */     Fetch<Object, Object> organizacionConfiguracion = fromCabecera.fetch("organizacionConfiguracion", JoinType.LEFT);
/* 164:209 */     fromCabecera.fetch("tipoIdentificacion", JoinType.LEFT);
/* 165:210 */     fromCabecera.fetch("tipoIdentificacionContador", JoinType.LEFT);
/* 166:211 */     fromCabecera.fetch("tipoIdentificacionRepresentante", JoinType.LEFT);
/* 167:213 */     for (int i = 1; i <= 10; i++) {
/* 168:214 */       organizacionConfiguracion.fetch("atributo" + i, JoinType.LEFT);
/* 169:    */     }
/* 170:217 */     Path<Integer> pathId = fromCabecera.get("idOrganizacion");
/* 171:218 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idOrganizacion)));
/* 172:219 */     CriteriaQuery<Organizacion> selectOrganizacion = cqCabecera.select(fromCabecera);
/* 173:    */     
/* 174:221 */     Organizacion organizacion = (Organizacion)this.em.createQuery(selectOrganizacion).getSingleResult();
/* 175:    */     
/* 176:    */ 
/* 177:224 */     CriteriaQuery<ProcesoOrganizacion> cqLista = criteriaBuilder.createQuery(ProcesoOrganizacion.class);
/* 178:225 */     Root<ProcesoOrganizacion> fromLista = cqLista.from(ProcesoOrganizacion.class);
/* 179:    */     
/* 180:227 */     fromLista.fetch("organizacion", JoinType.INNER);
/* 181:228 */     fromLista.fetch("entidadProceso", JoinType.INNER);
/* 182:    */     
/* 183:230 */     Path<Integer> pathIdListaOrganizacion = fromLista.join("organizacion").get("idOrganizacion");
/* 184:231 */     cqLista.where(criteriaBuilder.equal(pathIdListaOrganizacion, Integer.valueOf(idOrganizacion)));
/* 185:232 */     CriteriaQuery<ProcesoOrganizacion> selectLista = cqLista.select(fromLista);
/* 186:    */     
/* 187:234 */     List<ProcesoOrganizacion> listaProcesoOrganizacion = this.em.createQuery(selectLista).getResultList();
/* 188:235 */     organizacion.setListaProcesoOrganizacion(listaProcesoOrganizacion);
/* 189:    */     
/* 190:237 */     return organizacion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int getIdOrganizacionMinima()
/* 194:    */   {
/* 195:241 */     StringBuilder sql = new StringBuilder();
/* 196:242 */     sql.append(" SELECT MIN(o.idOrganizacion) ");
/* 197:243 */     sql.append(" FROM Organizacion o ");
/* 198:    */     
/* 199:245 */     Query queryOrganizacion = this.em.createQuery(sql.toString());
/* 200:246 */     queryOrganizacion.setMaxResults(1);
/* 201:247 */     return ((Integer)queryOrganizacion.getSingleResult()).intValue();
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.OrganizacionDao
 * JD-Core Version:    0.7.0.1
 */