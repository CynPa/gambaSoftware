/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.activos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Comparator;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.Query;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class ReporteActivoFijoDao
/*  20:    */   extends AbstractDaoAS2<ActivoFijo>
/*  21:    */ {
/*  22:    */   public ReporteActivoFijoDao()
/*  23:    */   {
/*  24: 42 */     super(ActivoFijo.class);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public List getReporteActivoFijo(int idActivoFijo)
/*  28:    */   {
/*  29: 47 */     String sql = " SELECT af.codigo, af.nombre, af.codigoBarras,af.numeroParte,af.numeroSerie,  af.fechaFacturaProveedor,af.numeroFacturaProveedor,  af.valorActivo,af.valorDepreciado,af.valorCompraRelacionada,  af.valorAdicional,af.fechaBaja,af.descripcion,ca.codigo,  ca.nombre,ccaf.codigo,ccaf.nombre,ccd.codigo,ccd.nombre,ccda.codigo,  ccda.nombre,ccspr.codigo,ccspr.nombre,ccdpr.codigo,ccdpr.nombre, mb.codigo,mb.nombre,ccmba.codigo,ccmba.nombre,  p.codigo, p.codigoAlterno,p.codigoComercial,p.nombre,p.nombreComercial  FROM ActivoFijo af  INNER JOIN af.categoriaActivo ca  LEFT OUTER JOIN ca.cuentaContableActivoFijo ccaf  LEFT OUTER JOIN ca.cuentaContableDepreciacion ccd  LEFT OUTER JOIN ca.cuentaContableDepreciacionAcumulada ccda  LEFT OUTER JOIN ca.cuentaContableSuperavitPorRevalorizacion ccspr  LEFT OUTER JOIN ca.cuentaContableDeficitPorRevalorizacion ccdpr  LEFT OUTER JOIN af.motivoBajaActivo mb  LEFT OUTER JOIN mb.cuentaContableMotivoBajaActivo ccmba  LEFT OUTER JOIN af.producto p  WHERE af.idActivoFijo = :idActivoFijo ";
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
/*  42: 60 */     Query query = this.em.createQuery(sql);
/*  43: 61 */     query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/*  44: 62 */     return query.getResultList();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List getReporteBajaActivoFijo(int idMotivoBajaActivo, boolean indicadorDepreciacionFiscal, Date fechaDesde, Date fechaHasta)
/*  48:    */   {
/*  49: 67 */     String sql = " SELECT  af.codigo, af.nombre,  af.valorActivo,af.valorDepreciado,af.valorCompraRelacionada,  af.valorAdicional,sum(CASE WHEN hdd IS NOT NULL then COALESCE(dd.valor,0) else 0 end), af.fechaBaja,  mb.codigo,mb.nombre,ca.codigo,ca.nombre  FROM DetalleDepreciacion dd  INNER JOIN dd.depreciacion d  INNER JOIN d.activoFijo af  INNER JOIN af.motivoBajaActivo mb  INNER JOIN af.categoriaActivo ca  LEFT JOIN dd.historicoDepreciacion hdd  WHERE d.activo = false  AND d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal  AND (mb.idMotivoBajaActivo = :idMotivoBajaActivo OR :idMotivoBajaActivo = 0)  AND af.fechaBaja BETWEEN :fechaDesde AND :fechaHasta  GROUP BY af.codigo, af.nombre,af.valorActivo,af.valorDepreciado,af.valorCompraRelacionada,af.valorAdicional,af.fechaBaja,mb.codigo,mb.nombre,ca.codigo,ca.nombre  ";
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 83 */     Query query = this.em.createQuery(sql);
/*  66: 84 */     query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(indicadorDepreciacionFiscal));
/*  67: 85 */     query.setParameter("idMotivoBajaActivo", Integer.valueOf(idMotivoBajaActivo));
/*  68: 86 */     query.setParameter("fechaDesde", fechaDesde);
/*  69: 87 */     query.setParameter("fechaHasta", fechaHasta);
/*  70: 88 */     return query.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<Object[]> getReporteActivoFijoNECVSNIIF(int anio, int mes, CategoriaActivo categoriaActivo, ActivoFijo activoFijo, int idOrganizacion, boolean indicadorFechaCompra)
/*  74:    */   {
/*  75: 94 */     HashMap<String, Object[]> hmReporte = new HashMap();
/*  76: 95 */     for (Object[] object : getReporteActivoFijo(anio, mes, categoriaActivo, activoFijo, idOrganizacion, indicadorFechaCompra, "TRUE"))
/*  77:    */     {
/*  78: 97 */       Object[] dato = new Object[26];
/*  79: 98 */       dato[0] = object[0];
/*  80: 99 */       dato[1] = object[1];
/*  81:100 */       dato[2] = object[2];
/*  82:101 */       dato[3] = object[3];
/*  83:102 */       dato[4] = object[4];
/*  84:103 */       dato[5] = object[5];
/*  85:104 */       dato[6] = object[6];
/*  86:105 */       dato[7] = object[7];
/*  87:106 */       dato[8] = object[8];
/*  88:107 */       dato[9] = object[9];
/*  89:108 */       dato[10] = object[10];
/*  90:109 */       dato[11] = object[11];
/*  91:110 */       dato[19] = object[12];
/*  92:111 */       dato[20] = object[13];
/*  93:112 */       dato[22] = object[14];
/*  94:113 */       dato[24] = object[15];
/*  95:114 */       dato[25] = Integer.valueOf(0);
/*  96:115 */       hmReporte.put((String)object[0], dato);
/*  97:    */     }
/*  98:119 */     for (Object[] object : getReporteActivoFijo(anio, mes, categoriaActivo, activoFijo, idOrganizacion, indicadorFechaCompra, "FALSE")) {
/*  99:121 */       if (hmReporte.containsKey((String)object[0]))
/* 100:    */       {
/* 101:123 */         Object[] dato = (Object[])hmReporte.get((String)object[0]);
/* 102:124 */         dato[12] = object[12];
/* 103:125 */         dato[13] = object[13];
/* 104:126 */         dato[14] = object[7];
/* 105:127 */         dato[15] = object[8];
/* 106:128 */         dato[16] = object[9];
/* 107:129 */         dato[17] = object[10];
/* 108:130 */         dato[18] = object[11];
/* 109:131 */         dato[23] = object[16];
/* 110:132 */         dato[25] = object[17];
/* 111:    */       }
/* 112:    */       else
/* 113:    */       {
/* 114:136 */         Object[] dato = new Object[24];
/* 115:137 */         dato[0] = object[0];
/* 116:138 */         dato[1] = object[1];
/* 117:139 */         dato[2] = object[2];
/* 118:140 */         dato[3] = object[3];
/* 119:141 */         dato[4] = object[4];
/* 120:142 */         dato[5] = object[5];
/* 121:143 */         dato[12] = object[12];
/* 122:144 */         dato[13] = object[13];
/* 123:145 */         dato[14] = object[7];
/* 124:146 */         dato[15] = object[8];
/* 125:147 */         dato[16] = object[9];
/* 126:148 */         dato[17] = object[10];
/* 127:149 */         dato[18] = object[11];
/* 128:150 */         dato[19] = object[14];
/* 129:151 */         dato[21] = object[15];
/* 130:152 */         dato[23] = object[16];
/* 131:153 */         hmReporte.put((String)object[0], dato);
/* 132:    */       }
/* 133:    */     }
/* 134:158 */     Object lista = new ArrayList();
/* 135:159 */     ((List)lista).addAll(hmReporte.values());
/* 136:160 */     Collections.sort((List)lista, new Comparator()
/* 137:    */     {
/* 138:    */       public int compare(Object[] o1, Object[] o2)
/* 139:    */       {
/* 140:164 */         return ((String)o1[19] + (String)o1[4] + (String)o1[1]).compareTo((String)o2[19] + (String)o2[4] + (String)o2[1]);
/* 141:    */       }
/* 142:167 */     });
/* 143:168 */     return lista;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<Object[]> getReporteActivoFijo(int anio, int mes, CategoriaActivo categoriaActivo, ActivoFijo activoFijo, int idOrganizacion, boolean indicadorFechaCompra, String indicador)
/* 147:    */   {
/* 148:175 */     Date fechaDepreciacion = FuncionesUtiles.getFechaFinMes(FuncionesUtiles.getFecha(1, mes, anio));
/* 149:    */     
/* 150:177 */     StringBuilder sql = new StringBuilder();
/* 151:    */     
/* 152:179 */     sql.append(" SELECT af.codigo,af.nombre,af.fechaFacturaProveedor,cc.nombre,sca.nombre,af.numeroFacturaProveedor, ");
/* 153:180 */     sql.append(" CASE WHEN d.indicadorDepreciacionFiscal = TRUE  THEN COALESCE(af.valorActivo,0) ELSE 0 END,");
/* 154:    */     
/* 155:182 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal = " + indicador);
/* 156:183 */     sql.append(" AND hd.mes <= :mes AND hd.anio = :anio THEN COALESCE(dd.valor,0)");
/* 157:    */     
/* 158:185 */     sql.append(" WHEN d.indicadorDepreciacionFiscal = " + indicador);
/* 159:186 */     sql.append(" AND hd.anio < :anio THEN COALESCE(dd.valor,0) ELSE 0 END),");
/* 160:    */     
/* 161:188 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal = " + indicador + " AND hd IS NOT NULL THEN 1 ELSE 0 END),");
/* 162:189 */     sql.append(" CASE WHEN d.indicadorDepreciacionFiscal = " + indicador + "  THEN COALESCE(d.vidaUtil,0) ELSE 0 END,");
/* 163:190 */     sql.append(" CASE WHEN d.indicadorDepreciacionFiscal = " + indicador + "  THEN d.valorDepreciado ELSE null END,");
/* 164:191 */     sql.append(" CASE WHEN d.indicadorDepreciacionFiscal = " + indicador + "  THEN COALESCE(d.valorResidual,0) ELSE 0 END,");
/* 165:192 */     if ("FALSE".equals(indicador))
/* 166:    */     {
/* 167:193 */       sql.append(" CASE WHEN d.indicadorDepreciacionFiscal = FALSE  THEN COALESCE(af.valorAdicional,0) ELSE 0 END,");
/* 168:194 */       sql.append(" CASE WHEN d.indicadorDepreciacionFiscal = FALSE  THEN COALESCE(af.valorCompraRelacionada,0) ELSE 0 END,");
/* 169:    */     }
/* 170:196 */     sql.append(" ca.nombre, ");
/* 171:197 */     sql.append(" (SELECT MAX(dd1.valor) FROM DetalleDepreciacion dd1 WHERE dd1.depreciacion = d AND dd1.fecha =:fechaDepreciacion),");
/* 172:    */     
/* 173:199 */     sql.append(" SUM(CASE WHEN d.indicadorDepreciacionFiscal = " + indicador + " AND hd.mes = :mes AND hd.anio = :anio THEN COALESCE(dd.valor,0) ELSE 0 END), ");
/* 174:    */     
/* 175:201 */     sql.append(" CASE WHEN d.indicadorDepreciacionFiscal = " + indicador + "  THEN COALESCE(d.vidaUtilInformativa,0) ELSE 0 END");
/* 176:202 */     sql.append(" FROM DetalleDepreciacion dd ");
/* 177:203 */     sql.append(" RIGHT JOIN dd.historicoDepreciacion hd ");
/* 178:204 */     sql.append(" RIGHT JOIN dd.depreciacion d ");
/* 179:205 */     sql.append(" INNER JOIN d.activoFijo af ");
/* 180:206 */     sql.append(" INNER JOIN af.categoriaActivo ca ");
/* 181:207 */     sql.append(" INNER JOIN af.departamento cc ");
/* 182:208 */     sql.append(" INNER JOIN af.subcategoriaActivo sca ");
/* 183:209 */     sql.append(" WHERE af.idOrganizacion=:idOrganizacion ");
/* 184:210 */     if (categoriaActivo != null) {
/* 185:211 */       sql.append(" AND ca = :categoriaActivo ");
/* 186:    */     }
/* 187:213 */     if (activoFijo != null) {
/* 188:214 */       sql.append(" AND af = :activoFijo ");
/* 189:    */     }
/* 190:216 */     sql.append(" AND (af.fechaBaja IS NULL OR af.fechaBaja > :fechaDepreciacion) ");
/* 191:217 */     sql.append(" AND (dd.fecha <= :fechaDepreciacion  OR (dd IS NULL AND d.fechaInicioDepreciacion <= :fechaDepreciacion ))");
/* 192:    */     
/* 193:219 */     sql.append(" AND d.indicadorDepreciacionFiscal = " + indicador);
/* 194:220 */     sql.append(" GROUP BY af.codigo,af.nombre,af.fechaFacturaProveedor,cc.nombre,sca.nombre,af.numeroFacturaProveedor,ca.nombre,");
/* 195:221 */     sql.append(" af.valorActivo,d.vidaUtil,d.valorDepreciado,d.valorResidual,d.indicadorDepreciacionFiscal, d.idDepreciacion, d.vidaUtilInformativa");
/* 196:222 */     if ("FALSE".equals(indicador)) {
/* 197:223 */       sql.append(", af.valorAdicional,af.valorCompraRelacionada");
/* 198:    */     }
/* 199:225 */     Query query = this.em.createQuery(sql.toString());
/* 200:226 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 201:227 */     query.setParameter("fechaDepreciacion", fechaDepreciacion);
/* 202:228 */     if (categoriaActivo != null) {
/* 203:229 */       query.setParameter("categoriaActivo", categoriaActivo);
/* 204:    */     }
/* 205:231 */     if (activoFijo != null) {
/* 206:232 */       query.setParameter("activoFijo", activoFijo);
/* 207:    */     }
/* 208:234 */     query.setParameter("mes", Integer.valueOf(mes));
/* 209:235 */     query.setParameter("anio", Integer.valueOf(anio));
/* 210:236 */     return query.getResultList();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<Object[]> listaActivoFijoFechas(Date fechaDesde, Date fechaHasta, CategoriaActivo categoriaActivo, SubcategoriaActivo subcategoriaActivo, int idOrganizacion, boolean activo)
/* 214:    */   {
/* 215:295 */     StringBuilder sql = new StringBuilder();
/* 216:    */     
/* 217:297 */     sql.append(" SELECT af.codigo, af.nombre, af.numeroFacturaProveedor,af.valorActivo , af.valorCompraRelacionada, af.valorAdicional, af.fechaFacturaProveedor, ca.nombre, sa.nombre ");
/* 218:298 */     sql.append(" FROM ActivoFijo af ");
/* 219:299 */     sql.append(" INNER JOIN af.categoriaActivo ca ");
/* 220:300 */     sql.append(" INNER JOIN af.subcategoriaActivo sa ");
/* 221:301 */     sql.append(" WHERE af.idOrganizacion = :idOrganizacion ");
/* 222:302 */     sql.append(" AND af.fechaFacturaProveedor BETWEEN :fechaDesde AND :fechaHasta ");
/* 223:303 */     sql.append(" AND (ca = :categoriaActivo OR :categoriaActivo IS NULL) ");
/* 224:304 */     sql.append(" AND (sa = :subcategoriaActivo OR :subcategoriaActivo IS NULL )");
/* 225:305 */     if (!activo) {
/* 226:306 */       sql.append(" AND af.fechaBaja is null OR :fechaHasta < af.fechaBaja ");
/* 227:    */     }
/* 228:309 */     Query query = this.em.createQuery(sql.toString());
/* 229:310 */     query.setParameter("fechaDesde", fechaDesde);
/* 230:311 */     query.setParameter("fechaHasta", fechaHasta);
/* 231:312 */     query.setParameter("categoriaActivo", categoriaActivo);
/* 232:313 */     query.setParameter("subcategoriaActivo", subcategoriaActivo);
/* 233:314 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 234:    */     
/* 235:316 */     return query.getResultList();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List<SubcategoriaActivo> listaSubcategoriaActivo(int idCategoriaActivo)
/* 239:    */   {
/* 240:322 */     StringBuilder sql = new StringBuilder();
/* 241:    */     
/* 242:324 */     sql.append(" SELECT sca ");
/* 243:325 */     sql.append(" FROM SubcategoriaActivo sca ");
/* 244:326 */     sql.append(" INNER JOIN sca.categoriaActivo ca ");
/* 245:327 */     sql.append(" WHERE ca.idCategoriaActivo = :idCategoriaActivo ");
/* 246:    */     
/* 247:329 */     Query query = this.em.createQuery(sql.toString());
/* 248:    */     
/* 249:331 */     query.setParameter("idCategoriaActivo", Integer.valueOf(idCategoriaActivo));
/* 250:    */     
/* 251:333 */     return query.getResultList();
/* 252:    */   }
/* 253:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.activos.ReporteActivoFijoDao
 * JD-Core Version:    0.7.0.1
 */