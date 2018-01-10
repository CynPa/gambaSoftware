/*   1:    */ package com.asinfo.as2.dao.reportes.ventas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  10:    */ import com.asinfo.as2.entities.Subempresa;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class ReporteDespachoClienteDao
/*  23:    */   extends AbstractDaoAS2<DespachoCliente>
/*  24:    */ {
/*  25:    */   public ReporteDespachoClienteDao()
/*  26:    */   {
/*  27: 45 */     super(DespachoCliente.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List getReporteDespachoCliente(DespachoCliente despachoCliente, int numeroAtributos)
/*  31:    */     throws ExcepcionAS2
/*  32:    */   {
/*  33: 59 */     StringBuilder sql = new StringBuilder();
/*  34: 60 */     sql.append(" SELECT d.numero, e.nombreComercial, d.fecha, p.nombreComercial, ddc.cantidad,  ");
/*  35: 61 */     sql.append(" b.nombre, p.peso, p.volumen, u.codigo, p.codigo, l.codigo, ci.nombre, e.nombreFiscal, ");
/*  36: 62 */     sql.append(" d.descripcion, fc.numero, pc.numero, pr.nombre, d.estado, ddc.descripcion, se.empresaFinal, ");
/*  37: 63 */     sql.append(" se.codigo, p.codigoAlterno, docu.nombre,  coalesce(concat(resp.nombres,' ',resp.apellidos), ''), ");
/*  38: 64 */     sql.append(" p.precioUltimaCompra, ce.nombre, coalesce(epl.nombreFiscal,''), coalesce(cip.nombre,''), ddc.cantidad*0, ");
/*  39: 65 */     sql.append(" lpp.idListaPrecios, p.idProducto, docu.descripcion, d.usuarioCreacion ");
/*  40: 66 */     if (numeroAtributos > 0)
/*  41:    */     {
/*  42: 67 */       sql.append(" , ");
/*  43: 68 */       for (int i = 1; i <= numeroAtributos; i++)
/*  44:    */       {
/*  45: 69 */         sql.append(" at" + i + ".codigo, ");
/*  46: 70 */         sql.append(" at" + i + ".nombre, ");
/*  47: 71 */         sql.append(" vat" + i + ".codigo, ");
/*  48: 72 */         sql.append(" vat" + i + ".nombre,");
/*  49:    */       }
/*  50:    */     }
/*  51: 75 */     sql = new StringBuilder(sql.toString().substring(0, sql.toString().length() - 1));
/*  52:    */     
/*  53: 77 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/*  54: 78 */     sql.append(" INNER JOIN ddc.despachoCliente d ");
/*  55: 79 */     sql.append(" INNER JOIN d.documento docu ");
/*  56: 80 */     sql.append(" INNER JOIN d.empresa e ");
/*  57: 81 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/*  58: 82 */     sql.append(" INNER JOIN d.direccionEmpresa de ");
/*  59: 83 */     sql.append(" INNER JOIN de.ciudad ci ");
/*  60: 84 */     sql.append(" INNER JOIN ddc.producto p ");
/*  61: 85 */     sql.append(" LEFT OUTER JOIN ddc.inventarioProducto ip ");
/*  62: 86 */     sql.append(" LEFT OUTER JOIN ip.bodega b ");
/*  63: 87 */     sql.append(" LEFT OUTER JOIN p.categoriaImpuesto cip ");
/*  64: 88 */     sql.append(" LEFT JOIN ip.lote l ");
/*  65: 89 */     sql.append(" LEFT OUTER JOIN l.empresa epl ");
/*  66: 90 */     sql.append(" LEFT OUTER JOIN epl.proveedor pl ");
/*  67: 91 */     sql.append(" LEFT OUTER JOIN pl.listaPrecios lpp");
/*  68: 92 */     if (numeroAtributos > 0) {
/*  69: 93 */       for (int i = 1; i <= numeroAtributos; i++)
/*  70:    */       {
/*  71: 94 */         sql.append(" LEFT JOIN l.atributo" + i + " at" + i);
/*  72: 95 */         sql.append(" LEFT JOIN l.valorAtributo" + i + " vat" + i);
/*  73:    */       }
/*  74:    */     }
/*  75: 98 */     sql.append(" LEFT JOIN d.subempresa se ");
/*  76: 99 */     sql.append(" LEFT JOIN se.empresa emp ");
/*  77:100 */     sql.append(" LEFT JOIN d.pedidoCliente pc ");
/*  78:101 */     sql.append(" LEFT JOIN d.responsableSalidaMercaderia resp ");
/*  79:102 */     sql.append(" LEFT JOIN ddc.detalleFacturaCliente dfc ");
/*  80:103 */     sql.append(" LEFT JOIN dfc.facturaCliente fc ");
/*  81:104 */     sql.append(" LEFT JOIN d.proyecto pr ");
/*  82:105 */     sql.append(" LEFT JOIN ddc.unidadVenta u ");
/*  83:106 */     sql.append(" WHERE d.idDespachoCliente = :idDespachoCliente ");
/*  84:107 */     sql.append(" AND d.fecha = :fecha");
/*  85:108 */     sql.append(" ORDER BY p.nombreComercial ");
/*  86:    */     
/*  87:110 */     Query query = this.em.createQuery(sql.toString());
/*  88:111 */     query.setParameter("idDespachoCliente", Integer.valueOf(despachoCliente.getId()));
/*  89:    */     
/*  90:113 */     query.setParameter("fecha", despachoCliente.getFecha(), TemporalType.DATE);
/*  91:114 */     return query.getResultList();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List getReporteDespachoDetallado(int tipoReporte, Date fechaDesde, Date fechaHasta, int idEmpresa, int idResponsableSalidaMercaderia, Bodega bodega, Subempresa subempresa, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto)
/*  95:    */   {
/*  96:121 */     StringBuilder sql = new StringBuilder();
/*  97:122 */     sql.append(" SELECT ");
/*  98:124 */     if (tipoReporte != 1) {
/*  99:125 */       sql.append(" dc.numero,dc.fecha,e.nombreComercial,e.nombreFiscal,e.identificacion, gr.numero, dc.usuarioCreacion, se.codigo, se.empresaFinal, fc.numero, ddc.descripcion, ");
/* 100:    */     }
/* 101:127 */     sql.append(" CONCAT(rsm.apellidos,' ',rsm.nombres), rsm.identificacion, ");
/* 102:128 */     sql.append(" p.codigo, p.codigoComercial, p.nombre, p.nombreComercial, uv.codigo, b.codigo, b.nombre, ");
/* 103:129 */     if (tipoReporte != 2) {
/* 104:130 */       sql.append(" SUM(ddc.cantidad), SUM(ip.costo) ");
/* 105:    */     }
/* 106:132 */     if (tipoReporte == 2)
/* 107:    */     {
/* 108:133 */       sql.append(" ddc.cantidad, ip.costo ");
/* 109:134 */       sql.append(" ,lot.codigo ");
/* 110:    */     }
/* 111:136 */     sql.append(", COALESCE(pc.numero,pcf.numero), p.peso ");
/* 112:    */     
/* 113:138 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/* 114:139 */     sql.append(" LEFT OUTER JOIN ddc.producto p ");
/* 115:140 */     sql.append(" LEFT OUTER JOIN p.subcategoriaProducto scp ");
/* 116:141 */     sql.append(" LEFT OUTER JOIN scp.categoriaProducto cp ");
/* 117:142 */     sql.append(" LEFT OUTER JOIN ddc.bodega b ");
/* 118:143 */     sql.append(" LEFT OUTER JOIN ddc.unidadVenta uv ");
/* 119:144 */     sql.append(" LEFT OUTER JOIN ddc.inventarioProducto ip ");
/* 120:145 */     sql.append(" LEFT OUTER JOIN ip.lote lot ");
/* 121:146 */     sql.append(" LEFT OUTER JOIN ddc.detalleFacturaCliente dfc ");
/* 122:147 */     sql.append(" LEFT OUTER JOIN dfc.facturaCliente fc ");
/* 123:148 */     sql.append(" LEFT OUTER JOIN ddc.despachoCliente dc ");
/* 124:149 */     sql.append(" LEFT OUTER JOIN dc.subempresa se ");
/* 125:150 */     sql.append(" LEFT OUTER JOIN dc.empresa e ");
/* 126:151 */     sql.append(" LEFT OUTER JOIN dc.responsableSalidaMercaderia rsm ");
/* 127:152 */     sql.append(" LEFT OUTER JOIN dc.guiaRemision gr ");
/* 128:153 */     sql.append(" LEFT OUTER JOIN ddc.detallePedidoCliente dpc ");
/* 129:154 */     sql.append(" LEFT OUTER JOIN dpc.pedidoCliente pc ");
/* 130:155 */     sql.append(" LEFT OUTER JOIN dfc.detallePedidoCliente dpcf ");
/* 131:156 */     sql.append(" LEFT OUTER JOIN dpcf.pedidoCliente pcf ");
/* 132:    */     
/* 133:158 */     sql.append(" WHERE dc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 134:159 */     sql.append(" AND dc.idOrganizacion = :idOrganizacion ");
/* 135:160 */     sql.append(" AND (e.idEmpresa = :idEmpresa OR :idEmpresa=0) AND dc.estado <> :estadoAnulado");
/* 136:161 */     sql.append(" AND (rsm.idPersonaResponsable = :idResponsableSalidaMercaderia OR :idResponsableSalidaMercaderia=0) ");
/* 137:162 */     if (bodega != null) {
/* 138:163 */       sql.append(" AND b =:bodega ");
/* 139:    */     }
/* 140:165 */     if ((null != subempresa) && (subempresa.getIdSubempresa() != 0)) {
/* 141:166 */       sql.append("AND dc.subempresa = :subempresa");
/* 142:    */     }
/* 143:168 */     if (producto != null) {
/* 144:169 */       sql.append("AND p.idProducto = :idProducto ");
/* 145:    */     }
/* 146:171 */     if (categoriaProducto != null) {
/* 147:172 */       sql.append("AND cp.idCategoriaProducto = :idCategoriaProducto ");
/* 148:    */     }
/* 149:174 */     if (subcategoriaProducto != null) {
/* 150:175 */       sql.append("AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/* 151:    */     }
/* 152:178 */     if (tipoReporte == 1)
/* 153:    */     {
/* 154:179 */       sql.append(" GROUP BY rsm.apellidos, rsm.nombres, rsm.identificacion, p.codigo, p.codigoComercial, p.nombre, p.nombreComercial, uv.codigo, b.codigo, b.nombre, COALESCE(pc.numero,pcf.numero), p.peso ");
/* 155:180 */       sql.append(" ORDER BY rsm.apellidos, rsm.nombres, p.nombre ");
/* 156:    */     }
/* 157:181 */     else if (tipoReporte == 2)
/* 158:    */     {
/* 159:182 */       sql.append(" ORDER BY rsm.apellidos, rsm.nombres, dc.fecha, COALESCE(pc.numero,pcf.numero), p.nombre ");
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:184 */       sql.append(" GROUP BY dc.numero,dc.fecha,e.nombreComercial,e.nombreFiscal,e.identificacion, gr.numero, dc.usuarioCreacion,  se.codigo, se.empresaFinal, fc.numero, ddc.descripcion, rsm.apellidos, rsm.nombres, rsm.identificacion, p.codigo, p.codigoComercial, p.nombre, p.nombreComercial, uv.codigo, b.codigo, b.nombre, COALESCE(pc.numero,pcf.numero), p.peso ");
/* 164:    */       
/* 165:186 */       sql.append(" ORDER BY rsm.apellidos, rsm.nombres, dc.fecha ");
/* 166:    */     }
/* 167:189 */     Query query = this.em.createQuery(sql.toString());
/* 168:190 */     query.setParameter("fechaDesde", fechaDesde);
/* 169:191 */     query.setParameter("fechaHasta", fechaHasta);
/* 170:192 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 171:193 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 172:194 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 173:195 */     query.setParameter("idResponsableSalidaMercaderia", Integer.valueOf(idResponsableSalidaMercaderia));
/* 174:196 */     if (bodega != null) {
/* 175:197 */       query.setParameter("bodega", bodega);
/* 176:    */     }
/* 177:199 */     if ((null != subempresa) && (subempresa.getIdSubempresa() != 0)) {
/* 178:200 */       query.setParameter("subempresa", subempresa);
/* 179:    */     }
/* 180:202 */     if (producto != null) {
/* 181:203 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 182:    */     }
/* 183:205 */     if (categoriaProducto != null) {
/* 184:206 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 185:    */     }
/* 186:208 */     if (subcategoriaProducto != null) {
/* 187:209 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 188:    */     }
/* 189:212 */     return query.getResultList();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List getReporteDespachoPrefacturaFactura(Date fechaDesde, Date fechaHasta, Empresa empresa, boolean indicadorDetallado)
/* 193:    */   {
/* 194:217 */     StringBuilder sql = new StringBuilder();
/* 195:218 */     sql.append(" SELECT e.nombreFiscal, dc.numero, dc.fecha, dc.descripcion, ");
/* 196:219 */     sql.append(" pc.numero, pc.fecha, SUM(dpc.cantidad*(dpc.precio-dpc.descuento)),");
/* 197:220 */     sql.append(" fc.numero, fc.fecha, CASE WHEN pr.tipoProducto = :tipoServicio THEN 'SERVICIO' ELSE 'ARTICULO' END, spr.nombre, pc.descripcion2, pc.valorBono, ");
/* 198:221 */     sql.append(" pc.guia, pc.mso, fc.descripcion2, SUM(COALESCE(ip.costo,0)) ");
/* 199:222 */     if (indicadorDetallado) {
/* 200:223 */       sql.append(" , pr.codigo, pr.nombre ");
/* 201:    */     } else {
/* 202:225 */       sql.append(" , '', '' ");
/* 203:    */     }
/* 204:227 */     sql.append(" FROM DetalleAjustePrefacturaCliente dpc");
/* 205:228 */     sql.append(" JOIN dpc.producto pr ");
/* 206:229 */     sql.append(" JOIN pr.subcategoriaProducto spr ");
/* 207:230 */     sql.append(" JOIN dpc.ajustePrefacturaCliente apc");
/* 208:231 */     sql.append(" JOIN apc.prefacturaCliente pc");
/* 209:232 */     sql.append(" JOIN pc.empresa e ");
/* 210:233 */     sql.append(" LEFT JOIN pc.facturaCliente fc");
/* 211:234 */     sql.append(" LEFT JOIN dpc.detalleDespachoCliente ddc ");
/* 212:235 */     sql.append(" LEFT JOIN ddc.despachoCliente dc ");
/* 213:236 */     sql.append(" LEFT JOIN ddc.inventarioProducto ip ");
/* 214:237 */     sql.append(" WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 215:238 */     sql.append(" AND apc.activo = true ");
/* 216:239 */     sql.append(" AND pc.estado != :estado ");
/* 217:240 */     if (empresa != null) {
/* 218:241 */       sql.append(" AND (e.idEmpresa = :idEmpresa) ");
/* 219:    */     }
/* 220:243 */     sql.append(" GROUP BY e.nombreFiscal, dc.numero, dc.fecha, dc.descripcion,  ");
/* 221:244 */     sql.append(" pc.numero, pc.fecha,fc.numero, fc.fecha, pr.tipoProducto, spr.nombre, pc.descripcion2, pc.valorBono,pc.guia, pc.mso,fc.descripcion2 ");
/* 222:245 */     if (indicadorDetallado) {
/* 223:246 */       sql.append(" , pr.codigo, pr.nombre ");
/* 224:    */     }
/* 225:248 */     sql.append(" ORDER BY e.nombreFiscal, pc.numero");
/* 226:    */     
/* 227:250 */     Query query = this.em.createQuery(sql.toString());
/* 228:251 */     query.setParameter("estado", Estado.ANULADO);
/* 229:252 */     query.setParameter("fechaDesde", fechaDesde);
/* 230:253 */     query.setParameter("fechaHasta", fechaHasta);
/* 231:254 */     query.setParameter("tipoServicio", TipoProducto.SERVICIO);
/* 232:255 */     if (empresa != null) {
/* 233:256 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getIdEmpresa()));
/* 234:    */     }
/* 235:258 */     return query.getResultList();
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.ReporteDespachoClienteDao
 * JD-Core Version:    0.7.0.1
 */