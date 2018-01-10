/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   7:    */ import com.asinfo.as2.entities.Cliente;
/*   8:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   9:    */ import com.asinfo.as2.entities.DetalleHojaRuta;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  12:    */ import com.asinfo.as2.entities.HojaRuta;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Subempresa;
/*  15:    */ import com.asinfo.as2.entities.Transportista;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*  18:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  22:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  23:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.context.ExternalContext;
/*  32:    */ import javax.faces.context.FacesContext;
/*  33:    */ import javax.servlet.http.HttpSession;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class HojaRutaTransportistaBean
/*  39:    */   extends HojaRutaBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioTransportista servicioTransportista;
/*  44:    */   @EJB
/*  45:    */   private ServicioDespachoCliente servicioDespachoCliente;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioEmpresa servicioEmpresa;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioListaPrecios servicioListaPrecios;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioDocumento servicioDocumento;
/*  56:    */   private Empresa empresaSeleccionanda;
/*  57:    */   private Subempresa subempresaSeleccionada;
/*  58:    */   private Transportista transportista;
/*  59:    */   private List<Transportista> listaTransportista;
/*  60:    */   private List<Transportista> listaTransportistaOtrosDespachos;
/*  61:    */   private List<DetalleHojaRuta> listaDetalleHojaRutaOtrosDespachos;
/*  62:    */   private List<Subempresa> listaSubempresa;
/*  63:    */   private List<DetalleHojaRuta> listaDetalleHojaRutaFilters;
/*  64: 84 */   private HashMap<String, DespachoCliente> hmDespachos = new HashMap();
/*  65:    */   private DataTable dtDetalleHojaRutaOtrosDespachos;
/*  66:    */   private boolean aprobarTodos;
/*  67:    */   private List<DetalleHojaRuta> listaDetalleFilter;
/*  68:    */   private List<DetalleHojaRuta> listaDetalleFilterDialogo;
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72: 93 */     if (getHojaRuta().getEstado().equals(Estado.FACTURADO)) {
/*  73: 94 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  74:    */     } else {
/*  75: 96 */       super.editar();
/*  76:    */     }
/*  77: 99 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void cargarInformacion()
/*  81:    */   {
/*  82:104 */     this.dtDetalleHojaRuta.reset();
/*  83:105 */     this.listaDetalleFilter = null;
/*  84:    */     
/*  85:107 */     getHojaRuta().getListaDetalleHojaRuta().clear();
/*  86:108 */     getHmDespachos().clear();
/*  87:    */     
/*  88:110 */     List<DetalleHojaRuta> listaDatosDetalleHojaRuta = this.servicioHojaRuta.detalleHojaRutaTransportista(AppUtil.getOrganizacion().getId(), 
/*  89:111 */       getSucursal(), getHojaRuta().getTransportista(), getFechaDesde(), null, getFechaHasta());
/*  90:112 */     for (DetalleHojaRuta dhr : listaDatosDetalleHojaRuta)
/*  91:    */     {
/*  92:113 */       getHojaRuta().getListaDetalleHojaRuta().add(dhr);
/*  93:114 */       this.hmDespachos.put(dhr.getDespachoCliente().getNumero(), dhr.getDespachoCliente());
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void cargarInformacionOtrosDespachos()
/*  98:    */   {
/*  99:122 */     getListaDetalleHojaRutaOtrosDespachos().clear();
/* 100:    */     
/* 101:124 */     List<DetalleHojaRuta> listaDatosDetalleHojaRuta = this.servicioHojaRuta.detalleHojaRutaTransportista(AppUtil.getOrganizacion().getId(), 
/* 102:125 */       getSucursal(), getTransportista(), getFechaDesde(), getEmpresaSeleccionanda(), getFechaHasta());
/* 103:126 */     for (DetalleHojaRuta dhr : listaDatosDetalleHojaRuta) {
/* 104:127 */       if (getHmDespachos().get(dhr.getDespachoCliente().getNumero()) == null) {
/* 105:128 */         getListaDetalleHojaRutaOtrosDespachos().add(dhr);
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<DetalleHojaRuta> getListaOtrosDespachos()
/* 111:    */   {
/* 112:136 */     List<DetalleHojaRuta> detalle = new ArrayList();
/* 113:137 */     if (getListaDetalleHojaRutaOtrosDespachos() == null) {
/* 114:138 */       setListaDetalleHojaRutaOtrosDespachos(new ArrayList());
/* 115:    */     }
/* 116:140 */     for (DetalleHojaRuta detalleHojaRuta : getListaDetalleHojaRutaOtrosDespachos()) {
/* 117:142 */       if (!detalleHojaRuta.isEliminado()) {
/* 118:143 */         detalle.add(detalleHojaRuta);
/* 119:    */       }
/* 120:    */     }
/* 121:147 */     return detalle;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void crearHojaRuta()
/* 125:    */   {
/* 126:151 */     super.crearHojaRuta();
/* 127:152 */     getHojaRuta().setIndicadorHojaRutaTransportista(Boolean.valueOf(true));
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void agregarFiltros(Map<String, String> filters)
/* 131:    */   {
/* 132:156 */     filters.put("indicadorHojaRutaTransportista", "true");
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Transportista getTransportista()
/* 136:    */   {
/* 137:160 */     return this.transportista;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String cancelar()
/* 141:    */   {
/* 142:164 */     this.listaDetalleHojaRutaFilters = null;
/* 143:165 */     return "/paginas/ventas/procesos/hojaRutaTransportista.xhtml?faces-redirect=true";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setTransportista(Transportista transportista)
/* 147:    */   {
/* 148:169 */     this.transportista = transportista;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public List<Transportista> getListaTransportista()
/* 152:    */   {
/* 153:173 */     HashMap<String, String> filters = new HashMap();
/* 154:174 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 155:175 */     if (this.listaTransportista == null) {
/* 156:176 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 157:    */     }
/* 158:179 */     return this.listaTransportista;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setListaTransportista(List<Transportista> listaTransportista)
/* 162:    */   {
/* 163:183 */     this.listaTransportista = listaTransportista;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void cargarTransportistasOtrosDespachos()
/* 167:    */   {
/* 168:187 */     getListaTransportistaOtrosDespachos().clear();
/* 169:188 */     for (Transportista t : getListaTransportista()) {
/* 170:189 */       if (!t.getCodigo().equals(getHojaRuta().getTransportista().getCodigo())) {
/* 171:190 */         getListaTransportistaOtrosDespachos().add(t);
/* 172:    */       }
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 177:    */   {
/* 178:196 */     HashMap<String, String> filters = new HashMap();
/* 179:197 */     filters.put("tipoCliente", "!=" + TipoEmpresaEnum.SUBCLIENTE.toString());
/* 180:198 */     return this.servicioEmpresa.autocompletarClientesTransportista(consulta, filters);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void agregarOtrosDespachos()
/* 184:    */   {
/* 185:210 */     for (DetalleHojaRuta dhr : getListaDetalleHojaRutaOtrosDespachos()) {
/* 186:211 */       if (dhr.isSeleccionado())
/* 187:    */       {
/* 188:212 */         getHojaRuta().getListaDetalleHojaRuta().add(dhr);
/* 189:213 */         this.hmDespachos.put(dhr.getDespachoCliente().getNumero(), dhr.getDespachoCliente());
/* 190:    */       }
/* 191:    */     }
/* 192:217 */     limpiarOtrosDespachos();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void limpiarOtrosDespachos()
/* 196:    */   {
/* 197:222 */     setListaDetalleHojaRutaOtrosDespachos(new ArrayList());
/* 198:223 */     this.empresaSeleccionanda = null;
/* 199:224 */     this.transportista = null;
/* 200:225 */     this.dtDetalleHojaRutaOtrosDespachos.reset();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String limpiar()
/* 204:    */   {
/* 205:230 */     super.limpiar();
/* 206:231 */     this.listaDetalleHojaRutaFilters = null;
/* 207:232 */     return "";
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void cargarSubempresas()
/* 211:    */   {
/* 212:236 */     if (getEmpresaSeleccionanda() != null) {
/* 213:237 */       this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(getEmpresaSeleccionanda().getId());
/* 214:    */     }
/* 215:239 */     cargarTransportitas();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void cargarTransportitas()
/* 219:    */   {
/* 220:243 */     getListaTransportista().clear();
/* 221:244 */     if (getEmpresaSeleccionanda() != null)
/* 222:    */     {
/* 223:245 */       Empresa empresa = this.servicioEmpresa.cargarDetalle(getEmpresaSeleccionanda());
/* 224:246 */       setTransportista(empresa.getCliente().getTransportista());
/* 225:    */     }
/* 226:247 */     else if ((getEmpresaSeleccionanda() != null) && (getSubempresaSeleccionada() != null))
/* 227:    */     {
/* 228:248 */       Empresa empresa = this.servicioEmpresa.cargarDetalle(getSubempresaSeleccionada().getEmpresa());
/* 229:249 */       setTransportista(empresa.getCliente().getTransportista());
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String emitirGuiaRemision()
/* 234:    */   {
/* 235:254 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/* 236:255 */     if ((getHojaRuta() != null) && 
/* 237:256 */       (getHojaRuta().getId() > 0) && 
/* 238:257 */       (getHojaRuta().getEstado() != Estado.ANULADO) && (
/* 239:258 */       (getHojaRuta().getGuiaRemision() == null) || ((!getHojaRuta().getGuiaRemision().getEstado().equals(Estado.EN_ESPERA)) && 
/* 240:259 */       (!getHojaRuta().getGuiaRemision().getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA)))))
/* 241:    */     {
/* 242:261 */       HttpSession session = (HttpSession)context.getSession(true);
/* 243:262 */       session.setAttribute("hojaRuta", getHojaRuta());
/* 244:263 */       return "/paginas/ventas/procesos/guiaRemision.xhtml?faces-redirect=true";
/* 245:    */     }
/* 246:265 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 247:266 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void seleccionarTodos()
/* 251:    */   {
/* 252:272 */     for (DetalleHojaRuta detalleHojaRuta : getHojaRuta().getListaDetalleHojaRuta()) {
/* 253:274 */       if (!detalleHojaRuta.isEliminado()) {
/* 254:275 */         detalleHojaRuta.setSeleccionado(this.aprobarTodos);
/* 255:    */       }
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void cargarTabla()
/* 260:    */   {
/* 261:281 */     if (getHojaRuta() != null) {
/* 262:282 */       this.listaDetalleFilter = getListaDetalleHojaRuta();
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void cargarTablaDialogo()
/* 267:    */   {
/* 268:287 */     this.listaDetalleFilterDialogo = getListaDetalleHojaRutaOtrosDespachos();
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setListaDetalleHojaRutaOtrosDespachos(List<DetalleHojaRuta> listaDetalleHojaRutaOtrosDespachos)
/* 272:    */   {
/* 273:291 */     this.listaDetalleHojaRutaOtrosDespachos = listaDetalleHojaRutaOtrosDespachos;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<DetalleHojaRuta> getListaDetalleHojaRutaOtrosDespachos()
/* 277:    */   {
/* 278:295 */     return this.listaDetalleHojaRutaOtrosDespachos;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public DataTable getDtDetalleHojaRutaOtrosDespachos()
/* 282:    */   {
/* 283:299 */     return this.dtDetalleHojaRutaOtrosDespachos;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setDtDetalleHojaRutaOtrosDespachos(DataTable dtDetalleHojaRutaOtrosDespachos)
/* 287:    */   {
/* 288:303 */     this.dtDetalleHojaRutaOtrosDespachos = dtDetalleHojaRutaOtrosDespachos;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Empresa getEmpresaSeleccionanda()
/* 292:    */   {
/* 293:307 */     return this.empresaSeleccionanda;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setEmpresaSeleccionanda(Empresa empresaSeleccionanda)
/* 297:    */   {
/* 298:311 */     this.empresaSeleccionanda = empresaSeleccionanda;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public Subempresa getSubempresaSeleccionada()
/* 302:    */   {
/* 303:315 */     return this.subempresaSeleccionada;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setSubempresaSeleccionada(Subempresa subempresaSeleccionada)
/* 307:    */   {
/* 308:319 */     this.subempresaSeleccionada = subempresaSeleccionada;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<Subempresa> getListaSubempresa()
/* 312:    */   {
/* 313:323 */     if (this.listaSubempresa == null) {
/* 314:324 */       this.listaSubempresa = new ArrayList();
/* 315:    */     }
/* 316:326 */     return this.listaSubempresa;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 320:    */   {
/* 321:330 */     this.listaSubempresa = listaSubempresa;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public List<Transportista> getListaTransportistaOtrosDespachos()
/* 325:    */   {
/* 326:334 */     return this.listaTransportistaOtrosDespachos == null ? (this.listaTransportistaOtrosDespachos = new ArrayList()) : this.listaTransportistaOtrosDespachos;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setListaTransportistaOtrosDespachos(List<Transportista> listaTransportistaOtrosDespachos)
/* 330:    */   {
/* 331:339 */     this.listaTransportistaOtrosDespachos = listaTransportistaOtrosDespachos;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public HashMap<String, DespachoCliente> getHmDespachos()
/* 335:    */   {
/* 336:343 */     return this.hmDespachos;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setHmDespachos(HashMap<String, DespachoCliente> hmDespachos)
/* 340:    */   {
/* 341:347 */     this.hmDespachos = hmDespachos;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public List<DetalleHojaRuta> getListaDetalleHojaRutaFilters()
/* 345:    */   {
/* 346:351 */     return this.listaDetalleHojaRutaFilters;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setListaDetalleHojaRutaFilters(List<DetalleHojaRuta> listaDetalleHojaRutaFilters)
/* 350:    */   {
/* 351:355 */     this.listaDetalleHojaRutaFilters = listaDetalleHojaRutaFilters;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean isAprobarTodos()
/* 355:    */   {
/* 356:359 */     return this.aprobarTodos;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setAprobarTodos(boolean aprobarTodos)
/* 360:    */   {
/* 361:363 */     this.aprobarTodos = aprobarTodos;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public List<DetalleHojaRuta> getListaDetalleFilter()
/* 365:    */   {
/* 366:367 */     return this.listaDetalleFilter;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setListaDetalleFilter(List<DetalleHojaRuta> listaDetalleFilter)
/* 370:    */   {
/* 371:371 */     this.listaDetalleFilter = listaDetalleFilter;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public List<DetalleHojaRuta> getListaDetalleFilterDialogo()
/* 375:    */   {
/* 376:375 */     return this.listaDetalleFilterDialogo;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setListaDetalleFilterDialogo(List<DetalleHojaRuta> listaDetalleFilterDialogo)
/* 380:    */   {
/* 381:379 */     this.listaDetalleFilterDialogo = listaDetalleFilterDialogo;
/* 382:    */   }
/* 383:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.HojaRutaTransportistaBean
 * JD-Core Version:    0.7.0.1
 */