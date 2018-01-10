/*    1:     */ package com.asinfo.as2.dao;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.db.AS2DBBase;
/*    4:     */ import com.asinfo.as2.entities.Banco;
/*    5:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*    6:     */ import com.asinfo.as2.entities.Departamento;
/*    7:     */ import com.asinfo.as2.entities.DimensionContable;
/*    8:     */ import com.asinfo.as2.entities.Empleado;
/*    9:     */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   10:     */ import com.asinfo.as2.entities.Organizacion;
/*   11:     */ import com.asinfo.as2.entities.PagoRol;
/*   12:     */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*   13:     */ import com.asinfo.as2.entities.Quincena;
/*   14:     */ import com.asinfo.as2.entities.Rubro;
/*   15:     */ import com.asinfo.as2.entities.Sucursal;
/*   16:     */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*   17:     */ import com.asinfo.as2.entities.TipoPermisoEmpleado;
/*   18:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   19:     */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*   20:     */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*   21:     */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   22:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   23:     */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   24:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   25:     */ import java.math.BigDecimal;
/*   26:     */ import java.util.ArrayList;
/*   27:     */ import java.util.Date;
/*   28:     */ import java.util.List;
/*   29:     */ import java.util.Map;
/*   30:     */ import javax.ejb.Stateless;
/*   31:     */ import javax.persistence.EntityManager;
/*   32:     */ import javax.persistence.NoResultException;
/*   33:     */ import javax.persistence.Query;
/*   34:     */ import javax.persistence.TemporalType;
/*   35:     */ import javax.persistence.TypedQuery;
/*   36:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   37:     */ import javax.persistence.criteria.CriteriaQuery;
/*   38:     */ import javax.persistence.criteria.Fetch;
/*   39:     */ import javax.persistence.criteria.JoinType;
/*   40:     */ import javax.persistence.criteria.Root;
/*   41:     */ import org.primefaces.model.SortOrder;
/*   42:     */ 
/*   43:     */ @Stateless
/*   44:     */ public class ReporteNominaDao
/*   45:     */   extends AS2DBBase
/*   46:     */ {
/*   47:     */   private static final String RAWTYPES = "rawtypes";
/*   48:     */   private static final String UNCKECKED = "unchecked";
/*   49:     */   
/*   50:     */   public List<PagoRolEmpleadoRubro> obtenerListaPorPaginaPagoRolEmpleadoRubro(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*   51:     */   {
/*   52:  70 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   53:  71 */     CriteriaQuery<PagoRolEmpleadoRubro> criteriaQuery = criteriaBuilder.createQuery(PagoRolEmpleadoRubro.class);
/*   54:  72 */     Root<PagoRolEmpleadoRubro> from = criteriaQuery.from(PagoRolEmpleadoRubro.class);
/*   55:  73 */     from.fetch("rubro", JoinType.LEFT);
/*   56:  74 */     Fetch<Object, Object> pagoRolEmpleado = from.fetch("pagoRolEmpleado", JoinType.LEFT);
/*   57:  75 */     Fetch<Object, Object> empleado = pagoRolEmpleado.fetch("empleado", JoinType.LEFT);
/*   58:  76 */     empleado.fetch("empresa", JoinType.LEFT);
/*   59:     */     
/*   60:  78 */     CriteriaQuery<PagoRolEmpleadoRubro> select = criteriaQuery.select(from);
/*   61:  79 */     TypedQuery<PagoRolEmpleadoRubro> typedQuery = this.em.createQuery(select);
/*   62:     */     
/*   63:  81 */     return typedQuery.getResultList();
/*   64:     */   }
/*   65:     */   
/*   66:     */   public int contarPorCriterioPagoRolEmpleadoRubro(Map<String, String> filters)
/*   67:     */   {
/*   68:  86 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   69:  87 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*   70:     */     
/*   71:  89 */     Root<PagoRolEmpleadoRubro> from = criteriaQuery.from(PagoRolEmpleadoRubro.class);
/*   72:  90 */     criteriaQuery.select(criteriaBuilder.count(from));
/*   73:     */     
/*   74:  92 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*   75:     */   }
/*   76:     */   
/*   77:     */   public List getListaIngresosEgresos(PagoRol pagoRol, FormaPagoEmpleado formaPagoEmpleado, Departamento departamento, Sucursal sucursal, int idCategoriaEmpresa, int idOrganizacion, boolean indicadorProvision, int indicadorAgrupado, boolean indicadorCodigos, DimensionContable dimensionContable)
/*   78:     */   {
/*   79: 100 */     String ordenamiento = "";
/*   80: 101 */     String nombreAgrupado = "coalesce(de.nombre,'')";
/*   81: 102 */     String codigos = "r.nombre,";
/*   82: 103 */     if (indicadorAgrupado == 1) {
/*   83: 104 */       ordenamiento = " coalesce(de.nombre,''),";
/*   84:     */     }
/*   85: 106 */     if (indicadorAgrupado == 2)
/*   86:     */     {
/*   87: 107 */       ordenamiento = " coalesce(cc.nombre,''),";
/*   88: 108 */       nombreAgrupado = "coalesce(cc.nombre,'')";
/*   89:     */     }
/*   90: 110 */     if (indicadorCodigos) {
/*   91: 111 */       codigos = "r.codigo,";
/*   92:     */     }
/*   93: 114 */     StringBuilder sql = new StringBuilder();
/*   94: 115 */     sql.append(" SELECT e.identificacion,em.apellidos,em.nombres,");
/*   95: 116 */     sql.append(codigos);
/*   96: 117 */     sql.append("prer.valor*r.operacion,r.operacion,pr.fecha,r.indicadorProvision, CASE WHEN r.indicadorProvision = true THEN '1 Provisiones' WHEN r.operacion=1 THEN '3 Ingresos' ELSE '2 Egresos' END,");
/*   97: 118 */     sql.append(" CONCAT(e.codigo,' ',em.apellidos,' ', em.nombres), r.ordenImpresion, coalesce(de.codigo,''),");
/*   98: 119 */     sql.append(nombreAgrupado);
/*   99: 120 */     sql.append(", ca.nombre, pre.diasFalta, pre.salarioAsignado, pre.baseImponibleImpuestoRenta, pre.diasTrabajados,");
/*  100: 121 */     sql.append(" prer.tiempo, CASE WHEN r.tipo=:tipoRubro THEN true ELSE prer.indicadorTiempo END , CASE WHEN r.tipo=:tipoRubro THEN '1' ELSE '0' END, de.nombre, su.nombre, cc.nombre, ");
/*  101: 122 */     sql.append(" em.genero, r.indicadorImpresionSobre, r.formula, pre.fechaIngresoEmpleado, em.indicadorPagoCash, pre.fechaSalidaEmpleado, pre.baseImponibleIEES ");
/*  102: 123 */     sql.append(" FROM PagoRolEmpleadoRubro prer");
/*  103: 124 */     sql.append(" INNER JOIN prer.rubro r ");
/*  104: 125 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre ");
/*  105: 126 */     sql.append(" LEFT JOIN pre.departamento de ");
/*  106: 127 */     sql.append(" LEFT JOIN pre.centroCosto cc ");
/*  107: 128 */     sql.append(" INNER JOIN pre.pagoRol pr ");
/*  108: 129 */     sql.append(" INNER JOIN pre.empleado em ");
/*  109: 130 */     sql.append(" INNER JOIN em.cargoEmpleado ca");
/*  110: 131 */     sql.append(" INNER JOIN em.empresa e ");
/*  111: 132 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/*  112: 133 */     sql.append(" INNER JOIN em.sucursal su ");
/*  113: 134 */     sql.append(" WHERE pr.idOrganizacion = :idOrganizacion ");
/*  114: 135 */     sql.append(" AND pr.idPagoRol = :idPagoRol");
/*  115: 136 */     sql.append(" AND prer.valor != :valorCero");
/*  116: 137 */     sql.append(" AND em.formaPagoEmpleado=:formaPagoEmpleado ");
/*  117: 138 */     sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  118: 139 */     sql.append(" AND (r.indicadorImpresionSobre = true " + (indicadorProvision ? "OR " : " AND ") + " r.indicadorProvision =:indicadorProvision) ");
/*  119: 142 */     if (idCategoriaEmpresa != 0) {
/*  120: 143 */       sql.append(" AND ce.idCategoriaEmpresa=:idCategoriaEmpresa ");
/*  121:     */     }
/*  122: 146 */     if (departamento != null) {
/*  123: 147 */       sql.append(" AND de=:departamento");
/*  124:     */     }
/*  125: 149 */     if (sucursal != null) {
/*  126: 150 */       sql.append(" AND su=:sucursal");
/*  127:     */     }
/*  128: 152 */     if (dimensionContable != null) {
/*  129: 153 */       sql.append(" AND cc=:dimensionContable");
/*  130:     */     }
/*  131: 156 */     sql.append(" ORDER BY " + ordenamiento + " em.apellidos,em.nombres");
/*  132:     */     
/*  133: 158 */     Query query = this.em.createQuery(sql.toString());
/*  134: 159 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  135: 160 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  136: 161 */     query.setParameter("indicadorProvision", Boolean.valueOf(indicadorProvision));
/*  137: 162 */     query.setParameter("formaPagoEmpleado", formaPagoEmpleado);
/*  138: 163 */     query.setParameter("valorCero", BigDecimal.ZERO);
/*  139: 164 */     query.setParameter("tipoRubro", TipoRubroEnum.PAGO_PROPINA);
/*  140: 166 */     if (departamento != null) {
/*  141: 167 */       query.setParameter("departamento", departamento);
/*  142:     */     }
/*  143: 169 */     if (sucursal != null) {
/*  144: 170 */       query.setParameter("sucursal", sucursal);
/*  145:     */     }
/*  146: 172 */     if (dimensionContable != null) {
/*  147: 173 */       query.setParameter("dimensionContable", dimensionContable);
/*  148:     */     }
/*  149: 175 */     if (idCategoriaEmpresa != 0) {
/*  150: 176 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(idCategoriaEmpresa));
/*  151:     */     }
/*  152: 179 */     List lista = query.getResultList();
/*  153: 180 */     return lista;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public List getReporteDiscapacidad(Sucursal sucursal, TipoDiscapacidad tipoDiscapacidad, Departamento departamento, Organizacion organizacion)
/*  157:     */   {
/*  158: 193 */     StringBuilder sql = new StringBuilder();
/*  159: 194 */     sql.append(" SELECT e.codigo, e.identificacion, em.apellidos, em.nombres, cae.nombre, d.nombre, td.nombre, em.porcentajeDiscapacidad ");
/*  160: 195 */     sql.append(" FROM Empleado em ");
/*  161: 196 */     sql.append(" LEFT OUTER JOIN em.empresa e ");
/*  162: 197 */     sql.append(" LEFT OUTER JOIN em.cargoEmpleado cae ");
/*  163: 198 */     sql.append(" LEFT OUTER JOIN em.tipoDiscapacidad td ");
/*  164: 199 */     sql.append(" LEFT OUTER JOIN em.departamento d ");
/*  165: 200 */     sql.append(" LEFT OUTER JOIN em.sucursal s ");
/*  166: 201 */     sql.append(" WHERE e.indicadorEmpleado = true AND em.tipoDiscapacidad IS NOT NULL");
/*  167: 202 */     sql.append(" AND (:sucursal IS NULL OR s = :sucursal) ");
/*  168: 203 */     sql.append(" AND (:tipoDiscapacidad IS NULL OR td = :tipoDiscapacidad)");
/*  169: 204 */     sql.append(" AND (:departamento IS NULL OR d = :departamento)");
/*  170: 205 */     sql.append(" AND em.activo = true and em.idOrganizacion = :idOrganizacion");
/*  171: 206 */     sql.append(" ORDER BY  em.apellidos, em.nombres ");
/*  172:     */     
/*  173:     */ 
/*  174: 209 */     Query query = this.em.createQuery(sql.toString()).setParameter("sucursal", sucursal).setParameter("tipoDiscapacidad", tipoDiscapacidad).setParameter("departamento", departamento).setParameter("idOrganizacion", Integer.valueOf(organizacion.getId()));
/*  175:     */     
/*  176: 211 */     return query.getResultList();
/*  177:     */   }
/*  178:     */   
/*  179:     */   public List getReporteDiscapacidadCargaEmpleado(Sucursal sucursal, TipoDiscapacidad tipoDiscapacidad, Departamento departamento, Organizacion organizacion)
/*  180:     */   {
/*  181: 225 */     StringBuilder sql = new StringBuilder();
/*  182: 226 */     sql.append(" SELECT e.codigo, e.identificacion, em.apellidos, em.nombres, cae.nombre, d.nombre, td.nombre, em.porcentajeDiscapacidad, ");
/*  183: 227 */     sql.append(" ce.nombre, tdc.nombre, ce.porcentajeDiscapacidad, ce.identificacion ");
/*  184: 228 */     sql.append(" FROM CargaEmpleado ce ");
/*  185: 229 */     sql.append(" LEFT OUTER JOIN ce.empleado em ");
/*  186: 230 */     sql.append(" LEFT OUTER JOIN em.empresa e ");
/*  187: 231 */     sql.append(" LEFT OUTER JOIN em.cargoEmpleado cae ");
/*  188: 232 */     sql.append(" LEFT OUTER JOIN em.tipoDiscapacidad td ");
/*  189: 233 */     sql.append(" LEFT OUTER JOIN em.departamento d ");
/*  190: 234 */     sql.append(" LEFT OUTER JOIN em.sucursal s ");
/*  191: 235 */     sql.append(" LEFT OUTER JOIN ce.tipoDiscapacidad tdc ");
/*  192: 236 */     sql.append(" WHERE e.indicadorEmpleado = true AND ce.porcentajeDiscapacidad > 0 ");
/*  193: 237 */     sql.append(" AND (:sucursal IS NULL OR s.idSucursal = :sucursal) ");
/*  194: 238 */     sql.append(" AND (:tipoDiscapacidad IS NULL OR td = :tipoDiscapacidad)");
/*  195: 239 */     sql.append(" AND (:departamento IS NULL OR d = :departamento)");
/*  196: 240 */     sql.append(" AND ce.idOrganizacion = :idOrganizacion");
/*  197: 241 */     sql.append(" ORDER BY  em.apellidos, em.nombres, ce.nombre ");
/*  198:     */     
/*  199:     */ 
/*  200: 244 */     Query query = this.em.createQuery(sql.toString()).setParameter("sucursal", sucursal).setParameter("tipoDiscapacidad", tipoDiscapacidad).setParameter("departamento", departamento).setParameter("idOrganizacion", Integer.valueOf(organizacion.getId()));
/*  201:     */     
/*  202: 246 */     return query.getResultList();
/*  203:     */   }
/*  204:     */   
/*  205:     */   public List getListaFirmas(PagoRol pagoRol, FormaPagoEmpleado formaPagoEmpleado, Sucursal sucursal, int idOrganizacion)
/*  206:     */   {
/*  207: 258 */     StringBuilder sql = new StringBuilder();
/*  208: 259 */     sql.append(" SELECT e.codigo,CONCAT(em.apellidos ,' ',em.nombres) ,e.identificacion,");
/*  209: 260 */     sql.append(" SUM(CASE WHEN r.operacion=1 THEN prer.valor ELSE 0 END) ,  SUM(CASE WHEN r.operacion=-1 THEN prer.valor ELSE 0 END),");
/*  210: 261 */     sql.append(" SUM(CASE WHEN r.indicadorProvision = true THEN prer.valor ELSE 0 END), pr.fecha FROM PagoRolEmpleadoRubro prer ");
/*  211: 262 */     sql.append(" INNER JOIN prer.rubro r INNER JOIN prer.pagoRolEmpleado pre  INNER JOIN pre.pagoRol pr ");
/*  212: 263 */     sql.append(" INNER JOIN pre.empleado em  INNER JOIN em.empresa e INNER JOIN em.sucursal su");
/*  213: 264 */     sql.append(" WHERE pr.idPagoRol=:idPagoRol AND em.formaPagoEmpleado=:formaPagoEmpleado");
/*  214: 265 */     sql.append(" AND r.indicadorImpresionSobre=true AND (su.idSucursal = :idSucursal OR :idSucursal = 0)");
/*  215: 266 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion");
/*  216: 267 */     sql.append(" AND prer.indicadorNoProcesado = FALSE");
/*  217: 268 */     sql.append(" GROUP BY em.idEmpleado,e.codigo,em.apellidos,em.nombres,e.identificacion,pr.fecha");
/*  218: 269 */     sql.append(" ORDER BY  em.apellidos, em.nombres");
/*  219:     */     
/*  220: 271 */     Query query = this.em.createQuery(sql.toString());
/*  221: 272 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  222: 273 */     query.setParameter("formaPagoEmpleado", formaPagoEmpleado);
/*  223: 274 */     query.setParameter("idSucursal", Integer.valueOf(sucursal != null ? sucursal.getIdSucursal() : 0));
/*  224: 275 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  225:     */     
/*  226: 277 */     return query.getResultList();
/*  227:     */   }
/*  228:     */   
/*  229:     */   public List getListaCuentasBancariasEmpleado(PagoRol pagoRol, FormaPagoEmpleado formaPagoEmpleado, Sucursal sucursal, int idOrganizacion, boolean aprobados, boolean indicadorPagoCash)
/*  230:     */   {
/*  231: 283 */     StringBuilder sql = new StringBuilder();
/*  232: 284 */     sql.append(" SELECT e.codigo,CONCAT(em.apellidos ,' ',em.nombres) ,e.identificacion,");
/*  233: 285 */     sql.append(" SUM(CASE WHEN r.operacion=1 THEN prer.valor ELSE 0 END) , SUM(CASE WHEN r.operacion=-1 THEN prer.valor ELSE 0 END),");
/*  234: 286 */     sql.append(" SUM(CASE WHEN r.indicadorProvision = true THEN prer.valor ELSE 0 END), (SELECT cb.numero FROM CuentaBancariaEmpresa cbe");
/*  235: 287 */     sql.append(" \tLEFT JOIN cbe.cuentaBancaria cb\tWHERE cbe.empresa=e ),  pr.fecha FROM PagoRolEmpleadoRubro prer ");
/*  236: 288 */     sql.append(" INNER JOIN prer.rubro r INNER JOIN prer.pagoRolEmpleado pre INNER JOIN pre.pagoRol pr ");
/*  237: 289 */     sql.append(" INNER JOIN pre.empleado em INNER JOIN em.empresa e INNER JOIN em.sucursal su");
/*  238: 290 */     sql.append(" WHERE pr.idPagoRol=:idPagoRol AND em.formaPagoEmpleado=:formaPagoEmpleado");
/*  239: 291 */     sql.append(" AND r.indicadorImpresionSobre=true ");
/*  240: 292 */     sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  241: 293 */     sql.append(" AND (su.idSucursal = :idSucursal OR :idSucursal = 0)");
/*  242: 294 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion");
/*  243: 295 */     sql.append(" AND prer.indicadorNoProcesado = FALSE");
/*  244: 296 */     if (aprobados) {
/*  245: 297 */       sql.append(" AND pre.indicadorAprobado = true ");
/*  246:     */     }
/*  247: 299 */     if (indicadorPagoCash) {
/*  248: 300 */       sql.append(" AND em.indicadorPagoCash = true ");
/*  249:     */     }
/*  250: 302 */     sql.append(" GROUP BY em.idEmpleado,e.codigo,em.apellidos,em.nombres,e.identificacion,e.idEmpresa,pr.fecha");
/*  251: 303 */     sql.append(" ORDER BY  em.apellidos, em.nombres");
/*  252:     */     
/*  253: 305 */     Query query = this.em.createQuery(sql.toString());
/*  254: 306 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  255: 307 */     query.setParameter("formaPagoEmpleado", formaPagoEmpleado);
/*  256: 308 */     query.setParameter("idSucursal", Integer.valueOf(sucursal != null ? sucursal.getIdSucursal() : 0));
/*  257: 309 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  258: 310 */     return query.getResultList();
/*  259:     */   }
/*  260:     */   
/*  261:     */   public List<Object[]> getSobreEmpleado(PagoRol pagoRol, Empleado empleado, FormaPagoEmpleado formaPagoEmpleado, Sucursal sucursal, int idOrganizacion, Departamento departamento, boolean aprobados)
/*  262:     */   {
/*  263: 316 */     StringBuilder sql = new StringBuilder();
/*  264: 317 */     sql.append(" SELECT CONCAT(em.apellidos,' ',em.nombres),e.identificacion, r.operacion,r.nombre,");
/*  265: 318 */     sql.append(" CASE WHEN r.operacion=1 THEN prer.valor ELSE 0 END,  CASE WHEN r.operacion=-1 THEN prer.valor ELSE 0 END,   pr.fecha,ce.nombre,pre.diasFalta, prer.tiempo, ");
/*  266:     */     
/*  267: 320 */     sql.append(" dep.nombre,   (SELECT MAX(cb.numero) FROM CuentaBancariaEmpresa cbe LEFT JOIN cbe.empresa e1  LEFT JOIN cbe.cuentaBancaria cb  WHERE e1 = e  AND cb.predeterminado = true ),   pre.diasTrabajados, ");
/*  268:     */     
/*  269:     */ 
/*  270: 323 */     sql.append(" q.nombre, r.indicadorProvision, r.indicadorImpresionSobre, COALESCE(e.email1, ''), COALESCE( e.email2, ''), prPadre.fecha, e.codigo ");
/*  271: 324 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/*  272: 325 */     sql.append(" INNER JOIN prer.rubro r ");
/*  273: 326 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre ");
/*  274: 327 */     sql.append(" LEFT JOIN pre.departamento dep ");
/*  275: 328 */     sql.append(" INNER JOIN pre.pagoRol pr ");
/*  276: 329 */     sql.append(" INNER JOIN pr.quincena q ");
/*  277: 330 */     sql.append(" INNER JOIN pre.empleado em ");
/*  278: 331 */     sql.append(" INNER JOIN em.cargoEmpleado ce ");
/*  279: 332 */     sql.append(" INNER JOIN em.empresa e");
/*  280: 333 */     sql.append(" INNER JOIN em.sucursal su ");
/*  281: 334 */     sql.append(" LEFT JOIN prer.pagoRolEmpleadoRubroPadre prerPadre ");
/*  282: 335 */     sql.append(" LEFT JOIN prerPadre.pagoRolEmpleado prePadre");
/*  283: 336 */     sql.append(" LEFT JOIN prePadre.pagoRol prPadre");
/*  284: 337 */     sql.append(" WHERE pr.idPagoRol=:idPagoRol AND em.formaPagoEmpleado =:formaPagoEmpleado ");
/*  285: 338 */     sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  286: 339 */     if ((sucursal != null) && (sucursal.getIdSucursal() != 0)) {
/*  287: 340 */       sql.append(" AND su.idSucursal=:idSucursal ");
/*  288:     */     }
/*  289: 342 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion");
/*  290: 343 */     sql.append(" AND prer.indicadorImpresionSobre=true ");
/*  291: 344 */     if ((empleado != null) && (empleado.getIdEmpleado() != 0)) {
/*  292: 345 */       sql.append(" AND em=:empleado ");
/*  293:     */     }
/*  294: 347 */     if (departamento != null) {
/*  295: 348 */       sql.append(" AND dep = :departamento ");
/*  296:     */     }
/*  297: 350 */     if (aprobados) {
/*  298: 351 */       sql.append(" AND pre.indicadorAprobado = true ");
/*  299:     */     }
/*  300: 353 */     sql.append(" ORDER BY em.apellidos, em.nombres, r.operacion DESC, r.ordenImpresion");
/*  301:     */     
/*  302: 355 */     Query query = this.em.createQuery(sql.toString());
/*  303: 356 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  304: 357 */     if ((empleado != null) && (empleado.getIdEmpleado() != 0)) {
/*  305: 358 */       query.setParameter("empleado", empleado);
/*  306:     */     }
/*  307: 360 */     query.setParameter("formaPagoEmpleado", formaPagoEmpleado);
/*  308: 361 */     if ((sucursal != null) && (sucursal.getIdSucursal() != 0)) {
/*  309: 362 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  310:     */     }
/*  311: 364 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  312: 365 */     if (departamento != null) {
/*  313: 366 */       query.setParameter("departamento", departamento);
/*  314:     */     }
/*  315: 368 */     return query.getResultList();
/*  316:     */   }
/*  317:     */   
/*  318:     */   public List getVacacion(Departamento departamento, Empleado empleado, Sucursal sucursal, String tipoSelecionado, FormaPagoEmpleado formaPagoEmpleado, int idOrganizacion, boolean activos, Date fechaDesde, Date fechaHasta, boolean porPeriodo)
/*  319:     */   {
/*  320: 375 */     StringBuilder sql = new StringBuilder();
/*  321: 376 */     sql.append(" SELECT e.codigo,e.identificacion,CONCAT(em.apellidos,' ',em.nombres),");
/*  322: 377 */     if (porPeriodo)
/*  323:     */     {
/*  324: 378 */       sql.append(" v.fechaInicioPeriodo, v.fechaFinPeriodo, v.dias,v.diasAdicionales,");
/*  325: 379 */       sql.append(" COALESCE(SUM(CASE WHEN  (dv.estado=:estadoAprobado OR dv.estado=:estadoCerrado) THEN dv.diasTomados ELSE 0 END),0),");
/*  326: 380 */       sql.append(" COALESCE(v.dias+v.diasAdicionales-COALESCE(SUM(CASE WHEN  (dv.estado=:estadoAprobado OR dv.estado=:estadoCerrado) THEN dv.diasTomados ELSE 0 END),0),0),");
/*  327:     */     }
/*  328:     */     else
/*  329:     */     {
/*  330: 383 */       sql.append(" dv.fechaInicio, dv.fechaFin, dv.diasTomados,");
/*  331:     */     }
/*  332: 385 */     sql.append(" dv.descripcion, dv.numero");
/*  333: 386 */     sql.append(" FROM DetalleVacacion dv");
/*  334: 387 */     sql.append(" RIGHT JOIN dv.vacacion v");
/*  335: 388 */     sql.append(" INNER JOIN v.historicoEmpleado he");
/*  336: 389 */     sql.append(" INNER JOIN he.empleado em");
/*  337: 390 */     sql.append(" INNER JOIN em.empresa e");
/*  338: 391 */     sql.append(" INNER JOIN em.sucursal su");
/*  339: 392 */     sql.append(" WHERE (em.departamento.idDepartamento=:idDepartamento OR :idDepartamento=0)");
/*  340: 393 */     sql.append(" AND (su.idSucursal=:idSucursal OR :idSucursal=0)");
/*  341: 394 */     sql.append(" AND (em.idEmpleado=:idEmpleado OR :idEmpleado=0  )");
/*  342: 395 */     sql.append(" AND he.idOrganizacion = :idOrganizacion");
/*  343: 396 */     sql.append(" AND (em.formaPagoEmpleado=:formaPagoEmpleado OR :formaPagoEmpleado=0  )");
/*  344: 397 */     sql.append(" AND he.fechaSalida IS NULL");
/*  345: 398 */     if (activos) {
/*  346: 399 */       sql.append(" AND em.activo = true ");
/*  347:     */     }
/*  348: 401 */     if (fechaDesde != null) {
/*  349: 402 */       sql.append(" AND dv.fechaInicio>= :fechaDesde");
/*  350:     */     }
/*  351: 404 */     if (fechaHasta != null) {
/*  352: 405 */       sql.append(" AND dv.fechaInicio<= :fechaHasta");
/*  353:     */     }
/*  354: 407 */     if (porPeriodo)
/*  355:     */     {
/*  356: 408 */       sql.append(" GROUP BY v.idVacacion,e.codigo,e.identificacion,CONCAT(em.apellidos,' ',em.nombres), v.fechaInicioPeriodo, v.fechaFinPeriodo,");
/*  357:     */       
/*  358: 410 */       sql.append(" em.apellidos, em.nombres,dv.descripcion,v.dias,v.diasAdicionales, dv.numero");
/*  359:     */     }
/*  360: 412 */     sql.append(" ORDER BY em.apellidos, em.nombres, v.fechaInicioPeriodo ");
/*  361:     */     
/*  362: 414 */     Query query = this.em.createQuery(sql.toString());
/*  363: 415 */     if (porPeriodo)
/*  364:     */     {
/*  365: 416 */       query.setParameter("estadoAprobado", Estado.APROBADO);
/*  366: 417 */       query.setParameter("estadoCerrado", Estado.CERRADO);
/*  367:     */     }
/*  368: 419 */     int idDepartamento = departamento == null ? 0 : departamento.getIdDepartamento();
/*  369: 420 */     query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/*  370: 421 */     int idSucursal = sucursal == null ? 0 : sucursal.getIdSucursal();
/*  371: 422 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  372: 423 */     int idEmpleado = empleado == null ? 0 : empleado.getIdEmpleado();
/*  373: 424 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  374: 425 */     query.setParameter("formaPagoEmpleado", formaPagoEmpleado);
/*  375: 426 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  376: 427 */     if (fechaDesde != null) {
/*  377: 428 */       query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  378:     */     }
/*  379: 430 */     if (fechaHasta != null) {
/*  380: 431 */       query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  381:     */     }
/*  382: 433 */     return query.getResultList();
/*  383:     */   }
/*  384:     */   
/*  385:     */   public String getReporteTipoContratoPorId(int idTipoContrato)
/*  386:     */     throws ExcepcionAS2
/*  387:     */   {
/*  388:     */     try
/*  389:     */     {
/*  390: 445 */       String sql = "SELECT tc.textoContrato FROM TipoContrato tc WHERE tc.idTipoContrato = :idTipoContrato";
/*  391:     */       
/*  392: 447 */       Query query = this.em.createQuery("SELECT tc.textoContrato FROM TipoContrato tc WHERE tc.idTipoContrato = :idTipoContrato");
/*  393: 448 */       query.setParameter("idTipoContrato", Integer.valueOf(idTipoContrato));
/*  394: 449 */       return (String)query.getSingleResult();
/*  395:     */     }
/*  396:     */     catch (Exception e)
/*  397:     */     {
/*  398: 451 */       throw new ExcepcionAS2Nomina("msg_no_hay_datos", e);
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public List<Object[]> getEmpleadosPagoCash(PagoRol pagoRol, Empleado empleado, boolean requiereAprobacion, TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria, Banco banco)
/*  403:     */   {
/*  404: 458 */     StringBuilder sql = new StringBuilder();
/*  405: 459 */     sql.append(" SELECT  e.idEmpresa ,e.identificacion, SUM(prer.valor*r.operacion*100), ");
/*  406: 460 */     sql.append(" e.tipoIdentificacion.codigo, e.nombreFiscal, e.email1, ccspp.idCuentaContable, em.idEmpleado,");
/*  407: 461 */     sql.append(" tcb.codigo, em.apellidos, em.nombres, ba.nombre ");
/*  408: 462 */     sql.append(" FROM PagoRolEmpleadoRubro prer");
/*  409: 463 */     sql.append(" INNER JOIN prer.rubro r ");
/*  410: 464 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre");
/*  411: 465 */     sql.append(" INNER JOIN pre.pagoRol pr");
/*  412: 466 */     sql.append(" INNER JOIN pre.empleado em ");
/*  413: 467 */     sql.append(" INNER JOIN em.empresa e");
/*  414: 468 */     sql.append(" INNER JOIN e.categoriaEmpresa ce");
/*  415: 469 */     sql.append(" LEFT JOIN ce.cuentaContableSueldoPorPagar ccspp, CuentaBancariaEmpresa cbem");
/*  416: 470 */     sql.append(" LEFT JOIN cbem.cuentaBancaria cb ");
/*  417: 471 */     sql.append(" LEFT JOIN cb.banco ba");
/*  418:     */     
/*  419: 473 */     sql.append(" LEFT JOIN cb.tipoCuentaBancaria tcb ");
/*  420: 474 */     sql.append(" WHERE pr.idPagoRol=:idPagoRol ");
/*  421: 475 */     sql.append(" AND r.indicadorImpresionSobre=true");
/*  422: 476 */     sql.append(" AND prer.indicadorProvision=false");
/*  423: 477 */     sql.append(" AND em.indicadorPagoCash=true ");
/*  424: 478 */     sql.append(" AND pre.indicadorCobrado=false");
/*  425: 479 */     sql.append(" AND prer.valor!=0");
/*  426: 480 */     sql.append(" AND prer.indicadorNoProcesado = FALSE");
/*  427: 482 */     if (tipoServicioCuentaBancaria != null) {
/*  428: 484 */       sql.append(" AND cb.tipoServicioCuentaBancaria =:tipoServicioCuentaBancaria ");
/*  429:     */     }
/*  430: 487 */     if (requiereAprobacion) {
/*  431: 488 */       sql.append(" AND pre.indicadorAprobado=true");
/*  432:     */     }
/*  433: 491 */     if (empleado != null) {
/*  434: 492 */       sql.append(" AND em =:empleado ");
/*  435:     */     }
/*  436: 495 */     if (banco != null) {
/*  437: 496 */       sql.append(" AND ba = :banco");
/*  438:     */     }
/*  439: 499 */     sql.append(" AND e = cbem.empresa ");
/*  440: 500 */     sql.append(" AND EXISTS(SELECT cbe FROM CuentaBancariaEmpresa cbe WHERE cbe.empresa = e) ");
/*  441: 501 */     sql.append(" GROUP BY  e.idEmpresa, e.identificacion, e.tipoIdentificacion.codigo , e.nombreFiscal, e.email1,  ");
/*  442: 502 */     sql.append(" ccspp.idCuentaContable, em.idEmpleado, tcb.codigo, em.apellidos, em.nombres, ba.nombre ");
/*  443:     */     
/*  444: 504 */     Query query = this.em.createQuery(sql.toString());
/*  445: 505 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  446: 507 */     if (banco != null) {
/*  447: 508 */       query.setParameter("banco", banco);
/*  448:     */     }
/*  449: 511 */     if (tipoServicioCuentaBancaria != null) {
/*  450: 512 */       query.setParameter("tipoServicioCuentaBancaria", tipoServicioCuentaBancaria);
/*  451:     */     }
/*  452: 515 */     if (empleado != null) {
/*  453: 516 */       query.setParameter("empleado", empleado);
/*  454:     */     }
/*  455: 519 */     return query.getResultList();
/*  456:     */   }
/*  457:     */   
/*  458:     */   public List<Object[]> getDireccionTelefonoEmpleado(int idOrganizacion)
/*  459:     */   {
/*  460: 524 */     StringBuilder sql = new StringBuilder();
/*  461: 525 */     sql.append(" SELECT e.idEmpresa , CONCAT(COALESCE(ub.direccion1, 'N/A'), COALESCE(ub.direccion2, 'N/A'), COALESCE(ub.direccion3, 'N/A'), COALESCE(ub.direccion4, 'N/A')), COALESCE(de.telefono1,' '), ci.nombre ");
/*  462: 526 */     sql.append(" FROM DireccionEmpresa de INNER JOIN de.empresa e INNER JOIN e.empleado em INNER JOIN de.ubicacion ub INNER JOIN de.ciudad ci ");
/*  463: 527 */     sql.append(" WHERE de.empresa=e and de.indicadorDireccionPrincipal = true and e.idOrganizacion = :idOrganizacion");
/*  464:     */     
/*  465: 529 */     Query query = this.em.createQuery(sql.toString());
/*  466: 530 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  467: 531 */     return query.getResultList();
/*  468:     */   }
/*  469:     */   
/*  470:     */   public List<Object[]> getCuentaBancariaEmpleado()
/*  471:     */   {
/*  472: 537 */     StringBuilder sql = new StringBuilder();
/*  473: 538 */     sql.append(" SELECT e.idEmpresa, cb.numero, ba.codigo, coalesce(cb.tipoServicioCuentaBancaria, 'OT') FROM CuentaBancariaEmpresa cbe ");
/*  474: 539 */     sql.append(" INNER JOIN cbe.empresa e ");
/*  475: 540 */     sql.append(" INNER JOIN e.empleado em ");
/*  476: 541 */     sql.append(" LEFT JOIN cbe.cuentaBancaria cb ");
/*  477: 542 */     sql.append(" LEFT JOIN cb.banco ba");
/*  478: 543 */     Query query = this.em.createQuery(sql.toString());
/*  479: 544 */     return query.getResultList();
/*  480:     */   }
/*  481:     */   
/*  482:     */   public List<Object[]> getReporteRubroEmpleado(int idSucursal, int idEmpleado, int idDepartamento, List<PagoRol> listaPagoRol, List<Rubro> listaRubro, int movimiento, boolean indicadorRubroEmpleado, boolean indicadorRubroProvisionEmpleado, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/*  483:     */   {
/*  484: 551 */     StringBuilder sql = new StringBuilder();
/*  485: 552 */     sql.append("SELECT CONCAT(e.codigo,' | ',em.apellidos,' ',em.nombres),e.identificacion, r.operacion, r.nombre, ");
/*  486: 553 */     sql.append("CASE WHEN r.operacion=1 THEN prer.valor ELSE 0 END, CASE WHEN r.operacion=-1 THEN prer.valor ELSE 0 END, ");
/*  487: 554 */     sql.append("pr.fecha, pr.mes,pr.anio,CASE WHEN r.operacion=1 THEN prer.valor ELSE -prer.valor END, CONCAT(em.apellidos,' ',em.nombres), ");
/*  488: 555 */     sql.append("prer.tiempo, prer.indicadorTiempo ");
/*  489: 556 */     sql.append("FROM PagoRolEmpleadoRubro prer ");
/*  490: 557 */     sql.append("LEFT JOIN prer.rubro r ");
/*  491: 558 */     sql.append("LEFT JOIN prer.pagoRolEmpleado pre ");
/*  492: 559 */     sql.append("LEFT JOIN pre.departamento d ");
/*  493: 560 */     sql.append("LEFT JOIN pre.pagoRol pr ");
/*  494: 561 */     sql.append("LEFT JOIN pre.empleado em ");
/*  495: 562 */     sql.append("LEFT JOIN em.empresa e ");
/*  496: 563 */     sql.append("LEFT JOIN e.categoriaEmpresa ce ");
/*  497: 564 */     sql.append("LEFT JOIN em.sucursal su ");
/*  498: 565 */     sql.append("WHERE pr IN (:listaPagoRol) ");
/*  499: 566 */     sql.append("AND pr.idOrganizacion = :idOrganizacion ");
/*  500: 567 */     sql.append("AND (su.idSucursal=:idSucursal OR :idSucursal=0) ");
/*  501: 568 */     sql.append("AND (em.idEmpleado=:idEmpleado OR :idEmpleado=0) ");
/*  502: 569 */     sql.append("AND (d.idDepartamento=:idDepartamento OR :idDepartamento=0) ");
/*  503: 570 */     sql.append("AND prer.valor != 0 ");
/*  504: 571 */     sql.append("AND prer.indicadorNoProcesado = FALSE ");
/*  505: 573 */     if (indicadorRubroProvisionEmpleado) {
/*  506: 574 */       sql.append("AND r.indicadorProvision=true ");
/*  507:     */     } else {
/*  508: 576 */       sql.append(" AND (r.indicadorImpresionSobre=true)");
/*  509:     */     }
/*  510: 579 */     if (!listaRubro.isEmpty()) {
/*  511: 580 */       sql.append("AND r IN (:listaRubro) ");
/*  512:     */     }
/*  513: 582 */     if (categoriaEmpresa != null) {
/*  514: 583 */       sql.append(" AND ce.idCategoriaEmpresa=:idCategoriaEmpresa ");
/*  515:     */     }
/*  516: 585 */     if ((movimiento == 1) || (movimiento == -1)) {
/*  517: 586 */       sql.append("AND r.operacion = :movimiento ");
/*  518:     */     }
/*  519: 589 */     if (indicadorRubroEmpleado) {
/*  520: 590 */       sql.append("ORDER BY em.apellidos, em.nombres ");
/*  521:     */     } else {
/*  522: 592 */       sql.append("ORDER BY pr.fecha, r.nombre, em.apellidos, em.nombres ");
/*  523:     */     }
/*  524: 596 */     Query query = this.em.createQuery(sql.toString());
/*  525: 597 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  526: 598 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  527: 599 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  528:     */     
/*  529: 601 */     query.setParameter("listaPagoRol", listaPagoRol);
/*  530: 602 */     query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/*  531: 604 */     if (!listaRubro.isEmpty()) {
/*  532: 605 */       query.setParameter("listaRubro", listaRubro);
/*  533:     */     }
/*  534: 607 */     if (categoriaEmpresa != null) {
/*  535: 608 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/*  536:     */     }
/*  537: 610 */     if ((movimiento == 1) || (movimiento == -1)) {
/*  538: 611 */       query.setParameter("movimiento", Integer.valueOf(movimiento));
/*  539:     */     }
/*  540: 614 */     return query.getResultList();
/*  541:     */   }
/*  542:     */   
/*  543:     */   public List<Object[]> getReporteRubroMensual(int idSucursal, int idEmpleado, int idDepartamento, List<PagoRol> listaPagoRol, List<Rubro> listaRubro, int movimiento, boolean indicadorRubroEmpleado, boolean indicadorRubroProvisionEmpleado, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/*  544:     */   {
/*  545: 621 */     StringBuilder sql = new StringBuilder();
/*  546: 622 */     sql.append("SELECT r.nombre, pr.mes, coalesce( SUM(prer.valor*r.operacion),0) ");
/*  547: 623 */     sql.append("FROM PagoRolEmpleadoRubro prer ");
/*  548: 624 */     sql.append("LEFT JOIN prer.rubro r ");
/*  549: 625 */     sql.append("LEFT JOIN prer.pagoRolEmpleado pre ");
/*  550: 626 */     sql.append("LEFT JOIN pre.departamento d ");
/*  551: 627 */     sql.append("LEFT JOIN pre.pagoRol pr ");
/*  552: 628 */     sql.append("LEFT JOIN pre.empleado em ");
/*  553: 629 */     sql.append("LEFT JOIN em.empresa e ");
/*  554: 630 */     sql.append("LEFT JOIN e.categoriaEmpresa ce ");
/*  555: 631 */     sql.append("LEFT JOIN em.sucursal su ");
/*  556: 632 */     sql.append("WHERE pr IN (:listaPagoRol) ");
/*  557: 633 */     sql.append("AND pr.idOrganizacion = :idOrganizacion ");
/*  558: 634 */     sql.append("AND (su.idSucursal=:idSucursal OR :idSucursal=0) ");
/*  559: 635 */     sql.append("AND (em.idEmpleado=:idEmpleado OR :idEmpleado=0) ");
/*  560: 636 */     sql.append("AND (d.idDepartamento=:idDepartamento OR :idDepartamento=0) ");
/*  561: 637 */     sql.append("AND ((r.indicadorImpresionSobre = true) OR (r.indicadorProvision =:indicadorRubroProvisionEmpleado))");
/*  562: 638 */     sql.append("AND prer.indicadorNoProcesado = FALSE ");
/*  563: 639 */     if (!listaRubro.isEmpty()) {
/*  564: 640 */       sql.append("AND r IN (:listaRubro) ");
/*  565:     */     }
/*  566: 642 */     if (categoriaEmpresa != null) {
/*  567: 643 */       sql.append(" AND ce.idCategoriaEmpresa=:idCategoriaEmpresa ");
/*  568:     */     }
/*  569: 646 */     if (movimiento == -1) {
/*  570: 647 */       sql.append("AND (r.operacion =-1) ");
/*  571: 648 */     } else if (movimiento == 1) {
/*  572: 649 */       sql.append("AND (r.operacion =1) ");
/*  573:     */     } else {
/*  574: 651 */       sql.append("AND (r.operacion =1 OR r.operacion =-1) ");
/*  575:     */     }
/*  576: 654 */     sql.append(" GROUP BY r.nombre, pr.mes");
/*  577:     */     
/*  578: 656 */     Query query = this.em.createQuery(sql.toString());
/*  579: 657 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  580: 658 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  581: 659 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  582: 660 */     query.setParameter("listaPagoRol", listaPagoRol);
/*  583: 661 */     query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/*  584: 662 */     query.setParameter("indicadorRubroProvisionEmpleado", Boolean.valueOf(indicadorRubroProvisionEmpleado));
/*  585: 664 */     if (!listaRubro.isEmpty()) {
/*  586: 665 */       query.setParameter("listaRubro", listaRubro);
/*  587:     */     }
/*  588: 667 */     if (categoriaEmpresa != null) {
/*  589: 668 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/*  590:     */     }
/*  591: 671 */     return query.getResultList();
/*  592:     */   }
/*  593:     */   
/*  594:     */   public List<Object[]> getReporteRubroMensualDetallado(int idSucursal, int idEmpleado, int idDepartamento, List<PagoRol> listaPagoRol, List<Rubro> listaRubro, int movimiento, boolean indicadorRubroEmpleado, boolean indicadorRubroProvisionEmpleado, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/*  595:     */   {
/*  596: 678 */     StringBuilder sql = new StringBuilder();
/*  597: 679 */     sql.append("SELECT CONCAT(em.apellidos,' ',em.nombres), r.nombre, pr.mes, CASE WHEN (prer.valor IS NOT NULL) THEN (prer.valor*r.operacion) ELSE 0 END, pr.anio ");
/*  598: 680 */     sql.append("FROM PagoRolEmpleadoRubro prer ");
/*  599: 681 */     sql.append("LEFT JOIN prer.rubro r ");
/*  600: 682 */     sql.append("LEFT JOIN prer.pagoRolEmpleado pre ");
/*  601: 683 */     sql.append("LEFT JOIN pre.departamento d ");
/*  602: 684 */     sql.append("LEFT JOIN pre.pagoRol pr ");
/*  603: 685 */     sql.append("LEFT JOIN pre.empleado em ");
/*  604: 686 */     sql.append("LEFT JOIN em.empresa e ");
/*  605: 687 */     sql.append("LEFT JOIN e.categoriaEmpresa ce ");
/*  606: 688 */     sql.append("LEFT JOIN em.sucursal su ");
/*  607: 689 */     sql.append("WHERE pr IN (:listaPagoRol) ");
/*  608: 690 */     sql.append("AND pr.idOrganizacion = :idOrganizacion ");
/*  609: 691 */     sql.append("AND (su.idSucursal=:idSucursal OR :idSucursal=0) ");
/*  610: 692 */     sql.append("AND (em.idEmpleado=:idEmpleado OR :idEmpleado=0) ");
/*  611: 693 */     sql.append("AND (d.idDepartamento=:idDepartamento OR :idDepartamento=0) ");
/*  612: 694 */     sql.append("AND ((r.indicadorImpresionSobre = true) OR (r.indicadorProvision =:indicadorRubroProvisionEmpleado)) ");
/*  613: 695 */     sql.append("AND prer.indicadorNoProcesado = FALSE ");
/*  614: 696 */     if (!listaRubro.isEmpty()) {
/*  615: 697 */       sql.append("AND r IN (:listaRubro) ");
/*  616:     */     }
/*  617: 699 */     if (categoriaEmpresa != null) {
/*  618: 700 */       sql.append(" AND ce.idCategoriaEmpresa=:idCategoriaEmpresa ");
/*  619:     */     }
/*  620: 702 */     if (movimiento == -1) {
/*  621: 703 */       sql.append("AND (r.operacion =-1) ");
/*  622: 704 */     } else if (movimiento == 1) {
/*  623: 705 */       sql.append("AND (r.operacion =1) ");
/*  624:     */     } else {
/*  625: 707 */       sql.append("AND (r.operacion =1 OR r.operacion =-1) ");
/*  626:     */     }
/*  627: 710 */     sql.append("GROUP BY em.apellidos,em.nombres, r.nombre,pr.anio, pr.mes, prer.valor, r.operacion ");
/*  628:     */     
/*  629: 712 */     Query query = this.em.createQuery(sql.toString());
/*  630: 713 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  631: 714 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  632: 715 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  633: 716 */     query.setParameter("listaPagoRol", listaPagoRol);
/*  634: 717 */     query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/*  635: 718 */     query.setParameter("indicadorRubroProvisionEmpleado", Boolean.valueOf(indicadorRubroProvisionEmpleado));
/*  636: 720 */     if (!listaRubro.isEmpty()) {
/*  637: 721 */       query.setParameter("listaRubro", listaRubro);
/*  638:     */     }
/*  639: 723 */     if (categoriaEmpresa != null) {
/*  640: 724 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/*  641:     */     }
/*  642: 727 */     return query.getResultList();
/*  643:     */   }
/*  644:     */   
/*  645:     */   public List getReporteUtilidadEmpleado(int idSucursal, int idEmpleado, int idUtilidad, int idDepartamento)
/*  646:     */   {
/*  647: 734 */     StringBuilder sql = new StringBuilder();
/*  648: 735 */     sql.append("SELECT CONCAT(COALESCE(em.apellidos,du.apellidos),' ',COALESCE(em.nombres,du.nombres)), COALESCE(e.identificacion,du.identificacion), ");
/*  649: 736 */     sql.append("du.cargasFamiliares ,du.diasTrabajados ,du.valor10, du.valor5, u.valor, du.diasRealesTrabajados, du.retencionJudicial,");
/*  650: 737 */     sql.append(" em.idEmpleado ");
/*  651: 738 */     sql.append("FROM DetalleUtilidad du ");
/*  652: 739 */     sql.append("LEFT JOIN du.utilidad u ");
/*  653: 740 */     sql.append("LEFT JOIN du.empleado em ");
/*  654: 741 */     sql.append("LEFT JOIN em.empresa e ");
/*  655: 742 */     sql.append("LEFT JOIN em.sucursal su ");
/*  656: 743 */     sql.append("LEFT JOIN em.departamento d ");
/*  657: 744 */     sql.append("WHERE u.idUtilidad=:idUtilidad ");
/*  658: 745 */     sql.append("AND (em.idEmpleado=:idEmpleado OR :idEmpleado=0) ");
/*  659: 746 */     sql.append("AND (d.idDepartamento=:idDepartamento OR :idDepartamento=0) ");
/*  660: 747 */     sql.append("AND (su.idSucursal=:idSucursal OR :idSucursal=0) ");
/*  661: 748 */     sql.append("ORDER BY CONCAT(COALESCE(em.apellidos,du.apellidos),' ',COALESCE(em.nombres,du.nombres))");
/*  662:     */     
/*  663: 750 */     Query query = this.em.createQuery(sql.toString());
/*  664: 751 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  665: 752 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  666: 753 */     query.setParameter("idUtilidad", Integer.valueOf(idUtilidad));
/*  667: 754 */     query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/*  668:     */     
/*  669: 756 */     return query.getResultList();
/*  670:     */   }
/*  671:     */   
/*  672:     */   public List getReporteDescuentoPrestamoEmpleado(int idOrganizacion, Sucursal sucursal, Empleado empleado, Date fechaDesde, Date fechaHasta)
/*  673:     */   {
/*  674: 762 */     StringBuilder sql = new StringBuilder();
/*  675: 763 */     sql.append(" SELECT e.idEmpleado, r.idRubro,pr.fecha, SUM(prer.valor) ");
/*  676: 764 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/*  677: 765 */     sql.append(" JOIN prer.pagoRolEmpleado pre ");
/*  678: 766 */     sql.append(" JOIN prer.rubro r");
/*  679: 767 */     sql.append(" JOIN pre.pagoRol pr ");
/*  680: 768 */     sql.append(" JOIN pre.empleado e ");
/*  681: 769 */     sql.append(" WHERE pr.idOrganizacion = :idOrganizacion");
/*  682: 770 */     sql.append(" AND prer.indicadorNoProcesado = FALSE");
/*  683: 771 */     sql.append(" AND pr.fecha between :fechaDesde AND :fechaHasta");
/*  684: 772 */     if (sucursal != null) {
/*  685: 773 */       sql.append(" AND pr.idSucursal = :idSucursal");
/*  686:     */     }
/*  687: 775 */     if (empleado != null) {
/*  688: 776 */       sql.append(" AND e.idEmpleado = :idEmpleado");
/*  689:     */     }
/*  690: 778 */     sql.append("GROUP BY e.idEmpleado, r.idRubro,pr.fecha ");
/*  691:     */     
/*  692: 780 */     Query query = this.em.createQuery(sql.toString());
/*  693: 781 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  694: 782 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  695: 783 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  696: 784 */     if (sucursal != null) {
/*  697: 785 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  698:     */     }
/*  699: 787 */     if (empleado != null) {
/*  700: 788 */       query.setParameter("idEmpleado", Integer.valueOf(empleado.getIdEmpleado()));
/*  701:     */     }
/*  702: 791 */     return query.getResultList();
/*  703:     */   }
/*  704:     */   
/*  705:     */   public List<Object[]> getDatosAprobacionPagoRol(int idOrganizacion, Sucursal sucursal, PagoRol pagoRol, Departamento departamento)
/*  706:     */   {
/*  707: 799 */     StringBuilder sql = new StringBuilder();
/*  708: 800 */     sql.append(" SELECT em.idEmpleado, pre.indicadorAprobado, e.identificacion, em.apellidos, em.nombres, ca.nombre,");
/*  709: 801 */     sql.append(" pre.diasFalta, pre.salarioAsignado, r.idRubro, r.nombre, prer.valor*r.operacion,");
/*  710: 802 */     sql.append(" r.operacion, r.ordenImpresion, pr.fecha, pre.indicadorCobrado, pre.idPagoRolEmpleado ");
/*  711: 803 */     sql.append(" FROM PagoRolEmpleadoRubro prer");
/*  712: 804 */     sql.append(" INNER JOIN prer.rubro r ");
/*  713: 805 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre ");
/*  714: 806 */     sql.append(" INNER JOIN pre.pagoRol pr ");
/*  715: 807 */     sql.append(" INNER JOIN pre.empleado em ");
/*  716: 808 */     sql.append(" INNER JOIN em.cargoEmpleado ca");
/*  717: 809 */     sql.append(" INNER JOIN em.empresa e ");
/*  718: 810 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/*  719: 811 */     sql.append(" INNER JOIN em.departamento de ");
/*  720: 812 */     sql.append(" INNER JOIN em.sucursal su ");
/*  721: 813 */     sql.append(" WHERE pr.idPagoRol = :idPagoRol ");
/*  722: 814 */     sql.append(" AND prer.valor > 0 ");
/*  723: 815 */     sql.append(" AND r.indicadorImpresionSobre=true");
/*  724: 816 */     sql.append(" AND r.indicadorProvision=false");
/*  725: 817 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion ");
/*  726: 818 */     sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  727: 820 */     if (sucursal != null) {
/*  728: 821 */       sql.append(" AND su=:sucursal");
/*  729:     */     }
/*  730: 824 */     if (departamento != null) {
/*  731: 825 */       sql.append(" AND de=:departamento");
/*  732:     */     }
/*  733: 828 */     sql.append(" ORDER BY em.apellidos, em.nombres, r.operacion*-1, r.ordenImpresion");
/*  734:     */     
/*  735: 830 */     Query query = this.em.createQuery(sql.toString());
/*  736: 831 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  737: 832 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  738: 834 */     if (sucursal != null) {
/*  739: 835 */       query.setParameter("sucursal", sucursal);
/*  740:     */     }
/*  741: 838 */     if (departamento != null) {
/*  742: 839 */       query.setParameter("departamento", departamento);
/*  743:     */     }
/*  744: 842 */     return query.getResultList();
/*  745:     */   }
/*  746:     */   
/*  747:     */   public List<Object[]> getRubrosAprobacionPagoRol(int idOrganizacion, Sucursal sucursal, PagoRol pagoRol, Departamento departamento)
/*  748:     */   {
/*  749: 849 */     StringBuilder sql = new StringBuilder();
/*  750: 850 */     sql.append(" SELECT DISTINCT r.idRubro, r.nombre, r.operacion, r.ordenImpresion");
/*  751: 851 */     sql.append(" FROM PagoRolEmpleadoRubro prer");
/*  752: 852 */     sql.append(" INNER JOIN prer.rubro r ");
/*  753: 853 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre ");
/*  754: 854 */     sql.append(" INNER JOIN pre.pagoRol pr ");
/*  755: 855 */     sql.append(" INNER JOIN pre.empleado em ");
/*  756: 856 */     sql.append(" INNER JOIN em.departamento de ");
/*  757: 857 */     sql.append(" INNER JOIN em.sucursal su ");
/*  758: 858 */     sql.append(" WHERE pr.idPagoRol = :idPagoRol ");
/*  759: 859 */     sql.append(" AND prer.valor > 0 ");
/*  760: 860 */     sql.append(" AND r.indicadorImpresionSobre=true");
/*  761: 861 */     sql.append(" AND r.indicadorProvision=false");
/*  762: 862 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion ");
/*  763: 863 */     sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  764: 865 */     if (sucursal != null) {
/*  765: 866 */       sql.append(" AND su=:sucursal");
/*  766:     */     }
/*  767: 869 */     if (departamento != null) {
/*  768: 870 */       sql.append(" AND de=:departamento");
/*  769:     */     }
/*  770: 873 */     sql.append(" ORDER BY r.operacion DESC, r.ordenImpresion ASC");
/*  771:     */     
/*  772: 875 */     Query query = this.em.createQuery(sql.toString());
/*  773: 876 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  774: 877 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  775: 879 */     if (sucursal != null) {
/*  776: 880 */       query.setParameter("sucursal", sucursal);
/*  777:     */     }
/*  778: 883 */     if (departamento != null) {
/*  779: 884 */       query.setParameter("departamento", departamento);
/*  780:     */     }
/*  781: 887 */     return query.getResultList();
/*  782:     */   }
/*  783:     */   
/*  784:     */   public List<Object[]> getIngresosPorEmpleado(int idOrganizacion, Sucursal sucursal, Quincena quincena, Date fecha, Departamento departamento)
/*  785:     */   {
/*  786: 894 */     PagoRol pagoRol = null;
/*  787:     */     
/*  788: 896 */     StringBuilder sqlRol = new StringBuilder();
/*  789: 897 */     sqlRol.append(" SELECT pr FROM PagoRol pr");
/*  790: 898 */     sqlRol.append(" JOIN pr.quincena qu");
/*  791: 899 */     sqlRol.append(" WHERE pr.idOrganizacion = :idOrganizacion ");
/*  792: 900 */     sqlRol.append(" AND qu = :quincena");
/*  793: 901 */     sqlRol.append(" AND pr.fecha <= :fecha");
/*  794: 902 */     sqlRol.append(" ORDER BY pr.fecha DESC");
/*  795: 903 */     Query queryRol = this.em.createQuery(sqlRol.toString());
/*  796: 904 */     queryRol.setMaxResults(1);
/*  797: 905 */     queryRol.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  798: 906 */     queryRol.setParameter("fecha", fecha);
/*  799: 907 */     queryRol.setParameter("quincena", quincena);
/*  800:     */     try
/*  801:     */     {
/*  802: 911 */       pagoRol = (PagoRol)queryRol.getSingleResult();
/*  803:     */       
/*  804: 913 */       StringBuilder sql = new StringBuilder();
/*  805: 914 */       sql.append(" SELECT em.idEmpleado, SUM(prer.valor)");
/*  806: 915 */       sql.append(" FROM PagoRolEmpleadoRubro prer");
/*  807: 916 */       sql.append(" INNER JOIN prer.rubro r ");
/*  808: 917 */       sql.append(" INNER JOIN prer.pagoRolEmpleado pre ");
/*  809: 918 */       sql.append(" INNER JOIN pre.pagoRol pr ");
/*  810: 919 */       sql.append(" INNER JOIN pr.quincena qu ");
/*  811: 920 */       sql.append(" INNER JOIN pre.empleado em ");
/*  812: 921 */       sql.append(" INNER JOIN em.departamento de ");
/*  813: 922 */       sql.append(" INNER JOIN em.sucursal su ");
/*  814: 923 */       sql.append(" WHERE pr.idPagoRol = :idPagoRol ");
/*  815: 924 */       sql.append(" AND prer.valor > 0 ");
/*  816: 925 */       sql.append(" AND r.indicadorImpresionSobre=true");
/*  817: 926 */       sql.append(" AND r.indicadorProvision=false");
/*  818: 927 */       sql.append(" AND r.operacion=1");
/*  819: 928 */       sql.append(" AND pr.idOrganizacion = :idOrganizacion ");
/*  820: 929 */       sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  821: 931 */       if (sucursal != null) {
/*  822: 932 */         sql.append(" AND su=:sucursal");
/*  823:     */       }
/*  824: 935 */       if (departamento != null) {
/*  825: 936 */         sql.append(" AND de=:departamento");
/*  826:     */       }
/*  827: 939 */       sql.append(" GROUP BY em.idEmpleado");
/*  828: 940 */       sql.append(" ORDER BY em.idEmpleado");
/*  829:     */       
/*  830: 942 */       Query query = this.em.createQuery(sql.toString());
/*  831: 943 */       query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  832: 944 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  833: 946 */       if (sucursal != null) {
/*  834: 947 */         query.setParameter("sucursal", sucursal);
/*  835:     */       }
/*  836: 950 */       if (departamento != null) {
/*  837: 951 */         query.setParameter("departamento", departamento);
/*  838:     */       }
/*  839: 954 */       return query.getResultList();
/*  840:     */     }
/*  841:     */     catch (NoResultException e) {}
/*  842: 957 */     return new ArrayList();
/*  843:     */   }
/*  844:     */   
/*  845:     */   public List<Object[]> getReportePermisos(Empleado empleado, int idSucursal, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion, TipoPermisoEmpleado tipoPermisoEmpleado)
/*  846:     */   {
/*  847: 965 */     StringBuilder sql = new StringBuilder();
/*  848: 966 */     sql.append("SELECT s.nombre , dpe.fechaPermiso, dpe.horas, dpe.horaDesde, dpe.horaHasta, pe.numero,");
/*  849: 967 */     sql.append(" e.nombres, e.apellidos, d.nombre, tpe.nombre,");
/*  850: 968 */     sql.append(" pe.diaHasta, pe.descripcion, pe.estado, pe.numero, emp.codigo, emp.identificacion");
/*  851: 969 */     sql.append(" FROM DetallePermisoEmpleado dpe");
/*  852: 970 */     sql.append(" INNER JOIN dpe.permisoEmpleado pe");
/*  853: 971 */     sql.append(" INNER JOIN pe.historicoEmpleado he ");
/*  854: 972 */     sql.append(" INNER JOIN pe.tipoPermisoEmpleado tpe ");
/*  855: 973 */     sql.append(" INNER JOIN he.empleado e ");
/*  856: 974 */     sql.append(" INNER JOIN e.empresa emp");
/*  857: 975 */     sql.append(" INNER JOIN e.departamento d , Sucursal s ");
/*  858: 976 */     sql.append(" WHERE pe.idOrganizacion = :idOrganizacion ");
/*  859: 977 */     sql.append(" AND pe.idSucursal= s.idSucursal ");
/*  860: 978 */     if (empleado != null) {
/*  861: 979 */       sql.append(" AND e = :empleado ");
/*  862:     */     }
/*  863: 981 */     if (idSucursal != 0) {
/*  864: 982 */       sql.append(" AND he.idSucursal = :idSucursal ");
/*  865:     */     }
/*  866: 984 */     if (departamento != null) {
/*  867: 985 */       sql.append(" AND d = :departamento  ");
/*  868:     */     }
/*  869: 987 */     if (tipoPermisoEmpleado != null) {
/*  870: 988 */       sql.append(" AND tpe = :tipoPermisoEmpleado ");
/*  871:     */     }
/*  872: 990 */     sql.append(" AND pe.fechaPermiso BETWEEN :fechaDesde AND :fechaHasta ");
/*  873: 991 */     sql.append(" AND (pe.estado = :estado OR pe.estado = :estadoCerrado) ");
/*  874: 992 */     sql.append(" ORDER BY e.apellidos, e.nombres, dpe.fechaPermiso ");
/*  875:     */     
/*  876: 994 */     Query query = this.em.createQuery(sql.toString());
/*  877: 995 */     if (empleado != null) {
/*  878: 996 */       query.setParameter("empleado", empleado);
/*  879:     */     }
/*  880: 998 */     if (idSucursal != 0) {
/*  881: 999 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  882:     */     }
/*  883:1001 */     if (departamento != null) {
/*  884:1002 */       query.setParameter("departamento", departamento);
/*  885:     */     }
/*  886:1004 */     if (tipoPermisoEmpleado != null) {
/*  887:1005 */       query.setParameter("tipoPermisoEmpleado", tipoPermisoEmpleado);
/*  888:     */     }
/*  889:1007 */     query.setParameter("fechaDesde", fechaDesde);
/*  890:1008 */     query.setParameter("fechaHasta", fechaHasta);
/*  891:1009 */     query.setParameter("estado", Estado.APROBADO);
/*  892:1010 */     query.setParameter("estadoCerrado", Estado.CERRADO);
/*  893:1011 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  894:     */     
/*  895:1013 */     return query.getResultList();
/*  896:     */   }
/*  897:     */   
/*  898:     */   public List<Object[]> getReporteRubroAsignado(int idOrganizacion, Empleado empleado, boolean activo)
/*  899:     */   {
/*  900:1019 */     StringBuilder sql = new StringBuilder();
/*  901:1020 */     sql.append(" SELECT e.nombreFiscal, e.nombreComercial, r.nombre, e.identificacion, r.valor, r.codigo   ");
/*  902:1021 */     sql.append(" FROM RubroEmpleado re ");
/*  903:1022 */     sql.append(" INNER JOIN re.empleado emp ");
/*  904:1023 */     sql.append(" INNER JOIN re.rubro r ");
/*  905:1024 */     sql.append(" INNER JOIN emp.empresa e ");
/*  906:1025 */     sql.append(" WHERE r.idOrganizacion =:idOrganizacion ");
/*  907:1026 */     if (empleado != null) {
/*  908:1027 */       sql.append(" AND emp =:empleado ");
/*  909:     */     }
/*  910:1029 */     if (activo) {
/*  911:1030 */       sql.append(" AND emp.activo = true ");
/*  912:     */     }
/*  913:1032 */     sql.append(" ORDER BY emp.apellidos ");
/*  914:     */     
/*  915:1034 */     Query query = this.em.createQuery(sql.toString());
/*  916:1035 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  917:1036 */     if (empleado != null) {
/*  918:1037 */       query.setParameter("empleado", empleado);
/*  919:     */     }
/*  920:1040 */     return query.getResultList();
/*  921:     */   }
/*  922:     */   
/*  923:     */   public List<Object[]> getPagoRolEmpleadoRubro(HistoricoEmpleado historicoEmpleado, Empleado empleado, int idOrganizacion, int idSucursal)
/*  924:     */   {
/*  925:1045 */     StringBuilder sql = new StringBuilder();
/*  926:1046 */     sql.append(" SELECT r.nombre, prer.valor, r.operacion, r.indicadorProvision ");
/*  927:1047 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/*  928:1048 */     sql.append(" INNER JOIN prer.rubro r");
/*  929:1049 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre");
/*  930:1050 */     sql.append(" INNER JOIN pre.pagoRol pr");
/*  931:1051 */     sql.append(" INNER JOIN pre.empleado e");
/*  932:1052 */     sql.append(" WHERE r.idOrganizacion =:idOrganizacion AND prer.valor != 0");
/*  933:1053 */     sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  934:1054 */     if (empleado != null) {
/*  935:1055 */       sql.append(" AND e =:empleado ");
/*  936:     */     }
/*  937:1057 */     sql.append(" AND pre.indicadorCobrado = false");
/*  938:1058 */     sql.append(" AND pr.mes = :mes AND pr.anio = :anio");
/*  939:     */     
/*  940:1060 */     Query query = this.em.createQuery(sql.toString());
/*  941:1061 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  942:1062 */     if (empleado != null) {
/*  943:1063 */       query.setParameter("empleado", empleado);
/*  944:     */     }
/*  945:1065 */     query.setParameter("mes", Integer.valueOf(FuncionesUtiles.getMes(historicoEmpleado.getFechaSalida())));
/*  946:1066 */     query.setParameter("anio", Integer.valueOf(FuncionesUtiles.getAnio(historicoEmpleado.getFechaSalida())));
/*  947:     */     
/*  948:1068 */     return query.getResultList();
/*  949:     */   }
/*  950:     */   
/*  951:     */   public List<Object[]> getReporteDotacionEmpleado(Date fechaDesde, Date fechaHasta, int idEmpleado, int idProducto, int idDepartamento, int idOrganizacion, int idSucursal, boolean dotacionEmpleadoDetallado, boolean dotacionEmpleadoResumido, boolean reposicionDotacion)
/*  952:     */   {
/*  953:1074 */     StringBuilder sql = new StringBuilder();
/*  954:1076 */     if ((dotacionEmpleadoDetallado) || (reposicionDotacion)) {
/*  955:1077 */       sql.append(" SELECT e.nombres, e.apellidos, dot.fechaEntrega, dot.fechaReposicion, p.codigo, p.nombre, de.nombre, dot.cantidadDotada, dot.descripcion");
/*  956:     */     }
/*  957:1080 */     if (dotacionEmpleadoResumido) {
/*  958:1081 */       sql.append(" SELECT p.codigo, p.nombre, de.nombre, SUM(dot.cantidadDotada)");
/*  959:     */     }
/*  960:1083 */     sql.append(" FROM DotacionEmpleado dot");
/*  961:1084 */     sql.append(" INNER JOIN dot.empleado e ");
/*  962:1085 */     sql.append(" LEFT JOIN dot.producto p");
/*  963:1086 */     sql.append(" LEFT JOIN e.departamento de");
/*  964:1087 */     sql.append(" WHERE dot.idOrganizacion = :idOrganizacion");
/*  965:1088 */     sql.append(" AND dot.idSucursal = :idSucursal ");
/*  966:1089 */     sql.append(" AND (e.idEmpleado = :idEmpleado OR :idEmpleado=0)");
/*  967:1090 */     sql.append(" AND (p.idProducto = :idProducto OR :idProducto=0)");
/*  968:1091 */     sql.append(" AND (de.idDepartamento = :idDepartamento OR :idDepartamento=0)");
/*  969:1093 */     if ((dotacionEmpleadoDetallado) || (dotacionEmpleadoResumido)) {
/*  970:1094 */       sql.append(" AND dot.fechaEntrega between :fechaDesde AND :fechaHasta");
/*  971:     */     } else {
/*  972:1096 */       sql.append(" AND dot.fechaReposicion between :fechaDesde AND :fechaHasta");
/*  973:     */     }
/*  974:1099 */     if (dotacionEmpleadoResumido)
/*  975:     */     {
/*  976:1100 */       sql.append(" GROUP BY p.codigo, p.nombre, de.nombre");
/*  977:1101 */       sql.append(" ORDER BY p.nombre, de.nombre");
/*  978:     */     }
/*  979:1103 */     if ((dotacionEmpleadoDetallado) || (reposicionDotacion)) {
/*  980:1104 */       sql.append(" ORDER BY e.nombres,dot.fechaEntrega, dot.fechaReposicion");
/*  981:     */     }
/*  982:1107 */     Query query = this.em.createQuery(sql.toString());
/*  983:1108 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  984:1109 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  985:1110 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  986:1111 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  987:1112 */     query.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/*  988:1113 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  989:1114 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  990:     */     
/*  991:1116 */     return query.getResultList();
/*  992:     */   }
/*  993:     */   
/*  994:     */   public String getReporteMotivoLlamadoAtencionPorId(int idMotivoLlamadoAtencion)
/*  995:     */     throws ExcepcionAS2
/*  996:     */   {
/*  997:     */     try
/*  998:     */     {
/*  999:1128 */       String sql = "SELECT mla.textoMotivoLlamadoAtencion FROM MotivoLlamadoAtencion mla WHERE mla.idMotivoLlamadoAtencion = :idMotivoLlamadoAtencion";
/* 1000:1129 */       Query query = this.em.createQuery("SELECT mla.textoMotivoLlamadoAtencion FROM MotivoLlamadoAtencion mla WHERE mla.idMotivoLlamadoAtencion = :idMotivoLlamadoAtencion");
/* 1001:1130 */       query.setParameter("idMotivoLlamadoAtencion", Integer.valueOf(idMotivoLlamadoAtencion));
/* 1002:1131 */       return (String)query.getSingleResult();
/* 1003:     */     }
/* 1004:     */     catch (Exception e)
/* 1005:     */     {
/* 1006:1133 */       throw new ExcepcionAS2Nomina("msg_no_hay_datos", e);
/* 1007:     */     }
/* 1008:     */   }
/* 1009:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ReporteNominaDao
 * JD-Core Version:    0.7.0.1
 */