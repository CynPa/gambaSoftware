/*   1:    */ package com.asinfo.as2.financiero.SRI.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.sri.AutorizacionAutoimpresorSRIDao;
/*   4:    */ import com.asinfo.as2.dao.sri.AutorizacionDocumentoAutoimpresorSRIDao;
/*   5:    */ import com.asinfo.as2.dao.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRIDao;
/*   6:    */ import com.asinfo.as2.dao.sri.AutorizacionPuntoDeVentaAutoimpresorSRIDao;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*  11:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoAutoimpresorSRI;
/*  12:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  13:    */ import com.asinfo.as2.entities.sri.AutorizacionPuntoDeVentaAutoimpresorSRI;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
/*  16:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  21:    */ import java.io.File;
/*  22:    */ import java.text.SimpleDateFormat;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.ejb.Stateless;
/*  29:    */ import javax.xml.parsers.DocumentBuilder;
/*  30:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  31:    */ import javax.xml.transform.Result;
/*  32:    */ import javax.xml.transform.Transformer;
/*  33:    */ import javax.xml.transform.TransformerFactory;
/*  34:    */ import javax.xml.transform.dom.DOMSource;
/*  35:    */ import javax.xml.transform.stream.StreamResult;
/*  36:    */ import org.w3c.dom.Document;
/*  37:    */ import org.w3c.dom.Element;
/*  38:    */ 
/*  39:    */ @Stateless
/*  40:    */ public class ServicioAutorizacionAutoimpresorSRIImpl
/*  41:    */   implements ServicioAutorizacionAutoimpresorSRI
/*  42:    */ {
/*  43:    */   @EJB
/*  44:    */   private AutorizacionAutoimpresorSRIDao autorizacionAutoimpresorSRIDao;
/*  45:    */   @EJB
/*  46:    */   private AutorizacionDocumentoAutoimpresorSRIDao autorizacionDocumentoAutoimpresorSRIDao;
/*  47:    */   @EJB
/*  48:    */   private AutorizacionPuntoDeVentaAutoimpresorSRIDao autorizacionPuntoDeVentaAutoimpresorSRIDao;
/*  49:    */   @EJB
/*  50:    */   private AutorizacionDocumentoPuntoDeVentaAutoimpresorSRIDao autorizacionDocumentoPuntoDeVentaAutoimpresorSRIDao;
/*  51:    */   
/*  52:    */   public void guardar(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI, Date fechaProceso)
/*  53:    */     throws ExcepcionAS2Financiero
/*  54:    */   {
/*  55: 78 */     validar(autorizacionAutoimpresorSRI, procesoAutoimpresorSRI);
/*  56: 80 */     for (AutorizacionDocumentoAutoimpresorSRI a : autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI()) {
/*  57: 81 */       this.autorizacionDocumentoAutoimpresorSRIDao.guardar(a);
/*  58:    */     }
/*  59: 83 */     for (AutorizacionPuntoDeVentaAutoimpresorSRI a : autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI()) {
/*  60: 84 */       this.autorizacionPuntoDeVentaAutoimpresorSRIDao.guardar(a);
/*  61:    */     }
/*  62: 86 */     for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI a : autorizacionAutoimpresorSRI
/*  63: 87 */       .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI())
/*  64:    */     {
/*  65: 88 */       if (!a.isIndicadorImpreso()) {
/*  66: 89 */         a.setNumeroInicial(a.getNumero());
/*  67:    */       }
/*  68: 91 */       this.autorizacionDocumentoPuntoDeVentaAutoimpresorSRIDao.guardar(a);
/*  69:    */     }
/*  70: 94 */     autorizacionAutoimpresorSRI.setFechaHasta(FuncionesUtiles.sumarFechaAnios(autorizacionAutoimpresorSRI.getFechaDesde(), 1));
/*  71: 95 */     this.autorizacionAutoimpresorSRIDao.guardar(autorizacionAutoimpresorSRI);
/*  72: 97 */     if (autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior() != null)
/*  73:    */     {
/*  74: 98 */       autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior().setActivo(false);
/*  75: 99 */       autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior().setFechaHasta(autorizacionAutoimpresorSRI.getFechaCambioSoftware());
/*  76:100 */       autorizacionAutoimpresorSRI.setListaAutorizacionDocumentoAutoimpresorSRI(null);
/*  77:101 */       autorizacionAutoimpresorSRI.setListaAutorizacionPuntoDeVentaAutoimpresorSRI(null);
/*  78:102 */       autorizacionAutoimpresorSRI.setListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(null);
/*  79:103 */       this.autorizacionAutoimpresorSRIDao.guardar(autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior());
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void validar(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI)
/*  84:    */     throws ExcepcionAS2Financiero
/*  85:    */   {
/*  86:110 */     if (autorizacionAutoimpresorSRI.getAutorizacion().equals("0000000000")) {
/*  87:111 */       throw new ExcepcionAS2Financiero("msg_error_numero_autorizacion_no_valido");
/*  88:    */     }
/*  89:113 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoAutoimpresorSRIEnum[procesoAutoimpresorSRI.ordinal()])
/*  90:    */     {
/*  91:    */     case 1: 
/*  92:115 */       validarAutorizacion(autorizacionAutoimpresorSRI);
/*  93:116 */       break;
/*  94:    */     case 2: 
/*  95:119 */       validarBaja(autorizacionAutoimpresorSRI);
/*  96:120 */       break;
/*  97:    */     case 3: 
/*  98:123 */       validarExclusion(autorizacionAutoimpresorSRI);
/*  99:124 */       break;
/* 100:    */     case 4: 
/* 101:127 */       validarImpresionComprobantes(autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior(), true);
/* 102:    */     case 5: 
/* 103:130 */       validarImpresionComprobantes(autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior(), true);
/* 104:131 */       break;
/* 105:    */     }
/* 106:136 */     if (FuncionesUtiles.DiasEntreFechas(new Date(), autorizacionAutoimpresorSRI.getFechaDesde()) > 1L) {
/* 107:137 */       throw new ExcepcionAS2Financiero("msg_info_fecha_desde_secuencia_autoimpresor");
/* 108:    */     }
/* 109:139 */     if (validarAutorizacionAutoimpresorVigente(autorizacionAutoimpresorSRI, autorizacionAutoimpresorSRI
/* 110:140 */       .getAutorizacionAutoimpresorSRIAnterior() != null ? autorizacionAutoimpresorSRI
/* 111:141 */       .getAutorizacionAutoimpresorSRIAnterior().getId() : 0)) {
/* 112:142 */       throw new ExcepcionAS2Financiero("msg_error_autorizacion_autoimpresor_vigente");
/* 113:    */     }
/* 114:144 */     if (autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI().size() == 0) {
/* 115:145 */       throw new ExcepcionAS2Financiero("msg_info_debe_escoger_documento");
/* 116:    */     }
/* 117:147 */     if (autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI().size() == 0) {
/* 118:148 */       throw new ExcepcionAS2Financiero("msg_info_debe_escoger_punto_emision");
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void validarBaja(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 123:    */     throws ExcepcionAS2Financiero
/* 124:    */   {
/* 125:154 */     validarImpresionComprobantes(autorizacionAutoimpresorSRI, false);
/* 126:    */   }
/* 127:    */   
/* 128:    */   private void validarExclusion(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 129:    */     throws ExcepcionAS2Financiero
/* 130:    */   {
/* 131:158 */     validarImpresionComprobantes(autorizacionAutoimpresorSRI, false);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private void validarImpresionComprobantes(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, boolean activo)
/* 135:    */     throws ExcepcionAS2Financiero
/* 136:    */   {
/* 137:162 */     String mensaje = "";
/* 138:163 */     boolean indicadorError = false;
/* 139:164 */     for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : autorizacionAutoimpresorSRI
/* 140:165 */       .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()) {
/* 141:166 */       if ((!adpv.isIndicadorImpreso()) && (adpv.isActivo() == activo))
/* 142:    */       {
/* 143:168 */         mensaje = mensaje + adpv.getDocumentoBase().getNombre() + "/" + adpv.getPuntoDeVenta().getSucursal().getCodigo() + "-" + adpv.getPuntoDeVenta().getCodigo();
/* 144:169 */         indicadorError = true;
/* 145:    */       }
/* 146:    */     }
/* 147:172 */     if (indicadorError) {
/* 148:173 */       throw new ExcepcionAS2Financiero("msg_error_documento_punto_venta_impreso", mensaje);
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void validarAutorizacion(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 153:    */     throws ExcepcionAS2Financiero
/* 154:    */   {
/* 155:178 */     if ((FuncionesUtiles.DiasEntreFechas(autorizacionAutoimpresorSRI.getFechaDesde(), new Date()) > 30L) && 
/* 156:179 */       (autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior() == null)) {
/* 157:180 */       throw new ExcepcionAS2Financiero("msg_error_fecha_30_dias_actual");
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void eliminar(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 162:    */   {
/* 163:193 */     this.autorizacionAutoimpresorSRIDao.eliminar(autorizacionAutoimpresorSRI);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public AutorizacionAutoimpresorSRI buscarPorId(int idAutorizacionAutoimpresorSRI)
/* 167:    */   {
/* 168:204 */     return (AutorizacionAutoimpresorSRI)this.autorizacionAutoimpresorSRIDao.buscarPorId(Integer.valueOf(idAutorizacionAutoimpresorSRI));
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<AutorizacionAutoimpresorSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean ordenar, Map<String, String> filters)
/* 172:    */   {
/* 173:217 */     return this.autorizacionAutoimpresorSRIDao.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<AutorizacionAutoimpresorSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 177:    */   {
/* 178:222 */     return this.autorizacionAutoimpresorSRIDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int contarPorCriterio(Map<String, String> filters)
/* 182:    */   {
/* 183:233 */     return this.autorizacionAutoimpresorSRIDao.contarPorCriterio(filters);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public AutorizacionAutoimpresorSRI cargarDetalle(int idAutorizacionAutoimpresorSRI)
/* 187:    */   {
/* 188:238 */     return this.autorizacionAutoimpresorSRIDao.cargarDetalle(idAutorizacionAutoimpresorSRI);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> obtenerAutorizacionDocumentoPuntoDeVentaSRI(Date fecha, AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI)
/* 192:    */   {
/* 193:253 */     return this.autorizacionAutoimpresorSRIDao.obtenerAutorizacionDocumentoPuntoDeVentaSRI(fecha, autorizacionAutoimpresorSRI, procesoAutoimpresorSRI);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<Date> obtenerFechaProceso(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI)
/* 197:    */   {
/* 198:266 */     return this.autorizacionAutoimpresorSRIDao.obtenerFechaProceso(autorizacionAutoimpresorSRI, procesoAutoimpresorSRI);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String obtenerSecuencia(AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv)
/* 202:    */     throws ExcepcionAS2Financiero
/* 203:    */   {
/* 204:279 */     adpv = (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)this.autorizacionDocumentoPuntoDeVentaAutoimpresorSRIDao.buscarPorId(Integer.valueOf(adpv.getId()));
/* 205:280 */     String numeroDocumento = "";
/* 206:281 */     if (adpv.isActivo())
/* 207:    */     {
/* 208:282 */       numeroDocumento = adpv.getPuntoDeVenta().getSucursal().getCodigo() + "-" + adpv.getPuntoDeVenta().getCodigo() + "-";
/* 209:283 */       String numero = String.valueOf(adpv.getNumero());
/* 210:284 */       numeroDocumento = numeroDocumento + FuncionesUtiles.completarALaIzquierda('0', 9 - numero.length(), numero);
/* 211:    */     }
/* 212:286 */     return numeroDocumento;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI obtenerAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(DocumentoBase documentoBase, PuntoDeVenta puntoDeVenta)
/* 216:    */     throws ExcepcionAS2Financiero
/* 217:    */   {
/* 218:292 */     return this.autorizacionAutoimpresorSRIDao.obtenerSecuencia(documentoBase, puntoDeVenta);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void actualizaSecuencia(AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI autorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
/* 222:    */   {
/* 223:299 */     autorizacionDocumentoPuntoDeVentaAutoimpresorSRI = (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)this.autorizacionDocumentoPuntoDeVentaAutoimpresorSRIDao.buscarPorId(Integer.valueOf(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getId()));
/* 224:300 */     if (!autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.isIndicadorImpreso()) {
/* 225:301 */       autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.setIndicadorImpreso(true);
/* 226:    */     }
/* 227:303 */     autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.setNumero(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getNumero() + 1);
/* 228:304 */     this.autorizacionDocumentoPuntoDeVentaAutoimpresorSRIDao.guardar(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI);
/* 229:    */   }
/* 230:    */   
/* 231:    */   private boolean validarAutorizacionAutoimpresorVigente(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, int idAutorizacionAutoimpresorSRIAnterior)
/* 232:    */   {
/* 233:310 */     List<AutorizacionAutoimpresorSRI> lista = new ArrayList();
/* 234:311 */     for (AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRITmp : this.autorizacionAutoimpresorSRIDao.obtenerAutorizacionVigente(autorizacionAutoimpresorSRI
/* 235:312 */       .getIdOrganizacion(), autorizacionAutoimpresorSRI.getFechaDesde(), idAutorizacionAutoimpresorSRIAnterior)) {
/* 236:313 */       if ((autorizacionAutoimpresorSRITmp.getIdAutorizacionAutoimpresorSRI() != autorizacionAutoimpresorSRI.getIdAutorizacionAutoimpresorSRI()) && 
/* 237:314 */         (autorizacionAutoimpresorSRITmp.isActivo())) {
/* 238:315 */         lista.add(autorizacionAutoimpresorSRITmp);
/* 239:    */       }
/* 240:    */     }
/* 241:319 */     return lista.size() > 0;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void generarXMLProceso(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI, Date fechaProceso)
/* 245:    */     throws ExcepcionAS2Financiero
/* 246:    */   {
/* 247:327 */     autorizacionAutoimpresorSRI = cargarDetalle(autorizacionAutoimpresorSRI.getId());
/* 248:    */     try
/* 249:    */     {
/* 250:330 */       SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
/* 251:    */       
/* 252:332 */       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 253:333 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 254:    */       
/* 255:335 */       Document doc = docBuilder.newDocument();
/* 256:    */       
/* 257:337 */       Element autorizacion = doc.createElement("autorizacion");
/* 258:338 */       doc.appendChild(autorizacion);
/* 259:    */       
/* 260:    */ 
/* 261:341 */       Element codTipoTra = doc.createElement("codTipoTra");
/* 262:342 */       codTipoTra.appendChild(doc.createTextNode(procesoAutoimpresorSRI.getCodigo()));
/* 263:343 */       autorizacion.appendChild(codTipoTra);
/* 264:    */       
/* 265:    */ 
/* 266:346 */       Element ruc = doc.createElement("ruc");
/* 267:347 */       ruc.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getIdentificacion()));
/* 268:348 */       autorizacion.appendChild(ruc);
/* 269:350 */       if ((procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.AUTORIZACION)) || 
/* 270:351 */         (procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.INCLUSION)) || 
/* 271:352 */         (procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.BAJA)))
/* 272:    */       {
/* 273:354 */         Element numAut = doc.createElement("numAut");
/* 274:355 */         numAut.appendChild(doc.createTextNode(autorizacionAutoimpresorSRI.getAutorizacion()));
/* 275:356 */         autorizacion.appendChild(numAut);
/* 276:    */       }
/* 277:359 */       Element fecha = doc.createElement("fecha");
/* 278:360 */       fecha.appendChild(doc.createTextNode(formatoFecha.format(fechaProceso)));
/* 279:361 */       autorizacion.appendChild(fecha);
/* 280:363 */       if ((procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.CAMBIO_SOFTWARE)) || 
/* 281:364 */         (procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.RENOVACION)))
/* 282:    */       {
/* 283:366 */         Element autOld = doc.createElement("autOld");
/* 284:367 */         autOld.appendChild(doc.createTextNode(autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior().getAutorizacion()));
/* 285:368 */         autorizacion.appendChild(autOld);
/* 286:    */         
/* 287:370 */         Element autNew = doc.createElement("autNew");
/* 288:371 */         autNew.appendChild(doc.createTextNode(autorizacionAutoimpresorSRI.getAutorizacion()));
/* 289:372 */         autorizacion.appendChild(autNew);
/* 290:    */       }
/* 291:377 */       Element detalles = doc.createElement("detalles");
/* 292:378 */       autorizacion.appendChild(detalles);
/* 293:379 */       String mensaje = "";
/* 294:380 */       boolean indicadorError = false;
/* 295:381 */       String documentoBaseString = "";
/* 296:382 */       for (AutorizacionDocumentoAutoimpresorSRI autorizacionDocumentoAutoimpresorSRI : autorizacionAutoimpresorSRI
/* 297:383 */         .getListaAutorizacionDocumentoAutoimpresorSRI())
/* 298:    */       {
/* 299:384 */         documentoBase = autorizacionDocumentoAutoimpresorSRI.getDocumentoBase();
/* 300:385 */         switch (documentoBase)
/* 301:    */         {
/* 302:    */         case FACTURA_CLIENTE: 
/* 303:387 */           documentoBaseString = "1";
/* 304:388 */           break;
/* 305:    */         case NOTA_CREDITO_CLIENTE: 
/* 306:390 */           documentoBaseString = "4";
/* 307:    */         }
/* 308:394 */         for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI autorizacionDocumentoPuntoDeVentaAutoimpresorSRI : autorizacionAutoimpresorSRI
/* 309:395 */           .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()) {
/* 310:396 */           if (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getDocumentoBase() == documentoBase) {
/* 311:398 */             if (((procesoAutoimpresorSRI == ProcesoAutoimpresorSRIEnum.AUTORIZACION) && 
/* 312:399 */               (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.isIndicadorNuevo())) || ((procesoAutoimpresorSRI == ProcesoAutoimpresorSRIEnum.INCLUSION) && 
/* 313:    */               
/* 314:401 */               (!autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.isIndicadorNuevo()) && 
/* 315:402 */               (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getFechaInclusion() != null) && 
/* 316:403 */               (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getFechaInclusion().equals(fechaProceso))) || ((procesoAutoimpresorSRI == ProcesoAutoimpresorSRIEnum.EXCLUSION) && 
/* 317:    */               
/* 318:405 */               (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getFechaExclusion() != null) && 
/* 319:406 */               (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getFechaExclusion().equals(fechaProceso))) || (procesoAutoimpresorSRI == ProcesoAutoimpresorSRIEnum.BAJA) || (procesoAutoimpresorSRI == ProcesoAutoimpresorSRIEnum.CAMBIO_SOFTWARE))
/* 320:    */             {
/* 321:410 */               if ((!autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.isIndicadorImpreso()) && 
/* 322:411 */                 (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.isActivo()))
/* 323:    */               {
/* 324:415 */                 mensaje = mensaje + autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getDocumentoBase().getNombre() + "/" + autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getPuntoDeVenta().getSucursal().getCodigo() + "-" + autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getPuntoDeVenta().getCodigo();
/* 325:416 */                 indicadorError = true;
/* 326:    */               }
/* 327:419 */               if (((procesoAutoimpresorSRI != ProcesoAutoimpresorSRIEnum.CAMBIO_SOFTWARE) && (procesoAutoimpresorSRI != ProcesoAutoimpresorSRIEnum.BAJA)) || 
/* 328:420 */                 (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getFechaInclusion() == null))
/* 329:    */               {
/* 330:424 */                 Element detalle = doc.createElement("detalle");
/* 331:425 */                 detalles.appendChild(detalle);
/* 332:    */                 
/* 333:    */ 
/* 334:428 */                 Element codDoc = doc.createElement("codDoc");
/* 335:429 */                 codDoc.appendChild(doc.createTextNode(documentoBaseString));
/* 336:430 */                 detalle.appendChild(codDoc);
/* 337:    */                 
/* 338:    */ 
/* 339:433 */                 Element estab = doc.createElement("estab");
/* 340:434 */                 estab.appendChild(doc.createTextNode(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getPuntoDeVenta().getSucursal()
/* 341:435 */                   .getCodigo()));
/* 342:436 */                 detalle.appendChild(estab);
/* 343:    */                 
/* 344:    */ 
/* 345:439 */                 Element ptoEmi = doc.createElement("ptoEmi");
/* 346:440 */                 ptoEmi.appendChild(doc.createTextNode(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getPuntoDeVenta().getCodigo()));
/* 347:441 */                 detalle.appendChild(ptoEmi);
/* 348:443 */                 if ((procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.AUTORIZACION)) || 
/* 349:444 */                   (procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.INCLUSION)))
/* 350:    */                 {
/* 351:446 */                   Element inicio = doc.createElement("inicio");
/* 352:447 */                   inicio.appendChild(doc.createTextNode(String.valueOf(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI
/* 353:448 */                     .getNumeroInicial())));
/* 354:449 */                   detalle.appendChild(inicio);
/* 355:    */                 }
/* 356:451 */                 if ((procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.BAJA)) || 
/* 357:452 */                   (procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.EXCLUSION)))
/* 358:    */                 {
/* 359:454 */                   Element fin = doc.createElement("fin");
/* 360:455 */                   fin.appendChild(doc.createTextNode(String.valueOf(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.getNumero() - 1)));
/* 361:456 */                   detalle.appendChild(fin);
/* 362:    */                 }
/* 363:458 */                 if ((procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.CAMBIO_SOFTWARE)) || 
/* 364:459 */                   (procesoAutoimpresorSRI.equals(ProcesoAutoimpresorSRIEnum.RENOVACION)))
/* 365:    */                 {
/* 366:461 */                   Element finOld = doc.createElement("finOld");
/* 367:462 */                   finOld.appendChild(doc.createTextNode(String.valueOf(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI
/* 368:463 */                     .getNumeroAnterior())));
/* 369:464 */                   detalle.appendChild(finOld);
/* 370:    */                   
/* 371:466 */                   Element iniNew = doc.createElement("iniNew");
/* 372:467 */                   iniNew.appendChild(doc.createTextNode(String.valueOf(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI
/* 373:468 */                     .getNumeroInicial())));
/* 374:469 */                   detalle.appendChild(iniNew);
/* 375:    */                 }
/* 376:    */               }
/* 377:    */             }
/* 378:    */           }
/* 379:    */         }
/* 380:    */       }
/* 381:    */       DocumentoBase documentoBase;
/* 382:477 */       if (!indicadorError)
/* 383:    */       {
/* 384:479 */         TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 385:480 */         Transformer transformer = transformerFactory.newTransformer();
/* 386:481 */         DOMSource source = new DOMSource(doc);
/* 387:482 */         String directorioAnexo = ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getIdOrganizacion()) + File.separator + "AutorizacionesXML";
/* 388:    */         
/* 389:484 */         String nombreArchivo = procesoAutoimpresorSRI.getCodigo() + ".xml";
/* 390:485 */         String rutaArchivo = directorioAnexo + File.separator + nombreArchivo;
/* 391:486 */         Result result = new StreamResult(new File(rutaArchivo));
/* 392:    */         
/* 393:    */ 
/* 394:489 */         transformer.transform(source, result);
/* 395:    */       }
/* 396:    */       else
/* 397:    */       {
/* 398:492 */         throw new ExcepcionAS2Financiero("msg_error_documento_punto_venta_impreso", mensaje);
/* 399:    */       }
/* 400:    */     }
/* 401:    */     catch (ExcepcionAS2Financiero e)
/* 402:    */     {
/* 403:496 */       throw e;
/* 404:    */     }
/* 405:    */     catch (Exception e)
/* 406:    */     {
/* 407:498 */       e.printStackTrace();
/* 408:499 */       throw new ExcepcionAS2Financiero("msg_proceso_erroneo");
/* 409:    */     }
/* 410:    */   }
/* 411:    */   
/* 412:    */   public AutorizacionAutoimpresorSRI obtenerAutorizacionSRIVigente(int idOrganizacion)
/* 413:    */     throws ExcepcionAS2Financiero
/* 414:    */   {
/* 415:506 */     AutorizacionAutoimpresorSRI a = this.autorizacionAutoimpresorSRIDao.obtenerAutorizacionSRIVigente(idOrganizacion);
/* 416:507 */     a = buscarPorId(a.getId());
/* 417:508 */     a.getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().size();
/* 418:509 */     for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : a.getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI())
/* 419:    */     {
/* 420:510 */       adpv.getId();
/* 421:511 */       adpv.getPuntoDeVenta().getId();
/* 422:512 */       adpv.getPuntoDeVenta().getSucursal().getId();
/* 423:    */     }
/* 424:514 */     a.getListaAutorizacionDocumentoAutoimpresorSRI().size();
/* 425:515 */     for (AutorizacionDocumentoAutoimpresorSRI ad : a.getListaAutorizacionDocumentoAutoimpresorSRI()) {
/* 426:516 */       ad.getId();
/* 427:    */     }
/* 428:518 */     a.getListaAutorizacionPuntoDeVentaAutoimpresorSRI().size();
/* 429:519 */     for (AutorizacionPuntoDeVentaAutoimpresorSRI apv : a.getListaAutorizacionPuntoDeVentaAutoimpresorSRI())
/* 430:    */     {
/* 431:520 */       apv.getId();
/* 432:521 */       apv.getPuntoDeVenta().getId();
/* 433:    */     }
/* 434:523 */     return a;
/* 435:    */   }
/* 436:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.impl.ServicioAutorizacionAutoimpresorSRIImpl
 * JD-Core Version:    0.7.0.1
 */