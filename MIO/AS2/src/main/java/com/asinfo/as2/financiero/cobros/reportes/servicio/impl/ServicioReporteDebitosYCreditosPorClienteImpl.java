/*   1:    */ package com.asinfo.as2.financiero.cobros.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.reportes.financiero.cobros.ReporteDebitosYCreditosPorClienteDao;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.Sucursal;
/*   6:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteDebitosYCreditosPorCliente;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.text.SimpleDateFormat;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Collection;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.TreeMap;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ServicioReporteDebitosYCreditosPorClienteImpl
/*  24:    */   implements ServicioReporteDebitosYCreditosPorCliente
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private ReporteDebitosYCreditosPorClienteDao reporteDebitosYCreditosPorClienteDao;
/*  28:    */   
/*  29:    */   public List<Object[]> getReporteDebitosYCreditosPorCliente(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte, boolean saldosDiferenteDeCero, int idVendedor)
/*  30:    */   {
/*  31: 53 */     int columnas = 22;
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55: 77 */     Map<String, Object[]> hmReporte = new HashMap();
/*  56:    */     
/*  57:    */ 
/*  58: 80 */     List<Object[]> listaVentas = this.reporteDebitosYCreditosPorClienteDao.getVentas(idOrganizacion, sucursal, empresa, DocumentoBase.FACTURA_CLIENTE, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/*  59: 83 */     for (Iterator localIterator = listaVentas.iterator(); localIterator.hasNext();)
/*  60:    */     {
/*  61: 83 */       datos = (Object[])localIterator.next();
/*  62: 84 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[4].toString()).toString());
/*  63: 85 */       Object[] detalleReporte = new Object[columnas];
/*  64: 86 */       detalleReporte[0] = datos[0];
/*  65: 87 */       detalleReporte[1] = datos[1];
/*  66: 88 */       detalleReporte[6] = datos[2];
/*  67: 89 */       detalleReporte[7] = datos[3];
/*  68: 90 */       detalleReporte[18] = (tipoReporte == 1 ? datos[4].toString() : datos[7].toString());
/*  69: 92 */       if (tipoReporte == 2)
/*  70:    */       {
/*  71: 93 */         detalleReporte[2] = datos[4];
/*  72: 94 */         detalleReporte[3] = datos[5];
/*  73: 95 */         detalleReporte[4] = datos[6];
/*  74: 96 */         detalleReporte[19] = datos[8];
/*  75: 97 */         detalleReporte[20] = datos[9];
/*  76: 98 */         detalleReporte[21] = datos[10];
/*  77:    */       }
/*  78:101 */       hmReporte.put(clave, detalleReporte);
/*  79:    */     }
/*  80:105 */     Object listaSaldos = this.reporteDebitosYCreditosPorClienteDao.getSaldos(idOrganizacion, empresa, fechaDesde, tipoReporte, idVendedor);
/*  81:107 */     for (Object[] datos = ((List)listaSaldos).iterator(); datos.hasNext();)
/*  82:    */     {
/*  83:107 */       datos = (Object[])datos.next();
/*  84:108 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/*  85:109 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/*  86:110 */       if (detalleReporte == null)
/*  87:    */       {
/*  88:111 */         detalleReporte = new Object[columnas];
/*  89:112 */         detalleReporte[0] = datos[0];
/*  90:113 */         detalleReporte[1] = datos[1];
/*  91:114 */         detalleReporte[18] = (tipoReporte == 1 ? datos[3].toString() : datos[6].toString());
/*  92:116 */         if (tipoReporte == 2)
/*  93:    */         {
/*  94:117 */           detalleReporte[2] = datos[3];
/*  95:118 */           detalleReporte[3] = datos[4];
/*  96:119 */           detalleReporte[4] = datos[5];
/*  97:120 */           detalleReporte[21] = datos[7];
/*  98:    */         }
/*  99:123 */         hmReporte.put(clave, detalleReporte);
/* 100:    */       }
/* 101:126 */       detalleReporte[5] = datos[2];
/* 102:    */     }
/* 103:130 */     List<Object[]> listaND = this.reporteDebitosYCreditosPorClienteDao.getVentas(idOrganizacion, sucursal, empresa, DocumentoBase.NOTA_DEBITO_CLIENTE, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/* 104:133 */     for (Object[] datos = listaND.iterator(); datos.hasNext();)
/* 105:    */     {
/* 106:133 */       datos = (Object[])datos.next();
/* 107:134 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[4].toString()).toString());
/* 108:135 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 109:136 */       if (detalleReporte == null)
/* 110:    */       {
/* 111:137 */         detalleReporte = new Object[columnas];
/* 112:138 */         detalleReporte[0] = datos[0];
/* 113:139 */         detalleReporte[1] = datos[1];
/* 114:140 */         detalleReporte[18] = (tipoReporte == 1 ? datos[4].toString() : datos[7].toString());
/* 115:142 */         if (tipoReporte == 2)
/* 116:    */         {
/* 117:143 */           detalleReporte[2] = datos[4];
/* 118:144 */           detalleReporte[3] = datos[5];
/* 119:145 */           detalleReporte[4] = datos[6];
/* 120:    */         }
/* 121:148 */         hmReporte.put(clave, detalleReporte);
/* 122:    */       }
/* 123:150 */       detalleReporte[8] = ((BigDecimal)datos[2]).add((BigDecimal)datos[3]);
/* 124:    */     }
/* 125:154 */     List<Object[]> listaNC = this.reporteDebitosYCreditosPorClienteDao.getVentas(idOrganizacion, sucursal, empresa, DocumentoBase.NOTA_CREDITO_CLIENTE, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/* 126:157 */     for (Object[] datos = listaNC.iterator(); datos.hasNext();)
/* 127:    */     {
/* 128:157 */       datos = (Object[])datos.next();
/* 129:158 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[4].toString()).toString());
/* 130:159 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 131:160 */       if (detalleReporte == null)
/* 132:    */       {
/* 133:161 */         detalleReporte = new Object[columnas];
/* 134:162 */         detalleReporte[0] = datos[0];
/* 135:163 */         detalleReporte[1] = datos[1];
/* 136:164 */         detalleReporte[18] = (tipoReporte == 1 ? datos[4].toString() : datos[7].toString());
/* 137:166 */         if (tipoReporte == 2)
/* 138:    */         {
/* 139:167 */           detalleReporte[2] = datos[4];
/* 140:168 */           detalleReporte[3] = datos[5];
/* 141:169 */           detalleReporte[4] = datos[6];
/* 142:    */         }
/* 143:172 */         hmReporte.put(clave, detalleReporte);
/* 144:    */       }
/* 145:174 */       detalleReporte[9] = ((BigDecimal)datos[2]).add((BigDecimal)datos[3]);
/* 146:    */     }
/* 147:179 */     List<Object[]> listaLiquidaciones = this.reporteDebitosYCreditosPorClienteDao.getLiquidaciones(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/* 148:182 */     for (Object[] datos = listaLiquidaciones.iterator(); datos.hasNext();)
/* 149:    */     {
/* 150:182 */       datos = (Object[])datos.next();
/* 151:183 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/* 152:184 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 153:185 */       if (detalleReporte == null)
/* 154:    */       {
/* 155:186 */         detalleReporte = new Object[columnas];
/* 156:187 */         detalleReporte[0] = datos[0];
/* 157:188 */         detalleReporte[1] = datos[1];
/* 158:189 */         detalleReporte[18] = (tipoReporte == 1 ? datos[3].toString() : datos[6].toString());
/* 159:191 */         if (tipoReporte == 2)
/* 160:    */         {
/* 161:192 */           detalleReporte[2] = datos[3];
/* 162:193 */           detalleReporte[3] = datos[4];
/* 163:194 */           detalleReporte[4] = datos[5];
/* 164:    */         }
/* 165:197 */         hmReporte.put(clave, detalleReporte);
/* 166:    */       }
/* 167:199 */       detalleReporte[10] = datos[2];
/* 168:    */     }
/* 169:203 */     List<Object[]> listaCobros = this.reporteDebitosYCreditosPorClienteDao.getCobros(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, Estado.CONTABILIZADO, tipoReporte, idVendedor);
/* 170:206 */     for (Object[] datos = listaCobros.iterator(); datos.hasNext();)
/* 171:    */     {
/* 172:206 */       datos = (Object[])datos.next();
/* 173:207 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/* 174:208 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 175:209 */       if (detalleReporte == null)
/* 176:    */       {
/* 177:210 */         detalleReporte = new Object[columnas];
/* 178:211 */         detalleReporte[0] = datos[0];
/* 179:212 */         detalleReporte[1] = datos[1];
/* 180:213 */         detalleReporte[18] = (tipoReporte == 1 ? datos[3].toString() : datos[6].toString());
/* 181:215 */         if (tipoReporte == 2)
/* 182:    */         {
/* 183:216 */           detalleReporte[2] = datos[3];
/* 184:217 */           detalleReporte[3] = datos[4];
/* 185:218 */           detalleReporte[4] = datos[5];
/* 186:    */         }
/* 187:221 */         hmReporte.put(clave, detalleReporte);
/* 188:    */       }
/* 189:223 */       detalleReporte[11] = datos[2];
/* 190:    */     }
/* 191:228 */     List<Object[]> listaRetenciones = this.reporteDebitosYCreditosPorClienteDao.getRetenciones(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/* 192:231 */     for (Object[] datos = listaRetenciones.iterator(); datos.hasNext();)
/* 193:    */     {
/* 194:231 */       datos = (Object[])datos.next();
/* 195:232 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/* 196:233 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 197:234 */       if (detalleReporte == null)
/* 198:    */       {
/* 199:235 */         detalleReporte = new Object[columnas];
/* 200:236 */         detalleReporte[0] = datos[0];
/* 201:237 */         detalleReporte[1] = datos[1];
/* 202:238 */         detalleReporte[18] = (tipoReporte == 1 ? datos[3].toString() : datos[6].toString());
/* 203:240 */         if (tipoReporte == 2)
/* 204:    */         {
/* 205:241 */           detalleReporte[2] = datos[3];
/* 206:242 */           detalleReporte[3] = datos[4];
/* 207:243 */           detalleReporte[4] = datos[5];
/* 208:    */         }
/* 209:246 */         hmReporte.put(clave, detalleReporte);
/* 210:    */       }
/* 211:248 */       detalleReporte[12] = datos[2];
/* 212:    */     }
/* 213:252 */     List<Object[]> listaProtestos = this.reporteDebitosYCreditosPorClienteDao.getProtestos(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/* 214:255 */     for (Object[] datos = listaProtestos.iterator(); datos.hasNext();)
/* 215:    */     {
/* 216:255 */       datos = (Object[])datos.next();
/* 217:256 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/* 218:257 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 219:258 */       if (detalleReporte == null)
/* 220:    */       {
/* 221:259 */         detalleReporte = new Object[columnas];
/* 222:260 */         detalleReporte[0] = datos[0];
/* 223:261 */         detalleReporte[1] = datos[1];
/* 224:262 */         detalleReporte[18] = (tipoReporte == 1 ? datos[3].toString() : datos[7].toString());
/* 225:264 */         if (tipoReporte == 2)
/* 226:    */         {
/* 227:265 */           detalleReporte[2] = datos[3];
/* 228:266 */           detalleReporte[3] = datos[4];
/* 229:267 */           detalleReporte[4] = datos[6];
/* 230:    */         }
/* 231:270 */         hmReporte.put(clave, detalleReporte);
/* 232:    */       }
/* 233:272 */       detalleReporte[14] = (detalleReporte[14] == null ? BigDecimal.ZERO : (BigDecimal)detalleReporte[14]).add((BigDecimal)datos[2]);
/* 234:274 */       if (tipoReporte == 2) {
/* 235:276 */         detalleReporte[15] = ((detalleReporte[15] == null ? "" : new StringBuilder().append((String)detalleReporte[15]).append(" ").toString()) + (String)datos[5] + " " + datos[2].toString());
/* 236:    */       }
/* 237:    */     }
/* 238:    */     Object[] datos;
/* 239:281 */     List<Object[]> listaPosfechados = this.reporteDebitosYCreditosPorClienteDao.getCobros(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, Estado.ELABORADO, tipoReporte, idVendedor);
/* 240:284 */     for (Object[] datos : listaPosfechados)
/* 241:    */     {
/* 242:285 */       clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/* 243:286 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 244:287 */       if (detalleReporte == null)
/* 245:    */       {
/* 246:288 */         detalleReporte = new Object[columnas];
/* 247:289 */         detalleReporte[0] = datos[0];
/* 248:290 */         detalleReporte[1] = datos[1];
/* 249:291 */         detalleReporte[18] = (tipoReporte == 1 ? datos[3].toString() : datos[6].toString());
/* 250:293 */         if (tipoReporte == 2)
/* 251:    */         {
/* 252:294 */           detalleReporte[2] = datos[3];
/* 253:295 */           detalleReporte[3] = datos[4];
/* 254:296 */           detalleReporte[4] = datos[5];
/* 255:    */         }
/* 256:299 */         hmReporte.put(clave, detalleReporte);
/* 257:    */       }
/* 258:301 */       detalleReporte[16] = datos[2];
/* 259:    */     }
/* 260:    */     String clave;
/* 261:    */     Map<String, Object[]> hmListaUnificada;
/* 262:    */     List<Object[]> listaSaldoAnticipo;
/* 263:    */     List<Object[]> listaSaldoLiquidaciones;
/* 264:    */     Object[] datos;
/* 265:306 */     if ((tipoReporte == 1) && (idVendedor == 0))
/* 266:    */     {
/* 267:308 */       hmListaUnificada = new HashMap();
/* 268:    */       
/* 269:310 */       listaSaldoAnticipo = this.reporteDebitosYCreditosPorClienteDao.getSaldoAnticipoCliente(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/* 270:315 */       for (clave = listaSaldoAnticipo.iterator(); clave.hasNext();)
/* 271:    */       {
/* 272:315 */         datos = (Object[])clave.next();
/* 273:316 */         String clave = datos[0].toString() + "";
/* 274:317 */         Object[] valoresSaldoAnticipo = (Object[])hmListaUnificada.get(clave);
/* 275:318 */         if (valoresSaldoAnticipo == null)
/* 276:    */         {
/* 277:319 */           valoresSaldoAnticipo = new Object[5];
/* 278:320 */           valoresSaldoAnticipo[0] = datos[0];
/* 279:321 */           valoresSaldoAnticipo[1] = datos[1];
/* 280:322 */           valoresSaldoAnticipo[2] = datos[2];
/* 281:323 */           valoresSaldoAnticipo[3] = datos[3];
/* 282:324 */           valoresSaldoAnticipo[4] = BigDecimal.ZERO;
/* 283:325 */           hmListaUnificada.put(clave, valoresSaldoAnticipo);
/* 284:    */         }
/* 285:    */       }
/* 286:329 */       listaSaldoLiquidaciones = this.reporteDebitosYCreditosPorClienteDao.getSaldoLiquidaciones(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, tipoReporte, idVendedor);
/* 287:331 */       for (Object[] datos = listaSaldoLiquidaciones.iterator(); datos.hasNext();)
/* 288:    */       {
/* 289:331 */         datos = (Object[])datos.next();
/* 290:332 */         String clave = datos[0].toString() + "";
/* 291:333 */         Object[] valoresSaldoAnticipo = (Object[])hmListaUnificada.get(clave);
/* 292:334 */         if (valoresSaldoAnticipo != null)
/* 293:    */         {
/* 294:335 */           valoresSaldoAnticipo[4] = datos[2];
/* 295:336 */           hmListaUnificada.put(clave, valoresSaldoAnticipo);
/* 296:    */         }
/* 297:    */       }
/* 298:341 */       List<Object[]> listaSaldoValoresAnticipo = new ArrayList();
/* 299:342 */       listaSaldoValoresAnticipo.addAll(hmListaUnificada.values());
/* 300:344 */       for (Object[] datos : listaSaldoValoresAnticipo)
/* 301:    */       {
/* 302:345 */         String clave = datos[0].toString() + "";
/* 303:346 */         Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 304:347 */         if (detalleReporte == null)
/* 305:    */         {
/* 306:348 */           detalleReporte = new Object[columnas];
/* 307:349 */           detalleReporte[0] = datos[0];
/* 308:350 */           detalleReporte[1] = datos[1];
/* 309:    */           
/* 310:352 */           hmReporte.put(clave, detalleReporte);
/* 311:    */         }
/* 312:356 */         BigDecimal a = ((BigDecimal)datos[2]).subtract(((BigDecimal)datos[3]).add(datos[4] == null ? BigDecimal.ZERO : (BigDecimal)datos[4]));
/* 313:    */         
/* 314:358 */         detalleReporte[17] = a;
/* 315:    */       }
/* 316:    */     }
/* 317:363 */     if (saldosDiferenteDeCero)
/* 318:    */     {
/* 319:364 */       hmListaUnificada = (Object[][])hmReporte.values().toArray(new Object[hmReporte.values().size()][]);listaSaldoAnticipo = hmListaUnificada.length;
/* 320:364 */       for (listaSaldoLiquidaciones = 0; listaSaldoLiquidaciones < listaSaldoAnticipo; listaSaldoLiquidaciones++)
/* 321:    */       {
/* 322:364 */         Object[] fila = hmListaUnificada[listaSaldoLiquidaciones];
/* 323:    */         
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:375 */         BigDecimal saldo = (fila[5] == null ? BigDecimal.ZERO : (BigDecimal)fila[5]).add(fila[6] == null ? BigDecimal.ZERO : (BigDecimal)fila[6]).add(fila[7] == null ? BigDecimal.ZERO : (BigDecimal)fila[7]).add(fila[8] == null ? BigDecimal.ZERO : (BigDecimal)fila[8]).add(fila[14] == null ? BigDecimal.ZERO : (BigDecimal)fila[14]).subtract(fila[10] == null ? BigDecimal.ZERO : (BigDecimal)fila[10]).subtract(fila[11] == null ? BigDecimal.ZERO : (BigDecimal)fila[11]).subtract(fila[12] == null ? BigDecimal.ZERO : (BigDecimal)fila[12]).subtract(fila[13] == null ? BigDecimal.ZERO : (BigDecimal)fila[13]).subtract(fila[16] == null ? BigDecimal.ZERO : (BigDecimal)fila[16]);
/* 334:377 */         if (saldo.compareTo(BigDecimal.ZERO) == 0)
/* 335:    */         {
/* 336:378 */           String clave = fila[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(fila[2].toString()).toString());
/* 337:379 */           hmReporte.remove(clave);
/* 338:    */         }
/* 339:    */       }
/* 340:    */     }
/* 341:385 */     Map<String, Object[]> tmReporte = new TreeMap();
/* 342:386 */     for (Object[] fila : hmReporte.values())
/* 343:    */     {
/* 344:387 */       String clave = fila[1].toString() + "-" + fila[0].toString();
/* 345:389 */       if (tipoReporte == 2) {
/* 346:390 */         if (fila[3] != null) {
/* 347:391 */           clave = clave + "-" + new SimpleDateFormat("yyyyMMdd").format((Date)fila[3]) + "-" + fila[2].toString();
/* 348:    */         } else {
/* 349:393 */           clave = clave + "-" + fila[2].toString();
/* 350:    */         }
/* 351:    */       }
/* 352:396 */       tmReporte.put(clave, fila);
/* 353:    */     }
/* 354:399 */     List<Object[]> datosReporte = new ArrayList(tmReporte.values());
/* 355:400 */     return datosReporte;
/* 356:    */   }
/* 357:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.impl.ServicioReporteDebitosYCreditosPorClienteImpl
 * JD-Core Version:    0.7.0.1
 */