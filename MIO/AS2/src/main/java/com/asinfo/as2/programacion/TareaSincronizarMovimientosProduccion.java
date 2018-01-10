/*  1:   */ package com.asinfo.as2.programacion;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  4:   */ import com.asinfo.as2.utils.ServiceLocator;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import javax.naming.Context;
/*  7:   */ import javax.naming.InitialContext;
/*  8:   */ import javax.naming.NamingException;
/*  9:   */ import org.quartz.Job;
/* 10:   */ import org.quartz.JobExecutionContext;
/* 11:   */ import org.quartz.JobExecutionException;
/* 12:   */ 
/* 13:   */ public class TareaSincronizarMovimientosProduccion
/* 14:   */   implements Job
/* 15:   */ {
/* 16:   */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/* 17:   */   private Context context;
/* 18:   */   
/* 19:   */   public ServicioOrdenFabricacion getServicioServicioOrdenFabricacion()
/* 20:   */   {
/* 21:20 */     if (this.servicioOrdenFabricacion == null) {
/* 22:   */       try
/* 23:   */       {
/* 24:22 */         this.context = new InitialContext();
/* 25:   */         
/* 26:24 */         this.servicioOrdenFabricacion = ((ServicioOrdenFabricacion)this.context.lookup("java:global/" + ServiceLocator.APP_NAME + "/ServicioOrdenFabricacionImpl"));
/* 27:   */       }
/* 28:   */       catch (NamingException e)
/* 29:   */       {
/* 30:26 */         e.printStackTrace();
/* 31:   */       }
/* 32:   */     }
/* 33:29 */     return this.servicioOrdenFabricacion;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void execute(JobExecutionContext jec)
/* 37:   */     throws JobExecutionException
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:34 */       System.out.println("******************************** INICIO SINCRONIZANDO MOVIMIENTOS PRODUCCION *****************************");
/* 42:35 */       getServicioServicioOrdenFabricacion().sincronizarMovimientosProduccion();
/* 43:36 */       System.out.println("******************************** FIN SINCRONIZANDO MOVIMIENTOS PRODUCCION *****************************");
/* 44:   */     }
/* 45:   */     catch (Exception e)
/* 46:   */     {
/* 47:38 */       System.out.println("******************************** ERROR SINCRONIZANDO FACTURAS ELECTRONICAS AL SRI *****************************");
/* 48:39 */       e.printStackTrace();
/* 49:40 */       System.out.println("****************************** FIN ERROR SINCRONIZANDO FACTURAS ELECTRONICAS AL SRI ***************************");
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.programacion.TareaSincronizarMovimientosProduccion
 * JD-Core Version:    0.7.0.1
 */