/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Canal;
/*   8:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.Zona;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  19:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  20:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  21:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  22:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Calendar;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  34:    */ import net.sf.jasperreports.engine.JRException;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class ReporteProductoPedidoVSFacturaBean
/*  40:    */   extends AbstractBaseReportBean
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  43:    */   @EJB
/*  44:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  45:    */   @EJB
/*  46:    */   private ServicioEmpresa servicioEmpresa;
/*  47:    */   @EJB
/*  48:    */   private ServicioZona servicioZona;
/*  49:    */   @EJB
/*  50:    */   private ServicioCanal servicioCanal;
/*  51:    */   @EJB
/*  52:    */   private ServicioUsuario servicioUsuario;
/*  53:    */   @EJB
/*  54:    */   private ServicioSucursal servicioSucursal;
/*  55:    */   @EJB
/*  56:    */   private ServicioPedidoCliente servicioPedidoCliente;
/*  57:    */   private Date fechaDesde;
/*  58:    */   private Date fechaHasta;
/*  59:    */   private int idCategoriaEmpresa;
/*  60:    */   private Empresa empresa;
/*  61:    */   private int idZona;
/*  62:    */   private int idCanal;
/*  63:    */   private int idVendedor;
/*  64:    */   private int idSucursal;
/*  65:    */   private DocumentoBase documentoBase;
/*  66:    */   private List<CategoriaEmpresa> listaCategoriaEmpresaCombo;
/*  67:    */   private List<Zona> listaZonaCombo;
/*  68:    */   private List<Canal> listaCanalCombo;
/*  69:    */   private List<EntidadUsuario> listaVendedorCombo;
/*  70:    */   private List<Sucursal> listaSucursalCombo;
/*  71:    */   private List<DocumentoBase> listaDocumentoBaseCombo;
/*  72:    */   
/*  73:    */   protected JRDataSource getJRDataSource()
/*  74:    */   {
/*  75:102 */     List listaDatosReporte = new ArrayList();
/*  76:103 */     JRDataSource ds = null;
/*  77:104 */     listaDatosReporte = this.servicioPedidoCliente.getReporteProductoPedidoVsFactura(this.fechaDesde, this.fechaHasta, this.idCategoriaEmpresa, this.empresa.getId(), this.idZona, this.idCanal, this.idVendedor, this.idSucursal, 
/*  78:105 */       AppUtil.getOrganizacion().getId(), this.documentoBase);
/*  79:106 */     String[] fields = null;
/*  80:107 */     if (this.documentoBase.equals(DocumentoBase.FACTURA_CLIENTE)) {
/*  81:108 */       fields = new String[] { "idDetallePedidoCliente", "fechaFactura", "numeroFactura", "identificacion", "nombreFiscal", "vendedor", "canal", "zona", "sucursal", "codigoProducto", "nombreProducto", "codigoSubcategoriaProducto", "nombreSubcategoriaProducto", "cantidadFactura", "precioFactura", "descuentoFactura", "totalFactura", "cantidadPedido", "precioPedido", "descuentoPedido", "totalPedido", "fechaPedido", "numeroPedido", "unidadVenta" };
/*  82:113 */     } else if (this.documentoBase.equals(DocumentoBase.PEDIDO_CLIENTE)) {
/*  83:114 */       fields = new String[] { "idDetallePedidoCliente", "fechaPedido", "numeroPedido", "identificacion", "nombreFiscal", "vendedor", "canal", "zona", "sucursal", "codigoProducto", "nombreProducto", "codigoSubcategoriaProducto", "nombreSubcategoriaProducto", "cantidadPedido", "precioPedido", "descuentoPedido", "totalPedido", "cantidadFactura", "precioFactura", "descuentoFactura", "totalFactura", "fechaFactura", "numeroFactura", "unidadVenta" };
/*  84:    */     }
/*  85:120 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  86:    */     
/*  87:122 */     return ds;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @PostConstruct
/*  91:    */   public void init()
/*  92:    */   {
/*  93:127 */     Calendar calfechaDesde = Calendar.getInstance();
/*  94:128 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  95:129 */     this.fechaDesde = calfechaDesde.getTime();
/*  96:130 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected String getCompileFileName()
/* 100:    */   {
/* 101:140 */     return "reportePedidoProductoVSFactura";
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected Map<String, Object> getReportParameters()
/* 105:    */   {
/* 106:150 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 107:151 */     reportParameters.put("ReportTitle", "Reporte Pedido vs Factura");
/* 108:152 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 109:153 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 110:154 */     reportParameters.put("Total", "Total");
/* 111:155 */     return reportParameters;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String execute()
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:165 */       validaDatos();
/* 119:166 */       super.prepareReport();
/* 120:    */     }
/* 121:    */     catch (JRException e)
/* 122:    */     {
/* 123:168 */       LOG.info("Error JRException");
/* 124:169 */       e.printStackTrace();
/* 125:170 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 126:    */     }
/* 127:    */     catch (IOException e)
/* 128:    */     {
/* 129:172 */       LOG.info("Error IOException");
/* 130:173 */       e.printStackTrace();
/* 131:174 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 132:    */     }
/* 133:177 */     return null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void validaDatos()
/* 137:    */   {
/* 138:181 */     if (this.empresa == null)
/* 139:    */     {
/* 140:182 */       this.empresa = new Empresa();
/* 141:183 */       this.empresa.setId(0);
/* 142:    */     }
/* 143:185 */     if (this.fechaDesde == null) {
/* 144:186 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 145:    */     }
/* 146:188 */     if (this.fechaHasta == null) {
/* 147:189 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Date getFechaDesde()
/* 152:    */   {
/* 153:199 */     return this.fechaDesde;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setFechaDesde(Date fechaDesde)
/* 157:    */   {
/* 158:209 */     this.fechaDesde = fechaDesde;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Date getFechaHasta()
/* 162:    */   {
/* 163:218 */     return this.fechaHasta;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setFechaHasta(Date fechaHasta)
/* 167:    */   {
/* 168:228 */     this.fechaHasta = fechaHasta;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getIdCategoriaEmpresa()
/* 172:    */   {
/* 173:232 */     return this.idCategoriaEmpresa;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setIdCategoriaEmpresa(int idCategoriaEmpresa)
/* 177:    */   {
/* 178:236 */     this.idCategoriaEmpresa = idCategoriaEmpresa;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Empresa getEmpresa()
/* 182:    */   {
/* 183:245 */     if (this.empresa == null)
/* 184:    */     {
/* 185:246 */       this.empresa = new Empresa();
/* 186:247 */       this.empresa.setId(0);
/* 187:    */     }
/* 188:249 */     return this.empresa;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setEmpresa(Empresa empresa)
/* 192:    */   {
/* 193:259 */     this.empresa = empresa;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int getIdVendedor()
/* 197:    */   {
/* 198:268 */     return this.idVendedor;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setIdVendedor(int idVendedor)
/* 202:    */   {
/* 203:278 */     this.idVendedor = idVendedor;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int getIdCanal()
/* 207:    */   {
/* 208:287 */     return this.idCanal;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setIdCanal(int idCanal)
/* 212:    */   {
/* 213:297 */     this.idCanal = idCanal;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public int getIdZona()
/* 217:    */   {
/* 218:306 */     return this.idZona;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setIdZona(int idZona)
/* 222:    */   {
/* 223:316 */     this.idZona = idZona;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public int getIdSucursal()
/* 227:    */   {
/* 228:325 */     return this.idSucursal;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setIdSucursal(int idSucursal)
/* 232:    */   {
/* 233:335 */     this.idSucursal = idSucursal;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public DocumentoBase getDocumentoBase()
/* 237:    */   {
/* 238:339 */     return this.documentoBase;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 242:    */   {
/* 243:343 */     this.documentoBase = documentoBase;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresaCombo()
/* 247:    */   {
/* 248:347 */     if (this.listaCategoriaEmpresaCombo == null) {
/* 249:348 */       this.listaCategoriaEmpresaCombo = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/* 250:    */     }
/* 251:350 */     return this.listaCategoriaEmpresaCombo;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaCategoriaEmpresaCombo(List<CategoriaEmpresa> listaCategoriaEmpresaCombo)
/* 255:    */   {
/* 256:354 */     this.listaCategoriaEmpresaCombo = listaCategoriaEmpresaCombo;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 260:    */   {
/* 261:358 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<Zona> getListaZonaCombo()
/* 265:    */   {
/* 266:367 */     if (this.listaZonaCombo == null) {
/* 267:368 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 268:    */     }
/* 269:370 */     return this.listaZonaCombo;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setListaZonaCombo(List<Zona> listaZonaCombo)
/* 273:    */   {
/* 274:380 */     this.listaZonaCombo = listaZonaCombo;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public List<Canal> getListaCanalCombo()
/* 278:    */   {
/* 279:389 */     if (this.listaCanalCombo == null) {
/* 280:390 */       this.listaCanalCombo = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 281:    */     }
/* 282:392 */     return this.listaCanalCombo;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setListaCanalCombo(List<Canal> listaCanalCombo)
/* 286:    */   {
/* 287:402 */     this.listaCanalCombo = listaCanalCombo;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public List<EntidadUsuario> getListaVendedorCombo()
/* 291:    */   {
/* 292:411 */     if (this.listaVendedorCombo == null) {
/* 293:412 */       this.listaVendedorCombo = this.servicioUsuario.getEntidadUsuario(AppUtil.getOrganizacion().getId(), true, AppUtil.getSucursal());
/* 294:    */     }
/* 295:414 */     return this.listaVendedorCombo;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setListaVendedorCombo(List<EntidadUsuario> listaVendedorCombo)
/* 299:    */   {
/* 300:424 */     this.listaVendedorCombo = listaVendedorCombo;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public List<Sucursal> getListaSucursalCombo()
/* 304:    */   {
/* 305:433 */     if (this.listaSucursalCombo == null) {
/* 306:434 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 307:    */     }
/* 308:436 */     return this.listaSucursalCombo;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 312:    */   {
/* 313:446 */     this.listaSucursalCombo = listaSucursalCombo;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<DocumentoBase> getListaDocumentoBaseCombo()
/* 317:    */   {
/* 318:450 */     if (this.listaDocumentoBaseCombo == null)
/* 319:    */     {
/* 320:451 */       this.listaDocumentoBaseCombo = new ArrayList();
/* 321:452 */       this.listaDocumentoBaseCombo.add(DocumentoBase.FACTURA_CLIENTE);
/* 322:453 */       this.listaDocumentoBaseCombo.add(DocumentoBase.PEDIDO_CLIENTE);
/* 323:    */     }
/* 324:455 */     return this.listaDocumentoBaseCombo;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setListaDocumentoBaseCombo(List<DocumentoBase> listaDocumentoBaseCombo)
/* 328:    */   {
/* 329:459 */     this.listaDocumentoBaseCombo = listaDocumentoBaseCombo;
/* 330:    */   }
/* 331:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteProductoPedidoVSFacturaBean
 * JD-Core Version:    0.7.0.1
 */