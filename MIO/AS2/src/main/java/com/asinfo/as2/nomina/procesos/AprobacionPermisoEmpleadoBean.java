/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   5:    */ import com.asinfo.as2.entities.PermisoEmpleado;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPermisoEmpleado;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.annotation.PostConstruct;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import org.primefaces.component.datatable.DataTable;
/*  15:    */ import org.primefaces.model.LazyDataModel;
/*  16:    */ import org.primefaces.model.SortOrder;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class AprobacionPermisoEmpleadoBean
/*  21:    */   extends PermisoEmpleadoBean
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 3880118084974074835L;
/*  24:    */   private LazyDataModel<PermisoEmpleado> listaAprobacionPermisoEmpleado;
/*  25:    */   private DataTable dtAprobacionPermisoEmpleado;
/*  26:    */   
/*  27:    */   @PostConstruct
/*  28:    */   public void init()
/*  29:    */   {
/*  30: 62 */     this.listaAprobacionPermisoEmpleado = new LazyDataModel()
/*  31:    */     {
/*  32:    */       private static final long serialVersionUID = 1L;
/*  33:    */       
/*  34:    */       public List<PermisoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  35:    */       {
/*  36: 69 */         List<PermisoEmpleado> lista = new ArrayList();
/*  37: 70 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  38:    */         
/*  39: 72 */         filters.put("estado", Estado.ELABORADO.toString());
/*  40: 73 */         lista = AprobacionPermisoEmpleadoBean.this.servicioPermisoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  41:    */         
/*  42: 75 */         AprobacionPermisoEmpleadoBean.this.listaAprobacionPermisoEmpleado.setRowCount(AprobacionPermisoEmpleadoBean.this.servicioPermisoEmpleado.contarPorCriterio(filters));
/*  43:    */         
/*  44: 77 */         return lista;
/*  45:    */       }
/*  46:    */     };
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void aprobarPermisoEmpleado()
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 91 */       PermisoEmpleado permisoEmpleado = this.servicioPermisoEmpleado.cargarDetalle(this.permisoEmpleado.getIdPermisoEmpleado());
/*  54: 92 */       permisoEmpleado.setEstado(Estado.APROBADO);
/*  55: 93 */       this.servicioPermisoEmpleado.guardar(permisoEmpleado);
/*  56: 94 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  57:    */     }
/*  58:    */     catch (Exception e)
/*  59:    */     {
/*  60: 97 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  61: 98 */       e.printStackTrace();
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void negarPermisoEmpleado()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:109 */       this.permisoEmpleado = this.servicioPermisoEmpleado.cargarDetalle(this.permisoEmpleado.getId());
/*  70:110 */       this.permisoEmpleado.setEstado(Estado.ANULADO);
/*  71:111 */       this.servicioPermisoEmpleado.guardar(this.permisoEmpleado);
/*  72:112 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  77:116 */       e.printStackTrace();
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void permisoEmpleadoSelecionado()
/*  82:    */   {
/*  83:122 */     this.permisoEmpleado = ((PermisoEmpleado)this.dtAprobacionPermisoEmpleado.getRowData());
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String editar()
/*  87:    */   {
/*  88:132 */     if (getPermisoEmpleado().getIdPermisoEmpleado() > 0)
/*  89:    */     {
/*  90:133 */       this.permisoEmpleado = this.servicioPermisoEmpleado.cargarDetalle(this.permisoEmpleado.getId());
/*  91:134 */       setEmpleado(this.permisoEmpleado.getHistoricoEmpleado().getEmpleado());
/*  92:135 */       setEditado(true);
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  97:    */     }
/*  98:139 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String crear()
/* 102:    */   {
/* 103:149 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 104:150 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String eliminar()
/* 108:    */   {
/* 109:155 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 110:156 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public LazyDataModel<PermisoEmpleado> getListaAprobacionPermisoEmpleado()
/* 114:    */   {
/* 115:173 */     return this.listaAprobacionPermisoEmpleado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setListaAprobacionPermisoEmpleado(LazyDataModel<PermisoEmpleado> listaAprobacionPermisoEmpleado)
/* 119:    */   {
/* 120:183 */     this.listaAprobacionPermisoEmpleado = listaAprobacionPermisoEmpleado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public DataTable getDtAprobacionPermisoEmpleado()
/* 124:    */   {
/* 125:192 */     return this.dtAprobacionPermisoEmpleado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setDtAprobacionPermisoEmpleado(DataTable dtAprobacionPermisoEmpleado)
/* 129:    */   {
/* 130:202 */     this.dtAprobacionPermisoEmpleado = dtAprobacionPermisoEmpleado;
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.AprobacionPermisoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */