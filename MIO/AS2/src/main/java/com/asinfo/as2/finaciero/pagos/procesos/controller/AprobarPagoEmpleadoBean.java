/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.PagoEmpleado;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoEmpleado;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.annotation.PostConstruct;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import org.apache.log4j.Logger;
/*  15:    */ import org.primefaces.component.datatable.DataTable;
/*  16:    */ import org.primefaces.model.LazyDataModel;
/*  17:    */ import org.primefaces.model.SortOrder;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class AprobarPagoEmpleadoBean
/*  22:    */   extends PageControllerAS2
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -8338346628015918176L;
/*  25:    */   @EJB
/*  26:    */   private transient ServicioPagoEmpleado servicioPagoEmpleado;
/*  27:    */   private PagoEmpleado pagoEmpleado;
/*  28:    */   private LazyDataModel<PagoEmpleado> listaPagoEmpleado;
/*  29:    */   private DataTable dtPagoEmpleado;
/*  30:    */   
/*  31:    */   @PostConstruct
/*  32:    */   public void init()
/*  33:    */   {
/*  34: 69 */     this.listaPagoEmpleado = new LazyDataModel()
/*  35:    */     {
/*  36:    */       private static final long serialVersionUID = 609994324204466060L;
/*  37:    */       
/*  38:    */       public List<PagoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  39:    */       {
/*  40: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  41:    */         
/*  42: 83 */         filters.put("estado", Estado.ELABORADO.toString());
/*  43:    */         
/*  44: 85 */         List<PagoEmpleado> lista = AprobarPagoEmpleadoBean.this.servicioPagoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  45:    */         
/*  46: 87 */         AprobarPagoEmpleadoBean.this.listaPagoEmpleado.setRowCount(AprobarPagoEmpleadoBean.this.servicioPagoEmpleado.contarPorCriterio(filters));
/*  47: 88 */         return lista;
/*  48:    */       }
/*  49:    */     };
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String crear()
/*  53:    */   {
/*  54:103 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  55:104 */     return "";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String editar()
/*  59:    */   {
/*  60:113 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  61:114 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String guardar()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:124 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  69:    */       
/*  70:    */ 
/*  71:127 */       limpiar();
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  76:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  77:    */     }
/*  78:132 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String eliminar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:143 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89:145 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  90:146 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  91:    */     }
/*  92:148 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String aprobarPagoEmpleado()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:158 */       PagoEmpleado pagoEmpleado = (PagoEmpleado)this.dtPagoEmpleado.getRowData();
/* 100:159 */       this.servicioPagoEmpleado.actualizarEstado(pagoEmpleado.getId(), Estado.APROBADO);
/* 101:    */       
/* 102:161 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 103:    */     }
/* 104:    */     catch (Exception e)
/* 105:    */     {
/* 106:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 107:164 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 108:    */     }
/* 109:166 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String cargarDatos()
/* 113:    */   {
/* 114:175 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String limpiar()
/* 118:    */   {
/* 119:184 */     crear();
/* 120:185 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public PagoEmpleado getPagoEmpleado()
/* 124:    */   {
/* 125:202 */     return this.pagoEmpleado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPagoEmpleado(PagoEmpleado pagoEmpleado)
/* 129:    */   {
/* 130:212 */     this.pagoEmpleado = pagoEmpleado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public DataTable getDtPagoEmpleado()
/* 134:    */   {
/* 135:221 */     return this.dtPagoEmpleado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setDtPagoEmpleado(DataTable dtPagoEmpleado)
/* 139:    */   {
/* 140:231 */     this.dtPagoEmpleado = dtPagoEmpleado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public LazyDataModel<PagoEmpleado> getListaPagoEmpleado()
/* 144:    */   {
/* 145:240 */     return this.listaPagoEmpleado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setListaPagoEmpleado(LazyDataModel<PagoEmpleado> listaPagoEmpleado)
/* 149:    */   {
/* 150:250 */     this.listaPagoEmpleado = listaPagoEmpleado;
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.AprobarPagoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */