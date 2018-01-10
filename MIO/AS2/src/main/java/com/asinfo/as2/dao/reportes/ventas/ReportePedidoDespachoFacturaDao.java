/*   1:    */ package com.asinfo.as2.dao.reportes.ventas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.MotivoPedidoCliente;
/*   7:    */ import com.asinfo.as2.entities.PedidoCliente;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.Transportista;
/*  10:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ import javax.persistence.EntityManager;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class ReportePedidoDespachoFacturaDao
/*  21:    */   extends AbstractDaoAS2<DetalleFacturaCliente>
/*  22:    */ {
/*  23:    */   public ReportePedidoDespachoFacturaDao()
/*  24:    */   {
/*  25: 40 */     super(DetalleFacturaCliente.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List getReportePedidoDespachoFactura(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente, int idOrganizacion, Producto producto)
/*  29:    */     throws ExcepcionAS2
/*  30:    */   {
/*  31: 59 */     StringBuilder sql = new StringBuilder();
/*  32: 60 */     sql.append("SELECT '-', '-', dc.numero, dc.fecha, COALESCE(dpc.cantidad,0)*0, COALESCE(ddc.cantidad,0), COALESCE(dpc.cantidad,0), ");
/*  33: 61 */     sql.append(" pc.numero, pc.fecha, p.codigo, p.nombre, e.nombreFiscal, dc.idDespachoCliente, ddc.idDetalleDespachoCliente, ");
/*  34: 62 */     sql.append(" pc.idPedidoCliente, dpc.idDetallePedidoCliente ");
/*  35: 63 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/*  36: 64 */     sql.append(" RIGHT OUTER JOIN ddc.detallePedidoCliente dpc ");
/*  37: 65 */     sql.append(" LEFT OUTER JOIN ddc.detalleFacturaCliente dfc ");
/*  38: 66 */     sql.append(" INNER JOIN dpc.producto p ");
/*  39: 67 */     sql.append(" INNER JOIN dpc.pedidoCliente pc ");
/*  40: 68 */     sql.append(" INNER JOIN pc.empresa e ");
/*  41: 69 */     sql.append(" INNER JOIN ddc.despachoCliente dc ");
/*  42: 70 */     sql.append(" WHERE pc.idOrganizacion = :idOrganizacion ");
/*  43: 71 */     if (producto != null) {
/*  44: 72 */       sql.append(" AND p=:producto ");
/*  45:    */     }
/*  46: 74 */     sql.append(" AND pc IS NOT NULL ");
/*  47: 75 */     if (pedidoCliente != null) {
/*  48: 76 */       sql.append(" AND pc=:pedidoCliente ");
/*  49:    */     }
/*  50: 78 */     sql.append(" AND pc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  51: 79 */     if (cliente != null) {
/*  52: 80 */       sql.append(" AND e=:cliente");
/*  53:    */     }
/*  54: 83 */     sql.append(" AND dfc.facturaCliente IS NULL ");
/*  55:    */     
/*  56:    */ 
/*  57: 86 */     Query query = this.em.createQuery(sql.toString());
/*  58: 87 */     if (pedidoCliente != null) {
/*  59: 88 */       query.setParameter("pedidoCliente", pedidoCliente);
/*  60:    */     }
/*  61: 90 */     query.setParameter("fechaDesde", fechaDesde);
/*  62: 91 */     query.setParameter("fechaHasta", fechaHasta);
/*  63: 92 */     if (cliente != null) {
/*  64: 93 */       query.setParameter("cliente", cliente);
/*  65:    */     }
/*  66: 95 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  67: 96 */     if (producto != null) {
/*  68: 97 */       query.setParameter("proucto", producto);
/*  69:    */     }
/*  70:100 */     return query.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List getReporteDespachoSinFactura(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente, int idOrganizacion, Producto producto)
/*  74:    */     throws ExcepcionAS2
/*  75:    */   {
/*  76:118 */     StringBuilder sql = new StringBuilder();
/*  77:119 */     sql.append(" SELECT fc.numero, fc.fecha, '-', '-', COALESCE(dfc.cantidad,0), COALESCE(dpc.cantidad,0)*0, COALESCE(dpc.cantidad,0), ");
/*  78:120 */     sql.append(" pc.numero, pc.fecha, p.codigo, p.nombre, e.nombreFiscal, fc.idFacturaCliente, dfc.idDetalleFacturaCliente, ");
/*  79:121 */     sql.append(" pc.idPedidoCliente, dpc.idDetallePedidoCliente ");
/*  80:122 */     sql.append(" FROM DetalleFacturaCliente dfc ");
/*  81:123 */     sql.append(" RIGHT OUTER JOIN dfc.detallePedidoCliente dpc ");
/*  82:124 */     sql.append(" LEFT OUTER JOIN dfc.detalleDespachoCliente ddc ");
/*  83:125 */     sql.append(" INNER JOIN dpc.pedidoCliente pc ");
/*  84:126 */     sql.append(" INNER JOIN dfc.facturaCliente fc ");
/*  85:127 */     sql.append(" INNER JOIN dpc.producto p ");
/*  86:128 */     sql.append(" INNER JOIN pc.empresa e ");
/*  87:129 */     sql.append(" WHERE pc.idOrganizacion = :idOrganizacion ");
/*  88:131 */     if (producto != null) {
/*  89:132 */       sql.append(" AND p = :producto");
/*  90:    */     }
/*  91:134 */     if (pedidoCliente != null) {
/*  92:135 */       sql.append(" AND  pc=:pedidoCliente ");
/*  93:    */     }
/*  94:137 */     sql.append(" AND pc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  95:138 */     if (cliente != null) {
/*  96:139 */       sql.append(" AND e=:cliente ");
/*  97:    */     }
/*  98:141 */     sql.append(" AND ddc.despachoCliente IS NULL ");
/*  99:    */     
/* 100:143 */     Query query = this.em.createQuery(sql.toString());
/* 101:144 */     if (pedidoCliente != null) {
/* 102:145 */       query.setParameter("pedidoCliente", pedidoCliente);
/* 103:    */     }
/* 104:147 */     query.setParameter("fechaDesde", fechaDesde);
/* 105:148 */     query.setParameter("fechaHasta", fechaHasta);
/* 106:149 */     if (cliente != null) {
/* 107:150 */       query.setParameter("cliente", cliente);
/* 108:    */     }
/* 109:152 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 110:153 */     if (producto != null) {
/* 111:154 */       query.setParameter("producto", producto);
/* 112:    */     }
/* 113:157 */     return query.getResultList();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List getReportePedidoConFactura(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente, int idOrganizacion, Producto producto)
/* 117:    */     throws ExcepcionAS2
/* 118:    */   {
/* 119:175 */     StringBuilder sql = new StringBuilder();
/* 120:176 */     sql.append("SELECT fc.numero, fc.fecha, dc.numero, dc.fecha, COALESCE(dfc.cantidad,0), COALESCE(ddc.cantidad,0), ");
/* 121:177 */     sql.append(" CASE WHEN dpc.cantidad IS NULL THEN dpcf.cantidad ELSE dpc.cantidad END, ");
/* 122:178 */     sql.append(" CASE WHEN pc.numero IS NULL THEN pcf.numero ELSE pc.numero END, CASE WHEN pc.fecha IS NULL THEN pcf.fecha ELSE pc.fecha END, ");
/* 123:179 */     sql.append(" p.codigo, p.nombre, e.nombreFiscal, fc.idFacturaCliente, dfc.idDetalleFacturaCliente, ");
/* 124:180 */     sql.append(" dc.idDespachoCliente, ddc.idDetalleDespachoCliente, ");
/* 125:181 */     sql.append(" CASE WHEN pc.idPedidoCliente IS NULL THEN pcf.idPedidoCliente ELSE pc.idPedidoCliente END, dpc.idDetallePedidoCliente ");
/* 126:182 */     sql.append(" FROM DetalleFacturaCliente dfc ");
/* 127:183 */     sql.append(" INNER JOIN dfc.facturaCliente fc ");
/* 128:184 */     sql.append(" LEFT OUTER JOIN dfc.detalleDespachoCliente ddc ");
/* 129:185 */     sql.append(" LEFT OUTER JOIN fc.despachoCliente dc ");
/* 130:186 */     sql.append(" LEFT OUTER JOIN ddc.detallePedidoCliente dpc ");
/* 131:187 */     sql.append(" INNER JOIN dfc.producto p ");
/* 132:188 */     sql.append(" LEFT OUTER JOIN dc.pedidoCliente pc ");
/* 133:189 */     sql.append(" LEFT OUTER JOIN dfc.detallePedidoCliente dpcf ");
/* 134:190 */     sql.append(" LEFT OUTER JOIN dpcf.pedidoCliente pcf ");
/* 135:191 */     sql.append(" LEFT OUTER JOIN fc.empresa e ");
/* 136:192 */     sql.append(" LEFT OUTER JOIN pcf.empresa ef ");
/* 137:193 */     sql.append(" WHERE pc.idOrganizacion=:idOrganizacion ");
/* 138:194 */     if (producto != null) {
/* 139:195 */       sql.append(" AND p=:producto ");
/* 140:    */     }
/* 141:197 */     if (pedidoCliente != null) {
/* 142:198 */       sql.append(" AND pc=:pedidoCliente ");
/* 143:    */     }
/* 144:200 */     if (pedidoCliente != null) {
/* 145:201 */       sql.append(" AND pcf=:pedidoCliente ");
/* 146:    */     }
/* 147:204 */     sql.append(" AND (pc.fecha BETWEEN :fechaDesde AND :fechaHasta) ");
/* 148:205 */     sql.append(" OR (pcf.fecha BETWEEN :fechaDesde AND :fechaHasta) ");
/* 149:206 */     if (cliente != null) {
/* 150:207 */       sql.append(" AND (:cliente IS NULL OR e=:cliente) ");
/* 151:    */     }
/* 152:209 */     Query query = this.em.createQuery(sql.toString());
/* 153:210 */     if (pedidoCliente != null) {
/* 154:211 */       query.setParameter("pedidoCliente", pedidoCliente);
/* 155:    */     }
/* 156:213 */     query.setParameter("fechaDesde", fechaDesde);
/* 157:214 */     query.setParameter("fechaHasta", fechaHasta);
/* 158:215 */     if (cliente != null) {
/* 159:216 */       query.setParameter("cliente", cliente);
/* 160:    */     }
/* 161:218 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 162:219 */     if (producto != null) {
/* 163:220 */       query.setParameter("producto", producto);
/* 164:    */     }
/* 165:223 */     return query.getResultList();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List getReporteDespachoFactura(Date fechaDesde, Date fechaHasta, Empresa cliente)
/* 169:    */     throws ExcepcionAS2
/* 170:    */   {
/* 171:236 */     String sql = " SELECT fc.numero, fc.fecha, dc.numero, dc.fecha, COALESCE(dfc.cantidad,0), COALESCE(ddc.cantidad,0), p.codigo, p.nombre, e.nombreFiscal, gr.numero FROM DetalleFacturaCliente dfc  INNER JOIN dfc.facturaCliente fc  INNER JOIN fc.empresa e INNER JOIN dfc.producto p LEFT OUTER JOIN dfc.detalleDespachoCliente ddc LEFT OUTER JOIN fc.despachoCliente dc LEFT OUTER JOIN dc.guiaRemision gr WHERE dc.pedidoCliente IS NULL AND (:cliente IS NULL OR e=:cliente) AND fc.fecha BETWEEN :fechaDesde AND :fechaHasta ";
/* 172:    */     
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:243 */     Query query = this.em.createQuery(sql).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("cliente", cliente);
/* 179:    */     
/* 180:245 */     return query.getResultList();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List getReporteFacturaDespacho(Date fechaDesde, Date fechaHasta, Empresa cliente)
/* 184:    */     throws ExcepcionAS2
/* 185:    */   {
/* 186:256 */     String sql = " SELECT fc.numero, fc.fecha, dc.numero, dc.fecha, COALESCE(dfc.cantidad,0),COALESCE (ddc.cantidad,0), p.codigo,p.nombre,e.nombreFiscal, gr.numero FROM DetalleFacturaCliente dfc  INNER JOIN dfc.facturaCliente fc  INNER JOIN fc.empresa e RIGHT OUTER JOIN dfc.detalleDespachoCliente ddc INNER JOIN ddc.despachoCliente dc LEFT JOIN dc.guiaRemision gr  INNER JOIN ddc.producto p WHERE dc.pedidoCliente IS NULL AND fc IS NULL AND fc.fecha BETWEEN :fechaDesde AND :fechaHasta  AND (:cliente IS NULL OR e=:cliente)";
/* 187:    */     
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:263 */     Query query = this.em.createQuery(sql).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("cliente", cliente);
/* 194:    */     
/* 195:265 */     return query.getResultList();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List getReportePedidoResumido(int idOrganizacion, Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, int idSucursal, MotivoPedidoCliente motivoPedidoCliente, Transportista transportista, boolean indicadorTomaFecha)
/* 199:    */   {
/* 200:273 */     StringBuilder sql = new StringBuilder();
/* 201:    */     
/* 202:275 */     sql.append(" SELECT s.codigo, s.nombre, c.codigo, c.nombre, z.codigo, z.nombre, ac.codigo, ac.nombre1, ");
/* 203:276 */     sql.append(" pe.numero, pe.fecha, pe.fechaDespacho, e.nombreFiscal, e.identificacion, pe.total, pe.descuento, tra.nombre, pe.impuesto, ");
/* 204:277 */     sql.append(" p.codigo, p.codigoComercial, p.nombre, u.codigo, dp.cantidad, dp.precio, dp.descripcion, dp.descuento, se.empresaFinal");
/* 205:278 */     sql.append(" FROM DetallePedidoCliente dp ");
/* 206:279 */     sql.append(" LEFT OUTER JOIN dp.pedidoCliente pe ");
/* 207:280 */     sql.append(" LEFT OUTER JOIN pe.transportista tra ");
/* 208:281 */     sql.append(" LEFT OUTER JOIN pe.sucursal s ");
/* 209:282 */     sql.append(" LEFT OUTER JOIN pe.empresa e ");
/* 210:283 */     sql.append(" LEFT OUTER JOIN pe.subempresa se");
/* 211:284 */     sql.append(" LEFT OUTER JOIN e.cliente cl ");
/* 212:285 */     sql.append(" LEFT OUTER JOIN pe.zona z ");
/* 213:286 */     sql.append(" LEFT OUTER JOIN pe.canal c ");
/* 214:287 */     sql.append(" LEFT OUTER JOIN pe.agenteComercial ac ");
/* 215:288 */     sql.append(" LEFT OUTER JOIN dp.producto p ");
/* 216:289 */     sql.append(" LEFT OUTER JOIN p.unidad u ");
/* 217:290 */     sql.append(" WHERE pe.idOrganizacion = :idOrganizacion ");
/* 218:291 */     if (indicadorTomaFecha == true) {
/* 219:292 */       sql.append(" AND pe.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 220:    */     } else {
/* 221:294 */       sql.append(" AND pe.fechaDespacho BETWEEN :fechaDesde AND :fechaHasta ");
/* 222:    */     }
/* 223:296 */     if (idVendedor != 0) {
/* 224:297 */       sql.append(" AND ac.idUsuario = :idVendedor ");
/* 225:    */     }
/* 226:298 */     if (idSucursal != 0) {
/* 227:299 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 228:    */     }
/* 229:300 */     if (idCliente != 0) {
/* 230:301 */       sql.append(" AND e.idEmpresa = :idCliente ");
/* 231:    */     }
/* 232:302 */     if (idZona != 0) {
/* 233:303 */       sql.append(" AND z.idZona = :idZona ");
/* 234:    */     }
/* 235:304 */     if (idCanal != 0) {
/* 236:305 */       sql.append(" AND c.idCanal = :idCanal ");
/* 237:    */     }
/* 238:307 */     sql.append(" AND pe.documento.documentoBase = :documentoBase");
/* 239:309 */     if (transportista != null) {
/* 240:310 */       sql.append(" AND tra = :transportista");
/* 241:    */     }
/* 242:313 */     if (anuladas) {
/* 243:314 */       sql.append(" AND pe.estado = :estadoAnulado ");
/* 244:    */     } else {
/* 245:316 */       sql.append(" AND pe.estado != :estadoAnulado ");
/* 246:    */     }
/* 247:318 */     if (motivoPedidoCliente != null) {
/* 248:319 */       sql.append(" AND pe.motivoPedidoCliente != :motivoPedidoCliente ");
/* 249:    */     }
/* 250:321 */     sql.append(" ORDER BY pe.numero");
/* 251:    */     
/* 252:323 */     Query query = this.em.createQuery(sql.toString());
/* 253:324 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 254:325 */     if (transportista != null) {
/* 255:326 */       query.setParameter("transportista", transportista);
/* 256:    */     }
/* 257:328 */     query.setParameter("fechaDesde", fechaDesde);
/* 258:329 */     query.setParameter("fechaHasta", fechaHasta);
/* 259:330 */     if (idVendedor != 0) {
/* 260:331 */       query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/* 261:    */     }
/* 262:332 */     if (idSucursal != 0) {
/* 263:333 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 264:    */     }
/* 265:334 */     if (idCliente != 0) {
/* 266:335 */       query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 267:    */     }
/* 268:336 */     if (idZona != 0) {
/* 269:337 */       query.setParameter("idZona", Integer.valueOf(idZona));
/* 270:    */     }
/* 271:338 */     if (idCanal != 0) {
/* 272:339 */       query.setParameter("idCanal", Integer.valueOf(idCanal));
/* 273:    */     }
/* 274:340 */     query.setParameter("documentoBase", DocumentoBase.PEDIDO_CLIENTE);
/* 275:341 */     query = query.setParameter("estadoAnulado", Estado.ANULADO);
/* 276:342 */     if (motivoPedidoCliente != null) {
/* 277:343 */       query = query.setParameter("motivoPedidoCliente", motivoPedidoCliente);
/* 278:    */     }
/* 279:350 */     return query.getResultList();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public List getReporteListaPedidos(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente)
/* 283:    */     throws ExcepcionAS2
/* 284:    */   {
/* 285:365 */     StringBuffer sql = new StringBuffer();
/* 286:366 */     sql.append("SELECT pc.idPedidoCliente, pc.numero, pc.fecha, em.nombreFiscal, dpc.cantidad, dpc.precio, p.nombre, p.codigo ");
/* 287:367 */     sql.append(" FROM DetallePedidoCliente dpc ");
/* 288:368 */     sql.append(" LEFT OUTER JOIN dpc.pedidoCliente pc ");
/* 289:369 */     sql.append(" INNER JOIN pc.empresa em ");
/* 290:370 */     sql.append(" INNER JOIN dpc.producto p ");
/* 291:371 */     sql.append(" WHERE (:pedidoCliente IS NULL OR pc=:pedidoCliente) ");
/* 292:372 */     sql.append(" AND pc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 293:373 */     sql.append(" AND (:cliente IS NULL OR em=:cliente) ");
/* 294:    */     
/* 295:    */ 
/* 296:376 */     Query query = this.em.createQuery(sql.toString()).setParameter("pedidoCliente", pedidoCliente).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("cliente", cliente);
/* 297:    */     
/* 298:378 */     return query.getResultList();
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List getReporteListaDespachos(PedidoCliente pedidoCliente, Date fechaDesde, Date fechaHasta, Empresa cliente)
/* 302:    */     throws ExcepcionAS2
/* 303:    */   {
/* 304:393 */     StringBuffer sql = new StringBuffer();
/* 305:394 */     sql.append("SELECT pc.idPedidoCliente, pc.numero, dc.fecha, em.nombreFiscal, ddc.cantidad, COALESCE(dfc.precio, 0) * 0,  ");
/* 306:395 */     sql.append(" p.nombre, p.codigo ");
/* 307:396 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/* 308:397 */     sql.append(" RIGHT OUTER JOIN ddc.detallePedidoCliente dpc ");
/* 309:398 */     sql.append(" LEFT OUTER JOIN ddc.detalleFacturaCliente dfc ");
/* 310:399 */     sql.append(" INNER JOIN dpc.producto p ");
/* 311:400 */     sql.append(" INNER JOIN dpc.pedidoCliente pc ");
/* 312:401 */     sql.append(" INNER JOIN pc.empresa em ");
/* 313:402 */     sql.append(" INNER JOIN ddc.despachoCliente dc ");
/* 314:403 */     sql.append(" WHERE (:pedidoCliente IS NULL OR pc=:pedidoCliente) ");
/* 315:404 */     sql.append(" AND pc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 316:405 */     sql.append(" AND (:cliente IS NULL OR em=:cliente) ");
/* 317:    */     
/* 318:407 */     return null;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public List getReportePedidos(Date fechaDesde, Date fechaHasta, int idCliente, Producto producto, boolean indicadorElaborado)
/* 322:    */   {
/* 323:413 */     String sql = " SELECT e.nombreFiscal, tr.nombre, pc.numero, pc.fecha, p.codigo, p.nombre, dp.cantidad FROM DetallePedidoCliente dp  LEFT JOIN dp.pedidoCliente pc  LEFT JOIN pc.empresa e  LEFT  JOIN e.cliente cl  LEFT  JOIN dp.producto p LEFT  JOIN pc.transportista tr  WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta  AND (e.idEmpresa = :idCliente OR :idCliente=0)  AND (p.idProducto = :idProducto OR :idProducto=0) AND (pc.estado=:estado)";
/* 324:    */     
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:419 */     sql = sql + " ORDER BY e.nombreFiscal, tr.nombre, pc.numero, pc.fecha";
/* 330:    */     
/* 331:421 */     Query query = this.em.createQuery(sql);
/* 332:422 */     query.setParameter("fechaDesde", fechaDesde);
/* 333:423 */     query.setParameter("fechaHasta", fechaHasta);
/* 334:424 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 335:425 */     query.setParameter("idProducto", Integer.valueOf(producto == null ? 0 : producto.getIdProducto()));
/* 336:427 */     if (indicadorElaborado) {
/* 337:428 */       query.setParameter("estado", Estado.ELABORADO);
/* 338:    */     } else {
/* 339:430 */       query.setParameter("estado", Estado.PROCESADO);
/* 340:    */     }
/* 341:433 */     return query.getResultList();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public List getReportePedidosResumido(Date fechaDesde, Date fechaHasta, Producto producto, boolean indicadorElaborado)
/* 345:    */   {
/* 346:439 */     String sql = " SELECT p.idProducto, pc.fecha, p.codigo, p.nombre, SUM(dpc.cantidad), SUM(dpc.cantidad*0) FROM DetallePedidoCliente dpc  LEFT OUTER JOIN dpc.pedidoCliente pc LEFT OUTER JOIN dpc.producto p  LEFT OUTER JOIN dpc.pedidoCliente pc  WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta  AND (p.idProducto = :idProducto OR :idProducto=0)  AND (pc.estado=:estado)";
/* 347:    */     
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:444 */     sql = sql + " GROUP BY p.idProducto, pc.fecha, p.codigo, p.nombre";
/* 352:445 */     sql = sql + " ORDER BY p.nombre";
/* 353:    */     
/* 354:447 */     Query query = this.em.createQuery(sql);
/* 355:448 */     query.setParameter("fechaDesde", fechaDesde);
/* 356:449 */     query.setParameter("fechaHasta", fechaHasta);
/* 357:450 */     query.setParameter("idProducto", Integer.valueOf(producto == null ? 0 : producto.getIdProducto()));
/* 358:452 */     if (indicadorElaborado) {
/* 359:453 */       query.setParameter("estado", Estado.ELABORADO);
/* 360:    */     } else {
/* 361:455 */       query.setParameter("estado", Estado.PROCESADO);
/* 362:    */     }
/* 363:457 */     return query.getResultList();
/* 364:    */   }
/* 365:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.ReportePedidoDespachoFacturaDao
 * JD-Core Version:    0.7.0.1
 */