/*    1:     */ package com.asinfo.as2.ventas.procesos.aerolineas;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*    4:     */ import com.asinfo.as2.controller.LanguageController;
/*    5:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    6:     */ import com.asinfo.as2.dao.GenericoDao;
/*    7:     */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   11:     */ import com.asinfo.as2.entities.Asiento;
/*   12:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   13:     */ import com.asinfo.as2.entities.Documento;
/*   14:     */ import com.asinfo.as2.entities.Empresa;
/*   15:     */ import com.asinfo.as2.entities.Organizacion;
/*   16:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   17:     */ import com.asinfo.as2.entities.Sucursal;
/*   18:     */ import com.asinfo.as2.entities.aerolineas.CargaArchivo;
/*   19:     */ import com.asinfo.as2.entities.aerolineas.DetalleTicket;
/*   20:     */ import com.asinfo.as2.entities.aerolineas.Ticket;
/*   21:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   22:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   23:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   24:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   25:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   26:     */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*   27:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   28:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   29:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   30:     */ import com.asinfo.as2.util.AppUtil;
/*   31:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   32:     */ import com.asinfo.as2.utils.JsfUtil;
/*   33:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   34:     */ import com.asinfo.as2.ventas.procesos.aerolineas.servicio.ServicioVentaTicket;
/*   35:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   36:     */ import java.io.BufferedInputStream;
/*   37:     */ import java.io.BufferedReader;
/*   38:     */ import java.io.File;
/*   39:     */ import java.io.FileOutputStream;
/*   40:     */ import java.io.FileReader;
/*   41:     */ import java.io.IOException;
/*   42:     */ import java.io.InputStream;
/*   43:     */ import java.io.PrintStream;
/*   44:     */ import java.math.BigDecimal;
/*   45:     */ import java.math.RoundingMode;
/*   46:     */ import java.text.ParseException;
/*   47:     */ import java.text.SimpleDateFormat;
/*   48:     */ import java.util.ArrayList;
/*   49:     */ import java.util.Collection;
/*   50:     */ import java.util.Date;
/*   51:     */ import java.util.HashMap;
/*   52:     */ import java.util.Iterator;
/*   53:     */ import java.util.List;
/*   54:     */ import java.util.Map;
/*   55:     */ import java.util.Map.Entry;
/*   56:     */ import javax.annotation.PostConstruct;
/*   57:     */ import javax.ejb.EJB;
/*   58:     */ import javax.faces.bean.ManagedBean;
/*   59:     */ import javax.faces.bean.ViewScoped;
/*   60:     */ import javax.faces.context.FacesContext;
/*   61:     */ import javax.faces.context.PartialViewContext;
/*   62:     */ import javax.faces.model.SelectItem;
/*   63:     */ import org.apache.log4j.Logger;
/*   64:     */ import org.primefaces.component.datatable.DataTable;
/*   65:     */ import org.primefaces.context.RequestContext;
/*   66:     */ import org.primefaces.event.FileUploadEvent;
/*   67:     */ import org.primefaces.model.LazyDataModel;
/*   68:     */ import org.primefaces.model.SortOrder;
/*   69:     */ import org.primefaces.model.UploadedFile;
/*   70:     */ 
/*   71:     */ @ManagedBean
/*   72:     */ @ViewScoped
/*   73:     */ public class CargaVentaTicketBean
/*   74:     */   extends PageControllerAS2
/*   75:     */ {
/*   76:     */   static final long serialVersionUID = 1L;
/*   77:     */   @EJB
/*   78:     */   private ServicioAsiento servicioAsiento;
/*   79:     */   @EJB
/*   80:     */   private ServicioDocumento servicioDocumento;
/*   81:     */   @EJB
/*   82:     */   private ServicioVentaTicket servicioBSP;
/*   83:     */   @EJB
/*   84:     */   private ServicioSecuencia servicioSecuencia;
/*   85:     */   @EJB
/*   86:     */   private ServicioVentaTicket servicioCargaBSP;
/*   87:     */   @EJB
/*   88:     */   private ServicioProducto servicioProducto;
/*   89:     */   @EJB
/*   90:     */   private ServicioGenerico<Ticket> servicioTickets;
/*   91:     */   @EJB
/*   92:     */   private ServicioEmpresa servicioEmpresa;
/*   93:     */   @EJB
/*   94:     */   private ServicioFacturaCliente servicioFacturaCliente;
/*   95:     */   @EJB
/*   96:     */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*   97:     */   @EJB
/*   98:     */   private ServicioMigracion servicioMigracion;
/*   99:     */   @EJB
/*  100:     */   private ServicioGenerico<Ticket> servicioTicket;
/*  101:     */   @EJB
/*  102:     */   private ServicioGenerico<DetalleTicket> servicioDetalleTicket;
/*  103:     */   @EJB
/*  104:     */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  105:     */   @EJB
/*  106:     */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  107:     */   @EJB
/*  108:     */   private GenericoDao<CargaArchivo> cargaArchivoDao;
/*  109:     */   private List<Ticket> listTickets;
/*  110:     */   private CargaArchivo bsp;
/*  111:     */   private LazyDataModel<CargaArchivo> listaBSP;
/*  112:     */   private DataTable dtBSP;
/*  113:     */   private List<DetalleAsiento> listaDetalleAsiento;
/*  114:     */   private boolean respaldo;
/*  115:     */   
/*  116:     */   static enum TipoCarga
/*  117:     */   {
/*  118:  70 */     BSP("BSP"),  VENTAS_LOCALES("Ventas locales");
/*  119:     */     
/*  120:     */     private String nombre;
/*  121:     */     
/*  122:     */     private TipoCarga(String nombre)
/*  123:     */     {
/*  124:  78 */       this.nombre = nombre;
/*  125:     */     }
/*  126:     */     
/*  127:     */     public String getNombre()
/*  128:     */     {
/*  129:  87 */       return this.nombre;
/*  130:     */     }
/*  131:     */     
/*  132:     */     public void setNombre(String nombre)
/*  133:     */     {
/*  134:  91 */       this.nombre = nombre;
/*  135:     */     }
/*  136:     */   }
/*  137:     */   
/*  138: 142 */   private boolean render = true;
/*  139: 143 */   private String referenciaArchivos = "";
/*  140:     */   private List<DetalleTicket> listaBfh;
/*  141:     */   private List<DetalleTicket> listaBch;
/*  142:     */   private List<DetalleTicket> listaBoh;
/*  143:     */   private List<DetalleTicket> listaBkt;
/*  144:     */   private List<DetalleTicket> listaBks;
/*  145:     */   private List<DetalleTicket> listaBki;
/*  146:     */   private List<DetalleTicket> listaBar;
/*  147:     */   private List<DetalleTicket> listaBmp;
/*  148:     */   private List<DetalleTicket> listaBkf;
/*  149:     */   private List<DetalleTicket> listaBkp;
/*  150:     */   private List<DetalleTicket> listaBot;
/*  151:     */   private List<DetalleTicket> listaBct;
/*  152:     */   private List<DetalleTicket> listaBft;
/*  153:     */   private List<DetalleTicket> listaBmd;
/*  154:     */   private List<Ticket> listaTicket;
/*  155:     */   private List<Ticket> listaPagos;
/*  156:     */   private HashMap<String, Ticket> hmTicket;
/*  157:     */   private HashMap<String, Ticket> hmTicketBot;
/*  158:     */   private List<Documento> listaDocumentoAerolinea;
/*  159: 171 */   private BigDecimal totalComision = BigDecimal.ZERO;
/*  160: 172 */   private BigDecimal totalIVA = BigDecimal.ZERO;
/*  161: 173 */   private BigDecimal totalTarifa = BigDecimal.ZERO;
/*  162: 174 */   private BigDecimal totalImpuestoExtranjero = BigDecimal.ZERO;
/*  163: 175 */   private BigDecimal totalIvaComision = BigDecimal.ZERO;
/*  164: 177 */   private BigDecimal totalRetencionFuente = BigDecimal.ZERO;
/*  165: 178 */   private BigDecimal totalWT = BigDecimal.ZERO;
/*  166: 179 */   private BigDecimal totalED = BigDecimal.ZERO;
/*  167: 180 */   private BigDecimal totalYQ = BigDecimal.ZERO;
/*  168: 181 */   private BigDecimal totalQB = BigDecimal.ZERO;
/*  169: 182 */   private BigDecimal totalQI = BigDecimal.ZERO;
/*  170: 183 */   private BigDecimal totalYR = BigDecimal.ZERO;
/*  171: 184 */   private BigDecimal totalE2 = BigDecimal.ZERO;
/*  172: 185 */   private BigDecimal totalCredito = BigDecimal.ZERO;
/*  173: 187 */   private BigDecimal totalPenalty = BigDecimal.ZERO;
/*  174: 189 */   private TipoCarga tipoCarga = TipoCarga.BSP;
/*  175: 191 */   private BigDecimal totalDebitoAsi = BigDecimal.ZERO;
/*  176: 192 */   private BigDecimal totalCreditoAsi = BigDecimal.ZERO;
/*  177:     */   private List<Ticket> listaTicketNoEditados;
/*  178:     */   private CargaArchivo bspParaEdicion;
/*  179:     */   
/*  180:     */   @PostConstruct
/*  181:     */   public void init()
/*  182:     */   {
/*  183: 199 */     this.listaBSP = new LazyDataModel()
/*  184:     */     {
/*  185:     */       private static final long serialVersionUID = 1L;
/*  186:     */       
/*  187:     */       public List<CargaArchivo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  188:     */       {
/*  189: 206 */         List<CargaArchivo> lista = new ArrayList();
/*  190: 207 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  191: 208 */         lista = CargaVentaTicketBean.this.servicioBSP.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  192: 209 */         CargaVentaTicketBean.this.listaBSP.setRowCount(CargaVentaTicketBean.this.servicioBSP.contarPorCriterio(filters));
/*  193:     */         
/*  194: 211 */         return lista;
/*  195:     */       }
/*  196:     */     };
/*  197:     */   }
/*  198:     */   
/*  199:     */   public String limpiar()
/*  200:     */   {
/*  201: 219 */     this.bsp = new CargaArchivo();
/*  202: 220 */     this.bsp.setEditado(true);
/*  203: 221 */     this.bsp.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  204: 222 */     this.bsp.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  205: 223 */     this.bsp.setEstado(Estado.ELABORADO);
/*  206: 224 */     this.bsp.setFecha(new Date());
/*  207: 226 */     for (Documento d : getListaDocumentoAerolinea()) {
/*  208: 227 */       if (d.isPredeterminado()) {
/*  209: 228 */         this.bsp.setDocumento(d);
/*  210:     */       }
/*  211:     */     }
/*  212: 232 */     this.listaBfh = null;
/*  213: 233 */     this.listaBch = null;
/*  214: 234 */     this.listaBoh = null;
/*  215: 235 */     this.listaBkt = null;
/*  216: 236 */     this.listaBks = null;
/*  217: 237 */     this.listaBki = null;
/*  218: 238 */     this.listaBar = null;
/*  219: 239 */     this.listaBmp = null;
/*  220: 240 */     this.listaBkf = null;
/*  221: 241 */     this.listaBkp = null;
/*  222: 242 */     this.listaBot = null;
/*  223: 243 */     this.listaBct = null;
/*  224: 244 */     this.listaBft = null;
/*  225: 245 */     this.referenciaArchivos = null;
/*  226: 246 */     this.listTickets = new ArrayList();
/*  227: 247 */     return null;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public void cambioNombreImpuesto(DetalleTicket dt)
/*  231:     */   {
/*  232: 252 */     dt.setTaxMiscFeeType1(dt.getTaxMiscFeeType1().replace(" ", ""));
/*  233: 253 */     if ((!dt.getTaxMiscFeeType1().equals("WT")) && (!dt.getTaxMiscFeeType1().equals("ED")) && (!dt.getTaxMiscFeeType1().equals("YQ")) && 
/*  234: 254 */       (!dt.getTaxMiscFeeType1().equals("QB")) && (!dt.getTaxMiscFeeType1().equals("QI")) && (!dt.getTaxMiscFeeType1().equals("YR")) && 
/*  235: 255 */       (!dt.getTaxMiscFeeType1().equals("E2")) && (!dt.getTaxMiscFeeType1().equals("EC")) && (!dt.getTaxMiscFeeType1().equals("VATEC")) && 
/*  236: 256 */       (!dt.getTaxMiscFeeType1().equals("EC1")) && (!dt.getTaxMiscFeeType1().equals("MF")) && (!dt.getTaxMiscFeeType1().equals("CP"))) {
/*  237: 257 */       dt.setTaxMiscFeeType1("XT");
/*  238:     */     }
/*  239:     */   }
/*  240:     */   
/*  241:     */   public String migrarBSP(FileUploadEvent event)
/*  242:     */     throws IOException, ExcepcionAS2
/*  243:     */   {
/*  244: 264 */     HashMap<String, DetalleTicket> hmDetalleTicket = new HashMap();
/*  245:     */     
/*  246:     */ 
/*  247:     */ 
/*  248: 268 */     String uploadDir = ParametrosSistema.getAS2_HOME(1) + File.separator + "documentos" + File.separator + "";
/*  249:     */     
/*  250: 270 */     File directorio = new File(uploadDir);
/*  251: 271 */     String fileName = event.getFile().getFileName();
/*  252:     */     
/*  253: 273 */     File file = new File(uploadDir + fileName);
/*  254: 275 */     if (!directorio.exists()) {
/*  255: 276 */       directorio.mkdirs();
/*  256:     */     }
/*  257: 278 */     InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  258:     */     
/*  259: 280 */     FileOutputStream output = new FileOutputStream(file);
/*  260: 281 */     while (input.available() != 0) {
/*  261: 282 */       output.write(input.read());
/*  262:     */     }
/*  263: 285 */     FileReader f = new FileReader(file.getPath());
/*  264: 286 */     BufferedReader b = new BufferedReader(f);
/*  265:     */     
/*  266: 288 */     String numeroTicket = "";
/*  267:     */     String cadena;
/*  268: 290 */     while ((cadena = b.readLine()) != null)
/*  269:     */     {
/*  270: 292 */       if ((cadena.substring(0, 3).equals("BFH")) && (cadena.substring(11, 13).equals("01")))
/*  271:     */       {
/*  272: 294 */         DetalleTicket bfh = new DetalleTicket();
/*  273:     */         
/*  274: 296 */         bfh.setStdMsgId(cadena.substring(0, 3));
/*  275: 297 */         bfh.setSeqNum(cadena.substring(3, 11));
/*  276: 298 */         bfh.setStdNumQual(cadena.substring(11, 13));
/*  277: 299 */         bfh.setBspId(cadena.substring(13, 16));
/*  278: 300 */         bfh.setTktngAlnCdNum(cadena.substring(16, 19));
/*  279: 301 */         bfh.setHandbookRevisionNum(cadena.substring(19, 22));
/*  280: 302 */         bfh.setTestProd(cadena.substring(22, 26));
/*  281: 303 */         bfh.setProcDate(stringAFecha(cadena.substring(26, 32)));
/*  282: 304 */         bfh.setProcTime(cadena.substring(32, 36));
/*  283: 305 */         bfh.setIsoCntryCd(cadena.substring(36, 38));
/*  284: 306 */         bfh.setFileSeqNum(cadena.substring(38, 44));
/*  285:     */         
/*  286: 308 */         getListaBfh().add(bfh);
/*  287:     */       }
/*  288: 312 */       if ((cadena.substring(0, 3).equals("BCH")) && (cadena.substring(11, 13).equals("02")))
/*  289:     */       {
/*  290: 313 */         DetalleTicket bch = new DetalleTicket();
/*  291: 314 */         bch.setStdMsgId(cadena.substring(0, 3));
/*  292: 315 */         bch.setSeqNum(cadena.substring(3, 11));
/*  293: 316 */         bch.setStdNumQual(cadena.substring(11, 13));
/*  294: 317 */         bch.setProcDateId(cadena.substring(13, 16));
/*  295: 318 */         bch.setProcCycleId(cadena.substring(16, 17));
/*  296: 319 */         bch.setBillAnalEndDate(stringAFecha(cadena.substring(17, 23)));
/*  297: 320 */         bch.setDynamicRunId(cadena.substring(23, 24));
/*  298: 321 */         getListaBch().add(bch);
/*  299:     */       }
/*  300: 323 */       if ((cadena.substring(0, 3).equals("BOH")) && (cadena.substring(11, 13).equals("03")))
/*  301:     */       {
/*  302: 324 */         DetalleTicket boh = new DetalleTicket();
/*  303:     */         
/*  304: 326 */         boh.setStdMsgId(cadena.substring(0, 3));
/*  305: 327 */         boh.setSeqNum(cadena.substring(3, 11));
/*  306: 328 */         boh.setStdNumQual(cadena.substring(11, 13));
/*  307: 329 */         boh.setAgentNumCd(cadena.substring(13, 21));
/*  308: 330 */         boh.setRemtPedEndDate(stringAFecha(cadena.substring(21, 27)));
/*  309: 331 */         boh.setCurrType(cadena.substring(27, 31));
/*  310: 332 */         boh.setMultiLocIdentifer(cadena.substring(31, 34));
/*  311: 333 */         getListaBoh().add(boh);
/*  312:     */       }
/*  313: 336 */       if ((cadena.substring(0, 3).equals("BKT")) && (cadena.substring(11, 13).equals("06")))
/*  314:     */       {
/*  315: 338 */         DetalleTicket bkt = new DetalleTicket();
/*  316: 339 */         bkt.setFileDefnId("");
/*  317: 340 */         bkt.setStdMsgId(cadena.substring(0, 3));
/*  318: 341 */         bkt.setSeqNum(cadena.substring(3, 11));
/*  319: 342 */         bkt.setStdNumQual(cadena.substring(11, 13));
/*  320: 343 */         bkt.setTransNum("");
/*  321: 344 */         bkt.setOrgnlTransNum(cadena.substring(13, 19));
/*  322: 345 */         bkt.setNetRptgInd(cadena.substring(19, 21));
/*  323: 346 */         bkt.setTransRcrdCnt(cadena.substring(21, 24));
/*  324: 347 */         bkt.setTktngAlnCdNum(cadena.substring(24, 27));
/*  325: 348 */         bkt.setComcAgmtRef(cadena.substring(27, 37));
/*  326: 349 */         bkt.setCustFileRef(cadena.substring(37, 64));
/*  327: 350 */         bkt.setRptgSysId(cadena.substring(64, 68));
/*  328: 351 */         bkt.setSttlAuthCd(cadena.substring(68, 82));
/*  329: 352 */         bkt.setDataInputStatInd(cadena.substring(82, 83));
/*  330: 353 */         bkt.setAutoRepriceInd(cadena.substring(85, 86));
/*  331:     */         
/*  332: 355 */         getListaBkt().add(bkt);
/*  333:     */       }
/*  334: 357 */       if ((cadena.substring(0, 3).equals("BKS")) && (cadena.substring(11, 13).equals("24")))
/*  335:     */       {
/*  336: 359 */         DetalleTicket bks = new DetalleTicket();
/*  337: 360 */         bks.setStdMsgId(cadena.substring(0, 3));
/*  338: 361 */         bks.setSeqNum(cadena.substring(3, 11));
/*  339: 362 */         bks.setStdNumQual(cadena.substring(11, 13));
/*  340: 363 */         bks.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  341: 364 */         bks.setTransNum("");
/*  342: 365 */         bks.setOrgnlTransNum(cadena.substring(19, 25));
/*  343: 366 */         bks.setTktDocNum(cadena.substring(25, 39));
/*  344: 367 */         bks.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  345: 368 */         bks.setCpnUseInd(cadena.substring(40, 44));
/*  346: 369 */         bks.setConjTktInd(cadena.substring(44, 47));
/*  347: 370 */         bks.setRsnForIssuCd(cadena.substring(55, 56));
/*  348: 371 */         bks.setTourCd(cadena.substring(56, 71));
/*  349: 372 */         bks.setTransCd(cadena.substring(71, 75));
/*  350: 373 */         bks.setTrueOrigDestCityCd(cadena.substring(75, 85));
/*  351: 374 */         bks.setPnrRefAndOrAlnData(cadena.substring(85, 98));
/*  352: 375 */         bks.setNumeroAerolinea(cadena.substring(47, 55));
/*  353: 376 */         bks.setTimeOfIssue(cadena.substring(98, 102));
/*  354: 377 */         getListaBks().add(bks);
/*  355:     */         
/*  356: 379 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  357: 380 */         if (ticket == null)
/*  358:     */         {
/*  359: 381 */           ticket = new Ticket();
/*  360: 382 */           ticket.setNumero(bks.getTktDocNum());
/*  361: 383 */           ticket.setAerolinea(bks.getNumeroAerolinea());
/*  362: 384 */           ticket.setFecha(bks.getDateOfIssue());
/*  363: 385 */           ticket.setTipo(bks.getCpnUseInd());
/*  364: 386 */           ticket.setOperacion(bks.getTransCd());
/*  365: 387 */           ticket.setMoneda(bks.getCurrType());
/*  366: 388 */           ticket.setBsp(getBsp());
/*  367: 389 */           ticket.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  368: 390 */           ticket.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  369: 391 */           ticket.setFechaReporte(new Date());
/*  370:     */           
/*  371: 393 */           ticket.getListaDetalleTicket().add(bks);
/*  372: 394 */           getHmTicket().put(cadena.substring(25, 39), ticket);
/*  373: 395 */           getBsp().getListaTicket().add(ticket);
/*  374:     */         }
/*  375: 398 */         bks.setTicket(ticket);
/*  376: 400 */         if (numeroTicket.equals(cadena.substring(25, 39))) {
/*  377: 401 */           numeroTicket = cadena.substring(25, 39);
/*  378:     */         } else {
/*  379: 403 */           numeroTicket = cadena.substring(25, 39);
/*  380:     */         }
/*  381:     */       }
/*  382: 407 */       if ((cadena.substring(0, 3).equals("BKS")) && (cadena.substring(11, 13).equals("30")))
/*  383:     */       {
/*  384: 408 */         DetalleTicket bks1 = new DetalleTicket();
/*  385:     */         
/*  386: 410 */         bks1.setStdMsgId(cadena.substring(0, 3));
/*  387: 411 */         bks1.setSeqNum(cadena.substring(3, 11));
/*  388: 412 */         bks1.setStdNumQual(cadena.substring(11, 13));
/*  389: 413 */         bks1.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  390: 414 */         bks1.setTransNum("");
/*  391: 415 */         bks1.setOrgnlTransNum(cadena.substring(19, 25));
/*  392: 416 */         bks1.setTktDocNum(cadena.substring(25, 39));
/*  393: 417 */         bks1.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  394: 418 */         bks1.setCommAmt(new BigDecimal(numeroLetra(cadena.substring(40, 51))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  395: 419 */         bks1.setNetFareAmt(new BigDecimal(numeroLetra(cadena.substring(51, 62))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  396: 420 */         bks1.setTaxMiscFeeType1(cadena.substring(62, 70));
/*  397: 421 */         cambioNombreImpuesto(bks1);
/*  398: 422 */         bks1.setTaxMiscFeeAmt1(new BigDecimal(numeroLetra(cadena.substring(70, 81))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  399: 423 */         bks1.setTicketDocAmt(new BigDecimal(numeroLetra(cadena.substring(119, 130))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  400: 424 */         bks1.setCurrType(cadena.substring(132, 136));
/*  401: 425 */         asignarClaseImpuesto(bks1);
/*  402:     */         
/*  403: 427 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  404: 428 */         if (ticket != null)
/*  405:     */         {
/*  406: 429 */           bks1.setTicket(ticket);
/*  407: 430 */           DetalleTicket dt1 = (DetalleTicket)hmDetalleTicket.get(ticket.getNumero() + "¬" + bks1.getTaxMiscFeeType1());
/*  408: 431 */           if (dt1 != null)
/*  409:     */           {
/*  410: 432 */             dt1.setTaxMiscFeeAmt1(dt1.getTaxMiscFeeAmt1().add(bks1.getTaxMiscFeeAmt1()));
/*  411:     */           }
/*  412:     */           else
/*  413:     */           {
/*  414: 434 */             ticket.getListaDetalleTicket().add(bks1);
/*  415: 435 */             hmDetalleTicket.put(ticket.getNumero() + "¬" + bks1.getTaxMiscFeeType1(), bks1);
/*  416:     */           }
/*  417:     */         }
/*  418:     */         else
/*  419:     */         {
/*  420: 438 */           getListaBks().add(bks1);
/*  421:     */         }
/*  422: 441 */         DetalleTicket bks2 = new DetalleTicket();
/*  423:     */         
/*  424: 443 */         bks2.setStdMsgId(cadena.substring(0, 3));
/*  425: 444 */         bks2.setSeqNum(cadena.substring(3, 11));
/*  426: 445 */         bks2.setStdNumQual(cadena.substring(11, 13));
/*  427: 446 */         bks2.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  428: 447 */         bks2.setTransNum("");
/*  429: 448 */         bks2.setOrgnlTransNum(cadena.substring(19, 25));
/*  430: 449 */         bks2.setTktDocNum(cadena.substring(25, 39));
/*  431: 450 */         bks2.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  432: 451 */         bks2.setCommAmt(new BigDecimal(numeroLetra(cadena.substring(40, 51))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  433: 452 */         bks2.setNetFareAmt(new BigDecimal(numeroLetra(cadena.substring(51, 62))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  434: 453 */         bks2.setTaxMiscFeeType1(cadena.substring(81, 89));
/*  435: 454 */         cambioNombreImpuesto(bks2);
/*  436:     */         
/*  437: 456 */         bks2.setTaxMiscFeeAmt1(new BigDecimal(numeroLetra(cadena.substring(89, 100))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  438: 457 */         bks2.setTicketDocAmt(new BigDecimal(numeroLetra(cadena.substring(119, 130))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  439: 458 */         bks2.setCurrType(cadena.substring(132, 136));
/*  440: 459 */         asignarClaseImpuesto(bks2);
/*  441:     */         
/*  442: 461 */         Ticket ticket1 = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  443: 462 */         if (ticket1 != null)
/*  444:     */         {
/*  445: 463 */           bks2.setTicket(ticket1);
/*  446: 464 */           DetalleTicket dt2 = (DetalleTicket)hmDetalleTicket.get(ticket1.getNumero() + "¬" + bks2.getTaxMiscFeeType1());
/*  447: 465 */           if (dt2 != null)
/*  448:     */           {
/*  449: 466 */             dt2.setTaxMiscFeeAmt1(dt2.getTaxMiscFeeAmt1().add(bks2.getTaxMiscFeeAmt1()));
/*  450:     */           }
/*  451:     */           else
/*  452:     */           {
/*  453: 468 */             ticket1.getListaDetalleTicket().add(bks2);
/*  454: 469 */             hmDetalleTicket.put(ticket1.getNumero() + "¬" + bks2.getTaxMiscFeeType1(), bks2);
/*  455:     */           }
/*  456:     */         }
/*  457:     */         else
/*  458:     */         {
/*  459: 472 */           getListaBks().add(bks2);
/*  460:     */         }
/*  461: 475 */         DetalleTicket bks3 = new DetalleTicket();
/*  462:     */         
/*  463: 477 */         bks3.setStdMsgId(cadena.substring(0, 3));
/*  464: 478 */         bks3.setSeqNum(cadena.substring(3, 11));
/*  465: 479 */         bks3.setStdNumQual(cadena.substring(11, 13));
/*  466: 480 */         bks3.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  467: 481 */         bks3.setTransNum("");
/*  468: 482 */         bks3.setOrgnlTransNum(cadena.substring(19, 25));
/*  469: 483 */         bks3.setTktDocNum(cadena.substring(25, 39));
/*  470: 484 */         bks3.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  471: 485 */         bks3.setCommAmt(new BigDecimal(numeroLetra(cadena.substring(40, 51))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  472: 486 */         bks3.setNetFareAmt(new BigDecimal(numeroLetra(cadena.substring(51, 62))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  473: 487 */         bks3.setTaxMiscFeeType1(cadena.substring(100, 108));
/*  474: 488 */         cambioNombreImpuesto(bks3);
/*  475: 489 */         bks3.setTaxMiscFeeAmt1(new BigDecimal(numeroLetra(cadena.substring(108, 119))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  476: 490 */         bks3.setTicketDocAmt(new BigDecimal(numeroLetra(cadena.substring(119, 130))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  477: 491 */         bks3.setCurrType(cadena.substring(132, 136));
/*  478: 492 */         asignarClaseImpuesto(bks3);
/*  479:     */         
/*  480: 494 */         Ticket ticket2 = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  481: 495 */         if (ticket2 != null)
/*  482:     */         {
/*  483: 496 */           bks3.setTicket(ticket2);
/*  484: 497 */           DetalleTicket dt3 = (DetalleTicket)hmDetalleTicket.get(ticket2.getNumero() + "¬" + bks3.getTaxMiscFeeType1());
/*  485: 498 */           if (dt3 != null)
/*  486:     */           {
/*  487: 499 */             dt3.setTaxMiscFeeAmt1(dt3.getTaxMiscFeeAmt1().add(bks3.getTaxMiscFeeAmt1()));
/*  488:     */           }
/*  489:     */           else
/*  490:     */           {
/*  491: 501 */             ticket2.getListaDetalleTicket().add(bks3);
/*  492: 502 */             hmDetalleTicket.put(ticket2.getNumero() + "¬" + bks3.getTaxMiscFeeType1(), bks3);
/*  493:     */           }
/*  494:     */         }
/*  495:     */         else
/*  496:     */         {
/*  497: 505 */           getListaBks().add(bks3);
/*  498:     */         }
/*  499:     */       }
/*  500: 508 */       if ((cadena.substring(0, 3).equals("BKS")) && (cadena.substring(11, 13).equals("39")))
/*  501:     */       {
/*  502: 509 */         DetalleTicket bks = new DetalleTicket();
/*  503:     */         
/*  504: 511 */         bks.setStdMsgId(cadena.substring(0, 3));
/*  505: 512 */         bks.setSeqNum(cadena.substring(3, 11));
/*  506: 513 */         bks.setStdNumQual(cadena.substring(11, 13));
/*  507: 514 */         bks.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  508: 515 */         bks.setTransNum("");
/*  509: 516 */         bks.setOrgnlTransNum(cadena.substring(19, 25));
/*  510: 517 */         bks.setTktDocNum(cadena.substring(25, 39));
/*  511: 518 */         bks.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  512: 519 */         bks.setStsclCd(cadena.substring(40, 43));
/*  513: 520 */         bks.setCommType(cadena.substring(43, 49));
/*  514: 521 */         bks.setCommRt(cadena.substring(49, 54));
/*  515: 522 */         bks.setCommAmt(new BigDecimal(numeroLetra(cadena.substring(54, 65))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  516: 523 */         bks.setSuplmType(cadena.substring(65, 71));
/*  517: 524 */         bks.setSuplmRt(cadena.substring(71, 76));
/*  518: 525 */         bks.setSuplmAmt(new BigDecimal(numeroLetra(cadena.substring(76, 87))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  519: 526 */         bks.setEffCommRt(cadena.substring(87, 92));
/*  520: 527 */         bks.setEffCommAmt(new BigDecimal(numeroLetra(cadena.substring(92, 103))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  521: 528 */         bks.setAmtPaidByCust(new BigDecimal(numeroLetra(cadena.substring(103, 114))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  522: 529 */         bks.setRoutingDiInd(cadena.substring(114, 115));
/*  523: 530 */         bks.setCurrType(cadena.substring(132, 135));
/*  524: 531 */         getListaBks().add(bks);
/*  525:     */         
/*  526: 533 */         Ticket ticket3 = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  527: 534 */         if (ticket3 != null) {
/*  528: 535 */           ticket3.getListaDetalleTicket().add(bks);
/*  529:     */         }
/*  530: 538 */         bks.setTicket(ticket3);
/*  531:     */       }
/*  532: 540 */       if ((cadena.substring(0, 3).equals("BKS")) && (cadena.substring(11, 13).equals("42")))
/*  533:     */       {
/*  534: 541 */         DetalleTicket bks = new DetalleTicket();
/*  535:     */         
/*  536: 543 */         bks.setStdMsgId(cadena.substring(0, 3));
/*  537: 544 */         bks.setSeqNum(cadena.substring(3, 11));
/*  538: 545 */         bks.setStdNumQual(cadena.substring(11, 13));
/*  539: 546 */         bks.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  540: 547 */         bks.setTransNum("");
/*  541: 548 */         bks.setOrgnlTransNum(cadena.substring(19, 25));
/*  542: 549 */         bks.setTktDocNum(cadena.substring(25, 39));
/*  543: 550 */         bks.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  544: 551 */         bks.setTaxOnCommType1(cadena.substring(40, 46));
/*  545: 552 */         bks.setTaxOnCommAmt1(new BigDecimal(numeroLetra(cadena.substring(46, 57))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  546: 553 */         bks.setTaxOnCommType2(cadena.substring(53, 63));
/*  547: 554 */         bks.setTaxOnCommAmt2(new BigDecimal(numeroLetra(cadena.substring(63, 74))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  548: 555 */         bks.setTaxOnCommType3(cadena.substring(74, 80));
/*  549: 556 */         bks.setTaxOnCommAmt3(new BigDecimal(numeroLetra(cadena.substring(80, 91))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  550: 557 */         bks.setTaxOnCommType4(cadena.substring(91, 97));
/*  551: 558 */         bks.setTaxOnCommAmt4(new BigDecimal(numeroLetra(cadena.substring(97, 108))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  552: 559 */         bks.setCurrType(cadena.substring(132, 136));
/*  553: 560 */         getListaBks().add(bks);
/*  554:     */         
/*  555: 562 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  556: 563 */         if (ticket != null) {
/*  557: 564 */           ticket.getListaDetalleTicket().add(bks);
/*  558:     */         }
/*  559: 567 */         bks.setTicket(ticket);
/*  560:     */       }
/*  561: 569 */       if ((cadena.substring(0, 3).equals("BKS")) && (cadena.substring(11, 13).equals("45")))
/*  562:     */       {
/*  563: 570 */         DetalleTicket bks = new DetalleTicket();
/*  564:     */         
/*  565: 572 */         bks.setStdMsgId(cadena.substring(0, 3));
/*  566: 573 */         bks.setSeqNum(cadena.substring(3, 11));
/*  567: 574 */         bks.setStdNumQual(cadena.substring(11, 13));
/*  568: 575 */         bks.setRemtPedEndDate(stringAFecha(cadena.substring(13, 19)));
/*  569: 576 */         bks.setTransNum("");
/*  570: 577 */         bks.setOrgnlTransNum(cadena.substring(19, 25));
/*  571: 578 */         bks.setReltTktDocNum(cadena.substring(25, 39));
/*  572: 579 */         bks.setReltTktDocNumChkDgt(cadena.substring(39, 40));
/*  573: 580 */         bks.setReltTktDocNumId(cadena.substring(59, 63));
/*  574: 581 */         bks.setDateOfIssueRefundDoc(stringAFecha(cadena.substring(63, 69)));
/*  575: 582 */         getListaBks().add(bks);
/*  576:     */         
/*  577: 584 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  578: 585 */         if (ticket != null) {
/*  579: 586 */           ticket.getListaDetalleTicket().add(bks);
/*  580:     */         }
/*  581: 589 */         bks.setTicket(ticket);
/*  582:     */       }
/*  583: 591 */       if ((cadena.substring(0, 3).equals("BKS")) && (cadena.substring(11, 13).equals("46")))
/*  584:     */       {
/*  585: 592 */         DetalleTicket bks = new DetalleTicket();
/*  586:     */         
/*  587: 594 */         bks.setStdMsgId(cadena.substring(0, 3));
/*  588: 595 */         bks.setSeqNum(cadena.substring(3, 11));
/*  589: 596 */         bks.setStdNumQual(cadena.substring(11, 13));
/*  590: 597 */         bks.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  591: 598 */         bks.setTransNum("");
/*  592: 599 */         bks.setOrgnlTransNum(cadena.substring(19, 25));
/*  593: 600 */         bks.setTktDocNum(cadena.substring(25, 39));
/*  594: 601 */         bks.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  595: 602 */         bks.setOrignlIssueInfo(cadena.substring(40, 72));
/*  596: 603 */         bks.setEndorseRstrs(cadena.substring(72, 121));
/*  597: 604 */         getListaBks().add(bks);
/*  598:     */         
/*  599: 606 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  600: 607 */         if (ticket != null) {
/*  601: 608 */           ticket.getListaDetalleTicket().add(bks);
/*  602:     */         }
/*  603: 611 */         bks.setTicket(ticket);
/*  604:     */       }
/*  605: 613 */       if ((cadena.substring(0, 3).equals("BKI")) && (cadena.substring(11, 13).equals("63")))
/*  606:     */       {
/*  607: 614 */         DetalleTicket bki = new DetalleTicket();
/*  608: 615 */         bki.setStdMsgId(cadena.substring(0, 3));
/*  609: 616 */         bki.setSeqNum(cadena.substring(3, 11));
/*  610: 617 */         bki.setStdNumQual(cadena.substring(11, 13));
/*  611: 618 */         bki.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  612: 619 */         bki.setTransNum("");
/*  613: 620 */         bki.setOrgnlTransNum(cadena.substring(19, 25));
/*  614: 621 */         bki.setTktDocNum(cadena.substring(25, 39));
/*  615: 622 */         bki.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  616: 623 */         bki.setSegId(cadena.substring(40, 41));
/*  617: 624 */         bki.setStpovrCd(cadena.substring(41, 42));
/*  618: 625 */         bki.setNotValidBeforeDate(cadena.substring(42, 47));
/*  619: 626 */         bki.setNotValidAfterDate(cadena.substring(47, 52));
/*  620: 627 */         bki.setOrigCd(cadena.substring(52, 57));
/*  621: 628 */         bki.setDestCd(cadena.substring(57, 62));
/*  622: 629 */         bki.setCarrCd(cadena.substring(62, 65));
/*  623: 630 */         bki.setFltNum(cadena.substring(66, 71));
/*  624: 631 */         bki.setRsrvBkngDsgn(cadena.substring(71, 73));
/*  625: 632 */         bki.setFlightDate(cadena.substring(73, 78));
/*  626: 633 */         bki.setFlightDprtTime(cadena.substring(78, 83));
/*  627: 634 */         bki.setFlightBkngStat(cadena.substring(83, 85));
/*  628: 635 */         bki.setFreeBagAlwnc(cadena.substring(85, 88));
/*  629: 636 */         bki.setFbOrTktDsgn(cadena.substring(88, 103));
/*  630: 637 */         bki.setFrqtFlyerRef(cadena.substring(103, 119));
/*  631: 638 */         bki.setFareCompPricedPaxType(cadena.substring(123, 126));
/*  632: 639 */         getListaBki().add(bki);
/*  633:     */         
/*  634: 641 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  635: 642 */         if (ticket != null) {
/*  636: 643 */           ticket.getListaDetalleTicket().add(bki);
/*  637:     */         }
/*  638: 646 */         bki.setTicket(ticket);
/*  639:     */       }
/*  640: 648 */       if ((cadena.substring(0, 3).equals("BAR")) && (cadena.substring(11, 13).equals("64")))
/*  641:     */       {
/*  642: 649 */         DetalleTicket bar = new DetalleTicket();
/*  643:     */         
/*  644: 651 */         bar.setStdMsgId(cadena.substring(0, 3));
/*  645: 652 */         bar.setSeqNum(cadena.substring(3, 11));
/*  646: 653 */         bar.setStdNumQual(cadena.substring(11, 13));
/*  647: 654 */         bar.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  648: 655 */         bar.setTransNum("");
/*  649: 656 */         bar.setOrgnlTransNum(cadena.substring(19, 25));
/*  650: 657 */         bar.setTktDocNum(cadena.substring(25, 39));
/*  651: 658 */         bar.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  652: 659 */         bar.setFare(cadena.substring(40, 51));
/*  653: 660 */         bar.setTktngModeInd(cadena.substring(52, 53));
/*  654: 661 */         bar.setEquivFarePaid(cadena.substring(53, 64));
/*  655: 662 */         bar.setTot(cadena.substring(65, 76));
/*  656: 663 */         bar.setSvcAlnOrSysPrvdrId(cadena.substring(77, 81));
/*  657: 664 */         bar.setFareCalcModeInd(cadena.substring(81, 82));
/*  658: 665 */         bar.setBkngAgentId(cadena.substring(82, 88));
/*  659:     */         
/*  660: 667 */         getListaBar().add(bar);
/*  661: 668 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  662: 669 */         if (ticket != null) {
/*  663: 670 */           ticket.getListaDetalleTicket().add(bar);
/*  664:     */         }
/*  665: 673 */         bar.setTicket(ticket);
/*  666:     */       }
/*  667: 677 */       if ((cadena.substring(0, 3).equals("BAR")) && (cadena.substring(11, 13).equals("65")))
/*  668:     */       {
/*  669: 678 */         DetalleTicket bar = new DetalleTicket();
/*  670: 679 */         bar.setStdMsgId(cadena.substring(0, 3));
/*  671: 680 */         bar.setSeqNum(cadena.substring(3, 11));
/*  672: 681 */         bar.setStdNumQual(cadena.substring(11, 13));
/*  673: 682 */         bar.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  674: 683 */         bar.setTransNum("");
/*  675: 684 */         bar.setOrgnlTransNum(cadena.substring(19, 25));
/*  676: 685 */         bar.setTktDocNum(cadena.substring(25, 39));
/*  677: 686 */         bar.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  678: 687 */         bar.setPaxName(cadena.substring(40, 89));
/*  679: 688 */         bar.setPaxSpecificData(cadena.substring(89, 118));
/*  680: 689 */         bar.setPaxTypeCd(cadena.substring(125, 128));
/*  681: 690 */         getListaBar().add(bar);
/*  682:     */         
/*  683: 692 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  684: 693 */         if (ticket != null) {
/*  685: 694 */           ticket.getListaDetalleTicket().add(bar);
/*  686:     */         }
/*  687: 697 */         bar.setTicket(ticket);
/*  688:     */       }
/*  689: 700 */       if ((cadena.substring(0, 3).equals("BAR")) && (cadena.substring(11, 13).equals("66")))
/*  690:     */       {
/*  691: 701 */         DetalleTicket bar = new DetalleTicket();
/*  692:     */         
/*  693: 703 */         bar.setStdMsgId(cadena.substring(0, 3));
/*  694: 704 */         bar.setSeqNum(cadena.substring(3, 11));
/*  695: 705 */         bar.setStdNumQual(cadena.substring(11, 13));
/*  696: 706 */         bar.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  697: 707 */         bar.setTransNum("");
/*  698: 708 */         bar.setOrgnlTransNum(cadena.substring(19, 25));
/*  699: 709 */         bar.setTktDocNum(cadena.substring(25, 39));
/*  700: 710 */         bar.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  701: 711 */         bar.setFopSeqNum(cadena.substring(40, 41));
/*  702: 712 */         bar.setFopInfo(cadena.substring(41, 91));
/*  703: 713 */         getListaBar().add(bar);
/*  704:     */         
/*  705: 715 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  706: 716 */         if (ticket != null) {
/*  707: 717 */           ticket.getListaDetalleTicket().add(bar);
/*  708:     */         }
/*  709: 720 */         bar.setTicket(ticket);
/*  710:     */       }
/*  711: 726 */       if ((cadena.substring(0, 3).equals("BMD")) && (cadena.substring(11, 13).equals("75")))
/*  712:     */       {
/*  713: 727 */         DetalleTicket bmd = new DetalleTicket();
/*  714:     */         
/*  715: 729 */         bmd.setStdMsgId(cadena.substring(0, 3));
/*  716: 730 */         bmd.setSeqNum(cadena.substring(3, 11));
/*  717: 731 */         bmd.setStdNumQual(cadena.substring(11, 13));
/*  718: 732 */         bmd.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  719: 733 */         bmd.setTransNum("");
/*  720: 734 */         bmd.setOrgnlTransNum(cadena.substring(19, 25));
/*  721: 735 */         bmd.setTktDocNum(cadena.substring(25, 39));
/*  722: 736 */         bmd.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  723: 737 */         bmd.setEmdCoupNum(cadena.substring(40, 41));
/*  724: 738 */         bmd.setEmdCoupVal(new BigDecimal(numeroLetra(cadena.substring(41, 52))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  725: 739 */         bmd.setEmdReltTktDocNum(cadena.substring(52, 66));
/*  726: 740 */         bmd.setEmdReltCpnNum(cadena.substring(66, 67));
/*  727: 741 */         bmd.setEmdSerTyp(cadena.substring(67, 68));
/*  728: 742 */         bmd.setEmdRsnIsuSubCd(cadena.substring(68, 71));
/*  729: 743 */         bmd.setEmdFeeOwAirDesig(cadena.substring(71, 74));
/*  730: 744 */         bmd.setEmdExBagOvrAllwQual(cadena.substring(74, 75));
/*  731: 745 */         bmd.setEmdExBagCurrCd(cadena.substring(75, 78));
/*  732: 746 */         bmd.setEmdExBagRpu(cadena.substring(78, 90));
/*  733: 747 */         bmd.setEmdExBagTotNumExcs(cadena.substring(90, 102));
/*  734: 748 */         bmd.setEmdConsuIssInd(cadena.substring(102, 103));
/*  735: 749 */         bmd.setEmdNoOfSer(cadena.substring(103, 106));
/*  736: 750 */         bmd.setEmdOppCarr(cadena.substring(106, 109));
/*  737: 751 */         bmd.setEmdAttGrp(cadena.substring(109, 112));
/*  738: 752 */         bmd.setEmdAttSubGrp(cadena.substring(112, 115));
/*  739: 753 */         bmd.setEmdIndCarrInd(cadena.substring(115, 116));
/*  740: 754 */         bmd.setCurrType(cadena.substring(132, 136));
/*  741:     */         
/*  742: 756 */         getListaBmd().add(bmd);
/*  743:     */         
/*  744: 758 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  745: 759 */         if (ticket != null) {
/*  746: 760 */           ticket.getListaDetalleTicket().add(bmd);
/*  747:     */         }
/*  748: 763 */         bmd.setTicket(ticket);
/*  749:     */       }
/*  750: 767 */       if ((cadena.substring(0, 3).equals("BKF")) && (cadena.substring(11, 13).equals("81")))
/*  751:     */       {
/*  752: 768 */         DetalleTicket bkf = new DetalleTicket();
/*  753: 769 */         bkf.setStdMsgId(cadena.substring(0, 3));
/*  754: 770 */         bkf.setSeqNum(cadena.substring(3, 11));
/*  755: 771 */         bkf.setStdNumQual(cadena.substring(11, 13));
/*  756: 772 */         bkf.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  757: 773 */         bkf.setTransNum("");
/*  758: 774 */         bkf.setOrgnlTransNum(cadena.substring(19, 25));
/*  759: 775 */         bkf.setTktDocNum(cadena.substring(25, 39));
/*  760: 776 */         bkf.setTktDocNumChkDgt(cadena.substring(39, 40));
/*  761: 777 */         bkf.setFareCalcSeqNum(cadena.substring(40, 41));
/*  762: 778 */         bkf.setFareCalcArea(cadena.substring(41, 128));
/*  763: 779 */         getListaBkf().add(bkf);
/*  764:     */         
/*  765: 781 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 39));
/*  766: 782 */         if (ticket != null) {
/*  767: 783 */           ticket.getListaDetalleTicket().add(bkf);
/*  768:     */         }
/*  769: 786 */         bkf.setTicket(ticket);
/*  770:     */       }
/*  771: 789 */       if ((cadena.substring(0, 3).equals("BKP")) && (cadena.substring(11, 13).equals("84")))
/*  772:     */       {
/*  773: 790 */         DetalleTicket bkp = new DetalleTicket();
/*  774:     */         
/*  775: 792 */         bkp.setStdMsgId(cadena.substring(0, 3));
/*  776: 793 */         bkp.setSeqNum(cadena.substring(3, 11));
/*  777: 794 */         bkp.setStdNumQual(cadena.substring(11, 13));
/*  778: 795 */         bkp.setDateOfIssue(stringAFecha(cadena.substring(13, 19)));
/*  779: 796 */         bkp.setTransNum("");
/*  780: 797 */         bkp.setOrgnlTransNum(cadena.substring(19, 25));
/*  781: 798 */         bkp.setFopType(cadena.substring(25, 35));
/*  782: 799 */         bkp.setFopAmt(new BigDecimal(numeroLetra(cadena.substring(35, 46))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  783: 800 */         bkp.setFopAcctNum(cadena.substring(46, 65));
/*  784: 801 */         bkp.setExpDate(cadena.substring(65, 69));
/*  785: 802 */         bkp.setExtdPymtCd(cadena.substring(69, 71));
/*  786: 803 */         bkp.setApprovalCd(cadena.substring(71, 77));
/*  787: 804 */         bkp.setInvNum(cadena.substring(77, 91));
/*  788: 805 */         bkp.setInvDate(stringAFecha(cadena.substring(91, 97)));
/*  789: 806 */         bkp.setRemtAmt(new BigDecimal(numeroLetra(cadena.substring(97, 108))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  790: 807 */         bkp.setFopCurrType(cadena.substring(132, 136));
/*  791: 808 */         bkp.setFopTransIdentifier("");
/*  792:     */         
/*  793: 810 */         getListaBkp().add(bkp);
/*  794:     */         
/*  795: 812 */         Ticket ticket = (Ticket)getHmTicket().get(numeroTicket);
/*  796: 813 */         if (ticket != null) {
/*  797: 814 */           ticket.getListaDetalleTicket().add(bkp);
/*  798:     */         }
/*  799: 817 */         bkp.setTicket(ticket);
/*  800:     */       }
/*  801: 820 */       if ((cadena.substring(0, 3).equals("BOT")) && (cadena.substring(11, 13).equals("93")))
/*  802:     */       {
/*  803: 821 */         DetalleTicket bot = new DetalleTicket();
/*  804:     */         
/*  805: 823 */         bot.setStdMsgId(cadena.substring(0, 3));
/*  806: 824 */         bot.setSeqNum(cadena.substring(3, 11));
/*  807: 825 */         bot.setStdNumQual(cadena.substring(11, 13));
/*  808: 826 */         bot.setAgentNumCd(cadena.substring(13, 21));
/*  809: 827 */         bot.setRemtPedEndDate(stringAFecha(cadena.substring(21, 27)));
/*  810: 828 */         bot.setGrossValueAmt(new BigDecimal(numeroLetra(cadena.substring(27, 42))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  811: 829 */         bot.setTotRemtAmt(new BigDecimal(numeroLetra(cadena.substring(42, 57))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  812: 830 */         bot.setTotCommValueAmt(new BigDecimal(numeroLetra(cadena.substring(57, 72))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  813: 831 */         bot.setTotTaxOrMiscFeeAmt(new BigDecimal(numeroLetra(cadena.substring(72, 87))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  814: 832 */         bot.setTransCd(cadena.substring(87, 91));
/*  815: 833 */         bot.setTotTaxCommAmt(new BigDecimal(numeroLetra(cadena.substring(91, 106))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  816: 834 */         bot.setCurrType(cadena.substring(132, 136));
/*  817: 835 */         getListaBot().add(bot);
/*  818:     */         
/*  819: 837 */         Ticket ticket = (Ticket)getHmTicket().get(numeroTicket);
/*  820: 838 */         if (ticket != null) {
/*  821: 839 */           ticket.getListaDetalleTicket().add(bot);
/*  822:     */         }
/*  823: 842 */         bot.setTicket(ticket);
/*  824:     */       }
/*  825: 845 */       if ((cadena.substring(0, 3).equals("BOT")) && (cadena.substring(11, 13).equals("94")))
/*  826:     */       {
/*  827: 846 */         DetalleTicket bot = new DetalleTicket();
/*  828:     */         
/*  829: 848 */         bot.setStdMsgId(cadena.substring(0, 3));
/*  830: 849 */         bot.setSeqNum(cadena.substring(3, 11));
/*  831: 850 */         bot.setStdNumQual(cadena.substring(11, 13));
/*  832: 851 */         bot.setAgentNumCd(cadena.substring(13, 21));
/*  833: 852 */         bot.setRemtPedEndDate(stringAFecha(cadena.substring(21, 27)));
/*  834: 853 */         bot.setGrossValueAmt(new BigDecimal(numeroLetra(cadena.substring(27, 42))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  835: 854 */         bot.setTotRemtAmt(new BigDecimal(numeroLetra(cadena.substring(42, 57))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  836: 855 */         bot.setTotCommValueAmt(new BigDecimal(numeroLetra(cadena.substring(57, 72))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  837: 856 */         bot.setTotTaxOrMiscFeeAmt(new BigDecimal(numeroLetra(cadena.substring(72, 87))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  838: 857 */         bot.setTotTaxCommAmt(new BigDecimal(numeroLetra(cadena.substring(87, 102))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  839: 858 */         bot.setCurrType(cadena.substring(132, 136));
/*  840: 859 */         getListaBot().add(bot);
/*  841:     */         
/*  842: 861 */         Ticket ticket = (Ticket)getHmTicket().get(numeroTicket);
/*  843: 862 */         if (ticket != null) {
/*  844: 863 */           ticket.getListaDetalleTicket().add(bot);
/*  845:     */         }
/*  846: 866 */         bot.setTicket(ticket);
/*  847:     */       }
/*  848: 869 */       if ((cadena.substring(0, 3).equals("BCT")) && (cadena.substring(11, 13).equals("95")))
/*  849:     */       {
/*  850: 870 */         DetalleTicket bct = new DetalleTicket();
/*  851:     */         
/*  852: 872 */         bct.setStdMsgId(cadena.substring(0, 3));
/*  853: 873 */         bct.setSeqNum(cadena.substring(3, 11));
/*  854: 874 */         bct.setStdNumQual(cadena.substring(11, 13));
/*  855: 875 */         bct.setProcDateId(cadena.substring(13, 16));
/*  856: 876 */         bct.setProcCycleId(cadena.substring(16, 17));
/*  857: 877 */         bct.setOfficeCnt(cadena.substring(17, 22));
/*  858: 878 */         bct.setGrossValueAmt(new BigDecimal(numeroLetra(cadena.substring(22, 37))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  859: 879 */         bct.setTotRemtAmt(new BigDecimal(numeroLetra(cadena.substring(37, 52))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  860: 880 */         bct.setTotCommValueAmt(new BigDecimal(numeroLetra(cadena.substring(52, 67))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  861: 881 */         bct.setTotMiscFeeAmt(new BigDecimal(numeroLetra(cadena.substring(67, 82))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  862: 882 */         bct.setTotTaxOnCommAmt(new BigDecimal(numeroLetra(cadena.substring(92, 97))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  863: 883 */         bct.setCurrType(cadena.substring(132, 136));
/*  864: 884 */         getListaBct().add(bct);
/*  865:     */         
/*  866: 886 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 40));
/*  867: 887 */         if (ticket != null) {
/*  868: 888 */           ticket.getListaDetalleTicket().add(bct);
/*  869:     */         }
/*  870: 891 */         bct.setTicket(ticket);
/*  871:     */       }
/*  872: 893 */       if ((cadena.substring(0, 3).equals("BFT")) && (cadena.substring(11, 13).equals("99")))
/*  873:     */       {
/*  874: 895 */         DetalleTicket bft = new DetalleTicket();
/*  875:     */         
/*  876: 897 */         bft.setStdMsgId(cadena.substring(0, 3));
/*  877: 898 */         bft.setSeqNum(cadena.substring(3, 11));
/*  878: 899 */         bft.setStdNumQual(cadena.substring(11, 13));
/*  879: 900 */         bft.setBspId(cadena.substring(13, 16));
/*  880: 901 */         bft.setOfficeCnt(cadena.substring(16, 21));
/*  881: 902 */         bft.setGrossValueAmt(new BigDecimal(numeroLetra(cadena.substring(21, 36))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  882: 903 */         bft.setTotRemtAmt(new BigDecimal(numeroLetra(cadena.substring(36, 51))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  883: 904 */         bft.setTotCommValueAmt(new BigDecimal(numeroLetra(cadena.substring(51, 66))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  884: 905 */         bft.setTotTaxOrMiscFeeAmt(new BigDecimal(numeroLetra(cadena.substring(66, 81))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  885: 906 */         bft.setTotTaxOnCommAmt(new BigDecimal(numeroLetra(cadena.substring(81, 96))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
/*  886: 907 */         bft.setCurrType(cadena.substring(132, 136));
/*  887: 908 */         getListaBft().add(bft);
/*  888:     */         
/*  889: 910 */         Ticket ticket = (Ticket)getHmTicket().get(cadena.substring(25, 40));
/*  890: 911 */         if (ticket != null) {
/*  891: 912 */           ticket.getListaDetalleTicket().add(bft);
/*  892:     */         }
/*  893: 915 */         bft.setTicket(ticket);
/*  894:     */       }
/*  895:     */     }
/*  896: 920 */     b.close();
/*  897:     */     
/*  898: 922 */     verificarTicket();
/*  899:     */     
/*  900: 924 */     return null;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public List<Ticket> getListaTicketCreditos()
/*  904:     */   {
/*  905: 930 */     List<Ticket> listaTicketCreditos = new ArrayList();
/*  906: 931 */     List<Ticket> listaTicketCreditosFiltrados = new ArrayList();
/*  907: 933 */     for (Ticket t : getBsp().getListaTicket())
/*  908:     */     {
/*  909: 934 */       int i = 0;
/*  910: 936 */       for (DetalleTicket dt : t.getListaDetalleTicket()) {
/*  911: 938 */         if ((dt.getStdMsgId().equals("BKP")) && 
/*  912: 939 */           (dt.getStdNumQual().equals("84")))
/*  913:     */         {
/*  914: 940 */           t.setCredito(BigDecimal.ZERO);
/*  915: 942 */           if (dt.getFopType().substring(0, 2).equals("CC"))
/*  916:     */           {
/*  917: 943 */             t.setIndicadorTicketCredito(Boolean.valueOf(true));
/*  918: 944 */             i++;
/*  919: 946 */             if (i >= 1)
/*  920:     */             {
/*  921: 947 */               Ticket tic = new Ticket();
/*  922: 948 */               tic.setIndicadorTicketCredito(Boolean.valueOf(true));
/*  923: 949 */               tic.setCredito(dt.getFopAmt());
/*  924: 950 */               tic.setEmisor(dt.getFopType().substring(2, 4));
/*  925: 951 */               tic.setVale(t.getVale());
/*  926: 952 */               tic.setNumero(t.getNumero());
/*  927: 953 */               tic.setAerolinea(t.getAerolinea());
/*  928: 954 */               tic.setFecha(t.getFecha());
/*  929: 955 */               tic.setTipo(t.getTipo());
/*  930: 956 */               tic.setOperacion(t.getOperacion());
/*  931: 957 */               tic.setMoneda(t.getMoneda());
/*  932: 958 */               tic.setBsp(t.getBsp());
/*  933: 959 */               tic.setPasajero(t.getPasajero());
/*  934: 960 */               tic.setMoneda(t.getMoneda());
/*  935: 961 */               tic.setComision(t.getComision());
/*  936: 962 */               tic.setIvaComision(t.getIvaComision());
/*  937: 963 */               tic.setWt(t.getWt());
/*  938: 964 */               tic.setEd(t.getEd());
/*  939: 965 */               tic.setYq(t.getYq());
/*  940: 966 */               tic.setQb(t.getQb());
/*  941: 967 */               tic.setQi(t.getQi());
/*  942: 968 */               tic.setYr(t.getYr());
/*  943: 969 */               tic.setE2(t.getE2());
/*  944: 970 */               tic.setRetencionFte(t.getRetencionFte());
/*  945: 971 */               tic.setTarifa(t.getTarifa());
/*  946: 972 */               tic.setIva(t.getIva());
/*  947: 973 */               tic.setPenalty(t.getPenalty());
/*  948: 974 */               tic.setPeriodo(t.getPeriodo());
/*  949: 975 */               tic.setRuta(t.getRuta());
/*  950: 976 */               tic.setImpuestoExtranjero(t.getImpuestoExtranjero());
/*  951:     */               
/*  952: 978 */               listaTicketCreditos.add(tic);
/*  953:     */             }
/*  954:     */           }
/*  955:     */         }
/*  956:     */       }
/*  957: 989 */       listaTicketCreditos.add(t);
/*  958:     */     }
/*  959: 992 */     for (Ticket tick : listaTicketCreditos) {
/*  960: 993 */       if ((tick.getIndicadorTicketCredito().booleanValue()) && (tick.getCredito().compareTo(BigDecimal.ZERO) != 0))
/*  961:     */       {
/*  962: 994 */         listaTicketCreditosFiltrados.add(tick);
/*  963: 995 */         setTotalCredito(getTotalCredito().add(tick.getCredito()));
/*  964:     */       }
/*  965:     */     }
/*  966:1000 */     return listaTicketCreditosFiltrados;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public void verificarTicket()
/*  970:     */   {
/*  971:1005 */     referenciaArchivoBSP();
/*  972:1006 */     for (Ticket t : getBsp().getListaTicket())
/*  973:     */     {
/*  974:1008 */       System.out.println("ticket    " + t.getNumero());
/*  975:1010 */       for (DetalleTicket dt : t.getListaDetalleTicket()) {
/*  976:1012 */         System.out.println("      " + dt.getStdMsgId() + "   " + dt.getStdNumQual() + "    " + dt.getTaxMiscFeeType1() + "    " + dt
/*  977:1013 */           .getTaxMiscFeeAmt1());
/*  978:     */       }
/*  979:1017 */       BigDecimal totalImpuestoExtranjero = BigDecimal.ZERO;
/*  980:1018 */       BigDecimal totalPenalty = BigDecimal.ZERO;
/*  981:1019 */       BigDecimal totalCredito = BigDecimal.ZERO;
/*  982:1020 */       String a = "";
/*  983:1021 */       List<String> fechas = new ArrayList();
/*  984:1022 */       for (DetalleTicket dt : t.getListaDetalleTicket())
/*  985:     */       {
/*  986:1024 */         if ((dt.getStdMsgId().equals("BKP")) && 
/*  987:1025 */           (dt.getStdNumQual().equals("84")))
/*  988:     */         {
/*  989:1026 */           totalCredito = totalCredito.add(dt.getFopAmt());
/*  990:1027 */           if (dt.getFopType().substring(0, 2).equals("CC"))
/*  991:     */           {
/*  992:1028 */             t.setEmisor(dt.getFopType().substring(2, 4));
/*  993:1029 */             t.setVale(dt.getFopType().substring(4, 8));
/*  994:     */           }
/*  995:     */         }
/*  996:1035 */         if (dt.getStdMsgId().equals("BKI"))
/*  997:     */         {
/*  998:1036 */           if (dt.getFbOrTktDsgn() != null)
/*  999:     */           {
/* 1000:1037 */             String nuevoCodigoDisenio = dt.getFbOrTktDsgn().replace(" ", "");
/* 1001:1038 */             int verifica = nuevoCodigoDisenio.indexOf("/");
/* 1002:1039 */             if (verifica > -1)
/* 1003:     */             {
/* 1004:1040 */               String[] codigoDisenioTicket = dt.getFbOrTktDsgn().split("/");
/* 1005:1041 */               t.setCodigoDisenioTicket(codigoDisenioTicket[1]);
/* 1006:     */             }
/* 1007:     */           }
/* 1008:1044 */           fechas.add(dt.getFlightDate());
/* 1009:     */         }
/* 1010:1047 */         if ((dt.getStdMsgId().equals("BAR")) && 
/* 1011:1048 */           (dt.getTktDocNum() != null) && 
/* 1012:1049 */           (dt.getStdNumQual().equals("65"))) {
/* 1013:1050 */           t.setPasajero(dt.getPaxName());
/* 1014:     */         }
/* 1015:1055 */         if (dt.getStdMsgId().equals("BKI")) {
/* 1016:1057 */           a = a + dt.getOrigCd() + " " + dt.getDestCd() + " " + dt.getCarrCd() + " " + dt.getFltNum() + " " + dt.getRsrvBkngDsgn() + " " + dt.getFlightDate() + " " + dt.getFlightDprtTime().substring(0, 2);
/* 1017:     */         }
/* 1018:1060 */         if (dt.getStdMsgId().equals("BKS")) {
/* 1019:1061 */           if (dt.getStdNumQual().equals("39"))
/* 1020:     */           {
/* 1021:1062 */             t.setMoneda(dt.getCurrType());
/* 1022:1063 */             t.setComision(dt.getEffCommAmt());
/* 1023:     */           }
/* 1024:1064 */           else if (dt.getStdNumQual().equals("42"))
/* 1025:     */           {
/* 1026:1065 */             t.setIvaComision(dt.getTaxOnCommAmt1());
/* 1027:     */           }
/* 1028:1066 */           else if (dt.getStdNumQual().equals("30"))
/* 1029:     */           {
/* 1030:1068 */             if ((dt.getTaxMiscFeeType1().equals("EC")) || (dt.getTaxMiscFeeType1().equals("EC1"))) {
/* 1031:1069 */               t.setIva(dt.getTaxMiscFeeAmt1());
/* 1032:     */             }
/* 1033:1071 */             if (dt.getTaxMiscFeeType1().equals("WT")) {
/* 1034:1072 */               t.setWt(dt.getTaxMiscFeeAmt1());
/* 1035:     */             }
/* 1036:1074 */             if (dt.getTaxMiscFeeType1().equals("ED")) {
/* 1037:1075 */               t.setEd(dt.getTaxMiscFeeAmt1());
/* 1038:     */             }
/* 1039:1077 */             if (dt.getTaxMiscFeeType1().equals("YQ")) {
/* 1040:1078 */               t.setYq(dt.getTaxMiscFeeAmt1());
/* 1041:     */             }
/* 1042:1080 */             if (dt.getTaxMiscFeeType1().equals("QB")) {
/* 1043:1081 */               t.setQb(dt.getTaxMiscFeeAmt1());
/* 1044:     */             }
/* 1045:1083 */             if (dt.getTaxMiscFeeType1().equals("QI")) {
/* 1046:1084 */               t.setQi(dt.getTaxMiscFeeAmt1());
/* 1047:     */             }
/* 1048:1086 */             if (dt.getTaxMiscFeeType1().equals("YR")) {
/* 1049:1087 */               t.setYr(dt.getTaxMiscFeeAmt1());
/* 1050:     */             }
/* 1051:1089 */             if (dt.getTaxMiscFeeType1().equals("E2")) {
/* 1052:1090 */               t.setE2(dt.getTaxMiscFeeAmt1());
/* 1053:     */             }
/* 1054:1092 */             if (dt.getTaxMiscFeeType1().equals("VATEC")) {
/* 1055:1093 */               t.setRetencionFte(dt.getTaxMiscFeeAmt1());
/* 1056:     */             }
/* 1057:1095 */             if (dt.getCommAmt().compareTo(BigDecimal.ZERO) != 0) {
/* 1058:1096 */               t.setTarifa(dt.getCommAmt());
/* 1059:     */             }
/* 1060:1099 */             if ((dt.getTaxMiscFeeType1().equals("MF")) || (dt.getTaxMiscFeeType1().equals("CP"))) {
/* 1061:1100 */               totalPenalty = totalPenalty.add(dt.getTaxMiscFeeAmt1());
/* 1062:     */             }
/* 1063:1103 */             if ((!dt.getTaxMiscFeeType1().equals("WT")) && (!dt.getTaxMiscFeeType1().equals("ED")) && (!dt.getTaxMiscFeeType1().equals("YQ")) && 
/* 1064:1104 */               (!dt.getTaxMiscFeeType1().equals("QB")) && (!dt.getTaxMiscFeeType1().equals("QI")) && 
/* 1065:1105 */               (!dt.getTaxMiscFeeType1().equals("YR")) && (!dt.getTaxMiscFeeType1().equals("E2")) && 
/* 1066:1106 */               (!dt.getTaxMiscFeeType1().equals("EC")) && (!dt.getTaxMiscFeeType1().equals("VATEC")) && 
/* 1067:1107 */               (!dt.getTaxMiscFeeType1().equals("EC1")) && (!dt.getTaxMiscFeeType1().equals("MF")) && 
/* 1068:1108 */               (!dt.getTaxMiscFeeType1().equals("CP"))) {
/* 1069:1109 */               totalImpuestoExtranjero = totalImpuestoExtranjero.add(dt.getTaxMiscFeeAmt1());
/* 1070:     */             }
/* 1071:     */           }
/* 1072:     */         }
/* 1073:     */       }
/* 1074:1115 */       t.setCredito(totalCredito);
/* 1075:1116 */       t.setPeriodo(((DetalleTicket)getListaBch().get(0)).getBillAnalEndDate());
/* 1076:1117 */       getBsp().setPeriodo(t.getPeriodo());
/* 1077:1118 */       if ((!fechas.isEmpty()) || (fechas.size() > 0))
/* 1078:     */       {
/* 1079:1119 */         Integer.toString(FuncionesUtiles.getAnio(t.getPeriodo()));
/* 1080:1120 */         t.setFechaViaje(stringAFechaVuelo((String)fechas.get(0), Integer.toString(FuncionesUtiles.getAnio(t.getPeriodo())), 
/* 1081:1121 */           FuncionesUtiles.getMes(t.getPeriodo())));
/* 1082:     */       }
/* 1083:1123 */       t.setRuta(a);
/* 1084:1124 */       t.setImpuestoExtranjero(totalImpuestoExtranjero);
/* 1085:1125 */       t.setPenalty(totalPenalty);
/* 1086:     */     }
/* 1087:1128 */     calcularTotalesTicket();
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public void calcularTotalesTicket()
/* 1091:     */   {
/* 1092:1134 */     for (Ticket tic : this.bsp.getListaTicket())
/* 1093:     */     {
/* 1094:1135 */       setTotalComision(getTotalComision().add(tic.getComision()));
/* 1095:1136 */       setTotalIVA(getTotalIVA().add(tic.getIva()));
/* 1096:1137 */       setTotalTarifa(getTotalTarifa().add(tic.getTarifa()));
/* 1097:1138 */       setTotalImpuestoExtranjero(getTotalImpuestoExtranjero().add(tic.getImpuestoExtranjero()));
/* 1098:1139 */       setTotalIvaComision(getTotalIvaComision().add(tic.getIvaComision()));
/* 1099:1140 */       setTotalRetencionFuente(getTotalRetencionFuente().add(tic.getRetencionFte()));
/* 1100:1141 */       setTotalWT(getTotalWT().add(tic.getWt()));
/* 1101:1142 */       setTotalED(getTotalED().add(tic.getEd()));
/* 1102:1143 */       setTotalYQ(getTotalYQ().add(tic.getYq()));
/* 1103:1144 */       setTotalQB(getTotalQB().add(tic.getQb()));
/* 1104:1145 */       setTotalQI(getTotalQI().add(tic.getQi()));
/* 1105:1146 */       setTotalYR(getTotalYR().add(tic.getYr()));
/* 1106:1147 */       setTotalE2(getTotalE2().add(tic.getE2()));
/* 1107:1148 */       setTotalPenalty(getTotalPenalty().add(tic.getPenalty()));
/* 1108:     */     }
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public Date stringAFechaVuelo(String diaMes, String anio, int mes)
/* 1112:     */   {
/* 1113:1155 */     if (diaMes.substring(2, 5).equals("JAN")) {
/* 1114:1156 */       diaMes = diaMes.replace("JAN", "ENE");
/* 1115:     */     }
/* 1116:1158 */     if (diaMes.substring(2, 5).equals("APR")) {
/* 1117:1159 */       diaMes = diaMes.replace("APR", "ABR");
/* 1118:     */     }
/* 1119:1161 */     if (diaMes.substring(2, 5).equals("AUG")) {
/* 1120:1162 */       diaMes = diaMes.replace("AUG", "AGO");
/* 1121:     */     }
/* 1122:1164 */     if (diaMes.substring(2, 5).equals("DEC")) {
/* 1123:1165 */       diaMes = diaMes.replace("DEC", "DIC");
/* 1124:     */     }
/* 1125:1168 */     diaMes = diaMes + anio;
/* 1126:1169 */     SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy");
/* 1127:1170 */     String strFecha = diaMes;
/* 1128:1171 */     Date fechaDate = null;
/* 1129:     */     try
/* 1130:     */     {
/* 1131:1173 */       fechaDate = formato.parse(strFecha);
/* 1132:1174 */       if (mes > FuncionesUtiles.getMes(fechaDate)) {
/* 1133:1175 */         fechaDate = FuncionesUtiles.getFecha(FuncionesUtiles.getDiaFecha(fechaDate), FuncionesUtiles.getMes(fechaDate), 
/* 1134:1176 */           FuncionesUtiles.getAnio(fechaDate) + 1);
/* 1135:     */       }
/* 1136:     */     }
/* 1137:     */     catch (ParseException localParseException) {}
/* 1138:1182 */     return fechaDate;
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public void referenciaArchivoBSP()
/* 1142:     */   {
/* 1143:1186 */     for (DetalleTicket dt : getListaBfh()) {
/* 1144:1187 */       if (dt.getStdNumQual().equals("01")) {
/* 1145:1190 */         this.referenciaArchivos = (dt.getStdMsgId() + "" + dt.getSeqNum() + "" + dt.getStdNumQual() + "" + dt.getBspId() + "" + dt.getTktngAlnCdNum() + "" + dt.getHandbookRevisionNum() + "" + dt.getTestProd() + "" + dt.getProcDate() + "" + dt.getProcTime() + "" + dt.getIsoCntryCd() + "" + dt.getFileSeqNum());
/* 1146:     */       }
/* 1147:     */     }
/* 1148:1194 */     for (DetalleTicket dt : getListaBch()) {
/* 1149:1195 */       if (dt.getStdNumQual().equals("02")) {
/* 1150:1197 */         this.referenciaArchivos = (this.referenciaArchivos + dt.getStdMsgId() + "" + dt.getSeqNum() + "" + dt.getStdNumQual() + "" + dt.getProcDateId() + "" + dt.getProcCycleId() + "" + dt.getBillAnalEndDate() + "" + dt.getDynamicRunId());
/* 1151:     */       }
/* 1152:     */     }
/* 1153:1204 */     this.referenciaArchivos = (this.referenciaArchivos + ((DetalleTicket)getListaBoh().get(0)).getStdMsgId() + "" + ((DetalleTicket)getListaBoh().get(0)).getSeqNum() + "" + ((DetalleTicket)getListaBoh().get(0)).getStdNumQual() + "" + ((DetalleTicket)getListaBoh().get(0)).getAgentNumCd() + "" + ((DetalleTicket)getListaBoh().get(0)).getRemtPedEndDate() + "" + ((DetalleTicket)getListaBoh().get(0)).getCurrType() + "" + ((DetalleTicket)getListaBoh().get(0)).getMultiLocIdentifer());
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public Date stringAFecha(String fecha)
/* 1157:     */     throws ExcepcionAS2
/* 1158:     */   {
/* 1159:1210 */     SimpleDateFormat formato = new SimpleDateFormat("yyMMdd");
/* 1160:1211 */     String strFecha = fecha;
/* 1161:1212 */     Date fechaDate = null;
/* 1162:     */     try
/* 1163:     */     {
/* 1164:1215 */       fechaDate = formato.parse(strFecha);
/* 1165:     */     }
/* 1166:     */     catch (ParseException e)
/* 1167:     */     {
/* 1168:1217 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 1169:     */     }
/* 1170:1219 */     return fechaDate;
/* 1171:     */   }
/* 1172:     */   
/* 1173:     */   public String numeroLetra(String a)
/* 1174:     */   {
/* 1175:1224 */     int tamanioCadena = a.length();
/* 1176:1225 */     String c = "";
/* 1177:1226 */     char character = a.charAt(tamanioCadena - 1);
/* 1178:1227 */     int ascii = character;
/* 1179:1228 */     if ((ascii >= 65) && (ascii < 74))
/* 1180:     */     {
/* 1181:1229 */       ascii -= 64;
/* 1182:1230 */       c = Integer.toString(ascii);
/* 1183:1231 */       a = a.replace(a.charAt(tamanioCadena - 1), ' ');
/* 1184:     */     }
/* 1185:1232 */     else if ((ascii > 73) && (ascii < 83))
/* 1186:     */     {
/* 1187:1233 */       ascii -= 73;
/* 1188:1234 */       c = Integer.toString(ascii);
/* 1189:1235 */       a = a.replace(a.charAt(tamanioCadena - 1), ' ');
/* 1190:     */     }
/* 1191:     */     else
/* 1192:     */     {
/* 1193:1237 */       a = a.replace(a.charAt(tamanioCadena - 1), '0');
/* 1194:     */     }
/* 1195:1240 */     return (a.trim() + c).trim();
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   public void asignarClaseImpuesto(DetalleTicket dt)
/* 1199:     */   {
/* 1200:1244 */     if ((!dt.getTaxMiscFeeType1().equals("WT")) && (!dt.getTaxMiscFeeType1().equals("ED")) && (!dt.getTaxMiscFeeType1().equals("YQ")) && 
/* 1201:1245 */       (!dt.getTaxMiscFeeType1().equals("QB")) && (!dt.getTaxMiscFeeType1().equals("QI")) && (!dt.getTaxMiscFeeType1().equals("YR")) && 
/* 1202:1246 */       (!dt.getTaxMiscFeeType1().equals("E2")) && (!dt.getTaxMiscFeeType1().equals("EC")) && (!dt.getTaxMiscFeeType1().equals("VATEC")) && 
/* 1203:1247 */       (!dt.getTaxMiscFeeType1().equals("EC1")) && (!dt.getTaxMiscFeeType1().equals("MF")) && (!dt.getTaxMiscFeeType1().equals("CP"))) {
/* 1204:1249 */       dt.setIndicadorNacional(Boolean.valueOf(false));
/* 1205:     */     } else {
/* 1206:1251 */       dt.setIndicadorNacional(Boolean.valueOf(true));
/* 1207:     */     }
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   public void relacionTicketDetalleTicket() {}
/* 1211:     */   
/* 1212:     */   public String editar()
/* 1213:     */   {
/* 1214:1262 */     return null;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public String guardar()
/* 1218:     */   {
/* 1219:1267 */     if (this.render) {
/* 1220:     */       try
/* 1221:     */       {
/* 1222:1269 */         this.bsp.setReferenciaArchivo(this.referenciaArchivos);
/* 1223:1270 */         this.bsp.setIndicadorRespaldo(Boolean.valueOf(isRespaldo()));
/* 1224:1271 */         this.bsp.setTipo(this.tipoCarga.getNombre());
/* 1225:1272 */         this.bsp.setNumero(this.servicioSecuencia.obtenerSecuenciaDocumento(this.bsp.getDocumento().getIdDocumento(), new Date()));
/* 1226:1273 */         this.servicioBSP.guardar(getBsp());
/* 1227:1274 */         setEditado(false);
/* 1228:1275 */         limpiar();
/* 1229:1276 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1230:     */       }
/* 1231:     */       catch (AS2Exception e)
/* 1232:     */       {
/* 1233:1279 */         JsfUtil.addErrorMessage(e, "");
/* 1234:1280 */         e.printStackTrace();
/* 1235:     */       }
/* 1236:     */       catch (ExcepcionAS2 e)
/* 1237:     */       {
/* 1238:1282 */         e.printStackTrace();
/* 1239:     */       }
/* 1240:     */     } else {
/* 1241:     */       try
/* 1242:     */       {
/* 1243:1286 */         this.bsp.setReferenciaArchivo(this.referenciaArchivos);
/* 1244:1287 */         this.bsp.setIndicadorRespaldo(Boolean.valueOf(isRespaldo()));
/* 1245:1288 */         this.bsp.setNumero(this.servicioSecuencia.obtenerSecuenciaDocumento(this.bsp.getDocumento().getIdDocumento(), new Date()));
/* 1246:1289 */         if (this.bsp.getDocumento().getNombre().contains("BSP")) {
/* 1247:1290 */           this.bsp.setTipo("BSP Actualizado");
/* 1248:     */         } else {
/* 1249:1292 */           this.bsp.setTipo(this.tipoCarga.getNombre());
/* 1250:     */         }
/* 1251:1294 */         this.bsp.setListaTicket(this.listTickets);
/* 1252:1295 */         this.bsp.setPeriodo(((Ticket)this.listTickets.get(0)).getPeriodo());
/* 1253:1296 */         this.servicioBSP.guardar(getBsp());
/* 1254:1297 */         limpiar();
/* 1255:1298 */         setEditado(false);
/* 1256:     */       }
/* 1257:     */       catch (AS2Exception e)
/* 1258:     */       {
/* 1259:1300 */         JsfUtil.addErrorMessage(e, "");
/* 1260:1301 */         e.printStackTrace();
/* 1261:     */       }
/* 1262:     */       catch (ExcepcionAS2 e)
/* 1263:     */       {
/* 1264:1303 */         e.printStackTrace();
/* 1265:     */       }
/* 1266:     */     }
/* 1267:1306 */     return null;
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   public List<Ticket> getListaTicketFiltrados()
/* 1271:     */   {
/* 1272:1311 */     List<Ticket> listaFiltrada = new ArrayList();
/* 1273:1312 */     for (Ticket t : getBsp().getListaTicket()) {
/* 1274:1313 */       if (t.getEmisor() != null) {
/* 1275:1314 */         listaFiltrada.add(t);
/* 1276:     */       }
/* 1277:     */     }
/* 1278:1318 */     return listaFiltrada;
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   public void facturarListener(CargaArchivo bsp)
/* 1282:     */   {
/* 1283:     */     try
/* 1284:     */     {
/* 1285:1325 */       Empresa empresa = null;
/* 1286:1326 */       Integer idEmpresa = ParametrosSistema.getClienteGenerico(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1287:1327 */       if (idEmpresa == null)
/* 1288:     */       {
/* 1289:1328 */         addInfoMessage(getLanguageController().getMensaje("msg_info_cliente_generico"));
/* 1290:     */       }
/* 1291:     */       else
/* 1292:     */       {
/* 1293:1330 */         empresa = this.servicioEmpresa.buscarPorId(idEmpresa);
/* 1294:1331 */         empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 1295:1332 */         if (empresa == null) {
/* 1296:1333 */           addInfoMessage(getLanguageController().getMensaje("msg_info_cliente_generico"));
/* 1297:     */         }
/* 1298:1336 */         this.servicioCargaBSP.facturarLote(bsp, AppUtil.getPuntoDeVenta(), empresa);
/* 1299:     */         
/* 1300:1338 */         bsp.setEstado(Estado.FACTURADO);
/* 1301:1339 */         this.cargaArchivoDao.guardar(bsp);
/* 1302:1340 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1303:     */       }
/* 1304:     */     }
/* 1305:     */     catch (ExcepcionAS2 e)
/* 1306:     */     {
/* 1307:1344 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1308:1345 */       e.printStackTrace();
/* 1309:     */     }
/* 1310:     */     catch (AS2Exception e)
/* 1311:     */     {
/* 1312:1347 */       JsfUtil.addErrorMessage(e, "");
/* 1313:1348 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 1314:1349 */       e.printStackTrace();
/* 1315:     */     }
/* 1316:     */   }
/* 1317:     */   
/* 1318:     */   public void contabilizar(CargaArchivo bsp) {}
/* 1319:     */   
/* 1320:     */   public void indicadorRespaldo()
/* 1321:     */   {
/* 1322:1359 */     if (this.tipoCarga.getNombre().equals("Ventas locales")) {
/* 1323:1360 */       setRespaldo(true);
/* 1324:     */     } else {
/* 1325:1362 */       setRespaldo(false);
/* 1326:     */     }
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   public String eliminar()
/* 1330:     */   {
/* 1331:     */     try
/* 1332:     */     {
/* 1333:1370 */       this.bsp = this.servicioCargaBSP.cargarDetalle(getBsp().getId());
/* 1334:1371 */       if (this.bsp.getEstado().equals(Estado.FACTURADO))
/* 1335:     */       {
/* 1336:1372 */         addErrorMessage(getLanguageController().getMensaje("msg_error_tickets_ya_facturados"));
/* 1337:     */       }
/* 1338:     */       else
/* 1339:     */       {
/* 1340:1374 */         Boolean contabilizado = Boolean.valueOf(false);
/* 1341:1375 */         for (Iterator localIterator = this.bsp.getListaTicket().iterator(); localIterator.hasNext();)
/* 1342:     */         {
/* 1343:1375 */           tk = (Ticket)localIterator.next();
/* 1344:1376 */           if (tk.isIndicadorContabilizado())
/* 1345:     */           {
/* 1346:1377 */             contabilizado = Boolean.valueOf(true);
/* 1347:1378 */             break;
/* 1348:     */           }
/* 1349:     */         }
/* 1350:     */         Ticket tk;
/* 1351:1381 */         if (contabilizado.booleanValue())
/* 1352:     */         {
/* 1353:1382 */           Object listaAsientosAnular = this.servicioCargaBSP.listaAsientos(this.bsp.getListaTicket());
/* 1354:1383 */           for (Asiento a : (List)listaAsientosAnular) {
/* 1355:1384 */             if (!a.getEstado().equals(Estado.ANULADO)) {
/* 1356:1385 */               this.servicioAsiento.anular(a);
/* 1357:     */             }
/* 1358:     */           }
/* 1359:     */         }
/* 1360:1390 */         this.servicioCargaBSP.eliminar(this.bsp);
/* 1361:1391 */         addErrorMessage(getLanguageController().getMensaje("msg_carga_archivo_eliminada_asientos_anulados"));
/* 1362:     */       }
/* 1363:     */     }
/* 1364:     */     catch (Exception e)
/* 1365:     */     {
/* 1366:1394 */       e.printStackTrace();
/* 1367:     */     }
/* 1368:1396 */     return null;
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   public String cargarDatos()
/* 1372:     */   {
/* 1373:1401 */     return null;
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   public List<DetalleTicket> getListaBfh()
/* 1377:     */   {
/* 1378:1405 */     return this.listaBfh == null ? (this.listaBfh = new ArrayList()) : this.listaBfh;
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public void setListaBfh(List<DetalleTicket> listaBfh)
/* 1382:     */   {
/* 1383:1409 */     this.listaBfh = listaBfh;
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public List<DetalleTicket> getListaBch()
/* 1387:     */   {
/* 1388:1413 */     return this.listaBch == null ? (this.listaBch = new ArrayList()) : this.listaBch;
/* 1389:     */   }
/* 1390:     */   
/* 1391:     */   public void setListaBch(List<DetalleTicket> listaBch)
/* 1392:     */   {
/* 1393:1417 */     this.listaBch = listaBch;
/* 1394:     */   }
/* 1395:     */   
/* 1396:     */   public List<DetalleTicket> getListaBoh()
/* 1397:     */   {
/* 1398:1421 */     return this.listaBoh == null ? (this.listaBoh = new ArrayList()) : this.listaBoh;
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   public void setListaBoh(List<DetalleTicket> listaBoh)
/* 1402:     */   {
/* 1403:1425 */     this.listaBoh = listaBoh;
/* 1404:     */   }
/* 1405:     */   
/* 1406:     */   public List<DetalleTicket> getListaBkt()
/* 1407:     */   {
/* 1408:1429 */     return this.listaBkt == null ? (this.listaBkt = new ArrayList()) : this.listaBkt;
/* 1409:     */   }
/* 1410:     */   
/* 1411:     */   public void setListaBkt(List<DetalleTicket> listaBkt)
/* 1412:     */   {
/* 1413:1433 */     this.listaBkt = listaBkt;
/* 1414:     */   }
/* 1415:     */   
/* 1416:     */   public List<DetalleTicket> getListaBks()
/* 1417:     */   {
/* 1418:1437 */     return this.listaBks == null ? (this.listaBks = new ArrayList()) : this.listaBks;
/* 1419:     */   }
/* 1420:     */   
/* 1421:     */   public void setListaBks(List<DetalleTicket> listaBks)
/* 1422:     */   {
/* 1423:1441 */     this.listaBks = listaBks;
/* 1424:     */   }
/* 1425:     */   
/* 1426:     */   public List<DetalleTicket> getListaBki()
/* 1427:     */   {
/* 1428:1445 */     return this.listaBki == null ? (this.listaBki = new ArrayList()) : this.listaBki;
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public void setListaBki(List<DetalleTicket> listaBki)
/* 1432:     */   {
/* 1433:1449 */     this.listaBki = listaBki;
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public List<DetalleTicket> getListaBar()
/* 1437:     */   {
/* 1438:1453 */     return this.listaBar == null ? (this.listaBar = new ArrayList()) : this.listaBar;
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public void setListaBar(List<DetalleTicket> listaBar)
/* 1442:     */   {
/* 1443:1457 */     this.listaBar = listaBar;
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public List<DetalleTicket> getListaBmp()
/* 1447:     */   {
/* 1448:1461 */     return this.listaBmp == null ? (this.listaBmp = new ArrayList()) : this.listaBmp;
/* 1449:     */   }
/* 1450:     */   
/* 1451:     */   public void setListaBmp(List<DetalleTicket> listaBmp)
/* 1452:     */   {
/* 1453:1465 */     this.listaBmp = listaBmp;
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public List<DetalleTicket> getListaBkf()
/* 1457:     */   {
/* 1458:1469 */     return this.listaBkf == null ? (this.listaBkf = new ArrayList()) : this.listaBkf;
/* 1459:     */   }
/* 1460:     */   
/* 1461:     */   public void setListaBkf(List<DetalleTicket> listaBkf)
/* 1462:     */   {
/* 1463:1473 */     this.listaBkf = listaBkf;
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public List<DetalleTicket> getListaBkp()
/* 1467:     */   {
/* 1468:1477 */     return this.listaBkp == null ? (this.listaBkp = new ArrayList()) : this.listaBkp;
/* 1469:     */   }
/* 1470:     */   
/* 1471:     */   public void setListaBkp(List<DetalleTicket> listaBkp)
/* 1472:     */   {
/* 1473:1481 */     this.listaBkp = listaBkp;
/* 1474:     */   }
/* 1475:     */   
/* 1476:     */   public List<DetalleTicket> getListaBot()
/* 1477:     */   {
/* 1478:1485 */     return this.listaBot == null ? (this.listaBot = new ArrayList()) : this.listaBot;
/* 1479:     */   }
/* 1480:     */   
/* 1481:     */   public void setListaBot(List<DetalleTicket> listaBot)
/* 1482:     */   {
/* 1483:1489 */     this.listaBot = listaBot;
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   public List<DetalleTicket> getListaBct()
/* 1487:     */   {
/* 1488:1493 */     return this.listaBct == null ? (this.listaBct = new ArrayList()) : this.listaBct;
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public void setListaBct(List<DetalleTicket> listaBct)
/* 1492:     */   {
/* 1493:1497 */     this.listaBct = listaBct;
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public List<DetalleTicket> getListaBft()
/* 1497:     */   {
/* 1498:1501 */     return this.listaBft == null ? (this.listaBft = new ArrayList()) : this.listaBft;
/* 1499:     */   }
/* 1500:     */   
/* 1501:     */   public void setListaBft(List<DetalleTicket> listaBft)
/* 1502:     */   {
/* 1503:1505 */     this.listaBft = listaBft;
/* 1504:     */   }
/* 1505:     */   
/* 1506:     */   public List<Ticket> getListaTicket()
/* 1507:     */   {
/* 1508:1509 */     return this.listaTicket == null ? (this.listaTicket = new ArrayList()) : this.listaTicket;
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   public void setListaTicket(List<Ticket> listaTicket)
/* 1512:     */   {
/* 1513:1513 */     this.listaTicket = listaTicket;
/* 1514:     */   }
/* 1515:     */   
/* 1516:     */   public List<Ticket> getListaPagos()
/* 1517:     */   {
/* 1518:1517 */     return this.listaPagos == null ? (this.listaPagos = new ArrayList()) : this.listaPagos;
/* 1519:     */   }
/* 1520:     */   
/* 1521:     */   public void setListaPagos(List<Ticket> listaPagos)
/* 1522:     */   {
/* 1523:1521 */     this.listaPagos = listaPagos;
/* 1524:     */   }
/* 1525:     */   
/* 1526:     */   public HashMap<String, Ticket> getHmTicket()
/* 1527:     */   {
/* 1528:1525 */     return this.hmTicket == null ? (this.hmTicket = new HashMap()) : this.hmTicket;
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   public void setHmTicket(HashMap<String, Ticket> hmTicket)
/* 1532:     */   {
/* 1533:1529 */     this.hmTicket = hmTicket;
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public HashMap<String, Ticket> getHmTicketBot()
/* 1537:     */   {
/* 1538:1533 */     return this.hmTicketBot == null ? (this.hmTicketBot = new HashMap()) : this.hmTicketBot;
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   public void setHmTicketBot(HashMap<String, Ticket> hmTicketBot)
/* 1542:     */   {
/* 1543:1537 */     this.hmTicketBot = hmTicketBot;
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public List<Documento> getListaDocumentoAerolinea()
/* 1547:     */   {
/* 1548:     */     try
/* 1549:     */     {
/* 1550:1543 */       if (this.listaDocumentoAerolinea == null) {
/* 1551:1544 */         this.listaDocumentoAerolinea = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.CARGA_ARCHIVO);
/* 1552:     */       }
/* 1553:     */     }
/* 1554:     */     catch (ExcepcionAS2 e)
/* 1555:     */     {
/* 1556:1547 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1557:     */     }
/* 1558:1550 */     return this.listaDocumentoAerolinea;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public void setListaDocumentoAerolinea(List<Documento> listaDocumentoDespacho)
/* 1562:     */   {
/* 1563:1554 */     this.listaDocumentoAerolinea = listaDocumentoDespacho;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public CargaArchivo getBsp()
/* 1567:     */   {
/* 1568:1558 */     return this.bsp;
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public void setBsp(CargaArchivo bsp)
/* 1572:     */   {
/* 1573:1562 */     this.bsp = bsp;
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public LazyDataModel<CargaArchivo> getListaBSP()
/* 1577:     */   {
/* 1578:1566 */     return this.listaBSP;
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   public void setListaBSP(LazyDataModel<CargaArchivo> listaBSP)
/* 1582:     */   {
/* 1583:1570 */     this.listaBSP = listaBSP;
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public DataTable getDtBSP()
/* 1587:     */   {
/* 1588:1574 */     return this.dtBSP;
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   public void setDtBSP(DataTable dtBSP)
/* 1592:     */   {
/* 1593:1578 */     this.dtBSP = dtBSP;
/* 1594:     */   }
/* 1595:     */   
/* 1596:     */   public boolean isRespaldo()
/* 1597:     */   {
/* 1598:1582 */     return this.respaldo;
/* 1599:     */   }
/* 1600:     */   
/* 1601:     */   public void setRespaldo(boolean respaldo)
/* 1602:     */   {
/* 1603:1586 */     this.respaldo = respaldo;
/* 1604:     */   }
/* 1605:     */   
/* 1606:     */   public String getReferenciaArchivos()
/* 1607:     */   {
/* 1608:1590 */     return this.referenciaArchivos;
/* 1609:     */   }
/* 1610:     */   
/* 1611:     */   public void setReferenciaArchivos(String referenciaArchivos)
/* 1612:     */   {
/* 1613:1594 */     this.referenciaArchivos = referenciaArchivos;
/* 1614:     */   }
/* 1615:     */   
/* 1616:     */   public List<DetalleTicket> getListaBmd()
/* 1617:     */   {
/* 1618:1598 */     return this.listaBmd == null ? (this.listaBmd = new ArrayList()) : this.listaBmd;
/* 1619:     */   }
/* 1620:     */   
/* 1621:     */   public void setListaBmd(List<DetalleTicket> listaBmd)
/* 1622:     */   {
/* 1623:1602 */     this.listaBmd = listaBmd;
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   public BigDecimal getTotalComision()
/* 1627:     */   {
/* 1628:1606 */     return this.totalComision;
/* 1629:     */   }
/* 1630:     */   
/* 1631:     */   public void setTotalComision(BigDecimal totalComision)
/* 1632:     */   {
/* 1633:1610 */     this.totalComision = totalComision;
/* 1634:     */   }
/* 1635:     */   
/* 1636:     */   public BigDecimal getTotalIVA()
/* 1637:     */   {
/* 1638:1614 */     return this.totalIVA;
/* 1639:     */   }
/* 1640:     */   
/* 1641:     */   public void setTotalIVA(BigDecimal totalIVA)
/* 1642:     */   {
/* 1643:1618 */     this.totalIVA = totalIVA;
/* 1644:     */   }
/* 1645:     */   
/* 1646:     */   public BigDecimal getTotalTarifa()
/* 1647:     */   {
/* 1648:1622 */     return this.totalTarifa;
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   public void setTotalTarifa(BigDecimal totalTarifa)
/* 1652:     */   {
/* 1653:1626 */     this.totalTarifa = totalTarifa;
/* 1654:     */   }
/* 1655:     */   
/* 1656:     */   public BigDecimal getTotalImpuestoExtranjero()
/* 1657:     */   {
/* 1658:1630 */     return this.totalImpuestoExtranjero;
/* 1659:     */   }
/* 1660:     */   
/* 1661:     */   public void setTotalImpuestoExtranjero(BigDecimal totalImpuestoExtranjero)
/* 1662:     */   {
/* 1663:1634 */     this.totalImpuestoExtranjero = totalImpuestoExtranjero;
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   public BigDecimal getTotalIvaComision()
/* 1667:     */   {
/* 1668:1638 */     return this.totalIvaComision;
/* 1669:     */   }
/* 1670:     */   
/* 1671:     */   public void setTotalIvaComision(BigDecimal totalIvaComision)
/* 1672:     */   {
/* 1673:1642 */     this.totalIvaComision = totalIvaComision;
/* 1674:     */   }
/* 1675:     */   
/* 1676:     */   public BigDecimal getTotalRetencionFuente()
/* 1677:     */   {
/* 1678:1646 */     return this.totalRetencionFuente;
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   public void setTotalRetencionFuente(BigDecimal totalRetencionFuente)
/* 1682:     */   {
/* 1683:1650 */     this.totalRetencionFuente = totalRetencionFuente;
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   public BigDecimal getTotalWT()
/* 1687:     */   {
/* 1688:1654 */     return this.totalWT;
/* 1689:     */   }
/* 1690:     */   
/* 1691:     */   public void setTotalWT(BigDecimal totalWT)
/* 1692:     */   {
/* 1693:1658 */     this.totalWT = totalWT;
/* 1694:     */   }
/* 1695:     */   
/* 1696:     */   public BigDecimal getTotalED()
/* 1697:     */   {
/* 1698:1662 */     return this.totalED;
/* 1699:     */   }
/* 1700:     */   
/* 1701:     */   public void setTotalED(BigDecimal totalED)
/* 1702:     */   {
/* 1703:1666 */     this.totalED = totalED;
/* 1704:     */   }
/* 1705:     */   
/* 1706:     */   public BigDecimal getTotalYQ()
/* 1707:     */   {
/* 1708:1670 */     return this.totalYQ;
/* 1709:     */   }
/* 1710:     */   
/* 1711:     */   public void setTotalYQ(BigDecimal totalYQ)
/* 1712:     */   {
/* 1713:1674 */     this.totalYQ = totalYQ;
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   public BigDecimal getTotalQB()
/* 1717:     */   {
/* 1718:1678 */     return this.totalQB;
/* 1719:     */   }
/* 1720:     */   
/* 1721:     */   public void setTotalQB(BigDecimal totalQB)
/* 1722:     */   {
/* 1723:1682 */     this.totalQB = totalQB;
/* 1724:     */   }
/* 1725:     */   
/* 1726:     */   public BigDecimal getTotalQI()
/* 1727:     */   {
/* 1728:1686 */     return this.totalQI;
/* 1729:     */   }
/* 1730:     */   
/* 1731:     */   public void setTotalQI(BigDecimal totalQI)
/* 1732:     */   {
/* 1733:1690 */     this.totalQI = totalQI;
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   public BigDecimal getTotalYR()
/* 1737:     */   {
/* 1738:1694 */     return this.totalYR;
/* 1739:     */   }
/* 1740:     */   
/* 1741:     */   public void setTotalYR(BigDecimal totalYR)
/* 1742:     */   {
/* 1743:1698 */     this.totalYR = totalYR;
/* 1744:     */   }
/* 1745:     */   
/* 1746:     */   public BigDecimal getTotalE2()
/* 1747:     */   {
/* 1748:1702 */     return this.totalE2;
/* 1749:     */   }
/* 1750:     */   
/* 1751:     */   public void setTotalE2(BigDecimal totalE2)
/* 1752:     */   {
/* 1753:1706 */     this.totalE2 = totalE2;
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   public BigDecimal getTotalPenalty()
/* 1757:     */   {
/* 1758:1710 */     return this.totalPenalty;
/* 1759:     */   }
/* 1760:     */   
/* 1761:     */   public void setTotalPenalty(BigDecimal totalPenalty)
/* 1762:     */   {
/* 1763:1714 */     this.totalPenalty = totalPenalty;
/* 1764:     */   }
/* 1765:     */   
/* 1766:     */   public BigDecimal getTotalCredito()
/* 1767:     */   {
/* 1768:1718 */     return this.totalCredito;
/* 1769:     */   }
/* 1770:     */   
/* 1771:     */   public void setTotalCredito(BigDecimal totalCredito)
/* 1772:     */   {
/* 1773:1722 */     this.totalCredito = totalCredito;
/* 1774:     */   }
/* 1775:     */   
/* 1776:     */   public List<SelectItem> getListaTipoCarga()
/* 1777:     */   {
/* 1778:1726 */     List<SelectItem> lista = new ArrayList();
/* 1779:1727 */     for (TipoCarga tipoReporte : TipoCarga.values()) {
/* 1780:1728 */       lista.add(new SelectItem(tipoReporte, tipoReporte.getNombre()));
/* 1781:     */     }
/* 1782:1730 */     return lista;
/* 1783:     */   }
/* 1784:     */   
/* 1785:     */   public TipoCarga getTipoCarga()
/* 1786:     */   {
/* 1787:1734 */     return this.tipoCarga;
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   public void setTipoCarga(TipoCarga tipoCarg)
/* 1791:     */   {
/* 1792:1738 */     if (tipoCarg.getNombre().equals("BSP")) {
/* 1793:1739 */       this.render = true;
/* 1794:     */     } else {
/* 1795:1741 */       this.render = false;
/* 1796:     */     }
/* 1797:1744 */     this.tipoCarga = tipoCarg;
/* 1798:     */   }
/* 1799:     */   
/* 1800:     */   public boolean isRender()
/* 1801:     */   {
/* 1802:1748 */     return this.render;
/* 1803:     */   }
/* 1804:     */   
/* 1805:     */   public void setRender(boolean render)
/* 1806:     */   {
/* 1807:1752 */     this.render = render;
/* 1808:     */   }
/* 1809:     */   
/* 1810:     */   public String edicionBSP(FileUploadEvent event)
/* 1811:     */   {
/* 1812:     */     try
/* 1813:     */     {
/* 1814:1759 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 1815:     */       
/* 1816:1761 */       List<Ticket> listaAux = this.servicioMigracion.migracionCargaTicket(input, 0, AppUtil.getOrganizacion().getId(), AppUtil.getSucursal().getId(), Integer.valueOf(13));
/* 1817:1762 */       this.bspParaEdicion = new CargaArchivo();
/* 1818:1763 */       HashMap<String, Ticket> hmTicket = new HashMap();
/* 1819:1764 */       HashMap<String, DetalleTicket> hmDetalleTicket = new HashMap();
/* 1820:     */       
/* 1821:1766 */       this.bspParaEdicion = ((CargaArchivo)getDtBSP().getRowData());
/* 1822:1767 */       this.bspParaEdicion = this.servicioCargaBSP.cargarDetalle(this.bspParaEdicion.getIdCargaArchivo());
/* 1823:1769 */       for (Iterator localIterator1 = this.bspParaEdicion.getListaTicket().iterator(); localIterator1.hasNext();)
/* 1824:     */       {
/* 1825:1769 */         ticke = (Ticket)localIterator1.next();
/* 1826:1770 */         hmTicket.put(ticke.getNumero().trim() + "~" + FuncionesUtiles.dateToString(ticke.getPeriodo()), ticke);
/* 1827:1771 */         for (localIterator2 = ticke.getListaDetalleTicket().iterator(); localIterator2.hasNext();)
/* 1828:     */         {
/* 1829:1771 */           dt = (DetalleTicket)localIterator2.next();
/* 1830:1772 */           if (dt.getTaxMiscFeeType1() != null) {
/* 1831:1773 */             hmDetalleTicket.put(ticke.getNumero().trim() + "~" + dt.getTaxMiscFeeType1().trim(), dt);
/* 1832:     */           }
/* 1833:     */         }
/* 1834:     */       }
/* 1835:     */       Ticket ticke;
/* 1836:     */       Iterator localIterator2;
/* 1837:     */       DetalleTicket dt;
/* 1838:1778 */       for (localIterator1 = listaAux.iterator(); localIterator1.hasNext();)
/* 1839:     */       {
/* 1840:1778 */         tic = (Ticket)localIterator1.next();
/* 1841:1779 */         Ticket ticAux = null;
/* 1842:1780 */         if (tic.getPeriodo() != null) {
/* 1843:1781 */           ticAux = (Ticket)hmTicket.get(tic.getNumero().trim() + "~" + FuncionesUtiles.dateToString(tic.getPeriodo()));
/* 1844:     */         }
/* 1845:1783 */         if (ticAux != null)
/* 1846:     */         {
/* 1847:1784 */           ticAux.setEditado(true);
/* 1848:1786 */           if (tic.getTarifaPreliminar() != null) {
/* 1849:1787 */             ticAux.setTarifa(tic.getTarifaPreliminar());
/* 1850:     */           }
/* 1851:1789 */           if ((tic.getMoneda() != null) && (!tic.getMoneda().isEmpty())) {
/* 1852:1790 */             ticAux.setMoneda(tic.getMoneda());
/* 1853:     */           }
/* 1854:1792 */           if (tic.getPuntoDeVenta() != null) {
/* 1855:1793 */             ticAux.setPuntoDeVenta(tic.getPuntoDeVenta());
/* 1856:     */           }
/* 1857:1795 */           if ((tic.getAerolinea() != null) && (!tic.getAerolinea().isEmpty())) {
/* 1858:1796 */             ticAux.setAerolinea(tic.getCodigoAgente());
/* 1859:     */           }
/* 1860:1798 */           if (tic.getPeriodo() != null) {
/* 1861:1799 */             ticAux.setPeriodo(tic.getPeriodo());
/* 1862:     */           }
/* 1863:1801 */           if (tic.getFechaEmision() != null) {
/* 1864:1802 */             ticAux.setFechaEmision(tic.getFechaEmision());
/* 1865:     */           }
/* 1866:1804 */           if ((tic.getRecord() != null) && (!tic.getRecord().isEmpty())) {
/* 1867:1805 */             ticAux.setTipo(tic.getRecord());
/* 1868:     */           }
/* 1869:1807 */           ticAux.setOriginalConjuncion(tic.getOriginalConjuncion());
/* 1870:1808 */           if ((tic.getNumeroDocumentoRelacionado() != null) && (!tic.getNumeroDocumentoRelacionado().isEmpty())) {
/* 1871:1809 */             ticAux.setNumeroDocumentoRelacionado(tic.getNumeroDocumentoRelacionado());
/* 1872:     */           }
/* 1873:1811 */           if ((tic.getOperacion() != null) && (!tic.getOperacion().isEmpty())) {
/* 1874:1812 */             ticAux.setOperacion(tic.getOperacion());
/* 1875:     */           }
/* 1876:1815 */           if ((tic.getTipoTransaccion() != null) && (!tic.getTipoTransaccion().isEmpty())) {
/* 1877:1816 */             ticAux.setTipoTransaccion(tic.getTipoTransaccion());
/* 1878:     */           }
/* 1879:1819 */           if ((tic.getPasajero() != null) && (!tic.getPasajero().isEmpty())) {
/* 1880:1820 */             ticAux.setPasajero(tic.getPasajero());
/* 1881:     */           }
/* 1882:1822 */           if ((tic.getIdentificacionTributaria() != null) && (!tic.getIdentificacionTributaria().isEmpty())) {
/* 1883:1823 */             ticAux.setIdentificacionTributaria(tic.getIdentificacionTributaria());
/* 1884:     */           }
/* 1885:1825 */           if ((tic.getRuta() != null) && (!tic.getRuta().isEmpty())) {
/* 1886:1826 */             ticAux.setRuta(tic.getRuta());
/* 1887:     */           }
/* 1888:1828 */           if (tic.getFechaViaje() != null) {
/* 1889:1829 */             ticAux.setFechaViaje(tic.getFechaViaje());
/* 1890:     */           }
/* 1891:1831 */           if (tic.getComision() != null) {
/* 1892:1832 */             ticAux.setComision(tic.getComision());
/* 1893:     */           }
/* 1894:1834 */           if (tic.getIvaComision() != null) {
/* 1895:1835 */             ticAux.setIvaComision(tic.getIvaComision());
/* 1896:     */           }
/* 1897:1837 */           if (tic.getPorComision() != null) {
/* 1898:1838 */             ticAux.setPorComision(tic.getPorComision());
/* 1899:     */           }
/* 1900:1840 */           if (tic.getNeto() != null) {
/* 1901:1841 */             ticAux.setNeto(tic.getNeto());
/* 1902:     */           }
/* 1903:1843 */           if ((tic.getNumeroPeriodo() != null) && (!tic.getNumeroPeriodo().isEmpty())) {
/* 1904:1844 */             ticAux.setNumeroPeriodo(tic.getNumeroPeriodo());
/* 1905:     */           }
/* 1906:1846 */           if ((tic.getPeriodoBSP() != null) && (!tic.getPeriodoBSP().isEmpty())) {
/* 1907:1847 */             ticAux.setPeriodoBSP(tic.getPeriodoBSP());
/* 1908:     */           }
/* 1909:1849 */           if (tic.getValorTotalPreliminar() != null) {
/* 1910:1850 */             ticAux.setValorTotal(tic.getValorTotalPreliminar());
/* 1911:     */           }
/* 1912:1852 */           if ((tic.getObservaciones() != null) && (!tic.getObservaciones().isEmpty())) {
/* 1913:1853 */             ticAux.setObservaciones(tic.getObservaciones());
/* 1914:     */           }
/* 1915:1855 */           if (tic.getYq() != null) {
/* 1916:1856 */             ticAux.setYq(tic.getYq());
/* 1917:     */           }
/* 1918:1858 */           if (tic.getPenalty() != null) {
/* 1919:1859 */             ticAux.setPenalty(tic.getPenalty());
/* 1920:     */           }
/* 1921:1861 */           hmTicket.put(ticAux.getNumero() + "~" + FuncionesUtiles.dateToString(ticAux.getPeriodo()), ticAux);
/* 1922:     */         }
/* 1923:1863 */         for (DetalleTicket dt : tic.getListaDetalleTicket())
/* 1924:     */         {
/* 1925:1864 */           DetalleTicket dtAux = (DetalleTicket)hmDetalleTicket.get(tic.getNumero().trim() + "~" + dt.getTaxMiscFeeType1().trim());
/* 1926:1865 */           if (dtAux != null)
/* 1927:     */           {
/* 1928:1866 */             if (dt.getTaxMiscFeeAmt1() != null) {
/* 1929:1867 */               dtAux.setTaxMiscFeeAmt1(dt.getTaxMiscFeeAmt1());
/* 1930:     */             }
/* 1931:1869 */             hmDetalleTicket.put(tic.getNumero().trim() + "~" + dt.getTaxMiscFeeType1().trim(), dt);
/* 1932:     */           }
/* 1933:     */         }
/* 1934:     */       }
/* 1935:     */       Ticket tic;
/* 1936:1874 */       this.listaTicketNoEditados = new ArrayList();
/* 1937:1875 */       for (Ticket t : this.bspParaEdicion.getListaTicket()) {
/* 1938:1876 */         if (!t.isEditado()) {
/* 1939:1877 */           getListaTicketNoEditados().add(t);
/* 1940:     */         }
/* 1941:     */       }
/* 1942:1881 */       if (getListaTicketNoEditados().size() > 0)
/* 1943:     */       {
/* 1944:1882 */         FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
/* 1945:1883 */         RequestContext.getCurrentInstance().execute("edicionTickets.show()");
/* 1946:     */       }
/* 1947:     */       else
/* 1948:     */       {
/* 1949:1885 */         actualizaCargaArchivo(this.bspParaEdicion);
/* 1950:     */       }
/* 1951:     */     }
/* 1952:     */     catch (IOException e1)
/* 1953:     */     {
/* 1954:1890 */       e1.printStackTrace();
/* 1955:     */     }
/* 1956:     */     catch (ExcepcionAS2 e)
/* 1957:     */     {
/* 1958:1892 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1959:1893 */       e.printStackTrace();
/* 1960:     */     }
/* 1961:     */     catch (AS2Exception e1)
/* 1962:     */     {
/* 1963:1895 */       e1.printStackTrace();
/* 1964:     */     }
/* 1965:1898 */     return null;
/* 1966:     */   }
/* 1967:     */   
/* 1968:     */   public void actualizaCargaArchivo(CargaArchivo ca)
/* 1969:     */     throws AS2Exception
/* 1970:     */   {
/* 1971:1902 */     ca.setTipo("BSP Actualizado");
/* 1972:1903 */     this.servicioCargaBSP.guardar(ca);
/* 1973:1904 */     addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1974:     */   }
/* 1975:     */   
/* 1976:     */   public String migrarCargaTickets(FileUploadEvent event)
/* 1977:     */   {
/* 1978:     */     try
/* 1979:     */     {
/* 1980:1910 */       Boolean verificadorNoRepetidos = Boolean.valueOf(true);
/* 1981:1911 */       String razon = "";
/* 1982:1912 */       List<String> nTkt = new ArrayList();
/* 1983:1913 */       this.listTickets.clear();
/* 1984:1914 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 1985:1915 */       List<Ticket> listaAux = this.servicioMigracion.migracionCargaTicket(input, 0, AppUtil.getOrganizacion().getId(), AppUtil.getSucursal()
/* 1986:1916 */         .getIdSucursal(), Integer.valueOf(20));
/* 1987:1917 */       for (Iterator localIterator1 = listaAux.iterator(); localIterator1.hasNext();)
/* 1988:     */       {
/* 1989:1917 */         tk = (Ticket)localIterator1.next();
/* 1990:1918 */         if ((tk.getNumero() == null) || (tk.getNumero().equals("")) || (tk.getNumero().equals("0")))
/* 1991:     */         {
/* 1992:1919 */           tk.setEliminado(true);
/* 1993:     */         }
/* 1994:     */         else
/* 1995:     */         {
/* 1996:1921 */           if (tk.getDescuento() == null) {
/* 1997:1922 */             tk.setDescuento(BigDecimal.ZERO);
/* 1998:     */           }
/* 1999:1924 */           if (tk.getTarifaPreliminar() == null) {
/* 2000:1925 */             tk.setTarifaPreliminar(BigDecimal.ZERO);
/* 2001:     */           }
/* 2002:1926 */           tk.setTarifa(tk.getTarifaPreliminar().add(tk.getDescuento()));
/* 2003:1927 */           int cont = 0;
/* 2004:1928 */           for (DetalleTicket dt : tk.getListaDetalleTicket()) {
/* 2005:1929 */             if (dt.getTaxMiscFeeType1().equals("EC"))
/* 2006:     */             {
/* 2007:1930 */               if (dt.getTaxMiscFeeAmt1().compareTo(BigDecimal.ZERO) > 0) {
/* 2008:1931 */                 tk.setIva(new BigDecimal("12.0"));
/* 2009:     */               }
/* 2010:1932 */               cont++;
/* 2011:     */             }
/* 2012:     */           }
/* 2013:1935 */           if ((!this.bsp.getDocumento().getNombre().contains("BSP")) && 
/* 2014:1936 */             (cont == 0) && 
/* 2015:1937 */             (!tk.getTipoDeDocumento().equals("VOID")) && (!tk.getTipoDeDocumento().equals("CONJUNCTION")) && 
/* 2016:1938 */             (!tk.getTipoDeDocumento().equals("EXCHANGED"))) {
/* 2017:1941 */             if ((tk.getRuta().startsWith("UIO")) || (tk.getRuta().startsWith("GYE")) || (tk.getRuta().startsWith("QUITO")) || 
/* 2018:1942 */               (tk.getRuta().startsWith("GUAYAQUIL")))
/* 2019:     */             {
/* 2020:1943 */               verificadorNoRepetidos = Boolean.valueOf(false);
/* 2021:1944 */               razon = "EC";
/* 2022:1945 */               nTkt.add(tk.getNumero() + razon);
/* 2023:     */             }
/* 2024:     */           }
/* 2025:1950 */           if ((tk.getIva() == null) || (tk.getIva().compareTo(BigDecimal.ZERO) <= 0)) {
/* 2026:1951 */             tk.setIva(BigDecimal.ZERO);
/* 2027:     */           }
/* 2028:1953 */           for (DetalleTicket dt : tk.getListaDetalleTicket())
/* 2029:     */           {
/* 2030:1954 */             if (dt.getTaxMiscFeeType1().equals("YQ"))
/* 2031:     */             {
/* 2032:1955 */               tk.setYq(dt.getTaxMiscFeeAmt1());
/* 2033:1956 */               if (tk.getIva().compareTo(BigDecimal.ZERO) > 0)
/* 2034:     */               {
/* 2035:1957 */                 tk.setYqDiferenteCero(dt.getTaxMiscFeeAmt1());
/* 2036:1958 */                 tk.setYqCero(BigDecimal.ZERO);
/* 2037:     */               }
/* 2038:     */               else
/* 2039:     */               {
/* 2040:1960 */                 tk.setYqCero(dt.getTaxMiscFeeAmt1());
/* 2041:1961 */                 tk.setYqDiferenteCero(BigDecimal.ZERO);
/* 2042:     */               }
/* 2043:     */             }
/* 2044:1964 */             if (dt.getTaxMiscFeeType1().equals("YR")) {
/* 2045:1965 */               tk.setYr(dt.getTaxMiscFeeAmt1());
/* 2046:     */             }
/* 2047:     */           }
/* 2048:1968 */           if (tk.getIva() == null) {
/* 2049:1969 */             tk.setIva(BigDecimal.ZERO);
/* 2050:     */           }
/* 2051:1971 */           if (tk.getIva().compareTo(BigDecimal.ZERO) > 0)
/* 2052:     */           {
/* 2053:1972 */             tk.setTarifaDiferenteCero(tk.getTarifa());
/* 2054:1973 */             tk.setTarifaCero(BigDecimal.ZERO);
/* 2055:     */           }
/* 2056:     */           else
/* 2057:     */           {
/* 2058:1975 */             tk.setTarifaCero(tk.getTarifa());
/* 2059:1976 */             tk.setTarifaDiferenteCero(BigDecimal.ZERO);
/* 2060:     */           }
/* 2061:1978 */           if ((tk.getTipoDeDocumento() != null) && (tk.getTipoDeDocumento().equals("MCO")))
/* 2062:     */           {
/* 2063:1979 */             tk.setTarifaDiferenteCero(tk.getTarifa());
/* 2064:1980 */             tk.setTarifaCero(BigDecimal.ZERO);
/* 2065:     */           }
/* 2066:1982 */           if (tk.getValorTotalPreliminar() == null) {
/* 2067:1983 */             tk.setValorTotalPreliminar(BigDecimal.ZERO);
/* 2068:     */           }
/* 2069:1984 */           tk.setValorTotal(tk.getValorTotalPreliminar().add(tk.getDescuento()));
/* 2070:1986 */           if ((tk.getFormaPago() != null) && ((tk.getFormaPago().equals("CA")) || (tk.getFormaPago().equals("CK")))) {
/* 2071:1987 */             tk.setValorFormaPago(tk.getValorTotal().subtract(tk.getDescuento()));
/* 2072:     */           }
/* 2073:1990 */           if ((tk.getFormaPago() != null) && (tk.getFormaPago().equals("CC")) && (tk.getValorTotal() != null)) {
/* 2074:1991 */             tk.setValorFormaPago(tk.getValorTotal().subtract(tk.getDescuento()));
/* 2075:     */           }
/* 2076:1993 */           if ((tk.getFormaPago() != null) && (tk.getFormaPago().equals("RV")) && (tk.getValorTotal() != null)) {
/* 2077:1994 */             tk.setValorFormaPago(tk.getValorTotal());
/* 2078:     */           }
/* 2079:1996 */           if (tk.getYqCero() == null)
/* 2080:     */           {
/* 2081:1997 */             tk.setYq(BigDecimal.ZERO);
/* 2082:1998 */             tk.setYqCero(BigDecimal.ZERO);
/* 2083:1999 */             tk.setYqDiferenteCero(BigDecimal.ZERO);
/* 2084:     */           }
/* 2085:2002 */           BigDecimal aux = BigDecimal.ZERO;
/* 2086:2003 */           aux = aux.add(tk.getValorFormaPago());
/* 2087:2004 */           aux = aux.add(tk.getDescuento());
/* 2088:2005 */           aux = tk.getValorTotal().subtract(aux);
/* 2089:2006 */           tk.setDiferencia(aux);
/* 2090:2007 */           this.listTickets.add(tk);
/* 2091:     */         }
/* 2092:     */       }
/* 2093:     */       Ticket tk;
/* 2094:2011 */       Object clavesDB = new HashMap();
/* 2095:2012 */       if (!this.bsp.getDocumento().getNombre().contains("BSP")) {
/* 2096:2014 */         for (Ticket tks : this.listTickets)
/* 2097:     */         {
/* 2098:2015 */           if (tks.getFecha().after(new Date()))
/* 2099:     */           {
/* 2100:2016 */             verificadorNoRepetidos = Boolean.valueOf(false);
/* 2101:2017 */             razon = "fechaEmision";
/* 2102:2018 */             nTkt.add(tks.getNumero() + razon);
/* 2103:     */           }
/* 2104:2020 */           if (tks.getPeriodo().before(tks.getFecha()))
/* 2105:     */           {
/* 2106:2021 */             verificadorNoRepetidos = Boolean.valueOf(false);
/* 2107:2022 */             razon = "fechaReporte";
/* 2108:2023 */             nTkt.add(tks.getNumero() + razon);
/* 2109:     */           }
/* 2110:2026 */           if ((tks.getFormaPago() != null) && (tks.getFormaPago().equals("CC")) && 
/* 2111:2027 */             (!tks.getTipoDeDocumento().equals("VOID")) && (!tks.getTipoDeDocumento().equals("CONJUNCTION")) && 
/* 2112:2028 */             (!tks.getTipoDeDocumento().equals("EXCHANGED"))) {
/* 2113:2031 */             if ((tks.getTipoTarjetaCredito().isEmpty()) || (tks.getTipoTarjetaCredito().length() != 2))
/* 2114:     */             {
/* 2115:2032 */               verificadorNoRepetidos = Boolean.valueOf(false);
/* 2116:2033 */               razon = "TipoTarjetaCredito";
/* 2117:2034 */               nTkt.add(tks.getNumero() + razon);
/* 2118:     */             }
/* 2119:     */           }
/* 2120:2038 */           if ((tks.getTipoDeDocumento().equals("REISSUED")) && 
/* 2121:2039 */             (tks.getNumeroDocumentoRelacionado().isEmpty()))
/* 2122:     */           {
/* 2123:2040 */             verificadorNoRepetidos = Boolean.valueOf(false);
/* 2124:2041 */             razon = "numeroDocumentoRelacionado";
/* 2125:2042 */             nTkt.add(tks.getNumero() + razon);
/* 2126:     */           }
/* 2127:2045 */           if ((tks.getTipoDeDocumento().equals("EXCHANGED")) && 
/* 2128:2046 */             (tks.getNumeroDocumentoRelacionado().isEmpty()))
/* 2129:     */           {
/* 2130:2047 */             verificadorNoRepetidos = Boolean.valueOf(false);
/* 2131:2048 */             razon = "numeroDocumentoRelacionado";
/* 2132:2049 */             nTkt.add(tks.getNumero() + razon);
/* 2133:     */           }
/* 2134:2052 */           if (((tks.getIdentificacionTributaria() == null) || (tks.getIdentificacionTributaria().isEmpty())) && 
/* 2135:2053 */             (!tks.getTipoDeDocumento().equals("VOID")) && (!tks.getTipoDeDocumento().equals("CONJUNCTION")))
/* 2136:     */           {
/* 2137:2056 */             verificadorNoRepetidos = Boolean.valueOf(false);
/* 2138:2057 */             razon = "IdentificacionTributaria";
/* 2139:2058 */             nTkt.add(tks.getNumero() + razon);
/* 2140:     */           }
/* 2141:2061 */           if ((tks.getFechaViaje() != null) && (tks.getFechaViaje().before(tks.getFecha())))
/* 2142:     */           {
/* 2143:2062 */             verificadorNoRepetidos = Boolean.valueOf(false);
/* 2144:2063 */             razon = "fechaViaje";
/* 2145:2064 */             nTkt.add(tks.getNumero() + razon);
/* 2146:     */           }
/* 2147:2066 */           if ((tks.getTipoDeDocumento().equals("EDD")) && 
/* 2148:2067 */             (tks.getCodigoDeServicio().isEmpty()))
/* 2149:     */           {
/* 2150:2068 */             verificadorNoRepetidos = Boolean.valueOf(false);
/* 2151:2069 */             razon = "CodigoDeServicio";
/* 2152:2070 */             nTkt.add(tks.getNumero() + razon);
/* 2153:     */           }
/* 2154:2073 */           if (!tks.getOperacion().equals("TKT")) {
/* 2155:2076 */             if (!tks.getOperacion().equals("ETKT"))
/* 2156:     */             {
/* 2157:2079 */               verificadorNoRepetidos = Boolean.valueOf(false);
/* 2158:2080 */               razon = "tipoEmision";
/* 2159:2081 */               nTkt.add(tks.getNumero() + razon);
/* 2160:     */             }
/* 2161:     */           }
/* 2162:2084 */           if (((HashMap)clavesDB).get(tks.getOperacion() + FuncionesUtiles.dateToString(tks.getPeriodo()) + tks.getPuntoDeVenta().getCodigoAlterno()) == null) {
/* 2163:2085 */             ((HashMap)clavesDB).put(tks.getOperacion() + FuncionesUtiles.dateToString(tks.getPeriodo()) + tks.getPuntoDeVenta().getCodigoAlterno(), tks);
/* 2164:     */           }
/* 2165:     */         }
/* 2166:     */       }
/* 2167:2090 */       if (!verificadorNoRepetidos.booleanValue())
/* 2168:     */       {
/* 2169:2091 */         this.listTickets.clear();
/* 2170:2092 */         if (razon.equals("fechaEmision")) {
/* 2171:2093 */           addErrorMessage(getLanguageController().getMensaje("msg_fecha_emision_tickets") + "\n Ticket No: " + 
/* 2172:2094 */             ticketsErroneos(nTkt, razon));
/* 2173:2095 */         } else if (razon.equals("fechaReporte")) {
/* 2174:2096 */           addErrorMessage(getLanguageController().getMensaje("msg_fecha_reporte_tickets") + "\n Ticket No: " + 
/* 2175:2097 */             ticketsErroneos(nTkt, razon));
/* 2176:2101 */         } else if (razon.equals("TipoTarjetaCredito")) {
/* 2177:2102 */           addErrorMessage(getLanguageController().getMensaje("msg_tipo_tarjeta_credito_tickets") + "\n Ticket No: " + 
/* 2178:2103 */             ticketsErroneos(nTkt, razon));
/* 2179:2104 */         } else if (razon.equals("numeroDocumentoRelacionado")) {
/* 2180:2105 */           addErrorMessage(getLanguageController().getMensaje("msg_numero_relacionado_tickets") + "\n Ticket No: " + 
/* 2181:2106 */             ticketsErroneos(nTkt, razon));
/* 2182:2107 */         } else if (razon.equals("tipoEmision")) {
/* 2183:2108 */           addErrorMessage(getLanguageController().getMensaje("msg_tipo_emision_tickets") + "\n Ticket No: " + 
/* 2184:2109 */             ticketsErroneos(nTkt, razon));
/* 2185:2110 */         } else if (razon.equals("EC")) {
/* 2186:2111 */           addErrorMessage(getLanguageController().getMensaje("msg_impuesto_ec_tickets") + "\n Ticket No: " + 
/* 2187:2112 */             ticketsErroneos(nTkt, razon));
/* 2188:2113 */         } else if (razon.equals("IdentificacionTributaria")) {
/* 2189:2114 */           addErrorMessage(getLanguageController().getMensaje("msg_identificacion_tributaria_tickets") + "\n Ticket No: " + 
/* 2190:2115 */             ticketsErroneos(nTkt, razon));
/* 2191:2116 */         } else if (razon.equals("fechaViaje")) {
/* 2192:2117 */           addErrorMessage(getLanguageController().getMensaje("msg_fecha_viaje_tickets") + "\n Ticket No: " + 
/* 2193:2118 */             ticketsErroneos(nTkt, razon));
/* 2194:2119 */         } else if (razon.equals("CodigoDeServicio")) {
/* 2195:2120 */           addErrorMessage(getLanguageController().getMensaje("msg_codigo_servicio_tickets") + "\n Ticket No: " + 
/* 2196:2121 */             ticketsErroneos(nTkt, razon));
/* 2197:     */         }
/* 2198:     */       }
/* 2199:     */       else
/* 2200:     */       {
/* 2201:2123 */         if (!this.bsp.getDocumento().getNombre().contains("BSP")) {
/* 2202:2124 */           for (Map.Entry<String, Ticket> entry : ((HashMap)clavesDB).entrySet()) {
/* 2203:2125 */             if (this.servicioReporteCobroCliente.countTickets(AppUtil.getOrganizacion().getId(), ((Ticket)entry.getValue()).getPeriodo(), 
/* 2204:2126 */               ((Ticket)entry.getValue()).getOperacion(), ((Ticket)entry.getValue()).getPuntoDeVenta().getCodigoAlterno()).longValue() > 0L) {
/* 2205:2127 */               verificadorNoRepetidos = Boolean.valueOf(false);
/* 2206:     */             }
/* 2207:     */           }
/* 2208:     */         }
/* 2209:2132 */         if (verificadorNoRepetidos.booleanValue())
/* 2210:     */         {
/* 2211:2133 */           addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 2212:     */         }
/* 2213:     */         else
/* 2214:     */         {
/* 2215:2135 */           this.listTickets.clear();
/* 2216:2136 */           addErrorMessage(getLanguageController().getMensaje("msg_tickets_repetidos") + "\n Ticket No: " + 
/* 2217:2137 */             ticketsErroneos(nTkt, razon));
/* 2218:     */         }
/* 2219:     */       }
/* 2220:     */     }
/* 2221:     */     catch (ExcepcionAS2 e)
/* 2222:     */     {
/* 2223:2141 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 2224:2142 */       e.printStackTrace();
/* 2225:     */     }
/* 2226:     */     catch (AS2Exception e)
/* 2227:     */     {
/* 2228:2144 */       JsfUtil.addErrorMessage(e, "");
/* 2229:2145 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 2230:2146 */       e.printStackTrace();
/* 2231:     */     }
/* 2232:     */     catch (Exception e)
/* 2233:     */     {
/* 2234:2149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 2235:2150 */       e.printStackTrace();
/* 2236:     */     }
/* 2237:2152 */     return null;
/* 2238:     */   }
/* 2239:     */   
/* 2240:     */   private String ticketsErroneos(List<String> s, String valor)
/* 2241:     */   {
/* 2242:2156 */     String tkt = "";
/* 2243:2157 */     for (String st : s) {
/* 2244:2158 */       if (st.contains(valor)) {
/* 2245:2159 */         tkt = tkt + st.replace(valor, "") + "\n";
/* 2246:     */       }
/* 2247:     */     }
/* 2248:2161 */     return tkt;
/* 2249:     */   }
/* 2250:     */   
/* 2251:     */   public List<Ticket> getListTickets()
/* 2252:     */   {
/* 2253:2165 */     return this.listTickets;
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   public void setListTickets(List<Ticket> listTickets)
/* 2257:     */   {
/* 2258:2169 */     this.listTickets = listTickets;
/* 2259:     */   }
/* 2260:     */   
/* 2261:     */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 2262:     */   {
/* 2263:2173 */     return this.listaDetalleAsiento == null ? (this.listaDetalleAsiento = new ArrayList()) : this.listaDetalleAsiento;
/* 2264:     */   }
/* 2265:     */   
/* 2266:     */   public void setListaDetalleAsiento(List<DetalleAsiento> listaDetalleAsiento)
/* 2267:     */   {
/* 2268:2177 */     this.listaDetalleAsiento = listaDetalleAsiento;
/* 2269:     */   }
/* 2270:     */   
/* 2271:     */   public BigDecimal getTotalDebitoAsi()
/* 2272:     */   {
/* 2273:2181 */     return this.totalDebitoAsi;
/* 2274:     */   }
/* 2275:     */   
/* 2276:     */   public void setTotalDebitoAsi(BigDecimal totalDebitoAsi)
/* 2277:     */   {
/* 2278:2185 */     this.totalDebitoAsi = totalDebitoAsi;
/* 2279:     */   }
/* 2280:     */   
/* 2281:     */   public BigDecimal getTotalCreditoAsi()
/* 2282:     */   {
/* 2283:2189 */     return this.totalCreditoAsi;
/* 2284:     */   }
/* 2285:     */   
/* 2286:     */   public void setTotalCreditoAsi(BigDecimal totalCreditoAsi)
/* 2287:     */   {
/* 2288:2193 */     this.totalCreditoAsi = totalCreditoAsi;
/* 2289:     */   }
/* 2290:     */   
/* 2291:     */   public List<Ticket> getListaTicketNoEditados()
/* 2292:     */   {
/* 2293:2197 */     return this.listaTicketNoEditados == null ? (this.listaTicketNoEditados = new ArrayList()) : this.listaTicketNoEditados;
/* 2294:     */   }
/* 2295:     */   
/* 2296:     */   public void setListaTicketNoEditados(List<Ticket> listaTicketNoEditados)
/* 2297:     */   {
/* 2298:2201 */     this.listaTicketNoEditados = listaTicketNoEditados;
/* 2299:     */   }
/* 2300:     */   
/* 2301:     */   public CargaArchivo getBspParaEdicion()
/* 2302:     */   {
/* 2303:2205 */     return this.bspParaEdicion == null ? (this.bspParaEdicion = new CargaArchivo()) : this.bspParaEdicion;
/* 2304:     */   }
/* 2305:     */   
/* 2306:     */   public void setBspParaEdicion(CargaArchivo bspParaEdicion)
/* 2307:     */   {
/* 2308:2209 */     this.bspParaEdicion = bspParaEdicion;
/* 2309:     */   }
/* 2310:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.CargaVentaTicketBean
 * JD-Core Version:    0.7.0.1
 */