/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   6:    */ import com.asinfo.as2.entities.Canal;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   9:    */ import com.asinfo.as2.entities.HojaRuta;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.Transportista;
/*  14:    */ import com.asinfo.as2.entities.Zona;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  20:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  24:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  25:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  26:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*  27:    */ import java.io.IOException;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Calendar;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.List;
/*  33:    */ import javax.annotation.PostConstruct;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import javax.faces.context.ExternalContext;
/*  38:    */ import javax.faces.context.FacesContext;
/*  39:    */ import javax.servlet.http.HttpServletRequest;
/*  40:    */ import javax.servlet.http.HttpServletResponse;
/*  41:    */ import javax.servlet.http.HttpSession;
/*  42:    */ import javax.validation.constraints.NotNull;
/*  43:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  44:    */ import net.sf.jasperreports.engine.JRException;
/*  45:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  46:    */ import org.apache.log4j.Logger;
/*  47:    */ 
/*  48:    */ @ManagedBean
/*  49:    */ @ViewScoped
/*  50:    */ public class FacturacionEnLoteBean
/*  51:    */   extends ReporteFacturaClienteBean
/*  52:    */ {
/*  53:    */   private static final long serialVersionUID = 1L;
/*  54:    */   @EJB
/*  55:    */   private ServicioZona servicioZona;
/*  56:    */   @EJB
/*  57:    */   private ServicioCanal servicioCanal;
/*  58:    */   @EJB
/*  59:    */   private ServicioTransportista servicioTransportista;
/*  60:    */   @EJB
/*  61:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  62:    */   @EJB
/*  63:    */   protected ServicioHojaRuta servicioHojaRuta;
/*  64:    */   @EJB
/*  65:    */   protected ServicioSucursal servicioSucursal;
/*  66:    */   @EJB
/*  67:    */   private ServicioGenerico<HojaRuta> servicioHRuta;
/*  68:    */   @EJB
/*  69:    */   private FacturaClienteDao facturaClienteDao;
/*  70: 89 */   private Date fechaDesde = Calendar.getInstance().getTime();
/*  71: 90 */   private Date fechaHasta = Calendar.getInstance().getTime();
/*  72:    */   private Canal canal;
/*  73:    */   private List<Canal> listaCanalCombo;
/*  74:    */   private Zona zona;
/*  75:    */   private List<Zona> listaZonaCombo;
/*  76:    */   private Transportista transportista;
/*  77:    */   private List<Transportista> listaTransportista;
/*  78:    */   private List<FacturaCliente> listaFacturas;
/*  79:    */   private HojaRuta hojaRuta;
/*  80:    */   private List<HojaRuta> listaHojaRuta;
/*  81:    */   @NotNull
/*  82:    */   private Sucursal sucursal;
/*  83:    */   private List<Sucursal> listaSucursal;
/*  84:106 */   private boolean reporteDetallado = false;
/*  85:    */   
/*  86:    */   @PostConstruct
/*  87:    */   public void init()
/*  88:    */   {
/*  89:110 */     this.sucursal = AppUtil.getSucursal();
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected JRDataSource getJRDataSource()
/*  93:    */   {
/*  94:115 */     return null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String execute()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:123 */       this.listaFacturas = this.servicioFacturaCliente.listaFacturas(AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaDesde, this.fechaHasta, this.transportista, this.zona, this.canal, this.hojaRuta);
/* 102:    */       
/* 103:    */ 
/* 104:126 */       List<JasperPrint> listaJasperPrint = new ArrayList();
/* 105:128 */       for (FacturaCliente fc : this.listaFacturas)
/* 106:    */       {
/* 107:130 */         setFacturaCliente(this.servicioFacturaCliente.cargarCabecera(Integer.valueOf(fc.getId())));
/* 108:131 */         setDocumentoElectronico(getFacturaCliente().getDocumento().isIndicadorDocumentoElectronico());
/* 109:    */         try
/* 110:    */         {
/* 111:134 */           List<Object[]> listaDatosReporte = this.servicioFacturaCliente.getReporteFacturaCliente(fc.getIdFacturaCliente(), 
/* 112:135 */             AppUtil.getOrganizacion().getIdOrganizacion());
/* 113:136 */           JRDataSource ds = new QueryResultDataSource(listaDatosReporte, ReporteFacturaClienteBean.fields);
/* 114:137 */           listaJasperPrint.add(super.prepareReport(ds));
/* 115:    */         }
/* 116:    */         catch (ExcepcionAS2 e)
/* 117:    */         {
/* 118:139 */           e.printStackTrace();
/* 119:    */         }
/* 120:    */       }
/* 121:143 */       if (!listaJasperPrint.isEmpty())
/* 122:    */       {
/* 123:144 */         ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
/* 124:145 */         HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
/* 125:146 */         HttpServletResponse response = (HttpServletResponse)externalContext.getResponse();
/* 126:    */         
/* 127:148 */         super.setearJasperSession(request);
/* 128:149 */         request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print_list", listaJasperPrint);
/* 129:    */         
/* 130:151 */         response.sendRedirect(request.getContextPath() + "/servlets/report/" + getExportOption());
/* 131:152 */         FacesContext.getCurrentInstance().responseComplete();
/* 132:    */       }
/* 133:    */     }
/* 134:    */     catch (JRException e)
/* 135:    */     {
/* 136:155 */       LOG.info("Error JRException");
/* 137:156 */       e.printStackTrace();
/* 138:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 139:    */     }
/* 140:    */     catch (IOException e)
/* 141:    */     {
/* 142:159 */       LOG.info("Error IOException");
/* 143:160 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 144:    */     }
/* 145:    */     catch (Exception e)
/* 146:    */     {
/* 147:163 */       e.printStackTrace();
/* 148:    */     }
/* 149:166 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getCompileFileName()
/* 153:    */   {
/* 154:171 */     if (!this.reporteDetallado) {
/* 155:172 */       return super.getCompileFileName();
/* 156:    */     }
/* 157:174 */     return "reporteFacturaClienteDetallado";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFechaDesde()
/* 161:    */   {
/* 162:179 */     return this.fechaDesde;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFechaDesde(Date fechaDesde)
/* 166:    */   {
/* 167:183 */     this.fechaDesde = fechaDesde;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Date getFechaHasta()
/* 171:    */   {
/* 172:187 */     return this.fechaHasta;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setFechaHasta(Date fechaHasta)
/* 176:    */   {
/* 177:191 */     this.fechaHasta = fechaHasta;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Canal getCanal()
/* 181:    */   {
/* 182:195 */     return this.canal;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setCanal(Canal canal)
/* 186:    */   {
/* 187:199 */     this.canal = canal;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<Canal> getListaCanalCombo()
/* 191:    */   {
/* 192:203 */     if (this.listaCanalCombo == null) {
/* 193:204 */       this.listaCanalCombo = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 194:    */     }
/* 195:206 */     return this.listaCanalCombo;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setListaCanalCombo(List<Canal> listaCanalCombo)
/* 199:    */   {
/* 200:210 */     this.listaCanalCombo = listaCanalCombo;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Zona getZona()
/* 204:    */   {
/* 205:214 */     return this.zona;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setZona(Zona zona)
/* 209:    */   {
/* 210:218 */     this.zona = zona;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<Zona> getListaZonaCombo()
/* 214:    */   {
/* 215:222 */     if (this.listaZonaCombo == null) {
/* 216:223 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 217:    */     }
/* 218:225 */     return this.listaZonaCombo;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setListaZonaCombo(List<Zona> listaZonaCombo)
/* 222:    */   {
/* 223:229 */     this.listaZonaCombo = listaZonaCombo;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Transportista getTransportista()
/* 227:    */   {
/* 228:233 */     return this.transportista;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setTransportista(Transportista transportista)
/* 232:    */   {
/* 233:237 */     this.transportista = transportista;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<Transportista> getListaTransportista()
/* 237:    */   {
/* 238:241 */     HashMap<String, String> filters = new HashMap();
/* 239:242 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 240:243 */     if (this.listaTransportista == null) {
/* 241:244 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 242:    */     }
/* 243:247 */     return this.listaTransportista;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setListaTransportista(List<Transportista> listaTransportista)
/* 247:    */   {
/* 248:251 */     this.listaTransportista = listaTransportista;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public List<FacturaCliente> getListaFacturas()
/* 252:    */   {
/* 253:255 */     if (this.listaFacturas == null) {
/* 254:256 */       this.listaFacturas = new ArrayList();
/* 255:    */     }
/* 256:258 */     return this.listaFacturas;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setListaFacturas(List<FacturaCliente> listaFacturas)
/* 260:    */   {
/* 261:262 */     this.listaFacturas = listaFacturas;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<HojaRuta> getListaHojaRuta()
/* 265:    */   {
/* 266:266 */     HashMap<String, String> filters = new HashMap();
/* 267:267 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 268:270 */     if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/* 269:271 */       filters.put("sucursal.idSucursal", String.valueOf(AppUtil.getSucursal().getIdSucursal()));
/* 270:    */     }
/* 271:273 */     filters.put("indicadorHojaRutaTransportista", "true");
/* 272:274 */     filters.put("estado", Estado.ELABORADO.toString());
/* 273:275 */     this.listaHojaRuta = this.servicioHRuta.obtenerListaCombo(HojaRuta.class, "numero", true, filters);
/* 274:    */     
/* 275:277 */     return this.listaHojaRuta;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setListaHojaRuta(List<HojaRuta> listaHojaRuta)
/* 279:    */   {
/* 280:281 */     this.listaHojaRuta = listaHojaRuta;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public HojaRuta getHojaRuta()
/* 284:    */   {
/* 285:285 */     return this.hojaRuta;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setHojaRuta(HojaRuta hojaRuta)
/* 289:    */   {
/* 290:289 */     this.hojaRuta = hojaRuta;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void facturarListener()
/* 294:    */   {
/* 295:    */     try
/* 296:    */     {
/* 297:294 */       this.servicioFacturaCliente.facturaEnLote(AppUtil.getOrganizacion().getId(), this.sucursal, this.hojaRuta, this.transportista, this.fechaDesde, this.fechaHasta, 
/* 298:295 */         AppUtil.getPuntoDeVenta());
/* 299:296 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 300:    */     }
/* 301:    */     catch (ExcepcionAS2 e)
/* 302:    */     {
/* 303:298 */       e.printStackTrace();
/* 304:299 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 305:    */     }
/* 306:    */     catch (AS2Exception e)
/* 307:    */     {
/* 308:301 */       e.printStackTrace();
/* 309:302 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public Sucursal getSucursal()
/* 314:    */   {
/* 315:307 */     return this.sucursal;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setSucursal(Sucursal sucursal)
/* 319:    */   {
/* 320:311 */     this.sucursal = sucursal;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<Sucursal> getListaSucursal()
/* 324:    */   {
/* 325:315 */     if (this.listaSucursal == null) {
/* 326:316 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 327:    */     }
/* 328:319 */     return this.listaSucursal;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 332:    */   {
/* 333:323 */     this.listaSucursal = listaSucursal;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean isReporteDetallado()
/* 337:    */   {
/* 338:327 */     return this.reporteDetallado;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setReporteDetallado(boolean reporteDetallado)
/* 342:    */   {
/* 343:331 */     this.reporteDetallado = reporteDetallado;
/* 344:    */   }
/* 345:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.FacturacionEnLoteBean
 * JD-Core Version:    0.7.0.1
 */