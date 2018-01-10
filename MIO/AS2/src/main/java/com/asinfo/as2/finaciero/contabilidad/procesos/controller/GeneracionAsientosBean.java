/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaCreditoProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   9:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*  10:    */ import com.asinfo.as2.entities.Asiento;
/*  11:    */ import com.asinfo.as2.entities.Cobro;
/*  12:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.EntidadBase;
/*  15:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  16:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  17:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  18:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*  19:    */ import com.asinfo.as2.entities.Organizacion;
/*  20:    */ import com.asinfo.as2.entities.Pago;
/*  21:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  28:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  29:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*  30:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  31:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  32:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  33:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioLiquidacionAnticipoProveedor;
/*  34:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*  35:    */ import com.asinfo.as2.util.AppUtil;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  38:    */ import com.asinfo.as2.ventas.procesos.FacturaClienteBaseBean;
/*  39:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  40:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  41:    */ import java.text.SimpleDateFormat;
/*  42:    */ import java.util.ArrayList;
/*  43:    */ import java.util.Collections;
/*  44:    */ import java.util.Date;
/*  45:    */ import java.util.HashMap;
/*  46:    */ import java.util.Iterator;
/*  47:    */ import java.util.List;
/*  48:    */ import java.util.Map;
/*  49:    */ import javax.ejb.EJB;
/*  50:    */ import javax.faces.bean.ManagedBean;
/*  51:    */ import javax.faces.bean.ViewScoped;
/*  52:    */ import javax.faces.model.SelectItem;
/*  53:    */ 
/*  54:    */ @ManagedBean
/*  55:    */ @ViewScoped
/*  56:    */ public class GeneracionAsientosBean
/*  57:    */   extends FacturaClienteBaseBean
/*  58:    */ {
/*  59:    */   private static final long serialVersionUID = -902955423742298817L;
/*  60:    */   @EJB
/*  61:    */   protected transient ServicioAnticipoCliente servicioAnticipoCliente;
/*  62:    */   @EJB
/*  63:    */   protected transient ServicioLiquidacionAnticipoCliente servicioLiquidacionAnticipoCliente;
/*  64:    */   @EJB
/*  65:    */   protected transient ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  66:    */   @EJB
/*  67:    */   protected transient ServicioLiquidacionAnticipoProveedor servicioLiquidacionAnticipoProveedor;
/*  68:    */   @EJB
/*  69:    */   protected transient ServicioCobro servicioCobro;
/*  70:    */   @EJB
/*  71:    */   protected transient ServicioPago servicioPago;
/*  72:    */   @EJB
/*  73:    */   protected transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  74:    */   @EJB
/*  75:    */   protected transient ServicioNotaCreditoProveedor servicioNotaCreditoProveedor;
/*  76:    */   @EJB
/*  77:    */   protected transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  78:    */   @EJB
/*  79:    */   protected transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  80:    */   @EJB
/*  81:    */   protected transient ServicioAsiento servicioAsiento;
/*  82:    */   private Date fechaDesde;
/*  83: 84 */   private Date fechaHasta = new Date();
/*  84:    */   private List<SelectItem> listaDocumentoBase;
/*  85:    */   private List<Documento> listaDocumento;
/*  86:    */   private DocumentoBase documentoBase;
/*  87:    */   private Documento documento;
/*  88:    */   private EntidadBase entidad;
/*  89:    */   
/*  90:    */   public List<SelectItem> getListaDocumentoBase()
/*  91:    */   {
/*  92: 99 */     if (this.listaDocumentoBase == null)
/*  93:    */     {
/*  94:100 */       this.listaDocumentoBase = new ArrayList();
/*  95:101 */       SelectItem item = null;
/*  96:102 */       item = new SelectItem(DocumentoBase.ANTICIPO_CLIENTE, DocumentoBase.ANTICIPO_CLIENTE.getNombre());
/*  97:103 */       this.listaDocumentoBase.add(item);
/*  98:104 */       item = new SelectItem(DocumentoBase.ANTICIPO_PROVEEDOR, DocumentoBase.ANTICIPO_PROVEEDOR.getNombre());
/*  99:105 */       this.listaDocumentoBase.add(item);
/* 100:106 */       item = new SelectItem(DocumentoBase.LIQUIDACION_ANTICIPO_CLIENTE, DocumentoBase.LIQUIDACION_ANTICIPO_CLIENTE.getNombre());
/* 101:107 */       this.listaDocumentoBase.add(item);
/* 102:108 */       item = new SelectItem(DocumentoBase.LIQUIDACION_ANTICIPO_PROVEEDOR, DocumentoBase.LIQUIDACION_ANTICIPO_PROVEEDOR.getNombre());
/* 103:109 */       this.listaDocumentoBase.add(item);
/* 104:110 */       item = new SelectItem(DocumentoBase.NOTA_CREDITO_CLIENTE, DocumentoBase.NOTA_CREDITO_CLIENTE.getNombre());
/* 105:111 */       this.listaDocumentoBase.add(item);
/* 106:112 */       item = new SelectItem(DocumentoBase.DEVOLUCION_CLIENTE, DocumentoBase.DEVOLUCION_CLIENTE.getNombre());
/* 107:113 */       this.listaDocumentoBase.add(item);
/* 108:114 */       item = new SelectItem(DocumentoBase.NOTA_CREDITO_PROVEEDOR, DocumentoBase.NOTA_CREDITO_PROVEEDOR.getNombre());
/* 109:115 */       this.listaDocumentoBase.add(item);
/* 110:116 */       item = new SelectItem(DocumentoBase.DEVOLUCION_PROVEEDOR, DocumentoBase.DEVOLUCION_PROVEEDOR.getNombre());
/* 111:117 */       this.listaDocumentoBase.add(item);
/* 112:118 */       item = new SelectItem(DocumentoBase.PAGO_PROVEEDOR, DocumentoBase.PAGO_PROVEEDOR.getNombre());
/* 113:119 */       this.listaDocumentoBase.add(item);
/* 114:120 */       item = new SelectItem(DocumentoBase.COBRO_CLIENTE, DocumentoBase.COBRO_CLIENTE.getNombre());
/* 115:121 */       this.listaDocumentoBase.add(item);
/* 116:122 */       item = new SelectItem(DocumentoBase.RECEPCION_BODEGA, DocumentoBase.RECEPCION_BODEGA.getNombre());
/* 117:123 */       this.listaDocumentoBase.add(item);
/* 118:124 */       item = new SelectItem(DocumentoBase.FACTURA_PROVEEDOR, DocumentoBase.FACTURA_PROVEEDOR.getNombre());
/* 119:125 */       this.listaDocumentoBase.add(item);
/* 120:    */     }
/* 121:127 */     Collections.sort(this.listaDocumentoBase, new SelectItemComparator());
/* 122:    */     
/* 123:129 */     return this.listaDocumentoBase;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<Documento> getListaDocumento()
/* 127:    */   {
/* 128:    */     try
/* 129:    */     {
/* 130:134 */       if (this.listaDocumento == null) {
/* 131:136 */         if (this.documentoBase != null) {
/* 132:137 */           this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(this.documentoBase, AppUtil.getOrganizacion()
/* 133:138 */             .getIdOrganizacion());
/* 134:    */         } else {
/* 135:140 */           this.listaDocumento = new ArrayList();
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:    */     catch (ExcepcionAS2 e)
/* 140:    */     {
/* 141:144 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 142:    */     }
/* 143:146 */     return this.listaDocumento;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<EntidadBase> autocompletarDocumentos(String consulta)
/* 147:    */   {
/* 148:150 */     List<EntidadBase> lista = new ArrayList();
/* 149:    */     
/* 150:152 */     HashMap<String, String> filters = new HashMap();
/* 151:153 */     filters.put("numero", "%" + consulta.trim());
/* 152:154 */     filters.put("documento.documentoBase", "" + this.documentoBase);
/* 153:155 */     filters.put("estado", "!=" + Estado.ANULADO);
/* 154:156 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[this.documentoBase.ordinal()])
/* 155:    */     {
/* 156:    */     case 1: 
/* 157:158 */       lista = new ArrayList(this.servicioAnticipoCliente.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 158:159 */       break;
/* 159:    */     case 2: 
/* 160:161 */       lista = new ArrayList(this.servicioLiquidacionAnticipoCliente.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 161:162 */       break;
/* 162:    */     case 3: 
/* 163:164 */       lista = new ArrayList(this.servicioAnticipoProveedor.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 164:165 */       break;
/* 165:    */     case 4: 
/* 166:167 */       lista = new ArrayList(this.servicioLiquidacionAnticipoProveedor.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 167:168 */       break;
/* 168:    */     case 5: 
/* 169:170 */       lista = new ArrayList(this.servicioNotaCreditoCliente.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 170:171 */       break;
/* 171:    */     case 6: 
/* 172:174 */       lista = new ArrayList(this.servicioNotaCreditoCliente.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 173:175 */       break;
/* 174:    */     case 7: 
/* 175:178 */       lista = new ArrayList(this.servicioNotaCreditoProveedor.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 176:179 */       break;
/* 177:    */     case 8: 
/* 178:182 */       lista = new ArrayList(this.servicioNotaCreditoProveedor.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 179:183 */       break;
/* 180:    */     case 9: 
/* 181:186 */       lista = new ArrayList(this.servicioPago.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 182:187 */       break;
/* 183:    */     case 10: 
/* 184:190 */       lista = new ArrayList(this.servicioCobro.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 185:191 */       break;
/* 186:    */     case 11: 
/* 187:194 */       lista = new ArrayList(this.servicioFacturaProveedor.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 188:195 */       break;
/* 189:    */     case 12: 
/* 190:198 */       lista = new ArrayList(this.servicioRecepcionProveedor.obtenerListaPorPagina(0, 20, "numero", true, filters));
/* 191:199 */       break;
/* 192:    */     }
/* 193:205 */     return lista;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void actualizarDocumentoBase()
/* 197:    */   {
/* 198:209 */     this.listaDocumento = null;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void contabilizarDocumentos()
/* 202:    */   {
/* 203:213 */     Map<String, String> filtros = new HashMap();
/* 204:214 */     boolean indicadorUnSoloProceso = false;
/* 205:    */     
/* 206:216 */     filtros.put("estado", "!=" + Estado.ANULADO);
/* 207:217 */     if (this.entidad == null)
/* 208:    */     {
/* 209:218 */       indicadorUnSoloProceso = false;
/* 210:219 */       SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
/* 211:220 */       filtros.put("fecha", OperacionEnum.BETWEEN.name() + sdf2.format(this.fechaDesde) + "~" + sdf2.format(this.fechaHasta));
/* 212:221 */       if (this.documento != null) {
/* 213:222 */         filtros.put("documento.idDocumento", "" + this.documento.getId());
/* 214:    */       } else {
/* 215:224 */         filtros.put("documento.documentoBase", "" + this.documentoBase);
/* 216:    */       }
/* 217:    */     }
/* 218:    */     else
/* 219:    */     {
/* 220:227 */       indicadorUnSoloProceso = true;
/* 221:    */     }
/* 222:    */     try
/* 223:    */     {
/* 224:    */       AnticipoCliente anticipoCliente;
/* 225:    */       LiquidacionAnticipoCliente liquidacionAnticipoCliente;
/* 226:    */       AnticipoProveedor anticipoProveedor;
/* 227:    */       LiquidacionAnticipoProveedor liquidacionAnticipoProveedor;
/* 228:    */       FacturaCliente notaCreditoCliente;
/* 229:    */       FacturaCliente notaCreditoCliente;
/* 230:    */       FacturaProveedor notaCreditoProveedor;
/* 231:    */       FacturaProveedor notaCreditoProveedor;
/* 232:    */       Pago pago;
/* 233:    */       Cobro cobro;
/* 234:    */       FacturaProveedor facturaProveedor;
/* 235:230 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[this.documentoBase.ordinal()])
/* 236:    */       {
/* 237:    */       case 1: 
/* 238:232 */         List<AnticipoCliente> lista = new ArrayList();
/* 239:233 */         if (indicadorUnSoloProceso) {
/* 240:234 */           lista.add(this.servicioAnticipoCliente.buscarPorId(this.entidad.getId()));
/* 241:    */         } else {
/* 242:236 */           lista = this.servicioAnticipoCliente.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 243:    */         }
/* 244:238 */         for (Iterator localIterator = lista.iterator(); localIterator.hasNext();)
/* 245:    */         {
/* 246:238 */           anticipoCliente = (AnticipoCliente)localIterator.next();
/* 247:239 */           AnticipoCliente anticipoClienteBDD = this.servicioAnticipoCliente.cargarDetalle(anticipoCliente.getId());
/* 248:240 */           if (anticipoClienteBDD.getAsiento() != null)
/* 249:    */           {
/* 250:241 */             anticipoClienteBDD.setAsiento(this.servicioAsiento.cargarDetalle(anticipoClienteBDD.getAsiento().getId()));
/* 251:    */             
/* 252:243 */             this.servicioAnticipoCliente.contabilizar(anticipoClienteBDD);
/* 253:244 */             if (anticipoClienteBDD.getAsientoDevolucion() != null)
/* 254:    */             {
/* 255:245 */               anticipoClienteBDD.setAsientoDevolucion(this.servicioAsiento.cargarDetalle(anticipoClienteBDD.getAsientoDevolucion().getId()));
/* 256:246 */               if (!anticipoClienteBDD.getCuentaBancariaOrganizacionDevolucion().isIndicadorCruce()) {
/* 257:247 */                 this.servicioAnticipoCliente.contabilizarDevolucion(anticipoClienteBDD);
/* 258:    */               }
/* 259:    */             }
/* 260:    */           }
/* 261:    */         }
/* 262:252 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 263:253 */         break;
/* 264:    */       case 2: 
/* 265:255 */         Object listaLAC = new ArrayList();
/* 266:256 */         if (indicadorUnSoloProceso)
/* 267:    */         {
/* 268:257 */           ((List)listaLAC).add(this.servicioLiquidacionAnticipoCliente.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 269:    */         }
/* 270:    */         else
/* 271:    */         {
/* 272:259 */           listaLAC = this.servicioLiquidacionAnticipoCliente.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 273:260 */           for (Documento doc : this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DEVOLUCION_CLIENTE, 
/* 274:261 */             AppUtil.getOrganizacion().getIdOrganizacion()))
/* 275:    */           {
/* 276:262 */             filtros.put("documento.idDocumento", "" + doc.getId());
/* 277:263 */             ((List)listaLAC).addAll(this.servicioLiquidacionAnticipoCliente.obtenerListaPorPagina(0, 1000, "numero", true, filtros));
/* 278:    */           }
/* 279:266 */           for (Documento doc : this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.NOTA_CREDITO_CLIENTE, 
/* 280:267 */             AppUtil.getOrganizacion().getIdOrganizacion()))
/* 281:    */           {
/* 282:268 */             filtros.put("documento.idDocumento", "" + doc.getId());
/* 283:269 */             ((List)listaLAC).addAll(this.servicioLiquidacionAnticipoCliente.obtenerListaPorPagina(0, 1000, "numero", true, filtros));
/* 284:    */           }
/* 285:    */         }
/* 286:272 */         for (anticipoCliente = ((List)listaLAC).iterator(); anticipoCliente.hasNext();)
/* 287:    */         {
/* 288:272 */           liquidacionAnticipoCliente = (LiquidacionAnticipoCliente)anticipoCliente.next();
/* 289:    */           
/* 290:274 */           LiquidacionAnticipoCliente liquidacionAnticipoClienteBDD = this.servicioLiquidacionAnticipoCliente.cargarDetalle(liquidacionAnticipoCliente.getId());
/* 291:275 */           if (liquidacionAnticipoClienteBDD.getAsiento() != null) {
/* 292:276 */             this.servicioLiquidacionAnticipoCliente.contabilizar(liquidacionAnticipoClienteBDD);
/* 293:    */           }
/* 294:    */         }
/* 295:279 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 296:280 */         break;
/* 297:    */       case 3: 
/* 298:282 */         List<AnticipoProveedor> listaAP = new ArrayList();
/* 299:283 */         if (indicadorUnSoloProceso) {
/* 300:284 */           listaAP.add(this.servicioAnticipoProveedor.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 301:    */         } else {
/* 302:286 */           listaAP = this.servicioAnticipoProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 303:    */         }
/* 304:288 */         for (liquidacionAnticipoCliente = listaAP.iterator(); liquidacionAnticipoCliente.hasNext();)
/* 305:    */         {
/* 306:288 */           anticipoProveedor = (AnticipoProveedor)liquidacionAnticipoCliente.next();
/* 307:289 */           AnticipoProveedor anticipoProveedorBDD = this.servicioAnticipoProveedor.cargarDetalle(anticipoProveedor.getId());
/* 308:290 */           if (anticipoProveedorBDD.getAsiento() != null)
/* 309:    */           {
/* 310:291 */             anticipoProveedorBDD.setAsiento(this.servicioAsiento.cargarDetalle(anticipoProveedorBDD.getAsiento().getId()));
/* 311:292 */             this.servicioAnticipoProveedor.contabilizar(anticipoProveedorBDD);
/* 312:293 */             if (anticipoProveedorBDD.getAsientoDevolucion() != null)
/* 313:    */             {
/* 314:294 */               anticipoProveedorBDD.setAsientoDevolucion(this.servicioAsiento.cargarDetalle(anticipoProveedorBDD.getAsientoDevolucion()
/* 315:295 */                 .getId()));
/* 316:296 */               if (!anticipoProveedorBDD.getCuentaBancariaOrganizacionDevolucion().isIndicadorCruce()) {
/* 317:297 */                 this.servicioAnticipoProveedor.contabilizarDevolucion(anticipoProveedorBDD);
/* 318:    */               }
/* 319:    */             }
/* 320:    */           }
/* 321:    */         }
/* 322:302 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 323:303 */         break;
/* 324:    */       case 4: 
/* 325:305 */         List<LiquidacionAnticipoProveedor> listaLAP = new ArrayList();
/* 326:307 */         if (indicadorUnSoloProceso)
/* 327:    */         {
/* 328:308 */           listaLAP.add(this.servicioLiquidacionAnticipoProveedor.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 329:    */         }
/* 330:    */         else
/* 331:    */         {
/* 332:310 */           listaLAP.addAll(this.servicioLiquidacionAnticipoProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros));
/* 333:311 */           for (Documento doc : this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DEVOLUCION_PROVEEDOR, 
/* 334:312 */             AppUtil.getOrganizacion().getIdOrganizacion()))
/* 335:    */           {
/* 336:313 */             filtros.put("documento.idDocumento", "" + doc.getId());
/* 337:314 */             listaLAP.addAll(this.servicioLiquidacionAnticipoProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros));
/* 338:    */           }
/* 339:317 */           for (Documento doc : this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.NOTA_CREDITO_PROVEEDOR, 
/* 340:318 */             AppUtil.getOrganizacion().getIdOrganizacion()))
/* 341:    */           {
/* 342:319 */             filtros.put("documento.idDocumento", "" + doc.getId());
/* 343:320 */             listaLAP.addAll(this.servicioLiquidacionAnticipoProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros));
/* 344:    */           }
/* 345:    */         }
/* 346:323 */         for (anticipoProveedor = listaLAP.iterator(); anticipoProveedor.hasNext();)
/* 347:    */         {
/* 348:323 */           liquidacionAnticipoProveedor = (LiquidacionAnticipoProveedor)anticipoProveedor.next();
/* 349:    */           
/* 350:325 */           LiquidacionAnticipoProveedor liquidacionAnticipoProveedorBDD = this.servicioLiquidacionAnticipoProveedor.cargarDetalle(liquidacionAnticipoProveedor.getId());
/* 351:327 */           if (liquidacionAnticipoProveedorBDD.getAsiento() != null)
/* 352:    */           {
/* 353:328 */             liquidacionAnticipoProveedorBDD.setAsiento(this.servicioAsiento
/* 354:329 */               .cargarDetalle(liquidacionAnticipoProveedorBDD.getAsiento().getId()));
/* 355:330 */             this.servicioLiquidacionAnticipoProveedor.contabilizar(liquidacionAnticipoProveedorBDD);
/* 356:    */           }
/* 357:    */         }
/* 358:333 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 359:334 */         break;
/* 360:    */       case 5: 
/* 361:336 */         List<FacturaCliente> listaNCC = new ArrayList();
/* 362:337 */         if (indicadorUnSoloProceso) {
/* 363:338 */           listaNCC.add(this.servicioFacturaCliente.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 364:    */         } else {
/* 365:340 */           listaNCC = this.servicioFacturaCliente.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 366:    */         }
/* 367:342 */         for (liquidacionAnticipoProveedor = listaNCC.iterator(); liquidacionAnticipoProveedor.hasNext();)
/* 368:    */         {
/* 369:342 */           notaCreditoCliente = (FacturaCliente)liquidacionAnticipoProveedor.next();
/* 370:343 */           AnticipoCliente anticipoClienteBDD = this.servicioAnticipoCliente.cargarDetalle(this.servicioFacturaCliente
/* 371:344 */             .cargarDetalle(notaCreditoCliente.getId()).getAnticipoCliente().getId());
/* 372:345 */           if (anticipoClienteBDD.getAsiento() != null)
/* 373:    */           {
/* 374:346 */             anticipoClienteBDD.setAsiento(this.servicioAsiento.cargarDetalle(anticipoClienteBDD.getAsiento().getId()));
/* 375:347 */             this.servicioAnticipoCliente.contabilizar(anticipoClienteBDD);
/* 376:    */           }
/* 377:    */         }
/* 378:350 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 379:351 */         break;
/* 380:    */       case 6: 
/* 381:354 */         List<FacturaCliente> listaDC = new ArrayList();
/* 382:355 */         if (indicadorUnSoloProceso) {
/* 383:356 */           listaDC.add(this.servicioFacturaCliente.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 384:    */         } else {
/* 385:358 */           listaDC = this.servicioFacturaCliente.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 386:    */         }
/* 387:360 */         for (notaCreditoCliente = listaDC.iterator(); notaCreditoCliente.hasNext();)
/* 388:    */         {
/* 389:360 */           notaCreditoCliente = (FacturaCliente)notaCreditoCliente.next();
/* 390:361 */           AnticipoCliente anticipoClienteBDD = this.servicioAnticipoCliente.cargarDetalle(this.servicioFacturaCliente
/* 391:362 */             .cargarDetalle(notaCreditoCliente.getId()).getAnticipoCliente().getId());
/* 392:363 */           if (anticipoClienteBDD.getAsiento() != null)
/* 393:    */           {
/* 394:364 */             anticipoClienteBDD.setAsiento(this.servicioAsiento.cargarDetalle(anticipoClienteBDD.getAsiento().getId()));
/* 395:365 */             this.servicioAnticipoCliente.contabilizar(anticipoClienteBDD);
/* 396:    */           }
/* 397:    */         }
/* 398:368 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 399:369 */         break;
/* 400:    */       case 7: 
/* 401:372 */         List<FacturaProveedor> listaNCP = new ArrayList();
/* 402:373 */         if (indicadorUnSoloProceso) {
/* 403:374 */           listaNCP.add(this.servicioFacturaProveedor.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 404:    */         } else {
/* 405:376 */           listaNCP = this.servicioFacturaProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 406:    */         }
/* 407:378 */         for (notaCreditoCliente = listaNCP.iterator(); notaCreditoCliente.hasNext();)
/* 408:    */         {
/* 409:378 */           notaCreditoProveedor = (FacturaProveedor)notaCreditoCliente.next();
/* 410:379 */           AnticipoProveedor anticipoProveedorBDD = this.servicioAnticipoProveedor.cargarDetalle(this.servicioFacturaProveedor
/* 411:380 */             .cargarDetalle(Integer.valueOf(notaCreditoProveedor.getId())).getAnticipoProveedor().getId());
/* 412:381 */           if (anticipoProveedorBDD.getAsiento() != null)
/* 413:    */           {
/* 414:382 */             anticipoProveedorBDD.setAsiento(this.servicioAsiento.cargarDetalle(anticipoProveedorBDD.getAsiento().getId()));
/* 415:383 */             this.servicioAnticipoProveedor.contabilizar(anticipoProveedorBDD);
/* 416:    */           }
/* 417:    */         }
/* 418:386 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 419:387 */         break;
/* 420:    */       case 8: 
/* 421:390 */         List<FacturaProveedor> listaDP = new ArrayList();
/* 422:391 */         if (indicadorUnSoloProceso) {
/* 423:392 */           listaDP.add(this.servicioFacturaProveedor.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 424:    */         } else {
/* 425:394 */           listaDP = this.servicioFacturaProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 426:    */         }
/* 427:396 */         for (notaCreditoProveedor = listaDP.iterator(); notaCreditoProveedor.hasNext();)
/* 428:    */         {
/* 429:396 */           notaCreditoProveedor = (FacturaProveedor)notaCreditoProveedor.next();
/* 430:397 */           AnticipoProveedor anticipoProveedorBDD = this.servicioAnticipoProveedor.cargarDetalle(this.servicioFacturaProveedor
/* 431:398 */             .cargarDetalle(Integer.valueOf(notaCreditoProveedor.getId())).getAnticipoProveedor().getId());
/* 432:399 */           if (anticipoProveedorBDD.getAsiento() != null)
/* 433:    */           {
/* 434:400 */             anticipoProveedorBDD.setAsiento(this.servicioAsiento.cargarDetalle(anticipoProveedorBDD.getAsiento().getId()));
/* 435:401 */             this.servicioAnticipoProveedor.contabilizar(anticipoProveedorBDD);
/* 436:    */           }
/* 437:    */         }
/* 438:404 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 439:405 */         break;
/* 440:    */       case 9: 
/* 441:408 */         List<Pago> listaP = new ArrayList();
/* 442:409 */         if (indicadorUnSoloProceso) {
/* 443:410 */           listaP.add(this.servicioPago.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 444:    */         } else {
/* 445:412 */           listaP = this.servicioPago.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 446:    */         }
/* 447:414 */         for (notaCreditoProveedor = listaP.iterator(); notaCreditoProveedor.hasNext();)
/* 448:    */         {
/* 449:414 */           pago = (Pago)notaCreditoProveedor.next();
/* 450:415 */           Pago pagoBDD = this.servicioPago.cargarDetalle(pago.getId());
/* 451:416 */           if (pagoBDD.getAsiento() != null)
/* 452:    */           {
/* 453:417 */             pagoBDD.setAsiento(this.servicioAsiento.cargarDetalle(pagoBDD.getAsiento().getId()));
/* 454:418 */             this.servicioPago.contabilizar(pagoBDD);
/* 455:    */           }
/* 456:    */         }
/* 457:421 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 458:422 */         break;
/* 459:    */       case 10: 
/* 460:425 */         List<Cobro> listaC = new ArrayList();
/* 461:426 */         if (indicadorUnSoloProceso) {
/* 462:427 */           listaC.add(this.servicioCobro.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 463:    */         } else {
/* 464:429 */           listaC = this.servicioCobro.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 465:    */         }
/* 466:431 */         for (pago = listaC.iterator(); pago.hasNext();)
/* 467:    */         {
/* 468:431 */           cobro = (Cobro)pago.next();
/* 469:432 */           Cobro cobroBDD = this.servicioCobro.cargarDetalle(cobro.getId());
/* 470:433 */           if (cobroBDD.getAsiento() != null)
/* 471:    */           {
/* 472:434 */             cobroBDD.setAsiento(this.servicioAsiento.cargarDetalle(cobroBDD.getAsiento().getId()));
/* 473:435 */             this.servicioCobro.contabilizar(cobroBDD);
/* 474:    */           }
/* 475:    */         }
/* 476:438 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 477:439 */         break;
/* 478:    */       case 11: 
/* 479:442 */         List<FacturaProveedor> listaPr = new ArrayList();
/* 480:443 */         if (indicadorUnSoloProceso) {
/* 481:444 */           listaPr.add(this.servicioFacturaProveedor.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 482:    */         } else {
/* 483:446 */           listaPr = this.servicioFacturaProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 484:    */         }
/* 485:448 */         for (cobro = listaPr.iterator(); cobro.hasNext();)
/* 486:    */         {
/* 487:448 */           facturaProveedor = (FacturaProveedor)cobro.next();
/* 488:449 */           FacturaProveedor facturaProveedorBDD = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(facturaProveedor.getId()));
/* 489:450 */           if (facturaProveedorBDD.getAsiento() != null)
/* 490:    */           {
/* 491:451 */             facturaProveedorBDD.setAsiento(this.servicioAsiento.cargarDetalle(facturaProveedorBDD.getAsiento().getId()));
/* 492:452 */             this.servicioFacturaProveedor.contabilizar(facturaProveedorBDD);
/* 493:    */           }
/* 494:    */         }
/* 495:455 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 496:456 */         break;
/* 497:    */       case 12: 
/* 498:459 */         List<RecepcionProveedor> listaRP = new ArrayList();
/* 499:460 */         if (indicadorUnSoloProceso) {
/* 500:461 */           listaRP.add(this.servicioRecepcionProveedor.buscarPorId(Integer.valueOf(this.entidad.getId())));
/* 501:    */         } else {
/* 502:463 */           listaRP = this.servicioRecepcionProveedor.obtenerListaPorPagina(0, 1000, "numero", true, filtros);
/* 503:    */         }
/* 504:465 */         for (RecepcionProveedor recepcionProveedor : listaRP)
/* 505:    */         {
/* 506:466 */           RecepcionProveedor recepcionProveedorBDD = this.servicioRecepcionProveedor.cargarDetalle(recepcionProveedor);
/* 507:468 */           if (recepcionProveedorBDD.getAsiento() != null)
/* 508:    */           {
/* 509:469 */             if (recepcionProveedorBDD.getFacturaProveedor() != null) {
/* 510:470 */               recepcionProveedorBDD.setFacturaProveedor(this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(recepcionProveedorBDD
/* 511:471 */                 .getFacturaProveedor().getId())));
/* 512:    */             }
/* 513:473 */             recepcionProveedorBDD.setAsiento(this.servicioAsiento.cargarDetalle(recepcionProveedorBDD.getAsiento().getId()));
/* 514:474 */             this.servicioRecepcionProveedor.contabilizar(recepcionProveedorBDD);
/* 515:    */           }
/* 516:    */         }
/* 517:477 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 518:    */       }
/* 519:    */     }
/* 520:    */     catch (ExcepcionAS2Financiero e)
/* 521:    */     {
/* 522:485 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 523:486 */       e.printStackTrace();
/* 524:    */     }
/* 525:    */     catch (ExcepcionAS2 e)
/* 526:    */     {
/* 527:488 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 528:489 */       e.printStackTrace();
/* 529:    */     }
/* 530:    */     catch (AS2Exception e)
/* 531:    */     {
/* 532:491 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 533:492 */       e.printStackTrace();
/* 534:    */     }
/* 535:    */   }
/* 536:    */   
/* 537:    */   public DocumentoBase getDocumentoBase()
/* 538:    */   {
/* 539:497 */     return this.documentoBase;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 543:    */   {
/* 544:501 */     this.documentoBase = documentoBase;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public Documento getDocumento()
/* 548:    */   {
/* 549:505 */     return this.documento;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setDocumento(Documento documento)
/* 553:    */   {
/* 554:509 */     this.documento = documento;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public EntidadBase getEntidad()
/* 558:    */   {
/* 559:513 */     return this.entidad;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setEntidad(EntidadBase entidad)
/* 563:    */   {
/* 564:517 */     this.entidad = entidad;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public Date getFechaDesde()
/* 568:    */   {
/* 569:526 */     if (this.fechaDesde == null) {
/* 570:527 */       this.fechaDesde = FuncionesUtiles.getFechaInicioMes(new Date());
/* 571:    */     }
/* 572:529 */     return this.fechaDesde;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public void setFechaDesde(Date fechaDesde)
/* 576:    */   {
/* 577:533 */     this.fechaDesde = fechaDesde;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public Date getFechaHasta()
/* 581:    */   {
/* 582:537 */     return this.fechaHasta;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public void setFechaHasta(Date fechaHasta)
/* 586:    */   {
/* 587:541 */     this.fechaHasta = fechaHasta;
/* 588:    */   }
/* 589:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.GeneracionAsientosBean
 * JD-Core Version:    0.7.0.1
 */