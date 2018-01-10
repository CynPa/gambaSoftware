/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Date;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.faces.bean.ManagedBean;
/*  12:    */ import javax.faces.bean.ViewScoped;
/*  13:    */ import javax.faces.context.FacesContext;
/*  14:    */ import javax.faces.context.PartialViewContext;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import org.primefaces.context.RequestContext;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class AjustesContablesPrefacturaClienteBean
/*  21:    */   extends PageController
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 6180042509279363488L;
/*  24:    */   @EJB
/*  25:    */   private transient ServicioPrefacturaCliente servicioPrefacturaCliente;
/*  26:    */   @NotNull
/*  27: 46 */   private Date fechaDesde = new Date();
/*  28:    */   @NotNull
/*  29: 48 */   private Date fechaHasta = new Date();
/*  30: 51 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  31:    */   
/*  32:    */   public void procesar()
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 55 */       this.servicioPrefacturaCliente.contabilizarAjustesPrefacturaCliente(this.fechaDesde, this.fechaHasta);
/*  37: 56 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/*  38:    */     }
/*  39:    */     catch (ExcepcionAS2 e)
/*  40:    */     {
/*  41: 58 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  42: 59 */       e.printStackTrace();
/*  43:    */     }
/*  44:    */     catch (AS2Exception e)
/*  45:    */     {
/*  46: 61 */       this.exContabilizacion = e;
/*  47: 62 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  48: 63 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Date getFechaDesde()
/*  53:    */   {
/*  54: 73 */     return this.fechaDesde;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setFechaDesde(Date fechaDesde)
/*  58:    */   {
/*  59: 83 */     this.fechaDesde = fechaDesde;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Date getFechaHasta()
/*  63:    */   {
/*  64: 92 */     return this.fechaHasta;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setFechaHasta(Date fechaHasta)
/*  68:    */   {
/*  69:102 */     this.fechaHasta = fechaHasta;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public AS2Exception getExContabilizacion()
/*  73:    */   {
/*  74:111 */     return this.exContabilizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/*  78:    */   {
/*  79:121 */     this.exContabilizacion = exContabilizacion;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.AjustesContablesPrefacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */