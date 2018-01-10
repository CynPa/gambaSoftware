/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Path;
/*  15:    */ import javax.persistence.criteria.Root;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class DireccionEmpresaDao
/*  19:    */   extends AbstractDaoAS2<DireccionEmpresa>
/*  20:    */ {
/*  21:    */   public DireccionEmpresaDao()
/*  22:    */   {
/*  23: 40 */     super(DireccionEmpresa.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<DireccionEmpresa> obtenerListaComboDirecciones(int idEmpresa)
/*  27:    */   {
/*  28: 54 */     Query query = this.em.createQuery("SELECT de FROM DireccionEmpresa de INNER JOIN de.empresa em  LEFT JOIN FETCH de.ubicacion u LEFT JOIN FETCH de.ciudad c LEFT JOIN FETCH c.provincia p LEFT JOIN FETCH p.pais ps WHERE em.idEmpresa=:idEmpresa ORDER BY de.indicadorDireccionPrincipal DESC ");
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37: 63 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  38:    */     
/*  39: 65 */     return query.getResultList();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<DireccionEmpresa> obtenerListaDireccionesPrincipales(int idEmpresa)
/*  43:    */     throws ExcepcionAS2
/*  44:    */   {
/*  45: 71 */     Query query = null;
/*  46: 72 */     if (verificaDireccionPrincipalEmpresa(idEmpresa).longValue() > 0L)
/*  47:    */     {
/*  48: 74 */       query = this.em.createQuery("SELECT de  FROM DireccionEmpresa de  INNER JOIN de.empresa em  WHERE em.idEmpresa=:idEmpresa  AND de.indicadorDireccionPrincipal = :indicadorDireccionPrincipal ");
/*  49:    */       
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54: 80 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  55: 81 */       query.setParameter("indicadorDireccionPrincipal", Boolean.valueOf(true));
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 84 */       throw new ExcepcionAS2("", "msg_error_direccion_principal_no_lista_direcciones");
/*  60:    */     }
/*  61: 87 */     return query.getResultList();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Long verificaDireccionPrincipalEmpresa(int idEmpresa)
/*  65:    */   {
/*  66: 99 */     Query query = this.em.createQuery("SELECT COUNT(de)  FROM DireccionEmpresa de  INNER JOIN de.empresa em  WHERE em.idEmpresa=:idEmpresa  AND de.indicadorDireccionPrincipal = :indicadorDireccionPrincipal ");
/*  67:    */     
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:105 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  73:106 */     query.setParameter("indicadorDireccionPrincipal", Boolean.valueOf(true));
/*  74:    */     
/*  75:108 */     return (Long)query.getSingleResult();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public DireccionEmpresa buscarPorEmpresa(Empresa empresa)
/*  79:    */   {
/*  80:113 */     List<DireccionEmpresa> DireccionEmpresa = new ArrayList();
/*  81:114 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  82:115 */     CriteriaQuery<DireccionEmpresa> criteriaQuery = criteriaBuilder.createQuery(DireccionEmpresa.class);
/*  83:116 */     Root<DireccionEmpresa> from = criteriaQuery.from(DireccionEmpresa.class);
/*  84:    */     
/*  85:118 */     Path<Empresa> pathEmpresa = from.get("empresa");
/*  86:119 */     criteriaQuery.where(criteriaBuilder.equal(pathEmpresa, empresa));
/*  87:120 */     CriteriaQuery<DireccionEmpresa> select = criteriaQuery.select(from);
/*  88:121 */     TypedQuery<DireccionEmpresa> typedQuery = this.em.createQuery(select);
/*  89:    */     
/*  90:123 */     DireccionEmpresa = typedQuery.getResultList();
/*  91:124 */     if (DireccionEmpresa.size() > 0) {
/*  92:125 */       return (DireccionEmpresa)DireccionEmpresa.get(0);
/*  93:    */     }
/*  94:127 */     return null;
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DireccionEmpresaDao
 * JD-Core Version:    0.7.0.1
 */