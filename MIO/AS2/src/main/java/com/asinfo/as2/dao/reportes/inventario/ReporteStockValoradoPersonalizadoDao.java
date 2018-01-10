/*   1:    */ package com.asinfo.as2.dao.reportes.inventario;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteSaldoProducto;
/*   4:    */ import com.asinfo.as2.clases.ReporteStockValoradoResumido;
/*   5:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Lote;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ import javax.persistence.EntityManager;
/*  20:    */ import javax.persistence.Query;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ReporteStockValoradoPersonalizadoDao
/*  24:    */   extends AbstractDaoAS2<Producto>
/*  25:    */ {
/*  26:    */   public ReporteStockValoradoPersonalizadoDao()
/*  27:    */   {
/*  28: 36 */     super(Producto.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<ReporteStockValoradoResumido> getReporteStockValoradoResumido2(Sucursal sucursal, Bodega bodega, Date fechaDesde, Date fechaHasta, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, boolean ordenProducto)
/*  32:    */   {
/*  33: 44 */     StringBuilder sql = new StringBuilder();
/*  34: 45 */     sql.append(" SELECT new ReporteStockValoradoResumido");
/*  35: 46 */     sql.append(" (");
/*  36: 47 */     sql.append(" \tp.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, cp.codigo, cp.nombre, b.idBodega, b.nombre,");
/*  37: 48 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseRecepcionProveedor THEN (ip.cantidad*ip.operacion) END),0),");
/*  38: 49 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion > 0 AND mi.estado != 0 THEN ip.cantidad END),0),");
/*  39: 50 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion > 0 THEN ip.cantidad END),0),");
/*  40: 51 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDevolucionCliente AND fc.estado != :estadoAnulado THEN ip.cantidad END),0),");
/*  41: 52 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion < 0 AND mi.estado != 0 THEN ip.cantidad END),0),");
/*  42: 53 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion < 0 THEN ip.cantidad END),0),");
/*  43: 54 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseConsumo AND mi.estado != :estadoAnulado THEN ip.cantidad END),0),");
/*  44: 55 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDespachoCliente THEN -(ip.cantidad*ip.operacion) END),0),");
/*  45: 56 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDevolucionProveedor THEN ip.cantidad END),0),");
/*  46: 57 */     sql.append(" \tu.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre, ");
/*  47: 58 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion > 0 AND mi.estado != :estadoAnulado THEN ip.costo END),0), ");
/*  48: 59 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseRecepcionProveedor AND rp.estado != :estadoAnulado THEN ip.costo END),0), ");
/*  49: 60 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion > 0 THEN ip.costo END),0), ");
/*  50: 61 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDevolucionCliente AND fc.estado != :estadoAnulado THEN ip.costo END),0), ");
/*  51: 62 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion < 0 AND mi.estado != :estadoAnulado THEN ip.costo END),0), ");
/*  52: 63 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion < 0 THEN ip.costo END),0), ");
/*  53: 64 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseConsumo AND mi.estado != :estadoAnulado THEN ip.costo END),0), ");
/*  54: 65 */     sql.append(" \tCOALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDespachoCliente AND dc.estado != :estadoAnulado THEN ip.costo END),0),");
/*  55: 66 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDevolucionProveedor THEN ip.costo END),0), ");
/*  56: 67 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDespachoCliente AND dc.estado != :estadoAnulado THEN (dfc.precioLinea-dfc.descuentoLinea) END),0), ");
/*  57: 68 */     sql.append("    COALESCE(SUM(CASE WHEN do.documentoBase = :documentoBaseDevolucionCliente AND fc.estado != :estadoAnulado THEN (dvc.precioLinea-dvc.descuentoLinea) END),0) ");
/*  58: 69 */     sql.append(" )");
/*  59: 70 */     sql.append(" FROM InventarioProducto ip ");
/*  60: 71 */     sql.append(" INNER JOIN ip.producto p");
/*  61: 72 */     sql.append(" INNER JOIN p.subcategoriaProducto sp");
/*  62: 73 */     sql.append(" INNER JOIN sp.categoriaProducto cp");
/*  63: 74 */     sql.append(" INNER JOIN ip.documento do ");
/*  64: 75 */     sql.append(" LEFT JOIN ip.bodega b");
/*  65: 76 */     sql.append(" LEFT JOIN p.unidad u ");
/*  66: 77 */     sql.append(" LEFT JOIN p.unidadVenta uv ");
/*  67: 78 */     sql.append(" LEFT JOIN p.unidadAlmacenamiento ua ");
/*  68: 79 */     sql.append(" LEFT JOIN ip.detalleDespachoCliente ddc ");
/*  69: 80 */     sql.append(" LEFT JOIN ddc.detalleFacturaCliente dfc ");
/*  70: 81 */     sql.append(" LEFT JOIN ddc.despachoCliente dc ");
/*  71: 82 */     sql.append(" LEFT JOIN ip.detalleRecepcionProveedor drp ");
/*  72: 83 */     sql.append(" LEFT JOIN drp.recepcionProveedor rp ");
/*  73: 84 */     sql.append(" LEFT JOIN ip.detalleMovimientoInventario dmi ");
/*  74: 85 */     sql.append(" LEFT JOIN dmi.movimientoInventario mi ");
/*  75: 86 */     sql.append(" LEFT JOIN ip.detalleDevolucionCliente dvc ");
/*  76: 87 */     sql.append(" LEFT JOIN dvc.facturaCliente fc ");
/*  77:    */     
/*  78: 89 */     sql.append(" WHERE ip.fecha BETWEEN :fechaDesde AND :fechaHasta AND p.tipoProducto=:tipoProducto");
/*  79: 90 */     sql.append(" AND ip.idOrganizacion = :idOrganizacion ");
/*  80: 92 */     if (categoriaProducto != null) {
/*  81: 93 */       sql.append(" AND cp = :categoriaProducto ");
/*  82:    */     }
/*  83: 95 */     if (subcategoriaProducto != null) {
/*  84: 96 */       sql.append(" AND sp = :subcategoriaProducto ");
/*  85:    */     }
/*  86: 99 */     if (sucursal != null) {
/*  87:100 */       sql.append(" AND ip.idSucursal = :idSucursal ");
/*  88:    */     }
/*  89:103 */     if (bodega != null) {
/*  90:104 */       sql.append(" AND ip.bodega = :bodega ");
/*  91:    */     }
/*  92:107 */     if (atributo != null)
/*  93:    */     {
/*  94:108 */       sql.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/*  95:110 */       if (!valorAtributo.isEmpty()) {
/*  96:111 */         sql.append(" AND pa.valor = :valorAtributo ");
/*  97:    */       }
/*  98:113 */       sql.append(" )");
/*  99:    */     }
/* 100:116 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, cp.codigo, cp.nombre,");
/* 101:117 */     sql.append(" b.idBodega, b.nombre,u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre ");
/* 102:118 */     sql.append(" ORDER BY " + (ordenProducto ? "b.nombre, " : "") + "p.nombre");
/* 103:119 */     Query query = this.em.createQuery(sql.toString());
/* 104:120 */     query.setParameter("fechaDesde", fechaDesde);
/* 105:121 */     query.setParameter("fechaHasta", fechaHasta);
/* 106:122 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 107:123 */     query.setParameter("documentoBaseRecepcionProveedor", DocumentoBase.RECEPCION_BODEGA);
/* 108:124 */     query.setParameter("documentoBaseAjusteInventario", DocumentoBase.AJUSTE_INVENTARIO);
/* 109:125 */     query.setParameter("documentoBaseTrasferencia", DocumentoBase.TRANSFERENCIA_BODEGA);
/* 110:126 */     query.setParameter("documentoBaseDevolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/* 111:127 */     query.setParameter("documentoBaseConsumo", DocumentoBase.CONSUMO_BODEGA);
/* 112:128 */     query.setParameter("documentoBaseDespachoCliente", DocumentoBase.DESPACHO_BODEGA);
/* 113:129 */     query.setParameter("documentoBaseDevolucionProveedor", DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 114:130 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 115:131 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 116:133 */     if (sucursal != null) {
/* 117:134 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 118:    */     }
/* 119:137 */     if (bodega != null) {
/* 120:138 */       query.setParameter("bodega", bodega);
/* 121:    */     }
/* 122:140 */     if (atributo != null)
/* 123:    */     {
/* 124:141 */       query.setParameter("atributo", atributo);
/* 125:142 */       if (!valorAtributo.isEmpty()) {
/* 126:143 */         query.setParameter("valorAtributo", valorAtributo);
/* 127:    */       }
/* 128:    */     }
/* 129:146 */     if (categoriaProducto != null) {
/* 130:147 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 131:    */     }
/* 132:149 */     if (subcategoriaProducto != null) {
/* 133:150 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 134:    */     }
/* 135:153 */     List<ReporteStockValoradoResumido> lista = query.getResultList();
/* 136:    */     
/* 137:155 */     return lista;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/* 141:    */   {
/* 142:175 */     StringBuilder sbSQL = new StringBuilder();
/* 143:176 */     sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre");
/* 144:177 */     sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 145:178 */     sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 146:179 */     sbSQL.append(", b.idBodega, b.codigo, b.nombre,MAX(sp.fecha),u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre )");
/* 147:180 */     sbSQL.append(" FROM SaldoProducto sp ");
/* 148:181 */     sbSQL.append(" INNER JOIN sp.bodega b");
/* 149:182 */     sbSQL.append(" INNER JOIN sp.producto pro");
/* 150:183 */     sbSQL.append(" INNER JOIN pro.subcategoriaProducto subpro");
/* 151:184 */     sbSQL.append(" INNER JOIN subpro.categoriaProducto catpro");
/* 152:185 */     sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 153:186 */     sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 154:187 */     sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 155:188 */     sbSQL.append(" WHERE sp.fecha<=:fechaDesde");
/* 156:189 */     sbSQL.append(" AND pro.tipoProducto=:tipoProducto");
/* 157:190 */     sbSQL.append(" AND pro.idOrganizacion=:idOrganizacion");
/* 158:192 */     if (categoriaProducto != null) {
/* 159:193 */       sbSQL.append(" AND catpro = :categoriaProducto ");
/* 160:    */     }
/* 161:195 */     if (subcategoriaProducto != null) {
/* 162:196 */       sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 163:    */     }
/* 164:199 */     if (bodega != null) {
/* 165:200 */       sbSQL.append(" AND sp.bodega = :bodega");
/* 166:    */     }
/* 167:203 */     if (atributo != null)
/* 168:    */     {
/* 169:204 */       sbSQL.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND pr = pro");
/* 170:206 */       if (!valorAtributo.isEmpty()) {
/* 171:207 */         sbSQL.append(" AND pa.valor = :valorAtributo ");
/* 172:    */       }
/* 173:209 */       sbSQL.append(" )");
/* 174:    */     }
/* 175:212 */     sbSQL.append(" GROUP BY pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre");
/* 176:213 */     sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 177:214 */     sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 178:215 */     sbSQL.append(", b.idBodega, b.codigo, b.nombre,u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre");
/* 179:    */     
/* 180:217 */     Query query = this.em.createQuery(sbSQL.toString());
/* 181:218 */     query.setParameter("fechaDesde", fechaDesde);
/* 182:219 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 183:220 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 184:221 */     if (categoriaProducto != null) {
/* 185:222 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 186:    */     }
/* 187:224 */     if (subcategoriaProducto != null) {
/* 188:225 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 189:    */     }
/* 190:227 */     if (bodega != null) {
/* 191:228 */       query.setParameter("bodega", bodega);
/* 192:    */     }
/* 193:230 */     if (atributo != null)
/* 194:    */     {
/* 195:231 */       query.setParameter("atributo", atributo);
/* 196:232 */       if (!valorAtributo.isEmpty()) {
/* 197:233 */         query.setParameter("valorAtributo", valorAtributo);
/* 198:    */       }
/* 199:    */     }
/* 200:236 */     return query.getResultList();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo1, String valorAtributo1, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo2, String valorAtributo2)
/* 204:    */   {
/* 205:257 */     StringBuilder sbSQL = new StringBuilder();
/* 206:259 */     if ((atributo1 != null) || (atributo2 != null))
/* 207:    */     {
/* 208:261 */       sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.nombre");
/* 209:262 */       sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 210:263 */       sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 211:264 */       sbSQL.append(", b.idBodega, b.codigo, b.nombre,MAX(sp.fecha),u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre");
/* 212:265 */       if ((atributo1 != null) && (atributo2 != null))
/* 213:    */       {
/* 214:266 */         sbSQL.append(", pa1.valor, pa2.valor ) ");
/* 215:    */       }
/* 216:    */       else
/* 217:    */       {
/* 218:268 */         if (atributo1 != null) {
/* 219:269 */           sbSQL.append(" ,pa1.valor ) ");
/* 220:    */         }
/* 221:271 */         if (atributo2 != null) {
/* 222:272 */           sbSQL.append(" ,pa2.valor ) ");
/* 223:    */         }
/* 224:    */       }
/* 225:275 */       sbSQL.append(" FROM SaldoProducto sp ");
/* 226:276 */       sbSQL.append(" INNER JOIN sp.bodega b");
/* 227:277 */       sbSQL.append(" INNER JOIN sp.producto pro ");
/* 228:278 */       sbSQL.append(" INNER JOIN pro.subcategoriaProducto subpro");
/* 229:279 */       sbSQL.append(" INNER JOIN subpro.categoriaProducto catpro");
/* 230:280 */       sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 231:281 */       sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 232:282 */       sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 233:283 */       if (atributo1 != null) {
/* 234:284 */         sbSQL.append(" ,ProductoAtributo pa1 ");
/* 235:    */       }
/* 236:286 */       if (atributo2 != null) {
/* 237:287 */         sbSQL.append(" ,ProductoAtributo pa2 ");
/* 238:    */       }
/* 239:289 */       sbSQL.append(" WHERE sp.fecha<=:fechaDesde");
/* 240:290 */       sbSQL.append(" AND pro.tipoProducto=:tipoProducto");
/* 241:291 */       sbSQL.append(" AND pro.idOrganizacion=:idOrganizacion");
/* 242:293 */       if (atributo1 != null)
/* 243:    */       {
/* 244:294 */         sbSQL.append(" AND pa1.producto=pro ");
/* 245:295 */         sbSQL.append(" AND pa1.atributo=:atributo1 ");
/* 246:296 */         if ((valorAtributo1 != null) && (!valorAtributo1.isEmpty())) {
/* 247:297 */           sbSQL.append(" AND pa1.valor=:valorAtributo1 ");
/* 248:    */         }
/* 249:    */       }
/* 250:300 */       if (atributo2 != null)
/* 251:    */       {
/* 252:301 */         sbSQL.append(" AND pa2.producto=pro ");
/* 253:302 */         sbSQL.append(" AND pa2.atributo=:atributo2  ");
/* 254:303 */         if ((valorAtributo2 != null) && (!valorAtributo2.isEmpty())) {
/* 255:304 */           sbSQL.append(" AND pa2.valor=:valorAtributo2 ");
/* 256:    */         }
/* 257:    */       }
/* 258:308 */       if (categoriaProducto != null) {
/* 259:309 */         sbSQL.append(" AND catpro = :categoriaProducto ");
/* 260:    */       }
/* 261:311 */       if (subcategoriaProducto != null) {
/* 262:312 */         sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 263:    */       }
/* 264:315 */       if (bodega != null) {
/* 265:316 */         sbSQL.append(" AND sp.bodega = :bodega");
/* 266:    */       }
/* 267:328 */       sbSQL.append(" GROUP BY pro.idProducto, pro.codigo, pro.nombre");
/* 268:329 */       sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 269:330 */       sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 270:331 */       sbSQL.append(", b.idBodega, b.codigo, b.nombre,u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre ");
/* 271:333 */       if ((atributo1 != null) && (atributo2 != null))
/* 272:    */       {
/* 273:334 */         sbSQL.append(", pa1.valor, pa2.valor ");
/* 274:    */       }
/* 275:    */       else
/* 276:    */       {
/* 277:336 */         if (atributo1 != null) {
/* 278:337 */           sbSQL.append(" ,pa1.valor ");
/* 279:    */         }
/* 280:339 */         if (atributo2 != null) {
/* 281:340 */           sbSQL.append(" ,pa2.valor ");
/* 282:    */         }
/* 283:    */       }
/* 284:344 */       Query query = this.em.createQuery(sbSQL.toString());
/* 285:345 */       query.setParameter("fechaDesde", fechaDesde);
/* 286:346 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 287:347 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 288:348 */       if (categoriaProducto != null) {
/* 289:349 */         query.setParameter("categoriaProducto", categoriaProducto);
/* 290:    */       }
/* 291:351 */       if (subcategoriaProducto != null) {
/* 292:352 */         query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 293:    */       }
/* 294:354 */       if (bodega != null) {
/* 295:355 */         query.setParameter("bodega", bodega);
/* 296:    */       }
/* 297:358 */       if (atributo1 != null)
/* 298:    */       {
/* 299:359 */         query.setParameter("atributo1", atributo1);
/* 300:360 */         if ((valorAtributo1 != null) && (!valorAtributo1.isEmpty())) {
/* 301:361 */           query.setParameter("valorAtributo1", valorAtributo1);
/* 302:    */         }
/* 303:    */       }
/* 304:364 */       if (atributo2 != null)
/* 305:    */       {
/* 306:365 */         query.setParameter("atributo2", atributo2);
/* 307:366 */         if ((valorAtributo2 != null) && (!valorAtributo2.isEmpty())) {
/* 308:367 */           query.setParameter("valorAtributo2", valorAtributo2);
/* 309:    */         }
/* 310:    */       }
/* 311:371 */       return query.getResultList();
/* 312:    */     }
/* 313:374 */     return getReporteSaldoProducto(sucursal, bodega, fechaDesde, null, null, idOrganizacion, categoriaProducto, subcategoriaProducto);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<ReporteSaldoProducto> getReporteSaldoProductoPorLote(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, boolean indicadorLote, Lote lote, Producto producto)
/* 317:    */   {
/* 318:399 */     StringBuilder sbSQL = new StringBuilder();
/* 319:400 */     sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.nombre, ");
/* 320:401 */     sbSQL.append(" subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre, ");
/* 321:402 */     sbSQL.append(" catpro.idCategoriaProducto, catpro.codigo, catpro.nombre, ");
/* 322:403 */     sbSQL.append(" b.idBodega, b.codigo, b.nombre,MAX(coalesce(sp.fecha,:fechaDesde)), u.idUnidad, u.codigo, u.nombre, ");
/* 323:404 */     sbSQL.append(" uv.idUnidad, uv.codigo, uv.nombre, ua.idUnidad, ua.codigo, ua.nombre, lo.codigo, lo.idLote) ");
/* 324:405 */     sbSQL.append(" FROM SaldoProductoLote sp ");
/* 325:406 */     sbSQL.append(" RIGHT JOIN sp.bodega b ");
/* 326:407 */     sbSQL.append(" LEFT JOIN sp.producto pro ");
/* 327:408 */     sbSQL.append(" LEFT JOIN pro.subcategoriaProducto subpro ");
/* 328:409 */     sbSQL.append(" LEFT JOIN subpro.categoriaProducto catpro ");
/* 329:410 */     sbSQL.append(" LEFT JOIN sp.lote lo ");
/* 330:411 */     sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 331:412 */     sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 332:413 */     sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 333:414 */     sbSQL.append(" WHERE sp.fecha<=:fechaDesde ");
/* 334:415 */     sbSQL.append(" AND pro.tipoProducto=:tipoProducto ");
/* 335:416 */     sbSQL.append(" AND pro.idOrganizacion=:idOrganizacion ");
/* 336:417 */     sbSQL.append(" AND pro.indicadorLote = true");
/* 337:419 */     if (categoriaProducto != null) {
/* 338:420 */       sbSQL.append(" AND catpro = :categoriaProducto ");
/* 339:    */     }
/* 340:422 */     if (subcategoriaProducto != null) {
/* 341:423 */       sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 342:    */     }
/* 343:426 */     if (bodega != null) {
/* 344:427 */       sbSQL.append(" AND sp.bodega = :bodega");
/* 345:    */     }
/* 346:430 */     if (lote != null) {
/* 347:431 */       sbSQL.append(" AND sp.lote = :lote");
/* 348:    */     }
/* 349:434 */     if (producto != null) {
/* 350:435 */       sbSQL.append(" AND sp.producto = :producto ");
/* 351:    */     }
/* 352:438 */     if (atributo != null)
/* 353:    */     {
/* 354:439 */       sbSQL.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND pr = pro");
/* 355:441 */       if (!valorAtributo.isEmpty()) {
/* 356:442 */         sbSQL.append(" AND pa.valor = :valorAtributo ");
/* 357:    */       }
/* 358:444 */       sbSQL.append(" )");
/* 359:    */     }
/* 360:447 */     sbSQL.append(" GROUP BY pro.idProducto, pro.codigo, pro.nombre");
/* 361:448 */     sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 362:449 */     sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 363:450 */     sbSQL.append(", b.idBodega, b.codigo, b.nombre, u.idUnidad, u.codigo, u.nombre,");
/* 364:451 */     sbSQL.append(" uv.idUnidad, uv.codigo, uv.nombre, ua.idUnidad, ua.codigo, ua.nombre, lo.codigo, lo.idLote ");
/* 365:    */     
/* 366:453 */     Query query = this.em.createQuery(sbSQL.toString());
/* 367:454 */     query.setParameter("fechaDesde", fechaDesde);
/* 368:455 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 369:456 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 370:458 */     if (categoriaProducto != null) {
/* 371:459 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 372:    */     }
/* 373:461 */     if (subcategoriaProducto != null) {
/* 374:462 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 375:    */     }
/* 376:464 */     if (bodega != null) {
/* 377:465 */       query.setParameter("bodega", bodega);
/* 378:    */     }
/* 379:467 */     if (lote != null) {
/* 380:468 */       query.setParameter("lote", lote);
/* 381:    */     }
/* 382:470 */     if (producto != null) {
/* 383:471 */       query.setParameter("producto", producto);
/* 384:    */     }
/* 385:473 */     if (atributo != null)
/* 386:    */     {
/* 387:474 */       query.setParameter("atributo", atributo);
/* 388:475 */       if (!valorAtributo.isEmpty()) {
/* 389:476 */         query.setParameter("valorAtributo", valorAtributo);
/* 390:    */       }
/* 391:    */     }
/* 392:479 */     List<ReporteSaldoProducto> lista = query.getResultList();
/* 393:481 */     if (!indicadorLote)
/* 394:    */     {
/* 395:483 */       sbSQL = new StringBuilder();
/* 396:484 */       sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.nombre, ");
/* 397:485 */       sbSQL.append(" subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre, ");
/* 398:486 */       sbSQL.append(" catpro.idCategoriaProducto, catpro.codigo, catpro.nombre, ");
/* 399:487 */       sbSQL.append(" b.idBodega, b.codigo, b.nombre,MAX(coalesce(sp.fecha,:fechaDesde)), u.idUnidad, u.codigo, u.nombre, ");
/* 400:488 */       sbSQL.append(" uv.idUnidad, uv.codigo, uv.nombre, ua.idUnidad, ua.codigo, ua.nombre, '', 0) ");
/* 401:489 */       sbSQL.append(" FROM SaldoProducto sp ");
/* 402:490 */       sbSQL.append(" RIGHT JOIN sp.bodega b ");
/* 403:491 */       sbSQL.append(" LEFT JOIN sp.producto pro ");
/* 404:492 */       sbSQL.append(" LEFT JOIN pro.subcategoriaProducto subpro ");
/* 405:493 */       sbSQL.append(" LEFT JOIN subpro.categoriaProducto catpro ");
/* 406:494 */       sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 407:495 */       sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 408:496 */       sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 409:497 */       sbSQL.append(" WHERE sp.fecha<=:fechaDesde ");
/* 410:498 */       sbSQL.append(" AND pro.tipoProducto=:tipoProducto ");
/* 411:499 */       sbSQL.append(" AND pro.idOrganizacion=:idOrganizacion ");
/* 412:500 */       sbSQL.append(" AND pro.indicadorLote = false");
/* 413:502 */       if (categoriaProducto != null) {
/* 414:503 */         sbSQL.append(" AND catpro = :categoriaProducto ");
/* 415:    */       }
/* 416:505 */       if (subcategoriaProducto != null) {
/* 417:506 */         sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 418:    */       }
/* 419:509 */       if (bodega != null) {
/* 420:510 */         sbSQL.append(" AND sp.bodega = :bodega");
/* 421:    */       }
/* 422:513 */       if (producto != null) {
/* 423:514 */         sbSQL.append(" AND sp.producto = :producto ");
/* 424:    */       }
/* 425:517 */       if (atributo != null)
/* 426:    */       {
/* 427:518 */         sbSQL.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND pr = pro");
/* 428:520 */         if (!valorAtributo.isEmpty()) {
/* 429:521 */           sbSQL.append(" AND pa.valor = :valorAtributo ");
/* 430:    */         }
/* 431:523 */         sbSQL.append(" )");
/* 432:    */       }
/* 433:526 */       sbSQL.append(" GROUP BY pro.idProducto, pro.codigo, pro.nombre");
/* 434:527 */       sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 435:528 */       sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 436:529 */       sbSQL.append(", b.idBodega, b.codigo, b.nombre, u.idUnidad, u.codigo, u.nombre,");
/* 437:530 */       sbSQL.append(" uv.idUnidad, uv.codigo, uv.nombre, ua.idUnidad, ua.codigo, ua.nombre");
/* 438:    */       
/* 439:532 */       query = this.em.createQuery(sbSQL.toString());
/* 440:533 */       query.setParameter("fechaDesde", fechaDesde);
/* 441:534 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 442:535 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 443:537 */       if (categoriaProducto != null) {
/* 444:538 */         query.setParameter("categoriaProducto", categoriaProducto);
/* 445:    */       }
/* 446:540 */       if (subcategoriaProducto != null) {
/* 447:541 */         query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 448:    */       }
/* 449:543 */       if (bodega != null) {
/* 450:544 */         query.setParameter("bodega", bodega);
/* 451:    */       }
/* 452:546 */       if (producto != null) {
/* 453:547 */         query.setParameter("producto", producto);
/* 454:    */       }
/* 455:549 */       if (atributo != null)
/* 456:    */       {
/* 457:550 */         query.setParameter("atributo", atributo);
/* 458:551 */         if (!valorAtributo.isEmpty()) {
/* 459:552 */           query.setParameter("valorAtributo", valorAtributo);
/* 460:    */         }
/* 461:    */       }
/* 462:555 */       lista.addAll(query.getResultList());
/* 463:    */     }
/* 464:557 */     return lista;
/* 465:    */   }
/* 466:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.inventario.ReporteStockValoradoPersonalizadoDao
 * JD-Core Version:    0.7.0.1
 */