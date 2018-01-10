/*  1:   */ package com.asinfo.as2.ventas.procesos;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.entities.PedidoCliente;
/*  5:   */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  6:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  7:   */ import com.asinfo.as2.util.AppUtil;
/*  8:   */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  9:   */ import java.util.Date;
/* 10:   */ import javax.annotation.PostConstruct;
/* 11:   */ import javax.faces.bean.ManagedBean;
/* 12:   */ import javax.faces.bean.ViewScoped;
/* 13:   */ import org.apache.log4j.Logger;
/* 14:   */ 
/* 15:   */ @ManagedBean
/* 16:   */ @ViewScoped
/* 17:   */ public class AprobarPedidoClienteBean
/* 18:   */   extends PedidoClienteBean
/* 19:   */ {
/* 20:   */   private static final long serialVersionUID = 1L;
/* 21:   */   private String descripcionAprobacion;
/* 22:   */   
/* 23:   */   @PostConstruct
/* 24:   */   public void init()
/* 25:   */   {
/* 26:37 */     this.indicadorAprobar = true;
/* 27:38 */     super.init();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void procesarTodoPedidoCliente()
/* 31:   */   {
/* 32:   */     try
/* 33:   */     {
/* 34:43 */       for (PedidoCliente pedidoCliente : this.listaPedidoCliente)
/* 35:   */       {
/* 36:44 */         pedidoCliente.setDescripcion(this.descripcionAprobacion);
/* 37:45 */         this.servicioPedidoCliente.procesarPedidoCliente(pedidoCliente, Boolean.valueOf(true), this.indicadorAprobarCompleto, 
/* 38:46 */           AppUtil.getUsuarioEnSesion().getNombreUsuario(), new Date());
/* 39:   */       }
/* 40:48 */       setIndicadorRender(false);
/* 41:49 */       this.descripcionAprobacion = null;
/* 42:   */     }
/* 43:   */     catch (ExcepcionAS2Inventario e)
/* 44:   */     {
/* 45:51 */       e.printStackTrace();
/* 46:52 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 47:53 */       setIndicadorRender(false);
/* 48:54 */       this.descripcionAprobacion = null;
/* 49:55 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/* 50:   */     }
/* 51:   */     catch (Exception e)
/* 52:   */     {
/* 53:57 */       e.printStackTrace();
/* 54:58 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 55:59 */       setIndicadorRender(false);
/* 56:60 */       this.descripcionAprobacion = null;
/* 57:61 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String getDescripcionAprobacion()
/* 62:   */   {
/* 63:67 */     return this.descripcionAprobacion;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setDescripcionAprobacion(String descripcionAprobacion)
/* 67:   */   {
/* 68:71 */     this.descripcionAprobacion = descripcionAprobacion;
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.AprobarPedidoClienteBean
 * JD-Core Version:    0.7.0.1
 */