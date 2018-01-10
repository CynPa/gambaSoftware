/*    1:     */ package com.asinfo.as2.financiero.cobros.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    5:     */ import com.asinfo.as2.dao.AnticipoClienteDao;
/*    6:     */ import com.asinfo.as2.dao.AsientoDao;
/*    7:     */ import com.asinfo.as2.dao.ClienteDao;
/*    8:     */ import com.asinfo.as2.dao.CobroDao;
/*    9:     */ import com.asinfo.as2.dao.CuentaPorCobrarDao;
/*   10:     */ import com.asinfo.as2.dao.DetalleCobroDao;
/*   11:     */ import com.asinfo.as2.dao.DetalleCobroFormaCobroDao;
/*   12:     */ import com.asinfo.as2.dao.DetalleFormaCobroDao;
/*   13:     */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   14:     */ import com.asinfo.as2.dao.GarantiaClienteDao;
/*   15:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   16:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   17:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   19:     */ import com.asinfo.as2.entities.AnticipoCliente;
/*   20:     */ import com.asinfo.as2.entities.Asiento;
/*   21:     */ import com.asinfo.as2.entities.Banco;
/*   22:     */ import com.asinfo.as2.entities.Caja;
/*   23:     */ import com.asinfo.as2.entities.Cliente;
/*   24:     */ import com.asinfo.as2.entities.Cobro;
/*   25:     */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   26:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   27:     */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   28:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   29:     */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*   30:     */ import com.asinfo.as2.entities.DetalleCobro;
/*   31:     */ import com.asinfo.as2.entities.DetalleCobroFormaCobro;
/*   32:     */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   33:     */ import com.asinfo.as2.entities.Documento;
/*   34:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   35:     */ import com.asinfo.as2.entities.Empresa;
/*   36:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   37:     */ import com.asinfo.as2.entities.FormaPago;
/*   38:     */ import com.asinfo.as2.entities.GarantiaCliente;
/*   39:     */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   40:     */ import com.asinfo.as2.entities.Organizacion;
/*   41:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   42:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   43:     */ import com.asinfo.as2.entities.Recaudador;
/*   44:     */ import com.asinfo.as2.entities.Sucursal;
/*   45:     */ import com.asinfo.as2.entities.TipoAsiento;
/*   46:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   47:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   48:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   49:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   50:     */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*   51:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   52:     */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
/*   53:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   54:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   55:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   56:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*   57:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*   58:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*   59:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   60:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*   61:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   62:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   63:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*   64:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   65:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   66:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   67:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   68:     */ import com.asinfo.as2.util.AppUtil;
/*   69:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   70:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   71:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   72:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   73:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*   74:     */ import java.io.IOException;
/*   75:     */ import java.math.BigDecimal;
/*   76:     */ import java.math.RoundingMode;
/*   77:     */ import java.text.SimpleDateFormat;
/*   78:     */ import java.util.ArrayList;
/*   79:     */ import java.util.Collection;
/*   80:     */ import java.util.Date;
/*   81:     */ import java.util.HashMap;
/*   82:     */ import java.util.Iterator;
/*   83:     */ import java.util.List;
/*   84:     */ import java.util.Map;
/*   85:     */ import javax.ejb.EJB;
/*   86:     */ import javax.ejb.SessionContext;
/*   87:     */ import javax.ejb.Stateless;
/*   88:     */ import javax.ejb.TransactionAttribute;
/*   89:     */ import javax.ejb.TransactionAttributeType;
/*   90:     */ import javax.ejb.TransactionManagement;
/*   91:     */ import javax.ejb.TransactionManagementType;
/*   92:     */ import org.apache.log4j.Logger;
/*   93:     */ import org.jdom2.Element;
/*   94:     */ import org.jdom2.JDOMException;
/*   95:     */ 
/*   96:     */ @Stateless
/*   97:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   98:     */ public class ServicioCobroImpl
/*   99:     */   extends AbstractServicioAS2Financiero
/*  100:     */   implements ServicioCobro
/*  101:     */ {
/*  102:     */   private static final long serialVersionUID = 1L;
/*  103:     */   @EJB
/*  104:     */   private transient CobroDao cobroDao;
/*  105:     */   @EJB
/*  106:     */   private transient AsientoDao asientoDao;
/*  107:     */   @EJB
/*  108:     */   private transient DetalleCobroDao detalleCobroDao;
/*  109:     */   @EJB
/*  110:     */   private transient ServicioPeriodo servicioPeriodo;
/*  111:     */   @EJB
/*  112:     */   private transient ServicioSecuencia servicioSecuencia;
/*  113:     */   @EJB
/*  114:     */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  115:     */   @EJB
/*  116:     */   private transient DetalleFormaCobroDao detalleFormaCobroDao;
/*  117:     */   @EJB
/*  118:     */   private transient GarantiaClienteDao garantiaClienteDao;
/*  119:     */   @EJB
/*  120:     */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*  121:     */   @EJB
/*  122:     */   private transient CuentaPorCobrarDao cuentaPorCobrarDao;
/*  123:     */   @EJB
/*  124:     */   private transient FacturaClienteDao facturaClienteDao;
/*  125:     */   @EJB
/*  126:     */   private transient ServicioTipoAsiento servicioTipoAsiento;
/*  127:     */   @EJB
/*  128:     */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  129:     */   @EJB
/*  130:     */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  131:     */   @EJB
/*  132:     */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  133:     */   @EJB
/*  134:     */   private transient DetalleCobroFormaCobroDao detalleCobroFormaCobroDao;
/*  135:     */   @EJB
/*  136:     */   private transient ServicioDocumento servicioDocumento;
/*  137:     */   @EJB
/*  138:     */   private transient ServicioAnticipoCliente servicioAnticipoCliente;
/*  139:     */   @EJB
/*  140:     */   private transient AnticipoClienteDao anticipoClienteDao;
/*  141:     */   @EJB
/*  142:     */   private transient ServicioGenerico<Banco> servicioBanco;
/*  143:     */   @EJB
/*  144:     */   private transient ServicioEmpresa servicioEmpresa;
/*  145:     */   @EJB
/*  146:     */   private transient ServicioFormaPago servicioFormaPago;
/*  147:     */   @EJB
/*  148:     */   private transient ClienteDao clienteDao;
/*  149:     */   
/*  150:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  151:     */   public void guardar(Cobro cobro)
/*  152:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  153:     */   {
/*  154: 173 */     FacturaCliente facturaClienteEmisionRetencion = null;
/*  155: 174 */     boolean indicadorNuevo = cobro.getId() <= 0;
/*  156:     */     try
/*  157:     */     {
/*  158: 177 */       BigDecimal valorFormaCobro = BigDecimal.ZERO;
/*  159: 178 */       BigDecimal valorFacturas = BigDecimal.ZERO;
/*  160: 180 */       for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro()) {
/*  161: 181 */         if (!detalleFormaCobro.isEliminado()) {
/*  162: 182 */           valorFormaCobro = valorFormaCobro.add(detalleFormaCobro.getValor());
/*  163:     */         }
/*  164:     */       }
/*  165: 185 */       for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro()) {
/*  166: 186 */         if (!detalleCobro.isEliminado()) {
/*  167: 187 */           valorFacturas = valorFacturas.add(detalleCobro.getValor());
/*  168:     */         }
/*  169:     */       }
/*  170: 192 */       actualizarGarantias(cobro);
/*  171: 193 */       actualizarDetalleFormaCobro(cobro);
/*  172:     */       
/*  173: 195 */       BigDecimal diferenciaParaGenerarAnticipo = valorFormaCobro.subtract(valorFacturas);
/*  174: 196 */       AnticipoCliente anticipoCliente = new AnticipoCliente();
/*  175: 197 */       AnticipoCliente anticipoClienteAux = null;
/*  176: 198 */       if (cobro.getId() != 0)
/*  177:     */       {
/*  178: 200 */         anticipoClienteAux = this.servicioAnticipoCliente.buscarPorCobro(cobro);
/*  179: 201 */         cobro.setAnticipoCliente(anticipoClienteAux);
/*  180:     */       }
/*  181: 204 */       if ((diferenciaParaGenerarAnticipo.compareTo(BigDecimal.ZERO) > 0) && (anticipoClienteAux == null))
/*  182:     */       {
/*  183: 206 */         anticipoCliente.setValor(diferenciaParaGenerarAnticipo);
/*  184: 207 */         anticipoCliente.setFecha(cobro.getFecha());
/*  185: 208 */         anticipoCliente.setEstado(Estado.ELABORADO);
/*  186: 209 */         anticipoCliente.setSucursal(cobro.getSucursal());
/*  187: 210 */         anticipoCliente.setIdOrganizacion(cobro.getIdOrganizacion());
/*  188: 211 */         anticipoCliente.setCaja(cobro.getCaja());
/*  189: 212 */         anticipoCliente.setNumero("");
/*  190: 213 */         anticipoCliente.setEmpresa(cobro.getEmpresa());
/*  191: 214 */         anticipoCliente.setIndicadorSaldoInicial(true);
/*  192: 215 */         anticipoCliente.setDocumento(
/*  193: 216 */           (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ANTICIPO_CLIENTE, anticipoCliente.getIdOrganizacion()).get(0));
/*  194:     */         
/*  195: 218 */         anticipoCliente.setCuentaBancariaOrganizacion(((DetalleFormaCobro)cobro.getListaDetalleFormaCobro().get(0)).getCuentaBancariaOrganizacion());
/*  196: 219 */         anticipoCliente.setFormaPago(((DetalleFormaCobro)cobro.getListaDetalleFormaCobro().get(0)).getFormaPago());
/*  197: 220 */         anticipoCliente.setDocumentoReferencia(((DetalleFormaCobro)cobro.getListaDetalleFormaCobro().get(0)).getDocumentoReferencia() + "1");
/*  198:     */         
/*  199: 222 */         this.servicioAnticipoCliente.guardar(anticipoCliente);
/*  200:     */         
/*  201: 224 */         cobro.setAnticipoCliente(anticipoCliente);
/*  202:     */       }
/*  203: 228 */       validar(cobro);
/*  204:     */       
/*  205: 230 */       cargarSecuencia(cobro);
/*  206: 231 */       if (!cobro.isIndicadorTienePosfechados()) {
/*  207: 232 */         cobro.setEstado(Estado.CONTABILIZADO);
/*  208:     */       }
/*  209: 234 */       if (indicadorNuevo) {
/*  210: 235 */         cobro.setFechaRegistro(cobro.getFecha());
/*  211:     */       }
/*  212: 237 */       if (cobro.getEstado().equals(Estado.CONTABILIZADO))
/*  213:     */       {
/*  214: 238 */         if (cobro.getAsiento() != null) {
/*  215: 240 */           this.servicioVerificadorVentas.actualizarCuentaPorCobrar(cobro, true);
/*  216:     */         }
/*  217: 244 */         this.servicioVerificadorVentas.actualizarCuentaPorCobrar(cobro, false);
/*  218:     */       }
/*  219:     */       else
/*  220:     */       {
/*  221: 246 */         this.clienteDao.actualizarNumeroFacturasPendientesSinGarantia(cobro.getEmpresa().getCliente());
/*  222:     */       }
/*  223: 249 */       String listadofacturas = "";
/*  224: 250 */       String factura = "";
/*  225: 253 */       for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro())
/*  226:     */       {
/*  227: 254 */         factura = detalleCobro.getCuentaPorCobrar().getFacturaCliente().getNumero();
/*  228: 256 */         if (detalleCobro.getValor().compareTo(BigDecimal.ZERO) == 0) {
/*  229: 257 */           detalleCobro.setEliminado(true);
/*  230:     */         }
/*  231: 260 */         if (!detalleCobro.isEliminado())
/*  232:     */         {
/*  233: 261 */           if (!listadofacturas.contains(factura)) {
/*  234: 262 */             listadofacturas = listadofacturas + "," + factura;
/*  235:     */           }
/*  236: 267 */           if (cobro.isIndicadorEmisionRetencion())
/*  237:     */           {
/*  238: 268 */             facturaClienteEmisionRetencion = detalleCobro.getCuentaPorCobrar().getFacturaCliente();
/*  239: 269 */             facturaClienteEmisionRetencion.setIndicadorEmisionRetencion(cobro.isIndicadorEmisionRetencion());
/*  240: 270 */             this.facturaClienteDao.guardar(facturaClienteEmisionRetencion);
/*  241:     */           }
/*  242:     */         }
/*  243: 273 */         this.detalleCobroDao.guardar(detalleCobro);
/*  244:     */       }
/*  245: 276 */       listadofacturas = listadofacturas.length() > 201 ? listadofacturas.substring(1, 201) : listadofacturas.substring(1);
/*  246:     */       
/*  247:     */ 
/*  248: 279 */       guardarFormaCobro(cobro, listadofacturas);
/*  249:     */       
/*  250: 281 */       actualizarGarantiasFactura(cobro);
/*  251: 282 */       this.cobroDao.flush();
/*  252: 284 */       if (cobro.getEstado().equals(Estado.CONTABILIZADO))
/*  253:     */       {
/*  254: 286 */         contabilizar(cobro);
/*  255: 288 */         if (cobro.isIndicadorTienePosfechados()) {
/*  256: 290 */           actualizaSaldoBloqueadoCXC(cobro, true);
/*  257:     */         }
/*  258:     */       }
/*  259: 293 */       else if (indicadorNuevo)
/*  260:     */       {
/*  261: 296 */         actualizaSaldoBloqueadoCXC(cobro, false);
/*  262:     */       }
/*  263: 300 */       anticipoCliente.setCobro(cobro);
/*  264:     */     }
/*  265:     */     catch (ExcepcionAS2Financiero e)
/*  266:     */     {
/*  267: 303 */       if (facturaClienteEmisionRetencion != null) {
/*  268: 304 */         facturaClienteEmisionRetencion.setIndicadorEmisionRetencion(false);
/*  269:     */       }
/*  270: 307 */       e.printStackTrace();
/*  271: 308 */       this.context.setRollbackOnly();
/*  272: 309 */       throw e;
/*  273:     */     }
/*  274:     */     catch (ExcepcionAS2 e)
/*  275:     */     {
/*  276: 311 */       if (facturaClienteEmisionRetencion != null) {
/*  277: 312 */         facturaClienteEmisionRetencion.setIndicadorEmisionRetencion(false);
/*  278:     */       }
/*  279: 314 */       e.printStackTrace();
/*  280: 315 */       this.context.setRollbackOnly();
/*  281: 316 */       throw e;
/*  282:     */     }
/*  283:     */     catch (AS2Exception e)
/*  284:     */     {
/*  285: 318 */       if (facturaClienteEmisionRetencion != null) {
/*  286: 319 */         facturaClienteEmisionRetencion.setIndicadorEmisionRetencion(false);
/*  287:     */       }
/*  288: 321 */       e.printStackTrace();
/*  289: 322 */       this.context.setRollbackOnly();
/*  290: 323 */       throw e;
/*  291:     */     }
/*  292:     */     catch (Exception e)
/*  293:     */     {
/*  294: 325 */       if (facturaClienteEmisionRetencion != null) {
/*  295: 326 */         facturaClienteEmisionRetencion.setIndicadorEmisionRetencion(false);
/*  296:     */       }
/*  297: 328 */       e.printStackTrace();
/*  298: 329 */       this.context.setRollbackOnly();
/*  299: 330 */       e.printStackTrace();
/*  300: 331 */       throw new AS2Exception(e.getMessage());
/*  301:     */     }
/*  302:     */   }
/*  303:     */   
/*  304:     */   private void actualizarDetalleFormaCobro(Cobro cobro)
/*  305:     */   {
/*  306: 337 */     for (DetalleFormaCobro dfc : cobro.getListaDetalleFormaCobro()) {
/*  307: 338 */       if (dfc.getFormaPago().isIndicadorTarjetaCredito()) {
/*  308: 339 */         actualizarVoucher(cobro, dfc, null, dfc.getFacturaCliente());
/*  309:     */       }
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void guardarCabecera(Cobro cobro)
/*  314:     */   {
/*  315: 346 */     this.cobroDao.guardar(cobro);
/*  316:     */   }
/*  317:     */   
/*  318:     */   private void guardarFormaCobro(Cobro cobro, String descripcionFormaCobro)
/*  319:     */     throws AS2Exception
/*  320:     */   {
/*  321: 350 */     String banco = "";
/*  322: 351 */     String numeroCheque = "";
/*  323: 352 */     String formaCobro = "";
/*  324: 353 */     String fechaCobro = "";
/*  325: 354 */     String descripcion2 = "";
/*  326: 355 */     for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro())
/*  327:     */     {
/*  328: 357 */       if (detalleFormaCobro.getGarantiaCliente() != null)
/*  329:     */       {
/*  330: 358 */         if (cobro.getEstado().equals(Estado.CONTABILIZADO)) {
/*  331: 359 */           detalleFormaCobro.getGarantiaCliente().setEstadoGarantiaCliente(EstadoGarantiaCliente.CONTABILIZADO);
/*  332:     */         } else {
/*  333: 361 */           detalleFormaCobro.getGarantiaCliente().setEstadoGarantiaCliente(EstadoGarantiaCliente.REGISTRADO);
/*  334:     */         }
/*  335: 363 */         detalleFormaCobro.getGarantiaCliente().setDetalleFormaCobro(detalleFormaCobro);
/*  336: 364 */         detalleFormaCobro.getGarantiaCliente().setFechaIngreso(cobro.getFecha());
/*  337:     */         
/*  338: 366 */         GarantiaCliente garantiaCliente = detalleFormaCobro.getGarantiaCliente();
/*  339: 368 */         if ((garantiaCliente != null) && (garantiaCliente.isEliminado()))
/*  340:     */         {
/*  341: 369 */           garantiaCliente.setDetalleFormaCobro(null);
/*  342: 370 */           detalleFormaCobro.setGarantiaCliente(null);
/*  343:     */         }
/*  344: 373 */         this.garantiaClienteDao.guardar(garantiaCliente);
/*  345:     */       }
/*  346: 375 */       if ((descripcionFormaCobro == null) && (detalleFormaCobro.getDescripcion() != null))
/*  347:     */       {
/*  348: 376 */         descripcionFormaCobro = "";
/*  349: 377 */         detalleFormaCobro.setDescripcion(detalleFormaCobro.getDescripcion());
/*  350:     */       }
/*  351:     */       else
/*  352:     */       {
/*  353: 379 */         detalleFormaCobro.setDescripcion(descripcionFormaCobro == null ? "" : descripcionFormaCobro);
/*  354:     */       }
/*  355: 382 */       if (detalleFormaCobro.getBanco() != null)
/*  356:     */       {
/*  357: 383 */         banco = detalleFormaCobro.getBanco().getNombre();
/*  358: 384 */         numeroCheque = detalleFormaCobro.getDocumentoReferencia();
/*  359: 385 */         formaCobro = detalleFormaCobro.getFormaPago().getCodigo();
/*  360: 386 */         if ((detalleFormaCobro.isIndicadorChequePosfechado()) && (detalleFormaCobro.getGarantiaCliente() != null) && 
/*  361: 387 */           (detalleFormaCobro.getGarantiaCliente().getFechaCobro() != null))
/*  362:     */         {
/*  363: 390 */           SimpleDateFormat formateador = new SimpleDateFormat(ParametrosSistema.getFormatoFecha(detalleFormaCobro.getCobro().getIdOrganizacion()));
/*  364:     */           
/*  365: 392 */           fechaCobro = "-" + formateador.format(detalleFormaCobro.getGarantiaCliente().getFechaCobro());
/*  366:     */         }
/*  367: 395 */         descripcion2 = descripcion2 + formaCobro + ": " + numeroCheque + " " + banco + fechaCobro + "\n";
/*  368:     */       }
/*  369: 397 */       this.detalleFormaCobroDao.guardar(detalleFormaCobro);
/*  370:     */     }
/*  371: 401 */     if ((AppUtil.getOrganizacion() != null) && (AppUtil.getOrganizacion().getOrganizacionConfiguracion() != null) && 
/*  372: 402 */       (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion() != null) && 
/*  373: 403 */       (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)))
/*  374:     */     {
/*  375: 406 */       String descripcionAux = formaCobro + ": " + numeroCheque + " " + banco + fechaCobro;
/*  376:     */       
/*  377:     */ 
/*  378:     */ 
/*  379: 410 */       cobro.setDescripcion(descripcionAux);
/*  380:     */     }
/*  381: 412 */     cobro.setDescripcion2(descripcion2);
/*  382: 413 */     boolean primeraVez = cobro.getId() == 0;
/*  383: 414 */     if (primeraVez) {
/*  384: 415 */       prorratearDetalleCobroFormaCobro(cobro);
/*  385:     */     }
/*  386: 417 */     this.cobroDao.guardar(cobro);
/*  387: 419 */     if ((primeraVez) && (!validarProrrateo(cobro))) {
/*  388: 420 */       throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.MENSAJE_ERROR_PRORRATEAR_COBROS");
/*  389:     */     }
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void guardarFormaCobro(Cobro cobro)
/*  393:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  394:     */   {
/*  395: 427 */     validar(cobro);
/*  396: 428 */     guardarFormaCobro(cobro, null);
/*  397: 429 */     if (cobro.getEstado().equals(Estado.CONTABILIZADO)) {
/*  398: 430 */       contabilizar(cobro);
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void prorratearDetalleCobroFormaCobro(Cobro cobro)
/*  403:     */     throws AS2Exception
/*  404:     */   {
/*  405: 436 */     FuncionesUtiles.ordenaLista(cobro.getListaDetalleFormaCobro(), "valor", false);
/*  406: 437 */     FuncionesUtiles.ordenaLista(cobro.getListaDetalleCobro(), "valor", false);
/*  407: 438 */     List<DetalleCobroFormaCobro> listaDetalleCobroFormaCobro = new ArrayList();
/*  408: 439 */     for (Iterator localIterator = cobro.getListaDetalleCobro().iterator(); localIterator.hasNext();)
/*  409:     */     {
/*  410: 439 */       detalleCobro = (DetalleCobro)localIterator.next();
/*  411: 440 */       detalleCobro.setListaDetalleCobroFormaCobro(new ArrayList());
/*  412:     */     }
/*  413:     */     DetalleCobro detalleCobro;
/*  414:     */     DetalleCobroFormaCobro detalleCobroFormaCobro;
/*  415: 444 */     if (cobro.getListaDetalleFormaCobro().size() == 1)
/*  416:     */     {
/*  417: 445 */       detalleFormaCobro = (DetalleFormaCobro)cobro.getListaDetalleFormaCobro().get(0);
/*  418: 446 */       ((DetalleFormaCobro)detalleFormaCobro).setListaDetalleCobroFormaCobro(new ArrayList());
/*  419: 447 */       for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro()) {
/*  420: 448 */         if (!detalleCobro.isEliminado())
/*  421:     */         {
/*  422: 449 */           detalleCobroFormaCobro = new DetalleCobroFormaCobro();
/*  423: 450 */           detalleCobroFormaCobro.setIdOrganizacion(cobro.getIdOrganizacion());
/*  424: 451 */           detalleCobroFormaCobro.setIdSucursal(cobro.getSucursal().getId());
/*  425: 452 */           detalleCobroFormaCobro.setDetalleCobro(detalleCobro);
/*  426: 453 */           detalleCobroFormaCobro.setDetalleFormaCobro((DetalleFormaCobro)detalleFormaCobro);
/*  427: 454 */           detalleCobroFormaCobro.setValor(detalleCobro.getValor());
/*  428: 455 */           detalleCobro.setValorProrrateado(detalleCobro.getValor());
/*  429:     */           
/*  430: 457 */           listaDetalleCobroFormaCobro.add(detalleCobroFormaCobro);
/*  431: 458 */           detalleCobro.getListaDetalleCobroFormaCobro().add(detalleCobroFormaCobro);
/*  432: 459 */           ((DetalleFormaCobro)detalleFormaCobro).getListaDetalleCobroFormaCobro().add(detalleCobroFormaCobro);
/*  433:     */         }
/*  434:     */       }
/*  435:     */     }
/*  436:     */     else
/*  437:     */     {
/*  438: 464 */       for (detalleFormaCobro = cobro.getListaDetalleFormaCobro().iterator(); ((Iterator)detalleFormaCobro).hasNext();)
/*  439:     */       {
/*  440: 464 */         detalleFormaCobro = (DetalleFormaCobro)((Iterator)detalleFormaCobro).next();
/*  441: 465 */         if (!detalleFormaCobro.isEliminado())
/*  442:     */         {
/*  443: 466 */           detalleFormaCobro.setListaDetalleCobroFormaCobro(new ArrayList());
/*  444: 467 */           valorProrratear = detalleFormaCobro.getValor();
/*  445: 468 */           for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro())
/*  446:     */           {
/*  447: 469 */             BigDecimal valorPorprorratear = detalleCobro.getValor().subtract(detalleCobro.getValorProrrateado());
/*  448: 470 */             if ((valorPorprorratear.compareTo(BigDecimal.ZERO) != 0) && (valorProrratear.compareTo(BigDecimal.ZERO) != 0))
/*  449:     */             {
/*  450: 471 */               DetalleCobroFormaCobro detalleCobroFormaCobro = new DetalleCobroFormaCobro();
/*  451: 472 */               detalleCobroFormaCobro.setIdOrganizacion(cobro.getIdOrganizacion());
/*  452: 473 */               detalleCobroFormaCobro.setIdSucursal(cobro.getSucursal().getId());
/*  453: 474 */               detalleCobroFormaCobro.setDetalleCobro(detalleCobro);
/*  454: 475 */               detalleCobroFormaCobro.setDetalleFormaCobro(detalleFormaCobro);
/*  455: 479 */               if (valorProrratear.abs().compareTo(valorPorprorratear.abs()) >= 0)
/*  456:     */               {
/*  457: 480 */                 detalleCobroFormaCobro.setValor(valorPorprorratear);
/*  458: 481 */                 detalleCobro.setValorProrrateado(detalleCobro.getValorProrrateado().add(valorPorprorratear));
/*  459: 482 */                 valorProrratear = valorProrratear.subtract(valorPorprorratear);
/*  460:     */               }
/*  461:     */               else
/*  462:     */               {
/*  463: 487 */                 detalleCobroFormaCobro.setValor(valorProrratear);
/*  464: 488 */                 detalleCobro.setValorProrrateado(detalleCobro.getValorProrrateado().add(valorProrratear));
/*  465: 489 */                 valorProrratear = BigDecimal.ZERO;
/*  466:     */               }
/*  467: 491 */               listaDetalleCobroFormaCobro.add(detalleCobroFormaCobro);
/*  468: 492 */               detalleCobro.getListaDetalleCobroFormaCobro().add(detalleCobroFormaCobro);
/*  469: 493 */               detalleFormaCobro.getListaDetalleCobroFormaCobro().add(detalleCobroFormaCobro);
/*  470:     */             }
/*  471: 495 */             if (valorProrratear.compareTo(BigDecimal.ZERO) == 0) {
/*  472:     */               break;
/*  473:     */             }
/*  474:     */           }
/*  475:     */         }
/*  476:     */       }
/*  477:     */     }
/*  478:     */     DetalleFormaCobro detalleFormaCobro;
/*  479:     */     BigDecimal valorProrratear;
/*  480: 503 */     cobro.setIndicadorProrrateadoDetalles(Boolean.valueOf(true));
/*  481: 505 */     if ((cobro.getId() > 0) && 
/*  482: 506 */       (!validarProrrateo(cobro))) {
/*  483: 507 */       throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.MENSAJE_ERROR_PRORRATEAR_COBROS", new String[] { cobro.getNumero() });
/*  484:     */     }
/*  485: 511 */     for (Object detalleFormaCobro = listaDetalleCobroFormaCobro.iterator(); ((Iterator)detalleFormaCobro).hasNext();)
/*  486:     */     {
/*  487: 511 */       DetalleCobroFormaCobro detalleCobroFormaCobro = (DetalleCobroFormaCobro)((Iterator)detalleFormaCobro).next();
/*  488: 512 */       this.detalleCobroFormaCobroDao.guardar(detalleCobroFormaCobro);
/*  489:     */     }
/*  490:     */   }
/*  491:     */   
/*  492:     */   private boolean validarProrrateo(Cobro cobro)
/*  493:     */   {
/*  494: 518 */     boolean valido = true;
/*  495:     */     
/*  496: 520 */     boolean generarAnticipo = ParametrosSistema.getGeneraAnticipoEnCobrosSuperioesAFacturas(cobro.getIdOrganizacion()).booleanValue();
/*  497: 525 */     for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro())
/*  498:     */     {
/*  499: 526 */       BigDecimal valorProrrateado = BigDecimal.ZERO;
/*  500:     */       
/*  501: 528 */       List<DetalleCobroFormaCobro> lista = detalleFormaCobro.getListaDetalleCobroFormaCobro();
/*  502: 529 */       for (DetalleCobroFormaCobro detalleCobroFormaCobro : lista) {
/*  503: 530 */         valorProrrateado = valorProrrateado.add(detalleCobroFormaCobro.getValor());
/*  504:     */       }
/*  505: 533 */       if ((!generarAnticipo) && (valorProrrateado.compareTo(detalleFormaCobro.getValor()) != 0))
/*  506:     */       {
/*  507: 536 */         valido = false;
/*  508: 537 */         break;
/*  509:     */       }
/*  510:     */     }
/*  511: 542 */     for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro())
/*  512:     */     {
/*  513: 543 */       BigDecimal valorProrrateado = BigDecimal.ZERO;
/*  514:     */       
/*  515: 545 */       List<DetalleCobroFormaCobro> lista = detalleCobro.getListaDetalleCobroFormaCobro();
/*  516: 546 */       for (DetalleCobroFormaCobro detalleCobroFormaCobro : lista) {
/*  517: 547 */         valorProrrateado = valorProrrateado.add(detalleCobroFormaCobro.getValor());
/*  518:     */       }
/*  519: 550 */       if ((!generarAnticipo) && (valorProrrateado.compareTo(detalleCobro.getValor()) != 0))
/*  520:     */       {
/*  521: 553 */         valido = false;
/*  522: 554 */         break;
/*  523:     */       }
/*  524:     */     }
/*  525: 558 */     return valido;
/*  526:     */   }
/*  527:     */   
/*  528:     */   public void eliminar(Cobro cobro)
/*  529:     */   {
/*  530: 568 */     this.cobroDao.eliminar(cobro);
/*  531:     */   }
/*  532:     */   
/*  533:     */   public Cobro buscarPorId(Integer id)
/*  534:     */   {
/*  535: 578 */     return (Cobro)this.cobroDao.buscarPorId(id);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public Cobro cargarDetalle(int idCobro)
/*  539:     */   {
/*  540: 588 */     return this.cobroDao.cargaDetalle(idCobro);
/*  541:     */   }
/*  542:     */   
/*  543:     */   public void validar(Cobro cobro)
/*  544:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  545:     */   {
/*  546: 600 */     this.servicioPeriodo.buscarPorFecha(cobro.getFecha(), cobro.getIdOrganizacion(), cobro.getDocumento().getDocumentoBase());
/*  547:     */     
/*  548: 602 */     boolean generarAnticipo = ParametrosSistema.getGeneraAnticipoEnCobrosSuperioesAFacturas(cobro.getIdOrganizacion()).booleanValue();
/*  549:     */     
/*  550: 604 */     BigDecimal valorCobro = cobro.getValor();
/*  551: 605 */     BigDecimal valorFormaCobro = BigDecimal.ZERO;
/*  552: 606 */     BigDecimal valorFacturas = BigDecimal.ZERO;
/*  553:     */     
/*  554: 608 */     BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/*  555: 609 */     BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*  556: 610 */     BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  557: 611 */     BigDecimal montoIva = BigDecimal.ZERO;
/*  558:     */     
/*  559: 613 */     int numeroChequesPosfechados = 0;
/*  560: 614 */     FacturaCliente facturaCliente = null;
/*  561: 615 */     Map<Integer, Integer> mfacturas = new HashMap();
/*  562: 616 */     for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro())
/*  563:     */     {
/*  564: 617 */       if ((detalleCobro.getValor().compareTo(BigDecimal.ZERO) != 0) && 
/*  565: 618 */         (FuncionesUtiles.compararFechas(cobro.getFecha(), detalleCobro.getCuentaPorCobrar().getFacturaCliente().getFecha()))) {
/*  566: 621 */         throw new ExcepcionAS2Financiero("msg_error_fecha_cobro_factura", detalleCobro.getCuentaPorCobrar().getFacturaCliente().getNumero() + " | " + detalleCobro.getCuentaPorCobrar().getFacturaCliente().getNumero());
/*  567:     */       }
/*  568: 625 */       if (detalleCobro.getValor().compareTo(BigDecimal.ZERO) != 0)
/*  569:     */       {
/*  570: 626 */         facturaCliente = detalleCobro.getCuentaPorCobrar().getFacturaCliente();
/*  571: 627 */         if (facturaCliente.getFacturaClienteSRI() != null)
/*  572:     */         {
/*  573: 628 */           baseImponibleTarifaCero = baseImponibleTarifaCero.add(facturaCliente.getFacturaClienteSRI().getBaseImponibleTarifaCero());
/*  574:     */           
/*  575: 630 */           baseImponibleDiferenteCero = baseImponibleDiferenteCero.add(facturaCliente.getFacturaClienteSRI().getBaseImponibleDiferenteCero());
/*  576: 631 */           baseImponibleNoObjetoIva = baseImponibleNoObjetoIva.add(facturaCliente.getFacturaClienteSRI().getBaseImponibleNoObjetoIva());
/*  577: 632 */           montoIva = montoIva.add(facturaCliente.getFacturaClienteSRI().getMontoIva());
/*  578:     */         }
/*  579: 634 */         mfacturas.put(Integer.valueOf(facturaCliente.getId()), Integer.valueOf(facturaCliente.getId()));
/*  580:     */       }
/*  581:     */     }
/*  582: 638 */     for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro()) {
/*  583: 639 */       if (!detalleFormaCobro.isEliminado())
/*  584:     */       {
/*  585: 640 */         valorFormaCobro = valorFormaCobro.add(detalleFormaCobro.getValor());
/*  586: 642 */         if (detalleFormaCobro.isIndicadorChequePosfechado())
/*  587:     */         {
/*  588: 643 */           numeroChequesPosfechados++;
/*  589: 644 */           if (detalleFormaCobro.getGarantiaCliente().getBanco() == null) {
/*  590: 645 */             throw new ExcepcionAS2Financiero("msg_error_banco_cheque_posfechado");
/*  591:     */           }
/*  592: 647 */           if ((detalleFormaCobro.getGarantiaCliente().getNumeroCuenta() == null) || 
/*  593: 648 */             (detalleFormaCobro.getGarantiaCliente().getNumeroCuenta().trim().length() < 1)) {
/*  594: 649 */             throw new ExcepcionAS2Financiero("msg_error_banco_cheque_posfechado");
/*  595:     */           }
/*  596:     */         }
/*  597: 657 */         FormaPago formaPago = detalleFormaCobro.getFormaPago();
/*  598: 658 */         if (((formaPago.isIndicadorRetencionFuente()) || (formaPago.isIndicadorRetencionIva())) && (cobro.getId() == 0))
/*  599:     */         {
/*  600: 660 */           if (mfacturas.size() > 1)
/*  601:     */           {
/*  602: 661 */             cobro.setIndicadorEmisionRetencion(false);
/*  603: 662 */             throw new ExcepcionAS2("msg_info_cobro_0005");
/*  604:     */           }
/*  605: 663 */           if (mfacturas.size() == 1)
/*  606:     */           {
/*  607: 664 */             if (facturaCliente.isIndicadorEmisionRetencion())
/*  608:     */             {
/*  609: 665 */               cobro.setIndicadorEmisionRetencion(false);
/*  610: 666 */               throw new ExcepcionAS2("msg_info_cobro_0006");
/*  611:     */             }
/*  612: 668 */             cobro.setIndicadorEmisionRetencion(true);
/*  613:     */           }
/*  614: 672 */           if (formaPago.isIndicadorRetencionFuente())
/*  615:     */           {
/*  616: 675 */             if (baseImponibleDiferenteCero.add(baseImponibleTarifaCero).add(baseImponibleNoObjetoIva).multiply(formaPago.getPorcentajeRetencion()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).compareTo(detalleFormaCobro.getValor()) < 0) {
/*  617: 677 */               throw new ExcepcionAS2Financiero("msg_error_retencion_fuente_porcentaje", " " + formaPago.getPorcentajeRetencion());
/*  618:     */             }
/*  619:     */           }
/*  620: 681 */           else if (montoIva.multiply(formaPago.getPorcentajeRetencion()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).compareTo(detalleFormaCobro.getValor()) < 0) {
/*  621: 683 */             throw new ExcepcionAS2Financiero("msg_error_retencion_iva_porcentaje", " " + formaPago.getPorcentajeRetencion());
/*  622:     */           }
/*  623:     */         }
/*  624: 690 */         if (formaPago.isIndicadorTarjetaCredito())
/*  625:     */         {
/*  626: 691 */           String error = "";
/*  627: 692 */           if (detalleFormaCobro.getTarjetaCredito() == null) {
/*  628: 693 */             error = error + "Tarjeta de Crédito,";
/*  629:     */           }
/*  630: 695 */           if (detalleFormaCobro.getPlanTarjetaCredito() == null) {
/*  631: 696 */             error = error + " Plan Tarjeta de Crédito,";
/*  632:     */           }
/*  633: 698 */           if ((detalleFormaCobro.getNumeroTarjeta() == null) || (detalleFormaCobro.getNumeroTarjeta().isEmpty())) {
/*  634: 699 */             error = error + " Número Tarjeta,";
/*  635:     */           }
/*  636: 701 */           if ((detalleFormaCobro.getLote() == null) || (detalleFormaCobro.getLote().isEmpty())) {
/*  637: 702 */             error = error + " Lote";
/*  638:     */           }
/*  639: 704 */           if (error.length() > 0)
/*  640:     */           {
/*  641: 705 */             if (error.charAt(error.length() - 1) == ',') {
/*  642: 706 */               error = error.substring(0, error.length() - 1);
/*  643:     */             }
/*  644: 709 */             throw new ExcepcionAS2Financiero("msg_error_campo_requerido_forma_pago", " " + formaPago.getNombre() + ": " + error);
/*  645:     */           }
/*  646:     */         }
/*  647:     */       }
/*  648:     */     }
/*  649: 716 */     if (numeroChequesPosfechados > 1) {
/*  650: 717 */       throw new ExcepcionAS2Financiero("msg_error_numero_maximo_cheques_posfechados");
/*  651:     */     }
/*  652: 720 */     if ((!cobro.getIndicadorCobroLiquidacion()) && 
/*  653: 721 */       (valorFormaCobro.compareTo(valorCobro) != 0)) {
/*  654: 722 */       throw new ExcepcionAS2Financiero("msg_info_cobro_0004");
/*  655:     */     }
/*  656: 727 */     for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro()) {
/*  657: 729 */       if (!detalleCobro.isEliminado()) {
/*  658: 730 */         valorFacturas = valorFacturas.add(detalleCobro.getValor());
/*  659:     */       }
/*  660:     */     }
/*  661: 733 */     if (!cobro.getIndicadorCobroLiquidacion())
/*  662:     */     {
/*  663: 734 */       if (numeroChequesPosfechados > 0) {
/*  664: 735 */         generarAnticipo = false;
/*  665:     */       }
/*  666: 737 */       if (((!generarAnticipo) && (valorFacturas.compareTo(valorFormaCobro) != 0)) || ((generarAnticipo) && 
/*  667: 738 */         (valorFormaCobro.compareTo(valorFacturas) < 0))) {
/*  668: 739 */         throw new ExcepcionAS2Financiero("msg_info_cobro_0001");
/*  669:     */       }
/*  670:     */     }
/*  671: 743 */     isVerificaCobro(cobro);
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void cargarSecuencia(Cobro cobro)
/*  675:     */     throws ExcepcionAS2
/*  676:     */   {
/*  677: 755 */     if (cobro.getNumero().equals(""))
/*  678:     */     {
/*  679: 756 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(cobro.getDocumento().getIdDocumento(), cobro.getFecha());
/*  680: 757 */       cobro.setNumero(numero);
/*  681:     */     }
/*  682:     */     else
/*  683:     */     {
/*  684: 759 */       this.servicioSecuencia.actualizarSecuencia(cobro.getDocumento().getSecuencia(), cobro.getNumero());
/*  685:     */     }
/*  686:     */   }
/*  687:     */   
/*  688:     */   public boolean validaAsientoCobro(Cobro cobro)
/*  689:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  690:     */   {
/*  691: 770 */     return (cobro.getAsiento().getEstado() != Estado.APROBADO) || (cobro.getAsiento().getEstado() != Estado.ANULADO);
/*  692:     */   }
/*  693:     */   
/*  694:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  695:     */   public void anularCobro(Cobro cobro)
/*  696:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, ExcepcionAS2Ventas
/*  697:     */   {
/*  698:     */     try
/*  699:     */     {
/*  700: 782 */       cobro = cargarDetalle(cobro.getIdCobro());
/*  701:     */       
/*  702: 784 */       esEditable(cobro, true);
/*  703: 786 */       if (!cobro.getEstado().equals(Estado.CONTABILIZADO)) {
/*  704: 787 */         actualizaSaldoBloqueadoCXC(cobro, true);
/*  705:     */       }
/*  706: 790 */       if (cobro.getAsiento() != null)
/*  707:     */       {
/*  708: 791 */         cobro.getAsiento().setIndicadorAutomatico(false);
/*  709: 792 */         this.servicioAsiento.anular(cobro.getAsiento());
/*  710:     */       }
/*  711: 794 */       if (cobro.getEstado() == Estado.CONTABILIZADO) {
/*  712: 795 */         this.servicioVerificadorVentas.actualizarCuentaPorCobrar(cobro, true);
/*  713:     */       }
/*  714: 797 */       actualizarEstado(cobro.getIdCobro(), Estado.ANULADO);
/*  715:     */       
/*  716:     */ 
/*  717:     */ 
/*  718:     */ 
/*  719: 802 */       cobro = cargarDetalle(cobro.getId());
/*  720: 803 */       FacturaCliente facturaCliente = null;
/*  721: 804 */       int numeroFacturas = 0;
/*  722: 805 */       for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro())
/*  723:     */       {
/*  724: 806 */         numeroFacturas++;
/*  725: 807 */         facturaCliente = detalleCobro.getCuentaPorCobrar().getFacturaCliente();
/*  726:     */       }
/*  727: 809 */       for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro())
/*  728:     */       {
/*  729: 810 */         FormaPago formaPago = detalleFormaCobro.getFormaPago();
/*  730: 811 */         if (((formaPago.isIndicadorRetencionFuente()) || (formaPago.isIndicadorRetencionIva())) && 
/*  731: 812 */           (numeroFacturas == 1))
/*  732:     */         {
/*  733: 813 */           facturaCliente.setIndicadorEmisionRetencion(false);
/*  734: 814 */           this.facturaClienteDao.guardar(facturaCliente);
/*  735:     */         }
/*  736: 818 */         if (detalleFormaCobro.getGarantiaCliente() != null)
/*  737:     */         {
/*  738: 819 */           detalleFormaCobro.getGarantiaCliente().setEstadoGarantiaCliente(EstadoGarantiaCliente.ANULADO);
/*  739: 820 */           this.garantiaClienteDao.guardar(detalleFormaCobro.getGarantiaCliente());
/*  740: 822 */           for (DetalleCobroFormaCobro dcfc : detalleFormaCobro.getListaDetalleCobroFormaCobro())
/*  741:     */           {
/*  742: 823 */             FacturaCliente facturaCLiente = dcfc.getDetalleCobro().getCuentaPorCobrar().getFacturaCliente();
/*  743: 824 */             if (facturaCLiente.getCantidadGarantias() > 0) {
/*  744: 825 */               facturaCLiente.setCantidadGarantias(facturaCLiente.getCantidadGarantias() - 1);
/*  745:     */             }
/*  746: 827 */             this.facturaClienteDao.guardar(facturaCLiente);
/*  747:     */           }
/*  748:     */         }
/*  749:     */       }
/*  750: 832 */       AnticipoCliente anticipoCliente = this.servicioAnticipoCliente.buscarPorCobro(cobro);
/*  751: 833 */       if (anticipoCliente != null) {
/*  752: 834 */         this.servicioAnticipoCliente.anularAnticipoCliente(anticipoCliente, false, true);
/*  753:     */       }
/*  754: 837 */       this.clienteDao.actualizarNumeroFacturasPendientesSinGarantia(cobro.getEmpresa().getCliente());
/*  755:     */     }
/*  756:     */     catch (ExcepcionAS2Financiero e)
/*  757:     */     {
/*  758: 840 */       this.context.setRollbackOnly();
/*  759: 841 */       throw e;
/*  760:     */     }
/*  761:     */     catch (Exception e)
/*  762:     */     {
/*  763: 844 */       this.context.setRollbackOnly();
/*  764: 845 */       LOG.error(e);
/*  765: 846 */       throw new ExcepcionAS2(e);
/*  766:     */     }
/*  767:     */   }
/*  768:     */   
/*  769:     */   public void esEditable(Cobro cobro)
/*  770:     */     throws ExcepcionAS2Financiero
/*  771:     */   {
/*  772: 857 */     esEditable(cobro, false);
/*  773:     */   }
/*  774:     */   
/*  775:     */   public void esEditable(Cobro cobro, boolean anular)
/*  776:     */     throws ExcepcionAS2Financiero
/*  777:     */   {
/*  778: 868 */     if ((cobro.getEstado() != Estado.ELABORADO) || (cobro.getAsiento() != null)) {
/*  779: 872 */       this.servicioPeriodo.buscarPorFecha(cobro.getFecha(), cobro.getIdOrganizacion(), cobro.getDocumento().getDocumentoBase());
/*  780:     */     }
/*  781: 875 */     if (cobro.getEstado().equals(Estado.ANULADO)) {
/*  782: 876 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/*  783:     */     }
/*  784: 878 */     if (!anular) {
/*  785: 880 */       if (cobro.getEstado().equals(Estado.CONTABILIZADO)) {
/*  786: 881 */         throw new ExcepcionAS2Financiero("msg_error_editar");
/*  787:     */       }
/*  788:     */     }
/*  789: 886 */     if ((cobro.getEstado().equals(Estado.CONTABILIZADO)) && (this.cobroDao.obtenerCierreCaja(cobro).size() > 0)) {
/*  790: 887 */       throw new ExcepcionAS2Financiero("msg_anular_cobro_cierre_caja");
/*  791:     */     }
/*  792: 889 */     if (cobro.getAsientoProtesto() != null) {
/*  793: 890 */       throw new ExcepcionAS2Financiero("msg_error_cobro_protesto");
/*  794:     */     }
/*  795: 895 */     if ((cobro.getAsiento() != null) && (cobro.getAsiento().getEstado() == Estado.REVISADO)) {
/*  796: 896 */       throw new ExcepcionAS2Financiero("msg_info_anulacion_proceso_estado_asiento");
/*  797:     */     }
/*  798:     */   }
/*  799:     */   
/*  800:     */   public List<Cobro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  801:     */   {
/*  802: 908 */     return this.cobroDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  803:     */   }
/*  804:     */   
/*  805:     */   public List<DetalleFormaCobro> obtenerListaPorPaginaConsulta(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  806:     */   {
/*  807: 919 */     return this.detalleFormaCobroDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  808:     */   }
/*  809:     */   
/*  810:     */   public void contabilizar(Cobro cobro)
/*  811:     */     throws ExcepcionAS2, AS2Exception
/*  812:     */   {
/*  813: 929 */     if (cobro.getDocumento().isIndicadorContabilizar())
/*  814:     */     {
/*  815: 930 */       Date fechaContabilizacion = cobro.getFecha();
/*  816: 931 */       int idCobro = cobro.getIdCobro();
/*  817:     */       Asiento asiento;
/*  818: 934 */       if (cobro.getAsiento() != null)
/*  819:     */       {
/*  820: 935 */         Asiento asiento = this.servicioAsiento.cargarDetalle(cobro.getAsiento().getId());
/*  821: 936 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  822: 937 */           detalleAsiento.setEliminado(true);
/*  823:     */         }
/*  824:     */       }
/*  825:     */       else
/*  826:     */       {
/*  827: 940 */         asiento = new Asiento();
/*  828: 941 */         asiento.setIdOrganizacion(cobro.getIdOrganizacion());
/*  829: 942 */         asiento.setSucursal(cobro.getSucursal());
/*  830: 943 */         TipoAsiento tipoAsiento = cobro.getDocumento().getTipoAsiento();
/*  831: 944 */         asiento.setTipoAsiento(tipoAsiento);
/*  832:     */       }
/*  833: 948 */       String concepto = "";
/*  834: 949 */       concepto = cobro.getDocumento().getNombre().trim() + " #" + cobro.getNumero() + " " + cobro.getDescripcion();
/*  835: 950 */       asiento.setConcepto(concepto);
/*  836: 951 */       asiento.setFecha(fechaContabilizacion);
/*  837:     */       
/*  838:     */ 
/*  839:     */ 
/*  840:     */ 
/*  841: 956 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(cobro.getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/*  842:     */       
/*  843:     */ 
/*  844: 959 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(cobro.getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/*  845:     */       
/*  846: 961 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/*  847: 962 */       listaDA.addAll(this.cobroDao.getDetalleFormaCobroIC(idCobro));
/*  848: 963 */       super.generarAsiento(asiento, listaDA, cobro.getDocumento());
/*  849:     */       
/*  850: 965 */       List<Integer> list = new ArrayList();
/*  851: 966 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/*  852:     */       
/*  853: 968 */       DocumentoContabilizacion documentoContabilizacion = new DocumentoContabilizacion();
/*  854: 969 */       for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/*  855: 970 */         if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.CXC_CLIENTE)) {
/*  856: 971 */           documentoContabilizacion = dc;
/*  857:     */         }
/*  858:     */       }
/*  859: 974 */       BigDecimal valorFactura = BigDecimal.ZERO;
/*  860: 975 */       List<DetalleInterfazContableProceso> listaTmp = new ArrayList();
/*  861: 977 */       for (DetalleCobro dc : cobro.getListaDetalleCobro()) {
/*  862: 978 */         if (dc.getValor().compareTo(BigDecimal.ZERO) != 0)
/*  863:     */         {
/*  864: 980 */           list = new ArrayList();
/*  865: 981 */           list.add(Integer.valueOf(dc.getCuentaPorCobrar().getFacturaCliente().getIdFacturaCliente()));
/*  866:     */           
/*  867: 983 */           listaTmp = this.facturaClienteDao.getInterfazVentasDimensiones(list, documentoContabilizacion.getProcesoContabilizacion());
/*  868: 984 */           valorFactura = BigDecimal.ZERO;
/*  869: 985 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : listaTmp) {
/*  870: 986 */             valorFactura = valorFactura.add(detalleInterfazContableProceso.getValor());
/*  871:     */           }
/*  872: 988 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : listaTmp) {
/*  873: 989 */             detalleInterfazContableProceso.setValor(dc
/*  874: 990 */               .getValor().multiply(detalleInterfazContableProceso.getValor()).divide(valorFactura, 24, RoundingMode.HALF_UP));
/*  875:     */           }
/*  876: 992 */           lista.addAll(listaTmp);
/*  877:     */         }
/*  878:     */       }
/*  879: 997 */       Object listaDetalleAsiento = this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true);
/*  880:     */       
/*  881:     */ 
/*  882:1000 */       asiento.getListaDetalleAsiento().addAll((Collection)listaDetalleAsiento);
/*  883:1004 */       if (cobro.getAnticipoCliente() != null)
/*  884:     */       {
/*  885:1007 */         List<DocumentoContabilizacion> listaDocumentoContabilizacionAnticipo = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(cobro.getAnticipoCliente().getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/*  886:     */         
/*  887:     */ 
/*  888:1010 */         Object listaCriterioDistribucionAnticipo = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(cobro.getAnticipoCliente().getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/*  889:     */         
/*  890:1012 */         List<Integer> listAnticipo = new ArrayList();
/*  891:1013 */         listAnticipo.add(Integer.valueOf(cobro.getAnticipoCliente().getIdAnticipoCliente()));
/*  892:1014 */         List<DetalleInterfazContableProceso> listaAnticipo = new ArrayList();
/*  893:1015 */         List<DetalleAsiento> listaDetalleAsientoAnticipo = new ArrayList();
/*  894:1016 */         for (DocumentoContabilizacion documentoContabilizacionAnticipo : listaDocumentoContabilizacionAnticipo) {
/*  895:1017 */           if (documentoContabilizacionAnticipo.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.ANTICIPO_CLIENTE))
/*  896:     */           {
/*  897:1018 */             listaAnticipo.addAll(this.anticipoClienteDao.getInterfazAnticipoClienteDimensiones(listAnticipo));
/*  898:1019 */             listaDetalleAsientoAnticipo.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, listaAnticipo, documentoContabilizacionAnticipo, (List)listaCriterioDistribucionAnticipo, false));
/*  899:     */           }
/*  900:     */         }
/*  901:1025 */         for (DetalleAsiento da : listaDetalleAsientoAnticipo)
/*  902:     */         {
/*  903:1026 */           da.setDescripcion(cobro.getNumero() + " " + cobro.getEmpresa().getNombreFiscal());
/*  904:1027 */           asiento.getListaDetalleAsiento().add(da);
/*  905:     */         }
/*  906:     */       }
/*  907:1035 */       this.servicioAsiento.guardar(asiento);
/*  908:     */       
/*  909:1037 */       cobro.setEstado(Estado.CONTABILIZADO);
/*  910:     */       
/*  911:1039 */       cobro.setFechaContabilizacion(fechaContabilizacion);
/*  912:     */       
/*  913:1041 */       cobro.setAsiento(asiento);
/*  914:1042 */       this.cobroDao.guardar(cobro);
/*  915:     */     }
/*  916:     */   }
/*  917:     */   
/*  918:     */   public void actualizarEstado(int idCobro, Estado estado)
/*  919:     */   {
/*  920:1053 */     this.cobroDao.actualizaEstado(idCobro, estado);
/*  921:     */   }
/*  922:     */   
/*  923:     */   public void cargarFacturasPendientes(Cobro cobro)
/*  924:     */   {
/*  925:1064 */     cargarFacturasPendientes(cobro, 0, null);
/*  926:     */   }
/*  927:     */   
/*  928:     */   public void cargarFacturasPendientes(Cobro cobro, String numeroFactura)
/*  929:     */   {
/*  930:1074 */     cargarFacturasPendientes(cobro, 0, numeroFactura);
/*  931:     */   }
/*  932:     */   
/*  933:     */   public void cargarFacturasPendientes(Cobro cobro, int idFacturaCliente)
/*  934:     */   {
/*  935:1079 */     cargarFacturasPendientes(cobro, idFacturaCliente, "");
/*  936:     */   }
/*  937:     */   
/*  938:     */   public void cargarFacturasPendientes(Cobro cobro, int idFacturaCliente, String numeroFactura)
/*  939:     */   {
/*  940:1090 */     for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro())
/*  941:     */     {
/*  942:1091 */       detalleCobro.setValor(BigDecimal.ZERO);
/*  943:1092 */       detalleCobro.setEliminado(true);
/*  944:     */     }
/*  945:1095 */     Object listaFacturasPendientes = null;
/*  946:1097 */     if (cobro.getIndicadorCobroLiquidacion())
/*  947:     */     {
/*  948:1099 */       BigDecimal tolerancia = ParametrosSistema.getValorToleranciaCompraVenta(AppUtil.getOrganizacion().getIdOrganizacion());
/*  949:1100 */       listaFacturasPendientes = this.servicioFacturaCliente.obtenerFacturasPendientesLiquidacionCuentaPorCobrar(cobro.getEmpresa().getId(), idFacturaCliente, cobro
/*  950:1101 */         .getFecha(), tolerancia);
/*  951:     */     }
/*  952:     */     else
/*  953:     */     {
/*  954:1103 */       listaFacturasPendientes = this.servicioFacturaCliente.obtenerFacturasPendientes(cobro.getEmpresa().getId(), idFacturaCliente, numeroFactura);
/*  955:     */     }
/*  956:1106 */     BigDecimal valorCobro = cobro.getValor();
/*  957:1107 */     int i = 0;
/*  958:1108 */     for (CuentaPorCobrar cxp : (List)listaFacturasPendientes)
/*  959:     */     {
/*  960:     */       DetalleCobro detalleCobro;
/*  961:     */       DetalleCobro detalleCobro;
/*  962:1110 */       if (i < cobro.getListaDetalleCobro().size())
/*  963:     */       {
/*  964:1111 */         detalleCobro = (DetalleCobro)cobro.getListaDetalleCobro().get(i);
/*  965:     */       }
/*  966:     */       else
/*  967:     */       {
/*  968:1113 */         detalleCobro = new DetalleCobro();
/*  969:1114 */         cobro.getListaDetalleCobro().add(detalleCobro);
/*  970:     */       }
/*  971:1117 */       detalleCobro.setCobro(cobro);
/*  972:1118 */       detalleCobro.setEliminado(false);
/*  973:1119 */       detalleCobro.setCuentaPorCobrar(cxp);
/*  974:1120 */       BigDecimal saldoCuentaPorCobrar = cxp.getSaldo().subtract(cxp.getValorBloqueado());
/*  975:1121 */       BigDecimal valor = BigDecimal.ZERO;
/*  976:1123 */       if (cobro.getIndicadorCobroLiquidacion()) {
/*  977:1124 */         valor = saldoCuentaPorCobrar;
/*  978:1127 */       } else if (valorCobro.compareTo(saldoCuentaPorCobrar) > 0) {
/*  979:1128 */         valor = saldoCuentaPorCobrar;
/*  980:     */       } else {
/*  981:1130 */         valor = valorCobro;
/*  982:     */       }
/*  983:1134 */       detalleCobro.setValor(valor);
/*  984:1135 */       valorCobro = valorCobro.subtract(valor);
/*  985:     */       
/*  986:1137 */       i++;
/*  987:     */     }
/*  988:1140 */     if (cobro.getIndicadorCobroLiquidacion()) {
/*  989:1141 */       cobro.setValor(valorCobro.multiply(new BigDecimal(-1)));
/*  990:     */     }
/*  991:     */   }
/*  992:     */   
/*  993:     */   public CuentaPorCobrar buscarCuentaPorCobrarPorId(int idCuentaPorCobrar)
/*  994:     */   {
/*  995:1148 */     return this.cuentaPorCobrarDao.cargarDetalle(idCuentaPorCobrar);
/*  996:     */   }
/*  997:     */   
/*  998:     */   public List<CuentaPorCobrar> buscarCuentaPorCobrarPorNumeroFacturaCliente(int idOrganizacion, int idEmpresa, String numeroFacturaCliente)
/*  999:     */   {
/* 1000:1153 */     return this.cuentaPorCobrarDao.buscarCuentaPorCobrarPorNumeroFacturaCliente(idOrganizacion, idEmpresa, numeroFacturaCliente);
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public int contarPorCriterio(Map<String, String> filters)
/* 1004:     */   {
/* 1005:1163 */     return this.cobroDao.contarPorCriterio(filters);
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public int contarPorCriterioConsulta(Map<String, String> filters)
/* 1009:     */   {
/* 1010:1168 */     return this.detalleFormaCobroDao.contarPorCriterio(filters);
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void actualizaSaldoBloqueadoCXC(Cobro cobro, boolean reverso)
/* 1014:     */   {
/* 1015:1178 */     for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro()) {
/* 1016:1179 */       if (!detalleCobro.isEliminado()) {
/* 1017:1180 */         this.cobroDao.actualizaSaldoBloqueadoCXC(detalleCobro.getCuentaPorCobrar(), detalleCobro.getValor(), reverso);
/* 1018:     */       }
/* 1019:     */     }
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1023:     */   public void procesarProtesto(Cobro cobro, List<DetalleFormaCobro> listaDetalleFormaCobro, Date fechaProtesto)
/* 1024:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1025:     */   {
/* 1026:     */     try
/* 1027:     */     {
/* 1028:1195 */       Date fechaCobro = FuncionesUtiles.setAtributoFecha(cobro.getFecha());
/* 1029:1196 */       Date fechaProtestoAux = FuncionesUtiles.setAtributoFecha(fechaProtesto);
/* 1030:     */       
/* 1031:     */ 
/* 1032:     */ 
/* 1033:1200 */       Cobro cobroConDetalle = cargarDetalle(cobro.getIdCobro());
/* 1034:1201 */       if (!cobroConDetalle.getEstado().equals(Estado.CONTABILIZADO)) {
/* 1035:1202 */         throw new ExcepcionAS2Financiero("msg_error_protestar_cheque_estado");
/* 1036:     */       }
/* 1037:1205 */       if (!FuncionesUtiles.compararFechaAnteriorOIgual(fechaCobro, fechaProtestoAux)) {
/* 1038:1206 */         throw new ExcepcionAS2Financiero("msg_error_fecha_cobro_protesto");
/* 1039:     */       }
/* 1040:1209 */       int tamanio = 0;
/* 1041:1210 */       for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro()) {
/* 1042:1211 */         if (detalleFormaCobro.isIndicadorChequeProtestado()) {
/* 1043:1212 */           tamanio++;
/* 1044:     */         }
/* 1045:     */       }
/* 1046:1216 */       if (tamanio == 0) {
/* 1047:1217 */         throw new ExcepcionAS2Financiero("msg_info_seleccionar");
/* 1048:     */       }
/* 1049:1220 */       Cobro cobroAux = cargarDetalle(cobro.getIdCobro());
/* 1050:1221 */       if (cobroAux.getAsientoProtesto() != null) {
/* 1051:1222 */         throw new ExcepcionAS2Financiero("msg_error_cobro_protesto");
/* 1052:     */       }
/* 1053:1225 */       String numeroCheque = "";
/* 1054:1226 */       String nombreBanco = "";
/* 1055:1227 */       String descripcionProtesto = "";
/* 1056:     */       
/* 1057:1229 */       BigDecimal valorCliente = BigDecimal.ZERO;
/* 1058:1230 */       for (DetalleFormaCobro detalleFormaCobro : listaDetalleFormaCobro) {
/* 1059:1231 */         if (detalleFormaCobro.isIndicadorChequeProtestado())
/* 1060:     */         {
/* 1061:1232 */           valorCliente = valorCliente.add(detalleFormaCobro.getValor());
/* 1062:1233 */           if (detalleFormaCobro.isIndicadorCargarClienteProtesto()) {
/* 1063:1234 */             valorCliente = valorCliente.add(detalleFormaCobro.getValorProtestado());
/* 1064:     */           }
/* 1065:1237 */           numeroCheque = detalleFormaCobro.getDocumentoReferencia();
/* 1066:1238 */           if (detalleFormaCobro.getBanco() != null) {
/* 1067:1239 */             nombreBanco = detalleFormaCobro.getBanco().getNombre();
/* 1068:     */           }
/* 1069:1241 */           descripcionProtesto = "CHQ: " + numeroCheque + " " + nombreBanco;
/* 1070:     */           
/* 1071:1243 */           detalleFormaCobro.setFechaProtesto(fechaProtesto);
/* 1072:1244 */           this.detalleFormaCobroDao.guardar(detalleFormaCobro);
/* 1073:1248 */           if (detalleFormaCobro.isIndicadorChequePosfechado())
/* 1074:     */           {
/* 1075:1249 */             detalleFormaCobro.getGarantiaCliente().setValorProtestado(detalleFormaCobro.getValorProtestado());
/* 1076:1250 */             detalleFormaCobro.getGarantiaCliente().setEstadoGarantiaCliente(EstadoGarantiaCliente.PROTESTADO);
/* 1077:1251 */             this.garantiaClienteDao.actualizar(detalleFormaCobro.getGarantiaCliente());
/* 1078:     */           }
/* 1079:1255 */           List<DetalleCobroFormaCobro> listaDetalleCobroFormaCobro = this.detalleCobroFormaCobroDao.obtenerLista(detalleFormaCobro, null);
/* 1080:1256 */           for (DetalleCobroFormaCobro detalleCobroFormaCobro : listaDetalleCobroFormaCobro)
/* 1081:     */           {
/* 1082:1257 */             CuentaPorCobrar cuentaPorCobrar = new CuentaPorCobrar();
/* 1083:1258 */             cuentaPorCobrar.setIdOrganizacion(detalleCobroFormaCobro.getDetalleCobro().getCuentaPorCobrar().getIdOrganizacion());
/* 1084:1259 */             cuentaPorCobrar.setIdSucursal(detalleCobroFormaCobro.getDetalleCobro().getCuentaPorCobrar().getIdSucursal());
/* 1085:1260 */             cuentaPorCobrar.setFacturaCliente(detalleCobroFormaCobro.getDetalleCobro().getCuentaPorCobrar().getFacturaCliente());
/* 1086:1261 */             cuentaPorCobrar.setNumeroCuota(detalleCobroFormaCobro.getDetalleCobro().getCuentaPorCobrar().getNumeroCuota());
/* 1087:1262 */             cuentaPorCobrar.setDescripcion(descripcionProtesto);
/* 1088:1263 */             cuentaPorCobrar.setDetalleFormaCobroProtesto(detalleFormaCobro);
/* 1089:1264 */             cuentaPorCobrar.setValor(detalleCobroFormaCobro.getValor());
/* 1090:1265 */             cuentaPorCobrar.setFechaVencimiento(detalleCobroFormaCobro.getDetalleCobro().getCuentaPorCobrar().getFechaVencimiento());
/* 1091:1266 */             cuentaPorCobrar.setFechaProtesto(fechaProtesto);
/* 1092:1267 */             cuentaPorCobrar.setSaldo(detalleCobroFormaCobro.getValor());
/* 1093:1268 */             cuentaPorCobrar.setIndicadorGeneradaProtesto(true);
/* 1094:1269 */             this.cuentaPorCobrarDao.guardar(cuentaPorCobrar);
/* 1095:     */           }
/* 1096:1273 */           if ((detalleFormaCobro.isIndicadorCargarClienteProtesto()) && (detalleFormaCobro.getValorProtestado().compareTo(BigDecimal.ZERO) == 1) && 
/* 1097:1274 */             (listaDetalleCobroFormaCobro.size() > 0))
/* 1098:     */           {
/* 1099:1275 */             CuentaPorCobrar cuentaPorCobrar = new CuentaPorCobrar();
/* 1100:1276 */             cuentaPorCobrar
/* 1101:1277 */               .setIdOrganizacion(((DetalleCobroFormaCobro)listaDetalleCobroFormaCobro.get(0)).getDetalleCobro().getCuentaPorCobrar().getIdOrganizacion());
/* 1102:1278 */             cuentaPorCobrar.setIdSucursal(((DetalleCobroFormaCobro)listaDetalleCobroFormaCobro.get(0)).getDetalleCobro().getCuentaPorCobrar().getIdSucursal());
/* 1103:1279 */             cuentaPorCobrar
/* 1104:1280 */               .setFacturaCliente(((DetalleCobroFormaCobro)listaDetalleCobroFormaCobro.get(0)).getDetalleCobro().getCuentaPorCobrar().getFacturaCliente());
/* 1105:1281 */             cuentaPorCobrar.setNumeroCuota(((DetalleCobroFormaCobro)listaDetalleCobroFormaCobro.get(0)).getDetalleCobro().getCuentaPorCobrar().getNumeroCuota());
/* 1106:1282 */             cuentaPorCobrar.setDescripcion(descripcionProtesto);
/* 1107:1283 */             BigDecimal valorGastoProtesto = detalleFormaCobro.getValorProtestado();
/* 1108:1284 */             cuentaPorCobrar.setValor(valorGastoProtesto);
/* 1109:1285 */             cuentaPorCobrar.setDetalleFormaCobroProtesto(detalleFormaCobro);
/* 1110:1286 */             cuentaPorCobrar
/* 1111:1287 */               .setFechaVencimiento(((DetalleCobroFormaCobro)listaDetalleCobroFormaCobro.get(0)).getDetalleCobro().getCuentaPorCobrar().getFechaVencimiento());
/* 1112:1288 */             cuentaPorCobrar.setFechaProtesto(fechaProtesto);
/* 1113:1289 */             cuentaPorCobrar.setSaldo(valorGastoProtesto);
/* 1114:1290 */             cuentaPorCobrar.setIndicadorGeneradaProtesto(true);
/* 1115:1291 */             this.cuentaPorCobrarDao.guardar(cuentaPorCobrar);
/* 1116:     */           }
/* 1117:1294 */           this.detalleFormaCobroDao.guardar(detalleFormaCobro);
/* 1118:     */         }
/* 1119:     */       }
/* 1120:1297 */       this.servicioVerificadorVentas.actualizarCreditoUtilizado(cobro.getEmpresa().getCliente(), valorCliente, false);
/* 1121:1298 */       this.clienteDao.actualizarNumeroFacturasPendientesSinGarantia(cobro.getEmpresa().getCliente());
/* 1122:1299 */       contabilizarProtesto(cobro, fechaProtesto);
/* 1123:     */     }
/* 1124:     */     catch (ExcepcionAS2Financiero e)
/* 1125:     */     {
/* 1126:1301 */       this.context.setRollbackOnly();
/* 1127:1302 */       throw e;
/* 1128:     */     }
/* 1129:     */     catch (ExcepcionAS2 e)
/* 1130:     */     {
/* 1131:1304 */       this.context.setRollbackOnly();
/* 1132:1305 */       throw e;
/* 1133:     */     }
/* 1134:     */     catch (AS2Exception e)
/* 1135:     */     {
/* 1136:1307 */       this.context.setRollbackOnly();
/* 1137:1308 */       throw e;
/* 1138:     */     }
/* 1139:     */     catch (Exception e)
/* 1140:     */     {
/* 1141:1310 */       this.context.setRollbackOnly();
/* 1142:1311 */       throw new ExcepcionAS2(e);
/* 1143:     */     }
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1147:     */   public void anularProtesto(Cobro cobro)
/* 1148:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 1149:     */   {
/* 1150:     */     try
/* 1151:     */     {
/* 1152:1324 */       BigDecimal valorCliente = BigDecimal.ZERO;
/* 1153:1326 */       if (!cobro.getAsientoProtesto().getEstado().equals(Estado.ELABORADO)) {
/* 1154:1327 */         throw new ExcepcionAS2Financiero("msg_info_anular_estado_asiento");
/* 1155:     */       }
/* 1156:1331 */       DetalleFormaCobro d = null;
/* 1157:1332 */       for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro()) {
/* 1158:1333 */         if (detalleFormaCobro.isIndicadorChequeProtestado())
/* 1159:     */         {
/* 1160:1334 */           valorCliente = valorCliente.add(detalleFormaCobro.getValor());
/* 1161:1335 */           if (detalleFormaCobro.isIndicadorCargarClienteProtesto()) {
/* 1162:1336 */             valorCliente = valorCliente.add(detalleFormaCobro.getValorProtestado());
/* 1163:     */           }
/* 1164:1338 */           d = detalleFormaCobro;
/* 1165:1339 */           if (d.isIndicadorChequePosfechado())
/* 1166:     */           {
/* 1167:1340 */             d.getGarantiaCliente().setValorProtestado(BigDecimal.ZERO);
/* 1168:1341 */             d.getGarantiaCliente().setEstadoGarantiaCliente(EstadoGarantiaCliente.CONTABILIZADO);
/* 1169:1342 */             this.garantiaClienteDao.actualizar(detalleFormaCobro.getGarantiaCliente());
/* 1170:     */           }
/* 1171:1344 */           detalleFormaCobro.setIndicadorChequeProtestado(false);
/* 1172:1345 */           detalleFormaCobro.setCuentaBancariaOrganizacionProtesto(null);
/* 1173:1346 */           detalleFormaCobro.setFormaPagoProtesto(null);
/* 1174:1347 */           this.detalleFormaCobroDao.guardar(detalleFormaCobro);
/* 1175:1348 */           if (d.getListaCuentaPorCobrar() != null)
/* 1176:     */           {
/* 1177:1349 */             for (CuentaPorCobrar cxc : d.getListaCuentaPorCobrar())
/* 1178:     */             {
/* 1179:1350 */               if (cxc.getSaldo().compareTo(cxc.getValor()) != 0) {
/* 1180:1351 */                 throw new ExcepcionAS2Financiero("msg_error_existen_cobros_protesto");
/* 1181:     */               }
/* 1182:1353 */               cxc.setIndicadorAnulada(true);
/* 1183:     */             }
/* 1184:1356 */             for (CuentaPorCobrar cxc : d.getListaCuentaPorCobrar()) {
/* 1185:1357 */               this.cuentaPorCobrarDao.guardar(cxc);
/* 1186:     */             }
/* 1187:     */           }
/* 1188:     */         }
/* 1189:     */       }
/* 1190:1362 */       this.servicioVerificadorVentas.actualizarCreditoUtilizado(cobro.getEmpresa().getCliente(), valorCliente, true);
/* 1191:     */       
/* 1192:1364 */       Asiento asiento = cobro.getAsientoProtesto();
/* 1193:1365 */       this.asientoDao.detach(asiento);
/* 1194:1366 */       asiento.setIndicadorAutomatico(false);
/* 1195:1367 */       this.servicioAsiento.anular(asiento);
/* 1196:1368 */       cobro.setIndicadorTieneCheques(true);
/* 1197:1369 */       cobro.setAsientoProtesto(null);
/* 1198:1370 */       this.cobroDao.guardar(cobro);
/* 1199:1371 */       this.clienteDao.actualizarNumeroFacturasPendientesSinGarantia(cobro.getEmpresa().getCliente());
/* 1200:     */     }
/* 1201:     */     catch (ExcepcionAS2Financiero e)
/* 1202:     */     {
/* 1203:1373 */       this.context.setRollbackOnly();
/* 1204:1374 */       throw e;
/* 1205:     */     }
/* 1206:     */     catch (Exception e)
/* 1207:     */     {
/* 1208:1376 */       this.context.setRollbackOnly();
/* 1209:1377 */       throw new ExcepcionAS2(e);
/* 1210:     */     }
/* 1211:     */   }
/* 1212:     */   
/* 1213:     */   public void contabilizarProtesto(Cobro cobro, Date fecha)
/* 1214:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1215:     */   {
/* 1216:1389 */     if (cobro.getDocumento().isIndicadorContabilizar())
/* 1217:     */     {
/* 1218:1390 */       int idCobro = cobro.getIdCobro();
/* 1219:     */       Asiento asiento;
/* 1220:     */       Asiento asiento;
/* 1221:1392 */       if (cobro.getAsientoProtesto() != null)
/* 1222:     */       {
/* 1223:1393 */         asiento = this.servicioAsiento.cargarDetalle(cobro.getAsientoProtesto().getId());
/* 1224:     */       }
/* 1225:     */       else
/* 1226:     */       {
/* 1227:1396 */         asiento = new Asiento();
/* 1228:1397 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1229:1398 */         asiento.setSucursal(AppUtil.getSucursal());
/* 1230:1399 */         Integer idTipoAsientoProtesto = ParametrosSistema.getTipoAsientoProtesto(cobro.getIdOrganizacion());
/* 1231:1400 */         if (idTipoAsientoProtesto.intValue() == 0) {
/* 1232:1401 */           throw new ExcepcionAS2("msg_info_configuracion", "tipoAsientoProtesto");
/* 1233:     */         }
/* 1234:1403 */         TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(idTipoAsientoProtesto);
/* 1235:1404 */         asiento.setTipoAsiento(tipoAsiento);
/* 1236:     */       }
/* 1237:1408 */       String concepto = "";
/* 1238:1409 */       concepto = "Protesto " + cobro.getDocumento().getNombre().trim() + " #" + cobro.getNumero() + " " + cobro.getDescripcion();
/* 1239:1410 */       asiento.setConcepto(concepto);
/* 1240:1411 */       asiento.setFecha(fecha);
/* 1241:     */       
/* 1242:1413 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/* 1243:1414 */       listaDA.addAll(this.cobroDao.getDetalleFormaCobroICProtesto(idCobro));
/* 1244:1415 */       listaDA.addAll(this.cobroDao.getDetalleGastoProtestoBanco(idCobro));
/* 1245:     */       
/* 1246:     */ 
/* 1247:1418 */       super.generarAsiento(asiento, listaDA, cobro.getDocumento());
/* 1248:     */       
/* 1249:     */ 
/* 1250:     */ 
/* 1251:1422 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(cobro.getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/* 1252:     */       
/* 1253:     */ 
/* 1254:1425 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(cobro.getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/* 1255:     */       
/* 1256:1427 */       List<Integer> list = new ArrayList();
/* 1257:1428 */       DocumentoContabilizacion documentoContabilizacionCXC = new DocumentoContabilizacion();
/* 1258:1429 */       for (Iterator localIterator1 = listaDocumentoContabilizacion.iterator(); localIterator1.hasNext();)
/* 1259:     */       {
/* 1260:1429 */         dc = (DocumentoContabilizacion)localIterator1.next();
/* 1261:1430 */         if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.CXC_CLIENTE)) {
/* 1262:1431 */           documentoContabilizacionCXC = dc;
/* 1263:     */         }
/* 1264:     */       }
/* 1265:1435 */       Object listaTmp = new ArrayList();
/* 1266:1438 */       for (DocumentoContabilizacion dc = cobro.getListaDetalleFormaCobro().iterator(); dc.hasNext();)
/* 1267:     */       {
/* 1268:1438 */         dfc = (DetalleFormaCobro)dc.next();
/* 1269:1439 */         if (dfc.isIndicadorChequeProtestado())
/* 1270:     */         {
/* 1271:1440 */           valorProtestoTotal = BigDecimal.ZERO;
/* 1272:     */           
/* 1273:1442 */           dfc.setListaDetalleCobroFormaCobro(this.cobroDao.getDetalleCobroFormaCobroProtesto(dfc));
/* 1274:1443 */           contador = 0;
/* 1275:1444 */           for (DetalleCobroFormaCobro dcfc : dfc.getListaDetalleCobroFormaCobro())
/* 1276:     */           {
/* 1277:1445 */             contador++;
/* 1278:1446 */             list = new ArrayList();
/* 1279:1447 */             list.add(Integer.valueOf(dcfc.getDetalleCobro().getCuentaPorCobrar().getFacturaCliente().getIdFacturaCliente()));
/* 1280:     */             
/* 1281:1449 */             listaTmp = this.facturaClienteDao.getInterfazVentasDimensiones(list, ProcesoContabilizacionEnum.CXC_CLIENTE);
/* 1282:1450 */             BigDecimal valorFactura = BigDecimal.ZERO;
/* 1283:1451 */             for (Iterator localIterator3 = ((List)listaTmp).iterator(); localIterator3.hasNext();)
/* 1284:     */             {
/* 1285:1451 */               detalleInterfazContableProceso = (DetalleInterfazContableProceso)localIterator3.next();
/* 1286:1452 */               valorFactura = valorFactura.add(detalleInterfazContableProceso.getValor());
/* 1287:     */             }
/* 1288:1454 */             BigDecimal valorTotal = BigDecimal.ZERO;
/* 1289:1456 */             for (DetalleInterfazContableProceso detalleInterfazContableProceso = ((List)listaTmp).iterator(); detalleInterfazContableProceso.hasNext();)
/* 1290:     */             {
/* 1291:1456 */               detalleInterfazContableProceso = (DetalleInterfazContableProceso)detalleInterfazContableProceso.next();
/* 1292:1457 */               BigDecimal valor = dcfc.getValor().multiply(detalleInterfazContableProceso.getValor()).divide(valorFactura, 24, RoundingMode.HALF_UP);
/* 1293:     */               
/* 1294:1459 */               detalleInterfazContableProceso.setValor(valor);
/* 1295:1460 */               valorTotal = valorTotal.add(valor);
/* 1296:1461 */               BigDecimal diferencia = valorTotal.subtract(dcfc.getValor());
/* 1297:1462 */               if (diferencia.compareTo(BigDecimal.ZERO) > 0) {
/* 1298:1463 */                 detalleInterfazContableProceso.setValor(valor.subtract(diferencia));
/* 1299:     */               }
/* 1300:     */             }
/* 1301:1466 */             asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, (List)listaTmp, documentoContabilizacionCXC, listaCriterioDistribucion, false));
/* 1302:     */             
/* 1303:     */ 
/* 1304:1469 */             BigDecimal valorProtesto = dfc.getValorProtestado().multiply(dcfc.getValor()).divide(dfc.getValor(), 2, RoundingMode.HALF_UP);
/* 1305:1470 */             valorProtestoTotal = valorProtestoTotal.add(valorProtesto);
/* 1306:1471 */             if (dfc.getValorProtestado().subtract(valorProtestoTotal).compareTo(BigDecimal.ZERO) < 0) {
/* 1307:1472 */               valorProtesto = valorProtesto.add(dfc.getValorProtestado().subtract(valorProtestoTotal));
/* 1308:     */             }
/* 1309:1474 */             if ((contador == dfc.getListaDetalleCobroFormaCobro().size()) && 
/* 1310:1475 */               (dfc.getValorProtestado().subtract(valorProtestoTotal).compareTo(BigDecimal.ZERO) > 0)) {
/* 1311:1476 */               valorProtesto = valorProtesto.add(dfc.getValorProtestado().subtract(valorProtestoTotal));
/* 1312:     */             }
/* 1313:1478 */             listaTmp = this.facturaClienteDao.getInterfazVentasDimensiones(list, ProcesoContabilizacionEnum.CXC_CLIENTE);
/* 1314:1479 */             valorTotal = BigDecimal.ZERO;
/* 1315:1480 */             for (DetalleInterfazContableProceso detalleInterfazContableProceso = ((List)listaTmp).iterator(); detalleInterfazContableProceso.hasNext();)
/* 1316:     */             {
/* 1317:1480 */               detalleInterfazContableProceso = (DetalleInterfazContableProceso)detalleInterfazContableProceso.next();
/* 1318:1481 */               BigDecimal valor = valorProtesto.multiply(detalleInterfazContableProceso.getValor()).divide(valorFactura, 24, RoundingMode.HALF_UP);
/* 1319:     */               
/* 1320:1483 */               detalleInterfazContableProceso.setValor(valor);
/* 1321:1484 */               valorTotal = valorTotal.add(valor);
/* 1322:1485 */               BigDecimal diferencia = valorTotal.subtract(valorProtesto);
/* 1323:1486 */               if (diferencia.compareTo(BigDecimal.ZERO) > 0) {
/* 1324:1487 */                 detalleInterfazContableProceso.setValor(valor.subtract(diferencia));
/* 1325:     */               }
/* 1326:     */             }
/* 1327:     */             DetalleInterfazContableProceso detalleInterfazContableProceso;
/* 1328:1491 */             if (dfc.isIndicadorCargarClienteProtesto())
/* 1329:     */             {
/* 1330:1492 */               asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, (List)listaTmp, documentoContabilizacionCXC, listaCriterioDistribucion, false));
/* 1331:     */             }
/* 1332:     */             else
/* 1333:     */             {
/* 1334:1495 */               listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(cobro.getIdOrganizacion(), DocumentoBase.PROTESTO_CHEQUE);
/* 1335:     */               
/* 1336:1497 */               DocumentoContabilizacion documentoContabilizacionProtesto = new DocumentoContabilizacion();
/* 1337:1498 */               for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/* 1338:1499 */                 if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.GASTO_PROTESTO)) {
/* 1339:1500 */                   documentoContabilizacionProtesto = dc;
/* 1340:     */                 }
/* 1341:     */               }
/* 1342:1504 */               List<CriterioDistribucion> listaCriterioDistribucionProtesto = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(cobro.getIdOrganizacion(), DocumentoBase.PROTESTO_CHEQUE);
/* 1343:1505 */               asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, (List)listaTmp, documentoContabilizacionProtesto, listaCriterioDistribucionProtesto, false));
/* 1344:     */             }
/* 1345:     */           }
/* 1346:     */         }
/* 1347:     */       }
/* 1348:     */       DetalleFormaCobro dfc;
/* 1349:     */       BigDecimal valorProtestoTotal;
/* 1350:     */       int contador;
/* 1351:1512 */       this.servicioAsiento.guardar(asiento);
/* 1352:     */       
/* 1353:1514 */       cobro.setEstado(Estado.CONTABILIZADO);
/* 1354:     */       
/* 1355:1516 */       cobro.setFechaContabilizacion(fecha);
/* 1356:     */       
/* 1357:1518 */       cobro.setAsientoProtesto(asiento);
/* 1358:1519 */       cobro.setIndicadorTieneCheques(false);
/* 1359:1520 */       this.cobroDao.guardar(cobro);
/* 1360:     */     }
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   public List<Cobro> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 1364:     */   {
/* 1365:1531 */     return this.cobroDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 1366:     */   }
/* 1367:     */   
/* 1368:     */   private void actualizarGarantias(Cobro cobro)
/* 1369:     */   {
/* 1370:1541 */     for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro()) {
/* 1371:1542 */       if (!detalleFormaCobro.isEliminado())
/* 1372:     */       {
/* 1373:1543 */         actualizarGarantiaCliente(detalleFormaCobro);
/* 1374:1545 */         if (detalleFormaCobro.getFormaPago().isIndicadorCheque()) {
/* 1375:1546 */           cobro.setIndicadorTieneCheques(true);
/* 1376:     */         }
/* 1377:1549 */         if ((detalleFormaCobro.getGarantiaCliente() != null) && (!detalleFormaCobro.getGarantiaCliente().isEliminado())) {
/* 1378:1550 */           cobro.setIndicadorTienePosfechados(true);
/* 1379:     */         }
/* 1380:     */       }
/* 1381:     */     }
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   private void actualizarGarantiasFactura(Cobro cobro)
/* 1385:     */   {
/* 1386:1559 */     for (DetalleFormaCobro detalleFormaCobro : cobro.getListaDetalleFormaCobro()) {
/* 1387:1560 */       if ((!detalleFormaCobro.isEliminado()) && 
/* 1388:1561 */         (detalleFormaCobro.getGarantiaCliente() != null) && (!detalleFormaCobro.getGarantiaCliente().isEliminado())) {
/* 1389:1563 */         for (DetalleCobroFormaCobro dcfc : detalleFormaCobro.getListaDetalleCobroFormaCobro())
/* 1390:     */         {
/* 1391:1564 */           FacturaCliente facturaCLiente = dcfc.getDetalleCobro().getCuentaPorCobrar().getFacturaCliente();
/* 1392:1566 */           if (cobro.getEstado().equals(Estado.CONTABILIZADO))
/* 1393:     */           {
/* 1394:1567 */             if (facturaCLiente.getCantidadGarantias() > 0) {
/* 1395:1568 */               facturaCLiente.setCantidadGarantias(facturaCLiente.getCantidadGarantias() - 1);
/* 1396:     */             }
/* 1397:     */           }
/* 1398:     */           else {
/* 1399:1572 */             facturaCLiente.setCantidadGarantias(facturaCLiente.getCantidadGarantias() + 1);
/* 1400:     */           }
/* 1401:1574 */           this.facturaClienteDao.guardar(facturaCLiente);
/* 1402:     */         }
/* 1403:     */       }
/* 1404:     */     }
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   public void actualizarGarantiaCliente(DetalleFormaCobro detalleFormaCobro)
/* 1408:     */   {
/* 1409:1590 */     GarantiaCliente garantiaCliente = null;
/* 1410:1592 */     if (!detalleFormaCobro.isIndicadorChequePosfechado())
/* 1411:     */     {
/* 1412:1594 */       if (detalleFormaCobro.getGarantiaCliente() != null) {
/* 1413:1595 */         detalleFormaCobro.getGarantiaCliente().setEliminado(true);
/* 1414:     */       }
/* 1415:     */     }
/* 1416:     */     else
/* 1417:     */     {
/* 1418:1600 */       if (detalleFormaCobro.getGarantiaCliente() == null)
/* 1419:     */       {
/* 1420:1601 */         garantiaCliente = new GarantiaCliente();
/* 1421:1602 */         detalleFormaCobro.setGarantiaCliente(garantiaCliente);
/* 1422:     */       }
/* 1423:     */       else
/* 1424:     */       {
/* 1425:1604 */         garantiaCliente = detalleFormaCobro.getGarantiaCliente();
/* 1426:1605 */         garantiaCliente.setEliminado(false);
/* 1427:     */       }
/* 1428:1608 */       garantiaCliente.setTipoGarantiaCliente(TipoGarantiaCliente.CHEQUE_POSFECHADO);
/* 1429:1609 */       garantiaCliente.setEmpresa(detalleFormaCobro.getCobro().getEmpresa());
/* 1430:1610 */       garantiaCliente.setNumero(detalleFormaCobro.getDocumentoReferencia());
/* 1431:1611 */       garantiaCliente.setValor(detalleFormaCobro.getValor());
/* 1432:1612 */       garantiaCliente.setCuentaBancariaOrganizacion(detalleFormaCobro.getCuentaBancariaOrganizacion());
/* 1433:1613 */       garantiaCliente.setBanco(detalleFormaCobro.getBanco());
/* 1434:1614 */       garantiaCliente.setFechaIngreso(detalleFormaCobro.getCobro().getFecha());
/* 1435:1615 */       if (garantiaCliente.getFechaCobro() == null) {
/* 1436:1616 */         garantiaCliente.setFechaCobro(detalleFormaCobro.getCobro().getFecha());
/* 1437:     */       }
/* 1438:1619 */       garantiaCliente.setDiasCreditoOtorgado(FuncionesUtiles.diferenciasDeFechas(garantiaCliente.getFechaIngreso(), garantiaCliente.getFechaCobro()));
/* 1439:1620 */       if (garantiaCliente.getDiasCreditoOtorgado() < 0) {
/* 1440:1621 */         garantiaCliente.setDiasCreditoOtorgado(0);
/* 1441:     */       }
/* 1442:1624 */       garantiaCliente.setIdOrganizacion(detalleFormaCobro.getCobro().getIdOrganizacion());
/* 1443:1625 */       garantiaCliente.setIdSucursal(detalleFormaCobro.getCobro().getSucursal().getId());
/* 1444:     */     }
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public Cobro buscarPorNumero(int idOrganizacion, String numero)
/* 1448:     */   {
/* 1449:1632 */     Cobro cobro = this.cobroDao.buscarPorNumero(idOrganizacion, numero);
/* 1450:1633 */     if (cobro != null) {
/* 1451:1634 */       cobro = cargarDetalle(cobro.getId());
/* 1452:     */     }
/* 1453:1636 */     return cobro;
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public void liberarCobro(DetalleFormaCobro detalleFormaCobro)
/* 1457:     */     throws AS2Exception
/* 1458:     */   {
/* 1459:1642 */     if (detalleFormaCobro.getDetalleCierreCaja().getInterfazContableProceso() != null) {
/* 1460:1645 */       throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.ERROR_COBRO_DEPOSITO_DE_CAJA", new String[] { detalleFormaCobro.getCobro().getNumero(), detalleFormaCobro.getDetalleCierreCaja().getInterfazContableProceso().getNumero(), detalleFormaCobro.getDetalleCierreCaja().getInterfazContableProceso().getNumero() });
/* 1461:     */     }
/* 1462:1647 */     this.cobroDao.liberarCobro(detalleFormaCobro);
/* 1463:     */   }
/* 1464:     */   
/* 1465:     */   public List<Object[]> reporteChequePosfechadoDetallado(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, EntidadUsuario vendedor)
/* 1466:     */   {
/* 1467:1654 */     List<Object[]> listaReporte = new ArrayList();
/* 1468:1655 */     List<Object[]> listaVentas = new ArrayList();
/* 1469:1656 */     Map<Integer, List<Object[]>> hmChequesPosfechados = new HashMap();
/* 1470:     */     
/* 1471:     */ 
/* 1472:     */ 
/* 1473:     */ 
/* 1474:     */ 
/* 1475:     */ 
/* 1476:1663 */     listaVentas = this.cobroDao.reporteSaldosVentas(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, vendedor);
/* 1477:     */     Object[] datos;
/* 1478:1664 */     if (listaVentas.size() > 0)
/* 1479:     */     {
/* 1480:1665 */       List<List<Integer>> listaTotal = new ArrayList();
/* 1481:     */       
/* 1482:1667 */       listaTotal.add(new ArrayList());
/* 1483:1668 */       int contador = 0;
/* 1484:1669 */       int numeroListas = 0;
/* 1485:1670 */       for (Iterator localIterator1 = listaVentas.iterator(); localIterator1.hasNext();)
/* 1486:     */       {
/* 1487:1670 */         datos = (Object[])localIterator1.next();
/* 1488:1671 */         if (contador == 2000)
/* 1489:     */         {
/* 1490:1672 */           listaTotal.add(new ArrayList());
/* 1491:1673 */           numeroListas++;
/* 1492:1674 */           contador = 0;
/* 1493:     */         }
/* 1494:1676 */         ((List)listaTotal.get(numeroListas)).add(new Integer(datos[0].toString()));
/* 1495:1677 */         contador++;
/* 1496:     */       }
/* 1497:1680 */       Object listaChequesPosfechados = new ArrayList();
/* 1498:1681 */       for (List<Integer> listaFactura : listaTotal) {
/* 1499:1682 */         ((List)listaChequesPosfechados).addAll(this.cobroDao.getChequesPosfechadoDetallado(listaFactura));
/* 1500:     */       }
/* 1501:1684 */       for (Object[] datosChaques : (List)listaChequesPosfechados)
/* 1502:     */       {
/* 1503:1685 */         Integer clave = new Integer(datosChaques[0].toString());
/* 1504:1686 */         List<Object[]> listaPosfechadoVenta = (List)hmChequesPosfechados.get(clave);
/* 1505:1687 */         if (listaPosfechadoVenta == null)
/* 1506:     */         {
/* 1507:1688 */           listaPosfechadoVenta = new ArrayList();
/* 1508:1689 */           hmChequesPosfechados.put(clave, listaPosfechadoVenta);
/* 1509:     */         }
/* 1510:1691 */         listaPosfechadoVenta.add(datosChaques);
/* 1511:     */       }
/* 1512:1694 */       for (datos = listaVentas.iterator(); datos.hasNext();)
/* 1513:     */       {
/* 1514:1694 */         datos = (Object[])datos.next();
/* 1515:1695 */         detalleReporte = new Object[19];
/* 1516:1696 */         listaReporte.add(detalleReporte);
/* 1517:1697 */         detalleReporte[0] = datos[0];
/* 1518:1698 */         detalleReporte[1] = datos[1];
/* 1519:1699 */         detalleReporte[2] = datos[2];
/* 1520:1700 */         detalleReporte[3] = datos[3];
/* 1521:1701 */         detalleReporte[4] = datos[4];
/* 1522:1702 */         detalleReporte[5] = datos[5];
/* 1523:1703 */         detalleReporte[6] = datos[6];
/* 1524:1704 */         detalleReporte[7] = datos[7];
/* 1525:1705 */         detalleReporte[8] = datos[8];
/* 1526:1706 */         detalleReporte[9] = datos[9];
/* 1527:1707 */         detalleReporte[10] = datos[10];
/* 1528:1708 */         detalleReporte[11] = datos[11];
/* 1529:1709 */         detalleReporte[12] = datos[12];
/* 1530:1710 */         detalleReporte[13] = datos[13];
/* 1531:1711 */         detalleReporte[18] = Long.valueOf(FuncionesUtiles.DiasEntreFechas((Date)datos[8], new Date()));
/* 1532:1712 */         contadorDetalle = 0;
/* 1533:1713 */         List<Object[]> listaPosfechadoVenta = (List)hmChequesPosfechados.get(datos[0]);
/* 1534:1714 */         if (listaPosfechadoVenta != null) {
/* 1535:1715 */           for (Object[] chequePosfechado : listaPosfechadoVenta)
/* 1536:     */           {
/* 1537:1716 */             contadorDetalle++;
/* 1538:1717 */             if (contadorDetalle > 1)
/* 1539:     */             {
/* 1540:1718 */               detalleReporte = new Object[19];
/* 1541:1719 */               listaReporte.add(detalleReporte);
/* 1542:1720 */               detalleReporte[0] = datos[0];
/* 1543:1721 */               detalleReporte[1] = datos[1];
/* 1544:1722 */               detalleReporte[2] = datos[2];
/* 1545:1723 */               detalleReporte[3] = datos[3];
/* 1546:1724 */               detalleReporte[4] = datos[4];
/* 1547:1725 */               detalleReporte[5] = datos[5];
/* 1548:1726 */               detalleReporte[6] = datos[6];
/* 1549:1727 */               detalleReporte[7] = datos[7];
/* 1550:1728 */               detalleReporte[8] = datos[8];
/* 1551:1729 */               detalleReporte[9] = datos[9];
/* 1552:1730 */               detalleReporte[10] = BigDecimal.ZERO;
/* 1553:1731 */               detalleReporte[11] = BigDecimal.ZERO;
/* 1554:1732 */               detalleReporte[18] = Long.valueOf(FuncionesUtiles.DiasEntreFechas((Date)datos[8], new Date()));
/* 1555:     */             }
/* 1556:1734 */             detalleReporte[14] = chequePosfechado[1];
/* 1557:1735 */             detalleReporte[15] = chequePosfechado[2];
/* 1558:1736 */             detalleReporte[16] = chequePosfechado[3];
/* 1559:1737 */             detalleReporte[17] = chequePosfechado[4];
/* 1560:     */           }
/* 1561:     */         }
/* 1562:     */       }
/* 1563:     */     }
/* 1564:     */     Object[] datos;
/* 1565:     */     Object[] detalleReporte;
/* 1566:     */     int contadorDetalle;
/* 1567:1743 */     return listaReporte;
/* 1568:     */   }
/* 1569:     */   
/* 1570:     */   public List<Cobro> cargarXML(List<Object[]> listaResumen, List<Element> infoImpuestos, Element nodeInfoTributaria, int idOrganizacion, Documento documento, Sucursal sucursal, Caja caja, String nombreArchivo, boolean validarCargaIndividual)
/* 1571:     */     throws IOException, JDOMException, ExcepcionAS2, AS2Exception
/* 1572:     */   {
/* 1573:1751 */     HashMap<String, Cobro> hmCobro = new HashMap();
/* 1574:1752 */     HashMap<String, String> filter = new HashMap();
/* 1575:1753 */     filter.put("predeterminado", "true");
/* 1576:1754 */     filter.put("idOrganizacion", Integer.toString(idOrganizacion));
/* 1577:1755 */     List<Cobro> listaCobros = new ArrayList();
/* 1578:     */     
/* 1579:1757 */     Banco banco = null;
/* 1580:1758 */     Recaudador recaudador = null;
/* 1581:     */     
/* 1582:1760 */     List<Banco> listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, filter);
/* 1583:1762 */     if (listaBanco.size() == 0) {
/* 1584:1763 */       throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.NO_EXISTE_BANCO_PREDETERMINADO", new String[] { "" });
/* 1585:     */     }
/* 1586:1765 */     banco = (Banco)listaBanco.get(0);
/* 1587:     */     
/* 1588:     */ 
/* 1589:1768 */     String secuencial = "";
/* 1590:1769 */     String numeroFactura = "";
/* 1591:1770 */     String numeroAutorizacion = "";
/* 1592:     */     
/* 1593:     */ 
/* 1594:1773 */     secuencial = nodeInfoTributaria.getChildText("estab") + "-" + nodeInfoTributaria.getChildText("ptoEmi") + "-" + nodeInfoTributaria.getChildText("secuencial");
/* 1595:1774 */     numeroAutorizacion = nodeInfoTributaria.getChildText("claveAcceso");
/* 1596:1775 */     String identificacion = nodeInfoTributaria.getChildText("ruc");
/* 1597:1776 */     Empresa empresaPorId = null;
/* 1598:     */     try
/* 1599:     */     {
/* 1600:1778 */       empresaPorId = this.servicioEmpresa.buscarEmpresaPorIdentificacion(identificacion);
/* 1601:     */     }
/* 1602:     */     catch (ExcepcionAS2 e)
/* 1603:     */     {
/* 1604:1780 */       e.printStackTrace();
/* 1605:     */     }
/* 1606:1782 */     Empresa empresaFinal = null;
/* 1607:1783 */     if (empresaPorId != null) {
/* 1608:1784 */       empresaFinal = this.servicioEmpresa.obtenerDatosCliente(empresaPorId.getId());
/* 1609:     */     }
/* 1610:1787 */     if ((empresaFinal == null) || (empresaFinal.getCliente() == null))
/* 1611:     */     {
/* 1612:1788 */       agregarResumen(numeroFactura, secuencial, nombreArchivo, listaResumen, "No existe cliente con identificacion " + identificacion, "", 0);
/* 1613:     */       
/* 1614:1790 */       throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.NO_EXISTE_EL_RECAUDADOR", new String[] { "" });
/* 1615:     */     }
/* 1616:1793 */     if (empresaFinal.getCliente().getRecaudador() == null)
/* 1617:     */     {
/* 1618:1794 */       agregarResumen(numeroFactura, secuencial, nombreArchivo, listaResumen, "No existe recaudador " + identificacion, "", 0);
/* 1619:1795 */       throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.NO_EXISTE_EL_RECAUDADOR", new String[] { empresaFinal.getNombreComercial() });
/* 1620:     */     }
/* 1621:1797 */     recaudador = empresaFinal.getCliente().getRecaudador();
/* 1622:     */     int j;
/* 1623:1800 */     for (int l = 0; l < infoImpuestos.size(); l++)
/* 1624:     */     {
/* 1625:1801 */       Element nodeImpuesto = (Element)infoImpuestos.get(l);
/* 1626:1802 */       List listaImp = nodeImpuesto.getChildren("impuesto");
/* 1627:     */       
/* 1628:1804 */       String mensajeRetenciones = "";
/* 1629:1805 */       for (j = 0; j < listaImp.size(); j++)
/* 1630:     */       {
/* 1631:1807 */         Element nodeInfoImpuesto = (Element)listaImp.get(j);
/* 1632:1808 */         String porcentajeRetener = nodeInfoImpuesto.getChildText("porcentajeRetener").trim();
/* 1633:1809 */         String codigo = nodeInfoImpuesto.getChildText("codigo");
/* 1634:1810 */         String valorRetenido = nodeInfoImpuesto.getChildText("valorRetenido").trim();
/* 1635:     */         
/* 1636:1812 */         String numDocSustento = nodeInfoImpuesto.getChildText("numDocSustento");
/* 1637:1814 */         if ((numDocSustento == null) || (numDocSustento.isEmpty()))
/* 1638:     */         {
/* 1639:1815 */           agregarResumen("", "", nombreArchivo, listaResumen, "Archivo Invalido", "", 0);
/* 1640:     */         }
/* 1641:     */         else
/* 1642:     */         {
/* 1643:1819 */           String sucursalRetencion = numDocSustento.substring(0, 3);
/* 1644:1820 */           String puntoVentaRentencion = numDocSustento.substring(3, 6);
/* 1645:1821 */           String numero = numDocSustento.substring(6, numDocSustento.length());
/* 1646:1822 */           numeroFactura = sucursalRetencion + "-" + puntoVentaRentencion + "-" + numero;
/* 1647:     */           
/* 1648:1824 */           Cobro cobro = (Cobro)hmCobro.get(numDocSustento);
/* 1649:1825 */           if (cobro == null)
/* 1650:     */           {
/* 1651:1826 */             mensajeRetenciones = "";
/* 1652:1827 */             cobro = new Cobro();
/* 1653:1828 */             cobro.setValor(BigDecimal.ZERO);
/* 1654:1829 */             cobro.setEmpresa(new Empresa());
/* 1655:1830 */             cobro.setIdOrganizacion(idOrganizacion);
/* 1656:1831 */             cobro.setSucursal(AppUtil.getSucursal());
/* 1657:1832 */             cobro.setEstado(Estado.ELABORADO);
/* 1658:1833 */             cobro.setRecaudador(recaudador);
/* 1659:1834 */             cobro.setEmpresa(empresaFinal);
/* 1660:1835 */             cobro.setNumeroFactura(numeroFactura);
/* 1661:1836 */             cobro.setDocumento(documento);
/* 1662:1837 */             cobro.setSucursal(sucursal);
/* 1663:1838 */             listaCobros.add(cobro);
/* 1664:1839 */             hmCobro.put(numDocSustento, cobro);
/* 1665:     */           }
/* 1666:1842 */           if ((validarCargaIndividual) && 
/* 1667:1843 */             (hmCobro.values().size() > 1)) {
/* 1668:1844 */             throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.RETENCION_INVALIDA_CARGAR_POR_LA_MASIVA", new String[] { "" });
/* 1669:     */           }
/* 1670:1848 */           boolean indicadorRetencionFuente = codigo.equals("1");
/* 1671:1849 */           boolean indicadorRetencionIVA = codigo.equals("2");
/* 1672:1850 */           BigDecimal bdPorcentajeRetener = new BigDecimal(porcentajeRetener);
/* 1673:1851 */           BigDecimal bdValorRetenido = new BigDecimal(valorRetenido);
/* 1674:1853 */           if (bdValorRetenido.compareTo(BigDecimal.ZERO) > 0)
/* 1675:     */           {
/* 1676:1854 */             FormaPago formaPago = this.servicioFormaPago.formaPagoPorTipoRetencionYPorcentaje(bdPorcentajeRetener, indicadorRetencionFuente, indicadorRetencionIVA, cobro
/* 1677:1855 */               .getIdOrganizacion());
/* 1678:1856 */             String mensaje = indicadorRetencionIVA ? "IVA" : "FUENTE";
/* 1679:1857 */             mensajeRetenciones = mensajeRetenciones + mensaje + "(" + bdValorRetenido + " ,  " + bdPorcentajeRetener.toString() + "% ) -     ";
/* 1680:1860 */             if (formaPago == null)
/* 1681:     */             {
/* 1682:1861 */               agregarResumen(numeroFactura, secuencial, nombreArchivo, listaResumen, "No existe forma de pago " + mensaje + " " + bdPorcentajeRetener
/* 1683:1862 */                 .toString(), mensajeRetenciones, 0);
/* 1684:     */               
/* 1685:1864 */               throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.NO_EXISTE_FORMA_PAGO", new String[] { indicadorRetencionIVA ? "IVA" : "FUENTE", bdPorcentajeRetener.toString() });
/* 1686:     */             }
/* 1687:1869 */             List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cuentaBancariaOrganizacionPorFormaPago(indicadorRetencionFuente, indicadorRetencionIVA, cobro.getIdOrganizacion());
/* 1688:1870 */             CuentaBancariaOrganizacion cuentaBancariaOrganizacion = null;
/* 1689:1871 */             if (listaCuentaBancariaOrganizacion.size() == 0)
/* 1690:     */             {
/* 1691:1872 */               agregarResumen(numeroFactura, secuencial, nombreArchivo, listaResumen, "No existe cuenta pagos tipo IVA o Fuente", mensajeRetenciones, 0);
/* 1692:     */               
/* 1693:1874 */               throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.NO_EXISTE_CUENTA_PAGOS_TIPO_IVA_FUENTE", new String[] { "" });
/* 1694:     */             }
/* 1695:1876 */             cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(((CuentaBancariaOrganizacion)listaCuentaBancariaOrganizacion.get(0)).getId());
/* 1696:     */             
/* 1697:     */ 
/* 1698:1879 */             DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/* 1699:1880 */             detalleFormaCobro.setIdOrganizacion(cobro.getIdOrganizacion());
/* 1700:1881 */             detalleFormaCobro.setIdSucursal(cobro.getSucursal().getIdSucursal());
/* 1701:1882 */             detalleFormaCobro.setDescripcion(secuencial);
/* 1702:1883 */             detalleFormaCobro.setFormaPago(formaPago);
/* 1703:1884 */             detalleFormaCobro.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/* 1704:1885 */             detalleFormaCobro.setValor(bdValorRetenido.setScale(2));
/* 1705:1886 */             detalleFormaCobro.setBanco(banco);
/* 1706:1887 */             detalleFormaCobro.setCobro(cobro);
/* 1707:1888 */             detalleFormaCobro.setCaja(caja);
/* 1708:1889 */             detalleFormaCobro.setCuentaContableFormaCobro(detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/* 1709:1890 */             detalleFormaCobro.setDocumentoReferencia(secuencial);
/* 1710:1891 */             detalleFormaCobro.setAutorizacion(numeroAutorizacion);
/* 1711:1892 */             cobro.setDescripcion(mensajeRetenciones);
/* 1712:1893 */             cobro.setValor(detalleFormaCobro.getValor().add(cobro.getValor()));
/* 1713:1894 */             cobro.getListaDetalleFormaCobro().add(detalleFormaCobro);
/* 1714:     */           }
/* 1715:     */         }
/* 1716:     */       }
/* 1717:1901 */       for (Cobro cobro : listaCobros)
/* 1718:     */       {
/* 1719:1903 */         cargarFacturasPendientes(cobro, cobro.getNumeroFactura());
/* 1720:     */         
/* 1721:1905 */         int idFacturaCliente = 0;
/* 1722:1906 */         if (cobro.getListaDetalleCobro().size() > 0)
/* 1723:     */         {
/* 1724:1907 */           DetalleCobro detalleCobro = (DetalleCobro)cobro.getListaDetalleCobro().get(0);
/* 1725:1908 */           detalleCobro.setValor(cobro.getValor());
/* 1726:1909 */           if (detalleCobro.getCuentaPorCobrar() != null) {
/* 1727:1910 */             idFacturaCliente = detalleCobro.getCuentaPorCobrar().getFacturaCliente().getIdFacturaCliente();
/* 1728:     */           }
/* 1729:     */         }
/* 1730:1914 */         if ((validarCargaIndividual) && (idFacturaCliente == 0)) {
/* 1731:1915 */           throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.FACTURA_LIQUIDAD_O_NO_EXSITE", new String[] { "" });
/* 1732:     */         }
/* 1733:1918 */         agregarResumen(cobro.getNumeroFactura(), secuencial, nombreArchivo, listaResumen, "CORRECTO", cobro.getDescripcion(), idFacturaCliente);
/* 1734:     */       }
/* 1735:     */     }
/* 1736:1924 */     return listaCobros;
/* 1737:     */   }
/* 1738:     */   
/* 1739:     */   public void agregarResumen(String numeroFactura, String secuencial, String nombreArchivo, List<Object[]> listaResumen, String mensaje, String retenciones, int idFacturaCliente)
/* 1740:     */   {
/* 1741:1929 */     Object[] o = new Object[7];
/* 1742:1930 */     o[0] = numeroFactura;
/* 1743:1931 */     o[1] = mensaje;
/* 1744:1932 */     o[2] = secuencial;
/* 1745:1933 */     o[3] = "";
/* 1746:1934 */     o[4] = nombreArchivo;
/* 1747:1935 */     o[5] = retenciones;
/* 1748:1936 */     o[6] = Integer.valueOf(idFacturaCliente);
/* 1749:1937 */     listaResumen.add(o);
/* 1750:     */   }
/* 1751:     */   
/* 1752:     */   public void isVerificaCobro(Cobro cobro)
/* 1753:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 1754:     */   {
/* 1755:1945 */     boolean indicadorReverso = cobro.getAsiento() != null;
/* 1756:1947 */     for (DetalleCobro detalleCobro : cobro.getListaDetalleCobro()) {
/* 1757:1949 */       if (!detalleCobro.isEliminado())
/* 1758:     */       {
/* 1759:1950 */         BigDecimal valor = detalleCobro.getValor();
/* 1760:1951 */         if (indicadorReverso) {
/* 1761:1952 */           valor = detalleCobro.getValor().negate();
/* 1762:     */         }
/* 1763:1954 */         BigDecimal saldo = detalleCobro.getCuentaPorCobrar().getSaldo();
/* 1764:1956 */         if (saldo.subtract(valor).add(cobro.getTolerancia()).compareTo(BigDecimal.ZERO) < 0) {
/* 1765:1958 */           throw new ExcepcionAS2Financiero("msg_info_cobro_0002", " " + detalleCobro.getCuentaPorCobrar().getFacturaCliente().getNumero());
/* 1766:     */         }
/* 1767:     */       }
/* 1768:     */     }
/* 1769:     */   }
/* 1770:     */   
/* 1771:     */   public void actualizarAtributoEntidad(Cobro cobro, HashMap<String, Object> campos)
/* 1772:     */   {
/* 1773:1966 */     this.cobroDao.actualizarAtributoEntidad(cobro, campos);
/* 1774:     */   }
/* 1775:     */   
/* 1776:     */   public void actualizarVoucher(Cobro cobro, DetalleFormaCobro detalleFormaCobroSeleccionado, PuntoDeVenta puntoDeVenta, FacturaCliente facturaCliente)
/* 1777:     */   {
/* 1778:1972 */     BigDecimal baseCero = BigDecimal.ZERO;
/* 1779:1973 */     BigDecimal baseDiferenteCero = BigDecimal.ZERO;
/* 1780:1974 */     BigDecimal iva = BigDecimal.ZERO;
/* 1781:     */     
/* 1782:1976 */     BigDecimal baseCeroFactura = BigDecimal.ZERO;
/* 1783:1977 */     BigDecimal baseDiferenteCeroFactura = BigDecimal.ZERO;
/* 1784:1978 */     BigDecimal ivaFactura = BigDecimal.ZERO;
/* 1785:1979 */     BigDecimal totalFacturas = BigDecimal.ZERO;
/* 1786:     */     
/* 1787:1981 */     BigDecimal porcentajeBaseCero = BigDecimal.ZERO;
/* 1788:1982 */     BigDecimal porcentajeBaseDiferenteCero = BigDecimal.ZERO;
/* 1789:1983 */     BigDecimal porcentajeIva = BigDecimal.ZERO;
/* 1790:1985 */     if (detalleFormaCobroSeleccionado.getPuntoVenta() == null) {
/* 1791:1986 */       detalleFormaCobroSeleccionado.setPuntoVenta(puntoDeVenta);
/* 1792:     */     }
/* 1793:1987 */     if (detalleFormaCobroSeleccionado.getFechaVoucher() == null) {
/* 1794:1988 */       detalleFormaCobroSeleccionado.setFechaVoucher(cobro.getFecha());
/* 1795:     */     }
/* 1796:1989 */     if (facturaCliente == null)
/* 1797:     */     {
/* 1798:1990 */       for (DetalleCobro dc : cobro.getListaDetalleCobro()) {
/* 1799:1991 */         if ((!dc.isEliminado()) && (BigDecimal.ZERO.compareTo(dc.getValor()) != 0))
/* 1800:     */         {
/* 1801:1992 */           FacturaClienteSRI fcSRI = dc.getCuentaPorCobrar().getFacturaCliente().getFacturaClienteSRI();
/* 1802:1993 */           if (fcSRI != null)
/* 1803:     */           {
/* 1804:1994 */             baseCeroFactura = baseCeroFactura.add(fcSRI.getBaseImponibleTarifaCero().add(fcSRI.getBaseImponibleNoObjetoIva()));
/* 1805:1995 */             baseDiferenteCeroFactura = baseDiferenteCeroFactura.add(fcSRI.getBaseImponibleDiferenteCero().add(fcSRI.getMontoIce()));
/* 1806:1996 */             ivaFactura = ivaFactura.add(fcSRI.getMontoIva().add(fcSRI.getMontoIRBPNR()));
/* 1807:     */           }
/* 1808:     */           else
/* 1809:     */           {
/* 1810:1998 */             baseCero = detalleFormaCobroSeleccionado.getValor();
/* 1811:     */           }
/* 1812:     */         }
/* 1813:     */       }
/* 1814:2002 */       totalFacturas = baseCeroFactura.add(baseDiferenteCeroFactura).add(ivaFactura);
/* 1815:2003 */       if (totalFacturas.compareTo(BigDecimal.ZERO) != 0)
/* 1816:     */       {
/* 1817:2004 */         porcentajeBaseCero = baseCeroFactura.divide(totalFacturas, 8, RoundingMode.HALF_UP);
/* 1818:2005 */         porcentajeBaseDiferenteCero = baseDiferenteCeroFactura.divide(totalFacturas, 8, RoundingMode.HALF_UP);
/* 1819:2006 */         porcentajeIva = ivaFactura.divide(totalFacturas, 8, RoundingMode.HALF_UP);
/* 1820:     */       }
/* 1821:2008 */       baseCero = baseCero.add(detalleFormaCobroSeleccionado.getValor().multiply(porcentajeBaseCero));
/* 1822:2009 */       baseDiferenteCero = baseDiferenteCero.add(detalleFormaCobroSeleccionado.getValor().multiply(porcentajeBaseDiferenteCero));
/* 1823:2010 */       iva = iva.add(detalleFormaCobroSeleccionado.getValor().multiply(porcentajeIva));
/* 1824:     */     }
/* 1825:2012 */     else if (facturaCliente.getFacturaClienteSRI() != null)
/* 1826:     */     {
/* 1827:2014 */       baseCeroFactura = facturaCliente.getFacturaClienteSRI().getBaseImponibleTarifaCero().add(facturaCliente.getFacturaClienteSRI().getBaseImponibleNoObjetoIva());
/* 1828:     */       
/* 1829:2016 */       baseDiferenteCeroFactura = facturaCliente.getFacturaClienteSRI().getBaseImponibleDiferenteCero().add(facturaCliente.getFacturaClienteSRI().getMontoIce());
/* 1830:2017 */       ivaFactura = facturaCliente.getFacturaClienteSRI().getMontoIva();
/* 1831:     */       
/* 1832:2019 */       totalFacturas = totalFacturas.add(baseCeroFactura).add(baseDiferenteCeroFactura).add(ivaFactura);
/* 1833:2020 */       if (totalFacturas.compareTo(BigDecimal.ZERO) != 0)
/* 1834:     */       {
/* 1835:2021 */         porcentajeBaseCero = baseCeroFactura.divide(totalFacturas, 8, RoundingMode.HALF_UP);
/* 1836:2022 */         porcentajeBaseDiferenteCero = baseDiferenteCeroFactura.divide(totalFacturas, 8, RoundingMode.HALF_UP);
/* 1837:2023 */         porcentajeIva = ivaFactura.divide(totalFacturas, 8, RoundingMode.HALF_UP);
/* 1838:     */       }
/* 1839:2026 */       baseCero = baseCero.add(detalleFormaCobroSeleccionado.getValor().multiply(porcentajeBaseCero));
/* 1840:2027 */       baseDiferenteCero = baseDiferenteCero.add(detalleFormaCobroSeleccionado.getValor().multiply(porcentajeBaseDiferenteCero));
/* 1841:2028 */       iva = iva.add(detalleFormaCobroSeleccionado.getValor().multiply(porcentajeIva));
/* 1842:     */     }
/* 1843:     */     else
/* 1844:     */     {
/* 1845:2031 */       baseCero = detalleFormaCobroSeleccionado.getValor();
/* 1846:     */     }
/* 1847:2035 */     detalleFormaCobroSeleccionado.setBaseImponibleDiferenteCero(baseDiferenteCero.setScale(2, RoundingMode.HALF_UP));
/* 1848:2036 */     detalleFormaCobroSeleccionado.setBaseImponibleTarifaCero(baseCero.setScale(2, RoundingMode.HALF_UP));
/* 1849:2037 */     detalleFormaCobroSeleccionado.setMontoIva(iva.setScale(2, RoundingMode.HALF_UP));
/* 1850:     */   }
/* 1851:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl
 * JD-Core Version:    0.7.0.1
 */