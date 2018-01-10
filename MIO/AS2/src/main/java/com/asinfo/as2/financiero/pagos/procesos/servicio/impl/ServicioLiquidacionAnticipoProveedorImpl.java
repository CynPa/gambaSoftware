/*   1:    */ package com.asinfo.as2.financiero.pagos.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioVerificadorCompras;
/*   6:    */ import com.asinfo.as2.dao.AnticipoProveedorDao;
/*   7:    */ import com.asinfo.as2.dao.DetalleLiquidacionAnticipoProveedorDao;
/*   8:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*   9:    */ import com.asinfo.as2.dao.LiquidacionAnticipoProveedorDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  11:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*  12:    */ import com.asinfo.as2.entities.Asiento;
/*  13:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  14:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  15:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  16:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor;
/*  17:    */ import com.asinfo.as2.entities.Documento;
/*  18:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  19:    */ import com.asinfo.as2.entities.Empresa;
/*  20:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  21:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*  22:    */ import com.asinfo.as2.entities.Organizacion;
/*  23:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  24:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  25:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  26:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  27:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  28:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  30:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  31:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  33:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  34:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  35:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  36:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioLiquidacionAnticipoProveedor;
/*  37:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  38:    */ import com.asinfo.as2.util.AppUtil;
/*  39:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  40:    */ import java.math.BigDecimal;
/*  41:    */ import java.math.RoundingMode;
/*  42:    */ import java.util.ArrayList;
/*  43:    */ import java.util.Collection;
/*  44:    */ import java.util.Date;
/*  45:    */ import java.util.HashMap;
/*  46:    */ import java.util.List;
/*  47:    */ import java.util.Map;
/*  48:    */ import javax.ejb.EJB;
/*  49:    */ import javax.ejb.SessionContext;
/*  50:    */ import javax.ejb.Stateless;
/*  51:    */ import javax.ejb.TransactionAttribute;
/*  52:    */ import javax.ejb.TransactionAttributeType;
/*  53:    */ import javax.ejb.TransactionManagement;
/*  54:    */ import javax.ejb.TransactionManagementType;
/*  55:    */ 
/*  56:    */ @Stateless
/*  57:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  58:    */ public class ServicioLiquidacionAnticipoProveedorImpl
/*  59:    */   extends AbstractServicioAS2Financiero
/*  60:    */   implements ServicioLiquidacionAnticipoProveedor
/*  61:    */ {
/*  62:    */   private static final long serialVersionUID = 6116475103775318727L;
/*  63:    */   @EJB
/*  64:    */   private LiquidacionAnticipoProveedorDao liquidacionAnticipoProveedorDao;
/*  65:    */   @EJB
/*  66:    */   private FacturaProveedorDao facturaProveedorDao;
/*  67:    */   @EJB
/*  68:    */   private ServicioPeriodo servicioPeriodo;
/*  69:    */   @EJB
/*  70:    */   private ServicioSecuencia servicioSecuencia;
/*  71:    */   @EJB
/*  72:    */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  73:    */   @EJB
/*  74:    */   private AnticipoProveedorDao anticipoProveedorDao;
/*  75:    */   @EJB
/*  76:    */   private DetalleLiquidacionAnticipoProveedorDao detalleLiquidacionAnticipoProveedorDao;
/*  77:    */   @EJB
/*  78:    */   private ServicioVerificadorCompras servicioVerificadorCompras;
/*  79:    */   @EJB
/*  80:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  81:    */   @EJB
/*  82:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  83:    */   @EJB
/*  84:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  85:    */   @EJB
/*  86:    */   private ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  87:    */   
/*  88:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  89:    */   public void guardar(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/*  90:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:114 */       validaFechaLiquidacionAnticipo(liquidacionAnticipoProveedor.getAnticipoProveedor().getFecha(), liquidacionAnticipoProveedor.getFecha());
/*  95:115 */       validar(liquidacionAnticipoProveedor);
/*  96:    */       
/*  97:    */ 
/*  98:118 */       this.servicioVerificadorCompras.actualizarCuentaPorPagar(liquidacionAnticipoProveedor, false);
/*  99:    */       
/* 100:    */ 
/* 101:121 */       cargarSecuencia(liquidacionAnticipoProveedor);
/* 102:    */       
/* 103:123 */       BigDecimal valorLiquidacion = BigDecimal.ZERO;
/* 104:126 */       for (DetalleLiquidacionAnticipoProveedor dLiquidacion : liquidacionAnticipoProveedor.getListaDetalleLiquidacionAnticipoProveedor()) {
/* 105:127 */         if (dLiquidacion.getValor().compareTo(BigDecimal.ZERO) != 0)
/* 106:    */         {
/* 107:129 */           if (!dLiquidacion.isEliminado()) {
/* 108:130 */             valorLiquidacion = valorLiquidacion.add(dLiquidacion.getValor());
/* 109:    */           }
/* 110:133 */           this.detalleLiquidacionAnticipoProveedorDao.guardar(dLiquidacion);
/* 111:    */         }
/* 112:    */       }
/* 113:138 */       BigDecimal saldoAnticipo = liquidacionAnticipoProveedor.getAnticipoProveedor().getSaldo();
/* 114:139 */       saldoAnticipo = saldoAnticipo.subtract(valorLiquidacion);
/* 115:140 */       liquidacionAnticipoProveedor.getAnticipoProveedor().setSaldo(saldoAnticipo);
/* 116:141 */       this.anticipoProveedorDao.guardar(liquidacionAnticipoProveedor.getAnticipoProveedor());
/* 117:    */       
/* 118:    */ 
/* 119:144 */       this.liquidacionAnticipoProveedorDao.guardar(liquidacionAnticipoProveedor);
/* 120:    */       
/* 121:    */ 
/* 122:147 */       contabilizar(liquidacionAnticipoProveedor);
/* 123:    */     }
/* 124:    */     catch (ExcepcionAS2Financiero e)
/* 125:    */     {
/* 126:150 */       this.context.setRollbackOnly();
/* 127:151 */       throw e;
/* 128:    */     }
/* 129:    */     catch (ExcepcionAS2 e)
/* 130:    */     {
/* 131:153 */       this.context.setRollbackOnly();
/* 132:154 */       throw e;
/* 133:    */     }
/* 134:    */     catch (Exception e)
/* 135:    */     {
/* 136:156 */       this.context.setRollbackOnly();
/* 137:157 */       throw new ExcepcionAS2(e);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void eliminar(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 142:    */   {
/* 143:169 */     this.liquidacionAnticipoProveedorDao.eliminar(liquidacionAnticipoProveedor);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public LiquidacionAnticipoProveedor buscarPorId(Integer id)
/* 147:    */   {
/* 148:179 */     return (LiquidacionAnticipoProveedor)this.liquidacionAnticipoProveedorDao.buscarPorId(id);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public LiquidacionAnticipoProveedor cargarDetalle(int idLiquidacionAnticipoProveedor)
/* 152:    */   {
/* 153:188 */     return this.liquidacionAnticipoProveedorDao.cargarDetalle(idLiquidacionAnticipoProveedor);
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void validar(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 157:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 158:    */   {
/* 159:193 */     this.servicioPeriodo.buscarPorFecha(liquidacionAnticipoProveedor.getFecha(), liquidacionAnticipoProveedor.getIdOrganizacion(), liquidacionAnticipoProveedor
/* 160:194 */       .getDocumento().getDocumentoBase());
/* 161:    */     
/* 162:196 */     BigDecimal saldoAnticipoProveedor = this.servicioAnticipoProveedor.getSaldoAnticipoPreveedor(liquidacionAnticipoProveedor.getAnticipoProveedor());
/* 163:197 */     if (saldoAnticipoProveedor.compareTo(BigDecimal.ZERO) == 0) {
/* 164:198 */       throw new ExcepcionAS2Financiero("msg_info_cobro_0001");
/* 165:    */     }
/* 166:202 */     BigDecimal saldoAnticipo = liquidacionAnticipoProveedor.getAnticipoProveedor().getSaldo();
/* 167:203 */     liquidacionAnticipoProveedor.setValor(BigDecimal.ZERO);
/* 168:204 */     for (DetalleLiquidacionAnticipoProveedor detalleCobro : liquidacionAnticipoProveedor.getListaDetalleLiquidacionAnticipoProveedor())
/* 169:    */     {
/* 170:205 */       saldoAnticipo = saldoAnticipo.subtract(detalleCobro.getValor());
/* 171:206 */       liquidacionAnticipoProveedor.setValor(liquidacionAnticipoProveedor.getValor().add(detalleCobro.getValor()));
/* 172:208 */       if (detalleCobro.getValor().compareTo(detalleCobro.getCuentaPorPagar().getSaldo()) > 0) {
/* 173:209 */         throw new ExcepcionAS2Financiero("msg_info_cobro_0002");
/* 174:    */       }
/* 175:    */     }
/* 176:213 */     if (saldoAnticipo.compareTo(BigDecimal.ZERO) < 0) {
/* 177:214 */       throw new ExcepcionAS2Financiero("msg_info_cobro_0001");
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   private void cargarSecuencia(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 182:    */     throws ExcepcionAS2
/* 183:    */   {
/* 184:225 */     if (liquidacionAnticipoProveedor.getNumero().equals(""))
/* 185:    */     {
/* 186:226 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(liquidacionAnticipoProveedor.getDocumento().getIdDocumento(), liquidacionAnticipoProveedor
/* 187:227 */         .getFecha());
/* 188:228 */       liquidacionAnticipoProveedor.setNumero(numero);
/* 189:    */     }
/* 190:    */     else
/* 191:    */     {
/* 192:230 */       this.servicioSecuencia.actualizarSecuencia(liquidacionAnticipoProveedor.getDocumento().getSecuencia(), liquidacionAnticipoProveedor
/* 193:231 */         .getNumero());
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void contabilizar(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 198:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 199:    */   {
/* 200:243 */     if (liquidacionAnticipoProveedor.getAnticipoProveedor().getIndicadorContabilizar().booleanValue())
/* 201:    */     {
/* 202:244 */       Date fechaContabilizacion = liquidacionAnticipoProveedor.getFecha();
/* 203:    */       Asiento asiento;
/* 204:    */       Asiento asiento;
/* 205:    */       TipoAsiento tipoAsiento;
/* 206:247 */       if (liquidacionAnticipoProveedor.getAsiento() != null)
/* 207:    */       {
/* 208:248 */         asiento = this.servicioAsiento.cargarDetalle(liquidacionAnticipoProveedor.getAsiento().getId());
/* 209:    */       }
/* 210:    */       else
/* 211:    */       {
/* 212:250 */         asiento = new Asiento();
/* 213:251 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 214:252 */         asiento.setSucursal(AppUtil.getSucursal());
/* 215:    */         
/* 216:254 */         tipoAsiento = liquidacionAnticipoProveedor.getDocumento().getTipoAsiento();
/* 217:255 */         asiento.setTipoAsiento(tipoAsiento);
/* 218:    */       }
/* 219:257 */       for (DetalleAsiento dt : asiento.getListaDetalleAsiento()) {
/* 220:258 */         dt.setEliminado(true);
/* 221:    */       }
/* 222:261 */       String concepto = "";
/* 223:262 */       concepto = liquidacionAnticipoProveedor.getDocumento().getNombre().trim() + " #" + liquidacionAnticipoProveedor.getNumero();
/* 224:263 */       asiento.setConcepto(concepto);
/* 225:264 */       asiento.setFecha(fechaContabilizacion);
/* 226:265 */       asiento.setEstado(Estado.ELABORADO);
/* 227:266 */       asiento.setIndicadorAutomatico(true);
/* 228:267 */       asiento.setDocumentoOrigen(liquidacionAnticipoProveedor.getDocumento());
/* 229:    */       
/* 230:269 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(liquidacionAnticipoProveedor.getIdOrganizacion(), DocumentoBase.ANTICIPO_PROVEEDOR);
/* 231:    */       
/* 232:    */ 
/* 233:272 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(liquidacionAnticipoProveedor.getIdOrganizacion(), DocumentoBase.ANTICIPO_PROVEEDOR);
/* 234:    */       
/* 235:274 */       List<Integer> list = new ArrayList();
/* 236:275 */       list.add(Integer.valueOf(liquidacionAnticipoProveedor.getAnticipoProveedor().getIdAnticipoProveedor()));
/* 237:276 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 238:    */       
/* 239:278 */       DocumentoContabilizacion documentoContabilizacion = new DocumentoContabilizacion();
/* 240:280 */       for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/* 241:281 */         documentoContabilizacion = dc;
/* 242:    */       }
/* 243:284 */       BigDecimal valorFactura = BigDecimal.ZERO;
/* 244:285 */       BigDecimal valorAnticipo = BigDecimal.ZERO;
/* 245:286 */       BigDecimal pagoTotal = BigDecimal.ZERO;
/* 246:287 */       BigDecimal diferencia = BigDecimal.ZERO;
/* 247:288 */       BigDecimal valorPago = BigDecimal.ZERO;
/* 248:    */       
/* 249:290 */       list = new ArrayList();
/* 250:    */       
/* 251:292 */       list.add(Integer.valueOf(liquidacionAnticipoProveedor.getAnticipoProveedor().getId()));
/* 252:    */       
/* 253:294 */       lista.addAll(this.anticipoProveedorDao.getInterfazAnticipoProveedorDimensiones(list));
/* 254:    */       
/* 255:296 */       list = new ArrayList();
/* 256:297 */       for (DetalleLiquidacionAnticipoProveedor dl : liquidacionAnticipoProveedor.getListaDetalleLiquidacionAnticipoProveedor()) {
/* 257:298 */         if (dl.getValor().compareTo(BigDecimal.ZERO) != 0)
/* 258:    */         {
/* 259:299 */           list.add(Integer.valueOf(dl.getCuentaPorPagar().getFacturaProveedor().getIdFacturaProveedor()));
/* 260:300 */           valorPago = valorPago.add(dl.getValor());
/* 261:    */         }
/* 262:    */       }
/* 263:303 */       for (DetalleInterfazContableProceso detalleInterfazContableProceso : lista) {
/* 264:304 */         valorAnticipo = valorAnticipo.add(detalleInterfazContableProceso.getValor());
/* 265:    */       }
/* 266:306 */       for (DetalleInterfazContableProceso detalleInterfazContableProceso : lista)
/* 267:    */       {
/* 268:308 */         detalleInterfazContableProceso.setValor(valorPago.multiply(detalleInterfazContableProceso.getValor()).divide(valorAnticipo, 24, RoundingMode.HALF_UP));
/* 269:309 */         pagoTotal = pagoTotal.add(detalleInterfazContableProceso.getValor());
/* 270:    */       }
/* 271:311 */       diferencia = pagoTotal.subtract(valorPago);
/* 272:312 */       if ((lista.size() > 0) && 
/* 273:313 */         (diferencia.compareTo(BigDecimal.ZERO) != 0)) {
/* 274:314 */         ((DetalleInterfazContableProceso)lista.get(0)).setValor(((DetalleInterfazContableProceso)lista.get(0)).getValor().add(diferencia));
/* 275:    */       }
/* 276:317 */       asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 277:318 */         .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true, true));
/* 278:    */       
/* 279:    */ 
/* 280:321 */       listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(liquidacionAnticipoProveedor.getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/* 281:    */       
/* 282:323 */       listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(liquidacionAnticipoProveedor.getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/* 283:    */       
/* 284:    */ 
/* 285:326 */       lista = new ArrayList();
/* 286:328 */       for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/* 287:329 */         if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.CXP_PROVEEDOR)) {
/* 288:330 */           documentoContabilizacion = dc;
/* 289:    */         }
/* 290:    */       }
/* 291:334 */       valorFactura = BigDecimal.ZERO;
/* 292:335 */       Object listaTmp = new ArrayList();
/* 293:336 */       Map<Integer, DetalleLiquidacionAnticipoProveedor> mapaCobroFactura = new HashMap();
/* 294:337 */       for (DetalleLiquidacionAnticipoProveedor dp : liquidacionAnticipoProveedor.getListaDetalleLiquidacionAnticipoProveedor())
/* 295:    */       {
/* 296:338 */         this.detalleLiquidacionAnticipoProveedorDao.detach(dp);
/* 297:339 */         clave = Integer.valueOf(dp.getCuentaPorPagar().getFacturaProveedor().getId());
/* 298:340 */         DetalleLiquidacionAnticipoProveedor d = (DetalleLiquidacionAnticipoProveedor)mapaCobroFactura.get(clave);
/* 299:341 */         if (d == null) {
/* 300:342 */           mapaCobroFactura.put(clave, dp);
/* 301:    */         } else {
/* 302:344 */           d.setValor(d.getValor().add(dp.getValor()));
/* 303:    */         }
/* 304:    */       }
/* 305:    */       Integer clave;
/* 306:349 */       for (DetalleLiquidacionAnticipoProveedor dc : mapaCobroFactura.values()) {
/* 307:350 */         if (dc.getValor().compareTo(BigDecimal.ZERO) != 0)
/* 308:    */         {
/* 309:351 */           list = new ArrayList();
/* 310:352 */           list.add(Integer.valueOf(dc.getCuentaPorPagar().getFacturaProveedor().getIdFacturaProveedor()));
/* 311:353 */           listaTmp = this.facturaProveedorDao.getInterfazFacturaProveedorDimensiones(list, documentoContabilizacion.getProcesoContabilizacion());
/* 312:354 */           valorFactura = BigDecimal.ZERO;
/* 313:355 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : (List)listaTmp) {
/* 314:356 */             valorFactura = valorFactura.add(detalleInterfazContableProceso.getValor());
/* 315:    */           }
/* 316:358 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : (List)listaTmp) {
/* 317:359 */             detalleInterfazContableProceso.setValor(dc
/* 318:360 */               .getValor().multiply(detalleInterfazContableProceso.getValor()).divide(valorFactura, 24, RoundingMode.HALF_UP));
/* 319:    */           }
/* 320:362 */           lista.addAll((Collection)listaTmp);
/* 321:    */         }
/* 322:    */       }
/* 323:367 */       Object listaDetalleAsiento = this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true, true);
/* 324:    */       
/* 325:369 */       asiento.getListaDetalleAsiento().addAll((Collection)listaDetalleAsiento);
/* 326:    */       
/* 327:    */ 
/* 328:372 */       this.servicioAsiento.guardar(asiento);
/* 329:    */       
/* 330:    */ 
/* 331:375 */       liquidacionAnticipoProveedor.setEstado(Estado.CONTABILIZADO);
/* 332:    */       
/* 333:377 */       liquidacionAnticipoProveedor.setFechaContabilizacion(fechaContabilizacion);
/* 334:    */       
/* 335:    */ 
/* 336:380 */       liquidacionAnticipoProveedor.setAsiento(asiento);
/* 337:381 */       this.liquidacionAnticipoProveedorDao.guardar(liquidacionAnticipoProveedor);
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void esEditable(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 342:    */     throws ExcepcionAS2Financiero
/* 343:    */   {
/* 344:387 */     this.servicioPeriodo.buscarPorFecha(liquidacionAnticipoProveedor.getFecha(), liquidacionAnticipoProveedor.getIdOrganizacion(), liquidacionAnticipoProveedor
/* 345:388 */       .getDocumento().getDocumentoBase());
/* 346:389 */     if ((liquidacionAnticipoProveedor.getAsiento() != null) && (liquidacionAnticipoProveedor.getAsiento().getEstado() == Estado.REVISADO)) {
/* 347:390 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 348:    */     }
/* 349:392 */     if (liquidacionAnticipoProveedor.getEstado() == Estado.ANULADO) {
/* 350:393 */       throw new ExcepcionAS2Financiero("msg_error_anular");
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void actualizarEstado(int idLiquidacionAnticipoProveedor, Estado estado)
/* 355:    */   {
/* 356:405 */     this.liquidacionAnticipoProveedorDao.actualizarEstado(Integer.valueOf(idLiquidacionAnticipoProveedor), estado);
/* 357:    */   }
/* 358:    */   
/* 359:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 360:    */   public void anular(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 361:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 362:    */   {
/* 363:    */     try
/* 364:    */     {
/* 365:415 */       esEditable(liquidacionAnticipoProveedor);
/* 366:    */       
/* 367:417 */       this.servicioVerificadorCompras.actualizarCuentaPorPagar(liquidacionAnticipoProveedor, true);
/* 368:    */       
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:423 */       AnticipoProveedor anticipoProveedor = (AnticipoProveedor)this.anticipoProveedorDao.buscarPorId(Integer.valueOf(liquidacionAnticipoProveedor.getAnticipoProveedor().getId()));
/* 374:424 */       anticipoProveedor.setSaldo(anticipoProveedor.getSaldo().add(liquidacionAnticipoProveedor.getValor()));
/* 375:425 */       this.anticipoProveedorDao.flush();
/* 376:    */       
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:431 */       actualizarEstado(liquidacionAnticipoProveedor.getId(), Estado.ANULADO);
/* 382:432 */       if (liquidacionAnticipoProveedor.getAsiento() != null)
/* 383:    */       {
/* 384:433 */         liquidacionAnticipoProveedor.getAsiento().setIndicadorAutomatico(false);
/* 385:434 */         this.servicioAsiento.anular(liquidacionAnticipoProveedor.getAsiento());
/* 386:    */       }
/* 387:    */     }
/* 388:    */     catch (ExcepcionAS2Financiero e)
/* 389:    */     {
/* 390:437 */       e.printStackTrace();
/* 391:438 */       this.context.setRollbackOnly();
/* 392:439 */       throw e;
/* 393:    */     }
/* 394:    */     catch (Exception e)
/* 395:    */     {
/* 396:442 */       e.printStackTrace();
/* 397:443 */       this.context.setRollbackOnly();
/* 398:444 */       throw new ExcepcionAS2(e);
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   public int contarPorCriterio(Map<String, String> filters)
/* 403:    */   {
/* 404:455 */     return this.liquidacionAnticipoProveedorDao.contarPorCriterio(filters);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public List<LiquidacionAnticipoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 408:    */   {
/* 409:467 */     return this.liquidacionAnticipoProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void validaFechaLiquidacionAnticipo(Date fechaAnticipoProveedor, Date fechaLiquidacion)
/* 413:    */     throws ExcepcionAS2Financiero
/* 414:    */   {
/* 415:479 */     Date fechaAnticipoProveedorAux = FuncionesUtiles.setAtributoFecha(fechaAnticipoProveedor);
/* 416:    */     
/* 417:    */ 
/* 418:482 */     Date fechaLiquidacionAux = FuncionesUtiles.setAtributoFecha(fechaLiquidacion);
/* 419:484 */     if (!FuncionesUtiles.compararFechaAnteriorOIgual(fechaAnticipoProveedorAux, fechaLiquidacionAux)) {
/* 420:485 */       throw new ExcepcionAS2Financiero("msg_error_fecha_anticipo_liquidacion");
/* 421:    */     }
/* 422:    */   }
/* 423:    */   
/* 424:    */   public LiquidacionAnticipoProveedor cargarFacturasPendientes(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor, int idFacturaProveedor)
/* 425:    */   {
/* 426:498 */     liquidacionAnticipoProveedor.getListaDetalleLiquidacionAnticipoProveedor().clear();
/* 427:    */     
/* 428:500 */     int idEmpresa = liquidacionAnticipoProveedor.getAnticipoProveedor().getEmpresa().getId();
/* 429:    */     
/* 430:502 */     List<CuentaPorPagar> listaFacturasPendientes = this.servicioFacturaProveedor.obtenerFacturasPendientes(liquidacionAnticipoProveedor
/* 431:503 */       .getAnticipoProveedor().getIdOrganizacion(), idEmpresa, idFacturaProveedor, null);
/* 432:    */     
/* 433:505 */     BigDecimal valorPago = liquidacionAnticipoProveedor.getAnticipoProveedor().getSaldo();
/* 434:506 */     for (CuentaPorPagar cxc : listaFacturasPendientes)
/* 435:    */     {
/* 436:507 */       DetalleLiquidacionAnticipoProveedor detalleLiquidacionAnticipoProveedor = new DetalleLiquidacionAnticipoProveedor();
/* 437:508 */       detalleLiquidacionAnticipoProveedor.setLiquidacionAnticipoProveedor(liquidacionAnticipoProveedor);
/* 438:509 */       detalleLiquidacionAnticipoProveedor.setCuentaPorPagar(cxc);
/* 439:    */       
/* 440:511 */       BigDecimal saldoCuentaPorCobra = cxc.getSaldo();
/* 441:512 */       BigDecimal valor = BigDecimal.ZERO;
/* 442:514 */       if (valorPago.compareTo(saldoCuentaPorCobra) > 0) {
/* 443:515 */         valor = saldoCuentaPorCobra;
/* 444:    */       } else {
/* 445:517 */         valor = valorPago;
/* 446:    */       }
/* 447:520 */       detalleLiquidacionAnticipoProveedor.setValor(valor);
/* 448:521 */       valorPago = valorPago.subtract(valor);
/* 449:    */       
/* 450:523 */       liquidacionAnticipoProveedor.getListaDetalleLiquidacionAnticipoProveedor().add(detalleLiquidacionAnticipoProveedor);
/* 451:    */     }
/* 452:525 */     return liquidacionAnticipoProveedor;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public List<LiquidacionAnticipoProveedor> getLiquidacionAnticipoProveedorPorAnticipoProveedor(int idAnticipoProveedor)
/* 456:    */   {
/* 457:536 */     return this.liquidacionAnticipoProveedorDao.getLiquidacionAnticipoProveedorPorAnticipoProveedor(Integer.valueOf(idAnticipoProveedor));
/* 458:    */   }
/* 459:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioLiquidacionAnticipoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */