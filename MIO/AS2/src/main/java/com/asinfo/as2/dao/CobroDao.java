/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.ReporteCobros;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.Banco;
/*   7:    */ import com.asinfo.as2.entities.CierreCaja;
/*   8:    */ import com.asinfo.as2.entities.Cobro;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*  10:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  11:    */ import com.asinfo.as2.entities.CuentaContable;
/*  12:    */ import com.asinfo.as2.entities.CuentaContableCruceCuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  14:    */ import com.asinfo.as2.entities.DetalleCobro;
/*  15:    */ import com.asinfo.as2.entities.DetalleCobroFormaCobro;
/*  16:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  17:    */ import com.asinfo.as2.entities.Documento;
/*  18:    */ import com.asinfo.as2.entities.Empresa;
/*  19:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  20:    */ import com.asinfo.as2.entities.FormaPago;
/*  21:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  22:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*  23:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*  24:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  25:    */ import com.asinfo.as2.entities.Recaudador;
/*  26:    */ import com.asinfo.as2.entities.Secuencia;
/*  27:    */ import com.asinfo.as2.entities.Sucursal;
/*  28:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*  29:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  30:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  31:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  32:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  33:    */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*  34:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  35:    */ import java.io.PrintStream;
/*  36:    */ import java.math.BigDecimal;
/*  37:    */ import java.math.RoundingMode;
/*  38:    */ import java.util.ArrayList;
/*  39:    */ import java.util.Date;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import javax.ejb.Stateless;
/*  43:    */ import javax.persistence.EntityManager;
/*  44:    */ import javax.persistence.Query;
/*  45:    */ import javax.persistence.TypedQuery;
/*  46:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  47:    */ import javax.persistence.criteria.CriteriaQuery;
/*  48:    */ import javax.persistence.criteria.Expression;
/*  49:    */ import javax.persistence.criteria.Fetch;
/*  50:    */ import javax.persistence.criteria.JoinType;
/*  51:    */ import javax.persistence.criteria.Predicate;
/*  52:    */ import javax.persistence.criteria.Root;
/*  53:    */ 
/*  54:    */ @Stateless
/*  55:    */ public class CobroDao
/*  56:    */   extends AbstractDaoAS2<Cobro>
/*  57:    */ {
/*  58:    */   public CobroDao()
/*  59:    */   {
/*  60: 64 */     super(Cobro.class);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Cobro cargaDetalle(int idCobro)
/*  64:    */   {
/*  65: 68 */     Cobro cobro = (Cobro)buscarPorId(Integer.valueOf(idCobro));
/*  66:    */     
/*  67: 70 */     cobro.getSucursal().getId();
/*  68:    */     
/*  69: 72 */     cobro.getDocumento().getId();
/*  70: 73 */     cobro.getDocumento().getSecuencia().getId();
/*  71: 74 */     if (cobro.getRecaudador() != null) {
/*  72: 75 */       cobro.getRecaudador().getIdRecaudador();
/*  73:    */     }
/*  74: 77 */     cobro.getDocumento().getTipoAsiento().getId();
/*  75: 78 */     cobro.getEmpresa().getId();
/*  76:    */     
/*  77: 80 */     cobro.getListaDetalleCobro().size();
/*  78: 81 */     for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro())
/*  79:    */     {
/*  80: 82 */       detalleCobro.getId();
/*  81: 83 */       detalleCobro.getCuentaPorCobrar().getId();
/*  82: 84 */       detalleCobro.getCuentaPorCobrar().getFacturaCliente().getId();
/*  83:    */     }
/*  84: 87 */     cobro.getListaDetalleFormaCobro().size();
/*  85: 88 */     for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro())
/*  86:    */     {
/*  87: 89 */       detalleFormaCobro.getFormaPago().getId();
/*  88: 90 */       if (detalleFormaCobro.getGarantiaCliente() != null)
/*  89:    */       {
/*  90: 91 */         detalleFormaCobro.getGarantiaCliente().getIdGarantiaCliente();
/*  91: 93 */         if (detalleFormaCobro.getGarantiaCliente().getBanco() != null) {
/*  92: 94 */           detalleFormaCobro.getGarantiaCliente().getBanco().getIdBanco();
/*  93:    */         }
/*  94:    */       }
/*  95: 98 */       if (detalleFormaCobro.getCuentaBancariaOrganizacion() != null)
/*  96:    */       {
/*  97: 99 */         detalleFormaCobro.getCuentaBancariaOrganizacion().getId();
/*  98:100 */         detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaBancaria();
/*  99:101 */         detalleFormaCobro.getCuentaBancariaOrganizacion().getListaFormaPago().size();
/* 100:103 */         if (detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaBancaria() != null)
/* 101:    */         {
/* 102:104 */           detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaBancaria().getIdCuentaBancaria();
/* 103:105 */           detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getIdBanco();
/* 104:106 */           if (detalleFormaCobro.getCuentaBancariaOrganizacionProtesto() != null)
/* 105:    */           {
/* 106:107 */             detalleFormaCobro.getCuentaBancariaOrganizacionProtesto().getId();
/* 107:108 */             detalleFormaCobro.getCuentaBancariaOrganizacionProtesto().getCuentaBancaria().getId();
/* 108:109 */             detalleFormaCobro.getFormaPagoProtesto().getId();
/* 109:110 */             detalleFormaCobro.getListaCuentaPorCobrar().size();
/* 110:111 */             for (CuentaPorCobrar cuentaPorCobrar : detalleFormaCobro.getListaCuentaPorCobrar()) {
/* 111:112 */               cuentaPorCobrar.getId();
/* 112:    */             }
/* 113:    */           }
/* 114:    */         }
/* 115:117 */         for (FormaPagoCuentaBancariaOrganizacion formaPagoCuenta : detalleFormaCobro.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/* 116:118 */           formaPagoCuenta.getFormaPago().getId();
/* 117:    */         }
/* 118:121 */         for (CuentaContableCruceCuentaBancariaOrganizacion ccccbo : detalleFormaCobro.getCuentaBancariaOrganizacion()
/* 119:122 */           .getListaCuentaContableCruceCuentaBancariaOrganizacion()) {
/* 120:123 */           ccccbo.getId();
/* 121:    */         }
/* 122:    */       }
/* 123:127 */       if (detalleFormaCobro.getBanco() != null) {
/* 124:128 */         detalleFormaCobro.getBanco().getId();
/* 125:    */       }
/* 126:131 */       if (detalleFormaCobro.getCuentaContableFormaCobro() != null) {
/* 127:132 */         detalleFormaCobro.getCuentaContableFormaCobro().getId();
/* 128:    */       }
/* 129:135 */       for (DetalleCobroFormaCobro dcfc : detalleFormaCobro.getListaDetalleCobroFormaCobro()) {
/* 130:136 */         if (dcfc.getDetalleCobro().getCuentaPorCobrar().getFacturaCliente() != null) {
/* 131:137 */           dcfc.getDetalleCobro().getCuentaPorCobrar().getFacturaCliente().getId();
/* 132:    */         }
/* 133:    */       }
/* 134:142 */       if (detalleFormaCobro.getPuntoVenta() != null) {
/* 135:143 */         detalleFormaCobro.getPuntoVenta().getId();
/* 136:    */       }
/* 137:145 */       if (detalleFormaCobro.getTarjetaCredito() != null) {
/* 138:146 */         detalleFormaCobro.getTarjetaCredito().getId();
/* 139:    */       }
/* 140:148 */       if (detalleFormaCobro.getPlanTarjetaCredito() != null) {
/* 141:149 */         detalleFormaCobro.getPlanTarjetaCredito().getId();
/* 142:    */       }
/* 143:151 */       if (detalleFormaCobro.getFormaPago() != null) {
/* 144:152 */         detalleFormaCobro.getFormaPago().getId();
/* 145:    */       }
/* 146:154 */       if (detalleFormaCobro.getCobroTarjeta() != null) {
/* 147:155 */         detalleFormaCobro.getCobroTarjeta().getId();
/* 148:    */       }
/* 149:    */     }
/* 150:160 */     if (cobro.getAsiento() != null) {
/* 151:161 */       cobro.getAsiento().getId();
/* 152:    */     }
/* 153:164 */     if (cobro.getAsientoProtesto() != null) {
/* 154:165 */       cobro.getAsientoProtesto().getId();
/* 155:    */     }
/* 156:168 */     return cobro;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<Cobro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 160:    */   {
/* 161:179 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 162:180 */     CriteriaQuery<Cobro> criteriaQuery = criteriaBuilder.createQuery(Cobro.class);
/* 163:181 */     Root<Cobro> from = criteriaQuery.from(Cobro.class);
/* 164:    */     
/* 165:183 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/* 166:184 */     empresa.fetch("cliente", JoinType.LEFT);
/* 167:185 */     empresa.fetch("proveedor", JoinType.LEFT);
/* 168:186 */     empresa.fetch("empleado", JoinType.LEFT);
/* 169:    */     
/* 170:188 */     from.fetch("documento", JoinType.LEFT);
/* 171:189 */     from.fetch("asientoProtesto", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/* 172:190 */     from.fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/* 173:191 */     from.fetch("sucursal", JoinType.LEFT).fetch("ubicacion", JoinType.LEFT);
/* 174:    */     
/* 175:193 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 176:    */     
/* 177:195 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 178:196 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 179:    */     
/* 180:198 */     CriteriaQuery<Cobro> select = criteriaQuery.select(from);
/* 181:    */     
/* 182:200 */     TypedQuery<Cobro> typedQuery = this.em.createQuery(select);
/* 183:201 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 184:    */     
/* 185:203 */     return typedQuery.getResultList();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<DetalleInterfazContable> getDetalleCobroIC(int idCobro)
/* 189:    */     throws ExcepcionAS2Financiero
/* 190:    */   {
/* 191:    */     try
/* 192:    */     {
/* 193:222 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero, ' ', co.descripcion),  '', -(dc.valor))  FROM DetalleCobro dc  INNER JOIN dc.cuentaPorCobrar cx  INNER JOIN cx.facturaCliente fc  INNER JOIN fc.documento do  INNER JOIN dc.cobro co  INNER JOIN fc.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableCliente cc  WHERE co.idCobro=:idCobro");
/* 194:    */       
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:234 */       query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 206:    */       
/* 207:236 */       return query.getResultList();
/* 208:    */     }
/* 209:    */     catch (IllegalArgumentException e)
/* 210:    */     {
/* 211:239 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableCliente");
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<DetalleInterfazContable> getDetalleFormaCobroIC(int idCobro)
/* 216:    */     throws ExcepcionAS2Financiero
/* 217:    */   {
/* 218:    */     try
/* 219:    */     {
/* 220:257 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(df.cuentaContableFormaCobro.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre, ' #', co.numero,' ', co.descripcion),  df.documentoReferencia,df.formaPago.idFormaPago, (df.valor))  FROM DetalleFormaCobro df  INNER JOIN df.cuentaBancariaOrganizacion cb  INNER JOIN df.cobro co  INNER JOIN co.documento do  INNER JOIN co.empresa em  LEFT JOIN cb.cuentaContableBanco cc  WHERE co.idCobro=:idCobro");
/* 221:    */       
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:265 */       query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 229:    */       
/* 230:267 */       return query.getResultList();
/* 231:    */     }
/* 232:    */     catch (IllegalArgumentException e)
/* 233:    */     {
/* 234:269 */       e.printStackTrace();
/* 235:270 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableCuentaPago");
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<DetalleInterfazContable> getDetalleFormaCobroICProtesto(int idCobro)
/* 240:    */     throws ExcepcionAS2Financiero
/* 241:    */   {
/* 242:    */     try
/* 243:    */     {
/* 244:288 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre, ' #', co.numero,' ', co.descripcion),  df.documentoReferenciaProtesto,df.formaPagoProtesto.idFormaPago, -(df.valor))  FROM DetalleFormaCobro df  INNER JOIN df.cuentaBancariaOrganizacionProtesto cb  INNER JOIN df.cobro co  INNER JOIN co.documento do  INNER JOIN co.empresa em  LEFT JOIN cb.cuentaContableBanco cc  WHERE co.idCobro=:idCobro AND df.indicadorChequeProtestado = true");
/* 245:    */       
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:299 */       query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 256:    */       
/* 257:301 */       return query.getResultList();
/* 258:    */     }
/* 259:    */     catch (IllegalArgumentException e)
/* 260:    */     {
/* 261:303 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableCuentaPago");
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<DetalleInterfazContable> getDetalleCobroICProtesto(int idCobro)
/* 266:    */     throws ExcepcionAS2Financiero
/* 267:    */   {
/* 268:    */     try
/* 269:    */     {
/* 270:321 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT('PROTESTO ',do.nombre, ' #', co.numero,' ', co.descripcion),  df.documentoReferenciaProtesto,df.formaPagoProtesto.idFormaPago,  (case when df.indicadorCargarClienteProtesto = 1 then df.valorProtestado else 0 end + df.valor))  FROM DetalleFormaCobro df  INNER JOIN df.cobro co  INNER JOIN co.documento do  INNER JOIN co.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableCliente cc  WHERE co.idCobro=:idCobro AND df.indicadorChequeProtestado = true");
/* 271:    */       
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:332 */       query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 282:    */       
/* 283:334 */       return query.getResultList();
/* 284:    */     }
/* 285:    */     catch (IllegalArgumentException e)
/* 286:    */     {
/* 287:336 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableCuentaPago");
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<DetalleInterfazContable> getDetalleGastoProtestoBanco(int idCobro)
/* 292:    */     throws ExcepcionAS2Financiero
/* 293:    */   {
/* 294:    */     try
/* 295:    */     {
/* 296:350 */       StringBuffer sql = new StringBuffer();
/* 297:351 */       sql.append("SELECT new DetalleInterfazContable(cc.idCuentaContable, em.nombreFiscal, CONCAT(do.nombre, ' #', co.numero), ");
/* 298:352 */       sql.append(" df.documentoReferenciaProtesto, df.formaPagoProtesto.idFormaPago, -df.valorProtestado) ");
/* 299:353 */       sql.append(" FROM DetalleFormaCobro df ");
/* 300:354 */       sql.append(" INNER JOIN df.cuentaBancariaOrganizacionProtesto cb ");
/* 301:355 */       sql.append(" INNER JOIN df.cobro co ");
/* 302:356 */       sql.append(" INNER JOIN co.documento do ");
/* 303:357 */       sql.append(" INNER JOIN co.empresa em ");
/* 304:358 */       sql.append(" LEFT JOIN cb.cuentaContableBanco cc ");
/* 305:359 */       sql.append(" LEFT OUTER JOIN df.garantiaCliente gc ");
/* 306:360 */       sql.append(" WHERE co.idCobro=:idCobro AND df.indicadorChequeProtestado = true AND df.valorProtestado > 0.00  ");
/* 307:    */       
/* 308:362 */       Query query = this.em.createQuery(sql.toString());
/* 309:363 */       query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 310:    */       
/* 311:365 */       return query.getResultList();
/* 312:    */     }
/* 313:    */     catch (IllegalArgumentException e)
/* 314:    */     {
/* 315:367 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableBancoProtestoProtesto");
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   public List<DetalleInterfazContable> getDetalleGastoProtesto(int idCobro)
/* 320:    */     throws ExcepcionAS2Financiero
/* 321:    */   {
/* 322:    */     try
/* 323:    */     {
/* 324:381 */       StringBuffer sql = new StringBuffer();
/* 325:382 */       sql.append("SELECT new DetalleInterfazContable(cbp.idCuentaContable, em.nombreFiscal, CONCAT(do.nombre, ' #', co.numero), ");
/* 326:383 */       sql.append(" df.documentoReferenciaProtesto,df.formaPagoProtesto.idFormaPago, df.valorProtestado) ");
/* 327:384 */       sql.append(" FROM DetalleFormaCobro df ");
/* 328:385 */       sql.append(" INNER JOIN df.cuentaBancariaOrganizacionProtesto cb ");
/* 329:386 */       sql.append(" LEFT OUTER JOIN cb.cuentaContableGastosProtesto cbp ");
/* 330:387 */       sql.append(" INNER JOIN df.cobro co ");
/* 331:388 */       sql.append(" INNER JOIN co.documento do ");
/* 332:389 */       sql.append(" INNER JOIN co.empresa em ");
/* 333:390 */       sql.append(" LEFT JOIN cb.cuentaContableBanco cc ");
/* 334:391 */       sql.append(" LEFT OUTER JOIN df.garantiaCliente gc ");
/* 335:392 */       sql.append(" WHERE co.idCobro=:idCobro AND df.indicadorChequeProtestado = true AND df.valorProtestado  > 0.00 and df.indicadorCargarClienteProtesto = 0");
/* 336:    */       
/* 337:394 */       Query query = this.em.createQuery(sql.toString());
/* 338:395 */       query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 339:    */       
/* 340:397 */       return query.getResultList();
/* 341:    */     }
/* 342:    */     catch (IllegalArgumentException e)
/* 343:    */     {
/* 344:399 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableGastoProtesto");
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void actualizaEstado(int idCobro, Estado estado)
/* 349:    */   {
/* 350:410 */     Query query = this.em.createQuery("UPDATE Cobro c SET c.estado= :estado WHERE c.idCobro = :idCobro");
/* 351:    */     
/* 352:412 */     query.setParameter("idCobro", Integer.valueOf(idCobro)).setParameter("estado", estado).executeUpdate();
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void actualizarEstado(Integer idCobro, Estado estado)
/* 356:    */   {
/* 357:423 */     Query query = this.em.createQuery("UPDATE Cobro a SET a.estado=:estado WHERE a.idCobro=:idCobro");
/* 358:424 */     query.setParameter("idPago", idCobro);
/* 359:425 */     query.setParameter("estado", estado);
/* 360:426 */     query.executeUpdate();
/* 361:    */   }
/* 362:    */   
/* 363:    */   public List<CierreCaja> obtenerCierreCaja(Cobro cobro)
/* 364:    */   {
/* 365:436 */     String sql = " SELECT c FROM DetalleCierreCaja dcc JOIN dcc.detalleFormaCobro d JOIN d.cobro co JOIN dcc.cierreCaja c  WHERE co.idCobro =:idCobro";
/* 366:    */     
/* 367:438 */     Query query = this.em.createQuery(sql);
/* 368:439 */     query.setParameter("idCobro", Integer.valueOf(cobro.getIdCobro()));
/* 369:    */     
/* 370:441 */     return query.getResultList();
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void actualizaSaldoBloqueadoCXC(CuentaPorCobrar cxc, BigDecimal valor, boolean reverso)
/* 374:    */   {
/* 375:452 */     valor = (reverso ? valor.negate() : valor).setScale(2, RoundingMode.HALF_UP);
/* 376:    */     
/* 377:454 */     StringBuffer sql = new StringBuffer();
/* 378:455 */     sql.append(" UPDATE CuentaPorCobrar cxc SET cxc.valorBloqueado = cxc.valorBloqueado+:valor ");
/* 379:456 */     sql.append(" WHERE cxc=:cxc");
/* 380:457 */     Query query = this.em.createQuery(sql.toString());
/* 381:    */     
/* 382:459 */     query.setParameter("valor", valor);
/* 383:460 */     query.setParameter("cxc", cxc);
/* 384:    */     
/* 385:462 */     query.executeUpdate();
/* 386:    */   }
/* 387:    */   
/* 388:    */   public Cobro buscarPorNumero(int idOrganizacion, String numero)
/* 389:    */   {
/* 390:    */     try
/* 391:    */     {
/* 392:467 */       StringBuffer sql = new StringBuffer();
/* 393:468 */       sql.append("SELECT c");
/* 394:469 */       sql.append(" FROM Cobro c ");
/* 395:470 */       sql.append(" WHERE c.idOrganizacion = :idOrganizacion AND c.numero = :numero");
/* 396:    */       
/* 397:472 */       Query query = this.em.createQuery(sql.toString());
/* 398:473 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 399:474 */       query.setParameter("numero", numero);
/* 400:    */       
/* 401:476 */       List lista = query.getResultList();
/* 402:478 */       if ((lista != null) && (lista.size() > 0)) {
/* 403:479 */         return (Cobro)lista.get(0);
/* 404:    */       }
/* 405:481 */       return null;
/* 406:    */     }
/* 407:    */     catch (Exception e)
/* 408:    */     {
/* 409:484 */       System.out.println(e.getMessage());
/* 410:    */     }
/* 411:485 */     return null;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void liberarCobro(DetalleFormaCobro detalleFormaCobro)
/* 415:    */   {
/* 416:491 */     int a = detalleFormaCobro.getIdDetalleFormaCobro();
/* 417:    */     
/* 418:493 */     StringBuilder sql = new StringBuilder();
/* 419:494 */     sql.append(" DELETE FROM DetalleCierreCaja dcc ");
/* 420:495 */     sql.append(" WHERE dcc.detalleFormaCobro.idDetalleFormaCobro = :a ");
/* 421:496 */     Query query = this.em.createQuery(sql.toString());
/* 422:497 */     query.setParameter("a", Integer.valueOf(a));
/* 423:498 */     query.executeUpdate();
/* 424:    */   }
/* 425:    */   
/* 426:    */   public List<Object[]> reporteSaldosVentas(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, EntidadUsuario vendedor)
/* 427:    */   {
/* 428:515 */     StringBuilder sql = new StringBuilder();
/* 429:516 */     sql.append(" SELECT cxc.idCuentaPorCobrar, do.nombre, e.identificacion, e.nombreFiscal, c.nombre, de.telefono1, f.numero, f.fecha, cxc.fechaVencimiento, ");
/* 430:517 */     sql.append(" concat(v.nombre1,v.nombre2), cxc.valor, cxc.saldo, de.telefono2, CONCAT(ub.direccion1,ub.direccion2,ub.direccion3,ub.direccion4,ub.direccion5)  ");
/* 431:518 */     sql.append(" FROM CuentaPorCobrar cxc ");
/* 432:519 */     sql.append(" JOIN cxc.facturaCliente f ");
/* 433:520 */     if (sucursal != null) {
/* 434:521 */       sql.append(" JOIN f.sucursal s ");
/* 435:    */     }
/* 436:523 */     sql.append(" JOIN f.empresa e ");
/* 437:524 */     sql.append(" JOIN e.cliente cl ");
/* 438:525 */     sql.append(" LEFT JOIN cl.agenteComercial v ");
/* 439:526 */     sql.append(" JOIN f.documento do ");
/* 440:527 */     sql.append(" JOIN f.direccionEmpresa de ");
/* 441:528 */     sql.append(" JOIN de.ciudad c ");
/* 442:529 */     sql.append(" LEFT JOIN de.ubicacion ub ");
/* 443:530 */     sql.append(" WHERE cxc.saldo <> 0 AND cxc.indicadorAnulada = false ");
/* 444:531 */     if (sucursal != null) {
/* 445:532 */       sql.append(" AND  s = :sucursal ");
/* 446:    */     }
/* 447:534 */     if (empresa != null) {
/* 448:535 */       sql.append(" AND  e = :empresa ");
/* 449:    */     }
/* 450:537 */     if (vendedor != null) {
/* 451:538 */       sql.append(" AND  v = :vendedor ");
/* 452:    */     }
/* 453:540 */     sql.append(" AND f.idOrganizacion = :idOrganizacion AND f.estado != :estadoAnulado");
/* 454:541 */     sql.append(" ORDER BY e.nombreFiscal, f.fecha, f.idFacturaCliente, cxc.fechaVencimiento ");
/* 455:    */     
/* 456:543 */     Query query = this.em.createQuery(sql.toString());
/* 457:544 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 458:545 */     if (sucursal != null) {
/* 459:546 */       query.setParameter("sucursal", sucursal);
/* 460:    */     }
/* 461:548 */     if (empresa != null) {
/* 462:549 */       query.setParameter("empresa", empresa);
/* 463:    */     }
/* 464:551 */     if (vendedor != null) {
/* 465:552 */       query.setParameter("vendedor", vendedor);
/* 466:    */     }
/* 467:554 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 468:555 */     return query.getResultList();
/* 469:    */   }
/* 470:    */   
/* 471:    */   public List<Object[]> getChequesPosfechadoDetallado(List<Integer> cxc)
/* 472:    */   {
/* 473:561 */     StringBuilder sql = new StringBuilder();
/* 474:562 */     sql.append(" SELECT cxc.idCuentaPorCobrar, b.nombre,  g.fechaCobro, dfc.documentoReferencia, dcfc.valor ");
/* 475:563 */     sql.append(" FROM DetalleCobroFormaCobro dcfc ");
/* 476:564 */     sql.append(" JOIN dcfc.detalleFormaCobro dfc ");
/* 477:565 */     sql.append(" JOIN dfc.garantiaCliente g ");
/* 478:566 */     sql.append(" JOIN dfc.banco b ");
/* 479:567 */     sql.append(" JOIN dcfc.detalleCobro dc ");
/* 480:568 */     sql.append(" JOIN dc.cuentaPorCobrar cxc ");
/* 481:569 */     sql.append(" JOIN dc.cobro c ");
/* 482:570 */     sql.append(" WHERE cxc.idCuentaPorCobrar in (:cxc) AND g.estadoGarantiaCliente = :estadoRegistrado ");
/* 483:571 */     sql.append(" AND dfc.indicadorChequePosfechado = true AND c.estado != :estadoAnulado ");
/* 484:572 */     Query query = this.em.createQuery(sql.toString());
/* 485:573 */     query.setParameter("cxc", cxc);
/* 486:574 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 487:575 */     query.setParameter("estadoRegistrado", EstadoGarantiaCliente.REGISTRADO);
/* 488:576 */     return query.getResultList();
/* 489:    */   }
/* 490:    */   
/* 491:    */   public List<DetalleCobroFormaCobro> getDetalleCobroFormaCobroProtesto(DetalleFormaCobro detalleFormaCobro)
/* 492:    */   {
/* 493:582 */     StringBuilder sql = new StringBuilder();
/* 494:583 */     sql.append(" SELECT dcfc ");
/* 495:584 */     sql.append(" FROM DetalleCobroFormaCobro dcfc ");
/* 496:585 */     sql.append(" INNER JOIN dcfc.detalleFormaCobro dfc ");
/* 497:586 */     sql.append(" WHERE dcfc.detalleFormaCobro = :detalleFormaCobro ");
/* 498:587 */     Query query = this.em.createQuery(sql.toString());
/* 499:588 */     query.setParameter("detalleFormaCobro", detalleFormaCobro);
/* 500:589 */     return query.getResultList();
/* 501:    */   }
/* 502:    */   
/* 503:    */   public ArrayList<ReporteCobros> getListaCobros(Integer idCliente, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 504:    */   {
/* 505:595 */     StringBuilder sql = new StringBuilder();
/* 506:596 */     sql.append(" SELECT new ReporteCobros (c.idCobro, c.fechaContabilizacion, c.fecha, c.numero, 0, c.valor)");
/* 507:597 */     sql.append(" FROM Cobro c ");
/* 508:598 */     sql.append(" INNER JOIN c.documento d  ");
/* 509:599 */     sql.append(" INNER JOIN c.empresa em ");
/* 510:600 */     sql.append(" WHERE em.idEmpresa = :idCliente ");
/* 511:601 */     sql.append(" AND c.idOrganizacion = :idOrganizacion ");
/* 512:602 */     sql.append(" AND c.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 513:603 */     sql.append(" AND d.documentoBase = :documentoBase ");
/* 514:604 */     sql.append(" AND c.estado=:estado");
/* 515:    */     
/* 516:606 */     Query query = this.em.createQuery(sql.toString());
/* 517:607 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 518:608 */     query.setParameter("fechaDesde", fechaDesde);
/* 519:609 */     query.setParameter("fechaHasta", fechaHasta);
/* 520:610 */     query.setParameter("idCliente", idCliente);
/* 521:611 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 522:612 */     query.setParameter("documentoBase", DocumentoBase.COBRO_CLIENTE);
/* 523:613 */     List<ReporteCobros> lista = query.getResultList();
/* 524:    */     
/* 525:615 */     StringBuilder sql1 = new StringBuilder();
/* 526:616 */     sql1.append(" SELECT new ReporteCobros (ac.idAnticipoCliente, ac.fechaContabilizacion, ac.fecha, ac.numero, 1, (ac.valor - ac.valorDevolucion))");
/* 527:617 */     sql1.append(" FROM AnticipoCliente ac ");
/* 528:618 */     sql1.append(" INNER JOIN ac.empresa e ");
/* 529:619 */     sql1.append(" WHERE ac.estado != :estadoAnulado ");
/* 530:620 */     sql1.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 531:621 */     sql1.append(" AND ac.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 532:622 */     sql1.append(" AND e.idEmpresa  = :idCliente ");
/* 533:623 */     sql1.append(" AND ac.cobro  is null ");
/* 534:624 */     sql1.append(" AND ac.notaCreditoCliente  is null ");
/* 535:625 */     sql1.append(" AND (ac.valor - ac.valorDevolucion)> 0 ");
/* 536:    */     
/* 537:627 */     Query query1 = this.em.createQuery(sql1.toString());
/* 538:628 */     query1.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 539:629 */     query1.setParameter("estadoAnulado", Estado.ANULADO);
/* 540:630 */     query1.setParameter("fechaDesde", fechaDesde);
/* 541:631 */     query1.setParameter("fechaHasta", fechaHasta);
/* 542:632 */     if ((idCliente != null) && (idCliente.intValue() != 0)) {
/* 543:633 */       query1.setParameter("idCliente", idCliente);
/* 544:    */     }
/* 545:635 */     lista.addAll(query1.getResultList());
/* 546:    */     
/* 547:    */ 
/* 548:638 */     return (ArrayList)lista;
/* 549:    */   }
/* 550:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CobroDao
 * JD-Core Version:    0.7.0.1
 */