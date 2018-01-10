/*  1:   */ package com.asinfo.as2.ventas.procesos.aerolineas.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GuiaAereaDao;
/*  4:   */ import com.asinfo.as2.entities.GuiaAerea;
/*  5:   */ import com.asinfo.as2.ventas.procesos.aerolineas.servicio.ServicioGuiaAerea;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioGuiaAereaImpl
/* 13:   */   implements ServicioGuiaAerea
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private GuiaAereaDao guiaAereaDao;
/* 17:   */   
/* 18:   */   public List<GuiaAerea> obtenerGuiasAereasPorAgentCode(String agentCode, int idOrganizacion, Date fechasDesde, Date fechaHasta, String numeroGuia)
/* 19:   */   {
/* 20:22 */     return this.guiaAereaDao.obtenerGuiasAereasPorAgentCode(agentCode, idOrganizacion, fechasDesde, fechaHasta, numeroGuia);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.servicio.impl.ServicioGuiaAereaImpl
 * JD-Core Version:    0.7.0.1
 */