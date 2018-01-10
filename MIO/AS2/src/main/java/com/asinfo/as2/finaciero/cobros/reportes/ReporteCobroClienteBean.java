/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.controller.EmpresaBean;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  10:    */ import com.asinfo.as2.entities.Recaudador;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.Zona;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  20:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Calendar;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ManagedProperty;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class ReporteCobroClienteBean
/*  39:    */   extends AbstractClientReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = -2421206896625032463L;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioDocumento servicioDocumento;
/*  46:    */   private Documento documento;
/*  47:    */   
/*  48:    */   static enum TipoReporte
/*  49:    */   {
/*  50: 54 */     DEFAULT("Normal"),  FORMA_PAGO("Por Forma Pago"),  VENDEDOR("Por Vendedor"),  FACTURAS_CANCELADAS("Facturas Canceladas");
/*  51:    */     
/*  52:    */     private String nombre;
/*  53:    */     
/*  54:    */     private TipoReporte(String nombre)
/*  55:    */     {
/*  56: 63 */       this.nombre = nombre;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public String getNombre()
/*  60:    */     {
/*  61: 72 */       return this.nombre;
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65: 90 */   private TipoReporte tipoReporte = TipoReporte.DEFAULT;
/*  66:    */   private boolean posfechados;
/*  67:    */   private List<Documento> listaDocumento;
/*  68:    */   @ManagedProperty("#{empresaBean}")
/*  69:    */   private EmpresaBean empresaBean;
/*  70:    */   
/*  71:    */   protected JRDataSource getJRDataSource()
/*  72:    */   {
/*  73:109 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  74:110 */     JRDataSource ds = null;
/*  75:111 */     String[] fields = null;
/*  76:    */     try
/*  77:    */     {
/*  78:114 */       validaDatos();
/*  79:115 */       if ((this.tipoReporte == TipoReporte.DEFAULT) && ((this.indicadorResumen) || (!this.indicadorResumen)))
/*  80:    */       {
/*  81:116 */         listaDatosReporte = this.servicioReporteCobroCliente.getReporteCobroCliente(this.fechaDesde, this.fechaHasta, this.empresa
/*  82:117 */           .getIdEmpresa(), this.recaudador, AppUtil.getOrganizacion().getId(), false, getDocumento().getId(), this.posfechados, this.sucursal, this.puntoVenta, this.agenteComercial, this.zona);
/*  83:    */         
/*  84:119 */         fields = new String[] { "f_identificacion", "f_nombreFiscal", "f_numero", "f_fecha", "f_factura", "f_estado", "f_descripcion", "f_asiento", "f_tipoAsiento", "f_valor", "f_recaudador", "f_usuarioRegistro", "f_fechaCancelacion", "f_fechaVencimiento", "f_codigoDocumento", "f_fechaFactura", "f_vendedor", "f_numeroPacking", "f_consignatario" };
/*  85:    */       }
/*  86:123 */       else if (this.tipoReporte == TipoReporte.FORMA_PAGO)
/*  87:    */       {
/*  88:124 */         listaDatosReporte = this.servicioReporteCobroCliente.getReporteCobroClientePorFormaPago(this.fechaDesde, this.fechaHasta, this.empresa
/*  89:125 */           .getIdEmpresa(), this.recaudador, AppUtil.getOrganizacion().getId(), this.indicadorResumen, 
/*  90:126 */           getDocumento().getId(), this.posfechados, this.sucursal, this.zona, this.puntoVenta, this.agenteComercial);
/*  91:127 */         fields = new String[] { "f_nombreCliente", "f_nombreFormaPago", "f_valor", "f_numero", "f_fecha", "f_descripcion", "f_numeroFactura" };
/*  92:    */       }
/*  93:128 */       else if (this.tipoReporte == TipoReporte.FACTURAS_CANCELADAS)
/*  94:    */       {
/*  95:129 */         listaDatosReporte = this.servicioReporteCobroCliente.facturasCanceladas(this.fechaDesde, this.fechaHasta, 
/*  96:130 */           AppUtil.getOrganizacion().getIdOrganizacion(), getSucursal(), this.zona);
/*  97:131 */         fields = new String[] { "f_agenteComercial", "f_numeroFactura", "f_fechaFactura", "f_cliente", "f_totalFactura", "f_idFacturaCliente", "f_subcategoriaProducto", "f_totalCredito", "f_impuestoFactura", "f_impuestoNotaCredito", "f_totalNeto", "f_numeroPacking" };
/*  98:    */       }
/*  99:    */       else
/* 100:    */       {
/* 101:135 */         listaDatosReporte = this.servicioReporteCobroCliente.getReporteCobroCliente(this.fechaDesde, this.fechaHasta, this.empresa
/* 102:136 */           .getIdEmpresa(), this.recaudador, AppUtil.getOrganizacion().getId(), true, getDocumento().getId(), this.posfechados, this.sucursal, this.puntoVenta, this.agenteComercial, this.zona);
/* 103:    */         
/* 104:138 */         fields = new String[] { "f_identificacion", "f_nombreFiscal", "f_numero", "f_fecha", "f_factura", "f_estado", "f_descripcion", "f_asiento", "f_tipoAsiento", "f_valor", "f_recaudador", "f_usuarioRegistro", "f_fechaCancelacion", "f_fechaVencimiento", "f_codigoDocumento", "f_fechaFactura", "f_vendedor", "f_numeroPacking", "f_consignatario" };
/* 105:    */       }
/* 106:144 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 107:    */     }
/* 108:    */     catch (ExcepcionAS2 e)
/* 109:    */     {
/* 110:146 */       LOG.info("Error " + e);
/* 111:147 */       e.printStackTrace();
/* 112:    */     }
/* 113:149 */     return ds;
/* 114:    */   }
/* 115:    */   
/* 116:    */   @PostConstruct
/* 117:    */   public void init()
/* 118:    */   {
/* 119:154 */     Calendar calfechaDesde = Calendar.getInstance();
/* 120:155 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 121:156 */     this.fechaDesde = calfechaDesde.getTime();
/* 122:157 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected String getCompileFileName()
/* 126:    */   {
/* 127:168 */     switch (1.$SwitchMap$com$asinfo$as2$finaciero$cobros$reportes$ReporteCobroClienteBean$TipoReporte[this.tipoReporte.ordinal()])
/* 128:    */     {
/* 129:    */     case 1: 
/* 130:170 */       if (this.indicadorResumen) {
/* 131:171 */         return "reporteCobroPagoResumido";
/* 132:    */       }
/* 133:173 */       return "reporteCobroPago";
/* 134:    */     case 2: 
/* 135:176 */       if (this.indicadorResumen) {
/* 136:177 */         return "reporteCobroFormaPagoResumido";
/* 137:    */       }
/* 138:179 */       return "reporteCobroFormaPago";
/* 139:    */     case 3: 
/* 140:182 */       if (this.indicadorResumen) {
/* 141:183 */         return "reporteCobroPorVendedorResumido";
/* 142:    */       }
/* 143:185 */       return "reporteCobroPorVendedor";
/* 144:    */     case 4: 
/* 145:188 */       return "reporteFacturasCanceladas";
/* 146:    */     }
/* 147:190 */     return "reporteCobroPago";
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected Map<String, Object> getReportParameters()
/* 151:    */   {
/* 152:202 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 153:203 */     reportParameters.put("ReportTitle", "Cobros");
/* 154:204 */     reportParameters.put("usuario", "Usuario:");
/* 155:205 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/* 156:206 */     reportParameters.put("pagina", "Pagina:");
/* 157:207 */     reportParameters.put("desde", "Desde:");
/* 158:208 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 159:209 */     reportParameters.put("hasta", "Hasta:");
/* 160:210 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 161:211 */     reportParameters.put("p_total", "Total");
/* 162:212 */     reportParameters.put("p_identificacion", "Identificacion ");
/* 163:213 */     reportParameters.put("p_nombreFiscal", "Nombre Fiscal ");
/* 164:214 */     reportParameters.put("p_numero", "Numero ");
/* 165:215 */     reportParameters.put("p_fecha", "Fecha");
/* 166:216 */     reportParameters.put("p_descripcion", "Descripcion");
/* 167:217 */     reportParameters.put("p_factura", "Factura");
/* 168:218 */     reportParameters.put("p_estado", "Estado");
/* 169:219 */     reportParameters.put("p_asiento", "Asiento");
/* 170:220 */     reportParameters.put("p_tipoAsiento", "Tipo Asiento");
/* 171:221 */     reportParameters.put("p_valor", "Valor");
/* 172:222 */     reportParameters.put("p_total", "Total");
/* 173:223 */     reportParameters.put("p_fechaVencimiento", "Fecha Vencimiento");
/* 174:224 */     reportParameters.put("p_fechaCancelacion", "Fecha Cancelacion");
/* 175:225 */     reportParameters.put("p_dias", "Dias");
/* 176:226 */     reportParameters.put("p_recaudador", this.recaudador != null ? this.recaudador.getNombre() : "Todos");
/* 177:227 */     reportParameters.put("p_documento", getDocumento().getId() != 0 ? getDocumento().getNombre() : "Todos");
/* 178:228 */     reportParameters.put("p_vendedor", this.agenteComercial != null ? this.agenteComercial
/* 179:229 */       .getNombre1().concat(" ").concat(this.agenteComercial.getNombre2()) : "Todos");
/* 180:    */     
/* 181:231 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 182:232 */     reportParameters.put("p_punto_venta", this.puntoVenta != null ? this.puntoVenta.getNombre() : "Todos");
/* 183:233 */     reportParameters.put("p_zona", this.zona != null ? this.zona.getNombre() : "Todos");
/* 184:    */     
/* 185:235 */     return reportParameters;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String execute()
/* 189:    */   {
/* 190:    */     try
/* 191:    */     {
/* 192:246 */       if (this.fechaDesde == null) {
/* 193:247 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 194:    */       }
/* 195:249 */       if (this.fechaHasta == null) {
/* 196:250 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 197:    */       }
/* 198:253 */       super.prepareReport();
/* 199:    */     }
/* 200:    */     catch (JRException e)
/* 201:    */     {
/* 202:255 */       LOG.info("Error JRException");
/* 203:256 */       e.printStackTrace();
/* 204:257 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 205:    */     }
/* 206:    */     catch (IOException e)
/* 207:    */     {
/* 208:259 */       LOG.info("Error IOException");
/* 209:260 */       e.printStackTrace();
/* 210:261 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 211:    */     }
/* 212:263 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void validaDatos()
/* 216:    */   {
/* 217:267 */     if (this.empresa == null)
/* 218:    */     {
/* 219:268 */       this.empresa = new Empresa();
/* 220:269 */       this.empresa.setId(0);
/* 221:    */     }
/* 222:271 */     if (this.fechaDesde == null) {
/* 223:272 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 224:    */     }
/* 225:274 */     if (this.fechaHasta == null) {
/* 226:275 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public EmpresaBean getEmpresaBean()
/* 231:    */   {
/* 232:280 */     return this.empresaBean;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setEmpresaBean(EmpresaBean empresaBean)
/* 236:    */   {
/* 237:284 */     this.empresaBean = empresaBean;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public TipoReporte getTipoReporte()
/* 241:    */   {
/* 242:293 */     return this.tipoReporte;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 246:    */   {
/* 247:303 */     this.tipoReporte = tipoReporte;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Documento getDocumento()
/* 251:    */   {
/* 252:312 */     if (this.documento == null) {
/* 253:313 */       this.documento = new Documento();
/* 254:    */     }
/* 255:315 */     return this.documento;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setDocumento(Documento documento)
/* 259:    */   {
/* 260:325 */     this.documento = documento;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public List<SelectItem> getListaTipoReporte()
/* 264:    */   {
/* 265:334 */     List<SelectItem> lista = new ArrayList();
/* 266:335 */     for (TipoReporte tipoReporte : TipoReporte.values()) {
/* 267:336 */       lista.add(new SelectItem(tipoReporte, tipoReporte.getNombre()));
/* 268:    */     }
/* 269:338 */     return lista;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public List<Documento> getListaDocumento()
/* 273:    */   {
/* 274:347 */     if (this.listaDocumento == null) {
/* 275:    */       try
/* 276:    */       {
/* 277:349 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.COBRO_CLIENTE);
/* 278:    */       }
/* 279:    */       catch (ExcepcionAS2 e)
/* 280:    */       {
/* 281:351 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 282:    */       }
/* 283:    */     }
/* 284:354 */     return this.listaDocumento;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public boolean isPosfechados()
/* 288:    */   {
/* 289:361 */     return this.posfechados;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setPosfechados(boolean posfechados)
/* 293:    */   {
/* 294:369 */     this.posfechados = posfechados;
/* 295:    */   }
/* 296:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteCobroClienteBean
 * JD-Core Version:    0.7.0.1
 */