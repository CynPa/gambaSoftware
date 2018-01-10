/*   1:    */ package com.asinfo.as2.financiero.pagos.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*   5:    */ import com.asinfo.as2.dao.GenericoDao;
/*   6:    */ import com.asinfo.as2.dao.OrdenPagoProveedorDao;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   8:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   9:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  10:    */ import com.asinfo.as2.entities.DetalleFormaPago;
/*  11:    */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*  12:    */ import com.asinfo.as2.entities.DetallePago;
/*  13:    */ import com.asinfo.as2.entities.DetallePagoCash;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  17:    */ import com.asinfo.as2.entities.FormaPago;
/*  18:    */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*  19:    */ import com.asinfo.as2.entities.Pago;
/*  20:    */ import com.asinfo.as2.entities.PagoCash;
/*  21:    */ import com.asinfo.as2.entities.Proveedor;
/*  22:    */ import com.asinfo.as2.entities.Sucursal;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  27:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  28:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*  29:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import java.io.Serializable;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.HashSet;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import java.util.Set;
/*  40:    */ import javax.annotation.Resource;
/*  41:    */ import javax.ejb.EJB;
/*  42:    */ import javax.ejb.SessionContext;
/*  43:    */ import javax.ejb.Stateless;
/*  44:    */ import javax.ejb.TransactionAttribute;
/*  45:    */ import javax.ejb.TransactionAttributeType;
/*  46:    */ import javax.ejb.TransactionManagement;
/*  47:    */ import javax.ejb.TransactionManagementType;
/*  48:    */ 
/*  49:    */ @Stateless
/*  50:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  51:    */ public class ServicioOrdenPagoProveedorImpl
/*  52:    */   implements ServicioOrdenPagoProveedor, Serializable
/*  53:    */ {
/*  54:    */   @EJB
/*  55:    */   private transient OrdenPagoProveedorDao ordenPagoProveedorDao;
/*  56:    */   @EJB
/*  57:    */   private transient GenericoDao<DetalleOrdenPagoProveedor> detalleOrdenPagoProveedorDao;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioSecuencia servicioSecuencia;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioPago servicioPago;
/*  66:    */   @EJB
/*  67:    */   private transient CuentaPorPagarDao cuentaPorPagarDao;
/*  68:    */   @Resource
/*  69:    */   protected SessionContext context;
/*  70:    */   private static final long serialVersionUID = 1L;
/*  71:    */   
/*  72:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  73:    */   public void guardar(OrdenPagoProveedor ordenPagoProveedor)
/*  74:    */     throws AS2Exception
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78: 94 */       cargarSecuencia(ordenPagoProveedor);
/*  79: 95 */       validar(ordenPagoProveedor);
/*  80:    */       
/*  81:    */ 
/*  82: 98 */       this.ordenPagoProveedorDao.guardar(ordenPagoProveedor);
/*  83: 99 */       for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor())
/*  84:    */       {
/*  85:100 */         if ((detalle.isIndicadorAnticipo()) && (detalle.getValor().equals(BigDecimal.ZERO)) && 
/*  86:101 */           (detalle.getValorAprobado().equals(BigDecimal.ZERO))) {
/*  87:102 */           detalle.setEliminado(true);
/*  88:    */         }
/*  89:104 */         if ((detalle.getValor().equals(BigDecimal.ZERO)) && (detalle.getCuentaPorPagar() != null)) {
/*  90:105 */           detalle.setEliminado(true);
/*  91:    */         }
/*  92:110 */         if ((Estado.REVISADO.equals(ordenPagoProveedor.getEstado())) && (detalle.getValorAprobado().compareTo(BigDecimal.ZERO) == 1)) {
/*  93:111 */           detalle.setEliminado(false);
/*  94:    */         }
/*  95:113 */         if (detalle.isEliminado())
/*  96:    */         {
/*  97:114 */           if ((detalle.getCuentaPorPagar() != null) && (detalle.getCuentaPorPagar().isIndicadorEnOrdenPagoProveedor()))
/*  98:    */           {
/*  99:115 */             detalle.getCuentaPorPagar().setIndicadorEnOrdenPagoProveedor(false);
/* 100:116 */             this.cuentaPorPagarDao.guardar(detalle.getCuentaPorPagar());
/* 101:    */           }
/* 102:    */         }
/* 103:119 */         else if ((detalle.getCuentaPorPagar() != null) && (!detalle.getCuentaPorPagar().isIndicadorEnOrdenPagoProveedor()))
/* 104:    */         {
/* 105:120 */           detalle.getCuentaPorPagar().setIndicadorEnOrdenPagoProveedor(true);
/* 106:121 */           this.cuentaPorPagarDao.guardar(detalle.getCuentaPorPagar());
/* 107:    */         }
/* 108:124 */         this.detalleOrdenPagoProveedorDao.guardar(detalle);
/* 109:    */       }
/* 110:    */     }
/* 111:    */     catch (AS2Exception e)
/* 112:    */     {
/* 113:127 */       this.context.setRollbackOnly();
/* 114:128 */       throw e;
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:130 */       this.context.setRollbackOnly();
/* 119:131 */       e.printStackTrace();
/* 120:132 */       throw new AS2Exception(e.getMessage());
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void validar(OrdenPagoProveedor ordenPagoProveedor)
/* 125:    */     throws AS2Exception
/* 126:    */   {
/* 127:138 */     if (Estado.REVISADO.equals(ordenPagoProveedor.getEstado())) {
/* 128:139 */       for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 129:140 */         if (detalle.getCuentaPorPagar() != null)
/* 130:    */         {
/* 131:141 */           BigDecimal saldoCxPBD = this.cuentaPorPagarDao.getSaldo(detalle.getCuentaPorPagar());
/* 132:142 */           if (detalle.getValorAprobado().compareTo(saldoCxPBD) == 1) {
/* 133:144 */             throw new AS2Exception("com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioOrdenPagoProveedorImpl.MENSAJE_ERROR_VALOR_APROBADO_MAYOR_AL_SALDO", new String[] { "\n" + detalle.getValorAprobado().toString(), detalle.getCuentaPorPagar().getSaldo().toString() });
/* 134:    */           }
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:149 */     for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 139:150 */       if (detalle.getCuentaPorPagar() != null)
/* 140:    */       {
/* 141:151 */         BigDecimal saldoCxPBD = this.cuentaPorPagarDao.getSaldo(detalle.getCuentaPorPagar());
/* 142:152 */         if (detalle.getValor().compareTo(saldoCxPBD) == 1) {
/* 143:154 */           throw new AS2Exception("com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioOrdenPagoProveedorImpl.MENSAJE_ERROR_VALOR_APROBADO_MAYOR_AL_SALDO", new String[] { "\n" + detalle.getValor().toString(), detalle.getCuentaPorPagar().getSaldo().toString() });
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:158 */     if (Estado.ELABORADO.equals(ordenPagoProveedor.getEstado())) {
/* 148:163 */       for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 149:164 */         if ((!detalle.isEliminado()) && (detalle.getCuentaPorPagar() != null) && (detalle.getValor().compareTo(BigDecimal.ZERO) != 0) && 
/* 150:165 */           (detalle.getId() == 0) && 
/* 151:166 */           (this.cuentaPorPagarDao.isIndicadorEnOrdenPagoProveedor(detalle.getCuentaPorPagar()).booleanValue())) {
/* 152:168 */           throw new AS2Exception("com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioOrdenPagoProveedorImpl.MENSAJE_ERROR_CUENTA_POR_PAGAR_YA_ESTA_REGISTRADA", new String[] {detalle.getCuentaPorPagar().getFacturaProveedor().getNumero() });
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   private void actualizarEstado(OrdenPagoProveedor ordenPagoProveedor)
/* 159:    */   {
/* 160:176 */     if (((ordenPagoProveedor.getEstado().equals(Estado.ELABORADO)) || (Estado.REVISADO.equals(ordenPagoProveedor.getEstado()))) && 
/* 161:177 */       (ordenPagoProveedor.getValorPago().compareTo(BigDecimal.ZERO) > 0) && 
/* 162:178 */       (ordenPagoProveedor.getValorAprobado().compareTo(BigDecimal.ZERO) > 0)) {
/* 163:179 */       if (ordenPagoProveedor.getValorAprobado().compareTo(ordenPagoProveedor.getValorPago()) < 0) {
/* 164:180 */         ordenPagoProveedor.setEstado(Estado.APROBADO_PARCIAL);
/* 165:    */       } else {
/* 166:182 */         ordenPagoProveedor.setEstado(Estado.APROBADO);
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   private void cargarSecuencia(OrdenPagoProveedor ordenPagoProveedor)
/* 172:    */     throws ExcepcionAS2
/* 173:    */   {
/* 174:190 */     if (ordenPagoProveedor.getNumero().equals(""))
/* 175:    */     {
/* 176:191 */       String numero = "";
/* 177:192 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(ordenPagoProveedor.getDocumento().getId(), ordenPagoProveedor.getFechaCorte());
/* 178:193 */       ordenPagoProveedor.setNumero(numero);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 183:    */   public void eliminar(OrdenPagoProveedor ordenPagoProveedor)
/* 184:    */     throws AS2Exception
/* 185:    */   {
/* 186:200 */     ordenPagoProveedor = cargarDetalle(ordenPagoProveedor.getId());
/* 187:201 */     if (!ordenPagoProveedor.getEstado().equals(Estado.ELABORADO)) {
/* 188:202 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] { ordenPagoProveedor.getEstado().toString() });
/* 189:    */     }
/* 190:    */     try
/* 191:    */     {
/* 192:205 */       for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 193:206 */         this.detalleOrdenPagoProveedorDao.eliminar(detalle);
/* 194:    */       }
/* 195:208 */       this.ordenPagoProveedorDao.eliminar(ordenPagoProveedor);
/* 196:    */     }
/* 197:    */     catch (Exception e)
/* 198:    */     {
/* 199:210 */       this.context.setRollbackOnly();
/* 200:211 */       throw new AS2Exception(e.getMessage());
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public OrdenPagoProveedor buscarPorId(Integer id)
/* 205:    */   {
/* 206:217 */     return (OrdenPagoProveedor)this.ordenPagoProveedorDao.buscarPorId(id);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void detach(OrdenPagoProveedor ordenPagoProveedor)
/* 210:    */   {
/* 211:222 */     this.ordenPagoProveedorDao.detach(ordenPagoProveedor);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public OrdenPagoProveedor cargarDetalle(int idOrdenPagoProveedor)
/* 215:    */   {
/* 216:227 */     return this.ordenPagoProveedorDao.cargarDetalle(idOrdenPagoProveedor);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<OrdenPagoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 220:    */   {
/* 221:233 */     return this.ordenPagoProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public int contarPorCriterio(Map<String, String> filters)
/* 225:    */   {
/* 226:238 */     return this.ordenPagoProveedorDao.contarPorCriterio(filters);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void cargarFacturasPendientes(OrdenPagoProveedor ordenPagoProveedor)
/* 230:    */   {
/* 231:245 */     List<CuentaPorPagar> listaFacturasPendientes = this.servicioFacturaProveedor.obtenerFacturasPendientes(0, 0, ordenPagoProveedor
/* 232:246 */       .getFechaCorte(), null, null, 0, null, null, Integer.valueOf(ordenPagoProveedor.getIdOrganizacion()), ordenPagoProveedor.getFechaDesdeFiltro(), Boolean.valueOf(false));
/* 233:247 */     Map<Integer, CuentaPorPagar> mapa = new HashMap();
/* 234:248 */     Set<Integer> cuentasPorPagarAsignadas = new HashSet();
/* 235:250 */     for (CuentaPorPagar cuentaPorPagar : listaFacturasPendientes) {
/* 236:251 */       mapa.put(Integer.valueOf(cuentaPorPagar.getId()), cuentaPorPagar);
/* 237:    */     }
/* 238:256 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 239:257 */       if ((!detalleOrdenPago.isIndicadorAnticipo()) && (!detalleOrdenPago.isEliminado())) {
/* 240:258 */         if (mapa.containsKey(Integer.valueOf(detalleOrdenPago.getCuentaPorPagar().getId())))
/* 241:    */         {
/* 242:259 */           CuentaPorPagar cxp = (CuentaPorPagar)mapa.get(Integer.valueOf(detalleOrdenPago.getCuentaPorPagar().getId()));
/* 243:260 */           detalleOrdenPago.setEliminado(false);
/* 244:261 */           detalleOrdenPago.setDiasVencidos(
/* 245:262 */             (int)FuncionesUtiles.DiasEntreFechas(cxp.getFechaVencimiento(), ordenPagoProveedor.getFechaCorte()) - 1);
/* 246:263 */           detalleOrdenPago.setCuentaPorPagar(cxp);
/* 247:264 */           cuentasPorPagarAsignadas.add(Integer.valueOf(cxp.getId()));
/* 248:    */         }
/* 249:    */         else
/* 250:    */         {
/* 251:266 */           detalleOrdenPago.setEliminado(true);
/* 252:    */         }
/* 253:    */       }
/* 254:    */     }
/* 255:272 */     for (CuentaPorPagar cxp : listaFacturasPendientes) {
/* 256:273 */       if (!cuentasPorPagarAsignadas.contains(Integer.valueOf(cxp.getId())))
/* 257:    */       {
/* 258:274 */         DetalleOrdenPagoProveedor detalleOrdenPago = new DetalleOrdenPagoProveedor();
/* 259:275 */         detalleOrdenPago.setIdOrganizacion(ordenPagoProveedor.getIdOrganizacion());
/* 260:276 */         detalleOrdenPago.setOrdenPagoProveedor(ordenPagoProveedor);
/* 261:277 */         detalleOrdenPago.setEliminado(false);
/* 262:278 */         detalleOrdenPago.setCuentaPorPagar(cxp);
/* 263:279 */         detalleOrdenPago.setProveedor(cxp.getFacturaProveedor().getEmpresa().getProveedor());
/* 264:280 */         detalleOrdenPago.setValor(BigDecimal.ZERO);
/* 265:281 */         detalleOrdenPago
/* 266:282 */           .setDiasVencidos((int)FuncionesUtiles.DiasEntreFechas(cxp.getFechaVencimiento(), ordenPagoProveedor.getFechaCorte()) - 1);
/* 267:283 */         ordenPagoProveedor.getListaDetalleOrdenPagoProveedor().add(detalleOrdenPago);
/* 268:    */       }
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   public List<Object[]> getReporteOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 273:    */   {
/* 274:290 */     return this.ordenPagoProveedorDao.getReporteOrdenPagoProveedor(ordenPagoProveedor);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public List<DetalleOrdenPagoProveedor> buscarDetallesPendientesPago(int idOrganizacion, Boolean indicadorPagoCash, Date fechaPago)
/* 278:    */   {
/* 279:295 */     return this.ordenPagoProveedorDao.buscarDetallesPendientesPago(idOrganizacion, indicadorPagoCash, fechaPago, null, null, null, null, null);
/* 280:    */   }
/* 281:    */   
/* 282:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 283:    */   public void generarPagos(Pago pagoBase, DetalleFormaPago detalleFormePagoBase, List<Proveedor> listaProveedor, String nombreUsuario)
/* 284:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 285:    */   {
/* 286:    */     try
/* 287:    */     {
/* 288:303 */       Set<Integer> listaIdOrdenPagoProveedor = new HashSet();
/* 289:304 */       Map<Integer, CuentaPorPagar> hashCuentaPorPagar = new HashMap();
/* 290:306 */       for (Proveedor proveedor : listaProveedor) {
/* 291:307 */         if (proveedor.getValorPagado().compareTo(BigDecimal.ZERO) > 0)
/* 292:    */         {
/* 293:309 */           Pago pago = new Pago();
/* 294:310 */           pago.setIdOrganizacion(pagoBase.getIdOrganizacion());
/* 295:311 */           pago.setSucursal(pagoBase.getSucursal());
/* 296:312 */           pago.setDocumento(pagoBase.getDocumento());
/* 297:313 */           pago.setDescripcion(proveedor.getDescripcionPagoAgil());
/* 298:314 */           pago.setEmpresa(proveedor.getEmpresa());
/* 299:315 */           pago.setFecha(pagoBase.getFecha());
/* 300:316 */           pago.setEstado(Estado.CONTABILIZADO);
/* 301:317 */           pago.setNumero("");
/* 302:318 */           pago.setValor(proveedor.getValorPagado());
/* 303:319 */           pago.setBeneficiario(proveedor.getEmpresa().getNombreFiscal().length() > 49 ? proveedor
/* 304:320 */             .getEmpresa().getNombreFiscal().substring(0, 49) : proveedor.getEmpresa().getNombreFiscal());
/* 305:    */           
/* 306:322 */           DetalleFormaPago detalleFormaPago = new DetalleFormaPago();
/* 307:323 */           detalleFormaPago.setIdOrganizacion(pago.getIdOrganizacion());
/* 308:324 */           detalleFormaPago.setIdSucursal(pago.getSucursal().getId());
/* 309:325 */           detalleFormaPago.setCuentaBancariaOrganizacion(detalleFormePagoBase.getCuentaBancariaOrganizacion());
/* 310:326 */           detalleFormaPago.setCuentaContableFormaPago(detalleFormePagoBase.getCuentaContableFormaPago());
/* 311:327 */           detalleFormaPago.setFormaPago(detalleFormePagoBase.getFormaPago());
/* 312:328 */           detalleFormaPago.setSecuencia(detalleFormePagoBase.getSecuencia());
/* 313:329 */           detalleFormaPago.setValor(proveedor.getValorPagado());
/* 314:330 */           detalleFormaPago.setPago(pago);
/* 315:331 */           pago.getListaDetalleFormaPago().add(detalleFormaPago);
/* 316:    */           String numero;
/* 317:334 */           if (detalleFormaPago.getFormaPago().isIndicadorCheque())
/* 318:    */           {
/* 319:335 */             numero = "";
/* 320:336 */             if (detalleFormaPago.getSecuencia() != null)
/* 321:    */             {
/* 322:337 */               numero = this.servicioSecuencia.obtenerSecuencia(detalleFormaPago.getSecuencia(), pago.getFecha());
/* 323:338 */               detalleFormaPago.setDocumentoReferencia(numero);
/* 324:    */             }
/* 325:    */           }
/* 326:342 */           for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 327:343 */             if (detalleOrdenPago.getValorPagado().compareTo(BigDecimal.ZERO) > 0)
/* 328:    */             {
/* 329:344 */               if (detalleOrdenPago.isIndicadorAnticipo())
/* 330:    */               {
/* 331:346 */                 String numero = "";
/* 332:347 */                 if ((detalleFormaPago.getFormaPago().isIndicadorCheque()) && 
/* 333:348 */                   (detalleFormaPago.getSecuencia() != null)) {
/* 334:349 */                   numero = this.servicioSecuencia.obtenerSecuencia(detalleFormaPago.getSecuencia(), pago.getFecha());
/* 335:    */                 }
/* 336:352 */                 generarAnticipo(detalleOrdenPago, detalleFormaPago, pago, numero);
/* 337:    */                 
/* 338:354 */                 detalleFormaPago.setValor(detalleFormaPago.getValor().subtract(detalleOrdenPago.getValorPagado()));
/* 339:355 */                 pago.setValor(pago.getValor().subtract(detalleOrdenPago.getValorPagado()));
/* 340:    */               }
/* 341:    */               else
/* 342:    */               {
/* 343:357 */                 DetallePago detallePago = new DetallePago();
/* 344:358 */                 detallePago.setIdOrganizacion(pago.getIdOrganizacion());
/* 345:359 */                 detallePago.setIdSucursal(pago.getSucursal().getId());
/* 346:360 */                 detallePago.setCuentaPorPagar(detalleOrdenPago.getCuentaPorPagar());
/* 347:361 */                 detallePago.setValor(detalleOrdenPago.getValorPagado());
/* 348:362 */                 detallePago.setPago(pago);
/* 349:363 */                 detallePago.setDetalleOrdenPagoProveedor(detalleOrdenPago);
/* 350:364 */                 pago.getListaDetallePago().add(detallePago);
/* 351:365 */                 if (pago.getOrdenPagoProveedor() == null) {
/* 352:366 */                   pago.setOrdenPagoProveedor(detalleOrdenPago.getOrdenPagoProveedor());
/* 353:    */                 }
/* 354:    */               }
/* 355:369 */               detalleOrdenPago.setIndicadorPagado(true);
/* 356:370 */               listaIdOrdenPagoProveedor.add(Integer.valueOf(detalleOrdenPago.getOrdenPagoProveedor().getId()));
/* 357:371 */               if (detalleOrdenPago.getCuentaPorPagar() != null)
/* 358:    */               {
/* 359:372 */                 CuentaPorPagar cuentaPorPagarDOPP = detalleOrdenPago.getCuentaPorPagar();
/* 360:373 */                 hashCuentaPorPagar.put(Integer.valueOf(cuentaPorPagarDOPP.getId()), cuentaPorPagarDOPP);
/* 361:    */               }
/* 362:375 */               this.detalleOrdenPagoProveedorDao.guardar(detalleOrdenPago);
/* 363:    */             }
/* 364:    */           }
/* 365:378 */           if (pago.getListaDetallePago().size() > 0) {
/* 366:379 */             this.servicioPago.guardar(pago);
/* 367:    */           }
/* 368:    */         }
/* 369:    */       }
/* 370:383 */       for (Integer idOPP : listaIdOrdenPagoProveedor) {
/* 371:384 */         this.ordenPagoProveedorDao.cerrarOrdenPagoProveedor(idOPP, nombreUsuario);
/* 372:    */       }
/* 373:386 */       liberarCuentasPorPagar(hashCuentaPorPagar);
/* 374:    */     }
/* 375:    */     catch (ExcepcionAS2Financiero e)
/* 376:    */     {
/* 377:388 */       this.context.setRollbackOnly();
/* 378:389 */       e.printStackTrace();
/* 379:390 */       throw e;
/* 380:    */     }
/* 381:    */     catch (ExcepcionAS2 e)
/* 382:    */     {
/* 383:392 */       this.context.setRollbackOnly();
/* 384:393 */       e.printStackTrace();
/* 385:394 */       throw e;
/* 386:    */     }
/* 387:    */     catch (Exception e)
/* 388:    */     {
/* 389:396 */       this.context.setRollbackOnly();
/* 390:397 */       e.printStackTrace();
/* 391:398 */       throw new ExcepcionAS2(e);
/* 392:    */     }
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void liberarCuentasPorPagar(Map<Integer, CuentaPorPagar> hashCuentasPorPagar)
/* 396:    */   {
/* 397:408 */     for (CuentaPorPagar cpp : hashCuentasPorPagar.values()) {
/* 398:409 */       this.cuentaPorPagarDao.actualizarIndicadorOrdenPagoProveedor(cpp);
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   private void generarAnticipo(DetalleOrdenPagoProveedor detalleOrdenPagoProveedor, DetalleFormaPago detalleFormaPago, Pago pago, String numero)
/* 403:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 404:    */   {
/* 405:414 */     AnticipoProveedor anticipoProveedor = new AnticipoProveedor();
/* 406:415 */     anticipoProveedor.setBeneficiario(detalleOrdenPagoProveedor.getProveedor().getEmpresa().getNombreFiscal().length() > 49 ? detalleOrdenPagoProveedor
/* 407:416 */       .getProveedor().getEmpresa().getNombreFiscal().substring(0, 49) : detalleOrdenPagoProveedor
/* 408:417 */       .getProveedor().getEmpresa().getNombreFiscal());
/* 409:418 */     anticipoProveedor.setCuentaBancariaOrganizacion(detalleFormaPago.getCuentaBancariaOrganizacion());
/* 410:419 */     anticipoProveedor.setDescripcion(detalleOrdenPagoProveedor.getDescripcion());
/* 411:420 */     anticipoProveedor.setPersonaResponsable(detalleOrdenPagoProveedor.getPersonaResponsable());
/* 412:    */     
/* 413:422 */     anticipoProveedor.setDocumento(detalleOrdenPagoProveedor.getOrdenPagoProveedor().getDocumentoAnticipo());
/* 414:423 */     anticipoProveedor.setDocumentoReferencia(numero);
/* 415:424 */     anticipoProveedor.setEmpresa(pago.getEmpresa());
/* 416:425 */     anticipoProveedor.setFecha(pago.getFecha());
/* 417:426 */     anticipoProveedor.setFormaPago(detalleFormaPago.getFormaPago());
/* 418:427 */     anticipoProveedor.setValor(detalleOrdenPagoProveedor.getValorPagado());
/* 419:428 */     anticipoProveedor.setIdOrganizacion(pago.getIdOrganizacion());
/* 420:429 */     anticipoProveedor.setSucursal(pago.getSucursal());
/* 421:430 */     anticipoProveedor.setNumero("");
/* 422:431 */     anticipoProveedor.setEstado(Estado.CONTABILIZADO);
/* 423:432 */     anticipoProveedor.setIndicadorContabilizar(Boolean.valueOf(pago.getDocumento().isIndicadorContabilizar()));
/* 424:433 */     anticipoProveedor.setDetalleOrdenPagoProveedor(detalleOrdenPagoProveedor);
/* 425:434 */     anticipoProveedor.setSecuencia(detalleFormaPago.getSecuencia());
/* 426:    */     
/* 427:436 */     this.servicioAnticipoProveedor.guardar(anticipoProveedor);
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void reversarOrdenAlAnularPago(Pago pago)
/* 431:    */   {
/* 432:441 */     List<Integer> listaIdDetallesPagoProveedor = new ArrayList();
/* 433:442 */     List<Integer> listaIdOrdenPagoProveedor = new ArrayList();
/* 434:443 */     if (pago.getOrdenPagoProveedor() != null) {
/* 435:444 */       for (DetallePago detallePago : pago.getListaDetallePago()) {
/* 436:445 */         if (detallePago.getDetalleOrdenPagoProveedor() != null)
/* 437:    */         {
/* 438:446 */           detallePago.getDetalleOrdenPagoProveedor().setIndicadorPagado(false);
/* 439:447 */           detallePago.getDetalleOrdenPagoProveedor().setValorPagado(BigDecimal.ZERO);
/* 440:448 */           listaIdDetallesPagoProveedor.add(Integer.valueOf(detallePago.getDetalleOrdenPagoProveedor().getId()));
/* 441:449 */           listaIdOrdenPagoProveedor.add(Integer.valueOf(detallePago.getDetalleOrdenPagoProveedor().getOrdenPagoProveedor().getId()));
/* 442:450 */           this.detalleOrdenPagoProveedorDao.guardar(detallePago.getDetalleOrdenPagoProveedor());
/* 443:    */         }
/* 444:    */       }
/* 445:    */     }
/* 446:454 */     for (Integer integer : listaIdDetallesPagoProveedor) {
/* 447:455 */       this.ordenPagoProveedorDao.desbloquearCuentaPorPagarSegunOrden(integer.intValue());
/* 448:    */     }
/* 449:457 */     for (Integer integer : listaIdOrdenPagoProveedor) {
/* 450:458 */       this.ordenPagoProveedorDao.abrirOrden(integer.intValue());
/* 451:    */     }
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void reversarOrdenAlAnularPagoCash(PagoCash pagoCash)
/* 455:    */   {
/* 456:464 */     List<Integer> listaIdDetallesPagoProveedor = new ArrayList();
/* 457:465 */     List<Integer> listaIdOrdenPagoProveedor = new ArrayList();
/* 458:466 */     if (pagoCash.getOrdenPagoProveedor() != null) {
/* 459:467 */       for (DetallePagoCash detallePago : pagoCash.getListaDetallePagoCash()) {
/* 460:468 */         if (detallePago.getDetalleOrdenPagoProveedor() != null)
/* 461:    */         {
/* 462:469 */           detallePago.getDetalleOrdenPagoProveedor().setIndicadorPagado(false);
/* 463:470 */           detallePago.getDetalleOrdenPagoProveedor().setValorPagado(BigDecimal.ZERO);
/* 464:471 */           listaIdDetallesPagoProveedor.add(Integer.valueOf(detallePago.getDetalleOrdenPagoProveedor().getId()));
/* 465:472 */           listaIdOrdenPagoProveedor.add(Integer.valueOf(detallePago.getDetalleOrdenPagoProveedor().getOrdenPagoProveedor().getId()));
/* 466:473 */           this.detalleOrdenPagoProveedorDao.guardar(detallePago.getDetalleOrdenPagoProveedor());
/* 467:    */         }
/* 468:    */       }
/* 469:    */     }
/* 470:477 */     for (Integer integer : listaIdDetallesPagoProveedor) {
/* 471:478 */       this.ordenPagoProveedorDao.desbloquearCuentaPorPagarSegunOrden(integer.intValue());
/* 472:    */     }
/* 473:480 */     for (Integer integer : listaIdOrdenPagoProveedor) {
/* 474:481 */       this.ordenPagoProveedorDao.abrirOrden(integer.intValue());
/* 475:    */     }
/* 476:    */   }
/* 477:    */   
/* 478:    */   public void reversarOrdenAlAnularAnticipo(AnticipoProveedor anticipoProveedor)
/* 479:    */   {
/* 480:487 */     List<Integer> listaIdOrdenPagoProveedor = new ArrayList();
/* 481:488 */     if (anticipoProveedor.getDetalleOrdenPagoProveedor() != null)
/* 482:    */     {
/* 483:489 */       anticipoProveedor.getDetalleOrdenPagoProveedor().setIndicadorPagado(false);
/* 484:490 */       anticipoProveedor.getDetalleOrdenPagoProveedor().setValorPagado(BigDecimal.ZERO);
/* 485:491 */       listaIdOrdenPagoProveedor.add(Integer.valueOf(anticipoProveedor.getDetalleOrdenPagoProveedor().getOrdenPagoProveedor().getId()));
/* 486:492 */       this.detalleOrdenPagoProveedorDao.guardar(anticipoProveedor.getDetalleOrdenPagoProveedor());
/* 487:    */     }
/* 488:494 */     for (Integer integer : listaIdOrdenPagoProveedor) {
/* 489:495 */       this.ordenPagoProveedorDao.abrirOrden(integer.intValue());
/* 490:    */     }
/* 491:    */   }
/* 492:    */   
/* 493:    */   public void isEditable(OrdenPagoProveedor ordenPagoProveedor)
/* 494:    */     throws AS2Exception
/* 495:    */   {
/* 496:501 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 497:502 */       if (detalleOrdenPago.isIndicadorPagado()) {
/* 498:503 */         throw new AS2Exception("com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCobroImpl.NO_EDITABLE_EXISTEN_PAGOS", new String[] { "" });
/* 499:    */       }
/* 500:    */     }
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void cerrar(OrdenPagoProveedor ordenPagoProveedor, String nombreUsuario)
/* 504:    */   {
/* 505:510 */     ordenPagoProveedor = cargarDetalle(ordenPagoProveedor.getId());
/* 506:511 */     ordenPagoProveedor.setEstado(Estado.CERRADO);
/* 507:512 */     ordenPagoProveedor.setUsuarioCierre(nombreUsuario);
/* 508:513 */     ordenPagoProveedor.setFechaCierre(new Date());
/* 509:514 */     this.ordenPagoProveedorDao.guardar(ordenPagoProveedor);
/* 510:517 */     for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor())
/* 511:    */     {
/* 512:518 */       boolean liberaFactura = false;
/* 513:519 */       CuentaPorPagar cuentaPorPagar = detalle.getCuentaPorPagar();
/* 514:520 */       if ((!detalle.isIndicadorAnticipo()) && (!detalle.isIndicadorPagado())) {
/* 515:521 */         liberaFactura = true;
/* 516:    */       }
/* 517:523 */       liberarFactura(cuentaPorPagar, true);
/* 518:    */     }
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void anular(OrdenPagoProveedor ordenPagoProveedor)
/* 522:    */     throws AS2Exception
/* 523:    */   {
/* 524:529 */     if (!Estado.APROBADO.equals(ordenPagoProveedor.getEstado()))
/* 525:    */     {
/* 526:530 */       ordenPagoProveedor = this.ordenPagoProveedorDao.cargarDetalle(ordenPagoProveedor.getId());
/* 527:531 */       ordenPagoProveedor.setEstado(Estado.ANULADO);
/* 528:532 */       for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 529:533 */         if (detalle.getCuentaPorPagar() != null)
/* 530:    */         {
/* 531:534 */           detalle.getCuentaPorPagar().setIndicadorEnOrdenPagoProveedor(false);
/* 532:535 */           this.cuentaPorPagarDao.guardar(detalle.getCuentaPorPagar());
/* 533:    */         }
/* 534:    */       }
/* 535:538 */       this.ordenPagoProveedorDao.guardar(ordenPagoProveedor);
/* 536:    */     }
/* 537:    */     else
/* 538:    */     {
/* 539:540 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] { ordenPagoProveedor.getEstado().toString() });
/* 540:    */     }
/* 541:    */   }
/* 542:    */   
/* 543:    */   public void revisarOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor, String usuarioRevisor)
/* 544:    */   {
/* 545:546 */     ordenPagoProveedor.setUsuarioRevisor(usuarioRevisor);
/* 546:547 */     ordenPagoProveedor.setEstado(Estado.REVISADO);
/* 547:548 */     ordenPagoProveedor.setFechaRevision(new Date());
/* 548:549 */     this.ordenPagoProveedorDao.guardar(ordenPagoProveedor);
/* 549:    */   }
/* 550:    */   
/* 551:    */   public List<Integer> buscarOrdenPagoProveedorPorNumeroDeFactura(int idOrganizacion, String numeroFactura)
/* 552:    */   {
/* 553:554 */     return this.ordenPagoProveedorDao.buscarOrdenPagoProveedorPorNumeroDeFactura(idOrganizacion, numeroFactura);
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void aprobarOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 557:    */   {
/* 558:559 */     ordenPagoProveedor = cargarDetalle(ordenPagoProveedor.getId());
/* 559:560 */     ordenPagoProveedor.setEstado(Estado.APROBADO);
/* 560:561 */     this.ordenPagoProveedorDao.guardar(ordenPagoProveedor);
/* 561:564 */     for (DetalleOrdenPagoProveedor detalle : ordenPagoProveedor.getListaDetalleOrdenPagoProveedor())
/* 562:    */     {
/* 563:565 */       boolean liberaFactura = false;
/* 564:566 */       CuentaPorPagar cuentaPorPagar = detalle.getCuentaPorPagar();
/* 565:567 */       if ((!detalle.isIndicadorAnticipo()) && (BigDecimal.ZERO.compareTo(ordenPagoProveedor.getValorAprobado()) == 0)) {
/* 566:568 */         liberaFactura = true;
/* 567:    */       }
/* 568:570 */       liberarFactura(cuentaPorPagar, liberaFactura);
/* 569:    */     }
/* 570:    */   }
/* 571:    */   
/* 572:    */   private void liberarFactura(CuentaPorPagar cuentaPorPagar, boolean liberaFactura)
/* 573:    */   {
/* 574:575 */     if (liberaFactura)
/* 575:    */     {
/* 576:577 */       if ((cuentaPorPagar != null) && (cuentaPorPagar.isIndicadorEnOrdenPagoProveedor()))
/* 577:    */       {
/* 578:578 */         cuentaPorPagar.setIndicadorEnOrdenPagoProveedor(false);
/* 579:579 */         this.cuentaPorPagarDao.guardar(cuentaPorPagar);
/* 580:    */       }
/* 581:    */     }
/* 582:583 */     else if ((cuentaPorPagar != null) && (!cuentaPorPagar.isIndicadorEnOrdenPagoProveedor()))
/* 583:    */     {
/* 584:584 */       cuentaPorPagar.setIndicadorEnOrdenPagoProveedor(true);
/* 585:585 */       this.cuentaPorPagarDao.guardar(cuentaPorPagar);
/* 586:    */     }
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void actualizarAtributoEntidad(OrdenPagoProveedor ordenPagoProveedor, HashMap<String, Object> campos)
/* 590:    */   {
/* 591:592 */     this.ordenPagoProveedorDao.actualizarAtributoEntidad(ordenPagoProveedor, campos);
/* 592:    */   }
/* 593:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioOrdenPagoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */