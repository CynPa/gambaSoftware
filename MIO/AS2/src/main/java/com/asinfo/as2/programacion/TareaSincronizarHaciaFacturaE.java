/*  1:   */ package com.asinfo.as2.programacion;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compronteselectronicos.ServicioComprobanteElectronicoPeriodico;
/*  4:   */ import com.asinfo.as2.utils.ServiceLocator;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import javax.naming.Context;
/*  7:   */ import javax.naming.InitialContext;
/*  8:   */ import javax.naming.NamingException;
/*  9:   */ import org.quartz.Job;
/* 10:   */ import org.quartz.JobDataMap;
/* 11:   */ import org.quartz.JobExecutionContext;
/* 12:   */ import org.quartz.JobExecutionException;
/* 13:   */ 
/* 14:   */ public class TareaSincronizarHaciaFacturaE
/* 15:   */   implements Job
/* 16:   */ {
/* 17:   */   private ServicioComprobanteElectronicoPeriodico servicioComprobanteElectronicoPeriodico;
/* 18:   */   private Context context;
/* 19:   */   
/* 20:   */   public ServicioComprobanteElectronicoPeriodico getServicioComprobanteElectronicoPeriodico()
/* 21:   */   {
/* 22:20 */     if (this.servicioComprobanteElectronicoPeriodico == null) {
/* 23:   */       try
/* 24:   */       {
/* 25:22 */         this.context = new InitialContext();
/* 26:23 */         this.servicioComprobanteElectronicoPeriodico = ((ServicioComprobanteElectronicoPeriodico)this.context.lookup("java:global/" + ServiceLocator.APP_NAME + "/ServicioComprobanteElectronicoPeriodicoImpl"));
/* 27:   */       }
/* 28:   */       catch (NamingException e)
/* 29:   */       {
/* 30:26 */         e.printStackTrace();
/* 31:   */       }
/* 32:   */     }
/* 33:29 */     return this.servicioComprobanteElectronicoPeriodico;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void execute(JobExecutionContext jec)
/* 37:   */     throws JobExecutionException
/* 38:   */   {
/* 39:33 */     Integer idOrganizacion = (Integer)jec.getMergedJobDataMap().get("idOrganizacion");
/* 40:   */     try
/* 41:   */     {
/* 42:35 */       getServicioComprobanteElectronicoPeriodico().publicarClaveAccesoPendiente(idOrganizacion.intValue());
/* 43:   */     }
/* 44:   */     catch (Exception e)
/* 45:   */     {
/* 46:37 */       System.out.println("******************************** ERROR SINCRONIZANDO FACTURAS ELECTRONICAS A FACTURAE *****************************");
/* 47:38 */       e.printStackTrace();
/* 48:39 */       System.out.println("****************************** FIN ERROR SINCRONIZANDO FACTURAS ELECTRONICAS A FACTURAE ***************************");
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.programacion.TareaSincronizarHaciaFacturaE
 * JD-Core Version:    0.7.0.1
 */