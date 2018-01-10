/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.GuiaAerea;
/*  4:   */ import com.asinfo.as2.enumeraciones.Estado;
/*  5:   */ import java.util.Calendar;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class GuiaAereaDao
/* 14:   */   extends AbstractDaoAS2<GuiaAerea>
/* 15:   */ {
/* 16:   */   public GuiaAereaDao()
/* 17:   */   {
/* 18:29 */     super(GuiaAerea.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public List<GuiaAerea> obtenerGuiasAereasPorAgentCode(String agentCode, int idOrganizacion, Date fechasDesde, Date fechaHasta, String numeroGuia)
/* 22:   */   {
/* 23:41 */     StringBuffer sql = new StringBuffer();
/* 24:42 */     sql.append("SELECT ga FROM GuiaAerea ga ");
/* 25:43 */     sql.append("INNER JOIN ga.cass c ");
/* 26:44 */     sql.append("WHERE c.estado != :estado  ");
/* 27:45 */     sql.append("AND c.idOrganizacion = :idOrganizacion ");
/* 28:46 */     if ((agentCode != null) && (fechasDesde == null) && (fechaHasta == null) && (numeroGuia == null)) {
/* 29:47 */       sql.append(" AND ga.agentCode = :agentCode");
/* 30:   */     }
/* 31:49 */     if ((agentCode == null) && (numeroGuia == null) && (fechasDesde != null) && (fechaHasta != null))
/* 32:   */     {
/* 33:50 */       sql.append(" AND c.datePeriodStart >= :datePeriodStart");
/* 34:51 */       sql.append(" AND c.datePeriodEnd <= :datePeriodEnd");
/* 35:   */     }
/* 36:53 */     if ((numeroGuia != null) && (fechasDesde == null) && (fechaHasta == null) && (agentCode == null)) {
/* 37:54 */       sql.append(" AND ga.awbSerialNumber = :numeroGuia");
/* 38:   */     }
/* 39:57 */     Query query = this.em.createQuery(sql.toString());
/* 40:58 */     query.setParameter("estado", Estado.ANULADO);
/* 41:59 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 42:61 */     if (agentCode != null) {
/* 43:62 */       query.setParameter("agentCode", agentCode);
/* 44:   */     }
/* 45:64 */     if ((agentCode == null) && (fechasDesde != null) && (fechaHasta != null))
/* 46:   */     {
/* 47:65 */       query.setParameter("datePeriodStart", formarCassFecha(fechasDesde));
/* 48:66 */       query.setParameter("datePeriodEnd", formarCassFecha(fechaHasta));
/* 49:   */     }
/* 50:68 */     if ((numeroGuia != null) && (fechasDesde == null) && (fechaHasta == null) && (agentCode == null)) {
/* 51:69 */       query.setParameter("numeroGuia", numeroGuia);
/* 52:   */     }
/* 53:72 */     return query.getResultList();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public Integer formarCassFecha(Date fecha)
/* 57:   */   {
/* 58:78 */     Calendar cal = Calendar.getInstance();
/* 59:79 */     cal.setTime(fecha);
/* 60:80 */     int anio = cal.get(1);
/* 61:81 */     int mes = 1 + cal.get(2);
/* 62:82 */     int dia = cal.get(5);
/* 63:   */     
/* 64:84 */     String diasUnoNueve = Integer.toString(dia).length() == 1 ? "0" + dia : Integer.toString(dia);
/* 65:85 */     String mesUnoNueve = Integer.toString(mes).length() == 1 ? "0" + mes : Integer.toString(mes);
/* 66:   */     
/* 67:87 */     String a = Integer.toString(anio).substring(2, 4) + "" + mesUnoNueve + "" + diasUnoNueve;
/* 68:   */     
/* 69:89 */     return Integer.valueOf(Integer.parseInt(a));
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.GuiaAereaDao
 * JD-Core Version:    0.7.0.1
 */