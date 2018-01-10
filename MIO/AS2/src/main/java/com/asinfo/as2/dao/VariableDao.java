/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaContable;
/*  4:   */ import com.asinfo.as2.entities.DetalleVariable;
/*  5:   */ import com.asinfo.as2.entities.Variable;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class VariableDao
/* 11:   */   extends AbstractDaoAS2<Variable>
/* 12:   */ {
/* 13:   */   public VariableDao()
/* 14:   */   {
/* 15:51 */     super(Variable.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void eliminar(Variable entidad)
/* 19:   */   {
/* 20:57 */     this.em.merge(entidad);
/* 21:58 */     if (entidad.getCodigo() != null) {
/* 22:59 */       entidad.setCodigo(null);
/* 23:   */     }
/* 24:61 */     super.eliminar(entidad);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Variable cargarDetalle(int idVariable)
/* 28:   */   {
/* 29:67 */     Variable variable = (Variable)buscarPorId(Integer.valueOf(idVariable));
/* 30:68 */     for (DetalleVariable detalle : variable.getListaDetalleVariable())
/* 31:   */     {
/* 32:69 */       detalle.getId();
/* 33:70 */       detalle.getCuentaContable().getCodigo();
/* 34:   */     }
/* 35:72 */     return variable;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.VariableDao
 * JD-Core Version:    0.7.0.1
 */