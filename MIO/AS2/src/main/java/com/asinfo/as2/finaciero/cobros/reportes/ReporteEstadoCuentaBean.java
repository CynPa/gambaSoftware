/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteCobros;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   8:    */ import com.asinfo.as2.entities.Recaudador;
/*   9:    */ import com.asinfo.as2.entities.Subempresa;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  14:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioGarantiaCliente;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  19:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  20:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.math.BigDecimal;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Calendar;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class ReporteEstadoCuentaBean
/*  39:    */   extends AbstractClientReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = -3323908391222764008L;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioAnticipoCliente servicioAnticipoCliente;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioGarantiaCliente servicioGarantiaCliente;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioReporteVenta servicioReporteVenta;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  50: 69 */   private OrdenamientoEnum orden = OrdenamientoEnum.FECHA;
/*  51:    */   private List<SelectItem> listaOrdenamiento;
/*  52: 71 */   private boolean saldoDiferenteDeCero = false;
/*  53:    */   
/*  54:    */   public boolean isSaldoDiferenteDeCero()
/*  55:    */   {
/*  56: 74 */     return this.saldoDiferenteDeCero;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setSaldoDiferenteDeCero(boolean saldoDiferenteDeCero)
/*  60:    */   {
/*  61: 78 */     this.saldoDiferenteDeCero = saldoDiferenteDeCero;
/*  62:    */   }
/*  63:    */   
/*  64: 81 */   private static String COMPILE_FILE_NAME = "";
/*  65: 83 */   public static final String[] fields = { "nombreCliente", "codigoCliente", "fechaFactura", "numeroFactura", "fechaVence", "detalleDocumento", "valorCompras", "valorPagos", "referenciaDocumento", "numeroDocumento", "indicadorGeneradaProtesto", "codigoDocumento", "codigoDocumentoProceso", "f_asiento", "f_asientoDocumento", "f_referencia1", "f_referencia2", "f_referencia3", "f_referencia4", "f_referencia5", "f_referencia6", "f_valorReferencia1", "f_valorReferencia2", "f_valorReferencia3", "identificacion", "f_consignatario", "f_fechaFactura", "f_descripcion" };
/*  66:    */   
/*  67:    */   protected String getCompileFileName()
/*  68:    */   {
/*  69: 97 */     if (this.orden.equals(OrdenamientoEnum.FACTURA)) {
/*  70: 98 */       COMPILE_FILE_NAME = "reporteEstadoCuentaFactura";
/*  71: 99 */     } else if (this.orden.equals(OrdenamientoEnum.DOCUMENTO)) {
/*  72:100 */       COMPILE_FILE_NAME = "reporteEstadoCuentaCobro";
/*  73:101 */     } else if (this.orden.equals(OrdenamientoEnum.SALDO_FACTURA)) {
/*  74:102 */       COMPILE_FILE_NAME = "reporteEstadoCuentaSaldoFactura";
/*  75:    */     } else {
/*  76:104 */       COMPILE_FILE_NAME = "reporteEstadoCuentaCliente";
/*  77:    */     }
/*  78:107 */     return COMPILE_FILE_NAME;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Map<String, Object> getReportParameters()
/*  82:    */   {
/*  83:120 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  84:121 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_estado_cuenta_titulo"));
/*  85:122 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  86:123 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  87:124 */     BigDecimal saldoAnticipo = this.servicioAnticipoCliente.obtenerSaldoAnticipo(this.empresa.getIdEmpresa(), this.fechaHasta);
/*  88:125 */     reportParameters.put("saldoAnticipo", saldoAnticipo);
/*  89:126 */     BigDecimal saldoChequePosfechado = this.servicioGarantiaCliente.obtenerSaldoChequePosfechado(this.empresa.getIdEmpresa(), this.fechaHasta);
/*  90:127 */     reportParameters.put("saldoChequePosfechado", saldoChequePosfechado);
/*  91:128 */     BigDecimal saldoInicial = this.servicioReporteVenta.obtenerSaldoEstadoCuenta(this.empresa.getIdEmpresa(), this.fechaDesde, this.orden.equals(OrdenamientoEnum.SALDO_FACTURA));
/*  92:129 */     reportParameters.put("saldoInicial", saldoInicial);
/*  93:130 */     reportParameters.put("mostrarChequePosfechado", Boolean.valueOf(true));
/*  94:131 */     reportParameters.put("p_subcliente", this.subempresa != null ? this.subempresa.getEmpresa().getNombreComercial() : "Todos");
/*  95:132 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/*  96:133 */     reportParameters.put("p_recaudador", this.recaudador != null ? this.recaudador.getNombre() : "Todos");
/*  97:134 */     reportParameters.put("p_punto_venta", this.puntoVenta != null ? this.puntoVenta.getNombre() : "Todos");
/*  98:135 */     reportParameters.put("reporteCliente", Boolean.TRUE);
/*  99:136 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 100:137 */     if (this.orden.equals(OrdenamientoEnum.SALDO_FACTURA))
/* 101:    */     {
/* 102:138 */       List<ReporteCobros> lista = this.servicioReporteCobroCliente.getListaCobros(Integer.valueOf(this.empresa.getIdEmpresa()), this.fechaDesde, this.fechaHasta, AppUtil.getOrganizacion().getId());
/* 103:139 */       reportParameters.put("p_listaCobros", lista);
/* 104:    */     }
/* 105:141 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 106:142 */     return reportParameters;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected JRDataSource getJRDataSource()
/* 110:    */   {
/* 111:154 */     List listaDatosReporte = new ArrayList();
/* 112:155 */     JRDataSource jrDataSource = null;
/* 113:    */     try
/* 114:    */     {
/* 115:158 */       listaDatosReporte = this.servicioReporteVenta.getListaReporteEstadoCuenta(this.fechaDesde, this.fechaHasta, this.empresa, this.recaudador, this.subempresa, 
/* 116:159 */         AppUtil.getOrganizacion().getId(), this.orden, this.saldoDiferenteDeCero, this.sucursal, this.puntoVenta);
/* 117:162 */       if (listaDatosReporte.isEmpty())
/* 118:    */       {
/* 119:163 */         Object[] arrayCampos = new Object[fields.length];
/* 120:164 */         arrayCampos[0] = this.empresa.getNombreComercial();
/* 121:165 */         arrayCampos[1] = this.empresa.getCodigo();
/* 122:166 */         arrayCampos[6] = BigDecimal.ZERO;
/* 123:167 */         arrayCampos[7] = BigDecimal.ZERO;
/* 124:168 */         listaDatosReporte.add(arrayCampos);
/* 125:    */       }
/* 126:170 */       jrDataSource = new QueryResultDataSource(listaDatosReporte, fields);
/* 127:    */     }
/* 128:    */     catch (ExcepcionAS2 e)
/* 129:    */     {
/* 130:172 */       LOG.info("Error " + e);
/* 131:173 */       e.printStackTrace();
/* 132:174 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 133:    */     }
/* 134:    */     catch (Exception e)
/* 135:    */     {
/* 136:176 */       e.printStackTrace();
/* 137:    */     }
/* 138:178 */     return jrDataSource;
/* 139:    */   }
/* 140:    */   
/* 141:    */   @PostConstruct
/* 142:    */   public void init()
/* 143:    */   {
/* 144:183 */     Calendar calfechaDesde = Calendar.getInstance();
/* 145:184 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 146:185 */     this.fechaDesde = calfechaDesde.getTime();
/* 147:186 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String execute()
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:196 */       if (null == this.empresa)
/* 155:    */       {
/* 156:197 */         addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_cliente"));
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:199 */         if (this.fechaDesde == null) {
/* 161:200 */           this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 162:    */         }
/* 163:202 */         if (this.fechaHasta == null) {
/* 164:203 */           this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 165:    */         }
/* 166:205 */         super.prepareReport();
/* 167:    */       }
/* 168:    */     }
/* 169:    */     catch (JRException e)
/* 170:    */     {
/* 171:208 */       e.printStackTrace();
/* 172:209 */       LOG.info("Error JRException");
/* 173:210 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 174:    */     }
/* 175:    */     catch (IOException e)
/* 176:    */     {
/* 177:212 */       e.printStackTrace();
/* 178:213 */       LOG.info("Error IOException");
/* 179:214 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 180:    */     }
/* 181:    */     catch (Exception e)
/* 182:    */     {
/* 183:216 */       e.printStackTrace();
/* 184:    */     }
/* 185:219 */     return "";
/* 186:    */   }
/* 187:    */   
/* 188:    */   public OrdenamientoEnum getOrden()
/* 189:    */   {
/* 190:230 */     return this.orden;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setOrden(OrdenamientoEnum orden)
/* 194:    */   {
/* 195:240 */     this.orden = orden;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<SelectItem> getListaOrdenamiento()
/* 199:    */   {
/* 200:244 */     if (this.listaOrdenamiento == null)
/* 201:    */     {
/* 202:245 */       this.listaOrdenamiento = new ArrayList();
/* 203:246 */       SelectItem item = new SelectItem(OrdenamientoEnum.FECHA, OrdenamientoEnum.FECHA.getNombre());
/* 204:247 */       this.listaOrdenamiento.add(item);
/* 205:248 */       item = new SelectItem(OrdenamientoEnum.FACTURA, OrdenamientoEnum.FACTURA.getNombre());
/* 206:249 */       this.listaOrdenamiento.add(item);
/* 207:250 */       item = new SelectItem(OrdenamientoEnum.DOCUMENTO, OrdenamientoEnum.DOCUMENTO.getNombre());
/* 208:251 */       this.listaOrdenamiento.add(item);
/* 209:252 */       item = new SelectItem(OrdenamientoEnum.SALDO_FACTURA, OrdenamientoEnum.SALDO_FACTURA.getNombre());
/* 210:253 */       this.listaOrdenamiento.add(item);
/* 211:    */     }
/* 212:255 */     return this.listaOrdenamiento;
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteEstadoCuentaBean
 * JD-Core Version:    0.7.0.1
 */