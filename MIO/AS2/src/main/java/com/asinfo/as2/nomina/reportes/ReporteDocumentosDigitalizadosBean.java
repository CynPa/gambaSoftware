/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*   7:    */ import com.asinfo.as2.entities.Departamento;
/*   8:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*   9:    */ import com.asinfo.as2.entities.Empleado;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  13:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCategoriaDocumentoDigitalizado;
/*  14:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado;
/*  15:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteDocumentosDigitalizadosBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioReporteNomina servicioReporteNomina;
/*  39:    */   @EJB
/*  40:    */   private ServicioSucursal servicioSucursal;
/*  41:    */   @EJB
/*  42:    */   private ServicioDepartamento servicioDepartamento;
/*  43:    */   @EJB
/*  44:    */   private ServicioCategoriaDocumentoDigitalizado servicioCategoriaDocumentoDigitalizado;
/*  45:    */   @EJB
/*  46:    */   private ServicioDocumentoDigitalizado servicioDocumentoDigitalizado;
/*  47:    */   @EJB
/*  48:    */   private ServicioDetalleDocumentoDigitalizado servicioDetalleDocumentoDigitalizado;
/*  49:    */   private Empleado empleado;
/*  50:    */   private Sucursal sucursal;
/*  51:    */   private Departamento departamento;
/*  52: 56 */   private List<Sucursal> listaSucursal = new ArrayList();
/*  53:    */   private List<Departamento> listaDepartamento;
/*  54: 58 */   private final String COMPILE_FILE_NAME = "reporteDocumentosDigitalizados";
/*  55:    */   private String tipoSelecionado;
/*  56:    */   private List<SelectItem> listaCategoriaDocumento;
/*  57:    */   private List<SelectItem> listaDocumentoDigitalizado;
/*  58:    */   private FormaPagoEmpleado formaPagoEmpleado;
/*  59:    */   private int idCategoriaSeleccionada;
/*  60:    */   private int idDocumentoDigitalizado;
/*  61:    */   private int tipoReporte;
/*  62:    */   private List<SelectItem> listaTipoReporte;
/*  63: 69 */   private boolean noCargados = true;
/*  64: 70 */   private boolean cargados = true;
/*  65: 71 */   private Date fechaVencer = null;
/*  66:    */   
/*  67:    */   protected JRDataSource getJRDataSource()
/*  68:    */   {
/*  69: 81 */     List listaDatosReporte = new ArrayList();
/*  70: 82 */     JRDataSource ds = null;
/*  71: 83 */     String[] fields = null;
/*  72:    */     try
/*  73:    */     {
/*  74: 85 */       int idEmpleado = this.empleado == null ? 0 : this.empleado.getId();
/*  75: 86 */       int idDepartamento = this.departamento == null ? 0 : this.departamento.getId();
/*  76: 87 */       listaDatosReporte = this.servicioDetalleDocumentoDigitalizado.reporteDocumentosDigitalizados(AppUtil.getOrganizacion().getId(), idEmpleado, idDepartamento, this.idDocumentoDigitalizado, this.cargados, this.noCargados, this.fechaVencer);
/*  77:    */       
/*  78: 89 */       fields = new String[] { "f_categoria", "f_documento", "f_requerido", "f_eNombres", "f_eIdentificacion", "f_departamento", "f_cargado", "f_desde", "f_hasta" };
/*  79:    */       
/*  80: 91 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84: 94 */       LOG.info("Error " + e);
/*  85: 95 */       e.printStackTrace();
/*  86:    */     }
/*  87: 97 */     return ds;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected String getCompileFileName()
/*  91:    */   {
/*  92:107 */     return "reporteDocumentosDigitalizados";
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected Map<String, Object> getReportParameters()
/*  96:    */   {
/*  97:113 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  98:114 */     reportParameters.put("ReportTitle", "Reporte de Documentos Digitalizados");
/*  99:    */     
/* 100:116 */     return reportParameters;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String execute()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:126 */       super.prepareReport();
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:128 */       e.printStackTrace();
/* 112:    */     }
/* 113:131 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String cargarEmpleado()
/* 117:    */   {
/* 118:135 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Empleado getEmpleado()
/* 122:    */   {
/* 123:144 */     if (this.empleado == null) {
/* 124:145 */       this.empleado = new Empleado();
/* 125:    */     }
/* 126:147 */     return this.empleado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setEmpleado(Empleado empleado)
/* 130:    */   {
/* 131:157 */     this.empleado = empleado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getCOMPILE_FILE_NAME()
/* 135:    */   {
/* 136:166 */     return "reporteDocumentosDigitalizados";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<Sucursal> getListaSucursal()
/* 140:    */   {
/* 141:175 */     if (this.listaSucursal == null) {
/* 142:176 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 143:    */     }
/* 144:178 */     return this.listaSucursal;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Sucursal getSucursal()
/* 148:    */   {
/* 149:187 */     return this.sucursal;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setSucursal(Sucursal sucursal)
/* 153:    */   {
/* 154:197 */     this.sucursal = sucursal;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Departamento getDepartamento()
/* 158:    */   {
/* 159:206 */     if (this.departamento == null) {
/* 160:207 */       this.departamento = new Departamento();
/* 161:    */     }
/* 162:209 */     return this.departamento;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDepartamento(Departamento departamento)
/* 166:    */   {
/* 167:219 */     this.departamento = departamento;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<Departamento> getListaDepartamento()
/* 171:    */   {
/* 172:228 */     if (this.listaDepartamento == null) {
/* 173:229 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 174:    */     }
/* 175:231 */     return this.listaDepartamento;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getTipoSelecionado()
/* 179:    */   {
/* 180:240 */     return this.tipoSelecionado;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setTipoSelecionado(String tipoSelecionado)
/* 184:    */   {
/* 185:250 */     this.tipoSelecionado = tipoSelecionado;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<SelectItem> getListaCategoriaDocumento()
/* 189:    */   {
/* 190:259 */     if (this.listaCategoriaDocumento == null)
/* 191:    */     {
/* 192:260 */       this.listaCategoriaDocumento = new ArrayList();
/* 193:261 */       Map<String, String> filters = new HashMap();
/* 194:262 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 195:263 */       List<CategoriaDocumentoDigitalizado> lista = this.servicioCategoriaDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 196:264 */       for (CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado : lista) {
/* 197:265 */         this.listaCategoriaDocumento.add(new SelectItem(Integer.valueOf(categoriaDocumentoDigitalizado.getId()), categoriaDocumentoDigitalizado.getNombre()));
/* 198:    */       }
/* 199:    */     }
/* 200:268 */     return this.listaCategoriaDocumento;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public FormaPagoEmpleado getFormaPagoEmpleado()
/* 204:    */   {
/* 205:277 */     return this.formaPagoEmpleado;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/* 209:    */   {
/* 210:287 */     this.formaPagoEmpleado = formaPagoEmpleado;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public int getIdCategoriaSeleccionada()
/* 214:    */   {
/* 215:291 */     return this.idCategoriaSeleccionada;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setIdCategoriaSeleccionada(int idCategoriaSeleccionada)
/* 219:    */   {
/* 220:295 */     Map<String, String> filters = new HashMap();
/* 221:296 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 222:297 */     filters.put("categoriaDocumentoDigitalizado.idCategoriaDocumentoDigitalizado", Integer.toString(idCategoriaSeleccionada));
/* 223:298 */     List<DocumentoDigitalizado> listaDocumentos = this.servicioDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 224:299 */     this.listaDocumentoDigitalizado = new ArrayList();
/* 225:300 */     for (DocumentoDigitalizado documentoDigitalizado : listaDocumentos) {
/* 226:301 */       this.listaDocumentoDigitalizado.add(new SelectItem(Integer.valueOf(documentoDigitalizado.getId()), documentoDigitalizado.getNombre()));
/* 227:    */     }
/* 228:303 */     this.idCategoriaSeleccionada = idCategoriaSeleccionada;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int getIdDocumentoDigitalizado()
/* 232:    */   {
/* 233:307 */     return this.idDocumentoDigitalizado;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIdDocumentoDigitalizado(int idDocumentoDigitalizado)
/* 237:    */   {
/* 238:311 */     this.idDocumentoDigitalizado = idDocumentoDigitalizado;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public List<SelectItem> getListaDocumentoDigitalizado()
/* 242:    */   {
/* 243:315 */     Map<String, String> filters = new HashMap();
/* 244:316 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 245:317 */     filters.put("activo", "true");
/* 246:318 */     filters.put("categoriaDocumentoDigitalizado.activo", "true");
/* 247:319 */     List<DocumentoDigitalizado> listaDocumentos = this.servicioDocumentoDigitalizado.obtenerListaCombo("nombre", true, filters);
/* 248:320 */     this.listaDocumentoDigitalizado = new ArrayList();
/* 249:321 */     for (DocumentoDigitalizado documentoDigitalizado : listaDocumentos) {
/* 250:322 */       this.listaDocumentoDigitalizado.add(new SelectItem(Integer.valueOf(documentoDigitalizado.getId()), documentoDigitalizado.getCategoriaDocumentoDigitalizado().getNombre() + "/" + documentoDigitalizado.getNombre()));
/* 251:    */     }
/* 252:324 */     return this.listaDocumentoDigitalizado;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setListaDocumentoDigitalizado(List<SelectItem> listaDocumentoDigitalizado)
/* 256:    */   {
/* 257:329 */     this.listaDocumentoDigitalizado = listaDocumentoDigitalizado;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public int getTipoReporte()
/* 261:    */   {
/* 262:333 */     return this.tipoReporte;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setTipoReporte(int tipoReporte)
/* 266:    */   {
/* 267:337 */     this.tipoReporte = tipoReporte;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public List<SelectItem> getListaTipoReporte()
/* 271:    */   {
/* 272:341 */     if (this.listaTipoReporte == null)
/* 273:    */     {
/* 274:342 */       this.listaTipoReporte = new ArrayList();
/* 275:343 */       this.listaTipoReporte.add(new SelectItem(Integer.valueOf(1), getLanguageController()
/* 276:344 */         .getMensaje("msg_combo_reporte_por_documento")));
/* 277:345 */       this.listaTipoReporte.add(new SelectItem(Integer.valueOf(2), getLanguageController()
/* 278:346 */         .getMensaje("msg_combo_reporte_por_empleado")));
/* 279:347 */       this.listaTipoReporte.add(new SelectItem(Integer.valueOf(3), getLanguageController()
/* 280:348 */         .getMensaje("msg_combo_reporte_por_departamento")));
/* 281:    */     }
/* 282:350 */     return this.listaTipoReporte;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public boolean isNoCargados()
/* 286:    */   {
/* 287:354 */     return this.noCargados;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setNoCargados(boolean noCargados)
/* 291:    */   {
/* 292:358 */     this.noCargados = noCargados;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean isCargados()
/* 296:    */   {
/* 297:362 */     return this.cargados;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setCargados(boolean cargados)
/* 301:    */   {
/* 302:366 */     this.cargados = cargados;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public Date getFechaVencer()
/* 306:    */   {
/* 307:370 */     return this.fechaVencer;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setFechaVencer(Date fechaVencer)
/* 311:    */   {
/* 312:374 */     this.fechaVencer = fechaVencer;
/* 313:    */   }
/* 314:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteDocumentosDigitalizadosBean
 * JD-Core Version:    0.7.0.1
 */