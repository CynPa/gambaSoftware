/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.SRI;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteComprasVentasRetenciones;
/*   4:    */ import com.asinfo.as2.clases.ReporteRetencionesResumido;
/*   5:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   6:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   9:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ReporteRetencionSRIDao
/*  19:    */   extends AbstractDaoAS2<FacturaProveedorSRI>
/*  20:    */ {
/*  21:    */   public ReporteRetencionSRIDao()
/*  22:    */   {
/*  23: 41 */     super(FacturaProveedorSRI.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<ReporteRetencionesResumido> getRetencionSRIResumido(int mes, int anio, int idOrganizacion)
/*  27:    */   {
/*  28: 58 */     String sql = "SELECT new ReporteRetencionesResumido(f.fechaEmisionRetencion,f.fechaRegistro,f.fechaEmision,  CONCAT(f.establecimiento,'-',f.puntoEmision,'-',f.numero),\tf.identificacionProveedor,c.descripcion,CASE WHEN c.tipoConceptoRetencion = :tipoConceptoFUENTE then (SUM(COALESCE(dfp.baseImponibleRetencion, 0)+COALESCE(dfp.baseImponibleTarifaCero, 0) +COALESCE(dfp.baseImponibleDiferenteCero, 0)+COALESCE(dfp.baseImponibleNoObjetoIva, 0))) else COALESCE(dfp.baseImponibleRetencion, 0) end,dfp.porcentajeRetencion,dfp.valorRetencion,\tc.codigo,CASE WHEN c.tipoConceptoRetencion = :tipoConceptoIVA then 'IVA' WHEN c.tipoConceptoRetencion = :tipoConceptoFUENTE then 'FUENTE' WHEN c.tipoConceptoRetencion = :tipoConceptoISD then 'ISD' else '' end,f.numeroRetencion) FROM DetalleFacturaProveedorSRI dfp INNER JOIN dfp.facturaProveedorSRI f INNER JOIN dfp.conceptoRetencionSRI c  WHERE MONTH(f.fechaRegistro) =:mes\tAND YEAR(f.fechaRegistro) =:anio AND f.estado!=:estadoAnulado AND f.indicadorSaldoInicial!=true AND f.idOrganizacion = :idOrganizacion\tGROUP BY c.codigo,f.numero,f.puntoEmision,\tf.establecimiento, f.identificacionProveedor,\tc.descripcion,dfp.baseImponibleRetencion,dfp.porcentajeRetencion,dfp.valorRetencion,f.fechaEmisionRetencion,f.fechaRegistro,f.fechaEmision,f.numeroRetencion, c.tipoConceptoRetencion  ORDER BY c.tipoConceptoRetencion, c.codigo ";
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
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
/*  45: 75 */     Query query = this.em.createQuery(sql);
/*  46: 76 */     query.setParameter("mes", Integer.valueOf(mes));
/*  47: 77 */     query.setParameter("anio", Integer.valueOf(anio));
/*  48: 78 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  49: 79 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  50: 80 */     query.setParameter("tipoConceptoIVA", TipoConceptoRetencion.IVA);
/*  51: 81 */     query.setParameter("tipoConceptoFUENTE", TipoConceptoRetencion.FUENTE);
/*  52: 82 */     query.setParameter("tipoConceptoISD", TipoConceptoRetencion.ISD);
/*  53: 83 */     List<ReporteRetencionesResumido> lista = query.getResultList();
/*  54:    */     
/*  55: 85 */     return lista;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Object[]> getRetencionSRI(int mes, int anio, int idOrganizacion, Sucursal sucursalFP, Sucursal sucursalRetencion, PuntoDeVenta puntoVentaRetencion, String orden)
/*  59:    */   {
/*  60:107 */     StringBuilder sql = new StringBuilder();
/*  61:    */     
/*  62:109 */     sql.append(" SELECT f.fechaEmisionRetencion, f.fechaRegistro, f.fechaEmision, ");
/*  63:110 */     sql.append(" CONCAT(f.establecimiento,'-',f.puntoEmision,'-',f.numero), ");
/*  64:111 */     sql.append(" f.identificacionProveedor, c.descripcion, ");
/*  65:112 */     sql.append(" coalesce(CASE WHEN c.tipoConceptoRetencion = :tipoConceptoFUENTE THEN (SUM(COALESCE(dfp.baseImponibleRetencion, 0)+COALESCE(dfp.baseImponibleTarifaCero, 0) ");
/*  66:113 */     sql.append(" \t+COALESCE(dfp.baseImponibleDiferenteCero, 0)+COALESCE(dfp.baseImponibleNoObjetoIva, 0))) ELSE COALESCE(dfp.baseImponibleRetencion, 0) END,0), ");
/*  67:114 */     sql.append(" coalesce(dfp.porcentajeRetencion, 0), coalesce(dfp.valorRetencion, 0), coalesce(c.codigo, 'NA'), ");
/*  68:115 */     sql.append(" coalesce(CASE WHEN c.tipoConceptoRetencion = :tipoConceptoIVA THEN 'IVA' WHEN c.tipoConceptoRetencion = :tipoConceptoFUENTE THEN 'FUENTE' WHEN c.tipoConceptoRetencion = :tipoConceptoISD THEN 'ISD' ELSE '' END,'NA'), ");
/*  69:116 */     sql.append(" CONCAT(f.establecimientoRetencion,'-',f.puntoEmisionRetencion,'-',f.numeroRetencion), ");
/*  70:117 */     sql.append(" f.baseImponibleTarifaCero, f.baseImponibleDiferenteCero, f.baseImponibleNoObjetoIva, f.nombreProveedor, f.montoIva, ");
/*  71:118 */     sql.append(" f.montoIce, ct.codigo, f.autorizacionRetencion, f.fechaRegistro, f.autorizacion, f.claveAcceso, f.estado,f.idFacturaProveedorSRI, fp.descripcion ");
/*  72:119 */     sql.append(" FROM DetalleFacturaProveedorSRI dfp ");
/*  73:120 */     sql.append(" RIGHT OUTER JOIN dfp.conceptoRetencionSRI c ");
/*  74:121 */     sql.append(" RIGHT OUTER JOIN dfp.facturaProveedorSRI f ");
/*  75:122 */     sql.append(" LEFT OUTER JOIN f.facturaProveedor fp ");
/*  76:123 */     sql.append(" LEFT  JOIN f.creditoTributarioSRI ct ");
/*  77:124 */     sql.append(" WHERE MONTH(f.fechaRegistro) =:mes AND f.documentoModificado IS NULL ");
/*  78:125 */     sql.append(" AND YEAR(f.fechaRegistro) =:anio ");
/*  79:126 */     sql.append(" AND f.estado!=:estadoAnulado ");
/*  80:127 */     sql.append(" AND f.indicadorSaldoInicial!=true ");
/*  81:128 */     sql.append(" AND f.idOrganizacion = :idOrganizacion ");
/*  82:129 */     if (sucursalFP != null) {
/*  83:130 */       sql.append(" AND f.idSucursal = :idSucursal ");
/*  84:    */     }
/*  85:132 */     if (sucursalRetencion != null) {
/*  86:133 */       sql.append(" AND f.establecimientoRetencion = :sucursalRetencion ");
/*  87:    */     }
/*  88:135 */     if (puntoVentaRetencion != null) {
/*  89:136 */       sql.append(" AND f.puntoEmisionRetencion = :puntoVentaRetencion ");
/*  90:    */     }
/*  91:138 */     sql.append(" GROUP BY f.idFacturaProveedorSRI, c.codigo, f.numero, f.puntoEmision, ");
/*  92:139 */     sql.append(" f.establecimiento, f.identificacionProveedor, ");
/*  93:140 */     sql.append(" c.descripcion, dfp.baseImponibleRetencion, dfp.porcentajeRetencion, dfp.valorRetencion, f.fechaEmisionRetencion, f.fechaRegistro, f.fechaEmision, f.numeroRetencion, ");
/*  94:141 */     sql.append(" f.baseImponibleTarifaCero, f.baseImponibleDiferenteCero, f.baseImponibleNoObjetoIva, f.nombreProveedor, f.montoIva, ");
/*  95:142 */     sql.append(" f.montoIce, ct.codigo, f.autorizacionRetencion, f.fechaRegistro, c.tipoConceptoRetencion, f.autorizacion, f.claveAcceso, f.estado, ");
/*  96:143 */     sql.append(" f.establecimientoRetencion, f.puntoEmisionRetencion, f.numeroRetencion, fp.descripcion ");
/*  97:144 */     if ((orden != null) && (orden.equals("POR_RETENCION"))) {
/*  98:145 */       sql.append(" ORDER BY f.establecimientoRetencion, f.puntoEmisionRetencion, f.numeroRetencion ");
/*  99:146 */     } else if ((orden != null) && (orden.equals("POR_FACTURA"))) {
/* 100:147 */       sql.append(" ORDER BY f.establecimiento, f.puntoEmision, f.numero ");
/* 101:148 */     } else if ((orden != null) && (orden.equals("POR_CONCEPTO"))) {
/* 102:149 */       sql.append(" ORDER BY c.descripcion ");
/* 103:    */     }
/* 104:151 */     Query query = this.em.createQuery(sql.toString());
/* 105:152 */     query.setParameter("mes", Integer.valueOf(mes));
/* 106:153 */     query.setParameter("anio", Integer.valueOf(anio));
/* 107:154 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 108:155 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 109:156 */     query.setParameter("tipoConceptoIVA", TipoConceptoRetencion.IVA);
/* 110:157 */     query.setParameter("tipoConceptoFUENTE", TipoConceptoRetencion.FUENTE);
/* 111:158 */     query.setParameter("tipoConceptoISD", TipoConceptoRetencion.ISD);
/* 112:159 */     if (sucursalFP != null) {
/* 113:160 */       query.setParameter("idSucursal", Integer.valueOf(sucursalFP.getId()));
/* 114:    */     }
/* 115:162 */     if (sucursalRetencion != null) {
/* 116:163 */       query.setParameter("sucursalRetencion", sucursalRetencion.getCodigo());
/* 117:    */     }
/* 118:165 */     if (puntoVentaRetencion != null) {
/* 119:166 */       query.setParameter("puntoVentaRetencion", puntoVentaRetencion.getCodigo());
/* 120:    */     }
/* 121:169 */     return query.getResultList();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public List<Object[]> getRetencionesAnuladas(int mes, int anio, int idOrganizacion, Sucursal sucursalFP, Sucursal sucursalRetencion, PuntoDeVenta puntoVentaRetencion, String orden, TipoComprobanteSRI tipoComprobante)
/* 125:    */   {
/* 126:175 */     StringBuilder sql = new StringBuilder();
/* 127:    */     
/* 128:177 */     sql.append(" SELECT a.fechaEmisionDocumento, f.fechaRegistro, f.fechaEmision, CONCAT(f.establecimiento,'-',f.puntoEmision,'-',f.numero), f.identificacionProveedor, ");
/* 129:    */     
/* 130:179 */     sql.append(" \t'', f.montoIva*0, f.montoIva*0, f.montoIva*0, '', '', ");
/* 131:    */     
/* 132:181 */     sql.append(" \tCONCAT(a.establecimiento,'-',a.puntoEmision,'-',a.numeroDesde), f.montoIva*0, f.montoIva*0, f.montoIva*0, f.nombreProveedor, ");
/* 133:    */     
/* 134:183 */     sql.append("\tf.montoIva*0, f.montoIva*0, '', a.autorizacion, f.fechaRegistro, f.autorizacion, '', 'ANULADO', f.idFacturaProveedorSRI,'' ");
/* 135:184 */     sql.append(" FROM AnuladoSRI a, FacturaProveedorSRI f ");
/* 136:185 */     sql.append(" WHERE a.documentoRelacionado = f.idFacturaProveedorSRI ");
/* 137:186 */     sql.append(" AND a.tipoComprobanteSRI = :tipoComprobante ");
/* 138:187 */     sql.append(" AND a.anio = :anio AND a.mes = :mes ");
/* 139:188 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/* 140:189 */     if (sucursalFP != null) {
/* 141:190 */       sql.append(" AND f.idSucursal = :idSucursal ");
/* 142:    */     }
/* 143:192 */     if (sucursalRetencion != null) {
/* 144:193 */       sql.append(" AND a.establecimiento = :sucursalRetencion ");
/* 145:    */     }
/* 146:195 */     if (puntoVentaRetencion != null) {
/* 147:196 */       sql.append(" AND a.puntoEmision = :puntoVentaRetencion ");
/* 148:    */     }
/* 149:198 */     Query query = this.em.createQuery(sql.toString());
/* 150:199 */     query.setParameter("mes", Integer.valueOf(mes));
/* 151:200 */     query.setParameter("anio", Integer.valueOf(anio));
/* 152:201 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 153:202 */     query.setParameter("tipoComprobante", tipoComprobante);
/* 154:203 */     if (sucursalFP != null) {
/* 155:204 */       query.setParameter("idSucursal", Integer.valueOf(sucursalFP.getId()));
/* 156:    */     }
/* 157:206 */     if (sucursalRetencion != null) {
/* 158:207 */       query.setParameter("sucursalRetencion", sucursalRetencion.getCodigo());
/* 159:    */     }
/* 160:209 */     if (puntoVentaRetencion != null) {
/* 161:210 */       query.setParameter("puntoVentaRetencion", puntoVentaRetencion.getCodigo());
/* 162:    */     }
/* 163:213 */     return query.getResultList();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public List<ReporteComprasVentasRetenciones> getReporteCompras(int mes, int anio, int idOrganizacion)
/* 167:    */   {
/* 168:227 */     StringBuffer sql = new StringBuffer();
/* 169:    */     
/* 170:229 */     sql.append(" SELECT new ReporteComprasVentasRetenciones(tc.codigo, tc.nombre, COUNT(tc.codigo), ");
/* 171:230 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fps.baseImponibleTarifaCero) ELSE SUM(fps.baseImponibleTarifaCero) END), ");
/* 172:231 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fps.baseImponibleDiferenteCero) ELSE SUM(fps.baseImponibleDiferenteCero) END), ");
/* 173:232 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fps.baseImponibleNoObjetoIva) ELSE SUM(fps.baseImponibleNoObjetoIva) END), ");
/* 174:233 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fps.montoIva) ELSE SUM(fps.montoIva) END), 'Compras',ct.codigo,ct.nombre) ");
/* 175:234 */     sql.append(" FROM FacturaProveedorSRI fps ");
/* 176:235 */     sql.append(" LEFT OUTER JOIN fps.tipoComprobanteSRI tc ");
/* 177:236 */     sql.append(" LEFT OUTER JOIN fps.creditoTributarioSRI ct");
/* 178:237 */     sql.append(" WHERE MONTH(fps.fechaRegistro) =:mes ");
/* 179:238 */     sql.append(" AND YEAR(fps.fechaRegistro) =:anio ");
/* 180:239 */     sql.append(" AND fps.estado!=:estadoAnulado ");
/* 181:240 */     sql.append(" AND fps.indicadorSaldoInicial!=true ");
/* 182:241 */     sql.append(" AND fps.idOrganizacion = :idOrganizacion ");
/* 183:242 */     sql.append(" GROUP BY tc.codigo, tc.nombre,ct.codigo,ct.nombre");
/* 184:243 */     sql.append(" ORDER BY tc.codigo, tc.nombre ");
/* 185:    */     
/* 186:245 */     Query query = this.em.createQuery(sql.toString());
/* 187:246 */     query.setParameter("mes", Integer.valueOf(mes));
/* 188:247 */     query.setParameter("anio", Integer.valueOf(anio));
/* 189:248 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 190:249 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 191:    */     
/* 192:251 */     return query.getResultList();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<ReporteComprasVentasRetenciones> getReporteVentas(int mes, int anio, int idOrganizacion, boolean electronicas)
/* 196:    */   {
/* 197:264 */     StringBuffer sql = new StringBuffer();
/* 198:    */     
/* 199:266 */     sql.append(" SELECT new ReporteComprasVentasRetenciones(tc.codigo, tc.nombre, COUNT(tc.codigo), ");
/* 200:267 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fcs.baseImponibleTarifaCero) ELSE SUM(fcs.baseImponibleTarifaCero) END), ");
/* 201:268 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fcs.baseImponibleDiferenteCero) ELSE SUM(fcs.baseImponibleDiferenteCero) END), ");
/* 202:269 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fcs.baseImponibleNoObjetoIva) ELSE SUM(fcs.baseImponibleNoObjetoIva) END), ");
/* 203:270 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fcs.montoIva) ELSE SUM(fcs.montoIva) END), 'Ventas', (CASE WHEN tc.codigo = '41' THEN '02' ELSE '01' END),");
/* 204:271 */     sql.append(" (CASE WHEN tc.codigo = '04' THEN -SUM(fc.descuentoImpuesto) ELSE SUM(fc.descuentoImpuesto) END))");
/* 205:272 */     sql.append(" FROM FacturaClienteSRI fcs ");
/* 206:273 */     sql.append(" JOIN fcs.facturaCliente fc  ");
/* 207:274 */     sql.append(" LEFT OUTER JOIN fcs.tipoComprobanteSRI tc ");
/* 208:275 */     sql.append(" LEFT OUTER JOIN fc.documento d ");
/* 209:276 */     sql.append(" LEFT OUTER JOIN fc.facturaClientePadre fcpadre ");
/* 210:277 */     sql.append(" LEFT OUTER JOIN fcpadre.documento dpadre ");
/* 211:278 */     sql.append(" WHERE MONTH(fcs.fechaEmision) =:mes ");
/* 212:279 */     sql.append(" AND YEAR(fcs.fechaEmision) =:anio ");
/* 213:280 */     sql.append(" AND fc.estado!=:estadoAnulado ");
/* 214:281 */     sql.append(" AND fcs.indicadorSaldoInicial!=true ");
/* 215:282 */     sql.append(" AND fcs.idOrganizacion = :idOrganizacion ");
/* 216:283 */     sql.append(" AND COALESCE(dpadre.indicadorDocumentoExterior, d.indicadorDocumentoExterior) = false ");
/* 217:284 */     sql.append(" AND fcs.indicadorDocumentoElectronico = :fisicas ");
/* 218:285 */     sql.append(" GROUP BY tc.codigo, tc.nombre ");
/* 219:286 */     sql.append(" ORDER BY tc.codigo, tc.nombre ");
/* 220:    */     
/* 221:288 */     Query query = this.em.createQuery(sql.toString());
/* 222:289 */     query.setParameter("mes", Integer.valueOf(mes));
/* 223:290 */     query.setParameter("anio", Integer.valueOf(anio));
/* 224:291 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 225:292 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 226:293 */     query.setParameter("fisicas", Boolean.valueOf(electronicas));
/* 227:294 */     return query.getResultList();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<ReporteComprasVentasRetenciones> getReporteRetencionClientes(int mes, int anio, int idOrganizacion)
/* 231:    */   {
/* 232:307 */     StringBuffer sql = new StringBuffer();
/* 233:308 */     sql.append("SELECT new ReporteComprasVentasRetenciones(fp.codigo, fp.nombre, COUNT(fp.codigo),");
/* 234:309 */     sql.append(" (CASE WHEN fp.indicadorRetencionIva = true OR fp.indicadorRetencionFuente = true THEN SUM(dcfc.valor) END),");
/* 235:310 */     sql.append(" 'Ventas', '' )");
/* 236:311 */     sql.append(" FROM DetalleCobroFormaCobro dcfc");
/* 237:312 */     sql.append(" JOIN dcfc.detalleFormaCobro dfc");
/* 238:313 */     sql.append(" LEFT JOIN dfc.cobro c");
/* 239:314 */     sql.append(" LEFT OUTER JOIN dfc.formaPago fp");
/* 240:315 */     sql.append(" LEFT OUTER JOIN dcfc.detalleCobro dc");
/* 241:316 */     sql.append(" LEFT OUTER JOIN dc.cuentaPorCobrar cpc");
/* 242:317 */     sql.append(" LEFT OUTER JOIN cpc.facturaCliente fc");
/* 243:318 */     sql.append(" WHERE MONTH(c.fecha) =:mes");
/* 244:319 */     sql.append(" AND YEAR(c.fecha) =:anio");
/* 245:320 */     sql.append(" AND fc.estado!=:estadoAnulado");
/* 246:321 */     sql.append(" AND c.estado!=:estadoAnulado");
/* 247:322 */     sql.append(" AND (fp.indicadorRetencionIva=true OR fp.indicadorRetencionFuente=true)");
/* 248:323 */     sql.append(" AND fc.idOrganizacion = :idOrganizacion");
/* 249:324 */     sql.append(" GROUP BY fp.codigo, fp.nombre, fp.indicadorRetencionIva, fp.indicadorRetencionFuente");
/* 250:325 */     sql.append(" ORDER BY fp.codigo, fp.nombre");
/* 251:326 */     Query query = this.em.createQuery(sql.toString());
/* 252:327 */     query.setParameter("mes", Integer.valueOf(mes));
/* 253:328 */     query.setParameter("anio", Integer.valueOf(anio));
/* 254:329 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 255:330 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 256:331 */     return query.getResultList();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<ReporteComprasVentasRetenciones> getReporteExportaciones(int mes, int anio, int idOrganizacion)
/* 260:    */   {
/* 261:344 */     StringBuffer sql = new StringBuffer();
/* 262:345 */     sql.append("SELECT new ReporteComprasVentasRetenciones(tc.codigo, tc.nombre, COUNT(tc.codigo), ");
/* 263:346 */     sql.append(" (CASE WHEN tc.codigo = '04' and COALESCE(dpadre.indicadorDocumentoExterior, false) = true THEN -SUM(fcs.baseImponibleTarifaCero) ELSE SUM(fcs.valorFobRefrendo) END),  ");
/* 264:347 */     sql.append(" 'Exportaciones')");
/* 265:348 */     sql.append(" FROM FacturaClienteSRI fcs ");
/* 266:349 */     sql.append(" LEFT OUTER JOIN fcs.tipoComprobanteSRI tc ");
/* 267:350 */     sql.append(" LEFT OUTER JOIN fcs.facturaCliente fc ");
/* 268:351 */     sql.append(" LEFT OUTER JOIN fc.documento d ");
/* 269:352 */     sql.append(" LEFT OUTER JOIN fc.facturaClientePadre fcpadre ");
/* 270:353 */     sql.append(" LEFT OUTER JOIN fcpadre.documento dpadre ");
/* 271:354 */     sql.append(" WHERE MONTH(fcs.fechaEmision) =:mes ");
/* 272:355 */     sql.append(" AND YEAR(fcs.fechaEmision) =:anio ");
/* 273:356 */     sql.append(" AND fc.estado!=:estadoAnulado ");
/* 274:357 */     sql.append(" AND fc.indicadorSaldoInicial!=true ");
/* 275:358 */     sql.append(" AND COALESCE(dpadre.indicadorDocumentoExterior, d.indicadorDocumentoExterior) = true ");
/* 276:359 */     sql.append(" AND fc.idOrganizacion = :idOrganizacion");
/* 277:360 */     sql.append(" GROUP BY tc.codigo, tc.nombre, dpadre.indicadorDocumentoExterior ");
/* 278:361 */     sql.append(" ORDER BY tc.codigo, tc.nombre ");
/* 279:    */     
/* 280:363 */     Query query = this.em.createQuery(sql.toString());
/* 281:364 */     query.setParameter("mes", Integer.valueOf(mes));
/* 282:365 */     query.setParameter("anio", Integer.valueOf(anio));
/* 283:366 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 284:367 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 285:368 */     return query.getResultList();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<ReporteComprasVentasRetenciones> getNumeroComprobantesAnulados(int mes, int anio, int idOrganizacion)
/* 289:    */   {
/* 290:381 */     StringBuffer sql = new StringBuffer();
/* 291:382 */     sql.append(" SELECT new ReporteComprasVentasRetenciones(tc.codigo, tc.nombre, SUM(CASE WHEN a.numeroRegistrosAnulados IS NULL THEN 0 ELSE a.numeroRegistrosAnulados END),   ");
/* 292:383 */     sql.append("  'Anulados' ) ");
/* 293:384 */     sql.append(" FROM AnuladoSRI a ");
/* 294:385 */     sql.append(" LEFT JOIN a.tipoComprobanteSRI tc ");
/* 295:386 */     sql.append(" WHERE a.mes = :mes ");
/* 296:387 */     sql.append(" AND a.anio = :anio ");
/* 297:388 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/* 298:389 */     sql.append(" GROUP BY tc.codigo, tc.nombre ");
/* 299:    */     
/* 300:391 */     Query query = this.em.createQuery(sql.toString());
/* 301:392 */     query.setParameter("mes", Integer.valueOf(mes));
/* 302:393 */     query.setParameter("anio", Integer.valueOf(anio));
/* 303:394 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 304:    */     
/* 305:396 */     return query.getResultList();
/* 306:    */   }
/* 307:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.SRI.ReporteRetencionSRIDao
 * JD-Core Version:    0.7.0.1
 */