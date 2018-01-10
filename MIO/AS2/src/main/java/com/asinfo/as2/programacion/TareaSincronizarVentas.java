/*  1:   */ package com.asinfo.as2.programacion;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compronteselectronicos.ServicioComprobanteElectronicoPeriodico;
/*  4:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  5:   */ import com.asinfo.as2.utils.ServiceLocator;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.naming.Context;
/* 10:   */ import javax.naming.InitialContext;
/* 11:   */ import javax.naming.NamingException;
/* 12:   */ import org.quartz.Job;
/* 13:   */ import org.quartz.JobDataMap;
/* 14:   */ import org.quartz.JobExecutionContext;
/* 15:   */ import org.quartz.JobExecutionException;
/* 16:   */ 
/* 17:   */ public class TareaSincronizarVentas
/* 18:   */   implements Job
/* 19:   */ {
/* 20:   */   private ServicioComprobanteElectronicoPeriodico servicioComprobanteElectronicoPeriodico;
/* 21:   */   private Context context;
/* 22:   */   
/* 23:   */   public ServicioComprobanteElectronicoPeriodico getServicioComprobanteElectronicoPeriodico()
/* 24:   */   {
/* 25:24 */     if (this.servicioComprobanteElectronicoPeriodico == null) {
/* 26:   */       try
/* 27:   */       {
/* 28:26 */         this.context = new InitialContext();
/* 29:27 */         this.servicioComprobanteElectronicoPeriodico = ((ServicioComprobanteElectronicoPeriodico)this.context.lookup("java:global/" + ServiceLocator.APP_NAME + "/ServicioComprobanteElectronicoPeriodicoImpl"));
/* 30:   */       }
/* 31:   */       catch (NamingException e)
/* 32:   */       {
/* 33:30 */         e.printStackTrace();
/* 34:   */       }
/* 35:   */     }
/* 36:33 */     return this.servicioComprobanteElectronicoPeriodico;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void execute(JobExecutionContext jec)
/* 40:   */     throws JobExecutionException
/* 41:   */   {
/* 42:37 */     Integer idOrganizacion = (Integer)jec.getMergedJobDataMap().get("idOrganizacion");
/* 43:   */     try
/* 44:   */     {
/* 45:39 */       List<DocumentoBase> listaDocumentoBase = new ArrayList();
/* 46:40 */       listaDocumentoBase.add(DocumentoBase.FACTURA_CLIENTE);
/* 47:41 */       listaDocumentoBase.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 48:42 */       listaDocumentoBase.add(DocumentoBase.DEVOLUCION_CLIENTE);
/* 49:43 */       listaDocumentoBase.add(DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 50:   */       
/* 51:45 */       getServicioComprobanteElectronicoPeriodico().sincronizarComprobantesSRI(idOrganizacion.intValue(), listaDocumentoBase);
/* 52:   */     }
/* 53:   */     catch (Exception e)
/* 54:   */     {
/* 55:47 */       System.out.println("******************************** ERROR SINCRONIZANDO FACTURAS ELECTRONICAS AL SRI *****************************");
/* 56:48 */       e.printStackTrace();
/* 57:49 */       System.out.println("****************************** FIN ERROR SINCRONIZANDO FACTURAS ELECTRONICAS AL SRI ***************************");
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.programacion.TareaSincronizarVentas
 * JD-Core Version:    0.7.0.1
 */