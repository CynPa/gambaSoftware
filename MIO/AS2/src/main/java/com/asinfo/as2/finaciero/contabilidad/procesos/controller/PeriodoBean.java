/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Periodo;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio;
/*   7:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.faces.bean.ManagedBean;
/*  11:    */ import javax.faces.bean.ManagedProperty;
/*  12:    */ import javax.faces.bean.ViewScoped;
/*  13:    */ import org.apache.log4j.Logger;
/*  14:    */ import org.primefaces.component.datatable.DataTable;
/*  15:    */ import org.primefaces.event.SelectEvent;
/*  16:    */ 
/*  17:    */ @ManagedBean
/*  18:    */ @ViewScoped
/*  19:    */ public class PeriodoBean
/*  20:    */   extends PageControllerAS2
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 4536686766259818703L;
/*  23:    */   @EJB
/*  24:    */   private ServicioPeriodo servicioPeriodo;
/*  25:    */   @EJB
/*  26:    */   private ServicioEjercicio servicioEjercicio;
/*  27:    */   private Periodo periodo;
/*  28:    */   private List<Periodo> listaPeriodo;
/*  29:    */   private DataTable dtPeriodo;
/*  30:    */   @ManagedProperty("#{ejercicioBean}")
/*  31:    */   private EjercicioBean ejercicioBean;
/*  32:    */   
/*  33:    */   public String editar()
/*  34:    */   {
/*  35: 61 */     if (getPeriodo().getId() > 0) {
/*  36: 62 */       setEditado(true);
/*  37:    */     } else {
/*  38: 64 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  39:    */     }
/*  40: 67 */     return "";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String limpiar()
/*  44:    */   {
/*  45: 77 */     this.periodo = new Periodo();
/*  46: 78 */     return "";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String guardar()
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 87 */       getServicioPeriodo().guardar(this.periodo);
/*  54: 88 */       setEditado(false);
/*  55: 89 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  56:    */       
/*  57: 91 */       cargarDatos();
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 94 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  62: 95 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  63:    */     }
/*  64: 97 */     return "";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String eliminar()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:106 */       getServicioPeriodo().eliminar(this.periodo);
/*  72:    */       
/*  73:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  74:109 */       cargarDatos();
/*  75:    */     }
/*  76:    */     catch (Exception e)
/*  77:    */     {
/*  78:112 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  79:113 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  80:    */     }
/*  81:115 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void onRowSelect(SelectEvent event)
/*  85:    */   {
/*  86:122 */     Periodo periodo1 = (Periodo)event.getObject();
/*  87:123 */     setPeriodo(periodo1);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ServicioPeriodo getServicioPeriodo()
/*  91:    */   {
/*  92:132 */     return this.servicioPeriodo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setServicioPeriodo(ServicioPeriodo servicioPeriodo)
/*  96:    */   {
/*  97:142 */     this.servicioPeriodo = servicioPeriodo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Periodo getPeriodo()
/* 101:    */   {
/* 102:151 */     return this.periodo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setPeriodo(Periodo periodo)
/* 106:    */   {
/* 107:161 */     this.periodo = periodo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<Periodo> getListaPeriodo()
/* 111:    */   {
/* 112:170 */     cargarDatos();
/* 113:171 */     return this.listaPeriodo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setListaPeriodo(List<Periodo> listaPeriodo)
/* 117:    */   {
/* 118:181 */     this.listaPeriodo = listaPeriodo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public DataTable getDtPeriodo()
/* 122:    */   {
/* 123:190 */     return this.dtPeriodo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setDtPeriodo(DataTable dtPeriodo)
/* 127:    */   {
/* 128:200 */     this.dtPeriodo = dtPeriodo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public EjercicioBean getEjercicioBean()
/* 132:    */   {
/* 133:209 */     return this.ejercicioBean;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setEjercicioBean(EjercicioBean ejercicioBean)
/* 137:    */   {
/* 138:219 */     this.ejercicioBean = ejercicioBean;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public ServicioEjercicio getServicioEjercicio()
/* 142:    */   {
/* 143:228 */     return this.servicioEjercicio;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setServicioEjercicio(ServicioEjercicio servicioEjercicio)
/* 147:    */   {
/* 148:238 */     this.servicioEjercicio = servicioEjercicio;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String cargarDatos()
/* 152:    */   {
/* 153:247 */     return null;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.PeriodoBean
 * JD-Core Version:    0.7.0.1
 */