/*   1:    */ package com.asinfo.as2.dao.reportes.compras;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoOperacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.Query;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class AnalisisVencimientosProveedorDao
/*  21:    */   extends AbstractDaoAS2<FacturaProveedor>
/*  22:    */ {
/*  23:    */   public AnalisisVencimientosProveedorDao()
/*  24:    */   {
/*  25: 43 */     super(FacturaProveedor.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List getAnalisisVencimientoProveedor(Date fechaDesde, Date fechaHasta, int idEmpresa, int idOrganizacion, int idTipoOperacion, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa, boolean indicadorSoloVencido)
/*  29:    */   {
/*  30: 58 */     Date fechaMenos120 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -120);
/*  31: 59 */     Date fechaMenos90 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -90);
/*  32: 60 */     Date fechaMenos60 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -60);
/*  33: 61 */     Date fechaMenos30 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -30);
/*  34: 62 */     Date fechaMas120 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 120);
/*  35: 63 */     Date fechaMas90 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 90);
/*  36: 64 */     Date fechaMas60 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 60);
/*  37: 65 */     Date fechaMas30 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 30);
/*  38:    */     
/*  39:    */ 
/*  40: 68 */     boolean agrupadoCategoriaEmpresa = (categoriaEmpresa != null) && (categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()));
/*  41:    */     
/*  42: 70 */     StringBuilder sql = new StringBuilder();
/*  43: 71 */     sql.append(" SELECT vec.codigo,vec.nombreFiscal,vec.identificacion,vec.numeroFactura,vec.fechaFactura,vec.fechaRecepcionFactura,vec.fechaVencimiento, ");
/*  44: 72 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento <  :fechaMenos120 THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  45: 73 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos120 AND vec.fechaVencimiento < :fechaMenos90  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  46: 74 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos90  AND vec.fechaVencimiento < :fechaMenos60  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  47: 75 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos60  AND vec.fechaVencimiento < :fechaMenos30  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  48: 76 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos30  AND vec.fechaVencimiento < :fechaHasta    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  49: 77 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaHasta    AND vec.fechaVencimiento < :fechaMas30    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  50: 78 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas30    AND vec.fechaVencimiento < :fechaMas60    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  51: 79 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas60    AND vec.fechaVencimiento < :fechaMas90    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  52: 80 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas90    AND vec.fechaVencimiento < :fechaMas120   THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  53: 81 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas120   THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  54: 82 */     sql.append(" SUM(vec.debito-vec.credito), SUM(vec.debito), SUM(vec.credito), MAX(vec.descripcionFactura), MAX(asientoCompra), SUM(valorRetenido),");
/*  55: 83 */     sql.append(" vec.idCategoriaEmpresa, vec.nombreCategoriaEmpresa, ");
/*  56:    */     
/*  57: 85 */     sql.append(" '', '' ");
/*  58: 86 */     sql.append(",vec.fechaEmision ");
/*  59: 87 */     sql.append(" FROM VEstadoCuentaProveedor vec ");
/*  60: 88 */     sql.append(" WHERE EXISTS (SELECT ec2.idFacturaProveedor FROM VEstadoCuentaProveedor ec2 WHERE ec2.idFacturaProveedor=vec.idFacturaProveedor AND ec2.fechaDocumento<=:fechaHasta GROUP BY ec2.idFacturaProveedor HAVING SUM(ec2.debito-ec2.credito) <> 0 ) ");
/*  61: 89 */     sql.append(" AND (vec.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/*  62: 90 */     sql.append(" AND (vec.idTipoOperacion = :idTipoOperacion OR 0 = :idTipoOperacion) ");
/*  63: 91 */     sql.append(" AND vec.idOrganizacion = :idOrganizacion ");
/*  64: 92 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  65: 93 */       sql.append(" AND vec.idCategoriaEmpresa =:idCategoriaEmpresa ");
/*  66:    */     }
/*  67: 95 */     if (indicadorSoloVencido) {
/*  68: 96 */       sql.append(" AND vec.fechaVencimiento < :fechaHasta ");
/*  69:    */     }
/*  70: 98 */     if (fechaDesde != null)
/*  71:    */     {
/*  72: 99 */       sql.append(" AND EXISTS (SELECT ec2.idFacturaProveedor FROM VEstadoCuentaProveedor ec2 WHERE ec2.idFacturaProveedor=vec.idFacturaProveedor AND ec2.fechaFactura BETWEEN :fechaDesde AND :fechaHasta ) ");
/*  73:100 */       sql.append(" AND vec.fechaDocumento >= :fechaDesde ");
/*  74:    */     }
/*  75:102 */     sql.append(" AND vec.fechaDocumento <= :fechaHasta ");
/*  76:103 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  77:104 */       sql.append(" AND vec.idSucursal = :idSucursal");
/*  78:    */     }
/*  79:106 */     sql.append(" GROUP BY vec.codigo,vec.nombreFiscal,vec.identificacion,vec.numeroFactura,vec.fechaFactura,vec.fechaRecepcionFactura,vec.fechaVencimiento, vec.idCategoriaEmpresa, vec.nombreCategoriaEmpresa, vec.fechaEmision ");
/*  80:107 */     sql.append(" HAVING SUM(vec.debito-vec.credito) <> 0 ");
/*  81:108 */     if ((categoriaEmpresa != null) && (agrupadoCategoriaEmpresa)) {
/*  82:109 */       sql.append(" ORDER BY vec.nombreCategoriaEmpresa, vec.nombreFiscal ASC, vec.fechaVencimiento ASC, vec.numeroFactura ASC ");
/*  83:    */     } else {
/*  84:111 */       sql.append(" ORDER BY vec.nombreFiscal ASC, vec.fechaVencimiento ASC, vec.numeroFactura ASC ");
/*  85:    */     }
/*  86:114 */     Query query = this.em.createQuery(sql.toString());
/*  87:115 */     query.setParameter("fechaMenos120", fechaMenos120, TemporalType.DATE);
/*  88:116 */     query.setParameter("fechaMenos90", fechaMenos90, TemporalType.DATE);
/*  89:117 */     query.setParameter("fechaMenos60", fechaMenos60, TemporalType.DATE);
/*  90:118 */     query.setParameter("fechaMenos30", fechaMenos30, TemporalType.DATE);
/*  91:119 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  92:120 */     query.setParameter("fechaMas30", fechaMas30, TemporalType.DATE);
/*  93:121 */     query.setParameter("fechaMas60", fechaMas60, TemporalType.DATE);
/*  94:122 */     query.setParameter("fechaMas90", fechaMas90, TemporalType.DATE);
/*  95:123 */     query.setParameter("fechaMas120", fechaMas120, TemporalType.DATE);
/*  96:124 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  97:125 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  98:126 */     query.setParameter("idTipoOperacion", Integer.valueOf(idTipoOperacion));
/*  99:127 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/* 100:128 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/* 101:    */     }
/* 102:130 */     if (fechaDesde != null) {
/* 103:131 */       query.setParameter("fechaDesde", fechaDesde);
/* 104:    */     }
/* 105:133 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 106:134 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 107:    */     }
/* 108:137 */     return query.getResultList();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List getVencimientos(Empresa empresa, Date fecha, int idOrganizacion, TipoOperacion tipoOperacion, CategoriaEmpresa categoriaEmpresa, boolean indicadorSoloVencido)
/* 112:    */     throws ExcepcionAS2
/* 113:    */   {
/* 114:145 */     boolean agrupadoCategoriaEmpresa = (categoriaEmpresa != null) && (categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()));
/* 115:    */     
/* 116:147 */     StringBuilder sql = new StringBuilder();
/* 117:148 */     sql.append(" SELECT ec.codigo,ec.nombreFiscal,ec.identificacion,ec.numeroFactura,ec.fechaFactura,ec.fechaVencimiento, ");
/* 118:149 */     sql.append(" SUM(CASE WHEN ec.fechaVencimiento <= :fecha THEN (ec.debito-ec.credito) ELSE 0 END), ");
/* 119:150 */     sql.append(" SUM(CASE WHEN ec.fechaVencimiento > :fecha THEN (ec.debito-ec.credito) ELSE 0 END), ");
/* 120:151 */     sql.append(" SUM(ec.debito-ec.credito), ec.idCategoriaEmpresa, ec.nombreCategoriaEmpresa ");
/* 121:152 */     sql.append(" FROM VEstadoCuentaProveedor ec ");
/* 122:153 */     sql.append(" WHERE EXISTS (");
/* 123:154 */     sql.append(" \t\t\t\tSELECT ec2.idFacturaProveedor FROM VEstadoCuentaProveedor ec2 ");
/* 124:155 */     sql.append(" \t\t\t\tWHERE ec2.idFacturaProveedor=ec.idFacturaProveedor AND ec2.fechaDocumento<=:fecha ");
/* 125:156 */     sql.append(" \t\t\t\tGROUP BY ec2.idFacturaProveedor HAVING SUM(ec2.debito-ec2.credito) <> 0 ");
/* 126:157 */     sql.append(" \t\t\t ) ");
/* 127:158 */     sql.append(" AND ec.fechaDocumento<=:fecha ");
/* 128:159 */     sql.append(" AND ec.idOrganizacion = :idOrganizacion ");
/* 129:160 */     if (null != empresa) {
/* 130:161 */       sql.append(" AND ec.idEmpresa = :idEmpresa ");
/* 131:    */     }
/* 132:163 */     if (null != tipoOperacion) {
/* 133:164 */       sql.append(" AND ec.idTipoOperacion = :idTipoOperacion ");
/* 134:    */     }
/* 135:166 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/* 136:167 */       sql.append(" AND ec.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 137:    */     }
/* 138:169 */     if (indicadorSoloVencido) {
/* 139:170 */       sql.append(" AND ec.fechaVencimiento < :fecha ");
/* 140:    */     }
/* 141:172 */     sql.append(" GROUP BY ec.codigo,ec.nombreFiscal,ec.identificacion,ec.numeroFactura,ec.fechaFactura,ec.fechaVencimiento, ec.idCategoriaEmpresa, ec.nombreCategoriaEmpresa ");
/* 142:173 */     sql.append(" HAVING SUM(ec.debito-ec.credito) <> 0  ");
/* 143:174 */     if ((categoriaEmpresa != null) && (agrupadoCategoriaEmpresa)) {
/* 144:175 */       sql.append(" ORDER BY ec.nombreCategoriaEmpresa, ec.nombreFiscal,ec.numeroFactura ");
/* 145:    */     } else {
/* 146:177 */       sql.append(" ORDER BY ec.nombreFiscal,ec.fechaVencimiento, ec.numeroFactura ");
/* 147:    */     }
/* 148:180 */     Query query = this.em.createQuery(sql.toString());
/* 149:181 */     query.setParameter("fecha", fecha);
/* 150:182 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 151:183 */     if (null != empresa) {
/* 152:184 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 153:    */     }
/* 154:186 */     if (null != tipoOperacion) {
/* 155:187 */       query.setParameter("idTipoOperacion", Integer.valueOf(tipoOperacion.getId()));
/* 156:    */     }
/* 157:189 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/* 158:190 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/* 159:    */     }
/* 160:193 */     return query.getResultList();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<Object[]> getDatosPedido(int idOrganizacion, int idEmpresa, Date fecha)
/* 164:    */   {
/* 165:199 */     StringBuilder sql = new StringBuilder();
/* 166:200 */     sql.append(" SELECT em.codigo, COALESCE(CONCAT(fpSRI.establecimiento,'-',fpSRI.puntoEmision,'-',fpSRI.numero), fp.referencia3), pp.numero");
/* 167:201 */     sql.append(" FROM DetalleFacturaProveedor df ");
/* 168:202 */     sql.append(" JOIN df.facturaProveedor fp ");
/* 169:203 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fpSRI ");
/* 170:204 */     sql.append(" JOIN df.detallePedidoProveedor dp ");
/* 171:205 */     sql.append(" JOIN dp.pedidoProveedor pp");
/* 172:206 */     sql.append(" JOIN pp.empresa em");
/* 173:207 */     sql.append(" WHERE fp.idOrganizacion = :idOrganizacion ");
/* 174:208 */     sql.append(" AND EXISTS (SELECT ec2.idFacturaProveedor FROM VEstadoCuentaProveedor ec2 WHERE ec2.idFacturaProveedor=fp.idFacturaProveedor AND ec2.fechaDocumento<=:fecha GROUP BY ec2.idFacturaProveedor HAVING SUM(ec2.debito-ec2.credito) <> 0 ) ");
/* 175:209 */     sql.append(" AND fp.fecha<=:fecha ");
/* 176:210 */     sql.append(" AND (em.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 177:211 */     sql.append(" GROUP BY em.codigo, COALESCE(CONCAT(fpSRI.establecimiento,'-',fpSRI.puntoEmision,'-',fpSRI.numero), fp.referencia3), pp.numero ");
/* 178:    */     
/* 179:213 */     Query query = this.em.createQuery(sql.toString());
/* 180:214 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 181:215 */     query.setParameter("fecha", fecha);
/* 182:216 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 183:    */     
/* 184:218 */     return query.getResultList();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<Object[]> getDatosDimension(int idOrganizacion, int idEmpresa, Date fecha)
/* 188:    */   {
/* 189:224 */     StringBuilder sql = new StringBuilder();
/* 190:225 */     sql.append(" SELECT em.codigo, ");
/* 191:226 */     sql.append(" COALESCE(CONCAT(fpSRI.establecimiento,'-',fpSRI.puntoEmision,'-',fpSRI.numero), fp.referencia3),");
/* 192:227 */     sql.append(" COALESCE(d1.nombre,COALESCE(d2.nombre,COALESCE(d3.nombre,COALESCE(d4.nombre,COALESCE(d5.nombre,'')))))");
/* 193:228 */     sql.append(" FROM DetalleFacturaProveedor df ");
/* 194:229 */     sql.append(" JOIN df.detallePedidoProveedor dp ");
/* 195:230 */     sql.append(" JOIN df.facturaProveedor fp ");
/* 196:231 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fpSRI ");
/* 197:232 */     sql.append(" JOIN df.detallePedidoProveedor dp ");
/* 198:233 */     sql.append(" JOIN dp.pedidoProveedor pp");
/* 199:234 */     sql.append(" JOIN pp.empresa em");
/* 200:235 */     sql.append(" LEFT JOIN dp.dimensionContable1 d1 ");
/* 201:236 */     sql.append(" LEFT JOIN dp.dimensionContable1 d2 ");
/* 202:237 */     sql.append(" LEFT JOIN dp.dimensionContable1 d3 ");
/* 203:238 */     sql.append(" LEFT JOIN dp.dimensionContable1 d4 ");
/* 204:239 */     sql.append(" LEFT JOIN dp.dimensionContable1 d5 ");
/* 205:240 */     sql.append(" WHERE fp.idOrganizacion = :idOrganizacion ");
/* 206:241 */     sql.append(" AND EXISTS (SELECT ec2.idFacturaProveedor FROM VEstadoCuentaProveedor ec2 WHERE ec2.idFacturaProveedor=fp.idFacturaProveedor AND ec2.fechaDocumento<=:fecha GROUP BY ec2.idFacturaProveedor HAVING SUM(ec2.debito-ec2.credito) <> 0 ) ");
/* 207:242 */     sql.append(" AND fp.fecha<=:fecha ");
/* 208:243 */     sql.append(" AND (em.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 209:244 */     sql.append(" GROUP BY em.codigo,");
/* 210:245 */     sql.append(" COALESCE(CONCAT(fpSRI.establecimiento,'-',fpSRI.puntoEmision,'-',fpSRI.numero), fp.referencia3),");
/* 211:246 */     sql.append(" COALESCE(d1.nombre,COALESCE(d2.nombre,COALESCE(d3.nombre,COALESCE(d4.nombre,COALESCE(d5.nombre,''))))) ");
/* 212:    */     
/* 213:248 */     Query query = this.em.createQuery(sql.toString());
/* 214:249 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 215:250 */     query.setParameter("fecha", fecha);
/* 216:251 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 217:    */     
/* 218:253 */     return query.getResultList();
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.AnalisisVencimientosProveedorDao
 * JD-Core Version:    0.7.0.1
 */