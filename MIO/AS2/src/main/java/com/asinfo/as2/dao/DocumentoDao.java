/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Documento;
/*   4:    */ import com.asinfo.as2.entities.DocumentoGastoImportacion;
/*   5:    */ import com.asinfo.as2.entities.GastoImportacion;
/*   6:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   7:    */ import com.asinfo.as2.entities.Secuencia;
/*   8:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   9:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  10:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ import javax.persistence.EntityManager;
/*  19:    */ import javax.persistence.NoResultException;
/*  20:    */ import javax.persistence.Query;
/*  21:    */ import javax.persistence.TypedQuery;
/*  22:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  23:    */ import javax.persistence.criteria.CriteriaQuery;
/*  24:    */ import javax.persistence.criteria.Expression;
/*  25:    */ import javax.persistence.criteria.JoinType;
/*  26:    */ import javax.persistence.criteria.Predicate;
/*  27:    */ import javax.persistence.criteria.Root;
/*  28:    */ 
/*  29:    */ @Stateless
/*  30:    */ public class DocumentoDao
/*  31:    */   extends AbstractDaoAS2<Documento>
/*  32:    */ {
/*  33:    */   @EJB
/*  34:    */   private SecuenciaDao secuenciaDao;
/*  35:    */   
/*  36:    */   public DocumentoDao()
/*  37:    */   {
/*  38: 56 */     super(Documento.class);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Documento cargarDetalle(int idDocumento)
/*  42:    */   {
/*  43: 67 */     Documento documento = buscarPorId(Integer.valueOf(idDocumento));
/*  44: 69 */     if (documento.getTipoAsiento() != null) {
/*  45: 70 */       documento.getTipoAsiento().getId();
/*  46:    */     }
/*  47: 73 */     if (documento.getSecuencia() != null) {
/*  48: 74 */       documento.getSecuencia().getId();
/*  49:    */     }
/*  50: 77 */     if (documento.getTipoComprobanteSRI() != null) {
/*  51: 78 */       documento.getTipoComprobanteSRI().getId();
/*  52:    */     }
/*  53: 82 */     documento.getListaDocumentoGastoImportacion().size();
/*  54: 83 */     for (DocumentoGastoImportacion documentoGastoImportacion : documento.getListaDocumentoGastoImportacion()) {
/*  55: 84 */       documentoGastoImportacion.getGastoImportacion().getId();
/*  56:    */     }
/*  57: 88 */     documento.getListaAutorizacionDocumentoSRI().size();
/*  58:    */     
/*  59: 90 */     return documento;
/*  60:    */   }
/*  61:    */   
/*  62:    */   @Deprecated
/*  63:    */   public List<Documento> buscarPorDocumentoBase(DocumentoBase documentoBase, int operacion)
/*  64:    */     throws ExcepcionAS2
/*  65:    */   {
/*  66: 97 */     String sqll = "SELECT d FROM Documento d left join fetch d.secuencia left join fetch d.tipoAsiento left join fetch d.tipoComprobanteSRI WHERE d.documentoBase=:documentoBase";
/*  67:100 */     if (operacion != 0) {
/*  68:101 */       sqll = sqll + " AND d.operacion=:operacion";
/*  69:    */     }
/*  70:104 */     Query query = this.em.createQuery(sqll);
/*  71:105 */     query.setParameter("documentoBase", documentoBase);
/*  72:106 */     if (operacion != 0) {
/*  73:107 */       query.setParameter("operacion", Integer.valueOf(operacion));
/*  74:    */     }
/*  75:110 */     List<Documento> lista = query.getResultList();
/*  76:112 */     if (lista.isEmpty()) {
/*  77:113 */       throw new ExcepcionAS2("msg_configuracion_documento", " " + documentoBase.getNombre());
/*  78:    */     }
/*  79:115 */     return lista;
/*  80:    */   }
/*  81:    */   
/*  82:    */   @Deprecated
/*  83:    */   public List<Documento> buscarPorDocumentoBase(DocumentoBase documentoBase)
/*  84:    */     throws ExcepcionAS2
/*  85:    */   {
/*  86:121 */     return buscarPorDocumentoBase(documentoBase, 0);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Documento buscarPorId(Integer idDocumento)
/*  90:    */   {
/*  91:127 */     Query query = this.em.createQuery("SELECT d FROM Documento d left join fetch d.secuencia left join fetch d.tipoAsiento left join fetch d.tipoComprobanteSRI WHERE d.idDocumento=:idDocumento");
/*  92:128 */     query.setParameter("idDocumento", idDocumento);
/*  93:    */     
/*  94:130 */     Documento documento = (Documento)query.getSingleResult();
/*  95:    */     
/*  96:132 */     return documento;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<Documento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 100:    */   {
/* 101:137 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 102:138 */     CriteriaQuery<Documento> criteriaQuery = criteriaBuilder.createQuery(Documento.class);
/* 103:139 */     Root<Documento> from = criteriaQuery.from(Documento.class);
/* 104:140 */     from.fetch("secuencia", JoinType.LEFT);
/* 105:141 */     from.fetch("tipoAsiento", JoinType.LEFT);
/* 106:142 */     from.fetch("tipoComprobanteSRI", JoinType.LEFT);
/* 107:    */     
/* 108:144 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 109:145 */     String nombre = (String)filters.get("nombre");
/* 110:146 */     filters.remove("nombre");
/* 111:147 */     if (nombre != null) {
/* 112:148 */       empresiones.add(criteriaBuilder.equal(from.get("nombre"), nombre));
/* 113:    */     }
/* 114:150 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 115:151 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 116:    */     
/* 117:153 */     CriteriaQuery<Documento> select = criteriaQuery.select(from);
/* 118:154 */     TypedQuery<Documento> typedQuery = this.em.createQuery(select);
/* 119:    */     
/* 120:156 */     return typedQuery.getResultList();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Documento cargarSecuencia(Documento documento, PuntoDeVenta puntoDeVenta, Date fecha)
/* 124:    */     throws ExcepcionAS2
/* 125:    */   {
/* 126:170 */     Secuencia secuencia = obtenerSecuencia(fecha, documento, puntoDeVenta);
/* 127:171 */     documento.setSecuencia(secuencia);
/* 128:172 */     return documento;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<Documento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 132:    */   {
/* 133:182 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 134:183 */     CriteriaQuery<Documento> criteriaQuery = criteriaBuilder.createQuery(Documento.class);
/* 135:184 */     Root<Documento> from = criteriaQuery.from(Documento.class);
/* 136:    */     
/* 137:    */ 
/* 138:187 */     from.fetch("tipoAsiento", JoinType.LEFT);
/* 139:188 */     from.fetch("secuencia", JoinType.LEFT);
/* 140:189 */     from.fetch("tipoComprobanteSRI", JoinType.LEFT);
/* 141:    */     
/* 142:191 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 143:192 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 144:    */     
/* 145:194 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 146:    */     
/* 147:196 */     CriteriaQuery<Documento> select = criteriaQuery.select(from);
/* 148:    */     
/* 149:198 */     TypedQuery<Documento> typedQuery = this.em.createQuery(select);
/* 150:199 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 151:    */     
/* 152:201 */     return typedQuery.getResultList();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Secuencia obtenerSecuencia(Date fecha, Documento documento, PuntoDeVenta puntoDeVenta)
/* 156:    */     throws ExcepcionAS2
/* 157:    */   {
/* 158:216 */     String sql = "SELECT a.secuencia FROM AutorizacionDocumentoSRI a  WHERE a.documento.idDocumento=:idDocumento AND a.puntoDeVenta.idPuntoDeVenta=:idPuntoDeVenta AND a.activo=true";
/* 159:219 */     if (fecha != null) {
/* 160:220 */       sql = sql + " AND :fecha BETWEEN a.secuencia.fechaDesde AND a.secuencia.fechaHasta";
/* 161:    */     }
/* 162:223 */     Query query = this.em.createQuery(sql);
/* 163:225 */     if (fecha != null) {
/* 164:226 */       query.setParameter("fecha", fecha);
/* 165:    */     }
/* 166:229 */     query.setParameter("idDocumento", Integer.valueOf(documento.getId()));
/* 167:230 */     query.setParameter("idPuntoDeVenta", Integer.valueOf(puntoDeVenta.getId()));
/* 168:231 */     query.setMaxResults(1);
/* 169:    */     try
/* 170:    */     {
/* 171:234 */       Secuencia secuencia = (Secuencia)query.getSingleResult();
/* 172:    */       
/* 173:236 */       this.secuenciaDao.detach(secuencia);
/* 174:237 */       return secuencia;
/* 175:    */     }
/* 176:    */     catch (NoResultException e)
/* 177:    */     {
/* 178:240 */       throw new ExcepcionAS2("msg_secuencia_no_encontrada", " " + documento.getNombre() + " (" + puntoDeVenta.getCodigo() + ")");
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public AutorizacionDocumentoSRI obtenerAutorizacionConSecuencia(Date fecha, Documento documento, PuntoDeVenta puntoDeVenta)
/* 183:    */     throws ExcepcionAS2
/* 184:    */   {
/* 185:255 */     String sql = "SELECT a FROM AutorizacionDocumentoSRI a LEFT JOIN FETCH a.secuencia WHERE a.documento.idDocumento=:idDocumento  AND a.puntoDeVenta.idPuntoDeVenta=:idPuntoDeVenta AND a.activo=true";
/* 186:258 */     if (fecha != null) {
/* 187:259 */       sql = sql + " AND :fecha BETWEEN a.secuencia.fechaDesde AND a.secuencia.fechaHasta";
/* 188:    */     }
/* 189:262 */     Query query = this.em.createQuery(sql);
/* 190:264 */     if (fecha != null) {
/* 191:265 */       query.setParameter("fecha", fecha);
/* 192:    */     }
/* 193:268 */     query.setParameter("idDocumento", Integer.valueOf(documento.getId()));
/* 194:269 */     query.setParameter("idPuntoDeVenta", Integer.valueOf(puntoDeVenta.getId()));
/* 195:270 */     query.setMaxResults(1);
/* 196:    */     try
/* 197:    */     {
/* 198:273 */       return (AutorizacionDocumentoSRI)query.getSingleResult();
/* 199:    */     }
/* 200:    */     catch (NoResultException e)
/* 201:    */     {
/* 202:277 */       throw new ExcepcionAS2("msg_secuencia_no_encontrada", " (" + documento.getNombre() + ")");
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<AutorizacionDocumentoSRI> obtenerAutorizaciones(Date fecha, int idOrganizacion, boolean activa)
/* 207:    */     throws ExcepcionAS2
/* 208:    */   {
/* 209:292 */     StringBuilder sql = new StringBuilder();
/* 210:    */     
/* 211:294 */     sql.append(" SELECT a FROM AutorizacionDocumentoSRI a ");
/* 212:295 */     sql.append(" LEFT JOIN FETCH a.secuencia s");
/* 213:296 */     sql.append(" LEFT JOIN FETCH a.documento d");
/* 214:297 */     sql.append(" LEFT JOIN FETCH d.tipoComprobanteSRI t");
/* 215:298 */     sql.append(" LEFT JOIN FETCH a.puntoDeVenta p");
/* 216:299 */     sql.append(" LEFT JOIN FETCH p.sucursal su");
/* 217:300 */     sql.append(" WHERE a.idOrganizacion=:idOrganizacion ");
/* 218:301 */     sql.append(" AND :fecha BETWEEN s.fechaDesde AND s.fechaHasta");
/* 219:302 */     sql.append(" AND a.activo = :activa");
/* 220:    */     
/* 221:304 */     Query query = this.em.createQuery(sql.toString());
/* 222:305 */     query.setParameter("fecha", fecha);
/* 223:306 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 224:307 */     query.setParameter("activa", Boolean.valueOf(activa));
/* 225:    */     
/* 226:    */ 
/* 227:    */ 
/* 228:311 */     return query.getResultList();
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DocumentoDao
 * JD-Core Version:    0.7.0.1
 */