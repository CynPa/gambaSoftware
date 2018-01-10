/*  1:   */ package com.asinfo.as2.internacionalizacion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.IdiomaDao;
/*  4:   */ import com.asinfo.as2.entities.Idioma;
/*  5:   */ import com.asinfo.as2.internacionalizacion.servicio.ServicioInternacionalizacionBean;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ServicioInternacionalizacionImpl
/* 12:   */   implements ServicioInternacionalizacionBean
/* 13:   */ {
/* 14:   */   @EJB
/* 15:   */   private IdiomaDao idiomaDao;
/* 16:   */   
/* 17:   */   public List<Idioma> getIdiomas()
/* 18:   */   {
/* 19:39 */     return this.idiomaDao.obtenerListaCombo(null, false, null);
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.internacionalizacion.servicio.impl.ServicioInternacionalizacionImpl
 * JD-Core Version:    0.7.0.1
 */