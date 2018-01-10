/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.dao.CargaBSPDao;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.Asiento;
/*  10:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  15:    */ import com.asinfo.as2.entities.aerolineas.Ticket;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  24:    */ import com.asinfo.as2.util.AppUtil;
/*  25:    */ import com.asinfo.as2.utils.JsfUtil;
/*  26:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  27:    */ import com.asinfo.as2.ventas.procesos.aerolineas.servicio.ServicioVentaTicket;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Calendar;
/*  31:    */ import java.util.Date;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.List;
/*  34:    */ import java.util.Map;
/*  35:    */ import javax.annotation.PostConstruct;
/*  36:    */ import javax.ejb.EJB;
/*  37:    */ import javax.faces.bean.ManagedBean;
/*  38:    */ import javax.faces.bean.ViewScoped;
/*  39:    */ import javax.faces.model.SelectItem;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.primefaces.component.datatable.DataTable;
/*  42:    */ import org.primefaces.model.LazyDataModel;
/*  43:    */ import org.primefaces.model.SortOrder;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class InterfazContableVentasLocalesBean
/*  48:    */   extends PageControllerAS2
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = -8914881290932226380L;
/*  51:    */   @EJB
/*  52:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  53:    */   @EJB
/*  54:    */   private ServicioDocumento servicioDocumento;
/*  55:    */   @EJB
/*  56:    */   private ServicioVentaTicket servicioCargaBSP;
/*  57:    */   @EJB
/*  58:    */   private ServicioAsiento servicioAsiento;
/*  59:    */   @EJB
/*  60:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  61:    */   @EJB
/*  62:    */   private ServicioSecuencia servicioSecuencia;
/*  63:    */   @EJB
/*  64:    */   private CargaBSPDao cargaBSPDao;
/*  65:    */   @EJB
/*  66:    */   private ServicioGenerico<Ticket> servicioTicket;
/*  67:    */   private BigDecimal debe;
/*  68:    */   private BigDecimal haber;
/*  69:    */   private InterfazContableProceso interfazContableProceso;
/*  70:    */   private PuntoDeVenta puntoDeVenta;
/*  71:    */   private List<PuntoDeVenta> listaPuntoVenta;
/*  72:    */   private List<Ticket> listaTicket;
/*  73:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  74:    */   private List<Documento> listaDocumento;
/*  75:    */   private List<SelectItem> listaDocumentoBase;
/*  76:    */   private DataTable dtDetalleAsiento;
/*  77:    */   private DataTable dtInterfazContableProceso;
/*  78:111 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  79:113 */   private List<Asiento> listaAsientoB = new ArrayList();
/*  80:    */   
/*  81:    */   @PostConstruct
/*  82:    */   public void init()
/*  83:    */   {
/*  84:118 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  85:    */     {
/*  86:    */       private static final long serialVersionUID = 763093382591716471L;
/*  87:    */       
/*  88:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  89:    */       {
/*  90:130 */         List<InterfazContableProceso> lista = new ArrayList();
/*  91:    */         
/*  92:132 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  93:133 */         filters.put("documentoBase", String.valueOf(DocumentoBase.CARGA_ARCHIVO));
/*  94:    */         
/*  95:135 */         lista = InterfazContableVentasLocalesBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  96:136 */         InterfazContableVentasLocalesBean.this.listaInterfazContableProceso.setRowCount(InterfazContableVentasLocalesBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  97:137 */         return lista;
/*  98:    */       }
/*  99:    */     };
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String crear()
/* 103:    */   {
/* 104:149 */     limpiar();
/* 105:150 */     setEditado(true);
/* 106:    */     
/* 107:152 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String editar()
/* 111:    */   {
/* 112:162 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 113:163 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String guardar()
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:176 */       this.interfazContableProceso.setNumero(this.servicioSecuencia.obtenerSecuenciaDocumento(this.interfazContableProceso.getFiltroDocumento()
/* 121:177 */         .getIdDocumento(), new Date()));
/* 122:178 */       this.interfazContableProceso.setDocumento(this.interfazContableProceso.getFiltroDocumento());
/* 123:180 */       for (Asiento asiento : getListaAsientoB())
/* 124:    */       {
/* 125:181 */         asiento.setTipoAsiento(this.servicioDocumento.buscarPorId(Integer.valueOf(this.interfazContableProceso.getFiltroDocumento().getIdDocumento())).getTipoAsiento());
/* 126:182 */         asiento.setDocumentoOrigen(this.servicioDocumento.buscarPorId(Integer.valueOf(this.interfazContableProceso.getFiltroDocumento().getIdDocumento())));
/* 127:183 */         asiento.setConcepto(asiento.getConcepto() + " " + this.interfazContableProceso.getNumero());
/* 128:184 */         asiento.setIndicadorAutomatico(false);
/* 129:185 */         this.servicioAsiento.guardar(asiento);
/* 130:    */       }
/* 131:188 */       for (Ticket tic : getListaTicket()) {
/* 132:    */         try
/* 133:    */         {
/* 134:190 */           tic.setIndicadorContabilizado(true);
/* 135:191 */           this.servicioTicket.guardar(tic);
/* 136:    */         }
/* 137:    */         catch (AS2Exception e)
/* 138:    */         {
/* 139:194 */           e.printStackTrace();
/* 140:    */         }
/* 141:    */       }
/* 142:198 */       this.servicioInterfazContableProceso.guardar(this.interfazContableProceso);
/* 143:    */       
/* 144:200 */       setEditado(false);
/* 145:    */       
/* 146:202 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 147:    */     }
/* 148:    */     catch (ExcepcionAS2Financiero e)
/* 149:    */     {
/* 150:205 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 151:    */     }
/* 152:    */     catch (ExcepcionAS2 e)
/* 153:    */     {
/* 154:208 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 155:    */     }
/* 156:211 */     return "";
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String eliminar()
/* 160:    */   {
/* 161:    */     try
/* 162:    */     {
/* 163:224 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 164:225 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 165:226 */       cargarDatos();
/* 166:    */     }
/* 167:    */     catch (ExcepcionAS2Ventas e)
/* 168:    */     {
/* 169:229 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 170:    */     }
/* 171:    */     catch (ExcepcionAS2Financiero e)
/* 172:    */     {
/* 173:232 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 174:    */     }
/* 175:    */     catch (ExcepcionAS2 e)
/* 176:    */     {
/* 177:235 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 178:236 */       LOG.info(e);
/* 179:    */     }
/* 180:    */     catch (Exception e)
/* 181:    */     {
/* 182:238 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 183:239 */       LOG.error(e);
/* 184:    */     }
/* 185:242 */     return "";
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String limpiar()
/* 189:    */   {
/* 190:252 */     setEditado(false);
/* 191:253 */     this.interfazContableProceso = new InterfazContableProceso();
/* 192:254 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 193:255 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 194:256 */     this.interfazContableProceso.setFechaDesde(Calendar.getInstance().getTime());
/* 195:257 */     this.interfazContableProceso.setFechaHasta(Calendar.getInstance().getTime());
/* 196:258 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 197:259 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.CARGA_ARCHIVO);
/* 198:260 */     this.interfazContableProceso.setAsiento(new Asiento());
/* 199:261 */     this.interfazContableProceso.setFiltroDocumentoBase(DocumentoBase.CARGA_ARCHIVO);
/* 200:262 */     this.interfazContableProceso.setFechaContabilizacion(new Date());
/* 201:263 */     cargarListaDocumento();
/* 202:264 */     return "";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String preliminarInterfazProceso()
/* 206:    */   {
/* 207:    */     try
/* 208:    */     {
/* 209:275 */       this.listaAsientoB = new ArrayList();
/* 210:    */       
/* 211:277 */       setListaTicket(this.servicioCargaBSP.obtenerListaTicketPorFechas(this.listaAsientoB, this.interfazContableProceso.getFechaDesde(), this.interfazContableProceso
/* 212:278 */         .getFechaHasta(), AppUtil.getOrganizacion().getIdOrganizacion(), getPuntoDeVenta()));
/* 213:    */     }
/* 214:    */     catch (AS2Exception e)
/* 215:    */     {
/* 216:281 */       JsfUtil.addErrorMessage(e, "");
/* 217:    */     }
/* 218:    */     catch (Exception e)
/* 219:    */     {
/* 220:283 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 221:284 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 222:    */     }
/* 223:286 */     return "";
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String cargarDatos()
/* 227:    */   {
/* 228:296 */     setEditado(false);
/* 229:297 */     limpiar();
/* 230:298 */     return null;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 234:    */   {
/* 235:307 */     List<DetalleAsiento> lista = new ArrayList();
/* 236:308 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 237:309 */       for (DetalleAsiento da : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 238:311 */         if (!da.isEliminado()) {
/* 239:312 */           lista.add(da);
/* 240:    */         }
/* 241:    */       }
/* 242:    */     }
/* 243:316 */     return lista;
/* 244:    */   }
/* 245:    */   
/* 246:    */   private void calcular()
/* 247:    */   {
/* 248:323 */     this.debe = BigDecimal.ZERO;
/* 249:324 */     this.haber = BigDecimal.ZERO;
/* 250:326 */     for (DetalleAsiento d : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 251:327 */       if (!d.isEliminado())
/* 252:    */       {
/* 253:328 */         this.haber = this.haber.add(d.getHaber());
/* 254:329 */         this.debe = this.debe.add(d.getDebe());
/* 255:    */       }
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void cargarListaDocumento()
/* 260:    */   {
/* 261:    */     try
/* 262:    */     {
/* 263:336 */       this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(getInterfazContableProceso().getFiltroDocumentoBase());
/* 264:    */     }
/* 265:    */     catch (ExcepcionAS2 e)
/* 266:    */     {
/* 267:338 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public BigDecimal getDebe()
/* 272:    */   {
/* 273:353 */     if (this.debe == null) {
/* 274:354 */       calcular();
/* 275:    */     }
/* 276:356 */     return this.debe;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setDebe(BigDecimal debe)
/* 280:    */   {
/* 281:360 */     this.debe = debe;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public BigDecimal getHaber()
/* 285:    */   {
/* 286:364 */     if (this.haber == null) {
/* 287:365 */       calcular();
/* 288:    */     }
/* 289:367 */     return this.haber;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setHaber(BigDecimal haber)
/* 293:    */   {
/* 294:371 */     this.haber = haber;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<Documento> getListaDocumento()
/* 298:    */   {
/* 299:380 */     if (this.listaDocumento == null) {
/* 300:381 */       this.listaDocumento = new ArrayList();
/* 301:    */     }
/* 302:383 */     return this.listaDocumento;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 306:    */   {
/* 307:393 */     this.listaDocumento = listaDocumento;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public List<SelectItem> getListaDocumentoBase()
/* 311:    */   {
/* 312:402 */     if (this.listaDocumentoBase == null)
/* 313:    */     {
/* 314:403 */       this.listaDocumentoBase = new ArrayList();
/* 315:404 */       SelectItem item1 = new SelectItem(DocumentoBase.CARGA_ARCHIVO, DocumentoBase.CARGA_ARCHIVO.getNombre());
/* 316:405 */       this.listaDocumentoBase.add(item1);
/* 317:    */     }
/* 318:407 */     return this.listaDocumentoBase;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setListaDocumentoBase(List<SelectItem> listaDocumentoBase)
/* 322:    */   {
/* 323:417 */     this.listaDocumentoBase = listaDocumentoBase;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public InterfazContableProceso getInterfazContableProceso()
/* 327:    */   {
/* 328:421 */     return this.interfazContableProceso;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 332:    */   {
/* 333:425 */     this.interfazContableProceso = interfazContableProceso;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 337:    */   {
/* 338:429 */     return this.listaInterfazContableProceso;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 342:    */   {
/* 343:433 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public DataTable getDtDetalleAsiento()
/* 347:    */   {
/* 348:437 */     return this.dtDetalleAsiento;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 352:    */   {
/* 353:441 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public DataTable getDtInterfazContableProceso()
/* 357:    */   {
/* 358:445 */     return this.dtInterfazContableProceso;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 362:    */   {
/* 363:449 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public AS2Exception getExContabilizacion()
/* 367:    */   {
/* 368:458 */     return this.exContabilizacion;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 372:    */   {
/* 373:468 */     this.exContabilizacion = exContabilizacion;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public List<Asiento> getListaAsientoB()
/* 377:    */   {
/* 378:472 */     return this.listaAsientoB;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setListaAsientoB(List<Asiento> listaAsientoB)
/* 382:    */   {
/* 383:476 */     this.listaAsientoB = listaAsientoB;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public PuntoDeVenta getPuntoDeVenta()
/* 387:    */   {
/* 388:480 */     return this.puntoDeVenta;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 392:    */   {
/* 393:484 */     this.puntoDeVenta = puntoDeVenta;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public List<PuntoDeVenta> getListaPuntoVenta()
/* 397:    */   {
/* 398:489 */     HashMap<String, String> filters = new HashMap();
/* 399:490 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 400:    */     
/* 401:492 */     return this.listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("nombre", true, filters);
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setListaPuntoVenta(List<PuntoDeVenta> listaPuntoVenta)
/* 405:    */   {
/* 406:496 */     this.listaPuntoVenta = listaPuntoVenta;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public List<Ticket> getListaTicket()
/* 410:    */   {
/* 411:500 */     return this.listaTicket;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setListaTicket(List<Ticket> listaTicket)
/* 415:    */   {
/* 416:504 */     this.listaTicket = listaTicket;
/* 417:    */   }
/* 418:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.InterfazContableVentasLocalesBean
 * JD-Core Version:    0.7.0.1
 */