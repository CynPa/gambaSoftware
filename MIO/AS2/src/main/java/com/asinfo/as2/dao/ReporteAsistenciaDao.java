/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.db.AS2DBBase;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ReporteAsistenciaDao
/*  18:    */   extends AS2DBBase
/*  19:    */ {
/*  20:    */   private static final String RAWTYPES = "rawtypes";
/*  21:    */   private static final String UNCKECKED = "unchecked";
/*  22:    */   
/*  23:    */   public List<Object[]> getReporteAsistencia(Empleado empleado, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion, int tipo)
/*  24:    */   {
/*  25: 51 */     StringBuilder sql = new StringBuilder();
/*  26: 52 */     if (tipo == 0)
/*  27:    */     {
/*  28: 53 */       sql.append("SELECT asisPadre.idAsistencia, asi.idAsistencia, asi.fecha, asi.entrada, asi.marcacionEntrada, asi.salidaReceso, asi.marcacionSalidaReceso, asi.entradaReceso,");
/*  29: 54 */       sql.append(" asi.marcacionEntradaReceso, asi.salida, asi.marcacionSalida, asi.horasFalta, asi.horasPermiso, asi.horasSubsidio, asi.horasExtras25, asi.horasExtras50,");
/*  30: 55 */       sql.append(" asi.horasExtras100, asi.horasExtras100FeriadoAprobadas, asi.horasExtras25Aprobadas, asi.horasExtras50Aprobadas, asi.horasExtras100Aprobadas, em.nombres,");
/*  31: 56 */       sql.append(" em.apellidos, dep.nombre, tsubs.nombre, asi.descripcion, asi.indicadorDiaFestivo, emp.identificacion, asi.horasExtras25*0, asi.horasExtras25*0, asi.horasExtras100Feriado,");
/*  32: 57 */       sql.append(" asi.marcacionEntradaReingreso1, asi.marcacionSalidaReingreso1, asi.marcacionEntradaReingreso2, asi.marcacionSalidaReingreso2");
/*  33: 58 */       sql.append(" FROM Asistencia asi ");
/*  34: 59 */       sql.append(" INNER JOIN asi.empleado em");
/*  35: 60 */       sql.append(" INNER JOIN em.empresa emp");
/*  36: 61 */       sql.append(" INNER JOIN em.departamento dep ");
/*  37: 62 */       sql.append(" LEFT JOIN asi.asistenciaPadre asisPadre");
/*  38: 63 */       sql.append(" LEFT JOIN asi.subsidioEmpleado subs");
/*  39: 64 */       sql.append(" LEFT JOIN subs.tipoSubsidio tsubs ");
/*  40: 65 */       sql.append(" WHERE asi.idOrganizacion = :idOrganizacion ");
/*  41: 66 */       if (empleado != null) {
/*  42: 67 */         sql.append(" AND em = :empleado ");
/*  43:    */       }
/*  44: 69 */       if (departamento != null) {
/*  45: 70 */         sql.append(" AND dep = :departamento");
/*  46:    */       }
/*  47: 72 */       sql.append(" AND asi.fechaFiltro BETWEEN :fechaDesde AND :fechaHasta ");
/*  48: 73 */       sql.append(" ORDER BY dep.nombre, em.apellidos, asi.fecha, asi.entrada ");
/*  49:    */       
/*  50: 75 */       Query query = this.em.createQuery(sql.toString());
/*  51: 77 */       if (empleado != null) {
/*  52: 78 */         query.setParameter("empleado", empleado);
/*  53:    */       }
/*  54: 81 */       if (departamento != null) {
/*  55: 82 */         query.setParameter("departamento", departamento);
/*  56:    */       }
/*  57: 85 */       query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  58: 86 */       query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  59: 87 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  60:    */       
/*  61: 89 */       return query.getResultList();
/*  62:    */     }
/*  63: 91 */     sql.append("SELECT e.nombres, e.apellidos, de.nombre, mr.fecha, mr.marcacion, emp.identificacion");
/*  64: 92 */     sql.append(" FROM MarcacionReloj mr");
/*  65: 93 */     sql.append(" INNER JOIN mr.empleado e");
/*  66: 94 */     sql.append(" INNER JOIN e.empresa emp");
/*  67: 95 */     sql.append(" INNER JOIN e.departamento de");
/*  68: 96 */     sql.append(" WHERE e.idOrganizacion = :idOrganizacion");
/*  69: 97 */     sql.append(" AND e.idEmpleado NOT IN (");
/*  70: 98 */     sql.append("SELECT em.idEmpleado");
/*  71: 99 */     sql.append(" FROM Asistencia asi");
/*  72:100 */     sql.append(" INNER JOIN asi.empleado em");
/*  73:101 */     sql.append(" WHERE asi.idOrganizacion = :idOrganizacion");
/*  74:102 */     sql.append(") AND mr.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  75:103 */     if (departamento != null) {
/*  76:104 */       sql.append(" AND de = :departamento");
/*  77:    */     }
/*  78:106 */     sql.append(" ORDER BY e.apellidos, e.nombres, de.nombre, mr.fecha");
/*  79:107 */     Query query = this.em.createQuery(sql.toString());
/*  80:108 */     if (departamento != null) {
/*  81:109 */       query.setParameter("departamento", departamento);
/*  82:    */     }
/*  83:111 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  84:112 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  85:113 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  86:    */     
/*  87:115 */     List<Object[]> list = query.getResultList();
/*  88:116 */     List<Object[]> result = new ArrayList();
/*  89:118 */     for (Object[] objects : list)
/*  90:    */     {
/*  91:119 */       Object[] object = new Object[26];
/*  92:120 */       object[19] = objects[0];
/*  93:121 */       object[20] = objects[1];
/*  94:122 */       object[21] = objects[2];
/*  95:123 */       object[0] = objects[3];
/*  96:124 */       object[2] = objects[4];
/*  97:125 */       object[25] = objects[5];
/*  98:126 */       object[24] = Boolean.valueOf(false);
/*  99:127 */       result.add(object);
/* 100:    */     }
/* 101:129 */     StringBuilder sql2 = new StringBuilder();
/* 102:130 */     sql2.append("SELECT e.nombres, e.apellidos, depa.nombre, emp.identificacion");
/* 103:131 */     sql2.append(" FROM Empleado e");
/* 104:132 */     sql2.append(" INNER JOIN e.empresa emp");
/* 105:133 */     sql2.append(" INNER JOIN e.departamento depa");
/* 106:134 */     sql2.append(" INNER JOIN e.cargoEmpleado ce");
/* 107:135 */     sql2.append(" WHERE e.idOrganizacion = :idOrganizacion");
/* 108:136 */     sql2.append(" AND ce.indicadorRegistraAsistencia = true");
/* 109:137 */     sql2.append(" AND e.idEmpleado NOT IN (");
/* 110:138 */     sql2.append("SELECT em.idEmpleado");
/* 111:139 */     sql2.append(" FROM Asistencia asi");
/* 112:140 */     sql2.append(" INNER JOIN asi.empleado em");
/* 113:141 */     sql2.append(" WHERE asi.idOrganizacion = :idOrganizacion");
/* 114:142 */     sql2.append(") AND e.idEmpleado NOT IN (");
/* 115:143 */     sql2.append("SELECT empl.idEmpleado");
/* 116:144 */     sql2.append(" FROM MarcacionReloj mr");
/* 117:145 */     sql2.append(" INNER JOIN mr.empleado empl");
/* 118:146 */     sql2.append(" WHERE mr.idOrganizacion = :idOrganizacion)");
/* 119:147 */     if (departamento != null) {
/* 120:148 */       sql2.append(" AND depa = :departamento");
/* 121:    */     }
/* 122:150 */     sql2.append(" ORDER BY e.apellidos, e.nombres, depa.nombre,emp.identificacion");
/* 123:151 */     Query query2 = this.em.createQuery(sql2.toString());
/* 124:153 */     if (departamento != null) {
/* 125:154 */       query2.setParameter("departamento", departamento);
/* 126:    */     }
/* 127:156 */     query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 128:    */     
/* 129:158 */     List<Object[]> list2 = query2.getResultList();
/* 130:159 */     for (Object[] objects : list2)
/* 131:    */     {
/* 132:160 */       Object[] object = new Object[26];
/* 133:161 */       object[19] = objects[0];
/* 134:162 */       object[20] = objects[1];
/* 135:163 */       object[21] = objects[2];
/* 136:164 */       object[25] = objects[3];
/* 137:165 */       result.add(object);
/* 138:    */     }
/* 139:167 */     return result;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<Object[]> getReporteHorarioPersonalizado(Departamento departamento, Mes mes, int anio, int diaInicio, int diaFin)
/* 143:    */   {
/* 144:173 */     StringBuilder sql = new StringBuilder();
/* 145:174 */     sql.append(" SELECT CONCAT(em.apellidos,' ', em.nombres) ");
/* 146:175 */     for (int i = diaInicio; i <= diaFin; i++) {
/* 147:176 */       sql.append(" ,t" + i + ".codigo ");
/* 148:    */     }
/* 149:178 */     sql.append(" FROM DetallePlanPersonalizadoHorarioEmpleado dhp ");
/* 150:179 */     sql.append(" INNER JOIN dhp.planPersonalizadoHorarioEmpleado hp");
/* 151:180 */     sql.append(" INNER JOIN hp.departamento dep ");
/* 152:181 */     sql.append(" LEFT JOIN dhp.empleado em ");
/* 153:182 */     for (int i = diaInicio; i <= diaFin; i++) {
/* 154:183 */       sql.append(" LEFT JOIN dhp.turno" + i + " t" + i);
/* 155:    */     }
/* 156:185 */     sql.append(" WHERE hp.idOrganizacion = :idOrganizacion ");
/* 157:186 */     sql.append(" AND hp.mes = :mes ");
/* 158:187 */     sql.append(" AND hp.anno = :anio ");
/* 159:188 */     if (departamento != null) {
/* 160:189 */       sql.append(" AND dep = :departamento");
/* 161:    */     }
/* 162:192 */     Query query = this.em.createQuery(sql.toString());
/* 163:194 */     if (departamento != null) {
/* 164:195 */       query.setParameter("departamento", departamento);
/* 165:    */     }
/* 166:197 */     query.setParameter("mes", mes);
/* 167:198 */     query.setParameter("anio", Integer.valueOf(anio));
/* 168:199 */     query.setParameter("idOrganizacion", Integer.valueOf(departamento.getIdOrganizacion()));
/* 169:    */     
/* 170:201 */     List<Object[]> listaQuery = query.getResultList();
/* 171:202 */     List<Object[]> resultado = new ArrayList();
/* 172:203 */     for (Object[] detalle : listaQuery)
/* 173:    */     {
/* 174:204 */       int j = 1;
/* 175:205 */       for (int i = diaInicio; i <= diaFin; i++)
/* 176:    */       {
/* 177:206 */         Object[] object = new Object[3];
/* 178:207 */         object[0] = detalle[0];
/* 179:    */         
/* 180:209 */         object[1] = FuncionesUtiles.getFecha(i, mes.ordinal() + 1, anio);
/* 181:210 */         object[2] = detalle[j];
/* 182:211 */         resultado.add(object);
/* 183:212 */         j++;
/* 184:    */       }
/* 185:    */     }
/* 186:215 */     return resultado;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<Object[]> getReporteAsistenciaHoras(Empleado empleado, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion, int criterio)
/* 190:    */   {
/* 191:221 */     String selectCriterio = null;
/* 192:223 */     if (criterio == 25) {
/* 193:224 */       selectCriterio = "SELECT dep.nombre, CONCAT(em.apellidos,' ', em.nombres), asi.fecha, asi.horasExtras25Aprobadas, '1Horas Extras 25' ";
/* 194:225 */     } else if (criterio == 50) {
/* 195:226 */       selectCriterio = "SELECT dep.nombre, CONCAT(em.apellidos,' ', em.nombres), asi.fecha, asi.horasExtras50Aprobadas, '2Horas Extras 50' ";
/* 196:227 */     } else if (criterio == 100) {
/* 197:228 */       selectCriterio = "SELECT dep.nombre, CONCAT(em.apellidos,' ', em.nombres), asi.fecha, asi.horasExtras100FeriadoAprobadas+asi.horasExtras100Aprobadas, '3Horas Extras 100'";
/* 198:    */     }
/* 199:231 */     StringBuilder sqlExtra = new StringBuilder();
/* 200:232 */     sqlExtra.append(selectCriterio);
/* 201:233 */     sqlExtra.append(" FROM Asistencia asi ");
/* 202:234 */     sqlExtra.append(" INNER JOIN asi.empleado em");
/* 203:235 */     sqlExtra.append(" INNER JOIN em.empresa emp");
/* 204:236 */     sqlExtra.append(" INNER JOIN em.departamento dep ");
/* 205:237 */     sqlExtra.append(" WHERE asi.idOrganizacion = :idOrganizacion ");
/* 206:238 */     if (empleado != null) {
/* 207:239 */       sqlExtra.append(" AND em = :empleado ");
/* 208:    */     }
/* 209:241 */     if (departamento != null) {
/* 210:242 */       sqlExtra.append(" AND dep = :departamento");
/* 211:    */     }
/* 212:244 */     sqlExtra.append(" AND asi.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 213:245 */     sqlExtra.append(" ORDER BY dep.nombre, CONCAT(em.apellidos,' ', em.nombres), asi.fecha");
/* 214:    */     
/* 215:247 */     Query queryExtras = this.em.createQuery(sqlExtra.toString());
/* 216:249 */     if (empleado != null) {
/* 217:250 */       queryExtras.setParameter("empleado", empleado);
/* 218:    */     }
/* 219:253 */     if (departamento != null) {
/* 220:254 */       queryExtras.setParameter("departamento", departamento);
/* 221:    */     }
/* 222:257 */     queryExtras.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 223:258 */     queryExtras.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 224:259 */     queryExtras.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 225:    */     
/* 226:261 */     return queryExtras.getResultList();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public List<Object[]> getReporteAsistenciaResumido(Empleado empleado, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 230:    */   {
/* 231:267 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 232:268 */     listaDatosReporte.addAll(getReporteAsistenciaHoras(empleado, departamento, fechaDesde, fechaHasta, idOrganizacion, 25));
/* 233:269 */     listaDatosReporte.addAll(getReporteAsistenciaHoras(empleado, departamento, fechaDesde, fechaHasta, idOrganizacion, 50));
/* 234:270 */     listaDatosReporte.addAll(getReporteAsistenciaHoras(empleado, departamento, fechaDesde, fechaHasta, idOrganizacion, 100));
/* 235:271 */     return listaDatosReporte;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List<Object[]> getReporteControlAsistenciSobretiempos(Departamento departamento, Date fechaDesde, Empleado supervisor)
/* 239:    */   {
/* 240:276 */     StringBuilder sql = new StringBuilder();
/* 241:277 */     sql.append(" SELECT d.nombre, CONCAT(e1.apellidos,' ', e1.nombres), CONCAT(e.apellidos,' ', e.nombres), cc.nombre ");
/* 242:278 */     sql.append(" FROM Departamento d,Empleado e ");
/* 243:279 */     sql.append(" LEFT JOIN d.supervisor e1");
/* 244:280 */     sql.append(" LEFT JOIN e.centroCosto cc");
/* 245:281 */     sql.append(" WHERE d.idOrganizacion = :idOrganizacion ");
/* 246:282 */     sql.append(" AND e.activo=TRUE ");
/* 247:283 */     if (departamento != null)
/* 248:    */     {
/* 249:284 */       sql.append(" AND d = :departamento ");
/* 250:285 */       sql.append(" AND e.departamento = :departamento ");
/* 251:    */     }
/* 252:287 */     if (supervisor != null) {
/* 253:288 */       sql.append(" AND (d.supervisor = :supervisor OR d.supervisor2 = :supervisor)");
/* 254:    */     }
/* 255:290 */     sql.append(" ORDER BY CONCAT(e.apellidos,' ', e.nombres) ");
/* 256:291 */     Query query = this.em.createQuery(sql.toString());
/* 257:293 */     if (departamento != null) {
/* 258:294 */       query.setParameter("departamento", departamento);
/* 259:    */     }
/* 260:296 */     if (supervisor != null) {
/* 261:297 */       query.setParameter("supervisor", supervisor);
/* 262:    */     }
/* 263:300 */     query.setParameter("idOrganizacion", Integer.valueOf(departamento.getIdOrganizacion()));
/* 264:    */     
/* 265:302 */     return query.getResultList();
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ReporteAsistenciaDao
 * JD-Core Version:    0.7.0.1
 */