/*   1:    */ package com.asinfo.as2.compras.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaDebitoProveedor;
/*   6:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioVerificadorCompras;
/*   7:    */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*   8:    */ import com.asinfo.as2.dao.DetalleFacturaProveedorDao;
/*   9:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*  10:    */ import com.asinfo.as2.dao.GastoProductoFacturaProveedorDao;
/*  11:    */ import com.asinfo.as2.dao.ImpuestoProductoFacturaProveedorDao;
/*  12:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*  14:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  15:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  16:    */ import com.asinfo.as2.entities.Asiento;
/*  17:    */ import com.asinfo.as2.entities.CondicionPago;
/*  18:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  19:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  20:    */ import com.asinfo.as2.entities.Documento;
/*  21:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  22:    */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*  23:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*  24:    */ import com.asinfo.as2.entities.Organizacion;
/*  25:    */ import com.asinfo.as2.entities.Producto;
/*  26:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  27:    */ import com.asinfo.as2.entities.Sucursal;
/*  28:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  29:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  30:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  31:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  33:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  34:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  35:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  36:    */ import com.asinfo.as2.util.AppUtil;
/*  37:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  38:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  39:    */ import java.math.BigDecimal;
/*  40:    */ import java.math.RoundingMode;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Calendar;
/*  43:    */ import java.util.Date;
/*  44:    */ import java.util.Iterator;
/*  45:    */ import java.util.List;
/*  46:    */ import java.util.Map;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.ejb.SessionContext;
/*  49:    */ import javax.ejb.Stateless;
/*  50:    */ import org.apache.log4j.Logger;
/*  51:    */ 
/*  52:    */ @Stateless
/*  53:    */ public class ServicioNotaDebitoProveedorImpl
/*  54:    */   extends AbstractServicioAS2Financiero
/*  55:    */   implements ServicioNotaDebitoProveedor
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = -2296561563805286367L;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioVerificadorCompras servicioVerificadorCompras;
/*  60:    */   @EJB
/*  61:    */   private transient InventarioProductoDao inventarioProductoDao;
/*  62:    */   @EJB
/*  63:    */   private transient ImpuestoProductoFacturaProveedorDao impuestoProductoFacturaProveedorDao;
/*  64:    */   @EJB
/*  65:    */   private transient CuentaPorPagarDao cuentaPorPagarDao;
/*  66:    */   @EJB
/*  67:    */   private transient DetalleFacturaProveedorDao detalleFacturaProveedorDao;
/*  68:    */   @EJB
/*  69:    */   private transient GastoProductoFacturaProveedorDao gastoProductoFacturaProveedorDao;
/*  70:    */   @EJB
/*  71:    */   private transient FacturaProveedorDao facturaProveedorDao;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioSecuencia servicioSecuencia;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioPeriodo servicioPeriodo;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioDocumento servicioDocumento;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioCondicionPago servicioCondicionPago;
/*  80:    */   
/*  81:    */   public FacturaProveedor guardar(FacturaProveedor notaDebito)
/*  82:    */     throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:105 */       validar(notaDebito);
/*  87:    */       
/*  88:    */ 
/*  89:108 */       this.servicioVerificadorCompras.actualizarCantidadPorFacturar(notaDebito, true);
/*  90:110 */       if ((notaDebito.getNumero() == null) || (notaDebito.getNumero().isEmpty())) {
/*  91:111 */         cargarSecuencia(notaDebito, null);
/*  92:    */       }
/*  93:113 */       for (Iterator localIterator1 = notaDebito.getListaDetalleFacturaProveedor().iterator(); localIterator1.hasNext();)
/*  94:    */       {
/*  95:113 */         dfp = (DetalleFacturaProveedor)localIterator1.next();
/*  96:116 */         for (ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor : dfp.getListaImpuestoProductoFacturaProveedor()) {
/*  97:117 */           this.impuestoProductoFacturaProveedorDao.guardar(impuestoProductoFacturaProveedor);
/*  98:    */         }
/*  99:121 */         for (GastoProductoFacturaProveedor gasto : dfp.getListaGastoProductoFacturaProveedor())
/* 100:    */         {
/* 101:122 */           if (!dfp.getProducto().isTraIndicadorServicio()) {
/* 102:123 */             gasto.setEliminado(true);
/* 103:    */           }
/* 104:126 */           this.gastoProductoFacturaProveedorDao.guardar(gasto);
/* 105:    */         }
/* 106:129 */         this.detalleFacturaProveedorDao.guardar(dfp);
/* 107:    */       }
/* 108:    */       DetalleFacturaProveedor dfp;
/* 109:133 */       BigDecimal bono = notaDebito.getBono();
/* 110:134 */       for (CuentaPorPagar cuentaPorPagar : notaDebito.getListaCuentaPorPagar())
/* 111:    */       {
/* 112:135 */         cuentaPorPagar.setSaldo(cuentaPorPagar.getValor());
/* 113:138 */         if (bono.compareTo(BigDecimal.ZERO) > 0) {
/* 114:139 */           if (bono.compareTo(cuentaPorPagar.getSaldo()) > 0)
/* 115:    */           {
/* 116:140 */             bono = bono.subtract(cuentaPorPagar.getSaldo());
/* 117:141 */             cuentaPorPagar.setSaldo(BigDecimal.ZERO);
/* 118:    */           }
/* 119:    */           else
/* 120:    */           {
/* 121:143 */             cuentaPorPagar.setSaldo(cuentaPorPagar.getSaldo().subtract(bono));
/* 122:144 */             bono = BigDecimal.ZERO;
/* 123:    */           }
/* 124:    */         }
/* 125:148 */         this.cuentaPorPagarDao.guardar(cuentaPorPagar);
/* 126:    */       }
/* 127:152 */       this.facturaProveedorDao.guardar(notaDebito);
/* 128:    */       
/* 129:    */ 
/* 130:155 */       this.servicioVerificadorCompras.actualizarCantidadPorFacturar(notaDebito, false);
/* 131:    */       
/* 132:    */ 
/* 133:158 */       this.servicioSecuencia.actualizarSecuencia(notaDebito.getDocumento().getSecuencia(), notaDebito.getNumero());
/* 134:172 */       if (!notaDebito.isIndicadorSaldoInicial()) {
/* 135:173 */         contabilizar(notaDebito);
/* 136:    */       }
/* 137:176 */       return notaDebito;
/* 138:    */     }
/* 139:    */     catch (ExcepcionAS2Compras e)
/* 140:    */     {
/* 141:179 */       this.context.setRollbackOnly();
/* 142:180 */       e.printStackTrace();
/* 143:181 */       throw e;
/* 144:    */     }
/* 145:    */     catch (ExcepcionAS2Financiero e)
/* 146:    */     {
/* 147:183 */       this.context.setRollbackOnly();
/* 148:184 */       e.printStackTrace();
/* 149:185 */       throw e;
/* 150:    */     }
/* 151:    */     catch (ExcepcionAS2 e)
/* 152:    */     {
/* 153:187 */       this.context.setRollbackOnly();
/* 154:188 */       e.printStackTrace();
/* 155:189 */       throw e;
/* 156:    */     }
/* 157:    */     catch (Exception e)
/* 158:    */     {
/* 159:191 */       this.context.setRollbackOnly();
/* 160:192 */       e.printStackTrace();
/* 161:193 */       LOG.error(e);
/* 162:194 */       throw new ExcepcionAS2(e);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   private void validar(FacturaProveedor facturaProveedor)
/* 167:    */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero
/* 168:    */   {
/* 169:209 */     this.servicioPeriodo.buscarPorFecha(facturaProveedor.getFecha(), facturaProveedor.getIdOrganizacion(), facturaProveedor.getDocumento().getDocumentoBase());
/* 170:212 */     if (verificaExistenciaNumero(facturaProveedor) > 0L) {
/* 171:213 */       throw new ExcepcionAS2Compras("msg_error_numero_duplicado", " " + facturaProveedor.getNumero());
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void eliminar(FacturaProveedor notaDebito)
/* 176:    */     throws ExcepcionAS2
/* 177:    */   {
/* 178:225 */     this.facturaProveedorDao.eliminar(notaDebito);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public FacturaProveedor buscarPorId(int idNotaDebito)
/* 182:    */     throws ExcepcionAS2
/* 183:    */   {
/* 184:235 */     return (FacturaProveedor)this.facturaProveedorDao.buscarPorId(Integer.valueOf(idNotaDebito));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<FacturaProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 188:    */   {
/* 189:246 */     return this.facturaProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<FacturaProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 193:    */   {
/* 194:256 */     return this.facturaProveedorDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public int contarPorCriterio(Map<String, String> filters)
/* 198:    */   {
/* 199:266 */     return this.facturaProveedorDao.contarPorCriterio(filters);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public FacturaProveedor cargarDetalle(int idNotaDebito)
/* 203:    */   {
/* 204:276 */     return this.facturaProveedorDao.cargarDetalle(idNotaDebito);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public long verificaExistenciaNumero(FacturaProveedor notaDebito)
/* 208:    */   {
/* 209:286 */     return this.facturaProveedorDao.verificaExistenciaNumero(notaDebito);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void generarCuentaPorPagar(FacturaProveedor notaDebito)
/* 213:    */     throws ExcepcionAS2
/* 214:    */   {
/* 215:296 */     if ((notaDebito.getNumeroCuotas() >= 0) && (notaDebito.getCondicionPago() != null) && (notaDebito.getCondicionPago().getId() > 0))
/* 216:    */     {
/* 217:298 */       int numeroCuota = 0;
/* 218:299 */       int numeroCuotas = notaDebito.getNumeroCuotas();
/* 219:    */       
/* 220:301 */       BigDecimal totalFactura = notaDebito.getTotalFactura();
/* 221:302 */       BigDecimal valorCuota = totalFactura.divide(new BigDecimal(numeroCuotas), 2, RoundingMode.HALF_UP);
/* 222:    */       
/* 223:304 */       int hasta = Math.max(notaDebito.getNumeroCuotas(), notaDebito.getListaCuentaPorPagar().size());
/* 224:306 */       for (int i = 0; i < hasta; i++)
/* 225:    */       {
/* 226:307 */         numeroCuota = i + 1;
/* 227:309 */         if (numeroCuota > notaDebito.getNumeroCuotas())
/* 228:    */         {
/* 229:310 */           ((CuentaPorPagar)notaDebito.getListaCuentaPorPagar().get(i)).setEliminado(true);
/* 230:    */         }
/* 231:    */         else
/* 232:    */         {
/* 233:    */           CuentaPorPagar cuentaPorPagar;
/* 234:    */           CuentaPorPagar cuentaPorPagar;
/* 235:315 */           if (numeroCuota > notaDebito.getListaCuentaPorPagar().size()) {
/* 236:316 */             cuentaPorPagar = new CuentaPorPagar();
/* 237:    */           } else {
/* 238:318 */             cuentaPorPagar = (CuentaPorPagar)notaDebito.getListaCuentaPorPagar().get(i);
/* 239:    */           }
/* 240:320 */           cuentaPorPagar.setEliminado(false);
/* 241:321 */           Date fechaVencimiento = null;
/* 242:    */           
/* 243:323 */           CondicionPago condicionPago = this.servicioCondicionPago.buscarPorId(Integer.valueOf(notaDebito.getCondicionPago().getIdCondicionPago()));
/* 244:325 */           if (condicionPago.isIndicadorFechaFija())
/* 245:    */           {
/* 246:326 */             Calendar calFechaVen = Calendar.getInstance();
/* 247:327 */             calFechaVen.setTime(notaDebito.getFecha());
/* 248:    */             
/* 249:329 */             fechaVencimiento = FuncionesUtiles.getFecha(condicionPago.getDiaVencimiento(), calFechaVen.get(2), calFechaVen
/* 250:330 */               .get(1));
/* 251:    */           }
/* 252:    */           else
/* 253:    */           {
/* 254:332 */             fechaVencimiento = FuncionesUtiles.sumarFechaDiasMeses(notaDebito.getFecha(), condicionPago.getMesesPlazo() * (i + 1), condicionPago
/* 255:333 */               .getDiasPlazo() * (i + 1));
/* 256:    */           }
/* 257:336 */           cuentaPorPagar.setFechaVencimiento(fechaVencimiento);
/* 258:337 */           cuentaPorPagar.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 259:338 */           cuentaPorPagar.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 260:339 */           cuentaPorPagar.setNumeroCuota(numeroCuota);
/* 261:342 */           if (numeroCuota == numeroCuotas) {
/* 262:343 */             valorCuota = totalFactura;
/* 263:    */           }
/* 264:345 */           cuentaPorPagar.setValor(valorCuota);
/* 265:    */           
/* 266:347 */           cuentaPorPagar.setFacturaProveedor(notaDebito);
/* 267:349 */           if (numeroCuota > notaDebito.getListaCuentaPorPagar().size()) {
/* 268:350 */             notaDebito.getListaCuentaPorPagar().add(cuentaPorPagar);
/* 269:    */           }
/* 270:353 */           totalFactura = totalFactura.subtract(valorCuota);
/* 271:    */         }
/* 272:    */       }
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void contabilizar(FacturaProveedor notaDebito)
/* 277:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 278:    */   {
/* 279:366 */     Date fechaContabilizacion = notaDebito.getFecha();
/* 280:367 */     int idFacturaProveedor = notaDebito.getIdFacturaProveedor();
/* 281:    */     Asiento asiento;
/* 282:370 */     if (notaDebito.getAsiento() != null)
/* 283:    */     {
/* 284:371 */       Asiento asiento = notaDebito.getAsiento();
/* 285:372 */       asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/* 286:    */     }
/* 287:    */     else
/* 288:    */     {
/* 289:374 */       asiento = new Asiento();
/* 290:375 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 291:376 */       asiento.setSucursal(AppUtil.getSucursal());
/* 292:    */       
/* 293:378 */       TipoAsiento tipoAsiento = notaDebito.getDocumento().getTipoAsiento();
/* 294:379 */       asiento.setTipoAsiento(tipoAsiento);
/* 295:    */     }
/* 296:383 */     String concepto = "";
/* 297:384 */     concepto = notaDebito.getDocumento().getNombre().trim() + " #" + notaDebito.getNumero() + " " + notaDebito.getDescripcion();
/* 298:385 */     asiento.setConcepto(concepto);
/* 299:386 */     asiento.setFecha(fechaContabilizacion);
/* 300:    */     
/* 301:388 */     List<DetalleInterfazContable> listaDA = new ArrayList();
/* 302:    */     
/* 303:390 */     listaDA.addAll(this.facturaProveedorDao.getFacturaProveedorNDIC(idFacturaProveedor));
/* 304:391 */     listaDA.addAll(this.facturaProveedorDao.getFacturaProveedorCPIC(idFacturaProveedor));
/* 305:    */     
/* 306:    */ 
/* 307:394 */     super.generarAsiento(asiento, listaDA, notaDebito.getDocumento());
/* 308:    */     
/* 309:    */ 
/* 310:397 */     this.servicioAsiento.guardar(asiento);
/* 311:    */     
/* 312:    */ 
/* 313:    */ 
/* 314:401 */     notaDebito.setEstado(Estado.CONTABILIZADO);
/* 315:    */     
/* 316:403 */     notaDebito.setFechaContabilizacion(fechaContabilizacion);
/* 317:    */     
/* 318:405 */     notaDebito.setAsiento(asiento);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public FacturaProveedor totalizar(FacturaProveedor notaDebito)
/* 322:    */     throws ExcepcionAS2Compras
/* 323:    */   {
/* 324:415 */     BigDecimal total = BigDecimal.ZERO;
/* 325:416 */     BigDecimal descuento = BigDecimal.ZERO;
/* 326:418 */     for (DetalleFacturaProveedor dfp : notaDebito.getListaDetalleFacturaProveedor()) {
/* 327:419 */       if (!dfp.isEliminado())
/* 328:    */       {
/* 329:421 */         total = total.add(dfp.getPrecioLinea());
/* 330:422 */         descuento = descuento.add(dfp.getDescuentoLinea());
/* 331:    */       }
/* 332:    */     }
/* 333:426 */     if (notaDebito.getFacturaProveedorImportacion() == null) {
/* 334:427 */       totalizarImpuesto(notaDebito);
/* 335:    */     }
/* 336:429 */     notaDebito.setTotal(FuncionesUtiles.redondearBigDecimal(total));
/* 337:430 */     notaDebito.setDescuento(FuncionesUtiles.redondearBigDecimal(descuento));
/* 338:431 */     return notaDebito;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public FacturaProveedor totalizarImpuesto(FacturaProveedor notaDebito)
/* 342:    */     throws ExcepcionAS2Compras
/* 343:    */   {
/* 344:441 */     BigDecimal totalImpuestoProducto = BigDecimal.ZERO;
/* 345:443 */     for (DetalleFacturaProveedor dfp : notaDebito.getListaDetalleFacturaProveedor()) {
/* 346:445 */       if (!dfp.isEliminado()) {
/* 347:446 */         totalImpuestoProducto = totalImpuestoProducto.add(dfp.getValorImpuestosLinea());
/* 348:    */       }
/* 349:    */     }
/* 350:450 */     notaDebito.setImpuesto(FuncionesUtiles.redondearBigDecimal(totalImpuestoProducto));
/* 351:451 */     return notaDebito;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void cargarSecuencia(FacturaProveedor notaDebito, PuntoDeVenta puntoDeVenta)
/* 355:    */     throws ExcepcionAS2
/* 356:    */   {
/* 357:463 */     if (puntoDeVenta != null) {
/* 358:464 */       this.servicioDocumento.cargarSecuencia(notaDebito.getDocumento(), puntoDeVenta, notaDebito.getFecha());
/* 359:    */     }
/* 360:467 */     String numero = this.servicioSecuencia.obtenerSecuencia(notaDebito.getDocumento().getSecuencia(), notaDebito.getFecha());
/* 361:    */     
/* 362:469 */     notaDebito.setNumero(numero);
/* 363:    */   }
/* 364:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.impl.ServicioNotaDebitoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */