/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   5:    */ import com.asinfo.as2.entities.aerolineas.CargaArchivo;
/*   6:    */ import com.asinfo.as2.entities.aerolineas.DetalleTicket;
/*   7:    */ import com.asinfo.as2.entities.aerolineas.Ticket;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.NoResultException;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ import javax.persistence.TypedQuery;
/*  19:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  20:    */ import javax.persistence.criteria.CriteriaQuery;
/*  21:    */ import javax.persistence.criteria.JoinType;
/*  22:    */ import javax.persistence.criteria.Root;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class CargaBSPDao
/*  26:    */   extends AbstractDaoAS2<CargaArchivo>
/*  27:    */ {
/*  28:    */   @EJB
/*  29:    */   private ServicioGenerico<Ticket> servicioTicket;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<DetalleTicket> servicioDetalleTicket;
/*  32:    */   
/*  33:    */   public CargaBSPDao()
/*  34:    */   {
/*  35: 43 */     super(CargaArchivo.class);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public CargaArchivo buscarBSPReferenciaIndicador(int idOrganizacion, String referenciaArchivo, boolean respaldo)
/*  39:    */   {
/*  40: 48 */     StringBuilder sql = new StringBuilder();
/*  41: 49 */     sql.append(" SELECT bsp FROM CargaArchivo bsp ");
/*  42: 50 */     sql.append(" WHERE bsp.idOrganizacion = :idOrganizacion ");
/*  43: 51 */     sql.append(" AND bsp.referenciaArchivo = :referenciaArchivo ");
/*  44: 52 */     sql.append(" AND bsp.indicadorRespaldo = :respaldo ");
/*  45: 53 */     sql.append(" AND bsp.estado != :estado ");
/*  46:    */     
/*  47: 55 */     Query query = this.em.createQuery(sql.toString());
/*  48: 56 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  49: 57 */     query.setParameter("referenciaArchivo", referenciaArchivo);
/*  50: 58 */     query.setParameter("respaldo", Boolean.valueOf(respaldo));
/*  51: 59 */     query.setParameter("estado", Estado.ANULADO);
/*  52:    */     try
/*  53:    */     {
/*  54: 62 */       return (CargaArchivo)query.getSingleResult();
/*  55:    */     }
/*  56:    */     catch (NoResultException e) {}
/*  57: 64 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public CargaArchivo cargarDetalle(int idCargaArchivo)
/*  61:    */   {
/*  62: 71 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  63: 72 */     CriteriaQuery<CargaArchivo> cq = cb.createQuery(CargaArchivo.class);
/*  64:    */     
/*  65: 74 */     Root<CargaArchivo> from = cq.from(CargaArchivo.class);
/*  66: 75 */     from.fetch("documento", JoinType.LEFT);
/*  67:    */     
/*  68: 77 */     cq.where(cb.equal(from.get("idCargaArchivo"), Integer.valueOf(idCargaArchivo)));
/*  69: 78 */     CargaArchivo bsp = (CargaArchivo)this.em.createQuery(cq.select(from)).getSingleResult();
/*  70: 79 */     this.em.detach(bsp);
/*  71:    */     
/*  72: 81 */     CriteriaQuery<Ticket> cqVersiones = cb.createQuery(Ticket.class);
/*  73: 82 */     Root<Ticket> detalle = cqVersiones.from(Ticket.class);
/*  74: 83 */     cqVersiones.where(cb.equal(detalle.get("bsp"), bsp));
/*  75: 84 */     List<Ticket> listaVersiones = this.em.createQuery(cqVersiones).getResultList();
/*  76: 85 */     bsp.setListaTicket(listaVersiones);
/*  77: 86 */     listaVersiones.size();
/*  78: 87 */     for (Iterator localIterator1 = listaVersiones.iterator(); localIterator1.hasNext();)
/*  79:    */     {
/*  80: 87 */       vc = (Ticket)localIterator1.next();
/*  81: 88 */       this.em.detach(vc);
/*  82: 89 */       vc.setBsp(bsp);
/*  83:    */       
/*  84: 91 */       CriteriaQuery<DetalleTicket> cqDetalleCaja = cb.createQuery(DetalleTicket.class);
/*  85: 92 */       Root<DetalleTicket> detalleCaja = cqDetalleCaja.from(DetalleTicket.class);
/*  86:    */       
/*  87: 94 */       cqDetalleCaja.where(cb.equal(detalleCaja.get("ticket"), vc));
/*  88:    */       
/*  89: 96 */       List<DetalleTicket> listaDetalleCajaPacking = this.em.createQuery(cqDetalleCaja).getResultList();
/*  90:    */       
/*  91: 98 */       vc.setListaDetalleTicket(listaDetalleCajaPacking);
/*  92:100 */       for (DetalleTicket com : listaDetalleCajaPacking)
/*  93:    */       {
/*  94:101 */         this.em.detach(com);
/*  95:102 */         com.setTicket(vc);
/*  96:    */       }
/*  97:    */     }
/*  98:    */     Ticket vc;
/*  99:107 */     return bsp;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<Object[]> obtenerListaTicketPorFechas(Date fechaDesde, Date fechaHasta, int idOrganizacion, PuntoDeVenta puntoDeVenta)
/* 103:    */   {
/* 104:147 */     StringBuilder sql = new StringBuilder();
/* 105:148 */     sql.append(" SELECT distinct t.periodo, pv  FROM Ticket t ");
/* 106:149 */     sql.append(" LEFT JOIN t.puntoDeVenta pv ");
/* 107:150 */     sql.append(" INNER JOIN t.bsp ca ");
/* 108:151 */     sql.append(" WHERE t.idOrganizacion = :idOrganizacion ");
/* 109:152 */     sql.append(" AND t.periodo between :fechaDesde and :fechaHasta");
/* 110:153 */     sql.append(" AND ca.tipo = :ventasLocales ");
/* 111:154 */     if (puntoDeVenta != null) {
/* 112:155 */       sql.append(" AND t.puntoDeVenta = :puntoDeVenta");
/* 113:    */     }
/* 114:158 */     Query query = this.em.createQuery(sql.toString());
/* 115:159 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 116:160 */     query.setParameter("fechaDesde", fechaDesde);
/* 117:161 */     query.setParameter("fechaHasta", fechaHasta);
/* 118:162 */     query.setParameter("ventasLocales", "Ventas locales");
/* 119:164 */     if (puntoDeVenta != null) {
/* 120:165 */       query.setParameter("puntoDeVenta", puntoDeVenta);
/* 121:    */     }
/* 122:168 */     return query.getResultList();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<Ticket> obtenerListaTicke(Date fecha, PuntoDeVenta puntoVenta, int idOrganizacion)
/* 126:    */   {
/* 127:174 */     StringBuilder sql = new StringBuilder();
/* 128:175 */     sql.append(" SELECT t  FROM Ticket t ");
/* 129:176 */     sql.append(" LEFT JOIN t.puntoDeVenta pv ");
/* 130:177 */     sql.append(" WHERE t.idOrganizacion = :idOrganizacion ");
/* 131:178 */     sql.append(" AND t.periodo =:fecha ");
/* 132:179 */     sql.append(" AND pv =:puntoVenta ");
/* 133:180 */     sql.append(" AND t.indicadorContabilizado = 0 ");
/* 134:    */     
/* 135:182 */     Query query = this.em.createQuery(sql.toString());
/* 136:183 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 137:184 */     query.setParameter("fecha", fecha);
/* 138:185 */     query.setParameter("puntoVenta", puntoVenta);
/* 139:    */     
/* 140:187 */     return query.getResultList();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void eliminar(CargaArchivo entidad)
/* 144:    */   {
/* 145:192 */     for (Ticket tk : entidad.getListaTicket())
/* 146:    */     {
/* 147:193 */       tk.setEliminado(true);
/* 148:194 */       for (DetalleTicket dt : tk.getListaDetalleTicket())
/* 149:    */       {
/* 150:195 */         dt.setEliminado(true);
/* 151:196 */         this.servicioDetalleTicket.eliminar(dt);
/* 152:    */       }
/* 153:198 */       this.servicioTicket.eliminar(tk);
/* 154:    */     }
/* 155:200 */     super.eliminar(entidad);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<Asiento> listaAsientosAnular(List<Ticket> listaTicket)
/* 159:    */   {
/* 160:206 */     StringBuilder sql = new StringBuilder();
/* 161:207 */     sql.append(" select distinct a from Ticket t ");
/* 162:208 */     sql.append(" inner join t.asiento a ");
/* 163:209 */     sql.append(" where t in (:listaTicket) ");
/* 164:    */     
/* 165:211 */     Query query = this.em.createQuery(sql.toString());
/* 166:212 */     query.setParameter("listaTicket", listaTicket);
/* 167:    */     
/* 168:214 */     return query.getResultList();
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CargaBSPDao
 * JD-Core Version:    0.7.0.1
 */