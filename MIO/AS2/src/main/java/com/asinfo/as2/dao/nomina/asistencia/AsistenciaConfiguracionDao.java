/*  1:   */ package com.asinfo.as2.dao.nomina.asistencia;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GenericoDao;
/*  4:   */ import com.asinfo.as2.entities.EntidadBase;
/*  5:   */ import com.asinfo.as2.entities.nomina.asistencia.DiaFestivo;
/*  6:   */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*  7:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ import javax.persistence.EntityManager;
/* 12:   */ import javax.persistence.NoResultException;
/* 13:   */ import javax.persistence.Query;
/* 14:   */ import javax.persistence.TemporalType;
/* 15:   */ import javax.persistence.TypedQuery;
/* 16:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 17:   */ import javax.persistence.criteria.CriteriaQuery;
/* 18:   */ import javax.persistence.criteria.JoinType;
/* 19:   */ import javax.persistence.criteria.Path;
/* 20:   */ import javax.persistence.criteria.Root;
/* 21:   */ 
/* 22:   */ @Stateless
/* 23:   */ public class AsistenciaConfiguracionDao
/* 24:   */   extends GenericoDao<EntidadBase>
/* 25:   */ {
/* 26:   */   public List<DiaFestivo> obtenerDiasFestivosARepetirPorAnno(int anno)
/* 27:   */   {
/* 28:27 */     Date primerDia = FuncionesUtiles.getFecha(1, 1, anno);
/* 29:28 */     Date ultimoDia = FuncionesUtiles.getFecha(31, 12, anno);
/* 30:29 */     StringBuilder sql = new StringBuilder();
/* 31:30 */     sql.append(" SELECT df ");
/* 32:31 */     sql.append(" FROM DiaFestivo df ");
/* 33:32 */     sql.append(" WHERE df.fecha >= :primerDia AND df.fecha <= :ultimoDia ");
/* 34:33 */     sql.append(" AND df.indicadorRepetir = true ");
/* 35:   */     
/* 36:35 */     Query query = this.em.createQuery(sql.toString());
/* 37:36 */     query.setParameter("primerDia", primerDia);
/* 38:37 */     query.setParameter("ultimoDia", ultimoDia);
/* 39:   */     
/* 40:39 */     return query.getResultList();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public HorarioEmpleado cargarDetalleHorarioEmpleado(HorarioEmpleado horarioEmpleado)
/* 44:   */   {
/* 45:43 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 46:   */     
/* 47:   */ 
/* 48:46 */     CriteriaQuery<HorarioEmpleado> cqCabecera = criteriaBuilder.createQuery(HorarioEmpleado.class);
/* 49:47 */     Root<HorarioEmpleado> fromCabecera = cqCabecera.from(HorarioEmpleado.class);
/* 50:   */     
/* 51:49 */     fromCabecera.fetch("turno1", JoinType.LEFT);
/* 52:50 */     fromCabecera.fetch("turno2", JoinType.LEFT);
/* 53:51 */     fromCabecera.fetch("turno3", JoinType.LEFT);
/* 54:52 */     fromCabecera.fetch("turno4", JoinType.LEFT);
/* 55:53 */     fromCabecera.fetch("turno5", JoinType.LEFT);
/* 56:54 */     fromCabecera.fetch("turno6", JoinType.LEFT);
/* 57:55 */     fromCabecera.fetch("turno0", JoinType.LEFT);
/* 58:   */     
/* 59:57 */     Path<Integer> pathId = fromCabecera.get("idHorarioEmpleado");
/* 60:58 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(horarioEmpleado.getId())));
/* 61:59 */     CriteriaQuery<HorarioEmpleado> select = cqCabecera.select(fromCabecera);
/* 62:   */     
/* 63:61 */     HorarioEmpleado horarioEmpleadoNew = (HorarioEmpleado)this.em.createQuery(select).getSingleResult();
/* 64:62 */     return horarioEmpleadoNew;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public boolean esDiaFestivo(int idOrganizacion, Date fecha)
/* 68:   */   {
/* 69:74 */     String sql = "SELECT d FROM DiaFestivo d WHERE d.idOrganizacion=:idOrganizacion AND d.fecha=:fecha AND d.activo = TRUE ";
/* 70:75 */     Query query = this.em.createQuery(sql);
/* 71:76 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 72:77 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 73:   */     try
/* 74:   */     {
/* 75:80 */       query.getSingleResult();
/* 76:81 */       return true;
/* 77:   */     }
/* 78:   */     catch (NoResultException e) {}
/* 79:83 */     return false;
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.nomina.asistencia.AsistenciaConfiguracionDao
 * JD-Core Version:    0.7.0.1
 */