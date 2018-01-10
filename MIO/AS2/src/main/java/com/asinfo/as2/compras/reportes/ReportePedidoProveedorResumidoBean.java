/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ManagedProperty;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.model.SelectItem;
/*  30:    */ import javax.validation.constraints.NotNull;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class ReportePedidoProveedorResumidoBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 3615647209557356681L;
/*  41:    */   @EJB
/*  42:    */   private ServicioEmpresa servicioEmpresa;
/*  43:    */   @EJB
/*  44:    */   private ServicioReportePedidoProveedor servicioReportePedidoProveedor;
/*  45:    */   @EJB
/*  46:    */   private ServicioDocumento servicioDocumento;
/*  47:    */   private Empresa empresa;
/*  48: 72 */   private Date fechaDesde = new Date();
/*  49: 73 */   private Date fechaHasta = new Date();
/*  50: 74 */   private String numeroDesde = "";
/*  51: 75 */   private String numeroHasta = "";
/*  52:    */   private Documento documento;
/*  53:    */   private List<Documento> listaDocumento;
/*  54:    */   @NotNull
/*  55:    */   private TipoReporteEnum tipoReporteEnum;
/*  56:    */   private List<SelectItem> listaTipoReporteEnum;
/*  57:    */   @NotNull
/*  58:    */   private Estado estado;
/*  59:    */   private DimensionContable dimensionContable;
/*  60:    */   private Integer numeroDimension;
/*  61:    */   private List<SelectItem> listaDimension;
/*  62:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  63:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  64:    */   
/*  65:    */   @PostConstruct
/*  66:    */   public void init()
/*  67:    */   {
/*  68: 93 */     setDocumentoBase(DocumentoBase.PEDIDO_PROVEEDOR);
/*  69: 94 */     setEstadoItemTodos(false);
/*  70: 95 */     setEstadoItemAnulado(false);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static enum TipoReporteEnum
/*  74:    */   {
/*  75:100 */     TOTALIZADO_POR_PEDIDO("Totalizado Por Pedido"),  DETALLADO_POR_PEDIDO("Detallado Por Pedido"),  DETALLADO_POR_PRODUCTO("Detallado Por Producto");
/*  76:    */     
/*  77:    */     private String nombre;
/*  78:    */     
/*  79:    */     private TipoReporteEnum(String nombre)
/*  80:    */     {
/*  81:108 */       this.nombre = nombre;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public String getNombre()
/*  85:    */     {
/*  86:117 */       return this.nombre;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public void setNombre(String nombre)
/*  90:    */     {
/*  91:128 */       this.nombre = nombre;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected JRDataSource getJRDataSource()
/*  96:    */   {
/*  97:141 */     List listaDatosReporte = new ArrayList();
/*  98:    */     
/*  99:143 */     String[] fields = new String[0];
/* 100:144 */     if (this.tipoReporteEnum == TipoReporteEnum.TOTALIZADO_POR_PEDIDO) {
/* 101:145 */       fields = new String[] { "f_numero", "f_fecha", "f_codigoEmpresa", "f_identificacionEmpresa", "f_nombreFiscalEmpresa", "f_nombreComercialEmpresa", "f_subTotal", "f_descuento", "f_impuestos", "f_descripcionPedido" };
/* 102:148 */     } else if ((this.tipoReporteEnum == TipoReporteEnum.DETALLADO_POR_PEDIDO) || (this.tipoReporteEnum == TipoReporteEnum.DETALLADO_POR_PRODUCTO)) {
/* 103:150 */       fields = new String[] { "f_numero", "f_fecha", "f_codigoEmpresa", "f_identificacionEmpresa", "f_nombreFiscalEmpresa", "f_nombreComercialEmpresa", "f_subTotal", "f_descuento", "f_impuestos", "f_descripcionPedido", "f_codigoProducto", "f_codigoBarras", "f_nombreProducto", "f_nombreComercialProducto", "f_cantidad", "f_precioUnitario", "f_descuentoUnitario", "f_descripcionProducto", "f_codigoDimension", "f_nombreDimension" };
/* 104:    */     }
/* 105:156 */     boolean totalizado = this.tipoReporteEnum == TipoReporteEnum.TOTALIZADO_POR_PEDIDO;
/* 106:157 */     boolean porProducto = this.tipoReporteEnum == TipoReporteEnum.DETALLADO_POR_PRODUCTO;
/* 107:158 */     this.numeroDimension = (this.tipoReporteEnum == TipoReporteEnum.TOTALIZADO_POR_PEDIDO ? null : this.numeroDimension);
/* 108:    */     
/* 109:    */ 
/* 110:161 */     listaDatosReporte = this.servicioReportePedidoProveedor.getListaReportePedidoProveedor(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, this.numeroDesde, this.numeroHasta, this.empresa, this.documento, totalizado, porProducto, this.dimensionContable, this.numeroDimension, this.estado);
/* 111:    */     
/* 112:    */ 
/* 113:164 */     return new QueryResultDataSource(listaDatosReporte, fields);
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected String getCompileFileName()
/* 117:    */   {
/* 118:175 */     if (this.tipoReporteEnum == TipoReporteEnum.TOTALIZADO_POR_PEDIDO) {
/* 119:176 */       return "reportePedidoProveedorTotalizadoPedido";
/* 120:    */     }
/* 121:177 */     if (this.tipoReporteEnum == TipoReporteEnum.DETALLADO_POR_PEDIDO) {
/* 122:178 */       return "reportePedidoProveedorDetalladoPedido";
/* 123:    */     }
/* 124:179 */     if (this.tipoReporteEnum == TipoReporteEnum.DETALLADO_POR_PRODUCTO) {
/* 125:180 */       return "reportePedidoProveedorDetalladoProducto";
/* 126:    */     }
/* 127:183 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected Map<String, Object> getReportParameters()
/* 131:    */   {
/* 132:193 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 133:194 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 134:195 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 135:196 */     reportParameters.put("p_documento", this.documento != null ? this.documento.getNombre() : "Todos");
/* 136:198 */     if (this.numeroDimension != null)
/* 137:    */     {
/* 138:199 */       if (this.numeroDimension.equals(Integer.valueOf(1))) {
/* 139:200 */         reportParameters.put("p_codigoDimension", getNombreDimension1());
/* 140:202 */       } else if (this.numeroDimension.equals(Integer.valueOf(2))) {
/* 141:203 */         reportParameters.put("p_codigoDimension", getNombreDimension2());
/* 142:205 */       } else if (this.numeroDimension.equals(Integer.valueOf(3))) {
/* 143:206 */         reportParameters.put("p_codigoDimension", getNombreDimension3());
/* 144:208 */       } else if (this.numeroDimension.equals(Integer.valueOf(4))) {
/* 145:209 */         reportParameters.put("p_codigoDimension", getNombreDimension4());
/* 146:211 */       } else if (this.numeroDimension.equals(Integer.valueOf(5))) {
/* 147:212 */         reportParameters.put("p_codigoDimension", getNombreDimension5());
/* 148:    */       } else {
/* 149:214 */         reportParameters.put("p_codigoDimension", "TODOS");
/* 150:    */       }
/* 151:    */     }
/* 152:    */     else {
/* 153:217 */       reportParameters.put("p_codigoDimension", "TODOS");
/* 154:    */     }
/* 155:219 */     return reportParameters;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String execute()
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:230 */       if (this.fechaDesde == null) {
/* 163:231 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 164:    */       }
/* 165:233 */       if (this.fechaHasta == null) {
/* 166:234 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 167:    */       }
/* 168:237 */       super.prepareReport();
/* 169:    */     }
/* 170:    */     catch (JRException e)
/* 171:    */     {
/* 172:239 */       LOG.info("Error JRException");
/* 173:240 */       e.printStackTrace();
/* 174:241 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 175:    */     }
/* 176:    */     catch (IOException e)
/* 177:    */     {
/* 178:243 */       LOG.info("Error IOException");
/* 179:244 */       e.printStackTrace();
/* 180:245 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 181:    */     }
/* 182:248 */     return null;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void cargarDimensionContableListener(DimensionContable dimension)
/* 186:    */   {
/* 187:252 */     setDimensionContable(dimension);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Empresa getEmpresa()
/* 191:    */   {
/* 192:263 */     return this.empresa;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setEmpresa(Empresa empresa)
/* 196:    */   {
/* 197:273 */     this.empresa = empresa;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Date getFechaDesde()
/* 201:    */   {
/* 202:282 */     return this.fechaDesde;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setFechaDesde(Date fechaDesde)
/* 206:    */   {
/* 207:292 */     this.fechaDesde = fechaDesde;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Date getFechaHasta()
/* 211:    */   {
/* 212:301 */     return this.fechaHasta;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setFechaHasta(Date fechaHasta)
/* 216:    */   {
/* 217:311 */     this.fechaHasta = fechaHasta;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String getNumeroDesde()
/* 221:    */   {
/* 222:315 */     return this.numeroDesde;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setNumeroDesde(String numeroDesde)
/* 226:    */   {
/* 227:319 */     this.numeroDesde = numeroDesde;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String getNumeroHasta()
/* 231:    */   {
/* 232:323 */     return this.numeroHasta;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setNumeroHasta(String numeroHasta)
/* 236:    */   {
/* 237:327 */     this.numeroHasta = numeroHasta;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 241:    */   {
/* 242:337 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public List<Documento> getListaDocumento()
/* 246:    */   {
/* 247:346 */     if (this.listaDocumento == null) {
/* 248:    */       try
/* 249:    */       {
/* 250:348 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PEDIDO_PROVEEDOR);
/* 251:    */       }
/* 252:    */       catch (ExcepcionAS2 e)
/* 253:    */       {
/* 254:350 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 255:    */       }
/* 256:    */     }
/* 257:353 */     return this.listaDocumento;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 261:    */   {
/* 262:363 */     this.listaDocumento = listaDocumento;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Documento getDocumento()
/* 266:    */   {
/* 267:372 */     return this.documento;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setDocumento(Documento documento)
/* 271:    */   {
/* 272:382 */     this.documento = documento;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public List<SelectItem> getListaTipoReporteEnum()
/* 276:    */   {
/* 277:387 */     if (this.listaTipoReporteEnum == null)
/* 278:    */     {
/* 279:388 */       this.listaTipoReporteEnum = new ArrayList();
/* 280:389 */       for (TipoReporteEnum tipoReporteEnum : TipoReporteEnum.values()) {
/* 281:391 */         if (tipoReporteEnum != TipoReporteEnum.DETALLADO_POR_PEDIDO)
/* 282:    */         {
/* 283:392 */           SelectItem selectItem = new SelectItem(tipoReporteEnum, tipoReporteEnum.getNombre());
/* 284:393 */           this.listaTipoReporteEnum.add(selectItem);
/* 285:    */         }
/* 286:    */       }
/* 287:    */     }
/* 288:398 */     return this.listaTipoReporteEnum;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setListaTipoReporteEnum(List<SelectItem> listaTipoReporteEnum)
/* 292:    */   {
/* 293:402 */     this.listaTipoReporteEnum = listaTipoReporteEnum;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public TipoReporteEnum getTipoReporteEnum()
/* 297:    */   {
/* 298:406 */     return this.tipoReporteEnum;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setTipoReporteEnum(TipoReporteEnum tipoReporteEnum)
/* 302:    */   {
/* 303:410 */     this.tipoReporteEnum = tipoReporteEnum;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public DimensionContable getDimensionContable()
/* 307:    */   {
/* 308:414 */     return this.dimensionContable;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 312:    */   {
/* 313:418 */     this.dimensionContable = dimensionContable;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Integer getNumeroDimension()
/* 317:    */   {
/* 318:422 */     return this.numeroDimension;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setNumeroDimension(Integer numeroDimension)
/* 322:    */   {
/* 323:426 */     this.numeroDimension = numeroDimension;
/* 324:427 */     if (numeroDimension != null) {
/* 325:428 */       this.listaDimensionContableBean.setNumeroDimension(numeroDimension.toString());
/* 326:    */     } else {
/* 327:430 */       this.listaDimensionContableBean.setNumeroDimension("0");
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public List<SelectItem> getListaDimension()
/* 332:    */   {
/* 333:435 */     if (this.listaDimension == null)
/* 334:    */     {
/* 335:436 */       this.listaDimension = new ArrayList();
/* 336:438 */       if ((getNombreDimension1() != null) && (!getNombreDimension1().isEmpty())) {
/* 337:439 */         this.listaDimension.add(new SelectItem(Integer.valueOf(1), getNombreDimension1()));
/* 338:    */       }
/* 339:442 */       if ((getNombreDimension2() != null) && (!getNombreDimension2().isEmpty())) {
/* 340:443 */         this.listaDimension.add(new SelectItem(Integer.valueOf(2), getNombreDimension2()));
/* 341:    */       }
/* 342:446 */       if ((getNombreDimension3() != null) && (!getNombreDimension3().isEmpty())) {
/* 343:447 */         this.listaDimension.add(new SelectItem(Integer.valueOf(3), getNombreDimension3()));
/* 344:    */       }
/* 345:450 */       if ((getNombreDimension4() != null) && (!getNombreDimension4().isEmpty())) {
/* 346:451 */         this.listaDimension.add(new SelectItem(Integer.valueOf(4), getNombreDimension4()));
/* 347:    */       }
/* 348:454 */       if ((getNombreDimension5() != null) && (!getNombreDimension5().isEmpty())) {
/* 349:455 */         this.listaDimension.add(new SelectItem(Integer.valueOf(5), getNombreDimension5()));
/* 350:    */       }
/* 351:    */     }
/* 352:459 */     return this.listaDimension;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setListaDimension(List<SelectItem> listaDimension)
/* 356:    */   {
/* 357:463 */     this.listaDimension = listaDimension;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 361:    */   {
/* 362:467 */     return this.listaDimensionContableBean;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 366:    */   {
/* 367:471 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Estado getEstado()
/* 371:    */   {
/* 372:475 */     return this.estado;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setEstado(Estado estado)
/* 376:    */   {
/* 377:479 */     this.estado = estado;
/* 378:    */   }
/* 379:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReportePedidoProveedorResumidoBean
 * JD-Core Version:    0.7.0.1
 */