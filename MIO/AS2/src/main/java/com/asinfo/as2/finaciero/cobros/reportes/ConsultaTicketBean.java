/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.aerolineas.Ticket;
/*   6:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*   7:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import javax.faces.model.SelectItem;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import net.sf.jasperreports.engine.JRException;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ConsultaTicketBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   protected ServicioGenerico<Ticket> servicioTicket;
/*  36:    */   @EJB
/*  37:    */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  38:    */   private Date fechaDesde;
/*  39:    */   private Date fechaHasta;
/*  40:    */   private String numeroTicket;
/*  41:    */   private String operacion;
/*  42:    */   private String agencia;
/*  43:    */   
/*  44:    */   private static enum enumTipoReporte
/*  45:    */   {
/*  46: 62 */     VENTA_LOCALES("Ventas locales"),  BSP("BSP"),  BSP_ACTUALIZADO("BSP Actualizado"),  TODOS("Todos");
/*  47:    */     
/*  48:    */     private String nombre;
/*  49:    */     
/*  50:    */     private enumTipoReporte(String nombre)
/*  51:    */     {
/*  52: 65 */       this.nombre = nombre;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public String getNombre()
/*  56:    */     {
/*  57: 69 */       return this.nombre;
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61: 73 */   private enumTipoReporte tipoReporte = enumTipoReporte.VENTA_LOCALES;
/*  62:    */   private List<SelectItem> listaTipoReporte;
/*  63:    */   private LazyDataModel<Ticket> listaTicket;
/*  64:    */   
/*  65:    */   @PostConstruct
/*  66:    */   public void init()
/*  67:    */   {
/*  68: 81 */     this.listaTicket = new LazyDataModel()
/*  69:    */     {
/*  70:    */       private static final long serialVersionUID = 1L;
/*  71:    */       
/*  72:    */       public List<Ticket> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  73:    */       {
/*  74: 86 */         List<Ticket> lista = new ArrayList();
/*  75:    */         
/*  76: 88 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  77:    */         
/*  78: 90 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  79:    */         
/*  80: 92 */         lista = ConsultaTicketBean.this.servicioTicket.obtenerListaPorPagina(Ticket.class, first, pageSize, sortField, ordenar, filters);
/*  81: 93 */         ConsultaTicketBean.this.listaTicket.setRowCount(ConsultaTicketBean.this.servicioTicket.contarPorCriterio(Ticket.class, filters));
/*  82:    */         
/*  83: 95 */         return lista;
/*  84:    */       }
/*  85:    */     };
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected JRDataSource getJRDataSource()
/*  89:    */   {
/*  90:102 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  91:103 */     JRDataSource ds = null;
/*  92:    */     try
/*  93:    */     {
/*  94:105 */       String[] fields = null;
/*  95:106 */       listaDatosReporte = this.servicioReporteCobroCliente.listaReporteTicket(AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaDesde, this.fechaHasta, this.numeroTicket, this.operacion, this.agencia, this.tipoReporte
/*  96:107 */         .getNombre());
/*  97:108 */       fields = new String[] { "f_moneda", "f_codigoEstacion", "f_codigoAgentes", "f_fechaReporte", "f_fechaEmisions", "f_record", "f_numeroDocumento", "f_originaConjuncion", "f_tipoEmision", "f_tipoDocumento", "f_tipoTransaccion", "f_pasajero", "f_identificacionTributaria", "f_ruta", "f_fechaViaje", "f_indicadorCredito", "f_formaPago", "f_valorFormaPago", "f_valorTotalPreliminar", "f_observaciones", "f_tarifaPreliminar", "f_tarifa", "f_tarifaDiferenciaCero", "t_tarifaCero", "f_yq", "f_yqDiferenteCero", "f_yqCero", "f_anticipo", "f_descuento", "f_penalty", "f_codigoDeServicio", "f_nombreImpuesto", "f_tipoImpuesto", "f_indicador", "f_tipo", "f_fechaArchivo", "f_numeroDocumentoRelacionado", "f_tipoTarjetaCredito", "f_valorTotal", "f_comision", "f_ivaComision", "f_retencionFte", "f_yr", "f_neto", "f_porComision", "f_numeroPeriodo", "f_periodoBSP", "f_valorImpuesto" };
/*  98:    */       
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:116 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109:118 */       e.printStackTrace();
/* 110:    */     }
/* 111:120 */     return ds;
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected String getCompileFileName()
/* 115:    */   {
/* 116:126 */     return "reporteTicket";
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected Map<String, Object> getReportParameters()
/* 120:    */   {
/* 121:137 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 122:138 */     reportParameters.put("ReportTitle", "Tickets Registrados");
/* 123:139 */     reportParameters.put("FechaDesde", this.fechaDesde);
/* 124:140 */     reportParameters.put("FechaHasta", this.fechaHasta);
/* 125:141 */     reportParameters.put("NumeroTicket", this.numeroTicket);
/* 126:142 */     reportParameters.put("Operacion", this.operacion);
/* 127:143 */     reportParameters.put("Agencia", this.agencia);
/* 128:    */     
/* 129:145 */     return reportParameters;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String execute()
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:154 */       super.prepareReport();
/* 137:    */     }
/* 138:    */     catch (JRException e)
/* 139:    */     {
/* 140:157 */       LOG.info("Error JRException");
/* 141:158 */       e.printStackTrace();
/* 142:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 143:    */     }
/* 144:    */     catch (IOException e)
/* 145:    */     {
/* 146:161 */       LOG.info("Error IOException");
/* 147:162 */       e.printStackTrace();
/* 148:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 149:    */     }
/* 150:166 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Date getFechaDesde()
/* 154:    */   {
/* 155:170 */     if (this.fechaDesde == null) {
/* 156:171 */       this.fechaDesde = FuncionesUtiles.getFechaInicioMes(new Date());
/* 157:    */     }
/* 158:173 */     return this.fechaDesde;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setFechaDesde(Date fechaDesde)
/* 162:    */   {
/* 163:177 */     this.fechaDesde = fechaDesde;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Date getFechaHasta()
/* 167:    */   {
/* 168:181 */     if (this.fechaHasta == null) {
/* 169:182 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(new Date());
/* 170:    */     }
/* 171:184 */     return this.fechaHasta;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setFechaHasta(Date fechaHasta)
/* 175:    */   {
/* 176:188 */     this.fechaHasta = fechaHasta;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public LazyDataModel<Ticket> getListaTicket()
/* 180:    */   {
/* 181:192 */     return this.listaTicket;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setListaTicket(LazyDataModel<Ticket> listaTicket)
/* 185:    */   {
/* 186:196 */     this.listaTicket = listaTicket;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getNumeroTicket()
/* 190:    */   {
/* 191:205 */     return this.numeroTicket;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setNumeroTicket(String numeroTicket)
/* 195:    */   {
/* 196:209 */     this.numeroTicket = numeroTicket;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getOperacion()
/* 200:    */   {
/* 201:213 */     return this.operacion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setOperacion(String operacion)
/* 205:    */   {
/* 206:217 */     this.operacion = operacion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String getAgencia()
/* 210:    */   {
/* 211:221 */     return this.agencia;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setAgencia(String agencia)
/* 215:    */   {
/* 216:225 */     this.agencia = agencia;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setListaTipoVenta(List<SelectItem> listaTipoVenta)
/* 220:    */   {
/* 221:235 */     this.listaTipoReporte = listaTipoVenta;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public enumTipoReporte getTipoReporte()
/* 225:    */   {
/* 226:239 */     return this.tipoReporte;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 230:    */   {
/* 231:243 */     this.tipoReporte = tipoReporte;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List<SelectItem> getListaTipoReporte()
/* 235:    */   {
/* 236:247 */     if (this.listaTipoReporte == null)
/* 237:    */     {
/* 238:248 */       this.listaTipoReporte = new ArrayList();
/* 239:249 */       for (enumTipoReporte tipoVenta : enumTipoReporte.values())
/* 240:    */       {
/* 241:250 */         SelectItem item = new SelectItem(tipoVenta, tipoVenta.name());
/* 242:251 */         this.listaTipoReporte.add(item);
/* 243:    */       }
/* 244:    */     }
/* 245:254 */     return this.listaTipoReporte;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 249:    */   {
/* 250:258 */     this.listaTipoReporte = listaTipoReporte;
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ConsultaTicketBean
 * JD-Core Version:    0.7.0.1
 */