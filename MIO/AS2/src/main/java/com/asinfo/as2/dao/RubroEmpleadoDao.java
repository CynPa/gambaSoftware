/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empleado;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   6:    */ import com.asinfo.as2.entities.Rubro;
/*   7:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.NoResultException;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ import javax.persistence.TypedQuery;
/*  19:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  20:    */ import javax.persistence.criteria.CriteriaQuery;
/*  21:    */ import javax.persistence.criteria.Expression;
/*  22:    */ import javax.persistence.criteria.Fetch;
/*  23:    */ import javax.persistence.criteria.Join;
/*  24:    */ import javax.persistence.criteria.JoinType;
/*  25:    */ import javax.persistence.criteria.Path;
/*  26:    */ import javax.persistence.criteria.Predicate;
/*  27:    */ import javax.persistence.criteria.Root;
/*  28:    */ 
/*  29:    */ @Stateless
/*  30:    */ public class RubroEmpleadoDao
/*  31:    */   extends AbstractDaoAS2<RubroEmpleado>
/*  32:    */ {
/*  33:    */   public RubroEmpleadoDao()
/*  34:    */   {
/*  35: 49 */     super(RubroEmpleado.class);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<RubroEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 58 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<RubroEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  44:    */   {
/*  45: 67 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  46: 68 */     CriteriaQuery<RubroEmpleado> criteriaQuery = criteriaBuilder.createQuery(RubroEmpleado.class);
/*  47: 69 */     Root<RubroEmpleado> from = criteriaQuery.from(RubroEmpleado.class);
/*  48:    */     
/*  49: 71 */     from.fetch("rubro", JoinType.LEFT);
/*  50: 72 */     Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/*  51: 73 */     empleado.fetch("empresa", JoinType.LEFT);
/*  52:    */     
/*  53: 75 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  54: 76 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  55:    */     
/*  56: 78 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  57:    */     
/*  58: 80 */     CriteriaQuery<RubroEmpleado> select = criteriaQuery.select(from);
/*  59: 81 */     TypedQuery<RubroEmpleado> typedQuery = this.em.createQuery(select);
/*  60: 82 */     return typedQuery.getResultList();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int contarPorCriterio(Map<String, String> filters)
/*  64:    */   {
/*  65: 94 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  66: 95 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  67:    */     
/*  68: 97 */     Root<RubroEmpleado> from = criteriaQuery.from(RubroEmpleado.class);
/*  69: 98 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  70:    */     
/*  71:100 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  72:101 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  73:    */     
/*  74:103 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  75:    */   }
/*  76:    */   
/*  77:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<RubroEmpleado> from)
/*  78:    */   {
/*  79:118 */     List<Predicate> predicates = new ArrayList();
/*  80:119 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  81:    */     {
/*  82:120 */       String filterProperty = (String)it.next();
/*  83:122 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  84:    */       {
/*  85:123 */         String filterValue = (String)filters.get(filterProperty);
/*  86:125 */         if (filterProperty.equals("idOrganizacion"))
/*  87:    */         {
/*  88:126 */           Expression<Integer> path = from.get(filterProperty);
/*  89:127 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  90:    */         }
/*  91:128 */         else if (filterProperty.equals("idEmpleado"))
/*  92:    */         {
/*  93:129 */           Path<Integer> path = from.join("empelado").get(filterProperty);
/*  94:130 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  95:    */         }
/*  96:    */       }
/*  97:    */     }
/*  98:134 */     return predicates;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public RubroEmpleado cargarDetalle(int idRubroEmpleado)
/* 102:    */   {
/* 103:145 */     RubroEmpleado rubroEmpleado = new RubroEmpleado();
/* 104:146 */     rubroEmpleado = (RubroEmpleado)buscarPorId(Integer.valueOf(idRubroEmpleado));
/* 105:147 */     rubroEmpleado.getEmpleado().getId();
/* 106:148 */     rubroEmpleado.getEmpleado().getEmpresa().getId();
/* 107:149 */     rubroEmpleado.getRubro().getId();
/* 108:    */     
/* 109:151 */     return rubroEmpleado;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<RubroEmpleado> getListaRubroEmpleado(int idRubro, int idOrganizacion)
/* 113:    */   {
/* 114:157 */     StringBuilder sql = new StringBuilder();
/* 115:158 */     sql.append(" SELECT re FROM RubroEmpleado re ");
/* 116:159 */     sql.append(" INNER JOIN  re.rubro    r ");
/* 117:160 */     sql.append(" JOIN  FETCH re.empleado e ");
/* 118:161 */     sql.append(" JOIN  FETCH e.empresa   em ");
/* 119:162 */     sql.append(" WHERE em.indicadorEmpleado=true ");
/* 120:163 */     sql.append(" AND   em.idOrganizacion = :idOrganizacion ");
/* 121:164 */     sql.append(" AND   r.idRubro=:idRubro ");
/* 122:165 */     sql.append(" ORDER BY e.apellidos ");
/* 123:    */     
/* 124:167 */     Query query = this.em.createQuery(sql.toString());
/* 125:168 */     query.setParameter("idRubro", Integer.valueOf(idRubro));
/* 126:169 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 127:170 */     return query.getResultList();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Rubro rubroEmpleadoRetencionJudicial(Empleado empleado, TipoRubroEnum tipoRubro)
/* 131:    */   {
/* 132:175 */     Rubro r = null;
/* 133:176 */     StringBuilder sql = new StringBuilder();
/* 134:177 */     sql.append(" SELECT r ");
/* 135:178 */     sql.append(" FROM  RubroEmpleado re ");
/* 136:179 */     sql.append(" INNER JOIN   re.rubro    r ");
/* 137:180 */     sql.append(" INNER JOIN   re.empleado e ");
/* 138:181 */     sql.append(" INNER JOIN   e.empresa   em ");
/* 139:182 */     sql.append(" WHERE  r.tipo =:tipoRubro ");
/* 140:183 */     sql.append(" AND   e =:empleado ");
/* 141:    */     
/* 142:185 */     Query query = this.em.createQuery(sql.toString());
/* 143:186 */     query.setParameter("empleado", empleado);
/* 144:187 */     query.setParameter("tipoRubro", tipoRubro);
/* 145:    */     try
/* 146:    */     {
/* 147:190 */       return (Rubro)query.getSingleResult();
/* 148:    */     }
/* 149:    */     catch (NoResultException e) {}
/* 150:192 */     return r;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<RubroEmpleado> getListaRubroEmpleadoFiniquito(PagoRolEmpleado pagoRolEmpleado, int anio, int mes)
/* 154:    */   {
/* 155:200 */     StringBuilder sql = new StringBuilder();
/* 156:201 */     sql.append(" SELECT re FROM RubroEmpleado re ");
/* 157:202 */     sql.append(" JOIN re.empleado e ");
/* 158:203 */     sql.append(" JOIN FETCH re.rubro r ");
/* 159:204 */     sql.append(" LEFT JOIN  FETCH r.rubroPadre rp ");
/* 160:205 */     sql.append(" WHERE r.indicadorPagoFiniquito = true ");
/* 161:206 */     sql.append(" AND e.idEmpleado = :idEmpleado ");
/* 162:    */     
/* 163:208 */     Query query = this.em.createQuery(sql.toString());
/* 164:209 */     query.setParameter("idEmpleado", Integer.valueOf(pagoRolEmpleado.getEmpleado().getIdEmpleado()));
/* 165:210 */     return query.getResultList();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List<RubroEmpleado> getRubroEmpleado(List<Rubro> listaRubro, int idOrganizacion)
/* 169:    */   {
/* 170:217 */     StringBuilder sql = new StringBuilder();
/* 171:218 */     sql.append(" SELECT re FROM RubroEmpleado re ");
/* 172:219 */     sql.append(" JOIN FETCH re.rubro r ");
/* 173:220 */     sql.append(" JOIN FETCH re.empleado e ");
/* 174:221 */     sql.append(" JOIN FETCH e.empresa   em ");
/* 175:222 */     sql.append(" WHERE em.indicadorEmpleado=true ");
/* 176:223 */     sql.append(" AND   e.activo=true ");
/* 177:224 */     sql.append(" AND   em.idOrganizacion = :idOrganizacion ");
/* 178:225 */     if (listaRubro != null) {
/* 179:226 */       sql.append(" AND   r in (:listaRubro) ");
/* 180:    */     }
/* 181:228 */     sql.append(" ORDER BY e.apellidos ");
/* 182:    */     
/* 183:230 */     Query query = this.em.createQuery(sql.toString());
/* 184:231 */     if (listaRubro != null) {
/* 185:232 */       query.setParameter("listaRubro", listaRubro);
/* 186:    */     }
/* 187:234 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 188:235 */     return query.getResultList();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<Empleado> getEmpleadoSinRubro(List<Rubro> listaRubro, int idOrganizacion)
/* 192:    */   {
/* 193:240 */     StringBuilder sql = new StringBuilder();
/* 194:241 */     sql.append(" SELECT e FROM Empleado e, Rubro r");
/* 195:242 */     sql.append(" JOIN FETCH e.empresa  em ");
/* 196:243 */     sql.append(" WHERE em.indicadorEmpleado=true ");
/* 197:244 */     sql.append(" AND   e.activo=true ");
/* 198:245 */     sql.append(" AND   em.idOrganizacion = :idOrganizacion ");
/* 199:246 */     sql.append(" AND NOT EXISTS (SELECT 1 FROM RubroEmpleado re WHERE re.empleado.idEmpleado = e.idEmpleado AND r in (:listaRubro)) ");
/* 200:247 */     Query query = this.em.createQuery(sql.toString());
/* 201:248 */     query.setParameter("listaRubro", listaRubro);
/* 202:249 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 203:250 */     return query.getResultList();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<RubroEmpleado> obtenerRubroPorFormula(int idOrganizacion, Empleado empleado, String formula)
/* 207:    */   {
/* 208:255 */     StringBuilder sql = new StringBuilder();
/* 209:256 */     sql.append("SELECT re ");
/* 210:257 */     sql.append(" FROM RubroEmpleado re ");
/* 211:258 */     sql.append(" JOIN FETCH re.rubro r ");
/* 212:259 */     sql.append(" JOIN re.empleado e ");
/* 213:260 */     sql.append(" WHERE r.formula =:formula  ");
/* 214:261 */     sql.append(" AND r.idOrganizacion =:idOrganizacion ");
/* 215:262 */     if (empleado != null) {
/* 216:263 */       sql.append(" AND e.idEmpleado = :idEmpleado ");
/* 217:    */     }
/* 218:266 */     Query query = this.em.createQuery(sql.toString());
/* 219:267 */     query.setParameter("formula", formula);
/* 220:268 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 221:269 */     if (empleado != null) {
/* 222:270 */       query.setParameter("idEmpleado", Integer.valueOf(empleado.getIdEmpleado()));
/* 223:    */     }
/* 224:272 */     List<RubroEmpleado> listaRubros = query.getResultList();
/* 225:    */     
/* 226:274 */     return listaRubros;
/* 227:    */   }
/* 228:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RubroEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */