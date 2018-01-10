/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.entities.Asiento;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.CuentaContableCruceCuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.Departamento;
/*   9:    */ import com.asinfo.as2.entities.DetallePagoCuotaPrestamo;
/*  10:    */ import com.asinfo.as2.entities.DetallePrestamo;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.Empleado;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.FormaPago;
/*  15:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  16:    */ import com.asinfo.as2.entities.PagoRol;
/*  17:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  18:    */ import com.asinfo.as2.entities.Prestamo;
/*  19:    */ import com.asinfo.as2.entities.Rubro;
/*  20:    */ import com.asinfo.as2.entities.Secuencia;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  23:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*  24:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ import javax.persistence.EntityManager;
/*  32:    */ import javax.persistence.Query;
/*  33:    */ import javax.persistence.TypedQuery;
/*  34:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  35:    */ import javax.persistence.criteria.CriteriaQuery;
/*  36:    */ import javax.persistence.criteria.Expression;
/*  37:    */ import javax.persistence.criteria.Fetch;
/*  38:    */ import javax.persistence.criteria.JoinType;
/*  39:    */ import javax.persistence.criteria.Predicate;
/*  40:    */ import javax.persistence.criteria.Root;
/*  41:    */ import org.omg.CORBA.Object;
/*  42:    */ 
/*  43:    */ @Stateless
/*  44:    */ public class PrestamoDao
/*  45:    */   extends AbstractDaoAS2<Prestamo>
/*  46:    */ {
/*  47:    */   public PrestamoDao()
/*  48:    */   {
/*  49: 61 */     super(Prestamo.class);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public List<Prestamo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  53:    */   {
/*  54: 70 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  55: 71 */     CriteriaQuery<Prestamo> criteriaQuery = criteriaBuilder.createQuery(Prestamo.class);
/*  56: 72 */     Root<Prestamo> from = criteriaQuery.from(Prestamo.class);
/*  57: 73 */     from.fetch("tipoPrestamo", JoinType.LEFT);
/*  58: 74 */     Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/*  59: 75 */     empleado.fetch("empresa", JoinType.LEFT);
/*  60:    */     
/*  61: 77 */     from.fetch("asiento", JoinType.LEFT);
/*  62: 78 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/*  63: 79 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  64:    */     
/*  65: 81 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  66: 82 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  67: 83 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  68:    */     
/*  69: 85 */     CriteriaQuery<Prestamo> select = criteriaQuery.select(from);
/*  70: 86 */     TypedQuery<Prestamo> typedQuery = this.em.createQuery(select);
/*  71: 87 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  72:    */     
/*  73: 89 */     return typedQuery.getResultList();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Prestamo cargarDetalle(int idPrestamo)
/*  77:    */   {
/*  78: 99 */     Prestamo prestamo = new Prestamo();
/*  79:100 */     prestamo = (Prestamo)buscarPorId(Integer.valueOf(idPrestamo));
/*  80:101 */     prestamo.getId();
/*  81:102 */     prestamo.getListaDetallePrestamo().size();
/*  82:103 */     if (prestamo.getPrestamoPadre() != null) {
/*  83:104 */       prestamo.getPrestamoPadre().getId();
/*  84:    */     }
/*  85:106 */     for (DetallePrestamo detallePrestamo : prestamo.getListaDetallePrestamo())
/*  86:    */     {
/*  87:107 */       detallePrestamo.getId();
/*  88:108 */       for (DetallePagoCuotaPrestamo detallePagoCuotaPrestamo : detallePrestamo.getListaDetallePagoCuotaPrestamo())
/*  89:    */       {
/*  90:109 */         detallePagoCuotaPrestamo.getId();
/*  91:110 */         if (detallePagoCuotaPrestamo.getPagoRolEmpleado() != null)
/*  92:    */         {
/*  93:111 */           detallePagoCuotaPrestamo.getPagoRolEmpleado().getId();
/*  94:112 */           detallePagoCuotaPrestamo.getPagoRolEmpleado().getPagoRol().getId();
/*  95:    */         }
/*  96:    */       }
/*  97:    */     }
/*  98:117 */     prestamo.getEmpleado().getId();
/*  99:118 */     prestamo.getEmpleado().getEmpresa().getId();
/* 100:120 */     if (prestamo.getTipoPrestamo() != null)
/* 101:    */     {
/* 102:121 */       prestamo.getTipoPrestamo().getId();
/* 103:122 */       if (prestamo.getTipoPrestamo().getDocumento() != null)
/* 104:    */       {
/* 105:123 */         prestamo.getTipoPrestamo().getDocumento().getId();
/* 106:124 */         if (prestamo.getTipoPrestamo().getDocumento().getSecuencia() != null) {
/* 107:125 */           prestamo.getTipoPrestamo().getDocumento().getSecuencia().getId();
/* 108:    */         }
/* 109:127 */         if (prestamo.getTipoPrestamo().getDocumento().getTipoAsiento() != null) {
/* 110:128 */           prestamo.getTipoPrestamo().getDocumento().getTipoAsiento().getId();
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:132 */     if (prestamo.getCuentaContable() != null) {
/* 115:133 */       prestamo.getCuentaContable().getId();
/* 116:    */     }
/* 117:136 */     if (prestamo.getAsiento() != null) {
/* 118:137 */       prestamo.getAsiento().getId();
/* 119:    */     }
/* 120:139 */     if (prestamo.getCuentaBancariaOrganizacion() != null)
/* 121:    */     {
/* 122:140 */       prestamo.getCuentaBancariaOrganizacion().getId();
/* 123:141 */       prestamo.getCuentaBancariaOrganizacion().getListaFormaPago().size();
/* 124:142 */       for (FormaPagoCuentaBancariaOrganizacion formaPagoCuentaBancariaOrganizacion : prestamo.getCuentaBancariaOrganizacion()
/* 125:143 */         .getListaFormaPago())
/* 126:    */       {
/* 127:144 */         formaPagoCuentaBancariaOrganizacion.getId();
/* 128:145 */         formaPagoCuentaBancariaOrganizacion.getFormaPago().getId();
/* 129:    */       }
/* 130:149 */       for (CuentaContableCruceCuentaBancariaOrganizacion cuentaContableCruceCuentaBancariaOrganizacion : prestamo.getCuentaBancariaOrganizacion()
/* 131:150 */         .getListaCuentaContableCruceCuentaBancariaOrganizacion())
/* 132:    */       {
/* 133:151 */         cuentaContableCruceCuentaBancariaOrganizacion.getId();
/* 134:153 */         if (cuentaContableCruceCuentaBancariaOrganizacion.getCuentaContable() != null) {
/* 135:154 */           cuentaContableCruceCuentaBancariaOrganizacion.getCuentaContable().getId();
/* 136:    */         }
/* 137:    */       }
/* 138:158 */       if (prestamo.getCuentaBancariaOrganizacion().getCuentaContableBanco() != null) {
/* 139:159 */         prestamo.getCuentaBancariaOrganizacion().getCuentaContableBanco().getId();
/* 140:    */       }
/* 141:    */     }
/* 142:164 */     return prestamo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<DetalleInterfazContable> getPrestamoIC(int idPrestamo)
/* 146:    */     throws ExcepcionAS2Financiero
/* 147:    */   {
/* 148:176 */     List<DetalleInterfazContable> lista = new ArrayList();
/* 149:    */     
/* 150:    */ 
/* 151:179 */     String sql = "SELECT new DetalleInterfazContable(p.cuentaContable.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre), p.documentoReferencia, p.beneficiario,p.formaPago.idFormaPago, -p.monto)  FROM Prestamo p INNER JOIN p.tipoPrestamo tp INNER JOIN tp.documento do  INNER JOIN p.cuentaBancariaOrganizacion cb  LEFT JOIN cb.cuentaContableBanco cc  INNER JOIN p.empleado e \tINNER JOIN e.empresa em WHERE p.idPrestamo=:idPrestamo";
/* 152:    */     
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:188 */     Query query = this.em.createQuery(sql);
/* 161:189 */     query.setParameter("idPrestamo", Integer.valueOf(idPrestamo));
/* 162:    */     try
/* 163:    */     {
/* 164:191 */       lista.addAll(query.getResultList());
/* 165:    */     }
/* 166:    */     catch (IllegalArgumentException e)
/* 167:    */     {
/* 168:194 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableBanco");
/* 169:    */     }
/* 170:197 */     sql = "SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre), p.documentoReferencia, p.beneficiario,p.formaPago.idFormaPago, p.monto)  FROM Prestamo p INNER JOIN p.tipoPrestamo tp  INNER JOIN tp.documento do  LEFT JOIN tp.cuentaContable cc  INNER JOIN p.empleado e \tINNER JOIN e.empresa em WHERE p.idPrestamo=:idPrestamo";
/* 171:    */     
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:205 */     query = this.em.createQuery(sql);
/* 179:206 */     query.setParameter("idPrestamo", Integer.valueOf(idPrestamo));
/* 180:    */     try
/* 181:    */     {
/* 182:208 */       lista.addAll(query.getResultList());
/* 183:    */     }
/* 184:    */     catch (IllegalArgumentException e)
/* 185:    */     {
/* 186:211 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableTipoPrestamo");
/* 187:    */     }
/* 188:213 */     return lista;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<DetallePrestamo> obtenerCuotasPrestamoPorEmpleadoRubroFecha(int idEmpleado, Rubro rubro, Date fechaDesde, Date fechaHasta)
/* 192:    */   {
/* 193:218 */     StringBuilder sql = new StringBuilder();
/* 194:219 */     sql.append(" SELECT dp ");
/* 195:220 */     sql.append(" FROM DetallePrestamo dp ");
/* 196:221 */     sql.append(" JOIN FETCH dp.prestamo p ");
/* 197:222 */     sql.append(" JOIN FETCH p.empleado e ");
/* 198:223 */     sql.append(" JOIN FETCH p.tipoPrestamo tp ");
/* 199:224 */     sql.append(" JOIN FETCH tp.rubro r ");
/* 200:225 */     sql.append(" WHERE r = :rubro ");
/* 201:226 */     sql.append(" AND e.idEmpleado = :idEmpleado ");
/* 202:227 */     sql.append(" AND p.estado != :estadoAnulado ");
/* 203:228 */     sql.append(" AND p.estado != :estadoElaborado ");
/* 204:229 */     sql.append(" AND coalesce(dp.fechaDescuento,dp.fechaCuota) between :fechaDesde and :fechaHasta");
/* 205:    */     
/* 206:231 */     Query query = this.em.createQuery(sql.toString());
/* 207:232 */     query.setParameter("rubro", rubro);
/* 208:233 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 209:234 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 210:235 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 211:236 */     query.setParameter("fechaDesde", fechaDesde);
/* 212:237 */     query.setParameter("fechaHasta", fechaHasta);
/* 213:    */     
/* 214:239 */     return query.getResultList();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public List reporteTablaAmortizacion(Prestamo prestamo)
/* 218:    */   {
/* 219:244 */     String sql = "\tSELECT e.codigo,e.identificacion,CONCAT(em.apellidos,' ',em.nombres),\tp.fechaSolicitud,p.fechaAprobacion,p.fechaInicioDescuento, p.monto,\tp.interes,p.plazo,dp.numeroCuota,dp.cuota,dp.capitalCuota,dp.interesCuota,dp.fechaCuota\tFROM DetallePrestamo dp\tINNER JOIN dp.prestamo p\tINNER JOIN p.empleado em\tINNER JOIN em.empresa e WHERE p=:prestamo";
/* 220:    */     
/* 221:    */ 
/* 222:    */ 
/* 223:248 */     Query query = this.em.createQuery(sql);
/* 224:249 */     query.setParameter("prestamo", prestamo);
/* 225:250 */     return query.getResultList();
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List getReportePrestamoEmpleado(int idOrganizacion, TipoPrestamo tipoPrestamo, Departamento departamento, Empleado empleado, Sucursal sucursal, boolean indicadorActivos, Date fechaDesde, Date fechaHasta)
/* 229:    */   {
/* 230:256 */     StringBuilder sql = new StringBuilder();
/* 231:257 */     sql.append(" SELECT tp.nombre, p.idPrestamo, p.fechaSolicitud, p.fechaAprobacion, p.fechaInicioDescuento, p.monto, p.plazo, ");
/* 232:258 */     sql.append(" em.identificacion, e.nombres, e.apellidos, dp.numeroCuota, dp.fechaCuota, ");
/* 233:259 */     sql.append(" dp.capitalCuota, CAST(COALESCE(dpcp.capitalCuota,0.00) as double), ");
/* 234:260 */     sql.append(" CAST(dp.capitalCuota as double)-CAST(COALESCE(dpcp.capitalCuota,0.00) as double) ");
/* 235:261 */     sql.append(" FROM DetallePagoCuotaPrestamo dpcp ");
/* 236:262 */     sql.append(" RIGHT JOIN dpcp.detallePrestamo dp ");
/* 237:263 */     sql.append(" INNER JOIN dp.prestamo p ");
/* 238:264 */     sql.append(" INNER JOIN p.empleado e ");
/* 239:265 */     sql.append(" INNER JOIN p.tipoPrestamo tp ");
/* 240:266 */     sql.append(" INNER JOIN e.empresa em ");
/* 241:267 */     sql.append(" INNER JOIN e.departamento d ");
/* 242:268 */     sql.append(" WHERE p.estado >= :estado ");
/* 243:269 */     sql.append(" AND p.idOrganizacion = :idOrganizacion ");
/* 244:270 */     sql.append(" AND  p.fechaSolicitud between :fechaDesde and :fechaHasta ");
/* 245:271 */     if (indicadorActivos)
/* 246:    */     {
/* 247:272 */       sql.append(" AND EXISTS ( ");
/* 248:273 */       sql.append(" \t\tSELECT 1 FROM DetallePagoCuotaPrestamo dpcp1 ");
/* 249:274 */       sql.append(" \t\tRIGHT JOIN dpcp1.detallePrestamo dp1 ");
/* 250:275 */       sql.append(" \t\tINNER JOIN dp1.prestamo p1 ");
/* 251:276 */       sql.append(" \t\tWHERE p1 = p ");
/* 252:277 */       sql.append(" \t\tAND (dp1.fechaCuota >= :fechaHasta OR dpcp1 IS NULL)");
/* 253:278 */       sql.append(" )");
/* 254:    */     }
/* 255:280 */     if ((null != tipoPrestamo) && (tipoPrestamo.getId() != 0)) {
/* 256:281 */       sql.append(" AND tp.idTipoPrestamo = :idTipoPrestamo ");
/* 257:    */     }
/* 258:283 */     if ((null != departamento) && (departamento.getId() != 0)) {
/* 259:284 */       sql.append(" AND d.idDepartamento = :idDepartamento ");
/* 260:    */     }
/* 261:286 */     if ((null != empleado) && (empleado.getId() != 0)) {
/* 262:287 */       sql.append(" AND e.idEmpleado = :idEmpleado ");
/* 263:    */     }
/* 264:289 */     if ((null != sucursal) && (sucursal.getId() != 0)) {
/* 265:290 */       sql.append(" AND em.idSucursal = :idSucursal ");
/* 266:    */     }
/* 267:292 */     sql.append(" ORDER BY em.identificacion, e.apellidos, e.nombres, p.idPrestamo, p.fechaInicioDescuento, dp.numeroCuota, tp.nombre ");
/* 268:    */     
/* 269:294 */     Query query = this.em.createQuery(sql.toString());
/* 270:295 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 271:296 */     query.setParameter("estado", Estado.APROBADO);
/* 272:297 */     query.setParameter("fechaDesde", fechaDesde);
/* 273:298 */     query.setParameter("fechaHasta", fechaHasta);
/* 274:300 */     if ((null != tipoPrestamo) && (tipoPrestamo.getId() != 0)) {
/* 275:301 */       query.setParameter("idTipoPrestamo", Integer.valueOf(tipoPrestamo.getId()));
/* 276:    */     }
/* 277:303 */     if ((null != departamento) && (departamento.getId() != 0)) {
/* 278:304 */       query.setParameter("idDepartamento", Integer.valueOf(departamento.getId()));
/* 279:    */     }
/* 280:306 */     if ((null != empleado) && (empleado.getId() != 0)) {
/* 281:307 */       query.setParameter("idEmpleado", Integer.valueOf(empleado.getIdEmpleado()));
/* 282:    */     }
/* 283:309 */     if ((null != sucursal) && (sucursal.getId() != 0)) {
/* 284:310 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 285:    */     }
/* 286:313 */     return query.getResultList();
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List getSaldoPrestamosEmpleado(int idOrganizacion, Empleado empleado)
/* 290:    */   {
/* 291:318 */     StringBuilder sql = new StringBuilder();
/* 292:319 */     sql.append(" SELECT p.idPrestamo, SUM(dp.capitalCuota - COALESCE(dpcp.capitalCuota,0.00)) ");
/* 293:320 */     sql.append(" FROM DetallePagoCuotaPrestamo dpcp ");
/* 294:321 */     sql.append(" RIGHT JOIN dpcp.detallePrestamo dp ");
/* 295:322 */     sql.append(" INNER JOIN dp.prestamo p ");
/* 296:323 */     sql.append(" INNER JOIN p.empleado e ");
/* 297:324 */     sql.append(" WHERE p.estado >= :estado ");
/* 298:325 */     sql.append(" AND p.idOrganizacion = :idOrganizacion ");
/* 299:326 */     sql.append(" AND EXISTS ( ");
/* 300:327 */     sql.append("  \t\tSELECT 1 FROM DetallePagoCuotaPrestamo dpcp1 ");
/* 301:328 */     sql.append("  \t\tRIGHT JOIN dpcp1.detallePrestamo dp1 ");
/* 302:329 */     sql.append("  \t\tINNER JOIN dp1.prestamo p1 ");
/* 303:330 */     sql.append("  \t\tWHERE p1 = p ");
/* 304:331 */     sql.append("  \t\tGROUP BY p1 ");
/* 305:332 */     sql.append("  \t\tHAVING SUM(dp1.capitalCuota - COALESCE(dpcp1.capitalCuota,0.00)) > 0 ");
/* 306:333 */     sql.append(" ) ");
/* 307:334 */     if ((null != empleado) && (empleado.getId() != 0)) {
/* 308:335 */       sql.append(" AND e.idEmpleado = :idEmpleado ");
/* 309:    */     }
/* 310:337 */     sql.append(" GROUP BY p ");
/* 311:    */     
/* 312:339 */     Query query = this.em.createQuery(sql.toString());
/* 313:340 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 314:341 */     query.setParameter("estado", Estado.APROBADO);
/* 315:342 */     if ((null != empleado) && (empleado.getId() != 0)) {
/* 316:343 */       query.setParameter("idEmpleado", Integer.valueOf(empleado.getIdEmpleado()));
/* 317:    */     }
/* 318:346 */     return query.getResultList();
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void eliminarDetallesPrestamo(Prestamo prestamo)
/* 322:    */   {
/* 323:350 */     String sql = " DELETE FROM DetallePrestamo dp WHERE dp.prestamo = :prestamo ";
/* 324:351 */     Query query = this.em.createQuery(sql);
/* 325:352 */     query.setParameter("prestamo", prestamo);
/* 326:353 */     query.executeUpdate();
/* 327:    */   }
/* 328:    */   
/* 329:    */   public List<DetallePagoCuotaPrestamo> obtenerCuotasPagadasPrestamo(Prestamo prestamo)
/* 330:    */   {
/* 331:358 */     StringBuilder sb = new StringBuilder();
/* 332:359 */     sb.append(" SELECT dpcp FROM DetallePagoCuotaPrestamo dpcp ");
/* 333:360 */     sb.append(" JOIN dpcp.detallePrestamo dp ");
/* 334:361 */     sb.append(" WHERE dp.prestamo = :prestamo ");
/* 335:362 */     Query query = this.em.createQuery(sb.toString());
/* 336:363 */     query.setParameter("prestamo", prestamo);
/* 337:    */     
/* 338:365 */     return query.getResultList();
/* 339:    */   }
/* 340:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PrestamoDao
 * JD-Core Version:    0.7.0.1
 */