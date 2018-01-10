/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.BancoAcreedor;
/*   4:    */ import com.asinfo.as2.entities.ConfiguracionExtractoBancario;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.entities.CuentaContableCruceCuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.FormaPago;
/*   8:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.Secuencia;
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
/*  20:    */ import javax.persistence.criteria.Join;
/*  21:    */ import javax.persistence.criteria.JoinType;
/*  22:    */ import javax.persistence.criteria.Path;
/*  23:    */ import javax.persistence.criteria.Predicate;
/*  24:    */ import javax.persistence.criteria.Root;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class CuentaBancariaOrganizacionDao
/*  28:    */   extends AbstractDaoAS2<CuentaBancariaOrganizacion>
/*  29:    */ {
/*  30:    */   public CuentaBancariaOrganizacionDao()
/*  31:    */   {
/*  32: 50 */     super(CuentaBancariaOrganizacion.class);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<CuentaBancariaOrganizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 57 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  38: 58 */     CriteriaQuery<CuentaBancariaOrganizacion> criteriaQuery = criteriaBuilder.createQuery(CuentaBancariaOrganizacion.class);
/*  39: 59 */     Root<CuentaBancariaOrganizacion> from = criteriaQuery.from(CuentaBancariaOrganizacion.class);
/*  40: 60 */     from.fetch("cuentaContableBanco", JoinType.LEFT);
/*  41: 61 */     from.fetch("cuentaContableGastosBancarios", JoinType.LEFT);
/*  42: 62 */     from.fetch("cuentaContableGastosProtesto", JoinType.LEFT);
/*  43: 63 */     Fetch<Object, Object> cuentaBancaria = from.fetch("cuentaBancaria", JoinType.INNER);
/*  44: 64 */     cuentaBancaria.fetch("tipoCuentaBancaria", JoinType.LEFT);
/*  45: 65 */     cuentaBancaria.fetch("pais", JoinType.LEFT);
/*  46: 66 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/*  47:    */     
/*  48: 68 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  49: 69 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  50:    */     
/*  51: 71 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  52:    */     
/*  53: 73 */     CriteriaQuery<CuentaBancariaOrganizacion> select = criteriaQuery.select(from);
/*  54:    */     
/*  55: 75 */     TypedQuery<CuentaBancariaOrganizacion> typedQuery = this.em.createQuery(select);
/*  56: 76 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  57:    */     
/*  58: 78 */     return typedQuery.getResultList();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<CuentaBancariaOrganizacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  62:    */   {
/*  63: 89 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  64: 90 */     CriteriaQuery<CuentaBancariaOrganizacion> criteriaQuery = criteriaBuilder.createQuery(CuentaBancariaOrganizacion.class);
/*  65: 91 */     Root<CuentaBancariaOrganizacion> from = criteriaQuery.from(CuentaBancariaOrganizacion.class);
/*  66: 92 */     from.fetch("cuentaContableBanco", JoinType.LEFT);
/*  67: 93 */     from.fetch("cuentaContableTransitoria", JoinType.LEFT);
/*  68: 94 */     from.fetch("cuentaContableGastosBancarios", JoinType.LEFT);
/*  69: 95 */     from.fetch("cuentaContableDiferencias", JoinType.LEFT);
/*  70:    */     
/*  71: 97 */     Fetch<Object, Object> cuentaBancaria = from.fetch("cuentaBancaria", JoinType.LEFT);
/*  72: 98 */     cuentaBancaria.fetch("tipoCuentaBancaria", JoinType.LEFT);
/*  73: 99 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/*  74:    */     
/*  75:101 */     Fetch<Object, Object> formaPago = from.fetch("listaFormaPago", JoinType.LEFT);
/*  76:102 */     formaPago.fetch("formaPago", JoinType.LEFT);
/*  77:    */     
/*  78:104 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  79:105 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  80:    */     
/*  81:107 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  82:    */     
/*  83:109 */     CriteriaQuery<CuentaBancariaOrganizacion> select = criteriaQuery.select(from).distinct(true);
/*  84:    */     
/*  85:111 */     TypedQuery<CuentaBancariaOrganizacion> typedQuery = this.em.createQuery(select);
/*  86:    */     
/*  87:113 */     return typedQuery.getResultList();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public CuentaBancariaOrganizacion buscarPorCuentaContable(int idCuentaContable)
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:126 */       Query query = this.em.createQuery("SELECT NEW CuentaBancariaOrganizacion(cb.idCuentaBancariaOrganizacion)  FROM CuentaBancariaOrganizacion cb  INNER JOIN cb.cuentaContableBanco cc WHERE cc.idCuentaContable=:idCuentaContable");
/*  95:    */       
/*  96:    */ 
/*  97:129 */       query.setParameter("idCuentaContable", Integer.valueOf(idCuentaContable));
/*  98:    */       
/*  99:131 */       return (CuentaBancariaOrganizacion)query.getSingleResult();
/* 100:    */     }
/* 101:    */     catch (Exception e) {}
/* 102:134 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public CuentaBancariaOrganizacion buscarPorNumeroCuentaBancaria(int idOrganizacion, String numeroCuentaBancaria)
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:141 */       Query query = this.em.createQuery("SELECT cbo  FROM CuentaBancariaOrganizacion cbo  INNER JOIN cbo.cuentaBancaria cb WHERE cb.numero = :numeroCuentaBancaria AND cbo.idOrganizacion = :idOrganizacion ");
/* 110:    */       
/* 111:143 */       query.setParameter("numeroCuentaBancaria", numeroCuentaBancaria);
/* 112:144 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 113:    */       
/* 114:146 */       List<CuentaBancariaOrganizacion> lista = query.getResultList();
/* 115:147 */       if ((lista != null) && (lista.size() > 0)) {
/* 116:148 */         return (CuentaBancariaOrganizacion)lista.get(0);
/* 117:    */       }
/* 118:150 */       return null;
/* 119:    */     }
/* 120:    */     catch (Exception e) {}
/* 121:153 */     return null;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public List<FormaPagoCuentaBancariaOrganizacion> getListaCuentaBancariaPorFormaPago(FormaPago formaPago)
/* 125:    */   {
/* 126:159 */     StringBuilder sql = new StringBuilder();
/* 127:160 */     sql.append(" SELECT fpcbo FROM FormaPagoCuentaBancariaOrganizacion fpcbo ");
/* 128:161 */     sql.append(" JOIN FETCH fpcbo.cuentaBancariaOrganizacion cbo ");
/* 129:162 */     sql.append(" JOIN FETCH cbo.cuentaContableBanco ");
/* 130:163 */     sql.append(" WHERE fpcbo.formaPago = :formaPago ");
/* 131:164 */     Query query = this.em.createQuery(sql.toString());
/* 132:165 */     query.setParameter("formaPago", formaPago);
/* 133:    */     
/* 134:167 */     return query.getResultList();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public CuentaBancariaOrganizacion cargarDetalle(int idCuentaBancariaOrganizacion)
/* 138:    */   {
/* 139:179 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 140:180 */     CriteriaQuery<CuentaBancariaOrganizacion> criteriaQuery = criteriaBuilder.createQuery(CuentaBancariaOrganizacion.class);
/* 141:181 */     Root<CuentaBancariaOrganizacion> cabecera = criteriaQuery.from(CuentaBancariaOrganizacion.class);
/* 142:182 */     cabecera.fetch("cuentaContableBanco", JoinType.LEFT);
/* 143:183 */     cabecera.fetch("cuentaContableBancoPagoCash", JoinType.LEFT);
/* 144:184 */     cabecera.fetch("cuentaContableGastosBancarios", JoinType.LEFT);
/* 145:185 */     cabecera.fetch("cuentaContableGastosProtesto", JoinType.LEFT);
/* 146:186 */     cabecera.fetch("cuentaContableDiferencias", JoinType.LEFT);
/* 147:187 */     cabecera.fetch("cuentaContableTransitoria", JoinType.LEFT);
/* 148:188 */     cabecera.fetch("empresa", JoinType.LEFT);
/* 149:189 */     cabecera.fetch("producto", JoinType.LEFT);
/* 150:190 */     cabecera.fetch("cuentaContableRetencion", JoinType.LEFT);
/* 151:191 */     Fetch<Object, Object> cuentaBancaria = cabecera.fetch("cuentaBancaria", JoinType.INNER);
/* 152:192 */     cuentaBancaria.fetch("tipoCuentaBancaria", JoinType.LEFT);
/* 153:193 */     cuentaBancaria.fetch("pais", JoinType.LEFT);
/* 154:194 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/* 155:    */     
/* 156:196 */     criteriaQuery.where(criteriaBuilder.equal(cabecera.get("idCuentaBancariaOrganizacion"), Integer.valueOf(idCuentaBancariaOrganizacion)));
/* 157:197 */     CriteriaQuery<CuentaBancariaOrganizacion> selectCabecera = criteriaQuery.select(cabecera);
/* 158:198 */     TypedQuery<CuentaBancariaOrganizacion> typedQuery = this.em.createQuery(selectCabecera);
/* 159:    */     
/* 160:200 */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion = (CuentaBancariaOrganizacion)typedQuery.getSingleResult();
/* 161:    */     
/* 162:    */ 
/* 163:203 */     CriteriaBuilder cbDetalle = this.em.getCriteriaBuilder();
/* 164:204 */     CriteriaQuery<FormaPagoCuentaBancariaOrganizacion> cqDetalle = criteriaBuilder.createQuery(FormaPagoCuentaBancariaOrganizacion.class);
/* 165:205 */     Root<FormaPagoCuentaBancariaOrganizacion> detalle = cqDetalle.from(FormaPagoCuentaBancariaOrganizacion.class);
/* 166:    */     
/* 167:207 */     detalle.fetch("formaPago", JoinType.LEFT);
/* 168:208 */     detalle.fetch("secuencia", JoinType.LEFT);
/* 169:209 */     cqDetalle
/* 170:210 */       .where(cbDetalle.equal(detalle.join("cuentaBancariaOrganizacion").get("idCuentaBancariaOrganizacion"), Integer.valueOf(idCuentaBancariaOrganizacion)));
/* 171:211 */     CriteriaQuery<FormaPagoCuentaBancariaOrganizacion> selectDetalle = cqDetalle.select(detalle);
/* 172:212 */     TypedQuery<FormaPagoCuentaBancariaOrganizacion> typedQueryDetale = this.em.createQuery(selectDetalle);
/* 173:213 */     List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago = typedQueryDetale.getResultList();
/* 174:214 */     cuentaBancariaOrganizacion.setListaFormaPago(listaFormaPago);
/* 175:    */     
/* 176:    */ 
/* 177:217 */     CriteriaBuilder cbBancoAcreedor = this.em.getCriteriaBuilder();
/* 178:218 */     CriteriaQuery<BancoAcreedor> cqBancoAcreedor = criteriaBuilder.createQuery(BancoAcreedor.class);
/* 179:219 */     Root<BancoAcreedor> detalleBancoAcreedor = cqBancoAcreedor.from(BancoAcreedor.class);
/* 180:    */     
/* 181:221 */     detalleBancoAcreedor.fetch("banco", JoinType.LEFT);
/* 182:222 */     cqBancoAcreedor.where(cbBancoAcreedor.equal(detalleBancoAcreedor.join("cuentaBancariaOrganizacion").get("idCuentaBancariaOrganizacion"), 
/* 183:223 */       Integer.valueOf(idCuentaBancariaOrganizacion)));
/* 184:224 */     CriteriaQuery<BancoAcreedor> selectDetalleBancoAcreedor = cqBancoAcreedor.select(detalleBancoAcreedor);
/* 185:225 */     TypedQuery<BancoAcreedor> typedQueryDetaleBancoAcreedor = this.em.createQuery(selectDetalleBancoAcreedor);
/* 186:226 */     List<BancoAcreedor> listaBancoAcreedor = typedQueryDetaleBancoAcreedor.getResultList();
/* 187:227 */     cuentaBancariaOrganizacion.setListaBancoAcreedor(listaBancoAcreedor);
/* 188:    */     
/* 189:    */ 
/* 190:    */ 
/* 191:231 */     CriteriaQuery<CuentaContableCruceCuentaBancariaOrganizacion> cqCuentaContableCruceCuentaBancariaOrganizacion = criteriaBuilder.createQuery(CuentaContableCruceCuentaBancariaOrganizacion.class);
/* 192:    */     
/* 193:233 */     Root<CuentaContableCruceCuentaBancariaOrganizacion> fromCuentaContableCruceCuentaBancariaOrganizacion = cqCuentaContableCruceCuentaBancariaOrganizacion.from(CuentaContableCruceCuentaBancariaOrganizacion.class);
/* 194:234 */     fromCuentaContableCruceCuentaBancariaOrganizacion.fetch("cuentaContable", JoinType.LEFT);
/* 195:    */     
/* 196:236 */     Path<Integer> pathIdCuentaBancariaOrganizacion = fromCuentaContableCruceCuentaBancariaOrganizacion.join("cuentaBancariaOrganizacion").get("idCuentaBancariaOrganizacion");
/* 197:237 */     cqCuentaContableCruceCuentaBancariaOrganizacion.where(criteriaBuilder.equal(pathIdCuentaBancariaOrganizacion, Integer.valueOf(idCuentaBancariaOrganizacion)));
/* 198:    */     
/* 199:239 */     CriteriaQuery<CuentaContableCruceCuentaBancariaOrganizacion> selectCuentaContableCruceCuentaBancariaOrganizacion = cqCuentaContableCruceCuentaBancariaOrganizacion.select(fromCuentaContableCruceCuentaBancariaOrganizacion);
/* 200:    */     
/* 201:241 */     List<CuentaContableCruceCuentaBancariaOrganizacion> listaCuentaContableCruceCuentaBancariaOrganizacion = this.em.createQuery(selectCuentaContableCruceCuentaBancariaOrganizacion).getResultList();
/* 202:242 */     cuentaBancariaOrganizacion.setListaCuentaContableCruceCuentaBancariaOrganizacion(listaCuentaContableCruceCuentaBancariaOrganizacion);
/* 203:    */     
/* 204:244 */     return cuentaBancariaOrganizacion;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getReporteCuentaBancaria(int idAsiento)
/* 208:    */   {
/* 209:249 */     String sql = "SELECT cbo.reporte  FROM MovimientoBancario ch  INNER JOIN ch.cuentaBancariaOrganizacion cbo  INNER JOIN ch.detalleAsiento da  INNER JOIN da.asiento a  WHERE a.idAsiento = :idAsiento AND ch.formaPago.indicadorCheque=true";
/* 210:    */     
/* 211:    */ 
/* 212:    */ 
/* 213:253 */     Query query = this.em.createQuery(sql);
/* 214:254 */     query.setMaxResults(1);
/* 215:255 */     query.setParameter("idAsiento", Integer.valueOf(idAsiento));
/* 216:    */     
/* 217:257 */     List<String> lista = query.getResultList();
/* 218:    */     
/* 219:259 */     return !lista.isEmpty() ? (String)lista.get(0) : "";
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Secuencia obtenerSecuenciaPorFormaPago(int idCuentaBancariaOrganizacion, int idFormaPago)
/* 223:    */   {
/* 224:264 */     String sql = " SELECT s  FROM FormaPagoCuentaBancariaOrganizacion fpcbo  JOIN fpcbo.formaPago fp  JOIN fpcbo.cuentaBancariaOrganizacion cbo  JOIN fpcbo.secuencia s WHERE cbo.idCuentaBancariaOrganizacion = :idCuentaBancariaOrganizacion AND fp.idFormaPago = :idFormaPago";
/* 225:    */     
/* 226:    */ 
/* 227:267 */     Query query = this.em.createQuery(sql);
/* 228:    */     
/* 229:269 */     query.setMaxResults(1);
/* 230:270 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(idCuentaBancariaOrganizacion));
/* 231:271 */     query.setParameter("idFormaPago", Integer.valueOf(idFormaPago));
/* 232:    */     
/* 233:273 */     List<Secuencia> lista = query.getResultList();
/* 234:    */     
/* 235:275 */     return !lista.isEmpty() ? (Secuencia)lista.get(0) : null;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public ConfiguracionExtractoBancario getConfiguracionExtractoBancario(CuentaBancariaOrganizacion cuentaBancaria)
/* 239:    */   {
/* 240:280 */     StringBuilder sqll = new StringBuilder();
/* 241:281 */     sqll.append(" SELECT ceb FROM ConfiguracionExtractoBancario ceb");
/* 242:282 */     sqll.append(" WHERE ceb.cuentaBancariaOrganizacion = :cuentaBancaria");
/* 243:    */     
/* 244:284 */     Query query = this.em.createQuery(sqll.toString());
/* 245:285 */     query.setParameter("cuentaBancaria", cuentaBancaria);
/* 246:    */     
/* 247:287 */     return (ConfiguracionExtractoBancario)query.getSingleResult();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<CuentaBancariaOrganizacion> cuentaBancariaOrganizacionPorFormaPago(boolean indicadorRetencionFuente, boolean indicadorRetencionIva, int idOrganizacion)
/* 251:    */   {
/* 252:293 */     StringBuilder sqll = new StringBuilder();
/* 253:294 */     sqll.append(" SELECT cbo FROM FormaPagoCuentaBancariaOrganizacion fpcbo ");
/* 254:295 */     sqll.append(" INNER JOIN  fpcbo.formaPago fp   ");
/* 255:296 */     sqll.append(" INNER JOIN fpcbo.cuentaBancariaOrganizacion cbo   ");
/* 256:297 */     sqll.append(" LEFT JOIN FETCH cbo.listaFormaPago lfp   ");
/* 257:298 */     sqll.append(" JOIN FETCH lfp.formaPago fpa   ");
/* 258:299 */     sqll.append(" WHERE fp.indicadorRetencionFuente = :indicadorRetencionFuente  ");
/* 259:300 */     sqll.append(" AND fp.indicadorRetencionIva = :indicadorRetencionIva  ");
/* 260:301 */     sqll.append(" AND fp.idOrganizacion = :idOrganizacion ");
/* 261:    */     
/* 262:303 */     Query query = this.em.createQuery(sqll.toString());
/* 263:304 */     query.setParameter("indicadorRetencionFuente", Boolean.valueOf(indicadorRetencionFuente));
/* 264:305 */     query.setParameter("indicadorRetencionIva", Boolean.valueOf(indicadorRetencionIva));
/* 265:306 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 266:    */     
/* 267:308 */     return query.getResultList();
/* 268:    */   }
/* 269:    */   
/* 270:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaConCheques(int idOrganizacion)
/* 271:    */   {
/* 272:313 */     StringBuilder sql = new StringBuilder();
/* 273:314 */     sql.append(" SELECT DISTINCT(cbo) ");
/* 274:315 */     sql.append(" FROM FormaPagoCuentaBancariaOrganizacion fpcbo ");
/* 275:316 */     sql.append(" INNER JOIN fpcbo.cuentaBancariaOrganizacion cbo ");
/* 276:317 */     sql.append(" INNER JOIN FETCH cbo.cuentaContableBanco  ");
/* 277:318 */     sql.append(" INNER JOIN FETCH cbo.cuentaBancaria cb ");
/* 278:319 */     sql.append(" INNER JOIN FETCH cb.banco ");
/* 279:320 */     sql.append(" INNER JOIN fpcbo.formaPago fpag ");
/* 280:321 */     sql.append(" WHERE cbo.idOrganizacion = :idOrganizacion ");
/* 281:322 */     sql.append(" AND cbo.activo = true ");
/* 282:323 */     sql.append(" AND fpag.indicadorCheque = true ");
/* 283:324 */     sql.append(" ORDER BY cbo.predeterminado DESC");
/* 284:    */     
/* 285:326 */     Query query = this.em.createQuery(sql.toString());
/* 286:327 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 287:    */     
/* 288:329 */     return query.getResultList();
/* 289:    */   }
/* 290:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CuentaBancariaOrganizacionDao
 * JD-Core Version:    0.7.0.1
 */