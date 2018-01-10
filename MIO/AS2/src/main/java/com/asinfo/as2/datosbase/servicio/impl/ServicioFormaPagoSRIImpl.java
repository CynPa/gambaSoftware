/*  1:   */ package com.asinfo.as2.datosbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.FormaPagoSRIDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.entities.FormaPagoSRI;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioFormaPagoSRIImpl
/* 13:   */   implements ServicioFormaPagoSRI
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private FormaPagoSRIDao formaPagoSRIDao;
/* 17:   */   
/* 18:   */   public List<FormaPagoSRI> getListaFormaPagoSRI(Empresa empresa)
/* 19:   */   {
/* 20:34 */     return this.formaPagoSRIDao.getListaFormaPagoSRI(empresa);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void guardar(FormaPagoSRI entidad)
/* 24:   */   {
/* 25:39 */     this.formaPagoSRIDao.guardar(entidad);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioFormaPagoSRIImpl
 * JD-Core Version:    0.7.0.1
 */