/*    1:     */ package com.asinfo.as2.compronteselectronicos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*    4:     */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaClienteSRIXML;
/*    5:     */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*    6:     */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*    7:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    9:     */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   10:     */ import com.asinfo.as2.dao.OrganizacionConfiguracionDao;
/*   11:     */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*   12:     */ import com.asinfo.as2.dao.sri.FacturaClienteSRIDao;
/*   13:     */ import com.asinfo.as2.entities.Ciudad;
/*   14:     */ import com.asinfo.as2.entities.Cliente;
/*   15:     */ import com.asinfo.as2.entities.CondicionPago;
/*   16:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   17:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   18:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   19:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   20:     */ import com.asinfo.as2.entities.Documento;
/*   21:     */ import com.asinfo.as2.entities.Empresa;
/*   22:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   23:     */ import com.asinfo.as2.entities.Impuesto;
/*   24:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   25:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   26:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   27:     */ import com.asinfo.as2.entities.Lote;
/*   28:     */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   29:     */ import com.asinfo.as2.entities.Organizacion;
/*   30:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   31:     */ import com.asinfo.as2.entities.Pais;
/*   32:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   33:     */ import com.asinfo.as2.entities.PresentacionProducto;
/*   34:     */ import com.asinfo.as2.entities.Producto;
/*   35:     */ import com.asinfo.as2.entities.Sucursal;
/*   36:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   37:     */ import com.asinfo.as2.entities.Ubicacion;
/*   38:     */ import com.asinfo.as2.entities.Unidad;
/*   39:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   40:     */ import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
/*   41:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   42:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   43:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   44:     */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*   45:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   46:     */ import com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico;
/*   47:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   48:     */ import com.asinfo.as2.util.AppUtil;
/*   49:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   50:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   51:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   52:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   53:     */ import com.asinfo.as2.xml.jaxb.sri.CampoAdicionalJaxb;
/*   54:     */ import com.asinfo.as2.xml.jaxb.sri.CompensacionJaxb;
/*   55:     */ import com.asinfo.as2.xml.jaxb.sri.CompensacionesJaxb;
/*   56:     */ import com.asinfo.as2.xml.jaxb.sri.ComprobanteVentaJaxb;
/*   57:     */ import com.asinfo.as2.xml.jaxb.sri.DetAdicionalJaxb;
/*   58:     */ import com.asinfo.as2.xml.jaxb.sri.DetalleAdicionalJaxb;
/*   59:     */ import com.asinfo.as2.xml.jaxb.sri.DetalleJaxb;
/*   60:     */ import com.asinfo.as2.xml.jaxb.sri.DetallesJaxb;
/*   61:     */ import com.asinfo.as2.xml.jaxb.sri.FacturaClienteJaxb;
/*   62:     */ import com.asinfo.as2.xml.jaxb.sri.ImpuestoJaxb;
/*   63:     */ import com.asinfo.as2.xml.jaxb.sri.ImpuestosJaxb;
/*   64:     */ import com.asinfo.as2.xml.jaxb.sri.InfoAdicionalJaxb;
/*   65:     */ import com.asinfo.as2.xml.jaxb.sri.InfoFacturaJaxb;
/*   66:     */ import com.asinfo.as2.xml.jaxb.sri.InfoNCreditoJaxb;
/*   67:     */ import com.asinfo.as2.xml.jaxb.sri.InfoNDebitoJaxb;
/*   68:     */ import com.asinfo.as2.xml.jaxb.sri.InfoTributariaJaxb;
/*   69:     */ import com.asinfo.as2.xml.jaxb.sri.MotivoJaxb;
/*   70:     */ import com.asinfo.as2.xml.jaxb.sri.MotivosJaxb;
/*   71:     */ import com.asinfo.as2.xml.jaxb.sri.NotaCreditoClienteJaxb;
/*   72:     */ import com.asinfo.as2.xml.jaxb.sri.NotaDebitoClienteJaxb;
/*   73:     */ import com.asinfo.as2.xml.jaxb.sri.PagoJaxb;
/*   74:     */ import com.asinfo.as2.xml.jaxb.sri.PagosJaxb;
/*   75:     */ import com.asinfo.as2.xml.jaxb.sri.TotalConImpuestosJaxb;
/*   76:     */ import java.io.File;
/*   77:     */ import java.io.PrintStream;
/*   78:     */ import java.math.BigDecimal;
/*   79:     */ import java.math.RoundingMode;
/*   80:     */ import java.text.SimpleDateFormat;
/*   81:     */ import java.util.ArrayList;
/*   82:     */ import java.util.Date;
/*   83:     */ import java.util.HashMap;
/*   84:     */ import java.util.Iterator;
/*   85:     */ import java.util.List;
/*   86:     */ import java.util.Map;
/*   87:     */ import java.util.Set;
/*   88:     */ import javax.ejb.EJB;
/*   89:     */ import javax.ejb.Stateless;
/*   90:     */ import javax.xml.bind.JAXBContext;
/*   91:     */ import javax.xml.bind.Marshaller;
/*   92:     */ 
/*   93:     */ @Stateless
/*   94:     */ public class ServicioFacturaClienteSRIXMLImpl
/*   95:     */   implements ServicioFacturaClienteSRIXML
/*   96:     */ {
/*   97:     */   @EJB
/*   98:     */   private ServicioSecuenciaDocumentoElectronico servicioSecuenciaDocumentoElectronico;
/*   99:     */   @EJB
/*  100:     */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  101:     */   @EJB
/*  102:     */   private ServicioFacturaCliente servicioFacturaCliente;
/*  103:     */   @EJB
/*  104:     */   private FacturaClienteDao facturaClienteDao;
/*  105:     */   @EJB
/*  106:     */   private FacturaClienteSRIDao facturaClienteSRIDao;
/*  107:     */   @EJB
/*  108:     */   private ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  109:     */   @EJB
/*  110:     */   private ServicioOrganizacion servicioOrganizacion;
/*  111:     */   @EJB
/*  112:     */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  113:     */   @EJB
/*  114:     */   private OrganizacionConfiguracionDao organizacionConfiguracionDao;
/*  115: 109 */   private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
/*  116: 112 */   private BigDecimal impuestoCampesino = BigDecimal.ZERO;
/*  117:     */   
/*  118:     */   public FacturaCliente generarClaveAcceso(DocumentoElectronico documento, FacturaCliente facturaCliente, boolean indicadorGenerarXML)
/*  119:     */     throws AS2Exception
/*  120:     */   {
/*  121: 117 */     if (documento == null)
/*  122:     */     {
/*  123: 118 */       String version = "1.0.0";
/*  124: 119 */       if ((facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.NOTA_CREDITO_CLIENTE)) || 
/*  125: 120 */         (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_CLIENTE)) || 
/*  126: 121 */         (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.FACTURA_CLIENTE))) {
/*  127: 122 */         version = facturaCliente.getEmpresa().getCliente().getVersion();
/*  128:     */       }
/*  129: 126 */       int ambiente = facturaCliente.getFacturaClienteSRI().getAmbiente();
/*  130: 127 */       int tipoEmision = facturaCliente.getFacturaClienteSRI().getTipoEmision();
/*  131:     */       
/*  132: 129 */       TipoDocumentoElectronicoEnum tipoDocumento = facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_DEBITO_CLIENTE ? TipoDocumentoElectronicoEnum.NOTA_DEBITO : facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.FACTURA_CLIENTE ? TipoDocumentoElectronicoEnum.FACTURA : TipoDocumentoElectronicoEnum.NOTA_CREDITO;
/*  133:     */       
/*  134:     */ 
/*  135: 132 */       facturaCliente.getFacturaClienteSRI().setIndicadorDocumentoElectronico(true);
/*  136:     */       
/*  137: 134 */       Organizacion organizacion = AppUtil.getOrganizacion();
/*  138: 135 */       if (organizacion == null)
/*  139:     */       {
/*  140: 136 */         int idOrganizacion = facturaCliente.getIdOrganizacion();
/*  141: 137 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/*  142:     */       }
/*  143: 139 */       documento = new DocumentoElectronico(organizacion, ambiente, tipoEmision, tipoDocumento, version);
/*  144: 140 */       facturaCliente.setDocumentoElectronico(documento);
/*  145: 141 */       documento.setDireccionMatriz(facturaCliente.getFacturaClienteSRI().getDireccionMatriz());
/*  146: 142 */       documento.setDireccionSucursal(facturaCliente.getFacturaClienteSRI().getDireccionSucursal());
/*  147: 143 */       documento.setEmail(facturaCliente.getEmail());
/*  148:     */     }
/*  149: 145 */     if (facturaCliente.getFacturaClienteSRI().getCodigoUnico() == null)
/*  150:     */     {
/*  151: 146 */       String codigoUnico = this.servicioSecuenciaDocumentoElectronico.generarSecuenciaDocumento(8);
/*  152: 147 */       documento.setCodigoUnico(codigoUnico);
/*  153: 148 */       facturaCliente.getFacturaClienteSRI().setCodigoUnico(codigoUnico);
/*  154:     */     }
/*  155:     */     else
/*  156:     */     {
/*  157: 151 */       String codigoUnico = facturaCliente.getFacturaClienteSRI().getCodigoUnico();
/*  158: 152 */       documento.setCodigoUnico(codigoUnico);
/*  159:     */     }
/*  160: 155 */     documento.init(facturaCliente);
/*  161: 156 */     if ((documento.getOrganizacion().getOrganizacionConfiguracion().getPkcs12Password() == null) || 
/*  162: 157 */       (documento.getOrganizacion().getOrganizacionConfiguracion().getPkcs12Password().trim().isEmpty()))
/*  163:     */     {
/*  164: 158 */       documento.getOrganizacion().getOrganizacionConfiguracion().setPkcs12Password(documento.getPkcs12_password());
/*  165: 159 */       this.organizacionConfiguracionDao.guardar(documento.getOrganizacion().getOrganizacionConfiguracion());
/*  166:     */     }
/*  167: 162 */     facturaCliente.getFacturaClienteSRI().setClaveAcceso(documento.getClaveAcceso());
/*  168: 163 */     facturaCliente.getFacturaClienteSRI().setAutorizacion(documento.getClaveAcceso());
/*  169: 164 */     facturaCliente.getFacturaClienteSRI().setFechaAutorizacion(null);
/*  170: 165 */     this.facturaClienteSRIDao.guardar(facturaCliente.getFacturaClienteSRI());
/*  171: 166 */     this.facturaClienteSRIDao.flush();
/*  172: 167 */     this.facturaClienteSRIDao.detach(facturaCliente.getFacturaClienteSRI());
/*  173: 168 */     this.facturaClienteDao.detach(facturaCliente);
/*  174: 170 */     if (indicadorGenerarXML)
/*  175:     */     {
/*  176:     */       try
/*  177:     */       {
/*  178: 172 */         generarXML(documento, facturaCliente);
/*  179:     */       }
/*  180:     */       catch (Exception e)
/*  181:     */       {
/*  182: 174 */         e.printStackTrace();
/*  183:     */       }
/*  184: 178 */       Map<String, String> filtros = new HashMap();
/*  185: 179 */       filtros.put("idFacturaCliente", facturaCliente.getIdFacturaCliente() + "");
/*  186: 180 */       List<ComprobanteElectronicoPendienteSRI> listaComprobante = this.comprobanteElectronicoPendienteSRIDao.obtenerListaCombo("idFacturaCliente", true, filtros);
/*  187:     */       
/*  188: 182 */       ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI = null;
/*  189: 183 */       if (listaComprobante.isEmpty())
/*  190:     */       {
/*  191: 184 */         comprobanteElectronicoPendienteSRI = new ComprobanteElectronicoPendienteSRI();
/*  192: 185 */         comprobanteElectronicoPendienteSRI.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/*  193: 186 */         comprobanteElectronicoPendienteSRI.setIdFacturaCliente(Integer.valueOf(facturaCliente.getId()));
/*  194:     */       }
/*  195:     */       else
/*  196:     */       {
/*  197: 188 */         comprobanteElectronicoPendienteSRI = (ComprobanteElectronicoPendienteSRI)listaComprobante.get(0);
/*  198:     */       }
/*  199: 190 */       comprobanteElectronicoPendienteSRI.setAmbiente(documento.getAmbiente());
/*  200: 191 */       comprobanteElectronicoPendienteSRI.setClaveAcceso(documento.getClaveAcceso());
/*  201: 192 */       comprobanteElectronicoPendienteSRI.setDocumentoBase(facturaCliente.getDocumento().getDocumentoBase());
/*  202: 193 */       comprobanteElectronicoPendienteSRI.setEmails(facturaCliente.getEmail());
/*  203: 194 */       System.out.println("emailssss: " + comprobanteElectronicoPendienteSRI.getEmails());
/*  204: 195 */       comprobanteElectronicoPendienteSRI.setFechaEmision(facturaCliente.getFecha());
/*  205: 196 */       comprobanteElectronicoPendienteSRI.setIdSucursal(facturaCliente.getSucursal().getId());
/*  206: 197 */       comprobanteElectronicoPendienteSRI.setNumero(facturaCliente.getNumero());
/*  207: 198 */       comprobanteElectronicoPendienteSRI.setIndicadorNoEnviado(true);
/*  208: 199 */       comprobanteElectronicoPendienteSRI.setIndicadorComprobarAutorizacion(false);
/*  209: 200 */       comprobanteElectronicoPendienteSRI.setIndicadorRechazado(false);
/*  210: 201 */       comprobanteElectronicoPendienteSRI.setTipoDocumento(documento.getTipoDocumento());
/*  211: 202 */       comprobanteElectronicoPendienteSRI.setTipoEmision(documento.getTipoEmision());
/*  212: 203 */       this.comprobanteElectronicoPendienteSRIDao.guardar(comprobanteElectronicoPendienteSRI);
/*  213:     */     }
/*  214: 206 */     return facturaCliente;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public DocumentoElectronico autorizarFactura(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/*  218:     */     throws AS2Exception, ExcepcionAS2Ventas
/*  219:     */   {
/*  220: 212 */     Organizacion organizacion = null;
/*  221: 213 */     if (organizacion == null)
/*  222:     */     {
/*  223: 214 */       int idOrganizacion = comprobanteElectronicoPendienteSRI.getIdOrganizacion();
/*  224: 215 */       organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/*  225:     */     }
/*  226: 217 */     DocumentoElectronico documento = new DocumentoElectronico(organizacion, comprobanteElectronicoPendienteSRI);
/*  227:     */     
/*  228:     */ 
/*  229: 220 */     File ficheroFirmado = new File(documento.getPathArchivoFirmado());
/*  230: 221 */     if (!ficheroFirmado.exists())
/*  231:     */     {
/*  232: 222 */       documento = null;
/*  233: 223 */       FacturaCliente facturaCliente = this.servicioFacturaCliente.cargarDetalle(comprobanteElectronicoPendienteSRI.getIdFacturaCliente().intValue());
/*  234: 224 */       facturaCliente = generarClaveAcceso(documento, facturaCliente, false);
/*  235: 225 */       documento = facturaCliente.getDocumentoElectronico();
/*  236:     */       
/*  237:     */ 
/*  238: 228 */       generarXML(documento, facturaCliente);
/*  239:     */     }
/*  240:     */     try
/*  241:     */     {
/*  242: 233 */       this.servicioDocumentoElectronico.autorizarDocumento(documento);
/*  243:     */     }
/*  244:     */     catch (AS2Exception e)
/*  245:     */     {
/*  246: 235 */       documento.setMensajeSRI(e.getMensaje());
/*  247:     */     }
/*  248: 237 */     return documento;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public DocumentoElectronico comprobarAutorizacion(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/*  252:     */     throws ExcepcionAS2DocumentoElectronico
/*  253:     */   {
/*  254: 243 */     DocumentoElectronico documento = null;
/*  255:     */     try
/*  256:     */     {
/*  257: 245 */       Organizacion organizacion = null;
/*  258: 246 */       if (organizacion == null)
/*  259:     */       {
/*  260: 247 */         int idOrganizacion = comprobanteElectronicoPendienteSRI.getIdOrganizacion();
/*  261: 248 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/*  262:     */       }
/*  263: 250 */       documento = new DocumentoElectronico(organizacion, comprobanteElectronicoPendienteSRI);
/*  264:     */       
/*  265: 252 */       this.servicioDocumentoElectronico.comprobarAutorizacionDocumento(documento);
/*  266:     */     }
/*  267:     */     catch (AS2Exception e)
/*  268:     */     {
/*  269: 254 */       documento.setMensajeSRI(e.getMensaje());
/*  270:     */     }
/*  271:     */     catch (Exception e)
/*  272:     */     {
/*  273: 256 */       e.printStackTrace();
/*  274: 257 */       throw new ExcepcionAS2DocumentoElectronico("msgs_error_autorizacion_documento_electronico", e.getMessage());
/*  275:     */     }
/*  276: 259 */     return documento;
/*  277:     */   }
/*  278:     */   
/*  279:     */   public void generarXML(DocumentoElectronico documento, FacturaCliente facturaCliente)
/*  280:     */     throws AS2Exception
/*  281:     */   {
/*  282: 264 */     String mensajeSRI = "";
/*  283: 265 */     this.impuestoCampesino = BigDecimal.ZERO;
/*  284: 266 */     EstadoDocumentoElectronico estadoSRI = null;
/*  285:     */     try
/*  286:     */     {
/*  287: 268 */       FacturaClienteSRI facturaClienteSRI = facturaCliente.getFacturaClienteSRI();
/*  288: 269 */       String numeroComprobante = FuncionesUtiles.completarALaIzquierda('0', 9, facturaClienteSRI.getNumero());
/*  289:     */       
/*  290: 271 */       ComprobanteVentaJaxb comprobanteVentaJaxb = null;
/*  291: 272 */       InfoAdicionalJaxb infoAdicionalJaxb = new InfoAdicionalJaxb();
/*  292:     */       
/*  293: 274 */       JAXBContext context = null;
/*  294: 276 */       if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA))
/*  295:     */       {
/*  296: 277 */         context = JAXBContext.newInstance(new Class[] { FacturaClienteJaxb.class });
/*  297: 278 */         comprobanteVentaJaxb = crearFacturaClienteJaxb(documento, facturaCliente);
/*  298:     */       }
/*  299: 281 */       else if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_DEBITO))
/*  300:     */       {
/*  301: 282 */         context = JAXBContext.newInstance(new Class[] { NotaDebitoClienteJaxb.class });
/*  302: 283 */         comprobanteVentaJaxb = crearNotaDebitoClienteJaxb(documento, facturaCliente);
/*  303:     */       }
/*  304: 286 */       else if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO))
/*  305:     */       {
/*  306: 287 */         context = JAXBContext.newInstance(new Class[] { NotaCreditoClienteJaxb.class });
/*  307: 288 */         comprobanteVentaJaxb = crearNotaCreditoClienteJaxb(documento, facturaCliente);
/*  308:     */       }
/*  309:     */       else
/*  310:     */       {
/*  311:     */         SimpleDateFormat sdf;
/*  312:     */         String mensaje;
/*  313: 290 */         return;
/*  314:     */       }
/*  315: 293 */       comprobanteVentaJaxb.setVersion(documento.getVersion());
/*  316:     */       
/*  317: 295 */       InfoTributariaJaxb infoTributariaJaxb = new InfoTributariaJaxb();
/*  318: 296 */       infoTributariaJaxb.setAmbiente(Integer.valueOf(documento.getAmbiente()));
/*  319: 297 */       infoTributariaJaxb.setTipoEmision(Integer.valueOf(documento.getTipoEmision()));
/*  320: 298 */       infoTributariaJaxb.setRazonSocial(documento.getAmbiente() == 2 ? documento.getOrganizacion().getRazonSocial() : "PRUEBAS SERVICIO DE RENTAS INTERNAS.");
/*  321:     */       
/*  322: 300 */       infoTributariaJaxb.setNombreComercial(documento.getOrganizacion().getRazonSocial());
/*  323: 301 */       infoTributariaJaxb.setRuc(documento.getOrganizacion().getIdentificacion());
/*  324: 302 */       infoTributariaJaxb.setClaveAcceso(documento.getClaveAcceso());
/*  325: 303 */       infoTributariaJaxb.setCodDoc(documento.getTipoDocumento().getCodigo());
/*  326: 304 */       infoTributariaJaxb.setEstab(facturaClienteSRI.getEstablecimiento());
/*  327: 305 */       infoTributariaJaxb.setPtoEmi(facturaClienteSRI.getPuntoEmision());
/*  328: 306 */       infoTributariaJaxb.setSecuencial(numeroComprobante);
/*  329: 307 */       infoTributariaJaxb.setDirMatriz(documento.getDireccionMatriz() == null ? "S/N" : documento.getDireccionMatriz());
/*  330:     */       
/*  331: 309 */       comprobanteVentaJaxb.setInfoTributaria(infoTributariaJaxb);
/*  332: 312 */       if ((documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA)) || 
/*  333: 313 */         (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO)))
/*  334:     */       {
/*  335: 314 */         DetallesJaxb detallesJaxb = crearDetallesJaxb(documento, facturaCliente, infoAdicionalJaxb);
/*  336: 315 */         if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA))
/*  337:     */         {
/*  338: 316 */           ((FacturaClienteJaxb)comprobanteVentaJaxb).setDetallesJaxb(detallesJaxb);
/*  339: 317 */           PagosJaxb pagosJaxb = ((FacturaClienteJaxb)comprobanteVentaJaxb).getInfoFactura().getPagos();
/*  340: 318 */           if ((pagosJaxb != null) && (pagosJaxb.getListaPagos() != null) && (!pagosJaxb.getListaPagos().isEmpty()))
/*  341:     */           {
/*  342: 320 */             PagoJaxb pagoJaxb = (PagoJaxb)pagosJaxb.getListaPagos().get(0);
/*  343: 321 */             pagoJaxb.setTotal(facturaCliente.getTotalFactura().subtract(this.impuestoCampesino));
/*  344:     */           }
/*  345:     */         }
/*  346: 324 */         if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO)) {
/*  347: 325 */           ((NotaCreditoClienteJaxb)comprobanteVentaJaxb).setDetallesJaxb(detallesJaxb);
/*  348:     */         }
/*  349:     */       }
/*  350: 329 */       infoAdicionalJaxb = agregarInfoAdicionalJaxb(documento, facturaCliente, infoAdicionalJaxb);
/*  351: 331 */       if ((infoAdicionalJaxb != null) && (!infoAdicionalJaxb.getlListaCampoAdicional().isEmpty())) {
/*  352: 332 */         comprobanteVentaJaxb.setInfoAdicional(infoAdicionalJaxb);
/*  353:     */       }
/*  354: 335 */       Marshaller m = context.createMarshaller();
/*  355: 336 */       m.marshal(comprobanteVentaJaxb, new File(documento.getPathArchivoEmitido()));
/*  356:     */       
/*  357:     */ 
/*  358: 339 */       documento.firmar();
/*  359:     */       
/*  360: 341 */       estadoSRI = EstadoDocumentoElectronico.EMITIDO;
/*  361: 342 */       mensajeSRI = estadoSRI.toString();
/*  362:     */     }
/*  363:     */     catch (Exception e)
/*  364:     */     {
/*  365:     */       SimpleDateFormat sdf;
/*  366:     */       String mensaje;
/*  367: 344 */       e.printStackTrace();
/*  368: 345 */       mensajeSRI = e.getMessage();
/*  369: 346 */       throw new AS2Exception(e.getMessage());
/*  370:     */     }
/*  371:     */     finally
/*  372:     */     {
/*  373: 349 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
/*  374: 350 */       mensajeSRI = sdf.format(new Date()) + " => " + mensajeSRI;
/*  375: 351 */       String mensaje = mensajeSRI.length() > 5000 ? mensajeSRI.substring(0, 5000) : mensajeSRI;
/*  376: 352 */       this.servicioFacturaCliente.actualizaFacturaClienteSRI(facturaCliente.getIdFacturaCliente(), Estado.EN_ESPERA_CONTINGENCIA, estadoSRI, null, null, mensaje);
/*  377:     */     }
/*  378:     */   }
/*  379:     */   
/*  380:     */   private InfoAdicionalJaxb agregarInfoAdicionalJaxb(DocumentoElectronico documento, FacturaCliente facturaCliente, InfoAdicionalJaxb infoAdicionalJaxb)
/*  381:     */   {
/*  382: 362 */     if ((facturaCliente.getDescripcion() != null) && (facturaCliente.getDescripcion().trim() != null) && 
/*  383: 363 */       (!facturaCliente.getDescripcion().trim().isEmpty()) && (!facturaCliente.getDescripcion().trim().equals("\n")))
/*  384:     */     {
/*  385: 364 */       String descripcion = facturaCliente.getDescripcion();
/*  386: 365 */       if ((facturaCliente.getDescripcion2() != null) && (facturaCliente.getDescripcion2().trim() != null) && 
/*  387: 366 */         (!facturaCliente.getDescripcion2().trim().equals("\n"))) {
/*  388: 367 */         descripcion = descripcion + " " + facturaCliente.getDescripcion2();
/*  389:     */       }
/*  390: 370 */       if (descripcion.length() > 300) {
/*  391: 371 */         descripcion = descripcion.substring(0, 299);
/*  392:     */       }
/*  393: 375 */       descripcion = descripcion.replace("\n", "");
/*  394:     */       
/*  395: 377 */       infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Descripcion", descripcion);
/*  396:     */     }
/*  397:     */     try
/*  398:     */     {
/*  399: 381 */       if ((facturaCliente.getDireccionEmpresa() != null) && (facturaCliente.getDireccionEmpresa().getDireccionCompleta() != null) && 
/*  400: 382 */         (!facturaCliente.getDireccionEmpresa().getDireccionCompleta().isEmpty()))
/*  401:     */       {
/*  402: 383 */         String direccion = facturaCliente.getDireccionEmpresa().getDireccionCompleta();
/*  403:     */         
/*  404: 385 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Direccion", direccion);
/*  405: 386 */         if ((facturaCliente.getDireccionEmpresa().getTelefono1() != null) && (!facturaCliente.getDireccionEmpresa().getTelefono1().isEmpty())) {
/*  406: 387 */           infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Telefono", facturaCliente.getDireccionEmpresa()
/*  407: 388 */             .getTelefono1());
/*  408:     */         }
/*  409:     */       }
/*  410:     */     }
/*  411:     */     catch (Exception e)
/*  412:     */     {
/*  413: 392 */       e.printStackTrace();
/*  414:     */     }
/*  415: 396 */     if ((facturaCliente.getFacturaClienteSRI().getEmail() != null) && (!facturaCliente.getFacturaClienteSRI().getEmail().isEmpty())) {
/*  416: 397 */       infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Email", facturaCliente.getFacturaClienteSRI().getEmail());
/*  417:     */     }
/*  418: 400 */     if (facturaCliente.getFechaVencimiento() != null) {
/*  419:     */       try
/*  420:     */       {
/*  421: 402 */         SimpleDateFormat sdf = new SimpleDateFormat(ParametrosSistema.getFormatoFecha(documento.getOrganizacion().getId()));
/*  422: 403 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "FechaVencimiento", sdf
/*  423: 404 */           .format(facturaCliente.getFechaVencimiento()));
/*  424:     */       }
/*  425:     */       catch (Exception localException1) {}
/*  426:     */     }
/*  427: 411 */     if (facturaCliente.getEmpresa().getCliente().getIndicadorEnvio().booleanValue())
/*  428:     */     {
/*  429: 412 */       FacturaCliente facturaClienteAux = facturaCliente;
/*  430: 414 */       if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO)) {
/*  431: 415 */         facturaClienteAux = facturaCliente.getFacturaClientePadre();
/*  432:     */       }
/*  433: 418 */       if (((facturaClienteAux.getReferencia8() != null) && (!facturaClienteAux.getReferencia8().isEmpty())) || (
/*  434: 419 */         (facturaClienteAux.getReferencia9() != null) && (!facturaClienteAux.getReferencia9().isEmpty())))
/*  435:     */       {
/*  436: 420 */         if ((facturaClienteAux.getReferencia8() != null) && (!facturaClienteAux.getReferencia8().isEmpty())) {
/*  437: 421 */           infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, facturaClienteAux.getEmpresa().getCliente().getReferencia(), facturaClienteAux
/*  438: 422 */             .getReferencia8());
/*  439:     */         }
/*  440: 424 */         if ((facturaClienteAux.getReferencia9() != null) && (!facturaClienteAux.getReferencia9().isEmpty())) {
/*  441: 425 */           infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, facturaClienteAux.getEmpresa().getCliente().getReferencia2(), facturaClienteAux
/*  442: 426 */             .getReferencia9());
/*  443:     */         }
/*  444:     */       }
/*  445:     */     }
/*  446: 431 */     if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA))
/*  447:     */     {
/*  448: 433 */       if (facturaCliente.getDespachoCliente() != null) {
/*  449: 434 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Despacho", facturaCliente.getDespachoCliente().getNumero());
/*  450:     */       }
/*  451: 436 */       if (facturaCliente.getPedidoCliente() != null) {
/*  452: 437 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Pedido", facturaCliente.getPedidoCliente().getNumero());
/*  453:     */       }
/*  454: 439 */       if (facturaCliente.getEmpresa().getCliente().getListaDescuentos() != null) {
/*  455: 440 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Descuentos", facturaCliente
/*  456: 441 */           .getEmpresa().getCliente().getListaDescuentos().getCodigo());
/*  457:     */       }
/*  458: 443 */       if ((facturaCliente.getAgenteComercial() != null) && (facturaCliente.getAgenteComercial().getNombre1() != null) && 
/*  459: 444 */         (facturaCliente.getAgenteComercial().getNombre2() != null)) {
/*  460: 445 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Vendedor", facturaCliente
/*  461: 446 */           .getAgenteComercial().getNombre1() + " " + facturaCliente.getAgenteComercial().getNombre2());
/*  462:     */       }
/*  463: 448 */       if ((facturaCliente.getDireccionEmpresa() != null) && (facturaCliente.getDireccionEmpresa().getCiudad() != null) && 
/*  464: 449 */         (facturaCliente.getDireccionEmpresa().getCiudad().getNombre() != null)) {
/*  465: 450 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Ciudad", facturaCliente
/*  466: 451 */           .getDireccionEmpresa().getCiudad().getNombre());
/*  467:     */       }
/*  468: 453 */       if (facturaCliente.getCondicionPago().getNombre() != null) {
/*  469: 454 */         infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "CondPago", facturaCliente.getCondicionPago().getNombre());
/*  470:     */       }
/*  471:     */     }
/*  472: 458 */     return infoAdicionalJaxb;
/*  473:     */   }
/*  474:     */   
/*  475:     */   private DetallesJaxb crearDetallesJaxb(DocumentoElectronico documento, FacturaCliente facturaCliente, InfoAdicionalJaxb infoAdicionalJaxb)
/*  476:     */   {
/*  477: 462 */     int decimales = 2;
/*  478: 463 */     if (((facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.NOTA_CREDITO_CLIENTE)) || 
/*  479: 464 */       (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_CLIENTE)) || 
/*  480: 465 */       (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.FACTURA_CLIENTE))) && 
/*  481: 466 */       (facturaCliente.getEmpresa().getCliente().getVersion().equals("1.1.0"))) {
/*  482: 467 */       decimales = 6;
/*  483:     */     }
/*  484: 471 */     DetallesJaxb detallesJaxb = new DetallesJaxb();
/*  485: 472 */     detallesJaxb.setListaDetalle(new ArrayList());
/*  486:     */     
/*  487: 474 */     HashMap<String, impuestoCampoAdicional> hmImpuestoCampoAdicional = new HashMap();
/*  488: 476 */     for (DetalleFacturaCliente detalleFactura : facturaCliente.getListaDetalleFacturaCliente()) {
/*  489: 478 */       if (!detalleFactura.isEliminado())
/*  490:     */       {
/*  491: 480 */         DetalleJaxb detalleJaxb = new DetalleJaxb();
/*  492: 481 */         detallesJaxb.getListaDetalle().add(detalleJaxb);
/*  493: 483 */         if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA))
/*  494:     */         {
/*  495: 484 */           detalleJaxb.setCodigoPrincipal(detalleFactura.getProducto().getCodigo());
/*  496: 485 */           if ((detalleFactura.getProducto().getCodigoAlterno() != null) && (!detalleFactura.getProducto().getCodigoAlterno().isEmpty()))
/*  497:     */           {
/*  498: 486 */             String codigoAuxiliar = detalleFactura.getProducto().getCodigoAlterno();
/*  499: 487 */             if (codigoAuxiliar.length() > 25) {
/*  500: 488 */               codigoAuxiliar = codigoAuxiliar.substring(0, 25);
/*  501:     */             }
/*  502: 490 */             detalleJaxb.setCodigoAuxiliar(codigoAuxiliar);
/*  503:     */           }
/*  504:     */         }
/*  505: 492 */         else if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO))
/*  506:     */         {
/*  507: 493 */           detalleJaxb.setCodigoInterno(detalleFactura.getProducto().getCodigo());
/*  508:     */         }
/*  509: 495 */         String descripcionProductoDetalle = detalleFactura.getProducto().getNombre();
/*  510: 496 */         boolean indicadorDescripcionProducto = ParametrosSistema.getXMLFacturacionElectronicaDescripcionProducto(documento.getOrganizacion()
/*  511: 497 */           .getId()).booleanValue();
/*  512: 498 */         if ((indicadorDescripcionProducto) && (detalleFactura.getProducto().getDescripcion() != null) && 
/*  513: 499 */           (!detalleFactura.getProducto().getDescripcion().isEmpty())) {
/*  514: 500 */           descripcionProductoDetalle = detalleFactura.getProducto().getDescripcion();
/*  515:     */         }
/*  516: 502 */         if ((detalleFactura.getDescripcion() != null) && (!detalleFactura.getDescripcion().isEmpty())) {
/*  517: 503 */           descripcionProductoDetalle = descripcionProductoDetalle + "  --  " + detalleFactura.getDescripcion();
/*  518:     */         }
/*  519: 505 */         if (descripcionProductoDetalle.length() > 300) {
/*  520: 506 */           descripcionProductoDetalle = descripcionProductoDetalle.substring(0, 299);
/*  521:     */         }
/*  522: 508 */         detalleJaxb.setDescripcion(descripcionProductoDetalle.replace("\n", ""));
/*  523: 510 */         if (!detalleFactura.isIndicadorManejoPeso()) {
/*  524: 511 */           detalleJaxb.setCantidad(detalleFactura.getCantidad().setScale(decimales, RoundingMode.HALF_UP));
/*  525:     */         }
/*  526: 513 */         if (detalleFactura.isIndicadorManejoPeso()) {
/*  527: 514 */           detalleJaxb.setCantidad(detalleFactura.getPeso().setScale(2, RoundingMode.HALF_UP));
/*  528:     */         }
/*  529: 516 */         detalleJaxb.setPrecioUnitario(detalleFactura.getPrecio().setScale(decimales, RoundingMode.HALF_UP));
/*  530: 518 */         if ((documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA)) && 
/*  531: 519 */           (detalleFactura.getSubsidio().compareTo(BigDecimal.ZERO) != 0)) {
/*  532: 521 */           detalleJaxb.setPrecioSinSubsidio(FuncionesUtiles.redondearBigDecimal(detalleFactura
/*  533: 522 */             .getSubsidio().add(detalleFactura.getPrecio()), 6));
/*  534:     */         }
/*  535: 525 */         detalleJaxb.setDescuento(detalleFactura.getDescuentoLinea().setScale(2, RoundingMode.HALF_UP));
/*  536: 526 */         detalleJaxb.setPrecioTotalSinImpuesto(detalleFactura.getPrecioLinea().subtract(detalleFactura.getDescuentoLinea())
/*  537: 527 */           .setScale(2, RoundingMode.HALF_UP));
/*  538:     */         
/*  539: 529 */         DetalleAdicionalJaxb detalleAdicionalJaxb = new DetalleAdicionalJaxb();
/*  540: 530 */         detalleJaxb.setDetallesAdicionales(detalleAdicionalJaxb);
/*  541: 531 */         if ((detalleFactura.getDetalleDespachoCliente() != null) && (detalleFactura.getDetalleDespachoCliente().getInventarioProducto() != null) && 
/*  542: 532 */           (detalleFactura.getDetalleDespachoCliente().getInventarioProducto().getLote() != null)) {
/*  543: 533 */           detalleAdicionalJaxb = agregarDetalleAdicional(detalleAdicionalJaxb, "Lote", detalleFactura
/*  544: 534 */             .getDetalleDespachoCliente().getInventarioProducto().getLote().getCodigo());
/*  545:     */         }
/*  546: 536 */         if (detalleFactura.getUnidadVenta().getCodigo() != null) {
/*  547: 537 */           detalleAdicionalJaxb = agregarDetalleAdicional(detalleAdicionalJaxb, "Unidad", detalleFactura.getUnidadVenta().getNombre());
/*  548:     */         }
/*  549: 539 */         if (detalleFactura.getPorcentajeDescuento() != null) {
/*  550: 540 */           detalleAdicionalJaxb = agregarDetalleAdicional(detalleAdicionalJaxb, "PorcentajeDescuento", detalleFactura.getPorcentajeDescuento() + "");
/*  551:     */         }
/*  552: 544 */         ImpuestosJaxb impuestosJaxb = new ImpuestosJaxb();
/*  553: 545 */         impuestosJaxb.setListaImpuesto(new ArrayList());
/*  554: 546 */         detalleJaxb.setImpuestos(impuestosJaxb);
/*  555:     */         
/*  556: 548 */         List<ImpuestoProductoFacturaCliente> listaImpuesto = new ArrayList();
/*  557: 549 */         listaImpuesto.addAll(detalleFactura.getListaImpuestoProductoFacturaCliente());
/*  558:     */         ImpuestoProductoFacturaCliente impuesto;
/*  559: 551 */         if (listaImpuesto.isEmpty())
/*  560:     */         {
/*  561: 553 */           impuesto = new ImpuestoProductoFacturaCliente();
/*  562: 554 */           impuesto.setImpuestoProducto(BigDecimal.ZERO);
/*  563: 555 */           impuesto.setImpuesto(new Impuesto(0, "2", "IVA 0%"));
/*  564: 556 */           impuesto.setPorcentajeImpuesto(BigDecimal.ZERO);
/*  565: 557 */           impuesto.setDetalleFacturaCliente(detalleFactura);
/*  566: 558 */           listaImpuesto.add(impuesto);
/*  567:     */         }
/*  568: 561 */         for (ImpuestoProductoFacturaCliente impuestoProducto : listaImpuesto) {
/*  569: 562 */           if (!impuestoProducto.isEliminado()) {
/*  570: 563 */             if ((impuestoProducto.getImpuesto().getCodigo().equals("2")) || (impuestoProducto.getImpuesto().getCodigo().equals("3")) || 
/*  571: 564 */               (impuestoProducto.getImpuesto().getCodigo().equals("5")))
/*  572:     */             {
/*  573: 566 */               ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/*  574: 567 */               impuestosJaxb.getListaImpuesto().add(impuestoJaxb);
/*  575:     */               
/*  576: 569 */               impuestoJaxb.setCodigo(impuestoProducto.getImpuesto().getCodigo());
/*  577: 570 */               double porcentaje = impuestoProducto.getPorcentajeImpuesto().doubleValue();
/*  578: 571 */               impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(Integer.parseInt(FuncionesUtiles.getCodigoImpuesto(porcentaje))));
/*  579: 572 */               impuestoJaxb.setTarifa(impuestoProducto.getPorcentajeImpuesto().setScale(2, RoundingMode.HALF_UP));
/*  580: 574 */               if (impuestoProducto.getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES))
/*  581:     */               {
/*  582: 575 */                 BigDecimal cantidadUnidades = detalleFactura.getCantidad();
/*  583: 576 */                 if (detalleFactura.getProducto().getPresentacionProducto() != null) {
/*  584: 577 */                   cantidadUnidades = cantidadUnidades.multiply(detalleFactura.getProducto().getPresentacionProducto()
/*  585: 578 */                     .getCantidadUnidades());
/*  586:     */                 }
/*  587: 580 */                 impuestoJaxb.setBaseImponible(cantidadUnidades.setScale(2, RoundingMode.HALF_UP));
/*  588:     */               }
/*  589: 581 */               else if (impuestoProducto.getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_PRECIO))
/*  590:     */               {
/*  591: 582 */                 impuestoJaxb.setBaseImponible(detalleFactura.getBaseImponible().setScale(2, RoundingMode.HALF_UP));
/*  592:     */               }
/*  593: 584 */               impuestoJaxb.setValor(impuestoProducto.getImpuestoProducto().setScale(2, RoundingMode.HALF_UP));
/*  594:     */             }
/*  595:     */             else
/*  596:     */             {
/*  597: 590 */               ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/*  598: 591 */               impuestosJaxb.getListaImpuesto().add(impuestoJaxb);
/*  599:     */               
/*  600: 593 */               impuestoJaxb.setCodigo("2");
/*  601: 594 */               impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(0));
/*  602: 595 */               impuestoJaxb.setTarifa(BigDecimal.ZERO);
/*  603: 596 */               impuestoJaxb.setBaseImponible(detalleFactura.getBaseImponible().setScale(2, RoundingMode.HALF_UP));
/*  604: 597 */               impuestoJaxb.setValor(BigDecimal.ZERO);
/*  605: 599 */               if (impuestoProducto.getImpuestoProducto().compareTo(BigDecimal.ZERO) > 0)
/*  606:     */               {
/*  607: 600 */                 String clave = impuestoProducto.getImpuesto().getCodigo() + "~" + impuestoProducto.getImpuesto().getNombre();
/*  608: 601 */                 if (hmImpuestoCampoAdicional.get(clave) != null)
/*  609:     */                 {
/*  610: 602 */                   ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(clave)).setBaseImponible(
/*  611: 603 */                     ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(clave)).getBaseImponible()
/*  612: 604 */                     .add(detalleFactura.getBaseImponible().setScale(2, RoundingMode.HALF_UP)));
/*  613: 605 */                   ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(clave)).setValor(
/*  614: 606 */                     ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(clave)).getValor()
/*  615: 607 */                     .add(impuestoProducto.getImpuestoProducto().setScale(2, RoundingMode.HALF_UP)));
/*  616:     */                 }
/*  617:     */                 else
/*  618:     */                 {
/*  619: 613 */                   impuestoCampoAdicional ica = new impuestoCampoAdicional(impuestoProducto.getImpuesto().getNombre(), impuestoProducto.getImpuesto().getCodigo(), impuestoProducto.getPorcentajeImpuesto().setScale(2, RoundingMode.HALF_UP), detalleFactura.getBaseImponible().setScale(2, RoundingMode.HALF_UP), impuestoProducto.getImpuestoProducto().setScale(2, RoundingMode.HALF_UP));
/*  620: 614 */                   hmImpuestoCampoAdicional.put(clave, ica);
/*  621:     */                 }
/*  622:     */               }
/*  623:     */             }
/*  624:     */           }
/*  625:     */         }
/*  626: 622 */         if (detalleFactura.getIceLinea().compareTo(BigDecimal.ZERO) > 0)
/*  627:     */         {
/*  628: 623 */           ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/*  629: 624 */           impuestosJaxb.getListaImpuesto().add(impuestoJaxb);
/*  630:     */           
/*  631: 626 */           impuestoJaxb.setCodigo("3");
/*  632: 627 */           impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(Integer.parseInt(detalleFactura.getCodigoIce())));
/*  633: 628 */           impuestoJaxb.setTarifa(detalleFactura.getIce());
/*  634: 629 */           impuestoJaxb.setBaseImponible(detalleFactura.getBaseImponible().subtract(detalleFactura.getIceLinea())
/*  635: 630 */             .setScale(2, RoundingMode.HALF_UP));
/*  636: 631 */           impuestoJaxb.setValor(detalleFactura.getIceLinea().setScale(2, RoundingMode.HALF_UP));
/*  637:     */         }
/*  638:     */       }
/*  639:     */     }
/*  640: 638 */     Object it = hmImpuestoCampoAdicional.keySet().iterator();
/*  641: 639 */     while (((Iterator)it).hasNext())
/*  642:     */     {
/*  643: 640 */       String key = (String)((Iterator)it).next();
/*  644: 643 */       if (((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(key)).getNombreImpuesto().equals("Seguro Campesino")) {
/*  645: 644 */         this.impuestoCampesino = this.impuestoCampesino.add(((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(key)).getValor());
/*  646:     */       }
/*  647: 648 */       infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Codigo Impuesto Adicional", ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(key))
/*  648: 649 */         .getCodigoImpuesto());
/*  649:     */       
/*  650: 651 */       infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Nombre Impuesto Adicional", ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(key))
/*  651: 652 */         .getNombreImpuesto());
/*  652:     */       
/*  653: 654 */       infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Tarifa Impuesto Adicional", ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(key))
/*  654: 655 */         .getTarifa().toString());
/*  655:     */       
/*  656: 657 */       infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Base Imponible Impuesto Adicional", ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(key))
/*  657: 658 */         .getBaseImponible().toString());
/*  658:     */       
/*  659: 660 */       infoAdicionalJaxb = agregarInformacionAdicional(infoAdicionalJaxb, "Valor Impuesto Adicional", ((impuestoCampoAdicional)hmImpuestoCampoAdicional.get(key))
/*  660: 661 */         .getValor().toString());
/*  661:     */     }
/*  662: 664 */     return detallesJaxb;
/*  663:     */   }
/*  664:     */   
/*  665:     */   private TotalConImpuestosJaxb crearTotalConImpuestosJaxb(DocumentoElectronico documento, FacturaCliente facturaCliente)
/*  666:     */   {
/*  667: 668 */     FacturaClienteSRI facturaClienteSRI = facturaCliente.getFacturaClienteSRI();
/*  668:     */     
/*  669: 670 */     TotalConImpuestosJaxb totalConImpuestosJaxb = new TotalConImpuestosJaxb();
/*  670: 671 */     totalConImpuestosJaxb.setListaTotalImpuesto(new ArrayList());
/*  671: 674 */     if (facturaClienteSRI.getMontoIRBPNR().compareTo(BigDecimal.ZERO) > 0)
/*  672:     */     {
/*  673: 675 */       ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/*  674: 676 */       impuestoJaxb.setCodigo("5");
/*  675: 677 */       impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(5001));
/*  676:     */       
/*  677: 679 */       impuestoJaxb.setBaseImponible(facturaClienteSRI.getMontoIRBPNR().divide(new BigDecimal(0.02D), 2, RoundingMode.HALF_UP));
/*  678: 680 */       impuestoJaxb.setValor(facturaClienteSRI.getMontoIRBPNR());
/*  679: 681 */       totalConImpuestosJaxb.getListaTotalImpuesto().add(impuestoJaxb);
/*  680:     */     }
/*  681: 685 */     if (facturaClienteSRI.getBaseImponibleDiferenteCero().compareTo(BigDecimal.ZERO) > 0)
/*  682:     */     {
/*  683: 686 */       double porcentaje = getPorcentajeImpuesto(facturaClienteSRI);
/*  684: 687 */       BigDecimal valorBaseImponible = facturaClienteSRI.getBaseImponibleDiferenteCero();
/*  685: 688 */       if (facturaCliente.getDocumento().isIndicadorDocumentoExterior()) {
/*  686: 692 */         valorBaseImponible = valorBaseImponible.subtract(facturaCliente.getFleteInternacional()).subtract(facturaCliente.getGastosAduaneros()).subtract(facturaCliente.getOtrosGastosTransporte()).subtract(facturaCliente.getSeguroInternacional());
/*  687:     */       }
/*  688: 695 */       ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/*  689: 696 */       impuestoJaxb.setCodigo("2");
/*  690: 697 */       impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(Integer.parseInt(FuncionesUtiles.getCodigoImpuesto(porcentaje))));
/*  691: 698 */       impuestoJaxb.setBaseImponible(valorBaseImponible);
/*  692: 699 */       impuestoJaxb.setValor(facturaClienteSRI.getMontoIva());
/*  693: 700 */       totalConImpuestosJaxb.getListaTotalImpuesto().add(impuestoJaxb);
/*  694:     */     }
/*  695: 704 */     if (facturaClienteSRI.getBaseImponibleTarifaCero().compareTo(BigDecimal.ZERO) != 0)
/*  696:     */     {
/*  697: 705 */       BigDecimal valorBaseImponibleTarifaCero = facturaClienteSRI.getBaseImponibleTarifaCero();
/*  698: 706 */       if (facturaCliente.getDocumento().isIndicadorDocumentoExterior()) {
/*  699: 710 */         valorBaseImponibleTarifaCero = valorBaseImponibleTarifaCero.subtract(facturaCliente.getFleteInternacional()).subtract(facturaCliente.getGastosAduaneros()).subtract(facturaCliente.getOtrosGastosTransporte()).subtract(facturaCliente.getSeguroInternacional());
/*  700:     */       }
/*  701: 713 */       ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/*  702: 714 */       impuestoJaxb.setCodigo("2");
/*  703: 715 */       impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(0));
/*  704: 716 */       impuestoJaxb.setBaseImponible(valorBaseImponibleTarifaCero);
/*  705: 717 */       impuestoJaxb.setValor(BigDecimal.ZERO);
/*  706: 718 */       totalConImpuestosJaxb.getListaTotalImpuesto().add(impuestoJaxb);
/*  707:     */     }
/*  708: 722 */     if (facturaCliente.getMontoIce().compareTo(BigDecimal.ZERO) > 0)
/*  709:     */     {
/*  710: 723 */       Map<String, informacionICE> mapaDetalleFacturaCliente = detalleFacturaClienteImpuestoIce(facturaCliente.getListaDetalleFacturaCliente());
/*  711:     */       
/*  712: 725 */       Iterator<String> it = mapaDetalleFacturaCliente.keySet().iterator();
/*  713: 726 */       while (it.hasNext())
/*  714:     */       {
/*  715: 727 */         String key = (String)it.next();
/*  716:     */         
/*  717: 729 */         ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/*  718: 730 */         impuestoJaxb.setCodigo("3");
/*  719: 731 */         impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(Integer.parseInt(key)));
/*  720: 732 */         impuestoJaxb.setBaseImponible(((informacionICE)mapaDetalleFacturaCliente.get(key)).getTotalBaseImponible().setScale(2, RoundingMode.HALF_UP));
/*  721: 733 */         impuestoJaxb.setValor(((informacionICE)mapaDetalleFacturaCliente.get(key)).getTotalIce().setScale(2, RoundingMode.HALF_UP));
/*  722: 734 */         totalConImpuestosJaxb.getListaTotalImpuesto().add(impuestoJaxb);
/*  723:     */       }
/*  724:     */     }
/*  725: 739 */     return totalConImpuestosJaxb;
/*  726:     */   }
/*  727:     */   
/*  728:     */   private CompensacionesJaxb crearCompensacionesJaxb(DocumentoElectronico documento, FacturaCliente facturaCliente)
/*  729:     */   {
/*  730: 743 */     CompensacionesJaxb compensacionesJaxb = new CompensacionesJaxb();
/*  731: 744 */     compensacionesJaxb.setListaCompensacionJaxb(new ArrayList());
/*  732:     */     
/*  733: 746 */     CompensacionJaxb compensacionJaxb = new CompensacionJaxb();
/*  734: 747 */     compensacionJaxb.setCodigo("1");
/*  735: 748 */     compensacionJaxb.setTarifa(new BigDecimal(2));
/*  736: 749 */     compensacionJaxb.setValor(facturaCliente.getDescuentoImpuesto());
/*  737: 750 */     compensacionesJaxb.getListaCompensacionJaxb().add(compensacionJaxb);
/*  738:     */     
/*  739: 752 */     return compensacionesJaxb;
/*  740:     */   }
/*  741:     */   
/*  742:     */   private FacturaClienteJaxb crearFacturaClienteJaxb(DocumentoElectronico documento, FacturaCliente facturaCliente)
/*  743:     */   {
/*  744: 757 */     FacturaClienteJaxb facturaClienteJaxb = new FacturaClienteJaxb();
/*  745:     */     
/*  746:     */ 
/*  747: 760 */     InfoFacturaJaxb infoFacturaJaxb = new InfoFacturaJaxb();
/*  748: 761 */     infoFacturaJaxb.setFechaEmision(formatoFecha.format(facturaCliente.getFecha()));
/*  749: 762 */     infoFacturaJaxb.setDirEstablecimiento(documento.getDireccionSucursal() == null ? "S/N" : documento.getDireccionSucursal());
/*  750: 763 */     if ((documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente() != null) && 
/*  751: 764 */       (!documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente().equals(""))) {
/*  752: 765 */       infoFacturaJaxb.setContribuyenteEspecial(documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente());
/*  753:     */     }
/*  754: 767 */     infoFacturaJaxb.setObligadoContabilidad(documento.getOrganizacion().getOrganizacionConfiguracion().isIndicadorObligadoContabilidad() ? "SI" : "NO");
/*  755:     */     
/*  756: 769 */     String tipIdenComprador = FuncionesUtiles.getTipoIdentificacionClienteSRI(facturaCliente.getEmpresa().getTipoIdentificacion().getCodigo());
/*  757: 770 */     infoFacturaJaxb.setTipoIdentificacionComprador(tipIdenComprador);
/*  758: 771 */     infoFacturaJaxb.setRazonSocialComprador(facturaCliente.getEmpresa().getNombreFiscal());
/*  759: 772 */     infoFacturaJaxb.setIdentificacionComprador(facturaCliente.getEmpresa().getIdentificacion());
/*  760:     */     
/*  761:     */ 
/*  762: 775 */     String direccionComprador = facturaCliente.getDireccionEmpresa().getUbicacion().getDireccionCompleta().length() < 300 ? facturaCliente.getDireccionEmpresa().getUbicacion().getDireccionCompleta() : facturaCliente.getDireccionEmpresa().getUbicacion().getDireccionCompleta().substring(0, 300);
/*  763: 776 */     infoFacturaJaxb.setDireccionComprador(direccionComprador);
/*  764: 777 */     infoFacturaJaxb.setTotalDescuento(facturaCliente.getDescuento());
/*  765: 778 */     infoFacturaJaxb.setPropina(BigDecimal.ZERO);
/*  766:     */     
/*  767: 780 */     BigDecimal importeTotal = facturaCliente.getTotal().subtract(facturaCliente.getDescuento()).add(facturaCliente.getImpuesto()).subtract(calcularOtrosImpuestos(facturaCliente)).add(facturaCliente.getMontoIce()).setScale(2, RoundingMode.HALF_UP);
/*  768: 781 */     infoFacturaJaxb.setImporteTotal(importeTotal);
/*  769: 782 */     infoFacturaJaxb.setMoneda("DOLAR");
/*  770: 784 */     if (facturaCliente.getFacturaClienteSRI().getTotalSubsidio().compareTo(BigDecimal.ZERO) != 0) {
/*  771: 785 */       infoFacturaJaxb.setTotalSubsidio(facturaCliente.getFacturaClienteSRI().getTotalSubsidio());
/*  772:     */     }
/*  773: 788 */     BigDecimal valorTotalSinImpuestos = facturaCliente.getTotal().subtract(facturaCliente.getDescuento());
/*  774: 790 */     if (facturaCliente.getDocumento().isIndicadorDocumentoExterior())
/*  775:     */     {
/*  776: 792 */       infoFacturaJaxb.setComercioExterior("EXPORTADOR");
/*  777: 793 */       infoFacturaJaxb.setIncoTermFactura(facturaCliente.getIncoterm().toUpperCase());
/*  778: 794 */       infoFacturaJaxb.setLugarIncoTerm(facturaCliente.getLugarIncoterm().getNombre());
/*  779: 795 */       infoFacturaJaxb.setPaisOrigen(facturaCliente.getPaisOrigen().getCodigo());
/*  780: 796 */       infoFacturaJaxb.setPuertoEmbarque(facturaCliente.getPuertoEmbarque().getNombre());
/*  781: 797 */       infoFacturaJaxb.setPuertoDestino(facturaCliente.getPuertoDestino().getNombre());
/*  782: 798 */       infoFacturaJaxb.setPaisDestino(facturaCliente.getPaisDestino().getCodigo());
/*  783: 799 */       infoFacturaJaxb.setIncoTermTotalSinImpuestos("FOB");
/*  784: 800 */       infoFacturaJaxb.setFleteInternacional(facturaCliente.getFleteInternacional());
/*  785: 801 */       infoFacturaJaxb.setSeguroInternacional(facturaCliente.getSeguroInternacional());
/*  786: 802 */       infoFacturaJaxb.setGastosAduaneros(facturaCliente.getGastosAduaneros());
/*  787: 803 */       infoFacturaJaxb.setGastosTransporteOtros(facturaCliente.getOtrosGastosTransporte());
/*  788: 804 */       if (facturaCliente.getPaisAdquisicion() != null) {
/*  789: 805 */         infoFacturaJaxb.setPaisAdquisicion(facturaCliente.getPaisAdquisicion().getCodigo());
/*  790:     */       }
/*  791: 810 */       valorTotalSinImpuestos = valorTotalSinImpuestos.subtract(facturaCliente.getFleteInternacional()).subtract(facturaCliente.getGastosAduaneros()).subtract(facturaCliente.getOtrosGastosTransporte()).subtract(facturaCliente.getSeguroInternacional());
/*  792:     */     }
/*  793: 812 */     infoFacturaJaxb.setTotalSinImpuestos(valorTotalSinImpuestos);
/*  794:     */     
/*  795: 814 */     TotalConImpuestosJaxb totalConImpuestosJaxb = crearTotalConImpuestosJaxb(documento, facturaCliente);
/*  796: 815 */     infoFacturaJaxb.setTotalConImpuestosJaxb(totalConImpuestosJaxb);
/*  797: 816 */     if (facturaCliente.getDescuentoImpuesto().compareTo(BigDecimal.ZERO) > 0)
/*  798:     */     {
/*  799: 817 */       CompensacionesJaxb compensacionesJaxb = crearCompensacionesJaxb(documento, facturaCliente);
/*  800: 818 */       infoFacturaJaxb.setCompensacionesJaxb(compensacionesJaxb);
/*  801:     */     }
/*  802: 822 */     if ((facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI() != null) && 
/*  803: 823 */       (!"".equals(facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI())))
/*  804:     */     {
/*  805: 824 */       PagosJaxb pagosJaxb = new PagosJaxb();
/*  806: 825 */       pagosJaxb.setListaPagos(new ArrayList());
/*  807:     */       
/*  808: 827 */       PagoJaxb pagoJaxb = new PagoJaxb();
/*  809: 828 */       pagoJaxb.setFormaPago(facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI());
/*  810:     */       
/*  811: 830 */       pagoJaxb.setTotal(facturaCliente.getTotalFactura().subtract(this.impuestoCampesino));
/*  812: 831 */       pagoJaxb.setPlazo(new BigDecimal(numeroPlazo(facturaCliente).intValue()));
/*  813: 832 */       pagoJaxb.setUnidadTiempo(unidadTiempo(facturaCliente));
/*  814:     */       
/*  815: 834 */       pagosJaxb.getListaPagos().add(pagoJaxb);
/*  816: 835 */       infoFacturaJaxb.setPagos(pagosJaxb);
/*  817:     */     }
/*  818: 837 */     facturaClienteJaxb.setInfoFactura(infoFacturaJaxb);
/*  819:     */     
/*  820: 839 */     return facturaClienteJaxb;
/*  821:     */   }
/*  822:     */   
/*  823:     */   private NotaCreditoClienteJaxb crearNotaCreditoClienteJaxb(DocumentoElectronico documento, FacturaCliente facturaCliente)
/*  824:     */   {
/*  825: 843 */     NotaCreditoClienteJaxb notaCreditoClienteJaxb = new NotaCreditoClienteJaxb();
/*  826:     */     
/*  827:     */ 
/*  828: 846 */     InfoNCreditoJaxb infoNCreditoJaxb = new InfoNCreditoJaxb();
/*  829: 847 */     infoNCreditoJaxb.setFechaEmision(formatoFecha.format(facturaCliente.getFecha()));
/*  830: 848 */     infoNCreditoJaxb.setDirEstablecimiento(documento.getDireccionSucursal() == null ? "S/N" : documento.getDireccionSucursal());
/*  831: 849 */     String tipIdenComprador = FuncionesUtiles.getTipoIdentificacionClienteSRI(facturaCliente.getEmpresa().getTipoIdentificacion().getCodigo());
/*  832: 850 */     infoNCreditoJaxb.setTipoIdentificacionComprador(tipIdenComprador);
/*  833: 851 */     infoNCreditoJaxb.setRazonSocialComprador(facturaCliente.getEmpresa().getNombreFiscal());
/*  834: 852 */     infoNCreditoJaxb.setIdentificacionComprador(facturaCliente.getEmpresa().getIdentificacion());
/*  835: 853 */     if ((documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente() != null) && 
/*  836: 854 */       (!documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente().equals(""))) {
/*  837: 855 */       infoNCreditoJaxb.setContribuyenteEspecial(documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente());
/*  838:     */     }
/*  839: 857 */     infoNCreditoJaxb.setObligadoContabilidad(documento.getOrganizacion().getOrganizacionConfiguracion().isIndicadorObligadoContabilidad() ? "SI" : "NO");
/*  840:     */     
/*  841:     */ 
/*  842: 860 */     String codigoTipoDocumentoModificado = "01";
/*  843: 861 */     infoNCreditoJaxb.setCodDocModificado(codigoTipoDocumentoModificado);
/*  844:     */     
/*  845: 863 */     String[] cadenaSplit = facturaCliente.getFacturaClientePadre().getNumero().split("-");
/*  846: 864 */     String numeroDocModificado1 = FuncionesUtiles.completarALaIzquierda('0', 3, cadenaSplit[0]);
/*  847: 865 */     String numeroDocModificado2 = FuncionesUtiles.completarALaIzquierda('0', 3, cadenaSplit[1]);
/*  848: 866 */     String numeroDocModificado3 = FuncionesUtiles.completarALaIzquierda('0', 9, cadenaSplit[2]);
/*  849: 867 */     infoNCreditoJaxb.setNumDocModificado(numeroDocModificado1 + "-" + numeroDocModificado2 + "-" + numeroDocModificado3);
/*  850: 868 */     infoNCreditoJaxb.setFechaEmisionDocSustento(formatoFecha.format(facturaCliente.getFacturaClientePadre().getFecha()));
/*  851: 869 */     BigDecimal totalMont = facturaCliente.getFacturaClienteSRI().getMontoIce().add(facturaCliente.getFacturaClienteSRI().getMontoIRBPNR()).add(facturaCliente
/*  852: 870 */       .getFacturaClienteSRI().getMontoIva());
/*  853:     */     
/*  854: 872 */     BigDecimal valorModificado = BigDecimal.ZERO;
/*  855: 874 */     if ((totalMont.compareTo(BigDecimal.ZERO) == 0) && (facturaCliente.getImpuesto().compareTo(BigDecimal.ZERO) > 0)) {
/*  856: 875 */       valorModificado = facturaCliente.getTotal().subtract(facturaCliente.getDescuento());
/*  857:     */     } else {
/*  858: 877 */       valorModificado = facturaCliente.getTotalFactura();
/*  859:     */     }
/*  860: 880 */     infoNCreditoJaxb.setTotalSinImpuestos(facturaCliente.getTotal().subtract(facturaCliente.getDescuento()));
/*  861: 881 */     infoNCreditoJaxb.setValorModificacion(valorModificado);
/*  862: 882 */     infoNCreditoJaxb.setMoneda("DOLAR");
/*  863:     */     
/*  864: 884 */     TotalConImpuestosJaxb totalConImpuestosJaxb = crearTotalConImpuestosJaxb(documento, facturaCliente);
/*  865: 885 */     infoNCreditoJaxb.setTotalConImpuestosJaxb(totalConImpuestosJaxb);
/*  866: 886 */     if (facturaCliente.getDescuentoImpuesto().compareTo(BigDecimal.ZERO) > 0)
/*  867:     */     {
/*  868: 887 */       CompensacionesJaxb compensacionesJaxb = crearCompensacionesJaxb(documento, facturaCliente);
/*  869: 888 */       infoNCreditoJaxb.setCompensacionesJaxb(compensacionesJaxb);
/*  870:     */     }
/*  871: 891 */     infoNCreditoJaxb.setMotivo(facturaCliente.getMotivoNotaCreditoCliente().getNombre());
/*  872:     */     
/*  873: 893 */     notaCreditoClienteJaxb.setInfoNotaCredito(infoNCreditoJaxb);
/*  874:     */     
/*  875: 895 */     return notaCreditoClienteJaxb;
/*  876:     */   }
/*  877:     */   
/*  878:     */   private NotaDebitoClienteJaxb crearNotaDebitoClienteJaxb(DocumentoElectronico documento, FacturaCliente facturaCliente)
/*  879:     */   {
/*  880: 899 */     NotaDebitoClienteJaxb notaDebitoClienteJaxb = new NotaDebitoClienteJaxb();
/*  881:     */     
/*  882:     */ 
/*  883: 902 */     InfoNDebitoJaxb infoNDebitoJaxb = new InfoNDebitoJaxb();
/*  884: 903 */     infoNDebitoJaxb.setFechaEmision(formatoFecha.format(facturaCliente.getFecha()));
/*  885: 904 */     infoNDebitoJaxb.setDirEstablecimiento(documento.getDireccionSucursal() == null ? "S/N" : documento.getDireccionSucursal());
/*  886: 905 */     String tipIdenComprador = FuncionesUtiles.getTipoIdentificacionClienteSRI(facturaCliente.getEmpresa().getTipoIdentificacion().getCodigo());
/*  887: 906 */     infoNDebitoJaxb.setTipoIdentificacionComprador(tipIdenComprador);
/*  888: 907 */     infoNDebitoJaxb.setRazonSocialComprador(facturaCliente.getEmpresa().getNombreFiscal());
/*  889: 908 */     infoNDebitoJaxb.setIdentificacionComprador(facturaCliente.getEmpresa().getIdentificacion());
/*  890: 909 */     if ((documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente() != null) && 
/*  891: 910 */       (!documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente().equals(""))) {
/*  892: 911 */       infoNDebitoJaxb.setContribuyenteEspecial(documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente());
/*  893:     */     }
/*  894: 913 */     infoNDebitoJaxb.setObligadoContabilidad(documento.getOrganizacion().getOrganizacionConfiguracion().isIndicadorObligadoContabilidad() ? "SI" : "NO");
/*  895:     */     
/*  896:     */ 
/*  897: 916 */     String codigoTipoDocumentoModificado = "01";
/*  898: 917 */     infoNDebitoJaxb.setCodDocModificado(codigoTipoDocumentoModificado);
/*  899:     */     
/*  900: 919 */     String[] cadenaSplit = facturaCliente.getFacturaClientePadre().getNumero().split("-");
/*  901: 920 */     String numeroDocModificado1 = FuncionesUtiles.completarALaIzquierda('0', 3, cadenaSplit[0]);
/*  902: 921 */     String numeroDocModificado2 = FuncionesUtiles.completarALaIzquierda('0', 3, cadenaSplit[1]);
/*  903: 922 */     String numeroDocModificado3 = FuncionesUtiles.completarALaIzquierda('0', 9, cadenaSplit[2]);
/*  904: 923 */     infoNDebitoJaxb.setNumDocModificado(numeroDocModificado1 + "-" + numeroDocModificado2 + "-" + numeroDocModificado3);
/*  905: 924 */     infoNDebitoJaxb.setFechaEmisionDocSustento(formatoFecha.format(facturaCliente.getFacturaClientePadre().getFecha()));
/*  906: 925 */     infoNDebitoJaxb.setTotalSinImpuestos(facturaCliente.getTotal().subtract(facturaCliente.getDescuento()));
/*  907: 926 */     infoNDebitoJaxb.setValorTotal(facturaCliente.getTotal().subtract(facturaCliente.getDescuento()).add(facturaCliente.getImpuesto())
/*  908: 927 */       .subtract(calcularOtrosImpuestos(facturaCliente)).setScale(2, RoundingMode.HALF_UP));
/*  909:     */     
/*  910: 929 */     notaDebitoClienteJaxb.setInfoNotaDebito(infoNDebitoJaxb);
/*  911:     */     
/*  912:     */ 
/*  913: 932 */     MotivosJaxb motivosJaxb = new MotivosJaxb();
/*  914: 933 */     motivosJaxb.setListaMotivo(new ArrayList());
/*  915: 934 */     notaDebitoClienteJaxb.setMotivos(motivosJaxb);
/*  916:     */     
/*  917: 936 */     Map<String, Object[]> mapaImpuestosNotaDebito = new HashMap();
/*  918: 937 */     for (DetalleFacturaCliente detalleFactura : facturaCliente.getListaDetalleFacturaCliente())
/*  919:     */     {
/*  920: 938 */       List<ImpuestoProductoFacturaCliente> listaImpuesto = new ArrayList();
/*  921: 939 */       listaImpuesto.addAll(detalleFactura.getListaImpuestoProductoFacturaCliente());
/*  922:     */       ImpuestoProductoFacturaCliente impuesto;
/*  923: 941 */       if (listaImpuesto.isEmpty())
/*  924:     */       {
/*  925: 943 */         impuesto = new ImpuestoProductoFacturaCliente();
/*  926: 944 */         impuesto.setImpuestoProducto(BigDecimal.ZERO);
/*  927: 945 */         impuesto.setImpuesto(new Impuesto(0, "2", "IVA 0%"));
/*  928: 946 */         impuesto.setPorcentajeImpuesto(BigDecimal.ZERO);
/*  929: 947 */         impuesto.setDetalleFacturaCliente(detalleFactura);
/*  930: 948 */         listaImpuesto.add(impuesto);
/*  931:     */       }
/*  932: 950 */       for (ImpuestoProductoFacturaCliente impuestoProducto : listaImpuesto) {
/*  933: 951 */         if (!impuestoProducto.isEliminado()) {
/*  934: 952 */           if ((impuestoProducto.getImpuesto().getCodigo().equals("2")) || (impuestoProducto.getImpuesto().getCodigo().equals("3")) || 
/*  935: 953 */             (impuestoProducto.getImpuesto().getCodigo().equals("5")))
/*  936:     */           {
/*  937: 954 */             String key = impuestoProducto.getImpuesto().getCodigo() + "-" + impuestoProducto.getPorcentajeImpuesto();
/*  938: 955 */             if (mapaImpuestosNotaDebito.get(key) == null)
/*  939:     */             {
/*  940: 956 */               Object[] detalleMapa = new Object[4];
/*  941: 957 */               detalleMapa[0] = impuestoProducto.getImpuestoProducto();
/*  942: 958 */               if (impuestoProducto.getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES))
/*  943:     */               {
/*  944: 959 */                 BigDecimal cantidadUnidades = detalleFactura.getCantidad();
/*  945: 960 */                 if (detalleFactura.getProducto().getPresentacionProducto() != null) {
/*  946: 961 */                   cantidadUnidades = cantidadUnidades.multiply(detalleFactura.getProducto().getPresentacionProducto()
/*  947: 962 */                     .getCantidadUnidades());
/*  948:     */                 }
/*  949: 964 */                 detalleMapa[1] = cantidadUnidades;
/*  950:     */               }
/*  951: 965 */               else if (impuestoProducto.getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_PRECIO))
/*  952:     */               {
/*  953: 966 */                 detalleMapa[1] = detalleFactura.getBaseImponible();
/*  954:     */               }
/*  955: 968 */               detalleMapa[2] = impuestoProducto.getImpuesto();
/*  956: 969 */               detalleMapa[3] = impuestoProducto.getPorcentajeImpuesto();
/*  957: 970 */               mapaImpuestosNotaDebito.put(key, detalleMapa);
/*  958:     */             }
/*  959:     */             else
/*  960:     */             {
/*  961: 972 */               Object[] detalleMapa = (Object[])mapaImpuestosNotaDebito.get(key);
/*  962:     */               
/*  963: 974 */               BigDecimal baseImponible = ((BigDecimal)detalleMapa[1]).add(detalleFactura.getBaseImponible());
/*  964: 975 */               detalleMapa[1] = baseImponible;
/*  965: 976 */               BigDecimal impuestoValor = ((BigDecimal)detalleMapa[0]).add(impuestoProducto.getImpuestoProducto());
/*  966: 977 */               detalleMapa[0] = impuestoValor;
/*  967:     */             }
/*  968:     */           }
/*  969: 981 */           else if (impuestoProducto.getImpuesto().getCodigo().equals("1"))
/*  970:     */           {
/*  971: 982 */             ImpuestoProductoFacturaCliente impuesto = new ImpuestoProductoFacturaCliente();
/*  972: 983 */             impuesto.setImpuestoProducto(BigDecimal.ZERO);
/*  973: 984 */             impuesto.setImpuesto(new Impuesto(0, "2", "IVA 0%"));
/*  974: 985 */             impuesto.setPorcentajeImpuesto(BigDecimal.ZERO);
/*  975: 986 */             impuesto.setDetalleFacturaCliente(detalleFactura);
/*  976: 989 */             if (mapaImpuestosNotaDebito.get(impuesto.getImpuesto().getCodigo() + "-" + impuesto.getPorcentajeImpuesto()) == null)
/*  977:     */             {
/*  978: 990 */               Object[] detalleMapa = new Object[4];
/*  979: 991 */               detalleMapa[0] = impuesto.getImpuestoProducto();
/*  980: 992 */               if (impuesto.getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES))
/*  981:     */               {
/*  982: 993 */                 BigDecimal cantidadUnidades = detalleFactura.getCantidad();
/*  983: 994 */                 if (detalleFactura.getProducto().getPresentacionProducto() != null) {
/*  984: 995 */                   cantidadUnidades = cantidadUnidades.multiply(detalleFactura.getProducto().getPresentacionProducto()
/*  985: 996 */                     .getCantidadUnidades());
/*  986:     */                 }
/*  987: 998 */                 detalleMapa[1] = cantidadUnidades;
/*  988:     */               }
/*  989: 999 */               else if (impuesto.getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_PRECIO))
/*  990:     */               {
/*  991:1000 */                 detalleMapa[1] = detalleFactura.getBaseImponible();
/*  992:     */               }
/*  993:1002 */               detalleMapa[2] = impuesto.getImpuesto();
/*  994:1003 */               detalleMapa[3] = impuesto.getPorcentajeImpuesto();
/*  995:1004 */               mapaImpuestosNotaDebito.put(impuesto.getImpuesto().getCodigo() + "-" + impuesto.getPorcentajeImpuesto(), detalleMapa);
/*  996:     */             }
/*  997:     */             else
/*  998:     */             {
/*  999:1006 */               Object[] detalleMapa = (Object[])mapaImpuestosNotaDebito.get(impuesto.getImpuesto().getCodigo() + "-" + impuesto
/* 1000:1007 */                 .getPorcentajeImpuesto());
/* 1001:     */               
/* 1002:1009 */               BigDecimal baseImponible = ((BigDecimal)detalleMapa[1]).add(detalleFactura.getBaseImponible());
/* 1003:1010 */               detalleMapa[1] = baseImponible;
/* 1004:1011 */               BigDecimal impuestoValor = ((BigDecimal)detalleMapa[0]).add(impuesto.getImpuestoProducto());
/* 1005:1012 */               detalleMapa[0] = impuestoValor;
/* 1006:     */             }
/* 1007:     */           }
/* 1008:     */         }
/* 1009:     */       }
/* 1010:1019 */       String descripcionProductoDetalle = detalleFactura.getProducto().getNombre();
/* 1011:1020 */       boolean indicadorDescripcionProducto = ParametrosSistema.getXMLFacturacionElectronicaDescripcionProducto(documento.getOrganizacion()
/* 1012:1021 */         .getId()).booleanValue();
/* 1013:1022 */       if ((indicadorDescripcionProducto) && (detalleFactura.getProducto().getDescripcion() != null) && 
/* 1014:1023 */         (!detalleFactura.getProducto().getDescripcion().isEmpty())) {
/* 1015:1024 */         descripcionProductoDetalle = detalleFactura.getProducto().getDescripcion();
/* 1016:     */       }
/* 1017:1026 */       if ((detalleFactura.getDescripcion() != null) && (!detalleFactura.getDescripcion().isEmpty())) {
/* 1018:1027 */         descripcionProductoDetalle = descripcionProductoDetalle + "  --  " + detalleFactura.getDescripcion();
/* 1019:     */       }
/* 1020:1029 */       if (descripcionProductoDetalle.length() > 300) {
/* 1021:1030 */         descripcionProductoDetalle = descripcionProductoDetalle.substring(0, 299);
/* 1022:     */       }
/* 1023:1033 */       MotivoJaxb motivoJaxb = new MotivoJaxb();
/* 1024:1034 */       motivoJaxb.setRazon(descripcionProductoDetalle);
/* 1025:1035 */       BigDecimal valorMotivo = null;
/* 1026:1036 */       if ((detalleFactura.isIndicadorManejoPeso()) && (detalleFactura.getPeso() != null) && 
/* 1027:1037 */         (detalleFactura.getPeso().compareTo(BigDecimal.ZERO) != 0)) {
/* 1028:1038 */         valorMotivo = detalleFactura.getPeso().multiply(detalleFactura.getPrecio()).setScale(2, RoundingMode.HALF_UP);
/* 1029:     */       } else {
/* 1030:1040 */         valorMotivo = detalleFactura.getCantidad().multiply(detalleFactura.getPrecio()).setScale(2, RoundingMode.HALF_UP);
/* 1031:     */       }
/* 1032:1042 */       motivoJaxb.setValor(valorMotivo);
/* 1033:1043 */       motivosJaxb.getListaMotivo().add(motivoJaxb);
/* 1034:     */     }
/* 1035:1046 */     ImpuestosJaxb impuestosJaxb = new ImpuestosJaxb();
/* 1036:1047 */     impuestosJaxb.setListaImpuesto(new ArrayList());
/* 1037:1048 */     infoNDebitoJaxb.setImpuestos(impuestosJaxb);
/* 1038:     */     
/* 1039:1050 */     Iterator<String> it = mapaImpuestosNotaDebito.keySet().iterator();
/* 1040:1051 */     while (it.hasNext())
/* 1041:     */     {
/* 1042:1052 */       String key = (String)it.next();
/* 1043:1053 */       Object[] impuestoProducto = (Object[])mapaImpuestosNotaDebito.get(key);
/* 1044:     */       
/* 1045:1055 */       ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/* 1046:1056 */       impuestoJaxb.setCodigo(((Impuesto)impuestoProducto[2]).getCodigo());
/* 1047:1057 */       double porcentaje = ((BigDecimal)impuestoProducto[3]).doubleValue();
/* 1048:1058 */       impuestoJaxb.setCodigoPorcentaje(Integer.valueOf(Integer.parseInt(FuncionesUtiles.getCodigoImpuesto(porcentaje))));
/* 1049:1059 */       impuestoJaxb.setTarifa(((BigDecimal)impuestoProducto[3]).setScale(2, RoundingMode.HALF_UP));
/* 1050:1060 */       impuestoJaxb.setBaseImponible(((BigDecimal)impuestoProducto[1]).setScale(2, RoundingMode.HALF_UP));
/* 1051:1061 */       impuestoJaxb.setValor(((BigDecimal)impuestoProducto[0]).setScale(2, RoundingMode.HALF_UP));
/* 1052:1062 */       impuestosJaxb.getListaImpuesto().add(impuestoJaxb);
/* 1053:     */     }
/* 1054:1066 */     if (facturaCliente.getDescuentoImpuesto().compareTo(BigDecimal.ZERO) > 0)
/* 1055:     */     {
/* 1056:1067 */       CompensacionesJaxb compensacionesJaxb = crearCompensacionesJaxb(documento, facturaCliente);
/* 1057:1068 */       infoNDebitoJaxb.setCompensacionesJaxb(compensacionesJaxb);
/* 1058:     */     }
/* 1059:1071 */     return notaDebitoClienteJaxb;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   private InfoAdicionalJaxb agregarInformacionAdicional(InfoAdicionalJaxb infoAdicionalJaxb, String nombreCampo, String valorCampo)
/* 1063:     */   {
/* 1064:1075 */     if (infoAdicionalJaxb == null)
/* 1065:     */     {
/* 1066:1076 */       infoAdicionalJaxb = new InfoAdicionalJaxb();
/* 1067:1077 */       infoAdicionalJaxb.setListaCampoAdicional(new ArrayList());
/* 1068:     */     }
/* 1069:1080 */     CampoAdicionalJaxb campoAdicionalJaxb = new CampoAdicionalJaxb();
/* 1070:1081 */     campoAdicionalJaxb.setNombre(nombreCampo);
/* 1071:1082 */     campoAdicionalJaxb.setValor(valorCampo);
/* 1072:     */     
/* 1073:1084 */     infoAdicionalJaxb.getlListaCampoAdicional().add(campoAdicionalJaxb);
/* 1074:     */     
/* 1075:1086 */     return infoAdicionalJaxb;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   private DetalleAdicionalJaxb agregarDetalleAdicional(DetalleAdicionalJaxb detalleAdicionalJaxb, String nombreCampo, String valorCampo)
/* 1079:     */   {
/* 1080:1090 */     if (detalleAdicionalJaxb == null)
/* 1081:     */     {
/* 1082:1091 */       detalleAdicionalJaxb = new DetalleAdicionalJaxb();
/* 1083:1092 */       detalleAdicionalJaxb.setListaDetAdicional(new ArrayList());
/* 1084:     */     }
/* 1085:1095 */     DetAdicionalJaxb detAdicionalJaxb = new DetAdicionalJaxb();
/* 1086:1096 */     detAdicionalJaxb.setNombre(nombreCampo);
/* 1087:1097 */     detAdicionalJaxb.setValor(valorCampo);
/* 1088:     */     
/* 1089:1099 */     detalleAdicionalJaxb.getListaDetAdicional().add(detAdicionalJaxb);
/* 1090:     */     
/* 1091:1101 */     return detalleAdicionalJaxb;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   private double getPorcentajeImpuesto(FacturaClienteSRI facturaClienteSRI)
/* 1095:     */   {
/* 1096:1107 */     double porcentaje = facturaClienteSRI.getMontoIva().divide(facturaClienteSRI.getBaseImponibleDiferenteCero(), 10, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP).doubleValue();
/* 1097:     */     
/* 1098:1109 */     return porcentaje;
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   private BigDecimal calcularOtrosImpuestos(FacturaCliente facturaCliente)
/* 1102:     */   {
/* 1103:1113 */     BigDecimal otrosImpuestos = BigDecimal.ZERO;
/* 1104:1114 */     for (DetalleFacturaCliente detalleFactura : facturaCliente.getListaDetalleFacturaCliente()) {
/* 1105:1115 */       for (ImpuestoProductoFacturaCliente impuestoProducto : detalleFactura.getListaImpuestoProductoFacturaCliente()) {
/* 1106:1116 */         if ((!impuestoProducto.isEliminado()) && 
/* 1107:1117 */           (!impuestoProducto.getImpuesto().getCodigo().equals("2")) && (!impuestoProducto.getImpuesto().getCodigo().equals("3")) && 
/* 1108:1118 */           (!impuestoProducto.getImpuesto().getCodigo().equals("5"))) {
/* 1109:1119 */           otrosImpuestos = otrosImpuestos.add(impuestoProducto.getImpuestoProducto());
/* 1110:     */         }
/* 1111:     */       }
/* 1112:     */     }
/* 1113:1124 */     return otrosImpuestos;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public Integer numeroPlazo(FacturaCliente facturaCliente)
/* 1117:     */   {
/* 1118:1128 */     int numeroPlazo = facturaCliente.getCondicionPago().getDiasPlazo();
/* 1119:1129 */     if (numeroPlazo == 0) {
/* 1120:1130 */       numeroPlazo = facturaCliente.getCondicionPago().getMesesPlazo();
/* 1121:     */     }
/* 1122:1132 */     return Integer.valueOf(numeroPlazo);
/* 1123:     */   }
/* 1124:     */   
/* 1125:     */   public String unidadTiempo(FacturaCliente facturaCliente)
/* 1126:     */   {
/* 1127:1136 */     String unidadTiempo = "dias";
/* 1128:1137 */     if ((facturaCliente.getCondicionPago().getDiasPlazo() == 0) && (facturaCliente.getCondicionPago().getMesesPlazo() != 0)) {
/* 1129:1138 */       unidadTiempo = "meses";
/* 1130:     */     }
/* 1131:1140 */     return unidadTiempo;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public Map<String, informacionICE> detalleFacturaClienteImpuestoIce(List<DetalleFacturaCliente> listaDFC)
/* 1135:     */   {
/* 1136:1145 */     Map<String, informacionICE> mapaDetalleFacturaCliente = new HashMap();
/* 1137:1146 */     for (DetalleFacturaCliente detalleFactura : listaDFC)
/* 1138:     */     {
/* 1139:1147 */       String clave = detalleFactura.getCodigoIce();
/* 1140:1148 */       if ((clave != null) && (!clave.isEmpty()))
/* 1141:     */       {
/* 1142:1149 */         informacionICE iICE = new informacionICE();
/* 1143:1150 */         iICE.setCodigo(clave);
/* 1144:1152 */         if (mapaDetalleFacturaCliente.get(clave) != null)
/* 1145:     */         {
/* 1146:1153 */           ((informacionICE)mapaDetalleFacturaCliente.get(clave)).setTotalBaseImponible(
/* 1147:1154 */             ((informacionICE)mapaDetalleFacturaCliente.get(clave)).getTotalBaseImponible()
/* 1148:1155 */             .add(detalleFactura.getBaseImponible().subtract(detalleFactura.getIceLinea())));
/* 1149:1156 */           ((informacionICE)mapaDetalleFacturaCliente.get(clave)).setTotalIce(
/* 1150:1157 */             ((informacionICE)mapaDetalleFacturaCliente.get(clave)).getTotalIce().add(detalleFactura.getIceLinea()));
/* 1151:     */         }
/* 1152:     */         else
/* 1153:     */         {
/* 1154:1160 */           iICE.setTotalBaseImponible(detalleFactura.getBaseImponible().subtract(detalleFactura.getIceLinea()));
/* 1155:1161 */           iICE.setTotalIce(detalleFactura.getIceLinea());
/* 1156:1162 */           mapaDetalleFacturaCliente.put(clave, iICE);
/* 1157:     */         }
/* 1158:     */       }
/* 1159:     */     }
/* 1160:1167 */     return mapaDetalleFacturaCliente;
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   public class informacionICE
/* 1164:     */   {
/* 1165:1171 */     String codigo = "";
/* 1166:1172 */     BigDecimal totalBaseImponible = BigDecimal.ZERO;
/* 1167:1173 */     BigDecimal totalIce = BigDecimal.ZERO;
/* 1168:     */     
/* 1169:     */     public informacionICE() {}
/* 1170:     */     
/* 1171:     */     public String getCodigo()
/* 1172:     */     {
/* 1173:1179 */       return this.codigo;
/* 1174:     */     }
/* 1175:     */     
/* 1176:     */     public void setCodigo(String codigo)
/* 1177:     */     {
/* 1178:1183 */       this.codigo = codigo;
/* 1179:     */     }
/* 1180:     */     
/* 1181:     */     public BigDecimal getTotalBaseImponible()
/* 1182:     */     {
/* 1183:1187 */       return this.totalBaseImponible;
/* 1184:     */     }
/* 1185:     */     
/* 1186:     */     public void setTotalBaseImponible(BigDecimal totalBaseImponible)
/* 1187:     */     {
/* 1188:1191 */       this.totalBaseImponible = totalBaseImponible;
/* 1189:     */     }
/* 1190:     */     
/* 1191:     */     public BigDecimal getTotalIce()
/* 1192:     */     {
/* 1193:1195 */       return this.totalIce;
/* 1194:     */     }
/* 1195:     */     
/* 1196:     */     public void setTotalIce(BigDecimal totalIce)
/* 1197:     */     {
/* 1198:1199 */       this.totalIce = totalIce;
/* 1199:     */     }
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public class impuestoCampoAdicional
/* 1203:     */   {
/* 1204:     */     public String nombreImpuesto;
/* 1205:     */     public String codigoImpuesto;
/* 1206:1208 */     public BigDecimal tarifa = BigDecimal.ZERO;
/* 1207:1209 */     public BigDecimal baseImponible = BigDecimal.ZERO;
/* 1208:1210 */     public BigDecimal valor = BigDecimal.ZERO;
/* 1209:     */     
/* 1210:     */     public impuestoCampoAdicional(String nombreImpuesto, String codigoImpuesto, BigDecimal tarifa, BigDecimal baseImponible, BigDecimal valor)
/* 1211:     */     {
/* 1212:1213 */       this.nombreImpuesto = nombreImpuesto;
/* 1213:1214 */       this.codigoImpuesto = codigoImpuesto;
/* 1214:1215 */       this.tarifa = tarifa;
/* 1215:1216 */       this.baseImponible = baseImponible;
/* 1216:1217 */       this.valor = valor;
/* 1217:     */     }
/* 1218:     */     
/* 1219:     */     public String getNombreImpuesto()
/* 1220:     */     {
/* 1221:1221 */       return this.nombreImpuesto;
/* 1222:     */     }
/* 1223:     */     
/* 1224:     */     public void setNombreImpuesto(String nombreImpuesto)
/* 1225:     */     {
/* 1226:1225 */       this.nombreImpuesto = nombreImpuesto;
/* 1227:     */     }
/* 1228:     */     
/* 1229:     */     public String getCodigoImpuesto()
/* 1230:     */     {
/* 1231:1229 */       return this.codigoImpuesto;
/* 1232:     */     }
/* 1233:     */     
/* 1234:     */     public void setCodigoImpuesto(String codigoImpuesto)
/* 1235:     */     {
/* 1236:1233 */       this.codigoImpuesto = codigoImpuesto;
/* 1237:     */     }
/* 1238:     */     
/* 1239:     */     public BigDecimal getTarifa()
/* 1240:     */     {
/* 1241:1237 */       return this.tarifa;
/* 1242:     */     }
/* 1243:     */     
/* 1244:     */     public void setTarifa(BigDecimal tarifa)
/* 1245:     */     {
/* 1246:1241 */       this.tarifa = tarifa;
/* 1247:     */     }
/* 1248:     */     
/* 1249:     */     public BigDecimal getBaseImponible()
/* 1250:     */     {
/* 1251:1245 */       return this.baseImponible;
/* 1252:     */     }
/* 1253:     */     
/* 1254:     */     public void setBaseImponible(BigDecimal baseImponible)
/* 1255:     */     {
/* 1256:1249 */       this.baseImponible = baseImponible;
/* 1257:     */     }
/* 1258:     */     
/* 1259:     */     public BigDecimal getValor()
/* 1260:     */     {
/* 1261:1253 */       return this.valor;
/* 1262:     */     }
/* 1263:     */     
/* 1264:     */     public void setValor(BigDecimal valor)
/* 1265:     */     {
/* 1266:1257 */       this.valor = valor;
/* 1267:     */     }
/* 1268:     */   }
/* 1269:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.servicio.impl.ServicioFacturaClienteSRIXMLImpl
 * JD-Core Version:    0.7.0.1
 */