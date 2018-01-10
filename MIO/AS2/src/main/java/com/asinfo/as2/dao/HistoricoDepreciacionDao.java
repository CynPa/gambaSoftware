/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*   5:    */ import com.asinfo.as2.entities.HistoricoDepreciacion;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.Fetch;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Predicate;
/*  22:    */ import javax.persistence.criteria.Root;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class HistoricoDepreciacionDao
/*  26:    */   extends AbstractDaoAS2<HistoricoDepreciacion>
/*  27:    */ {
/*  28:    */   public HistoricoDepreciacionDao()
/*  29:    */   {
/*  30: 49 */     super(HistoricoDepreciacion.class);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<HistoricoDepreciacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 60 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  36: 61 */     CriteriaQuery<HistoricoDepreciacion> criteriaQuery = criteriaBuilder.createQuery(HistoricoDepreciacion.class);
/*  37: 62 */     Root<HistoricoDepreciacion> from = criteriaQuery.from(HistoricoDepreciacion.class);
/*  38: 63 */     Fetch<Object, Object> asientoNIIF = from.fetch("asientoNIIF", JoinType.LEFT);
/*  39: 64 */     asientoNIIF.fetch("tipoAsiento", JoinType.LEFT);
/*  40:    */     
/*  41: 66 */     Fetch<Object, Object> asientoFiscal = from.fetch("asientoFiscal", JoinType.LEFT);
/*  42: 67 */     asientoFiscal.fetch("tipoAsiento", JoinType.LEFT);
/*  43:    */     
/*  44: 69 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  45: 70 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  46: 71 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  47:    */     
/*  48: 73 */     CriteriaQuery<HistoricoDepreciacion> select = criteriaQuery.select(from);
/*  49: 74 */     TypedQuery<HistoricoDepreciacion> typedQuery = this.em.createQuery(select);
/*  50: 75 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  51:    */     
/*  52: 77 */     return typedQuery.getResultList();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<HistoricoDepreciacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  56:    */   {
/*  57: 86 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<DetalleDepreciacion> obtieneDetalleDepreciacionAdepreciarFiscal(int anio, int mes, int idOrganizacion)
/*  61:    */     throws ExcepcionAS2Financiero
/*  62:    */   {
/*  63:102 */     StringBuilder sql = new StringBuilder();
/*  64:    */     
/*  65:104 */     sql.append(" SELECT dd FROM DetalleDepreciacion dd ");
/*  66:105 */     sql.append(" INNER JOIN dd.depreciacion d ");
/*  67:106 */     sql.append(" INNER JOIN d.activoFijo af ");
/*  68:107 */     sql.append(" WHERE af.activo = false and dd.fecha > af.fechaBaja AND af.indicadorDepreciar = true ");
/*  69:108 */     sql.append(" AND d.indicadorDepreciacionFiscal = true ");
/*  70:109 */     sql.append(" AND af.idOrganizacion = :idOrganizacion AND dd.historicoDepreciacion IS NOT NULL ");
/*  71:    */     
/*  72:111 */     Query query = this.em.createQuery(sql.toString());
/*  73:112 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  74:113 */     List<DetalleDepreciacion> listaDetalleDepreciacion = query.getResultList();
/*  75:115 */     if (listaDetalleDepreciacion.size() > 0)
/*  76:    */     {
/*  77:116 */       sql = new StringBuilder();
/*  78:117 */       sql.append(" UPDATE DetalleDepreciacion dd set historicoDepreciacion = NULL ");
/*  79:118 */       sql.append(" WHERE dd IN (:listaDetalleDepreciacion)");
/*  80:119 */       query = this.em.createQuery(sql.toString());
/*  81:120 */       query.setParameter("listaDetalleDepreciacion", listaDetalleDepreciacion);
/*  82:    */       
/*  83:122 */       query.executeUpdate();
/*  84:    */     }
/*  85:125 */     sql = new StringBuilder();
/*  86:126 */     sql.append(" SELECT dd FROM DetalleDepreciacion dd ");
/*  87:127 */     sql.append(" INNER JOIN dd.depreciacion d ");
/*  88:128 */     sql.append(" INNER JOIN d.activoFijo af ");
/*  89:129 */     sql.append(" WHERE dd.anio = :anio AND dd.mes = :mes AND (af.activo = true or dd.fecha <= af.fechaBaja) AND af.indicadorDepreciar = true ");
/*  90:130 */     sql.append(" AND d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal AND d.activo = :activo AND dd.activo = true ");
/*  91:131 */     sql.append(" AND af.idOrganizacion = :idOrganizacion ");
/*  92:    */     
/*  93:133 */     query = this.em.createQuery(sql.toString());
/*  94:134 */     query.setParameter("anio", Integer.valueOf(anio));
/*  95:135 */     query.setParameter("mes", Integer.valueOf(mes));
/*  96:136 */     query.setParameter("activo", Boolean.valueOf(true));
/*  97:137 */     query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(true));
/*  98:138 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  99:    */     
/* 100:140 */     return query.getResultList();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<DetalleDepreciacion> obtieneDetalleDepreciacionAdepreciarNIIF(int anio, int mes, int idOrganizacion)
/* 104:    */     throws ExcepcionAS2Financiero
/* 105:    */   {
/* 106:156 */     Date fechaFinMes = FuncionesUtiles.getFechaFinMes(anio, mes);
/* 107:    */     
/* 108:158 */     StringBuilder sql = new StringBuilder();
/* 109:159 */     sql.append(" SELECT dd FROM DetalleDepreciacion dd ");
/* 110:160 */     sql.append(" INNER JOIN dd.depreciacion d ");
/* 111:161 */     sql.append(" INNER JOIN d.activoFijo af ");
/* 112:162 */     sql.append(" WHERE af.activo = false and dd.fecha > af.fechaBaja AND af.indicadorDepreciar = true ");
/* 113:163 */     sql.append(" AND d.indicadorDepreciacionFiscal = false ");
/* 114:164 */     sql.append(" AND af.idOrganizacion = :idOrganizacion AND dd.historicoDepreciacion IS NOT NULL ");
/* 115:    */     
/* 116:166 */     Query query = this.em.createQuery(sql.toString());
/* 117:167 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 118:168 */     List<DetalleDepreciacion> listaDetalleDepreciacion = query.getResultList();
/* 119:170 */     if (listaDetalleDepreciacion.size() > 0)
/* 120:    */     {
/* 121:171 */       sql = new StringBuilder();
/* 122:172 */       sql.append(" UPDATE DetalleDepreciacion dd set historicoDepreciacion = NULL ");
/* 123:173 */       sql.append(" WHERE dd IN (:listaDetalleDepreciacion)");
/* 124:174 */       query = this.em.createQuery(sql.toString());
/* 125:175 */       query.setParameter("listaDetalleDepreciacion", listaDetalleDepreciacion);
/* 126:    */       
/* 127:177 */       query.executeUpdate();
/* 128:    */     }
/* 129:180 */     sql = new StringBuilder();
/* 130:181 */     sql.append(" SELECT dd FROM DetalleDepreciacion dd ");
/* 131:182 */     sql.append(" INNER JOIN dd.depreciacion d ");
/* 132:183 */     sql.append(" INNER JOIN d.activoFijo af ");
/* 133:184 */     sql.append(" WHERE dd.anio = :anio AND dd.mes = :mes AND (af.activo = true or :fechaFinMes <= af.fechaBaja) AND af.indicadorDepreciar = true ");
/* 134:185 */     sql.append(" AND d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal AND dd.activo = :activo ");
/* 135:186 */     sql.append(" AND af.idOrganizacion = :idOrganizacion ");
/* 136:    */     
/* 137:188 */     query = this.em.createQuery(sql.toString());
/* 138:189 */     query.setParameter("anio", Integer.valueOf(anio));
/* 139:190 */     query.setParameter("mes", Integer.valueOf(mes));
/* 140:191 */     query.setParameter("activo", Boolean.valueOf(true));
/* 141:192 */     query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(false));
/* 142:193 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 143:194 */     query.setParameter("fechaFinMes", fechaFinMes);
/* 144:    */     
/* 145:196 */     return query.getResultList();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void verificaRegistrosDepreciados(int mes, int anio, int idOrganizacion)
/* 149:    */     throws ExcepcionAS2
/* 150:    */   {
/* 151:208 */     StringBuilder sql = new StringBuilder();
/* 152:209 */     sql.append(" SELECT count(hd.idHistoricoDepreciacion)  ");
/* 153:210 */     sql.append(" FROM HistoricoDepreciacion hd ");
/* 154:211 */     sql.append(" WHERE hd.anio = :anio AND hd.mes = :mes AND hd.idOrganizacion = :idOrganizacion");
/* 155:    */     
/* 156:213 */     Query query = this.em.createQuery(sql.toString());
/* 157:214 */     query.setParameter("anio", Integer.valueOf(anio));
/* 158:215 */     query.setParameter("mes", Integer.valueOf(mes));
/* 159:216 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 160:    */     
/* 161:218 */     Long numeroRegistros = (Long)query.getSingleResult();
/* 162:220 */     if (numeroRegistros.longValue() > 0L) {
/* 163:221 */       throw new ExcepcionAS2("msg_existen_depreciacion_mensual");
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<DetalleInterfazContableProceso> getInterfazContableDepreciacion(HistoricoDepreciacion historicoDepreciacion)
/* 168:    */   {
/* 169:227 */     StringBuilder sql = new StringBuilder();
/* 170:    */     
/* 171:229 */     String mes = FuncionesUtiles.nombreMes(historicoDepreciacion.getMes() - 1);
/* 172:    */     
/* 173:231 */     sql.append(" SELECT new DetalleInterfazContableProcesoDepreciaciones(s.idSucursal, s.nombre, ");
/* 174:232 */     sql.append(" dp.idDepartamento, dp.nombre, ca.idCategoriaActivo, ca.nombre, sa.idSubcategoriaActivo, sa.nombre, ");
/* 175:233 */     sql.append(" af.idActivoFijo, af.nombre, 'Depreciación " + mes + "', 'Depreciación " + mes + "', SUM(dd.valor))");
/* 176:234 */     sql.append(" FROM DetalleDepreciacion dd");
/* 177:235 */     sql.append(" INNER JOIN dd.depreciacion d");
/* 178:236 */     sql.append(" INNER JOIN d.activoFijo af ");
/* 179:237 */     sql.append(" INNER JOIN af.sucursal s ");
/* 180:238 */     sql.append(" INNER JOIN af.departamento dp ");
/* 181:239 */     sql.append(" INNER JOIN af.categoriaActivo ca ");
/* 182:240 */     sql.append(" INNER JOIN af.subcategoriaActivo sa ");
/* 183:241 */     sql.append(" WHERE dd.anio = :anio AND dd.mes = :mes AND (af.activo = true or dd.fecha <= af.fechaBaja)  ");
/* 184:242 */     sql.append(" AND d.indicadorDepreciacionFiscal = true  ");
/* 185:243 */     sql.append(" AND af.idOrganizacion = :idOrganizacion ");
/* 186:244 */     sql.append(" GROUP BY s.idSucursal, s.nombre, ");
/* 187:245 */     sql.append(" dp.idDepartamento, dp.nombre, ca.idCategoriaActivo, ca.nombre, sa.idSubcategoriaActivo, sa.nombre, ");
/* 188:246 */     sql.append(" af.idActivoFijo, af.nombre, concat('Depreciación ',af.nombre) ");
/* 189:    */     
/* 190:248 */     Query query = this.em.createQuery(sql.toString());
/* 191:    */     
/* 192:250 */     query.setParameter("anio", Integer.valueOf(historicoDepreciacion.getAnio()));
/* 193:251 */     query.setParameter("mes", Integer.valueOf(historicoDepreciacion.getMes()));
/* 194:252 */     query.setParameter("idOrganizacion", Integer.valueOf(historicoDepreciacion.getIdOrganizacion()));
/* 195:253 */     return query.getResultList();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<DetalleInterfazContableProceso> getInterfazContableDepreciacionNIIF(HistoricoDepreciacion historicoDepreciacion)
/* 199:    */   {
/* 200:258 */     StringBuilder sql = new StringBuilder();
/* 201:    */     
/* 202:260 */     String mes = FuncionesUtiles.nombreMes(historicoDepreciacion.getMes() - 1);
/* 203:    */     
/* 204:262 */     sql.append(" SELECT new DetalleInterfazContableProcesoDepreciaciones(s.idSucursal, s.nombre, ");
/* 205:263 */     sql.append(" dp.idDepartamento, dp.nombre, ca.idCategoriaActivo, ca.nombre, sa.idSubcategoriaActivo, sa.nombre, ");
/* 206:264 */     sql.append(" af.idActivoFijo, af.nombre, CONCAT('Depreciación ',:mesConcat),CONCAT('Depreciación ',:mesConcat), ");
/* 207:265 */     sql.append(" ROUND(SUM(CASE WHEN dpp=null THEN dd.diferenciaTemporal ELSE dd.diferenciaTemporalRevalorizacion END),2))");
/* 208:266 */     sql.append(" FROM DetalleDepreciacion dd ");
/* 209:267 */     sql.append(" INNER JOIN dd.depreciacion d ");
/* 210:268 */     sql.append(" INNER JOIN d.activoFijo af ");
/* 211:269 */     sql.append(" INNER JOIN af.sucursal s ");
/* 212:270 */     sql.append(" INNER JOIN af.departamento dp ");
/* 213:271 */     sql.append(" INNER JOIN af.categoriaActivo ca ");
/* 214:272 */     sql.append(" INNER JOIN af.subcategoriaActivo sa ");
/* 215:273 */     sql.append(" LEFT JOIN d.depreciacionPadre dpp ");
/* 216:274 */     sql.append(" WHERE dd.anio = :anio AND dd.mes = :mes AND (af.activo = true or dd.fecha <= af.fechaBaja) ");
/* 217:275 */     sql.append(" AND d.indicadorDepreciacionFiscal = false  ");
/* 218:276 */     sql.append(" AND af.idOrganizacion = :idOrganizacion ");
/* 219:277 */     sql.append(" GROUP BY s.idSucursal, s.nombre, ");
/* 220:278 */     sql.append(" dp.idDepartamento, dp.nombre, ca.idCategoriaActivo, ca.nombre, sa.idSubcategoriaActivo, sa.nombre, ");
/* 221:279 */     sql.append(" af.idActivoFijo, af.nombre, CONCAT('Depreciación ',af.nombre) ");
/* 222:    */     
/* 223:281 */     Query query = this.em.createQuery(sql.toString());
/* 224:282 */     query.setParameter("anio", Integer.valueOf(historicoDepreciacion.getAnio()));
/* 225:283 */     query.setParameter("mes", Integer.valueOf(historicoDepreciacion.getMes()));
/* 226:284 */     query.setParameter("idOrganizacion", Integer.valueOf(historicoDepreciacion.getIdOrganizacion()));
/* 227:285 */     query.setParameter("mesConcat", mes);
/* 228:286 */     return query.getResultList();
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.HistoricoDepreciacionDao
 * JD-Core Version:    0.7.0.1
 */