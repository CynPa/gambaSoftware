/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   4:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*   7:    */ import com.asinfo.as2.entities.Departamento;
/*   8:    */ import com.asinfo.as2.entities.Depreciacion;
/*   9:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*  10:    */ import com.asinfo.as2.entities.DimensionContable;
/*  11:    */ import com.asinfo.as2.entities.Empleado;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.HistoricoDepreciacion;
/*  14:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  19:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import javax.persistence.EntityManager;
/*  25:    */ import javax.persistence.NoResultException;
/*  26:    */ import javax.persistence.Query;
/*  27:    */ import javax.persistence.TypedQuery;
/*  28:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  29:    */ import javax.persistence.criteria.CriteriaQuery;
/*  30:    */ import javax.persistence.criteria.Expression;
/*  31:    */ import javax.persistence.criteria.Fetch;
/*  32:    */ import javax.persistence.criteria.JoinType;
/*  33:    */ import javax.persistence.criteria.Path;
/*  34:    */ import javax.persistence.criteria.Predicate;
/*  35:    */ import javax.persistence.criteria.Root;
/*  36:    */ import javax.persistence.criteria.Selection;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ public class ActivoFijoDao
/*  40:    */   extends AbstractDaoAS2<ActivoFijo>
/*  41:    */ {
/*  42:    */   public ActivoFijoDao()
/*  43:    */   {
/*  44: 49 */     super(ActivoFijo.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<ActivoFijo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 59 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  50: 60 */     CriteriaQuery<ActivoFijo> criteriaQuery = criteriaBuilder.createQuery(ActivoFijo.class);
/*  51: 61 */     Root<ActivoFijo> from = criteriaQuery.from(ActivoFijo.class);
/*  52: 62 */     from.fetch("categoriaActivo", JoinType.LEFT);
/*  53: 63 */     from.fetch("subcategoriaActivo", JoinType.LEFT);
/*  54: 64 */     from.fetch("sucursal", JoinType.LEFT);
/*  55: 65 */     from.fetch("departamento", JoinType.LEFT);
/*  56: 66 */     from.fetch("asientoBajaActivoNIIF", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*  57: 67 */     from.fetch("asientoBajaActivoFiscal", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*  58:    */     
/*  59: 69 */     Fetch<Object, Object> custodioActivoFijo = from.fetch("custodioActivoFijo", JoinType.LEFT);
/*  60:    */     
/*  61: 71 */     Fetch<Object, Object> empleado = custodioActivoFijo.fetch("empleado", JoinType.LEFT);
/*  62: 72 */     empleado.fetch("empresa", JoinType.LEFT);
/*  63:    */     
/*  64: 74 */     Fetch<Object, Object> ubicacionActivo = custodioActivoFijo.fetch("ubicacionActivo", JoinType.LEFT);
/*  65: 75 */     ubicacionActivo.fetch("sucursal", JoinType.LEFT);
/*  66: 76 */     ubicacionActivo.fetch("departamento", JoinType.LEFT);
/*  67:    */     
/*  68: 78 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  69: 79 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  70:    */     
/*  71: 81 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  72:    */     
/*  73: 83 */     CriteriaQuery<ActivoFijo> select = criteriaQuery.select(from);
/*  74: 84 */     TypedQuery<ActivoFijo> typedQuery = this.em.createQuery(select);
/*  75: 85 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  76:    */     
/*  77: 87 */     return typedQuery.getResultList();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<ActivoFijo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  81:    */   {
/*  82: 97 */     boolean notSetMaxResults = false;
/*  83: 98 */     if ((filters != null) && (filters.get("notSetMaxResults") != null))
/*  84:    */     {
/*  85: 99 */       notSetMaxResults = true;
/*  86:100 */       filters.remove("notSetMaxResults");
/*  87:    */     }
/*  88:102 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  89:103 */     CriteriaQuery<ActivoFijo> criteriaQuery = criteriaBuilder.createQuery(ActivoFijo.class);
/*  90:104 */     Root<ActivoFijo> from = criteriaQuery.from(ActivoFijo.class);
/*  91:    */     
/*  92:106 */     Path<Integer> pathIdActivoFijo = from.get("idActivoFijo");
/*  93:107 */     Path<String> pathCodigo = from.get("codigo");
/*  94:108 */     Path<String> pathNombre = from.get("nombre");
/*  95:    */     
/*  96:110 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  97:111 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  98:    */     
/*  99:113 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 100:    */     
/* 101:115 */     CriteriaQuery<ActivoFijo> select = criteriaQuery.multiselect(new Selection[] { pathIdActivoFijo, pathCodigo, pathNombre });
/* 102:116 */     TypedQuery<ActivoFijo> typedQuery = this.em.createQuery(select);
/* 103:119 */     if (!notSetMaxResults) {
/* 104:120 */       typedQuery.setMaxResults(20);
/* 105:    */     }
/* 106:123 */     return typedQuery.getResultList();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<ActivoFijo> obtenerListaComboParaMantenimiento(String sortField, boolean sortOrder, Map<String, String> filters)
/* 110:    */   {
/* 111:127 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 112:128 */     CriteriaQuery<ActivoFijo> criteriaQuery = criteriaBuilder.createQuery(ActivoFijo.class);
/* 113:129 */     Root<ActivoFijo> from = criteriaQuery.from(ActivoFijo.class);
/* 114:130 */     from.fetch("centroCosto", JoinType.LEFT);
/* 115:    */     
/* 116:132 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 117:    */     
/* 118:134 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 119:    */     
/* 120:136 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 121:    */     
/* 122:138 */     CriteriaQuery<ActivoFijo> select = criteriaQuery.select(from);
/* 123:139 */     TypedQuery<ActivoFijo> typedQuery = this.em.createQuery(select);
/* 124:140 */     typedQuery.setMaxResults(20);
/* 125:    */     
/* 126:142 */     return typedQuery.getResultList();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public ActivoFijo cargarDetalle(int idActivoFijo)
/* 130:    */   {
/* 131:154 */     ActivoFijo activoFijo = (ActivoFijo)buscarPorId(Integer.valueOf(idActivoFijo));
/* 132:    */     
/* 133:156 */     activoFijo.getCategoriaActivo().getListaSubcategoriaActivo().size();
/* 134:158 */     if (activoFijo.getMotivoBajaActivo() != null) {
/* 135:159 */       activoFijo.getMotivoBajaActivo().getId();
/* 136:    */     }
/* 137:162 */     if (activoFijo.getSubcategoriaActivo() != null) {
/* 138:163 */       activoFijo.getSubcategoriaActivo().getId();
/* 139:    */     }
/* 140:166 */     if (activoFijo.getCustodioActivoFijo() != null)
/* 141:    */     {
/* 142:167 */       activoFijo.getCustodioActivoFijo().getId();
/* 143:168 */       activoFijo.getCustodioActivoFijo().getUbicacionActivo().getId();
/* 144:169 */       activoFijo.getCustodioActivoFijo().getUbicacionActivo().getDepartamento().getId();
/* 145:170 */       activoFijo.getCustodioActivoFijo().getUbicacionActivo().getSucursal().getId();
/* 146:    */     }
/* 147:172 */     if (activoFijo.getSucursal() != null) {
/* 148:173 */       activoFijo.getSucursal().getId();
/* 149:    */     }
/* 150:175 */     if (activoFijo.getDepartamento() != null) {
/* 151:176 */       activoFijo.getDepartamento().getId();
/* 152:    */     }
/* 153:179 */     activoFijo.getCategoriaActivo().getId();
/* 154:181 */     if (activoFijo.getCategoriaActivo().getCuentaContableActivoFijo() != null) {
/* 155:182 */       activoFijo.getCategoriaActivo().getCuentaContableActivoFijo().getId();
/* 156:    */     }
/* 157:185 */     if (activoFijo.getCategoriaActivo().getCuentaContableDepreciacionAcumulada() != null) {
/* 158:186 */       activoFijo.getCategoriaActivo().getCuentaContableDepreciacionAcumulada().getId();
/* 159:    */     }
/* 160:189 */     if (activoFijo.getCategoriaActivo().getCuentaContableSuperavitPorRevalorizacion() != null) {
/* 161:190 */       activoFijo.getCategoriaActivo().getCuentaContableSuperavitPorRevalorizacion().getId();
/* 162:    */     }
/* 163:193 */     if (activoFijo.getCategoriaActivo().getCuentaContableDeDeficitPorRevalorizacion() != null) {
/* 164:194 */       activoFijo.getCategoriaActivo().getCuentaContableDeDeficitPorRevalorizacion().getId();
/* 165:    */     }
/* 166:197 */     if (activoFijo.getProducto() != null) {
/* 167:198 */       activoFijo.getProducto().getId();
/* 168:    */     }
/* 169:201 */     if (activoFijo.getActivoFijoPrincipal() != null) {
/* 170:202 */       activoFijo.getActivoFijoPrincipal().getId();
/* 171:    */     }
/* 172:204 */     if (activoFijo.getCentroCosto() != null) {
/* 173:205 */       activoFijo.getCentroCosto().getIdDimensionContable();
/* 174:    */     }
/* 175:208 */     activoFijo.getListaDepreciacion().size();
/* 176:209 */     activoFijo.getListaActivoFijoRelacionado().size();
/* 177:211 */     for (Depreciacion depreciacion : activoFijo.getListaDepreciacion())
/* 178:    */     {
/* 179:212 */       depreciacion.getListaDetalleDepreciacion().size();
/* 180:213 */       for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion()) {
/* 181:214 */         if (detalleDepreciacion.getHistoricoDepreciacion() != null) {
/* 182:215 */           detalleDepreciacion.getHistoricoDepreciacion().getId();
/* 183:    */         }
/* 184:    */       }
/* 185:    */     }
/* 186:220 */     activoFijo.getListaCustodioActivoFijo().size();
/* 187:221 */     for (CustodioActivoFijo custodioActivoFijo : activoFijo.getListaCustodioActivoFijo())
/* 188:    */     {
/* 189:222 */       if (custodioActivoFijo.getEmpleado() != null)
/* 190:    */       {
/* 191:223 */         custodioActivoFijo.getEmpleado().getId();
/* 192:224 */         custodioActivoFijo.getEmpleado().getEmpresa().getId();
/* 193:    */       }
/* 194:226 */       if (custodioActivoFijo.getEmpresa() != null) {
/* 195:227 */         custodioActivoFijo.getEmpresa().getId();
/* 196:    */       }
/* 197:229 */       custodioActivoFijo.getUbicacionActivo().getId();
/* 198:    */     }
/* 199:232 */     return activoFijo;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public BigDecimal getValorImpuestosDiferidosNIIF(int idActivoFijo)
/* 203:    */   {
/* 204:244 */     Query query = this.em.createQuery(" SELECT ROUND(SUM(dd.diferenciaTemporal)*p.porcentaje/100,2) FROM DetalleDepreciacion dd  INNER JOIN dd.depreciacion d  INNER JOIN d.activoFijo af, PorcentajeImpuestoRentaAnual p  WHERE d.activo = :activo  AND d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal  AND dd.historicoDepreciacion IS NOT NULL  AND af.idActivoFijo = :idActivoFijo AND p.anio = dd.anio GROUP BY p.porcentaje");
/* 205:    */     
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:249 */     query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 210:250 */     query.setParameter("activo", Boolean.valueOf(true));
/* 211:251 */     query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(false));
/* 212:    */     try
/* 213:    */     {
/* 214:253 */       return (BigDecimal)query.getSingleResult();
/* 215:    */     }
/* 216:    */     catch (NoResultException e) {}
/* 217:255 */     return BigDecimal.ZERO;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public BigDecimal getValorDepreciacionTotalAcumulada(int idActivoFijo, boolean indicadorDepreciacionFiscal)
/* 221:    */   {
/* 222:268 */     StringBuilder sql = new StringBuilder();
/* 223:269 */     sql.append(" SELECT coalesce(sum(dd.valor),0) FROM DetalleDepreciacion dd ");
/* 224:270 */     sql.append(" INNER JOIN dd.depreciacion d ");
/* 225:271 */     sql.append(" INNER JOIN d.activoFijo af ");
/* 226:272 */     sql.append(" WHERE d.activo = :activo ");
/* 227:273 */     sql.append(" AND d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal ");
/* 228:274 */     sql.append(" AND dd.historicoDepreciacion IS NOT NULL ");
/* 229:275 */     sql.append(" AND af.idActivoFijo = :idActivoFijo AND dd.fecha <= af.fechaBaja  ");
/* 230:276 */     Query query = this.em.createQuery(sql.toString());
/* 231:    */     
/* 232:278 */     query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 233:279 */     query.setParameter("activo", Boolean.valueOf(true));
/* 234:280 */     query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(indicadorDepreciacionFiscal));
/* 235:    */     
/* 236:282 */     return (BigDecimal)query.getSingleResult();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public BigDecimal getValorActivoFijoNIIF(int idActivoFijo)
/* 240:    */   {
/* 241:295 */     Query query = this.em.createQuery(" SELECT COALESCE(SUM(d.valorActivo-dp.valorActivo),0) FROM Depreciacion d  JOIN d.depreciacionPadre dp  INNER JOIN d.activoFijo af  WHERE d.activo = :activo  AND af.idActivoFijo = :idActivoFijo");
/* 242:    */     
/* 243:    */ 
/* 244:298 */     query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 245:299 */     query.setParameter("activo", Boolean.valueOf(true));
/* 246:300 */     return (BigDecimal)query.getSingleResult();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public TipoAsiento cargarTipoAsiento(ActivoFijo activoFijo)
/* 250:    */   {
/* 251:306 */     Query query = this.em.createQuery(" SELECT ta FROM ActivoFijo af  INNER JOIN af.motivoBajaActivo mba  INNER JOIN mba.documento d  INNER JOIN d.tipoAsiento ta  WHERE af = :activoFijo ");
/* 252:    */     
/* 253:    */ 
/* 254:309 */     query.setParameter("activoFijo", activoFijo);
/* 255:310 */     return (TipoAsiento)query.getSingleResult();
/* 256:    */   }
/* 257:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ActivoFijoDao
 * JD-Core Version:    0.7.0.1
 */