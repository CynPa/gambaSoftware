/*  1:   */ package com.asinfo.as2.dao.reportes.financiero.contabilidad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.MovimientoBancario;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ReporteChequeAsientoDao
/* 12:   */   extends AbstractDaoAS2<MovimientoBancario>
/* 13:   */ {
/* 14:   */   public ReporteChequeAsientoDao()
/* 15:   */   {
/* 16:24 */     super(MovimientoBancario.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public List getReporteChequeAsiento(int idAsiento)
/* 20:   */   {
/* 21:29 */     StringBuilder sql = new StringBuilder();
/* 22:30 */     sql.append(" SELECT COALESCE(ch.fechaPosfechado,ch.fecha), ch.beneficiario, ABS(ch.valor), ch.documentoReferencia , cbo.reporte, c.nombre");
/* 23:31 */     sql.append(" FROM MovimientoBancario ch ");
/* 24:32 */     sql.append(" INNER JOIN ch.cuentaBancariaOrganizacion cbo ");
/* 25:33 */     sql.append(" INNER JOIN ch.detalleAsiento da ");
/* 26:34 */     sql.append(" INNER JOIN da.asiento a ");
/* 27:35 */     sql.append(" INNER JOIN a.sucursal s ");
/* 28:36 */     sql.append(" INNER JOIN s.ciudad c ");
/* 29:37 */     sql.append(" WHERE a.idAsiento = :idAsiento");
/* 30:38 */     sql.append(" AND ch.formaPago.indicadorCheque=true");
/* 31:39 */     sql.append(" ORDER BY ch.documentoReferencia ");
/* 32:   */     
/* 33:41 */     Query query = this.em.createQuery(sql.toString()).setParameter("idAsiento", Integer.valueOf(idAsiento));
/* 34:42 */     return query.getResultList();
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteChequeAsientoDao
 * JD-Core Version:    0.7.0.1
 */