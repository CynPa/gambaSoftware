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
/*  20:    */ public class CierrePermisoEmpleadoBean
/*  21:    */   extends PermisoEmpleadoBean
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 3880118084974074835L;
/*  24:    */   private LazyDataModel<PermisoEmpleado> listaAprobacionPermisoEmpleado;
/*  25:    */   private DataTable dtAprobacionPermisoEmpleado;
/*  26:    */   
/*  27:    */   @PostConstruct
/*  28:    */   public void init()
/*  29:    */   {
/*  30: 64 */     this.listaAprobacionPermisoEmpleado = new LazyDataModel()
/*  31:    */     {
/*  32:    */       private static final long serialVersionUID = 1L;
/*  33:    */       
/*  34:    */       public List<PermisoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  35:    */       {
/*  36: 71 */         List<PermisoEmpleado> lista = new ArrayList();
/*  37: 72 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  38:    */         
/*  39: 74 */         filters.put("estado", Estado.APROBADO.toString());
/*  40: 75 */         lista = CierrePermisoEmpleadoBean.this.servicioPermisoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  41:    */         
/*  42: 77 */         CierrePermisoEmpleadoBean.this.listaAprobacionPermisoEmpleado.setRowCount(CierrePermisoEmpleadoBean.this.servicioPermisoEmpleado.contarPorCriterio(filters));
/*  43:    */         
/*  44: 79 */         return lista;
/*  45:    */       }
/*  46:    */     };
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void cerrarPermisoEmpleado()
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 93 */       PermisoEmpleado permisoEmpleado = this.servicioPermisoEmpleado.cargarDetalle(this.permisoEmpleado.getIdPermisoEmpleado());
/*  54: 94 */       permisoEmpleado.setEstado(Estado.CERRADO);
/*  55:    */       
/*  56: 96 */       this.servicioPermisoEmpleado.guardar(permisoEmpleado);
/*  57: 97 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61:100 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  62:101 */       e.printStackTrace();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void permisoEmpleadoSelecionado()
/*  67:    */   {
/*  68:107 */     this.permisoEmpleado = ((PermisoEmpleado)this.dtAprobacionPermisoEmpleado.getRowData());
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String editar()
/*  72:    */   {
/*  73:112 */     if (getPermisoEmpleado().getIdPermisoEmpleado() > 0)
/*  74:    */     {
/*  75:113 */       this.permisoEmpleado = this.servicioPermisoEmpleado.cargarDetalle(this.permisoEmpleado.getId());
/*  76:114 */       setEmpleado(this.permisoEmpleado.getHistoricoEmpleado().getEmpleado());
/*  77:115 */       setEditado(true);
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  82:    */     }
/*  83:119 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String crear()
/*  87:    */   {
/*  88:131 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  89:132 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String eliminar()
/*  93:    */   {
/*  94:137 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  95:138 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public LazyDataModel<PermisoEmpleado> getListaAprobacionPermisoEmpleado()
/*  99:    */   {
/* 100:155 */     return this.listaAprobacionPermisoEmpleado;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setListaAprobacionPermisoEmpleado(LazyDataModel<PermisoEmpleado> listaAprobacionPermisoEmpleado)
/* 104:    */   {
/* 105:165 */     this.listaAprobacionPermisoEmpleado = listaAprobacionPermisoEmpleado;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public DataTable getDtAprobacionPermisoEmpleado()
/* 109:    */   {
/* 110:174 */     return this.dtAprobacionPermisoEmpleado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDtAprobacionPermisoEmpleado(DataTable dtAprobacionPermisoEmpleado)
/* 114:    */   {
/* 115:184 */     this.dtAprobacionPermisoEmpleado = dtAprobacionPermisoEmpleado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void negarPermisoEmpleado()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:193 */       this.permisoEmpleado = this.servicioPermisoEmpleado.cargarDetalle(this.permisoEmpleado.getId());
/* 123:194 */       this.permisoEmpleado.setEstado(Estado.ANULADO);
/* 124:195 */       this.servicioPermisoEmpleado.guardar(this.permisoEmpleado);
/* 125:196 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:199 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 130:200 */       e.printStackTrace();
/* 131:    */     }
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.CierrePermisoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */