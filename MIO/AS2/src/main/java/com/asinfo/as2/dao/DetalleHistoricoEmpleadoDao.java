/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*   4:    */ import com.asinfo.as2.entities.Empleado;
/*   5:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.NoResultException;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TemporalType;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class DetalleHistoricoEmpleadoDao
/*  19:    */   extends AbstractDaoAS2<DetalleHistoricoEmpleado>
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   private EmpleadoDao empleadoDao;
/*  23:    */   
/*  24:    */   public DetalleHistoricoEmpleadoDao()
/*  25:    */   {
/*  26: 45 */     super(DetalleHistoricoEmpleado.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Date getMaximaFechaDetalleHistoricoEmpleado(int idHistoricoEmpleado)
/*  30:    */   {
/*  31:    */     try
/*  32:    */     {
/*  33: 50 */       StringBuilder sql = new StringBuilder();
/*  34: 51 */       sql.append(" SELECT MAX(dhe.fechaFin) FROM DetalleHistoricoEmpleado dhe");
/*  35: 52 */       sql.append(" WHERE dhe.historicoEmpleado.idHistoricoEmpleado= :idHistoricoEmpleado");
/*  36: 53 */       Query query = this.em.createQuery(sql.toString());
/*  37: 54 */       query.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/*  38: 55 */       return (Date)query.getSingleResult();
/*  39:    */     }
/*  40:    */     catch (NoResultException e) {}
/*  41: 57 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<DetalleHistoricoEmpleado> getListaDetalleHistoricoEmpleado(HistoricoEmpleado historicoEmpleado, Date fechaDesde, Date fechaHasta)
/*  45:    */   {
/*  46: 63 */     return getListaDetalleHistoricoEmpleado(historicoEmpleado, fechaDesde, fechaHasta, false);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<DetalleHistoricoEmpleado> getListaDetalleHistoricoEmpleado(HistoricoEmpleado historicoEmpleado, Date fechaDesde, Date fechaHasta, boolean provision)
/*  50:    */   {
/*  51: 74 */     StringBuilder sql = new StringBuilder();
/*  52: 75 */     List<DetalleHistoricoEmpleado> lista = null;
/*  53:    */     
/*  54: 77 */     String fechaInicio = provision ? " dhe.fechaInicio" : " COALESCE(dhe.fechaInicioContratoFijo, dhe.fechaInicio)";
/*  55:    */     
/*  56: 79 */     sql.append(" SELECT dhe FROM DetalleHistoricoEmpleado dhe");
/*  57: 80 */     sql.append(" WHERE dhe.historicoEmpleado = :historicoEmpleado");
/*  58: 81 */     sql.append(" AND " + fechaInicio + " <= :fechaHasta");
/*  59: 82 */     sql.append(" AND ((dhe.fechaFin IS NULL) OR (" + fechaInicio + " >= :fechaDesde) OR (:fechaDesde BETWEEN " + fechaInicio + " AND dhe.fechaFin) )");
/*  60: 83 */     sql.append(" ORDER BY dhe.fechaInicio ASC");
/*  61: 84 */     Query query = this.em.createQuery(sql.toString());
/*  62: 85 */     query.setParameter("historicoEmpleado", historicoEmpleado);
/*  63: 86 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  64: 87 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  65:    */     
/*  66: 89 */     lista = query.getResultList();
/*  67:    */     
/*  68: 91 */     return lista;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<DetalleHistoricoEmpleado> getListaDetalleHistoricoEmpleado(int idEmpleado, int anio)
/*  72:    */     throws ExcepcionAS2
/*  73:    */   {
/*  74: 96 */     List<DetalleHistoricoEmpleado> lista = new ArrayList();
/*  75: 97 */     StringBuilder sql = new StringBuilder();
/*  76:    */     try
/*  77:    */     {
/*  78:101 */       sql.append(" SELECT dhe FROM DetalleHistoricoEmpleado dhe");
/*  79:102 */       sql.append(" JOIN  dhe.historicoEmpleado he");
/*  80:103 */       sql.append(" JOIN  he.empleado e ");
/*  81:104 */       sql.append(" WHERE e.idEmpleado = :idEmpleado AND dhe.fechaFin IS NOT NULL");
/*  82:105 */       sql.append(" AND :anio between YEAR(dhe.fechaInicio) AND YEAR(dhe.fechaFin) ");
/*  83:106 */       Query query = this.em.createQuery(sql.toString());
/*  84:107 */       query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  85:108 */       query.setParameter("anio", Integer.valueOf(anio));
/*  86:109 */       lista.addAll(query.getResultList());
/*  87:    */       
/*  88:111 */       sql = new StringBuilder();
/*  89:112 */       sql.append(" SELECT dhe FROM DetalleHistoricoEmpleado dhe");
/*  90:113 */       sql.append(" JOIN dhe.historicoEmpleado he");
/*  91:114 */       sql.append(" JOIN he.empleado e ");
/*  92:115 */       sql.append(" WHERE e.idEmpleado = :idEmpleado");
/*  93:116 */       sql.append(" AND dhe.fechaFin IS NULL");
/*  94:117 */       query = this.em.createQuery(sql.toString());
/*  95:118 */       query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  96:119 */       lista.addAll(query.getResultList());
/*  97:121 */       if (lista.isEmpty()) {
/*  98:122 */         throw new NoResultException();
/*  99:    */       }
/* 100:    */     }
/* 101:    */     catch (NoResultException e2)
/* 102:    */     {
/* 103:125 */       Empleado empleado = (Empleado)this.empleadoDao.buscarPorId(Integer.valueOf(idEmpleado));
/* 104:126 */       throw new ExcepcionAS2("msg_error_carga_horaria_semanal", " " + empleado.getApellidos() + " " + empleado.getNombres());
/* 105:    */     }
/* 106:129 */     return lista;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Date getMaximaFechaInicioDetalleHistoricoEmpleado(int idHistoricoEmpleado)
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:135 */       StringBuilder sql = new StringBuilder();
/* 114:136 */       sql.append(" SELECT MAX(dhe.fechaInicio) FROM DetalleHistoricoEmpleado dhe");
/* 115:137 */       sql.append(" WHERE dhe.historicoEmpleado.idHistoricoEmpleado= :idHistoricoEmpleado");
/* 116:138 */       Query query = this.em.createQuery(sql.toString());
/* 117:139 */       query.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/* 118:140 */       return (Date)query.getSingleResult();
/* 119:    */     }
/* 120:    */     catch (NoResultException e) {}
/* 121:142 */     return null;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleHistoricoEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */