/*   1:    */ package com.asinfo.as2.ventas.procesos.aerolineas.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CargaBSPDao;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.dao.SubempresaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Asiento;
/*   9:    */ import com.asinfo.as2.entities.Cliente;
/*  10:    */ import com.asinfo.as2.entities.CuentaContable;
/*  11:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  12:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  13:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  17:    */ import com.asinfo.as2.entities.Organizacion;
/*  18:    */ import com.asinfo.as2.entities.Producto;
/*  19:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  20:    */ import com.asinfo.as2.entities.Subempresa;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.entities.aerolineas.CargaArchivo;
/*  23:    */ import com.asinfo.as2.entities.aerolineas.ConfiguracionCargaTicket;
/*  24:    */ import com.asinfo.as2.entities.aerolineas.DetalleTicket;
/*  25:    */ import com.asinfo.as2.entities.aerolineas.Ticket;
/*  26:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  27:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  28:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  29:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  30:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  31:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  33:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  34:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  35:    */ import com.asinfo.as2.util.AppUtil;
/*  36:    */ import com.asinfo.as2.ventas.procesos.aerolineas.servicio.ServicioVentaTicket;
/*  37:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  38:    */ import java.io.PrintStream;
/*  39:    */ import java.math.BigDecimal;
/*  40:    */ import java.math.RoundingMode;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.List;
/*  45:    */ import java.util.Map;
/*  46:    */ import javax.ejb.EJB;
/*  47:    */ import javax.ejb.Stateless;
/*  48:    */ 
/*  49:    */ @Stateless
/*  50:    */ public class ServicioCargaVentaTicketImpl
/*  51:    */   implements ServicioVentaTicket
/*  52:    */ {
/*  53:    */   @EJB
/*  54:    */   private CargaBSPDao cargaBSPDao;
/*  55:    */   @EJB
/*  56:    */   private ServicioGenerico<Ticket> servicioTicket;
/*  57:    */   @EJB
/*  58:    */   private ServicioGenerico<DetalleTicket> servicioDetalleTicket;
/*  59:    */   @EJB
/*  60:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  61:    */   @EJB
/*  62:    */   private ServicioDocumento servicioDocumento;
/*  63:    */   @EJB
/*  64:    */   private ServicioEmpresa servicioEmpresa;
/*  65:    */   @EJB
/*  66:    */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  67:    */   @EJB
/*  68:    */   private SubempresaDao subempresaDao;
/*  69:    */   @EJB
/*  70:    */   private ServicioCuentaContable servicioCuentaContable;
/*  71:    */   @EJB
/*  72:    */   private ServicioProducto servicioProducto;
/*  73:    */   @EJB
/*  74:    */   private ServicioVentaTicket servicioCargaBSP;
/*  75:    */   @EJB
/*  76:    */   private ServicioGenerico<ConfiguracionCargaTicket> servicioCatalogoConfiguracionTicket;
/*  77:    */   @EJB
/*  78:    */   private GenericoDao<Ticket> TicketDao;
/*  79:    */   
/*  80:    */   public void guardar(CargaArchivo bsp)
/*  81:    */     throws AS2Exception
/*  82:    */   {
/*  83: 81 */     if (!bsp.getTipo().equals("BSP Actualizado")) {
/*  84: 82 */       validacionArchivoBSP(bsp.getIdOrganizacion(), bsp.getReferenciaArchivo(), bsp.getIndicadorRespaldo().booleanValue());
/*  85:    */     }
/*  86: 85 */     for (Ticket t : bsp.getListaTicket())
/*  87:    */     {
/*  88: 86 */       this.servicioTicket.guardar(t);
/*  89: 87 */       t.setBsp(bsp);
/*  90: 88 */       for (DetalleTicket dt : t.getListaDetalleTicket()) {
/*  91: 89 */         this.servicioDetalleTicket.guardar(dt);
/*  92:    */       }
/*  93:    */     }
/*  94: 92 */     this.cargaBSPDao.guardar(bsp);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<CargaArchivo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  98:    */   {
/*  99: 99 */     return this.cargaBSPDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int contarPorCriterio(Map<String, String> filters)
/* 103:    */   {
/* 104:104 */     return this.cargaBSPDao.contarPorCriterio(filters);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public CargaArchivo cargarDetalle(int idBillingSettlementPlan)
/* 108:    */   {
/* 109:109 */     return this.cargaBSPDao.cargarDetalle(idBillingSettlementPlan);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<CargaArchivo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 113:    */   {
/* 114:114 */     return this.cargaBSPDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void eliminar(CargaArchivo bsp)
/* 118:    */   {
/* 119:119 */     this.cargaBSPDao.eliminar(bsp);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void facturarLote(CargaArchivo bsp, PuntoDeVenta puntoVenta, Empresa empresa)
/* 123:    */     throws ExcepcionAS2, AS2Exception
/* 124:    */   {
/* 125:125 */     List<Ticket> listaTicket = new ArrayList();
/* 126:    */     
/* 127:127 */     bsp = this.servicioCargaBSP.cargarDetalle(bsp.getIdCargaArchivo());
/* 128:129 */     for (Ticket t : bsp.getListaTicket()) {
/* 129:130 */       listaTicket.add(t);
/* 130:    */     }
/* 131:132 */     Object listaFacturas = new ArrayList();
/* 132:    */     
/* 133:    */ 
/* 134:    */ 
/* 135:136 */     List<Documento> listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBaseOrganizacionAerolinea(DocumentoBase.FACTURA_CLIENTE, 
/* 136:137 */       AppUtil.getOrganizacion().getIdOrganizacion());
/* 137:    */     
/* 138:139 */     validarDocumentoAerolinea(listaDocumentoCliente);
/* 139:    */     
/* 140:141 */     Documento documento = null;
/* 141:142 */     for (Documento doc : listaDocumentoCliente) {
/* 142:143 */       if (doc.isPredeterminado()) {
/* 143:144 */         documento = doc;
/* 144:    */       }
/* 145:    */     }
/* 146:147 */     if ((documento == null) && (!listaDocumentoCliente.isEmpty())) {
/* 147:148 */       documento = (Documento)listaDocumentoCliente.get(0);
/* 148:    */     }
/* 149:151 */     for (Ticket ticke : listaTicket)
/* 150:    */     {
/* 151:153 */       facturaCliente = new FacturaCliente();
/* 152:    */       
/* 153:155 */       ((List)listaFacturas).add(facturaCliente);
/* 154:    */       
/* 155:157 */       facturaCliente.setNumero("");
/* 156:    */       
/* 157:159 */       facturaCliente.setFecha(new Date());
/* 158:160 */       facturaCliente.setEstado(Estado.CONTABILIZADO);
/* 159:161 */       facturaCliente.setDocumento(documento);
/* 160:162 */       facturaCliente.setDescripcion("N°  " + ticke.getNumero());
/* 161:163 */       facturaCliente.setIndicadorSaldoInicial(true);
/* 162:164 */       facturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 163:165 */       facturaCliente.setSucursal(AppUtil.getSucursal());
/* 164:    */       
/* 165:167 */       facturaCliente.setFacturaClienteSRI(new FacturaClienteSRI());
/* 166:168 */       facturaCliente.getFacturaClienteSRI().setEstado(facturaCliente.getEstado());
/* 167:169 */       facturaCliente.getFacturaClienteSRI().setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 168:170 */       facturaCliente.getFacturaClienteSRI().setIdSucursal(facturaCliente.getSucursal().getId());
/* 169:    */       
/* 170:172 */       facturaCliente.getFacturaClienteSRI().setFacturaCliente(facturaCliente);
/* 171:173 */       facturaCliente.setFacturaClienteSRI(facturaCliente.getFacturaClienteSRI());
/* 172:    */       
/* 173:    */ 
/* 174:176 */       em = empresa;
/* 175:    */       
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:181 */       facturaCliente.setEmpresa(em);
/* 180:182 */       facturaCliente.setCondicionPago(em.getCliente().getCondicionPago());
/* 181:183 */       facturaCliente.setNumeroCuotas(em.getCliente().getNumeroCuotas());
/* 182:184 */       facturaCliente.setZona(em.getCliente().getZona());
/* 183:    */       
/* 184:186 */       facturaCliente.setDireccionEmpresa((DireccionEmpresa)em.getDirecciones().get(0));
/* 185:187 */       facturaCliente.setEmail(this.servicioEmpresa.cargarMails(facturaCliente.getEmpresa(), DocumentoBase.FACTURA_CLIENTE));
/* 186:189 */       if ((ticke.getOperacion().equals("TKTT")) || (ticke.getOperacion().equals("TKT")))
/* 187:    */       {
/* 188:191 */         for (DetalleTicket dt : ticke.getListaDetalleTicket()) {
/* 189:193 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getStdNumQual().equals("30"))) {
/* 190:195 */             if (ticke.getTarifa().compareTo(BigDecimal.ZERO) != 0)
/* 191:    */             {
/* 192:196 */               if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("EC")))
/* 193:    */               {
/* 194:197 */                 DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 195:198 */                 facturaCliente.getListaDetalleFacturaCliente().add(dfc);
/* 196:199 */                 Producto producto = this.servicioProducto.buscarPorCodigo("V12", bsp.getIdOrganizacion(), null);
/* 197:200 */                 dfc.setCantidad(BigDecimal.ONE);
/* 198:201 */                 dfc.setUnidadVenta(producto.getUnidadVenta());
/* 199:202 */                 dfc.setProducto(producto);
/* 200:203 */                 dfc.setEliminado(false);
/* 201:204 */                 dfc.setIndicadorImpuesto(true);
/* 202:205 */                 dfc.setFacturaCliente(facturaCliente);
/* 203:206 */                 dfc.setDescripcion("V12   " + producto.getNombre());
/* 204:207 */                 dfc.setPrecio(ticke.getTarifa());
/* 205:208 */                 this.servicioFacturaCliente.obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 206:209 */                 break;
/* 207:    */               }
/* 208:211 */               DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 209:212 */               facturaCliente.getListaDetalleFacturaCliente().add(dfc);
/* 210:213 */               Producto producto = this.servicioProducto.buscarPorCodigo("V0", bsp.getIdOrganizacion(), null);
/* 211:214 */               dfc.setCantidad(BigDecimal.ONE);
/* 212:215 */               dfc.setUnidadVenta(producto.getUnidadVenta());
/* 213:216 */               dfc.setProducto(producto);
/* 214:217 */               dfc.setEliminado(false);
/* 215:218 */               dfc.setIndicadorImpuesto(true);
/* 216:219 */               dfc.setFacturaCliente(facturaCliente);
/* 217:220 */               dfc.setDescripcion("V0   " + producto.getNombre());
/* 218:221 */               dfc.setPrecio(ticke.getTarifa());
/* 219:222 */               this.servicioFacturaCliente.obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 220:223 */               break;
/* 221:    */             }
/* 222:    */           }
/* 223:    */         }
/* 224:232 */         for (DetalleTicket dt : ticke.getListaDetalleTicket()) {
/* 225:234 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getStdNumQual().equals("30")))
/* 226:    */           {
/* 227:236 */             if ((dt.getTaxMiscFeeType1() != null) && 
/* 228:237 */               ((dt.getTaxMiscFeeType1().substring(0, 2).equals("WT")) || (dt.getTaxMiscFeeType1().substring(0, 2).equals("ED")) || 
/* 229:238 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("YQ")) || 
/* 230:239 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("QB")) || 
/* 231:240 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("QI")) || 
/* 232:241 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("YR")) || 
/* 233:242 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("E2")) || (dt.getTaxMiscFeeType1().length() > 2) ? 
/* 234:243 */               !dt.getTaxMiscFeeType1().substring(0, dt.getTaxMiscFeeType1().length()).equals("VATEC") : 
/* 235:244 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("LL")) || 
/* 236:245 */               (dt.getTaxMiscFeeType1().length() > 2) ? 
/* 237:246 */               !dt.getTaxMiscFeeType1().substring(0, dt.getTaxMiscFeeType1().length()).equals("EC1") : 
/* 238:247 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("LL")) || 
/* 239:248 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("MF")) || 
/* 240:249 */               (dt.getTaxMiscFeeType1().substring(0, 2).equals("CP")))) {
/* 241:251 */               if (dt.getTaxMiscFeeAmt1().compareTo(BigDecimal.ZERO) != 0)
/* 242:    */               {
/* 243:253 */                 String codigoProducto = dt.getTaxMiscFeeType1().replace(" ", "");
/* 244:254 */                 DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 245:255 */                 facturaCliente.getListaDetalleFacturaCliente().add(dfc);
/* 246:256 */                 Producto producto = this.servicioProducto.buscarPorCodigo(codigoProducto, bsp.getIdOrganizacion(), null);
/* 247:257 */                 dfc.setCantidad(BigDecimal.ONE);
/* 248:258 */                 dfc.setUnidadVenta(producto.getUnidadVenta());
/* 249:259 */                 dfc.setProducto(producto);
/* 250:260 */                 dfc.setEliminado(false);
/* 251:261 */                 dfc.setFacturaCliente(facturaCliente);
/* 252:262 */                 dfc.setDescripcion(codigoProducto + "   " + producto.getNombre());
/* 253:263 */                 dfc.setPrecio(dt.getTaxMiscFeeAmt1());
/* 254:    */               }
/* 255:    */             }
/* 256:268 */             if ((dt.getTaxMiscFeeType1() != null) && (!dt.getTaxMiscFeeType1().substring(0, 2).equals("WT")) && 
/* 257:269 */               (!dt.getTaxMiscFeeType1().substring(0, 2).equals("ED")) && (!dt.getTaxMiscFeeType1().substring(0, 2).equals("YQ")) && 
/* 258:270 */               (!dt.getTaxMiscFeeType1().substring(0, 2).equals("QB")) && (!dt.getTaxMiscFeeType1().substring(0, 2).equals("QI")) && 
/* 259:271 */               (!dt.getTaxMiscFeeType1().substring(0, 2).equals("YR")) && (!dt.getTaxMiscFeeType1().substring(0, 2).equals("E2")) && 
/* 260:272 */               (!dt.getTaxMiscFeeType1().substring(0, 2).equals("EC")) && (dt.getTaxMiscFeeType1().length() > 2) ? 
/* 261:273 */               !dt.getTaxMiscFeeType1().substring(0, 5).equals("VATEC") : (!dt.getTaxMiscFeeType1().substring(0, 2).equals("EC")) && 
/* 262:274 */               (dt.getTaxMiscFeeType1().length() > 2) ? !dt.getTaxMiscFeeType1().substring(0, 3).equals("EC1") : 
/* 263:275 */               (!dt.getTaxMiscFeeType1().substring(0, 2).equals("EC")) && 
/* 264:276 */               (!dt.getTaxMiscFeeType1().substring(0, 2).equals("MF")) && (!dt.getTaxMiscFeeType1().substring(0, 2).equals("CP"))) {
/* 265:278 */               if (dt.getTaxMiscFeeAmt1().compareTo(BigDecimal.ZERO) != 0)
/* 266:    */               {
/* 267:279 */                 String codigoProducto = dt.getTaxMiscFeeType1().replace(" ", "");
/* 268:280 */                 DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 269:281 */                 facturaCliente.getListaDetalleFacturaCliente().add(dfc);
/* 270:282 */                 Producto producto = this.servicioProducto.buscarPorCodigo(codigoProducto, bsp.getIdOrganizacion(), null);
/* 271:283 */                 dfc.setCantidad(BigDecimal.ONE);
/* 272:284 */                 dfc.setUnidadVenta(producto.getUnidadVenta());
/* 273:285 */                 dfc.setProducto(producto);
/* 274:286 */                 dfc.setEliminado(false);
/* 275:287 */                 dfc.setFacturaCliente(facturaCliente);
/* 276:288 */                 dfc.setDescripcion(codigoProducto + "   " + producto.getNombre());
/* 277:289 */                 dfc.setPrecio(dt.getTaxMiscFeeAmt1().compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ONE : dt.getTaxMiscFeeAmt1());
/* 278:    */               }
/* 279:    */             }
/* 280:    */           }
/* 281:    */         }
/* 282:    */       }
/* 283:    */     }
/* 284:    */     FacturaCliente facturaCliente;
/* 285:    */     Empresa em;
/* 286:302 */     FacturaCliente fc = new FacturaCliente();
/* 287:303 */     fc.setDocumento(documento);
/* 288:304 */     fc = this.servicioFacturaCliente.cargarSecuencia(fc, AppUtil.getPuntoDeVenta());
/* 289:    */     
/* 290:306 */     String[] numeroFactura = fc.getNumero().split("-");
/* 291:307 */     int numero = Integer.valueOf(numeroFactura[2]).intValue();
/* 292:309 */     for (FacturaCliente facturaCliente : (List)listaFacturas) {
/* 293:311 */       if (facturaCliente.getListaDetalleFacturaCliente().size() > 0)
/* 294:    */       {
/* 295:313 */         facturaCliente.setNumero(numeroFactura[0] + "-" + numeroFactura[1] + "-" + numero);
/* 296:314 */         numero++;
/* 297:    */         
/* 298:316 */         facturaCliente.setDocumento(documento);
/* 299:317 */         this.servicioFacturaCliente.totalizar(facturaCliente);
/* 300:318 */         this.servicioFacturaCliente.generarCuentaPorCobrar(facturaCliente);
/* 301:319 */         this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 302:320 */         this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, AppUtil.getPuntoDeVenta());
/* 303:321 */         this.servicioFacturaCliente.guardar(facturaCliente);
/* 304:    */       }
/* 305:    */     }
/* 306:326 */     bsp.setEstado(Estado.FACTURADO);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void validacionArchivoBSP(int idOrganizacion, String referenciaArchivo, boolean indicadorRespaldo)
/* 310:    */     throws AS2Exception
/* 311:    */   {
/* 312:331 */     CargaArchivo bsp = this.cargaBSPDao.buscarBSPReferenciaIndicador(idOrganizacion, referenciaArchivo, indicadorRespaldo);
/* 313:332 */     if (bsp != null) {
/* 314:333 */       throw new AS2Exception("com.asinfo.as2.ventas.procesos.aerolineas.servicio.impl.ServicioCargaBSPImpl.YA_EXISTE_EL_ARCHIVO_BSP_EN_EL_SISTEMA", new String[] { "" });
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Subempresa validarAgenCode(int idOrganizacion, String codeAgent)
/* 319:    */     throws AS2Exception
/* 320:    */   {
/* 321:339 */     Subempresa subempresa = this.subempresaDao.buscarSubempresaPorCodigo(idOrganizacion, codeAgent);
/* 322:341 */     if (subempresa == null) {
/* 323:342 */       throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioInterfazContableProcesoImpl.NO_EXISTE_CODE_AGENT", new String[] { codeAgent });
/* 324:    */     }
/* 325:344 */     return subempresa;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void validarDocumentoAerolinea(List<Documento> listaDocumento)
/* 329:    */     throws AS2Exception
/* 330:    */   {
/* 331:349 */     if ((listaDocumento == null) || (listaDocumento.size() == 0)) {
/* 332:350 */       throw new AS2Exception("com.asinfo.as2.ventas.procesos.aerolineas.servicio.impl.ServicioCargaBSPImpl.NO_EXISTE_DOCUMENTO_AEROLINEA", new String[] { "" });
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public Asiento contabilizar(List<Ticket> listaTicket, PuntoDeVenta puntoVenta)
/* 337:    */     throws ExcepcionAS2, AS2Exception
/* 338:    */   {
/* 339:356 */     Asiento asiento = new Asiento();
/* 340:357 */     asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 341:358 */     asiento.setSucursal(AppUtil.getSucursal());
/* 342:359 */     asiento.setFecha(new Date());
/* 343:360 */     asiento.setNumero("");
/* 344:361 */     asiento.setEstado(Estado.ELABORADO);
/* 345:362 */     asiento.setIndicadorAutomatico(true);
/* 346:363 */     asiento.setConcepto("ventas directas");
/* 347:    */     
/* 348:365 */     BigDecimal totalEfectivo = BigDecimal.ZERO;
/* 349:366 */     CuentaContable cuentaContableEfectivo = null;
/* 350:    */     
/* 351:368 */     BigDecimal totalTarjetaCredito = BigDecimal.ZERO;
/* 352:369 */     CuentaContable cuentaContableTarjetaCredito = null;
/* 353:    */     
/* 354:371 */     BigDecimal totalCuentasCorporativas = BigDecimal.ZERO;
/* 355:372 */     CuentaContable cuentaContableCuentasCorporativas = null;
/* 356:    */     
/* 357:374 */     BigDecimal totalExcesoFaltante = BigDecimal.ZERO;
/* 358:375 */     CuentaContable cuentaContableExcesoFaltante = null;
/* 359:    */     
/* 360:377 */     BigDecimal totalTarifaDocePorciento = BigDecimal.ZERO;
/* 361:378 */     CuentaContable cuentaContableTarifaDocePorciento = null;
/* 362:    */     
/* 363:380 */     BigDecimal totalTarifaCeroPorciento = BigDecimal.ZERO;
/* 364:381 */     CuentaContable cuentaContableTarifaCeroPorciento = null;
/* 365:    */     
/* 366:383 */     BigDecimal totalYQDocePorciento = BigDecimal.ZERO;
/* 367:384 */     CuentaContable cuentaContableYQDocePorciento = null;
/* 368:    */     
/* 369:386 */     BigDecimal totalYQCeroPorciento = BigDecimal.ZERO;
/* 370:387 */     CuentaContable cuentaContableYQCeroPorciento = null;
/* 371:    */     
/* 372:389 */     BigDecimal totalAnticipo = BigDecimal.ZERO;
/* 373:390 */     CuentaContable cuentaContableAnticipo = null;
/* 374:    */     
/* 375:392 */     BigDecimal totalDescuento = BigDecimal.ZERO;
/* 376:393 */     CuentaContable cuentaContableDescuento = null;
/* 377:    */     
/* 378:395 */     BigDecimal totalPenalidadesYServicios = BigDecimal.ZERO;
/* 379:396 */     CuentaContable cuentaContablePenalidadesYServicios = null;
/* 380:    */     
/* 381:398 */     BigDecimal totalEC = BigDecimal.ZERO;
/* 382:399 */     CuentaContable cuentaContableEC = null;
/* 383:    */     
/* 384:401 */     BigDecimal totalED = BigDecimal.ZERO;
/* 385:402 */     CuentaContable cuentaContableED = null;
/* 386:    */     
/* 387:404 */     BigDecimal totalE2 = BigDecimal.ZERO;
/* 388:405 */     CuentaContable cuentaContableE2 = null;
/* 389:    */     
/* 390:407 */     BigDecimal totalQB = BigDecimal.ZERO;
/* 391:408 */     CuentaContable cuentaContableQB = null;
/* 392:    */     
/* 393:410 */     BigDecimal totalQI = BigDecimal.ZERO;
/* 394:411 */     CuentaContable cuentaContableQI = null;
/* 395:    */     
/* 396:413 */     BigDecimal totalWT = BigDecimal.ZERO;
/* 397:414 */     CuentaContable cuentaContableWT = null;
/* 398:    */     
/* 399:416 */     BigDecimal totalImpuestosExtranjeros = BigDecimal.ZERO;
/* 400:417 */     CuentaContable cuentaContableImpuestosExtranjeros = null;
/* 401:    */     
/* 402:419 */     BigDecimal totalMCOPositivos = BigDecimal.ZERO;
/* 403:420 */     BigDecimal totalMCONegativos = BigDecimal.ZERO;
/* 404:421 */     CuentaContable cuentaContableMCO = null;
/* 405:    */     
/* 406:423 */     BigDecimal totalIngresoPorPenalidades = BigDecimal.ZERO;
/* 407:424 */     CuentaContable cuentaContablePenalidadesTkt2 = null;
/* 408:    */     
/* 409:    */ 
/* 410:427 */     BigDecimal totalTarjetaCreditoETKT = BigDecimal.ZERO;
/* 411:428 */     CuentaContable cuentaContableTarjetaCreditoETKT = null;
/* 412:429 */     BigDecimal totalECETKT = BigDecimal.ZERO;
/* 413:430 */     BigDecimal totalEDETKT = BigDecimal.ZERO;
/* 414:431 */     BigDecimal totalE2ETKT = BigDecimal.ZERO;
/* 415:432 */     BigDecimal totalQBETKT = BigDecimal.ZERO;
/* 416:433 */     BigDecimal totalQIETKT = BigDecimal.ZERO;
/* 417:434 */     BigDecimal totalWTETKT = BigDecimal.ZERO;
/* 418:435 */     BigDecimal totalImpuestosExtranjerosETKT = BigDecimal.ZERO;
/* 419:    */     
/* 420:437 */     BigDecimal totalTarifaDocePorcientoETKT = BigDecimal.ZERO;
/* 421:438 */     BigDecimal totalYQDocePorcientoETKT = BigDecimal.ZERO;
/* 422:439 */     BigDecimal totalTarifaCeroPorcientoETKT = BigDecimal.ZERO;
/* 423:440 */     BigDecimal totalYQCeroPorcientoETKT = BigDecimal.ZERO;
/* 424:    */     
/* 425:442 */     BigDecimal totalPenalidadesYServiciosETKT = BigDecimal.ZERO;
/* 426:443 */     BigDecimal totalIngresoPorPenalidadesETKT = BigDecimal.ZERO;
/* 427:    */     
/* 428:445 */     BigDecimal totalDescuentoETKT = BigDecimal.ZERO;
/* 429:446 */     CuentaContable cuentaContableDescuentoETKT = null;
/* 430:    */     
/* 431:448 */     BigDecimal totalTarifaETKT = BigDecimal.ZERO;
/* 432:    */     
/* 433:450 */     String referenciaEfectivo = "";
/* 434:451 */     String referenciaTarjetaDeCredito = "";
/* 435:452 */     String referenciaCuentasCoorporativas = "";
/* 436:453 */     String referenciaExcesoFaltante = "";
/* 437:454 */     String referenciaTarifaDocePorCiento = "";
/* 438:455 */     String referenciaTarifaCeroPorCiento = "";
/* 439:456 */     String referenciaTarifaYQDocePorCiento = "";
/* 440:457 */     String referenciaTarifaYQCeroPorCiento = "";
/* 441:458 */     String referenciaAnticipo = "";
/* 442:459 */     String referenciaDescuento = "";
/* 443:460 */     String referenciaPenalidadesYServicios = "";
/* 444:461 */     String referenciaEC = "";
/* 445:462 */     String referenciaED = "";
/* 446:463 */     String referenciaE2 = "";
/* 447:464 */     String referenciaQB = "";
/* 448:465 */     String referenciaQI = "";
/* 449:466 */     String referenciaWT = "";
/* 450:467 */     String referenciaImpuestosExtranjeros = "";
/* 451:    */     
/* 452:469 */     String referenciaECETKT = "";
/* 453:470 */     String referenciaDescuentoETKT = "";
/* 454:471 */     String referenciaEDETKT = "";
/* 455:472 */     String referenciaE2ETKT = "";
/* 456:473 */     String referenciaQBETKT = "";
/* 457:474 */     String referenciaQIETKT = "";
/* 458:475 */     String referenciaWTETKT = "";
/* 459:476 */     String referenciaImpuestosExtranjerosETKT = "";
/* 460:477 */     String referenciaTarifaDocePorCientoETKT = "";
/* 461:478 */     String referenciaTarifaCeroPorCientoETKT = "";
/* 462:479 */     String referenciaTarifaYQDocePorCientoETKT = "";
/* 463:480 */     String referenciaTarifaYQCeroPorCientoETKT = "";
/* 464:    */     
/* 465:482 */     HashMap<String, String> filters = new HashMap();
/* 466:483 */     filters.put("idOrganizacion", "=" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 467:484 */     List<ConfiguracionCargaTicket> listConfiguraciones = this.servicioCatalogoConfiguracionTicket.obtenerListaCombo(ConfiguracionCargaTicket.class, "orden", true, filters);
/* 468:487 */     for (ConfiguracionCargaTicket tic : listConfiguraciones)
/* 469:    */     {
/* 470:489 */       if (tic.getNombreEtiqueta().equals("EFECTIVO"))
/* 471:    */       {
/* 472:490 */         cuentaContableEfectivo = tic.getCuentaContableTKT1();
/* 473:491 */         referenciaEfectivo = tic.getReferencia();
/* 474:    */       }
/* 475:493 */       if (tic.getNombreEtiqueta().equals("TARJETA DE CREDITO"))
/* 476:    */       {
/* 477:494 */         cuentaContableTarjetaCredito = tic.getCuentaContableTKT1();
/* 478:495 */         cuentaContableTarjetaCreditoETKT = tic.getCuentaContableTKT2();
/* 479:496 */         referenciaTarjetaDeCredito = tic.getReferencia();
/* 480:    */       }
/* 481:498 */       if (tic.getNombreEtiqueta().equals("CUENTAS CORPORATIVAS"))
/* 482:    */       {
/* 483:499 */         cuentaContableCuentasCorporativas = tic.getCuentaContableTKT1();
/* 484:500 */         referenciaCuentasCoorporativas = tic.getReferencia();
/* 485:    */       }
/* 486:502 */       if (tic.getNombreEtiqueta().equals("EXCESO / FALTANTE"))
/* 487:    */       {
/* 488:503 */         cuentaContableExcesoFaltante = tic.getCuentaContableTKT1();
/* 489:504 */         referenciaExcesoFaltante = tic.getReferencia();
/* 490:    */       }
/* 491:506 */       if (tic.getNombreEtiqueta().equals("TARIFA 12%"))
/* 492:    */       {
/* 493:507 */         cuentaContableTarifaDocePorciento = tic.getCuentaContableTKT1();
/* 494:508 */         referenciaTarifaDocePorCiento = tic.getReferencia();
/* 495:    */       }
/* 496:510 */       if (tic.getNombreEtiqueta().equals("TARIFA 0%"))
/* 497:    */       {
/* 498:511 */         cuentaContableTarifaCeroPorciento = tic.getCuentaContableTKT1();
/* 499:512 */         referenciaTarifaCeroPorCiento = tic.getReferencia();
/* 500:    */       }
/* 501:514 */       if (tic.getNombreEtiqueta().equals("YQ 12%"))
/* 502:    */       {
/* 503:515 */         cuentaContableYQDocePorciento = tic.getCuentaContableTKT1();
/* 504:516 */         referenciaTarifaYQDocePorCiento = tic.getReferencia();
/* 505:    */       }
/* 506:518 */       if (tic.getNombreEtiqueta().equals("YQ 0%"))
/* 507:    */       {
/* 508:519 */         cuentaContableYQCeroPorciento = tic.getCuentaContableTKT1();
/* 509:520 */         referenciaTarifaYQCeroPorCiento = tic.getReferencia();
/* 510:    */       }
/* 511:522 */       if (tic.getNombreEtiqueta().equals("ANTICIPO"))
/* 512:    */       {
/* 513:523 */         cuentaContableAnticipo = tic.getCuentaContableTKT1();
/* 514:524 */         referenciaAnticipo = tic.getReferencia();
/* 515:    */       }
/* 516:526 */       if (tic.getNombreEtiqueta().equals("DESCUENTO"))
/* 517:    */       {
/* 518:527 */         cuentaContableDescuentoETKT = tic.getCuentaContableTKT1();
/* 519:528 */         cuentaContableDescuento = tic.getCuentaContableTKT1();
/* 520:529 */         referenciaDescuento = tic.getReferencia();
/* 521:530 */         referenciaDescuentoETKT = tic.getReferencia2();
/* 522:    */       }
/* 523:532 */       if (tic.getNombreEtiqueta().equals("PENALIDADES Y SERVICIOS"))
/* 524:    */       {
/* 525:533 */         cuentaContablePenalidadesTkt2 = tic.getCuentaContableTKT2();
/* 526:534 */         cuentaContablePenalidadesYServicios = tic.getCuentaContableTKT1();
/* 527:535 */         referenciaPenalidadesYServicios = tic.getReferencia();
/* 528:    */       }
/* 529:537 */       if (tic.getNombreEtiqueta().equals("EC"))
/* 530:    */       {
/* 531:538 */         cuentaContableEC = tic.getCuentaContableTKT1();
/* 532:539 */         referenciaEC = tic.getReferencia();
/* 533:540 */         referenciaECETKT = tic.getReferencia2();
/* 534:    */       }
/* 535:542 */       if (tic.getNombreEtiqueta().equals("ED"))
/* 536:    */       {
/* 537:543 */         cuentaContableED = tic.getCuentaContableTKT1();
/* 538:544 */         referenciaED = tic.getReferencia();
/* 539:545 */         referenciaEDETKT = tic.getReferencia2();
/* 540:    */       }
/* 541:547 */       if (tic.getNombreEtiqueta().equals("E2"))
/* 542:    */       {
/* 543:548 */         cuentaContableE2 = tic.getCuentaContableTKT1();
/* 544:549 */         referenciaE2 = tic.getReferencia();
/* 545:550 */         referenciaE2ETKT = tic.getReferencia2();
/* 546:    */       }
/* 547:552 */       if (tic.getNombreEtiqueta().equals("QB"))
/* 548:    */       {
/* 549:553 */         cuentaContableQB = tic.getCuentaContableTKT1();
/* 550:554 */         referenciaQB = tic.getReferencia();
/* 551:555 */         referenciaQBETKT = tic.getReferencia2();
/* 552:    */       }
/* 553:557 */       if (tic.getNombreEtiqueta().equals("QI"))
/* 554:    */       {
/* 555:558 */         cuentaContableQI = tic.getCuentaContableTKT1();
/* 556:559 */         referenciaQI = tic.getReferencia();
/* 557:560 */         referenciaQIETKT = tic.getReferencia2();
/* 558:    */       }
/* 559:562 */       if (tic.getNombreEtiqueta().equals("WT"))
/* 560:    */       {
/* 561:563 */         cuentaContableWT = tic.getCuentaContableTKT1();
/* 562:564 */         referenciaWT = tic.getReferencia();
/* 563:565 */         referenciaWTETKT = tic.getReferencia2();
/* 564:    */       }
/* 565:567 */       if ((tic.getNombreEtiqueta().equals("AH")) || (tic.getNombreEtiqueta().equals("AY")) || (tic.getNombreEtiqueta().equals("CA")) || 
/* 566:568 */         (tic.getNombreEtiqueta().equals("CH")) || (tic.getNombreEtiqueta().equals("DE")) || (tic.getNombreEtiqueta().equals("RA")) || 
/* 567:569 */         (tic.getNombreEtiqueta().equals("RC")) || (tic.getNombreEtiqueta().equals("SQ")) || (tic.getNombreEtiqueta().equals("UB")) || 
/* 568:570 */         (tic.getNombreEtiqueta().equals("UK")) || (tic.getNombreEtiqueta().equals("US")) || (tic.getNombreEtiqueta().equals("XA")) || 
/* 569:571 */         (tic.getNombreEtiqueta().equals("XD")) || (tic.getNombreEtiqueta().equals("XF")) || (tic.getNombreEtiqueta().equals("XG")) || 
/* 570:572 */         (tic.getNombreEtiqueta().equals("XY")) || (tic.getNombreEtiqueta().equals("YC")) || (tic.getNombreEtiqueta().equals("ZO")))
/* 571:    */       {
/* 572:573 */         cuentaContableImpuestosExtranjeros = tic.getCuentaContableTKT1();
/* 573:574 */         referenciaImpuestosExtranjeros = tic.getReferencia();
/* 574:575 */         referenciaImpuestosExtranjerosETKT = tic.getReferencia2();
/* 575:    */       }
/* 576:577 */       if (tic.getNombreEtiqueta().equals("ANTICIPO"))
/* 577:    */       {
/* 578:578 */         cuentaContableMCO = tic.getCuentaContableTKT1();
/* 579:579 */         referenciaAnticipo = tic.getReferencia();
/* 580:    */       }
/* 581:    */     }
/* 582:584 */     String fechaPeriodo = "";
/* 583:585 */     String estacion = "";
/* 584:586 */     for (Ticket tic : listaTicket)
/* 585:    */     {
/* 586:587 */       List<String> listaCampos = new ArrayList();
/* 587:588 */       listaCampos.add("puntoDeVenta");
/* 588:589 */       listaCampos.add("listaDetalleTicket");
/* 589:    */       
/* 590:591 */       tic = (Ticket)this.servicioTicket.cargarDetalle(Ticket.class, tic.getIdTicket(), listaCampos);
/* 591:    */       
/* 592:593 */       asiento.setDocumentoReferencia(tic.getPeriodo().toString() + "    " + tic.getPuntoDeVenta().getCodigoAlterno());
/* 593:594 */       asiento.setFecha(tic.getPeriodo());
/* 594:595 */       tic.setAsiento(asiento);
/* 595:596 */       fechaPeriodo = tic.getPeriodo().toString();
/* 596:597 */       estacion = tic.getPuntoDeVenta().getCodigoAlterno().substring(0, 3);
/* 597:599 */       if (tic.getOperacion().equals("TKT"))
/* 598:    */       {
/* 599:600 */         if ((tic.getFormaPago() != null) && (
/* 600:601 */           (tic.getFormaPago().substring(0, 2).equals("CA")) || (tic.getFormaPago().substring(0, 2).equals("CK")))) {
/* 601:602 */           totalEfectivo = totalEfectivo.add(tic.getValorFormaPago());
/* 602:    */         }
/* 603:604 */         if ((tic.getFormaPago() != null) && (tic.getFormaPago().substring(0, 2).equals("CC"))) {
/* 604:605 */           totalTarjetaCredito = totalTarjetaCredito.add(tic.getValorFormaPago());
/* 605:    */         }
/* 606:607 */         if ((tic.getFormaPago() != null) && (tic.getFormaPago().substring(0, 2).equals("RV"))) {
/* 607:608 */           totalCuentasCorporativas = totalCuentasCorporativas.add(tic.getValorFormaPago());
/* 608:    */         }
/* 609:610 */         if (tic.getAnticipo().compareTo(BigDecimal.ZERO) > 0) {
/* 610:611 */           totalMCOPositivos = totalMCOPositivos.add(tic.getAnticipo());
/* 611:    */         }
/* 612:613 */         if (tic.getAnticipo().compareTo(BigDecimal.ZERO) < 0) {
/* 613:614 */           totalMCONegativos = totalMCONegativos.add(tic.getAnticipo());
/* 614:    */         }
/* 615:617 */         totalTarifaDocePorciento = totalTarifaDocePorciento.add(tic.getTarifaDiferenteCero());
/* 616:618 */         totalYQDocePorciento = totalYQDocePorciento.add(tic.getYqDiferenteCero() != null ? tic.getYqDiferenteCero() : BigDecimal.ZERO);
/* 617:619 */         totalTarifaCeroPorciento = totalTarifaCeroPorciento.add(tic.getTarifaCero());
/* 618:620 */         totalYQCeroPorciento = totalYQCeroPorciento.add(tic.getYqCero() != null ? tic.getYqCero() : BigDecimal.ZERO);
/* 619:622 */         if (tic.getCodigoDeServicio().equals("CHANGE FEE")) {
/* 620:623 */           totalIngresoPorPenalidades = totalIngresoPorPenalidades.add(tic.getPenalty());
/* 621:    */         } else {
/* 622:625 */           totalPenalidadesYServicios = totalPenalidadesYServicios.add(tic.getPenalty());
/* 623:    */         }
/* 624:627 */         totalDescuento = totalDescuento.add(tic.getDescuento());
/* 625:    */       }
/* 626:629 */       else if (tic.getOperacion().equals("ETKT"))
/* 627:    */       {
/* 628:630 */         if ((tic.getFormaPago() != null) && (tic.getFormaPago().substring(0, 2).equals("CC"))) {
/* 629:631 */           totalTarjetaCreditoETKT = totalTarjetaCreditoETKT.add(tic.getValorFormaPago());
/* 630:    */         }
/* 631:634 */         totalTarifaDocePorcientoETKT = totalTarifaDocePorcientoETKT.add(tic.getTarifaDiferenteCero());
/* 632:    */         
/* 633:636 */         totalYQDocePorcientoETKT = totalYQDocePorcientoETKT.add(tic.getYqDiferenteCero() != null ? tic.getYqDiferenteCero() : BigDecimal.ZERO);
/* 634:637 */         totalTarifaCeroPorcientoETKT = totalTarifaCeroPorcientoETKT.add(tic.getTarifaCero());
/* 635:638 */         totalYQCeroPorcientoETKT = totalYQCeroPorcientoETKT.add(tic.getYqCero() != null ? tic.getYqCero() : BigDecimal.ZERO);
/* 636:640 */         if (tic.getCodigoDeServicio().equals("CHANGE FEE")) {
/* 637:641 */           totalIngresoPorPenalidadesETKT = totalIngresoPorPenalidadesETKT.add(tic.getPenalty());
/* 638:    */         } else {
/* 639:643 */           totalPenalidadesYServiciosETKT = totalPenalidadesYServiciosETKT.add(tic.getPenalty());
/* 640:    */         }
/* 641:645 */         totalDescuentoETKT = totalDescuentoETKT.add(tic.getDescuento());
/* 642:646 */         totalTarifaETKT = totalTarifaETKT.add(tic.getTarifa());
/* 643:    */       }
/* 644:649 */       for (DetalleTicket dt : tic.getListaDetalleTicket()) {
/* 645:651 */         if (tic.getOperacion().equals("TKT"))
/* 646:    */         {
/* 647:652 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("EC"))) {
/* 648:653 */             totalEC = totalEC.add(dt.getTaxMiscFeeAmt1());
/* 649:    */           }
/* 650:655 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("ED"))) {
/* 651:656 */             totalED = totalED.add(dt.getTaxMiscFeeAmt1());
/* 652:    */           }
/* 653:658 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("E2"))) {
/* 654:659 */             totalE2 = totalE2.add(dt.getTaxMiscFeeAmt1());
/* 655:    */           }
/* 656:661 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("QI"))) {
/* 657:662 */             totalQI = totalQI.add(dt.getTaxMiscFeeAmt1());
/* 658:    */           }
/* 659:664 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("QB"))) {
/* 660:665 */             totalQB = totalQB.add(dt.getTaxMiscFeeAmt1());
/* 661:    */           }
/* 662:667 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("WT"))) {
/* 663:668 */             totalWT = totalWT.add(dt.getTaxMiscFeeAmt1());
/* 664:    */           }
/* 665:670 */           if ((dt.getTaxMiscFeeType1() != null) && (!dt.getIndicadorNacional().booleanValue())) {
/* 666:671 */             totalImpuestosExtranjeros = totalImpuestosExtranjeros.add(dt.getTaxMiscFeeAmt1());
/* 667:    */           }
/* 668:    */         }
/* 669:674 */         else if (tic.getOperacion().equals("ETKT"))
/* 670:    */         {
/* 671:676 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("EC"))) {
/* 672:677 */             totalECETKT = totalECETKT.add(dt.getTaxMiscFeeAmt1());
/* 673:    */           }
/* 674:679 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("WT"))) {
/* 675:680 */             totalWTETKT = totalWTETKT.add(dt.getTaxMiscFeeAmt1());
/* 676:    */           }
/* 677:682 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("ED"))) {
/* 678:683 */             totalEDETKT = totalEDETKT.add(dt.getTaxMiscFeeAmt1());
/* 679:    */           }
/* 680:685 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("QI"))) {
/* 681:686 */             totalQIETKT = totalQIETKT.add(dt.getTaxMiscFeeAmt1());
/* 682:    */           }
/* 683:688 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("QB"))) {
/* 684:689 */             totalQBETKT = totalQBETKT.add(dt.getTaxMiscFeeAmt1());
/* 685:    */           }
/* 686:691 */           if ((dt.getTaxMiscFeeType1() != null) && (dt.getTaxMiscFeeType1().substring(0, 2).equals("E2"))) {
/* 687:692 */             totalE2ETKT = totalE2ETKT.add(dt.getTaxMiscFeeAmt1());
/* 688:    */           }
/* 689:695 */           if ((dt.getTaxMiscFeeType1() != null) && (!dt.getIndicadorNacional().booleanValue())) {
/* 690:696 */             totalImpuestosExtranjerosETKT = totalImpuestosExtranjerosETKT.add(dt.getTaxMiscFeeAmt1());
/* 691:    */           }
/* 692:    */         }
/* 693:    */       }
/* 694:703 */       this.TicketDao.detach(tic);
/* 695:704 */       tic.setIndicadorContabilizado(true);
/* 696:    */     }
/* 697:708 */     for (int i = 1; i <= 34; i++)
/* 698:    */     {
/* 699:709 */       if ((i == 1) && (totalEfectivo.compareTo(BigDecimal.ZERO) != 0)) {
/* 700:710 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableEfectivo.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalEfectivo
/* 701:711 */           .setScale(2, RoundingMode.HALF_UP), true, referenciaEfectivo);
/* 702:    */       }
/* 703:713 */       if ((i == 2) && (totalTarjetaCredito.compareTo(BigDecimal.ZERO) != 0)) {
/* 704:714 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableTarjetaCredito.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalTarjetaCredito
/* 705:715 */           .setScale(2, RoundingMode.HALF_UP), true, referenciaTarjetaDeCredito);
/* 706:    */       }
/* 707:718 */       if ((i == 3) && (totalCuentasCorporativas.compareTo(BigDecimal.ZERO) != 0)) {
/* 708:719 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableCuentasCorporativas.getIdCuentaContable()), fechaPeriodo + "  " + estacion + " (CORP.ACCT.) ", totalCuentasCorporativas
/* 709:720 */           .setScale(2, RoundingMode.HALF_UP), true, referenciaCuentasCoorporativas);
/* 710:    */       }
/* 711:723 */       if ((i == 4) && (totalEC.compareTo(BigDecimal.ZERO) != 0)) {
/* 712:724 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableEC.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalEC
/* 713:725 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaEC);
/* 714:    */       }
/* 715:729 */       if ((i == 5) && (totalED.compareTo(BigDecimal.ZERO) != 0)) {
/* 716:730 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableED.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalED
/* 717:731 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaED);
/* 718:    */       }
/* 719:735 */       if ((i == 6) && (totalE2.compareTo(BigDecimal.ZERO) != 0)) {
/* 720:736 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableE2.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalE2
/* 721:737 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaE2);
/* 722:    */       }
/* 723:741 */       if ((i == 7) && (totalQB.compareTo(BigDecimal.ZERO) != 0)) {
/* 724:742 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableQB.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalQB
/* 725:743 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaQB);
/* 726:    */       }
/* 727:747 */       if ((i == 8) && (totalQI.compareTo(BigDecimal.ZERO) != 0)) {
/* 728:748 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableQI.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalQI
/* 729:749 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaQI);
/* 730:    */       }
/* 731:753 */       if ((i == 9) && (totalWT.compareTo(BigDecimal.ZERO) != 0)) {
/* 732:754 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableWT.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalWT
/* 733:755 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaWT);
/* 734:    */       }
/* 735:760 */       if ((i == 10) && (totalMCOPositivos.compareTo(BigDecimal.ZERO) != 0)) {
/* 736:761 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableMCO.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalMCOPositivos
/* 737:762 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaAnticipo);
/* 738:    */       }
/* 739:764 */       if ((i == 11) && (totalMCONegativos.compareTo(BigDecimal.ZERO) != 0)) {
/* 740:765 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableMCO.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalMCONegativos
/* 741:766 */           .abs().setScale(2, RoundingMode.HALF_UP), true, referenciaAnticipo);
/* 742:    */       }
/* 743:768 */       if ((i == 12) && (totalImpuestosExtranjeros.compareTo(BigDecimal.ZERO) != 0)) {
/* 744:769 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableImpuestosExtranjeros.getIdCuentaContable()), fechaPeriodo + "  " + estacion + " (XT)", totalImpuestosExtranjeros
/* 745:770 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaImpuestosExtranjeros);
/* 746:    */       }
/* 747:773 */       if ((i == 13) && (totalTarifaDocePorciento.compareTo(BigDecimal.ZERO) != 0)) {
/* 748:774 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableTarifaDocePorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalTarifaDocePorciento
/* 749:775 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaDocePorCiento);
/* 750:    */       }
/* 751:780 */       if ((i == 14) && (totalYQDocePorciento.compareTo(BigDecimal.ZERO) > 0)) {
/* 752:781 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableYQDocePorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalYQDocePorciento
/* 753:782 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaYQDocePorCiento);
/* 754:    */       }
/* 755:784 */       if ((i == 14) && (totalYQDocePorciento.compareTo(BigDecimal.ZERO) < 0))
/* 756:    */       {
/* 757:785 */         totalYQDocePorciento = totalYQDocePorciento.multiply(new BigDecimal(-1));
/* 758:786 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableYQDocePorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalYQDocePorciento
/* 759:787 */           .setScale(2, RoundingMode.HALF_UP), true, referenciaTarifaYQDocePorCiento);
/* 760:    */       }
/* 761:791 */       if ((i == 15) && (totalTarifaCeroPorciento.compareTo(BigDecimal.ZERO) != 0)) {
/* 762:792 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableTarifaCeroPorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalTarifaCeroPorciento
/* 763:793 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaCeroPorCiento);
/* 764:    */       }
/* 765:796 */       if ((i == 16) && (totalYQCeroPorciento.compareTo(BigDecimal.ZERO) != 0)) {
/* 766:797 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableYQCeroPorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalYQCeroPorciento
/* 767:798 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaYQCeroPorCiento);
/* 768:    */       }
/* 769:800 */       if ((i == 17) && (totalPenalidadesYServicios.compareTo(BigDecimal.ZERO) != 0)) {
/* 770:801 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContablePenalidadesYServicios.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalPenalidadesYServicios
/* 771:802 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaPenalidadesYServicios);
/* 772:    */       }
/* 773:805 */       if ((i == 18) && (totalDescuento.compareTo(BigDecimal.ZERO) != 0)) {
/* 774:806 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableDescuento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalDescuento
/* 775:807 */           .setScale(2, RoundingMode.HALF_UP), true, referenciaDescuento);
/* 776:    */       }
/* 777:809 */       if ((i == 19) && (totalIngresoPorPenalidades.compareTo(BigDecimal.ZERO) != 0)) {
/* 778:810 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContablePenalidadesTkt2.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalIngresoPorPenalidades
/* 779:811 */           .setScale(2, RoundingMode.HALF_UP), false, "");
/* 780:    */       }
/* 781:813 */       if ((i == 20) && (totalTarjetaCreditoETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 782:814 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableTarjetaCreditoETKT.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalTarjetaCreditoETKT
/* 783:815 */           .setScale(2, RoundingMode.HALF_UP), true, "");
/* 784:    */       }
/* 785:818 */       if ((i == 21) && (totalECETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 786:819 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableEC.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalECETKT
/* 787:820 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaECETKT);
/* 788:    */       }
/* 789:822 */       if ((i == 22) && (totalWTETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 790:823 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableWT.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalWTETKT
/* 791:824 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaWTETKT);
/* 792:    */       }
/* 793:827 */       if ((i == 23) && (totalEDETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 794:828 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableED.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalEDETKT
/* 795:829 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaEDETKT);
/* 796:    */       }
/* 797:831 */       if ((i == 24) && (totalQIETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 798:832 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableQI.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalQIETKT
/* 799:833 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaQIETKT);
/* 800:    */       }
/* 801:835 */       if ((i == 25) && (totalQBETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 802:836 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableQB.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalQBETKT
/* 803:837 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaQBETKT);
/* 804:    */       }
/* 805:839 */       if ((i == 26) && (totalE2ETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 806:840 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableE2.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalE2ETKT
/* 807:841 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaE2ETKT);
/* 808:    */       }
/* 809:843 */       if ((i == 27) && (totalImpuestosExtranjerosETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 810:844 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableImpuestosExtranjeros.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalImpuestosExtranjerosETKT
/* 811:845 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaImpuestosExtranjerosETKT);
/* 812:    */       }
/* 813:848 */       if ((i == 28) && (totalTarifaDocePorcientoETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 814:849 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableTarifaDocePorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalTarifaDocePorcientoETKT
/* 815:850 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaDocePorCientoETKT);
/* 816:    */       }
/* 817:853 */       if ((i == 29) && (totalYQDocePorcientoETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 818:854 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableYQDocePorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalYQDocePorcientoETKT
/* 819:855 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaYQDocePorCientoETKT);
/* 820:    */       }
/* 821:857 */       if ((i == 30) && (totalTarifaCeroPorcientoETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 822:858 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableTarifaCeroPorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalTarifaCeroPorcientoETKT
/* 823:859 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaCeroPorCientoETKT);
/* 824:    */       }
/* 825:862 */       if ((i == 31) && (totalYQCeroPorcientoETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 826:863 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableYQCeroPorciento.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalYQCeroPorcientoETKT
/* 827:864 */           .setScale(2, RoundingMode.HALF_UP), false, referenciaTarifaYQCeroPorCientoETKT);
/* 828:    */       }
/* 829:867 */       if ((i == 32) && (totalPenalidadesYServiciosETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 830:868 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContablePenalidadesYServicios.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalPenalidadesYServiciosETKT
/* 831:869 */           .setScale(2, RoundingMode.HALF_UP), false, "");
/* 832:    */       }
/* 833:871 */       if ((i == 33) && (totalIngresoPorPenalidadesETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 834:872 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContablePenalidadesTkt2.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalIngresoPorPenalidadesETKT
/* 835:873 */           .setScale(2, RoundingMode.HALF_UP), false, "");
/* 836:    */       }
/* 837:875 */       if ((i == 34) && (totalDescuentoETKT.compareTo(BigDecimal.ZERO) != 0)) {
/* 838:876 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableDescuentoETKT.getIdCuentaContable()), fechaPeriodo + "  " + estacion, totalDescuentoETKT
/* 839:877 */           .setScale(2, RoundingMode.HALF_UP), true, "");
/* 840:    */       }
/* 841:    */     }
/* 842:881 */     totalizarAsiento(asiento);
/* 843:882 */     return asiento;
/* 844:    */   }
/* 845:    */   
/* 846:    */   public void crearDetalleAsiento(Asiento asiento, CuentaContable cuentaContable, String descripcion, BigDecimal valor, boolean debe, String referencia)
/* 847:    */   {
/* 848:890 */     if (valor.compareTo(BigDecimal.ZERO) < 0)
/* 849:    */     {
/* 850:891 */       debe = !debe;
/* 851:892 */       valor = valor.negate();
/* 852:    */     }
/* 853:897 */     DetalleAsiento detalleAsiento = new DetalleAsiento();
/* 854:898 */     detalleAsiento.setAsiento(asiento);
/* 855:899 */     detalleAsiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 856:900 */     detalleAsiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 857:901 */     detalleAsiento.setCuentaContable(cuentaContable);
/* 858:902 */     detalleAsiento.setDescripcion(descripcion);
/* 859:903 */     detalleAsiento.setDescripcion2(referencia);
/* 860:904 */     if (debe)
/* 861:    */     {
/* 862:905 */       detalleAsiento.setDebe(valor);
/* 863:906 */       detalleAsiento.setHaber(BigDecimal.ZERO);
/* 864:    */     }
/* 865:    */     else
/* 866:    */     {
/* 867:908 */       detalleAsiento.setHaber(valor);
/* 868:909 */       detalleAsiento.setDebe(BigDecimal.ZERO);
/* 869:    */     }
/* 870:911 */     asiento.getListaDetalleAsiento().add(detalleAsiento);
/* 871:    */   }
/* 872:    */   
/* 873:    */   public void totalizarAsiento(Asiento asiento)
/* 874:    */   {
/* 875:917 */     asiento.setTotalDebe(BigDecimal.ZERO);
/* 876:918 */     asiento.setTotalHaber(BigDecimal.ZERO);
/* 877:920 */     for (DetalleAsiento da : asiento.getListaDetalleAsiento())
/* 878:    */     {
/* 879:921 */       asiento.setTotalDebe(asiento.getTotalDebe().add(da.getDebe()));
/* 880:922 */       asiento.setTotalHaber(asiento.getTotalHaber().add(da.getHaber()));
/* 881:    */     }
/* 882:    */   }
/* 883:    */   
/* 884:    */   public List<Ticket> obtenerListaTicketPorFechas(List<Asiento> listaAsiento, Date fechaDesde, Date fechaHasta, int idOrganizacion, PuntoDeVenta puntoDeVenta)
/* 885:    */     throws ExcepcionAS2, AS2Exception
/* 886:    */   {
/* 887:930 */     List<Object[]> listaTicketFiltros = this.cargaBSPDao.obtenerListaTicketPorFechas(fechaDesde, fechaHasta, idOrganizacion, puntoDeVenta);
/* 888:    */     
/* 889:932 */     validaciones(listaTicketFiltros);
/* 890:    */     
/* 891:934 */     List<Ticket> listaTickets = new ArrayList();
/* 892:935 */     List<Ticket> listaTicketsGlobal = new ArrayList();
/* 893:936 */     for (Object[] o : listaTicketFiltros)
/* 894:    */     {
/* 895:937 */       Date fecha = (Date)o[0];
/* 896:938 */       PuntoDeVenta puntoVenta = (PuntoDeVenta)o[1];
/* 897:939 */       listaTickets = obtenerListaTicke(fecha, puntoVenta, idOrganizacion);
/* 898:941 */       for (Ticket ti : listaTickets) {
/* 899:942 */         listaTicketsGlobal.add(ti);
/* 900:    */       }
/* 901:945 */       Asiento as = contabilizar(listaTickets, puntoVenta);
/* 902:946 */       if (as.getListaDetalleAsiento().size() > 0) {
/* 903:947 */         listaAsiento.add(as);
/* 904:    */       }
/* 905:    */     }
/* 906:951 */     return listaTicketsGlobal;
/* 907:    */   }
/* 908:    */   
/* 909:    */   public List<Ticket> obtenerListaTicke(Date fecha, PuntoDeVenta puntoVenta, int idOrganizacion)
/* 910:    */   {
/* 911:955 */     return this.cargaBSPDao.obtenerListaTicke(fecha, puntoVenta, idOrganizacion);
/* 912:    */   }
/* 913:    */   
/* 914:    */   public void validaciones(List<Object[]> listaObjetos)
/* 915:    */     throws AS2Exception
/* 916:    */   {
/* 917:960 */     System.out.println("tamaño lista para validacion    " + listaObjetos.size());
/* 918:962 */     if (listaObjetos.size() == 0) {
/* 919:963 */       throw new AS2Exception("com.asinfo.as2.ventas.procesos.aerolineas.servicio.impl.ServicioCargaVentaTicketImpl.NO_EXISTEN_TICKETS_PARA_EN_EL_RANGO_FECHA", new String[] { "" });
/* 920:    */     }
/* 921:    */   }
/* 922:    */   
/* 923:    */   public List<Asiento> listaAsientos(List<Ticket> listaTicket)
/* 924:    */   {
/* 925:970 */     return this.cargaBSPDao.listaAsientosAnular(listaTicket);
/* 926:    */   }
/* 927:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.servicio.impl.ServicioCargaVentaTicketImpl
 * JD-Core Version:    0.7.0.1
 */