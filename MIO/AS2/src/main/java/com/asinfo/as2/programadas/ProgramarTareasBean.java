/*   1:    */ package com.asinfo.as2.programadas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.TareaProgramada;
/*   4:    */ import com.asinfo.as2.programacion.TareaProcesarAsistencia;
/*   5:    */ import com.asinfo.as2.programacion.TareaSincronizarGuiaRemision;
/*   6:    */ import com.asinfo.as2.programacion.TareaSincronizarHaciaFacturaE;
/*   7:    */ import com.asinfo.as2.programacion.TareaSincronizarMovimientosProduccion;
/*   8:    */ import com.asinfo.as2.programacion.TareaSincronizarRetencionProveedor;
/*   9:    */ import com.asinfo.as2.programacion.TareaSincronizarVentas;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ApplicationScoped;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import org.quartz.CronScheduleBuilder;
/*  19:    */ import org.quartz.CronTrigger;
/*  20:    */ import org.quartz.JobBuilder;
/*  21:    */ import org.quartz.JobDetail;
/*  22:    */ import org.quartz.Scheduler;
/*  23:    */ import org.quartz.SchedulerException;
/*  24:    */ import org.quartz.SchedulerFactory;
/*  25:    */ import org.quartz.TriggerBuilder;
/*  26:    */ import org.quartz.impl.StdSchedulerFactory;
/*  27:    */ 
/*  28:    */ @ManagedBean(eager=true)
/*  29:    */ @ApplicationScoped
/*  30:    */ public class ProgramarTareasBean
/*  31:    */ {
/*  32:    */   @EJB
/*  33:    */   private ServicioGenerico<TareaProgramada> servicioTareaProgramada;
/*  34:    */   Scheduler sched;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 59 */       SchedulerFactory sf = new StdSchedulerFactory();
/*  42: 60 */       this.sched = sf.getScheduler();
/*  43:    */       
/*  44:    */ 
/*  45: 63 */       Map<String, String> filters = new HashMap();
/*  46: 64 */       filters.put("activo", "true");
/*  47: 65 */       List<TareaProgramada> lista = this.servicioTareaProgramada.obtenerListaPorPagina(TareaProgramada.class, 0, 100000, null, true, filters);
/*  48: 66 */       for (TareaProgramada tareaProgramada : lista) {
/*  49: 67 */         iniciarTareas(tareaProgramada);
/*  50:    */       }
/*  51:    */     }
/*  52:    */     catch (Exception e)
/*  53:    */     {
/*  54: 70 */       e.printStackTrace();
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void iniciarTareas(TareaProgramada tarea)
/*  59:    */     throws SchedulerException
/*  60:    */   {
/*  61: 76 */     JobDetail job = null;
/*  62: 77 */     CronTrigger trigger = null;
/*  63:    */     
/*  64: 79 */     String nombreTarea = tarea.getTareaProgramadaEnum() + "." + tarea.getIdOrganizacion();
/*  65: 81 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$TareaProgramadaEnum[tarea.getTareaProgramadaEnum().ordinal()])
/*  66:    */     {
/*  67:    */     case 1: 
/*  68: 84 */       job = JobBuilder.newJob(TareaProcesarAsistencia.class).withIdentity("job_" + nombreTarea, "group_" + nombreTarea).usingJobData("idOrganizacion", Integer.valueOf(tarea.getIdOrganizacion())).build();
/*  69:    */       
/*  70: 86 */       trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger_" + nombreTarea, "group_" + nombreTarea).withSchedule(CronScheduleBuilder.cronSchedule(tarea.getExpresionTiempo())).forJob(job).build();
/*  71: 87 */       break;
/*  72:    */     case 2: 
/*  73: 90 */       job = JobBuilder.newJob(TareaSincronizarGuiaRemision.class).withIdentity("job_" + nombreTarea, "group_" + nombreTarea).usingJobData("idOrganizacion", Integer.valueOf(tarea.getIdOrganizacion())).build();
/*  74:    */       
/*  75: 92 */       trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger_" + nombreTarea, "group_" + nombreTarea).withSchedule(CronScheduleBuilder.cronSchedule(tarea.getExpresionTiempo())).forJob(job).build();
/*  76: 93 */       break;
/*  77:    */     case 3: 
/*  78: 96 */       job = JobBuilder.newJob(TareaSincronizarHaciaFacturaE.class).withIdentity("job_" + nombreTarea, "group_" + nombreTarea).usingJobData("idOrganizacion", Integer.valueOf(tarea.getIdOrganizacion())).build();
/*  79:    */       
/*  80: 98 */       trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger_" + nombreTarea, "group_" + nombreTarea).withSchedule(CronScheduleBuilder.cronSchedule(tarea.getExpresionTiempo())).forJob(job).build();
/*  81: 99 */       break;
/*  82:    */     case 4: 
/*  83:102 */       job = JobBuilder.newJob(TareaSincronizarRetencionProveedor.class).withIdentity("job_" + nombreTarea, "group_" + nombreTarea).usingJobData("idOrganizacion", Integer.valueOf(tarea.getIdOrganizacion())).build();
/*  84:    */       
/*  85:104 */       trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger_" + nombreTarea, "group_" + nombreTarea).withSchedule(CronScheduleBuilder.cronSchedule(tarea.getExpresionTiempo())).forJob(job).build();
/*  86:105 */       break;
/*  87:    */     case 5: 
/*  88:108 */       job = JobBuilder.newJob(TareaSincronizarVentas.class).withIdentity("job_" + nombreTarea, "group_" + nombreTarea).usingJobData("idOrganizacion", Integer.valueOf(tarea.getIdOrganizacion())).build();
/*  89:    */       
/*  90:110 */       trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger_" + nombreTarea, "group_" + nombreTarea).withSchedule(CronScheduleBuilder.cronSchedule(tarea.getExpresionTiempo())).forJob(job).build();
/*  91:111 */       break;
/*  92:    */     case 6: 
/*  93:114 */       job = JobBuilder.newJob(TareaSincronizarMovimientosProduccion.class).withIdentity("job_" + nombreTarea, "group_" + nombreTarea).usingJobData("idOrganizacion", Integer.valueOf(tarea.getIdOrganizacion())).build();
/*  94:    */       
/*  95:116 */       trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger_" + nombreTarea, "group_" + nombreTarea).withSchedule(CronScheduleBuilder.cronSchedule(tarea.getExpresionTiempo())).forJob(job).build();
/*  96:117 */       break;
/*  97:    */     }
/*  98:122 */     this.sched.scheduleJob(job, trigger);
/*  99:123 */     this.sched.start();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void reinicarTareas()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:128 */       if (this.sched != null) {
/* 107:129 */         this.sched.shutdown();
/* 108:    */       }
/* 109:131 */       init();
/* 110:    */     }
/* 111:    */     catch (Exception e)
/* 112:    */     {
/* 113:134 */       e.printStackTrace();
/* 114:    */     }
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.programadas.ProgramarTareasBean
 * JD-Core Version:    0.7.0.1
 */