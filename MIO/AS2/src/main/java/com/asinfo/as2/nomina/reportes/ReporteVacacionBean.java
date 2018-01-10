/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   5:    */ import com.asinfo.as2.entities.Departamento;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import javax.faces.model.SelectItem;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteVacacionBean
/*  28:    */   extends AbstractBaseReportBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioReporteNomina servicioReporteNomina;
/*  33:    */   @EJB
/*  34:    */   private ServicioSucursal servicioSucursal;
/*  35:    */   @EJB
/*  36:    */   private ServicioDepartamento servicioDepartamento;
/*  37:    */   private Empleado empleado;
/*  38:    */   private Sucursal sucursal;
/*  39:    */   private Departamento departamento;
/*  40:    */   
/*  41:    */   private static enum TIPO_REPORTE_ENUM
/*  42:    */   {
/*  43: 46 */     GENERAL("General"),  RANGO_FECHAS("Rango de fechas");
/*  44:    */     
/*  45:    */     private String nombre;
/*  46:    */     
/*  47:    */     private TIPO_REPORTE_ENUM(String nombre)
/*  48:    */     {
/*  49: 56 */       this.nombre = nombre;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public String getNombre()
/*  53:    */     {
/*  54: 65 */       return this.nombre;
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58: 83 */   private List<Sucursal> listaSucursal = new ArrayList();
/*  59:    */   private List<Departamento> listaDepartamento;
/*  60: 85 */   private final String COMPILE_FILE_NAME = "vacacion";
/*  61:    */   private String tipoSelecionado;
/*  62:    */   private List<SelectItem> listaFormaPagoEmpleado;
/*  63:    */   private FormaPagoEmpleado formaPagoEmpleado;
/*  64: 89 */   private boolean activos = true;
/*  65:    */   private boolean resumido;
/*  66:    */   private Date fechaDesde;
/*  67:    */   private Date fechaHasta;
/*  68: 93 */   private TIPO_REPORTE_ENUM tipoReporte = TIPO_REPORTE_ENUM.GENERAL;
/*  69:    */   private boolean saldoMayorCero;
/*  70:    */   
/*  71:    */   protected JRDataSource getJRDataSource()
/*  72:    */   {
/*  73:105 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  74:106 */     JRDataSource ds = null;
/*  75:    */     
/*  76:108 */     HashMap<String, Long> hmListaDatosReporte = new HashMap();
/*  77:109 */     List<Object[]> listaDiasPendientCero = new ArrayList();
/*  78:    */     try
/*  79:    */     {
/*  80:112 */       listaDatosReporte = this.servicioReporteNomina.getVacacion(getDepartamento(), getEmpleado(), this.sucursal, getTipoSelecionado(), this.formaPagoEmpleado, 
/*  81:113 */         AppUtil.getOrganizacion().getId(), this.activos, getFechaDesde(), getFechaHasta(), TIPO_REPORTE_ENUM.GENERAL.equals(this.tipoReporte));
/*  82:    */       String[] fields;
/*  83:    */       String[] fields;
/*  84:114 */       if (TIPO_REPORTE_ENUM.GENERAL.equals(this.tipoReporte))
/*  85:    */       {
/*  86:116 */         for (Object[] dato : listaDatosReporte)
/*  87:    */         {
/*  88:118 */           String identificacion = dato[1].toString();
/*  89:119 */           String inicioPeriodo = dato[3].toString();
/*  90:120 */           String finPeriodo = dato[4].toString();
/*  91:121 */           String clave = inicioPeriodo + "~" + finPeriodo + "~" + identificacion;
/*  92:123 */           if (hmListaDatosReporte.containsKey(clave))
/*  93:    */           {
/*  94:124 */             dato[5] = Integer.valueOf(0);
/*  95:125 */             dato[6] = Integer.valueOf(0);
/*  96:126 */             dato[8] = Long.valueOf(Long.valueOf(dato[5].toString()).longValue() + Long.valueOf(dato[6].toString()).longValue() + ((Long)hmListaDatosReporte.get(clave)).longValue() - ((Long)dato[7])
/*  97:127 */               .longValue());
/*  98:    */             
/*  99:129 */             hmListaDatosReporte.put(clave, (Long)dato[8]);
/* 100:    */           }
/* 101:    */           else
/* 102:    */           {
/* 103:132 */             hmListaDatosReporte.put(clave, (Long)dato[8]);
/* 104:    */           }
/* 105:134 */           if ((this.saldoMayorCero) && 
/* 106:135 */             (Long.valueOf(dato[8].toString()).equals(Long.valueOf(0L)))) {
/* 107:136 */             listaDiasPendientCero.add(dato);
/* 108:    */           }
/* 109:    */         }
/* 110:141 */         if ((this.saldoMayorCero) && (listaDiasPendientCero.size() > 0)) {
/* 111:142 */           listaDatosReporte.removeAll(listaDiasPendientCero);
/* 112:    */         }
/* 113:145 */         fields = new String[] { "codigo", "identificacion", "nombreEmpleado", "fechaInicioPeriodo", "fechaFinPeriodo", "dias", "diasAdicionales", "diasTomados", "diasPendientes", "descripcion", "f_numero" };
/* 114:    */       }
/* 115:    */       else
/* 116:    */       {
/* 117:148 */         fields = new String[] { "codigo", "identificacion", "nombreEmpleado", "f_fechaInicio", "f_fechaFin", "f_diasVacacion", "descripcion", "f_numero" };
/* 118:    */       }
/* 119:151 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:154 */       LOG.info("Error " + e);
/* 124:155 */       e.printStackTrace();
/* 125:    */     }
/* 126:157 */     return ds;
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected String getCompileFileName()
/* 130:    */   {
/* 131:168 */     if (TIPO_REPORTE_ENUM.RANGO_FECHAS.equals(this.tipoReporte)) {
/* 132:169 */       return "vacacionPorFechas";
/* 133:    */     }
/* 134:171 */     if (isResumido()) {
/* 135:172 */       return "vacacionResumido";
/* 136:    */     }
/* 137:174 */     return "vacacion";
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected Map<String, Object> getReportParameters()
/* 141:    */   {
/* 142:180 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 143:181 */     reportParameters.put("ReportTitle", "Reporte De Vacaciones");
/* 144:    */     
/* 145:183 */     return reportParameters;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String execute()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:193 */       super.prepareReport();
/* 153:    */     }
/* 154:    */     catch (Exception e)
/* 155:    */     {
/* 156:195 */       e.printStackTrace();
/* 157:    */     }
/* 158:198 */     return null;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String cargarEmpleado()
/* 162:    */   {
/* 163:202 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Empleado getEmpleado()
/* 167:    */   {
/* 168:211 */     if (this.empleado == null) {
/* 169:212 */       this.empleado = new Empleado();
/* 170:    */     }
/* 171:214 */     return this.empleado;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setEmpleado(Empleado empleado)
/* 175:    */   {
/* 176:224 */     this.empleado = empleado;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getCOMPILE_FILE_NAME()
/* 180:    */   {
/* 181:233 */     return "vacacion";
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<Sucursal> getListaSucursal()
/* 185:    */   {
/* 186:242 */     if (this.listaSucursal == null) {
/* 187:243 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 188:    */     }
/* 189:245 */     return this.listaSucursal;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Sucursal getSucursal()
/* 193:    */   {
/* 194:254 */     return this.sucursal;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setSucursal(Sucursal sucursal)
/* 198:    */   {
/* 199:264 */     this.sucursal = sucursal;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Departamento getDepartamento()
/* 203:    */   {
/* 204:273 */     if (this.departamento == null) {
/* 205:274 */       this.departamento = new Departamento();
/* 206:    */     }
/* 207:276 */     return this.departamento;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setDepartamento(Departamento departamento)
/* 211:    */   {
/* 212:286 */     this.departamento = departamento;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<Departamento> getListaDepartamento()
/* 216:    */   {
/* 217:295 */     if (this.listaDepartamento == null) {
/* 218:296 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 219:    */     }
/* 220:298 */     return this.listaDepartamento;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getTipoSelecionado()
/* 224:    */   {
/* 225:307 */     return this.tipoSelecionado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setTipoSelecionado(String tipoSelecionado)
/* 229:    */   {
/* 230:317 */     this.tipoSelecionado = tipoSelecionado;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public List<SelectItem> getListaFormaPagoEmpleado()
/* 234:    */   {
/* 235:326 */     if (this.listaFormaPagoEmpleado == null)
/* 236:    */     {
/* 237:327 */       this.listaFormaPagoEmpleado = new ArrayList();
/* 238:328 */       for (FormaPagoEmpleado formaPagoEmpleado : FormaPagoEmpleado.values())
/* 239:    */       {
/* 240:329 */         SelectItem item = new SelectItem(formaPagoEmpleado, formaPagoEmpleado.getNombre());
/* 241:330 */         this.listaFormaPagoEmpleado.add(item);
/* 242:    */       }
/* 243:    */     }
/* 244:333 */     return this.listaFormaPagoEmpleado;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public FormaPagoEmpleado getFormaPagoEmpleado()
/* 248:    */   {
/* 249:342 */     return this.formaPagoEmpleado;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/* 253:    */   {
/* 254:352 */     this.formaPagoEmpleado = formaPagoEmpleado;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean isActivos()
/* 258:    */   {
/* 259:356 */     return this.activos;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setActivos(boolean activos)
/* 263:    */   {
/* 264:360 */     this.activos = activos;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isResumido()
/* 268:    */   {
/* 269:367 */     return this.resumido;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setResumido(boolean resumido)
/* 273:    */   {
/* 274:374 */     this.resumido = resumido;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Date getFechaDesde()
/* 278:    */   {
/* 279:378 */     return this.fechaDesde;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setFechaDesde(Date fechaDesde)
/* 283:    */   {
/* 284:382 */     this.fechaDesde = fechaDesde;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Date getFechaHasta()
/* 288:    */   {
/* 289:386 */     return this.fechaHasta;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setFechaHasta(Date fechaHasta)
/* 293:    */   {
/* 294:390 */     this.fechaHasta = fechaHasta;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<SelectItem> getListaTipoReporte()
/* 298:    */   {
/* 299:394 */     List<SelectItem> lista = new ArrayList();
/* 300:395 */     for (TIPO_REPORTE_ENUM tr : TIPO_REPORTE_ENUM.values()) {
/* 301:396 */       lista.add(new SelectItem(tr, tr.getNombre()));
/* 302:    */     }
/* 303:398 */     return lista;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public TIPO_REPORTE_ENUM getTipoReporte()
/* 307:    */   {
/* 308:402 */     return this.tipoReporte;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setTipoReporte(TIPO_REPORTE_ENUM tipoReporte)
/* 312:    */   {
/* 313:406 */     this.tipoReporte = tipoReporte;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public boolean isSaldoMayorCero()
/* 317:    */   {
/* 318:410 */     return this.saldoMayorCero;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setSaldoMayorCero(boolean saldoMayorCero)
/* 322:    */   {
/* 323:414 */     this.saldoMayorCero = saldoMayorCero;
/* 324:    */   }
/* 325:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteVacacionBean
 * JD-Core Version:    0.7.0.1
 */