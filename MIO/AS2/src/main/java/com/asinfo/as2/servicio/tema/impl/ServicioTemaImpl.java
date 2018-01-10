/*  1:   */ package com.asinfo.as2.servicio.tema.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.TemaDao;
/*  4:   */ import com.asinfo.as2.entities.Tema;
/*  5:   */ import com.asinfo.as2.servicio.ServicioTema;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTemaImpl
/* 13:   */   implements ServicioTema
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TemaDao temaDao;
/* 17:   */   
/* 18:   */   public List<Tema> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 19:   */   {
/* 20:41 */     return this.temaDao.obtenerListaCombo("nombre", true, null);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.servicio.tema.impl.ServicioTemaImpl
 * JD-Core Version:    0.7.0.1
 */