/*   1:    */ package com.asinfo.as2.finaciero.presupuesto.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DimensionContable;
/*   5:    */ import com.asinfo.as2.entities.Ejercicio;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   8:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   9:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio;
/*  11:    */ import com.asinfo.as2.financiero.presupuesto.reportes.servicio.ServicioReportePresupuesto;
/*  12:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReportePresupuestoBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1127232307431136748L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReportePresupuesto servicioReportePresupuesto;
/*  37:    */   @EJB
/*  38:    */   private ServicioEjercicio servicioEjercicio;
/*  39:    */   @EJB
/*  40:    */   private ServicioDimensionContable servicioDimensionContable;
/*  41:    */   private List<Ejercicio> listaEjercicio;
/*  42:    */   private List<DimensionContable> listaDimensionContable;
/*  43:    */   private Ejercicio ejercicio;
/*  44:    */   private DimensionContable dimensionContable;
/*  45:    */   private Mes mesSeleccionado;
/*  46:    */   private String nivel;
/*  47:    */   
/*  48:    */   static enum TIPO_REPORTE
/*  49:    */   {
/*  50: 48 */     POR_DIMENSION("Por dimensión"),  DIMENSION_CUENTA("Dimensión y cuenta contable");
/*  51:    */     
/*  52:    */     private String nombre;
/*  53:    */     
/*  54:    */     private TIPO_REPORTE(String nombre)
/*  55:    */     {
/*  56: 58 */       this.nombre = nombre;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public String getNombre()
/*  60:    */     {
/*  61: 67 */       return this.nombre;
/*  62:    */     }
/*  63:    */     
/*  64:    */     public void setNombre(String nombre)
/*  65:    */     {
/*  66: 76 */       this.nombre = nombre;
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70: 94 */   private int cantDigitos = 0;
/*  71:    */   String[] arregloMascara;
/*  72: 96 */   private TIPO_REPORTE tipoReporte = TIPO_REPORTE.POR_DIMENSION;
/*  73:    */   private boolean render;
/*  74:    */   
/*  75:    */   @PostConstruct
/*  76:    */   public void init()
/*  77:    */   {
/*  78:101 */     getDimensionPresupuesto();
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected JRDataSource getJRDataSource()
/*  82:    */   {
/*  83:106 */     JRDataSource ds = null;
/*  84:107 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  85:108 */     listaDatosReporte = this.servicioReportePresupuesto.getReportePresupuesto(getEjercicio(), getMesSeleccionado(), this.cantDigitos, this.dimensionContable, 
/*  86:109 */       getListaDimensionContable(), this.tipoReporte.ordinal(), AppUtil.getUsuarioEnSesion().getIdUsuario(), AppUtil.getOrganizacion()
/*  87:110 */       .getIdOrganizacion());
/*  88:    */     String[] fields;
/*  89:    */     String[] fields;
/*  90:112 */     if ((this.tipoReporte.ordinal() == 1) && (this.mesSeleccionado != null)) {
/*  91:113 */       fields = new String[] { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorMes", "f_transferenciasIngreso", "f_transferenciasEgreso", "f_incrementos", "f_decrementos" };
/*  92:    */     } else {
/*  93:117 */       fields = new String[] { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorEnero", "f_valorFebrero", "f_valorMarzo", "f_valorAbril", "f_valorMayo", "f_valorJunio", "f_valorJulio", "f_valorAgosto", "f_valorSeptiembre", "f_valorOctubre", "f_valorNoviembre", "f_valorDiciembre", "f_transfereciasIngresosEnero", "f_transfereciasEgresosEnero", "f_incrementosEnero", "f_decrementosEnero", "f_transfereciasIngresosFebrero", "f_transfereciasEgresosFebrero", "f_incrementosFebrero", "f_decrementosFebrero", "f_transfereciasIngresosMarzo", "f_transfereciasEgresosMarzo", "f_incrementosMarzo", "f_decrementosMarzo", "f_transfereciasIngresosAbril", "f_transfereciasEgresosAbril", "f_incrementosAbril", "f_decrementosAbril", "f_transfereciasIngresosMayo", "f_transfereciasEgresosMayo", "f_incrementosMayo", "f_decrementosMayo", "f_transfereciasIngresosJunio", "f_transfereciasEgresosJunio", "f_incrementosJunio", "f_decrementosJunio", "f_transfereciasIngresosJulio", "f_transfereciasEgresosJulio", "f_incrementosJulio", "f_decrementosJulio", "f_transfereciasIngresosAgosto", "f_transfereciasEgresosAgosto", "f_incrementosAgosto", "f_decrementosAgosto", "f_transfereciasIngresosSeptiembre", "f_transfereciasEgresosSeptiembre", "f_incrementosSeptiembre", "f_decrementosSeptiembre", "f_transfereciasIngresosOctubre", "f_transfereciasEgresosOctubre", "f_incrementosOctubre", "f_decrementosOctubre", "f_transfereciasIngresosNoviembre", "f_transfereciasEgresosNoviembre", "f_incrementosNoviembre", "f_decrementosNoviembre", "f_transfereciasIngresosDiciembre", "f_transfereciasEgresosDiciembre", "f_incrementosDiciembre", "f_decrementosDiciembre" };
/*  94:    */     }
/*  95:133 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  96:    */     
/*  97:135 */     return ds;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected String getCompileFileName()
/* 101:    */   {
/* 102:140 */     if (this.tipoReporte.ordinal() == 1)
/* 103:    */     {
/* 104:141 */       if (this.mesSeleccionado == null) {
/* 105:142 */         return "reportePresupuestoPorCuentaContable";
/* 106:    */       }
/* 107:144 */       return "reportePresupuestoPorCuentaContableMes";
/* 108:    */     }
/* 109:146 */     return "reportePresupuestoPorDimension";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String execute()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:153 */       super.prepareReport();
/* 117:    */     }
/* 118:    */     catch (JRException e)
/* 119:    */     {
/* 120:155 */       LOG.info("Error JRException");
/* 121:156 */       e.printStackTrace();
/* 122:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 123:    */     }
/* 124:    */     catch (IOException e)
/* 125:    */     {
/* 126:159 */       LOG.info("Error IOException");
/* 127:160 */       e.printStackTrace();
/* 128:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 129:    */     }
/* 130:164 */     return null;
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected Map<String, Object> getReportParameters()
/* 134:    */   {
/* 135:169 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 136:170 */     reportParameters.put("ReportTitle", "Reporte Presupuesto");
/* 137:171 */     reportParameters.put("ejercicio", this.ejercicio.getNombre());
/* 138:172 */     if ((this.tipoReporte.ordinal() == 1) && (this.mesSeleccionado != null)) {
/* 139:173 */       reportParameters.put("mes", this.mesSeleccionado.getNombre());
/* 140:    */     }
/* 141:174 */     return reportParameters;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Ejercicio getEjercicio()
/* 145:    */   {
/* 146:183 */     return this.ejercicio;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setEjercicio(Ejercicio ejercicio)
/* 150:    */   {
/* 151:193 */     this.ejercicio = ejercicio;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<Ejercicio> getListaEjercicio()
/* 155:    */   {
/* 156:202 */     if (this.listaEjercicio == null) {
/* 157:203 */       this.listaEjercicio = this.servicioEjercicio.obtenerListaCombo("nombre", true, null);
/* 158:    */     }
/* 159:205 */     return this.listaEjercicio;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<SelectItem> getListaMes()
/* 163:    */   {
/* 164:209 */     ArrayList<SelectItem> lista = new ArrayList();
/* 165:210 */     for (Mes mes : Mes.values()) {
/* 166:211 */       lista.add(new SelectItem(mes, mes.getNombre()));
/* 167:    */     }
/* 168:213 */     return lista;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Mes getMesSeleccionado()
/* 172:    */   {
/* 173:217 */     return this.mesSeleccionado;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setMesSeleccionado(Mes mesSeleccionado)
/* 177:    */   {
/* 178:221 */     this.mesSeleccionado = mesSeleccionado;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<DimensionContable> getListaDimensionContable()
/* 182:    */   {
/* 183:228 */     if ((getNivel() == null) || (getNivel().isEmpty()))
/* 184:    */     {
/* 185:229 */       this.listaDimensionContable = this.servicioDimensionContable.obtenerDimensionContablePorUsuario(AppUtil.getOrganizacion().getId(), 
/* 186:230 */         AppUtil.getUsuarioEnSesion().getIdUsuario(), 0, true);
/* 187:    */     }
/* 188:    */     else
/* 189:    */     {
/* 190:232 */       this.cantDigitos = 0;
/* 191:233 */       for (int i = 0; i <= this.nivel.split("\\.").length - 1; i++) {
/* 192:234 */         this.cantDigitos += this.arregloMascara[i].length();
/* 193:    */       }
/* 194:236 */       this.cantDigitos += this.nivel.split("\\.").length;
/* 195:237 */       this.listaDimensionContable = this.servicioDimensionContable.obtenerDimensionContablePorUsuario(AppUtil.getOrganizacion().getId(), 
/* 196:238 */         AppUtil.getUsuarioEnSesion().getIdUsuario(), this.cantDigitos, true);
/* 197:    */     }
/* 198:240 */     return this.listaDimensionContable;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public DimensionContable getDimensionContable()
/* 202:    */   {
/* 203:244 */     return this.dimensionContable;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 207:    */   {
/* 208:248 */     this.dimensionContable = dimensionContable;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<SelectItem> getListaNivelDimensionContable()
/* 212:    */   {
/* 213:252 */     return getNivelDimension(getDimensionPresupuesto());
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getDimensionPresupuesto()
/* 217:    */   {
/* 218:256 */     OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/* 219:257 */     String numeroDimension = "";
/* 220:258 */     if ((!aux.getNombreDimension1().equals("")) && (aux.isIndicadorUsoPresupuestoDimension1()))
/* 221:    */     {
/* 222:259 */       this.arregloMascara = aux.getMascaraDimension1().split("\\.");
/* 223:260 */       numeroDimension = "1";
/* 224:    */     }
/* 225:261 */     else if ((!aux.getNombreDimension2().equals("")) && (aux.isIndicadorUsoPresupuestoDimension2()))
/* 226:    */     {
/* 227:262 */       this.arregloMascara = aux.getMascaraDimension2().split("\\.");
/* 228:263 */       numeroDimension = "2";
/* 229:    */     }
/* 230:264 */     else if ((!aux.getNombreDimension3().equals("")) && (aux.isIndicadorUsoPresupuestoDimension3()))
/* 231:    */     {
/* 232:265 */       this.arregloMascara = aux.getMascaraDimension3().split("\\.");
/* 233:266 */       numeroDimension = "3";
/* 234:    */     }
/* 235:267 */     else if ((!aux.getNombreDimension4().equals("")) && (aux.isIndicadorUsoPresupuestoDimension4()))
/* 236:    */     {
/* 237:268 */       this.arregloMascara = aux.getMascaraDimension4().split("\\.");
/* 238:269 */       numeroDimension = "4";
/* 239:    */     }
/* 240:270 */     else if ((!aux.getNombreDimension5().equals("")) && (aux.isIndicadorUsoPresupuestoDimension5()))
/* 241:    */     {
/* 242:271 */       this.arregloMascara = aux.getMascaraDimension5().split("\\.");
/* 243:272 */       numeroDimension = "5";
/* 244:    */     }
/* 245:274 */     return numeroDimension;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getNivel()
/* 249:    */   {
/* 250:278 */     return this.nivel;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setNivel(String nivel)
/* 254:    */   {
/* 255:282 */     this.nivel = nivel;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<SelectItem> getListaTipoReporte()
/* 259:    */   {
/* 260:286 */     List<SelectItem> lista = new ArrayList();
/* 261:287 */     for (TIPO_REPORTE tipo : TIPO_REPORTE.values()) {
/* 262:288 */       lista.add(new SelectItem(tipo, tipo.getNombre()));
/* 263:    */     }
/* 264:290 */     return lista;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public TIPO_REPORTE getTipoReporte()
/* 268:    */   {
/* 269:294 */     return this.tipoReporte;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setTipoReporte(TIPO_REPORTE tipoReporte)
/* 273:    */   {
/* 274:298 */     this.tipoReporte = tipoReporte;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String actualizarDimension()
/* 278:    */   {
/* 279:302 */     setDimensionContable(null);
/* 280:303 */     return null;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public boolean isRender()
/* 284:    */   {
/* 285:307 */     if (this.tipoReporte.ordinal() == 1) {
/* 286:308 */       this.render = true;
/* 287:    */     } else {
/* 288:310 */       this.render = false;
/* 289:    */     }
/* 290:311 */     return this.render;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setRender(boolean render)
/* 294:    */   {
/* 295:315 */     this.render = render;
/* 296:    */   }
/* 297:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.reportes.ReportePresupuestoBean
 * JD-Core Version:    0.7.0.1
 */