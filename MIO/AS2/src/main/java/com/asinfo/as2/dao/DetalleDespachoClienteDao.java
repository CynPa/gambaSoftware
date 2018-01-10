/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DespachoCliente;
/*  4:   */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class DetalleDespachoClienteDao
/* 12:   */   extends AbstractDaoAS2<DetalleDespachoCliente>
/* 13:   */ {
/* 14:   */   public DetalleDespachoClienteDao()
/* 15:   */   {
/* 16:30 */     super(DetalleDespachoCliente.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void actualizarDespachoCliente(List<DetalleDespachoCliente> listaDetalleDespachoCliente, DespachoCliente despachoCliente)
/* 20:   */   {
/* 21:35 */     StringBuilder sql = new StringBuilder();
/* 22:36 */     sql.append(" UPDATE DetalleDespachoCliente ddc ");
/* 23:37 */     sql.append(" SET ddc.despachoCliente = :despachoCliente ");
/* 24:38 */     sql.append(" WHERE ddc IN(:listaDetalleDespachoCliente) ");
/* 25:   */     
/* 26:40 */     Query query = this.em.createQuery(sql.toString());
/* 27:41 */     query.setParameter("listaDetalleDespachoCliente", listaDetalleDespachoCliente);
/* 28:42 */     query.setParameter("despachoCliente", despachoCliente);
/* 29:   */     
/* 30:44 */     query.executeUpdate();
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleDespachoClienteDao
 * JD-Core Version:    0.7.0.1
 */