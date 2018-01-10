/*   1:    */ package com.asinfo.as2.dao.reportes.compras.importaciones;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   6:    */ import com.asinfo.as2.entities.Pais;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ReporteImportacionDao
/*  17:    */   extends AbstractDaoAS2<FacturaProveedorImportacion>
/*  18:    */ {
/*  19:    */   public ReporteImportacionDao()
/*  20:    */   {
/*  21: 43 */     super(FacturaProveedorImportacion.class);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public List getReporteFacturasProveedorImportacionPorLiquidar(Date fechaDesde, Date fechaHasta, Empresa proveedor)
/*  25:    */     throws ExcepcionAS2
/*  26:    */   {
/*  27: 54 */     StringBuilder sql = new StringBuilder();
/*  28: 55 */     sql.append(" SELECT fpp.numero, fpp.fecha, fpp.descripcion, MAX(CASE WHEN e = ee THEN fph.referencia3 ELSE '' END) , ");
/*  29: 56 */     sql.append(" MAX(CASE WHEN e = ee THEN fph.fecha ELSE null END), e.nombreFiscal, MAX(CASE WHEN e = ee THEN (fph.total+fph.impuesto-fph.descuento) ELSE 0.00 END),");
/*  30:    */     
/*  31: 58 */     sql.append(" fpi.fechaCierre, SUM(dfpi.valor), fpi.puertoEmbarque, fpi.puertoLlegada, p.nombre, fpi.informacionTransporte, fpp.estado");
/*  32: 59 */     sql.append(" FROM DetalleFacturaProveedorImportacion dfpi");
/*  33: 60 */     sql.append(" JOIN dfpi.detalleFacturaProveedor dfph ");
/*  34: 61 */     sql.append(" JOIN dfph.facturaProveedor fph ");
/*  35: 62 */     sql.append(" JOIN fph.empresa ee ");
/*  36: 63 */     sql.append(" RIGHT JOIN dfpi.facturaProveedor fpp ");
/*  37: 64 */     sql.append(" JOIN fpp.facturaProveedorImportacion fpi ");
/*  38: 65 */     sql.append(" JOIN fpp.empresa e ");
/*  39: 66 */     sql.append(" JOIN fpi.pais p ");
/*  40: 67 */     sql.append(" WHERE fpp.fecha BETWEEN :fechaDesde AND :fechaHasta AND fpp.estado <> :estadoAnulado");
/*  41: 68 */     if (proveedor != null) {
/*  42: 69 */       sql.append(" AND e = :proveedor)");
/*  43:    */     }
/*  44: 71 */     sql.append(" GROUP BY fpp.numero, fpp.fecha, fpp.descripcion, e.nombreFiscal, ");
/*  45: 72 */     sql.append(" fpi.fechaCierre,fpi.puertoEmbarque, fpi.puertoLlegada, p.nombre, fpi.informacionTransporte, fpp.estado");
/*  46: 73 */     sql.append(" ORDER BY fpp.fecha DESC ");
/*  47: 74 */     Query query = this.em.createQuery(sql.toString());
/*  48: 75 */     query.setParameter("fechaDesde", fechaDesde);
/*  49: 76 */     query.setParameter("fechaHasta", fechaHasta);
/*  50: 77 */     if (proveedor != null) {
/*  51: 78 */       query.setParameter("proveedor", proveedor);
/*  52:    */     }
/*  53: 81 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  54: 82 */     return query.getResultList();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List getReporteFacturaProveedorImportacionGasto(Date fechaDesde, Date fechaHasta, Empresa proveedor, Pais paisOrigen)
/*  58:    */   {
/*  59: 96 */     StringBuilder sql = new StringBuilder();
/*  60: 97 */     sql.append(" SELECT fp.numero, fp.fecha, e.nombreFiscal, fpi.fechaEmbarque, ");
/*  61: 98 */     sql.append(" fpi.puertoEmbarque, fpi.fechaLlegada, fpi.puertoLlegada, fpi.medioTransporteEnum, ");
/*  62: 99 */     sql.append(" p.nombre, fp.estado, gi.nombre, dfpig.tipoProrrateoEnum, dfpig.valorPresupuesto, dfpig.valorReal ");
/*  63:100 */     sql.append(" FROM DetalleFacturaProveedorImportacionGasto dfpig ");
/*  64:101 */     sql.append(" INNER JOIN dfpig.facturaProveedorImportacion fpi ");
/*  65:102 */     sql.append(" INNER JOIN fpi.facturaProveedor fp ");
/*  66:103 */     sql.append(" INNER JOIN fp.empresa e ");
/*  67:104 */     sql.append(" INNER JOIN fpi.pais p ");
/*  68:105 */     sql.append(" INNER JOIN dfpig.gastoImportacion gi ");
/*  69:106 */     sql.append(" WHERE fp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  70:107 */     if (proveedor != null) {
/*  71:108 */       sql.append(" AND e = :proveedor ");
/*  72:    */     }
/*  73:110 */     if (paisOrigen != null) {
/*  74:111 */       sql.append(" AND p = :paisOrigen ");
/*  75:    */     }
/*  76:113 */     sql.append(" ORDER BY fp DESC ");
/*  77:    */     
/*  78:115 */     Query query = this.em.createQuery(sql.toString());
/*  79:116 */     query.setParameter("fechaDesde", fechaDesde);
/*  80:117 */     query.setParameter("fechaHasta", fechaHasta);
/*  81:118 */     if (proveedor != null) {
/*  82:119 */       query.setParameter("proveedor", proveedor);
/*  83:    */     }
/*  84:121 */     if (paisOrigen != null) {
/*  85:122 */       query.setParameter("paisOrigen", paisOrigen);
/*  86:    */     }
/*  87:125 */     return query.getResultList();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List getReporteFacturaProveedorImportacionGastoDetallado(Date fechaDesde, Date fechaHasta, Empresa proveedor)
/*  91:    */   {
/*  92:138 */     StringBuilder sql = new StringBuilder();
/*  93:139 */     sql.append(" SELECT fp.numero, fp.fecha, e.nombreFiscal, ");
/*  94:140 */     sql.append(" (SELECT fpi.puertoEmbarque FROM FacturaProveedorImportacion fpi INNER JOIN fpi.facturaProveedor fpIn WHERE fpIn = fp), ");
/*  95:141 */     sql.append(" pr.codigo, pr.nombre, dfp.gastoPresupuesto, dfp.gastoReal ");
/*  96:142 */     sql.append(" FROM DetalleFacturaProveedor dfp ");
/*  97:143 */     sql.append(" INNER JOIN dfp.facturaProveedor fp ");
/*  98:144 */     sql.append(" INNER JOIN fp.empresa e ");
/*  99:145 */     sql.append(" INNER JOIN fp.documento d ");
/* 100:146 */     sql.append(" INNER JOIN dfp.producto pr ");
/* 101:147 */     sql.append(" WHERE d.indicadorDocumentoExterior = true ");
/* 102:148 */     sql.append(" AND fp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 103:149 */     if (proveedor != null) {
/* 104:150 */       sql.append(" AND e = :proveedor ");
/* 105:    */     }
/* 106:153 */     sql.append(" ORDER BY fp DESC ");
/* 107:    */     
/* 108:155 */     Query query = this.em.createQuery(sql.toString());
/* 109:156 */     query.setParameter("fechaDesde", fechaDesde);
/* 110:157 */     query.setParameter("fechaHasta", fechaHasta);
/* 111:158 */     if (proveedor != null) {
/* 112:159 */       query.setParameter("proveedor", proveedor);
/* 113:    */     }
/* 114:162 */     return query.getResultList();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<Object[]> getReporteLiquidacionFacturaImportacion(Date fechaDesde, Date fechaHasta, int idEmpresa, int idPais, int idFacturaProveedor)
/* 118:    */   {
/* 119:177 */     StringBuilder sql = new StringBuilder();
/* 120:    */     
/* 121:179 */     sql.append("SELECT fpp.numero, fpp.fecha,fpp.estado, efpp.nombreFiscal, efpp.identificacion, fpp.total, fpp.descuento, fpp.impuesto, ");
/* 122:180 */     sql.append("fpi.fechaEmbarque, fpi.puertoEmbarque, fpi.fechaLlegada, fpi.puertoLlegada, fpi.medioTransporteEnum,p.nombre, ");
/* 123:181 */     sql.append("fph.numero, fph.fecha,fph.estado, efph.nombreFiscal, efph.identificacion, fph.total, fph.descuento, fph.impuesto, ");
/* 124:182 */     sql.append("pfph.codigo, pfph.codigoComercial,pfph.nombre,u.codigo,dfph.cantidad,dfph.precio,dfph.descuento, dfph.descripcion,gi.nombre, ");
/* 125:    */     
/* 126:184 */     sql.append("coalesce(fphsri.establecimiento,''), coalesce(fphsri.puntoEmision,''), coalesce(fphsri.numero,''), ggi.nombre, dfpi.valor, fpp.descripcion, fpi.informacionTransporte, fpp.descripcion, fpp.idFacturaProveedor, fpi.numeroDUI ");
/* 127:    */     
/* 128:    */ 
/* 129:    */ 
/* 130:188 */     sql.append("FROM  DetalleFacturaProveedorImportacion dfpi ");
/* 131:189 */     sql.append("INNER JOIN dfpi.detalleFacturaProveedor dfph ");
/* 132:190 */     sql.append("INNER JOIN dfph.facturaProveedor fph ");
/* 133:191 */     sql.append("INNER JOIN fph.empresa efph  ");
/* 134:192 */     sql.append("INNER JOIN fph.documento dofph ");
/* 135:193 */     sql.append("INNER JOIN dfph.producto pfph ");
/* 136:194 */     sql.append("INNER JOIN pfph.unidadCompra u ");
/* 137:195 */     sql.append("INNER JOIN dfph.gastoImportacion gi ");
/* 138:196 */     sql.append("INNER JOIN gi.grupoGastoImportacion ggi ");
/* 139:197 */     sql.append("LEFT  JOIN fph.facturaProveedorSRI fphsri ");
/* 140:    */     
/* 141:    */ 
/* 142:200 */     sql.append("INNER JOIN dfpi.facturaProveedor fpp ");
/* 143:201 */     sql.append("INNER JOIN fpp.empresa efpp ");
/* 144:202 */     sql.append("INNER JOIN fpp.documento dofpp ");
/* 145:203 */     sql.append("INNER JOIN fpp.facturaProveedorImportacion fpi ");
/* 146:204 */     sql.append("INNER JOIN fpi.pais p ");
/* 147:    */     
/* 148:    */ 
/* 149:207 */     sql.append("WHERE dofpp.indicadorDocumentoExterior = true ");
/* 150:208 */     if ((fechaDesde != null) && (fechaHasta != null)) {
/* 151:209 */       sql.append("AND fpp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 152:    */     }
/* 153:211 */     sql.append("AND (efpp.idEmpresa = :idEmpresa OR 0=:idEmpresa) ");
/* 154:212 */     sql.append("AND (p.idPais = :idPais OR 0=:idPais) AND fph.estado <> :estadoAnulado ");
/* 155:213 */     sql.append("AND (fpp.idFacturaProveedor = :idFacturaProveedor OR 0=:idFacturaProveedor) ");
/* 156:214 */     sql.append("ORDER BY efpp.nombreFiscal, fpp.fecha, fpp.numero, ggi.nombre, fph.fecha ");
/* 157:    */     
/* 158:216 */     Query query = this.em.createQuery(sql.toString());
/* 159:217 */     if ((fechaDesde != null) && (fechaHasta != null))
/* 160:    */     {
/* 161:218 */       query.setParameter("fechaDesde", fechaDesde);
/* 162:219 */       query.setParameter("fechaHasta", fechaHasta);
/* 163:    */     }
/* 164:221 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 165:222 */     query.setParameter("idPais", Integer.valueOf(idPais));
/* 166:223 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 167:224 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 168:    */     
/* 169:226 */     return query.getResultList();
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.importaciones.ReporteImportacionDao
 * JD-Core Version:    0.7.0.1
 */