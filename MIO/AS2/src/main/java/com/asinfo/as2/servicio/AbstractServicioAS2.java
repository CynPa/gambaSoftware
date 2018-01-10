/*  1:   */ package com.asinfo.as2.servicio;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import javax.annotation.Resource;
/*  5:   */ import javax.ejb.SessionContext;
/*  6:   */ import org.apache.log4j.Logger;
/*  7:   */ 
/*  8:   */ public abstract class AbstractServicioAS2
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 3500286236705106014L;
/* 12:   */   @Resource
/* 13:   */   protected SessionContext context;
/* 14:36 */   protected static final Logger LOG = Logger.getLogger("com.asinfo.as2");
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.servicio.AbstractServicioAS2
 * JD-Core Version:    0.7.0.1
 */