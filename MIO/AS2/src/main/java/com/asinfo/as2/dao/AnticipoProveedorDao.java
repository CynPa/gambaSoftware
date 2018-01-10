/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.clases.PagoAnticipoCheque;
/*   6:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  14:    */ import com.asinfo.as2.entities.FormaPago;
/*  15:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  16:    */ import com.asinfo.as2.entities.Secuencia;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import javax.persistence.EntityManager;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ import javax.persistence.TypedQuery;
/*  27:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  28:    */ import javax.persistence.criteria.CriteriaQuery;
/*  29:    */ import javax.persistence.criteria.Expression;
/*  30:    */ import javax.persistence.criteria.Fetch;
/*  31:    */ import javax.persistence.criteria.JoinType;
/*  32:    */ import javax.persistence.criteria.Predicate;
/*  33:    */ import javax.persistence.criteria.Root;
/*  34:    */ 
/*  35:    */ @Stateless
/*  36:    */ public class AnticipoProveedorDao
/*  37:    */   extends AbstractDaoAS2<AnticipoProveedor>
/*  38:    */ {
/*  39:    */   public AnticipoProveedorDao()
/*  40:    */   {
/*  41: 52 */     super(AnticipoProveedor.class);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public AnticipoProveedor cargarDetalle(int idAnticipoProveedor)
/*  45:    */   {
/*  46: 63 */     AnticipoProveedor anticipoProveedor = (AnticipoProveedor)buscarPorId(Integer.valueOf(idAnticipoProveedor));
/*  47: 64 */     anticipoProveedor.getDocumento().getId();
/*  48: 65 */     anticipoProveedor.getDocumento().getSecuencia().getId();
/*  49: 66 */     anticipoProveedor.getDocumento().getTipoAsiento();
/*  50: 67 */     anticipoProveedor.getEmpresa().getId();
/*  51: 68 */     if (anticipoProveedor.getDetalleOrdenPagoProveedor() != null) {
/*  52: 69 */       anticipoProveedor.getDetalleOrdenPagoProveedor().getId();
/*  53:    */     }
/*  54: 72 */     if (anticipoProveedor.getNotaCreditoProveedor() != null)
/*  55:    */     {
/*  56: 73 */       anticipoProveedor.getNotaCreditoProveedor().getId();
/*  57: 74 */       anticipoProveedor.getNotaCreditoProveedor().getDocumento().getId();
/*  58:    */     }
/*  59: 77 */     if (anticipoProveedor.getFormaPago() != null) {
/*  60: 78 */       anticipoProveedor.getFormaPago().getId();
/*  61:    */     }
/*  62: 81 */     if (anticipoProveedor.getCuentaBancariaOrganizacion() != null)
/*  63:    */     {
/*  64: 82 */       anticipoProveedor.getCuentaBancariaOrganizacion().getId();
/*  65: 83 */       anticipoProveedor.getCuentaBancariaOrganizacion().getCuentaBancaria().getId();
/*  66: 84 */       anticipoProveedor.getCuentaBancariaOrganizacion().getListaFormaPago().size();
/*  67: 86 */       for (FormaPagoCuentaBancariaOrganizacion formaPagoCuenta : anticipoProveedor.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/*  68: 87 */         formaPagoCuenta.getFormaPago().getId();
/*  69:    */       }
/*  70:    */     }
/*  71: 91 */     if (anticipoProveedor.getCuentaBancariaOrganizacionDevolucion() != null) {
/*  72: 92 */       anticipoProveedor.getCuentaBancariaOrganizacionDevolucion().getId();
/*  73:    */     }
/*  74: 96 */     if (anticipoProveedor.getAsiento() != null) {
/*  75: 97 */       anticipoProveedor.getAsiento().getId();
/*  76:    */     }
/*  77:101 */     if (anticipoProveedor.getAsientoDevolucion() != null) {
/*  78:102 */       anticipoProveedor.getAsientoDevolucion().getId();
/*  79:    */     }
/*  80:105 */     if (anticipoProveedor.getDocumentoDevolucion() != null) {
/*  81:106 */       anticipoProveedor.getDocumentoDevolucion().getId();
/*  82:    */     }
/*  83:109 */     if (anticipoProveedor.getCuentaBancariaOrganizacionDevolucion() != null) {
/*  84:110 */       anticipoProveedor.getCuentaBancariaOrganizacionDevolucion().getId();
/*  85:    */     }
/*  86:113 */     return anticipoProveedor;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List<AnticipoProveedor> obtenerAnticiposPendientes(int idEmpresa)
/*  90:    */   {
/*  91:118 */     Query query = this.em.createQuery("SELECT a FROM AnticipoProveedor a WHERE a.empresa.idEmpresa=:idEmpresa AND a.saldo > 0");
/*  92:119 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  93:120 */     return query.getResultList();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<AnticipoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  97:    */   {
/*  98:132 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  99:133 */     CriteriaQuery<AnticipoProveedor> criteriaQuery = criteriaBuilder.createQuery(AnticipoProveedor.class);
/* 100:134 */     Root<AnticipoProveedor> from = criteriaQuery.from(AnticipoProveedor.class);
/* 101:    */     
/* 102:136 */     from.fetch("empresa", JoinType.LEFT);
/* 103:137 */     from.fetch("formaPago", JoinType.LEFT);
/* 104:138 */     from.fetch("documento", JoinType.LEFT);
/* 105:139 */     from.fetch("asiento", JoinType.LEFT);
/* 106:140 */     from.fetch("notaCreditoProveedor", JoinType.LEFT);
/* 107:141 */     from.fetch("detalleOrdenPagoProveedor", JoinType.LEFT).fetch("ordenPagoProveedor", JoinType.LEFT);
/* 108:142 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/* 109:143 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/* 110:144 */     Fetch<Object, Object> asientoDevolucion = from.fetch("asientoDevolucion", JoinType.LEFT);
/* 111:145 */     asientoDevolucion.fetch("tipoAsiento", JoinType.LEFT);
/* 112:146 */     from.fetch("sucursal", JoinType.LEFT);
/* 113:    */     
/* 114:148 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 115:    */     
/* 116:150 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 117:151 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 118:    */     
/* 119:153 */     CriteriaQuery<AnticipoProveedor> select = criteriaQuery.select(from);
/* 120:    */     
/* 121:155 */     TypedQuery<AnticipoProveedor> typedQuery = this.em.createQuery(select);
/* 122:156 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 123:    */     
/* 124:158 */     return typedQuery.getResultList();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<DetalleInterfazContable> getCuentaAnticipoProveedor(int idAnticipoProveedor)
/* 128:    */     throws ExcepcionAS2Financiero
/* 129:    */   {
/* 130:    */     try
/* 131:    */     {
/* 132:173 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', ac.numero,' ', ac.descripcion), ac.documentoReferencia ,ac.valor)  FROM AnticipoProveedor ac  INNER JOIN ac.documento do  INNER JOIN ac.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableAnticipoProveedor cc  WHERE ac.idAnticipoProveedor=:idAnticipoProveedor");
/* 133:    */       
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:181 */       query.setParameter("idAnticipoProveedor", Integer.valueOf(idAnticipoProveedor));
/* 141:    */       
/* 142:183 */       return query.getResultList();
/* 143:    */     }
/* 144:    */     catch (IllegalArgumentException e)
/* 145:    */     {
/* 146:186 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableAnticipoProveedor");
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<DetalleInterfazContable> getDetalleFormaPagoAnticipoIC(int idAnticipoProveedor)
/* 151:    */     throws ExcepcionAS2Financiero
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:203 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable( CASE WHEN pcsh IS NULL THEN cc.idCuentaContable ELSE ccbpc.idCuentaContable END   ,em.nombreFiscal, CONCAT(do.nombre,' #', ac.numero,' ', ac.descripcion), ac.documentoReferencia, ac.beneficiario,ac.formaPago.idFormaPago, -ac.valor)  FROM AnticipoProveedor ac INNER JOIN ac.documento do  INNER JOIN ac.cuentaBancariaOrganizacion cb  INNER JOIN cb.cuentaContableBanco cc  INNER JOIN ac.empresa em  LEFT  JOIN ac.pagoCash pcsh  LEFT  JOIN ac.cuentaContableBancoPagoCash ccbpc\t  WHERE ac.idAnticipoProveedor=:idAnticipoProveedor");
/* 156:    */       
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:213 */       query.setParameter("idAnticipoProveedor", Integer.valueOf(idAnticipoProveedor));
/* 166:    */       
/* 167:215 */       return query.getResultList();
/* 168:    */     }
/* 169:    */     catch (IllegalArgumentException e)
/* 170:    */     {
/* 171:218 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableBancos");
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<DetalleInterfazContable> getDetalleFormaPagoDevolucionAnticipoIC(int idAnticipoProveedor)
/* 176:    */   {
/* 177:233 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre,' #', ap.numero), ap.documentoReferenciaDevolucion, '' ,ap.formaPagoDevolucion.idFormaPago, ap.valorDevolucion)  FROM AnticipoProveedor ap INNER JOIN ap.documentoDevolucion do  INNER JOIN ap.cuentaBancariaOrganizacionDevolucion cb  INNER JOIN cb.cuentaContableBanco cc  INNER JOIN ap.empresa em  WHERE ap.idAnticipoProveedor=:idAnticipoProveedor");
/* 178:    */     
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:240 */     query.setParameter("idAnticipoProveedor", Integer.valueOf(idAnticipoProveedor));
/* 185:    */     
/* 186:242 */     return query.getResultList();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<DetalleInterfazContable> getCuentaDevolucionAnticipoProveedor(int idAnticipoProveedor)
/* 190:    */   {
/* 191:255 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', ap.numero), ap.documentoReferenciaDevolucion ,-ap.valorDevolucion)  FROM AnticipoProveedor ap  INNER JOIN ap.documentoDevolucion do  INNER JOIN ap.empresa em  INNER JOIN em.categoriaEmpresa ce  INNER JOIN ce.cuentaContableAnticipoProveedor cc  WHERE ap.idAnticipoProveedor=:idAnticipoProveedor");
/* 192:    */     
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:263 */     query.setParameter("idAnticipoProveedor", Integer.valueOf(idAnticipoProveedor));
/* 200:    */     
/* 201:265 */     return query.getResultList();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public BigDecimal obtenerSaldoAnticipo(int idProveedor, Date fechaHasta)
/* 205:    */   {
/* 206:273 */     BigDecimal resultado = (BigDecimal)this.em.createQuery("SELECT SUM(saldo) FROM AnticipoProveedor a  WHERE a.fecha <= :fechaHasta AND a.estado != :estadoAnulado AND a.empresa.idEmpresa= :idProveedor").setParameter("fechaHasta", fechaHasta).setParameter("idProveedor", Integer.valueOf(idProveedor)).setParameter("estadoAnulado", Estado.ANULADO).getSingleResult();
/* 207:274 */     if (resultado == null) {
/* 208:275 */       resultado = BigDecimal.ZERO;
/* 209:    */     }
/* 210:277 */     return resultado;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void actualizarEstado(Integer idAnticipoProveedor, Estado estado)
/* 214:    */   {
/* 215:288 */     Query query = this.em.createQuery("UPDATE AnticipoProveedor a SET a.estado=:estado WHERE a.idAnticipoProveedor=:idAnticipoProveedor");
/* 216:289 */     query.setParameter("idAnticipoProveedor", idAnticipoProveedor);
/* 217:290 */     query.setParameter("estado", estado);
/* 218:    */     
/* 219:292 */     query.executeUpdate();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void actualizarSaldo(int idAnticipoProveedor, BigDecimal valor)
/* 223:    */   {
/* 224:303 */     Query query = this.em.createQuery("UPDATE AnticipoProveedor a SET a.saldo=a.saldo + :valor  WHERE a.idAnticipoProveedor=:idAnticipoProveedor AND a.estado!=:estadoAnulado");
/* 225:    */     
/* 226:305 */     query.setParameter("idAnticipoProveedor", Integer.valueOf(idAnticipoProveedor));
/* 227:306 */     query.setParameter("valor", valor);
/* 228:307 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 229:308 */     query.executeUpdate();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public BigDecimal obtenerSaldoProveedor(int idEmpresa)
/* 233:    */   {
/* 234:318 */     Query query = this.em.createQuery("SELECT SUM(a.saldo) FROM AnticipoProveedor a  WHERE a.empresa.idEmpresa=:idEmpresa AND a.saldo > 0 AND a.estado!=:estadoAnulado");
/* 235:    */     
/* 236:320 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 237:321 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 238:    */     
/* 239:323 */     Object valor = query.getSingleResult();
/* 240:    */     
/* 241:325 */     return valor == null ? BigDecimal.ZERO : (BigDecimal)valor;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void actualizarChequeEntregadoPago(int idAnticipoProveedor, boolean indicadorEntregaCheque, String usuarioEntregaCheque, Date fechaEntregaCheque)
/* 245:    */   {
/* 246:337 */     Query query = this.em.createQuery("UPDATE AnticipoProveedor p SET p.indicadorChequeEntregado = :indicadorEntregaCheque,  p.fechaEntregaCheque = :fechaEntregaCheque, p.usuarioEntregaCheque = :usuarioEntregaCheque  WHERE p.idAnticipoProveedor = :idAnticipoProveedor");
/* 247:    */     
/* 248:    */ 
/* 249:340 */     query.setParameter("indicadorEntregaCheque", Boolean.valueOf(indicadorEntregaCheque)).setParameter("idAnticipoProveedor", Integer.valueOf(idAnticipoProveedor))
/* 250:341 */       .setParameter("fechaEntregaCheque", fechaEntregaCheque).setParameter("usuarioEntregaCheque", usuarioEntregaCheque).executeUpdate();
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<PagoAnticipoCheque> getPagoConCheques()
/* 254:    */   {
/* 255:351 */     StringBuilder sql = new StringBuilder();
/* 256:352 */     sql.append("SELECT new PagoAnticipoCheque(ap.idAnticipoProveedor, d.documentoBase, d.nombre, ap.numero, ap.fecha, e.nombreFiscal, ap.valor) ");
/* 257:353 */     sql.append(" FROM AnticipoProveedor ap INNER JOIN ap.documento d INNER JOIN ap.empresa e LEFT OUTER JOIN ap.formaPago fp ");
/* 258:354 */     sql.append(" WHERE fp.indicadorCheque = true AND ap.indicadorChequeEntregado = false AND (ap.estado <> :anulado ");
/* 259:355 */     sql.append(" OR ap.estado = :contabilizado)");
/* 260:    */     
/* 261:357 */     Query query = this.em.createQuery(sql.toString()).setParameter("anulado", Estado.ANULADO).setParameter("contabilizado", Estado.CONTABILIZADO);
/* 262:    */     
/* 263:359 */     return query.getResultList();
/* 264:    */   }
/* 265:    */   
/* 266:    */   public BigDecimal saldoAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 267:    */   {
/* 268:370 */     StringBuilder sql = new StringBuilder();
/* 269:371 */     sql.append(" SELECT ap.saldo ");
/* 270:372 */     sql.append(" FROM AnticipoProveedor ap ");
/* 271:373 */     sql.append(" WHERE ap = :anticipoProveedor ");
/* 272:    */     
/* 273:375 */     Query query = this.em.createQuery(sql.toString());
/* 274:376 */     query.setParameter("anticipoProveedor", anticipoProveedor);
/* 275:    */     
/* 276:378 */     return (BigDecimal)query.getSingleResult();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<DetalleInterfazContableProceso> getInterfazAnticipoProveedorDimensiones(List<Integer> listaAnticipoProveedor)
/* 280:    */   {
/* 281:396 */     StringBuilder sql = new StringBuilder();
/* 282:    */     
/* 283:398 */     String descripcion = "";
/* 284:399 */     String grupoDescripcion = "";
/* 285:400 */     if (listaAnticipoProveedor.size() == 1)
/* 286:    */     {
/* 287:401 */       descripcion = "CONCAT(d.nombre,' #', ap.numero,' ',e.nombreFiscal,' ', ap.descripcion)";
/* 288:402 */       grupoDescripcion = "," + descripcion;
/* 289:    */     }
/* 290:    */     else
/* 291:    */     {
/* 292:404 */       descripcion = "''";
/* 293:    */     }
/* 294:407 */     sql.append("SELECT new DetalleInterfazContableProcesoAnticipoProveedor(CASE WHEN nc IS NULL THEN d.idDocumento ELSE dc.idDocumento END, ap.sucursal.idSucursal, ce.idCategoriaEmpresa, e.idEmpresa,");
/* 295:408 */     sql.append(" " + descripcion + ", SUM(ap.valor) )");
/* 296:409 */     sql.append(" FROM AnticipoProveedor ap ");
/* 297:410 */     sql.append(" INNER JOIN ap.documento d ");
/* 298:411 */     sql.append(" INNER JOIN ap.empresa e ");
/* 299:412 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 300:413 */     sql.append(" LEFT JOIN ap.notaCreditoProveedor nc ");
/* 301:414 */     sql.append(" LEFT JOIN nc.documento dc ");
/* 302:415 */     sql.append(" WHERE ap.idAnticipoProveedor in (:listaAnticipoProveedor) ");
/* 303:416 */     sql.append(" GROUP BY CASE WHEN nc IS NULL THEN d.idDocumento ELSE dc.idDocumento END, ap.sucursal.idSucursal, ce.idCategoriaEmpresa, e.idEmpresa" + grupoDescripcion);
/* 304:    */     
/* 305:418 */     sql.append(" HAVING SUM(ap.valor) <> 0");
/* 306:    */     
/* 307:420 */     Query query = this.em.createQuery(sql.toString());
/* 308:421 */     query.setParameter("listaAnticipoProveedor", listaAnticipoProveedor);
/* 309:422 */     return query.getResultList();
/* 310:    */   }
/* 311:    */   
/* 312:    */   public List<AnticipoProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 313:    */   {
/* 314:431 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 315:432 */     CriteriaQuery<AnticipoProveedor> cq = cb.createQuery(this.claseEntidad);
/* 316:433 */     Root<AnticipoProveedor> from = cq.from(this.claseEntidad);
/* 317:    */     
/* 318:    */ 
/* 319:436 */     agregarFiltros(filtros, cb, cq, from);
/* 320:    */     
/* 321:    */ 
/* 322:439 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 323:    */     
/* 324:441 */     return this.em.createQuery(cq).getResultList();
/* 325:    */   }
/* 326:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.AnticipoProveedorDao
 * JD-Core Version:    0.7.0.1
 */