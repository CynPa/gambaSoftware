/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CondicionPago;
/*   4:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.ejb.Stateless;
/*   7:    */ import javax.persistence.EntityManager;
/*   8:    */ import javax.persistence.NoResultException;
/*   9:    */ import javax.persistence.Query;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class CondicionPagoDao
/*  14:    */   extends AbstractDaoAS2<CondicionPago>
/*  15:    */ {
/*  16:    */   public CondicionPagoDao()
/*  17:    */   {
/*  18: 37 */     super(CondicionPago.class);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Long verificaExistenciaCondicionPago()
/*  22:    */   {
/*  23: 47 */     Query query = this.em.createQuery("SELECT COUNT(cp) FROM CondicionPago cp ");
/*  24:    */     
/*  25: 49 */     return (Long)query.getSingleResult();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public CondicionPago buscarPorCodigo(String codigo)
/*  29:    */   {
/*  30: 54 */     return (CondicionPago)this.em.createQuery("select c from CondicionPago c where c.codigo = :codigo").setParameter("codigo", codigo).getSingleResult();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CondicionPago buscarCondicionPagoPorDiasPlazo(int diasPlazo, int idOrganizacion)
/*  34:    */     throws ExcepcionAS2
/*  35:    */   {
/*  36: 60 */     CondicionPago condicionPago = null;
/*  37:    */     try
/*  38:    */     {
/*  39: 65 */       StringBuilder sql = new StringBuilder();
/*  40: 66 */       sql.append(" SELECT cp FROM CondicionPago cp ");
/*  41: 67 */       sql.append(" WHERE cp.diasPlazo = :diasPlazo ");
/*  42: 68 */       sql.append(" AND cp.idOrganizacion = :idOrganizacion ");
/*  43:    */       
/*  44: 70 */       TypedQuery<CondicionPago> typedQuery = this.em.createQuery(sql.toString(), CondicionPago.class);
/*  45: 71 */       typedQuery.setParameter("diasPlazo", Integer.valueOf(diasPlazo));
/*  46: 72 */       typedQuery.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  47: 73 */       typedQuery.setMaxResults(1);
/*  48:    */       
/*  49: 75 */       condicionPago = (CondicionPago)typedQuery.getSingleResult();
/*  50:    */     }
/*  51:    */     catch (NoResultException e)
/*  52:    */     {
/*  53: 80 */       throw new ExcepcionAS2("msg_error_condicion_pago_no_existe", "Dias Plazo " + diasPlazo);
/*  54:    */     }
/*  55:    */     StringBuilder sql;
/*  56: 83 */     return condicionPago;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CondicionPago devuelveCondicionPagoPredeterminada()
/*  60:    */     throws ExcepcionAS2
/*  61:    */   {
/*  62: 94 */     String sql = "SELECT c FROM CondicionPago c WHERE c.predeterminado = true";
/*  63: 95 */     Query query = this.em.createQuery(sql);
/*  64: 96 */     List<CondicionPago> lista = query.getResultList();
/*  65: 97 */     if (lista.isEmpty()) {
/*  66: 98 */       throw new ExcepcionAS2("msg_error_condicion_pago_predeterminada");
/*  67:    */     }
/*  68:100 */     return (CondicionPago)lista.get(0);
/*  69:    */   }
/*  70:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CondicionPagoDao
 * JD-Core Version:    0.7.0.1
 */