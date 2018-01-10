/*  1:   */ package com.asinfo.as2.rs.datosbase.rest;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GenericoDao;
/*  4:   */ import com.asinfo.as2.entities.EntidadBase;
/*  5:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  6:   */ import com.asinfo.as2.rs.datosbase.dto.ConsultarDatoBaseRequestDto;
/*  7:   */ import com.asinfo.as2.rs.datosbase.dto.ConsultarDatoBaseResponseDto;
/*  8:   */ import com.asinfo.as2.rs.datosbase.dto.FiltroRequestDto;
/*  9:   */ import com.asinfo.as2.servicio.ServicioGenerico;
/* 10:   */ import java.lang.reflect.Field;
/* 11:   */ import java.lang.reflect.Method;
/* 12:   */ import java.util.ArrayList;
/* 13:   */ import java.util.HashMap;
/* 14:   */ import java.util.List;
/* 15:   */ import java.util.Map;
/* 16:   */ import javax.ejb.EJB;
/* 17:   */ import javax.ejb.Stateless;
/* 18:   */ import javax.ws.rs.Consumes;
/* 19:   */ import javax.ws.rs.POST;
/* 20:   */ import javax.ws.rs.Path;
/* 21:   */ import javax.ws.rs.Produces;
/* 22:   */ 
/* 23:   */ @Path("/pedido")
/* 24:   */ @Stateless
/* 25:   */ public class ConsultarDatoBaseServicioRest
/* 26:   */ {
/* 27:   */   @EJB
/* 28:   */   private transient ServicioGenerico<?> servicioGenerico;
/* 29:   */   @EJB
/* 30:   */   private transient GenericoDao<?> genericDao;
/* 31:   */   
/* 32:   */   @POST
/* 33:   */   @Path("/consultarEntidad")
/* 34:   */   @Consumes({"application/json"})
/* 35:   */   @Produces({"application/json"})
/* 36:   */   public ConsultarDatoBaseResponseDto consultarEntidad(ConsultarDatoBaseRequestDto request)
/* 37:   */     throws AS2Exception
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:42 */       Map<String, String> mfiltros = new HashMap();
/* 42:43 */       for (FiltroRequestDto filreq : request.getListaFiltro()) {
/* 43:44 */         mfiltros.put(filreq.getCampo(), filreq.getValor());
/* 44:   */       }
/* 45:46 */       Object lresultqry = this.servicioGenerico.obtenerListaCombo(Class.forName(request.getEntidad()), request.getSortField(), request
/* 46:47 */         .getSortOrder().booleanValue(), mfiltros);
/* 47:48 */       List<Map<String, Object>> lreturn = new ArrayList();
/* 48:49 */       ConsultarDatoBaseResponseDto response = new ConsultarDatoBaseResponseDto();
/* 49:50 */       response.setListaDatoBase(lreturn);
/* 50:51 */       for (Object obj : (List)lresultqry)
/* 51:   */       {
/* 52:52 */         Class<?> entidadClase = obj.getClass();
/* 53:53 */         Map<String, Object> maptmp = new HashMap();
/* 54:54 */         for (Field field : entidadClase.getDeclaredFields())
/* 55:   */         {
/* 56:55 */           field.setAccessible(true);
/* 57:56 */           Class<?> fieldType = field.getType();
/* 58:58 */           if (!List.class.isAssignableFrom(fieldType))
/* 59:   */           {
/* 60:61 */             Object valor = field.get(obj);
/* 61:62 */             if (!EntidadBase.class.isAssignableFrom(fieldType))
/* 62:   */             {
/* 63:63 */               maptmp.put(field.getName(), valor);
/* 64:   */             }
/* 65:   */             else
/* 66:   */             {
/* 67:65 */               Method method = fieldType.getMethod("getId" + fieldType.getSimpleName(), new Class[0]);
/* 68:66 */               if (null != valor) {
/* 69:67 */                 valor = method.invoke(valor, new Object[0]);
/* 70:   */               }
/* 71:69 */               maptmp.put(field.getName(), valor);
/* 72:   */             }
/* 73:   */           }
/* 74:   */         }
/* 75:72 */         response.getListaDatoBase().add(maptmp);
/* 76:   */       }
/* 77:75 */       return response;
/* 78:   */     }
/* 79:   */     catch (Exception e)
/* 80:   */     {
/* 81:77 */       e.printStackTrace();
/* 82:78 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 83:   */     }
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.ConsultarDatoBaseServicioRest
 * JD-Core Version:    0.7.0.1
 */