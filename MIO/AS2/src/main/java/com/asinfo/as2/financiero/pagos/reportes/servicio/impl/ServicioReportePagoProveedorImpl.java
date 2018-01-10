/*   1:    */ package com.asinfo.as2.financiero.pagos.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.dao.reportes.financiero.pagos.ReportePagoProveedorDao;
/*   6:    */ import com.asinfo.as2.entities.Banco;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  11:    */ import com.asinfo.as2.entities.DetalleFormaPago;
/*  12:    */ import com.asinfo.as2.entities.DetallePagoCash;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  16:    */ import com.asinfo.as2.entities.FormaPago;
/*  17:    */ import com.asinfo.as2.entities.PagoCash;
/*  18:    */ import com.asinfo.as2.entities.Proveedor;
/*  19:    */ import com.asinfo.as2.entities.Secuencia;
/*  20:    */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*  21:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  22:    */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*  23:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  24:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  25:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor;
/*  26:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  27:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.ejb.Stateless;
/*  36:    */ 
/*  37:    */ @Stateless
/*  38:    */ public class ServicioReportePagoProveedorImpl
/*  39:    */   implements ServicioReportePagoProveedor
/*  40:    */ {
/*  41:    */   @EJB
/*  42:    */   private transient ReportePagoProveedorDao reportePagoProveedorDao;
/*  43:    */   @EJB
/*  44:    */   private ServicioCiudad servicioCiudad;
/*  45:    */   @EJB
/*  46:    */   private ServicioSucursal servicioSucursal;
/*  47:    */   
/*  48:    */   public List getReportePagoProveedor(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/*  49:    */     throws ExcepcionAS2
/*  50:    */   {
/*  51: 60 */     return this.reportePagoProveedorDao.getReportePagoProveedor(fechaDesde, fechaHasta, idProveedor, idOrganizacion, categoriaEmpresa);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List getReportePago(int idPago)
/*  55:    */   {
/*  56: 71 */     return this.reportePagoProveedorDao.getReportePago(idPago);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<Object[]> getCashManagementProveedor(PagoCash pagoCash)
/*  60:    */     throws AS2Exception
/*  61:    */   {
/*  62: 83 */     List<Object[]> listaPagoCashManagementProveedor = new ArrayList();
/*  63: 85 */     if ((pagoCash.getCuentaBancariaOrganizacion() == null) || (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria() == null) || 
/*  64: 86 */       (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco() == null) || 
/*  65: 87 */       (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getTipoCuentaBancaria() == null)) {
/*  66: 88 */       throw new AS2Exception("msg_error_cuenta_bancaria_proveedor", new String[] { "" });
/*  67:    */     }
/*  68: 89 */     String cuentaBancariaEmpresa = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero();
/*  69: 90 */     String formaPagoEmpresa = pagoCash.getFormaPago().getCodigo();
/*  70: 91 */     String codigoBanco = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo();
/*  71: 92 */     String nombreBanco = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre();
/*  72: 93 */     String tipoCuenta = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getTipoCuentaBancaria().getCodigo();
/*  73: 94 */     String sector = "Norte";
/*  74:    */     
/*  75:    */ 
/*  76: 97 */     List<Object[]> listaProveedor = getListaProveedoresCash(pagoCash);
/*  77: 98 */     List<Integer> lproveedor = new ArrayList();
/*  78: 99 */     for (Object[] obj : listaProveedor) {
/*  79:100 */       lproveedor.add(Integer.valueOf(((Integer)obj[0]).intValue()));
/*  80:    */     }
/*  81:102 */     Object listaCuentaBancariaProveedor = this.reportePagoProveedorDao.getCuentaBancariaProveedor(lproveedor);
/*  82:    */     
/*  83:104 */     List<Object[]> listaDireccionTelefonoProveedor = this.reportePagoProveedorDao.getDireccionTelefonoProveedor(lproveedor);
/*  84:    */     
/*  85:106 */     HashMap<Integer, Object[]> hashMapDatosBanco = new HashMap();
/*  86:107 */     HashMap<Integer, Object[]> hashMapDatosDireccion = new HashMap();
/*  87:    */     
/*  88:109 */     int idProveedor = 0;
/*  89:110 */     for (Object[] object : (List)listaCuentaBancariaProveedor)
/*  90:    */     {
/*  91:111 */       idProveedor = Integer.parseInt(object[0].toString());
/*  92:112 */       hashMapDatosBanco.put(Integer.valueOf(idProveedor), object);
/*  93:    */     }
/*  94:116 */     validarCuentas(listaProveedor, hashMapDatosBanco);
/*  95:118 */     for (Object[] object : listaDireccionTelefonoProveedor)
/*  96:    */     {
/*  97:119 */       idProveedor = Integer.parseInt(object[0].toString());
/*  98:120 */       hashMapDatosDireccion.put(Integer.valueOf(idProveedor), object);
/*  99:    */     }
/* 100:123 */     validarDirecciones(listaProveedor, hashMapDatosDireccion);
/* 101:    */     
/* 102:125 */     int secuencial = 1;
/* 103:126 */     int secuencial2 = 1;
/* 104:127 */     if ((codigoBanco.equals("10")) || (nombreBanco.toLowerCase().contains("pichincha"))) {
/* 105:128 */       listaPagoCashProveedoresParaBancoPichincha(listaProveedor, pagoCash, cuentaBancariaEmpresa, secuencial, formaPagoEmpresa, codigoBanco, tipoCuenta, "Norte", listaPagoCashManagementProveedor, hashMapDatosBanco, hashMapDatosDireccion);
/* 106:    */     }
/* 107:131 */     if ((codigoBanco.equals("30")) || (nombreBanco.toLowerCase().contains("pacifico"))) {
/* 108:132 */       listaPagoCashProveedoresParaBancoPacifico(listaProveedor, listaPagoCashManagementProveedor, pagoCash.getTipoServicioCuentaBancaria(), hashMapDatosBanco);
/* 109:    */     }
/* 110:136 */     if ((codigoBanco.equals("36")) || (nombreBanco.toLowerCase().contains("produbanco"))) {
/* 111:137 */       listaPagoCashProveedoresParaBancoProdubanco(listaProveedor, cuentaBancariaEmpresa, secuencial, formaPagoEmpresa, codigoBanco, tipoCuenta, listaPagoCashManagementProveedor, secuencial2, hashMapDatosBanco, hashMapDatosDireccion);
/* 112:    */     }
/* 113:141 */     if ((codigoBanco.equals("32")) || (nombreBanco.toLowerCase().contains("internacional"))) {
/* 114:142 */       listaPagoCashProveedoresParaBancoInternacional(listaProveedor, formaPagoEmpresa, tipoCuenta, listaPagoCashManagementProveedor, hashMapDatosBanco);
/* 115:    */     }
/* 116:146 */     if ((codigoBanco.equals("34")) || (codigoBanco.equals("37")) || (nombreBanco.toLowerCase().contains("bolivariano"))) {
/* 117:147 */       listaPagoCashProveedoresParaBancoBolivariano(listaProveedor, secuencial2, formaPagoEmpresa, listaPagoCashManagementProveedor, pagoCash, hashMapDatosBanco, hashMapDatosDireccion);
/* 118:    */     }
/* 119:151 */     if ((codigoBanco.equals("17")) || (nombreBanco.toLowerCase().contains("guayaquil"))) {
/* 120:152 */       listaPagoCashProveedoresParaBancoGuayaquil(listaProveedor, secuencial, formaPagoEmpresa, listaPagoCashManagementProveedor, pagoCash, hashMapDatosBanco, hashMapDatosDireccion);
/* 121:    */     }
/* 122:157 */     return listaPagoCashManagementProveedor;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private List<Object[]> getListaProveedoresCash(PagoCash pagoCash)
/* 126:    */   {
/* 127:168 */     List<Object[]> lista = new ArrayList();
/* 128:169 */     Object[] datos = new Object[7];
/* 129:170 */     Map<Integer, DetallePagoCash> mapaDetallePagoCash = new HashMap();
/* 130:171 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash()) {
/* 131:172 */       if (detallePagoCash.isIndicadorAprobado()) {
/* 132:173 */         if (!mapaDetallePagoCash.containsKey(Integer.valueOf(detallePagoCash.getProveedor().getEmpresa().getIdEmpresa())))
/* 133:    */         {
/* 134:174 */           mapaDetallePagoCash.put(Integer.valueOf(detallePagoCash.getProveedor().getEmpresa().getIdEmpresa()), detallePagoCash);
/* 135:    */         }
/* 136:    */         else
/* 137:    */         {
/* 138:176 */           DetallePagoCash dpc = (DetallePagoCash)mapaDetallePagoCash.get(Integer.valueOf(detallePagoCash.getProveedor().getEmpresa().getIdEmpresa()));
/* 139:177 */           dpc.setValor(dpc.getValor().add(detallePagoCash.getValor()));
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:181 */     for (DetallePagoCash detallePagoCash : mapaDetallePagoCash.values())
/* 144:    */     {
/* 145:182 */       BigDecimal montoMaximo = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getMontoLimitePagoCash();
/* 146:183 */       BigDecimal valorPago = detallePagoCash.getValor();
/* 147:187 */       while (valorPago.compareTo(BigDecimal.ZERO) > 0)
/* 148:    */       {
/* 149:188 */         BigDecimal valorLinea = (valorPago.compareTo(montoMaximo) > 0 ? montoMaximo : valorPago).setScale(2);
/* 150:189 */         valorPago = valorPago.subtract(valorLinea);
/* 151:190 */         datos = new Object[13];
/* 152:191 */         datos[0] = Integer.valueOf(detallePagoCash.getProveedor().getEmpresa().getIdEmpresa());
/* 153:192 */         datos[1] = detallePagoCash.getProveedor().getEmpresa().getIdentificacion();
/* 154:193 */         datos[2] = detallePagoCash.getValor().multiply(new BigDecimal(100));
/* 155:194 */         datos[3] = detallePagoCash.getProveedor().getEmpresa().getTipoIdentificacion().getCodigo();
/* 156:195 */         datos[4] = detallePagoCash.getProveedor().getEmpresa().getNombreFiscal();
/* 157:196 */         datos[5] = detallePagoCash.getProveedor().getEmpresa().getEmail1();
/* 158:197 */         datos[6] = detallePagoCash.getDescripcion();
/* 159:198 */         datos[7] = numeroCiudad(pagoCash);
/* 160:199 */         datos[8] = valorPagoCash(new DetallePagoCash(valorLinea));
/* 161:200 */         datos[9] = identificacionProveedor(detallePagoCash, false);
/* 162:201 */         datos[10] = nombreProveedor(detallePagoCash).trim();
/* 163:202 */         datos[11] = identificacionProveedor(detallePagoCash, true);
/* 164:203 */         if (detallePagoCash.getCuentaPorPagar() != null) {
/* 165:204 */           datos[12] = detallePagoCash.getCuentaPorPagar().getFacturaProveedor().getNumero();
/* 166:    */         }
/* 167:206 */         lista.add(datos);
/* 168:    */       }
/* 169:    */     }
/* 170:229 */     return lista;
/* 171:    */   }
/* 172:    */   
/* 173:    */   private void listaPagoCashProveedoresParaBancoGuayaquil(List<Object[]> listaProveedor, int secuencial, String formaPagoEmpresa, List<Object[]> listaPagoCashManagementProveedor, PagoCash pagoCash, HashMap<Integer, Object[]> hashMapDatosBanco, HashMap<Integer, Object[]> hashMapDatosDireccion)
/* 174:    */   {
/* 175:235 */     for (Object[] object : listaProveedor)
/* 176:    */     {
/* 177:237 */       int idProv = Integer.parseInt(object[0].toString());
/* 178:238 */       Object[] datosBanco = (Object[])hashMapDatosBanco.get(Integer.valueOf(idProv));
/* 179:239 */       Object[] datosDireccion = (Object[])hashMapDatosDireccion.get(Integer.valueOf(idProv));
/* 180:240 */       String valorAPagar = object[8].toString();
/* 181:    */       
/* 182:242 */       Object[] datos = new Object[31];
/* 183:243 */       datos[0] = "PA";
/* 184:244 */       String ctaOrigen = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero().trim();
/* 185:245 */       datos[1] = FuncionesUtiles.completarALaIzquierda('0', 10, ctaOrigen);
/* 186:    */       
/* 187:247 */       datos[2] = FuncionesUtiles.completarALaDerecha(' ', 7, String.valueOf(secuencial));
/* 188:248 */       secuencial++;
/* 189:    */       
/* 190:250 */       datos[3] = FuncionesUtiles.completarALaDerecha(' ', 20, pagoCash.getNumero());
/* 191:    */       
/* 192:    */ 
/* 193:253 */       String numeroCuentaDestino = datosBanco[1].toString().trim();
/* 194:254 */       datos[4] = FuncionesUtiles.completarALaDerecha(' ', 20, numeroCuentaDestino);
/* 195:255 */       datos[5] = "USD";
/* 196:    */       
/* 197:257 */       datos[6] = FuncionesUtiles.completarALaIzquierda('0', 13, valorAPagar);
/* 198:    */       
/* 199:259 */       datos[7] = "CTA";
/* 200:    */       
/* 201:    */ 
/* 202:262 */       datos[8] = FuncionesUtiles.completarALaIzquierda('0', 4, datosBanco[3].toString());
/* 203:    */       
/* 204:    */ 
/* 205:265 */       datos[9] = datosBanco[2].toString();
/* 206:    */       
/* 207:    */ 
/* 208:268 */       numeroCuentaDestino = FuncionesUtiles.completarALaIzquierda('0', 10, numeroCuentaDestino);
/* 209:269 */       datos[10] = FuncionesUtiles.completarALaDerecha(' ', 30, numeroCuentaDestino);
/* 210:    */       
/* 211:271 */       datos[11] = object[3].toString().trim();
/* 212:    */       
/* 213:273 */       String identificacion = object[1].toString().trim();
/* 214:274 */       datos[12] = FuncionesUtiles.completarALaDerecha(' ', 13, identificacion.length() > 13 ? identificacion.substring(0, 13) : identificacion);
/* 215:    */       
/* 216:    */ 
/* 217:277 */       String nombreProveedor = object[4].toString().trim();
/* 218:278 */       datos[13] = FuncionesUtiles.completarALaDerecha(' ', 40, nombreProveedor
/* 219:279 */         .length() > 40 ? nombreProveedor.substring(0, 40) : nombreProveedor);
/* 220:    */       
/* 221:281 */       String direccionProveedor = datosDireccion[1].toString();
/* 222:282 */       datos[14] = FuncionesUtiles.completarALaDerecha(' ', 40, direccionProveedor
/* 223:283 */         .length() > 40 ? direccionProveedor.substring(0, 40) : direccionProveedor);
/* 224:    */       
/* 225:285 */       datos[15] = FuncionesUtiles.completarALaDerecha(' ', 20, "");
/* 226:286 */       datos[16] = FuncionesUtiles.completarALaDerecha(' ', 20, "");
/* 227:287 */       datos[17] = FuncionesUtiles.completarALaDerecha(' ', 20, "");
/* 228:288 */       if (object[12] != null)
/* 229:    */       {
/* 230:289 */         String referencia = object[12].toString();
/* 231:290 */         datos[18] = FuncionesUtiles.completarALaDerecha(' ', 200, referencia.length() > 200 ? referencia.substring(0, 200) : referencia);
/* 232:    */       }
/* 233:    */       else
/* 234:    */       {
/* 235:292 */         datos[18] = FuncionesUtiles.completarALaDerecha(' ', 200, "");
/* 236:    */       }
/* 237:294 */       datos[19] = FuncionesUtiles.completarALaDerecha(' ', 100, "");
/* 238:    */       
/* 239:296 */       Object[] datos2 = new Object[1];
/* 240:297 */       datos2[0] = (datos[0] + "\t" + datos[1] + "\t" + datos[2] + "\t" + datos[3] + "\t" + datos[4] + "\t" + datos[5] + "\t" + datos[6] + "\t" + datos[7] + "\t" + datos[8] + "\t" + datos[9] + "\t" + datos[10] + "\t" + datos[11] + "\t" + datos[12] + "\t" + datos[13] + "\t" + datos[14] + "\t" + datos[15] + "\t" + datos[16] + "\t" + datos[17] + "\t" + datos[18] + "\t" + datos[19]);
/* 241:    */       
/* 242:    */ 
/* 243:    */ 
/* 244:301 */       listaPagoCashManagementProveedor.add(datos2);
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   private void listaPagoCashProveedoresParaBancoPichincha(List<Object[]> listaProveedor, PagoCash pagoCash, String cuentaBancariaEmpresa, int secuencial, String formaPagoEmpresa, String codigoBanco, String tipoCuenta, String sector, List<Object[]> listaPagoCashManagementProveedor, HashMap<Integer, Object[]> hashMapDatosBanco, HashMap<Integer, Object[]> hashMapDatosDireccion)
/* 249:    */   {
/* 250:309 */     for (Object[] object : listaProveedor)
/* 251:    */     {
/* 252:311 */       String referencia = " ";
/* 253:    */       
/* 254:313 */       referencia = pagoCash.getDocumento().getNombre() + " " + pagoCash.getFechaPago() + " " + object[6].toString() != null ? object[6].toString() : "";
/* 255:314 */       int idProv = Integer.parseInt(object[0].toString());
/* 256:315 */       String valorAPagar = object[8].toString();
/* 257:    */       
/* 258:    */ 
/* 259:318 */       Object[] datosBanco = (Object[])hashMapDatosBanco.get(Integer.valueOf(idProv));
/* 260:319 */       Object[] datosDireccion = (Object[])hashMapDatosDireccion.get(Integer.valueOf(idProv));
/* 261:    */       Object[] datos;
/* 262:320 */       if (ParametrosSistema.isPagoCashProveedorShort(pagoCash.getIdOrganizacion()).booleanValue())
/* 263:    */       {
/* 264:321 */         Object[] datos = new Object[12];
/* 265:322 */         datos[0] = "PA";
/* 266:323 */         datos[1] = object[1];
/* 267:324 */         datos[2] = "USD";
/* 268:325 */         datos[3] = valorAPagar;
/* 269:326 */         datos[4] = "CTA";
/* 270:    */         
/* 271:328 */         datos[5] = datosBanco[2];
/* 272:329 */         datos[6] = datosBanco[1];
/* 273:330 */         datos[7] = referencia;
/* 274:331 */         datos[8] = object[3];
/* 275:332 */         datos[9] = object[1];
/* 276:333 */         datos[10] = object[4];
/* 277:334 */         datos[11] = ("00" + datosBanco[3]);
/* 278:    */       }
/* 279:    */       else
/* 280:    */       {
/* 281:337 */         datos = new Object[20];
/* 282:338 */         datos[0] = "PA";
/* 283:339 */         datos[1] = cuentaBancariaEmpresa;
/* 284:340 */         datos[2] = Integer.valueOf(secuencial++);
/* 285:341 */         datos[3] = "10A";
/* 286:342 */         datos[4] = object[1];
/* 287:343 */         datos[5] = "USD";
/* 288:344 */         datos[6] = valorAPagar;
/* 289:345 */         datos[7] = formaPagoEmpresa;
/* 290:346 */         datos[8] = ("00" + datosBanco[3]);
/* 291:347 */         datos[9] = datosBanco[2];
/* 292:348 */         datos[10] = datosBanco[1];
/* 293:349 */         datos[11] = object[3];
/* 294:350 */         datos[12] = object[1];
/* 295:351 */         datos[13] = object[4];
/* 296:352 */         datos[14] = (datosDireccion[1] != null ? datosDireccion[1].toString() : "");
/* 297:353 */         datos[15] = (datosDireccion[3] != null ? datosDireccion[3].toString() : "");
/* 298:354 */         datos[16] = (datosDireccion[2] != null ? datosDireccion[2].toString() : "");
/* 299:355 */         datos[17] = sector;
/* 300:356 */         datos[18] = referencia;
/* 301:357 */         datos[19] = ("|" + object[5] + "|");
/* 302:    */       }
/* 303:359 */       listaPagoCashManagementProveedor.add(datos);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   private void listaPagoCashProveedoresParaBancoPacifico(List<Object[]> listaProveedor, List<Object[]> listaPagoCashManagementProveedor, TipoServicioCuentaBancariaEnum tipoServicio, HashMap<Integer, Object[]> hashMapDatosBanco)
/* 308:    */     throws AS2Exception
/* 309:    */   {
/* 310:365 */     if (tipoServicio == null) {
/* 311:366 */       throw new AS2Exception("com.asinfo.as2.financiero.pagos.reportes.servicio.impl.ServicioReportePagoProveedorImpl.SELECCIONAR_TIPO_SERVICIO", new String[] { "" });
/* 312:    */     }
/* 313:369 */     for (Object[] object : listaProveedor)
/* 314:    */     {
/* 315:371 */       int idProv = Integer.parseInt(object[0].toString());
/* 316:    */       
/* 317:373 */       Object[] datosBanco = (Object[])hashMapDatosBanco.get(Integer.valueOf(idProv));
/* 318:375 */       if (tipoServicio.equals(TipoServicioCuentaBancariaEnum.RU))
/* 319:    */       {
/* 320:376 */         Object[] datos = new Object[17];
/* 321:377 */         datos[0] = object[7];
/* 322:378 */         datos[1] = "OCP";
/* 323:379 */         datos[2] = datosBanco[4].toString();
/* 324:380 */         datos[3] = tipoCuentaBancariaProveedor(datosBanco[2]);
/* 325:381 */         datos[4] = "00000000";
/* 326:382 */         datos[5] = FuncionesUtiles.completarALaIzquierda('0', 15, object[8].toString());
/* 327:383 */         datos[6] = object[9];
/* 328:384 */         datos[7] = "PAGO PACIFICO       ";
/* 329:385 */         datos[8] = "CU";
/* 330:386 */         datos[9] = "USD";
/* 331:387 */         String b = remove1(object[10].toString().trim().toUpperCase());
/* 332:388 */         datos[10] = (b.length() > 30 ? b.substring(0, 30) : FuncionesUtiles.completarALaDerecha(' ', 30, b));
/* 333:389 */         datos[11] = "    ";
/* 334:390 */         datos[12] = object[3];
/* 335:391 */         datos[13] = object[11];
/* 336:392 */         datos[14] = "                                                            ";
/* 337:393 */         datos[15] = datosBanco[3];
/* 338:394 */         datos[16] = FuncionesUtiles.completarALaDerecha(' ', 20, datosBanco[1].toString());
/* 339:395 */         Object[] datos2 = new Object[1];
/* 340:396 */         datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8] + "" + datos[9] + "" + datos[10] + "" + datos[11] + "" + datos[12] + "" + datos[13] + "" + datos[14] + "" + datos[15] + "" + datos[16]);
/* 341:    */         
/* 342:    */ 
/* 343:399 */         listaPagoCashManagementProveedor.add(datos2);
/* 344:    */       }
/* 345:    */       else
/* 346:    */       {
/* 347:401 */         Object[] datos = new Object[15];
/* 348:    */         
/* 349:403 */         datos[0] = object[7];
/* 350:    */         
/* 351:405 */         datos[1] = "OCP";
/* 352:    */         
/* 353:407 */         datos[2] = datosBanco[4];
/* 354:    */         
/* 355:409 */         datos[3] = tipoCuentaBancariaProveedor(datosBanco[2]);
/* 356:    */         
/* 357:411 */         datos[4] = FuncionesUtiles.completarALaIzquierda('0', 8, datosBanco[1].toString());
/* 358:    */         
/* 359:413 */         datos[5] = FuncionesUtiles.completarALaIzquierda('0', 15, object[8].toString());
/* 360:    */         
/* 361:415 */         datos[6] = FuncionesUtiles.completarALaDerecha(' ', 15, object[1].toString());
/* 362:    */         
/* 363:417 */         datos[7] = "PAGO PACIFICO       ";
/* 364:418 */         String a = "CU";
/* 365:419 */         if ((datosBanco[1].toString().trim().equals("CH")) || (datosBanco[1].toString().trim().equals("EF")))
/* 366:    */         {
/* 367:420 */           a = datosBanco[1].toString().trim();
/* 368:421 */           datos[3] = "  ";
/* 369:422 */           datos[4] = "        ";
/* 370:    */         }
/* 371:425 */         datos[8] = a;
/* 372:    */         
/* 373:427 */         datos[9] = "USD";
/* 374:428 */         String b = remove1(object[10].toString().trim().toUpperCase());
/* 375:429 */         datos[10] = (b.length() > 30 ? b.substring(0, 30) : FuncionesUtiles.completarALaDerecha(' ', 30, b));
/* 376:    */         
/* 377:    */ 
/* 378:432 */         datos[11] = "    ";
/* 379:    */         
/* 380:434 */         datos[12] = object[3];
/* 381:    */         
/* 382:436 */         datos[13] = object[11];
/* 383:    */         
/* 384:438 */         datos[14] = "          ";
/* 385:439 */         Object[] datos2 = new Object[1];
/* 386:440 */         datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8] + "" + datos[9] + "" + datos[10] + "" + datos[11] + "" + datos[12] + "" + datos[13] + "" + datos[14]);
/* 387:    */         
/* 388:442 */         listaPagoCashManagementProveedor.add(datos2);
/* 389:    */       }
/* 390:    */     }
/* 391:    */   }
/* 392:    */   
/* 393:    */   private void listaPagoCashProveedoresParaBancoProdubanco(List<Object[]> listaProveedor, String cuentaBancariaEmpresa, int secuencial, String formaPagoEmpresa, String codigoBanco, String tipoCuenta, List<Object[]> listaPagoCashManagementProveedor, int secuencial2, HashMap<Integer, Object[]> hashMapDatosBanco, HashMap<Integer, Object[]> hashMapDatosDireccion)
/* 394:    */   {
/* 395:452 */     for (Object[] object : listaProveedor)
/* 396:    */     {
/* 397:453 */       int idProv = Integer.parseInt(object[0].toString());
/* 398:454 */       Object[] datosBanco = (Object[])hashMapDatosBanco.get(Integer.valueOf(idProv));
/* 399:455 */       Object[] datosDireccion = (Object[])hashMapDatosDireccion.get(Integer.valueOf(idProv));
/* 400:456 */       String valorAPagar = object[8].toString();
/* 401:    */       
/* 402:458 */       Object[] datos = new Object[17];
/* 403:459 */       datos[0] = "PA";
/* 404:460 */       datos[1] = FuncionesUtiles.completarALaIzquierda('0', 11, cuentaBancariaEmpresa);
/* 405:461 */       datos[2] = Integer.valueOf(secuencial++);
/* 406:462 */       datos[3] = Integer.valueOf(secuencial2++);
/* 407:463 */       datos[4] = datosBanco[1].toString().trim();
/* 408:464 */       datos[5] = "USD";
/* 409:465 */       datos[6] = FuncionesUtiles.completarALaIzquierda('0', 13, valorAPagar.toString());
/* 410:466 */       datos[7] = formaPagoEmpresa;
/* 411:467 */       datos[8] = FuncionesUtiles.completarALaIzquierda('0', 4, datosBanco[3].toString().trim());
/* 412:468 */       datos[9] = datosBanco[2];
/* 413:469 */       datos[10] = datosBanco[1].toString().trim();
/* 414:470 */       datos[11] = object[3].toString().trim();
/* 415:471 */       datos[12] = object[1].toString().trim();
/* 416:472 */       String b = remove1(object[10].toString().trim().toUpperCase());
/* 417:473 */       datos[13] = (b.length() > 40 ? b.substring(0, 40) : b);
/* 418:474 */       String c = remove1(datosDireccion[1].toString().trim().toUpperCase());
/* 419:475 */       datos[14] = (c.length() > 40 ? c.substring(0, 40) : c);
/* 420:476 */       datos[15] = "PROVEEDORES BIENES";
/* 421:477 */       datos[16] = object[5];
/* 422:    */       
/* 423:479 */       Object[] datos2 = new Object[1];
/* 424:480 */       datos2[0] = (datos[0] + "\t" + datos[1] + "\t" + datos[2] + "\t" + datos[3] + "\t" + datos[4] + "\t" + datos[5] + "\t" + datos[6] + "\t" + datos[7] + "\t" + datos[8] + "\t" + datos[9] + "\t" + datos[10] + "\t" + datos[11] + "\t" + datos[12] + "\t" + datos[13] + "\t\t\t\t\t" + datos[14]);
/* 425:    */       
/* 426:    */ 
/* 427:    */ 
/* 428:484 */       listaPagoCashManagementProveedor.add(datos2);
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   private void listaPagoCashProveedoresParaBancoInternacional(List<Object[]> listaProveedor, String formaPagoEmpresa, String tipoCuenta, List<Object[]> listaPagoCashManagementProveedor, HashMap<Integer, Object[]> hashMapDatosBanco)
/* 433:    */   {
/* 434:490 */     for (Object[] object : listaProveedor)
/* 435:    */     {
/* 436:492 */       int idProv = Integer.parseInt(object[0].toString());
/* 437:493 */       Object[] datosBanco = (Object[])hashMapDatosBanco.get(Integer.valueOf(idProv));
/* 438:    */       
/* 439:495 */       String valorAPagar = object[8].toString();
/* 440:    */       
/* 441:497 */       Object[] datos = new Object[12];
/* 442:498 */       datos[0] = "PA";
/* 443:499 */       datos[1] = datosBanco[1].toString().trim();
/* 444:500 */       datos[2] = "USD";
/* 445:501 */       datos[3] = FuncionesUtiles.completarALaIzquierda('0', 13, valorAPagar);
/* 446:502 */       datos[4] = formaPagoEmpresa;
/* 447:503 */       datos[5] = datosBanco[2].toString().trim();
/* 448:504 */       datos[6] = datosBanco[1].toString().trim().trim();
/* 449:505 */       datos[7] = "PAGOS PROVEEDORES";
/* 450:506 */       datos[8] = object[3].toString().trim();
/* 451:507 */       datos[9] = object[1];
/* 452:508 */       String b = remove1(object[10].toString().trim().toUpperCase());
/* 453:509 */       datos[10] = (b.length() > 41 ? b.substring(0, 41) : b);
/* 454:510 */       datos[11] = datosBanco[3];
/* 455:    */       
/* 456:512 */       Object[] datos2 = new Object[1];
/* 457:513 */       datos2[0] = (datos[0] + "\t" + datos[1] + "\t" + datos[2] + "\t" + datos[3] + "\t" + datos[4] + "\t" + datos[5] + "\t" + datos[6] + "\t" + datos[7] + "\t" + datos[8] + "\t" + datos[9] + "\t" + datos[10] + "\t" + datos[11]);
/* 458:    */       
/* 459:515 */       listaPagoCashManagementProveedor.add(datos2);
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   private void listaPagoCashProveedoresParaBancoBolivariano(List<Object[]> listaProveedor, int secuencial, String formaPagoEmpresa, List<Object[]> listaPagoCashManagementProveedor, PagoCash pagoCash, HashMap<Integer, Object[]> hashMapDatosBanco, HashMap<Integer, Object[]> hashMapDatosDireccion)
/* 464:    */   {
/* 465:522 */     for (Object[] object : listaProveedor)
/* 466:    */     {
/* 467:524 */       int idProv = Integer.parseInt(object[0].toString());
/* 468:525 */       Object[] datosBanco = (Object[])hashMapDatosBanco.get(Integer.valueOf(idProv));
/* 469:526 */       Object[] datosDireccion = (Object[])hashMapDatosDireccion.get(Integer.valueOf(idProv));
/* 470:527 */       String valorAPagar = object[8].toString();
/* 471:    */       
/* 472:529 */       Object[] datos = new Object[31];
/* 473:530 */       datos[0] = "BZDET";
/* 474:531 */       datos[1] = FuncionesUtiles.completarALaIzquierda('0', 6, Integer.toString(secuencial++));
/* 475:532 */       datos[2] = (datosBanco[1].toString().trim().length() > 18 ? datosBanco[1].toString().trim().substring(0, 18) : 
/* 476:533 */         FuncionesUtiles.completarALaIzquierda(' ', 18, datosBanco[1].toString()));
/* 477:534 */       datos[3] = object[3].toString().trim();
/* 478:535 */       datos[4] = FuncionesUtiles.completarALaIzquierda(' ', 14, object[1].toString());
/* 479:536 */       String b = remove1(object[10].toString().trim().toUpperCase());
/* 480:537 */       datos[5] = (b.length() > 60 ? b.substring(0, 60) : FuncionesUtiles.completarALaIzquierda(' ', 60, b));
/* 481:538 */       datos[6] = (formaPagoEmpresa.length() > 3 ? formaPagoEmpresa.substring(0, 3) : formaPagoEmpresa);
/* 482:539 */       datos[7] = "001";
/* 483:540 */       String codigoBanco = datosBanco[3].toString();
/* 484:541 */       datos[8] = 
/* 485:542 */         (formaPagoEmpresa.equals("COB") ? codigoBanco : formaPagoEmpresa.equals("CUE") ? "34" : FuncionesUtiles.completarALaIzquierda(' ', 2, ""));
/* 486:    */       
/* 487:544 */       String codigoTipoCuentaBancaria = datosBanco[2].toString().trim().toUpperCase().equals("CTE") ? "03" : datosBanco[2].toString().trim().toUpperCase().equals("AHO") ? "04" : FuncionesUtiles.completarALaIzquierda(' ', 2, "");
/* 488:545 */       datos[9] = ((formaPagoEmpresa.equals("CUE")) || (formaPagoEmpresa.equals("COB")) || (formaPagoEmpresa.equals("IMP")) ? codigoTipoCuentaBancaria : 
/* 489:546 */         FuncionesUtiles.completarALaIzquierda(' ', 2, ""));
/* 490:547 */       datos[10] = ((formaPagoEmpresa.equals("CUE")) || (formaPagoEmpresa.equals("COB")) || (formaPagoEmpresa.equals("IMP")) ? 
/* 491:548 */         FuncionesUtiles.completarALaIzquierda('0', 20, datosBanco[1].toString().trim()) : 
/* 492:549 */         FuncionesUtiles.completarALaIzquierda(' ', 20, ""));
/* 493:    */       
/* 494:551 */       datos[11] = "1";
/* 495:552 */       datos[12] = FuncionesUtiles.completarALaIzquierda('0', 15, valorAPagar);
/* 496:553 */       datos[13] = FuncionesUtiles.completarALaIzquierda(' ', 60, "PAGO PROVEEDOR");
/* 497:    */       
/* 498:555 */       Secuencia secuencia = pagoCash.getDocumento().getSecuencia();
/* 499:556 */       String numeroString = pagoCash.getNumero();
/* 500:557 */       if ((secuencia.getPrefijo() != null) && (!secuencia.getPrefijo().isEmpty()))
/* 501:    */       {
/* 502:558 */         String[] cadena = numeroString.split(secuencia.getPrefijo());
/* 503:559 */         numeroString = cadena[1];
/* 504:    */       }
/* 505:561 */       if ((secuencia.getSufijo() != null) && (!secuencia.getSufijo().isEmpty()))
/* 506:    */       {
/* 507:562 */         String[] cadena = numeroString.split(secuencia.getSufijo());
/* 508:563 */         numeroString = cadena[0];
/* 509:    */       }
/* 510:565 */       Integer numero = Integer.valueOf(numeroString);
/* 511:    */       
/* 512:567 */       datos[14] = FuncionesUtiles.completarALaIzquierda('0', 15, Integer.toString(numero.intValue()));
/* 513:568 */       datos[15] = "000000000000000";
/* 514:569 */       datos[16] = "000000000000000";
/* 515:570 */       datos[17] = "00000000000000000000";
/* 516:571 */       datos[18] = "          ";
/* 517:572 */       datos[19] = "                                                  ";
/* 518:573 */       String c = remove1(datosDireccion[1].toString().trim().toUpperCase());
/* 519:574 */       datos[20] = (c.length() > 50 ? c.substring(0, 50) : FuncionesUtiles.completarALaIzquierda(' ', 50, c));
/* 520:575 */       datos[21] = FuncionesUtiles.completarALaIzquierda(' ', 20, datosDireccion[2].toString().trim());
/* 521:576 */       datos[22] = "PRO";
/* 522:577 */       datos[23] = "          ";
/* 523:578 */       datos[24] = "          ";
/* 524:579 */       datos[25] = "          ";
/* 525:580 */       datos[26] = " ";
/* 526:581 */       datos[27] = (pagoCash.getCuentaBancariaOrganizacion().getDescripcion() == null ? "" : 
/* 527:582 */         FuncionesUtiles.completarALaIzquierda(' ', 5, pagoCash.getCuentaBancariaOrganizacion().getDescripcion().trim()));
/* 528:583 */       datos[28] = "      ";
/* 529:584 */       datos[29] = "RPA";
/* 530:585 */       datos[30] = "          ";
/* 531:    */       
/* 532:587 */       Object[] datos2 = new Object[1];
/* 533:588 */       datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8] + "" + datos[9] + "" + datos[10] + "" + datos[11] + "" + datos[12] + "" + datos[13] + "" + datos[14] + "" + datos[15] + "" + datos[16] + "" + datos[17] + "" + datos[18] + "" + datos[19] + "" + datos[20] + "" + datos[21] + "" + datos[22] + "" + datos[23] + "" + datos[24] + "" + datos[25] + "" + datos[26] + "" + datos[27] + "" + datos[28] + "" + datos[29] + "" + datos[30]);
/* 534:    */       
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:593 */       listaPagoCashManagementProveedor.add(datos2);
/* 539:    */     }
/* 540:    */   }
/* 541:    */   
/* 542:    */   public String numeroCiudad(PagoCash pagoCash)
/* 543:    */   {
/* 544:598 */     return pagoCash.getCuentaBancariaOrganizacion().getLocalidad();
/* 545:    */   }
/* 546:    */   
/* 547:    */   public String tipoCuentaBancariaProveedor(Object tipoCuenta)
/* 548:    */   {
/* 549:603 */     String a = "01";
/* 550:604 */     if (tipoCuenta.toString().toUpperCase().equals("AHO")) {
/* 551:605 */       a = "10";
/* 552:606 */     } else if (tipoCuenta.toString().toUpperCase().equals("CTE")) {
/* 553:607 */       a = "00";
/* 554:    */     }
/* 555:609 */     return a;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public String valorPagoCash(DetallePagoCash detallePagoCash)
/* 559:    */   {
/* 560:614 */     BigDecimal valor = detallePagoCash.getValor();
/* 561:615 */     String valorParaCash = valor.toString();
/* 562:616 */     valorParaCash = valorParaCash.replace(",", "").trim();
/* 563:617 */     valorParaCash = valorParaCash.replace(".", "").trim();
/* 564:    */     
/* 565:619 */     return valorParaCash;
/* 566:    */   }
/* 567:    */   
/* 568:    */   public String identificacionProveedor(DetallePagoCash detallePagoCash, boolean mascaraIdentificacion)
/* 569:    */   {
/* 570:624 */     String identificacion = detallePagoCash.getProveedor().getEmpresa().getIdentificacion().trim();
/* 571:626 */     if (identificacion.length() != (!mascaraIdentificacion ? 15 : 14))
/* 572:    */     {
/* 573:627 */       int a = 15 - identificacion.length();
/* 574:628 */       for (int i = 0; i < a; i++) {
/* 575:629 */         identificacion = identificacion + " ";
/* 576:    */       }
/* 577:    */     }
/* 578:632 */     return identificacion;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public String nombreProveedor(DetallePagoCash detallePagoCash)
/* 582:    */   {
/* 583:636 */     String nombreProveedor = remove1(detallePagoCash.getProveedor().getEmpresa().getNombreFiscal().toUpperCase());
/* 584:637 */     if (nombreProveedor.length() > 30)
/* 585:    */     {
/* 586:638 */       nombreProveedor = nombreProveedor.substring(0, 30);
/* 587:    */     }
/* 588:640 */     else if (nombreProveedor.length() < 30)
/* 589:    */     {
/* 590:641 */       int a = 30 - nombreProveedor.length();
/* 591:642 */       for (int i = 0; i < a; i++) {
/* 592:643 */         nombreProveedor = nombreProveedor + " ";
/* 593:    */       }
/* 594:    */     }
/* 595:647 */     return nombreProveedor;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public static String remove1(String input)
/* 599:    */   {
/* 600:651 */     String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
/* 601:652 */     String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
/* 602:653 */     String output = input;
/* 603:654 */     for (int i = 0; i < original.length(); i++) {
/* 604:655 */       output = output.replace(original.charAt(i), ascii.charAt(i));
/* 605:    */     }
/* 606:657 */     return output;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public Object[] getCuentaBancariaProveedor(Integer idProveedor)
/* 610:    */   {
/* 611:662 */     List<Integer> idProv = new ArrayList();
/* 612:663 */     List<Object[]> listaProvedor = new ArrayList();
/* 613:664 */     idProv.add(idProveedor);
/* 614:665 */     listaProvedor = this.reportePagoProveedorDao.getCuentaBancariaProveedor(idProv);
/* 615:666 */     Object[] cuentaBancaria = null;
/* 616:667 */     for (Object[] o : listaProvedor) {
/* 617:668 */       cuentaBancaria = o;
/* 618:    */     }
/* 619:670 */     return cuentaBancaria;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public void validarCuentas(List<Object[]> listaProveedor, Map<Integer, Object[]> hashMapDatosBanco)
/* 623:    */     throws AS2Exception
/* 624:    */   {
/* 625:674 */     String proveedores = "";
/* 626:676 */     for (Object[] objects : listaProveedor) {
/* 627:677 */       if (!hashMapDatosBanco.containsKey(objects[0])) {
/* 628:678 */         proveedores = proveedores + objects[4].toString() + " - ";
/* 629:    */       }
/* 630:    */     }
/* 631:681 */     if (!proveedores.isEmpty()) {
/* 632:682 */       throw new AS2Exception("com.asinfo.as2.financiero.pagos.reportes.servicio.impl.ServicioReportePagoProveedorImpl.ERROR_GENERACION_ARCHIVO_PAGO_CASH_PROVEEDOR_CUENTAS", new String[] { proveedores });
/* 633:    */     }
/* 634:    */   }
/* 635:    */   
/* 636:    */   public void validarDirecciones(List<Object[]> listaProveedor, Map<Integer, Object[]> hashMapDatosDirecciones)
/* 637:    */     throws AS2Exception
/* 638:    */   {
/* 639:687 */     String proveedores = "";
/* 640:689 */     for (Object[] objects : listaProveedor) {
/* 641:690 */       if (!hashMapDatosDirecciones.containsKey(objects[0])) {
/* 642:691 */         proveedores = proveedores + objects[4].toString() + " - ";
/* 643:    */       }
/* 644:    */     }
/* 645:694 */     if (!proveedores.isEmpty()) {
/* 646:695 */       throw new AS2Exception("com.asinfo.as2.financiero.pagos.reportes.servicio.impl.ServicioReportePagoProveedorImpl.ERROR_GENERACION_ARCHIVO_PAGO_CASH_PROVEEDOR_DIRECCIONES", new String[] { proveedores });
/* 647:    */     }
/* 648:    */   }
/* 649:    */   
/* 650:    */   public List<DetalleFormaPago> getDetalleFormaCobro(int idPago)
/* 651:    */   {
/* 652:701 */     return this.reportePagoProveedorDao.getDetalleFormaCobro(idPago);
/* 653:    */   }
/* 654:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.reportes.servicio.impl.ServicioReportePagoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */