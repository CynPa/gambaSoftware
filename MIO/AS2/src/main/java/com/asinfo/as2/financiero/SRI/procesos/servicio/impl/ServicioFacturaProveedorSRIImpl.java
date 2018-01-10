/*    1:     */ package com.asinfo.as2.financiero.SRI.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.ReporteComprasVentasRetenciones;
/*    4:     */ import com.asinfo.as2.clases.ReporteRetencionesResumido;
/*    5:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    6:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    7:     */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*    8:     */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaProveedorSRIXML;
/*    9:     */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*   10:     */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   11:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   12:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   13:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   14:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   15:     */ import com.asinfo.as2.dao.CajaChicaDao;
/*   16:     */ import com.asinfo.as2.dao.CategoriaImpuestoDao;
/*   17:     */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*   18:     */ import com.asinfo.as2.dao.PagoDao;
/*   19:     */ import com.asinfo.as2.dao.reportes.financiero.SRI.ReporteRetencionSRIDao;
/*   20:     */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*   21:     */ import com.asinfo.as2.dao.sri.DetalleFacturaProveedorSRIDao;
/*   22:     */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*   23:     */ import com.asinfo.as2.dao.sri.TipoComprobanteSRIDao;
/*   24:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   25:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   26:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   27:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   28:     */ import com.asinfo.as2.entities.CajaChica;
/*   29:     */ import com.asinfo.as2.entities.CategoriaRetencion;
/*   30:     */ import com.asinfo.as2.entities.Ciudad;
/*   31:     */ import com.asinfo.as2.entities.CompraCajaChica;
/*   32:     */ import com.asinfo.as2.entities.CuentaContable;
/*   33:     */ import com.asinfo.as2.entities.DetalleCategoriaRetencion;
/*   34:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   35:     */ import com.asinfo.as2.entities.DetalleFormaPago;
/*   36:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   37:     */ import com.asinfo.as2.entities.Documento;
/*   38:     */ import com.asinfo.as2.entities.Empresa;
/*   39:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   40:     */ import com.asinfo.as2.entities.FormaPago;
/*   41:     */ import com.asinfo.as2.entities.Impuesto;
/*   42:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*   43:     */ import com.asinfo.as2.entities.Organizacion;
/*   44:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   45:     */ import com.asinfo.as2.entities.Pago;
/*   46:     */ import com.asinfo.as2.entities.Proveedor;
/*   47:     */ import com.asinfo.as2.entities.Provincia;
/*   48:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   49:     */ import com.asinfo.as2.entities.Sucursal;
/*   50:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   51:     */ import com.asinfo.as2.entities.Ubicacion;
/*   52:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   53:     */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   54:     */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*   55:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   56:     */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*   57:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   58:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   59:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   60:     */ import com.asinfo.as2.enumeraciones.TipoAnexoSRI;
/*   61:     */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   62:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   63:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   64:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   65:     */ import com.asinfo.as2.finaciero.SRI.reportes.ReporteFacturacionSRIBean;
/*   66:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*   67:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*   68:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*   69:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*   70:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*   71:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   72:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*   73:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCompraCajaChica;
/*   74:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   75:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*   76:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   77:     */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*   78:     */ import com.asinfo.as2.util.AppUtil;
/*   79:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   80:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   81:     */ import com.asinfo.as2.utils.comparator.ConceptoRetencionSRIComparator;
/*   82:     */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   83:     */ import java.io.IOException;
/*   84:     */ import java.io.InputStream;
/*   85:     */ import java.io.PrintStream;
/*   86:     */ import java.math.BigDecimal;
/*   87:     */ import java.util.ArrayList;
/*   88:     */ import java.util.Calendar;
/*   89:     */ import java.util.Collections;
/*   90:     */ import java.util.Date;
/*   91:     */ import java.util.HashMap;
/*   92:     */ import java.util.Iterator;
/*   93:     */ import java.util.List;
/*   94:     */ import java.util.Map;
/*   95:     */ import java.util.Set;
/*   96:     */ import javax.annotation.Resource;
/*   97:     */ import javax.ejb.EJB;
/*   98:     */ import javax.ejb.SessionContext;
/*   99:     */ import javax.ejb.Stateless;
/*  100:     */ import javax.ejb.TransactionAttribute;
/*  101:     */ import javax.ejb.TransactionAttributeType;
/*  102:     */ import javax.ejb.TransactionManagement;
/*  103:     */ import javax.ejb.TransactionManagementType;
/*  104:     */ import net.sf.jasperreports.engine.JRDataSource;
/*  105:     */ import org.apache.log4j.Logger;
/*  106:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  107:     */ 
/*  108:     */ @Stateless
/*  109:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  110:     */ public class ServicioFacturaProveedorSRIImpl
/*  111:     */   extends AbstractServicioAS2
/*  112:     */   implements ServicioFacturaProveedorSRI
/*  113:     */ {
/*  114:     */   private static final long serialVersionUID = 417279376202522495L;
/*  115:     */   @EJB
/*  116:     */   private transient FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  117:     */   @EJB
/*  118:     */   private transient DetalleFacturaProveedorSRIDao detalleFacturaProveedorSRIDao;
/*  119:     */   @EJB
/*  120:     */   private transient ServicioPago servicioPago;
/*  121:     */   @EJB
/*  122:     */   private transient ServicioDocumento servicioDocumento;
/*  123:     */   @EJB
/*  124:     */   private transient ServicioFormaPago servicioFormaPago;
/*  125:     */   @EJB
/*  126:     */   private transient ReporteRetencionSRIDao reporteRetencionSRIDao;
/*  127:     */   @EJB
/*  128:     */   private transient ServicioEmpresa servicioEmpresa;
/*  129:     */   @EJB
/*  130:     */   private DireccionEmpresaDao direccionEmpresaDao;
/*  131:     */   @EJB
/*  132:     */   private transient ServicioSecuencia servicioSecuencia;
/*  133:     */   @EJB
/*  134:     */   private transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  135:     */   @EJB
/*  136:     */   private transient ServicioCreditoTributario servicioCreditoTributario;
/*  137:     */   @EJB
/*  138:     */   private transient ServicioCategoriaRetencion servicioCategoriaRetencion;
/*  139:     */   @EJB
/*  140:     */   private transient ServicioCompraCajaChica servicioCompraCajaChica;
/*  141:     */   @EJB
/*  142:     */   private transient CajaChicaDao cajaChicaDao;
/*  143:     */   @EJB
/*  144:     */   private transient ServicioProducto servicioProducto;
/*  145:     */   @EJB
/*  146:     */   private transient PagoDao pagoDao;
/*  147:     */   @EJB
/*  148:     */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  149:     */   @EJB
/*  150:     */   private transient ServicioCuentaContable servicioCuentaContable;
/*  151:     */   @EJB
/*  152:     */   private ServicioFacturaProveedorSRIXML servicioFacturaProveedorSRIXML;
/*  153:     */   @EJB
/*  154:     */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  155:     */   @EJB
/*  156:     */   private ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  157:     */   @EJB
/*  158:     */   private ServicioSucursal servicioSucursal;
/*  159:     */   @EJB
/*  160:     */   private ServicioImpuesto servicioImpuesto;
/*  161:     */   @EJB
/*  162:     */   private CategoriaImpuestoDao categoriaImpuestoDao;
/*  163:     */   @EJB
/*  164:     */   private ServicioOrganizacion servicioOrganizacion;
/*  165:     */   @EJB
/*  166:     */   private TipoComprobanteSRIDao tipoComprobanteSRIDao;
/*  167:     */   @EJB
/*  168:     */   protected transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  169:     */   @EJB
/*  170:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  171:     */   @EJB
/*  172:     */   private ServicioSRI servicioSRI;
/*  173:     */   @Resource
/*  174:     */   protected SessionContext context;
/*  175:     */   
/*  176:     */   private boolean emiteRetencion(FacturaProveedorSRI facturaProveedorSRI)
/*  177:     */   {
/*  178: 193 */     BigDecimal suma = BigDecimal.ZERO;
/*  179: 194 */     facturaProveedorSRI.setEmitirRetencionIva0(false);
/*  180: 195 */     for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/*  181: 196 */       if (!detalleFacturaProveedorSRI.isEliminado())
/*  182:     */       {
/*  183: 197 */         suma = suma.add(detalleFacturaProveedorSRI.getValorRetencion());
/*  184: 198 */         if (detalleFacturaProveedorSRI.getConceptoRetencionSRI().getTipoConceptoRetencion() == TipoConceptoRetencion.IVA) {
/*  185: 199 */           facturaProveedorSRI.setEmitirRetencionIva0(true);
/*  186:     */         }
/*  187:     */       }
/*  188:     */     }
/*  189: 203 */     if ((suma.compareTo(BigDecimal.ZERO) > 0) || (facturaProveedorSRI.isEmitirRetencionIva0())) {
/*  190: 204 */       return true;
/*  191:     */     }
/*  192: 206 */     return false;
/*  193:     */   }
/*  194:     */   
/*  195:     */   private void validarFecha(FacturaProveedorSRI facturaProveedorSRI)
/*  196:     */     throws ExcepcionAS2Financiero
/*  197:     */   {
/*  198: 211 */     FacturaProveedorSRI consultaFacturaProveedorSRI = this.servicioFacturaProveedorSRI.buscarPorId(Integer.valueOf(facturaProveedorSRI.getIdFacturaProveedorSRI()));
/*  199: 212 */     if ((consultaFacturaProveedorSRI != null) && (consultaFacturaProveedorSRI.getFechaModificacion() != null))
/*  200:     */     {
/*  201: 213 */       boolean compararFecha = consultaFacturaProveedorSRI.getFechaModificacion() == facturaProveedorSRI.getFechaModificacion();
/*  202: 214 */       if (!compararFecha) {
/*  203: 215 */         compararFecha = FuncionesUtiles.compararFechaAnteriorOIgual(consultaFacturaProveedorSRI.getFechaModificacion(), facturaProveedorSRI
/*  204: 216 */           .getFechaModificacion());
/*  205:     */       }
/*  206: 218 */       if (!compararFecha) {
/*  207: 219 */         throw new ExcepcionAS2Financiero("msg_error_creacion_retencion");
/*  208:     */       }
/*  209:     */     }
/*  210:     */   }
/*  211:     */   
/*  212:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  213:     */   public void guardar(FacturaProveedorSRI facturaProveedorSRI)
/*  214:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  215:     */   {
/*  216:     */     try
/*  217:     */     {
/*  218: 236 */       validarFecha(facturaProveedorSRI);
/*  219: 237 */       if ((facturaProveedorSRI.getDocumento() != null) && (facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) && 
/*  220: 238 */         (emiteRetencion(facturaProveedorSRI)))
/*  221:     */       {
/*  222: 240 */         int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(facturaProveedorSRI.getIdOrganizacion()).booleanValue() ? 2 : 1;
/*  223: 241 */         facturaProveedorSRI.setAmbiente(ambiente);
/*  224: 242 */         int tipoEmision = 1;
/*  225: 243 */         facturaProveedorSRI.setIndicadorDocumentoElectronico(true);
/*  226:     */         
/*  227: 245 */         facturaProveedorSRI.setTipoEmision(tipoEmision);
/*  228: 247 */         if (facturaProveedorSRI.getIdSucursal() != 0)
/*  229:     */         {
/*  230: 248 */           Sucursal sucursal = this.servicioSucursal.cargarDetalle(facturaProveedorSRI.getIdSucursal());
/*  231: 249 */           facturaProveedorSRI.setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/*  232:     */         }
/*  233: 251 */         if (facturaProveedorSRI.getIdOrganizacion() != 0)
/*  234:     */         {
/*  235: 252 */           String dirMatriz = "";
/*  236:     */           try
/*  237:     */           {
/*  238: 254 */             dirMatriz = this.servicioOrganizacion.obtenerDireccionMatriz(facturaProveedorSRI.getIdOrganizacion());
/*  239:     */           }
/*  240:     */           catch (Exception e)
/*  241:     */           {
/*  242: 256 */             dirMatriz = "N/A";
/*  243:     */           }
/*  244: 258 */           facturaProveedorSRI.setDireccionMatriz(dirMatriz);
/*  245:     */         }
/*  246:     */       }
/*  247: 264 */       BigDecimal valorAnteriorRetenido = facturaProveedorSRI.getTotalValorRetenido();
/*  248: 265 */       validar(facturaProveedorSRI);
/*  249:     */       
/*  250:     */ 
/*  251: 268 */       int numeroRetencion = Integer.parseInt(facturaProveedorSRI.getNumeroRetencion());
/*  252: 269 */       if (numeroRetencion > 0)
/*  253:     */       {
/*  254: 270 */         String numeroRetencionString = FuncionesUtiles.completarALaIzquierda('0', 9, String.valueOf(numeroRetencion));
/*  255: 271 */         facturaProveedorSRI.setNumeroRetencion(numeroRetencionString);
/*  256:     */       }
/*  257: 274 */       BigDecimal valor = BigDecimal.ZERO;
/*  258: 275 */       for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/*  259: 276 */         if (!detalleFacturaProveedorSRI.isEliminado()) {
/*  260: 277 */           valor = valor.add(detalleFacturaProveedorSRI.getValorRetencion());
/*  261:     */         }
/*  262:     */       }
/*  263: 280 */       facturaProveedorSRI.setTotalValorRetenido(valor);
/*  264: 282 */       if (((facturaProveedorSRI.getFacturaProveedor() != null) && 
/*  265: 283 */         (facturaProveedorSRI.getValorRetenidoIVA().add(facturaProveedorSRI.getValorRetenidoFuente()).compareTo(BigDecimal.ZERO) > 0)) || 
/*  266: 284 */         (facturaProveedorSRI.getPago() != null)) {
/*  267: 285 */         generarPago(facturaProveedorSRI);
/*  268:     */       }
/*  269: 288 */       facturaProveedorSRI.setIndicadorRetencionEmitida(false);
/*  270: 290 */       for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI())
/*  271:     */       {
/*  272: 292 */         if (!detalleFacturaProveedorSRI.isEliminado()) {
/*  273: 295 */           facturaProveedorSRI.setIndicadorRetencionEmitida(true);
/*  274:     */         }
/*  275: 298 */         this.detalleFacturaProveedorSRIDao.guardar(detalleFacturaProveedorSRI);
/*  276:     */       }
/*  277: 302 */       if ((!facturaProveedorSRI.getNumeroRetencion().equals(String.valueOf(0))) && 
/*  278: 303 */         (facturaProveedorSRI.getFacturaClienteSRI() == null) && 
/*  279: 304 */         (this.facturaProveedorSRIDao.existeRetencion(facturaProveedorSRI))) {
/*  280: 305 */         throw new ExcepcionAS2("msg_error_numero_retencion_existe", ": " + facturaProveedorSRI.getNumeroRetencion());
/*  281:     */       }
/*  282: 309 */       this.facturaProveedorSRIDao.guardar(facturaProveedorSRI);
/*  283:     */       
/*  284: 311 */       FacturaProveedorSRI facturaProveedorSRIDetalle = cargarDetalle(facturaProveedorSRI.getIdFacturaProveedorSRI());
/*  285: 315 */       if (facturaProveedorSRI.getCompraCajaChica() != null)
/*  286:     */       {
/*  287: 316 */         int idCompraCajaChica = facturaProveedorSRIDetalle.getCompraCajaChica().getIdCompraCajaChica();
/*  288:     */         
/*  289:     */ 
/*  290:     */ 
/*  291: 320 */         actualizarSaldoCajaChica(idCompraCajaChica, facturaProveedorSRI
/*  292: 321 */           .isIndicadorRetencionAsumida() ? facturaProveedorSRI
/*  293: 322 */           .getCompraCajaChica().getValor()
/*  294: 323 */           .subtract(facturaProveedorSRI.getCompraCajaChica().getDescuentoImpuesto()) : facturaProveedorSRI
/*  295: 324 */           .getCompraCajaChica().getValor()
/*  296: 325 */           .subtract(facturaProveedorSRI.getCompraCajaChica().getDescuentoImpuesto())
/*  297: 326 */           .subtract(facturaProveedorSRI.getTotalValorRetenido()), facturaProveedorSRI
/*  298: 327 */           .getCompraCajaChica().getCajaChica());
/*  299:     */       }
/*  300: 330 */       if ((facturaProveedorSRI.getFacturaClienteSRI() == null) && (
/*  301: 331 */         (facturaProveedorSRI.getTotalValorRetenido().compareTo(BigDecimal.ZERO) != 0) || (facturaProveedorSRI.isEmitirRetencionIva0()))) {
/*  302: 332 */         this.servicioSecuencia.actualizarSecuencia(facturaProveedorSRI.getDocumento().getSecuencia(), facturaProveedorSRI.getNumeroRetencion());
/*  303:     */       }
/*  304: 335 */       if ((facturaProveedorSRI.getEmail() != null) && (!facturaProveedorSRI.getEmail().isEmpty()) && 
/*  305: 336 */         (facturaProveedorSRI.getFacturaProveedor() != null) && (facturaProveedorSRI.getFacturaProveedor().getEmpresa() != null))
/*  306:     */       {
/*  307: 339 */         Empresa empresa = null;
/*  308: 340 */         if (facturaProveedorSRI.getFacturaProveedor() != null) {
/*  309: 341 */           empresa = facturaProveedorSRI.getFacturaProveedor().getEmpresa();
/*  310:     */         }
/*  311: 343 */         if (facturaProveedorSRI.getCompraCajaChica() != null) {
/*  312: 344 */           empresa = facturaProveedorSRI.getCompraCajaChica().getEmpresa();
/*  313:     */         }
/*  314: 346 */         this.servicioEmpresa.actualizarMails(empresa, facturaProveedorSRI.getEmail(), DocumentoBase.RETENCION_PROVEEDOR);
/*  315:     */       }
/*  316: 349 */       if ((facturaProveedorSRI.getDocumento() != null) && (facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) && 
/*  317: 350 */         (emiteRetencion(facturaProveedorSRI)))
/*  318:     */       {
/*  319: 351 */         facturaProveedorSRI = this.servicioFacturaProveedorSRIXML.generarClaveAcceso(null, facturaProveedorSRI, true);
/*  320:     */         
/*  321: 353 */         boolean indicadorEnviarEmail = ParametrosSistema.isComprobantesElectronicosEnviarEmailGuardar(facturaProveedorSRI
/*  322: 354 */           .getIdOrganizacion()).booleanValue();
/*  323: 355 */         if ((indicadorEnviarEmail) && (!facturaProveedorSRI.isIndicadorRetencionAsumida())) {
/*  324: 357 */           if (facturaProveedorSRI.getAmbiente() == 2) {
/*  325:     */             try
/*  326:     */             {
/*  327: 359 */               enviarMail(facturaProveedorSRI, facturaProveedorSRI.getDocumentoElectronico(), null);
/*  328:     */             }
/*  329:     */             catch (Exception e)
/*  330:     */             {
/*  331: 361 */               e.printStackTrace();
/*  332:     */               
/*  333: 363 */               System.out.println("Error en la generacion del reporte y enviar por mail al cliente (Facturacion electronica)" + e
/*  334: 364 */                 .getMessage());
/*  335:     */             }
/*  336:     */           }
/*  337:     */         }
/*  338:     */       }
/*  339:     */     }
/*  340:     */     catch (ExcepcionAS2Financiero e)
/*  341:     */     {
/*  342: 371 */       this.context.setRollbackOnly();
/*  343: 372 */       throw e;
/*  344:     */     }
/*  345:     */     catch (ExcepcionAS2 e)
/*  346:     */     {
/*  347: 375 */       this.context.setRollbackOnly();
/*  348: 376 */       throw e;
/*  349:     */     }
/*  350:     */     catch (Exception e)
/*  351:     */     {
/*  352: 379 */       this.context.setRollbackOnly();
/*  353: 380 */       e.printStackTrace();
/*  354: 381 */       throw new ExcepcionAS2(e);
/*  355:     */     }
/*  356:     */   }
/*  357:     */   
/*  358:     */   public void actualizarRetencion(FacturaProveedorSRI facturaProveedorSRI)
/*  359:     */     throws ExcepcionAS2, AS2Exception
/*  360:     */   {
/*  361: 396 */     if ((facturaProveedorSRI.getDocumento() != null) && (facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) && 
/*  362: 397 */       (!facturaProveedorSRI.getEstado().equals(Estado.PROCESADO)) && 
/*  363: 398 */       (emiteRetencion(facturaProveedorSRI)))
/*  364:     */     {
/*  365: 400 */       int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(facturaProveedorSRI.getIdOrganizacion()).booleanValue() ? 2 : 1;
/*  366: 401 */       facturaProveedorSRI.setAmbiente(ambiente);
/*  367: 402 */       int tipoEmision = 1;
/*  368: 403 */       facturaProveedorSRI.setIndicadorDocumentoElectronico(true);
/*  369: 404 */       facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/*  370:     */       
/*  371: 406 */       facturaProveedorSRI.setTipoEmision(tipoEmision);
/*  372: 408 */       if (facturaProveedorSRI.getIdSucursal() != 0)
/*  373:     */       {
/*  374: 409 */         Sucursal sucursal = this.servicioSucursal.cargarDetalle(facturaProveedorSRI.getIdSucursal());
/*  375: 410 */         facturaProveedorSRI.setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/*  376:     */       }
/*  377: 412 */       if (facturaProveedorSRI.getIdOrganizacion() != 0)
/*  378:     */       {
/*  379: 413 */         String dirMatriz = "";
/*  380:     */         try
/*  381:     */         {
/*  382: 415 */           dirMatriz = this.servicioOrganizacion.obtenerDireccionMatriz(facturaProveedorSRI.getIdOrganizacion());
/*  383:     */         }
/*  384:     */         catch (Exception e)
/*  385:     */         {
/*  386: 417 */           dirMatriz = "N/A";
/*  387:     */         }
/*  388: 419 */         facturaProveedorSRI.setDireccionMatriz(dirMatriz);
/*  389:     */       }
/*  390:     */     }
/*  391: 426 */     validar(facturaProveedorSRI);
/*  392: 427 */     this.facturaProveedorSRIDao.guardar(facturaProveedorSRI);
/*  393: 429 */     if (facturaProveedorSRI.getPago() != null)
/*  394:     */     {
/*  395: 430 */       LOG.info("ENTRO CORRECION RETENCION ");
/*  396: 431 */       Pago pago = this.servicioPago.cargarDetalle(facturaProveedorSRI.getPago().getIdPago());
/*  397: 432 */       pago.setDescripcion("RETENCION #" + facturaProveedorSRI.getNumeroRetencion());
/*  398: 433 */       this.pagoDao.guardar(pago);
/*  399: 434 */       this.servicioPago.contabilizar(pago);
/*  400:     */     }
/*  401: 437 */     if ((facturaProveedorSRI.getDocumento() != null) && (facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) && 
/*  402: 438 */       (!facturaProveedorSRI.getEstado().equals(Estado.PROCESADO)) && 
/*  403: 439 */       (emiteRetencion(facturaProveedorSRI))) {
/*  404: 440 */       facturaProveedorSRI = this.servicioFacturaProveedorSRIXML.generarClaveAcceso(null, facturaProveedorSRI, true);
/*  405:     */     }
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void actualizaFacturaProveedorSRI(int idFacturaProveedorSRI, Estado estadoFactura, EstadoDocumentoElectronico estadoSRI, Date fechaAutorizacion, String numeroAutorizacion, String mensajeSRI)
/*  409:     */   {
/*  410: 449 */     this.facturaProveedorSRIDao.actualizaFacturaProveedorSRI(idFacturaProveedorSRI, estadoFactura, estadoSRI, fechaAutorizacion, numeroAutorizacion, mensajeSRI);
/*  411:     */   }
/*  412:     */   
/*  413:     */   private void actualizarSaldoCajaChica(int idCompraCajaChica, BigDecimal valor, CajaChica cajaChica)
/*  414:     */     throws ExcepcionAS2Financiero
/*  415:     */   {
/*  416: 462 */     CompraCajaChica compraCajaChica = this.servicioCompraCajaChica.cargarDetalle(idCompraCajaChica);
/*  417: 463 */     if (compraCajaChica != null)
/*  418:     */     {
/*  419: 465 */       System.out.println("valor 1 " + valorAcumuladoCajaChica(compraCajaChica, cajaChica));
/*  420: 466 */       System.out.println("valor 2 " + valor);
/*  421: 467 */       compraCajaChica.getCajaChica().setValor(valorAcumuladoCajaChica(compraCajaChica, cajaChica).add(valor));
/*  422: 468 */       this.cajaChicaDao.guardar(compraCajaChica.getCajaChica());
/*  423:     */     }
/*  424:     */   }
/*  425:     */   
/*  426:     */   public BigDecimal valorAcumuladoCajaChica(CompraCajaChica compraCajaChica, CajaChica cajaChica)
/*  427:     */   {
/*  428: 475 */     return this.cajaChicaDao.valorAcumuladoCompraCajaChica(compraCajaChica, cajaChica);
/*  429:     */   }
/*  430:     */   
/*  431:     */   private void generarPago(FacturaProveedorSRI facturaProveedorSRI)
/*  432:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  433:     */   {
/*  434: 487 */     Pago pago = null;
/*  435: 488 */     HashMap<Integer, DetalleFormaPago> hmDetalleFormaPago = new HashMap();
/*  436: 489 */     HashMap<Integer, BigDecimal> hmValoresRetenidos = new HashMap();
/*  437: 491 */     if (facturaProveedorSRI.getPago() == null)
/*  438:     */     {
/*  439: 492 */       pago = new Pago();
/*  440: 493 */       pago.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  441: 494 */       pago.setSucursal(AppUtil.getSucursal());
/*  442: 495 */       pago.setNumero("");
/*  443:     */     }
/*  444:     */     else
/*  445:     */     {
/*  446: 498 */       pago = this.servicioPago.cargarDetalle(facturaProveedorSRI.getPago().getId());
/*  447: 499 */       this.servicioPago.detach(pago);
/*  448: 501 */       for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago())
/*  449:     */       {
/*  450: 502 */         hmDetalleFormaPago.put(Integer.valueOf(detalleFormaPago.getCuentaContableFormaPago().getId()), detalleFormaPago);
/*  451: 503 */         detalleFormaPago.setEliminado(true);
/*  452:     */       }
/*  453:     */     }
/*  454: 507 */     pago.setEmpresa(facturaProveedorSRI.getFacturaProveedor().getEmpresa());
/*  455: 508 */     pago.setBeneficiario("");
/*  456: 509 */     pago.setIndicadorRetencionAsumida(facturaProveedorSRI.isIndicadorRetencionAsumida());
/*  457: 510 */     pago.setIndicadorRetencion(true);
/*  458:     */     
/*  459: 512 */     pago.setDescripcion("RETENCION #" + facturaProveedorSRI.getNumeroRetencion());
/*  460: 513 */     pago.setEstado(Estado.CONTABILIZADO);
/*  461: 518 */     if (facturaProveedorSRI.getFechaEmisionRetencion() == null) {
/*  462: 519 */       pago.setFecha(facturaProveedorSRI.getFechaRegistro());
/*  463:     */     } else {
/*  464: 521 */       pago.setFecha(facturaProveedorSRI.getFechaEmisionRetencion());
/*  465:     */     }
/*  466: 525 */     DetalleFormaPago detalleFormaPago = null;
/*  467: 526 */     FormaPago formaPago = null;
/*  468: 527 */     Map<String, String> filters = new HashMap();
/*  469: 528 */     filters.put("indicadorRetencionFuente", "true");
/*  470: 529 */     List<FormaPago> listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", true, filters);
/*  471: 530 */     if (!listaFormaPago.isEmpty()) {
/*  472: 531 */       formaPago = (FormaPago)listaFormaPago.get(0);
/*  473:     */     }
/*  474: 562 */     for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI())
/*  475:     */     {
/*  476: 564 */       BigDecimal valorRetenido = detalleFacturaProveedorSRI.getValorRetencion();
/*  477: 566 */       if ((!detalleFacturaProveedorSRI.isEliminado()) && (valorRetenido.compareTo(BigDecimal.ZERO) > 0))
/*  478:     */       {
/*  479: 568 */         if (detalleFacturaProveedorSRI.getConceptoRetencionSRI().getCuentaContable() == null) {
/*  480: 570 */           throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " Concepto " + detalleFacturaProveedorSRI.getConceptoRetencionSRI().getCodigo());
/*  481:     */         }
/*  482: 573 */         agregarValorRetenido(hmValoresRetenidos, Integer.valueOf(detalleFacturaProveedorSRI.getConceptoRetencionSRI().getCuentaContable()
/*  483: 574 */           .getIdCuentaContable()), valorRetenido);
/*  484:     */       }
/*  485:     */     }
/*  486: 578 */     BigDecimal totalValorRetenido = BigDecimal.ZERO;
/*  487: 580 */     for (Iterator<Integer> it = hmValoresRetenidos.keySet().iterator(); it.hasNext();)
/*  488:     */     {
/*  489: 581 */       Integer idCuentaContable = (Integer)it.next();
/*  490: 582 */       BigDecimal valorRetenido = (BigDecimal)hmValoresRetenidos.get(idCuentaContable);
/*  491: 584 */       if (hmDetalleFormaPago.containsKey(idCuentaContable))
/*  492:     */       {
/*  493: 585 */         detalleFormaPago = (DetalleFormaPago)hmDetalleFormaPago.get(idCuentaContable);
/*  494: 586 */         detalleFormaPago.setEliminado(false);
/*  495: 587 */         if (detalleFormaPago.getDocumentoReferencia() == null) {
/*  496: 588 */           detalleFormaPago.setDocumentoReferencia("");
/*  497:     */         }
/*  498:     */       }
/*  499:     */       else
/*  500:     */       {
/*  501: 591 */         CuentaContable cuentaContableFormaPago = this.servicioCuentaContable.buscarPorId(idCuentaContable);
/*  502: 592 */         detalleFormaPago = new DetalleFormaPago();
/*  503: 593 */         detalleFormaPago.setFormaPago(formaPago);
/*  504: 594 */         detalleFormaPago.setPago(pago);
/*  505: 595 */         detalleFormaPago.setDocumentoReferencia("");
/*  506: 596 */         detalleFormaPago.setCuentaContableFormaPago(cuentaContableFormaPago);
/*  507: 597 */         pago.getListaDetalleFormaPago().add(detalleFormaPago);
/*  508:     */       }
/*  509: 600 */       detalleFormaPago.setValor(valorRetenido);
/*  510: 601 */       totalValorRetenido = totalValorRetenido.add(valorRetenido);
/*  511:     */     }
/*  512: 604 */     pago.setValor(totalValorRetenido);
/*  513:     */     
/*  514: 606 */     Documento documento = (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PAGO_RETENCION_PROVEEDOR, pago.getIdOrganizacion()).get(0);
/*  515:     */     
/*  516: 608 */     this.servicioDocumento.detach(documento);
/*  517:     */     
/*  518: 610 */     pago.setDocumento(documento);
/*  519:     */     
/*  520:     */ 
/*  521: 613 */     pago.getDocumento().setIndicadorContabilizar(facturaProveedorSRI.getDocumento().isIndicadorContabilizar());
/*  522:     */     
/*  523:     */ 
/*  524: 616 */     this.servicioPago.cargarFacturasPendientes(pago, facturaProveedorSRI.getFacturaProveedor().getId(), false, false);
/*  525:     */     
/*  526: 618 */     this.servicioPago.guardar(pago);
/*  527: 619 */     facturaProveedorSRI.setPago(pago);
/*  528:     */   }
/*  529:     */   
/*  530:     */   private void agregarValorRetenido(HashMap<Integer, BigDecimal> hmValoresRetenidos, Integer idCuentaContable, BigDecimal valorRetenido)
/*  531:     */   {
/*  532: 625 */     if (hmValoresRetenidos.containsKey(idCuentaContable)) {
/*  533: 626 */       valorRetenido = valorRetenido.add((BigDecimal)hmValoresRetenidos.get(idCuentaContable));
/*  534:     */     }
/*  535: 629 */     hmValoresRetenidos.put(idCuentaContable, valorRetenido);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void eliminar(FacturaProveedorSRI facturaProveedorSRI)
/*  539:     */   {
/*  540: 641 */     this.facturaProveedorSRIDao.eliminar(facturaProveedorSRI);
/*  541:     */   }
/*  542:     */   
/*  543:     */   public FacturaProveedorSRI buscarPorId(Integer id)
/*  544:     */   {
/*  545: 652 */     return (FacturaProveedorSRI)this.facturaProveedorSRIDao.buscarPorId(id);
/*  546:     */   }
/*  547:     */   
/*  548:     */   public FacturaProveedorSRI cargarDetalle(int idFacturaProveedorSRI)
/*  549:     */   {
/*  550: 662 */     return this.facturaProveedorSRIDao.cargarDetalle(idFacturaProveedorSRI);
/*  551:     */   }
/*  552:     */   
/*  553:     */   public List<FacturaProveedorSRI> obtenerFacturasMes(int anio, int mes, int idOrganizacion)
/*  554:     */   {
/*  555: 672 */     return this.facturaProveedorSRIDao.obtenerFacturasMes(anio, mes, idOrganizacion);
/*  556:     */   }
/*  557:     */   
/*  558:     */   public FacturaProveedorSRI obtenerFacturaProveedorSRI(int idFacturaProveedor)
/*  559:     */   {
/*  560: 682 */     return this.facturaProveedorSRIDao.obtenerFacturaProveedorSRI(idFacturaProveedor);
/*  561:     */   }
/*  562:     */   
/*  563:     */   public List<Object[]> getRetencionSRI(int mes, int anio, int idOrganizacion, Sucursal sucursalFP, Sucursal sucursalRetencion, PuntoDeVenta puntoVentaRetencion, String orden, boolean anuladas)
/*  564:     */     throws ExcepcionAS2
/*  565:     */   {
/*  566: 693 */     List<Object[]> lresult = new ArrayList();
/*  567: 694 */     lresult = this.reporteRetencionSRIDao.getRetencionSRI(mes, anio, idOrganizacion, sucursalFP, sucursalRetencion, puntoVentaRetencion, orden);
/*  568: 695 */     if ((orden != null) && (orden.equals("POR_RETENCION")) && (anuladas))
/*  569:     */     {
/*  570: 697 */       TipoComprobanteSRI tcsri = ((Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.RETENCION_PROVEEDOR, idOrganizacion).get(0)).getTipoComprobanteSRI();
/*  571: 698 */       lresult.addAll(this.reporteRetencionSRIDao.getRetencionesAnuladas(mes, anio, idOrganizacion, sucursalFP, sucursalRetencion, puntoVentaRetencion, orden, tcsri));
/*  572:     */     }
/*  573: 711 */     return lresult;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public List getReporteFacturaProveedorSRI(int idFacturaProveedorSRI)
/*  577:     */     throws ExcepcionAS2
/*  578:     */   {
/*  579: 722 */     return this.facturaProveedorSRIDao.getReporteFacturaProveedorSRI(idFacturaProveedorSRI);
/*  580:     */   }
/*  581:     */   
/*  582:     */   private void validar(FacturaProveedorSRI facturaProveedorSRI)
/*  583:     */     throws ExcepcionAS2Financiero
/*  584:     */   {
/*  585: 728 */     if ((facturaProveedorSRI.getFechaEmisionRetencion() != null) && (facturaProveedorSRI.getFechaEmision() != null))
/*  586:     */     {
/*  587: 730 */       Date fechaMaximaRetencion = FuncionesUtiles.sumarFechaAnios(facturaProveedorSRI.getFechaEmision(), 1);
/*  588: 731 */       if ((!facturaProveedorSRI.isIndicadorDocumentoElectronico()) && 
/*  589: 732 */         (facturaProveedorSRI.getFechaEmisionRetencion().compareTo(fechaMaximaRetencion) > 0)) {
/*  590: 733 */         throw new ExcepcionAS2Financiero("msg_error_fecha_emision_retencion");
/*  591:     */       }
/*  592: 737 */       if (facturaProveedorSRI.getFechaEmisionRetencion().compareTo(facturaProveedorSRI.getFechaEmision()) < 0) {
/*  593: 738 */         throw new ExcepcionAS2Financiero("msg_error_fecha_emision_retencion");
/*  594:     */       }
/*  595:     */     }
/*  596: 744 */     if (facturaProveedorSRI.getFechaRegistro().compareTo(facturaProveedorSRI.getFechaEmision()) < 0) {
/*  597: 745 */       throw new ExcepcionAS2Financiero("msg_error_fecha_registro_factura");
/*  598:     */     }
/*  599: 749 */     if (facturaProveedorSRI.getAutorizacion() == null) {
/*  600: 750 */       throw new ExcepcionAS2Financiero("msg_error_autorizaciono_factura");
/*  601:     */     }
/*  602: 751 */     if (facturaProveedorSRI.isIndicadorValidar())
/*  603:     */     {
/*  604: 752 */       int longitudAutorizacion = 10;
/*  605: 753 */       if (facturaProveedorSRI.isIndicadorFacturaElectronica()) {
/*  606: 754 */         longitudAutorizacion = 50;
/*  607:     */       }
/*  608: 756 */       if (facturaProveedorSRI.getAutorizacion().length() > longitudAutorizacion) {
/*  609: 757 */         throw new ExcepcionAS2Financiero("msg_error_autorizaciono_factura");
/*  610:     */       }
/*  611:     */     }
/*  612: 762 */     BigDecimal baseImponibleRetencion = BigDecimal.ZERO;
/*  613: 763 */     for (Iterator localIterator = facturaProveedorSRI.getListaDetalleFacturaProveedorSRI().iterator(); localIterator.hasNext();)
/*  614:     */     {
/*  615: 763 */       detalleFacturaProveedorSRI = (DetalleFacturaProveedorSRI)localIterator.next();
/*  616: 764 */       if ((!detalleFacturaProveedorSRI.isEliminado()) && 
/*  617: 765 */         (detalleFacturaProveedorSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.FUENTE))) {
/*  618: 768 */         baseImponibleRetencion = baseImponibleRetencion.add(detalleFacturaProveedorSRI.getBaseImponibleRetencion()).add(detalleFacturaProveedorSRI.getBaseImponibleTarifaCero()).add(detalleFacturaProveedorSRI.getBaseImponibleDiferenteCero()).add(detalleFacturaProveedorSRI.getBaseImponibleNoObjetoIva());
/*  619:     */       }
/*  620:     */     }
/*  621:     */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI;
/*  622: 771 */     if (facturaProveedorSRI.getTipoComprobanteSRI().getCodigo().equals("19"))
/*  623:     */     {
/*  624: 772 */       if ((facturaProveedorSRI.getFacturaClienteSRI() == null) && (facturaProveedorSRI.getBaseImponible().compareTo(baseImponibleRetencion) < 0)) {
/*  625: 773 */         throw new ExcepcionAS2Financiero("msg_error_base_imponible");
/*  626:     */       }
/*  627:     */     }
/*  628: 776 */     else if ((facturaProveedorSRI.getFacturaClienteSRI() == null) && (facturaProveedorSRI.getBaseImponible().compareTo(baseImponibleRetencion) != 0)) {
/*  629: 777 */       throw new ExcepcionAS2Financiero("msg_error_base_imponible");
/*  630:     */     }
/*  631: 782 */     BigDecimal baseImponibleRetencionIVA = BigDecimal.ZERO;
/*  632: 783 */     for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/*  633: 784 */       if ((!detalleFacturaProveedorSRI.isEliminado()) && (detalleFacturaProveedorSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA))) {
/*  634: 785 */         baseImponibleRetencionIVA = baseImponibleRetencionIVA.add(detalleFacturaProveedorSRI.getBaseImponibleRetencion());
/*  635:     */       }
/*  636:     */     }
/*  637: 789 */     Organizacion organizacion = this.servicioOrganizacion.cargarDetalle(facturaProveedorSRI.getIdOrganizacion());
/*  638: 790 */     if (facturaProveedorSRI.isIndicadorValidar())
/*  639:     */     {
/*  640: 792 */       if ((organizacion != null) && (organizacion.getOrganizacionConfiguracion() != null) && 
/*  641: 793 */         (organizacion.getOrganizacionConfiguracion().getNumeroResolucionContribuyente() != null) && 
/*  642: 794 */         (!organizacion.getOrganizacionConfiguracion().getNumeroResolucionContribuyente().trim().isEmpty()) && 
/*  643: 795 */         (facturaProveedorSRI.getMontoIva().compareTo(baseImponibleRetencionIVA) != 0)) {
/*  644: 796 */         throw new ExcepcionAS2Financiero("msg_error_base_imponible_iva");
/*  645:     */       }
/*  646: 798 */       if ((organizacion != null) && (organizacion.getOrganizacionConfiguracion() != null) && 
/*  647: 799 */         (organizacion.getOrganizacionConfiguracion().getNumeroResolucionContribuyente() != null) && 
/*  648: 800 */         (organizacion.getOrganizacionConfiguracion().getNumeroResolucionContribuyente().trim().isEmpty()) && 
/*  649: 801 */         (facturaProveedorSRI.getMontoIva().compareTo(baseImponibleRetencionIVA) < 0)) {
/*  650: 802 */         throw new ExcepcionAS2Financiero("msg_error_base_imponible_iva");
/*  651:     */       }
/*  652:     */     }
/*  653: 807 */     if ((facturaProveedorSRI.getValorRetenidoIVA().add(facturaProveedorSRI.getValorRetenidoFuente()).compareTo(BigDecimal.ZERO) > 0) || 
/*  654: 808 */       (facturaProveedorSRI.isEmitirRetencionIva0()))
/*  655:     */     {
/*  656: 810 */       if ((facturaProveedorSRI.getEstablecimientoRetencion() == null) || (facturaProveedorSRI.getEstablecimientoRetencion().length() != 3) || 
/*  657: 811 */         (Integer.parseInt(facturaProveedorSRI.getEstablecimientoRetencion()) <= 0)) {
/*  658: 812 */         throw new ExcepcionAS2Financiero("msg_error_establecimiento_retencion");
/*  659:     */       }
/*  660: 815 */       if ((facturaProveedorSRI.getPuntoEmisionRetencion() == null) || (facturaProveedorSRI.getPuntoEmisionRetencion().length() != 3) || 
/*  661: 816 */         (Integer.parseInt(facturaProveedorSRI.getPuntoEmisionRetencion()) <= 0)) {
/*  662: 817 */         throw new ExcepcionAS2Financiero("msg_error_punto_retencion");
/*  663:     */       }
/*  664: 820 */       if ((facturaProveedorSRI.getNumeroRetencion() == null) || (Integer.parseInt(facturaProveedorSRI.getNumeroRetencion()) <= 0)) {
/*  665: 821 */         throw new ExcepcionAS2Financiero("msg_error_numero_retencion");
/*  666:     */       }
/*  667: 823 */       this.facturaProveedorSRIDao.verificarExitenciaRetencion(facturaProveedorSRI);
/*  668: 825 */       if ((facturaProveedorSRI.getAutorizacionRetencion() == null) || (
/*  669: 826 */         (facturaProveedorSRI.getAutorizacionRetencion().length() != 10) && 
/*  670: 827 */         (facturaProveedorSRI.getAutorizacionRetencion().length() != 37) && 
/*  671: 828 */         (facturaProveedorSRI.getAutorizacionRetencion().length() != 49))) {
/*  672: 829 */         throw new ExcepcionAS2Financiero("msg_error_autorizaciono_retencion");
/*  673:     */       }
/*  674: 832 */       if (facturaProveedorSRI.getFechaEmisionRetencion() == null) {
/*  675: 833 */         throw new ExcepcionAS2Financiero("msg_error_retencion_iva");
/*  676:     */       }
/*  677: 838 */       if ((facturaProveedorSRI.getCompraCajaChica() == null) && 
/*  678: 839 */         (facturaProveedorSRI.getFechaEmisionRetencion().compareTo(facturaProveedorSRI.getFechaRegistro()) < 0)) {
/*  679: 840 */         throw new ExcepcionAS2Financiero("msg_error_fecha_emision_retencion");
/*  680:     */       }
/*  681: 842 */       Calendar date = Calendar.getInstance();
/*  682: 843 */       Date fechaActual = date.getTime();
/*  683: 844 */       if ((facturaProveedorSRI.isIndicadorDocumentoElectronico()) && 
/*  684: 845 */         (facturaProveedorSRI.getFechaEmisionRetencion().compareTo(fechaActual) > 0)) {
/*  685: 846 */         throw new ExcepcionAS2Financiero("msg_error_fecha_emision_retencion_posterior");
/*  686:     */       }
/*  687:     */     }
/*  688:     */     else
/*  689:     */     {
/*  690: 850 */       facturaProveedorSRI.setEstablecimientoRetencion("000");
/*  691: 851 */       facturaProveedorSRI.setPuntoEmisionRetencion("000");
/*  692: 852 */       facturaProveedorSRI.setNumeroRetencion("0");
/*  693: 853 */       facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/*  694: 854 */       facturaProveedorSRI.setFechaEmisionRetencion(null);
/*  695:     */     }
/*  696:     */   }
/*  697:     */   
/*  698:     */   public void actualizarEstado(Integer idFacturaProveedorSRI, Estado estado)
/*  699:     */   {
/*  700: 860 */     this.facturaProveedorSRIDao.actualizarEstado(idFacturaProveedorSRI, estado);
/*  701:     */   }
/*  702:     */   
/*  703:     */   public void actualizarFacturaProveedorSRI(FacturaProveedor facturaProveedor)
/*  704:     */     throws ExcepcionAS2
/*  705:     */   {
/*  706: 873 */     if (!facturaProveedor.getDocumento().isIndicadorDocumentoTributario())
/*  707:     */     {
/*  708: 874 */       FacturaProveedorSRI fp = facturaProveedor.getFacturaProveedorSRI();
/*  709: 876 */       if (fp != null)
/*  710:     */       {
/*  711: 877 */         facturaProveedor.setFacturaProveedorSRI(null);
/*  712: 878 */         fp.setFacturaProveedor(null);
/*  713: 879 */         fp.setEliminado(true);
/*  714: 880 */         this.facturaProveedorSRIDao.guardar(fp);
/*  715:     */       }
/*  716:     */     }
/*  717:     */     else
/*  718:     */     {
/*  719: 885 */       BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*  720: 886 */       BigDecimal montoIva = BigDecimal.ZERO;
/*  721: 887 */       BigDecimal montoIce = BigDecimal.ZERO;
/*  722: 888 */       BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  723:     */       FacturaProveedorSRI facturaProveedorSRI;
/*  724: 892 */       if (facturaProveedor.getFacturaProveedorSRI() == null)
/*  725:     */       {
/*  726: 894 */         FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/*  727: 895 */         facturaProveedorSRI.setEstablecimiento("000");
/*  728: 896 */         facturaProveedorSRI.setPuntoEmision("000");
/*  729: 897 */         facturaProveedorSRI.setNumero("0");
/*  730: 898 */         facturaProveedorSRI.setEstablecimientoRetencion("000");
/*  731: 899 */         facturaProveedorSRI.setPuntoEmisionRetencion("000");
/*  732: 900 */         facturaProveedorSRI.setNumeroRetencion("0");
/*  733: 901 */         facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/*  734:     */         
/*  735: 903 */         facturaProveedorSRI.setFacturaProveedor(facturaProveedor);
/*  736: 904 */         facturaProveedor.setFacturaProveedorSRI(facturaProveedorSRI);
/*  737: 905 */         facturaProveedorSRI.setIdSucursal(facturaProveedor.getSucursal().getId());
/*  738:     */       }
/*  739:     */       else
/*  740:     */       {
/*  741: 908 */         facturaProveedorSRI = facturaProveedor.getFacturaProveedorSRI();
/*  742:     */       }
/*  743: 911 */       facturaProveedorSRI.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/*  744:     */       
/*  745: 913 */       facturaProveedorSRI.setTipoIdentificacion(facturaProveedor.getEmpresa().getTipoIdentificacion());
/*  746: 914 */       facturaProveedorSRI.setTipoEmpresa(facturaProveedor.getEmpresa().getTipoEmpresa());
/*  747: 915 */       facturaProveedorSRI.setIndicadorParteRelacionada(facturaProveedor.getEmpresa().getProveedor().isIndicadorParteRelacionada());
/*  748: 916 */       DocumentoBase documentoBase = facturaProveedor.getDocumento().getDocumentoBase();
/*  749: 917 */       if ((documentoBase == DocumentoBase.NOTA_CREDITO_PROVEEDOR) || (documentoBase == DocumentoBase.DEVOLUCION_PROVEEDOR) || (documentoBase == DocumentoBase.NOTA_DEBITO_PROVEEDOR))
/*  750:     */       {
/*  751: 920 */         facturaProveedorSRI.setEstablecimientoModificado(facturaProveedor.getFacturaProveedorPadre().getFacturaProveedorSRI()
/*  752: 921 */           .getEstablecimiento());
/*  753: 922 */         facturaProveedorSRI.setPuntoEmisionModificado(facturaProveedor.getFacturaProveedorPadre().getFacturaProveedorSRI().getPuntoEmision());
/*  754: 923 */         facturaProveedorSRI.setNumeroModificado(facturaProveedor.getFacturaProveedorPadre().getFacturaProveedorSRI().getNumero());
/*  755: 924 */         facturaProveedorSRI.setDocumentoModificado(facturaProveedor.getFacturaProveedorPadre().getFacturaProveedorSRI()
/*  756: 925 */           .getTipoComprobanteSRI());
/*  757: 926 */         facturaProveedorSRI.setAutorizacionModificado(facturaProveedor.getFacturaProveedorPadre().getFacturaProveedorSRI().getAutorizacion());
/*  758: 927 */         facturaProveedorSRI.setTipoIdentificacion(facturaProveedor.getFacturaProveedorPadre().getFacturaProveedorSRI()
/*  759: 928 */           .getTipoIdentificacion());
/*  760: 930 */         if (facturaProveedorSRI.getCreditoTributarioSRI() == null) {
/*  761: 931 */           facturaProveedorSRI.setCreditoTributarioSRI(facturaProveedor.getFacturaProveedorPadre().getFacturaProveedorSRI()
/*  762: 932 */             .getCreditoTributarioSRI());
/*  763:     */         }
/*  764:     */       }
/*  765: 936 */       for (DetalleFacturaProveedor detalleFacturaProveedor : facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  766: 938 */         if (!detalleFacturaProveedor.isEliminado())
/*  767:     */         {
/*  768: 939 */           for (ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor : detalleFacturaProveedor
/*  769: 940 */             .getListaImpuestoProductoFacturaProveedor()) {
/*  770: 941 */             if (!impuestoProductoFacturaProveedor.isEliminado()) {
/*  771: 943 */               if (impuestoProductoFacturaProveedor.getImpuesto().isIndicadorImpuestoTributario())
/*  772:     */               {
/*  773: 944 */                 baseImponibleDiferenteCero = baseImponibleDiferenteCero.add(detalleFacturaProveedor.getBaseImponible());
/*  774: 945 */                 montoIva = montoIva.add(impuestoProductoFacturaProveedor.getImpuestoProducto());
/*  775:     */               }
/*  776: 947 */               else if (impuestoProductoFacturaProveedor.getImpuesto().isIndicadorNoObjetoIVA())
/*  777:     */               {
/*  778: 948 */                 baseImponibleNoObjetoIva = baseImponibleNoObjetoIva.add(detalleFacturaProveedor.getBaseImponible());
/*  779:     */               }
/*  780:     */             }
/*  781:     */           }
/*  782: 952 */           if (detalleFacturaProveedor.isIndicadorImpuestoIce()) {
/*  783: 953 */             montoIce = montoIce.add(detalleFacturaProveedor.getPrecio());
/*  784:     */           }
/*  785:     */         }
/*  786:     */       }
/*  787: 961 */       facturaProveedorSRI.setEstado(facturaProveedor.getEstado());
/*  788:     */       
/*  789:     */ 
/*  790: 964 */       facturaProveedorSRI.setEliminado(false);
/*  791: 966 */       if (facturaProveedorSRI.getTipoComprobanteSRI() == null)
/*  792:     */       {
/*  793: 968 */         TipoComprobanteSRI tipoComprobanteSRI = facturaProveedor.getDocumento().getTipoComprobanteSRI();
/*  794: 969 */         if (tipoComprobanteSRI == null) {
/*  795: 970 */           throw new ExcepcionAS2("msgs_error_info_configuracion_documento_sri");
/*  796:     */         }
/*  797: 972 */         facturaProveedorSRI.setTipoComprobanteSRI(tipoComprobanteSRI);
/*  798:     */       }
/*  799: 976 */       baseImponibleDiferenteCero = FuncionesUtiles.redondearBigDecimal(baseImponibleDiferenteCero, 2);
/*  800: 977 */       montoIva = FuncionesUtiles.redondearBigDecimal(montoIva, 2);
/*  801: 978 */       montoIce = FuncionesUtiles.redondearBigDecimal(montoIce, 2);
/*  802: 979 */       baseImponibleNoObjetoIva = FuncionesUtiles.redondearBigDecimal(baseImponibleNoObjetoIva, 2);
/*  803:     */       
/*  804: 981 */       facturaProveedorSRI.setBaseImponibleDiferenteCero(baseImponibleDiferenteCero);
/*  805:     */       
/*  806:     */ 
/*  807: 984 */       BigDecimal auxBaseImponibleTarifaCero = facturaProveedor.getTotal().subtract(facturaProveedor.getDescuento()).subtract(baseImponibleDiferenteCero).subtract(baseImponibleNoObjetoIva);
/*  808: 985 */       if (auxBaseImponibleTarifaCero.compareTo(BigDecimal.ZERO) < 0) {
/*  809: 986 */         facturaProveedorSRI.setBaseImponibleTarifaCero(BigDecimal.ZERO);
/*  810:     */       } else {
/*  811: 988 */         facturaProveedorSRI.setBaseImponibleTarifaCero(auxBaseImponibleTarifaCero);
/*  812:     */       }
/*  813: 991 */       facturaProveedorSRI.setIndicadorSaldoInicial(facturaProveedor.isIndicadorSaldoInicial());
/*  814: 992 */       facturaProveedorSRI.setIdentificacionProveedor(facturaProveedor.getEmpresa().getIdentificacion().trim());
/*  815: 993 */       DireccionEmpresa direccionEmpresa = this.servicioEmpresa.buscarDireccionEmpresaPorId(facturaProveedor.getDireccionEmpresa().getId());
/*  816: 994 */       facturaProveedorSRI.setDireccionProveedor(direccionEmpresa.getDireccionCompleta());
/*  817: 995 */       facturaProveedorSRI.setTelefonoProveedor(direccionEmpresa.getTelefono1());
/*  818: 996 */       facturaProveedorSRI.setCiudad(direccionEmpresa.getCiudad().getNombre());
/*  819: 997 */       facturaProveedorSRI.setProvincia(direccionEmpresa.getCiudad().getProvincia().getNombre());
/*  820: 998 */       facturaProveedorSRI.setNombreProveedor(facturaProveedor.getEmpresa().getNombreFiscal());
/*  821: 999 */       facturaProveedorSRI.setFechaRegistro(facturaProveedor.getFecha());
/*  822:1001 */       if (facturaProveedorSRI.getFechaEmision() == null) {
/*  823:1002 */         facturaProveedorSRI.setFechaEmision(facturaProveedor.getFecha());
/*  824:     */       }
/*  825:1005 */       facturaProveedorSRI.setMontoIva(montoIva);
/*  826:1006 */       facturaProveedorSRI.setMontoIce(montoIce);
/*  827:1007 */       facturaProveedorSRI.setBaseImponibleNoObjetoIva(baseImponibleNoObjetoIva);
/*  828:     */     }
/*  829:     */   }
/*  830:     */   
/*  831:     */   public boolean existeFactura(FacturaProveedorSRI facturaProveedorSRI)
/*  832:     */   {
/*  833:1025 */     return this.facturaProveedorSRIDao.existeFactura(facturaProveedorSRI);
/*  834:     */   }
/*  835:     */   
/*  836:     */   public List<FacturaProveedorSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  837:     */   {
/*  838:1035 */     return this.facturaProveedorSRIDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public void actualizarFechaDeRegistroPorCajaChica(CajaChica cajaChica)
/*  842:     */   {
/*  843:1045 */     this.facturaProveedorSRIDao.actualizarFechaDeRegistroPorCajaChica(cajaChica);
/*  844:     */   }
/*  845:     */   
/*  846:     */   public List<FacturaProveedorSRI> obtenerFacturasPorEgresoPago(int idAsiento)
/*  847:     */   {
/*  848:1055 */     return this.facturaProveedorSRIDao.obtenerFacturasPorEgresoPago(idAsiento);
/*  849:     */   }
/*  850:     */   
/*  851:     */   public List<ReporteComprasVentasRetenciones> getReporteCompras(int mes, int anio, int idOrganizacion)
/*  852:     */   {
/*  853:1065 */     return this.reporteRetencionSRIDao.getReporteCompras(mes, anio, idOrganizacion);
/*  854:     */   }
/*  855:     */   
/*  856:     */   public List<ReporteComprasVentasRetenciones> getReporteVentas(int mes, int anio, int idOrganizacion, boolean electronicas)
/*  857:     */   {
/*  858:1075 */     return this.reporteRetencionSRIDao.getReporteVentas(mes, anio, idOrganizacion, electronicas);
/*  859:     */   }
/*  860:     */   
/*  861:     */   public List<ReporteComprasVentasRetenciones> getReporteRetencionClientes(int mes, int anio, int idOrganizacion)
/*  862:     */   {
/*  863:1085 */     return this.reporteRetencionSRIDao.getReporteRetencionClientes(mes, anio, idOrganizacion);
/*  864:     */   }
/*  865:     */   
/*  866:     */   public List<ReporteComprasVentasRetenciones> getReporteExportaciones(int mes, int anio, int idOrganizacion)
/*  867:     */   {
/*  868:1095 */     return this.reporteRetencionSRIDao.getReporteExportaciones(mes, anio, idOrganizacion);
/*  869:     */   }
/*  870:     */   
/*  871:     */   public List<ReporteComprasVentasRetenciones> getNumeroComprobantesAnulados(int mes, int anio, int idOrganizacion)
/*  872:     */   {
/*  873:1105 */     return this.reporteRetencionSRIDao.getNumeroComprobantesAnulados(mes, anio, idOrganizacion);
/*  874:     */   }
/*  875:     */   
/*  876:     */   public void cargarConceptosRetencion(FacturaProveedorSRI facturaProveedorSRI, Empresa empresa)
/*  877:     */     throws ExcepcionAS2
/*  878:     */   {
/*  879:1110 */     if (facturaProveedorSRI.getCreditoTributarioSRI() == null)
/*  880:     */     {
/*  881:1111 */       Map<String, String> filters = new HashMap();
/*  882:1112 */       filters.put("predeterminado", "true");
/*  883:1114 */       if (empresa.getProveedor().getCategoriaRetencion() != null)
/*  884:     */       {
/*  885:1115 */         int idCatRetencion = empresa.getProveedor().getCategoriaRetencion().getIdCategoriaRetencion();
/*  886:1116 */         CategoriaRetencion categoriaRetencion = this.servicioCategoriaRetencion.cargarDetalle(idCatRetencion);
/*  887:     */         
/*  888:1118 */         List<Object[]> listaBasesPorTipoProducto = new ArrayList();
/*  889:1119 */         List<Object[]> listaBaseTipoProductoActivos = new ArrayList();
/*  890:1121 */         if ((facturaProveedorSRI.getFacturaProveedor() != null) && (facturaProveedorSRI.getFacturaProveedor().isIndicadorCreditoTributarioSRI()))
/*  891:     */         {
/*  892:1123 */           CreditoTributarioSRI creditoTributarioSRI = getCreditoTributarioSRI(facturaProveedorSRI.getFacturaProveedor());
/*  893:1124 */           facturaProveedorSRI.setCreditoTributarioSRI(creditoTributarioSRI);
/*  894:     */           
/*  895:     */ 
/*  896:1127 */           listaBasesPorTipoProducto = getMontoBasePorTipoProducto(facturaProveedorSRI.getFacturaProveedor());
/*  897:     */         }
/*  898:1132 */         List<ConceptoRetencionSRI> listaConceptoRetencionSRI = this.servicioConceptoRetencionSRI.getConceptoListaRetencionPorFecha(facturaProveedorSRI.getFechaRegistro());
/*  899:1133 */         List<ConceptoRetencionSRI> listaConceptoActivos = new ArrayList();
/*  900:     */         
/*  901:     */ 
/*  902:1136 */         Collections.sort(listaConceptoRetencionSRI, new ConceptoRetencionSRIComparator());
/*  903:     */         
/*  904:1138 */         BigDecimal baseImponibleTotal = facturaProveedorSRI.getBaseImponible();
/*  905:1139 */         for (DetalleCategoriaRetencion detalleCategoriaRetencion : categoriaRetencion.getListaDetalleCategoriaRetencion())
/*  906:     */         {
/*  907:1142 */           int i = Collections.binarySearch(listaConceptoRetencionSRI, detalleCategoriaRetencion.getConceptoRetencionSRI(), new ConceptoRetencionSRIComparator());
/*  908:1145 */           if (i >= 0)
/*  909:     */           {
/*  910:1146 */             conceptoRetencionSRI = (ConceptoRetencionSRI)listaConceptoRetencionSRI.get(i);
/*  911:1147 */             listaConceptoActivos.add(conceptoRetencionSRI);
/*  912:1150 */             for (Object[] dato : listaBasesPorTipoProducto)
/*  913:     */             {
/*  914:1152 */               TipoProducto tipoProducto = (TipoProducto)dato[0];
/*  915:1153 */               TipoConceptoRetencion tipoConcepto = (TipoConceptoRetencion)dato[2];
/*  916:1155 */               if ((conceptoRetencionSRI.getTipoProducto() != null) && (tipoProducto == conceptoRetencionSRI.getTipoProducto()) && 
/*  917:1156 */                 (tipoConcepto == conceptoRetencionSRI.getTipoConceptoRetencion()))
/*  918:     */               {
/*  919:1157 */                 dato[3] = conceptoRetencionSRI;
/*  920:     */                 
/*  921:1159 */                 listaBaseTipoProductoActivos.add(dato);
/*  922:1160 */                 break;
/*  923:     */               }
/*  924:     */             }
/*  925:     */           }
/*  926:     */         }
/*  927:     */         ConceptoRetencionSRI conceptoRetencionSRI;
/*  928:1167 */         for (??? = listaBaseTipoProductoActivos.iterator(); ???.hasNext();)
/*  929:     */         {
/*  930:1167 */           dato = (Object[])???.next();
/*  931:1168 */           ConceptoRetencionSRI conceptoRetencionSRI = (ConceptoRetencionSRI)dato[3];
/*  932:1169 */           BigDecimal baseImponible = FuncionesUtiles.redondearBigDecimal((BigDecimal)dato[1]);
/*  933:1170 */           baseImponibleTotal = baseImponibleTotal.subtract(baseImponible);
/*  934:1171 */           cargarDetalleRetencion(facturaProveedorSRI, conceptoRetencionSRI, baseImponible);
/*  935:     */         }
/*  936:1175 */         BigDecimal baseImponible = BigDecimal.ZERO;
/*  937:1177 */         for (Object[] dato = facturaProveedorSRI.getListaDetalleFacturaProveedorSRI().iterator(); dato.hasNext();)
/*  938:     */         {
/*  939:1177 */           detalle = (DetalleFacturaProveedorSRI)dato.next();
/*  940:1178 */           if (detalle.getConceptoRetencionSRI().getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA)) {
/*  941:1179 */             baseImponible = baseImponible.add(detalle.getBaseImponibleRetencion());
/*  942:     */           }
/*  943:     */         }
/*  944:     */         DetalleFacturaProveedorSRI detalle;
/*  945:1181 */         BigDecimal diferencia = facturaProveedorSRI.getMontoIva().subtract(baseImponible);
/*  946:1183 */         if (diferencia.compareTo(BigDecimal.ZERO) != 0) {
/*  947:1184 */           for (DetalleFacturaProveedorSRI det : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/*  948:1185 */             if (det.getConceptoRetencionSRI().getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA))
/*  949:     */             {
/*  950:1186 */               det.setBaseImponibleRetencion(det.getBaseImponibleRetencion().add(diferencia));
/*  951:1187 */               break;
/*  952:     */             }
/*  953:     */           }
/*  954:     */         }
/*  955:1193 */         if (listaBaseTipoProductoActivos.isEmpty()) {
/*  956:1194 */           for (ConceptoRetencionSRI concepto : listaConceptoActivos) {
/*  957:1195 */             cargarDetalleRetencion(facturaProveedorSRI, concepto, null);
/*  958:     */           }
/*  959:     */         }
/*  960:1199 */         if ((facturaProveedorSRI.getFacturaProveedor() != null) && (baseImponibleTotal.compareTo(BigDecimal.ZERO) > 0)) {
/*  961:1201 */           this.servicioFacturaProveedor.agregarDetalleFacturaSRI332(facturaProveedorSRI);
/*  962:     */         }
/*  963:     */       }
/*  964:1205 */       if (facturaProveedorSRI.getCreditoTributarioSRI() == null)
/*  965:     */       {
/*  966:1207 */         List<CreditoTributarioSRI> lista = this.servicioCreditoTributario.obtenerListaCombo("codigo", true, filters);
/*  967:1208 */         if (!lista.isEmpty()) {
/*  968:1209 */           facturaProveedorSRI.setCreditoTributarioSRI((CreditoTributarioSRI)lista.get(0));
/*  969:     */         }
/*  970:     */       }
/*  971:     */     }
/*  972:1213 */     if (Integer.parseInt(facturaProveedorSRI.getNumeroRetencion()) == 0)
/*  973:     */     {
/*  974:1214 */       facturaProveedorSRI.setFechaEmisionRetencion(facturaProveedorSRI.getFechaRegistro());
/*  975:1215 */       if (facturaProveedorSRI.getCompraCajaChica() != null) {
/*  976:1216 */         facturaProveedorSRI.setFechaEmisionRetencion(facturaProveedorSRI.getCompraCajaChica().getFecha());
/*  977:     */       }
/*  978:1218 */       facturaProveedorSRI.setEstablecimientoRetencion(AppUtil.getSucursal().getCodigo());
/*  979:1219 */       facturaProveedorSRI.setPuntoEmisionRetencion(AppUtil.getPuntoDeVenta().getCodigo());
/*  980:     */     }
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void cargarDetalleRetencion(FacturaProveedorSRI facturaProveedorSRI, ConceptoRetencionSRI conceptoRetencionSRI, BigDecimal baseImponible)
/*  984:     */   {
/*  985:1233 */     if ((conceptoRetencionSRI != null) && (conceptoRetencionSRI.getTipoConceptoRetencion() != null)) {
/*  986:1234 */       cargarDetalleRetencion(facturaProveedorSRI, conceptoRetencionSRI, conceptoRetencionSRI.getTipoConceptoRetencion(), baseImponible);
/*  987:     */     } else {
/*  988:1236 */       cargarDetalleRetencion(facturaProveedorSRI, conceptoRetencionSRI, TipoConceptoRetencion.FUENTE, baseImponible);
/*  989:     */     }
/*  990:     */   }
/*  991:     */   
/*  992:     */   public void cargarDetalleIVARetencion(FacturaProveedorSRI facturaProveedorSRI, ConceptoRetencionSRI conceptoRetencionSRI)
/*  993:     */   {
/*  994:1242 */     cargarDetalleRetencion(facturaProveedorSRI, conceptoRetencionSRI, TipoConceptoRetencion.IVA, null);
/*  995:     */   }
/*  996:     */   
/*  997:     */   private void cargarDetalleRetencion(FacturaProveedorSRI facturaProveedorSRI, ConceptoRetencionSRI conceptoRetencionSRI, TipoConceptoRetencion tipo, BigDecimal baseImponible)
/*  998:     */   {
/*  999:1248 */     DetalleFacturaProveedorSRI detalle = new DetalleFacturaProveedorSRI();
/* 1000:1249 */     detalle.setTipoConceptoRetencion(tipo);
/* 1001:1250 */     if (ParametrosSistema.getTipoAnexoSRI(AppUtil.getOrganizacion().getId()).equals(TipoAnexoSRI.REOC.getNombreAbreviado()))
/* 1002:     */     {
/* 1003:1252 */       detalle.setBaseImponibleDiferenteCero(facturaProveedorSRI.getBaseImponibleDiferenteCero());
/* 1004:1253 */       detalle.setBaseImponibleNoObjetoIva(facturaProveedorSRI.getBaseImponibleNoObjetoIva());
/* 1005:1254 */       detalle.setBaseImponibleTarifaCero(facturaProveedorSRI.getBaseImponibleTarifaCero());
/* 1006:     */       
/* 1007:1256 */       detalle.setBaseImponibleRetencion(BigDecimal.ZERO);
/* 1008:1257 */       detalle.setFacturaProveedorSRI(facturaProveedorSRI);
/* 1009:     */     }
/* 1010:     */     else
/* 1011:     */     {
/* 1012:1259 */       detalle.setBaseImponibleDiferenteCero(BigDecimal.ZERO);
/* 1013:1260 */       detalle.setBaseImponibleNoObjetoIva(BigDecimal.ZERO);
/* 1014:1261 */       detalle.setBaseImponibleTarifaCero(BigDecimal.ZERO);
/* 1015:1263 */       if (tipo.equals(TipoConceptoRetencion.FUENTE))
/* 1016:     */       {
/* 1017:1264 */         if (baseImponible == null) {
/* 1018:1265 */           baseImponible = facturaProveedorSRI.getBaseImponible();
/* 1019:     */         }
/* 1020:1267 */         detalle.setBaseImponibleRetencion(baseImponible);
/* 1021:     */       }
/* 1022:1269 */       if (tipo.equals(TipoConceptoRetencion.IVA))
/* 1023:     */       {
/* 1024:1270 */         if (baseImponible == null) {
/* 1025:1271 */           baseImponible = facturaProveedorSRI.getMontoIva();
/* 1026:     */         }
/* 1027:1273 */         detalle.setBaseImponibleRetencion(baseImponible);
/* 1028:     */       }
/* 1029:1276 */       detalle.setFacturaProveedorSRI(facturaProveedorSRI);
/* 1030:     */     }
/* 1031:1280 */     detalle.setConceptoRetencionSRI(conceptoRetencionSRI);
/* 1032:1281 */     facturaProveedorSRI.getListaDetalleFacturaProveedorSRI().add(detalle);
/* 1033:1282 */     if (conceptoRetencionSRI != null)
/* 1034:     */     {
/* 1035:1283 */       detalle.setPorcentajeRetencion(conceptoRetencionSRI.getPorcentaje());
/* 1036:1284 */       actualizarValorRetencion(detalle);
/* 1037:     */     }
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public String actualizarValorRetencion(DetalleFacturaProveedorSRI detalleFacturaProveedorSRI)
/* 1041:     */   {
/* 1042:1290 */     if (!detalleFacturaProveedorSRI.getConceptoRetencionSRI().isIngresaPorcentaje()) {
/* 1043:1291 */       detalleFacturaProveedorSRI.setPorcentajeRetencion(detalleFacturaProveedorSRI.getConceptoRetencionSRI().getPorcentaje().setScale(2));
/* 1044:     */     }
/* 1045:1294 */     BigDecimal baseImponibleRetencion = BigDecimal.ZERO;
/* 1046:1295 */     if (detalleFacturaProveedorSRI.getConceptoRetencionSRI().getTipoConceptoRetencion().equals(TipoConceptoRetencion.FUENTE)) {
/* 1047:1298 */       baseImponibleRetencion = detalleFacturaProveedorSRI.getBaseImponibleRetencion().add(detalleFacturaProveedorSRI.getBaseImponibleTarifaCero()).add(detalleFacturaProveedorSRI.getBaseImponibleDiferenteCero()).add(detalleFacturaProveedorSRI.getBaseImponibleNoObjetoIva());
/* 1048:     */     }
/* 1049:1300 */     if (detalleFacturaProveedorSRI.getConceptoRetencionSRI().getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA)) {
/* 1050:1301 */       baseImponibleRetencion = detalleFacturaProveedorSRI.getBaseImponibleRetencion();
/* 1051:     */     }
/* 1052:1304 */     BigDecimal valorRetencion = baseImponibleRetencion.multiply(detalleFacturaProveedorSRI.getPorcentajeRetencion().divide(new BigDecimal(100)));
/* 1053:     */     
/* 1054:1306 */     valorRetencion = FuncionesUtiles.redondearBigDecimal(valorRetencion, 2);
/* 1055:     */     
/* 1056:1308 */     detalleFacturaProveedorSRI.setValorRetencion(FuncionesUtiles.redondearBigDecimal(valorRetencion));
/* 1057:     */     
/* 1058:1310 */     return "";
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public List<ReporteRetencionesResumido> getRetencionSRIResumido(int mes, int anio, int idOrganizacion)
/* 1062:     */   {
/* 1063:1320 */     return this.reporteRetencionSRIDao.getRetencionSRIResumido(mes, anio, idOrganizacion);
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public void enviarMail(FacturaProveedorSRI facturaProveedorSRI, String emails)
/* 1067:     */     throws ExcepcionAS2
/* 1068:     */   {
/* 1069:1325 */     enviarMail(facturaProveedorSRI, null, emails);
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public void enviarMail(FacturaProveedorSRI facturaProveedorSRI, DocumentoElectronico documento, String emails)
/* 1073:     */     throws ExcepcionAS2
/* 1074:     */   {
/* 1075:     */     try
/* 1076:     */     {
/* 1077:1332 */       facturaProveedorSRI = cargarDetalle(facturaProveedorSRI.getId());
/* 1078:1333 */       if (documento == null)
/* 1079:     */       {
/* 1080:1334 */         String version = "1.0.0";
/* 1081:     */         
/* 1082:1336 */         int ambiente = facturaProveedorSRI.getAmbiente();
/* 1083:1337 */         int tipoEmision = facturaProveedorSRI.getTipoEmision();
/* 1084:1338 */         TipoDocumentoElectronicoEnum tipoDocumento = TipoDocumentoElectronicoEnum.RETENCION;
/* 1085:     */         
/* 1086:1340 */         Organizacion organizacion = null;
/* 1087:1341 */         int idOrganizacion = facturaProveedorSRI.getIdOrganizacion();
/* 1088:1342 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 1089:1343 */         documento = new DocumentoElectronico(organizacion, ambiente, tipoEmision, tipoDocumento, "1.0.0");
/* 1090:1344 */         documento.prepararParaEnvioEmail(facturaProveedorSRI, emails, facturaProveedorSRI.getDocumento().getReporte());
/* 1091:     */       }
/* 1092:1346 */       documento.setNombreReporte(facturaProveedorSRI.getDocumento().getReporte());
/* 1093:1347 */       if (emails != null) {
/* 1094:1348 */         documento.setEmail(emails);
/* 1095:     */       }
/* 1096:1352 */       List<Object[]> listaDatosReporte = getReporteFacturaProveedorSRI(facturaProveedorSRI.getId());
/* 1097:1353 */       JRDataSource ds = new QueryResultDataSource(listaDatosReporte, ReporteFacturacionSRIBean.fields);
/* 1098:1354 */       documento.setDataSource(ds);
/* 1099:     */       
/* 1100:     */ 
/* 1101:1357 */       Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(facturaProveedorSRI.getIdSucursal()));
/* 1102:1358 */       documento.setTelefonoSucursal(sucursal.getTelefono1().concat(sucursal.getTelefono2() != null ? "   " + sucursal.getTelefono2() : ""));
/* 1103:     */       
/* 1104:1360 */       Empresa empEnviarMail = null;
/* 1105:1361 */       if (facturaProveedorSRI.getFacturaProveedor() != null) {
/* 1106:1362 */         empEnviarMail = facturaProveedorSRI.getFacturaProveedor().getEmpresa();
/* 1107:1363 */       } else if (facturaProveedorSRI.getCompraCajaChica() != null) {
/* 1108:1364 */         empEnviarMail = facturaProveedorSRI.getCompraCajaChica().getEmpresa();
/* 1109:     */       }
/* 1110:1367 */       this.servicioDocumentoElectronico.enviarDocumentoPorEmail(documento, empEnviarMail);
/* 1111:     */     }
/* 1112:     */     catch (Exception e)
/* 1113:     */     {
/* 1114:1369 */       e.printStackTrace();
/* 1115:1370 */       throw new ExcepcionAS2(e.getMessage());
/* 1116:     */     }
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public List<FacturaProveedorSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 1120:     */   {
/* 1121:1379 */     return this.facturaProveedorSRIDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   public int contarPorCriterio(Map<String, String> filters)
/* 1125:     */   {
/* 1126:1384 */     return this.facturaProveedorSRIDao.contarPorCriterio(filters);
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public TipoComprobanteSRI cargarTipoComprobanteSRI(String codigo, int idOrganizacion)
/* 1130:     */   {
/* 1131:1390 */     Map<String, String> filtros = new HashMap();
/* 1132:1391 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 1133:1392 */     filtros.put("codigo", codigo);
/* 1134:     */     
/* 1135:1394 */     return (TipoComprobanteSRI)this.tipoComprobanteSRIDao.obtenerListaCombo("nombre", true, filtros).get(0);
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1139:     */   public List<FacturaProveedorSRI> migracionFacturaProveedorSRI(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial)
/* 1140:     */     throws ExcepcionAS2, AS2Exception
/* 1141:     */   {
/* 1142:1405 */     HashMap<String, Documento> hmDocumento = new HashMap();
/* 1143:1406 */     HashMap<String, ConceptoRetencionSRI> hmConceptoRetencionSRI = new HashMap();
/* 1144:1407 */     HashMap<String, FacturaProveedorSRI> hmFacturaProveedorSRI = new HashMap();
/* 1145:1408 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/* 1146:     */     
/* 1147:1410 */     FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/* 1148:     */     
/* 1149:1412 */     List<FacturaProveedorSRI> listaFacturaProveedorSRI = new ArrayList();
/* 1150:1413 */     AS2Exception excepcionFacturasProveedorSRI = null;
/* 1151:     */     
/* 1152:1415 */     int filaActual = filaInicial;
/* 1153:1416 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1154:1417 */     int columnaErronea = 0;
/* 1155:     */     try
/* 1156:     */     {
/* 1157:1421 */       datos = FuncionesUtiles.leerExcelFinal(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 1158:     */     }
/* 1159:     */     catch (IOException ioe)
/* 1160:     */     {
/* 1161:     */       HSSFCell[][] datos;
/* 1162:1423 */       throw new ExcepcionAS2(ioe);
/* 1163:     */     }
/* 1164:     */     HSSFCell[][] datos;
/* 1165:1426 */     for (HSSFCell[] fila : datos)
/* 1166:     */     {
/* 1167:     */       try
/* 1168:     */       {
/* 1169:1428 */         filaErronea = fila;
/* 1170:1429 */         filaActual++;
/* 1171:     */         
/* 1172:     */ 
/* 1173:1432 */         String nombreDocumento = fila[(columnaErronea = 0)].getStringCellValue().trim();
/* 1174:1433 */         String identificacionProveedor = fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 1175:     */         
/* 1176:1435 */         String numero = fila[(columnaErronea = 2)].getStringCellValue().trim();
/* 1177:1436 */         String establecimiento = numero.substring(0, 3);
/* 1178:1437 */         String puntoEmision = numero.substring(3, 6);
/* 1179:1438 */         String secuencia = numero.substring(6);
/* 1180:     */         
/* 1181:1440 */         Date fechaEmision = fila[(columnaErronea = 3)].getDateCellValue();
/* 1182:     */         
/* 1183:1442 */         String numeroComprobante = fila[(columnaErronea = 4)].getStringCellValue().trim();
/* 1184:1443 */         boolean facturaElectronica = false;
/* 1185:1444 */         if (numeroComprobante.length() == 37) {
/* 1186:1445 */           facturaElectronica = true;
/* 1187:     */         }
/* 1188:1447 */         BigDecimal baseImponibleTarifaCero = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 5)]
/* 1189:1448 */           .getNumericCellValue()), 2);
/* 1190:1449 */         BigDecimal baseImponibleDiferenteCero = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 6)]
/* 1191:1450 */           .getNumericCellValue()), 2);
/* 1192:1451 */         BigDecimal baseImponibleNoObjetoIva = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 7)]
/* 1193:1452 */           .getNumericCellValue()), 2);
/* 1194:1453 */         BigDecimal montoIva = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 8)].getNumericCellValue()), 2);
/* 1195:1454 */         BigDecimal montoIce = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 9)].getNumericCellValue()), 2);
/* 1196:     */         
/* 1197:1456 */         String numeroRetencion = fila[(columnaErronea = 10)].getStringCellValue().trim();
/* 1198:1457 */         String establecimientoRetencion = numeroRetencion.substring(0, 3);
/* 1199:1458 */         String puntoEmisionRetencion = numeroRetencion.substring(3, 6);
/* 1200:1459 */         String secuenciaRetencion = numeroRetencion.substring(6);
/* 1201:     */         
/* 1202:1461 */         Date fechaEmisionRetencion = fila[(columnaErronea = 11)].getDateCellValue();
/* 1203:1462 */         BigDecimal valorRetenidoBienes = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 12)].getNumericCellValue()), 2);
/* 1204:     */         
/* 1205:1464 */         BigDecimal valorRetenidoServicios = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 13)]
/* 1206:1465 */           .getNumericCellValue()), 2);
/* 1207:1466 */         BigDecimal valorRetenidoBienesServicios = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 14)]
/* 1208:1467 */           .getNumericCellValue()), 2);
/* 1209:1468 */         String email = fila[(columnaErronea = 15)].getStringCellValue().trim();
/* 1210:1469 */         String codigoConceptoRetencion = fila[(columnaErronea = 16)].getStringCellValue().trim();
/* 1211:1470 */         BigDecimal porcentajeRetencion = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 17)].getNumericCellValue()), 2);
/* 1212:     */         
/* 1213:1472 */         BigDecimal baseImponibleTarifaCeroDetalle = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 18)]
/* 1214:1473 */           .getNumericCellValue()), 2);
/* 1215:1474 */         BigDecimal baseImponibleDiferenteCeroDetalle = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 19)]
/* 1216:1475 */           .getNumericCellValue()), 2);
/* 1217:1476 */         BigDecimal baseImponibleNoObjetoIvaDetalle = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 20)]
/* 1218:1477 */           .getNumericCellValue()), 2);
/* 1219:     */         
/* 1220:1479 */         columnaErronea = -1;
/* 1221:     */         
/* 1222:     */ 
/* 1223:     */ 
/* 1224:     */ 
/* 1225:1484 */         StringBuilder clave = new StringBuilder();
/* 1226:1485 */         clave.append(identificacionProveedor);
/* 1227:     */         
/* 1228:     */ 
/* 1229:     */ 
/* 1230:     */ 
/* 1231:1490 */         Empresa empresa = (Empresa)hmEmpresa.get(identificacionProveedor);
/* 1232:1491 */         if (empresa == null)
/* 1233:     */         {
/* 1234:1492 */           Map<String, String> filters = new HashMap();
/* 1235:1493 */           filters.put("identificacion", identificacionProveedor);
/* 1236:1494 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 1237:1495 */           empresa = this.servicioEmpresa.bucarEmpresaPorIdentificacion(filters);
/* 1238:1496 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 1239:1497 */           hmEmpresa.put(identificacionProveedor, empresa);
/* 1240:     */         }
/* 1241:1503 */         Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/* 1242:1504 */         if (documento == null)
/* 1243:     */         {
/* 1244:1505 */           HashMap<String, String> filters = new HashMap();
/* 1245:1506 */           filters.put("nombre", nombreDocumento);
/* 1246:1507 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 1247:1508 */           documento = (Documento)this.servicioDocumento.obtenerListaCombo("", false, filters).get(0);
/* 1248:1509 */           hmDocumento.put(nombreDocumento, documento);
/* 1249:     */         }
/* 1250:1515 */         facturaProveedorSRI = (FacturaProveedorSRI)hmFacturaProveedorSRI.get(clave.toString());
/* 1251:1516 */         if (facturaProveedorSRI == null)
/* 1252:     */         {
/* 1253:1517 */           facturaProveedorSRI = new FacturaProveedorSRI();
/* 1254:1518 */           facturaProveedorSRI.setIdOrganizacion(idOrganizacion);
/* 1255:1519 */           facturaProveedorSRI.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1256:1520 */           facturaProveedorSRI.setTipoEmpresa(empresa.getTipoEmpresa());
/* 1257:1521 */           facturaProveedorSRI.setTipoIdentificacion(empresa.getTipoIdentificacion());
/* 1258:1522 */           facturaProveedorSRI.setNombreProveedor(empresa.getNombreComercial());
/* 1259:1523 */           facturaProveedorSRI.setTipoComprobanteSRI(this.servicioFacturaProveedorSRI.cargarTipoComprobanteSRI("01", AppUtil.getOrganizacion()
/* 1260:1524 */             .getId()));
/* 1261:1525 */           facturaProveedorSRI.setFechaRegistro(new Date());
/* 1262:1526 */           facturaProveedorSRI.setEstado(Estado.PROCESADO);
/* 1263:1527 */           facturaProveedorSRI.setPuntoEmisionRetencion(AppUtil.getPuntoDeVenta().getCodigo());
/* 1264:1528 */           facturaProveedorSRI.setEstablecimientoRetencion(AppUtil.getSucursal().getCodigo());
/* 1265:1529 */           facturaProveedorSRI.setDocumento(documento);
/* 1266:1530 */           facturaProveedorSRI.setIdentificacionProveedor(identificacionProveedor);
/* 1267:1531 */           facturaProveedorSRI.setPuntoEmision(puntoEmision);
/* 1268:1532 */           facturaProveedorSRI.setEstablecimiento(establecimiento);
/* 1269:1533 */           facturaProveedorSRI.setNumero(secuencia);
/* 1270:     */           
/* 1271:1535 */           facturaProveedorSRI.setFechaEmision(new Date());
/* 1272:1536 */           facturaProveedorSRI.setAutorizacion(numeroComprobante);
/* 1273:1537 */           facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/* 1274:1538 */           facturaProveedorSRI.setBaseImponibleTarifaCero(baseImponibleTarifaCero);
/* 1275:1539 */           facturaProveedorSRI.setBaseImponibleDiferenteCero(baseImponibleDiferenteCero);
/* 1276:1540 */           facturaProveedorSRI.setBaseImponibleNoObjetoIva(baseImponibleNoObjetoIva);
/* 1277:1541 */           facturaProveedorSRI.setMontoIva(montoIva);
/* 1278:1542 */           facturaProveedorSRI.setMontoIce(montoIce);
/* 1279:1543 */           facturaProveedorSRI.setNumeroRetencion(secuenciaRetencion);
/* 1280:     */           
/* 1281:1545 */           facturaProveedorSRI.setFechaEmisionRetencion(new Date());
/* 1282:1546 */           facturaProveedorSRI.setEmail(email);
/* 1283:     */         }
/* 1284:1552 */         ConceptoRetencionSRI conceptoRetencionSRI = (ConceptoRetencionSRI)hmConceptoRetencionSRI.get(codigoConceptoRetencion);
/* 1285:1553 */         if (conceptoRetencionSRI == null)
/* 1286:     */         {
/* 1287:1554 */           HashMap<String, String> filters = new HashMap();
/* 1288:1555 */           filters.put("codigo", codigoConceptoRetencion);
/* 1289:1556 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 1290:1557 */           conceptoRetencionSRI = (ConceptoRetencionSRI)this.servicioConceptoRetencionSRI.obtenerListaCombo("", false, filters).get(0);
/* 1291:1558 */           hmConceptoRetencionSRI.put(codigoConceptoRetencion, conceptoRetencionSRI);
/* 1292:     */         }
/* 1293:1564 */         DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = new DetalleFacturaProveedorSRI();
/* 1294:1565 */         detalleFacturaProveedorSRI.setBaseImponibleTarifaCero(baseImponibleTarifaCero);
/* 1295:1566 */         detalleFacturaProveedorSRI.setBaseImponibleDiferenteCero(baseImponibleDiferenteCero);
/* 1296:1567 */         detalleFacturaProveedorSRI.setBaseImponibleNoObjetoIva(baseImponibleNoObjetoIva);
/* 1297:1568 */         detalleFacturaProveedorSRI.setConceptoRetencionSRI(conceptoRetencionSRI);
/* 1298:1569 */         detalleFacturaProveedorSRI.setPorcentajeRetencion(porcentajeRetencion);
/* 1299:1570 */         detalleFacturaProveedorSRI.setBaseImponibleTarifaCero(baseImponibleTarifaCeroDetalle);
/* 1300:1571 */         detalleFacturaProveedorSRI.setBaseImponibleDiferenteCero(baseImponibleDiferenteCeroDetalle);
/* 1301:1572 */         detalleFacturaProveedorSRI.setBaseImponibleNoObjetoIva(baseImponibleNoObjetoIvaDetalle);
/* 1302:1573 */         detalleFacturaProveedorSRI.getConceptoRetencionSRI().setIngresaPorcentaje(true);
/* 1303:1574 */         this.servicioFacturaProveedorSRI.actualizarValorRetencion(detalleFacturaProveedorSRI);
/* 1304:1575 */         detalleFacturaProveedorSRI.setFacturaProveedorSRI(facturaProveedorSRI);
/* 1305:     */         
/* 1306:1577 */         facturaProveedorSRI.getListaDetalleFacturaProveedorSRI().add(detalleFacturaProveedorSRI);
/* 1307:     */         
/* 1308:1579 */         hmFacturaProveedorSRI.put(clave.toString(), facturaProveedorSRI);
/* 1309:     */       }
/* 1310:     */       catch (IllegalStateException e)
/* 1311:     */       {
/* 1312:1582 */         LOG.error("Error al migrar Factura Proveedor SRI", e);
/* 1313:1583 */         if (excepcionFacturasProveedorSRI == null) {
/* 1314:1584 */           excepcionFacturasProveedorSRI = new AS2Exception();
/* 1315:     */         }
/* 1316:1586 */         excepcionFacturasProveedorSRI.getMensajes().add(e.toString());
/* 1317:     */       }
/* 1318:     */       catch (IllegalArgumentException e)
/* 1319:     */       {
/* 1320:1588 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1321:1589 */         if (excepcionFacturasProveedorSRI == null) {
/* 1322:1590 */           excepcionFacturasProveedorSRI = new AS2Exception();
/* 1323:     */         }
/* 1324:1592 */         excepcionFacturasProveedorSRI.getMensajes().add(e.toString());
/* 1325:     */       }
/* 1326:     */       catch (ExcepcionAS2Financiero e)
/* 1327:     */       {
/* 1328:1594 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1329:1595 */         LOG.error("Error al migrar Factura Proveedor SRI", e);
/* 1330:1596 */         if (excepcionFacturasProveedorSRI == null) {
/* 1331:1597 */           excepcionFacturasProveedorSRI = new AS2Exception();
/* 1332:     */         }
/* 1333:1599 */         excepcionFacturasProveedorSRI.getCodigoMensajes().add(e.getCodigoExcepcion() + "*" + e.getMessage());
/* 1334:     */       }
/* 1335:     */       catch (ExcepcionAS2Compras e)
/* 1336:     */       {
/* 1337:1601 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1338:1602 */         LOG.error("Error al migrar Factura Proveedor SRI", e);
/* 1339:1603 */         if (excepcionFacturasProveedorSRI == null) {
/* 1340:1604 */           excepcionFacturasProveedorSRI = new AS2Exception();
/* 1341:     */         }
/* 1342:1606 */         excepcionFacturasProveedorSRI.getCodigoMensajes().add(e.getCodigoExcepcion() + "*" + e.getMessage());
/* 1343:     */       }
/* 1344:     */       catch (ExcepcionAS2 e)
/* 1345:     */       {
/* 1346:1608 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1347:1609 */         LOG.error("Error al migrar Factura Proveedor SRI", e);
/* 1348:1610 */         if (excepcionFacturasProveedorSRI == null) {
/* 1349:1611 */           excepcionFacturasProveedorSRI = new AS2Exception();
/* 1350:     */         }
/* 1351:1613 */         excepcionFacturasProveedorSRI.getCodigoMensajes().add(e.getCodigoExcepcion() + "*" + e.getMessage());
/* 1352:     */       }
/* 1353:     */       catch (Exception e)
/* 1354:     */       {
/* 1355:1615 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1356:1616 */         LOG.error("Error al migrar Factura Proveedor SRI", e);
/* 1357:1617 */         if (excepcionFacturasProveedorSRI == null) {
/* 1358:1618 */           excepcionFacturasProveedorSRI = new AS2Exception();
/* 1359:     */         }
/* 1360:1620 */         excepcionFacturasProveedorSRI.getMensajes().add(e.toString() + " Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1361:     */       }
/* 1362:1623 */       if ((excepcionFacturasProveedorSRI != null) && (
/* 1363:1624 */         (!excepcionFacturasProveedorSRI.getMensajes().isEmpty()) || (!excepcionFacturasProveedorSRI.getCodigoMensajes().isEmpty()))) {
/* 1364:1625 */         throw excepcionFacturasProveedorSRI;
/* 1365:     */       }
/* 1366:     */     }
/* 1367:1629 */     for (FacturaProveedorSRI fpSRI : hmFacturaProveedorSRI.values()) {
/* 1368:1630 */       listaFacturaProveedorSRI.add(fpSRI);
/* 1369:     */     }
/* 1370:1633 */     return listaFacturaProveedorSRI;
/* 1371:     */   }
/* 1372:     */   
/* 1373:     */   public List<ReembolsoGastos> listaReembolsoGastos(FacturaProveedorSRI facturaProveedorSRI)
/* 1374:     */   {
/* 1375:1638 */     return this.facturaProveedorSRIDao.listaReembolsoGastos(facturaProveedorSRI);
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   public void actualizarTipoComprobanteSRI(FacturaProveedorSRI facturaProveedorSRI, TipoComprobanteSRI tipoComprobanteSRI, Date fecha)
/* 1379:     */     throws ExcepcionAS2
/* 1380:     */   {
/* 1381:1648 */     tipoComprobanteSRI = this.servicioSRI.buscarTipoComprobanteSRIPorId(tipoComprobanteSRI.getId());
/* 1382:1649 */     facturaProveedorSRI.setTipoComprobanteSRI(tipoComprobanteSRI);
/* 1383:1651 */     if (((!facturaProveedorSRI.getTipoIdentificacion().isIndicadorValidarIdentificacion()) || 
/* 1384:1652 */       (facturaProveedorSRI.getTipoIdentificacion().getLongitudMaxima() != 13)) && 
/* 1385:1653 */       (facturaProveedorSRI.getTipoComprobanteSRI() != null) && 
/* 1386:1654 */       (facturaProveedorSRI.isIndicadorLiquidacionCompra()))
/* 1387:     */     {
/* 1388:1655 */       ArrayList<Documento> listaDocumento = (ArrayList)this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.LIQUIDACION_COMPRA);
/* 1389:1656 */       if (listaDocumento.size() > 0)
/* 1390:     */       {
/* 1391:1657 */         Documento documento = (Documento)listaDocumento.get(0);
/* 1392:1658 */         this.servicioDocumento.cargarDocumentoConAutorizacion(documento, AppUtil.getPuntoDeVenta(), facturaProveedorSRI.getFechaEmision());
/* 1393:1659 */         facturaProveedorSRI.setSecuenciaLiquidacionCompra(documento.getSecuencia());
/* 1394:1661 */         if ((facturaProveedorSRI.getNumero() == null) || (facturaProveedorSRI.getNumero().equals("0")))
/* 1395:     */         {
/* 1396:1662 */           this.servicioSecuencia.detach(documento.getSecuencia());
/* 1397:1663 */           String numero = this.servicioSecuencia.obtenerSecuencia(documento.getSecuencia(), fecha);
/* 1398:1664 */           facturaProveedorSRI.setNumero(numero);
/* 1399:     */         }
/* 1400:     */       }
/* 1401:     */       else
/* 1402:     */       {
/* 1403:1667 */         throw new ExcepcionAS2("msg_mensaje_no_documento_liquidacion_compra");
/* 1404:     */       }
/* 1405:     */     }
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   public CreditoTributarioSRI getCreditoTributarioSRI(FacturaProveedor fp)
/* 1409:     */   {
/* 1410:1674 */     return this.facturaProveedorSRIDao.getCreditoTributarioSRI(fp);
/* 1411:     */   }
/* 1412:     */   
/* 1413:     */   public List<Object[]> getMontoBasePorTipoProducto(FacturaProveedor fp)
/* 1414:     */   {
/* 1415:1679 */     return this.facturaProveedorSRIDao.getMontoBasePorTipoProducto(fp);
/* 1416:     */   }
/* 1417:     */   
/* 1418:     */   public List<FacturaProveedorSRI> obtenerRetencionesProveedor(int idOrganizacion, Date fechaDesde, Date fechaHasta, DocumentoBase documentoBase, int idEmpresa)
/* 1419:     */   {
/* 1420:1684 */     return this.facturaProveedorSRIDao.obtenerRetencionesProveedor(idOrganizacion, fechaDesde, fechaHasta, documentoBase, idEmpresa);
/* 1421:     */   }
/* 1422:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.impl.ServicioFacturaProveedorSRIImpl
 * JD-Core Version:    0.7.0.1
 */