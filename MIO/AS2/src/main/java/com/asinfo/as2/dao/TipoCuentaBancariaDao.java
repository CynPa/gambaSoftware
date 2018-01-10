/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*  4:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class TipoCuentaBancariaDao
/* 12:   */   extends AbstractDaoAS2<TipoCuentaBancaria>
/* 13:   */ {
/* 14:   */   public TipoCuentaBancariaDao()
/* 15:   */   {
/* 16:39 */     super(TipoCuentaBancaria.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TipoCuentaBancaria buscarPorCodigo(String codigoTipoCuenta)
/* 20:   */     throws ExcepcionAS2
/* 21:   */   {
/* 22:49 */     Map<String, String> filters = new HashMap();
/* 23:50 */     filters.put("codigo", codigoTipoCuenta);
/* 24:   */     
/* 25:52 */     List<TipoCuentaBancaria> lista = obtenerListaCombo("codigo", true, filters);
/* 26:54 */     if (lista.isEmpty()) {
/* 27:55 */       throw new ExcepcionAS2("msg_no_hay_datos", " TipoCuenta codigo=" + codigoTipoCuenta);
/* 28:   */     }
/* 29:57 */     return (TipoCuentaBancaria)lista.get(0);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoCuentaBancariaDao
 * JD-Core Version:    0.7.0.1
 */