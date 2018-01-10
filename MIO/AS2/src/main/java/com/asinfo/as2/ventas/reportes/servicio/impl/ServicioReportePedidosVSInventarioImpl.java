/*  1:   */ package com.asinfo.as2.ventas.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.ventas.ReportePedidosVSInventarioDao;
/*  4:   */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  5:   */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReportePedidosVSInventario;
/*  6:   */ import java.math.BigDecimal;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.HashMap;
/* 10:   */ import java.util.Iterator;
/* 11:   */ import java.util.List;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.ejb.Stateless;
/* 14:   */ 
/* 15:   */ @Stateless
/* 16:   */ public class ServicioReportePedidosVSInventarioImpl
/* 17:   */   implements ServicioReportePedidosVSInventario
/* 18:   */ {
/* 19:   */   @EJB
/* 20:   */   private ReportePedidosVSInventarioDao reportePedidosVSInventarioDao;
/* 21:   */   
/* 22:   */   public List getPedidosVSInventario(Date fecha)
/* 23:   */     throws ExcepcionAS2Ventas
/* 24:   */   {
/* 25:47 */     HashMap<Integer, Object[]> hm = new HashMap();
/* 26:48 */     List lista = this.reportePedidosVSInventarioDao.getPedidosVSInventario(fecha);
/* 27:49 */     Iterator iterator = lista.iterator();
/* 28:51 */     while (iterator.hasNext())
/* 29:   */     {
/* 30:53 */       Object[] object = (Object[])iterator.next();
/* 31:54 */       Integer idProducto = Integer.valueOf(Integer.parseInt(object[3].toString()));
/* 32:56 */       if (!hm.containsKey(idProducto))
/* 33:   */       {
/* 34:57 */         hm.put(idProducto, object);
/* 35:   */       }
/* 36:   */       else
/* 37:   */       {
/* 38:60 */         Object[] obAux = (Object[])hm.get(idProducto);
/* 39:61 */         obAux[1] = new BigDecimal(obAux[1].toString()).add(new BigDecimal(object[1].toString()));
/* 40:62 */         obAux[2] = new BigDecimal(obAux[2].toString()).add(new BigDecimal(object[2].toString()));
/* 41:   */       }
/* 42:   */     }
/* 43:65 */     List listaRetorno = new ArrayList(hm.values());
/* 44:66 */     return listaRetorno;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.impl.ServicioReportePedidosVSInventarioImpl
 * JD-Core Version:    0.7.0.1
 */