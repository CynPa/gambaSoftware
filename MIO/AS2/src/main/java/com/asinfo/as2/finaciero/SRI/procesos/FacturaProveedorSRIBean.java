/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.CompraCajaChica;
/*  10:    */ import com.asinfo.as2.entities.Contacto;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  16:    */ import com.asinfo.as2.entities.TipoContacto;
/*  17:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  18:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  19:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  20:    */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*  21:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  22:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  23:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  24:    */ import com.asinfo.as2.enumeraciones.TipoAnexoSRI;
/*  25:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*  28:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*  29:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*  30:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  31:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCompraCajaChica;
/*  33:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  34:    */ import com.asinfo.as2.util.AppUtil;
/*  35:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  36:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  37:    */ import java.math.BigDecimal;
/*  38:    */ import java.util.ArrayList;
/*  39:    */ import java.util.List;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.faces.bean.ManagedBean;
/*  42:    */ import javax.faces.bean.ViewScoped;
/*  43:    */ import javax.faces.context.ExternalContext;
/*  44:    */ import javax.faces.context.FacesContext;
/*  45:    */ import javax.servlet.http.HttpSession;
/*  46:    */ import org.apache.log4j.Logger;
/*  47:    */ import org.primefaces.component.datatable.DataTable;
/*  48:    */ import org.primefaces.event.SelectEvent;
/*  49:    */ 
/*  50:    */ @ManagedBean
/*  51:    */ @ViewScoped
/*  52:    */ public class FacturaProveedorSRIBean
/*  53:    */   extends PageControllerAS2
/*  54:    */ {
/*  55:    */   private static final long serialVersionUID = 2079139990048172552L;
/*  56:    */   @EJB
/*  57:    */   protected transient ServicioCreditoTributario servicioCreditoTributario;
/*  58:    */   @EJB
/*  59:    */   protected transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  60:    */   @EJB
/*  61:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  62:    */   @EJB
/*  63:    */   protected transient ServicioSRI servicioSRI;
/*  64:    */   @EJB
/*  65:    */   protected transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  66:    */   @EJB
/*  67:    */   protected transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  68:    */   @EJB
/*  69:    */   protected transient ServicioCategoriaRetencion servicioCategoriaRetencion;
/*  70:    */   @EJB
/*  71:    */   protected transient ServicioDocumento servicioDocumento;
/*  72:    */   @EJB
/*  73:    */   protected transient ServicioSecuencia servicioSecuencia;
/*  74:    */   @EJB
/*  75:    */   protected transient ServicioCompraCajaChica servicioCompraCajaChica;
/*  76:    */   protected List<CreditoTributarioSRI> listaCreditoTributarioSRI;
/*  77:    */   protected List<ConceptoRetencionSRI> listaConceptoRetencionSRI;
/*  78:    */   protected List<TipoComprobanteSRI> listaTipoComprobanteSRI;
/*  79:    */   protected List<Documento> listaDocumento;
/*  80:    */   private Boolean indicadorFactura;
/*  81:    */   protected FacturaProveedorSRI facturaProveedorSRI;
/*  82:    */   protected DataTable dtDetalleFacturaProveedorSRI;
/*  83:    */   protected DataTable dtDetalleIVAFacturaProveedorSRI;
/*  84:    */   protected boolean mostrarBasesDetalle;
/*  85:    */   protected boolean mostrarBaseImponible;
/*  86:    */   protected boolean corrigeDatosRetencion;
/*  87:    */   protected String tipoAnexoSRI;
/*  88:    */   private String mensaje;
/*  89:    */   
/*  90:    */   public ServicioCreditoTributario getServicioCreditoTributario()
/*  91:    */   {
/*  92:113 */     return this.servicioCreditoTributario;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setServicioCreditoTributario(ServicioCreditoTributario servicioCreditoTributario)
/*  96:    */   {
/*  97:117 */     this.servicioCreditoTributario = servicioCreditoTributario;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ServicioSRI getServicioSRI()
/* 101:    */   {
/* 102:121 */     return this.servicioSRI;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setServicioSRI(ServicioSRI servicioSRI)
/* 106:    */   {
/* 107:125 */     this.servicioSRI = servicioSRI;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public ServicioFacturaProveedorSRI getServicioFacturaProveedorSRI()
/* 111:    */   {
/* 112:129 */     return this.servicioFacturaProveedorSRI;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setServicioFacturaProveedorSRI(ServicioFacturaProveedorSRI servicioFacturaProveedorSRI)
/* 116:    */   {
/* 117:133 */     this.servicioFacturaProveedorSRI = servicioFacturaProveedorSRI;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public ServicioConceptoRetencionSRI getServicioConceptoRetencionSRI()
/* 121:    */   {
/* 122:137 */     return this.servicioConceptoRetencionSRI;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setServicioConceptoRetencionSRI(ServicioConceptoRetencionSRI servicioConceptoRetencionSRI)
/* 126:    */   {
/* 127:141 */     this.servicioConceptoRetencionSRI = servicioConceptoRetencionSRI;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<CreditoTributarioSRI> getListaCreditoTributarioSRI()
/* 131:    */   {
/* 132:145 */     return this.listaCreditoTributarioSRI;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaCreditoTributarioSRI(List<CreditoTributarioSRI> listaCreditoTributarioSRI)
/* 136:    */   {
/* 137:149 */     this.listaCreditoTributarioSRI = listaCreditoTributarioSRI;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<ConceptoRetencionSRI> getListaConceptoRetencionSRI()
/* 141:    */   {
/* 142:153 */     if (this.listaConceptoRetencionSRI == null) {
/* 143:154 */       cargarConceptoRetencion();
/* 144:    */     }
/* 145:156 */     return this.listaConceptoRetencionSRI;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setListaConceptoRetencionSRI(List<ConceptoRetencionSRI> listaConceptoRetencionSRI)
/* 149:    */   {
/* 150:160 */     this.listaConceptoRetencionSRI = listaConceptoRetencionSRI;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public FacturaProveedorSRI getFacturaProveedorSRI()
/* 154:    */   {
/* 155:164 */     return this.facturaProveedorSRI;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void cargarInformacion()
/* 159:    */   {
/* 160:168 */     if (this.indicadorFactura != null)
/* 161:    */     {
/* 162:170 */       if (getTipoAnexoSRI().equals(TipoAnexoSRI.ATS.getNombreAbreviado()))
/* 163:    */       {
/* 164:171 */         setMostrarBaseImponible(true);
/* 165:172 */         setMostrarBasesDetalle(false);
/* 166:    */       }
/* 167:    */       else
/* 168:    */       {
/* 169:174 */         setMostrarBaseImponible(false);
/* 170:175 */         setMostrarBasesDetalle(true);
/* 171:    */       }
/* 172:    */       try
/* 173:    */       {
/* 174:179 */         this.mensaje = null;
/* 175:    */         
/* 176:    */ 
/* 177:182 */         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/* 178:183 */         HttpSession session = (HttpSession)context.getSession(true);
/* 179:184 */         CompraCajaChica compraCajaChica = (CompraCajaChica)session.getAttribute("compraCajaChica");
/* 180:185 */         FacturaProveedor facturaProveedor = (FacturaProveedor)session.getAttribute("facturaProveedor");
/* 181:186 */         Empresa empresa = null;
/* 182:187 */         if (!this.indicadorFactura.booleanValue())
/* 183:    */         {
/* 184:188 */           this.corrigeDatosRetencion = compraCajaChica.getFacturaProveedorSRI().isTraCorregirDatos();
/* 185:189 */           CompraCajaChica ccc = this.servicioCompraCajaChica.cargarDetalle(compraCajaChica.getIdCompraCajaChica());
/* 186:190 */           empresa = ccc.getEmpresa();
/* 187:191 */           this.facturaProveedorSRI = ccc.getFacturaProveedorSRI();
/* 188:    */         }
/* 189:    */         else
/* 190:    */         {
/* 191:193 */           this.corrigeDatosRetencion = facturaProveedor.getFacturaProveedorSRI().isTraCorregirDatos();
/* 192:194 */           FacturaProveedor facProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(facturaProveedor.getId()));
/* 193:195 */           empresa = facProveedor.getEmpresa();
/* 194:196 */           this.facturaProveedorSRI = facProveedor.getFacturaProveedorSRI();
/* 195:    */         }
/* 196:200 */         if (empresa != null)
/* 197:    */         {
/* 198:201 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 199:202 */           if (this.facturaProveedorSRI != null)
/* 200:    */           {
/* 201:204 */             String email = "";
/* 202:205 */             boolean tieneEmail = false;
/* 203:206 */             if ((empresa.getListaContacto() != null) && (!empresa.getListaContacto().isEmpty()))
/* 204:    */             {
/* 205:207 */               for (Contacto contacto : empresa.getListaContacto()) {
/* 206:208 */                 if ((contacto.getTipoContacto() != null) && (contacto.getTipoContacto().isIndicadorNotificarRetencion()))
/* 207:    */                 {
/* 208:209 */                   if ((contacto.getEmail1() != null) && (!contacto.getEmail1().equals(""))) {
/* 209:210 */                     email = email + contacto.getEmail1() + ";";
/* 210:    */                   }
/* 211:212 */                   if ((contacto.getEmail2() != null) && (!contacto.getEmail2().equals(""))) {
/* 212:213 */                     email = email + contacto.getEmail2() + ";";
/* 213:    */                   }
/* 214:    */                 }
/* 215:    */               }
/* 216:217 */               if (!email.equals(""))
/* 217:    */               {
/* 218:218 */                 email = email.substring(0, email.trim().length() - 1).trim();
/* 219:219 */                 tieneEmail = true;
/* 220:    */               }
/* 221:    */             }
/* 222:223 */             if (!tieneEmail)
/* 223:    */             {
/* 224:224 */               if ((empresa.getEmail1() != null) && (!empresa.getEmail1().equals(""))) {
/* 225:225 */                 email = email + empresa.getEmail1();
/* 226:    */               }
/* 227:227 */               if ((empresa.getEmail2() != null) && (!empresa.getEmail2().equals("")))
/* 228:    */               {
/* 229:228 */                 if (!email.equals("")) {
/* 230:229 */                   email = email + ";";
/* 231:    */                 }
/* 232:231 */                 email = email + empresa.getEmail2();
/* 233:    */               }
/* 234:    */             }
/* 235:235 */             this.facturaProveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(this.facturaProveedorSRI.getIdFacturaProveedorSRI());
/* 236:236 */             this.facturaProveedorSRI.setEmail(email);
/* 237:237 */             this.servicioFacturaProveedorSRI.cargarConceptosRetencion(this.facturaProveedorSRI, empresa);
/* 238:238 */             this.facturaProveedorSRI.setTraCorregirDatos(this.corrigeDatosRetencion);
/* 239:239 */             if ((this.facturaProveedorSRI.getDocumento() == null) && (getListaDocumento().size() > 0)) {
/* 240:240 */               this.facturaProveedorSRI.setDocumento((Documento)this.listaDocumento.get(0));
/* 241:    */             }
/* 242:242 */             cargarSecuencia(this.facturaProveedorSRI, AppUtil.getPuntoDeVenta());
/* 243:    */           }
/* 244:    */         }
/* 245:246 */         if (this.facturaProveedorSRI != null)
/* 246:    */         {
/* 247:247 */           this.facturaProveedorSRI.setTraCorregirDatos(this.corrigeDatosRetencion);
/* 248:248 */           if ((!this.corrigeDatosRetencion) && (FuncionesUtiles.getAnio(this.facturaProveedorSRI.getFechaRegistro()) != 9999)) {
/* 249:249 */             this.facturaProveedorSRI.setFechaEmisionRetencion(this.facturaProveedorSRI.getFechaRegistro());
/* 250:    */           }
/* 251:    */         }
/* 252:252 */         if ((this.facturaProveedorSRI.isIndicadorRetencionEmitida()) && (!this.facturaProveedorSRI.isTraCorregirDatos())) {
/* 253:253 */           this.mensaje = getLanguageController().getMensaje("msg_accion_implica_anulacion_previa");
/* 254:    */         }
/* 255:    */       }
/* 256:    */       catch (ExcepcionAS2 e)
/* 257:    */       {
/* 258:256 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 259:257 */         LOG.info("ERROR ExcepcionAS2 AL CARGAR DATOS EN FACTURAPROVEEDORSRIBEAN", e);
/* 260:    */       }
/* 261:    */       catch (Exception e)
/* 262:    */       {
/* 263:260 */         LOG.error("ERROR Exception AL CARGAR DATOS EN FACTURAPROVEEDORSRIBEAN ", e);
/* 264:261 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 265:    */       }
/* 266:    */       finally
/* 267:    */       {
/* 268:264 */         cargarListaTipoComprobanteSRI();
/* 269:    */         
/* 270:266 */         cargarListaCreditoTributarioSRI();
/* 271:    */       }
/* 272:268 */       this.indicadorFactura = null;
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void cargarSecuencia(FacturaProveedorSRI facturaProveedorSRI, PuntoDeVenta puntoDeVenta)
/* 277:    */     throws ExcepcionAS2
/* 278:    */   {
/* 279:280 */     AutorizacionDocumentoSRI autorizacionDocumentoSRI = null;
/* 280:283 */     if (puntoDeVenta != null) {
/* 281:284 */       autorizacionDocumentoSRI = this.servicioDocumento.cargarDocumentoConAutorizacion(getFacturaProveedorSRI().getDocumento(), puntoDeVenta, facturaProveedorSRI
/* 282:285 */         .getFechaEmisionRetencion());
/* 283:    */     }
/* 284:287 */     if ((Integer.parseInt(facturaProveedorSRI.getNumeroRetencion()) == 0) || (
/* 285:288 */       (facturaProveedorSRI.getDocumento() != null) && (facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) && 
/* 286:289 */       (!facturaProveedorSRI.isTraCorregirDatos())))
/* 287:    */     {
/* 288:290 */       String numero = this.servicioSecuencia.obtenerSecuencia(getFacturaProveedorSRI().getDocumento().getSecuencia(), facturaProveedorSRI
/* 289:291 */         .getFechaEmisionRetencion());
/* 290:    */       
/* 291:293 */       facturaProveedorSRI.setNumeroRetencion(numero);
/* 292:294 */       facturaProveedorSRI.setAutorizacionRetencion(autorizacionDocumentoSRI.getAutorizacion());
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public List<Documento> getListaDocumento()
/* 297:    */   {
/* 298:299 */     if (this.listaDocumento == null) {
/* 299:    */       try
/* 300:    */       {
/* 301:301 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.RETENCION_PROVEEDOR, AppUtil.getOrganizacion()
/* 302:302 */           .getId());
/* 303:    */       }
/* 304:    */       catch (ExcepcionAS2 e)
/* 305:    */       {
/* 306:304 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 307:    */       }
/* 308:    */     }
/* 309:307 */     return this.listaDocumento;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public List<Documento> getListaDocumentoFacturaProveedor()
/* 313:    */   {
/* 314:311 */     List<Documento> listaDocumentoFactura = new ArrayList();
/* 315:    */     try
/* 316:    */     {
/* 317:313 */       listaDocumentoFactura = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_PROVEEDOR, AppUtil.getOrganizacion()
/* 318:314 */         .getId());
/* 319:    */     }
/* 320:    */     catch (ExcepcionAS2 e)
/* 321:    */     {
/* 322:316 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 323:    */     }
/* 324:318 */     return listaDocumentoFactura;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String actualizarDocumento()
/* 328:    */   {
/* 329:    */     try
/* 330:    */     {
/* 331:323 */       if ((!this.corrigeDatosRetencion) && (this.facturaProveedorSRI.getDocumento() != null) && 
/* 332:324 */         (this.facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) && 
/* 333:325 */         (FuncionesUtiles.getAnio(this.facturaProveedorSRI.getFechaRegistro()) != 9999)) {
/* 334:326 */         this.facturaProveedorSRI.setFechaEmisionRetencion(this.facturaProveedorSRI.getFechaRegistro());
/* 335:    */       }
/* 336:329 */       cargarSecuencia(this.facturaProveedorSRI, AppUtil.getPuntoDeVenta());
/* 337:    */     }
/* 338:    */     catch (ExcepcionAS2 e)
/* 339:    */     {
/* 340:331 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 341:    */     }
/* 342:333 */     return "";
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setFacturaProveedorSRI(FacturaProveedorSRI facturaProveedorSRI)
/* 346:    */   {
/* 347:337 */     this.facturaProveedorSRI = facturaProveedorSRI;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public List<TipoComprobanteSRI> getListaTipoComprobanteSRI()
/* 351:    */   {
/* 352:341 */     return this.listaTipoComprobanteSRI;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setListaTipoComprobanteSRI(List<TipoComprobanteSRI> listaTipoComprobanteSRI)
/* 356:    */   {
/* 357:345 */     this.listaTipoComprobanteSRI = listaTipoComprobanteSRI;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public DataTable getDtDetalleFacturaProveedorSRI()
/* 361:    */   {
/* 362:349 */     return this.dtDetalleFacturaProveedorSRI;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setDtDetalleFacturaProveedorSRI(DataTable dtDetalleFacturaProveedorSRI)
/* 366:    */   {
/* 367:353 */     this.dtDetalleFacturaProveedorSRI = dtDetalleFacturaProveedorSRI;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public List<DetalleFacturaProveedorSRI> getListaDetalleFacturaProveedorSRI()
/* 371:    */   {
/* 372:362 */     List<DetalleFacturaProveedorSRI> detalle = new ArrayList();
/* 373:363 */     for (DetalleFacturaProveedorSRI dfpSRI : getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRI()) {
/* 374:364 */       if ((!dfpSRI.isEliminado()) && (dfpSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.FUENTE))) {
/* 375:365 */         detalle.add(dfpSRI);
/* 376:    */       }
/* 377:    */     }
/* 378:368 */     return detalle;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public List<DetalleFacturaProveedorSRI> getListaDetalleIVAFacturaProveedorSRI()
/* 382:    */   {
/* 383:372 */     List<DetalleFacturaProveedorSRI> detalle = new ArrayList();
/* 384:373 */     for (DetalleFacturaProveedorSRI dfpSRI : getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRI()) {
/* 385:374 */       if ((!dfpSRI.isEliminado()) && (dfpSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA))) {
/* 386:375 */         detalle.add(dfpSRI);
/* 387:    */       }
/* 388:    */     }
/* 389:378 */     return detalle;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void cargarConceptoRetencion()
/* 393:    */   {
/* 394:    */     try
/* 395:    */     {
/* 396:387 */       this.listaConceptoRetencionSRI = this.servicioConceptoRetencionSRI.getConceptoListaRetencionPorFecha(this.facturaProveedorSRI.getFechaRegistro());
/* 397:    */     }
/* 398:    */     catch (Exception e)
/* 399:    */     {
/* 400:390 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 401:    */     }
/* 402:    */   }
/* 403:    */   
/* 404:    */   public String agregarDetalleFacturaSRI()
/* 405:    */   {
/* 406:401 */     this.servicioFacturaProveedorSRI.cargarDetalleRetencion(this.facturaProveedorSRI, null, null);
/* 407:    */     
/* 408:403 */     return "";
/* 409:    */   }
/* 410:    */   
/* 411:    */   public String agregarDetalleFacturaSRI332()
/* 412:    */   {
/* 413:415 */     this.servicioFacturaProveedor.agregarDetalleFacturaSRI332(this.facturaProveedorSRI);
/* 414:416 */     return "";
/* 415:    */   }
/* 416:    */   
/* 417:    */   public String agregarDetalleIVAFacturaSRI()
/* 418:    */   {
/* 419:421 */     this.servicioFacturaProveedorSRI.cargarDetalleIVARetencion(this.facturaProveedorSRI, null);
/* 420:    */     
/* 421:423 */     return "";
/* 422:    */   }
/* 423:    */   
/* 424:    */   public String eliminarDetalleFacturaSRI()
/* 425:    */   {
/* 426:428 */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = (DetalleFacturaProveedorSRI)this.dtDetalleFacturaProveedorSRI.getRowData();
/* 427:429 */     detalleFacturaProveedorSRI.setEliminado(true);
/* 428:    */     
/* 429:431 */     return "";
/* 430:    */   }
/* 431:    */   
/* 432:    */   public String eliminarDetalleIVAFacturaSRI()
/* 433:    */   {
/* 434:436 */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = (DetalleFacturaProveedorSRI)this.dtDetalleIVAFacturaProveedorSRI.getRowData();
/* 435:437 */     detalleFacturaProveedorSRI.setEliminado(true);
/* 436:    */     
/* 437:439 */     return "";
/* 438:    */   }
/* 439:    */   
/* 440:    */   public String editar()
/* 441:    */   {
/* 442:445 */     return null;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public String guardar()
/* 446:    */   {
/* 447:455 */     String url = "";
/* 448:    */     try
/* 449:    */     {
/* 450:458 */       if ((this.facturaProveedorSRI.getMensajeSRI() == null) || (this.facturaProveedorSRI.getMensajeSRI().toLowerCase().contains("guardado")) || 
/* 451:459 */         (!this.facturaProveedorSRI.isTraCorregirDatos()))
/* 452:    */       {
/* 453:461 */         if (this.facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) {
/* 454:462 */           this.facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/* 455:    */         }
/* 456:464 */         if (isMostrarBaseImponible()) {
/* 457:465 */           for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : this.facturaProveedorSRI.getListaDetalleFacturaProveedorSRI())
/* 458:    */           {
/* 459:466 */             detalleFacturaProveedorSRI.setBaseImponibleTarifaCero(BigDecimal.ZERO);
/* 460:467 */             detalleFacturaProveedorSRI.setBaseImponibleDiferenteCero(BigDecimal.ZERO);
/* 461:468 */             detalleFacturaProveedorSRI.setBaseImponibleNoObjetoIva(BigDecimal.ZERO);
/* 462:    */           }
/* 463:    */         }
/* 464:471 */         if (isMostrarBasesDetalle()) {
/* 465:472 */           for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : this.facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/* 466:473 */             detalleFacturaProveedorSRI.setBaseImponibleRetencion(BigDecimal.ZERO);
/* 467:    */           }
/* 468:    */         }
/* 469:477 */         this.servicioFacturaProveedorSRI.guardar(this.facturaProveedorSRI);
/* 470:    */       }
/* 471:    */       else
/* 472:    */       {
/* 473:480 */         this.servicioFacturaProveedorSRI.actualizarRetencion(this.facturaProveedorSRI);
/* 474:    */       }
/* 475:482 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 476:484 */       if (this.facturaProveedorSRI.getFacturaProveedor() == null) {
/* 477:485 */         url = "/paginas/financiero/contabilidad/procesos/compraCajaChica?faces-redirect=true";
/* 478:    */       } else {
/* 479:488 */         url = "/paginas/compras/procesos/facturaProveedor?faces-redirect=true&numero=" + this.facturaProveedorSRI.getFacturaProveedor().getNumero();
/* 480:    */       }
/* 481:    */     }
/* 482:    */     catch (ExcepcionAS2Financiero e)
/* 483:    */     {
/* 484:492 */       e.printStackTrace();
/* 485:493 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 486:494 */       LOG.info("ERROR ExcepcionAS2Financiero AL GUARDAR DATOS FACTURA PROVEEDOR SRI", e);
/* 487:    */     }
/* 488:    */     catch (ExcepcionAS2 e)
/* 489:    */     {
/* 490:497 */       e.printStackTrace();
/* 491:498 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 492:499 */       LOG.info("ERROR ExcepcionAS2 AL GUARDAR DATOS FACTURA PROVEEDOR SRI", e);
/* 493:    */     }
/* 494:    */     catch (Exception e)
/* 495:    */     {
/* 496:502 */       e.printStackTrace();
/* 497:503 */       addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 498:504 */       LOG.error("ERROR AL GUARDAR DATOS FACTURA PROVEEDOR SRI", e);
/* 499:    */     }
/* 500:507 */     return url;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public String eliminar()
/* 504:    */   {
/* 505:518 */     return null;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public String limpiar()
/* 509:    */   {
/* 510:529 */     return null;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public String cargarDatos()
/* 514:    */   {
/* 515:540 */     return null;
/* 516:    */   }
/* 517:    */   
/* 518:    */   public String cancelar()
/* 519:    */   {
/* 520:545 */     String url = "";
/* 521:546 */     if (this.facturaProveedorSRI.getFacturaProveedor() != null) {
/* 522:547 */       url = "/paginas/compras/procesos/facturaProveedor?faces-redirect=true";
/* 523:    */     } else {
/* 524:549 */       url = "/paginas/financiero/contabilidad/procesos/compraCajaChica?faces-redirect=true";
/* 525:    */     }
/* 526:551 */     return url;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void calcular()
/* 530:    */   {
/* 531:556 */     this.facturaProveedorSRI.setMontoIva(this.facturaProveedorSRI.getBaseImponibleDiferenteCero().multiply(
/* 532:557 */       ParametrosSistema.getPorcentajeIva().divide(BigDecimal.valueOf(100L))));
/* 533:    */   }
/* 534:    */   
/* 535:    */   public List<ConceptoRetencionSRI> autocompletarConceptoRetencionSRI(String consulta)
/* 536:    */   {
/* 537:568 */     String consultaMayuscula = consulta.toUpperCase();
/* 538:569 */     List<ConceptoRetencionSRI> lista = new ArrayList();
/* 539:571 */     for (ConceptoRetencionSRI conceptoRetencionSRI : getListaConceptoRetencionSRI()) {
/* 540:572 */       if (((conceptoRetencionSRI.getCodigo().toUpperCase().contains(consultaMayuscula)) || 
/* 541:573 */         (conceptoRetencionSRI.getNombre().toUpperCase().contains(consultaMayuscula))) && 
/* 542:574 */         (conceptoRetencionSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.FUENTE))) {
/* 543:575 */         lista.add(conceptoRetencionSRI);
/* 544:    */       }
/* 545:    */     }
/* 546:579 */     return lista;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public List<ConceptoRetencionSRI> autocompletarConceptoRetencionIVASRI(String consulta)
/* 550:    */   {
/* 551:583 */     String consultaMayuscula = consulta.toUpperCase();
/* 552:584 */     List<ConceptoRetencionSRI> lista = new ArrayList();
/* 553:586 */     for (ConceptoRetencionSRI conceptoRetencionSRI : getListaConceptoRetencionSRI()) {
/* 554:587 */       if (((conceptoRetencionSRI.getCodigo().toUpperCase().contains(consultaMayuscula)) || 
/* 555:588 */         (conceptoRetencionSRI.getNombre().toUpperCase().contains(consultaMayuscula))) && 
/* 556:589 */         (conceptoRetencionSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA))) {
/* 557:590 */         lista.add(conceptoRetencionSRI);
/* 558:    */       }
/* 559:    */     }
/* 560:594 */     return lista;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public String actualizarValorRetencionListener()
/* 564:    */   {
/* 565:599 */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = (DetalleFacturaProveedorSRI)this.dtDetalleFacturaProveedorSRI.getRowData();
/* 566:600 */     this.servicioFacturaProveedorSRI.actualizarValorRetencion(detalleFacturaProveedorSRI);
/* 567:601 */     return "";
/* 568:    */   }
/* 569:    */   
/* 570:    */   public String actualizarValorRetencionIVAListener()
/* 571:    */   {
/* 572:605 */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = (DetalleFacturaProveedorSRI)this.dtDetalleIVAFacturaProveedorSRI.getRowData();
/* 573:606 */     this.servicioFacturaProveedorSRI.actualizarValorRetencion(detalleFacturaProveedorSRI);
/* 574:607 */     return "";
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void actualizarConceptoRetencionSRI(SelectEvent event)
/* 578:    */   {
/* 579:620 */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = (DetalleFacturaProveedorSRI)this.dtDetalleFacturaProveedorSRI.getRowData();
/* 580:621 */     detalleFacturaProveedorSRI.setConceptoRetencionSRI((ConceptoRetencionSRI)event.getObject());
/* 581:622 */     this.servicioFacturaProveedorSRI.actualizarValorRetencion(detalleFacturaProveedorSRI);
/* 582:    */   }
/* 583:    */   
/* 584:    */   public void actualizarConceptoRetencionIVASRI(SelectEvent event)
/* 585:    */   {
/* 586:627 */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = (DetalleFacturaProveedorSRI)this.dtDetalleIVAFacturaProveedorSRI.getRowData();
/* 587:628 */     detalleFacturaProveedorSRI.setConceptoRetencionSRI((ConceptoRetencionSRI)event.getObject());
/* 588:629 */     this.servicioFacturaProveedorSRI.actualizarValorRetencion(detalleFacturaProveedorSRI);
/* 589:    */   }
/* 590:    */   
/* 591:    */   public boolean isMostrarBasesDetalle()
/* 592:    */   {
/* 593:638 */     return this.mostrarBasesDetalle;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void setMostrarBasesDetalle(boolean mostrarBasesDetalle)
/* 597:    */   {
/* 598:648 */     this.mostrarBasesDetalle = mostrarBasesDetalle;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public boolean isMostrarBaseImponible()
/* 602:    */   {
/* 603:657 */     return this.mostrarBaseImponible;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void setMostrarBaseImponible(boolean mostrarBaseImponible)
/* 607:    */   {
/* 608:667 */     this.mostrarBaseImponible = mostrarBaseImponible;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public String getTipoAnexoSRI()
/* 612:    */   {
/* 613:676 */     this.tipoAnexoSRI = ParametrosSistema.getTipoAnexoSRI(AppUtil.getOrganizacion().getId());
/* 614:677 */     return this.tipoAnexoSRI;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public void setTipoAnexoSRI(String tipoAnexoSRI)
/* 618:    */   {
/* 619:687 */     this.tipoAnexoSRI = tipoAnexoSRI;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public boolean isCorrigeDatosRetencion()
/* 623:    */   {
/* 624:696 */     return this.corrigeDatosRetencion;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public void setCorrigeDatosRetencion(boolean corrigeDatosRetencion)
/* 628:    */   {
/* 629:706 */     this.corrigeDatosRetencion = corrigeDatosRetencion;
/* 630:    */   }
/* 631:    */   
/* 632:    */   public String getMensaje()
/* 633:    */   {
/* 634:710 */     return this.mensaje;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public void setMensaje(String mensaje)
/* 638:    */   {
/* 639:714 */     this.mensaje = mensaje;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public DataTable getDtDetalleIVAFacturaProveedorSRI()
/* 643:    */   {
/* 644:718 */     return this.dtDetalleIVAFacturaProveedorSRI;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public void setDtDetalleIVAFacturaProveedorSRI(DataTable dtDetalleIVAFacturaProveedorSRI)
/* 648:    */   {
/* 649:722 */     this.dtDetalleIVAFacturaProveedorSRI = dtDetalleIVAFacturaProveedorSRI;
/* 650:    */   }
/* 651:    */   
/* 652:    */   public void btnActualizarSecuencia()
/* 653:    */   {
/* 654:727 */     AutorizacionDocumentoSRI autorizacionDocumentoSRI = null;
/* 655:728 */     PuntoDeVenta puntoDeVenta = AppUtil.getPuntoDeVenta();
/* 656:    */     try
/* 657:    */     {
/* 658:731 */       if (puntoDeVenta != null) {
/* 659:732 */         autorizacionDocumentoSRI = this.servicioDocumento.cargarDocumentoConAutorizacion(getFacturaProveedorSRI().getDocumento(), puntoDeVenta, this.facturaProveedorSRI
/* 660:733 */           .getFechaEmisionRetencion());
/* 661:    */       }
/* 662:735 */       String numero = this.servicioSecuencia.obtenerSecuencia(getFacturaProveedorSRI().getDocumento().getSecuencia(), this.facturaProveedorSRI
/* 663:736 */         .getFechaEmisionRetencion());
/* 664:    */       
/* 665:738 */       this.facturaProveedorSRI.setNumeroRetencion(numero);
/* 666:739 */       this.facturaProveedorSRI.setAutorizacionRetencion(autorizacionDocumentoSRI.getAutorizacion());
/* 667:    */     }
/* 668:    */     catch (ExcepcionAS2 e)
/* 669:    */     {
/* 670:741 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 671:    */     }
/* 672:    */   }
/* 673:    */   
/* 674:    */   public void cargarListaTipoComprobanteSRI()
/* 675:    */   {
/* 676:746 */     this.listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(this.facturaProveedorSRI.getTipoIdentificacion());
/* 677:    */   }
/* 678:    */   
/* 679:    */   public void cargarListaCreditoTributarioSRI()
/* 680:    */   {
/* 681:750 */     this.listaCreditoTributarioSRI = this.servicioCreditoTributario.buscarPorTipoComprobanteSRI(this.facturaProveedorSRI.getTipoComprobanteSRI(), this.facturaProveedorSRI
/* 682:751 */       .getTipoIdentificacion());
/* 683:    */   }
/* 684:    */   
/* 685:    */   public void actualizarPorTipoIdentificacion()
/* 686:    */   {
/* 687:755 */     cargarListaTipoComprobanteSRI();
/* 688:756 */     cargarListaCreditoTributarioSRI();
/* 689:    */   }
/* 690:    */   
/* 691:    */   public boolean isEditaConceptoYBase()
/* 692:    */   {
/* 693:761 */     return (this.facturaProveedorSRI.getMensajeSRI() != null) && (!this.facturaProveedorSRI.getMensajeSRI().toLowerCase().contains("guardado")) && (this.facturaProveedorSRI.isTraCorregirDatos());
/* 694:    */   }
/* 695:    */   
/* 696:    */   public Boolean getIndicadorFactura()
/* 697:    */   {
/* 698:765 */     return this.indicadorFactura;
/* 699:    */   }
/* 700:    */   
/* 701:    */   public void setIndicadorFactura(Boolean indicadorFactura)
/* 702:    */   {
/* 703:769 */     this.indicadorFactura = indicadorFactura;
/* 704:    */   }
/* 705:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.FacturaProveedorSRIBean
 * JD-Core Version:    0.7.0.1
 */