/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.FormaPago;
/*   4:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.NoResultException;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Predicate;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class FormaPagoDao
/*  21:    */   extends AbstractDaoAS2<FormaPago>
/*  22:    */ {
/*  23:    */   public FormaPagoDao()
/*  24:    */   {
/*  25: 47 */     super(FormaPago.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public FormaPago buscarFormaPago(Map<String, String> filters)
/*  29:    */     throws ExcepcionAS2
/*  30:    */   {
/*  31:    */     try
/*  32:    */     {
/*  33: 53 */       CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  34: 54 */       CriteriaQuery<FormaPago> cq = cb.createQuery(FormaPago.class);
/*  35: 55 */       Root<FormaPago> from = cq.from(FormaPago.class);
/*  36:    */       
/*  37: 57 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, cb, from);
/*  38: 58 */       cq.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  39:    */       
/*  40: 60 */       CriteriaQuery<FormaPago> selectEmpresa = cq.select(from);
/*  41: 61 */       return (FormaPago)this.em.createQuery(selectEmpresa).getSingleResult();
/*  42:    */     }
/*  43:    */     catch (NoResultException e)
/*  44:    */     {
/*  45: 63 */       throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + (String)filters.get("nombre"));
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public FormaPago buscarPorCodigo(String codigo)
/*  50:    */   {
/*  51: 75 */     return (FormaPago)this.em.createQuery("select f from FormaPago f where f.codigo = :codigo").setParameter("codigo", codigo).getSingleResult();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public FormaPago cargarDetalle(int idFormaPago)
/*  55:    */   {
/*  56: 87 */     FormaPago formaPago = (FormaPago)buscarPorId(Integer.valueOf(idFormaPago));
/*  57:    */     
/*  58: 89 */     return formaPago;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public FormaPago devuelveFormaPagoPredeterminada()
/*  62:    */   {
/*  63: 98 */     String sql = "SELECT f FROM FormaPago f WHERE f.predeterminado = true";
/*  64: 99 */     Query query = this.em.createQuery(sql);
/*  65:100 */     return (FormaPago)query.getResultList().get(0);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public FormaPago formaPagoPorTipoRetencionYPorcentaje(BigDecimal porcentajeRetencion, boolean indicadorRetencionFuente, boolean indicadorRetencionIVA, int idOrganizacion)
/*  69:    */   {
/*  70:114 */     StringBuilder sql = new StringBuilder();
/*  71:115 */     sql.append(" SELECT fp FROM FormaPago fp ");
/*  72:116 */     sql.append(" WHERE fp.porcentajeRetencion >= :porcentajeRetencion ");
/*  73:117 */     sql.append(" AND fp.indicadorRetencionFuente = :indicadorRetencionFuente ");
/*  74:118 */     sql.append(" AND fp.indicadorRetencionIva = :indicadorRetencionIVA ");
/*  75:119 */     sql.append(" AND fp.idOrganizacion = :idOrganizacion ");
/*  76:120 */     sql.append(" ORDER BY fp.porcentajeRetencion DESC ");
/*  77:    */     
/*  78:122 */     Query query = this.em.createQuery(sql.toString());
/*  79:123 */     query.setParameter("porcentajeRetencion", porcentajeRetencion);
/*  80:124 */     query.setParameter("indicadorRetencionFuente", Boolean.valueOf(indicadorRetencionFuente));
/*  81:125 */     query.setParameter("indicadorRetencionIVA", Boolean.valueOf(indicadorRetencionIVA));
/*  82:126 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  83:127 */     query.setMaxResults(1);
/*  84:    */     try
/*  85:    */     {
/*  86:130 */       return (FormaPago)query.getSingleResult();
/*  87:    */     }
/*  88:    */     catch (NoResultException e) {}
/*  89:132 */     return null;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.FormaPagoDao
 * JD-Core Version:    0.7.0.1
 */