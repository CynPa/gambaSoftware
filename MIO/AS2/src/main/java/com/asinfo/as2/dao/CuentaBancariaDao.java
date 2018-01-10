/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaBancaria;
/*  4:   */ import com.asinfo.as2.entities.Empresa;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class CuentaBancariaDao
/* 12:   */   extends AbstractDaoAS2<CuentaBancaria>
/* 13:   */ {
/* 14:   */   public CuentaBancariaDao()
/* 15:   */   {
/* 16:36 */     super(CuentaBancaria.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public List<CuentaBancaria> listaCuentaBancariaPorCliente(Empresa cliente)
/* 20:   */   {
/* 21:47 */     String sql = " SELECT cb FROM CuentaBancariaEmpresa cbe  INNER JOIN cbe.empresa e INNER JOIN cbe.cuentaBancaria cb INNER JOIN FETCH cb.banco b WHERE e = :cliente";
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:53 */     Query query = this.em.createQuery(sql);
/* 28:54 */     query.setParameter("cliente", cliente);
/* 29:55 */     return query.getResultList();
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CuentaBancariaDao
 * JD-Core Version:    0.7.0.1
 */