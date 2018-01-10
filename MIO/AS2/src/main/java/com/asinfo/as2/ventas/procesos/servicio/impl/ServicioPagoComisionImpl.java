/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetallePagoComisionDao;
/*   4:    */ import com.asinfo.as2.dao.PagoComisionDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Cobro;
/*   9:    */ import com.asinfo.as2.entities.DetalleCobro;
/*  10:    */ import com.asinfo.as2.entities.DetallePagoComision;
/*  11:    */ import com.asinfo.as2.entities.DetalleVersionPlanComision;
/*  12:    */ import com.asinfo.as2.entities.DetalleVersionPlanComisionRangoDias;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  16:    */ import com.asinfo.as2.entities.PagoComision;
/*  17:    */ import com.asinfo.as2.entities.PlanComision;
/*  18:    */ import com.asinfo.as2.entities.Producto;
/*  19:    */ import com.asinfo.as2.entities.RangoDiasComision;
/*  20:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  21:    */ import com.asinfo.as2.entities.VersionPlanComision;
/*  22:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  23:    */ import com.asinfo.as2.enumeraciones.FormaPagoComisionEnum;
/*  24:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  28:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  29:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  30:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioPlanComision;
/*  31:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPagoComision;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.math.RoundingMode;
/*  34:    */ import java.util.ArrayList;
/*  35:    */ import java.util.Date;
/*  36:    */ import java.util.HashMap;
/*  37:    */ import java.util.Iterator;
/*  38:    */ import java.util.List;
/*  39:    */ import java.util.Map;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.ejb.Stateless;
/*  42:    */ 
/*  43:    */ @Stateless
/*  44:    */ public class ServicioPagoComisionImpl
/*  45:    */   implements ServicioPagoComision
/*  46:    */ {
/*  47:    */   @EJB
/*  48:    */   private PagoComisionDao pagoComisionDao;
/*  49:    */   @EJB
/*  50:    */   private DetallePagoComisionDao detallePagoComisionDao;
/*  51:    */   @EJB
/*  52:    */   private ServicioPeriodo servicioPeriodo;
/*  53:    */   @EJB
/*  54:    */   private ServicioSecuencia servicioSecuencia;
/*  55:    */   @EJB
/*  56:    */   private ServicioUsuario servicioUsuario;
/*  57:    */   @EJB
/*  58:    */   private ServicioPlanComision servicioPlanComision;
/*  59:    */   
/*  60:    */   public void guardar(PagoComision pagoComision)
/*  61:    */     throws ExcepcionAS2, AS2Exception
/*  62:    */   {
/*  63: 75 */     actualizarFechas(pagoComision);
/*  64: 76 */     validar(pagoComision);
/*  65: 77 */     cargarSecuencia(pagoComision);
/*  66: 78 */     this.pagoComisionDao.guardar(pagoComision);
/*  67: 79 */     for (DetallePagoComision detalle : pagoComision.getListaDetallePagoComision()) {
/*  68: 80 */       this.detallePagoComisionDao.guardar(detalle);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void validar(PagoComision pagoComision)
/*  73:    */     throws AS2Exception
/*  74:    */   {
/*  75: 85 */     this.pagoComisionDao.validarFechasPagoComision(pagoComision);
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void actualizarFechas(PagoComision pagoComision)
/*  79:    */   {
/*  80: 89 */     Date fechaDesde = FuncionesUtiles.getFecha(1, pagoComision.getMesInicial().getNumero(), pagoComision.getAnioInicial());
/*  81: 90 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(pagoComision.getAnioFinal(), pagoComision.getMesFinal().getNumero());
/*  82: 91 */     pagoComision.setFechaDesde(fechaDesde);
/*  83: 92 */     pagoComision.setFechaHasta(fechaHasta);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void eliminar(PagoComision entidad)
/*  87:    */   {
/*  88: 97 */     entidad = cargarDetalle(entidad.getId());
/*  89: 98 */     for (DetallePagoComision detalle : entidad.getListaDetallePagoComision()) {
/*  90: 99 */       this.detallePagoComisionDao.eliminar(detalle);
/*  91:    */     }
/*  92:101 */     this.pagoComisionDao.eliminar(entidad);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public PagoComision buscarPorId(int idPagoComision)
/*  96:    */   {
/*  97:106 */     return (PagoComision)this.pagoComisionDao.buscarPorId(Integer.valueOf(idPagoComision));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<PagoComision> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 101:    */   {
/* 102:111 */     return this.pagoComisionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<PagoComision> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 106:    */   {
/* 107:116 */     return this.pagoComisionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int contarPorCriterio(Map<String, String> filters)
/* 111:    */   {
/* 112:121 */     return this.pagoComisionDao.contarPorCriterio(filters);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public PagoComision cargarDetalle(int idPagoComision)
/* 116:    */   {
/* 117:126 */     return this.pagoComisionDao.cargarDetalle(idPagoComision);
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void cargarSecuencia(PagoComision pagoComision)
/* 121:    */     throws ExcepcionAS2
/* 122:    */   {
/* 123:131 */     if (pagoComision.getNumero().equals(""))
/* 124:    */     {
/* 125:132 */       String numero = "";
/* 126:133 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(pagoComision.getDocumento().getId(), pagoComision.getFecha());
/* 127:134 */       pagoComision.setNumero(numero);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<DetallePagoComision> generarPagosComision(PagoComision pagoComision)
/* 132:    */   {
/* 133:140 */     actualizarFechas(pagoComision);
/* 134:141 */     Map<String, DetallePagoComision> mapaComisiones = new HashMap();
/* 135:142 */     for (DetallePagoComision detalle : pagoComision.getListaDetallePagoComision())
/* 136:    */     {
/* 137:143 */       detalle.setEliminado(true);
/* 138:144 */       detalle.setBaseComision(BigDecimal.ZERO);
/* 139:145 */       detalle.setPorcentajeComision(BigDecimal.ZERO);
/* 140:146 */       detalle.setValorComision(BigDecimal.ZERO);
/* 141:    */       
/* 142:148 */       String keyProducto = "~";
/* 143:149 */       if (detalle.getCategoriaProducto() != null) {
/* 144:150 */         keyProducto = keyProducto + detalle.getCategoriaProducto().getId() + "~";
/* 145:    */       }
/* 146:152 */       if (detalle.getSubcategoriaProducto() != null) {
/* 147:153 */         keyProducto = keyProducto + detalle.getSubcategoriaProducto().getId() + "~";
/* 148:    */       }
/* 149:155 */       if (detalle.getProducto() != null) {
/* 150:156 */         keyProducto = keyProducto + detalle.getProducto().getId() + "~";
/* 151:    */       }
/* 152:161 */       String key = detalle.getAgenteComercial().getId() + "~" + detalle.getFacturaCliente().getId() + "~" + detalle.getDetalleCobro().getCobro().getId() + "~" + keyProducto + detalle.getRangoDiasComision().getId() + "~" + detalle.getFormaPagoComisionEnum().ordinal();
/* 153:    */       
/* 154:163 */       mapaComisiones.put(key, detalle);
/* 155:    */     }
/* 156:166 */     Date fechaInicialPago = pagoComision.getFechaDesde();
/* 157:167 */     Date fechaFinalPago = pagoComision.getFechaHasta();
/* 158:168 */     Map<Integer, PlanComision> mapaPlanComision = new HashMap();
/* 159:    */     
/* 160:    */ 
/* 161:171 */     List<EntidadUsuario> listaAgenteComercial = this.servicioUsuario.obtenerListaAgenteComercial(pagoComision.getIdOrganizacion(), Boolean.valueOf(true), Boolean.valueOf(true));
/* 162:172 */     for (Iterator localIterator2 = listaAgenteComercial.iterator(); localIterator2.hasNext();)
/* 163:    */     {
/* 164:172 */       agenteComercial = (EntidadUsuario)localIterator2.next();
/* 165:173 */       PlanComision planComision = (PlanComision)mapaPlanComision.get(Integer.valueOf(agenteComercial.getPlanComision().getId()));
/* 166:175 */       if (planComision == null)
/* 167:    */       {
/* 168:176 */         planComision = this.servicioPlanComision.cargarDetalle(agenteComercial.getPlanComision().getId());
/* 169:177 */         mapaPlanComision.put(Integer.valueOf(planComision.getId()), planComision);
/* 170:    */       }
/* 171:180 */       for (localIterator3 = planComision.getListaVersionPlanComision().iterator(); localIterator3.hasNext();)
/* 172:    */       {
/* 173:180 */         version = (VersionPlanComision)localIterator3.next();
/* 174:181 */         if (version.isActivo())
/* 175:    */         {
/* 176:182 */           fechaInicialVersion = FuncionesUtiles.getFecha(1, version.getMesInicial().getNumero(), version.getAnioInicial());
/* 177:183 */           fechaFinalVersion = FuncionesUtiles.getFechaFinMes(version.getAnioFinal(), version.getMesFinal().getNumero());
/* 178:186 */           for (localIterator4 = version.getListaDetalleVersionPlanComision().iterator(); localIterator4.hasNext();)
/* 179:    */           {
/* 180:186 */             detalle = (DetalleVersionPlanComision)localIterator4.next();
/* 181:187 */             Map<Integer, Object[]> mapaCobros = new HashMap();
/* 182:188 */             List<Object[]> listaFacturas = this.pagoComisionDao.getFacturasAPagarComision(agenteComercial, detalle, version, fechaInicialPago, fechaFinalPago, fechaInicialVersion, fechaFinalVersion);
/* 183:190 */             for (Object[] row : listaFacturas)
/* 184:    */             {
/* 185:191 */               Integer idDetalleCobro = (Integer)row[8];
/* 186:    */               
/* 187:193 */               mapaCobros.put(idDetalleCobro, row);
/* 188:    */             }
/* 189:196 */             for (Object[] row : mapaCobros.values())
/* 190:    */             {
/* 191:197 */               Integer idFacturaCliente = (Integer)row[0];
/* 192:198 */               String numeroFacturaCliente = (String)row[1];
/* 193:199 */               Date fechaFactura = (Date)row[2];
/* 194:200 */               Date fechaCobro = (Date)row[3];
/* 195:201 */               BigDecimal precioTotalFacturaConImpuestos = (BigDecimal)row[4];
/* 196:202 */               BigDecimal precioTotalFacturaSinImpuestos = (BigDecimal)row[5];
/* 197:203 */               BigDecimal precioProductosSinImpuestos = (BigDecimal)row[6];
/* 198:204 */               BigDecimal cantidadProductos = (BigDecimal)row[7];
/* 199:205 */               Integer idDetalleCobro = (Integer)row[8];
/* 200:206 */               BigDecimal valorCobro = (BigDecimal)row[9];
/* 201:207 */               String numeroCobro = (String)row[10];
/* 202:208 */               Integer idCobro = (Integer)row[12];
/* 203:    */               
/* 204:210 */               int diferenciaDias = FuncionesUtiles.diferenciasDeFechas(fechaFactura, fechaCobro);
/* 205:211 */               if (diferenciaDias < 0) {
/* 206:212 */                 diferenciaDias = 0;
/* 207:    */               } else {
/* 208:214 */                 diferenciaDias--;
/* 209:    */               }
/* 210:216 */               DetalleVersionPlanComisionRangoDias detalleVersionPlanComisionRangoDias = null;
/* 211:217 */               for (DetalleVersionPlanComisionRangoDias detalleRangoDias : detalle.getListaDetalleVersionPlanComisionRangoDias()) {
/* 212:218 */                 if ((diferenciaDias <= detalleRangoDias.getRangoDiasCobro().getDiaFinal()) && 
/* 213:219 */                   (diferenciaDias >= detalleRangoDias.getRangoDiasCobro().getDiaInicial()))
/* 214:    */                 {
/* 215:220 */                   detalleVersionPlanComisionRangoDias = detalleRangoDias;
/* 216:221 */                   break;
/* 217:    */                 }
/* 218:    */               }
/* 219:225 */               if (detalleVersionPlanComisionRangoDias != null)
/* 220:    */               {
/* 221:227 */                 String keyProducto = "~";
/* 222:228 */                 if (detalle.getCategoriaProducto() != null) {
/* 223:229 */                   keyProducto = keyProducto + detalle.getCategoriaProducto().getId() + "~";
/* 224:    */                 }
/* 225:231 */                 if (detalle.getSubcategoriaProducto() != null) {
/* 226:232 */                   keyProducto = keyProducto + detalle.getSubcategoriaProducto().getId() + "~";
/* 227:    */                 }
/* 228:234 */                 if (detalle.getProducto() != null) {
/* 229:235 */                   keyProducto = keyProducto + detalle.getProducto().getId() + "~";
/* 230:    */                 }
/* 231:240 */                 String key = agenteComercial.getId() + "~" + idFacturaCliente + "~" + idCobro + "~" + keyProducto + detalleVersionPlanComisionRangoDias.getRangoDiasCobro().getId() + "~" + detalle.getFormaPagoComisionEnum().ordinal();
/* 232:    */                 
/* 233:242 */                 DetallePagoComision detallePagoComision = (DetallePagoComision)mapaComisiones.get(key);
/* 234:243 */                 if (detallePagoComision == null)
/* 235:    */                 {
/* 236:244 */                   FacturaCliente facturaCliente = new FacturaCliente();
/* 237:245 */                   facturaCliente.setIdFacturaCliente(idFacturaCliente.intValue());
/* 238:246 */                   facturaCliente.setNumero(numeroFacturaCliente);
/* 239:    */                   
/* 240:248 */                   DetalleCobro detalleCobro = new DetalleCobro();
/* 241:249 */                   detalleCobro.setIdDetalleCobro(idDetalleCobro.intValue());
/* 242:250 */                   Cobro cobro = new Cobro();
/* 243:251 */                   cobro.setIdCobro(idCobro.intValue());
/* 244:252 */                   cobro.setNumero(numeroCobro);
/* 245:253 */                   detalleCobro.setCobro(cobro);
/* 246:    */                   
/* 247:255 */                   detallePagoComision = new DetallePagoComision();
/* 248:256 */                   detallePagoComision.setIdOrganizacion(pagoComision.getIdOrganizacion());
/* 249:257 */                   detallePagoComision.setIdSucursal(pagoComision.getIdSucursal());
/* 250:258 */                   detallePagoComision.setPagoComision(pagoComision);
/* 251:259 */                   detallePagoComision.setAgenteComercial(agenteComercial);
/* 252:260 */                   detallePagoComision.setFacturaCliente(facturaCliente);
/* 253:261 */                   detallePagoComision.setDetalleCobro(detalleCobro);
/* 254:262 */                   detallePagoComision.setCategoriaProducto(detalle.getCategoriaProducto());
/* 255:263 */                   detallePagoComision.setSubcategoriaProducto(detalle.getSubcategoriaProducto());
/* 256:264 */                   detallePagoComision.setProducto(detalle.getProducto());
/* 257:265 */                   detallePagoComision.setRangoDiasComision(detalleVersionPlanComisionRangoDias.getRangoDiasCobro());
/* 258:266 */                   detallePagoComision.setFormaPagoComisionEnum(detalle.getFormaPagoComisionEnum());
/* 259:267 */                   detallePagoComision.setPorcentajeComision(detalleVersionPlanComisionRangoDias.getValor());
/* 260:268 */                   mapaComisiones.put(key, detallePagoComision);
/* 261:269 */                   pagoComision.getListaDetallePagoComision().add(detallePagoComision);
/* 262:    */                 }
/* 263:271 */                 detallePagoComision.setPorcentajeComision(detalleVersionPlanComisionRangoDias.getValor());
/* 264:272 */                 detallePagoComision.setEliminado(false);
/* 265:    */                 
/* 266:274 */                 BigDecimal baseComision = BigDecimal.ZERO;
/* 267:275 */                 BigDecimal valorComision = BigDecimal.ZERO;
/* 268:    */                 
/* 269:277 */                 BigDecimal valorImpuestosFactura = precioTotalFacturaConImpuestos.subtract(precioTotalFacturaSinImpuestos);
/* 270:    */                 
/* 271:279 */                 BigDecimal proporcionImpuestos = valorImpuestosFactura.divide(precioTotalFacturaSinImpuestos, 4, RoundingMode.HALF_UP);
/* 272:    */                 
/* 273:281 */                 BigDecimal valorImpuestosCobro = valorCobro.multiply(proporcionImpuestos);
/* 274:282 */                 BigDecimal valorCobroSinImpuestos = valorCobro.subtract(valorImpuestosCobro);
/* 275:    */                 
/* 276:284 */                 BigDecimal proporcionProductosFacturados = precioProductosSinImpuestos.divide(precioTotalFacturaSinImpuestos, 4, RoundingMode.HALF_UP);
/* 277:287 */                 if (detallePagoComision.getFormaPagoComisionEnum().equals(FormaPagoComisionEnum.PORCENTAJE_COBRO))
/* 278:    */                 {
/* 279:290 */                   BigDecimal valorCobroProductos = valorCobroSinImpuestos.multiply(proporcionProductosFacturados);
/* 280:291 */                   baseComision = valorCobroProductos;
/* 281:    */                   
/* 282:293 */                   valorComision = detalleVersionPlanComisionRangoDias.getValor().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP).multiply(baseComision);
/* 283:    */                 }
/* 284:295 */                 if (detallePagoComision.getFormaPagoComisionEnum().equals(FormaPagoComisionEnum.CANTIDAD))
/* 285:    */                 {
/* 286:297 */                   BigDecimal proporcionalCobro = valorCobroSinImpuestos.divide(precioTotalFacturaSinImpuestos, 4, RoundingMode.HALF_UP);
/* 287:    */                   
/* 288:299 */                   baseComision = cantidadProductos.multiply(proporcionalCobro);
/* 289:300 */                   valorComision = baseComision.multiply(detalleVersionPlanComisionRangoDias.getValor());
/* 290:    */                 }
/* 291:303 */                 baseComision = baseComision.add(detallePagoComision.getBaseComision());
/* 292:304 */                 valorComision = valorComision.add(detallePagoComision.getValorComision());
/* 293:305 */                 detallePagoComision.setBaseComision(baseComision.setScale(4, RoundingMode.HALF_UP));
/* 294:306 */                 detallePagoComision.setValorComision(valorComision.setScale(2, RoundingMode.HALF_UP));
/* 295:    */               }
/* 296:    */             }
/* 297:    */           }
/* 298:    */         }
/* 299:    */       }
/* 300:    */     }
/* 301:    */     EntidadUsuario agenteComercial;
/* 302:    */     Iterator localIterator3;
/* 303:    */     VersionPlanComision version;
/* 304:    */     Date fechaInicialVersion;
/* 305:    */     Date fechaFinalVersion;
/* 306:    */     Iterator localIterator4;
/* 307:    */     DetalleVersionPlanComision detalle;
/* 308:314 */     Object lista = new ArrayList();
/* 309:315 */     for (DetallePagoComision detallePagoComision : mapaComisiones.values()) {
/* 310:316 */       ((List)lista).add(detallePagoComision);
/* 311:    */     }
/* 312:319 */     return lista;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<Object[]> getReportePagoComision(PagoComision pagoComision)
/* 316:    */   {
/* 317:324 */     return this.pagoComisionDao.getReportePagoComision(pagoComision);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public PagoComision obtenerUltimoPagoComision()
/* 321:    */   {
/* 322:329 */     return this.pagoComisionDao.obtenerUltimoPagoComision();
/* 323:    */   }
/* 324:    */   
/* 325:    */   public List<Object[]> getReportePagoComision(int idOrganizacion, Date fechaDesde, Date fechaHasta, EntidadUsuario agenteComercial, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 326:    */   {
/* 327:336 */     return this.pagoComisionDao.getReportePagoComision(idOrganizacion, fechaDesde, fechaHasta, agenteComercial, categoriaProducto, subcategoriaProducto, producto, categoriaEmpresa, empresa);
/* 328:    */   }
/* 329:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioPagoComisionImpl
 * JD-Core Version:    0.7.0.1
 */