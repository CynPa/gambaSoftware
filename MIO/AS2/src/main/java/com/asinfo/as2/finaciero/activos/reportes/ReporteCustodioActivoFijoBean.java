/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteCustodioActivoFijo;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Calendar;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteCustodioActivoFijoBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -4208174416130321470L;
/*  33:    */   @EJB
/*  34:    */   private ServicioReporteCustodioActivoFijo servicioReporteCustodioActivoFijo;
/*  35:    */   @EJB
/*  36:    */   private ServicioEmpresa servicioEmpresa;
/*  37:    */   private ActivoFijo activoFijo;
/*  38:    */   private ActivoFijo activoFijoSeleccionado;
/*  39:    */   private Date fechaDesde;
/*  40:    */   private Date fechaHasta;
/*  41:    */   private Empresa cliente;
/*  42:    */   private Empresa empleado;
/*  43:    */   
/*  44:    */   static enum Orden
/*  45:    */   {
/*  46: 54 */     CUSTODIO("Custodio"),  EMPLEADO("Empleado");
/*  47:    */     
/*  48:    */     private String nombre;
/*  49:    */     
/*  50:    */     private Orden(String nombre)
/*  51:    */     {
/*  52: 59 */       this.nombre = nombre;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public String getNombre()
/*  56:    */     {
/*  57: 63 */       return this.nombre;
/*  58:    */     }
/*  59:    */     
/*  60:    */     public void setNombre(String nombre)
/*  61:    */     {
/*  62: 67 */       this.nombre = nombre;
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66: 84 */   private Orden orden = Orden.CUSTODIO;
/*  67:    */   
/*  68:    */   protected JRDataSource getJRDataSource()
/*  69:    */   {
/*  70: 95 */     List listaDatosReporte = new ArrayList();
/*  71: 96 */     JRDataSource ds = null;
/*  72:    */     
/*  73: 98 */     validaDatos();
/*  74:    */     
/*  75:100 */     listaDatosReporte = this.servicioReporteCustodioActivoFijo.getReporteCustodioActivoFijo(getActivoFijoSeleccionado().getId(), this.fechaDesde, this.fechaHasta, this.cliente, this.empleado, this.orden
/*  76:101 */       .ordinal());
/*  77:102 */     String[] fields = { "f_fechaInicio", "f_fechaFin", "f_descripcionCustodio", "f_codigoActivoFijo", "f_nombreActivoFijo", "f_nombreEmpleado", "f_identificacionEmpleado", "f_codigoUbicacion", "f_nombreUbicacion", "f_descripcionUbicacion", "f_codigoDepartamento", "f_nombreDepartamento", "f_descripcionDepartamento", "f_empresa", "f_empresaIdentificacion" };
/*  78:    */     
/*  79:    */ 
/*  80:    */ 
/*  81:106 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  82:    */     
/*  83:108 */     return ds;
/*  84:    */   }
/*  85:    */   
/*  86:    */   @PostConstruct
/*  87:    */   public void init()
/*  88:    */   {
/*  89:114 */     Calendar calfechaDesde = Calendar.getInstance();
/*  90:115 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  91:116 */     this.fechaDesde = calfechaDesde.getTime();
/*  92:117 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected String getCompileFileName()
/*  96:    */   {
/*  97:127 */     if (this.orden.ordinal() == 0) {
/*  98:128 */       return "reporteCustodioActivoFijo";
/*  99:    */     }
/* 100:130 */     return "reporteCustodioActivoFijoCliente";
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected Map<String, Object> getReportParameters()
/* 104:    */   {
/* 105:140 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 106:141 */     validaDatos();
/* 107:142 */     reportParameters.put("ReportTitle", "Reporte de Custodio Activo Fijo");
/* 108:143 */     reportParameters.put("usuario", "Usuario:");
/* 109:144 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/* 110:145 */     reportParameters.put("pagina", "Pagina:");
/* 111:146 */     reportParameters.put("desde", "Desde:");
/* 112:147 */     reportParameters.put("hasta", "Hasta:");
/* 113:148 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 114:149 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 115:150 */     return reportParameters;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String execute()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:161 */       super.prepareReport();
/* 123:    */     }
/* 124:    */     catch (JRException e)
/* 125:    */     {
/* 126:164 */       LOG.info("Error JRException");
/* 127:165 */       e.printStackTrace();
/* 128:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 129:    */     }
/* 130:    */     catch (IOException e)
/* 131:    */     {
/* 132:168 */       LOG.info("Error IOException");
/* 133:169 */       e.printStackTrace();
/* 134:170 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 135:    */     }
/* 136:172 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void validaDatos()
/* 140:    */   {
/* 141:177 */     if (this.fechaDesde == null) {
/* 142:178 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 143:    */     }
/* 144:180 */     if (this.fechaHasta == null) {
/* 145:181 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String cargarActivoFijo()
/* 150:    */   {
/* 151:186 */     setActivoFijoSeleccionado(this.activoFijo);
/* 152:187 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public ActivoFijo getActivoFijo()
/* 156:    */   {
/* 157:196 */     return this.activoFijo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 161:    */   {
/* 162:206 */     this.activoFijo = activoFijo;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public ActivoFijo getActivoFijoSeleccionado()
/* 166:    */   {
/* 167:215 */     if (this.activoFijoSeleccionado == null) {
/* 168:216 */       this.activoFijoSeleccionado = new ActivoFijo();
/* 169:    */     }
/* 170:218 */     return this.activoFijoSeleccionado;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setActivoFijoSeleccionado(ActivoFijo activoFijoSeleccionado)
/* 174:    */   {
/* 175:228 */     System.out.println("SET");
/* 176:229 */     this.activoFijoSeleccionado = activoFijoSeleccionado;
/* 177:230 */     System.out.println("RESULT SET: " + this.activoFijoSeleccionado.getId());
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Date getFechaDesde()
/* 181:    */   {
/* 182:239 */     return this.fechaDesde;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setFechaDesde(Date fechaDesde)
/* 186:    */   {
/* 187:249 */     this.fechaDesde = fechaDesde;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Date getFechaHasta()
/* 191:    */   {
/* 192:258 */     return this.fechaHasta;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setFechaHasta(Date fechaHasta)
/* 196:    */   {
/* 197:268 */     this.fechaHasta = fechaHasta;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Empresa getCliente()
/* 201:    */   {
/* 202:272 */     return this.cliente;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setCliente(Empresa cliente)
/* 206:    */   {
/* 207:276 */     this.cliente = cliente;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Empresa getEmpleado()
/* 211:    */   {
/* 212:280 */     return this.empleado;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setEmpleado(Empresa empleado)
/* 216:    */   {
/* 217:284 */     this.empleado = empleado;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<Empresa> autocompletarEmpleados(String consulta)
/* 221:    */   {
/* 222:288 */     return this.servicioEmpresa.autocompletarEmpleados(consulta);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 226:    */   {
/* 227:292 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Orden getOrden()
/* 231:    */   {
/* 232:296 */     return this.orden;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setOrden(Orden orden)
/* 236:    */   {
/* 237:300 */     this.orden = orden;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<SelectItem> getListaOrden()
/* 241:    */   {
/* 242:307 */     List<SelectItem> listaOrden = new ArrayList();
/* 243:308 */     for (Orden tipoReporte : Orden.values())
/* 244:    */     {
/* 245:309 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 246:310 */       listaOrden.add(item);
/* 247:    */     }
/* 248:313 */     return listaOrden;
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteCustodioActivoFijoBean
 * JD-Core Version:    0.7.0.1
 */