/*  1:   */ package com.asinfo.as2.datosbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.VersionListaPreciosDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioVersionListaPrecios;
/*  5:   */ import com.asinfo.as2.entities.ListaPrecios;
/*  6:   */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioVersionListaPreciosImpl
/* 13:   */   implements ServicioVersionListaPrecios
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private VersionListaPreciosDao versionListaPreciosDao;
/* 17:   */   
/* 18:   */   public List<VersionListaPrecios> autocompletarListaVersionListaPrecios(String consulta, ListaPrecios listaPrecios)
/* 19:   */   {
/* 20:21 */     return this.versionListaPreciosDao.autocompletarListaVersionListaPrecios(consulta, listaPrecios);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioVersionListaPreciosImpl
 * JD-Core Version:    0.7.0.1
 */