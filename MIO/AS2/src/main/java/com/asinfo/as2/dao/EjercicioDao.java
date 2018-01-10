/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleAsiento;
/*  4:   */ import com.asinfo.as2.entities.Ejercicio;
/*  5:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class EjercicioDao
/* 13:   */   extends AbstractDaoAS2<Ejercicio>
/* 14:   */ {
/* 15:   */   public EjercicioDao()
/* 16:   */   {
/* 17:32 */     super(Ejercicio.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<DetalleAsiento> cerrarEjercicio(int idEjercicio)
/* 21:   */     throws ExcepcionAS2Financiero
/* 22:   */   {
/* 23:46 */     List<DetalleAsiento> detalles = this.em.createQuery("SELECT new DetalleAsiento(d.cuentaContable, SUM(d.debe),SUM(d.haber))  FROM DetalleAsiento d  WHERE d.asiento.periodo.ejercicio.idEjercicio = :idEjercidio AND SUBSTRING(d.cuentaContable.codigo,1,1) IN ('4','5','6') GROUP BY d.cuentaContable ").setParameter("idEjercicio", Integer.valueOf(idEjercicio)).getResultList();
/* 24:47 */     if (detalles.size() == 0) {
/* 25:49 */       throw new ExcepcionAS2Financiero("", "No existen movimientos ");
/* 26:   */     }
/* 27:51 */     return detalles;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Ejercicio recuperaEjercicio(int idEjercicio)
/* 31:   */   {
/* 32:55 */     Ejercicio ejercicio = (Ejercicio)buscarPorId(Integer.valueOf(idEjercicio));
/* 33:   */     
/* 34:57 */     ejercicio.getPeriodos().size();
/* 35:   */     
/* 36:59 */     return ejercicio;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EjercicioDao
 * JD-Core Version:    0.7.0.1
 */