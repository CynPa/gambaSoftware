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
/*  13:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ import javax.persistence.EntityManager;
/*  21:    */ import javax.persistence.Query;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class ReporteStockValoradoDao
/*  25:    */   extends AbstractDaoAS2<Producto>
/*  26:    */ {
/*  27:    */   public ReporteStockValoradoDao()
/*  28:    */   {
/*  29: 37 */     super(Producto.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<ReporteStockValoradoResumido> getReporteStockValoradoResumido2(Sucursal sucursal, Bodega bodega, Date fechaDesde, Date fechaHasta, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/*  33:    */   {
/*  34: 45 */     StringBuilder sql = new StringBuilder();
/*  35: 46 */     sql.append(" SELECT new ReporteStockValoradoResumido");
/*  36: 47 */     sql.append(" (");
/*  37: 48 */     sql.append(" \tp.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, cp.codigo, cp.nombre, b.idBodega, b.nombre,");
/*  38: 49 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseRecepcionProveedor THEN (ip.cantidad*ip.operacion) END),0)),");
/*  39: 50 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion > 0 THEN ip.cantidad END),0)),");
/*  40: 51 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion > 0 THEN ip.cantidad END),0)),");
/*  41: 52 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionCliente AND fc.estado != :estadoAnulado THEN ip.cantidad END),0)),");
/*  42: 53 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion < 0 THEN ip.cantidad END),0)),");
/*  43: 54 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion < 0 THEN ip.cantidad END),0)),");
/*  44: 55 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseConsumo THEN ip.cantidad END),0)),");
/*  45: 56 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDespachoCliente THEN (ip.cantidad*ip.operacion) END),0))*(-1),");
/*  46: 57 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionProveedor AND fp.estado != :estadoAnulado THEN ip.cantidad END),0)),");
/*  47: 58 */     sql.append(" \tu.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre, ");
/*  48: 59 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionCliente AND fc.estado = :estadoAnulado THEN ip.cantidad END),0)),");
/*  49: 60 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionProveedor AND fp.estado = :estadoAnulado THEN ip.cantidad END),0)), ");
/*  50: 61 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTransformacionProducto AND ip.operacion > 0 THEN ip.cantidad END),0)), ");
/*  51: 62 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTransformacionProducto AND ip.operacion < 0 THEN ip.cantidad END),0)), ");
/*  52: 63 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseSalidaMaterial THEN (ip.cantidad*ip.operacion) END),0))*(-1), ");
/*  53: 64 */     sql.append(" \tp.codigoAlterno, ");
/*  54: 65 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseRecepcionProveedor THEN ip.costo END),0)),");
/*  55: 66 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion > 0 THEN ip.costo END),0)),");
/*  56: 67 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion > 0 THEN ip.costo END),0)),");
/*  57: 68 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionCliente AND fc.estado != :estadoAnulado THEN ip.costo END),0)),");
/*  58: 69 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseAjusteInventario AND ip.operacion < 0 THEN ip.costo END),0)),");
/*  59: 70 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTrasferencia AND ip.operacion < 0 THEN ip.costo END),0)),");
/*  60: 71 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseConsumo THEN ip.costo END),0)),");
/*  61: 72 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDespachoCliente THEN ip.costo END),0)),");
/*  62: 73 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionProveedor AND fp.estado != :estadoAnulado THEN ip.costo END),0)),");
/*  63: 74 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionCliente AND fc.estado = :estadoAnulado THEN ip.costo END),0)),");
/*  64: 75 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseDevolucionProveedor AND fp.estado = :estadoAnulado THEN ip.costo END),0)), ");
/*  65: 76 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTransformacionProducto AND ip.operacion > 0 THEN ip.costo END),0)), ");
/*  66: 77 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseTransformacionProducto AND ip.operacion < 0 THEN ip.costo END),0)), ");
/*  67: 78 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseSalidaMaterial THEN ip.costo END),0)),");
/*  68: 79 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseIngresoProduccion THEN (ip.cantidad*ip.operacion) END),0)),");
/*  69: 80 */     sql.append(" \tSUM(COALESCE((CASE WHEN do.documentoBase = :documentoBaseIngresoProduccion THEN ip.costo END),0))");
/*  70: 81 */     sql.append(" )");
/*  71: 82 */     sql.append(" FROM InventarioProducto ip ");
/*  72: 83 */     sql.append(" LEFT JOIN ip.documento do ");
/*  73: 84 */     sql.append(" LEFT JOIN ip.bodega b");
/*  74: 85 */     sql.append(" JOIN ip.producto p");
/*  75: 86 */     sql.append(" JOIN p.subcategoriaProducto sp");
/*  76: 87 */     sql.append(" JOIN sp.categoriaProducto cp");
/*  77: 88 */     sql.append(" LEFT JOIN p.unidad u ");
/*  78: 89 */     sql.append(" LEFT JOIN p.unidadVenta uv ");
/*  79: 90 */     sql.append(" LEFT JOIN p.unidadAlmacenamiento ua ");
/*  80: 91 */     sql.append(" LEFT JOIN ip.detalleDevolucionCliente dvc ");
/*  81: 92 */     sql.append(" LEFT JOIN dvc.facturaCliente fc ");
/*  82: 93 */     sql.append(" LEFT JOIN ip.detalleDevolucionProveedor dvp ");
/*  83: 94 */     sql.append(" LEFT JOIN dvp.facturaProveedor fp ");
/*  84: 95 */     sql.append(" WHERE ip.fecha BETWEEN :fechaDesde AND :fechaHasta AND p.tipoProducto=:tipoProducto");
/*  85: 96 */     sql.append(" AND ip.idOrganizacion = :idOrganizacion ");
/*  86: 98 */     if (categoriaProducto != null) {
/*  87: 99 */       sql.append(" AND cp = :categoriaProducto ");
/*  88:    */     }
/*  89:101 */     if (subcategoriaProducto != null) {
/*  90:102 */       sql.append(" AND sp = :subcategoriaProducto ");
/*  91:    */     }
/*  92:105 */     if (sucursal != null) {
/*  93:106 */       sql.append(" AND ip.idSucursal = :idSucursal ");
/*  94:    */     }
/*  95:109 */     if (bodega != null) {
/*  96:110 */       sql.append(" AND ip.bodega = :bodega ");
/*  97:    */     }
/*  98:113 */     if (atributo != null)
/*  99:    */     {
/* 100:114 */       sql.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/* 101:116 */       if (!valorAtributo.isEmpty()) {
/* 102:117 */         sql.append(" AND pa.valor = :valorAtributo ");
/* 103:    */       }
/* 104:119 */       sql.append(" )");
/* 105:    */     }
/* 106:122 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, cp.codigo, cp.nombre,");
/* 107:123 */     sql.append(" b.idBodega, b.nombre,u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre, p.codigoAlterno ");
/* 108:124 */     sql.append(" ORDER BY b.nombre, p.nombre");
/* 109:125 */     Query query = this.em.createQuery(sql.toString());
/* 110:126 */     query.setParameter("fechaDesde", fechaDesde);
/* 111:127 */     query.setParameter("fechaHasta", fechaHasta);
/* 112:128 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 113:129 */     query.setParameter("documentoBaseRecepcionProveedor", DocumentoBase.RECEPCION_BODEGA);
/* 114:130 */     query.setParameter("documentoBaseAjusteInventario", DocumentoBase.AJUSTE_INVENTARIO);
/* 115:131 */     query.setParameter("documentoBaseTrasferencia", DocumentoBase.TRANSFERENCIA_BODEGA);
/* 116:132 */     query.setParameter("documentoBaseDevolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/* 117:133 */     query.setParameter("documentoBaseConsumo", DocumentoBase.CONSUMO_BODEGA);
/* 118:134 */     query.setParameter("documentoBaseDespachoCliente", DocumentoBase.DESPACHO_BODEGA);
/* 119:135 */     query.setParameter("documentoBaseDevolucionProveedor", DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 120:136 */     query.setParameter("documentoBaseTransformacionProducto", DocumentoBase.TRANSFORMACION_PRODUCTO);
/* 121:137 */     query.setParameter("documentoBaseSalidaMaterial", DocumentoBase.SALIDA_MATERIAL);
/* 122:138 */     query.setParameter("documentoBaseIngresoProduccion", DocumentoBase.INGRESO_PRODUCCION);
/* 123:139 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 124:140 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 125:142 */     if (sucursal != null) {
/* 126:143 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 127:    */     }
/* 128:146 */     if (bodega != null) {
/* 129:147 */       query.setParameter("bodega", bodega);
/* 130:    */     }
/* 131:149 */     if (atributo != null)
/* 132:    */     {
/* 133:150 */       query.setParameter("atributo", atributo);
/* 134:151 */       if (!valorAtributo.isEmpty()) {
/* 135:152 */         query.setParameter("valorAtributo", valorAtributo);
/* 136:    */       }
/* 137:    */     }
/* 138:155 */     if (categoriaProducto != null) {
/* 139:156 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 140:    */     }
/* 141:158 */     if (subcategoriaProducto != null) {
/* 142:159 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 143:    */     }
/* 144:162 */     return query.getResultList();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Boolean indicadorSaldoCero)
/* 148:    */   {
/* 149:183 */     StringBuilder sbSQL = new StringBuilder();
/* 150:184 */     sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre");
/* 151:185 */     sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 152:186 */     sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 153:187 */     sbSQL.append(", b.idBodega, b.codigo, b.nombre,MAX(sp.fecha),u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre, pro.nombreComercial )");
/* 154:188 */     sbSQL.append(" FROM SaldoProducto sp ");
/* 155:189 */     sbSQL.append(" INNER JOIN sp.bodega b");
/* 156:190 */     sbSQL.append(" INNER JOIN sp.producto pro");
/* 157:191 */     sbSQL.append(" INNER JOIN pro.subcategoriaProducto subpro");
/* 158:192 */     sbSQL.append(" INNER JOIN subpro.categoriaProducto catpro");
/* 159:193 */     sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 160:194 */     sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 161:195 */     sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 162:196 */     sbSQL.append(" WHERE sp.fecha<=:fechaDesde");
/* 163:197 */     sbSQL.append(" AND pro.tipoProducto=:tipoProducto");
/* 164:198 */     sbSQL.append(" AND pro.idOrganizacion=:idOrganizacion");
/* 165:200 */     if (categoriaProducto != null) {
/* 166:201 */       sbSQL.append(" AND catpro = :categoriaProducto ");
/* 167:    */     }
/* 168:203 */     if (subcategoriaProducto != null) {
/* 169:204 */       sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 170:    */     }
/* 171:207 */     if (bodega != null) {
/* 172:208 */       sbSQL.append(" AND sp.bodega = :bodega");
/* 173:    */     }
/* 174:211 */     if (atributo != null)
/* 175:    */     {
/* 176:212 */       sbSQL.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND pr = pro");
/* 177:214 */       if (!valorAtributo.isEmpty()) {
/* 178:215 */         sbSQL.append(" AND pa.valor = :valorAtributo ");
/* 179:    */       }
/* 180:217 */       sbSQL.append(" )");
/* 181:    */     }
/* 182:219 */     if ((null != indicadorSaldoCero) && (indicadorSaldoCero.booleanValue())) {
/* 183:220 */       sbSQL.append(" AND pro.activo = true");
/* 184:    */     }
/* 185:223 */     sbSQL.append(" GROUP BY pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre");
/* 186:224 */     sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 187:225 */     sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 188:226 */     sbSQL.append(", b.idBodega, b.codigo, b.nombre,u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre, pro.nombreComercial");
/* 189:    */     
/* 190:228 */     Query query = this.em.createQuery(sbSQL.toString());
/* 191:229 */     query.setParameter("fechaDesde", fechaDesde);
/* 192:230 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 193:231 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 194:232 */     if (categoriaProducto != null) {
/* 195:233 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 196:    */     }
/* 197:235 */     if (subcategoriaProducto != null) {
/* 198:236 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 199:    */     }
/* 200:238 */     if (bodega != null) {
/* 201:239 */       query.setParameter("bodega", bodega);
/* 202:    */     }
/* 203:241 */     if (atributo != null)
/* 204:    */     {
/* 205:242 */       query.setParameter("atributo", atributo);
/* 206:243 */       if (!valorAtributo.isEmpty()) {
/* 207:244 */         query.setParameter("valorAtributo", valorAtributo);
/* 208:    */       }
/* 209:    */     }
/* 210:247 */     return query.getResultList();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal sucursal, Bodega bodega, Date fechaDesde, Atributo atributo1, String valorAtributo1, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo2, String valorAtributo2, Boolean indicadorSaldoCero)
/* 214:    */   {
/* 215:268 */     StringBuilder sbSQL = new StringBuilder();
/* 216:270 */     if ((atributo1 != null) || (atributo2 != null))
/* 217:    */     {
/* 218:272 */       sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre");
/* 219:273 */       sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 220:274 */       sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 221:275 */       sbSQL.append(", b.idBodega, b.codigo, b.nombre,MAX(sp.fecha),u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre");
/* 222:276 */       if ((atributo1 != null) && (atributo2 != null))
/* 223:    */       {
/* 224:277 */         sbSQL.append(", pa1.valor, pa2.valor");
/* 225:    */       }
/* 226:    */       else
/* 227:    */       {
/* 228:279 */         if (atributo1 != null) {
/* 229:280 */           sbSQL.append(", pa1.valor ");
/* 230:    */         }
/* 231:282 */         if (atributo2 != null) {
/* 232:283 */           sbSQL.append(", pa2.valor ");
/* 233:    */         }
/* 234:    */       }
/* 235:286 */       sbSQL.append(", pro.nombreComercial ) ");
/* 236:287 */       sbSQL.append(" FROM SaldoProducto sp ");
/* 237:288 */       sbSQL.append(" INNER JOIN sp.bodega b");
/* 238:289 */       sbSQL.append(" INNER JOIN sp.producto pro ");
/* 239:290 */       sbSQL.append(" INNER JOIN pro.subcategoriaProducto subpro");
/* 240:291 */       sbSQL.append(" INNER JOIN subpro.categoriaProducto catpro");
/* 241:292 */       sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 242:293 */       sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 243:294 */       sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 244:295 */       if (atributo1 != null) {
/* 245:296 */         sbSQL.append(" ,ProductoAtributo pa1 ");
/* 246:    */       }
/* 247:298 */       if (atributo2 != null) {
/* 248:299 */         sbSQL.append(" ,ProductoAtributo pa2 ");
/* 249:    */       }
/* 250:301 */       sbSQL.append(" WHERE sp.fecha<=:fechaDesde");
/* 251:302 */       sbSQL.append(" AND sp.saldo <> 0 ");
/* 252:303 */       sbSQL.append(" AND pro.tipoProducto=:tipoProducto");
/* 253:304 */       sbSQL.append(" AND pro.idOrganizacion=:idOrganizacion");
/* 254:306 */       if (atributo1 != null)
/* 255:    */       {
/* 256:307 */         sbSQL.append(" AND pa1.producto=pro ");
/* 257:308 */         sbSQL.append(" AND pa1.atributo=:atributo1 ");
/* 258:309 */         if ((valorAtributo1 != null) && (!valorAtributo1.isEmpty())) {
/* 259:310 */           sbSQL.append(" AND pa1.valor=:valorAtributo1 ");
/* 260:    */         }
/* 261:    */       }
/* 262:313 */       if (atributo2 != null)
/* 263:    */       {
/* 264:314 */         sbSQL.append(" AND pa2.producto=pro ");
/* 265:315 */         sbSQL.append(" AND pa2.atributo=:atributo2  ");
/* 266:316 */         if ((valorAtributo2 != null) && (!valorAtributo2.isEmpty())) {
/* 267:317 */           sbSQL.append(" AND pa2.valor=:valorAtributo2 ");
/* 268:    */         }
/* 269:    */       }
/* 270:321 */       if (categoriaProducto != null) {
/* 271:322 */         sbSQL.append(" AND catpro = :categoriaProducto ");
/* 272:    */       }
/* 273:324 */       if (subcategoriaProducto != null) {
/* 274:325 */         sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 275:    */       }
/* 276:327 */       if (bodega != null) {
/* 277:328 */         sbSQL.append(" AND sp.bodega = :bodega");
/* 278:    */       }
/* 279:330 */       if ((null != indicadorSaldoCero) && (indicadorSaldoCero.booleanValue())) {
/* 280:331 */         sbSQL.append(" AND pro.activo = true");
/* 281:    */       }
/* 282:334 */       sbSQL.append(" GROUP BY pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre");
/* 283:335 */       sbSQL.append(", subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre");
/* 284:336 */       sbSQL.append(", catpro.idCategoriaProducto, catpro.codigo, catpro.nombre");
/* 285:337 */       sbSQL.append(", b.idBodega, b.codigo, b.nombre,u.idUnidad,u.codigo,u.nombre,uv.idUnidad,uv.codigo,uv.nombre,ua.idUnidad,ua.codigo,ua.nombre, pro.nombreComercial ");
/* 286:339 */       if ((atributo1 != null) && (atributo2 != null))
/* 287:    */       {
/* 288:340 */         sbSQL.append(", pa1.valor, pa2.valor ");
/* 289:    */       }
/* 290:    */       else
/* 291:    */       {
/* 292:342 */         if (atributo1 != null) {
/* 293:343 */           sbSQL.append(" ,pa1.valor ");
/* 294:    */         }
/* 295:345 */         if (atributo2 != null) {
/* 296:346 */           sbSQL.append(" ,pa2.valor ");
/* 297:    */         }
/* 298:    */       }
/* 299:350 */       Query query = this.em.createQuery(sbSQL.toString());
/* 300:351 */       query.setParameter("fechaDesde", fechaDesde);
/* 301:352 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 302:353 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 303:354 */       if (categoriaProducto != null) {
/* 304:355 */         query.setParameter("categoriaProducto", categoriaProducto);
/* 305:    */       }
/* 306:357 */       if (subcategoriaProducto != null) {
/* 307:358 */         query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 308:    */       }
/* 309:360 */       if (bodega != null) {
/* 310:361 */         query.setParameter("bodega", bodega);
/* 311:    */       }
/* 312:364 */       if (atributo1 != null)
/* 313:    */       {
/* 314:365 */         query.setParameter("atributo1", atributo1);
/* 315:366 */         if ((valorAtributo1 != null) && (!valorAtributo1.isEmpty())) {
/* 316:367 */           query.setParameter("valorAtributo1", valorAtributo1);
/* 317:    */         }
/* 318:    */       }
/* 319:370 */       if (atributo2 != null)
/* 320:    */       {
/* 321:371 */         query.setParameter("atributo2", atributo2);
/* 322:372 */         if ((valorAtributo2 != null) && (!valorAtributo2.isEmpty())) {
/* 323:373 */           query.setParameter("valorAtributo2", valorAtributo2);
/* 324:    */         }
/* 325:    */       }
/* 326:377 */       return query.getResultList();
/* 327:    */     }
/* 328:380 */     return getReporteSaldoProducto(sucursal, bodega, fechaDesde, null, null, idOrganizacion, categoriaProducto, subcategoriaProducto, indicadorSaldoCero);
/* 329:    */   }
/* 330:    */   
/* 331:    */   public List<ReporteSaldoProducto> getReporteSaldoProductoPorLote(Sucursal sucursal, Bodega bodega, Date fechaHasta, Atributo atributo, String valorAtributo, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, boolean indicadorLote, Lote lote, Producto producto, List<ValorAtributo> listValoresAtributos, int numeroAtributosOrganizacion)
/* 332:    */   {
/* 333:405 */     StringBuilder sbSQL = new StringBuilder();
/* 334:406 */     sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre, ");
/* 335:407 */     sbSQL.append(" subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre, ");
/* 336:408 */     sbSQL.append(" catpro.idCategoriaProducto, catpro.codigo, catpro.nombre, ");
/* 337:409 */     sbSQL.append(" b.idBodega, b.codigo, b.nombre, spl1.fecha, u.idUnidad, u.codigo, u.nombre, ");
/* 338:410 */     sbSQL.append(" uv.idUnidad, uv.codigo, uv.nombre, ua.idUnidad, ua.codigo, ua.nombre, lo.codigo, lo.idLote, spl1.saldo)");
/* 339:411 */     if (numeroAtributosOrganizacion > 0)
/* 340:    */     {
/* 341:412 */       sbSQL = new StringBuilder(sbSQL.toString().substring(0, sbSQL.toString().length() - 1));
/* 342:413 */       for (int i = 1; i <= numeroAtributosOrganizacion; i++) {
/* 343:414 */         sbSQL.append(" ,vat" + i + ".nombre");
/* 344:    */       }
/* 345:416 */       sbSQL.append(")");
/* 346:    */     }
/* 347:418 */     sbSQL.append(" FROM SaldoProductoLote spl1 ");
/* 348:419 */     sbSQL.append(" RIGHT JOIN spl1.bodega b ");
/* 349:420 */     sbSQL.append(" JOIN spl1.producto pro ");
/* 350:421 */     sbSQL.append(" LEFT JOIN pro.subcategoriaProducto subpro ");
/* 351:422 */     sbSQL.append(" LEFT JOIN subpro.categoriaProducto catpro ");
/* 352:423 */     sbSQL.append(" LEFT JOIN spl1.lote lo ");
/* 353:424 */     for (int i = 1; i <= numeroAtributosOrganizacion; i++) {
/* 354:425 */       sbSQL.append(" LEFT JOIN lo.valorAtributo" + i + " vat" + i);
/* 355:    */     }
/* 356:427 */     sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 357:428 */     sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 358:429 */     sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 359:430 */     sbSQL.append(" WHERE pro.idOrganizacion = :idOrganizacion ");
/* 360:431 */     sbSQL.append(" AND pro.tipoProducto = :tipoProducto ");
/* 361:432 */     sbSQL.append(" AND pro.indicadorLote = :indicadorLote ");
/* 362:433 */     sbSQL.append(" AND spl1.saldo <> 0 ");
/* 363:434 */     sbSQL.append(" AND spl1.fecha IN (SELECT DISTINCT MAX(spl2.fecha) ");
/* 364:435 */     sbSQL.append(" \t\tFROM SaldoProductoLote spl2 ");
/* 365:436 */     sbSQL.append(" \t\tWHERE spl2.producto = spl1.producto AND spl2.bodega = spl1.bodega AND spl2.lote = spl1.lote ");
/* 366:437 */     sbSQL.append(" \t\tAND spl2.fecha <= :fechaHasta ");
/* 367:438 */     sbSQL.append(" \t\tGROUP BY spl2.bodega, spl2.producto, spl2.lote) ");
/* 368:439 */     if (categoriaProducto != null) {
/* 369:440 */       sbSQL.append(" AND catpro = :categoriaProducto ");
/* 370:    */     }
/* 371:442 */     if (subcategoriaProducto != null) {
/* 372:443 */       sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 373:    */     }
/* 374:445 */     if (bodega != null) {
/* 375:446 */       sbSQL.append(" AND spl1.bodega = :bodega");
/* 376:    */     }
/* 377:448 */     if (lote != null) {
/* 378:449 */       sbSQL.append(" AND spl1.lote = :lote");
/* 379:    */     }
/* 380:451 */     if (producto != null) {
/* 381:452 */       sbSQL.append(" AND spl1.producto = :producto ");
/* 382:    */     }
/* 383:454 */     if (atributo != null)
/* 384:    */     {
/* 385:455 */       sbSQL.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND pr = pro");
/* 386:456 */       if (!valorAtributo.isEmpty()) {
/* 387:457 */         sbSQL.append(" AND pa.valor = :valorAtributo ");
/* 388:    */       }
/* 389:459 */       sbSQL.append(" )");
/* 390:    */     }
/* 391:462 */     if ((listValoresAtributos != null) && (!listValoresAtributos.isEmpty()))
/* 392:    */     {
/* 393:463 */       sbSQL.append(" AND (");
/* 394:464 */       for (int i = 1; i <= numeroAtributosOrganizacion; i++) {
/* 395:465 */         sbSQL.append(" vat" + i + " IN (:listValoresAtributos) OR");
/* 396:    */       }
/* 397:467 */       sbSQL = new StringBuilder(sbSQL.toString().substring(0, sbSQL.toString().length() - 2));
/* 398:468 */       sbSQL.append(")");
/* 399:    */     }
/* 400:470 */     Query query = this.em.createQuery(sbSQL.toString());
/* 401:471 */     query.setParameter("fechaHasta", fechaHasta);
/* 402:472 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 403:473 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 404:474 */     query.setParameter("indicadorLote", Boolean.valueOf(true));
/* 405:475 */     if (categoriaProducto != null) {
/* 406:476 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 407:    */     }
/* 408:478 */     if (subcategoriaProducto != null) {
/* 409:479 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 410:    */     }
/* 411:481 */     if (bodega != null) {
/* 412:482 */       query.setParameter("bodega", bodega);
/* 413:    */     }
/* 414:484 */     if (lote != null) {
/* 415:485 */       query.setParameter("lote", lote);
/* 416:    */     }
/* 417:487 */     if (producto != null) {
/* 418:488 */       query.setParameter("producto", producto);
/* 419:    */     }
/* 420:490 */     if (atributo != null)
/* 421:    */     {
/* 422:491 */       query.setParameter("atributo", atributo);
/* 423:492 */       if (!valorAtributo.isEmpty()) {
/* 424:493 */         query.setParameter("valorAtributo", valorAtributo);
/* 425:    */       }
/* 426:    */     }
/* 427:496 */     if ((listValoresAtributos != null) && (!listValoresAtributos.isEmpty())) {
/* 428:497 */       query.setParameter("listValoresAtributos", listValoresAtributos);
/* 429:    */     }
/* 430:499 */     List<ReporteSaldoProducto> lista = query.getResultList();
/* 431:501 */     if (!indicadorLote)
/* 432:    */     {
/* 433:502 */       sbSQL = new StringBuilder();
/* 434:503 */       sbSQL.append(" SELECT new ReporteSaldoProducto(pro.idProducto, pro.codigo, pro.codigoAlterno, pro.nombre, ");
/* 435:504 */       sbSQL.append(" subpro.idSubcategoriaProducto, subpro.codigo, subpro.nombre, ");
/* 436:505 */       sbSQL.append(" catpro.idCategoriaProducto, catpro.codigo, catpro.nombre, ");
/* 437:506 */       sbSQL.append(" b.idBodega, b.codigo, b.nombre, sp1.fecha, u.idUnidad, u.codigo, u.nombre, ");
/* 438:507 */       sbSQL.append(" uv.idUnidad, uv.codigo, uv.nombre, ua.idUnidad, ua.codigo, ua.nombre, '', 0, sp1.saldo) ");
/* 439:508 */       sbSQL.append(" FROM SaldoProducto sp1 ");
/* 440:509 */       sbSQL.append(" RIGHT JOIN sp1.bodega b ");
/* 441:510 */       sbSQL.append(" JOIN sp1.producto pro ");
/* 442:511 */       sbSQL.append(" LEFT JOIN pro.subcategoriaProducto subpro ");
/* 443:512 */       sbSQL.append(" LEFT JOIN subpro.categoriaProducto catpro ");
/* 444:513 */       sbSQL.append(" LEFT JOIN pro.unidad u ");
/* 445:514 */       sbSQL.append(" LEFT JOIN pro.unidadVenta uv ");
/* 446:515 */       sbSQL.append(" LEFT JOIN pro.unidadAlmacenamiento ua ");
/* 447:516 */       sbSQL.append(" WHERE pro.idOrganizacion = :idOrganizacion ");
/* 448:517 */       sbSQL.append(" AND pro.tipoProducto = :tipoProducto ");
/* 449:518 */       sbSQL.append(" AND pro.indicadorLote = :indicadorLote");
/* 450:519 */       sbSQL.append(" AND sp1.saldo <> 0 ");
/* 451:520 */       sbSQL.append(" AND sp1.fecha IN (SELECT DISTINCT MAX(sp2.fecha) ");
/* 452:521 */       sbSQL.append(" \t\tFROM SaldoProducto sp2 ");
/* 453:522 */       sbSQL.append(" \t\tWHERE sp2.producto = sp1.producto AND sp2.bodega = sp1.bodega ");
/* 454:523 */       sbSQL.append(" \t\tAND sp2.fecha <= :fechaHasta ");
/* 455:524 */       sbSQL.append(" \t\tGROUP BY sp2.bodega, sp2.producto) ");
/* 456:525 */       if (categoriaProducto != null) {
/* 457:526 */         sbSQL.append(" AND catpro = :categoriaProducto ");
/* 458:    */       }
/* 459:528 */       if (subcategoriaProducto != null) {
/* 460:529 */         sbSQL.append(" AND subpro = :subcategoriaProducto ");
/* 461:    */       }
/* 462:532 */       if (bodega != null) {
/* 463:533 */         sbSQL.append(" AND sp.bodega = :bodega");
/* 464:    */       }
/* 465:535 */       if (producto != null) {
/* 466:536 */         sbSQL.append(" AND sp.producto = :producto ");
/* 467:    */       }
/* 468:538 */       if (atributo != null)
/* 469:    */       {
/* 470:539 */         sbSQL.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND pr = pro");
/* 471:540 */         if (!valorAtributo.isEmpty()) {
/* 472:541 */           sbSQL.append(" AND pa.valor = :valorAtributo ");
/* 473:    */         }
/* 474:543 */         sbSQL.append(" )");
/* 475:    */       }
/* 476:546 */       query = this.em.createQuery(sbSQL.toString());
/* 477:547 */       query.setParameter("fechaHasta", fechaHasta);
/* 478:548 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 479:549 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 480:550 */       query.setParameter("indicadorLote", Boolean.valueOf(false));
/* 481:551 */       if (categoriaProducto != null) {
/* 482:552 */         query.setParameter("categoriaProducto", categoriaProducto);
/* 483:    */       }
/* 484:554 */       if (subcategoriaProducto != null) {
/* 485:555 */         query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 486:    */       }
/* 487:557 */       if (bodega != null) {
/* 488:558 */         query.setParameter("bodega", bodega);
/* 489:    */       }
/* 490:560 */       if (producto != null) {
/* 491:561 */         query.setParameter("producto", producto);
/* 492:    */       }
/* 493:563 */       if (atributo != null)
/* 494:    */       {
/* 495:564 */         query.setParameter("atributo", atributo);
/* 496:565 */         if (!valorAtributo.isEmpty()) {
/* 497:566 */           query.setParameter("valorAtributo", valorAtributo);
/* 498:    */         }
/* 499:    */       }
/* 500:569 */       lista.addAll(query.getResultList());
/* 501:    */     }
/* 502:572 */     return lista;
/* 503:    */   }
/* 504:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.inventario.ReporteStockValoradoDao
 * JD-Core Version:    0.7.0.1
 */