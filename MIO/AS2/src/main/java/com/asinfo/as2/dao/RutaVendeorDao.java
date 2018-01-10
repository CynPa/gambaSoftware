/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Cliente;
/*  4:   */ import com.asinfo.as2.entities.RutaVendedor;
/*  5:   */ import com.asinfo.as2.enumeraciones.DiaSemanaEnum;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class RutaVendeorDao
/* 13:   */   extends AbstractDaoAS2<RutaVendedor>
/* 14:   */ {
/* 15:   */   public RutaVendeorDao()
/* 16:   */   {
/* 17:35 */     super(RutaVendedor.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<Cliente> buscarClientesPorDiaSemanaVendedor(int idUsuarioVendedor, DiaSemanaEnum diaSemana)
/* 21:   */   {
/* 22:42 */     StringBuilder sql = new StringBuilder();
/* 23:43 */     sql.append(" SELECT c ");
/* 24:44 */     sql.append(" FROM Cliente c, RutaVendedor vs ");
/* 25:45 */     sql.append(" INNER JOIN FETCH c.empresa e ");
/* 26:46 */     sql.append(" INNER JOIN vs.sector sec1 ");
/* 27:47 */     sql.append(" INNER JOIN vs.usuario us1 ");
/* 28:48 */     sql.append(" LEFT JOIN FETCH c.listaPrecios lp ");
/* 29:49 */     sql.append(" INNER JOIN FETCH e.tipoIdentificacion ti ");
/* 30:50 */     sql.append(" INNER JOIN c.agenteComercial ac ");
/* 31:51 */     sql.append(" INNER JOIN c.sector sec ");
/* 32:52 */     sql.append(" WHERE ac.idUsuario = :idUsuario");
/* 33:53 */     sql.append(" AND us1.idUsuario = :idUsuario ");
/* 34:54 */     sql.append(" AND sec1.idSector = sec.idSector ");
/* 35:55 */     sql.append(" AND vs.diaSemana = :diaSemana ");
/* 36:56 */     sql.append(" AND e.activo IS TRUE ");
/* 37:   */     
/* 38:58 */     Query query = this.em.createQuery(sql.toString());
/* 39:59 */     query.setParameter("idUsuario", Integer.valueOf(idUsuarioVendedor));
/* 40:60 */     query.setParameter("diaSemana", diaSemana);
/* 41:61 */     return query.getResultList();
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RutaVendeorDao
 * JD-Core Version:    0.7.0.1
 */