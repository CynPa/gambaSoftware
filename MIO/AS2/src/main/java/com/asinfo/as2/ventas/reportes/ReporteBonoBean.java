/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   9:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.Especialidad;
/*  12:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  15:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  18:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioEmisionBono;
/*  24:    */ import java.io.IOException;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Calendar;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.annotation.PostConstruct;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.faces.bean.ManagedBean;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import javax.faces.model.SelectItem;
/*  36:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  37:    */ import net.sf.jasperreports.engine.JRException;
/*  38:    */ import org.apache.log4j.Logger;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class ReporteBonoBean
/*  43:    */   extends AbstractBaseReportBean
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 1L;
/*  46:    */   @EJB
/*  47:    */   private ServicioEmpresa servicioEmpresa;
/*  48:    */   @EJB
/*  49:    */   private ServicioPersonaResponsable servicioResponsableSalidaMercaderia;
/*  50:    */   @EJB
/*  51:    */   private ServicioSucursal servicioSucursal;
/*  52:    */   @EJB
/*  53:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  54:    */   @EJB
/*  55:    */   private ServicioEmisionBono servicioBono;
/*  56:    */   @EJB
/*  57:    */   private ServicioEspecialidad servicioEspecialidad;
/*  58:    */   @EJB
/*  59:    */   private ServicioUsuario servicioUsuario;
/*  60:    */   private Especialidad tipoBono;
/*  61:    */   private PuntoDeVenta puntoVenta;
/*  62:    */   private Empresa empresa;
/*  63:    */   private Empresa empresaBono;
/*  64:    */   private Sucursal sucursal;
/*  65:    */   private PersonaResponsable medico;
/*  66:    */   private Date fechaDesde;
/*  67:    */   private Date fechaHasta;
/*  68: 86 */   private List<PuntoDeVenta> listaPuntoVenta = new ArrayList();
/*  69:    */   private List<PersonaResponsable> listaResponsableSalidaMercaderia;
/*  70:    */   private List<Sucursal> listaSucursalCombo;
/*  71:    */   private DetalleFacturaCliente bono;
/*  72:    */   private EntidadUsuario usuario;
/*  73:    */   
/*  74:    */   public static enum TipoReporteEnum
/*  75:    */   {
/*  76: 93 */     ANULADOS,  ELABORADOS,  TODOS;
/*  77:    */     
/*  78:    */     private TipoReporteEnum() {}
/*  79:    */   }
/*  80:    */   
/*  81: 96 */   private TipoReporteEnum tipoReporte = TipoReporteEnum.TODOS;
/*  82:    */   private List<SelectItem> listaTipoReporte;
/*  83:    */   
/*  84:    */   protected JRDataSource getJRDataSource()
/*  85:    */   {
/*  86:107 */     List listaDatosReporte = new ArrayList();
/*  87:108 */     JRDataSource ds = null;
/*  88:109 */     int valorReporte = 0;
/*  89:110 */     boolean anulado = false;
/*  90:112 */     switch (1.$SwitchMap$com$asinfo$as2$ventas$reportes$ReporteBonoBean$TipoReporteEnum[this.tipoReporte.ordinal()])
/*  91:    */     {
/*  92:    */     case 1: 
/*  93:114 */       valorReporte = 1;
/*  94:115 */       anulado = true;
/*  95:116 */       break;
/*  96:    */     case 2: 
/*  97:118 */       valorReporte = 2;
/*  98:    */     }
/*  99:123 */     listaDatosReporte = this.servicioBono.listaBonos(getBono() != null ? getBono().getFacturaCliente().getFecha() : getFechaDesde(), 
/* 100:124 */       getBono() != null ? getBono().getFacturaCliente().getFecha() : getFechaHasta(), getEmpresa(), getEmpresaBono(), getSucursal(), 
/* 101:125 */       getPuntoVenta(), getTipoBono(), getMedico(), AppUtil.getOrganizacion().getIdOrganizacion(), getBono() != null ? getBono()
/* 102:126 */       .getFacturaCliente() : null, getUsuario(), valorReporte, anulado);
/* 103:127 */     String[] fields = { "f_fecha", "f_cliente", "f_clienteBono", "f_tipoBono", "f_sucursal", "f_precio", "f_total", "f_numeroBono", "f_numeroFactura", "f_doctor", "f_totalBono", "f_estado" };
/* 104:    */     
/* 105:129 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 106:    */     
/* 107:131 */     return ds;
/* 108:    */   }
/* 109:    */   
/* 110:    */   @PostConstruct
/* 111:    */   public void init()
/* 112:    */   {
/* 113:136 */     Calendar calfechaDesde = Calendar.getInstance();
/* 114:137 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 115:138 */     this.fechaDesde = calfechaDesde.getTime();
/* 116:139 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<PersonaResponsable> autocompletarMedico(String consulta)
/* 120:    */   {
/* 121:143 */     return this.servicioResponsableSalidaMercaderia.autocompletarMedico(consulta, AppUtil.getOrganizacion().getIdOrganizacion());
/* 122:    */   }
/* 123:    */   
/* 124:    */   public List<Especialidad> autocompletarTipoBono(String consulta)
/* 125:    */   {
/* 126:147 */     return this.servicioEspecialidad.autocompletarEspecialidad(consulta, AppUtil.getOrganizacion().getIdOrganizacion());
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected String getCompileFileName()
/* 130:    */   {
/* 131:157 */     if ((getBono() != null) && (getBono().getFacturaCliente() != null)) {
/* 132:158 */       return "impresionBono";
/* 133:    */     }
/* 134:160 */     return "reporteEmisionBono";
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected Map<String, Object> getReportParameters()
/* 138:    */   {
/* 139:170 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 140:171 */     reportParameters.put("p_fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 141:172 */     reportParameters.put("p_fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 142:173 */     reportParameters.put("p_cliente", this.empresa != null ? this.empresa.getNombreFiscal() : "Todos");
/* 143:174 */     reportParameters.put("p_clienteBono", this.empresaBono != null ? this.empresaBono.getNombreFiscal() : "Todos");
/* 144:175 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 145:176 */     reportParameters.put("p_puntoVenta", this.puntoVenta != null ? this.puntoVenta.getNombre() : "Todos");
/* 146:177 */     reportParameters.put("p_tipoBono", this.tipoBono != null ? this.tipoBono.getNombre() : "Todos");
/* 147:178 */     reportParameters.put("p_medico", this.medico != null ? this.medico.getApellidos() + " " + this.medico.getNombres() : "Todos");
/* 148:179 */     reportParameters.put("p_usuarioBono", this.usuario != null ? this.usuario.getNombreUsuario() : "Todos");
/* 149:180 */     return reportParameters;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void cargarPuntoVenta()
/* 153:    */   {
/* 154:184 */     if (this.sucursal != null)
/* 155:    */     {
/* 156:185 */       Map<String, String> filters = new HashMap();
/* 157:186 */       filters.put("sucursal.idSucursal", String.valueOf(this.sucursal.getId()));
/* 158:187 */       this.listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("nombre", true, filters);
/* 159:    */     }
/* 160:    */     else
/* 161:    */     {
/* 162:189 */       this.listaPuntoVenta = new ArrayList();
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String execute()
/* 167:    */   {
/* 168:    */     try
/* 169:    */     {
/* 170:200 */       super.prepareReport();
/* 171:    */     }
/* 172:    */     catch (JRException e)
/* 173:    */     {
/* 174:202 */       LOG.info("Error JRException");
/* 175:203 */       e.printStackTrace();
/* 176:204 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 177:    */     }
/* 178:    */     catch (IOException e)
/* 179:    */     {
/* 180:206 */       LOG.info("Error IOException");
/* 181:207 */       e.printStackTrace();
/* 182:208 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 183:    */     }
/* 184:211 */     return null;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 188:    */   {
/* 189:221 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<Empresa> autocompletarClientesBono(String consulta)
/* 193:    */   {
/* 194:225 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<EntidadUsuario> autocompletarUsuario(String consulta)
/* 198:    */   {
/* 199:229 */     return this.servicioUsuario.autocompletarUsuarios(consulta);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Date getFechaDesde()
/* 203:    */   {
/* 204:249 */     return this.fechaDesde;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setFechaDesde(Date fechaDesde)
/* 208:    */   {
/* 209:259 */     this.fechaDesde = fechaDesde;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Date getFechaHasta()
/* 213:    */   {
/* 214:268 */     return this.fechaHasta;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setFechaHasta(Date fechaHasta)
/* 218:    */   {
/* 219:278 */     this.fechaHasta = fechaHasta;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Empresa getEmpresa()
/* 223:    */   {
/* 224:287 */     return this.empresa;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setEmpresa(Empresa empresa)
/* 228:    */   {
/* 229:297 */     this.empresa = empresa;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public PersonaResponsable getMedico()
/* 233:    */   {
/* 234:301 */     return this.medico;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setMedico(PersonaResponsable medico)
/* 238:    */   {
/* 239:305 */     this.medico = medico;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public List<PersonaResponsable> getListaResponsableSalidaMercaderia()
/* 243:    */   {
/* 244:314 */     if (this.listaResponsableSalidaMercaderia == null)
/* 245:    */     {
/* 246:315 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 247:316 */       filters.put("indicadorSalidaMercaderia", "true");
/* 248:317 */       this.listaResponsableSalidaMercaderia = this.servicioResponsableSalidaMercaderia.obtenerListaCombo("apellidos", true, filters);
/* 249:    */     }
/* 250:319 */     return this.listaResponsableSalidaMercaderia;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setListaResponsableSalidaMercaderia(List<PersonaResponsable> listaResponsableSalidaMercaderia)
/* 254:    */   {
/* 255:329 */     this.listaResponsableSalidaMercaderia = listaResponsableSalidaMercaderia;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Empresa getEmpresaBono()
/* 259:    */   {
/* 260:333 */     return this.empresaBono;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setEmpresaBono(Empresa empresaBono)
/* 264:    */   {
/* 265:337 */     this.empresaBono = empresaBono;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Sucursal getSucursal()
/* 269:    */   {
/* 270:341 */     return this.sucursal;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setSucursal(Sucursal sucursal)
/* 274:    */   {
/* 275:345 */     this.sucursal = sucursal;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public List<Sucursal> getListaSucursalCombo()
/* 279:    */   {
/* 280:349 */     HashMap<String, String> filters = new HashMap();
/* 281:350 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 282:351 */     if (this.listaSucursalCombo == null) {
/* 283:352 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 284:    */     }
/* 285:354 */     return this.listaSucursalCombo;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 289:    */   {
/* 290:358 */     this.listaSucursalCombo = listaSucursalCombo;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public List<PuntoDeVenta> getListaPuntoVenta()
/* 294:    */   {
/* 295:362 */     return this.listaPuntoVenta;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setListaPuntoVenta(List<PuntoDeVenta> listaPuntoVenta)
/* 299:    */   {
/* 300:366 */     this.listaPuntoVenta = listaPuntoVenta;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public PuntoDeVenta getPuntoVenta()
/* 304:    */   {
/* 305:370 */     return this.puntoVenta;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setPuntoVenta(PuntoDeVenta puntoVenta)
/* 309:    */   {
/* 310:374 */     this.puntoVenta = puntoVenta;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public Especialidad getTipoBono()
/* 314:    */   {
/* 315:378 */     return this.tipoBono;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setTipoBono(Especialidad tipoBono)
/* 319:    */   {
/* 320:382 */     this.tipoBono = tipoBono;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public DetalleFacturaCliente getBono()
/* 324:    */   {
/* 325:386 */     return this.bono;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setBono(DetalleFacturaCliente bono)
/* 329:    */   {
/* 330:390 */     this.bono = bono;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public EntidadUsuario getUsuario()
/* 334:    */   {
/* 335:394 */     return this.usuario;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setUsuario(EntidadUsuario usuario)
/* 339:    */   {
/* 340:398 */     this.usuario = usuario;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public TipoReporteEnum getTipoReporte()
/* 344:    */   {
/* 345:402 */     return this.tipoReporte;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setTipoReporte(TipoReporteEnum tipoReporte)
/* 349:    */   {
/* 350:406 */     this.tipoReporte = tipoReporte;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public List<SelectItem> getListaTipoReporte()
/* 354:    */   {
/* 355:410 */     if (this.listaTipoReporte == null)
/* 356:    */     {
/* 357:411 */       this.listaTipoReporte = new ArrayList();
/* 358:412 */       for (TipoReporteEnum tr : TipoReporteEnum.values()) {
/* 359:413 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 360:    */       }
/* 361:    */     }
/* 362:417 */     return this.listaTipoReporte;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 366:    */   {
/* 367:421 */     this.listaTipoReporte = listaTipoReporte;
/* 368:    */   }
/* 369:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteBonoBean
 * JD-Core Version:    0.7.0.1
 */