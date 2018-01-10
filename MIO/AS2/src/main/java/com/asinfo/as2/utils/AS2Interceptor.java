/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import java.util.Date;
/*  5:   */ import javax.interceptor.AroundInvoke;
/*  6:   */ import javax.interceptor.InvocationContext;
/*  7:   */ import org.apache.log4j.Logger;
/*  8:   */ 
/*  9:   */ public class AS2Interceptor
/* 10:   */ {
/* 11:   */   protected static final int TIEMPO_MAXIMO = 5;
/* 12:30 */   protected static final Logger LOG = Logger.getLogger("com.asinfo.as2");
/* 13:   */   
/* 14:   */   @AroundInvoke
/* 15:   */   public Object obtenerTiempoEjecucionMetodos(InvocationContext ic)
/* 16:   */     throws Exception
/* 17:   */   {
/* 18:35 */     long inicio = new Date().getTime();
/* 19:   */     
/* 20:37 */     Object retorno = ic.proceed();
/* 21:   */     
/* 22:39 */     long fin = new Date().getTime();
/* 23:40 */     long segundos = (fin - inicio) / 1000L;
/* 24:42 */     if (segundos > 5L) {
/* 25:43 */       LOG.info("El Metodo: " + ic.getTarget().getClass().getSimpleName() + "." + ic.getMethod().getName() + ". Ha superado el tiempo de ejecucion. (" + segundos + " segundos)");
/* 26:   */     }
/* 27:47 */     return retorno;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.AS2Interceptor
 * JD-Core Version:    0.7.0.1
 */