/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ConfiguracionExtractoBancario;
/*   4:    */ import com.asinfo.as2.entities.DetalleConfiguracionExtractoBancario;
/*   5:    */ import com.asinfo.as2.entities.DetalleExtractoBancario;
/*   6:    */ import com.asinfo.as2.entities.ExtractoBancario;
/*   7:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Fetch;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Order;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class ExtractoBancarioDao
/*  21:    */   extends AbstractDaoAS2<ExtractoBancario>
/*  22:    */ {
/*  23:    */   public ExtractoBancarioDao()
/*  24:    */   {
/*  25: 45 */     super(ExtractoBancario.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public InterfazContableProceso cargarDetalle(InterfazContableProceso interfazContableProceso)
/*  29:    */   {
/*  30: 50 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  31: 51 */     CriteriaQuery<InterfazContableProceso> cq = cb.createQuery(InterfazContableProceso.class);
/*  32: 52 */     Root<InterfazContableProceso> from = cq.from(InterfazContableProceso.class);
/*  33: 53 */     from.fetch("documento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*  34: 54 */     Fetch<Object, Object> cuentaBancaria = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  35: 55 */     cuentaBancaria.fetch("cuentaContableBanco", JoinType.LEFT);
/*  36: 56 */     cuentaBancaria.fetch("cuentaBancaria", JoinType.LEFT);
/*  37:    */     
/*  38: 58 */     cq.where(cb.equal(from.get("idInterfazContableProceso"), Integer.valueOf(interfazContableProceso.getId())));
/*  39: 59 */     CriteriaQuery<InterfazContableProceso> select = cq.select(from);
/*  40: 60 */     interfazContableProceso = (InterfazContableProceso)this.em.createQuery(select).getSingleResult();
/*  41:    */     
/*  42: 62 */     this.em.detach(interfazContableProceso);
/*  43:    */     
/*  44: 64 */     CriteriaQuery<ExtractoBancario> cqExtracto = cb.createQuery(ExtractoBancario.class);
/*  45: 65 */     Root<ExtractoBancario> fromExtracto = cqExtracto.from(ExtractoBancario.class);
/*  46:    */     
/*  47: 67 */     cqExtracto.where(cb.equal(fromExtracto.get("interfazContableProceso"), interfazContableProceso));
/*  48: 68 */     CriteriaQuery<ExtractoBancario> selectExtracto = cqExtracto.select(fromExtracto);
/*  49: 69 */     List<ExtractoBancario> listaExtractoBancario = this.em.createQuery(selectExtracto).getResultList();
/*  50:    */     
/*  51: 71 */     interfazContableProceso.setListaExtractoBancario(listaExtractoBancario);
/*  52: 73 */     for (ExtractoBancario extractoBancario : listaExtractoBancario)
/*  53:    */     {
/*  54: 74 */       this.em.detach(extractoBancario);
/*  55:    */       
/*  56: 76 */       CriteriaQuery<DetalleExtractoBancario> cqDetalleExtracto = cb.createQuery(DetalleExtractoBancario.class);
/*  57: 77 */       Root<DetalleExtractoBancario> fromDetalleExtracto = cqDetalleExtracto.from(DetalleExtractoBancario.class);
/*  58: 78 */       fromDetalleExtracto.fetch("cuentaContable", JoinType.LEFT);
/*  59:    */       
/*  60: 80 */       cqDetalleExtracto.where(cb.equal(fromDetalleExtracto.get("extractoBancario"), extractoBancario));
/*  61:    */       
/*  62: 82 */       CriteriaQuery<DetalleExtractoBancario> selectDetalleExtracto = cqDetalleExtracto.select(fromDetalleExtracto);
/*  63: 83 */       List<DetalleExtractoBancario> listaDetalleExtractoBancario = this.em.createQuery(selectDetalleExtracto).getResultList();
/*  64:    */       
/*  65: 85 */       extractoBancario.setListaDetalleExtractoBancario(listaDetalleExtractoBancario);
/*  66:    */     }
/*  67: 88 */     return interfazContableProceso;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public ConfiguracionExtractoBancario cargarDetalle(ConfiguracionExtractoBancario configuracionExtracto)
/*  71:    */   {
/*  72: 93 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  73: 94 */     CriteriaQuery<ConfiguracionExtractoBancario> cq = cb.createQuery(ConfiguracionExtractoBancario.class);
/*  74: 95 */     Root<ConfiguracionExtractoBancario> from = cq.from(ConfiguracionExtractoBancario.class);
/*  75:    */     
/*  76: 97 */     from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT).fetch("cuentaBancaria", JoinType.LEFT);
/*  77:    */     
/*  78: 99 */     cq.where(cb.equal(from.get("idConfiguracionExtractoBancario"), Integer.valueOf(configuracionExtracto.getId())));
/*  79:100 */     CriteriaQuery<ConfiguracionExtractoBancario> select = cq.select(from);
/*  80:101 */     configuracionExtracto = (ConfiguracionExtractoBancario)this.em.createQuery(select).getSingleResult();
/*  81:    */     
/*  82:103 */     this.em.detach(configuracionExtracto);
/*  83:    */     
/*  84:105 */     CriteriaQuery<DetalleConfiguracionExtractoBancario> cqDetalle = cb.createQuery(DetalleConfiguracionExtractoBancario.class);
/*  85:106 */     Root<DetalleConfiguracionExtractoBancario> fromDetalle = cqDetalle.from(DetalleConfiguracionExtractoBancario.class);
/*  86:107 */     fromDetalle.fetch("cuentaContable", JoinType.LEFT);
/*  87:108 */     fromDetalle.fetch("cuentaContable2", JoinType.LEFT);
/*  88:    */     
/*  89:110 */     cqDetalle.where(cb.equal(fromDetalle.get("configuracionExtractoBancario"), configuracionExtracto));
/*  90:111 */     Order orden = cb.asc(fromDetalle.get("orden"));
/*  91:112 */     Order fechaCreacion = cb.asc(fromDetalle.get("fechaCreacion"));
/*  92:113 */     cqDetalle.orderBy(new Order[] { orden, fechaCreacion });
/*  93:114 */     CriteriaQuery<DetalleConfiguracionExtractoBancario> selectExtracto = cqDetalle.select(fromDetalle);
/*  94:115 */     List<DetalleConfiguracionExtractoBancario> listaDetalleConfiguracionExtractoBancario = this.em.createQuery(selectExtracto).getResultList();
/*  95:    */     
/*  96:117 */     configuracionExtracto.setListaDetalleConfiguracionExtractoBancario(listaDetalleConfiguracionExtractoBancario);
/*  97:    */     
/*  98:119 */     return configuracionExtracto;
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ExtractoBancarioDao
 * JD-Core Version:    0.7.0.1
 */