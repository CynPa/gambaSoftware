/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.dao.ContratoVentaDao;
/*   5:    */ import com.asinfo.as2.dao.DetalleValoresContratoVentaDao;
/*   6:    */ import com.asinfo.as2.dao.DetallesFacturaContratoVentaDao;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  10:    */ import com.asinfo.as2.entities.Canal;
/*  11:    */ import com.asinfo.as2.entities.Cliente;
/*  12:    */ import com.asinfo.as2.entities.CondicionPago;
/*  13:    */ import com.asinfo.as2.entities.ContratoVenta;
/*  14:    */ import com.asinfo.as2.entities.ContratoVentaFacturaContratoVenta;
/*  15:    */ import com.asinfo.as2.entities.DetalleContratoVenta;
/*  16:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  17:    */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*  18:    */ import com.asinfo.as2.entities.DetallesFacturaContratoVenta;
/*  19:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  20:    */ import com.asinfo.as2.entities.Documento;
/*  21:    */ import com.asinfo.as2.entities.Empresa;
/*  22:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  23:    */ import com.asinfo.as2.entities.Organizacion;
/*  24:    */ import com.asinfo.as2.entities.Producto;
/*  25:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  26:    */ import com.asinfo.as2.entities.Subempresa;
/*  27:    */ import com.asinfo.as2.entities.Sucursal;
/*  28:    */ import com.asinfo.as2.entities.Zona;
/*  29:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  30:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  31:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  32:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  33:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  34:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  35:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  36:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  37:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  38:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  39:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*  40:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDetalleFacturaContratoVenta;
/*  41:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  42:    */ import java.io.PrintStream;
/*  43:    */ import java.math.BigDecimal;
/*  44:    */ import java.math.RoundingMode;
/*  45:    */ import java.util.ArrayList;
/*  46:    */ import java.util.Calendar;
/*  47:    */ import java.util.Date;
/*  48:    */ import java.util.HashMap;
/*  49:    */ import java.util.List;
/*  50:    */ import java.util.Map;
/*  51:    */ import java.util.Map.Entry;
/*  52:    */ import javax.ejb.EJB;
/*  53:    */ import javax.ejb.SessionContext;
/*  54:    */ import javax.ejb.Stateless;
/*  55:    */ import javax.ejb.TransactionAttribute;
/*  56:    */ import javax.ejb.TransactionAttributeType;
/*  57:    */ import javax.ejb.TransactionManagement;
/*  58:    */ import javax.ejb.TransactionManagementType;
/*  59:    */ 
/*  60:    */ @Stateless
/*  61:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  62:    */ public class ServicioDetalleFacturaContratoVentaImpl
/*  63:    */   extends AbstractServicioAS2
/*  64:    */   implements ServicioDetalleFacturaContratoVenta
/*  65:    */ {
/*  66:    */   private static final long serialVersionUID = -9214403611253077141L;
/*  67:    */   @EJB
/*  68:    */   private DetallesFacturaContratoVentaDao detallesFacturaContratoVentaDao;
/*  69:    */   @EJB
/*  70:    */   private DetalleValoresContratoVentaDao detalleValoresContratoVentaDao;
/*  71:    */   @EJB
/*  72:    */   private ServicioContratoVenta servicioContratoVenta;
/*  73:    */   @EJB
/*  74:    */   private ContratoVentaDao contratoVentaDao;
/*  75:    */   @EJB
/*  76:    */   private ServicioSucursal servicioSucursal;
/*  77:    */   @EJB
/*  78:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  79:    */   @EJB
/*  80:    */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  81:    */   @EJB
/*  82:    */   private ServicioCondicionPago servicioCondicionPago;
/*  83:    */   @EJB
/*  84:    */   private ServicioDocumento servicioDocumento;
/*  85:    */   @EJB
/*  86:    */   private ServicioEmpresa servicioEmpresa;
/*  87:    */   @EJB
/*  88:    */   private ServicioDetalleFacturaContratoVenta servicioDetalleFacturaContratoVenta;
/*  89:    */   
/*  90:    */   public List<DetallesFacturaContratoVenta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  91:    */   {
/*  92: 98 */     return this.detallesFacturaContratoVentaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int contarPorCriterio(Map<String, String> filters)
/*  96:    */   {
/*  97:103 */     return this.detallesFacturaContratoVentaDao.contarPorCriterio(filters);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void guardar(DetallesFacturaContratoVenta detallesFacturaContratoVenta)
/* 101:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 102:    */   {
/* 103:108 */     this.detallesFacturaContratoVentaDao.guardar(detallesFacturaContratoVenta);
/* 104:    */   }
/* 105:    */   
/* 106:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 107:    */   public void crearFacturaCliente(List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVenta, Date fechaFacturacion, PuntoDeVenta puntoDeVenta, Sucursal sucursal, Organizacion organizacion, Documento documento)
/* 108:    */     throws ExcepcionAS2
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:116 */       FacturaCliente facturaCliente = new FacturaCliente();
/* 113:117 */       HashMap<String, FacturaCliente> hmFacturaCliente = new HashMap();
/* 114:118 */       List<FacturaCliente> listaFacturaClientes = new ArrayList();
/* 115:119 */       int nuevoNumero = 0;
/* 116:120 */       Map<String, String> mapaDescripcion = new HashMap();
/* 117:121 */       List<Object[]> valoresADevengar = new ArrayList();
/* 118:122 */       String codigoFormaPagoSRI = null;
/* 119:123 */       DetallesFacturaContratoVenta dfcvAux = null;
/* 120:124 */       for (DetallesFacturaContratoVenta dfcv : listaDetallesFacturaContratoVenta)
/* 121:    */       {
/* 122:126 */         EntidadUsuario agenteComercial = dfcv.getContratoVenta().getAgenteComercial();
/* 123:127 */         DireccionEmpresa direccionEmpresa = dfcv.getContratoVenta().getDireccionEmpresa();
/* 124:128 */         CondicionPago condicionPago = dfcv.getContratoVenta().getCondicionPago();
/* 125:129 */         Subempresa subempresa = dfcv.getContratoVenta().getSubempresa();
/* 126:130 */         Zona zona = dfcv.getContratoVenta().getZona();
/* 127:131 */         Canal canal = dfcv.getContratoVenta().getCanal();
/* 128:132 */         String descripcion = dfcv.getDescripcion();
/* 129:133 */         String emails = dfcv.getContratoVenta().getEmail();
/* 130:134 */         mapaDescripcion.put(dfcv.getContratoVenta().getNumero() + "~" + descripcion, descripcion);
/* 131:135 */         String codigoFormaPagoSRIAux = dfcv.getCodigoFormaPagoSRI();
/* 132:136 */         dfcv = this.detallesFacturaContratoVentaDao.cargarDetalle(dfcv);
/* 133:137 */         dfcvAux = (DetallesFacturaContratoVenta)this.detallesFacturaContratoVentaDao.buscarPorId(Integer.valueOf(dfcv.getId()));
/* 134:139 */         if ((dfcvAux != null) && (dfcvAux.isIndicadorFacturado()))
/* 135:    */         {
/* 136:140 */           System.out.println("DETALLE FACTURADO");
/* 137:141 */           throw new ExcepcionAS2("msg_info_contrato_existe", " ");
/* 138:    */         }
/* 139:144 */         dfcv.setDescripcion(descripcion);
/* 140:145 */         BigDecimal totalDFCV = dfcv.getTotalDetalleFacturaContratoVenta();
/* 141:    */         
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:150 */         StringBuilder clave = new StringBuilder();
/* 146:151 */         clave.append(dfcv.getContratoVenta().getEmpresa().getCodigo());
/* 147:152 */         if (dfcv.getContratoVenta().getSubempresa() != null)
/* 148:    */         {
/* 149:153 */           clave.append("-");
/* 150:154 */           clave.append(dfcv.getContratoVenta().getSubempresa().getCodigo());
/* 151:    */         }
/* 152:160 */         facturaCliente = (FacturaCliente)hmFacturaCliente.get(clave.toString());
/* 153:    */         String[] numero;
/* 154:161 */         if (facturaCliente == null)
/* 155:    */         {
/* 156:163 */           facturaCliente = new FacturaCliente();
/* 157:164 */           facturaCliente.setSucursal(sucursal);
/* 158:165 */           facturaCliente.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 159:166 */           facturaCliente.setEmpresa(dfcv.getContratoVenta().getEmpresa());
/* 160:    */           
/* 161:168 */           facturaCliente.setDireccionEmpresa(direccionEmpresa);
/* 162:169 */           facturaCliente.setEstado(Estado.PROCESADO);
/* 163:170 */           facturaCliente.setDireccionFactura(direccionEmpresa.getDireccionCompleta());
/* 164:171 */           facturaCliente.setNumeroCuotas(1);
/* 165:172 */           facturaCliente.setListaDetalleFacturaCliente(new ArrayList());
/* 166:    */           
/* 167:174 */           facturaCliente.setFecha(fechaFacturacion);
/* 168:175 */           facturaCliente.setAgenteComercial(agenteComercial);
/* 169:176 */           facturaCliente.setEmail(emails);
/* 170:177 */           facturaCliente.setDocumento(documento);
/* 171:178 */           facturaCliente.setCondicionPago(condicionPago);
/* 172:179 */           facturaCliente.setSubempresa(subempresa);
/* 173:180 */           facturaCliente.setZona(zona);
/* 174:181 */           facturaCliente.setCanal(canal);
/* 175:    */           
/* 176:183 */           facturaCliente.setFacturaClienteFloricola(true);
/* 177:    */           
/* 178:185 */           this.servicioFacturaCliente.cargarSecuencia(facturaCliente, puntoDeVenta);
/* 179:186 */           numero = facturaCliente.getNumero().split("-");
/* 180:187 */           String codigoSucursal = numero[0];
/* 181:188 */           String codigoPuntoVenta = numero[1];
/* 182:189 */           String numeroAux = numero[2];
/* 183:    */           
/* 184:191 */           facturaCliente.setNumero(codigoSucursal + "-" + codigoPuntoVenta + "-" + 
/* 185:192 */             FuncionesUtiles.completarALaIzquierda('0', 9, String.valueOf(Integer.valueOf(numeroAux).intValue() + nuevoNumero)));
/* 186:193 */           nuevoNumero++;
/* 187:194 */           hmFacturaCliente.put(clave.toString(), facturaCliente);
/* 188:    */           
/* 189:196 */           codigoFormaPagoSRI = codigoFormaPagoSRIAux;
/* 190:    */         }
/* 191:198 */         descripcion = "";
/* 192:200 */         for (Map.Entry<String, String> entry : mapaDescripcion.entrySet()) {
/* 193:201 */           if (((String)entry.getKey()).equals(dfcv.getContratoVenta().getNumero() + "~" + ((String)entry.getValue()).toString().toString()))
/* 194:    */           {
/* 195:202 */             descripcion = descripcion + ((String)entry.getValue()).toString() + "\n";
/* 196:203 */             System.out.println(descripcion);
/* 197:    */           }
/* 198:    */         }
/* 199:207 */         facturaCliente.setDescripcion(descripcion);
/* 200:212 */         for (ContratoVentaFacturaContratoVenta cvfcv : dfcv.getListaContratoVentaFacturaContratoVenta()) {
/* 201:213 */           if (cvfcv.getValor().compareTo(BigDecimal.ZERO) != 0)
/* 202:    */           {
/* 203:214 */             DetalleFacturaCliente detalleFactura = new DetalleFacturaCliente();
/* 204:215 */             detalleFactura.setContratoVentaFacturaContratoVenta(cvfcv);
/* 205:216 */             detalleFactura.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 206:217 */             detalleFactura.setIdSucursal(sucursal.getId());
/* 207:218 */             detalleFactura.setCantidad(cvfcv.getDetalleContratoVenta().getCantidad());
/* 208:219 */             detalleFactura.setFacturaCliente(facturaCliente);
/* 209:220 */             detalleFactura.setPorcentajeDescuento(new BigDecimal(0));
/* 210:221 */             detalleFactura.setDescuento(new BigDecimal(0));
/* 211:222 */             detalleFactura.setPrecio(cvfcv.getValor().divide(detalleFactura.getCantidad(), 6, RoundingMode.HALF_UP));
/* 212:223 */             detalleFactura.setProducto(cvfcv.getDetalleContratoVenta().getProducto());
/* 213:224 */             detalleFactura.setUnidadVenta(cvfcv.getDetalleContratoVenta().getProducto().getUnidadVenta());
/* 214:    */             
/* 215:    */ 
/* 216:227 */             Calendar fechaDesdeC = Calendar.getInstance();
/* 217:228 */             fechaDesdeC.clear();
/* 218:229 */             fechaDesdeC.setTime(cvfcv.getDetalleContratoVenta().getFechaDesde());
/* 219:230 */             int diaDesde = fechaDesdeC.get(5);
/* 220:231 */             int mesDesde = fechaDesdeC.get(2) + 1;
/* 221:232 */             int anioDesde = fechaDesdeC.get(1);
/* 222:    */             
/* 223:    */ 
/* 224:235 */             String desde = FuncionesUtiles.completarALaIzquierda('0', 2, new StringBuilder().append(diaDesde).append("").toString()) + "/" + FuncionesUtiles.completarALaIzquierda('0', 2, new StringBuilder().append(mesDesde).append("").toString()) + "/" + FuncionesUtiles.completarALaIzquierda('0', 4, new StringBuilder().append(anioDesde).append("").toString());
/* 225:236 */             Calendar fechaHastaC = Calendar.getInstance();
/* 226:237 */             fechaHastaC.clear();
/* 227:238 */             fechaHastaC.setTime(cvfcv.getDetalleContratoVenta().getFechaHasta());
/* 228:239 */             int diaHasta = fechaHastaC.get(5);
/* 229:240 */             int mesHasta = fechaHastaC.get(2) + 1;
/* 230:241 */             int anioHasta = fechaHastaC.get(1);
/* 231:    */             
/* 232:    */ 
/* 233:244 */             String hasta = FuncionesUtiles.completarALaIzquierda('0', 2, new StringBuilder().append(diaHasta).append("").toString()) + "/" + FuncionesUtiles.completarALaIzquierda('0', 2, new StringBuilder().append(mesHasta).append("").toString()) + "/" + FuncionesUtiles.completarALaIzquierda('0', 4, new StringBuilder().append(anioHasta).append("").toString());
/* 234:    */             
/* 235:    */ 
/* 236:    */ 
/* 237:248 */             String periodo = desde + " al " + hasta;
/* 238:249 */             String pago = "";
/* 239:250 */             if ((dfcv.getContratoVenta().getCuotasAnteriores() + dfcv.getContratoVenta().getNumeroCuotas() > 1) && 
/* 240:251 */               (dfcv.getContratoVenta().getIndicadorManejaCuotas().booleanValue()))
/* 241:    */             {
/* 242:252 */               int cuota = dfcv.getContratoVenta().getCuotasAnteriores() + dfcv.getNumeroCuota().intValue();
/* 243:253 */               int totalCuotas = dfcv.getContratoVenta().getCuotasAnteriores() + dfcv.getContratoVenta().getNumeroCuotas();
/* 244:254 */               pago = ". PAGO " + cuota + " de " + totalCuotas + ".";
/* 245:    */             }
/* 246:256 */             if (dfcv.getContratoVenta().isIndicadorMostrarPeriodo()) {
/* 247:257 */               detalleFactura.setDescripcion("PERIODO: " + periodo + pago);
/* 248:    */             }
/* 249:260 */             facturaCliente.getListaDetalleFacturaCliente().add(detalleFactura);
/* 250:262 */             if ((facturaCliente.getEmpresa().getCliente().isExcentoImpuestos()) || 
/* 251:263 */               (facturaCliente.getDocumento().isIndicadorDocumentoExterior())) {
/* 252:264 */               detalleFactura.setIndicadorImpuesto(false);
/* 253:    */             } else {
/* 254:266 */               detalleFactura.setIndicadorImpuesto(detalleFactura.getProducto().isIndicadorImpuestos());
/* 255:    */             }
/* 256:269 */             if (detalleFactura.isIndicadorImpuesto()) {
/* 257:270 */               this.servicioFacturaCliente.obtenerImpuestosProductos(detalleFactura.getProducto(), detalleFactura);
/* 258:    */             }
/* 259:    */           }
/* 260:    */         }
/* 261:275 */         this.servicioFacturaCliente.totalizar(facturaCliente);
/* 262:276 */         this.servicioFacturaCliente.generarCuentaPorCobrar(facturaCliente);
/* 263:277 */         this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 264:    */         
/* 265:    */ 
/* 266:280 */         facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(codigoFormaPagoSRI);
/* 267:    */         
/* 268:282 */         this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, puntoDeVenta);
/* 269:    */         
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:288 */         dfcv.setIndicadorFacturado(true);
/* 275:289 */         this.servicioDetalleFacturaContratoVenta.guardar(dfcv);
/* 276:    */         
/* 277:291 */         dfcv.getContratoVenta().setEstado(Estado.EMITIDO_CONTRATO);
/* 278:292 */         this.contratoVentaDao.guardar(dfcv.getContratoVenta());
/* 279:    */         
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:298 */         Object[] devengar = new Object[3];
/* 285:299 */         devengar[0] = facturaCliente;
/* 286:300 */         devengar[1] = dfcv;
/* 287:301 */         devengar[2] = totalDFCV;
/* 288:302 */         valoresADevengar.add(devengar);
/* 289:    */       }
/* 290:310 */       for (FacturaCliente fc : hmFacturaCliente.values())
/* 291:    */       {
/* 292:312 */         if ((fc.getFacturaClienteSRI().getCodigoFormaPagoSRI() == null) || (fc.getFacturaClienteSRI().getCodigoFormaPagoSRI() == "")) {
/* 293:313 */           throw new ExcepcionAS2Financiero("msg_error_forma_pago_sri_vacia", " " + fc.getEmpresa().getNombreFiscal());
/* 294:    */         }
/* 295:316 */         listaFacturaClientes.add(fc);
/* 296:    */       }
/* 297:319 */       this.servicioFacturaCliente.guardarFacturaClienteRevisadas(listaFacturaClientes);
/* 298:325 */       for (Object[] devengar : valoresADevengar) {
/* 299:326 */         generarValoresDevengadosFactura((FacturaCliente)devengar[0], (DetallesFacturaContratoVenta)devengar[1], (BigDecimal)devengar[2]);
/* 300:    */       }
/* 301:    */     }
/* 302:    */     catch (ExcepcionAS2Ventas e)
/* 303:    */     {
/* 304:330 */       e.printStackTrace();
/* 305:331 */       this.context.setRollbackOnly();
/* 306:332 */       throw e;
/* 307:    */     }
/* 308:    */     catch (ExcepcionAS2Inventario e)
/* 309:    */     {
/* 310:334 */       e.printStackTrace();
/* 311:335 */       this.context.setRollbackOnly();
/* 312:336 */       throw e;
/* 313:    */     }
/* 314:    */     catch (ExcepcionAS2Financiero e)
/* 315:    */     {
/* 316:338 */       e.printStackTrace();
/* 317:339 */       this.context.setRollbackOnly();
/* 318:340 */       throw e;
/* 319:    */     }
/* 320:    */     catch (ExcepcionAS2 e)
/* 321:    */     {
/* 322:342 */       e.printStackTrace();
/* 323:343 */       this.context.setRollbackOnly();
/* 324:344 */       throw e;
/* 325:    */     }
/* 326:    */     catch (Exception e)
/* 327:    */     {
/* 328:346 */       e.printStackTrace();
/* 329:347 */       this.context.setRollbackOnly();
/* 330:348 */       throw new ExcepcionAS2(e);
/* 331:    */     }
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void generarValoresDevengadosFactura(FacturaCliente facturaCliente, DetallesFacturaContratoVenta dfcv, BigDecimal totalDFCV)
/* 335:    */   {
/* 336:354 */     Date fechaFacturacion = facturaCliente.getFecha();
/* 337:355 */     BigDecimal acumulado = BigDecimal.ZERO;
/* 338:356 */     for (DetalleValoresContratoVenta dvcvAux : this.detalleValoresContratoVentaDao.listaDetalleValoresContratoVenta(dfcv.getContratoVenta(), facturaCliente
/* 339:357 */       .getDocumento().getDocumentoBase(), null)) {
/* 340:358 */       if (dvcvAux.getFecha().before(fechaFacturacion))
/* 341:    */       {
/* 342:359 */         acumulado = acumulado.add(dvcvAux.getValor());
/* 343:    */         
/* 344:361 */         dvcvAux.setIndicadorFacturado(Boolean.valueOf(acumulado.compareTo(totalDFCV) <= 0));
/* 345:364 */         if (acumulado.compareTo(totalDFCV) >= 0) {
/* 346:365 */           dvcvAux.setValor(acumulado.subtract(totalDFCV));
/* 347:    */         } else {
/* 348:367 */           dvcvAux.setValor(BigDecimal.ZERO);
/* 349:    */         }
/* 350:369 */         this.detalleValoresContratoVentaDao.guardar(dvcvAux);
/* 351:372 */         if (acumulado.compareTo(totalDFCV) >= 0)
/* 352:    */         {
/* 353:373 */           DetalleValoresContratoVenta detalleValoresContratoVenta = new DetalleValoresContratoVenta();
/* 354:374 */           detalleValoresContratoVenta.setIdOrganizacion(dfcv.getIdOrganizacion());
/* 355:375 */           detalleValoresContratoVenta.setIdSucursal(dfcv.getIdSucursal());
/* 356:376 */           detalleValoresContratoVenta.setContratoVenta(dfcv.getContratoVenta());
/* 357:377 */           detalleValoresContratoVenta.setFecha(FuncionesUtiles.getFechaFinMes(fechaFacturacion));
/* 358:378 */           detalleValoresContratoVenta.setValor(BigDecimal.ZERO);
/* 359:379 */           detalleValoresContratoVenta.setValorDevengar(totalDFCV);
/* 360:380 */           detalleValoresContratoVenta.setIndicadorFacturado(Boolean.valueOf(true));
/* 361:381 */           detalleValoresContratoVenta.setIndicadorDevengado(true);
/* 362:    */           
/* 363:383 */           detalleValoresContratoVenta.setFacturaCliente(facturaCliente);
/* 364:384 */           this.detalleValoresContratoVentaDao.guardar(detalleValoresContratoVenta);
/* 365:385 */           acumulado = BigDecimal.ZERO;
/* 366:386 */           System.out.println("11");
/* 367:387 */           break;
/* 368:    */         }
/* 369:    */       }
/* 370:392 */       else if ((dvcvAux.getFecha().equals(FuncionesUtiles.getFechaFinMes(fechaFacturacion))) && (!dvcvAux.isIndicadorDevengado()) && 
/* 371:393 */         (acumulado.add(dvcvAux.getValor()).compareTo(totalDFCV) <= 0))
/* 372:    */       {
/* 373:394 */         acumulado = acumulado.add(dvcvAux.getValor());
/* 374:395 */         dvcvAux.setIndicadorFacturado(Boolean.valueOf(true));
/* 375:396 */         dvcvAux.setIndicadorDevengado(true);
/* 376:    */         
/* 377:398 */         dvcvAux.setFacturaCliente(facturaCliente);
/* 378:399 */         dvcvAux.setValor(BigDecimal.ZERO);
/* 379:    */         
/* 380:    */ 
/* 381:    */ 
/* 382:403 */         dvcvAux.setValorDevengar(dvcvAux.getValorDevengar().add(acumulado));
/* 383:    */         
/* 384:405 */         totalDFCV = totalDFCV.subtract(acumulado);
/* 385:406 */         acumulado = BigDecimal.ZERO;
/* 386:407 */         System.out.println("22");
/* 387:    */       }
/* 388:410 */       else if ((dvcvAux.getFecha().equals(FuncionesUtiles.getFechaFinMes(fechaFacturacion))) && 
/* 389:411 */         (acumulado.add(dvcvAux.getValor()).compareTo(totalDFCV) == 1))
/* 390:    */       {
/* 391:412 */         acumulado = acumulado.add(dvcvAux.getValor());
/* 392:413 */         dvcvAux.setIndicadorFacturado(Boolean.valueOf(false));
/* 393:414 */         dvcvAux.setIndicadorDevengado(true);
/* 394:    */         
/* 395:416 */         dvcvAux.setFacturaCliente(facturaCliente);
/* 396:417 */         dvcvAux.setValor(acumulado.subtract(totalDFCV));
/* 397:418 */         dvcvAux.setValorDevengar(totalDFCV);
/* 398:419 */         dvcvAux.setFecha(FuncionesUtiles.getFechaFinMes(fechaFacturacion));
/* 399:420 */         totalDFCV = BigDecimal.ZERO;
/* 400:421 */         acumulado = acumulado.subtract(totalDFCV);
/* 401:422 */         System.out.println("33");
/* 402:    */       }
/* 403:    */       else
/* 404:    */       {
/* 405:426 */         if ((dvcvAux.getValor().add(acumulado).compareTo(totalDFCV) <= 0) && (dvcvAux.getValorDevengar().compareTo(BigDecimal.ZERO) == 0))
/* 406:    */         {
/* 407:427 */           dvcvAux.setIndicadorFacturado(Boolean.valueOf(true));
/* 408:428 */           dvcvAux.setIndicadorDevengado(true);
/* 409:    */           
/* 410:430 */           dvcvAux.setFacturaCliente(facturaCliente);
/* 411:431 */           dvcvAux.setValorDevengar(dvcvAux.getValor().add(acumulado));
/* 412:432 */           totalDFCV = totalDFCV.subtract(dvcvAux.getValor());
/* 413:433 */           dvcvAux.setValor(BigDecimal.ZERO);
/* 414:434 */           System.out.println("44_1");
/* 415:    */         }
/* 416:437 */         else if ((dvcvAux.getValorDevengar().compareTo(BigDecimal.ZERO) == 0) && (totalDFCV.compareTo(BigDecimal.ZERO) != 0))
/* 417:    */         {
/* 418:438 */           dvcvAux.setIndicadorFacturado(Boolean.valueOf(false));
/* 419:439 */           dvcvAux.setIndicadorDevengado(true);
/* 420:    */           
/* 421:441 */           dvcvAux.setFacturaCliente(facturaCliente);
/* 422:442 */           dvcvAux.setValorDevengar(totalDFCV);
/* 423:443 */           dvcvAux.setValor(dvcvAux.getValor().subtract(totalDFCV));
/* 424:444 */           totalDFCV = BigDecimal.ZERO;
/* 425:445 */           System.out.println("55");
/* 426:    */         }
/* 427:448 */         else if ((dvcvAux.getValorDevengar().compareTo(BigDecimal.ZERO) != 0) && (totalDFCV.compareTo(BigDecimal.ZERO) != 0))
/* 428:    */         {
/* 429:449 */           BigDecimal viejoValor = dvcvAux.getValor();
/* 430:450 */           dvcvAux.setIndicadorFacturado(Boolean.valueOf(true));
/* 431:451 */           dvcvAux.setIndicadorDevengado(true);
/* 432:452 */           dvcvAux.setValor(BigDecimal.ZERO);
/* 433:    */           
/* 434:    */ 
/* 435:455 */           BigDecimal nuevoValor = BigDecimal.ZERO;
/* 436:456 */           BigDecimal nuevoValorDevengar = BigDecimal.ZERO;
/* 437:457 */           if (viejoValor.compareTo(totalDFCV) <= 0)
/* 438:    */           {
/* 439:458 */             totalDFCV = totalDFCV.subtract(viejoValor);
/* 440:459 */             nuevoValorDevengar = viejoValor;
/* 441:460 */             nuevoValor = BigDecimal.ZERO;
/* 442:    */           }
/* 443:    */           else
/* 444:    */           {
/* 445:462 */             nuevoValorDevengar = totalDFCV;
/* 446:463 */             totalDFCV = BigDecimal.ZERO;
/* 447:464 */             nuevoValor = viejoValor.subtract(nuevoValorDevengar);
/* 448:    */           }
/* 449:467 */           DetalleValoresContratoVenta detalleValoresContratoVenta = new DetalleValoresContratoVenta();
/* 450:468 */           detalleValoresContratoVenta.setIdOrganizacion(dfcv.getIdOrganizacion());
/* 451:469 */           detalleValoresContratoVenta.setIdSucursal(dfcv.getIdSucursal());
/* 452:470 */           detalleValoresContratoVenta.setContratoVenta(dfcv.getContratoVenta());
/* 453:471 */           detalleValoresContratoVenta.setFecha(dvcvAux.getFecha());
/* 454:472 */           detalleValoresContratoVenta.setValor(nuevoValor);
/* 455:473 */           detalleValoresContratoVenta.setValorDevengar(nuevoValorDevengar);
/* 456:474 */           detalleValoresContratoVenta.setIndicadorFacturado(Boolean.valueOf(nuevoValor.compareTo(BigDecimal.ZERO) == 0));
/* 457:475 */           detalleValoresContratoVenta.setIndicadorDevengado(true);
/* 458:    */           
/* 459:477 */           detalleValoresContratoVenta.setFacturaCliente(facturaCliente);
/* 460:478 */           this.detalleValoresContratoVentaDao.guardar(detalleValoresContratoVenta);
/* 461:479 */           acumulado = BigDecimal.ZERO;
/* 462:    */           
/* 463:481 */           System.out.println("66");
/* 464:    */         }
/* 465:484 */         this.detalleValoresContratoVentaDao.guardar(dvcvAux);
/* 466:485 */         acumulado = BigDecimal.ZERO;
/* 467:486 */         if (totalDFCV.compareTo(BigDecimal.ZERO) == 0) {
/* 468:    */           break;
/* 469:    */         }
/* 470:    */       }
/* 471:    */     }
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void generarValoresDevengadosNotaCredito(FacturaCliente notaCredito, DetallesFacturaContratoVenta dfcv, BigDecimal totalDFCV)
/* 475:    */   {
/* 476:495 */     System.out.println("totalDFCV:" + totalDFCV);
/* 477:496 */     Date fechaNotaCredito = FuncionesUtiles.getFechaFinMes(notaCredito.getFecha());
/* 478:497 */     BigDecimal acumulado = BigDecimal.ZERO;
/* 479:499 */     for (DetalleValoresContratoVenta dvcvAux : this.detalleValoresContratoVentaDao.listaDetalleValoresContratoVenta(dfcv.getContratoVenta(), notaCredito
/* 480:500 */       .getDocumento().getDocumentoBase(), notaCredito.getFacturaClientePadre()))
/* 481:    */     {
/* 482:501 */       BigDecimal valorFactura = dvcvAux.getValorDevengar().subtract(dvcvAux.getValorNotaCredito());
/* 483:503 */       if (dvcvAux.getFecha().before(fechaNotaCredito))
/* 484:    */       {
/* 485:504 */         acumulado = acumulado.add(valorFactura);
/* 486:505 */         dvcvAux.setValorNotaCredito(valorFactura);
/* 487:506 */         if (acumulado.compareTo(totalDFCV) >= 0) {
/* 488:507 */           dvcvAux.setValorNotaCredito(valorFactura.subtract(acumulado.subtract(totalDFCV)).setScale(2, RoundingMode.HALF_UP));
/* 489:    */         } else {
/* 490:509 */           dvcvAux.setValorNotaCredito(valorFactura.setScale(2, RoundingMode.HALF_UP));
/* 491:    */         }
/* 492:511 */         this.detalleValoresContratoVentaDao.guardar(dvcvAux);
/* 493:    */       }
/* 494:    */       else
/* 495:    */       {
/* 496:514 */         if (acumulado.compareTo(BigDecimal.ZERO) > 0)
/* 497:    */         {
/* 498:515 */           DetalleValoresContratoVenta detalleValoresContratoVenta = new DetalleValoresContratoVenta();
/* 499:516 */           detalleValoresContratoVenta.setIdOrganizacion(dfcv.getIdOrganizacion());
/* 500:517 */           detalleValoresContratoVenta.setIdSucursal(dfcv.getIdSucursal());
/* 501:518 */           detalleValoresContratoVenta.setContratoVenta(dfcv.getContratoVenta());
/* 502:519 */           detalleValoresContratoVenta.setFecha(fechaNotaCredito);
/* 503:520 */           detalleValoresContratoVenta.setValor(BigDecimal.ZERO);
/* 504:521 */           detalleValoresContratoVenta.setValorDevengar(acumulado.setScale(2, RoundingMode.HALF_UP).negate());
/* 505:    */           
/* 506:523 */           detalleValoresContratoVenta.setIndicadorFacturado(Boolean.valueOf(true));
/* 507:524 */           detalleValoresContratoVenta.setIndicadorDevengado(true);
/* 508:525 */           detalleValoresContratoVenta.setFacturaCliente(notaCredito.getFacturaClientePadre());
/* 509:526 */           detalleValoresContratoVenta.setNotaCredito(notaCredito);
/* 510:527 */           this.detalleValoresContratoVentaDao.guardar(detalleValoresContratoVenta);
/* 511:    */           
/* 512:529 */           totalDFCV = totalDFCV.subtract(acumulado);
/* 513:    */           
/* 514:531 */           acumulado = BigDecimal.ZERO;
/* 515:    */         }
/* 516:534 */         if (totalDFCV.compareTo(BigDecimal.ZERO) > 0)
/* 517:    */         {
/* 518:535 */           BigDecimal valorDevengar = BigDecimal.ZERO;
/* 519:537 */           if (totalDFCV.compareTo(valorFactura) >= 0) {
/* 520:538 */             valorDevengar = valorFactura.setScale(2, RoundingMode.HALF_UP);
/* 521:    */           } else {
/* 522:540 */             valorDevengar = totalDFCV.setScale(2, RoundingMode.HALF_UP);
/* 523:    */           }
/* 524:542 */           if (valorDevengar.compareTo(BigDecimal.ZERO) > 0)
/* 525:    */           {
/* 526:543 */             dvcvAux.setValorNotaCredito(valorDevengar.add(dvcvAux.getValorNotaCredito()));
/* 527:544 */             this.detalleValoresContratoVentaDao.guardar(dvcvAux);
/* 528:    */             
/* 529:546 */             DetalleValoresContratoVenta detalleValoresContratoVenta = new DetalleValoresContratoVenta();
/* 530:547 */             detalleValoresContratoVenta.setIdOrganizacion(dfcv.getIdOrganizacion());
/* 531:548 */             detalleValoresContratoVenta.setIdSucursal(dfcv.getIdSucursal());
/* 532:549 */             detalleValoresContratoVenta.setContratoVenta(dfcv.getContratoVenta());
/* 533:550 */             detalleValoresContratoVenta.setFecha(dvcvAux.getFecha());
/* 534:551 */             detalleValoresContratoVenta.setValor(BigDecimal.ZERO);
/* 535:552 */             detalleValoresContratoVenta.setValorDevengar(valorDevengar.negate());
/* 536:553 */             detalleValoresContratoVenta.setIndicadorFacturado(Boolean.valueOf(true));
/* 537:554 */             detalleValoresContratoVenta.setIndicadorDevengado(true);
/* 538:555 */             detalleValoresContratoVenta.setFacturaCliente(notaCredito.getFacturaClientePadre());
/* 539:556 */             detalleValoresContratoVenta.setNotaCredito(notaCredito);
/* 540:557 */             this.detalleValoresContratoVentaDao.guardar(detalleValoresContratoVenta);
/* 541:    */             
/* 542:559 */             totalDFCV = totalDFCV.subtract(valorDevengar);
/* 543:    */           }
/* 544:    */         }
/* 545:    */       }
/* 546:564 */       if (totalDFCV.compareTo(BigDecimal.ZERO) == 0) {
/* 547:    */         break;
/* 548:    */       }
/* 549:    */     }
/* 550:569 */     if (acumulado.compareTo(BigDecimal.ZERO) > 0)
/* 551:    */     {
/* 552:570 */       DetalleValoresContratoVenta detalleValoresContratoVenta = new DetalleValoresContratoVenta();
/* 553:571 */       detalleValoresContratoVenta.setIdOrganizacion(dfcv.getIdOrganizacion());
/* 554:572 */       detalleValoresContratoVenta.setIdSucursal(dfcv.getIdSucursal());
/* 555:573 */       detalleValoresContratoVenta.setContratoVenta(dfcv.getContratoVenta());
/* 556:574 */       detalleValoresContratoVenta.setFecha(fechaNotaCredito);
/* 557:575 */       detalleValoresContratoVenta.setValor(BigDecimal.ZERO);
/* 558:576 */       detalleValoresContratoVenta.setValorDevengar(acumulado.setScale(2, RoundingMode.HALF_UP).negate());
/* 559:    */       
/* 560:578 */       detalleValoresContratoVenta.setIndicadorFacturado(Boolean.valueOf(true));
/* 561:579 */       detalleValoresContratoVenta.setIndicadorDevengado(true);
/* 562:580 */       detalleValoresContratoVenta.setFacturaCliente(notaCredito.getFacturaClientePadre());
/* 563:581 */       detalleValoresContratoVenta.setNotaCredito(notaCredito);
/* 564:582 */       this.detalleValoresContratoVentaDao.guardar(detalleValoresContratoVenta);
/* 565:583 */       totalDFCV = totalDFCV.subtract(acumulado);
/* 566:584 */       acumulado = BigDecimal.ZERO;
/* 567:    */     }
/* 568:    */   }
/* 569:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioDetalleFacturaContratoVentaImpl
 * JD-Core Version:    0.7.0.1
 */