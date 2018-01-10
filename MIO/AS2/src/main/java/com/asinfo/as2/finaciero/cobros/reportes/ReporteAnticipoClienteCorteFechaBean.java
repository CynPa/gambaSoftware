/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class ReporteAnticipoClienteCorteFechaBean
/*  33:    */   extends AbstractBaseReportBean
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = -326616192156976012L;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioReporteVenta servicioReporteVenta;
/*  38:    */   @EJB
/*  39:    */   private transient ServicioEmpresa servicioEmpresa;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioSucursal servicioSucursal;
/*  42:    */   private Date fechaHasta;
/*  43:    */   private Date fechaDesde;
/*  44:    */   private Empresa empresa;
/*  45:    */   private boolean indicadorResumen;
/*  46:    */   private boolean indicadorSaldoDiferenciaCero;
/*  47:    */   private boolean indicadorIncluirNotasCreditos;
/*  48:    */   private Sucursal sucursal;
/*  49:    */   private List<Sucursal> listaSucursal;
/*  50:    */   
/*  51:    */   protected JRDataSource getJRDataSource()
/*  52:    */   {
/*  53: 78 */     List listaDatosReporte = new ArrayList();
/*  54: 79 */     JRDataSource ds = null;
/*  55:    */     try
/*  56:    */     {
/*  57: 82 */       listaDatosReporte = this.servicioReporteVenta.getReporteCorteFechaAnticipoClientes(this.fechaDesde, this.fechaHasta, this.empresa.getIdEmpresa(), this.indicadorSaldoDiferenciaCero, 
/*  58: 83 */         AppUtil.getOrganizacion().getId(), this.sucursal, this.indicadorIncluirNotasCreditos);
/*  59: 84 */       if (listaDatosReporte.size() == 0)
/*  60:    */       {
/*  61: 85 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  62:    */       }
/*  63:    */       else
/*  64:    */       {
/*  65: 88 */         String[] fields = { "numeroAnticipo", "valorAnticipo", "valorLiquidacion", "nombreComercial", "nombreFiscal", "fechaLiquidacion", "factura", "saldoAnticipo", "identificacion", "numeroNotaCredito", "fechaAnticipoCliente", "valorDevolucion" };
/*  66:    */         
/*  67:    */ 
/*  68: 91 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  69:    */       }
/*  70:    */     }
/*  71:    */     catch (ExcepcionAS2 e)
/*  72:    */     {
/*  73: 96 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  74:    */     }
/*  75: 98 */     return ds;
/*  76:    */   }
/*  77:    */   
/*  78:    */   @PostConstruct
/*  79:    */   public void init()
/*  80:    */   {
/*  81:103 */     Calendar calfechaDesde = Calendar.getInstance();
/*  82:104 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  83:105 */     this.fechaDesde = calfechaDesde.getTime();
/*  84:106 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected String getCompileFileName()
/*  88:    */   {
/*  89:115 */     if (this.indicadorResumen) {
/*  90:116 */       return "reporteCorteFechaAnticipoResumido";
/*  91:    */     }
/*  92:118 */     return "reporteCorteFechaAnticipo";
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected Map<String, Object> getReportParameters()
/*  96:    */   {
/*  97:129 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  98:130 */     if (!this.indicadorResumen) {
/*  99:131 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_corte_fecha_anticipo_cliente"));
/* 100:    */     } else {
/* 101:133 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_corte_fecha_anticipo_cliente_resumido"));
/* 102:    */     }
/* 103:135 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 104:136 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 105:137 */     reportParameters.put("total", "Total");
/* 106:138 */     return reportParameters;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void validaDatos()
/* 110:    */   {
/* 111:143 */     if (this.empresa == null) {
/* 112:144 */       this.empresa = new Empresa();
/* 113:    */     }
/* 114:146 */     if (this.fechaDesde == null) {
/* 115:147 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 116:    */     }
/* 117:149 */     if (this.fechaHasta == null) {
/* 118:150 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Date getFechaDesde()
/* 123:    */   {
/* 124:160 */     return this.fechaDesde;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setFechaDesde(Date fechaDesde)
/* 128:    */   {
/* 129:170 */     this.fechaDesde = fechaDesde;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Date getFechaHasta()
/* 133:    */   {
/* 134:179 */     return this.fechaHasta;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFechaHasta(Date fechaHasta)
/* 138:    */   {
/* 139:189 */     this.fechaHasta = fechaHasta;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String execute()
/* 143:    */   {
/* 144:    */     try
/* 145:    */     {
/* 146:200 */       validaDatos();
/* 147:201 */       super.prepareReport();
/* 148:    */     }
/* 149:    */     catch (JRException e)
/* 150:    */     {
/* 151:203 */       LOG.info("Error JRException");
/* 152:204 */       e.printStackTrace();
/* 153:205 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 154:    */     }
/* 155:    */     catch (IOException e)
/* 156:    */     {
/* 157:207 */       LOG.info("Error IOException");
/* 158:208 */       e.printStackTrace();
/* 159:209 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 160:    */     }
/* 161:212 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 165:    */   {
/* 166:216 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Empresa getEmpresa()
/* 170:    */   {
/* 171:225 */     if (this.empresa == null) {
/* 172:226 */       this.empresa = new Empresa();
/* 173:    */     }
/* 174:228 */     return this.empresa;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setEmpresa(Empresa empresa)
/* 178:    */   {
/* 179:238 */     this.empresa = empresa;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean isIndicadorResumen()
/* 183:    */   {
/* 184:247 */     return this.indicadorResumen;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 188:    */   {
/* 189:257 */     this.indicadorResumen = indicadorResumen;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isIndicadorSaldoDiferenciaCero()
/* 193:    */   {
/* 194:266 */     return this.indicadorSaldoDiferenciaCero;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setIndicadorSaldoDiferenciaCero(boolean indicadorSaldoDiferenciaCero)
/* 198:    */   {
/* 199:276 */     this.indicadorSaldoDiferenciaCero = indicadorSaldoDiferenciaCero;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Sucursal getSucursal()
/* 203:    */   {
/* 204:280 */     return this.sucursal;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setSucursal(Sucursal sucursal)
/* 208:    */   {
/* 209:284 */     this.sucursal = sucursal;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Sucursal> getListaSucursal()
/* 213:    */   {
/* 214:288 */     if (this.listaSucursal == null)
/* 215:    */     {
/* 216:289 */       Map<String, String> filters = new HashMap();
/* 217:290 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 218:291 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 219:    */     }
/* 220:293 */     return this.listaSucursal;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 224:    */   {
/* 225:297 */     this.listaSucursal = listaSucursal;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isIndicadorIncluirNotasCreditos()
/* 229:    */   {
/* 230:301 */     return this.indicadorIncluirNotasCreditos;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setIndicadorIncluirNotasCreditos(boolean indicadorIncluirNotasCreditos)
/* 234:    */   {
/* 235:305 */     this.indicadorIncluirNotasCreditos = indicadorIncluirNotasCreditos;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteAnticipoClienteCorteFechaBean
 * JD-Core Version:    0.7.0.1
 */