/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   8:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.text.ParseException;
/*  16:    */ import java.text.SimpleDateFormat;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import javax.faces.model.SelectItem;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ConsultaVoucherBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 1L;
/*  38:    */   @EJB
/*  39:    */   protected ServicioGenerico<DetalleFormaCobro> servicioRegistroVoucher;
/*  40:    */   @EJB
/*  41:    */   protected ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  42:    */   @EJB
/*  43:    */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  44:    */   private Date fechaDesde;
/*  45:    */   private Date fechaHasta;
/*  46:    */   
/*  47:    */   static enum TipoReporte
/*  48:    */   {
/*  49: 60 */     FECHA_REGISTRO("Fecha Voucher"),  FECHA_PAGO("Fecha de pago");
/*  50:    */     
/*  51:    */     private String nombre;
/*  52:    */     
/*  53:    */     private TipoReporte(String nombre)
/*  54:    */     {
/*  55: 68 */       this.nombre = nombre;
/*  56:    */     }
/*  57:    */     
/*  58:    */     public String getNombre()
/*  59:    */     {
/*  60: 77 */       return this.nombre;
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64: 90 */   private Date fechaReporte = new Date();
/*  65:    */   private PuntoDeVenta puntoVenta;
/*  66:    */   private List<PuntoDeVenta> listaPuntoDeVenta;
/*  67:    */   private LazyDataModel<DetalleFormaCobro> listaVoucher;
/*  68: 94 */   private TipoReporte tipoReporte = TipoReporte.FECHA_REGISTRO;
/*  69: 95 */   private static List<String> campos = new ArrayList();
/*  70:    */   
/*  71:    */   static
/*  72:    */   {
/*  73: 97 */     campos.add("banco");
/*  74: 98 */     campos.add("cobro");
/*  75: 99 */     campos.add("cobro.documento");
/*  76:100 */     campos.add("cobroTarjeta.documento");
/*  77:101 */     campos.add("puntoVenta");
/*  78:102 */     campos.add("planTarjetaCredito");
/*  79:103 */     campos.add("tarjetaCredito.banco");
/*  80:104 */     campos.add("tarjetaCredito.tipoTarjetaCredito");
/*  81:    */   }
/*  82:    */   
/*  83:    */   @PostConstruct
/*  84:    */   public void init()
/*  85:    */   {
/*  86:110 */     this.listaVoucher = new LazyDataModel()
/*  87:    */     {
/*  88:    */       private static final long serialVersionUID = 1L;
/*  89:    */       
/*  90:    */       public List<DetalleFormaCobro> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  91:    */       {
/*  92:115 */         List<DetalleFormaCobro> lista = new ArrayList();
/*  93:    */         
/*  94:117 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  95:118 */         if (ConsultaVoucherBean.this.puntoVenta != null) {
/*  96:119 */           filters.put("puntoVenta.codigoAlterno", "" + ConsultaVoucherBean.this.puntoVenta.getCodigoAlterno());
/*  97:    */         }
/*  98:122 */         String filtroFechaCobro = (String)filters.get("cobroTarjeta.fecha");
/*  99:123 */         if (filtroFechaCobro != null) {
/* 100:125 */           if (filtroFechaCobro.length() != 10) {
/* 101:126 */             filters.remove("cobroTarjeta.fecha");
/* 102:    */           } else {
/* 103:    */             try
/* 104:    */             {
/* 105:129 */               SimpleDateFormat sdf = new SimpleDateFormat(ConsultaVoucherBean.this.getFormatoFecha());
/* 106:130 */               Date fechaPago = sdf.parse(filtroFechaCobro);
/* 107:131 */               SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
/* 108:132 */               filters.put("cobroTarjeta.fecha", "=" + sdf2.format(fechaPago));
/* 109:    */             }
/* 110:    */             catch (ParseException e)
/* 111:    */             {
/* 112:135 */               e.printStackTrace();
/* 113:    */             }
/* 114:    */           }
/* 115:    */         }
/* 116:141 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 117:142 */         lista = ConsultaVoucherBean.this.servicioRegistroVoucher.obtenerListaPorPagina(DetalleFormaCobro.class, first, pageSize, sortField, ordenar, filters, ConsultaVoucherBean.campos);
/* 118:143 */         ConsultaVoucherBean.this.listaVoucher.setRowCount(ConsultaVoucherBean.this.servicioRegistroVoucher.contarPorCriterio(DetalleFormaCobro.class, filters));
/* 119:    */         
/* 120:145 */         return lista;
/* 121:    */       }
/* 122:    */     };
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected JRDataSource getJRDataSource()
/* 126:    */   {
/* 127:152 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 128:153 */     JRDataSource ds = null;
/* 129:    */     try
/* 130:    */     {
/* 131:155 */       String[] fields = null;
/* 132:156 */       if (this.tipoReporte.nombre.equals("Fecha Voucher"))
/* 133:    */       {
/* 134:157 */         listaDatosReporte = this.servicioReporteCobroCliente.getReporteConsultaVoucher(AppUtil.getOrganizacion().getId(), this.puntoVenta, this.fechaDesde, this.fechaHasta, "Fecha de registro");
/* 135:    */         
/* 136:159 */         fields = new String[] { "f_secuencial", "f_tcNombre", "f_ttcNombre", "f_NumeroTarjeta", "f_baseImponibleDiferenteCero", "f_baseImponibleTarifaCero", "f_montoIva", "f_interes", "f_valor", "f_documentoReferencia", "f_lote", "f_fechaVoucher", "f_pvNombre", "f_pvCodigoAlterno", "f_fechaReporte", "f_agencia", "f_ptcCodigo", "f_dfcDescripcion", "f_ctNumero", "f_ctFecha", "f_bdfcNombre", "f_bancoNombre", "f_ctValor", "f_porcentajeComision", "f_valorComision", "f_valorPagado", "f_cobroNumero", "f_cobroFecha", "f_cobroValor", "f_fechaPago", "f_secuencia", "f_impuestoComision" };
/* 137:    */       }
/* 138:    */       else
/* 139:    */       {
/* 140:166 */         listaDatosReporte = this.servicioReporteCobroCliente.getReporteConsultaVoucher(AppUtil.getOrganizacion().getId(), this.puntoVenta, this.fechaDesde, this.fechaHasta, "");
/* 141:    */         
/* 142:168 */         fields = new String[] { "f_secuencial", "f_tcNombre", "f_ttcNombre", "f_NumeroTarjeta", "f_baseImponibleDiferenteCero", "f_baseImponibleTarifaCero", "f_montoIva", "f_interes", "f_valor", "f_documentoReferencia", "f_lote", "f_fechaVoucher", "f_pvNombre", "f_pvCodigoAlterno", "f_fechaReporte", "f_agencia", "f_ptcCodigo", "f_dfcDescripcion", "f_ctNumero", "f_ctFecha", "f_bdfcNombre", "f_bancoNombre", "f_ctValor", "f_porcentajeComision", "f_valorComision", "f_valorPagado", "f_cobroNumero", "f_cobroFecha", "f_cobroValor", "f_fechaPago", "f_secuencia", "f_impuestoComision" };
/* 143:    */       }
/* 144:176 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 145:    */     }
/* 146:    */     catch (Exception e)
/* 147:    */     {
/* 148:178 */       e.printStackTrace();
/* 149:    */     }
/* 150:180 */     return ds;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected String getCompileFileName()
/* 154:    */   {
/* 155:185 */     return "reporteConsultaVoucher";
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected Map<String, Object> getReportParameters()
/* 159:    */   {
/* 160:196 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 161:197 */     reportParameters.put("ReportTitle", "Consulta Voucher");
/* 162:198 */     if (this.puntoVenta == null) {
/* 163:199 */       reportParameters.put("Estacion", "Todas");
/* 164:    */     }
/* 165:200 */     if (this.puntoVenta != null) {
/* 166:201 */       reportParameters.put("Estacion", "" + this.puntoVenta.getCodigoAlterno());
/* 167:    */     }
/* 168:202 */     reportParameters.put("Fecha", this.tipoReporte.nombre);
/* 169:203 */     reportParameters.put("FechaDesde", this.fechaDesde);
/* 170:204 */     reportParameters.put("FechaHasta", this.fechaHasta);
/* 171:    */     
/* 172:206 */     return reportParameters;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String execute()
/* 176:    */   {
/* 177:    */     try
/* 178:    */     {
/* 179:215 */       super.prepareReport();
/* 180:    */     }
/* 181:    */     catch (JRException e)
/* 182:    */     {
/* 183:218 */       LOG.info("Error JRException");
/* 184:219 */       e.printStackTrace();
/* 185:220 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 186:    */     }
/* 187:    */     catch (IOException e)
/* 188:    */     {
/* 189:222 */       LOG.info("Error IOException");
/* 190:223 */       e.printStackTrace();
/* 191:224 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 192:    */     }
/* 193:227 */     return "";
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Date getFechaDesde()
/* 197:    */   {
/* 198:231 */     if (this.fechaDesde == null) {
/* 199:232 */       this.fechaDesde = FuncionesUtiles.getFechaInicioMes(new Date());
/* 200:    */     }
/* 201:234 */     return this.fechaDesde;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setFechaDesde(Date fechaDesde)
/* 205:    */   {
/* 206:238 */     this.fechaDesde = fechaDesde;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Date getFechaHasta()
/* 210:    */   {
/* 211:242 */     if (this.fechaHasta == null) {
/* 212:243 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(new Date());
/* 213:    */     }
/* 214:245 */     return this.fechaHasta;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setFechaHasta(Date fechaHasta)
/* 218:    */   {
/* 219:249 */     this.fechaHasta = fechaHasta;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public List<PuntoDeVenta> getListaPuntoDeVenta()
/* 223:    */   {
/* 224:253 */     if (this.listaPuntoDeVenta == null) {
/* 225:254 */       this.listaPuntoDeVenta = this.servicioPuntoDeVenta.obtenerListaCombo("codigoAlterno", true, null);
/* 226:    */     }
/* 227:256 */     return this.listaPuntoDeVenta;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setListaPuntoDeVenta(List<PuntoDeVenta> listaPuntoDeVenta)
/* 231:    */   {
/* 232:260 */     this.listaPuntoDeVenta = listaPuntoDeVenta;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public PuntoDeVenta getPuntoVenta()
/* 236:    */   {
/* 237:264 */     return this.puntoVenta;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setPuntoVenta(PuntoDeVenta puntoVenta)
/* 241:    */   {
/* 242:268 */     this.puntoVenta = puntoVenta;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Date getFechaReporte()
/* 246:    */   {
/* 247:272 */     return this.fechaReporte;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setFechaReporte(Date fechaReporte)
/* 251:    */   {
/* 252:276 */     this.fechaReporte = fechaReporte;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public LazyDataModel<DetalleFormaCobro> getListaVoucher()
/* 256:    */   {
/* 257:280 */     return this.listaVoucher;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setListaVoucher(LazyDataModel<DetalleFormaCobro> listaVoucher)
/* 261:    */   {
/* 262:284 */     this.listaVoucher = listaVoucher;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public TipoReporte getTipoReporte()
/* 266:    */   {
/* 267:288 */     return this.tipoReporte;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 271:    */   {
/* 272:292 */     this.tipoReporte = tipoReporte;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public List<SelectItem> getListaTipoReporte()
/* 276:    */   {
/* 277:301 */     List<SelectItem> lista = new ArrayList();
/* 278:302 */     for (TipoReporte tipoReporte : TipoReporte.values()) {
/* 279:303 */       lista.add(new SelectItem(tipoReporte, tipoReporte.getNombre()));
/* 280:    */     }
/* 281:305 */     return lista;
/* 282:    */   }
/* 283:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ConsultaVoucherBean
 * JD-Core Version:    0.7.0.1
 */