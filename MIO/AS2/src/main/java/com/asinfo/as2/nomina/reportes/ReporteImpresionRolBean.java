/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PagoRol;
/*   8:    */ import com.asinfo.as2.entities.Quincena;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  11:    */ import com.asinfo.as2.enumeraciones.ExportOption;
/*  12:    */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  13:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  14:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  15:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  16:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.RequestScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @RequestScoped
/*  38:    */ public class ReporteImpresionRolBean
/*  39:    */   extends AbstractBaseReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioReporteNomina servicioReporteNomina;
/*  44:    */   @EJB
/*  45:    */   private ServicioPagoRol servicioPagoRol;
/*  46:    */   @EJB
/*  47:    */   private ServicioSucursal servicioSucursal;
/*  48:    */   @EJB
/*  49:    */   private ServicioEmpleado servicioEmpleado;
/*  50:    */   @EJB
/*  51:    */   private ServicioUsuario servicioUsuario;
/*  52:    */   private List<SelectItem> listaPagoRol;
/*  53:    */   private PagoRol pagoRol;
/*  54:    */   private Empleado empleado;
/*  55:    */   private Sucursal sucursal;
/*  56:    */   private List<SelectItem> listaFormaPagoEmpleado;
/*  57:    */   private FormaPagoEmpleado formaPagoEmpleado;
/*  58:    */   private List<Sucursal> listaSucursal;
/*  59:    */   private List<SelectItem> listaExportOption;
/*  60:    */   private Usuario usuario;
/*  61:    */   protected Empleado empleadoSession;
/*  62:    */   protected boolean empleadoSimple;
/*  63:    */   private EntidadUsuario entidadUsuario;
/*  64:    */   
/*  65:    */   @PostConstruct
/*  66:    */   public void init()
/*  67:    */   {
/*  68: 77 */     int idUsuario = AppUtil.getUsuarioEnSesion().getIdUsuario();
/*  69: 78 */     this.entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(idUsuario));
/*  70: 79 */     if ((this.entidadUsuario.getEmpleado() == null) || (this.entidadUsuario.isIndicadorAdministrador())) {
/*  71: 80 */       setEmpleadoSimple(false);
/*  72:    */     } else {
/*  73: 82 */       setEmpleadoSimple(true);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected JRDataSource getJRDataSource()
/*  78:    */   {
/*  79: 97 */     List listaDatosReporte = new ArrayList();
/*  80: 98 */     JRDataSource ds = null;
/*  81:    */     boolean aprobados;
/*  82:    */     boolean aprobados;
/*  83: 99 */     if (ParametrosSistema.getIndicadorAprobarNomina(AppUtil.getOrganizacion().getId()).booleanValue()) {
/*  84:100 */       aprobados = true;
/*  85:    */     } else {
/*  86:102 */       aprobados = false;
/*  87:    */     }
/*  88:105 */     if (getEmpleado() != null) {
/*  89:106 */       listaDatosReporte = this.servicioReporteNomina.getSobreEmpleado(this.pagoRol, getEmpleado(), this.formaPagoEmpleado, getSucursal(), 
/*  90:107 */         AppUtil.getOrganizacion().getIdOrganizacion(), null, aprobados);
/*  91:    */     }
/*  92:109 */     String[] fields = { "nombreEmpleado", "identificacion", "operacion", "nombreRubro", "ingresos", "egresos", "fechaPagoRol", "cargoEmpleado", "diasFalta", "tiempo", "departamento", "centroCosto", "diasTrabajados", "quincena", "indicadorProvision", "indicadorImpresionSobre" };
/*  93:    */     
/*  94:111 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  95:    */     
/*  96:113 */     return ds;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected Map<String, Object> getReportParameters()
/* 100:    */   {
/* 101:126 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 102:127 */     reportParameters.put("ReportTitle", "Sobre Rol");
/* 103:128 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 104:129 */     reportParameters.put("FechaRol", FuncionesUtiles.nombreMes(this.pagoRol.getMes() - 1) + "-" + Integer.toString(this.pagoRol.getAnio()));
/* 105:    */     
/* 106:131 */     return reportParameters;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String execute()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:141 */       super.prepareReport();
/* 114:    */     }
/* 115:    */     catch (JRException e)
/* 116:    */     {
/* 117:143 */       LOG.info("Error JRException");
/* 118:144 */       e.printStackTrace();
/* 119:145 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:    */     catch (IOException e)
/* 122:    */     {
/* 123:147 */       LOG.info("Error IOException");
/* 124:148 */       e.printStackTrace();
/* 125:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 126:    */     }
/* 127:152 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String cargarEmpleado()
/* 131:    */   {
/* 132:156 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected String getCompileFileName()
/* 136:    */   {
/* 137:168 */     return "reporteSobreEmpleado";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Empleado getEmpleado()
/* 141:    */   {
/* 142:178 */     if (this.empleado == null)
/* 143:    */     {
/* 144:179 */       EntidadUsuario entidadUsuario = this.servicioUsuario.cargarDetalle(getUsuario().getIdUsuario(), AppUtil.getOrganizacion().getId());
/* 145:180 */       this.empleado = entidadUsuario.getEmpleado();
/* 146:    */     }
/* 147:182 */     return this.empleado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setEmpleado(Empleado empleado)
/* 151:    */   {
/* 152:192 */     this.empleado = empleado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<SelectItem> getListaPagoRol()
/* 156:    */   {
/* 157:201 */     List<PagoRol> lista = new ArrayList();
/* 158:202 */     Map<String, String> filters = new HashMap();
/* 159:203 */     filters.put("indicadorFiniquito", "false");
/* 160:    */     
/* 161:205 */     lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 162:206 */     if (this.listaPagoRol == null)
/* 163:    */     {
/* 164:207 */       this.listaPagoRol = new ArrayList();
/* 165:208 */       for (PagoRol pagoRol : lista)
/* 166:    */       {
/* 167:214 */         String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 168:215 */         SelectItem item = new SelectItem(Integer.valueOf(pagoRol.getIdPagoRol()), label);
/* 169:216 */         this.listaPagoRol.add(item);
/* 170:    */       }
/* 171:    */     }
/* 172:219 */     return this.listaPagoRol;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public PagoRol getPagoRol()
/* 176:    */   {
/* 177:228 */     if (this.pagoRol == null) {
/* 178:229 */       this.pagoRol = new PagoRol();
/* 179:    */     }
/* 180:231 */     return this.pagoRol;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setPagoRol(PagoRol pagoRol)
/* 184:    */   {
/* 185:241 */     this.pagoRol = pagoRol;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<SelectItem> getListaFormaPagoEmpleado()
/* 189:    */   {
/* 190:250 */     if (this.listaFormaPagoEmpleado == null)
/* 191:    */     {
/* 192:251 */       this.listaFormaPagoEmpleado = new ArrayList();
/* 193:252 */       for (FormaPagoEmpleado formaPagoEmpleado : FormaPagoEmpleado.values())
/* 194:    */       {
/* 195:253 */         SelectItem item = new SelectItem(formaPagoEmpleado, formaPagoEmpleado.getNombre());
/* 196:254 */         this.listaFormaPagoEmpleado.add(item);
/* 197:    */       }
/* 198:    */     }
/* 199:257 */     return this.listaFormaPagoEmpleado;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public FormaPagoEmpleado getFormaPagoEmpleado()
/* 203:    */   {
/* 204:266 */     return this.formaPagoEmpleado;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/* 208:    */   {
/* 209:276 */     this.formaPagoEmpleado = formaPagoEmpleado;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Sucursal getSucursal()
/* 213:    */   {
/* 214:285 */     if (this.sucursal == null) {
/* 215:286 */       this.sucursal = AppUtil.getSucursal();
/* 216:    */     }
/* 217:288 */     return this.sucursal;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setSucursal(Sucursal sucursal)
/* 221:    */   {
/* 222:298 */     this.sucursal = sucursal;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<Sucursal> getListaSucursal()
/* 226:    */   {
/* 227:307 */     if (this.listaSucursal == null) {
/* 228:308 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 229:    */     }
/* 230:310 */     return this.listaSucursal;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 234:    */   {
/* 235:320 */     this.listaSucursal = listaSucursal;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Usuario getUsuario()
/* 239:    */   {
/* 240:324 */     if (this.usuario == null) {
/* 241:325 */       this.usuario = AppUtil.getUsuarioEnSesion();
/* 242:    */     }
/* 243:327 */     return this.usuario;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setUsuario(Usuario usuario)
/* 247:    */   {
/* 248:331 */     this.usuario = usuario;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public List<SelectItem> getListaExportOption()
/* 252:    */   {
/* 253:335 */     if (this.listaExportOption == null)
/* 254:    */     {
/* 255:336 */       this.listaExportOption = new ArrayList();
/* 256:337 */       this.listaExportOption.add(new SelectItem(ExportOption.PDF, ExportOption.PDF.getNombre()));
/* 257:    */     }
/* 258:339 */     return this.listaExportOption;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Empleado getEmpleadoSession()
/* 262:    */   {
/* 263:343 */     if ((this.empleado != null) && (this.empleado.getNombres() != null)) {
/* 264:344 */       return this.empleado;
/* 265:    */     }
/* 266:346 */     int idUsuario = AppUtil.getUsuarioEnSesion().getIdUsuario();
/* 267:    */     
/* 268:348 */     EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(idUsuario));
/* 269:349 */     this.empleadoSession = entidadUsuario.getEmpleado();
/* 270:350 */     this.empleado = this.empleadoSession;
/* 271:351 */     return this.empleadoSession;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isEmpleadoSimple()
/* 275:    */   {
/* 276:355 */     return this.empleadoSimple;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setEmpleadoSimple(boolean empleadoSimple)
/* 280:    */   {
/* 281:359 */     this.empleadoSimple = empleadoSimple;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setEmpleadoSession(Empleado empleadoSession)
/* 285:    */   {
/* 286:363 */     this.empleadoSession = empleadoSession;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public EntidadUsuario getEntidadUsuario()
/* 290:    */   {
/* 291:367 */     return this.entidadUsuario;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/* 295:    */   {
/* 296:371 */     this.entidadUsuario = entidadUsuario;
/* 297:    */   }
/* 298:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteImpresionRolBean
 * JD-Core Version:    0.7.0.1
 */