/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteGarantiaCliente;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  17:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Calendar;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  31:    */ import net.sf.jasperreports.engine.JRException;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ReporteGarantiaClienteListaBean
/*  37:    */   extends AbstractBaseReportBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 8083558964305805588L;
/*  40:    */   @EJB
/*  41:    */   private ServicioReporteGarantiaCliente servicioReporteGarantiaCliente;
/*  42:    */   @EJB
/*  43:    */   private ServicioEmpresa servicioEmpresa;
/*  44:    */   @EJB
/*  45:    */   private ServicioSucursal servicioSucursal;
/*  46:    */   private Empresa empresa;
/*  47:    */   private Date fechaIngresoDesde;
/*  48:    */   private Date fechaIngresoHasta;
/*  49:    */   private boolean indicadorFechaRegistro;
/*  50:    */   private boolean indicadorAgrupado;
/*  51: 55 */   private EstadoGarantiaCliente estadoGarantiaCliente = EstadoGarantiaCliente.REGISTRADO;
/*  52:    */   private Sucursal sucursal;
/*  53:    */   private TipoGarantiaCliente tipoGarantiaCliente;
/*  54:    */   private List<TipoGarantiaCliente> listaTipoGarantiaCliente;
/*  55:    */   private List<EstadoGarantiaCliente> listaEstadoGarantiaCliente;
/*  56:    */   private List<Sucursal> listaSucursal;
/*  57: 61 */   private TipoOrganizacion tipoOrganizacion = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion();
/*  58:    */   
/*  59:    */   protected JRDataSource getJRDataSource()
/*  60:    */   {
/*  61: 66 */     List listaDatosReporte = new ArrayList();
/*  62: 67 */     JRDataSource ds = null;
/*  63:    */     try
/*  64:    */     {
/*  65:    */       String[] fields;
/*  66:    */       String[] fields;
/*  67: 72 */       if (this.tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA) {
/*  68: 73 */         fields = new String[] { "f_cliente", "f_identificacionCliente", "f_banco", "f_numeroCheque", "f_numeroCuenta", "f_fechaIngreso", "f_fechaCobro", "f_girador", "f_recibidoPor", "f_estadoGarantiaCliente", "f_valor", "f_concepto", "f_numeroCuentaBancariaOrganizacion", "f_valorProtestado", "f_diasCredito", "f_facturaCliente", "f_valorDetalleCobro", "f_numeroCobro", "f_observacion" };
/*  69:    */       } else {
/*  70: 78 */         fields = new String[] { "f_cliente", "f_identificacionCliente", "f_banco", "f_numeroCheque", "f_numeroCuenta", "f_fechaIngreso", "f_fechaCobro", "f_girador", "f_recibidoPor", "f_estadoGarantiaCliente", "f_valor", "f_concepto", "f_numeroCuentaBancariaOrganizacion", "f_valorProtestado", "f_diasCredito" };
/*  71:    */       }
/*  72: 82 */       if ((this.tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA) && (this.indicadorAgrupado)) {
/*  73: 83 */         this.tipoOrganizacion = TipoOrganizacion.TIPO_ORGANIZACION_GENERAL;
/*  74:    */       }
/*  75: 85 */       listaDatosReporte = this.servicioReporteGarantiaCliente.getReporteGarantiaClienteLista(this.empresa, this.estadoGarantiaCliente, this.fechaIngresoDesde, this.fechaIngresoHasta, this.tipoGarantiaCliente, 
/*  76: 86 */         AppUtil.getOrganizacion().getIdOrganizacion(), isIndicadorFechaRegistro(), this.sucursal, this.tipoOrganizacion);
/*  77:    */       
/*  78: 88 */       List<Object[]> lista = listaDatosReporte;
/*  79:    */       String numeroCobro;
/*  80: 89 */       if (this.tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)
/*  81:    */       {
/*  82: 91 */         numeroCobro = "00000";
/*  83: 92 */         for (Object[] object : lista)
/*  84:    */         {
/*  85: 93 */           if (numeroCobro.equals(object[17])) {
/*  86: 94 */             object[13] = BigDecimal.ZERO;
/*  87:    */           }
/*  88: 96 */           numeroCobro = object[17].toString();
/*  89:    */         }
/*  90:    */       }
/*  91: 99 */       this.tipoOrganizacion = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion();
/*  92:    */       
/*  93:101 */       ds = new QueryResultDataSource(lista, fields);
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:104 */       LOG.info("Error " + e);
/*  98:105 */       e.printStackTrace();
/*  99:    */     }
/* 100:107 */     return ds;
/* 101:    */   }
/* 102:    */   
/* 103:    */   @PostConstruct
/* 104:    */   public void init()
/* 105:    */   {
/* 106:113 */     Calendar calfechaDesde = Calendar.getInstance();
/* 107:114 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 108:115 */     this.fechaIngresoDesde = calfechaDesde.getTime();
/* 109:116 */     this.fechaIngresoHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected String getCompileFileName()
/* 113:    */   {
/* 114:127 */     if (this.tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)
/* 115:    */     {
/* 116:128 */       if (this.indicadorAgrupado) {
/* 117:129 */         return "reporteGarantiaClienteLista";
/* 118:    */       }
/* 119:131 */       return "reporteGarantiaClienteListaPorFactura";
/* 120:    */     }
/* 121:134 */     return "reporteGarantiaClienteLista";
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected Map<String, Object> getReportParameters()
/* 125:    */   {
/* 126:146 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 127:147 */     reportParameters.put("ReportTitle", "Garantia Cliente");
/* 128:148 */     reportParameters.put("fechaDesde", this.fechaIngresoDesde);
/* 129:149 */     reportParameters.put("fechaHasta", this.fechaIngresoHasta);
/* 130:150 */     return reportParameters;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String execute()
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:155 */       super.prepareReport();
/* 138:    */     }
/* 139:    */     catch (JRException e)
/* 140:    */     {
/* 141:157 */       LOG.info("Error JRException");
/* 142:158 */       e.printStackTrace();
/* 143:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 144:    */     }
/* 145:    */     catch (IOException e)
/* 146:    */     {
/* 147:161 */       LOG.info("Error IOException");
/* 148:162 */       e.printStackTrace();
/* 149:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 150:    */     }
/* 151:166 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 155:    */   {
/* 156:170 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Empresa getEmpresa()
/* 160:    */   {
/* 161:179 */     return this.empresa;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setEmpresa(Empresa empresa)
/* 165:    */   {
/* 166:189 */     this.empresa = empresa;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Date getFechaIngresoDesde()
/* 170:    */   {
/* 171:198 */     return this.fechaIngresoDesde;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setFechaIngresoDesde(Date fechaIngresoDesde)
/* 175:    */   {
/* 176:208 */     this.fechaIngresoDesde = fechaIngresoDesde;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Date getFechaIngresoHasta()
/* 180:    */   {
/* 181:217 */     return this.fechaIngresoHasta;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setFechaIngresoHasta(Date fechaIngresoHasta)
/* 185:    */   {
/* 186:227 */     this.fechaIngresoHasta = fechaIngresoHasta;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public EstadoGarantiaCliente getEstadoGarantiaCliente()
/* 190:    */   {
/* 191:236 */     return this.estadoGarantiaCliente;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setEstadoGarantiaCliente(EstadoGarantiaCliente estadoGarantiaCliente)
/* 195:    */   {
/* 196:246 */     this.estadoGarantiaCliente = estadoGarantiaCliente;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public List<TipoGarantiaCliente> getListaTipoGarantiaCliente()
/* 200:    */   {
/* 201:255 */     if (this.listaTipoGarantiaCliente == null)
/* 202:    */     {
/* 203:256 */       this.listaTipoGarantiaCliente = new ArrayList();
/* 204:257 */       for (TipoGarantiaCliente tipoGarantiaCliente : TipoGarantiaCliente.values()) {
/* 205:258 */         this.listaTipoGarantiaCliente.add(tipoGarantiaCliente);
/* 206:    */       }
/* 207:    */     }
/* 208:261 */     return this.listaTipoGarantiaCliente;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<EstadoGarantiaCliente> getListaEstadoGarantiaCliente()
/* 212:    */   {
/* 213:270 */     if (this.listaEstadoGarantiaCliente == null)
/* 214:    */     {
/* 215:271 */       this.listaEstadoGarantiaCliente = new ArrayList();
/* 216:272 */       for (EstadoGarantiaCliente estadoGarantiaCliente : EstadoGarantiaCliente.values()) {
/* 217:273 */         this.listaEstadoGarantiaCliente.add(estadoGarantiaCliente);
/* 218:    */       }
/* 219:    */     }
/* 220:276 */     return this.listaEstadoGarantiaCliente;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public TipoGarantiaCliente getTipoGarantiaCliente()
/* 224:    */   {
/* 225:285 */     return this.tipoGarantiaCliente;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setTipoGarantiaCliente(TipoGarantiaCliente tipoGarantiaCliente)
/* 229:    */   {
/* 230:295 */     this.tipoGarantiaCliente = tipoGarantiaCliente;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isIndicadorFechaRegistro()
/* 234:    */   {
/* 235:304 */     return this.indicadorFechaRegistro;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setIndicadorFechaRegistro(boolean indicadorFechaRegistro)
/* 239:    */   {
/* 240:314 */     this.indicadorFechaRegistro = indicadorFechaRegistro;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Sucursal getSucursal()
/* 244:    */   {
/* 245:323 */     if (this.sucursal == null)
/* 246:    */     {
/* 247:324 */       this.sucursal = new Sucursal();
/* 248:325 */       this.sucursal = AppUtil.getSucursal();
/* 249:    */     }
/* 250:327 */     return this.sucursal;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setSucursal(Sucursal sucursal)
/* 254:    */   {
/* 255:337 */     this.sucursal = sucursal;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<Sucursal> getListaSucursal()
/* 259:    */   {
/* 260:346 */     if (this.listaSucursal == null)
/* 261:    */     {
/* 262:347 */       Map<String, String> filters = new HashMap();
/* 263:348 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 264:349 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 265:    */     }
/* 266:351 */     return this.listaSucursal;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 270:    */   {
/* 271:361 */     this.listaSucursal = listaSucursal;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isIndicadorAgrupado()
/* 275:    */   {
/* 276:365 */     return this.indicadorAgrupado;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setIndicadorAgrupado(boolean indicadorAgrupado)
/* 280:    */   {
/* 281:369 */     this.indicadorAgrupado = indicadorAgrupado;
/* 282:    */   }
/* 283:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteGarantiaClienteListaBean
 * JD-Core Version:    0.7.0.1
 */