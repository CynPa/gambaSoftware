/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Fetch;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class GenericoDao<T extends EntidadBase>
/*  22:    */   extends AbstractDaoAS2<T>
/*  23:    */ {
/*  24:    */   public GenericoDao()
/*  25:    */   {
/*  26: 43 */     super(EntidadBase.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void insertar(T entidad)
/*  30:    */   {
/*  31: 52 */     this.claseEntidad = entidad.getClass();
/*  32: 53 */     super.insertar(entidad);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void actualizar(T entidad)
/*  36:    */   {
/*  37: 62 */     this.claseEntidad = entidad.getClass();
/*  38: 63 */     super.actualizar(entidad);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void eliminarAnular(T entidad)
/*  42:    */   {
/*  43: 72 */     this.claseEntidad = entidad.getClass();
/*  44: 73 */     super.eliminarAnular(entidad);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void eliminar(T entidad)
/*  48:    */   {
/*  49: 82 */     this.claseEntidad = entidad.getClass();
/*  50: 83 */     super.eliminar(entidad);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public T buscarPorId(Class claseEntidad, Object id)
/*  54:    */   {
/*  55: 93 */     this.claseEntidad = claseEntidad;
/*  56: 94 */     return super.buscarPorId(id);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void refrescar(T entidad)
/*  60:    */   {
/*  61:103 */     this.claseEntidad = entidad.getClass();
/*  62:104 */     super.refrescar(entidad);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void detach(T entidad)
/*  66:    */   {
/*  67:113 */     this.claseEntidad = entidad.getClass();
/*  68:114 */     super.detach(entidad);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void flush(Class claseEntidad)
/*  72:    */   {
/*  73:122 */     this.claseEntidad = claseEntidad;
/*  74:123 */     super.flush();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void guardar(T entidad)
/*  78:    */   {
/*  79:133 */     this.claseEntidad = entidad.getClass();
/*  80:134 */     super.guardar(entidad);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void guardarValidar(T entidad)
/*  84:    */     throws AS2Exception
/*  85:    */   {
/*  86:138 */     this.claseEntidad = entidad.getClass();
/*  87:139 */     boolean tieneCodigo = false;
/*  88:    */     try
/*  89:    */     {
/*  90:141 */       this.claseEntidad.getDeclaredField("codigo");
/*  91:142 */       this.claseEntidad.getDeclaredField("idOrganizacion");
/*  92:143 */       tieneCodigo = true;
/*  93:    */     }
/*  94:    */     catch (NoSuchFieldException e)
/*  95:    */     {
/*  96:145 */       tieneCodigo = false;
/*  97:    */     }
/*  98:    */     catch (SecurityException e)
/*  99:    */     {
/* 100:147 */       tieneCodigo = false;
/* 101:    */     }
/* 102:150 */     if (tieneCodigo)
/* 103:    */     {
/* 104:151 */       String codigo = buscarPorCodigo(this.claseEntidad, entidad);
/* 105:152 */       String nombre = buscarPorNombre(this.claseEntidad, entidad);
/* 106:153 */       if (codigo != null) {
/* 107:154 */         throw new AS2Exception("msg_error_codigo_repetido", new String[] { codigo });
/* 108:    */       }
/* 109:156 */       if (nombre != null) {
/* 110:157 */         throw new AS2Exception("msg_error_nombre_repetido", new String[] { nombre });
/* 111:    */       }
/* 112:    */     }
/* 113:161 */     super.guardar(entidad);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String buscarPorCodigo(Class claseEntidad, T entidad)
/* 117:    */   {
/* 118:166 */     Integer idOrganizacion = null;
/* 119:167 */     String codigo = null;
/* 120:    */     try
/* 121:    */     {
/* 122:169 */       Object[] parametros = null;
/* 123:170 */       Method metodo = claseEntidad.getMethod("getIdOrganizacion", new Class[0]);
/* 124:171 */       idOrganizacion = (Integer)metodo.invoke(entidad, parametros);
/* 125:    */       
/* 126:173 */       Method metodo2 = claseEntidad.getMethod("getCodigo", new Class[0]);
/* 127:174 */       codigo = (String)metodo2.invoke(entidad, parametros);
/* 128:    */     }
/* 129:    */     catch (Exception e)
/* 130:    */     {
/* 131:176 */       return null;
/* 132:    */     }
/* 133:178 */     if ((idOrganizacion == null) || (codigo == null)) {
/* 134:179 */       return null;
/* 135:    */     }
/* 136:182 */     Map<String, String> filtros = new HashMap();
/* 137:183 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 138:184 */     filtros.put("codigo", "=" + codigo);
/* 139:185 */     List<T> lista = obtenerListaCombo(claseEntidad, "codigo", true, filtros);
/* 140:186 */     if ((lista.size() > 0) && (entidad.getId() != ((EntidadBase)lista.get(0)).getId())) {
/* 141:187 */       return codigo;
/* 142:    */     }
/* 143:189 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String buscarPorNombre(Class claseEntidad, T entidad)
/* 147:    */   {
/* 148:195 */     Integer idOrganizacion = null;
/* 149:196 */     String nombre = null;
/* 150:    */     try
/* 151:    */     {
/* 152:198 */       Object[] parametros = null;
/* 153:199 */       Method metodo = claseEntidad.getMethod("getIdOrganizacion", new Class[0]);
/* 154:200 */       idOrganizacion = (Integer)metodo.invoke(entidad, parametros);
/* 155:    */       
/* 156:202 */       Method metodo2 = claseEntidad.getMethod("getNombre", new Class[0]);
/* 157:203 */       nombre = (String)metodo2.invoke(entidad, parametros);
/* 158:    */     }
/* 159:    */     catch (Exception e)
/* 160:    */     {
/* 161:205 */       return null;
/* 162:    */     }
/* 163:207 */     if ((idOrganizacion == null) || (nombre == null)) {
/* 164:208 */       return null;
/* 165:    */     }
/* 166:211 */     Map<String, String> filtros = new HashMap();
/* 167:212 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 168:213 */     filtros.put("nombre", "=" + nombre);
/* 169:214 */     List<T> lista = obtenerListaCombo(claseEntidad, "nombre", true, filtros);
/* 170:215 */     if ((lista.size() > 0) && (entidad.getId() != ((EntidadBase)lista.get(0)).getId())) {
/* 171:216 */       return nombre;
/* 172:    */     }
/* 173:218 */     return null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int contarPorCriterio(Class claseEntidad, Map<String, String> filtros)
/* 177:    */   {
/* 178:229 */     this.claseEntidad = claseEntidad;
/* 179:230 */     return super.contarPorCriterio(filtros);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<T> obtenerListaCombo(Class claseEntidad, String sortField, boolean sortOrder, Map<String, String> filtros)
/* 183:    */   {
/* 184:241 */     this.claseEntidad = claseEntidad;
/* 185:242 */     return super.obtenerListaCombo(sortField, sortOrder, filtros);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<T> obtenerListaPorPagina(Class claseEntidad, int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filtros)
/* 189:    */   {
/* 190:256 */     this.claseEntidad = claseEntidad;
/* 191:257 */     return super.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filtros);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<T> obtenerListaPorPagina(Class claseEntidad, int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filtros, List<String> listaCampos)
/* 195:    */   {
/* 196:272 */     if (listaCampos == null) {
/* 197:273 */       listaCampos = new ArrayList();
/* 198:    */     }
/* 199:275 */     this.claseEntidad = claseEntidad;
/* 200:276 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 201:277 */     CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
/* 202:278 */     Root<T> from = cq.from(claseEntidad);
/* 203:280 */     for (String campo : listaCampos)
/* 204:    */     {
/* 205:281 */       String[] propiedades = campo.split("\\.");
/* 206:282 */       String propiedad = propiedades[(propiedades.length - 1)];
/* 207:283 */       Fetch<Object, Object> fromFetch = null;
/* 208:284 */       if (propiedades.length > 1) {
/* 209:285 */         for (int i = 0; i < propiedades.length - 1; i++) {
/* 210:286 */           if (fromFetch == null) {
/* 211:287 */             fromFetch = from.fetch(propiedades[i], JoinType.LEFT);
/* 212:    */           } else {
/* 213:289 */             fromFetch = fromFetch.fetch(propiedades[i], JoinType.LEFT);
/* 214:    */           }
/* 215:    */         }
/* 216:    */       }
/* 217:293 */       if (fromFetch == null) {
/* 218:294 */         fromFetch = from.fetch(propiedad, JoinType.LEFT);
/* 219:    */       } else {
/* 220:296 */         fromFetch = fromFetch.fetch(propiedad, JoinType.LEFT);
/* 221:    */       }
/* 222:    */     }
/* 223:301 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 224:    */     
/* 225:    */ 
/* 226:304 */     agregarFiltros(filtros, cb, cq, from);
/* 227:    */     
/* 228:    */ 
/* 229:307 */     Object typedQuery = this.em.createQuery(cq.select(from));
/* 230:308 */     agregarPaginacion(startIndex, pageSize, (TypedQuery)typedQuery);
/* 231:    */     
/* 232:310 */     return ((TypedQuery)typedQuery).getResultList();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public T cargarDetalle(Class claseEntidad, int id, List<String> listaCampos)
/* 236:    */   {
/* 237:314 */     if (listaCampos == null) {
/* 238:315 */       listaCampos = new ArrayList();
/* 239:    */     }
/* 240:317 */     this.claseEntidad = claseEntidad;
/* 241:318 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 242:319 */     CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
/* 243:320 */     Root<T> from = cq.from(claseEntidad);
/* 244:322 */     for (String campo : listaCampos)
/* 245:    */     {
/* 246:323 */       String[] propiedades = campo.split("\\.");
/* 247:324 */       String propiedad = propiedades[(propiedades.length - 1)];
/* 248:325 */       Fetch<Object, Object> fromFetch = null;
/* 249:326 */       if (propiedades.length > 1) {
/* 250:327 */         for (int i = 0; i < propiedades.length - 1; i++) {
/* 251:328 */           if (fromFetch == null) {
/* 252:329 */             fromFetch = from.fetch(propiedades[i], JoinType.LEFT);
/* 253:    */           } else {
/* 254:331 */             fromFetch = fromFetch.fetch(propiedades[i], JoinType.LEFT);
/* 255:    */           }
/* 256:    */         }
/* 257:    */       }
/* 258:335 */       if (fromFetch == null) {
/* 259:336 */         fromFetch = from.fetch(propiedad, JoinType.LEFT);
/* 260:    */       } else {
/* 261:338 */         fromFetch = fromFetch.fetch(propiedad, JoinType.LEFT);
/* 262:    */       }
/* 263:    */     }
/* 264:342 */     Object pathId = from.get("id" + claseEntidad.getSimpleName());
/* 265:343 */     cq.where(cb.equal((Expression)pathId, Integer.valueOf(id)));
/* 266:    */     
/* 267:345 */     TypedQuery<T> typedQuery = this.em.createQuery(cq.select(from));
/* 268:    */     
/* 269:347 */     return (EntidadBase)typedQuery.getSingleResult();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Class<?> getTipoDato(Class claseEntidad, Class<?> claseRoot, String propiedadRoot)
/* 273:    */   {
/* 274:358 */     this.claseEntidad = claseEntidad;
/* 275:359 */     return super.getTipoDato(claseRoot, propiedadRoot);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void actualizarAtributoEntidad(T entidad, HashMap<String, Object> campos)
/* 279:    */   {
/* 280:371 */     this.claseEntidad = entidad.getClass();
/* 281:372 */     super.actualizarAtributoEntidad(entidad, campos);
/* 282:    */   }
/* 283:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.GenericoDao
 * JD-Core Version:    0.7.0.1
 */