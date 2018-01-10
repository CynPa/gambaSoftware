/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PagoRol;
/*   7:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*   9:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  10:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class DiasFaltaBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -1172033451617750967L;
/*  28:    */   @EJB
/*  29:    */   ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  30:    */   @EJB
/*  31:    */   ServicioRubro servicioRubro;
/*  32:    */   @EJB
/*  33:    */   ServicioPagoRol servicioPagoRol;
/*  34: 60 */   private PagoRol pagoRol = new PagoRol();
/*  35:    */   private List<PagoRolEmpleado> listaPagoRolEmpleado;
/*  36:    */   private DataTable dtPagoRolEmpleado;
/*  37:    */   
/*  38:    */   @PostConstruct
/*  39:    */   public void init()
/*  40:    */   {
/*  41: 77 */     this.pagoRol = ((PagoRol)AppUtil.getAtributo("pago_rol"));
/*  42: 78 */     cargarDatos();
/*  43: 79 */     setEditado(true);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String editar()
/*  47:    */   {
/*  48: 91 */     return "";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String guardar()
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55:101 */       for (PagoRolEmpleado pagoRolEmpleado : this.listaPagoRolEmpleado)
/*  56:    */       {
/*  57:102 */         pagoRolEmpleado.setListaPagoRolEmpleadoRubro(null);
/*  58:103 */         this.servicioPagoRolEmpleado.guardar(pagoRolEmpleado);
/*  59:    */       }
/*  60:105 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  61:106 */       setEditado(false);
/*  62:    */     }
/*  63:    */     catch (Exception e)
/*  64:    */     {
/*  65:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  66:109 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  67:    */     }
/*  68:112 */     return "pagoRol?faces-redirect=true";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String cancelar()
/*  72:    */   {
/*  73:123 */     super.cancelar();
/*  74:124 */     return "pagoRol?faces-redirect=true";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String eliminar()
/*  78:    */   {
/*  79:133 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String cargarDatos()
/*  83:    */   {
/*  84:142 */     Map<String, String> filters = new HashMap();
/*  85:143 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  86:144 */     filters.put("pagoRol.idPagoRol", "" + this.pagoRol.getIdPagoRol());
/*  87:145 */     this.listaPagoRolEmpleado = this.servicioPagoRolEmpleado.obtenerListaCombo(null, true, filters);
/*  88:146 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String limpiar()
/*  92:    */   {
/*  93:155 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<PagoRolEmpleado> getListaPagoRolEmpleado()
/*  97:    */   {
/*  98:168 */     return this.listaPagoRolEmpleado;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public DataTable getDtPagoRolEmpleado()
/* 102:    */   {
/* 103:177 */     return this.dtPagoRolEmpleado;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setDtPagoRolEmpleado(DataTable dtPagoRolEmpleado)
/* 107:    */   {
/* 108:187 */     this.dtPagoRolEmpleado = dtPagoRolEmpleado;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public PagoRol getPagoRol()
/* 112:    */   {
/* 113:196 */     return this.pagoRol;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setPagoRol(PagoRol pagoRol)
/* 117:    */   {
/* 118:206 */     this.pagoRol = pagoRol;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.DiasFaltaBean
 * JD-Core Version:    0.7.0.1
 */