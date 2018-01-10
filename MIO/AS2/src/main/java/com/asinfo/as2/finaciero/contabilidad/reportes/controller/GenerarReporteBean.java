/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DetalleReporteadorVariable;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Reporteador;
/*   7:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   8:    */ import com.asinfo.as2.enumeraciones.Periodicidad;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteador;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.text.SimpleDateFormat;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.context.RequestContext;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class GenerarReporteBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 254125867293632941L;
/*  37:    */   @EJB
/*  38:    */   private ServicioReporteador servicioReporteador;
/*  39:    */   public static final String COMPILE_FILE_NAME = "reporteador";
/*  40:    */   private Integer anio;
/*  41:    */   private Mes mes;
/*  42:    */   private Date fechaDesde;
/*  43:    */   private Date fechaHasta;
/*  44:    */   private List<Mes> listaMes;
/*  45:    */   private List<Reporteador> listaReporteador;
/*  46:    */   private Reporteador reporteadorSeleccionado;
/*  47:    */   private List<DetalleReporteadorVariable> listaDetalleReporteadorVariable;
/*  48:    */   
/*  49:    */   protected JRDataSource getJRDataSource()
/*  50:    */   {
/*  51: 79 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  52: 80 */     JRDataSource ds = null;
/*  53: 81 */     Date fechaInicial = null;
/*  54: 82 */     Date fechaFinal = null;
/*  55:    */     try
/*  56:    */     {
/*  57: 86 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Periodicidad[this.reporteadorSeleccionado.getPeriodicidad().ordinal()])
/*  58:    */       {
/*  59:    */       case 1: 
/*  60: 88 */         fechaInicial = FuncionesUtiles.getFecha(1, 1, this.anio.intValue());
/*  61: 89 */         fechaFinal = FuncionesUtiles.getFechaFinMes(this.anio.intValue(), 12);
/*  62: 90 */         break;
/*  63:    */       case 2: 
/*  64: 92 */         fechaInicial = FuncionesUtiles.getFecha(1, this.mes.getNumero(), this.anio.intValue());
/*  65: 93 */         fechaFinal = FuncionesUtiles.getFechaFinMes(this.anio.intValue(), this.mes.getNumero());
/*  66: 94 */         break;
/*  67:    */       case 3: 
/*  68: 96 */         fechaInicial = this.fechaDesde;
/*  69: 97 */         fechaFinal = this.fechaHasta;
/*  70: 98 */         break;
/*  71:    */       }
/*  72:103 */       listaDatosReporte = this.servicioReporteador.getReporte(this.reporteadorSeleccionado, fechaInicial, fechaFinal, 0);
/*  73:    */       
/*  74:105 */       String[] fields = { "f_nivel", "f_nombreCampo", "f_descripcionCampo", "f_valorCampo" };
/*  75:    */       
/*  76:107 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80:110 */       LOG.info("Error " + e);
/*  81:111 */       e.printStackTrace();
/*  82:112 */       addErrorMessage(e.getMessage());
/*  83:    */     }
/*  84:114 */     return ds;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected String getCompileFileName()
/*  88:    */   {
/*  89:119 */     if (this.reporteadorSeleccionado != null) {
/*  90:120 */       return this.reporteadorSeleccionado.getFicheroReporte();
/*  91:    */     }
/*  92:122 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected Map<String, Object> getReportParameters()
/*  96:    */   {
/*  97:127 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  98:128 */     reportParameters.put("ReportTitle", this.reporteadorSeleccionado == null ? "" : this.reporteadorSeleccionado.getNombre());
/*  99:129 */     String periodo = "";
/* 100:130 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Periodicidad[this.reporteadorSeleccionado.getPeriodicidad().ordinal()])
/* 101:    */     {
/* 102:    */     case 1: 
/* 103:132 */       periodo = this.anio + "";
/* 104:133 */       break;
/* 105:    */     case 2: 
/* 106:135 */       periodo = this.mes.getNombre() + " " + this.anio;
/* 107:136 */       break;
/* 108:    */     case 3: 
/* 109:138 */       SimpleDateFormat sdf = new SimpleDateFormat(ParametrosSistema.getFormatoFecha(AppUtil.getOrganizacion().getId()));
/* 110:139 */       periodo = sdf.format(this.fechaDesde) + " - " + sdf.format(this.fechaHasta);
/* 111:140 */       break;
/* 112:    */     }
/* 113:144 */     reportParameters.put("periodo", periodo);
/* 114:145 */     reportParameters.put("anio", this.anio);
/* 115:146 */     reportParameters.put("mes", this.mes == null ? "" : this.mes.getNombre());
/* 116:147 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 117:148 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 118:    */     
/* 119:150 */     return reportParameters;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String execute()
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:157 */       super.prepareReport();
/* 127:    */     }
/* 128:    */     catch (JRException e)
/* 129:    */     {
/* 130:160 */       LOG.info("Error JRException");
/* 131:161 */       e.printStackTrace();
/* 132:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 133:    */     }
/* 134:    */     catch (IOException e)
/* 135:    */     {
/* 136:164 */       LOG.info("Error IOException");
/* 137:165 */       e.printStackTrace();
/* 138:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 139:    */     }
/* 140:168 */     return null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getAnio()
/* 144:    */   {
/* 145:172 */     return this.anio.intValue();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setAnio(int anio)
/* 149:    */   {
/* 150:176 */     this.anio = Integer.valueOf(anio);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Mes getMes()
/* 154:    */   {
/* 155:180 */     return this.mes;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setMes(Mes mes)
/* 159:    */   {
/* 160:184 */     this.mes = mes;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Date getFechaDesde()
/* 164:    */   {
/* 165:188 */     return this.fechaDesde;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setFechaDesde(Date fechaDesde)
/* 169:    */   {
/* 170:192 */     this.fechaDesde = fechaDesde;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Date getFechaHasta()
/* 174:    */   {
/* 175:196 */     return this.fechaHasta;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setFechaHasta(Date fechaHasta)
/* 179:    */   {
/* 180:200 */     this.fechaHasta = fechaHasta;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<Reporteador> getListaReporteador()
/* 184:    */   {
/* 185:204 */     if (this.listaReporteador == null)
/* 186:    */     {
/* 187:205 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 188:206 */       filtros.put("activo", "true");
/* 189:207 */       this.listaReporteador = this.servicioReporteador.obtenerListaCombo("nombre", true, filtros);
/* 190:    */     }
/* 191:209 */     return this.listaReporteador;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Reporteador getReporteadorSeleccionado()
/* 195:    */   {
/* 196:213 */     return this.reporteadorSeleccionado;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setReporteadorSeleccionado(Reporteador reporteadorSeleccionado)
/* 200:    */   {
/* 201:218 */     if ((this.reporteadorSeleccionado == null) || 
/* 202:219 */       (!this.reporteadorSeleccionado.getPeriodicidad().equals(reporteadorSeleccionado.getPeriodicidad())))
/* 203:    */     {
/* 204:220 */       this.mes = null;
/* 205:221 */       this.anio = null;
/* 206:222 */       this.fechaDesde = null;
/* 207:223 */       this.fechaHasta = null;
/* 208:    */       
/* 209:225 */       Calendar hoy = Calendar.getInstance();
/* 210:226 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Periodicidad[reporteadorSeleccionado.getPeriodicidad().ordinal()])
/* 211:    */       {
/* 212:    */       case 1: 
/* 213:228 */         this.anio = Integer.valueOf(hoy.get(1));
/* 214:229 */         break;
/* 215:    */       case 2: 
/* 216:231 */         this.anio = Integer.valueOf(hoy.get(1));
/* 217:232 */         this.mes = Mes.values()[hoy.get(2)];
/* 218:233 */         break;
/* 219:    */       case 3: 
/* 220:235 */         this.fechaDesde = FuncionesUtiles.getFechaInicioMes(hoy.getTime());
/* 221:236 */         this.fechaHasta = FuncionesUtiles.getFechaFinMes(hoy.getTime());
/* 222:237 */         break;
/* 223:    */       }
/* 224:    */     }
/* 225:242 */     this.reporteadorSeleccionado = reporteadorSeleccionado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<Mes> getListaMes()
/* 229:    */   {
/* 230:246 */     if (this.listaMes == null)
/* 231:    */     {
/* 232:247 */       this.listaMes = new ArrayList();
/* 233:248 */       for (Mes mes : Mes.values()) {
/* 234:249 */         this.listaMes.add(mes);
/* 235:    */       }
/* 236:    */     }
/* 237:252 */     return this.listaMes;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void cargarVariedades()
/* 241:    */     throws AS2Exception
/* 242:    */   {
/* 243:256 */     this.listaDetalleReporteadorVariable = this.servicioReporteador.listaVariablesSinCuentaContable(this.reporteadorSeleccionado);
/* 244:257 */     RequestContext.getCurrentInstance().execute("PF('confirmacionImprimir').show()");
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<DetalleReporteadorVariable> getListaDetalleReporteadorVariable()
/* 248:    */   {
/* 249:265 */     return this.listaDetalleReporteadorVariable;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaDetalleReporteadorVariable(List<DetalleReporteadorVariable> listaDetalleReporteadorVariable)
/* 253:    */   {
/* 254:273 */     this.listaDetalleReporteadorVariable = listaDetalleReporteadorVariable;
/* 255:    */   }
/* 256:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.GenerarReporteBean
 * JD-Core Version:    0.7.0.1
 */