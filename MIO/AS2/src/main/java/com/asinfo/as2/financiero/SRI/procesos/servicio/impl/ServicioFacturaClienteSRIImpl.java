/*   1:    */ package com.asinfo.as2.financiero.SRI.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DocumentoDao;
/*   4:    */ import com.asinfo.as2.dao.sri.FacturaClienteSRIDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  10:    */ import com.asinfo.as2.entities.FormaPagoSRI;
/*  11:    */ import com.asinfo.as2.entities.Impuesto;
/*  12:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  15:    */ import com.asinfo.as2.entities.Secuencia;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*  18:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  19:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  20:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  21:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  22:    */ import com.asinfo.as2.enumeraciones.RefrendoEnum;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  25:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRIRemote;
/*  26:    */ import com.asinfo.as2.utils.DatosSRI;
/*  27:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  28:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  29:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  30:    */ import java.math.BigDecimal;
/*  31:    */ import java.math.RoundingMode;
/*  32:    */ import java.util.Date;
/*  33:    */ import java.util.List;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.ejb.Stateless;
/*  36:    */ import javax.faces.model.SelectItem;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ public class ServicioFacturaClienteSRIImpl
/*  40:    */   implements ServicioFacturaClienteSRI, ServicioFacturaClienteSRIRemote
/*  41:    */ {
/*  42:    */   @EJB
/*  43:    */   private FacturaClienteSRIDao facturaClienteSRIDao;
/*  44:    */   @EJB
/*  45:    */   private DocumentoDao documentoDao;
/*  46:    */   @EJB
/*  47:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  48:    */   @EJB
/*  49:    */   private ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  50:    */   
/*  51:    */   public void guardar(FacturaClienteSRI entidad)
/*  52:    */   {
/*  53: 69 */     this.facturaClienteSRIDao.guardar(entidad);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void eliminar(FacturaClienteSRI entidad)
/*  57:    */   {
/*  58: 80 */     this.facturaClienteSRIDao.eliminar(entidad);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public FacturaClienteSRI buscarPorId(int idFacturaClienteSRISRI)
/*  62:    */   {
/*  63: 91 */     return (FacturaClienteSRI)this.facturaClienteSRIDao.buscarPorId(Integer.valueOf(idFacturaClienteSRISRI));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void eliminarFacturaClienteSRI(int idFacturaClienteSRI)
/*  67:    */   {
/*  68:101 */     this.facturaClienteSRIDao.eliminarFacturaClienteSRI(Integer.valueOf(idFacturaClienteSRI));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<FacturaClienteSRI> obtenerFacturasMes(int anio, int mes, int idOrganizacion)
/*  72:    */   {
/*  73:111 */     return this.facturaClienteSRIDao.obtenerFacturasMes(anio, mes, idOrganizacion);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List<FacturaClienteSRI> obtenerValoresRetenidosMes(int anio, int mes, int idOrganizacion)
/*  77:    */   {
/*  78:121 */     return this.facturaClienteSRIDao.obtenerValoresRetenidosMes(anio, mes, idOrganizacion);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public FacturaCliente actualizarAutorizacionSRI(FacturaCliente facturaCliente, PuntoDeVenta puntoDeVenta)
/*  82:    */     throws ExcepcionAS2
/*  83:    */   {
/*  84:134 */     if (facturaCliente.getFacturaClienteSRI() != null) {
/*  85:135 */       if (facturaCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() != null)
/*  86:    */       {
/*  87:136 */         facturaCliente.getFacturaClienteSRI().setAutorizacion(facturaCliente
/*  88:137 */           .getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().getAutorizacionAutoimpresorSRI().getAutorizacion());
/*  89:138 */         facturaCliente.getFacturaClienteSRI().setFechaAutorizacion(facturaCliente
/*  90:139 */           .getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().getAutorizacionAutoimpresorSRI().getFechaDesde());
/*  91:140 */         facturaCliente.getFacturaClienteSRI().setFechaCaducidad(facturaCliente
/*  92:141 */           .getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().getAutorizacionAutoimpresorSRI().getFechaHasta());
/*  93:    */       }
/*  94:    */       else
/*  95:    */       {
/*  96:143 */         AutorizacionDocumentoSRI autorizacionDocumentoSRI = this.documentoDao.obtenerAutorizacionConSecuencia(facturaCliente.getFecha(), facturaCliente
/*  97:144 */           .getDocumento(), puntoDeVenta);
/*  98:145 */         facturaCliente.getFacturaClienteSRI().setAutorizacion(autorizacionDocumentoSRI.getAutorizacion());
/*  99:146 */         facturaCliente.getFacturaClienteSRI().setFechaAutorizacion(autorizacionDocumentoSRI.getSecuencia().getFechaDesde());
/* 100:147 */         facturaCliente.getFacturaClienteSRI().setFechaCaducidad(autorizacionDocumentoSRI.getSecuencia().getFechaHasta());
/* 101:    */       }
/* 102:    */     }
/* 103:151 */     return facturaCliente;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public FacturaCliente actualizarFacturaClienteSRI(FacturaCliente facturaCliente)
/* 107:    */     throws ExcepcionAS2
/* 108:    */   {
/* 109:164 */     if (!facturaCliente.getDocumento().isIndicadorDocumentoTributario())
/* 110:    */     {
/* 111:165 */       if (facturaCliente.getFacturaClienteSRI() != null)
/* 112:    */       {
/* 113:167 */         FacturaClienteSRI fc = facturaCliente.getFacturaClienteSRI();
/* 114:169 */         if (fc != null)
/* 115:    */         {
/* 116:170 */           facturaCliente.setFacturaClienteSRI(null);
/* 117:171 */           fc.setFacturaCliente(null);
/* 118:172 */           fc.setEliminado(true);
/* 119:173 */           this.facturaClienteSRIDao.guardar(fc);
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:    */     else
/* 124:    */     {
/* 125:177 */       BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/* 126:178 */       BigDecimal montoIva = BigDecimal.ZERO;
/* 127:179 */       BigDecimal montoIce = facturaCliente.getMontoIce();
/* 128:180 */       BigDecimal montoIRBPNR = BigDecimal.ZERO;
/* 129:    */       FacturaClienteSRI facturaClienteSRI;
/* 130:183 */       if (facturaCliente.getFacturaClienteSRI() == null)
/* 131:    */       {
/* 132:185 */         FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 133:186 */         facturaClienteSRI.setEstado(facturaCliente.getEstado());
/* 134:187 */         facturaClienteSRI.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 135:188 */         facturaClienteSRI.setIdSucursal(facturaCliente.getSucursal().getId());
/* 136:    */         
/* 137:190 */         facturaClienteSRI.setFacturaCliente(facturaCliente);
/* 138:191 */         facturaCliente.setFacturaClienteSRI(facturaClienteSRI);
/* 139:    */       }
/* 140:    */       else
/* 141:    */       {
/* 142:194 */         facturaClienteSRI = facturaCliente.getFacturaClienteSRI();
/* 143:    */       }
/* 144:    */       List<FormaPagoSRI> listaFormaPagoSRI;
/* 145:197 */       if ((facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI() == null) || 
/* 146:198 */         (facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI().equals("")))
/* 147:    */       {
/* 148:199 */         listaFormaPagoSRI = this.servicioFormaPagoSRI.getListaFormaPagoSRI(facturaCliente.getEmpresa());
/* 149:200 */         if (listaFormaPagoSRI.size() > 0) {
/* 150:201 */           facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(((FormaPagoSRI)listaFormaPagoSRI.get(0)).getCodigo());
/* 151:    */         } else {
/* 152:204 */           facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(((SelectItem)DatosSRI.getListaFormaPago(facturaCliente.getIdOrganizacion()).get(0)).getValue().toString());
/* 153:    */         }
/* 154:    */       }
/* 155:207 */       for (DetalleFacturaCliente detalleFacturaCliente : facturaCliente.getListaDetalleFacturaCliente()) {
/* 156:209 */         if (!detalleFacturaCliente.isEliminado())
/* 157:    */         {
/* 158:210 */           boolean existeIRBPNR = false;
/* 159:211 */           boolean existeOtrosImpuestos = false;
/* 160:212 */           for (ImpuestoProductoFacturaCliente impuestoProductoFacturaCliente : detalleFacturaCliente
/* 161:213 */             .getListaImpuestoProductoFacturaCliente()) {
/* 162:214 */             if (!impuestoProductoFacturaCliente.isEliminado())
/* 163:    */             {
/* 164:215 */               if (impuestoProductoFacturaCliente.getImpuesto().getCodigo().equals("5"))
/* 165:    */               {
/* 166:216 */                 montoIRBPNR = montoIRBPNR.add(impuestoProductoFacturaCliente.getImpuestoProducto());
/* 167:217 */                 existeIRBPNR = true;
/* 168:    */               }
/* 169:219 */               if (impuestoProductoFacturaCliente.getImpuesto().isIndicadorImpuestoTributario())
/* 170:    */               {
/* 171:220 */                 baseImponibleDiferenteCero = baseImponibleDiferenteCero.add(detalleFacturaCliente.getBaseImponible());
/* 172:221 */                 montoIva = montoIva.add(impuestoProductoFacturaCliente.getImpuestoProducto());
/* 173:222 */                 existeOtrosImpuestos = true;
/* 174:    */               }
/* 175:    */             }
/* 176:    */           }
/* 177:227 */           if ((!existeIRBPNR) || (existeOtrosImpuestos)) {}
/* 178:    */         }
/* 179:    */       }
/* 180:233 */       facturaClienteSRI.setEliminado(false);
/* 181:    */       
/* 182:    */ 
/* 183:236 */       String numeroFactura = facturaCliente.getNumero();
/* 184:237 */       facturaClienteSRI.setEstablecimiento(numeroFactura.substring(0, 3));
/* 185:238 */       facturaClienteSRI.setPuntoEmision(numeroFactura.substring(4, 7));
/* 186:239 */       facturaClienteSRI.setNumero(numeroFactura.substring(8));
/* 187:    */       
/* 188:    */ 
/* 189:242 */       BigDecimal diferencia = facturaCliente.getTotal().add(facturaClienteSRI.getMontoIce()).subtract(facturaCliente.getDescuento()).subtract(baseImponibleDiferenteCero);
/* 190:244 */       if (diferencia.compareTo(BigDecimal.ZERO) < 0) {
/* 191:245 */         baseImponibleDiferenteCero = baseImponibleDiferenteCero.add(diferencia);
/* 192:    */       }
/* 193:249 */       baseImponibleDiferenteCero = FuncionesUtiles.redondearBigDecimal(baseImponibleDiferenteCero, 2);
/* 194:    */       
/* 195:251 */       montoIva = FuncionesUtiles.redondearBigDecimal(montoIva, 2);
/* 196:    */       
/* 197:    */ 
/* 198:254 */       facturaClienteSRI.setBaseImponibleDiferenteCero(baseImponibleDiferenteCero);
/* 199:255 */       facturaClienteSRI.setTipoIdentificacion(facturaCliente.getEmpresa().getTipoIdentificacion());
/* 200:258 */       if (facturaCliente.isIndicadorGeneraCxC()) {
/* 201:259 */         facturaCliente.getFacturaClienteSRI().setIndicadorSaldoInicial(facturaCliente.isIndicadorSaldoInicial());
/* 202:    */       } else {
/* 203:261 */         facturaCliente.getFacturaClienteSRI().setIndicadorSaldoInicial(false);
/* 204:    */       }
/* 205:265 */       TipoComprobanteSRI tipoComprobanteSRI = facturaCliente.getDocumento().getTipoComprobanteSRI();
/* 206:266 */       if (tipoComprobanteSRI == null) {
/* 207:267 */         throw new ExcepcionAS2("msgs_error_info_configuracion_documento_sri");
/* 208:    */       }
/* 209:269 */       facturaClienteSRI.setTipoComprobanteSRI(tipoComprobanteSRI);
/* 210:    */       
/* 211:271 */       facturaClienteSRI.setBaseImponibleTarifaCero(facturaCliente.getTotal().add(facturaClienteSRI.getMontoIce())
/* 212:272 */         .subtract(facturaCliente.getDescuento()).subtract(baseImponibleDiferenteCero).setScale(2, RoundingMode.HALF_UP));
/* 213:    */       
/* 214:274 */       facturaClienteSRI.setIdentificacionCliente(facturaCliente.getEmpresa().getIdentificacion().trim());
/* 215:275 */       facturaClienteSRI.setFechaEmision(facturaCliente.getFecha());
/* 216:276 */       facturaClienteSRI.setMontoIva(montoIva);
/* 217:277 */       facturaClienteSRI.setMontoIRBPNR(montoIRBPNR.setScale(2, RoundingMode.HALF_UP));
/* 218:280 */       if (facturaCliente.getDocumento().isIndicadorDocumentoExterior())
/* 219:    */       {
/* 220:281 */         facturaClienteSRI.setValorFobComprobanteRefrendo(facturaCliente.getTotalFactura());
/* 221:282 */         facturaClienteSRI.setValorFobRefrendo(facturaCliente.getTotalFactura());
/* 222:    */       }
/* 223:286 */       if (facturaCliente.getFacturaClientePadre() != null)
/* 224:    */       {
/* 225:287 */         FacturaCliente facPadre = this.servicioFacturaCliente.cargarDetalle(facturaCliente.getFacturaClientePadre().getIdFacturaCliente());
/* 226:288 */         if (facPadre.getDocumento().isIndicadorDocumentoExterior())
/* 227:    */         {
/* 228:289 */           facturaClienteSRI.setFechaTransaccion(facturaCliente.getFecha());
/* 229:290 */           facturaClienteSRI.setCorrelativoRefrendo(facPadre.getFacturaClienteSRI().getCorrelativoRefrendo());
/* 230:291 */           facturaClienteSRI.setDistritoRefrendo(facPadre.getFacturaClienteSRI().getDistritoRefrendo());
/* 231:292 */           facturaClienteSRI.setDocumentoTransporteRefrendo(facPadre.getFacturaClienteSRI().getDocumentoTransporteRefrendo());
/* 232:293 */           facturaClienteSRI.setRefrendo(facPadre.getFacturaClienteSRI().getRefrendo());
/* 233:294 */           facturaClienteSRI.setRegimenRefrendo(facPadre.getFacturaClienteSRI().getRegimenRefrendo());
/* 234:295 */           facturaClienteSRI.setValorFobComprobanteRefrendo(facturaCliente.getTotalFactura());
/* 235:296 */           facturaClienteSRI.setValorFobRefrendo(facturaCliente.getTotalFactura());
/* 236:297 */           facturaClienteSRI.setAnioRefrendo(facPadre.getFacturaClienteSRI().getAnioRefrendo());
/* 237:    */         }
/* 238:    */       }
/* 239:303 */       facturaClienteSRI.setEmail(facturaCliente.getEmail());
/* 240:304 */       if ((facturaCliente.getDocumento() != null) && (facturaCliente.getFacturaClienteSRI() != null) && 
/* 241:305 */         (facturaCliente.getFacturaClienteSRI().getGenerarDocumentoElectronico().booleanValue())) {
/* 242:306 */         facturaClienteSRI.setIndicadorDocumentoElectronico(facturaCliente.getDocumento().isIndicadorDocumentoElectronico());
/* 243:    */       }
/* 244:    */     }
/* 245:310 */     return facturaCliente;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public FacturaClienteSRI buscarFacturaClienteSRIPorFacturaCliente(FacturaCliente facturaCliente)
/* 249:    */     throws ExcepcionAS2
/* 250:    */   {
/* 251:322 */     return this.facturaClienteSRIDao.buscarFacturaClienteSRIPorFacturaCliente(facturaCliente);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public List<FacturaClienteSRI> obtenerFacturasPorSerieEntreNumero(String establecimiento, String puntoVenta, Date fechaDesde, Date fechaHasta, Organizacion organizacion)
/* 255:    */   {
/* 256:334 */     return this.facturaClienteSRIDao.obtenerFacturasPorSerieEntreNumero(establecimiento, puntoVenta, fechaDesde, fechaHasta, organizacion);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<FacturaClienteSRI> obtenerFacturasExportacionMes(int anio, int mes, int idOrganizacion)
/* 260:    */   {
/* 261:344 */     return this.facturaClienteSRIDao.obtenerFacturasExportacionMes(anio, mes, idOrganizacion);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void actualizarDatosExportacion(FacturaCliente facturaCliente)
/* 265:    */     throws ExcepcionAS2Ventas
/* 266:    */   {
/* 267:355 */     validarDatosExportaciones(facturaCliente);
/* 268:356 */     this.facturaClienteSRIDao.actualizarDatosExportacion(facturaCliente);
/* 269:    */   }
/* 270:    */   
/* 271:    */   private void validarDatosExportaciones(FacturaCliente facturaCliente)
/* 272:    */     throws ExcepcionAS2Ventas
/* 273:    */   {
/* 274:366 */     if ((facturaCliente.getDocumento().isIndicadorDocumentoExterior()) && (facturaCliente.getFacturaClienteSRI().getRefrendo() == null)) {
/* 275:367 */       throw new ExcepcionAS2Ventas("msg_error_refrendo");
/* 276:    */     }
/* 277:371 */     if ((facturaCliente.getDocumento().isIndicadorDocumentoExterior()) && 
/* 278:372 */       (facturaCliente.getFacturaClienteSRI().getRefrendo().equals(RefrendoEnum.CON_REFRENDO)))
/* 279:    */     {
/* 280:373 */       if (("".equals(facturaCliente.getFacturaClienteSRI().getDistritoRefrendo())) || 
/* 281:374 */         (facturaCliente.getFacturaClienteSRI().getDistritoRefrendo() == null)) {
/* 282:375 */         throw new ExcepcionAS2Ventas("msg_error_distrito_refrendo");
/* 283:    */       }
/* 284:377 */       if ((facturaCliente.getFacturaClienteSRI().getAnioRefrendo().equals(Integer.valueOf(0))) || (facturaCliente.getFacturaClienteSRI().getAnioRefrendo() == null)) {
/* 285:378 */         throw new ExcepcionAS2Ventas("msg_error_anio_refrendo");
/* 286:    */       }
/* 287:380 */       if (("".equals(facturaCliente.getFacturaClienteSRI().getRegimenRefrendo())) || 
/* 288:381 */         (facturaCliente.getFacturaClienteSRI().getAnioRefrendo() == null)) {
/* 289:382 */         throw new ExcepcionAS2Ventas("msg_error_regimen_refrendo");
/* 290:    */       }
/* 291:384 */       if (("".equals(facturaCliente.getFacturaClienteSRI().getCorrelativoRefrendo())) || 
/* 292:385 */         (facturaCliente.getFacturaClienteSRI().getCorrelativoRefrendo() == null)) {
/* 293:386 */         throw new ExcepcionAS2Ventas("msg_error_correlativo_refrendo");
/* 294:    */       }
/* 295:388 */       if (("".equals(facturaCliente.getFacturaClienteSRI().getDocumentoTransporteRefrendo())) || 
/* 296:389 */         (facturaCliente.getFacturaClienteSRI().getDocumentoTransporteRefrendo() == null)) {
/* 297:390 */         throw new ExcepcionAS2Ventas("msg_error_documento_transportista_refrendo");
/* 298:    */       }
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public List<Object[]> obtenerFacturasMesResumen(int anio, int mes, int idOrganizacion)
/* 303:    */   {
/* 304:397 */     return this.facturaClienteSRIDao.obtenerFacturasMesResumen(anio, mes, idOrganizacion);
/* 305:    */   }
/* 306:    */   
/* 307:    */   public List<Object[]> getReporteICE(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 308:    */   {
/* 309:402 */     return this.facturaClienteSRIDao.getReporteICE(idOrganizacion, fechaDesde, fechaHasta);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public Long getImportaciones(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 313:    */   {
/* 314:407 */     return this.facturaClienteSRIDao.getImportaciones(idOrganizacion, fechaDesde, fechaHasta);
/* 315:    */   }
/* 316:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.impl.ServicioFacturaClienteSRIImpl
 * JD-Core Version:    0.7.0.1
 */