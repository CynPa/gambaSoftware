/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.dao.AsientoDao;
/*   5:    */ import com.asinfo.as2.dao.DetalleAsientoCentroCostoDao;
/*   6:    */ import com.asinfo.as2.dao.DetalleAsientoDao;
/*   7:    */ import com.asinfo.as2.dao.EstadoChequeDao;
/*   8:    */ import com.asinfo.as2.dao.GenericoDao;
/*   9:    */ import com.asinfo.as2.dao.MovimientoBancarioDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  13:    */ import com.asinfo.as2.entities.Asiento;
/*  14:    */ import com.asinfo.as2.entities.CentroCosto;
/*  15:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  16:    */ import com.asinfo.as2.entities.CuentaContable;
/*  17:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  18:    */ import com.asinfo.as2.entities.DetallePlantillaAsiento;
/*  19:    */ import com.asinfo.as2.entities.DimensionContable;
/*  20:    */ import com.asinfo.as2.entities.Documento;
/*  21:    */ import com.asinfo.as2.entities.EstadoCheque;
/*  22:    */ import com.asinfo.as2.entities.FormaPago;
/*  23:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  24:    */ import com.asinfo.as2.entities.MovimientoBancarioEstadoCheque;
/*  25:    */ import com.asinfo.as2.entities.Organizacion;
/*  26:    */ import com.asinfo.as2.entities.Periodo;
/*  27:    */ import com.asinfo.as2.entities.PlantillaAsiento;
/*  28:    */ import com.asinfo.as2.entities.Sucursal;
/*  29:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  30:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  31:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  32:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  33:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  34:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  35:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  36:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  37:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  38:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioPlantillaAsiento;
/*  39:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  40:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  41:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  42:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*  43:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  44:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  45:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  46:    */ import com.asinfo.as2.util.AppUtil;
/*  47:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  48:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  49:    */ import java.io.InputStream;
/*  50:    */ import java.math.BigDecimal;
/*  51:    */ import java.math.RoundingMode;
/*  52:    */ import java.util.ArrayList;
/*  53:    */ import java.util.Collection;
/*  54:    */ import java.util.Date;
/*  55:    */ import java.util.HashMap;
/*  56:    */ import java.util.Iterator;
/*  57:    */ import java.util.List;
/*  58:    */ import java.util.Map;
/*  59:    */ import javax.ejb.EJB;
/*  60:    */ import javax.ejb.SessionContext;
/*  61:    */ import javax.ejb.Stateless;
/*  62:    */ import javax.ejb.TransactionAttribute;
/*  63:    */ import javax.ejb.TransactionAttributeType;
/*  64:    */ import org.apache.log4j.Logger;
/*  65:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  66:    */ 
/*  67:    */ @Stateless
/*  68:    */ public class ServicioAsientoImpl
/*  69:    */   extends AbstractServicioAS2Financiero
/*  70:    */   implements ServicioAsiento
/*  71:    */ {
/*  72:    */   private static final long serialVersionUID = -4586967051052446973L;
/*  73:    */   @EJB
/*  74:    */   private transient AsientoDao asientoDao;
/*  75:    */   @EJB
/*  76:    */   private transient DetalleAsientoDao detalleAsientoDao;
/*  77:    */   @EJB
/*  78:    */   private transient DetalleAsientoCentroCostoDao detalleAsientoCentroCostoDao;
/*  79:    */   @EJB
/*  80:    */   private transient MovimientoBancarioDao movimientoBancarioDao;
/*  81:    */   @EJB
/*  82:    */   private transient GenericoDao<MovimientoBancarioEstadoCheque> movimientoBancarioEstadoChequeDao;
/*  83:    */   @EJB
/*  84:    */   private transient ServicioSecuencia servicioSecuencia;
/*  85:    */   @EJB
/*  86:    */   private transient ServicioPeriodo servicioPeriodo;
/*  87:    */   @EJB
/*  88:    */   private transient ServicioTipoAsiento servicioTipoAsiento;
/*  89:    */   @EJB
/*  90:    */   private transient ServicioCuentaContable servicioCuentaContable;
/*  91:    */   @EJB
/*  92:    */   private transient ServicioCentroCosto servicioCentroCosto;
/*  93:    */   @EJB
/*  94:    */   private transient ServicioPlantillaAsiento servicioPlantillaAsiento;
/*  95:    */   @EJB
/*  96:    */   private transient ServicioDimensionContable servicioDimensionContable;
/*  97:    */   @EJB
/*  98:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  99:    */   @EJB
/* 100:    */   private transient ServicioDocumento servicioDocumento;
/* 101:    */   @EJB
/* 102:    */   private transient ServicioMovimientoBancario servicioMovimientoBancario;
/* 103:    */   @EJB
/* 104:    */   private transient ServicioFormaPago servicioFormaPago;
/* 105:    */   @EJB
/* 106:    */   private EstadoChequeDao estadoChequeDao;
/* 107:    */   
/* 108:    */   public void guardar(Asiento asiento)
/* 109:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 110:    */   {
/* 111:128 */     validar(asiento);
/* 112:129 */     validarDimensiones(asiento);
/* 113:130 */     cargarSecuencia(asiento);
/* 114:140 */     if (verificarExistenciaNumero(asiento).longValue() > 0L) {
/* 115:141 */       throw new ExcepcionAS2("msg_numero_asiento_ya_existe", asiento.getNumero());
/* 116:    */     }
/* 117:144 */     generarEstadoInicialCheque(asiento);
/* 118:    */     
/* 119:146 */     List<MovimientoBancario> listaMovimientoBancario = new ArrayList();
/* 120:148 */     for (DetalleAsiento d : asiento.getListaDetalleAsiento())
/* 121:    */     {
/* 122:149 */       if (d.getDebe().abs().add(d.getHaber().abs()).compareTo(BigDecimal.ZERO) == 0) {
/* 123:150 */         d.setEliminado(true);
/* 124:    */       }
/* 125:158 */       if (asiento.getProyecto() != null) {
/* 126:160 */         d.setDimensionContable5(asiento.getProyecto());
/* 127:    */       }
/* 128:163 */       if (d.getMovimientoBancario() != null)
/* 129:    */       {
/* 130:165 */         if (asiento.getFechaChequePosfechado() != null) {
/* 131:166 */           d.getMovimientoBancario().setFechaPosfechado(asiento.getFechaChequePosfechado());
/* 132:    */         }
/* 133:169 */         if ((asiento.getNotaPosfechado() != null) && (!asiento.getNotaPosfechado().isEmpty())) {
/* 134:170 */           d.getMovimientoBancario().setNotaPosfechado(asiento.getNotaPosfechado());
/* 135:    */         }
/* 136:173 */         d.getMovimientoBancario().setFecha(asiento.getFecha());
/* 137:177 */         for (MovimientoBancarioEstadoCheque mbec : d.getMovimientoBancario().getListaMovimientoBancarioEstadoCheque()) {
/* 138:178 */           if (mbec.isEliminado()) {
/* 139:179 */             this.movimientoBancarioEstadoChequeDao.guardar(mbec);
/* 140:    */           }
/* 141:    */         }
/* 142:183 */         this.movimientoBancarioDao.guardar(d.getMovimientoBancario());
/* 143:184 */         if (d.getMovimientoBancario().getMovEstadoChequeInicial() != null) {
/* 144:185 */           this.movimientoBancarioEstadoChequeDao.guardar(d.getMovimientoBancario().getMovEstadoChequeInicial());
/* 145:    */         }
/* 146:188 */         if ((!d.getMovimientoBancario().isEliminado()) && (d.getMovimientoBancario().getSecuencia() != null) && 
/* 147:189 */           (d.getHaber().compareTo(BigDecimal.ZERO) > 0)) {
/* 148:192 */           listaMovimientoBancario.add(d.getMovimientoBancario());
/* 149:    */         }
/* 150:    */       }
/* 151:197 */       this.detalleAsientoDao.guardar(d);
/* 152:    */     }
/* 153:200 */     this.asientoDao.guardar(asiento);
/* 154:    */     
/* 155:202 */     this.asientoDao.guardar(asiento);
/* 156:205 */     for (MovimientoBancario movimientoBancario : listaMovimientoBancario) {
/* 157:206 */       this.servicioSecuencia.actualizarSecuencia(movimientoBancario.getSecuencia(), movimientoBancario.getDocumentoReferencia());
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   private void cargarSecuencia(Asiento asiento)
/* 162:    */     throws ExcepcionAS2
/* 163:    */   {
/* 164:212 */     if ((asiento.getNumero() == null) || (asiento.getNumero().isEmpty()))
/* 165:    */     {
/* 166:213 */       String numero = this.servicioSecuencia.obtenerSecuenciaTipoAsiento(asiento.getTipoAsiento().getId(), asiento.getFecha());
/* 167:214 */       asiento.setNumero(numero);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   private void validar(Asiento asiento)
/* 172:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 173:    */   {
/* 174:222 */     Periodo periodo = this.servicioPeriodo.buscarPorFecha(asiento.getFecha(), asiento.getIdOrganizacion(), DocumentoBase.CONCEPTOS_CONTABLES);
/* 175:    */     
/* 176:224 */     asiento.setPeriodo(periodo);
/* 177:    */     
/* 178:    */ 
/* 179:227 */     validarDescuadre(asiento);
/* 180:230 */     for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/* 181:231 */       if (!detalleAsiento.isEliminado())
/* 182:    */       {
/* 183:233 */         if (!detalleAsiento.getCuentaContable().isIndicadorMovimiento()) {
/* 184:234 */           throw new ExcepcionAS2("msg_error_cuenta_contable_movimiento", detalleAsiento.getCuentaContable().getCodigo() + "<>" + detalleAsiento.getCuentaContable().getNombre());
/* 185:    */         }
/* 186:237 */         if ((detalleAsiento.getMovimientoBancario() != null) && 
/* 187:238 */           (detalleAsiento.getMovimientoBancario().getDocumentoReferencia() != null) && 
/* 188:239 */           (!detalleAsiento.getMovimientoBancario().getDocumentoReferencia().isEmpty()) && 
/* 189:240 */           (detalleAsiento.getMovimientoBancario().getValor().compareTo(BigDecimal.ZERO) == -1)) {
/* 190:241 */           this.movimientoBancarioDao.verificarExitenciaDocumentoReferencia(detalleAsiento.getMovimientoBancario());
/* 191:    */         }
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void validarCuentas(Asiento asiento)
/* 197:    */     throws ExcepcionAS2
/* 198:    */   {
/* 199:250 */     Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(asiento.getIdOrganizacion()));
/* 200:251 */     List<CuentaContable> listaCuentaContableDiferente = new ArrayList();
/* 201:253 */     for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/* 202:254 */       if (!detalleAsiento.isEliminado()) {
/* 203:255 */         listaCuentaContableDiferente.add(detalleAsiento.getCuentaContable());
/* 204:    */       }
/* 205:    */     }
/* 206:259 */     Object lista = this.asientoDao.listaCuentaOrganizacionDiferente(listaCuentaContableDiferente, asiento.getIdOrganizacion());
/* 207:261 */     if ((lista != null) && (!((List)lista).isEmpty()))
/* 208:    */     {
/* 209:262 */       AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.CUENTA_CONTABLE_ERRONEA", new String[] { ((CuentaContable)((List)lista).get(0)).getNombre(), organizacion.getRazonSocial() });
/* 210:263 */       throw new ExcepcionAS2(exception.getMensaje());
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void anular(Asiento asiento)
/* 215:    */     throws ExcepcionAS2Financiero
/* 216:    */   {
/* 217:276 */     boolean indicadorAutomatico = asiento.isIndicadorAutomatico();
/* 218:277 */     asiento.setIndicadorAutomatico(indicadorAutomatico);
/* 219:278 */     esEditable(asiento);
/* 220:279 */     this.asientoDao.actualizarEstado(Integer.valueOf(asiento.getIdAsiento()), Estado.ANULADO);
/* 221:    */     
/* 222:    */ 
/* 223:282 */     this.asientoDao.eliminarMovimientoBancarioAsiento(asiento);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Asiento buscarPorId(Integer id)
/* 227:    */   {
/* 228:292 */     return (Asiento)this.asientoDao.buscarPorId(id);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Asiento cargarDetalle(int idAsiento)
/* 232:    */   {
/* 233:302 */     return this.asientoDao.cargarDetalle(idAsiento);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void mayorizarDesmayorizar(Date fechaDesde, Date fechaHasta, TipoAsiento tipoAsiento, Estado estado, int idOrganizacion)
/* 237:    */     throws ExcepcionAS2Financiero
/* 238:    */   {
/* 239:314 */     this.servicioPeriodo.validar(fechaDesde, fechaHasta, idOrganizacion);
/* 240:315 */     this.asientoDao.mayorizarDesmayorizar(fechaDesde, fechaHasta, tipoAsiento, estado, idOrganizacion);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void mayorizarDesmayorizarPorAsiento(Estado estado, Asiento asiento)
/* 244:    */     throws ExcepcionAS2Financiero
/* 245:    */   {
/* 246:320 */     this.servicioPeriodo.validar(asiento.getFecha(), asiento.getFecha(), asiento.getIdOrganizacion());
/* 247:321 */     this.asientoDao.mayorizarDesmayorizarPorAsiento(estado, asiento);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<Asiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 251:    */   {
/* 252:326 */     return this.asientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public List<Asiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 256:    */   {
/* 257:331 */     return this.asientoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void validarDescuadre(Asiento asiento)
/* 261:    */     throws ExcepcionAS2Financiero
/* 262:    */   {
/* 263:341 */     calcularTotales(asiento);
/* 264:344 */     if (asiento.getTotalDebe().compareTo(asiento.getTotalHaber()) != 0) {
/* 265:346 */       throw new ExcepcionAS2Financiero("msg_error_asiento_descuadre", " " + asiento.getTotalDebe().toString() + "<>" + asiento.getTotalHaber().toString() + " " + asiento.getConcepto());
/* 266:    */     }
/* 267:349 */     asiento.setValor(asiento.getTotalDebe());
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void actualizarEstado(Integer idAsiento, Estado estado)
/* 271:    */     throws ExcepcionAS2Financiero
/* 272:    */   {
/* 273:360 */     this.asientoDao.actualizarEstado(idAsiento, estado);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public Estado getEstado(Integer idAsiento)
/* 277:    */   {
/* 278:370 */     return this.asientoDao.getEstado(idAsiento);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void esEditable(Asiento asiento)
/* 282:    */     throws ExcepcionAS2Financiero
/* 283:    */   {
/* 284:381 */     if ((asiento.isIndicadorAutomatico()) && (!ParametrosSistema.getEditarAsientosAutomaticos(asiento.getIdOrganizacion()).booleanValue())) {
/* 285:383 */       throw new ExcepcionAS2Financiero("msg_info_asiento_generado");
/* 286:    */     }
/* 287:387 */     this.servicioPeriodo.buscarPorFecha(asiento.getFecha(), asiento.getIdOrganizacion(), DocumentoBase.CONCEPTOS_CONTABLES);
/* 288:389 */     if (asiento.getEstado() == Estado.ANULADO) {
/* 289:391 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 290:    */     }
/* 291:393 */     if (asiento.getEstado() == Estado.REVISADO) {
/* 292:395 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 293:    */     }
/* 294:398 */     if (this.asientoDao.verificarExistenciaMovimientoBancario(asiento.getId()).longValue() > 0L) {
/* 295:399 */       throw new ExcepcionAS2Financiero("msg_error_editar_movimiento_conciliado");
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List getReporteAsiento(Asiento asiento, boolean indicadorCentroCostos, Boolean resumido)
/* 300:    */     throws ExcepcionAS2Financiero
/* 301:    */   {
/* 302:412 */     return this.asientoDao.getReporteAsiento(asiento, indicadorCentroCostos, resumido);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public int contarPorCriterio(Map<String, String> filters)
/* 306:    */   {
/* 307:422 */     return this.asientoDao.contarPorCriterio(filters);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Asiento copiarAsiento(Asiento asiento)
/* 311:    */     throws ExcepcionAS2
/* 312:    */   {
/* 313:433 */     asiento.setIdAsiento(0);
/* 314:434 */     asiento.setNumero("");
/* 315:435 */     asiento.setEstado(Estado.ELABORADO);
/* 316:436 */     asiento.setIndicadorAutomatico(false);
/* 317:437 */     asiento.setDocumentoOrigen(null);
/* 318:438 */     for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento())
/* 319:    */     {
/* 320:439 */       detalleAsiento.setIdDetalleAsiento(0);
/* 321:440 */       if (detalleAsiento.getMovimientoBancario() != null)
/* 322:    */       {
/* 323:441 */         detalleAsiento.getMovimientoBancario().setIdMovimientoBancario(0);
/* 324:442 */         detalleAsiento.getMovimientoBancario().setConciliacionBancaria(null);
/* 325:    */       }
/* 326:444 */       actualizarMovimientoBancario(detalleAsiento);
/* 327:    */     }
/* 328:447 */     return asiento;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void actualizarMovimientoBancario(DetalleAsiento detalleAsiento)
/* 332:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 333:    */   {
/* 334:    */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/* 335:454 */     if (detalleAsiento.getCuentaContable().getTipoCuentaContable() == TipoCuentaContable.BANCO)
/* 336:    */     {
/* 337:456 */       cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.buscarPorCuentaContable(detalleAsiento
/* 338:457 */         .getCuentaContable().getId());
/* 339:458 */       if (cuentaBancariaOrganizacion == null)
/* 340:    */       {
/* 341:459 */         detalleAsiento.setEliminado(true);
/* 342:460 */         throw new ExcepcionAS2Financiero("msg_error_parametrizacion_cuenta_pago", detalleAsiento.getCuentaContable().getCodigo());
/* 343:    */       }
/* 344:    */       MovimientoBancario movimientoBancario;
/* 345:    */       MovimientoBancario movimientoBancario;
/* 346:465 */       if (detalleAsiento.getMovimientoBancario() != null)
/* 347:    */       {
/* 348:466 */         movimientoBancario = detalleAsiento.getMovimientoBancario();
/* 349:    */       }
/* 350:    */       else
/* 351:    */       {
/* 352:468 */         movimientoBancario = new MovimientoBancario();
/* 353:469 */         detalleAsiento.setMovimientoBancario(movimientoBancario);
/* 354:470 */         movimientoBancario.setDetalleAsiento(detalleAsiento);
/* 355:    */       }
/* 356:473 */       movimientoBancario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 357:474 */       movimientoBancario.setIdSucursal(AppUtil.getSucursal().getId());
/* 358:475 */       movimientoBancario.setValor(detalleAsiento.getDebe().subtract(detalleAsiento.getHaber()));
/* 359:476 */       movimientoBancario.setFecha(detalleAsiento.getAsiento().getFecha());
/* 360:477 */       movimientoBancario.setEliminado(false);
/* 361:    */       
/* 362:479 */       movimientoBancario.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(cuentaBancariaOrganizacion
/* 363:480 */         .getIdCuentaBancariaOrganizacion()));
/* 364:481 */       movimientoBancario.setDocumento((Documento)this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.CONCEPTOS_CONTABLES).get(0));
/* 365:482 */       movimientoBancario.setFormaPago(null);
/* 366:483 */       movimientoBancario.setEstado(Estado.CONTABILIZADO);
/* 367:484 */       movimientoBancario.setEstadoCheque(null);
/* 368:485 */       movimientoBancario.setDocumentoReferencia("");
/* 369:    */     }
/* 370:489 */     else if (detalleAsiento.getMovimientoBancario() != null)
/* 371:    */     {
/* 372:490 */       for (MovimientoBancarioEstadoCheque mbec : detalleAsiento.getMovimientoBancario().getListaMovimientoBancarioEstadoCheque()) {
/* 373:491 */         mbec.setEliminado(true);
/* 374:    */       }
/* 375:493 */       detalleAsiento.getMovimientoBancario().setEliminado(true);
/* 376:494 */       if (detalleAsiento.getMovimientoBancario().getId() == 0) {
/* 377:495 */         detalleAsiento.setMovimientoBancario(null);
/* 378:    */       }
/* 379:    */     }
/* 380:    */   }
/* 381:    */   
/* 382:    */   private CentroCosto buscarCentroCostoPorCodigo(String codigo)
/* 383:    */   {
/* 384:    */     try
/* 385:    */     {
/* 386:505 */       return this.servicioCentroCosto.buscarPorCodigo(codigo);
/* 387:    */     }
/* 388:    */     catch (ExcepcionAS2 e) {}
/* 389:508 */     return null;
/* 390:    */   }
/* 391:    */   
/* 392:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 393:    */   public void migracionAsiento(String fileName, InputStream imInputStream, int filaInicial, int idOrganizacion)
/* 394:    */     throws ExcepcionAS2, AS2Exception
/* 395:    */   {
/* 396:521 */     HashMap<String, String> filtros = new HashMap();
/* 397:522 */     filtros.put("idOrganizacion", Integer.toString(idOrganizacion));
/* 398:    */     
/* 399:    */ 
/* 400:525 */     HashMap<String, Documento> hmDocumento = new HashMap();
/* 401:526 */     for (Iterator localIterator = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros).iterator(); localIterator.hasNext();)
/* 402:    */     {
/* 403:526 */       documento = (Documento)localIterator.next();
/* 404:527 */       hmDocumento.put(documento.getNombre(), documento);
/* 405:    */     }
/* 406:    */     Documento documento;
/* 407:531 */     Object hmFormaPago = new HashMap();
/* 408:532 */     for (FormaPago formaPago : this.servicioFormaPago.obtenerListaCombo("nombre", true, filtros)) {
/* 409:533 */       ((HashMap)hmFormaPago).put(formaPago.getCodigo(), formaPago);
/* 410:    */     }
/* 411:537 */     HashMap<String, Asiento> hmAsiento = new HashMap();
/* 412:538 */     HashMap<String, TipoAsiento> hmTipoAsiento = new HashMap();
/* 413:539 */     HashMap<String, CuentaContable> hmCuentaContable = new HashMap();
/* 414:    */     
/* 415:    */ 
/* 416:542 */     HashMap<String, DimensionContable> hmDimensionContable1 = new HashMap();
/* 417:543 */     HashMap<String, DimensionContable> hmDimensionContable2 = new HashMap();
/* 418:544 */     HashMap<String, DimensionContable> hmDimensionContable3 = new HashMap();
/* 419:545 */     HashMap<String, DimensionContable> hmDimensionContable4 = new HashMap();
/* 420:546 */     HashMap<String, DimensionContable> hmDimensionContable5 = new HashMap();
/* 421:    */     
/* 422:548 */     int filaActual = filaInicial;
/* 423:549 */     int columnaErronea = -1;
/* 424:550 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 425:    */     try
/* 426:    */     {
/* 427:554 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, filaInicial, 0);
/* 428:557 */       for (HSSFCell[] fila : datos)
/* 429:    */       {
/* 430:558 */         filaErronea = fila;
/* 431:    */         
/* 432:    */ 
/* 433:561 */         columnaErronea = 0;
/* 434:562 */         Date fecha = fila[0].getDateCellValue();
/* 435:563 */         columnaErronea = 1;
/* 436:564 */         String numeroReferencial = fila[1].getStringCellValue().trim();
/* 437:565 */         columnaErronea = 2;
/* 438:566 */         String nombreTipoAsiento = fila[2].getStringCellValue().trim();
/* 439:567 */         columnaErronea = 3;
/* 440:568 */         String concepto = (fila[3] == null) || (fila[3].getStringCellValue().trim().isEmpty()) ? null : fila[3].getStringCellValue().trim();
/* 441:569 */         columnaErronea = 4;
/* 442:570 */         String codigoCuentaContable = fila[4].getStringCellValue().trim();
/* 443:571 */         columnaErronea = 5;
/* 444:572 */         String descripcion = fila[5].getStringCellValue().trim();
/* 445:575 */         if (descripcion.length() > 200) {
/* 446:576 */           throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.DESCRIPCION_MAYOR", new String[] { " 200 caracteres fila " + String.valueOf(filaActual) });
/* 447:    */         }
/* 448:579 */         columnaErronea = 6;
/* 449:580 */         BigDecimal debe = BigDecimal.ZERO;
/* 450:581 */         if (fila[6] != null) {
/* 451:582 */           debe = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[6].getNumericCellValue()), 2);
/* 452:    */         }
/* 453:585 */         columnaErronea = 7;
/* 454:586 */         BigDecimal haber = BigDecimal.ZERO;
/* 455:587 */         if (fila[7] != null) {
/* 456:588 */           haber = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[7].getNumericCellValue()), 2);
/* 457:    */         }
/* 458:592 */         String codigoDimensionContable1 = (fila[(columnaErronea = 8)] == null) || (fila[8].getStringCellValue().trim().isEmpty()) ? null : fila[8].getStringCellValue().trim();
/* 459:    */         
/* 460:594 */         String codigoDimensionContable2 = (fila[(columnaErronea = 9)] == null) || (fila[9].getStringCellValue().trim().isEmpty()) ? null : fila[9].getStringCellValue().trim();
/* 461:    */         
/* 462:596 */         String codigoDimensionContable3 = (fila[(columnaErronea = 10)] == null) || (fila[10].getStringCellValue().trim().isEmpty()) ? null : fila[10].getStringCellValue().trim();
/* 463:    */         
/* 464:598 */         String codigoDimensionContable4 = (fila[(columnaErronea = 11)] == null) || (fila[11].getStringCellValue().trim().isEmpty()) ? null : fila[11].getStringCellValue().trim();
/* 465:    */         
/* 466:600 */         String codigoDimensionContable5 = (fila[(columnaErronea = 12)] == null) || (fila[12].getStringCellValue().trim().isEmpty()) ? null : fila[12].getStringCellValue().trim();
/* 467:    */         
/* 468:602 */         String descripcion2 = (fila[(columnaErronea = 13)] == null) || (fila[13].getStringCellValue().trim().isEmpty()) ? null : fila[13].getStringCellValue().trim();
/* 469:    */         
/* 470:    */ 
/* 471:605 */         TipoAsiento tipoAsiento = (TipoAsiento)hmTipoAsiento.get(nombreTipoAsiento);
/* 472:606 */         if (tipoAsiento == null)
/* 473:    */         {
/* 474:607 */           tipoAsiento = this.servicioTipoAsiento.buscarPorNombre(nombreTipoAsiento, AppUtil.getOrganizacion().getIdOrganizacion());
/* 475:608 */           hmTipoAsiento.put(nombreTipoAsiento, tipoAsiento);
/* 476:    */         }
/* 477:612 */         CuentaContable cuentaContable = (CuentaContable)hmCuentaContable.get(codigoCuentaContable);
/* 478:    */         
/* 479:614 */         Documento documento = null;
/* 480:615 */         FormaPago formaPago = null;
/* 481:616 */         String documentoReferencia = "";
/* 482:617 */         if (cuentaContable == null)
/* 483:    */         {
/* 484:618 */           cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion().getIdOrganizacion());
/* 485:619 */           hmCuentaContable.put(codigoCuentaContable, cuentaContable);
/* 486:    */         }
/* 487:622 */         if (cuentaContable.getTipoCuentaContable().equals(TipoCuentaContable.BANCO))
/* 488:    */         {
/* 489:625 */           String nombreDocumento = (fila[(columnaErronea = 14)] == null) || (fila[14].getStringCellValue().trim().isEmpty()) ? null : fila[14].getStringCellValue().trim();
/* 490:626 */           documento = (Documento)hmDocumento.get(nombreDocumento);
/* 491:627 */           if (documento == null) {
/* 492:629 */             throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl.NO_EXISTE_DOCUMENTO", new String[] { nombreDocumento == null ? "(Celda Vacia)" : nombreDocumento, Integer.toString(filaActual), Integer.toString(columnaErronea) });
/* 493:    */           }
/* 494:634 */           String codigoFormaPago = (fila[(columnaErronea = 15)] == null) || (fila[15].getStringCellValue().trim().isEmpty()) ? null : fila[15].getStringCellValue().trim();
/* 495:635 */           formaPago = (FormaPago)((HashMap)hmFormaPago).get(codigoFormaPago);
/* 496:636 */           if (formaPago == null) {
/* 497:637 */             throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.NO_EXISTE_FORMA_PAGO_MOVIMIENTO_BANCARIO", new String[] { codigoFormaPago });
/* 498:    */           }
/* 499:641 */           documentoReferencia = (fila[(columnaErronea = 16)] == null) || (fila[15].getStringCellValue().trim().isEmpty()) ? "" : fila[16].getStringCellValue().trim();
/* 500:642 */           if (documentoReferencia.equals("")) {
/* 501:643 */             throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.ERROR_DOCUMENTO_REFERENCIA_MOVIMIENTO_BANCARIO");
/* 502:    */           }
/* 503:    */         }
/* 504:647 */         DimensionContable dimensionContable1 = null;
/* 505:648 */         if (codigoDimensionContable1 != null)
/* 506:    */         {
/* 507:650 */           dimensionContable1 = (DimensionContable)hmDimensionContable1.get(codigoDimensionContable1);
/* 508:652 */           if (dimensionContable1 == null)
/* 509:    */           {
/* 510:653 */             dimensionContable1 = this.servicioDimensionContable.buscarPorCodigo("1", codigoDimensionContable1);
/* 511:654 */             hmDimensionContable1.put(codigoDimensionContable1, dimensionContable1);
/* 512:    */           }
/* 513:    */         }
/* 514:658 */         DimensionContable dimensionContable2 = null;
/* 515:659 */         if (codigoDimensionContable2 != null)
/* 516:    */         {
/* 517:661 */           dimensionContable2 = (DimensionContable)hmDimensionContable2.get(codigoDimensionContable2);
/* 518:663 */           if (dimensionContable2 == null)
/* 519:    */           {
/* 520:664 */             dimensionContable2 = this.servicioDimensionContable.buscarPorCodigo("2", codigoDimensionContable2);
/* 521:665 */             hmDimensionContable2.put(codigoDimensionContable2, dimensionContable2);
/* 522:    */           }
/* 523:    */         }
/* 524:669 */         DimensionContable dimensionContable3 = null;
/* 525:670 */         if (codigoDimensionContable3 != null)
/* 526:    */         {
/* 527:672 */           dimensionContable3 = (DimensionContable)hmDimensionContable3.get(codigoDimensionContable3);
/* 528:674 */           if (dimensionContable3 == null)
/* 529:    */           {
/* 530:675 */             dimensionContable3 = this.servicioDimensionContable.buscarPorCodigo("3", codigoDimensionContable3);
/* 531:676 */             hmDimensionContable3.put(codigoDimensionContable3, dimensionContable3);
/* 532:    */           }
/* 533:    */         }
/* 534:680 */         DimensionContable dimensionContable4 = null;
/* 535:681 */         if (codigoDimensionContable4 != null)
/* 536:    */         {
/* 537:683 */           dimensionContable4 = (DimensionContable)hmDimensionContable4.get(codigoDimensionContable4);
/* 538:685 */           if (dimensionContable4 == null)
/* 539:    */           {
/* 540:686 */             dimensionContable4 = this.servicioDimensionContable.buscarPorCodigo("4", codigoDimensionContable4);
/* 541:687 */             hmDimensionContable4.put(codigoDimensionContable4, dimensionContable4);
/* 542:    */           }
/* 543:    */         }
/* 544:691 */         DimensionContable dimensionContable5 = null;
/* 545:692 */         if (codigoDimensionContable5 != null)
/* 546:    */         {
/* 547:694 */           dimensionContable5 = (DimensionContable)hmDimensionContable5.get(codigoDimensionContable5);
/* 548:696 */           if (dimensionContable5 == null)
/* 549:    */           {
/* 550:697 */             dimensionContable5 = this.servicioDimensionContable.buscarPorCodigo("5", codigoDimensionContable5);
/* 551:698 */             hmDimensionContable5.put(codigoDimensionContable5, dimensionContable5);
/* 552:    */           }
/* 553:    */         }
/* 554:702 */         Asiento asiento = (Asiento)hmAsiento.get(numeroReferencial);
/* 555:703 */         if (asiento == null)
/* 556:    */         {
/* 557:705 */           asiento = new Asiento();
/* 558:706 */           asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 559:707 */           asiento.setSucursal(AppUtil.getSucursal());
/* 560:708 */           asiento.setTipoAsiento(tipoAsiento);
/* 561:709 */           asiento.setEstado(Estado.ELABORADO);
/* 562:710 */           asiento.setFecha(fecha);
/* 563:711 */           asiento.setConcepto(concepto);
/* 564:    */           
/* 565:713 */           hmAsiento.put(numeroReferencial, asiento);
/* 566:    */         }
/* 567:720 */         DetalleAsiento detalleAsiento = new DetalleAsiento();
/* 568:721 */         detalleAsiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 569:722 */         detalleAsiento.setIdOrganizacion(AppUtil.getSucursal().getId());
/* 570:723 */         detalleAsiento.setDescripcion(descripcion);
/* 571:724 */         detalleAsiento.setDescripcion2(descripcion2);
/* 572:725 */         detalleAsiento.setCuentaContable(cuentaContable);
/* 573:726 */         detalleAsiento.setDebe(debe);
/* 574:727 */         detalleAsiento.setHaber(haber);
/* 575:728 */         detalleAsiento.setAsiento(asiento);
/* 576:729 */         detalleAsiento.setDimensionContable1(dimensionContable1);
/* 577:730 */         detalleAsiento.setDimensionContable2(dimensionContable2);
/* 578:731 */         detalleAsiento.setDimensionContable3(dimensionContable3);
/* 579:732 */         detalleAsiento.setDimensionContable4(dimensionContable4);
/* 580:733 */         detalleAsiento.setDimensionContable5(dimensionContable5);
/* 581:734 */         asiento.getListaDetalleAsiento().add(detalleAsiento);
/* 582:737 */         if (cuentaContable.getTipoCuentaContable().equals(TipoCuentaContable.BANCO))
/* 583:    */         {
/* 584:738 */           CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.buscarPorCuentaContable(detalleAsiento
/* 585:739 */             .getCuentaContable().getId());
/* 586:740 */           if (cuentaBancariaOrganizacion == null) {
/* 587:742 */             throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.NO_EXISTE_CUENTA_BANCARIA_ORGANIZACION", new String[] {detalleAsiento.getCuentaContable().getCodigo() + " -> " + detalleAsiento.getCuentaContable().getNombre() });
/* 588:    */           }
/* 589:745 */           MovimientoBancario movimientoBancario = new MovimientoBancario();
/* 590:746 */           movimientoBancario.setEstado(Estado.CONTABILIZADO);
/* 591:747 */           movimientoBancario.setDetalleAsiento(detalleAsiento);
/* 592:748 */           movimientoBancario.setIdOrganizacion(asiento.getIdOrganizacion());
/* 593:749 */           movimientoBancario.setIdSucursal(asiento.getSucursal().getId());
/* 594:750 */           movimientoBancario.setBeneficiario("Migracion:  " + asiento.getFecha());
/* 595:752 */           if (detalleAsiento.getDebe().compareTo(BigDecimal.ZERO) == 1) {
/* 596:753 */             movimientoBancario.setValor(detalleAsiento.getDebe());
/* 597:    */           }
/* 598:755 */           if (detalleAsiento.getHaber().compareTo(BigDecimal.ZERO) == 1) {
/* 599:756 */             movimientoBancario.setValor(detalleAsiento.getHaber().negate());
/* 600:    */           }
/* 601:759 */           movimientoBancario.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/* 602:760 */           movimientoBancario.setDocumento(documento);
/* 603:761 */           movimientoBancario.setDescripcion(asiento.getConcepto());
/* 604:762 */           movimientoBancario.setDocumentoReferencia(documentoReferencia);
/* 605:763 */           movimientoBancario.setFecha(asiento.getFecha());
/* 606:764 */           movimientoBancario.setFormaPago(formaPago);
/* 607:765 */           detalleAsiento.setMovimientoBancario(movimientoBancario);
/* 608:    */         }
/* 609:767 */         filaActual++;
/* 610:    */       }
/* 611:770 */       for (??? = hmAsiento.values().iterator(); ((Iterator)???).hasNext();)
/* 612:    */       {
/* 613:770 */         Asiento a = (Asiento)((Iterator)???).next();
/* 614:771 */         guardar(a);
/* 615:    */       }
/* 616:    */     }
/* 617:    */     catch (AS2Exception e)
/* 618:    */     {
/* 619:775 */       LOG.error("Error al migrar asiento", e);
/* 620:776 */       this.context.setRollbackOnly();
/* 621:777 */       throw e;
/* 622:    */     }
/* 623:    */     catch (IllegalArgumentException e)
/* 624:    */     {
/* 625:779 */       LOG.info("Error al migrar asiento", e);
/* 626:780 */       this.context.setRollbackOnly();
/* 627:    */       
/* 628:782 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 629:    */     }
/* 630:    */     catch (IllegalStateException e)
/* 631:    */     {
/* 632:784 */       LOG.info("Error al migrar asiento", e);
/* 633:785 */       this.context.setRollbackOnly();
/* 634:    */       
/* 635:787 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 636:    */     }
/* 637:    */     catch (ExcepcionAS2Financiero e)
/* 638:    */     {
/* 639:789 */       LOG.error("Error al migrar asiento", e);
/* 640:790 */       this.context.setRollbackOnly();
/* 641:791 */       throw e;
/* 642:    */     }
/* 643:    */     catch (ExcepcionAS2 e)
/* 644:    */     {
/* 645:793 */       LOG.error("Error al migrar asiento", e);
/* 646:794 */       this.context.setRollbackOnly();
/* 647:795 */       throw e;
/* 648:    */     }
/* 649:    */     catch (Exception e)
/* 650:    */     {
/* 651:797 */       LOG.error("Error al migrar asiento", e);
/* 652:798 */       this.context.setRollbackOnly();
/* 653:799 */       throw new ExcepcionAS2(e);
/* 654:    */     }
/* 655:    */   }
/* 656:    */   
/* 657:    */   public void eliminarCheque(Asiento asiento)
/* 658:    */     throws ExcepcionAS2Financiero
/* 659:    */   {
/* 660:812 */     if (asiento.getEstado().equals(Estado.ANULADO))
/* 661:    */     {
/* 662:813 */       List<MovimientoBancario> listaMovimientoBancario = this.movimientoBancarioDao.getListaMovimientoBancarioPorAsiento(asiento.getIdAsiento());
/* 663:815 */       for (MovimientoBancario movimientoBancario : listaMovimientoBancario)
/* 664:    */       {
/* 665:817 */         for (MovimientoBancarioEstadoCheque movimientoBancarioEstadoCheque : movimientoBancario.getListaMovimientoBancarioEstadoCheque())
/* 666:    */         {
/* 667:818 */           movimientoBancarioEstadoCheque.setEliminado(true);
/* 668:819 */           this.movimientoBancarioEstadoChequeDao.guardar(movimientoBancarioEstadoCheque);
/* 669:    */         }
/* 670:822 */         if (movimientoBancario.getFormaPago().isIndicadorCheque())
/* 671:    */         {
/* 672:823 */           movimientoBancario.setEliminado(true);
/* 673:824 */           this.movimientoBancarioDao.guardar(movimientoBancario);
/* 674:    */         }
/* 675:    */       }
/* 676:    */     }
/* 677:    */     else
/* 678:    */     {
/* 679:828 */       throw new ExcepcionAS2Financiero("msg_error_eliminar_cheque_anulado");
/* 680:    */     }
/* 681:    */   }
/* 682:    */   
/* 683:    */   private void validarDimensiones(Asiento asiento)
/* 684:    */     throws ExcepcionAS2
/* 685:    */   {
/* 686:835 */     for (DetalleAsiento detalle : asiento.getListaDetalleAsiento()) {
/* 687:837 */       if (!detalle.isEliminado())
/* 688:    */       {
/* 689:838 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension1()) && (detalle.getDimensionContable1() == null))
/* 690:    */         {
/* 691:840 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "1" });
/* 692:841 */           throw new ExcepcionAS2("", exception.getMessage());
/* 693:    */         }
/* 694:843 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension2()) && (detalle.getDimensionContable2() == null))
/* 695:    */         {
/* 696:845 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "2" });
/* 697:846 */           throw new ExcepcionAS2("", exception.getMessage());
/* 698:    */         }
/* 699:848 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension3()) && (detalle.getDimensionContable3() == null))
/* 700:    */         {
/* 701:850 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "3" });
/* 702:851 */           throw new ExcepcionAS2("", exception.getMessage());
/* 703:    */         }
/* 704:853 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension4()) && (detalle.getDimensionContable4() == null))
/* 705:    */         {
/* 706:855 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "4" });
/* 707:856 */           throw new ExcepcionAS2("", exception.getMessage());
/* 708:    */         }
/* 709:858 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension5()) && (detalle.getDimensionContable5() == null))
/* 710:    */         {
/* 711:860 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "5" });
/* 712:861 */           throw new ExcepcionAS2("", exception.getMessage());
/* 713:    */         }
/* 714:    */       }
/* 715:    */     }
/* 716:    */   }
/* 717:    */   
/* 718:    */   public Asiento cargarPlantillaAsiento(Asiento asiento, PlantillaAsiento plantillaAsiento, BigDecimal valor)
/* 719:    */   {
/* 720:877 */     PlantillaAsiento plantilla = this.servicioPlantillaAsiento.cargarDetalle(Integer.valueOf(plantillaAsiento.getId()));
/* 721:878 */     BigDecimal factor = new BigDecimal(1).setScale(2);
/* 722:879 */     if (plantilla.isIndicadorPorcentaje()) {
/* 723:880 */       factor = valor.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
/* 724:    */     }
/* 725:883 */     BigDecimal totalDebe = BigDecimal.ZERO.setScale(2);
/* 726:884 */     BigDecimal totalHaber = BigDecimal.ZERO.setScale(2);
/* 727:    */     
/* 728:886 */     DetalleAsiento detalleAsiento = null;
/* 729:887 */     for (DetallePlantillaAsiento detallePlantilla : plantilla.getListaDetallePlantillaAsiento()) {
/* 730:888 */       if (detallePlantilla.getDebe().compareTo(BigDecimal.ZERO) > 0)
/* 731:    */       {
/* 732:889 */         detalleAsiento = new DetalleAsiento();
/* 733:890 */         detalleAsiento.setAsiento(asiento);
/* 734:891 */         detalleAsiento.setCuentaContable(detallePlantilla.getCuentaContable());
/* 735:892 */         detalleAsiento.setDebe(detallePlantilla.getDebe().multiply(factor).setScale(2, RoundingMode.HALF_UP));
/* 736:893 */         detalleAsiento.setDimensionContable1(detallePlantilla.getDimensionContable1());
/* 737:894 */         detalleAsiento.setDimensionContable2(detallePlantilla.getDimensionContable2());
/* 738:895 */         detalleAsiento.setDimensionContable3(detallePlantilla.getDimensionContable3());
/* 739:896 */         detalleAsiento.setDimensionContable4(detallePlantilla.getDimensionContable4());
/* 740:897 */         detalleAsiento.setDimensionContable5(detallePlantilla.getDimensionContable5());
/* 741:898 */         asiento.getListaDetalleAsiento().add(detalleAsiento);
/* 742:    */         
/* 743:900 */         totalDebe = totalDebe.add(detalleAsiento.getDebe());
/* 744:    */       }
/* 745:    */     }
/* 746:904 */     if ((plantilla.isIndicadorPorcentaje()) && (detalleAsiento != null) && (totalDebe.compareTo(BigDecimal.ZERO) != 0) && 
/* 747:905 */       (totalDebe.compareTo(valor) != 0)) {
/* 748:906 */       detalleAsiento.setDebe(detalleAsiento.getDebe().add(valor.subtract(totalDebe)));
/* 749:    */     }
/* 750:909 */     detalleAsiento = null;
/* 751:910 */     for (DetallePlantillaAsiento detallePlantilla : plantilla.getListaDetallePlantillaAsiento()) {
/* 752:911 */       if (detallePlantilla.getHaber().compareTo(BigDecimal.ZERO) > 0)
/* 753:    */       {
/* 754:912 */         detalleAsiento = new DetalleAsiento();
/* 755:913 */         detalleAsiento.setAsiento(asiento);
/* 756:914 */         detalleAsiento.setCuentaContable(detallePlantilla.getCuentaContable());
/* 757:915 */         detalleAsiento.setHaber(detallePlantilla.getHaber().multiply(factor).setScale(2, RoundingMode.HALF_UP));
/* 758:916 */         detalleAsiento.setDimensionContable1(detallePlantilla.getDimensionContable1());
/* 759:917 */         detalleAsiento.setDimensionContable2(detallePlantilla.getDimensionContable2());
/* 760:918 */         detalleAsiento.setDimensionContable3(detallePlantilla.getDimensionContable3());
/* 761:919 */         detalleAsiento.setDimensionContable4(detallePlantilla.getDimensionContable4());
/* 762:920 */         detalleAsiento.setDimensionContable5(detallePlantilla.getDimensionContable5());
/* 763:921 */         asiento.getListaDetalleAsiento().add(detalleAsiento);
/* 764:    */         
/* 765:923 */         totalHaber = totalHaber.add(detalleAsiento.getHaber());
/* 766:    */       }
/* 767:    */     }
/* 768:926 */     if ((plantilla.isIndicadorPorcentaje()) && (detalleAsiento != null) && (totalHaber.compareTo(BigDecimal.ZERO) != 0) && 
/* 769:927 */       (totalHaber.compareTo(valor) != 0)) {
/* 770:928 */       detalleAsiento.setHaber(detalleAsiento.getHaber().add(valor.subtract(totalHaber)));
/* 771:    */     }
/* 772:931 */     return asiento;
/* 773:    */   }
/* 774:    */   
/* 775:    */   public void calcularTotales(Asiento asiento)
/* 776:    */   {
/* 777:941 */     BigDecimal totalDebe = BigDecimal.ZERO;
/* 778:942 */     BigDecimal totalHaber = BigDecimal.ZERO;
/* 779:944 */     for (DetalleAsiento detalle : asiento.getListaDetalleAsiento()) {
/* 780:945 */       if (!detalle.isEliminado())
/* 781:    */       {
/* 782:946 */         totalDebe = totalDebe.add(detalle.getDebe());
/* 783:947 */         totalHaber = totalHaber.add(detalle.getHaber());
/* 784:    */       }
/* 785:    */     }
/* 786:951 */     asiento.setTotalDebe(totalDebe.setScale(2, RoundingMode.HALF_UP));
/* 787:952 */     asiento.setTotalHaber(totalHaber.setScale(2, RoundingMode.HALF_UP));
/* 788:    */   }
/* 789:    */   
/* 790:    */   public void actualizarIndicadorAsientoAerolineas(Integer idAsiento)
/* 791:    */   {
/* 792:958 */     this.asientoDao.actualizarIndicadorAsientoAerolineas(idAsiento);
/* 793:    */   }
/* 794:    */   
/* 795:    */   public Long verificarExistenciaNumero(Asiento asiento)
/* 796:    */   {
/* 797:964 */     return this.asientoDao.verificarExistenciaNumero(asiento);
/* 798:    */   }
/* 799:    */   
/* 800:    */   private void generarEstadoInicialCheque(Asiento asiento)
/* 801:    */   {
/* 802:973 */     for (DetalleAsiento da : asiento.getListaDetalleAsiento()) {
/* 803:975 */       if ((!da.isEliminado()) && (da.getMovimientoBancario() != null) && (da.getMovimientoBancario().getEstadoCheque() == null) && 
/* 804:976 */         (da.getMovimientoBancario().getValor().compareTo(BigDecimal.ZERO) < 0))
/* 805:    */       {
/* 806:980 */         FormaPago formaPago = this.servicioFormaPago.buscarPorId(Integer.valueOf(da.getMovimientoBancario().getFormaPago().getId()));
/* 807:981 */         da.getMovimientoBancario().setFormaPago(formaPago);
/* 808:982 */         if (formaPago.isIndicadorCheque())
/* 809:    */         {
/* 810:983 */           EstadoCheque ecInicial = this.estadoChequeDao.getEstadoInicial(da.getMovimientoBancario().getIdOrganizacion());
/* 811:984 */           MovimientoBancarioEstadoCheque mbec = new MovimientoBancarioEstadoCheque();
/* 812:985 */           mbec.setEstadoCheque(ecInicial);
/* 813:986 */           mbec.setMovimientoBancario(da.getMovimientoBancario());
/* 814:987 */           da.getMovimientoBancario().setEstadoCheque(ecInicial);
/* 815:988 */           da.getMovimientoBancario().setMovEstadoChequeInicial(mbec);
/* 816:    */         }
/* 817:    */       }
/* 818:    */     }
/* 819:    */   }
/* 820:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl
 * JD-Core Version:    0.7.0.1
 */