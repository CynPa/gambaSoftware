/*   1:    */ package com.asinfo.as2.financiero.activos.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.clases.DetalleInterfazContableProcesoBajaActivo;
/*   6:    */ import com.asinfo.as2.clases.DetalleInterfazContableProcesoDepreciaciones;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   8:    */ import com.asinfo.as2.dao.ActivoFijoDao;
/*   9:    */ import com.asinfo.as2.dao.DepreciacionDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*  11:    */ import com.asinfo.as2.entities.ActivoFijo;
/*  12:    */ import com.asinfo.as2.entities.Asiento;
/*  13:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*  14:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  15:    */ import com.asinfo.as2.entities.Departamento;
/*  16:    */ import com.asinfo.as2.entities.Depreciacion;
/*  17:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*  18:    */ import com.asinfo.as2.entities.DimensionContable;
/*  19:    */ import com.asinfo.as2.entities.Documento;
/*  20:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  21:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*  22:    */ import com.asinfo.as2.entities.Organizacion;
/*  23:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*  24:    */ import com.asinfo.as2.entities.Sucursal;
/*  25:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  26:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  27:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  28:    */ import com.asinfo.as2.enumeraciones.FormatoCelda;
/*  29:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  30:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  31:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  32:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  33:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo;
/*  34:    */ import com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacion;
/*  35:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  36:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  37:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  38:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  39:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  40:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  41:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  42:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  43:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  44:    */ import com.asinfo.as2.util.AppUtil;
/*  45:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  46:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  47:    */ import java.io.InputStream;
/*  48:    */ import java.io.PrintStream;
/*  49:    */ import java.math.BigDecimal;
/*  50:    */ import java.util.ArrayList;
/*  51:    */ import java.util.Date;
/*  52:    */ import java.util.HashMap;
/*  53:    */ import java.util.Iterator;
/*  54:    */ import java.util.List;
/*  55:    */ import java.util.Map;
/*  56:    */ import javax.ejb.EJB;
/*  57:    */ import javax.ejb.SessionContext;
/*  58:    */ import javax.ejb.Stateless;
/*  59:    */ import javax.ejb.TransactionAttribute;
/*  60:    */ import javax.ejb.TransactionAttributeType;
/*  61:    */ import org.apache.log4j.Logger;
/*  62:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  63:    */ 
/*  64:    */ @Stateless
/*  65:    */ public class ServicioActivoFijoImpl
/*  66:    */   extends AbstractServicioAS2Financiero
/*  67:    */   implements ServicioActivoFijo
/*  68:    */ {
/*  69:    */   private static final long serialVersionUID = -991873840151626208L;
/*  70:    */   @EJB
/*  71:    */   private ActivoFijoDao activoFijoDao;
/*  72:    */   @EJB
/*  73:    */   private ServicioDepreciacion servicioDepreciacion;
/*  74:    */   @EJB
/*  75:    */   private DepreciacionDao depreciacionDao;
/*  76:    */   @EJB
/*  77:    */   private ServicioCategoriaActivo servicioCategoriaActivo;
/*  78:    */   @EJB
/*  79:    */   private ServicioSucursal servicioSucursal;
/*  80:    */   @EJB
/*  81:    */   private ServicioDepartamento servicioDepartamento;
/*  82:    */   @EJB
/*  83:    */   private ServicioDimensionContable servicioDimensionContable;
/*  84:    */   @EJB
/*  85:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  86:    */   @EJB
/*  87:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  88:    */   @EJB
/*  89:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  90:    */   @EJB
/*  91:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  92:    */   @EJB
/*  93:    */   private transient ServicioPeriodo servicioPeriodo;
/*  94:    */   
/*  95:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  96:    */   public void guardar(ActivoFijo activoFijo)
/*  97:    */     throws Exception
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:121 */       List<Depreciacion> lista = activoFijo.getListaDepreciacion();
/* 102:122 */       activoFijo.setListaDepreciacion(null);
/* 103:123 */       this.activoFijoDao.guardar(activoFijo);
/* 104:124 */       activoFijo.setListaDepreciacion(lista);
/* 105:125 */       if ((activoFijo.getListaDepreciacion().size() == 2) && (activoFijo.getMotivoBajaActivo() == null)) {
/* 106:126 */         for (Depreciacion depreciacion : activoFijo.getListaDepreciacion()) {
/* 107:127 */           this.servicioDepreciacion.guardar(depreciacion);
/* 108:    */         }
/* 109:    */       }
/* 110:131 */       if (activoFijo.getMotivoBajaActivo() != null)
/* 111:    */       {
/* 112:132 */         validarBajaActivo(activoFijo);
/* 113:135 */         if (!activoFijo.getMotivoBajaActivo().isIndicadorFinVidaUtil()) {
/* 114:136 */           contabilizar(activoFijo);
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:    */     catch (ExcepcionAS2Financiero e)
/* 119:    */     {
/* 120:141 */       this.context.setRollbackOnly();
/* 121:142 */       throw e;
/* 122:    */     }
/* 123:    */     catch (ExcepcionAS2 e)
/* 124:    */     {
/* 125:144 */       this.context.setRollbackOnly();
/* 126:145 */       throw e;
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:147 */       this.context.setRollbackOnly();
/* 131:148 */       throw e;
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void eliminar(ActivoFijo activoFijo)
/* 136:    */     throws ExcepcionAS2Financiero
/* 137:    */   {
/* 138:160 */     activoFijo = cargarDetalle(activoFijo.getId());
/* 139:161 */     esEditable(activoFijo);
/* 140:162 */     this.activoFijoDao.eliminar(activoFijo);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public ActivoFijo buscarPorId(int idActivoFijo)
/* 144:    */   {
/* 145:173 */     return (ActivoFijo)this.activoFijoDao.buscarPorId(Integer.valueOf(idActivoFijo));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<ActivoFijo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 149:    */   {
/* 150:184 */     return this.activoFijoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<ActivoFijo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 154:    */   {
/* 155:194 */     return this.activoFijoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int contarPorCriterio(Map<String, String> filters)
/* 159:    */   {
/* 160:204 */     return this.activoFijoDao.contarPorCriterio(filters);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public ActivoFijo cargarDetalle(int idActivoFijo)
/* 164:    */   {
/* 165:214 */     return this.activoFijoDao.cargarDetalle(idActivoFijo);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void esEditable(ActivoFijo activoFijo)
/* 169:    */   {
/* 170:225 */     if (activoFijo.getListaDepreciacion().size() == 2) {
/* 171:226 */       for (Depreciacion depreciacion : activoFijo.getListaDepreciacion())
/* 172:    */       {
/* 173:227 */         boolean existeDepreciacionDepreciados = false;
/* 174:228 */         for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion()) {
/* 175:229 */           if (detalleDepreciacion.getHistoricoDepreciacion() != null)
/* 176:    */           {
/* 177:230 */             existeDepreciacionDepreciados = true;
/* 178:231 */             break;
/* 179:    */           }
/* 180:    */         }
/* 181:234 */         if (existeDepreciacionDepreciados)
/* 182:    */         {
/* 183:235 */           depreciacion.setTraEsEditable(false);
/* 184:236 */           activoFijo.setTraEsEditable(true);
/* 185:    */         }
/* 186:237 */         else if (!existeDepreciacionDepreciados)
/* 187:    */         {
/* 188:238 */           depreciacion.setTraEsEditable(true);
/* 189:239 */           activoFijo.setTraEsEditable(false);
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   private void validarBajaActivo(ActivoFijo activoFijo)
/* 196:    */     throws ExcepcionAS2Financiero
/* 197:    */   {}
/* 198:    */   
/* 199:    */   private void contabilizarNIIF(ActivoFijo activoFijo)
/* 200:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 201:    */   {
/* 202:    */     try
/* 203:    */     {
/* 204:281 */       Date fechaContabilizacion = activoFijo.getFechaBaja();
/* 205:    */       
/* 206:283 */       Asiento asiento = new Asiento();
/* 207:284 */       asiento.setIdOrganizacion(activoFijo.getIdOrganizacion());
/* 208:285 */       asiento.setSucursal(AppUtil.getSucursal());
/* 209:286 */       int idTipoAsientoDepreciacion = 0;
/* 210:    */       try
/* 211:    */       {
/* 212:289 */         idTipoAsientoDepreciacion = ParametrosSistema.getTipoAsientoInterfazDepreciacion(activoFijo.getIdOrganizacion()).intValue();
/* 213:290 */         TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(idTipoAsientoDepreciacion));
/* 214:291 */         if (tipoAsiento == null) {
/* 215:292 */           throw new Exception();
/* 216:    */         }
/* 217:    */       }
/* 218:    */       catch (Exception e)
/* 219:    */       {
/* 220:296 */         throw new ExcepcionAS2("msg_info_configuracion", "TipoAsientoDepreciacion");
/* 221:    */       }
/* 222:    */       TipoAsiento tipoAsiento;
/* 223:298 */       asiento.setTipoAsiento(tipoAsiento);
/* 224:299 */       asiento.setEstado(Estado.ELABORADO);
/* 225:300 */       asiento.setIndicadorAutomatico(true);
/* 226:    */       
/* 227:    */ 
/* 228:303 */       String concepto = "";
/* 229:    */       
/* 230:305 */       concepto = activoFijo.getMotivoBajaActivo().getDocumento().getNombre().trim() + " - " + activoFijo.getCodigo() + " - " + activoFijo.getNombre();
/* 231:306 */       asiento.setConcepto(concepto);
/* 232:307 */       asiento.setFecha(fechaContabilizacion);
/* 233:    */       
/* 234:    */ 
/* 235:310 */       Depreciacion depreciacion = this.depreciacionDao.obtenerDepreciacionActivo(activoFijo.getId(), false);
/* 236:311 */       BigDecimal valorDepreciado = depreciacion != null ? depreciacion.getValorDepreciado() : BigDecimal.ZERO;
/* 237:    */       
/* 238:    */ 
/* 239:314 */       BigDecimal valordepreciacionAcumulada = valorDepreciado.add(this.activoFijoDao.getValorDepreciacionTotalAcumulada(activoFijo.getIdActivoFijo(), false));
/* 240:315 */       BigDecimal valorActivoFijo = this.activoFijoDao.getValorActivoFijoNIIF(activoFijo.getIdActivoFijo());
/* 241:316 */       BigDecimal valorMotivoBaja = valorActivoFijo.subtract(valordepreciacionAcumulada);
/* 242:317 */       BigDecimal valorImpuestoDiferido = this.activoFijoDao.getValorImpuestosDiferidosNIIF(activoFijo.getIdActivoFijo());
/* 243:    */       
/* 244:319 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/* 245:    */       
/* 246:321 */       Integer idCuentaOrdenImpuestosRenta = ParametrosSistema.getCuentaOrdenImpuestoRenta(activoFijo.getIdOrganizacion());
/* 247:322 */       if ((idCuentaOrdenImpuestosRenta == null) || (idCuentaOrdenImpuestosRenta.intValue() == 0)) {
/* 248:323 */         throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " CuentaOrdenImpuestoRenta");
/* 249:    */       }
/* 250:325 */       listaDA.add(new DetalleInterfazContable(idCuentaOrdenImpuestosRenta, "", activoFijo.getMotivoBajaActivo().getDocumento().getNombre(), "", valorImpuestoDiferido
/* 251:326 */         .negate()));
/* 252:    */       
/* 253:328 */       Integer idCuentaImpuestosDiferidos = ParametrosSistema.getCuentaImpuestoDiferido(activoFijo.getIdOrganizacion());
/* 254:329 */       if ((idCuentaImpuestosDiferidos == null) || (idCuentaImpuestosDiferidos.intValue() == 0)) {
/* 255:330 */         throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " CuentaOrdenImpuestosDiferidos");
/* 256:    */       }
/* 257:332 */       listaDA.add(new DetalleInterfazContable(idCuentaImpuestosDiferidos, "", activoFijo.getMotivoBajaActivo().getDocumento().getNombre(), "", valorImpuestoDiferido));
/* 258:    */       
/* 259:    */ 
/* 260:335 */       super.generarAsiento(asiento, listaDA, activoFijo.getMotivoBajaActivo().getDocumento());
/* 261:    */       
/* 262:    */ 
/* 263:    */ 
/* 264:339 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(activoFijo.getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 265:    */       
/* 266:    */ 
/* 267:342 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(activoFijo.getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 268:343 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion) {
/* 269:344 */         if (documentoContabilizacion.getProcesoContabilizacion() == ProcesoContabilizacionEnum.DEPRECIACION)
/* 270:    */         {
/* 271:352 */           DetalleInterfazContableProcesoDepreciaciones dicpDepreciacionAcumulada = new DetalleInterfazContableProcesoDepreciaciones(Integer.valueOf(activoFijo.getSucursal().getIdSucursal()), activoFijo.getSucursal().getNombre(), Integer.valueOf(activoFijo.getDepartamento().getIdDepartamento()), activoFijo.getDepartamento().getNombre(), Integer.valueOf(activoFijo.getCategoriaActivo().getIdCategoriaActivo()), activoFijo.getCategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getSubcategoriaActivo().getIdSubcategoriaActivo()), activoFijo.getSubcategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getIdActivoFijo()), activoFijo.getNombre(), concepto, concepto, valordepreciacionAcumulada, activoFijo.getCentroCosto() != null ? Integer.valueOf(activoFijo.getCentroCosto().getIdDimensionContable()) : null, activoFijo.getCentroCosto() != null ? activoFijo.getCentroCosto().getNombre() : null);
/* 272:353 */           List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 273:354 */           lista.add(dicpDepreciacionAcumulada);
/* 274:    */           
/* 275:356 */           asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true));
/* 276:    */         }
/* 277:    */       }
/* 278:361 */       listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(activoFijo.getIdOrganizacion(), DocumentoBase.BAJA_ACTIVO);
/* 279:364 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 280:    */       {
/* 281:365 */         BigDecimal valor = BigDecimal.ZERO;
/* 282:366 */         if (documentoContabilizacion.getProcesoContabilizacion() == ProcesoContabilizacionEnum.ACTIVO_FIJO) {
/* 283:367 */           valor = valorActivoFijo;
/* 284:    */         } else {
/* 285:369 */           valor = valorMotivoBaja;
/* 286:    */         }
/* 287:380 */         DetalleInterfazContableProcesoBajaActivo dicpDepreciacionActivoMotivo = new DetalleInterfazContableProcesoBajaActivo(Integer.valueOf(activoFijo.getSucursal().getIdSucursal()), activoFijo.getSucursal().getNombre(), Integer.valueOf(activoFijo.getDepartamento().getIdDepartamento()), activoFijo.getDepartamento().getNombre(), Integer.valueOf(activoFijo.getCategoriaActivo().getIdCategoriaActivo()), activoFijo.getCategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getSubcategoriaActivo().getIdSubcategoriaActivo()), activoFijo.getSubcategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getIdActivoFijo()), activoFijo.getNombre(), Integer.valueOf(activoFijo.getMotivoBajaActivo().getIdMotivoBajaActivo()), activoFijo.getMotivoBajaActivo().getNombre(), concepto, concepto, valor, activoFijo.getCentroCosto() != null ? Integer.valueOf(activoFijo.getCentroCosto().getIdDimensionContable()) : null, activoFijo.getCentroCosto() != null ? activoFijo.getCentroCosto().getNombre() : null);
/* 288:381 */         List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 289:382 */         lista.add(dicpDepreciacionActivoMotivo);
/* 290:    */         
/* 291:384 */         asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 292:385 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 293:    */       }
/* 294:389 */       this.servicioAsiento.guardar(asiento);
/* 295:    */       
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:395 */       activoFijo.setAsientoBajaActivoNIIF(asiento);
/* 301:    */       
/* 302:397 */       this.activoFijoDao.guardar(activoFijo);
/* 303:    */     }
/* 304:    */     catch (AS2Exception e)
/* 305:    */     {
/* 306:399 */       this.context.setRollbackOnly();
/* 307:400 */       throw e;
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   private void contabilizarFiscal(ActivoFijo activoFijo)
/* 312:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 313:    */   {
/* 314:    */     try
/* 315:    */     {
/* 316:406 */       Date fechaContabilizacion = activoFijo.getFechaBaja();
/* 317:    */       
/* 318:408 */       Asiento asiento = new Asiento();
/* 319:409 */       asiento.setIdOrganizacion(activoFijo.getIdOrganizacion());
/* 320:410 */       asiento.setSucursal(AppUtil.getSucursal());
/* 321:411 */       int idTipoAsientoDepreciacion = 0;
/* 322:    */       try
/* 323:    */       {
/* 324:414 */         idTipoAsientoDepreciacion = ParametrosSistema.getTipoAsientoInterfazDepreciacion(activoFijo.getIdOrganizacion()).intValue();
/* 325:415 */         TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(idTipoAsientoDepreciacion));
/* 326:416 */         if (tipoAsiento == null) {
/* 327:417 */           throw new Exception();
/* 328:    */         }
/* 329:    */       }
/* 330:    */       catch (Exception e)
/* 331:    */       {
/* 332:421 */         throw new ExcepcionAS2("msg_info_configuracion", "TipoAsientoDepreciacion");
/* 333:    */       }
/* 334:    */       TipoAsiento tipoAsiento;
/* 335:423 */       asiento.setTipoAsiento(tipoAsiento);
/* 336:424 */       asiento.setEstado(Estado.ELABORADO);
/* 337:425 */       asiento.setIndicadorAutomatico(true);
/* 338:    */       
/* 339:    */ 
/* 340:428 */       String concepto = "";
/* 341:    */       
/* 342:430 */       concepto = activoFijo.getMotivoBajaActivo().getDocumento().getNombre().trim() + " - " + activoFijo.getCodigo() + " - " + activoFijo.getNombre();
/* 343:431 */       asiento.setConcepto(concepto);
/* 344:432 */       asiento.setFecha(fechaContabilizacion);
/* 345:    */       
/* 346:    */ 
/* 347:435 */       Depreciacion depreciacion = this.depreciacionDao.obtenerDepreciacionActivo(activoFijo.getId(), true);
/* 348:436 */       BigDecimal valorDepreciado = depreciacion != null ? depreciacion.getValorDepreciado() : BigDecimal.ZERO;
/* 349:    */       
/* 350:    */ 
/* 351:439 */       BigDecimal valordepreciacionAcumulada = valorDepreciado.add(this.activoFijoDao.getValorDepreciacionTotalAcumulada(activoFijo.getIdActivoFijo(), true));
/* 352:440 */       BigDecimal valorActivoFijo = activoFijo.getValorActivo();
/* 353:441 */       BigDecimal valorMotivoBaja = valorActivoFijo.subtract(valordepreciacionAcumulada);
/* 354:    */       
/* 355:    */ 
/* 356:    */ 
/* 357:445 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(activoFijo.getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 358:    */       
/* 359:    */ 
/* 360:448 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(activoFijo.getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 361:449 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion) {
/* 362:450 */         if (documentoContabilizacion.getProcesoContabilizacion() == ProcesoContabilizacionEnum.DEPRECIACION)
/* 363:    */         {
/* 364:458 */           DetalleInterfazContableProcesoDepreciaciones dicpDepreciacionAcumulada = new DetalleInterfazContableProcesoDepreciaciones(Integer.valueOf(activoFijo.getSucursal().getIdSucursal()), activoFijo.getSucursal().getNombre(), Integer.valueOf(activoFijo.getDepartamento().getIdDepartamento()), activoFijo.getDepartamento().getNombre(), Integer.valueOf(activoFijo.getCategoriaActivo().getIdCategoriaActivo()), activoFijo.getCategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getSubcategoriaActivo().getIdSubcategoriaActivo()), activoFijo.getSubcategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getIdActivoFijo()), activoFijo.getNombre(), concepto, concepto, valordepreciacionAcumulada, activoFijo.getCentroCosto() != null ? Integer.valueOf(activoFijo.getCentroCosto().getIdDimensionContable()) : null, activoFijo.getCentroCosto() != null ? activoFijo.getCentroCosto().getNombre() : null);
/* 365:459 */           List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 366:460 */           lista.add(dicpDepreciacionAcumulada);
/* 367:    */           
/* 368:462 */           asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true));
/* 369:    */         }
/* 370:    */       }
/* 371:467 */       listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(activoFijo.getIdOrganizacion(), DocumentoBase.BAJA_ACTIVO);
/* 372:470 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 373:    */       {
/* 374:471 */         BigDecimal valor = BigDecimal.ZERO;
/* 375:472 */         if (documentoContabilizacion.getProcesoContabilizacion() == ProcesoContabilizacionEnum.ACTIVO_FIJO) {
/* 376:473 */           valor = valorActivoFijo;
/* 377:    */         } else {
/* 378:475 */           valor = valorMotivoBaja;
/* 379:    */         }
/* 380:486 */         DetalleInterfazContableProcesoBajaActivo dicpDepreciacionActivoMotivo = new DetalleInterfazContableProcesoBajaActivo(Integer.valueOf(activoFijo.getSucursal().getIdSucursal()), activoFijo.getSucursal().getNombre(), Integer.valueOf(activoFijo.getDepartamento().getIdDepartamento()), activoFijo.getDepartamento().getNombre(), Integer.valueOf(activoFijo.getCategoriaActivo().getIdCategoriaActivo()), activoFijo.getCategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getSubcategoriaActivo().getIdSubcategoriaActivo()), activoFijo.getSubcategoriaActivo().getNombre(), Integer.valueOf(activoFijo.getIdActivoFijo()), activoFijo.getNombre(), Integer.valueOf(activoFijo.getMotivoBajaActivo().getIdMotivoBajaActivo()), activoFijo.getMotivoBajaActivo().getNombre(), concepto, concepto, valor, activoFijo.getCentroCosto() != null ? Integer.valueOf(activoFijo.getCentroCosto().getIdDimensionContable()) : null, activoFijo.getCentroCosto() != null ? activoFijo.getCentroCosto().getNombre() : null);
/* 381:487 */         List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 382:488 */         lista.add(dicpDepreciacionActivoMotivo);
/* 383:    */         
/* 384:490 */         asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 385:491 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 386:    */       }
/* 387:495 */       this.servicioAsiento.guardar(asiento);
/* 388:    */       
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:501 */       activoFijo.setAsientoBajaActivoFiscal(asiento);
/* 394:    */       
/* 395:503 */       this.activoFijoDao.guardar(activoFijo);
/* 396:    */     }
/* 397:    */     catch (AS2Exception e)
/* 398:    */     {
/* 399:505 */       this.context.setRollbackOnly();
/* 400:506 */       e.printStackTrace();
/* 401:507 */       throw e;
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void contabilizar(ActivoFijo activoFijo)
/* 406:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 407:    */   {
/* 408:519 */     contabilizarFiscal(activoFijo);
/* 409:520 */     contabilizarNIIF(activoFijo);
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void cargarActivosFijos(InputStream imInputStream, int filaInicial)
/* 413:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 414:    */   {
/* 415:526 */     Map<String, String> filtroOrganizacion = new HashMap();
/* 416:527 */     filtroOrganizacion.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 417:    */     
/* 418:    */ 
/* 419:530 */     HashMap<String, CategoriaActivo> hashMapCategoriaActivo = new HashMap();
/* 420:531 */     HashMap<String, SubcategoriaActivo> hashMapSubcategoriaActivo = new HashMap();
/* 421:532 */     HashMap<String, String> hashMapSubcategoriaActivoCodigo = new HashMap();
/* 422:533 */     for (CategoriaActivo ca : this.servicioCategoriaActivo.obtenerListaCombo("", false, filtroOrganizacion))
/* 423:    */     {
/* 424:534 */       caTmp = this.servicioCategoriaActivo.cargarDetalle(ca.getIdCategoriaActivo());
/* 425:535 */       hashMapCategoriaActivo.put(ca.getCodigo().trim(), caTmp);
/* 426:536 */       for (SubcategoriaActivo sca : caTmp.getListaSubcategoriaActivo()) {
/* 427:537 */         hashMapSubcategoriaActivo.put(caTmp.getCodigo().trim() + "~" + sca.getCodigo().trim(), sca);
/* 428:    */       }
/* 429:    */     }
/* 430:542 */     Object hashMapCentroCosto = new HashMap();
/* 431:543 */     HashMap<String, String> filtroDimension = new HashMap();
/* 432:544 */     filtroDimension.put("numero", "1");
/* 433:545 */     filtroDimension.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 434:546 */     for (CategoriaActivo caTmp = this.servicioDimensionContable.obtenerListaCombo("", false, filtroDimension).iterator(); caTmp.hasNext();)
/* 435:    */     {
/* 436:546 */       dc = (DimensionContable)caTmp.next();
/* 437:547 */       ((HashMap)hashMapCentroCosto).put(((DimensionContable)dc).getCodigo().trim(), dc);
/* 438:    */     }
/* 439:550 */     filtroOrganizacion.put("indicadorMovimiento", "true");
/* 440:    */     
/* 441:    */ 
/* 442:553 */     HashMap<String, ActivoFijo> hashMapActivoFijo = new HashMap();
/* 443:554 */     for (Object dc = obtenerListaCombo("", false, null).iterator(); ((Iterator)dc).hasNext();)
/* 444:    */     {
/* 445:554 */       af = (ActivoFijo)((Iterator)dc).next();
/* 446:555 */       hashMapActivoFijo.put(af.getCodigo().trim(), af);
/* 447:    */     }
/* 448:559 */     Object hmSucursal = new HashMap();
/* 449:560 */     for (ActivoFijo af = this.servicioSucursal.obtenerListaCombo("", false, null).iterator(); af.hasNext();)
/* 450:    */     {
/* 451:560 */       sucursal = (Sucursal)af.next();
/* 452:561 */       ((HashMap)hmSucursal).put(sucursal.getCodigo().trim(), sucursal);
/* 453:    */     }
/* 454:    */     Sucursal sucursal;
/* 455:565 */     HashMap<String, Departamento> hmDepartamento = new HashMap();
/* 456:566 */     for (Departamento departamento : this.servicioDepartamento.obtenerListaCombo("", false, null)) {
/* 457:567 */       hmDepartamento.put(departamento.getCodigo().trim(), departamento);
/* 458:    */     }
/* 459:570 */     int filaActual = filaInicial;
/* 460:571 */     int columnaActual = 0;
/* 461:572 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 462:    */     try
/* 463:    */     {
/* 464:574 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 465:575 */       for (HSSFCell[] fila : datos)
/* 466:    */       {
/* 467:577 */         filaErronea = fila;
/* 468:578 */         filaActual++;
/* 469:    */         
/* 470:580 */         String codigoActivoFijo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, Integer.valueOf(1), Integer.valueOf(10));
/* 471:581 */         String codigoBarras = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 1, false, Integer.valueOf(0), Integer.valueOf(20));
/* 472:582 */         String numeroSerie = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 2, false, Integer.valueOf(0), Integer.valueOf(20));
/* 473:583 */         String numeroParte = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 3, false, Integer.valueOf(0), Integer.valueOf(20));
/* 474:584 */         String nombreActivo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 4, true, Integer.valueOf(2), Integer.valueOf(50));
/* 475:585 */         String codigoCategoriaActivoFijo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 5, true, 
/* 476:586 */           Integer.valueOf(2), Integer.valueOf(10));
/* 477:587 */         String nombreCategoriaActivoFijo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 6, true, 
/* 478:588 */           Integer.valueOf(2), Integer.valueOf(50));
/* 479:589 */         Date fechaCompra = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 7, true, Integer.valueOf(0), Integer.valueOf(0));
/* 480:590 */         String numeroFactura = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 8, true, Integer.valueOf(1), Integer.valueOf(20));
/* 481:591 */         BigDecimal valorActivo = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 9, true, Integer.valueOf(0), 
/* 482:592 */           Integer.valueOf(0));
/* 483:593 */         BigDecimal valorCompra = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 10, true, Integer.valueOf(0), 
/* 484:594 */           Integer.valueOf(0));
/* 485:595 */         BigDecimal valorAdicional = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 11, true, 
/* 486:596 */           Integer.valueOf(0), Integer.valueOf(0));
/* 487:    */         
/* 488:598 */         String cadenaIndicadorDepreciar = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 12, true, 
/* 489:599 */           Integer.valueOf(0), Integer.valueOf(2));
/* 490:600 */         boolean indicadorDepreciar = cadenaIndicadorDepreciar.equalsIgnoreCase("SI");
/* 491:    */         
/* 492:602 */         Date fechaInicioDepreciacion = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 13, true, Integer.valueOf(0), 
/* 493:603 */           Integer.valueOf(0));
/* 494:    */         
/* 495:605 */         BigDecimal valorDepreciadoNIIF = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 14, true, 
/* 496:606 */           Integer.valueOf(0), Integer.valueOf(0));
/* 497:607 */         BigDecimal valorResidualNIIF = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 15, true, 
/* 498:608 */           Integer.valueOf(0), Integer.valueOf(0));
/* 499:    */         
/* 500:610 */         BigDecimal a = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 16, false, Integer.valueOf(0), Integer.valueOf(0));
/* 501:611 */         int vidaUtilNIIF = a.intValue();
/* 502:    */         
/* 503:613 */         BigDecimal valorDepreciadoFiscal = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 17, false, 
/* 504:614 */           Integer.valueOf(0), Integer.valueOf(0));
/* 505:615 */         BigDecimal valorResidualFiscal = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 18, false, 
/* 506:616 */           Integer.valueOf(0), Integer.valueOf(0));
/* 507:617 */         BigDecimal b = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 19, false, Integer.valueOf(0), Integer.valueOf(0));
/* 508:    */         
/* 509:619 */         int vidaUtilFiscal = b.intValue();
/* 510:620 */         String codigoSubcategoriaActivoFijo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 20, true, 
/* 511:621 */           Integer.valueOf(2), Integer.valueOf(10));
/* 512:622 */         String nombreSubcategoriaActivoFijo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 21, true, 
/* 513:623 */           Integer.valueOf(2), Integer.valueOf(50));
/* 514:624 */         String codigoSucursal = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 22, true, Integer.valueOf(2), Integer.valueOf(50));
/* 515:625 */         Sucursal sucursal = (Sucursal)((HashMap)hmSucursal).get(codigoSucursal);
/* 516:626 */         if (sucursal == null) {
/* 517:627 */           throw new AS2Exception("com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioActivoFijoImpl.NO_EXISTE_SUCURSAL", new String[] { codigoSucursal, Integer.toString(filaActual), Integer.toString(columnaActual) });
/* 518:    */         }
/* 519:629 */         String codigoDepartamento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 23, true, Integer.valueOf(2), 
/* 520:630 */           Integer.valueOf(50));
/* 521:631 */         Departamento departamento = (Departamento)hmDepartamento.get(codigoDepartamento);
/* 522:632 */         if (departamento == null) {
/* 523:633 */           throw new AS2Exception("com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioActivoFijoImpl.NO_EXISTE_DEPARTAMENTO", new String[] { codigoDepartamento, Integer.toString(filaActual), Integer.toString(columnaActual) });
/* 524:    */         }
/* 525:637 */         String centroCosto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 24, true, Integer.valueOf(1), Integer.valueOf(20));
/* 526:638 */         DimensionContable dimensionContable = (DimensionContable)((HashMap)hashMapCentroCosto).get(centroCosto);
/* 527:639 */         if (dimensionContable == null) {
/* 528:640 */           throw new AS2Exception("com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioActivoFijoImpl.NO_EXISTE_DIMENSION_CONTABLE", new String[] { centroCosto, Integer.toString(filaActual), Integer.toString(columnaActual) });
/* 529:    */         }
/* 530:645 */         int vidaUtilFiscalInformativa = ((BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 25, false, Integer.valueOf(0), Integer.valueOf(0))).intValue();
/* 531:646 */         int vidaUtilNIIFInformativa = ((BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 26, false, Integer.valueOf(0), Integer.valueOf(0))).intValue();
/* 532:    */         
/* 533:    */ 
/* 534:    */ 
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:    */ 
/* 539:654 */         CategoriaActivo categoriaActivo = (CategoriaActivo)hashMapCategoriaActivo.get(codigoCategoriaActivoFijo);
/* 540:655 */         if (categoriaActivo == null)
/* 541:    */         {
/* 542:656 */           categoriaActivo = new CategoriaActivo();
/* 543:657 */           categoriaActivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 544:658 */           categoriaActivo.setIdSucursal(AppUtil.getSucursal().getId());
/* 545:659 */           categoriaActivo.setCodigo(codigoCategoriaActivoFijo);
/* 546:660 */           categoriaActivo.setNombre(nombreCategoriaActivoFijo);
/* 547:661 */           categoriaActivo.setDescripcion("");
/* 548:662 */           categoriaActivo.setActivo(true);
/* 549:663 */           this.servicioCategoriaActivo.guardar(categoriaActivo);
/* 550:664 */           hashMapCategoriaActivo.put(codigoCategoriaActivoFijo, categoriaActivo);
/* 551:    */         }
/* 552:667 */         SubcategoriaActivo subcategoriaActivo = (SubcategoriaActivo)hashMapSubcategoriaActivo.get(codigoCategoriaActivoFijo + "~" + codigoSubcategoriaActivoFijo);
/* 553:668 */         if (subcategoriaActivo == null)
/* 554:    */         {
/* 555:669 */           subcategoriaActivo = new SubcategoriaActivo();
/* 556:670 */           subcategoriaActivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 557:671 */           subcategoriaActivo.setIdSucursal(AppUtil.getSucursal().getId());
/* 558:672 */           subcategoriaActivo.setCodigo(codigoSubcategoriaActivoFijo);
/* 559:673 */           subcategoriaActivo.setNombre(nombreSubcategoriaActivoFijo);
/* 560:674 */           subcategoriaActivo.setCategoriaActivo(categoriaActivo);
/* 561:675 */           categoriaActivo.getListaSubcategoriaActivo().add(subcategoriaActivo);
/* 562:    */           
/* 563:677 */           String codigoCategoria = (String)hashMapSubcategoriaActivoCodigo.get(codigoSubcategoriaActivoFijo);
/* 564:678 */           if ((codigoCategoria != null) && (codigoCategoria.equals(codigoCategoriaActivoFijo))) {
/* 565:679 */             throw new ExcepcionAS2Financiero("msg_error_categoria_subcategoria_activo_erronea", " " + codigoActivoFijo + "/" + codigoSubcategoriaActivoFijo + "/" + codigoCategoria);
/* 566:    */           }
/* 567:682 */           hashMapSubcategoriaActivoCodigo.put(codigoSubcategoriaActivoFijo, codigoCategoriaActivoFijo);
/* 568:683 */           this.servicioCategoriaActivo.guardar(categoriaActivo);
/* 569:684 */           hashMapSubcategoriaActivo.put(codigoCategoriaActivoFijo + "~" + codigoSubcategoriaActivoFijo, subcategoriaActivo);
/* 570:    */         }
/* 571:690 */         System.out.println("el activo es codigoActivoFijo - - - - - - - - - - - - - - " + codigoActivoFijo);
/* 572:691 */         if (!hashMapActivoFijo.containsKey(codigoActivoFijo))
/* 573:    */         {
/* 574:693 */           ActivoFijo activoFijo = new ActivoFijo();
/* 575:694 */           activoFijo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 576:695 */           activoFijo.setSucursal(sucursal);
/* 577:696 */           activoFijo.setDepartamento(departamento);
/* 578:697 */           activoFijo.setCodigo(codigoActivoFijo);
/* 579:698 */           activoFijo.setCodigoBarras(codigoBarras);
/* 580:699 */           activoFijo.setNumeroSerie(numeroSerie == null ? "" : numeroSerie);
/* 581:700 */           activoFijo.setNumeroParte(numeroParte == null ? "" : numeroParte);
/* 582:701 */           activoFijo.setNombre(nombreActivo);
/* 583:702 */           activoFijo.setCategoriaActivo(categoriaActivo);
/* 584:703 */           activoFijo.setSubcategoriaActivo(subcategoriaActivo);
/* 585:704 */           activoFijo.setFechaFacturaProveedor(fechaCompra);
/* 586:705 */           activoFijo.setNumeroFacturaProveedor(numeroFactura);
/* 587:706 */           activoFijo.setValorActivo(valorActivo);
/* 588:707 */           activoFijo.setValorCompraRelacionada(valorCompra);
/* 589:708 */           activoFijo.setValorAdicional(valorAdicional);
/* 590:709 */           if (indicadorDepreciar == true) {
/* 591:710 */             activoFijo.setIndicadorDepreciar(true);
/* 592:    */           } else {
/* 593:712 */             activoFijo.setIndicadorDepreciar(false);
/* 594:    */           }
/* 595:714 */           activoFijo.setActivo(true);
/* 596:715 */           activoFijo.setValorDepreciado(BigDecimal.ZERO);
/* 597:716 */           activoFijo.setCentroCosto(dimensionContable);
/* 598:    */           
/* 599:718 */           BigDecimal valorADepreciarFiscal = valorActivo.subtract(valorDepreciadoFiscal).subtract(valorResidualFiscal);
/* 600:720 */           if (valorADepreciarFiscal.compareTo(BigDecimal.ZERO) > 0)
/* 601:    */           {
/* 602:721 */             Depreciacion depreciacionFiscal = new Depreciacion();
/* 603:722 */             depreciacionFiscal.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 604:723 */             depreciacionFiscal.setIdSucursal(activoFijo.getSucursal().getId());
/* 605:724 */             depreciacionFiscal.setEstado(Estado.ELABORADO);
/* 606:725 */             depreciacionFiscal.setFechaInicioDepreciacion(fechaInicioDepreciacion);
/* 607:726 */             depreciacionFiscal.setValorActivo(valorActivo);
/* 608:727 */             depreciacionFiscal.setValorDepreciado(valorDepreciadoFiscal);
/* 609:728 */             depreciacionFiscal.setValorADepreciar(valorADepreciarFiscal);
/* 610:729 */             depreciacionFiscal.setValorResidual(valorResidualFiscal);
/* 611:730 */             depreciacionFiscal.setActivo(true);
/* 612:731 */             depreciacionFiscal.setIndicadorDepreciacionFiscal(true);
/* 613:732 */             depreciacionFiscal.setActivoFijo(activoFijo);
/* 614:733 */             depreciacionFiscal.setVidaUtil(vidaUtilFiscal);
/* 615:734 */             depreciacionFiscal.setVidaUtilInformativa(vidaUtilFiscalInformativa);
/* 616:735 */             activoFijo.getListaDepreciacion().add(depreciacionFiscal);
/* 617:736 */             if (activoFijo.isIndicadorDepreciar()) {
/* 618:737 */               this.servicioDepreciacion.generarListaDepreciacion(depreciacionFiscal, vidaUtilFiscal);
/* 619:    */             }
/* 620:    */           }
/* 621:741 */           BigDecimal valorADepreciarNIIF = valorActivo.subtract(valorDepreciadoNIIF).subtract(valorResidualNIIF).add(valorCompra).add(valorAdicional);
/* 622:743 */           if (valorADepreciarNIIF.compareTo(BigDecimal.ZERO) > 0)
/* 623:    */           {
/* 624:744 */             Depreciacion depreciacionNIIF = new Depreciacion();
/* 625:745 */             depreciacionNIIF.setIdOrganizacion(activoFijo.getIdOrganizacion());
/* 626:746 */             depreciacionNIIF.setIdSucursal(activoFijo.getSucursal().getId());
/* 627:747 */             depreciacionNIIF.setEstado(Estado.ELABORADO);
/* 628:748 */             depreciacionNIIF.setFechaInicioDepreciacion(fechaInicioDepreciacion);
/* 629:749 */             depreciacionNIIF.setValorActivo(valorActivo);
/* 630:750 */             depreciacionNIIF.setValorDepreciado(valorDepreciadoNIIF);
/* 631:751 */             depreciacionNIIF.setActivo(true);
/* 632:752 */             depreciacionNIIF.setValorADepreciar(valorActivo
/* 633:753 */               .subtract(valorDepreciadoNIIF).subtract(valorResidualNIIF).add(valorCompra).add(valorAdicional));
/* 634:754 */             depreciacionNIIF.setValorResidual(valorResidualNIIF);
/* 635:755 */             depreciacionNIIF.setActivoFijo(activoFijo);
/* 636:756 */             depreciacionNIIF.setIndicadorDepreciacionFiscal(false);
/* 637:757 */             depreciacionNIIF.setActivo(true);
/* 638:758 */             depreciacionNIIF.setVidaUtil(vidaUtilNIIF);
/* 639:759 */             depreciacionNIIF.setVidaUtilInformativa(vidaUtilNIIFInformativa);
/* 640:760 */             activoFijo.getListaDepreciacion().add(depreciacionNIIF);
/* 641:761 */             if (activoFijo.isIndicadorDepreciar()) {
/* 642:762 */               this.servicioDepreciacion.generarListaDepreciacion(depreciacionNIIF, vidaUtilNIIF);
/* 643:    */             }
/* 644:    */           }
/* 645:765 */           guardar(activoFijo);
/* 646:766 */           hashMapActivoFijo.put(codigoActivoFijo, activoFijo);
/* 647:    */         }
/* 648:    */       }
/* 649:    */     }
/* 650:    */     catch (AS2Exception e)
/* 651:    */     {
/* 652:773 */       throw e;
/* 653:    */     }
/* 654:    */     catch (IllegalStateException e)
/* 655:    */     {
/* 656:776 */       LOG.info("Error al migrar ", e);
/* 657:777 */       this.context.setRollbackOnly();
/* 658:    */       
/* 659:779 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 660:    */     }
/* 661:    */     catch (ExcepcionAS2Financiero e)
/* 662:    */     {
/* 663:781 */       this.context.setRollbackOnly();
/* 664:782 */       throw e;
/* 665:    */     }
/* 666:    */     catch (ExcepcionAS2 e)
/* 667:    */     {
/* 668:784 */       this.context.setRollbackOnly();
/* 669:785 */       throw e;
/* 670:    */     }
/* 671:    */     catch (Exception e)
/* 672:    */     {
/* 673:787 */       this.context.setRollbackOnly();
/* 674:788 */       throw new ExcepcionAS2(e);
/* 675:    */     }
/* 676:    */   }
/* 677:    */   
/* 678:    */   public List<ActivoFijo> obtenerListaComboParaMantenimiento(String sortField, boolean sortOrder, Map<String, String> filters)
/* 679:    */   {
/* 680:798 */     return this.activoFijoDao.obtenerListaComboParaMantenimiento(sortField, sortOrder, filters);
/* 681:    */   }
/* 682:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioActivoFijoImpl
 * JD-Core Version:    0.7.0.1
 */