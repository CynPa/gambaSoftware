/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.dao.sri.ConceptoRetencionSRIDao;
/*   5:    */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.CajaChica;
/*   8:    */ import com.asinfo.as2.entities.CompraCajaChica;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.CuentaContable;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.FormaPago;
/*  13:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import javax.persistence.EntityManager;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ import javax.persistence.TypedQuery;
/*  27:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  28:    */ import javax.persistence.criteria.CriteriaQuery;
/*  29:    */ import javax.persistence.criteria.Expression;
/*  30:    */ import javax.persistence.criteria.Fetch;
/*  31:    */ import javax.persistence.criteria.JoinType;
/*  32:    */ import javax.persistence.criteria.Path;
/*  33:    */ import javax.persistence.criteria.Predicate;
/*  34:    */ import javax.persistence.criteria.Root;
/*  35:    */ import javax.persistence.criteria.Selection;
/*  36:    */ 
/*  37:    */ @Stateless
/*  38:    */ public class CajaChicaDao
/*  39:    */   extends AbstractDaoAS2<CajaChica>
/*  40:    */ {
/*  41:    */   @EJB
/*  42:    */   private FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  43:    */   @EJB
/*  44:    */   private ConceptoRetencionSRIDao conceptoRetencionSRIDao;
/*  45:    */   
/*  46:    */   public CajaChicaDao()
/*  47:    */   {
/*  48: 63 */     super(CajaChica.class);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<DetalleInterfazContable> getCajaChicaIC(int idCajaChica, int idOrganizacion)
/*  52:    */     throws ExcepcionAS2Financiero
/*  53:    */   {
/*  54: 76 */     List<DetalleInterfazContable> lista = new ArrayList();
/*  55:    */     
/*  56: 78 */     List<DetalleInterfazContable> ldFuente = new ArrayList();
/*  57: 79 */     List<DetalleInterfazContable> ldFuenteAsumida = new ArrayList();
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62: 84 */     StringBuilder sql = new StringBuilder();
/*  63: 85 */     sql.append("SELECT new DetalleInterfazContable(d.cuentaContable.idCuentaContable,");
/*  64: 86 */     sql.append(" d1.idDimensionContable,d2.idDimensionContable, d3.idDimensionContable, d4.idDimensionContable, d5.idDimensionContable,");
/*  65: 87 */     sql.append(" CASE WHEN em.nombreFiscal IS NULL THEN c.documentoReferencia ELSE em.nombreFiscal END,");
/*  66: 88 */     sql.append(" CONCAT(d.descripcion,' - ',cc.codigo), '', d.valor)");
/*  67: 89 */     sql.append(" FROM DetalleCompraCajaChica d ");
/*  68: 90 */     sql.append(" LEFT JOIN d.dimensionContable1 d1");
/*  69: 91 */     sql.append(" LEFT JOIN d.dimensionContable2 d2");
/*  70: 92 */     sql.append(" LEFT JOIN d.dimensionContable3 d3");
/*  71: 93 */     sql.append(" LEFT JOIN d.dimensionContable4 d4");
/*  72: 94 */     sql.append(" LEFT JOIN d.dimensionContable5 d5");
/*  73: 95 */     sql.append(" INNER JOIN d.compraCajaChica c ");
/*  74: 96 */     sql.append(" INNER JOIN c.cajaChica cc ");
/*  75: 97 */     sql.append(" INNER JOIN cc.documento do ");
/*  76: 98 */     sql.append(" LEFT JOIN c.empresa em ");
/*  77: 99 */     sql.append(" WHERE cc.estado = :estado AND cc.idCajaChica=:idCajaChica");
/*  78:100 */     Query query = this.em.createQuery(sql.toString());
/*  79:101 */     query.setParameter("idCajaChica", Integer.valueOf(idCajaChica));
/*  80:102 */     query.setParameter("estado", Estado.ELABORADO);
/*  81:103 */     lista.addAll(query.getResultList());
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:109 */     sql = new StringBuilder();
/*  88:110 */     sql.append("SELECT new DetalleInterfazContable");
/*  89:111 */     sql.append("(cc.cuentaContableLiquidacion.idCuentaContable,cc.nombre,CONCAT(cc.nombre,' #', cc.codigo), cc.documentoReferencia,cc.beneficiario,cc.formaPago.idFormaPago, -SUM(d.valor)) ");
/*  90:    */     
/*  91:113 */     sql.append(" FROM DetalleCompraCajaChica d ");
/*  92:114 */     sql.append(" INNER JOIN d.compraCajaChica c ");
/*  93:115 */     sql.append(" INNER JOIN c.cajaChica cc ");
/*  94:116 */     sql.append(" INNER JOIN cc.cuentaBancariaOrganizacion cbo ");
/*  95:117 */     sql.append(" WHERE cc.estado = :estado AND cc.idCajaChica=:idCajaChica");
/*  96:118 */     sql.append(" GROUP BY cc.cuentaContableLiquidacion.idCuentaContable,cc.nombre, cc.codigo, cc.documentoReferencia,cc.beneficiario,cc.formaPago.idFormaPago");
/*  97:    */     
/*  98:120 */     query = this.em.createQuery(sql.toString());
/*  99:121 */     query.setParameter("idCajaChica", Integer.valueOf(idCajaChica));
/* 100:122 */     query.setParameter("estado", Estado.ELABORADO);
/* 101:123 */     List<DetalleInterfazContable> listaDetalleInterfazContable = query.getResultList();
/* 102:124 */     DetalleInterfazContable dBanco = null;
/* 103:125 */     if (listaDetalleInterfazContable.size() > 0) {
/* 104:126 */       dBanco = (DetalleInterfazContable)listaDetalleInterfazContable.get(0);
/* 105:    */     } else {
/* 106:128 */       throw new ExcepcionAS2Financiero("msg_error_no_existe_compras_para_la_caja_seleccionada");
/* 107:    */     }
/* 108:    */     try
/* 109:    */     {
/* 110:135 */       sql = new StringBuilder();
/* 111:136 */       sql.append("SELECT new DetalleInterfazContable(ccon.idCuentaContable,em.nombreFiscal,");
/* 112:137 */       sql.append(" CONCAT(do.nombre,' #', c.documentoReferencia), '', -SUM(d.valorRetencion)) ");
/* 113:138 */       sql.append(" FROM DetalleFacturaProveedorSRI d ");
/* 114:139 */       sql.append(" INNER JOIN d.conceptoRetencionSRI cr");
/* 115:140 */       sql.append(" INNER JOIN d.facturaProveedorSRI f ");
/* 116:141 */       sql.append(" INNER JOIN f.compraCajaChica c ");
/* 117:142 */       sql.append(" INNER JOIN c.cajaChica cc ");
/* 118:143 */       sql.append(" INNER JOIN cc.documento do ");
/* 119:144 */       sql.append(" INNER JOIN c.empresa em ");
/* 120:145 */       sql.append(" LEFT JOIN cr.cuentaContable ccon");
/* 121:146 */       sql.append(" WHERE cc.estado = :estado AND cc.idCajaChica=:idCajaChica AND f.indicadorRetencionAsumida = false");
/* 122:147 */       sql.append(" GROUP BY ccon.idCuentaContable, em.nombreFiscal, do.nombre,c.documentoReferencia HAVING SUM(d.valorRetencion) > 0");
/* 123:148 */       query = this.em.createQuery(sql.toString());
/* 124:149 */       query.setParameter("idCajaChica", Integer.valueOf(idCajaChica));
/* 125:150 */       query.setParameter("estado", Estado.ELABORADO);
/* 126:151 */       ldFuente = query.getResultList();
/* 127:152 */       lista.addAll(ldFuente);
/* 128:    */     }
/* 129:    */     catch (IllegalArgumentException e)
/* 130:    */     {
/* 131:155 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableConceptoRetencion");
/* 132:    */     }
/* 133:162 */     BigDecimal valorAsumidoRetenidoFuente = BigDecimal.ZERO;
/* 134:    */     try
/* 135:    */     {
/* 136:164 */       sql = new StringBuilder();
/* 137:165 */       sql.append("SELECT new DetalleInterfazContable(ccon.idCuentaContable,em.nombreFiscal,");
/* 138:166 */       sql.append(" CONCAT(do.nombre,' #', c.documentoReferencia), '', -SUM(d.valorRetencion)) ");
/* 139:167 */       sql.append(" FROM DetalleFacturaProveedorSRI d ");
/* 140:168 */       sql.append(" INNER JOIN d.conceptoRetencionSRI cr");
/* 141:169 */       sql.append(" INNER JOIN d.facturaProveedorSRI f ");
/* 142:170 */       sql.append(" INNER JOIN f.compraCajaChica c ");
/* 143:171 */       sql.append(" INNER JOIN c.cajaChica cc ");
/* 144:172 */       sql.append(" INNER JOIN cc.documento do ");
/* 145:173 */       sql.append(" INNER JOIN c.empresa em ");
/* 146:174 */       sql.append(" LEFT JOIN cr.cuentaContable ccon");
/* 147:175 */       sql.append(" WHERE cc.estado = :estado AND cc.idCajaChica=:idCajaChica AND f.indicadorRetencionAsumida = true");
/* 148:176 */       sql.append(" GROUP BY ccon.idCuentaContable, em.nombreFiscal, do.nombre,c.documentoReferencia HAVING SUM(d.valorRetencion) > 0");
/* 149:177 */       query = this.em.createQuery(sql.toString());
/* 150:178 */       query.setParameter("idCajaChica", Integer.valueOf(idCajaChica));
/* 151:179 */       query.setParameter("estado", Estado.ELABORADO);
/* 152:180 */       ldFuenteAsumida = query.getResultList();
/* 153:181 */       lista.addAll(ldFuenteAsumida);
/* 154:182 */       for (DetalleInterfazContable dFuente : ldFuenteAsumida) {
/* 155:183 */         valorAsumidoRetenidoFuente = valorAsumidoRetenidoFuente.add(dFuente.getValor());
/* 156:    */       }
/* 157:    */     }
/* 158:    */     catch (IllegalArgumentException e)
/* 159:    */     {
/* 160:186 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableConceptoRetencion");
/* 161:    */     }
/* 162:    */     int idCuentaContableRetencionAsumida;
/* 163:    */     try
/* 164:    */     {
/* 165:191 */       BigDecimal valorRetenidoAsumido = valorAsumidoRetenidoFuente.negate();
/* 166:192 */       if (valorRetenidoAsumido.compareTo(BigDecimal.ZERO) != 0)
/* 167:    */       {
/* 168:193 */         idCuentaContableRetencionAsumida = ParametrosSistema.getcuentaRetencionAsumida(idOrganizacion).intValue();
/* 169:194 */         if (idCuentaContableRetencionAsumida == 0) {
/* 170:195 */           throw new Exception();
/* 171:    */         }
/* 172:198 */         DetalleInterfazContable detalle = new DetalleInterfazContable(idCuentaContableRetencionAsumida, "RETENCION ASUMIDA", "RETENCION ASUMIDA", "", valorRetenidoAsumido, Integer.valueOf(idCuentaContableRetencionAsumida));
/* 173:199 */         lista.add(detalle);
/* 174:    */       }
/* 175:    */     }
/* 176:    */     catch (Exception e)
/* 177:    */     {
/* 178:202 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "CuentaRetencionAsumida");
/* 179:    */     }
/* 180:208 */     BigDecimal fuente = BigDecimal.ZERO;
/* 181:209 */     for (DetalleInterfazContable dFuente : ldFuente) {
/* 182:210 */       fuente = fuente.add(dFuente.getValor());
/* 183:    */     }
/* 184:212 */     if (dBanco != null)
/* 185:    */     {
/* 186:213 */       dBanco.setValor(dBanco.getValor().subtract(fuente));
/* 187:    */       
/* 188:215 */       lista.add(dBanco);
/* 189:    */     }
/* 190:217 */     return lista;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<CajaChica> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 194:    */   {
/* 195:231 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 196:232 */     CriteriaQuery<CajaChica> criteriaQuery = criteriaBuilder.createQuery(CajaChica.class);
/* 197:233 */     Root<CajaChica> from = criteriaQuery.from(CajaChica.class);
/* 198:    */     
/* 199:235 */     from.fetch("documento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/* 200:236 */     from.fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/* 201:237 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 202:238 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 203:    */     
/* 204:240 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 205:    */     
/* 206:242 */     CriteriaQuery<CajaChica> select = criteriaQuery.select(from);
/* 207:    */     
/* 208:244 */     TypedQuery<CajaChica> typedQuery = this.em.createQuery(select);
/* 209:245 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 210:    */     
/* 211:247 */     return typedQuery.getResultList();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<CajaChica> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 215:    */   {
/* 216:252 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 217:253 */     CriteriaQuery<CajaChica> criteriaQuery = criteriaBuilder.createQuery(CajaChica.class);
/* 218:254 */     Root<CajaChica> from = criteriaQuery.from(CajaChica.class);
/* 219:    */     
/* 220:256 */     Path<Integer> pathIdCajaChica = from.get("idCajaChica");
/* 221:257 */     Path<String> pathNombre = from.get("nombre");
/* 222:    */     
/* 223:259 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 224:260 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 225:    */     
/* 226:262 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 227:    */     
/* 228:264 */     CriteriaQuery<CajaChica> select = criteriaQuery.multiselect(new Selection[] { pathIdCajaChica, pathNombre });
/* 229:    */     
/* 230:266 */     TypedQuery<CajaChica> typedQuery = this.em.createQuery(select);
/* 231:    */     
/* 232:268 */     return typedQuery.getResultList();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public CajaChica cargarDetalle(int idCajaChica)
/* 236:    */   {
/* 237:273 */     CajaChica cajaChica = (CajaChica)buscarPorId(Integer.valueOf(idCajaChica));
/* 238:    */     
/* 239:275 */     cajaChica.getDocumento().getId();
/* 240:276 */     cajaChica.getDocumento().getTipoAsiento().getId();
/* 241:277 */     if (cajaChica.getCuentaContable() != null) {
/* 242:278 */       cajaChica.getCuentaContable().getId();
/* 243:    */     }
/* 244:280 */     if (cajaChica.getCuentaBancariaOrganizacion() != null) {
/* 245:281 */       cajaChica.getCuentaBancariaOrganizacion().getId();
/* 246:    */     }
/* 247:283 */     if (cajaChica.getFormaPago() != null) {
/* 248:284 */       cajaChica.getFormaPago().getId();
/* 249:    */     }
/* 250:286 */     if (cajaChica.getAsiento() != null)
/* 251:    */     {
/* 252:287 */       cajaChica.getAsiento().getId();
/* 253:288 */       if (cajaChica.getAsiento().getTipoAsiento() != null) {
/* 254:289 */         cajaChica.getAsiento().getTipoAsiento().getId();
/* 255:    */       }
/* 256:    */     }
/* 257:293 */     return cajaChica;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void verificaEmisionRetencion(CajaChica cajaChica)
/* 261:    */     throws ExcepcionAS2
/* 262:    */   {
/* 263:299 */     StringBuilder sql = new StringBuilder();
/* 264:300 */     sql.append(" SELECT fps FROM FacturaProveedorSRI fps ");
/* 265:301 */     sql.append(" INNER JOIN fps.compraCajaChica      ccc ");
/* 266:302 */     sql.append(" INNER JOIN fps.tipoComprobanteSRI   tcsri ");
/* 267:303 */     sql.append(" INNER JOIN ccc.cajaChica\t         cc  ");
/* 268:304 */     sql.append(" WHERE NOT EXISTS (SELECT dfs FROM DetalleFacturaProveedorSRI dfs WHERE fps = dfs.facturaProveedorSRI) ");
/* 269:305 */     sql.append(" AND   cc = :cajaChica ");
/* 270:306 */     sql.append(" AND   ccc.estado != :estadoAnulado ");
/* 271:307 */     sql.append(" AND   tcsri.codigo != :codigo  ");
/* 272:    */     
/* 273:309 */     String codigo = "41";
/* 274:310 */     Query query = this.em.createQuery(sql.toString());
/* 275:311 */     query.setParameter("cajaChica", cajaChica);
/* 276:312 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 277:313 */     query.setParameter("codigo", codigo);
/* 278:315 */     if (!query.getResultList().isEmpty()) {
/* 279:316 */       throw new ExcepcionAS2("msgs_error_existen_facturas_sin_retencion");
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public BigDecimal valorAcumuladoCompraCajaChica(CompraCajaChica compraCajaChica, CajaChica cajaChica)
/* 284:    */   {
/* 285:325 */     StringBuilder sql = new StringBuilder();
/* 286:    */     
/* 287:327 */     sql.append(" SELECT sum( ");
/* 288:328 */     sql.append(" case ");
/* 289:329 */     sql.append(" when coalesce( fpsri.indicadorRetencionAsumida,true) = false then (ccc.valor - (coalesce( ccc.descuentoImpuesto , 0 )) - coalesce( fpsri.totalValorRetenido,0)) ");
/* 290:330 */     sql.append(" else (ccc.valor - (coalesce( ccc.descuentoImpuesto , 0 )))  end ");
/* 291:331 */     sql.append(" ) ");
/* 292:332 */     sql.append(" FROM FacturaProveedorSRI fpsri ");
/* 293:333 */     sql.append(" RIGHT JOIN fpsri.compraCajaChica ccc ");
/* 294:334 */     sql.append(" RIGHT JOIN ccc.cajaChica cc  ");
/* 295:335 */     sql.append(" WHERE cc = :cajaChica ");
/* 296:336 */     sql.append(" AND ccc.estado != :estadoAnulado ");
/* 297:337 */     if ((compraCajaChica != null) && (compraCajaChica.getIdCompraCajaChica() != 0)) {
/* 298:338 */       sql.append(" AND ccc != :compraCajaChica ");
/* 299:    */     }
/* 300:341 */     Query query = this.em.createQuery(sql.toString());
/* 301:342 */     query.setParameter("cajaChica", cajaChica);
/* 302:343 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 303:344 */     if ((compraCajaChica != null) && (compraCajaChica.getIdCompraCajaChica() != 0)) {
/* 304:345 */       query.setParameter("compraCajaChica", compraCajaChica);
/* 305:    */     }
/* 306:348 */     return (BigDecimal)query.getSingleResult() == null ? BigDecimal.ZERO : (BigDecimal)query.getSingleResult();
/* 307:    */   }
/* 308:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CajaChicaDao
 * JD-Core Version:    0.7.0.1
 */