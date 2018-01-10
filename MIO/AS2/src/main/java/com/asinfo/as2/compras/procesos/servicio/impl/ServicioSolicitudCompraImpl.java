/*   1:    */ package com.asinfo.as2.compras.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioSolicitudCompra;
/*   6:    */ import com.asinfo.as2.compras.reportes.ReporteSolicitudCompraBean;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   8:    */ import com.asinfo.as2.dao.GenericoDao;
/*   9:    */ import com.asinfo.as2.dao.ProductoUltimaCompraDao;
/*  10:    */ import com.asinfo.as2.dao.ProveedorDao;
/*  11:    */ import com.asinfo.as2.dao.SolicitudCompraDao;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  14:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  15:    */ import com.asinfo.as2.entities.Bodega;
/*  16:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*  17:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  18:    */ import com.asinfo.as2.entities.Contacto;
/*  19:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*  20:    */ import com.asinfo.as2.entities.DetalleSolicitudCompra;
/*  21:    */ import com.asinfo.as2.entities.DetalleSolicitudCompraProveedor;
/*  22:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  23:    */ import com.asinfo.as2.entities.Documento;
/*  24:    */ import com.asinfo.as2.entities.Empleado;
/*  25:    */ import com.asinfo.as2.entities.Empresa;
/*  26:    */ import com.asinfo.as2.entities.ImpuestoProductoPedidoProveedor;
/*  27:    */ import com.asinfo.as2.entities.Organizacion;
/*  28:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  29:    */ import com.asinfo.as2.entities.Producto;
/*  30:    */ import com.asinfo.as2.entities.ProductoUltimaCompra;
/*  31:    */ import com.asinfo.as2.entities.Proveedor;
/*  32:    */ import com.asinfo.as2.entities.RangoImpuesto;
/*  33:    */ import com.asinfo.as2.entities.SolicitudCompra;
/*  34:    */ import com.asinfo.as2.entities.Sucursal;
/*  35:    */ import com.asinfo.as2.entities.TipoContacto;
/*  36:    */ import com.asinfo.as2.entities.Unidad;
/*  37:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  38:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  39:    */ import com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum;
/*  40:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  41:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  42:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  43:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  44:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  45:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  46:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  47:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  48:    */ import com.asinfo.as2.util.AppUtil;
/*  49:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  50:    */ import java.io.PrintStream;
/*  51:    */ import java.math.BigDecimal;
/*  52:    */ import java.text.SimpleDateFormat;
/*  53:    */ import java.util.ArrayList;
/*  54:    */ import java.util.Date;
/*  55:    */ import java.util.HashMap;
/*  56:    */ import java.util.HashSet;
/*  57:    */ import java.util.Iterator;
/*  58:    */ import java.util.List;
/*  59:    */ import java.util.Map;
/*  60:    */ import java.util.Set;
/*  61:    */ import java.util.TreeMap;
/*  62:    */ import javax.ejb.EJB;
/*  63:    */ import javax.ejb.SessionContext;
/*  64:    */ import javax.ejb.Stateless;
/*  65:    */ import javax.ejb.TransactionManagement;
/*  66:    */ import javax.ejb.TransactionManagementType;
/*  67:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  68:    */ 
/*  69:    */ @Stateless
/*  70:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  71:    */ public class ServicioSolicitudCompraImpl
/*  72:    */   extends AbstractServicioAS2
/*  73:    */   implements ServicioSolicitudCompra
/*  74:    */ {
/*  75:    */   private static final long serialVersionUID = 1L;
/*  76:    */   @EJB
/*  77:    */   private SolicitudCompraDao solicitudCompraDao;
/*  78:    */   @EJB
/*  79:    */   private GenericoDao<DetalleSolicitudCompra> detalleSolicitudCompraDao;
/*  80:    */   @EJB
/*  81:    */   private ServicioSecuencia servicioSecuencia;
/*  82:    */   @EJB
/*  83:    */   private ServicioEmpleado servicioEmpleado;
/*  84:    */   @EJB
/*  85:    */   private ServicioProducto servicioProducto;
/*  86:    */   @EJB
/*  87:    */   private transient ProductoUltimaCompraDao productoUltimaCompraDao;
/*  88:    */   @EJB
/*  89:    */   private ServicioEmpresa servicioEmpresa;
/*  90:    */   @EJB
/*  91:    */   private ServicioGenerico<DetalleSolicitudCompraProveedor> servicioDetalleSolicitudCompraProveedor;
/*  92:    */   @EJB
/*  93:    */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  94:    */   @EJB
/*  95:    */   private ServicioDocumento servicioDocumento;
/*  96:    */   @EJB
/*  97:    */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  98:    */   @EJB
/*  99:    */   private ServicioEnvioEmail servicioEnvioEmail;
/* 100:    */   @EJB
/* 101:    */   private ServicioOrganizacion servicioOrganizacion;
/* 102:    */   @EJB
/* 103:    */   private transient ProveedorDao proveedorDao;
/* 104:    */   
/* 105:    */   public void guardar(SolicitudCompra solicitudCompra)
/* 106:    */     throws ExcepcionAS2, AS2Exception
/* 107:    */   {
/* 108:108 */     validar(solicitudCompra);
/* 109:109 */     cargarSecuencia(solicitudCompra);
/* 110:110 */     for (DetalleSolicitudCompra dsc : solicitudCompra.getListaDetalleSolicitudCompra())
/* 111:    */     {
/* 112:111 */       dsc.setEmpleado(solicitudCompra.getEmpleado());
/* 113:112 */       if (!dsc.isEliminadoDesdeConsolidacion()) {
/* 114:113 */         this.detalleSolicitudCompraDao.guardar(dsc);
/* 115:    */       }
/* 116:116 */       for (DetalleSolicitudCompra dsc2 : dsc.getListaDetalleSolicitudCompra())
/* 117:    */       {
/* 118:117 */         if (dsc2.isEliminadoDesdeConsolidacion()) {
/* 119:118 */           dsc2.setDetalleSolicitudCompraPadre(null);
/* 120:    */         } else {
/* 121:120 */           dsc2.setDetalleSolicitudCompraPadre(dsc);
/* 122:    */         }
/* 123:122 */         this.detalleSolicitudCompraDao.guardar(dsc2);
/* 124:    */       }
/* 125:124 */       if (dsc.isEliminadoDesdeConsolidacion())
/* 126:    */       {
/* 127:125 */         dsc.setListaDetalleSolicitudCompra(new ArrayList());
/* 128:126 */         dsc.setSolicitudCompra(null);
/* 129:127 */         dsc.setEliminado(true);
/* 130:128 */         this.detalleSolicitudCompraDao.guardar(dsc);
/* 131:    */       }
/* 132:    */     }
/* 133:132 */     for (DetalleSolicitudCompraProveedor det : solicitudCompra.getListaDetalleSolicitudCompraProveedor()) {
/* 134:133 */       this.servicioDetalleSolicitudCompraProveedor.guardar(det);
/* 135:    */     }
/* 136:135 */     this.solicitudCompraDao.guardar(solicitudCompra);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void aprobar(SolicitudCompra solicitudCompra)
/* 140:    */   {
/* 141:142 */     aprobar(solicitudCompra, false);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void aprobar(SolicitudCompra solicitudCompra, boolean reversar)
/* 145:    */   {
/* 146:147 */     if (!reversar)
/* 147:    */     {
/* 148:148 */       solicitudCompra.setEstado(EstadoSolicitudCompraEnum.APROBADO);
/* 149:149 */       this.solicitudCompraDao.guardar(solicitudCompra);
/* 150:150 */       if (!solicitudCompra.isIndicadorConsolidado()) {
/* 151:    */         try
/* 152:    */         {
/* 153:152 */           enviarEmail(solicitudCompra);
/* 154:    */         }
/* 155:    */         catch (Exception e)
/* 156:    */         {
/* 157:154 */           System.out.println("Error al enviar el mail");
/* 158:155 */           e.printStackTrace();
/* 159:    */         }
/* 160:    */       }
/* 161:    */     }
/* 162:159 */     else if (solicitudCompra.getEstado().equals(EstadoSolicitudCompraEnum.APROBADO))
/* 163:    */     {
/* 164:160 */       solicitudCompra.setEstado(EstadoSolicitudCompraEnum.ELABORADO);
/* 165:161 */       this.solicitudCompraDao.guardar(solicitudCompra);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   private void validar(SolicitudCompra solicitudCompra)
/* 170:    */     throws ExcepcionAS2
/* 171:    */   {
/* 172:166 */     if ((solicitudCompra.getEmpleado() == null) && (!solicitudCompra.isIndicadorConsolidado())) {
/* 173:167 */       throw new ExcepcionAS2("msg_info_seleccionar_empleado");
/* 174:    */     }
/* 175:169 */     boolean tieneRegistros = false;
/* 176:171 */     for (DetalleSolicitudCompra det : solicitudCompra.getListaDetalleSolicitudCompra()) {
/* 177:172 */       if (!det.isEliminado())
/* 178:    */       {
/* 179:173 */         tieneRegistros = true;
/* 180:174 */         if ((!solicitudCompra.isIndicadorConsolidado()) && 
/* 181:175 */           (BigDecimal.ZERO.compareTo(det.getCantidadOriginal()) == 0)) {
/* 182:176 */           throw new ExcepcionAS2("msg_error_cantidad_0", "");
/* 183:    */         }
/* 184:    */       }
/* 185:    */     }
/* 186:181 */     if (!tieneRegistros) {
/* 187:182 */       throw new ExcepcionAS2("msg_error_detalles_vacios");
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void enviarEmail(SolicitudCompra solicitudCompra)
/* 192:    */   {
/* 193:189 */     enviarEmail(solicitudCompra, null);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void enviarEmail(SolicitudCompra solicitudCompra, String emails)
/* 197:    */   {
/* 198:194 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 199:195 */     List<Contacto> listaContactos = this.solicitudCompraDao.getContactosSolicitudCompra(solicitudCompra.getSucursal());
/* 200:197 */     if (listaContactos.size() > 0)
/* 201:    */     {
/* 202:198 */       Contacto contacto = (Contacto)listaContactos.get(0);
/* 203:199 */       String bodyText = contacto.getTipoContacto().getTextoCuerpoCorreoSolicitudCompra();
/* 204:200 */       bodyText = bodyText.replaceAll(":numeroSolicitud:", solicitudCompra.getNumero());
/* 205:201 */       bodyText = bodyText.replaceAll(":fechaSolicitud:", sdf.format(solicitudCompra.getFecha()));
/* 206:202 */       bodyText = bodyText.replaceAll(":nombreSolicitante:", solicitudCompra.getEmpleado().getNombreCompleto());
/* 207:203 */       bodyText = bodyText.replaceAll(":identificacionSolicitante:", solicitudCompra.getEmpleado().getEmpresa().getIdentificacion());
/* 208:    */       
/* 209:205 */       Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(solicitudCompra.getIdOrganizacion()));
/* 210:    */       
/* 211:207 */       String asunto = "Solicitud de compra No. " + solicitudCompra.getNumero() + " - " + organizacion.getRazonSocial() + " => " + solicitudCompra.getEmpleado().getNombreCompleto();
/* 212:208 */       if ((emails == null) || (emails.isEmpty())) {
/* 213:209 */         for (Contacto cont : listaContactos)
/* 214:    */         {
/* 215:210 */           if (emails != null)
/* 216:    */           {
/* 217:211 */             emails = emails + ";";
/* 218:212 */             emails = emails + cont.getEmail1();
/* 219:    */           }
/* 220:    */           else
/* 221:    */           {
/* 222:214 */             emails = cont.getEmail1();
/* 223:    */           }
/* 224:216 */           if ((cont.getEmail2() != null) && (!cont.getEmail2().isEmpty())) {
/* 225:217 */             emails = emails + ";" + cont.getEmail2();
/* 226:    */           }
/* 227:    */         }
/* 228:    */       }
/* 229:221 */       Object listaDatosReporte = new ArrayList();
/* 230:222 */       listaDatosReporte = getReporteSolicitudCompra(solicitudCompra.getId());
/* 231:223 */       JRDataSource ds = new QueryResultDataSource((List)listaDatosReporte, ReporteSolicitudCompraBean.fields);
/* 232:224 */       this.servicioEnvioEmail.enviarEmailComprobanteNoElectronicos(organizacion, solicitudCompra.getSucursal().getId(), emails, asunto, bodyText, solicitudCompra
/* 233:225 */         .getDocumento().getDocumentoBase(), solicitudCompra.getNumero(), ds, "reporteSolicitudCompra", solicitudCompra
/* 234:226 */         .getUsuarioCreacion());
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getEmails(SolicitudCompra solicitudCompra)
/* 239:    */   {
/* 240:233 */     String mails = "";
/* 241:234 */     List<Contacto> listaContactos = this.solicitudCompraDao.getContactosSolicitudCompra(solicitudCompra.getSucursal());
/* 242:236 */     if (listaContactos.size() > 0)
/* 243:    */     {
/* 244:237 */       Contacto contacto = (Contacto)listaContactos.get(0);
/* 245:238 */       mails = contacto.getEmail1();
/* 246:239 */       if ((contacto.getEmail2() != null) && (!contacto.getEmail2().isEmpty())) {
/* 247:240 */         mails = mails + ";" + contacto.getEmail2();
/* 248:    */       }
/* 249:    */     }
/* 250:243 */     return mails;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<SolicitudCompra> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 254:    */     throws ExcepcionAS2
/* 255:    */   {
/* 256:249 */     return this.solicitudCompraDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public int contarPorCriterio(Map<String, String> filters)
/* 260:    */   {
/* 261:254 */     return this.solicitudCompraDao.contarPorCriterio(filters);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void cargarSecuencia(SolicitudCompra solicitudCompra)
/* 265:    */     throws ExcepcionAS2
/* 266:    */   {
/* 267:258 */     if (solicitudCompra.getNumero().isEmpty())
/* 268:    */     {
/* 269:259 */       String numero = "";
/* 270:260 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(solicitudCompra.getDocumento().getIdDocumento(), solicitudCompra.getFecha());
/* 271:261 */       solicitudCompra.setNumero(numero);
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void cerrarSolicitudCompra(SolicitudCompra solicitudCompra)
/* 276:    */     throws AS2Exception
/* 277:    */   {
/* 278:267 */     boolean cierraSolicitud = true;
/* 279:268 */     String numeroConsolidacion = "";
/* 280:269 */     for (DetalleSolicitudCompra det : solicitudCompra.getListaDetalleSolicitudCompra()) {
/* 281:270 */       if ((det.getCantidad().compareTo(BigDecimal.ZERO) != 0) && (det.getDetalleSolicitudCompraPadre() != null))
/* 282:    */       {
/* 283:271 */         cierraSolicitud = false;
/* 284:272 */         numeroConsolidacion = det.getDetalleSolicitudCompraPadre().getSolicitudCompra().getNumero();
/* 285:273 */         break;
/* 286:    */       }
/* 287:    */     }
/* 288:276 */     if (cierraSolicitud)
/* 289:    */     {
/* 290:277 */       solicitudCompra.setEstado(EstadoSolicitudCompraEnum.CERRADO);
/* 291:278 */       this.solicitudCompraDao.guardar(solicitudCompra);
/* 292:    */     }
/* 293:    */     else
/* 294:    */     {
/* 295:280 */       throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioSolicitudCompraImpl.MENSAJE_ERROR_CANTIDAD_POR_APROBAR", new String[] { numeroConsolidacion });
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void cerrarConsolidacion(int idSolicitudCompra)
/* 300:    */   {
/* 301:286 */     this.solicitudCompraDao.cerrarConsolidacion(idSolicitudCompra);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public SolicitudCompra cargarDetalle(int idSolicitudCompra)
/* 305:    */   {
/* 306:291 */     return this.solicitudCompraDao.cargarDetalle(idSolicitudCompra);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public List<Empleado> autocompletarEmpleado(String consulta, HashMap<String, String> filtros)
/* 310:    */   {
/* 311:297 */     filtros.put("activo", "true");
/* 312:298 */     filtros.put("nombres", consulta.trim());
/* 313:299 */     filtros.put("apellidos", consulta.trim());
/* 314:300 */     return this.servicioEmpleado.obtenerListaComboMultiple("nombres", true, filtros);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public List<Producto> autocompletarProducto(String consulta, HashMap<String, String> filtros)
/* 318:    */   {
/* 319:305 */     filtros.put("activo", "true");
/* 320:306 */     filtros.put("indicadorCompra", "true");
/* 321:307 */     filtros.put("codigo", consulta.trim());
/* 322:308 */     filtros.put("nombre", consulta.trim());
/* 323:309 */     return this.servicioProducto.obtenerListaComboMultiple("codigo", true, filtros);
/* 324:    */   }
/* 325:    */   
/* 326:    */   public SolicitudCompra consolidarProducto(int idOrganizacion, List<CategoriaProducto> listCategoriaProducto, List<Producto> listaProducto, List<Empleado> listaEmpleado, SolicitudCompra solicitudCompra, Date fechaDesde, Date fechaHasta, List<SolicitudCompra> listaSolicitudCompra)
/* 327:    */   {
/* 328:316 */     solicitudCompra.setIndicadorConsolidado(true);
/* 329:    */     
/* 330:318 */     List<DetalleSolicitudCompra> listaDetalleSolicitudCompra = this.solicitudCompraDao.getConsolidarSolicitudCompra(idOrganizacion, listCategoriaProducto, listaProducto, listaEmpleado, fechaDesde, fechaHasta, listaSolicitudCompra);
/* 331:    */     
/* 332:320 */     TreeMap<String, DetalleSolicitudCompra> hmDetalleSolicitudCompraConsolidada = new TreeMap();
/* 333:321 */     TreeMap<String, DetalleSolicitudCompra> hmDetalleSolicitudCompraHijasConsolidada = new TreeMap();
/* 334:323 */     if (solicitudCompra.getIdSolicitudCompra() != 0) {
/* 335:324 */       solicitudCompra = this.solicitudCompraDao.cargarDetalle(solicitudCompra.getIdSolicitudCompra());
/* 336:    */     }
/* 337:326 */     if (solicitudCompra.getListaDetalleSolicitudCompra().size() > 0) {
/* 338:327 */       for (DetalleSolicitudCompra dsc : solicitudCompra.getListaDetalleSolicitudCompra())
/* 339:    */       {
/* 340:328 */         hmDetalleSolicitudCompraConsolidada.put(dsc.getProducto().getCodigo(), dsc);
/* 341:329 */         for (DetalleSolicitudCompra detH : dsc.getListaDetalleSolicitudCompra()) {
/* 342:331 */           hmDetalleSolicitudCompraHijasConsolidada.put(detH.getProducto().getId() + "~" + detH.getEmpleado().getId() + "~" + detH.getSolicitudCompra().getId(), detH);
/* 343:    */         }
/* 344:    */       }
/* 345:    */     }
/* 346:336 */     for (DetalleSolicitudCompra dsc : listaDetalleSolicitudCompra)
/* 347:    */     {
/* 348:337 */       this.detalleSolicitudCompraDao.detach(dsc);
/* 349:340 */       if (!hmDetalleSolicitudCompraHijasConsolidada.containsKey(dsc.getProducto().getId() + "~" + dsc.getEmpleado().getId() + "~" + dsc.getSolicitudCompra().getId()))
/* 350:    */       {
/* 351:341 */         DetalleSolicitudCompra dscAux = (DetalleSolicitudCompra)hmDetalleSolicitudCompraConsolidada.get(dsc.getProducto().getCodigo());
/* 352:342 */         if ((dscAux != null) && (!dscAux.isIndicadorEnPedido()))
/* 353:    */         {
/* 354:343 */           dsc.setDetalleSolicitudCompraPadre(dscAux);
/* 355:344 */           dscAux.getListaDetalleSolicitudCompra().add(dsc);
/* 356:    */         }
/* 357:    */         else
/* 358:    */         {
/* 359:347 */           d = new DetalleSolicitudCompra();
/* 360:348 */           d.setIndicadorAgrupado(true);
/* 361:349 */           d.setSolicitudCompra(solicitudCompra);
/* 362:350 */           d.setIdOrganizacion(dsc.getIdOrganizacion());
/* 363:351 */           d.setIdSucursal(dsc.getSolicitudCompra().getSucursal().getIdSucursal());
/* 364:352 */           d.setCantidad(BigDecimal.ZERO);
/* 365:353 */           d.setProducto(dsc.getProducto());
/* 366:354 */           d.setEmpleado(new Empleado());
/* 367:355 */           d.setUnidadCompra(dsc.getUnidadCompra());
/* 368:356 */           dsc.setDetalleSolicitudCompraPadre(d);
/* 369:357 */           d.getListaDetalleSolicitudCompra().add(dsc);
/* 370:358 */           solicitudCompra.getListaDetalleSolicitudCompra().add(d);
/* 371:359 */           hmDetalleSolicitudCompraConsolidada.put(dsc.getProducto().getCodigo(), d);
/* 372:    */         }
/* 373:    */       }
/* 374:    */     }
/* 375:    */     DetalleSolicitudCompra d;
/* 376:364 */     Object lista = new ArrayList();
/* 377:365 */     ((List)lista).addAll(hmDetalleSolicitudCompraConsolidada.values());
/* 378:366 */     Set<Integer> listaIdsSolicitudCompra = new HashSet();
/* 379:367 */     String nota = solicitudCompra.getDescripcion() != null ? solicitudCompra.getDescripcion() : "";
/* 380:368 */     for (DetalleSolicitudCompra det : solicitudCompra.getListaDetalleSolicitudCompra()) {
/* 381:369 */       for (DetalleSolicitudCompra d : det.getListaDetalleSolicitudCompra()) {
/* 382:370 */         if ((!listaIdsSolicitudCompra.contains(Integer.valueOf(d.getSolicitudCompra().getId()))) && 
/* 383:371 */           (d.getSolicitudCompra().getDescripcion() != null) && (!d.getSolicitudCompra().getDescripcion().isEmpty()) && 
/* 384:372 */           (!nota.contains(d.getSolicitudCompra().getDescripcion()))) {
/* 385:373 */           nota = nota + "" + d.getSolicitudCompra().getDescripcion() + ",";
/* 386:    */         }
/* 387:    */       }
/* 388:    */     }
/* 389:377 */     solicitudCompra.setDescripcion(nota);
/* 390:378 */     return solicitudCompra;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void crearDetalleProveedor(SolicitudCompra solicitudCompra, List<ProductoUltimaCompra> listProveedores, DetalleSolicitudCompra detalleSeleccionado)
/* 394:    */     throws AS2Exception, ExcepcionAS2
/* 395:    */   {
/* 396:    */     try
/* 397:    */     {
/* 398:386 */       BigDecimal validator = BigDecimal.ZERO;
/* 399:387 */       for (Iterator localIterator = listProveedores.iterator(); localIterator.hasNext();)
/* 400:    */       {
/* 401:387 */         productoUltimaCompra = (ProductoUltimaCompra)localIterator.next();
/* 402:388 */         if (productoUltimaCompra.getEmpresa().isIndicadorProveedorSeleccionado()) {
/* 403:389 */           validator = validator.add(productoUltimaCompra.getCantidad());
/* 404:    */         }
/* 405:    */       }
/* 406:396 */       hasDetalles = new HashMap();
/* 407:397 */       for (DetalleSolicitudCompraProveedor dscp : solicitudCompra.getListaDetalleSolicitudCompraProveedor()) {
/* 408:398 */         if (!dscp.isIndicadorEnPedido()) {
/* 409:399 */           ((Map)hasDetalles).put(dscp.getEmpresa().getId() + "~" + dscp.getProducto().getId(), dscp);
/* 410:    */         }
/* 411:    */       }
/* 412:403 */       for (ProductoUltimaCompra puc : listProveedores)
/* 413:    */       {
/* 414:404 */         DetalleSolicitudCompraProveedor dscp = (DetalleSolicitudCompraProveedor)((Map)hasDetalles).get(puc.getEmpresa().getId() + "~" + detalleSeleccionado.getProducto().getId());
/* 415:405 */         if ((puc.getEmpresa().isIndicadorProveedorSeleccionado()) && (BigDecimal.ZERO.compareTo(puc.getCantidad()) != 0))
/* 416:    */         {
/* 417:407 */           if (dscp == null)
/* 418:    */           {
/* 419:408 */             dscp = new DetalleSolicitudCompraProveedor();
/* 420:409 */             dscp.setIdOrganizacion(solicitudCompra.getIdOrganizacion());
/* 421:410 */             dscp.setIdSucursal(solicitudCompra.getSucursal().getId());
/* 422:411 */             solicitudCompra.getListaDetalleSolicitudCompraProveedor().add(dscp);
/* 423:    */           }
/* 424:413 */           dscp.setCantidad(puc.getCantidad());
/* 425:414 */           dscp.setPrecio(puc.getPrecioPactado());
/* 426:415 */           dscp.setTotal(dscp.getCantidad().multiply(dscp.getPrecio()));
/* 427:416 */           dscp.setSolicitudCompra(solicitudCompra);
/* 428:417 */           dscp.setProducto(detalleSeleccionado.getProducto());
/* 429:418 */           dscp.setUnidadCompra(detalleSeleccionado.getUnidadCompra());
/* 430:419 */           dscp.setEmpresa(puc.getEmpresa());
/* 431:420 */           dscp.setIndicadorEnPedido(false);
/* 432:    */         }
/* 433:423 */         if (puc.isIndicadorGuardar()) {
/* 434:424 */           this.productoUltimaCompraDao.guardar(puc);
/* 435:    */         }
/* 436:    */       }
/* 437:    */     }
/* 438:    */     catch (AS2Exception e)
/* 439:    */     {
/* 440:    */       ProductoUltimaCompra productoUltimaCompra;
/* 441:    */       Object hasDetalles;
/* 442:436 */       this.context.setRollbackOnly();
/* 443:437 */       throw e;
/* 444:    */     }
/* 445:    */     catch (Exception e1)
/* 446:    */     {
/* 447:439 */       this.context.setRollbackOnly();
/* 448:440 */       throw new ExcepcionAS2(e1);
/* 449:    */     }
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void crearPedidoProveedor(SolicitudCompra solicitudCompra, List<DetalleSolicitudCompraProveedor> listaDetallesPedidoProveedor, Bodega bodega)
/* 453:    */     throws ExcepcionAS2, AS2Exception
/* 454:    */   {
/* 455:447 */     Map<Integer, List<DetalleSolicitudCompraProveedor>> hashDetallesPedido = new HashMap();
/* 456:448 */     Map<Integer, DetalleSolicitudCompra> hashDetallesSolicitud = new HashMap();
/* 457:449 */     Map<Integer, SolicitudCompra> hashSolicitudes = new HashMap();
/* 458:450 */     for (DetalleSolicitudCompra dsc : solicitudCompra.getListaDetalleSolicitudCompra()) {
/* 459:451 */       hashDetallesSolicitud.put(Integer.valueOf(dsc.getProducto().getId()), dsc);
/* 460:    */     }
/* 461:    */     try
/* 462:    */     {
/* 463:454 */       for (??? = listaDetallesPedidoProveedor.iterator(); ???.hasNext();)
/* 464:    */       {
/* 465:454 */         DetalleSolicitudCompraProveedor dscp = (DetalleSolicitudCompraProveedor)???.next();
/* 466:455 */         if (hashDetallesPedido.containsKey(Integer.valueOf(dscp.getEmpresa().getId())))
/* 467:    */         {
/* 468:456 */           ((List)hashDetallesPedido.get(Integer.valueOf(dscp.getEmpresa().getId()))).add(dscp);
/* 469:    */         }
/* 470:    */         else
/* 471:    */         {
/* 472:458 */           List<DetalleSolicitudCompraProveedor> detalles = new ArrayList();
/* 473:459 */           detalles.add(dscp);
/* 474:460 */           hashDetallesPedido.put(Integer.valueOf(dscp.getEmpresa().getId()), detalles);
/* 475:    */         }
/* 476:    */       }
/* 477:464 */       for (??? = hashDetallesPedido.keySet().iterator(); ???.hasNext();)
/* 478:    */       {
/* 479:464 */         Integer id = (Integer)???.next();
/* 480:465 */         PedidoProveedor pedidoProveedor = crearPedidoProveedor(solicitudCompra, bodega);
/* 481:466 */         pedidoProveedor.setSolicitudCompra(solicitudCompra);
/* 482:467 */         pedidoProveedor.setFechaEntrega(new Date());
/* 483:468 */         pedidoProveedor.setDescripcion(solicitudCompra.getDescripcion());
/* 484:469 */         Empresa empresa = this.servicioEmpresa.cargarDetalle(((DetalleSolicitudCompraProveedor)((List)hashDetallesPedido.get(id)).get(0)).getEmpresa());
/* 485:470 */         pedidoProveedor.setEmpresa(empresa);
/* 486:471 */         pedidoProveedor.setCondicionPago(empresa.getProveedor().getCondicionPago());
/* 487:472 */         pedidoProveedor.setNumeroCuotas(empresa.getProveedor().getNumeroCuotas());
/* 488:473 */         this.servicioPedidoProveedor.cargaDatosProveedor(pedidoProveedor);
/* 489:474 */         cargarDirecciones(pedidoProveedor);
/* 490:475 */         for (Iterator localIterator2 = ((List)hashDetallesPedido.get(id)).iterator(); localIterator2.hasNext();)
/* 491:    */         {
/* 492:475 */           dscp = (DetalleSolicitudCompraProveedor)localIterator2.next();
/* 493:476 */           crearDetallePedidoProveedor(pedidoProveedor, dscp);
/* 494:477 */           dscp.setIndicadorEnPedido(true);
/* 495:478 */           this.servicioDetalleSolicitudCompraProveedor.guardar(dscp);
/* 496:479 */           DetalleSolicitudCompra d = (DetalleSolicitudCompra)hashDetallesSolicitud.get(Integer.valueOf(dscp.getProducto().getId()));
/* 497:480 */           for (DetalleSolicitudCompra dsc : d.getListaDetalleSolicitudCompra())
/* 498:    */           {
/* 499:482 */             if (dsc.getCantidad().subtract(dsc.getCantidadAprobada()).compareTo(BigDecimal.ZERO) == 0)
/* 500:    */             {
/* 501:483 */               dsc.setIndicadorEnPedido(true);
/* 502:484 */               dsc.setCantidad(dsc.getCantidad().subtract(dsc.getCantidadAprobada()));
/* 503:485 */               dsc.setCantidadAprobada(BigDecimal.ZERO);
/* 504:486 */               hashSolicitudes.put(Integer.valueOf(dsc.getSolicitudCompra().getId()), dsc.getSolicitudCompra());
/* 505:    */             }
/* 506:    */             else
/* 507:    */             {
/* 508:488 */               dsc.setIndicadorEnPedido(false);
/* 509:489 */               dsc.setCantidad(dsc.getCantidad().subtract(dsc.getCantidadAprobada()));
/* 510:490 */               dsc.setCantidadAprobada(BigDecimal.ZERO);
/* 511:491 */               hashSolicitudes.put(Integer.valueOf(dsc.getSolicitudCompra().getId()), dsc.getSolicitudCompra());
/* 512:    */             }
/* 513:493 */             this.detalleSolicitudCompraDao.guardar(dsc);
/* 514:    */           }
/* 515:496 */           d.setCantidad(BigDecimal.ZERO);
/* 516:497 */           this.detalleSolicitudCompraDao.guardar(d);
/* 517:    */         }
/* 518:    */         DetalleSolicitudCompraProveedor dscp;
/* 519:499 */         boolean cambiarEstado = true;
/* 520:500 */         for (DetalleSolicitudCompra det : solicitudCompra.getListaDetalleSolicitudCompra()) {
/* 521:501 */           for (DetalleSolicitudCompra dscH : det.getListaDetalleSolicitudCompra()) {
/* 522:502 */             if (!dscH.isIndicadorEnPedido()) {
/* 523:503 */               cambiarEstado = false;
/* 524:    */             }
/* 525:    */           }
/* 526:    */         }
/* 527:508 */         cambiarEstadosSolicitudes(hashSolicitudes);
/* 528:509 */         if (cambiarEstado) {
/* 529:510 */           solicitudCompra.setEstado(EstadoSolicitudCompraEnum.CONSOLIDADO);
/* 530:    */         } else {
/* 531:512 */           solicitudCompra.setEstado(EstadoSolicitudCompraEnum.CONSOLIDADO_PARCIAL);
/* 532:    */         }
/* 533:514 */         this.solicitudCompraDao.guardar(solicitudCompra);
/* 534:515 */         this.servicioPedidoProveedor.totalizar(pedidoProveedor);
/* 535:516 */         this.servicioPedidoProveedor.guardar(pedidoProveedor);
/* 536:    */       }
/* 537:    */     }
/* 538:    */     catch (ExcepcionAS2Compras e)
/* 539:    */     {
/* 540:519 */       this.context.setRollbackOnly();
/* 541:520 */       throw e;
/* 542:    */     }
/* 543:    */     catch (AS2Exception e)
/* 544:    */     {
/* 545:522 */       this.context.setRollbackOnly();
/* 546:523 */       throw e;
/* 547:    */     }
/* 548:    */     catch (ExcepcionAS2 e)
/* 549:    */     {
/* 550:525 */       this.context.setRollbackOnly();
/* 551:526 */       throw e;
/* 552:    */     }
/* 553:    */   }
/* 554:    */   
/* 555:    */   private void cambiarEstadosSolicitudes(Map<Integer, SolicitudCompra> hashSolicitudes)
/* 556:    */   {
/* 557:531 */     for (SolicitudCompra sc : hashSolicitudes.values())
/* 558:    */     {
/* 559:532 */       sc = cargarDetalle(sc.getId());
/* 560:533 */       sc.setEstado(EstadoSolicitudCompraEnum.CONSOLIDADO);
/* 561:534 */       for (DetalleSolicitudCompra dsc : sc.getListaDetalleSolicitudCompra())
/* 562:    */       {
/* 563:535 */         if (!dsc.isIndicadorEnPedido())
/* 564:    */         {
/* 565:536 */           sc.setEstado(EstadoSolicitudCompraEnum.CONSOLIDADO_PARCIAL);
/* 566:537 */           break;
/* 567:    */         }
/* 568:539 */         sc.setEstado(EstadoSolicitudCompraEnum.CERRADO);
/* 569:    */       }
/* 570:542 */       this.solicitudCompraDao.guardar(sc);
/* 571:    */     }
/* 572:    */   }
/* 573:    */   
/* 574:    */   private void crearDetallePedidoProveedor(PedidoProveedor pedidoProveedor, DetalleSolicitudCompraProveedor detalleProveedor)
/* 575:    */   {
/* 576:    */     try
/* 577:    */     {
/* 578:550 */       DetallePedidoProveedor d = new DetallePedidoProveedor();
/* 579:    */       
/* 580:552 */       d.setPedidoProveedor(pedidoProveedor);
/* 581:553 */       d.setCantidad(detalleProveedor.getCantidad());
/* 582:554 */       d.setPrecio(detalleProveedor.getPrecio());
/* 583:555 */       Producto producto = this.servicioProducto.buscarPorCodigo(detalleProveedor.getProducto().getCodigo(), 
/* 584:556 */         AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 585:557 */       d.setProducto(producto);
/* 586:558 */       d.setPedidoProveedor(pedidoProveedor);
/* 587:559 */       actualizarProducto(d, producto);
/* 588:560 */       pedidoProveedor.getListaDetallePedidoProveedor().add(d);
/* 589:    */     }
/* 590:    */     catch (ExcepcionAS2 e)
/* 591:    */     {
/* 592:564 */       e.printStackTrace();
/* 593:    */     }
/* 594:    */   }
/* 595:    */   
/* 596:    */   private void actualizarProducto(DetallePedidoProveedor dpp, Producto producto)
/* 597:    */     throws ExcepcionAS2
/* 598:    */   {
/* 599:571 */     for (ImpuestoProductoPedidoProveedor ippc : dpp.getListaImpuestoProductoPedidoProveedor()) {
/* 600:572 */       ippc.setEliminado(true);
/* 601:    */     }
/* 602:575 */     dpp.setProducto(producto);
/* 603:576 */     dpp.setDescripcion(producto.getNombre());
/* 604:577 */     dpp.setUnidadCompra(producto.getUnidadCompra());
/* 605:578 */     dpp.setIndicadorImpuestos(producto.isIndicadorImpuestos());
/* 606:580 */     if (dpp.isIndicadorImpuestos()) {
/* 607:581 */       obtenerImpuestosProductos(dpp.getProducto(), dpp);
/* 608:    */     }
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void obtenerImpuestosProductos(Producto producto, DetallePedidoProveedor d)
/* 612:    */     throws ExcepcionAS2
/* 613:    */   {
/* 614:    */     try
/* 615:    */     {
/* 616:589 */       producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/* 617:    */       
/* 618:591 */       List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, d.getPedidoProveedor().getFecha());
/* 619:593 */       for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 620:    */       {
/* 621:594 */         ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor = new ImpuestoProductoPedidoProveedor();
/* 622:595 */         impuestoProductoPedidoProveedor.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 623:596 */         impuestoProductoPedidoProveedor.setImpuesto(rangoImpuesto.getImpuesto());
/* 624:597 */         impuestoProductoPedidoProveedor.setDetallePedidoProveedor(d);
/* 625:598 */         d.getListaImpuestoProductoPedidoProveedor().add(impuestoProductoPedidoProveedor);
/* 626:    */       }
/* 627:    */     }
/* 628:    */     catch (ExcepcionAS2 ex)
/* 629:    */     {
/* 630:601 */       throw ex;
/* 631:    */     }
/* 632:    */     catch (Exception e)
/* 633:    */     {
/* 634:603 */       throw new ExcepcionAS2(e);
/* 635:    */     }
/* 636:    */   }
/* 637:    */   
/* 638:    */   private void cargarDirecciones(PedidoProveedor pedidoProveedor)
/* 639:    */   {
/* 640:608 */     List<DireccionEmpresa> listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(pedidoProveedor.getEmpresa().getId());
/* 641:610 */     for (DireccionEmpresa de : listaDireccionEmpresa) {
/* 642:611 */       if (de.isIndicadorDireccionPrincipal())
/* 643:    */       {
/* 644:612 */         pedidoProveedor.setDireccionEmpresa(de);
/* 645:613 */         break;
/* 646:    */       }
/* 647:    */     }
/* 648:    */   }
/* 649:    */   
/* 650:    */   public PedidoProveedor crearPedidoProveedor(SolicitudCompra solicitudCompra, Bodega bodega)
/* 651:    */     throws ExcepcionAS2
/* 652:    */   {
/* 653:620 */     PedidoProveedor pedidoProveedor = new PedidoProveedor();
/* 654:621 */     pedidoProveedor.setSucursal(solicitudCompra.getSucursal());
/* 655:622 */     pedidoProveedor.setIdOrganizacion(solicitudCompra.getIdOrganizacion());
/* 656:623 */     pedidoProveedor.setNumero("");
/* 657:624 */     pedidoProveedor.setFecha(new Date());
/* 658:625 */     pedidoProveedor.setEstado(Estado.ELABORADO);
/* 659:626 */     pedidoProveedor.setBodega(bodega);
/* 660:    */     
/* 661:628 */     Documento documento = null;
/* 662:629 */     List<Documento> list = getListaDocumentoProveedor(solicitudCompra.getIdOrganizacion());
/* 663:630 */     if ((list != null) && (!list.isEmpty()))
/* 664:    */     {
/* 665:632 */       for (Documento d : list)
/* 666:    */       {
/* 667:633 */         if (d.isPredeterminado())
/* 668:    */         {
/* 669:634 */           documento = d;
/* 670:635 */           break;
/* 671:    */         }
/* 672:637 */         documento = new Documento();
/* 673:    */       }
/* 674:640 */       pedidoProveedor.setDocumento(documento);
/* 675:641 */       if (documento.getId() != 0)
/* 676:    */       {
/* 677:642 */         Documento d = this.servicioDocumento.buscarPorId(Integer.valueOf(pedidoProveedor.getDocumento().getId()));
/* 678:643 */         pedidoProveedor.setDocumento(d);
/* 679:    */       }
/* 680:    */     }
/* 681:646 */     return pedidoProveedor;
/* 682:    */   }
/* 683:    */   
/* 684:    */   private List<Documento> getListaDocumentoProveedor(int idOrganizacion)
/* 685:    */     throws ExcepcionAS2
/* 686:    */   {
/* 687:    */     try
/* 688:    */     {
/* 689:652 */       listaDocumentoProveedor = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PEDIDO_PROVEEDOR, idOrganizacion);
/* 690:    */     }
/* 691:    */     catch (ExcepcionAS2 e)
/* 692:    */     {
/* 693:    */       List<Documento> listaDocumentoProveedor;
/* 694:654 */       throw e;
/* 695:    */     }
/* 696:    */     List<Documento> listaDocumentoProveedor;
/* 697:657 */     return listaDocumentoProveedor;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public SolicitudCompra copiar(SolicitudCompra solicitudCompraACopiar, boolean copiarTodo, Sucursal sucursal)
/* 701:    */   {
/* 702:662 */     SolicitudCompra solicitudCompra = new SolicitudCompra();
/* 703:663 */     solicitudCompra.setFecha(new Date());
/* 704:664 */     solicitudCompra.setEmpleado(solicitudCompraACopiar.getEmpleado());
/* 705:665 */     solicitudCompra.setDocumento(solicitudCompraACopiar.getDocumento());
/* 706:666 */     solicitudCompra.setEstado(EstadoSolicitudCompraEnum.ELABORADO);
/* 707:667 */     solicitudCompra.setIdOrganizacion(solicitudCompraACopiar.getIdOrganizacion());
/* 708:668 */     solicitudCompra.setSucursal(sucursal);
/* 709:669 */     solicitudCompra.setDescripcion(solicitudCompraACopiar.getDescripcion());
/* 710:671 */     for (DetalleSolicitudCompra det : solicitudCompraACopiar.getListaDetalleSolicitudCompra())
/* 711:    */     {
/* 712:672 */       BigDecimal cantidadASolicitar = copiarTodo ? det.getCantidadOriginal() : det.getCantidad();
/* 713:673 */       if (cantidadASolicitar.compareTo(BigDecimal.ZERO) != 0)
/* 714:    */       {
/* 715:674 */         DetalleSolicitudCompra detalleSolicitudCompra = new DetalleSolicitudCompra();
/* 716:675 */         detalleSolicitudCompra.setCantidadOriginal(cantidadASolicitar);
/* 717:676 */         detalleSolicitudCompra.setCantidad(cantidadASolicitar);
/* 718:677 */         detalleSolicitudCompra.setDescripcion(det.getDescripcion());
/* 719:678 */         detalleSolicitudCompra.setEmpleado(solicitudCompra.getEmpleado());
/* 720:679 */         detalleSolicitudCompra.setIdOrganizacion(solicitudCompra.getIdOrganizacion());
/* 721:680 */         detalleSolicitudCompra.setIdSucursal(solicitudCompra.getSucursal().getId());
/* 722:681 */         detalleSolicitudCompra.setProducto(det.getProducto());
/* 723:682 */         detalleSolicitudCompra.setSolicitudCompra(solicitudCompra);
/* 724:683 */         detalleSolicitudCompra.setUnidadCompra(det.getUnidadCompra());
/* 725:684 */         solicitudCompra.getListaDetalleSolicitudCompra().add(detalleSolicitudCompra);
/* 726:    */       }
/* 727:    */     }
/* 728:688 */     return solicitudCompra;
/* 729:    */   }
/* 730:    */   
/* 731:    */   public List<Object[]> getReporteSolicitudCompra(int idSolicitudCompra)
/* 732:    */   {
/* 733:693 */     return this.solicitudCompraDao.getReporteSolicitudCompra(idSolicitudCompra);
/* 734:    */   }
/* 735:    */   
/* 736:    */   public List<Object[]> getReporteConsolidacionCompra(SolicitudCompra solicitudCompra)
/* 737:    */   {
/* 738:698 */     List<Object[]> result = new ArrayList();
/* 739:699 */     Date fechaSolicitud = solicitudCompra.getFecha();
/* 740:700 */     String numeroSolicitud = solicitudCompra.getNumero();
/* 741:701 */     EstadoSolicitudCompraEnum estadoSolicitud = solicitudCompra.getEstado();
/* 742:702 */     String sucursal = solicitudCompra.getSucursal().getNombre();
/* 743:703 */     String descripcionSolicitud = solicitudCompra.getDescripcion();
/* 744:708 */     for (DetalleSolicitudCompra det : solicitudCompra.getListaDetalleSolicitudCompra())
/* 745:    */     {
/* 746:709 */       codigoProducto = det.getProducto().getCodigo();
/* 747:710 */       nombreProducto = det.getProducto().getNombre();
/* 748:711 */       nombreUnidad = det.getUnidadCompra().getNombre();
/* 749:712 */       Object[] obj = new Object[14];
/* 750:713 */       obj[0] = fechaSolicitud;
/* 751:714 */       obj[1] = numeroSolicitud;
/* 752:715 */       obj[2] = descripcionSolicitud;
/* 753:716 */       obj[3] = "";
/* 754:717 */       obj[4] = estadoSolicitud;
/* 755:718 */       obj[5] = sucursal;
/* 756:719 */       obj[6] = codigoProducto;
/* 757:720 */       obj[7] = nombreProducto;
/* 758:721 */       obj[8] = nombreUnidad;
/* 759:722 */       obj[9] = null;
/* 760:723 */       obj[10] = null;
/* 761:724 */       obj[11] = null;
/* 762:725 */       obj[12] = null;
/* 763:726 */       obj[13] = null;
/* 764:727 */       result.add(obj);
/* 765:728 */       for (DetalleSolicitudCompra detH : det.getListaDetalleSolicitudCompra())
/* 766:    */       {
/* 767:729 */         Object[] objH = new Object[14];
/* 768:730 */         objH[0] = fechaSolicitud;
/* 769:731 */         objH[1] = numeroSolicitud;
/* 770:732 */         objH[2] = descripcionSolicitud;
/* 771:733 */         objH[3] = "";
/* 772:734 */         objH[4] = estadoSolicitud;
/* 773:735 */         objH[5] = sucursal;
/* 774:736 */         objH[6] = codigoProducto;
/* 775:737 */         objH[7] = nombreProducto;
/* 776:738 */         objH[8] = nombreUnidad;
/* 777:739 */         objH[9] = detH.getCantidad();
/* 778:740 */         objH[10] = detH.getCantidadOriginal();
/* 779:741 */         objH[11] = detH.getDescripcion();
/* 780:742 */         objH[12] = null;
/* 781:743 */         objH[13] = detH.getEmpleado().getNombreCompleto();
/* 782:744 */         result.add(objH);
/* 783:    */       }
/* 784:    */     }
/* 785:    */     String codigoProducto;
/* 786:    */     String nombreProducto;
/* 787:    */     String nombreUnidad;
/* 788:747 */     return result;
/* 789:    */   }
/* 790:    */   
/* 791:    */   public List<Object[]> getReporteProductosConsolidados(int idSolicitudCompra)
/* 792:    */   {
/* 793:752 */     return this.solicitudCompraDao.getReporteProductosConsolidados(idSolicitudCompra);
/* 794:    */   }
/* 795:    */   
/* 796:    */   public List<SolicitudCompra> autocompletarSolicitudCompra(String consulta, HashMap<String, String> filtros)
/* 797:    */   {
/* 798:757 */     return this.solicitudCompraDao.obtenerListaCombo("numero", true, filtros);
/* 799:    */   }
/* 800:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.impl.ServicioSolicitudCompraImpl
 * JD-Core Version:    0.7.0.1
 */