/*   1:    */ package com.asinfo.as2.financiero.cobros.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   4:    */ import com.asinfo.as2.entities.Banco;
/*   5:    */ import com.asinfo.as2.entities.Cobro;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   8:    */ import com.asinfo.as2.entities.DetalleCobro;
/*   9:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  13:    */ import com.asinfo.as2.entities.FormaPago;
/*  14:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  15:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCargaCobros;
/*  23:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  27:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  30:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  31:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  32:    */ import java.io.IOException;
/*  33:    */ import java.io.InputStream;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.util.ArrayList;
/*  36:    */ import java.util.Collections;
/*  37:    */ import java.util.Comparator;
/*  38:    */ import java.util.Date;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.Iterator;
/*  41:    */ import java.util.List;
/*  42:    */ import java.util.Map;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.ejb.SessionContext;
/*  45:    */ import javax.ejb.Stateless;
/*  46:    */ import javax.ejb.TransactionAttribute;
/*  47:    */ import javax.ejb.TransactionAttributeType;
/*  48:    */ import org.apache.log4j.Logger;
/*  49:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  50:    */ 
/*  51:    */ @Stateless
/*  52:    */ public class ServicioCargaCobrosImpl
/*  53:    */   extends AbstractServicioAS2Financiero
/*  54:    */   implements ServicioCargaCobros
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = 1L;
/*  57:    */   @EJB
/*  58:    */   private ServicioCobro servicioCobro;
/*  59:    */   @EJB
/*  60:    */   private ServicioDocumento servicioDocumento;
/*  61:    */   @EJB
/*  62:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  63:    */   @EJB
/*  64:    */   private ServicioGenerico<Banco> servicioBanco;
/*  65:    */   
/*  66:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  67:    */   public void cargarCobros(Integer idOrganizacion, String fileName, InputStream imInputStream, int finaInicial, boolean retencion)
/*  68:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  69:    */   {
/*  70: 87 */     Map<String, Cobro> hmCobroDeposito = new HashMap();
/*  71: 88 */     Map<String, CuentaBancariaOrganizacion> hmCuentaBancariaOrganizacion = new HashMap();
/*  72: 89 */     Map<String, Documento> hmDocumento = new HashMap();
/*  73: 90 */     Map<String, Banco> hmBanco = new HashMap();
/*  74: 91 */     HashMap<CuentaPorCobrar, DetalleCobro> hmDetalleCobro = new HashMap();
/*  75:    */     
/*  76:    */ 
/*  77: 94 */     BigDecimal tolerancia = ParametrosSistema.getValorToleranciaCompraVenta(AppUtil.getOrganizacion().getIdOrganizacion());
/*  78:    */     try
/*  79:    */     {
/*  80: 99 */       datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, finaInicial, 0);
/*  81:    */     }
/*  82:    */     catch (IOException e1)
/*  83:    */     {
/*  84:    */       HSSFCell[][] datos;
/*  85:101 */       throw new ExcepcionAS2("msg_error_cargar_datos");
/*  86:    */     }
/*  87:    */     HSSFCell[][] datos;
/*  88:111 */     String descripcion = "";
/*  89:    */     
/*  90:113 */     boolean indicadorValidarValor = false;
/*  91:114 */     String numeroAutorizacion = "";
/*  92:115 */     FormaPago formaPago = null;
/*  93:116 */     Banco banco = null;
/*  94:117 */     String nombreBanco = "";
/*  95:118 */     boolean indicadorChequePosfechado = false;
/*  96:119 */     String numeroCuentaCheque = "";
/*  97:120 */     Date fechaCobroCheque = null;
/*  98:121 */     String checheGiradoPor = "";
/*  99:122 */     String checheRecibodoPor = "";
/* 100:123 */     String notaCheche = "";
/* 101:124 */     String punto = "";
/* 102:125 */     String establecimiento = "";
/* 103:    */     
/* 104:127 */     BigDecimal valorAcumuladoParaFactura = BigDecimal.ZERO;
/* 105:    */     
/* 106:    */ 
/* 107:130 */     int filaActual = finaInicial + 1;
/* 108:131 */     int columnaActual = 0;
/* 109:132 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 110:136 */     for (HSSFCell[] fila : datos)
/* 111:    */     {
/* 112:    */       try
/* 113:    */       {
/* 114:141 */         filaErronea = fila;
/* 115:    */         
/* 116:143 */         String factura = fila[(columnaActual = 0)].getStringCellValue().trim();
/* 117:144 */         Date fechaCobro = fila[(columnaActual = 1)].getDateCellValue();
/* 118:145 */         String codigoFormaPago = fila[(columnaActual = 2)].getStringCellValue().trim();
/* 119:146 */         String cuentaContable = fila[(columnaActual = 3)].getStringCellValue().trim();
/* 120:147 */         BigDecimal valor = BigDecimal.valueOf(fila[(columnaActual = 4)].getNumericCellValue());
/* 121:    */         
/* 122:149 */         columnaActual = 5;
/* 123:150 */         String numeroDeposito = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 124:    */         
/* 125:152 */         columnaActual = 6;
/* 126:153 */         descripcion = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 127:    */         
/* 128:155 */         columnaActual = 7;
/* 129:156 */         if (retencion)
/* 130:    */         {
/* 131:157 */           String nombreDocumento = fila[columnaActual].getStringCellValue().trim();
/* 132:158 */           columnaActual = 8;
/* 133:159 */           numeroAutorizacion = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 134:160 */           columnaActual = 9;
/* 135:161 */           punto = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 136:162 */           columnaActual = 10;
/* 137:163 */           establecimiento = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 138:    */         }
/* 139:    */         else
/* 140:    */         {
/* 141:165 */           int numeroControl = (int)(fila[columnaActual] != null ? fila[columnaActual].getNumericCellValue() : 0.0D);
/* 142:    */           
/* 143:167 */           columnaActual = 8;
/* 144:    */           
/* 145:169 */           indicadorValidarValor = (fila[columnaActual] == null ? "NO" : fila[columnaActual].getStringCellValue().trim()).equalsIgnoreCase("SI");
/* 146:    */           
/* 147:171 */           String nombreDocumento = fila[(columnaActual = 9)].getStringCellValue().trim();
/* 148:173 */           if (fila.length >= 11)
/* 149:    */           {
/* 150:175 */             columnaActual = 10;
/* 151:176 */             nombreBanco = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 152:    */             
/* 153:178 */             columnaActual = 11;
/* 154:    */             
/* 155:180 */             indicadorChequePosfechado = (fila[columnaActual] == null ? "NO" : fila[columnaActual].getStringCellValue().trim()).equalsIgnoreCase("SI");
/* 156:    */             
/* 157:182 */             columnaActual = 12;
/* 158:183 */             numeroCuentaCheque = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 159:    */             
/* 160:185 */             columnaActual = 13;
/* 161:186 */             fechaCobroCheque = fila[columnaActual] != null ? fila[columnaActual].getDateCellValue() : null;
/* 162:    */             
/* 163:188 */             columnaActual = 14;
/* 164:189 */             checheGiradoPor = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 165:    */             
/* 166:191 */             columnaActual = 15;
/* 167:192 */             checheRecibodoPor = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 168:    */             
/* 169:194 */             columnaActual = 16;
/* 170:195 */             notaCheche = fila[columnaActual] != null ? fila[columnaActual].getStringCellValue().trim() : "";
/* 171:    */           }
/* 172:    */         }
/* 173:    */       }
/* 174:    */       catch (IllegalArgumentException e)
/* 175:    */       {
/* 176:201 */         this.context.setRollbackOnly();
/* 177:    */         
/* 178:203 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/* 179:    */       }
/* 180:    */       catch (Exception e)
/* 181:    */       {
/* 182:205 */         this.context.setRollbackOnly();
/* 183:206 */         e.printStackTrace();
/* 184:    */         
/* 185:208 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/* 186:    */       }
/* 187:    */       try
/* 188:    */       {
/* 189:    */         String nombreDocumento;
/* 190:    */         String numeroDeposito;
/* 191:    */         BigDecimal valor;
/* 192:    */         String cuentaContable;
/* 193:    */         String codigoFormaPago;
/* 194:    */         Date fechaCobro;
/* 195:    */         String factura;
/* 196:218 */         CuentaBancariaOrganizacion cuentaBancariaOrganizacion = (CuentaBancariaOrganizacion)hmCuentaBancariaOrganizacion.get(cuentaContable);
/* 197:    */         Map<String, String> filters;
/* 198:219 */         if (cuentaBancariaOrganizacion == null)
/* 199:    */         {
/* 200:220 */           filters = new HashMap();
/* 201:221 */           filters.put("cuentaContableBanco.codigo", cuentaContable);
/* 202:222 */           List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", true, filters);
/* 203:224 */           if (!listaCuentaBancariaOrganizacion.isEmpty())
/* 204:    */           {
/* 205:225 */             cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(((CuentaBancariaOrganizacion)listaCuentaBancariaOrganizacion.get(0)).getId());
/* 206:226 */             hmCuentaBancariaOrganizacion.put(cuentaContable, cuentaBancariaOrganizacion);
/* 207:    */           }
/* 208:    */           else
/* 209:    */           {
/* 210:228 */             throw new ExcepcionAS2("msg_error_cuenta_bancaria_erronea", " " + cuentaContable);
/* 211:    */           }
/* 212:    */         }
/* 213:235 */         formaPago = null;
/* 214:236 */         if (cuentaBancariaOrganizacion.getListaFormaPago().isEmpty()) {
/* 215:237 */           throw new ExcepcionAS2("msg_error_no_existe_forma_para_cuenta_bancaria", " " + cuentaContable);
/* 216:    */         }
/* 217:239 */         for (FormaPagoCuentaBancariaOrganizacion fp : cuentaBancariaOrganizacion.getListaFormaPago()) {
/* 218:240 */           if (fp.getFormaPago().getCodigo().equalsIgnoreCase(codigoFormaPago))
/* 219:    */           {
/* 220:241 */             formaPago = fp.getFormaPago();
/* 221:242 */             break;
/* 222:    */           }
/* 223:    */         }
/* 224:250 */         if (!nombreBanco.isEmpty())
/* 225:    */         {
/* 226:251 */           banco = (Banco)hmBanco.get(nombreBanco);
/* 227:252 */           if (banco == null) {
/* 228:253 */             banco = (Banco)this.servicioBanco.buscarPorNombre(Banco.class, idOrganizacion.intValue(), nombreBanco);
/* 229:    */           }
/* 230:    */         }
/* 231:257 */         if (formaPago == null) {
/* 232:258 */           throw new ExcepcionAS2("msg_error_no_existe_forma_para_cuenta_bancaria", " " + cuentaContable);
/* 233:    */         }
/* 234:264 */         FacturaCliente facturaCliente = null;
/* 235:265 */         Map<String, String> filters = new HashMap();
/* 236:266 */         filters.put("idOrganizacion", "" + idOrganizacion);
/* 237:267 */         filters.put("numero", factura);
/* 238:268 */         filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/* 239:269 */         List<FacturaCliente> listaFacturaCliente = this.servicioFacturaCliente.obtenerListaComboConEqual("numero", true, filters);
/* 240:    */         
/* 241:271 */         CuentaPorCobrar cuentaPorCobrar = null;
/* 242:272 */         if (!listaFacturaCliente.isEmpty())
/* 243:    */         {
/* 244:274 */           facturaCliente = (FacturaCliente)listaFacturaCliente.get(0);
/* 245:275 */           List<CuentaPorCobrar> listaCxC = this.servicioFacturaCliente.obtenerFacturasPendientes(0, facturaCliente.getId());
/* 246:    */           
/* 247:277 */           Iterator localIterator2 = listaCxC.iterator();
/* 248:277 */           if (localIterator2.hasNext())
/* 249:    */           {
/* 250:277 */             CuentaPorCobrar cxc = (CuentaPorCobrar)localIterator2.next();
/* 251:278 */             cuentaPorCobrar = cxc;
/* 252:    */           }
/* 253:282 */           if (cuentaPorCobrar == null) {
/* 254:283 */             throw new ExcepcionAS2("msg_error_numero_factura", " " + factura);
/* 255:    */           }
/* 256:    */         }
/* 257:    */         else
/* 258:    */         {
/* 259:287 */           throw new ExcepcionAS2("msg_error_numero_factura", " " + factura);
/* 260:    */         }
/* 261:293 */         Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/* 262:294 */         if (documento == null)
/* 263:    */         {
/* 264:295 */           Object filtroDoc = new HashMap();
/* 265:296 */           ((Map)filtroDoc).put("documentoBase", "" + DocumentoBase.COBRO_CLIENTE);
/* 266:297 */           ((Map)filtroDoc).put("nombre", nombreDocumento);
/* 267:298 */           List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("", false, (Map)filtroDoc);
/* 268:299 */           if (listaDocumento.isEmpty()) {
/* 269:300 */             throw new ExcepcionAS2("msg_configuracion_documento", " " + nombreDocumento);
/* 270:    */           }
/* 271:302 */           documento = (Documento)listaDocumento.get(0);
/* 272:303 */           hmDocumento.put("nombre", documento);
/* 273:    */         }
/* 274:313 */         String clave = null;
/* 275:315 */         if (tolerancia.compareTo(BigDecimal.ZERO) == 0) {
/* 276:316 */           clave = facturaCliente.getEmpresa().getId() + "~" + (numeroDeposito.isEmpty() ? facturaCliente.getNumero() : numeroDeposito);
/* 277:    */         } else {
/* 278:318 */           clave = numeroDeposito.isEmpty() ? facturaCliente.getNumero() : numeroDeposito;
/* 279:    */         }
/* 280:321 */         Cobro cobro = (Cobro)hmCobroDeposito.get(clave);
/* 281:322 */         if (cobro == null)
/* 282:    */         {
/* 283:323 */           valorAcumuladoParaFactura = BigDecimal.ZERO;
/* 284:    */           
/* 285:325 */           cobro = new Cobro();
/* 286:326 */           cobro.setEstado(Estado.ELABORADO);
/* 287:327 */           cobro.setDocumento(documento);
/* 288:328 */           cobro.setEmpresa(facturaCliente.getEmpresa());
/* 289:329 */           cobro.setFecha(fechaCobro);
/* 290:330 */           cobro.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 291:331 */           cobro.setSucursal(facturaCliente.getSucursal());
/* 292:332 */           cobro.setValor(valor);
/* 293:333 */           cobro.setDescripcion(descripcion);
/* 294:334 */           cobro.setTolerancia(tolerancia);
/* 295:    */           
/* 296:    */ 
/* 297:337 */           DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/* 298:338 */           detalleFormaCobro.setIdOrganizacion(cobro.getIdOrganizacion());
/* 299:339 */           detalleFormaCobro.setIdSucursal(cobro.getSucursal().getId());
/* 300:340 */           detalleFormaCobro.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/* 301:341 */           detalleFormaCobro.setCuentaContableFormaCobro(cuentaBancariaOrganizacion.getCuentaContableBanco());
/* 302:342 */           detalleFormaCobro.setCaja(AppUtil.getCaja());
/* 303:343 */           detalleFormaCobro.setFormaPago(formaPago);
/* 304:344 */           detalleFormaCobro.setBanco(banco);
/* 305:345 */           detalleFormaCobro.setCobro(cobro);
/* 306:346 */           detalleFormaCobro.setDescripcion(descripcion);
/* 307:347 */           detalleFormaCobro.setDocumentoReferencia(numeroDeposito);
/* 308:348 */           if (retencion)
/* 309:    */           {
/* 310:349 */             detalleFormaCobro.setValor(valor);
/* 311:350 */             if ((detalleFormaCobro.getFormaPago().isIndicadorRetencionFuente()) || 
/* 312:351 */               (detalleFormaCobro.getFormaPago().isIndicadorRetencionIva()))
/* 313:    */             {
/* 314:352 */               detalleFormaCobro.setAutorizacion(numeroAutorizacion.length() > 50 ? numeroAutorizacion.substring(0, 50) : numeroAutorizacion);
/* 315:353 */               detalleFormaCobro.setDocumentoReferencia(establecimiento + "-" + punto + "-" + FuncionesUtiles.completarALaIzquierda('0', 9, numeroDeposito));
/* 316:    */             }
/* 317:355 */             valorAcumuladoParaFactura = valorAcumuladoParaFactura.add(detalleFormaCobro.getValor());
/* 318:    */           }
/* 319:357 */           cobro.getListaDetalleFormaCobro().add(detalleFormaCobro);
/* 320:360 */           if (indicadorChequePosfechado)
/* 321:    */           {
/* 322:362 */             cobro.setIndicadorTieneCheques(true);
/* 323:363 */             cobro.setIndicadorTienePosfechados(true);
/* 324:    */             
/* 325:365 */             GarantiaCliente garantiaCliente = new GarantiaCliente();
/* 326:366 */             garantiaCliente.setIdOrganizacion(detalleFormaCobro.getCobro().getIdOrganizacion());
/* 327:367 */             garantiaCliente.setIdSucursal(detalleFormaCobro.getCobro().getSucursal().getId());
/* 328:368 */             garantiaCliente.setTipoGarantiaCliente(TipoGarantiaCliente.CHEQUE_POSFECHADO);
/* 329:369 */             garantiaCliente.setEmpresa(detalleFormaCobro.getCobro().getEmpresa());
/* 330:370 */             garantiaCliente.setNumero(detalleFormaCobro.getDocumentoReferencia());
/* 331:371 */             garantiaCliente.setValor(detalleFormaCobro.getValor());
/* 332:372 */             garantiaCliente.setFechaIngreso(detalleFormaCobro.getCobro().getFecha());
/* 333:373 */             garantiaCliente.setCuentaBancariaOrganizacion(detalleFormaCobro.getCuentaBancariaOrganizacion());
/* 334:374 */             garantiaCliente.setBanco(detalleFormaCobro.getBanco());
/* 335:375 */             garantiaCliente.setNumeroCuenta(numeroCuentaCheque);
/* 336:376 */             garantiaCliente.setFechaCobro(fechaCobroCheque);
/* 337:377 */             garantiaCliente.setDiasCreditoOtorgado(FuncionesUtiles.diferenciasDeFechas(fechaCobro, fechaCobroCheque));
/* 338:379 */             if (garantiaCliente.getDiasCreditoOtorgado() < 0) {
/* 339:380 */               garantiaCliente.setDiasCreditoOtorgado(0);
/* 340:    */             }
/* 341:383 */             garantiaCliente.setRecibidoPor(checheRecibodoPor);
/* 342:384 */             garantiaCliente.setGirador(checheGiradoPor);
/* 343:385 */             garantiaCliente.setObservacion(notaCheche);
/* 344:    */             
/* 345:387 */             detalleFormaCobro.setGarantiaCliente(garantiaCliente);
/* 346:388 */             detalleFormaCobro.setIndicadorChequePosfechado(true);
/* 347:    */           }
/* 348:391 */           hmCobroDeposito.put(clave, cobro);
/* 349:    */         }
/* 350:    */         else
/* 351:    */         {
/* 352:394 */           valorAcumuladoParaFactura = cobro.getValor();
/* 353:395 */           cobro.setValor(cobro.getValor().add(valor));
/* 354:397 */           if (retencion)
/* 355:    */           {
/* 356:399 */             DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/* 357:400 */             detalleFormaCobro.setIdOrganizacion(cobro.getIdOrganizacion());
/* 358:401 */             detalleFormaCobro.setIdSucursal(cobro.getSucursal().getId());
/* 359:402 */             detalleFormaCobro.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/* 360:403 */             detalleFormaCobro.setCuentaContableFormaCobro(cuentaBancariaOrganizacion.getCuentaContableBanco());
/* 361:404 */             detalleFormaCobro.setCaja(AppUtil.getCaja());
/* 362:405 */             detalleFormaCobro.setFormaPago(formaPago);
/* 363:406 */             detalleFormaCobro.setBanco(banco);
/* 364:407 */             detalleFormaCobro.setCobro(cobro);
/* 365:408 */             detalleFormaCobro.setDescripcion(descripcion);
/* 366:409 */             detalleFormaCobro.setDocumentoReferencia(numeroDeposito);
/* 367:410 */             if ((detalleFormaCobro.getFormaPago().isIndicadorRetencionFuente()) || 
/* 368:411 */               (detalleFormaCobro.getFormaPago().isIndicadorRetencionIva())) {
/* 369:412 */               detalleFormaCobro.setAutorizacion(numeroAutorizacion);
/* 370:    */             }
/* 371:414 */             if (retencion)
/* 372:    */             {
/* 373:415 */               detalleFormaCobro.setValor(valor);
/* 374:416 */               valorAcumuladoParaFactura = valorAcumuladoParaFactura.add(detalleFormaCobro.getValor());
/* 375:    */             }
/* 376:419 */             cobro.getListaDetalleFormaCobro().add(detalleFormaCobro);
/* 377:    */           }
/* 378:    */         }
/* 379:424 */         if (!retencion) {
/* 380:425 */           ((DetalleFormaCobro)cobro.getListaDetalleFormaCobro().get(0)).setValor(cobro.getValor());
/* 381:    */         }
/* 382:428 */         if ((indicadorValidarValor) && 
/* 383:429 */           (valor.subtract(cuentaPorCobrar.getSaldo()).abs().compareTo(tolerancia) > 0)) {
/* 384:430 */           throw new ExcepcionAS2("msg_error_valor_cobro", " " + factura);
/* 385:    */         }
/* 386:434 */         DetalleCobro detalleCobro = (DetalleCobro)hmDetalleCobro.get(cuentaPorCobrar);
/* 387:435 */         if (retencion)
/* 388:    */         {
/* 389:436 */           if (detalleCobro == null)
/* 390:    */           {
/* 391:437 */             detalleCobro = new DetalleCobro();
/* 392:438 */             detalleCobro.setCuentaPorCobrar(cuentaPorCobrar);
/* 393:439 */             detalleCobro.setCobro(cobro);
/* 394:440 */             detalleCobro.setIdOrganizacion(cobro.getIdOrganizacion());
/* 395:441 */             detalleCobro.setIdSucursal(cobro.getSucursal().getId());
/* 396:442 */             cobro.getListaDetalleCobro().add(detalleCobro);
/* 397:443 */             hmDetalleCobro.put(cuentaPorCobrar, detalleCobro);
/* 398:    */           }
/* 399:445 */           detalleCobro.setValor(valorAcumuladoParaFactura);
/* 400:    */         }
/* 401:    */         else
/* 402:    */         {
/* 403:447 */           detalleCobro = new DetalleCobro();
/* 404:448 */           detalleCobro.setCuentaPorCobrar(cuentaPorCobrar);
/* 405:449 */           detalleCobro.setCobro(cobro);
/* 406:450 */           detalleCobro.setIdOrganizacion(cobro.getIdOrganizacion());
/* 407:451 */           detalleCobro.setIdSucursal(cobro.getSucursal().getId());
/* 408:452 */           detalleCobro.setValor(valor);
/* 409:453 */           cobro.getListaDetalleCobro().add(detalleCobro);
/* 410:    */         }
/* 411:456 */         filaActual++;
/* 412:    */       }
/* 413:    */       catch (ExcepcionAS2Financiero e)
/* 414:    */       {
/* 415:459 */         LOG.error("Error al migrar factura proveedor", e);
/* 416:460 */         this.context.setRollbackOnly();
/* 417:461 */         throw e;
/* 418:    */       }
/* 419:    */       catch (ExcepcionAS2 e)
/* 420:    */       {
/* 421:463 */         LOG.error("Error al migrar factura proveedor", e);
/* 422:464 */         this.context.setRollbackOnly();
/* 423:465 */         throw e;
/* 424:    */       }
/* 425:    */       catch (Exception e)
/* 426:    */       {
/* 427:467 */         LOG.error("Error al migrar factura proveedor", e);
/* 428:468 */         this.context.setRollbackOnly();
/* 429:469 */         throw new ExcepcionAS2(e);
/* 430:    */       }
/* 431:    */     }
/* 432:    */     try
/* 433:    */     {
/* 434:477 */       Object listaCobro = new ArrayList(hmCobroDeposito.values());
/* 435:    */       
/* 436:    */ 
/* 437:    */ 
/* 438:    */ 
/* 439:    */ 
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:486 */       Collections.sort((List)listaCobro, new Comparator()
/* 444:    */       {
/* 445:    */         public int compare(Cobro arg0, Cobro arg1)
/* 446:    */         {
/* 447:482 */           return arg0.getFecha().compareTo(arg1.getFecha());
/* 448:    */         }
/* 449:    */       });
/* 450:490 */       for (Cobro cobro : (List)listaCobro) {
/* 451:491 */         this.servicioCobro.guardar(cobro);
/* 452:    */       }
/* 453:    */     }
/* 454:    */     catch (ExcepcionAS2Financiero e)
/* 455:    */     {
/* 456:495 */       LOG.error("Error al migrar factura proveedor", e);
/* 457:496 */       this.context.setRollbackOnly();
/* 458:497 */       throw e;
/* 459:    */     }
/* 460:    */     catch (ExcepcionAS2 e)
/* 461:    */     {
/* 462:499 */       LOG.error("Error al migrar factura proveedor", e);
/* 463:500 */       this.context.setRollbackOnly();
/* 464:501 */       throw e;
/* 465:    */     }
/* 466:    */     catch (Exception e)
/* 467:    */     {
/* 468:503 */       LOG.error("Error al migrar factura proveedor", e);
/* 469:504 */       this.context.setRollbackOnly();
/* 470:505 */       throw new ExcepcionAS2(e);
/* 471:    */     }
/* 472:    */   }
/* 473:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioCargaCobrosImpl
 * JD-Core Version:    0.7.0.1
 */