/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.dao.CajaChicaDao;
/*   6:    */ import com.asinfo.as2.dao.CompraCajaChicaDao;
/*   7:    */ import com.asinfo.as2.dao.DetalleCompraCajaChicaCentroCostoDao;
/*   8:    */ import com.asinfo.as2.dao.DetalleCompraCajaChicaDao;
/*   9:    */ import com.asinfo.as2.dao.GenericoDao;
/*  10:    */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*  11:    */ import com.asinfo.as2.dao.sri.DetalleFacturaProveedorSRIDao;
/*  12:    */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  14:    */ import com.asinfo.as2.entities.CajaChica;
/*  15:    */ import com.asinfo.as2.entities.CompraCajaChica;
/*  16:    */ import com.asinfo.as2.entities.CuentaContable;
/*  17:    */ import com.asinfo.as2.entities.DetalleCompraCajaChica;
/*  18:    */ import com.asinfo.as2.entities.DetalleCompraCajaChicaCentroCosto;
/*  19:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  20:    */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*  21:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  22:    */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*  23:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  24:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  28:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCajaChica;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCompraCajaChica;
/*  30:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  31:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  32:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  33:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  34:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  35:    */ import java.math.BigDecimal;
/*  36:    */ import java.util.Date;
/*  37:    */ import java.util.HashMap;
/*  38:    */ import java.util.Iterator;
/*  39:    */ import java.util.List;
/*  40:    */ import java.util.Map;
/*  41:    */ import javax.ejb.EJB;
/*  42:    */ import javax.ejb.SessionContext;
/*  43:    */ import javax.ejb.Stateless;
/*  44:    */ import org.apache.log4j.Logger;
/*  45:    */ 
/*  46:    */ @Stateless
/*  47:    */ public class ServicioCompraCajaChicaImpl
/*  48:    */   extends AbstractServicioAS2Financiero
/*  49:    */   implements ServicioCompraCajaChica
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = -2762209274145119084L;
/*  52:    */   @EJB
/*  53:    */   private CompraCajaChicaDao compraCajaChicaDao;
/*  54:    */   @EJB
/*  55:    */   private CajaChicaDao cajaChicaDao;
/*  56:    */   @EJB
/*  57:    */   private DetalleCompraCajaChicaDao detalleCompraCajaChicaDao;
/*  58:    */   @EJB
/*  59:    */   private DetalleCompraCajaChicaCentroCostoDao detalleCompraCajaChicaCentroCostoDao;
/*  60:    */   @EJB
/*  61:    */   private ServicioPeriodo servicioPeriodo;
/*  62:    */   @EJB
/*  63:    */   private ServicioCajaChica servicioCajaChica;
/*  64:    */   @EJB
/*  65:    */   private FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  66:    */   @EJB
/*  67:    */   private transient ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioEmpresa servicioEmpresa;
/*  70:    */   @EJB
/*  71:    */   private transient GenericoDao<ReembolsoGastos> reembolsoGastosDao;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  74:    */   @EJB
/*  75:    */   private transient DetalleFacturaProveedorSRIDao detalleFacturaProveedorSRIDao;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioFacturaProveedorSRI servicioFacturaProveedorSri;
/*  78:    */   
/*  79:    */   public void guardar(CompraCajaChica compraCajaChica)
/*  80:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  81:    */   {
/*  82:103 */     validar(compraCajaChica);
/*  83:105 */     if (compraCajaChica.getId() > 0) {
/*  84:106 */       actualizarSaldoCajaChica(compraCajaChica.getId(), true, compraCajaChica.getCajaChica());
/*  85:    */     }
/*  86:109 */     if ((compraCajaChica.isIndicadorFactura()) && (compraCajaChica.getFacturaProveedorSRI() != null))
/*  87:    */     {
/*  88:111 */       documentoReferencia = compraCajaChica.getFacturaProveedorSRI().getEstablecimiento() + "-" + compraCajaChica.getFacturaProveedorSRI().getPuntoEmision() + "-" + compraCajaChica.getFacturaProveedorSRI().getNumero();
/*  89:112 */       compraCajaChica.setDocumentoReferencia(documentoReferencia);
/*  90:    */     }
/*  91:115 */     for (String documentoReferencia = compraCajaChica.getListaDetalleCompraCajaChica().iterator(); documentoReferencia.hasNext();)
/*  92:    */     {
/*  93:115 */       detalleCompraCajaChica = (DetalleCompraCajaChica)documentoReferencia.next();
/*  94:117 */       for (DetalleCompraCajaChicaCentroCosto detalleCompraCajaChicaCentroCosto : detalleCompraCajaChica
/*  95:118 */         .getListaDetalleCompraCajaChicaCentroCosto()) {
/*  96:119 */         this.detalleCompraCajaChicaCentroCostoDao.guardar(detalleCompraCajaChicaCentroCosto);
/*  97:    */       }
/*  98:122 */       this.detalleCompraCajaChicaDao.guardar(detalleCompraCajaChica);
/*  99:    */     }
/* 100:    */     DetalleCompraCajaChica detalleCompraCajaChica;
/* 101:124 */     if (compraCajaChica.isIndicadorFactura())
/* 102:    */     {
/* 103:125 */       DireccionEmpresa direccionEmpresa = this.servicioEmpresa.buscarDireccionEmpresaPorId(compraCajaChica.getDireccionEmpresa().getId());
/* 104:126 */       compraCajaChica.getFacturaProveedorSRI().setDireccionProveedor(direccionEmpresa.getDireccionCompleta());
/* 105:127 */       compraCajaChica.getFacturaProveedorSRI().setTelefonoProveedor(direccionEmpresa.getTelefono1());
/* 106:    */       
/* 107:129 */       this.facturaProveedorSRIDao.guardar(compraCajaChica.getFacturaProveedorSRI());
/* 108:131 */       if ((compraCajaChica.getFacturaProveedorSRI() != null) && (compraCajaChica.getFacturaProveedorSRI().isIndicadorReembolso())) {
/* 109:132 */         for (DetalleFacturaProveedorSRI dfpsri : compraCajaChica.getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRINoEliminados()) {
/* 110:133 */           this.detalleFacturaProveedorSRIDao.guardar(dfpsri);
/* 111:    */         }
/* 112:    */       }
/* 113:137 */       for (ReembolsoGastos rg : compraCajaChica.getFacturaProveedorSRI().getListaReembolsoGastos())
/* 114:    */       {
/* 115:138 */         if (!compraCajaChica.getFacturaProveedorSRI().isIndicadorReembolso()) {
/* 116:139 */           rg.setEliminado(true);
/* 117:    */         }
/* 118:141 */         this.reembolsoGastosDao.guardar(rg);
/* 119:    */       }
/* 120:    */     }
/* 121:    */     else
/* 122:    */     {
/* 123:144 */       compraCajaChica.setDireccionEmpresa(null);
/* 124:145 */       compraCajaChica.setFacturaProveedorSRI(null);
/* 125:    */     }
/* 126:147 */     this.compraCajaChicaDao.guardar(compraCajaChica);
/* 127:148 */     actualizarSaldoCajaChica(compraCajaChica.getId(), false, compraCajaChica.getCajaChica());
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void actualizarSaldoCajaChica(int idCompraCajaChica, boolean indicadorReverso, CajaChica cajaChica)
/* 131:    */     throws ExcepcionAS2Financiero
/* 132:    */   {
/* 133:161 */     CompraCajaChica compraCajaChica = cargarDetalle(idCompraCajaChica);
/* 134:162 */     if (compraCajaChica != null)
/* 135:    */     {
/* 136:163 */       BigDecimal valorCompraCajaChica = compraCajaChica.getValor().subtract(compraCajaChica.getDescuentoImpuesto());
/* 137:164 */       if (indicadorReverso) {
/* 138:165 */         valorCompraCajaChica = valorCompraCajaChica.negate();
/* 139:    */       }
/* 140:168 */       compraCajaChica.getCajaChica().setValor(valorCompraCajaChica.add(this.servicioFacturaProveedorSri.valorAcumuladoCajaChica(compraCajaChica, cajaChica)));
/* 141:169 */       this.cajaChicaDao.guardar(cajaChica);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void eliminar(CompraCajaChica compraCajaChica)
/* 146:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, ExcepcionAS2Compras
/* 147:    */   {
/* 148:    */     try
/* 149:    */     {
/* 150:184 */       CompraCajaChica ccc = cargarDetalle(compraCajaChica.getId());
/* 151:185 */       CajaChica cc = ccc.getCajaChica();
/* 152:    */       
/* 153:187 */       esEditable(ccc);
/* 154:189 */       for (DetalleCompraCajaChica dccc : ccc.getListaDetalleCompraCajaChica()) {
/* 155:190 */         this.detalleCompraCajaChicaDao.eliminar(dccc);
/* 156:    */       }
/* 157:193 */       if (ccc.getFacturaProveedorSRI() != null)
/* 158:    */       {
/* 159:195 */         this.comprobanteElectronicoPendienteSRIDao.eliminarComprobanteElectronicoPendienteSRI(null, ccc.getFacturaProveedorSRI(), null);
/* 160:    */         
/* 161:197 */         this.facturaProveedorSRIDao.eliminarFacturaProveedorSRI(Integer.valueOf(ccc.getFacturaProveedorSRI().getId()));
/* 162:    */       }
/* 163:201 */       this.compraCajaChicaDao.eliminar(ccc);
/* 164:    */       
/* 165:    */ 
/* 166:204 */       BigDecimal valorAcumulado = this.servicioFacturaProveedorSri.valorAcumuladoCajaChica(null, cc);
/* 167:205 */       cc.setValor(valorAcumulado);
/* 168:206 */       this.cajaChicaDao.guardar(cc);
/* 169:    */     }
/* 170:    */     catch (ExcepcionAS2Financiero e)
/* 171:    */     {
/* 172:211 */       this.context.setRollbackOnly();
/* 173:212 */       throw e;
/* 174:    */     }
/* 175:    */     catch (ExcepcionAS2Compras e)
/* 176:    */     {
/* 177:214 */       this.context.setRollbackOnly();
/* 178:215 */       throw new ExcepcionAS2(e.getCodigoExcepcion(), e);
/* 179:    */     }
/* 180:    */     catch (Exception e)
/* 181:    */     {
/* 182:217 */       this.context.setRollbackOnly();
/* 183:218 */       LOG.error(e);
/* 184:219 */       throw new ExcepcionAS2(e);
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public CompraCajaChica buscarPorId(Integer id)
/* 189:    */   {
/* 190:231 */     return (CompraCajaChica)this.compraCajaChicaDao.buscarPorId(id);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public CompraCajaChica cargarDetalle(int idCompraCajaChica)
/* 194:    */   {
/* 195:241 */     CompraCajaChica compraCajaChica = this.compraCajaChicaDao.cargarDetalle(idCompraCajaChica);
/* 196:242 */     compraCajaChica.setCajaChica(this.cajaChicaDao.cargarDetalle(compraCajaChica.getCajaChica().getIdCajaChica()));
/* 197:243 */     return compraCajaChica;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List<CompraCajaChica> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 201:    */   {
/* 202:255 */     return this.compraCajaChicaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public List<CompraCajaChica> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 206:    */   {
/* 207:266 */     return this.compraCajaChicaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void esEditable(CompraCajaChica compraCajaChica)
/* 211:    */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero
/* 212:    */   {
/* 213:277 */     this.servicioPeriodo.buscarPorFecha(compraCajaChica.getFecha(), compraCajaChica.getIdOrganizacion(), DocumentoBase.CAJA_CHICA);
/* 214:279 */     if (compraCajaChica.getEstado() == Estado.ANULADO) {
/* 215:281 */       throw new ExcepcionAS2Compras("msg_error_editar");
/* 216:    */     }
/* 217:283 */     if (compraCajaChica.getCajaChica().getEstado() == Estado.CONTABILIZADO) {
/* 218:286 */       if ((compraCajaChica.getFacturaProveedorSRI() != null) && (compraCajaChica.getFacturaProveedorSRI().isIndicadorReembolso())) {
/* 219:287 */         compraCajaChica.setSoloLectura(true);
/* 220:    */       } else {
/* 221:289 */         throw new ExcepcionAS2Compras("msg_error_editar");
/* 222:    */       }
/* 223:    */     }
/* 224:294 */     if ((compraCajaChica.getFacturaProveedorSRI() != null) && 
/* 225:295 */       (!compraCajaChica.getFacturaProveedorSRI().getAutorizacionRetencion().equals("0000000000"))) {
/* 226:296 */       throw new ExcepcionAS2Compras("msgs_error_existe_retencion_factura");
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public CompraCajaChica cargarInformacionSRI(Integer idCompraCajaChica)
/* 231:    */     throws ExcepcionAS2Compras
/* 232:    */   {
/* 233:308 */     return this.compraCajaChicaDao.cargarInformacionSRI(idCompraCajaChica.intValue());
/* 234:    */   }
/* 235:    */   
/* 236:    */   private void validar(CompraCajaChica compraCajaChica)
/* 237:    */     throws ExcepcionAS2, AS2Exception
/* 238:    */   {
/* 239:    */     BigDecimal descuento;
/* 240:315 */     if (compraCajaChica.getFacturaProveedorSRI() != null)
/* 241:    */     {
/* 242:316 */       descuento = compraCajaChica.getDescuentoImpuesto();
/* 243:317 */       BigDecimal iva = compraCajaChica.getFacturaProveedorSRI().getMontoIva();
/* 244:318 */       if (descuento.compareTo(iva) > 0) {
/* 245:319 */         throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioCompraCajaChicaImpl.DESCUENTO_NO_MAYOR_A_MONTO_IVA", new String[] { "" });
/* 246:    */       }
/* 247:    */     }
/* 248:323 */     if ((compraCajaChica.getFacturaProveedorSRI() != null) && (compraCajaChica.getFacturaProveedorSRI().isIndicadorReembolso())) {
/* 249:324 */       for (descuento = compraCajaChica.getFacturaProveedorSRI().getListaReembolsoGastos().iterator(); descuento.hasNext();)
/* 250:    */       {
/* 251:324 */         rg = (ReembolsoGastos)descuento.next();
/* 252:325 */         if ((!rg.isEliminado()) && 
/* 253:326 */           (FuncionesUtiles.compararFechas(compraCajaChica.getFacturaProveedorSRI().getFechaEmision(), rg.getFechaEmision()))) {
/* 254:328 */           throw new AS2Exception("msg_error_fecha_fuera_rango", new String[] {"Para el reembolso menor o igual " + FuncionesUtiles.dateToString(compraCajaChica.getFacturaProveedorSRI().getFechaEmision()) });
/* 255:    */         }
/* 256:    */       }
/* 257:    */     }
/* 258:    */     ReembolsoGastos rg;
/* 259:334 */     CajaChica cajaChica = this.servicioCajaChica.buscarPorId(Integer.valueOf(compraCajaChica.getCajaChica().getIdCajaChica()));
/* 260:335 */     compraCajaChica.setCajaChica(cajaChica);
/* 261:337 */     if ((compraCajaChica.getFecha().before(cajaChica.getFechaDesde())) || (compraCajaChica.getFecha().after(cajaChica.getFechaHasta()))) {
/* 262:338 */       throw new ExcepcionAS2Financiero("msg_error_fecha_intervalo");
/* 263:    */     }
/* 264:341 */     for (DetalleCompraCajaChica detalleCompraCajaChica : compraCajaChica.getListaDetalleCompraCajaChica()) {
/* 265:342 */       if (!detalleCompraCajaChica.isEliminado())
/* 266:    */       {
/* 267:345 */         if ((detalleCompraCajaChica.getCuentaContable().isIndicadorValidarDimension1()) && 
/* 268:346 */           (detalleCompraCajaChica.getDimensionContable1() == null))
/* 269:    */         {
/* 270:348 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalleCompraCajaChica.getCuentaContable().getCodigo(), detalleCompraCajaChica.getCuentaContable().getNombre(), "1" });
/* 271:349 */           throw new ExcepcionAS2(exception.getMensaje());
/* 272:    */         }
/* 273:351 */         if ((detalleCompraCajaChica.getCuentaContable().isIndicadorValidarDimension2()) && 
/* 274:352 */           (detalleCompraCajaChica.getDimensionContable2() == null))
/* 275:    */         {
/* 276:354 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalleCompraCajaChica.getCuentaContable().getCodigo(), detalleCompraCajaChica.getCuentaContable().getNombre(), "2" });
/* 277:355 */           throw new ExcepcionAS2(exception.getMensaje());
/* 278:    */         }
/* 279:357 */         if ((detalleCompraCajaChica.getCuentaContable().isIndicadorValidarDimension3()) && 
/* 280:358 */           (detalleCompraCajaChica.getDimensionContable3() == null))
/* 281:    */         {
/* 282:360 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalleCompraCajaChica.getCuentaContable().getCodigo(), detalleCompraCajaChica.getCuentaContable().getNombre(), "3" });
/* 283:361 */           throw new ExcepcionAS2(exception.getMensaje());
/* 284:    */         }
/* 285:363 */         if ((detalleCompraCajaChica.getCuentaContable().isIndicadorValidarDimension4()) && 
/* 286:364 */           (detalleCompraCajaChica.getDimensionContable4() == null))
/* 287:    */         {
/* 288:366 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalleCompraCajaChica.getCuentaContable().getCodigo(), detalleCompraCajaChica.getCuentaContable().getNombre(), "4" });
/* 289:367 */           throw new ExcepcionAS2(exception.getMensaje());
/* 290:    */         }
/* 291:369 */         if ((detalleCompraCajaChica.getCuentaContable().isIndicadorValidarDimension5()) && 
/* 292:370 */           (detalleCompraCajaChica.getDimensionContable5() == null))
/* 293:    */         {
/* 294:372 */           AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalleCompraCajaChica.getCuentaContable().getCodigo(), detalleCompraCajaChica.getCuentaContable().getNombre(), "5" });
/* 295:373 */           throw new ExcepcionAS2(exception.getMensaje());
/* 296:    */         }
/* 297:    */       }
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List getListaReporteCompraCajaChica(Date fechaDesde, Date fechaHasta, String numeroFacturaDesde, String numeroFacturaHasta, int idCajaChica, int tipoCompra, int idOrganizacion, boolean fechaContabilizacion)
/* 302:    */     throws ExcepcionAS2Ventas
/* 303:    */   {
/* 304:391 */     return this.compraCajaChicaDao.getListaReporteCompraCajaChica(fechaDesde, fechaHasta, numeroFacturaDesde, numeroFacturaHasta, idCajaChica, tipoCompra, idOrganizacion, fechaContabilizacion);
/* 305:    */   }
/* 306:    */   
/* 307:    */   public int contarPorCriterio(Map<String, String> filters)
/* 308:    */   {
/* 309:402 */     return this.compraCajaChicaDao.contarPorCriterio(filters);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void actualizarAtributoEntidad(CompraCajaChica compraCajaChica, HashMap<String, Object> campos)
/* 313:    */   {
/* 314:407 */     this.compraCajaChicaDao.actualizarAtributoEntidad(compraCajaChica, campos);
/* 315:    */   }
/* 316:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioCompraCajaChicaImpl
 * JD-Core Version:    0.7.0.1
 */