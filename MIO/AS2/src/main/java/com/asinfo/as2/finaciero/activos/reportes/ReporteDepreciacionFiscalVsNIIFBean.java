/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.entities.DimensionContable;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*   8:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteDepreciacion;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Calendar;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ManagedProperty;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import net.sf.jasperreports.engine.JRException;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.event.SelectEvent;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReporteDepreciacionFiscalVsNIIFBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteDepreciacion servicioReporteDepreciacion;
/*  37:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  38:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  39:    */   private ActivoFijo activoFijo;
/*  40:    */   private ActivoFijo activoFijoSeleccionado;
/*  41:    */   private List<SelectItem> listaSelectItemMesDesde;
/*  42:    */   private List<SelectItem> listaSelectItemMesHasta;
/*  43:    */   private int mesDesde;
/*  44:    */   private int anioDesde;
/*  45:    */   private int mesHasta;
/*  46:    */   private int anioHasta;
/*  47:    */   private boolean activo;
/*  48:    */   DimensionContable dimension;
/*  49: 78 */   private String valorDimension = "";
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 82 */     this.anioDesde = Calendar.getInstance().get(1);
/*  55: 83 */     this.anioHasta = Calendar.getInstance().get(1);
/*  56: 84 */     this.mesDesde = Calendar.getInstance().get(2);
/*  57: 85 */     this.mesHasta = (Calendar.getInstance().get(2) + 1);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected JRDataSource getJRDataSource()
/*  61:    */   {
/*  62: 97 */     List listaDatosReporte = new ArrayList();
/*  63: 98 */     JRDataSource ds = null;
/*  64:    */     
/*  65:100 */     Date fechaDesde = FuncionesUtiles.getFechaFinMes(this.anioDesde, this.mesDesde);
/*  66:101 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(this.anioHasta, this.mesHasta);
/*  67:    */     
/*  68:103 */     listaDatosReporte = this.servicioReporteDepreciacion.getReporteDepreciacionFiscalVsNIIF(getActivoFijoSeleccionado(), fechaDesde, fechaHasta, this.activo, 
/*  69:104 */       getListaDimensionContableBean().getNumeroDimension(), getValorDimension());
/*  70:    */     
/*  71:106 */     String[] fields = { "f_codigoActivoFijo", "f_activoFijo", "f_fechaInicioDepreciacion", "f_valorActivoFiscal", "f_valorActivo", "f_valorDepreciadoFiscal", "f_valorDepreciado", "f_valorResidualFiscal", "f_valorResidual", "f_valorADepreciarFiscal", "f_valorADepreciar", "f_vidaUtilFiscal", "f_vidaUtil", "f_valorFiscal", "f_valor", "f_diferenciaTemporal", "f_diferenciaTemporalRevalorizacion", "f_impuestoDiferido", "f_mes", "f_anio" };
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:111 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  77:    */     
/*  78:113 */     return ds;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected String getCompileFileName()
/*  82:    */   {
/*  83:124 */     return "reporteDepreciacionFiscalVsNIIF";
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected Map<String, Object> getReportParameters()
/*  87:    */   {
/*  88:134 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  89:135 */     reportParameters.put("ReportTitle", "Depreciacion");
/*  90:136 */     reportParameters.put("usuario", "Usuario:");
/*  91:137 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/*  92:138 */     reportParameters.put("pagina", "Pagina:");
/*  93:139 */     reportParameters.put("desde", "Desde:");
/*  94:140 */     reportParameters.put("hasta", "Hasta:");
/*  95:141 */     reportParameters.put("p_mesDesde", Integer.valueOf(this.mesDesde));
/*  96:142 */     reportParameters.put("p_anioDesde", Integer.valueOf(this.anioDesde));
/*  97:143 */     reportParameters.put("p_mesHasta", Integer.valueOf(this.mesHasta));
/*  98:144 */     reportParameters.put("p_anioHasta", Integer.valueOf(this.anioHasta));
/*  99:145 */     reportParameters.put("p_dimension", getValorDimension() != "" ? getListaDimensionContableBean().getNumeroDimension() : "Todos");
/* 100:146 */     reportParameters.put("p_dimensionValor", getValorDimension() != "" ? getValorDimension() : "Todos");
/* 101:147 */     return reportParameters;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String execute()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:158 */       super.prepareReport();
/* 109:    */     }
/* 110:    */     catch (JRException e)
/* 111:    */     {
/* 112:161 */       LOG.info("Error JRException");
/* 113:162 */       e.printStackTrace();
/* 114:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 115:    */     }
/* 116:    */     catch (IOException e)
/* 117:    */     {
/* 118:165 */       LOG.info("Error IOException");
/* 119:166 */       e.printStackTrace();
/* 120:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 121:    */     }
/* 122:169 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void validaDatos() {}
/* 126:    */   
/* 127:    */   public String cargarActivoFijo()
/* 128:    */   {
/* 129:177 */     setActivoFijoSeleccionado(this.activoFijo);
/* 130:178 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public ActivoFijo getActivoFijo()
/* 134:    */   {
/* 135:187 */     return this.activoFijo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 139:    */   {
/* 140:197 */     this.activoFijo = activoFijo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public ActivoFijo getActivoFijoSeleccionado()
/* 144:    */   {
/* 145:206 */     return this.activoFijoSeleccionado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setActivoFijoSeleccionado(ActivoFijo activoFijoSeleccionado)
/* 149:    */   {
/* 150:216 */     this.activoFijoSeleccionado = activoFijoSeleccionado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<SelectItem> getListaSelectItemMesDesde()
/* 154:    */   {
/* 155:225 */     if (this.listaSelectItemMesDesde == null)
/* 156:    */     {
/* 157:226 */       this.listaSelectItemMesDesde = new ArrayList();
/* 158:227 */       for (Mes mes : Mes.values())
/* 159:    */       {
/* 160:228 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 161:229 */         this.listaSelectItemMesDesde.add(selectItem);
/* 162:    */       }
/* 163:    */     }
/* 164:232 */     return this.listaSelectItemMesDesde;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaSelectItemMesDesde(List<SelectItem> listaSelectItemMesDesde)
/* 168:    */   {
/* 169:242 */     this.listaSelectItemMesDesde = listaSelectItemMesDesde;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<SelectItem> getListaSelectItemMesHasta()
/* 173:    */   {
/* 174:251 */     if (this.listaSelectItemMesHasta == null)
/* 175:    */     {
/* 176:252 */       this.listaSelectItemMesHasta = new ArrayList();
/* 177:253 */       for (Mes mes : Mes.values())
/* 178:    */       {
/* 179:254 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 180:255 */         this.listaSelectItemMesHasta.add(selectItem);
/* 181:    */       }
/* 182:    */     }
/* 183:258 */     return this.listaSelectItemMesHasta;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setListaSelectItemMesHasta(List<SelectItem> listaSelectItemMesHasta)
/* 187:    */   {
/* 188:268 */     this.listaSelectItemMesHasta = listaSelectItemMesHasta;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int getMesDesde()
/* 192:    */   {
/* 193:277 */     return this.mesDesde;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setMesDesde(int mesDesde)
/* 197:    */   {
/* 198:287 */     this.mesDesde = mesDesde;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getAnioDesde()
/* 202:    */   {
/* 203:296 */     return this.anioDesde;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setAnioDesde(int anioDesde)
/* 207:    */   {
/* 208:306 */     this.anioDesde = anioDesde;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public int getMesHasta()
/* 212:    */   {
/* 213:315 */     return this.mesHasta;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setMesHasta(int mesHasta)
/* 217:    */   {
/* 218:325 */     this.mesHasta = mesHasta;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public int getAnioHasta()
/* 222:    */   {
/* 223:334 */     return this.anioHasta;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setAnioHasta(int anioHasta)
/* 227:    */   {
/* 228:344 */     this.anioHasta = anioHasta;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean isActivo()
/* 232:    */   {
/* 233:353 */     return this.activo;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setActivo(boolean activo)
/* 237:    */   {
/* 238:363 */     this.activo = activo;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 242:    */   {
/* 243:372 */     return this.listaDimensionContableBean;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 247:    */   {
/* 248:382 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public DimensionContable getDimension()
/* 252:    */   {
/* 253:386 */     return this.dimension;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setDimension(DimensionContable dimension)
/* 257:    */   {
/* 258:390 */     this.dimension = dimension;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getValorDimension()
/* 262:    */   {
/* 263:394 */     return this.valorDimension;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setValorDimension(String valorDimension)
/* 267:    */   {
/* 268:398 */     this.valorDimension = valorDimension;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void cargarDimensionContableListener(SelectEvent event)
/* 272:    */   {
/* 273:403 */     this.dimension = ((DimensionContable)event.getObject());
/* 274:    */     try
/* 275:    */     {
/* 276:405 */       this.valorDimension = this.dimension.getCodigo();
/* 277:    */     }
/* 278:    */     catch (Exception e)
/* 279:    */     {
/* 280:407 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 281:408 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/* 282:    */     }
/* 283:    */   }
/* 284:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteDepreciacionFiscalVsNIIFBean
 * JD-Core Version:    0.7.0.1
 */