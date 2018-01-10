/*   1:    */ package com.asinfo.as2.finaciero.cobros.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.event.SelectEvent;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class PlanTarjetaCreditoBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -9061218854698018535L;
/*  29:    */   @EJB
/*  30:    */   private ServicioGenerico<PlanTarjetaCredito> servicioPlan;
/*  31:    */   private PlanTarjetaCredito plan;
/*  32:    */   private LazyDataModel<PlanTarjetaCredito> listaPlan;
/*  33:    */   private DataTable dataTablePlan;
/*  34:    */   
/*  35:    */   @PostConstruct
/*  36:    */   public void init()
/*  37:    */   {
/*  38: 57 */     this.listaPlan = new LazyDataModel()
/*  39:    */     {
/*  40:    */       private static final long serialVersionUID = 1L;
/*  41:    */       
/*  42:    */       public List<PlanTarjetaCredito> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  43:    */       {
/*  44: 64 */         List<PlanTarjetaCredito> lista = new ArrayList();
/*  45: 65 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  46: 66 */         lista = PlanTarjetaCreditoBean.this.servicioPlan.obtenerListaPorPagina(PlanTarjetaCredito.class, startIndex, pageSize, sortField, ordenar, filters);
/*  47: 67 */         PlanTarjetaCreditoBean.this.listaPlan.setRowCount(PlanTarjetaCreditoBean.this.servicioPlan.contarPorCriterio(PlanTarjetaCredito.class, filters));
/*  48:    */         
/*  49: 69 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String editar()
/*  55:    */   {
/*  56: 84 */     if (this.plan.getIdPlanTarjetaCredito() > 0) {
/*  57: 85 */       setEditado(true);
/*  58:    */     } else {
/*  59: 87 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  60:    */     }
/*  61: 90 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String guardar()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68: 99 */       this.servicioPlan.guardar(this.plan);
/*  69:100 */       limpiar();
/*  70:101 */       setEditado(false);
/*  71:102 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  76:105 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  77:    */     }
/*  78:108 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String eliminar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:117 */       this.servicioPlan.eliminar(this.plan);
/*  86:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:120 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  91:121 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  92:    */     }
/*  93:124 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String cargarDatos()
/*  97:    */   {
/*  98:132 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String limpiar()
/* 102:    */   {
/* 103:140 */     crearPlan();
/* 104:141 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void crearPlan()
/* 108:    */   {
/* 109:148 */     this.plan = new PlanTarjetaCredito();
/* 110:149 */     this.plan.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 111:150 */     this.plan.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 112:151 */     this.plan.setActivo(true);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void onRowSelect(SelectEvent event)
/* 116:    */   {
/* 117:158 */     PlanTarjetaCredito plan = (PlanTarjetaCredito)event.getObject();
/* 118:159 */     setPlan(plan);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public PlanTarjetaCredito getPlan()
/* 122:    */   {
/* 123:164 */     if (this.plan == null) {
/* 124:165 */       crearPlan();
/* 125:    */     }
/* 126:167 */     return this.plan;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPlan(PlanTarjetaCredito plan)
/* 130:    */   {
/* 131:171 */     this.plan = plan;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public LazyDataModel<PlanTarjetaCredito> getListaPlan()
/* 135:    */   {
/* 136:175 */     return this.listaPlan;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setListaPlan(LazyDataModel<PlanTarjetaCredito> listaPlan)
/* 140:    */   {
/* 141:179 */     this.listaPlan = listaPlan;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public DataTable getDataTablePlan()
/* 145:    */   {
/* 146:183 */     return this.dataTablePlan;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDataTablePlan(DataTable dataTablePlan)
/* 150:    */   {
/* 151:187 */     this.dataTablePlan = dataTablePlan;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.configuracion.PlanTarjetaCreditoBean
 * JD-Core Version:    0.7.0.1
 */