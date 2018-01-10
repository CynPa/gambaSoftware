/*   1:    */ package com.asinfo.as2.dao.reportes.ventas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.PedidoCliente;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.LinkedHashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.Query;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class ReportePedidoSaldoPorDespacharDao
/*  20:    */   extends AbstractDaoAS2<PedidoCliente>
/*  21:    */ {
/*  22:    */   public ReportePedidoSaldoPorDespacharDao()
/*  23:    */   {
/*  24: 42 */     super(PedidoCliente.class);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public List<Object[]> getReportePedidoSaldoPorDespachar(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/*  28:    */   {
/*  29: 55 */     StringBuilder sqlp = new StringBuilder();
/*  30: 56 */     sqlp.append(" SELECT pc.numero, e.nombreFiscal, p.codigo, p.nombre, p.volumen, p.peso,dpc.cantidad, SUM(dpc.cantidad*0), SUM(dpc.cantidad*0), pc.fecha, pc.estado");
/*  31: 57 */     sqlp.append(" FROM DetallePedidoCliente dpc");
/*  32: 58 */     sqlp.append(" INNER JOIN dpc.pedidoCliente pc");
/*  33: 59 */     sqlp.append(" INNER JOIN pc.empresa e");
/*  34: 60 */     sqlp.append(" INNER JOIN dpc.producto p");
/*  35: 61 */     sqlp.append(" LEFT JOIN p.subcategoriaProducto scp");
/*  36: 62 */     sqlp.append(" LEFT JOIN scp.categoriaProducto cp");
/*  37: 63 */     sqlp.append(" WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  38: 64 */     sqlp.append(" AND pc.idOrganizacion = :idOrganizacion");
/*  39: 65 */     sqlp.append(" AND pc.estado != :estadoElaborado ");
/*  40: 66 */     sqlp.append(" AND pc.estado != :estadoAnulado ");
/*  41: 67 */     if (idEmpresa != 0) {
/*  42: 68 */       sqlp.append("  AND e.idEmpresa = :idEmpresa ");
/*  43:    */     }
/*  44: 70 */     if (categoriaProducto != null) {
/*  45: 71 */       sqlp.append("  AND cp.idCategoriaProducto = :idCategoriaProducto ");
/*  46:    */     }
/*  47: 73 */     if (subcategoriaProducto != null) {
/*  48: 74 */       sqlp.append(" AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/*  49:    */     }
/*  50: 77 */     sqlp.append(" GROUP BY pc.numero, e.nombreFiscal, p.codigo, p.nombre, p.volumen, p.peso,dpc.cantidad, pc.fecha, pc.estado");
/*  51: 78 */     sqlp.append("  ORDER BY pc.numero, e.nombreFiscal, p.nombre ");
/*  52:    */     
/*  53: 80 */     Query query = this.em.createQuery(sqlp.toString());
/*  54: 81 */     query.setParameter("fechaDesde", fechaDesde);
/*  55: 82 */     query.setParameter("fechaHasta", fechaHasta);
/*  56: 83 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/*  57: 84 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  58: 85 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  59: 86 */     if (idEmpresa != 0) {
/*  60: 87 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  61:    */     }
/*  62: 89 */     if (categoriaProducto != null) {
/*  63: 90 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/*  64:    */     }
/*  65: 92 */     if (subcategoriaProducto != null) {
/*  66: 93 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/*  67:    */     }
/*  68: 96 */     List<Object[]> lista = query.getResultList();
/*  69:    */     
/*  70: 98 */     return lista;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public LinkedHashMap<String, Object[]> getReporteDespachos(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean indicadorSaldosPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/*  74:    */   {
/*  75:105 */     LinkedHashMap<String, Object[]> hmDatosReporte = new LinkedHashMap();
/*  76:    */     
/*  77:107 */     StringBuilder sql = new StringBuilder();
/*  78:108 */     sql.append(" SELECT pc.numero, e.nombreFiscal, p.codigo, p.nombre, SUM(ddc.cantidad)");
/*  79:109 */     sql.append(" FROM DetalleDespachoCliente ddc");
/*  80:110 */     sql.append(" INNER JOIN ddc.despachoCliente dc");
/*  81:111 */     sql.append(" INNER JOIN ddc.detallePedidoCliente dpc");
/*  82:112 */     sql.append(" INNER JOIN dpc.pedidoCliente pc");
/*  83:113 */     sql.append(" INNER JOIN pc.empresa e");
/*  84:114 */     sql.append(" INNER JOIN ddc.producto p");
/*  85:115 */     sql.append(" LEFT JOIN p.subcategoriaProducto scp");
/*  86:116 */     sql.append(" LEFT JOIN scp.categoriaProducto cp");
/*  87:117 */     sql.append(" WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  88:118 */     sql.append(" AND pc.idOrganizacion = :idOrganizacion");
/*  89:119 */     sql.append(" AND pc.estado != :estadoAnulado ");
/*  90:120 */     sql.append(" AND dc.estado != :estadoAnulado ");
/*  91:122 */     if (idEmpresa != 0) {
/*  92:123 */       sql.append("  AND e.idEmpresa = :idEmpresa ");
/*  93:    */     }
/*  94:125 */     if (categoriaProducto != null) {
/*  95:126 */       sql.append("  AND cp.idCategoriaProducto = :idCategoriaProducto ");
/*  96:    */     }
/*  97:128 */     if (subcategoriaProducto != null) {
/*  98:129 */       sql.append(" AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/*  99:    */     }
/* 100:132 */     sql.append(" GROUP BY pc.numero, e.nombreFiscal, p.codigo, p.nombre ");
/* 101:133 */     sql.append(" ORDER BY pc.numero, e.nombreFiscal, p.nombre ");
/* 102:    */     
/* 103:135 */     Query query = this.em.createQuery(sql.toString());
/* 104:136 */     query.setParameter("fechaDesde", fechaDesde);
/* 105:137 */     query.setParameter("fechaHasta", fechaHasta);
/* 106:138 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 107:139 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 108:141 */     if (idEmpresa != 0) {
/* 109:142 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 110:    */     }
/* 111:144 */     if (categoriaProducto != null) {
/* 112:145 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 113:    */     }
/* 114:147 */     if (subcategoriaProducto != null) {
/* 115:148 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 116:    */     }
/* 117:151 */     List<Object[]> lista = query.getResultList();
/* 118:153 */     for (Object[] objects : lista) {
/* 119:154 */       hmDatosReporte.put(objects[0] + "~" + objects[2], objects);
/* 120:    */     }
/* 121:157 */     return hmDatosReporte;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public LinkedHashMap<String, Object[]> getReporteFacturas(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean indicadorSaldosPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 125:    */   {
/* 126:164 */     LinkedHashMap<String, Object[]> hmDatosReporte = new LinkedHashMap();
/* 127:    */     
/* 128:166 */     StringBuilder sql = new StringBuilder();
/* 129:167 */     sql.append(" SELECT pc.numero, e.nombreFiscal, p.codigo, p.nombre, \tSUM(dfc.cantidad)");
/* 130:168 */     sql.append(" FROM DetalleFacturaCliente dfc");
/* 131:169 */     sql.append(" INNER JOIN dfc.facturaCliente fc");
/* 132:170 */     sql.append(" INNER JOIN fc.pedidoCliente pc");
/* 133:171 */     sql.append(" INNER JOIN pc.empresa e");
/* 134:172 */     sql.append(" INNER JOIN dfc.producto p");
/* 135:173 */     sql.append(" LEFT JOIN p.subcategoriaProducto scp");
/* 136:174 */     sql.append(" LEFT JOIN scp.categoriaProducto cp");
/* 137:175 */     sql.append(" WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 138:176 */     sql.append(" AND pc.idOrganizacion = :idOrganizacion");
/* 139:177 */     sql.append(" AND pc.estado != :estadoAnulado ");
/* 140:178 */     sql.append(" AND fc.estado != :estadoAnulado ");
/* 141:180 */     if (idEmpresa != 0) {
/* 142:181 */       sql.append("  AND e.idEmpresa = :idEmpresa ");
/* 143:    */     }
/* 144:183 */     if (categoriaProducto != null) {
/* 145:184 */       sql.append("  AND cp.idCategoriaProducto = :idCategoriaProducto ");
/* 146:    */     }
/* 147:186 */     if (subcategoriaProducto != null) {
/* 148:187 */       sql.append(" AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/* 149:    */     }
/* 150:190 */     sql.append(" GROUP BY pc.numero, e.nombreFiscal, p.codigo, p.nombre ");
/* 151:191 */     sql.append("  ORDER BY pc.numero, e.nombreFiscal, p.codigo, p.nombre");
/* 152:    */     
/* 153:193 */     Query query = this.em.createQuery(sql.toString());
/* 154:194 */     query.setParameter("fechaDesde", fechaDesde);
/* 155:195 */     query.setParameter("fechaHasta", fechaHasta);
/* 156:196 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 157:197 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 158:199 */     if (idEmpresa != 0) {
/* 159:200 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 160:    */     }
/* 161:202 */     if (categoriaProducto != null) {
/* 162:203 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 163:    */     }
/* 164:205 */     if (subcategoriaProducto != null) {
/* 165:206 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 166:    */     }
/* 167:209 */     List<Object[]> lista = query.getResultList();
/* 168:211 */     for (Object[] objects : lista) {
/* 169:212 */       hmDatosReporte.put(objects[0] + "~" + objects[2], objects);
/* 170:    */     }
/* 171:215 */     return hmDatosReporte;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public LinkedHashMap<String, Object[]> getReporteFacturaDespachos(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean indicadorSaldosPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 175:    */   {
/* 176:222 */     LinkedHashMap<String, Object[]> hmDatosReporte = new LinkedHashMap();
/* 177:    */     
/* 178:224 */     StringBuilder sql = new StringBuilder();
/* 179:225 */     sql.append(" SELECT pc.numero, e.nombreFiscal, p.codigo, p.nombre, SUM(ddc.cantidad)");
/* 180:226 */     sql.append(" FROM DetalleFacturaCliente dfc");
/* 181:227 */     sql.append(" INNER JOIN dfc.detalleDespachoCliente ddc");
/* 182:228 */     sql.append(" INNER JOIN ddc.despachoCliente dc");
/* 183:229 */     sql.append(" INNER JOIN dfc.detallePedidoCliente dpc");
/* 184:230 */     sql.append(" INNER JOIN dpc.pedidoCliente pc");
/* 185:231 */     sql.append(" INNER JOIN pc.empresa e");
/* 186:232 */     sql.append(" INNER JOIN ddc.producto p");
/* 187:233 */     sql.append(" LEFT JOIN p.subcategoriaProducto scp");
/* 188:234 */     sql.append(" LEFT JOIN scp.categoriaProducto cp");
/* 189:235 */     sql.append(" WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 190:236 */     sql.append(" AND pc.idOrganizacion = :idOrganizacion");
/* 191:237 */     sql.append(" AND pc.estado != :estadoAnulado ");
/* 192:238 */     sql.append(" AND dc.estado != :estadoAnulado ");
/* 193:240 */     if (idEmpresa != 0) {
/* 194:241 */       sql.append("  AND e.idEmpresa = :idEmpresa ");
/* 195:    */     }
/* 196:243 */     if (categoriaProducto != null) {
/* 197:244 */       sql.append("  AND cp.idCategoriaProducto = :idCategoriaProducto ");
/* 198:    */     }
/* 199:246 */     if (subcategoriaProducto != null) {
/* 200:247 */       sql.append(" AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/* 201:    */     }
/* 202:249 */     sql.append(" GROUP BY pc.numero, e.nombreFiscal, p.codigo, p.nombre ");
/* 203:250 */     sql.append("  ORDER BY pc.numero, e.nombreFiscal, p.codigo, p.nombre");
/* 204:    */     
/* 205:252 */     Query query = this.em.createQuery(sql.toString());
/* 206:253 */     query.setParameter("fechaDesde", fechaDesde);
/* 207:254 */     query.setParameter("fechaHasta", fechaHasta);
/* 208:255 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 209:256 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 210:258 */     if (idEmpresa != 0) {
/* 211:259 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 212:    */     }
/* 213:261 */     if (categoriaProducto != null) {
/* 214:262 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 215:    */     }
/* 216:264 */     if (subcategoriaProducto != null) {
/* 217:265 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 218:    */     }
/* 219:268 */     List<Object[]> lista = query.getResultList();
/* 220:270 */     for (Object[] objects : lista) {
/* 221:271 */       hmDatosReporte.put(objects[0] + "~" + objects[2], objects);
/* 222:    */     }
/* 223:274 */     return hmDatosReporte;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List getReportePedidoSaldoPorDescacharGenerico(Date fechaDesde, Date fechaHasta, int idEmpresa)
/* 227:    */   {
/* 228:288 */     List lista = new ArrayList();
/* 229:    */     
/* 230:290 */     String sql = "SELECT fc.idFacturaCliente, 0, dc.idDespachoCliente, fc.empresa.idEmpresa, dfc.idDetalleFacturaCliente, dfc.detalleDespachoCliente.idDetalleDespachoCliente,  dfc.detallePedidoCliente.idDetallePedidoCliente, dfc.cantidad, 0, 0 FROM DetalleDespachoCliente ddc  INNER JOIN ddc.despachoCliente dc  RIGHT OUTER JOIN ddc.detalleFacturaCliente dfc  LEFT OUTER JOIN dfc.facturaCliente fc";
/* 231:    */     
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:295 */     Query query = this.em.createQuery(sql);
/* 236:296 */     lista.addAll(query.getResultList());
/* 237:    */     
/* 238:298 */     sql = "SELECT fc.idFacturaCliente, 0, dc.idDespachoCliente, dc.empresa.idEmpresa, ddc.detalleFacturaCliente.idDetalleFacturaCliente, ddc.idDetalleDespachoCliente,  ddc.detallePedidoCliente.idDetallePedidoCliente, 0, ddc.cantidad, 0 FROM DetalleDespachoCliente ddc  INNER JOIN ddc.despachoCliente dc\tLEFT OUTER JOIN ddc.detalleFacturaCliente dfc  LEFT OUTER JOIN dfc.facturaCliente fc";
/* 239:    */     
/* 240:    */ 
/* 241:    */ 
/* 242:302 */     query = this.em.createQuery(sql);
/* 243:303 */     lista.addAll(query.getResultList());
/* 244:    */     
/* 245:305 */     sql = "SELECT 0, pc.idPedidoCliente, dc.idDespachoCliente, pc.empresa.idEmpresa, 0, 0, dpc.idDetallePedidoCliente, 0, 0, dpc.cantidad  FROM DetalleDespachoCliente ddc  INNER JOIN ddc.despachoCliente dc  RIGHT OUTER JOIN ddc.detallePedidoCliente dpc  LEFT OUTER JOIN dpc.pedidoCliente pc";
/* 246:    */     
/* 247:    */ 
/* 248:308 */     query = this.em.createQuery(sql);
/* 249:309 */     lista.addAll(query.getResultList());
/* 250:    */     
/* 251:311 */     return lista;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public List getReporteDespachoFactura(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 255:    */   {
/* 256:317 */     String sql = " SELECT fc.numero, fc.fecha, dc.numero, dc.fecha, SUM(COALESCE(dfc.cantidad,0)), SUM(COALESCE(ddc.cantidad,0)), p.codigo, p.nombre, e.nombreFiscal, gr.numero FROM DetalleDespachoCliente ddc   INNER JOIN ddc.despachoCliente dc LEFT JOIN dc.guiaRemision gr  LEFT JOIN ddc.detalleFacturaCliente dfc  LEFT JOIN dfc.facturaCliente fc  LEFT JOIN fc.documento d  INNER JOIN dc.empresa e INNER JOIN ddc.producto p WHERE (:idEmpresa = 0 OR e.idEmpresa=:idEmpresa) AND (:categoriaProducto IS NULL OR p.subcategoriaProducto.categoriaProducto=:categoriaProducto) AND (:subcategoriaProducto IS NULL OR p.subcategoriaProducto=:subcategoriaProducto) AND (dc.fecha BETWEEN :fechaDesde AND :fechaHasta)  AND (dc.idOrganizacion = :idOrganizacion) AND (dc.estado != :estado)";
/* 257:328 */     if (soloPendientes) {
/* 258:329 */       sql = sql + " AND (p.tipoProducto = :tipoProducto)";
/* 259:    */     }
/* 260:331 */     sql = sql + " GROUP BY fc.numero, fc.fecha, dc.numero, dc.fecha, p.codigo, p.nombre, e.nombreFiscal, gr.numero ";
/* 261:332 */     if (soloPendientes) {
/* 262:333 */       sql = sql + " HAVING SUM(COALESCE(ddc.cantidad,0)) > SUM(COALESCE(dfc.cantidad,0)) ";
/* 263:    */     }
/* 264:336 */     Query query = this.em.createQuery(sql);
/* 265:337 */     query.setParameter("fechaDesde", fechaDesde);
/* 266:338 */     query.setParameter("fechaHasta", fechaHasta);
/* 267:339 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 268:340 */     query.setParameter("categoriaProducto", categoriaProducto);
/* 269:341 */     query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 270:342 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 271:343 */     query.setParameter("estado", Estado.ANULADO);
/* 272:345 */     if (soloPendientes) {
/* 273:346 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 274:    */     }
/* 275:349 */     return query.getResultList();
/* 276:    */   }
/* 277:    */   
/* 278:    */   public List getReporteFacturaDespacho(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 279:    */   {
/* 280:361 */     String sql = " SELECT fc.numero, fc.fecha, dc.numero, dc.fecha, SUM(COALESCE(dfc.cantidad,0)), SUM(COALESCE (ddc.cantidad,0)), p.codigo,p.nombre, e.nombreFiscal, gr.numero FROM DetalleFacturaCliente dfc  INNER JOIN dfc.facturaCliente fc  INNER JOIN fc.empresa e INNER JOIN fc.documento d  LEFT JOIN dfc.detalleDespachoCliente ddc LEFT JOIN ddc.despachoCliente dc LEFT OUTER JOIN dc.guiaRemision gr INNER JOIN dfc.producto p WHERE (:idEmpresa = 0 OR e.idEmpresa=:idEmpresa) AND (fc.fecha BETWEEN :fechaDesde AND :fechaHasta)  AND (fc.idOrganizacion = :idOrganizacion) AND (fc.estado != :estado) AND (d.documentoBase = :facturaCliente)";
/* 281:367 */     if (categoriaProducto != null) {
/* 282:368 */       sql = sql + " AND (p.subcategoriaProducto.categoriaProducto=:categoriaProducto) ";
/* 283:    */     }
/* 284:370 */     if (subcategoriaProducto != null) {
/* 285:371 */       sql = sql + " AND (p.subcategoriaProducto=:subcategoriaProducto) ";
/* 286:    */     }
/* 287:373 */     if (soloPendientes) {
/* 288:374 */       sql = sql + " AND (p.tipoProducto = :tipoProducto)";
/* 289:    */     }
/* 290:377 */     sql = sql + " GROUP BY fc.numero, fc.fecha, dc.numero, dc.fecha, p.codigo,p.nombre, e.nombreFiscal, gr.numero ";
/* 291:378 */     if (soloPendientes) {
/* 292:379 */       sql = sql + " HAVING  SUM(COALESCE(dfc.cantidad,0)) > SUM(COALESCE (ddc.cantidad,0)) ";
/* 293:    */     }
/* 294:382 */     Query query = this.em.createQuery(sql);
/* 295:383 */     query.setParameter("fechaDesde", fechaDesde);
/* 296:384 */     query.setParameter("fechaHasta", fechaHasta);
/* 297:385 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 298:386 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 299:387 */     query.setParameter("estado", Estado.ANULADO);
/* 300:388 */     query.setParameter("facturaCliente", DocumentoBase.FACTURA_CLIENTE);
/* 301:389 */     if (categoriaProducto != null) {
/* 302:390 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 303:    */     }
/* 304:392 */     if (subcategoriaProducto != null) {
/* 305:393 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 306:    */     }
/* 307:396 */     if (soloPendientes) {
/* 308:397 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 309:    */     }
/* 310:400 */     return query.getResultList();
/* 311:    */   }
/* 312:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.ReportePedidoSaldoPorDespacharDao
 * JD-Core Version:    0.7.0.1
 */