/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   6:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteDepreciacion;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Calendar;
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
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ReporteDepreciacionMensualBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 3973324081959781051L;
/*  32:    */   @EJB
/*  33:    */   private ServicioReporteDepreciacion servicioReporteDepreciacion;
/*  34:    */   private ActivoFijo activoFijo;
/*  35:    */   private ActivoFijo activoFijoSeleccionado;
/*  36:    */   private List<SelectItem> listaSelectItemMesDesde;
/*  37:    */   private List<SelectItem> listaSelectItemMesHasta;
/*  38:    */   private int mesDesde;
/*  39:    */   private int anioDesde;
/*  40:    */   private int mesHasta;
/*  41:    */   private int anioHasta;
/*  42:    */   private boolean indicadorResumen;
/*  43:    */   private boolean indicadorDepreciacionFiscal;
/*  44:    */   private boolean activo;
/*  45:    */   
/*  46:    */   @PostConstruct
/*  47:    */   public void init()
/*  48:    */   {
/*  49: 72 */     this.anioDesde = Calendar.getInstance().get(1);
/*  50: 73 */     this.anioHasta = Calendar.getInstance().get(1);
/*  51: 74 */     this.mesDesde = Calendar.getInstance().get(2);
/*  52: 75 */     this.mesHasta = (Calendar.getInstance().get(2) + 1);
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected JRDataSource getJRDataSource()
/*  56:    */   {
/*  57: 88 */     List listaDatosReporte = new ArrayList();
/*  58: 89 */     JRDataSource ds = null;
/*  59:    */     
/*  60: 91 */     Date fechaDesde = FuncionesUtiles.getFechaFinMes(this.anioDesde, this.mesDesde);
/*  61: 92 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(this.anioHasta, this.mesHasta);
/*  62:    */     
/*  63: 94 */     listaDatosReporte = this.servicioReporteDepreciacion.getReporteDepreciacionMensualNIIF(getActivoFijoSeleccionado(), fechaDesde, fechaHasta, this.indicadorDepreciacionFiscal, this.activo);
/*  64:    */     
/*  65:    */ 
/*  66: 97 */     String[] fields = { "f_codigoActivoFijo", "f_activoFijo", "f_fechaInicioDepreciacion", "f_valorActivo", "f_valorDepreciado", "f_valorResidual", "f_valorADepreciar", "f_vidaUtil", "f_mes", "f_anio", "f_valor", "f_descripcionActivoFijo" };
/*  67:    */     
/*  68:    */ 
/*  69:100 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  70:    */     
/*  71:102 */     return ds;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected String getCompileFileName()
/*  75:    */   {
/*  76:114 */     if (this.indicadorResumen) {
/*  77:115 */       return "reporteDepreciacionMensualResumido";
/*  78:    */     }
/*  79:117 */     return "reporteDepreciacionMensual";
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected Map<String, Object> getReportParameters()
/*  83:    */   {
/*  84:130 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  85:131 */     reportParameters.put("ReportTitle", "Depreciacion");
/*  86:132 */     reportParameters.put("usuario", "Usuario:");
/*  87:133 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/*  88:134 */     reportParameters.put("pagina", "Pagina:");
/*  89:135 */     reportParameters.put("desde", "Desde:");
/*  90:136 */     reportParameters.put("hasta", "Hasta:");
/*  91:137 */     reportParameters.put("p_mesDesde", Integer.valueOf(this.mesDesde));
/*  92:138 */     reportParameters.put("p_anioDesde", Integer.valueOf(this.anioDesde));
/*  93:139 */     reportParameters.put("p_mesHasta", Integer.valueOf(this.mesHasta));
/*  94:140 */     reportParameters.put("p_anioHasta", Integer.valueOf(this.anioHasta));
/*  95:141 */     return reportParameters;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String execute()
/*  99:    */   {
/* 100:    */     try
/* 101:    */     {
/* 102:152 */       super.prepareReport();
/* 103:    */     }
/* 104:    */     catch (JRException e)
/* 105:    */     {
/* 106:155 */       LOG.info("Error JRException");
/* 107:156 */       e.printStackTrace();
/* 108:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 109:    */     }
/* 110:    */     catch (IOException e)
/* 111:    */     {
/* 112:159 */       LOG.info("Error IOException");
/* 113:160 */       e.printStackTrace();
/* 114:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 115:    */     }
/* 116:163 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void validaDatos() {}
/* 120:    */   
/* 121:    */   public String cargarActivoFijo()
/* 122:    */   {
/* 123:171 */     System.out.println("activo fijo Seleccionado");
/* 124:172 */     setActivoFijoSeleccionado(this.activoFijo);
/* 125:173 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public ActivoFijo getActivoFijo()
/* 129:    */   {
/* 130:182 */     return this.activoFijo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 134:    */   {
/* 135:192 */     this.activoFijo = activoFijo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public ActivoFijo getActivoFijoSeleccionado()
/* 139:    */   {
/* 140:201 */     return this.activoFijoSeleccionado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setActivoFijoSeleccionado(ActivoFijo activoFijoSeleccionado)
/* 144:    */   {
/* 145:211 */     this.activoFijoSeleccionado = activoFijoSeleccionado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<SelectItem> getListaSelectItemMesDesde()
/* 149:    */   {
/* 150:220 */     if (this.listaSelectItemMesDesde == null)
/* 151:    */     {
/* 152:221 */       this.listaSelectItemMesDesde = new ArrayList();
/* 153:222 */       for (Mes mes : Mes.values())
/* 154:    */       {
/* 155:223 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 156:224 */         this.listaSelectItemMesDesde.add(selectItem);
/* 157:    */       }
/* 158:    */     }
/* 159:227 */     return this.listaSelectItemMesDesde;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaSelectItemMesDesde(List<SelectItem> listaSelectItemMesDesde)
/* 163:    */   {
/* 164:237 */     this.listaSelectItemMesDesde = listaSelectItemMesDesde;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<SelectItem> getListaSelectItemMesHasta()
/* 168:    */   {
/* 169:246 */     if (this.listaSelectItemMesHasta == null)
/* 170:    */     {
/* 171:247 */       this.listaSelectItemMesHasta = new ArrayList();
/* 172:248 */       for (Mes mes : Mes.values())
/* 173:    */       {
/* 174:249 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 175:250 */         this.listaSelectItemMesHasta.add(selectItem);
/* 176:    */       }
/* 177:    */     }
/* 178:253 */     return this.listaSelectItemMesHasta;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setListaSelectItemMesHasta(List<SelectItem> listaSelectItemMesHasta)
/* 182:    */   {
/* 183:263 */     this.listaSelectItemMesHasta = listaSelectItemMesHasta;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int getMesDesde()
/* 187:    */   {
/* 188:272 */     return this.mesDesde;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setMesDesde(int mesDesde)
/* 192:    */   {
/* 193:282 */     this.mesDesde = mesDesde;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int getAnioDesde()
/* 197:    */   {
/* 198:291 */     return this.anioDesde;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setAnioDesde(int anioDesde)
/* 202:    */   {
/* 203:301 */     this.anioDesde = anioDesde;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int getMesHasta()
/* 207:    */   {
/* 208:310 */     return this.mesHasta;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setMesHasta(int mesHasta)
/* 212:    */   {
/* 213:320 */     this.mesHasta = mesHasta;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public int getAnioHasta()
/* 217:    */   {
/* 218:329 */     return this.anioHasta;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setAnioHasta(int anioHasta)
/* 222:    */   {
/* 223:339 */     this.anioHasta = anioHasta;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isIndicadorResumen()
/* 227:    */   {
/* 228:348 */     return this.indicadorResumen;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 232:    */   {
/* 233:358 */     this.indicadorResumen = indicadorResumen;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean isIndicadorDepreciacionFiscal()
/* 237:    */   {
/* 238:367 */     return this.indicadorDepreciacionFiscal;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setIndicadorDepreciacionFiscal(boolean indicadorDepreciacionFiscal)
/* 242:    */   {
/* 243:377 */     this.indicadorDepreciacionFiscal = indicadorDepreciacionFiscal;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public boolean isActivo()
/* 247:    */   {
/* 248:386 */     return this.activo;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setActivo(boolean activo)
/* 252:    */   {
/* 253:396 */     this.activo = activo;
/* 254:    */   }
/* 255:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteDepreciacionMensualBean
 * JD-Core Version:    0.7.0.1
 */