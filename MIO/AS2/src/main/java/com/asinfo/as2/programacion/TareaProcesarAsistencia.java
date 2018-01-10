/*  1:   */ package com.asinfo.as2.programacion;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  4:   */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistencia;
/*  5:   */ import com.asinfo.as2.utils.ServiceLocator;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ import java.text.SimpleDateFormat;
/*  8:   */ import java.util.Calendar;
/*  9:   */ import java.util.Date;
/* 10:   */ import javax.naming.Context;
/* 11:   */ import javax.naming.InitialContext;
/* 12:   */ import javax.naming.NamingException;
/* 13:   */ import org.quartz.Job;
/* 14:   */ import org.quartz.JobDataMap;
/* 15:   */ import org.quartz.JobExecutionContext;
/* 16:   */ import org.quartz.JobExecutionException;
/* 17:   */ 
/* 18:   */ public class TareaProcesarAsistencia
/* 19:   */   implements Job
/* 20:   */ {
/* 21:   */   private ServicioAsistencia servicioAsistencia;
/* 22:   */   private Context context;
/* 23:   */   
/* 24:   */   public ServicioAsistencia getServicioAsistencia()
/* 25:   */   {
/* 26:25 */     if (this.servicioAsistencia == null) {
/* 27:   */       try
/* 28:   */       {
/* 29:28 */         this.context = new InitialContext();
/* 30:29 */         this.servicioAsistencia = ((ServicioAsistencia)this.context.lookup("java:global/" + ServiceLocator.APP_NAME + "/ServicioAsistenciaImpl"));
/* 31:   */       }
/* 32:   */       catch (NamingException e)
/* 33:   */       {
/* 34:31 */         e.printStackTrace();
/* 35:   */       }
/* 36:   */     }
/* 37:34 */     return this.servicioAsistencia;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void execute(JobExecutionContext jec)
/* 41:   */     throws JobExecutionException
/* 42:   */   {
/* 43:39 */     Integer idOrganizacion = (Integer)jec.getMergedJobDataMap().get("idOrganizacion");
/* 44:   */     
/* 45:41 */     Date hoy = new Date();
/* 46:42 */     Calendar ayer = Calendar.getInstance();
/* 47:43 */     ayer.add(5, -1);
/* 48:   */     try
/* 49:   */     {
/* 50:46 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
/* 51:   */       
/* 52:48 */       getServicioAsistencia().registrarAsistencia(idOrganizacion.intValue(), ayer.getTime());
/* 53:49 */       System.out.println("Registrando marcaciones (" + sdf.format(ayer.getTime()) + "): Organizacion " + idOrganizacion);
/* 54:   */       
/* 55:51 */       getServicioAsistencia().generarAsistencia(idOrganizacion.intValue(), hoy);
/* 56:52 */       System.out.println("Generando asistencia (" + sdf.format(hoy) + "): Organizacion " + idOrganizacion);
/* 57:   */     }
/* 58:   */     catch (AS2Exception e)
/* 59:   */     {
/* 60:55 */       e.printStackTrace();
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.programacion.TareaProcesarAsistencia
 * JD-Core Version:    0.7.0.1
 */